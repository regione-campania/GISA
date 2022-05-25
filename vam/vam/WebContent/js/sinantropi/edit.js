/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function chooseSpecie(numero) {	
	
	if (numero == 1) {
		document.getElementById( 'uccelli' ).style.display = "";
		document.getElementById( 'mammiferi' ).style.display = "none";
		document.getElementById( 'rettiliAnfibi' ).style.display = "none";
	}
	else if (numero == 2) {
		document.getElementById( 'uccelli' ).style.display = "none";
		document.getElementById( 'mammiferi' ).style.display = "";
		document.getElementById( 'rettiliAnfibi' ).style.display = "none";
	}
	else if (numero == 3) {
		document.getElementById( 'uccelli' ).style.display = "none";
		document.getElementById( 'mammiferi' ).style.display = "none";
		document.getElementById( 'rettiliAnfibi' ).style.display = "";
	}
	else if (numero == 0) {
		document.getElementById( 'uccelli' ).style.display = "none";
		document.getElementById( 'mammiferi' ).style.display = "none";
		document.getElementById( 'rettiliAnfibi' ).style.display = "none";		
	}
	
}


function checkform(form) {
	
	
	if (form.specieSinantropo.value == '0') {
		alert("Inserire la classe del Sinantropo");			
		return false;
	}
	
	if (form.specieSinantropo.value == '1' && form.tipologiaSinantropoU.value == '0') {
		alert("Selezionare il tipo di uccello");			
		return false;
	}
	
	if (form.specieSinantropo.value == '2' && form.tipologiaSinantropoM.value == '0') {
		alert("Selezionare il tipo di mammifero");			
		return false;
	}
	
	if (form.specieSinantropo.value == '3' && form.tipologiaSinantropoRA.value == '0') {
		alert("Selezionare il tipo di rettile/anfibio");			
		return false;
	}	
	
	if (form.sesso.value == 'X') {
		alert("Inserire il sesso del sinantropo");			
		return false;
	}	
			
	if (form.idEta.value == '-1') {
		alert("Selezionare l'et�");	
		form.idEta.focus();
		return false;
	}	
	
			
		
	attendere();
	return true;
	
}