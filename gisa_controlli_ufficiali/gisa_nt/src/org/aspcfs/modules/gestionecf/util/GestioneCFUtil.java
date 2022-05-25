/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestionecf.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneCFUtil  extends CFSModule{

	public String executeCommandGestioneCF(ActionContext context)
	{
		
		String nome = context.getRequest().getParameter("nome");
		context.getRequest().setAttribute("nome", nome);
		
		String cognome = context.getRequest().getParameter("cognome");
		context.getRequest().setAttribute("cognome", cognome);
		
		String data = context.getRequest().getParameter("data");
		context.getRequest().setAttribute("data", data);
		
		String comune = context.getRequest().getParameter("comune");
		context.getRequest().setAttribute("comune", comune);
		
		String sesso = context.getRequest().getParameter("sesso");
		context.getRequest().setAttribute("sesso", sesso);
		
		context.getRequest().setAttribute("cf", context.getRequest().getParameter("cf"));
				
		Connection db = null;

		try {
			db = this.getConnection(context);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,-1);
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}
		return "GestioneCFOK";
	}

}
