/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macroareaiuv.actions;

import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.utils.web.*;

public final class MacroareaIUV extends CFSModule {
	
	public String executeCommandDashboardScelta(ActionContext context) {
	    if (!hasPermission(context, "macroarea-view")) {
	      if (!hasPermission(context, "macroarea-view")) {
	        return ("PermissionError");
	      }
	      
	    }
	    addModuleBean(context, "Dashboard", "Dashboard");
	    
	  

	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");


	  
	    context.getRequest().setAttribute("Return", "Accounts");
	    return ("DashboardSceltaOK");
	  }

}
