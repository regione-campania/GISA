/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function formRandagio(checkbox)
{
	if(checkbox.checked)
	{
		document.getElementById('proprietarioCap').disabled=true;
		document.getElementById('proprietarioCap').value="";
		document.getElementById('proprietarioNome').disabled=true;
		document.getElementById('proprietarioNome').value="";
		document.getElementById('proprietarioCognome').disabled=true;
		document.getElementById('proprietarioCognome').value="";
		document.getElementById('proprietarioCodiceFiscale').disabled=true;
		document.getElementById('proprietarioCodiceFiscale').value="";
		document.getElementById('proprietarioIndirizzo').disabled=true;
		document.getElementById('proprietarioIndirizzo').value="";
		document.getElementById('proprietarioComune').disabled=true;
		document.getElementById('proprietarioComune').value="";
		document.getElementById('proprietarioProvincia').disabled=true;
		document.getElementById('proprietarioProvincia').value="";
		document.getElementById('proprietarioTelefono').disabled=true;
		document.getElementById('proprietarioTelefono').value="";
		document.getElementById('proprietarioDocumento').disabled=true;
		document.getElementById('proprietarioDocumento').value="";
		document.getElementById('comuneSindaco').disabled=false;
		document.getElementById('trProprietarioCap').style.display="none";
		document.getElementById('trProprietarioNome').style.display="none";
		document.getElementById('trProprietarioCognome').style.display="none";
		document.getElementById('trProprietarioCodiceFiscale').style.display="none";
		document.getElementById('trProprietarioIndirizzo').style.display="none";
		document.getElementById('trProprietarioComune').style.display="none";
		document.getElementById('trProprietarioProvincia').style.display="none";
		document.getElementById('trProprietarioTelefono').style.display="none";
		document.getElementById('trProprietarioDocumento').style.display="none";
		document.getElementById('trComuneSindaco').style.display="block";
		//document.getElementById('datiRitrovamento').style.display="block";
	}
	else
	{
		document.getElementById('proprietarioCap').disabled=false;
		document.getElementById('proprietarioNome').disabled=false;
		document.getElementById('proprietarioCognome').disabled=false;
		document.getElementById('proprietarioCodiceFiscale').disabled=false;
		document.getElementById('proprietarioIndirizzo').disabled=false;
		document.getElementById('proprietarioComune').disabled=false;
		document.getElementById('proprietarioProvincia').disabled=false;
		document.getElementById('proprietarioTelefono').disabled=false;
		document.getElementById('proprietarioDocumento').disabled=false;
		document.getElementById('comuneSindaco').disabled=true;
		document.getElementById('comuneSindaco').value="0";
		document.getElementById('trProprietarioCap').style.display="block";
		document.getElementById('trProprietarioNome').style.display="block";
		document.getElementById('trProprietarioCognome').style.display="block";
		document.getElementById('trProprietarioCodiceFiscale').style.display="block";
		document.getElementById('trProprietarioIndirizzo').style.display="block";
		document.getElementById('trProprietarioComune').style.display="block";
		document.getElementById('trProprietarioProvincia').style.display="block";
		document.getElementById('trProprietarioTelefono').style.display="block";
		document.getElementById('trProprietarioDocumento').style.display="block";
		document.getElementById('trComuneSindaco').style.display="none";
		//document.getElementById('datiRitrovamento').style.display="none";
	}
}


function checkForm()
{
	
	if((document.getElementById("idSpecie").value=="1" || document.getElementById("idSpecie").value=="2") && document.getElementById("sesso").value=="ND")
	{
		alert("Selezionare il sesso per animali di specie cane e gatto");
		document.getElementById('sesso').focus();
		return false;	
	}
	if(document.getElementById("dataMorte").value=="")
	{
		alert("Inserire la data del decesso");
		document.getElementById('dataMorte').focus();
		return false;	
	}
	if(document.getElementById("idSpecie").value=="3")
	{
		var idDaControllare = "specieSinantropoS";
		var primaSpecie = "uccello";
		var secondaSpecie = "mammifero";
		var terzaSpecie = "rettile/anfibio";
		var primaSpecieId = "uccelli";
		var secondaSpecieId = "mammiferi";
		var terzaSpecieId = "rettiliAnfibi";
		if(document.getElementById('idSottotipologia').value=="mar")
		{
			idDaControllare = "specieSinantropoM";
			primaSpecie = "mammiferi/cetacei";
			secondaSpecie = "rettile/testuggine";
			terzaSpecie = "selaci";
			primaSpecieId = "mammiferiCetacei";
			secondaSpecieId = "rettiliTestuggini";
			terzaSpecieId = "selaci";
		}
		else if(document.getElementById('idSottotipologia').value=="zoo")
		{
			primaSpecie = "uccello";
			secondaSpecie = "mammifero";
			terzaSpecie = "rettile/anfibio";
			idDaControllare = "specieSinantropoZ";
			primaSpecieId = "uccelliZ";
			secondaSpecieId = "mammiferiZ";
			terzaSpecieId = "rettiliAnfibiZ";
		}
		if(document.getElementById(idDaControllare).value=="0")
		{
			alert("Inserire la classe del Sinantropo/Marino/Zoo");
			document.getElementById(idDaControllare).focus();
			return false;
		}
		if(document.getElementById(idDaControllare).value=="1" && document.getElementById(primaSpecieId).value=="0")
		{
			alert("Selezionare il tipo di " + primaSpecie);
			document.getElementById(primaSpecieId).value="0";
			document.getElementById(secondaSpecieId).value="0";
			document.getElementById(terzaSpecieId).value="0";
			document.getElementById(primaSpecieId).focus();
			return false;	
		}
		if( document.getElementById(idDaControllare).value=="2" && document.getElementById(secondaSpecieId).value=="0")
		{
			alert("Selezionare il tipo di " + secondaSpecie);	
			document.getElementById(primaSpecieId).value="0";
			document.getElementById(secondaSpecieId).value="0";
			document.getElementById(terzaSpecieId).value="0";
			document.getElementById(secondaSpecieId).focus();
			return false;	
		}
		if(document.getElementById(idDaControllare).value=="3" && document.getElementById(terzaSpecieId).value=="0")
		{
			alert("Selezionare il tipo di " + terzaSpecie);
			document.getElementById(primaSpecieId).value="0";
			document.getElementById(secondaSpecieId).value="0";
			document.getElementById(terzaSpecieId).value="0";
			document.getElementById(terzaSpecieId).focus();
			return false;	
		}
	}
	
	if(document.getElementById("idSpecie").value!="3")
	{
		var comuneSindaco = document.getElementById("comuneSindaco");
		if(document.getElementById("randagio").checked && comuneSindaco.value=="0")
		{
			alert("Selezionare il comune del sindaco proprietario");
			document.getElementById('comuneSindaco').focus();
			return false;	
		}
		var proprietarioNome = document.getElementById("proprietarioNome");
		if(!document.getElementById("randagio").checked && proprietarioNome.value=="")
		{
			alert("Inserire il nome del proprietario");
			document.getElementById('proprietarioNome').focus();
			return false;	
		}
		var proprietarioCognome = document.getElementById("proprietarioCognome");
		if(!document.getElementById("randagio").checked && proprietarioCognome.value=="")
		{
			alert("Inserire il cognome del proprietario");
			document.getElementById('proprietarioCognome').focus();
			return false;	
		}
		var proprietarioIndirizzo = document.getElementById("proprietarioIndirizzo");
		if(!document.getElementById("randagio").checked && proprietarioIndirizzo.value=="")
		{
			alert("Inserire l'indirizzo di residenza del proprietario");
			document.getElementById('proprietarioIndirizzo').focus();
			return false;	
		}
		var proprietarioComune = document.getElementById("proprietarioComune");
		if(!document.getElementById("randagio").checked && proprietarioComune.value=="")
		{
			alert("Inserire il comune di residenza del proprietario");
			document.getElementById('proprietarioComune').focus();
			return false;	
		}
		
	}
	
	var provincia = document.getElementById("provinciaRitrovamento");
	if(//document.getElementById("randagio").checked && 
			provincia.value=="")
	{
		alert("Selezionare la provincia del ritrovamento");
		document.getElementById('provinciaRitrovamento').focus();
		return false;	
	}
	var comune = document.getElementById("comuneRitrovamentoChooser"+provincia.value);
	if(//document.getElementById("randagio").checked && 
			provincia.value!="" && comune.value=="0")
	{
		alert("Selezionare il comune del ritrovamento");
		document.getElementById('provinciaRitrovamento').focus();
		return false;	
	}
	if(//document.getElementById("randagio").checked && 
			document.getElementById("indirizzoRitrovamento").value=="")
	{
		alert("Inserire l'indirizzo del ritrovamento");
		document.getElementById('indirizzoRitrovamento').focus();
		return false;	
	}
	
	if(!controllaDataAnnoCorrente(document.getElementById('dataMorte'),'Data del decesso'))
	{
		return false;
	}
	return true;
}

function chooseProvinciaRitrovamento(provincia) {
	if (provincia == 'BN') {
		document.getElementById( 'comuneRitrovamentoBN' ).style.display = "";
		document.getElementById( 'comuneRitrovamentoNA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoSA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoCE' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoAV' ).style.display = "none";
		
	}
	else if (provincia == 'NA') {
		document.getElementById( 'comuneRitrovamentoBN' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoNA' ).style.display = "";
		document.getElementById( 'comuneRitrovamentoSA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoCE' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoAV' ).style.display = "none";
	}	
	else if (provincia == 'AV') {
		document.getElementById( 'comuneRitrovamentoBN' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoNA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoSA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoCE' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoAV' ).style.display = "";
	}
	else if (provincia == 'SA') {
		document.getElementById( 'comuneRitrovamentoBN' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoNA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoSA' ).style.display = "";
		document.getElementById( 'comuneRitrovamentoCE' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoAV' ).style.display = "none";
	}
	else if (provincia == 'CE') {
		document.getElementById( 'comuneRitrovamentoBN' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoNA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoSA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoCE' ).style.display = "";
		document.getElementById( 'comuneRitrovamentoAV' ).style.display = "none";
	}
	else if (provincia == 'X') {
		document.getElementById( 'comuneRitrovamentoBN' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoNA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoSA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoCE' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoAV' ).style.display = "none";
	}
	
}

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

function chooseSpecieZ(numero) {	
	
	if (numero == 1) {
		document.getElementById( 'uccelliZ' ).style.display = "";
		document.getElementById( 'mammiferiZ' ).style.display = "none";
		document.getElementById( 'rettiliAnfibiZ' ).style.display = "none";
	}
	else if (numero == 2) {
		document.getElementById( 'uccelliZ' ).style.display = "none";
		document.getElementById( 'mammiferiZ' ).style.display = "";
		document.getElementById( 'rettiliAnfibiZ' ).style.display = "none";
	}
	else if (numero == 3) {
		document.getElementById( 'uccelliZ' ).style.display = "none";
		document.getElementById( 'mammiferiZ' ).style.display = "none";
		document.getElementById( 'rettiliAnfibiZ' ).style.display = "";
	}
	else if (numero == 0) {
		document.getElementById( 'uccelliZ' ).style.display = "none";
		document.getElementById( 'mammiferiZ' ).style.display = "none";
		document.getElementById( 'rettiliAnfibiZ' ).style.display = "none";		
	}
	
}

function chooseSpecieM(numero) {	
	if (numero == 1) {
		document.getElementById( 'selaci' ).style.display = "none";
		document.getElementById( 'mammiferiCetacei' ).style.display = "";
		document.getElementById( 'rettiliTestuggini' ).style.display = "none";
	}
	else if (numero == 2) {
		document.getElementById( 'selaci' ).style.display = "none";
		document.getElementById( 'mammiferiCetacei' ).style.display = "none";
		document.getElementById( 'rettiliTestuggini' ).style.display = "";
	}
	else if (numero == 3) {
		document.getElementById( 'selaci' ).style.display = "";
		document.getElementById( 'mammiferiCetacei' ).style.display = "none";
		document.getElementById( 'rettiliTestuggini' ).style.display = "none";
	}
	else if (numero == 0) {
		document.getElementById( 'selaci' ).style.display = "none";
		document.getElementById( 'mammiferiCetacei' ).style.display = "none";
		document.getElementById( 'rettiliTestuggini' ).style.display = "none";		
	}
	
}