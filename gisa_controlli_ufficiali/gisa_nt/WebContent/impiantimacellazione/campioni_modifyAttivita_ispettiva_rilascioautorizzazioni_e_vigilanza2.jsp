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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.impiantimacellazione.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.campioni.base.Ticket" scope="request"/>
<jsp:useBean id="ConseguenzePositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ResponsabilitaPositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDue" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoTre" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoQuattro" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoCinque" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoSei" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoSette" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoOtto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoNove" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDieci" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformatiValori" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DestinatarioCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<body onLoad="nascondiResponsabilita();abilitaNote(details);abilitaSpecie(details);abilitaTestoAlimentoComposto();abilitaNucleoIspettivo(details)">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript">
function abilita2(){
document.getElementById("nucleodue").style.display="";

}

function abilita3(){
document.getElementById("nucleotre").style.display="";
}

function abilita4(){
document.getElementById("nucleoquattro").style.display="";
}

function abilita5(){
document.getElementById("nucleocinque").style.display="";
}

function abilita6(){
document.getElementById("nucleosei").style.display="";
}

function abilita7(){
document.getElementById("nucleosette").style.display="";
}

function abilita8(){
document.getElementById("nucleootto").style.display="";
}

function abilita9(){
document.getElementById("nucleonove").style.display="";
}

function abilita10(){
document.getElementById("nucleodieci").style.display="";
}

//aggiunto da d.dauria
function abilitaNucleoIspettivo(form)
{
 if(form.nucleoIspettivo.value != -1)
     {
        sel3 = document.getElementById("nucleouno").style.display="";
     }
 if(form.nucleoIspettivoDue.value != -1)
     {
        sel4 = document.getElementById("nucleodue").style.display="";
     }  
  if(form.nucleoIspettivoTre.value != -1)
     {
        document.getElementById("nucleotre").style.display="";
     }        
  if(form.nucleoIspettivoQuattro.value != -1)
     {
        document.getElementById("nucleoquattro").style.display="";
     }         
   if(form.nucleoIspettivoCinque.value != -1)
     {
        document.getElementById("nucleocinque").style.display="";
     }           
   if(form.nucleoIspettivoSei.value != -1)
     {
        document.getElementById("nucleosei").style.display="";
     }           
   if(form.nucleoIspettivoSette.value != -1)
     {
        document.getElementById("nucleosette").style.display="";
     }           
    if(form.nucleoIspettivoOtto.value != -1)
     {
        document.getElementById("nucleootto").style.display="";
     }          
    if(form.nucleoIspettivoNove.value != -1)
     {
        document.getElementById("nucleonove").style.display="";
     }          
    if(form.nucleoIspettivoDieci.value != -1)
     {
        document.getElementById("nucleodieci").style.display="";
     }          

}




function abilitaCheckSegnalazione()
{
   allerta = document.getElementById("allerta");
   nonConformita = document.getElementById("nonConformita");
   if(nonConformita.checked)
    { allerta.checked = false;
    }
}


function abilitaCheckAllerta()
{
   allerta = document.getElementById("allerta");
   nonConformita = document.getElementById("nonConformita");
   if(allerta.checked)
    { nonConformita.checked = false;
    }
}



function disabilitaNonTrasformati(form)
{
   
   
   if(form.alimentiOrigineAnimaleTrasformati.value == -1)
     {
        sel3 = document.getElementById("lookupNonTrasformati");
        sel3.style.visibility = "visible";
     }
     else
     {
      sel3 = document.getElementById("lookupNonTrasformati");
      sel3.style.visibility = "hidden";
      form.alimentiOrigineAnimaleNonTrasformati.value = -1;
      sel4 = document.getElementById("lookupNonTrasformatiValori");
      sel4.style.visibility = "hidden";
      form.alimentiOrigineAnimaleNonTrasformatiValori.value = -1;
     }  

}



function abilitaCompostiVegetaleCheck(){
	var alimentiOrigine1 = document.getElementById("alimentiOrigineVegetale");
	alimentiOrigine1.disabled=false;
	
	
}
function abilitaCompostiCheck(){
	var alimentiOrigine2  = document.getElementById("alimentiComposti");
		alimentiOrigine2.disabled=false;
	
}

function abilitaAnimaliCheck(){
	 var alimentiOrigine3 = document.getElementById("alimentiOrigineAnimale");
		alimentiOrigine3.disabled=false;
	
}
function disabilitaCompostiVegetale(){
	 alimentiOrigine = document.getElementById("alimentiOrigineVegetale");
	alimentiOrigine.disabled="true";
}

function disabilitaCompostiAnimale(){
	 alimentiOrigine = document.getElementById("alimentiOrigineAnimale");
	alimentiOrigine.disabled="true";
}

function disabilitaComposti(){
	 alimentiOrigine = document.getElementById("alimentiComposti");
	alimentiOrigine.disabled="true";
}



//aggiunto per positività
function abilitaNote(form)
{
    
   
   if(form.conseguenzePositivita.value == 4)
   {
    note=document.getElementById("note");
    note.style.visibility="visible";
    note2=document.getElementById("note_etichetta");
    note2.style.visibility="visible";
   } 
   else
   {
    note=document.getElementById("note");
    note.style.visibility="hidden";
    note2=document.getElementById("note_etichetta");
    note2.style.visibility="hidden";
   
   }
}


function controlloLookup(form)
{
    //aggiunto da d.dauria
    sel = document.getElementById("lookupNonTrasformati");
    sel.style.visibility = "hidden"; 
     sel2 = document.getElementById("lookupNonTrasformatiValori");
    sel2.style.visibility = "hidden";
     sel3 = document.getElementById("lookupTrasformati");
    sel3.style.visibility = "hidden"; 
     sel4 = document.getElementById("lookupVegetale");
    sel4.style.visibility = "hidden";
    
}

function abilitaSpecie(form)
{
    if((form.alimentiOrigineAnimaleNonTrasformati.value >= 1) && (form.alimentiOrigineAnimaleNonTrasformati.value <= 4))
     {
      sel2 = document.getElementById("lookupNonTrasformatiValori");
      sel2.style.visibility = "visible";
     } 
    else
     {
      sel2 = document.getElementById("lookupNonTrasformatiValori");
      form.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
      sel2.style.visibility = "hidden";
     } 
    if(form.alimentiOrigineAnimaleNonTrasformati.value == -1)
     {
        sel3 = document.getElementById("lookupTrasformati");
        sel3.style.visibility = "visible";
        sel2 = document.getElementById("lookupNonTrasformatiValori");
        sel2.style.visibility = "hidden";
     }
     else
     {
      sel3 = document.getElementById("lookupTrasformati");
      sel3.style.visibility = "hidden";
      form.alimentiOrigineAnimaleTrasformati.value = -1;
      
     }   
}



function abilitaLookupOrigineAnimale()
{
	
    alimentiOrigine = document.getElementById("alimentiOrigineAnimale");
    sel = document.getElementById("lookupNonTrasformati");
    sel2 = document.getElementById("lookupNonTrasformatiValori");
    sel3 = document.getElementById("lookupTrasformati");
    if(alimentiOrigine.checked)
    { sel.style.visibility = "visible";
      sel3.style.visibility = "visible";
      disabilitaCompostiVegetale();
      disabilitaComposti();
    }
    else
    { sel.style.visibility = "hidden";
      sel2.style.visibility = "hidden";
      sel3.style.visibility = "hidden";
      abilitaCompostiVegetaleCheck();
      abilitaCompostiCheck();
     
    } 
     
}
function abilitaLookupOrigineVegetale()
{
    alimentiOrigine = document.getElementById("alimentiOrigineVegetale");
    sel2 = document.getElementById("lookupVegetale");
    if(alimentiOrigine.checked)
    { sel2.style.visibility = "visible";
    disabilitaCompostiAnimale();
    disabilitaComposti();
    }
    else
    { 
     sel2.style.visibility = "hidden";

     abilitaAnimaliCheck();
     abilitaCompostiCheck();
          
    }  
}


//aggiunto da d.dauria
function abilitaTestoAlimentoComposto()
{
 alimenti = document.getElementById("alimentiComposti");
 testo = document.getElementById("testoAlimentoComposto"); 
 if (alimenti.checked)
 {
  testo.style.visibility = "visible";

  disabilitaCompostiAnimale();
  disabilitaCompostiVegetale();
 
  

  
 }
 else
 {
  testo.style.visibility = "hidden";
  abilitaCompostiVegetaleCheck();
  abilitaAnimaliCheck();
 } 
}




//fine delle modifiche


function updateSubList1() {
  var orgId = document.forms['details'].orgId.value;
  if(orgId != '-1'){
    var sel = document.forms['details'].elements['catCode'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsCampioni.do?command=CategoryJSList&form=details&catCode=" + escape(value)+'&orgId='+orgId;
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
  var url = "TroubleTicketsCampioni.do?command=CategoryJSList&form=details&subCat1=" + escape(value)+'&orgId='+orgId;
  window.frames['server_commands'].location.href=url;
}
<dhv:include name="ticket.subCat2" none="true">
function updateSubList3() {
  var orgId = document.forms['details'].orgId.value;
  var sel = document.forms['details'].elements['subCat2'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTicketsCampioni.do?command=CategoryJSList&form=details&subCat2=" + escape(value)+'&orgId='+orgId;
  window.frames['server_commands'].location.href=url;
}
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  function updateSubList4() {
    var orgId = document.forms['details'].orgId.value;
    var sel = document.forms['details'].elements['subCat3'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsCampioni.do?command=CategoryJSList&form=details&subCat3=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
function updateUserList() {
  var sel = document.forms['details'].elements['departmentCode'];
  var value = sel.options[sel.selectedIndex].value;
  var orgSite = document.forms['details'].elements['orgSiteId'].value;
  var url = "TroubleTicketsCampioni.do?command=DepartmentJSList&form=details&dept=Assigned&orgSiteId="+ orgSite +"&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateResolvedByUserList() {
  var sel = document.forms['details'].elements['resolvedByDeptCode'];
  var value = sel.options[sel.selectedIndex].value;
  var orgSite = document.forms['details'].elements['orgSiteId'].value;
  var url = "TroubleTicketsCampioni.do?command=DepartmentJSList&form=details&dept=Resolved&orgSiteId="+ orgSite + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(value);
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
  
  //aggiunto da d.dauria
    alimentiOrigine = document.getElementById("alimentiOrigineAnimale");
    sel = document.getElementById("lookupNonTrasformati");
    sel2 = document.getElementById("lookupNonTrasformatiValori");
    sel3 = document.getElementById("lookupTrasformati");
    if(((alimentiOrigine.checked) && (form.alimentiOrigineAnimaleNonTrasformati.value == "-1")) && ((alimentiOrigine.checked) && (form.alimentiOrigineAnimaleTrasformati.value == "-1")))
    { message +=label("TESTO1","Controllare che il campo Alimenti Origine Animale sia stato riempito\n"); formTest = false; }
  /*  if((alimentiOrigine.checked) &&(form.alimentiOrigineAnimaleTrasformati.value == "-1"))
    { message +=label("TESTO2","Controllare che il campo Alimenti Origine Animale Trasformati sia stato selezionato\n"); formTest = false; }
    */
if (form.assignedDate.value == "") { 
    message += label("check.ticket.dataRichiesta.entered","- Controlla che \"Data Prelievo\" sia stata selezionata\r\n");
    formTest = false;
  }
  <dhv:include name="ticket.contact" none="true">
    if (form.TipoCampione.value == "-1") {
      message += label("check.campioni.richiedente.selezionato","- Controllare che \"Tipo di Analisi\" sia stato selezionato\r\n");
      formTest = false;
    }
    </dhv:include>
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
</script>
<form name="details" action="AccountCampioni.do?command=UpdateTicket&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" onSubmit="return checkForm(this);" method="post" >
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="ImpiantiMacellazione.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> >
  <a href="ImpiantiMacellazione.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="ImpiantiMacellazione.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <a href="ImpiantiMacellazione.do?command=ViewCampioni&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="campioni">Tickets</dhv:label></a> >
  <% if (request.getParameter("return") == null) {%>
  <a href="AccountCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>"><dhv:label name="campioni.dettagli">Scheda Campione</dhv:label></a> >
  <%}%>
  <dhv:label name="campioni.modify">Modifica Campione</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<dhv:container name="impiantiMacellazione" selected="campioni" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="accountscampioni" selected="details" object="TicketDetails" param='<%= "id=" + TicketDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <%--@ include file="accounts_ticket_header_include_campioni.jsp" --%>
     <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
          <%--dhv:permission name="impiantiMacellazione-impiantiMacellazione-campioni-edit">
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='AccountCampioni.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission--%>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='ImpiantiMacellazione.do?command=ViewCampioni&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='AccountCampioni.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
          <dhv:permission name="impiantiMacellazione-impiantiMacellazione-campioni-edit">
            <input type="submit" value="<dhv:label name="global.button.update" >Update</dhv:label>" onclick="return controllo_data_esito(document.details);return checkEsitoForm(document.details);"  />
          </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='ImpiantiMacellazione.do?command=ViewCampioni&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='AccountCampioni.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
          <%= showAttribute(request, "closedError") %>
       </dhv:evaluate>
      </dhv:evaluate>
    <br />
    <dhv:formMessage />
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="Campioni.information">Informazioni Campione</dhv:label></strong>
      </th>
	</tr>
	 <dhv:include name="stabilimenti-sites" none="true">
 <%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
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
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
   <%--<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Impresa</dhv:label>
    </td>
   
     
     <td>
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
      </td>--%>
    
  </tr>
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
      <td>
      		<%= toHtmlValue(TicketDetails.getIdControlloUfficiale()) %>
      </td>
  </tr>	
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Codice Campione</dhv:label>
    </td>
   
     
      <td>
      		<%= toHtmlValue(TicketDetails.getIdentificativo()) %>
      </td>
    
  </tr>	
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Numero Verbale</dhv:label>
    </td>
    <td>
	<%if((TicketDetails.getLocation() != "" && TicketDetails.getLocation() != null)){ %>
      <input type="text" name="location" id="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="20" maxlength="256" />
    <%}else{%>
          <input type="text" name="location" id="location" value="" size="20" maxlength="256" />
    <%} %>
    </td>
  </tr>
 <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Prelievo</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="details" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
	
  <%--
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.data_ispezione">Data Macellazione</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"  showTimeZone="false" />
      <%= showAttribute(request, "estimatedResolutionDateError") %>
    </td>
  </tr>--%>
  <dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Tipo di Analisi</dhv:label>
      </td>
    <td>
      <%= TipoCampione.getHtmlSelect("TipoCampione",TicketDetails.getTipoCampione()) %><font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
   <%--  <input type="hidden" name="tipoCampione" value="<%=TicketDetails.getTipoCampione()%>" > --%>
    </td>
  </tr>
</dhv:include>
<dhv:include name="organization.source" none="true">
   <tr>
      <td name="destinatarioCampione1" id="destinatarioCampione1" nowrap class="formLabel">
        <dhv:label name="">Destinatario del Campione</dhv:label>
      </td>
    <td>
      <%= DestinatarioCampione.getHtmlSelect("DestinatarioCampione",TicketDetails.getDestinatarioCampione()) %>
  <%--   <input type="hidden" name="destinatarioCampione" value="<%=TicketDetails.getDestinatarioCampione()%>" > --%>
    </td>
  </tr>
</dhv:include>
<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Accettazione</dhv:label>
    </td>
    <td>
        <zeroio:dateSelect form="details" field="dataAccettazione" timestamp="<%= TicketDetails.getDataAccettazione() %>"  timeZone="<%= TicketDetails.getDataAccettazioneTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
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
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Codice Accettazione</dhv:label>
    </td>
    <td>
      <input type="text" name="cause" id="cause" value="<%= toHtmlValue(TicketDetails.getCause()) %>" size="20" maxlength="256" />
    </td>
  </tr>
  
  
  
 <!-- modifiche aggiunto da d.dauria  -->
 
 <!--  alimenti di origine  animale -->
 <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti di origine animale</dhv:label>
    </td>
    <td>    
    <table>
    <tr>
     <td>
     <% if( TicketDetails.getAlimentiOrigineAnimale() == true ){%>
       <input type="checkbox" name="alimentiOrigineAnimale" id="alimentiOrigineAnimale" size="20" maxlength="256"  "checked" id="alimentiOrigineAnimale" onclick="abilitaLookupOrigineAnimale()"/>
       <%} else {%>
       <input type="checkbox" name="alimentiOrigineAnimale" id="alimentiOrigineAnimale" size="20" maxlength="256" onclick="abilitaLookupOrigineAnimale()"/>
       <%} %>
     </td>
      <td id="lookupNonTrasformati">
       <% AlimentiNonTrasformati.setJsEvent("onchange=\"javascript:abilitaSpecie(this.form);\"");%>
      <%= AlimentiNonTrasformati.getHtmlSelect("alimentiOrigineAnimaleNonTrasformati",TicketDetails.getAlimentiOrigineAnimaleNonTrasformati()) %>
      </td> 
     <td id="lookupNonTrasformatiValori">
      <%= AlimentiNonTrasformatiValori.getHtmlSelect("alimentiOrigineAnimaleNonTrasformatiValori",TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()) %>
     </td>
     <td id="lookupTrasformati">
     <%  AlimentiTrasformati.setJsEvent("onchange=\"javascript:disabilitaNonTrasformati(this.form);\"");%>
     <%= AlimentiTrasformati.getHtmlSelect("alimentiOrigineAnimaleTrasformati",TicketDetails.getAlimentiOrigineAnimaleTrasformati()) %>
     </td>
    </tr>
    </table>
    </td> 
  </tr><!-- chiusura tabella interna -->
  
  <!-- alimenti origine vegetale -->
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti di origine vegetale</dhv:label>
    </td>
     <td>    
    <table>
    <tr>
     <td>
     <% if( TicketDetails.getAlimentiOrigineVegetale() == true ){%>
       <input type="checkbox" name="alimentiOrigineVegetale" id="alimentiOrigineVegetale" size="20" maxlength="256" "checked"  onclick="abilitaLookupOrigineVegetale()"/>
       <%} else {%>
              <input type="checkbox" name="alimentiOrigineVegetale" id="alimentiOrigineVegetale" onclick="abilitaLookupOrigineVegetale()" size="20" maxlength="256" />
       
       <%} %>
     </td>
      <td id="lookupVegetale">
      <%= AlimentiVegetali.getHtmlSelect("alimentiOrigineVegetaleValori",TicketDetails.getAlimentiOrigineVegetaleValori()) %>
      </td> 
      </tr>
      </table> <!--  chiusura tabella alimenti vegetali -->
     </td>
     </tr>
  
   <!-- alimenti composti -->
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti composti</dhv:label>
    </td>
    <td>
      <% if( TicketDetails.getAlimentiComposti() == true ){%>
       <input type="checkbox" name="alimentiComposti" id="alimentiComposti" size="20" maxlength="256" onchange="abilitaTestoAlimentoComposto()" "checked"/>
       <input type="text" name="testoAlimentoComposto" id="testoAlimentoComposto"  value="<%= toHtmlValue(TicketDetails.getTestoAlimentoComposto()) %>" size="20" maxlength="256" />
       <%} else {%>
          <input type="checkbox" name="alimentiComposti" id="alimentiComposti" size="20" maxlength="256" onchange="abilitaTestoAlimentoComposto()" />    
          <input type="text" name="testoAlimentoComposto" id="testoAlimentoComposto"  value="<%= toHtmlValue(TicketDetails.getTestoAlimentoComposto()) %>" size="20" maxlength="256" />
     
       <%} %>
     </td>
     </tr>
    
       <!-- nucleo ispettivo -->
   <tr class="containerBody" >
    <td valign="top" class="formLabel">
      <dhv:label name="nucleo.ispettivo">Nucleo Ispettivo</dhv:label>
    </td>
  <td>
     <table> 
     <div id="nucleouno">
      <tr >
        <td>
            <%= TitoloNucleo.getHtmlSelect("nucleoIspettivo",TicketDetails.getNucleoIspettivo()) %>   
        </td>
        <td>
              <input type="text" name="componenteNucleo" value="<%=TicketDetails.getComponenteNucleo()%>" onclick="abilita2()"  size="20" maxlength="256" onchange="abilita2()"/>
        </td>
      </tr>  
     </div>
     <div > 
      <tr id="nucleodue" style="display: none">   
        <td>
            <%= TitoloNucleoDue.getHtmlSelect("nucleoIspettivoDue",TicketDetails.getNucleoIspettivoDue()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoDue" value="<%=TicketDetails.getComponenteNucleoDue()%>" onclick="abilita3()" size="20" maxlength="256" onchange="abilita3()"/>
        </td>
      </tr>
     </div> 
     <div >
      <tr id="nucleotre" style="display: none">  
        <td>
            <%= TitoloNucleoTre.getHtmlSelect("nucleoIspettivoTre",TicketDetails.getNucleoIspettivoTre()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoTre" value="<%=TicketDetails.getComponenteNucleoTre()%>" onclick="abilita4()"  size="20" maxlength="256" onchange="abilita4()"/>
        </td>
      </tr>
      </div>
      <div >
      <tr id="nucleoquattro" style="display: none">  
         <td>
            <%= TitoloNucleoQuattro.getHtmlSelect("nucleoIspettivoQuattro",TicketDetails.getNucleoIspettivoQuattro()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoQuattro" value="<%=TicketDetails.getComponenteNucleoQuattro()%>" onclick="abilita5()" size="20" maxlength="256" onchange="abilita5()"/>
        </td>
      </tr>
      </div>
     <div > 
      <tr id="nucleocinque" style="display: none">  
         <td>
            <%= TitoloNucleoCinque.getHtmlSelect("nucleoIspettivoCinque",TicketDetails.getNucleoIspettivoCinque()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoCinque" value="<%=TicketDetails.getComponenteNucleoCinque()%>" onclick="abilita6()" size="20" maxlength="256" onchange="abilita6()"/>
        </td>
      </tr>
     </div>
     <div > 
     <tr id="nucleosei" style="display: none">  
         <td>
            <%= TitoloNucleoSei.getHtmlSelect("nucleoIspettivoSei",TicketDetails.getNucleoIspettivoSei()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoSei" value="<%=TicketDetails.getComponenteNucleoSei()%>" onclick="abilita7()"  size="20" maxlength="256" onchange="abilita7()"/>
        </td>
      </tr>
      </div>
      <div  >
      <tr id="nucleosette" style="display: none">  
         <td>
            <%= TitoloNucleoSette.getHtmlSelect("nucleoIspettivoSette",TicketDetails.getNucleoIspettivoSette()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoSette"  value="<%=TicketDetails.getComponenteNucleoSette()%>" onclick="abilita8()" size="20" maxlength="256" onchange="abilita8()"/>
        </td>
      </tr>
      </div>
      <div >
      <tr id="nucleootto"  style="display: none">  
         <td>
            <%= TitoloNucleoOtto.getHtmlSelect("nucleoIspettivoOtto",TicketDetails.getNucleoIspettivoOtto()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoOtto"  value="<%=TicketDetails.getComponenteNucleoOtto()%>" size="20" maxlength="256" onclick="abilita9()" onchange="abilita9()"/>
        </td>
      </tr>
      </div>
      <div >
      <tr id="nucleonove" style="display: none">  
         <td>
            <%= TitoloNucleoNove.getHtmlSelect("nucleoIspettivoNove",TicketDetails.getNucleoIspettivoNove()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoNove" value="<%=TicketDetails.getComponenteNucleoNove()%>"  size="20" onclick="abilita10()" maxlength="256" onchange="abilita10()"/>
        </td>
      </tr>
      </div>
      <div >
      <tr id="nucleodieci" style="display:none">  
         <td>
            <%= TitoloNucleoDieci.getHtmlSelect("nucleoIspettivoDieci",TicketDetails.getNucleoIspettivoDieci()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoDieci"  value="<%=TicketDetails.getComponenteNucleoDieci()%>" size="20" maxlength="256" />
        </td>
      </tr>
      </div>
      
     </table>  
     
      
    </td>
   </tr> 
     
   </table> <!--  chiusura tabella generale -->
   </br>
   

   
   
   
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Risultato Esito Campione</dhv:label></strong>
    </th>
	</tr>
   <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="">Data</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="details" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"  showTimeZone="false" />
        <%= showAttribute(request, "estimatedResolutionDateError") %>
      </td>
    </tr>
  <dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="esitoCampione1" id="esitoCampione1" nowrap class="formLabel">
        <dhv:label name="">Esito</dhv:label>
      </td>
    <td>
      <%= EsitoCampione.getHtmlSelect("EsitoCampione",TicketDetails.getEsitoCampione()) %>
      <input type="hidden" name="esitoCampione" value="<%=TicketDetails.getEsitoCampione()%>" >
    </td>
  </tr>
</dhv:include>
<tr>
      <td name="punteggio" id="punteggio" nowrap class="formLabel">
        <dhv:label name="">Punteggio</dhv:label>
      </td>
    <td>
      <input type="text" name="punteggio" id="punteggio" size="20" value="<%= TicketDetails.getPunteggio() %>" maxlength="256" />
    </td>
  </tr>
  
  
   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importox">Follow Up positività</dhv:label>
    	</td>
     <td>    
       
        <table>
         <tr>
           <td>
              <% ConseguenzePositivita.setJsEvent("onchange=\"javascript:abilitaNote(this.form);\"");%>
             <%= ConseguenzePositivita.getHtmlSelect("conseguenzePositivita",TicketDetails.getConseguenzePositivita()) %>
           </td>
           <td id="note_etichetta">Note per altro </td>
           <td id="note"><input type="text" name="noteEsito" id="noteEsito" value="<%= toHtmlValue(TicketDetails.getNoteEsito()) %>" size="60" maxlength="256" /></td>
         </tr>
         </table>
      </td>
     </tr>      
     
     
      <!--responsabilità positività -->
    <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importox">Responsabilità positività</dhv:label>
    	</td>
    	 <td><%= ResponsabilitaPositivita.getHtmlSelect("responsabilitaPositivita",TicketDetails.getResponsabilitaPositivita()) %></td>
    </tr> 
    
    <!-- allerta -->
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Accensione Sistema Allarme rapido</dhv:label>
    </td>
    <td>
     <% if( TicketDetails.getAllerta() == true ){%>
       <input type="checkbox" name="allerta" id="allerta"  size="20" maxlength="256" checked onchange="abilitaCheckAllerta()" />
       <%}else {%>
         <input type="checkbox" name="allerta" id="allerta" size="20" maxlength="256" onchange="abilitaCheckAllerta()" />
        <%} %> 
     </td>
  </tr>
  
  
<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Ulteriori Note</dhv:label>
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
    

  </table>
        &nbsp;<br>
   <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
        <%--dhv:permission name="impiantiMacellazione-impiantiMacellazione-campioni-edit">
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='AccountCampioni.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission--%>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='ImpiantiMacellazione.do?command=ViewCampioni&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='AccountCampioni.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
        <dhv:permission name="impiantiMacellazione-impiantiMacellazione-campioni-edit">
          <input type="submit" value="<dhv:label name="global.button.update" >Update</dhv:label>" onclick="return controllo_data_esito(document.details);return checkEsitoForm(document.details);"  />
        </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='ImpiantiMacellazione.do?command=ViewCampioni&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='AccountCampioni.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
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
