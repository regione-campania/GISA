/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javassist.bytecode.Descriptor.Iterator;
import it.us.web.action.GenericAction;
import it.us.web.action.Home;
import it.us.web.bean.guc.Asl;
import it.us.web.bean.guc.Utente;
import it.us.web.dao.AslDAO;
import it.us.web.dao.guc.UtenteGucDAO;
import it.us.web.exceptions.AuthorizationException;

public class ToEditEmail extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception{
		
		int id = interoFromRequest("id");
		Utente u = null;
		
		List<Utente> utentiList = UtenteGucDAO.listaUtentibyId(db, id);
		if( utentiList.size() > 0 )
		{
			u = utentiList.get(0);
			req.setAttribute("UserRecord", u);
			gotoPage( "/jsp/guc/edit_email.jsp" );
			
		}
		else
		{
			setErrore("Utente con id " + id + " non trovato");
			goToAction(new Home());
		}	
	}
}
