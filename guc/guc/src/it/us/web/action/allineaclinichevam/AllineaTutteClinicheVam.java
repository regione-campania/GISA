/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.allineaclinichevam;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import it.us.web.action.GenericAction;
import it.us.web.bean.raggruppabduvam.Utente;
import it.us.web.bean.raggruppabduvam.UtentiList;
import it.us.web.exceptions.AuthorizationException;

public class AllineaTutteClinicheVam extends GenericAction
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
		
		int idUtenteGuc = interoFromRequest("id");
		int idUtenteOperazione = utente.getId();
		
		String esito = "";
		
		PreparedStatement pst = db.prepareStatement("select * from allinea_tutte_cliniche_vam_per_hd (?, ?)");
		int i = 0;
		pst.setInt(++i, idUtenteGuc);
		pst.setInt(++i, idUtenteOperazione);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			esito = rs.getString(1);
		
		req.setAttribute("esito", esito); 
		req.setAttribute("idUtenteGuc", String.valueOf(idUtenteGuc)); 

		gotoPage( "/jsp/allineaclinichevam/esito.jsp" );
	}


}
