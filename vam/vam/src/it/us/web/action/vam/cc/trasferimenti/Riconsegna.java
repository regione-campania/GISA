/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.trasferimenti;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.BUtenteAll;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupEventoAperturaCc;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.constants.TipiRichiedente;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.vam.AccettazioneDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;
import it.us.web.util.vam.CCUtil;
import it.us.web.util.vam.CaninaRemoteUtil;

import java.sql.Connection;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class Riconsegna extends GenericAction {

	public void can() throws AuthorizationException 
	{
		BGuiView gui = GuiViewDAO.getView("TRASFERIMENTI", "MAIN", "MAIN");
		can(gui, "w");
	}
	
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("trasferimenti");
	}

	public void execute() throws Exception 
	{
		
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		Trasferimento trasferimento = (Trasferimento) persistence.find(Trasferimento.class, interoFromRequest("idTrasferimento"));

		/**
		 * Commentato in quanto una riconsegna non deve mai passare per approvazione CRIUV (VERONICA)
		 */
	//	if(trasferimento.getUrgenza())
			trasferimento.setDataApprovazioneRiconsegna(new Date());
		trasferimento.setDataRiconsegna(dataFromRequest( "dataRiconsegna" ));
		trasferimento.setNotaRiconsegna(stringaFromRequest("notaRiconsegna"));

		trasferimento.setModified(trasferimento.getDataRiconsegna());
		trasferimento.setModifiedBy(utente);
		trasferimento.setDataRifiutoRiconsegna( null );
		
		Iterator<LookupOperazioniAccettazione> iter = trasferimento.getOperazioniRichieste().iterator();
		while(iter.hasNext())
		{
			LookupOperazioniAccettazione op = iter.next();
			op.getOperazioniCondizionate();
			op.getOperazioniCondizionanti();
		}

		// Chiusura cc destinatario
		CartellaClinica ccDestinatario = trasferimento.getCartellaClinicaMortoDestinatario();
		if(ccDestinatario==null)
			ccDestinatario = trasferimento.getCartellaClinicaDestinatario();
		ccDestinatario.setDataChiusura(dataFromRequest( "dataRiconsegna" ));
		
		/**
		 * Commentato in quanto una riconsegna non deve mai passare per approvazione CRIUV (VERONICA)
		 */
//		if( // se il trasferimento era in asl la restituzione non passa per il CRIUV
//			trasferimento.getClinicaDestinazione().getLookupAsl().getId() 
//			== trasferimento.getClinicaOrigine().getLookupAsl().getId() || trasferimento.getUrgenza() )
//		{
			// Apertura nuova cc mittente
			Accettazione accettazioneNuovaMittente = nuovaACCmittente(trasferimento, persistence, connection, UtenteDAO.getUtenteAll(utente.getId()));
			persistence.insert(accettazioneNuovaMittente);
			
			// 2.Creazione nuova cc per il mittente
			CartellaClinica ccNuovaMittente = nuovaCCmittente(trasferimento, accettazioneNuovaMittente, persistence,connection);
			persistence.insert(ccNuovaMittente);
//		}
			
			//Aggiornamento BDU se asl clinica destinazione != asl clinica mittente
			if (trasferimento.getClinicaOrigine().getLookupAsl().getId() != trasferimento.getClinicaDestinazione()
					.getLookupAsl().getId()) {
				CaninaRemoteUtil.comunicaRiconsegnaClinicaExtraAsl(trasferimento.getCartellaClinica().getAccettazione().getAnimale(),
						trasferimento.getDataApprovazioneRiconsegna().toString(), utente.getClinica().getLookupAsl().getId(),  utente
								.getClinica().getId(), trasferimento.getClinicaOrigine().getId(), utente, req);
			}
		
		persistence.update(ccDestinatario);
		persistence.update(trasferimento);
		persistence.commit();
		
		
		

		setMessaggio("Riconsegna animale trasferito effettuata con successo.");
		redirectTo("vam.cc.trasferimenti.List.us");
	}

	public static CartellaClinica nuovaCCmittente(Trasferimento trasferimento,
			Accettazione accettazioneNuovaMittente, Persistence persistence,Connection connection) throws ParseException,
			Exception {
		CartellaClinica ccMittente = trasferimento.getCartellaClinica();
		CartellaClinica ccNuovaMittente = new CartellaClinica();
		int progressivo = CCUtil.getSequence(connection, trasferimento.getClinicaOrigine().getId(),trasferimento.getDataRiconsegna().getYear());
		String numeroCC = CCUtil.assignedNumber(connection, progressivo, trasferimento.getClinicaOrigine().getId(),trasferimento.getDataRiconsegna().getYear());

		ccNuovaMittente.setNumero(numeroCC);
		ccNuovaMittente.setAccettazione(accettazioneNuovaMittente);
		ccNuovaMittente.setProgressivo(progressivo);
		ccNuovaMittente.setDataApertura(trasferimento.getDataRiconsegna());
		ccNuovaMittente.setEntered(new Date());
		ccNuovaMittente.setEnteredBy(ccMittente.getEnteredBy());
		ccNuovaMittente.setModified( ccNuovaMittente.getEntered() );
		ccNuovaMittente.setModifiedBy(ccMittente.getEnteredBy());
		ccNuovaMittente.setCcRiconsegna(true);
		ccNuovaMittente.setFascicoloSanitario(ccMittente.getFascicoloSanitario());
		ccNuovaMittente.setCatturaData(ccMittente.getCatturaData());
		ccNuovaMittente.setCatturaLuogo(ccMittente.getCatturaLuogo());
		ccNuovaMittente.setCatturaPersonaleIntervenuto(ccMittente.getCatturaPersonaleIntervenuto());
		ccNuovaMittente.setDataDecesso(ccMittente.getDataDecesso());
		ccNuovaMittente.setLookupAlimentazionis( (Set<LookupAlimentazioni>)new HashSet<LookupAlimentazioni>(ccMittente.getLookupAlimentazionis()));
		ccNuovaMittente.setLookupHabitats( (Set<LookupHabitat>)new HashSet<LookupHabitat>(ccMittente.getLookupHabitats()));
		ccNuovaMittente.setPeso(ccMittente.getPeso());
		ccNuovaMittente.setDayHospital(ccMittente.isDayHospital());
		ccNuovaMittente.setEventoApertura((LookupEventoAperturaCc)persistence.find(LookupEventoAperturaCc.class, 3));

		// Associazione al trasferimento della cc nuova cc mittente
		trasferimento.setCartellaClinicaMittenteRiconsegna(ccNuovaMittente);
		return ccNuovaMittente;
	}

	public static Accettazione nuovaACCmittente(Trasferimento trasferimento, Persistence persistence, Connection connection, BUtenteAll utente) throws Exception 
	{
		// 1.Creazione accettazione fittizia per la nuova cc
		Accettazione accettazioneNuovaMittente = new Accettazione();
		Accettazione accettazioneMittente = trasferimento.getCartellaClinica().getAccettazione();
		accettazioneNuovaMittente.setLookupTipiRichiedente((LookupTipiRichiedente)persistence.find(LookupTipiRichiedente.class, TipiRichiedente.ALTRO));
		accettazioneNuovaMittente.setAnimale(accettazioneMittente.getAnimale());
		accettazioneNuovaMittente.setAslAnimale(accettazioneMittente.getAslAnimale());
		accettazioneNuovaMittente.setProprietarioCap(accettazioneMittente.getProprietarioCap());
		accettazioneNuovaMittente.setProprietarioCodiceFiscale(accettazioneMittente.getProprietarioCodiceFiscale());
		accettazioneNuovaMittente.setProprietarioCognome(accettazioneMittente.getProprietarioCognome());
		accettazioneNuovaMittente.setProprietarioTipo(accettazioneMittente.getProprietarioTipo());
		accettazioneNuovaMittente.setProprietarioComune(accettazioneMittente.getProprietarioComune());
		accettazioneNuovaMittente.setProprietarioDocumento(accettazioneMittente.getProprietarioDocumento());
		accettazioneNuovaMittente.setProprietarioIndirizzo(accettazioneMittente.getProprietarioIndirizzo());
		accettazioneNuovaMittente.setProprietarioNome(accettazioneMittente.getProprietarioNome());
		accettazioneNuovaMittente.setProprietarioProvincia(accettazioneMittente.getProprietarioProvincia());
		accettazioneNuovaMittente.setProprietarioTelefono(accettazioneMittente.getProprietarioTelefono());
		accettazioneNuovaMittente.setOperazioniRichieste(new HashSet<LookupOperazioniAccettazione>(accettazioneMittente.getOperazioniRichieste()));
		accettazioneNuovaMittente.setRandagio(accettazioneMittente.getRandagio());
		accettazioneNuovaMittente.setSterilizzato(accettazioneMittente.getSterilizzato());
		accettazioneNuovaMittente.setData(trasferimento.getDataRiconsegna());
		accettazioneNuovaMittente.setEntered(new Date());
		accettazioneNuovaMittente.setEnteredBy(accettazioneMittente.getEnteredBy());
		accettazioneNuovaMittente.setModified(new Date());
		accettazioneNuovaMittente.setModifiedBy(accettazioneMittente.getEnteredBy());
		accettazioneNuovaMittente.setRichiedenteAltro("Clinica \"" + trasferimento.getClinicaDestinazione().getNome() + "\": riconsegna dopo trasferimento temporaneo");
		BUtenteAll ut = UtenteDAO.getUtenteAll(trasferimento.getCartellaClinica().getAccettazione().getEnteredBy().getId());
		setupProgressivo(accettazioneNuovaMittente, connection, ut);
		return accettazioneNuovaMittente;
	}
	
	

	@SuppressWarnings("unchecked")
	public static void setupProgressivo(Accettazione accettazione, Connection connection, BUtenteAll utente) 
	{
		int nextProgressivo = 1;
		nextProgressivo =  AccettazioneDAO.getNextProgressivo(DateUtils.annoCorrente( accettazione.getData() )  , 
				                           DateUtils.annoSuccessivo( accettazione.getData()) ,
				                           utente.getClinica().getId(),
				                           connection );
		
		accettazione.setProgressivo( nextProgressivo );
	
	}
}
