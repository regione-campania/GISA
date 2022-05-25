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
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
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

public class AddDecessoSenzaComunicazioneBDU extends GenericAction  implements Specie
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
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		setConnectionBdu(connection);
		
		Context ctxVam = new InitialContext();
		javax.sql.DataSource dsVam = (javax.sql.DataSource)ctxVam.lookup("java:comp/env/jdbc/vamM");
		Connection connectionVam = dsVam.getConnection();
		aggiornaConnessioneApertaSessione(req);
		setConnection(connectionVam);
		
		final Logger logger = LoggerFactory.getLogger(Add.class);

		int idCM;
		boolean dataMortePresunta;
		Accettazione accettazioneNuova = null;
		//Trasferimento trasferimento = null;
					
		BeanUtils.populate(cc, req.getParameterMap());
				
		LookupDestinazioneAnimale lda = (LookupDestinazioneAnimale) persistence.find(LookupDestinazioneAnimale.class, interoFromRequest("destinazioneAninale"));
			
		if(lda.getId()==2)
		{
			BGuiView gui = GuiViewDAO.getView( "BDR", "ADD", "MAIN" );
			can( gui, "w" );
		}
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
		
			idCM = interoFromRequest("causaMorteIniziale");
			dataMortePresunta = !booleanoFromRequest("dataMorteCerta");
			
			//*===================================================*/
			/*===================================================*/
			/*  AGGIORNAMENTO IN BDR DEL DECESSO*/
			/*===================================================*/
			/*===================================================*/
			
			if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() < Specie.SINANTROPO  && !cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) {
					
				LookupOperazioniAccettazione decesso = (LookupOperazioniAccettazione) persistence.find( LookupOperazioniAccettazione.class, IdOperazioniBdr.decesso );
				
				AttivitaBdr abdr = new AttivitaBdr();
				abdr.setCc					( cc );
				abdr.setEntered				( new Date() );
				abdr.setEnteredBy			( utente.getId() );
				abdr.setModified			( abdr.getEntered() );
				abdr.setModifiedBy			( utente.getId() );
				abdr.setOperazioneBdr		( decesso );
			

				
				
				int idTipoRegBdr = RegistrazioniUtil.getIdTipoBdr (cc.getAccettazione().getAnimale(), cc.getAccettazione(), decesso, connectionVam, utente, connection,req);
				abdr.setIdRegistrazioneBdr(CaninaRemoteUtil.getUltimaRegistrazione(cc.getAccettazione().getAnimale().getIdentificativo(), idTipoRegBdr, connection,req));
				
				persistence.insert( abdr );
			}
				
			
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
				 accettazioneNuova.setOperazioniRichieste( (Set<LookupOperazioniAccettazione>) objectList( LookupOperazioniAccettazione.class, "op_" ) );
				 accettazioneNuova.setEnteredBy(utente);
				 accettazioneNuova.setModifiedBy(utente);
				 accettazioneNuova.setRandagio(cc.getAccettazione().getRandagio());
				 accettazioneNuova.setSterilizzato(cc.getAccettazione().getSterilizzato());
				 accettazioneNuova.setData( dataFromRequest("dataChiusura") );
				 accettazioneNuova.setEntered(new Date());
				 accettazioneNuova.setModified(new Date());
				 accettazioneNuova.setRichiedenteAltro("Clinica: " + cc.getEnteredBy().getClinica().getNome() + "; Cc: " + cc.getNumero());
				 accettazioneNuova.setLookupTipiRichiedente((LookupTipiRichiedente)persistence.find(LookupTipiRichiedente.class, TipiRichiedente.ALTRO));
				 accettazioneNuova.setCcVivo(cc);
				 setupProgressivo(accettazioneNuova);
		
		validaBean( cc , new it.us.web.action.vam.cc.dimissioni.ToEdit()  );
		if(accettazioneNuova!=null)
			validaBean( accettazioneNuova , new it.us.web.action.vam.cc.dimissioni.AddDecessoSenzaComunicazioneBDU()  );
			
		try {
			persistence.insert(accettazioneNuova);
			//if(cc.getCcPostTrasferimento())
			//persistence.update(trasferimento);
			persistence.update(cc);	
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

			
	}

	@SuppressWarnings("unchecked")
	private void setupProgressivo(Accettazione accettazione) 
	{
		int nextProgressivo = 1;
		ArrayList<Integer> result = (ArrayList<Integer>)persistence.createCriteria(Accettazione.class).add(Restrictions.ge("data", DateUtils.annoCorrente(accettazione.getData()))).add(
						Restrictions.lt("data", DateUtils.annoSuccessivo(accettazione.getData()))).setProjection(Projections.max("progressivo")).createCriteria(
						"enteredBy").add(Restrictions.eq("clinica", utente.getClinica())).list();

		if (result.size() > 0 && result.get(0) != null) 
		{
			nextProgressivo = (result.get(0) + 1);
		}

		accettazione.setProgressivo(nextProgressivo);
	}
	
	
}




