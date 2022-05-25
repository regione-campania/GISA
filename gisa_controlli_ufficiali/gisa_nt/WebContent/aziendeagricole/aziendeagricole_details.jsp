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
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />

<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.aziendeagricole.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<%@page import="org.aspcfs.modules.audit.base.Audit"%>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.aziendeagricole.base.Organization" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%if (refreshUrl!=null && !"".equals(refreshUrl)){ %>
<script language="JavaScript" TYPE="text/javascript">
parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>
<%}%>	
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="AziendeAgricole.do?command=SearchForm"><dhv:label name="">Aziende Agricole</dhv:label></a> > 
<%--if (request.getParameter("return") == null) { %>
<a href="AziendeAgricole.do?command=Search&tipoRicerca=<%= request.getAttribute("tipoRicerca")%>"><dhv:label name="">Risultati Ricerca</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<%}--%>
<dhv:label name="">Scheda Aziende Agricole</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

</dhv:evaluate>
<% String param1 = "orgId=" + OrgDetails.getOrgId();
   %>
   
    <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
<dhv:permission name="">
	<table width="100%" border="0">
		<tr>
			<%-- aggiunto da d.dauria--%>

			<td nowrap align="right">
			<!-- img
				src="images/icons/stock_print-16.gif" border="0" align="absmiddle"
				height="16" width="16" /> <a
				href="SchedaPrint.do?command=PrintReport&file=stabilimenti&id=<%= OrgDetails.getId() %>"><dhv:label
				name="stabilimenti.osa.print">Stampa Scheda stabilimenti</dhv:label></a-->
				
				
				
 		  <%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda Aziende Agricole" value="Stampa Scheda Aziende Agricole"		onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '-1', '-1', '-1', 'aziendeagricole', 'SchedaAziendeAgricole');"--%>
 
		<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda" value="Stampa Scheda"		onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '-1', '-1', '-1', '11');">
 
					
				
			</td>


			<%-- fine degli inserimenti --%>
		</tr>
	</table>
</dhv:permission>
<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %>
  
<dhv:container name="aziendeagricole" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= OrgDetails.isTrashed() %>">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>"> 
<%

if(OrgDetails.getAccountNumber() ==null || "".equals(OrgDetails.getAccountNumber()))
{
%>
<input type="button" title="Genera Numero Registrazione" value="Genera Numero Registrazione"	onclick="location.href='AziendeAgricole.do?command=GeneraNumero&orgId=<%=OrgDetails.getOrgId() %>'">
<%
}
%>
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="aziendeagricole-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='AziendeAgricole.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>

<dhv:evaluate if="<%=!OrgDetails.isTrashed() && (OrgDetails.getNotes()!= null && ! OrgDetails.getNotes().equals("IMPORTATO AUTOMATICAMENTE SULLA BASE DI COMUNICAZIONE"))%>">
 
    <dhv:permission name="aziendeagricole-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='AziendeAgricole.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
 
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="aziendeagricole-delete"><input type="button" value="<dhv:label name="">Elimina</dhv:label>" onClick="javascript:popURLReturn('AziendeAgricole.do?command=ConfirmDelete&id=<%=OrgDetails.getOrgId()%>&popup=true','AziendeAgricole.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  	
  </dhv:evaluate>
</dhv:evaluate>

<dhv:evaluate if="<%=!OrgDetails.isTrashed() && (OrgDetails.getNotes()!= null &&  OrgDetails.getNotes().startsWith("IMPORTATO AUTOMATICAMENTE SULLA BASE DI COMUNICAZIONE"))%>">

		&nbsp;&nbsp;&nbsp;&nbsp;<a class="ovalbutton" href ="#dialog10" name = "modal"><span>Completa Scheda</span></a>
</dhv:evaluate>


<dhv:permission name="aziendeagricole-edit,aziendeagricole-delete"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni Principali</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
        <td nowrap class="formLabel" name="name" id="name">
          <dhv:label name="">Denominazione</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getName()) %>&nbsp;
       </td>
  </tr>
  <tr class="containerBody">
        <td nowrap class="formLabel" name="site_id" id="site_id">
          <dhv:label name="">Asl</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(SiteList.getSelectedValue(OrgDetails.getSiteId())) %>&nbsp;
       </td>
  </tr>
  <tr class="containerBody">
        <td nowrap class="formLabel" name="account_number" id="account_number">
          <dhv:label name="">Numero Registrazione</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getAccountNumber()) %>&nbsp;
       </td>
  </tr>
  
  <%
  if(!"".equals(OrgDetails.getCodiceImpresaInterno()) && OrgDetails.getCodiceImpresaInterno()!=null)
  {
  %>
   <tr class="containerBody">
        <td nowrap class="formLabel">
          <dhv:label name="">Codice Interno</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getCodiceImpresaInterno()) %>&nbsp;
       </td>
  </tr>
  <%} %>
  
   <tr class="containerBody">
        <td nowrap class="formLabel" name="codice_fiscale" id="codice_fiscale">
          Codice Fiscale
        </td>
        <td>
          <% if( OrgDetails.getCodice_Fiscale()!= null) {  %>		
	          <%= toHtmlValue(OrgDetails.getCodice_Fiscale()) %>&nbsp;
	      <% } else { %>
	      		<%= toHtmlValue("N.D") %>
	      	<%} %>    
       </td>
  </tr>
  <tr class="containerBody">
        <td nowrap class="formLabel" name="partita_iva" id="partita_iva">
          Partita IVA
        </td>
        <td>
          <% if( OrgDetails.getPartita_Iva()!= null) {  %>		
	          <%= toHtmlValue(OrgDetails.getPartita_Iva()) %>&nbsp;
	      <% } else { %>
	      		<%= toHtmlValue("N.D") %>
	      	<%} %>    
       </td>
  </tr>
  <tr class="containerBody">
        <td nowrap class="formLabel" name="notes" id="notes">
          <dhv:label name="">Attivita'</dhv:label>
        </td>
        <td>
          <% if( OrgDetails.getNotes()!= null) {  %>		
	          <%= toHtmlValue(OrgDetails.getNotes()) %>&nbsp;
	      <% } else { %>
	      		<%= toHtmlValue("N.D") %>
	     <%} %>      
       </td>
  </tr>
   <dhv:include name="organization.date1" none="true">
    <dhv:evaluate if="<%= (OrgDetails.getdate1() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Inizio Attivitita'</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OrgDetails.getdate1() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
      </td>
    </tr>
    </dhv:evaluate>
 </dhv:include> 
</table>

<%


  Iterator iaddress = OrgDetails.getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
%>  
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:evaluate if="<%= thisAddress.getType() == 1 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede legale</dhv:label></strong>
	  </dhv:evaluate>
	  <dhv:evaluate if="<%= thisAddress.getType() == 5 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede operativa</dhv:label></strong>
	  </dhv:evaluate>  
	  </th>
	  </tr>
  <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <%= toHtml(thisAddress.getTypeName()) %>
      </td>
      <td>
      
        <%= toHtml(thisAddress.toString()) %>&nbsp;<br/><%=thisAddress.getGmapLink() %>
      
        <dhv:evaluate if="<%=thisAddress.getPrimaryAddress() %>">        
          <dhv:label name="requestor.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate> 
      </td>
     </tr> 
</table>
	  <%} } %>

<dhv:permission name="aziendeagricole-edit,aziendeagricole-delete"><br></dhv:permission>
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="aziendeagricole-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='AziendeAgricole.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed() && (OrgDetails.getNotes()!= null && ! OrgDetails.getNotes().equals("IMPORTATO AUTOMATICAMENTE SULLA BASE DI COMUNICAZIONE"))%>">
 
    <dhv:permission name="aziendeagricole-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='AziendeAgricole.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
 
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="aziendeagricole-delete"><input type="button" value="<dhv:label name="">Elimina</dhv:label>" onClick="javascript:popURLReturn('AziendeAgricole.do?command=ConfirmDelete&id=<%=OrgDetails.getOrgId()%>&popup=true','AziendeAgricole.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  	
  </dhv:evaluate>
</dhv:evaluate>

<dhv:evaluate if="<%=!OrgDetails.isTrashed() && (OrgDetails.getNotes()!= null &&  OrgDetails.getNotes().startsWith("IMPORTATO AUTOMATICAMENTE SULLA BASE DI COMUNICAZIONE"))%>">

		&nbsp;&nbsp;&nbsp;&nbsp;<a class="ovalbutton" href ="#dialog10" name = "modal"><span>Completa Scheda</span></a>
</dhv:evaluate>
</dhv:container>
<input type="hidden" name="source" value="searchForm">
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>










	
	
	
	
<script>


$(document).ready(function() {	

	
	//select all the a tag with name equal to modal
	$('a[name=modal]').click(function(e) {
		//Cancel the link behavior
		e.preventDefault();
		
		//Get the A tag
		var id = $(this).attr('href');
	
		//Get the screen height and width
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();
	
		//Set heigth and width to mask to fill up the whole screen
		$('#mask').css({'width':maskWidth,'height':maskHeight});
		
		//transition effect		
		$('#mask').fadeIn(1000);	
		$('#mask').fadeTo("slow",0.8);	
	
		//Get the window height and width
		var winH = $(window).height();
		var winW = $(window).width();
              
		//Set the popup window to center
		$(id).css('top',  winH/2-$(id).height()/2);
		$(id).css('left', winW/2-$(id).width()/2);
		
		//transition effect
		$(id).fadeIn(2000); 
	
	});
	
	//if close button is clicked
	$('.window .close').click(function (e) {
		//Cancel the link behavior
		e.preventDefault();
		
		$('#mask').hide();
		$('.window').hide();
	});		
	
	//if mask is clicked
	$('#mask').click(function () {
		$(this).hide();
		$('.window').hide();
	});			
	
});

</script>
<style>
body {
	font-family: verdana;
	font-size: 15px;
}

a {
	color: #333;
	text-decoration: none
}

a:hover {
	color: #ccc;
	text-decoration: none
}

#mask {
	position: absolute;
	left: 0;
	top: 0;
	z-index: 9000;
	background-color: #000;
	display: none;
}

#boxes .window {
	position: absolute;
	left: 0;
	top: 0;
	width: 675px;
	height: 658;
	display: none;
	z-index: 9999;
	padding: 20px;
}

#boxes
#dialog
#
{
width:675px;
height:680;
padding:10px;
background-color:#ffffff
;}
#dialog4 {
	width: 100%;
	height: 100%;
	padding: 10px;
	background-color: #ffffff;
	overflow: scroll;
}

#dialog10 {
	width: 100%;
	height: 100%;
	padding: 10px;
	background-color: #ffffff;
	overflow: scroll;
}


#boxes #dialog1 {
	width: 375px;
	height: 203px;
}

#dialog1 .d-header {
	background: url(images/login-header.png) no-repeat 0 0 transparent;
	width: 375px;
	height: 150px;
}

#dialog1 .d-header input {
	position: relative;
	top: 60px;
	left: 100px;
	border: 3px solid #cccccc;
	height: 22px;
	width: 200px;
	font-size: 15px;
	padding: 5px;
	margin-top: 4px;
}

#dialog1 .d-blank {
	float: left;
	background: url(images/login-blank.png) no-repeat 0 0 transparent;
	width: 267px;
	height: 53px;
}

#dialog1 .d-login {
	float: left;
	width: 108px;
	height: 53px;
}

#boxes #dialog2 {
	background: url(images/notice.png) no-repeat 0 0 transparent;
	width: 326px;
	height: 229px;
	padding: 50px 0 20px 25px;
}
</style>




<input type="hidden" id="idChecklist" name="idChecklist"
	value="<%=request.getAttribute("idChecklist_corrente")%>" />


<div id="boxes">



<div id="dialog10" class="window"><a href="#" class="close" /><font
	color="red">CHIUDI</font></a> <br>
<script>
function checkFormCompletaDati()
{
	flag = true ;
	msg = 'Controlla di aver inserito le seguenti informazioni : \n'
	if (document.aziendeagricolecompletadati.account_number.value == '')
	{
		flag = false ;
		msg += '- Numero di Registrazione \n' ;
	}

	if (document.aziendeagricolecompletadati.date1.value == '')
	{
		flag = false ;
		msg += '- Data inizio Attivita \n' ;
	}
	if (document.aziendeagricolecompletadati.notes.value == '')
	{
		flag = false ;
		msg += '- Attivita \n' ;
	}

	if (flag==true)
		document.aziendeagricolecompletadati.submit();
	else
	{
		alert (msg);
	}
}
	
</script>
<form method="post" name = "aziendeagricolecompletadati" action = "AziendeAgricole.do?command=CompletaDati&orgId=<%= OrgDetails.getId() %>">
 <table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">

				<tr>
					<th colspan="2"><%=OrgDetails.getNote() %>Completamento Scheda</th>
				</tr>
				
				<tr>
					<td class="formLabel" nowrap>Attivita</td>
					<td>
					<input type = "text" name = "notes">
					    <%= showAttribute(request, "date1Error") %><font color="red">*</font>
					</td>
				</tr>
				
				<tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data Inizio Attività</dhv:label>
      </td>
      <td>
      	<input readonly type="text" id="date1" name="date1" size="10" value="<%= ""%>"/>
		<a href="#" onClick="cal19.select(document.forms[0].date1,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        <%= showAttribute(request, "date1Error") %><font color="red">*</font>
      </td>
    </tr>
				
				<tr>
  <td nowrap class="formLabel" name="account_number" id="account_number">
      <dhv:label name="">Numero Registrazione</dhv:label>
    </td>
    <td>
    	<input type="text" size="50" name="account_number" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>"><font color="red">*</font>
    </td>
  </tr>
				<tr><td colspan="2"><input type = "button" onclick="checkFormCompletaDati()" value = "Salva"></td></tr>

</table>
</form>

</div>





<!-- Mask to cover the whole screen -->
<div id="mask"></div>

</div>