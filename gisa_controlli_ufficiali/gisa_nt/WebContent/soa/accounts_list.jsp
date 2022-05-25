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
<%@ page import="java.util.*,org.aspcfs.modules.soa.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.soa.base.OrganizationList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statiStabilimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Soa.do"><dhv:label name="soa.soa">Accounts</dhv:label></a> > 
<dhv:label name="soa.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="soa.manage">Select an account to manage.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="SearchOrgListInfo"/></center></dhv:include>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    
    <th nowrap <% ++columnCount; %>>
      <strong><a href="Soa.do?command=Search&column=o.name"><dhv:label name="organization.name">Account Name</dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
    
    <th nowrap <% ++columnCount; %>>
      <strong>Stato SOA</strong>
    </th>
    
     <%--<th nowrap <% ++columnCount; %>>
      <strong>Categoria</strong>
    </th>--%>
    
   <th nowrap <% ++columnCount; %>>
          <strong>Stato Istruttoria</strong>
		</th>
    
<%--    <dhv:include name="organization.list.siteId" none="true"> --%>
<zeroio:debug value='<%="JSP::accounts_list.jsp "+ SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")+" == "+(String.valueOf(Constants.INVALID_SITE)) %>'/>
      <dhv:evaluate if='<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE)) %>'>
        <th <% ++columnCount; %>>
          <strong><dhv:label name="soa.site">Site</dhv:label></strong>
        </th>
      </dhv:evaluate>
		<th nowrap <% ++columnCount; %>>
          <strong>Approval Number</strong>
		</th>
		<%--th nowrap <% ++columnCount; %>>
          <strong>Tipo Check List</strong>
		</th--%>
        <th nowrap <% ++columnCount; %>>
          <strong>Inizio Attività</strong>
		</th>
  </tr>
<%
	Iterator j = OrgList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Organization thisOrg = (Organization)j.next();
%>
  <tr class="row<%= rowid %>">
    
	<td>
      <a href="Soa.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a>
	</td>
	<%if(thisOrg.getStatoLab()>-1) {%>
	<td>
      <%= statoLab.getSelectedValue(thisOrg.getStatoLab()) %>
	</td>
	<%}else{%>
		<td>&nbsp;
		</td> 
	<%}%>
	<%--<td>
      <%= toHtml(thisOrg.getCategoria()) %>
	</td>--%>
	
	
	<td nowrap >
    <%=statiStabilimenti.getSelectedValue(thisOrg.getStatoIstruttoria())%>
	</td>
		
<%--    <dhv:include name="organization.list.siteId" none="true"> --%>
      <dhv:evaluate if='<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE)) %>'>
        <td valign="top" nowrap><%= SiteIdList.getSelectedValue(thisOrg.getSiteId()) %></td>
      </dhv:evaluate>
<%--    </dhv:include> --%>
	  
      <% 
      	String lotto="";
      	if(thisOrg.getTipo_stab() != null && !thisOrg.getTipo_stab().equals("")){
      		lotto = "-"+thisOrg.getTipo_stab();
      	}	 
      %>
      
	<%--<td nowrap>
      <%= toHtml(thisOrg.getPartitaIva()) %>
	</td>
	<td nowrap>
      <%= toHtml(thisOrg.getCodiceFiscaleCorrentista()) %>
	</td>--%>
      <td>
      	
        <%= toHtmlValue(thisOrg.getNumAut()+lotto) %>
      </td>
	<%-- if(thisOrg.getAccountSize() > 0) {%>
    <td valign="top" nowrap>
      <%= OrgCategoriaRischioList.getSelectedValue(thisOrg.getAccountSize()) %>
       &nbsp;
       [<a href="Soa.do?command=ModificaCatRischio&orgId=<%=thisOrg.getOrgId()%>"><dhv:label name="">Modifica</dhv:label></a>]
    </td>
<%}else { %>
	<td valign="top" nowrap>
      Nessuna
       &nbsp;
       [<a href="Soa.do?command=ModificaCatRischio&orgId=<%=thisOrg.getOrgId()%>"><dhv:label name="accountsa.accounts_add.select">Aggiungi</dhv:label></a>]
    </td>
    <%} --%>
    <td nowrap>
      <%= toHtml(thisOrg.getDate2String()) %>
	</td>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="soa.search.notFound">No accounts found with the specified search parameters.</dhv:label><br />
      <a href="Soa.do?command=SearchForm"><dhv:label name="soa.soa_list.ModifySearch">Modify Search</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>

<form method="post" action="./Soa.do?command=export">
<%

String source="";
String numaut="";
 source = (String) request.getParameter("source");

if((request.getParameter("searchNumAut"))!=null){
    numaut=request.getParameter("searchNumAut");
    }

%>


<%--input type="submit" value="scarica soa .xls"--%>
<input type="hidden" value="<%=numaut %>">
</form>

