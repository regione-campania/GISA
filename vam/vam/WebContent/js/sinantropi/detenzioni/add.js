/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function showNumeroDocumento() {	
	target = document.getElementById( 'numeroDocumento' ).style.display = "";	
}

function disableNumeroDocumento() {	
	target = document.getElementById( 'numeroDocumento' ).style.display = "none";
	document.form.richiedenteNumeroDocumento.value='';	
}

function documento () {
	if (form.tipologiaDocumento.value == '0') {
		disableNumeroDocumento();
	}
	else {
		showNumeroDocumento();
	}
}

function chooseProvinciaDetenzione(provincia) {
	if (provincia == 'BN') {
		document.getElementById( 'comuneDetenzioneBN' ).style.display = "";
		document.getElementById( 'comuneDetenzioneNA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneSA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneCE' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneAV' ).style.display = "none";
		
	}
	else if (provincia == 'NA') {
		document.getElementById( 'comuneDetenzioneBN' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneNA' ).style.display = "";
		document.getElementById( 'comuneDetenzioneSA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneCE' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneAV' ).style.display = "none";
	}	
	else if (provincia == 'AV') {
		document.getElementById( 'comuneDetenzioneBN' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneNA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneSA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneCE' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneAV' ).style.display = "";
	}
	else if (provincia == 'SA') {
		document.getElementById( 'comuneDetenzioneBN' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneNA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneSA' ).style.display = "";
		document.getElementById( 'comuneDetenzioneCE' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneAV' ).style.display = "none";
	}
	else if (provincia == 'CE') {
		document.getElementById( 'comuneDetenzioneBN' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneNA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneSA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneCE' ).style.display = "";
		document.getElementById( 'comuneDetenzioneAV' ).style.display = "none";
	}
	else if (provincia == 'X') {
		document.getElementById( 'comuneDetenzioneBN' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneNA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneSA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneCE' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneAV' ).style.display = "none";
	}
}


function chooseDetentore (detentore) {
	if (detentore == '1') {
		document.getElementById( 'detentorePrivato' ).style.display = "";
	}
	else {
		document.getElementById( 'detentorePrivato' ).style.display = "none";
	}
}

function controllaCF(cf)
{
	var validi, i, s, set1, set2, setpari, setdisp;
	
	if( cf == '' ) 
		return '';
	cf = cf.toUpperCase();
	
	if( cf.length != 16 )
		return "La lunghezza del codice fiscale non è\n"
		+"corretta: il codice fiscale dovrebbe essere lungo\n"
		+"esattamente 16 caratteri.\n";
	validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	for( i = 0; i < 16; i++ ){
		if( validi.indexOf( cf.charAt(i) ) == -1 )
			return "Il codice fiscale contiene un carattere non valido `" +
			cf.charAt(i) +
			"'.\nI caratteri validi sono le lettere e le cifre.\n";
	}
	set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
	setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
	
	s = 0;
	for( i = 1; i <= 13; i += 2 )
		s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
	for( i = 0; i <= 14; i += 2 )
		s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
	if( s%26 != cf.charCodeAt(15)-'A'.charCodeAt(0) )
		return "Controlla il campo del codice fiscale:\n"+
		"il codice di controllo non corrisponde.\n";
	return "";
}


function checkform(form) {
	
	
	
	if (form.dataDetenzioneDa.value == '') {
		alert("Inserire la data di inizio detenzione");		
		return false;
	}	
	
		
	if (form.tipologiaDetentore.value == '0') {
		alert("Inserire la tipologia di detentore");		
		return false;
	}
	
	
	if (form.tipologiaDetentore.value == '1' && form.detentorePrivatoNome.value == '') {
		alert("Inserire il nome del Detentore");			
		return false;
	}
	
	if (form.tipologiaDetentore.value == '1' && form.detentorePrivatoCognome.value == '') {
		alert("Inserire il cognome del Detentore");			
		return false;
	}
	
	if (form.tipologiaDetentore.value == '1' && form.detentorePrivatoCodiceFiscale.value == '') {
		alert("Inserire il codice fiscale del Detentore");			
		return false;
	}
	
	if (form.tipologiaDetentore.value == '1' && form.detentorePrivatoCodiceFiscale.value != '') {
		
		var checkCF = controllaCF(form.detentorePrivatoCodiceFiscale.value);
		if (checkCF != "") {
			alert(checkCF);			
			return false;
		}
	}
	
		
	if (form.tipologiaDetentore.value == '1' && form.provinciaDetenzione.value == 'X') {
		alert("Inserire la provincia di detenzione");			
		return false;
	}	
	
	if (form.tipologiaDetentore.value == '1' && form.provinciaDetenzione.value == 'AV' && form.comuneDetenzioneAV.value == '0') {
		alert("Selezionare il comune AV");			
		return false;
	}
	
	if (form.tipologiaDetentore.value == '1' && form.provinciaDetenzione.value == 'BN' && form.comuneDetenzioneBN.value == '0') {
		alert("Selezionare il comune BN");			
		return false;
	}
	
	if (form.tipologiaDetentore.value == '1' && form.provinciaDetenzione.value == 'CE' && form.comuneDetenzioneCE.value == '0') {
		alert("Selezionare il comune CE");			
		return false;
	}
	
	if (form.tipologiaDetentore.value == '1' && form.provinciaDetenzione.value == 'NA' && form.comuneDetenzioneNA.value == '0') {
		alert("Selezionare il comune NA");			
		return false;
	}
	
	if (form.tipologiaDetentore.value == '1' && form.provinciaDetenzione.value == 'SA' && form.comuneDetenzioneSA.value == '0') {
		alert("Selezionare il comune SA");			
		return false;
	}
			
	if (form.tipologiaDetentore.value == '1' && form.luogoDetenzione.value == '') {
		alert("Inserire il luogo di detenzione");			
		return false;
	}

		
		
	attendere();
	return true;
	
}