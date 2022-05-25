<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ page import="org.json.*"%>

<jsp:useBean id="esito" class="java.lang.String" scope="request"/>
<jsp:useBean id="idUtenteGuc" class="java.lang.String" scope="request"/>

<%@ include file="../guc/modalWindow.jsp"%>
<%@ include file="../guc/initPage.jsp"%>


<script>
function vaiADettaglio(id){
	loadModalWindow();
	window.location.href="guc.Detail.us?id="+id;
}

function indietro(){
	history.back();
}
</script>

<br/><br/><br/>
<center>
	<table style="border: 1px solid black;  border-spacing: 40px 10px; border-collapse: collapse" cellpadding="20" cellspacing="20" class="details" width="100%">

<% if (esito!=null && !esito.equals("")){
	JSONObject jsonEsito = new JSONObject(esito); %>
	
	<tr><th colspan="2">ESITO OPERAZIONE ALLINEAMENTO TUTTE LE CLINICHE VAM</th></tr>
	
	<% if (jsonEsito.has("Esito")){ %>
	<tr><th>ESITO</th> <td><%=jsonEsito.get("Esito")%></td></tr>
	<%} %>
	<% if (jsonEsito.has("DescrizioneErrore") && !jsonEsito.get("DescrizioneErrore").equals("")){ %>
	<tr><th>DESCRIZIONE ERRORE</th> <td><%=jsonEsito.get("DescrizioneErrore")%></td></tr>
	<%} %>
<% } %>

<tr><th colspan="2">
<input type="button" value="TORNA AL DETTAGLIO" style="width:200px; height: 50px; font-size: 15px" onClick="vaiADettaglio('<%=idUtenteGuc%>')"/>
</th></tr>
</table>
</center>

