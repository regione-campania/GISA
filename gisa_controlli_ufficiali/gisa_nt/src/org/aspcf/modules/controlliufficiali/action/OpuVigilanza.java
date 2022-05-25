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
package org.aspcf.modules.controlliufficiali.action;
import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;

import org.aspcf.modules.controlliufficiali.base.AziendeZootFields;
import org.aspcf.modules.controlliufficiali.base.Mod5;
import org.aspcf.modules.controlliufficiali.base.MotivoIspezione;
import org.aspcf.modules.controlliufficiali.base.OggettoControllo;
import org.aspcf.modules.controlliufficiali.base.Organization;
import org.aspcf.modules.controlliufficiali.base.OrganizationUtente;
import org.aspcf.modules.controlliufficiali.base.Piano;
import org.aspcf.modules.controlliufficiali.base.Ticket.TipoOperatori;
import org.aspcf.modules.report.util.Filtro;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.allerte.base.AslCoinvolte;
import org.aspcfs.modules.allerte.base.GestionePEC;
import org.aspcfs.checklist.base.Audit;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.buffer.base.Buffer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import org.aspcfs.modules.campioni.base.Analita;
import org.aspcfs.modules.controller.base.Nodo;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.oia.base.OiaNodo; 
import org.aspcfs.modules.oia.base.UserInfo; 
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.RispostaDwrCodicePiano;
import org.aspcfs.utils.UtilityTemplateFile; 
import org.aspcfs.modules.soa.base.LineaAttivitaSoa; 
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.modules.vigilanza.base.TicketList;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletResponse;

 
/**
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *          $
 * @created August 15, 2001
 */
public final class OpuVigilanza extends CFSModule {

	Logger logger = Logger.getLogger("MainLogger");


	
	
  public String executeCommandReopenTicket(ActionContext context,Connection db) {
    
    int resultCount = -1;
    
    Ticket thisTicket = null;
    Ticket oldTicket = null;
    try {
      
      thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
      oldTicket = new Ticket(db, thisTicket.getId());
      
      thisTicket.setModifiedBy(getUserId(context));
      resultCount = thisTicket.reopen(db);
      thisTicket.queryRecord(db, thisTicket.getId());
    
      context.getRequest().setAttribute("resultCount",resultCount);  
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      
    }
    if (resultCount == -1) {
      return (executeCommandTicketDetails(context,db));
    } else if (resultCount == 1) {
      this.processUpdateHook(context, oldTicket, thisTicket);
      return (executeCommandTicketDetails(context,db));
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }


 
  public String executeCommandTicketDetails(ActionContext context,Connection db) 
  {
   
	 

   
    //Parameters
    String ticketId = context.getRequest().getParameter("id");
    if(ticketId==null)
    {
    	ticketId = (String)context.getRequest().getAttribute("id");
    }
    String retPag = null;
    try {
     
      
      	LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      	OrgCategoriaRischioList.buildList(db);
      	
      	
		OrgCategoriaRischioList.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
		
		context.getRequest().setAttribute("OrgCategoriaRischioListView", OrgCategoriaRischioList);
		
      // Load the ticket
      Ticket newTic = new Ticket();
     
      SystemStatus systemStatus = this.getSystemStatus(context);
      newTic.setSystemStatus(systemStatus);
      if(context.getRequest().getAttribute("tipologia")!=null)
      {
    	  Integer tipologia = (Integer)context.getRequest().getAttribute("tipologia");
    	 
    		   newTic.queryRecord(db, Integer.parseInt(ticketId));
    	 
      }
      else
      {
      	newTic.queryRecord(db, Integer.parseInt(ticketId));
      }
      
     
      
      /*Gestione controlli ufficiali per i laboratori haccp inseriti nei vari O.S.A*/

      newTic.getLabInRegioneControllatiList().buildListLaboratoriHACCP(db,ticketId);
      
      /*Gestione controlli ufficiali per i laboratori haccp inseriti nei vari O.S.A*/

      newTic.getLabNonInRegioneControllatiList().buildListLaboratoriHACCPNoReg(db, ticketId);
      /*COSTRUZIONE LINEE ATTIVITA PER IMPRESE*/
      ArrayList<String> linee_attivita = new ArrayList<String>();
      String idCu = ""+newTic.getId();
      ArrayList<LineeAttivita> lista_la = LineeAttivita.opu_load_linea_attivita_per_cu(idCu, db,newTic.getIdStabilimento());
      if (lista_la.size()>0)
      {
    	  for (LineeAttivita la : lista_la)
    	  {
    		  String idLineaAttivita = "" ;
    	  
    		  if (!la.getLinea_attivita().isEmpty())
    			  idLineaAttivita = la.getCodice_istat() + " : " + la.getCategoria() + " - " + la.getLinea_attivita();
    		  else
    			  idLineaAttivita = la.getCodice_istat() + " : " + la.getCategoria();
			// val[i] = linea.getCategoria();
      		linee_attivita.add(idLineaAttivita);
    	  }
    	  context.getRequest().setAttribute("linea_attivita", lista_la);
      }
      
      /*COSTRUZIONE BUFFER*/
      if(newTic.getCodiceBuffer() != null && !newTic.getCodiceBuffer().equals("")) {
    	  Buffer b = new Buffer(db,Integer.parseInt(newTic.getCodiceBuffer()));
    	  context.getRequest().setAttribute("BufferDetails", b);
      }
      
      UserBean user=(UserBean)context.getSession().getAttribute("User");
		 String nameContext=context.getRequest().getServletContext().getServletContextName();
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
				context.getRequest().setAttribute("View", "AutoritaNonCompetenti");
      
      /*COSTRUZIONE LINEE ATTIVITA PER STABILIMENTI E SOA*/
//      ArrayList<LineaAttivitaSoa> linee_attivita_stabilimenti = new ArrayList<LineaAttivitaSoa>();
//      ArrayList<LineaAttivitaSoa> linee_attivita_stabilimenti_desc = new ArrayList<LineaAttivitaSoa>();
//      String sel = "select * from linee_attivita_controlli_ufficiali_stab_soa where trashed_date is null and id_controllo_ufficiale = "+idCu;
//      PreparedStatement pst = db.prepareStatement(sel);
//      ResultSet rs = pst.executeQuery();
//      while (rs.next())
//      {
//    	  LineaAttivitaSoa l = new LineaAttivitaSoa () ;
//    	  l.setCategoria(rs.getString(2));
//    	  l.setImpianto(rs.getString(3));
//    	  linee_attivita_stabilimenti.add(l);
//    	  
//      }
//    		
//      if (linee_attivita_stabilimenti.size()>0)
//      {
//      context.getRequest().setAttribute("linee_attivita_stabilimenti", linee_attivita_stabilimenti);
//      context.getRequest().setAttribute("linee_attivita_stabilimenti_desc", linee_attivita_stabilimenti_desc);
//      }
//      
//      newTic.setFlagBloccoNonConformita(db,newTic.getId());
      
      org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
      int passedId = newTic.getIdStabilimento();
      ticList.setIdStabilimento(passedId);
      ticList.buildListControlli(db, passedId, ticketId);
      context.getRequest().setAttribute("TicList", ticList);
      
     
      int passeId = newTic.getIdStabilimento();
      
      org.aspcfs.modules.osservazioni.base.OsservazioniList ossList = new org.aspcfs.modules.osservazioni.base.OsservazioniList();
      int orgId = newTic.getIdStabilimento();
      ossList.setIdStabilimento(orgId);
      ossList.buildListControlli(db, passeId, ticketId);
      context.getRequest().setAttribute("OsservazioniList", ossList);
      
      org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
      int pasId = newTic.getIdStabilimento();
      tamponiList.setIdStabilimento(passedId);
      tamponiList.buildListControlli(db, pasId, ticketId);
      context.getRequest().setAttribute("TamponiList", tamponiList);
      
       
		org.aspcfs.modules.prvvedimentinc.base.TicketList ticListProvvedimenti = new org.aspcfs.modules.prvvedimentinc.base.TicketList();
		int paId = newTic.getIdStabilimento();
		ticListProvvedimenti.setIdStabilimento(paId);
		ticListProvvedimenti.buildListControlli(db, paId, ticketId);
		  context.getRequest().setAttribute("ProvvedimentiList", ticListProvvedimenti);
      
      
      /**
       * se il controllo non contiene campioni e tampo oppure
       * se il controllo contiene campioni e tamponi e sono tutti chiusi 
       * allora il controllo puo essere chiuso in maniera normale ; 
       * altrimenti puo essere chiuso in maniera temporanea in quanto in attesa di esito
       */
  
      
      if ((ticList.size()== 0 && tamponiList.size()==0 && newTic.getTipoCampione()==4) || newTic.getTipoCampione()==3)
      {
    	  newTic.setControllo_chiudibile(true);
      }
      else
      {
    	  if (newTic.getTipoCampione()!=3)
    	  {
    	  boolean campioni_chiusi = true ;
    	  boolean tamponi_chiusi = true ;
    	  
    	  if(ticList.size()!=0)
    	  {
    	  Iterator itCampioni = ticList.iterator();
    	  while (itCampioni.hasNext())
    	  {
    		  org.aspcfs.modules.campioni.base.Ticket campione = (org.aspcfs.modules.campioni.base.Ticket)itCampioni.next() ;
    		  for (Analita an :campione.getTipiCampioni())
				{
					if(an.getEsito_id()<=0)
					{

						campioni_chiusi = false ;
						break ;
					}
				}
    		  
          }
    	  }
    	  if(tamponiList.size()!=0)
    	  {
    	  Iterator itTamponi = tamponiList.iterator();
    	  while (itTamponi.hasNext())
    	  {
    		  org.aspcfs.modules.tamponi.base.Ticket tampone = (org.aspcfs.modules.tamponi.base.Ticket)itTamponi.next() ;
    		  if (tampone.getClosed()== null)
    		  {
    			  tamponi_chiusi = false ;
    			  break ;
    		  }
    		  
          }
    	  }
    	  if (campioni_chiusi == true && tamponi_chiusi == true)
    	  {
    		  newTic.setControllo_chiudibile(true);
    	  }
    	  else
    	  {
    		  newTic.setControllo_chiudibile(false);
    	  }
    	  }
      }
      
      
  	org.aspcfs.modules.sanzioni.base.TicketList sanzioniList = new org.aspcfs.modules.sanzioni.base.TicketList();
	int passId = -1 ; newTic.getOrgId();
	
	if(newTic.getIdStabilimento()>0)
	{
	sanzioniList.setIdStabilimento(newTic.getIdStabilimento());
	passId =  newTic.getIdStabilimento();
	}
	else
	{
		sanzioniList.setOrgId(newTic.getOrgId());
		passId =newTic.getOrgId();	
	}
	
	
	sanzioniList.buildListControlli(db, passId, ticketId,8);
	context.getRequest().setAttribute("SanzioniList", sanzioniList);
	
      newTic.setFlagBloccoNonConformita(db,newTic.getId());

     
      
      org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
      int passIdN = newTic.getIdStabilimento();
     
      nonCList.setIdStabilimento(passedId);
      nonCList.buildListControlli(db, passIdN, ticketId);
      context.getRequest().setAttribute("NonCList", nonCList);
      
      org.aspcfs.checklist.base.AuditList audit = new org.aspcfs.checklist.base.AuditList();
      int AuditOrgId = newTic.getIdStabilimento();
      String idT = newTic.getPaddedId();
      audit.setOrgId(AuditOrgId);
     
      audit.buildListControlli(db, AuditOrgId, idT);
      
      Iterator<Audit> itera=audit.iterator();
      int punteggioChecklist=0;
      boolean categoriaAggiornabile = true ;
      while(itera.hasNext())
      {
    	  Audit temp=itera.next();
    	  punteggioChecklist+=temp.getLivelloRischio();
    	  if ("temporanea".equalsIgnoreCase( temp.getStato()))
    	  {
    		  categoriaAggiornabile = false ;
    	  }
    	  
      }
      newTic.setCategoriaAggiornabile(categoriaAggiornabile);
      context.getRequest().setAttribute("PunteggioCheckList", punteggioChecklist);
      context.getRequest().setAttribute("Audit", audit);
      
      //find record permissions for portal users
//      if (!isRecordAccessPermitted(context, db, newTic.getIdStabilimento())) 
//      {
//        return ("PermissionError");
//      }
      
      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
      categoriaRischioList.removeElementByLevel(2);
      String tipoAudit= newTic.getTipoAudit()+"";
      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
      
      LookupList categoriaRischioList2 = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList2.addItem(-1, "-- SELEZIONA VOCE --");
      categoriaRischioList2.removeElementByLevel(1);
      
      categoriaRischioList2.buildListWithEnabled(db);
       context.getRequest().setAttribute("OrgCategoriaRischioList2", categoriaRischioList2);
      
        
      int siteId 	= -1;
      if (context.getRequest().getAttribute("siteId") != null)
      {
    	   siteId 		= (Integer)context.getRequest().getAttribute("siteId");
      }
      int tipologia	 	= -1;
      if (context.getRequest().getAttribute("tipologia") != null)
      {
    	   tipologia	 	= (Integer)context.getRequest().getAttribute("tipologia");
      }
     
      ControlliUfficialiUtil.buildLookupTipoControlloUfficiale(db, context, systemStatus, user.getRoleId(), siteId,-1,null);
     

      
      
      if(newTic.getIdFileAllegato()!=-1 && (Integer)newTic.getIdFileAllegato()!=0)
      {
    	  newTic.setListaDistribuzioneAllegata(true);
    	 if (newTic.getIdFileAllegato() != 0 && newTic.getIdFileAllegato() != -1){
    	  FileItem thisItem = new FileItem(
          db, newTic.getIdFileAllegato(), newTic.getIdStabilimento(), Constants.ACCOUNTS);
    	  context.getRequest().setAttribute("fileItem", thisItem);
    	 }
      } 
      else
      {
    	  newTic.setListaDistribuzioneAllegata(false);
      }
      newTic.setTipologia_operatore(tipologia);
      newTic.setPermission();
      context.getRequest().setAttribute("TicketDetails", newTic);
      addRecentItem(context, newTic);
      addModuleBean(context, "View Accounts", "View Tickets");
      deletePagedListInfo(context, "AccountTicketsFolderInfo");
      deletePagedListInfo(context, "AccountTicketDocumentListInfo");
      deletePagedListInfo(context, "AccountTicketTaskListInfo");
      deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
      String browser = context.getRequest().getHeader("User-Agent");
		
     
    } 
    catch (Exception errorMessage) 
    {
    	errorMessage.printStackTrace();
    	context.getRequest().setAttribute("Error", errorMessage);
    	errorMessage.printStackTrace();
    	return ("SystemError");
    } 
   
	
    return "";
  }

  


 
  public String executeCommandConfirmDelete(ActionContext context,Connection db) {
   
    String id = context.getRequest().getParameter("id");
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    try 
    {
      
      SystemStatus systemStatus = this.getSystemStatus(context);
      Ticket ticket = new Ticket(db, Integer.parseInt(id));
      String codiceAllerta = context.getRequest().getParameter("codiceallerta");
      DependencyList dependencies = ticket.processDependencies(db);
      //Prepare the dialog based on the dependencies
      HtmlDialog htmlDialog = new HtmlDialog();
      dependencies.setSystemStatus(systemStatus);
      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
      htmlDialog.addMessage(
      systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
      htmlDialog.addButton(systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='"+context.getRequest().getAttribute("url_confirm")+"&codiceallerta=" +codiceAllerta + "&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId") + "'");
      htmlDialog.addButton(
      systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
      context.getSession().setAttribute("Dialog", htmlDialog);
      return "";
    } 
    catch (Exception errorMessage) 
    {
    	errorMessage.printStackTrace();
    	context.getRequest().setAttribute("Error", errorMessage);
    	return ("SystemError");
    } 
   
  }


  /**
   * Delete the specified ticket
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDeleteTicket(ActionContext context,Connection db) {
  
    boolean recordDeleted = false;
   
    //Parameters
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    String passedId = context.getRequest().getParameter("id");
    try 
    {
      
      Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
      recordDeleted = thisTic.logicDelete(db, getDbNamePath(context));
      if (recordDeleted) 
      {
        processDeleteHook(context, thisTic);
        String inline = context.getRequest().getParameter("popupType");
        context.getRequest().setAttribute("refreshUrl", ""+context.getRequest().getAttribute("url")+"&idStabilimentoopu="+thisTic.getIdStabilimento()+"&orgId=" + orgId + (inline != null && "inline".equals(inline.trim()) ? "&popup=true" : ""));
        String codiceAllerta=(String)context.getRequest().getParameter("codiceallerta");
	  	if(thisTic.getTipoIspezioneCodiceInterno().contains("7a"))
	    {
	  	  if(thisTic.getIdFileAllegato()!=-1)
	      {
	  			FileItem thisItem = new FileItem(db, thisTic.getIdFileAllegato(), thisTic.getIdStabilimento(), Constants.ACCOUNTS);
	    		//recordDeleted = thisItem.delete(db, this.getPath(context, "accounts"));
	      }
	    }
	  }
      
      context.getRequest().setAttribute("recordDeleted", recordDeleted);
      
          
    } 
    catch (Exception errorMessage) 
    {
      context.getRequest().setAttribute("Error", errorMessage);
      errorMessage.printStackTrace();
      return ("SystemError");
    } 
    
   return "" ;
  }




  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandRestoreTicket(ActionContext context,Connection db) {

   
    boolean recordUpdated = false;
    
    Ticket thisTicket = null;
    try {
      
      thisTicket = new Ticket(
          db, Integer.parseInt(context.getRequest().getParameter("id")));
      //check permission to record
      
      thisTicket.setModifiedBy(getUserId(context));
      recordUpdated = thisTicket.updateStatus(db, false, this.getUserId(context));
      context.getRequest().setAttribute("recordUpdated", recordUpdated);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      
    } 
    return "";
    
  }

  public String executeCommandSupervisiona(ActionContext context,Connection db) throws NumberFormatException, SQLException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException  {
	   
	Ticket newTic = new Ticket(db,Integer.parseInt(context.getRequest().getParameter("id")));
	Timestamp time = new Timestamp(System.currentTimeMillis());
	newTic.setSupervisionato_in_data(time);
	
	Stabilimento newStabilimento = new Stabilimento(db,  Integer.parseInt(context.getParameter("idStabilimento")));
	context.getRequest().setAttribute("OrgDetails", newStabilimento);
	
	if ("si".equalsIgnoreCase( context.getParameter("flag_supervisione_efficace")))
		
			newTic.setSupervisione_flag_congruo(true);
	else
		newTic.setSupervisione_flag_congruo(false);
	newTic.setSupervisione_note(context.getParameter("supervisione_note"));
	newTic.setSupervisionato_da(getUserId(context));
	newTic.setSupervisione(db);
	return "" ;

  }

  /**
   * Update the specified ticket
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
 * @throws ParseException 
   */
  public String executeCommandUpdateTicket(ActionContext context,Connection db) throws ParseException {
    
   
    int resultCount = 0;
    boolean isValid = true;
    
    Ticket newTic = null;
    
    try
    {
    	
    newTic = (Ticket) context.getFormBean();
    UserBean user = (UserBean) context.getSession().getAttribute("User");
    String ip = user.getUserRecord().getIp();
    newTic.setIpEntered(ip);
    newTic.setIpModified(ip);
    newTic.setEnteredBy(getUserId(context));
    newTic.setModifiedBy(getUserId(context));
    Ticket oldTic = new Ticket();
    String esitoDeclassamento = context.getRequest().getParameter("esito") ;
    newTic.setEsitoDeclassamento(esitoDeclassamento);
//    if(context.getRequest().getParameter("declassamento")!=null)
//    {
//    	int declassamento = Integer.parseInt(context.getRequest().getParameter("declassamento"));
//    	newTic.setDeclassamento(declassamento);
//    }
    newTic.setData_preavviso(context.getParameter("data_preavviso"));
    newTic.setData_comunicazione_svincolo(context.getParameter("data_comunicazione_svincolo"));
    if(context.getParameter("peso") != null){
    	newTic.setPeso(Double.parseDouble(context.getParameter("peso")));
    }
    
    newTic.setProtocollo_preavviso(context.getParameter("protocollo_preavviso"));
    newTic.setProtocollo_svincolo(context.getParameter("protocollo_svincolo"));
    newTic.setTipologia_sottoprodotto(context.getParameter("tipologia_sottoprodotto"));
    if(context.getRequest().getParameter("nome_conducente")!=null)
    	newTic.setNome_conducente(context.getRequest().getParameter("nome_conducente"));
    if(context.getRequest().getParameter("cognome_conducente")!=null)
    	newTic.setCognome_conducente(context.getRequest().getParameter("cognome_conducente"));
    if(context.getRequest().getParameter("documento_conducente")!=null)
    	newTic.setDocumento_conducente(context.getRequest().getParameter("documento_conducente"));
    if(context.getRequest().getParameter("assetId")!=null)
    	newTic.setAssetId(Integer.parseInt(context.getRequest().getParameter("assetId")));
    /*if(context.getRequest().getParameter("codici_selezionabili")!=null){
    	newTic.setCodiceAteco(context.getRequest().getParameter("codici_selezionabili"));
    }*/
    
    if(context.getRequest().getParameter("quantitativo")!=null)
    	newTic.setQuantitativo(Integer.parseInt(context.getRequest().getParameter("quantitativo")));
 
    if(context.getRequest().getParameter("quintali")!=null)
    	newTic.setQuintali(Double.parseDouble(context.getRequest().getParameter("quintali")));
    
    if(context.getRequest().getParameter("tipologia_cu")!=null)
    	newTic.setTipologia_controllo_cani(Integer.parseInt(context.getRequest().getParameter("tipologia_cu")));
  
    if (context.getRequest().getParameter("data_nascita_conducente")!=null )
    {
    	newTic.setData_nascita_conducente(context.getRequest().getParameter("data_nascita_conducente"));
    }
    if (context.getRequest().getParameter("luogo_nascita_conducente")!=null )
    {
    	newTic.setLuogo_nascita_conducente(context.getRequest().getParameter("luogo_nascita_conducente"));
    }
    if (context.getRequest().getParameter("citta_conducente")!=null )
    {
    	newTic.setCitta_conducente(context.getRequest().getParameter("citta_conducente"));
    }
    if (context.getRequest().getParameter("indirizzo_conducente")!=null )
    {
    	newTic.setVia_connducente(context.getRequest().getParameter("indirizzo_conducente"));
    }
    if (context.getRequest().getParameter("cap_conducente")!=null && ! "".equals(context.getRequest().getParameter("cap_conducente")))
    {
    	newTic.setCap_conducente(context.getRequest().getParameter("cap_conducente"));
    }
    if (context.getRequest().getParameter("provincia_conducente")!=null )
    {
    	newTic.setProv_conducente(context.getRequest().getParameter("provincia_conducente"));
    }

    
    if (context.getRequest().getParameter("azione")!=null)
    {
    	if (context.getRequest().getParameter("azione").equals("0"))
    	{
    		newTic.setAzione(false);
    		
    	}
    	else
    	{
    		newTic.setAzione(true);
    		newTic.setAzione_descrizione(context.getRequest().getParameter("azione_descrizione"));
    	}
    }

    newTic.setIspezioni_desc1(context.getRequest().getParameter("ispezioni_desc1"));
    newTic.setIspezioni_desc2(context.getRequest().getParameter("ispezioni_desc2"));
    newTic.setIspezioni_desc3(context.getRequest().getParameter("ispezioni_desc3"));
    newTic.setIspezioni_desc4(context.getRequest().getParameter("ispezioni_desc4"));
    newTic.setIspezioni_desc5(context.getRequest().getParameter("ispezioni_desc5"));
    newTic.setIspezioni_desc6(context.getRequest().getParameter("ispezioni_desc6"));
    newTic.setIspezioni_desc7(context.getRequest().getParameter("ispezioni_desc7"));
    newTic.setIspezioni_desc8(context.getRequest().getParameter("ispezioni_desc8"));
    
    if( context.getRequest().getParameter("text_ispezione_altro")!=null ){
    	newTic.setIspezioneAltro(context.getRequest().getParameter("text_ispezione_altro"));
    }
    
    newTic.setCodiceAllerta(context.getRequest().getParameter("idAllerta"));
    
    newTic.setCodiceBuffer(context.getRequest().getParameter("id_buffer"));


    if(context.getRequest().getParameter("dataSintomi")!=null){
    	newTic.setDataSintomi(context.getRequest().getParameter("dataSintomi"));
    }
    if(context.getRequest().getParameter("alimenti_sospetti")!=null){
    	newTic.setAlimentiSospetti(context.getRequest().getParameter("alimenti_sospetti"));
    }
    
    if(context.getRequest().getParameter("dataPasto")!=null){
    	newTic.setDataPasto(context.getRequest().getParameter("dataPasto"));
    }
    if(context.getRequest().getParameter("ncrilevate")!=null)
    {
    	if(context.getRequest().getParameter("ncrilevate").equals("1"))
    		newTic.setNcrilevate(true);
    	else
    		newTic.setNcrilevate(false);
    }
    
    newTic.setId(Integer.parseInt(context.getRequest().getParameter("id")));
    
    	SystemStatus systemStatus = this.getSystemStatus(context);	
      //UserBean user = (UserBean) context.getSession().getAttribute("User");
      
      int siteId 		= (Integer)context.getRequest().getAttribute("siteId");
      int tipologia	 	= (Integer)context.getRequest().getAttribute("tipologia");
      ControlliUfficialiUtil.buildLookupTipoControlloUfficiale(db, context, systemStatus, user.getRoleId(), siteId, tipologia, "Es. Commerciale");
      
      oldTic = new Ticket(db, newTic.getId());
      
       String ticketId = context.getRequest().getParameter("id");
       
     
        org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
        int passedId = newTic.getIdStabilimento();
        ticList.setOrgId(passedId);
        ticList.buildListControlli(db, passedId, ticketId);
        context.getRequest().setAttribute("TicList", ticList);
        
         
        
        org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
        int pasId = newTic.getIdStabilimento();
        tamponiList.setOrgId(passedId);
        tamponiList.buildListControlli(db, pasId, ticketId);
        context.getRequest().setAttribute("TamponiList", tamponiList);
        
        org.aspcfs.modules.sanzioni.base.TicketList sanzioniList = new org.aspcfs.modules.sanzioni.base.TicketList();
        int passId = newTic.getIdStabilimento();
        sanzioniList.setOrgId(passedId);
        sanzioniList.buildListControlli(db, passId, ticketId,8);
        context.getRequest().setAttribute("SanzioniList", sanzioniList);
        
        org.aspcfs.modules.reati.base.TicketList reatiList = new org.aspcfs.modules.reati.base.TicketList();
        int passIdR = newTic.getIdStabilimento();
        reatiList.setOrgId(passedId);
        reatiList.buildListControlli(db, passIdR, ticketId,8);
        context.getRequest().setAttribute("ReatiList", reatiList);
        
        org.aspcfs.modules.sequestri.base.TicketList seqList = new org.aspcfs.modules.sequestri.base.TicketList();
        int passIdS = newTic.getIdStabilimento();
        seqList.setOrgId(passedId);
        seqList.buildListControlli(db, passIdS, ticketId,8);
        context.getRequest().setAttribute("SequestriList", seqList);
        
        org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
        int passIdN = newTic.getIdStabilimento();
        nonCList.setOrgId(passedId);
        nonCList.buildListControlli(db, passIdN, ticketId);
        context.getRequest().setAttribute("NonCList", nonCList);
        

        org.aspcfs.modules.osservazioni.base.OsservazioniList ossList = new org.aspcfs.modules.osservazioni.base.OsservazioniList();
        int orgId = newTic.getIdStabilimento();
        ossList.setOrgId(orgId);
        ossList.buildListControlli(db, passIdN, ticketId);
        context.getRequest().setAttribute("OsservazioniList", ossList);

        //In caso di aggiornamento ricostruisco la lista dei laboratori
		//
		String size_lab = context.getRequest().getParameter("dim_lab");
		//svuota la attuale lista operatori
		newTic.deleteCULaboratori(db, Integer.parseInt(context.getRequest().getParameter("id")));
		
		if(size_lab != null && size_lab != "0" && !size_lab.equals("0")){
			ArrayList<Integer> orgIdList = new ArrayList<Integer>();
			
				//Recupero iesimo org_id_op
				if(context.getRequest().getParameterValues("org_id_lab") != null){
					String[] lab_in_reg = context.getRequest().getParameterValues("org_id_lab") ;
					for(int i=0; i<lab_in_reg.length; i++)
					{
						if(!lab_in_reg[i].equals(""))
						{
							int org_id_op = Integer.parseInt(lab_in_reg[i]);
							orgIdList.add(org_id_op);
							//Inserimento operatore selezionato in fase di modifica
							newTic.insertCULaboratori(db, org_id_op);
						}
				}
			}	
		}
		
		//Laboratori fuori regione
		String size_lab_noreg = context.getRequest().getParameter("dim_lab_noreg");
		//svuota la attuale lista operatori
		newTic.deleteCULaboratoriNoReg(db, Integer.parseInt(context.getRequest().getParameter("id")));
		
		if(size_lab_noreg!= null && size_lab_noreg != "0" && !size_lab_noreg.equals("0")){
			ArrayList<String> listLab = new ArrayList<String>();
			
				//Recupero iesimo org_id_op
				if(context.getRequest().getParameterValues("text_lab") != null){
					String[] lab_non_reg = context.getRequest().getParameterValues("text_lab") ;
					
					for(int i=0; i<lab_non_reg.length; i++)
					{
					String text_lab = lab_non_reg[i];
					if (! "".equals(text_lab))
					{
						text_lab = text_lab.replaceAll("u00E0", "a");
						text_lab = text_lab.replaceAll("u00E8", "e");
						text_lab = text_lab.replaceAll("u00f2", "o");
						text_lab = text_lab.replaceAll("u00f9", "u");
						listLab.add(text_lab);
						//Inserimento operatore selezionato in fase di modifica
						newTic.insertCULaboratoriNoReg(db, text_lab);
					}
				}
				 
				
			}	
		}
		
		
		
		TicketList labList = newTic.getLabInRegioneControllatiList();
		labList.buildListLaboratoriHACCP(db,ticketId);
		//context.getRequest().setAttribute("TicListLab", labList);
		newTic.setElencoLabInRegioneControllati(labList);
		
		TicketList labListNoReg = newTic.getLabNonInRegioneControllatiList();
		labListNoReg.buildListLaboratoriHACCPNoReg(db,ticketId);
		//context.getRequest().setAttribute("TicListLab", labList);
		newTic.setElencoLabNonInRegioneControllati(labListNoReg);
        
        org.aspcfs.checklist.base.AuditList audit = new org.aspcfs.checklist.base.AuditList();
        int AuditOrgId = newTic.getIdStabilimento();
        String idT = newTic.getPaddedId();
        audit.setOrgId(AuditOrgId);
       
        
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
       
        
        audit.buildListControlli(db, AuditOrgId, idT);
        context.getRequest().setAttribute("Audit", audit);

      //Get the previousTicket, update the ticket, then send both to a hook
      if(context.getRequest().getParameter("contributi")!=null && !context.getRequest().getParameter("contributi").equals(""))
      {
	    	
	    	newTic.setContributi_seguito_campioni_tamponi(Double.parseDouble(context.getRequest().getParameter("contributi")));
	    }
      
      if(context.getRequest().getParameter("contributi_risol_nc") != null && !context.getRequest().getParameter("contributi_risol_nc").equals("")){
	    	
	    	newTic.setContributi_verifica_risoluzione_nc(Double.parseDouble(context.getRequest().getParameter("contributi_risol_nc")));
	    }
	    
	    if(context.getRequest().getParameter("contributi_macellazione")!= null && !context.getRequest().getParameter("contributi_macellazione").equals("")){
	    	
	    	newTic.setContributi_macellazione(Double.parseDouble(context.getRequest().getParameter("contributi_macellazione")));
	    }
	    if(context.getRequest().getParameter("contributi_macellazione_urgenza")!= null && !context.getRequest().getParameter("contributi_macellazione_urgenza").equals("")){
	    	
	    	newTic.setContributi_macellazione_urgenza(Double.parseDouble(context.getRequest().getParameter("contributi_macellazione_urgenza")));
	    }
	    
	    if(context.getRequest().getParameter("contributi_rilascio_certificazione")!= null && !context.getRequest().getParameter("contributi_rilascio_certificazione").equals("")){
	    	
	    	newTic.setContributi_rilascio_certificazione(Double.parseDouble(context.getRequest().getParameter("contributi_rilascio_certificazione")));
	    }

	    if (context.getRequest().getParameter("tipoSosp")!=null && !(context.getRequest().getParameter("tipoSosp").equals(""))){
	    	if(Integer.parseInt(context.getRequest().getParameter("tipoSosp")) == 1)
	    		newTic.setTipoSospetto("Per emergenza ambientale");
	    	else 
	    		newTic.setTipoSospetto("Per altro motivo");
	    }
	    
	    if(context.getRequest().getParameter("contributi_allarme_rapido")!= null && !context.getRequest().getParameter("contributi_allarme_rapido").equals("")){
	
	    	newTic.setContributi_allarme_rapido(Double.parseDouble(context.getRequest().getParameter("contributi_allarme_rapido")));
	    }
	    if(context.getRequest().getParameter("contributi_importazione_scambio")!= null && !context.getRequest().getParameter("contributi_importazione_scambio").equals("")){
	    	
	    	newTic.setContributi_importazione_scambio(Double.parseDouble(context.getRequest().getParameter("contributi_importazione_scambio")));
	    }
	    
      
	    newTic.setDestinazioneDistribuzione(Integer.parseInt(context.getRequest().getParameter("destinazioneDistribuzione")));
	    newTic.setUnitaMisura(context.getRequest().getParameter("unitaMisura"));
	    if("0".equals(context.getRequest().getParameter("comunicazioneRischio1")) )
	    {
	    	newTic.setComunicazioneRischio(true);
	    	newTic.setNoteRischio(context.getRequest().getParameter("noteRischio"));
	    }
	    else
	    {
	    	newTic.setComunicazioneRischio(false);	
	    }
	    
	    if("0".equals(context.getRequest().getParameter("procedureRitiro")))
	    {
	    	newTic.setProceduraRitiro(context.getRequest().getParameter("procedureRitiro"));	
	    }
	    else
	    {
	    	newTic.setProceduraRitiro(context.getRequest().getParameter("procedureRitiro"));
	    }
	    
	    newTic.setProceduraRichiamo(Integer.parseInt(context.getRequest().getParameter("procedureRichiamo")));
	    int oldProceduraRichiamo = Integer.parseInt(context.getRequest().getParameter("procedureRichiamo"));
	    
	    newTic.setEsitoControllo(Integer.parseInt(context.getRequest().getParameter("esitoControllo")));
	  
	    	
	   
	   
	    if(newTic.getEsitoControllo()==7)
	    {
	    	newTic.setNumDDt(context.getRequest().getParameter("numDdt"));
	    	String dataDdt = context.getRequest().getParameter("dataddt");
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    	Timestamp time = new Timestamp(sdf.parse(dataDdt).getTime());
	    	newTic.setQuantitaPartita(context.getRequest().getParameter("quantita")); 
	    	newTic.setDataDdt(time);
	    	
	    }
	    else
	    {
	    	if(newTic.getEsitoControllo()==4 || newTic.getEsitoControllo()==5 || newTic.getEsitoControllo()==6 || newTic.getEsitoControllo()==8)
	    	{
	    		  newTic.setQuantitaPartita(context.getRequest().getParameter("quantita")); 
	    	}
	    	else
	    	{
	    		if(newTic.getEsitoControllo()==10 || newTic.getEsitoControllo()==11 || newTic.getEsitoControllo()==14)
		    	{
	    			newTic.setQuantitaBloccata(context.getRequest().getParameter("quantitaBloccata"));
		    	}
	    	}
	    }
	    
	    String[] azioniAdottate = context.getRequest().getParameterValues("azioniAdottate");
	    if(context.getRequest().getParameter("idFile")!=null && !context.getRequest().getParameter("idFile").equals("-1") )
	    {
	    	newTic.setIdFileAllegato(Integer.parseInt(context.getRequest().getParameter("idFile")));
	    }
	    else
	    {
	    	newTic.setIdFileAllegato(-1);
	    }
	    	newTic.setAzioneArticolo(Integer.parseInt(context.getRequest().getParameter("articoliAzioni")));
	    	
	    	int elementi = Integer.parseInt(context.getRequest().getParameter("elementi"));
		      
	    	for (int i = 0 ; i < elementi ; i++)
			{
				int c = i+1;
				String medici = "";
				String tpal = "";
				String veterinati = "";
				String userId ="-1";
				String altri = "";
				if (context.getRequest().getParameter("nucleo_ispettivo_"+c)!=null && ! "-1".equals(context.getRequest().getParameter("nucleo_ispettivo_"+c)))
				{
					int nucleo = Integer.parseInt(context.getRequest().getParameter("nucleo_ispettivo_"+c));

					userId = context.getRequest().getParameter("risorse_"+c);
					veterinati = context.getRequest().getParameter("componenteid_"+c);
					altri = context.getRequest().getParameter("Utente_"+c);
					if (userId != null && Integer.parseInt(userId)>0)
						setComponentiNucleo(newTic, veterinati, nucleo,userId, i);
					else	
						setComponentiNucleo(newTic, altri, nucleo,"-1", i);







				}

			}
		      
		      
	    	  /*GESTIONE CAMPI AGGIUNTIVI PER AZIENDE ZOOTECNICHE*/
	    	  AziendeZootFields fields = new AziendeZootFields();
	          fields.setId_controllo(Integer.parseInt(ticketId));
			  fields.setId_allevamento(newTic.getIdStabilimento()); 
			  boolean gestione_campi = false;
			      if(context.getRequest().getParameter("num_tot_animali_presenti") != null && !context.getRequest().getParameter("num_tot_animali_presenti").equals("")){
			    	  fields.setNum_tot_animali_presenti(Integer.parseInt(context.getRequest().getParameter("num_tot_animali_presenti")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("cap_max_animali") != null && !context.getRequest().getParameter("cap_max_animali").equals("")){
			    	  fields.setCap_max_animali(Integer.parseInt(context.getRequest().getParameter("cap_max_animali")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_animali_isp") != null && !context.getRequest().getParameter("num_animali_isp").equals("")){
			    	  fields.setNum_animali_isp(Integer.parseInt(context.getRequest().getParameter("num_animali_isp")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_tot_capannoni") != null && !context.getRequest().getParameter("num_tot_capannoni").equals("")){
			    	  fields.setNum_tot_capannoni(Integer.parseInt(context.getRequest().getParameter("num_tot_capannoni")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_tot_capannoni_isp") != null && !context.getRequest().getParameter("num_tot_capannoni_isp").equals("")){
			    	  fields.setNum_tot_capannoni_isp(Integer.parseInt(context.getRequest().getParameter("num_tot_capannoni_isp")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_capannoni_con_gabbie") != null && !context.getRequest().getParameter("num_capannoni_con_gabbie").equals("")){
			    	  fields.setNum_capannoni_con_gabbie(Integer.parseInt(context.getRequest().getParameter("num_capannoni_con_gabbie")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_capannoni_non_gabbie") != null && !context.getRequest().getParameter("num_capannoni_non_gabbie").equals("")){
			    	  fields.setNum_capannoni_non_gabbie(Integer.parseInt(context.getRequest().getParameter("num_capannoni_non_gabbie")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_tot_vitelli_inf_8_settimane") != null && !context.getRequest().getParameter("num_tot_vitelli_inf_8_settimane").equals("")){
			    	  fields.setNum_tot_vitelli_inf_8_settimane(Integer.parseInt(context.getRequest().getParameter("num_tot_vitelli_inf_8_settimane")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_tot_box") != null && !context.getRequest().getParameter("num_tot_box").equals("")){
			    	  fields.setNum_tot_box(Integer.parseInt(context.getRequest().getParameter("num_tot_box")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_tot_box_isp") != null && !context.getRequest().getParameter("num_tot_box_isp").equals("")){
			    	  fields.setNum_tot_box_isp(Integer.parseInt(context.getRequest().getParameter("num_tot_box_isp")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_verri") != null && !context.getRequest().getParameter("num_verri").equals("")){
			    	  fields.setNum_verri(Integer.parseInt(context.getRequest().getParameter("num_verri")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("cap_max_verri") != null && !context.getRequest().getParameter("cap_max_verri").equals("")){
			    	  fields.setCap_max_verri(Integer.parseInt(context.getRequest().getParameter("cap_max_verri")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_verri_isp") != null && !context.getRequest().getParameter("num_verri_isp").equals("")){
			    	  fields.setNum_verri_isp(Integer.parseInt(context.getRequest().getParameter("num_verri_isp")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_scrofe_scrofette") != null && !context.getRequest().getParameter("num_scrofe_scrofette").equals("")){
			    	  fields.setNum_scrofe_scrofette(Integer.parseInt(context.getRequest().getParameter("num_scrofe_scrofette")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("cap_max_scrofe_scrofette") != null && !context.getRequest().getParameter("cap_max_scrofe_scrofette").equals("")){
			    	  fields.setCap_max_scrofe_scrofette(Integer.parseInt(context.getRequest().getParameter("cap_max_scrofe_scrofette")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_scrofe_scrofette_isp") != null && !context.getRequest().getParameter("num_verri_isp").equals("")){
			    	  fields.setNum_scrofe_scrofette_isp(Integer.parseInt(context.getRequest().getParameter("num_verri_isp")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_lattonzoli") != null && !context.getRequest().getParameter("num_lattonzoli").equals("")){
			    	  fields.setNum_lattonzoli(Integer.parseInt(context.getRequest().getParameter("num_lattonzoli")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("cap_max_lattonzoli") != null && !context.getRequest().getParameter("cap_max_lattonzoli").equals("")){
			    	  fields.setCap_max_lattonzoli(Integer.parseInt(context.getRequest().getParameter("cap_max_lattonzoli")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_lattonzoli_isp") != null && !context.getRequest().getParameter("num_verri_isp").equals("")){
			    	  fields.setNum_lattonzoli_isp(Integer.parseInt(context.getRequest().getParameter("num_verri_isp")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_suinetti") != null && !context.getRequest().getParameter("num_suinetti").equals("")){
			    	  fields.setNum_suinetti(Integer.parseInt(context.getRequest().getParameter("num_suinetti")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("cap_max_suinetti") != null && !context.getRequest().getParameter("cap_max_suinetti").equals("")){
			    	  fields.setCap_max_suinetti(Integer.parseInt(context.getRequest().getParameter("cap_max_suinetti")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_suinetti_isp") != null && !context.getRequest().getParameter("num_suinetti_isp").equals("")){
			    	  fields.setNum_suinetti_isp(Integer.parseInt(context.getRequest().getParameter("num_suinetti_isp")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_suini_al_grasso") != null && !context.getRequest().getParameter("num_suini_al_grasso").equals("")){
			    	  fields.setNum_suini_al_grasso(Integer.parseInt(context.getRequest().getParameter("num_suini_al_grasso")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("cap_max_suini_al_grasso") != null && !context.getRequest().getParameter("cap_max_suini_al_grasso").equals("")){
			    	  fields.setCap_max_suini_al_grasso(Integer.parseInt(context.getRequest().getParameter("cap_max_suini_al_grasso")));
			    	  gestione_campi = true;
			      }
			      if(context.getRequest().getParameter("num_suini_al_grasso_isp") != null && !context.getRequest().getParameter("num_suini_al_grasso_isp").equals("")){
			    	  fields.setNum_suini_al_grasso_isp(Integer.parseInt(context.getRequest().getParameter("num_suini_al_grasso_isp")));
			    	  gestione_campi = true;
			      }
			      
			      if(context.getRequest().getParameter("metodo_allevamento") != null && !context.getRequest().getParameter("metodo_allevamento").equals("")){
			    	  String metodo = context.getRequest().getParameter("metodo_allevamento");
			    	  if(metodo.equals("0")) {
			    		  fields.setMetodo_allevamento("All'aperto");
			    	  }
			    	  if(metodo.equals("1")) {
			    		  fields.setMetodo_allevamento("A terra");
			    	  }
			    	  if(metodo.equals("2")) {
			    		  fields.setMetodo_allevamento("Biologico");
			    	  }
			    	  if(metodo.equals("3")) {
			    		  fields.setMetodo_allevamento("In Gabbia");
			    	  }
			    	
			    	  gestione_campi = true;
			      }
			      
			   if(gestione_campi)
				   fields.saveFieldsZoot(db);
		     
		      
		      //Gestione Trasporto Animali
		      String[] listaispezioni_animali=context.getRequest().getParameterValues("animalitrasp");
		      //Per i trasporti animali, in particolare per le 15 specie esistenti
		      for(int i=1;i<=27;i++){
		    	  String num_specie = context.getRequest().getParameter("num_specie"+i);
		    	  if(num_specie != null && !num_specie.equals("")){  		  
		    		  if(i==1){
		    			  newTic.setNum_specie1(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 2){
		    			  newTic.setNum_specie2(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 3){
		    			  newTic.setNum_specie3(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 4){
		    			  newTic.setNum_specie4(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 5){
		    			  newTic.setNum_specie5(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 6){
		    			  newTic.setNum_specie6(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 7){
		    			  newTic.setNum_specie7(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 8){
		    			  newTic.setNum_specie8(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 9){
		    			  newTic.setNum_specie9(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 10){
		    			  newTic.setNum_specie10(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 11){
		    			  newTic.setNum_specie11(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 12){
		    			  newTic.setNum_specie12(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 13){
		    			  newTic.setNum_specie13(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 14){
		    			  newTic.setNum_specie14(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 15){
		    			  newTic.setNum_specie15(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 22){
		    			  newTic.setNum_specie22(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 23){
		    			  newTic.setNum_specie23(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 24){
		    			  newTic.setNum_specie24(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 25){
		    			  newTic.setNum_specie25(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		  else if(i == 26){
		    			  newTic.setNum_specie26(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
		    		  }
		    		 
		    	  }
		    	
		      }
      
    
		      String tipoControllo=context.getRequest().getParameter("tipoCampione");
              newTic.setTipoCampione(Integer.parseInt(tipoControllo));
            
              
              HashMap<Integer,OiaNodo> tipoIspezioni = new  HashMap<Integer,OiaNodo>();
	    	  
 	    	 
	    	  newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria		
	    	  
              String[] listabpi=context.getRequest().getParameterValues("bpi");
              String[] listaHaccp=context.getRequest().getParameterValues("haccp");
          	   String[] tipoAudit= context.getRequest().getParameterValues("tipoAudit");
          	   String[] tipoispezione =context.getRequest().getParameterValues("tipoIspezione");
          	 String[] oggettoAudit= context.getRequest().getParameterValues("oggetto_audit");
          	 
          	   //Aggiunta per i tipi di sospetto
          	 
          	   LookupList lookuptipoAudit = new LookupList(db,"lookup_tipo_audit");
          	   LookupList lookupOggettoAudit = new LookupList(db,"lookup_oggetto_audit");

          	   LookupList lookupbpi = new LookupList(db,"lookup_bpi");
          	   LookupList lookuphaccp = new LookupList(db,"lookup_haccp");
          	   LookupList lookuptipoispezione = new LookupList(db,"lookup_tipo_ispezione");

          	   newTic.setOggettoAudit(this.getHashmapfromArray(oggettoAudit, lookupOggettoAudit));

          	   newTic.setTipoAudit(this.getHashmapfromArray(tipoAudit, lookuptipoAudit));
          	   newTic.setLisaElementibpi(this.getHashmapfromArray(listabpi,lookupbpi));
          	   newTic.setLisaElementihaccp(this.getHashmapfromArray(listaHaccp,lookuphaccp));
          	   newTic.setTipoIspezione(this.getHashmapfromArray(tipoispezione,lookuptipoispezione));
          	   
          	   Iterator<Integer> itTipiIsp = newTic.getTipoIspezione().keySet().iterator();
          	 int idOianodo = -1 ;
          	   while (itTipiIsp.hasNext())
				{
          		 idOianodo = -1 ;
					int tipo_ispezione = itTipiIsp.next();
					
					if (context.getParameter("per_condo_di"+tipo_ispezione)!=null)
						idOianodo = Integer.parseInt(context.getParameter("per_condo_di"+tipo_ispezione)) ;
					OiaNodo n = new OiaNodo();
					n.setId(idOianodo);
					tipoIspezioni.put(tipo_ispezione, n);
					
				}
          	   newTic.setLista_uo_ispezione(tipoIspezioni);
          	   
          	   
          	 Iterator<Integer> itKey = newTic.getTipoIspezione().keySet().iterator();
 			while (itKey.hasNext())
 			{
 				int code = itKey.next();
 				RispostaDwrCodicePiano codiceInterno = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_tipo_ispezione", code);
 				newTic.getTipoIspezioneCodiceInterno().add(codiceInterno.getCodiceInterno());
 			}
          	   
          	
	          	  int indice = 1 ;
	          	  ArrayList<Piano> listapianiSel = new ArrayList<Piano>();
	          	  while (context.getRequest().getParameter("piano_monitoraggio"+indice)!=null) 
	          	  {
	          		  Piano p = new Piano();
	          		  p.setId(Integer.parseInt(context.getRequest().getParameter("piano_monitoraggio"+indice)));
	          		  p.setId_uo(Integer.parseInt(context.getRequest().getParameter("uo"+indice)));
	          		  listapianiSel.add(p);
	          		  indice ++ ;
	          		  
	          	  }
	          
	          	if ( newTic.getTipoCampione()!=4)
	       	 
	          	  {
	          		newTic.setUo(context.getRequest().getParameterValues("uo_controllo"));
	          	  }
	          	  
	           newTic.setPianoMonitoraggio(listapianiSel);

          	   
          	   newTic.setAuditTipo(Integer.parseInt(context.getRequest().getParameter("auditTipo")));
          	   newTic.setNoteAltrodiSistema(context.getRequest().getParameter("notealtro"));      
          	   String[] listaispezionisel = null ;
     	      
     	      //if(newTic.getTipoCampione()!=5)
     	    	  listaispezionisel =context.getRequest().getParameterValues("ispezione");
     	    	  
      newTic.setModifiedBy(getUserId(context));
      newTic.setSiteId(Integer.parseInt(context.getRequest().getParameter("siteId")));
      
      String[] azioniAdottateValidazione = context.getRequest().getParameterValues("azioniAdottate");
		LookupList AzioniAdottate = new LookupList(db, "lookup_azioni_adottate");
		if (AzioniAdottate!=null)
		{
		for(int k = 0 ; k < azioniAdottateValidazione.length; k++)
		{
			newTic.getAzioniAdottate().put(Integer.parseInt(azioniAdottateValidazione[k]), AzioniAdottate.getSelectedValue(azioniAdottateValidazione[k]));
		
		}
		}
      
      //newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getIdStabilimento()));
      isValid = this.validateObject(context, db, newTic) && isValid;
      context.getRequest().setAttribute("isValid", isValid);
      if (isValid) {
    	newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria  
    	
    	
    	
       
   	   newTic.setModificabile(context.getRequest().getParameter("modificabile"));
   	   resultCount= 	  newTic.update(db,listaispezionisel,context);
       
 
//    	if(newTic.getTipoIspezione().containsKey(7) && "si".equals(context.getRequest().getParameter("modificabile")) )
//    	{
   	if(newTic.getTipoIspezioneCodiceInterno().contains("7a")  )
	{	
   	 newTic.salvaAzioniAdottate(db, azioniAdottate);
    	}
    	 String[] linee_Attivita = context.getRequest().getParameterValues("id_linea_sottoposta_a_controllo");

    	  if(newTic.getTipoCampione()!=5)
    		  newTic.inserimentoLineeAttivita(db, newTic.getId(),linee_Attivita);
   	  	
   	 String[] linee_Attivita_stab_soa_desc = context.getRequest().getParameterValues("alertText");

	  String[] linee_Attivita_stab_soa = context.getRequest().getParameterValues("codici_selezionabili");
	  /*if(newTic.getTipoCampione()!=5)
	    newTic.inserimentoLineeAttivitaStabSoa(db, newTic.getId(),linee_Attivita_stab_soa,linee_Attivita_stab_soa_desc);
	  */
   	  ArrayList<LineeAttivita> linee_attivita = new ArrayList<LineeAttivita>();
      String idCu = ""+newTic.getId();
      ArrayList<LineeAttivita> lista_la = LineeAttivita.opu_load_linea_attivita_per_cu(idCu, db,newTic.getIdStabilimento());
      if (lista_la.size()>0)
      {
    	  for (LineeAttivita la : lista_la)
    	  {
    		  String idLineaAttivita = "" ;
    	  
    		  if (!la.getLinea_attivita().isEmpty())
    			  idLineaAttivita = la.getCodice_istat() + " : " + la.getCategoria() + " - " + la.getLinea_attivita();
    		  else
    			  idLineaAttivita = la.getCodice_istat() + " : " + la.getCategoria();
			// val[i] = linea.getCategoria();
      		//linee_attivita.add(idLineaAttivita);
    	  }
    	  context.getRequest().setAttribute("linea_attivita", lista_la);
      }
      
      /*COSTRUZIONE BUFFER*/
      if(newTic.getCodiceBuffer() != null && !newTic.getCodiceBuffer().equals("")) {
    	  Buffer b = new Buffer(db,Integer.parseInt(newTic.getCodiceBuffer()));
    	  context.getRequest().setAttribute("BufferDetails", b);
      }
      
      /*COSTRUZIONE LINEE ATTIVITA PER STABILIMENTI E SOA*/
//      ArrayList<LineaAttivitaSoa> linee_attivita_stabilimenti = new ArrayList<LineaAttivitaSoa>();
//      ArrayList<String> linee_attivita_stabilimenti_desc = new ArrayList<String>();
//      String sel = "select * from linee_attivita_controlli_ufficiali_stab_soa where id_controllo_ufficiale = "+idCu;
//      PreparedStatement pst = db.prepareStatement(sel);
//      ResultSet rs = pst.executeQuery();
//      while (rs.next())
//      {
//    	  LineaAttivitaSoa l = new LineaAttivitaSoa () ;
//    	  l.setCategoria(rs.getString(2));
//    	  l.setImpianto(rs.getString(3));
//    	  linee_attivita_stabilimenti.add(l);
//      }
//    	
//      if(linee_attivita_stabilimenti.size()>0)
//      {
//    	  context.getRequest().setAttribute("linee_attivita_stabilimenti", linee_attivita_stabilimenti);
//    	  context.getRequest().setAttribute("linee_attivita_stabilimenti_desc", linee_attivita_stabilimenti_desc);
//      }
    	/*String[] nc_formali=context.getRequest().getParameterValues("Provvedimenti1");
        String[] nc_significative=context.getRequest().getParameterValues("Provvedimenti2");
        String[] nc_gravi=context.getRequest().getParameterValues("Provvedimenti3");
        newTic.updateNonConformita(db, nc_formali, nc_significative, nc_gravi);
        */newTic.setDescrizioneCodiceAteco(context.getRequest().getParameter("alertText"));
      //Inserimento della specie trasportata
        if(listaispezioni_animali != null)
        	newTic.salvaSpecieTrasportata(db,listaispezioni_animali);
     
      
        
      }
      context.getRequest().setAttribute("resultCount", resultCount);

      if(newTic.getIdFileAllegato()!=-1)
      {
    	  newTic.setListaDistribuzioneAllegata(true);
    	  FileItem thisItem = new FileItem(
          db, newTic.getIdFileAllegato(), newTic.getIdStabilimento(), Constants.ACCOUNTS);
    	  context.getRequest().setAttribute("fileItem", thisItem);
      } 
      else
      {
    	  newTic.setListaDistribuzioneAllegata(false);
      }
      
      if( context.getRequest().getParameterValues("ragioneSociale")!=null)
	  {
		  newTic.inserimentoRiferimentoSoa(db, newTic.getId(), context.getRequest().getParameterValues("ragioneSociale"), context.getRequest().getParameterValues("indirizzo"), context.getRequest().getParameterValues("orgIdSoa"));
	  }
      
      LookupList categoriaRischioList2 = new LookupList(db, "lookup_org_catrischio");
      categoriaRischioList2.addItem(-1, "-- SELEZIONA VOCE --");
      categoriaRischioList2.removeElementByLevel(1);
       context.getRequest().setAttribute("OrgCategoriaRischioList2", categoriaRischioList2);
      newTic = new Ticket(db,newTic.getId()); 
      
      if(newTic.getTipoCampione()==4 && newTic.getTipoIspezioneCodiceInterno().contains("7a"))
      {
      	/**
      	 * 	PROCEDURA DI RICHIAMO NON ATTIVA MA NECESSARIA
      	 */
      	if (newTic.getProceduraRichiamo() == 2)
      	{
      		
      		//String filePath = this.getPath(context, "accounts");
      		  FileItemList files = new FileItemList();
      	      files.setBuildPortalRecords(
      	      isPortalUser(context) ? Constants.TRUE : Constants.UNDEFINED);
      	      files.setLinkModuleId(Constants.ACCOUNTS);
      	      files.setLinkItemId(newTic.getIdStabilimento());
      	      files.buildList(db);
      	      Iterator it = files.iterator();
      	      String[] pathFiles 				= new String[ files.size() ];
      	      String[] pathFilesRipristinare 	= new String[ files.size() ];
      	      indice = 0 ;
      	      while(it.hasNext())
      	      {
      	    	        
      	    	  FileItem thisItem = (FileItem) it.next();
      	    	 
      	    	  FileItem itemToDownload = thisItem;
      	    	  itemToDownload.setEnteredBy(this.getUserId(context));
      	    	  String filePath = this.getPath(context, "accounts") + getDatePath(
      	    	  itemToDownload.getModified()) + itemToDownload.getFilename();
      	    	  FileDownload fileDownload = new FileDownload();
      	    	  fileDownload.setFullPath(filePath);
      	    	  File temp = new File(filePath);
      	    	  temp.renameTo(new File (this.getPath(context, "accounts") + getDatePath(itemToDownload.getModified())+itemToDownload.getClientFilename()));
      	    	  fileDownload.setDisplayName(itemToDownload.getClientFilename());
      	    	  String filePClient = this.getPath(context, "accounts") + getDatePath(itemToDownload.getModified())+itemToDownload.getClientFilename();
      	    	  pathFiles [ indice ] = filePClient ;
      	    	  pathFilesRipristinare [ indice ] = fileDownload.getFullPath() ;
      	    	  indice ++ ;
  	    		
      	    	
      	      }
      	      UtilityTemplateFile.write2(context,newTic,db);
      	      GestionePEC.preparaSpedisciPECcu(""+newTic.getSiteId(), newTic,pathFiles,pathFilesRipristinare,context);
      	 	 	
      	    	
  	      
      	}
      }
      else
      {
    	  if (oldProceduraRichiamo == 2)
    	  {
      	      GestionePEC.preparaSpedisciPECcu(""+newTic.getSiteId(), newTic,null,null,context);

    	  }
      }
      
    
      
      
      if (ticList.size()== 0 && tamponiList.size()==0)
      {
    	  newTic.setControllo_chiudibile(true);
      }
      else
      {
    	  boolean campioni_chiusi = true ;
    	  boolean tamponi_chiusi = true ;
    	  Iterator itCampioni = ticList.iterator();
    	  while (itCampioni.hasNext())
    	  {
    		  org.aspcfs.modules.campioni.base.Ticket campione = (org.aspcfs.modules.campioni.base.Ticket)itCampioni.next() ;
    		  if (campione.getClosed()== null)
    		  {
    			  campioni_chiusi = false ;
    			  break ;
    		  }
    		  
          }
    	  Iterator itTamponi = tamponiList.iterator();
    	  while (itTamponi.hasNext())
    	  {
    		  org.aspcfs.modules.tamponi.base.Ticket tampone = (org.aspcfs.modules.tamponi.base.Ticket)itTamponi.next() ;
    		  if (tampone.getClosed()== null)
    		  {
    			  tamponi_chiusi = false ;
    			  break ;
    		  }
    		  
          }
    	  if (campioni_chiusi == true && tamponi_chiusi == true)
    	  {
    		  newTic.setControllo_chiudibile(true);
    	  }
    	  else
    	  {
    		  newTic.setControllo_chiudibile(false);
    	  }
    	  
      }
      
      
     newTic.setElencoLabInRegioneControllati(labList);
      newTic.setElencoLabNonInRegioneControllati(labListNoReg);
      //newTic.setStrutturaAsl(db);

      context.getRequest().setAttribute("TicketDetails", newTic);
      } catch (Exception e) {
      context.getRequest().setAttribute("Error", e.getMessage());
      e.printStackTrace();
      
    } 

    return "" ;
  }

 


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   * @deprecated Replaced combobox with a pop-up
   */
 
  

  
  
  //aggiunto da d.dauria
  public String executeCommandDeleteListaDistribuzione(ActionContext context,Connection db) {
	    if (!hasPermission(context, "opu-vigilanza-add")) {
	      return ("PermissionError");
	    }
	    Ticket newTic = null;
	    try {
	    	
	      String filePath = this.getPath(context, "accounts");
	    //Process the form data
	      HashMap parts = (HashMap) context.getRequest().getAttribute("parts");
	      String idAll = (String)parts.get("ticketidd");
		     context.getRequest().setAttribute("ticketidd",idAll);
	      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
	      Provvedimenti.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
	      Provvedimenti.setMultiple(true);
	      Provvedimenti.setSelectSize(9);
	      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
	      newTic = (Ticket)context.getFormBean();
	      newTic.setTipoCampione(Integer.parseInt((String)parts.get("tipoCampione")));
	     // newTic.setTipoIspezione(Integer.parseInt((String)parts.get("tipoIspezione")));
	      newTic.setDestinazioneDistribuzione(Integer.parseInt((String)parts.get("destinazioneDistribuzione")));
	      newTic.setCodiceAllerta((String)parts.get("idAllerta"));
	      newTic.setContributi_seguito_campioni_tamponi(Double.parseDouble((String)parts.get("contributi")));
	      newTic.setProblem((String)parts.get("problem"));
	      newTic.setAssignedDate((String)parts.get("assignedDate"));
	      newTic.setUnitaMisura((String)parts.get("unitaMisura"));
	      String nucleo1 = (String)parts.get("nucleoIspettivo");
	      String nucleo2 = (String)parts.get("nucleoIspettivoDue");
	      String nucleo3 = (String)parts.get("nucleoIspettivoTre");
	      String nucleo4 = (String)parts.get("nucleoIspettivoQuattro");
	      String nucleo5 = (String)parts.get("nucleoIspettivoCinque");
	      String nucleo6 = (String)parts.get("nucleoIspettivoSei");
	      String nucleo7 = (String)parts.get("nucleoIspettivoSette");
	      String nucleo8 = (String)parts.get("nucleoIspettivoOtto");
	      String nucleo9 = (String)parts.get("nucleoIspettivoNove");
	      String nucleo10 = (String)parts.get("nucleoIspettivoDieci");
	      
	      
	      String componentenucleo1T = (String)parts.get("componenteNucleot");
	      String componentenucleo2T = (String)parts.get("componenteNucleoDuet");
	      String componentenucleo3T = (String)parts.get("componenteNucleoTret");
	      String componentenucleo4T = (String)parts.get("componenteNucleoQuattrot");
	      String componentenucleo5T = (String)parts.get("componenteNucleoCinquet");
	      String componentenucleo6T = (String)parts.get("componenteNucleoSeit");
	      String componentenucleo7T = (String)parts.get("componenteNucleoSettet");
	      String componentenucleo8T = (String)parts.get("componenteNucleoOttot");
	      String componentenucleo9T = (String)parts.get("componenteNucleoNovet");
	      String componentenucleo10T = (String)parts.get("componenteNucleoDiecit");
	      
	      String componentenucleo1S = (String)parts.get("componenteNucleo");
	      String componentenucleo2S = (String)parts.get("componenteNucleoDueS");
	      String componentenucleo3S = (String)parts.get("componenteNucleoTreS");
	      String componentenucleo4S = (String)parts.get("componenteNucleoQuattroS");
	      String componentenucleo5S = (String)parts.get("componenteNucleoCinqueS");
	      String componentenucleo6S = (String)parts.get("componenteNucleoSeiS");
	      String componentenucleo7S = (String)parts.get("componenteNucleoSetteS");
	      String componentenucleo8S = (String)parts.get("componenteNucleoOttoS");
	      String componentenucleo9S = (String)parts.get("componenteNucleoNoveS");
	      String componentenucleo10S = (String)parts.get("componenteNucleoDieciS");
	      
	      String componentenucleo1Tpal = (String)parts.get("componenteNucleo");
	      String componentenucleo2Tpal = (String)parts.get("componenteNucleoDueTpal");
	      String componentenucleo3Tpal = (String)parts.get("componenteNucleoTreTpal");
	      String componentenucleo4Tpal = (String)parts.get("componenteNucleoQuattroTpal");
	      String componentenucleo5Tpal = (String)parts.get("componenteNucleoCinqueTpal");
	      String componentenucleo6Tpal = (String)parts.get("componenteNucleoSeiTpal");
	      String componentenucleo7Tpal = (String)parts.get("componenteNucleoSetteTpal");
	      String componentenucleo8Tpal = (String)parts.get("componenteNucleoOttoTpal");
	      String componentenucleo9Tpal = (String)parts.get("componenteNucleoNoveTpal");
	      String componentenucleo10Tpal = (String)parts.get("componenteNucleoDieciTpal");
	      
	      newTic.setNucleoIspettivo(Integer.parseInt(nucleo1));
	      newTic.setNucleoIspettivoDue(Integer.parseInt(nucleo2));
	      newTic.setNucleoIspettivoTre(Integer.parseInt(nucleo3));
	      newTic.setNucleoIspettivoQuattro(Integer.parseInt(nucleo4));
	      newTic.setNucleoIspettivoCinque(Integer.parseInt(nucleo5));
	      newTic.setNucleoIspettivoSei(Integer.parseInt(nucleo6));
	      newTic.setNucleoIspettivoSette(Integer.parseInt(nucleo7));
	      newTic.setNucleoIspettivoOtto(Integer.parseInt(nucleo8));
	      newTic.setNucleoIspettivoNove(Integer.parseInt(nucleo9));
	      newTic.setNucleoIspettivoDieci(Integer.parseInt(nucleo10));
	      
	      if(!nucleo1.equals("-1"))
	      {
	      if(( nucleo1.equals("1") || nucleo1.equals("2")) )
	      {
	    	  newTic.setComponenteNucleo(componentenucleo1S);
	      }
	      else
	      {
	    	  if(nucleo1.equals("23"))
	    	  {
	    		  newTic.setComponenteNucleo(componentenucleo1Tpal);
	    	  }
	    	  else
	    	  {
	    		  newTic.setComponenteNucleo(componentenucleo1T);
	    	  }
	      }  
	      }
	      else
	      {
	    	  newTic.setComponenteNucleo("");
	      }
	      
	      if(!nucleo2.equals("-1"))
	      {
	      if(( nucleo2.equals("1") || nucleo2.equals("2")) )
	      {
	    	  newTic.setComponenteNucleoDue(componentenucleo2S);
	      }
	      else
	      {
	    	  if(nucleo2.equals("23"))
	    	  {
	    		  newTic.setComponenteNucleoDue(componentenucleo2Tpal);
	    	  }
	    	  else
	    	  {
	    		  newTic.setComponenteNucleoDue(componentenucleo2T);
	    	  }
	      }  
	      }
	      else
	      {
	    	  newTic.setComponenteNucleoDue("");
	      }
	      
	      if(!nucleo3.equals("-1"))
	      {
	      if(( nucleo3.equals("1") || nucleo3.equals("2")) )
	      {
	    	  newTic.setComponenteNucleoTre(componentenucleo3S);
	      }
	      else
	      {
	    	  if(nucleo3.equals("23"))
	    	  {
	    		  newTic.setComponenteNucleoTre(componentenucleo3Tpal);
	    	  }
	    	  else
	    	  {
	    		  newTic.setComponenteNucleoTre(componentenucleo3T);
	      
	    	  }
	      }
	      }
	      else
	      {
	    	  newTic.setComponenteNucleoTre("");
	    	  
	      }
	      
	      if(!nucleo4.equals("-1"))
	      {
	      if(( nucleo4.equals("1") || nucleo4.equals("2")) )
	      {
	    	  newTic.setComponenteNucleoQuattro(componentenucleo4S);
	      }
	      else
	      {
	    	  if(nucleo4.equals("23"))
	    	  {
	    		  newTic.setComponenteNucleoQuattro(componentenucleo4Tpal);
	    	  }
	    	  else
	    	  {
	    		  newTic.setComponenteNucleoQuattro(componentenucleo4T);
	    	  }	  
	    	}
	      }
	      else
	      {
	    	  newTic.setComponenteNucleoQuattro("");
	      }
	      if(!nucleo5.equals("-1"))
	      {
	      if(( nucleo5.equals("1") || nucleo5.equals("2")) )
	      {
	    	  newTic.setComponenteNucleoCinque(componentenucleo5S);
	      }
	      else
	      {
	    	  if(nucleo5.equals("23"))
	    	  {
	    		  newTic.setComponenteNucleoCinque(componentenucleo5Tpal);
	    	  }
	    	  else
	    	  {
	    		  newTic.setComponenteNucleoCinque(componentenucleo5T);
	    	  }  
	      }
	      }
	      else
	      {
	    	  newTic.setComponenteNucleoCinque("");
	      }
	      
	      if(!nucleo6.equals("-1"))
	      {
	      if(( nucleo6.equals("1") || nucleo6.equals("2")) )
	      {
	    	  newTic.setComponenteNucleoSei(componentenucleo6S);
	      }
	      else
	      {
	    	  if(nucleo6.equals("23"))
	    	  {
	    		  newTic.setComponenteNucleoSei(componentenucleo6Tpal);
	    	  }
	    	  else
	    	  {
	    		  newTic.setComponenteNucleoSei(componentenucleo6T);
	    	  }  
	    	 }
	      }
	      else
	      {
	    	  newTic.setComponenteNucleoSei("");
	      }
	      
	      if(!nucleo7.equals("-1"))
	      {
	      if(( nucleo7.equals("1") || nucleo7.equals("2")) )
	      {
	    	  newTic.setComponenteNucleoSette(componentenucleo7S);
	      }
	      else
	      {
	    	  if(nucleo7.equals("23"))
	    	  {
	    		  newTic.setComponenteNucleoSette(componentenucleo7Tpal);
	    	  }
	    	  else
	    	  {
	    		  newTic.setComponenteNucleoSette(componentenucleo7T);
	    	  }  
	      }
	      }
	      else
	      {
	    	  newTic.setComponenteNucleoSette("");
	      }
	      
	      if(!nucleo8.equals("-1"))
	      {
	      if(( nucleo8.equals("1") || nucleo8.equals("2")) )
	      {
	    	  newTic.setComponenteNucleoOtto(componentenucleo8S);
	      }
	      else
	      {
	    	  if(nucleo8.equals("23"))
	    	  {
	    		  newTic.setComponenteNucleoOtto(componentenucleo8Tpal);
	    	  }
	    	  else
	    	  {
	    		  newTic.setComponenteNucleoOtto(componentenucleo8T);
	    	  }  
	      }
	      }
	      else
	      {
	    	  newTic.setComponenteNucleoOtto(componentenucleo8T);
	      }
	      
	      if(!nucleo9.equals("-1"))
	      {
	      if(( nucleo9.equals("1") || nucleo9.equals("2")) )
	      {
	    	  newTic.setComponenteNucleoNove(componentenucleo9S);
	      }
	      else
	      {
	    	  if(nucleo9.equals("23"))
	    	  {
	    		  newTic.setComponenteNucleoNove(componentenucleo9Tpal);
	    	  }
	    	  else
	    	  {
	    		  newTic.setComponenteNucleoNove(componentenucleo9T);
	    	  }
	      }
	      }
	      else
	      {
	    	  newTic.setComponenteNucleoNove("");
	      }
	      
	      if(!nucleo10.equals("-1"))
	      {
	      if(( nucleo10.equals("1") || nucleo10.equals("2")) )
	      {
	    	  newTic.setComponenteNucleoDieci(componentenucleo10S);
	      }
	      else
	      {
	    	  if(nucleo10.equals("23"))
	    	  {
	    		  newTic.setComponenteNucleoDieci(componentenucleo10Tpal);
	    	  }
	    	  else
	    	  {
	    		  newTic.setComponenteNucleoDieci(componentenucleo10T);
	    	  }
	      }  
	      }
	      else
	      {
	    	  newTic.setComponenteNucleoDieci("");
	      }
	      
	      
	      //aggiunte queste istruzioni
	      String gotoInsert = (String)parts.get("gotoPage");
	      newTic.setListaDistribuzioneAllegata(false);
	      String id = "";
	      if(!gotoInsert.equals("insert"))
	      { 
	    	   id = (String) parts.get("orgId");
	    	   newTic.setId(Integer.parseInt((String) parts.get("id")));
	    	  
	      }
	      else
	      {
	    	   id = (String) parts.get("id");
	      } 
	      String temporgId = (String)parts.get("orgId");
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      UserBean user=(UserBean)context.getSession().getAttribute("User");
	      int siteId 		= (Integer)context.getRequest().getAttribute("siteId");
	      int tipologia	 	= (Integer)context.getRequest().getAttribute("tipologia");
	      String tipoDest 	= (String)context.getRequest().getAttribute("tipoDest");
	      
	      ControlliUfficialiUtil.buildLookupTipoControlloUfficiale(db, context, systemStatus, user.getRoleId(), siteId, tipologia, tipoDest);
	
	      //Load the organization
	     
	      /**
	       * 		DELETE DEL FILE DA ALLEGARE
	       */
	      boolean recordDeleted = false;
	      String itemId = (String) context.getRequest().getParameter("fid");
	      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId),Integer.parseInt(temporgId), Constants.ACCOUNTS);
	      recordDeleted = thisItem.delete(db, this.getPath(context, "accounts"));
	         
		      
	      newTic.setListaDistribuzioneAllegata(false);
	      newTic.setSubjectFileAllegato("");
	    
	      //getting current date in mm/dd/yyyy format
	      String currentDate = getCurrentDateAsString(context);
	      context.getRequest().setAttribute("currentDate", currentDate);
	      context.getRequest().setAttribute(
	          "systemStatus", this.getSystemStatus(context));

	      return "";
	    
	    } catch (Exception e) {
	    	e.printStackTrace();
	      context.getRequest().setAttribute("Error", e);
	      
	      return ("SystemError");
	    } 
	  }

  
  
  public String executeCommandUploadLista(ActionContext context) {
  
	  context.getRequest().setAttribute("orgId", Integer.parseInt(context.getRequest().getParameter("orgId")));
	  context.getRequest().setAttribute("folderId", ""+Integer.parseInt(context.getRequest().getParameter("folderId")));
	  String tipo = "";
	  if(context.getRequest().getParameter("tipo") != null) {
		  if( "verbale".equalsIgnoreCase(context.getRequest().getParameter("tipo")))
			  context.getRequest().setAttribute("TipoAllegato", "1");
		  else
			  context.getRequest().setAttribute("TipoAllegato", "2");
	  } else {
		  context.getRequest().setAttribute("TipoAllegato", "2");
	  }

	  return "UploadListaOK";
  }
  
  public String executeCommandUpload(ActionContext context) {
	   
	    Organization thisOrg = null;
	    boolean recordInserted = false;
	    boolean isValid = false;
	    Connection db = null;
	    try {
	    	
	    		
	      db = this.getConnection(context);
	      String filePath = this.getPath(context, "accounts");
	      //Process the form data
	      HttpMultiPartParser multiPart = new HttpMultiPartParser();
	      multiPart.setUsePathParam(false);
	      multiPart.setUseUniqueName(true);
	      multiPart.setUseDateForFolder(true);
	      multiPart.setExtensionId(getUserId(context));
	     
	      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
	      
	      context.getRequest().setAttribute("TipoAllegato", (String) parts.get("TipoAllegato"));
		    
	      String id = (String) parts.get("id");
	      String subject = (String) parts.get("subject");
	      String folderId = (String) parts.get("folderId");
	      String actionStepWork = (String) parts.get("actionStepWork");
	      String allowPortalAccess = (String) parts.get("allowPortalAccess");
	      if (folderId != null) {
	        context.getRequest().setAttribute("folderId", folderId);
	      }
	      if (subject != null) {
	        context.getRequest().setAttribute("subject", subject);
	      }
	      if (actionStepWork != null) {
	        context.getRequest().setAttribute("actionStepWork", actionStepWork);
	      }
	      
	    
	      FileInfo ftest = new FileInfo();
	      
	      FileInfo ftest2 =  (FileInfo)  parts.get("id" + (String) parts.get("id"));
	      if (ftest2 instanceof FileInfo) {
	        //Update the database with the resulting file
	        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);
	        //Insert a file description record into the database
	        FileItem thisItem = new FileItem();
	        thisItem.setLinkModuleId(Constants.ACCOUNTS);
	        thisItem.setLinkItemId(Integer.parseInt(id));
	        thisItem.setEnteredBy(getUserId(context));
	        thisItem.setModifiedBy(getUserId(context));
	        thisItem.setFolderId(Integer.parseInt(folderId));
	        thisItem.setSubject(subject);
	        thisItem.setClientFilename(newFileInfo.getClientFileName());
	        thisItem.setFilename(newFileInfo.getRealFilename());
	        thisItem.setVersion(1.0);
	        thisItem.setSize(newFileInfo.getSize());
	        thisItem.setAllowPortalAccess(allowPortalAccess);
	        isValid = this.validateObject(context, db, thisItem);
	        if (isValid) {
	          recordInserted = thisItem.insert(db);
	        }
	        if (recordInserted) {
	          this.processInsertHook(context, thisItem);
	          context.getRequest().setAttribute("fileItem", thisItem);
	          context.getRequest().setAttribute("subject", "");
	       
  	          context.getRequest().setAttribute("idFile", thisItem.getId());
  	          context.getRequest().setAttribute("fileCaricato", "yes");
	        }
	      } else {
	        recordInserted = false;
	        HashMap errors = new HashMap();
	        SystemStatus systemStatus = this.getSystemStatus(context);
	        errors.put(
	            "actionError", systemStatus.getLabel(
	                "object.validation.incorrectFileName"));
	        if (subject != null && "".equals(subject.trim())) {
	          errors.put(
	              "subjectError", systemStatus.getLabel(
	                  "object.validation.required"));
	        }
	        processErrors(context, errors);
	      }
	      
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally{
	    	freeConnection(context, db);
	    }
	      return ("UploadListaOK");

	    
	  }
  
  public String executeCommandUploadListaDistribuzione(ActionContext context,Connection db) {
	   
	    Organization thisOrg = null;
	    boolean recordInserted = false;
	    boolean isValid = false;
	    try {
	      String filePath = this.getPath(context, "accounts");
	      //Process the form data
	      HttpMultiPartParser multiPart = new HttpMultiPartParser();
	      multiPart.setUsePathParam(false);
	      multiPart.setUseUniqueName(true);
	      multiPart.setUseDateForFolder(true);
	      multiPart.setExtensionId(getUserId(context));
	      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
	      String id = (String) parts.get("id");
	      String subject = (String) parts.get("subject");
	      String folderId = (String) parts.get("folderId");
	      String actionStepWork = (String) parts.get("actionStepWork");
	      String allowPortalAccess = (String) parts.get("allowPortalAccess");
	      if (folderId != null) {
	        context.getRequest().setAttribute("folderId", folderId);
	      }
	      if (subject != null) {
	        context.getRequest().setAttribute("subject", subject);
	      }
	      if (actionStepWork != null) {
	        context.getRequest().setAttribute("actionStepWork", actionStepWork);
	      }
	      
	    
	     
	      if (((Object) parts.get("id" + (String) parts.get("id"))) instanceof FileInfo) {
	        //Update the database with the resulting file
	        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);
	        //Insert a file description record into the database
	        FileItem thisItem = new FileItem();
	        thisItem.setLinkModuleId(Constants.ACCOUNTS);
	        thisItem.setLinkItemId(Integer.parseInt(id));
	        thisItem.setEnteredBy(getUserId(context));
	        thisItem.setModifiedBy(getUserId(context));
	        thisItem.setFolderId(Integer.parseInt(folderId));
	        thisItem.setSubject(subject);
	        thisItem.setClientFilename(newFileInfo.getClientFileName());
	        thisItem.setFilename(newFileInfo.getRealFilename());
	        thisItem.setVersion(1.0);
	        thisItem.setSize(newFileInfo.getSize());
	        thisItem.setAllowPortalAccess(allowPortalAccess);
	        isValid = this.validateObject(context, db, thisItem);
	        if (isValid) {
	          recordInserted = thisItem.insert(db);
	        }
	        if (recordInserted) {
	          this.processInsertHook(context, thisItem);
	          context.getRequest().setAttribute("fileItem", thisItem);
	          context.getRequest().setAttribute("subject", "");
	       
	          context.getRequest().setAttribute("idFile", thisItem.getId());
	          context.getRequest().setAttribute("fileCaricato", "yes");
	        }
	      } else {
	        recordInserted = false;
	        HashMap errors = new HashMap();
	        SystemStatus systemStatus = this.getSystemStatus(context);
	        errors.put(
	            "actionError", systemStatus.getLabel(
	                "object.validation.incorrectFileName"));
	        if (subject != null && "".equals(subject.trim())) {
	          errors.put(
	              "subjectError", systemStatus.getLabel(
	                  "object.validation.required"));
	        }
	        processErrors(context, errors);
	      }
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } 

	      return ("UploadListaOK");
	    
	  }
  
  

	private LookupList ritorna_lookup_tutti_comuni(ActionContext context,int idAsl, Connection db) {
		LookupList ret = new LookupList();
		int i=-1;

		PreparedStatement	stat	= null;
		ResultSet			res		= null;
		String sql = "SELECT codiceistatcomune, comune FROM comuni ";
		if (idAsl>0)
		{
			sql += " where 1=1 and codiceistatasl='"+idAsl+"'"; 
		}

		try {
			stat = db.prepareStatement( sql );
			res		= stat.executeQuery();

			while( res.next() )
			{
				LookupElement thisElement = new LookupElement();
				thisElement.setCode(Integer.parseInt(res.getString(1)) );
				thisElement.setDescription(res.getString(2));
				thisElement.setEnabled(true);
				ret.add(thisElement);
			}
			return ( ret );

		} catch (Exception e) {	
			e.printStackTrace();
			return (null);
		} 
	}
  public String executeCommandAdd(ActionContext context,Connection db) {
	   
	    try {
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      UserBean user=(UserBean)context.getSession().getAttribute("User");
	      int siteId 		= ((UserBean)(context.getRequest().getSession().getAttribute("User"))).getSiteId();
	      int tipologia	 	= (Integer)context.getRequest().getAttribute("tipologia");
	      
	      
	      
	      int idAsl = -1 ; 
	      if(context.getRequest().getAttribute("siteId")!=null)
	      {
	    	  idAsl = (Integer)context.getRequest().getAttribute("siteId");
	      }
	      
	      	LookupList lookup_comuni = ritorna_lookup_tutti_comuni(context,-1, db);
			lookup_comuni.setSelectName("comune");
			context.getRequest().setAttribute("lookup_comuni", lookup_comuni);
	      
			int idAslnodo = (user.getSiteId()>0)? user.getSiteId() : idAsl ;
			HashMap<Integer,ArrayList<OiaNodo>> strutture = (HashMap<Integer,ArrayList<OiaNodo>>) context.getServletContext().getAttribute("StruttureOIA");
		     ArrayList<OiaNodo> nodi = new ArrayList<OiaNodo>();
		     if(user.getSiteId()>0)
		     {
		    	nodi = strutture.get(user.getSiteId());
		     }
		     else
		     {
		    	 Iterator<Integer> itK = strutture.keySet().iterator();
		    	 while (itK.hasNext())
		    	 {
		    		 int k = itK.next();
		    		 ArrayList<OiaNodo> nodiTemp = strutture.get(k);
		    		 for(OiaNodo nodotemp : nodiTemp)
		    		 {
		    			 nodi.add(nodi.size(), nodotemp);
		    		 }
		    		 
		    	 }
		     }
	      context.getRequest().setAttribute("StrutturaAsl", nodi);
	      
	      
	      Stabilimento org  = new Stabilimento(db, Integer.parseInt(context.getParameter("orgId")));
			for(OiaNodo nn : nodi)
			{
				if(nn.getId_padre()==8 && nn.getIdAsl()==org.getSiteId())
				{
					context.getRequest().setAttribute("StrutturaAslSelezionata", nn);
					break;

				}
			}
	      
	      ArrayList<OiaNodo> lista_uo = UserInfo.load_unita_operative(""+siteId,db,this.getSystemStatus(context));
	      
	     
	      String currentDate = getCurrentDateAsString(context);
	      context.getRequest().setAttribute("currentDate", currentDate);
	      context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
	  
	      LookupList IspezioneMacrocategorie = new LookupList(db, "lookup_ispezione_macrocategorie");
		  IspezioneMacrocategorie.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
		  context.getRequest().setAttribute("IspezioneMacrocategorie", IspezioneMacrocategorie);
		    
	      ControlliUfficialiUtil.buildLookupTipoControlloUfficialeOpu(db, context, systemStatus, user.getRoleId(), siteId);
	      
	     
	      
	      if (tipologia == 1 || tipologia == 9999)
	      {
	    	  context.getRequest().setAttribute("ViewLdA", "si");
	      }
	      
	    } catch (Exception e) {
	    	e.printStackTrace();
	      context.getRequest().setAttribute("Error", e);
	     
	    }
	    
	    context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
	    return "" ;
	  }

  
  public String executeCommandInsert(ActionContext context,Connection db) throws SQLException, ParseException {
	   
	    context.getRequest().setAttribute("reload", "false");
	   
	   
	    boolean recordInserted = false;
	    boolean isValid = true;
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Ticket newTicket = null;
	    Ticket newTic = null;

	    try
	    {
	    newTic = (Ticket) context.getFormBean();
	    if (context.getRequest().getAttribute("orgId")!=null)
	    	newTic.setOrgId((Integer)context.getRequest().getAttribute("orgId"));
	    else
		    if (context.getRequest().getAttribute("idStabilimentoopu")!=null)
		    	newTic.setIdStabilimento((Integer)context.getRequest().getAttribute("idStabilimentoopu"));

	    if(context.getRequest().getParameter("nome_conducente")!=null)
	    	newTic.setNome_conducente(context.getRequest().getParameter("nome_conducente"));
	    if(context.getRequest().getParameter("cognome_conducente")!=null)
	    	newTic.setCognome_conducente(context.getRequest().getParameter("cognome_conducente"));
	    if(context.getRequest().getParameter("documento_conducente")!=null)
	    	newTic.setDocumento_conducente(context.getRequest().getParameter("documento_conducente"));
	    if(context.getRequest().getParameter("tipologia_cu")!=null)
	    	newTic.setTipologia_controllo_cani(Integer.parseInt(context.getRequest().getParameter("tipologia_cu")));
	 
	    if(context.getRequest().getParameter("quantitativo")!=null)
	    	newTic.setQuantitativo(Integer.parseInt(context.getRequest().getParameter("quantitativo")));
	 
	    if(context.getRequest().getParameter("quintali")!=null)
	    	newTic.setQuintali(Double.parseDouble(context.getRequest().getParameter("quintali")));
	 
	  
	    if (context.getRequest().getParameter("cu_int_asl")!=null)
	    {
	    	if (context.getRequest().getParameter("cu_int_asl").equals("si"))
	    	{
	    		newTic.setIntera_asl(true);
	    	}
	    	else
	    	{
	    		newTic.setIntera_asl(false);
	    	}
	    	
	    }
	    
	    
	    
	    
	    
	    
	    newTic.setData_preavviso(context.getParameter("data_preavviso"));
	    newTic.setData_comunicazione_svincolo(context.getParameter("data_comunicazione_svincolo"));
	    if(context.getParameter("peso")!= null && ! "".equals(context.getParameter("peso")))
	    	newTic.setPeso(Double.parseDouble(context.getParameter("peso")));
	    newTic.setProtocollo_preavviso(context.getParameter("protocollo_preavviso"));
	    newTic.setProtocollo_svincolo(context.getParameter("protocollo_svincolo"));
	    newTic.setTipologia_sottoprodotto(context.getParameter("tipologia_sottoprodotto"));
	    
	    if (context.getRequest().getParameter("cod_azienda")!=null)
	    {
	    	newTic.setCodice_azienda(context.getRequest().getParameter("cod_azienda"));
	    }
	    if (context.getRequest().getParameter("id_allevamento")!=null && ! "".equals(context.getRequest().getParameter("id_allevamento")) )
	    {
	    	newTic.setId_allevamento(Integer.parseInt((context.getRequest().getParameter("id_allevamento"))));
	    }
	    if (context.getRequest().getParameter("ragione_sociale_allevamento")!=null )
	    {
	    	newTic.setRagione_sociale_allevamento(context.getRequest().getParameter("ragione_sociale_allevamento"));
	    }
	    
	    if (context.getRequest().getParameter("data_nascita_conducente")!=null )
	    {
	    	newTic.setData_nascita_conducente(context.getRequest().getParameter("data_nascita_conducente"));
	    }
	    if (context.getRequest().getParameter("luogo_nascita_conducente")!=null )
	    {
	    	newTic.setLuogo_nascita_conducente(context.getRequest().getParameter("luogo_nascita_conducente"));
	    }
	    if (context.getRequest().getParameter("citta_conducente")!=null )
	    {
	    	newTic.setCitta_conducente(context.getRequest().getParameter("citta_conducente"));
	    }
	    if (context.getRequest().getParameter("indirizzo_conducente")!=null )
	    {
	    	newTic.setVia_connducente(context.getRequest().getParameter("indirizzo_conducente"));
	    }
	    if (context.getRequest().getParameter("cap_conducente")!=null && ! "".equals(context.getRequest().getParameter("cap_conducente")))
	    {
	    	newTic.setCap_conducente(context.getRequest().getParameter("cap_conducente"));
	    }
	    if (context.getRequest().getParameter("provincia_conducente")!=null )
	    {
	    	newTic.setProv_conducente(context.getRequest().getParameter("provincia_conducente"));
	    }


	    
	    
	    
	    UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newTic.setIpEntered(ip);
	    newTic.setIpModified(ip);
	    
	    
	    
//	    String esitDeclassamento = context.getRequest().getParameter("esito");
//	    String declassamento = context.getRequest().getParameter("declassamento");
//	   
//	    if (declassamento!= null)
//	    {
//	    	 newTic.setEsitoDeclassamento(esitDeclassamento);
//	    	newTic.setDeclassamento(Integer.parseInt(declassamento));
//	    }
	    String dataFine = context.getRequest().getParameter( "dataFineControllo" );
	    String scadenzaFollowUp=context.getRequest().getParameter( "followupDate" );
	   
	    if(context.getRequest().getParameter("idNodo")!=null)
	    {
	    	newTic.setIdMacchinetta(Integer.parseInt(context.getRequest().getParameter("idNodo")));
	    }
	    
	    if(context.getRequest().getParameter("idConcessionario")!=null)
	    {
	    	 if(!"null".equals(context.getRequest().getParameter("idConcessionario")))
	    			 newTic.setIdConcessionario(Integer.parseInt(context.getRequest().getParameter("idConcessionario")));
	    }
	    newTic.setDescrizioneCodiceAteco(context.getRequest().getParameter("alertText"));
	    if (context.getRequest().getParameter("orgId") != null && ! "0".equals(context.getRequest().getParameter(("orgId"))))
	    	newTic.setOrgId(Integer.parseInt(context.getRequest().getParameter(("orgId"))));
	    else
	    {
	    	newTic.setIdStabilimento(Integer.parseInt(context.getRequest().getParameter(("idStabilimentoopu"))));
	    }
	    
	    newTic.setIspezioni_desc1(context.getRequest().getParameter("ispezioni_desc1"));
	    newTic.setIspezioni_desc2(context.getRequest().getParameter("ispezioni_desc2"));
	    newTic.setIspezioni_desc3(context.getRequest().getParameter("ispezioni_desc3"));
	    newTic.setIspezioni_desc4(context.getRequest().getParameter("ispezioni_desc4"));
	    newTic.setIspezioni_desc5(context.getRequest().getParameter("ispezioni_desc5"));
	    newTic.setIspezioni_desc6(context.getRequest().getParameter("ispezioni_desc6"));
	    newTic.setIspezioni_desc7(context.getRequest().getParameter("ispezioni_desc7"));
	    newTic.setIspezioni_desc8(context.getRequest().getParameter("ispezioni_desc8"));
	    

	    
	    if( context.getRequest().getParameter("text_ispezione_altro")!=null ){
	    	newTic.setIspezioneAltro(context.getRequest().getParameter("text_ispezione_altro"));
	    }
	    

	    if (context.getRequest().getParameter("azione")!=null)
	    {
	    	if (context.getRequest().getParameter("azione").equals("0"))
	    	{
	    		newTic.setAzione(false);
	    		
	    	}
	    	else
	    	{
	    		newTic.setAzione(true);
	    		newTic.setAzione_descrizione(context.getRequest().getParameter("azione_descrizione"));
	    	}
	    }
	    
	    
	    if(dataFine.equals("")||(dataFine == null)){
	    	dataFine = context.getRequest().getParameter( "assignedDate" );
	    }
	    
	    if(context.getRequest().getParameter("contributi_risol_nc") != null && !context.getRequest().getParameter("contributi_risol_nc").equals("")){
	    	
	    	newTic.setContributi_verifica_risoluzione_nc(Double.parseDouble(context.getRequest().getParameter("contributi_risol_nc").replace(",", ".")));
	    }
	    
	    if(context.getRequest().getParameter("contributi_macellazione")!= null && !context.getRequest().getParameter("contributi_macellazione").equals("")){
	    	
	    	newTic.setContributi_macellazione(Double.parseDouble(context.getRequest().getParameter("contributi_macellazione").replace(",", ".")));
	    }
 if(context.getRequest().getParameter("contributi_macellazione_urgenza")!= null && !context.getRequest().getParameter("contributi_macellazione_urgenza").equals("")){
	    	
	    	newTic.setContributi_macellazione_urgenza(Double.parseDouble(context.getRequest().getParameter("contributi_macellazione_urgenza").replace(",", ".")));
	    }
	    
	    if(context.getRequest().getParameter("contributi_rilascio_certificazione")!= null && !context.getRequest().getParameter("contributi_rilascio_certificazione").equals("")){
	    	
	    	newTic.setContributi_rilascio_certificazione(Double.parseDouble(context.getRequest().getParameter("contributi_rilascio_certificazione").replace(",", ".")));
	    }
	    
	    if (context.getRequest().getParameter("tipoSosp")!=null && !(context.getRequest().getParameter("tipoSosp").equals(""))){
	    	if(Integer.parseInt(context.getRequest().getParameter("tipoSosp")) == 1)
	    		newTic.setTipoSospetto("Per emergenza ambientale");
	    	else 
	    		newTic.setTipoSospetto("Per altro motivo");
	    }
  	    
  		  

	    if(context.getRequest().getParameter("contributi_allarme_rapido")!= null && !context.getRequest().getParameter("contributi_allarme_rapido").equals("")){
	
	    	newTic.setContributi_allarme_rapido(Double.parseDouble(context.getRequest().getParameter("contributi_allarme_rapido").replace(",", ".")));
	    }
	    
	    if(context.getRequest().getParameter("contributi")!= null && !context.getRequest().getParameter("contributi").equals("")){
	    	
	    	newTic.setContributi_seguito_campioni_tamponi(Double.parseDouble(context.getRequest().getParameter("contributi").replace(",", ".")));
	    }
	    
	    if(context.getRequest().getParameter("contributi_importazione_scambio")!= null && !context.getRequest().getParameter("contributi_importazione_scambio").equals("")){
	    	
	    	newTic.setContributi_importazione_scambio(Double.parseDouble(context.getRequest().getParameter("contributi_importazione_scambio").replace(",", ".")));
	    }
	    
	    
	   /* if(context.getRequest().getParameter("codici_selezionabili")!=null){
	    	newTic.setCodiceAteco(context.getRequest().getParameter("codici_selezionabili"));
	    }*/
	    
	    if(context.getRequest().getParameter("alimenti_sospetti")!=null){
	    	newTic.setAlimentiSospetti(context.getRequest().getParameter("alimenti_sospetti"));
	    }
	    
	    if(context.getRequest().getParameter("dataSintomi")!=null){
	    	newTic.setDataSintomi(context.getRequest().getParameter("dataSintomi"));
	    }
	    
	    if(context.getRequest().getParameter("dataPasto")!=null){
	    	newTic.setDataPasto(context.getRequest().getParameter("dataPasto"));
	    }
	    
	    
	    newTic.setFollowupDate(scadenzaFollowUp);
	    newTic.setDataFineControllo( dataFine );
	    newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
	    newTic.setIdControlloUfficiale( context.getRequest().getParameter( "idControlloUfficiale" ) );
	    newTic.setFollowUp( context.getRequest().getParameter( "followUp" ) );
	    newTic.setEnteredBy(getUserId(context));
	    newTic.setModifiedBy(getUserId(context));
	    newTic.setCodiceAllerta(context.getRequest().getParameter("idAllerta"));
	    
	    //Aggiunta Codice buffer
	    newTic.setCodiceBuffer(context.getRequest().getParameter("id_buffer"));

	   
	    
	    newTic.setSiteId(Integer.parseInt(context.getRequest().getParameter("siteId")));
	    if(context.getRequest().getParameter("destinazioneDistribuzione") != null){
	    	newTic.setDestinazioneDistribuzione(Integer.parseInt(context.getRequest().getParameter("destinazioneDistribuzione")));
	    }
	    
	    
	    newTic.setUnitaMisura(context.getRequest().getParameter("unitaMisura"));
	    if("0".equals(context.getRequest().getParameter("comunicazioneRischio1")) )
	    {
	    	newTic.setComunicazioneRischio(true);
	    	newTic.setNoteRischio(context.getRequest().getParameter("noteRischio"));
	    }
	    else
	    {
	    	newTic.setComunicazioneRischio(false);
	    	
	    }
	    
	    if("0".equals(context.getRequest().getParameter("procedureRitiro")))
	    {
	    	newTic.setProceduraRitiro(context.getRequest().getParameter("procedureRitiro"));
	    	
	    }
	    else
	    {
	    	newTic.setProceduraRitiro(context.getRequest().getParameter("procedureRitiro"));
	    }
	    
	    if (context.getRequest().getParameter("procedureRichiamo") != null)
	    	newTic.setProceduraRichiamo(Integer.parseInt(context.getRequest().getParameter("procedureRichiamo")));
	    if (context.getRequest().getParameter("esitoControllo") != null)
	    newTic.setEsitoControllo(Integer.parseInt(context.getRequest().getParameter("esitoControllo")));
	    if(newTic.getEsitoControllo()==7)
	    {
	    	newTic.setNumDDt(context.getRequest().getParameter("numDdt"));
	    	String dataDdt = context.getRequest().getParameter("dataddt");
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    	Timestamp time = new Timestamp(sdf.parse(dataDdt).getTime());
	    	newTic.setDataDdt(time);
	    	  newTic.setQuantitaPartita(context.getRequest().getParameter("quantita"));
	    }
	    else
	    {
	    	if(newTic.getEsitoControllo()==4 || newTic.getEsitoControllo()==5 || newTic.getEsitoControllo()==6 || newTic.getEsitoControllo()==8)
	    	{
	    		  newTic.setQuantitaPartita(context.getRequest().getParameter("quantita"));
	    		   
	    	}
	    	else
	    	{
	    		if(newTic.getEsitoControllo()==10 || newTic.getEsitoControllo()==11  || newTic.getEsitoControllo()==14)
		    	{
	    			newTic.setQuantitaBloccata(context.getRequest().getParameter("quantitaBloccata"));
		    	}
	    		
	    	}
	    }
	    
	    String[] azioniAdottate = context.getRequest().getParameterValues("azioniAdottate");
	    if(context.getRequest().getParameter("idFile")!=null && !context.getRequest().getParameter("idFile").equals("-1") )
	    {
	    	newTic.setIdFileAllegato(Integer.parseInt(context.getRequest().getParameter("idFile")));
	    }
	    else
	    {
	    	if(context.getRequest().getParameter("idFileSupervisione")!=null && !context.getRequest().getParameter("idFileSupervisione").equals("-1") )
		    {
		    	newTic.setIdFileAllegato(Integer.parseInt(context.getRequest().getParameter("idFileSupervisione")));
		    }
	    	else
	    	newTic.setIdFileAllegato(-1);
	    	
	    }
	    
	    
	  
	    if (context.getRequest().getParameter("articoliAzioni")!=null)
	    	newTic.setAzioneArticolo(Integer.parseInt(context.getRequest().getParameter("articoliAzioni")));
	    	
	    
	    
	    
	

	      LookupList SiteIdList = new LookupList();
	      SiteIdList.setTable("lookup_site_id");
	      SiteIdList.buildListWithEnabled(db);
	      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteIdList", SiteIdList);
	      
	      LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
	      categoriaRischioList.addItem(-1, "-- SELEZIONA VOCE --");
	      categoriaRischioList.removeElementByLevel(2);
	     
	      context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
	      
	     // UserBean user = (UserBean) context.getSession().getAttribute("User");
	      LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
	      
	     
	      int elementi = Integer.parseInt(context.getRequest().getParameter("elementi"));
	      
	      for (int i = 0 ; i < elementi ; i++)
			{
				int c = i+1;
				String medici = "";
				String tpal = "";
				String veterinati = "";
				String userId ="-1";
				String altri = "";
				if (context.getRequest().getParameter("nucleo_ispettivo_"+c)!=null && ! "-1".equals(context.getRequest().getParameter("nucleo_ispettivo_"+c)))
				{
					int nucleo = Integer.parseInt(context.getRequest().getParameter("nucleo_ispettivo_"+c));
					userId = context.getRequest().getParameter("risorse_"+c);
					veterinati = context.getRequest().getParameter("componenteid_"+c);
					altri = context.getRequest().getParameter("Utente_"+c);
					if (userId == null)
						userId = "-1";
					if (Integer.parseInt(userId)>0)
						setComponentiNucleo(newTic, veterinati, nucleo,userId, i);
					else	
						setComponentiNucleo(newTic, altri, nucleo,"-1", i);


					/*  switch(nucleo)
	    		  {
	    		  case 1 :
	    		  {




	    			  setComponentiNucleo(newTic, altri, nucleo,"-1", i);


	    			  break;

	    		  }
	    		  case 2 :
	    		  {

	    			  userId = context.getRequest().getParameter("Medici_"+c);
	    			  medici = context.getRequest().getParameter("componenteid_"+c);




	    			  setComponentiNucleo(newTic, medici, nucleo,userId, i);
	    			  break;
	    		  }
	    		  case 23 :
	    		  {



	    			  userId = context.getRequest().getParameter("Tpal_"+c);
	    			  tpal = context.getRequest().getParameter("componenteid_"+c);


	    			  setComponentiNucleo(newTic, tpal, nucleo,userId, i);
	    			  break;
	    		  }
	    		  case 24 :
	    		  {

	    			  userId = context.getRequest().getParameter("Ref_"+c);
	    			  tpal = context.getRequest().getParameter("componenteid_"+c);

	    			  setComponentiNucleo(newTic, tpal, nucleo,userId, i);
	    			  break;
	    		  }
	    		  case 25 :
	    		  {


	    			  userId = context.getRequest().getParameter("Amm_"+c);
	    			  tpal = context.getRequest().getParameter("componenteid_"+c);

	    			  setComponentiNucleo(newTic, tpal, nucleo,userId, i);
	    			  break;
	    		  }
	    		  case 26 :
	    		  {


	    			  userId = context.getRequest().getParameter("criuv_"+c);
	    			  tpal = context.getRequest().getParameter("componenteid_"+c);
	    			  setComponentiNucleo(newTic, tpal, nucleo,userId, i);
	    			  break;
	    		  }
	    		  default :
	    		  {
	    			  altri = context.getRequest().getParameter("Utente_"+c);

	    			  setComponentiNucleo(newTic, altri, nucleo,"-1", i);
	    			  break;
	    		  }

	    		  }*/

				}

			}
	    	  
	      String[] listaispezioni_animali=context.getRequest().getParameterValues("animalitrasp");
	      
	      //Per i trasporti animali, in particolare per le 15 specie esistenti
	      for(int i=1;i<=27;i++){
	    	  String num_specie = context.getRequest().getParameter("num_specie"+i);
	    	  if(num_specie != null && !num_specie.equals("")){  		  
	    		  //java.lang.reflect.Method method = newTic.getClass().getMethod("setNum_specie"+i,new Class[]{Integer.class});
	    		  //.invoke(newTic.getClass(), Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  if(i==1){
	    			  newTic.setNum_specie1(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==2){
	    			  newTic.setNum_specie2(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==3){
	    			  newTic.setNum_specie3(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==4){
	    			  newTic.setNum_specie4(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==5){
	    			  newTic.setNum_specie5(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==6){
	    			  newTic.setNum_specie6(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==7){
	    			  newTic.setNum_specie7(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==8){
	    			  newTic.setNum_specie8(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==9){
	    			  newTic.setNum_specie9(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==10){
	    			  newTic.setNum_specie10(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==11){
	    			  newTic.setNum_specie11(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==12){
	    			  newTic.setNum_specie12(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==13){
	    			  newTic.setNum_specie13(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==14){
	    			  newTic.setNum_specie14(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==15){
	    			  newTic.setNum_specie15(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==22){
	    			  newTic.setNum_specie22(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==23){
	    			  newTic.setNum_specie23(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==24){
	    			  newTic.setNum_specie24(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==25){
	    			  newTic.setNum_specie25(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		  else if(i==26){
	    			  newTic.setNum_specie26(Integer.parseInt(context.getRequest().getParameter("num_specie"+i)));
	    		  }
	    		 
	    	  }
	    	
	      }
	      
	   
	      LookupList categoriaRischioList2 = new LookupList(db, "lookup_org_catrischio");
	      categoriaRischioList2.addItem(-1, "-- SELEZIONA VOCE --");
	      categoriaRischioList2.removeElementByLevel(1);
	      context.getRequest().setAttribute("OrgCategoriaRischioList2", categoriaRischioList2);
	      String[] listaispezionisel = null ;
	      
	      //if(newTic.getTipoCampione()!=5)
	    	  listaispezionisel =context.getRequest().getParameterValues("ispezione");
	    	  
	    	  HashMap<Integer,OiaNodo> tipoIspezioni = new  HashMap<Integer,OiaNodo>();
	    	  
	    	 
	    	  newTic.setTestoAppoggio("Inserimento"); //aggiunto da d.dauria		
	              
	              String tipoControllo=context.getRequest().getParameter("tipoCampione");
	              newTic.setTipoCampione(Integer.parseInt(tipoControllo));
	            
	              String[] listabpi=context.getRequest().getParameterValues("bpi");
	              String[] listaHaccp=context.getRequest().getParameterValues("haccp");
	          	   String[] tipoAudit= context.getRequest().getParameterValues("tipoAudit");
	          	   String[] tipoispezione =context.getRequest().getParameterValues("tipoIspezione");
	          	 String[] oggettoAudit= context.getRequest().getParameterValues("oggetto_audit");
	          	 
	          	   //Aggiunta per i tipi di sospetto
	          	 
	          	   LookupList lookuptipoAudit = new LookupList(db,"lookup_tipo_audit");
	          	   LookupList lookupOggettoAudit = new LookupList(db,"lookup_oggetto_audit");

	          	   LookupList lookupbpi = new LookupList(db,"lookup_bpi");
	          	   LookupList lookuphaccp = new LookupList(db,"lookup_haccp");
	          	   LookupList lookuptipoispezione = new LookupList(db,"lookup_tipo_ispezione");

	          	   newTic.setOggettoAudit(this.getHashmapfromArray(oggettoAudit, lookupOggettoAudit));

	          	   newTic.setTipoAudit(this.getHashmapfromArray(tipoAudit, lookuptipoAudit));
	          	   newTic.setLisaElementibpi(this.getHashmapfromArray(listabpi,lookupbpi));
	          	   newTic.setLisaElementihaccp(this.getHashmapfromArray(listaHaccp,lookuphaccp));
	          	   newTic.setTipoIspezione(this.getHashmapfromArray(tipoispezione,lookuptipoispezione));
	          	   
	          	   Iterator<Integer> itTipiIsp = newTic.getTipoIspezione().keySet().iterator();
	          	 int idOianodo = -1 ;
	          	   while (itTipiIsp.hasNext())
					{
	          		 idOianodo = -1 ;
						int tipo_ispezione = itTipiIsp.next();
						
						if (context.getParameter("per_condo_di"+tipo_ispezione)!=null)
							idOianodo = Integer.parseInt(context.getParameter("per_condo_di"+tipo_ispezione)) ;
						OiaNodo n = new OiaNodo();
						n.setId(idOianodo);
						tipoIspezioni.put(tipo_ispezione, n);
						
					}
	          	   newTic.setLista_uo_ispezione(tipoIspezioni);
	          	   
	          	 Iterator<Integer> itKey = newTic.getTipoIspezione().keySet().iterator();
	 			while (itKey.hasNext())
	 			{
	 				int code = itKey.next();
	 				RispostaDwrCodicePiano codiceInterno = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_tipo_ispezione", code);
	 				newTic.getTipoIspezioneCodiceInterno().add(codiceInterno.getCodiceInterno());
	 			}
	          	   
	          	   
	          	
		          	  int indice = 1 ;
		          	  ArrayList<Piano> listapianiSel = new ArrayList<Piano>();
		          	  while (context.getRequest().getParameter("piano_monitoraggio"+indice)!=null) 
		          	  {
		          		  Piano p = new Piano();
		          		  p.setId(Integer.parseInt(context.getRequest().getParameter("piano_monitoraggio"+indice)));
		          		  p.setId_uo(Integer.parseInt(context.getRequest().getParameter("uo"+indice)));
		          		  listapianiSel.add(p);
		          		  indice ++ ;
		          		  
		          	  }
		          
		          	if ( newTic.getTipoCampione()!=4)
		       	 
		          	  {
		          		newTic.setUo(context.getRequest().getParameterValues("uo_controllo"));
		          	  }
		          	  
		           newTic.setPianoMonitoraggio(listapianiSel);

	          	   
	          	   newTic.setAuditTipo(Integer.parseInt(context.getRequest().getParameter("auditTipo")));
	          	   newTic.setNoteAltrodiSistema(context.getRequest().getParameter("notealtro"));
	    	  
	    	  
	    	  
	     /* else
	      {
	    	  
	    	  listaispezioniselMacro = context.getRequest().getParameterValues("ispezione_macro");
	    	  listaispezionisel = this.getIspezioniFromMacro(db,listaispezioniselMacro);
	    	  
	      }*/
	      
	    
	      
	      
	      if(context.getRequest().getParameter("ncrilevate").equals("1"))
	    	  newTic.setNcrilevate(true);
	      else
	    	  newTic.setNcrilevate(false);
	      newTic.setInserisciContinua(false);
	    
	      String[] azioniAdottateValidazione = context.getRequest().getParameterValues("azioniAdottate");
			LookupList AzioniAdottate = new LookupList(db, "lookup_azioni_adottate");
			
			for(int k = 0 ; k < azioniAdottateValidazione.length; k++)
			{
				newTic.getAzioniAdottate().put(Integer.parseInt(azioniAdottateValidazione[k]), AzioniAdottate.getSelectedValue(azioniAdottateValidazione[k]));
			
			}
		       
	        isValid = this.validateObject(context, db, newTic) && isValid;
	        if (isValid) {
	          newTic.setTestoAppoggio("Inserimento"); //aggiunto da d.dauria			
//	          String tipoControllo=context.getRequest().getParameter("tipoCampione");
             
          	   recordInserted =  newTic.insert(db,listaispezionisel,context);
              
          	   
          	   
          	   
              String[] nc_formali=context.getRequest().getParameterValues("Provvedimenti1");
              String[] nc_significative=context.getRequest().getParameterValues("Provvedimenti2");
              String[] nc_gravi=context.getRequest().getParameterValues("Provvedimenti3");
            //  newTic.insertNonConformita(db, nc_formali, nc_significative, nc_gravi);
    		  //Inserimento della specie trasportata
    		  newTic.salvaSpecieTrasportata(db,listaispezioni_animali);
    	      
    		  /*SE IL CONTROLLO e' STATO INSERITO E SE TRA I MOTIVI DI ISPEZIONE e' SELEZIONATO COME TIPO DI
    		   * ISPEZIONE LA VOCE MACELLAZIONE FAMILIARE
    		   * E SE SONO STATI SETTATI I RELATIVI FLAG DI INSERIMENTO CAMPIONE VENGONO COSTRUITI 2 CAMPIONI CON
    		   * CAMPI AUTOCOMPILTATI FORNITI DALLA REGIONE E INSERITI AUTOMATICAMENTE INSIEME AL CONTROLLO
    		   * 
    		   * */
    		  if (recordInserted)
    		  {
    			 
    			  
				  String tipoIspezioneMacellazionePrivataString = ApplicationProperties.getProperty("ID_TIPOISPEZIONE_CAMPIONE_AUTOMATICO") ;
    			  String idMatriceCamp1String = ApplicationProperties.getProperty("ID_MATRICE_TIPO_CAMPIONE_TRICHINE") ;
    			  String idAnalitaCamp1String = ApplicationProperties.getProperty("ID_ANALITA_TIPO_CAMPIONE_TRICHINE") ;
    			  
    			  String idMatriceCamp2String = ApplicationProperties.getProperty("ID_MATRICE_TIPO_CAMPIONE_MVS") ;
    			  String idAnalitaCamp2String = ApplicationProperties.getProperty("ID_ANALITA_TIPO_CAMPIONE_MVS") ;

    			 
    						  
    				  if (tipoIspezioneMacellazionePrivataString !=null && !"".equals(tipoIspezioneMacellazionePrivataString) && !"-1".equals(tipoIspezioneMacellazionePrivataString) && 
        					  idMatriceCamp1String !=null && !"".equals(idMatriceCamp1String) && !"-1".equals(idMatriceCamp1String) &&
        					  idAnalitaCamp1String !=null && !"".equals(idAnalitaCamp1String) && !"-1".equals(idAnalitaCamp1String)
        					 )
    			  {
    					  
    					  
        				  int tipoIspezioneMacellazionePrivata = new Integer(tipoIspezioneMacellazionePrivataString);
            			  int idMatriceCamp1 =  new Integer(idMatriceCamp1String);
        				  int idAnalitaCamp1 = new Integer(idAnalitaCamp1String);
            			
    					  
    				  if (  newTic.getTipoIspezioneCodiceInterno().contains(tipoIspezioneMacellazionePrivata+"a") && context.getParameter("flagCampione1")!=null ) /*Costruzione del campione I*/
    				  {
    					  org.aspcfs.modules.campioni.base.Ticket campione = new org.aspcfs.modules.campioni.base.Ticket();
    					  /*setto i campi forniti dalla regione*/
    					  
    					  campione.setOrgId(newTic.getIdStabilimento());
    					  campione.setAssignedDate(newTic.getAssignedDate());
    					  LookupList listatipi = new LookupList(db,"lookup_tipo_ispezione");
    					  campione.setMotivazione_campione(listatipi.getValueFromCodiceInterno(tipoIspezioneMacellazionePrivata+"a"));
    					  campione.setMotivazione_piano_campione(-1);
    					  campione.setIpEntered(ip);
    					  campione.setIpModified(ip);
    					  campione.setDestinatarioCampione(2);
    					  campione.setEnteredBy(getUserId(context));
    					  campione.setModifiedBy(getUserId(context));
    					  campione.setSiteId(newTic.getSiteId());
    					  campione.setIdControlloUfficiale(newTic.getPaddedId());
    					  campione.setProblem("PRELIEVO CAMPIONE PER RICERCA TRICHINE INSERITO DA CONTROLLO");
    					  //Aggiunta generazione automatica campione
    					  int sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_osa_serial_id_seq");
    					  campione.setLocation("0"+sequence);
    					  campione.setLocation_new("0"+sequence+"-"+newTic.getSiteId());
    					  
    					  boolean inserito = campione.insert(db,context);
    					  if (inserito)
    					  {
    						  
    						  campione.insertAnaliti(db, idAnalitaCamp1, Nodo.getRamo(idAnalitaCamp1, context, "analiti", db).getPath());
    						  campione.insertMatrici(db, idMatriceCamp1,Nodo.getRamo(idMatriceCamp1, context, "matrici", db).getPath());
    					  }
    				  }
    			  }
    				  
    				  if (tipoIspezioneMacellazionePrivataString !=null && !"".equals(tipoIspezioneMacellazionePrivataString) && !"-1".equals(tipoIspezioneMacellazionePrivataString) && 
        					  idMatriceCamp2String !=null && !"".equals(idMatriceCamp2String) && !"-1".equals(idMatriceCamp2String) &&
        					  idAnalitaCamp2String !=null && !"".equals(idAnalitaCamp2String) && !"-1".equals(idAnalitaCamp2String) 
        					 )
    			
    				  {
    					  int tipoIspezioneMacellazionePrivata = new Integer(tipoIspezioneMacellazionePrivataString);
            			  
            			  int idMatriceCamp2 = new Integer(idMatriceCamp2String);
        				  int idAnalitaCamp2 = new Integer(idAnalitaCamp2String); 
    					  
    				  if ( newTic.getTipoIspezioneCodiceInterno().contains(tipoIspezioneMacellazionePrivata+"a") &&  context.getParameter("flagCampione2")!=null ) /*Costruzione del campione I*/
    				  {
    					  
    					  org.aspcfs.modules.campioni.base.Ticket campione = new org.aspcfs.modules.campioni.base.Ticket();
    					  /*setto i campi forniti dalla regione*/
    					  
    					  campione.setOrgId(newTic.getIdStabilimento());
    					  campione.setAssignedDate(newTic.getAssignedDate());
    					  LookupList listatipi = new LookupList(db,"lookup_tipo_ispezione");
    					  campione.setMotivazione_campione(listatipi.getValueFromCodiceInterno(tipoIspezioneMacellazionePrivata+"a"));
    					  campione.setMotivazione_piano_campione(-1);
    					  campione.setIpEntered(ip);
    					  campione.setIpModified(ip);
    					  campione.setDestinatarioCampione(2);
    					  campione.setEnteredBy(getUserId(context));
    					  campione.setModifiedBy(getUserId(context));
    					  campione.setSiteId(newTic.getSiteId());
    					  campione.setIdControlloUfficiale(newTic.getPaddedId());
    					  campione.setProblem("PRELIEVO COAGULO PER MVS INSERITO DA CONTROLLO");
    					  int sequence = DatabaseUtils.getNextSeqTipo(db, "barcode_osa_serial_id_seq");
    					  campione.setLocation("0"+sequence);
    					  campione.setLocation_new("0"+sequence+"-"+newTic.getSiteId());
    					  
    					  boolean inserito = campione.insert(db,context);
    					  if (inserito)
    					  {
    						  int idMatrice = -1 ;
    						  int idAnalita = -1 ;
    						  campione.insertAnaliti(db,idAnalitaCamp2 , Nodo.getRamo(idAnalitaCamp2, context, "analiti", db).getPath());
    						  campione.insertMatrici(db, idMatriceCamp2,Nodo.getRamo(idMatriceCamp2, context, "matrici", db).getPath());
    					  }
    					  
    				  }}
    			  
    			  
    		  }
    		  
	        }
	      
	     
	      if (recordInserted) {
	    	  
	    	  if (context.getRequest().getParameter("cu_int_asl")!=null)
	  	    	{
	  	    	if (context.getRequest().getParameter("cu_int_asl").equals("no"))
	  	    	{
	  	    		if (context.getRequest().getParameterValues("struttura_asl")!=null)
	  	    		{
	  	    			newTic.insert_struttura_asl(db,context.getRequest().getParameterValues("struttura_asl"));
	  	    		}
	  	    	}
	  	    	
	  	    	
	  	    }
	    	  
	    	  String[] linee_Attivita = context.getRequest().getParameterValues("id_linea_sottoposta_a_controllo");

	    	  if(newTic.getTipoCampione()!=5)
	    		  newTic.inserimentoLineeAttivita(db, newTic.getId(),linee_Attivita);
	    	  
	    	  if( context.getRequest().getParameterValues("ragioneSociale")!=null)
	    	  {
	    		  newTic.inserimentoRiferimentoSoa(db, newTic.getId(), context.getRequest().getParameterValues("ragioneSociale"), context.getRequest().getParameterValues("indirizzo"), context.getRequest().getParameterValues("orgIdSoa"));
	    	  }
	    	  String[] tipoispezione1=context.getRequest().getParameterValues("tipoIspezione");
	    	  
	    	  boolean isAllarmeRapido = false ; 
	    	  //R.M
	    	  boolean isSospetto = false ;
	    	  
	    	  if(tipoispezione1!=null)
	    	  for (int i = 0 ; i < tipoispezione1.length; i++)
	    	  {
	    		  if (tipoispezione1[i].equals("7"))
	    			  isAllarmeRapido=true ;
	    		
	    	  }
	    	  
    		  
	    	  if(isAllarmeRapido == true ){
	    		  
	    		  newTic.salvaAzioniAdottate(db, azioniAdottate);
	    		  String codiceAllerta=(String)context.getRequest().getParameter("ticketidd");
	    	  
	    	  if(!codiceAllerta.equals("")){
	    	  AslCoinvolte.addCU(codiceAllerta, newTic.getSiteId(), db);
	    	  
	    	  }}
	    	 
	    	  
	    	  String size = context.getRequest().getParameter("dim_lab");
				if(size != null){
					ArrayList<Integer> orgIdList = new ArrayList<Integer>();
					
						//Recupero iesimo org_id_op
						if(context.getRequest().getParameterValues("org_id_lab") != null ) {
							String[] lab_in_regione = context.getRequest().getParameterValues("org_id_lab") ;
							for(int i=0; i<lab_in_regione.length; i++)
							{
								if(!lab_in_regione[i].equals(""))
								{
									int org_id_op = Integer.parseInt(lab_in_regione[i]);
									orgIdList.add(org_id_op);
									newTic.insertCULaboratori(db,org_id_op);
								}
								//Query per recuperare le info sulle imprese o gli abusivi selezionati
								
						}	
					}	
				}
				/*LABORATORIO FUORI REGIONE	*/
				String size_lab = context.getRequest().getParameter("dim_lab_noreg");
				if(size_lab != null){
						ArrayList<String> labDatiList = new ArrayList<String>();
						
							if(context.getRequest().getParameterValues("text_lab") != null) {
								String[] lab_non_in_regione = context.getRequest().getParameterValues("text_lab") ;
								
								for(int i=0; i<lab_non_in_regione.length; i++)
								{
									
									String labText = lab_non_in_regione[i];
									if(!"".equals(labText))
									{
										labText = labText.replaceAll("u00E0", "a");
										labText = labText.replaceAll("u00E8", "e");
										labText = labText.replaceAll("u00f2", "o");
										labText = labText.replaceAll("u00f9", "u");
										labDatiList.add(labText);
										newTic.insertCULaboratoriNoReg(db,labText);
									}
								}
								
						}	
				}
	    	  
	    	 
	        //Prepare the ticket for the response
	        newTicket = new Ticket(db, newTic.getId());
	        
	        context.getRequest().setAttribute("idControlloCaniPadronali", newTicket.getId());
	    	 
	        /**
	         * 	INVIO LA PEC SE IL CONTROLLO e' ISPEZIONE SISTEMA ALLARME RAPIDO
	         *  PROCEDURE DI RICHIAMO RAPIDO NON ATTIVA MA NECESSARIA
	         */
	        if(newTicket.getTipoCampione()==4 && newTicket.getTipoIspezioneCodiceInterno().contains("7a"))
	        {
	        	/**
	        	 * 	PROCEDURA DI RICHIAMO NON ATTIVA MA NECESSARIA
	        	 */
	        	if (newTicket.getProceduraRichiamo() == 2)
	        	{
	        		
	        		//String filePath = this.getPath(context, "accounts");
	        		  FileItemList files = new FileItemList();
	        	      files.setBuildPortalRecords(
	        	      isPortalUser(context) ? Constants.TRUE : Constants.UNDEFINED);
	        	      files.setLinkModuleId(Constants.ACCOUNTS);
	        	      files.setLinkItemId(newTicket.getIdStabilimento());
	        	      files.buildList(db);
	        	      Iterator it = files.iterator();
	        	      String[] pathFiles 				= new String[ files.size() ];
	        	      String[] pathFilesRipristinare 	= new String[ files.size() ];
	        	      int indice2 = 0 ;
	        	      while(it.hasNext())
	        	      {
	        	    	        
	        	    	  FileItem thisItem = (FileItem) it.next();
	        	    	 
	        	    	  FileItem itemToDownload = thisItem;
	        	    	  itemToDownload.setEnteredBy(this.getUserId(context));
	        	    	  String filePath = this.getPath(context, "accounts") + getDatePath(
	        	    	  itemToDownload.getModified()) + itemToDownload.getFilename();
	        	    	  FileDownload fileDownload = new FileDownload();
	        	    	  fileDownload.setFullPath(filePath);
	        	    	  File temp = new File(filePath);
	        	    	  temp.renameTo(new File (this.getPath(context, "accounts") + getDatePath(itemToDownload.getModified())+itemToDownload.getClientFilename()));
	        	    	  fileDownload.setDisplayName(itemToDownload.getClientFilename());
	        	    	  String filePClient = this.getPath(context, "accounts") + getDatePath(itemToDownload.getModified())+itemToDownload.getClientFilename();
	        	    	  pathFiles [ indice ] = filePClient ;
	        	    	  pathFilesRipristinare [ indice ] = fileDownload.getFullPath() ;
	        	    	  indice2 ++ ;
        	    		
	        	    	
	        	      }
	        	      UtilityTemplateFile.write2(context,newTicket,db);
	        	      GestionePEC.preparaSpedisciPECcu(""+newTicket.getSiteId(), newTicket,pathFiles,pathFilesRipristinare,context);
	        	 	 	
	        	    	
        	      
	        	}
	        }
	       
	        /*GESTIONE CAMPI AGGIUNTIVI PER AZIENDE ZOOTECNICHE*/
		    AziendeZootFields fields =  new AziendeZootFields();
		    boolean gestione_campi  = false;
		      if(context.getRequest().getParameter("num_tot_animali_presenti") != null && !context.getRequest().getParameter("num_tot_animali_presenti").equals("")){
		    	  fields.setNum_tot_animali_presenti(Integer.parseInt(context.getRequest().getParameter("num_tot_animali_presenti")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("cap_max_animali") != null && !context.getRequest().getParameter("cap_max_animali").equals("")){
		    	  fields.setCap_max_animali(Integer.parseInt(context.getRequest().getParameter("cap_max_animali")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_animali_isp") != null && !context.getRequest().getParameter("num_animali_isp").equals("")){
		    	  fields.setNum_animali_isp(Integer.parseInt(context.getRequest().getParameter("num_animali_isp")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_tot_capannoni") != null && !context.getRequest().getParameter("num_tot_capannoni").equals("")){
		    	  fields.setNum_tot_capannoni(Integer.parseInt(context.getRequest().getParameter("num_tot_capannoni")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_tot_capannoni_isp") != null && !context.getRequest().getParameter("num_tot_capannoni_isp").equals("")){
		    	  fields.setNum_tot_capannoni_isp(Integer.parseInt(context.getRequest().getParameter("num_tot_capannoni_isp")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_capannoni_con_gabbie") != null && !context.getRequest().getParameter("num_capannoni_con_gabbie").equals("")){
		    	  fields.setNum_capannoni_con_gabbie(Integer.parseInt(context.getRequest().getParameter("num_capannoni_con_gabbie")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_capannoni_non_gabbie") != null && !context.getRequest().getParameter("num_capannoni_non_gabbie").equals("")){
		    	  fields.setNum_capannoni_non_gabbie(Integer.parseInt(context.getRequest().getParameter("num_capannoni_non_gabbie")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_tot_vitelli_inf_8_settimane") != null && !context.getRequest().getParameter("num_tot_vitelli_inf_8_settimane").equals("")){
		    	  fields.setNum_tot_vitelli_inf_8_settimane(Integer.parseInt(context.getRequest().getParameter("num_tot_vitelli_inf_8_settimane")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_tot_box") != null && !context.getRequest().getParameter("num_tot_box").equals("")){
		    	  fields.setNum_tot_box(Integer.parseInt(context.getRequest().getParameter("num_tot_box")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_tot_box_isp") != null && !context.getRequest().getParameter("num_tot_box_isp").equals("")){
		    	  fields.setNum_tot_box_isp(Integer.parseInt(context.getRequest().getParameter("num_tot_box_isp")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_verri") != null && !context.getRequest().getParameter("num_verri").equals("")){
		    	  fields.setNum_verri(Integer.parseInt(context.getRequest().getParameter("num_verri")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("cap_max_verri") != null && !context.getRequest().getParameter("cap_max_verri").equals("")){
		    	  fields.setCap_max_verri(Integer.parseInt(context.getRequest().getParameter("cap_max_verri")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_verri_isp") != null && !context.getRequest().getParameter("num_verri_isp").equals("")){
		    	  fields.setNum_verri_isp(Integer.parseInt(context.getRequest().getParameter("num_verri_isp")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_scrofe_scrofette") != null && !context.getRequest().getParameter("num_scrofe_scrofette").equals("")){
		    	  fields.setNum_scrofe_scrofette(Integer.parseInt(context.getRequest().getParameter("num_scrofe_scrofette")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("cap_max_scrofe_scrofette") != null && !context.getRequest().getParameter("cap_max_scrofe_scrofette").equals("")){
		    	  fields.setCap_max_scrofe_scrofette(Integer.parseInt(context.getRequest().getParameter("cap_max_scrofe_scrofette")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_scrofe_scrofette_isp") != null && !context.getRequest().getParameter("num_verri_isp").equals("")){
		    	  fields.setNum_scrofe_scrofette_isp(Integer.parseInt(context.getRequest().getParameter("num_verri_isp")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_lattonzoli") != null && !context.getRequest().getParameter("num_lattonzoli").equals("")){
		    	  fields.setNum_lattonzoli(Integer.parseInt(context.getRequest().getParameter("num_lattonzoli")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("cap_max_lattonzoli") != null && !context.getRequest().getParameter("cap_max_lattonzoli").equals("")){
		    	  fields.setCap_max_lattonzoli(Integer.parseInt(context.getRequest().getParameter("cap_max_lattonzoli")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_lattonzoli_isp") != null && !context.getRequest().getParameter("num_verri_isp").equals("")){
		    	  fields.setNum_lattonzoli_isp(Integer.parseInt(context.getRequest().getParameter("num_verri_isp")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_suinetti") != null && !context.getRequest().getParameter("num_suinetti").equals("")){
		    	  fields.setNum_suinetti(Integer.parseInt(context.getRequest().getParameter("num_suinetti")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("cap_max_suinetti") != null && !context.getRequest().getParameter("cap_max_suinetti").equals("")){
		    	  fields.setCap_max_suinetti(Integer.parseInt(context.getRequest().getParameter("cap_max_suinetti")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_suinetti_isp") != null && !context.getRequest().getParameter("num_suinetti_isp").equals("")){
		    	  fields.setNum_suinetti_isp(Integer.parseInt(context.getRequest().getParameter("num_suinetti_isp")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_suini_al_grasso") != null && !context.getRequest().getParameter("num_suini_al_grasso").equals("")){
		    	  fields.setNum_suini_al_grasso(Integer.parseInt(context.getRequest().getParameter("num_suini_al_grasso")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("cap_max_suini_al_grasso") != null && !context.getRequest().getParameter("cap_max_suini_al_grasso").equals("")){
		    	  fields.setCap_max_suini_al_grasso(Integer.parseInt(context.getRequest().getParameter("cap_max_suini_al_grasso")));
		    	  gestione_campi = true;
		      }
		      if(context.getRequest().getParameter("num_suini_al_grasso_isp") != null && !context.getRequest().getParameter("num_suini_al_grasso_isp").equals("")){
		    	  fields.setNum_suini_al_grasso_isp(Integer.parseInt(context.getRequest().getParameter("num_suini_al_grasso_isp")));
		    	  gestione_campi = true;
		      }
		      
		      if(context.getRequest().getParameter("metodo_allevamento") != null && !context.getRequest().getParameter("metodo_allevamento").equals("")){
		    	  String metodo = context.getRequest().getParameter("metodo_allevamento");
		    	  if(metodo.equals("0")) {
		    		  fields.setMetodo_allevamento("All'aperto");
		    	  }
		    	  if(metodo.equals("1")) {
		    		  fields.setMetodo_allevamento("A terra");
		    	  }
		    	  if(metodo.equals("2")) {
		    		  fields.setMetodo_allevamento("Biologico");
		    	  }
		    	  if(metodo.equals("3")) {
		    		  fields.setMetodo_allevamento("In Gabbia");
		    	  } 
		    	  
		    	  gestione_campi = true;
		      }
		     
		     if(gestione_campi){
		    	 fields.setId_allevamento(newTic.getIdStabilimento());
			     fields.setId_controllo(newTic.getId());
			     fields.insert(db);
		     }
		    	 
		     
	        context.getRequest().setAttribute("TicketDetails", newTic);
	        context.getRequest().setAttribute("AzDetails", fields);
	      
	  


	        addRecentItem(context, newTicket);

	        processInsertHook(context, newTicket);
	      } else {
	        if (newTic.getIdStabilimento() != -1) {
	          Organization thisOrg = new Organization(db, newTic.getIdStabilimento());
	          newTic.setCompanyName(thisOrg.getName());
	        }
	      }
	    context.getRequest().setAttribute("inserted",recordInserted);
        context.getRequest().setAttribute("isValid",isValid);
	    String nameOrg = (String)context.getRequest().getAttribute("name");
	             
        
	    } catch (Exception e) {
	      e.printStackTrace();
	      context.getRequest().setAttribute("Error", e.getMessage());
	      return ("SystemError");
	    } 
	   return "";
	  
  }
  public void setComponentiNucleo(Ticket newTic,String valoreComponente,int nucleo,String userId,int i)
  {
	  switch(i)
	  {
	  case 0 :
	  {
		  newTic.setNucleoIspettivo(nucleo);
		  newTic.setComponenteNucleo(valoreComponente);
		  newTic.setComponentenucleoid_uno(Integer.parseInt(userId));
		  break;
	  }
	  case 1 :
	  {
		  newTic.setNucleoIspettivoDue(nucleo);
		  newTic.setComponenteNucleoDue(valoreComponente); 
		  newTic.setComponentenucleoid_due(Integer.parseInt(userId));
		  break;
	  }
	  case 2 :
	  {
		  newTic.setNucleoIspettivoTre(nucleo);
		  newTic.setComponenteNucleoTre(valoreComponente);
		  newTic.setComponentenucleoid_tre(Integer.parseInt(userId));
		  break;
		  
	  }
	  case 3 :
	  {
		  newTic.setNucleoIspettivoQuattro(nucleo);
		  newTic.setComponenteNucleoQuattro(valoreComponente);
		  newTic.setComponentenucleoid_quattro(Integer.parseInt(userId));
		  break;
		  
	  }
	  case 4 :
	  {
		  newTic.setNucleoIspettivoCinque(nucleo);
		  newTic.setComponenteNucleoCinque(valoreComponente);
		  newTic.setComponentenucleoid_cinque(Integer.parseInt(userId));
		  break;
	  }
	  case 5 :
	  {
		  newTic.setNucleoIspettivoSei(nucleo);
		  newTic.setComponenteNucleoSei(valoreComponente);
		  newTic.setComponentenucleoid_sei(Integer.parseInt(userId));
		  break;
	  }
	  case 6 :
	  {
		  newTic.setNucleoIspettivoSette(nucleo);
		  newTic.setComponenteNucleoSette(valoreComponente);
		  newTic.setComponentenucleoid_sette(Integer.parseInt(userId));
		  break;
	  }
	  case 7 :
	  {
		  newTic.setNucleoIspettivoOtto(nucleo);
		  newTic.setComponenteNucleoOtto(valoreComponente);
		  newTic.setComponentenucleoid_otto(Integer.parseInt(userId));
		  break;
	  }
	  case 8 :
	  {
		  newTic.setNucleoIspettivoNove(nucleo);
		  newTic.setComponenteNucleoNove(valoreComponente);
		  newTic.setComponentenucleoid_nove(Integer.parseInt(userId));
		  break;
	  }
	  case 9 :
	  {
		  newTic.setNucleoIspettivoDieci(nucleo);
		  newTic.setComponenteNucleoDieci(valoreComponente);
		  newTic.setComponentenucleoid_dieci(Integer.parseInt(userId));
		  break;
	  }
	  
	  }
	  
  }
  
  
  public String executeCommandViewVigilanza(ActionContext context,Connection db) {
  
	    if (!hasPermission(context, "opu-vigilanza-view")) {
	      return ("PermissionError");
	    }
	   
	    org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
	    Organization newOrg = null;
	    //Process request parameters
	    int passedId = Integer.parseInt(
	        context.getRequest().getParameter("orgId"));
	    
	    //Prepare PagedListInfo
	    PagedListInfo ticketListInfo = this.getPagedListInfo(
	        context, "AccountTicketInfo", "t.entered", "desc");
	    ticketListInfo.setLink(
	        "AccountVigilanza.do?command=ViewVigilanza&orgId=" + passedId);
	    ticList.setPagedListInfo(ticketListInfo);
	    try {
	      
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
	      TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("TipoCampione", TipoCampione);
	      
	      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
	      EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
	      //find record permissions for portal users
	      if (!isRecordAccessPermitted(context, db, passedId)) {
	        return ("PermissionError");
	      }
	      newOrg = new Organization(db, passedId);
	      ticList.setOrgId(passedId);
	    
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
	    } 
	    return getReturn(context, "ViewVigilanza");
	  }
  
 
  

  /**
   * Loads the ticket for modification
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModifyTicket(ActionContext context,Connection db) {
   
    Ticket newTic = null;
    
    //Parameters
    
    
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      
      
      User user = this.getUser(context, this.getUserId(context));
      int siteId 		= (Integer)context.getRequest().getAttribute("siteId");
      int tipologia	 	= (Integer)context.getRequest().getAttribute("tipologia");
      
      
      ControlliUfficialiUtil.buildLookupTipoControlloUfficiale(db, context, systemStatus, user.getRoleId(), siteId,-1,null);
      
      LookupList TipoCampione = (LookupList)context.getRequest().getAttribute("TipoCampione");
      if (TipoCampione.containsKey(22))
      {
    	  if (!hasPermission(context, "vigilanza-vigilanza-supervisiona-view"))
    	  {
    		  TipoCampione.removeElementByLevel(5);
    		  
    	  }
      }
      context.getRequest().setAttribute("TipoCampione", TipoCampione);
      LookupList IspezioneMacrocategorie = new LookupList(db, "lookup_ispezione_macrocategorie");
	    IspezioneMacrocategorie.addItem(-1, "-- SELEZIONA UNA O PIU' VOCI --");
	    context.getRequest().setAttribute("IspezioneMacrocategorie", IspezioneMacrocategorie);
	    ArrayList<OiaNodo> lista_uo = UserInfo.load_unita_operative(""+siteId,db,this.getSystemStatus(context));
	      context.getRequest().setAttribute("lista_uo", lista_uo);
    
        newTic = (Ticket) context.getRequest().getAttribute("TicketDetails");
        
     Timestamp data_ultima_modifica =  newTic.getEntered();
     Timestamp current_time = new Timestamp(System.currentTimeMillis());
     //data_ultima_modifica.setDate(data_ultima_modifica.getDate()+7);
//     if ( current_time.before(data_ultima_modifica) && newTic.getTipoCampione()!=5)
    	 context.getRequest().setAttribute("Modificabile", "si");
//     }
     int idAslnodo = (user.getSiteId()>0)? user.getSiteId() : siteId ;
     
     HashMap<Integer,ArrayList<OiaNodo>> strutture = (HashMap<Integer,ArrayList<OiaNodo>>) context.getServletContext().getAttribute("StruttureOIA");
     ArrayList<OiaNodo> nodi = new ArrayList<OiaNodo>();
     if(user.getSiteId()>0)
     {
    	nodi = strutture.get(user.getSiteId());
     }
     else
     {
    	 Iterator<Integer> itK = strutture.keySet().iterator();
    	 while (itK.hasNext())
    	 {
    		 int k = itK.next();
    		 ArrayList<OiaNodo> nodiTemp = strutture.get(k);
    		 for(OiaNodo nodotemp : nodiTemp)
    		 {
    			 nodi.add(nodi.size(), nodotemp);
    		 }
    		 
    	 }
     }
     
     
     
     context.getRequest().setAttribute("StrutturaAsl", nodi);
        
        
      if(newTic.getIdFileAllegato()!=-1 && newTic.getIdFileAllegato()!=0)
      {
    	  newTic.setListaDistribuzioneAllegata(true);
    	  	FileItem thisItem = new FileItem(
              db, newTic.getIdFileAllegato(), newTic.getIdStabilimento(), Constants.ACCOUNTS);
      context.getRequest().setAttribute("fileItem", thisItem);
      } 
      else
      {
    	  newTic.setListaDistribuzioneAllegata(false);
      }
      
      /*Gestione controlli ufficiali per i laboratori haccp inseriti nei vari O.S.A*/

      newTic.getLabInRegioneControllatiList().buildListLaboratoriHACCP(db,newTic.getId()+"");
      
      /*Gestione controlli ufficiali per i laboratori haccp fuori regione inseriti nei vari O.S.A*/
      
      newTic.getLabNonInRegioneControllatiList().buildListLaboratoriHACCPNoReg(db,newTic.getId()+"");

      LookupList azioni_adottate = new LookupList();
      //Load the organization
      LookupList tipi_bpi = new LookupList();
      //Load the departmentList for assigning
      HashMap<Integer, String> lista_bpi=newTic.getLisaElementibpi();
      Iterator<Integer> iteraListaBpi = lista_bpi.keySet().iterator();
      while(iteraListaBpi.hasNext()){
      int kiave1=iteraListaBpi.next();
      String valore1=lista_bpi.get(kiave1);
    	  
      tipi_bpi.addItem(kiave1,valore1);
    	  
      }
      
      //Load the organization
      LookupList tipi_haccp = new LookupList();
      //Load the departmentList for assigning
      HashMap<Integer, String> ListaHaccp=newTic.getLisaElementihaccp();
      Iterator<Integer> iteraListaHaccp= ListaHaccp.keySet().iterator();
      while(iteraListaHaccp.hasNext()){
      int kiave1=iteraListaHaccp.next();
      String valore1=ListaHaccp.get(kiave1);
    	  
      tipi_haccp.addItem(kiave1,valore1);
    	  
      }
      //Load the organization
      LookupList linee_selezionate = new LookupList();
      String idCu = newTic.getId()+"";
      ArrayList<LineeAttivita> lista_la = LineeAttivita.opu_load_linea_attivita_per_cu(idCu, db,newTic.getIdStabilimento());
      if (lista_la.size()>0)
      {
    	  for (LineeAttivita la : lista_la)
    	  {
    		  String idLineaAttivita = "" ;
    	  
    		  if (!la.getLinea_attivita().isEmpty())
    			  idLineaAttivita = la.getCategoria() + " - " + la.getLinea_attivita();
    		  else
    			  idLineaAttivita =  la.getCategoria();
    		  linee_selezionate.addItem(la.getId(),idLineaAttivita);
    	  }
    	  
    	  if(linee_selezionate.size()==0)
    	  {
    		  ArrayList<LineeAttivita> lista_linee =LineeAttivita.load_linee_attivita_per_id_stabilimento_opu(newTic.getIdStabilimento()+"", db);
    		  for (LineeAttivita la : lista_linee)
        	  {
        		  String idLineaAttivita = "" ;
        	  
        		  if (!la.getLinea_attivita().isEmpty())
        			  idLineaAttivita =  la.getCategoria() + " - " + la.getLinea_attivita();
        		  else
        			  idLineaAttivita =  la.getCategoria();
        		  linee_selezionate.addItem(la.getId(),idLineaAttivita);
        	  }
    		  
    		  
    	  }
    	  context.getRequest().setAttribute("LineaSelezionata", linee_selezionate);
      }
      
       //commento al 214
		ArrayList<org.aspcfs.modules.canipadronali.base.Cane> lista_cani = new ArrayList<org.aspcfs.modules.canipadronali.base.Cane>();
		PreparedStatement pstCani = db.prepareStatement("select a.asset_id_canina, a.taglia,a.mantello, a.asset_id as id_cane ,a.account_id as id_prop , a.serial_number as mc , a.description as dett_add , a.po_number as tatu , a.data_nascita as data_nascita_cane , a.razza , a.sesso from asset a where idControllo = ?");
		pstCani.setInt(1, newTic.getId());
		ResultSet rsCani = pstCani.executeQuery() ;
		while (rsCani.next())
		{
			
			org.aspcfs.modules.canipadronali.base.Cane c = new org.aspcfs.modules.canipadronali.base.Cane();
			c.setId(rsCani.getInt("id_cane"));
			c.setMc(rsCani.getString("mc"));
			c.setRazza(rsCani.getString("razza"));
			c.setTaglia(rsCani.getString("taglia"));
			c.setMantello(rsCani.getString("mantello"));
			c.setSesso(rsCani.getString("sesso"));
			c.setDettagliAddizionali(rsCani.getString("dett_add"));
			c.setDataNascita(rsCani.getDate("data_nascita_cane"));
			lista_cani.add(c);
		}
		
		context.getRequest().setAttribute("lista_cani", lista_cani);
		
      
      /*COSTRUZIONE BUFFER*/
      if(newTic.getCodiceBuffer() != null && !newTic.getCodiceBuffer().equals("")) {
    	  Buffer b = new Buffer(db,Integer.parseInt(newTic.getCodiceBuffer()));
    	  context.getRequest().setAttribute("BufferDetails", b);
      }
      
    
      HashMap<Integer, String> ListaAzioniAdottate=newTic.getAzioniAdottate();
      Iterator<Integer> itListaAzioniAdottate= ListaAzioniAdottate.keySet().iterator();
      while(itListaAzioniAdottate.hasNext()){
      int kiave1=itListaAzioniAdottate.next();
      String valore1=ListaAzioniAdottate.get(kiave1);
    	  
      azioni_adottate.addItem(kiave1,valore1);
    	  
      }
      newTic.setAzioni_adottate_def(azioni_adottate);
      
      //Load the organization
      LookupList tipi_ispezioni = new LookupList();
      //Load the departmentList for assigning
      HashMap<Integer, String> ListaTipiIspezioni=newTic.getTipoIspezione();
      Iterator<Integer> iteraListaTipiIspezioni = ListaTipiIspezioni.keySet().iterator();
      while(iteraListaTipiIspezioni.hasNext()){
      int kiave1=iteraListaTipiIspezioni.next();
      String valore1=ListaTipiIspezioni.get(kiave1);
    	  
      tipi_ispezioni.addItem(kiave1,valore1);
    	  
      }
      
      
    //Load the organization
      LookupList tipi_audit = new LookupList();
      //Load the departmentList for assigning
      HashMap<Integer, String> ListaTipiAudit=newTic.getTipoAudit();
      Iterator<Integer> iteraListaTipiAudit = ListaTipiAudit.keySet().iterator();
      while(iteraListaTipiAudit.hasNext()){
      int kiave1=iteraListaTipiAudit.next();
      String valore1=ListaTipiAudit.get(kiave1);
    	  
      tipi_audit.addItem(kiave1,valore1);
    	  
      }
      
      
      newTic.setBpi_default(tipi_bpi);
      newTic.setHaccp_default(tipi_haccp);
      newTic.setTipiispezioni_default(tipi_ispezioni);
      newTic.setAudit_default(tipi_audit);
      
      
      //Load the organization
      LookupList multipleSelects1=new LookupList();
      //Load the departmentList for assigning
      HashMap<Integer, String> ListaAzioni=newTic.getAzioniAdottate();
      Iterator<Integer> iteraListaAzioni = ListaAzioni.keySet().iterator();
      while(iteraListaAzioni.hasNext()){
      int kiave1=iteraListaAzioni.next();
      String valore1=ListaAzioni.get(kiave1);
    	  
    	  multipleSelects1.addItem(kiave1,valore1);
    	  
      }
     
      LookupList AzioniAdottate =(LookupList) context.getRequest().getAttribute("AzioniAdottate");
      AzioniAdottate.setMultiple(true);
      AzioniAdottate.setSelectSize(5);
      AzioniAdottate.setMultipleSelects(multipleSelects1);
      context.getRequest().setAttribute("AzioniAdottate", AzioniAdottate);
      
      
      //Caso Trasporti Animali
      LookupList multipleSelects2=new LookupList();
      //Load the departmentList for assigning
      HashMap<Integer, String> ListaSpecie=newTic.getListaAnimali_Ispezioni();
      Iterator<Integer> iteraListaSpecie = ListaSpecie.keySet().iterator();
      while(iteraListaSpecie.hasNext()){
    	  int kiave1=iteraListaSpecie.next();
    	  String valore1=ListaSpecie.get(kiave1);
    	  multipleSelects2.addItem(kiave1,valore1);
    	  
      }
      LookupList SpecieTrasportate =(LookupList) context.getRequest().getAttribute("SpecieA");
      SpecieTrasportate.setMultiple(true);
      SpecieTrasportate.setSelectSize(10);
      SpecieTrasportate.setMultipleSelects(multipleSelects2);
      context.getRequest().setAttribute("SpecieA", SpecieTrasportate);
      
    
     
      LookupList TipoAudit =(LookupList) context.getRequest().getAttribute("TipoAudit");
      //TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
      //TipoAudit.setDefaultValue(newTic.getTipoAudit());
      if(newTic.getTipoAudit().isEmpty())
    	  TipoAudit.setSelectStyle("display:none");
      else
    	  TipoAudit.setSelectStyle("display:block");
      
      context.getRequest().setAttribute("TipoAudit", TipoAudit);
      

      LookupList PianoMonitoraggio1=(LookupList) context.getRequest().getAttribute("PianoMonitoraggio1");
      LookupList PianoMonitoraggio2= (LookupList) context.getRequest().getAttribute("PianoMonitoraggio2");
      context.getRequest().setAttribute("PianoMonitoraggio2", PianoMonitoraggio2);
      
      LookupList PianoMonitoraggio3= (LookupList) context.getRequest().getAttribute("PianoMonitoraggio3");
      context.getRequest().setAttribute("PianoMonitoraggio3", PianoMonitoraggio3);
      
   
      /*Trasporti
      LookupList llist = new LookupList(db,"lookup_specie_trasportata");
		llist.addItem(-1, "-- SELEZIONA VOCE --");
		llist.removeElementByLevel(1);
		/* llist.setMultiple(true);
  		llist.setSelectSize(5);*/
	//	context.getRequest().setAttribute("SpecieA", llist);
      
      
      HashMap<Integer, String> ListaIspezioni_o_piano_1=newTic.getLisaElementipianoMonitoraggio_ispezioni();
      Iterator<Integer> iteraKiaviIspezione_1= ListaIspezioni_o_piano_1.keySet().iterator();
     
      int kiavePiano=-1;
      while(iteraKiaviIspezione_1.hasNext())
      {
    	  kiavePiano=iteraKiaviIspezione_1.next();
    
      }
      /*
      HashMap<Integer, String> piani = newTic.getPianoMonitoraggio();
      int livello = newTic.getTipoPiano(db, idPiano);
      if(livello == 1){
      PianoMonitoraggio1.setDefaultValue(kiavePiano);
      if(newTic.getTipoIspezione()==2){
    	  
    	  PianoMonitoraggio1.setSelectStyle("display:block");
      }else{
    	  PianoMonitoraggio1.setSelectStyle("display:none");
      }
      context.getRequest().setAttribute("PianoMonitoraggio1", PianoMonitoraggio1);
      }else if(livello == 2){
    	  PianoMonitoraggio2.setDefaultValue(kiavePiano);
          if(newTic.getTipoIspezione()==2){
        	  
        	  PianoMonitoraggio2.setSelectStyle("display:block");
          }else{
        	  PianoMonitoraggio2.setSelectStyle("display:none");
          }
          context.getRequest().setAttribute("PianoMonitoraggio2", PianoMonitoraggio2);
         
      }else if(livello == 3){
    	  PianoMonitoraggio3.setDefaultValue(kiavePiano);
          if(newTic.getTipoIspezione()==2){
        	  
        	  PianoMonitoraggio3.setSelectStyle("display:block");
          }else{
        	  PianoMonitoraggio3.setSelectStyle("display:none");
          }
          context.getRequest().setAttribute("PianoMonitoraggio3", PianoMonitoraggio3);
         
      }
      commento di giuseppe
      */
     
      
      
      LookupList TipoIspezione =  (LookupList) context.getRequest().getAttribute("TipoIspezione");
      //TipoIspezione.setDefaultValue(newTic.getTipoAudit());
      /*if(newTic.getTipoIspezione()==1)
    	  TipoIspezione.setSelectStyle("display:none");
      else
    	  TipoIspezione.setSelectStyle("display:block");
      */
      context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
      
      LookupList Bpi = (LookupList) context.getRequest().getAttribute("Bpi");
     
      
      Bpi.setMultiple(true);
      LookupList multipleSelects=new LookupList();
      
      
     /* HashMap<Integer, String> ListaBpi=newTic.getLisaElementibpiOhaccp();
      Iterator<Integer> iteraKiaviBpi= newTic.getLisaElementibpiOhaccp().keySet().iterator();
      while(iteraKiaviBpi.hasNext())
      {
    	  	int kiave=iteraKiaviBpi.next();
      		String valore=ListaBpi.get(kiave);
      		multipleSelects.addItem(kiave,valore);
    	  
      }
      
      Bpi.setMultipleSelects(multipleSelects);
      
      */
     
      
      if(newTic.getLisaElementibpi().isEmpty()){
    	  Bpi.setSelectStyle("display:none");
      }else{
    	  if(newTic.getTipoAudit().containsKey(2))
    	  Bpi.setSelectStyle("display:block");
    	  else
    		  Bpi.setSelectStyle("display:none");
      }
      context.getRequest().setAttribute("Bpi", Bpi);
       
      LookupList Haccp = (LookupList) context.getRequest().getAttribute("Haccp");
      Haccp.setMultiple(true);
      Haccp.setMultipleSelects(multipleSelects);
      //Bpi.setSelectedItems(newTic.getLisaElementibpiOhaccp());
      if(newTic.getLisaElementihaccp().isEmpty()){
    	  Haccp.setSelectStyle("display:none");
      }else{
    	  if(newTic.getTipoAudit().containsKey(3))
    	 Haccp.setSelectStyle("display:block");
    	  else
    		  Haccp.setSelectStyle("display:none");
      }
      
      
      context.getRequest().setAttribute("Haccp", Haccp);
      LookupList Provvedimenti = (LookupList) context.getRequest().getAttribute("Provvedimenti");
      LookupList multipleSelectsIspezioni=new LookupList();
     
      HashMap<Integer, String>  formali=newTic.getNon_conformita_formali();
      Iterator<Integer> iteraKiaviIspezione= formali.keySet().iterator();
      while(iteraKiaviIspezione.hasNext())
      {
    	  int kiave=iteraKiaviIspezione.next();
    	  String valore=formali.get(kiave);
    	  multipleSelectsIspezioni.addItem(kiave,valore);
    	  
      }
      
      Provvedimenti.setMultiple(true);
      Provvedimenti.setMultipleSelects(multipleSelectsIspezioni);
      Provvedimenti.setMultiple(true);
      Provvedimenti.setSelectSize(7);
      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
      
      LookupList Provvedimenti2 = (LookupList) context.getRequest().getAttribute("Provvedimenti");
      LookupList multipleSelectsIspezioni2=new LookupList();
      
      
      
      HashMap<Integer, String>  significative=newTic.getNon_conformita_significative();
      Iterator<Integer> iteraKiavisignificative= significative.keySet().iterator();
      while(iteraKiavisignificative.hasNext())
      {
    	  int kiave=iteraKiavisignificative.next();
    	  String valore=significative.get(kiave);
    	  multipleSelectsIspezioni2.addItem(kiave,valore);
    	  
      }
      
      Provvedimenti2.setMultiple(true);
      Provvedimenti2.setMultipleSelects(multipleSelectsIspezioni2);
      Provvedimenti2.setMultiple(true);
      Provvedimenti2.setSelectSize(7);
      
      context.getRequest().setAttribute("Provvedimenti2", Provvedimenti2);
      LookupList Provvedimenti3 =  (LookupList) context.getRequest().getAttribute("Provvedimenti");
      LookupList multipleSelectsGravi3=new LookupList();
      
      
      
      HashMap<Integer, String>  gravi=newTic.getNon_conformita_gravi();
      Iterator<Integer> iteraKiavigravi= gravi.keySet().iterator();
      while(iteraKiavigravi.hasNext())
      {
    	  int kiave=iteraKiavigravi.next();
    	  String valore=gravi.get(kiave);
    	  multipleSelectsGravi3.addItem(kiave,valore);
    	  
      }
      
      Provvedimenti3.setMultiple(true);
      Provvedimenti3.setMultipleSelects(multipleSelectsGravi3);
      Provvedimenti3.setMultiple(true);
      Provvedimenti3.setSelectSize(7);
      
      context.getRequest().setAttribute("Provvedimenti3", Provvedimenti3);
     // LookupList Ispezione =  (LookupList) context.getRequest().getAttribute("Ispezione");
      LookupList multipleSelectsIspezioni11=new LookupList();
     
      HashMap<Integer, String> ListaIspezioni_o_piano=newTic.getLisaElementipianoMonitoraggio_ispezioni();
      Iterator<Integer> iteraKiaviIspezione2= ListaIspezioni_o_piano.keySet().iterator();
      while(iteraKiaviIspezione2.hasNext())
      {
    	  int kiave=iteraKiaviIspezione2.next();
    	  String valore=ListaIspezioni_o_piano.get(kiave);
	      multipleSelectsIspezioni11.addItem(kiave,valore);
    	  
      }
      /*Ispezione.setMultiple(true);
      Ispezione.setMultipleSelects(multipleSelectsIspezioni11);
      
      if(newTic.getTipoIspezione()==3)
      {
    	  Ispezione.setSelectStyle("display:block");
      }
       else
       {
     		 Ispezione.setSelectStyle("display:none");
       }*/
      
     
      context.getRequest().setAttribute("TicketDetails", newTic);
      addModuleBean(context, "View Accounts", "View Tickets");

      //getting current date in mm/dd/yyyy format
      String currentDate = getCurrentDateAsString(context);
      context.getRequest().setAttribute("currentDate", currentDate);

    } catch (Exception errorMessage) {
    	errorMessage.printStackTrace();
      context.getRequest().setAttribute("Error", errorMessage);
      return ("");
    } 
    //return "ModifyautorizzazionetrasportoanimaliviviOK";
    return "";
    //return getReturn(context, "ModifyTicket");
  }
  
  
  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModify(ActionContext context,Connection db) {
    if (!hasPermission(context, "opu-vigilanzaedit")) {
      return ("PermissionError");
    }
    Ticket newTic = null;
    //Parameters
    String ticketId = context.getRequest().getParameter("id");
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      User user = this.getUser(context, this.getUserId(context));
      //Load the ticket
      if (context.getRequest().getParameter("companyName") == null) {
        newTic = new Ticket();
        newTic.queryRecord(db, Integer.parseInt(ticketId));
      } else {
        newTic = (Ticket) context.getFormBean();
        if (newTic.getIdStabilimento() != -1) {
        }
      }

      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, newTic.getIdStabilimento())) {
        return ("PermissionError");
      }

      String fromDefectDetails = context.getRequest().getParameter("defectCheck");
      if (fromDefectDetails == null || "".equals(fromDefectDetails.trim())) {
        fromDefectDetails = (String) context.getRequest().getAttribute("defectCheck");
      }
      if (fromDefectDetails != null && !"".equals(fromDefectDetails.trim())) {
        context.getRequest().setAttribute("defectCheck", fromDefectDetails);
      }

     
      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("SiteIdList", SiteIdList);
      
      
      LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
      TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("TipoCampione", TipoCampione);
      
      LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
      EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
      
      LookupList DestinatarioCampione = new LookupList(db, "lookup_destinazione_campione");
      DestinatarioCampione.addItem(-1, "-- SELEZIONA VOCE --");
      context.getRequest().setAttribute("DestinatarioCampione", DestinatarioCampione);
       
     
      Organization orgDetails = new Organization(db, newTic.getIdStabilimento());
      context.getRequest().setAttribute("OrgDetails", orgDetails);

     
     
    
      context.getRequest().setAttribute("TicketDetails", newTic);
      addRecentItem(context, newTic);

      //getting current date in mm/dd/yyyy format
      String currentDate = getCurrentDateAsString(context);
      context.getRequest().setAttribute("currentDate", currentDate);
    } catch (Exception errorMessage) {
    	errorMessage.printStackTrace();
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } 
    context.getRequest().setAttribute("TicketDetails", newTic);
    addModuleBean(context, "ViewTickets", "View Tickets");

    String retPage = "Modify";

    return getReturn( context, retPage );
  }

  
  
  public String executeCommandDettaglio(ActionContext context,Connection db) {
	    if (!hasPermission(context, "opu-vigilanza-view")) {
	      return ("PermissionError");
	    }
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
	      
	      // Load the ticket
	      newTic = new Ticket();
	      newTic.queryRecord(db, Integer.parseInt(ticketId));
	      
	      String idLineaAttivita = ""+newTic.getId_imprese_linee_attivita();
	      LineeAttivita la = LineeAttivita.load_linea_attivita_per_id(idLineaAttivita, db);
	      if (!la.getLinea_attivita().isEmpty())
	    	  idLineaAttivita = la.getCodice_istat() + " : " + la.getCategoria() + " - " + la.getLinea_attivita();
				// val[i] = linea.getCategoria() + " - " + linea.getLinea_attivita();
			else
			  idLineaAttivita = la.getCodice_istat() + " : " + la.getCategoria();
				// val[i] = linea.getCategoria();
	      context.getRequest().setAttribute("linea_attivita", idLineaAttivita);
	      
	      //find record permissions for portal users
	      if (!isRecordAccessPermitted(context, db, newTic.getIdStabilimento())) {
	        return ("PermissionError");
	      }

	      Organization orgDetails = new Organization(db, newTic.getIdStabilimento());
	     

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
		if (!(hasPermission(context, "opu-vigilanza-edit"))) {
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
				return (executeCommandTicketDetails(context,db));
			} else if (resultCount == 1) {
				thisTicket.queryRecord(db, thisTicket.getId());
				this.processUpdateHook(context, oldTicket, thisTicket);
				context.getRequest().setAttribute("Chiudi", "" + flag);
				return (executeCommandTicketDetails(context,db));
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

  
  
  public String executeCommandAnnullaChiusuraTemp(ActionContext context) {
	 
	  int resultCount = -1;
	  
	  Ticket thisTicket = null;
	  Connection db = null ;
	  try {
		  
		  db = this.getConnection(context);
		  thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
		  Ticket ticket = new Ticket(db, thisTicket.getId());
		  ticket.riapriChiusuraTemporanea(db);
		 
	  }
	  catch(Exception e){
		e.printStackTrace();  
	  }
	  finally
	  {
		  this.freeConnection(context, db);
	  }
	  
	  return "-none-" ;
  }
  
  public String executeCommandChiudi(ActionContext context,Connection db) {
	  if (!(hasPermission(context, "opu-vigilanza-edit"))) {
		  return ("PermissionError");
	  }
	  int resultCount = -1;
	  Ticket thisTicket = null;
	  try {
		  
		  thisTicket = new Ticket(db, Integer.parseInt(context.getRequest()
				  .getParameter("id")));
		  Ticket oldTicket = new Ticket(db, thisTicket.getId());

		  Integer idT = thisTicket.getId();
		  String idCU = idT.toString();

		  thisTicket.setClosed(new Timestamp(System.currentTimeMillis()));

		 

		  String ticketId = context.getRequest().getParameter("id");

		  org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
		  int passedId = thisTicket.getIdStabilimento();
		  ticList.setOrgId(passedId);
		  ticList.buildListControlli(db, passedId, ticketId);

		  org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
		  int pasId = thisTicket.getIdStabilimento();
		  tamponiList.setOrgId(passedId);
		  tamponiList.buildListControlli(db, pasId, ticketId);

		  Iterator campioniIterator = ticList.iterator();
		  Iterator tamponiIterator = tamponiList.iterator();

		  int flag = 0;
		  while (campioniIterator.hasNext()) {
			  org.aspcfs.modules.campioni.base.Ticket tic = 
					  (org.aspcfs.modules.campioni.base.Ticket) campioniIterator.next();
			  if (tic.getClosed() == null) {
				  flag = 1;
				  break;
			  }
		  }

		  while (tamponiIterator.hasNext()) {
			  org.aspcfs.modules.tamponi.base.Ticket tic = 
					  (org.aspcfs.modules.tamponi.base.Ticket)tamponiIterator.next();
			  if (tic.getClosed() == null) {
				  flag = 1;
				  break;
			  }
		  }

		  if (thisTicket.isNcrilevate() == true) {
			  org.aspcfs.modules.nonconformita.base.TicketList nonCList = 
					  new org.aspcfs.modules.nonconformita.base.TicketList();
			  int passIdN = thisTicket.getIdStabilimento();
			  nonCList.setOrgId(passedId);
			  nonCList.buildListControlli(db, passIdN, ticketId);

			  Iterator ncIterator = nonCList.iterator();

			  while (ncIterator.hasNext()) {
				  org.aspcfs.modules.nonconformita.base.Ticket tic = 
						  (org.aspcfs.modules.nonconformita.base.Ticket) ncIterator.next();
				  if (tic.getClosed() == null) {
					  flag = 1;
					  break;
				  }
			  }
		  }

		  if (thisTicket.getTipoCampione()==5) {

			  org.aspcfs.checklist.base.AuditList audit = new org.aspcfs.checklist.base.AuditList();
			  int AuditOrgId = thisTicket.getIdStabilimento();
			  String idTi = thisTicket.getPaddedId();
			  audit.setOrgId(AuditOrgId);

			  audit.buildListControlli(db, AuditOrgId, idTi);

			  Iterator itAudit = audit.iterator();

			  if (!itAudit.hasNext()) {
				  flag = 2;
			  } else {
				  while (itAudit.hasNext()) {
					  org.aspcfs.checklist.base.Audit auditTemp = (org.aspcfs.checklist.base.Audit) itAudit
							  .next();
					  Organization orgDetails = new Organization(db,
							  thisTicket.getIdStabilimento());

					  if (thisTicket.isCategoriaisAggiornata() == false) {
						  flag = 2;
						  break;
					  }
				  }
			  }
		  }

		  String attivitaCollegate = context.getRequest().getParameter(
				  "altro");

		  if (attivitaCollegate == null) {
			  if (flag == 1 || flag == 2) {
				  context.getRequest().setAttribute("Chiudi", "" + flag);
				  return (executeCommandTicketDetails(context,db));
			  }
		  }

		  if (flag == 0) {
			  thisTicket.setModifiedBy(getUserId(context));
			  resultCount = thisTicket.chiudi(db);
		  }
		  if (resultCount == -1) {
			  return (executeCommandTicketDetails(context,db));
		  } else if (resultCount == 1) {
			  thisTicket.queryRecord(db, thisTicket.getId());
			  this.processUpdateHook(context, oldTicket, thisTicket);
			  return (executeCommandTicketDetails(context,db));
		  }
	  } catch (Exception errorMessage) {
		  context.getRequest().setAttribute("Error", errorMessage);
		  return ("SystemError");
	  }
	  context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
	  return ("UserError");
  }
  
  public String executeCommandViewSchedaIspezione(ActionContext context) {
	  
	 Logger logger = Logger.getLogger("MainLogger");
	 Connection db = null ;
      try {
    	  db = this.getConnection(context);
		Filtro f = new Filtro();
		HttpServletResponse res = context.getResponse();
		String orgId = context.getRequest().getParameter("orgId");
		//recupero dei dati di input
		String id_controllo = context.getParameter("idControllo");
		//String mercatoIttico= context.getParameter("mercatoIttico");

		String url = context.getRequest().getParameter("url");
		String redirect = "";
		logger.info("GESTIONE CU CENTRALIZZATA PER OPERATORE:"+url);
		f.setIdControllo(Integer.parseInt(id_controllo));
		f.setOrgId(Integer.parseInt(orgId));
		boolean mercatoIttico = f.isOperatoreMercatoIttico(db);
		ResultSet rs = null;
		
		//Completare redirect per alcuni operatori
		if(url != null){
			TipoOperatori to = TipoOperatori.valueOf(url);
			switch (to) {
			case Account : { rs = f.queryRecord_controlli(db); 
			redirect = "AccountVigilanza.do?command=TicketDetails&id="+ id_controllo + "&orgId=" + orgId;	
			break ;} 
			case Allevamenti: { rs = f.queryRecord_allevamento(db);
			redirect = "AllevamentiVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case Stabilimenti: {
				redirect = "StabilimentiVigilanza.do?command=TicketDetails&id="
					+ id_controllo + "&orgId=" + orgId;
				if(mercatoIttico){
					rs = f.queryRecord_operatori_mercatoIttico(db);
					break;
				}
				else {
					rs = f.queryRecord_controlli_stabilimenti(db); 
					break ;
				}
				
			}
			
			case OperatoriMercatoIttico: { rs = f.queryRecord_operatori_mercatoIttico(db); 
			break ;
			}
			case OperatoriCommerciali: { rs = f.queryRecord_operatori_commerciali(db); 
			
			break ;}
			case OperatoriFuoriRegione: { rs = f.queryRecord_controlli_op_fuori_asl(db);
			redirect = "OperatoriFuoriRegioneVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case Operatoriprivati: {	rs = f.queryRecord_controlli_privati(db);
			redirect = "OperatoriprivatiVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;}  
			case Farmacie: { rs = f.queryRecord_controlli_farmacosorveglianza(db); 
				
			break ;}
			case Parafarmacie: { rs = f.queryRecord_controlli_farmacosorveglianza(db); 
			redirect = "FarmacieVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case Abusivismi: { rs = f.queryRecord_controlli_abusivi(db); 
			redirect = "AbusivismiVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case Soa: { rs = f.queryRecord_controlli_soa(db); 
			redirect = "SoaVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case Osm: { rs = f.queryRecord_controlli_osm(db); 
			redirect = "OsmVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case AziendeAgricole: { rs = f.queryRecord_controlli_aziendeagricole(db); 
			redirect = "AziendeAgricoleVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break; }
			case AcqueRete: { rs = f.queryRecord_controlli_acquerete(db); 
			redirect = "AcqueReteVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break; }
			case RiproduzioneAnimale: {rs = f.queryRecord_controlli_riproduzioneanimale(db); break;}
			case OsmRegistrati:{ rs = f.queryRecord_controlli_osm(db); break ;} 
			case Trasporti: { rs = f.queryRecord_controlli_trasporto(db); 
			redirect = "TrasportiVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case Canili: { rs = f.queryRecord_controlli_canile(db); 
			redirect = "CaniliVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;}
			case Colonie: { rs = f.queryRecord_controlli_canile(db); 
			redirect = "CaniliVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case LabHaccp: { rs = f.queryRecord_controlli_lab_haccp(db); break ;} 
			case CaniPadronali: { rs = f.queryRecord_controlli_cani_padronali(db); 
			redirect = "CaniPadronaliVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;}
			case PuntiSbarco: { rs = f.queryRecord_controlli_cani_padronali(db);
			redirect = "PuntiSbarcoVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case ZoneControllo: { rs = f.queryRecord_controlli_cani_padronali(db);
			redirect = "PuntiSbarcoVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case OSAnimali: { rs = f.queryRecord_controlli_operatori_sperimentazione_animale(db); 
			redirect = "OSAnimaliVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case OpnonAltrove: { rs = f.queryRecord_controlli_cani_padronali(db);
			redirect = "OpnonAltroveVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case Imbarcazioni: { rs = f.queryRecord_controlli_cani_padronali(db);
			redirect = "ImbarcazioniVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;} 
			case MolluschiBivalvi : { rs = f.queryRecord_controlliMolluschiBivalvi(db); 
			redirect = "MolluschiBivalviVigilanza.do?command=TicketDetails&id="
				+ id_controllo + "&orgId=" + orgId;
			break ;}
			default: logger.warning("Tipo Operatore non previsto!");
			}
		}
		
		ResultSet rs_utente = f.queryRecord_utente(db);
		
		org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization(rs);
		org.setOrgId(Integer.parseInt(orgId));
		org.setOperatoreUrl(url);
		
		org.aspcf.modules.controlliufficiali.base.OrganizationUtente orgUtente = new org.aspcf.modules.controlliufficiali.base.OrganizationUtente(rs_utente);
	
		Mod5 mod5 = new Mod5();
		mod5.getFieldsMod5(db, "true", orgId, Integer.parseInt(id_controllo), org);
		
		MotivoIspezione motivoIsp = new MotivoIspezione(db,Integer.parseInt(id_controllo));
		OggettoControllo oggettoCU = new OggettoControllo(db, Integer.parseInt(id_controllo));
		
		context.getRequest().setAttribute("OrgOperatore", org);
		context.getRequest().setAttribute("OrgUtente", orgUtente);
		context.getRequest().setAttribute("OggettoControllo", oggettoCU);
		context.getRequest().setAttribute("MotivoIspezione", motivoIsp);
		context.getRequest().setAttribute("redirect", redirect);
		context.getRequest().setAttribute("bozza", context.getRequest().getParameter("bozza"));

		//context.getRequest().setAttribute("listaCampiUtente", org_utente);
	   
	 }catch (Exception e) {
		  e.printStackTrace();
        context.getRequest().setAttribute("Error", e);
        return ("SystemError");
     } 
	 finally
	 {
		 this.freeConnection(context, db);
	 }
   
	  return "ViewOK";
  }
    
  private HashMap<Integer, String> getHashmapfromArray(String[] array, LookupList lookup)
  	{
  		HashMap<Integer,String> to_return = new HashMap<Integer, String>();
  		
  		if(array!=null)
  		{
  			for(int i = 0 ; i < array.length; i ++)
  			{
  				int idArray = Integer.parseInt(array[i]);
  				String descrizione = lookup.getSelectedValue(idArray);
  				to_return.put(idArray, descrizione) ;
  			}
  		}
  		
  		return to_return;
  			
  	}
  	
  	
  	private String[] getIspezioniFromMacro(Connection db, String[] macro) throws SQLException
  	{
  		String[] toRet = null ;
  		
  		String sql = "select code from lookup_ispezione where level in ("+macro+") ";
  		String sql2 = "select count(code) as num from lookup_ispezione where level in ("+macro+") and enabled = true";
  		int numEl = 0 ;
  		
  		ResultSet rs = db.prepareStatement(sql2).executeQuery();
  		if(rs.next())
  			numEl = rs.getInt(1);
  		
  		toRet = new String[numEl];
  		
  		ResultSet rs2 = db.prepareStatement(sql).executeQuery();
  		int i = 0 ;
  		while(rs2.next())
  		{
  			toRet[i] = ""+rs2.getInt(1);
  			i++;
  		}
  			
  		
  		
  		return toRet ;
  		
  		
  		
  	}
  	
  	public String executeCommandSaveUserFields(ActionContext context) {
  	  
    	  Logger logger = Logger.getLogger("MainLogger");
    	  Connection db = null ;
        try {
  		HttpServletResponse res = context.getResponse();
  		String orgId = context.getParameter("orgId");
  		String id_controllo = context.getParameter("idControllo");
  		String bozza = context.getRequest().getParameter("bozza");
  		
  		if(bozza.equals("null")){
  			bozza = "false";
  		}
  		
  		String url = context.getRequest().getParameter("url");
  		logger.info("GESTIONE CU CENTRALIZZATA PER OPERATORE:"+url);
  		
  		
  		db = this.getConnection(context);
  		//Recupero dati utente
  		OrganizationUtente orgU = new OrganizationUtente();
  		orgU.setServizio(context.getRequest().getParameter("servizio"));
  		orgU.setUo(context.getRequest().getParameter("uo"));
  		orgU.setVia_amm(context.getRequest().getParameter("via_amm"));
  		orgU.setMail(context.getRequest().getParameter("mail"));
  		orgU.setDomicilio_digitale(context.getRequest().getParameter("domicilio_digitale"));
  		orgU.setIdControllo(id_controllo);
  		orgU.setMod(bozza);
  		orgU.setIndirizzo_legale(context.getRequest().getParameter("indirizzo_legale_rappresentante"));
  		orgU.setResidenza_legale(context.getRequest().getParameter("luogo_residenza"));
  		orgU.setNumero_legale(context.getRequest().getParameter("num_civico_rappresentante"));
  		orgU.setNome_presente_ispezione(context.getRequest().getParameter("nome_presente_ispezione"));
  		orgU.setLuogo_nascita_presente_ispezione(context.getRequest().getParameter("luogo_nascita_presente_ispezione"));
  		orgU.setGiorno_presente_ispezione(context.getRequest().getParameter("giorno_presente_ispezione"));
  		orgU.setMese_presente_ispezione(context.getRequest().getParameter("mese_presente_ispezione"));
  		orgU.setAnno_presente_ispezione(context.getRequest().getParameter("anno_presente_ispezione")); 
  		orgU.setLuogo_nascita_presente_ispezione(context.getRequest().getParameter("luogo_nascita_presente_ispezione"));
  		orgU.setLuogo_residenza_presente_ispezione(context.getRequest().getParameter("luogo_residenza_presente_ispezione"));
  		orgU.setVia_ispezione(context.getRequest().getParameter("via_ispezione"));
  		orgU.setNum_civico_presente_ispezione(context.getRequest().getParameter("num_civico_presente_ispezione"));
  		orgU.setDoc_identita_presente_ispezione(context.getRequest().getParameter("doc_identita_presente_ispezione"));
  		orgU.setStrumenti_ispezione(context.getRequest().getParameter("strumenti_ispezione"));
  		orgU.setDescrizione(context.getRequest().getParameter("descrizione"));  
  		orgU.setDichiarazione(context.getRequest().getParameter("dichiarazione"));
  		orgU.setResponsabile_procedimento(context.getRequest().getParameter("responsabile_procedimento"));
  		orgU.setNote(context.getRequest().getParameter("note")); 
  		orgU.setNumero_copie(context.getRequest().getParameter("numero_copie"));
  		orgU.setLuogo_partenza_trasporto(context.getRequest().getParameter("luogo_partenza_trasporto"));
  		orgU.setNazione_partenza_trasporto(context.getRequest().getParameter("nazione_partenza_trasporto"));
  		orgU.setData_partenza_trasporto(context.getRequest().getParameter("data_partenza"));
  		orgU.setOra_partenza_trasporto( context.getRequest().getParameter("ora_partenza"));
  		orgU.setDestinazione_trasporto(context.getRequest().getParameter("destinazione_trasporto"));
  		orgU.setNazione_destinazione_trasporto(context.getRequest().getParameter("nazione_destinazione_trasporto"));
  		orgU.setData_arrivo_trasporto(context.getRequest().getParameter("data_arrivo_trasporto"));
  		orgU.setOra_arrivo_trasporto(context.getRequest().getParameter("ora_arrivo_trasporto"));
  		orgU.setCertificato_trasporto(context.getRequest().getParameter("certificato_trasporto"));
  		orgU.setData_certificato_trasporto(context.getRequest().getParameter("data_certificato_trasporto"));
  		orgU.setLuogo_rilascio_trasporto(context.getRequest().getParameter("luogo_rilascio_trasporto"));
  		orgU.setOre(context.getRequest().getParameter("ore"));
  		
  		String val = "";
  		for (int i = 1; i <= 12; i++) {
  			String field = "gravi_" + i;
  			String value = context.getRequest().getParameter(field);
  			if(value.equals("") || value.equals("null") || value == null){
  				value = " ";
  			}
  			//if (value != null && !value.equals("null") && !value.equals("")) {
  				val += field + "=" + value + ";";				
  			//}
  		}
  		orgU.setGravi(val);
  		
  		
  		//orgU.setNumCampioni(context.getRequest().getParameter("numero_campione"));
  		
  		orgU.gestioneDatiUtente(db, Integer.parseInt(id_controllo), Integer.parseInt(orgId));
  		context.getRequest().setAttribute("chiudi", "si");
  		context.getRequest().setAttribute("bozza", bozza);
  		
        } catch (Exception e) {
  		  e.printStackTrace();
  	        context.getRequest().setAttribute("Error", e);
  	        return ("SystemError");
  	     } 
        finally
        {
        	this.freeConnection(context, db);
        }
        
  	  return executeCommandViewSchedaIspezione(context);
    }

}
