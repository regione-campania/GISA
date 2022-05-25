/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.canipadronali.actions;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.checklist.action.Checklist;
import org.aspcfs.checklist.base.Audit;
import org.aspcfs.checklist.base.AuditChecklist;
import org.aspcfs.checklist.base.AuditChecklistType;
import org.aspcfs.modules.canipadronali.actions.AccountVigilanza;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.canipadronali.base.Cane;
import org.aspcfs.modules.canipadronali.base.OperazioniCaniDAO;
import org.aspcfs.modules.canipadronali.base.Proprietario;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.AuditReport;
import org.aspcfs.utils.web.CustomLookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class CheckListCaniPadronali extends CFSModule{
	
	 public String executeCommandAdd(ActionContext context)
	  {
		 
		 if (!hasPermission(context, "checklist-checklist-add"))
		{
			return ("PermissionError");
		}
		
		Connection db = null;
		try 
		{
			
			Checklist c = new Checklist();
			c.executeCommandAdd(context);
			
			
			db = this.getConnection(context);
			String 	idC 						= context.getRequest().getParameter("idC")				;
			String temporgId = context.getRequest().getParameter("orgId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(idC)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			proprietario.setAccountSize(Integer.parseInt(context.getRequest().getParameter("accountSize")));
			context.getRequest().setAttribute("OrgDetails", proprietario);
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
	 
	 
	 public String executeCommandSave(ActionContext context)
	  {
		 
		 if (!hasPermission(context, "checklist-checklist-add")) 
		 {
				return ("PermissionError");
		 }		
		 
		Connection db = null;
		try 
		{
			
			Checklist c = new Checklist();
			c.executeCommandSave(context);
			
			db = this.getConnection(context);
			String 		idCU 			= 	context.getRequest().getParameter("idC")			;		
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(idCU)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);
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
	 
	 public String executeCommandView(ActionContext context)
	 {
		 
		 if (!hasPermission(context, "checklist-checklist-view")) 
		{
			return ("PermissionError");
		}
				
		Connection db = null;
		try 
		{
			
			Checklist c = new Checklist();
			c.executeCommandView(context);
			db = this.getConnection(context);
			Ticket tic = (Ticket)context.getRequest().getAttribute("TicketDetails");
			org.aspcfs.checklist.base.Organization 	orgTemp 		= 	(org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(tic.getIdControlloUfficiale())) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);
			
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
	 
	 public String executeCommandModify(ActionContext context)
	 {
		 
		 
		 if (!hasPermission(context, "checklist-checklist-edit")) 
		 {
			return ("PermissionError");
		 }
			
		Connection db = null;
		try 
		{
		
			
			Checklist c = new Checklist();
			c.executeCommandModify(context);
			db = this.getConnection(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Ticket ticket = (Ticket) context.getRequest().getAttribute("TicketDetails");
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(ticket.getIdControlloUfficiale())) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		 return "checklistImpreseModify";
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
			
			Checklist c = new Checklist();
			c.executeCommandUpdate(context);
			db = this.getConnection(context);
			String idC = context.getRequest().getParameter("idC")	;

			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(idC)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);
			
		} 
		catch (SQLException e) 
		{
			
			context.getRequest().setAttribute("ErroreChecklist", "Errore");
			return executeCommandModify(context);
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
			String idC = context.getRequest().getParameter("idControllo");

			
			Checklist c = new Checklist();
			c.executeCommandDelete(context);
			db = this.getConnection(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(idC)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);
		
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
					org.aspcfs.modules.canipadronali.actions.AccountVigilanza AV = new org.aspcfs.modules.canipadronali.actions.AccountVigilanza();
					//return AV.executeCommandTicketDetails(context);
					return AV.executeCommandChiudiTutto(context);
				}
				
				
			Checklist c = new Checklist();
			c.executeCommandUpdateLivello(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(idControllo)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);
		
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		
		AccountVigilanza AV = new AccountVigilanza();
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
			
			Checklist c = new Checklist();
			c.executeCommandITextReport(context);
			db = this.getConnection(context);
			org.aspcfs.checklist.base.Organization orgTemp = (org.aspcfs.checklist.base.Organization) context.getRequest().getAttribute("OrgDetails");
			Organization organization	 = 	new Organization(db, orgTemp.getOrg_id());
			context.getRequest().setAttribute("OrgDetails", organization);
			
			OutputStream out = context.getResponse().getOutputStream();
			AuditReport report = new AuditReport();
			report.setContext(context);

			Audit 							audit 				= 	(Audit) context.getRequest().getAttribute("Audit");
			ArrayList<CustomLookupList> 	checklistList 		= 	( ArrayList<CustomLookupList>) context.getRequest().getAttribute("checklistList");
			CustomLookupList 				checklistType 		=	(CustomLookupList) context.getRequest().getAttribute("typeList");
			ArrayList<AuditChecklistType> 	auditChecklistType 	=	(ArrayList<AuditChecklistType>) context.getRequest().getAttribute("auditChecklistType");
			ArrayList<AuditChecklist> 		auditChecklist 		=	( ArrayList<AuditChecklist>) context.getRequest().getAttribute("auditChecklist");
			
			report.generate( out, audit, organization, checklistList, checklistType,auditChecklist, auditChecklistType, db);
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
