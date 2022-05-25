/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.agenda;

import java.util.Date;
import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.BookingClinica;
import it.us.web.bean.vam.StrutturaClinica;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;
import it.us.web.util.DetectBrowser;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddBooking extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "ADD", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("agenda");
	}

	public void execute() throws Exception
	{
		
		final Logger logger = LoggerFactory.getLogger(AddBooking.class);		
		
		//ID Prenotazione	
		int idStrutturaClinica = Integer.parseInt(session.getAttribute("idStrutturaClinica").toString());
						
		StrutturaClinica sc = (StrutturaClinica) persistence.find(StrutturaClinica.class, idStrutturaClinica);
			
		
		BookingClinica bc = new BookingClinica();
		BeanUtils.populate(bc, req.getParameterMap());
				
		bc.setDa(DateUtils.calendarConvert(stringaFromRequest("start")));
		bc.setA(DateUtils.calendarConvert(stringaFromRequest("end")));
		
		bc.setEntered(new Date());		
		bc.setEnteredBy(utente);
		bc.setModified(new Date());		
		bc.setModifiedBy(utente);
		bc.setClinica(utente.getClinica());
		bc.setStrutturaClinica(sc);		
		
		try {
			
			validaBean( bc , new ToDetail() );
			
			persistence.insert(bc);
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
			logger.error("Cannot save this booking");
			throw e;		
		}
				
		setMessaggio("Prenotazione effettuata con successo");
				
		redirectTo( "vam.agenda.ToDetail.us?idStrutturaClinica="+idStrutturaClinica );	
		
	}
}

