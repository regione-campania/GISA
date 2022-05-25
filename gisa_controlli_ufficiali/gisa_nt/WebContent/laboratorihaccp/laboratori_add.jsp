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
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.laboratorihaccp.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AddressSedeOperativa" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.laboratorihaccp.base.Organization" scope="request"/>
<jsp:useBean id="Address" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
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
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        <script type="text/javascript">

			function popolaAslHidden(){

				document.addAccount.asl.value = document.addAccount.siteId.value;
				
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
<script language="JavaScript" TYPE="text/javascript">
  var indSelected = 0;
  var orgSelected = 1;
  var onLoad = 1;  
  
  function doCheck(form) {
	  
      if (form.dosubmit.value == "false") {
          alert('falso')
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
    	
    if (form.name){
        if ((checkNullString(form.name.value))){
          message += "- Denominazione richiesta\r\n";
          formTest = false;
        }
      }
    if (form.accountNumber){
        if ((checkNullString(form.accountNumber.value))){
          message += "- Numero di iscrizione richiesto\r\n";
          formTest = false;
        }
      }
    
    if (checkNullString(form.address2line1.value)&&(form.address2line1.disabled==false)){
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
	     loadModalWindow();
	     return true;
   	   	
  }else {
   	    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
   	    return false;
   	 }     	 
  }
  
  function resetFormElements() {
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
     
      elm5 = document.getElementById("ticker1");
      <dhv:include name="accounts-size" none="true">
        elm6 = document.getElementById("accountSize1");
      </dhv:include>
      <dhv:include name="accounts-title" none="true">
        elm7 = document.getElementById("listSalutation1");
      </dhv:include>
      <dhv:include name="accounts-firstname" none="true">
        elm1.style.color = "#000000";
        document.addAccount.nameFirst.style.background = "#ffffff";
        document.addAccount.nameFirst.disabled = false;
      </dhv:include>
      <dhv:include name="accounts-middlename" none="true">
        elm2.style.color = "#000000";
        document.addAccount.nameMiddle.style.background = "#ffffff";
        document.addAccount.nameMiddle.disabled = false;
      </dhv:include>
      <dhv:include name="accounts-lastname" none="true">
        elm3.style.color = "#000000";
        document.addAccount.nameLast.style.background = "#ffffff";
        document.addAccount.nameLast.disabled = false;
      </dhv:include>
      
      document.addAccount.name.style.background = "#ffffff";
        document.addAccount.name.disabled = false;
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
      <dhv:include name="accounts-firstname" none="true">
        elm1 = document.getElementById("nameFirst1");
      </dhv:include>
      <dhv:include name="accounts-middlename" none="true">
        elm2 = document.getElementById("nameMiddle1");
      </dhv:include>
      <dhv:include name="accounts-lastname" none="true">
        elm3 = document.getElementById("nameLast1");
      </dhv:include>
      
      elm5 = document.getElementById("ticker1");
      <dhv:include name="accounts-size" none="true">
        elm6 = document.getElementById("accountSize1");
      </dhv:include>
      <dhv:include name="accounts-title" none="true">
        elm7 = document.getElementById("listSalutation1");
      </dhv:include>
      if (index == 1) {
        indSelected = 1;
        orgSelected = 0;
        resetFormElements();
        
        document.addAccount.ragioneSociale.style.background = "#cccccc";
        document.addAccount.name.value = "";
        document.addAccount.name.disabled = true;
        if (elm5) {
          elm5.style.color="#cccccc";
          document.addAccount.ticker.style.background = "#cccccc";
          document.addAccount.ticker.value = "";
          document.addAccount.ticker.disabled = true;
        }
        <dhv:include name="accounts-size" none="true">
          elm6.style.color = "#cccccc";
          document.addAccount.accountSize.style.background = "#cccccc";
          document.addAccount.accountSize.value = -1;
          document.addAccount.accountSize.disabled = true;
        </dhv:include>
      } else {
        indSelected = 0;
        orgSelected = 1;
        resetFormElements();
        <dhv:include name="accounts-firstname" none="true">
          elm1.style.color = "#cccccc";
          document.addAccount.nameFirst.style.background = "#cccccc";
          document.addAccount.nameFirst.value = "";
          document.addAccount.nameFirst.disabled = true;
        </dhv:include>
        <dhv:include name="accounts-middlename" none="true">
          elm2.style.color = "#cccccc";
          document.addAccount.nameMiddle.style.background = "#cccccc";
          document.addAccount.nameMiddle.value = "";
          document.addAccount.nameMiddle.disabled = true;
        </dhv:include>
        <dhv:include name="accounts-lastname" none="true">
          elm3.style.color = "#cccccc";
          document.addAccount.nameLast.style.background = "#cccccc";
          document.addAccount.nameLast.value = "";
          document.addAccount.nameLast.disabled = true;
        </dhv:include>
        
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
<dhv:evaluate if='<%= (request.getParameter("form_type") == null || "laboratori".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();updateFormElements(0);">
</dhv:evaluate>
<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();updateFormElements(1);">
</dhv:evaluate>
<form name="addAccount" action="LaboratoriHACCP.do?command=Insert&auto-populate=true"   method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate if="<%= !popUp %>">  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="LaboratoriHACCP.do?command=SearchForm"><dhv:label name="">Laboratori HACCP</dhv:label></a> >
<dhv:label name="">Aggiungi Laboratorio HACCP</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';return doCheck(document.addAccount);">
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='LaboratoriHACCP.do?command=SearchForm';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<br /><br />
 <%-- SiteList.setJsEvent("onchange='javascript:popolaAslHidden();'"); --%>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Laboratorio HACCP</dhv:label></strong>
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
        <input type="hidden" name="asl" value=""/>
        
        <font color="red">* Campo compilato in automatico.</font>
      </td>
    </tr>
  </dhv:evaluate> 


  <dhv:include name="name" none="true">
  <tr>
    <td nowrap class="formLabel" name="name" id="name">
      <dhv:label name="">Denominazione</dhv:label>
    </td>
    <td>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font>
    </td>
  </tr>
  </dhv:include>
  
  <dhv:include name="organization.accountNumber" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="lab.number">Numero di Iscrizione</dhv:label>
      </td>
      <td>
        <input type="text" size="30" name="accountNumber" maxlength="30" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>"><font color="red">*</font>
      </td>
    </tr>
  </dhv:include>
  
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="lab.dr">Direttore responsabile laboratorio</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="cognomeRappresentante" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>">
    </td>
  </tr>
    	
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="lab.dr">Partita IVA</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="partita_iva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>" />
    </td>
  </tr>  	
    	
  <tr> <td nowrap class="formLabel">Stato Laboratorio</td>
  <td>
  <table class = "noborder"><tr><td>
    	<select name="stato" >
    	    <option value="Attivo" >Attivo</option>
    		<option value="Sospeso" >Sospeso</option>
    		<option value="Revocato" >Revocato</option>
    	</select></td>
    	<td>
    	
    	<% Timestamp data_stato = new Timestamp(System.currentTimeMillis()); %>
    	In Data  <zeroio:dateSelect form="addAccount"  field="dataCambioStato" timestamp="<%= data_stato %>" showTimeZone="false" />
    	
    	
    	</td>
    	
    	</tr>
    	</table>
    </td>
  </tr>
  </table>
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
    <td > 
    <table class = "noborder">
    <td>
    <input type="text" name="address1city" id="address1city" value = "<%=toHtmlValue(Address.getCity()) %>" style="display: block;">
	<select  name="address1city" id="address1city2" style="display: none;" >
	<option value="-1">Nessuna Selezione</option>
            
	 <%
                Vector v41 = OrgDetails.getComuni2();
	 			Enumeration e41=v41.elements();
                while (e41.hasMoreElements()) {
                	String prov41=e41.nextElement().toString();
                	
                  
        %>
                <option value="<%=prov41%>" <%if(prov41.equalsIgnoreCase(Address.getCity())) {%> selected="selected" <%} %>><%= prov41 %></option>	
              <%}%>
		
	</select></td><td>	</td></table>
	
	</td>
  	</tr>	
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" id="address1line1" name="address1line1" maxlength="80" value="<%= toHtmlValue(Address.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="">C/O</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address1line2" maxlength="80" value = "<%=toHtmlValue(Address.getStreetAddressLine2()) %>">
    </td>
  </tr>

  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address1zip" maxlength="12" value = "<%=toHtmlValue(Address.getZip()) %>">
    </td>
  </tr>
  
  	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	  <input type="text" size="28" name="address1state" maxlength="80" value="<%= toHtmlValue(Address.getCityState()) %>">      
    </td>
  </tr>

   
  
  
</table>

<br>
   
    

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Sede Operativa</dhv:label></strong>
	  <input type="hidden" name="address2type" value="5">
	  </th>
  </tr>
 <tr>
	<td nowrap class="formLabel" name="province1" id="prov2">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
	<select  name="address2city" id="prov12" onchange="javascript:popolaComuni();">
	<option value="-1">Nessuna Selezione</option>
            
	 <%
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
                while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                	
        %>
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(AddressSedeOperativa.getCity())) {%> selected="selected" <%} %>><%= prov4 %></option>	
              <%}%>
		
	</select> 
	<font color="red">*</font>
	</td>
  	</tr>	
  	  	
  <tr>
    <td nowrap class="formLabel" id="indirizzo1">
      Indirizzo
    </td>
    <td>
      <input type="text" size="40" name="address2line1" maxlength="80" id="indirizzo12" value="<%= toHtmlValue(AddressSedeOperativa.getStreetAddressLine1()) %>"><font color="red">*</font>
    </td>
  </tr>
   
  <tr>
    <td nowrap class="formLabel" id="cap1">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address2zip" maxlength="12" value = "<%=toHtmlValue(AddressSedeOperativa.getZip()) %>" id="cap">
    </td>
  </tr>  
  	 
  	<tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
          <input type="text" readonly="readonly" size="2" name="address2state" maxlength="5" value=""> 
    </td>
  </tr>
  
  
   
</table>
<br>
<dhv:evaluate if="<%= !popUp %>">  
<br />
</dhv:evaluate>  
<br />
<input type="hidden" name="onlyWarnings" value='<%=(OrgDetails.getOnlyWarnings()?"on":"off")%>' />
<%= addHiddenParams(request, "actionSource|popup") %>
<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';return doCheck(document.addAccount);" />
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='LaboratoriHACCP.do?command=SearchForm';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true" />
</form>
</body>