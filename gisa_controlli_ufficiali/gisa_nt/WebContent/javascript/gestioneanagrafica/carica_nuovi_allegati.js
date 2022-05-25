/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
$(function() {
	
	 $('#popup_lista_allegati').dialog({
		title : 'LISTA ALLEGATI DA AGGIUNGERE ALLA PRATICA',
       autoOpen: false,
       resizable: false,
       closeOnEscape: false,
       width:1000,
       height:550,
       draggable: false,
       modal: true,
	     buttons: {
			 'COMPLETA INSERIMENTO ALLEGATI': function() {
				 aggiungi_allegati_alla_pratica();
			},
			'ESCI': function() {
				loadModalWindowUnlock();
				$( this ).dialog('close');
			}
    	}
	});
	 
});

function carica_allegati(){
	
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


$(function() {
	
	 $('#popup_allegati_selezionati').dialog({
	  title : 'CARICA ALLEGATI',
      autoOpen: false,
      resizable: false,
      closeOnEscape: false,
      width:1000,
      height:350,
      draggable: false,
      modal: true
	});
	 
});


function aggiungi_allegati_alla_pratica(){
	
	var popup_lista_alleg = document.getElementById("popup_lista_allegati").getElementsByTagName("input"); 
	var n_allegati = 0;
	$('#popup_allegati_selezionati').html('');
	var htmlText='<br>';
	htmlText+='<form id=\'salvataggioAllegati\' name=\'salvataggioAllegati\' class=\'form-horizontal\' role=\'form\' method=\'post\' ' + 
			  ' action=\'GestioneAllegatiGins.do?command=AggiungiAllegatiPraticaEsistente\' ' + 
			  ' onsubmit=\'return validateForm()\' >';
	htmlText+='<input type=\'hidden\' id=\'numeroPratica\' name=\'numeroPratica\' value=\''+document.getElementById('numeroPraticaInput').value+'\' />'; 
	htmlText+='<input type=\'hidden\' id=\'comune_richiedente\' name=\'comune_richiedente\' value=\''+document.getElementById('idComunePratica').value+'\' />';
	htmlText+='<input type=\'hidden\' id=\'idAggiuntaPratica\' name=\'idAggiuntaPratica\' value=\''+document.getElementById('idAggiuntaPraticatemp').value+'\' />';
	htmlText+='<input type=\'hidden\' id=\'altId\' name=\'altId\' value=\''+document.getElementById('alt_id').value+'\' />';
	htmlText+='<input type=\'hidden\' id=\'stabId\' name=\'stabId\' value=\''+document.getElementById('stab_id').value+'\' />';
	htmlText+='<input type=\'hidden\' id=\'desc_operatore\' name=\'desc_operatore\' value=\''+document.getElementById('desc_operatore').value+'\' />';
	htmlText+='<table id=\'tabella_allegati_selezionati\' class=\'details\' border=\'1\' cellpadding=\'4\' width=\'100%\'>'; desc_operatore
	htmlText+='<tr><th colspan=\'2\'>lista allegati selezionati</th></tr>';
        
	for (var x = 0; x < popup_lista_alleg.length; x++){
		var campo_alleg = popup_lista_alleg[x];
		if (campo_alleg.checked == true){
			if(document.getElementById("tr_header_" + allegati[x].code) == null){
				n_allegati++;
				var sigla_allegato = allegati[x].code;
				var desc_allegato_selezionato = allegati[x].desc; 
				htmlText+='<tr id=\'tr_header_' + sigla_allegato + '\'>';
				htmlText+='<td align=\'center\' width=\'40%\'><p><b>ALLEGATO ' + sigla_allegato +'</b><br>'+
							desc_allegato_selezionato + '</p></td>';
				htmlText+='<td>';
				htmlText += "<a href= \"javascript:openUploadAllegatoGins(document.getElementById('idAggiuntaPratica').value, '"+sigla_allegato+"', 'GINS_Pratica')\" id='allega'>Allega file</a>";
				htmlText += "<input type='hidden' readonly='readonly' id='header_"+ sigla_allegato +"' name='header_"+ sigla_allegato +"'/>";
				htmlText += "<label id='titolo_"+ sigla_allegato +"' name='titolo_"+ sigla_allegato +"'></label>";
				htmlText += "<input type='button' style='float: right;' value='rimuovi allegato' onclick='rimuovi_linea(\"tr_header_" + sigla_allegato + "\")'/>";
				htmlText+='</td>';
				htmlText+='</tr>';
			}
		}
	}
	htmlText+='</table><br><br><center>' + 
	   '<button  type="submit" class="btn btn-primary" style="width: 80px;">SALVA</button>' +
	   '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
	   '<button type="button" class="btn btn-primary" style="width: 80px;" onclick="loadModalWindowUnlock(); $(\'#popup_allegati_selezionati\').dialog(\'close\'); ">ESCI</button>';
	htmlText+='</center></form>';
	if(n_allegati>0){
		$('#popup_lista_allegati').dialog('close');
		$('#popup_allegati_selezionati').html(htmlText);
	    $('#popup_allegati_selezionati').dialog('open');
	} else {
		alert('Attenzione! nessun allegato selezionato.');
	}
		
}

function rimuovi_linea(idtr){
	var child = document.getElementById(idtr);
	child.parentNode.removeChild(child);
}


function validateForm(){
	
	var campi = document.getElementById("salvataggioAllegati").getElementsByTagName("input"); 
	var campi_allegati_sel = 0;
	for (var x = 0; x<campi.length; x++){
		var campo = campi[x];
		if (campo.name.includes("header_")>0){
			campi_allegati_sel++;
		}
		if (campo.name.includes("header_")>0 && campo.value == ''){
			alert('Attenzione! Caricare i file per gli allegati selezionati.');
			return false;
		}
	}
	
	if(campi_allegati_sel == 0){
		alert('Attenzione! non ci sono allegati da salvare.');
		return false;
	}
	
	loadModalWindowCustom('Attendere Prego...');
	return true;
	
}
