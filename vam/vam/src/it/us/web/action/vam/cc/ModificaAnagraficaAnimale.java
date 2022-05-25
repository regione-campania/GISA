/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.ServicesStatus;
import it.us.web.bean.remoteBean.Cane;
import it.us.web.bean.remoteBean.Gatto;
import it.us.web.bean.remoteBean.ProprietarioCane;
import it.us.web.bean.remoteBean.ProprietarioGatto;
import it.us.web.bean.sinantropi.Sinantropo;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.StoricoAnagraficaAnimale;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.sinantropi.SinantropoUtil;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModificaAnagraficaAnimale extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "EDIT", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("modificaAnagraficaAnimale");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(ModificaAnagraficaAnimale.class);
				
		Animale animale = cc.getAccettazione().getAnimale();
		
		//Recupero Bean Storico per verifica primo inserimento
		List<StoricoAnagraficaAnimale> storicoAnagrafica = persistence.createCriteria( StoricoAnagraficaAnimale.class ).add( Restrictions.eq( "animale", animale ) ).list();
		
		StoricoAnagraficaAnimale tmp1=null;
		StoricoAnagraficaAnimale tmp2=new StoricoAnagraficaAnimale();
		
		String specie=null;
		if(animale.getLookupSpecie().getId()==Specie.CANE)
			specie="Cane";
		else if(animale.getLookupSpecie().getId()==Specie.GATTO)
			specie="Gatto";
		else
			specie="Sinantropo";
		
		
		
		//Modifica dati animale in BDR
		//Questa modifica, se fatta da utenti IZSM ed Università, non la comunichiamo alla Bdr in quanto questi utenti non vi hanno accesso: 
		//la modifica rimane in Vam(vedi Verbale del 27/02/2013)
		if(!animale.getDecedutoNonAnagrafe() && !utente.getRuolo().equals("IZSM") && !utente.getRuolo().equals("Universita"))
		{
			if(animale.getLookupSpecie().getId()==Specie.CANE)
			{
				CaninaRemoteUtil.eseguiModificaAnagrafica(
						animale, 
						interoFromRequest("razza"), 
						((interoFromRequest( "mantello"+specie )>=0)? interoFromRequest( "mantello"+specie):(null)), 
						stringaFromRequest("sesso"), 
						((interoFromRequest( "idTaglia" )>0)?((LookupTaglie) persistence.find( LookupTaglie.class, interoFromRequest( "idTaglia" ))):(null)),
						utente,req);
			}
			else if(animale.getLookupSpecie().getId()==Specie.GATTO)
			{
				FelinaRemoteUtil.eseguiModificaAnagrafica(
						  animale, 
						  interoFromRequest("razza"), 
						  ((interoFromRequest( "mantello"+specie )>=0)? interoFromRequest( "mantello"+specie):(null)), 
						  stringaFromRequest("sesso"), 
						  ((interoFromRequest( "idTaglia" )>0)?((LookupTaglie) persistence.find( LookupTaglie.class, interoFromRequest( "idTaglia" ))):(null)),
						  utente,req);
			}
			else if(animale.getLookupSpecie().getId()==Specie.SINANTROPO)
			{
				Sinantropo sinantropo = SinantropoUtil.getSinantropoByNumero(persistence, animale.getIdentificativo()) ;
				sinantropo.setSesso(stringaFromRequest("sesso"));
			/*	sinantropo.setRazza(stringaFromRequest("razzaSinantropo"));
				if(interoFromRequest("idTaglia")>0)
					sinantropo.setTaglia((LookupTaglie) persistence.find( LookupTaglie.class, interoFromRequest( "idTaglia" )));
				else
					sinantropo.setTaglia( null );
				sinantropo.setMantello(stringaFromRequest("mantello"+specie));*/
				persistence.update(sinantropo);
			}
		}
		
		//verifica primo inserimento in storico
		if (storicoAnagrafica.isEmpty()){
			
			tmp1=new StoricoAnagraficaAnimale();
			
			//inserimento primo record
			tmp1.setAnimale(animale);
			tmp1.setMantello(animale.getMantello());
			tmp1.setRazza(animale.getRazza());
			tmp1.setMantelloSinantropo(animale.getMantelloSinantropo());
			tmp1.setRazzaSinantropo(animale.getRazzaSinantropo());
			tmp1.setSesso(animale.getSesso());
			tmp1.setTaglia(animale.getTaglia());
			tmp1.setEntered(new Date());
			tmp1.setEnteredBy(utente);
			tmp1.setModified(new Date());
			tmp1.setModifiedBy(utente);
			tmp1.setTrashedDate(null);
			persistence.insert(tmp1);
			
		}
		
		//Modifica dati animale in vam
		animale.setSesso(stringaFromRequest("sesso"));
		//animale.setDataNascita(dataFromRequest("dataNascita"));
		int idTaglia = interoFromRequest( "idTaglia" );
		if(animale.getLookupSpecie().getId()==Specie.CANE && idTaglia>0)
			animale.setTaglia(idTaglia);

		//Se è un Cane o un Gatto la razza ed il mantello vanno presi dalla combo, altrimenti è già stata settata col populate 
		//tramite attributi razzaSinantropo e mantelloSinantropo
		if(animale.getLookupSpecie().getId()==Specie.CANE || animale.getLookupSpecie().getId()==Specie.GATTO)
		{
			int idRazza = interoFromRequest("razza");
			if (idRazza >=0)
				animale.setRazza(idRazza);
			
			int idMantello=interoFromRequest("mantello"+specie);
			//if(idMantello>=0)
				animale.setMantello(idMantello);
		}
	/*	else
		{
			animale.setMantelloSinantropo(stringaFromRequest("mantelloSinantropo"));
			animale.setRazzaSinantropo(stringaFromRequest("razzaSinantropo"));
		} */	
		
		
		//inserimento nella tabella di storico delle modifiche appena effettuate
		
		tmp2.setAnimale(animale);
		tmp2.setMantello(animale.getMantello());
		tmp2.setRazza(animale.getRazza());
		tmp2.setMantelloSinantropo(animale.getMantelloSinantropo());
		tmp2.setRazzaSinantropo(animale.getRazzaSinantropo());
		tmp2.setSesso(animale.getSesso());
		tmp2.setTaglia(animale.getTaglia());
		tmp2.setEntered(new Date());
		tmp2.setEnteredBy(utente);
		tmp2.setModified(new Date());
		tmp2.setModifiedBy(utente);
		tmp2.setTrashedDate(null);
		persistence.insert(tmp2);
		
		try 
		{
			persistence.update(animale);
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
			logger.error("Non è stato possibile aggiornare l'anagrafica dell'animale: " + e.getMessage());
			throw e;		
		}
		
		setMessaggio("Anagrafica animale aggiornata con successo");
		redirectTo( "vam.cc.Detail.us" );
	}
}

