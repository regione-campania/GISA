<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.guc.Utente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="//WEB-INF/ustl.tld" prefix="us" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pannello</title>
</head>
<body>
<div><center>

<form id="utenti"  name="utenti" method="post" action="Home.us">
	<input name="UTENTI" type="submit" value="GESTIONE UTENTI"/>
</form>


<us:can>
<form id="ruolipermessi" action="guc.rpm.MyConnectAction.us" name="ruolipermessi" method="get">
	<input name="RUOLIPERMESSI" type="submit" value="GESTIONE RUOLI & PERMESSI"/>
</form>
</us:can>

</center></div>
<script>
	var browser = navigator.userAgent;
	if (browser.indexOf("Firefox")>-1){
		alert("ATTENZIONE!!!\n\nModifiche agli utenti effettuate all'ora di punta potrebbe comportare rallentamenti degli ambienti di destinazione.\n\nLe modifiche agli utenti hanno effetto istantaneo su tutti i sistemi.\n\nVERSIONE OTTIMIZZATA PER FIREFOX");
	} else {
		alert("ATTENZIONE!!! E' possibile utilizzare il sistema S.I.R.V. solo con browser Firefox");
		window.location='login.Logout.us';
	}
</script>
</body>
</html>