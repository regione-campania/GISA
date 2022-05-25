<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="listaFlussi"
	class="org.aspcfs.modules.devdoc.base.FlussoList" scope="request" />
<jsp:useBean id="idModulo" class="java.lang.String" scope="request" />
<jsp:useBean id="idFlusso" class="java.lang.String" scope="request" />
<jsp:useBean id="listaTipiModulo"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="Errore" class="java.lang.String" scope="request" />
<jsp:useBean id="listaTipiPriorita"
	class="org.aspcfs.utils.web.LookupList" scope="request" />

<%@page import="org.aspcfs.modules.devdoc.base.Modulo"%>
<%@page import="org.aspcfs.modules.devdoc.base.ModuloList"%>
<%@page import="org.aspcfs.modules.devdoc.base.FlussoList"%>
<%@page import="org.aspcfs.modules.devdoc.base.Flusso"%>
<%@page import="java.util.regex.*"%>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<%@ include file="../../initPage.jsp"%>

<!-- fontawesome css -->
<link href="icons/fontawesome-free/css/all.css" rel="stylesheet">
<!-- filter css -->
<link href="devdoc/utils/filter/filter.css" rel="stylesheet">


<script>
	function checkFormNoteFlusso(event) {
		event.stopPropagation()
		event.preventDefault()
		let nota = document.getElementById("nuova-nota")
		nota.value = nota.value.trim()
		if(nota.value == "")
			alert("Attenzione! L'inserimento di una nota vuota non � consentito.")
		else {
			loadModalWindow()
			document.getElementById("form-aggiungi-nota").submit()
		}
			
	}
</script>


<style>
.visualizza {
	background-color: #b2ffb2;
}

.aggiungi {
	background-color: #fddcd3;
}

.nondisponibile {
	background-color: #D3D3D3;
}

.consegnato {
	background-color: #ddffdd;
}

.standby {
	background-color: #ffff7f;
}

.aperto {
	background-color: #ffffff;
}

.annullato {
	background-color: #ffb2b2;
}
</style>


</style>
<%!public static String zeroPad(int id) {
		String toRet = String.valueOf(id);
		while (toRet.length() < 3)
			toRet = "0" + toRet;
		return toRet;

	}

	public String getFirst10Words(String arg) {
		Pattern pattern = Pattern.compile("([\\S]+\\s*){1,10}");
		Matcher matcher = pattern.matcher(arg);
		matcher.find();
		return matcher.group();
	}%>

<script>
function sleep(milliseconds) {
	  var start = new Date().getTime();
	  for (var i = 0; i < 1e7; i++) {
	    if ((new Date().getTime() - start) > milliseconds){
	      break;
	    }
	  }
	}
	
function aggiungiCampoNote(idUtente){
	var aggiungi = document.getElementById("aggiungiNote"+idUtente);
	var tr = document.getElementById("trAggiungiNote"+idUtente);
	aggiungi.style.display = "none";
	tr.style.display = "table-row"; 
}

function modificaCampoNote(idUtente){
	var input = document.getElementById("note"+idUtente);
	var modifica = document.getElementById("modifica"+idUtente);
	var salva = document.getElementById("salva"+idUtente);
	input.readOnly  = false;
	input.className="scrittura";
	modifica.style.display = "none";
	salva.style.display = "block";
}


function salvaCampoNote(form, idUtente){
	var salva = document.getElementById("salva"+idUtente);
	salva.style.display="none";
	document.getElementById("idUtente").value = idUtente;
	loadModalWindow();
	form.submit();
}

function modificaCampoNote(idUtente){
	var input = document.getElementById("note"+idUtente);
	var modifica = document.getElementById("modifica"+idUtente);
	var salva = document.getElementById("salva"+idUtente);
	input.readOnly  = false;
	input.className="scrittura";
	modifica.style.display = "none";
	salva.style.display = "block";
}

function aggiungi(tipo){
	document.getElementById("idTipo").value = tipo;
	switch(tipo) {
		case 1: document.getElementById("idTipoLabel").innerHTML = "<%=listaTipiModulo.getSelectedValue(1)%>"; break;
		case 2: document.getElementById("idTipoLabel").innerHTML = "<%=listaTipiModulo.getSelectedValue(2)%>"; break;
		case 3: document.getElementById("idTipoLabel").innerHTML = "<%=listaTipiModulo.getSelectedValue(3)%>"; break;
		case 4: document.getElementById("idTipoLabel").innerHTML = "<%=listaTipiModulo.getSelectedValue(4)%>"; break;
		case 5: document.getElementById("idTipoLabel").innerHTML = "<%=listaTipiModulo.getSelectedValue(5)%>"; break;
		case 6: document.getElementById("idTipoLabel").innerHTML = "<%=listaTipiModulo.getSelectedValue(6)%>"; break;
		case 7: document.getElementById("idTipoLabel").innerHTML = "<%=listaTipiModulo.getSelectedValue(7)%>"; break;
		default: break;
	}
	
	document.getElementById("tr-checkbox").hidden = tipo == 6 || tipo == 7 ? true : false; //nascondi 'Invio Mail' se il modulo � VCE o AL

	document.getElementById("bottoniAggiungi").style.display="none";
	document.getElementById("nuovo").style.display="block"; 
}

function aggiungiConFlusso(tipo, idFlusso){
	aggiungi(tipo);
	document.getElementById("idFlusso").value=idFlusso;
	switch(tipo) {
		case 1: document.getElementById("tipoModulo").value = 'B'; break;
		case 2: document.getElementById("tipoModulo").value = 'C'; break;
		case 3: document.getElementById("tipoModulo").value = 'D'; break;
		case 4: document.getElementById("tipoModulo").value = 'CH'; break;
		case 5: document.getElementById("tipoModulo").value = 'A'; break;
		case 6: document.getElementById("tipoModulo").value = 'VCE'; break;
		case 7: document.getElementById("tipoModulo").value = 'AL'; break;
		default: break;
	}
	document.getElementById("aggiuntaDaFlusso").value='SI';
	recuperaFlusso();
	document.getElementById("file1").value = null;
}

function chiudi(){
	document.getElementById("nuovo").style.display="none";
	document.getElementById("bottoniAggiungi").style.display="table-row"; 
	
	document.getElementById("descrizione").value="";
	document.getElementById("descrizione").readOnly=false;
	document.getElementById("descrizione").style.background="";
	document.getElementById("tags").value="";

	document.getElementById("aggiuntaDaFlusso").value='';
	document.getElementById("idFlusso").value='';
	document.getElementById("tipoEstensione").value='';
	document.getElementById("tipoModulo").value='';

	rimuoviFile(1);
}
function checkForm(form){

	if (document.getElementById("file1").value==''){
		alert('Allegare un file.');
		return false;
	}
	
	if (document.getElementById("descrizione").value.trim()==''){
		alert('Inserire una descrizione.');
		return false;
	}
	
	if (document.getElementById("tags").value.trim()==''){
		alert('Inserire i tag.');
		return false;
	}
	
	
	loadModalWindow();
	form.action="GestioneFlussoSviluppo.do?command=Insert";
	form.submit();
}

function apriDettaglioModulo(idModulo)
{

	loadModalWindow();
		
		$.ajax({
	    	type: 'POST',
	   		dataType: "html",
	   		cache: false,
	  		url: 'GestioneFlussoSviluppo.do?command=DettaglioModulo&popup=true',
	        data: { "id": idModulo} , 
	    	success: function(msg) {
	    		loadModalWindowUnlock();
	       		document.getElementById('dettaglioModulo').innerHTML=msg ; 
	       		$('#dettaglioModulo').dialog('open');
	   		},
	   		error: function (err, errore) {
	   			alert('ko '+errore);
	        }
			});
	
}


$(function () {
	$( "#dettaglioModulo" ).dialog({
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: true,
	   	title:"DETTAGLIO MODULO <input type=\"button\" value=\"CHIUDI\" onclick=\"javascript:$('#dettaglioModulo').dialog('close');\" />",
	    width:950,
	    height:750,
	    draggable: false,
	    modal: true
	   
	}).prev(".ui-dialog-titlebar");
	});
$(".ui-widget-overlay").live("click", function() {  $("#dettaglioModulo").dialog("close"); } );	

//dettaglio flusso


function apriDettaglioFlusso(idFlusso)
{

	loadModalWindow();
		
		$.ajax({
	    	type: 'POST',
	   		dataType: "html",
	   		cache: false,
	  		url: 'GestioneFlussoSviluppo.do?command=DettaglioFlusso&popup=true',
	        data: { "id": idFlusso} , 
	    	success: function(msg) {
	    		loadModalWindowUnlock();
	       		document.getElementById('dettaglioFlusso').innerHTML=msg ; 
	       		$('#dettaglioFlusso').dialog('open');
	   		},
	   		error: function (err, errore) {
	   			alert('ko '+errore);
	        }
			});
	
}


$(function () {
	$( "#dettaglioFlusso" ).dialog({
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: true,
	   	title:"DETTAGLIO RICHIESTA <input type=\"button\" value=\"CHIUDI\" onclick=\"javascript:$('#dettaglioFlusso').dialog('close');\" />",
	    width:950,
	    height:750,
	    draggable: false,
	    modal: true
	   
	}).prev(".ui-dialog-titlebar");
	});
$(".ui-widget-overlay").live("click", function() {  $("#dettaglioFlusso").dialog("close"); } );	


//consegna flusso

function apriConsegnaFlusso(idFlusso)
{

	loadModalWindow();
		
		$.ajax({
	    	type: 'POST',
	   		dataType: "html",
	   		cache: false,
	  		url: 'GestioneFlussoSviluppo.do?command=PrepareConsegna&popup=true',
	        data: { "id": idFlusso} , 
	    	success: function(msg) {
	    		loadModalWindowUnlock();
	       		document.getElementById('consegnaFlusso').innerHTML=msg ; 
	       		$('#consegnaFlusso').dialog('open');
	   		},
	   		error: function (err, errore) {
	   			alert('ko '+errore);
	        }
			});
	
}


$(function () {
	$( "#consegnaFlusso" ).dialog({
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: true,
	   	title:"CONSEGNA RICHIESTA <input type=\"button\" value=\"CHIUDI\" onclick=\"javascript:$('#consegnaFlusso').dialog('close');\" />",
	    width:950,
	    height:750,
	    draggable: false,
	    modal: true
	   
	}).prev(".ui-dialog-titlebar");
	});
$(".ui-widget-overlay").live("click", function() {  $("#consegnaFlusso").dialog("close"); } );	

function checkFormConsegna(form, tipo){

	if (tipo == '1' && document.getElementById("dataConsegna").value==''){
		alert('Data consegna obbligatoria.');
		return false;
	}
	
	document.getElementById("bottoneConsegna").style.display = "none";
	form.action="GestioneFlussoSviluppo.do?command=Consegna";
	form.submit();
}


//standby flusso

function apriStandbyFlusso(idFlusso)
{

	loadModalWindow();
		
		$.ajax({
	    	type: 'POST',
	   		dataType: "html",
	   		cache: false,
	  		url: 'GestioneFlussoSviluppo.do?command=PrepareStandby&popup=true',
	        data: { "id": idFlusso} , 
	    	success: function(msg) {
	    		loadModalWindowUnlock();
	       		document.getElementById('standbyFlusso').innerHTML=msg ; 
	       		$('#standbyFlusso').dialog('open');
	   		},
	   		error: function (err, errore) {
	   			alert('ko '+errore);
	        }
			});
	
}


$(function () {
	$( "#standbyFlusso" ).dialog({
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: true,
	   	title:"STANDBY RICHIESTA <input type=\"button\" value=\"CHIUDI\" onclick=\"javascript:$('#standbyFlusso').dialog('close');\" />",
	    width:950,
	    height:750,
	    draggable: false,
	    modal: true
	   
	}).prev(".ui-dialog-titlebar");
	});
$(".ui-widget-overlay").live("click", function() {  $("#standbyFlusso").dialog("close"); } );	

function checkFormStandby(form, tipo){

	if (tipo=='1' && document.getElementById("dataStandby").value==''){
		alert('Data standby obbligatoria.');
		return false;
	}
	
	document.getElementById("bottoneStandby").style.display = "none";
	form.action="GestioneFlussoSviluppo.do?command=Standby";
	form.submit();
}


//annulla flusso

function apriAnnullamentoFlusso(idFlusso)
{

	loadModalWindow();
		
		$.ajax({
	    	type: 'POST',
	   		dataType: "html",
	   		cache: false,
	  		url: 'GestioneFlussoSviluppo.do?command=PrepareAnnullamento&popup=true',
	        data: { "id": idFlusso} , 
	    	success: function(msg) {
	    		loadModalWindowUnlock();
	       		document.getElementById('annullamentoFlusso').innerHTML=msg ; 
	       		$('#annullamentoFlusso').dialog('open');
	   		},
	   		error: function (err, errore) {
	   			alert('ko '+errore);
	        }
			});
	
}


$(function () {
	$( "#annullamentoFlusso" ).dialog({
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: true,
	   	title:"ANNULLAMENTO RICHIESTA <input type=\"button\" value=\"CHIUDI\" onclick=\"javascript:$('#annullamentoFlusso').dialog('close');\" />",
	    width:950,
	    height:750,
	    draggable: false,
	    modal: true
	   
	}).prev(".ui-dialog-titlebar");
	});
$(".ui-widget-overlay").live("click", function() {  $("#annullamentoFlusso").dialog("close"); } );	

function checkFormAnnullamento(form, tipo){

	if (tipo=='1' && document.getElementById("dataAnnullamento").value==''){
		alert('Data annullamento obbligatoria.');
		return false;
	}
	
	document.getElementById("bottoneAnnullamento").style.display = "none";
	form.action="GestioneFlussoSviluppo.do?command=Annullamento";
	form.submit();
}

</script>

<script type="text/javascript" src="dwr/interface/DwrFlussoSviluppo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script>
function recuperaFlusso()
{
	var idFlusso = document.getElementById("idFlusso").value;
	if (idFlusso!='')
		DwrFlussoSviluppo.recuperaFlusso(idFlusso,{callback:recuperaFlussoCallBack,async:false});
	
}
function recuperaFlussoCallBack(val)
{
	var id = val[0];
	var desc = val[1];
	var tags = val[2];
	if (id!=null && id!=''){
			document.getElementById("descrizione").value=val[1];
			document.getElementById("descrizione").readOnly=true;
			document.getElementById("descrizione").style.background="grey";
			document.getElementById("tags").value=val[2];
	}
	else {
		document.getElementById("descrizione").readOnly=false;
		document.getElementById("descrizione").style.background="";
	}
			
	}

// function consegnaFlusso(idFlusso)
// { 
	
// 	if (confirm('Sei sicuro di voler segnare il flusso come CONSEGNATO?')){
// 		DwrFlussoSviluppo.consegnaFlusso(idFlusso,{callback:consegnaFlussoCallBack,async:false});
	
// 	}
	
// }
// function consegnaFlussoCallBack(val)
// {
// 		loadModalWindow();
// 		window.location.href="GestioneFlussoSviluppo.do?command=Dashboard";
// }

function nonPresenzaModulo(idTipo, idFlusso)
{ 
	if (confirm('Sei sicuro di voler segnare il modulo come NON DISPONIBILE?')){
		DwrFlussoSviluppo.nonPresenzaModulo(idFlusso, idTipo,{callback:nonPresenzaModuloCallBack,async:false});
	}
}
function nonPresenzaModuloCallBack(val)
{
	loadModalWindow();
	window.location.href="GestioneFlussoSviluppo.do?command=Dashboard";
}
/*
function filtraFlussi(val){
	var table = document.getElementById("tabellaFlussi");
	for (var i = 1, tr; tr = table.rows[i]; i++) {
		if (val==0) { //TUTTI
			tr.style.display="";
		} else if (val==1) { //APERTI
			if (tr.className=='aperto')
				tr.style.display="";
			else
				tr.style.display="none";
		} else if (val==2) { //CONSEGNATI
			if (tr.className=='consegnato')
				tr.style.display="";
			else
				tr.style.display="none";
		}
	else if (val==3) { //STANDBY
		if (tr.className=='standby')
			tr.style.display="";
		else
			tr.style.display="none";
	}
	else if (val==4) { //ANNULLATI
		if (tr.className=='annullati')
			tr.style.display="";
		else
			tr.style.display="none";
	}
		}
	
}
*/

function openPopupScadenzario(){
	  window.open('GestioneFlussoSviluppo.do?command=Scadenzario','popupSelect',
      'height=400px,width=500px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>



<script>
         function rimuoviFileConsegna(id){
	    	 document.getElementById("fileConsegna"+id).value="";
		  }

</script>


	<form method="post" action="" enctype="multipart/form-data"
		onSubmit="loadModalWindow()" acceptcharset="UTF-8">
		<center>
		<table>
			<tr id="bottoniAggiungi">
					<dhv:permission name="devdoc-mod-a-add">
						<td>
							<input class="yellowBigButton" id="buttonA" type="button"
								value="AGGIUNGI MODULO A" onClick="aggiungi(5)">
						</td>
					</dhv:permission>
					<dhv:permission name="devdoc-mod-b-add">
						<td>
							<input class="yellowBigButton" id="buttonB" type="button"
								value="AGGIUNGI MODULO B" onClick="aggiungi(1)">
						</td>
					</dhv:permission>
<%-- 					<dhv:permission name="devdoc-mod-ch-add"> --%>
<!-- 						<td> -->
<!-- 							<input class="yellowBigButton" id="buttonCH" type="button" -->
<!-- 								value="AGGIUNGI MODULO CH" onClick="aggiungi(4)"> -->
<!-- 						</td> -->
<%-- 					</dhv:permission> --%>
<%-- 					<dhv:permission name="devdoc-mod-c-add"> --%>
<!-- 						<td> -->
<!-- 							<input class="yellowBigButton" id="buttonC" type="button" -->
<!-- 								value="AGGIUNGI MODULO C" onClick="aggiungi(2)"> -->
<!-- 						</td> -->
<%-- 					</dhv:permission> --%>
<%-- 					<dhv:permission name="devdoc-mod-d-add"> --%>
<!-- 						<td> -->
<!-- 							<input class="yellowBigButton" id="buttonD" type="button" -->
<!-- 								value="AGGIUNGI MODULO D" onClick="aggiungi(3)"> -->
<!-- 						</td> -->
<%-- 					</dhv:permission> --%>
				</tr>
				<tr>
					<td colspan="3">

						<table id="nuovo" style="display: none; border: 1px solid;" cellpadding="4">
							<tr>
								<td>Modulo</td>
								<td><b><label id="idTipoLabel"></label></b></td>
							</tr>
							<tr>
								<td>Descrizione</td>
								<td><textarea cols="100" rows="3" id="descrizione"
										name="descrizione"></textarea></td>
							</tr>
							<tr>
								<td>Tags</td>
								<td><textarea cols="100" rows="3" id="tags" name="tags"></textarea></td>
							</tr>
							<tr>
								<td colspan="2"><%@ include file="allegaFile.jsp"%></td>
							</tr>
							<tr id="tr-checkbox">
								<td>						
									<input type="checkbox" id="flag-invio-mail" name="flag-invio-mail" value="checked" checked>
									<label for="flag-invio-mail">Invio notifica via mail</label>
								</td>
							</tr>
							<tr>
								<td align="left"><input type="button" value="ANNULLA"
									onClick="chiudi();return false"></td>
								<td align="right"><input type="button" value="INSERISCI"
									onClick="checkForm(this.form)"></td>
							</tr>
							
							<input type="hidden" id="idTipo" name="idTipo" value="" />
							<input type="hidden" id="aggiuntaDaFlusso"
								name="aggiuntaDaFlusso" value="" />

						</table>
					</td>
				</tr>	
		</table>
		</center>
	</form>
	<br>

<div align="right">
	<a href="#" onClick="openPopupScadenzario()">Scadenzario</a>
</div>

<table class="details" width="100%" cellpadding="4" id="tabellaFlussi">

	<tr>
		<th>Anno</th>
		<th>ID</th>
		<th>Richiesta</th>
		<th>Priorita'</th>
		<th>Stato</th>
		<dhv:permission name="devdoc-mod-a-view">
			<th>Modulo A</th>
		</dhv:permission>
		<dhv:permission name="devdoc-mod-b-view">
			<th>Modulo B</th>
		</dhv:permission>
		<dhv:permission name="devdoc-mod-ch-view">
			<th>Modulo CH</th>
		</dhv:permission>
		<dhv:permission name="devdoc-mod-c-view">
			<th>Modulo C</th>
		</dhv:permission>
		<dhv:permission name="devdoc-mod-d-view">
			<th>Modulo D</th>
		</dhv:permission>
		<dhv:permission name="devdoc-mod-vce-view">
			<th>Modulo VCE</th>
		</dhv:permission>
		<th>Altri Allegati</th>
		<th>Ultima modifica</th>
			<th>Operazioni</th>
	</tr>


	<%
		for (int i = 0; i < listaFlussi.size(); i++) {
			Flusso flusso = (Flusso) listaFlussi.get(i);
			ModuloList listaModuli = flusso.getModuli();
			Modulo modA = null;
			Modulo modB = null;
			Modulo modCH = null;
			Modulo modC = null;
			Modulo modD = null;
			Modulo modVCE = null;
			Modulo modAL = null;

			for (int k = 0; k < listaModuli.size(); k++) {
				Modulo mod = (Modulo) listaModuli.get(k);
				switch (mod.getIdTipo()) {
					case 1 :
						modB = mod; break;
					case 2 :
						modC = mod; break;
					case 3 :
						modD = mod; break;
					case 4 :
						modCH = mod; break;
					case 5 :
						modA = mod; break;
					case 6 :
						modVCE = mod; break;
					case 7 :
						modAL = mod; break;
					default : break;
				}
			}

			boolean standby = false;
			if (flusso.getDataStandby() != null)
				standby = true;
			boolean annullato = false;
			if (flusso.getDataAnnullamento() != null)
				annullato = true;
			boolean consegnato = false;
			if (flusso.getDataConsegna() != null)
				consegnato = true;
	%>

	<%
		String classe = "aperto";
			if (consegnato == true)
				classe = "consegnato";
			else if (standby == true)
				classe = "standby";
			else if (annullato == true)
				classe = "annullato";
	%>


	<tr class="<%=classe%>">
		<td><%=flusso.getData().getYear() + 1900%></td>
		<td> <%=zeroPad(flusso.getIdFlusso())%> </td>
		<td>
		<a href="#" onClick="apriDettaglioFlusso('<%=flusso.getIdFlusso()%>'); return false;"><%=getFirst10Words(flusso.getDescrizione())%></a>
		</td>
		<td><%=listaTipiPriorita.getSelectedValue(flusso.getIdPriorita())%>
		</td>

		<td>
			<%
				if (flusso.getDataConsegna() != null) {
			%>CONSEGNATA <%=getShortDateLong(flusso.getDataConsegna())%> <%
 	} else if (flusso.getDataStandby() != null) {
 %>STANDBY <%=getShortDateLong(flusso.getDataStandby())%> <%
 	} else if (flusso.getDataAnnullamento() != null) {
 %>ANNULLATA <%=getShortDateLong(flusso.getDataAnnullamento())%> <%
 	} else {
 %>APERTA<%
 	}
 %>
		</td>
	<dhv:permission name="devdoc-mod-a-view">
		<td>
			<%
				if (modA != null) {
			%> <%
 	if (!modA.isNonDisponibile()) {
 %> 
 		<a href="#" onClick="apriDettaglioModulo('<%=modA.getId()%>'); return false;">
 			<span class="visualizza">Visualizza</span>
 		</a> 
 <%
 	} else {
 %> <i>NON DISPONIBILE</i> <%
 	}
 %> <%
 	} else if (!consegnato) {
 %> <dhv:permission name="devdoc-mod-a-add">
		<a href="#" onClick="aggiungiConFlusso(5, '<%=flusso.getIdFlusso()%>')">
			<span class="aggiungi">Aggiungi</span>
		</a>
	</dhv:permission>
	<dhv:permission name="devdoc-edit">
		<br />
		<a href="#" onClick="nonPresenzaModulo(5, '<%=flusso.getIdFlusso()%>')">
			<span class="nondisponibile">Non disponibile</span>
		</a>
	</dhv:permission> <%
 	}
 %>
		</td>
	</dhv:permission>
	<dhv:permission name="devdoc-mod-b-view">
		<td>
			<%
				if (modB != null) {
			%> <%
 	if (!modB.isNonDisponibile()) {
 %> 
 		<a href="#" onClick="apriDettaglioModulo('<%=modB.getId()%>'); return false;">
 			<span class="visualizza">Visualizza</span>
 		</a>
 		
 <%
 	} else {
 %> <i>NON DISPONIBILE</i> <%
 	}
 %> <%
 	} if (!consegnato) {
 %> <dhv:permission name="devdoc-mod-b-add">
		<a href="#" onClick="aggiungiConFlusso(1, '<%=flusso.getIdFlusso()%>')">
			<span class="aggiungi">Aggiungi</span>
		</a>
	</dhv:permission>
	<dhv:permission name="devdoc-edit">
		<br />
		<a href="#" onClick="nonPresenzaModulo(1, '<%=flusso.getIdFlusso()%>')">
			<span class="nondisponibile">Non disponibile</span>
		</a>
	</dhv:permission> <%
 	}
 %>
		</td>
	</dhv:permission>
<dhv:permission name="devdoc-mod-ch-view">
		<td>
			<%
				if (modCH != null) {
			%> <%
 	if (!modCH.isNonDisponibile()) {
 %> 
 	
 		<a href="#" onClick="apriDettaglioModulo('<%=modCH.getId()%>'); return false;">
 			<span class="visualizza">Visualizza</span>
 		</a>
 <%
 	} else {
 %> <i>NON DISPONIBILE</i> <%
 	}
 %> <%
 	} else if (!consegnato) {
 %> <dhv:permission name="devdoc-mod-ch-add">
		<a href="#" onClick="aggiungiConFlusso(4, '<%=flusso.getIdFlusso()%>')">
			<span class="aggiungi">Aggiungi</span>
		</a>
	</dhv:permission>
	<dhv:permission name="devdoc-edit">
		<br />
		<a href="#" onClick="nonPresenzaModulo(4, '<%=flusso.getIdFlusso()%>')">
			<span class="nondisponibile">Non disponibile</span>
		</a>
	</dhv:permission>		
	 <%
 	}
 %>
		</td>
	</dhv:permission>
	<dhv:permission name="devdoc-mod-c-view">
	<td>
			<%
	if (modC != null) {
	%> 
		<a href="#" onClick="apriDettaglioModulo('<%=modC.getId()%>'); return false;">
			<span class="visualizza">Visualizza</span>
		</a>
	 <%
 	} if (!consegnato) {
 %> <dhv:permission name="devdoc-mod-c-add">
		<a href="#" onClick="aggiungiConFlusso(2, '<%=flusso.getIdFlusso()%>')">
			<span class="aggiungi">Aggiungi</span>
		</a>
	</dhv:permission> <%
 	}
 %>
	</td>
	</dhv:permission>
	<dhv:permission name="devdoc-mod-d-view">
	<td>
			<%
	if (modD != null) {
	%> 
		<a href="#" onClick="apriDettaglioModulo('<%=modD.getId()%>'); return false;">
			<span class="visualizza">Visualizza</span>
		</a>
	 <%
 	} else if (!consegnato) {
 %> <dhv:permission name="devdoc-mod-d-add">
		<a href="#" onClick="aggiungiConFlusso(3, '<%=flusso.getIdFlusso()%>')">
			<span class="aggiungi">Aggiungi</span>
		</a>
	</dhv:permission> <%
 	}
 %>
	</td>
	</dhv:permission>
	<dhv:permission name="devdoc-mod-vce-view">	
	<td>
				<%
	if (modVCE != null) {
	%> 
		<a href="#" onClick="apriDettaglioModulo('<%=modVCE.getId()%>'); return false;">
			<span class="visualizza">Visualizza</span>
		</a> <%
 	}
 			if (!consegnato) {
 %> <dhv:permission name="devdoc-mod-vce-add">
		<a href="#" onClick="aggiungiConFlusso(6, '<%=flusso.getIdFlusso()%>')">
			<span class="aggiungi">Aggiungi</span>
		</a>
	</dhv:permission> <%
 	}
 %>
	</td>
	</dhv:permission>
	<td>
	<% if (modAL != null) { %> 
			<a href="#" onClick="apriDettaglioModulo('<%=modAL.getId()%>'); return false;">
				<span class="visualizza">Visualizza</span>
			</a> 
	<% } if (!consegnato) { %> 
			<a href="#" onClick="aggiungiConFlusso(7, '<%=flusso.getIdFlusso()%>')">
				<span class="aggiungi">Aggiungi</span>
			</a>
	 <% } %>
	</td>
		<td>
			<%
				if (flusso.getDataUltimaModifica() != null) {
			%> <%=toDateWithTimeasString(flusso.getDataUltimaModifica())%> <%
 	}
 %>
		</td>

	<td>
	 	<%
	 		boolean isFlussoConsegnato = flusso.getDataConsegna() == null ? false : true;
	 		boolean isFlussoInStandby = flusso.getDataStandby() == null ? false : true;
	 		boolean isFlussoAnnullato = flusso.getDataAnnullamento() == null ? false : true;
	 	%>
	 	<% if(isFlussoConsegnato) { %>
	 	        	<dhv:permission name="devdoc-edit">
	 	        	<input type="button" value="RIAPRI" onclick="apriConsegnaFlusso('<%=flusso.getIdFlusso()%>')">
	 	        	</dhv:permission>
	 	<% } else { %>
	 		<% if(isFlussoInStandby) { %>
	 			<dhv:permission name="devdoc-edit">
	 			<input type="button" value="RIATTIVA" onclick="apriStandbyFlusso('<%=flusso.getIdFlusso()%>')">
	 			</dhv:permission>
			<% } else if(isFlussoAnnullato) { %>
				<dhv:permission name="devdoc-edit">
				<input type="button" value="RIPRISTINA" onclick="apriAnnullamentoFlusso('<%=flusso.getIdFlusso()%>')">
				</dhv:permission>
	 		<% } else { %>
	 			<dhv:permission name="devdoc-delete">
	 			<input type="button" value="ANNULLAMENTO" onclick="apriAnnullamentoFlusso('<%=flusso.getIdFlusso()%>')">
	 			</dhv:permission>
	 	        <dhv:permission name="devdoc-edit">
				<input type="button" value="STANDBY" onclick="apriStandbyFlusso('<%=flusso.getIdFlusso()%>')">
				<input type="button" value="CONSEGNA" onclick="apriConsegnaFlusso('<%=flusso.getIdFlusso()%>')">
				</dhv:permission>
	 		<% } %>
	 	<% } %>
	</td>
		

	</tr>

	<%
		}
	%>
	<%--

<td valign="top">

<table class="details" width="100%" cellpadding="4">
<tr><th colspan="4"><%=header %></th></tr>
<%for (int i = 0; i<lista.size(); i++) {
Modulo mod = (Modulo) lista.get(i);%>
<tr class="row<%=i%2%>"> <td valign="middle"> 
<a href="#" onClick="apriDettaglioModulo('<%=mod.getId() %>'); return false;">
<%=mod.getDescrizione()%></a> 


</td></tr>
<%} %>
</table>

</td>

<%} %>

</tr>
</table>

</table>
--%>

	<div id="dettaglioFlusso"></div>

	<div id="dettaglioModulo"></div>

	<div id="consegnaFlusso"></div>

	<div id="standbyFlusso"></div>

	<div id="annullamentoFlusso"></div>


	<script>
$(document).ready(function() {
	<%if (idModulo != null && !idModulo.equals("null") && !idModulo.equals("")) {%>
	apriDettaglioModulo('<%=idModulo%>');
	<%} else if (idFlusso != null && !idFlusso.equals("null") && !idFlusso.equals("")) {%>
	apriDettaglioFlusso('<%=idFlusso%>');
	<%}%>
});
</script>


	<%
		if (Errore != null && !Errore.equals("")) {
	%>
	<script>
alert("<%=Errore%>
		");
	</script>
	<%
		}
	%>

<!-- fontawesome js -->	
<script src="icons/fontawesome-free/js/all.js"></script>
<!-- filter js -->
<script src="devdoc/utils/filter/filter.js"></script>