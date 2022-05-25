<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

  
 <%@ page contentType="application/vnd.ms-excel" %>
  
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.osmregistrati.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.osmregistrati.base.OrganizationList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>


<table>

<tr><th colspan="7">Export Osm</th></tr>


<tr>
<th>Ragione Sociale</th>
<th>Stato OSM</th>
<th>Sezione</th>
<th>Attività</th>
<th>A.S.L</th>
<th>Partita IVA</th>
<th>Codice Istat</th>

</tr>

<%

Iterator it=OrgList.iterator();

while(it.hasNext()){
Organization org=(Organization) it.next();
%>
<tr>

<td><%=org.getName() %></td>
<td>la devo settare</td>
<td><%=org.getCategoria() %></td>
<td> <%=impianto.getSelectedValue(org.getImpianto())%></td>
<td>la devo settare</td>
<td>la devo settare</td>
<td>la devo settare</td>
</tr>

<%} %>


</table>