/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import java.util.Vector;

import it.us.web.action.GenericAction;
import it.us.web.bean.BRuolo;
import it.us.web.bean.BUtente;
import it.us.web.dao.RuoloDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToAddEditAdministrator extends GenericAction
{
	private static final Logger logger = LoggerFactory.getLogger( EditAnagrafica.class );
	
	@Override
	public void can() throws AuthorizationException
	{
			isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception{
		String op = stringaFromRequest("operation");
		Vector<BRuolo> ruoli = RuoloDAO.getRuoli();
		BUtente u = null;
		int id = -1;
		
		if (op.equals("edit")) {
			id = interoFromRequest("id");
			u = UtenteDAO.getUtenteBId(db, id);
		}
		
		req.setAttribute("idUtente", id);
		req.setAttribute("operation", op);
		req.setAttribute("UserRecord", u);
		req.setAttribute("ruoli", ruoli);
		gotoPage( "/jsp/guc/addEditAdministrator.jsp" );
	}
}
