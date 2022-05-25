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
   - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_add.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description
  --%>
  
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.distributori.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.distributori.base.Organization" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="AccountSizeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="TipoDitributore" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCheckList.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
<script language="JavaScript" TYPE="text/javascript">
  indSelected = 0;
  orgSelected = 1;
  onLoad = 1;
   
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


    










    
    if (document.addAccount.siteId.value == "-1"){
        message += "- ASL richiesta\r\n";
        formTest = false;
     }
  
     if (checkNullString(form.name.value)){
        message += "- Ragione Sociale richiesta\r\n";
        formTest = false;
      }
     if (checkNullString(form.contractEndDate.value)){
        message += "- Data Individuazione richiesta\r\n";
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
     if (form.address1latitude && form.address1latitude.value!=""){
     
      		if ((orgSelected == 1)  ){
      			if ((form.address1latitude.value < 4431788.049190) || (form.address1latitude.value >4593983.337630)){
       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 4431788.049190 e 4593983.337630  \r\n";
       				 formTest = false;
       			}		 
      		}
   	 }   
   	 
   	 if (form.address1latitude && form.address1latitude.value!="" ){
      	
      		if ((orgSelected == 1)  ){
      		
      			if ((form.address1longitude.value < 2417159.584320) || (form.address1longitude.value > 2587487.362260)){
       			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2417159.584320 e 2587487.362260  \r\n";
       				 formTest = false;
       			}
      		}
   	 }   
  

if (form.address2latitude && form.address2latitude.value!=""){
      	 //alert(!isNaN(form.address2latitude.value));
      		if ((orgSelected == 1)  ){
      			if (isNaN(form.address2latitude.value) ||  (form.address2latitude.value < 4431788.049190) || (form.address2latitude.value >4593983.337630)){
       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 4431788.049190 e 4593983.337630  (Indirizzo 2)\r\n";
       				 formTest = false;
       			}		 
      		}
   	 }   
   	 
   	 if (form.address2longitude && form.address2longitude.value!=""){
      	 //alert(!isNaN(form.address2longitude.value));
      		if ((orgSelected == 1)  ){
      			if (isNaN(form.address2longitude.value) ||  (form.address2longitude.value < 2417159.584320) || (form.address2longitude.value > 2587487.362260)){
       			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra  2417159.584320 e 2587487.362260  (Indirizzo 2)\r\n";
       				 formTest = false;
       			}		 
      		}
   	 }      

      
   
  
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      var test = document.addAccount.selectedList;
      if (test != null) {
        selectAllOptions(document.addAccount.selectedList);
      }
      if(alertMessage != "") {
        confirmAction(alertMessage);
      }
      return true;
    }
    
  }
  
        
 function resetFormElements() {
    if (document.getElementById) {
      //elm4 = document.getElementById("orgname1");
      elm5 = document.getElementById("ticker1");
      <dhv:include name="accounts-size" none="true">
        elm6 = document.getElementById("accountSize1");
      </dhv:include>
      <dhv:include name="accounts-title" none="true">
        elm7 = document.getElementById("listSalutation1");
      </dhv:include>
      //elm4.style.color = "#000000";
     
        
      if (elm5) {
        elm5.style.color = "#000000";
        document.addAccount.ticker.style.background = "#ffffff";
        document.addAccount.ticker.disabled = false;
      }
      <dhv:include name="accounts-size" none="true">
        elm6.style.color = "#000000";
        document.addAccount.accountSize.style.background = "#ffffff";
        document.addAccount.accountSize.disabled = false;
      </dhv:include>
     
     
    }
  }
  function updateFormElements(index) {
    if (document.getElementById) {
      //elm4 = document.getElementById("orgname1");
      elm5 = document.getElementById("ticker1");
      <dhv:include name="abusivismi-size" none="true">
        elm6 = document.getElementById("accountSize1");
      </dhv:include>
      <dhv:include name="abusivismi-title" none="true">
        elm7 = document.getElementById("listSalutation1");
      </dhv:include>
      if (index == 1) {
        indSelected = 1;
        orgSelected = 0;
        resetFormElements();
        //elm4.style.color="#cccccc";
     
        document.addAccount.name.value = "";
        document.addAccount.name.disabled = true;
        if (elm5) {
          elm5.style.color="#cccccc";
          document.addAccount.ticker.style.background = "#cccccc";
          document.addAccount.ticker.value = "";
          document.addAccount.ticker.disabled = true;
        }
        <dhv:include name="abusivismi-size" none="true">
          elm6.style.color = "#cccccc";
          document.addAccount.accountSize.style.background = "#cccccc";
          document.addAccount.accountSize.value = -1;
          document.addAccount.accountSize.disabled = true;
        </dhv:include>
      } else {
        indSelected = 0;
        orgSelected = 1;
        resetFormElements();
        /*
        <dhv:include name="accounts-title" none="true">
          elm7.style.color = "#cccccc";
          document.addAccount.listSalutation.style.background = "#cccccc";
          document.addAccount.listSalutation.value = -1;
          document.addAccount.listSalutation.disabled = true;
        </dhv:include>
        */
      }
    }
    
   
    onLoad = 0;
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

  var states = new Array();
  var initStates = false;
  
  function resetStateList(country, stateObj) {
    var stateSelect = document.forms['addAccount'].elements['address'+stateObj+'state'];
    var i = 0;
    if (initStates == false) {
      for(i = stateSelect.options.length -1; i > 0 ;i--) {
        var state = new Array(stateSelect.options[i].value, stateSelect.options[i].text);
        states[states.length] = state;
      }
    }
    if (initStates == false) {
      initStates = true;
    }
    stateSelect.options.length = 0;
    for(i = states.length -1; i > 0 ;i--) {
      var state = states[i];
      if (state[0].indexOf(country) != -1 || country == label('option.none','-- None --')) {
        stateSelect.options[stateSelect.options.length] = new Option(state[1], state[0]);
      }
    }
  }
  
  function updateCopyAddress(state){
    copyAddr = document.getElementById("copyAddress");
    if (state == 0){
     copyAddr.checked = false; 
     copyAddr.disabled = true;
    } else {
     copyAddr.disabled = false;
    }
  }
</script>
<dhv:evaluate if='<%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="updateFormElements(0);">
</dhv:evaluate>
<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();ricarica();updateFormElements(1);">
</dhv:evaluate>
<form name="addAccount" action="Distributori.do?command=Insert&auto-populate=true" onSubmit="return doCheck(this);" method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate if="<%= !popUp %>">  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Distributori.do"><dhv:label name="distributori.distributori">Distributori Automatici</dhv:label></a> >
<dhv:label name="distributori.add">Aggiungi Distributore Automatico</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';">
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Distributori.do?command=Search';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<br /><br />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Ditta</dhv:label></strong>
    </th>
  </tr>
 <dhv:include name="abusivismi-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
    <tr>
      <td nowrap class="formLabel">
        ASL
      </td>
      <td>
        <dhv:evaluate if="<%= User.getSiteId() == -1 %>" >
          <%= SiteList.getHtmlSelect("siteId",OrgDetails.getSiteId()) %>
        </dhv:evaluate>
        <dhv:evaluate if="<%= User.getSiteId() != -1 %>" >
           <%= SiteList.getSelectedValue(User.getSiteId()) %>
          <input type="hidden" name="siteId" value="<%=User.getSiteId()%>" >
        </dhv:evaluate><font color="red">*</font>
      </td>
    </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
  <dhv:include name="organization.types" none="true">
    <tr>
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="abusivismi.abusivismi.types">Account Type(s)</dhv:label>
      </td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td>
              <select multiple name="selectedList" id="selectedList" size="5">
              <dhv:evaluate if="<%=OrgDetails.getTypes().isEmpty()%>">
              <option value="-1"><dhv:label name="abusivismi.abusivismi_add.NoneSelected">None Selected</dhv:label></option>
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
              &nbsp;[<a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_distributori_types');"><dhv:label name="abusivismi.abusivismi_add.select">Select</dhv:label></a>]
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="accounts-classification" none="true">
  <tr style="display: none">
    <td nowrap class="form">
      <dhv:label name="abusivismi.abusivismis_add.Classification">Classification</dhv:label>
    </td>
    <td>
      <input type="radio" name="form_type" value="organization" onClick="javascript:updateFormElements(0);" <%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) ? " checked" : "" %>>
      <dhv:label name="abusivismi.abusivismi_add.Organization">Organization</dhv:label>
      <input type="radio" name="form_type" value="individual" onClick="javascript:updateFormElements(1);" <%= "individual".equals((String) request.getParameter("form_type")) ? " checked" : "" %>>
      <dhv:label name="abusivismi.abusivismi_add.Individual">Individual</dhv:label>
    </td>
  </tr>
  </dhv:include>
  

   <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Ragione Sociale</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font><%= showAttribute(request, "nameError") %>
    </td>
  </tr>
 
 

    <dhv:include name="organization.contractEndDate" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data Inizio Attività</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="contractEndDate" timestamp="<%= OrgDetails.getContractEndDate() %>" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" /><%= showError(request, "dataFineAttivitaError") %>
      </td>
    </tr>
  </dhv:include>
    
    <tr>
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="20" maxlength="16" name="codiceFiscale" value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>">    
    </td>
  </tr>
   
  <tr>
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <input type="text" size="20" maxlength="11" name="partitaIva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>">
    </td>
  </tr>
 

  
 <dhv:include name="organization.rating" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="sales.rating">Rating</dhv:label>
      </td>
      <td>
        <%= RatingList.getHtmlSelect("rating",OrgDetails.getRating()) %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.url" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.WebSiteURL">Web Site URL</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="url" value="<%= toHtmlValue(OrgDetails.getUrl()) %>">
      </td>
    </tr>
  </dhv:include>

  <dhv:include name="organization.dunsType" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.duns_type">DUNS Type</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="dunsType" maxlength="300" value="<%= toHtmlValue(OrgDetails.getDunsType()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.yearStarted" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.year_started">Year Started</dhv:label>
      </td>
      <td>
        <input type="text" size="10" name="yearStarted" value="<%= OrgDetails.getYearStarted() > -1 ? String.valueOf(OrgDetails.getYearStarted()) : "" %>">
        <%= showAttribute(request, "yearStartedWarning") %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.employees" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="organization.employees">No. of Employees</dhv:label>
      </td>
      <td>
        <input type="text" size="10" name="employees" value='<%= OrgDetails.getEmployees() == 0 ? "" : String.valueOf(OrgDetails.getEmployees()) %>'>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.revenue" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.Revenue">Revenue</dhv:label>
      </td>
      <td>
        <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
        <input type="text" name="revenue" size="15" value="<zeroio:number value="<%= OrgDetails.getRevenue() %>" locale="<%= User.getLocale() %>" />">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.potential" none="true">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.Potential">Potential</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="potential" size="15" value="<zeroio:number value="<%= OrgDetails.getPotential() %>" locale="<%= User.getLocale() %>" />">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.ticker" none="true">
    <tr>
      <td name="ticker1" id="ticker1" nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.TickerSymbol">Ticker Symbol</dhv:label>
      </td>
      <td>
        <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="10" maxlength="10" name="ticker" value="<%= toHtmlValue(OrgDetails.getTicker()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.dunsNumber" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.duns_number">DUNS Number</dhv:label>
      </td>
      <td>
        <input type="text" size="15" name="dunsNumber" maxlength="30" value="<%= toHtmlValue(OrgDetails.getDunsNumber()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.businessNameTwo" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.business_name_two">Business Name 2</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="businessNameTwo" maxlength="300" value="<%= toHtmlValue(OrgDetails.getBusinessNameTwo()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.sicCode" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.sic_code">SIC</dhv:label>
      </td>
      <td>
        <%= SICCodeList.getHtmlSelect("sicCode",OrgDetails.getSicCode()) %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.sicDescription" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.sicDescription">SIC Description</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="sicDescription" maxlength="300" value="<%= toHtmlValue(OrgDetails.getSicDescription()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="abusivismi-directbill" none="true">
    <dhv:permission name="abusivismi-directbill-edit">
      <tr>
        <td nowrap class="formLabel">
          <dhv:label name="abusivismi.abusivismi_add.directBill">Direct Bill</dhv:label>
        </td>
        <td>
          <input type="checkbox" name="directBill" Direct Bill>
        </td>
      </tr>
    </dhv:permission>
  </dhv:include>
    
 
    
    
  <%--<dhv:include name="organization.contractEndDate" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.ContractEndDate">Contract End Date</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="contractEndDate" timestamp="<%= OrgDetails.getContractEndDate() %>" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" /><%= showError(request, "dataFineAttivitaError") %>
      </td>
    </tr>
  </dhv:include>
  <tr>
    <td name="accountSize1" id="accountSize1" nowrap class="formLabel">
      <dhv:label name="osa.categoriaRischio"/>
    </td>
    <td>
      <%= OrgCategoriaRischioList.getHtmlSelect("accountSize",OrgDetails.getAccountSize()) %>
    </td>
  </tr--%>
  
  <!--  aggiunto da d.dauria -->
  
  </table>
  
  <table>
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr>
  </table>
  
 <%--<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
      <input type="hidden" name="address1type" value="1">
    </th>
  </tr>
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Titolo</dhv:label>
    </td>
    <td>  <!-- titoloRappresentante è il nome della variabile nel bean -->
       <%= TitoloList.getHtmlSelect("titoloRappresentante",OrgDetails.getTitoloRappresentante()) %></td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="16" maxlength="16" name="codiceFiscaleRappresentante" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Nome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="nomeRappresentante" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Cognome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="cognomeRappresentante" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Email</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="emailRappresentante" value="<%= toHtmlValue(OrgDetails.getEmailRappresentante()) %>">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Telefono</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="telefonoRappresentante" value="<%= toHtmlValue(OrgDetails.getTelefonoRappresentante()) %>">
    </td>
    
  </tr>
  
  
  <!--  -->
  
  
</table>
<br>--%>
<%
  boolean noneSelected = false;
%>

<dhv:include name="organization.addresses" none="true">
<%-- Addresses --%>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
      <%--input type="hidden" name="address1type" value="1"--%>
    </th>
  </tr>
  
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Titolo</dhv:label>
    </td>
    <td>  <!-- titoloRappresentante è il nome della variabile nel bean -->
       <%= TitoloList.getHtmlSelect("titoloRappresentante",OrgDetails.getTitoloRappresentante()) %></td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="30" maxlength="16" name="codiceFiscaleRappresentante" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Nome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="nomeRappresentante" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Cognome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="cognomeRappresentante" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="dataNascitaRappresentante" showTimeZone="false" />
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      Luogo di Nascita
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" >
    </td>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Email</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="emailRappresentante" value="<%= toHtmlValue(OrgDetails.getEmailRappresentante()) %>">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Telefono</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="telefonoRappresentante" value="<%= toHtmlValue(OrgDetails.getTelefonoRappresentante()) %>">
    </td>
    
  </tr>
  
 
  
  
  
  <!--  -->
  
  
  
  
</table>
<br>



<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="abusivismi.abusivismi_add.AddressesNN">Indirizzo Sede Legale</dhv:label></strong>
    </th>
  </tr>
  <%
    noneSelected = false;
  %>
<%
  int acount = 0;
  Iterator anumber = OrgDetails.getAddressList().iterator();
  if(anumber.hasNext()){
  while (anumber.hasNext()) {
    ++acount;
    OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
%>
  <tr class="containerBody">
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    <td class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <input type="radio" name="primaryAddress" value="<%=acount%>" <%= thisAddress.getPrimaryAddress() ? " checked" : ""%>><dhv:label name="abusivismi.abusivismi_add.primary">Primary</dhv:label>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>"><font color="red"><%= acount == 1 ? "*" : ""%></font>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>"><font color="red"><%= acount == 1 ? "*" : ""%></font>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style='<%= StateSelect.hasCountry(thisAddress.getCountry())? "" : " display:none" %>'>
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry(), thisAddress.getState()) %>
      </span><font color="red"><%= acount == 1 ? "*" : ""%></font>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style='<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>'>
        <input type="text" size="25" name='<%= "address" + acount + "otherState" %>'  value="<%= toHtmlValue(thisAddress.getState()) %>">
      </span>
    </td>
  </tr>
  
  
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude" size="10" value='<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? "" + thisAddress.getLatitude() : "") %>'></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" size="10" value='<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? "" + thisAddress.getLongitude() : "") %>'></td>
  </tr>
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr>
<%
  }
  ++acount;
%>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type","") %>
      <input type="radio" name="primaryAddress" value="<%=acount%>"><dhv:label name="abusivismi.abusivismi_add.primary">Primary</dhv:label>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80"><font color="red"><%= acount == 1 ? "*" : ""%></font>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80"><font color="red"><%= acount == 1 ? "*" : ""%></font>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>                                                         
      <span name="state1<%= acount %>" ID="state1<%= acount %>"  style='<%= StateSelect.hasCountry(applicationPrefs.get("SYSTEM.COUNTRY")) ? "" : " display:none" %>'>
        <%= StateSelect.getHtmlSelect("address" + acount + "state", applicationPrefs.get("SYSTEM.COUNTRY")) %>
      </span><font color="red"><%= acount == 1 ? "*" : ""%></font>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>"  style='<%= !StateSelect.hasCountry(applicationPrefs.get("SYSTEM.COUNTRY")) ? "" : " display:none" %>'>
        <input type="text" size="25" name='<%= "address" + acount + "otherState" %>'>
      </span>
    </td>
  </tr>
  
  <!--
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.county">County</dhv:label></td>
    <td><input type="text" name="address<%= acount %>county" size="28" maxlenth="80"></td>
  </tr>
  -->
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude" size="10" value=""></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" size="10" value=""></td>
  </tr>
<%
  }else{
    noneSelected = true;
  }
%>


<dhv:evaluate if="<%= noneSelected %>">
  <%
    Iterator addressTypeIterator = OrgAddressTypeList.iterator();
  %>
  <tr style="display:none">
    <td class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address1type", (addressTypeIterator.hasNext()?((LookupElement)addressTypeIterator.next()).getDescription():"")) %>
      <input type="radio" name="primaryAddress" value="1"><dhv:label name="abusivismi.abusivismi_add.primary">Primary</dhv:label>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address1city" maxlength="80">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address1line1" maxlength="80">
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address1zip" maxlength="12">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state11" ID="state11" style='<%= StateSelect.hasCountry(applicationPrefs.get("SYSTEM.COUNTRY")) ? "" : " display:none" %>'>
        <%= StateSelect.getHtmlSelect("address1state",applicationPrefs.get("SYSTEM.COUNTRY")) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state21" ID="state21" style='<%= !StateSelect.hasCountry(applicationPrefs.get("SYSTEM.COUNTRY")) ? "" : " display:none" %>'>
        <input type="text" size="25" name='<%= "address1otherState" %>' >
      </span>
    </td>
  </tr>
  
  
  <!--  
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.county">County</dhv:label></td>
    <td><input type="text" name="address1county" size="28" maxlenth="80"></td>
  </tr>
  -->
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address1latitude" size="10" value=""></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address1longitude" size="10" value=""></td>
  </tr>
  <tr style="display:none">
    <td colspan="2">&nbsp;</td>
  </tr>
  <dhv:include name="abusivismi-address2" none="false">
    <tr>
      <td class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.Type">Type</dhv:label>
      </td>
      <td>
        <%= OrgAddressTypeList.getHtmlSelect("address2type", (addressTypeIterator.hasNext()?((LookupElement)addressTypeIterator.next()).getDescription():"")) %>
        <input type="radio" name="primaryAddress" value="2"><dhv:label name="abusivismi.abusivismi_add.primary">Primary</dhv:label>
      </td>
    </tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.City">City</dhv:label>
      </td>
      <td>
        <input type="text" size="28" name="address2city" maxlength="80">
      </td>
    </tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.AddressLine1">Address Line 1</dhv:label>
      </td>
      <td>
        <input type="text" size="40" name="address2line1" maxlength="80">
      </td>
    </tr>
    <!--  
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.AddressLine2">Address Line 2</dhv:label>
      </td>
      <td>
        <input type="text" size="40" name="address2line2" maxlength="80">
      </td>
    </tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.AddressLine3">Address Line 3</dhv:label>
      </td>
      <td>
        <input type="text" size="40" name="address2line3" maxlength="80">
      </td>
    </tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.AddressLine4">Address Line 4</dhv:label>
      </td>
      <td>
        <input type="text" size="40" name="address2line4" maxlength="80">
      </td>
    </tr>
    -->
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.ZipPostalCode">Zip/Postal Code</dhv:label>
      </td>
      <td>
        <input type="text" size="28" name="address2zip" maxlength="12">
      </td>
    </tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.StateProvince">State/Province</dhv:label>
      </td>
      <td>
        <span name="state12" ID="state12" style='<%= StateSelect.hasCountry(applicationPrefs.get("SYSTEM.COUNTRY")) ? "" : " display:none" %>'>
          <%= StateSelect.getHtmlSelect("address2state",applicationPrefs.get("SYSTEM.COUNTRY")) %>
        </span>
        <%-- If selected country is not US/Canada use textfield --%>
        <span name="state22" ID="state22" style='<%= !StateSelect.hasCountry(applicationPrefs.get("SYSTEM.COUNTRY")) ? "" : " display:none" %>'>
          <input type="text" size="25" name='<%= "address2otherState" %>' >
        </span>
      </td>
    </tr>
    
   
    <!--  
	  <tr class="containerBody">
	    <td class="formLabel" nowrap><dhv:label name="accounts.address.county">County</dhv:label></td>
	    <td><input type="text" name="address2county" size="28" maxlenth="80"></td>
	  </tr>
	  -->
	  <tr class="containerBody">
	    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.latitude">Latitude</dhv:label></td>
	    <td><input type="text" name="address2latitude" size="10" value=""></td>
	  </tr>
	  <tr class="containerBody">
	    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.longitude">Longitude</dhv:label></td>
	    <td><input type="text" name="address2longitude" size="10" value=""></td>
	  </tr>
  </dhv:include>
</dhv:evaluate>



</table>
<br />






	







</dhv:include>


</br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Dettagli Addizionali</strong>
	  </th>
  </tr>
  <tr>
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.Notes">Notes</dhv:label>
    </td>
    <td><TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA></td>
  </tr>
</table>
<dhv:evaluate if="<%= !popUp %>">  
<br /><%-- %>
<dhv:label name="abusivismi.radio.header">Where do you want to go after this action is complete?</dhv:label><br />
<input type="radio" name="target" value="return" onClick="javascript:updateCopyAddress(0)" <%= request.getParameter("target") == null || "return".equals(request.getParameter("target")) ? " checked" : "" %> /> <dhv:label name="abusivismi.radio.details">View this account's details</dhv:label><br />
<input type="radio" name="target" value="add_contact" onClick="javascript:updateCopyAddress(1)" <%= "add_contact".equals(request.getParameter("target")) ? " checked" : "" %> /> <dhv:label name="abusivismi.radio.addContact">Add a contact to this account</dhv:label>
<input type="checkbox" id="copyAddress" name="copyAddress" value="true"  disabled="true" /><dhv:label name="abusivismi.abusivismi_add.copyEmailPhoneAddress">Copy email, phone and postal address</dhv:label>--%>
</dhv:evaluate>  
<br />
<input type="hidden" name="onlyWarnings" value='<%=(OrgDetails.getOnlyWarnings()?"on":"off")%>' />
<%= addHiddenParams(request, "actionSource|popup") %>
<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';" />
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Distributori.do?command=Search';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true" />
</form>
</body>