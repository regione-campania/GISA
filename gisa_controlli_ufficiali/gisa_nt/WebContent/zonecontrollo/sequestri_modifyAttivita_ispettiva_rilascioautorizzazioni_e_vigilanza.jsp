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

<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.zonecontrollo.base.Organization" scope="request"/>
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

<jsp:useBean id="TipoSequestro" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDi_sp" class="org.aspcfs.utils.web.LookupList" scope="request"/>

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
<body onload="abilitaTipoSequestro1();abilitaTipoAltro();abilitaTipoSequestro();abilitaTipoSequestro1_sp();">
<form name="details" action="ZoneControlloSequestri.do?command=UpdateTicket&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" onSubmit="return checkForm(this); " method="post">

<input type ="hidden" name = "idC" value="<%=request.getAttribute("idC") %>">
<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="ZoneControllo.do">ZoneControllo</a> >
  <a href="ZoneControllo.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="ZoneControllo.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Scheda Zona di Controllo</a> > 
<a href="ZoneControlloVigilanza.do?command=ViewVigilanza&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
<a href="ZoneControlloVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >

 <%@page import="org.aspcfs.modules.troubletickets.base.Ticket"%>
 
  
<%
if (TicketDetails.getTipologiaNonConformita()==Ticket.TIPO_NON_CONFORMITA_A_CARICO)
{
	%>
<a href="ZoneControlloNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >
	
	<%
}else
{
%>
<a href="ZoneControlloAltreNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >

<%} %>  <% if (request.getParameter("return") == null) {%>
  <a href="ZoneControlloSequestri.do?command=TicketDetails&id=<%=TicketDetails.getId()%>"><dhv:label name="sequestri.dettagli">Scheda Sequestro/Blocco</dhv:label></a> >
  <%}%>
  <dhv:label name="richieste.modify">Modifica Sequestro</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<dhv:container name="zonecontrollo" selected="sequestri" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <%--@ include file="accounts_ticket_header_include.jsp" --%>
     <%@ include file="../controlliufficiali/sequestri_modify.jsp"%>
  
  </dhv:container>
</form>
</body>
    