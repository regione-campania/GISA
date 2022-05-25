<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script type="text/javascript" src="dwr/interface/DWRnoscia.js"> </script>

<script  src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<div id='popup_associa_pratica'></div>
<div id='popup_ricerca_pratica'></div>

<script>

$(function() {
	
	 $('#popup_associa_pratica').dialog({
		title : 'ASSOCIA PRATICA A STABILIMENTO',
        autoOpen: false,
        resizable: false,
        closeOnEscape: false,
        width:500,
        height:260,
        position: ['center','top'],
        draggable: false,
        modal: true
	});
	 
});

function gestione_associa_pratica(cod_comune, stab_id, id_utente){
	var htmlText = '<center>' +
	   '<br><br><b>inserisci numero pratica</b><br><br>' + 
	   '<input type="text" id="numero_pratica" placeholder="numero pratica" style="text-align:center;" size="40" autocomplete="off"/>' + 
	   '<br><br>' + 
	   '<button  type="button" class="btn btn-primary" style="width: 120px;" onclick="associaPraticaSuap(' + cod_comune + ',' + stab_id +',' + id_utente + ')">ASSOCIA</button>' +
	   '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
	   '<button type="button" class="btn btn-primary" style="width: 120px;" onclick="loadModalWindowUnlock(); $(\'#popup_associa_pratica\').dialog(\'close\'); ">ESCI</button>' +
	   '<br><br>Nel caso in cui non si disponga del numero pratica è possibile utilizzare il tasto lista pratiche associabili qui sotto e scegliere una pratica da associare<br><br>' +
	   '<button class="btn btn-primary" style="width: 250px;" onclick="caricaListaPratiche(' + cod_comune + ',' + stab_id +',' + id_utente + ')">LISTA PRATICHE ASSOCIABILI</button>' +
	   '</center>';
	$('#popup_associa_pratica').html(htmlText);
    $('#popup_associa_pratica').dialog('open');
}

function associaPraticaSuap(cod_comune, stab_id, id_utente){
	var numero_pratica = document.getElementById('numero_pratica').value;
	
	if(numero_pratica.trim() == ''){
		alert('Attenzione, inserire numero pratica');
		return false;
	}
	
	loadModalWindowCustom('Operazione in corso. Attendere...');
	DWRnoscia.associaPraticaSuap(numero_pratica, cod_comune, stab_id, id_utente,{callback:associaPraticaSuapCallBack,async:false});
}

function associaPraticaSuapCallBack(val)
{	
	if(val == '1'){
		$('#popup_associa_pratica').dialog('close');
		alert('Pratica associata con successo');
	} else if(val == '2'){
		alert('Errore: la pratica non esiste per il comune di questo stabilimento');
	} else if (val == '3'){
		alert('Attenzione, la pratica risulta gia associata a questo stabilimento');
	} else {
		alert('Errore generico');
	}
	loadModalWindowUnlock();
	
}


$(function() {
	
	$('#popup_ricerca_pratica').dialog({
		title : 'LISTA PRATICHE ASSOCIABILI',
		autoOpen: false,
		resizable: false,
		closeOnEscape: false,
		width:700,
		height:400,
		draggable: false,
		modal: true,
		buttons: {
			 'ASSOCIA': function() {
				 		associaPraticaDaLista(getRadioValue('radiopratsele'));
			},
			 'ANNULLA': function() {
			            loadModalWindowUnlock();
						$( this ).dialog('close');
			}
	    }
	});
	 
});

var objPraticheSuap;
var idUtenteAssocciaPratica;
var idStabAssociaPratica;
var idComuneStab;
function caricaListaPratiche(cod_comune, stab_id, id_utente){
	var htmlText = '';
	$('#popup_associa_pratica').html(htmlText);
	$('#popup_associa_pratica').dialog('close');
	loadModalWindowCustom('Operazione in corso. Attendere...');
	idUtenteAssocciaPratica = id_utente;
	idStabAssociaPratica = stab_id;
	idComuneStab = cod_comune;
	DWRnoscia.cercaPraticaDaAssociare(cod_comune, stab_id,{callback:caricaListaPraticheCallBack,async:false});
}

function caricaListaPraticheCallBack(val){
	var dati = val;
	var obj;
	obj = JSON.parse(dati);
	objPraticheSuap = obj;
	var len = obj.length;
	if (len > 0){
		var htmlText = '';
		var j = 0;
		htmlText='<br>'; 
		htmlText+='<center><table border=\'1\' cellpadding=\'4\' width=\'100%\' ><tr><th>NUMERO PRATICA</th><th>COMUNE</th><th>DATA PEC</th><th>TIPO PRATICA</th><th></th></tr>';
		for (i = 0; i < len; i++){
			htmlText+='<tr><td align=\'center\'>' + obj[i].numero_pratica + 
			'</td><td align=\'center\'>' + obj[i].comune_pratica + 
			'</td><td align=\'center\'>' + obj[i].data_pratica + 
			'</td><td align=\'center\'>' + obj[i].tipo_pratica + 
	  		'</td><td align=\'center\'><input type=\'radio\' id=\'radiopratsele\' name=\'radiopratsele\' value=\''+i+'\'></td></tr>';
		}
		htmlText+='</table></center>';
	    
    	$('#popup_ricerca_pratica').html(htmlText);
     	$('#popup_ricerca_pratica').dialog('open');
	} else {
		alert('Attenzione, per questo stabilimento non è stata trovata nessuna pratica associabile negli ultimi 30 giorni!!!');
        loadModalWindowUnlock();
	}
}

function getRadioValue(theRadioGroup)
{
    var elements = document.getElementsByName(theRadioGroup);
    for (var i = 0, l = elements.length; i < l; i++)
    {
        if (elements[i].checked)
        {
            return elements[i].value;
        }
    }
}

function associaPraticaDaLista(idpratsel){
	
	if(idpratsel == null){
		alert('Attenzione, nessuna pratica selezionata!');
		return false;
	}
	var obj = objPraticheSuap[idpratsel];
	loadModalWindowCustom('Operazione in corso. Attendere...');
	DWRnoscia.associaPraticaSuap(obj.numero_pratica, idComuneStab, idStabAssociaPratica, idUtenteAssocciaPratica,{callback:associaPraticaSuapDaListaCallBack,async:false});
	
}

function associaPraticaSuapDaListaCallBack(val)
{	
	if(val == '1'){
		$('#popup_ricerca_pratica').dialog('close');
		alert('Pratica associata con successo');
	} else if(val == '2'){
		alert('Errore: la pratica non esiste per il comune di questo stabilimento');
	} else if (val == '3'){
		alert('Attenzione, la pratica risulta gia associata a questo stabilimento');
	} else {
		alert('Errore generico');
	}
	loadModalWindowUnlock();
	
}


</script>
