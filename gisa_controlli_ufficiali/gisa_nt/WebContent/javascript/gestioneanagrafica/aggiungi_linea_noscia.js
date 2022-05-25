/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
$(function() {
	
	 $('#popuplineeattivita').dialog({
		title : 'AGGIUNGI LINEA DI ATTIVITA',
         autoOpen: false,
         resizable: false,
         closeOnEscape: false,
         width:1200,
         height:550,
         draggable: false,
         modal: true,
	     buttons: {
			 'AGGIUNGI': function() {   
				var data_inizio_attivita = document.getElementById('data_inizio').value.toString();
				var data_fine_attivita = document.getElementById('data_fine').value.toString();
				var tipo_carat_id = document.getElementById('tipo_carattere').value.toString();
				var tipo_carat_testo;
				if (document.getElementById('tipo_carattere').value == '1'){
					tipo_carat_testo = 'PERMANENTE';
				} else {
					tipo_carat_testo = 'TEMPORANEA';
				}
				var id_linea_attivita_ml = document.getElementById('linea_attivita').value;
				var cun_popup = document.getElementById('cun_popup').value;
				var ok_insert_popup = verifica_inserimento_linea(id_linea_attivita_ml, data_inizio_attivita, data_fine_attivita, tipo_carat_id, cun_popup);
				if(ok_insert_popup){
					aggiungi_riga(tipo_carat_testo, data_inizio_attivita, data_fine_attivita, id_linea_attivita_ml, tipo_carat_id,cun_popup);
	                loadModalWindowUnlock();
					$( this ).dialog('close');
				}
                
			},
			'ANNULLA': function() {
                                loadModalWindowUnlock();
				$( this ).dialog('close');
			}
      }
 });
	 
});

function aggiungi_linea(){
	var htmlText='<br>';
	htmlText += '<fieldset>'+
					'<legend><b>INDICARE LINEA DI ATTIVITA</b></legend>'+
					'<table style="width:100%;">'+
						'<tr>'+
							'<td colspan="2" width="100%" align="left">'+
								'<div style="width:100%;"></div>'+
							'</td>'+
						'</tr>';
	
	if(document.getElementById('tipo_linee_attivita').value == '1'){
		htmlText += '<tr id="tr_tipoattivita_popup">'+
						'<td style="width:10%" align="left">'+
							'<p>TIPO ATTIVITA</p>'+
						'</td>'+
						'<td style="width:80%" align="left">'+
							'<input type="hidden" id="tipoattivita" value="1"/>CON SEDE FISSA' +
						'</td>'+
					'</tr>';
	} else if (document.getElementById('tipo_linee_attivita').value == '2') {
		htmlText += '<tr id="tr_tipoattivita_popup">'+
						'<td style="width:10%" align="left">'+
							'<p>TIPO ATTIVITA</p>'+
						'</td>'+
						'<td style="width:80%" align="left">'+
							'<input type="hidden" id="tipoattivita" value="2"/>SENZA SEDE FISSA' +
						'</td>'+
					'</tr>';
	} else {
		htmlText += '<tr id="tr_tipoattivita_popup">'+
						'<td style="width:10%" align="left">'+
							'<p>TIPO ATTIVITA</p>'+
						'</td>'+
						'<td style="width:80%" align="left">'+
							'<select id="tipoattivita" >' +
								'<option value="1" selected="selected"> CON SEDE FISSA </option>' +
								'<option value="2"> SENZA SEDE FISSA </option>' +
							'</select>'+
						'</td>'+
					'</tr>';
	}
	
						
	htmlText += '<tr id="tr_macroarea_popup">'+
							'<td style="width:10%" align="left">'+
								'<p>MACROAREA</p>'+
							'</td>'+
							'<td style="width:80%" align="left">'+
								'<select id="macroarea"><option value="" selected="selected">SELEZIONA MACROAREA</option></select>'+
							'</td>'+
						'</tr>'+
						
						'<tr id="tr_aggregazione_popup">'+
							'<td style="width:10%" align="left">'+
								'<p>AGGREGAZIONE</p>'+
								'</td>'+
								'<td style="width:80%" align="left">'+
									'<select id="aggregazione"><option value="" selected="selected">SELEZIONA AGGREGAZIONE</option></select>'+
								'</td>'+
						'</tr>'+
						
						'<tr id="tr_linea_attivita_popup">'+
							'<td style="width:10%" align="left">'+
								'<p>LINEA ATTIVITA</p>'+
							'</td>'+
							'<td style="width:80%" align="left">'+
								'<select id="linea_attivita"><option value="" selected="selected">SELEZIONA LINEA ATTIVITA</option></select>'+
							'</td>'+
						'</tr>'+

						'<tr>'+
							'<td colspan="2" align="left">'+
								'<div></div>'+
							'</td>'+
						'</tr>'+
						
						'<tr id="tr_tipo_carattere_popup">'+
							'<td style="width:10%" align="left">'+
								'<p>TIPO CARATTERE</p>'+
							'</td>'+
							'<td style="width:80%" align="left">'+
								'<select id="tipo_carattere">'+
									'<option value="1">PERMANENTE</option>' +
									'<option value="2">TEMPORANEA</option>' +
								'</select>'+
							'</td>'+
						'</tr>'+
						
						'<tr id="tr_data_inizio_popup">'+
							'<td style="width:15%" align="left">' +
								'<p>DATA INIZIO ATTIVITA</p>' +
							'</td>'+
							'<td style="width:80%" align="left">' +
								'<input placeholder="DATA INIZIO ATTIVITA" type="text" id="data_inizio" autocomplete="off" onkeydown="return false">' +
							 '</td>' +
						 '</tr>' +
						 
						 '<tr id="tr_data_fine_popup">'+
							 '<td style="width:15%" align="left">' +
								'<p>DATA FINE ATTIVITA</p>' +
							'</td>'+
							'<td style="width:80%" align="left">' +
								'<input placeholder="DATA FINE ATTIVITA" type="text" id="data_fine" autocomplete="off" onkeydown="return false">' +
							'</td>'+
						'</tr>'+
						
					'</table>'+
				'</fieldset>'+
				'<br><fieldset id="field_cun">' +
				'<legend><b>DATI AGGIUNTIVI LINEA</b></legend>'+
					'<table style="width:100%;">'+
						'<tr>'+
							'<td colspan="2" align="left">'+
								'<div></div>'+
							'</td>'+
						'</tr>'+
						'<tr id="tr_data_cun">'+
							'<td style="width:15%" align="left">' +
								'<p>CUN</p>' +
							'</td>'+
							'<td style="width:80%" align="left">' +
								'<input placeholder="CUN" type="text" id="cun_popup" maxlength="20" autocomplete="off">' +
								'<br> <font color="red">' +
								'Non obbligatorio per tutte le linee, per le linee che lo prevedono obbligatorio viene mostrato un opportuno messaggio nel caso venga omesso.' +
								'</font>' +
							 '</td>' +
						 '</tr>' +
					
					'</table>'+
				'</fieldset>';
	
	$('#popuplineeattivita').html(htmlText);
	$('#popuplineeattivita').dialog('open');
	$('#tr_aggregazione_popup').css({"visibility":"hidden"});
	$('#tr_linea_attivita_popup').css({"visibility":"hidden"});
	$('#tr_data_fine_popup').css({"visibility":"hidden"});
	popup_date('data_inizio','0');
	popup_date('data_fine','+3Y');
	$('#tipo_carattere').change(function(){
		if(document.getElementById('tipo_carattere').value == '2'){
			$('#data_fine').val(null);
			$('#tr_data_fine_popup').css({"visibility":"visible"});
		} else {
			$('#data_fine').val(null);
			$('#tr_data_fine_popup').css({"visibility":"hidden"});
		}
	});
	
	popola_select_popup('GestioneAnagraficaGetDatiLinea.do?command=Search&tiporichiesta=macroareanoscia&lineanoscia=' 
						+ document.getElementById('id_lp_0').value , 'macroarea'); 
	
	$('#tipoattivita').change(function(){
		if(document.getElementById('tipoattivita').value == '1'){ //sede fissa
			$('#macroarea').children().remove();
			$('#macroarea').append('<option value="" selected="selected">SELEZIONA MACROAREA</option>');
			$('#aggregazione').children().remove();
			$('#aggregazione').append('<option value="" selected="selected">SELEZIONA AGGREGAZIONE</option>');
			$('#tr_aggregazione_popup').css({"visibility":"hidden"});
			$('#linea_attivita').children().remove();
			$('#linea_attivita').append('<option value="" selected="selected">SELEZIONA LINEA ATTIVITA</option>');
			$('#tr_linea_attivita_popup').css({"visibility":"hidden"});
			popola_select_popup('GestioneAnagraficaGetDatiLinea.do?command=Search&tiporichiesta=macroareanoscia&lineanoscia=' 
						+ document.getElementById('id_lp_0').value, 'macroarea');
		}else{ //senza sede fissa
			$('#macroarea').children().remove();
			$('#macroarea').append('<option value="" selected="selected">SELEZIONA MACROAREA</option>');
			$('#aggregazione').children().remove();
			$('#aggregazione').append('<option value="" selected="selected">SELEZIONA AGGREGAZIONE</option>');
			$('#tr_aggregazione_popup').css({"visibility":"hidden"});
			$('#linea_attivita').children().remove();
			$('#linea_attivita').append('<option value="" selected="selected">SELEZIONA LINEA ATTIVITA</option>');
			$('#tr_linea_attivita_popup').css({"visibility":"hidden"});
			popola_select_popup('GestioneAnagraficaGetDatiLinea.do?command=Search&tiporichiesta=macroareanoscia&lineanoscia=' 
						+ document.getElementById('id_lp_0').value, 'macroarea');
		}		
	});
	
	$('#macroarea').change(function(){
		if(document.getElementById('macroarea').value != ''){
			$('#aggregazione').children().remove();
			$('#aggregazione').append('<option value="" selected="selected">SELEZIONA AGGREGAZIONE</option>');
			$('#tr_aggregazione_popup').css({"visibility":"visible"});
			$('#linea_attivita').children().remove();
			$('#linea_attivita').append('<option value="" selected="selected">SELEZIONA LINEA ATTIVITA</option>');
			$('#tr_linea_attivita_popup').css({"visibility":"hidden"});
			popola_select_popup('GestioneAnagraficaGetDatiLinea.do?command=Search&tiporichiesta=aggregazionenoscia&idmacroarea='+
					document.getElementById('macroarea').value + '&lineanoscia=' + document.getElementById('id_lp_0').value, 'aggregazione');
		}else{
			$('#aggregazione').children().remove();
			$('#aggregazione').append('<option value="" selected="selected">SELEZIONA AGGREGAZIONE</option>');
			$('#tr_aggregazione_popup').css({"visibility":"hidden"});
			$('#linea_attivita').children().remove();
			$('#linea_attivita').append('<option value="" selected="selected">SELEZIONA LINEA ATTIVITA</option>');
			$('#tr_linea_attivita_popup').css({"visibility":"hidden"});
		}
	});
	
	$('#aggregazione').change(function(){
		if(document.getElementById('aggregazione').value != ''){
			$('#linea_attivita').children().remove();
			$('#linea_attivita').append('<option value="" selected="selected">SELEZIONA LINEA ATTIVITA</option>');
			$('#tr_linea_attivita_popup').css({"visibility":"visible"});
			popola_select_popup('GestioneAnagraficaGetDatiLinea.do?command=Search&tiporichiesta=lineaattivitanoscia&idaggregazione='+
					document.getElementById('aggregazione').value + '&lineanoscia=' + document.getElementById('id_lp_0').value, 'linea_attivita');
		}else{
			$('#linea_attivita').children().remove();
			$('#linea_attivita').append('<option value="" selected="selected">SELEZIONA LINEA ATTIVITA</option>');
			$('#tr_linea_attivita_popup').css({"visibility":"hidden"});
			
		}
	});
}


function popola_select_popup(urlservice, id_elemento){
	
	loadModalWindow();
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
		  	for (var i = 0; i < obj.length; i++) {
		  		$('#'+id_elemento).append('<option value="'+obj[i].code+'">'+obj[i].description+'</option>');
		  	}
		  	loadModalWindowUnlock();
        },
        fail: function(xhr, textStatus, errorThrown){
        	alert('request failed');
       	}
          
  	});
  	
}

function popup_date(elemento_html_data,max_data){
	$( '#' + elemento_html_data ).datepicker({
		  dateFormat: 'dd-mm-yy',
		  changeMonth: true,
		  changeYear: true,
		  yearRange: '-100:+3',
		  maxDate: max_data,
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
function verifica_inserimento_linea(id_linea_attivita_ml, data_inizio_attivita, data_fine_attivita, tipo_carattere_id, cun_popup_inserito){
	var esito = false;
	var messagioerrore = '';
	var esitocun = false;
	
	if(tipo_carattere_id == '1'){
		if(id_linea_attivita_ml != '' && data_inizio_attivita != ''){
			esito = true;
		}else{
			messagioerrore = 'selezionare i campi obbligatori';	
			esito = false;
		}
	}else if(tipo_carattere_id == '2'){
		if(id_linea_attivita_ml != '' && data_inizio_attivita != '' && data_fine_attivita != ''){
			
			var arr1 = data_inizio_attivita.split("-");
			var arr2 = data_fine_attivita.split("-");
			
			var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
			var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
			
			var r1 = d1.getTime();
			var r2 = d2.getTime();
			
			var diff = r2 - r1;

			if(diff < 0){
				messagioerrore = 'la data inizio attivita non puo essere successiva alla data fine attivita';
				esito = false;
			} else {
				esito = true;
			}
			
		}else{
			messagioerrore = 'selezionare i campi obbligatori';
			esito = false;
		}
	}
	
	
	var url = "CunRichiesto.do?command=Search&codiceLinea=" + id_linea_attivita_ml;
	
	var request = $.ajax({
		url : url,
		async: false,
		dataType : "json"
	});

	request.done(function(result) {
		
		if(result.cun_obbligatorio == '1'){
			if(trim(cun_popup_inserito) != ''){
				esitocun = true;
			}else{
				messagioerrore = 'Attenzione! La linea selezionata richiede obbligatoriamente il cun';
				esitocun = false;
			}
		}else{
			esitocun = true;
		}
	});
	request.fail(function(jqXHR, textStatus) {
		console.log('Error');
		
	});

	if (esito && esitocun){
		if(verificaEsistenzaCun(document.getElementById('cun_popup'))){
			return true;
		}else{
			return false;
		}
		
	}else{
		alert(messagioerrore);
		return false;
	}
}

function aggiungi_riga(tipo_carattere, data_inizio_attivita, data_fine_attivita, id_linea_attivita_popup, tipo_carattere_id, cun){
	var urlservice = "GestioneAnagraficaGetDatiLinea.do?command=Search&tiporichiesta=datilineaattivita&idlineaattivita=" + id_linea_attivita_popup;
	loadModalWindow();
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
		  	for (var i = 0; i < obj.length; i++) {
		  		aggiungi_riga_gui(
		  				obj[i].macroarea, 
		  				obj[i].aggregazione, 
		  				obj[i].attivita, 
		  				tipo_carattere, 
		  				data_inizio_attivita, 
		  				data_fine_attivita, 
		  				id_linea_attivita_popup, 
		  				tipo_carattere_id,
		  				cun)
		  	}
		  	loadModalWindowUnlock();
        },
        fail: function(xhr, textStatus, errorThrown){
        	alert('request failed');
       	}
          
  	});
}

function aggiungi_riga_gui(macroarea, aggregazione, attivita, tipo_carattere, data_inizio_attivita, data_fine_attivita, id_linea_attivita_popup, tipo_carattere_id, cun){
		
	//aggiungo il componente tr
	var numero_linee = document.getElementById('numero_linee').value;					
	var n_linea_attivita = 'lineaattivita_' + numero_linee;
	var td_button_elimina = 'td_button_elimina_' + + numero_linee;
	
	var trfield = document.createElement('tr');
		trfield.setAttribute('id',n_linea_attivita);
		document.getElementById('tabella_linee').appendChild(trfield);
	//aggiungo i componenti td 
	var tdfield1 = document.createElement('td');
	    tdfield1.setAttribute('class', 'formLabel');
	    tdfield1.setAttribute('id','td1_'+n_linea_attivita);
		tdfield1.innerHTML = 'linea';
		document.getElementById(n_linea_attivita).appendChild(tdfield1);
	//aggiungo i componenti td	
	var tdfield2 = document.createElement('td');
	var cuntext = '';
	if (cun != ''){
		cuntext = '<br><b>cun</b>: &emsp;'+cun;
	} 
	
	var tipo_attivita_label = '';
	if(document.getElementById('tipoattivita').value == '1'){
		tipo_attivita_label = 'con sede fissa';
	} else {
		tipo_attivita_label = 'senza sede fissa';
	}
	
	if(tipo_carattere_id == '2')
		{
			tdfield2.innerHTML = '<span>'+macroarea+
								'<br>->'+aggregazione+
								'<br>->'+attivita+
								'<br><br><b>tipo attivita</b>: &emsp;'+tipo_attivita_label+
								'<br><b>tipo carattere</b>: &emsp;'+tipo_carattere+
								'<br><b>data inizio attivita</b>: &emsp;'+data_inizio_attivita+
								'<br><b>data fine attivita</b>: &emsp;'+data_fine_attivita+
								cuntext + '<br></span>';
		}else{
			tdfield2.innerHTML = '<span>'+macroarea+
								'<br>->'+aggregazione+
								'<br>->'+attivita+
								'<br><br><b>tipo attivita</b>: &emsp;'+tipo_attivita_label+
								'<br><b>tipo carattere</b>: &emsp;'+tipo_carattere+
								'<br><b>data inizio attivita</b>: &emsp;'+data_inizio_attivita+
								cuntext + '<br></span>';
		}
		
		document.getElementById(n_linea_attivita).appendChild(tdfield2);

    var onclick_event = 'rimuovi_linea(\''+ n_linea_attivita +'\')';	
	var buttonfield = document.createElement('input');
	    buttonfield.setAttribute('type','button');
	    buttonfield.setAttribute('value','elimina linea');
		buttonfield.setAttribute('onclick', onclick_event);
		document.getElementById('td1_'+n_linea_attivita).appendChild(buttonfield);
	//aggiungo i componenti hidden 
	var hiddenfield1 = document.createElement('input');
		hiddenfield1.setAttribute('type','hidden');
		hiddenfield1.setAttribute('id','lineaattivita_' + numero_linee + '_id_linea_attivita_ml');
		hiddenfield1.setAttribute('name', '_b_lineaattivita_' + numero_linee + '_id_linea_attivita_ml');
		hiddenfield1.setAttribute('value', id_linea_attivita_popup);
		document.getElementById(n_linea_attivita).appendChild(hiddenfield1);
		
	var hiddenfield2 = document.createElement('input');
		hiddenfield2.setAttribute('type','hidden');
		hiddenfield2.setAttribute('id','lineaattivita_' + numero_linee + '_data_inizio_attivita');
		hiddenfield2.setAttribute('name', '_b_lineaattivita_' + numero_linee + '_data_inizio_attivita');
		hiddenfield2.setAttribute('value',data_inizio_attivita);
		document.getElementById(n_linea_attivita).appendChild(hiddenfield2);
	
	var hiddenfield3 = document.createElement('input');
		hiddenfield3.setAttribute('type','hidden');
		hiddenfield3.setAttribute('id','lineaattivita_' + numero_linee + '_data_fine_attivita');
		hiddenfield3.setAttribute('name', '_b_lineaattivita_' + numero_linee + '_data_fine_attivita');
		hiddenfield3.setAttribute('value',data_fine_attivita);
		document.getElementById(n_linea_attivita).appendChild(hiddenfield3);
	
	var hiddenfield4 = document.createElement('input');
		hiddenfield4.setAttribute('type','hidden');
		hiddenfield4.setAttribute('id','lineaattivita_' + numero_linee + '_tipo_carattere_attivita');
		hiddenfield4.setAttribute('name', '_b_lineaattivita_' + numero_linee + '_tipo_carattere_attivita');
		hiddenfield4.setAttribute('value',tipo_carattere_id);
		document.getElementById(n_linea_attivita).appendChild(hiddenfield4);
	
	var hiddenfield5 = document.createElement('input');
		hiddenfield5.setAttribute('type','hidden');
		hiddenfield5.setAttribute('id','lineaattivita_' + numero_linee + '_num_riconoscimento');
		hiddenfield5.setAttribute('name', '_b_lineaattivita_' + numero_linee + '_num_riconoscimento');
		hiddenfield5.setAttribute('value',cun);
		document.getElementById(n_linea_attivita).appendChild(hiddenfield5);
		
	var hiddenfield6 = document.createElement('input');
		hiddenfield6.setAttribute('type','hidden');
		hiddenfield6.setAttribute('id','lineaattivita_' + numero_linee + '_tipo_attivita');
		hiddenfield6.setAttribute('name', '_b_lineaattivita_' + numero_linee + '_tipo_attivita');
		hiddenfield6.setAttribute('value',document.getElementById('tipoattivita').value);
		document.getElementById(n_linea_attivita).appendChild(hiddenfield6);
		
								
	numero_linee = parseInt(numero_linee) + 1;
	var linee_effettive = document.getElementById('numero_linee_effettivo').value;
	document.getElementById('numero_linee_effettivo').value = parseInt(linee_effettive) + 1;
	document.getElementById('numero_linee').value = numero_linee;
}
				

function rimuovi_linea(idtr){
	var child = document.getElementById(idtr);
	child.parentNode.removeChild(child);
	var linee_effettive = document.getElementById('numero_linee_effettivo').value;
	document.getElementById('numero_linee_effettivo').value = parseInt(linee_effettive) - 1;
}


