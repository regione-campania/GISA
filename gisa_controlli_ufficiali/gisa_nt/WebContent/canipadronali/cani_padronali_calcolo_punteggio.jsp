<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.canipadronali.base.Proprietario"%><jsp:useBean id="ListProprietari" class="org.aspcfs.modules.canipadronali.base.ProprietarioList" scope="request"/>
<jsp:useBean id="CaniPadronaliPunteggiInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<%@ include file="../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="CaniPadronali.do?command=SearchForm">Anagrafica Cani di proprieta</a> > 
 	Calcolo Punteggio Proprietari
</td>
</tr>
</table>

<br><br>

<form method="post" action="CaniPadronaliVigilanza.do?command=CalcolaPunteggio">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2">Calcolo Punteggio Controlli Ufficiali</th>
			</tr>
			<tr>
				<td class="formLabel">Codice Fiscale Proprietario</td>
				<td><input type="text" maxlength="70" size="50" name="searchCfProprietario" value=""></td>
			</tr>
</table>
<input type ="submit" value = "Calcola Punteggio">
</form>



<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="CaniPadronaliPunteggiInfo"/>

<table class="details" width="100%">
<tr>
<th>Nominativo</th>
<th>Codice Fiscale</th>
<th>Comune di nascita</th>
<th>Data Nascita</th>
<th>Punteggio</th>
</tr>


<%
Iterator j = ListProprietari.iterator();
if ( j.hasNext() ) {
int rowid = 0;
int i = 0;
while (j.hasNext()) {
i++;
rowid = (rowid != 1 ? 1 : 2);
Proprietario p = (Proprietario)j.next();

%>
 <tr class="row<%= rowid %>">
	<td><%=p.getRagioneSociale() %></td>
	<td><%=p.getCodiceFiscale() %></td>
	<td><%=p.getLuogoNascita() %></td>
	<td>
	<%SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	%>
	<%=(p.getDataNascita()!=null) ? sdf.format(p.getDataNascita()) : ""%>
	</td>
	<td><%=p.getPunteggioTotale() %></td>
	<%
}
}
else
{
%>

<br>
<tr>
<td colspan="4">
Nessun Proprietario Presente</td></tr>
<%}
	
%>


</table>


