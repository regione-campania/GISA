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
 <% %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.trasportoanimali.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieA" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoriaTrasportata" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.trasportoanimali.base.Organization" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="AccountSizeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CodiceIstatList" class="org.aspcfs.utils.web.CustomLookupList" scope="request"/>
<jsp:useBean id="Address" class="org.aspcfs.modules.trasportoanimali.base.OrganizationAddress" scope="request"/>
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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
<%-- script language="JavaScript" TYPE="text/javascript" SRC="javascript/geocodifica.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/Geocodifica.js"> </script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/engine.js"> </script --%>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<script language="JavaScript" TYPE="text/javascript">
  indSelected = 0;
  orgSelected = 1;
  onLoad = 1;
  function doCheck(form) {
	  return(checkForm(form));
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
       if (checkNullString(form.address2line1.value)){
           message += "- Indirizzo sede operativa richiesto\r\n";
           formTest = false;
         }          
        
         if (checkNullString(form.address2city.value)){
           message += "- Comune sede operativa richiesta\r\n";
           formTest = false;
         }
         
         if (checkNullString(form.address2state.value)){
             message += "- Provincia sede operativa richiesta\r\n";
             formTest = false;
           }
        


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
         		if ((orgSelected == 1)  ) {
         			/*if (isNaN(form.address2longitude.value)||(form.address2longitude.value < 2417159.584320) || (form.address3longitude.value > 2587487.362260)){
          			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2417159.584320 e 2587487.362260  (Sede Operativa)\r\n";
          				 formTest = false;
          			}*/
          			if (isNaN(form.address3longitude.value) ||  (form.address3longitude.value < 13.7563172) || (form.address3longitude.value > 15.8032837)){
            			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Lavaggio autorizzato)\r\n";
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
  function resetFormElementsNew() {
   	 
       	
       elm12 = document.getElementById("indirizzo1");
       document.addAccount.indirizzo12.style.background = "#ffffff";
       document.addAccount.indirizzo12.disabled = false;
       elm12.style.color = "#000000";
       
       elm17 = document.getElementById("prov2");
       document.getElementById("prov12").disabled = false;
       document.getElementById("prov12").selectedIndex = 0;
       elm17.style.color = "#000000";
       
       //document.getElementById("prov").selectedIndex = 0;
       
       elm13 = document.getElementById("cap1");
       document.addAccount.cap.style.background = "#ffffff";
       document.addAccount.cap.disabled = false;
       elm13.style.color = "#000000";
       
       elm14 = document.getElementById("stateProv2");
       elm14.style.color = "#000000";
       
       elm15 = document.getElementById("latitude2");
       document.addAccount.address2latitude.style.background = "#ffffff";
       document.addAccount.address2latitude.disabled = false;
       elm15.style.color = "#000000";
       
       elm16 = document.getElementById("longitude2");
       document.addAccount.address2longitude.style.background = "#ffffff";
       document.addAccount.address2longitude.disabled = false;
       elm16.style.color = "#000000";
       
       document.addAccount.address1type.style.background = "#ffffff";
       document.addAccount.address1type.disabled = false;
       document.addAccount.address1type.style.color="#000000"
       	
       
       
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
      elm4 = document.getElementById("orgname1");
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
      
      elm4.style.color = "#000000";
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
     	elm4 = document.getElementById("orgname1");
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
        
        elm4.style.color="#cccccc";
        document.addAccount.name.style.background = "#cccccc";
        document.addAccount.name.value = "";
        
        document.addAccount.name.disabled = false;
        
        
        elmEsIdo = document.getElementById("nameMiddle");
        elmEsIdo.style.color = "#cccccc";
        document.addAccount.nameMiddle.value = "";
    	document.addAccount.nameMiddle.disabled = true;
            
    elmNS = document.getElementById("cin");
    elmNS.style.color="#cccccc";
    document.addAccount.cin.value = "";
    document.addAccount.cin.disabled = true;
    
    
    elmNSd3 = document.getElementById("date3");
    elmNSd3.style.color="#cccccc";
    document.addAccount.date3.value = "";
    document.addAccount.date3.disabled = true;
    
    date3 = document.getElementById("data3");
    date3.style.visibility="hidden";
    
  
        
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



   function updateFormElementsNew(index) {
  	
  
  	
  	if(index==1){
  		
  	}
  	else if(index==0){
  		
  	
  	
  	}
  	

  	
    if (document.getElementById) {
      
       elm5 = document.getElementById("addressLine");
       elm7 = document.getElementById("labelCap");
   
       
      if (index == 0) {
        resetFormElementsNew();
     
        
      } else if (index == 1){
      
        resetFormElementsNew();
        
        document.addAccount.address1type.style.background = "#000000";
       	document.addAccount.address1type.disabled = false;
        
       elm5 = document.getElementById("indirizzo1");
       elm6 = document.getElementById("prov2");
       elm7 = document.getElementById("cap1");
     
       
      	 elm5.style.color="#cccccc";
        document.addAccount.indirizzo12.style.background = "#cccccc";
        document.addAccount.indirizzo12.value = "";
        document.addAccount.indirizzo12.disabled = true;
        
  
        elm7.style.color="#cccccc";
        document.addAccount.cap.style.background = "#cccccc";
        document.addAccount.cap.value = "";
        document.addAccount.cap.disabled = true;
        
     
        
      } else if (index==2) {
      	
      	resetFormElementsNew();
        
        elm1.style.color="#cccccc";
        document.addAccount.tipoVeicolo.style.background = "#cccccc";
        document.addAccount.tipoVeicolo.value = "";
        document.addAccount.tipoVeicolo.disabled = true;
    
        elm2.style.color="#cccccc";
        document.addAccount.targaVeicolo.style.background = "#cccccc";
        document.addAccount.targaVeicolo.value = "";
        document.addAccount.targaVeicolo.disabled = true;
        
        document.addAccount.check.value = "codiceCont";
        document.addAccount.orgType.value = "19"; //Valore per sindaco
        
      }
    }
   
    onLoad = 0;
  }
  
  function resetCarattere(){
  	
  		
  		elm1 = document.getElementById("data1");
 		elm2 = document.getElementById("data2");
 		elm3 = document.getElementById("dat3");
 		elm4 = document.getElementById("data4");
 		
 		elm1.style.visibility = "hidden";
 		elm2.style.visibility = "hidden";
 		elm3.style.visibility = "hidden";
 		elm4.style.visibility = "hidden";
 		document.addAccount.source.selectedIndex=0;
 		
  }
  
  function selectCarattere(){
  
 		elm1 = document.getElementById("data1");
 		elm2 = document.getElementById("data2");
 		elm3 = document.getElementById("dat3");
 		elm4 = document.getElementById("data4");
 		elm5 = document.getElementById("cessazione");
 		elm6 = document.getElementById("data5");
 		car = document.addAccount.source.value;
 	
 		if(car == 1){
 			elm1.style.visibility = "visible";
 			elm2.style.visibility = "visible";
 			elm3.style.visibility = "visible";
 			elm4.style.visibility = "visible";
 			elm5.style.visibility = "visible";
 			elm6.style.visibility = "visible";
 		}
 		else {
 			elm1.style.visibility = "hidden";
 			elm2.style.visibility = "hidden";
 			elm3.style.visibility = "hidden";
 			elm4.style.visibility = "hidden";
 			elm5.style.visibility = "hidden";
 			elm6.style.visibility = "hidden";
 		}
 	
  }
    
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



<dhv:evaluate if='<%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();updateFormElementsNew(0);updateFormElements(0);">
</dhv:evaluate>
<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
<body onLoad="javascript:document.addAccount.name.focus();updateFormElements(1);updateFormElementsNew(0);resetCodice();">
</dhv:evaluate>

	<form name="addAccount" action="TrasportoAnimali.do?command=Insert&tipo_richiesta=tipo2&auto-populate=true"  onsubmit="return doCheck(this);" method="post">
		<input type="hidden" name="tiplogia_modulo" value="1">
		
		
		<%boolean popUp = false; 
		  if(request.getParameter("popup")!=null){
		    popUp = true;
		  }%>
		<dhv:evaluate if="<%= !popUp %>">  
		<% %>
		<%-- Trails --%>
		<table class="trails" cellspacing="0">
			<tr>
				<td width="100%">
					<a href="TrasportoAnimali.do"><dhv:label name="trasportoanimali.trasportoanimali">Trasporto animali</dhv:label></a> >
					<dhv:label name="trasportoanimali.add">Richiesta di autorizzazione al trasporto tipo 2</dhv:label>
				</td>
			</tr>
		</table>
		<%-- End Trails --%>
		</dhv:evaluate>
		
		<!-- Tasti menù -->
		<dhv:formMessage showSpace="false"/>
		<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';">
		<%--dhv:permission name="trasportoanimali-trasportoanimali-add"><input type="submit" value="Inserisci e Clona" onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='true';" /></dhv:permission--%>
		<dhv:evaluate if="<%= !popUp %>">
		  <input type="button" value="Annulla" onClick="javascript:window.location.href = 'TrasportoAnimali.do?command=ScegliRichiesta';">
		</dhv:evaluate>
		<dhv:evaluate if="<%= popUp %>">
		  <input type="button" value="Annulla" onClick="javascript:self.close();">
		</dhv:evaluate>
		
		<br>
		<br>
		
		<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
		
		<% %>
		<!-- Dati richiesta -->
		<!-- ASL e Tipologia autorizzazione -->
		<!-- Tipologia della richiesta -->
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
			<tr>
			  <th colspan="2">
			    <strong><dhv:label name="trasportoanimali.trasportoanimali.AddNewTrasportoAnimali">Richiesta</dhv:label></strong>
			  </th>
			</tr>  
		
		
			<!-- Combo ASL -->
			<dhv:include name="trasportoanimali-sites" none="true">
			 <dhv:evaluate if="<%= SiteList.size() > 1 %>">
			   <tr>
			     <td nowrap class="formLabel">
			       <dhv:label name="trasportoanimali.site">A.S.L.</dhv:label>
			     </td>
			     <td>
			       <dhv:evaluate if="<%= User.getSiteId() == -1 %>" >
			         <%= SiteList.getHtmlSelect("siteId",OrgDetails.getSiteId()) %><font color="red">*</font> <%= showAttribute(request, "nameError") %>
			       </dhv:evaluate>
			       <dhv:evaluate if="<%= User.getSiteId() != -1 %>" >
			          <%= SiteList.getSelectedValue(User.getSiteId()) %>
			         <input type="hidden" name="siteId" value="<%=User.getSiteId()%>" ><font color="red">*</font> <%= showAttribute(request, "nameError") %>
			       </dhv:evaluate>
			     </td>
			   </tr>
			 </dhv:evaluate> 
			 <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
			   <input type="hidden" name="siteId" id="siteId" value="-1" /><font color="red">*</font> <%= showAttribute(request, "nameError") %>
			 </dhv:evaluate>
			</dhv:include>
			
			<!-- Data richiesta autorizzazione -->
			<tr style="display: none">
      			<td nowrap class="formLabel">
        			<dhv:label name="requestor.requestor_add.AlertDate">Alert Date</dhv:label>
      			</td>
      			<td>
      			<input readonly type="text" id="alertDate" name="alertDate" size="10" value="<%= toDateString(OrgDetails.getAlertDate()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].alertDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			<font color="red">*</font>
				        <%= showAttribute(request, "alertDateError") %>
				        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      			</td>
    		</tr>
    		<tr>
      			<td nowrap class="formLabel">
        			<dhv:label name="">Data Presentazione Richiesta</dhv:label>
      			</td>
      			<td>
      			<input readonly type="text" id="date1" name="date1" size="10" value="<%= toDateString(OrgDetails.getDate1()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].date1,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      				<font color="red">*</font>
        			<%= showAttribute(request, "date1Error") %>
      			</td>
    		</tr>
			<dhv:include name="accounts-name" none="true">
  			<tr>
    			<td nowrap class="formLabel" name="orgname1" id="orgname1">
      				<dhv:label name="">Denominazione</dhv:label>
    			</td>
    			<td>
      				<input type="text" size="50" maxlength="80"  name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    			</td>
  			</tr>
  			</dhv:include>
  			<tr>
    			<td class="formLabel" nowrap>
      				Partita IVA
    			</td>
    			<td>
      				<input type="text" size="20" maxlength="11" name="partitaIva" value="<%= toHtmlValue( OrgDetails.getPartitaIva()) %>">
    			</td>
  			</tr>
  			<tr>
    			<td class="formLabel" nowrap>
      				Codice Fiscale
    			</td>
    			<td>
      				<input type="text" size="20" maxlength="16" name="codiceFiscale" value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>">    
    			</td>
  			</tr>
  			<dhv:include name="organization.specieAllev" none="true">
			    <tr>
			      <td nowrap class="formLabel">
			        <dhv:label name="">Animali Trasportati
</br>
			        (In caso di selezione</br> 
multipla tenere </br> premuto
il tasto Ctrl) </dhv:label>
			      </td>
			      <td>
			      <%= CategoriaTrasportata.getHtmlSelect("categoriaTrasportata",OrgDetails.getSpecieA())%><font color="red">*</font> <%= showAttribute(request, "nameError") %>
			        <%--<input type="text" size="20" name="specieAllev" maxlength="20" value=""><font color="red">*</font>--%>
			      </td>
			    </tr>
  			</dhv:include>
  			<%
      
      SpecieA.setJsEvent("onChange=abilitaDes();");
      
      %>
  			<dhv:include name="organization.specieAllev" none="true">
			    <tr>
			      <td nowrap class="formLabel">
			        <dhv:label name="">Specie Animali Trasportati<BR><BR>(In caso di selezione<br> multipla tenere premuto<br> il tasto Ctrl)</dhv:label>
			      </td>
			      <td>
			      <%= SpecieA.getHtmlSelect("specieA",OrgDetails.getSpecieA())%><font color="red">*</font> <%= showAttribute(request, "nameError") %>
			      <div id="des" style="display:none"><br><b>Descrizione:</b>
    					<br><textarea rows="8" cols="40" name="codice10"></textarea></div>
     		      </td>
			    </tr>
  			</dhv:include>
		</table>  
		<br>

		<!-- Titolare o Legale Rappresentante -->
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="rappresentanteLegale">
			<tr>
				<th colspan="2">
					<strong>
						<dhv:label name="">Titolare o Legale Rappresentante</dhv:label>
					</strong>
				</th>
			</tr>
			
			
			<!-- Codice fiscale -->
			<tr >
			   <td class="formLabel" nowrap>
			     Codice Fiscale
			  </td>
			  <td>
			    <input type="text" size="30" maxlength="16" name="codiceFiscaleRappresentante" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>			  </td>
			</tr>
			<input type="hidden" id="tipoD2" name="tipoDest" value="<%= request.getParameter("tipo_richiesta") %>">
			<!-- Nome -->
			<tr >
			  <td class="formLabel" nowrap>
			    Nome
			</td>
			<td>
			  <input type="text" size="30" maxlength="50" name="nomeRappresentante" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
			  </td>
			</tr>
			<!-- Cognome -->
			<tr >
				<td class="formLabel" nowrap>
				  Cognome
				</td>
				<td>
				  <input type="text" size="30" maxlength="50" name="cognomeRappresentante" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
				</td>
			</tr>
		  
		  	<!-- Data nascita -->
			<tr>
			    <td nowrap class="formLabel">
			    	<dhv:label name="">Data Nascita</dhv:label>
				</td>
				<td>
				<input readonly type="text" id="dataNascitaRappresentante" name="dataNascitaRappresentante" size="10" value="<%= toDateString(OrgDetails.getDataNascitaRappresentante()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataNascitaRappresentante,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					<%= showAttribute(request, "alertDateError") %>
					<%= showWarningAttribute(request, "alertDateWarning") %>
				</td>
			</tr>
			
			<!-- Comune di nascita -->
			<tr >
				<td class="formLabel" nowrap>
					<dhv:label name="">Comune di nascita</dhv:label>
				</td>
				<td>
					<input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtmlValue(OrgDetails.getLuogoNascitaRappresentante()) %>">
				</td>
			</tr>
		  
			<!-- Email -->  
			<tr >
				<td class="formLabel" nowrap>
					<dhv:label name="">Email</dhv:label>
				</td>
				<td>
					<input type="text" size="30" maxlength="50" name="emailRappresentante" value="<%= toHtmlValue(OrgDetails.getEmailRappresentante()) %>">
				</td>
			</tr>
		  	
		  	<!-- Telefono -->
			<tr >
				<td class="formLabel" nowrap>
					<dhv:label name="">Telefono</dhv:label>
				</td>
				<td>
					<input type="text" size="30" maxlength="50" name="telefonoRappresentante" value="<%= toHtmlValue(OrgDetails.getTelefonoRappresentante()) %>">
				</td>
			</tr>
		  	
		  	<!-- Fax -->
			<tr>
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
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		  <tr>
		    <th colspan="2">
			    <strong>Sede Legale</strong>
			    <input type="hidden" name="address1type" value="1">
			  </th>
		  
		
		    <td ></tr>
		    <tr>
		    <td nowrap class="formLabel">
		      <dhv:label name="requestor.requestor_add.City" >City</dhv:label>
		    </td>  <td>
	<select  name="address1city" id="prov12">
	<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            
	 <%
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
                while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                  
        %>
                <option value="<%=prov4%>"><%= prov4 %></option>	
              <%}%>
		
	</select> 
	
	</td>
		  </tr>
		  <tr>
		    <td nowrap class="formLabel">
		      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
		    </td>
		    <td>
		      <input type="text" size="40" name="address1line1" maxlength="80" value="<%= toHtmlValue(Address.getStreetAddressLine1()) %>">
		    </td>
		  </tr>
		  <tr>
		    <td nowrap class="formLabel">
		      <dhv:label name="">C/O</dhv:label>
		    </td>
		    <td>
		      <input type="text" size="40" name="address1line2" maxlength="80">
		    </td>
		  </tr>
		
		  	 <tr>
		    <td nowrap class="formLabel">
		      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
		    </td>
		    <td>
		      <input type="text" size="28" name="address1zip" maxlength="12">
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
		  </br>
		
		<!-- Sede operativa -->
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
   <input type="text" name="address2city" id="prov12">
    
	
	<font color="red">*</font>
	</td>
  	</tr>
  <tr>
    <td nowrap class="formLabel" id="indirizzo1">
      Indirizzo
    </td>
    <td>
      <input type="text" size="40" name="address2line1" maxlength="80" id="indirizzo12" value="<%= toHtmlValue(Address.getStreetAddressLine2()) %>"><font color="red">*</font>
    </td>
  </tr>
 	
  	
  <tr>
    <td nowrap class="formLabel" id="cap1">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address2zip" maxlength="12" id="cap">
    </td>
  </tr>
  	<tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    
    <input type="text"  size="28" name="address2state" maxlength="80" ><font color="red">*</font>
      
    </td>
  </tr>
  
  	
 
 
  
  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude2"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
   
    <td>
       	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address2latitude"   name="address2latitude" size="30" value=""  readonly="readonly">
 		<font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td>
    	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address2longitude"   name="address2longitude" size="30" value="" readonly="readonly">
    		<font color="red">*</font>
    </td>
    </tr>
    <tr style="display: block">
    <td colspan="2">
    <input id="coord1button" type="button" value="Calcola Coordinate"
    onclick="javascript:showCoordinate(document.getElementById('indirizzo12').value, document.forms['addAccount'].address2city.value,document.forms['addAccount'].address2state.value, document.forms['addAccount'].address2zip.value, document.forms['addAccount'].address2latitude, document.forms['addAccount'].address2longitude);" /> 
    </td>
    </tr>
  
 
  
</table>

		<!-- Lavaggio autorizzato -->
		<br>
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id="lavaggioAutomezzi">
			<tr>
				<th colspan="2">
			   		<strong><dhv:label name="">Lavaggio autorizzato</dhv:label></strong>
			  		<input type="hidden" name="address3type" value="7">
				</th>
			
			
			<tr>
				<td nowrap class="formLabel">
			    	<dhv:label name="requestor.requestor_add.City" >City</dhv:label>
			  	</td>
			  	<td>
			    	<input type="text" size="28" name="address3city" maxlength="80" value="">
			  	</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">
			    	<dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
				</td>
				<td>
			  		<input type="text" size="40" name="address3line1" maxlength="80" value="<%= toHtmlValue(Address.getStreetAddressLine1()) %>">			  	</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">
					<dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
				</td>
				<td>
					<input type="text" size="28" name="address3zip" maxlength="12">
				</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">
			    	<dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
				</td>
				<td>
					<input type="text" size="28" name="address3state" maxlength="80" value="<%= toHtmlValue(Address.getCityState()) %>">
		
			  	</td>
			</tr>
	
			
		
			
		
		
			<tr class="containerBody">
				<td class="formLabel" nowrap>
					<dhv:label name="requestor.address.latitude">Latitude
					</dhv:label>
				</td>
				<td>
					<input type="text" id="address3latitude" name="address3latitude" size="30" value="">
				
			  	</td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel" nowrap>
					<dhv:label name="requestor.address.longitude">Longitude</dhv:label>
				</td>
			    <td>
			    	<input type="text" id="address3longitude" name="address3longitude" size="30" value="">
			    </td>
			 </tr>  
		</table>
		<br>
		
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
				  <input type="text" size="40" name="codiceFiscaleCorrentista" maxlength="80" value="">
				</td>
			</tr>
			
			<!-- Nome -->
			<tr>
				<td nowrap class="formLabel">
				  Nome
				</td>
				<td>
				  <input type="text" size="40" name="nomeCorrentista" maxlength="80" value="">
				</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">
				  <dhv:label name="">Telefono</dhv:label>
				</td>
				<td>
				  <input type="text" size="40" name="contoCorrente" maxlength="80" value="">
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
		
	
		
		<br><br>

		<input type="hidden" name="onlyWarnings" value='<%= (OrgDetails.getOnlyWarnings()?"on":"off")%>' />
			<%= addHiddenParams(request, "actionSource|popup") %>
		<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';" />
		<dhv:permission name="trasportoanimali-trasportoanimali-add">
			<%--input type="submit" value="Inserisci e Clona" onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='true';" /--%>
		</dhv:permission>
		<dhv:evaluate if="<%= !popUp %>">
			<input type="button" value="Annulla" onClick="javascript:window.location.href = 'TrasportoAnimali.do?command=ScegliRichiesta';">
		</dhv:evaluate>
		<dhv:evaluate if="<%= popUp %>">
			<input type="button" value="Annulla" onClick="javascript:self.close();">
		</dhv:evaluate>
		<input type="hidden" name="dosubmit" value="true" />
		<input type="hidden" name="saveandclone" value=""/>
	</form>   
	
</body>