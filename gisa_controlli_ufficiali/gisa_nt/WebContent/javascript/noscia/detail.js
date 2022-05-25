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

function setLinea(key, value){
	
	var newRow = $("<tr>");
    var cols = "";
    cols += '<td class="formLabel">LINEA</td>';
    cols += '<td>'+value+'</td>';
    newRow.append(cols);
    newRow.insertAfter($('#trLinee'));
}

function VisualizzaDettaglio(arrAnagrafica, arrLinee){

	$("div#dettaglio :input").each(function(){
		 var input = $(this); // This is the jquery object of the input, do what you will
		 if (input.attr('type') == 'button'){
			 input.hide();
		 }
		 if (input.attr('type') == 'text'){
			 input.hide();
		 }
		});
	$("#dati_impresa_id").hide();
	
	var value;
	for (var key in arrAnagrafica) {
	    value = arrAnagrafica[key];
	   setValore(key, value);
	}
	
	for (var key in arrLinee) {
	    value = arrLinee[key];
    	setLinea(key, value);
	}
	
}