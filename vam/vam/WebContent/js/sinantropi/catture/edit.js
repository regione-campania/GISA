/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function chooseProvinciaCattura(provincia) {
	if (provincia == 'BN') {
		document.getElementById( 'comuneCatturaBN' ).style.display = "";
		document.getElementById( 'comuneCatturaNA' ).style.display = "none";
		document.getElementById( 'comuneCatturaSA' ).style.display = "none";
		document.getElementById( 'comuneCatturaCE' ).style.display = "none";
		document.getElementById( 'comuneCatturaAV' ).style.display = "none";
		
	}
	else if (provincia == 'NA') {
		document.getElementById( 'comuneCatturaBN' ).style.display = "none";
		document.getElementById( 'comuneCatturaNA' ).style.display = "";
		document.getElementById( 'comuneCatturaSA' ).style.display = "none";
		document.getElementById( 'comuneCatturaCE' ).style.display = "none";
		document.getElementById( 'comuneCatturaAV' ).style.display = "none";
	}	
	else if (provincia == 'AV') {
		document.getElementById( 'comuneCatturaBN' ).style.display = "none";
		document.getElementById( 'comuneCatturaNA' ).style.display = "none";
		document.getElementById( 'comuneCatturaSA' ).style.display = "none";
		document.getElementById( 'comuneCatturaCE' ).style.display = "none";
		document.getElementById( 'comuneCatturaAV' ).style.display = "";
	}
	else if (provincia == 'SA') {
		document.getElementById( 'comuneCatturaBN' ).style.display = "none";
		document.getElementById( 'comuneCatturaNA' ).style.display = "none";
		document.getElementById( 'comuneCatturaSA' ).style.display = "";
		document.getElementById( 'comuneCatturaCE' ).style.display = "none";
		document.getElementById( 'comuneCatturaAV' ).style.display = "none";
	}
	else if (provincia == 'CE') {
		document.getElementById( 'comuneCatturaBN' ).style.display = "none";
		document.getElementById( 'comuneCatturaNA' ).style.display = "none";
		document.getElementById( 'comuneCatturaSA' ).style.display = "none";
		document.getElementById( 'comuneCatturaCE' ).style.display = "";
		document.getElementById( 'comuneCatturaAV' ).style.display = "none";
	}
	else if (provincia == 'X') {
		document.getElementById( 'comuneCatturaBN' ).style.display = "none";
		document.getElementById( 'comuneCatturaNA' ).style.display = "none";
		document.getElementById( 'comuneCatturaSA' ).style.display = "none";
		document.getElementById( 'comuneCatturaCE' ).style.display = "none";
		document.getElementById( 'comuneCatturaAV' ).style.display = "none";
	}
	
}

function checkform(form) {
		
		
	if (form.dataCattura.value == '') {
		alert("Inserire la data rinvenimento");		
		return false;
	}	
	
	if (form.provinciaCattura.value == 'X') {
		alert("Inserire la provincia");			
		return false;
	}	
	
	if (form.provinciaCattura.value == 'AV' && form.comuneCatturaAV.value == '0') {
		alert("Selezionare il comune AV");			
		return false;
	}
	
	if (form.provinciaCattura.value == 'CE' && form.comuneCatturaCE.value == '0') {
		alert("Selezionare il comune CE");			
		return false;
	}
	
	if (form.provinciaCattura.value == 'BN' && form.comuneCatturaBN.value == '0') {
		alert("Selezionare il comune BN");			
		return false;
	}
	
	if (form.provinciaCattura.value == 'NA' && form.comuneCatturaNA.value == '0') {
		alert("Selezionare il comune NA");			
		return false;
	}
	
	if (form.provinciaCattura.value == 'SA' && form.comuneCatturaSA.value == '0') {
		alert("Selezionare il comune SA");			
		return false;
	}
			
	
	if (form.luogoCattura.value == '') {
		alert("Inserire il luogo del rinvenimento");			
		return false;
	}
		
	attendere();
	return true;
	
}