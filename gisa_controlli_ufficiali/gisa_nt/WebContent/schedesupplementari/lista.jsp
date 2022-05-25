<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<jsp:useBean id="listaSchede" class="java.util.ArrayList" scope="request"/>

<%@ page import="org.aspcfs.modules.schedesupplementari.base.*" %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<link rel="stylesheet" href="schedesupplementari/css/screen.css" type="text/css" media="screen" />

<script>
function apriScheda(riferimentoId, riferimentoIdNomeTab, idIstanzaLinea, numScheda){
	loadModalWindow();
	window.location.href="GestioneSchedeSupplementari.do?command=ViewScheda&riferimentoId="+riferimentoId+"&riferimentoIdNomeTab="+riferimentoIdNomeTab+"&idIstanzaLinea="+idIstanzaLinea+"&numScheda="+numScheda;
}
</script>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<center><div class="intestazione"><i>Schede supplementari configurate:</i></div><br/>

<%if (listaSchede.size()==0){ %>
<script>
alert('AL MOMENTO NON SONO DISPONIBILI SCHEDE SUPPLEMENTARI PER LE LINEE DI QUESTA ANAGRAFICA');
window.close();
</script>
<%}  %>


<% for (int i=0;i<listaSchede.size(); i++){
		IstanzaScheda scheda = (IstanzaScheda) listaSchede.get(i); %>
		<table class="tableListaSchede">
		<thead><tr><th scope="col"><%=scheda.getDescrizioneScheda() %></th></tr></thead>
		<tbody>
		<tr><td><input type="button" class="blueBigButton" value="APRI" onClick="apriScheda('<%=scheda.getRiferimentoId()%>', '<%=scheda.getRiferimentoIdNomeTab()%>', '<%=scheda.getIdIstanzaLinea() %>', '<%=scheda.getNumScheda()%>' )"/></td></tr>
		</tbody>
		</table>
<% } %>

<br/><br/>
<input type="button" class="blueBigButton" value="CHIUDI" onClick="window.close()"/>

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
