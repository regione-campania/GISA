<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<form name="addticket" action="AccountNonConformita.do?command=Insert&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >

<a href="Accounts.do?command=ViewNonConformita&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="nonconformita">NonConformita Amministrative</dhv:label></a> > 
<dhv:label name="nonconformita.aggiungi">Aggiungi Non Conformità Rilevate</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewNonConformita'">
<br>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
<%-- include basic troubleticket add form --%>

<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
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
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.nonconformita.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformitaAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformitaPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>

<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript">

function checkImporto(form)
{
if (form.cause.value == "") {
      alert("- Controllare che il campo 'importo' sia stato popolato ");
    }
}

  function updateCategoryList() {
    var orgId = document.forms['addticket'].orgId.value;
    var url = 'TroubleTicketsNonConformita.do?command=CategoryJSList&form=addticket&reset=true&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
<dhv:include name="ticket.catCode" none="false">
  function updateSubList1() {
    var orgId = document.forms['addticket'].orgId.value;
    if(orgId != '-1'){
      var sel = document.forms['addticket'].elements['catCode'];
      var value = sel.options[sel.selectedIndex].value;
      var url = "TroubleTicketsNonConformita.do?command=CategoryJSList&form=addticket&catCode=" + escape(value)+'&orgId='+orgId;
      window.frames['server_commands'].location.href=url;
    } else {
      var sel = document.forms['addticket'].elements['catCode'];
      sel.options.selectedIndex = 0;
      alert(label("select.account.first",'You have to select an Account first'));
      return;
    }
  }
</dhv:include>
<dhv:include name="ticket.subCat1" none="true">
  function updateSubList2() {
    var orgId = document.forms['addticket'].orgId.value;
    var sel = document.forms['addticket'].elements['subCat1'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsNonConformita.do?command=CategoryJSList&form=addticket&subCat1=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  function updateSubList3() {
    var orgId = document.forms['addticket'].orgId.value;
    var sel = document.forms['addticket'].elements['subCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsNonConformita.do?command=CategoryJSList&form=addticket&subCat2=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  function updateSubList4() {
    var orgId = document.forms['addticket'].orgId.value;
    var sel = document.forms['addticket'].elements['subCat3'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsNonConformita.do?command=CategoryJSList&form=addticket&subCat3=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
  function updateUserList() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var url = "TroubleTicketsNonConformita.do?command=DepartmentJSList&form=addticket&dept=Assigned&orgSiteId="+ orgSite +"&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateResolvedByUserList() {
    var sel = document.forms['addticket'].elements['resolvedByDeptCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var url = "TroubleTicketsNonConformita.do?command=DepartmentJSList&form=addticket&dept=Resolved&orgSiteId="+ orgSite + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  
  function updateAllUserLists() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var sel2 = document.forms['addticket'].elements['resolvedByDeptCode'];
    var value2 = sel2.options[sel2.selectedIndex].value;
    var url = "TroubleTicketsNonConformita.do?command=DepartmentJSList&form=addticket&orgSiteId="+ orgSite +"&populateResourceAssigned=true&populateResolvedBy=true&resourceAssignedDepartmentCode=" + escape(value)+'&resolveByDepartmentCode='+ escape(value2);
    window.frames['server_commands'].location.href=url;
  }

  function updateLists() {
  <dhv:include name="ticket.contact" none="true">
    var orgWidget = document.forms['addticket'].elements['orgId'];
    var orgValue = document.forms['addticket'].orgId.value;

    //var resourceAssignedDepartmentWidget = document.forms['addticket'].elements['departmentCode'];
    //var resourceAssignedDepartmentValue = resourceAssignedDepartmentWidget.options[resourceAssignedDepartmentWidget.selectedIndex].value;

    //var resolvedByDepartmentWidget = document.forms['addticket'].elements['resolvedByDeptCode'];
    //var resolvedByDepartmentValue = resolvedByDepartmentWidget.options[resolvedByDepartmentWidget.selectedIndex].value;

    var params = "&orgId=" + escape(orgValue);
    //params = params + "&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(resourceAssignedDepartmentValue);
    //params = params + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(resolvedByDepartmentValue);
    params = params + "&populateDefects=true";

    var url = "TroubleTicketsNonConformita.do?command=OrganizationJSList" + params; 
    window.frames['server_commands'].location.href=url;
  </dhv:include>
  }
  function checkForm(form){
    formTest = true;
    message = "";
   // if (form.cause.value == "" && form.NonConformitaAmministrative.value!=-1) {
     // message += "- Controllare che il campo 'importo' sia stato popolato \n";
      //formTest = false;
    //}
    <dhv:include name="ticket.contact" none="true">
    if (form.siteId.value == "-1") {
      message += label("check.nonconformita.richiedente.selezionato","- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
      formTest = false;
    }
    </dhv:include>
    <dhv:include name="ticket.contact" none="true">
    if (form.orgId.value == "-1") {
      message += label("check.nonconformita.richiedente.selezionato","- Controllare che l'impresa sia stata selezionata\r\n");
      formTest = false;
    }
    </dhv:include>
    if (form.assignedDate.value == "") {
      message += label("check.nonconformita.data_richiesta.selezionato","- Controllare che il campo \"Data Non Conformita\" sia stato popolato\r\n");
      formTest = false;
    }
  //  if ((form.descrizione1.value == "" && form.Provvedimenti.selectedIndex == 9 )|| (form.descrizione2.value == "" && form.NonConformitaAmministrative.selectedIndex == 9 ) || ( form.descrizione3.value == "" && form.NonConformitaPenali.selectedIndex == 6 )) {
    //  message += label("check.nonconformita.data_richiesta.selezionato","- Controllare che il campo \"Descrizione\" sia stato popolato\r\n");
      //formTest = false;
    //}
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
  //used when a new contact is added
  function insertOption(text,value,optionListId){
   var obj = document.forms['addticket'].contactId;
   insertIndex= obj.options.length;
   obj.options[insertIndex] = new Option(text,value);
   obj.selectedIndex = insertIndex;
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
    //when the content of any of the select items changes, do something here
    //reset the sc and asset
    if (divName == 'changeaccount') {
      <dhv:include name="ticket.contact" none="false">
      if(document.forms['addticket'].orgId.value != '-1'){
        updateLists();
      }
      </dhv:include>
      <dhv:include name="ticket.contractNumber" none="false">
      changeDivContent('addServiceContract',label('none.selected','None Selected'));
      resetNumericFieldValue('contractId');
      </dhv:include>
      <dhv:include name="ticket.contractNumber" none="false">
      changeDivContent('addAsset',label('none.selected','None Selected'));
      resetNumericFieldValue('assetId');
      </dhv:include>
      <%-- dhv:include name="ticket.laborCategory" none="false">
      changeDivContent('addLaborCategory',label('none.selected','None Selected'));
      resetNumericFieldValue('productId');
      </dhv:include --%>
    }
  }

  function addNewContact(){
    <dhv:permission name="accounts-accounts-contacts-add">
      var acctPermission = true;
    </dhv:permission>
    <dhv:permission name="accounts-accounts-contacts-add" none="true">
      var acctPermission = false;
    </dhv:permission>
    <dhv:permission name="contacts-internal_contacts-add">
      var empPermission = true;
    </dhv:permission>
    <dhv:permission name="contacts-internal_contacts-add" none="true">
      var empPermission = false;
    </dhv:permission>
    var orgId = document.forms['addticket'].orgId.value;
    if(orgId == -1){
      alert(label("select.account.first",'You have to select an Account first'));
      return;
    }
    if(orgId == '0'){
      if (empPermission) {
        popURL('CompanyDirectory.do?command=Prepare&container=false&popup=true&source=troubletickets', 'New_Employee','600','550','yes','yes');
      } else {
        alert(label('no.permission.addemployees','You do not have permission to add employees'));
        return;
      }
    }else{
      if (acctPermission) {
        popURL('Contacts.do?command=Prepare&container=false&popup=true&source=troubletickets&hiddensource=troubletickets&orgId=' + document.forms['addticket'].orgId.value, 'New_Contact','600','550','yes','yes');
      } else {
        alert(label("no.permission.addcontacts","You do not have permission to add contacts"));
        return;
      }
    }
  }

 function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
 }

 function setAssignedDate(){
  resetAssignedDate();
  if (document.forms['addticket'].assignedTo.value > 0){
    document.forms['addticket'].assignedDate.value = document.forms['addticket'].currentDate.value;
  }
 }

 function resetAssignedDate(){
    document.forms['addticket'].assignedDate.value = '';
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
    var orgId = document.forms['addticket'].orgId.value;
    var siteId = document.forms['addticket'].orgSiteId.value;
    if (orgId != '-1') {
      popUserGroupsListSingle('userGroupId','changeUserGroup', '&userId=<%= User.getUserRecord().getId() %>&siteId='+siteId);
    } else {
      alert(label("select.account.first",'You have to select an Account first'));
      return;
    }
  }
  
  function popKbEntries() {
    var siteId = document.forms['addticket'].orgSiteId.value;
    var form = document.forms['addticket'];
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
  <dhv:include name="ticket.subCat3" none="true">
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
 		
 		document.addticket.Provvedimenti.selectedIndex=0;
 		document.addticket.NonConformitaAmministrative.selectedIndex=0;
 		document.addticket.NonConformitaPenali.selectedIndex=0;
 		
  }
  
  function selectCarattere(str, n, m, x){
  
 		elm1 = document.getElementById("dat"+n);
 		elm2 = document.getElementById("dat"+m);
 		
 		
 		if(str == "Provvedimenti"){
 			car = document.addticket.Provvedimenti.value;
 		}
 		if(str == "NonConformitaAmministrative"){
 			car = document.addticket.NonConformitaAmministrative.value;
 		}
 		if(str == "NonConformitaPenali"){
 			car = document.addticket.NonConformitaPenali.value;
 		}
 		
 		if(car == 9 || (car == 6 && str == "NonConformitaPenali")){
 			elm1.style.visibility = "visible";
 			elm2.style.visibility = "visible";
 			
 		}
 		else {
 			elm1.style.visibility = "hidden";
 			elm2.style.visibility = "hidden";
 			
 			if(x == 1){
 			document.forms['addticket'].descrizione1.value="";
 			}
 			if(x == 2){
 			document.forms['addticket'].descrizione2.value="";
 			}
 			if(x == 3){
 			document.forms['addticket'].descrizione3.value="";
 			}
 		}
 	  }
</script>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Non Conformità Rilevate</dhv:label></strong>
    </th>
	</tr>
	
  

<%UserBean user=(UserBean)session.getAttribute("User");
String aslCU=(String)request.getAttribute("aslCU");
int aslcu=Integer.parseInt(aslCU);

	if(user.getSiteId()==-1){	

%>  
<tr>
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
       <%= SiteIdList.getSelectedValue(aslcu)%>
        <input type="hidden" name="siteId" value="<%=aslCU%>" >
      </td>
    </tr>



<%}else{ %>
   
   
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
       <%= SiteIdList.getSelectedValue(user.getSiteId())%>
          <input type="hidden" name="siteId" value="<%=user.getSiteId()%>" >
      
      </td>
    </tr>

<%} %>
	
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
  <tr>
    <td class="formLabel">
      <dhv:label name="nonconformita.richiedente">Impresa</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
      </td>
    
  </tr>
  <%--<tr>
    <td class="formLabel">
      <dhv:label name="nonconformita.tipo_animale">Tipo Animale</dhv:label>
    </td>
    <td>
      <%= SourceList.getHtmlSelect("sourceCode",  TicketDetails.getSourceCode()) %>
    </td>
	</tr>--%>
	
  <%-- dhv:include name="ticket.contact" none="true">
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
    </td>
    <td valign="center">
	<% if (TicketDetails == null || TicketDetails.getOrgId() == -1 || ContactList.size() == 0) { %>
      <%= ContactList.getEmptyHtmlSelect(systemStatus, "contactId") %>
	<%} else {%>
      <%= ContactList.getHtmlSelect("contactId", TicketDetails.getContactId() ) %>
	<%}%>
      <font color="red">*</font><%= showAttribute(request, "contactIdError") %>
      [<a href="javascript:addNewContact();"><dhv:label name="account.createNewContact">Create New Contact</dhv:label></a>]
    </td>
	</tr>
  </dhv:include --%>
  <%-- dhv:include name="ticket.contractNumber" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="tickects.ServiceContractNumber">Service Contract Number</dhv:label>
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addServiceContract">
            <% if(TicketDetails.getContractId() != -1) {%>
              <%= toHtml(TicketDetails.getServiceContractNumber()) %>
            <%} else {%>
              <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
            <%}%>
          </div>
        </td>
        <td>
          <input type="hidden" name="contractId" id="contractId" value="<%=  TicketDetails.getContractId() %>">
          <input type="hidden" name="serviceContractNumber" id="serviceContractNumber" value="<%= TicketDetails.getServiceContractNumber() %>">
          &nbsp;
          <%= showAttribute(request, "contractIdError") %>
          [<a href="javascript:popServiceContractListSingle('contractId','addServiceContract', 'filters=all|my|disabled');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          &nbsp [<a href="javascript:changeDivContent('addServiceContract',label('none.selected','None Selected'));javascript:resetNumericFieldValue('contractId');javascript:changeDivContent('addAsset',label('none.selected','None Selected'));javascript:resetNumericFieldValue('assetId');"><dhv:label name="button.clear">Clear</dhv:label></a>]
        </td>
      </tr>
    </table>
   </td>
  </tr>
  </dhv:include>
  <dhv:include name="ticket.asset" none="true">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.asset">Asset</dhv:label>
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addAsset">
            <% if(TicketDetails.getAssetId() != -1) {%>
              <%= toHtml(TicketDetails.getAssetSerialNumber()) %>
            <%} else {%>
              <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
            <%}%>
          </div>
        </td>
        <td>
          <input type="hidden" name="assetId" id="assetId" value="<%=  TicketDetails.getAssetId() %>">
          <input type="hidden" name="assetSerialNumber" id="assetSerialNumber" value="<%=  TicketDetails.getAssetSerialNumber() %>">
          &nbsp;
          <%= showAttribute(request, "assetIdError") %>
          [<a href="javascript:popAssetListSingle('assetId','addAsset', 'filters=allassets|undercontract','contractId','addServiceContract');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          &nbsp [<a href="javascript:changeDivContent('addAsset',label('none.selected','None Selected'));javascript:resetNumericFieldValue('assetId');"><dhv:label name="button.clear">Clear</dhv:label></a>]
        </td>
      </tr>
    </table>
   </td>
  </tr>
  </dhv:include --%>
  <%-- tr class="containerBody" style="display: none">
    <td class="formLabel">
      <dhv:label name="account.laborCategory">Labor Category</dhv:label>
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="addLaborCategory">
            <% if(TicketDetails.getProductId() != -1) {%>
              <%= toHtml(TicketDetails.getProductName()) %>
            <%} else {%>
              <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
            <%}%>
          </div>
        </td>
        <td>
          <input type="hidden" name="productId" id="productId" value="<%=  TicketDetails.getProductId() %>">
  		  <input type="hidden" name="productSku" id="productSku" value="<%=  TicketDetails.getProductSku() %>">
          &nbsp;
          <%= showAttribute(request, "productIdError") %>
          [<a href="javascript:popProductListSingle('productId','addLaborCategory', 'filters=all|my|disabled');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          &nbsp [<a href="javascript:changeDivContent('addLaborCategory',label('none.selected','None Selected'));javascript:resetNumericFieldValue('productId');"><dhv:label name="button.clear">Clear</dhv:label></a>]
        </td>
      </tr>
    </table>
   </td>
  </tr --%>
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>
  
  <tr>
    <td class="formLabel">
      <dhv:label name="nonconformita.richiedente">Numero C.U</dhv:label>
    </td>
   
     
      <td>
        <input type="text" name="numerocu"  />
      </td>
    
  </tr>
  
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Numero Verbale</dhv:label>
    </td>
    <td>
    <%if(TicketDetails.getLocation() != "" && TicketDetails.getLocation() != null){ %>
      <input type="text" name="location" id="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="20" maxlength="256" />
    <%}else{%>
          <input type="text" name="location" id="location" value="" size="20" maxlength="256" />
    <%} %>
    </td>
  </tr>
  
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
      <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>
<!--   <tr>
  <td nowrap class="formLabel">
      <dhv:label name="">Identificativo</dhv:label>
    </td>
  <td >
      <input type="radio" id="tipoD" name="pippo" value="Numero" checked>
      Numero Verbale
      <input type="radio" id="tipoD2" name="pippo" value="Protocollo">
      Protocollo Denuncia     
      <input type="hidden" name="orgType" value="" />
      <%--input type="hidden" name="comment" value="<%= toHtmlValue(TicketDetails.getComment()) %>" /--%>
	  <input type="text" name="tipo_richiesta" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/>
      <input type="hidden" name="check"/>
    </td>
  </tr> -->
  <%--
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="nonconformita.data_ispezione">Data Macellazione</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"  showTimeZone="false" />
      <%= showAttribute(request, "estimatedResolutionDateError") %>
    </td>
  </tr>--%>
<dhv:include name="ticket.location" none="true">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
    <dhv:label name="">Tipo di Non Conformita Rilevata</dhv:label>
    </td>
    <td>
      <input type="text" name="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="50" maxlength="256" />
    </td>
  </tr>
</dhv:include>
  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformita.note">Note</dhv:label>
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
	
	
	  <!-- nucleo ispettivo 
   <tr class="containerBody" >
    <td valign="top" class="formLabel">
      <dhv:label name="nucleo.ispettivo">Nucleo Ispettivo</dhv:label>
    </td>
  <td>
     <table> 
     <div id="nucleouno">
      <tr >
        <td>
            <%//= TitoloNucleo.getHtmlSelect("nucleoIspettivo",TicketDetails.getNucleoIspettivo()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleo"  size="20" maxlength="256" onclick="abilita2()"/>
        </td>
      </tr>  
     </div>
     <div > 
      <tr id="nucleodue" style="display: none">   
        <td>
            <%//= TitoloNucleoDue.getHtmlSelect("nucleoIspettivoDue",TicketDetails.getNucleoIspettivoDue()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoDue"  size="20" maxlength="256" onclick="abilita3()"/>
        </td>
      </tr>
     </div> 
     <div >
      <tr id="nucleotre" style="display: none">  
        <td>
            <%//= TitoloNucleoTre.getHtmlSelect("nucleoIspettivoTre",TicketDetails.getNucleoIspettivoTre()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoTre"  size="20" maxlength="256" onclick="abilita4()"/>
        </td>
      </tr>
      </div>
      <div >
      <tr id="nucleoquattro" style="display: none">  
         <td>
            <%//= TitoloNucleoQuattro.getHtmlSelect("nucleoIspettivoQuattro",TicketDetails.getNucleoIspettivoQuattro()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoQuattro"  size="20" maxlength="256" onclick="abilita5()"/>
        </td>
      </tr>
      </div>
     <div > 
      <tr id="nucleocinque" style="display: none">  
         <td>
            <%//= TitoloNucleoCinque.getHtmlSelect("nucleoIspettivoCinque",TicketDetails.getNucleoIspettivoCinque()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoCinque"  size="20" maxlength="256" onclick="abilita6()"/>
        </td>
      </tr>
     </div>
     <div > 
     <tr id="nucleosei" style="display: none">  
         <td>
            <%//= TitoloNucleoSei.getHtmlSelect("nucleoIspettivoSei",TicketDetails.getNucleoIspettivoSei()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoSei"  size="20" maxlength="256" onclick="abilita7()"/>
        </td>
      </tr>
      </div>
      <div  >
      <tr id="nucleosette" style="display: none">  
         <td>
            <%//= TitoloNucleoSette.getHtmlSelect("nucleoIspettivoSette",TicketDetails.getNucleoIspettivoSette()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoSette"  size="20" maxlength="256" onclick="abilita8()"/>
        </td>
      </tr>
      </div>
      <div >
      <tr id="nucleootto"  style="display: none">  
         <td>
            <%//= TitoloNucleoOtto.getHtmlSelect("nucleoIspettivoOtto",TicketDetails.getNucleoIspettivoOtto()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoOtto"  size="20" maxlength="256" onclick="abilita9()"/>
        </td>
      </tr>
      </div>
      <div >
      <tr id="nucleonove" style="display: none">  
         <td>
            <%//= TitoloNucleoNove.getHtmlSelect("nucleoIspettivoNove",TicketDetails.getNucleoIspettivoNove()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoNove"  size="20" maxlength="256" onclick="abilita10()"/>
        </td>
      </tr>
      </div>
      <div >
      <tr id="nucleodieci" style="display:none">  
         <td>
            <%//= TitoloNucleoDieci.getHtmlSelect("nucleoIspettivoDieci",TicketDetails.getNucleoIspettivoDieci()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoDieci"  size="20" maxlength="256" />
        </td>
      </tr>
      </div>
      
     </table>  
     
      
    </td>
   </tr> 
    --> 
	
	  <dhv:include name="organization.source" none="true">
   <tr>
      <td name="provvedimento1" id="provvedimento1" nowrap class="formLabel">
        <dhv:label name="">Follow Up delle Non Conformità</dhv:label>
      </td>
    <td>
   <table border=0>
      <tr>
      <td >
      <%	Provvedimenti.setJsEvent("onChange=\"javascript:selectCarattere('Provvedimenti', '1', 2, '1');\"");
        %>
         <%= Provvedimenti.getHtmlSelect("Provvedimenti",TicketDetails.getProvvedimenti()) %>
         
       
    		</td>
          	<td style="visibility: hidden;" id="dat1">
        		Descrizione<font color="red">*</font>
        	</td>
        	<td style="visibility: hidden;" id="dat2">
           		<input type="text" name="descrizione1" size="50">
          	</td>
       </tr>
       </table>
    </td>
  </tr>
</dhv:include> 
	
	
  <!-- <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformita.importos">Importo da Pagare (euro)</dhv:label>
    </td>
    <td>
      <input type="text" name="cause" value="<%//= toHtmlValue(TicketDetails.getCause()) %>" size="20" maxlength="256" /> <font color="red">*</font><font color="red">solo per nonconformita</font>
    </td>
  </tr>-->
 <!--  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformita.azioni">Ulteriori Azioni</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="solution" cols="55" rows="8"><%= toString(TicketDetails.getSolution()) %></textarea>
          </td>
          <td valign="top">
            <%//= showAttribute(request, "problemError") %>
          </td>
        </tr> 
      </table>
    </td>
  </tr>-->
  
     <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformitaa.azioni">Punteggio</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <input type="text" name="punteggio">
          </td>
         
        </tr>
        
        
        
    </table>
    </td>
    </tr>
    
    <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformita.azioni">Ulteriori Note</dhv:label>
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
           </tr>
  
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewNonConformita'">
</form>
