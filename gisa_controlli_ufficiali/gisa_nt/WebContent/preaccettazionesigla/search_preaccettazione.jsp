<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>


<script src="javascript/vendor/moment.min.js"></script>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			CERCA PREACCETTAZIONE
		</td>
	</tr>
</table>
<br><br>
<center>
	<button type="button" class="yellowBigButton" style="width: 450px;" 
	onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='ReportPreaccettazione.do?command=Report'">REPORT GENERALE PREACCETTAZIONE</button>
</center>

<form id="ricercaPreaccettazione" name="ricercaPreaccettazione" class="form-horizontal" role="form" method="post" 
			action="ReportPreaccettazione.do?command=Search" onsubmit="loadModalWindowCustom('Attendere Prego...');">
<br><br>

<table id="tabella_cerca_preaccettazione" class="details" cellspacing="0" border="0" width="100%" cellpadding="4">
<tbody>
	<tr>
		<th colspan="2">Dati ricerca &nbsp;&nbsp;&nbsp; <input type="button" value="pulisci" onclick="pulisci_form()"/></th>
	</tr>
	
	<tr>
		<td class="formLabel"><p>CODICE PREACCETTAZIONE</p></td>
		<td>
			<input placeholder="CODICE PREACCETTAZIONE" type="text" id="codice_preaccettazione" name="_b_codice_preaccettazione" 
				autocomplete="off"  pattern="[A-Za-z0-9]{13}"  size="40" style="text-align:center;" title="inserire qui il codice di preaccettazione di 13 cifre">
		</td>
	</tr>
	
	<tr>
		<td class="formLabel"><p>ENTE</p></td>
		<td>
			<input type="radio" id="ente" name="_b_ente" value="1" checked> REGIONE 
		</td>
	</tr>
	
	<tr>
		<td class="formLabel"><p>LABORATORIO</p></td>
		<td>
			<input type="radio" id="laboratorio2" name="_b_laboratorio" value="2" checked> SIGLA 
			<input type="radio" id="laboratorio1" name="_b_laboratorio" value="1"> ARPAC 
		</td>
	</tr>
	
	<tr>
		<td class="formLabel"><p>ASL</p></td>
		<td>
			<select id="asl" name="_b_asl" style="text-align:center;">
					<option value="-1"> -- TUTTI -- </option>
					<option value="201">AVELLINO</option>
					<option value="202">BENEVENTO</option>
					<option value="203">CASERTA</option>
					<option value="204">NAPOLI 1 CENTRO</option>
					<option value="205">NAPOLI 2 NORD</option>
					<option value="206">NAPOLI 3 SUD</option>
					<option value="207">SALERNO</option>
					<option value="16">ASL FUORI REGIONE</option>
				</select> 
		</td>
	</tr>
	
	<tr>
		<td class="formLabel"><p>Nome/Ditta/Ragione sociale</p></td>
		<td>
			<input placeholder="Nome/Ditta/Ragione sociale" type="text" id="osa" name="_b_osa" autocomplete="off"  size="40" style="text-align:center;"/>
		</td>
	</tr>
	
	<tr>
		<td class="formLabel"><p>PARTITA IVA/CODICE FISCALE IMPRESA</p></td>
		<td>
			<input placeholder="PARTITA IVA/CODICE FISCALE IMPRESA" type="text" id="partita_iva" name="_b_partita_iva" autocomplete="off"  size="40" style="text-align:center;"/>
		</td>
	</tr>
	
	<tr>
		<td class="formLabel"> <p>DATA GENERAZIONE</p> </td>
		<td>
			<input placeholder="DATA GENERAZIONE" type="text" id="data_generazione" name="_b_data_generazione" autocomplete="off" size="25" style="text-align:center;">
		</td>
	</tr>
	
	<tr>
		<td class="formLabel"> <p>UTENTE</p></td>
		<td>
			<input placeholder="UTENTE" type="text" id="utente" name="_b_utente" autocomplete="off"  size="40" style="text-align:center;"/>
		</td>
	</tr>
	
	
</tbody>
</table>
<br><br>
<center>

<button type="submit" class="yellowBigButton" style="width: 250px;">CERCA</button>

</center>
</form>
<br><br>


<script>

function pulisci_form(){
	document.getElementById('codice_preaccettazione').value = '';
	document.getElementById('osa').value = '';
	document.getElementById('asl').value = '-1';
	document.getElementById('data_generazione').value = '';
	document.getElementById('utente').value = '';
	document.getElementById('laboratorio1').checked = false;
	document.getElementById('laboratorio2').checked = false;

}

popup_date('data_generazione');

function popup_date(elemento_html_data){
	$( '#' + elemento_html_data ).datepicker({
		  dateFormat: 'dd-mm-yy',
		  changeMonth: true,
		  changeYear: true,
		  yearRange: '2019:+3',
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

</script>