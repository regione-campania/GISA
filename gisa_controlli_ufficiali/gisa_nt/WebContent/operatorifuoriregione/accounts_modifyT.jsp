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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.operatorifuoriregione.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.operatorifuoriregione.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="OrgDetailsAttori" class="org.aspcfs.modules.operatorifuoriregione.base.AttoriOpfuoriasl" scope="request"/>
<jsp:useBean id="cod1" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod2" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod3" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod4" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod5" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod6" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod7" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod8" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod9" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod10" class="java.lang.String" scope="request"/>
<jsp:useBean id="Conducente" class="org.aspcfs.modules.operatorifuoriregione.base.AttoriOpfuoriasl" scope="request"/>
<jsp:useBean id="Mittente" class="org.aspcfs.modules.operatorifuoriregione.base.AttoriOpfuoriasl" scope="request"/>
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
  
  
  		/*
       <%--- if((OrgDetails.getDunsType().equals("DIA Semplice"))&&(OrgDetails.getDunsType() != null)) { --%>
        
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

    	
      <%--}--%>
      */

      <%if (OrgDetails.getCessato()==0) {%>
		//document.addAccount.contractEndDate.type="hidden";
	
  <%}%>
        
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

  function updateFormElementsNew(index) {
	  
	  	if(index==1){
	  		document.getElementById("ciccio").style.visibility=""
	  		//document.addAccount.contractEndDate.type="";
	  		
	  	}
	  	else if(index==0){
	  		document.getElementById("ciccio").style.visibility="hidden"
			//document.addAccount.contractEndDate.type="hidden";
	  			  		
	  	}
	   
	    onLoad = 0;
	  }
  
  function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";
    if (form.name){
        if ((checkNullString(form.name.value))){
          message += "- Ragione Sociale richiesta\r\n";
          formTest = false;
        }
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
       
  	 if ((document.getElementById("tipoD").value=="Autoveicolo") && checkNullString(form.nomeCorrentista.value)&&(form.nomeCorrentista.disabled==false)){
         message += "- Targa/Codice autoveicolo richiesto\r\n";
         formTest = false;
       }
    
  
  
  
  <% if (OrgDetails.getEmailAddressList()!=null){ %>	 

  <dhv:include name="organization.emailAddresses" none="true">
<%
    for (int i=1; i<=OrgDetails.getEmailAddressList().size(); i++) {
%>
  <dhv:evaluate if="<%=(i>1)%>">else </dhv:evaluate>if (!checkEmail(form.email<%=i%>address.value)) {
      message += label("check.email", "- At least one entered email address is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
<%
    }
  
%>
  </dhv:include>
  
  <% } %>
  
  <dhv:include name="organization.url" none="true">
    if (!checkURL(form.url.value)) { 
      message += label("URL.invalid", "- URL entered is invalid.  Make sure there are no invalid characters\r\n");
      formTest = false;
    }
  </dhv:include>
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      var test = document.addAccount.selectedList;
      if (test != null) {
        selectAllOptions(document.addAccount.selectedList);
      }
      if(alertMessage != ""){
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
<body onLoad="javascript:initializeClassification();">
<form name="addAccount" action="OperatoriFuoriRegione.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
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
<!--<a href="AltriOperatori.do?command=DashboardScelta"><dhv:label name="">Altri Operatori</dhv:label></a> >--> 
<a href="OperatoriFuoriRegione.do"><dhv:label name="">Imprese Fuori Ambito ASL</dhv:label></a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="OperatoriFuoriRegione.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="OperatoriFuoriRegione.do?command=SearchForm&tipoD=Distributori">Cruscotto</a> >
	<%}%>
<%} else {%>
<a href="OperatoriFuoriRegione.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="OperatoriFuoriRegione.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Dettaglio Impresa Fuori Ambito ASL</dhv:label></a> >
<%}%>
<dhv:label name="">Modifica Impresa Fuori Ambito ASL</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="operatoriregione" selected="details" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='OperatoriFuoriRegione.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='OperatoriFuoriRegione.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
<br />
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
  
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Impresa Erogatrice</dhv:label></strong>
    </th>     
  </tr>
 <dhv:evaluate if='<%= (OrgDetails.getFuori_regione()) %>'>
 <tr class="containerBody">
 	<td nowrap class="formLabel"><dhv:label
						name="">Operatore</dhv:label></td>
 	<td>
        <%= toHtml("Fuori Regione") %>&nbsp;
    </td>
 </tr>
 </dhv:evaluate>
 <dhv:evaluate if='<%= (!OrgDetails.getFuori_regione()) %>'>
 <tr class="containerBody">
 	<td nowrap class="formLabel"><dhv:label
						name="">Operatore</dhv:label></td>
 	<td>
        <%= toHtml("di Altre ASL della Campania") %>&nbsp;
    </td>
 </tr>
 </dhv:evaluate>
 <input type="hidden" name="fuori_regione" value="<%= OrgDetails.getFuori_regione() %>"/>
 <% String tipoD = (String)session.getAttribute("tipoD");
%>
<input type="hidden" id="tipoD" name="tipoDest" value="<%=OrgDetails.getTipoDest()%>">
 <input type="hidden" name="siteId" value="16" >




  
    <dhv:include name="accounts-name" none="true">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="accounts.accounts_add.OrganizationName">Organization Name</dhv:label>
        </td>
        <td>
          <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
       </td>
      </tr>
    </dhv:include>
    <dhv:evaluate if="<%= (OrgDetails.getAccountNumber()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Numero Registrazione</dhv:label>
      </td>
      <td>
        <%= toHtmlValue(OrgDetails.getAccountNumber()) %>&nbsp;
              <input type="hidden" size="30" maxlength="30" name="codiceImpresaInterno" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>">
        
      </td>
    </tr>
   </dhv:evaluate>
      <tr class="containerBody">
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <input type="text" size="20" maxlength="11" name="partitaIva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="20" maxlength="16" name="codiceFiscale" value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>">    
    </td>
  </tr>
 
  <%if(OrgDetails.getTipoDest().equals("Autoveicolo")){%>
  		<tr class="containerBody" id="list"  >
    <td class="formLabel" nowrap  id="tipoVeicolo1">
      <dhv:label name="">Tipo Veicolo</dhv:label>
    </td>
    <td>
      <input id="tipoVeicolo" type="text" size="30" maxlength="50" name="contoCorrente" value="<%= toHtmlValue(OrgDetails.getContoCorrente()) %>">
    </td>
  </tr>
  <tr class="containerBody" id="list2" >
    <td class="formLabel" nowrap id="targaVeicolo1">
      <dhv:label name="">Identificativo Veicolo</dhv:label>
    </td>
    <td>
      <input id="targaVeicolo" type="text" size="20" title="Questo campo contiene la targa del veicolo o il numero di immatricolazione"  maxlength="10" name="nomeCorrentista" value="<%= toHtmlValue(OrgDetails.getNomeCorrentista()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
    <%} %>
    
  </table>
</br>  
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
    </th>
  </tr>
   <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Codice Fiscale
      </td>
      <td>
        <input type="text" size="50" name="codiceFiscaleRappresentante" maxlength="16" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Nome
      </td>
      <td>
        <input type="text" size="50" name="nomeRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>">
      </td>
    </tr>
  </dhv:include> 
    <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Cognome
      </td>
      <td>
        <input type="text" size="50" name="cognomeRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>">
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
  
    <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Fax</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="fax" maxlength="300" value="<%= toHtmlValue(OrgDetails.getFax()) %>">
      </td>
    </tr>
  </dhv:include>
  <!-- fine delle modifiche -->
  

</table>
<%
  boolean noneSelected = false;
%>

<dhv:include name="organization.phoneNumbers" none="true">

<%  
  int acount = 0;
  Iterator anumber = OrgDetails.getAddressList().iterator();
  while (anumber.hasNext()) {
    ++acount;
    OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
%> 

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
        <strong><dhv:label name="accounts.accounts_add.Addressess">Sede legale</dhv:label></strong>
	    <input type="hidden" name="address<%=acount %>id" value="<%=thisAddress.getId() %>">
    </th>
    <input type="hidden" name = "address<%=acount %>type" value ="<%= thisAddress.getType()%>">
  </tr> 
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
     <%if(OrgDetails.getFuori_regione()){%>
    	 <dhv:label name="">Nazione D'origine</dhv:label>
     <%}else{%>
     	 <dhv:label name="">ASL Appartenenza</dhv:label>
     <%}%>
    </td>
    <td>
    <%
    if(OrgDetails.getFuori_regione()){
    %>
    		<input type="text" size="40" name="address1line2" maxlength="80" value="<%= thisAddress.getStreetAddressLine2() %>">
   <%}else{%>
    	<%= SiteList.getHtmlSelect("site2",thisAddress.getStreetAddressLine3()) %>
    	<input type="hidden" size="40" name="address1line3" maxlength="80" value="<%= thisAddress.getStreetAddressLine3() %>">
    
    <%}%>
    </td>
  </tr>  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
     <input type="text" name="address1city" id="address1city" value="<%= (thisAddress.getCity()!= null ?(thisAddress.getCity()):("")) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
   	<input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate>
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
       <input type="text" size="10" name="address<%= acount %>state" maxlength="80" value=""> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate>
    </td>
  </tr>
  </table><br>
<%    
  }
  ++acount;
  OrganizationAddress thisAddress = new OrganizationAddress();
  thisAddress.setCountry(applicationPrefs.get("SYSTEM.COUNTRY"));
%>

<br />
</dhv:include>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="requestor.requestor_add.AdditionalDetails">Additional Details</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="">Luogo di controllo/note</dhv:label>
      </td>
      <td>
        <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA>
      </td>
    </tr>
  </table>
  <br />
  <input type="hidden" name="onlyWarnings" value=<%=(OrgDetails.getOnlyWarnings()?"on":"off")%> />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if(request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='OperatoriFuoriRegione.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='OperatoriFuoriRegione.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="statusId" value="<%=OrgDetails.getStatusId()%>">
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
</dhv:container>
</form>
