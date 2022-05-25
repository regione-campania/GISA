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
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTateIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_modify.jsp 19046 2007-02-07 18:53:43Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.allevamenti.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
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
<%@ include file="../initPage.jsp" %>
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
      return(controllaPosizione(form));
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
        document.addAccount.nameFirst.style.background = "#ffffff";
        document.addAccount.nameFirst.disabled = false;
      }
      if (elm2) {
        elm2.style.color = "#000000";
        document.addAccount.nameMiddle.style.background = "#ffffff";
        document.addAccount.nameMiddle.disabled = false;
      }
      if (elm3) {
        elm3.style.color = "#000000";
        document.addAccount.nameLast.style.background = "#ffffff";
        document.addAccount.nameLast.disabled = false;
      }
      if (elm4) {
        elm4.style.color = "#000000";
        document.addAccount.name.style.background = "#ffffff";
        document.addAccount.name.disabled = false;
      }
      if (elm5) {
        elm5.style.color = "#000000";
        document.addAccount.ticker.style.background = "#ffffff";
        document.addAccount.ticker.disabled = false;
      }
      if (elm6) {
        elm6.style.color = "#000000";
        document.addAccount.accountSize.style.background = "#ffffff";
        document.addAccount.accountSize.disabled = false;
      }
      if (elm7) {
        elm7.style.color = "#000000";
        document.addAccount.listSalutation.style.background = "#ffffff";
        document.addAccount.listSalutation.disabled = false;
      }
      if (elm8) {
        elm8.style.color = "#000000";
        document.addAccount.primaryContactId.style.background = "#ffffff";
        document.addAccount.primaryContactId.disabled = false;
      }
    }
  }
  function updateFormElements(index) {
    if (document.getElementById) {
      <dhv:include name="allevamenti-firstname" none="true">
        elm1 = document.getElementById("nameFirst1");
      </dhv:include>
      <dhv:include name="allevamenti-middlename" none="true">
        elm2 = document.getElementById("nameMiddle1");
      </dhv:include>
      <dhv:include name="allevamenti-lastname" none="true">
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
          document.addAccount.name.style.background = "#cccccc";
          document.addAccount.name.value = "";
          document.addAccount.name.disabled = true;
        }
        if (elm5) {
          elm5.style.color="#cccccc";
          document.addAccount.ticker.style.background = "#cccccc";
          document.addAccount.ticker.value = "";
          document.addAccount.ticker.disabled = true;
        }
        if (elm6) {
          elm6.style.color="#cccccc";
          document.addAccount.accountSize.style.background = "#cccccc";
          document.addAccount.accountSize.value = "";
          document.addAccount.accountSize.disabled = true;
        }
      } else {
        indSelected = 0;
        orgSelected = 1;
        resetFormElements();
        if (elm1) {
          elm1.style.color = "#cccccc";
          document.addAccount.nameFirst.style.background = "#cccccc";
          document.addAccount.nameFirst.value = "";
          document.addAccount.nameFirst.disabled = true;
        }
        if (elm2) {
          elm2.style.color = "#cccccc";  
          document.addAccount.nameMiddle.style.background = "#cccccc";
          document.addAccount.nameMiddle.value = "";
          document.addAccount.nameMiddle.disabled = true;
        }
        if (elm3) {
          elm3.style.color = "#cccccc";      
          document.addAccount.nameLast.style.background = "#cccccc";
          document.addAccount.nameLast.value = "";
          document.addAccount.nameLast.disabled = true;
        }
        if (elm7) {
          elm7.style.color = "#cccccc";
          document.addAccount.listSalutation.style.background = "#cccccc";
          document.addAccount.listSalutation.value = -1;     
          document.addAccount.listSalutation.disabled = true;
        }
        if (elm8) {
          elm8.style.color = "#cccccc";
          document.addAccount.primaryContactId.style.background = "#cccccc";
          document.addAccount.primaryContactId.selectedIndex = 0;
          document.addAccount.primaryContactId.disabled = true;
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

  function controllaPosizione(form)
  {
	
	  message ="";
	  formTest = true;
	  
	if( form.address1latitude.value > 0 && form.address1longitude.value > 0)
	{	
	  if (form.address1latitude.value > 0 ){
		    
	      			if ((form.address1latitude.value < 4431788.049190) || (form.address1latitude.value > 4593983.337630)){
                         
		       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 4431788.049190 e 4593983.337630  \r\n";
	       				 formTest = false;
	       				 
  		}
	   	 }   
	   	 
	   	 if (form.address1longitude.value > 0 ){
	      	     
	      			if (  (form.address1longitude.value < 2417159.584320) || (form.address1longitude.value > 2587487.362260)){
	      				
	       			     message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2417159.584320 e 2587487.362260  \r\n";
	       				 formTest = false;
	 
	      		}
	   	 }
	}else
	 {	  	 
	   	 
	     if (form.address0latitude.value > 0 ){
	    	
	      			if ((form.address0latitude.value < 4431788.049190) || (form.address0latitude.value > 4593983.337630)){
	       			 message += "- Il campo latitudine non verrà salvato,sono mancanti gli indirizzi  \r\n";
	       				 alert(message);
  		}
	   	 }   
	   	 
	   	 if (form.address0longitude.value > 0 ){
	      		  
	      			if (  (form.address0longitude.value < 2417159.584320) || (form.address0longitude.value > 2587487.362260)){
	       			 message += "- Il campo longitudine non verrà salvato,sono mancanti gli indirizzi  \r\n";
	                     alert(message);
	      		}
	   	 }
	 }
	   	 
	   	 if(formTest == false)
	   	 { 
		   
		   alert(message);
	   	   return false;
	   	 }
	   	 else
		   	 return true;      
	   
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
	if (form.partitaIva && form.partitaIva.value!=""){
   		if ((orgSelected == 1)  ){
   			if (isNaN(form.partitaIva.value)){
    			 message += "- Valore errato per il campo Partita IVA. Si prega di inserire solo cifre\r\n";
    				 formTest = false;
    			}		 
   		}
	 }   
      if (checkNullString(form.codiceFiscaleCorrentista.value)){
        message += "- Codice ISTAT richiesto\r\n";
        formTest = false;
      }
      
  

      if (checkNullString(form.date2.value)){
        message += "- Data inizio attività richiesta\r\n";
        formTest = false;
      }
      if (checkNullString(form.codiceFiscaleRappresentante.value) || (form.codiceFiscaleRappresentante.value == "")){
        message += "- Codicefiscale del rappresentante richiesto\r\n";
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

      if (checkNullString(form.address1line1.value)){
        message += "- Indirizzo richiesto\r\n";
        formTest = false;
      }

      if (checkNullString(form.address1city.value)){
        message += "- Comune richiesta\r\n";
        formTest = false;
      }
      if (form.address1zip.value == ""){
        message += "- Il campo CAP è richiesto \r\n";
        formTest = false;
      }
 if (form.address1state.value == -1){
        message += "- Il campo Provincia è richiesto \r\n";
        formTest = false;
      }
      
      if (form.address1latitude ){
      		if ((orgSelected == 1)  ){
      			if (isNaN(form.address1latitude.value) ||  (form.address1latitude.value < 4431788.049190) || (form.address1latitude.value > 4593983.337630)){
       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 4431788.049190 e 4593983.337630  \r\n";
       				 formTest = false;
       			}		 
      		}
   	 }   
   	 
   	 if (form.address1longitude ){
      	 //alert(!isNaN(form.address1longitude.value));
      		if ((orgSelected == 1)  ){
      			if (isNaN(form.address1longitude.value) ||  (form.address1longitude.value < 2417159.584320) || (form.address1longitude.value > 2587487.362260)){
       			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2417159.584320 e 2587487.362260  \r\n";
       				 formTest = false;
       			}		 
      		}
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
      var test = document.addAccount.selectedList;
      if (test != null) {
        selectAllOptions(document.addAccount.selectedList);
      }
      if(alertMessage != ""){
        confirmAction(alertMessage);
      }
      return true;
    }
  }
  
  function update(countryObj, stateObj, selectedValue) {
    var country = document.forms['addAccount'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=addAccount&stateObj=address"+stateObj+"state";
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

  function updateOwnerList(){
    var sel = document.forms['addAccount'].elements['siteId'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "Allevamenti.do?command=OwnerJSList&form=addAccount&widget=owner&allowBlank=false&siteId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
</script>
<body onLoad="javascript:initializeClassification();">
<form name="addAccount" action="Allevamenti.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
<%
  boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }
%>
<dhv:evaluate if="<%= !popUp %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Allevamenti.do"><dhv:label name="allevamenti.allevamenti">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="Allevamenti.do?command=Search"><dhv:label name="allevamenti.SearchResults">Search Results</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="Allevamenti.do?command=Dashboard">Cruscotto</a> >
	<%}%>
<%} else {%>
<a href="Allevamenti.do?command=Search"><dhv:label name="allevamenti.SearchResults">Search Results</dhv:label></a> >
<a href="Allevamenti.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="allevamenti.details">Account Details</dhv:label></a> >
<%}%>
<dhv:label name="allevamenti.modify">Modify Account</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="allevamenti" selected="details" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Allevamenti.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Allevamenti.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
<br />
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="allevamentiaa.allevamenti_modify.ModifyPrimaryInformation">Informazione Primaria</dhv:label></strong>
    </th>     
  </tr>
<dhv:include name="allevamenti-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti.site">Site</dhv:label>
    </td>
    <td>
      <%= SiteList.getSelectedValue(OrgDetails.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
</dhv:include>

<dhv:include name="organization.types" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="allevamenti.allevamenti.types">Account Type(s)</dhv:label> 
    </td>
  	<td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
            <dhv:evaluate if="<%=OrgDetails.getTypes().isEmpty()%>">
            <option value="-1"><dhv:label name="allevamenti.allevamenti_add.NoneSelected">None Selected</dhv:label></option>
            </dhv:evaluate>
            <dhv:evaluate if="<%=!(OrgDetails.getTypes().isEmpty())%>">
      <%
              Iterator i = OrgDetails.getTypes().iterator();
              while (i.hasNext()) {
                LookupElement thisElt = (LookupElement)i.next();
      %>
              <option value="<%=thisElt.getCode()%>"><%=thisElt.getDescription()%></option>
            <%}%>
            </dhv:evaluate>      
            </select>
          </td>
          <td valign="top">
            <input type="hidden" name="previousSelection" value="" />
            &nbsp;[<a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_allevamenti_types');"><dhv:label name="allevamenti.allevamenti_add.select">Select</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
</dhv:include>
<dhv:include name="organization.classification" none="true">
  <tr class="containerBody" style="display: none">
    <td class="formLabel">
      <dhv:label name="allevamenti.allevamenti_add.Classification">Classification</dhv:label>
    </td>
    <td>
      <input type="radio" name="form_type" value="organization" onClick="javascript:updateFormElements(0);" <%= !OrgDetails.getIsIndividual() ? " checked" : "" %>>
      <dhv:label name="allevamenti.allevamenti_add.Organization">Organization</dhv:label>
      <input type="radio" name="form_type" value="individual" onClick="javascript:updateFormElements(1);" <%= OrgDetails.getIsIndividual() ? " checked" : "" %>>
      <dhv:label name="allevamenti.allevamenti_add.Individual">Individual</dhv:label>
    </td>
  </tr>
</dhv:include>


        <tr class="containerBody">
			<td class="formLabel" nowrap>
				<dhv:label name="">Denominazione</dhv:label>
			</td>
			<td>
                <%= toHtmlValue(OrgDetails.getName()) %>
		  		<input type="hidden" size="50" maxlength="80" name="banca" value="<%= toHtmlValue(OrgDetails.getName()) %>">
			</td>
		</tr>
		
   
  <dhv:include name="allevamenti-number" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="organization.specieAlleva">Specie Allevata</dhv:label>
      </td>
      <td>
        <%= toHtmlValue(OrgDetails.getSpecieAllev()) %>
        <input type="hidden" size="20" name="specieAllev" maxlength="20" value="<%= toHtmlValue(OrgDetails.getSpecieAllev()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:evaluate if="<%= hasText(OrgDetails.getPartitaIva()) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      <dhv:label name="">Partita IVA / Codice Fiscale</dhv:label>
			</td>
			<td>
         <%= toHtml(OrgDetails.getPartitaIva()) %>&nbsp;
               <input type="hidden" size="20" maxlength="11" name="partitaIva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>">
         
			</td>
		</tr>
  </dhv:evaluate>
     
    <dhv:evaluate if="<%= (OrgDetails.getDate2() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data inizio attività</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getDate2() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
<dhv:include name="organization.contractEndDate" none="true">
<dhv:evaluate if="<%= hasText(OrgDetails.getContractEndDateString()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti.allevamenti_add.ContractEndDate">Contract End Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
</dhv:include>

   <dhv:include name="organization.alert" none="true">
    <tr class="containerBody" style="display: none">
      <td nowrap class="formLabel">
        <dhv:label name="allevamenti.allevamenti_add.AlertDate">Alert Date</dhv:label>
      </td>
      <td>
      <input readonly type="text" id="alertDate" name="alertDate" size="10" value="<%= toDateString(OrgDetails.getAlertDate()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        <%= showAttribute(request, "alertDateError") %><%= showWarningAttribute(request, "alertDateWarning") %>
      </td>
    </tr>
  </dhv:include>
        
    <dhv:include name="allevamenti-select-primary-contact" none="true">
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
              <dhv:label name="allevamenti.allevamenti_add.NoneSelected">None Selected</dhv:label>
              <input type="hidden" name="primaryContactId" id="primaryContactId" value="-1"/>
            </dhv:evaluate>  <%= showAttribute(request, "primaryContactIdError") %>
          </td>
        </tr>
    </dhv:include>

  
  <dhv:include name="organization.url" none="true">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="allevamenti.allevamenti_add.WebSiteURL">Web Site URL</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="url" value="<%= toHtmlValue(OrgDetails.getUrl()) %>">
      </td>
    </tr>
  </dhv:include>

  <dhv:include name="organization.dunsType" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="allevamenti.allevamenti_add.duns_type">DUNS Type</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="dunsType" maxlength="300" value="<%= toHtmlValue(OrgDetails.getDunsType()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.yearStarted" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="allevamenti.allevamenti_add.year_started">Year Started</dhv:label>
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
      <dhv:label name="allevamenti.allevamenti_add.Revenue">Revenue</dhv:label>
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
        <dhv:label name="allevamenti.allevamenti_add.Potential">Potential</dhv:label>
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
        <dhv:label name="allevamenti.allevamenti_add.duns_number">DUNS Number</dhv:label>
      </td>
      <td>
        <input type="text" size="15" name="dunsNumber" maxlength="30" value="<%= toHtmlValue(OrgDetails.getDunsNumber()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.businessNameTwo" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="allevamenti.allevamenti_add.business_name_two">Business Name 2</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="businessNameTwo" maxlength="300" value="<%= toHtmlValue(OrgDetails.getBusinessNameTwo()) %>">
      </td>
    </tr>
  </dhv:include>

  <dhv:include name="organization.sicDescription" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="allevamenti.allevamenti_add.sicDescription">SIC Description</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="sicDescription" maxlength="300" value="<%= toHtmlValue(OrgDetails.getSicDescription()) %>">
      </td>
    </tr>
  </dhv:include>
 
  <dhv:include name="allevamenti-segment" none="true"> 
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="allevamenti.allevamenti_add.segment">Segment</dhv:label>
      </td>
      <td>
        <%= SegmentList.getHtmlSelect("segmentId",OrgDetails.getSegmentId()) %>
        </td>
    </tr>
  </dhv:include>
  <dhv:include name="allevamenti-directbill" none="true">
    <dhv:permission name="allevamenti-directbill-view">
      <tr class="containerBody">
          <td nowrap class="formLabel">
          <dhv:label name="allevamenti.allevamenti_add.directBill">Direct Bill</dhv:label>
        </td>
        <td>
          <dhv:permission name="allevamenti-directbill-edit">
            <input type="checkbox" name="directBill" <%=(OrgDetails.getDirectBill()? "CHECKED" : "") %> />
          </dhv:permission>
          <dhv:permission name="allevamenti-directbill-edit" none="true">
            <dhv:permission name="allevamenti-directbill-view">
                <input type="checkbox" name="directBill1" <%=OrgDetails.getDirectBill()?"CHECKED":""%> DISABLED />
            </dhv:permission>
            <input type="hidden" name="directBill" value="<%=OrgDetails.getDirectBill()%>" />
          </dhv:permission>
         </td>
        </tr>
      </dhv:permission>
  </dhv:include>
    

<br>
<%
  boolean noneSelected = false;
%>
<dhv:include name="organization.phoneNumbers" none="true">
<%-- Phone Numbers --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="stabilimenti.stabilimenti_add.PhoneNumbers">Phone Numbers</dhv:label></strong>
	  </th>
  </tr>
<%  
  int icount = 0;
  Iterator inumber = OrgDetails.getPhoneNumberList().iterator();
  while (inumber.hasNext()) {
    ++icount;
    OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber)inumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.Phone">Phone</dhv:label> <%= icount %>
    </td>
    <td>
      <input type="hidden" name="phone<%= icount %>id" value="<%= thisPhoneNumber.getId() %>">
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + icount + "type", thisPhoneNumber.getType()) %>
      <input type="text" size="20" name="phone<%= icount %>number" value="<%= toHtmlValue(thisPhoneNumber.getNumber()) %>">&nbsp;<dhv:label name="stabilimenti.stabilimenti_add.ext">ext.</dhv:label>
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" value="<%= toHtmlValue(thisPhoneNumber.getExtension()) %>">
      <input type="radio" name="primaryNumber" value="<%= icount %>" <%= (thisPhoneNumber.getPrimaryNumber()) ? " checked" : "" %>><dhv:label name="stabilimenti.stabilimenti_add.primary">Primary</dhv:label>
      <input type="checkbox" name="phone<%= icount %>delete" value="on"><dhv:label name="stabilimenti.stabilimenti_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>    
<%    
  }
  ++icount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.Phone">Phone</dhv:label> <%= icount %>
    </td>
    <td>
      <%= OrgPhoneTypeList.getHtmlSelect("phone" + icount + "type", "Main") %>
      <input type="text" size="20" name="phone<%= icount %>number">&nbsp;<dhv:label name="stabilimenti.stabilimenti_add.ext">ext.</dhv:label>
      <input type="text" size="5" name="phone<%= icount %>ext" maxlength="10" />
      <input type="radio" name="primaryNumber" value="<%= icount %>"><dhv:label name="stabilimenti.stabilimenti_add.primary">Primary</dhv:label>
    </td>
  </tr>


</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="stabilimenti.stabilimenti_add.Addresses">Addresses</dhv:label></strong>
    </th>
  </tr>
<%  
  int acount = 0;
  Iterator anumber = OrgDetails.getAddressList().iterator();
  OrganizationAddress thisAddress = new OrganizationAddress();
  while (anumber.hasNext() ) {
    ++acount;
    thisAddress = (OrganizationAddress)anumber.next();
%>    
  <tr class="containerBody" style="display:none">
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    <td class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <input type="radio" name="primaryAddress" value="<%=acount%>" <%= thisAddress.getPrimaryAddress() ? " checked" : ""%>><dhv:label name="stabilimenti.stabilimenti_add.primary">Primary</dhv:label>
      <input type="checkbox" name="address<%= acount %>delete" value="on"><dhv:label name="stabilimenti.stabilimenti_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
 
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.StateProvince">State/Province</dhv:label>
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
  
 

<%} %>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="stabilimenti.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude" size="10" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLatitude()) : "") %>"></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="stabilimenti.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" size="10" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLongitude()) : "") %>"></td>
  </tr>
  <tr class="containerBody">
    <td colspan="2" style="display:none">
      &nbsp;
    </td>
  </tr> 
<%    
  
  ++acount;
  thisAddress = new OrganizationAddress();
  thisAddress.setCountry(applicationPrefs.get("SYSTEM.COUNTRY"));
%>
  <tr class="containerBody" style="display:none">
    <td class="formLabel">
     <dhv:label name="stabilimenti.stabilimenti_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", "") %>
      <input type="radio" name="primaryAddress" value="<%=acount%>"><dhv:label name="stabilimenti.stabilimenti_add.primary">Primary</dhv:label>
    </td>
  </tr>
  <tr class="containerBody" style="display:none">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody" style="display:none">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80">
    </td>
  </tr>
  
  <tr class="containerBody" style="display:none">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12">
    </td>
  </tr>
  <tr class="containerBody" style="display:none">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.StateProvince">State/Province</dhv:label>
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
  
  
  <tr class="containerBody" style="display:none">
    <td class="formLabel" nowrap><dhv:label name="stabilimenti.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude" size="10" value=""></td>
  </tr>
  <tr class="containerBody" style="display:none">
    <td class="formLabel" nowrap><dhv:label name="stabilimenti.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" size="10" value=""></td>
  </tr>

</table>
<br />
</dhv:include>
<dhv:include name="organization.emailAddresses" none="true">
<%-- Email Addresses --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="stabilimenti.stabilimenti_add.EmailAddresses">Email Addresses</dhv:label></strong>
	  </th>
  </tr>
<%  
  int ecount = 0;
  Iterator enumber = OrgDetails.getEmailAddressList().iterator();
  while (enumber.hasNext()) {
    ++ecount;
    OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress)enumber.next();
%>    
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="stabilimenti.stabilimentis_add.Email">Email</dhv:label> <%= ecount %>
    </td>
    <td>
      <input type="hidden" name="email<%= ecount %>id" value="<%= thisEmailAddress.getId() %>">
      <%= OrgEmailTypeList.getHtmlSelect("email" + ecount + "type", thisEmailAddress.getType()) %>
      <input type="text" size="40" name="email<%= ecount %>address" maxlength="255" value="<%= toHtmlValue(thisEmailAddress.getEmail()) %>">
      <input type="radio" name="primaryEmail" value="<%= ecount %>" <%= (thisEmailAddress.getPrimaryEmail()) ? " checked" : "" %>><dhv:label name="stabilimenti.stabilimenti_add.primary">Primary</dhv:label>
      <input type="checkbox" name="email<%= ecount %>delete" value="on"><dhv:label name="stabilimenti.stabilimenti_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>
<%    
  }
  ++ecount;
%>


  </table>
  <br />
  </dhv:include>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="allevamenti.allevamenti_add.AdditionalDetails">Additional Details</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="allevamenti.accountasset_include.Notes">Notes</dhv:label>
      </td>
      <td>
        <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA>
      </td>
    </tr>
  </table>
  <br />
  <input type="hidden" name="onlyWarnings" value=<%=(OrgDetails.getOnlyWarnings()?"on":"off")%> />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Allevamenti.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Allevamenti.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="statusId" value="<%=OrgDetails.getStatusId()%>">
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
</dhv:container>
</form>
