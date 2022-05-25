<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.soa.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.cessazionevariazione.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.cessazionevariazione.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.soa.base.Organization" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DestinatarioCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="defect" class="org.aspcfs.modules.troubletickets.base.TicketDefect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="AccountSizeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />

<script language="JavaScript">
  indSelected = 0;
  orgSelected = 0; 
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }

  function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";
    
        if (form.name){
      if ((orgSelected == 1) && (checkNullString(form.name.value))){
        message += "- Ragione Sociale richiesta\r\n";
        formTest = false;
      }
    }
    
      if (checkNullString(form.accountNumber.value)){
        message += "- Codice richiesto\r\n";
        formTest = false;
      }

      if (checkNullString(form.partitaIva.value)){
        message += "- Partita IVA richiesta\r\n";
        formTest = false;
      }

      if (checkNullString(form.codiceFiscaleCorrentista.value)){
        message += "- Codice ISTAT richiesto\r\n";
        formTest = false;
      }
      
      /*
      if (form.stageId.value == "-1"){
        message += "- Tipo D.I.A. richiesto\r\n";
        formTest = false;
      }
      */

      if (checkNullString(form.date2.value)){
        message += "- Data inizio attività richiesta\r\n";
        formTest = false;
      }
         
      if (form.address2latitude && form.address2latitude.value!=""){
      	 //alert(!isNaN(form.address2latitude.value));
      		if ((orgSelected == 1)  ){
      			if (isNaN(form.address2latitude.value) ||  (form.address2latitude.value < 2417159.584320) || (form.address2latitude.value > 4431788.049190)){
       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 2417159.584320 e 4431788.049190  (Sede Operativa)\r\n";
       				 formTest = false;
       			}		 
      		}
   	 }   
   	 
   	 if (form.address2longitude && form.address2longitude.value!=""){
      	 //alert(!isNaN(form.address2longitude.value));
      		if ((orgSelected == 1)  ){
      			if (isNaN(form.address2longitude.value) ||  (form.address2longitude.value < 2587487.362260) || (form.address2longitude.value > 4593983.337630)){
       			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2587487.362260 e 4593983.337630  (Sede Operativa)\r\n";
       				 formTest = false;
       			}		 
      		}
   	 }      


      if (checkNullString(form.address1line1.value)){
        message += "- Indirizzo richiesto\r\n";
        formTest = false;
      }

      if (checkNullString(form.address1city.value)){
        message += "- Comune richiesta\r\n";
        formTest = false;
      }
      /*      
      if (form.address1state.value == "-1"){
        message += "- Provincia richiesta\r\n";
        formTest = false;
      }
      */
  if (form.nameLast){
    if ((indSelected == 1) && (checkNullString(form.nameLast.value))){
      message += label("check.lastname", "- Last name is a required field\r\n");
      formTest = false;
    }
  }
  
</script>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.cessazionevariazione.base.*" %>
<%@ include file="../initPage.jsp" %>

<form name="details" action="SoaCessazionevariazione.do?command=ModifyTicket&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<td>
  <a href="Soa.do">Soa</a> > 
  <a href="Soa.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="Soa.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Scheda Stabilimento</a> >
  <%--<a href="Accounts.do?command=ViewCessazionevariazione&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="cessazionevariazione2">Cessazioni/Variazioni</dhv:label></a> >--%>
    <a href="Soa.do?command=ViewCessazionevariazione&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="cessazionevariazione2">Volture</dhv:label></a> >
  
<dhv:label name="campione.dettaglia">Scheda Voltura</dhv:label>
</td>
</tr>
</table>
<%
	String param1 = "id=" + TicketDetails.getId();
%>
<dhv:container name="soa" selected="cessazionevariazione" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>

	<%@ include file="ticket_header_include_cessazionevariazione2.jsp"%>
	<%
	
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="soa-soa-cessazionevariazione-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='SoaCessazionevariazione.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<%-- dhv:permission name="soa-soa-cessazionevariazione-edit">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='AccountCampioni.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission--%>
	<%
		} else {
	%>
	<dhv:permission name="soa-soa-cessazionevariazione-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='SoaCessazionevariazione.do?command=ModifyTicket&id=<%=TicketDetails.getId() %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%--
      <dhv:permission name="quotes-view">
        <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
          <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Details&productId=<%= TicketDetails.getProductId() %>&id=<%= TicketDetails.getId() %>';submit();"/>
        </dhv:evaluate>
      </dhv:permission>
      --%>
	<dhv:permission name="soa-soa-cessazionevariazione-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('SoaCessazionevariazione.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('SoaCessazionevariazione.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="soa-soa-cessazionevariazione-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='SoaCessazionevariazione.do?command=Chiudi&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere questa variazione?') ){submit()};">
	</dhv:permission>
	<%
		}
	%><br><br>
	<br>
	<br>
	
	<%
	if(TicketDetails.getStatoVoltura()==0 || TicketDetails.getStatoVoltura()==2)
	{
	%>
	<dhv:permission name="soa-soa-cessazionevariazione-inoltra-vet-view">
		
		<div class="buttonwrapper">
<a class="ovalbutton" href = "#dialog4" name="modal" ><span >Invia allo Stap</span></a>
</div>
		
			
	</dhv:permission>
	<%} %>
	
	
		<%
	if(TicketDetails.getStatoVoltura()==3)
	{
	%>
	<dhv:permission name="soa-soa-cessazionevariazione-inoltra-stap-view">
		
		<div class="buttonwrapper">
<a class="ovalbutton" href = "#dialog4" name="modal" ><span >Invia al Settore Veterinario</span></a>
</div>
		
			
	</dhv:permission>
	<%
 	}
		
		%>
	
	<%
	if(TicketDetails.getStatoVoltura()==4)
	{
	%>
	<dhv:permission name="soa-soa-cessazionevariazione-approva-view">
		
		<div class="buttonwrapper">
<a class="ovalbutton" href = "#dialog4" name="modal" ><span >Approva</span></a>
</div>
		
			
	</dhv:permission>
	<%} %>
	
	
	<% %>
	<dhv:permission name="soa-soa-cessazionevariazione-edit,soa-soa-cessazionevariazione-delete">
		<br />&nbsp;<br />
	</dhv:permission>

<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
<%-- include basic troubleticket add form --%>

<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>

<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>

<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.soa.base.OrganizationList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>

<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript">
  
  function checkForm(form){
    formTest = true;
    message = "";
    <dhv:include name="ticket.contact" none="true">
    if (form.siteId.value == "-1") {
      message += label("check.campioni.richiedente.selezionato","- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
      formTest = false;
    }
    </dhv:include>
    <dhv:include name="ticket.contact" none="true">
    if (form.orgId.value == "-1") {
      message += label("check.campioni.richiedente.selezionato","- Controllare che \"Impresa\" sia stato selezionato\r\n");
      formTest = false;
    }
    </dhv:include>
    if (form.assignedDate.value == "") {
      message += label("check.campioni.data_richiesta.selezionato","- Controllare che il campo \"Data\" sia stato popolato\r\n");
      formTest = false;
    }
    <dhv:include name="ticket.resolution" none="false">
    if (form.closeNow){
      if (form.closeNow.checked && form.solution.value == "") {
        message += label("check.ticket.resolution.atclose","- Resolution needs to be filled in when closing a ticket\r\n");
        formTest = false;
      }
    }
    </dhv:include>
    
    <dhv:include name="ticket.actionPlans" none="false">
      if (form.insertActionPlan.checked && form.assignedTo.value <= 0) {
        message += label("check.ticket.assignToUser","- Please assign the ticket to create the related action plan.\r\n");
        formTest = false;
      }
      if (form.insertActionPlan.checked && form.actionPlanId.value <= 0) {
        message += label("check.actionplan","- Please select an action plan to be inserted.\r\n");
        formTest = false;
      }
    </dhv:include>
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }
  
</script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Voltura</dhv:label></strong>
    </th>
	</tr>
  
   <dhv:include name="soa-sites" none="true">
 <%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="soa.site">Site</dhv:label>
      </td>
      <td>
       <%=SiteIdList.getSelectedValue(OrgDetails
										.getSiteId())%>
          <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
      
      </td>
    </tr>
<%--</dhv:evaluate>  --%>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
 <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="sanzionia.data_richiesta">Data Voltura</dhv:label>
    </td>
    <td>
          <zeroio:tz timestamp="<%= TicketDetails.getAssignedDate() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
</td>
  </tr>
  <input type="hidden" name="tipo_richiesta" value="autorizzazione_trasporto_animali_vivi" />
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Ragione Sociale</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(TicketDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
      </td>
    
  </tr>
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>
  <input type="hidden" name="tipo_richiesta" value="attivita_ispettiva_rilascioautorizzazioni_e_vigilanza" />
     <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Denominazione</dhv:label>
    </td>
   
     
      <td>
        <%= toHtmlValue(TicketDetails.getBanca()) %>
        
      </td>
    
  </tr>
    <dhv:include name="accounts-number" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="organization.accountNumber">Account Number</dhv:label>
      </td>
      <td>
              <%= ((OrgDetails.getAccountNumber()!=null && (!OrgDetails.getAccountNumber().equals("")))?(toHtmlValue(OrgDetails.getAccountNumber())):(toHtmlValue(OrgDetails.getNumAut()))) %>
      </td>
    </tr>
  </dhv:include>
      <tr class="containerBody">
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <%= toHtmlValue(TicketDetails.getPartitaIva()) %>
    </td>
  </tr>
  <tr class="containerBody" style="display:none">
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="20" maxlength="16" name="codiceFiscale" value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>">    
    </td>
  </tr>
  <tr class="containerBody" style="display: none">
	<td class="formLabel" nowrap>
      <dhv:label name="">Codice Istat Principale</dhv:label>
	</td>
	<td>
	  <%= toHtmlValue(OrgDetails.getCodiceFiscaleCorrentista()) %>
	</td>
  </tr>
  <tr class="containerBody" style="display: none">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(OrgDetails.getAlertText()) %>
    </td>
  </tr>

   
  </table>
  </br>

  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
     
    </th>
  </tr>

    <%--<tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Titolo</dhv:label>
    </td>
    <td class="containerBody"> 
       <%= TitoloList.getSelectedValue(TicketDetails.getTitoloRappresentante()) %></td>
  </tr>--%>
  <dhv:evaluate if="<%= hasText(TicketDetails.getCodiceFiscaleRappresentante()) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Codice Fiscale
			</td>
			<td>
         	<%= TicketDetails.getCodiceFiscaleRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
  	 <dhv:evaluate if="<%= hasText(TicketDetails.getCognomeRappresentante()) %>">
<tr class="containerBody">
			<td nowrap class="formLabel">
      			Cognome
			</td>
			<td>
         	<%= TicketDetails.getCognomeRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>

  	 <dhv:evaluate if="<%= hasText(TicketDetails.getNomeRappresentante()) %>">		
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Nome
			</td>
			<td>
         	<%= TicketDetails.getNomeRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
<dhv:evaluate if="<%= TicketDetails.getDataNascitaRappresentante()!=null %>">		
	<tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
       <td>
    
        <%= ((OrgDetails.getDataNascitaRappresentante()!=null)?(toHtml(DateUtils.getDateAsString(OrgDetails.getDataNascitaRappresentante(),Locale.ITALY))):("")) %>
         </td>
    </tr>
    </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(TicketDetails.getLuogoNascitaRappresentante()) %>">    
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di nascita</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(TicketDetails.getLuogoNascitaRappresentante()) %>
    </td>
  </tr>
  </dhv:evaluate>
	<dhv:evaluate if="<%= hasText(TicketDetails.getEmailRappresentante()) %>">						
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Email</dhv:label>
			</td>
			<td>
         	<%= TicketDetails.getEmailRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
	<dhv:evaluate if="<%= TicketDetails.getTelefonoRappresentante()!=null && !TicketDetails.getTelefonoRappresentante().equals("")%>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Telefono</dhv:label>
			</td>
			<td>
         	<%= toHtmlValue(TicketDetails.getTelefonoRappresentante()) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
	<dhv:evaluate if="<%= TicketDetails.getFax()!=null && !TicketDetails.getFax().equals("")%>">							
		
		<tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Fax</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(TicketDetails.getFax()) %>
    </td>
  </tr>	
  </dhv:evaluate>
</table>
<br>

<%
  boolean noneSelected = false;
%>

<br />
	<%
	if(TicketDetails.getStatoVoltura()==0)
	{
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="soa-soa-cessazionevariazione-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='SoaCessazionevariazione.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<%--dhv:permission name="campioni-campioni-edit">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='AccountCampioni.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission--%>
	<%
		} else {
	%>
	<dhv:permission name="soa-soa-cessazionevariazione-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='SoaCessazionevariazione.do?command=ModifyTicket&id=<%=TicketDetails.getId() %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%--
  <dhv:permission name="quotes-view">
    <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
      <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Display&productId=<%= TicketDetails.getProductId() %>&id=<%= TicketDetails.getId() %>';submit();"/>
    </dhv:evaluate>
  </dhv:permission>
  --%>
	<dhv:permission name="soa-soa-cessazionevariazione-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('SoaCessazionevariazione.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('SoaCessazionevariazione.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="soa-soa-cessazionevariazione-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='SoaCessazionevariazione.do?command=Chiudi&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere questa variazione?') ){submit()};">
	</dhv:permission>
	<%
		}}
	%>
</dhv:container>
</form>
<script>


$(document).ready(function() {	

	
	//select all the a tag with name equal to modal
	$('a[name=modal]').click(function(e) {
		//Cancel the link behavior
		e.preventDefault();
		
		//Get the A tag
		var id = $(this).attr('href');
	
		//Get the screen height and width
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();
	
		//Set heigth and width to mask to fill up the whole screen
		$('#mask').css({'width':maskWidth,'height':maskHeight});
		
		//transition effect		
		$('#mask').fadeIn(1000);	
		$('#mask').fadeTo("slow",0.8);	
	
		//Get the window height and width
		var winH = $(window).height();
		var winW = $(window).width();
              
		//Set the popup window to center
		$(id).css('top',  winH/2-$(id).height()/2);
		$(id).css('left', winW/2-$(id).width()/2);
		
		//transition effect
		$(id).fadeIn(2000); 
	
	});
	
	//if close button is clicked
	$('.window .close').click(function (e) {
		//Cancel the link behavior
		e.preventDefault();
		
		$('#mask').hide();
		$('.window').hide();
	});		
	
	//if mask is clicked
	$('#mask').click(function () {
		$(this).hide();
		$('.window').hide();
	});			
	
});

</script>
<style>
body {
	font-family: verdana;
	font-size: 15px;
}

a {
	color: #333;
	text-decoration: none
}

a:hover {
	color: #ccc;
	text-decoration: none
}

#mask {
	position: absolute;
	left: 0;
	top: 0;
	z-index: 9000;
	background-color: #000;
	display: none;
}

#boxes .window {
	position: absolute;
	left: 0;
	top: 0;
	width: 675px;
	height: 658;
	display: none;
	z-index: 9999;
	padding: 20px;
}

#boxes
#dialog
#
{
width:675px;
height:680;
padding:10px;
background-color:#ffffff
;}
#dialog4 {
	width: 100%;
	height: 100%;
	padding: 10px;
	background-color: #ffffff;
	overflow: scroll;
}

#boxes #dialog1 {
	width: 375px;
	height: 203px;
}

#dialog1 .d-header {
	background: url(images/login-header.png) no-repeat 0 0 transparent;
	width: 375px;
	height: 150px;
}

#dialog1 .d-header input {
	position: relative;
	top: 60px;
	left: 100px;
	border: 3px solid #cccccc;
	height: 22px;
	width: 200px;
	font-size: 15px;
	padding: 5px;
	margin-top: 4px;
}

#dialog1 .d-blank {
	float: left;
	background: url(images/login-blank.png) no-repeat 0 0 transparent;
	width: 267px;
	height: 53px;
}

#dialog1 .d-login {
	float: left;
	width: 108px;
	height: 53px;
}

#boxes #dialog2 {
	background: url(images/notice.png) no-repeat 0 0 transparent;
	width: 326px;
	height: 229px;
	padding: 50px 0 20px 25px;
}
</style>

<div id="boxes">



<div id="dialog4" class="window"><a href="#" class="close" /><font
	color="red">CHIUDI</font></a> <br>

<%
if(TicketDetails.getStatoVoltura()!=1)
{
%>

<script>
function mostaMotivazioni(){
scelta = document.getElementById('approva').value;
if (scelta=='2')
{

	document.getElementById('motivazione').style.display='';
	
}
else
{
	document.getElementById('motivazione').style.display='none';
	
}
	
}
function checkForm()
{
	formTest= false ;
	scelta = document.getElementById('approva').value ;
	msg = '' ;
if(scelta=='2')
{
	if(document.getElementById('motivo').value == '')
	{
		formTest = true ;
		msg='Controllare di aver inserito la motivazione' ;
	}
	
}

if(formTest==true)
{
alert(msg);
}
else
{
	document.motivazioneForm.submit();
}
}
</script>


<table class="trails" cellspacing="0">
<tr>
<td>
Approvazione Voltura
</td>
</tr>
</table>

<form method="post" action="SoaCessazionevariazione.do?command=ConfermaVoltura" name = "motivazioneForm">
<input type = "hidden" name = "idVoltura" value = "<%=TicketDetails.getId()  %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Approvazione</dhv:label></strong>
     
    </th>
  </tr>

   	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Stato</dhv:label>
			</td>
			<td>
			<select name = "approvazione" id = "approva" onchange="mostaMotivazioni()">
			
			<dhv:permission name="soa-soa-cessazionevariazione-approva-view">
			<option value = "1">Richiesta Approvata</option>
			</dhv:permission>
		
			<!-- VISIBILE AL VETERINARIO -->
			<dhv:permission name="soa-soa-cessazionevariazione-inoltra-vet-view">
			<option value = "3">Inoltra Allo Stap</option>
			</dhv:permission>
			
			<!-- VISIBILE ALLO STAP -->
				<dhv:permission name="soa-soa-cessazionevariazione-inoltra-stap-view">
			
				<option value = "4">Inoltra Alla Regione</option>
				<option value = "2">Richiesta Respinta</option>
				</dhv:permission>
			</select>
         	
			</td>
		</tr>
		
		<tr class="containerBody" id = "motivazione" style="display: none">
			<td nowrap class="formLabel">
      			<dhv:label name="">Motivo</dhv:label>
			</td>
			<td>
         	<textarea rows="6" cols="30" name = "motivazione" id = "motivo"></textarea>
			</td>
		</tr>
		
</table>		
  <input type="hidden" name="tipo_richiesta" value="autorizzazione_trasporto_animali_vivi" />

<input type = "button" onclick="return checkForm()" value = "Salva">
	</form>
<%	

}%>
</div>

<!-- Mask to cover the whole screen -->
<div id="mask"></div>

</div>
