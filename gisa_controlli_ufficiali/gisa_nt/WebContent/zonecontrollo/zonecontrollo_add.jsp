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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.zonecontrollo.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.zonecontrollo.base.Organization" scope="request"/>
<jsp:useBean id="Address" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="AccountSizeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="provinceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
       
<script language="JavaScript" TYPE="text/javascript">
  var indSelected = 0;
  var orgSelected = 1;
  var onLoad = 1;  
  
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
  
  function doCheck(form) {
	  
      if (form.dosubmit.value == "false") {
         
      return true;
    } else {
      return(checkForm(form));
    }
  }
  
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

  
  function checkForm(form) {
	
    var formTest = true;
    var formTest2 = true; 
    message = "";
    alertMessage = "";
    	
//     if (form.name){
//         if (form.name.value==''){
//           message += "- Denominazione richiesta\r\n";
//           formTest = false;
//         }
//       }
    
    if (form.siteId){
        if (form.siteId.value=='-1' || form.siteId.value=='' ){
          message += "- ASL richiesta\r\n";
          formTest = false;
        }
      }
    
    if(form.address1city){
     if (form.address1city.value == "-1" || form.address1city.value == "" ){
     	 //alert(!isNaN(form.address2address1longitude.value));
    	 message += "- Comune richiesto\r\n";
         formTest = false;
      }
    }
    
    if(form.alert){
        if (form.alert.value == "" ){
        	 //alert(!isNaN(form.address2address1longitude.value));
       	 message += "- Descrizione richiesta\r\n";
            formTest = false;
         }
       }
    
    if(form.address1city){
        if (form.address1line1.value == "" ){
        	 //alert(!isNaN(form.address2address1longitude.value));
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
  
  function resetFormElements() {
   
  }
  
  function updateFormElements(index) {
    
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
    document.getElementById("address1zip").readOnly = true;
    document.getElementById("address1zip").value = "";
    document.getElementById("address1latitude").value = "";
    document.getElementById("address1longitude").value = "";
    document.getElementById("address1line1").value = "";

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

</script>


<dhv:evaluate if='<%= (request.getParameter("form_type") == null || "zonecontrollo".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="updateFormElements(0);">
</dhv:evaluate>
<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="updateFormElements(1);">
</dhv:evaluate>
<form name="addAccount" action="ZoneControllo.do?command=Insert&auto-populate=true"   method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate if="<%= !popUp %>">  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="ZoneControllo.do?command=SearchForm"><dhv:label name="">Zone di Controllo</dhv:label></a> >
<dhv:label name="">Aggiungi Zone di Controllo</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<input type="button" value="Inserisci" name="Save" onClick="javascript:checkForm(document.addAccount)" />
<dhv:evaluate if="<%= !popUp %>">
  <input type="button" value="Annulla" onClick="javascript:location.href='ZoneControllo.do?command=SearchForm'">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<br /><br />
 <%-- SiteList.setJsEvent("onchange='javascript:popolaAsl();'"); --%>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi una nuova Zona di Controllo</dhv:label></strong>
    </th>
  </tr>

  <dhv:include name="name" none="true">
    
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
          <input type="hidden" name="siteId" value="<%=User.getSiteId()%>" >
        </dhv:evaluate><font color="red">*</font>
      </td>
    </tr>
  </dhv:evaluate> 
<!--   <tr> -->
<!--     <td nowrap class="formLabel" name="name" id="name"> -->
<%--       <dhv:label name="">Denominazione</dhv:label> --%>
<!--     </td> -->
<!--     <td> -->
<%--       <input type="text" size="50"  name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> --%>
<!--     </td> -->
<!--   </tr> -->
   <tr>
    <td nowrap class="formLabel" name="alert" id="alert">
      <dhv:label name="">Descrizione luogo del controllo</dhv:label>
    </td>
    <td>
      <input type="text" size="50" name="alert" id="alert" value="<%= toHtmlValue(OrgDetails.getAlert()) %>">
    <font color="red">*</font> </td> 
  </tr>
  </dhv:include>
  
  
<!--   </table> -->
<!-- <br> -->
<!-- <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details"> -->
<!--   <tr> -->
<!--     <th colspan="2"> -->
<%-- 	    <strong><dhv:label name="">Sede Operativa</dhv:label></strong> --%>
<!-- 	  <input type="hidden" name="address1type" value="5"> -->
<!-- 	  </th> -->
<!--   </tr> -->

  
   <input type="hidden" name="address1type" value="5">
  	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	  <input type="text" readonly size="28" id = "address1state" name="address1state" style="border:none" maxlength="80" >          
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
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(AddressSedeOperativa.getCity())) {%> selected="selected" <%} %>><%= prov4 %></option>	
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
      <input type="text" size="40" id="address1line1" name="address1line1" maxlength="80" ><font color="red">*</font>
    </td>
  </tr>
<!--   <tr> -->
<!--     <td nowrap class="formLabel"> -->
<%--       <dhv:label name="">C/O</dhv:label> --%>
<!--     </td> -->
<!--     <td> -->
<!--       <input type="text" size="40" name="address1line2" maxlength="80" > -->
<!--     </td> -->
<!--   </tr> -->

  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" readonly size="5" name="address1zip" id="address1zip" maxlength="5" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"> 
<input type="button" value="Calcola CAP" onclick="if (document.getElementById('address1city').value!='') { calcolaCap(document.getElementById('address1city').value, 'address1zip') };" />
    </td>
  </tr>
  
  
<tr>
<td nowrap class="formLabel">COORDINATE</td>
<td>
LAT <input type="text" readonly name="address1latitude" id="address1latitude" value="" onChange="controllaCoordinate(this, 'address1latitude')" />
LON <input type="text" readonly name="address1longitude" id="address1longitude" value="" onChange="controllaCoordinate(this, 'address1longitude')" />	
<input id="coord1button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate(document.getElementById('address1line1').value, document.getElementById('address1city').value, document.getElementById('address1state').value, document.getElementById('address1zip').value, document.getElementById('address1latitude'), document.getElementById('address1longitude'));" />
</td></tr>
 
  
<!--    <tr class="containerBody"> -->
<%--     <td class="formLabel" nowrap><dhv:label name="requestor.address.address1latitude">address1latitude</dhv:label></td> --%>
<!--     <td> -->
<!--     	<input type="text" id="address1address1latitude" name="address1address1latitude" size="30" > -->
<!--     </td> -->
<!--   </tr> -->
<!--   <tr class="containerBody"> -->
<%--     <td class="formLabel" nowrap><dhv:label name="requestor.address.address1longitude">address1longitude</dhv:label></td> --%>
<!--     <td><input type="text" id="address1address1longitude" name="address1address1longitude" size="30"  ></td> -->
<!--   </tr> -->
<!--   <tr style="display: block"> -->
<!--     <td colspan="2"> -->
<!--     	<input id="coordbutton" type="button" value="Calcola Coordinate"  -->
<!--     	onclick="javascript:showCoordinate(document.getElementById('address1line1').value, document.forms['addAccount'].address1city.value,document.forms['addAccount'].address1state.value, document.forms['addAccount'].address1zip.value, document.forms['addAccount'].address1address1latitude, document.forms['addAccount'].address1address1longitude);" />  -->
<!--     </td> -->
<!--   </tr>  -->
  	
  	
  	

  	  	
  
   
</table>
<br>
<dhv:evaluate if="<%= !popUp %>">  
<br />
</dhv:evaluate>  
<br />
<input type="hidden" name="onlyWarnings" value='<%=(OrgDetails.getOnlyWarnings()?"on":"off")%>' />
<%= addHiddenParams(request, "actionSource|popup") %>
<input type="button" value="Inserisci" name="Save" onClick="javascript:checkForm(document.addAccount)" />
<dhv:evaluate if="<%= !popUp %>">
  <input type="button" value="Annulla" onClick="javascript:location.href='ZoneControllo.do?command=SearchForm'">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true" />
</form>
</body>