<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%--Pagina JSP creata da Francesco --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.utils.*" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>

<%@ include file="initPage.jsp" %>



<%

boolean trovato = (Boolean)request.getAttribute("DiaEsistente");

if(trovato == true)
{

%>
<body >

<script>
popLookupSelectorCheckImpreseRed();
</script>

<table>

<tr><td colspan="2">
<b>L' impresa Con Partita Iva : <%=request.getAttribute("PartitaIva") %> è stata già inserita precedentemente . Continuare con l'inserimenti degli altri Dati ?</b></td></tr>
<tr><td>
<input type = "button" value = "si" onclick="popLookupSelectorCheckImpreseOk();">

</td><td>

<input type = "button" value = "no" onclick="popLookupSelectorCheckImpreseNo();">

</td></tr>

</table>
</body>

<%}else
{
	%>


<body onload="closeCheckDia();">
</body>

<%}%>

