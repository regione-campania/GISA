/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedeCentralizzate.actions;

import java.sql.Connection;
import java.sql.SQLException;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.schedeCentralizzate.base.SchedaCentralizzataControllo;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class SchedaCentralizzataControlloAction extends CFSModule {
	public SchedaCentralizzataControlloAction(){
		
	}
	
	public String executeCommandGeneraScheda(ActionContext context) {

		String ticketId = context.getRequest().getParameter("ticketId");
	
		SchedaCentralizzataControllo scheda = new SchedaCentralizzataControllo();
		scheda.setTicketId(Integer.parseInt(ticketId));
			
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			
			Ticket t = new Ticket();
			t.queryRecord(db, Integer.parseInt(ticketId));
			scheda.setTipo(t.getTipoCampione());
			scheda.popolaScheda(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("SchedaControllo", scheda);
		return "SchedaControlloOk";
		
	}

	
	
	
	
}
