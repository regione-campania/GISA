<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope = "request"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="javascript/vendor/moment.min.js"></script>
<script src="javascript/noscia/addNoScia.js"></script>
<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/interface/DWRnoscia.js"> </script>

<script  src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script src="javascript/gestioneanagrafica/add.js"></script>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="GestionePraticheAction.do?command=HomeGins">PRATICHE SUAP 2.0</a> >
			<a href="GestionePraticheAction.do?command=ListaPraticheStabilimenti">GESTIONE ATT. FISSE, MOBILI E RICONOSCIMENTO</a> > NUOVA PRATICA
		</td>
	</tr>
</table>
<div align="right" style="padding-right: 1%">
<img class="masterTooltip" onclick="apri_mini_guida();" src="images/questionmark.png" title="GUIDA FUNZIONALITA DI QUESTA PAGINA"  width="20">
</div>

<input type="hidden" id="tipo_operazione_pratica" value="${tipo_operazione_pratica}"/>

<form id="inserimentoPratica" name="inserimentoPratica" class="form-horizontal" role="form" method="post" action="" >
<br>
<input type="hidden" id="stabId" name="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>
<input type="hidden" id="altId" name="altId" value="<%=StabilimentoDettaglio.getAltId()%>"/>
<input type="hidden" id="aslUtente" value="<%=User.getSiteId()%>"/>
<input type="hidden" id="idAggiuntaPratica" name="idAggiuntaPratica" value="<%=System.currentTimeMillis() %><%=new Random().nextInt(10000) %>"/>

<table id="tabella_crea_pratica" class="details" cellspacing="0" border="0" width="100%" cellpadding="4">
<tbody>
	<tr>
		<th colspan="4">Dati pratica</th>
	</tr>
	<tr>
		<th style="text-align:center; width:25%;"><p>NUMERO PRATICA</p></th>
		<th style="text-align:center; width:20%;"><p>COMUNE</p></th>
		<th style="text-align:center; width:15%;"><p>DATA PEC / DATA SCIA</p></th>
		<th style="text-align:center; width:40%;"><p>TIPO PRATICA</p></th>
	</tr>
	<tr>
		<td style="text-align:center;">
			<center>
				<p><input type="text" id="numeroPratica" name="numeroPratica" size="40" autocomplete="off" 
				maxlength="40" pattern="[A-Za-z /\-, 0-9 .]{1,40}" 
				title="numero pratica: 40 caratteri consentiti (lettere non accentate, numeri, / - , .)"/>
				<br>
				<font color="red" size="0.5%" >* Campo non obbligatorio. Nel caso venga lasciato vuoto il sistema all'atto dell'inseriemento 
									provvederà a generarne uno automaticamente</font>
				</p>
			</center>
		</td>
	
		<td style="text-align:center;">
			<% if(StabilimentoDettaglio.getIdStabilimento() > 0){ %>
				<select id="comune_richiedente" name="comune_richiedente" >
					<option value="${comuneOsaId}">${comuneTesto}</option>
				</select>
			<% } else {%>
				<select id="comune_richiedente" name="comune_richiedente">
				</select>
			<% } %>
			<font color="red" size="0.5%" >*</font>
		</td>
	
		<td style="text-align:center;">
			<input placeholder="DATA PEC / DATA SCIA" type="text" id="data_richiesta" name="data_richiesta" autocomplete="off" onkeydown="return false" size="20" style="text-align:center;" >
		</td>

		<td style="text-align:center;"> 
			<% if(StabilimentoDettaglio.getIdStabilimento() == 0){ %>
					<select id="idTipologiaPratica" name="idTipologiaPratica" style="width:350px">
						<option></option>
						<c:forEach items="${listRichieste}" var="idTipologiaPratica">
							<option value="${idTipologiaPratica.code}">${idTipologiaPratica.long_description}</option>
						</c:forEach>
					</select>
			<% } else { %>
					<select id="idTipologiaPratica" name="idTipologiaPratica" style="width:350px">
						<option></option>
						<c:forEach items="${listRichieste}" var="idTipologiaPratica">
						    <c:choose>
						    	<c:when test="${idTipologiaPratica.code != 1}">
						    		<option value="${idTipologiaPratica.code}">${idTipologiaPratica.long_description}</option>
						    	</c:when>
						    </c:choose>	
						</c:forEach>
					</select>
			<% } %> 
			<font color="red" size="0.5%" >*</font>
			<img class="masterTooltip" onclick="apri_mini_guida_tipo_pratica();" src="images/questionmark.png" title="GUIDA FUNZIONALITA DI QUESTA PAGINA"  width="20">			
		</td>
	</tr>
	
	<tr id="button_aggiungi_allegati">
		<th colspan="4">lista allegati &nbsp;&nbsp;&nbsp;<input type="button" value="seleziona allegati" onclick="apri_lista_allegati();"/></th>
	</tr>
		
</tbody>
</table>

<table id="tabella_schede_supplementari" class="details" cellspacing="0" border="0" width="100%" cellpadding="4">
<tbody>
	<tr id="tr_aggiungi_schede_supplementari">
		<th colspan="4">
			schede supplementari &nbsp;&nbsp;&nbsp;
			<input type="button" value="allega scheda supplementare" 
				onclick="javascript:openUploadAllegatoGins(document.getElementById('idAggiuntaPratica').value, 'schedasup_'+document.getElementById('numero_schede_sup').value, 'GINS_Pratica')"/>
		</th>
	</tr>
</tbody>
</table>
<input type="hidden" id="numero_schede_sup" value="1"/>
<br><br>


<table width="100%" cellpadding="4">
<tr>
	<td align="left">
		<input type="button" class="yellowBigButton" style="width: 250px;" 
			id="salva_temporaneo"
			onclick="salva_pratica('3')" value="Salva temporaneo" />
	</td>
	<td align="center">
		<input type="button" class="yellowBigButton" style="width: 450px; height: 70px;" 
			id="salva_e_continua"
			onclick="salva_pratica('1')" value="Salva e continua"/>
	</td>
	<td align="right">
		<input type="button" class="yellowBigButton" style="width: 250px;"
			onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestionePraticheAction.do?command=ListaPraticheStabilimenti'"
			value="Annulla"/>
	</td>
</tr>
</table>


<input type="hidden" id="tipo_output"  name="tipo_output"/>
</form>
<!-- inizio parte popup per miniguida -->
<%@include file="/gestione_pratica/guide_html/mini_guida_crea_pratica.html" %>
<jsp:include page="/gestione_pratica/mini_guida.jsp" />
<%@include file="/gestione_pratica/guide_html/mini_guida_tipo_pratiche.html" %>

<script>
function apri_mini_guida_tipo_pratica() {
	  var messaggio = document.getElementById('descrizione_mini_guida_tipo_pratica').innerHTML;
	  loadModalWindowMiniGuida(messaggio);
}
</script>

<!-- fine parte popup per miniguida -->
<br><br>

<div id='popup_lista_allegati' />
<script src="javascript/gestioneanagrafica/lista_allegati_pratica.js"></script>
<script>

var numero_pratica = '';
var comune_pratica = '';
function validateForm(){
	
	if(document.getElementById('comune_richiedente').value.trim() == ''){
		alert('Inserire comune');
		return false;
	} 
	
	if(document.getElementById('data_richiesta').value.trim() == ''){
		document.getElementById('data_richiesta').value = '';
	} 
	
	if(document.getElementById('idTipologiaPratica').value.trim() == ''){
		alert('Inserire tipo pratica');
		return false;
	} 
	
	if(document.getElementById("numeroPratica").value.trim() == ''){
		
		if (confirm('Attenzione: non hai inserito il numero pratica, il sistema ne genererà automaticamente uno!') == false){
			return false;
		}
	} else if (!document.getElementById("numeroPratica").checkValidity()){
		alert('Attenzione, caratteri non validi in numero pratica: massimo 40 caratteri consentiti (lettere non accentate, numeri, / - , . )');
		return false;
	}
	
	controllaEsistenzaNumeroPratica(document.getElementById("numeroPratica").value.trim(), document.getElementById("comune_richiedente").value);
	
	if(numero_pratica == document.getElementById("numeroPratica").value.trim() 
				&& comune_pratica == document.getElementById("comune_richiedente").value){
		
		var alertmess = "Numero pratica "  + numero_pratica + 
						" non utilizzabile perche' risulta già assegnato ad un'altra pratica nell'ambito dello stesso comune.\n" +
						"Per visualizzare i dettagli del numero pratica inserito andare in RICERCA PRATICA."; 
		alert(alertmess);
		document.getElementById("numeroPratica").value = "";
		return false;
	}
	
	var campi = document.getElementById("inserimentoPratica").getElementsByTagName("input"); 
	for (var x = 0; x<campi.length; x++){
		var campo = campi[x];
		if (campo.name.includes("header_")>0 && campo.value == ''){
			alert('Attenzione. Allegare tutta la documentazione richiesta.');
			return false;
		}
	}
	
	loadModalWindowCustom("Attendere Prego...");
	return true;
	
}

function salva_pratica(scelta_output){
	
	document.getElementById('tipo_output').value = scelta_output;
	document.getElementById('inserimentoPratica').action = "GestionePraticheAction.do?command=InserisciPratica";
	if(validateForm()){
		document.getElementById('inserimentoPratica').submit();
	}
	
}


popup_date('data_richiesta');


function popup_date(elemento_html_data){
	$( '#' + elemento_html_data ).datepicker({
		  dateFormat: 'dd-mm-yy',
		  changeMonth: true,
		  changeYear: true,
		  yearRange: '-100:+3',
		  minDate: '01-01-1990',
		  maxDate: 0,
		  dayNamesMin : [ 'do', 'lu', 'ma', 'me', 'gi', 've', 'sa' ],
		  monthNamesShort :['Gennaio','Febbraio','Marzo','Aprile','Maggio','Giugno', 'Luglio','Agosto','Settembre','Ottobre','Novembre','Dicembre'],
		  beforeShow: function(input, inst) {
              setTimeout(function () {
                         var offsets = $('#' + elemento_html_data).offset();
                         var top = offsets.top - 100;
                         inst.dpDiv.css({ top: top, left: offsets.left});
                         $(".ui-datepicker-next").hide();
							$(".ui-datepicker-prev").hide();
							$(".ui-state-default").css({'font-size': 15});
							$(".ui-datepicker-title").css({'text-align': 'center'});
							$(".ui-datepicker-calendar").css({'text-align': 'center'});
               });
             },
        onChangeMonthYear: function(year, month, inst) {
              setTimeout(function () {
                         var offsets = $('#' + elemento_html_data).offset();
                         var top = offsets.top - 100;
                         inst.dpDiv.css({ top: top, left: offsets.left});
                         $(".ui-datepicker-next").hide();
							$(".ui-datepicker-prev").hide();
							$(".ui-state-default").css({'font-size': 15});
							$(".ui-datepicker-title").css({'text-align': 'center'});
							$(".ui-datepicker-calendar").css({'text-align': 'center'});
               });
             }                                                  
		});
}



if(document.getElementById('stabId').value == '0' ){
	popola_select_popup('GetComuneByAsl.do?command=Search&idAsl='+document.getElementById('aslUtente').value , 'comune_richiedente');
}

if(document.getElementById('tipo_operazione_pratica').value != '-1'){
	//idTipologiaPratica
	var select_tipo_pratiche = document.getElementById('idTipologiaPratica');
	var i;
	var label_pratica;
	for(i = 0; i < select_tipo_pratiche.length; i++){
		if(select_tipo_pratiche.options[i].value == document.getElementById('tipo_operazione_pratica').value){
			label_pratica = select_tipo_pratiche.options[i].text;
		}
	}
	$('#idTipologiaPratica').children().remove();
	$('#idTipologiaPratica').append('<option value="' + document.getElementById('tipo_operazione_pratica').value + '">' + label_pratica + '</option>');
	
}


function popola_select_popup(urlservice, id_elemento){
		
	//loadModalWindow();
  	$.ajax({  	    
        url: urlservice,
        dataType: "text",
        async:false,
        success: function(dati) { 	 
	   	    var obj;
	   	    try {
				console.log(dati);
	       		obj = JSON.parse(dati);
			} catch (e) {
				alert(e instanceof SyntaxError); // true
				loadModalWindowUnlock();
				return false;
			}
			
			obj = JSON.parse(dati);	 
			$('#'+id_elemento).children().remove();
			$('#'+id_elemento).append('<option></option>');
		  	for (var i = 0; i < obj.length; i++) {
		  		$('#'+id_elemento).append('<option value="'+obj[i].id+'">'+obj[i].nome+'</option>');
		  	}
		  	loadModalWindowUnlock();
        },
        fail: function(xhr, textStatus, errorThrown){
        	alert('request failed');
       	}
          
  	});
  	
}


function trim(str) {
    try {
        if (str && typeof(str) == 'string') {
            return str.replace(/^\s*|\s*$/g, "");
        } else {
            return '';
        }
    } catch (e) {
        return str;
    }
}


function controllaEsistenzaNumeroPratica(numeroPratica, comune_ric)
{
	loadModalWindowCustom('Verifica esistenza pratica in corso. Attendere...');
	DWRnoscia.controlloEsistenzaNumeroPratica(numeroPratica,comune_ric,"1",{callback:controllaEsistenzaNumeroPraticaCallBack,async:false});
}

function controllaEsistenzaNumeroPraticaCallBack(val)
{	
	var dati = val;
	var objresp;
	objresp = JSON.parse(dati);
	var len = objresp.length;
	if (len > 0){
		
		var praticaobj;
		praticaobj = objresp[0];
		
		numero_pratica = praticaobj.numero_pratica;
		comune_pratica = praticaobj.id_comune_richiedente;
		loadModalWindowUnlock();	
		
	} else {
		loadModalWindowUnlock();
		}
}

	

$(function() {
	
	 $('#popup_lista_allegati').dialog({
		title : 'Lista allegati da aggiungere alla pratica',
        autoOpen: false,
        resizable: false,
        closeOnEscape: false,
        width:1000,
        height:550,
        draggable: false,
        modal: true,
	     buttons: {
			 'torna alla pratica e completa inserimento allegati': function() {
				 aggiungi_allegati_alla_pratica();
                               loadModalWindowUnlock();
				$( this ).dialog('close');
			},
			'ESCI': function() {
                               loadModalWindowUnlock();
				$( this ).dialog('close');
			}
     }
});
	 
});

function apri_lista_allegati(){
	
	var htmlText='<br>';
	htmlText+='<table border=\'1\' cellpadding=\'4\' width=\'100%\'>';
	htmlText+='<tr><th  width=\'5%\'>SIGLA</th><th  width=\'85%\'>Descrizione</th><th  width=\'10%\'>seleziona</th></tr>';
	for (var i = 0 in allegati) {
			htmlText+='<tr><td align=\'center\'>' + allegati[i].code + 
			'</td><td align=\'rigth\'>' + allegati[i].desc + 
	  		'</td><td align=\'center\'><input type=\'checkbox\' id=\'allegato_'+i+'\' name=\'allegato_'+i+'\'></td></tr>';
		}
		htmlText+='</table>';
        $('#popup_lista_allegati').html(htmlText);
        $('#popup_lista_allegati').dialog('open');

}

function aggiungi_allegati_alla_pratica(){
	
	var popup_lista_alleg = document.getElementById("popup_lista_allegati").getElementsByTagName("input"); 
	
	for (var x = 0; x < popup_lista_alleg.length; x++){
		var campo_alleg = popup_lista_alleg[x];
		if (campo_alleg.checked == true){
			if(document.getElementById("tr_header_" + allegati[x].code) == null){
				
				var sigla_allegato = allegati[x].code;
				var desc_allegato = allegati[x].desc;
				
				var trfield = document.createElement('tr');
				trfield.setAttribute('id','tr_header_' + sigla_allegato);
				document.getElementById('tabella_crea_pratica').appendChild(trfield);
				
				var tdfield1 = document.createElement('td');
			    tdfield1.setAttribute('class', 'formLabel');
				tdfield1.innerHTML = '<p align="center"><b>ALLEGATO ' + sigla_allegato + '</b><br>' + 
									 desc_allegato + '</p>';
				document.getElementById('tr_header_' + sigla_allegato).appendChild(tdfield1);
				
				var tdfield2 = document.createElement('td');
				tdfield2.setAttribute('colspan', '3');
				var texthtml = "";
				texthtml += "<a href = \"javascript:openUploadAllegatoGins(document.getElementById('idAggiuntaPratica').value, '"+sigla_allegato+"', 'GINS_Pratica')\" id='allega'>Allega file</a>";
				texthtml += "<input type='hidden' readonly='readonly' id='header_"+ sigla_allegato +"' name='header_"+ sigla_allegato +"'/>";
				texthtml += "<label id='titolo_"+ sigla_allegato +"' name='titolo_"+ sigla_allegato +"'></label>";
				texthtml += "<input type='button' style='float: right;' value='rimuovi allegato' onclick='rimuovi_linea(\"tr_header_" + sigla_allegato + "\")'/>";
				tdfield2.innerHTML = texthtml;
				document.getElementById('tr_header_' + sigla_allegato).appendChild(tdfield2);
			}
			
		}
	}
	
}

function rimuovi_linea(idtr){
	var child = document.getElementById(idtr);
	child.parentNode.removeChild(child);
}


</script>