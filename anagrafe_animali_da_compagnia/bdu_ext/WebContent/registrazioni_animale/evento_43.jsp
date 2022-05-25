<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page
	import="org.aspcfs.modules.registrazioniAnimali.base.EventoModificaResidenza,org.aspcfs.modules.opu.base.*"%>


<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="evento"
	class="org.aspcfs.modules.registrazioniAnimali.base.Evento"
	scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	


<%
EventoModificaResidenza eventoF = (EventoModificaResidenza) evento;
%>
<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>



<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="pagedList">
	<th colspan="2">Dettagli della modifica residenza</th>

	<tr>
		<td><b><dhv:label name="">Data della modifica</dhv:label></b></td>
		<td><%=toDateasString(eventoF.getDataModificaResidenza())%>&nbsp;</td>
	</tr>
	<tr>
		<td width="20%"><b><dhv:label name="">Indirizzo originario</dhv:label></b>
		</td>
		<td>
		<%
			Indirizzo oldIndirizzo = eventoF.getOldIndirizzo();%>
		 <%=oldIndirizzo.toString()%></td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Nuovo indirizzo </dhv:label></b></td>
		<td>
		<%
			Indirizzo newIndirizzo = eventoF.getNewIndirizzo();%>
		 <%=newIndirizzo.toString()%>
		</td>
	</tr>


</table>