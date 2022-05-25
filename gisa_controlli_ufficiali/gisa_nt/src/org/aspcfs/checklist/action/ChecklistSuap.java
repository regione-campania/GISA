/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.checklist.action;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.aspcf.modules.controlliufficiali.base.Ticket.TipoOperatori;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.checklist.base.Audit;
import org.aspcfs.checklist.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.checklist.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Parameter;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
//import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.modules.troubletickets.base.TicketDefect;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.DbiBdu;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupElement;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.ParameterUtils;
import org.aspcfs.utils.web.StateSelect;

import com.darkhorseventures.framework.actions.ActionContext;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
import com.zeroio.webutils.FileDownload;


/**
 * Actions for the Account Audit
 * 
 * @author Francesco Ricci
 * @created January 20, 2008
 */
public final class ChecklistSuap extends CFSModule {

	private java.util.logging.Logger logger =   java.util.logging.Logger.getLogger("MainLogger");

	public String executeCommandList(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-audit-view")) {
			return ("PermissionError");
		}
		AuditList auditList = new AuditList();
		String orgId = context.getRequest().getParameter("stabId");  //questo mi da problemi

		//Prepare pagedListInfo
		PagedListInfo auditListInfo = this.getPagedListInfo(context, "AuditListInfo");
		auditListInfo.setLink("AccountsAudit.do?command=List&orgId=" + orgId);

		//qui mettero la lookup

		Connection db = null;
		try {
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			setOrganization(context, db);
			LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
			categoriaRischioList.addItem(-1, "Nessuno");
			context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
			// Build the audit list
			auditList.setPagedListInfo(auditListInfo);
			auditList.setOrgId(Integer.parseInt(orgId));
			auditList.buildList(db);

			context.getRequest().setAttribute("AuditList", auditList);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("AccountsAuditListOK");
	}


	public String executeCommandView(ActionContext context) {


		String 				id 				= context.getRequest().getParameter("id")	;
		
		Connection 			db 				= 	null	;
		Organization 		org 			= 	null	;
		Audit 				audit 			= 	null	;
		CustomLookupList 	checklistType	= 	null	;
		
		ArrayList < CustomLookupList > 		checklistList 		= null	; 
		ArrayList < AuditChecklist > 		auditChecklist 		= null	;
		ArrayList < AuditChecklistType > 	auditChecklistType 	= null	;
		
		String aggiornaCategoria = context.getRequest().getParameter("aggiorna");

		try 
		{
			db = this.getConnection(context);

			audit 	= 	new Audit( db , id )						;
			org 	= 	new Organization(  audit.getIdStabilimento() )	;
			
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

			org.aspcfs.checklist.base.AuditList auditL = new org.aspcfs.checklist.base.AuditList()	;
			
			int AuditOrgId 	= newTic.getIdStabilimento()		;
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

			
			LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");    	      
			
			categoriaRischioList.removeElementByLevel(2);
			context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);

			
		
		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		
		} 
		

		finally 
		{
			this.freeConnection(context, db);
		}

		
		context.getRequest().setAttribute("Audit", audit);
		context.getRequest().setAttribute("OrgDetails", org);
		context.getRequest().setAttribute("checklistList", checklistList);
		context.getRequest().setAttribute("typeList", checklistType);
		context.getRequest().setAttribute("auditChecklist", auditChecklist);
		context.getRequest().setAttribute("auditChecklistType", auditChecklistType);
		return ("AccountsAuditViewOK");
	}


	public String executeCommandSave(ActionContext context) {

		Audit newAudit = (Audit) context.getFormBean();

		boolean recordInserted 	= false	;
		boolean isValid 		= false	;
		Audit insertedAudit 	= null	;
		Connection db 			= null	;
		Organization organ 		= null	;
		ArrayList<Parameter> 	risposte 				= ParameterUtils.list(context.getRequest(), "risposta")		;
		ArrayList<Parameter> 	valoreRange 			= ParameterUtils.list(context.getRequest(), "valoreRange")	;
		ArrayList<Parameter> 	operazione 				= ParameterUtils.list(context.getRequest(), "operazione")	;
		ArrayList<Parameter> 	paragrafiabilitati 		= ParameterUtils.list(context.getRequest(), "disabilita")	;
		ArrayList<Parameter> 	nota 					= ParameterUtils.list(context.getRequest(), "nota");

		String 		punti 			= 	context.getParameter( "punteggioUltimiAnni" )		;
		String 		idC 			= 	context.getRequest().getParameter( "idC" )			;
		String 		idLastDomanda	=	context.getRequest().getParameter( "lastdomanda" )	;
		String 		idCU 			= 	context.getRequest().getParameter("idC")			;		
		String 		isPrincipale	= 	context.getRequest().getParameter("isPrincipale")	;

		context.getRequest().setAttribute("idC",idCU)	;
		int punteggio = 0	;
		if ((punti != null)&&(!punti.equals("")))
		{
			punteggio = Integer.parseInt(punti);
		}
		else 
		{
			punteggio = 0;
		}
		org.aspcfs.modules.vigilanza.base.Ticket controlloUff =null;

		try {
			db = this.getConnection(context);
			controlloUff = new org.aspcfs.modules.vigilanza.base.Ticket(db, Integer.parseInt(idC));
			newAudit.setOrgId(Integer.parseInt(context.getRequest().getParameter("orgId")));
			newAudit.setNumeroRegistrazione( "" + newAudit.nextRegistrationNumber( db, newAudit.getOrgId() ) );
			newAudit.setTipoChecklist(context.getRequest().getParameter("accountSize"));
			newAudit.setLivelloRischio(newAudit.getLivelloRischio());
			newAudit.setPunteggioUltimiAnni(newAudit.getPunteggioUltimiAnni());
			newAudit.setIdControllo(controlloUff.getPaddedId());  
			newAudit.setIdLastDomanda(idLastDomanda);
			newAudit.setIdStabilimento(Integer.parseInt(context.getRequest().getParameter("orgId")));
			if(isPrincipale.equals("true"))
			{
				newAudit.setPrincipale(true);
			}
			
			if (newAudit.verificaEsistenzaChecklist(db, newAudit.getIdControllo(), newAudit.getTipoChecklist())==false)
			{
				recordInserted = newAudit.insert(context,db, risposte, valoreRange, operazione, nota, punteggio,paragrafiabilitati);
			
				context.getRequest().setAttribute("AuditDetail", newAudit);
			
				AuditList listAudit=new AuditList();
				listAudit.setOrgId(newAudit.getOrgId());
				listAudit.buildList(db);
				

			if (recordInserted) 
			{
				
				
				Integer puntiV = controlloUff.getPunteggio();
				int puntiNuovi =  newAudit.getLivelloRischio();
				if(puntiV != null)
				{
					puntiNuovi  += puntiV ;
				}
				controlloUff.setPunteggio(puntiNuovi);
				controlloUff.aggiornaPunteggioControlloUfficiale(db);
				
				org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
				controlliList.setOrgId(newAudit.getOrgId());
				int tipoIspezione = controlliList.getTipoIspezione();
				if(tipoIspezione == 4)
				{
					int punteggioAccumulato = controlliList.buildListControlliUltimiAnni(db, newAudit.getOrgId(), controlloUff.getAssignedDate(),controlloUff.getPaddedId());
					context.getRequest().setAttribute("punteggioUltimiAnni", punteggioAccumulato);
				}

				insertedAudit = new Audit(db, String.valueOf(newAudit.getId()));
				context.getRequest().setAttribute("AuditDetails", insertedAudit);
			}

			Integer idtampone = insertedAudit.getId();
			}
			else
			{
				context.getRequest().setAttribute("ChecklistError", "Attenzione il tipo di checklist inserito per questo controllo e' presente");
				
			}
		} 
		catch (Exception errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");
		}
		finally 
		{
			this.freeConnection(context, db);
		}

		if (recordInserted) 
		{
			String retPag = null;
			try
			{
				db = this.getConnection(context);
				Ticket newTic = new Ticket();
				SystemStatus systemStatus = this.getSystemStatus(context);
				newTic.setSystemStatus(systemStatus);
				newTic.queryRecord(db, Integer.parseInt(idC));

				org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
				int passedId = newTic.getOrgId();
				ticList.setOrgId(passedId);
				ticList.buildListControlli(db, passedId, idC);
				context.getRequest().setAttribute("TicList", ticList);

				org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
				int pasId = newTic.getOrgId();
				tamponiList.setOrgId(passedId);
				tamponiList.buildListControlli(db, pasId, idC);
				context.getRequest().setAttribute("TamponiList", tamponiList);

				org.aspcfs.modules.sanzioni.base.TicketList sanzioniList = new org.aspcfs.modules.sanzioni.base.TicketList();
				int passId = newTic.getOrgId();
				sanzioniList.setOrgId(passedId);
				sanzioniList.buildListControlli(db, passId, idC,8);
				context.getRequest().setAttribute("SanzioniList", sanzioniList);

				org.aspcfs.modules.reati.base.TicketList reatiList = new org.aspcfs.modules.reati.base.TicketList();
				int passIdR = newTic.getOrgId();
				reatiList.setOrgId(passedId);
				reatiList.buildListControlli(db, passIdR, idC,8);
				context.getRequest().setAttribute("ReatiList", reatiList);

				org.aspcfs.modules.sequestri.base.TicketList seqList = new org.aspcfs.modules.sequestri.base.TicketList();
				int passIdS = newTic.getOrgId();
				seqList.setOrgId(passedId);
				seqList.buildListControlli(db, passIdS, idC,8);
				context.getRequest().setAttribute("SequestriList", seqList);

				org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
				int passIdN = newTic.getOrgId();
				nonCList.setOrgId(passedId);
				nonCList.buildListControlli(db, passIdN, idC);
				context.getRequest().setAttribute("NonCList", nonCList);

				org.aspcfs.checklist.base.AuditList audit = new org.aspcfs.checklist.base.AuditList();
				int AuditOrgId = newTic.getOrgId();
				String idT = newTic.getPaddedId();
				audit.setOrgId(AuditOrgId);

				audit.buildListControlli(db, AuditOrgId, idT);
				Iterator<Audit> itera=audit.iterator();
				int punteggioChecklist=0;

				while(itera.hasNext())
				{
					Audit temp=itera.next();
					punteggioChecklist+=temp.getLivelloRischio();

				}
				context.getRequest().setAttribute("PunteggioCheckList", punteggioChecklist);
				context.getRequest().setAttribute("Audit", audit);

				//find record permissions for portal users
				if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) 
				{
					return ("PermissionError");
				}
		
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

				LookupList categoriaRischioList2 = new LookupList(db, "lookup_org_catrischio");
				categoriaRischioList2.addItem(-1, "-- SELEZIONA VOCE --");
				categoriaRischioList2.removeElementByLevel(1);
				context.getRequest().setAttribute("OrgCategoriaRischioList2", categoriaRischioList2);

	

				//Load the ticket state

				LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
				TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("TipoCampione", TipoCampione);



				LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
				EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("EsitoCampione", EsitoCampione);



				LookupList SiteIdList = new LookupList(db, "lookup_site_id");
				SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
				SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
				context.getRequest().setAttribute("SiteIdList", SiteIdList);

				// check wether or not the customer product id exists
				
				
				context.getRequest().setAttribute("TicketDetails", newTic);

				retPag = "DettaglioOK";

				addRecentItem(context, newTic);
				// Load the organization for the header
				Organization thisOrganization = new Organization(db, newTic.getOrgId());	      
				addModuleBean(context, "View Accounts", "View Tickets");
				// Reset any pagedLists since this could be a new visit to this ticket
				deletePagedListInfo(context, "AccountTicketsFolderInfo");
				deletePagedListInfo(context, "AccountTicketDocumentListInfo");
				deletePagedListInfo(context, "AccountTicketTaskListInfo");
				deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
	

			} 
			catch (Exception errorMessage) 
			{
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} 
			finally 
			{
				this.freeConnection(context, db);
			}

			return "DettaglioCU";

		}
		return (executeCommandAdd(context));
	}
	
	
	
	
	/*ublic String executeCommandAdd(Connection db , ActionContext context)
	{

		ArrayList< CustomLookupList > 							checklistList 	= 	null	; 
		CustomLookupList 										checklistType 	= 	null	;


		String 	orgId 						= context.getRequest().getParameter( "orgId" )			;
		String 	idControlloUfficiale 		= context.getRequest().getParameter("idControllo")		;
		String 	idC 						= context.getRequest().getParameter("idC")				;
		String 	accountSize 				= context.getRequest().getParameter("accountSize")		;
		String	isPrincipale				= context.getRequest().getParameter("isPrincipale")		;
		boolean isFirstCheckList = false;
		context.getRequest().setAttribute("isPrincipale", isPrincipale);
		if(isPrincipale.equals("true"))
		{
			isFirstCheckList = true;
		}

		try 
		{
			
			  idControlloUfficiale = (String.valueOf(idC));
			    while (idControlloUfficiale.length() < 6) 
			    {
			    	idControlloUfficiale = "0" + idControlloUfficiale;
			    }
			
			int catRischioId = Integer.parseInt(accountSize);

			checklistType = new CustomLookupList();
			checklistType.setTableName("checklist_type");
			checklistType.addField("*");
			checklistType.buildListByIdField(db, "catrischio_id", catRischioId,isFirstCheckList);
			context.getRequest().setAttribute("TipoCheckList", catRischioId);
			checklistList = new ArrayList<CustomLookupList>();

			org.aspcfs.modules.vigilanza.base.Ticket controlloUff = new org.aspcfs.modules.vigilanza.base.Ticket(db, Integer.parseInt(idC));

			org.aspcfs.checklist.base.AuditList audit = new org.aspcfs.checklist.base.AuditList();
			int AuditOrgId = controlloUff.getOrgId();
			String idControlloasString = controlloUff.getPaddedId();
			audit.setOrgId(AuditOrgId);

			audit.buildListControlli(db, AuditOrgId, idControlloasString);

			// VERIFICA SE STO APRENDO LA PRIMA CHECKLIST IN QUESTO CONTROLLO UFFICIALE
			

			Iterator<Audit> itera= audit.iterator();
			int punteggioChecklist=0;
			while(itera.hasNext())
			{
				Audit temp=itera.next();
				punteggioChecklist+=temp.getLivelloRischio();

			}

			context.getRequest().setAttribute("punteggioCheckList", punteggioChecklist);  
			org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
			controlliList.setOrgId(AuditOrgId);
			int punteggioAccumulato = controlliList.buildListControlliUltimiAnni(db, AuditOrgId,Integer.parseInt(idC));
			context.getRequest().setAttribute("punteggioUltimiAnni", punteggioAccumulato);


			org.aspcfs.modules.vigilanza.base.Ticket controlliList2 = new org.aspcfs.modules.vigilanza.base.Ticket();
			controlliList2.queryRecord(db, Integer.parseInt(idC));

			context.getRequest().setAttribute("ControlloUfficiale", controlliList2);
			Iterator itr = checklistType.iterator();
			while (itr.hasNext())
			{	 
				CustomLookupElement thisElem = new CustomLookupElement(); 
				thisElem = (CustomLookupElement) itr.next();
				int checklistTypeId = Integer.parseInt(thisElem.getValue("code"));

				CustomLookupList checklist = new CustomLookupList();
				checklist.setTableName("checklist");
				checklist.addField("*");
				checklist.buildListByType(db, checklistTypeId);
				if (!checklist.isEmpty())
				{
					checklistList.add(checklist);
				}
			}


			context.getRequest().setAttribute("idControllo",idControlloUfficiale);
			context.getRequest().setAttribute("idC",idC);


		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e)	;
			e.printStackTrace();
			return ("SystemError")	;
		} 
		finally 
		{
			context.getRequest().setAttribute("checklistList", checklistList);
			context.getRequest().setAttribute("typeList", checklistType);
			this.freeConnection(context, db);
		}


		return "";

	}
	*/
	
	
	
	
	public String executeCommandUpdate(ActionContext context) throws SQLException {
		
		SystemStatus 	systemStatus 	= 	this.getSystemStatus(context)	;
		

		boolean 			isValid				= 	false	;
		Connection 			db 					= 	null	;
		int 				resultCount 		= 	-1		;
		int 				resultCountCk 		= 	-1		;
		int 				resultCountCkTp 	= 	-1		;
		Ticket newTic=null; 
		String 			punti 		= context.getParameter("punteggioUltimiAnni")	;
		int 			punteggio 	= 0		;
		Organization 	organ 		= null	;
		Audit 			thisAudit 		= null ;
		ArrayList<Parameter> risposte 			= ParameterUtils.list(context.getRequest(), "risposta")		;
		ArrayList<Parameter> valoreRange 		= ParameterUtils.list(context.getRequest(), "valoreRange")	;
		ArrayList<Parameter> operazione			= ParameterUtils.list(context.getRequest(), "operazione")	;
		ArrayList<Parameter> nota			 	= ParameterUtils.list(context.getRequest(), "nota")			;
		ArrayList<Parameter> paragrafiabilitati = ParameterUtils.list(context.getRequest(), "disabilita")	;
		String stato = context.getRequest().getParameter("stato");
		
		String idC = context.getRequest().getParameter("idC")	;
		try {
			db = this.getConnection(context);
		 			thisAudit 		= new Audit(db,context.getRequest().getParameter("id"))	;
		thisAudit.setIdLastDomanda(context.getRequest().getParameter("idLastDomanda"))						;
			thisAudit.setNote(context.getParameter("note"));
			newTic = new Ticket();
			newTic.setSystemStatus(systemStatus);
			newTic.setId(Integer.parseInt(idC));
			newTic.queryRecord(db, Integer.parseInt(idC));
			thisAudit.setOrgId(-1);
			
	
		if ((punti != null)&&(!punti.equals("")))
		{
			punteggio = Integer.parseInt(punti);}
		else 
		{
			punteggio = 0;
		}
		boolean aggiornato=false;
		
			thisAudit.setPunteggioUltimiAnni(punteggio);
			
			thisAudit.setStato(stato);
			System.out.println("aggiornamento checklist in corso . . . ");
			resultCount = thisAudit.update(db, risposte, valoreRange, operazione, nota, punteggio,paragrafiabilitati);
			
			System.out.println("Fine aggiornamento checklist . . . ");
			
			org.aspcfs.checklist.base.AuditList audit 			= new org.aspcfs.checklist.base.AuditList();
			int 								AuditOrgId 		= newTic.getOrgId();
			String 								idT 			= newTic.getPaddedId();
			
			audit.buildListControlli(db, AuditOrgId, idT);
			Iterator<Audit> itera=audit.iterator();
			int punteggioChecklist=0;
			boolean categoriaAggiornabile = true ;

			while(itera.hasNext())
			{
				Audit temp=itera.next();
				punteggioChecklist+=temp.getLivelloRischio();
				 if (temp.getStato().equalsIgnoreCase("temporanea"))
		    	 {
		    		  categoriaAggiornabile = false ;
		    	 }
			}
		      newTic.setCategoriaAggiornabile(categoriaAggiornabile);

			context.getRequest().setAttribute("PunteggioCheckList", punteggioChecklist);
			Integer idtampone = thisAudit.getId();
	
				

					org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
				int passIdN = newTic.getIdStabilimento();
				nonCList.setIdStabilimento(newTic.getIdStabilimento());
				nonCList.buildListControlli(db, passIdN, idC);
				context.getRequest().setAttribute("NonCList", nonCList);

				 audit = new org.aspcfs.checklist.base.AuditList();
				 AuditOrgId = newTic.getOrgId();
				 idT = newTic.getPaddedId();
				audit.setOrgId(-1);

				audit.buildListControlli(db, AuditOrgId, idT);


				 itera=audit.iterator();
				 punteggioChecklist=0;
				while(itera.hasNext())
				{
					Audit temp=itera.next();
					punteggioChecklist+=temp.getLivelloRischio();

				}
				
				context.getRequest().setAttribute("idChecklist_corrente", thisAudit.getTipoChecklist());
				context.getRequest().setAttribute("PunteggioCheckList", punteggioChecklist);
				context.getRequest().setAttribute("Audit", audit);

				
			
				LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
				
				newTic.getTipoAudit();
				categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
				if(newTic.getTipoAudit().containsKey(2))
				{
					LookupElement element= categoriaRischioList.get("Audit BPI");
					categoriaRischioList.remove(element.getCode());

				}else
				{
					if(newTic.getTipoAudit().containsKey(3))
					{
						LookupElement element=  categoriaRischioList.get("Audit HACCP");
						categoriaRischioList.remove(element.getCode());
					}

				}
				categoriaRischioList.removeElementByLevel(2);
				context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);

				LookupList categoriaRischioList2 = new LookupList(db, "lookup_org_catrischio");
				categoriaRischioList2.addItem(-1, "-- SELEZIONA VOCE --");
				categoriaRischioList2.removeElementByLevel(1);
				context.getRequest().setAttribute("OrgCategoriaRischioList2", categoriaRischioList2);

				
				//Load the ticket state
				LookupList nucleo = new LookupList(db,"lookup_nucleo_ispettivo");
				context.getRequest().setAttribute("TitoloNucleo", nucleo);
				LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
				TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("TipoCampione", TipoCampione);

				//aggiunto da d.dauria
				

				LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
				EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("EsitoCampione", EsitoCampione);

			
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			UserBean user = (UserBean)context.getSession().getAttribute("User");
			
			
			ControlliUfficialiUtil.buildLookupTipoControlloUfficiale(db, context, systemStatus, user.getRoleId(), newTic.getSiteId(), Ticket.TIPO_OPU, null);

			
				newTic.setNumeroAudit(db);
				context.getRequest().setAttribute("TicketDetails", newTic);

				String  retPag = "DettaglioOK";

				addRecentItem(context, newTic);
				// Load the organization for the header
				Organization thisOrganization = new Organization(db, newTic.getOrgId());
				context.getRequest().setAttribute("OrgDetails", thisOrganization);
				addModuleBean(context, "View Accounts", "View Tickets");
				// Reset any pagedLists since this could be a new visit to this ticket
				deletePagedListInfo(context, "AccountTicketsFolderInfo");
				deletePagedListInfo(context, "AccountTicketDocumentListInfo");
				deletePagedListInfo(context, "AccountTicketTaskListInfo");
				deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
				
				context.getRequest().setAttribute("SalvataggioChecklist", "OK");
				
				
		} catch (SQLException errorMessage) 
		{
			
			errorMessage.printStackTrace();
			context.getResponse().setStatus(2012); // set il codice di risposta http per l'applet in caso di eccezione
			if (stato.equalsIgnoreCase("temporanea"))
			{
				context.getRequest().setAttribute("Error", errorMessage);
			}
				
			throw errorMessage ;
			
		}
		finally 
		{
			this.freeConnection(context, db);
		}

		if (resultCount == 1) 
		{
			if ("list".equals(context.getRequest().getParameter("return"))) {
				return executeCommandView(context);
			} 
		
		} else 
		{
			if (resultCount == -1 || !isValid) 
			{
				return (executeCommandModify(context));
			}
			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
		
		
		return "";
		
	}
	
	public String executeCommandDelete(ActionContext context) {
		if (!(hasPermission(context, "checklist-checklist-delete"))) {
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		boolean 			recordDeleted 	= false	;
		Connection 			db 				= null	;
		Audit 				thisAudit 		= null	;

		try {
			
			db = this.getConnection(context);
			
			thisAudit = new Audit(db, context.getRequest().getParameter("id"));
			
			Organization thisOrganization = new Organization(thisAudit.getIdStabilimento());
			//check permission to record
			if (!isRecordAccessPermitted(context, db, thisAudit.getOrgId())) 
			{
				return ("PermissionError");
			}
			UserBean user = (UserBean)context.getSession().getAttribute("User");

			recordDeleted = thisAudit.deleteAudit(db, user.getUserId());

		

		if (recordDeleted) 
		{
			String idC = context.getRequest().getParameter("idControllo");
			
			Ticket newTic = new Ticket();
			
			
			newTic.setSystemStatus(systemStatus);
			newTic.queryRecord(db, Integer.parseInt(idC));
			
			if(newTic.getPunteggio() >= thisAudit.getLivelloRischio())
			{
				int punteggioAggiornato = newTic.getPunteggio() - thisAudit.getLivelloRischio();
				newTic.setPunteggio(punteggioAggiornato);
			}
			newTic.aggiornaPunteggioControlloUfficiale(db);

			org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
			int passedId = newTic.getOrgId();
			ticList.setOrgId(passedId);
			ticList.buildListControlli(db, passedId, idC);
			context.getRequest().setAttribute("TicList", ticList);

			org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
			int pasId = newTic.getOrgId();
			tamponiList.setOrgId(passedId);
			tamponiList.buildListControlli(db, pasId, idC);
			context.getRequest().setAttribute("TamponiList", tamponiList);

			org.aspcfs.modules.sanzioni.base.TicketList sanzioniList = new org.aspcfs.modules.sanzioni.base.TicketList();
			int passId = newTic.getOrgId();
			sanzioniList.setOrgId(passedId);
			sanzioniList.buildListControlli(db, passId, idC,8);
			context.getRequest().setAttribute("SanzioniList", sanzioniList);

			org.aspcfs.modules.reati.base.TicketList reatiList = new org.aspcfs.modules.reati.base.TicketList();
			int passIdR = newTic.getOrgId();
			reatiList.setOrgId(passedId);
			reatiList.buildListControlli(db, passIdR, idC,8);
			context.getRequest().setAttribute("ReatiList", reatiList);

			org.aspcfs.modules.sequestri.base.TicketList seqList = new org.aspcfs.modules.sequestri.base.TicketList();
			int passIdS = newTic.getOrgId();
			seqList.setOrgId(passedId);
			seqList.buildListControlli(db, passIdS, idC,8);
			context.getRequest().setAttribute("SequestriList", seqList);

			org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
			int passIdN = newTic.getOrgId();
			nonCList.setOrgId(passedId);
			nonCList.buildListControlli(db, passIdN, idC);
			context.getRequest().setAttribute("NonCList", nonCList);

			org.aspcfs.checklist.base.AuditList audit = new org.aspcfs.checklist.base.AuditList();
			int AuditOrgId = newTic.getOrgId();
			String idT = newTic.getPaddedId();

			audit.buildListControlli(db, AuditOrgId, idT);
			Iterator<Audit> itera=audit.iterator();
			int punteggioChecklist=0;

			while(itera.hasNext())
			{
				Audit temp=itera.next();
				punteggioChecklist+=temp.getLivelloRischio();

			}
			context.getRequest().setAttribute("PunteggioCheckList", punteggioChecklist);
			context.getRequest().setAttribute("Audit", audit);

			//find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) 
			{
				return ("PermissionError");
			}
			

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

			LookupList categoriaRischioList2 = new LookupList(db, "lookup_org_catrischio");
			categoriaRischioList2.addItem(-1, "-- SELEZIONA VOCE --");
			categoriaRischioList2.removeElementByLevel(1);
			context.getRequest().setAttribute("OrgCategoriaRischioList2", categoriaRischioList2);

		
			//Load the ticket state

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);



			LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);


			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			newTic.setNumeroAudit(db);
			context.getRequest().setAttribute("TicketDetails", newTic);

		

			addRecentItem(context, newTic);
			// Load the organization for the header
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			addModuleBean(context, "View Accounts", "View Tickets");
			// Reset any pagedLists since this could be a new visit to this ticket
			deletePagedListInfo(context, "AccountTicketsFolderInfo");
			deletePagedListInfo(context, "AccountTicketDocumentListInfo");
			deletePagedListInfo(context, "AccountTicketTaskListInfo");
			deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
			
		}
		} catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		return null;

		
	}
	
	
	public String executeCommandUpdateLivello(ActionContext context) throws SQLException, ParseException {

		Connection 	db 		= null	;
		Timestamp 	date 	= null	;
		try
		{
			
		Audit 			thisAudit 	= 	(Audit) context.getFormBean()				;
		Organization 	organ 		= 	null										;
						db		 	= 	this.getConnection(context)					;
		String 			idC 		= 	context.getRequest().getParameter("idC")	;
		String 			altId 		= 	context.getRequest().getParameter("altId")	;
			
		SystemStatus systemStatus = this.getSystemStatus(context)	;
		
		Ticket newTic = new Ticket()					;
		newTic.setSystemStatus(systemStatus)			;
		newTic.queryRecord(db, Integer.parseInt(idC))	;
		
		  int passedId = newTic.getAltId();
	      
	      org.aspcfs.modules.followup.base.TicketList follList = new org.aspcfs.modules.followup.base.TicketList();
	      int passeId = newTic.getAltId();
	      follList.setAltId(passeId);
	      follList.buildListControlliAlt(db, passeId, idC,8);
	      context.getRequest().setAttribute("FollowupList", follList);
	      
	      org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
	   
	      tamponiList.setAltId(passedId);
	      tamponiList.buildListControlliAlt(db, passeId, idC);
	      context.getRequest().setAttribute("TamponiList", tamponiList);
	      
	      org.aspcfs.modules.sanzioni.base.TicketList sanzioniList = new org.aspcfs.modules.sanzioni.base.TicketList();
	      sanzioniList.setAltId(passedId);
	      sanzioniList.buildListControlliAlt(db, passedId, idC,8);
	      context.getRequest().setAttribute("SanzioniList", sanzioniList);
	      
	      
	      /**
	       * se il controllo non contiene campioni e tampo oppure
	       * se il controllo contiene campioni e tamponi e sono tutti chiusi 
	       * allora il controllo puo essere chiuso in maniera normale ; 
	       * altrimenti puo essere chiuso in maniera temporanea in quanto in attesa di esito
	       */
	  
	      
	   
	    	
	    		  newTic.setControllo_chiudibile(true);
	    	 
	  			UserBean user = (UserBean)context.getSession().getAttribute("User");

	  			ControlliUfficialiUtil.buildLookupTipoControlloUfficiale(db, context, systemStatus, user.getRoleId(), newTic.getSiteId(), Ticket.TIPO_OPU, null);

		
		thisAudit.setIdControllo(newTic.getPaddedId())								;
		
		Integer livello = thisAudit.getPunteggioTotaleChecklistInCU(db);
		organ = new Organization(db, Integer.parseInt(altId));
		int operatore_default = 1 ;
		int tipo_operatore = organ.getTipologia();
		int tipologiaOp = organ.getTipologia();

		logger.info("Aggiornamento categoria di rischio. Tipo Operatore "+tipo_operatore);
		
	ResultSet 	rs_operatore = db.prepareStatement("select distinct tipo_operatore from parametri_categorizzazzione_osa where tipo_operatore ="+tipo_operatore).executeQuery();
	if(!rs_operatore.next())
	{
		tipo_operatore = operatore_default;
	}
		
		
		String sql	=	"select * from get_categorizzazzione_osa(?,?,?)" ; 
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, tipo_operatore);
		pst.setTimestamp(2, newTic.getAssignedDate());
		pst.setInt(3, livello);
		ResultSet rs = pst.executeQuery();
		
		int categoria=0;
		if (rs.next())
		{
			categoria = rs.getInt("categoria_rischio");
			date=rs.getTimestamp("data_prossimo_controllo");
			String data = (new SimpleDateFormat("dd/MM/yyyy")).format(date);
			int intervallo_tempo = rs.getInt("intervallo_mesi");
		
		}
		
		
		
		/*
		if((livello==null) || (livello<1 && livello<1) )
		{
			categoria=3;
			date=(new Timestamp( System.currentTimeMillis() ) );
		}
		else
			if(livello >= 1 && livello <= 150)
			{
				categoria= 1 ;
				String data = (new SimpleDateFormat("dd/MM/yyyy")).format(org.aspcfs.utils.DateAudit.addMonth(newTic.getAssignedDate(),48)); 
				Date d = (new SimpleDateFormat("dd/MM/yyyy")).parse(data);
				date = new Timestamp(d.getTime());
			}
			else
				if(livello <=251 && livello >= 151)
				{
					categoria= 2 ;
					String data = (new SimpleDateFormat("dd/MM/yyyy")).format(org.aspcfs.utils.DateAudit.addMonth(newTic.getAssignedDate(),36)); 
					Date d = (new SimpleDateFormat("dd/MM/yyyy")).parse(data);
					date = new Timestamp(d.getTime());
				}
				else
					if(livello >= 251 && livello <= 350)
					{
						categoria= 3 ;
						String data = (new SimpleDateFormat("dd/MM/yyyy")).format(org.aspcfs.utils.DateAudit.addMonth(newTic.getAssignedDate(),24)); 
						Date d = (new SimpleDateFormat("dd/MM/yyyy")).parse(data);
						date = new Timestamp(d.getTime());
					}
					else
						if(livello >= 351 && livello <= 450)
						{
							categoria= 4 ;
							String data = (new SimpleDateFormat("dd/MM/yyyy")).format(org.aspcfs.utils.DateAudit.addMonth(newTic.getAssignedDate(),12)); 
							Date d = (new SimpleDateFormat("dd/MM/yyyy")).parse(data);
							date = new Timestamp(d.getTime());
						}
						else
							if(livello >= 451 )
							{
								categoria=5;
								String data = (new SimpleDateFormat("dd/MM/yyyy")).format(org.aspcfs.utils.DateAudit.addMonth(newTic.getAssignedDate(),6)); 
								Date d = (new SimpleDateFormat("dd/MM/yyyy")).parse(data);
								date = new Timestamp(d.getTime());
							}

		*/
		
		
		//AGGIORNO LA CATEGORIA IN IMPRESA CHE CONTERRa' SEMPRE QUELLA ATTUALE
		
		organ.updateCategoriaRischioSuap(db, categoria,date, Integer.parseInt(altId));


		
		if (tipologiaOp== 10 || tipologiaOp==20)
		{
			DbiBdu.aggiorna_categoria_rischio(context, organ, db, newTic,date);
		}
		
		
		
		
		
		
		
		
		
		context.getRequest().setAttribute("OrgDetails", organ);
		//AGGIORNO PUNTEGGIO , CATEGORIA , FLAG, DATAPROSSIMO CONTROLLO PER IL CONTROLLO UFFICIALE
		
		
		
		newTic.setCategoriaRischio(categoria)			;
		newTic.setPunteggio(livello)					;
		newTic.setDataProssimoControllo(date)			;
		newTic.updateCategoria(db)						;
		
		

		

		org.aspcfs.modules.reati.base.TicketList reatiList = new org.aspcfs.modules.reati.base.TicketList();
		int passIdR = newTic.getAltId();
		reatiList.setAltId(passedId);
		reatiList.buildListControlliAlt(db, passIdR, idC,8);
		context.getRequest().setAttribute("ReatiList", reatiList);

		org.aspcfs.modules.sequestri.base.TicketList seqList = new org.aspcfs.modules.sequestri.base.TicketList();
		int passIdS = newTic.getAltId();
		seqList.setAltId(passedId);
		seqList.buildListControlliAlt(db, passIdS, idC,8);
		context.getRequest().setAttribute("SequestriList", seqList);

		org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
		int passIdN = newTic.getAltId();
		nonCList.setAltId(passedId);
		nonCList.buildListControlliAlt(db, passIdN, idC);
		context.getRequest().setAttribute("NonCList", nonCList);

		org.aspcfs.checklist.base.AuditList audit = new org.aspcfs.checklist.base.AuditList();
		int AuditOrgId = newTic.getOrgId();
		String idT = newTic.getPaddedId();
		audit.setOrgId(AuditOrgId);

		audit.buildListControlli(db, AuditOrgId, idT);
		Iterator<Audit> itera=audit.iterator();
		int punteggioChecklist=0;

		while(itera.hasNext())
		{
			Audit temp=itera.next();
			punteggioChecklist+=temp.getLivelloRischio();

		}
		context.getRequest().setAttribute("PunteggioCheckList", punteggioChecklist);
		context.getRequest().setAttribute("Audit", audit);

		//find record permissions for portal users
		if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) 
		{
			return ("PermissionError");
		}
		

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

		LookupList categoriaRischioList2 = new LookupList(db, "lookup_org_catrischio");
		categoriaRischioList2.addItem(-1, "-- SELEZIONA VOCE --");
		categoriaRischioList2.removeElementByLevel(1);
		context.getRequest().setAttribute("OrgCategoriaRischioList2", categoriaRischioList2);

		
		
				LookupList SiteIdList = new LookupList(db, "lookup_site_id");
		SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
		SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
		context.getRequest().setAttribute("SiteIdList", SiteIdList);
		
		newTic.queryRecord(db, Integer.parseInt(idC))	;

		context.getRequest().setAttribute("TicketDetails", newTic);

	

		addRecentItem(context, newTic);
		// Load the organization for the header
		addModuleBean(context, "View Accounts", "View Tickets");
		// Reset any pagedLists since this could be a new visit to this ticket
		deletePagedListInfo(context, "AccountTicketsFolderInfo");
		deletePagedListInfo(context, "AccountTicketDocumentListInfo");
		deletePagedListInfo(context, "AccountTicketTaskListInfo");
		deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
		
		
		
		} 
		catch (Exception errorMessage)
		{
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}

		return null;
	}  

	

	
	 public String executeCommandITextReport(ActionContext context) {
	    if (!hasPermission(context, "accounts-accounts-audit-view")) {
	      return ("PermissionError");
	    }

	    String id = context.getRequest().getParameter("id");
	    Connection db = null;
	    Stabilimento org = null;
	    Audit audit = null;
	    CustomLookupList checklistType = null;
	    ArrayList<CustomLookupList> checklistList = null; 
	    ArrayList<AuditChecklist> auditChecklist = null;
	    ArrayList<AuditChecklistType> auditChecklistType = null;

	    try {
	      db = this.getConnection(context);

	      audit = new Audit(db, id);
	      org = new Stabilimento(db, audit.getIdStabilimento());
	      checklistType = new CustomLookupList();
	      checklistList = new ArrayList<CustomLookupList>();
	      auditChecklist = new ArrayList<AuditChecklist>();
	      auditChecklistType = new ArrayList<AuditChecklistType>();

	      int catRischioId=audit.getTipoChecklist();

	      checklistType.setTableName("checklist_type");
	      checklistType.addField("*");
	      checklistType.buildListByIdField(db, "catrischio_id", catRischioId,audit.isPrincipale());
	      
	      Iterator itr = checklistType.iterator();
	      while (itr.hasNext())
	      { 
	        CustomLookupElement thisElem = new CustomLookupElement(); 
	        thisElem = (CustomLookupElement) itr.next();
	        int checklistTypeId = Integer.parseInt(thisElem.getValue("code"));

	        CustomLookupList checklist = new CustomLookupList();
	        checklist.setTableName("checklist");
	        checklist.addField("*");
	        checklist.buildListByType(db, checklistTypeId);

	        if (!checklist.isEmpty()){
	        	 checklistList.add(checklist);
	        }
	      }
	      auditChecklist = AuditChecklist.queryRecord(db, audit.getId());
	      auditChecklistType = AuditChecklistType.queryRecord(db, audit.getId());


	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      this.freeConnection(context, db);
	    }
	    context.getRequest().setAttribute("Audit", audit);
	    context.getRequest().setAttribute("OrgDetails", org);
	    context.getRequest().setAttribute("checklistList", checklistList);
	    context.getRequest().setAttribute("typeList", checklistType);
	    context.getRequest().setAttribute("auditChecklist", auditChecklist);
	    context.getRequest().setAttribute("auditChecklistType", auditChecklistType);

	    context.getResponse().setContentType( "application/pdf" );
	    context.getResponse().setHeader("Content-Disposition", "attachment; filename=\"Report_Audit.pdf\"");

	    

	    return ("-none-");
	  }

	 	




	public String executeCommandModify(ActionContext context) {
	
		if (context.getParameter("isSalvata")!=null)
		{
			context.getRequest().setAttribute("isSalvata", "false");
			
		}
		else
		{
			context.getRequest().setAttribute("isSalvata", "true");
		}
		
		String 			id 		= "" ;//	context.getRequest().getParameter("id");
		if (context.getRequest().getAttribute("id")!= null)
			id =""+(Integer) context.getRequest().getAttribute("id");
		else
		{
			id 		= 	context.getRequest().getParameter("id");
		}
			
		Connection 		db 		= 	null	;
		Organization 	org 	= 	null	;
		Audit 			audit 	= 	null	;
		
		CustomLookupList 				checklistType 		= null	;
		ArrayList<CustomLookupList> 	checklistList 		= null	;	 
		ArrayList<AuditChecklist> 		auditChecklist 		= null	;
		ArrayList<AuditChecklistType> 	auditChecklistType 	= null	;

		try {

			db 					= this.getConnection(context)				;
			audit 				= new Audit(db, id)							;
			org 				= new Organization(audit.getIdStabilimento())	;
			checklistType 		= new CustomLookupList()					;
			checklistList 		= new ArrayList<CustomLookupList>()			;
			auditChecklist		= new ArrayList<AuditChecklist>()			;
			auditChecklistType 	= new ArrayList<AuditChecklistType>()		;
			
			String 	idControlloUfficiale 	= 	context.getRequest().getParameter("idControllo");
			if (idControlloUfficiale == null)
				idControlloUfficiale =(String) context.getRequest().getAttribute("idControllo");
			String 	idC 					= context.getRequest().getParameter("idC");
			if (idC == null)
				idC =(String) context.getRequest().getAttribute("idC");
			context.getRequest().setAttribute("idControllo",idControlloUfficiale);

			while(idC.startsWith("0"))
			{
				idC=idC.substring(1);
			}
			
			context.getRequest().setAttribute("idCon",idC);
			context.getRequest().setAttribute("idC",idC);
			
			SystemStatus systemStatus = this.getSystemStatus(context)	;
			
			Ticket newTic = new Ticket()					;
			newTic.setSystemStatus(systemStatus)			;
			newTic.queryRecord(db, Integer.parseInt(idC))	;
			
			context.getRequest().setAttribute( "TicketDetails" , newTic )	;

			org.aspcfs.checklist.base.AuditList auditL = new org.aspcfs.checklist.base.AuditList()	;
			
			int 		AuditOrgId 	= 	newTic.getOrgId()		;
			String 		idT 		= 	newTic.getPaddedId()	;
			audit.setOrgId(AuditOrgId)	;
			auditL.buildListControlli(db, AuditOrgId, idT)	;

			Iterator<Audit> itera= auditL.iterator()	;
			int punteggioChecklist=0	;
			
			while(itera.hasNext())
			{
				Audit temp			=	itera.next()				;
				punteggioChecklist	+=	temp.getLivelloRischio()	;

			}
			punteggioChecklist	=	punteggioChecklist - audit.getLivelloRischio()	;

			context.getRequest().setAttribute("punteggioCheckList", punteggioChecklist)	;

			org.aspcfs.modules.vigilanza.base.Ticket controlliList2 = new org.aspcfs.modules.vigilanza.base.Ticket();
			controlliList2.queryRecord(db, Integer.parseInt(idC));
			
			context.getRequest().setAttribute("ControlloUfficiale", controlliList2)	;
			LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio")	;
			categoriaRischioList.addItem(-1, "Nessuno")	;
			
			context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
			
			int catRischioId	=	audit.getTipoChecklist()	;   
			checklistType.setTableName("checklist_type")		;
			checklistType.addField("*")							;
			
			checklistType.buildListByIdField(db, "catrischio_id", catRischioId,audit.isPrincipale())			;
			
				Iterator itr = checklistType.iterator()	;
				while ( itr.hasNext() )
				{ 
					CustomLookupElement thisElem	 = 	new CustomLookupElement()		; 
					thisElem = (CustomLookupElement) itr.next()							;
					int checklistTypeId = Integer.parseInt(thisElem.getValue("code"))	;

					CustomLookupList checklist = new CustomLookupList()					;
					checklist.setTableName("checklist")									;
					checklist.addField("*")												;
					checklist.buildListByType(db, checklistTypeId)						;

					if (!checklist.isEmpty())
					{
						checklistList.add(checklist)		;
					}
				}
				auditChecklist = AuditChecklist.queryRecord(db, audit.getId())			;
				auditChecklistType = AuditChecklistType.queryRecord(db, audit.getId())	;

				
			
		} 

		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
			
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("OrgDetails", org)										;
		context.getRequest().setAttribute("Audit", audit)											;
		context.getRequest().setAttribute("return", context.getRequest().getParameter("return"))	;
		context.getRequest().setAttribute("checklistList", checklistList)							;
		context.getRequest().setAttribute("typeList", checklistType)								;
		context.getRequest().setAttribute("auditChecklist", auditChecklist)							;
		context.getRequest().setAttribute("auditChecklistType", auditChecklistType)					;
		return ("AccountsAuditModifyOK");

	}

	
public String executeCommandAdd(ActionContext context) {
	
	Connection db = null ;
	ArrayList< CustomLookupList > 							checklistList 	= 	null	; 
	CustomLookupList 										checklistType 	= 	null	;


	
	String 	orgId 						= context.getRequest().getParameter( "altId" )			;
	String 	idControlloUfficiale 		= context.getRequest().getParameter("idControllo")		;
	String 	idC 						= context.getRequest().getParameter("idC")				;
	String 	accountSize 				= context.getRequest().getParameter("accountSize")		;
	String	isPrincipale				= context.getRequest().getParameter("isPrincipale")		;
	boolean isFirstCheckList = false;
	context.getRequest().setAttribute("isPrincipale", isPrincipale);
	if(isPrincipale.equals("true"))
	{
		isFirstCheckList = true;
	}

	try 
	{
		db = this.getConnection(context);
		
		  idControlloUfficiale = (String.valueOf(idC));
		    while (idControlloUfficiale.length() < 6) 
		    {
		    	idControlloUfficiale = "0" + idControlloUfficiale;
		    }
		
		int catRischioId = Integer.parseInt(accountSize);
		
		Audit auditDet = new Audit () ;
		auditDet.setIdControllo(idControlloUfficiale);
		auditDet.setTipoChecklist(Integer.parseInt(accountSize));
		auditDet.setTipoChecklist(accountSize);
		auditDet.setOrgId(orgId);
		auditDet.setIdStabilimento(orgId);
		auditDet.setAltId(orgId);
		auditDet.setStato("Temporanea");
		auditDet.setNumeroRegistrazione( "" + auditDet.nextRegistrationNumber( db, auditDet.getOrgId() ) );
		ArrayList<Parameter> listaRisposte = new ArrayList<Parameter>();
		ArrayList<Parameter> listaCapitoli = new ArrayList<Parameter>();
		
		checklistType = new CustomLookupList();
		checklistType.setTableName("checklist_type");
		checklistType.addField("*");
		checklistType.buildListByIdField(db, "catrischio_id", catRischioId,isFirstCheckList);
		context.getRequest().setAttribute("TipoCheckList", catRischioId);
		checklistList = new ArrayList<CustomLookupList>();

		org.aspcfs.modules.vigilanza.base.Ticket controlloUff = new org.aspcfs.modules.vigilanza.base.Ticket(db, Integer.parseInt(idC));

		org.aspcfs.checklist.base.AuditList audit = new org.aspcfs.checklist.base.AuditList();
		int AuditOrgId = controlloUff.getAltId();
		String idControlloasString = controlloUff.getPaddedId();
		audit.setOrgId(AuditOrgId);

		audit.buildListControlli(db, AuditOrgId, idControlloasString);

		// VERIFICA SE STO APRENDO LA PRIMA CHECKLIST IN QUESTO CONTROLLO UFFICIALE
		

		Iterator<Audit> itera= audit.iterator();
		int punteggioChecklist=0;
		while(itera.hasNext())
		{
			Audit temp=itera.next();
			punteggioChecklist+=temp.getLivelloRischio();

		}
		
		context.getRequest().setAttribute("punteggioCheckList", punteggioChecklist);  
		org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
		controlliList.setAltId(AuditOrgId);
		int punteggioAccumulato = controlliList.buildListControlliUltimiAnniOpu(db, AuditOrgId,Integer.parseInt(idC));
		context.getRequest().setAttribute("punteggioUltimiAnni", punteggioAccumulato);

		auditDet.setPunteggioUltimiAnni(""+punteggioAccumulato);
		if(audit.size()==0)
		{
			context.getRequest().setAttribute("prima", "si") ;
			auditDet.setLivelloRischio(punteggioAccumulato);

		}
		else
		{
			auditDet.setLivelloRischio(0);
		}
		auditDet.setPrincipale(isFirstCheckList);
		org.aspcfs.modules.vigilanza.base.Ticket controlliList2 = new org.aspcfs.modules.vigilanza.base.Ticket();
		controlliList2.queryRecord(db, Integer.parseInt(idC));

		context.getRequest().setAttribute("ControlloUfficiale", controlliList2);
		Iterator itr = checklistType.iterator();
		while (itr.hasNext())
		{	Parameter p = new Parameter(); 
			
			CustomLookupElement thisElem = new CustomLookupElement(); 
			thisElem = (CustomLookupElement) itr.next();
			p.setId(Integer.parseInt(thisElem.getValue("code")));
			p.setValore("");
			listaCapitoli.add(p);
			
			int checklistTypeId = Integer.parseInt(thisElem.getValue("code"));

			CustomLookupList checklist = new CustomLookupList();
			checklist.setTableName("checklist");
			checklist.addField("*");
			checklist.buildListByType(db, checklistTypeId);
			if (!checklist.isEmpty())
			{
				checklistList.add(checklist);
				Iterator itr_risp = checklist.iterator();
				while (itr_risp.hasNext())
				{
					
					Parameter p_risp = new Parameter(); 
					CustomLookupElement thisElemRisp = new CustomLookupElement(); 
					thisElemRisp = (CustomLookupElement) itr_risp.next(); 
					p_risp.setId(Integer.parseInt(thisElemRisp.getValue("id")));
					listaRisposte.add(p_risp);
					
					
				}
				
			}
		}
		auditDet.setOrgId(-1);
		auditDet.insert_su_apertura(context,db, listaRisposte, listaCapitoli, punteggioAccumulato);

		context.getRequest().setAttribute("AuditDetail",auditDet);
		context.getRequest().setAttribute("idControllo",idControlloUfficiale);
		context.getRequest().setAttribute("idC",idC);
		context.getRequest().setAttribute("id",auditDet.getId());


	} 
	catch (Exception e) 
	{
		context.getRequest().setAttribute("Error", e)	;
		e.printStackTrace();
		return ("SystemError")	;
	} 
	finally 
	{
		context.getRequest().setAttribute("checklistList", checklistList);
		context.getRequest().setAttribute("typeList", checklistType);
		this.freeConnection(context, db);
	}
	return "" ;

	}


	

	


	public void setOrganization(ActionContext context, Connection db) throws SQLException {
		Organization thisOrganization = null;
		String orgId = context.getRequest().getParameter("orgId");
		thisOrganization = new Organization(db, Integer.parseInt(orgId));
		context.getRequest().setAttribute("OrgDetails", thisOrganization);
	}

	public String goToPage( int tipologia , String comando )
	{
		String toReturn = "" ;

		switch (tipologia)
		{
		case 1 : //TIPOLOGIA = 1 IMPRESE
		{
			if (comando.equals("Add"))
			{
				toReturn = "checklistImpreseAdd";
				break;
			}
			else
			{
				if(comando.equals("modify"))
				{
					toReturn = "checklistImpreseModify";
					break;
				}
				else
				{
					if(comando.equals("view"))
					{
						toReturn = "checklistImpreseDetails";
						break;
					}

				}
			}  
		}

		case 3 : //TIPOLOGIA = 3 STABILIMENTI
		{
			if (comando.equals("Add"))
			{
				toReturn = "checklistStabilimentiAdd";
				break;
			}
			else
			{
				if(comando.equals("modify"))
				{
					toReturn = "checklistStabilimentiModify";
					break;
				}
				else
				{
					if(comando.equals("view"))
					{
						toReturn = "checklistStabilimentiDetails";
						break;
					}

				}
			} 
		}

		} // END SWITCH

		return toReturn ;
	}
}
