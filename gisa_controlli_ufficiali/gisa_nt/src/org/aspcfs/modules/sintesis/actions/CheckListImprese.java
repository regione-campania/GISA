/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.actions;

import java.sql.Connection;
import java.sql.SQLException;

import org.aspcfs.checklist.action.ChecklistSintesis;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.sintesis.base.SintesisStabilimento;
import org.aspcfs.modules.vigilanza.base.Ticket;

import com.darkhorseventures.framework.actions.ActionContext;

public class CheckListImprese extends CFSModule{
	
	 public String executeCommandAdd(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException
	  {
		 
		 if (!hasPermission(context, "checklist-checklist-add"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		try 
		{
		
			ChecklistSintesis c = new ChecklistSintesis();
			c.executeCommandAdd(context);
			
			db = this.getConnection(context);
			SintesisStabilimento organization	 = 	new SintesisStabilimento(db, Integer.parseInt(context.getRequest().getParameter("altId")), true);
			organization.getPrefissoAction(context.getAction().getActionName());
			
			context.getRequest().setAttribute("OrgDetails", organization);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		
		 return "checklistImpreseAdd";
	 
	  }
	 
	 
	 public String executeCommandSave(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException
	 {
		 
		 if (!hasPermission(context, "checklist-checklist-add")) 
		 {
				return ("PermissionError");
		 }		
		 
		Connection db = null;
		try 
		{
			
			ChecklistSintesis c = new ChecklistSintesis();
			c.executeCommandSave(context);
			
			db = this.getConnection(context);
			SintesisStabilimento organization	 = 	new SintesisStabilimento(db, Integer.parseInt(context.getRequest().getParameter("altId")), true);
			organization.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", organization);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		 return "checklistImpreseSaveOk";
	  }
	 
	 public String executeCommandView(ActionContext context) throws IndirizzoNotFoundException, IllegalAccessException, InstantiationException
	 {
		 
		 if (!hasPermission(context, "checklist-checklist-view")) 
		{
			return ("PermissionError");
		}
				
		Connection db = null;
		try 
		{
			
			ChecklistSintesis c = new ChecklistSintesis();
			c.executeCommandView(context);
			
			db = this.getConnection(context);
			org.aspcfs.checklist.base.Organization 	orgTemp 		= 	(org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			SintesisStabilimento 							organization	= 	new SintesisStabilimento(db, orgTemp.getOrg_id(), true);
			organization.getPrefissoAction(context.getAction().getActionName());
			
			context.getRequest().setAttribute("OrgDetails", organization);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		 return "checklistImpreseView";
	 }
	 
	 public String executeCommandModify(ActionContext context) throws IndirizzoNotFoundException, IllegalAccessException, InstantiationException
	 {
		 
		 
		 if (!hasPermission(context, "checklist-checklist-edit")) 
		 {
			return ("PermissionError");
		 }
			
		Connection db = null;
		try 
		{
			
			ChecklistSintesis c = new ChecklistSintesis();
			c.executeCommandModify(context);
			db = this.getConnection(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			
			SintesisStabilimento organization	 = 	new SintesisStabilimento(db, orgTemp.getOrg_id(), true);
			organization.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", organization);
			
		} 
		catch (SQLException e) 
		{
			context.getResponse().setStatus(2012);
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		 return "checklistImpreseModify";
	 }
	 
	 public String executeCommandAggiornaChecklist(ActionContext context)
	 {
		 
		 return "checklistImpreseUpdate";

	 }
	 
	 public String executeCommandUpdate(ActionContext context) throws IndirizzoNotFoundException, IllegalAccessException, InstantiationException
	 {
		 
			
		Connection db = null;
		try 
		{
			
			ChecklistSintesis c = new ChecklistSintesis()		;
			c.executeCommandUpdate(context)		;
			Ticket newTic = (Ticket) context.getRequest().getAttribute("TicketDetails");

			db = this.getConnection(context)	;
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			SintesisStabilimento organization	 = 	new SintesisStabilimento(db, newTic.getAltId(), true);
			organization.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", organization);
			
		} 
		catch (Exception e) 
		{
			
				context.getRequest().setAttribute("ErroreChecklist", "Errore");
				return executeCommandModify(context);
			
			//context.getRequest().setAttribute("Error", e);
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		 return "checklistImpreseUpdate";
	 }
	 
	 public String executeCommandDelete(ActionContext context)
	 {
		 
		 
		 if (!hasPermission(context, "checklist-checklist-delete")) 
		 {
			return ("PermissionError");
		 }
			
		Connection db = null;
		try  
		{
			
			ChecklistSintesis c = new ChecklistSintesis();
			c.executeCommandDelete(context);
			
			db = this.getConnection(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			SintesisStabilimento organization	 = 	new SintesisStabilimento(db, orgTemp.getOrg_id(), true);
				organization.getPrefissoAction(context.getAction().getActionName());
				context.getRequest().setAttribute("OrgDetails", organization);
		
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		
		 return ("checklistImpreseDelete");
	 }
	 
	 public String executeCommandUpdateCategoria(ActionContext context)
	 {
					
		Connection db = null;
		String action="";
		String idControllo = context.getRequest().getParameter("idC");
		String altId = context.getRequest().getParameter("altId");
	
		
		
		try 
		{
			
			db = this.getConnection(context);
			
			//verifica possibilita chiusura CU
			Ticket thisTicket = new Ticket(db, Integer.parseInt(idControllo));
			String messaggioAllegatiSanzione = thisTicket.isControlloChiudibileAllegatiSanzione(db);
			if (messaggioAllegatiSanzione!=null && !messaggioAllegatiSanzione.equals("")){
				int flag = 6;
				context.getRequest().setAttribute("Chiudi",""+flag);
				context.getRequest().setAttribute("Messaggio",messaggioAllegatiSanzione);
				context.getRequest().setAttribute("id", idControllo);
				context.getRequest().setAttribute("altId", altId);
				org.aspcfs.modules.sintesis.actions.AccountVigilanza AV = new org.aspcfs.modules.sintesis.actions.AccountVigilanza();
				//return AV.executeCommandTicketDetails(context);
				return AV.executeCommandChiudiTutto(context);
			}
			


			ChecklistSintesis c = new ChecklistSintesis();
			
			c.executeCommandUpdateLivello(context);
			

			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			SintesisStabilimento organization	 = 	new SintesisStabilimento(db,Integer.parseInt(altId), true);
			
			action = organization.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", organization);
		
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		
		if ("OpuStab".equals(action)){
			context.getRequest().setAttribute("id", idControllo);
			context.getRequest().setAttribute("altId", altId);
			org.aspcfs.modules.suap.actions.AccountVigilanza AV = new org.aspcfs.modules.suap.actions.AccountVigilanza();

			return AV.executeCommandChiudiTutto(context);
			//return AV.executeCommandTicketDetails(context);
		}
		
		AccountVigilanza AV = new AccountVigilanza();
		return AV.executeCommandChiudiTutto(context);
		//return ("checklistImpreseUpdateCategoria");
		}
	 
	 
	 
	
	 
	 
	
	 
	 
	 

}
