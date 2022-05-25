<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="ListaStrutture" class="java.util.ArrayList" scope="request"/>
<%@ page import="org.aspcfs.modules.gestionecu.base.*"%>

<%@ page import="java.util.ArrayList"%>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<script>

function setPerContoDi(val){
	var motivoId = window.opener.document.getElementById("motivoIdSelezionato").value;
	window.opener.document.getElementById("motivoPerContoDiId_"+motivoId).value = val.value;
	window.opener.document.getElementById("motivoPerContoDiNome_"+motivoId).value = val.getAttribute("uo");
	window.opener.document.getElementById("label_"+motivoId).innerHTML = "<br/>"+val.getAttribute("uo");
	window.close();
}
	
function filtraRighePerContoDi() {
  // Declare variables
  var input, filter, table, tr, td, i, txtValue;
  input0 = document.getElementById("myInputAsl");
  input1 = document.getElementById("myInputTipologia");
  input2 = document.getElementById("myInputStruttura");
  input3 = document.getElementById("myInputDescrizione");
  
  filter0 = input0.value.toUpperCase();
  filter1 = input1.value.toUpperCase();
  filter2 = input2.value.toUpperCase();
  filter3 = input3.value.toUpperCase();
  
  table = document.getElementById("tablePerContoDi");
  tr = table.getElementsByTagName("tr");

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {
    td0 = tr[i].getElementsByTagName("td")[0];
    td1 = tr[i].getElementsByTagName("td")[1];
    td2 = tr[i].getElementsByTagName("td")[2];
    td3 = tr[i].getElementsByTagName("td")[3];
    
    if (td0) {
      txtValue0 = td0.textContent || td0.innerText;
      txtValue1 = td1.textContent || td1.innerText;
      txtValue2 = td2.textContent || td2.innerText;
      txtValue3 = td3.textContent || td3.innerText;
      
      if (txtValue0.toUpperCase().indexOf(filter0) > -1 && txtValue1.toUpperCase().indexOf(filter1) > -1 && txtValue2.toUpperCase().indexOf(filter2) > -1 && txtValue3.toUpperCase().indexOf(filter3) > -1 ) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
}
</script>


<table class="details" id ="tablePerContoDi" name="tablePerContoDi" cellpadding="10" cellspacing="10" width="100%" style="border-collapse: collapse">
<tr><th colspan="5">
<font color="red">Attenzione! Di seguito sono riportate tutte le strutture presenti nello strumento di calcolo per cui è stato eseguito il "Salva e Chiudi".<br>
Qualora non fossero presenti le strutture desiderate, controllare che figurino correttamente nello strumento di calcolo e che quest'ultimo sia stato Salvato/Chiuso.
</font>
</th></tr>
<tr>
<th>ASL</th>
<th>TIPOLOGIA STRUTTURA</th>
<th>DESCRIZIONE STRUTTURA</th>
<th>STRUTTURA DI APPARTENENZA</th>
<th></th>
</tr>

<tr>
<th><input type="text" id="myInputAsl" onkeyup="filtraRighePerContoDi()" placeholder="FILTRA ASL" style="width: 100%"></th>
<th><input type="text" id="myInputTipologia" onkeyup="filtraRighePerContoDi()" placeholder="FILTRA TIPOLOGIA" style="width: 100%"></th>
<th><input type="text" id="myInputStruttura" onkeyup="filtraRighePerContoDi()" placeholder="FILTRA STRUTTURA" style="width: 100%"></th>
<th><input type="text" id="myInputDescrizione" onkeyup="filtraRighePerContoDi()" placeholder="FILTRA DESCRIZIONE" style="width: 100%"></th>
<th></th>
</tr>
	
<% for (int i = 0; i<ListaStrutture.size(); i++) {
		Struttura str = (Struttura) ListaStrutture.get(i);  %>
		<tr>
		<td><%= str.getAsl()%></td>
		<td><%= str.getTipologia()%></td>
		<td><%= str.getAppartenenza() %></td>
		<td><%= str.getDescrizione() %></td>
		<td><input type="radio" onClick="setPerContoDi(this)" uo="<%=str.getAppartenenza().replaceAll("\"", "")+"->"+str.getDescrizione().replaceAll("\"", "")  %>" name = "uoSelect" value="<%=str.getId()%>" ></td>
		</tr>
<%} %>
	
</table>



