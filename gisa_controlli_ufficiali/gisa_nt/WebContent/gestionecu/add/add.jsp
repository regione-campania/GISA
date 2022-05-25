<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="jsonControllo" class="org.json.JSONObject" scope="request"/>
<jsp:useBean id="ListaTecniche" class="java.util.ArrayList" scope="request"/>

<%@ page import="org.aspcfs.modules.gestionecu.base.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<script>
function checkForm(form){
	
	var esito = true;
	var msg = '';
	
	if (form.tecnicaId.value==""){
		msg+= "Selezionare una tecnica del controllo.";
		esito = false;
	}
	
	if (!esito){
		alert(msg);
		return false;
	}
	
	loadModalWindow();
	form.submit();
}

function filtraRigheTecniche() {
	  // Declare variables
	  var input, filter, table, tr, td, i, txtValue;
	  input0 = document.getElementById("myInputTecnica");
	  filter0= input0.value.toUpperCase();
	  
	  table = document.getElementById("tableTecniche");
	  tr = table.getElementsByTagName("tr");

	  // Loop through all table rows, and hide those who don't match the search query
	  for (i = 0; i < tr.length; i++) {
	    td0 = tr[i].getElementsByTagName("td")[0];
	    
	    if (td0) {
	      txtValue0 = td0.textContent || td0.innerText;
	      
	      if (txtValue0.toUpperCase().indexOf(filter0) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }
	  }
	}

</script>

<form name="aggiungiCU" action="GestioneCU.do?command=AddLinea&auto-populate=true" onSubmit="" method="post">

<center>

<!-- RIEPILOGO -->
<%@ include file="riepilogo.jsp"%>
<!-- RIEPILOGO -->

<br/>
<table class="details" id="tableTecniche" name="tableTecniche" cellpadding="10" cellspacing="10" width="100%">
<tr><th colspan="2"><center><b>TECNICA DEL CONTROLLO</b></center></th></tr>

<tr>
<th><input type="text" id="myInputTecnica" onkeyup="filtraRigheTecniche()" placeholder="FILTRA TECNICA" style="width: 100%"></th>
</tr>

<%for (int i = 0; i<ListaTecniche.size(); i++){
	Tecnica tec = (Tecnica) ListaTecniche.get(i);%>
	<tr><td colspan="2">
	<input type="radio" id="tecnicaId_<%=i %>" name="tecnicaId" value="<%=tec.getId()%>"/> <%=tec.getNome() %>
	<input type="hidden" id="tecnicaNome_<%=tec.getId()%>" name="tecnicaNome_<%=tec.getId()%>" value="<%=tec.getNome() %>"/> 
	</td></tr>
	<% } %>
</table>

<!-- BOTTONI -->
<table class="details" cellpadding="10" cellspacing="10" width="100%">
<tr><td colspan="2" align="center"><br/><br/>
<input type="button" style="font-size:40px" value="PROSEGUI" onclick="checkForm(this.form)"/></td></tr>
</table>
<!-- BOTTONI -->

</center>

<!--JSON -->
<br/><br/><br/><br/>
<textarea rows="10" cols="200" readonly id="jsonControllo" name="jsonControllo"><%=jsonControllo%></textarea>
<!--JSON -->

</form>

