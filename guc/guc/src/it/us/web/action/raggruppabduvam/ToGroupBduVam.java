/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.raggruppabduvam;

import it.us.web.action.GenericAction;
import it.us.web.bean.raggruppabduvam.Utente;
import it.us.web.bean.raggruppabduvam.UtentiList;
import it.us.web.exceptions.AuthorizationException;

import java.util.ArrayList;



public class ToGroupBduVam extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		
		ArrayList<Utente> listaUtentiBDU = UtentiList.creaLista(db, "bdu");
		req.setAttribute("listaUtentiBDU", listaUtentiBDU);
		
		ArrayList<Utente> listaUtentiVAM = UtentiList.creaLista(db, "vam");
		req.setAttribute("listaUtentiVAM", listaUtentiVAM);
		
		gotoPage( "/jsp/raggruppabduvam/utentilist.jsp" );
		
	}
	

}
