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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.trasportoanimali.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>

<%@page import="java.util.Date"%><jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.trasportoanimali.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="AccountSizeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieA" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoriaTrasportata" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
	  return(checkForm(form));
  }
  function initializeClassification() {
  
  
  	
  		
       <%// if((OrgDetails.getDunsType().equals("DIA Semplice"))&&(OrgDetails.getDunsType() != null)) { %>
        	/* 
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
         */
         
      <%//}%>
    
  <% if (OrgDetails.getIsIndividual()) { %>
      indSelected = 1;
      updateFormElements(1);
  <%} else {%>
      orgSelected = 1;
      updateFormElements(0);
  <%}%>
  }

  function abilitaDes(){

		flag=0;
		arr=document.addAccount.specieA;
		for( z=0; z<arr.length; z++) {

			if(arr[z].value=="26" && arr[z].selected==true ){
				flag=1;
				
				 document.getElementById("des").style.display="block";
				}
			


		}
		if(flag==0)
			  document.getElementById("des").style.display="none";
				document.getElementById("descrizione").value = "";

		 
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
  function checkForm(form) {
	    formTest = true;
	    message = "";
	    
	    form = document.addAccount;
	    
	    if (form.siteId.value == "-1"){
	      message += "- Il campo ASL è richiesto \r\n";
	      formTest = false;
	    }

	    if (checkNullString(form.date1.value)){
	      message += "- Data Presentazione Richiesta è richiesto\r\n";
	      formTest = false;
	    }
	  
	    
	        if ((checkNullString(form.name.value))){
	          message += "- Denominazione è richiesto\r\n";
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

	    //inizio controlli js per le coordinate
	    if (form.address2latitude.value){
	      	 //alert(!isNaN(form.address2latitude.value));
	      		if ((orgSelected == 1)  ){
	      			/*if ( isNaN(form.address2latitude.value)||(form.address2latitude.value < 4431788.049190) || (form.address3latitude.value > 4593983.337630)){
	       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compresotra  4431788.049190 e 4593983.337630  (Sede Operativa)\r\n";
	       				 formTest = false;
	       			}*/
	       			if (isNaN(form.address2latitude.value) ||  (form.address2latitude.value < 39.988475) || (form.address2latitude.value > 41.503754)){
	          			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Operativa)\r\n";
	          				 formTest = false;
	          		}			 
	      		}
	   	 }   
	   	 
	   	 if (form.address2longitude.value){
	      	 //alert(!isNaN(form.address2longitude.value));
	      		if ((orgSelected == 1)  ){
	      			/*if (isNaN(form.address2longitude.value)||(form.address2longitude.value < 2417159.584320) || (form.address3longitude.value > 2587487.362260)){
	       			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2417159.584320 e 2587487.362260  (Sede Operativa)\r\n";
	       				 formTest = false;
	       			}*/
	       			if (isNaN(form.address2longitude.value) ||  (form.address2longitude.value < 13.7563172) || (form.address2longitude.value > 15.8032837)){
	         			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Operativa)\r\n";
	         				 formTest = false;
	         		}		 
	      		}
	   	 }      

	   	 if (form.address3latitude.value) {
	      	 //alert(!isNaN(form.address2latitude.value));
	      		if ((orgSelected == 1)  ){
	      			/*if ( isNaN(form.address2latitude.value)||(form.address2latitude.value < 4431788.049190) || (form.address3latitude.value > 4593983.337630)){
	       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compresotra  4431788.049190 e 4593983.337630  (Sede Operativa)\r\n";
	       				 formTest = false;
	       			}*/
	       			if (isNaN(form.address3latitude.value) ||  (form.address3latitude.value < 39.988475) || (form.address3latitude.value > 41.503754)){
	          			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Lavaggio autorizzato)\r\n";
	          				 formTest = false;
	          		}			 
	      		}
	   	 }   
	   	 
	   	 if (form.address3longitude.value) {
	      	 //alert(!isNaN(form.address2longitude.value));
	      		if ((orgSelected == 1)  ){
	      			/*if (isNaN(form.address2longitude.value)||(form.address2longitude.value < 2417159.584320) || (form.address3longitude.value > 2587487.362260)){
	       			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2417159.584320 e 2587487.362260  (Sede Operativa)\r\n";
	       				 formTest = false;
	       			}*/
	       			if (isNaN(form.address3longitude.value) ||  (form.address3longitude.value < 13.7563172) || (form.address3longitude.value > 15.8032837)){
	         			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Lavaggio autorizzato)\r\n";
	         				 formTest = false;
	         		}		 
	      		}
	   	 }  //Fine controlli js per le coordinate  
			


	   	    
	    if (checkNullString(form.partitaIva.value)){
	  	  if (checkNullString(form.codiceFiscale.value)){
	      	message += "- Partita IVA/Codice Fiscale è richiesto\r\n";
	     	 formTest = false;
	  	  }
	    }
	    if (form.categoriaTrasportata.value == "-1"){
	        message += "- Animali Trasportati è richiesto\r\n";
	        formTest = false;
	      }
	        
	      
	    if (form.specieA.value == "-1"){
	        message += "- Specie Animali Trasportati è richiesto\r\n";
	        formTest = false;
	      }
	        
	       if (checkNullString(form.codiceFiscaleRappresentante.value)){
	    	   if (checkNullString(form.partitaIva.value)){
	        message += "- Codice fiscale del rappresentante è richiesto\r\n";
	        formTest = false;
	    	   }
	      }
	       if (checkNullString(form.cognomeRappresentante.value)){
	        message += "- Cognome del rappresentante è richiesto\r\n";
	        formTest = false;
	      }
	      
	       if (checkNullString(form.nomeRappresentante.value)){
	        message += "- Nome del rappresentante è richiesto\r\n";
	        formTest = false;
	      }
	       if (checkNullString(document.getElementById("citta").value)){
	           message += "- Comune sede operativa richiesto\r\n";
	           formTest = false;
	         }          
	        
	         if (checkNullString(document.getElementById("indirizzo").value)){
	           message += "- Indirizzo sede operativa richiesta\r\n";
	           formTest = false;
	         }
	         if (checkNullString(document.getElementById("provincia").value)){
		           message += "- Provincia sede operativa richiesta\r\n";
		           formTest = false;
		         }
	        
	         
	  
	    if (formTest == false) {
	      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	      return false;
	    } else {
	      var test = document.addAccount.selectedList;
	      if (test != null) {
	        selectAllOptions(document.addAccount.selectedList);
	      }
	      if(message != "") {
	        confirmAction(message);
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
    var url = "TrasportoAnimali.do?command=OwnerJSList&form=addAccount&widget=owner&allowBlank=false&siteId=" + escape(value);
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
  
  var latitudine ;
  var longitudine ;
 function showCoordinate(address,city,prov,cap,campo_lat,campo_long)
 {
	
	   Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
	   campo_lat.value = latitudine;
	   campo_long.value =longitudine;

	}
	function setGeocodedLatLonCoordinate(value)
	{
		
		latitudine = value[1];
		longitudine = value[0];
		
	}
</script>
<body onLoad="javascript:initializeClassification();">
<form name="addAccount" action="TrasportoAnimali.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onsubmit="return doCheck(this);" method="post">
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
<a href="TrasportoAnimali.do"><dhv:label name="trasportoanimali.trasportoanimali">Trasporto Animali</dhv:label></a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="TrasportoAnimali.do?command=Search"><dhv:label name="trasportoanimali.SearchResults">Risultati Ricerca</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="TrasportoAnimali.do?command=Dashboard">Cruscotto</a> >
	<%}%>
<%} else {%>
<a href="TrasportoAnimali.do?command=Search"><dhv:label name="trasportoanimali.SearchResults">Risultati Ricerca</dhv:label></a> >
<a href="TrasportoAnimali.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="trasportoanimali.details">Scheda Trasportatore</dhv:label></a> >
<%}%>
<dhv:label name="trasportoanimali.modify">Modifica Scheda Impresa Trasporto Animali</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="trasportoanimali" selected="details" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form..value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='TrasportoAnimali.do?command=Search';this.form..value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='TrasportoAnimali.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form..value='false';" />
<% } %>
<br />
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="trasportoanimali.trasportoanimali_modify.ModifyPrimaryInformation">Scheda Impresa Trasporto Animali</dhv:label></strong>
    </th>     
  </tr>
     <input type="hidden" name="accountNumber" value="<%= OrgDetails.getAccountNumber()%>" />
<dhv:include name="accounts-sites" none="true">
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
 <input type="hidden" name="statoImpresa" value="<%= OrgDetails.getStatoImpresa() %>">
		<!-- Data richiesta autorizzazione -->
			<tr style="display: none">
      			<td nowrap class="formLabel">
        			<dhv:label name="requestor.requestor_add.AlertDate">Alert Date</dhv:label>
      			</td>
      			<td>
      			
      			  <input readonly type="text" id="alertDate" name="alertDate" size="10" 
		value="<%= toDateasString(OrgDetails.getAlertDate()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].alertDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
				        <%= showAttribute(request, "alertDateError") %>
				        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      			</td>
    		</tr>
    		<tr class="containerBody" style="display:none">
      			<td nowrap class="formLabel">
        			<dhv:label name="">Data Presentazione Richiesta</dhv:label>
      			</td>
      			<td>
      			
      			  <input readonly type="text" id="date1" name="date1" size="10" 
		value="<%= toDateasString(OrgDetails.getDate1()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataFineControllo,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
        			<%= showAttribute(request, "date1Error") %>
      			</td>
    		</tr>
			<dhv:include name="accounts-name" none="true">
  			<tr class="containerBody">
    			<td nowrap class="formLabel" name="orgname1" id="orgname1">
      				<dhv:label name="">Denominazione</dhv:label>
    			</td>
    			<td>
      				<input type="text" size="50" maxlength="80"  name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    			</td>
  			</tr>
  			</dhv:include>
  			<dhv:include name="organization.classification" none="true">
  <tr class="containerBody" style="display: none">
    <td class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.Classification">Classification</dhv:label>
    </td>
    <td>
      <input type="radio" name="form_type" value="organization" onClick="javascript:updateFormElements(0);" <%= !OrgDetails.getIsIndividual() ? " checked" : "" %>>
      <dhv:label name="stabilimenti.stabilimenti_add.Organization">Organization</dhv:label>
      <input type="radio" name="form_type" value="individual" onClick="javascript:updateFormElements(1);" <%= OrgDetails.getIsIndividual() ? " checked" : "" %>>
      <dhv:label name="stabilimenti.stabilimenti_add.Individual">Individual</dhv:label>
    </td>
  </tr>
</dhv:include>
  			<tr class="containerBody">
    			<td class="formLabel" nowrap>
      				Partita IVA
    			</td>
    			<td>
      				<input type="text" size="20" maxlength="11" name="partitaIva" value="<%= toHtmlValue( OrgDetails.getPartitaIva()) %>">
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
  			<%LookupList multipleSelects7=new LookupList();
  			HashMap<Integer, String> ListaBpi7=OrgDetails.getListaCategoria();
			Iterator<Integer> iteraKiavi7= OrgDetails.getListaCategoria().keySet().iterator();
			while(iteraKiavi7.hasNext()){
				int kiave1=iteraKiavi7.next();
				String valore1=ListaBpi7.get(kiave1);
				multipleSelects7.addItem(kiave1,valore1);
			}					
  			%>
  			<dhv:include name="organization.specieAllev" none="true">
			    <tr class="containerBody">
			      <td nowrap class="formLabel">
			        <dhv:label name="">Animali Trasportati<BR><BR>(In caso di selezione<br> multipla tenere premuto<br> il tasto Ctrl)</dhv:label>
			      </td>
			      <td>
			       <%= CategoriaTrasportata.getHtmlSelect("categoriaTrasportata",multipleSelects7) %>
			      		<font color="red">*</font>
      					
			      </td>
			    </tr>
  			</dhv:include>
  			<%LookupList multipleSelects6=new LookupList();
  			HashMap<Integer, String> ListaBpi6=OrgDetails.getListaAnimali();
			Iterator<Integer> iteraKiavi6= OrgDetails.getListaAnimali().keySet().iterator();
			while(iteraKiavi6.hasNext()){
				int kiave1=iteraKiavi6.next();
				String valore1=ListaBpi6.get(kiave1);
				multipleSelects6.addItem(kiave1,valore1);
			}					
  			%>
  			<%
      
      SpecieA.setJsEvent("onChange=abilitaDes();");
      
      %>
  			<dhv:include name="organization.specieAllev" none="true">
			    <tr class="containerBody">
			      <td nowrap class="formLabel">
			        <dhv:label name="">Specie Animali Trasportati<BR><BR>(In caso di selezione<br> multipla tenere premuto<br> il tasto Ctrl)</dhv:label>
			      </td>
			     <td>
			      <%= SpecieA.getHtmlSelect("specieA",multipleSelects6)%><font color="red">*</font> <%= showAttribute(request, "nameError") %>
			     	<div id="des" <%=((OrgDetails.getCodice10()!=null && !OrgDetails.getCodice10().equals(""))?("style=\"display:block\""):("style=\"display:none\""))%>><br><b>Descrizione:</b>
    					<br><textarea rows="8" cols="40" id="descrizione" name="codice10"><%= ((OrgDetails.getCodice10()!=null)?toHtmlValue(OrgDetails.getCodice10()):("")) %></textarea></div>
						
      				 </td>
			    </tr>
  			</dhv:include>
  			<tr>
  				<td  class="formLabel" nowrap>
  					<dhv:label name="">Stato</dhv:label>
  				</td>
  				<td class="containerBody">
  				  					      <%= toHtmlValue(OrgDetails.getStato()) %>
  				
  				</td>  				
  			</tr>
		</table>  
		<br>

		<!-- Titolare o Legale Rappresentante -->
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="rappresentanteLegale">
			<tr class="containerBody">
				<th colspan="2">
					<strong>
						<dhv:label name="">Titolare o Legale Rappresentante</dhv:label>
					</strong>
				</th>
			</tr>
			
			<!-- Codice fiscale -->
			<tr class="containerBody">
			   <td class="formLabel" nowrap>
			     Codice Fiscale
			  </td>
			  <td>
			    <input type="text" size="30" maxlength="16" name="codiceFiscaleRappresentante" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>			  </td>
			</tr>
			<input type="hidden" id="tipoD2" name="tipoDest" value="<%= request.getParameter("tipo_richiesta") %>">
			<!-- Nome -->
			<tr class="containerBody">
			  <td class="formLabel" nowrap>
			    Nome
			</td>
			<td>
			  <input type="text" size="30" maxlength="50" name="nomeRappresentante" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
			  </td>
			</tr>
			<!-- Cognome -->
			<tr class="containerBody">
				<td class="formLabel" nowrap>
				  Cognome
				</td>
				<td>
				  <input type="text" size="30" maxlength="50" name="cognomeRappresentante" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
				</td>
			</tr>
		  
		  	<!-- Data nascita -->
			<tr class="containerBody">
			    <td nowrap class="formLabel">
			    	<dhv:label name="">Data Nascita</dhv:label>
				</td>
				<td>
				 <%
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      
      
      %>
				<input  type="text" id="dataNascitaRappresentante" name="dataNascitaRappresentante" size="10" value = "<%=(OrgDetails.getDataNascitaRappresentante()  != null) ? sdf.format(new Date(OrgDetails.getDataNascitaRappresentante().getTime())):"" %>" />
		<a href="#" onClick="cal19.select(document.forms[0].dataNascitaRappresentante,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
				</td>
			</tr>
			
			<!-- Comune di nascita -->
			<tr class="containerBody">
				<td class="formLabel" nowrap>
					<dhv:label name="">Comune di nascita</dhv:label>
				</td>
				<td>
					<input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtmlValue(OrgDetails.getLuogoNascitaRappresentante()) %>">
				</td>
			</tr>
		  
			<!-- Email -->  
			<tr class="containerBody">
				<td class="formLabel" nowrap>
					<dhv:label name="">Email</dhv:label>
				</td>
				<td>
					<input type="text" size="30" maxlength="50" name="emailRappresentante" value="<%= toHtmlValue(OrgDetails.getEmailRappresentante()) %>">
				</td>
			</tr>
		  	
		  	<!-- Telefono -->
			<tr class="containerBody">
				<td class="formLabel" nowrap>
					<dhv:label name="">Telefono</dhv:label>
				</td>
				<td>
					<input type="text" size="30" maxlength="50" name="telefonoRappresentante" value="<%= toHtmlValue(OrgDetails.getTelefonoRappresentante()) %>">
				</td>
			</tr>
		  	
		  	<!-- Fax -->
			<tr class="containerBody">
				<td class="formLabel" nowrap>
					<dhv:label name="">Fax</dhv:label>
				</td>
				<td>
					<input type="text" size="30" maxlength="50" name="fax" value="<%= toHtmlValue(OrgDetails.getFax()) %>">
				</td>
			</tr>
			
		</table>
		<br>
<%
  boolean noneSelected = false;
%>
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
	  <dhv:evaluate if="<%= thisAddress.getType() == 7 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Lavaggio Autorizzato</dhv:label></strong>
	  </dhv:evaluate>  
    </th>
    <input type="hidden" name = "address<%=acount %>type" value ="<%= thisAddress.getType()%>">
    <input type="hidden" name="address<%=acount %>id" value="<%=thisAddress.getId() %>">
  </tr> 
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
    <%
    if((thisAddress.getType()==5)){%>
            <input type="text" name="address2city" id="address2city" value="<%=thisAddress.getCity() %>"><font color= 'red'>*</font>
     
    <%}else if((thisAddress.getType()==1)){ %>
    
    <select  name="address<%= acount %>city" id="address<%= acount %>city" >
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
	<%} else{%>    
      <input type="text" size="28" name="address<%= acount %>city" id="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>" >
      <%} %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
    <%if(thisAddress.getType()==5){ %>
                <input type="text" size="40" id="address2line1" name="address2line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>"> <dhv:evaluate if="<%= thisAddress.getType() != 7 && thisAddress.getType() != 1%>"><font color= "red">*</font></dhv:evaluate>
      <%}else{ %>
            <input type="text" size="40" name="address<%= acount %>line1" id="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>"> <dhv:evaluate if="<%= thisAddress.getType() != 7 && thisAddress.getType() != 1%>"><font color= "red">*</font></dhv:evaluate>
      <%} %>
    </td>
  </tr>
  <dhv:evaluate if="<%=thisAddress.getType() == 1 %>">
      <tr class="containerBody">
       <td nowrap class="formLabel">
          <dhv:label name="">C/O</dhv:label>
       </td>
       <td>
        <input type="text" size="40" name="address1line2"  id="address1line2" maxlength="80" value="<%= ((thisAddress.getStreetAddressLine2()!=null) ? (thisAddress.getStreetAddressLine2()) : ("")) %>">
      </td>
  </tr>
  </dhv:evaluate>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip"  id="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>   
    <%
    if((thisAddress.getType()==5)){%>
                   <input type="text" size="10" name="address<%= acount %>state" id="address<%= acount %>state" maxlength="80" value="<%= toHtmlValue(thisAddress.getState()) %>"> <font color= "red">*</font>
      
    <%}else{ %>
           <input type="text" size="10" name="address<%= acount %>state" id="address<%= acount %>state" maxlength="80" value="<%= toHtmlValue(thisAddress.getState()) %>">
    <%} %>
  
    </td>
  </tr>
 
  
  <%if(thisAddress.getType() == 5 || thisAddress.getType() == 7){ %>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" readonly="readonly" name="address<%= acount %>latitude" id="address<%= acount %>latitude" size="30" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLatitude()) : "") %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" readonly="readonly" name="address<%= acount %>longitude" id="address<%= acount %>longitude" size="30" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLongitude()) : "") %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate></td>
  </tr>
    <tr style="display: block">
    <td colspan="2">
    <input id="coord1button" type="button" value="Calcola Coordinate" 
	onclick="javascript:showCoordinate(document.getElementById('address<%= acount %>line1').value, document.forms['addAccount'].address<%= acount %>city.value,document.forms['addAccount'].address<%= acount %>state.value, document.forms['addAccount'].address<%= acount %>zip.value, document.forms['addAccount'].address<%= acount %>latitude, document.forms['addAccount'].address<%= acount %>longitude);" />     </td>
   </tr>
  <tr class="containerBody">
   <%} %> 

  </tr> 
  </table><br>
<%    
  }
  ++acount;
  OrganizationAddress thisAddress = new OrganizationAddress();
  thisAddress.setCountry(applicationPrefs.get("SYSTEM.COUNTRY"));
%>

 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  
 
</table>

<br />
		<!-- Responsabile -->
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="responsabile">
			<tr>
				<th colspan="2">
					<strong>
						<dhv:label name="">Responsabile Trasporto</dhv:label>
					</strong>
				</th>
			</tr>
			
			<!-- Cognome -->
			<tr>
				<td nowrap class="formLabel">
				  Cognome
				</td>
				<td>
				  <input type="text" size="40" name="codiceFiscaleCorrentista" maxlength="80" value="<%=((OrgDetails.getCodiceFiscaleCorrentista()!=null)?(OrgDetails.getCodiceFiscaleCorrentista()):(""))%>">
				</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">
				  Nome
				</td>
				<td>
				  <input type="text" size="40" name="nomeCorrentista" maxlength="80" value="<%=((OrgDetails.getNomeCorrentista()!=null)?(OrgDetails.getNomeCorrentista()):("")) %>">
				</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">
				  <dhv:label name="">Telefono</dhv:label>
				</td>
				<td>
				  <input type="text" size="40" name="contoCorrente" maxlength="80" value="<%=((OrgDetails.getContoCorrente()!=null)?(OrgDetails.getContoCorrente()):("")) %>">
				</td>
			</tr>
							
		</table>
	
		<br>
		
		<!-- Note aggiuntive -->
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="noteAggiuntive">
			<tr>
				<th colspan="2">
			   		<strong><dhv:label name="requestor.requestor_add.AdditionalDetails">Additional Details</dhv:label></strong>
			 	</th>
			</tr>
			<tr>
				<td valign="top" nowrap class="formLabel">
			    	<dhv:label name="requestor.requestor_add.Notes">Notes</dhv:label>
				</td>
				<td>
					<TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA>
				</td>
			</tr>
		</table>
  <br />
  <input type="hidden" name="onlyWarnings" value=<%=(OrgDetails.getOnlyWarnings()?"on":"off")%> />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form..value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='TrasportoAnimali.do?command=Search';this.form..value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='TrasportoAnimali.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form..value='false';" />
<% } %>
  <input type="hidden" name="" value="true">
  <input type="hidden" name="statusId" value="<%=OrgDetails.getStatusId()%>">
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
</dhv:container>
</form>
