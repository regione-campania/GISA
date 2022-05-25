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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.parafarmacie.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<%@page import="org.aspcfs.modules.audit.base.Audit"%>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipologiaList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.parafarmacie.base.Veterinari" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
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
<a href="ParafarmacieVet.do?command=SearchFormVet"><dhv:label name="">Veterinari</dhv:label></a> > 
<%-- if (request.getParameter("return") == null) { %>
<a href="ParafarmacieVet.do?command=SearchVet"><dhv:label name="">Risultati Ricerca</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="ParafarmacieVet.do?command=DashboardVet">Cruscotto</a> >
<%}--%>
<dhv:label name="">Scheda Veterinario</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:permission name="parafarmacie-parafarmacie-report-view">
  <table width="100%" border="0">
    <tr>
      <td nowrap align="right">
        <%-- 
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="ParafarmacieVet.do?command=PrintReport&id=<%= OrgDetails.getId() %>"><dhv:label name="accounts.osa.print">Stampa Scheda O.S.A.</dhv:label></a>
       --%>
      </td>
    </tr>
  </table>
</dhv:permission>
<% String param1 = "idVeterinario=" + OrgDetails.getIdVeterinario();
   %>
  
<dhv:container name="parafarmacie" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= OrgDetails.isTrashed() %>">
<input type="hidden" name="idVeterinario" value="<%= OrgDetails.getIdVeterinario() %>"> 
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="parafarmacie-parafarmacie-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='ParafarmacieVet.do?command=Restore&idVeterinario=<%= OrgDetails.getIdVeterinario() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
 
    <dhv:permission name="parafarmacie-parafarmacie-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='ParafarmacieVet.do?command=ModifyVet&idVeterinario=<%= OrgDetails.getIdVeterinario() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  
  
    <%--<dhv:permission name="parafarmacie-parafarmacie-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='ParafarmacieVet.do?command=Enable&idVeterinario=<%= OrgDetails.getIdVeterinario() %>';">
    </dhv:permission>--%>
 
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="parafarmacie-parafarmacie-delete"><input type="button" value="<dhv:label name="accounts.accounts_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('ParafarmacieVet.do?command=ConfirmDeleteVet&id=<%=OrgDetails.getIdVeterinario()%>&popup=true','ParafarmacieVet.do?command=SearchVet', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
</dhv:evaluate>
<dhv:permission name="parafarmacie-parafarmacie-edit,parafarmacie-parafarmacie-delete"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni Principali</dhv:label></strong>
    </th>
  </tr>
  
  <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          Cognome
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getCognome()) %>&nbsp;
                <!-- input type="hidden" size="50" maxlength="80" name="cognome" value="<%= toHtmlValue(OrgDetails.getCognome()) %>"-->
       </td>
    </tr>
     <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname2" id="orgname2">
          Nome
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getNome()) %>&nbsp;
          <!-- input type="hidden" size="50" maxlength="80" name="nome" value="<%= toHtmlValue(OrgDetails.getNome()) %>"-->
       </td>
    </tr>
       <tr class="containerBody">
        <td nowrap class="formLabel" name="codiceFiscale" id="codiceFiscale">
          Codice Fiscale
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>&nbsp;
          <!-- input type="hidden" size="16" maxlength="16" name="codiceFiscale" value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>"-->
       </td>
    </tr>
    <dhv:evaluate if="<%= (OrgDetails.getTipologiaId()>-1) %>">
    <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Tipologia</dhv:label>
    </td>
    <td class="containerBody"> 
       <%= TipologiaList.getSelectedValue(OrgDetails.getTipologiaId()) %>&nbsp;
       <input type="hidden" name="tipologiaId" value="<%=OrgDetails.getTipologiaId()%>" >
       <input type="hidden" name="idVeterinario" value="<%=OrgDetails.getIdVeterinario()%>" >
       </td>
  </tr>
  </dhv:evaluate>
    <dhv:evaluate if="<%= hasText(OrgDetails.getIndirizzo()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Indirizzo</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getIndirizzo()) %>&nbsp;
         		<!-- input type="hidden" size="40" name="indirizzo" maxlength="80" value="<%= toHtmlValue(OrgDetails.getIndirizzo()) %>"-->
			</td>
		</tr>
  	</dhv:evaluate>  
<dhv:evaluate if="<%= hasText(OrgDetails.getCitta()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Comune</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getCitta()) %>&nbsp;
       <!-- input type="hidden" name="citta" id="citta" value="<%= toHtmlValue(OrgDetails.getCitta()) %>"-->
    </td>
  </tr>
</dhv:evaluate>
 <dhv:evaluate if="<%= hasText(OrgDetails.getPostalCode()) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      <dhv:label name="">C.A.P.</dhv:label>
			</td>
			<td>
         <%= toHtml(OrgDetails.getPostalCode()) %>&nbsp;
         <!-- input type="hidden" size="5" name="postalCode" maxlength="5" value="<%= toHtmlValue(OrgDetails.getPostalCode()) %>"-->
			</td>
		</tr>
  </dhv:evaluate>
<dhv:evaluate if="<%= hasText(OrgDetails.getProvincia()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Provincia</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getProvincia()) %>&nbsp;
       <!-- input type="hidden" size="28" name="provincia" maxlength="80" value="<%= toHtmlValue(OrgDetails.getProvincia()) %>"-->    
    </td>
  </tr>
</dhv:evaluate>
  <%--dhv:include name="accounts-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
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
</dhv:include--%>
<% String st = SiteList.getSelectedValue(OrgDetails.getSiteId()); 
if(st.equals("--Nessuno--")){ %>    
<%}else{ %>
<dhv:include name="accounts-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 0 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
      <%= SiteList.getSelectedValue(OrgDetails.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
    </td>
   
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 0 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
  </dhv:include>
  <%} %>
</table>
<br/>
<br/>
 <%if(hasText(OrgDetails.getNote1())){ %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Informazioni Aggiuntive</dhv:label></strong>
	  </th>
  </tr>
 
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Note</dhv:label>
    </td>
    <td>
      <%=toHtml(OrgDetails.getNote1()) %>&nbsp;
    </td>
  </tr>
</table>
<br />
<%} %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="requestor.requestor_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_calls_list.Entered">Entered</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= OrgDetails.getEnteredBy() %>" />
      <zeroio:tz timestamp="<%= OrgDetails.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_contacts_calls_details.Modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= OrgDetails.getModifiedBy() %>" />
      <zeroio:tz timestamp="<%= OrgDetails.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
</table>
<dhv:permission name="parafarmacie-parafarmacie-edit,parafarmacie-parafarmacie-delete"><br></dhv:permission>
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="parafarmacie-parafarmacie-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='ParafarmacieVet.do?command=RestoreVet&idVeterinario=<%= OrgDetails.getIdVeterinario() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  
    <dhv:permission name="parafarmacie-parafarmacie-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='ParafarmacieVet.do?command=ModifyVet&idVeterinario=<%= OrgDetails.getIdVeterinario() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
 
    <%--<dhv:permission name="parafarmacie-parafarmacie-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='ParafarmacieVet.do?command=EnableVet&idVeterinario=<%= OrgDetails.getIdVeterinario() %>';">
    </dhv:permission>--%>
  
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="parafarmacie-parafarmacie-delete"><input type="button" value="<dhv:label name="accounts.accounts_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('ParafarmacieVet.do?command=ConfirmDeleteVet&id=<%=OrgDetails.getIdVeterinario()%>&popup=true','ParafarmacieVet.do?command=SearchVet', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
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