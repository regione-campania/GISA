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
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Prova" class="org.aspcfs.modules.laboratorihaccp.base.Prova" scope="request"/>
<jsp:useBean id="Address" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="AccountSizeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="MatriciHaccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DenominazioniHaccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Ente" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<jsp:useBean id="idProva" class="java.lang.String" scope="request"/>

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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/elenco_prove.js"></script>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript" src="javascript/controlli_sottoattivita.js"></script>       
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
	    	
	    
	    if (form.codiceMatrice.value && form.codiceMatrice.value=="-1"){
	    	 			 message += "- Controllare di aver selezionato un valore per il campo Materiale / Prodotto / Matrice \r\n";
	     				 formTest = false;
	     		
	 	 }   

	    if (form.codiceDenominazione.value && form.codiceDenominazione.value=="-1"){
	    	 message += "- Controllare di aver selezionato un valore per il campo Denominazione della Prova \r\n";
	     				 formTest = false;
	     		
	 	 }   

	    if (form.codiceEnte.value && form.codiceEnte.value=="-1"){
	    	message += "- Controllare di aver selezionato un valore per il campo Ente di Accreditamento \r\n";
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
      
     }
  }
  
  function updateFormElements(index) {
    if (document.getElementById) {}
            
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

  function chiudiPopUp(flagInserimento,orgId)
  {
	  if(flagInserimento != null && flagInserimento != 'null')
	  {
		  window.opener.document.forms[0].action = 'LaboratoriHACCP.do?command=Details&orgId='+orgId;
		  window.opener.document.forms[0].submit();
		  window.close();
	  }

	}
  
</script>
  <body  onblur="if(focus_==true){window.focus();}" onmouseout="focus_=true;" onmouseover="focus_=false;"  onload = "chiudiPopUp('<%=request.getAttribute("inserito") %>',<%=request.getAttribute("orgId") %>)">


<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'></dhv:evaluate>
<form name="addAccount" action="ElencoProveHACCP.do?command=Update&orgId=<%= Prova.getOrgId()%>&id=<%= Prova.getId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate if="<%= !popUp %>">  </dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" name="Save" onClick="return checkForm(this.form);window.close();">
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:window.close();">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<br /><br />
 <%-- SiteList.setJsEvent("onchange='javascript:popolaAsl();'"); --%>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

 <%-- <%
   			
   int cont1=0;
   Iterator prove = OrgDetails.getProveList().iterator();
   Object prov[] = null;
   int j = 0;
   if (prove.hasNext()) {
     while (prove.hasNext()) {
       Prova thisProva = (Prova)prove.next();
                
   
 %>
 --%> 

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Modifica Prova</dhv:label></strong>
    </th>
  </tr>
 <input type="hidden" name="orgId" value="<%= request.getParameter("orgId") %>" >

    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Materiale / Prodotto / Matrice</dhv:label>
      </td>
      <td>
      <table>
      <tr>
      <td id = "id_matrice"> <%=MatriciHaccp.getSelectedValue(Prova.getCodiceMatrice()) %></td>
      <td>
       &nbsp;[<a href="javascript:popLookupSelectorCustomMatrici('description','short_description','lookup_matrici_labhaccp','');">Seleziona Matrice</a>] 
              <input type = "hidden" name = "codiceMatrice" id = "codiceMatrice" value ="<%= Prova.getCodiceMatrice()%>" > 
              <input type="hidden" name="matrice" value="" >
              <font color="red">*</font>
      </td>
      </tr>
      </table>
             
             
     </td>
     </tr>
      <tr>
       <td nowrap class="formLabel">
        <dhv:label name="">Denominazione della Prova</dhv:label>
      </td>
      
      <td>
      	<table>
      	<tr>
      	<td id = "id_prova"><%=DenominazioniHaccp.getSelectedValue(Prova.getCodiceDenominazione()) %></td>
      
      	<td>
      	&nbsp;[<a href="javascript:popLookupSelectorCustomProve('description','short_description','lookup_denominazioni_labhaccp','');">Seleziona Prova</a>] 
            <input type = "hidden" name = "codiceDenominazione" id = "codiceDenominazione" value ="<%= Prova.getCodiceDenominazione()%>"> 
            <input type="hidden" name="denominazione" value="" >
   			<font color="red">*</font>
      	</td>
      	</tr>
      	</table>	
            	
      </td>
      </tr>
 <tr>
    <td nowrap class="formLabel" name="norma" id="norma">
      <dhv:label name="">Norma / Metodo</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="80" name="norma" value="<%= toHtmlValue(Prova.getNorma()) %>">
    </td>
  </tr>

	<tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Ente Accreditamento Prove</dhv:label>
      </td>
      <td>
          <%= Ente.getHtmlSelect("codiceEnte", Prova.getCodiceEnte()) %><font color="red">*</font>
          <input type="hidden" name="ente" value="<%= Prova.getCodiceEnte() %>" >
      </td>
    </tr>

    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Accreditamento</dhv:label>
      </td>
      <td>
      	 SI <input type="radio" name="accreditata" value=true <%if(Prova.getAccreditata()) {%>checked="checked"<%} %> >
    	 NO <input type="radio" name="accreditata" value=false  <%if(!Prova.getAccreditata()) {%>checked="checked"<%} %> >
		 <input type="hidden" name="accreditata" value="<%= Prova.getAccreditata() %>" >
		     	 
      </td>
    </tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Decreto</dhv:label>
      </td>
      <td>
      	<input type="text" size="50" maxlength="80" name="decreto" value="<%= toHtmlValue(Prova.getDecreto()) %>">
      </td>
    </tr>

  <%-- <% } } %> --%>
  </table>
<br>
<dhv:evaluate if="<%= !popUp %>">  
<br />
</dhv:evaluate>  
<br />
<input type="hidden" name="onlyWarnings" value='<%=(Prova.getOnlyWarnings()?"on":"off")%>' />
<%= addHiddenParams(request, "actionSource|popup") %>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="return checkForm(this.form);window.close();" />
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:window.close();">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:window.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true" />
</form>
</body>