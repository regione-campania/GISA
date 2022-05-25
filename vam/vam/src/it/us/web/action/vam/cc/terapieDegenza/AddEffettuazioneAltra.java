/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.cc.terapieDegenza;

import it.us.web.action.GenericAction;
import it.us.web.action.vam.magazzino.farmaci.Detail;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.bean.vam.MagazzinoFarmaci;
import it.us.web.bean.vam.ScaricoFarmaco;
import it.us.web.bean.vam.TerapiaAssegnata;
import it.us.web.bean.vam.TerapiaEffettuata;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import java.util.Date;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddEffettuazioneAltra extends GenericAction 
{

	@Override
	public void can() throws Exception
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
		can( gui, "w" );
		
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("terapia");
	}
	
	@Override
	public void execute() throws Exception
	{		
		
		final Logger logger = LoggerFactory.getLogger(AddEffettuazioneAltra.class);


		TerapiaAssegnata ta = (TerapiaAssegnata) persistence.find(TerapiaAssegnata.class, interoFromRequest("idTerapiaAssegnata"));
				
		
		/* ***************************************** */
		/* ***************************************** */
		/* 			GESTIONE EFFETTUAZIONE TERAPIA	*/
		/* ***************************************** */
		/* ***************************************** */
		
		TerapiaEffettuata te = new TerapiaEffettuata();
					
		te.setEntered(new Date());		
		te.setEnteredBy(utente);
		te.setModified(new Date());		
		te.setModifiedBy(utente);
		te.setTerapiaAssegnata(ta);
		te.setData(new Date());
		
		ta.setDataUltimaEffettuazione(new Date());
		ta.setModifiable(false);
		ta.setErasable(false);
		
		
		
		validaBean( te , new List()  );
		
		try {
			persistence.insert(te);
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(te);
			}
			persistence.update(ta);
			if(cc.getDataChiusura()!=null)
			{
				beanModificati.add(ta);
			}
			cc.setModified( new Date() );
			cc.setModifiedBy( utente );
			persistence.update( cc );
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
			logger.error("Cannot save Assegnazione" + e.getMessage());
			throw e;		
		}
		
		setMessaggio("Effettuazione registrata sul diario");
		redirectTo("vam.cc.terapieDegenza.DetailAltra.us?idTerapiaDegenza="+ta.getTerapiaDegenza().getId());
	
	}
	
	@Override
	public String getDescrizione()
	{
		return "Aggiunta Effettuazione Altra Terapia";
	}

}




