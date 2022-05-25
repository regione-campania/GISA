<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="../../initPage.jsp"%>
<jsp:useBean id="listaModifiche" class="java.util.ArrayList" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.base.*, org.aspcfs.modules.registrazioniAnimali.base.*" %>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script type="text/javascript">
function openPopup(dataModifica, idUtenteModifica){
	var res;
	var result;
		window.open('OperatoreAction.do?command=ListaModifiche&dataModifica='+dataModifica+'&idUtenteModifica='+idUtenteModifica,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script> 



<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>

	<table class="trails" cellspacing="0">
		<tr>
			<td><a href="OperatoreAction.do"><dhv:label
				name="opu.operatore"></dhv:label></a> > <%
				if (request.getParameter("return") == null) {
			%> <a href="OperatoreAction.do?command=Search"><dhv:label
				name="accounts.SearchResults">Search Results</dhv:label></a> > <%
 	} else if (request.getParameter("return").equals("dashboard")) {
 %> <a href="OperatoreAction.do?command=Dashboard"><dhv:label
				name="communications.campaign.Dashboard">Dashboard</dhv:label></a> > <%
 	}
 %> <dhv:label name="">Lista modifiche</dhv:label></td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>

    <br>
  
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
				<th><strong>Data modifica</strong></th>
				<th><strong>Utente modifica</strong></th>
			</tr>
	
			
			<%
	String[] split;
	if (listaModifiche.size()>0){
		for (int i=0;i<listaModifiche.size(); i++){
			split = listaModifiche.get(i).toString().split(";;");
		%>
			
			<tr>
			 <td>
			<a href="#"
				onclick="openPopup('<%=split[1]%>','<%=split[0]%>');"
				id="" target="_self"><%=split[1]%></a></td>
			 <td>  <dhv:username id="<%= split[0]%>" /></td> 
					
		</tr>
		<%} } else { %>
		<tr>
			 <td colspan="9">Non sono presenti modifiche per questo operatore.</td></tr>
			 
		<%} %>
		
		</table>
	

  <!-- dhv:pagedListControl object="AssetTicketInfo"/-->



</body>
</html>