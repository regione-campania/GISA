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
<%@ page import="java.util.*,org.aspcfs.modules.canili.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.canili.base.OrganizationList" scope="request"/>
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
<a href="Canili.do">Canili</a> > 
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
      <strong><a href="Canili.do?command=Search&column=o.name"><dhv:label name="organization.name">Account Name</dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
<%--    <dhv:include name="organization.list.siteId" none="true"> --%>
<zeroio:debug value='<%="JSP::accounts_list.jsp "+ SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")+" == "+(String.valueOf(Constants.INVALID_SITE)) %>'/>
      <dhv:evaluate if='<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE)) %>'>
        <th <% ++columnCount; %>>
          <strong><dhv:label name="accounts.site">Site</dhv:label></strong>
        </th>
      </dhv:evaluate>
<%--    </dhv:include> --%>
      <dhv:include name="organization.addresses" none="true">
        <th nowrap <% ++columnCount; %>>
           <strong><a href="Canili.do?command=Search&column=oa.city"><dhv:label name="accounts.accounts_add.City">City</dhv:label></a></strong>
	 	   <%= SearchOrgListInfo.getSortIcon("oa.city") %> 
	 	</th>
      </dhv:include>    
      <dhv:include name="organization.addresses" none="true">
    	<th nowrap <% ++columnCount; %>>
          <strong><a href="Canili.do?command=Search&column=oa.state"><dhv:label name="accounts.accounts_add.StateProvince">State</dhv:label></a></strong>
					<%= SearchOrgListInfo.getSortIcon("oa.state") %> 
		</th>
	  </dhv:include>
	  <%-- 
      <dhv:include name="organization.addresses" none="true">
        <th nowrap <% ++columnCount; %>>
          <strong><a href="Accounts.do?command=Search&column=oa.postalcode"><dhv:label name="accounts.accounts_add.Zip">Zip</dhv:label></a></strong>
					<%= SearchOrgListInfo.getSortIcon("oa.postalcode") %> 
		</th>
      </dhv:include>
      --%>
      <dhv:include name="organization.phoneNumbers" none="true">
        <th nowrap <% ++columnCount; %>>
          <strong><dhv:label name="account.phoneFax">Phone/Fax</dhv:label></strong>
		</th>
      </dhv:include>
       <th nowrap <% ++columnCount; %>>
      <%--strong><a href="Accounts.do?command=Search&column=ct_owner.namelast"><dhv:label name="">Owner Name</dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("ct_owner.namelast") --%>
      <strong>Codice Fiscale</strong>     
    </th>
  <th nowrap <% ++columnCount; %>>
      <%--strong><a href="Accounts.do?command=Search&column=ct_owner.namelast"><dhv:label name="">Owner Name</dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("ct_owner.namelast") --%>
      <strong><dhv:label name="">Categoria Rischio</dhv:label></strong>     
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
      <a href="Canili.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %> <%= ( thisOrg.isAbusivo() ? " - soggetto Abusivo - " : "" ) %> <%= ( thisOrg.getDataChiusuraCanile() != null ? " - canile chiuso - " : "" ) %></a>
	<% }  %>
	</td>
<%--    <dhv:include name="organization.list.siteId" none="true"> --%>
      <dhv:evaluate if='<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE)) %>'>
        <td valign="top"><%= SiteIdList.getSelectedValue(thisOrg.getSiteId()) %></td>
      </dhv:evaluate>
<%--    </dhv:include> --%>
	
      <dhv:include name="organization.addresses" none="true">
		<td valign="center" nowrap>
		<% if (thisOrg.getNotes().equals("OPU")){ 
		      if (thisOrg.getCity()!=null && !thisOrg.getCity().equals("")){
		    	  out.print(thisOrg.getCity());
		      }else{
		    	  out.print("N.D.");
		      }
		 } else { %>
	     <% if ( thisOrg.getPrimaryAddress() != null) { %>	
           <% if ( (!"".equals(thisOrg.getPrimaryAddress().getCity()))) { %>
             <%= toHtml(thisOrg.getPrimaryAddress().getCity()) %>
           <%} else {%>
             &nbsp;
           <%}%>
         <%} else {%>
           &nbsp;
         <%}%>
         <% } %>
		
		</td>
    </dhv:include>
    <dhv:include name="organization.addresses" none="true">
		<td valign="center" nowrap>
				<% if (thisOrg.getNotes().equals("OPU")){ 
		      if (thisOrg.getState()!=null && !thisOrg.getState().equals("")){
		    	  out.print(thisOrg.getState());
		      }else{
		    	  out.print("N.D.");
		      }
		 } else { %>
	     <% if ( thisOrg.getPrimaryAddress() != null) { %>	
           <% if ( (!"-1".equals(thisOrg.getPrimaryAddress().getState()))) { %>
             <%= toHtml(thisOrg.getPrimaryAddress().getState()) %>
           <%} else {%>
             &nbsp;
           <%}%>
         <%} else {%>
           &nbsp;
         <%}%>
		<% } %>
		</td>
    </dhv:include>
     <dhv:include name="organization.phoneNumbers" none="true">
		<td valign="center" nowrap>
      <%Iterator itr = thisOrg.getPhoneNumberList().iterator();%>
      <%if (!itr.hasNext()) {%>N.D<%}%>
      <%while (itr.hasNext()) {
        PhoneNumber phoneNumber = (PhoneNumber)itr.next(); 
         if(phoneNumber.getPhoneNumber() != null && !phoneNumber.getPhoneNumber().equals("")) {
        %>
        	<%= phoneNumber.getPhoneNumber()%> (<%=phoneNumber.getTypeName()%>)<br />
        <% } else { %>
        N.D (<%=phoneNumber.getTypeName()%>)<br/>
      <%} }%>
		
		</td>
    </dhv:include>
    <dhv:include name="" none="true">
		<td valign="center" nowrap>
		<dhv:evaluate if="<%=(thisOrg.getCodiceFiscale() != null)%>">
		 <%= toHtml(thisOrg.getCodiceFiscale()) %>
	   </dhv:evaluate>
		</td>
    </dhv:include>
    <td valign="center" nowrap>
	
		 <%= thisOrg.getCategoriaRischio() %>
	</td>
    

  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="accounts.search.notFound">No accounts found with the specified search parameters.</dhv:label><br />
      <a href="Canili.do?command=SearchForm"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>

