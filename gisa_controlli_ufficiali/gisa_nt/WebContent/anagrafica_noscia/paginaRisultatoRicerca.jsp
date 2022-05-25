<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.anagrafica_noscia.prototype.base_beans.*" %>
<%@ page import="com.anagrafica_noscia.prototype.masterlist.*" %>
<%@ page import="com.anagrafica_noscia.prototype.anagrafica.*" %>
<%@ page import="com.anagrafica_noscia.prototype.base_beans.Utilities" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@page import="java.sql.Timestamp" %>
<jsp:useBean class="java.util.ArrayList" id="results" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
	tr.tr_entry:hover
	 	{
	 		background-color: rgba(125,126,130,0.3);
	 	}
</style>
</head>
<body>

	<center>
	<div  style="max-width:1300px;">
		<dhv:container name="anagnoscia_risultatiricercaanagrafica_container" selected="Risultati Ricerca" object=""  param="" >
			
		<table class="details" width="100%">
			<tr>	
				<th>RAGIONE SOCIALE IMP.</th>
				<th>PARTITA IVA IMP.</th>
				<th>CODICE FISCALE IMP.</th>
				<th>COMUNE SEDE LEGALE</th>
				<th>NOME RAPP.LE</th>
				<th>COGNOME RAPP.LE</th>
				<th>CODICE FISCALE RAPP.LE</th>
				<th>COMUNE SEDE OPERATIVA</th>
				<th>DATA INSERIMENTO</th>
			</tr>	
			
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
				for(int i = 0; i< results.size(); i++)
				{
					AnagraficaBase entry = (AnagraficaBase)results.get(i);
					String descComuneImpresa = "";
					String nomeRappLe = "";
					String cognomeRappLe = "";
					String cfRappLe = "";
					String descComuneStab = "";
					String dataInserimentoS = "";
					try {   descComuneImpresa = entry.getImpresa().getIndirizzi().get(0).getDescComune(); } catch(Exception ex){}
					try {   nomeRappLe = entry.getLegaliRappresentanti().get(0).getNome(); } catch(Exception ex){}
					try {   cognomeRappLe = entry.getLegaliRappresentanti().get(0).getCognome(); } catch(Exception ex){}
					try {   cfRappLe = entry.getLegaliRappresentanti().get(0).getCodiceFiscale(); } catch(Exception ex){}
					try{    descComuneStab = entry.getStabilimenti().get(0).getIndirizzi().get(0).getDescComune(); } catch(Exception ex){}
					try{    dataInserimentoS = sdf.format(new Date(entry.getImpresa().getDataInserimento().getTime())); } catch(Exception ex){}
					%>
						<tr class="tr_entry" style="cursor:pointer"   onclick="apriDettaglio(<%=entry.getImpresa().getId()%>)">
							<td><%=entry.getImpresa().getRagioneSociale() %></td>
							<td><%=entry.getImpresa().getPiva() %></td>
							<td><%=entry.getImpresa().getCodFiscale() %></td>
							<td><%=descComuneImpresa %></td>
							<td><%=nomeRappLe %></td>
							<td><%=cognomeRappLe %></td>
							<td><%=cfRappLe %></td>
							<td><%=descComuneStab %></td>
							<td><%= dataInserimentoS %> </td>
						</tr>
					<%
				}
			%>	
			
		</table>
		</dhv:container>
	</div>
	</center>


	<script>
	
		function apriDettaglio(idImpresa)
		{
			var urlDest = "";
			document.location.href="MainAnagraficaNoScia.do?command=Details&idImpresa="+idImpresa;
		}
		
		 
	</script>



</body>
</html>