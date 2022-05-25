/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.modello5.actions;
import java.sql.Connection;
import java.util.logging.Logger;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.modello5.base.Mod5;
import org.aspcfs.modules.vigilanza.base.Ticket;

import com.darkhorseventures.framework.actions.ActionContext;


public final class Modello5 extends CFSModule {
	

	Logger logger = Logger.getLogger("MainLogger");


	public String executeCommandView(ActionContext context) {
		
		int idControllo = -1;
		
		try { idControllo = Integer.parseInt(context.getRequest().getParameter("idControllo"));} catch (Exception e) {}
		if (idControllo == -1)
			try { idControllo = Integer.parseInt((String)context.getRequest().getAttribute("idControllo"));} catch (Exception e) {}
		
		String tipoMod = context.getRequest().getParameter("tipoMod");
		if (tipoMod == null)
			tipoMod = (String) context.getRequest().getAttribute("tipoMod");
		
		context.getRequest().setAttribute("tipoMod", tipoMod);
	
		Connection db = null;
		try {
			db = this.getConnection(context);
			
			Ticket cu = new Ticket(db, idControllo);
			context.getRequest().setAttribute("cu", cu);

			Mod5 mod = new Mod5(db, idControllo);
			context.getRequest().setAttribute("mod", mod);
		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "Mod5Rev8OK";
	}

public String executeCommandSave(ActionContext context) {
		
		int idControllo = Integer.parseInt(context.getRequest().getParameter("idControllo"));
		String tipoMod = context.getRequest().getParameter("tipoMod");
		int id = -1;
		try { id = Integer.parseInt(context.getRequest().getParameter("id")); } catch (Exception e) {}
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			Mod5 mod = new Mod5();
			mod.buildDaRequest(context);
			mod.setControlloId(idControllo);
			if (id > 0){
				mod.setId(id);
				mod.setModifiedBy(getUserId(context));
				mod.update(db);
			}
			else {
				mod.setEnteredBy(getUserId(context));
				mod.insert(db);
			}
			
			mod.updateCu(db);
			
			context.getRequest().setAttribute("idControllo", idControllo);
			context.getRequest().setAttribute("tipoMod", tipoMod);

		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return executeCommandView(context);
	}




}
