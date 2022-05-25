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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.distributori.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoDitributore" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.distributori.base.Organization" scope="request"/>
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
<script language="JavaScript">
  indSelected = 0;
  orgSelected = 0; 
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
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


	    
	numero=form.numElementi.value



	for(i=1;i<=numero;i++){

		
		data=document.getElementById("dataInstallazione"+i).value;
		matricola=document.getElementById("matricola"+i).value;
		comune=document.getElementById("comune"+i).value;
		indirizzo=document.getElementById("indirizzo"+i).value;
		prov=document.getElementById("provincia"+i).value;
		cap=document.getElementById("cap"+i).value;
		lat=document.getElementById("latitudine"+i).value;
		longit=document.getElementById("longitudine"+i).value;

		if(comune=="" || data=="" || matricola == "" || prov ==""  || indirizzo=="" || lat=="" || longit==""){

			  message += "- Controllare di aver inserito tutti i campi il il distributore "+i+"\r\n";
		        formTest = false;
			}
		

			
			


			
		}





	    
	    if (document.addAccount.siteId.value == "-1"){
	        message += "- ASL richiesta\r\n";
	        formTest = false;
	     }
	  
	     if (checkNullString(form.name.value)){
	        message += "- Ragione Sociale richiesta\r\n";
	        formTest = false;
	      }
	     if (checkNullString(form.contractEndDate.value)){
	        message += "- Data Individuazione richiesta\r\n";
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
	     if (form.address1latitude && form.address1latitude.value!=""){
	     
	      		if ((orgSelected == 1)  ){
	      			if ((form.address1latitude.value < 4431788.049190) || (form.address1latitude.value >4593983.337630)){
	       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 4431788.049190 e 4593983.337630  \r\n";
	       				 formTest = false;
	       			}		 
	      		}
	   	 }   
	   	 
	   	 if (form.address1latitude && form.address1latitude.value!="" ){
	      	
	      		if ((orgSelected == 1)  ){
	      		
	      			if ((form.address1longitude.value < 2417159.584320) || (form.address1longitude.value > 2587487.362260)){
	       			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2417159.584320 e 2587487.362260  \r\n";
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
    var url = "Distributori.do?command=OwnerJSList&form=addAccount&widget=owner&allowBlank=false&siteId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
</script>
<body onLoad="javascript:initializeClassification();">
<form name="addAccount" action="Distributori.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
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
<a href="Distributori.do"><dhv:label name="">Distributori Automatici</dhv:label></a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="Distributori.do?command=Search"><dhv:label name="">Risultati Ricerca</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="Distributori.do?command=Dashboard">Cruscotto</a> >
	<%}%>
<%} else {%>
<a href=Distributori.do?command=Search"><dhv:label name="">Risultati Ricerca</dhv:label></a> >
<a href="Distributori.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Dettaglio Distributore Automatico</dhv:label></a> >
<%}%>
<dhv:label name="">Modifica Distributore Automatico</dhv:label>
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
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Distributori.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Distributori.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
<br />
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="abusivismi.abusivismi_modifyaa.ModifyPrimaryInformation">Ditta</dhv:label></strong>
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

<dhv:include name="organization.types" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="abusivismi.abusivismi.types">Account Type(s)</dhv:label> 
    </td>
  	<td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <select multiple name="selectedList" id="selectedList" size="5">
            <dhv:evaluate if="<%=OrgDetails.getTypes().isEmpty()%>">
            <option value="-1"><dhv:label name="abusivismi.abusivismi_add.NoneSelected">None Selected</dhv:label></option>
            </dhv:evaluate>
            <dhv:evaluate if="<%=!(OrgDetails.getTypes().isEmpty())%>">
      <%
              Iterator i = OrgDetails.getTypes().iterator();
              while (i.hasNext()) {
                LookupElement thisElt = (LookupElement)i.next();
      %>
              <option value="<%=thisElt.getCode()%>"><%=thisElt.getDescription()%></option>
            <%}%>
            </dhv:evaluate>      
            </select>
          </td>
          <td valign="top">
            <input type="hidden" name="previousSelection" value="" />
            &nbsp;[<a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_distributori_types');"><dhv:label name="abusivismi.abusivismi_add.select">Select</dhv:label></a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
</dhv:include>
<dhv:include name="organization.classification" none="true">
  <tr class="containerBody" style="display: none">
    <td class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.Classification">Classification</dhv:label>
    </td>
    <td>
      <input type="radio" name="form_type" value="organization" onClick="javascript:updateFormElements(0);" <%= !OrgDetails.getIsIndividual() ? " checked" : "" %>>
      <dhv:label name="abusivismi.abusivismi_add.Organization">Organization</dhv:label>
      <input type="radio" name="form_type" value="individual" onClick="javascript:updateFormElements(1);" <%= OrgDetails.getIsIndividual() ? " checked" : "" %>>
      <dhv:label name="abusivismi.abusivismi_add.Individual">Individual</dhv:label>
    </td>
  </tr>
</dhv:include>
<tr class="containerBody">
			<td class="formLabel" nowrap>
				<dhv:label name="">Ragione Sociale</dhv:label>
			</td>
			<td>
				<input type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
			</td>
</tr>
  

   <dhv:include name="organization.contractEndDate" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Inizio</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="contractEndDate" timestamp="<%= OrgDetails.getContractEndDate() %>" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" /><%= showError(request, "dataFineAttivitaError") %>
      </td>
    </tr>
    </dhv:include>  
 
    <tr class="containerBody">
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="20" maxlength="16" name="codiceFiscale" value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>">    
    </td>
  </tr>
    <tr class="containerBody">
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <input type="text" size="20" maxlength="11" name="partitaIva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>">
    </td>
  </tr>
  


</table>

<br>





	












<%
  boolean noneSelected = false;
%>
<dhv:include name="organization.phoneNumbers" none="true">

<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Indirizzo Sede Legale</dhv:label></strong>
    </th>
  </tr>

<%  
  int acount = 0;
  Iterator anumber = OrgDetails.getAddressList().iterator();
  while (anumber.hasNext() ) {
    ++acount;
    OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
%>    
  <tr class="containerBody" style="display:none">
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
    <td class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", thisAddress.getType()) %>
      <input type="radio" name="primaryAddress" value="<%=acount%>" <%= thisAddress.getPrimaryAddress() ? " checked" : ""%>><dhv:label name="abusivismi.abusivismi_add.primary">Primary</dhv:label>
      <input type="checkbox" name="address<%= acount %>delete" value="on"><dhv:label name="abusivismi.abusivismi_modify.MarkToRemove">mark to remove</dhv:label>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
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
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry(), thisAddress.getState()) %>
      </span>
      
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>"  value="<%= toHtmlValue(thisAddress.getState()) %>">
      </span>
    </td>
  </tr>
  
 
  <!--
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.county">County</dhv:label></td>
    <td><input type="text" name="address<%= acount %>county" size="28" maxlenth="80" value="<%= toHtmlValue(thisAddress.getCounty()) %>"></td>
  </tr>
   -->
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude" size="10" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLatitude()) : "") %>"></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" size="10" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLongitude()) : "") %>"></td>
  </tr>
  <tr class="containerBody">
    <td colspan="2" style="display:none">
      &nbsp;
    </td>
  </tr> 
<%    
  }
  ++acount;
  OrganizationAddress thisAddress = new OrganizationAddress();
  thisAddress.setCountry(applicationPrefs.get("SYSTEM.COUNTRY"));
%>
  <tr class="containerBody" style="display:none">
    <td class="formLabel">
     <dhv:label name="abusivismi.abusivismi_add.Type">Type</dhv:label>
    </td>
    <td>
      <%= OrgAddressTypeList.getHtmlSelect("address" + acount + "type", "") %>
      <input type="radio" name="primaryAddress" value="<%=acount%>"><dhv:label name="abusivismi.abusivismi_add.primary">Primary</dhv:label>
    </td>
  </tr>
  <tr class="containerBody" style="display:none">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.City">City</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>city" maxlength="80">
    </td>
  </tr>
  <tr class="containerBody" style="display:none">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80">
    </td>
  </tr>
 
  <tr class="containerBody" style="display:none">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12">
    </td>
  </tr>
  <tr class="containerBody" style="display:none">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style="<%= StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <%= StateSelect.getHtmlSelect("address" + acount + "state", thisAddress.getCountry()) %>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style="<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>">
        <input type="text" size="25" name="<%= "address" + acount + "otherState" %>">
      </span>
    </td>
  </tr>
  
  
  <!--
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.county">County</dhv:label></td>
    <td><input type="text" name="address<%= acount %>county" size="28" maxlenth="80"></td>
  </tr>
   -->
  <tr class="containerBody" style="display:none">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude" size="10" value=""></td>
  </tr>
  <tr class="containerBody" style="display:none">
    <td class="formLabel" nowrap><dhv:label name="abusivismi.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" size="10" value=""></td>
  </tr>

</table>
<br />

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Titolo rappresentante</dhv:label>
    </td>
    <td>  <!-- titoloRappresentante è il nome della variabile nel bean -->
       <%= TitoloList.getHtmlSelect("titoloRappresentante",OrgDetails.getTitoloRappresentante()) %></td>
  </tr>
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Codice Fiscale
      </td>
      <td>
        <input type="text" size="50" name="codiceFiscaleRappresentante" maxlength="16" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Nome
      </td>
      <td>
        <input type="text" size="50" name="nomeRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font>
      </td>
    </tr>
  </dhv:include> 
    <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Cognome
      </td>
      <td>
        <input type="text" size="50" name="cognomeRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font>
      </td>
    </tr>
  </dhv:include>
  
  <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="dataNascitaRappresentante" timestamp="<%= OrgDetails.getDataNascitaRappresentante() %>"  showTimeZone="false" />
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di nascita</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtmlValue(OrgDetails.getLuogoNascitaRappresentante()) %>">
    </td>
  </tr>
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Email</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="emailRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getEmailRappresentante()) %>">
      </td>
    </tr>
  </dhv:include>
  
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Telefono</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="telefonoRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getTelefonoRappresentante()) %>">
      </td>
    </tr>
  </dhv:include>
  
   
  <!-- fine delle modifiche -->
  

</table>
<br>

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
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Distributori.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Didstributori.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="statusId" value="<%=OrgDetails.getStatusId()%>">
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
</dhv:container>
</form>
