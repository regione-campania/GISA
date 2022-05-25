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
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="CountryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
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
    var curr_date = new Date();
	if(form.assignedDate.value.length!=10)
	{
		 message += label("", "- Data Errata\r\n");
	      formTest = false;
	}
	else
	{
		data_inserita = form.assignedDate.value ;
		anno = parseInt(data_inserita.substr(6),10);
		mese = parseInt(data_inserita.substr(3, 2),10);
		giorno = parseInt(data_inserita.substr(0, 2),10);
		
		var data=new Date(anno, mese-1, giorno);
	
		if(data>curr_date)
		{
			 message += label("", "- Non è possibile inserire una data maggiore di quella di oggi\r\n");
		      formTest = false;
		}
	}
    if (checkNullString(form.name.value)){
        message += "- Impresa richiesta\r\n";
        formTest = false;
      }
      if (checkNullString(form.codiceFiscaleRappresentante.value)){
          message += "- Codice Fiscale del rappresentante richiesto\r\n";
          formTest = false;
        }
        
         if (checkNullString(form.cognomeRappresentante.value)){
          message += "- Cognome del rappresentante richiesto\r\n";
          formTest = false;
        }
        
         if (checkNullString(form.nomeRappresentante.value)){
          message += "- Nome del rappresentante richiesto\r\n";
          formTest = false;
        }
    
 
	 if (form.assignedDate.value == "") {
	      message += label("check.campioni.data_richiesta.selezionato","- Data Voltura è richiesto\r\n");
	      formTest = false;
	    }
     
  if (form.nameLast){
    if ((indSelected == 1) && (checkNullString(form.nameLast.value))){
      message += label("check.lastname", "- Last name is a required field\r\n");
      formTest = false;
    }
  }

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

<script>
function gestisciPIVA(ckb){
	if (ckb.checked){
		//DISABILITA P_IVA
		document.getElementById("divListaEstera").style.display="none";
		document.getElementById("partitaIva").value="";
		document.getElementById("partitaIva").disabled="disabled";
		document.getElementById("linkpiva").style.display="none";
		document.getElementById("no_cf").style.display="block";
		document.getElementById("codiceFiscale").value="";
		document.getElementById("codiceFiscale").disabled="";
		document.getElementById("provenienzaIT").checked="";
		document.getElementById("provenienzaEST").checked="";
		document.getElementById("provenienzaIT").disabled="disabled";
		document.getElementById("provenienzaEST").disabled="disabled";
	} else{
		//RIABILITA P_IVA
		document.getElementById("partitaIva").value="";
		document.getElementById("partitaIva").disabled="";
		document.getElementById("linkpiva").style.display="";
		document.getElementById("no_cf").style.display="none";
		document.getElementById("codiceFiscale").value="";
		document.getElementById("codiceFiscale").disabled="disabled";
		document.getElementById("provenienzaIT").checked="checked";
		document.getElementById("provenienzaEST").checked="";
		document.getElementById("provenienzaIT").disabled="";
		document.getElementById("provenienzaEST").disabled="";
	}
}
</script>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.cessazionevariazione.base.*" %>
<%@ include file="../initPage.jsp" %>

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


    var params = "&orgId=" + escape(orgValue);
  params = params + "&populateDefects=true";

    var url = "TroubleTicketsCessazionevariazione.do?command=OrganizationJSList" + params; 
    window.frames['server_commands'].location.href=url;
  </dhv:include>
  }
  function checkForm(form){
    formTest = true;
    message = "";
  	if (form.no_piva.checked==false){
     if(form.partitaIva.value.length<11)
	{
		 message += label("", "- Lunghezza partita IVA errata.\r\n");
	     formTest = false;
	}
  	}
    var curr_date = new Date();
	if(form.assignedDate.value=='')
	{
		 message += label("", "- Data Errata\r\n");
	      formTest = false;
	}
	else
	{
		data_inserita = form.assignedDate.value ;
		anno = parseInt(data_inserita.substr(6),10);
		mese = parseInt(data_inserita.substr(3, 2),10);
		giorno = parseInt(data_inserita.substr(0, 2),10);
		
		var data=new Date(anno, mese-1, giorno);
	
		if(data>curr_date)
		{
			 message += label("", "- Non è possibile inserire una data maggiore di quella di oggi\r\n");
		      formTest = false;
		}
	}
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
    
	<!-- CONTROLLI SU PARTITA IVA-->
  	if (form.no_piva.checked==false){
	
    if (checkNullString(form.partitaIva.value) && checkNullString(form.codiceFiscale.value)){
     	message += "- Partita IVA/Codice Fiscale richiesto\r\n";
    	 formTest = false;
 	  }
   
if (! checkNullString(form.partitaIva.value) && form.partitaIva.value.length<11 && (form.provenienza==null || form.provenienzaIT.checked)){
	  
 	message += "- Partita IVA non Valida \r\n";
	 formTest = false;
	  
}

if (form.partitaIva.value.length>11 && (form.provenienza==null || form.provenienzaIT.checked)){
	 
   	message += "- Partita IVA non Valida per provenienza ITALIA \r\n";
  	 formTest = false;
	  
 }
  	} else {
  		if ( form.codiceFiscale.value=="" || form.codiceFiscale.value.length<16 ){
			message += "- Codice Fiscale richiesto \r\n";
		  	 formTest = false;
	 }
  	}
  if (form.provenienza!=null && form.provenienzaEST.checked && form.country.value==-1){
	 	 
	   	message += "- Selezionare un PAESE in caso di provenienza estera. \r\n";
	  	 formTest = false;
		  
	 }  

	if (form.no_piva.checked==false){
	if (form.partitaIva && form.partitaIva.value!="" && (form.provenienza==null || form.provenienzaIT.checked)){
 	 //alert(!isNaN(form.address2latitude.value));
 		
 			if (isNaN(form.partitaIva.value)){
  			 message += "- Valore errato per il campo Partita IVA. Si prega di inserire solo cifre\r\n";
  				 formTest = false;
  			}		 
 		}
	}
	   	    			
  	    		
	<!-- FINE CONTROLLI SU PARTITA IVA-->
	
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


<script>
function gestisciProvenienzaEstera(scelta){
	var pi = document.getElementById("partitaIva");
	var divListEst = document.getElementById("divListaEstera");
	if (divListEst!=null){
	if (scelta==1){
		pi.maxLength="99";
		divListEst.style.display="block";
		}
	else{
		pi.maxLength="11";
		divListEst.style.display="none";
	}
	}
}

function inizializzaProvenienzaEstera(idNazione){
	if (idNazione==106)
		gestisciProvenienzaEstera(0);
	else
		gestisciProvenienzaEstera(1);

}

</script>

<body>
<form name="addticket" action="AccountCessazionevariazione.do?command=UpdateTicket&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" onSubmit="return checkForm(this);" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Accounts.do"><dhv:label name="">Stabilimenti 852</dhv:label></a> >
  <a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Scheda Stabilimento 852</dhv:label></a> >
  <a href="Accounts.do?command=ViewCessazionevariazione&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Volture</dhv:label></a> >
  <% if (request.getParameter("return") == null) {%>
  <a href="AccountCessazionevariazione.do?command=TicketDetails&id=<%=TicketDetails.getId()%>"><dhv:label name="cessazionevariazionea.dettagli">Scheda Voltura</dhv:label></a> >
  <%}%>
  <dhv:label name="cessazionevariazione.modify">Modifica Voltura</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<dhv:container name="accounts" selected="cessazionevariazione" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="accountscessazionevariazione" selected="details" object="TicketDetails" param='<%= "id=" + TicketDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <%--@ include file="accounts_ticket_header_include_cessazionevariazione.jsp" --%>
     <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
          <dhv:permission name="accounts-accounts-cessazionevariazione-edit">
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="button" value="Annulla" onClick="location.href='Accounts.do?command=ViewCessazionevariazione&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="button" value="Annulla" onClick="location.href='AccountCessazionevariazione.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
          <dhv:permission name="accounts-accounts-cessazionevariazione-edit">
            <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
          </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="button" value="Annulla" onClick="location.href='Accounts.do?command=ViewCessazionevariazione&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="button" value="Annulla" onClick="location.href='AccountCessazionevariazione.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
          <%= showAttribute(request, "closedError") %>
       </dhv:evaluate>
      </dhv:evaluate>
    <br />
    <dhv:formMessage />
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
    <input readonly type="text" id="assignedDate" name="assignedDate" size="10" value="<%= toDateString(TicketDetails.getAssignedDate()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
      <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>
  <input type="hidden" name="tipo_richiesta" value="autorizzazione_trasporto_animali_vivi" />
	
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Impresa</dhv:label>
    </td>
   
     
      <td>
        <input type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="#00FF00"><b>* Inserire la variazione</b></font>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
      </td>
    
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Denominazione</dhv:label>
    </td>
   
     
      <td>
        <input type="text" size="50" maxlength="80" name="banca" value="<%= toHtmlValue(OrgDetails.getBanca()) %>"><font color="#00FF00"><b>* Inserire la variazione</b></font>
        
      </td>
    
  </tr>
  <input type="hidden" name="tipo_richiesta" value="attivita_ispettiva_rilascioautorizzazioni_e_vigilanza" />
     
    <dhv:include name="accounts-number" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="organization.accountNumber">Account Number</dhv:label>
      </td>
      <td>
              <%= ((OrgDetails.getAccountNumber()!=null)?(toHtmlValue(OrgDetails.getAccountNumber())):(toHtmlValue(OrgDetails.getCodiceImpresaInterno()))) %>
      </td>
    </tr>
  </dhv:include>
  
  
    <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Ente/Associazione</dhv:label>
    </td>
    <td>
      <input type="checkbox" id="no_piva" name="no_piva"
      		<% if  (OrgDetails.getNo_piva()==true){ %> checked="checked"<%} %>
      		onclick="javascript:gestisciPIVA(this)"/>Partita IVA non obbligatoria
    </td>
  </tr>
      <tr class="containerBody">
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
            <input type="text" size="50" maxlength="11" name="partitaIva" id="partitaIva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>"><font id="linkpiva" color="#00FF00"><b>* Inserire la variazione</b></font>
   
    <!--  BLOCCO PARTITA IVA -->
     <% if (OrgDetails.getTipoDest().equals("Es. Commerciale")) {%>
     <div id="divProvenienza" style="display:block">
        <dhv:label name="">Provenienza: </dhv:label>
       <input type="radio" name="provenienza" id="provenienzaIT" <%if (OrgDetails.getIdNazione()==106) {%> checked="checked" <% } %> value="ITALIA" onclick="gestisciProvenienzaEstera(0)"/> <img width="20px" src="images/flags/it.gif"/> Italia
      <input type="radio" name="provenienza" id="provenienzaEST" <%if (OrgDetails.getIdNazione()!=106) {%> checked="checked" <% } %> value="ESTERO" onclick="gestisciProvenienzaEstera(1)"/> <img width="20px" src="images/flags/eu.gif"/> Estera
       
       <div id="divListaEstera" style="display:<%if (OrgDetails.getIdNazione()==106) {%>none<%} else { %>block<%} %>">
        <%= CountryList.getHtmlSelect("country",OrgDetails.getIdNazione()) %>   	<font color = "red">*</font>
      </div>
      </div>	
      <% } %>
      
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="20" maxlength="16" id="codiceFiscale" name="codiceFiscale" value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>"><font id="no_cf" color="#00FF00"><b>* Inserire la variazione</b></font>    
    </td>
  </tr>
  
  <% if (OrgDetails.getNo_piva()==true || OrgDetails.getPartitaIva()==null || OrgDetails.getPartitaIva().trim().equals("")){ %>
    		<script>
	    		if(document.getElementById("divProvenienza")!=null){
	    			document.getElementById("divListaEstera").style.display="none";
	    			document.getElementById("provenienzaIT").checked="";
	    			document.getElementById("provenienzaEST").checked="";
	    			document.getElementById("provenienzaIT").disabled="disabled";
	    			document.getElementById("provenienzaEST").disabled="disabled";
	    		}
    		    document.getElementById("no_piva").checked="checked";
    			document.getElementById("partitaIva").value="";
    			document.getElementById("partitaIva").disabled="disabled";
    			document.getElementById("linkpiva").style.display="none";
    			document.getElementById("no_cf").style.display="block";
    		</script>
    <% } 
    	if (OrgDetails.getNo_piva()==false && (OrgDetails.getCodiceFiscale()==null || OrgDetails.getCodiceFiscale().trim().equals(""))) { %>
    		<script>
			    document.getElementById("no_piva").checked="";
				document.getElementById("no_cf").style.display="none";
				document.getElementById("codiceFiscale").value="";
				document.getElementById("codiceFiscale").disabled="disabled";
			</script>
    <% }%>
    
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
      <input readonly type="text" id="alertDate" name="alertDate" size="10" value="<%= toDateString(OrgDetails.getAlertDate()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].alertDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
        <%= showAttribute(request, "alertDateError") %><%= showWarningAttribute(request, "alertDateWarning") %>
      </td>
    </tr>
  </dhv:include>
        
    <dhv:include name="accounts-select-primary-contact" none="true">
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

  <dhv:include name="accounts-segment" none="true"> 
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.segment">Segment</dhv:label>
      </td>
      <td>
        <%= SegmentList.getHtmlSelect("segmentId",OrgDetails.getSegmentId()) %>
        </td>
    </tr>
  </dhv:include>
  <dhv:include name="accounts-directbill" none="true">
    <dhv:permission name="accounts-directbill-view">
      <tr class="containerBody">
          <td nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.directBill">Direct Bill</dhv:label>
        </td>
        <td>
          <dhv:permission name="accounts-directbill-edit">
            <input type="checkbox" name="directBill" <%=(OrgDetails.getDirectBill()? "CHECKED" : "") %> />
          </dhv:permission>
          <dhv:permission name="accounts-directbill-edit" none="true">
            <dhv:permission name="accounts-directbill-view">
                <input type="checkbox" name="directBill1" <%=OrgDetails.getDirectBill()?"CHECKED":""%> DISABLED />
            </dhv:permission>
            <input type="hidden" name="directBill" value="<%=OrgDetails.getDirectBill()%>" />
          </dhv:permission>
         </td>
        </tr>
      </dhv:permission>
  </dhv:include>
    
   
  </table>
  </br>
  
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
    </th>
  </tr>
 
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Codice Fiscale
      </td>
      <td>
        <input type="text" size="16" name="codiceFiscaleRappresentante" maxlength="300" value="<%= toHtmlValue(TicketDetails.getCodiceFiscaleRappresentante()) %>"><font color="#00FF00"><b>* Inserire la variazione</b></font>
      </td>
    </tr>
  </dhv:include>
    <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Cognome
      </td>
      <td>
        <input type="text" size="50" name="cognomeRappresentante" maxlength="300" value="<%= toHtmlValue(TicketDetails.getCognomeRappresentante()) %>"><font color="#00FF00"><b>* Inserire la variazione</b></font>
      </td>
    </tr>
  </dhv:include>
  
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Nome
      </td>
      <td>
        <input type="text" size="50" name="nomeRappresentante" maxlength="300" value="<%= toHtmlValue(TicketDetails.getNomeRappresentante()) %>"><font color="#00FF00"><b>* Inserire la variazione</b></font>
      </td>
    </tr>
  </dhv:include> 
 
    <tr class="containerBody">
		      <td nowrap class="formLabel">
		        Data Nascita
		      </td>
		      <td>
		        <input type="text" name="dataNascitaRappresentante" size="10" value="<%= ((OrgDetails.getDataNascitaRappresentante()!=null)?(toHtml(DateUtils.getDateAsString(OrgDetails.getDataNascitaRappresentante(),Locale.ITALY))):("")) %>" />
<a href="#" onClick="cal19.select(document.forms[0].dataNascitaRappresentante,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
		      </td>
		  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di Nascita</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtmlValue(TicketDetails.getLuogoNascitaRappresentante()) %>"><font color="#00FF00"><b>* Inserire la variazione</b></font>
    </td>
  </tr>
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Email</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="emailRappresentante" maxlength="300" value="<%= toHtmlValue(TicketDetails.getEmailRappresentante()) %>"><font color="#00FF00"><b>* Inserire la variazione</b></font>
      </td>
    </tr>
  </dhv:include>
  
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Telefono</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="telefonoRappresentante" maxlength="300" value="<%= ((TicketDetails.getTelefonoRappresentante()!=null)?(TicketDetails.getTelefonoRappresentante()):("")) %>"><font color="#00FF00"><b>* Inserire la variazione</b></font>
      </td>
    </tr>
  </dhv:include>
   <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Fax</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="fax" value="<%= TicketDetails.getFax()!=null ? (TicketDetails.getFax()):("") %>"><font color="#00FF00"><b>* Inserire la variazione</b></font>
    </td>
    
  </tr>
  <!-- fine delle modifiche -->
  

</table>
<br>

<%
  boolean noneSelected = false;
%>

   <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
        <dhv:permission name="accounts-accounts-cessazionevariazione-edit">
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="button" value="Annulla" onClick="location.href='Accounts.do?command=ViewCessazionevariazione&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="button" value="Annulla" onClick="location.href='AccountCessazionevariazione.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
        <dhv:permission name="accounts-accounts-cessazionevariazione-edit">
          <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
        </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="button" value="Annulla" onClick="location.href='Accounts.do?command=ViewCessazionevariazione&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="button" value="Annulla" onClick="location.href='AccountCessazionevariazione.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
        <%= showAttribute(request, "closedError") %>
     </dhv:evaluate>
    </dhv:evaluate>
    <input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
    <%--input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getOrgSiteId() %>" /--%>
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