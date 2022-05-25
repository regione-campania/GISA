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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.punti_di_sbarco.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.punti_di_sbarco.base.Organization" scope="request"/>
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
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
       
<script language="JavaScript" TYPE="text/javascript">
  var indSelected = 0;
  var orgSelected = 1;
  var onLoad = 1;  

function checkNullString (text)
{
if (text!='' || text == null)
	return false ;
return true ;
}
  
  function doCheck(form) {
	  
      if (form.dosubmit.value == "false") {
          alert('falso')
      return true;
    } else {
      return(checkForm(form));
    }
  }
  
  function popolaComboComuni(idAsl)
	{
		idAsl = document.addAccount.siteId.value;
		PopolaCombo.getValoriComboComuniAsl(idAsl,setComuniComboCallback);
		//CountView.getProvincia(idAsl,setProv);		
	}

  function setComuniComboCallback(returnValue)
  {	        
  	  var select = document.forms['addAccount'].address1city; //Recupero la SELECT
        //Azzero il contenuto della seconda select
        
        for (var i = select.length - 1; i >= 0; i--)
      	  select.remove(i);

        indici = returnValue [0];
        valori = returnValue [1];
        //Popolo la seconda Select
        for(j =1 ; j<indici.length; j++){
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
  
  
  
  function checkForm(form) {
	
    var formTest = true;
    var formTest2 = true; 
    message = "";
    alertMessage = "";
    	
    
    if (form.siteId){
        if (form.siteId.value=='-1' || form.siteId.value=='' ){
          message += "- ASL richiesta\r\n";
          formTest = false;
        }
      }
    if (form.name){
        if ((checkNullString(form.name.value))){
          message += "- Nome Impresa richiesto\r\n";
          formTest = false;
        }
      }
    if (form.alert){
        if ((checkNullString(form.alert.value))){
          message += "-Descrizione richiesta\r\n";
          formTest = false;
        }
      }
    
    
	if (form.address1city.value == -1)
	{
		message += "- Comune Richiesto \r\n";
        formTest = false;
		}
     
   	 if (formTest ==true){

  	   form.submit() ;
	     
   	   	
  }else {
   	    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
   	   
   	 }     	 
  }
  
  function resetFormElements() {
    
  }
  
  function updateFormElements(index) {
  
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
<dhv:evaluate if='<%= (request.getParameter("form_type") == null || "punti_di_sbarco".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();updateFormElements(0);">
</dhv:evaluate>
<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();updateFormElements(1);">
</dhv:evaluate>
<form name="addAccount" action="PuntiSbarco.do?command=Insert&auto-populate=true"   method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate if="<%= !popUp %>">  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="PuntiSbarco.do?command=SearchForm"><dhv:label name="">Punti di sbarco</dhv:label></a> >
<dhv:label name="">Aggiungi Punti Di Sbarco</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<input type="button" value="Inserisci" name="Save" onclick="checkForm(document.addAccount)" >
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='PuntiSbarco.do?command=SearchForm';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<br /><br />
 <%-- SiteList.setJsEvent("onchange='javascript:popolaAsl();'"); --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi un Nuovo Punto Di Sbarco</dhv:label></strong>
    </th>
  </tr>

  <dhv:include name="name" none="true">
  
  <% SiteList.setJsEvent("onChange=popolaComboComuni()");%>
   <dhv:evaluate if="<%= SiteList.size() > 1 %>">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
        <dhv:evaluate if="<%= User.getSiteId() == -1 %>" >
          <%= SiteList.getHtmlSelect("siteId",OrgDetails.getSiteId()) %>
        </dhv:evaluate>
        <dhv:evaluate if="<%= User.getSiteId() != -1 %>" >
           <%= SiteList.getSelectedValue(User.getSiteId()) %>
          <input type="hidden" name="siteId" value="<%=User.getSiteId()%>" onChange=popolaComboComuni()>
        </dhv:evaluate><font color="red">*</font>
      </td>
    </tr>
  </dhv:evaluate> 
  <tr>
    <td nowrap class="formLabel" name="name" id="name">
      <dhv:label name="">Impresa</dhv:label>
    </td>
    <td>
      <input type="text" size="50"  name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font>
    </td>
  </tr>
   <tr>
    <td nowrap class="formLabel" name="alert" id="alert">
      <dhv:label name="">Descrizione</dhv:label>
    </td>
    <td>
      <input type="text" size="50" name="alert" value="<%= toHtmlValue(OrgDetails.getAlert()) %>"><font color="red">*</font>
    </td>
  </tr>
  </dhv:include>
  
  
  </table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Sede Operativa</dhv:label></strong>
	  <input type="hidden" name="address1type" value="5">
	  </th>
  </tr>
 <tr>
	<td nowrap class="formLabel" name="address1city" id="address1city">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
	<select  name="address1city" id="address1city">
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
  	  	
 
   
</table>
<br>
<dhv:evaluate if="<%= !popUp %>">  
<br />
</dhv:evaluate>  
<br />
<input type="hidden" name="onlyWarnings" value='<%=(OrgDetails.getOnlyWarnings()?"on":"off")%>' />
<%= addHiddenParams(request, "actionSource|popup") %>
<input type="button" value="Inserisci" name="Save" onclick="checkForm(document.addAccount)" >
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='PuntiSbarco.do?command=SearchForm';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true" />
</form>
</body>