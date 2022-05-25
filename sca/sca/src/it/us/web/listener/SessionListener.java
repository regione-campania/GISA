/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package it.us.web.listener;

import java.util.ArrayList;
import java.util.HashMap;

import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.UserOperation;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class SessionListener implements HttpSessionListener 
{

	@Override
	public void sessionCreated(HttpSessionEvent event) 
	{
		
	} 

	@Override
	public void sessionDestroyed(HttpSessionEvent event) 
	{
		try
		{
			BUtente utente = null;
			HttpSession sessione = event.getSession();
			
			//Nel caso del Logout l'utente e gia stato tolto dalla struttura dati degli utenti loggati "utenti", 
			//quindi non c'e bisogno di aggiornarla
			//Nel caso la Session viene distrutta per timeout o altri motivi, l'aggiornamento e necessario
			
			if (sessione.getAttribute("operazioni")!=null){
				ArrayList<UserOperation> op=(ArrayList<UserOperation>) sessione.getAttribute("operazioni");
				GenericAction.writeStorico(op, false);  
			 }
			
			if(sessione!=null && sessione.getAttribute("utente")!=null)
			{
				HashMap<String, HttpSession> utenti = (HashMap<String, HttpSession>)sessione.getServletContext().getAttribute("utenti");
				utenti.remove(((BUtente)sessione.getAttribute("utente")).getUsername());
				sessione.getServletContext().setAttribute("utenti", utenti);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}

