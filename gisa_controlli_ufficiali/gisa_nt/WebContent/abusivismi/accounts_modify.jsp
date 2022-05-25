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
<%@ page import="org.aspcfs.modules.accounts.base.Comuni,java.util.*,java.text.DateFormat,org.aspcfs.modules.abusivismi.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Comuni" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.abusivismi.base.Organization" scope="request"/>
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
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation(); 
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script src="http://maps.google.com/maps?file=api&amp;v=2.x&amp;key=ABQIAAAAfqqVQ6abSmqvma-HE3AC-RSEOwKxX8QlFXServN_tg1dNm4ZmhTjhDmuhe98kaalZ9Rn31hwme5OwQ" type="text/javascript"></script>

<script language="JavaScript">
  indSelected = 0;
  orgSelected = 0; 
  function doCheck(form) {
	  return(checkForm(form));
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
  function disabilitaData(){
		 
	  if(document.getElementById("cessato2").checked == true ){
		  document.addAccount.date1.disabled = true;
	}
	  else {
		  document.addAccount.date1.disabled = false;
		   }
  }

  function disabilitaCampi(val1, val2, val3, val4, val5, val6, val7, val8){
		 
	  if(document.getElementById("cessato2").checked == true ){
		  document.addAccount.name.value = "IGNOTO";
		  document.addAccount.banca.value = "";
		  document.addAccount.partitaIva.value = "";
		  document.addAccount.categoria.value = "";
		  document.addAccount.nameFirst.value = "";
		  document.addAccount.nameLast.value = "";
		  document.addAccount.date1.value = "";
		  document.addAccount.codiceFiscale.value = "";
		  document.addAccount.name.disabled = true;
		  document.addAccount.banca.disabled = true;
		  document.addAccount.partitaIva.disabled = true;
		  document.addAccount.categoria.disabled = true;
		  document.addAccount.nameFirst.disabled = true;
		  document.addAccount.nameLast.disabled = true;
		  document.addAccount.date1.disabled = true;
		  document.addAccount.codiceFiscale.disabled = true;
	  }
	  else {
		  document.addAccount.name.disabled = false;
		  document.addAccount.banca.disabled = false;
		  document.addAccount.partitaIva.disabled = false;
		  document.addAccount.categoria.disabled = false;
		  document.addAccount.nameFirst.disabled = false;
		  document.addAccount.nameLast.disabled = false;
		  document.addAccount.date1.disabled = false;
		  document.addAccount.codiceFiscale.disabled = false;
		  document.addAccount.name.value = val1;
		  document.addAccount.banca.value = val2;
		  document.addAccount.partitaIva.value = val3;
		  document.addAccount.categoria.value = val4;
		  document.addAccount.nameFirst.value = val5;
		  document.addAccount.nameLast.value = val6;
		  document.addAccount.date1.value = val7;
		  document.addAccount.codiceFiscale.value = val8;
		  document.addAccount.date1.value="";
		  document.addAccount.name.value = "";
		  document.addAccount.banca.value = "";
		  document.addAccount.partitaIva.value = "";
		  document.addAccount.categoria.value = "";
		  document.addAccount.nameFirst.value = "";
		  document.addAccount.nameLast.value = "";
		
		  document.addAccount.codiceFiscale.value = "";
	  }
  }
  function updateFormElements(index) {
    if (document.getElementById) {
      <dhv:include name="abusivismi-firstname" none="true">
        elm1 = document.getElementById("nameFirst1");
      </dhv:include>
      <dhv:include name="abusivismi-middlename" none="true">
        elm2 = document.getElementById("nameMiddle1");
      </dhv:include>
      <dhv:include name="abusivismi-lastname" none="true">
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
    alertMessage = "";
    
    form = document.addAccount;
    
    if( document.getElementById("cessato1").checked == true){ 
    	   
    if (checkNullString(form.name.value)){
      message += "- Cognome richiesto\r\n";
     formTest = false;
    } 
    if (checkNullString(form.banca.value)){
        message += "- Nome richiesto\r\n";
        formTest = false;
      }}
    if ((form.address1latitude && form.address1latitude.value!="") ){
        
  		if ((orgSelected == 1)  ){
  			/*if ((form.address1latitude.value < 4431788.049190) || (form.address1latitude.value >4593983.337630)){
   			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 4431788.049190 e 4593983.337630  \r\n";
   				 formTest = false;
   			}*/
   			if ((form.address1latitude.value < 39.988475) || (form.address1latitude.value >41.503754)){
   	       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754  \r\n";
   	       				 formTest = false;
   	       	}
   					 
  		}
	 }   
	 if(checkNullString(form.address1latitude.value)){
		message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754  \r\n";
		 formTest = false;
	 }
	 
	 if ((form.address1latitude && form.address1latitude.value!="" )){
  	
  		if ((orgSelected == 1)  ){
  		
  			/*if ((form.address1longitude.value < 2417159.584320) || (form.address1longitude.value > 2587487.362260)){
   			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2417159.584320 e 2587487.362260  \r\n";
   				 formTest = false;
   			}*/
   			if ((form.address1longitude.value < 13.7563172) || (form.address1longitude.value > 15.8032837)){
      			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837  \r\n";
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
	

      if (checkNullString(form.address1line1.value)){
        message += "- Indirizzo richiesto\r\n";
        formTest = false;
      }

      if (checkNullString(form.address1city.value)){
        message += "- Comune richiesto\r\n";
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
      if(alertMessage != ""){
        confirmAction(alertMessage);
      }
      return formTest;
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
    var url = "Abusivismi.do?command=OwnerJSList&form=addAccount&widget=owner&allowBlank=false&siteId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }


 /* var geocoder = null;

  function initialize() {
           if (GBrowserIsCompatible()) {
               geocoder = new GClientGeocoder();
           }
  }

  function showAddress(address, lat, lng) {

       initialize();
       if (geocoder) {
           geocoder.getLatLng(
                 address,
               function (point) {
                   if (!point) {
                       alert(address + " non trovato");
                   } else {
                       lat.value = point.lat();
                       lng.value = point.lng();
                   }
               }
           );
       }
       GUnload();
  }*/
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
<body onLoad="javascript:initializeClassification();disabilitaData();">
<form name="addAccount" action="Abusivismi.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
<%
  boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }
%>
<dhv:evaluate if="<%= !popUp %>">
<table>
<tr>
<td>
    <dhv:permission name="abusivismi-abusivismi-add"><a href="Abusivismi.do?command=Add"><dhv:label name="">Aggiungi</dhv:label></a></dhv:permission>
</td>
<td>
    <dhv:permission name="abusivismi-abusivismi-view"><a href="Abusivismi.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a></dhv:permission>
</td>
</tr>
</table>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<!--<a href="AltriOperatori.do?command=DashboardScelta"><dhv:label name="">Altri Operatori</dhv:label></a> >--> 
<a href="Abusivismi.do">Operatori Abusivi</a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="Abusivismi.do?command=Search"><dhv:label name="">Risultati Ricerca</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="Abusivismi.do?command=Dashboard"><dhv:label name="">Risultati ricerca</dhv:label></a> >
	<%}%>
<%} else {%>
<a href="Abusivismi.do?command=Search"><dhv:label name="">Risultati Ricerca</dhv:label></a> >
<a href="Abusivismi.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Scheda</a> >
<%}%>
<dhv:label name="abusivismi.modify">Modify Account</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="abusivismi" selected="details" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Abusivismi.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Abusivismi.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
<br />
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="abusivismi.abusivismi_modifyaa.ModifyPrimaryInformation">Modificare l'informazione primaria</dhv:label></strong>
    </th>     
  </tr>


<%
String val1 = OrgDetails.getName();
String val2 = OrgDetails.getBanca();
String val3 = OrgDetails.getPartitaIva();
String val4 = OrgDetails.getCategoria();
String val5 = OrgDetails.getNameFirst();
String val6 = OrgDetails.getNameLast();
Timestamp val7 = OrgDetails.getDate1();
String val8 = OrgDetails.getCodiceFiscale();

%>
<tr  class="containerBody">
    <td valign="top" class="formLabel">
      Ignoto
    </td>
    <td>
    <table class="empty"><tr><td> 
     No <input type="radio" <%=(OrgDetails.getCessato()==0)?("checked='checked'"):("")%> onclick="javascript:disabilitaCampi('<%= OrgDetails.getName() %>', '<%= OrgDetails.getBanca()%>', '<%=OrgDetails.getPartitaIva() %>', '<%=OrgDetails.getCategoria() %>', '<%= OrgDetails.getNameFirst()%>', '<%= OrgDetails.getNameLast()%>', '<%= OrgDetails.getDate1()%>', '<%=OrgDetails.getCodiceFiscale()%>');" id="cessato1" name="cessato" value="0">
    </td>
    <td>
    
    Si <input type="radio" name="cessato" <%=(OrgDetails.getCessato()==1)?("checked='checked'"):("")%> onclick="javascript:disabilitaCampi('<%= OrgDetails.getName() %>', '<%= OrgDetails.getBanca()%>', '<%=OrgDetails.getPartitaIva() %>', '<%=OrgDetails.getCategoria() %>', '<%= OrgDetails.getNameFirst()%>', '<%= OrgDetails.getNameLast()%>', '<%= ((OrgDetails.getDate1()==null)? (OrgDetails.getDate1()) :(""))%>', '<%=OrgDetails.getCodiceFiscale()%>');" id="cessato2" value="1">
    </td> 
    </tr></table></td>
</tr>
     <dhv:include name="abusivismi-name" none="true">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          Cognome
        </td>
        <td>
        <%
       
        
        %>
          <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font>
       </td>
      </tr>
    </dhv:include>
	<tr class="containerBody">
			<td class="formLabel" nowrap>
				Nome
			</td>
			<td>
		
				<input type="text" size="50" maxlength="80" name="banca" <%= ((OrgDetails.getCessato()==1)?("disabled='disabled'"):("")) %> value="<%= toHtmlValue(OrgDetails.getBanca()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
			</td>
	</tr>   
   <dhv:include name="organization.contractEndDate" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.ContractEndDate">Contract End Date</dhv:label>
      </td>
      <td>
      
      <input readonly type="text" id="date1" name="date1" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].date1,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>
      
      </td>
    </tr>
    </dhv:include>  
  <tr class="containerBody">
      <td nowrap class="formLabel">
        Luogo di Nascita
      </td>
      <td>
        <input type="text" size="20" name="categoria" <%= ((OrgDetails.getCessato()==1)?("disabled='disabled'"):("")) %> maxlength="20" value="<%= toHtmlValue(OrgDetails.getCategoria()) %>">
      </td>
    </tr>
    <tr class="containerBody">
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="20" maxlength="16" <%= ((OrgDetails.getCessato()==1)?("disabled='disabled'"):("")) %> name="codiceFiscale" value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>">    
    </td>
  </tr>
    <tr class="containerBody">
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <input type="text" size="20" maxlength="11" <%= ((OrgDetails.getCessato()==1)?("disabled='disabled'"):("")) %> name="partitaIva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>">
    </td>
  </tr>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Comune Residenza
    </td>
    <td>
      <input type="text" size="28" name="nameFirst" <%= ((OrgDetails.getCessato()==1)?("disabled='disabled'"):("")) %> maxlength="80" value="<%=OrgDetails.getNameFirst() %>">
    <dhv:label name="">&nbsp;Via</dhv:label>
      <input type="text" size="28" name="nameLast" <%= ((OrgDetails.getCessato()==1)?("disabled='disabled'"):("")) %> maxlength="80" value="<%=OrgDetails.getNameLast() %>">
    </td>
  </tr>
  <tr class="containerBody">
      <td nowrap class="formLabel">
        Data Controllo
      </td>
      <td>
<input readonly type="text" id="date2" name="date2" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].date2,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>      </td>
    </tr>
</table>

<%
  boolean noneSelected = false;
%>
<dhv:include name="organization.phoneNumbers" none="true">

<br />
<dhv:include name="organization.addresses" none="true">
<%-- Addresses --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Luogo di Rilevazione Attività Abusiva</dhv:label></strong>
    </th>
  </tr>
  <dhv:include name="abusivismi-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      ASL
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
  <%
    noneSelected = false;
  %>
<%
  int acount = 0;
  Iterator anumber = OrgDetails.getAddressList().iterator();
  if(anumber.hasNext()){
  while (anumber.hasNext()) {
    acount+=1;
    OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
%>
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
      <input type ="hidden" name = "address1type" value = "7">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.City">City</dhv:label>
    </td>
    <td>
    <%
    	  if(Comuni!=null)
    	  {
    	  %>
    	  <select name ="address<%=acount %>city"> 
    	  <%
    		  for(int i = 0 ; i<Comuni.size(); i++)
    		  {
    			  Comuni c =(Comuni) Comuni.get(i);
      %>
      <option value = "<%=c.getComune() %>" <%if(c.getComune().equalsIgnoreCase(thisAddress.getCity())){ %>selected="selected" <%} %>><%=c.getComune() %></option>
      
      <%}%>
      </select>
      <% 
      
      }
      %>
     
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" id="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>"><font color="red">*</font>
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
      <% if (OrgDetails.getSiteId() == 3) 
	    { %>
          <input type="text" readonly="readonly" size="5" name="address1state" id="address1state" maxlength="80" value="BN">          
        <%}%>       
         <% if (OrgDetails.getSiteId() == 201) 
         { %>
          <input type="text" readonly="readonly" size="5" name="address1state" id="address1state"maxlength="80" value="AV">
          <%}%>
          <% if (OrgDetails.getSiteId() == 203) { %>
          <input type="text" readonly="readonly" size="5" name="address1state" id="address1state"maxlength="80" value="CE">
          <%}%>
          <% if (OrgDetails.getSiteId() == 204 || OrgDetails.getSiteId() == 205 || OrgDetails.getSiteId() == 206) { %>
          <input type="text" readonly="readonly" size="5" name="address1state" id="address1state"maxlength="80" value="NA">
          <%}%>
          <% if (OrgDetails.getSiteId() == 207) { %>
          <input type="text" readonly="readonly" size="5" name="address1state" id="address1state" maxlength="80" value="SA">
          <%}%>    
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude"  id ="address<%= acount %>latitude"  readonly="readonly" size="20" value='<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? "" + thisAddress.getLatitude() : "") %>'><font color="red">*</font></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" id="address<%= acount %>longitude" readonly="readonly" size="20" value='<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? "" + thisAddress.getLongitude() : "") %>'><font color="red">*</font></td>
  </tr>
  <tr style="display: block">
    <td colspan="2">
    <input id="coord1button" type="button" value="Calcola Coordinate" 
	onclick="javascript:showCoordinate(document.getElementById('address1line1').value, document.forms['addAccount'].address1city.value,document.forms['addAccount'].address1state.value, document.forms['addAccount'].address1zip.value, document.forms['addAccount'].address1latitude, document.forms['addAccount'].address1longitude);" />     </td>
   </tr>
  
  
<%
  }}
  ++acount;
%>
</table>
<br />
</dhv:include>
<br />
</dhv:include>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Dettagli Addizionali</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="abusivismi.accountasset_include.Notes">Notes</dhv:label>
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
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Abusivismi.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Abusivismi.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="statusId" value="<%=OrgDetails.getStatusId()%>">
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
</dhv:container>
</form>
