<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.guc.Utente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pannello</title>
</head>
<body>
<div><center>
<table>

<c:if test = "${utente.ruolo == 'Amministratore' || utente.ruolo == 'SuperAmministratore'}">
<tr><td align="center" colspan="2">
<form id="utenti"  name="utenti" method="post" action="Home.us">
<input name="UTENTI" type="submit" value="GESTIONE UTENTI" style="width:500px; height: 100px; font-size: 20px"/>
</form>
</td></tr>
</c:if>

<c:if test = "${utente.ruolo == 'SuperAmministratore'}">
<tr><td align="center">
<form id="ruolo" action="guc.ToAddRuolo.us" name="ruolo" method="get">
<input name="RUOLO" type="submit" value="INSERISCI RUOLO" style="width:500px; height: 50px; font-size: 20px"/>
</form>
</td></tr>
</c:if>

<%-- <c:if test = "${utente.ruolo == 'Amministratore' || utente.ruolo == 'SuperAmministratore'}"> --%>
<!-- <tr><td align="center" colspan="2"> -->
<!-- <form id="raggruppa"  name="raggruppa" method="post" action="ToGroup.us"> -->
<!-- <input name="RAGGRUPPA" type="submit" value="RAGGRUPPA UTENTI" style="width:500px; height: 50px; font-size: 20px"/> -->
<!-- </form> -->
<!-- </td></tr> -->
<%-- </c:if> --%>

<c:if test = "${utente.ruolo == 'Amministratore' || utente.ruolo == 'SuperAmministratore'}">
<tr><td align="center" colspan="2">
<form id="raggruppabduvam"  name="raggruppabduvam" method="post" action="raggruppabduvam.ToGroupBduVam.us">
<input name="RAGGRUPPABDUVAM" type="submit" value="RAGGRUPPA UTENTI BDU/VAM" style="width:500px; height: 50px; font-size: 20px"/>
</form>
</td></tr>
</c:if>

<c:if test = "${utente.ruolo == 'GestoreRichiesteValidazione' || utente.ruolo == 'Amministratore' || utente.ruolo == 'SuperAmministratore'}">
<tr><td align="center" colspan="2">
<form id="valida" name="valida" method="post" action="validazione.ToValidazione.us">
<input name="VALIDA" type="submit" value="REGISTRAZIONE UTENTI" style="width:500px; height: 100px; font-size: 20px"/>
</form>
</td></tr>
</c:if>

<c:if test = "${utente.ruolo == 'SuperAmministratore'}">
<tr><td align="center" colspan="2">
<form id="epc"  name="epc" method="post" action="endpointconnector.ConfigActionList.us">
<input name="CONTROLLO CONFIGURAZIONE EPC" type="submit" value="CONTROLLO CONFIGURAZIONE EPC" style="width:500px; height: 50px; font-size: 20px"/> 
</form>
</td></tr>
</c:if>

</table>
</center></div>

<script>
	var browser = navigator.userAgent;
	if (browser.indexOf("Firefox")>-1){
		alert("ATTENZIONE!!!\n\nModifiche agli utenti effettuate all'ora di punta potrebbe comportare rallentamenti degli ambienti di destinazione.\n\nLe modifiche agli utenti hanno effetto istantaneo su tutti i sistemi.\n\nVERSIONE OTTIMIZZATA PER FIREFOX");
	} else {
		alert("ATTENZIONE!!! E' possibile utilizzare il sistema G.U.C solo con browser Firefox");
		window.location='login.Logout.us';
	}
</script>
</body>
</html>


<h4 style="font-size:12px;text-align:center;background-color:#4477AA;color:white;">Codice sorgente disponibile sul <a  style="color:white;" href="https://github.com/gisa-riuso-temp/guc_cmd" target="_new">repository github della Regione Campania</a></h4>
