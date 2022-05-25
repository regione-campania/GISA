<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="jsonControllo" class="org.json.JSONObject" scope="request"/>
<jsp:useBean id="ListaMotivi" class="java.util.ArrayList" scope="request"/>

<%@ page import="org.aspcfs.modules.gestionecu.base.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<script>
function checkForm(form){
	var msg = "";
	var esito = true;
	
	var almenoUnMotivo = false;
	var tuttiPerContoDi = true;

	var x = document.getElementsByName("motivoId");
	for (var i = 0; i<x.length; i++) {
		if (x[i].checked){
			almenoUnMotivo = true;
			if (document.getElementById("motivoPerContoDiId_"+x[i].id).value=='')
				tuttiPerContoDi = false;
		}
	}
	
	if (!almenoUnMotivo){
		msg +="Selezionare almeno un motivo di ispezione.\n";
		esito = false;
	}
	
	if (!tuttiPerContoDi){
		msg +="Selezionare un Per Conto Di per ogni motivo selezionato.\n";
		esito = false;
	}
		
	if (!esito){
		alert(msg);
		return false;
	}
	
	loadModalWindow();
	form.submit();
}

function backForm(form){
	form.action="GestioneCU.do?command=AddDataOggetto";
	loadModalWindow();
	form.submit();
}

function checkMotivo(cb, evento){
	
	//per conto di
	if (cb.checked)
		document.getElementById("td_"+cb.id).style.visibility ="visible";
	else
		document.getElementById("td_"+cb.id).style.visibility ="hidden";
	
	//campi estesi
	if (document.getElementById("tr_"+evento)!=null){
		if (cb.checked)
			document.getElementById("tr_"+evento).style.display ="table-row";
		else
			document.getElementById("tr_"+evento).style.display ="none";
	}
}

function filtraRigheMotivi() {
	  // Declare variables
	  var input, filter, table, tr, td, i, txtValue;
	  input1 = document.getElementById("myInputTipoMotivo");
	  input2 = document.getElementById("myInputDescrizioneMotivo");
	  
	  filter1 = input1.value.toUpperCase();
	  filter2 = input2.value.toUpperCase();
	  
	  table = document.getElementById("tableMotivi");
	  tr = table.getElementsByTagName("tr");

	  // Loop through all table rows, and hide those who don't match the search query
	  for (i = 0; i < tr.length; i++) {
	    td0 = tr[i].getElementsByTagName("td")[0];
	    td1 = tr[i].getElementsByTagName("td")[1];
	    td2 = tr[i].getElementsByTagName("td")[2];
	    
	    if (td0) {
	      txtValue0 = td0.textContent || td0.innerText;
	      txtValue1 = td1.textContent || td1.innerText;
	      txtValue2 = td2.textContent || td2.innerText;
	      
	      if (txtValue1.toUpperCase().indexOf(filter1) > -1 && txtValue2.toUpperCase().indexOf(filter2) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }
	  }
	}
	
	function apriPerContoDi(motivoId, idAsl, idUtente){
		popupPerContoDi =  window.open('GestioneCUUtil.do?command=CaricaPerContoDi&idAsl='+idAsl+'&idUtente='+idUtente, 'popupPerContoDi',
        'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
		document.getElementById("motivoIdSelezionato").value = motivoId;
	}
</script>

<form name="aggiungiCU" action="GestioneCU.do?command=AddNucleo&auto-populate=true" onSubmit="" method="post">

<center>

<!-- RIEPILOGO -->
<%@ include file="riepilogo.jsp"%>
<!-- RIEPILOGO -->

<br/>
<table class="details" id ="tableMotivi" name="tableMotivi" cellpadding="10" cellspacing="10" width="100%" style="border-collapse: collapse">
<col width="10%"><col width="20%"><col width="40%">
<tr><th colspan="4"><center><b>MOTIVO DEL CONTROLLO</b></center></th></tr>

<tr>
<th>Seleziona</th>
<th>Tipo motivo</th>
<th>Descrizione</th>
<th>Per conto di</th>
</tr>

<tr>
<th></th>
<th><input type="text" id="myInputTipoMotivo" onkeyup="filtraRigheMotivi()" placeholder="FILTRA TIPO MOTIVO" style="width: 100%"></th>
<th><input type="text" id="myInputDescrizioneMotivo" onkeyup="filtraRigheMotivi()" placeholder="FILTRA DESCRIZIONE" style="width: 100%"></th>
<th></th>
</tr>

<% 
for (int i = 0; i<ListaMotivi.size(); i++) {
Motivo motivo = (Motivo) ListaMotivi.get(i); %>

<tr>
<td>
<input type="checkbox" id ="<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>" name="motivoId" onClick="checkMotivo(this, '<%=motivo.getCodiceEvento()%>')" value="<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>"/>
<input type="text" readonly id ="motivoNome_<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>" name ="motivoNome_<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>" value="<%= motivo.getIdMotivoIspezione() == 89 ? motivo.getDescrizionePiano() : motivo.getDescrizioneMotivoIspezione() %>"/>
<input type="text" readonly id ="motivoCodiceEvento_<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>" name ="motivoCodiceEvento_<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>" value="<%= motivo.getCodiceEvento() %>"/>
</td>
<td><%= motivo.getIdMotivoIspezione() == 89 ? motivo.getDescrizioneMotivoIspezione() : "ATTIVITA" %></td>
<td><%= motivo.getIdMotivoIspezione() == 89 ? motivo.getDescrizionePiano() : motivo.getDescrizioneMotivoIspezione() %></td>
<td id="td_<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>" style="visibility:hidden">
<a href="#" onClick="apriPerContoDi('<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>', '<%=(int)((JSONObject)((JSONObject) jsonControllo).get("Asl")).get("id") %>', '<%=(int)((JSONObject)((JSONObject) jsonControllo).get("Utente")).get("userId") %>'); return false;">Seleziona Per Conto Di</a>
<input type="text" readonly id="motivoPerContoDiId_<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>" name="motivoPerContoDiId_<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>" value=""/>
<input type="text" readonly id="motivoPerContoDiNome_<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>" name="motivoPerContoDiNome_<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>" value=""/>
<label id="label_<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>" name="label_<%= motivo.getIdMotivoIspezione()%>_<%=motivo.getIdPiano()%>"></label>
</td>
</tr>
<% } %>
</table>	

</td></tr>

<tr><td colspan="2">

<table>
<tr id="tr_isAllarmeRapido" style="display:none"><td><b>Allarme rapido:</b> <textarea rows="3" cols="20" name="_isAllarmeRapido_desc2"></textarea></td></tr>
<tr id="tr_isMacellazioneUrgenza" style="display:none"><td><b>Macellazione urgenza:</b> <textarea rows="3" cols="20" name="_iisMacellazioneUrgenza_desc5"></textarea></td></tr>
</table>

</td></tr>
</table>

<!-- BOTTONI -->
<table class="details" cellpadding="10" cellspacing="10" width="100%">
<tr>
<td colspan="2" align="center"><br/><br/>
<input type="button" value="INDIETRO" onclick="backForm(this.form)" style="font-size:40px; background-color:red"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" style="font-size:40px" value="PROSEGUI" onclick="checkForm(this.form)"/>
</td>
</tr>
</table>
<!-- BOTTONI -->

</center>

<!--JSON -->
<br/><br/><br/><br/>
<textarea rows="10" cols="200" readonly id="jsonControllo" name="jsonControllo"><%=jsonControllo%></textarea>
<!--JSON -->

</form>

<input type="hidden" id="motivoIdSelezionato" name="motivoIdSelezionato"/>
