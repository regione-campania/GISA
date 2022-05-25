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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.laboratorihaccp.base.Organization,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>

<%@page import="org.aspcfs.modules.laboratorihaccp.base.OrganizationAddress"%><jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="idConfezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="idTiposomministrazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipologiaList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.laboratorihaccp.base.Organization" scope="request"/>
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
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

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

<script type="text/javascript">

			function popolaAslHidden(){
			
				document.addAccount.asl.value = document.addAccount.siteId.value;
				
			}

			function setAslCallback(returnValue)
	          {
				var select = document.addAccount.address2city; //Recupero la SELECT
				//Azzero il contenuto della seconda select
	              for (var i = select.length - 1; i >= 0; i--)
	            	  select.remove(i);
	          }

			function popolaAsl()
			{

				//PopolaCombo.getValoriComboComuniProvincia(document.addAccount.siteId.value,setAslCallback) ;

				PopolaCombo.getValoriComboComuniAsl(document.addAccount.siteId.value,setAslCallback) ;

				if(document.addAccount.siteId.value==201)
					document.addAccount.address2state.value="AV";
				if(document.addAccount.siteId.value==202)
					document.addAccount.address2state.value="BN";
				if(document.addAccount.siteId.value==203)
					document.addAccount.address2state.value="CE";
				if(document.addAccount.siteId.value==204 || document.addAccount.siteId.value==205 || document.addAccount.siteId.value==206)
					document.addAccount.address2state.value="NA";
				if(document.addAccount.siteId.value==207)
					document.addAccount.address2state.value="SA";
		}




		
		/*setta l'asl in base al comune*/
		function popolaComuni()
		{
			PopolaCombo.getValoriComuniASL(document.addAccount.address2city.value,setComuniCallback) ;
			
			
	}

		function setAslCallback(returnValue)
          {
			var select = document.addAccount.address2city; //Recupero la SELECT
			//Azzero il contenuto della seconda select
              for (var i = select.length - 1; i >= 0; i--)
            	  select.remove(i);

              indici = returnValue [0];
              valori = returnValue [1];
              //Popolo la seconda Select
              for(j =0 ; j<indici.length; j++){
              //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
              var NewOpt = document.createElement('option');
              NewOpt.value = indici[j]; // Imposto il valore
              NewOpt.text = valori[j]; // Imposto il testo

              //Aggiungo l'elemento option
              try
              {
            	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
              }catch(e){
            	  select.add(NewOpt); // Funziona solo con IE
              }
              }
		          
			
	          }

		function setComuniCallback(returnValue)
          {
			var select = document.addAccount.siteId; //Recupero la SELECT
			//Azzero il contenuto della seconda select
              for (var i = select.length - 1; i >= 0; i--)
            	  select.remove(i);

              indici = returnValue [0];
              valori = returnValue [1];
              //Popolo la seconda Select
              for(j =0 ; j<indici.length; j++){
              //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
              var NewOpt = document.createElement('option');
              NewOpt.value = indici[j]; // Imposto il valore
              NewOpt.text = valori[j]; // Imposto il testo

              //Aggiungo l'elemento option
              try
              {
            	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
            	  if(document.addAccount.siteId.value==201)
  					document.addAccount.address2state.value="AV";
  				if(document.addAccount.siteId.value==202)
  					document.addAccount.address2state.value="BN";
  				if(document.addAccount.siteId.value==203)
  					document.addAccount.address2state.value="CE";
  				if(document.addAccount.siteId.value==204 || document.addAccount.siteId.value==205 || document.addAccount.siteId.value==206)
  					document.addAccount.address2state.value="NA";
  				if(document.addAccount.siteId.value==207)
  					document.addAccount.address2state.value="SA";
              }catch(e){
            	  select.add(NewOpt); // Funziona solo con IE
            	  if(document.addAccount.siteId.value==201)
  					document.addAccount.address2state.value="AV";
  				if(document.addAccount.siteId.value==202)
  					document.addAccount.address2state.value="BN";
  				if(document.addAccount.siteId.value==203)
  					document.addAccount.address2state.value="CE";
  				if(document.addAccount.siteId.value==204 || document.addAccount.siteId.value==205 || document.addAccount.siteId.value==206)
  					document.addAccount.address2state.value="NA";
  				if(document.addAccount.siteId.value==207)
  					document.addAccount.address2state.value="SA";
            	  
              }
              }
              popolaAslHidden(); 
			
	          }
</script>
<script language="JavaScript">
  indSelected = 0;
  orgSelected = 1; 
  
function doCheck(form) {

      if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  
function checkForm(form) {
    var formTest = true;
    var formTest2 = true; 
    message = "";
    alertMessage = "";
    	
   
   if (form.name.value == "" ){
  	 //alert(!isNaN(form.address2longitude.value));
  		 message += "- Si Prega di Inserire la Denominazione\r\n";
  		 formTest = false;
  }
   if (form.accountNumber.value == "" ){
	  	 //alert(!isNaN(form.address2longitude.value));
	  		 message += "- Si Prega di Inserire il Numero di Iscrizione\r\n";
	  		 formTest = false;
	}
   
   
   if (checkNullString(form.address2line1.value)) {
       message += "- Indirizzo sede operativa richiesto\r\n";
       formTest = false;
     }
	var obj1 = document.getElementById("prov12");
	if((document.getElementById("prov12").disabled == false)){
     if ((obj1.value == -1)){
       message += "- Comune sede operativa richiesto\r\n";
       formTest = false;
     }}
     if (form.address2state.value == ""){
         message += "- Provincia sede operativa richiesta\r\n";
         formTest = false;
       }
   	 
   	 if (formTest && formTest2){
   		 var test = document.addAccount.selectedList;
	     if (test != null) {
	       selectAllOptions(document.addAccount.selectedList);
	     }
	     if(alertMessage != "") {
	       confirmAction(alertMessage);
	     }
	     return true;
   	   	
  }else {
   	    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
   	    return false;
   	 }     	 
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

  function cambioStato(){
	  	var tDay = new Date();
		var tMonth = tDay.getMonth()+1;
		var tDate = tDay.getDate();
		if ( tMonth < 10) tMonth = "0"+tMonth;
		if ( tDate < 10) tDate = "0"+tDate;
		document.addAccount.dataCambioStato.value=tDate+"/"+tMonth+"/"+tDay.getFullYear();
		
		
		
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

<form name="addAccount" action="LaboratoriHACCP.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
<%
  boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }
%>
<dhv:evaluate if="<%= !popUp %>">
<%-- Trails --%>
<table class="trails" cellspacing="0" class="details">
<tr>
<td>
<a href="LaboratoriHACCP.do?command=SearchForm"><dhv:label name="">Laboratori HACCP</dhv:label></a> > 
<%-- if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="LaboratoriHACCP.do?command=SearchFar"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="LaboratoriHACCP.do?command=DashboardFar">Cruscotto</a> >
	<%}%>
<%} else {%>
<a href="LaboratoriHACCP.do?command=SearchFar"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >--%>
<a href="LaboratoriHACCP.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Scheda Laboratorio HACCP</dhv:label></a> >
<%--}--%>
<dhv:label name="accountsc.modify">Modifica Informazioni Laboratorio HACCP</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="laboratorihaccp" selected="details" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='LaboratoriHACCP.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='LaboratoriHACCP.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
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
  <% SiteList.setJsEvent(" disabled = \"disabled\" "); %>
 
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.site">Site</dhv:label>
      </td>
      <td>
        
          	<%= SiteList.getHtmlSelect("siteId",OrgDetails.getSiteId()) %>
        	<input type="hidden" name="asl" value="<%=OrgDetails.getSiteId()%>"/>
        
        <font color="red">* Campo compilato in automatico.</font>
      </td>
    </tr>
  </dhv:evaluate> 

 
 
  <tr  class="containerBody">
    <td nowrap class="formLabel" name="name" id="name">
      <dhv:label name="">Denominazione</dhv:label>
    </td>
    <td>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font>
    </td>
  </tr>
 
  
  
    <tr  class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="lab.number">Numero di Iscrizione</dhv:label>
      </td>
      <td>
        <input type="text" size="30" name="accountNumber" maxlength="30" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>"><font color="red">*</font>
      </td>
    </tr>
 
   <tr  class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="lab.number">Partita IVA</dhv:label>
      </td>
      <td>
        <input type="text" size="30" name="partita_iva" maxlength="30" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>">
      </td>
  </tr>
    
  <tr  class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="lab.dr">Direttore responsabile laboratorio</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="cognomeRappresentante" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>">
    </td>
  </tr>
    	
  <tr class="containerBody"> 
  	<td nowrap class="formLabel">Stato Laboratorio</td>
  <td>
  <table class = "noborder"><tr><td>
    	<select name="stato" >
    	    <option value="Attivo" >Attivo</option>
    		<option value="Sospeso" >Sospeso</option>
    		<option value="Revocato" >Revocato</option>
    	</select></td>
    	<td>
    	
    	<% Timestamp data_stato = new Timestamp(System.currentTimeMillis()); %>
    	In Data  
    	<input readonly type="text" id="dataCambioStato" name="dataCambioStato" size="10" 
value="<%= (OrgDetails.getDataCambioStato()==null)?(""):(getLongDate(OrgDetails.getDataCambioStato()))%>"/>
<a href="#" onClick="cal19.select(document.forms[0].dataCambioStato,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>

    	
    	
    	
    	</td>
    	
    	</tr>
    	</table>
    </td>
  </tr>
  </table>
  </br>

<%
  boolean noneSelected = false;
 
	int s=1;	
	boolean primo=false;
	int flag=0;
	
	  int acount = 0;
	  int locali=0;
	
	  Iterator anumber = OrgDetails.getAddressList().iterator();
	  
	  if(OrgDetails.getAddressList().size()<=1){
		 while (anumber.hasNext()) {
	     OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
	   %>
	   </br>
	  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	  <tr>
	    <th colspan="2">
	      <dhv:evaluate if="<%= thisAddress.getType() == 5 %>">
		    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede operativa</dhv:label></strong>
		  </dhv:evaluate>  
		 
	      <input type="hidden" name="address2id" value="<%=thisAddress.getId() %>">
	      
	    </th>
	    </tr>
	    <input type="hidden" name = "address2type" value ="<%= thisAddress.getType()%>">
	   
	 <tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
	    </td>
	    <td>
	
				<select  name="address2city" id="prov12" onchange="javascript:popolaComuni();">
				<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
	            
		 		<%
	                Vector v = OrgDetails.getComuni2();
		 			Enumeration e=v.elements();
	                while (e.hasMoreElements()) {
	                	String prov=e.nextElement().toString();
	                  
	        	%>
	                <option <%if(thisAddress.getCity() != null && thisAddress.getCity().equalsIgnoreCase( prov ) ){%>selected="selected"<%} %>  value="<%=prov%>"><%= prov %></option>	
	           <%} %>
		</select>
			<font color = "red">*</font>

	
	    </td>
	  </tr>
	 
	  <tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
	    </td>
	    <td>
	   
	      <input type="text" size="40" name="address2line1" id="address2line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate>
	      
			<font color = "red">*</font>	
	      
	    </td>
	  </tr>

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
 				<input type="text" readonly="readonly" size="2" name="address2state" maxlength="5" value="<%=thisAddress.getState()%>"> 
	    </td>
	  </tr>
	 </table>
	 <%} %>
	 </br>
	 
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Sede Legale</strong>
	    <input type="hidden" name="address1type" value="1">
	  </th>
  
 
  <tr>
	<td nowrap class="formLabel" name="province" id="province">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td> 
    
    	<input type="text" name="address1city" id="address1city" value = "" maxlength="80">
	
	</td>
  	</tr>	
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" id="address1line1" name="address1line1" maxlength="80" value="">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="">C/O</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address1line2" maxlength="80" value = "">
    </td>
  </tr>

  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address1zip" maxlength="12" value = "">
    </td>
  </tr>
  
  	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	  <input type="text" size="28" name="address1state" maxlength="80" value="">      
    </td>
  </tr>
  
</table>
	  
	  <%}else{%>
	  
	  <%
	  while (anumber.hasNext()) {
		  
	     OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
	   
	  		 ++acount;
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
		 
	      <input type="hidden" name="address<%=acount %>id" value="<%=thisAddress.getId() %>">
	      
	    </th>
	    </tr>
	    <input type="hidden" name = "address<%=acount %>type" value ="<%= thisAddress.getType()%>">
	   
	 <tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
	    </td>
	    <td>
	    
	   <%
	    
	    if(thisAddress.getType()==1 ){%>
	    
	    	<input type = "text"  name="address1city" id="address1city" value = "<%=thisAddress.getCity() %>">
	      
	    <%} else if((thisAddress.getType()==5)){ %>
	
				<select  name="address2city" id="prov12" onchange="javascript:popolaComuni();">
				<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
	            
		 		<%
	                Vector v = OrgDetails.getComuni2();
		 			Enumeration e=v.elements();
	                while (e.hasMoreElements()) {
	                	String prov=e.nextElement().toString();
	                  
	        	%>
	                <option <%if(thisAddress.getCity() != null && thisAddress.getCity().equalsIgnoreCase( prov ) ){%>selected="selected"<%} %>  value="<%=prov%>"><%= prov %></option>	
	              <%}%>
			
		</select>
				
						
	    <%}else{ %>
	        
	    	
		<%} %>
		
		<% if (thisAddress.getType() == 5) { %>
			<font color = "red">*</font>
		<% } %>	
	
	    </td>
	  </tr>
	 
	  <tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
	    </td>
	    <td>
	   
	      <input type="text" size="40" name="address<%= acount %>line1" id="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate>
	      
	      <% if (thisAddress.getType() == 5) { %>
			<font color = "red">*</font>
		 <% } %>	
	      
	    </td>
	  </tr>

	 
	  <dhv:evaluate if="<%=thisAddress.getType() == 1 %>">
	      <tr class="containerBody">
	       <td nowrap class="formLabel">
	          <dhv:label name="">C/O</dhv:label>
	       </td>
	       <td>
	        <input type="text" size="40" name="address1line2" maxlength="80" value="<%= ((thisAddress.getStreetAddressLine2()!=null) ? (thisAddress.getStreetAddressLine2()) : ("")) %>">
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
			   
		%>
	        <input type="text" size="10" name="address1state" maxlength="80" value="<%=thisAddress.getState()%>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate>
	   <%
	   			
			}
		    else if(thisAddress.getType() == 5){ 
		    %>
 						<input type="text" readonly="readonly" size="2" name="address2state" maxlength="5" value="<%=thisAddress.getState()%>"> 
 		    <%}else{ %>
		      <input type="text"  size="28" name="address<%= acount %>state"  value="<%=thisAddress.getState()%>" maxlength="80"><font color="red">*</font>
		     
		      <%} %>
	    </td>
	  </tr>
	 </table>
	 <% }
	 }%>

	 
	
	  
<br>
  <input type="hidden" name="onlyWarnings" value=<%=(OrgDetails.getOnlyWarnings()?"on":"off")%> />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='LaboratoriHACCP.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='LaboratoriHACCP.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
  <input type="hidden" name="dosubmit" value="true">
  
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
<input type="hidden" name="action1" id="action1" value="modify">
</dhv:container>
</form>