/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.checklistdocumenti.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

import org.aspcfs.checklist.base.Audit;
import org.aspcfs.checklist.base.AuditChecklist;
import org.aspcfs.checklist.base.AuditChecklistType;
import org.aspcfs.checklist.base.Organization;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.web.CustomLookupElement;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class ChecklistDettaglio extends CFSModule {

	public String executeCommandView(ActionContext context, Connection db) {


		String 				id 				= context.getRequest().getParameter("id")	;
		
	//	Connection 			db 				= 	null	;
		Organization 		org 			= 	null	;
		Audit 				audit 			= 	null	;
		CustomLookupList 	checklistType	= 	null	;
		
		ArrayList < CustomLookupList > 		checklistList 		= null	; 
		ArrayList < AuditChecklist > 		auditChecklist 		= null	;
		ArrayList < AuditChecklistType > 	auditChecklistType 	= null	;
		
		String aggiornaCategoria = context.getRequest().getParameter("aggiorna");

		try 
		{
		//	db = this.getConnection(context);

			audit 	= 	new Audit( db , id )						;
			org 	= 	new Organization( db, audit.getOrgId() )	;
			
			checklistType 		= new CustomLookupList()				;
			checklistList 		= new ArrayList<CustomLookupList>()		;
			auditChecklist 		= new ArrayList<AuditChecklist>()		;
			auditChecklistType 	= new ArrayList<AuditChecklistType>()	;

			int catRischioId=audit.getTipoChecklist();

			checklistType.setTableName("checklist_type");
			checklistType.addField("*");
			checklistType.buildListByIdField(db, "catrischio_id", catRischioId,audit.isPrincipale());
			String id_controllo=audit.getIdControllo();

			while(id_controllo.startsWith("0"))
			{
				id_controllo=id_controllo.substring(1);
			}

			int numero = Integer.parseInt(id_controllo);
			
			SystemStatus systemStatus = this.getSystemStatus(context);
			
			Ticket newTic = new Ticket()			;
			newTic.setSystemStatus(systemStatus)	;
			newTic.queryRecord(db, numero)			;

			context.getRequest().setAttribute("TicketDetails",newTic)	;
			context.getRequest().setAttribute("idC", numero)			;
			Iterator itr = checklistType.iterator()						;
			LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");    	      
			newTic.getTipoAudit();
			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			if(newTic.getTipoAudit().containsKey(2))
			{
				LookupElement element= categoriaRischioList.get("Audit BPI");
				categoriaRischioList.remove(element.getCode());

			}
			else
			{
				if(newTic.getTipoAudit().containsKey(3))
				{
					LookupElement element=  categoriaRischioList.get("Audit HACCP");
					categoriaRischioList.remove(element.getCode());
				}

			}

			categoriaRischioList.removeElementByLevel(2);
			context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);

			org.aspcfs.checklist.base.AuditList auditL = new org.aspcfs.checklist.base.AuditList()	;
			
			int AuditOrgId 	= newTic.getOrgId()		;
			String idT 		= newTic.getPaddedId()	;
			audit.setOrgId(AuditOrgId)				;

			auditL.buildListControlli(db, AuditOrgId, idT)		;
			Iterator < Audit > itera	=  auditL.iterator()	;
			int  punteggioChecklist	=	0							;
			while(itera.hasNext())
			{
				Audit temp=itera.next()						;
				punteggioChecklist+=temp.getLivelloRischio();

			}
			if(auditL.size()==1)
			{
				context.getRequest().setAttribute("prima", "si") ;
			}

			context.getRequest().setAttribute("punteggioCheckList", punteggioChecklist) ;
		
			if (context.getParameter("SalvataggioChecklist")!=null)
			{
				context.getRequest().setAttribute("SalvataggioChecklist", "OK");
			}

			while (itr.hasNext())
			{ 
				CustomLookupElement thisElem = new CustomLookupElement()			; 
				thisElem = (CustomLookupElement) itr.next()							;
				int checklistTypeId = Integer.parseInt(thisElem.getValue("code"))	;

				CustomLookupList checklist = new CustomLookupList()		;
				checklist.setTableName("checklist")						;
				checklist.addField("*")									;
				checklist.buildListByType(db, checklistTypeId)			;

				if (!checklist.isEmpty())
				{
					checklistList.add(checklist)	;
				}
			}
			auditChecklist 		= AuditChecklist.queryRecord(db, audit.getId())		;
			auditChecklistType 	= AuditChecklistType.queryRecord(db, audit.getId())	;

		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		
		} 
		finally 
		{
			//this.freeConnection(context, db);
		}

		
		context.getRequest().setAttribute("Audit", audit);
		context.getRequest().setAttribute("OrgDetails", org);
		context.getRequest().setAttribute("checklistList", checklistList);
		context.getRequest().setAttribute("typeList", checklistType);
		context.getRequest().setAttribute("auditChecklist", auditChecklist);
		context.getRequest().setAttribute("auditChecklistType", auditChecklistType);
		return ("AccountsAuditViewOK");
	}
	
}
