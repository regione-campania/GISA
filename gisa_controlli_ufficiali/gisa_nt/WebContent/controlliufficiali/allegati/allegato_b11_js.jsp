<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript">

// function resetRisposta(cap, i){
// 	var campo = cap+'_'+i;
// 	var dsi = document.getElementById('d_si_'+campo);
// 	var dno = document.getElementById('d_no_'+campo);
	
// 	dsi.checked = false;
// 	dno.checked = false;
	
// }

function checkFormB11(){
	var esito = true;
	var msg = '';
	
	var numCapiPresenti = document.getElementById("numeroCapiEffettivamentePresenti").value;
	var numCapiControllati = document.getElementById("numeroCapiControllati").value;

	var numCapiBlocco = document.getElementById("bloccoMovimentazioni").value;
	var numCapiSanzione = document.getElementById("amministrativaPecuniaria").value;
	var numCapiSequestro = document.getElementById("sequestroCapi").value;
	var numCapiAltro = document.getElementById("altroCapi").value;
	var sanzAltroDesc = document.getElementById("altroSpecificareDesc").value;
	
	var evidenze = document.getElementById("riscontroNonConformita_si");
	var evidenzeBa = document.getElementById("evidenzeBenessere");
	var evidenzeIr = document.getElementById("evidenzeIdentificazione");
	var evidenzeSv = document.getElementById("evidenzeSostanze");

	
	var nomeResponsabile = document.getElementById("nomeResponsabilePrimoControlloLoco").value;
	var nomeControllore = document.getElementById("nomeControllorePrimoControlloLoco").value;
	var dataPrimoControllo = document.getElementById("dataPrimoControlloLoco").value;
	
	var note = document.getElementById("puntoNote").value;

	
	if (numCapiBlocco>numCapiPresenti){
		esito = false;
		msg+="Numero di capi soggetti al blocco movimentazioni deve essere minore o uguale a Numero di capi effettivamente presenti.\n";
	}
	if (numCapiSanzione>numCapiPresenti){
		esito = false;
		msg+="Numero di capi soggetti a sanzione amministrativa deve essere minore o uguale a Numero di capi effettivamente presenti.\n";
	}
	if (numCapiSequestro>numCapiPresenti){
		esito = false;
		msg+="Numero di capi soggetti al sequestro deve essere minore o uguale a Numero di capi effettivamente presenti.\n";
	}
	if (numCapiAltro>numCapiPresenti){
		esito = false;
		msg+="Numero di capi soggetti ad altra sanzione deve essere minore o uguale a Numero di capi effettivamente presenti.\n";
	}
	if (numCapiAltro>0 && sanzAltroDesc==''){
		esito = false;
		msg+="Specificare descrizione altra sanzione applicata ai capi.\n";
	}
	if ((numCapiAltro<=0|| numCapiAltro=='') && sanzAltroDesc!=''){
		esito = false;
		msg+="Specificare numero di capi soggetti ad altra sanzione o svuotare il campo Descrizione altra sanzione.\n";
	}
	
	if (evidenze.checked && (evidenzeBa.value=='' && evidenzeIr.value=='' && evidenzeSv.value=='') ){
		esito = false;
		msg+="Indicare un tipo di evidenza dopo aver selezionato Riscontro di elementi di non conformita'.\n";
	}
	
	if (!evidenze.checked && (evidenzeBa.value!='' || evidenzeIr.value!='' || evidenzeSv.value!='') ){
		esito = false;
		msg+="Impossibile indicare un tipo di evidenza senza aver selezionato Riscontro di elementi di non conformita'.\n";
	}
	
	if(document.querySelector('input[name="appartenenteCondizionalita"]:checked') == null) {
		esito = false;
		msg+="Indicare appartenenza al Campione Condizionalita'.\n";
	}
	
	if(nomeResponsabile=='') {
		esito = false;
		msg+="Indicare Nome e cognome del detentore/proprietario/altro responsabile dell'azienda presente.\n";
	}
	
	if(nomeControllore=='') {
		esito = false;
		msg+="Indicare Nome e cognome del controllore.\n";
	}
	
	if(dataPrimoControllo=='') {
		esito = false;
		msg+="Indicare Data primo controllo.\n";
	}
	
	if(document.querySelector('input[name="criteriUtilizzati"]:checked') == null) {
		esito = false;
		msg+="Selezionare almeno un criterio di controllo.\n";
	}
	

	if (esito==false)
		alert(msg);
	return esito;
	
	
}

function gestisciEsito(campo){
	
	var val = campo.value;
	
	if (val=='si'){
		
		  var inputs = document.getElementById("checklist").getElementsByTagName("input");

		    //Loop and find only the Radios
		    for (var i = 0; i < inputs.length; ++i) {
		        if (inputs[i].type == 'radio') {
		        	if (inputs[i].value == 'na'){
						inputs[i].checked=true;;
					}
		        
		        
		        }
		    }
		document.getElementById("checklist").style.display="none";
		
	}
	else {
		document.getElementById("checklist").style.display="";
	}
	
	
	
	
}



</script>