/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.validazione;

import it.us.web.action.GenericAction;
import it.us.web.bean.validazione.Richiesta;
import it.us.web.exceptions.AuthorizationException;



public class ToRichiesta extends GenericAction
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
		String numeroRichiesta = stringaFromRequest("numeroRichiesta");
		Richiesta ric = new Richiesta(db, numeroRichiesta);
		req.setAttribute("dettaglioRichiesta", ric);
		gotoPage( "/jsp/validazione/richiesta.jsp" );
		
	}
	

}
