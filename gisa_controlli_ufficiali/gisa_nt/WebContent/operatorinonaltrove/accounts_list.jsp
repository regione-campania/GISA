<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.operatorinonaltrove.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.operatorinonaltrove.base.OrganizationList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditList" class="org.aspcfs.modules.audit.base.AuditList" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>

<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">

  <%-- Preload image rollovers for drop-down menu --%>
  
</script>


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="operatorinonaltrove/opnonaltrove.js"></SCRIPT>

<% boolean hasPermissionAdd = false; %>
    <dhv:permission name="operatorinonaltrove-operatorinonaltrove-add">
  <% hasPermissionAdd = true;%>
</dhv:permission>

<table>
<tr>
<td>
      <% if (hasPermissionAdd){ %>
     <a href="#" onClick="window.location.href='OpnonAltrove.do?command=Add'">
    <%} else { %>
    <!-- OpnonAltrove.do?command=Add-->
    <a href="#" onClick="popupAggiunta();" >
    <%} %>
    <dhv:label name="">Aggiungi</dhv:label>
    </a>
    
</td>
<td>
    <dhv:permission name="operatorinonaltrove-operatorinonaltrove-view"><a href="OpnonAltrove.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a></dhv:permission>
</td>
</tr>
</table>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OpnonAltrove.do">Operatori non presenti altrove</a> > 
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>



<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    
    <th nowrap <% ++columnCount; %>>
      <strong><a href="OpnonAltrove.do?command=Search&column=o.name">
      Impresa</a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
    <th nowrap <% ++columnCount; %>>
          <strong>Partita IVA</strong>
		</th>
    
    
   <th nowrap <% ++columnCount; %>>

      <strong>Codice Autorizzazione</strong>

    </th>
    <th nowrap <% ++columnCount; %>>
          <strong>Sede Operativa</strong>
		</th>
    
    
        <th <% ++columnCount; %>>
          <strong><dhv:label name="accounts.site">Site</dhv:label></strong>
        </th>
      
        <th nowrap <% ++columnCount; %>>
          <strong>Responsabile Legale</strong>
		</th>
	
		<th nowrap <% ++columnCount; %>>
      <strong>Categoria Rischio</strong>
    </th>
    
        <th nowrap <% ++columnCount; %>>
          <strong>Inserito da</strong>
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
      <a href="OpnonAltrove.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a>
	</td>
	
	<td>
      <%= toHtml(thisOrg.getPartitaIva()) %>
	</td>
	
	<td>
      <%= toHtml(thisOrg.getAccountNumber()) %>
	</td>

		<%
	Iterator iaddress = thisOrg.getAddressList().iterator();
       Object address[] = null;
       int x = 0;
       boolean trovato = false ;
       if (iaddress.hasNext()) {
          
    	   while (iaddress.hasNext()) {
        	  org.aspcfs.modules.accounts.base.OrganizationAddress thisAddress = (org.aspcfs.modules.accounts.base.OrganizationAddress)iaddress.next(); 
          if(thisAddress.getType()==5){
        	  trovato = true ;
        	  %>
     <td>
      <%= toHtml((thisAddress.getStreetAddressLine1()!= null && (thisAddress.getCity()!=null)&&(thisAddress.getType()==5)) ? (thisAddress.getStreetAddressLine1()+" "+thisAddress.getCity()) : ("")) %>
	</td> 
	<%}}}
   
	 %>
		
        <td valign="top" nowrap><%= SiteIdList.getSelectedValue(thisOrg.getSiteId()) %></td>

	  
    
	<td nowrap>
       <%= toHtml(thisOrg.getNomeRappresentante()) %>
       </td>
  
	<td>
	<%= (thisOrg.getCategoriaRischio()) %>
	</td>
	
    <td nowrap>
      <dhv:username id="<%= thisOrg.getEnteredBy() %>" />
	</td>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      Nessu Operatore Trovato Con i parametri specificati
      <a href="OpnonAltrove.do?command=SearchForm"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>

