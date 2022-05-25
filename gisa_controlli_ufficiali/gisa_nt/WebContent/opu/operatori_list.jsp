<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.opu.base.OperatoreList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script>

function setOperatorePopUp(idOperatore)
{
	if (window.opener.document != null)
	{
		window.opener.document.getElementById('id_operatore').value = idOperatore;
		window.opener.document.getElementById('idOperatore').value=idOperatore;
		window.opener.document.forms[0].doContinueStab.value = 'false';
// 		
		window.opener.document.forms[0].submit();
		
		window.close();
		
	}
	
}

</script>


<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
Impresa > 
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>


<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>


<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">

  <tr>
  
    <th nowrap <% ++columnCount; %>>
  <strong> DITTA/DENOMINAZIONE/RAGIONE SOCIALE</strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
    
            <th nowrap <% ++columnCount; %>>
     <strong> PARTITA IVA</strong>
		</th>
		
		 <th nowrap <% ++columnCount; %>>
       <strong>	CODICE FISCALE </strong>
		</th>

		<th nowrap <% ++columnCount; %>>
        <strong>Dati SEDE LEGALE / RESIDENZA </strong>
        </th>
     
        <th nowrap <% ++columnCount; %>>
          <strong>Inserito da</strong>
		</th>
		
		<th nowrap <% ++columnCount; %>>
          <strong>Modificato da</strong>
		</th>
  </tr>
<%
	Iterator j = OrgList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    //rowid = (rowid != 1 ? 1 : 2);
    Operatore thisOrg = (Operatore)j.next();
    
    		
%>
  <tr class="row<%= rowid %>">
<%--   <dhv:evaluate if="<%= isPopup(request) %>"> --%>
<%-- 			<td nowrap> <a href="#" onclick="setOperatorePopUp(<%=thisOrg.getIdOperatore()%>)"><%=thisOrg.getRagioneSociale() %></a></td> --%>
<%-- 		</dhv:evaluate> --%>
	
	 <td nowrap>
       
       <a href="OperatoreAction.do?command=Details&opId=<%=thisOrg.getIdOperatore()%>">
       <%= toHtml(thisOrg.getRagioneSociale()) %>
       </a>
	</td>
		
    <td nowrap>
       <%= toHtml(thisOrg.getPartitaIva()) %>
	</td>

	
    <td nowrap>
       <%= toHtml(thisOrg.getCodFiscale()) %>
	</td>
	
	   <td nowrap>
       <%= (thisOrg.getSedeLegale() != null) ? toHtml(thisOrg.getSedeLegale().toString()) : "" %>
	</td>

	
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
      <dhv:label name="accounts.search.notFound">No accounts found with the specified search parameters.</dhv:label><br/>
       
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>
