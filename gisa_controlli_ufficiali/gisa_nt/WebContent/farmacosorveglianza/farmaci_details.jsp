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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.farmacosorveglianza.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<%@page import="org.aspcfs.modules.audit.base.Audit"%>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="idConfezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="idTiposomministrazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.farmacosorveglianza.base.Farmaci" scope="request"/>
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
<a href="FarmacosorveglianzaFar.do?command=SearchFormFar"><dhv:label name="">Farmaci</dhv:label></a> > 
<%-- if (request.getParameter("return") == null) { %>
<a href="FarmacosorveglianzaFar.do?command=SearchFar"><dhv:label name="">Risultati Ricerca</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="FarmacosorveglianzaFar.do?command=DashboardFar">Cruscotto</a> >
<%}--%>
<dhv:label name="">Scheda Farmaco</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

</dhv:evaluate>
<dhv:permission name="farmacosorveglianza-farmacosorveglianza-report-view">
  <table width="100%" border="0">
    <tr>
      <td nowrap align="right">
        <%-- 
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="FarmacosorveglianzaFar.do?command=PrintReport&id=<%= OrgDetails.getId() %>"><dhv:label name="accounts.osa.print">Stampa Scheda O.S.A.</dhv:label></a>
       --%>
      </td>
    </tr>
  </table>
</dhv:permission>
<% String param1 = "idFarmaco=" + OrgDetails.getIdFarmaco();
   %>
  
<dhv:container name="farmacosorveglianza" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= OrgDetails.isTrashed() %>">
<input type="hidden" name="idFarmaco" value="<%= OrgDetails.getIdFarmaco() %>"> 
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="farmacosorveglianza-farmacosorveglianza-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='FarmacosorveglianzaFar.do?command=Restore&idFarmaco=<%= OrgDetails.getIdFarmaco() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
 
    <dhv:permission name="farmacosorveglianza-farmacosorveglianza-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='FarmacosorveglianzaFar.do?command=ModifyFar&idFarmaco=<%= OrgDetails.getIdFarmaco() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  
  
    <%--dhv:permission name="farmacosorveglianza-farmacosorveglianza-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='FarmacosorveglianzaFar.do?command=Enable&idFarmaco=<%= OrgDetails.getIdFarmaco() %>';">
    </dhv:permission--%>
 
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="farmacosorveglianza-farmacosorveglianza-delete"><input type="button" value="<dhv:label name="accounts.accounts_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('FarmacosorveglianzaFar.do?command=ConfirmDeleteFar&id=<%=OrgDetails.getIdFarmaco()%>&popup=true','FarmacosorveglianzaFar.do?command=SearchFar', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
</dhv:evaluate>
<dhv:permission name="farmacosorveglianza-farmacosorveglianza-edit,farmacosorveglianza-farmacosorveglianza-delete"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni Principali</dhv:label></strong>
    </th>
  </tr>
  
  <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Nome Commerciale</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getNomeCommerciale()) %>&nbsp;
       </td>
    </tr>
       <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Ditta</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(OrgDetails.getDitta()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Gruppo Merceologico</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(OrgDetails.getGruppoMerceologico()) %>&nbsp;
    </td>
  </tr>
    <dhv:evaluate if="<%= hasText(OrgDetails.getPrincipioAttivo()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Principio Attivo</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getPrincipioAttivo()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate>  
<%--<dhv:evaluate if="<%= hasText(OrgDetails.getTipoSomministrazione()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Tipo Somministrazione</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getTipoSomministrazione()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>
<%Integer qt = OrgDetails.getQuantita();
String quant = qt.toString(); %>
<dhv:evaluate if="<%= (OrgDetails.getQuantita()>-1) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Quantità Farmaco per Confezione</dhv:label>
    </td>
    <td>
       <%= toHtml(quant) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>--%>
<dhv:evaluate if="<%= (OrgDetails.getIdTiposomministrazione()>-1) %>">
    <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Tipo somministrazione</dhv:label>
    </td>
    <td class="containerBody"> 
       <%= idTiposomministrazione.getSelectedValue(OrgDetails.getIdTiposomministrazione()) %>&nbsp;
       </td>
  </tr>
  </dhv:evaluate>
<dhv:evaluate if="<%= (OrgDetails.getIdConfezione()>-1) %>">
    <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Confezione</dhv:label>
    </td>
    <td class="containerBody"> 
       <%= idConfezione.getSelectedValue(OrgDetails.getIdConfezione()) %>&nbsp;</td>
  </tr>
  </dhv:evaluate>
<%Double prezzo = OrgDetails.getPrezzo();
  String price = prezzo.toString();
  %>
  <dhv:evaluate if="<%= hasText(price) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      <dhv:label name="">Prezzo</dhv:label>
			</td>
			<td>
         <%= toHtml(price) %> Euro&nbsp;
			</td>
		</tr>
  </dhv:evaluate>
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
<dhv:permission name="farmacosorveglianza-farmacosorveglianza-edit,farmacosorveglianza-farmacosorveglianza-delete"><br></dhv:permission>
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="farmacosorveglianza-farmacosorveglianza-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='FarmacosorveglianzaFar.do?command=RestoreFar&idFarmaco=<%= OrgDetails.getIdFarmaco() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  
    <dhv:permission name="farmacosorveglianza-farmacosorveglianza-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='FarmacosorveglianzaFar.do?command=ModifyFar&idFarmaco=<%= OrgDetails.getIdFarmaco() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
 
    <%--dhv:permission name="farmacosorveglianza-farmacosorveglianza-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='FarmacosorveglianzaFar.do?command=EnableFar&idFarmaco=<%= OrgDetails.getIdFarmaco() %>';">
    </dhv:permission--%>
  
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="farmacosorveglianza-farmacosorveglianza-delete"><input type="button" value="<dhv:label name="accounts.accounts_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('FarmacosorveglianzaFar.do?command=ConfirmDeleteFar&id=<%=OrgDetails.getIdFarmaco()%>&popup=true','FarmacosorveglianzaFar.do?command=SearchFar', 'Delete_account','320','200','yes','no');"></dhv:permission>
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