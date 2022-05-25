<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="javascript/vendor/moment.min.js"></script>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="GestionePraticheAction.do?command=HomeGins">PRATICHE SUAP 2.0</a> >
			<a href="GestionePraticheAction.do?command=ListaPraticheApicoltura">PRATICHE APICOLTURA</a> > RICERCA PRATICA APICOLTURA
		</td>
	</tr>
</table>

<form id="ricercaPratica" name="ricercaPratica" class="form-horizontal" role="form" method="post" 
			action="GestionePraticheAction.do?command=SearchPraticheAmministrativeApi" onsubmit="return validateForm();">

<input type="hidden" id="aslUtente" value="<%=User.getSiteId()%>"/>
<br><br>

<table id="tabella_cerca_pratica" class="details" cellspacing="0" border="0" width="100%" cellpadding="4">
<tbody>
	<tr>
		<th colspan="2">Dati ricerca &nbsp;&nbsp;&nbsp; <input type="button" value="pulisci" onclick="pulisci_form()"/></th>
	</tr>
	
	<tr>
		<td class="formLabel"><p>NUMERO PRATICA</p></td>
		<td>
			<input placeholder="NUMERO PRATICA" type="text" id="numero_pratica" name="numero_pratica" autocomplete="off"  
			maxlength="40" pattern="[A-Za-z /\-, 0-9 .]{1,40}"  size="40" style="text-align:center;"
			title="numero pratica: massimo 40 caratteri consentiti (lettere non accentate, numeri, / - , . )"
			oninvalid="alert('Attenzione, caratteri non validi in numero pratica: massimo 40 caratteri consentiti (lettere non accentate, numeri, / - , . )');">
		</td>
	</tr>
	
	<tr>
		<td class="formLabel"><p>COMUNE</p></td>
		<td>
			<select id="comune_richiedente" name="comune_richiedente" style="text-align:center;"></select>
		</td>
	</tr>
	<tr>
		<td class="formLabel"> <p>DATA PEC / DATA SCIA</p> </td>
		<td>
			<input placeholder="DATA PEC / DATA SCIA" type="text" id="data_richiesta" name="data_richiesta" autocomplete="off" onkeydown="return false" size="15" style="text-align:center;">
		</td>
	</tr>
	
	<tr style="display: none">
		<td class="formLabel"><p>TIPO PRATICA</p></td>
		<td>
			<select id="idTipologiaPratica" name="idTipologiaPratica" style="text-align:center;">
				<option value="1">AVVIO DELL'ATTIVITA'</option>
			</select>
		</td>
	</tr>
	
</tbody>
</table>
<br><br>
<center>

<button type="submit" class="yellowBigButton" style="width: 250px;">CERCA</button>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<button type="button" class="yellowBigButton" style="width: 250px;" 
	onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestionePraticheAction.do?command=ListaPraticheApicoltura'">ANNULLA</button>

</center>
</form>
<br><br>

<script>

function validateForm(){
	
	if (!document.getElementById("numeroPratica").checkValidity()){
		alert('Attenzione, caratteri non validi in numero pratica: massimo 40 caratteri consentiti (lettere non accentate, numeri, / - , . )');
		return false;
	}
	
	loadModalWindowCustom('Attendere Prego...');
	return true;
	
}

function pulisci_form(){
	document.getElementById('numero_pratica').value = '';
	document.getElementById('comune_richiedente').value = '';
	document.getElementById('data_richiesta').value = '';
	//document.getElementById('idTipologiaPratica').value = '';
}

popup_date('data_richiesta');

function popup_date(elemento_html_data){
	$( '#' + elemento_html_data ).datepicker({
		  dateFormat: 'dd-mm-yy',
		  changeMonth: true,
		  changeYear: true,
		  yearRange: '-100:+3',
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


popola_select_popup('GetComuneByAsl.do?command=Search&idAsl='+document.getElementById('aslUtente').value , 'comune_richiedente');


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
			$('#'+id_elemento).append('<option value=""> -- COMUNE -- </option>');
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


</script>