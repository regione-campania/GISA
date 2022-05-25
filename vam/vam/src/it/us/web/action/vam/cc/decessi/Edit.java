/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.decessi;

import java.util.Date;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.remoteBean.RegistrazioniFelinaResponse;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.lookup.LookupCMI;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Edit extends GenericAction {

	
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
		final Logger logger = LoggerFactory.getLogger(Edit.class);
		
		/* Gestione Causa Morte*/
		int idCM = interoFromRequest("causaMorteIniziale");
			
		boolean dataMortePresunta = !booleanoFromRequest("dataMorteCerta");
				
		//*===================================================*/
		/*===================================================*/
		/*  AGGIORNAMENTO IN BDR DEL DECESSO*/
		/*===================================================*/
		/*===================================================*/
		int numeroRegistrazione = 0;
		String erroriSinantropi = null;
		
		//Se l'animale è morto senza mc devo aggiornare solo in locale, altrimenti posso aggiornare la bdr
		if(cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe())
		{
			Animale animale = cc.getAccettazione().getAnimale();
			animale.setDataMorte(dataFromRequest("dataMorte"));
			animale.setDataMortePresunta(!booleanoFromRequest("dataMorteCerta"));
			animale.setCausaMorte((LookupCMI)persistence.find(LookupCMI.class, interoFromRequest("causaMorteIniziale")));
			persistence.update( animale );
		}
		else
		{

			if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.CANE) 
			{
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
			else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.GATTO) 
			{
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

			else if (cc.getAccettazione().getAnimale().getLookupSpecie().getId() == Specie.SINANTROPO) 
			{
				erroriSinantropi = it.us.web.action.sinantropi.decessi.Add.addDecesso(logger, SinantropoUtil.getSinantropoByNumero(persistence, cc.getAccettazione().getAnimale().getIdentificativo()), utente, persistence, req, dataFromRequest("dataMorte"),idCM, dataMortePresunta );
			}
		}
		
		if (erroriSinantropi==null || cc.getAccettazione().getAnimale().getDecedutoNonAnagrafe()) {

			cc.setModified( new Date() );
			cc.setModifiedBy( utente );
			persistence.update( cc );
			setMessaggio( "Modifica decesso effettuata correttamente");			
			redirectTo("vam.cc.decessi.Detail.us");
						
		}
		else 
		{
			setErrore( "Si è verificato un errore durante la registrazione in BDR" );
			redirectTo("vam.cc.decessi.Detail.us");
		}		
				
	}	
}
