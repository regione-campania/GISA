<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="jsonControllo" class="org.json.JSONObject" scope="request"/>

<%@ page import="org.aspcfs.modules.gestionecu.base.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>


<center>

<!-- RIEPILOGO -->
<%@ page import="org.json.*"%>

<%! public static String fixData(Object objData)
  {
	  String toRet = "";
	  if (objData == null || objData.equals(""))
		  return toRet;
	  String data = objData.toString();
	  String anno = data.substring(0,4);
	  String mese = data.substring(5,7);
	  String giorno = data.substring(8,10);
	  toRet =giorno+"/"+mese+"/"+anno;
	  return toRet;
	  
  }%>

<!-- RIEPILOGO -->
<table class="details" cellpadding="10" cellspacing="10" width="100%">
<col width="20%">

<tr><th colspan="2"><center><b>DETTAGLIO CONTROLLO UFFICIALE</b></center></th></tr>

<% if ( ((JSONObject) jsonControllo).has("Anagrafica")) { %>
<% JSONObject jsonAnagrafica = (JSONObject) jsonControllo.get("Anagrafica"); %>
<tr><td class="formLabel">Anagrafica</td><td><%=jsonAnagrafica.get("ragioneSociale") %> (<%=jsonAnagrafica.get("partitaIva") %>)</td></tr>
<%} %>

<% if ( ((JSONObject) jsonControllo).has("Tecnica")) { %>
<% JSONObject jsonTecnica = (JSONObject) jsonControllo.get("Tecnica"); %>
<tr><td class="formLabel">Tecnica del controllo </td><td><%=jsonTecnica.get("nome") %></td></tr>
<%} %>

<% if ( ((JSONObject) jsonControllo).has("Linee")) { %>
<% JSONArray jsonLinee = (JSONArray) jsonControllo.get("Linee"); 
if (jsonLinee.length()>0) {%>
<tr><td class="formLabel">Linee sottoposte a controllo </td><td>
<% for (int i = 0; i<jsonLinee.length(); i++) {
JSONObject jsonLinea = (JSONObject) jsonLinee.get(i);%>
<%=jsonLinea.get("nome") %><br/><br/>
<% } %>
</td></tr>
<%} }%>

<% if ( ((JSONObject) jsonControllo).has("Dati")) { %>
<% JSONObject jsonDati = (JSONObject) jsonControllo.get("Dati");
if (jsonDati.length()>0) {%>
<tr><td class="formLabel">Data inizio controllo </td><td><%=fixData(jsonDati.get("dataInizio"))%></td></tr>
<tr><td class="formLabel">Data fine controllo </td><td><%=fixData(jsonDati.get("dataFine"))%></td></tr>
<tr><td class="formLabel">Note controllo </td><td><%=jsonDati.get("note")%></td></tr>
<%} } %>

<% if ( ((JSONObject) jsonControllo).has("Asl")) { %>
<% JSONObject jsonAsl = (JSONObject) jsonControllo.get("Asl");
if (jsonAsl.length()>0) {%>
<tr><td class="formLabel">ASL controllo </td><td><%=jsonAsl.get("nome")%></td></tr>
<%} } %>

<% if ( ((JSONObject) jsonControllo).has("Oggetti")) { 
JSONArray jsonOggetti = (JSONArray) jsonControllo.get("Oggetti");
if (jsonOggetti.length()>0) {%>
<tr><td class="formLabel">Oggetto del controllo </td><td>
<% for (int i = 0; i<jsonOggetti.length(); i++) {
JSONObject jsonOggetto = (JSONObject) jsonOggetti.get(i);%>
<%=jsonOggetto.get("nome") %><br/><br/>
<% } %>
</td></tr>
<%} } %>

<% if ( ((JSONObject) jsonControllo).has("Motivi")) { 
JSONArray jsonMotivi = (JSONArray) jsonControllo.get("Motivi");
if (jsonMotivi.length()>0) {%>
<tr><td class="formLabel">Motivi del controllo </td><td>
<% for (int i = 0; i<jsonMotivi.length(); i++) {
JSONObject jsonMotivo = (JSONObject) jsonMotivi.get(i);
JSONObject jsonPerContoDi = (JSONObject) jsonMotivo.get("PerContoDi");
%>
<%=jsonMotivo.get("nome") %><br/> <b>Per Conto Di:</b> <%=jsonPerContoDi.get("nome") %><br/><br/>
<% } %>
</td></tr>
<%} } %>

<% if ( ((JSONObject) jsonControllo).has("Nucleo")) { 
JSONArray jsonNucleo = (JSONArray) jsonControllo.get("Nucleo");
if (jsonNucleo.length()>0) {%>
<tr><td class="formLabel">Nucleo ispettivo </td><td>
<% 
for (int i = 0; i<jsonNucleo.length(); i++) {
JSONObject jsonComponente = (JSONObject) jsonNucleo.get(i);
JSONObject jsonQualifica = (JSONObject) jsonComponente.get("Qualifica");
JSONObject jsonStruttura = (JSONObject) jsonComponente.get("Struttura");
%>
<%=jsonComponente.get("nominativo") %> (<b><%=jsonQualifica.get("nome") %></b>) <%=jsonStruttura.get("nome") %> </b><br/><br/>
<% } %>
</td></tr>
<%} }%>

</table>
<!-- RIEPILOGO -->

</center>

<!--JSON -->
<br/><br/><br/><br/>
<textarea rows="10" cols="200" readonly id="jsonControllo" name="jsonControllo"><%=jsonControllo%></textarea>
<!--JSON -->

</form>