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
  - Version: $Id: accounts_add.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description
  --%>
  
  <!-- Prova svn -->

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.abusivismi.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*"%>

<%@page import="org.aspcfs.modules.accounts.base.Comuni"%><jsp:useBean
	id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="OrgCategoriaRischioList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgPhoneTypeList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgAddressTypeList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgEmailTypeList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AccountTypeList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.abusivismi.base.Organization" scope="request" />
<jsp:useBean id="AccountSizeList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="ComuniAdmin" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Comuni" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="applicationPrefs"
	class="org.aspcfs.controller.ApplicationPrefs" scope="application" />

<jsp:useBean id="systemStatus"
	class="org.aspcfs.controller.SystemStatus" scope="request" />
<jsp:useBean id="popup"
	class="java.lang.String" scope="request" />	
	
	
<%@ include file="../initPage.jsp"%>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCheckList.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/setSalutation.js"></script>
	
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>


<script>
function openGestioneCodiceFiscale(nome, cognome, comune, data, sesso, cf) {
	
	 window.open('GestioneCFUtil.do?command=GestioneCF&nome='+nome+'&cognome='+cognome+'&comune='+comune+'&data='+data+'&sesso='+sesso+'&cf='+cf,'popupSelect',
     'height=300px,width=500px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

	
}

</script>
	
<!--  [MATTEO CARELLA] -->
<!--  import di jquery per i controlli sul form -->
<script language="JavaScript" TYPE="text/javascript">




  indSelected = 0;
  orgSelected = 1;
  onLoad = 1;
   

    

  function cambiaprovincia(){
	  if ($("#siteIde").val() == 3) 
		  $("#address1state").text("BN");
      if ($("#siteIde").val() == 1 || $("#siteIde").val() == 2) 
    	  $("#address1state").text("AV");
      if ($("#siteIde").val() == 4 || $("#siteIde").val() == 5) 
    	  $("#address1state").text("CE");
      if ($("#siteIde").val() == 6 || $("#siteIde").val() == 7 || $("#siteIde").val() == 8 || $("#siteIde").val() == 9 || $("#siteIde").val() == 10) 
    	  $("#address1state").text("NA");
      if ($("#siteIde").val() == 11 || $("#siteIde").val() == 12 || $("#siteIde").val() == 13) 
    	  $("#address1state").text("SA");
          
  }
  
  function checkForm(form) {
	  

	       
    formTest = true;
    message = "";
    alertMessage = "";
    form = document.addAccount;
    /* [MATTEO CARELLA] */
	/* validazione clientside del CF con l'utilizzo di jquery */
	
	//Controllo commentato in quanto non funzionante su tutti i browser, tra l'altro il codice fiscale non è obbligatorio.
	/*
    var codiceFiscale = $('#codiceFiscale').val();
    if(codiceFiscale!=''){
		var regexCf = /^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$/;
		if(!regexCf.test(codiceFiscale)){
			message += "- Valore errato per il campo Codice Fiscale, il valore non rispetta il formato corretto\r\n";
			formTest = false;
		}
	}
	*/
	
    /* fine */
    
    form = document.addAccount;
    
    
    if (form.siteId.value == "-1"){
        message += "- ASL richiesta\r\n";
        formTest = false;
     }
    if( document.getElementById("cessato1").checked == true){ 
   
   if (form.name.value==""){
       message += "- Cognome richiesto\r\n";
        formTest = false;
      }
   
     if (checkNullString(form.banca.value)){
        message += "- Nome richiesto\r\n";
        formTest = false;
      }}
    
    if(checkNullString(form.codiceFiscale.value) && form.cessato1.checked){
   		message += "- Codice fiscale richiesto in caso di operatore non ignoto.  \r\n";
			 formTest = false;
   	 }
    
     
     if (checkNullString(form.address1line1.value)){
        message += "- Indirizzo luogo rilevazione richiesto\r\n";
        formTest = false;
      }
        if (checkNullString(form.address1city.value)){
        message += "- Comune richiesto\r\n";
        formTest = false;
      }
      
     if (form.partitaIva && form.partitaIva.value!=""){
       	 
       		if ((orgSelected == 1)  ){
       			/* [MATTEO CARELLA] */
       			/* validazione clientside della partita iva con l'utilizzo di jquery */
       			
       			var partitaIva = $('#partitaIva').val();
       			var regexPiva = /^[0-9]{11}$/;
				if(!regexPiva.test(partitaIva)){
					message += "- Valore errato per il campo Partita IVA, il valore deve essere di 11 caratteri numerici\r\n";
					formTest = false;
				}
				
				/* fine */
       		}
    	 }
	 if(checkNullString(form.address1longitude.value)){
		 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 \r\n";
			 formTest = false;
	 }
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
      loadModalWindow();
      return formTest;
    }
  }

  function disabilitaCampi(){
	 
	  if(document.getElementById("cessato2").checked == true ){
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
	  }
  }
  
 /* function ricarica(){
  
		document.getElementById("codice1").value = "";
        document.getElementById("codice2").value = "";
        document.getElementById("codice3").value = "";
        document.getElementById("codice4").value = "";
        document.getElementById("codice5").value = "";
        document.getElementById("codice6").value = "";
        document.getElementById("codice7").value = "";
        document.getElementById("codice8").value = "";
        document.getElementById("codice9").value = "";
        document.getElementById("codice10").value = "";  
        }*/
        
 function resetFormElements() {
    
  }
  function updateFormElements(index) {
    if (document.getElementById) {
      elm4 = document.getElementById("orgname1");
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
        elm4.style.color="#cccccc";
        document.addAccount.banca.style.background = "#cccccc";
        document.addAccount.banca.value = "";
        document.addAccount.banca.disabled = true;
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

<script src="javascript/geocodifica.js" type="text/javascript" language="JavaScript"></script>
<script src="dwr/interface/Geocodifica.js" type="text/javascript" language="JavaScript"></script>
<script src="dwr/engine.js" type="text/javascript" language="JavaScript"></script>

<script>
var campoLat;
	var campoLong;
	function showCoordinate(address,city,prov,cap,campo_lat,campo_long)
	{
	   campoLat = campo_lat;
	   campoLong = campo_long;
	   Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
	   
	   
	}
	function setGeocodedLatLonCoordinate(value)
	{
		campoLat.value = value[1];;
		campoLong.value =value[0];
		
	}
</script>


<dhv:evaluate
	if='<%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) %>'>
	<body
		onLoad="javascript:document.addAccount.name.focus();updateFormElements(0);">
</dhv:evaluate>
<dhv:evaluate
	if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
	<body
		onLoad="javascript:document.addAccount.name.focus();ricarica();updateFormElements(1);">
</dhv:evaluate>
<form name="addAccount"
	action="Abusivismi.do?command=Insert&auto-populate=true"
	onSubmit="return checkForm(this);" method="post">
	<%
		boolean popUp = false;
		if (request.getParameter("popup") != null) {
			popUp = true;
		}
	%>
	<dhv:evaluate if="<%= !popUp %>">
		<%-- Trails --%>
		<table>
			<tr>
				<td><dhv:permission name="abusivismi-abusivismi-add">
						<a href="Abusivismi.do?command=Add">Aggiungi</a>
					</dhv:permission></td>
				<td><dhv:permission name="abusivismi-abusivismi-view">
						<a href="Abusivismi.do?command=SearchForm">Ricerca</a>
					</dhv:permission></td>
			</tr>
		</table>
		<table class="trails" cellspacing="0">
			<tr>
				<td width="100%"><!-- <a
					href="AltriOperatori.do?command=DashboardScelta"><dhv:label
							name="">Altri Operatori</dhv:label></a> > --><a href="Abusivismi.do"><dhv:label
							name="abusivismi.abusivismi">Accounts</dhv:label></a> > <dhv:label
						name="abusivismi.add">Add Account</dhv:label></td>
			</tr>
		</table>
		<%-- End Trails --%>
	</dhv:evaluate>
	<dhv:formMessage showSpace="false" />
	<input type="submit"
		value="Inserisci"
		name="Save" >
	<dhv:evaluate if="<%= !popUp %>">
		<input type="button"
			value="Annulla"
			onClick="window.location.href='Abusivismi.do?';">
	</dhv:evaluate>
	<dhv:evaluate if="<%= popUp %>">
		<input type="button"
			value="Annulla"
			onClick="window.location.href='Abusivismi.do?';s">
	</dhv:evaluate>
	<br />
	<br />
	<iframe src="empty.html" name="server_commands" id="server_commands"
		style="visibility: hidden" height="0"></iframe>
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong>Aggiungi Operatore Abusivo</strong>
			</th>
		</tr>

	
		
		<tr>
			<td valign="top" class="formLabel">Ignoto</td>
			<td>
				<table class="empty">
					<tr>
						<td>No <input type="radio"
							onclick="javascript:disabilitaCampi();" checked="checked"
							id="cessato1" name="cessato" value="0">
						</td>
						<td>Si <input type="radio"
							onclick="javascript:disabilitaCampi();" name="cessato"
							id="cessato2" value="1">
						</td>
					</tr>
				</table>
			</td>
		</tr>

		<dhv:include name="accounts-name" none="true">
			<tr>
				<td nowrap class="formLabel" name="orgname1" id="orgname1">Cognome</td>
				<td><input type="text" size="50" maxlength="80" id="name1"
					name="name" value="<%=toHtmlValue(OrgDetails.getName())%>"><font
					color="red">*</font><%=showAttribute(request, "nameError")%></td>
			</tr>
		</dhv:include>
		<tr>
			<td class="formLabel" nowrap>Nome
			</td>
			<td><input type="text" size="50"
				onFocus="if (indSelected == 1) { tabNext(this) }" maxlength="80"
				name="banca" id="banca" value="<%=toHtmlValue(OrgDetails.getBanca())%>"><font
				color="red">*</font><%=showAttribute(request, "nameError")%></td>
		</tr>

		
		<tr>
			<td nowrap class="formLabel">Comune di nascita
			</td>
			<td><input type="text" size="40" name="categoria" id="categoria" maxlength="50"
				value="<%=toHtmlValue(OrgDetails.getCategoria())%>"></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>Codice Fiscale
			</td>
			<td><input type="text" size="20" maxlength="16"
				name="codiceFiscale" ID='codiceFiscale'
				value="<%=toHtmlValue(OrgDetails.getCodiceFiscale())%>">
				<input type="button" value="Genera codice fiscale da dati anagrafici" onClick="openGestioneCodiceFiscale(forms[0].banca.value, forms[0].name1.value, forms[0].categoria.value, '', '', 'codiceFiscale')"/></td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">Comune Residenza
			</td>
			<td><input type="text" size="28" name="nameFirst" maxlength="80"
				value=""> &nbsp;Via <input
				type="text" size="28" name="nameLast" maxlength="80" value="">
			</td>
		</tr>

		
		<tr>
			<td class="formLabel" nowrap>Partita IVA</td>
			<td>
				<input type="text" size="20" maxlength="11" name="partitaIva" ID='partitaIva' value="<%=toHtmlValue(OrgDetails.getPartitaIva())%>">
			</td>
		</tr>
		
		

		

		<tr>
			<td nowrap class="formLabel">Data Controllo
			</td>
			<td>
				<input readonly type="text" id="date2" name="date2" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].date2,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>
		
			</td>
		</tr>

	</table>
	<br>
	<%
		boolean noneSelected = false;
	%>
	<dhv:include name="organization.addresses" none="true">
		<%
			SiteList.setJsEvent("onChange=\"javascript:cambiaprovincia();\"");
		%>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong>Luogo di Rilevazione Attività Abusiva</strong>
				</th>
			</tr>
			<input type = "hidden" name = "address1type" value = "7"/>
			<dhv:include name="abusivismi-sites" none="true">
				<dhv:evaluate if="<%= SiteList.size() > 1 %>">
					<tr>
						<td nowrap class="formLabel"><dhv:label
								name="abusivismi.site">Site</dhv:label></td>
						<td><dhv:evaluate if="<%= User.getSiteId() == -1 %>">
								<%=SiteList.getHtmlSelect("siteId",
										OrgDetails.getSiteId())%>
							</dhv:evaluate> <dhv:evaluate if="<%= User.getSiteId() != -1 %>">
								<%=SiteList.getSelectedValue(User
										.getSiteId())%>
								<input type="hidden" name="siteId" value="<%=User.getSiteId()%>">
							</dhv:evaluate><font color="red">*</font></td>
					</tr>
				</dhv:evaluate>
				<dhv:evaluate if="<%= SiteList.size() <= 1 %>">
					<input type="hidden" name="siteId" id="siteId" value="-1" />
				</dhv:evaluate>
			</dhv:include>
			
			
			
				
				<tr>
					<td nowrap class="formLabel"><dhv:label
							name="abusivismi.abusivismi_add.City">City</dhv:label></td>
					<td>
						<%
							if (Comuni != null) {
						%> <select name="address1city">
							<%
								for (int i = 0; i < Comuni.size(); i++) {
													Comuni c = (Comuni) Comuni.get(i);
							%>
							<option value="<%=c.getComune()%>"><%=c.getComune()%></option>

							<%
								}
							%>
					</select> <%
 	}
 %>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel"><dhv:label
							name="abusivismi.abusivismi_add.AddressLine1">Address Line 1</dhv:label>
					</td>
					<td><input type="text" size="40" name="address1line1"
						id="address1line1" maxlength="80"><font color="red">*</font><%=showAttribute(request, "nameError")%>
					</td>
				</tr>

				<tr>
					<td nowrap class="formLabel"><dhv:label
							name="abusivismi.abusivismi_add.ZipPostalCode">Zip/Postal Code</dhv:label>
					</td>
					<td><input type="text" size="28" name="address1zip"
						maxlength="12" onBlur="if(this.value.trim()=='80100') { alert('Impossibile inserire il CAP: 80100.'); this.value = '';}"></td>
				</tr>
				<tr>
					<td nowrap class="formLabel"><dhv:label
							name="abusivismi.abusivismi_add.StateProvince">State/Province</dhv:label>
					</td>
					<td><input type="text"  size="5"
						name="address1state" id="address1state" maxlength="80" value=""></td>
				</tr>

			

				<tr class="containerBody">
					<td class="formLabel" nowrap><dhv:label
							name="abusivismi.address.latitude">Latitude</dhv:label></td>
					<td><input type="text" name="address1latitude"
						id="address1latitude" size="20" value=""><font color="red">*</font></td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel" nowrap><dhv:label
							name="abusivismi.address.longitude">Longitude</dhv:label></td>
					<td><input type="text" name="address1longitude"
						id="address1longitude" size="20" value=""><font
						color="red">*</font></td>
				</tr>
				<tr style="display: block">
					<td colspan="2"><input id="coord1button" type="button"
						value="Calcola Coordinate"
						onclick="javascript:showCoordinate(document.getElementById('address1line1').value, document.forms['addAccount'].address1city.value,document.forms['addAccount'].address1state.value, document.forms['addAccount'].address1zip.value, document.forms['addAccount'].address1latitude, document.forms['addAccount'].address1longitude);" />
					</td>
				</tr>
			
		</table>
		<br />
	</dhv:include>


	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
						name="abusivismi.abusivismi_add.AdditionalDetails">Additional Details</dhv:label></strong>
			</th>
		</tr>
		<tr>
			<td valign="top" nowrap class="formLabel"><dhv:label
					name="abusivismi.abusivismi_add.Notes">Notes</dhv:label></td>
			<td><TEXTAREA NAME="notes" ROWS="3" COLS="50"><%=toString(OrgDetails.getNotes())%></TEXTAREA></td>
		</tr>
	</table>
	<dhv:evaluate if="<%= !popUp %>">
		<br />
		<%-- %>
<dhv:label name="abusivismi.radio.header">Where do you want to go after this action is complete?</dhv:label><br />
<input type="radio" name="target" value="return" onClick="javascript:updateCopyAddress(0)" <%= request.getParameter("target") == null || "return".equals(request.getParameter("target")) ? " checked" : "" %> /> <dhv:label name="abusivismi.radio.details">View this account's details</dhv:label><br />
<input type="radio" name="target" value="add_contact" onClick="javascript:updateCopyAddress(1)" <%= "add_contact".equals(request.getParameter("target")) ? " checked" : "" %> /> <dhv:label name="abusivismi.radio.addContact">Add a contact to this account</dhv:label>
<input type="checkbox" id="copyAddress" name="copyAddress" value="true"  disabled="true" /><dhv:label name="abusivismi.abusivismi_add.copyEmailPhoneAddress">Copy email, phone and postal address</dhv:label>--%>
	</dhv:evaluate>
	<br /> <input type="hidden" name="onlyWarnings"
		value='<%=(OrgDetails.getOnlyWarnings() ? "on" : "off")%>' />
	<%=addHiddenParams(request, "actionSource|popup")%>
	<input type="submit"
		value="Inserisci"
		name="Save"  />
	<dhv:evaluate if="<%= !popUp %>">
		<input type="button"
			value="Annulla"
			onClick="window.location.href='Abusivismi.do?';">
	</dhv:evaluate>
	<dhv:evaluate if="<%= popUp %>">
		<input type="button"
			value="Annulla"
			onClick="window.location.href='Abusivismi.do?';">
	</dhv:evaluate>
	<input type="hidden" name="dosubmit" value="true" />
</form>
</body>

<script>
cambiaprovincia();
$( "#siteId" ).change(function() {
	cambiaprovincia();
	});

</script>