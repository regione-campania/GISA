<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="jsonControllo" class="org.json.JSONObject" scope="request"/>
<jsp:useBean id="CU" class="org.aspcfs.modules.gestionecu.base.Controllo" scope="request"/>

<%@ page import="org.aspcfs.modules.gestionecu.base.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<script>
function redirectDettaglioCu(idControllo){
	loadModalWindow();
	window.location.href="Vigilanza.do?command=TicketDetails&id="+idControllo;
}

function backForm(form){
	form.action="GestioneCU.do?command=AddRiepilogo";
	loadModalWindow();
	form.submit();
}
	
</script>

<form name="aggiungiCU" action="" onSubmit="" method="post">

<center>

<% if (CU.getIdControllo()>0) { %>
<font color="green" size="5px">Inserimento del controllo ufficiale eseguito con successo.</font>
<% } else { %>
<font color="red" size="5px">Inserimento del controllo ufficiale fallito.</font>
<br/><br/>

<!-- RIEPILOGO -->
<%@ include file="riepilogo.jsp"%>
<!-- RIEPILOGO -->

<!-- BOTTONI -->
<table class="details" cellpadding="10" cellspacing="10" width="100%">
<tr>
<td colspan="2" align="center"><br/><br/>
<input type="button" value="INDIETRO" onclick="backForm(this.form)" style="font-size:40px; background-color:red"/>
</td>
</tr>
</table>
<!-- BOTTONI -->
<%} %>

</center>

<!--JSON -->
<br/><br/><br/><br/>
<textarea rows="10" cols="200" readonly id="jsonControllo" name="jsonControllo"><%=jsonControllo%></textarea>
<!--JSON -->

</form>


<% if (CU.getIdControllo()>0) { %>
<script>
alert("Inserimento del controllo eseguito con successo.");
redirectDettaglioCu(<%=CU.getIdControllo()%>);
</script>
<% } %>