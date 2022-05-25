<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.acquedirete.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.acquedirete.base.OrganizationList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.acquedirete.base.Organization" scope="request"/>
<jsp:useBean id="AddressSedeOperativa" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
arrayValue = new Array();
arrayDesc = new Array();
arrayTipi = new Array();
indice = -1 ;
function fillArray(campo,id,descrizione, tipo)
{
	if(campo.checked == true)
	{
		indice++;
		arrayValue[indice] = id ;
		arrayDesc[indice] = descrizione ;
		arrayTipi[indice] = tipo ;
	}
	else
	{
		indice = arrayValue.indexOf(id);
		arrayValue.splice(indice, 1);
		arrayDesc.splice(indice, 1);
		arrayTipi.splice(indice, 1);
		<!-- RIMUOVO DALL'ARRAY-->
	
	}
	
}
  
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:label name="">Punto di Prelievo <%= SiteIdList.getSelectedValue(OrgDetails.getSiteId()) %></dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
  </tr>
</table>
</dhv:evaluate>


<br />


<script>
(function(document) {
	'use strict';

	var LightTableFilter = (function(Arr) {

		var _input;

		function _onInputEvent(e) {
			_input = e.target;
			var tables = document.getElementsByClassName(_input.getAttribute('data-table'));
			Arr.forEach.call(tables, function(table) {
					Arr.forEach.call(table.tBodies, function(tbody) {
					Arr.forEach.call(tbody.rows, _filter);
				});
			});
		}

		function _filter(row) {
			var text = row.textContent.toLowerCase(), val = _input.value.toLowerCase();
			row.style.display = text.indexOf(val) === -1 ? 'none' : 'table-row';
		}

		return {
			init: function() {
				var inputs = document.getElementsByClassName('light-table-filter');
				Arr.forEach.call(inputs, function(input) {
					input.oninput = _onInputEvent;
				});
			}
		};
	})(Array.prototype);

	document.addEventListener('readystatechange', function() {
		if (document.readyState === 'complete') {
			LightTableFilter.init();
		}
	});

})(document);
</script>


<section class="container">

<% int columnCount = 0; %>
<input type="button" onclick="javascript:clonaNelPadreuntidiPrelievo(arrayValue,arrayDesc, arrayTipi)" value="Seleziona">
	<input type="search" class="light-table-filter" data-table="order-table" placeholder="Filtra">

	<table class="order-table table, pagedList" cellpadding="8" cellspacing="0" border="0" width="100%" border="1px">
	 <tr>
  <th>&nbsp;</th>
    <th nowrap <% ++columnCount; %>>
     <dhv:label name="">Denominazione</dhv:label>
    </th>  
<%--      <th nowrap <% ++columnCount; %>> --%>
<%--       <dhv:label name="">ASL</dhv:label> --%>
<!--     </th>   -->
        
        <th <% ++columnCount; %>>
          <strong><dhv:label name="">Ubicazione</dhv:label></strong>
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
 
  
  <td><input id = "<%=thisOrg.getOrgId() %>" type = "checkbox" <%=(thisOrg.isSelezionato()==true) ? "checked" : "" %> value="<%=thisOrg.getOrgId() %>" onclick="fillArray(this,<%=thisOrg.getOrgId() %>,'<%=thisOrg.getName().replaceAll("'", "") %>', '<%=thisOrg.getTipo_struttura() %>');" >
    <%if (thisOrg.isSelezionato()){
	%>
	<script>
	fillArray(document.getElementById('<%=thisOrg.getOrgId()%>'),<%=thisOrg.getOrgId() %>,'<%=thisOrg.getName().replaceAll("'", "") %>',  '<%=thisOrg.getTipo_struttura() %>');
	</script>
	<%
  } %>
  
  </td>
	<td>
      <%= toHtml(thisOrg.getName())%> 
	</td>
<!--     <td> -->
<%--     	<%= SiteIdList.getSelectedValue(thisOrg.getSiteId()) %> --%>
<!--     </td>   -->
      
	<% Iterator iaddress = thisOrg.getAddressList().iterator();
       Object address[] = null;
       int x = 0;
       if (iaddress.hasNext()) {%>
       <td>
    	   <%
          while (iaddress.hasNext()) {
        	  
        	  org.aspcfs.modules.acquedirete.base.OrganizationAddress thisAddress = (org.aspcfs.modules.acquedirete.base.OrganizationAddress)iaddress.next(); 
          if(thisAddress.getType()==1){ 
         %>
     <b>Sede Legale:&nbsp;</b> 
      <%= toHtml((thisAddress.getStreetAddressLine1()!= null && (thisAddress.getCity()!=null)&&(thisAddress.getType()==1)) ? (thisAddress.getStreetAddressLine1()+" "+thisAddress.getCity()) : ("")) %>&nbsp;<br/><br/>
	<%}
	if(thisAddress.getType()==5){ %>
	<b>Sede Operativa:&nbsp;</b> 
    <%--  <%= toHtml(thisAddress.getCity()) %>  --%>
       <%= toHtml((thisAddress.getStreetAddressLine1()!= null) ? thisAddress.getStreetAddressLine1()+" "+thisAddress.getCity() : (thisAddress.getCity())) %>
		
	<%}}%>
	</td>
	<%}else{ %>
	<td nowrap>Nessun Indirizzo.</td>
	<%} %>
	
	<%-- td><%=thisOrg.getTipo_struttura() %></td--%>
  </tr>
   
  <input type="hidden" name="source" value="searchForm">
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="">Nessun Acque Di Rete trovato.</dhv:label><br />
      <!-- a href="AcqueRete.do?command=SearchForm"><dhv:label name="">Modifica Ricerca</dhv:label></a-->
    </td>
  </tr>
<%}%>
</table>
<input type="button" onclick="javascript:clonaNelPadreuntidiPrelievo(arrayValue,arrayDesc, arrayTipi)" value="Seleziona">
</section>













