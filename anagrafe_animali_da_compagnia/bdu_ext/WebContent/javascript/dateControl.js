/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * Elenco di tutti i tipi di controlli sulle date
 * 
 */

/**
 * Tipo controllo T2 : campo non nullo ed espresso nel formato dd/mm/yyyy
 * @arg1 = campo su cui effettuare il controllo
 */
function T2(campo){
	if(campo.value != null && trim(campo.value) != ""){
		var pattern = /\d{2}\/\d{2}\/\d{4}/;
		var res = campo.value.search(pattern);
		if(campo.value.length == "10" && res == "0"){
			return true;
		}
		else{
			message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere nel formato dd/mm/yyyy.\r\n";
			return false;
		}
	}
	else{
		message += "- " + campo.getAttribute("labelcampo", 0) + " richiesta.\r\n";
		return false;
	}
	
}


/**
 * Tipo controllo T6 : campo superiore o uguale alla data di chippatura
 * @arg1 = campo su cui effettuare il controllo
 */
function T6(campo){
	
	var mcDate = elementoConAttributo("nomecampo","chippatura");
	
	/*
	Manca almeno uno dei parametri di confronto, 
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esito negativo
	*/
	if(campo==null || mcDate == null)
	{
		return true;
	}
	else
	if( campo.value == null || trim(campo.value) == "" || mcDate.value == null || trim(mcDate.value) == ""){
		return true;
	}
	else{
		if(giorni_differenza(mcDate.value,campo.value)>=0 ){
			return true;
		}
		else{
			message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere superiore o uguale a " + mcDate.getAttribute("labelcampo", 0) + ".\r\n";
			return false;
		}
	}
	
	
}

/**
 * Tipo controllo T7 : campo superiore alla data di chippatura entro un certo limite
 * @arg1 = campo su cui effettuare il controllo
 */
function T7(campo) {
	
	var mcDate = elementoConAttributo("nomecampo","chippatura");
	
	/*
	Manca almeno uno dei parametri di confronto, 
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esito negativo
	*/
	if(mcDate==null){return true;}else
	if(campo.value == null || trim(campo.value) == "" || mcDate.value == null || trim(mcDate.value) == ""){
		return true;
	}else{
		
		if( (document.getElementById('provenienza') != null && document.getElementById('provenienza').checked) || 
			 document.getElementById('serialNumber') == null || 
			 document.getElementById('serialNumber').value == null || 
			 trim(document.getElementById('serialNumber').value) == ""){
			return true;
		}
		
		var limite_giorni = 30;
		
		if(giorni_differenza(mcDate.value,campo.value) <= limite_giorni){
			return true;
		}
		else{
			message += "- La differenza tra " + campo.getAttribute("labelcampo", 0) + " e " + mcDate.getAttribute("labelcampo", 0) + " non deve superare " + limite_giorni + " giorni.\r\n";
			return false;
		}
	}
	
	

	
}

/**
 * Tipo controllo T9 : campo superiore o uguale alla data di nascita
 * @arg1 = campo su cui effettuare il controllo
 */
function T9(campo){
	
	var bornDate = elementoConAttributo("nomecampo","nascita");
	//alert(bornDate.value);
	/*
	Manca almeno uno dei parametri di confronto, 
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esito negativo
	*/
	if(campo.value == null || trim(campo.value) == "" || bornDate.value == null || trim(bornDate.value) == ""){
		return true;
	}
	else{
		if(giorni_differenza(bornDate.value, campo.value)>=0 ){
			return true;
		}
		else{
			message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere superiore o uguale a " + bornDate.getAttribute("labelcampo", 0) + ".\r\n";
			return false;
		}
	}
		
}

/**
 * Tipo controllo T10 : campo non obbligatorio, pertanto o � vuoto o se � valorizzato deve essere espresso nel formato dd/mm/yyyy
 * @arg1 = campo su cui effettuare il controllo
 */
function T10(campo){
	
	if(campo.value != null && trim(campo.value) != ""){
		var pattern = /\d{2}\/\d{2}\/\d{4}/;
		var res = campo.value.search(pattern);
		if(campo.value.length == "10" && res == "0"){
			return true;
		}
		else{
			message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere nel formato dd/mm/yyyy.\r\n";
			return false;
		}
	}
	else{
		return true;
	}
	
}


/**
 * Tipo controllo T11 : campo superiore o uguale alla data di registrazione
 * @arg1 = campo su cui effettuare il controllo
 */
function T11(campo){

	
	var regDate = elementoConAttributo("nomecampo","registrazione");

	/*
	Manca almeno uno dei parametri di confronto, 
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esito negativo
	*/
	if(campo.value == null || trim(campo.value) == "" || regDate.value == null || trim(regDate.value) == ""){
		return true;
	}
	
	// se il cane proviene da fuori regione
	if ( (document.getElementById('provenienza') != null && document.getElementById('provenienza').checked) ||
		 (document.getElementById('provenienzaSFR') != null && document.getElementById('provenienzaSFR').checked) ) {		
		// se il controllo riguarda il campo 'Data rilascio passaporto'
		if (campo.getAttribute("labelcampo", 0) == "Data rilascio passaporto") {				
			// non facciamo nessun controllo
			return true;
		}
	}
	else{
		if(giorni_differenza(regDate.value,campo.value)>=0 ){
			//alert('reg data' +regDate.value);
			//alert('ster data' +campo.value);
			return true;
		}
		else{
			if (campo.getAttribute("labelcampo", 0)=="Data Sterilizzazione"){
				message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere superiore o uguale a " + regDate.getAttribute("labelcampo", 0) + ".\r\n";
				message += "- ATTENZIONE! Per inserire ''Data Sterilizzazione'' anteriore a ''Data Registrazione'' e' necessario rivolgersi all'Help desk";
			}
			else
			message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere superiore o uguale a " + regDate.getAttribute("labelcampo", 0) + ".\r\n";
			return false;
		}
	}
	
		
}

/**
 * Tipo controllo T13 : campo superiore o uguale alla prima data di prelievo leishmaniosi
 * @arg1 = campo su cui effettuare il controllo
 */
function T13(campo){
	
	if (campo.value == null || trim(campo.value) == ""){ // il campo da confrontare con la data prelievo � vuoto, non sono necessari controlli
		return true;
	}
	else{
		
		var prelievoLeishDate = elementoConAttributo("nomecampo","leishmaniosiDataPrelievo1");
		
		if( prelievoLeishDate.value == null || trim(prelievoLeishDate.value) == "" ){
			message += "- " + campo.getAttribute("labelcampo", 0) + " prevede la valorizzazione di " + prelievoLeishDate.getAttribute("labelcampo", 0) + ".\r\n";
			return false;
		}
		else{
			if(giorni_differenza(prelievoLeishDate.value,campo.value)>=0 ){
				return true;
			}
			else{
				message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere superiore o uguale a " + prelievoLeishDate.getAttribute("labelcampo", 0) + ".\r\n";
				return false;
			}
		}
	}
	
}


/**
 * Tipo controllo T14 : campo superiore o uguale alla seconda data di prelievo leishmaniosi
 * @arg1 = campo su cui effettuare il controllo
 */
function T14(campo){
	
	if (campo.value == null || trim(campo.value) == ""){ // il campo da confrontare con la data prelievo � vuoto, non sono necessari controlli
		return true;
	}
	else{
		
		var prelievoLeishDate = elementoConAttributo("nomecampo","leishmaniosiDataPrelievo2");
		
		if( prelievoLeishDate.value == null || trim(prelievoLeishDate.value) == "" ){
			message += "- " + campo.getAttribute("labelcampo", 0) + " prevede la valorizzazione di " + prelievoLeishDate.getAttribute("labelcampo", 0) + ".\r\n";
			return false;
		}
		else{
			if(giorni_differenza(prelievoLeishDate.value,campo.value)>=0 ){
				return true;
			}
			else{
				message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere superiore o uguale a " + prelievoLeishDate.getAttribute("labelcampo", 0) + ".\r\n";
				return false;
			}
		}
	}
	
}


/**
 * Tipo controllo T15 : campo superiore o uguale alla terza data di prelievo leishmaniosi
 * @arg1 = campo su cui effettuare il controllo
 */
function T15(campo){
	
	if (campo.value == null || trim(campo.value) == ""){ // il campo da confrontare con la data prelievo � vuoto, non sono necessari controlli
		return true;
	}
	else{
		
		var prelievoLeishDate = elementoConAttributo("nomecampo","leishmaniosiDataPrelievo3");
		
		if( prelievoLeishDate.value == null || trim(prelievoLeishDate.value) == "" ){
			message += "- " + campo.getAttribute("labelcampo", 0) + " prevede la valorizzazione di " + prelievoLeishDate.getAttribute("labelcampo", 0) + ".\r\n";
			return false;
		}
		else{
			if(giorni_differenza(prelievoLeishDate.value,campo.value)>=0 ){
				return true;
			}
			else{
				message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere superiore o uguale a " + prelievoLeishDate.getAttribute("labelcampo", 0) + ".\r\n";
				return false;
			}
		}
	}
	
}


/**
 * Tipo controllo T16 : campo superiore o uguale alla quarta data di prelievo leishmaniosi
 * @arg1 = campo su cui effettuare il controllo
 */
function T16(campo){
	
	if (campo.value == null || trim(campo.value) == ""){ // il campo da confrontare con la data prelievo � vuoto, non sono necessari controlli
		return true;
	}
	else{
		
		var prelievoLeishDate = elementoConAttributo("nomecampo","leishmaniosiDataPrelievo4");
		
		if( prelievoLeishDate.value == null || trim(prelievoLeishDate.value) == "" ){
			message += "- " + campo.getAttribute("labelcampo", 0) + " prevede la valorizzazione di " + prelievoLeishDate.getAttribute("labelcampo", 0) + ".\r\n";
			return false;
		}
		else{
			if(giorni_differenza(prelievoLeishDate.value,campo.value)>=0 ){
				return true;
			}
			else{
				message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere superiore o uguale a " + prelievoLeishDate.getAttribute("labelcampo", 0) + ".\r\n";
				return false;
			}
		}
	}
	
}


/**
 * Tipo controllo T17 : campo superiore alla data di nascita
 * @arg1 = campo su cui effettuare il controllo
 */
function T17(campo){
	
	var bornDate = elementoConAttributo("nomecampo","nascita");
	
	/*
	Manca almeno uno dei parametri di confronto, 
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esito negativo
	*/
	if(campo.value == null || trim(campo.value) == "" || bornDate.value == null || trim(bornDate.value) == ""){
		return true;
	}
	else{
		if(giorni_differenza(bornDate.value, campo.value)>0 ){
			return true;
		}
		else{
			message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere superiore a " + bornDate.getAttribute("labelcampo", 0) + ".\r\n";
			return false;
		}
	}
		
}


/**
 * Tipo controllo T18 : campo inferiore alla data attuale entro un certo limite
 * @arg1 = campo su cui effettuare il controllo
 */
function T18(campo, roleIdUtente, roleIdLP)
{
	if(roleIdUtente!=roleIdLP)
	{
		return true;
	}
	else
	{
	
		
		var currentDate = document.getElementById("datacorrente");
	
		/*
		Manca almeno uno dei parametri di confronto, 
		pertanto viene lasciata ad altri controlli la possibilit� di dare un esito negativo
		 */
		if(campo.value == null || trim(campo.value) == "" || currentDate.value == null || trim(currentDate.value) == "")
		{
			return true;
		}
		else
		{
			var limite_giorni = 7;
			if(giorni_differenza(campo.value, currentDate.value) <= limite_giorni )
			{
				return true;
			}
			else
			{
				message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere entro i " + limite_giorni + " giorni dalla data odierna.\r\n";
				return false;
			}
		}
	}
}


/**
 * Tipo controllo T19 : campo inferiore ad una data di 30 giorni come limite inferiore 
 * @arg1 = campo su cui effettuare il controllo
 */
function T19(campo){
	
	var currentDate = elementoConAttributo("nomecampo","datacorrente");
	/*
	Manca almeno uno dei parametri di confronto, 
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esito negativo
	*/
	if(campo.value == null || trim(campo.value) == "" || currentDate.value == null || trim(currentDate.value) == ""){
		return true;
	}
	else{
		var limite_giorni = 30;
//		if((giorni_differenza(campo.value, currentDate.value) <= limite_giorni )&& (giorni_differenza(currentDate.value,campo.value)<=limite_giorni)){
		if((giorni_differenza(campo.value, currentDate.value) <= limite_giorni ) ){
			return true;
		}
		else{
			message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere entro i " + limite_giorni + " giorni dalla data decreto.\r\n";
			return false;
		}
	}
		
}

/**
 * Tipo controllo T20 : campo inferiore alla data di apertura CC
 * @arg1 = campo su cui effettuare il controllo
 */
function T20(campo)
{
	var dataAperturaCC = document.getElementById('dataAperturaCC').value;
	if(dataAperturaCC!=null && dataAperturaCC!="" && dataAperturaCC != "null")
	{
		if((giorni_differenza(campo.value, dataAperturaCC) > 0 ) )
		{
			message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere maggiore o uguale della data di apertura CC (" + dataAperturaCC + ").\r\n";
			return false;
		}
		else
		{
			return true;
		}
	}
}

function T21(campo)
{
	var dataUltimaRegistrazione = document.getElementById('dataUltimaRegistrazione');
	
	if(dataUltimaRegistrazione.value == null || trim(dataUltimaRegistrazione.value) == "")
	{
		return true;
	}
	
	if(campo.value == null || trim(campo.value) == "" )
	{
		return true;
	}
	
	if(giorni_differenza(dataUltimaRegistrazione.value,campo.value)<0 
			&& ( !$('#flagRegistrazioneForzata').length || !document.getElementById("flagRegistrazioneForzata").checked ) )
	{
		message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere maggiore o uguale della data ultima registrazione (" + dataUltimaRegistrazione.value + ").\r\n";
		return false;
	}
	return true;
}

function T22(campo,roleIdUtente)
{
	var dataUltimaRegistrazione = document.getElementById('dataUltimaRegistrazione');
	
	if(dataUltimaRegistrazione.value == null || trim(dataUltimaRegistrazione.value) == "")
	{
		return true;
	}
	
	if(campo.value == null || trim(campo.value) == "" )
	{
		return true;
	}
	
	if(giorni_differenza(dataUltimaRegistrazione.value,campo.value)<0 )
	{
		if(roleIdUtente!=5  && roleIdUtente!=6)
		{
			message += "- " + campo.getAttribute("labelcampo", 0) + " è antecedente alla data ultima registrazione (" + dataUltimaRegistrazione.value + "). Contattare l'Help Desk per inserirla.\r\n";
			return false;
		}
		else
		{
			if(confirm(campo.getAttribute("labelcampo", 0) + " è antecedente alla data ultima registrazione (" + dataUltimaRegistrazione.value + "). Questo potrebbe causare inconsistenze nella sequenza di registrazioni dell'animale. Continuare lo stesso?.\r\n"))
				return true;
			else
				return false;
		}
	}
	return true;
}

function T23(campo)
{
	if(campo.value == null || trim(campo.value) == "" || trim(campo.value) == -1 || trim(campo.value) == '0' || trim(campo.value) == "null" )
	{
		message += "- Selezionare il proprietario importandolo, selezionandolo o inserendolo nuovo.\r\n";
		return false;
	}
	
	return true;
}


/**
 * Funzione principale di controllo: lancia a seconda dei casi i vari tipi di controlli definiti sopra
 * 
 */
function lanciaControlloDate(roleIdUtente, roleIdLP){
	
	var esito;
	var campi = elementiConAttributo("tipocontrollo"); 
	var campo;
	var i=0;
	while(campo = campi[i++]){
		//alert(campo);
		var controlli = campo.getAttribute("tipocontrollo", 0).split(",");
		ciclo_controlli:
			for(n = 0 ; n < controlli.length; n++){
				switch(controlli[n]){
		
					case "T2" :
						//alert('t2');
						esito = T2(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
					
					case "T6" : 
						esito = T6(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
					
					case "T7" : 
						esito = T7(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
						
					case "T9" : 
						esito = T9(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
						
					case "T10" : 
						esito = T10(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
						
					case "T11" : 
						//alert('11');
						esito = T11(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
						
					case "T13" : 
						esito = T13(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
						
					case "T14" : 
						esito = T14(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
						
					case "T15" : 
						esito = T15(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
						
					case "T16" : 
						esito = T16(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
						
					case "T17" : 
						esito = T17(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
						
					case "T18" : 
						esito = T18(campo, roleIdUtente, roleIdLP);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
			
					case "T19" : 
						esito = T19(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
						
					case "T20" : 
						esito = T20(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
						
					case "T21" : 
						esito = T21(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
					case "T22" : 
						esito = T22(campo,roleIdUtente);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
					case "T23" : 
						esito = T23(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
					case "T24" : 
						esito = T24(campo);
						formTest = esito && formTest;
						if (!esito){break ciclo_controlli;}
						break;
				}//fine switch sul singolo controllo

			}//fine for sui controlli

	}//fine while sui campi
      
}

/**
 * Funzione che restituisce i campi su cui effettuare il controllo. 
 * Tali campi sono di tipo input e hanno come attributo quello passato in input
 * @arg1 = nome attributo
 */
function elementiConAttributo(attr){

	var campi = document.getElementsByTagName("input");
	var campo;
	var campiDaControllare = new Array();
	var i;
	var k;
		
	try{
		i=0;
		k=0;
		ciclo_esterno:
		while (campo = campi[i++]) {

			/* Se il campo in questione � invisibile 
			 * (o equivalentemente lo � un campo che lo contiene) 
			 * non va sottoposto a controlli, pertanto si passa al campo successivo*/
			var nodo = campo;
			while(nodo){
				if(nodo.style != null && nodo.style.display != null && nodo.style.display == "none"){
					continue ciclo_esterno;
				}
				nodo = nodo.parentNode;
			}

			
			var attributi = campo.attributes;
			var attributo;
			var j=0;
			while(attributo = attributi[j++]){
				if(attributo.name == attr){
					campiDaControllare[k++] = campo;
				}
			}
		}
	}
	catch(ex){alert(ex);}
	
	return campiDaControllare;
}

/**
 * Funzione che restituisce il campo di tipo input avente attributo e relativo valore come quelli passati in input
 * @arg1 = nome attributo
 * @arg2 = valore attributo
 */
function elementoConAttributo(attr,val){

	var campi = document.getElementsByTagName("input");
	var campo;
	
	var i;
	var k;
		
	i=0;
	k=0;
	while (campo = campi[i++]) {
		var attributi = campo.attributes;
		var attributo;
		var j=0;
		while(attributo = attributi[j++]){
			if(attributo.name == attr && attributo.value == val){
				return campo;
			}
		}
	}		
}

/**
 * Funzione che restituisce i giorni di differenza esistenti tra le due date passate in input (data2 - data1)
 * @arg1 = prima data
 * @arg2 = seconda data
 */
function giorni_differenza(data1,data2){
	
	anno1 = parseInt(data1.substr(6),10);
	mese1 = parseInt(data1.substr(3, 2),10);
	giorno1 = parseInt(data1.substr(0, 2),10);
	anno2 = parseInt(data2.substr(6),10);
	mese2 = parseInt(data2.substr(3, 2),10);
	giorno2 = parseInt(data2.substr(0, 2),10);

	var dataok1=new Date(anno1, mese1-1, giorno1);
	var dataok2=new Date(anno2, mese2-1, giorno2);

	differenza = dataok2-dataok1;    
	giorni_diff = new String(differenza/86400000);
	//alert('diff');
	//alert(giorni_diff);
	return giorni_diff;
}

function trim(str){
	var newstr = "";
	newstr = str.replace(/^\s+|\s+$/g,"");
    return newstr;
} 


/**
 * Tipo controllo T24 : campo non superiore alla data di nascita entro un certo limite
 * @arg1 = campo su cui effettuare il controllo
 */
function T24(campo) {
	var dataNascita = elementoConAttributo("nomecampo","nascita");
		var limite_giorni = 60;
		
		if(giorni_differenza(dataNascita.value,campo.value) <= limite_giorni){
			return true;
		}
		else{
			message += "- La differenza tra " + campo.getAttribute("labelcampo", 0) + " e " + dataNascita.getAttribute("labelcampo", 0) + " non deve superare " + limite_giorni + " giorni.\r\n";
			return false;
		}
	}