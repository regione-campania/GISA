<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.lang.management.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
  <title>JVM Memory Monitor</title>
</head>

<%


String s = request.getParameter("cane");
System.out.print("CANE = "+s);

        Iterator iter1 = ManagementFactory.getMemoryPoolMXBeans().iterator();
        while (iter1.hasNext()) {
            MemoryPoolMXBean item1 = (MemoryPoolMXBean) iter1.next();
%>

<table border="0" width="100%">
<tr><td colspan="2" align="center"><h3>Memory MXBean</h3></td></tr>
<tr><td
width="200">Heap Memory Usage</td><td><%=
ManagementFactory.getMemoryMXBean().getHeapMemoryUsage()
%></td></tr>
<tr><td>Non-Heap Memory
Usage</td><td><%=
ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage()
%></td></tr></body>
<tr><td colspan="2">&nbsp;</td></tr>
<tr><td colspan="2" align="center"><h3>Memory Pool MXBeans</h3></td></tr>
<%
        Iterator<MemoryPoolMXBean> iter = ManagementFactory.getMemoryPoolMXBeans().iterator();
        while (iter.hasNext()) {
            MemoryPoolMXBean item = (MemoryPoolMXBean) iter.next();
%>
<tr><td colspan="2">
<table border="0" width="100%" style="border: 1px #98AAB1 solid;">
<tr><td colspan="2" align="center"><b><%= item.getName() %></b></td></tr>
<tr><td width="200">Type</td><td><%= item.getType() %></td></tr>
<tr><td>Usage</td><td><%= item.getUsage() %></td></tr>
<tr><td>Peak Usage</td><td><%= item.getPeakUsage() %></td></tr>
<tr><td>Collection Usage</td><td><%= item.getCollectionUsage() %></td></tr>
</table>
</td></tr>
<tr><td colspan="2">&nbsp;</td></tr>
<%
}
        }
%>

</table> 