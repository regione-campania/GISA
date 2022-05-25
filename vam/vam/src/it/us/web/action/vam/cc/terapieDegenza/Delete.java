/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.terapieDegenza;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.bean.vam.TerapiaAssegnata;
import it.us.web.bean.vam.TerapiaDegenza;
import it.us.web.bean.vam.TerapiaEffettuata;
import it.us.web.bean.vam.lookup.LookupFarmaci;
import it.us.web.bean.vam.lookup.LookupModalitaSomministrazioneFarmaci;
import it.us.web.bean.vam.lookup.LookupTipiFarmaco;
import it.us.web.bean.vam.lookup.LookupVieSomministrazioneFarmaci;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class Delete extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "DETAIL", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("terapia");
	}

	public void execute() throws Exception
	{
		final Logger logger = LoggerFactory.getLogger(Add.class);
		
		int id = interoFromRequest("idTerapiaDegenza");
		
		//Recupero Bean Terapia Degenza
		TerapiaDegenza td = (TerapiaDegenza) persistence.find (TerapiaDegenza.class, id);
		
		td.setModified(new Date());
		td.setModifiedBy(utente);
		td.setTrashedDate(new Date());
		
		
		try
		{
			Iterator iter = td.getTarapiaAssegnatas().iterator();
			while(iter.hasNext())
			{
				TerapiaAssegnata terapiaAss = (TerapiaAssegnata)iter.next();
				terapiaAss.setModified(new Date());
				terapiaAss.setModifiedBy(utente);
				terapiaAss.setTrashedDate(new Date());
				Iterator iter2 = terapiaAss.getTarapiaEffettuates().iterator();
				while(iter2.hasNext())
				{
					TerapiaEffettuata terapiaEff = (TerapiaEffettuata)iter2.next();
					terapiaEff.setModified(new Date());
					terapiaEff.setModifiedBy(utente);
					terapiaEff.setTrashedDate(new Date());
					persistence.update(terapiaEff);
					beanModificati.add(terapiaEff);
				}
				persistence.update(terapiaAss);
				beanModificati.add(terapiaAss);
			}
		
			persistence.update(td);
			beanModificati.add(td);
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
			logger.error("Cannot delete Terapia" + e.getMessage());
			throw e;		
		}
			
		//Recupero di tutte le terapie
		ArrayList<TerapiaDegenza> tdList = (ArrayList<TerapiaDegenza>) persistence.getNamedQuery("GetTerapieDegenzaByCC").setInteger("idCartellaClinica", idCc).list();
	
		//Recupero terapie separate per tipo
		ArrayList<TerapiaDegenza> tdListFarm  = (ArrayList<TerapiaDegenza>) persistence.getNamedQuery("GetTerapieDegenzaByCCFarmacologica").setInteger("idCartellaClinica", idCc).list();
		ArrayList<TerapiaDegenza> tdListAltra = (ArrayList<TerapiaDegenza>) persistence.getNamedQuery("GetTerapieDegenzaByCCAltra").setInteger("idCartellaClinica", idCc).list();
		
		//Controllo esistenza farmaci in magazzino
		ArrayList<MagazzinoFarmaci> mfList 
		= (ArrayList<MagazzinoFarmaci>) persistence.getNamedQuery("GetFarmaciByClinica").setInteger("idClinica", utente.getClinica().getId()).list();
		
		boolean esisteMagazzinoFarmaci = mfList!=null && !mfList.isEmpty(); 
		
		
		
		req.setAttribute("td", td);		
		req.setAttribute("esisteMagazzinoFarmaci", esisteMagazzinoFarmaci);
		req.setAttribute("tdList", tdList);
		req.setAttribute("numeroTerapieFarmacologiche", tdListFarm.size());
		req.setAttribute("numeroAltreTerapie", 			tdListAltra.size());
		
		setMessaggio("Terapia in degenza eliminata correttamente");
		gotoPage("/jsp/vam/cc/terapieDegenza/list.jsp");
	}
	
	@Override
	public String getDescrizione()
	{
		return "Cancellazione Terapia";
	}
}

