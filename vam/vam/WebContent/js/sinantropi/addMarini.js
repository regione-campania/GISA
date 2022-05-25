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



function showNumeroSin(numero) {	
	
	if(document.getElementById( 'mc' ) != null)
	{
		document.getElementById( 'mc' ).value="";
		document.getElementById( 'mc' ).style.display = "none";
	}
	if(document.getElementById( 'codiceIspra' ) != null)
	{
		document.getElementById( 'codiceIspra' ).value="";
		document.getElementById( 'codiceIspra' ).style.display = "none";
	}
	target = document.getElementById( 'numeroUfficialeD' ).style.display = "";	
	if (numero != null)
		 document.getElementById( 'numeroUfficialeD' ).value = numero;
}

function showMc(numero) {	
	
	if(document.getElementById( 'numeroUfficialeD' ) != null)
	{
		document.getElementById( 'numeroUfficialeD' ).value="";
		document.getElementById( 'numeroUfficialeD' ).style.display = "none";
	}
	if(document.getElementById( 'codiceIspra' ) != null)
	{
		document.getElementById( 'codiceIspra' ).value="";
		document.getElementById( 'codiceIspra' ).style.display = "none";
	}
	target = document.getElementById( 'mc' ).style.display = "";	
	if (numero != null)
		 document.getElementById( 'mc' ).value = numero;
}

function showCodiceIspra(numero) {	
	
	if(document.getElementById( 'numeroUfficialeD' ) != null)
	{
		document.getElementById( 'numeroUfficialeD' ).value="";
		document.getElementById( 'numeroUfficialeD' ).style.display = "none";
	}
	if(document.getElementById( 'mc' ) != null)
	{
		target = document.getElementById( 'mc' ).style.display = "none";
		document.getElementById( 'mc' ).value="";
	}
	target = document.getElementById( 'codiceIspra' ).style.display = "";	
	if (numero != null)
		 document.getElementById( 'codiceIspra' ).value = numero;
}

function disableNumeroSin() {	
	if(document.getElementById( 'numeroUfficialeD' ) != null)
	{
		target = document.getElementById( 'numeroUfficialeD' ).style.display = "none";
		document.getElementById( 'numeroUfficialeD' ).value="";
	}
	if(document.getElementById( 'mc' ) != null)
	{
		target = document.getElementById( 'mc' ).style.display = "none";
		document.getElementById( 'mc' ).value="";
	}
}

function chooseProvinciaCattura(provincia) {
	if (provincia == 'NA') {
		document.getElementById( 'comuneCatturaNA' ).style.display = "";
		document.getElementById( 'comuneCatturaSA' ).style.display = "none";
		document.getElementById( 'comuneCatturaCE' ).style.display = "none";
	}	
	else if (provincia == 'SA') {
		document.getElementById( 'comuneCatturaNA' ).style.display = "none";
		document.getElementById( 'comuneCatturaSA' ).style.display = "";
		document.getElementById( 'comuneCatturaCE' ).style.display = "none";
	}
	else if (provincia == 'CE') {
		document.getElementById( 'comuneCatturaNA' ).style.display = "none";
		document.getElementById( 'comuneCatturaSA' ).style.display = "none";
		document.getElementById( 'comuneCatturaCE' ).style.display = "";
	}
	else if (provincia == 'X') {
		document.getElementById( 'comuneCatturaNA' ).style.display = "none";
		document.getElementById( 'comuneCatturaSA' ).style.display = "none";
		document.getElementById( 'comuneCatturaCE' ).style.display = "none";
	}
	
}


function chooseProvinciaDetenzione(provincia) {
	if (provincia == 'NA') {
		document.getElementById( 'comuneDetenzioneNA' ).style.display = "";
		document.getElementById( 'comuneDetenzioneSA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneCE' ).style.display = "none";
	}	
	else if (provincia == 'SA') {
		document.getElementById( 'comuneDetenzioneNA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneSA' ).style.display = "";
		document.getElementById( 'comuneDetenzioneCE' ).style.display = "none";
	}
	else if (provincia == 'CE') {
		document.getElementById( 'comuneDetenzioneNA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneSA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneCE' ).style.display = "";
	}
	else if (provincia == 'X') {
		document.getElementById( 'comuneDetenzioneNA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneSA' ).style.display = "none";
		document.getElementById( 'comuneDetenzioneCE' ).style.display = "none";
	}
}


function chooseProvinciaReimmissione(provincia) {
	if (provincia == 'NA') {
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
	}	
	else if (provincia == 'SA') {
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
	}
	else if (provincia == 'CE') {
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "";
	}
	else if (provincia == 'X') {
		document.getElementById( 'comuneReimmissioneNA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneSA' ).style.display = "none";
		document.getElementById( 'comuneReimmissioneCE' ).style.display = "none";
	}
}








function chooseSpecie(numero) {	
	
	if (numero == 1) {
		document.getElementById( 'mammiferiCetacei' ).style.display = "";
		document.getElementById( 'rettiliTestuggini' ).style.display = "none";
		document.getElementById( 'selaci' ).style.display = "none";
	}
	else if (numero == 2) {
		document.getElementById( 'mammiferiCetacei' ).style.display = "none";
		document.getElementById( 'rettiliTestuggini' ).style.display = "";
		document.getElementById( 'selaci' ).style.display = "none";
	}
	else if (numero == 3) {
		document.getElementById( 'mammiferiCetacei' ).style.display = "none";
		document.getElementById( 'rettiliTestuggini' ).style.display = "none";
		document.getElementById( 'selaci' ).style.display = "";
	}
	else if (numero == 0) {
		document.getElementById( 'mammiferiCetacei' ).style.display = "none";
		document.getElementById( 'rettiliTestuggini' ).style.display = "none";
		document.getElementById( 'selaci' ).style.display = "none";		
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
		return "La lunghezza del codice fiscale non �\n"
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
	
	
	
	if (document.getElementById( 'numeroUfficialeD' ).style.display == "" && form.numeroUfficiale.value == '') {
		alert("Inserire il numero ufficiale dell'animale");			
		return false;
	}
	
	if (document.getElementById( 'mc' ).style.display == "" && form.mc.value == '') {
		alert("Inserire il microchip dell'animale");			
		return false;
	}
	
	
	if (form.specieSinantropo.value == '0') {
		alert("Inserire la classe dell'animale marino");			
		return false;
	}
	
	if (form.specieSinantropo.value == '1' && form.tipologiaSinantropoM.value == '0') {
		alert("Selezionare il tipo di mammifero/cetaceo");			
		return false;
	}
	
	if (form.specieSinantropo.value == '2' && form.tipologiaSinantropoRA.value == '0') {
		alert("Selezionare il tipo di rettile/testuggine");			
		return false;
	}
	
	if (form.specieSinantropo.value == '3' && form.tipologiaSinantropoU.value == '0') {
		alert("Selezionare il tipo di selace");			
		return false;
	}	
	
	if (form.sesso.value == 'X') {
		alert("Inserire il sesso del sinantropo");			
		return false;
	}	
			
	if (form.idEta.value == '-1') {
		alert("Selezionare l'eta''");	
		form.idEta.focus();
		return false;
	}	
		
	
	if (form.dataCattura.value == '') {
		alert("Inserire la data rinvenimento");		
		return false;
	}	
	
	if (form.provinciaCattura.value == 'X') {
		alert("Inserire la provincia del rinvenimento");			
		return false;
	}	
	
	if (form.provinciaCattura.value == 'CE' && form.comuneCatturaCE.value == '0') {
		alert("Selezionare il comune CE");			
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
	
	if (form.tipologiaDetentore.value != 0 && form.dataDetenzioneDa.value == '') {
		alert("Inserire la data di inizio detenzione");		
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
		
		
	//RIAPILITO CAMPI
	document.getElementById('numeroUfficialeD').disabled = false;
	document.getElementById('mc').disabled = false;
	document.getElementById('codiceIspra').disabled = false;


	attendere();
	return true;
	
}