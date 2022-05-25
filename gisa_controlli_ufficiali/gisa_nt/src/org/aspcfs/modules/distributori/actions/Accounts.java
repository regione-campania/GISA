/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.distributori.actions;

import com.daffodilwoods.daffodildb.server.sql99.ddl.schemadefinition.columnconstraint;
import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.distributori.base.Organization;
import org.aspcfs.modules.distributori.base.OrganizationList;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.actionplans.base.ActionItemWork;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.audit.base.Audit;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.ContactReport;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityReport;
import org.aspcfs.modules.troubletickets.base.TicketReport;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.LeggiFile;
import org.aspcfs.utils.RiepilogoImport;
import org.aspcfs.utils.web.*;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.component.Column;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesa.view.html.event.RowEvent;
import org.jmesa.view.html.renderer.HtmlCellRenderer;
import org.jmesa.worksheet.UniqueProperty;
import org.jmesa.worksheet.Worksheet;
import org.jmesa.worksheet.WorksheetCallbackHandler;
import org.jmesa.worksheet.WorksheetColumn;
import org.jmesa.worksheet.WorksheetRow;
import org.jmesa.worksheet.WorksheetRowImpl;
import org.jmesa.worksheet.WorksheetUtils;
import org.jmesa.worksheet.editor.CheckboxWorksheetEditor;
import org.jmesa.worksheet.editor.HtmlWorksheetEditor;
import org.jmesa.worksheet.editor.WorksheetEditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

/**
 *  Action for the Account module
 *
 * @author     chris
 * @created    August 15, 2001
 * @version    $Id: Accounts.java 18261 2007-01-08 14:45:02Z matt $
 */
public final class Accounts extends CFSModule {

	/** 
	 *  Default: not used
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */ 
	public String executeCommandDefault(ActionContext context) {
		String comand=context.getRequest().getParameter("command");
		if(comand!=null)
			if(comand.equalsIgnoreCase("list"))
				return executeCommandList( context );

		return executeCommandDashboard(context);
	}


	public String executeCommandScegliD(ActionContext context) {
		if (!hasPermission(context, "altri-view")) {
			if (!hasPermission(context, "altri-view")) {
				return ("PermissionError");
			}
			//Bypass dashboard and search form for portal users
			/*if (isPortalUser(context)) {
	        return (executeCommandSearch(context));
	      }
	      return (executeCommandSearchForm(context));*/
		}
		addModuleBean(context, "Dashboard", "Dashboard");
		CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
				"AccountsCalendarInfo");
		if (calendarInfo == null) {
			calendarInfo = new CalendarBean(
					this.getUser(context, this.getUserId(context)).getLocale());
			calendarInfo.addAlertType(
					"Accounts", "org.aspcfs.modules.abusivismi.base.AccountsListScheduledActions", "Accounts");
			calendarInfo.setCalendarDetailsView("Accounts");
			context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
		}

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		//this is how we get the multiple-level heirarchy...recursive function.
		User thisRec = thisUser.getUserRecord();

		UserList shortChildList = thisRec.getShortChildList();
		UserList newUserList = thisRec.getFullChildList(
				shortChildList, new UserList());

		newUserList.setMyId(getUserId(context));
		newUserList.setMyValue(
				thisUser.getUserRecord().getContact().getNameLastFirst());
		newUserList.setIncludeMe(true);

		newUserList.setJsEvent(
				"onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
		HtmlSelect userListSelect = newUserList.getHtmlSelectObj(
				"userId", getUserId(context));
		userListSelect.addAttribute("id", "userId");
		context.getRequest().setAttribute("Return", "Accounts");
		context.getRequest().setAttribute("NewUserList", userListSelect);
		return ("ScegliDOK");
	}


	public String executeCommandList( ActionContext context )
	{


		SystemStatus 	systemStatus	= this.getSystemStatus(context);
		Connection		db				= null;
		RowSetDynaClass	rsdc			= null;

		try
		{
			db = getConnection(context);

			String sql = "select distributori_automatici.*,lookup_site_id.description as asl ,case when distributori_automatici.org_id >0 then 'pregresso'::text when distributori_automatici.id_stabilimento>0 then 'nuovo operatore' end as tipo_operatore, aslstab.description as asl_stab,organization.name, opu_operatore.ragione_sociale, " +
					"organization.org_id,opu_stabilimento.id as id_stab " +
					",lookup_tipo_distributore.description as descrizione,asl1.code,asl1.description " +  
					"from distributori_automatici  " +
					"left join comuni on distributori_automatici.comune=comuni.comune  " +
					"left join lookup_site_id asl1 on asl1.codiceistat =comuni.codiceistatasl   " +
					"left join lookup_tipo_distributore on alimenti_distribuiti=lookup_tipo_distributore.code " +
					"left join organization on distributori_automatici.org_id=organization.org_id  " +
					"left join opu_stabilimento on distributori_automatici.id_stabilimento=opu_stabilimento.id " +
					"left join lookup_site_id aslstab on aslstab.code = opu_stabilimento.id_asl " +
					"left join opu_operatore on opu_operatore.id = opu_stabilimento.id_operatore " +
					"left join lookup_site_id on distributori_automatici.org_id=organization.org_id and organization.site_id=lookup_site_id.code" ;
			UserBean utenteConnesso=(UserBean) context.getSession().getAttribute("User");
			PreparedStatement	stat=null;

			stat	= db.prepareStatement( sql);






			ResultSet			rs		= stat.executeQuery();

			int siteid=utenteConnesso.getSiteId();
			ArrayList<Distrubutore> listaDistributori=new ArrayList<Distrubutore>();
			while(rs.next()){
				String matricola=rs.getString("matricola");
				String comune=rs.getString("comune");

				int idAslAppartenenzaComune=-1;
				String descrAslComune="";

				idAslAppartenenzaComune=rs.getInt("code");
				descrAslComune=rs.getString("description");
				if(idAslAppartenenzaComune==-1)
				{
					idAslAppartenenzaComune = 16;
					descrAslComune = "Fuori Asl";
				}

				int org_id=rs.getInt("org_id");
				if (org_id <=0)
					org_id =rs.getInt("id_stab");

				int id=rs.getInt("id");
				String provincia=rs.getString("provincia");
				String latitudine=rs.getString("latitudine");
				String longitudine=rs.getString("longitudine");
				String cap=rs.getString("cap");
				String note=rs.getString("note");
				String indirizzo=rs.getString("indirizzo");
				Date data=rs.getDate("data");
				String description=rs.getString("descrizione");
				int alimentiDstribuiti=rs.getInt("alimenti_distribuiti");
				String ubicazione=rs.getString("ubicazione");
				Distrubutore dist=new Distrubutore(matricola,comune,indirizzo,cap,provincia,latitudine,longitudine,note,data,alimentiDstribuiti,ubicazione);
				dist.setDescrizioneTipoAlimenti(description);
				dist.setOrg_id(org_id);
				dist.setAslMacchinetta(idAslAppartenenzaComune);
				String asl=rs.getString("asl");
				if (asl == null || "".equals(asl))
					asl=rs.getString("asl_stab");
				dist.setNomeImpresa(rs.getString("name"));
				if (dist.getNomeImpresa()==null || ("").equals(dist.getNomeImpresa()))
				{
					dist.setNomeImpresa(rs.getString("ragione_sociale"));
					dist.setTipoOperatoreOpu(1);
				}
				else
				{
					dist.setTipoOperatoreOpu(0);
				}
				dist.setId(id);
				dist.setAslMacchinettaDesc(descrAslComune);
				dist.setAsl(asl);
				dist.setTipo_operatore(rs.getString("tipo_operatore"));
				//if(siteid==-1){
				//listaDistributori.add(dist);
				//}
				//else{

				//  if(idAslAppartenenzaComune==siteid){
				listaDistributori.add(dist);

				//		}
				//}
			}



			TableFacade tf = TableFacadeFactory.createTableFacade("15", context.getRequest());

			Worksheet worksheet = tf.getWorksheet();

			tf.setItems(listaDistributori);

			tf.setColumnProperties( 
					"matricola", "dataInst","ubicazione", "indirizzo", "comune", "provincia",
					"cap","asl","aslMacchinettaDesc","descrizioneTipoAlimenti",  "note","nomeImpresa","tipo_operatore"
					);
			tf.setStateAttr("restore");					
			HtmlRow row = (HtmlRow) tf.getTable().getRow();
			row.setUniqueProperty("matricola"); // the unique worksheet properties to identify the row





			tf.getTable().getRow().getColumn( "matricola" ).setTitle( "matricola" );
			tf.getTable().getRow().getColumn( "dataInst" ).setTitle( "data Installazione" );



			tf.getTable().getRow().getColumn( "comune" ).setTitle( "comune" );
			tf.getTable().getRow().getColumn( "provincia" ).setTitle( "provincia" );
			tf.getTable().getRow().getColumn( "indirizzo" ).setTitle( "indirizzo" );
			tf.getTable().getRow().getColumn( "cap" ).setTitle( "cap" );

			tf.getTable().getRow().getColumn( "descrizioneTipoAlimenti" ).setTitle( "Alimento Distribuito" );
			tf.getTable().getRow().getColumn( "note" ).setTitle( "note" );
			tf.getTable().getRow().getColumn( "ubicazione" ).setTitle( "Ubicazione" );
			tf.getTable().getRow().getColumn( "nomeImpresa" ).setTitle( "Nome Impresa" );
			tf.getTable().getRow().getColumn( "asl" ).setTitle( "ASL Impresa" );
			tf.getTable().getRow().getColumn( "aslMacchinettaDesc" ).setTitle( "ASL Distributore" );
			tf.getTable().getRow().getColumn( "tipo_operatore" ).setTitle( "Tipo Impresa" );






			Limit limit = tf.getLimit();
			if(! limit.isExported() )
			{



				HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("matricola");
				cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("tipo_operatore");
				cg.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
				cg.setFilterable( true );
				//cg.setFilterable( false );
				cg.setWidth("55");
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("dataInst");
				cg.setFilterable( true );

				/*cg.getCellRenderer().setCellEditor( 
	  	        		new CellEditor()
	  	        		{	
	  	        			public Object getValue(Object item, String property, int rowCount)
	  	        			{



	  	        				String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
	  	        				String	data= (String) (new HtmlCellEditor()).getValue(item, "dataInst", rowCount);

	  	        				String dataForm = "";
	  	        				if(data!=null)
	  	        				 dataForm = data.substring(8,10)+"-"+data.substring(5,7)+"-"+data.substring(0, 4);



	  	        				return ""+dataForm
	  	        				;

	  	        			}
	  	        		}

	  	        	);*/

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("comune");
				cg.setFilterable( true );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("provincia");
				cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("indirizzo");
				cg.setFilterable( true );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("cap");
				cg.setFilterable( false );



				cg = (HtmlColumn) tf.getTable().getRow().getColumn("descrizioneTipoAlimenti");
				cg.setFilterable( false );
				cg.setEditable(false);

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("note");
				cg.setFilterable( false );

				cg = (HtmlColumn) tf.getTable().getRow().getColumn("nomeImpresa");
				cg.setFilterable( true );

				cg.getCellRenderer().setCellEditor( 
						new CellEditor()
						{	
							public Object getValue(Object item, String property, int rowCount)
							{



								String temp	= (String) (new HtmlCellEditor()).getValue(item, property, rowCount);
								String id	= (String) (new HtmlCellEditor()).getValue(item, "nomeImpresa", rowCount);
								String org_id=(String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
								String val=(String)(new HtmlCellEditor()).getValue(item, "id", rowCount);
								String tipo	= (String) (new HtmlCellEditor()).getValue(item, "tipo_operatore", rowCount);


								if ("pregresso".equalsIgnoreCase(tipo))
									return "<a href='Distributori.do?command=Details&orgId="+org_id+"&id="+val+"'>"+id+"</a>";
								else
									return "<a href='Distributori.do?command=DetailsOpu&stabId="+org_id+"&id="+val+"'>"+id+"</a>";

							}
						}

						);



				cg = (HtmlColumn) tf.getTable().getRow().getColumn("asl");
				cg.setFilterable( true );
				cg = (HtmlColumn) tf.getTable().getRow().getColumn("aslMacchinettaDesc");
				cg.setFilterable( true );


			}



			String tabella = tf.render();
			context.getRequest().setAttribute( "tabella", tabella );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		finally
		{
			this.freeConnection(context, db);
			System.gc();
		}

		return "ListOKMacchinette";
	}


	public String executeCommandInsertDistributori(ActionContext context) {

		/* if (!hasPermission(context, "distributori-distributori-upload")) {
	      return ("PermissionError");
	    }
		 */
		context.getSession().setAttribute("orgIdDistributore",context.getRequest().getParameter("id")) ;

		return ("Upload");

	}

	public String executeCommandAllImportRecords(ActionContext context){
		Connection db = null;

		try {

			db = getConnection(context);
			String idorg=	context.getRequest().getParameter("orgid");

			int orgid=Integer.parseInt(idorg);
			RiepilogoImport rImport = new RiepilogoImport();
			rImport.buildListDistributori(db,orgid);

			context.getRequest().setAttribute("allRecords", rImport.getAllRecord());

		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("DistributoriUploadListOK");

	}


	public String executeCommandUploadDoc(ActionContext context){
		HttpServletRequest req = context.getRequest();
		String action = req.getParameter("action");
		Connection db = null;

		try {

			db = getConnection(context);
			MultipartRequest multi = null;
			if (action==null){
				int maxUploadSize = 50000000;
				multi = new MultipartRequest( req, ".", maxUploadSize );
			}



			String orgId=multi.getParameter("orgId");
			int org_Id=Integer.parseInt(orgId);

			File myFileT = multi.getFile("file1"); 
			FileInputStream fiStream=new FileInputStream(myFileT);
			//BufferedReader input = new BufferedReader(new FileReader(myFileT));


			BufferedReader input = new BufferedReader(new FileReader(myFileT));

			String logPath = getWebContentPath(context, "logdistributori");

			String pathDownloadLog = LeggiFile.leggiCampiDistributori(context, db,myFileT,logPath, getUserId(context),org_Id);

			String[] data = pathDownloadLog.split("[ _ ]");
			String dataOperazione = data[0].trim()+"/"+data[1].trim()+"/"+data[2].trim();

			RiepilogoImport rImport = new RiepilogoImport( dataOperazione, pathDownloadLog );
			rImport.insertDistributore(db,org_Id);

			context.getRequest().setAttribute("pathLog", pathDownloadLog);
			context.getRequest().setAttribute("orgId", orgId);

		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("DistributoriUploadSaveOK");

	}





	/*aggiunto da d.dauria*/
	public String executeCommandViewSanzioni(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-sanzioni-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.sanzioni.base.TicketList ticList = new org.aspcfs.modules.sanzioni.base.TicketList();
		Organization newOrg = null;
		//Process request parameters
		int passedId = Integer.parseInt(
				context.getRequest().getParameter("orgId"));

		//Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(
				context, "AccountTicketInfo", "t.entered", "desc");
		ticketListInfo.setLink(
				"Accounts.do?command=ViewSanzioni&orgId=" + passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			//find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, passedId)) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, passedId);
			ticList.setOrgId(passedId);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			ticList.buildList(db);
			context.getRequest().setAttribute("TicList", ticList);
			context.getRequest().setAttribute("OrgDetails", newOrg);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewSanzioni");
	}
	/*fine delle modifiche*/

	public String executeCommandViewCampioni(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-campioni-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
		Organization newOrg = null;
		//Process request parameters
		int passedId = Integer.parseInt(
				context.getRequest().getParameter("orgId"));

		//Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(
				context, "AccountTicketInfo", "t.entered", "desc");
		ticketListInfo.setLink(
				"Distributori.do?command=ViewCampioni&orgId=" + passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			//find record permissions for portal users

			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
			TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
			EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			if (!isRecordAccessPermitted(context, db, passedId)) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, passedId);
			ticList.setOrgId(passedId);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			ticList.buildList(db);
			context.getRequest().setAttribute("TicList", ticList);
			context.getRequest().setAttribute("OrgDetails", newOrg);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewCampioni");
	}

	public String executeCommandViewVigilanza(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-vigilanza-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
		Organization newOrg = null;
		//Process request parameters
		int passedId = Integer.parseInt(
				context.getRequest().getParameter("orgId"));

		int passedIdMacchinetta = Integer.parseInt(
				context.getRequest().getParameter("id"));



		//Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(
				context, "AccountTicketInfo", "t.entered", "desc");
		ticketListInfo.setLink(
				"Distributori.do?command=ViewVigilanza&orgId=" + passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			//find record permissions for portal users

			LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
			AuditTipo.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AuditTipo", AuditTipo);

			LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
			TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAudit", TipoAudit);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
			EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			/* if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }*/
			newOrg = new Organization(db, passedId);
			ticList.setOrgId(passedId);
			ticList.setIdMacchinetta(passedIdMacchinetta);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			ticList.buildListMacchinette(db);
			context.getRequest().setAttribute("id", context.getRequest().getParameter("id"));
			int idDistributore=-1;
			if(context.getRequest().getParameter("id")!=null)
				idDistributore=Integer.parseInt(context.getRequest().getParameter("id"));

			Distrubutore distributore=new Distrubutore();
			if(idDistributore!=-1){

				distributore= distributore.loadDistributore(newOrg.getOrgId(), idDistributore, db);
				context.getRequest().setAttribute("NewDistributore",distributore);
				context.getRequest().setAttribute("id",idDistributore);

			}
			if(context.getRequest().getParameter("asl")!=null)
				context.getRequest().setAttribute("aslMacchinetta", context.getRequest().getParameter("asl"));
			else

				context.getRequest().setAttribute("aslMacchinetta", context.getRequest().getParameter("aslMacchinetta"));

			context.getRequest().setAttribute("TicList", ticList);
			context.getRequest().setAttribute("OrgDetails", newOrg);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewVigilanza");
	}



	/**
	 *  Reports: Displays a list of previously generated reports with
	 *  view/delete/download options.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandReports(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-reports-view")) {
			return ("PermissionError");
		}
		//Set the menu: the user is in the Reports module
		addModuleBean(context, "Reports", "ViewReports");
		//Retrieve the paged list that will be used for paging through reports
		PagedListInfo rptListInfo = this.getPagedListInfo(context, "RptListInfo");
		rptListInfo.setLink("Distributori.do?command=Reports");
		//Prepare the file list for accounts
		FileItemList files = new FileItemList();
		files.setLinkModuleId(Constants.DOCUMENTS_ACCOUNTS_REPORTS);
		files.setPagedListInfo(rptListInfo);
		//Check the combo box value from the report list for filtering reports
		if ("all".equals(rptListInfo.getListView())) {
			//Show only the reports that this user or someone below this user created
			files.setOwnerIdRange(this.getUserRange(context));
		} else {
			//Show only this user's reports
			files.setOwner(this.getUserId(context));
		}
		Connection db = null;
		try {
			//Get a connection from the connection pool for this user
			db = this.getConnection(context);
			//Generate the list of files based on the above criteria
			files.buildList(db);
			context.getRequest().setAttribute("FileList", files);
		} catch (Exception errorMessage) {
			//An error occurred, go to generic error message page
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			//Always free the database connection
			this.freeConnection(context, db);
		}
		return ("ReportsOK");
	}


	/**
	 *  DownloadCSVReport: Sends a copy of the CSV report to the user's local
	 *  machine
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandDownloadCSVReport(ActionContext context) {
		if (!(hasPermission(context, "distributori-distributori-reports-view"))) {
			return ("PermissionError");
		}
		Exception errorMessage = null;
		String itemId = (String) context.getRequest().getParameter("fid");
		FileItem thisItem = null;
		Connection db = null;
		try {
			db = getConnection(context);
			thisItem = new FileItem(
					db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_ACCOUNTS_REPORTS);
		} catch (Exception e) {
			errorMessage = e;
		} finally {
			this.freeConnection(context, db);
		}
		//Start the download
		try {
			FileItem itemToDownload = null;
			itemToDownload = thisItem;
			//itemToDownload.setEnteredBy(this.getUserId(context));
			String filePath = this.getPath(context, "distributori-reports") + getDatePath(itemToDownload.getEntered()) + itemToDownload.getFilename() + ".csv";
			FileDownload fileDownload = new FileDownload();
			fileDownload.setFullPath(filePath);
			fileDownload.setDisplayName(itemToDownload.getClientFilename());
			if (fileDownload.fileExists()) {
				fileDownload.sendFile(context);
				//Get a db connection now that the download is complete
				db = getConnection(context);
				itemToDownload.updateCounter(db);
			} else {
				System.err.println(
						"distributori-> Trying to send a file that does not exist");
			}
		} catch (java.net.SocketException se) {
			//User either canceled the download or lost connection
		} catch (Exception e) {
			errorMessage = e;
			System.out.println(e.toString());
		} finally {
			this.freeConnection(context, db);
		}
		if (errorMessage == null) {
			return ("-none-");
		} else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}


	/**
	 *  ShowReportHtml: Displays a preview of the selected report in HTML format
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandShowReportHtml(ActionContext context) {
		if (!(hasPermission(context, "distributori-distributori-reports-view"))) {
			return ("PermissionError");
		}
		FileItem thisItem = null;
		String itemId = (String) context.getRequest().getParameter("fid");
		Connection db = null;
		try {
			db = getConnection(context);
			thisItem = new FileItem(
					db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_ACCOUNTS_REPORTS);
			String filePath = this.getPath(context, "distributori-reports") + getDatePath(
					thisItem.getEntered()) + thisItem.getFilename() + ".html";
			String textToShow = includeFile(filePath);
			context.getRequest().setAttribute("ReportText", textToShow);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("ReportHtmlOK");
	}


	/**
	 *  GenerateForm: Displays the form that allows the user to select criteria
	 *  and specify information for a new Accounts report
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandGenerateForm(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-reports-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		CustomFieldCategoryList thisList = new CustomFieldCategoryList();
		thisList.setLinkModuleId(Constants.ACCOUNTS);
		thisList.setIncludeEnabled(Constants.TRUE);
		thisList.setIncludeScheduled(Constants.TRUE);
		thisList.setAllSelectOption(true);
		thisList.setBuildResources(false);
		try {
			db = getConnection(context);
			thisList.buildList(db);
			context.getRequest().setAttribute("CategoryList", thisList);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Reports", "Generate new");
		return ("GenerateFormOK");
	}



	/**
	 *  Search: Displays the Account search form
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandSearchForm(ActionContext context) {
		if (!(hasPermission(context, "distributori-distributori-view"))) {
			return ("PermissionError");
		}

		/* if (!(hasPermission(context, "request-view"))) {
        return ("PermissionError");
      }
		 */
		//Bypass search form for portal users
		if (isPortalUser(context)) {
			return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);



			/*    LookupList llist=new LookupList(db,"lookup_stabilimenti_types");
      llist.addItem(-1, "-nessuno-");
      context.getRequest().setAttribute("llist", llist);
			 */


			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteList", siteList);


			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			//tabella modificata

			//reset the offset and current letter of the paged list in order to make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(
					context, "SearchOrgListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);
			if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeContactCountry"))) {
				StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeContactCountry"));
				if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
					HashMap map = new HashMap();
					map.put((String)orgListInfo.getSearchOptionValue("searchcodeContactCountry"), (String)orgListInfo.getSearchOptionValue("searchContactOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("ContactStateSelect", stateSelect);
			}
			if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeAccountCountry"))) {
				StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeAccountCountry"));
				if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
					HashMap map = new HashMap();
					map.put((String)orgListInfo.getSearchOptionValue("searchcodeAccountCountry"), (String)orgListInfo.getSearchOptionValue("searchAccountOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("AccountStateSelect", stateSelect);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");
		return ("SearchOK");
	}


	/**
	 *  Add: Displays the form used for adding a new Account
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);


			LookupList tipoDitributore = new LookupList(db, "lookup_tipo_distributore");
			tipoDitributore.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoDitributore", tipoDitributore);




			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);



			Organization newOrg = (Organization) context.getFormBean();
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
			stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
			context.getRequest().setAttribute("StateSelect", stateSelect);
			if (newOrg.getEnteredBy() != -1) {

				context.getRequest().setAttribute("OrgDetails", newOrg);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));
		//if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		return getReturn(context, "Add");
	}




	public String executeCommandDetailsOpu(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-view")) {
			return ("PermissionError");
		}


		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newOrg = null;
		try {
			String temporgId = context.getRequest().getParameter("stabId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("stabId");
				if (temporgId == null)
					temporgId = context.getRequest().getParameter("stabId");
			}
			int tempid = Integer.parseInt(temporgId);
			db = this.getConnection(context);
			/* if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
        return ("PermissionError");
      }*/
			newOrg = new Stabilimento(db, tempid);
			ArrayList<Distrubutore> l = new  ArrayList<Distrubutore>();
			newOrg.setListaDistributori(l);

			//check whether or not the owner is an active User

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);

			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);





			LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);


			LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);

			org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
			int AuditOrgId = newOrg.getOrgId();
			audit.setOrgId(AuditOrgId);


			Distrubutore distributore=new Distrubutore();
			int idDistributore=-1;
			if(context.getRequest().getParameter("id")!=null)
				idDistributore=Integer.parseInt(context.getRequest().getParameter("id"));


			if(idDistributore!=-1){

				distributore= distributore.loadDistributoreOpu(newOrg.getIdStabilimento(), idDistributore, db);
				context.getRequest().setAttribute("NewDistributore",distributore);
				context.getRequest().setAttribute("id",idDistributore);

			}





		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");


		if (action != null && action.equals("modify")) {
			//If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("DetailsOKOpu");
		} else {
			//If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);

			return getReturn(context, "Details") + "Opu";
		}
	}

	/**
	 *  Details: Displays all details relating to the selected Account. The user
	 *  can also goto a modify page from this form or delete the Account entirely
	 *  from the database
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */

	Connection db = null;
	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-view")) {
			return ("PermissionError");
		}

		db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		org.aspcfs.modules.accounts.base.Organization newOrg = null;
		try {
			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);
			db = this.getConnection(context);
			/* if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
        return ("PermissionError");
      }*/
			newOrg = new org.aspcfs.modules.accounts.base.Organization(db, tempid);

			//Caricamento Diffide
			Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));




			//check whether or not the owner is an active User

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);

			LookupList IstatList = new LookupList(db, "lookup_codistat");
			IstatList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("IstatList", IstatList);





			LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);

			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);


			LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
			categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);

			org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
			int AuditOrgId = newOrg.getOrgId();
			audit.setOrgId(AuditOrgId);


			Distrubutore distributore=new Distrubutore();
			int idDistributore=-1;
			if(context.getRequest().getParameter("id")!=null)
				idDistributore=Integer.parseInt(context.getRequest().getParameter("id"));


			if(idDistributore!=-1){

				distributore= distributore.loadDistributore(newOrg.getOrgId(), idDistributore, db);
				context.getRequest().setAttribute("NewDistributore",distributore);
				context.getRequest().setAttribute("id",idDistributore);

			}







			/*
     // audit.buildList(db);

      if( (audit.size() - 1)>=0){

      context.getRequest().setAttribute("Audit",audit.get( audit.size() - 1) );
      }*/
			if(newOrg.getVoltura()){
				org.aspcfs.modules.cessazionevariazione.base.Ticket voltura = new org.aspcfs.modules.cessazionevariazione.base.Ticket();
				voltura.setId(Integer.parseInt(newOrg.getIdVoltura(db, newOrg.getOrgId())));
				voltura.queryRecord(db, Integer.parseInt(newOrg.getIdVoltura(db, newOrg.getOrgId())));
				if( (voltura!=null)){

					context.getRequest().setAttribute("Voltura", voltura);
				}}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");



		if (action != null && action.equals("modify")) {
			//If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("DetailsOK");
		} else {
			//If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);

			return getReturn(context, "Details");
		}
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandDashboard(ActionContext context) {
		if (!hasPermission(context, "distributori-dashboard-view")) {
			if (!hasPermission(context, "distributori-distributori-view")) {
				return ("PermissionError");
			}
			//Bypass dashboard and search form for portal users
			if (isPortalUser(context)) {
				return (executeCommandSearch(context));
			}
			return (executeCommandSearchForm(context));
		}
		addModuleBean(context, "Dashboard", "Dashboard");
		CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
				"AccountsCalendarInfo");
		if (calendarInfo == null) {
			calendarInfo = new CalendarBean(
					this.getUser(context, this.getUserId(context)).getLocale());
			calendarInfo.addAlertType(
					"Accounts", "org.aspcfs.modules.distributori.base.AccountsListScheduledActions", "Accounts");
			calendarInfo.setCalendarDetailsView("Accounts");
			context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
		}
		Connection db = null;


		try {
			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		//this is how we get the multiple-level heirarchy...recursive function.
		User thisRec = thisUser.getUserRecord();

		UserList shortChildList = thisRec.getShortChildList();
		UserList newUserList = thisRec.getFullChildList(
				shortChildList, new UserList());

		newUserList.setMyId(getUserId(context));
		newUserList.setMyValue(
				thisUser.getUserRecord().getContact().getNameLastFirst());
		newUserList.setIncludeMe(true);

		newUserList.setJsEvent(
				"onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
		HtmlSelect userListSelect = newUserList.getHtmlSelectObj(
				"userId", getUserId(context));
		userListSelect.addAttribute("id", "userId");
		context.getRequest().setAttribute("Return", "Accounts");
		context.getRequest().setAttribute("NewUserList", userListSelect);
		return ("DashboardOK");
	}

	public String executeCommandDashboardScelta(ActionContext context) {
		if (!hasPermission(context, "altri-view")) {
			if (!hasPermission(context, "altri-view")) {
				return ("PermissionError");
			}
			//Bypass dashboard and search form for portal users
			/*if (isPortalUser(context)) {
	        return (executeCommandSearch(context));
	      }
	      return (executeCommandSearchForm(context));*/
		}
		addModuleBean(context, "Dashboard", "Dashboard");
		CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
				"AccountsCalendarInfo");
		if (calendarInfo == null) {
			calendarInfo = new CalendarBean(
					this.getUser(context, this.getUserId(context)).getLocale());
			calendarInfo.addAlertType(
					"Accounts", "org.aspcfs.modules.distributori.base.AccountsListScheduledActions", "Accounts");
			calendarInfo.setCalendarDetailsView("Accounts");
			context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
		}

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		//this is how we get the multiple-level heirarchy...recursive function.
		User thisRec = thisUser.getUserRecord();

		UserList shortChildList = thisRec.getShortChildList();
		UserList newUserList = thisRec.getFullChildList(
				shortChildList, new UserList());

		newUserList.setMyId(getUserId(context));
		newUserList.setMyValue(
				thisUser.getUserRecord().getContact().getNameLastFirst());
		newUserList.setIncludeMe(true);

		newUserList.setJsEvent(
				"onChange = \"javascript:fillFrame('calendar','MyCFS.do?command=MonthView&source=Calendar&userId='+document.getElementById('userId').value + '&return=Accounts'); javascript:fillFrame('calendardetails','MyCFS.do?command=Alerts&source=CalendarDetails&userId='+document.getElementById('userId').value + '&return=Accounts');javascript:changeDivContent('userName','Scheduled Actions for ' + document.getElementById('userId').options[document.getElementById('userId').selectedIndex].firstChild.nodeValue);\"");
		HtmlSelect userListSelect = newUserList.getHtmlSelectObj(
				"userId", getUserId(context));
		userListSelect.addAttribute("id", "userId");
		context.getRequest().setAttribute("Return", "Accounts");
		context.getRequest().setAttribute("NewUserList", userListSelect);
		return ("DashboardSceltaOK");
	}


	/**
	 *  Search Accounts
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandSearch(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-view")) {
			return ("PermissionError");
		}



		String source = (String) context.getRequest().getParameter("source");
		String partitaIva = (String) context.getRequest().getParameter("searchPartitaIva");
		String codiceF = (String) context.getRequest().getParameter("codiceFiscale");

		String cognomeR = (String) context.getRequest().getParameter("searchCognomeRappresentante");
		String nomeR = (String) context.getRequest().getParameter("searchNomeRappresentante");
		String name= context.getRequest().getParameter("searchAccountName");
		OrganizationList organizationList = new OrganizationList();
		organizationList.setTipologia(11);


		if(!"".equals(partitaIva) && partitaIva!=null) {
			organizationList.setPartitaIva(partitaIva);
		}
		if(!"".equals(codiceF) && codiceF != null) {
			organizationList.setCodiceFiscale(codiceF);
		}
		if(!"".equals(cognomeR) && cognomeR != null) {
			organizationList.setCognomeRappresentante(cognomeR);
		}
		if(!"".equals(nomeR) && nomeR != null) {
			organizationList.setNomeRappresentante(nomeR);
		}

		if(!"".equals(nomeR) && nomeR != null) {
			organizationList.setNomeRappresentante(nomeR);
		}


		if(!"".equals(name) && name != null){

			organizationList.setName(context.getRequest().getParameter("searchAccountName"));
		}
		//Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(
				context, "SearchOrgListInfo");
		searchListInfo.setListView("all");
		searchListInfo.setLink("Distributori.do?command=Search");
		SystemStatus systemStatus = this.getSystemStatus(context);
		//Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

			//For portal usr set source as 'searchForm' explicitly since
			//the search form is bypassed.
			//temporary solution for page redirection for portal user.
			if (isPortalUser(context)) {
				organizationList.setOrgSiteId(this.getUserSiteId(context));
				source = "searchForm";
			}
			organizationList.setTipologia(11);
			//return if no criteria is selected
			if ((searchListInfo.getListView() == null || "".equals( searchListInfo.getListView())) && !"searchForm".equals(source)) {
				return "ListOK";
			}
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(-2, "-- TUTTI --");
			context.getRequest().setAttribute("SiteList", siteList);

			//inserito da Carmela
			LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
			OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);


			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			//Display list of accounts if user chooses not to list contacts
			if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
				if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchOrgListInfo");
					searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
					searchListInfo.setLink("Distributori.do?command=Search");
				}
				//Build the organization list
				organizationList.setPagedListInfo(searchListInfo);
				organizationList.setMinerOnly(false);
				organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));

				organizationList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));

				searchListInfo.setSearchCriteria(organizationList, context);
				//fetching criterea for account source (my accounts or all accounts)
				if ("my".equals(searchListInfo.getListView())) {
					organizationList.setOwnerId(this.getUserId(context));
				}
				if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
					organizationList.setOrgSiteId(this.getUserSiteId(context));
					organizationList.setIncludeOrganizationWithoutSite(false);
				} else if (organizationList.getOrgSiteId() == -1) {
					organizationList.setIncludeOrganizationWithoutSite(true);
				}
				//fetching criterea for account status (active, disabled or any)
				int enabled = searchListInfo.getFilterKey("listFilter2");
				organizationList.setIncludeEnabled(enabled);
				//If the user is a portal user, fetching only the
				//the organization that he access to
				//(i.e., the organization for which he is an account contact
				if (isPortalUser(context)) {
					organizationList.setOrgSiteId(this.getUserSiteId(context));
					organizationList.setIncludeOrganizationWithoutSite(false);
					organizationList.setOrgId(getPortalUserPermittedOrgId(context));
				}




				organizationList.buildList(db);
				context.getRequest().setAttribute("OrgList", organizationList);
				context.getSession().setAttribute("previousSearchType", "accounts");
				if (organizationList.size() == 1 && organizationList.getAssetSerialNumber() != null) {
					AssetList assets = new AssetList();
					assets.setOrgId(((Organization) organizationList.get(0)).getId());
					assets.setSerialNumber(organizationList.getAssetSerialNumber());
					assets.setSkipParentIdRequirement(true);
					assets.buildList(db);
					if (assets.size() == 1) {
						Asset asset = (Asset) assets.get(0);
						context.getRequest().setAttribute(
								"id", String.valueOf(asset.getId()));
						return "AssetDetailsOK";
					}
				}
				return "ListOK";
			} else {}
		} catch (Exception e) {
			//Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "" ;
	}


	/**
	 *  ViewTickets: Displays Ticket history (open and closed) for a particular
	 *  Account.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandViewTickets(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-tickets-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		TicketList ticList = new TicketList();
		Organization newOrg = null;
		//Process request parameters
		int passedId = Integer.parseInt(
				context.getRequest().getParameter("orgId"));

		//Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(
				context, "AccountTicketInfo", "t.entered", "desc");
		ticketListInfo.setLink(
				"Distributori.do?command=ViewTickets&orgId=" + passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			//find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, passedId)) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, passedId);
			ticList.setOrgId(passedId);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			ticList.buildList(db);
			context.getRequest().setAttribute("TicList", ticList);
			context.getRequest().setAttribute("OrgDetails", newOrg);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewTickets");
	}


	/**
	 *  Insert: Inserts a new Account into the database.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandInsert(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Organization insertedOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = (Organization) context.getFormBean();
		Audit audit = new Audit();
		audit.setLivelloRischioFinale(-1);

		newOrg.setTypeList(context.getRequest().getParameterValues("selectedList"));
		newOrg.setEnteredBy(getUserId(context));
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setOwner(getUserId(context));
		newOrg.setName(context.getRequest().getParameter("name"));
		newOrg.setLuogoNascitaRappresentante(context.getRequest().getParameter("luogoNascitaRappresentante"));
		newOrg.setTitoloRappresentante(Integer.parseInt(context.getRequest().getParameter("titoloRappresentante")));
		if(context.getRequest().getParameter("dataNascitaRappresentante")!=null && !((String)context.getRequest().getParameter("dataNascitaRappresentante")).equals("")){
			SimpleDateFormat sf=new SimpleDateFormat("dd/MM/yyyy");
			java.sql.Date d = null;
			try {
				d = new java.sql.Date(sf.parse(context.getRequest().getParameter("dataNascitaRappresentante")).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Timestamp t=new Timestamp(d.getTime());
			newOrg.setDataNascitaRappresentante(t);



		}


		try {
			db = this.getConnection(context);



			LookupList tipoDitributore = new LookupList(db, "lookup_tipo_distributore");
			tipoDitributore.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoDitributore", tipoDitributore);





			//aggiunto da d.dauria

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
			OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			//set the name to namelastfirstmiddle if individual

			newOrg.setRequestItems(context);

			if (this.getUserSiteId(context) != -1) {
				// Set the site Id of the account to be equal to the site Id of the user
				// for a new account
				if (newOrg.getId() == -1) {
					newOrg.setSiteId(this.getUserSiteId(context));
				} else {
					// Check whether the user has access to update the organization
					if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
						return ("PermissionError");
					}
				}
			}

			isValid = this.validateObject(context, db, newOrg);
			if (isValid) {



				String numDistributori=context.getRequest().getParameter(("numElementi"));



				SimpleDateFormat sf=new SimpleDateFormat("dd/MM/yyyy");

				ArrayList<Distrubutore> listaDistributori=new ArrayList<Distrubutore>();


				recordInserted = newOrg.insert(db,context);
				newOrg.insertDistributori(db);



			}
			if (recordInserted) {
				insertedOrg = new Organization(db, newOrg.getOrgId());
				context.getRequest().setAttribute("OrgDetails", insertedOrg);
				addRecentItem(context, newOrg);


			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			String target = context.getRequest().getParameter("target");
			if (context.getRequest().getParameter("popup") != null) {
				return ("ClosePopupOK");
			}
			if (target != null && "add_contact".equals(target)) {
				return ("InsertAndAddContactOK");  
			} else {
				return ("InsertOK");
			}
		}
		return (executeCommandAdd(context));
	}


	/**
	 *  Update: Updates the Organization table to reflect user-entered
	 *  changes/modifications to the currently selected Account
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandUpdate(ActionContext context) {
		if (!(hasPermission(context, "distributori-distributori-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		org.aspcfs.modules.distributori.base.Organization newOrg = (org.aspcfs.modules.distributori.base.Organization) context.getFormBean();
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		newOrg.setTypeList(
				context.getRequest().getParameterValues("selectedList"));
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setEnteredBy(getUserId(context));
		try {
			db = this.getConnection(context);
			//set the name to namelastfirstmiddle if individual
			if (context.getRequest().getParameter("form_type").equalsIgnoreCase(
					"individual")) {
				newOrg.setIsIndividual(true);

				newOrg.setRequestItems(context);

				oldOrg = new Organization(db, newOrg.getOrgId());
				isValid = this.validateObject(context, db, newOrg);
				if (isValid) {
					resultCount = newOrg.update(db,context);

					ArrayList<Distrubutore> listaDistributori=new ArrayList<Distrubutore>();


					newOrg.updateDistributori(db);


				}
				if (resultCount == 1) {
					processUpdateHook(context, oldOrg, newOrg);
					//if this is an individual account, populate and update the primary contact

				}
				//update all contacts which are associated with this organization



				LookupList statoLab = new LookupList(db, "lookup_stato_lab");
				statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("statoLab", statoLab);

				LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
				OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);

				LookupList impianto = new LookupList(db, "lookup_impianto");
				impianto.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("impianto", impianto);

			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Modify Account");

		if (resultCount == -1 || !isValid) {
			return (executeCommandModify(context));
		} else if (resultCount == 1) {
			if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
					"return").equals("list")) {
				return (executeCommandSearch(context));
			} else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
					"return").equals("dashboard")) {
				return (executeCommandDashboard(context));
			} else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
					"return").equals("Calendar")) {
				if (context.getRequest().getParameter("popup") != null) {
					return ("PopupCloseOK");
				}
			} else {
				return ("UpdateOK");
			}
		} else {
			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
		return ("UpdateOK");
	}

	/**
	 *  Update: Updates the Organization table to reflect user-entered
	 *  changes/modifications to the currently selected Account
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandUpdateCatRischio(ActionContext context) {
		if (!(hasPermission(context, "distributori-tipochecklist-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		String org_id = context.getRequest().getParameter( "orgId" );
		String account_size = context.getRequest().getParameter( "accountSize" );
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);
			//set the name to namelastfirstmiddle if individual

			Organization newOrg = new Organization( db, Integer.parseInt( org_id ) );
			newOrg.setAccountSize( account_size );
			newOrg.setTypeList(
					context.getRequest().getParameterValues("selectedList"));
			newOrg.setModifiedBy(getUserId(context));
			newOrg.setEnteredBy(getUserId(context));

			oldOrg = new Organization(db, newOrg.getOrgId());
			isValid = this.validateObject(context, db, newOrg);
			if (isValid) {
				resultCount = newOrg.update(db,context);
			}

		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Modify Account");

		return ("UpdateCatRischioOK");
	}

	/**
	 *  Delete: Deletes an Account from the Organization table
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandDelete(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-delete")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Exception errorMessage = null;
		boolean recordDeleted = false;
		Organization thisOrganization = null;
		Connection db = null;
		try {
			db = this.getConnection(context);
			thisOrganization = new Organization(
					db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			//check permission to record
			if (!isRecordAccessPermitted(context, db, thisOrganization.getId())) {
				return ("PermissionError");
			}
			if (context.getRequest().getParameter("action") != null) {
				if (((String) context.getRequest().getParameter("action")).equals(
						"delete")) {
					// NOTE: these may have different options later
					thisOrganization.setContactDelete(true);
					thisOrganization.setRevenueDelete(true);
					thisOrganization.setDocumentDelete(true);
					OpportunityHeaderList opportunityList = new OpportunityHeaderList();
					opportunityList.setOrgId(thisOrganization.getOrgId());
					opportunityList.buildList(db);
					opportunityList.invalidateUserData(context, db);
					thisOrganization.setForceDelete(
							context.getRequest().getParameter("forceDelete"));
					recordDeleted = thisOrganization.delete(
							db, context, getDbNamePath(context));
				} else if (((String) context.getRequest().getParameter("action")).equals(
						"disable")) {
					recordDeleted = thisOrganization.disable(db);
				}
			}
		} catch (Exception e) {
			errorMessage = e;
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		if (errorMessage == null) {
			if (recordDeleted) {
				
				context.getRequest().setAttribute(
						"refreshUrl", "Distributori.do?command=Search");
				if ("disable".equals(context.getRequest().getParameter("action")) && "list".equals(
						context.getRequest().getParameter("return"))) {
					return executeCommandSearch(context);
				}
				return "DeleteOK";
			} else {
				processErrors(context, thisOrganization.getErrors());
				return (executeCommandSearch(context));
			}
		} else {

			context.getRequest().setAttribute(
					"actionError", systemStatus.getLabel(
							"object.validation.actionError.accountDeletion"));
			context.getRequest().setAttribute(
					"refreshUrl", "Distributori.do?command=Search");
			return ("DeleteError");
		}
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandTrash(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-delete")) {
			return ("PermissionError");
		}
		boolean recordUpdated = false;
		Connection db = null;
		try {
		      db = this.getConnection(context);
		      int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		      String note = context.getRequest().getParameter("note");

		      //check permission to record
		      if (!isRecordAccessPermitted(context, db, orgId)) {
		        return ("PermissionError");
		      }
		     
		      // NOTE: these may have different options later
		      recordUpdated = AccountsUtil.deleteCentralizzato(db, orgId, note, this.getUserId(context));
		   
		    } catch (Exception e) {
			context.getRequest().setAttribute(
					"refreshUrl", "Distributori.do?command=Search");
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		if (recordUpdated) {
			context.getRequest().setAttribute(
					"refreshUrl", "Distributori.do?command=Search");
			if ("list".equals(context.getRequest().getParameter("return"))) {
				return executeCommandSearch(context);
			}
			return "DeleteOK";
		} else {
			return (executeCommandSearch(context));
		}
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandRestore(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-delete")) {
			return ("PermissionError");
		}
		boolean recordUpdated = false;
		Organization thisOrganization = null;
		Connection db = null;
		try {
			db = this.getConnection(context);
			String orgId = context.getRequest().getParameter("orgId");
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			// NOTE: these may have different options later
			recordUpdated = thisOrganization.updateStatus(
					db, context, false, this.getUserId(context));
			this.invalidateUserData(context, this.getUserId(context));
			this.invalidateUserData(context, thisOrganization.getOwner());
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		if (recordUpdated) {
			context.getRequest().setAttribute(
					"refreshUrl", "Distributori.do?command=Search");
			if ("list".equals(context.getRequest().getParameter("return"))) {
				return executeCommandSearch(context);
			}
			return this.executeCommandDetails(context);
		} else {
			return (executeCommandSearch(context));
		}
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandEnable(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-edit")) {
			return ("PermissionError");
		}
		boolean recordEnabled = false;
		Organization thisOrganization = null;
		Connection db = null;
		try {
			db = this.getConnection(context);
			thisOrganization = new Organization(
					db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			recordEnabled = thisOrganization.enable(db);
			if (!recordEnabled) {
				this.validateObject(context, db, thisOrganization);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		return (executeCommandSearch(context));
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-delete")) {
			return ("PermissionError");
		}
		Connection db = null;
		HtmlDialog htmlDialog = new HtmlDialog();
		int orgId = -1;
		SystemStatus systemStatus = this.getSystemStatus(context);
		if (context.getRequest().getParameter("id") != null) {
			orgId = Integer.parseInt(context.getRequest().getParameter("id"));
		}
		try {
		      db = this.getConnection(context);
		      //check permission to record
		      if (!AccountsUtil.isCancellabile(db, orgId)){
		    	  htmlDialog.addMessage("<font color=\"red\"><b>Impossibile proseguire nella cancellazione. Sono presenti CU associati.</b></font>");
		          htmlDialog.addMessage("<br/>");
		          htmlDialog.addMessage("<input type=\"button\" value=\"CHIUDI\" onClick=\"javascript:parent.window.close()\"/>");
		      }
		      else {
		      htmlDialog.addMessage("<form action=\"Distributori.do?command=Trash&auto-populate=true\" method=\"post\">");
		      htmlDialog.addMessage("<b>Note cancellazione</b>: <textarea required id=\"note\" name=\"note\" rows=\"2\" cols=\"40\"></textarea>");
		      htmlDialog.addMessage("<br/>");
		      htmlDialog.addMessage("<input type=\"submit\" value=\"ELIMINA\"/>");
		      htmlDialog.addMessage("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ");
		      htmlDialog.addMessage("<input type=\"button\" value=\"ANNULLA\" onClick=\"javascript:parent.window.close()\"/>");
		      htmlDialog.addMessage("<input type=\"hidden\" id=\"action\" name=\"action\" value=\"delete\"/>");
		      htmlDialog.addMessage("<input type=\"hidden\" id=\"forceDelete\" name=\"forceDelete\" value=\"true\"/>");
		      htmlDialog.addMessage("<input type=\"hidden\" id=\"orgId\" name=\"orgId\" value=\""+orgId+"\"/>");
		      htmlDialog.addMessage("</form>");
		      }
		    } catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getSession().setAttribute("Dialog", htmlDialog);
		return ("ConfirmDeleteOK");
	}


	/**
	 *  Modify: Displays the form used for modifying the information of the
	 *  currently selected Account
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandModify(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-edit")) {
			return ("PermissionError");
		}
		String orgid = context.getRequest().getParameter("orgId");
		context.getRequest().setAttribute("orgId", orgid);

		int tempid = Integer.parseInt(orgid);
		Connection db = null;
		Organization newOrg = null;
		try {
			db = this.getConnection(context);
			newOrg = (Organization) context.getFormBean();
			if (newOrg.getId() == -1) {
				newOrg = new Organization(db, tempid);

			} 
			if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
				return ("PermissionError");
			}

			SystemStatus systemStatus = this.getSystemStatus(context);
			context.getRequest().setAttribute("systemStatus", systemStatus);


			LookupList tipoDitributore = new LookupList(db, "lookup_tipo_distributore");
			tipoDitributore.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoDitributore", tipoDitributore);


			LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
			OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);



			LookupList statoLab = new LookupList(db, "lookup_stato_lab");
			statoLab.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("statoLab", statoLab);

			LookupList impianto = new LookupList(db, "lookup_impianto");
			impianto.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("impianto", impianto);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);


			//Make the StateSelect and CountrySelect drop down menus available in the request.
			//This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible

			CountrySelect countrySelect = new CountrySelect(systemStatus);
			context.getRequest().setAttribute("CountrySelect", countrySelect);
			context.getRequest().setAttribute("systemStatus", systemStatus);

			UserList userList = filterOwnerListForSite(context, newOrg.getSiteId());
			context.getRequest().setAttribute("UserList", userList);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Account Modify");
		context.getRequest().setAttribute("OrgDetails", newOrg);
		if (context.getRequest().getParameter("popup") != null) {
			return ("PopupModifyOK");
		} else {
			return ("ModifyOK");
		}
	}


	//inserito carmela
	public String executeCommandModificaCatRischio(ActionContext context) {
		if ((!hasPermission(context, "distributori-tipochecklist-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {
			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);
			db = this.getConnection(context);
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, tempid);
			//check whether or not the owner is an active User
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");
		if (action != null && action.equals("modify")) {
			//If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("ModificaCatRischioOK");
		} else {
			//If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);
			return getReturn(context, "ModificaCatRischio");
		}
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandFolderList(ActionContext context) {
		if (!(hasPermission(context, "distributori-distributori-folders-view"))) {
			return ("PermissionError");
		}
		Connection db = null;
		Organization thisOrganization = null;
		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			//Show a list of the different folders available in Accounts
			CustomFieldCategoryList thisList = new CustomFieldCategoryList();
			thisList.setLinkModuleId(Constants.ACCOUNTS);
			thisList.setLinkItemId(thisOrganization.getId());
			thisList.setIncludeEnabled(Constants.TRUE);
			thisList.setIncludeScheduled(Constants.TRUE);
			thisList.setBuildResources(false);
			thisList.setBuildTotalNumOfRecords(true);
			thisList.buildList(db);
			context.getRequest().setAttribute("CategoryList", thisList);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Custom Fields Details");
		return this.getReturn(context, "FolderList");
	}


	/**
	 *  Fields: Shows a list of custom field records that are located "within" the
	 *  selected Custom Folder. Also shows the details of a particular Custom
	 *  Field Record when it is selected (details page)
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandFields(ActionContext context) {
		if (!(hasPermission(context, "distributori-distributori-folders-view"))) {
			return ("PermissionError");
		}
		Connection db = null;
		Organization thisOrganization = null;
		String source = context.getRequest().getParameter("source");
		String actionStepId = context.getRequest().getParameter("actionStepId");
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("source", source);
		}
		if (actionStepId != null && !"".equals(actionStepId.trim())) {
			context.getRequest().setAttribute("actionStepId", actionStepId);
		}
		String recordId = null;
		boolean showRecords = true;
		String selectedCatId = null;
		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			//Show a list of the different folders available in Accounts
			CustomFieldCategoryList thisList = new CustomFieldCategoryList();
			thisList.setLinkModuleId(Constants.ACCOUNTS);
			thisList.setIncludeEnabled(Constants.TRUE);
			thisList.setIncludeScheduled(Constants.TRUE);
			thisList.setBuildResources(false);
			thisList.buildList(db);
			context.getRequest().setAttribute("CategoryList", thisList);

			//See which one is currently selected or use the default
			selectedCatId = (String) context.getRequest().getParameter("catId");
			if (selectedCatId == null) {
				selectedCatId = (String) context.getRequest().getAttribute("catId");
			}
			if (selectedCatId == null) {
				selectedCatId = "" + thisList.getDefaultCategoryId();
			}
			context.getRequest().setAttribute("catId", selectedCatId);

			if (Integer.parseInt(selectedCatId) > 0) {
				//See if a specific record has been chosen from the list
				recordId = context.getRequest().getParameter("recId");
				String recordDeleted = (String) context.getRequest().getAttribute(
						"recordDeleted");
				if (recordDeleted != null) {
					recordId = null;
				}

				//Now build the specified or default category
				CustomFieldCategory thisCategory = thisList.getCategory(
						Integer.parseInt(selectedCatId));
				if (recordId == null && thisCategory.getAllowMultipleRecords()) {
					//The user didn't request a specific record, so show a list
					//of records matching this category that the user can choose from
					PagedListInfo folderListInfo = this.getPagedListInfo(
							context, "AccountFolderInfo");
					folderListInfo.setLink(
							"Distributori.do?command=Fields&orgId=" + orgId + "&catId=" + selectedCatId);

					CustomFieldRecordList recordList = new CustomFieldRecordList();
					recordList.setLinkModuleId(Constants.ACCOUNTS);
					recordList.setLinkItemId(thisOrganization.getOrgId());
					recordList.setCategoryId(thisCategory.getId());
					recordList.buildList(db);
					recordList.buildRecordColumns(db, thisCategory);
					context.getRequest().setAttribute("Records", recordList);
				} else {
					//The user requested a specific record, or this category only
					//allows a single record.
					thisCategory.setLinkModuleId(Constants.ACCOUNTS);
					thisCategory.setLinkItemId(thisOrganization.getOrgId());
					if (recordId != null) {
						thisCategory.setRecordId(Integer.parseInt(recordId));
					} else {
						thisCategory.buildRecordId(db);
						recordId = String.valueOf(thisCategory.getRecordId());
					}
					thisCategory.setIncludeEnabled(Constants.TRUE);
					thisCategory.setIncludeScheduled(Constants.TRUE);
					thisCategory.setBuildResources(true);
					thisCategory.buildResources(db);
					showRecords = false;

					if (thisCategory.getRecordId() > -1) {
						CustomFieldRecord thisRecord = new CustomFieldRecord(
								db, thisCategory.getRecordId());
						context.getRequest().setAttribute("Record", thisRecord);
					}
				}
				context.getRequest().setAttribute("Category", thisCategory);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Custom Fields Details");
		if (Integer.parseInt(selectedCatId) <= 0) {
			return getReturn(context, "FieldsEmpty");
		} else if (recordId == null && showRecords) {
			return getReturn(context, "FieldRecordList");
		} else {
			return getReturn(context, "Fields");
		}
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandCheckFields(ActionContext context) {
		if (!(hasPermission(context, "distributori-distributori-folders-view"))) {
			return ("PermissionError");
		}
		Connection db = null;
		Organization thisOrganization = null;
		String recordId = context.getRequest().getParameter("recId");
		String source = context.getRequest().getParameter("source");
		String actionStepId = context.getRequest().getParameter("actionStepId");
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("source", source);
		}
		if (actionStepId != null && !"".equals(actionStepId.trim())) {
			context.getRequest().setAttribute("actionStepId", actionStepId);
		}
		boolean hasMultipleRecords = false;
		String selectedCatId = null;
		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			//Show a list of the different folders available in Accounts
			CustomFieldCategoryList thisList = new CustomFieldCategoryList();
			thisList.setLinkModuleId(Constants.ACCOUNTS);
			thisList.setIncludeEnabled(Constants.TRUE);
			thisList.setIncludeScheduled(Constants.TRUE);
			thisList.setBuildResources(false);
			thisList.buildList(db);
			context.getRequest().setAttribute("CategoryList", thisList);

			//See which one is currently selected or use the default
			selectedCatId = (String) context.getRequest().getParameter("catId");
			if (selectedCatId == null) {
				selectedCatId = (String) context.getRequest().getAttribute("catId");
			}
			if (selectedCatId == null) {
				selectedCatId = "" + thisList.getDefaultCategoryId();
			}
			context.getRequest().setAttribute("catId", selectedCatId);

			if (Integer.parseInt(selectedCatId) > 0) {
				//Now build the specified or default category
				CustomFieldCategory thisCategory = thisList.getCategory(
						Integer.parseInt(selectedCatId));
				thisCategory.setLinkModuleId(Constants.ACCOUNTS);
				thisCategory.setIncludeEnabled(Constants.TRUE);
				thisCategory.setIncludeScheduled(Constants.TRUE);
				thisCategory.setLinkItemId(thisOrganization.getOrgId());
				thisCategory.setBuildResources(true);
				thisCategory.buildRecordId(db);
				thisCategory.buildResources(db);
				if (recordId == null || "".equals(recordId.trim()) || "-1".equals(recordId)) {
					if (thisCategory.getRecordId() != -1) {
						recordId = String.valueOf(thisCategory.getRecordId());
					}
				}
				if ((recordId != null && !"".equals(recordId.trim())) && !"-1".equals(recordId)) {
					CustomFieldRecord thisRecord = new CustomFieldRecord(
							db, Integer.parseInt(recordId));
					context.getRequest().setAttribute("Record", thisRecord);
				}
				if (thisCategory.getRecordId() == -1 && thisCategory.getAllowMultipleRecords()) {
					//The user didn't request a specific record, so show a list
					//of records matching this category that the user can choose from
					PagedListInfo folderListInfo = this.getPagedListInfo(
							context, "AccountFolderInfo");
					folderListInfo.setLink(
							"Distributori.do?command=Fields&orgId=" + orgId + "&catId=" + selectedCatId);

					CustomFieldRecordList recordList = new CustomFieldRecordList();
					recordList.setLinkModuleId(Constants.ACCOUNTS);
					recordList.setLinkItemId(thisOrganization.getOrgId());
					recordList.setCategoryId(thisCategory.getId());
					recordList.buildList(db);
					recordList.buildRecordColumns(db, thisCategory);
					hasMultipleRecords = (recordList.size() > 0 && (recordId == null || "".equals(recordId.trim()))) ||
							(recordList.size() > 1 && recordId != null && !"".equals(recordId.trim()));
					context.getRequest().setAttribute("Records", recordList);
				} else if (thisCategory.getRecordId() != -1 && thisCategory.getAllowMultipleRecords()) {
					context.getRequest().setAttribute("recordDeleted", "true");
					// TODO: Executing a new action within an open db can create a deadlock
					return executeCommandFields(context);
				} else if (thisCategory.getRecordId() != -1 && !thisCategory.getAllowMultipleRecords()) {
					context.getRequest().setAttribute("recId", recordId);
					// TODO: Executing a new action within an open db can create a deadlock
					return executeCommandModifyFields(context);
				} else if (thisCategory.getRecordId() == -1 && !thisCategory.getAllowMultipleRecords()) {
					// TODO: Executing a new action within an open db can create a deadlock
					return executeCommandAddFolderRecord(context);
				}
				//The user requested a specific record, or this category only
				//allows a single record.
				context.getRequest().setAttribute("Category", thisCategory);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Custom Fields Details");
		if (Integer.parseInt(selectedCatId) <= 0) {
			return getReturn(context, "FieldsEmpty");
		} else {
			if (hasMultipleRecords) {
				return executeCommandModifyFields(context);
			}
			return getReturn(context, "FieldRecordList");
		}
	}


	/**
	 *  AddFolderRecord: Displays the form for inserting a new custom field record
	 *  for the selected Account.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandAddFolderRecord(ActionContext context) {
		if (!(hasPermission(context, "distributori-distributori-folders-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		String source = context.getRequest().getParameter("source");
		String actionStepId = context.getRequest().getParameter("actionStepId");
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("source", source);
		}
		if (actionStepId != null && !"".equals(actionStepId.trim())) {
			context.getRequest().setAttribute("actionStepId", actionStepId);
		}
		Organization thisOrganization = null;
		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			String selectedCatId = (String) context.getRequest().getParameter(
					"catId");
			CustomFieldCategory thisCategory = new CustomFieldCategory(
					db,
					Integer.parseInt(selectedCatId));
			thisCategory.setLinkModuleId(Constants.ACCOUNTS);
			thisCategory.setLinkItemId(thisOrganization.getOrgId());
			thisCategory.setIncludeEnabled(Constants.TRUE);
			thisCategory.setIncludeScheduled(Constants.TRUE);
			thisCategory.setBuildResources(true);
			thisCategory.buildResources(db);
			context.getRequest().setAttribute("Category", thisCategory);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Add Folder Record");
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));
		return getReturn(context, "AddFolderRecord");
	}


	/**
	 *  ModifyFields: Displays the modify form for the selected Custom Field
	 *  Record.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandModifyFields(ActionContext context) {
		if (!hasPermission(context, "distributori-distributori-folders-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		Organization thisOrganization = null;
		String source = context.getRequest().getParameter("source");
		String actionStepId = context.getRequest().getParameter("actionStepId");
		String specie_allev = context.getRequest().getParameter("specie_allev");
		context.getRequest().setAttribute("specie_allev", specie_allev);
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("source", source);
		}
		if (actionStepId != null && !"".equals(actionStepId.trim())) {
			context.getRequest().setAttribute("actionStepId", actionStepId);
		}
		String selectedCatId = (String) context.getRequest().getParameter("catId");
		String recordId = (String) context.getRequest().getParameter("recId");
		if (recordId == null || "".equals(recordId.trim()) || "-1".equals(recordId.trim())) {
			recordId = (String) context.getRequest().getAttribute("recId");
			if (recordId == null || "".equals(recordId.trim())) {
				recordId = String.valueOf(-1);
			}
		}
		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			CustomFieldCategory thisCategory = new CustomFieldCategory(
					db,
					Integer.parseInt(selectedCatId));
			thisCategory.setLinkModuleId(Constants.ACCOUNTS);
			thisCategory.setLinkItemId(thisOrganization.getOrgId());
			thisCategory.setRecordId(Integer.parseInt(recordId));
			thisCategory.setIncludeEnabled(Constants.TRUE);
			thisCategory.setIncludeScheduled(Constants.TRUE);
			thisCategory.setBuildResources(true);
			thisCategory.buildResources(db);
			context.getRequest().setAttribute("Category", thisCategory);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Modify Custom Fields");
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));
		if (recordId.equals("-1")) {
			return getReturn(context, "AddFolderRecord");
		} else {
			return getReturn(context, "ModifyFields");
		}
	}


	/**
	 *  UpdateFields: Performs the actual update of the selected Custom Field
	 *  Record based on user-submitted information from the modify form.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandUpdateFields(ActionContext context) {
		if (!(hasPermission(context, "distributori-distributori-folders-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean popup = false;
		String source = context.getRequest().getParameter("source");
		String actionStepId = context.getRequest().getParameter("actionStepId");
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("source", source);
		}
		if (actionStepId != null && !"".equals(actionStepId.trim())) {
			context.getRequest().setAttribute("actionStepId", actionStepId);
		}
		String popupString = context.getRequest().getParameter("popup");
		popup = (popupString != null && !"".equals(popupString.trim()) && "true".equals(popupString));
		Organization thisOrganization = null;
		int resultCount = 0;
		boolean isValid = false;
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));

		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			CustomFieldCategoryList thisList = new CustomFieldCategoryList();
			thisList.setLinkModuleId(Constants.ACCOUNTS);
			thisList.setIncludeEnabled(Constants.TRUE);
			thisList.setIncludeScheduled(Constants.TRUE);
			thisList.setBuildResources(false);
			thisList.buildList(db);
			context.getRequest().setAttribute("CategoryList", thisList);

			String selectedCatId = (String) context.getRequest().getParameter(
					"catId");
			String recordId = (String) context.getRequest().getParameter("recId");
			context.getRequest().setAttribute("catId", selectedCatId);
			CustomFieldCategory thisCategory = new CustomFieldCategory(
					db,
					Integer.parseInt(selectedCatId));
			thisCategory.setLinkModuleId(Constants.ACCOUNTS);
			thisCategory.setLinkItemId(thisOrganization.getOrgId());
			thisCategory.setRecordId(Integer.parseInt(recordId));
			thisCategory.setIncludeEnabled(Constants.TRUE);
			thisCategory.setIncludeScheduled(Constants.TRUE);
			thisCategory.setBuildResources(true);
			thisCategory.buildResources(db);
			thisCategory.setParameters(context);
			thisCategory.setModifiedBy(this.getUserId(context));
			if (!thisCategory.getReadOnly()) {
				thisCategory.setCanNotContinue(true);
				isValid = this.validateObject(context, db, thisCategory);
				if (isValid) {
					Iterator groups = (Iterator) thisCategory.iterator();
					while (groups.hasNext()) {
						CustomFieldGroup group = (CustomFieldGroup) groups.next();
						Iterator fields = (Iterator) group.iterator();
						while (fields.hasNext()) {
							CustomField field = (CustomField) fields.next();
							field.setValidateData(true);
							field.setRecordId(thisCategory.getRecordId());
							isValid = this.validateObject(context, db, field) && isValid;
						}
					}
				}
				if (isValid && resultCount != -1) {
					thisCategory.setCanNotContinue(true);
					resultCount = thisCategory.update(db);
					thisCategory.setCanNotContinue(false);
					resultCount = thisCategory.insertGroup(
							db, thisCategory.getRecordId());
				}
			}
			context.getRequest().setAttribute("Category", thisCategory);
			if (resultCount != -1 && isValid) {
				thisCategory.buildResources(db);
				CustomFieldRecord thisRecord = new CustomFieldRecord(
						db, thisCategory.getRecordId());
				context.getRequest().setAttribute("Record", thisRecord);
			} else {
				if (System.getProperty("DEBUG") != null) {
					System.out.println("distributori-> ModifyField validation error");
				}
				if (actionStepId != null && !"".equals(actionStepId.trim())) {
					context.getRequest().setAttribute("recId", recordId);
				}
				context.getRequest().setAttribute(
						"systemStatus", this.getSystemStatus(context));
				return getReturn(context, "ModifyFields");
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (resultCount == 1 && isValid) {
			if (context.getRequest().getParameter("source") != null && "attachplan".equals(context.getRequest().getParameter("source"))) {
				return "UpdateFieldsAttachPlanOK";
			}
			return getReturn(context, "UpdateFields");
		} else {
			context.getRequest().setAttribute(
					"Error", CFSModule.NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 */
	private void resetPagedListInfo(ActionContext context) {
		
		this.deletePagedListInfo(context, "AccountFolderInfo");
		this.deletePagedListInfo(context, "RptListInfo");
		this.deletePagedListInfo(context, "OpportunityPagedInfo");
		this.deletePagedListInfo(context, "AccountTicketInfo");
		this.deletePagedListInfo(context, "AutoGuideAccountInfo");
		this.deletePagedListInfo(context, "RevenueListInfo");
		this.deletePagedListInfo(context, "AccountDocumentInfo");
		this.deletePagedListInfo(context, "ServiceContractListInfo");
		this.deletePagedListInfo(context, "AssetListInfo");
		this.deletePagedListInfo(context, "AccountProjectInfo");
		this.deletePagedListInfo(context, "orgHistoryListInfo");
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context        Description of the Parameter
	 * @param  siteId         Description of the Parameter
	 * @return                Description of the Return Value
	 * @exception  Exception  Description of the Exception
	 */
	private UserList filterOwnerListForSite(ActionContext context, int siteId) throws Exception {
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		//this is how we get the multiple-level heirarchy...recursive function.
		User thisRec = thisUser.getUserRecord();
		UserList shortChildList = thisRec.getShortChildList();
		UserList userList = thisRec.getFullChildList(
				shortChildList, new UserList());
		userList.setMyId(getUserId(context));
		userList.setMyValue(thisUser.getContact().getNameLastFirst());
		userList.setIncludeMe(true);
		userList.setExcludeDisabledIfUnselected(true);
		userList.setExcludeExpiredIfUnselected(true);

		// filter possible owners for accounts with site ids.

		// An account WITH a site id can be owned by a user with the same
		// site id or by one who has access to all sites
		// (i.e., siteId = -1 or site_id IS NULL)
		if (siteId != -1) {
			Iterator itr = userList.iterator();
			while (itr.hasNext()) {
				User tmpUser = (User) itr.next();
				if (tmpUser.getSiteId() == -1) {
					continue;
				}
				if (siteId != tmpUser.getSiteId()) {
					itr.remove();
				}
			}
		}

		// An account WITHOUT a site id can ONLY be owned by a user with access
		// to all sites
		// (i.e., siteId = -1 or site_id IS NULL)
		if (siteId == -1) {
			Iterator itr = userList.iterator();
			while (itr.hasNext()) {
				User tmpUser = (User) itr.next();
				if (siteId != tmpUser.getSiteId()) {
					itr.remove();
				}
			}
		}
		return userList;
	}

	

	/*inserito da d.dauria */
	public String executeCommandPrintReport(ActionContext context) {
		if (!hasPermission(context, "requestor-requestor-view") && !hasPermission(context, "requestor-requestor-report-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("id");
			HashMap map = new HashMap();
			map.put("orgid", new Integer(id));
			map.put("path", getWebInfPath(context, "reports"));
			//provide the dictionary as a parameter to the quote report
			map.put("CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
			//String filename = "modB.xml";
			String filename = (String) context.getRequest().getParameter("file");

			//provide a seperate database connection for the subreports
			Connection scriptdb = this.getConnection(context);
			map.put("SCRIPT_DB_CONNECTION", scriptdb);

			//Replace the font based on the system language to support i18n chars
			String fontPath = getWebInfPath(context, "fonts");
			String reportDir = getWebInfPath(context, "reports");
			JasperReport jasperReport = JasperReportUtils.getReport(reportDir + filename);
			String language = getPref(context, "SYSTEM.LANGUAGE");

			JasperReportUtils.modifyFontProperties(jasperReport, reportDir, fontPath, language);

			byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, map, db);

			if (bytes != null) {
				FileDownload fileDownload = new FileDownload();
				fileDownload.setDisplayName("DettaglioOperatoreAbusivo_" + id + ".pdf");
				fileDownload.sendFile(context, bytes, "application/pdf");
			} else {
				return ("SystemError");
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("-none-");
	}


}







