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
  - Version: $Id: accounts_list.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.modules.base.*" %>

<%@page import="org.aspcfs.modules.opu.base.Operatore"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%><jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>

<jsp:useBean id="OpuList" class="org.aspcfs.modules.opu.base.OperatoreList" scope="request"/>

<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditList" class="org.aspcfs.modules.audit.base.AuditList" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">

  <%-- Preload image rollovers for drop-down menu --%>
  
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="">Stabilimenti 852</dhv:label></a> > 
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="accounts.manage">Select an account to manage.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<% int columnCount = 0; %>

<% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    
    <th nowrap <% ++columnCount; %>>
      <strong><a href="Accounts.do?command=Search&column=o.name"><dhv:label name="">Impresa</dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
   <th nowrap <% ++columnCount; %>>

      <strong><dhv:label name="organization.accountNumber">Numero Registrazione</dhv:label></strong>

    </th>
    <th nowrap <% ++columnCount; %>>
          <strong>Targa Autoveicolo/Sede Operativa</strong>
		</th>
    
    
         <th <% ++columnCount; %>>
          <strong><dhv:label name="accounts.site">Site</dhv:label></strong>
        </th>
      
        <th nowrap <% ++columnCount; %>>
          <strong>Partita IVA</strong>
		</th>
		 <th nowrap <% ++columnCount; %>>
          <strong>Codice Fiscale</strong>
		</th>
        <th nowrap <% ++columnCount; %>>
          <strong>Codice ISTAT Principale</strong>
		</th>
		<th nowrap <% ++columnCount; %>>
      <strong>Categoria Rischio</strong>
    </th>
    <th nowrap <% ++columnCount; %>>
      <strong>Stato</strong>
    </th>
    <th nowrap <% ++columnCount; %>>Tipologia</th>
        <th nowrap <% ++columnCount; %>>
          <strong>Inserito da</strong>
		</th>
		<th nowrap <% ++columnCount; %>>
          <strong>Modificato da</strong>
		</th>
  </tr>
<%
	Iterator j = OrgList.iterator();
	if ( j.hasNext() || OpuList.iterator().hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Organization thisOrg = (Organization)j.next();
%>
  <tr class="row<%= rowid %>">
    
	<td>
	
      <a  onclick="<%=(thisOrg.getOrgId()==0)? "alert('Nessuna sede produttiva esistente');" : "" %>"  href="<%=(thisOrg.getTipo_opu_operatore()==1) ? "Accounts.do?command=Details&orgId="+thisOrg.getOrgId() : (thisOrg.getOrgId()>0) ? "Stabilimenti852.do?command=Details&stabId="+thisOrg.getOrgId() : "#"%>" ><%= toHtml(thisOrg.getName()) %></a>
	</td>
	
	<td>
      <%= toHtml(thisOrg.getAccountNumber()) %>
	</td>

		
	<td>
      <%= toHtml((thisOrg.getNomeCorrentista()!=null) ? (thisOrg.getNomeCorrentista()) : ("")) %>
	</td>
		
		
        <td valign="top" nowrap><%= SiteIdList.getSelectedValue(thisOrg.getSiteId()) %></td>

	  
    
	<td nowrap>
       <%= toHtml(thisOrg.getPartitaIva()) %>
       </td>
       <td>
       <%= toHtml(thisOrg.getCodiceFiscale()) %>
	</td>
	<td nowrap>
      <%= toHtml(thisOrg.getCodiceFiscaleCorrentista()) %>
	</td>
	<td>
	<%= (thisOrg.getCategoriaRischio()) %>
	</td>
	<td>
	
			
		<%=((thisOrg.getSource()==1) ? (( thisOrg.getDateF()!= null && thisOrg.getDateF().before(d)) ? "<font color='red'>Cessato</font>" : (thisOrg.getCessato()==1) ? "<font color='red'>Cessato</font>" : "In Attivita") : (thisOrg.getCessato()==0 ? "In Attivita" : (thisOrg.getCessato()==2 ? "Sospeso":"<font color='red'>Cessato</font>")) ) %>
	
	</td>
	<td><%=(thisOrg.getTipo_opu_operatore()==1) ? "PREGRESSO" : "OPERATORE UNICO" %></td>
    <td nowrap>
      <dhv:username id="<%= thisOrg.getEnteredBy() %>" />
	</td>
	<td nowrap>
      <dhv:username id="<%= thisOrg.getModifiedBy() %>" />
	</td>
  </tr>
<%}%>

<%} else {%>

  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="accounts.search.notFound">No accounts found with the specified search parameters.</dhv:label><br />
      <a href="Accounts.do?command=SearchForm"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>


<!--  INCLUDE PER RICERCA UNICA -->
<%-- <jsp:include page="/ricercaunica/unica.jsp"> --%>
<%-- <jsp:param name="ragioneSociale" value="<%=SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>" /> --%>
<%-- <jsp:param name="partitaIva" value="<%=SearchOrgListInfo.getSearchOptionValue("searchPartitaIva") %>" /> --%>
<%-- <jsp:param name="codFiscaleRappresentante" value="<%=SearchOrgListInfo.getSearchOptionValue("searchCodiceFiscale") %>" /> --%>
<%-- <jsp:param name="comuneStabilimento" value="<%=SearchOrgListInfo.getSearchOptionValue("searchAccountCity") %>" /> --%>
<%--  <jsp:param name="indirizzoStabilimento" value="" /> --%>
<%--  <jsp:param name="asl" value="<%=SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId") %>" /> --%>
<%--  <jsp:param name="numeroRegistrazione" value="<%=SearchOrgListInfo.getSearchOptionValue("searchAccountNumber") %>" /> --%>
<%--   </jsp:include> --%>

