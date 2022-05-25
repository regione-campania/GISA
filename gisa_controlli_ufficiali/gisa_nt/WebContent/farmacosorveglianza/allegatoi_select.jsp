<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%>
<form method="post" action="Farmacosorveglianza.do?command=AllegatoI">

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Farmacie</dhv:label></a> > 
<dhv:label name="">AllegatoI</dhv:label>

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
<%=anno.getHtmlSelect("anno",14)%>
</td>
</tr>

<tr><td><input type = "submit" value = "invia"> </td></tr>

</table>



</form>


<script>
$("#anno").html($("#anno option").sort(function (a, b) {
    return a.text == b.text ? 0 : a.text > b.text ? -1 : 1
}))
</script>