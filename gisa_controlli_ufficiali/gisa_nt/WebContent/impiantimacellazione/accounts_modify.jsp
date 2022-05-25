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
  - Version: $Id: accounts_modify.jsp 19046 2007-02-07 18:53:43Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.impiantimacellazione.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.impiantimacellazione.base.Organization" scope="request"/>
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
  
  
  		/*
       <%--- if((OrgDetails.getDunsType().equals("DIA Semplice"))&&(OrgDetails.getDunsType() != null)) { --%>
        
        elmEsIdo = document.getElementById("nameMiddle");
        elmEsIdo.style.color = "#000000";
       	document.addAccount.nameMiddle.style.background = "#ffffff";
        document.addAccount.nameMiddle.disabled = true;
                
        elmNS = document.getElementById("cin");
        elmNS.style.color = "#000000";        
        document.addAccount.cin.style.background = "#ffffff";
        document.addAccount.cin.disabled = true;        
                
        elmNSd3 = document.getElementById("date3");
        elmNSd3.style.color = "#000000";
        document.addAccount.date3.style.background = "#ffffff";
        document.addAccount.date3.disabled = true;
                
       date3 = document.getElementById("data3");
    	date3.style.visibility="hidden";

    	
      <%--}--%>
      */

      <%if (OrgDetails.getCessato()==0) {%>
		//document.addAccount.contractEndDate.type="hidden";
		document.getElementById("ciccio").style.visibility="hidden";
  <%}%>
        
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

  function updateFormElementsNew(index) {
	  
	  	if(index==1){
	  		document.getElementById("ciccio").style.visibility=""
	  		//document.addAccount.contractEndDate.type="";
	  		
	  	}
	  	else if(index==0){
	  		document.getElementById("ciccio").style.visibility="hidden"
			//document.addAccount.contractEndDate.type="hidden";
	  			  		
	  	}
	   
	    onLoad = 0;
	  }
  
  function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";

     if (checkNullString(form.codiceFiscaleRappresentante.value) || (form.codiceFiscaleRappresentante.value == "")){
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
       
    
        if (form.name){
      if ((orgSelected == 1) && (checkNullString(form.name.value))){
        message += "- Ragione Sociale richiesta\r\n";
        formTest = false;
      }
    }
    
   /* if (form.contoCorrente){
      if ((orgSelected == 1) && (checkNullString(form.contoCorrente.value))){
        message += "- Tipo Veicolo richiesto\r\n";
        formTest = false;
      }
    
    }*/
    if (form.nomeCorrentista){
      if ((orgSelected == 1) && (checkNullString(form.nomeCorrentista.value))){
        message += "- Targa Veicolo richiesta\r\n";
        formTest = false;
      }
    }
      if(form.source.value == 1){
    	if(checkNullString(form.dateI.value)){
    		message += "- Data inizio carattere temporanea richiesta\r\n";
    		formTest = false;
    	}
       	if(checkNullString(form.dateF.value)){
    		message += "- Data fine carattere temporanea richiesta\r\n";
    		formTest = false;
    	}
    }
    
     /*if (form.address1latitude && form.address1latitude.value!=""){
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
   	 }      */

    
    
    if (form.codiceCont){
      if ((orgSelected == 1) && (checkNullString(form.codiceCont.value))){
        message += "- Codice Contenitore richiesto\r\n";
        formTest = false;
      }
    }
    
    /*
      if (checkNullString(form.accountNumber.value)){
        message += "- Codice richiesto\r\n";
        formTest = false;
      }*/

    if (checkNullString(form.partitaIva.value)){
     	  if (checkNullString(form.codiceFiscale.value)){
         	message += "- Partita IVA/Codice Fiscale richiesto\r\n";
        	 formTest = false;
     	  }
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
     /* if (form.stageId.value == "-1"){
        message += "- Il Servizio Competente è richiesto \r\n";
        formTest = false;
      }*/
      
      if(checkNullString(form.address1line1.value)){
      	message += "- Indirizzo sede legale richiesto\r\n"
      	formTest = false;
      }
          
      if(form.address1city.value == -1){
      		message += "- Comune sede legale richiesta\r\n"
      		formTest = false;
      }
        if(checkNullString(form.address1state.value)){
      		message += "- Provincia sede legale  richiesta\r\n"
      		formTest = false;
      }
       /*if (checkNullString(form.address2line1.value)&&(form.address2line1.disabled==false)){
        message += "- Indirizzo sede operativa richiesto\r\n";
        formTest = false;
      }
	var obj1 = document.getElementById("prov12");
      if ((obj1.options[obj1.selectedIndex]!=-1)&&(document.getElementById("prov12").disabled == false)){
        message += "- Comune sede operativa richiesta\r\n";
        formTest = false;
      }
      
      if (checkNullString(form.nomeCorrentista.value)&&(form.nomeCorrentista.disabled==false)){
        message += "- Targa/Codice autoveicolo richiesto\r\n";
        formTest = false;
      }
      
       if (checkNullString(form.address3line1.value)&&(form.address3line1.disabled==false)){
        message += "- Indirizzo attività mobile richiesto\r\n";
        formTest = false;
      }
		var obj = document.getElementById("prov");
      if ((obj.options[obj.selectedIndex]!=-1)&&(document.getElementById("prov").disabled == false)){
        message += "- Comune attività mobile richiesta\r\n";
        formTest = false;
      }*/
     
      
   /*   if (checkNullString(form.address1latitude.value)){
        message += "- Latitudine sede legale richiesta\r\n";
        formTest = false;
      }
      if (checkNullString(form.address1longitude.value)){
        message += "- Longitudine  sede legale richiesta\r\n";
        formTest = false;
      }
     */ 
      if(form.address2line1){
      
       if(checkNullString(form.address2line1.value)){
      		message += "- Indirizzo sede operativa richiesto\r\n"
      		formTest = false;
      }
      if(form.address2city.value == -1){
      		message += "- Comune richiesta\r\n"
      		formTest = false;
      }
      
       /* if(checkNullString(form.address2state.value)){
      		message += "- Provincia sede legale richiesta\r\n"
      		formTest = false;
      }*/
      
      /*
       if (checkNullString(form.address2latitude.value)){
        message += "- Latitudine richiesta\r\n";
        formTest = false;
      }
      if (checkNullString(form.address2longitude.value)){
        message += "- Longitudine richiesta\r\n";
        formTest = false;
      }*/
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
      if (checkNullString(form.address1line1.value)){
        message += "- Indirizzo richiesto\r\n";
        formTest = false;
      }

      if (checkNullString(form.address1city.value)){
        message += "- Comune richiesta\r\n";
        formTest = false;
      }
          
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
    var url = "Requestor.do?command=OwnerJSList&form=addAccount&widget=owner&allowBlank=false&siteId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  
  function selectCarattere(){
  
 		elm1 = document.getElementById("data1");
 		elm2 = document.getElementById("data2");
 		elm3 = document.getElementById("data3");
 		elm4 = document.getElementById("data4");
 		elm5 = document.getElementById("cessazione");
 		car = document.addAccount.source.value;
 	
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
 			document.forms['addAccount'].dateI.value = ""; 
 			document.forms['addAccount'].dateF.value = ""; 
 			document.forms['addAccount'].cessazione.checked = "true";
 		}
 	
  }
</script>
<body onLoad="javascript:initializeClassification();">
<form name="addAccount" action="ImpiantiMacellazione.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
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
<a href="ImpiantiMacellazione.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="ImpiantiMacellazione.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="ImpiantiMacellazione.do?command=Dashboard">Cruscotto</a> >
	<%}%>
<%} else {%>
<a href="ImpiantiMacellazione.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ImpiantiMacellazione.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<%}%>
<dhv:label name="accountsc.modify">Modifica Impresa</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="impiantiMacellazione" selected="details" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='ImpiantiMacellazione.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='ImpiantiMacellazione.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
<br />
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_modify.ModifyPrimaryInformation">Modify Primary Information</dhv:label></strong>
    </th>     
  </tr>
<dhv:include name="impiantiMacellazione-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
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
      <dhv:label name="accounts.impiantimacellazione.types">Account Type(s)</dhv:label> 
    </td>
  	<td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
            <dhv:evaluate if="<%=OrgDetails.getTypes().isEmpty()%>">
            <option value="-1"><dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label></option>
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
            &nbsp;[<a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_account_types');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
</dhv:include>
<dhv:include name="organization.classification" none="true">
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
  
  <%--tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">TIPO D.I.A.</dhv:label>
      </td>
      <td>
        <%= toHtmlValue(OrgDetails.getDunsType()) %>
        <input type="hidden" name="dunsType" value="<%= toHtmlValue(OrgDetails.getDunsType()) %>">
      </td>
    </tr--%>
		
 
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
        
        
        
        <tr class="containerBody">
			<td class="formLabel" nowrap>
				<dhv:label name="">Denominazione</dhv:label>
			</td>
			<td>
				<input type="text" size="50" maxlength="80" name="banca" value="<%= toHtmlValue(OrgDetails.getBanca()) %>">
			</td>
		</tr>		
				  
 
    
    <%-- 
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Date4</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="date1" timestamp="<%= OrgDetails.getDate4() %>" showTimeZone="false" />
        <%= showAttribute(request, "date1Error") %>
      </td>
    </tr>
     --%>
		
		
		
  
    <dhv:include name="impiantiMacellazione-number" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="organization.accountNumber">Account Number</dhv:label>
      </td>
      <td>
        <%= toHtmlValue(OrgDetails.getAccountNumber()) %>
              <input type="hidden" size="30" maxlength="30" name="accountNumber" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>">
        
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="impiantiMacellazione-number" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="organization.codice_impresa_interno">Account Number</dhv:label>
      </td>
      <td>
        <%= toHtmlValue(OrgDetails.getCodiceImpresaInterno()) %>
              <input type="hidden" size="30" maxlength="30" name="codiceImpresaInterno" value="<%= toHtmlValue(OrgDetails.getCodiceImpresaInterno()) %>">
        
      </td>
    </tr>
  </dhv:include>
      <tr class="containerBody">
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <input type="text" size="20" maxlength="11" name="partitaIva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
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
	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="codiceFiscaleCorrentista" name="codiceFiscaleCorrentista" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleCorrentista()) %>"><font color="red">*</font>
	  &nbsp;[<a href="javascript:popLookupSelectorCustom('codiceFiscaleCorrentista','alertText','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Seleziona</dhv:label></a>]
	</td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
    </td>
    <td>
      <input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="alertText" name="alertText" value="<%= toHtmlValue(OrgDetails.getAlertText()) %>">
    </td>
  </tr>
  <tr class="containerBody">
	<td class="formLabel" nowrap>
	  <dhv:label name="">Codici Istat Secondari</dhv:label>
	</td>
	<%--<td>
	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="abi" name="abi" value="<%= toHtmlValue(OrgDetails.getAbi()) %>">
	  &nbsp;[<a href="javascript:popLookupSelectorCustom('abi','cab','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
	</td>--%>
	<td>
			  Codice 1&nbsp;&nbsp;
      		 <%--<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice1" name="codice1" value="<%= toHtmlValue(OrgDetails.getCodice1()) %>">
      		    [<a href="javascript:popLookupSelectorCustomNew('codice1', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 <div id="div_codice1" style="display: <%=(((OrgDetails.getCodice1()!= null) && (!OrgDetails.getCodice1().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 2&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice2" name="codice2" value="<%= toHtmlValue(OrgDetails.getCodice2()) %>">
      		 	[<a href="javascript:popLookupSelectorCustomNew('codice2', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 </div>
      		 <div id="div_codice2" style="display: <%=(((OrgDetails.getCodice2()!= null) && (!OrgDetails.getCodice2().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 3&nbsp;&nbsp;
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice3" name="codice3" value="<%= toHtmlValue(OrgDetails.getCodice3()) %>">
      		    [<a href="javascript:popLookupSelectorCustomNew('codice3', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 </div>
      		 <div id="div_codice3" style="display: <%=(((OrgDetails.getCodice3()!= null) && (!OrgDetails.getCodice3().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 4&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice4" name="codice4" value="<%= toHtmlValue(OrgDetails.getCodice4()) %>">
      		 	[<a href="javascript:popLookupSelectorCustomNew('codice4', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 </div>
      		 <div id="div_codice4" style="display: <%=(((OrgDetails.getCodice4()!= null) && (!OrgDetails.getCodice4().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 5&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice5" name="codice5" value="<%= toHtmlValue(OrgDetails.getCodice5()) %>">
      		    [<a href="javascript:popLookupSelectorCustomNew('codice5', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 </dir>
      		 <div id="div_codice5" style="display: <%=(((OrgDetails.getCodice5()!= null) && (!OrgDetails.getCodice5().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 6&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice6" name="codice6" value="<%= toHtmlValue(OrgDetails.getCodice6()) %>">

      		  	 [<a href="javascript:popLookupSelectorCustomNew('codice6', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 </div>
      		 <div id="div_codice6" style="display: <%=(((OrgDetails.getCodice6()!= null) && (!OrgDetails.getCodice6().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 7&nbsp;&nbsp;
      		  	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice7" name="codice7" value="<%= toHtmlValue(OrgDetails.getCodice7()) %>">
      		  	[<a href="javascript:popLookupSelectorCustomNew('codice7', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  </div>	
      		 <div id="div_codice7" style="display: <%=(((OrgDetails.getCodice7()!= null) && (!OrgDetails.getCodice7().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 8&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice8" name="codice8" value="<%= toHtmlValue(OrgDetails.getCodice8()) %>">
      		 	[<a href="javascript:popLookupSelectorCustomNew('codice8', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]	
      		 </div>
      		 <div id="div_codice8" style="display: <%=(((OrgDetails.getCodice8()!= null) && (!OrgDetails.getCodice8().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 9&nbsp;&nbsp;
         	    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice9" name="codice9" value="<%= toHtmlValue(OrgDetails.getCodice9()) %>">
         	    [<a href="javascript:popLookupSelectorCustomNew('codice9', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
         	 </div>
      		 <div id="div_codice9" style="display: <%=(((OrgDetails.getCodice9()!= null) && (!OrgDetails.getCodice9().equals("")))) ? ("block") : ("none") %>">
      		 	Codice 10
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice10" name="codice10" value="<%= toHtmlValue(OrgDetails.getCodice10()) %>">
      		    [<a href="javascript:popLookupSelectorCustomNew('codice10', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
        	 </div>--%>
	 
      		<%-- Codice 1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Codice 2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Codice 3&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Codice 4&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Codice 5&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Codice 6&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Codice 7&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Codice 8&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Codice 9&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Codice 10</br>
      		 <input style="background-color: lightgray" size="9px" readonly="readonly" type="text" size="20" id="codice1" name="codice1" value="<%= toHtmlValue(OrgDetails.getCodice1()) %>">
      		 <input style="background-color: lightgray" size="9px" readonly="readonly" type="text" size="20" id="codice2" name="codice2" value="<%= toHtmlValue(OrgDetails.getCodice2()) %>">
      		 <input style="background-color: lightgray" size="9px" readonly="readonly" type="text" size="20" id="codice3" name="codice3" value="<%= toHtmlValue(OrgDetails.getCodice3()) %>">
      		 <input style="background-color: lightgray" size="9px" readonly="readonly" type="text" size="20" id="codice4" name="codice4" value="<%= toHtmlValue(OrgDetails.getCodice4()) %>">
      		 <input style="background-color: lightgray" size="9px" readonly="readonly" type="text" size="20" id="codice5" name="codice5" value="<%= toHtmlValue(OrgDetails.getCodice5()) %>">
      		 <input style="background-color: lightgray" size="9px" readonly="readonly" type="text" size="20" id="codice6" name="codice6" value="<%= toHtmlValue(OrgDetails.getCodice6()) %>">
      		  <input style="background-color: lightgray" size="9px" readonly="readonly" type="text" size="20" id="codice7" name="codice7" value="<%= toHtmlValue(OrgDetails.getCodice7()) %>">
      		 <input style="background-color: lightgray" size="9px" readonly="readonly" type="text" size="20" id="codice8" name="codice8" value="<%= toHtmlValue(OrgDetails.getCodice8()) %>">
      		 <input style="background-color: lightgray" size="9px" readonly="readonly" type="text" size="20" id="codice9" name="codice9" value="<%= toHtmlValue(OrgDetails.getCodice9()) %>">
      		  <input style="background-color: lightgray" size="9px" readonly="readonly" type="text" size="20" id="codice10" name="codice10" value="<%= toHtmlValue(OrgDetails.getCodice10()) %>"></br >
        	 [<a href="javascript:popLookupSelectorCustomNew('codice1', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
        	 &nbsp;[<a href="javascript:popLookupSelectorCustomNew('codice2', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
        	 &nbsp;[<a href="javascript:popLookupSelectorCustomNew('codice3', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 &nbsp;&nbsp;[<a href="javascript:popLookupSelectorCustomNew('codice4', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
        	&nbsp;[<a href="javascript:popLookupSelectorCustomNew('codice5', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      &nbsp;[<a href="javascript:popLookupSelectorCustomNew('codice6', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      &nbsp; [<a href="javascript:popLookupSelectorCustomNew('codice7', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
        	 &nbsp;[<a href="javascript:popLookupSelectorCustomNew('codice8', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
       &nbsp;[<a href="javascript:popLookupSelectorCustomNew('codice9', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
        &nbsp; [<a href="javascript:popLookupSelectorCustomNew('codice10', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      --%>
      		<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice1" name="codice1" value="<%= toHtmlValue(OrgDetails.getCodice1()) %>">
      		    [<a href="javascript:popLookupSelectorCustomNew2('codice1','cod1', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
        	 <input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod1" name="cod1" value="<%= request.getAttribute("cod1") %>">
      		 <div id="div_codice1" style="display: none">
      		 	Codice 2&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice2" name="codice2" value="<%= toHtmlValue(OrgDetails.getCodice2()) %>">
      		 	[<a href="javascript:popLookupSelectorCustomNew2('codice2','cod2', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod2" name="cod2" value="">
      		 </div>
      		 <div id="div_codice2" style="display: none">
      		 	Codice 3&nbsp;&nbsp;
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice3" name="codice3" value="<%= toHtmlValue(OrgDetails.getCodice3()) %>">
      		    [<a href="javascript:popLookupSelectorCustomNew2('codice3','cod3', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod3" name="cod3" value="">
      		 </div>
      		 <div id="div_codice3" style="display: none">
      		 	Codice 4&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice4" name="codice4" value="<%= toHtmlValue(OrgDetails.getCodice4()) %>">
      		 	[<a href="javascript:popLookupSelectorCustomNew2('codice4','cod4', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod4" name="cod4" value="">
      		 </div>
      		 <div id="div_codice4" style="display: none">
      		 	Codice 5&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice5" name="codice5" value="<%= toHtmlValue(OrgDetails.getCodice5()) %>">
      		    [<a href="javascript:popLookupSelectorCustomNew2('codice5', 'cod5','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod5" name="cod5" value="">
      		 </div>
      		 <div id="div_codice5" style="display: none">
      		 	Codice 6&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice6" name="codice6" value="<%= toHtmlValue(OrgDetails.getCodice6()) %>">
      		  	 [<a href="javascript:popLookupSelectorCustomNew2('codice6','cod6', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	 <input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod6" name="cod6" value="">
      		 </div>
      		 <div id="div_codice6" style="display: none">
      		 	Codice 7&nbsp;&nbsp;
      		  	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice7" name="codice7" value="<%= toHtmlValue(OrgDetails.getCodice7()) %>">
      		  	[<a href="javascript:popLookupSelectorCustomNew2('codice7','cod7', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	<input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod7" name="cod7" value="">
      		  </div>	
      		 <div id="div_codice7" style="display: none">
      		 	Codice 8&nbsp;&nbsp;
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice8" name="codice8" value="<%= toHtmlValue(OrgDetails.getCodice8()) %>">
      		 	[<a href="javascript:popLookupSelectorCustomNew2('codice8', 'cod8','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod8" name="cod8" value="">	
      		 </div>
      		 <div id="div_codice8" style="display: none">
      		 	Codice 9&nbsp;&nbsp;
         	    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice9" name="codice9" value="<%= toHtmlValue(OrgDetails.getCodice9()) %>">
         	    [<a href="javascript:popLookupSelectorCustomNew2('codice9','cod9', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
         	    <input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod9" name="cod9" value="">
         	 </div>
      		 <div id="div_codice9" style="display: none">
      		 	Codice 10
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice10" name="codice10" value="<%= toHtmlValue(OrgDetails.getCodice10()) %>">
      		    [<a href="javascript:popLookupSelectorCustomNew2('codice10', 'cod10','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod10" name="cod10" value="">
        	 </div>
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
  		<%-- %><tr class="containerBody" id="list"  >
    <td class="formLabel" nowrap  id="tipoVeicolo1">
      <dhv:label name="">Tipo Autoveicolo</dhv:label>
    </td>
    <td>
      <input id="tipoVeicolo" type="text" size="30" maxlength="50" name="contoCorrente" value="<%= toHtmlValue(OrgDetails.getContoCorrente()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <tr class="containerBody" id="list2" >
    <td class="formLabel" nowrap id="targaVeicolo1">
      <dhv:label name="">Targa Autoveicolo</dhv:label>
    </td>
    <td>
      <input id="targaVeicolo" type="text" size="20" maxlength="10" name="nomeCorrentista" value="<%= toHtmlValue(OrgDetails.getNomeCorrentista()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>--%>
		<%}if((OrgDetails.getCodiceCont()==null)||(OrgDetails.getContoCorrente()== "")) {}else{ %>
	 <tr class="containerBody" id="list3">
    <td class="formLabel" nowrap  id="codiceCont1">
      <dhv:label name="">Codice Contenitore</dhv:label>
    </td>
    <td>
      <input id="codiceCont" type="text" size="20" maxlength="20" name="codiceCont" value="<%= toHtmlValue(OrgDetails.getCodiceCont()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <%} %>
  <%if(hasText(OrgDetails.getTipoDest())) {%>
   <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Attività</dhv:label>
    </td>
    <td>
        <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Autoveicolo")%>">
        Mobile
        </dhv:evaluate>
       
        <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Es. Commerciale")%>">
      Fissa
        </dhv:evaluate>
       <%--= toHtmlValue(OrgDetails.getTipoDest()) --%>&nbsp;
       <input type="hidden" name="tipoDest" value="<%= toHtmlValue(OrgDetails.getTipoDest()) %>">
     
    </td></tr>
    <%} %>
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
        <dhv:evaluate if="<%= OrgDetails.getSource()== 1 %>"> 
        <td style="visibility: visible;" id="data1">
        		Dal
        	</td>
        	<td style="visibility: visible;" id="data3">
           		<zeroio:dateSelect form="addAccount" field="dateI" timestamp="<%= OrgDetails.getDateI() %>" showTimeZone="false" /><font color="red">*</font>
          	</td>
       
       	 	
           	<td style="visibility: visible;" id="data2">
           		Al
           	</td>
            	<td style="visibility: visible;" id="data4">
           		<zeroio:dateSelect form="addAccount" field="dateF" timestamp="<%= OrgDetails.getDateF() %>" showTimeZone="false" /><font color="red">*</font>
           	</td>
           	<td style="visibility: visible;" id="cessazione">
           	<input type="checkbox" name="cessazione" value ="true" <%= OrgDetails.getCessazione()?"checked":"" %> /> <dhv:label name="accounts.Assetsf">Cessazione Automatica</dhv:label>
           	</td>
           	</dhv:evaluate>
    </tr>
    </table>
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
        <zeroio:dateSelect form="addAccount" field="alertDate" timestamp="<%= OrgDetails.getAlertDate() %>" timeZone="<%= OrgDetails.getAlertDateTimeZone() %>" showTimeZone="false" /><font color="red">*</font>
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
  </dhv:evaluate>
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
          <% SalutationList.setJsEvent("onchange=\"javascript:fillSalutation('addAccount');\"");%>
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
    
    
   <tr class="containerBody">
        <td name="nameMiddle1" id="nameMiddle1" nowrap class="formLabel">
          <dhv:label name="">Middle Name</dhv:label>
        </td>
        <td>
          <input type="text" size="35" name="nameMiddle" value="<%= toHtmlValue(OrgDetails.getNameMiddle()) %>">
        </td>
    </tr>   
        
   
   
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
        <td name="accountSize1" id="accountSize1" nowrap style="" class="formLabel">
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
    <dhv:include name="organization.date1" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Presentazione D.I.A./Inizio Attività</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="date1" timestamp="<%= OrgDetails.getDate1() %>" showTimeZone="false" />
        <%= showAttribute(request, "date1Error") %>
      </td>
    </tr>
    </dhv:include>
  
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data inizio attività</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="date2" timestamp="<%= OrgDetails.getDate2() %>" showTimeZone="false" /><font color="red">*</font>
      </td>
    </tr>
  	<%--tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.stage">Stage</dhv:label>
      </td>
      <td>
        <%= StageList.getHtmlSelect("stageId",OrgDetails.getStageId()) %>
        <font color = "red">*</font>
      </td>
    </tr--%>   
 <!--  
  <dhv:include name="organization.contractEndDate" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.ContractEndDate">Contract End Date</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="contractEndDate" timestamp="<%= OrgDetails.getContractEndDate() %>" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" /><%= showError(request, "dataFineAttivitaError") %>
      </td>
    </tr>
    </dhv:include>
  --> 
   <%--tr>
    <td name="accountSize1" id="accountSize1" style="" nowrap class="formLabel">
      <dhv:label name="osa.categoriaRischio"/>
    </td>
    <td class="containerBody">
      <%= OrgCategoriaRischioList.getHtmlSelect("accountSize",OrgDetails.getAccountSize()) %>
    </td>
  </tr--%>
  <tr class="containerBody">
	<td nowrap class="formLabel">
      	<dhv:label name="">Stato Impresa</dhv:label>
	</td>
	<td>
      <input type="radio" id="attivo" name="cessato" value="0" onClick="javascript:updateFormElementsNew(0);" <%=((OrgDetails.getCessato()==0) ? ("") : ("checked = checked"))%>>
      In Attività
      <input type="radio" id="cessato" name="cessato" value="1" onClick="javascript:updateFormElementsNew(1);" <%=((OrgDetails.getCessato()==1) ? ("checked = checked") : (""))%>>
      Cessato
      <input type="radio" id="cessato" name="cessato" value="2" onClick="javascript:updateFormElementsNew(1);" <%=((OrgDetails.getCessato()==2) ? ("checked = checked") : (""))%>>
      Sospeso 
      <input type="hidden" name="orgType" value="" />

      <input type="hidden" name="check" />

  <div id="ciccio" style="visibility:hidden">
      in data <zeroio:dateSelect form="addAccount" field="contractEndDate" timestamp="<%= OrgDetails.getContractEndDate() %>" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" /><%--= showError(request, "dataFineAttivitaError") --%> <%=((OrgDetails.getCessato()==1) ? (", di conseguenza  Impresa cessato non più gestibile in modifica.") : (""))%>
 </div>

 </td> 
</tr>
<%--<dhv:evaluate if="<%= OrgDetails.getOwner() == User.getUserId() || isManagerOf(pageContext, User.getUserId(), OrgDetails.getOwner()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.owner">Account Owner</dhv:label>
    </td>
    <td>
      <%= UserList.getHtmlSelect("owner", OrgDetails.getOwner() ) %>
    </td>
  </tr>
</dhv:evaluate>--%>
<!-- aggiunto da d.dauria -->
  
  </table>
  
  <table>
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr>
  </table>

  
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
       <%= TitoloList.getHtmlSelect("titoloRappresentante",OrgDetails.getTitoloRappresentante()) %></td>
  </tr>
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Codice Fiscale
      </td>
      <td>
        <input type="text" size="50" name="codiceFiscaleRappresentante" maxlength="16" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font>
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
        Cognome
      </td>
      <td>
        <input type="text" size="50" name="cognomeRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font>
      </td>
    </tr>
  </dhv:include>
  
  <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="dataNascitaRappresentante" timestamp="<%= OrgDetails.getDataNascitaRappresentante() %>"  showTimeZone="false" />
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Luogo di Nascita
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtmlValue(OrgDetails.getLuogoNascitaRappresentante()) %>">
    </td>
  </tr>
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
  
    <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Fax</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="fax" maxlength="300" value="<%= toHtmlValue(OrgDetails.getFax()) %>">
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
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
    <%
    if(thisAddress.getType()==1){%>
       <input type="text" name="address1city" id="address1city" value="<%=thisAddress.getCity() %>">
    <%}else if(thisAddress.getType()==6){ %>
       <input type="text" name="address3city" id="address3city" value="<%=thisAddress.getCity() %>"> 
     <%}else if((thisAddress.getType()==5)&&(OrgDetails.getTipoDest().equals("Es. Commerciale"))){ %>
     <select  name="address2city" id="prov12">
	<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            
	 <%
                Vector v = OrgDetails.getComuni2();
	 			Enumeration e=v.elements();
                while (e.hasMoreElements()) {
                	String prov=e.nextElement().toString();
                  
        %>
                <option <%= ( (thisAddress.getCity() != null) && thisAddress.getCity().equalsIgnoreCase( prov ) ) ? ( "selected=\"selected\"" ) : ("")%> value="<%=prov%>"><%= prov %></option>	
              <%}%>
		
	</select>
    <%}else{ %>
    
    <select  name="address<%= acount %>city">
	<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            
	 <%
                Vector v = OrgDetails.getComuni2();
	 			Enumeration e=v.elements();
                while (e.hasMoreElements()) {
                	String prov=e.nextElement().toString();
                  
        %>
                <option <%= ( (thisAddress.getCity() != null) && thisAddress.getCity().equalsIgnoreCase( prov ) ) ? ( "selected=\"selected\"" ) : ("")%> value="<%=prov%>"><%= prov %></option>	
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
   <dhv:evaluate if="<%=thisAddress.getType() == 1 %>">
      <tr class="containerBody">
       <td nowrap class="formLabel">
          <dhv:label name="">C/O</dhv:label>
       </td>
       <td>
        <input type="text" size="40" name="address1line2" maxlength="80" value="<%= thisAddress.getStreetAddressLine2() %>">
      </td>
  </tr>
  </dhv:evaluate>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>   
    
    <%
	    if(thisAddress.getType() == 1)
	    {
		    if(OrgDetails.getState()==null) 
		    {
    %>
            <input type="text" size="10" name="address<%= acount %>state" maxlength="80" value=""> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate>
   <%
   			}
		    else
		    {
	%>
        <input type="text" size="10" name="address1state" maxlength="80" value="<%=OrgDetails.getState()%>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate>
   <%
   			}
		}
	    else if(thisAddress.getType()==6) 
	    {
  %>
           <input type="text" name="address3state" id="address3state" value="<%=thisAddress.getState() %>">    	
        <%}
	    else{ 
	    %>
	    <% if (User.getSiteId() == 3) 
	    { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="BN"><font color="red">*</font>          
        <%}%>       
         <% if (User.getSiteId() == 1 || User.getSiteId() == 2) 
         { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="AV"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 4 || User.getSiteId() == 5) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="CE"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 6 || User.getSiteId() == 7 || User.getSiteId() == 8 || User.getSiteId() == 9 || User.getSiteId() == 10) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="NA"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 11 || User.getSiteId() == 12 || User.getSiteId() == 13) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="SA"><font color="red">*</font>
          <%}%>                                                   
       <%-- input type="text" readonly="readonly" size="10" name="address<%= acount %>state" maxlength="80" value="<%=OrgDetails.getState()%>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate--%>
       <%} %>
    </td>
  </tr>
  
  <%--    
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>                                                        
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry(), thisAddress.getState()) %>
      </span>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>"  value="<%= toHtmlValue(thisAddress.getState()) %>">
      </span>
    </td>
  </tr>
--%> 
  
  
  
  
  
  
  
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
 
  
  
  <%--  
    
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry()) %>
      </span>
      
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>">
      </span>
    </td>
  </tr>
 --%>
 
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
  <%--<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>                                                        
       <input type="text" readonly="readonly" size="50" name="address<%= acount %>state" maxlength="80" value="<%=OrgDetails.getState()%>">
    </td>
  </tr>--%>
  
  <%--    
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry(), thisAddress.getState()) %>
      </span>
      
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>"  value="<%= toHtmlValue(thisAddress.getState()) %>">
      </span>
    </td>
  </tr>
   --%> 
  
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
  <%--<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry()) %>
      </span>
      
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>">
      </span>
    </td>
  </tr>--%>
  
  
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
<%--<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni per Impresa</dhv:label></strong>
    </th>     
  </tr>
  
  <dhv:include name="organization.date1" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Presentazione D.I.A./Inizio Attività</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="date1" timestamp="<%= OrgDetails.getDate1() %>" showTimeZone="false" />
        <%= showAttribute(request, "date1Error") %>
      </td>
    </tr>
    </dhv:include>
  
  <tr class="containerBody">
    <td name="accountSize1" id="accountSize1" nowrap class="formLabel">
      <dhv:label name="osa.categoriaRischio"/>
    </td>
    <td>
      <%= OrgCategoriaRischioList.getHtmlSelect("accountSize",OrgDetails.getAccountSize()) %>
    </td>
  </tr>
  
  
</table>--%>


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
  <input type="hidden" name="onlyWarnings" value=<%=(OrgDetails.getOnlyWarnings()?"on":"off")%> />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if(request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='ImpiantiMacellazione.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='ImpiantiMacellazione.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="statusId" value="<%=OrgDetails.getStatusId()%>">
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
</dhv:container>
</form>
