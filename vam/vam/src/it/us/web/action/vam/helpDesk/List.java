/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.helpDesk;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.bean.vam.Ecg;
import it.us.web.bean.vam.Ticket;
import it.us.web.bean.vam.lookup.LookupDiagnosi;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.lookup.LookupAslDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.DateUtils;

public class List extends GenericAction {

	
	public void can() throws AuthorizationException
	{
		BGuiView gui = GuiViewDAO.getView( "HD", "LIST", "MAIN" );
		can( gui, "w" );
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("helpDesk");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		//ArrayList<Ticket> t = (ArrayList<Ticket>) persistence.findAll(Ticket.class);
		ArrayList<Ticket> t = null;
		HashMap<Integer, String> ruoliUtenti       = (HashMap<Integer, String>)context.getAttribute("ruoliUtenti");
		String ruoloUtente = ruoliUtenti.get(utente.getSuperutente().getId());
		
		if(!ruoloUtente.equalsIgnoreCase("Amministratore"))
		{
			ArrayList<BUtente> enteredBy = null;
			if(ruoloUtente.equalsIgnoreCase("Referente ASL"))
			{
				//Per prendere i ticket inseriti da tutti gli utenti dell'ASL del referente
				//persistence.update(utente.getAslReferenza());
				Context ctx3 = new InitialContext();
				javax.sql.DataSource ds3 = (javax.sql.DataSource)ctx3.lookup("java:comp/env/jdbc/vamM");
				connection = ds3.getConnection();
				GenericAction.aggiornaConnessioneApertaSessione(req);
				setConnection(connection);
				enteredBy = UtenteDAO.getUtenti(connection, utente.getAslReferenza().getId(),-1);
			}
			else
			{
				//Per prendere anche i ticket inseriti dallo stesso utente quando si è loggato con un'altra clinica
				enteredBy = (ArrayList<BUtente>) persistence.createCriteria( BUtente.class )
				.add( Restrictions.eq("username", utente.getUsername()))
				.list();
			}
						
			t = (ArrayList<Ticket>) persistence.createCriteria( Ticket.class )
			.addOrder( Order.desc( "id" ) )
			.add( Restrictions.in("enteredBy", enteredBy))
			.list();
		}
		else
		{
			t = (ArrayList<Ticket>) persistence.createCriteria( Ticket.class )
			.addOrder( Order.desc( "id" ) )
			.list();
		}
		
		req.setAttribute("t", t);
		
		gotoPage("/jsp/vam/helpDesk/list.jsp");
			
	}
}
