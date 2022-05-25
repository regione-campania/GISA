/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function chooseProvinciaReimmissione(provincia) {
	if (provincia == 'BN') {
		document.getElementById( 'comuneReimmissioneBN' ).style.display = "";
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneAV' ).style.display = "none";
		
	}
	else if (provincia == 'NA') {
		document.getElementById( 'comuneReimmissioneBN' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneAV' ).style.display = "none";
	}	
	else if (provincia == 'AV') {
		document.getElementById( 'comuneReimmissioneBN' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneAV' ).style.display = "";
	}
	else if (provincia == 'SA') {
		document.getElementById( 'comuneReimmissioneBN' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneAV' ).style.display = "none";
	}
	else if (provincia == 'CE') {
		document.getElementById( 'comuneReimmissioneBN' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "";
		document.getElementById( 'comuneReimmissioneAV' ).style.display = "none";
	}
	else if (provincia == 'X') {
		document.getElementById( 'comuneReimmissioneBN' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneAV' ).style.display = "none";
	}
}



function checkform(form) {
		
	if (form.dataReimmissione.value == '') {
		alert("Inserire la data del rilascio");		
		return false;
	}	
	
	if (form.provinciaReimmissione.value == 'X') {
		alert("Inserire la provincia");			
		return false;
	}	
	
	if (form.provinciaReimmissione.value == 'AV' && form.comuneReimmissioneAV.value == '0') {
		alert("Selezionare il comune AV");			
		return false;
	}
	
	if (form.provinciaReimmissione.value == 'CE' && form.comuneReimmissioneCE.value == '0') {
		alert("Selezionare il comune CE");			
		return false;
	}
	
	if (form.provinciaReimmissione.value == 'BN' && form.comuneReimmissioneBN.value == '0') {
		alert("Selezionare il comune BN");			
		return false;
	}
	
	if (form.provinciaReimmissione.value == 'NA' && form.comuneReimmissioneNA.value == '0') {
		alert("Selezionare il comune NA");			
		return false;
	}
	
	if (form.provinciaReimmissione.value == 'SA' && form.comuneReimmissioneSA.value == '0') {
		alert("Selezionare il comune SA");			
		return false;
	}
			
	
	if (form.luogoReimmissione.value == '') {
		alert("Inserire il luogo del rilascio");			
		return false;
	}
		
	return true;
	
	
}