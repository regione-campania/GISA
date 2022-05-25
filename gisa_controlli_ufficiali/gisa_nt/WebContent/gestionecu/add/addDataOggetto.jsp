<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="jsonControllo" class="org.json.JSONObject" scope="request"/>
<jsp:useBean id="ListaOggetti" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="ListaSpecieTrasportata" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaAsl" class="java.util.ArrayList" scope="request"/>

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
	
	if (form.dataInizioControllo.value==""){
		msg +="Selezionare la data inizio controllo.\n";
		esito = false;	
	}
	
	var aslSelezionata = false;
	var x = document.getElementsByName("aslId");
	for (var i = 0; i<x.length; i++) {
		if (x[i].checked)
			aslSelezionata = true;
	}
	
	if (!aslSelezionata){
		msg +="Selezionare una ASL controllo.\n";
		esito = false;	
	}
	
	var almenoUnOggetto = false;
	var x = document.getElementsByName("oggettoId");
	for (var i = 0; i<x.length; i++) {
		if (x[i].checked)
			almenoUnOggetto = true;
	}
	
	if (x.length>0 && !almenoUnOggetto){
		msg +="Selezionare almeno un oggetto del controllo.\n";
		esito = false;
	}
	
	if (document.getElementById("tr_isAltroAlimentazioneanimale")!=null && document.getElementById("tr_isAltroAlimentazioneanimale").style.display!="none"){
		if (form._isAltroAlimentazioneanimale_ispezioni_desc2.value==""){
			msg +="Selezionare ALIMENTAZIONE ANIMALE: ALTRO.\n";
			esito = false;
		}
	}
	if (document.getElementById("tr_isAltroImpianti")!=null && document.getElementById("tr_isAltroImpianti").style.display!="none"){
		if (form._isAltroAlimentazioneanimale_ispezioni_desc5.value==""){
			msg +="Selezionare IMPIANTI: ALTRO.\n";
			esito = false;
		}
	}
	if (document.getElementById("tr_isAltroRifiuti")!=null && document.getElementById("tr_isAltroRifiuti").style.display!="none"){
		if (form._isAltroAlimentazioneanimale_ispezioni_desc6.value==""){
			msg +="Selezionare RIFIUTI: ALTRO.\n";
			esito = false;
		}
	}
	if (document.getElementById("tr_isAltroSalute")!=null && document.getElementById("tr_isAltroSalute").style.display!="none"){
		if (form._isAltroAlimentazioneanimale_ispezioni_desc4.value==""){
			msg +="Selezionare SALUTE: ALTRO.\n";
			esito = false;
		}
	}
	if (document.getElementById("tr_isAltroAltro")!=null && document.getElementById("tr_isAltroAltro").style.display!="none"){
		if (form._isAltroAlimentazioneanimale_ispezioni_desc7.value==""){
			msg +="Selezionare ALTRO: ALTRO.\n";
			esito = false;
		}
	}
	if (document.getElementById("tr_isBenessereTrasporto")!=null && document.getElementById("tr_isBenessereTrasporto").style.display!="none"){ 
		
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("-1")){
			msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Specie (O deselezionare SSELEZIONA VOCE).\n";
			esito = false;
		}
		if (form._isBenessereTrasporto_num_documento_accompagnamento.value==""){
			msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Docum. di accompagnam. controllati.\n";
			esito = false;
		}
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("26") && form._isBenessereTrasporto_num_specie26.value==""){
			msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Altro.\n";
			esito = false;
		}
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("1") && form._isBenessereTrasporto_num_specie1.value==""){
					msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Bovini.\n";
					esito = false;
				}
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("10") && form._isBenessereTrasporto_num_specie10.value==""){
					msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Bufali.\n";
					esito = false;
				}
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("20") && form._isBenessereTrasporto_num_specie20.value==""){
					msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Cani.\n";
					esito = false;
				}
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("14") && form._isBenessereTrasporto_num_specie14.value==""){
					msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Conigli.\n";
					esito = false;
				}
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("4") && form._isBenessereTrasporto_num_specie4.value==""){
					msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Equidi.\n";
					esito = false;
				}
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("21") && form._isBenessereTrasporto_num_specie21.value==""){
					msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Ovicaprini.\n";
					esito = false;
				}
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("23") && form._isBenessereTrasporto_num_specie23.value==""){
					msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Pesci.\n";
					esito = false;
				}
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("22") && form._isBenessereTrasporto_num_specie22.value==""){
					msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Pollame.\n";
					esito = false;
				}
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("25") && form._isBenessereTrasporto_num_specie25.value==""){
					msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Rettili.\n";
					esito = false;
				}
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("2") && form._isBenessereTrasporto_num_specie2.value==""){
					msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Suini.\n";
					esito = false;
				}
		if (([...document.getElementById("_isBenessereTrasporto_animalitrasp").options].filter(option => option.selected).map(option => option.value)).includes("24") && form._isBenessereTrasporto_num_specie24.value==""){
					msg +="Selezionare BENESSERE DURANTE IL TRASPORTO: Num. Uccelli.\n";
					esito = false;
				}
	}
	
	
	if (!esito){
		alert(msg);
		return false;
	}
	
	loadModalWindow();
	form.submit();
}

function backForm(form){
	form.action="GestioneCU.do?command=AddLinea";
	loadModalWindow();
	form.submit();
}

function checkOggetto(cb, evento){
	if (document.getElementById("tr_"+evento)!=null){
		if (cb.checked)
			document.getElementById("tr_"+evento).style.display ="table-row";
		else
			document.getElementById("tr_"+evento).style.display ="none";
	}
}
function checkSpecieTrasportata(sel){
	
	for (var option of sel.options){ 
		if (document.getElementById("div_isBenessereTrasporto_num_specie"+option.value)!=null){
			if (option.selected){
				document.getElementById("div_isBenessereTrasporto_num_specie"+option.value).style.display="table-row";
			}
			else {
				document.getElementById("div_isBenessereTrasporto_num_specie"+option.value).style.display="none";
				document.getElementById("_isBenessereTrasporto_num_specie"+option.value).value="";
			}
		}
	}
}	

function filtraRigheOggetti() {
	  // Declare variables
	  var input, filter, table, tr, td, i, txtValue;
	  input1 = document.getElementById("myInputMacrocategoria");
	  input2 = document.getElementById("myInputOggetto");
	  
	  filter1 = input1.value.toUpperCase();
	  filter2 = input2.value.toUpperCase();
	  
	  table = document.getElementById("tableOggetti");
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
	
function filtraRigheAsl() {
	  // Declare variables
	  var input, filter, table, tr, td, i, txtValue;
	  input1 = document.getElementById("myInputAsl");
	  
	  filter1 = input1.value.toUpperCase();
	  
	  table = document.getElementById("tableAsl");
	  tr = table.getElementsByTagName("tr");

	  // Loop through all table rows, and hide those who don't match the search query
	  for (i = 0; i < tr.length; i++) {
	    td1 = tr[i].getElementsByTagName("td")[1];
	    
	    if (td1) {
	      txtValue1 = td1.textContent || td1.innerText;
	      
	      if (txtValue1.toUpperCase().indexOf(filter1) > -1 ) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }
	  }
	}
</script>

<form name="aggiungiCU" action="GestioneCU.do?command=AddMotivo&auto-populate=true" onSubmit="" method="post">

<center>

<!-- RIEPILOGO -->
<%@ include file="riepilogo.jsp"%>
<!-- RIEPILOGO -->

<br/>
<table class="details" cellpadding="10" cellspacing="10" width="100%">
<col width="20%">
<tr><th colspan="2"><center><b>DATI DEL CONTROLLO</b></center></th></tr>
<tr><td class="formLabel">Data inizio controllo </td><td><input type="date" id="dataInizioControllo" name="dataInizioControllo" /></td></tr>
<script>document.getElementById("dataInizioControllo").max = new Date().toISOString().split("T")[0];</script>
<tr><td class="formLabel">Data fine controllo </td><td><input type="date" id="dataFineControllo" name="dataFineControllo" /></td></tr>
<tr><td class="formLabel">Note </td><td><textarea id="noteControllo" name="noteControllo" cols="50" rows="5"></textarea></td></tr>
</table>

<br/>
<table class="details" cellpadding="10" cellspacing="10" width="100%" id="tableAsl">
<col width="20%">
<tr><th colspan="2"><center><b>ASL DEL CONTROLLO</b></center></th></tr>
<tr><th></th>
<th><input type="text" id="myInputAsl" onkeyup="filtraRigheAsl()" placeholder="FILTRA ASL" style="width: 100%"></th>
</tr>

<%for (int i = 0; i<ListaAsl.size(); i++){
	Asl asl = (Asl) ListaAsl.get(i);
%>
<tr><td align="right"><input type="radio" id="aslId_<%=asl.getId() %>" name="aslId" value="<%=asl.getId()%>"/></td><td><%=asl.getNome() %> <input type="text" readonly id="aslNome_<%=asl.getId()%>" name="aslNome_<%=asl.getId()%>" value="<%=asl.getNome()%>"/></td></tr>
<%} %>
</table>

<% if ((int)((JSONObject)((JSONObject) jsonControllo).get("Tecnica")).get("id")!=5) { %>
<br/>
<table class="details" id ="tableOggetti" name="tableOggetti" cellpadding="10" cellspacing="10" width="100%" style="border-collapse: collapse">
<col width="5%"><col width="20%"><col width="30%">
<tr><th colspan="3"><center><b>OGGETTO DEL CONTROLLO</b></center></th></tr>

<tr>
<th>Seleziona</th>
<th>Macrocategoria</th>
<th>Oggetto</th>
</tr>

<tr>
<th></th>
<th><input type="text" id="myInputMacrocategoria" onkeyup="filtraRigheOggetti()" placeholder="FILTRA MACROCATEGORIE" style="width: 100%"></th>
<th><input type="text" id="myInputOggetto" onkeyup="filtraRigheOggetti()" placeholder="FILTRA OGGETTI" style="width: 100%"></th>
</tr>

<%
	for (int i = 0; i<ListaOggetti.size(); i++){
	Oggetto oggetto = (Oggetto) ListaOggetti.get(i);
%>

<tr>
<td>
<input type="checkbox" id="oggettoId_<%=i %>" name="oggettoId" value="<%=oggetto.getCode()%>" onClick="checkOggetto(this, '<%=oggetto.getCodiceEvento()%>')"/>
<input type="readonly" id="oggettoNome_<%=oggetto.getCode() %>" name="oggettoNome_<%=oggetto.getCode() %>" value="<%=oggetto.getCapitolo()%>-><%=oggetto.getOggetto()%>"/>
<inputype="text" readonlyreadonly" id="oggettoCodiceEvento_<%=oggetto.getCode() %>" name="oggettoCodiceEvento_<%=oggetto.getCode() %>" value="<%=oggetto.getCodiceEvento()%>"/>
</td>
<td><%=oggetto.getCapitolo()%></td>
<td><%=oggetto.getOggetto() %></td>
</tr>
<% } %>
</table>
</td></tr>

<tr><td colspan="2">

<table>
<tr id="tr_isAltroAlimentazioneanimale" style="display:none"><td><b>Alimentazione Animale Altro:</b> <input type="text" id="_isAltroAlimentazioneanimale_ispezioni_desc2" name="_isAltroAlimentazioneanimale_ispezioni_desc2" value=""/></td></tr>
<tr id="tr_isAltroImpianti" style="display:none"><td><b>Impianti Altro:</b> <input type="text" id="_isAltroAlimentazioneanimale_ispezioni_desc5" name="_isAltroAlimentazioneanimale_ispezioni_desc5" value=""/></td></tr>
<tr id="tr_isAltroRifiuti" style="display:none"><td><b>Rifiuti Altro:</b> <input type="text" id="_isAltroAlimentazioneanimale_ispezioni_desc6" name="_isAltroAlimentazioneanimale_ispezioni_desc6" value=""/></td></tr>
<tr id="tr_isAltroSalute" style="display:none"><td><b>Salute Altro:</b> <input type="text" id="_isAltroAlimentazioneanimale_ispezioni_desc4" name="_isAltroAlimentazioneanimale_ispezioni_desc4" value=""/></td></tr>
<tr id="tr_isAltroAltro" style="display:none"><td><b>Altro Altro:</b> <input type="text" id="_isAltroAlimentazioneanimale_ispezioni_desc7" name="_isAltroAlimentazioneanimale_ispezioni_desc7" value=""/></td></tr>
<tr id="tr_isBenessereTrasporto" style="display:none"><td>
<b>Benessere durante il trasporto: </b> <%ListaSpecieTrasportata.setMultiple(true); ListaSpecieTrasportata.setSelectSize(10); ListaSpecieTrasportata.setJsEvent("onChange=\"checkSpecieTrasportata(this)\""); %><%= ListaSpecieTrasportata.getHtmlSelect("_isBenessereTrasporto_animalitrasp", -1) %><br/>
<b>Num. docum. di accompagnam. controllati</b>: <input type="text" id="_isBenessereTrasporto_num_documento_accompagnamento" name="_isBenessereTrasporto_num_documento_accompagnamento"/><br/>
<div id="div_isBenessereTrasporto_num_specie1" style="display:none"><b>Num. Bovini</b>: <input type="number" id="_isBenessereTrasporto_num_specie1" name="_isBenessereTrasporto_num_specie1"/><br/></div>
<div id="div_isBenessereTrasporto_num_specie2" style="display:none"><b>Num. Suini</b>: <input type="number" id="_isBenessereTrasporto_num_specie2" name="_isBenessereTrasporto_num_specie2"/><br/></div>
<div id="div_isBenessereTrasporto_num_specie4" style="display:none"><b>Num. Equidi</b>: <input type="number" id="_isBenessereTrasporto_num_specie4" name="_isBenessereTrasporto_num_specie4"/><br/></div>
<div id="div_isBenessereTrasporto_num_specie10" style="display:none"><b>Num. Bufali</b>: <input type="number" id="_isBenessereTrasporto_num_specie10" name="_isBenessereTrasporto_num_specie10"/><br/></div>
<div id="div_isBenessereTrasporto_num_specie14" style="display:none"><b>Num. Conigli</b>: <input type="number" id="_isBenessereTrasporto_num_specie14" name="_isBenessereTrasporto_num_specie14"/><br/></div>
<div id="div_isBenessereTrasporto_num_specie20" style="display:none"><b>Num. Cani</b>: <input type="number" id="_isBenessereTrasporto_num_specie20" name="_isBenessereTrasporto_num_specie20"/><br/></div>
<div id="div_isBenessereTrasporto_num_specie21" style="display:none"><b>Num. Ovicaprini</b>: <input type="number" id="_isBenessereTrasporto_num_specie21" name="_isBenessereTrasporto_num_specie21"/><br/></div>
<div id="div_isBenessereTrasporto_num_specie22" style="display:none"><b>Num. Pollame</b>: <input type="number" id="_isBenessereTrasporto_num_specie22" name="_isBenessereTrasporto_num_specie22"/><br/></div>
<div id="div_isBenessereTrasporto_num_specie23" style="display:none"><b>Num. Pesci</b>: <input type="number" id="_isBenessereTrasporto_num_specie23" name="_isBenessereTrasporto_num_specie23"/><br/></div>
<div id="div_isBenessereTrasporto_num_specie24" style="display:none"><b>Num. Uccelli</b>: <input type="number" id="_isBenessereTrasporto_num_specie24" name="_isBenessereTrasporto_num_specie24"/><br/></div>
<div id="div_isBenessereTrasporto_num_specie25" style="display:none"><b>Num. Rettili</b>: <input type="number" id="_isBenessereTrasporto_num_specie25" name="_isBenessereTrasporto_num_specie25"/><br/></div>
<div id="div_isBenessereTrasporto_num_specie26" style="display:none"><b>Num. Altro</b>: <input type="number" id="_isBenessereTrasporto_num_specie26" name="_isBenessereTrasporto_num_specie26"/><br/></div>
</td></tr>
</table>

</td></tr>
</table>

<% } %>

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

