<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="listaReport" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@page import="org.aspcfs.modules.reportisticainterna.base.Report"%>


<form id = "addAccount" name="addAccount" action="ReportisticaInterna.do?command=Report&auto-populate=true" method="post" onSubmit="loadModalWindow();">

<table class="details" width="100%" cellspacing="10" cellpadding="10">

<tr><th style="text-align:center !important"><font size="5px"> Report </font></th> <td>
<select id="idReport" name="idReport" style="font-size: 25px">
<% for (int i = 0; i<listaReport.size(); i++) {
	Report report = (Report) listaReport.get(i);%>
	<option value="<%=report.getId()%>"><%=report.getNome() %></option>
<%} %>

</select>
</td></tr>
<tr><th style="text-align:center !important"><font size="5px"> ASL </font></th> <td> <%SiteList.setJsEvent("style=\"font-size: 25px\""); %> <%=SiteList.getHtmlSelect("idAsl", -1) %> </td></tr>
<tr> <td colspan="2"><input type="submit" value="CONFERMA"/></td></tr>

</table>

</form>
