/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.decessi;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.Autopsia;
import it.us.web.bean.vam.AutopsiaCMF;
import it.us.web.bean.vam.AutopsiaOrganiTipiEsamiEsiti;
import it.us.web.bean.vam.AutopsiaOrganoPatologie;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupAlimentazioni;
import it.us.web.bean.vam.lookup.LookupAutopsiaEsitiEsami;
import it.us.web.bean.vam.lookup.LookupAutopsiaFenomeniCadaverici;
import it.us.web.bean.vam.lookup.LookupAutopsiaModalitaConservazione;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrgani;
import it.us.web.bean.vam.lookup.LookupAutopsiaOrganiTipiEsamiEsiti;
import it.us.web.bean.vam.lookup.LookupAutopsiaPatologiePrevalenti;
import it.us.web.bean.vam.lookup.LookupAutopsiaTipiEsami;
import it.us.web.bean.vam.lookup.LookupCMF;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.bean.vam.lookup.LookupEventoAperturaCc;
import it.us.web.bean.vam.lookup.LookupHabitat;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.CCUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Add extends GenericAction {

	
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("decesso");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Add.class);
			
		int idClinica 	   = utente.getClinica().getId();
				
		/* Gestione Causa Morte*/
		int idCM = interoFromRequest("causaMorteIniziale");
		
		boolean dataMortePresunta = !booleanoFromRequest("dataMorteCerta");
				
		//*===================================================*/
		/*===================================================*/
		/*  AGGIORNAMENTO IN BDR DEL DECESSO*/
		/*===================================================*/
		/*===================================================*/
		int numeroRegistrazione = 0;
		
		if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.CANE && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) {
						
			String dataMorte = stringaFromRequest("dataMorte");
			
			String provincia = stringaFromRequest("provincia");
			String comune = "0"; 
			if (provincia.equals("BN"))		
				comune = stringaFromRequest("comuneBN");
			else if (provincia.equals("NA"))	
				comune = stringaFromRequest("comuneNA");
			else if (provincia.equals("SA"))	
				comune = stringaFromRequest("comuneSA");
			else if (provincia.equals("CE"))	
				comune = stringaFromRequest("comuneCE");
			else if (provincia.equals("AV"))	
				comune = stringaFromRequest("comuneAV");
			
			CaninaRemoteUtil.eseguiDecesso(
						cc.getAccettazione().getAnimale(), 
						idCM, 
						dataMorte, 
						dataMortePresunta, 
						comune,
						stringaFromRequest("indirizzo"),
						stringaFromRequest("note"),
						utente,req);
				
		}
		else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.GATTO && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) {
						
			String dataMorte = stringaFromRequest("dataMorte");
			
			String provincia = stringaFromRequest("provincia");
			String comune = "0"; 
			if (provincia.equals("BN"))		
				comune = stringaFromRequest("comuneBN");
			else if (provincia.equals("NA"))	
				comune = stringaFromRequest("comuneNA");
			else if (provincia.equals("SA"))	
				comune = stringaFromRequest("comuneSA");
			else if (provincia.equals("CE"))	
				comune = stringaFromRequest("comuneCE");
			else if (provincia.equals("AV"))	
				comune = stringaFromRequest("comuneAV");
			
			FelinaRemoteUtil.eseguiDecesso(
					cc.getAccettazione().getAnimale(), 
					idCM, 
					dataMorte, 
					dataMortePresunta, 
					comune,
					stringaFromRequest("indirizzo"),
					stringaFromRequest("note"),
					utente,req);
		}
		
		else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.SINANTROPO && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) {
								
			it.us.web.action.sinantropi.decessi.Add.addDecesso(logger, SinantropoUtil.getSinantropoByNumero(persistence, cc.getAccettazione().getAnimale().getIdentificativo()), utente, persistence, req, dataFromRequest("dataMorte"),idCM, dataMortePresunta );
		}
		
		cc.setDataChiusura(new Date());
		CartellaClinica ccMorto = null;
		Accettazione accettazioneNuova = null;
		Trasferimento trasferimento = null;
			 accettazioneNuova = new Accettazione();
			 accettazioneNuova.setAnimale(cc.getAccettazione().getAnimale());
			 accettazioneNuova.setAslAnimale(cc.getAccettazione().getAslAnimale());
			 accettazioneNuova.setProprietarioCap(cc.getAccettazione().getProprietarioCap());
			 accettazioneNuova.setProprietarioCodiceFiscale(cc.getAccettazione().getProprietarioCodiceFiscale());
			 accettazioneNuova.setProprietarioCognome(cc.getAccettazione().getProprietarioCognome());
			 accettazioneNuova.setProprietarioTipo(cc.getAccettazione().getProprietarioTipo());
			 accettazioneNuova.setProprietarioComune(cc.getAccettazione().getProprietarioComune());
			 accettazioneNuova.setProprietarioDocumento(cc.getAccettazione().getProprietarioDocumento());
			 accettazioneNuova.setProprietarioIndirizzo(cc.getAccettazione().getProprietarioIndirizzo());
			 accettazioneNuova.setProprietarioNome(cc.getAccettazione().getProprietarioNome());
			 accettazioneNuova.setProprietarioProvincia(cc.getAccettazione().getProprietarioProvincia());
			 accettazioneNuova.setProprietarioTelefono(cc.getAccettazione().getProprietarioTelefono());
			 accettazioneNuova.setOperazioniRichieste(new HashSet<LookupOperazioniAccettazione>(cc.getAccettazione().getOperazioniRichieste()));
			 accettazioneNuova.setEnteredBy(utente);
			 accettazioneNuova.setModifiedBy(utente);
			 accettazioneNuova.setRandagio(cc.getAccettazione().getRandagio());
			 accettazioneNuova.setSterilizzato(cc.getAccettazione().getSterilizzato());
			 accettazioneNuova.setData(new Date());
			 accettazioneNuova.setEntered(new Date());
			 accettazioneNuova.setModified(new Date());
			 accettazioneNuova.setRichiedenteAltro("Aperta post decesso animale");
			 setupProgressivo(accettazioneNuova);
			 
			 ccMorto = new CartellaClinica();
			 int progressivo = CCUtil.getSequence(persistence, idClinica,new Date().getYear());
			 String numeroCC = CCUtil.assignedNumber(persistence, progressivo, idClinica,new Date().getYear());
			 ccMorto.setDataApertura(new Date());
			 ccMorto.setEntered(new Date());
			 ccMorto.setEnteredBy(utente);
			 ccMorto.setModified(new Date());
			 ccMorto.setModifiedBy(utente);
			 ccMorto.setNumero(numeroCC);
			 ccMorto.setAccettazione(accettazioneNuova);
			 ccMorto.setProgressivo(progressivo);
			 ccMorto.setCcPostTrasferimento(cc.getCcPostTrasferimento());
			 if(cc.getCcPostTrasferimento())
			 {
				 trasferimento = ((List<Trasferimento>)persistence.createCriteria(Trasferimento.class).add(Restrictions.eq("cartellaClinicaDestinatario", cc)).list()).get(0);
				 trasferimento.setCartellaClinicaMortoDestinatario(ccMorto);
				 ccMorto.setCcPostTrasferimentoMorto(true);
			 }
			 ccMorto.setCcRiconsegna(cc.getCcRiconsegna());
			 ccMorto.setFascicoloSanitario(cc.getFascicoloSanitario());
			 ccMorto.setCatturaData(cc.getCatturaData());
			 ccMorto.setCatturaLuogo(cc.getCatturaLuogo());
			 ccMorto.setCatturaPersonaleIntervenuto(cc.getCatturaPersonaleIntervenuto());
			 ccMorto.setLookupAlimentazionis( (Set<LookupAlimentazioni>)new HashSet<LookupAlimentazioni>(cc.getLookupAlimentazionis()));
			 ccMorto.setLookupHabitats( (Set<LookupHabitat>)new HashSet<LookupHabitat>(cc.getLookupHabitats()));
			 ccMorto.setPeso(cc.getPeso());
			 ccMorto.setDayHospital(cc.isDayHospital());
			 ccMorto.setEventoApertura((LookupEventoAperturaCc)persistence.find(LookupEventoAperturaCc.class, 4));
			 ccMorto.setCcMorto(true);
			 
			 session.setAttribute("idCc", ccMorto.getId());
			 req.setAttribute("cc", ccMorto);
		
		if (cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) {
			
			cc.setModified( new Date() );
			cc.setModifiedBy( utente );
			persistence.update( cc );
			persistence.insert(accettazioneNuova);
			persistence.insert(ccMorto);
			if(cc.getCcPostTrasferimento())
				 persistence.update(trasferimento);
			if(!cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe())
				setMessaggio( "Registrazione DECESSO inserita con successo in BDR. Registrazione numero " + numeroRegistrazione);
			else
				setMessaggio("Inserimento decesso effettuato correttamente");
				redirectTo("vam.cc.decessi.Detail.us");
						
		}
		
		else {
			
			setErrore( "Si è verificato un errore durante la registrazione in BDR" );
			redirectTo("vam.cc.Detail.us");
		}		
				
	}	
	
	@SuppressWarnings("unchecked")
	private void setupProgressivo(Accettazione accettazione) 
	{
		int nextProgressivo = 1;
		List<Integer> result = persistence.createCriteria(Accettazione.class).add(Restrictions.ge("data", DateUtils.annoCorrente(accettazione.getData()))).add(
						Restrictions.lt("data", DateUtils.annoSuccessivo(accettazione.getData()))).setProjection(Projections.max("progressivo")).createCriteria(
						"enteredBy").add(Restrictions.eq("clinica", utente.getClinica())).list();

		if (result.size() > 0 && result.get(0) != null) 
		{
			nextProgressivo = (result.get(0) + 1);
		}

		accettazione.setProgressivo(nextProgressivo);
	}
}


