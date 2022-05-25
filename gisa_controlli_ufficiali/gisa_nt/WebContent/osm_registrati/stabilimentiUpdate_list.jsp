<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*,org.aspcfs.utils.*"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../initPage.jsp"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />

<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td width="100%">
			<a href="OsmRegistrati.do">OSM Registrati</a> > 
			<dhv:label name="">Importa Osm Registrati</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>

<br>

<table cellpadding="4" cellspacing="0" border="0" width="50%"
	class="details">
	<tr>
		<th nowrap class="formLabel"><strong>DATA</strong></th>
		<th nowrap class="formLabel"><strong>RIEPILOGO</strong></th>
		<th nowrap class="formLabel"><strong>ERRORI</strong></th>
		<th nowrap class="formLabel"><strong>INSERITI</strong></th>
	</tr>
	
	<% 
		ArrayList<RiepilogoImport> rImport = ( ArrayList<RiepilogoImport> ) request.getAttribute("allRecords");
		if ( rImport.size() > 0 ) { 
			for ( int i=0; i< rImport.size(); i++ ) {
	%>
			<tr>
				<td align="right"><zeroio:tz timestamp="<%= rImport.get(i).getDataOp() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" /></td>
				<td align="right"><a href="<%= "logOsm/"+rImport.get(i).getRiepilogo() %>">Download</a></td>
				<td align="right"><a href="<%= "logOsm/"+rImport.get(i).getErrori() %>">Download</a></td>
				<td align="right"><a href="<%= "logOsm/"+rImport.get(i).getRecordInseriti() %>">Download</a></td>
			</tr>
			<% } %>
		<% } else { %>
		  <tr class="containerBody">
      			<td colspan="4">
        			<dhv:label name="">Nessun import eseguito.</dhv:label>
      			</td>
    	  </tr>
		<% } %> 
	
</table>




	