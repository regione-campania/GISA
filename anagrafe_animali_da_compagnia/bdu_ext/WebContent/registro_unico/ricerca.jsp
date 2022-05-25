<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<jsp:useBean id="anno" class="java.lang.String" scope="request"/>
<jsp:useBean id="annoCorrente" class="java.lang.String" scope="request"/>

<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="AnimaleAction.do"><dhv:label
			name="">Anagrafe animali</dhv:label></a> > <dhv:label name="">REGISTRO UNICO CANI A RISCHIO ELEVATO DI AGGRESSIVITA'</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>

<form name="searchRegistroUnico" action="AnimaleAction.do?command=SearchRegistroUnico" onSubmit="" method="post">
<input type="hidden" name="doContinue" id="doContinue" value="" />
<label>Selezionare l'anno </label>

<select name= "anno" id="anno">
<%
	for (int i = Integer.parseInt(annoCorrente); i>= Integer.parseInt(anno); i--)
	{
%>
		<option value="<%=i%>"><%=i%></option>
<%
	} 
%>


</select>

<input type="submit" value="Apri registro"> </form>