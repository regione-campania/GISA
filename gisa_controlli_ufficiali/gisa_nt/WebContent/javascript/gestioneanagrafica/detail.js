/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function setValore(key, value){
	var campoOld = $('#'+key);
	
	if (campoOld==null)
		return false;
	
	if (campoOld.attr('type') != 'hidden'){
			campoOld.replaceWith(value);
		}
		
}

function setExtra(key, value){
	
	var newRow = $("<tr>");
    newRow.append(value);
    newRow.insertAfter($('#trExtra'));
}

function setInfoSpec(key, value){
	
	var newRow = $("<tr>");
    newRow.append(value);
    newRow.insertAfter($('#trInformazioneSpecifica'));
}

function setLinea(key, value){
	
	var newRow = $("<tr>");
    newRow.append(value);
    newRow.insertAfter($('#trLinee'));
}

function VisualizzaDettaglio(arrAnagrafica, arrExtra, arrLinee, arrEstesi, arrInfoSpeci){
	
	document.getElementById("dettaglio").style = "display: block inline-block";
	var value;
	for (var key in arrAnagrafica) {
	    value = arrAnagrafica[key];
	   setValore(key, value);
	}
	for (var key in arrEstesi) {
	    value = arrEstesi[key];
	   setValore(key, value);
	}
	for (var key in arrExtra) {
	    value = arrExtra[key];
    	setExtra(key, value);
	}
	
	for (var key in arrLinee) {
	    value = arrLinee[key];
    	setLinea(key, value);
	}
	
	for (var key in arrInfoSpeci){
		value = arrInfoSpeci[key];
    	setInfoSpec(key, value);
    	if(document.getElementById('tabella_dati_aggiuntivi')){
    		document.getElementById('tabella_dati_aggiuntivi').style='display: none';
    	}
	}
	
	$("div#dettaglio :input").each(function(){
		 var input = $(this); // This is the jquery object of the input, do what you will
		 if (input.attr('type') == 'button'){
			 input.hide();
		 }
		 else {
			input.prop('disabled', true);
            input.hide();
			}
		});
	
	
	$("#dati_impresa_id").hide();
	
	if(arrAnagrafica["nazione_nascita_rapp_legale"] != '' && arrAnagrafica["nazione_nascita_rapp_legale"] != null){
		if(arrAnagrafica["nazione_nascita_rapp_legale"].toUpperCase() != 'ITALIA')
		{
			if(document.getElementById('tr_comune_nascita_rapp_legale') 
				&& document.getElementById('tr_comune_nascita_estero_rapp_legale') ){
				
				document.getElementById('tr_comune_nascita_rapp_legale').style='display: none';
				document.getElementById('tr_comune_nascita_estero_rapp_legale').style='display: ';
			}
			
		}
		else{
			if(document.getElementById('tr_comune_nascita_rapp_legale')){
				document.getElementById('tr_comune_nascita_rapp_legale').innerHTML = '<td class="formLabel">COMUNE NASCITA</td>'+
				'<td>'+arrAnagrafica["comune_nascita_rapp_legaleLabel"]+'</td>';
			}			
		}
	}
	
	if(arrAnagrafica["nazione_residenza_rapp_legale"] != '' && arrAnagrafica["nazione_residenza_rapp_legale"] != null){
		if(arrAnagrafica["nazione_residenza_rapp_legale"].toUpperCase() != 'ITALIA')
		{
			if(document.getElementById('indirizzo_residenza_rapp_legale') 
					&& document.getElementById('tr_comune_residenza_estero_rapp_legale')) {
				
				document.getElementById('indirizzo_residenza_rapp_legale').style='display: none';
				document.getElementById('tr_comune_residenza_estero_rapp_legale').style='display: ';
			}			
		}
	}
	
	if(arrAnagrafica["nazione_sede_legale"] != '' && arrAnagrafica["nazione_sede_legale"] != null){
		if(arrAnagrafica["nazione_sede_legale"].toUpperCase() != 'ITALIA')
		{
			if(document.getElementById('indirizzo_sede_legale') 
					&& document.getElementById('tr_comune_estero_sede_legale')){
				
				document.getElementById('indirizzo_sede_legale').style='display: none';
				document.getElementById('tr_comune_estero_sede_legale').style='display: ';
			}
		}
	}
	
	if(document.getElementById("tipo_attivita_stab_template").value == '1' && document.getElementById("codice_linea_template").value == "SCIA-FISSO"){
		if(arrAnagrafica["tipo_impresa"].toUpperCase() == 'IMPRESA INDIVIDUALE' || arrAnagrafica["tipo_impresa"].trim().toUpperCase() == "ATTIVITA' SVOLTA DA UN PRIVATO SENZA PARTITA IVA"){
			document.getElementById('tr_sede_legale_id').style = 'display: none';
			document.getElementById('tr_nazione_sede_legale').style = 'display: none';
			document.getElementById('indirizzo_sede_legale').style = 'display: none';
			document.getElementById('tr_comune_estero_sede_legale').style = 'display: none';	
		}
	}
	
	if(document.getElementById("tipo_attivita_stab_template").value == '2'  && document.getElementById("codice_linea_template").value == "SCIA-MOBILE"){
		
		if(arrAnagrafica["tipo_impresa"].trim().toUpperCase() == 'IMPRESA INDIVIDUALE' || arrAnagrafica["tipo_impresa"].trim().toUpperCase() == "ATTIVITA' SVOLTA DA UN PRIVATO SENZA PARTITA IVA"){
			document.getElementById('tr_nazione_residenza_rapp_legale').style = 'display: ';
			document.getElementById('tr_comune_residenza_estero_rapp_legale').style = 'display: none';
			document.getElementById('indirizzo_sede_legale').style = 'display: none'; 
			document.getElementById('indirizzo_residenza_rapp_legale').style = 'display: ';
			document.getElementById('tr_sede_legale_id').style = 'display: none';	
			var ind_res = $("#indirizzo_residenza_rapp_legale").closest('tr');
			var tr_telefono_rapp_leg = $("#tr_telefono_rapp_legale").closest('tr');
			ind_res.insertAfter(tr_telefono_rapp_leg);
			var naz_res = $("#tr_nazione_residenza_rapp_legale").closest('tr');
			naz_res.insertAfter(tr_telefono_rapp_leg);
		}
	}
	
}