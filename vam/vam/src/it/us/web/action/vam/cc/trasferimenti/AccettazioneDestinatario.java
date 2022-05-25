/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.trasferimenti;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupEventoAperturaCc;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.constants.Specie;
import it.us.web.constants.TipiRichiedente;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;
import it.us.web.util.vam.CCUtil;
import it.us.web.util.vam.CaninaRemoteUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class AccettazioneDestinatario extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "TRASFERIMENTI", "MAIN", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("trasferimenti");
	}

	public void execute() throws Exception
	{
		Context ctxVAM = new InitialContext();
		javax.sql.DataSource dsVAM = (javax.sql.DataSource) ctxVAM.lookup("java:comp/env/jdbc/vamM");
		connection = dsVAM.getConnection();
		
		Trasferimento trasferimento = (Trasferimento) persistence.find( Trasferimento.class, interoFromRequest( "idTrasferimento" ) );
	
		trasferimento.setDataAccettazioneDestinatario( dataFromRequest( "dataAccettazioneDestinatario" ) );
		trasferimento.setNotaDestinatario( stringaFromRequest( "notaDestinatario" ) );
		
		trasferimento.setModified( new Date() );
		trasferimento.setModifiedBy( utente );
		
		//Apertura cc per il destinatario
		//1.Creazione accettazione fittizia
		Accettazione accettazioneDestinatario = new Accettazione();
		Accettazione accettazioneMittente = trasferimento.getCartellaClinica().getAccettazione();
		accettazioneDestinatario.setLookupTipiRichiedente((LookupTipiRichiedente)persistence.find(LookupTipiRichiedente.class, TipiRichiedente.ALTRO));
		accettazioneDestinatario.setAnimale(accettazioneMittente.getAnimale());
		accettazioneDestinatario.setAslAnimale(accettazioneMittente.getAslAnimale());
		accettazioneDestinatario.setProprietarioCap(accettazioneMittente.getProprietarioCap());
		accettazioneDestinatario.setProprietarioCodiceFiscale(accettazioneMittente.getProprietarioCodiceFiscale());
		accettazioneDestinatario.setProprietarioCognome(accettazioneMittente.getProprietarioCognome());
		accettazioneDestinatario.setProprietarioTipo(accettazioneMittente.getProprietarioTipo());
		accettazioneDestinatario.setProprietarioComune(accettazioneMittente.getProprietarioComune());
		accettazioneDestinatario.setProprietarioDocumento(accettazioneMittente.getProprietarioDocumento());
		accettazioneDestinatario.setProprietarioIndirizzo(accettazioneMittente.getProprietarioIndirizzo());
		accettazioneDestinatario.setProprietarioNome(accettazioneMittente.getProprietarioNome());
		accettazioneDestinatario.setProprietarioProvincia(accettazioneMittente.getProprietarioProvincia());
		accettazioneDestinatario.setProprietarioTelefono(accettazioneMittente.getProprietarioTelefono());
		accettazioneDestinatario.setOperazioniRichieste(new HashSet<LookupOperazioniAccettazione>(trasferimento.getOperazioniRichieste()));
		accettazioneDestinatario.setRandagio(accettazioneMittente.getRandagio());
		accettazioneDestinatario.setSterilizzato(accettazioneMittente.getSterilizzato());
		accettazioneDestinatario.setData( dataFromRequest( "dataAccettazioneDestinatario" ) );
		accettazioneDestinatario.setEntered(new Date());
		accettazioneDestinatario.setEnteredBy(utente);
		accettazioneDestinatario.setModified(new Date());
		accettazioneDestinatario.setModifiedBy(utente);
		accettazioneDestinatario.setRichiedenteAltro("Clinica \"" + trasferimento.getClinicaOrigine().getNome() +  "\" mediante trasferimento");
		setupProgressivo( accettazioneDestinatario );
		
		//2.Creazione cc del destinatario
		CartellaClinica ccDestinatario = new CartellaClinica();
		int progressivo = CCUtil.getSequence(connection, utente.getClinica().getId(), dataFromRequest( "dataAccettazioneDestinatario" ) .getYear());
		String numeroCC = CCUtil.assignedNumber(connection, progressivo, utente.getClinica().getId(), dataFromRequest( "dataAccettazioneDestinatario" ) .getYear());
		
		ccDestinatario.setNumero(numeroCC);
		ccDestinatario.setAccettazione(accettazioneDestinatario);
		ccDestinatario.setProgressivo(progressivo);
		ccDestinatario.setDataApertura( dataFromRequest( "dataAccettazioneDestinatario" ) );
		ccDestinatario.setEntered(new Date());
		ccDestinatario.setEnteredBy(utente);
		ccDestinatario.setModified(new Date());
		ccDestinatario.setModifiedBy(utente);
		ccDestinatario.setCcPostTrasferimento(true);
		ccDestinatario.setFascicoloSanitario(trasferimento.getCartellaClinica().getFascicoloSanitario());
		ccDestinatario.setCcMorto(trasferimento.getCartellaClinica().getCcMorto());
		ccDestinatario.setCatturaData(trasferimento.getCartellaClinica().getCatturaData());
		ccDestinatario.setCatturaLuogo(trasferimento.getCartellaClinica().getCatturaLuogo());
		ccDestinatario.setCatturaPersonaleIntervenuto(trasferimento.getCartellaClinica().getCatturaPersonaleIntervenuto());
		ccDestinatario.setDataDecesso(trasferimento.getCartellaClinica().getDataDecesso());
		ccDestinatario.setLookupAlimentazionis( (Set<LookupAlimentazioni>)new HashSet<LookupAlimentazioni>(trasferimento.getCartellaClinica().getLookupAlimentazionis()));
		ccDestinatario.setLookupHabitats((Set<LookupHabitat>)new HashSet<LookupHabitat>(trasferimento.getCartellaClinica().getLookupHabitats()));
		ccDestinatario.setPeso(trasferimento.getCartellaClinica().getPeso());
		ccDestinatario.setDayHospital(trasferimento.getCartellaClinica().isDayHospital());
		ccDestinatario.setEventoApertura((LookupEventoAperturaCc)persistence.find(LookupEventoAperturaCc.class, 2));
		ccDestinatario.setRuoloEnteredBy( ((HashMap<Integer, String>)context.getAttribute("ruoliUtenti")).get(utente.getSuperutente().getId()) );
		//Accettazione trasferimento automatico per necroscopia
		if(trasferimento.getAutomaticoPerNecroscopia())
			ccDestinatario.setAutopsia(trasferimento.getCartellaClinica().getAutopsia());
		
		//Associazione al trasferimento della cc destinatario
		trasferimento.setCartellaClinicaDestinatario(ccDestinatario);
		
		persistence.insert( accettazioneDestinatario );
		persistence.insert( ccDestinatario );
		persistence.update( trasferimento );
		persistence.commit();
		setMessaggio( "Richiesta di trasferimento accettata con successo." );
		
		
		//Aggiornamento BDU se asl clinica destinazione != asl clinica mittente
		if (trasferimento.getClinicaOrigine().getLookupAsl().getId() != trasferimento.getClinicaDestinazione().getLookupAsl().getId() && !accettazioneMittente.getAnimale().getDecedutoNonAnagrafe() && accettazioneMittente.getAnimale().getLookupSpecie().getId()!=Specie.SINANTROPO) 
		{
			CaninaRemoteUtil.comunicaTrasferimentoClinicaExtraAsl(accettazioneMittente.getAnimale(),
					accettazioneDestinatario.getEntered().toString(), trasferimento.getClinicaOrigine().getId(), utente
							.getClinica().getId(), utente.getClinica().getLookupAsl().getId(), utente, req);
		}
		redirectTo( "vam.cc.Detail.us?idCartellaClinica=" + trasferimento.getCartellaClinicaDestinatario().getId() );
	}
	
	@SuppressWarnings("unchecked")
	private void setupProgressivo(Accettazione accettazione)
	{
		int nextProgressivo = 1;
		List<Integer> result = persistence.createCriteria( Accettazione.class )
			.add( Restrictions.ge( "data", DateUtils.annoCorrente( accettazione.getData() ) ) )
			.add( Restrictions.lt( "data", DateUtils.annoSuccessivo( accettazione.getData() ) ) )
			.setProjection( Projections.max( "progressivo" ) )
			.createCriteria( "enteredBy" )
				.add( Restrictions.eq( "clinica", utente.getClinica() ) ).list();
		
		if( result.size() > 0 && result.get( 0 ) != null )
		{
			nextProgressivo = (result.get( 0 ) + 1 );
		}
		
		accettazione.setProgressivo( nextProgressivo );
	}
}
