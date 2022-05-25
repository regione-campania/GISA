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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.zonecontrollo.base.Organization,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>

<%@page import="org.aspcfs.modules.zonecontrollo.base.OrganizationAddress"%><jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.zonecontrollo.base.Organization" scope="request"/>
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
    	
    alertMessage = "";
	
//  if (form.name){
//      if (form.name.value==''){
//        message += "- Denominazione richiesta\r\n";
//        formTest = false;
//      }
//    }
 
 if (form.siteId){
     if (form.siteId.value=='-1' || form.siteId.value=='' ){
       message += "- ASL richiesta\r\n";
       formTest = false;
     }
   }
 
 if(form.address1city){
  if (form.address1city.value == "-1" || form.address1city.value == "" ){
  	 //alert(!isNaN(form.address2longitude.value));
 	 message += "- Comune richiesto\r\n";
      formTest = false;
   }
 }
 
 if(form.alert){
     if (form.alert.value == "" ){
     	 //alert(!isNaN(form.address2longitude.value));
    	 message += "- Descrizione richiesta\r\n";
         formTest = false;
      }
    }
 
 if(form.address1city){
     if (form.address1line1.value == "" ){
     	 //alert(!isNaN(form.address2longitude.value));
    	 message += "- Indirizzo richiesto\r\n";
         formTest = false;
      }
    }
   	 
 if(form.address1latitude){
     if (form.address1latitude.value == "" || form.address1latitude.value == "0.0" ){
     	 //alert(!isNaN(form.address2address1longitude.value));
    	 message += "- Latitudine richiesta\r\n";
         formTest = false;
      }
    }
 
 if(form.address1longitude){
     if (form.address1longitude.value == "" || form.address1longitude.value == "0.0" ){
     	 //alert(!isNaN(form.address2address1longitude.value));
    	 message += "- Longitudine richiesta\r\n";
         formTest = false;
      }
    }
 
 if(form.address1zip){
     if (form.address1zip.value == "" ){
     	 //alert(!isNaN(form.address2address1longitude.value));
    	 message += "- CAP richiesto\r\n";
         formTest = false;
      }
     else if (form.address1zip.value == "80100" ){
    	 //alert(!isNaN(form.address2address1longitude.value));
       	 message += "- CAP errato\r\n";
            formTest = false;
            form.address1zip.value = "";
         }
    }
 
	 if (formTest ){
		 loadModalWindow();
		 form.submit();
	     return true;
	   	
}else {
	    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	    return false;
	 }     	 
}

function calcolaCap(comune,campo){	
   	SuapDwr.getCapDaComuneTesto(comune, campo,{callback:calcolaCapCallBack,async:false});
   }
   
   
   function calcolaCapCallBack(val){
   	var campo = val[1];
   	var value = val[0];

   	if (value=='')
   		document.getElementById(campo).readOnly = false;
   	else
	   	document.getElementById(campo).value = value;
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


<script type="text/javascript">
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

<script language="JavaScript" TYPE="text/javascript">
var asl_code;
var asl_desc;
function aggiornaCampiIndirizzo(form){
	var campoAsl = form.siteId;
	var campoComune = form.address1city;
	var campoProvincia = form.address1state;
	var comune = campoComune.value;
	getAsl(comune);
	var idAsl = gestisciAsl(form.siteId);
	campoAsl.selectedIndex=idAsl;
	gestisciProvincia(campoProvincia, campoAsl);
}
function getAsl(comune)
{
	PopolaCombo.getValoriComuniASL(comune,{callback:getProvinciaCallBack,async:false}) ;
}
function getProvinciaCallBack(val)
{
asl_code = val[0];	
asl_desc =val[1];
	}
	function gestisciAsl(siteId){
		  var options = siteId.options;
		   for(j =0 ; j<siteId.length; j++){
		if (asl_code == options[j].value)
        		return j;
        }
		   return 0;
}
function gestisciProvincia(campoProvincia, campoAsl){
		if(campoAsl.value==201)
			campoProvincia.value="AV";
		else if(campoAsl.value==202)
			campoProvincia.value="BN";
		else if(campoAsl.value==203)
			campoProvincia.value="CE";
		else if(campoAsl.value==204 || campoAsl.value==205 || campoAsl.value==206)
			campoProvincia.value="NA";
		else if(campoAsl.value==207)
			campoProvincia.value="SA";
		else
			campoProvincia.value="";
}

function azzeraIndirizzi(form){
  	form.alert.value = "";
	form.address1state.value="";
	form.address1city.selectedIndex=-1;
	document.getElementById("address1zip").readOnly = true;
    document.getElementById("address1zip").value = "";
    document.getElementById("address1latitude").value = "";
    document.getElementById("address1longitude").value = "";
    document.getElementById("address1line1").value = "";
}
</script>


<form name="addAccount" action="ZoneControllo.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
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
<a href="ZoneControllo.do?command=SearchForm"><dhv:label name="">Zone di Controllo</dhv:label></a> > 
<%-- if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="ZoneControllo.do?command=SearchFar"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<%}%>
<%} else {%>
<a href="ZoneControllo.do?command=SearchFar"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >--%>
<a href="ZoneControllo.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Scheda Zone di Controllo</dhv:label></a> >
<%--}--%>
Modifica
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="zonecontrollo" selected="details" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='ZoneControllo.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='ZoneControllo.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
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
 
   <dhv:evaluate if="<%= SiteList.size() > 1 %>">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
        <dhv:evaluate if="<%= User.getSiteId() == -1 %>" >
        <%SiteList.setJsEvent("onChange=\"azzeraIndirizzi(this.form)\""); %>
          <%= SiteList.getHtmlSelect("siteId",OrgDetails.getSiteId()) %>
        </dhv:evaluate>
        <dhv:evaluate if="<%= User.getSiteId() != -1 %>" >
           <%= SiteList.getSelectedValue(User.getSiteId()) %>
          <input type="hidden" name="siteId" id="siteId" value="<%=User.getSiteId()%>" >
        </dhv:evaluate><font color="red">*</font>
      </td>
    </tr>
  </dhv:evaluate> 
 
<%--  <dhv:include name="name" none="true"> --%>
<!--   <tr> -->
<!--     <td nowrap class="formLabel" name="name" id="name"> -->
<%--       <dhv:label name="">Denominazione</dhv:label> --%>
<!--     </td> -->
<!--     <td> -->
<%--       <input type="text" size="50" maxlength="250" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> --%>
<!--     </td> -->
<!--   </tr> -->
<%--   </dhv:include> --%>

  <tr>
    <td nowrap class="formLabel" name="alert" id="alert">
      <dhv:label name="">Descrizione luogo di controllo</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="250" name="alert" value="<%= toHtmlValue(OrgDetails.getAlert()) %>">
    </td>
  </tr>
  
	 <input type="hidden" name="address1id" value="<%=OrgDetails.getAddressList().getAddress(5).getId() %>">
	 <input type="hidden" name="address1type" value="5">
  	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	  <input type="text" readonly size="28" id = "address1state" name="address1state" style="border:none" maxlength="80">
    	  <script>gestisciProvincia(document.getElementById("address1state"), document.getElementById("siteId"));</script>          
        <div id="div_provincia" style="display: none;">  
      <input type="text"  size="28" name="address1state1" maxlength="80" >  
          
       </div>      
    </td>
  </tr> 

 <tr>
	<td nowrap class="formLabel" name="TDaddress1city" id="TDaddress1city">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
	<select  name="address1city" id="address1city" onChange="aggiornaCampiIndirizzo(this.form)">  
	<option value="-1">Nessuna Selezione</option>
            
	 <%
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
                while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                	
        %>
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(OrgDetails.getAddressList().getAddress(5).getCity())) {%> selected="selected" <%} %>><%= prov4 %></option>	
              <%}%>
		
	</select> 
	<font color="red">*</font>
	</td>
  	</tr>
  	
  	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" id="address1line1" name="address1line1" maxlength="80" value="<%=OrgDetails.getAddressList().getAddress(5).getStreetAddressLine1()%>"><font color="red">*</font>
    </td>
  </tr>
  
  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" readonly size="5" name="address1zip" id="address1zip" maxlength="5" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" value="<%=OrgDetails.getAddressList().getAddress(5).getZip()%>"> 
<input type="button" value="Calcola CAP" onclick="if (document.getElementById('address1city').value!='') { calcolaCap(document.getElementById('address1city').value, 'address1zip') };" />
    </td>
  </tr>
  
  
<tr>
<td nowrap class="formLabel">COORDINATE</td>
<td>
LAT <input type="text" readonly name="address1latitude" id="address1latitude" onChange="controllaCoordinate(this, 'address1latitude')" value="<%=OrgDetails.getAddressList().getAddress(5).getLatitude()%>"/>
LON <input type="text" readonly name="address1longitude" id="address1longitude" onChange="controllaCoordinate(this, 'address1longitude')" value="<%=OrgDetails.getAddressList().getAddress(5).getLongitude()%>"/>	
<input id="coord1button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate(document.getElementById('address1line1').value, document.getElementById('address1city').value, document.getElementById('address1state').value, document.getElementById('address1zip').value, document.getElementById('address1latitude'), document.getElementById('address1longitude'));" />
</td></tr>
 

	 
	</table>
	  
<br>
  <input type="hidden" name="onlyWarnings" value=<%=(OrgDetails.getOnlyWarnings()?"on":"off")%> />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='ZoneControllo.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='ZoneControllo.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
  <input type="hidden" name="dosubmit" value="true">
  
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
<input type="hidden" name="action1" id="action1" value="modify">
</dhv:container>
</form>