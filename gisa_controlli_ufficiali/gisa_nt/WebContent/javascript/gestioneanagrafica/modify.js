/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

function setValore(key, value){
	var campoOld = $('#'+key);
	
	if (campoOld==null)
		return false;
	
	campoOld.replaceWith(value);
}

function VisualizzaValoriModifica(arrAnagrafica){
	loadModalWindow();
	var value;
	for (var key in arrAnagrafica) {
		{      
			var elemento = arrAnagrafica[key];
			var keyfield =  document.getElementById(key);
			if (typeof(keyfield) != 'undefined' && keyfield != null)
			{			
				keyfield.value = elemento;
				if (keyfield.id == 'numero_registrazione_stabilimento' || keyfield.id == 'asl_stabilimento'){
					if(document.getElementById('n_reg_import_802') && keyfield.id == 'numero_registrazione_stabilimento'){
						document.getElementById('n_reg_import_802').value = arrAnagrafica[key];
					}
					value = arrAnagrafica[key];
					   setValore(key, value);
				}
				
				if (keyfield.id == 'partita_iva_impresa'){
					if (arrAnagrafica[key] == null || arrAnagrafica[key] == "")
						{
							keyfield.readOnly = false;
						} else {
							keyfield.readOnly = true;
						}
					
				}
				
				if(keyfield.type == 'select-multiple'){
					var valori_select = elemento.split(",");
					for(var i = 0; i < keyfield.options.length; i++){
						if (valori_select.indexOf(keyfield.options[i].value) >= 0){
							keyfield.options[i].setAttribute('selected', 'selected');
						}
					}
					
				}
			}
		}
	}
	
	if(typeof(document.getElementById('nazione_nascita_rapp_legale')) != 'undefined' && document.getElementById('nazione_nascita_rapp_legale') != null){
		if(document.getElementById('nazione_nascita_rapp_legale').value != 106)
		{
			document.getElementById('tr_comune_nascita_rapp_legale').style='display: none';
			document.getElementById('tr_comune_nascita_estero_rapp_legale').style='display: block inline-block';
			document.getElementById('comune_nascita_estero_rapp_legale').style='display: block inline-block';
	        document.getElementById('comune_nascita_estero_rapp_legale').disabled = false;
	        document.getElementById('comune_nascita_rapp_legale').disabled = true;
			document.getElementById('calcola_cf_rapp_legale').style='display: none';
			document.getElementById('cf_rapp_legale').removeAttribute('required');
		}
	}
	
	
	if(typeof(document.getElementById('nazione_residenza_rapp_legale')) != 'undefined' && document.getElementById('nazione_residenza_rapp_legale') != null){
		if(document.getElementById('nazione_residenza_rapp_legale').value != '106'){
			document.getElementById('indirizzo_residenza_rapp_legale').style='display: none';
			document.getElementById('tr_comune_residenza_estero_rapp_legale').style='display: block inline-block';
			document.getElementById('comune_residenza_estero_rapp_legale').style='display: block inline-block';
	        document.getElementById('comune_residenza_estero_rapp_legale').value = document.getElementById('comune_residenza_rapp_legale').value;
	        document.getElementById('comune_residenza_rapp_legale').value = "";
	        document.getElementById('cap_residenza_rapp_legale').removeAttribute('required');
		} else if(arrAnagrafica['provincia_residenza_rapp_legale'] != '' || 
				  arrAnagrafica['comune_residenza_rapp_legale'] != '' ||
				  arrAnagrafica['toponimo_residenza_rapp_legale'] != '' ||
				  arrAnagrafica['via_residenza_rapp_legale'] != '' ||
				  arrAnagrafica['cap_residenza_rapp_legale'] != ''){
			
			document.getElementById('civico_residenza_rapp_legale').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('cap_residenza_rapp_legale').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('via_residenza_rapp_legale').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('toponimo_residenza_rapp_legale').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('comune_residenza_rapp_legale').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('provincia_residenza_rapp_legale').style = 'display: ; background-color: #dddddd; text-align: center';
			
			$("._placeholder_provincia_residenza_rapp_legale").remove();
			$('<span class="_placeholder_provincia_residenza_rapp_legale">provincia </span>').insertBefore('#provincia_residenza_rapp_legale');
			$("._placeholder_comune_residenza_rapp_legale").remove();
			$('<span class="_placeholder_comune_residenza_rapp_legale">comune </span>').insertBefore('#comune_residenza_rapp_legale');
			$("._placeholder_cap_residenza_rapp_legale").remove();
			$('<span class="_placeholder_cap_residenza_rapp_legale">cap </span>').insertBefore('#cap_residenza_rapp_legale');
			$("._placeholder_toponimo_residenza_rapp_legale").remove();
			$('<span class="_placeholder_toponimo_residenza_rapp_legale">indirizzo </span>').insertBefore('#toponimo_residenza_rapp_legale');
			
		}
	}
	
	if(typeof(document.getElementById('nazione_sede_legale')) != 'undefined' && document.getElementById('nazione_sede_legale') != null){
		if(document.getElementById('nazione_sede_legale').value != '106'){
			document.getElementById('indirizzo_sede_legale').style='display: none';
			document.getElementById('tr_comune_estero_sede_legale').style='display: block inline-block';
			document.getElementById('comune_estero_sede_legale').style='display: block inline-block';
	        document.getElementById('cap_sede_legale').removeAttribute('required');
	        document.getElementById('comune_estero_sede_legale').value = document.getElementById('comune_sede_legale').value;
	        document.getElementById('comune_sede_legale').value = "";
	        
	        if(arrAnagrafica['tipo_impresa'] == '11' || arrAnagrafica['tipo_impresa'] == '17'){
				document.getElementById('tr_sede_legale_id').style = 'display: none';
				document.getElementById('indirizzo_sede_legale').style = 'display: none';
				document.getElementById('tr_nazione_sede_legale').style = 'display: none';
				document.getElementById('tr_comune_estero_sede_legale').style = 'display: none';
				document.getElementById('comune_estero_sede_legale').value = "";
				document.getElementById('cap_sede_legale').removeAttribute('required');
			}
		} else if(arrAnagrafica['provincia_sede_legale'] != '' || 
				  arrAnagrafica['comune_sede_legale'] != '' ||
				  arrAnagrafica['toponimo_sede_legale'] != '' ||
				  arrAnagrafica['via_sede_legale'] != '' ||
				  arrAnagrafica['cap_sede_legale'] != ''){
			
			document.getElementById('civico_sede_legale').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('cap_sede_legale').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('via_sede_legale').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('toponimo_sede_legale').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('comune_sede_legale').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('provincia_sede_legale').style = 'display: ; background-color: #dddddd; text-align: center';
			
			$("._placeholder_provincia_sede_legale").remove();
			$('<span class="_placeholder_provincia_sede_legale">provincia </span>').insertBefore('#provincia_sede_legale');
			$("._placeholder_comune_sede_legale").remove();
			$('<span class="_placeholder_comune_sede_legale">comune </span>').insertBefore('#comune_sede_legale');
			$("._placeholder_cap_sede_legale").remove();
			$('<span class="_placeholder_cap_sede_legale">cap </span>').insertBefore('#cap_sede_legale');
			$("._placeholder_toponimo_sede_legale").remove();
			$('<span class="_placeholder_toponimo_sede_legale">indirizzo </span>').insertBefore('#toponimo_sede_legale');
			
		}
	}
	
	if(document.getElementById('cap_stabilimento')){
		if(arrAnagrafica['provincia_stabilimento'] != '' || 
				arrAnagrafica['comune_stabilimento'] != '' ||
				arrAnagrafica['toponimo_stabilimento'] != '' ||
				arrAnagrafica['via_stabilimento'] != '' ||
				arrAnagrafica['cap_stabilimento'] != '') {
			
			document.getElementById('civico_stabilimento').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('cap_stabilimento').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('via_stabilimento').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('toponimo_stabilimento').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('comune_stabilimento').style = 'display: ; background-color: #dddddd; text-align: center';
			document.getElementById('provincia_stabilimento').style = 'display: ; background-color: #dddddd; text-align: center';
			
			$("._placeholder_provincia_stabilimento").remove();
			$('<span class="_placeholder_provincia_stabilimento">provincia </span>').insertBefore('#provincia_stabilimento');
			$("._placeholder_comune_stabilimento").remove();
			$('<span class="_placeholder_comune_stabilimento">comune </span>').insertBefore('#comune_stabilimento');
			$("._placeholder_cap_stabilimento").remove();
			$('<span class="_placeholder_cap_stabilimento">cap </span>').insertBefore('#cap_stabilimento');
			$("._placeholder_toponimo_stabilimento").remove();
			$('<span class="_placeholder_toponimo_stabilimento">indirizzo </span>').insertBefore('#toponimo_stabilimento');
			
		}
	}
	
	
	$("#ins_numero_registrazione").hide();
	$("#stabilimento_id_sezione").hide();
	$("#dati_impresa_id").hide();
	resetStar();
	loadModalWindowUnlock();
}