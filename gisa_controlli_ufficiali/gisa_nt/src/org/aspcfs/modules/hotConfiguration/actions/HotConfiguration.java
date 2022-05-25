/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.hotConfiguration.actions;

import java.util.logging.Logger;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import com.darkhorseventures.framework.actions.ActionContext;


public final class HotConfiguration extends CFSModule 
{
	
	Logger logger = Logger.getLogger("MainLogger");
	
	public String executeCommandDefault( ActionContext context )
	{
		return executeCommandDashboard(context);
	}
	
	public String executeCommandDashboard(ActionContext context) {
		
		 if (!hasPermission(context, "accounts-dashboard-view")) {
			 if (!hasPermission(context, "hot-configuration-view")) {
		        return ("PermissionError");
		     }
		 } 
		 
		 return "ConfigOK";
		  
	}
	
	public String executeCommandConfig(ActionContext context) {
		
		 if (!hasPermission(context, "accounts-dashboard-view")) {
			 if (!hasPermission(context, "hot-configuration-view")) {
		        return ("PermissionError");
		     }
		 } 
		 
		 logger.info("Invocata la Configurazione a caldo");
			
			try{
			
				for(Object chiave : ApplicationProperties.getApplicationProperties().keySet()){
					ApplicationProperties.getApplicationProperties().setProperty(chiave.toString().trim(), context.getRequest().getParameter(chiave.toString().trim()) );
				}
				
				logger.info("Riconfigurazione a caldo avvenuta con successo");
				context.getRequest().setAttribute("configMessage", "Configurazione a caldo avvenuta con successo");
				
			}
			catch (Exception e) {
				logger.severe("Errore nella Configurazione a caldo");
				context.getRequest().setAttribute("configMessage", "Errore durante la Configurazione a caldo");
			}
		 
		 return "ConfigOK";
		  
	}

}