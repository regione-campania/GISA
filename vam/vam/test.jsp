<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.tomcat.jdbc.pool.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%
Context ctx = new InitialContext();
DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/bduM");




%>

<table border ="1">
<tr>
<td colspan="2">BDU_M</td>
</tr>
<tr><td>PARAMETRO</td><td>#</td></tr>
<tr <%=(ds.getActive()>1) ? "style='background-color: red'" : ""%>><td>CONNESSIONI ATTIVE</td><td><%=ds.getActive() %></td></tr>
<tr><td>MAX ACTIVE</td><td><%=ds.getMaxActive() %></td></tr>
<tr><td>CONNESSIONI IDLE</td><td><%=ds.getIdle() %></td></tr>
<tr><td>MAX IDLE</td><td><%=ds.getMaxIdle() %></td></tr>


</table>

<%
 ds = (DataSource)ctx.lookup("java:comp/env/jdbc/bduS");

%>
<br>
<table border ="1">
<tr>
<td colspan="2">BDU_S</td>
</tr>
<tr <%=(ds.getActive()>1) ? "style='background-color: red'" : ""%>><td>CONNESSIONI ATTIVE</td><td><%=ds.getActive() %></td></tr>
<tr><td>MAX ACTIVE</td><td><%=ds.getMaxActive() %></td></tr>
<tr><td>CONNESSIONI IDLE</td><td><%=ds.getIdle() %></td></tr>
<tr><td>MAX IDLE</td><td><%=ds.getMaxIdle() %></td></tr>
</table>
<%
ds = (DataSource)ctx.lookup("java:comp/env/jdbc/vamM");


%>
<br>
<table border ="1">
<tr>
<td colspan="2">VAM_M</td>
</tr>
<tr><td>PARAMETRO</td><td>#</td></tr>

<tr <%=(ds.getActive()>1) ? "style='background-color: red'" : ""%>><td>CONNESSIONI ATTIVE</td><td><%=ds.getActive() %></td></tr>
<tr><td>MAX ACTIVE</td><td><%=ds.getMaxActive() %></td></tr>
<tr><td>CONNESSIONI IDLE</td><td><%=ds.getIdle() %></td></tr>
<tr><td>MAX IDLE</td><td><%=ds.getMaxIdle() %></td></tr>
</table>


<%
ds = (DataSource)ctx.lookup("java:comp/env/jdbc/vamS");


%>

<br>
<table border ="1">
<tr>
<td colspan="2">VAM_S</td>
</tr>
<tr><td>PARAMETRO</td><td>#</td></tr>

<tr <%=(ds.getActive()>1) ? "style='background-color: red'" : ""%>><td>CONNESSIONI ATTIVE</td><td><%=ds.getActive() %></td></tr>
<tr><td>MAX ACTIVE</td><td><%=ds.getMaxActive() %></td></tr>
<tr><td>CONNESSIONI IDLE</td><td><%=ds.getIdle() %></td></tr>
<tr><td>MAX IDLE</td><td><%=ds.getMaxIdle() %></td></tr>
</table>





