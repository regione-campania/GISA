<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  -
  - Version: $Id: accounts_tickets_modify.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@page import="org.aspcfs.modules.troubletickets.base.Ticket"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="SequestroDiStabilimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDiLocali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDiAttrezzature" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDiAlimentiorigineAnimale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDiAlimentiorigineVegetale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDiAnimali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDiLocalieAttrezzature" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDivegetaleEanimale" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.colonie.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.sequestri.base.Ticket" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AzioneNonConforme" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitiSequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript">




function abilitaTipoAltro(){

	if(document.details.esitoSequestro.value==7){

	document.getElementById("esitoaltro").style.display="block";
		
	}
	else{
		document.getElementById("esitoaltro").style.display="none";
		
	}
	}



function abilitaTipoSequestro1(){
	 
	
	

	  tipo = document.forms['details'].SequestroDi.value;



 if(tipo=="-1"){
		 document.getElementById("notesequestridi").style.visibility="hidden";
		 }else{
			document.getElementById("notesequestridi").style.visibility="visible";
	 		 }
 if(tipo=="4" || tipo=="5" || tipo=="6"){
		document.getElementById("quantita1").style.display="block";
	}else{
		document.getElementById("quantita1").style.display="none";
		}

	
}
function updateSubList1() {
  var orgId = document.forms['details'].orgId.value;
  if(orgId != '-1'){
    var sel = document.forms['details'].elements['catCode'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets_asl.do?command=CategoryJSList&form=details&catCode=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  } else {
    var sel = document.forms['details'].elements['catCode'];
    sel.options.selectedIndex = 0;
    alert(label("select.account.first",'You have to select an Account first'));
    return;
  }
}
function updateSubList2() {
  var orgId = document.forms['details'].orgId.value;
  var sel = document.forms['details'].elements['subCat1'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets_asl.do?command=CategoryJSList&form=details&subCat1=" + escape(value)+'&orgId='+orgId;
  window.frames['server_commands'].location.href=url;
}
<dhv:include name="ticket.subCat2" none="true">
function updateSubList3() {
  var orgId = document.forms['details'].orgId.value;
  var sel = document.forms['details'].elements['subCat2'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets_asl.do?command=CategoryJSList&form=details&subCat2=" + escape(value)+'&orgId='+orgId;
  window.frames['server_commands'].location.href=url;
}
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  function updateSubList4() {
    var orgId = document.forms['details'].orgId.value;
    var sel = document.forms['details'].elements['subCat3'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets_asl.do?command=CategoryJSList&form=details&subCat3=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
function updateUserList() {
  var sel = document.forms['details'].elements['departmentCode'];
  var value = sel.options[sel.selectedIndex].value;
  var orgSite = document.forms['details'].elements['orgSiteId'].value;
  var url = "TroubleTickets_asl.do?command=DepartmentJSList&form=details&dept=Assigned&orgSiteId="+ orgSite +"&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateResolvedByUserList() {
  var sel = document.forms['details'].elements['resolvedByDeptCode'];
  var value = sel.options[sel.selectedIndex].value;
  var orgSite = document.forms['details'].elements['orgSiteId'].value;
  var url = "TroubleTickets_asl.do?command=DepartmentJSList&form=details&dept=Resolved&orgSiteId="+ orgSite + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
  function changeDivContent(divName, divContents) {
  if(document.layers){
    // Netscape 4 or equiv.
    divToChange = document.layers[divName];
    divToChange.document.open();
    divToChange.document.write(divContents);
    divToChange.document.close();
  } else if(document.all){
    // MS IE or equiv.
    divToChange = document.all[divName];
    divToChange.innerHTML = divContents;
  } else if(document.getElementById){
    // Netscape 6 or equiv.
    divToChange = document.getElementById(divName);
    divToChange.innerHTML = divContents;
  }
}
function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
}
function checkForm(form) {
  formTest = true;
  message = "";
  
if (form.assignedDate.value == "") { 
    message += label("check.ticket.dataRichiesta.entered","- Controlla che \"Data Sanzione\" sia stata selezionata\r\n");
    formTest = false;
  }
  <dhv:include name="ticket.resolution" none="false">
  if (form.closeNow.checked && form.solution.value == "") { 
    message += label("check.ticket.resolution.atclose","- Resolution needs to be filled in when closing a ticket\r\n");
    formTest = false;
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

function setAssignedDate(){
  resetAssignedDate();
  if (document.forms['details'].assignedTo.value > 0){
    document.forms['details'].assignedDate.value = document.forms['details'].currentDate.value;
  }
}

function resetAssignedDate(){
  document.forms['details'].assignedDate.value = '';
}  

function setField(formField,thisValue,thisForm) {
  var frm = document.forms[thisForm];
  var len = document.forms[thisForm].elements.length;
  var i=0;
  for( i=0 ; i<len ; i++) {
    if (frm.elements[i].name.indexOf(formField)!=-1) {
      if(thisValue){
        frm.elements[i].value = "1";
      } else {
        frm.elements[i].value = "0";
      }
    }
  }
}

function selectUserGroups() {
  var siteId = document.forms['details'].orgSiteId.value;
  if ('<%= OrgDetails.getOrgId() %>' != '-1') {
    popUserGroupsListSingle('userGroupId','changeUserGroup', '&userId=<%= User.getUserRecord().getId() %>&siteId='+siteId);
  } else {
    alert(label("select.account.first",'You have to select an Account first'));
    return;
  }
}

function popKbEntries() {
  var siteId = document.forms['details'].orgSiteId.value;
  var form = document.forms['details'];
  var catCode = form.elements['catCode'];
  var catCodeValue = catCode.options[catCode.selectedIndex].value;
  if (catCodeValue == '0') {
    alert(label('','Please select a category first'));
    return;
  }
  var subCat1 = form.elements['subCat1'];
  var subCat1Value = subCat1.options[subCat1.options.selectedIndex].value;
<dhv:include name="ticket.subCat2" none="true">
  var subCat2 = form.elements['subCat2'];
  var subCat2Value = subCat2.options[subCat2.options.selectedIndex].value;
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  var subCat3 = form.elements['subCat3'];
  var subCat3Value = subCat3.options[subCat3.options.selectedIndex].value;
</dhv:include>
  var url = 'KnowledgeBaseManager.do?command=Search&popup=true&searchcodeSiteId='+siteId+'&searchcodeCatCode='+catCodeValue;
  url = url + '&searchcodeSubCat1='+ subCat1Value;
<dhv:include name="ticket.subCat2" none="true">
  url = url + '&searchcodeSubCat2='+ subCat2Value;
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  url = url + '&searchcodeSubCat3='+ subCat3Value;
</dhv:include>
  popURL(url, 'KnowledgeBase','600','550','yes','yes');
}

 function resetCarattere(){
  	
  		
  		elm1 = document.getElementById("dat1");
 		elm2 = document.getElementById("dat2");
 		elm3 = document.getElementById("dat3");
 		elm4 = document.getElementById("dat4");
 		elm5 = document.getElementById("dat5");
 		elm6 = document.getElementById("dat6");
 		
 		elm1.style.visibility = "hidden";
 		elm2.style.visibility = "hidden";
 		elm3.style.visibility = "hidden";
 		elm4.style.visibility = "hidden";
 		elm5.style.visibility = "hidden";
 		elm6.style.visibility = "hidden";
 		
 		document.details.Provvedimenti.selectedIndex=0;
 		document.details.SequestriAmministrative.selectedIndex=0;
 		document.details.SequestriPenali.selectedIndex=0;
 		
  }
  
  function selectCarattere(str, n, m, x){
  
 		elm1 = document.getElementById("dat"+n);
 		elm2 = document.getElementById("dat"+m);
 		
 		
 		if(str == "Provvedimenti"){
 			car = document.details.Provvedimenti.value;
 		}
 		if(str == "SequestriAmministrative"){
 			car = document.details.SequestriAmministrative.value;
 		}
 		if(str == "SequestriPenali"){
 			car = document.details.SequestriPenali.value;
 		}
 		
 		if(car == 9 || (car == 6 && str == "SequestriPenali")){
 			elm1.style.visibility = "visible";
 			elm2.style.visibility = "visible";
 			
 		}
 		else {
 			elm1.style.visibility = "hidden";
 			elm2.style.visibility = "hidden";
 			
 			if(x == 1){
 			document.forms['details'].descrizione1.value="";
 			}
 			if(x == 2){
 			document.forms['details'].descrizione2.value="";
 			}
 			if(x == 3){
 			document.forms['details'].descrizione3.value="";
 			}
 		}
 	  }
</script> 
<body onload="abilitaTipoSequestro1();abilitaTipoAltro();">
<form name="details" action="ColonieSequestri.do?command=UpdateTicket&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" onSubmit="return checkForm(this); " method="post">

<input type ="hidden" name = "idC" value="<%=request.getAttribute("idC") %>">
<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Colonie.do">Colonie</a> >
  <a href="Colonie.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="Colonie.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Scheda Colonia</a> > 
<a href="Colonie.do?command=ViewVigilanza&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
<a href="ColonieVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >

  <%if (TicketDetails.getTipologiaNonConformita()==Ticket.TIPO_NON_CONFORMITA_A_CARICO){ %>
<a href="ColonieNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >
 <%}
else
{
%>
<a href="ColonieAltreNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >

<%} %>  <% if (request.getParameter("return") == null) {%>
  <a href="ColonieSequestri.do?command=TicketDetails&id=<%=TicketDetails.getId()%>"><dhv:label name="sequestri.dettagli">Scheda Sequestro/Blocco</dhv:label></a> >
  <%}%>
  <dhv:label name="richieste.modify">Modifica Sequestro</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<dhv:container name="colonie" selected="sequestri" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <%--@ include file="accounts_ticket_header_include.jsp" --%>
     <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
          <dhv:permission name="colonie-sequestri-edit">
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='ColonieSequestri.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='Colonie.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='ColonieSequestri.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
          <dhv:permission name="colonie-sequestri-edit">
            <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
          </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='Colonie.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='ColonieSequestri.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
          <%= showAttribute(request, "closedError") %>
       </dhv:evaluate>
      </dhv:evaluate>
    <br />
    <dhv:formMessage />
   <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="sequestri.information">Scheda Sequestro/Blocco</dhv:label></strong>
      </th>
	</tr>
	<dhv:include name="" none="true">
  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.site">Site</dhv:label>
    </td>
    <td>
      <%= SiteIdList.getSelectedValue(TicketDetails.getSiteId()) %>
      
      <input type="hidden" name="siteId" value="<%=TicketDetails.getSiteId()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
   
  </dhv:evaluate>
</dhv:include>
	<%--<tr>
    <td class="formLabel">
      <dhv:label name="richieste.tipo_richiesta">Tipo Richiesta</dhv:label>
    </td>
    <td>
      <dhv:label name="richieste.attivita_ispettiva_rilascioautorizzazioni_e_vigilanza">Macellazioni</dhv:label>
      <input type="hidden" name="tipo_richiesta" value="attivita_ispettiva_rilascioautorizzazioni_e_vigilanza" />
    </td>
	</tr>
	<tr class="containerBody">
      <td class="formLabel">
      <dhv:label name="richieste.tipo_animale">Ticket Source</dhv:label>
      </td>
      <td>
        <%= SourceList.getHtmlSelect("sourceCode",  TicketDetails.getSourceCode()) %>
      </td>
	</tr>--%>
	<%-- <input type="hidden" name="orgId" id="orgId" value="<%=  TicketDetails.getOrgId() %>" />--%>
	
	<tr class="containerBody">
  <td nowrap class="formLabel">
      Identificativo Non Conformita
       
        
    </td>
    <td>
        <%=TicketDetails.getIdentificativonc() %>
    </td>
</tr>
	
	
	<tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="sequestri.data_richiestas">Data Seqeustro</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="details" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
<tr class="containerBody">
  <td nowrap class="formLabel">
      Numero Verbale
       
        
    </td>
    <td>
        <input type="text" name="tipo_richiesta" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/>
       <input type="hidden" name="pippo" value="<%= toHtmlValue(TicketDetails.getPippo()) %>">
    </td>
</tr>


<dhv:include name="ticket.location" none="true">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
    Articoli Legge
    </td>
    <td>
      <table>
    <tr>
    <td>Articolo 354 C.P.P<input type="radio" name="articolo" <%if(TicketDetails.getCodiceArticolo()==1) {%>checked="checked" <%} %> value="1"/>
     &nbsp 
     </td>
      <td> Articolo 13 L. 689/81 <input type="radio" name="articolo" <%if(TicketDetails.getCodiceArticolo()==2) {%>checked="checked" <%} %> value="2"/> 
      &nbsp
      </td>
   <td> Articoli 18 e 54 Reg CE 882/04 <input type="radio" name="articolo" <%if(TicketDetails.getCodiceArticolo()==3) {%>checked="checked" <%} %> value="3"/>
   </td>   
      </tr>
    </table>
    
    </td>
  </tr>
</dhv:include>

<dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="sequestriAmministrative1" id="sequestriAmministrative1" nowrap class="formLabel">
        <dhv:label name="">Sequestro di </dhv:label>
      </td>
    <td>
     <table border=0>
      <tr>
       <td style="border:0;border-style:none">
      <%
      SequestroDi.setJsEvent("onChange=abilitaTipoSequestro1()"); %>
         <%= SequestroDi.getHtmlSelect("SequestroDi",TicketDetails.getSequestroDi()) %>
          <font color = "red">*</font>
      </td>
    <td id="quantita1" style="display:none;border:0"></br></br>
      <center>Quantità (espressa in Kg)</center></br>
    	<input type="text" name="quantita" value="<%=  TicketDetails.getQuantita() %>"  />
      </td>
    	<td " id="notesequestridi" style="visibility:hidden;border:0">
    	<center> Descrizione</center> </br>
    	<textarea rows="8" cols="50" name="notesequestridi" value="<%=TicketDetails.getNoteSequestrodi() %>"><%=TicketDetails.getNoteSequestrodi() %></textarea>
   	</td>
  </tr>
  </table>
    </td>
  </tr>
</dhv:include>


  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sequestri.note">Note</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="8"><%= toString(TicketDetails.getProblem()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>
	
	</table>
	
	
	
<br>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Follow Up del Sequestro/Blocco</dhv:label></strong>
    </th>
	
	
  
  <tr class="containerBody">
    <td class="formLabel" class="containerBody">
      <dhv:label name="sanzionia.data_ispezione">Data</dhv:label>
    </td>
    <td>
       <zeroio:dateSelect form="details" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"  showTimeZone="false" />
     <%= showAttribute(request, "estimatedResolutionDateError") %>
    </td>
  </tr>
  
   <dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td id="esitoTampone1" nowrap class="formLabel">
        <dhv:label name="">Esito</dhv:label>
      </td>
    <td>
    
    
    <table class="noborder">
    <tr class="containerBody">
    <td>
     <%
      EsitiSequestri.setJsEvent("onChange=abilitaTipoAltro()"); %>
         <%= EsitiSequestri.getHtmlSelect("esitoSequestro",TicketDetails.getEsitoSequestro()) %>
    </td>
    
    <td style="display: none" id="esitoaltro">
    Descrizione <br>
    <input type="text" name="descrizione" value="<%=TicketDetails.getDescrizione() %>">
    
    </td>
    
    </tr>
    </table>
    </td>
  </tr>
</dhv:include>
  
  
  <tr class="containerBody">
    <td valign="top" class="formLabel" class="containerBody">
      <dhv:label name="sequestri.azioni">Ulteriori Note</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="solution" cols="55" rows="8"><%= toString(TicketDetails.getSolution()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  
  
    <!--<dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="sequestriAmministrative1" id="sequestriAmministrative1" nowrap class="formLabel">
        <dhv:label name="">Sequestri Amministrative</dhv:label>
      </td>
    <td>
     <table border=0>
      <tr>
      <td >
      <%	//SequestriAmministrative.setJsEvent("onChange=\"javascript:selectCarattere('SequestriAmministrative', '3', '4', '2');\"");
        %>
         <%//= SequestriAmministrative.getHtmlSelect("SequestriAmministrative",TicketDetails.getSequestriAmministrative()) %>
         
       
    		</td>
    		<dhv:evaluate if="<%=TicketDetails.getSequestriAmministrative() != 9 %>">
          	<td style="visibility: hidden;" id="dat3">
        		Descrizione<font color="red">*</font>
        	</td>
        	<td style="visibility: hidden;" id="dat4">
           		<input type="text" name="descrizione2" size="80" value="<%=TicketDetails.getDescrizione2() %>" id="descrizione2">
          	</td>
          </dhv:evaluate>
          	<dhv:evaluate if="<%=TicketDetails.getSequestriAmministrative() == 9 %>">
          	<td style="visibility: visible;" id="dat3">
        		Descrizione<font color="red">*</font>
        	</td>
        	<td style="visibility: visible;" id="dat4">
           		<input type="text" name="descrizione2" size="80" value="<%=TicketDetails.getDescrizione2() %>">
          	</td>
          </dhv:evaluate>
       </tr>
       </table>
      
    </td>
  </tr>
</dhv:include>
 -->
  
	<%--	<tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="project.department">Department</dhv:label>
      </td>
      <td>
        <%= DepartmentList.getHtmlSelect("departmentCode", TicketDetails.getDepartmentCode()) %>
      </td>
		</tr>
		<tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label>
      </td>
      <td>
        <% UserList.setJsEvent("onChange=\"javascript:setAssignedDate();\"");%>
        <%= UserList.getHtmlSelect("assignedTo", TicketDetails.getAssignedTo() ) %>
      </td>
		</tr>
    <dhv:include name="tickets.userGroup" none="true">
    <tr class="containerBody">
      <td class="formLabel" valign="top">
        <dhv:label name="usergroup.assignedGroup">Assigned Group</dhv:label>
      </td>
      <td>
        <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr>
            <td>
              <div id="changeUserGroup">
                <dhv:evaluate if="<%= TicketDetails.getUserGroupId() != -1 %>">
                  <%= toHtml(TicketDetails.getUserGroupName()) %>
                </dhv:evaluate>
                <dhv:evaluate if="<%= TicketDetails.getUserGroupId() == -1 %>">
                  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                </dhv:evaluate>
              </div>
            </td>
            <td>
              <input type="hidden" name="userGroupId" id="userGroupId" value="<%= TicketDetails.getUserGroupId() %>"/> &nbsp;
              [<a href="javascript:selectUserGroups();"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
              [<a href="javascript:document.forms['details'].userGroupId.value='-1';javascript:changeDivContent('changeUserGroup', label('none.selected','None Selected'));"><dhv:label name="button.clear">Clear</dhv:label></a>]
            </td>
          </tr>
        </table>
      </td>
    </tr>
    </dhv:include>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="account.ticket.assignmentDate">Assignment Date</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="details" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="true" />
        <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="tickets.escalationLevel">Escalation Level</dhv:label>
      </td>
      <td>
        <%= EscalationList.getHtmlSelect("escalationLevel", TicketDetails.getEscalationLevel() ) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="ticket.estimatedResolutionDate">Estimated Resolution Date</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="details" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"  showTimeZone="true" />
        <%= showAttribute(request, "estimatedResolutionDateError") %>
      </td>
    </tr>
		<tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="ticket.issueNotes">Issue Notes</dhv:label>
      </td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td valign="top">
              <textarea name="comment" cols="55" rows="5"><%= toString(TicketDetails.getComment()) %></textarea><br />
              <dhv:label name="tickets.noteAddedtoTicketHistory.brackets">(This note is added to the ticket history. Previous notes for this ticket are listed under the history tab.)</dhv:label>
            </td>
          </tr>
        </table>
      </td>
		</tr>
  </table>
  < br />
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_asset_history.Resolution">Resolution</dhv:label></strong>
      </th>
		</tr>
    <dhv:include name="ticket.cause" none="true">
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="account.ticket.cause">Cause</dhv:label>
      </td>
      <td>
        <textarea name="cause" cols="55" rows="8"><%= toString(TicketDetails.getCause()) %></textarea>
      <dhv:include name="ticket.causeId" none="true"><br />
        <%= causeList.getHtmlSelect("causeId", TicketDetails.getCauseId()) %>
      </dhv:include>
      </td>
		</tr>
    </dhv:include>
    
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="project.department">Department</dhv:label>
    </td>
    <td>
      <%= resolvedByDeptList.getHtmlSelect("resolvedByDeptCode", TicketDetails.getResolvedByDeptCode()) %>
    </td>
	</tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="ticket.resolvedby">Resolved By</dhv:label>
    </td>
    <td>
      <%= resolvedUserList.getHtmlSelect("resolvedBy", TicketDetails.getResolvedBy()) %><br />
      <input type="checkbox" name="chk1" value="true" onclick="javascript:setField('resolvable',document.details.chk1.checked,'details');" <%= TicketDetails.getResolvable() ? " checked" : ""%>><dhv:label name="tickets.resolvable">Resolvable</dhv:label>
      <input type="hidden" name="resolvable" value="" />
  </td>
	</tr>
 
		< tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="ticket.resolution">Resolution</dhv:label>
      </td>
      <td>
        <dhv:include name="ticket.resolution" none="true">
        <textarea name="solution" cols="55" rows="8"><%= toString(TicketDetails.getSolution()) %></textarea><br /></dhv:include>
        <dhv:include name="ticket.resolutionId" none="true">
        <%= resolutionList.getHtmlSelect("resolutionId", TicketDetails.getResolutionId()) %><br />
        </dhv:include>
        <input type="checkbox" name="closeNow" value="true"><dhv:label name="tickets.ticket.close">Close ticket</dhv:label>
   
        <br>
        <input type="checkbox" name="kbase" value="true">Add this solution to Knowledge Base
 

<
<%
        CampaignList campaignList = (CampaignList) request.getAttribute("CampaignList");
        if (campaignList != null && campaignList.size() > 0) {
          HtmlSelect campaignSelect = new HtmlSelect();
          campaignSelect.addItem(-1, "-- None --");
          Iterator campaigns = campaignList.iterator();
          while (campaigns.hasNext()) {
            Campaign thisCampaign = (Campaign)campaigns.next();
            campaignSelect.addItem(thisCampaign.getId(), thisCampaign.getName());
          }
%>
        <br>Send contact a follow-up message: <%= campaignSelect.getHtml("campaignId", TicketDetails.getCampaignId()) %>
<%
        }
%>

      <%-- /td>
		</tr>
    <dhv:include name="ticket.resolution.date" none="true">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="ticket.resolutionDate">Resolution Date</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="details" field="resolutionDate" timestamp="<%= TicketDetails.getResolutionDate() %>"  timeZone="<%= TicketDetails.getResolutionDateTimeZone() %>"  showTimeZone="true" />
        <%= showAttribute(request, "resolutionDateError") %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="ticket.feedback" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.serviceExpectation.question">Have our services met or exceeded your expectations?</dhv:label>
    </td>
    <td>
      <input type="radio" name="expectation" value="1" <%= (TicketDetails.getExpectation() == 1) ? " checked" : "" %>><dhv:label name="account.yes">Yes</dhv:label>
      <input type="radio" name="expectation" value="0" <%= (TicketDetails.getExpectation() == 0) ? " checked" : "" %>><dhv:label name="account.no">No</dhv:label>
      <input type="radio" name="expectation" value="-1" <%= (TicketDetails.getExpectation() == -1) ? " checked" : "" %>><dhv:label name="account.undecided">Undecided</dhv:label>
    </td>
  </tr>
  </dhv:include>--%>
	</table>
        &nbsp;<br>
   <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
        <dhv:permission name="colonie-sequestri-edit">
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='ColonieSequestri.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='Colonie.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='ColonieSequestri.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
        <dhv:permission name="colonie-sequestri-edit">
          <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
        </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='Colonie.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='ColonieSequestri.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
        <%= showAttribute(request, "closedError") %>
     </dhv:evaluate>
    </dhv:evaluate>
    <input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
    <input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getOrgSiteId() %>" />
    <input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
    <input type="hidden" name="companyName" value="<%= toHtml(TicketDetails.getCompanyName()) %>">
    <input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
    <input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
    <input type="hidden" name="close" value="">
    <input type="hidden" name="refresh" value="-1">
    <input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
</dhv:container>
</form>
</body>
