/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.agenda;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.vam.BookingClinica;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class ListBookings extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "CC", "LIST", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("agenda");
	}

	public void execute() throws Exception
	{
			
		int idStrutturaClinica = Integer.parseInt(session.getAttribute("idStrutturaClinica").toString());
		int idClinica 	= utente.getClinica().getId();
		
		List<Map<String, Object>> listBookings = new ArrayList();
				
		//Recupero prenotazioni di una clinica
		//ArrayList<BookingClinica> prenotazioni = (ArrayList<BookingClinica>) persistence.getNamedQuery("GetBookingsByClinica").setInteger("idClinica", idClinica).list();
		
		
		//Recupero prenotazioni di una struttura clinica
		ArrayList<BookingClinica> prenotazioni = (ArrayList<BookingClinica>) persistence.getNamedQuery("GetBookingsByStrutturaClinica").setInteger("idStrutturaClinica", idStrutturaClinica).list();
				
		Iterator bookings = prenotazioni.iterator();
		BookingClinica bc;
		
		while(bookings.hasNext()) {
			
			bc = (BookingClinica)bookings.next();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", 		bc.getId());		
			map.put("start", 	bc.getDa());
			map.put("end", 		bc.getA());
			map.put("title", 	bc.getTitle());
			map.put("body", 	bc.getBody());
			
			listBookings.add(map);			
			
		}
			
		String events = new Gson().toJson(listBookings);
					
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write(events);
		
	}
}


