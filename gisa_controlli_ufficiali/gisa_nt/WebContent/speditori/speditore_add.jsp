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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.speditori.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.speditori.base.Organization" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<jsp:useBean id="Nazioni" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Province" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="popup"
	class="java.lang.String" scope="request" />	
	
<%@ include file="../initPage.jsp" %>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
       
<script language="JavaScript" TYPE="text/javascript">

function controllaStatoItaliano()
{
	
	if ( document.getElementById('address1country').value != 113 ) {
		document.getElementById('siteId').value 	= -1;
		
		document.getElementById('siteIdList').value = -1;
		document.getElementById('siteIdList').disabled  = true;

		document.getElementById('address1city').value = '';
		document.getElementById('address1city').disabled  = true;

		document.getElementById('address1state').value 	= -1;
		document.getElementById('address1state').disabled  = true;

		$(".displayable").hide();
		
	} 
	else {
		document.getElementById('siteIdList').disabled  = false;
		document.getElementById('address1state').disabled  = false;
		document.getElementById('address1city').disabled  = false;

		$(".displayable").show();
	}
		
};

function setAsl(){
	
	document.getElementById('siteId').value = document.getElementById('siteIdList').value;
};

function controllaProvince()
{
	var asl = document.getElementById('address1state').value; 
	if ( asl != "7" && asl != "11" && asl != "22" && asl != "60" && asl != "87") {
		
		document.getElementById('siteId').value = 16;
		document.getElementById('siteIdList').value = 16;
		document.getElementById('siteIdList').disabled = true;		
	} 
	else if(asl == "7"){
			document.getElementById('siteId').value = 201;
			document.getElementById('siteIdList').value = 201;
			document.getElementById('siteIdList').disabled = true;
	}
	else if(asl == "11"){
			document.getElementById('siteId').value = 202;
			document.getElementById('siteIdList').value = 202;
			document.getElementById('siteIdList').disabled = true;
	}
	else if(asl == "22"){
			document.getElementById('siteId').value = 203;
			document.getElementById('siteIdList').value = 203;
			document.getElementById('siteIdList').disabled = true;
	}
	else if(asl == "60"){
			document.getElementById('siteId').value = 204;
			document.getElementById('siteIdList').value = 204;
			document.getElementById('siteIdList').disabled = false;
	}
	else if(asl == "87"){
			document.getElementById('siteId').value = 207 ;
			document.getElementById('siteIdList').value = 207;
			document.getElementById('siteIdList').disabled = true;
	}
	
		
};


function doCheck(form) {
    if (form.dosubmit.value == "false") {
    return true;
  } else {
    return(checkForm(form));
  }
  	
}

function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";
    
     if (form.name.value==""){
       message += "- Denominazione richiesta\r\n";
        formTest = false;
     }

     if (form.account_number.value==""){
         message += "- Codice Azienda richiesto\r\n";
          formTest = false;
     }

     if (form.nome_rappresentante.value==""){
         message += "- Proprietario richiesto\r\n";
          formTest = false;
     }
     
     
     if (form.address1country.value== "-1"){
         message += "- Stato richiesto\r\n";
          formTest = false;
     }

    /*Se lo stato è Italia, fai i controlli */ 
    if(form.address1country.value == 113) {
        
     	if (form.address1state.value == "-1"){
         	message += "- Provincia richiesta\r\n";
            formTest = false;
     	}
     
     	if (form.address1city.value==""){
           message += "- Comune richiesto\r\n";
           formTest = false;
     	}

     	if (form.siteId.value == "" || form.siteId.value == "-1" || form.siteIdList.value == "-1"){
         	message += "- ASL richiesta\r\n";
            formTest = false;
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
      return true;
    }
  }

</script>


<%
if (request.getAttribute("msg")!=null)
{
%>
<font color = "red">Lo speditore non è stato Trovarlo.Inserirlo dalla maschera seguente</font>
<%	
}
%>
<dhv:evaluate if='<%= (request.getParameter("form_type") == null || "speditore".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();">
</dhv:evaluate>
<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();updateFormElements(1);">
</dhv:evaluate>
<form name="addAccount" action="Speditore.do?command=Insert&auto-populate=true"  onSubmit="return doCheck(this);"  method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
  <%
if (request.getAttribute("msg")!=null)
{
	%>
	<input type = "hidden" name = "fromlist" value = "si">
	<%
}
%>
<dhv:evaluate if="<%= !popUp %>">  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Speditore.do?command=SearchForm"><dhv:label name="">Speditore</dhv:label></a> >
<dhv:label name="">Aggiungi Speditore</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';">
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Speditore.do?command=SearchForm';this.form.dosubmit.value='false';">
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
      <strong><dhv:label name="">Aggiungi un Nuovo Speditore</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel" name="name" id="name">
      <dhv:label name="">Denominazione</dhv:label>
    </td>
    <td>
      <input type="text" size="50"  name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel" name="account_number" id="account_number">
      <dhv:label name="">Codice Azienda</dhv:label>
    </td>
    <td>
      <input type="text" size="50" name="account_number" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel" name="nome_rappresentante" id="nome_rappresentante">
      <dhv:label name="">Proprietario</dhv:label>
    </td>
    <td>
      <input type="text" size="50" name="nome_rappresentante" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>

  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr>
		<td nowrap class="formLabel">ASL</td>
			<td>
				<% SiteList.setJsEvent("onChange=\"javascript:setAsl();\""); %>
				<%=SiteList.getHtmlSelect("siteIdList",OrgDetails.getSiteId())%>
				<input type="hidden" name="siteId" id="siteId" value="" />
				<font color="red" class="displayable">*</font>
			</td>	
			
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
		<input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
  <tr class="containerBody">
       <td class="formLabel">Stato</td>
       <td>
           <% Nazioni.setJsEvent("onChange=\"javascript:controllaStatoItaliano();\""); %>
           <%=Nazioni.getHtmlSelect( "address1country",(Nazioni.getDefaultElementCode()) )%>
           <font color="red">*</font>
       </td>
       <td>
       		<input type="hidden" name="address1type" id="address1type" value="1" />
       </td>
  </tr>
  <tr class="containerBody">
       <td class="formLabel">Provincia</td>
       <td>
           <% Province.setJsEvent("onChange=\"javascript:controllaProvince();\"");  %>
           <%=Province.getHtmlSelect("address1state", Province.getDefaultElementCode())%>
           <font color="red" class="displayable">*</font>
      </td>
  </tr>
  <tr class="containerBody">
    	<td class="formLabel">Comune</td>
    	<td>
           <input type="text" id ="address1city" name="address1city" value="" />
           <font color="red" class="displayable">*</font>
        </td>
  </tr>
  
  </table> 
  
<br>

<br>
<dhv:evaluate if="<%= !popUp %>">  
<br />
</dhv:evaluate>  
<br />
<input type="hidden" name="onlyWarnings" value='<%=(OrgDetails.getOnlyWarnings()?"on":"off")%>' />
<%= addHiddenParams(request, "actionSource|popup") %>
<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';" />
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Speditore.do?command=SearchForm';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true" />
</form>
</body>