<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="tot" class="java.lang.String" scope="request"/>

<script>
function checkForm(form){
	loadModalWindow();
	form.submit();
}
</script>

<style>
.tableOsm {
table-layout: fixed;
word-wrap:break-word; 
border: 1px solid black;
border-collapse: collapse;
}
.tableOsm td,th{
border: 1px solid black;
font-size: 18px;
}
.tableOsm th{
background-color: 00ffee;
}
.esito {
font-size: 12px !important;
}

</style>

<form name="addAccount" action="GestioneOSM.do?command=InvioMassivoOSM&auto-populate=true" method="post">

<table class="tableOsm">
<tr><th colspan="2">Invio massivo OSM</th></tr>
<tr><td>OSM da inviare</td><td><%=tot %></td></tr>
<tr><td>OSM da inviare in questa operazione</td><td>
<select id="num" name="num">
<% int n = Integer.parseInt(tot); %>
<% if (n>=1) {%><option value="1" selected>1</option><% } %>
<% if (n>=5) {%><option value="5">5</option><% } %>
<% if (n>=10) {%><option value="10">10</option><% } %>
<% if (n>=100) {%><option value="100">100</option><% } %>
<% if (n>=400) {%><option value="400">400</option><% } %>
<% if (n>0) {%><option value="<%=n %>"><%=n %></option><% } %>
</select>
</td>
</tr>

<tr><td colspan="2"> 
<dhv:permission name="osm-invio-massivo-view">
<input type="button" onClick="checkForm(this.form)" value="INVIA"/>
</dhv:permission>
 </td></tr>
</table>
<input type="hidden" id="tot" name="tot" value="<%=tot %>"/>
</form>