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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.reati.base.Ticket" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ReatiAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ReatiPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
    message += label("check.ticket.dataRichiesta.entered","- Controlla che \"Data Rilevazione Reato\" sia stata selezionata\r\n");
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
 		document.details.ReatiAmministrative.selectedIndex=0;
 		document.details.ReatiPenali.selectedIndex=0;
 		
  }
  
  function selectCarattere(str, n, m, x){
  
 		elm1 = document.getElementById("dat"+n);
 		elm2 = document.getElementById("dat"+m);
 		
 		
 		if(str == "Provvedimenti"){
 			car = document.details.Provvedimenti.value;
 		}
 		if(str == "ReatiAmministrative"){
 			car = document.details.ReatiAmministrative.value;
 		}
 		if(str == "ReatiPenali"){
 			car = document.details.ReatiPenali.value;
 		}
 		
 		if(car == 9 || (car == 6 && str == "ReatiPenali")){
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
<body>
<form name="details" action="StabilimentiReati.do?command=UpdateTicket&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" onSubmit="return checkForm(this);" method="post">

<input type ="hidden" name = "idC" value="<%=request.getAttribute("idC") %>">
<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Stabilimenti.do"><dhv:label name="stabilimenti.stabilimenti">Accounts</dhv:label></a> >
  <a href="Stabilimenti.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="Stabilimenti.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="stabilimenti.details">Account Details</dhv:label></a> > 
<a href="Stabilimenti.do?command=ViewVigilanza&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
<a href="StabilimentiVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
<%@page import="org.aspcfs.modules.troubletickets.base.Ticket"%>
 
  <%if (TicketDetails.getTipologiaNonConformita()==Ticket.TIPO_NON_CONFORMITA_A_CARICO){ %>
<a href="StabilimentiNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >
 <%}
else
{
%>
<a href="StabilimentiAltreNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >

<%} %>  <% if (request.getParameter("return") == null) {%>
  <a href="StabilimentiReati.do?command=TicketDetails&id=<%=TicketDetails.getId()%>"><dhv:label name="reati.dettagli">Scheda note di reato</dhv:label></a> >
  <%}%>
  <dhv:label name="richieste.modify">Modifica Notizia di Reato</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<dhv:container name="accounts" selected="reati" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="stabilimentireati" selected="details" object="TicketDetails" param='<%= "id=" + TicketDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <%--@ include file="accounts_ticket_header_include.jsp" --%>
     <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
          <dhv:permission name="stabilimenti-stabilimenti-reati-edit">
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='StabilimentiReati.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='Stabilimenti.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='StabilimentiReati.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
          <dhv:permission name="stabilimenti-stabilimenti-reati-edit">
            <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
          </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='Stabilimenti.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='StabilimentiReati.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
          <%= showAttribute(request, "closedError") %>
       </dhv:evaluate>
      </dhv:evaluate>
    <br />
    <dhv:formMessage />
   <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="reati.information">Scheda Notizia di Reato</dhv:label></strong>
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
	
	
	<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Identificativo Non Conformità</dhv:label>
    </td>
   
     
      <td>
      		<%= TicketDetails.getIdentificativonc() %>
      </td>
    
  </tr>
	
<tr class="containerBody">
  <td nowrap class="formLabel">
     Protocollo n.
       
    </td>
    <td>
        <input type="text" name="tipo_richiesta" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/>
       <input type="hidden" name="pippo" value="<%= toHtmlValue(TicketDetails.getPippo()) %>">
    </td>
</tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="reati.data_richiestas">Del</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="details" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
    
 <dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="reatiPenali1" id="reatiPenali1" nowrap class="formLabel">
        <dhv:label name="">Illecito Penale</dhv:label>
      </td>
    <td>
     <table border=0 class="noborder">
      <tr>
      <td >
      <%	ReatiPenali.setJsEvent("onChange=\"javascript:selectCarattere('ReatiPenali', '5', '6', '3');\"");
        %>
        <%= ReatiPenali.getHtmlSelect("ReatiPenali",TicketDetails.getReatiPenali()) %>
         
       
    		</td>
    		<dhv:evaluate if="<%=TicketDetails.getReatiPenali() != 6 %>">
          	<td style="visibility: hidden;" id="dat5">
        		Descrizione<font color="red">*</font>
        	</td>
        	<td style="visibility: hidden;" id="dat6">
           		<input type="text" name="descrizione3" size="80" value="<%=TicketDetails.getDescrizione3() %>" id="descrizione3">
          	</td>
          	</dhv:evaluate>
          	<dhv:evaluate if="<%=TicketDetails.getReatiPenali() == 6 %>">
          	<td style="visibility: visible;" id="dat5">
        		Descrizione<font color="red">*</font>
        	</td>
        	<td style="visibility: visible;" id="dat6">
           		<input type="text" name="descrizione3" size="80" value="<%=TicketDetails.getDescrizione3() %>" id="descrizione3">
          	</td>
          	</dhv:evaluate>
       </tr>
       </table>
    </td>
  </tr>
</dhv:include>
    
  <tr class="containerBody">
  <td nowrap class="formLabel">
      <dhv:label name="">Norma Violata</dhv:label>
    </td>
  <td >

	  <input type="text" name="normaviolata" value="<%=TicketDetails.getNormaviolata() %>"  size="20" maxlength="256"/> <font color="red">*</font>
    
    </td>
  </tr>
    
    
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="reati.note">Note</dhv:label>
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
        &nbsp;<br>
   <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
        <dhv:permission name="stabilimenti-stabilimenti-reati-edit">
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='StabilimentiReati.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='Stabilimenti.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='StabilimentiReati.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
        <dhv:permission name="stabilimenti-stabilimenti-reati-edit">
          <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
        </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='Stabilimenti.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='StabilimentiReati.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
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
</dhv:container>
</form>
</body>
