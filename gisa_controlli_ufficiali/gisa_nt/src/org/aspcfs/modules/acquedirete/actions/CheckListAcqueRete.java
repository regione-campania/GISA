/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.acquedirete.actions;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.aspcfs.checklist.action.Checklist;
import org.aspcfs.checklist.base.Audit;
import org.aspcfs.checklist.base.AuditChecklist;
import org.aspcfs.checklist.base.AuditChecklistType;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.acquedirete.actions.AcqueReteVigilanza;
import org.aspcfs.modules.acquedirete.base.Organization;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.AuditReport;
import org.aspcfs.utils.web.CustomLookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class CheckListAcqueRete extends CFSModule{
	
	Logger logger = Logger.getLogger(CheckListAcqueRete.class);
	 public String executeCommandAdd(ActionContext context)
	  {
		 
		 if (!hasPermission(context, "checklist-checklist-add"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			Organization organization	 = 	new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			organization.setAccountSize(context.getRequest().getParameter("accountSize"));
			context.getRequest().setAttribute("OrgDetails", organization);
			Checklist c = new Checklist();
			c.executeCommandAdd(context);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		
		 return "checklistAcqueReteAdd";
	 
	  }
	 
	 
	 public String executeCommandSave(ActionContext context)
	  {
		 
		 if (!hasPermission(context, "checklist-checklist-add")) 
		 {
				return ("PermissionError");
		 }		
		 
		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			Organization organization	 = 	new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", organization);
			Checklist c = new Checklist();
			c.executeCommandSave(context);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		 return "checklistAcqueReteSaveOk";
	  }
	 
	 public String executeCommandView(ActionContext context)
	 {
		 
		 if (!hasPermission(context, "checklist-checklist-view")) 
		{
			return ("PermissionError");
		}
				
		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			Checklist c = new Checklist();
			c.executeCommandView(context);
			org.aspcfs.checklist.base.Organization 	orgTemp 		= 	(org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization 							organization	= 	new Organization(db, orgTemp.getOrg_id());
			
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
		 return "checklistAcqueReteView";
	 }
	 
	 public String executeCommandModify(ActionContext context)
	 {
		 
		 
		 if (!hasPermission(context, "checklist-checklist-edit")) 
		 {
			return ("PermissionError");
		 }
			
		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			Checklist c = new Checklist();
			c.executeCommandModify(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization organization	 = 	new Organization(db, orgTemp.getOrg_id());
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
		 return "checklistAcqueReteModify";
	 }
	 
	 public String executeCommandUpdate(ActionContext context)
	 {
		 
		 
		 if (!hasPermission(context, "checklist-checklist-edit")) 
		 {
			return ("PermissionError");
		 }
			
		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			Checklist c = new Checklist();
			c.executeCommandUpdate(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization organization	 = 	new Organization(db, orgTemp.getOrg_id());
			context.getRequest().setAttribute("OrgDetails", organization);
			
		} 
		catch (SQLException e) 
		{
			logger.error("Errore in modifica Checklis : "+e.getMessage());
			context.getRequest().setAttribute("ErroreChecklist", "Errore");
			return executeCommandModify(context);
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		 return "checklistAcqueReteUpdate";
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
			db = this.getConnection(context);
			Checklist c = new Checklist();
			c.executeCommandDelete(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization organization	 = 	new Organization(db, orgTemp.getOrg_id());
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
		
		 return ("checklistAcqueReteDelete");
	 }
	 
	 public String executeCommandUpdateCategoria(ActionContext context)
	 {
					
		 Connection db = null;
			
			String idControllo = context.getRequest().getParameter("idC");
			String orgId = context.getRequest().getParameter("orgId");
			
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
					context.getRequest().setAttribute("orgId", orgId);
					org.aspcfs.modules.acquedirete.actions.AcqueReteVigilanza AV = new org.aspcfs.modules.acquedirete.actions.AcqueReteVigilanza();
					//return AV.executeCommandTicketDetails(context);
					return AV.executeCommandChiudiTutto(context);
				}
				
			Checklist c = new Checklist();
			c.executeCommandUpdateLivello(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization organization	 = 	new Organization(db, orgTemp.getOrg_id());
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
		
		AcqueReteVigilanza AV = new AcqueReteVigilanza();
		return AV.executeCommandChiudiTutto(context);
		//return ("checklistImpreseUpdateCategoria");	 
		}
	 
	 public String executeCommandStampa(ActionContext context)
	 {
		
		 
		 if (!hasPermission(context, "checklist-checklist-edit")) 
		 {
			return ("PermissionError");
		 }
			
		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			Checklist c = new Checklist();
			c.executeCommandITextReport(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization organization	 = 	new Organization(db, orgTemp.getOrg_id());
			context.getRequest().setAttribute("OrgDetails", organization);
			
			OutputStream out = context.getResponse().getOutputStream();
			AuditReport report = new AuditReport();
			

			Audit 							audit 				= 	(Audit) context.getRequest().getAttribute("Audit");
			ArrayList<CustomLookupList> 	checklistList 		= 	( ArrayList<CustomLookupList>) context.getRequest().getAttribute("checklistList");
			CustomLookupList 				checklistType 		=	(CustomLookupList) context.getRequest().getAttribute("typeList");
			ArrayList<AuditChecklistType> 	auditChecklistType 	=	(ArrayList<AuditChecklistType>) context.getRequest().getAttribute("auditChecklistType");
			ArrayList<AuditChecklist> 		auditChecklist 		=	( ArrayList<AuditChecklist>) context.getRequest().getAttribute("auditChecklist");
			
			//report.generate( out, audit, organization, checklistList, checklistType,auditChecklist, auditChecklistType, db);
			out.flush();
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		 return ("-none-");
	 }
	 
	 
	 
	
	 
	 
	 

}
