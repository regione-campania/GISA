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
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieA" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AnimaliPropri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
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
	      message += "- Data Presentazione Autodichiarazione è richiesto\r\n";
	      formTest = false;
	    }
	    if (checkNullString(form.codiceFiscaleRappresentante.value)){
	     message += "- Codice fiscale Trasportatore è richiesto\r\n";
	     formTest = false;
	 	  
	   }
	    if (checkNullString(form.cognomeRappresentante.value)){
	     message += "- Cognome Trasportatore è richiesto\r\n";
	     formTest = false;
	   }
	   
	    if (checkNullString(form.nomeRappresentante.value)){
	     message += "- Nome Trasportatore è richiesto\r\n";
	     formTest = false;
	   }
	    
	    if (form.specieA.value == "-1"){
            message += "- Proprietario/Detentore di è richiesto\r\n";
            formTest = false;
          }           
	          if (checkNullString(form.banca.value)){
	              message += "- Abitazione/Allevamento è richiesto\r\n";
	              formTest = false;
	            }
	          if (form.address2city.value == "-1"){
	              message += "- Comune è richiesto\r\n";
	              formTest = false;
	            }
	          if (checkNullString(form.address2line1.value)){
	              message += "- Indirizzo è richiesto\r\n";
	              formTest = false;
	            }
	          if (checkNullString(form.address2state.value)){
	              message += "- Provincia è richiesta\r\n";
	              formTest = false;
	            }

	          if (form.address2latitude.value) {
	         	 //alert(!isNaN(form.address2latitude.value));
	         		if ((orgSelected == 1)  ){
	         			/*if ( isNaN(form.address2latitude.value)||(form.address2latitude.value < 4431788.049190) || (form.address3latitude.value > 4593983.337630)){
	          			 message += "- Valore errato per il campo Latitudine, il valore deve essere compresotra  4431788.049190 e 4593983.337630  (Sede Operativa)\r\n";
	          				 formTest = false;
	          			}*/
	          			if (isNaN(form.address2latitude.value) ||  (form.address2latitude.value < 39.988475) || (form.address2latitude.value > 41.503754)){
	             			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Informazioni Abitazioni/Allevamento)\r\n";
	             				 formTest = false;
	             		}			 
	         		}
	      	 }   
	      	 
	      	 if (form.address2longitude.value) {
	         	 //alert(!isNaN(form.address2longitude.value));
	         		if ((orgSelected == 1)  ){
	         			/*if (isNaN(form.address2longitude.value)||(form.address2longitude.value < 2417159.584320) || (form.address3longitude.value > 2587487.362260)){
	          			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2417159.584320 e 2587487.362260  (Sede Operativa)\r\n";
	          				 formTest = false;
	          			}*/
	          			if (isNaN(form.address2longitude.value) ||  (form.address2longitude.value < 13.7563172) || (form.address2longitude.value > 15.8032837)){
	            			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Informazioni Abitazioni/Allevamento)\r\n";
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
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='TrasportoAnimali.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='TrasportoAnimali.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
<br />
<dhv:formMessage />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="trasportoanimali.trasportoanimali_modify.ModifyPrimaryInformation">Scheda Impresa Trasporto Animali</dhv:label></strong>
    </th>     
  </tr>
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
<tr class="containerBody">
    <td nowrap class="formLabel">
      Ragione Sociale
    </td>
    <td>
      
      <input type="text" name="name" value="<%=OrgDetails.getName()%>" >
    </td>
  </tr>
<input type="hidden" name="statoImpresa" value="<%= OrgDetails.getStatoImpresa() %>">
		<!-- Data richiesta autorizzazione -->
			<tr style="display: none">
      			<td nowrap class="formLabel">
        			<dhv:label name="requestor.requestor_add.AlertDate">Alert Date</dhv:label>
      			</td>
      			<td>
      			  <input readonly type="text" id="alertDate" name="alertDate" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].alertDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      			
      			
				        <%= showAttribute(request, "alertDateError") %>
				        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      			</td>
    		</tr>
    		<input type="hidden" name="tipo_richiesta" value="tipo4">
    		
    		<tr class="containerBody">
      			<td nowrap class="formLabel">
        			<dhv:label name="">Data Presentazione Autodichiarazione</dhv:label>
      			</td>
      			<td>
      			<input readonly type="text" id="date1" name="date1" size="10" 
value="<%= (OrgDetails.getDate1()==null)?(""):(toDateasString(OrgDetails.getDate1()))%>"/>
<a href="#" onClick="cal19.select(document.forms[0].date1,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      				<font color="red">*</font>
      				
      				
        			<%= showAttribute(request, "date1Error") %>
      			</td>
    		</tr>
			<dhv:include name="accounts-name" none="true">
  			<tr class="containerBody">
    			<td nowrap class="formLabel" name="orgname1" id="orgname1">
      				<dhv:label name="">Cognome Trasportatore</dhv:label>
    			</td>
    			<td>
    				<input type="text" size="30" maxlength="50" name="cognomeRappresentante" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    		</td>
  			</tr>
	  	</dhv:include>
	  	<dhv:include name="accounts-name" none="true">
  			<tr class="containerBody">
    			<td nowrap class="formLabel" name="orgname1" id="orgname1">
      				<dhv:label name="">Nome Trasportatore</dhv:label>
    			</td>
    			<td>			  <input type="text" size="30" maxlength="50" name="nomeRappresentante" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    			</td>
  			</tr>
	  	</dhv:include>
	  	<dhv:include name="accounts-name" none="true">
  			<tr class="containerBody">
    			<td nowrap class="formLabel" name="codiceFiscale1" id="codiceFiscale1">
      				<dhv:label name="">Codice Fiscale Trasportatore</dhv:label>
    			</td>
    			<td>
    			<input type="text" size="30" maxlength="16" name="codiceFiscaleRappresentante" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>			  
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
	  	<dhv:include name="organization.specieAllev" none="true">
			    <tr class="containerBody">
			      <td nowrap class="formLabel">
			        <dhv:label name="">Proprietario/Detentore di<BR><BR>(In caso di selezione<br> multipla tenere premuto<br> il tasto Ctrl)</dhv:label>
			      </td>
			      <td>
				  <%= AnimaliPropri.getHtmlSelect("specieA",multipleSelects6)%><font color="red">*</font> <%= showAttribute(request, "nameError") %>
				      <%--<input type="text" size="20" name="specieAllev" maxlength="20" value=""><font color="red">*</font>--%>
			      </td>
			    </tr>
  			</dhv:include>
  			<tr>
  				<td  class="formLabel" nowrap>
  					<dhv:label name="">Stato</dhv:label>
  				</td>
  				<td class="containerBody">
					<%--= statoLab.getHtmlSelect("statoLab",OrgDetails.getStatoLab()) --%>
					  					      <%= toHtmlValue(OrgDetails.getStato()) %>
					
  				</td>  				
  			</tr>
	  	<%  
  int acount = 0;
  Iterator anumber = OrgDetails.getAddressList().iterator();
  while (anumber.hasNext()) {
    ++acount;
    
    OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
    if(thisAddress.getType()==1){
%> 
<input type="hidden" name="address<%=acount %>id" value="<%=thisAddress.getId() %>">
	  	<tr class="containerBody">
		    <td nowrap class="formLabel">
		      <dhv:label name="" >Comune Residenza</dhv:label>
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
                <option <%= ( (thisAddress.getCity() != null) && thisAddress.getCity().equalsIgnoreCase( prov ) ) ? ( "selected=\"selected\"" ) : ("")%> value="<%=prov%>"><%= prov %></option>	
              <%}%>
		
	</select> 
    </td>
		  </tr>
		  <tr class="containerBody">
		    <td nowrap class="formLabel">
		      <dhv:label name="">Indirizzo</dhv:label>
		    </td>
		    <td>
		      <input type="text" size="28" name="address1line1" maxlength="80" value="<%=toHtmlValue(thisAddress.getStreetAddressLine1())%>">
		    </td>
		  </tr>
		   <tr class="containerBody">
		    <td nowrap class="formLabel">
		      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
		    </td>
		    <td>
		      <input type="text" size="28" name="address1zip" maxlength="12" value="<%=toHtmlValue(thisAddress.getZip())%>">
		    </td>
		  </tr>
		  	<tr class="containerBody">
		    <td nowrap class="formLabel">
		      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
		    </td>
		    <td>
		    	  <input type="text" size="28" name="address1state" maxlength="80" value="<%=toHtmlValue(thisAddress.getState())%>">
		        </td>
		        </tr>
		        <dhv:include name="address.country" none="true">
		 
		  </dhv:include>
		  <%}} %>
		
		</table>  
		<br>
<%
  boolean noneSelected = false;

%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		  <tr>
		    <th colspan="2">
			    <strong><dhv:label name="">Informazioni Abitazione/Allevamento</dhv:label></strong>
			    <input type="hidden" name="address1type" value="1">
			  </th>
		  </tr>
		  <dhv:include name="accounts-name" none="true">
  			<tr class="containerBody">
    			<td nowrap class="formLabel" name="name1" id="name1">
      				<dhv:label name="">Abitazione/Allevamento</dhv:label>
    			</td>
    			<td>
      				<input type="text" size="20" maxlength="50"  name="banca" value="<%=OrgDetails.getBanca() %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    			</td>
  			</tr>
	  	</dhv:include>
	  		<%  
  int count = 0;
  Iterator number = OrgDetails.getAddressList().iterator();
  while (number.hasNext()) {
    ++count;
    
    OrganizationAddress thisAddress2 = (OrganizationAddress)number.next();
    if(thisAddress2.getType()==5){
%> 
<input type="hidden" name="address<%=count %>id"  id="address<%=count %>id" value="<%=thisAddress2.getId() %>">
		<tr class="containerBody">
	<td nowrap class="formLabel" name="province1" id="prov2">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
   <input type="text" name="address2city" id="address2city" value="<%= thisAddress2.getCity() %>">
    
	<%-- %>select  name="address2city" id="prov12">
	<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            
	 <%
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
                while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                  
        %>
                <option value="<%=prov4%>"><%= prov4 %></option>	
              <%}%>
		
	</select--%><font color="red">*</font> <%= showAttribute(request, "nameError") %>
 <input type="hidden" name="address2type" value="5">
	</td>
  	</tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" id="indirizzo1">
      Indirizzo
    </td>
    <td>
      <input type="text" size="40" name="address2line1"  id="address2line1" maxlength="80" value="<%= toHtmlValue(thisAddress2.getStreetAddressLine1()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
   <tr class="containerBody">
    <td nowrap class="formLabel" id="cap1">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address2zip"  id="address2zip" maxlength="12" value="<%= toHtmlValue(thisAddress2.getZip()) %>">
    </td>
  </tr>
  	<tr class="containerBody">
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    <input type="text"  size="28" name="address2state" id="address2state"  maxlength="80" value="<%= toHtmlValue(thisAddress2.getState()) %>"><font color="red">*</font>
    </td>
  </tr>
 
 

  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude2"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" id="address2latitude" name="address2latitude" size="30" value="<%=  String.valueOf(thisAddress2.getLatitude()) %>">
    	<%--input type="button"
			onclick="javascript:Geocodifica.getCoordinate( document.forms[0].address2line1.value,document.forms[0].address2city.value,document.forms[0].address2state.value,'','address2latitude','address2longitude','address2coordtype',setGeocodedLatLon)"
			value="<dhv:label name="geocodifica.calcola">Calcola</dhv:label>"
		/>
    	<input type="hidden" name="address2coordtype" id="address2coordtype" value="0" /--%>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" id="address2longitude" name="address2longitude" size="30" value="<%= String.valueOf(thisAddress2.getLongitude())  %>" ></td>
  </tr>
  <tr style="display: block">
    <td colspan="2">
    <input id="coord1button" type="button" value="Calcola Coordinate" 
	onclick="javascript:showCoordinate(document.getElementById('address2line1').value, document.forms['addAccount'].address2city.value,document.forms['addAccount'].address2state.value, document.forms['addAccount'].address2zip.value, document.forms['addAccount'].address2latitude, document.forms['addAccount'].address2longitude);" />     </td>
   </tr>
  <%}}%>
</table>
<br>
		<!-- Note aggiuntive -->
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="noteAggiuntive">
			<tr>
				<th colspan="2">
			   		<strong><dhv:label name="requestor.requestor_add.AdditionalDetails">Additional Details</dhv:label></strong>
			 	</th>
			</tr>
			<tr class="containerBody">
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
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='TrasportoAnimali.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='TrasportoAnimali.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="statusId" value="<%=OrgDetails.getStatusId()%>">
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
</dhv:container>
</form>
