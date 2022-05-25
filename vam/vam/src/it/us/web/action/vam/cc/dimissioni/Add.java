/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.dimissioni;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.cc.diagnosticaImmagini.tac.ToAdd;
import it.us.web.bean.BGuiView;
import it.us.web.bean.remoteBean.Registrazioni;
import it.us.web.bean.sinantropi.Catture;
import it.us.web.bean.sinantropi.Reimmissioni;
import it.us.web.bean.vam.Accettazione;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.AttivitaBdr;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.CartellaClinicaNoH;
import it.us.web.bean.vam.FascicoloSanitario;
import it.us.web.bean.vam.Trasferimento;
import it.us.web.bean.vam.lookup.LookupComuni;
import it.us.web.bean.vam.lookup.LookupDestinazioneAnimale;
import it.us.web.bean.vam.lookup.LookupOperazioniAccettazione;
import it.us.web.bean.vam.lookup.LookupTipiRichiedente;
import it.us.web.constants.IdOperazioniBdr;
import it.us.web.constants.Specie;
import it.us.web.constants.TipiRichiedente;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.SinantropoDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.dao.lookup.LookupComuniDAO;
import it.us.web.dao.lookup.LookupDestinazioneAnimaleDAO;
import it.us.web.dao.lookup.LookupOperazioniAccettazioneDAO;
import it.us.web.dao.lookup.LookupTipiRichiedenteDAO;
import it.us.web.dao.vam.AccettazioneDAO;
import it.us.web.dao.vam.CartellaClinicaDAONoH;
import it.us.web.dao.vam.FascicoloSanitarioDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.AnimaliUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;
import it.us.web.util.vam.RegistrazioniUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Add extends GenericAction  implements Specie
{

	@Override
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
		can( gui, "w" );
		canCc(cc);
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("dimissioni");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
	@Override
	public void execute() throws Exception
	{		
		Context ctxBdu = new InitialContext();
		javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduS");
		Connection connectionBdu = dsBdu.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connectionBdu);
		final Logger logger = LoggerFactory.getLogger(Add.class);

		int idCM;
		boolean dataMortePresunta;
		Accettazione accettazioneNuova = null;
		//Trasferimento trasferimento = null;
		FascicoloSanitario fascicolo = null;
				
		Context ctxVAM = new InitialContext();
		javax.sql.DataSource dsVAM = (javax.sql.DataSource) ctxVAM.lookup("java:comp/env/jdbc/vamM");
		connection = dsVAM.getConnection();
		//if(connection.getAutoCommit())
			//connection.setAutoCommit(false);
		
		BeanUtils.populate(cc, req.getParameterMap());
		
		CartellaClinicaNoH ccNoH = CartellaClinicaDAONoH.getCc(connection, cc.getId());
		
		BeanUtils.populate(ccNoH, req.getParameterMap());
		
		//LookupDestinazioneAnimale lda = (LookupDestinazioneAnimale) persistence.find(LookupDestinazioneAnimale.class, interoFromRequest("destinazioneAninale"));
		LookupDestinazioneAnimale lda = LookupDestinazioneAnimaleDAO.getDestinazioneAnimale(connection, interoFromRequest("destinazioneAninale"));
		
		if(lda.getId()==2)
		{
			BGuiView gui = GuiViewDAO.getView( "BDR", "ADD", "MAIN" );
			can( gui, "w" );
		}
		ccNoH.setDestinazioneAnimale(lda);
		ccNoH.setModified(new Date());		
		ccNoH.setModifiedBy(utente);
		if(stringaFromRequest("intraFuoriAsl")!=null)
			ccNoH.setAdozioneFuoriAsl(true);
		else
			ccNoH.setAdozioneFuoriAsl(false);
		if(stringaFromRequest("versoAssocCanili")!=null)
			ccNoH.setAdozioneVersoAssocCanili(true);
		else
			ccNoH.setAdozioneVersoAssocCanili(false);
		//setta data ed utente che effettua la dimissione
		ccNoH.setDimissioniEntered(new Date());
		ccNoH.setDimissioniEnteredBy(utente);
		
		cc.setDestinazioneAnimale(lda);
		cc.setModified(new Date());		
		cc.setModifiedBy(utente);
		if(stringaFromRequest("intraFuoriAsl")!=null)
			cc.setAdozioneFuoriAsl(true);
		else
			cc.setAdozioneFuoriAsl(false);
		if(stringaFromRequest("versoAssocCanili")!=null)
			cc.setAdozioneVersoAssocCanili(true);
		else
			cc.setAdozioneVersoAssocCanili(false);
		//setta data ed utente che effettua la dimissione
		cc.setDimissioniEntered(new Date());
		cc.setDimissioniEnteredBy(utente);

		//Se non è stato dimesso per morte o se è stato dimesso per morte ma la necroscopia successiva non si deve fare
		//chiudo il fascicolo sanitario
		if(lda.getId()!=2)
		{
			if((ccNoH.getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.SINANTROPO && lda.getId()==5) ||
					   (ccNoH.getAccettazione().getAnimale().getLookupSpecie().getId()!=Specie.SINANTROPO && lda.getId()==1 && !AnimaliUtil.getInserireRitornoProprietario(ccNoH.getAccettazione(),req)))
					{
						
					}
					else
					{
						LookupOperazioniAccettazione op = null;
						if(lda.getId()==3)//Adozione
							op = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.adozione);
						if(lda.getId()==5)//Re-immissione
							op = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.reimmissione);
						if(lda.getId()==8)//Ritorno asl origine
							op = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoAslOrigine);
						if(lda.getId()==4)//Trasf a canile
							op = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.trasfCanile);
						if(lda.getId()==1)//Ritorno a prop.
							op = (LookupOperazioniAccettazione)persistence.find(LookupOperazioniAccettazione.class, IdOperazioniBdr.ritornoProprietario);
						
						
						AttivitaBdr abdr = new AttivitaBdr();
						abdr.setCc					( cc );
						abdr.setEntered				( new Date() );
						abdr.setEnteredBy			( utente.getId() );
						abdr.setModified			( abdr.getEntered() );
						abdr.setModifiedBy			( utente.getId() );
						abdr.setOperazioneBdr		( op );
						persistence.insert( abdr );
					}
			
			
			
			
			
			fascicolo = ccNoH.getFascicoloSanitario();
			if(fascicolo!=null)
				FascicoloSanitarioDAO.chiudi(ccNoH, connection);
		}	
		else
		{
			idCM = interoFromRequest("causaMorteIniziale");
			dataMortePresunta = !booleanoFromRequest("dataMorteCerta");
			
			//*===================================================*/
			/*===================================================*/
			/*  AGGIORNAMENTO IN BDR DEL DECESSO*/
			/*===================================================*/
			/*===================================================*/
			
			if (ccNoH.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.CANE && !ccNoH.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) 
			{
					
				//LookupOperazioniAccettazione decesso = (LookupOperazioniAccettazione) persistence.find( LookupOperazioniAccettazione.class, IdOperazioniBdr.decesso );
				LookupOperazioniAccettazione decesso = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.decesso, connection);
				
				AttivitaBdr abdr = new AttivitaBdr();
				abdr.setCc					( cc );
				abdr.setEntered				( new Date() );
				abdr.setEnteredBy			( utente.getId() );
				abdr.setModified			( abdr.getEntered() );
				abdr.setModifiedBy			( utente.getId() );
				abdr.setOperazioneBdr		( decesso );
				
				String provincia = stringaFromRequest("provincia");
				String comune = null; 
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
						interoFromRequest("causaMorteIniziale"), 
						stringaFromRequest("dataMorte"), 
						dataMortePresunta, 
						comune,
						stringaFromRequest("indirizzo"),
						stringaFromRequest("note"),
						utente,req);
				
				
				int idTipoRegBdr = RegistrazioniUtil.getIdTipoBdr (cc.getAccettazione().getAnimale(), cc.getAccettazione(), decesso, connection, utente, connectionBdu,req);
				abdr.setIdRegistrazioneBdr(CaninaRemoteUtil.getUltimaRegistrazione(ccNoH.getAccettazione().getAnimale().getIdentificativo(), idTipoRegBdr, connectionBdu,req));
				
				persistence.insert( abdr );
			}
			else if (ccNoH.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.GATTO && !ccNoH.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) 
			{
				//LookupOperazioniAccettazione decesso = (LookupOperazioniAccettazione) persistence.find( LookupOperazioniAccettazione.class, IdOperazioniBdr.decesso );
				LookupOperazioniAccettazione decesso = LookupOperazioniAccettazioneDAO.getOperazioneAccettazione(IdOperazioniBdr.decesso, connection);
				
				AttivitaBdr abdr = new AttivitaBdr();
				abdr.setCc					( cc );
				abdr.setEntered				( new Date() );
				abdr.setEnteredBy			( utente.getId() );
				abdr.setModified			( abdr.getEntered() );
				abdr.setModifiedBy			( utente.getId() );
				abdr.setOperazioneBdr		( decesso );
				
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
						interoFromRequest("causaMorteIniziale"), 
						stringaFromRequest("dataMorte"), 
						dataMortePresunta,
						comune,
						stringaFromRequest("indirizzo"),
						stringaFromRequest("note"),
						utente,req);
				
				int idTipoRegBdr = RegistrazioniUtil.getIdTipoBdr (cc.getAccettazione().getAnimale(), cc.getAccettazione(), decesso, connection, utente, connectionBdu,req);
				abdr.setIdRegistrazioneBdr(CaninaRemoteUtil.getUltimaRegistrazione(ccNoH.getAccettazione().getAnimale().getIdentificativo(), idTipoRegBdr, connectionBdu,req));
				
				persistence.insert( abdr );
			}
			
			else if (ccNoH.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.SINANTROPO && !ccNoH.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) {
									
				it.us.web.action.sinantropi.decessi.Add.addDecesso(logger, SinantropoUtil.getSinantropoByNumero(persistence, ccNoH.getAccettazione().getAnimale().getIdentificativo()), utente, persistence, req, dataFromRequest("dataMorte"),idCM, dataMortePresunta );
			}
			
				 accettazioneNuova = new Accettazione();
				 accettazioneNuova.setAnimale(cc.getAccettazione().getAnimale());
				 accettazioneNuova.setAslAnimale(ccNoH.getAccettazione().getAslAnimale());
				 accettazioneNuova.setProprietarioCap(ccNoH.getAccettazione().getProprietarioCap());
				 accettazioneNuova.setProprietarioCodiceFiscale(ccNoH.getAccettazione().getProprietarioCodiceFiscale());
				 accettazioneNuova.setProprietarioCognome(ccNoH.getAccettazione().getProprietarioCognome());
				 accettazioneNuova.setProprietarioTipo(ccNoH.getAccettazione().getProprietarioTipo());
				 accettazioneNuova.setProprietarioComune(ccNoH.getAccettazione().getProprietarioComune());
				 accettazioneNuova.setProprietarioDocumento(ccNoH.getAccettazione().getProprietarioDocumento());
				 accettazioneNuova.setProprietarioIndirizzo(ccNoH.getAccettazione().getProprietarioIndirizzo());
				 accettazioneNuova.setProprietarioNome(ccNoH.getAccettazione().getProprietarioNome());
				 accettazioneNuova.setProprietarioProvincia(ccNoH.getAccettazione().getProprietarioProvincia());
				 accettazioneNuova.setProprietarioTelefono(ccNoH.getAccettazione().getProprietarioTelefono());
				 accettazioneNuova.setOperazioniRichieste( (Set<LookupOperazioniAccettazione>) objectList( LookupOperazioniAccettazione.class, "op_" ) );
				 accettazioneNuova.setEnteredBy(utente);
				 accettazioneNuova.setModifiedBy(utente);
				 accettazioneNuova.setRandagio(ccNoH.getAccettazione().getRandagio());
				 accettazioneNuova.setSterilizzato(ccNoH.getAccettazione().getSterilizzato());
				 accettazioneNuova.setData( dataFromRequest("dataChiusura") );
				 accettazioneNuova.setEntered(new Date());
				 accettazioneNuova.setModified(new Date());
				 accettazioneNuova.setRichiedenteAltro("Clinica: " + ccNoH.getEnteredBy().getClinica().getNome() + "; Cc: " + ccNoH.getNumero());
				 //accettazioneNuova.setLookupTipiRichiedente((LookupTipiRichiedente)persistence.find(LookupTipiRichiedente.class, TipiRichiedente.ALTRO));
				 accettazioneNuova.setLookupTipiRichiedente(LookupTipiRichiedenteDAO.getTipoRichiedente(TipiRichiedente.ALTRO,connection));
				 accettazioneNuova.setCcVivo(cc);
				 setupProgressivo(accettazioneNuova);
		}
		
		if(lda.getId()==5 && ccNoH.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.SINANTROPO)
		{
			Catture c = SinantropoUtil.getLastCattura(SinantropoUtil.getSinantropoByNumero(persistence, ccNoH.getAccettazione().getAnimale().getIdentificativo()));
			
			/*GESTIONE RE-IMMISSIONE*/
			Reimmissioni r = new Reimmissioni();
			BeanUtils.populate(r, req.getParameterMap());	
			r.setEntered(ccNoH.getDataChiusura());		
			r.setEnteredBy(utente);
			r.setModified(ccNoH.getDataChiusura());		
			r.setModifiedBy(utente);
			r.setDataReimmissione(ccNoH.getDataChiusura());
			
			
			String provinciaReimmissione = stringaFromRequest("provinciaReimmissione");
			int comuneReimmissione = 0; 
			
			if (provinciaReimmissione.equals("BN"))		
				comuneReimmissione = interoFromRequest("comuneReimmissioneBN");
			else if (provinciaReimmissione.equals("NA"))	
				comuneReimmissione = interoFromRequest("comuneReimmissioneNA");
			else if (provinciaReimmissione.equals("SA"))	
				comuneReimmissione = interoFromRequest("comuneReimmissioneSA");
			else if (provinciaReimmissione.equals("CE"))	
				comuneReimmissione = interoFromRequest("comuneReimmissioneCE");
			else if (provinciaReimmissione.equals("AV"))	
				comuneReimmissione = interoFromRequest("comuneReimmissioneAV");
					
			
			if(comuneReimmissione>0)
			{
				//LookupComuni cReimmissione = (LookupComuni) persistence.find (LookupComuni.class, comuneReimmissione);
				LookupComuni cReimmissione = LookupComuniDAO.getComune(comuneReimmissione, connection);				
				r.setComuneReimmissione(cReimmissione);
			}
			
			r.setCatture(c);	

			validaBean( r, new ToAdd() );			
			
			//Inserimento re-immissione
			if (!stringaFromRequest("provinciaReimmissione").equals("X"))
			{
				c.getSinantropo().setLastOperation("RILASCIO");
				persistence.update(r.getComuneReimmissione());
				persistence.insert(r);
				c.setReimmissioni(r);
				persistence.update(c);
										
			}						
		}
		
		validaBean( cc , new it.us.web.action.vam.cc.dimissioni.ToEdit()  );
		if(accettazioneNuova!=null)
			validaBean( accettazioneNuova , new it.us.web.action.vam.cc.dimissioni.ToEdit()  );
			
		try {
			if(lda.getId()!=2 && fascicolo!=null)
				FascicoloSanitarioDAO.chiudi(ccNoH, connection);
			else
			{
					persistence.insert(accettazioneNuova);
					//if(cc.getCcPostTrasferimento())
						 //persistence.update(trasferimento);
			}
			persistence.update(cc);
			//CartellaClinicaDAONoH.chiudi(ccNoH, connection);
			
			//if(!connection.getAutoCommit())
				//connection.commit();
			//connection.setAutoCommit(true);
			persistence.commit();
		}
		catch (RuntimeException e)
		{
			try
			{		
				persistence.rollBack();				
			}
			catch (HibernateException e1)
			{				
				logger.error("Error during Rollback transaction" + e1.getMessage());
			}
			logger.error("Cannot update Dimissioni" + e.getMessage());
			throw e;		
		}
		setMessaggio("Dimissione registrata con successo");
			
		
		if(lda.getId()==2 )
		{
			redirectTo( "vam.accettazione.Detail.us?id="+accettazioneNuova.getId() );
		}
		else if(lda.getId()==2 )
		{
			redirectTo("vam.cc.dimissioni.ToAdd.us?");
		}
		else
		{
			if((ccNoH.getAccettazione().getAnimale().getLookupSpecie().getId()==Specie.SINANTROPO && lda.getId()==5) ||
			   (ccNoH.getAccettazione().getAnimale().getLookupSpecie().getId()!=Specie.SINANTROPO && lda.getId()==1 && !AnimaliUtil.getInserireRitornoProprietario(ccNoH.getAccettazione(),req)))
			{
				redirectTo("vam.cc.dimissioni.Detail.us?");
			}
			else
			{
				setMessaggio("Questa destinazione selezionata necessita anche dell'aggiornamento in BDR");
				redirectTo("vam.cc.dimissioni.ToRegistrazioniInterattive.us?");
			}
		}
					
			
	}

	@SuppressWarnings("unchecked")
	private void setupProgressivo(Accettazione accettazione)
	{
		int nextProgressivo = 1;
		nextProgressivo =  AccettazioneDAO.getNextProgressivo(DateUtils.annoCorrente( accettazione.getData() )  , 
				                           DateUtils.annoSuccessivo( accettazione.getData()) ,
				                           utente.getClinica().getId(),
				                           connection );
		
		accettazione.setProgressivo( nextProgressivo );
	}
	
	
}



