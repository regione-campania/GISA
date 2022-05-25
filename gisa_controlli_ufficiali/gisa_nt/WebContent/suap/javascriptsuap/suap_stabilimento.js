/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/





function visualizzaData(campo){
	
        if (campo.value=='1'){
                document.getElementById("trDataInizio").style.display="none";
                document.getElementById("trDataFine").style.display="none";
                
                if (document.getElementById("divDataInizioStabilimento")!=null)
                	document.getElementById("divDataInizioStabilimento").style.display="none";
             
        }else{
                document.getElementById("trDataInizio").style.display="";
                document.getElementById("trDataFine").style.display="";
                
                if (document.getElementById("divDataInizioStabilimento")!=null)
                	document.getElementById("divDataInizioStabilimento").style.display="";
        }
   
}




function selezionaStabilimento(i){
	var msgHD="ATTENZIONE! Uno o più campi obbligatori dello stabilimento non sono presenti, contattare l'helpdesk.";
	
	var stab = resultStab[i];
	
	$('input[name="searchcodeIdprovinciaStabinput"]').val(stab.sedeOperativa.descrizione_provincia);
	$("#searchcodeIdprovinciaStab").append("<option value='"+stab.sedeOperativa.idProvincia+ "' selected='selected'>"+stab.sedeOperativa.descrizione_provincia+"</option>');");
	

	$('input[name="searchcodeIdComuneStabinput"]').val(stab.sedeOperativa.descrizioneComune);
	$("#searchcodeIdComuneStab").append("<option value='"+stab.sedeOperativa.comune+ "' selected='selected'>"+stab.sedeOperativa.comune+"</option>');");
	


	$('input[name="viaStabinput"]').val(stab.sedeOperativa.via);
	$("#viaStab").append("<option value='"+stab.sedeOperativa.idIndirizzo+ "' selected='selected'>"+stab.sedeOperativa.via+"</option>');");
	

	$('input[name="toponimoSedeOperativa"]').val(stab.sedeOperativa.toponimo);
	$('input[name="civicoSedeOperativa"]').val(stab.sedeOperativa.civico);
	
	

	$('input[name="capStab"]').val(stab.sedeOperativa.cap);	


	
	//alert(stab.tipoAttivita);

	$('select[name="tipoAttivita"]').val(stab.tipoAttivita);

	if(stab.numeroRegistrazione!='' && stab.numeroRegistrazione!=null && $('input[name="numeroRegistrazioneVariazione"]')!=null)
		$('input[name="numeroRegistrazioneVariazione"]').val(stab.numeroRegistrazione);

	
	
	//alert(stab.tipoCarattere);
		$('select[name="tipoCarattere"]').val(stab.tipoCarattere);
	
	

	$('#dataInizio').val(stab.dataInizioAttivitaString);
	
	

	$('#dataFine').val(stab.dataFineAttivitaString);
	
	

	$('#latStab').val(stab.sedeOperativa.latitudine);

	

	$('#longStab').val(stab.sedeOperativa.longitudine);

	

	$('#civicoSedeOperativa').val(stab.sedeOperativa.civico);
	
  
	
	

	
	
	$('#dialogsuaplistastab').dialog('close');
	
    
}
function closePopupStab(){
    $('#dialogsuaplistastab').dialog('close');
}


