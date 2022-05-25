<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%><form method="post" action="Parafarmacie.do?command=AllegatoII">

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Parafarmacie.do?command=SearchFormFcie"><dhv:label name="">Farmacie / Grossisti / Parafarmacie</dhv:label></a> > 
<dhv:label name="">AllegatoII</dhv:label>

</td>
</tr>
</table>
<br>
<%

LookupList asl = (LookupList) request.getAttribute("Asl");
LookupList anno = (LookupList) request.getAttribute("Anno");
%>
<table align= "center">
<%
UserBean user = (UserBean) session.getAttribute("User");
if (user.getSiteId()!=-1)
{%>
<tr>
<td>Asl</td>
<td>
<%=asl.getSelectedValue(user.getSiteId()) %>
<input type = "hidden" name = "asl" value = "<%=user.getSiteId() %>">



</td>
</tr><%} %>
<tr>

<td>Anno</td>
<td>
<%=anno.getHtmlSelect("anno",-1)%>
</td>
</tr>

<tr><td><input type = "submit" value = "invia"> </td></tr>

</table>



</form>