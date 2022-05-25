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
	//alert('t2');
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
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esitoDateControl negativo
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
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esitoDateControl negativo
	*/
	if(mcDate==null){return true;}else
	if(campo.value == null || trim(campo.value) == "" || mcDate.value == null || trim(mcDate.value) == ""){
		return true;
	}else{
		
		if( (document.getElementById('flagProvenienzaFuoriRegione') != null && document.getElementById('flagProvenienzaFuoriRegione').checked) || 
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
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esitoDateControl negativo
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
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esitoDateControl negativo
	*/
	if(campo.value == null || trim(campo.value) == "" || regDate.value == null || trim(regDate.value) == ""){
		return true;
	}
	//alert(document.getElementById('flagProvenienzaFuoriRegione').value);
	
	// se il cane proviene da fuori regione
	if ( (document.getElementById('flagProvenienzaFuoriRegione') != null && document.getElementById('flagProvenienzaFuoriRegione').checked) ||
		 (document.getElementById('flagSindacoFuoriRegione') != null && document.getElementById('flagSindacoFuoriRegione').checked) ) {		
		// se il controllo riguarda il campo 'Data rilascio passaporto'
		if (campo.getAttribute("labelcampo", 0) == "Data rilascio passaporto") {				
			// non facciamo nessun controllo
	//		alert('passaporto');
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
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esitoDateControl negativo
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
 * Tipo controllo T21: campo inferiore alla data di riferimento nomecampo "datatocheck"
 * @arg1 = campo su cui effettuare il controllo
 */
function T21(campo){
	//alert("t21");
	var datatoCheck = elementoConAttributo("nomecampo","datatocheck");
	//alert(decessoData.value);
	
	/*
	Manca almeno uno dei parametri di confronto, 
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esitoDateControl negativo
	*/
	if(campo.value == null || trim(campo.value) == "" || datatoCheck.value == null || trim(datatoCheck.value) == ""){
		return true;
	}
	else{
	//	alert (giorni_differenza(decessoData.value, campo.value));
		if(giorni_differenza(datatoCheck.value, campo.value)<=0 ){
			return true;
		}
		else{
			message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere inferiore a " + datatoCheck.getAttribute("labelcampo", 0) + ".\r\n";
			return false;
		}
	}
		
}


/**
 * Tipo controllo T18 : campo inferiore alla data attuale entro un certo limite
 * @arg1 = campo su cui effettuare il controllo
 */
function T18(campo){
	
	var currentDate = elementoConAttributo("nomecampo","datacorrente");
	
	/*
	Manca almeno uno dei parametri di confronto, 
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esitoDateControl negativo
	*/
	if(campo.value == null || trim(campo.value) == "" || currentDate.value == null || trim(currentDate.value) == ""){
		return true;
	}
	else{
		var limite_giorni = 7;
		if(giorni_differenza(campo.value, currentDate.value) <= limite_giorni ){
			return true;
		}
		else{
			message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere entro i " + limite_giorni + " giorni dalla data odierna.\r\n";
			return false;
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
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esitoDateControl negativo
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
 * Tipo controllo T20 : campo compreso nell'anno corrente
 */
function T20(campo){
	
	
	var currentTime = new Date();
	var year = currentTime.getFullYear();

	
	var data = campo.value;
	var data_limite = '01/01/2014';
	var data_limite2 = '01/02/2014';

	var arr1 = data.split('/');
	var arr2 = data_limite.split('/');
	var arr3 = data_limite2.split('/');

	var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
	var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
	var d3 = new Date(arr3[2],arr3[1]-1,arr3[0]);
	var oggi = new Date();

	var r1 = d1.getTime();
	var r2 = d2.getTime();
	var r3 = d3.getTime();
	var r4 = oggi.getTime();
	if(r1<r2 && r4>=r3)
	{
	var anno1 = parseInt(campo.value.substr(6),10);
	if (year!=anno1){
			message += "- " + campo.getAttribute("labelcampo", 0) + " deve essere nell'anno corrente ("+year+").\r\n";
			return false;
		}
		}
	return true;
}

/**
 * Tipo controllo T22 : campo non inferiore alla data odierna
 * @arg1 = campo su cui effettuare il controllo
 */
function T22(campo){
	var currentDate =  new Date();
	var anno = currentDate.getFullYear();
	var mese = ("0" + (currentDate.getMonth() + 1)).slice(-2) 
	var giorno = ("0" + (currentDate.getUTCDate() + 1)).slice(-2) 
	
	var dataOdierna = giorno+"/"+mese+"/"+anno;

	/*
	Manca almeno uno dei parametri di confronto, 
	pertanto viene lasciata ad altri controlli la possibilit� di dare un esitoDateControl negativo
	*/
	if(campo.value == null || trim(campo.value) == "" || dataOdierna == null || trim(dataOdierna) == ""){
		return true;
	}
	else{
		
//		if((giorni_differenza(campo.value, currentDate.value) <= limite_giorni )&& (giorni_differenza(currentDate.value,campo.value)<=limite_giorni)){
		if((giorni_differenza(campo.value, dataOdierna) >=0 ) ){
			return true;
		}
		else{
			message += "- " + campo.getAttribute("label", 0) + " deve essere inferiore o uguale alla data odierna.\r\n";
			return false;
		}
	}
		
}

/**
 * Funzione principale di controllo: lancia a seconda dei casi i vari tipi di controlli definiti sopra
 * 
 */
function lanciaControlloDate(){
	
	var esitoDateControl = true;
	var formTestDateControl = true;
	var campi = elementiConAttributo("tipocontrollo");
	var campo;
	var i=0;
	
	while(campo = campi[i++]){
		//alert(campo);
		var controlli = campo.getAttribute("tipocontrollo", 0).split(",");
		//alert(controlli.length);
		ciclo_controlli:
			for(n = 0 ; n < controlli.length; n++){
			//	alert(controlli[n]);
				switch(controlli[n]){
		
					case "T2" :
						//alert('t2_');
						esitoDateControl = T2(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
					
					case "T6" : 
						esitoDateControl = T6(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
					
					case "T7" : 
						esitoDateControl = T7(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
						
					case "T9" : 
						esitoDateControl = T9(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
						
					case "T10" : 
						esitoDateControl = T10(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
						
					case "T11" : 
						//alert('11');
						esitoDateControl = T11(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
						
					case "T13" : 
						esitoDateControl = T13(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
						
					case "T14" : 
						esitoDateControl = T14(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
						
					case "T15" : 
						esitoDateControl = T15(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
						
					case "T16" : 
						esitoDateControl = T16(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
						
					case "T17" : 
						esitoDateControl = T17(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
						
					case "T18" : 
						esitoDateControl = T18(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
			
					case "T19" : 
						esitoDateControl = T19(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
						
					case "T20" : 
						esitoDateControl = T20(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
						
					case "T21" : 
						esitoDateControl = T21(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
						
					case "T22" :
						esitoDateControl = T22(campo);
						formTestDateControl = esitoDateControl && formTestDateControl;
						if (!esitoDateControl){break ciclo_controlli;}
						break;
				}//fine switch sul singolo controllo

			}//fine for sui controlli 

	}//fine while sui campi
      return esitoDateControl;
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