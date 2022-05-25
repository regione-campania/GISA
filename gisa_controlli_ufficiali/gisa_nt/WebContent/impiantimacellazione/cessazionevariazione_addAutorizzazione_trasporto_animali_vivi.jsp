<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.impiantimacellazione.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.impiantimacellazione.base.Organization" scope="request"/>
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
  function initializeClassification() {
  <% if (OrgDetails.getIsIndividual()) { %>
      indSelected = 1;
      updateFormElements(1);
  <%} else {%>
      orgSelected = 1;
      updateFormElements(0);
  <%}%>
  }
  function resetFormElements() {
    if (document.getElementById) {
      elm1 = document.getElementById("nameFirst1");
      elm2 = document.getElementById("nameMiddle1");
      elm3 = document.getElementById("nameLast1");
      elm4 = document.getElementById("orgname1");
      elm5 = document.getElementById("ticker1");
      elm6 = document.getElementById("accountSize1");
      elm7 = document.getElementById("listSalutation1");
      elm8 = document.getElementById("primarycontact1");
      if (elm1) {
        elm1.style.color = "#000000";
        document.addticket.nameFirst.style.background = "#ffffff";
        document.addticket.nameFirst.disabled = false;
      }
      if (elm2) {
        elm2.style.color = "#000000";
        document.addticket.nameMiddle.style.background = "#ffffff";
        document.addticket.nameMiddle.disabled = false;
      }
      if (elm3) {
        elm3.style.color = "#000000";
        document.addticket.nameLast.style.background = "#ffffff";
        document.addticket.nameLast.disabled = false;
      }
      if (elm4) {
        elm4.style.color = "#000000";
        document.addticket.name.style.background = "#ffffff";
        document.addticket.name.disabled = false;
      }
      if (elm5) {
        elm5.style.color = "#000000";
        document.addticket.ticker.style.background = "#ffffff";
        document.addticket.ticker.disabled = false;
      }
      if (elm6) {
        elm6.style.color = "#000000";
        document.addticket.accountSize.style.background = "#ffffff";
        document.addticket.accountSize.disabled = false;
      }
      if (elm7) {
        elm7.style.color = "#000000";
        document.addticket.listSalutation.style.background = "#ffffff";
        document.addticket.listSalutation.disabled = false;
      }
      if (elm8) {
        elm8.style.color = "#000000";
        document.addticket.primaryContactId.style.background = "#ffffff";
        document.addticket.primaryContactId.disabled = false;
      }
    }
  }
  function updateFormElements(index) {
    if (document.getElementById) {
      <dhv:include name="impiantiMacellazione-firstname" none="true">
        elm1 = document.getElementById("nameFirst1");
      </dhv:include>
      <dhv:include name="impiantiMacellazione-middlename" none="true">
        elm2 = document.getElementById("nameMiddle1");
      </dhv:include>
      <dhv:include name="impiantiMacellazione-lastname" none="true">
        elm3 = document.getElementById("nameLast1");
      </dhv:include>
      elm4 = document.getElementById("orgname1");
      elm5 = document.getElementById("ticker1");
      elm6 = document.getElementById("accountSize1");
      elm7 = document.getElementById("listSalutation1");
      elm8 = document.getElementById("primarycontact1");
      if (index == 1) {
        indSelected = 1;
        orgSelected = 0;        
        resetFormElements();
        if (elm4) {
          elm4.style.color="#cccccc";
          document.addticket.name.style.background = "#cccccc";
          document.addticket.name.value = "";
          document.addticket.name.disabled = true;
        }
        if (elm5) {
          elm5.style.color="#cccccc";
          document.addticket.ticker.style.background = "#cccccc";
          document.addticket.ticker.value = "";
          document.addticket.ticker.disabled = true;
        }
        if (elm6) {
          elm6.style.color="#cccccc";
          document.addticket.accountSize.style.background = "#cccccc";
          document.addticket.accountSize.value = "";
          document.addticket.accountSize.disabled = true;
        }
      } else {
        indSelected = 0;
        orgSelected = 1;
        resetFormElements();
        if (elm1) {
          elm1.style.color = "#cccccc";
          document.addticket.nameFirst.style.background = "#cccccc";
          document.addticket.nameFirst.value = "";
          document.addticket.nameFirst.disabled = true;
        }
        if (elm2) {
          elm2.style.color = "#cccccc";  
          document.addticket.nameMiddle.style.background = "#cccccc";
          document.addticket.nameMiddle.value = "";
          document.addticket.nameMiddle.disabled = true;
        }
        if (elm3) {
          elm3.style.color = "#cccccc";      
          document.addticket.nameLast.style.background = "#cccccc";
          document.addticket.nameLast.value = "";
          document.addticket.nameLast.disabled = true;
        }
        if (elm7) {
          elm7.style.color = "#cccccc";
          document.addticket.listSalutation.style.background = "#cccccc";
          document.addticket.listSalutation.value = -1;     
          document.addticket.listSalutation.disabled = true;
        }
        if (elm8) {
          elm8.style.color = "#cccccc";
          document.addticket.primaryContactId.style.background = "#cccccc";
          document.addticket.primaryContactId.selectedIndex = 0;
          document.addticket.primaryContactId.disabled = true;
        }
      }
    }
  }
  //-------------------------------------------------------------------
  // getElementIndex(input_object)
  //   Pass an input object, returns index in form.elements[] for the object
  //   Returns -1 if error
  //-------------------------------------------------------------------
  function getElementIndex(obj) {
    var theform = obj.form;
    for (var i=0; i<theform.elements.length; i++) {
      if (obj.name == theform.elements[i].name) {
        return i;
      }
    }
    return -1;
  }
  // -------------------------------------------------------------------
  // tabNext(input_object)
  //   Pass an form input object. Will focus() the next field in the form
  //   after the passed element.
  //   a) Will not focus to hidden or disabled fields
  //   b) If end of form is reached, it will loop to beginning
  //   c) If it loops through and reaches the original field again without
  //      finding a valid field to focus, it stops
  // -------------------------------------------------------------------
  function tabNext(obj) {
    if (navigator.platform.toUpperCase().indexOf("SUNOS") != -1) {
      obj.blur(); return; // Sun's onFocus() is messed up
      }
    var theform = obj.form;
    var i = getElementIndex(obj);
    var j=i+1;
    if (j >= theform.elements.length) { j=0; }
    if (i == -1) { return; }
    while (j != i) {
      if ((theform.elements[j].type!="hidden") && 
          (theform.elements[j].name != theform.elements[i].name) && 
        (!theform.elements[j].disabled)) {
        theform.elements[j].focus();
        break;
        }
      j++;
      if (j >= theform.elements.length) { j=0; }
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
      if (form.partitaIva && form.partitaIva.value!=""){
   	 //alert(!isNaN(form.address2latitude.value));
   		if ((orgSelected == 1)  ){
   			if (isNaN(form.partitaIva.value)){
    			 message += "- Valore errato per il campo Partita IVA. Si prega di inserire solo cifre\r\n";
    				 formTest = false;
    			}		 
   		}
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
      /*
      if (form.address1latitude && form.address1latitude.value!=""){
      	 //alert(!isNaN(form.address1latitude.value));
      		if ((orgSelected == 1)  ){
      			if (isNaN(form.address1latitude.value) ||  (form.address1latitude.value < 2417159.584320) || (form.address1latitude.value > 4431788.049190)){
       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 2417159.584320 e 4431788.049190  (Sede Legale)\r\n";
       				 formTest = false;
       			}		 
      		}
   	 }   
   	 
   	 if (form.address1longitude && form.address1longitude.value!=""){
      	 //alert(!isNaN(form.address1longitude.value));
      		if ((orgSelected == 1)  ){
      			if (isNaN(form.address1longitude.value) ||  (form.address1longitude.value < 2587487.362260) || (form.address1longitude.value > 4593983.337630)){
       			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2587487.362260 e 4593983.337630  (Sede Legale)\r\n";
       				 formTest = false;
       			}		 
      		}
   	 }  
   	 */    
      
      
      
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
  /*
  <dhv:include name="organization.alert" none="true">
    if ((!checkNullString(form.alertText.value)) && (checkNullString(form.alertDate.value))) { 
      message += label("specify.alert.date", "- Please specify an alert date\r\n");
      formTest = false;
    }
    if ((!checkNullString(form.alertDate.value)) && (checkNullString(form.alertText.value))) { 
      message += label("specify.alert.description", "- Please specify an alert description\r\n");
      formTest = false;
    }
  </dhv:include>
  */
  <dhv:include name="organization.phoneNumbers" none="true">
<%
    for (int i=1; i<=OrgDetails.getPhoneNumberList().size(); i++) {
%>
  <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkPhone(form.phone<%=i%>number.value)) { 
      message += label("check.phone", "- At least one entered phone number is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
<%
    }
    for (int i=1; i<=OrgDetails.getPhoneNumberList().size(); i++) {
%>
      <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if ((checkNullString(form.phone<%= i %>ext.value) && form.phone<%= i %>ext.value != "")) {
        message += label("check.phone.ext","- Please enter a valid phone number extension\r\n");
        formTest = false;
      }
  <%}%>
  </dhv:include>
  <dhv:include name="organization.emailAddresses" none="true">
<%
    for (int i=1; i<=OrgDetails.getEmailAddressList().size(); i++) {
%>
  <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkEmail(form.email<%=i%>address.value)) {
      message += label("check.email", "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
<%
    }
%>
  </dhv:include>
  <dhv:include name="organization.url" none="true">
    if (!checkURL(form.url.value)) { 
      message += label("URL.invalid", "- URL entered is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
  </dhv:include>
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      var test = document.addticket.selectedList;
      if (test != null) {
        selectAllOptions(document.addticket.selectedList);
      }
      if(alertMessage != ""){
        confirmAction(alertMessage);
      }
      return true;
    }
  }
  
  function update(countryObj, stateObj, selectedValue) {
    var country = document.forms['addticket'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=addticket&stateObj=address"+stateObj+"state";
    window.frames['server_commands'].location.href=url;
  }

  function continueUpdateState(stateObj, showText) {
    if(showText == 'true'){
      hideSpan('state1' + stateObj);
      showSpan('state2' + stateObj);
    } else {
      hideSpan('state2' + stateObj);
      showSpan('state1' + stateObj);
    }
  }

function selectCarattere(){
  
 		elm1 = document.getElementById("data1");
 		elm2 = document.getElementById("data2");
 		elm3 = document.getElementById("data3");
 		elm4 = document.getElementById("data4");
 		elm5 = document.getElementById("cessazione");
 		car = document.addticket.source.value;
 	
 		if(car == 1){
 			elm1.style.visibility = "visible";
 			elm2.style.visibility = "visible";
 			elm3.style.visibility = "visible";
 			elm4.style.visibility = "visible";
 			elm5.style.visibility = "visible";
 		}
 		else {
 			elm1.style.visibility = "hidden";
 			elm2.style.visibility = "hidden";
 			elm3.style.visibility = "hidden";
 			elm4.style.visibility = "hidden";
 			elm5.style.visibility = "hidden";
 		}
 	
  }
  function updateOwnerList(){
    var sel = document.forms['addticket'].elements['siteId'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "ImpiantiMacellazione.do?command=OwnerJSList&form=addticket&widget=owner&allowBlank=false&siteId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
</script>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.cessazionevariazione.base.*" %>
<%@ include file="../initPage.jsp" %>

<form name="addticket" action="AccountCessazionevariazione.do?command=InsertTicket&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="ImpiantiMacellazione.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
  <a href="ImpiantiMacellazione.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="ImpiantiMacellazione.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <a href="ImpiantiMacellazione.do?command=ViewCessazionevariazione&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Cessazioni/Variazioni</dhv:label></a> >
  <dhv:label name="cessazionevariazione.aggiungi">Variazione</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="impiantiMacellazione" selected="cessazionevariazione" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='ImpiantiMacellazione.do?command=ViewCessazionevariazione&orgId=<%=OrgDetails.getOrgId()%>'">
<br>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
<%-- include basic troubleticket add form --%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.cessazionevariazione.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="DestinatarioCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.impiantimacellazione.base.OrganizationList" scope="request"/>
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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript">
  function updateCategoryList() {
    var orgId = document.forms['addticket'].orgId.value;
    var url = 'TroubleTicketsCessazionevariazione.do?command=CategoryJSList&form=addticket&reset=true&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
<dhv:include name="ticket.catCode" none="false">
  function updateSubList1() {
    var orgId = document.forms['addticket'].orgId.value;
    if(orgId != '-1'){
      var sel = document.forms['addticket'].elements['catCode'];
      var value = sel.options[sel.selectedIndex].value;
      var url = "TroubleTicketsCessazionevariazione.do?command=CategoryJSList&form=addticket&catCode=" + escape(value)+'&orgId='+orgId;
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
    var url = "TroubleTicketsCessazionevariazione.do?command=CategoryJSList&form=addticket&subCat1=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  function updateSubList3() {
    var orgId = document.forms['addticket'].orgId.value;
    var sel = document.forms['addticket'].elements['subCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsCessazionevariazione.do?command=CategoryJSList&form=addticket&subCat2=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  function updateSubList4() {
    var orgId = document.forms['addticket'].orgId.value;
    var sel = document.forms['addticket'].elements['subCat3'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsCessazionevariazione.do?command=CategoryJSList&form=addticket&subCat3=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
  function updateUserList() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var url = "TroubleTicketsCessazionevariazione.do?command=DepartmentJSList&form=addticket&dept=Assigned&orgSiteId="+ orgSite +"&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  function updateResolvedByUserList() {
    var sel = document.forms['addticket'].elements['resolvedByDeptCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var url = "TroubleTicketsCessazionevariazione.do?command=DepartmentJSList&form=addticket&dept=Resolved&orgSiteId="+ orgSite + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  
  function updateAllUserLists() {
    var sel = document.forms['addticket'].elements['departmentCode'];
    var value = sel.options[sel.selectedIndex].value;
    var orgSite = document.forms['addticket'].elements['orgSiteId'].value;
    var sel2 = document.forms['addticket'].elements['resolvedByDeptCode'];
    var value2 = sel2.options[sel2.selectedIndex].value;
    var url = "TroubleTicketsCessazionevariazione.do?command=DepartmentJSList&form=addticket&orgSiteId="+ orgSite +"&populateResourceAssigned=true&populateResolvedBy=true&resourceAssignedDepartmentCode=" + escape(value)+'&resolveByDepartmentCode='+ escape(value2);
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

    var url = "TroubleTicketsCessazionevariazione.do?command=OrganizationJSList" + params; 
    window.frames['server_commands'].location.href=url;
  </dhv:include>
  }
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
    <dhv:permission name="impiantiMacellazione-impiantiMacellazione-contacts-add">
      var acctPermission = true;
    </dhv:permission>
    <dhv:permission name="impiantiMacellazione-impiantiMacellazione-contacts-add" none="true">
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
</script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Variazione</dhv:label></strong>
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
 <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="sanzionia.data_richiesta">Data Variazione</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
      <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>
  <input type="hidden" name="tipo_richiesta" value="autorizzazione_trasporto_animali_vivi" />
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Impresa Richiedente</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(OrgDetails.getName()) %>
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
			<td class="formLabel" nowrap>
				<dhv:label name="">Denominazione</dhv:label>
			</td>
			<td>
				<input type="text" size="50" maxlength="80" name="banca" value="<%= toHtmlValue(OrgDetails.getBanca()) %>">
			</td>
		</tr>
		
  <%--dhv:include name="organization.classification" none="true">
  <tr class="containerBody" style="display: none">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Classification">Classification</dhv:label>
    </td>
    <td>
      <input type="radio" name="form_type" value="organization" onClick="javascript:updateFormElements(0);" <%= !OrgDetails.getIsIndividual() ? " checked" : "" %>>
      <dhv:label name="accounts.accounts_add.Organization">Organization</dhv:label>
      <input type="radio" name="form_type" value="individual" onClick="javascript:updateFormElements(1);" <%= OrgDetails.getIsIndividual() ? " checked" : "" %>>
      <dhv:label name="accounts.accounts_add.Individual">Individual</dhv:label>
    </td>
  </tr>
</dhv:include>
  <dhv:evaluate if="<%= OrgDetails.getPrimaryContact() == null %>">
    <dhv:include name="impiantiMacellazione-name" none="true">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="accounts.accounts_add.OrganizationName">Organization Name</dhv:label>
        </td>
        <td>
          <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
       </td>
      </tr>
    </dhv:include>
    </dhv:evaluate--%>
        
    <dhv:include name="impiantiMacellazione-number" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="organization.accountNumber">Account Number</dhv:label>
      </td>
      <td>
       <%= toHtmlValue(OrgDetails.getAccountNumber()) %>
      </td>
    </tr>
  </dhv:include>
      <tr class="containerBody">
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <%= toHtmlValue(OrgDetails.getPartitaIva()) %>
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
  <tr class="containerBody">
	<td class="formLabel" nowrap>
      <dhv:label name="">Codice Istat Principale</dhv:label>
	</td>
	<td>
	  <%= toHtmlValue(OrgDetails.getCodiceFiscaleCorrentista()) %>
	</td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(OrgDetails.getAlertText()) %>
    </td>
  </tr>

  <tr class="containerBody">
	<td class="formLabel" nowrap>
	  <dhv:label name="">Codici Istat Secondari</dhv:label>
	</td>
	<%--<td>
	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="abi" name="abi" value="<%= toHtmlValue(OrgDetails.getAbi()) %>">
	  &nbsp;[<a href="javascript:popLookupSelectorCustom('abi','cab','lookup_codistat','');"><dhv:label name="requestor.requestor_add.select">Select</dhv:label></a>]
	</td>--%>
	<td>
	
				  Codice 1&nbsp;&nbsp;
      		 <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice1" name="codice1" value="<%= toHtmlValue(OrgDetails.getCodice1()) %>">
      		 <%if((OrgDetails.getCodice1()!= null) && (!OrgDetails.getCodice1().equals(""))) {}else{%>
      		    [<a href="javascript:popLookupSelectorCustomNew('codice1', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <%} %>
      		 <div id="div_codice1" style="display: <%=(((OrgDetails.getCodice1()!= null) && (!OrgDetails.getCodice1().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 2&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice2" name="codice2" value="<%= toHtmlValue(OrgDetails.getCodice2()) %>">
      		 	<%if((OrgDetails.getCodice2()!= null) && (!OrgDetails.getCodice2().equals(""))){}else {%>
      		 	[<a href="javascript:popLookupSelectorCustomNew('codice2', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<%} %>
      		 </div>
      		 <div id="div_codice2" style="display: <%=(((OrgDetails.getCodice2()!= null) && (!OrgDetails.getCodice2().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 3&nbsp;&nbsp;
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice3" name="codice3" value="<%= toHtmlValue(OrgDetails.getCodice3()) %>">
      		    <%if((OrgDetails.getCodice3()!= null) && (!OrgDetails.getCodice3().equals(""))){}else{ %>
      		    [<a href="javascript:popLookupSelectorCustomNew('codice3', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <%} %>
      		 </div>
      		 <div id="div_codice3" style="display: <%=(((OrgDetails.getCodice3()!= null) && (!OrgDetails.getCodice3().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 4&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice4" name="codice4" value="<%= toHtmlValue(OrgDetails.getCodice4()) %>">
      		 	<%if((OrgDetails.getCodice4()!= null) && (!OrgDetails.getCodice4().equals(""))){}else{ %>
      		 	[<a href="javascript:popLookupSelectorCustomNew('codice4', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<%} %>
      		 </div>
      		 <div id="div_codice4" style="display: <%=(((OrgDetails.getCodice4()!= null) && (!OrgDetails.getCodice4().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 5&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice5" name="codice5" value="<%= toHtmlValue(OrgDetails.getCodice5()) %>">
      		 	<%if((OrgDetails.getCodice5()!= null) && (!OrgDetails.getCodice5().equals(""))){}else{ %>
      		    [<a href="javascript:popLookupSelectorCustomNew('codice5', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <%} %>
      		 </dir>
      		 <div id="div_codice5" style="display: <%=(((OrgDetails.getCodice5()!= null) && (!OrgDetails.getCodice5().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 6&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice6" name="codice6" value="<%= toHtmlValue(OrgDetails.getCodice6()) %>">
				 <%if((OrgDetails.getCodice6()!= null) && (!OrgDetails.getCodice6().equals(""))){}else{ %>
      		  	 [<a href="javascript:popLookupSelectorCustomNew('codice6', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	 <%} %>
      		 </div>
      		 <div id="div_codice6" style="display: <%=(((OrgDetails.getCodice6()!= null) && (!OrgDetails.getCodice6().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 7&nbsp;&nbsp;
      		  	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice7" name="codice7" value="<%= toHtmlValue(OrgDetails.getCodice7()) %>">
      		  	<%if((OrgDetails.getCodice7()!= null) && (!OrgDetails.getCodice7().equals(""))){}else{ %>
      		  	[<a href="javascript:popLookupSelectorCustomNew('codice7', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	<%} %>
      		  </div>	
      		 <div id="div_codice7" style="display: <%=(((OrgDetails.getCodice7()!= null) && (!OrgDetails.getCodice7().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 8&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice8" name="codice8" value="<%= toHtmlValue(OrgDetails.getCodice8()) %>">
      		 	<%if((OrgDetails.getCodice8()!= null) && (!OrgDetails.getCodice8().equals(""))){}else{ %>
      		 	[<a href="javascript:popLookupSelectorCustomNew('codice8', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<%} %>	
      		 </div>
      		 <div id="div_codice8" style="display: <%=(((OrgDetails.getCodice8()!= null) && (!OrgDetails.getCodice8().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 9&nbsp;&nbsp;
         	    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice9" name="codice9" value="<%= toHtmlValue(OrgDetails.getCodice9()) %>">
         	    <%if((OrgDetails.getCodice9()!= null) && (!OrgDetails.getCodice9().equals(""))){}else{ %>
         	    [<a href="javascript:popLookupSelectorCustomNew('codice9', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
         	    <%} %>
         	 </div>
      		 <div id="div_codice9" style="display: <%=(((OrgDetails.getCodice9()!= null) && (!OrgDetails.getCodice9().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 10
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice10" name="codice10" value="<%= toHtmlValue(OrgDetails.getCodice10()) %>">
      		    <%if((OrgDetails.getCodice10()!= null) && (!OrgDetails.getCodice10().equals(""))){}else{ %>
      		    [<a href="javascript:popLookupSelectorCustomNew('codice10', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <%} %>
        	 </div>
      </td>
  </tr>
  <tr class="containerBody" style="display:none">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.cab">CAB</dhv:label>
    </td>
    <td>
      <input style="background-color: lightgray" readonly="readonly" type="text" size="50" id="cab" name="cab" value="<%= toHtmlValue(OrgDetails.getCab()) %>">
    </td>
  </tr>
  <dhv:include name="organization.industry" none="true">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_add.Industry">Industry</dhv:label>
      </td>
      <td>
        <%= IndustryList.getHtmlSelect("industry",OrgDetails.getIndustry()) %>
      </td>
    </tr>
  </dhv:include>
  <%if((OrgDetails.getContoCorrente()==null) || (OrgDetails.getContoCorrente()== "")) {}else{%>
  		<tr class="containerBody">
			<td class="formLabel" nowrap>
				<dhv:label name="">Tipo Autoveicolo</dhv:label>
			</td>
			<td>
				<input type="text" size="30" maxlength="50" name="contoCorrente" value="<%= toHtmlValue(OrgDetails.getContoCorrente()) %>">
			</td>
		</tr>
		<tr class="containerBody">
			<td class="formLabel" nowrap>
				<dhv:label name="">Targa Autoveicolo</dhv:label>
			</td>
			<td>
				<input type="text" size="20" maxlength="10" name="nomeCorrentista" value="<%= toHtmlValue(OrgDetails.getNomeCorrentista()) %>">
			</td>
		</tr>
		
		<%}if((OrgDetails.getCodiceCont()==null)||(OrgDetails.getContoCorrente()== "")) {}else{ %>
	 <tr class="containerBody" id="list3" style="display:none">
    <td class="formLabel" nowrap  id="codiceCont1">
      <dhv:label name="">Codice Contenitore</dhv:label>
    </td>
    <td>
      <input id="codiceCont" type="text" size="20" maxlength="20" name="codiceCont" value="<%= toHtmlValue(OrgDetails.getCodiceCont()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <%} %>
  <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Attività</dhv:label>
    </td><td>
        <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Autoveicolo")%>">
        Mobile
        </dhv:evaluate>
       
        <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Es. Commerciale")%>">
      Fissa
        </dhv:evaluate>
       <%--= toHtmlValue(OrgDetails.getTipoDest()) --%>&nbsp;
       <input type="hidden" name="tipoDest" value="<%= toHtmlValue(OrgDetails.getTipoDest()) %>">
     
    </td></tr>
       <dhv:include name="organization.source" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="contact.source">Source</dhv:label>
      </td>
      <td>
   <table border=0>
      <tr>
      <td >
       <%	SourceList.setJsEvent("onChange=\"javascript:selectCarattere('source');\"");
        %>
        <%= SourceList.getHtmlSelect("source",OrgDetails.getSource()) %>
      </td>
 
      <dhv:evaluate if="<%= OrgDetails.getSource()!= 1 %>">
       	<td style="visibility: hidden;" id="data1">
        		Dal
        	</td>
        	<td style="visibility: hidden;" id="data3">
           		<zeroio:dateSelect form="addAccount" field="dateI" timestamp="<%= OrgDetails.getDateI() %>" showTimeZone="false"/><font color="red">*</font>
          	</td>
       
       	 	
           	<td style="visibility: hidden;" id="data2">
           		Al
           	</td>
            	<td style="visibility: hidden;" id="data4">
           		<zeroio:dateSelect form="addAccount" field="dateF" timestamp="<%= OrgDetails.getDateF() %>" showTimeZone="false" /><font color="red">*</font>
           	</td>
           	<td style="visibility: hidden;" id="cessazione">
           	<input type="checkbox" name="cessazione" value ="true" <%= OrgDetails.getCessazione()?"checked":"" %> /> <dhv:label name="accounts.Assetsf">Cessazione Automatica</dhv:label>
           	</td>
        </dhv:evaluate> 
        
    </tr>
    </table>
    </tr>
  </dhv:include>    
    <dhv:include name="organization.stage" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="account.stage">Stage</dhv:label>
      </td>
      <td>
        <%= StageList.getHtmlSelect("stageId",OrgDetails.getStageId()) %><font color="red">*</font>
      </td>
    </tr>
  </dhv:include>
        <%--dhv:include name="organization.date1" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.date1">Date1</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addticket" field="date1" timestamp="<%= OrgDetails.getDate1() %>" showTimeZone="false" />
        <%= showAttribute(request, "date1Error") %>
      </td>
    </tr>
    </dhv:include--%>

    <dhv:include name="organization.rating" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="sales.rating">Rating</dhv:label>
      </td>
      <td>
        <%= RatingList.getHtmlSelect("rating",OrgDetails.getRating()) %>
      </td>
    </tr>
  </dhv:include>
   <dhv:include name="organization.alert" none="true">
    <tr class="containerBody" style="display: none">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addticket" field="alertDate" timestamp="<%= OrgDetails.getAlertDate() %>" timeZone="<%= OrgDetails.getAlertDateTimeZone() %>" showTimeZone="false" /><font color="red">*</font>
        <%= showAttribute(request, "alertDateError") %><%= showWarningAttribute(request, "alertDateWarning") %>
      </td>
    </tr>
  </dhv:include>
        
    <dhv:include name="impiantiMacellazione-select-primary-contact" none="true">
        <tr class="containerBody">
          <td nowrap class="formLabel" name="primarycontact1" id="primarycontact1">
            <dhv:label name="ticket.primaryContact">Primary Contact</dhv:label>
          </td>
          <td>
            <dhv:evaluate if="<%= contactList != null && contactList.size() > 0 %>">
              <%  HtmlSelect contactSelect = contactList.getHtmlSelectObj();
                  contactSelect.addItem(-1,  "-- SELEZIONA VOCE --",0); %>
              <%= contactSelect.getHtml("primaryContactId") %>
            </dhv:evaluate><dhv:evaluate if="<%= contactList == null || contactList.size() == 0 %>">
              <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <input type="hidden" name="primaryContactId" id="primaryContactId" value="-1"/>
            </dhv:evaluate>  <%= showAttribute(request, "primaryContactIdError") %>
          </td>
        </tr>
    </dhv:include>

  <dhv:evaluate if="<%= OrgDetails.getPrimaryContact() != null %>">
    <dhv:include name="impiantiMacellazione-name" none="true">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="accounts.accounts_add.OrganizationName">Organization Name</dhv:label>
        </td>
        <td>
          <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="35" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
       </td>
      </tr>
    </dhv:include>
    <dhv:include name="account-salutation" none="true">
       <tr class="containerBody">
        <td id="listSalutation1" name="listSalutation1" nowrap class="formLabel">
          <dhv:label name="accounts.accounts_contacts_add.Salutation">Salutation</dhv:label>
        </td>
        <td>
          <% SalutationList.setJsEvent("onchange=\"javascript:fillSalutation('addticket');\"");%>
          <%= SalutationList.getHtmlSelect("listSalutation",OrgDetails.getNameSalutation()) %>
          <input type="hidden" size="35" name="nameSalutation" value="<%= toHtmlValue(OrgDetails.getNameSalutation()) %>">
        </td>
      </tr>
    </dhv:include>
    <dhv:include name="impiantiMacellazione-firstname" none="true">
      <tr class="containerBody">
        <td name="nameFirst1" id="nameFirst1" nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label>
        </td>
        <td>
          <input onFocus="if (orgSelected == 1) { tabNext(this) }" type="text" size="35" name="nameFirst" value="<%= toHtmlValue(OrgDetails.getNameFirst()) %>">
        </td>
      </tr>
    </dhv:include>
    <dhv:include name="impiantiMacellazione-middlename" none="true">
      <tr class="containerBody">
        <td name="nameMiddle1" id="nameMiddle1" nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.MiddleName">Middle Name</dhv:label>
        </td>
        <td>
          <input onFocus="if (orgSelected == 1) { tabNext(this) }" type="text" size="35" name="nameMiddle" value="<%= toHtmlValue(OrgDetails.getNameMiddle()) %>">
        </td>
      </tr>
    </dhv:include>
    <dhv:include name="impiantiMacellazione-lastname" none="true">
      <tr class="containerBody">
        <td name="nameLast1" id="nameLast1" nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label>
        </td>
        <td>
          <input onFocus="if (orgSelected == 1) { tabNext(this) }" type="text" size="35" name="nameLast" value="<%= toHtmlValue(OrgDetails.getNameLast()) %>"><font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
        </td>
      </tr>
    </dhv:include>
  </dhv:evaluate>

  
  <dhv:include name="organization.url" none="true">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_add.WebSiteURL">Web Site URL</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="url" value="<%= toHtmlValue(OrgDetails.getUrl()) %>">
      </td>
    </tr>
  </dhv:include>

  <dhv:include name="organization.dunsType" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.duns_type">DUNS Type</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="dunsType" maxlength="300" value="<%= toHtmlValue(OrgDetails.getDunsType()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.yearStarted" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.year_started">Year Started</dhv:label>
      </td>
      <td>
        <input type="text" size="10" name="yearStarted" value='<%= OrgDetails.getYearStarted() > 0 ? String.valueOf(OrgDetails.getYearStarted()) : "" %>'>
        <%= showAttribute(request, "yearStartedWarning") %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.employees" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.employees">No. of Employees</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="employees" value='<%= OrgDetails.getEmployees() > -1 ? String.valueOf(OrgDetails.getEmployees()) : "" %>'>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.revenue" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Revenue">Revenue</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="revenue" size="15" value="<zeroio:number value="<%= OrgDetails.getRevenue() %>" locale="<%= User.getLocale() %>" />">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.potential" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.Potential">Potential</dhv:label>
      </td>
      <td>
        <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
        <input type="text" name="potential" size="15" value="<zeroio:number value="<%= OrgDetails.getPotential() %>" locale="<%= User.getLocale() %>" />">
      </td>
    </tr>
  </dhv:include>
  <dhv:evaluate if="<%= OrgDetails.getPrimaryContact() == null %>">
    <dhv:include name="organization.ticker" none="true">
      <tr class="containerBody">
        <td name="ticker1" id="ticker1" nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.TickerSymbol">Ticker Symbol</dhv:label>
        </td>
        <td>
          <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="10" maxlength="10" name="ticker" value="<%= toHtmlValue(OrgDetails.getTicker()) %>">
      </td>
      </tr>
    </dhv:include>
  </dhv:evaluate>
  <dhv:include name="organization.dunsNumber" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.duns_number">DUNS Number</dhv:label>
      </td>
      <td>
        <input type="text" size="15" name="dunsNumber" maxlength="30" value="<%= toHtmlValue(OrgDetails.getDunsNumber()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.businessNameTwo" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.business_name_two">Business Name 2</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="businessNameTwo" maxlength="300" value="<%= toHtmlValue(OrgDetails.getBusinessNameTwo()) %>">
      </td>
    </tr>
  </dhv:include>
	<%--
  <dhv:include name="organization.sicCode" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.sic_code">SIC</dhv:label>
      </td>
      <td>
        <%= SICCodeList.getHtmlSelect("sicCode",OrgDetails.getSicCode()) %>
      </td>
    </tr>
  </dhv:include>
	--%>
  <dhv:include name="organization.sicDescription" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.sicDescription">SIC Description</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="sicDescription" maxlength="300" value="<%= toHtmlValue(OrgDetails.getSicDescription()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:evaluate if="<%= OrgDetails.getPrimaryContact() == null %>">
    <dhv:include name="impiantiMacellazione-size" none="true"> 
      <tr class="containerBody">
        <td name="accountSize1" id="accountSize1" nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.accountSize">Account Size</dhv:label>
        </td>
        <td>
          <%= AccountSizeList.getHtmlSelect("accountSize",OrgDetails.getAccountSize()) %>
        </td>
      </tr>
    </dhv:include>
  </dhv:evaluate>
  <dhv:include name="impiantiMacellazione-segment" none="true"> 
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.segment">Segment</dhv:label>
      </td>
      <td>
        <%= SegmentList.getHtmlSelect("segmentId",OrgDetails.getSegmentId()) %>
        </td>
    </tr>
  </dhv:include>
  <dhv:include name="impiantiMacellazione-directbill" none="true">
    <dhv:permission name="impiantiMacellazione-directbill-view">
      <tr class="containerBody">
          <td nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.directBill">Direct Bill</dhv:label>
        </td>
        <td>
          <dhv:permission name="impiantiMacellazione-directbill-edit">
            <input type="checkbox" name="directBill" <%=(OrgDetails.getDirectBill()? "CHECKED" : "") %> />
          </dhv:permission>
          <dhv:permission name="impiantiMacellazione-directbill-edit" none="true">
            <dhv:permission name="impiantiMacellazione-directbill-view">
                <input type="checkbox" name="directBill1" <%=OrgDetails.getDirectBill()?"CHECKED":""%> DISABLED />
            </dhv:permission>
            <input type="hidden" name="directBill" value="<%=OrgDetails.getDirectBill()%>" />
          </dhv:permission>
         </td>
        </tr>
      </dhv:permission>
  </dhv:include>
    
    <%--tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data inizio attività</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="date2" timestamp="<%= OrgDetails.getDate2() %>" showTimeZone="false" />
      </td>
    </tr--%>
  
  <%--dhv:include name="organization.contractEndDate" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.ContractEndDate">Contract End Date</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addticket" field="contractEndDate" timestamp="<%= OrgDetails.getContractEndDate() %>" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" /><%= showError(request, "dataFineAttivitaError") %>
      </td>
    </tr>
  </dhv:include--%>
  <%--dhv:evaluate if="<%= OrgDetails.getOwner() == User.getUserId() || isManagerOf(pageContext, User.getUserId(), OrgDetails.getOwner()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.owner">Account Owner</dhv:label>
    </td>
    <td>
      <%= UserList.getHtmlSelect("owner", OrgDetails.getOwner() ) %>
    </td>
  </tr>
</dhv:evaluate--%>
  </table>
  </br>
  
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Titolo rappresentante</dhv:label>
    </td>
    <td>  <!-- titoloRappresentante è il nome della variabile nel bean -->
       <%= TitoloList.getHtmlSelect("titoloRappresentante",OrgDetails.getTitoloRappresentante()) %><font color="red">*</font></td>
  </tr>
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Codice Fiscale
      </td>
      <td>
        <input type="text" size="50" name="codiceFiscaleRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font>
      </td>
    </tr>
  </dhv:include>
    <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Cognome
      </td>
      <td>
        <input type="text" size="50" name="cognomeRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font>
      </td>
    </tr>
  </dhv:include>
  
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Nome
      </td>
      <td>
        <input type="text" size="50" name="nomeRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font>
      </td>
    </tr>
  </dhv:include> 
  
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Email</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="emailRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getEmailRappresentante()) %>">
      </td>
    </tr>
  </dhv:include>
  
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Telefono</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="telefonoRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getTelefonoRappresentante()) %>">
      </td>
    </tr>
  </dhv:include>
  
  <!-- fine delle modifiche -->
  

</table>
<br>

<%
  boolean noneSelected = false;
%>

<dhv:include name="organization.phoneNumbers" none="true">
<%-- Phone Numbers
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.requestor_add.PhoneNumbers">Phone Numbers</dhv:label></strong>
	  </th>
  </tr>
<dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() == null) %>">
<%  
  int icount = 0;
  Iterator inumber = OrgDetails.getPhoneNumberList().iterator();
  while (inumber.hasNext()) {
    ++icount;
    OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber)inumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="requestor.requestor_add.Phone">Phone</dhv:label> <%= icount %>
    </td>
    <td>
      <input type="hidden" name="phone<%= icount %>id" value="<%= thisPhoneNumber.getId() %>">
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <input type="text" size="20" name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;<dhv:label name="requestor.requestor_add.ext">ext.</dhv:label>
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
      <input type="radio" name="primaryNumber" value="<%= icount %>" <%= (thisPhoneNumber.getPrimaryNumber()) ? " checked" : "" %>><dhv:label name="requestor.requestor_add.primary">Primary</dhv:label>
      <input type="checkbox" name="phone<%= icount %>delete" value="on"><dhv:label name="requestor.requestor_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>    
<%    
  }
  ++icount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="requestor.requestor_add.Phone">Phone</dhv:label> <%= icount %>
    </td>
    <td>
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + icount + "type", "Main") %>
      <input type="text" size="20" name="phone<%= icount %>number">&nbsp;<dhv:label name="requestor.requestor_add.ext">ext.</dhv:label>
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" />
      <input type="radio" name="primaryNumber" value="<%= icount %>"><dhv:label name="requestor.requestor_add.primary">Primary</dhv:label>
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() != null) %>">
<%  
  int icount = 0;
  Iterator inumber = OrgDetails.getPrimaryContact().getPhoneNumberList().iterator();
  while (inumber.hasNext()) {
    ++icount;
    ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber) inumber.next();
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="requestor.requestor_add.Phone">Phone</dhv:label> <%= icount %>
    </td>
    <td>
      <input type="hidden" name="phone<%= icount %>id" value="<%= thisPhoneNumber.getId() %>">
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <input type="text" size="20" name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;<dhv:label name="requestor.requestor_add.ext">ext.</dhv:label>
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
      <input type="checkbox" name="phone<%= icount %>delete" value="on"><dhv:label name="requestor.requestor_modify.MarkToRemove">mark to remove</dhv:label>
      <input type="radio" name="primaryNumber" value="<%= icount %>" <%= (thisPhoneNumber.getPrimaryNumber()) ? " checked" : "" %>><dhv:label name="requestor.requestor_add.primary">Primary</dhv:label>
    </td>
  </tr>
<%
  }
  ++icount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="requestor.requestor_add.Phone">Phone</dhv:label> <%= icount %>
    </td>
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone" + icount + "type", "") %>
      <input type="text" size="20" name="phone<%= icount %>number">&nbsp;<dhv:label name="requestor.requestor_add.ext">ext.</dhv:label>
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10">
      <input type="radio" name="primaryNumber" value="<%= icount %>"><dhv:label name="requestor.requestor_add.primary">Primary</dhv:label>
    </td>
  </tr>
</dhv:evaluate>
</table>
<br />
--%>








<dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() == null) %>">
<%  
  int acount = 0;
  Iterator anumber = OrgDetails.getAddressList().iterator();
  while (anumber.hasNext()) {
    ++acount;
    OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
%> 
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:evaluate if="<%= thisAddress.getType() == 1 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede legale</dhv:label></strong>
	  </dhv:evaluate>
	  <dhv:evaluate if="<%= thisAddress.getType() == 5 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede operativa</dhv:label></strong>
	  </dhv:evaluate>  
	  <dhv:evaluate if="<%= thisAddress.getType() == 6 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Locale funzionalmente collegato</dhv:label></strong>
	  </dhv:evaluate>  
	  <dhv:evaluate if="<%= thisAddress.getType() == 7%>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede attività mobile</dhv:label></strong>
	  </dhv:evaluate> 
      <%-- %><strong><dhv:label name="requestor.requestor_add.Addressess"><%= toHtml(thisAddress.getTypeName())%></dhv:label></strong>--%>
      <input type="hidden" name="address<%=acount %>id" value="<%=thisAddress.getId() %>">
      
    </th>
    <input type="hidden" name = "address<%=acount %>type" value ="<%= thisAddress.getType()%>">
   <dhv:evaluate if="<%=thisAddress.getType() == 7 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel" id="tipoStruttura1">
        <dhv:label name="contact.fsource">Tipo Struttura</dhv:label>
    	<%-- %><input type="hidden" name="address3type" value="7">--%>
      </td>
      <td class="containerBody" id="tipoStruttura">
        <%= TipoStruttura.getHtmlSelect("TipoStruttura",OrgDetails.getTipoStruttura())%>
      </td>
     </tr>
     <%if((OrgDetails.getContoCorrente()==null) || (OrgDetails.getContoCorrente()== "")) {}else{%>
  		<tr class="containerBody" id="list"  >
    <td class="formLabel" nowrap  id="tipoVeicolo1">
      <dhv:label name="">Tipo Autoveicolo</dhv:label>
    </td>
    <td>
      <input id="tipoVeicolo" type="text" size="30" maxlength="50" name="contoCorrente" value="<%= toHtmlValue(OrgDetails.getContoCorrente()) %>">
    </td>
  </tr>
  <tr class="containerBody" id="list2" >
    <td class="formLabel" nowrap id="targaVeicolo1">
      <dhv:label name="">Targa Autoveicolo</dhv:label>
    </td>
    <td>
      <input id="targaVeicolo" type="text" size="20" maxlength="10" name="nomeCorrentista" value="<%= toHtmlValue(OrgDetails.getNomeCorrentista()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
    <%} %>
   
   </dhv:evaluate>
   <dhv:evaluate if="<%= thisAddress.getType() == 6 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel" id="tipoStruttura1">
        <dhv:label name="contact.fsource">Tipo locale</dhv:label>
 		<%-- %><input type="hidden" name="address4type" value="6">--%>
      </td>
    
      <td class="containerBody">
        <%= TipoLocale.getHtmlSelect("TipoLocale",OrgDetails.getTipoLocale())%>
      </td>
     </tr>
   	
   </dhv:evaluate>
  
  </tr> 
  
    
<%--   
  <tr class="containerBody">
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    <td class="formLabel">
      <dhv:label name="requestor.requestor_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <input type="radio" name="primaryAddress" value="<%=acount%>" <%= thisAddress.getPrimaryAddress() ? " checked" : ""%>><dhv:label name="requestor.requestor_add.primary">Primary</dhv:label>
      <input type="checkbox" name="address<%= acount %>delete" value="on"><dhv:label name="requestor.requestor_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>--%>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
   
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate>
    </td>
  </tr>
  <!-- 
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine3">Address Line 3</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine3()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine4">Address Line 4</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line4" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine4()) %>">
    </td>
  </tr>
   -->
   
   
   
  
    
     
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
    <%if(thisAddress.getType()==1){%>
       <input type="text" name="address1city" id="address1city" value="<%=thisAddress.getCity() %>">    	
    <%}else{ %>
    
    <select  name="address<%= acount %>city">
	<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            
	 <%
                Vector v = OrgDetails.getComuni2();
	 			Enumeration e=v.elements();
                while (e.hasMoreElements()) {
                	String prov=e.nextElement().toString();
                  
        %>
                <option <%= ( (thisAddress.getCity() != null) && thisAddress.getCity().equals( prov ) ) ? ( "selected=\"selected\"" ) : ("")%> value="<%=prov%>"><%= prov %></option>	
              <%}%>
		
	</select> 
	<%} %>
	 <dhv:evaluate if="<%= thisAddress.getType() != 6 %>">
	<font color = "red">*</font>
	</dhv:evaluate>
    
    
      <%-- input type="text" size="28" name="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>" --%>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>   
    <%if(thisAddress.getType() == 1){%>
        <input type="text" size="10" name="address<%= acount %>state" maxlength="80" value="<%=OrgDetails.getState()%>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate>
 <%}else{ %><% if (User.getSiteId() == 3) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="BN">         
          <%}%>
          <% if (User.getSiteId() == 1 || User.getSiteId() == 2) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="AV">
          <%}%>
          <% if (User.getSiteId() == 4 || User.getSiteId() == 5) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="CE">
          <%}%>
          <% if (User.getSiteId() == 6 || User.getSiteId() == 7 || User.getSiteId() == 8 || User.getSiteId() == 9 || User.getSiteId() == 10) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="NA">
          <%}%>
          <% if (User.getSiteId() == 11 || User.getSiteId() == 12 || User.getSiteId() == 13) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="SA">
          <%}%>                                                   
       <%-- input type="text" readonly="readonly" size="10" name="address<%= acount %>state" maxlength="80" value="<%=OrgDetails.getState()%>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate--%>
       <%} %>
    </td>
  </tr>
  
  <!--    
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>                                                        
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry(), thisAddress.getState()) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>"  value="<%= toHtmlValue(thisAddress.getState()) %>">
      </span>
    </td>
  </tr>
--> 
  
  
  
  
  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <dhv:include name="address.country" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Country">Country</dhv:label>
    </td>
    <td>
      <%
        CountrySelect = new CountrySelect(systemStatus, applicationPrefs.get("SYSTEM.COUNTRY"));
        CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "','"+thisAddress.getState()+"');\"");
      %>
      <%= CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry()) %>
    </td>
  </tr>
  </dhv:include>
  <!--
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.county">County</dhv:label></td>
    <td><input type="text" name="address<%= acount %>county" size="28" maxlenth="80" value="<%= toHtmlValue(thisAddress.getCounty()) %>"></td>
  </tr>
   -->
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude" size="30" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLatitude()) : "") %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" size="30" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLongitude()) : "") %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate></td>
  </tr>
  <tr class="containerBody">
    

  </tr> 
  </table><br>
<%    
  }
  ++acount;
  OrganizationAddress thisAddress = new OrganizationAddress();
  thisAddress.setCountry(applicationPrefs.get("SYSTEM.COUNTRY"));
%>

 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <%-- %><tr>
    <th colspan="2">
      <strong><dhv:label name="requestor.requestor_add.Addressess"><%= toHtml(thisAddress.getTypeName())%></dhv:label></strong>
    </th>
  </tr> <%-- 
  <tr class="containerBody">
    <td class="formLabel">
     <dhv:label name="requestor.requestor_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", "") %>
      <input type="radio" name="primaryAddress" value="<%=acount%>"><dhv:label name="requestor.requestor_add.primary">Primary</dhv:label>
    </td>
  </tr>--%>
  <%-- %>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80">
    </td>
  </tr>--%>
  
  
  
  <!--
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine3">Address Line 3</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine4">Address Line 4</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line4" maxlength="80">
    </td>
  </tr>
   -->
   
  <%-- %>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
    
    
    <select  name="address<%= acount %>city">
	<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            
	 <%
                Vector v = OrgDetails.getComuni2();
	 			Enumeration e=v.elements();
                while (e.hasMoreElements()) {
                	String prov=e.nextElement().toString();
                  
        %>
                <option <%= ( (thisAddress.getCity() != null) && thisAddress.getCity().equals( prov ) ) ? ( "selected=\"selected\"" ) : ("")%> value="<%=prov%>"><%= prov %></option>	
              <%}%>
		
	</select> 
    
    
      <%-- input type="text" size="28" name="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>" --%>
  <%-- %>  </td>
  </tr> --%> 
  
  <!--   
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80">
    </td>
  </tr>
  --> 
  <%-- %>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>                                                        
       <input type="text" readonly="readonly" size="50" name="address<%= acount %>state" maxlength="80" value="<%=OrgDetails.getState()%>">
    </td>
  </tr>--%>
 
  
  
  <!--  
    
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry()) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>">
      </span>
    </td>
  </tr>
 -->
 
  <%-- %>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12">
    </td>
  </tr>--%>
  <dhv:include name="address.country" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Country">Country</dhv:label>
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "','');\""); %>
      <%= CountrySelect.getHtml("address" + acount + "country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
    </td>
  </tr>
  </dhv:include>
 
  
  <!--
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.county">County</dhv:label></td>
    <td><input type="text" name="address<%= acount %>county" size="28" maxlenth="80"></td>
  </tr>
   -->
   
  <%-- %>   
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude" size="10" value=""></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" size="10" value=""></td>
  </tr>--%>
  
  </dhv:evaluate>
 
 
 
  
 
<dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() != null) %>">
<%  
  int acount = 0;
  Iterator anumber = OrgDetails.getPrimaryContact().getAddressList().iterator();
  while (anumber.hasNext()) {
    ++acount;
    ContactAddress thisAddress = (ContactAddress)anumber.next();
%>   
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="requestor.requestor_add.Addresses">Addresses</dhv:label></strong>
    </th>
  </tr> 
  
     
  <%-- <tr class="containerBody">
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    <td class="formLabel">
      <dhv:label name="requestor.requestor_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <input type="radio" name="primaryAddress" value="<%=acount%>" <%= thisAddress.getPrimaryAddress() ? " checked" : ""%>><dhv:label name="requestor.requestor_add.primary">Primary</dhv:label>
      <input type="checkbox" name="address<%= acount %>delete" value="on"><dhv:label name="requestor.requestor_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>--%>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <!--
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine3">Address Line 3</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine3()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine4">Address Line 4</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line4" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine4()) %>">
    </td>
  </tr>
   -->
   
   
   
   
   
   
  <select  name="address<%= acount %>city">
	<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            
	 <%
                Vector v = OrgDetails.getComuni2();
	 			Enumeration e=v.elements();
                while (e.hasMoreElements()) {
                	String prov=e.nextElement().toString();
                  
        %>
                <option <%= ( (thisAddress.getCity() != null) && thisAddress.getCity().equals( prov ) ) ? ( "selected=\"selected\"" ) : ("")%> value="<%=prov%>"><%= prov %></option>	
              <%}%>
		
	</select> 
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>                                                        
       <input type="text" readonly="readonly" size="50" name="address<%= acount %>state" maxlength="80" value="<%=OrgDetails.getState()%>">
    </td>
  </tr>
  
  <!--    
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry(), thisAddress.getState()) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>"  value="<%= toHtmlValue(thisAddress.getState()) %>">
      </span>
    </td>
  </tr>
   --> 
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <dhv:include name="address.country" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Country">Country</dhv:label>
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "','"+thisAddress.getState()+"');\""); %>
      <%= CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry()) %>
    </td>
  </tr>
  </dhv:include>
  <!--
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.county">County</dhv:label></td>
    <td><input type="text" name="address<%= acount %>county" size="28" maxlenth="80" value="<%= toHtmlValue(thisAddress.getCounty()) %>"></td>
  </tr>
   -->
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude" size="10" value="<%= thisAddress.getLatitude() %>"></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" size="10" value="<%= thisAddress.getLongitude() %>"></td>
  </tr>
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr> 
  </table>
  <br>
<%    
  }
  ++acount;
  ContactAddress thisAddress = new ContactAddress();
  thisAddress.setCountry(applicationPrefs.get("SYSTEM.COUNTRY"));
%>

  
 <%--  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="requestor.requestor_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address" + acount + "type", "") %>
      <input type="radio" name="primaryAddress" value="<%=acount%>"><dhv:label name="requestor.requestor_add.primary">Primary</dhv:label>
    </td>
  </tr>--%>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80">
    </td>
  </tr>
  
  
  
  <!--
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine2">Address Line 2</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine3">Address Line 3</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line3" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AddressLine4">Address Line 4</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line4" maxlength="80">
    </td>
  </tr>
   -->
  
  
    
  
  
  
  <select  name="address<%= acount %>city">
	<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            
	 <%
                Vector v = OrgDetails.getComuni2();
	 			Enumeration e=v.elements();
                while (e.hasMoreElements()) {
                	String prov=e.nextElement().toString();
                  
        %>
                <option <%= ( (thisAddress.getCity() != null) && thisAddress.getCity().equals( prov ) ) ? ( "selected=\"selected\"" ) : ("")%> value="<%=prov%>"><%= prov %></option>	
              <%}%>
		
	</select> 
  <  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry()) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>">
      </span>
    </td>
  </tr>
  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12">
    </td>
  </tr>
  <dhv:include name="address.country" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Country">Country</dhv:label>
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "','');\""); %>
      <%= CountrySelect.getHtml("address" + acount + "country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
    </td>
  </tr>
  </dhv:include>
  
  
  
  <!-- 
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.county">County</dhv:label></td>
    <td><input type="text" name="address<%= acount %>county" size="28" maxlenth="80"></td>
  </tr>
   -->
   
   
      
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude" size="10" value=""></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" size="10" value=""></td>
  </tr>
  

  
  </dhv:evaluate>
</table>
<br />
</dhv:include>


<br>
<dhv:include name="organization.emailAddresses" none="false">
<%-- Email Addresses --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="requestor.requestor_add.EmailAddresses">Email Addresses</dhv:label></strong>
	  </th>
  </tr>
<dhv:evaluate if="<%=(OrgDetails.getPrimaryContact() == null)%>">
<%  
  int ecount = 0;
  Iterator enumber = OrgDetails.getEmailAddressList().iterator();
  while (enumber.hasNext()) {
    ++ecount;
    OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress)enumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Email">Email</dhv:label> <%= ecount %>
    </td>
    <td>
      <input type="hidden" name="email<%= ecount %>id" value="<%= thisEmailAddress.getId() %>">
      <%= OrgEmailTypeList.getHtmlSelect("email" + ecount + "type", thisEmailAddress.getType()) %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255" value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
      <input type="radio" name="primaryEmail" value="<%= ecount %>" <%= (thisEmailAddress.getPrimaryEmail()) ? " checked" : "" %>><dhv:label name="requestor.requestor_add.primary">Primary</dhv:label>
      <input type="checkbox" name="email<%= ecount %>delete" value="on"><dhv:label name="requestor.requestor_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>
<%    
  }
  ++ecount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="requestor.requestor_add.Email">Email</dhv:label> <%= ecount %>
    </td>
    <td>
      <%= OrgEmailTypeList.getHtmlSelect("email" + ecount + "type", "") %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255">
      <input type="radio" name="primaryEmail" value="<%= ecount %>"><dhv:label name="requestor.requestor_add.primary">Primary</dhv:label>
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%=(OrgDetails.getPrimaryContact() != null)%>">
<%  
  int ecount = 0;
  Iterator enumber = OrgDetails.getPrimaryContact().getEmailAddressList().iterator();
  while (enumber.hasNext()) {
    ++ecount;
    ContactEmailAddress thisEmailAddress = (ContactEmailAddress) enumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="requestor.requestor_add.Email">Email</dhv:label> <%= ecount %>
    </td>
    <td>
      <input type="hidden" name="email<%= ecount %>id" value="<%= thisEmailAddress.getId() %>">
      <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", thisEmailAddress.getType()) %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255" value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
      <input type="checkbox" name="email<%= ecount %>delete" value="on"><dhv:label name="requestor.requestor_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>
<%    
  }
  ++ecount;
%>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="requestor.requestor_add.Email">Email</dhv:label> <%= ecount %>
      </td>
      <td>
        <%= ContactEmailTypeList.getHtmlSelect("email" + ecount + "type", "") %>
        <input type="text" size="40" name="email<%= ecount %>address" maxlength="255">
      </td>
    </tr>
  </dhv:evaluate>
  </table>
  <br />
  </dhv:include>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="requestor.requestor_add.AdditionalDetails">Additional Details</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
      </td>
      <td>
        <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA>
      </td>
    </tr>
  </table>
  <br />
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='ImpiantiMacellazione.do?command=ViewCessazionevariazione&orgId=<%=OrgDetails.getOrgId()%>'">
</dhv:container>
</form>
