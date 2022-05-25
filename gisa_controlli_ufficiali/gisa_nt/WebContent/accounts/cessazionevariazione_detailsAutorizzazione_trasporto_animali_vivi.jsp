<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.controller.SystemStatus"%>
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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CountryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

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
      <dhv:include name="accounts-firstname" none="true">
        elm1 = document.getElementById("nameFirst1");
      </dhv:include>
      <dhv:include name="accounts-middlename" none="true">
        elm2 = document.getElementById("nameMiddle1");
      </dhv:include>
      <dhv:include name="accounts-lastname" none="true">
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
        message += "- Impresa richiesta\r\n";
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
    var url = "Accounts.do?command=OwnerJSList&form=addticket&widget=owner&allowBlank=false&siteId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
</script>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.cessazionevariazione.base.*" %>
<%@ include file="../initPage.jsp" %>

<form name="details" action="AccountCessazionevariazione.do?command=ModifyTicket&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<td>
  <a href="Accounts.do"><dhv:label name="">Stabilimenti 852</dhv:label></a> > 
  <a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Scheda Stabilimento 852</dhv:label></a> >
  <%--<a href="Accounts.do?command=ViewCessazionevariazione&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="cessazionevariazione2">Cessazioni/Variazioni</dhv:label></a> >--%>
    <a href="Accounts.do?command=ViewCessazionevariazione&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="cessazionevariazione2">Volture</dhv:label></a> >
  
<dhv:label name="campione.dettaglia">Scheda Voltura</dhv:label>
</td>
</tr>
</table>
<%
	String param1 = "id=" + TicketDetails.getId();
%>
<dhv:container name="accounts" selected="cessazionevariazione" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
<dhv:container name="accountscessazionevariazione" selected="details" object="TicketDetails"
	param="<%= param1 %>"
	hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
	<%@ include file="ticket_header_include_cessazionevariazione2.jsp"%>
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="accounts-accounts-cessazionevariazione-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<%-- dhv:permission name="accounts-accounts-cessazionevariazione-edit">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='AccountCampioni.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission--%>
	<%
		} else {
	%>
	<dhv:permission name="accounts-accounts-cessazionevariazione-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=ModifyTicket&id=<%=TicketDetails.getId() %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%--
      <dhv:permission name="quotes-view">
        <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
          <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Details&productId=<%= TicketDetails.getProductId() %>&id=<%= TicketDetails.getId() %>';submit();"/>
        </dhv:evaluate>
      </dhv:permission>
      --%>
	<dhv:permission name="accounts-accounts-cessazionevariazione-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('AccountCessazionevariazione.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('AccountCessazionevariazione.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="accounts-accounts-cessazionevariazione-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=Chiudi&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere questa variazione?') ){submit()};">
	</dhv:permission>
	<%
		}
	%>
	<dhv:permission name="accounts-accounts-cessazionevariazione-edit,accounts-accounts-cessazionevariazione-delete">
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
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
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
      message += label("check.campioni.richiedente.selezionato","- Controllare che lo \"Stabilimento\" sia stato selezionato\r\n");
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
</script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Voltura</dhv:label></strong>
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
          <input type="hidden" name="siteId" value="<%=TicketDetails.getSiteId()%>" >
      
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
      <dhv:label name="sanzioni.richiedente">Impresa</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(TicketDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=TicketDetails.getSiteId()%>" />
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
              <%=((OrgDetails.getAccountNumber()!=null && (!OrgDetails.getAccountNumber().equals("")))?(toHtmlValue(OrgDetails.getAccountNumber())):(toHtmlValue(OrgDetails.getCodiceImpresaInterno()))) %>
      </td>
    </tr>
  </dhv:include>
  
  <dhv:evaluate if="<%= (TicketDetails.getPartitaIva()==null || TicketDetails.getPartitaIva().equals(""))%>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      <dhv:label name="">Ente/Associazione</dhv:label>
			</td>
			<td>
         <input type = "checkbox" checked="checked" disabled="disabled"/>
			</td>
		</tr>
</dhv:evaluate>

<dhv:evaluate if="<%= hasText(TicketDetails.getPartitaIva()) %>">
      <tr class="containerBody">
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <%= toHtmlValue(TicketDetails.getPartitaIva()) %>
        <dhv:evaluate if="<%= OrgDetails.getIdNazione() != 106 %>">
        <img width="20" src="images/flags/eu.gif"/> 
        <i>(<%= CountryList.getSelectedValue(OrgDetails.getIdNazione()) %>)</i>
  			</dhv:evaluate>  
    </td>
  </tr>
 </dhv:evaluate>
 
 <dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscale()) %>">
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>    
    </td>
  </tr>
  </dhv:evaluate>
  
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
      <dhv:label name="">Comune di Nascita</dhv:label>
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
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="accounts-accounts-cessazionevariazione-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
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
	<dhv:permission name="accounts-accounts-cessazionevariazione-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=ModifyTicket&id=<%=TicketDetails.getId() %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>

	<dhv:permission name="accounts-accounts-cessazionevariazione-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('AccountCessazionevariazione.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('AccountCessazionevariazione.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="accounts-accounts-cessazionevariazione-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=Chiudi&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere questa variazione?') ){submit()};">
	</dhv:permission>
	<%
		}
	%>
</dhv:container>
</dhv:container>
</form>
