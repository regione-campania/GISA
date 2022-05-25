<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<jsp:useBean id="risultato" class="java.lang.String" scope="request"/>
<jsp:useBean id="indice" class="java.lang.String" scope="request"/>
<jsp:useBean id="respReloadUtente" class="java.lang.String" scope="request"/>

<script>
function chiudiRicarica(){
window.opener.mostraUtenti(<%=indice%>);
window.close();
}

</script>


<center>
Risultato inserimento utente: <%=risultato %> <br/>
Risultato reload utente: <%=respReloadUtente %>
</center>

Controllare la presenza del nuovo componente nel nucleo ispettivo.<br/>
<input type="button" onClick="chiudiRicarica()" value ="CHIUDI E RICARICA LISTA"/>


    