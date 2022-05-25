/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function mostraNascondiNumeroPratica(val){
	
	if (val=='manuale'){
		document.getElementById("numeroPratica").required = "required";
		document.getElementById("numeroPratica").style.visibility = "visible";
	}
	else if (val =='automatico'){
		document.getElementById("numeroPratica").required = "";
		document.getElementById("numeroPratica").style.visibility = "hidden";
		document.getElementById("numeroPratica").value = "";
	}
}

function openUploadAllegatoGins(idAggiuntaPratica, codiceAllegato, tipoUpload){
	var res;
	var result;
	
	if (document.all || 1==1) {
		window.open('GestioneAllegatiGins.do?command=PrepareUploadAllegato&tipo='+tipoUpload+'&tipoAllegato='+tipoUpload+'&idAggiuntaPratica='+idAggiuntaPratica+'&codiceAllegato='+codiceAllegato,null,
		'height=450px,width=480px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
		} else {
		
			res = window.showModalDialog('GestioneAllegatiGins.do?command=PrepareUploadAllegato&tipo='+tipoUpload+'&tipoAllegato='+tipoUpload+'&idAggiuntaPratica='+idAggiuntaPratica+'&codiceAllegato='+codiceAllegato,null,
			'dialogWidth:480px;dialogHeight:450px;center: 1; scroll: 0; help: 1; status: 0');
		
		}
		} 