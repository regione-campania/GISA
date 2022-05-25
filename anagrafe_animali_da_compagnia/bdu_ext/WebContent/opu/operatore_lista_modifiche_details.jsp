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


<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>

	<table class="trails" cellspacing="0">
		<tr>
			<td>
				
 	<dhv:label name="">Dettaglio lista modifiche</dhv:label></td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>

    <br>
  
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
			<!-- th><strong>Id</strong></th-->
				<th><strong>Campo modificato</strong></th>
				<th><strong>Valore precedente</strong></th>
				<th><strong>Valore nuovo</strong></th>
				<th><strong>Utente modifica</strong></th>
				<th><strong>Data modifica</strong></th>
			</tr>
	
			
			<%
	String[] split;
	if (listaModifiche.size()>0)
		for (int i=0;i<listaModifiche.size(); i++){
			split = listaModifiche.get(i).toString().split(";;");
		%>
			
			<tr>
			<!-- td><%= split[0] %></td--> 
			<td><%= split[1] %></td> 
			<td><%= split[2] %></td> 
			<td><%= split[3] %></td> 
			<td> <dhv:username id="<%= split[4] %>"></dhv:username></td> 
			<td><%= split[5] %></td> 
				
		</tr>
		<%} %>
		
		</table>
	

  <!-- dhv:pagedListControl object="AssetTicketInfo"/-->



</body>
</html>