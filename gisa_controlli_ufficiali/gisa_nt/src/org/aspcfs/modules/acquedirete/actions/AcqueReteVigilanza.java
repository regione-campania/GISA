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
package org.aspcfs.modules.acquedirete.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
//import com.itextpdf.text.log.SysoLogger;
import com.zeroio.iteam.base.FileItem;

import org.aspcf.modules.controlliufficiali.base.ModCampioni;
import org.aspcf.modules.report.util.Filtro;
import org.aspcf.modules.report.util.StampaPdf;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.campioni.base.Analita;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.allerte.base.AslCoinvolte;
import org.aspcfs.checklist.base.Audit;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.aspcfs.modules.controller.base.Nodo;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.acquedirete.base.InfoPdP;
import org.aspcfs.modules.acquedirete.base.InfoPdPList;
import org.aspcfs.modules.acquedirete.base.Organization;
import org.aspcfs.modules.acquedirete.base.OrganizationList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.DwrPreaccettazione;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *          $
 * @created August 15, 2001
 */
public final class AcqueReteVigilanza extends CFSModule {

	/**
	 * Update the specified ticket
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public static int CODICE_INTERNO_PIANO_CONTROLLO = 1425 ;
	public static int CODICI_INTERNI_PIANI_CONTROLLO[] = { 1425} ;
	public static int CODICI_INTERNI_ATTIVITA_CONTROLLO[] = { 50} ;


	public static int MATRICE_ACQUA_RETE = 1161; 
	public String executeCommandSupervisiona(ActionContext context) {
		
		Connection db = null;
		int resultCount = 0;
		boolean isValid = true;



		


		try {


			db = this.getConnection(context);

			
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandSupervisiona(context, db);
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if (resultCount == 1 && isValid) {
			//return "UpdateOK";
			return "DettaglioOK";
		}
		return (executeCommandTicketDetails(context));
	}
	/**
	 * ACTION DI AGGIUNTA DI UN NUOVO CONTROLLO UFFICIALE
	 * 	
	 * @param context
	 * @return
	 */
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "acquedirete-vigilanza-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			String temporgId = context.getRequest().getParameter("orgId");
			int tempid = Integer.parseInt(temporgId);
			Organization newOrg = new Organization(db, tempid);
			context.getRequest().setAttribute("siteId", newOrg.getSiteId());
			context.getRequest().setAttribute("tipologia", 14);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza c = new  org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			c.executeCommandAdd(context,db);
			//Load the organization
			Organization thisOrganization = new Organization(db, Integer.parseInt(context.getParameter("orgId")));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute(
					"systemStatus", this.getSystemStatus(context));
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		 UserBean user = (UserBean)context.getSession().getAttribute("User");
		 String nameContext=context.getRequest().getServletContext().getServletContextName();
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA  )
				return getReturn(context, "AddTipo2");
			return getReturn(context, "Add");	}



	public String executeCommandInsert(ActionContext context) throws SQLException, ParseException {
		if (!(hasPermission(context, "acquedirete-vigilanza-add"))) 
		{
			return ("PermissionError");
		}
		
		context.getRequest().setAttribute("reload", "false");
		String retPage = "InsertOK";

		Connection db = null;
		
		try
		{
			db = this.getConnection(context);
			Ticket newTic = (Ticket) context.getFormBean();
			Organization thisOrg = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			newTic.setCompanyName(thisOrg.getName());
			addModuleBean(context, "Acque di Rete CU", "Acque di Rete CU");
			
			context.getRequest().setAttribute("OrgDetails", thisOrg);
			context.getRequest().setAttribute("name", thisOrg.getName());
			
			UserBean user = (UserBean) context.getSession().getAttribute("User");
			String ip = user.getUserRecord().getIp();
			
			String tipoCampione = context.getRequest().getParameter("tipoCampione");
			String  idPiano = context.getRequest().getParameter("tipoIspezione");
			String idIspezione = context.getRequest().getParameter("piano_monitoraggio1");
			String[] tipoispezione = context.getRequest().getParameterValues("tipoIspezione");
			//String[] pianomonitoraggio = context.getRequest().getParameterValues("piano_monitoraggio1");
			

			int indUO = 0;
			String[] idUO = new String[10];
			while(context.getRequest().getParameter("uo"+(indUO+1))!=null)
			{
				idUO[indUO]= context.getRequest().getParameter("uo"+(indUO+1));
				indUO++;
			}
			
			
			int indPianoM = 0;
			String[] idPianoM = new String[10];
			while(context.getRequest().getParameter("piano_monitoraggio"+(indPianoM+1))!=null)
			{
				idPianoM[indPianoM]= context.getRequest().getParameter("piano_monitoraggio"+(indPianoM+1));
				indPianoM++;
			}
			
			
			String[] ispezione = context.getRequest().getParameterValues("ispezione");
			String[] idComponente = new String[10];
			
			int indComp=0;
			
			while (((context.getRequest().getParameter("risorse_"+(indComp+1))!=null)) && (!context.getRequest().getParameter("risorse_"+(indComp+1)).equalsIgnoreCase("-1")))
			{
				idComponente[indComp]= context.getRequest().getParameter("risorse_"+(indComp+1));
				indComp++;
			}
			
			
			int ticketID = CallDbiAcquaDiRete (newTic, context, db, getUserId(context), ip,  tipoCampione ,  idPiano,  idIspezione,  tipoispezione , ispezione, idComponente,  indComp,  idUO, idPianoM);
			
		/*	String dbiControlloUfficialeAcque = "Select * from public.insert_controllo_ufficiale_acque(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement stmt = db.prepareStatement(dbiControlloUfficialeAcque);
				
			 stmt.setInt(1,newTic.getOrgId());
     		 stmt.setInt(2,getUserId(context));
			 stmt.setInt(3, getUserId(context));
			 stmt.setTimestamp(4,newTic.getAssignedDate());
			 stmt.setInt(5, newTic.getSiteId());
			 stmt.setInt(6, newTic.getTipoCampione());  
		
		     stmt.setInt(7, newTic.getPunteggio() );
			 stmt.setTimestamp(8,newTic.getDataFineControllo());
			 stmt.setInt(9, newTic.getEsitoControllo());
			 stmt.setString(10,ip );
			 stmt.setString(11,ip );
			 stmt.setString(12,tipoCampione);
			 stmt.setString(13, newTic.getProblem());
			 
			 // 2 parte dbi
			 
			 InfoPdPList listPd =new InfoPdPList();
				listPd.buildListFromRequest(context);
				int sizeCampioni = listPd.size();
				String testoAnalita = "";
				int indice = 0;
				  String[] tempIdFoglia = new String[6];
				  String[] tempDescrProt=  new String[6];
				  
				  int dim = listPd.size();
				  String[] tempTemperatura=  new String[dim];
				  String[] tempCloro=  new String[dim];
				  String[] tempOre=  new String[dim];
				  String[] tempProtRoutine=  new String[dim];
				  String[] tempProtVerifica=  new String[dim];
				  String[] tempProtReplicaMicro=  new String[dim];
				  String[] tempProtReplicaChim=  new String[dim];
				  String[] tempAltro=  new String[dim];
				  String[] tempProtRadioAttivita=  new String[dim];
				  String[] tempProtRicercaFit=  new String[dim];
				  
		
				for ( int i = 0 ; i<dim; i++)
				{

					InfoPdP pdp = (InfoPdP) listPd.get(i);
					//org.aspcfs.modules.campioni.base.Ticket campione = new org.aspcfs.modules.campioni.base.Ticket();

					 testoAnalita = Nodo.getRamo(MATRICE_ACQUA_RETE, context, "matrici", db).getPath();
					  
					  //qui dbi per il campione e il controllo
					  
						  
						  for (int a=0; a<6; a++){
							  boolean selected = pdp.isProtocolloSelezionato(a);
							  if (selected){
								  Analita analita = new Analita();
								  int idFoglia = analita.getIdAnalitaDaNome(db, pdp.getDescrizioneProtocollo(a));
								  tempIdFoglia[indice] = idFoglia+"";
								  tempDescrProt[indice++] = pdp.getDescrizioneProtocollo(a);
								  //campione.insertAnaliti(db, idFoglia, pdp.getDescrizioneProtocollo(a));
							  }
						  }
						  
						  
						  tempTemperatura[i] = pdp.getTemperatura();
						  tempCloro[i]	= pdp.getCloro();
						  tempOre[i] =  pdp.getOre();
						  tempProtRoutine[i] = String.valueOf(pdp.isProt_routine());
						  tempProtVerifica[i] = String.valueOf( pdp.isProt_verifica());  
						  tempProtReplicaMicro[i] = String.valueOf( pdp.isProt_replica_micro());
						  tempProtReplicaChim[i] =  String.valueOf( pdp.isProt_replica_chim());
						  tempAltro[i] =  pdp.getAltro();
						  tempProtRadioAttivita[i] = String.valueOf( pdp.isProt_radioattivita());
						  tempProtRicercaFit[i] =  String.valueOf( pdp.isProt_ricerca_fitosanitari());
						  
				}
			 
							stmt.setInt(14, Integer.parseInt(idPiano));
							stmt.setInt(15, Integer.parseInt(idIspezione) );
						  
							Array intIdFoglia = db.createArrayOf("integer", tempIdFoglia);
							stmt.setArray(16,intIdFoglia);
						  
							Array stringsDescrProt = db.createArrayOf("text", tempDescrProt);
							stmt.setArray(17, stringsDescrProt);
						  
							stmt.setInt(18, MATRICE_ACQUA_RETE);
							stmt.setString(19, testoAnalita);
							stmt.setInt(20, indice);
							stmt.setInt(21, sizeCampioni);
							
							
							Array strTemp = db.createArrayOf("text", tempTemperatura);
							stmt.setArray(22,strTemp);
						  
							Array strCloro = db.createArrayOf("text", tempCloro);
							stmt.setArray(23, strCloro);
						  
							Array strOre= db.createArrayOf("text", tempOre);
							stmt.setArray(24,strOre);
						  
							Array boolProtRout = db.createArrayOf("boolean", tempProtRoutine);
							stmt.setArray(25, boolProtRout);
						  
							Array boolProtVer = db.createArrayOf("boolean", tempProtVerifica);
							stmt.setArray(26,boolProtVer);
						  
							Array boolProRepMic = db.createArrayOf("boolean", tempProtReplicaMicro);
							stmt.setArray(27, boolProRepMic);
						  
							Array booloProtRepCho = db.createArrayOf("boolean", tempProtReplicaChim);
							stmt.setArray(28,booloProtRepCho);
						  
							Array strAltro = db.createArrayOf("text", tempAltro);
							stmt.setArray(29, strAltro);
						  
							Array boolProtRadio = db.createArrayOf("boolean", tempProtRadioAttivita);
							stmt.setArray(30,boolProtRadio);
						  
							Array boolProtRic = db.createArrayOf("boolean", tempProtRicercaFit);
							stmt.setArray(31, boolProtRic);
							
							
							//Parametri per nucleo ispettivo
								
							
							Array tipoisp = db.createArrayOf("integer", tipoispezione);
							stmt.setArray(32, tipoisp);
							
							Array isp = db.createArrayOf("integer", ispezione);
							stmt.setArray(33, isp);
							
							Array componentiNucleo = db.createArrayOf("integer", idComponente);
							stmt.setArray(34, componentiNucleo);
							
							stmt.setInt(35, indComp);
							
							Array idUnitaOp = db.createArrayOf("integer", idUO);
							stmt.setArray(36, idUnitaOp);
							
							
							Array pianoM = db.createArrayOf("integer", idPianoM);
							stmt.setArray(37, pianoM);
							
							 System.out.println("Query"+stmt);

							 int ticketID = 0; 
							 ResultSet rs = stmt.executeQuery();
							 
							 if (rs.next())
							 {
								 ticketID = rs.getInt(1); 
							 }*/
							 
							System.out.println("ticketID: "+ticketID);
								
				String tipo_richiesta = newTic.getTipo_richiesta();
				tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
				
				if (ticketID!=0)
				{
					newTic.setId(ticketID);
					retPage = "InsertOK";
				}
				else
				{
					return executeCommandAdd(context);

				}
	

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return ( retPage );//("InsertOK"); 
	}
	
	



	/**
	 * Loads the ticket for modification
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandModifyTicket(ActionContext context) {
		if (!hasPermission(context, "acquedirete-vigilanza-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;

		try 
		{
			db = this.getConnection(context);
			String ticketId = context.getRequest().getParameter("id");

			if (context.getRequest().getParameter("companyName") == null) 
			{
				newTic = new Ticket(db, Integer.parseInt(ticketId));

			} else 
			{
				newTic = (Ticket) context.getFormBean();
			}
			
			//gestione della modifica del controllo ufficale
			boolean isModificabile = true;
			PreparedStatement pst = db.prepareStatement("select * from get_lista_sottoattivita(?, ?, ?)");
			pst.setInt(1, newTic.getId());
			pst.setInt(2, 2);
			pst.setBoolean(3, false);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				int idCampione = rs.getInt("ticketid");
				//Preaccettazione
				DwrPreaccettazione dwrPreacc = new DwrPreaccettazione();
				String result = dwrPreacc.Preaccettazione_RecuperaCodPreaccettazione(String.valueOf(idCampione));
				JSONObject jsonObj;
				jsonObj = new JSONObject(result);
				String codicePreaccettazione = jsonObj.getString("codice_preaccettazione");
				if (!codicePreaccettazione.equalsIgnoreCase("")){
					isModificabile = false;
				}
				
			}
			
			UserBean user = (UserBean)context.getSession().getAttribute("User");
			if (user.getUserRecord().getRoleId()!=Role.RUOLO_ORSA && user.getUserRecord().getRoleId()!=Role.HD_1LIVELLO && user.getUserRecord().getRoleId()!=Role.HD_2LIVELLO ){
				if (!isModificabile){
					context.getRequest().setAttribute("Error", "Controllo non modificabile perche' associato ad un codice preaccettazione");
					return(executeCommandTicketDetails(context));
				}
			}
			//gestione della modifica del controllo ufficiale
			
			Organization thisOrganization = new Organization(db, newTic.getOrgId());

			context.getRequest().setAttribute("siteId", thisOrganization.getSiteId());
			context.getRequest().setAttribute("tipologia", 14);
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			context.getRequest().setAttribute("TicketDetails", newTic);

			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandModifyTicket(context,db);
			Ticket cu = (Ticket)context.getRequest().getAttribute("TicketDetails");
			InfoPdPList list = new InfoPdPList();
			list.setId_controllo(cu.getId());
			list.buildList(db);
			context.getRequest().setAttribute("PdpList", list);

		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		//return "ModifyautorizzazionetrasportoanimaliviviOK";
		 UserBean user = (UserBean)context.getSession().getAttribute("User");
		 String nameContext=context.getRequest().getServletContext().getServletContextName();
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
				return getReturn(context, "ModifyTipo2");
			return getReturn(context, "Modify");		//return getReturn(context, "ModifyTicket");
	}
	

	/**
	 * Update the specified ticket
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException 
	 */
	public String executeCommandUpdateTicket(ActionContext context) throws SQLException {
		if (!hasPermission(context, "acquedirete-vigilanza-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		
		int resultCount = 0;
		boolean isValid = true;
		Ticket newTic = null;
		try {


		db = this.getConnection(context);
		int ticketId = Integer.parseInt(context.getRequest().getParameter("id"));
		Ticket oldTicket = new Ticket (db, ticketId);
		
		 newTic = (Ticket) context.getFormBean();

		if(context.getRequest().getParameter("codici_selezionabili")!=null){
			newTic.setCodiceAteco(context.getRequest().getParameter("codici_selezionabili"));
		}

		newTic.setCodiceAllerta(context.getRequest().getParameter("idAllerta"));
	

		if( "1".equals(context.getRequest().getParameter("ncrilevate")))
			newTic.setNcrilevate(true);
		else
			newTic.setNcrilevate(false);


		newTic.setId(Integer.parseInt(context.getRequest().getParameter("id")));

		
			Organization orgDetails = new Organization(db, Integer.parseInt(context.getParameter("orgId")));

			context.getRequest().setAttribute("siteId",orgDetails.getSiteId());
			context.getRequest().setAttribute("tipologia",14);
			//context.getRequest().setAttribute("tipoDest",orgDetails.getTipoDest());
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandUpdateTicket(context,db);
			context.getRequest().setAttribute("OrgDetails", orgDetails);
			resultCount = (Integer) context.getRequest().getAttribute("resultCount");
			addModuleBean(context, "Acque di Reta CU", "Acque di Rete CU");
			isValid = (Boolean) context.getRequest().getAttribute("isValid");
			
			Ticket cuInserito =(Ticket) context.getRequest().getAttribute("TicketDetails");
			 
			db.prepareStatement("update controlli_punti_di_prelievo_acque_rete set enabled = false where id_controllo = "+cuInserito.getId()).execute();
			InfoPdPList listPd =new InfoPdPList();
			listPd.buildListFromRequest(context);
			for ( int i = 0 ; i<listPd.size(); i++)
			{
				InfoPdP pdp = (InfoPdP) listPd.get(i);
				
				
				org.aspcfs.modules.campioni.base.Ticket campione = new org.aspcfs.modules.campioni.base.Ticket();
				if (!oldTicket.isPianoCodiceInternoSelected(db, CODICE_INTERNO_PIANO_CONTROLLO+"") && cuInserito.isPianoCodiceInternoSelected(db, CODICE_INTERNO_PIANO_CONTROLLO+"")){
	
					  /*setto i campi forniti dalla regione*/
				  
				  campione.setOrgId(newTic.getOrgId());
				  
				  String sql_piano = "select p.code, i.code from lookup_piano_monitoraggio p left join lookup_tipo_ispezione i on i.codice_interno= p.codice_interno_tipo_ispezione where p.enabled and i.enabled and p.codice_interno =  "+CODICE_INTERNO_PIANO_CONTROLLO;
				  PreparedStatement pst = db.prepareStatement(sql_piano);
				  ResultSet rs_piano = pst.executeQuery();
				  int idPiano = -1 ;
				  int idIspezione = -1 ;
				  if (rs_piano.next()){
					  idPiano=rs_piano.getInt(1);
				  	  idIspezione = rs_piano.getInt(2);
				  }
				 
				  campione.setLocation("");
				  campione.setAssignedDate(newTic.getAssignedDate());
				  campione.setMotivazione_campione(idIspezione);
				  campione.setMotivazione_piano_campione(idPiano);
				  campione.setIpEntered(cuInserito.getIpEntered());
				  campione.setIpModified(cuInserito.getIpModified());
				  campione.setDestinatarioCampione(1); //ARPAC
				  campione.setEnteredBy(getUserId(context));
				  campione.setModifiedBy(getUserId(context));
				  campione.setSiteId(newTic.getSiteId());
				  campione.setIdControlloUfficiale(newTic.getPaddedId());
				  campione.setProblem("PRELIEVO CAMPIONE PER RICERCA ACQUE DI RETE INSERITO DA CONTROLLO");
				  
				  boolean inserito = campione.insert(db,context);
				  if (inserito)
				  {
					  for (int a=0; a<6; a++){
						  boolean selected = pdp.isProtocolloSelezionato(a);
						  if (selected){
							  Analita analita = new Analita();
							  int idFoglia = analita.getIdAnalitaDaNome(db, pdp.getDescrizioneProtocollo(a));
							  campione.insertAnaliti(db, idFoglia, pdp.getDescrizioneProtocollo(a));
						  }
					  }
					  
					  campione.insertMatrici(db, MATRICE_ACQUA_RETE,Nodo.getRamo(MATRICE_ACQUA_RETE, context, "matrici", db).getPath());
					  
					  //recupero il codice creato e lo setto a location
					   ModCampioni mc = new ModCampioni();
					  //String location = mc.generaCodiceAcque(db, campione.getId(), newTic.getId(),newTic.getOrgId());
					  String location2 = mc.generaCodice(db, 0,newTic.getOrgId(),campione.getId());
					  campione.setLocation(location2);
					  campione.updateLocation(db);
					 
				  }
			
				    pdp.setId_campione(campione.getId());

				  
				}
				
				else
				{
					pdp.setIdCampionePrecedente(db, cuInserito.getId());
				}
				
				pdp.setId_controllo(cuInserito.getId());
				pdp.insert(db);
			}
			
			InfoPdPList list = new InfoPdPList();
			list.setId_controllo(cuInserito.getId());
			list.buildList(db);
			context.getRequest().setAttribute("PdpList", list);

			
			if (!isValid)
			{
				return executeCommandModifyTicket(context);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if (resultCount == 1 && isValid) {
			//return "UpdateOK";
			
			context.getRequest().setAttribute("id", newTic.getId());
			context.getRequest().setAttribute("orgId", newTic.getOrgId());
						
			return executeCommandTicketDetails(context);
		}
		return (executeCommandDettaglio(context));
	}
	
	public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "acquedirete-vigilanza-delete")) {
			return ("PermissionError");
		}

		Connection db = null ;
		try 
		{	
			db = this.getConnection(context);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			context.getRequest().setAttribute("url_confirm","AcqueReteVigilanza.do?command=DeleteTicket");
			controlliGeneric.executeCommandConfirmDelete(context,db);

			return ("ConfirmDeleteOK");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		finally
		{
			this.freeConnection(context, db);
		}
	}

	
	public String executeCommandDeleteTicket(ActionContext context) {
		if (!hasPermission(context, "acquedirete-vigilanza-delete")) {
			return ("PermissionError");
		}
		boolean recordDeleted = false;
		Connection db = null;

		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));

		try {
			db = this.getConnection(context);
			Organization newOrg = new Organization(db, orgId);
			context.getRequest().setAttribute("OrgDetails", newOrg);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			context.getRequest().setAttribute("url","AcqueReteVigilanza.do?command=ViewVigilanza");

			controlliGeneric.executeCommandDeleteTicket(context,db);
			//SET TRASHED_DATE ANCHE PER GLI OPERATORI ASSOCIATI
			
			recordDeleted = (Boolean) context.getRequest().getAttribute("recordDeleted");


		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (recordDeleted) {
			return ("DeleteTicketOK");
		} else {
			return (executeCommandTicketDetails(context));
		}
	}

	/**
	 * Re-opens a ticket
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandReopenTicket(ActionContext context) {
		if (!hasPermission(context, "acquedirete-vigilanza-edit")) {
			return ("PermissionError");
		}
		int resultCount = -1;
		
		Connection db = null ;
		try {
			db = this.getConnection(context);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandReopenTicket(context,db);
			resultCount = (Integer)context.getRequest().getAttribute("resultCount");

		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally
		{
			this.freeConnection(context, db);
		}
		if (resultCount == -1) {
			return (executeCommandTicketDetails(context));
		} else if (resultCount == 1) {
			return (executeCommandTicketDetails(context));
		} else {
			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
	}
	
	/**
	 * Load the ticket details tab
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandTicketDetails(ActionContext context) {
		if (!hasPermission(context, "acquedirete-vigilanza-view")) {
			return ("PermissionError");
		}
		
		int ticketId = -1;
		int orgId = -1;
		
		String ticketIdString = context.getRequest().getParameter("id");
		String orgIdString = context.getRequest().getParameter("orgId");
		
		if (ticketIdString==null)
			ticketId = (int) context.getRequest().getAttribute("id");
		else
			ticketId = Integer.parseInt(ticketIdString);
		
		if (orgIdString==null)
			orgId = (int) context.getRequest().getAttribute("orgId");
		else
			orgId = Integer.parseInt(orgIdString);
		
		Connection db = null;
		//Parameters
		String retPag = null;
		PreparedStatement pst = null;
		String sql = null;
		String flagMod5 = null;
		ResultSet rs = null;
		try {
			db = this.getConnection(context);
			// Load the ticket
			int i = 0;
			Organization orgDetail = new Organization(db,orgId);
			
			//Read flag for mod5 from ticket table
			sql = "select flag_mod5 as flag from ticket where ticketid = ? ";
			pst=db.prepareStatement(sql);
			pst.setInt(++i,ticketId);
			rs = pst.executeQuery();
			if(rs.next()){
				 flagMod5 = rs.getString("flag");
			}
				
			
			context.getRequest().setAttribute("OrgDetails", orgDetail);      
			context.getRequest().setAttribute("siteId",orgDetail.getSiteId());
			context.getRequest().setAttribute("tipologia",14);
			context.getRequest().setAttribute("bozza", flagMod5);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			
			controlliGeneric.setIgnoraPunteggi(true); //ACQUE DI RETE: IGNORA PUNTEGGI
			
			controlliGeneric.executeCommandTicketDetails(context,db);
			
			Ticket cu = (Ticket)context.getRequest().getAttribute("TicketDetails");
			InfoPdPList list = new InfoPdPList();
			list.setId_controllo(cu.getId());
			list.buildList(db);
			context.getRequest().setAttribute("PdpList", list);
			retPag = "DettaglioOK";


		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		//return getReturn(context, "TicketDetails");
		return retPag;
	}
	
	

	//aggiunto da d.dauria
	public String executeCommandDeleteListaDistribuzione(ActionContext context) {
		if (!hasPermission(context, "acquedirete-vigilanza-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		try {

			String filePath = this.getPath(context, "accounts");
			//Process the form data
			HttpMultiPartParser multiPart = new HttpMultiPartParser();
			multiPart.setUsePathParam(false);
			multiPart.setUseUniqueName(true);
			multiPart.setUseDateForFolder(true);
			multiPart.setExtensionId(getUserId(context));
			HashMap parts = multiPart.parseData(context.getRequest(), filePath);
			context.getRequest().setAttribute("parts", parts);
			db = this.getConnection(context);

			String gotoInsert = (String)parts.get("gotoPage");
			String temporgId = (String)parts.get("orgId");
			int tempid = Integer.parseInt(temporgId);
			Organization newOrg = new Organization(db, tempid);
			SystemStatus systemStatus = this.getSystemStatus(context);
			context.getRequest().setAttribute("siteId", newOrg.getSiteId());
			context.getRequest().setAttribute("tipologia", 14);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandDeleteListaDistribuzione(context,db);
			context.getRequest().setAttribute("OrgDetawils", newOrg);

			if(!"insert".equals(gotoInsert))
			{
				return "ModifyOK";
			}
			else
			{

				if (((String)parts.get("actionSource")) != null) {
					context.getRequest().setAttribute("actionSource", (String)parts.get("actionSource"));
					return "AddTicketOK";
				}
				context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
				return ("AddOK");
			}

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}


	public String executeCommandUploadListaDistribuzione(ActionContext context) {
		if (!hasPermission(context, "acquedirete-vigilanza-add")) {
			return ("PermissionError");
		}
		Connection db = null;

		try {

			String filePath = this.getPath(context, "accounts");
			//Process the form data
			HttpMultiPartParser multiPart = new HttpMultiPartParser();
			multiPart.setUsePathParam(false);
			multiPart.setUseUniqueName(true);
			multiPart.setUseDateForFolder(true);
			multiPart.setExtensionId(getUserId(context));
			HashMap parts = multiPart.parseData(context.getRequest(), filePath);
			context.getRequest().setAttribute("parts", parts);
			db = this.getConnection(context);
			String temporgId = (String)parts.get("orgId");
			int tempid = Integer.parseInt(temporgId);
			Organization newOrg = new Organization(db, tempid);
			SystemStatus systemStatus = this.getSystemStatus(context);
			context.getRequest().setAttribute("siteId", newOrg.getSiteId());
			context.getRequest().setAttribute("tipologia", 14);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric =new  org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandUploadListaDistribuzione(context,db);

			//Load the organization
			context.getRequest().setAttribute("OrgDetails", newOrg);

			String gotoInsert 		=(String)parts.get("gotoPage");
			if("insert".equals(gotoInsert))
			{
				if (((String)parts.get("actionSource")) != null) {
					context.getRequest().setAttribute(
							"actionSource", (String)parts.get("actionSource"));
					return "AddTicketOK";
				}
				context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
				return ("AddOK");
			}
			else
			{
				return "ModifyOK";
			}

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);

			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}


	


	public String executeCommandViewVigilanza(ActionContext context) {

		if (!hasPermission(context, "acquedirete-vigilanza-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
		Organization newOrg = null;
		//Process request parameters
		int passedId = Integer.parseInt(
				context.getRequest().getParameter("orgId"));

		//Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(
				context, "AccountTicketInfo", "t.entered", "desc");
		ticketListInfo.setLink(
				"AcqueReteVigilanza.do?command=ViewVigilanza&orgId=" + passedId);
		ticList.setPagedListInfo(ticketListInfo);
		
		try {
			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			//find record permissions for portal users
			
			newOrg = new Organization(db, passedId);
			ticList.setOrgId(passedId);
			ticList.setOrg_id_pdp(passedId);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
			if (thisUser.getRoleId()==Role.RUOLO_CRAS)
				ticList.setIdRuolo(thisUser.getRoleId());
			
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
		return getReturn(context, "ViewVigilanza");
	}

	public String executeCommandDettaglio(ActionContext context) {
		if (!hasPermission(context, "acquedirete-vigilanza-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		String ticketId = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			String fromDefectDetails = context.getRequest().getParameter("defectCheck");
			if (fromDefectDetails == null || "".equals(fromDefectDetails.trim())) {
				fromDefectDetails = (String) context.getRequest().getAttribute("defectCheck");
			}

			// Parameters
			ticketId = context.getRequest().getParameter("id");
			// Reset the pagedLists since this could be a new visit to this ticket
			deletePagedListInfo(context, "TicketDocumentListInfo");
			deletePagedListInfo(context, "SunListInfo");
			deletePagedListInfo(context, "TMListInfo");
			deletePagedListInfo(context, "CSSListInfo");
			deletePagedListInfo(context, "TicketsFolderInfo");
			deletePagedListInfo(context, "TicketTaskListInfo");
			deletePagedListInfo(context, "ticketPlanWorkListInfo");
			db = this.getConnection(context);
			// Load the ticket
			newTic = new Ticket();
			newTic.queryRecord(db, Integer.parseInt(ticketId));

			

			Organization orgDetails = new Organization(db, newTic.getOrgId());
			
		

			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList EsitoCampione = new LookupList(db, "lookup_sanzioni_amministrative");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);


			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
			SanzioniPenali.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);
		
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("TicketDetails", newTic);
		addRecentItem(context, newTic);
		addModuleBean(context, "ViewTickets", "View Tickets");
		String retPage = "DettaglioOK";
		String tipo_richiesta = newTic.getTipo_richiesta();
		tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
		retPage = "DettaglioOK";

		return ( retPage );
	}



	public String executeCommandChiudiTutto(ActionContext context) {
		// public String executeCommandChiudi(ActionContext context) {
		if (!(hasPermission(context, "acquedirete-vigilanza-edit"))) {
			return ("PermissionError");
		}

		int resultCount = -1;
		Connection db = null;
		
		Ticket thisTicket = null;
		Ticket oldTicket = null;
		try {
			db = this.getConnection(context);

//			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
//			Ticket oldTicket = new Ticket(db, thisTicket.getId());
			
			thisTicket =  new org.aspcfs.modules.vigilanza.base.Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			oldTicket =  thisTicket;
			
			if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
				return ("PermissionError");
			}

			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			int flag=controlliGeneric.executeCommandChiudiTutto(context,db, thisTicket, oldTicket);

			if (flag == 4) {
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudiTemp(db);
				// return (executeCommandTicketDetails(context));
			}

			context.getRequest().setAttribute("Chiudi", "" + flag); if (flag == 0 || flag == 1 || flag == 3) {
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudi(db);
			}
			if (resultCount == -1) {
				return (executeCommandTicketDetails(context));
			} else if (resultCount == 1) {
				thisTicket.queryRecord(db, thisTicket.getId());
				this.processUpdateHook(context, oldTicket, thisTicket);
				context.getRequest().setAttribute("Chiudi", "" + flag);
				return (executeCommandTicketDetails(context));
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
		return ("UserError");
	}



	public String executeCommandChiudi(ActionContext context)
	{
		if (!(hasPermission(context, "acquedirete-vigilanza-edit"))){
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			Ticket oldTicket = new Ticket(db, thisTicket.getId());

			Integer idT = thisTicket.getId();
			String idCU = idT.toString(); 

			thisTicket.setClosed(new Timestamp(System.currentTimeMillis()));


			



			String ticketId = context.getRequest().getParameter("id");



			org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
			int passedId = thisTicket.getOrgId();
			ticList.setOrgId(passedId);
			ticList.buildListControlli(db, passedId, ticketId);


			org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
			int pasId = thisTicket.getOrgId();
			tamponiList.setOrgId(passedId);
			tamponiList.buildListControlli(db, pasId, ticketId);




			Iterator campioniIterator=ticList.iterator();
			Iterator tamponiIterator=tamponiList.iterator();



			int flag=0;
			while(campioniIterator.hasNext()){

				org.aspcfs.modules.campioni.base.Ticket tic = (org.aspcfs.modules.campioni.base.Ticket) campioniIterator.next();

				if(tic.getClosed()==null){
					flag=1;

					break;

				}

			}

			org.aspcfs.modules.prvvedimentinc.base.TicketList ticListProvvedimenti = new org.aspcfs.modules.prvvedimentinc.base.TicketList();
			int passId = thisTicket.getOrgId();
			ticListProvvedimenti.setOrgId(passId);
			ticListProvvedimenti.buildListControlli(db, passId, ticketId);
			Iterator provvedimentiIterator=ticListProvvedimenti.iterator();


			while(provvedimentiIterator.hasNext()){

				org.aspcfs.modules.prvvedimentinc.base.Ticket tic = (org.aspcfs.modules.prvvedimentinc.base.Ticket) provvedimentiIterator.next();

				if(tic.getClosed()==null){
					flag=1;

					break;
				}

			}

			while(tamponiIterator.hasNext()){

				org.aspcfs.modules.tamponi.base.Ticket tic = (org.aspcfs.modules.tamponi.base.Ticket) tamponiIterator.next();

				if(tic.getClosed()==null){
					flag=1;

					break;
				}

			}


			if(thisTicket.isNcrilevate()==true){
				org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
				int passIdN = thisTicket.getOrgId();
				nonCList.setOrgId(passedId);
				nonCList.buildListControlli(db, passIdN, ticketId);

				Iterator ncIterator=nonCList.iterator();

				while(ncIterator.hasNext()){

					org.aspcfs.modules.nonconformita.base.Ticket tic = (org.aspcfs.modules.nonconformita.base.Ticket) ncIterator.next();

					if(tic.getClosed()==null){
						flag=1;

						break;

					}

				}

			}


			if(thisTicket.getTipoCampione()==5){

				org.aspcfs.checklist.base.AuditList audit = new org.aspcfs.checklist.base.AuditList();
				int AuditOrgId = thisTicket.getOrgId();
				String idTi = thisTicket.getPaddedId();
				audit.setOrgId(AuditOrgId);

				audit.buildListControlli(db, AuditOrgId, idTi);

				Iterator itAudit=audit.iterator();

				if(!itAudit.hasNext()){

					flag=2;

				}else{

					while(itAudit.hasNext()){

						org.aspcfs.checklist.base.Audit auditTemp = (org.aspcfs.checklist.base.Audit) itAudit.next();
						Organization orgDetails = new Organization(db,thisTicket.getOrgId());

						if(thisTicket.isCategoriaisAggiornata()==false){
							flag=2;

							break;

						}

					}}
			}

			String attivitaCollegate=context.getRequest().getParameter("altro");

			if(attivitaCollegate==null){
				if(flag==1 || flag==2){

					context.getRequest().setAttribute("Chiudi", ""+flag);
					return (executeCommandTicketDetails(context));

				}
			}

			if(flag==0){
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudi(db);
			}
			if (resultCount == -1) {
				return ( executeCommandTicketDetails(context));
			} else if (resultCount == 1) {
				thisTicket.queryRecord(db, thisTicket.getId());
				this.processUpdateHook(context, oldTicket, thisTicket);
				return (executeCommandTicketDetails(context));
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
		return ("UserError");


	}
	
	public String executeCommandChiudiTemp(ActionContext context)
	{
		if (!(hasPermission(context, "acquedirete-vigilanza-edit"))){
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			Ticket oldTicket = new Ticket(db, thisTicket.getId());

			Integer idT = thisTicket.getId();
			String idCU = idT.toString(); 

		
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudiTemp(db);
			
			if (resultCount == -1) {
				return ( executeCommandTicketDetails(context));
			} else if (resultCount == 1) {
				thisTicket.queryRecord(db, thisTicket.getId());
				this.processUpdateHook(context, oldTicket, thisTicket);
				return (executeCommandTicketDetails(context));
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
		return ("UserError");


	}
	
	
	public int  CallDbiAcquaDiRete (Ticket newTic, ActionContext context, Connection db, int userId, String ip, String tipoCampione , String idPiano, String idIspezione, String[] tipoispezione ,	String[] ispezione, String[] idComponente, int indComp, String[] idUO, String[] idPianoM) throws SQLException, ParserConfigurationException, SAXException, IOException
	{
		int ticketID = 0;
		
		
		String dbiControlloUfficialeAcque = "Select * from public.insert_controllo_ufficiale_acque(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement stmt = db.prepareStatement(dbiControlloUfficialeAcque);
			
		 stmt.setInt(1,newTic.getOrgId());
 		 stmt.setInt(2,userId);
		 stmt.setInt(3, userId);
		 stmt.setTimestamp(4,newTic.getAssignedDate());
		 stmt.setInt(5, newTic.getSiteId());
		 stmt.setInt(6, newTic.getTipoCampione());  
	
	     stmt.setInt(7, newTic.getPunteggio() );
		 stmt.setTimestamp(8,newTic.getDataFineControllo());
		 stmt.setInt(9, newTic.getEsitoControllo());
		 stmt.setString(10,ip );
		 stmt.setString(11,ip );
		 stmt.setString(12,tipoCampione);
		 stmt.setString(13, newTic.getProblem());
		 
		 // 2 parte dbi
		 
		 InfoPdPList listPd =new InfoPdPList();
			listPd.buildListFromRequest(context);
			int sizeCampioni = listPd.size();
			String testoAnalita = "";
			int indice = 0;
			  String[] tempIdFoglia = new String[6];
			  String[] tempDescrProt=  new String[6];
			  
			  int dim = listPd.size();
			  String[] tempTemperatura=  new String[dim];
			  String[] tempCloro=  new String[dim];
			  String[] tempOre=  new String[dim];
			  String[] tempProtRoutine=  new String[dim];
			  String[] tempProtVerifica=  new String[dim];
			  String[] tempProtReplicaMicro=  new String[dim];
			  String[] tempProtReplicaChim=  new String[dim];
			  String[] tempAltro=  new String[dim];
			  String[] tempProtRadioAttivita=  new String[dim];
			  String[] tempProtRicercaFit=  new String[dim];
			  
	
			for ( int i = 0 ; i<dim; i++)
			{

				InfoPdP pdp = (InfoPdP) listPd.get(i);
				//org.aspcfs.modules.campioni.base.Ticket campione = new org.aspcfs.modules.campioni.base.Ticket();

				 testoAnalita = Nodo.getRamo(MATRICE_ACQUA_RETE, context, "matrici", db).getPath();
				  
				  //qui dbi per il campione e il controllo
				  
					  
					  for (int a=0; a<6; a++){
						  boolean selected = pdp.isProtocolloSelezionato(a);
						  if (selected){
							  Analita analita = new Analita();
							  int idFoglia = analita.getIdAnalitaDaNome(db, pdp.getDescrizioneProtocollo(a));
							  tempIdFoglia[indice] = idFoglia+"";
							  tempDescrProt[indice++] = pdp.getDescrizioneProtocollo(a);
							  //campione.insertAnaliti(db, idFoglia, pdp.getDescrizioneProtocollo(a));
						  }
					  }
					  
					  
					  tempTemperatura[i] = pdp.getTemperatura();
					  tempCloro[i]	= pdp.getCloro();
					  tempOre[i] =  pdp.getOre();
					  tempProtRoutine[i] = String.valueOf(pdp.isProt_routine());
					  tempProtVerifica[i] = String.valueOf( pdp.isProt_verifica());  
					  tempProtReplicaMicro[i] = String.valueOf( pdp.isProt_replica_micro());
					  tempProtReplicaChim[i] =  String.valueOf( pdp.isProt_replica_chim());
					  tempAltro[i] =  pdp.getAltro();
					  tempProtRadioAttivita[i] = String.valueOf( pdp.isProt_radioattivita());
					  tempProtRicercaFit[i] =  String.valueOf( pdp.isProt_ricerca_fitosanitari());
					  
			}
		 
						stmt.setInt(14, Integer.parseInt(idPiano));
						stmt.setInt(15, Integer.parseInt(idIspezione) );
					  
						Array intIdFoglia = db.createArrayOf("integer", tempIdFoglia);
						stmt.setArray(16,intIdFoglia);
					  
						Array stringsDescrProt = db.createArrayOf("text", tempDescrProt);
						stmt.setArray(17, stringsDescrProt);
					  
						stmt.setInt(18, MATRICE_ACQUA_RETE);
						stmt.setString(19, testoAnalita);
						stmt.setInt(20, indice);
						stmt.setInt(21, sizeCampioni);
						
						
						Array strTemp = db.createArrayOf("text", tempTemperatura);
						stmt.setArray(22,strTemp);
					  
						Array strCloro = db.createArrayOf("text", tempCloro);
						stmt.setArray(23, strCloro);
					  
						Array strOre= db.createArrayOf("text", tempOre);
						stmt.setArray(24,strOre);
					  
						Array boolProtRout = db.createArrayOf("boolean", tempProtRoutine);
						stmt.setArray(25, boolProtRout);
					  
						Array boolProtVer = db.createArrayOf("boolean", tempProtVerifica);
						stmt.setArray(26,boolProtVer);
					  
						Array boolProRepMic = db.createArrayOf("boolean", tempProtReplicaMicro);
						stmt.setArray(27, boolProRepMic);
					  
						Array booloProtRepCho = db.createArrayOf("boolean", tempProtReplicaChim);
						stmt.setArray(28,booloProtRepCho);
					  
						Array strAltro = db.createArrayOf("text", tempAltro);
						stmt.setArray(29, strAltro);
					  
						Array boolProtRadio = db.createArrayOf("boolean", tempProtRadioAttivita);
						stmt.setArray(30,boolProtRadio);
					  
						Array boolProtRic = db.createArrayOf("boolean", tempProtRicercaFit);
						stmt.setArray(31, boolProtRic);
						
						
						//Parametri per nucleo ispettivo
							
						
						Array tipoisp = db.createArrayOf("integer", tipoispezione);
						stmt.setArray(32, tipoisp);
						
						Array isp = db.createArrayOf("integer", ispezione);
						stmt.setArray(33, isp);
						
						Array componentiNucleo = db.createArrayOf("integer", idComponente);
						stmt.setArray(34, componentiNucleo);
						
						stmt.setInt(35, indComp);
						
						Array idUnitaOp = db.createArrayOf("integer", idUO);
						stmt.setArray(36, idUnitaOp);
						
						
						Array pianoM = db.createArrayOf("integer", idPianoM);
						stmt.setArray(37, pianoM);
						
						 System.out.println("Query"+stmt);

						 
						 ResultSet rs = stmt.executeQuery();
						 
						 if (rs.next())
						 {
							 ticketID = rs.getInt(1);
								String insert = " select * from public_functions.insert_linee_attivita_controlli_ufficiali(?,?,?,?,?,?)";
								PreparedStatement pstInsert =db.prepareStatement(insert);
								pstInsert.setInt(1, ticketID);
								pstInsert.setInt(2, newTic.getOrgId());
								pstInsert.setObject(3, null);
								pstInsert.setObject(4, "CONTROLLO ACQUE DI RETE");
								pstInsert.setObject(5, null);
								pstInsert.setInt(6,14);
								pstInsert.execute();
						 }
		
		
		return ticketID;
		
	}
	


}

