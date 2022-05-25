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
<%@ page import="java.util.*,org.aspcfs.modules.operatori_commerciali.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.operatori_commerciali.base.OrganizationList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<a href="OperatoriCommerciali.do">OperatoriCommerciali</a> > 
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
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="SearchOrgListInfo"/></center></dhv:include>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th nowrap <% ++columnCount; %>>
      <strong><a href="OperatoriCommerciali.do?command=Search&column=o.name">
      <dhv:label name="organization.name">Account Name</dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>

        <th <% ++columnCount; %>>
          <strong><dhv:label name="accounts.site">Site</dhv:label></strong>
        </th>
        <th nowrap <% ++columnCount; %>>
           <strong><a href="OperatoriCommerciali.do?command=Search&column=oa.city"><dhv:label name="accounts.accounts_add.City">City</dhv:label></a></strong>
	 	   <%= SearchOrgListInfo.getSortIcon("oa.city") %> 
	 	</th>
        <th nowrap <% ++columnCount; %>>
      <strong>Partita IVA</strong>     
    </th> 
    	<th nowrap <% ++columnCount; %>>
       	Rappresentante Legale
       </th>
	
     
       <th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="">Categoria Rischio</dhv:label></strong>     
    </th>
     <th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="">STATO</dhv:label></strong>     
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
		<% if (thisOrg.getNotes()!=null && thisOrg.getNotes().equals("OPU")){  %>
      <a href="OpuStab.do?command=Details&stabId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a>	
	<% }else{ %>
      <a href="OperatoriCommerciali.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %> <%= ( thisOrg.getDataChiusuraCommerciale() != null ? " - chiuso - " : "" ) %></a>
	<% }  %>
	</td>
        <td valign="top"><%= SiteIdList.getSelectedValue(thisOrg.getSiteId()) %></td>

	

		<td valign="center" nowrap>
	     <% if ( thisOrg.getAddressList().getAddress(2) != null) { %>	
           <% if ( (!"".equals(thisOrg.getAddressList().getAddress(2).getCity()))) { %>
             <%= toHtml2(thisOrg.getAddressList().getAddress(2).getCity()) %>
           <%} else {%>
             &nbsp;
           <%}%>
         <%} else {%>
           &nbsp;
         <%}%>
	
		</td>
		<td valign="center" nowrap>
			<%=toHtml2(thisOrg.getPartitaIva()) %>
		</td>
    
		<td valign="center" nowrap>
			<%=toHtml2(thisOrg.getNomeRappresentante()) + " " + toHtml2(thisOrg.getCognomeRappresentante()) %>
		</td>
   
    	<td valign="center" nowrap>
			<%=(thisOrg.getCategoriaRischio()>0) ? thisOrg.getCategoriaRischio() : "3" %>
	   </td>
	   <td valign="center" nowrap>
			<%=(thisOrg.getDataChiusuraCanile()!=null) ? "CHIUSA IL"+toDateasString(thisOrg.getDataChiusuraCanile()) : "ATTIVTA" %>
	   </td>
    

  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="accounts.search.notFound">No accounts found with the specified search parameters.</dhv:label><br />
      <a href="OperatoriCommerciali.do?command=SearchForm"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>

