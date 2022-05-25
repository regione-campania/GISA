/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

function trim(stringa){
	
	if(stringa!='')
	{
    while (stringa.substring(0,1) == ' '){
        stringa = stringa.substring(1, stringa.length);
    }
    while (stringa.substring(stringa.length-1, stringa.length) == ' '){
        stringa = stringa.substring(0,stringa.length-1);
    }
    while (stringa.substring(stringa.length-1, stringa.length) == '\n'){
        stringa = stringa.substring(0,stringa.length-1);
    }
	}
    return stringa;
}
function checkForm(form){
	formTest = true;
	message = "";
	flag=0;
	if (form.puntiFormali && form.puntiFormali.value!=""){
		if (isNaN(form.puntiFormali.value)){
			message += "- Valore errato per il campo Punteggio per le Non Conformit� Formali. Si prega di inserire solo cifre\r\n";
			formTest = false;
		}		 

	}    
//	if (form.assignedDate.value == "") {
//		message += label("check.nonconformita.data_richiesta.selezionato","- Controllare che il campo \"Data \" sia stato popolato\r\n");
//		formTest = false;
//	}
	
	if (document.getElementById('descrizione_impresa_sanzionata')!=null && document.getElementById('descrizione_impresa_sanzionata').value=='')
		{
		message += label("check.nonconformita.richiedente.selezionato","- Controllare di aver Inserito il campo Non Conformita a carico di'	\'\r\n");
		formTest = false;
		
		}
	if (document.getElementById('Provvedimenti1_1').options[0].selected==true && document.getElementById('Provvedimenti2_1').options[0].selected==true && document.getElementById('Provvedimenti3_1').options[0].selected==true)
	{
		message += label("check.nonconformita.richiedente.selezionato","- Controllare di aver Inserito almeno un tipo di non conformita\'	\'\r\n");
		formTest = false;
	}
	if( document.getElementById('stato_gravi').value!='true'  	||
			document.getElementById('stato_significative').value!='true'	||
			document.getElementById('stato_formali').value!='true'
	)
	{
		message += label("check.nonconformita.richiedente.selezionato","- Attenzione prima di Salvare Controlla di aver riempito in maniera corretta i tre tipi di non conformita\'	\'\r\n");
		formTest = false;
	}





	if (formTest == false) {
		isSave=false;
		alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
		return false;
	} else {
		if (document.getElementById('dosubmit')!=null)
		{
			document.getElementById('dosubmit').value = "1";

		}
		//loadModalWindow();
		isSave=true;
		
		return true;
	}
}
  //used when a new contact is added

  function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
 }

 function setAssignedDate(){
  resetAssignedDate();
  if (document.forms['addticket'].assignedTo.value > 0){
    document.forms['addticket'].assignedDate.value = document.forms['addticket'].currentDate.value;
  }
 }

 function resetAssignedDate(){
    document.forms['addticket'].assignedDate.value = '';
 }
 
 function setField(formField,thisValue,thisForm) {
  var frm = document.forms[thisForm];
  var len = document.forms[thisForm].elements.length;
  var i=0;
  for( i=0 ; i<len ; i++) {
    if (frm.elements[i].name.indexOf(formField)!=-1) {
      if(thisValue){
        frm.elements[i].value = "1";
      } else {
        frm.elements[i].value = "0";
      }
    }
  }
 }
 
  function selectUserGroups() {
    var orgId = document.forms['addticket'].orgId.value;
    var siteId = document.forms['addticket'].orgSiteId.value;
    if (orgId != '-1') {
      popUserGroupsListSingle('userGroupId','changeUserGroup', '&userId=<%= User.getUserRecord().getId() %>&siteId='+siteId);
    } else {
      alert(label("select.account.first",'You have to select an Account first'));
      return;
    }
  }
  
 
  
  function resetCarattere(){
  	
  		
  		elm1 = document.getElementById("dat1");
 		elm2 = document.getElementById("dat2");
 		elm3 = document.getElementById("dat3");
 		elm4 = document.getElementById("dat4");
 		elm5 = document.getElementById("dat5");
 		elm6 = document.getElementById("dat6");
 		
 		elm1.style.visibility = "hidden";
 		elm2.style.visibility = "hidden";
 		elm3.style.visibility = "hidden";
 		elm4.style.visibility = "hidden";
 		elm5.style.visibility = "hidden";
 		elm6.style.visibility = "hidden";
 		
 		document.addticket.Provvedimenti.selectedIndex=0;
 		document.addticket.NonConformitaAmministrative.selectedIndex=0;
 		document.addticket.NonConformitaPenali.selectedIndex=0;
 		
  }
  
  function selectCarattere(str, n, m, x){
  
 		elm1 = document.getElementById("dat"+n);
 		elm2 = document.getElementById("dat"+m);
 		
 		
 		if(str == "Provvedimenti"){
 			car = document.addticket.Provvedimenti.value;
 		}
 		if(str == "NonConformitaAmministrative"){
 			car = document.addticket.NonConformitaAmministrative.value;
 		}
 		if(str == "NonConformitaPenali"){
 			car = document.addticket.NonConformitaPenali.value;
 		}
 		
 		if(car == 9 || (car == 6 && str == "NonConformitaPenali")){
 			elm1.style.visibility = "visible";
 			elm2.style.visibility = "visible";
 			
 		}
 		else {
 			elm1.style.visibility = "hidden";
 			elm2.style.visibility = "hidden";
 			
 			if(x == 1){
 			document.forms['addticket'].descrizione1.value="";
 			}
 			if(x == 2){
 			document.forms['addticket'].descrizione2.value="";
 			}
 			if(x == 3){
 			document.forms['addticket'].descrizione3.value="";
 			}
 		}
 	  }

/**
 * CALCOLA IL PUNTEGGIO TOTALE DELLA NC.
 * IL TOTALE � OTTENUTO DALLA SOMMA DELLE FORMALI , 
 * SIGNIFICATIVE E GRAVI
 * @return
 */  
function calcolaTotale(){
	var f=0;
	var g=0;
	var h=0;

	
	 if(document.getElementById("puntiFormali")!=null && document.getElementById("puntiFormali").value=="")
		 f=0;
	 else{
		 if(document.getElementById("puntiFormali")!=null )
		 f = parseInt(document.getElementById("puntiFormali").value);

	 }
	if(document.getElementById("puntiSignificativi")!=null &&  document.getElementById("puntiSignificativi").value=="")
		g=0;
	else{
		 if(document.getElementById("puntiSignificativi")!=null )
		 g = parseInt(document.getElementById("puntiSignificativi").value);
			
	}
	if(document.getElementById("puntiGravi")!=null && document.getElementById("puntiGravi").value=="")
	h=0;
else{

	if(document.getElementById("puntiGravi")!=null)
		h = parseInt(document.getElementById("puntiGravi").value);
		
}
	 totale = document.getElementById("totale");
	if(totale!=null)
	totale.value=f+g+h;


	
}

/**
 * CALCOLA IL PUNTEGGIO IN MANIERA AUTOMATICA DELLE NC FORMALI
 * IL PUNTEGGIO VIENE INCREMENTATO DELLA VARIABILE IN INPUT PUNTEGGIO (7)
 * OGNI VOLTA CHE � STATA SELEZIONATA UNA NC E INSERITA PER ESSA UNA DESCRIZIONE.
 * SE NON VIENE RISPETTATA QUESTA CONDIZIONE IL PUNTEGGIO VIENE DECREMENTATO DI PUNTEGGIO (7)
 * @param idElementi
 * @param punteggio
 * @return
 */
function calcolaPuntiFormali(idElementi,punteggio)
{
	var msg = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
	if (document.getElementById('puntiFormali')!=null)
	{
		
	var punti = 0;
	for (j=0 ; j<idElementi;j++)
	{
	array = document.getElementById('Provvedimenti1_'+(parseInt(j)+1));
	for(i=0; i<array.length; i++){
		if(array[i].value!="-1" && array[i].selected==true && trim(document.getElementById('nonConformitaFormali_'+(parseInt(j)+1)).value)!='' && trim(document.getElementById('nonConformitaFormali_'+(parseInt(j)+1)).value)!=msg)
		{
			punti =parseInt(punti)+1;
		}
	}
	}
	punti = parseInt(punti) * parseInt(punteggio) ;
	document.getElementById('puntiFormali').value =  parseInt(punti);
	calcolaTotale();
	}
}


var punteggio_formali ;
var punteggio_signifi ;
var punteggio_gravi ;



function calcolaPuntiNonConformita(idElementi,prefisso_id_campo_provvedimenti,id_campo_punti,prefisso_id_campo_note_provvedimenti,tipocontrollo,datacontrollo)
{
	var msg = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
//	if (document.getElementById('puntiFormali')!=null)
	if (document.getElementById(id_campo_punti)!=null)
	{
		
	var punti = 0;
	for (j=0 ; j<idElementi;j++)
	{
//	array = document.getElementById('Provvedimenti1_'+(parseInt(j)+1));
	array = document.getElementById(prefisso_id_campo_provvedimenti+(parseInt(j)+1));
	
	for(i=0; i<array.length; i++){
		if(array[i].value!="-1" && array[i].selected==true && trim(document.getElementById(prefisso_id_campo_note_provvedimenti+(parseInt(j)+1)).value)!='' && trim(document.getElementById(prefisso_id_campo_note_provvedimenti+(parseInt(j)+1)).value)!=msg)
		{
			punti =parseInt(punti)+1;
		}
	}
	}
	
	DwrNonConformita.get_punteggio_non_conformita(tipocontrollo,datacontrollo,{callback:setPunteggioCallback,async:false});
	
	if (prefisso_id_campo_provvedimenti =='Provvedimenti1_')
	{
		punteggio = punteggio_formali;
	}
	if (prefisso_id_campo_provvedimenti =='Provvedimenti2_')
	{
		punteggio = punteggio_signifi;
	}
	if (prefisso_id_campo_provvedimenti =='Provvedimenti3_')
	{
		punteggio = punteggio_gravi;
	}
	
	punti = parseInt(punti) * parseInt(punteggio) ;
	document.getElementById(id_campo_punti).value =  parseInt(punti);
	calcolaTotale();
	}
}

function setPunteggioCallback(punteggioBean)
{
	punteggio_formali = punteggioBean.puntiformali;
	punteggio_signifi = punteggioBean.puntisignificativi;
	punteggio_gravi = punteggioBean.puntigravi ;
}

/**
 * CALCOLA IL PUNTEGGIO IN MANIERA AUTOMATICA DELLE NC SIGNIFICATIVE
 * IL PUNTEGGIO VIENE INCREMENTATO DELLA VARIABILE IN INPUT PUNTEGGIO (7)
 * OGNI VOLTA CHE � STATA SELEZIONATA UNA NC E INSERITA PER ESSA UNA DESCRIZIONE.
 * SE NON VIENE RISPETTATA QUESTA CONDIZIONE IL PUNTEGGIO VIENE DECREMENTATO DI PUNTEGGIO (7)
 * @param idElementi
 * @param punteggio
 * @return
 */
function calcolaPuntiSignificative(idElementi,punteggio)
{
	var msg = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
	if (document.getElementById('puntiSignificativi')!=null)
	{
	var punti = 0;
	for (j=0 ; j<idElementi;j++)
	{
	array = document.getElementById('Provvedimenti2_'+(parseInt(j)+1));
	for(i=0; i<array.length; i++){
		if(array[i].value!="-1" && array[i].selected==true && trim(document.getElementById('nonConformitaSignificative_'+(parseInt(j)+1)).value)!='' && trim(document.getElementById('nonConformitaSignificative_'+(parseInt(j)+1)).value)!=msg)
	 {
			punti =parseInt(punti)+1;
	 }
	}
	}

	punti = parseInt(punti) * parseInt(punteggio) ;
	document.getElementById('puntiSignificativi').value =  parseInt(punti);
	calcolaTotale();
	}
	
}

/**
 * CALCOLA IL PUNTEGGIO IN MANIERA AUTOMATICA DELLE NC GRAVI
 * IL PUNTEGGIO VIENE INCREMENTATO DELLA VARIABILE IN INPUT PUNTEGGIO (25)
 * OGNI VOLTA CHE � STATA SELEZIONATA UNA NC E INSERITA PER ESSA UNA DESCRIZIONE.
 * SE NON VIENE RISPETTATA QUESTA CONDIZIONE IL PUNTEGGIO VIENE DECREMENTATO DI PUNTEGGIO (25)
 * @param idElementi
 * @param punteggio
 * @return
 */
function calcolaPuntiGravi(idElementi,punteggio)
{
	var msg = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
	if (document.getElementById('puntiGravi')!=null)
	{
	var punti = 0;
	
	for (j=0 ; j<idElementi;j++)
	{
	array = document.getElementById('Provvedimenti3_'+(parseInt(j)+1));
	for(i=0; i<array.length; i++){
		if(array[i].value!="-1" && array[i].selected==true & trim(document.getElementById('nonConformitaGravi_'+(parseInt(j)+1)).value)!='' && trim(document.getElementById('nonConformitaGravi_'+(parseInt(j)+1)).value)!=msg)
	 {
			punti =parseInt(punti)+1;
	 }
	}
	}
	punti = parseInt(punti) * parseInt(punteggio) ;
	
	document.getElementById('puntiGravi').value =  parseInt(punti);
	calcolaTotale();
}
}

/**
 * FUNZIONE DI AGGIUNTA DI UNA NC FORMALE
 * @return
 */
function clonaNC_Formali(){
  	var maxElementi = 25;
  	var elementi;
  	var elementoClone;
  	var tableClonata;
  	var tabella;
  	var selezionato;
  	var x;
  	elementi = document.getElementById('elementi_nc_formali');
  	var msg_default = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
  	if (elementi.value < maxElementi)
  	{
	
  		
  	if  ( document.getElementById('Provvedimenti1_'+elementi.value).value != '-1' 				&& 
  		  trim(document.getElementById('nonConformitaFormali_'+elementi.value).value) != ''		&&
  		  trim(document.getElementById('nonConformitaFormali_'+elementi.value).value) != msg_default
  		)
  	{
  	document.getElementById('Provvedimenti1_'+elementi.value).disabled='true';
  	document.getElementById('nonConformitaFormali_'+elementi.value).readOnly='true';
  	elementi.value=parseInt(elementi.value)+1;
  	size = document.getElementById('size_nc_formali');
  	size.value=parseInt(size.value)+1;
  	var primo_elemento = document.getElementById('Provvedimenti1_1');
  	var indice = parseInt(elementi.value) - 1;
  	
  	if(primo_elemento!=null && x==null){
  		selezionato = document.getElementById('Provvedimenti1_1').selectedIndex;
  	}else if(primo_elemento==null && x!=null){
  		selezionato = x.selectedIndex;
  	}
  	var clonato = document.getElementById('nc_formali_1');
  	
  	/*clona riga vuota*/
  	clone=clonato.cloneNode(true);
	clone.getElementsByTagName('SELECT')[0].name = "Provvedimenti1_"+elementi.value;
  	clone.getElementsByTagName('SELECT')[0].id = "Provvedimenti1_"+String(indice+1);
  	clone.getElementsByTagName('SELECT')[0].value = '-1';
  	clone.getElementsByTagName('SELECT')[0].disabled ='';
  	clone.getElementsByTagName('LABEL')[0].innerHTML ="<b>"+elementi.value+"</b>";
  	
  	for (var k = 0; k<10; k++) {
  		if (clone.getElementsByTagName('SELECT')[k]!=null) {
  			
  			if (clone.getElementsByTagName('SELECT')[k].name.includes("Linea")){
  				clone.getElementsByTagName('SELECT')[k].name = "Linea1_"+elementi.value;
  	  		  	clone.getElementsByTagName('SELECT')[k].id = "Linea1_"+String(indice+1);
  	  		  	clone.getElementsByTagName('SELECT')[k].value = '-1';
  	  		  	clone.getElementsByTagName('SELECT')[k].disabled ='';
  			}

  			if (clone.getElementsByTagName('SELECT')[k].name.includes("ProvvedimentiBenessereMacellazione")){
  				clone.getElementsByTagName('SELECT')[k].name = "ProvvedimentiBenessereMacellazione1_"+elementi.value;
  	  		  	clone.getElementsByTagName('SELECT')[k].id = "ProvvedimentiBenessereMacellazione1_"+String(indice+1);
  	  		  	clone.getElementsByTagName('SELECT')[k].value = '-1';
  	  		  	clone.getElementsByTagName('SELECT')[k].disabled ='';
  	  		  	clone.getElementsByTagName('SELECT')[k].style.display ='none';
  			}
  			
  			if (clone.getElementsByTagName('SELECT')[k].name.includes("ProvvedimentiBenessereTrasporto")){
  				clone.getElementsByTagName('SELECT')[k].name = "ProvvedimentiBenessereTrasporto1_"+elementi.value;
  	  		  	clone.getElementsByTagName('SELECT')[k].id = "ProvvedimentiBenessereTrasporto1_"+String(indice+1);
  	  		  	clone.getElementsByTagName('SELECT')[k].value = '-1';
  	  		  	clone.getElementsByTagName('SELECT')[k].disabled ='';
  	  		  	clone.getElementsByTagName('SELECT')[k].style.display ='none';
  			}
  			
  			if (clone.getElementsByTagName('SELECT')[k].name.includes("OperatoriMercato")){
  				clone.getElementsByTagName('SELECT')[k].name = "OperatoriMercato1_"+elementi.value;
  	  		  	clone.getElementsByTagName('SELECT')[k].id = "OperatoriMercato1_"+String(indice+1);
  	  		  	clone.getElementsByTagName('SELECT')[k].value = '-1';
  	  		  	clone.getElementsByTagName('SELECT')[k].disabled ='';
  	  		  	clone.getElementsByTagName('SELECT')[k].style.display ='none';
  			}
   	  	}
  		
  	}
  	
  	clone.getElementsByTagName('textarea')[0].name = "nonConformitaFormali_"+elementi.value;
  	clone.getElementsByTagName('textarea')[0].id = "nonConformitaFormali_"+elementi.value;
  	clone.getElementsByTagName('textarea')[0].readOnly='';
  	clone.getElementsByTagName('textarea')[0].value= msg_default ;
  	clone.getElementsByTagName('input')[0].name = "Provvedimenti1_"+elementi.value+"_selezionato";
  	clone.getElementsByTagName('input')[0].id = "Provvedimenti1_"+elementi.value+"_selezionato";
	clone.getElementsByTagName('input')[0].value = "-1";
 
 
  	clone.id = "nc_formali_"+elementi.value;
  	clonato.parentNode.appendChild(clone);
  	clone.style.visibility="visible";
  	}
  	else{
  		alert('Compilare Tutte le voci per la non conformita\' corrente prima di aggiungerne una nuova!')
  	}
 }
  	
}

/**
 * FUNZIONE DI AGGIUNTA DI UNA NC SIGNIFICATIVA
 * @return
 */
function clonaNC_significative(){
  	var maxElementi = 25;
  	var elementi;
  	var elementoClone;
  	var tableClonata;
  	var tabella;
  	var selezionato;
  	var x;
  	elementi = document.getElementById('elementi_nc_significative');
	var msg_default = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
  	if (elementi.value < maxElementi)
  	{
	
  	if  ( document.getElementById('Provvedimenti2_'+elementi.value).value != '-1' 				&& 
  		  trim(document.getElementById('nonConformitaSignificative_'+elementi.value).value) != ''		&&
  	  	  trim(document.getElementById('nonConformitaSignificative_'+elementi.value).value) != msg_default
  	  	)
  	{
  	document.getElementById('Provvedimenti2_'+elementi.value).disabled='true';
  	document.getElementById('nonConformitaSignificative_'+elementi.value).readOnly='true';
  	elementi.value=parseInt(elementi.value)+1;
  	size = document.getElementById('size_nc_significative');
  	size.value=parseInt(size.value)+1;
  	var primo_elemento = document.getElementById('Provvedimenti2_1');
  	var indice = parseInt(elementi.value) - 1;
  	
  	if(primo_elemento!=null && x==null){
  		selezionato = document.getElementById('Provvedimenti2_1').selectedIndex;
  	}else if(primo_elemento==null && x!=null){
  		selezionato = x.selectedIndex;
  	}
  	var clonato = document.getElementById('nc_significative_1');
  	
  	
  	/*clona riga vuota*/
  	clone=clonato.cloneNode(true);
	clone.getElementsByTagName('SELECT')[0].name = "Provvedimenti2_"+elementi.value;
  	clone.getElementsByTagName('SELECT')[0].id = "Provvedimenti2_"+String(indice+1);
  	clone.getElementsByTagName('SELECT')[0].value = '-1';
	clone.getElementsByTagName('SELECT')[0].disabled='';
	clone.getElementsByTagName('LABEL')[0].innerHTML ="<b>"+elementi.value+"</b>";
	
	for (var k = 0; k<10; k++) {
  		if (clone.getElementsByTagName('SELECT')[k]!=null) {
  			
  			if (clone.getElementsByTagName('SELECT')[k].name.includes("Linea")){
  				clone.getElementsByTagName('SELECT')[k].name = "Linea2_"+elementi.value;
  	  		  	clone.getElementsByTagName('SELECT')[k].id = "Linea2_"+String(indice+1);
  	  		  	clone.getElementsByTagName('SELECT')[k].value = '-1';
  	  		  	clone.getElementsByTagName('SELECT')[k].disabled ='';
  			}

  			if (clone.getElementsByTagName('SELECT')[k].name.includes("ProvvedimentiBenessereMacellazione")){
  				clone.getElementsByTagName('SELECT')[k].name = "ProvvedimentiBenessereMacellazione2_"+elementi.value;
  	  		  	clone.getElementsByTagName('SELECT')[k].id = "ProvvedimentiBenessereMacellazione2_"+String(indice+1);
  	  		  	clone.getElementsByTagName('SELECT')[k].value = '-1';
  	  		  	clone.getElementsByTagName('SELECT')[k].disabled ='';
  	  		  	clone.getElementsByTagName('SELECT')[k].style.display ='none';
  			}
  			
  			if (clone.getElementsByTagName('SELECT')[k].name.includes("ProvvedimentiBenessereTrasporto")){
  				clone.getElementsByTagName('SELECT')[k].name = "ProvvedimentiBenessereTrasporto2_"+elementi.value;
  	  		  	clone.getElementsByTagName('SELECT')[k].id = "ProvvedimentiBenessereTrasporto2_"+String(indice+1);
  	  		  	clone.getElementsByTagName('SELECT')[k].value = '-1';
  	  		  	clone.getElementsByTagName('SELECT')[k].disabled ='';
  	  		  	clone.getElementsByTagName('SELECT')[k].style.display ='none';
  			}
  			
  			if (clone.getElementsByTagName('SELECT')[k].name.includes("OperatoriMercato")){
  				clone.getElementsByTagName('SELECT')[k].name = "OperatoriMercato2_"+elementi.value;
  	  		  	clone.getElementsByTagName('SELECT')[k].id = "OperatoriMercato2_"+String(indice+1);
  	  		  	clone.getElementsByTagName('SELECT')[k].value = '-1';
  	  		  	clone.getElementsByTagName('SELECT')[k].disabled ='';
  	  		  	clone.getElementsByTagName('SELECT')[k].style.display ='none';
  			}
   	  	}
  		
  	}
	
  	clone.getElementsByTagName('textarea')[0].name = "nonConformitaSignificative_"+elementi.value;
	clone.getElementsByTagName('textarea')[0].id = "nonConformitaSignificative_"+elementi.value;
	clone.getElementsByTagName('textarea')[0].readOnly='';
	clone.getElementsByTagName('textarea')[0].value= msg_default ;
	
	clone.getElementsByTagName('input')[0].name = "Provvedimenti2_"+elementi.value+"_selezionato";
  	clone.getElementsByTagName('input')[0].id = "Provvedimenti2_"+elementi.value+"_selezionato";
  	clone.getElementsByTagName('input')[0].value = "-1";
  	/*Lo rendo visibile*/

  	clone.id = "nc_significative_"+elementi.value;
  	/*Aggancio il nodo*/
  	clonato.parentNode.appendChild(clone);

  	/*Lo rendo visibile*/
  
  	clone.style.visibility="visible";
  	
  	}else{
  		alert('Selezionare una voce Per la Non Conformita\' Significativa Corrente, e successivamente aggiungere!')
  		
  	}
  	
  	}
  }

/**
 * FUNZIONE DI AGGIUNTA DI UNA NC GRAVE
 * @return
 */
function clonaNC_Gravi(){
  	var maxElementi = 30;
  	var elementi;
  	var elementoClone;
  	var tableClonata;
  	var tabella;
  	var selezionato;
  	var x;
  	elementi = document.getElementById('elementi_nc_gravi');
  	var msg_default = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
  	if (elementi.value < maxElementi)
  	{
	
  		if  ( document.getElementById('Provvedimenti3_'+elementi.value).value != '-1' 				&& 
  	  		  trim(document.getElementById('nonConformitaGravi_'+elementi.value).value) != ''		&&
  	  	  	  trim(document.getElementById('nonConformitaGravi_'+elementi.value).value) != msg_default
  	  	  	)
  	  	{
  	document.getElementById('Provvedimenti3_'+elementi.value).disabled='true';
  	document.getElementById('nonConformitaGravi_'+elementi.value).readOnly='true';
  	
  		
  	elementi.value=parseInt(elementi.value)+1;
  	size = document.getElementById('size_nc_gravi');
  	size.value=parseInt(size.value)+1;
  	var primo_elemento = document.getElementById('Provvedimenti3_1');
  	var indice = parseInt(elementi.value) - 1;
  	
  	if(primo_elemento!=null && x==null){
  		selezionato = document.getElementById('Provvedimenti3_1').selectedIndex;
  	}else if(primo_elemento==null && x!=null){
  		selezionato = x.selectedIndex;
  	}
  	var clonato = document.getElementById('nc_gravi_1');
  	
  	
  	/*clona riga vuota*/
  	clone=clonato.cloneNode(true);
	clone.getElementsByTagName('SELECT')[0].name = "Provvedimenti3_"+elementi.value;
  	clone.getElementsByTagName('SELECT')[0].id = "Provvedimenti3_"+String(indice+1);
  	clone.getElementsByTagName('SELECT')[0].value = '-1';
  	clone.getElementsByTagName('SELECT')[0].disabled='';
  	clone.getElementsByTagName('LABEL')[0].innerHTML ="<b>"+elementi.value+"</b>";
  	
  	for (var k = 0; k<10; k++) {
  		if (clone.getElementsByTagName('SELECT')[k]!=null) {
  			
  			if (clone.getElementsByTagName('SELECT')[k].name.includes("Linea")){
  				clone.getElementsByTagName('SELECT')[k].name = "Linea3_"+elementi.value;
  	  		  	clone.getElementsByTagName('SELECT')[k].id = "Linea3_"+String(indice+1);
  	  		  	clone.getElementsByTagName('SELECT')[k].value = '-1';
  	  		  	clone.getElementsByTagName('SELECT')[k].disabled ='';
  			}

  			if (clone.getElementsByTagName('SELECT')[k].name.includes("ProvvedimentiBenessereMacellazione")){
  				clone.getElementsByTagName('SELECT')[k].name = "ProvvedimentiBenessereMacellazione3_"+elementi.value;
  	  		  	clone.getElementsByTagName('SELECT')[k].id = "ProvvedimentiBenessereMacellazione3_"+String(indice+1);
  	  		  	clone.getElementsByTagName('SELECT')[k].value = '-1';
  	  		  	clone.getElementsByTagName('SELECT')[k].disabled ='';
  	  		  	clone.getElementsByTagName('SELECT')[k].style.display ='none';
  			}
  			
  			if (clone.getElementsByTagName('SELECT')[k].name.includes("ProvvedimentiBenessereTrasporto")){
  				clone.getElementsByTagName('SELECT')[k].name = "ProvvedimentiBenessereTrasporto3_"+elementi.value;
  	  		  	clone.getElementsByTagName('SELECT')[k].id = "ProvvedimentiBenessereTrasporto3_"+String(indice+1);
  	  		  	clone.getElementsByTagName('SELECT')[k].value = '-1';
  	  		  	clone.getElementsByTagName('SELECT')[k].disabled ='';
  	  		  	clone.getElementsByTagName('SELECT')[k].style.display ='none';
  			}
  			
  			if (clone.getElementsByTagName('SELECT')[k].name.includes("OperatoriMercato")){
  				clone.getElementsByTagName('SELECT')[k].name = "OperatoriMercato3_"+elementi.value;
  	  		  	clone.getElementsByTagName('SELECT')[k].id = "OperatoriMercato3_"+String(indice+1);
  	  		  	clone.getElementsByTagName('SELECT')[k].value = '-1';
  	  		  	clone.getElementsByTagName('SELECT')[k].disabled ='';
  	  		  	clone.getElementsByTagName('SELECT')[k].style.display ='none';
  			}
   	  	}
  		
  	}
	
  	clone.getElementsByTagName('textarea')[0].name = "nonConformitaGravi_"+elementi.value;
  	clone.getElementsByTagName('textarea')[0].id = "nonConformitaGravi_"+elementi.value;
  	clone.getElementsByTagName('textarea')[0].readOnly='';
  	clone.getElementsByTagName('textarea')[0].value= msg_default ;
  	clone.getElementsByTagName('input')[0].name = "Provvedimenti3_"+elementi.value+"_selezionato";
  	clone.getElementsByTagName('input')[0].id = "Provvedimenti3_"+elementi.value+"_selezionato";
  	clone.getElementsByTagName('input')[0].value = "-1";
  	/*Lo rendo visibile*/

  	clone.id = "nc_gravi_"+elementi.value;
  	/*Aggancio il nodo*/
  	clonato.parentNode.appendChild(clone);

  	/*Lo rendo visibile*/
  
  	clone.style.visibility="visible";
  	
  	}
  	else
  	{
  		alert('Selezionare una voce Per la Non Conformita\' Grave Corrente, e successivamente aggiungere!')
  		
  	}
  	
  	}
  }





/**
 * RIMUOVE UNA NC GRAVE INVOCATA SULL'ONCLICK DEL PULSANTE ELIMINA NC FORMALE
 * @return
 */
function removeFormali(reset,num_nc_inserite)
{
	elementi = document.getElementById('elementi_nc_formali');
	if (parseInt(elementi.value)>1 && document.getElementById('Provvedimenti1_'+elementi.value).disabled==false)
	{
		
	document.getElementById('nc_formali_'+elementi.value).parentNode.removeChild(document.getElementById('nc_formali_'+elementi.value));
	document.getElementById('elementi_nc_formali').value = parseInt(elementi.value)-1;
	
	calcolaPuntiNonConformita(document.getElementById('elementi_nc_formali').value,'Provvedimenti1_','puntiFormali','nonConformitaFormali_',document.getElementById('tipoControlloUfficiale').value,document.getElementById('dataInizioControlloUfficiale').value);
	document.getElementById('Provvedimenti1_'+elementi.value).disabled='';
	document.getElementById('nonConformitaFormali_'+elementi.value).readOnly='';
	}
	else
	{
		if (parseInt(elementi.value)==1 && document.getElementById('Provvedimenti1_'+elementi.value).disabled==false)
		{
			var msg_default = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
		document.getElementById('nonConformitaFormali_'+elementi.value).value = msg_default;
		document.getElementById('Provvedimenti1_'+elementi.value).value = '-1' ;
		document.getElementById('valutazione_rischio_formali').value = 'INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' FORMALI RISCONTRATE';
		if(document.getElementById('Provvedimenti1_'+elementi.value+'_selezionato')!=null)
			document.getElementById('Provvedimenti1_'+elementi.value+'_selezionato').value='-1';
		
		document.getElementById('stato_formali').value = 'true' ;
//		document.getElementById('Provvedimenti1_'+elementi.value).options[0].selected ='true';
		document.getElementById('puntiFormali').value ='0';
		
		} 
		else
			if (reset == 'true')
			{
				document.getElementById('Provvedimenti1_'+elementi.value).disabled='';
				document.getElementById('nonConformitaFormali_'+elementi.value).readOnly='';
			}
		
	}
	
	
}

/**
 * RIMUOVE UNA NC GRAVE INVOCATA SULL'ONCLICK DEL PULSANTE ELIMINA NC SIGNIFICATIVA
 * @return
 */
function removeSignificative(reset,num_nc_inserite)
{
	elementi = document.getElementById('elementi_nc_significative');
	if (parseInt(elementi.value)>1 && document.getElementById('Provvedimenti2_'+elementi.value).disabled==false)
	{
	
	document.getElementById('nc_significative_'+elementi.value).parentNode.removeChild(document.getElementById('nc_significative_'+elementi.value));
	document.getElementById('elementi_nc_significative').value = parseInt(elementi.value)-1;
	calcolaPuntiNonConformita(document.getElementById('elementi_nc_significative').value,'Provvedimenti2_','puntiSignificativi','nonConformitaSignificative_',document.getElementById('tipoControlloUfficiale').value,document.getElementById('dataInizioControlloUfficiale').value);
//	calcolaPuntiSignificative(document.getElementById('elementi_nc_significative').value,7);
	document.getElementById('Provvedimenti2_'+elementi.value).disabled='';
  	document.getElementById('nonConformitaSignificative_'+elementi.value).readOnly='';
	}
	else
	{
		if (parseInt(elementi.value)==1 && document.getElementById('Provvedimenti2_'+elementi.value).disabled==false)
		{
			var msg_default = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
		document.getElementById('nonConformitaSignificative_'+elementi.value).value = msg_default;
		document.getElementById('Provvedimenti2_'+elementi.value).value ='-1' ;
		document.getElementById('valutazione_rischio_significative').value = 'INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' SIGNIFICATIVE RISCONTRATE';
		if(document.getElementById('Provvedimenti2_'+elementi.value+'_selezionato')!=null)
			document.getElementById('Provvedimenti2_'+elementi.value+'_selezionato').value='-1';
		document.getElementById('stato_significative').value = 'true' ;
		document.getElementById('puntiSignificativi').value ='0';
		} 
		else
			if (reset == 'true' )
			{
			document.getElementById('Provvedimenti2_'+elementi.value).disabled='';
		  	document.getElementById('nonConformitaSignificative_'+elementi.value).readOnly='';
			}
	}
	
}

/**
 * RIMUOVE UNA NC GRAVE INVOCATA SULL'ONCLICK DEL PULSANTE ELIMINA NC GRAVE
 * @return
 */
function removeGravi(reset,num_nc_inserite)
{
	elementi = document.getElementById('elementi_nc_gravi');
	if (parseInt(elementi.value)>1 && document.getElementById('Provvedimenti3_'+elementi.value).disabled==false)
	{
	
	document.getElementById('nc_gravi_'+elementi.value).parentNode.removeChild(document.getElementById('nc_gravi_'+elementi.value));
	document.getElementById('elementi_nc_gravi').value = parseInt(elementi.value)-1;
	calcolaPuntiNonConformita(document.getElementById('elementi_nc_gravi').value,'Provvedimenti3_','puntiGravi','nonConformitaGravi_',document.getElementById('tipoControlloUfficiale').value,document.getElementById('dataInizioControlloUfficiale').value);

	document.getElementById('Provvedimenti3_'+elementi.value).disabled='';
  	document.getElementById('nonConformitaGravi_'+elementi.value).readOnly='';
	}
	else
	if (parseInt(elementi.value)==1 && document.getElementById('Provvedimenti3_'+elementi.value).disabled==false)
	{
		var msg_default = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
		document.getElementById('nonConformitaGravi_'+elementi.value).value = msg_default;
		document.getElementById('Provvedimenti3_'+elementi.value).value = '-1' ;
		if (document.getElementById('valutazione_rischio_gravi')!=null)
			document.getElementById('valutazione_rischio_gravi').value = 'INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' GRAVI RISCONTRATE';
		if(document.getElementById('Provvedimenti3_'+elementi.value+'_selezionato')!=null)
			document.getElementById('Provvedimenti3_'+elementi.value+'_selezionato').value='-1';
		document.getElementById('stato_gravi').value = 'true' ;
		if(document.getElementById('puntiGravi')!=null)
			document.getElementById('puntiGravi').value ='0';
	} 
	else
	if (reset == 'true' )
	{
	document.getElementById('Provvedimenti3_'+elementi.value).disabled='';
  	document.getElementById('nonConformitaGravi_'+elementi.value).readOnly='';
	}
}


/**
 * FUNZIONE INVOCATA SUL CARICAMENTO DELLA PAGINA DI AGGIUNTA E MODIFICA DI UNA NC.
 * SETTA I CAMPI CON QUELLI PASSATI IN INPUT
 * @param pformali
 * @param psign
 * @param pgravi
 * @param formali_size
 * @param sign_size
 * @param gravi_size
 * @return
 */
function resetElementi(pformali,psign,pgravi,formali_size,sign_size,gravi_size)
{
	document.getElementById('elementi_nc_formali').value=formali_size;
	document.getElementById('elementi_nc_significative').value=sign_size;
	document.getElementById('elementi_nc_gravi').value=gravi_size;
	
	 if (document.getElementById('puntiGravi')!=null)
	 {
	 document.getElementById('puntiGravi').value = pgravi ;
	 document.getElementById('puntiSignificativi').value = psign;
	 document.getElementById('puntiFormali').value = pformali ;
	 }
}

function sevValueNcFormali()
{
	elementi = document.getElementById('elementi_nc_formali') ;
	document.getElementById('Provvedimenti1_'+elementi.value+'_selezionato').value=	document.getElementById('Provvedimenti1_'+elementi.value).value;
}

function sevValueNcFormaliModify(elem_curr)
{
	elementi = document.getElementById('elementi_nc_formali') ;
	document.getElementById('Provvedimenti1_'+elem_curr+'_selezionato').value=	document.getElementById('Provvedimenti1_'+elem_curr).value;
}

function sevValueNcSignificative()
{
	elementi = document.getElementById('elementi_nc_significative') ;
	document.getElementById('Provvedimenti2_'+elementi.value+'_selezionato').value=	document.getElementById('Provvedimenti2_'+elementi.value).value;
}

function sevValueNcSignificativeModify(elem_curr)
{
	elementi = document.getElementById('elementi_nc_significative') ;
	document.getElementById('Provvedimenti2_'+elem_curr+'_selezionato').value=	document.getElementById('Provvedimenti2_'+elem_curr).value;
}

function sevValueNcGravi()
{
	elementi = document.getElementById('elementi_nc_gravi') ;
	document.getElementById('Provvedimenti3_'+elementi.value+'_selezionato').value=	document.getElementById('Provvedimenti3_'+elementi.value).value;
}

function sevValueNcGraviModify(elem_curr)
{
	elementi = document.getElementById('elementi_nc_gravi') ;
	document.getElementById('Provvedimenti3_'+elem_curr+'_selezionato').value=	document.getElementById('Provvedimenti3_'+elem_curr).value;
}

/**
 * ABILITA PER LE NON CONFORMITA FORMALI IL FLAG ABILITA_FORMALI E descrizione_or_combo_sel_formali
 * SE PER ALMENO UNA NC TRA QUELLE AGGIUNTE DALL'UTENTE ESISTE UNA PER LA QUALE � STATA SELEZIONATA
 * UNA VOCE DALLA COMBO E INSERITA UNA DESCRIZIONE VIENE SETTATO A TRUE IL FLAG ABILITA_FORMALI.
 * SE INVECE NE ESISTE UNA PER LA QUALE � STATA INSERITA UNA DESCRIZIONE E NON SELEZIONATA UNA VOCE DALLA COMBO
 * O VICEVERSA,VIENE SETTATO A TRUE IL FLAG descrizione_or_combo_sel_formali
 * @return
 */
function abilitaFlagFormali()
{
sevValueNcFormali();
var msg_default = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';  
isSel = false ;  
document.getElementById('descrizione_or_combo_sel_formali').value = false ;
for (i = 1 ; i<=document.getElementById('elementi_nc_formali').value; i++)
{
	array = document.getElementById('Provvedimenti1_'+i)
	if  ( array[0].selected == false && 
			( trim(document.getElementById('nonConformitaFormali_'+i).value) != ''	&&
			  trim(document.getElementById('nonConformitaFormali_'+i).value) != msg_default
		 	)
		)
	{
		isSel = true ;
		
	}
	else
	{
		if  ( array[0].selected == true && 
				( trim(document.getElementById('nonConformitaFormali_'+i).value) == ''	||
				  trim(document.getElementById('nonConformitaFormali_'+i).value) == msg_default
			 	)
			)
		{
			document.getElementById('descrizione_or_combo_sel_formali').value  = false ;
		}
		else
		{
			document.getElementById('descrizione_or_combo_sel_formali').value  = true ;
		}
	}
}

document.getElementById('abilita_formali').value =isSel;
}


/**
 * ABILITA PER LE NON CONFORMITA SIGNIFICATIVE IL FLAG ABILITA_SIGNIFICATIVE E descrizione_or_combo_sel_significative
 * SE PER ALMENO UNA NC TRA QUELLE AGGIUNTE DALL'UTENTE ESISTE UNA PER LA QUALE � STATA SELEZIONATA
 * UNA VOCE DALLA COMBO E INSERITA UNA DESCRIZIONE VIENE SETTATO A TRUE IL FLAG ABILITA_SIGNIFICATIVE.
 * SE INVECE NE ESISTE UNA PER LA QUALE � STATA INSERITA UNA DESCRIZIONE E NON SELEZIONATA UNA VOCE DALLA COMBO
 * O VICEVERSA,VIENE SETTATO A TRUE IL FLAG descrizione_or_combo_sel_significative
 * @return
 */
function abilitaFlagSignificative()
{
	sevValueNcSignificative();
	var msg_default = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';  
	isSel = false ;  
	document.getElementById('descrizione_or_combo_sel_significative').value = false ;
	for (i = 1 ; i<=document.getElementById('elementi_nc_significative').value; i++)
	{
		array = document.getElementById('Provvedimenti2_'+i)
		if  ( array[0].selected == false && 
				( trim(document.getElementById('nonConformitaSignificative_'+i).value) != ''	&&
				  trim(document.getElementById('nonConformitaSignificative_'+i).value) != msg_default
			 	)
			)
		{
			
			isSel = true ;
			
		}
		else
		{
			if  ( array[0].selected == true && 
					( trim(document.getElementById('nonConformitaSignificative_'+i).value) == ''	||
					  trim(document.getElementById('nonConformitaSignificative_'+i).value) == msg_default
				 	)
				)
			{
				document.getElementById('descrizione_or_combo_sel_significative').value  = false ;
			}
			else
			{
				document.getElementById('descrizione_or_combo_sel_significative').value  = true ;
			}				
		}
	}
	document.getElementById('abilita_significative').value =isSel;			
}

/**
 * ABILITA PER LE NON CONFORMITA GRAVI IL FLAG ABILITA_GRAVI E descrizione_or_combo_sel_gravi
 * SE PER ALMENO UNA NC TRA QUELLE AGGIUNTE DALL'UTENTE ESISTE UNA PER LA QUALE � STATA SELEZIONATA
 * UNA VOCE DALLA COMBO E INSERITA UNA DESCRIZIONE VIENE SETTATO A TRUE IL FLAG ABILITA_GRAVI.
 * SE INVECE NE ESISTE UNA PER LA QUALE � STATA INSERITA UNA DESCRIZIONE E NON SELEZIONATA UNA VOCE DALLA COMBO
 * O VICEVERSA,VIENE SETTATO A TRUE IL FLAG descrizione_or_combo_sel_gravi
 * @return
 */
function abilitaFlagGravi()
{
	sevValueNcGravi();
	var msg_default = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';  
	isSel = false ;  
	document.getElementById('descrizione_or_combo_sel_gravi').value = false ;
	for (i = 1 ; i<=document.getElementById('elementi_nc_gravi').value; i++)
	{
		array = document.getElementById('Provvedimenti3_'+i)
		if  ( array[0].selected == false && 
				( trim(document.getElementById('nonConformitaGravi_'+i).value) != ''	&&
				  trim(document.getElementById('nonConformitaGravi_'+i).value) != msg_default
			 	)
			)
		{
			
			isSel = true ;
			
		}
		else
		{
			if  ( array[0].selected == true && 
					( trim(document.getElementById('nonConformitaGravi_'+i).value) == ''	||
					  trim(document.getElementById('nonConformitaGravi_'+i).value) == msg_default
				 	)
				)
			{
				document.getElementById('descrizione_or_combo_sel_gravi').value  = false ;
			}
			else
			{
				document.getElementById('descrizione_or_combo_sel_gravi').value  = true ;
			}				
		}
	}
	document.getElementById('abilita_gravi').value =isSel;
}

function gestisciBenessereNonConformita(provv){
	var val = provv.value;
	var indice = provv.name.replace("Provvedimenti", "");
	
	if (val == '65'){ //MACELLAZIONE
		if (document.getElementById("ProvvedimentiBenessereMacellazione"+indice)!=null)
			document.getElementById("ProvvedimentiBenessereMacellazione"+indice).style.display="block";
		if (document.getElementById("ProvvedimentiBenessereTrasporto"+indice)!=null){
			document.getElementById("ProvvedimentiBenessereTrasporto"+indice).style.display="none";
			document.getElementById("ProvvedimentiBenessereTrasporto"+indice).value="-1";
		}
	}
	else if (val == '126'){ // TRASPORTO
		if (document.getElementById("ProvvedimentiBenessereTrasporto"+indice)!=null)
			document.getElementById("ProvvedimentiBenessereTrasporto"+indice).style.display="block";
		if (document.getElementById("ProvvedimentiBenessereMacellazione"+indice)!=null){
			document.getElementById("ProvvedimentiBenessereMacellazione"+indice).style.display="none";
			document.getElementById("ProvvedimentiBenessereMacellazione"+indice).value="-1";
		}
	}
	else {
		if (document.getElementById("ProvvedimentiBenessereMacellazione"+indice)!=null){
			document.getElementById("ProvvedimentiBenessereMacellazione"+indice).style.display="none";
			document.getElementById("ProvvedimentiBenessereMacellazione"+indice).value="-1";
		}
		if (document.getElementById("ProvvedimentiBenessereTrasporto"+indice)!=null){
			document.getElementById("ProvvedimentiBenessereTrasporto"+indice).style.display="none";
			document.getElementById("ProvvedimentiBenessereTrasporto"+indice).value="-1";
	}
	}
}

/**
 * FUNZIONE INVOCATA ALL'USCITA DELLA PAGINA SE NON SI HA INSERITO LA NC.
 * CANCELLA TUTTE LE SOTTOATTIVITA INSERITE , IN MODO DA NON RIMANERLE APPESE
 * (NON ASSOCIATE A NESSUNA NON CONFORMITA)
 * @param idControllo
 * @return
 */
function deleteAttivita(idControllo,doSubmit)
{
	
	
	if(confirm("Attenzione ! Uscendo dalla pagina senza cliccare sul bottone \"inserisci\" i followup inseriti non saranno salvati. "))
		{
		
		}
}
	
function deleteAttivitaCallBack(value){
	
}

var isSave = false ;
/**
 * ABILITA LO STATO PER LE NON CONFORMITA FORMALI SE � STATA INSERITA ALMENO UNA SOTTOATTIVITA
 * @param idControllo
 * @return
 */ 	
function abilitaStatoFormali(idControllo)
{	
	formali = document.getElementById('abilita_formali').value ;
	haveNC = document.getElementById('descrizione_or_combo_sel_formali').value;
	haveFollowup = 'false';
	if(document.getElementById('followup_inseriti_formali').value!='')
	{
		haveFollowup = 'true' ;
	}
	
	if ((formali == 'true' && haveFollowup == 'true' && haveNC =='false') || (formali == 'false' && haveFollowup == 'false' && haveNC =='false'))
	{
		stato = true ;
	}
	else
	{
		stato = false ;
	}
	abilitaStatoFormaliCallBack(stato);
	
}
	
function abilitaStatoFormaliCallBack(value)
{
	
	document.getElementById('stato_formali').value = value;
	abilitaInserimentoTotale()
}

/**
 * ABILITA LO STATO PER LE NON CONFORMITA SIGNIFICATIVE SE � STATA INSERITA ALMENO UNA SOTTOATTIVITA
 * @param idControllo
 * @return
 */ 
function abilitaStatoSignificative(idControllo)
{	
	significative = document.getElementById('abilita_significative').value ;
	haveNC = document.getElementById('descrizione_or_combo_sel_significative').value;
	haveFollowup = 'false';
	if(document.getElementById('followup_inseriti_significativi').value!='')
	{
		haveFollowup = 'true' ;
	}
	if ((significative == 'true' && haveFollowup == 'true' && haveNC =='false') || (significative == 'false' && haveFollowup == 'false' && haveNC =='false'))
	{
		stato = true ;
	}
	else
	{
		stato = false ;
	}
	
	abilitaStatoSignificativeCallBack(stato)
}
	
function abilitaStatoSignificativeCallBack(value)
{
	document.getElementById('stato_significative').value = value;
	abilitaInserimentoTotale()
}

/**
 * ABILITA LO STATO PER LE NON CONFORMITA GRAVI SE � STATA INSERITA ALMENO UNA SOTTOATTIVITA
 * @param idControllo
 * @return
 */ 
function abilitaStatoGravi(idControllo)
{	
	gravi = document.getElementById('abilita_gravi').value ;
	haveNC = document.getElementById('descrizione_or_combo_sel_gravi').value;
	haveFollowup = 'false';
	if((document.getElementById('attivita_inseriti_gravi').value!='' ||  document.getElementById('followup_gravi_inseriti').value!='') )
	{
		haveFollowup = 'true' ;
	}
	
	if ((gravi == 'true' && haveFollowup == 'true' && haveNC =='false') || (gravi == 'false' && haveFollowup == 'false' && haveNC =='false'))
	{
		stato = true ;
	}
	else
	{
		stato = false ;
	}
	
	abilitaStatoGraviCallBack(stato);
	
}
	
function abilitaStatoGraviCallBack(value)
{
	
	document.getElementById('stato_gravi').value = value;
	abilitaInserimentoTotale()
}

/**
 * ABILITA IL PULSANTE INSERISCI GLOBALE (QUELLO RELATIVO A TUTTA LA NON CONFORMITA)
 * SOLO SE LE NC FORMALI , SIGNIFICATIVE E GRAVI SI TROVANO NELLO STATO TRUE
 * @return
 */ 
function abilitaInserimentoTotale()
{
if( document.getElementById('stato_gravi').value=='true'  	&&
	document.getElementById('stato_significative').value=='true'	&&
	document.getElementById('stato_formali').value=='true'
)
{
	document.getElementById('btn_salva').disabled='';
	document.getElementById('btn_salva2').disabled='';
}
else
{
	document.getElementById('btn_salva').disabled='true';
	document.getElementById('btn_salva2').disabled='true';
}
}
/**
 * FUNZIONE INVOCATA SULL'ONCLICK DEL PULSANTE RESET NC FORMALI
 * RIPRISTINA LA SITUAZIONE DELLE NC SIGNIFICATIVE COSI COME ERA INIZIALMENTE.
 * RIMUOVE LE NON CONFORMITA INSERITE DALL'UTENTE ,
 * SETTA LO STATO GLOBALE DELLE NC FORMALI A TRUE , 
 * CANCELLA LE SOTTOATTIVITA INSERITE .
 * @param idControllo
 * @param tipo_nc
 * @return
 */
function resetFormali(idControllo,tipo_nc,num_nc_inserite,domanda)
{	
	if(domanda==true)
	{
	if (confirm('Sei sicuro di voler azzerare il lavoro svolto nelle non conformita\' Formali ?'))
	{
	elementi = document.getElementById('elementi_nc_formali') ;
	document.getElementById('stato_formali').value = 'true' ;
	document.getElementById('descrizione_or_combo_sel_formali').value = 'false' ;
	document.getElementById('abilita_formali').value = 'false' ;
	for (i =elementi.value ; i>=1 ; i--  )
	{
		removeFormali('true',num_nc_inserite);
	}
	document.getElementById('valutazione_rischio_formali').value='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' FORMALI RISCONTRATE';
	if(document.getElementById('puntiFormali')!=null)
		document.getElementById('puntiFormali').value='0';
	document.getElementById('Provvedimenti1_'+elementi.value).disabled='';
	document.getElementById('Provvedimenti1_'+elementi.value+'_selezionato').value='-1';
	document.getElementById('Provvedimenti1_'+elementi.value).options[0].selected ='true';
  	document.getElementById('nonConformitaFormali_'+elementi.value).readOnly='';
  	document.getElementById('nonConformitaFormali_'+elementi.value).value='INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
  	//PopolaCombo.deleteAttivitaNc(document.getElementById('formali').value, idControllo,tipo_nc,document.getElementById('dosubmit').value);
  	
  	document.getElementById('followup_inseriti_formali').value=''
  	calcolaTotale();
  	abilitaInserimentoTotale();
  	
	}
	}
	else
	{
		elementi = document.getElementById('elementi_nc_formali') ;
		document.getElementById('stato_formali').value = 'true' ;
		document.getElementById('descrizione_or_combo_sel_formali').value = 'false' ;
		document.getElementById('abilita_formali').value = 'false' ;
		for (i =elementi.value ; i>1 ; i--  )
		{
			removeFormali('true',num_nc_inserite);
		}
		document.getElementById('valutazione_rischio_formali').value='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' FORMALI RISCONTRATE';
		if(document.getElementById('puntiFormali')!=null)
			document.getElementById('puntiFormali').value='0';
		document.getElementById('Provvedimenti1_'+elementi.value).disabled='';
		document.getElementById('Provvedimenti1_'+elementi.value+'_selezionato').value='-1';
		document.getElementById('Provvedimenti1_'+elementi.value).options[0].selected ='true';
	  	document.getElementById('nonConformitaFormali_'+elementi.value).readOnly='';
	  	document.getElementById('nonConformitaFormali_'+elementi.value).value='INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
	  	//PopolaCombo.deleteAttivitaNc(document.getElementById('formali').value, idControllo,tipo_nc,document.getElementById('dosubmit').value);
	  	
	  	document.getElementById('followup_inseriti_formali').value=''
	  	calcolaTotale();
	  	abilitaInserimentoTotale();
	  
	  	document.getElementById('followup_inseriti_formali').value=''
	}
	clonaFollowup();
}



function resetFormali_update(idControllo,tipo_nc,num_nc_inserite,followup_inseriti_formali,nc_inserite,note_inserite)
{
	if (confirm('Sei sicuro di voler azzerare il lavoro svolto nelle non conformita\' Formali ?'))
	{
		array_nc_inserite = nc_inserite.split(";");	
		array_note_inserite = note_inserite.split(";");	
	elementi = document.getElementById('elementi_nc_formali') ;
	document.getElementById('stato_formali').value = 'true' ;
	document.getElementById('descrizione_or_combo_sel_formali').value = 'false' ;
	if(followup_inseriti_formali!= "" )
		document.getElementById('abilita_formali').value = 'true' ;
	else
		document.getElementById('abilita_formali').value = 'false' ;
	for (i =elementi.value ; i>=num_nc_inserite ; i--  )
	{
		removeFormali('true',num_nc_inserite);
	}
	document.getElementById('valutazione_rischio_formali').value='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' FORMALI RISCONTRATE';
	if(document.getElementById('puntiFormali')!=null)
		document.getElementById('puntiFormali').value= num_nc_inserite;
	have_nc =true ;
	
	}
}

function resetSignificative_update(idControllo,tipo_nc,num_nc_inserite,followup_inseriti_significativi,nc_inserite,note_inserite)
{
	if (confirm('Sei sicuro di voler azzerare il lavoro svolto nelle non conformita\' Significative ?'))
	{
		array_nc_inserite = nc_inserite.split(";");	
		array_note_inserite = note_inserite.split(";");	
	elementi = document.getElementById('elementi_nc_significative') ;
	document.getElementById('stato_significative').value = 'true' ;
	document.getElementById('descrizione_or_combo_sel_significative').value = 'false' ;
	if(followup_inseriti_significativi!= "" )
		document.getElementById('abilita_significative').value = 'true' ;
	else
		document.getElementById('abilita_significative').value = 'false' ;
	for (i =elementi.value ; i>=num_nc_inserite ; i--  )
	{
		removeSignificative('true',num_nc_inserite);
	}
	
	have_nc =true ;
	/*for (i =1 ; i<=num_nc_inserite ; i++  )
	{
		document.getElementById('Provvedimenti2_'+i).disabled='';
		document.getElementById('Provvedimenti2_'+i).value = array_nc_inserite[(parseInt(i)-1)];
		document.getElementById('nonConformitaSignificative_'+i).value = array_note_inserite[(parseInt(i)-1)];
		document.getElementById('nonConformitaSignificative_'+i).readOnly='';
		
		if(num_nc_inserite=='1' && (array_nc_inserite[(parseInt(i)-1)] =='-1' ||array_nc_inserite[(parseInt(i)-1)] =='0' ) )
		{
			have_nc = false ;
		}
		
	}
	if(have_nc == true)
	{
		if(document.getElementById('puntiSignificativi')!=null)
			document.getElementById('puntiSignificativi').value= (parseInt(num_nc_inserite))*7;
	}
	else
	{
		document.getElementById('Provvedimenti2_1').value = '-1';
		document.getElementById('Provvedimenti2_1_selezionato').value = '-1';
		document.getElementById('nonConformitaSignificative_1').value = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
		if(document.getElementById('puntiSignificativi')!=null)
			document.getElementById('puntiSignificativi').value= '0';
	}*/
	document.getElementById('valutazione_rischio_significative').value='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' SIGNIFICATIVE RISCONTRATE';

  	
	}
}

function resetGravi_update(idControllo,tipo_nc,num_nc_inserite,followup_inseriti_gravi,nc_inserite,note_inserite)
{
	if (confirm('Sei sicuro di voler azzerare il lavoro svolto nelle non conformita\' Gravi ?'))
	{
	array_nc_inserite = nc_inserite.split(";");	
	array_note_inserite = note_inserite.split(";");	
	elementi = document.getElementById('elementi_nc_gravi') ;
	document.getElementById('stato_gravi').value = 'true' ;
	document.getElementById('descrizione_or_combo_sel_gravi').value = 'false' ;
	if(followup_inseriti_gravi!= "" )
		document.getElementById('abilita_gravi').value = 'true' ;
	else
		document.getElementById('abilita_gravi').value = 'false' ;
	for (i =elementi.value ; i>=num_nc_inserite ; i--  )
	{
		removeGravi('true',num_nc_inserite);
	}
	
	
	have_nc =true ;
	if (document.getElementById('valutazione_rischio_gravi')!=null)
		document.getElementById('valutazione_rischio_gravi').value='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' GRAVI RISCONTRATE';

	
	}
}

/**
 * FUNZIONE INVOCATA SULL'ONCLICK DEL PULSANTE RESET NC SIGNIFICATIVE
 * RIPRISTINA LA SITUAZIONE DELLE NC SIGNIFICATIVE COSI COME ERA INIZIALMENTE.
 * RIMUOVE LE NON CONFORMITA INSERITE DALL'UTENTE ,
 * SETTA LO STATO GLOBALE DELLE NC SIGNIFICATIVE A TRUE , 
 * CANCELLA LE SOTTOATTIVITA INSERITE .
 * @param idControllo
 * @param tipo_nc
 * @return
 */
function resetSignificative(idControllo,tipo_nc,domanda)
{
	if(domanda==true)
	{
	if (confirm('Sei sicuro di voler azzerare il lavoro svolto nelle non conformita\' Significative ?'))
	{
	elementi = document.getElementById('elementi_nc_significative') ;
	document.getElementById('stato_significative').value = 'true' ;
	document.getElementById('descrizione_or_combo_sel_significative').value = 'false' ;
	document.getElementById('abilita_significative').value = 'false' ;
	for (i =elementi.value ; i>1 ; i--  )
	{
		removeSignificative('true');
	}
	document.getElementById('valutazione_rischio_significative').value='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' SIGNIFICATIVE RISCONTRATE';
	if(document.getElementById('puntiSignificativi')!=null)
	document.getElementById('puntiSignificativi').value='0';
	document.getElementById('Provvedimenti2_'+elementi.value).disabled='';
	document.getElementById('Provvedimenti2_'+elementi.value+'_selezionato').value='-1';
	document.getElementById('Provvedimenti2_'+elementi.value).options[0].selected ='true';
  	document.getElementById('nonConformitaSignificative_'+elementi.value).readOnly='';
  	document.getElementById('nonConformitaSignificative_'+elementi.value).value='INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
  	//PopolaCombo.deleteAttivitaNc(document.getElementById('formali').value, idControllo,tipo_nc,document.getElementById('dosubmit').value);
  	document.getElementById('followup_inseriti_significativi').value=''
  	calcolaTotale();
	abilitaInserimentoTotale();
	
	
	}
	}
	else
	{
		elementi = document.getElementById('elementi_nc_significative') ;
		document.getElementById('stato_significative').value = 'true' ;
		document.getElementById('descrizione_or_combo_sel_significative').value = 'false' ;
		document.getElementById('abilita_significative').value = 'false' ;
		for (i =elementi.value ; i>1 ; i--  )
		{
			removeSignificative('true');
		}
		document.getElementById('valutazione_rischio_significative').value='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' SIGNIFICATIVE RISCONTRATE';
		document.getElementById('puntiSignificativi').value='0';
		document.getElementById('Provvedimenti2_'+elementi.value).disabled='';
		document.getElementById('Provvedimenti2_'+elementi.value+'_selezionato').value='-1';
		document.getElementById('Provvedimenti2_'+elementi.value).options[0].selected ='true';
	  	document.getElementById('nonConformitaSignificative_'+elementi.value).readOnly='';
	  	document.getElementById('nonConformitaSignificative_'+elementi.value).value='INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
	  	//PopolaCombo.deleteAttivitaNc(document.getElementById('formali').value, idControllo,tipo_nc,document.getElementById('dosubmit').value);
	  	document.getElementById('followup_inseriti_significativi').value=''
	  	calcolaTotale();
		abilitaInserimentoTotale();
		var clonato = document.getElementById('lista_followup_significativi');
	  
		
	}
	clonaFollowupSignificative();
}

/**
 * FUNZIONE INVOCATA SULL'ONCLICK DEL PULSANTE RESET NC GRAVI
 * RIPRISTINA LA SITUAZIONE DELLE NC GRAVI COSI COME ERA INIZIALMENTE.
 * RIMUOVE LE NON CONFORMITA INSERITE DALL'UTENTE ,
 * SETTA LO STATO GLOBALE DELLE NC GRAVI A TRUE , 
 * CANCELLA LE SOTTOATTIVITA INSERITE .
 * @param idControllo
 * @param tipo_nc
 * @return
 */
function resetGravi(idControllo,tipo_nc,domanda)
{
	if(domanda==true)
	{
	if (confirm('Sei sicuro di voler azzerare il lavoro svolto nelle non conformita\' Gravi ?'))
	{
	elementi = document.getElementById('elementi_nc_gravi') ;
	document.getElementById('stato_gravi').value = 'true' ;
	document.getElementById('descrizione_or_combo_sel_gravi').value = 'false' ;
	document.getElementById('abilita_gravi').value = 'false' ;
	for (i =elementi.value ; i>1 ; i--  )
	{
		removeGravi('true');
	}
	
	if (document.getElementById('valutazione_rischio_gravi')!=null)
		document.getElementById('valutazione_rischio_gravi').value='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' GRAVI RISCONTRATE';

	if(document.getElementById('puntiGravi')!=null)
		document.getElementById('puntiGravi').value='0';
	document.getElementById('Provvedimenti3_'+elementi.value).disabled='';
	document.getElementById('Provvedimenti3_'+elementi.value+'_selezionato').value='-1';
	document.getElementById('Provvedimenti3_'+elementi.value).options[0].selected ='true';
  	document.getElementById('nonConformitaGravi_'+elementi.value).readOnly='';
  	document.getElementById('nonConformitaGravi_'+elementi.value).value='INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
  	//PopolaCombo.deleteAttivitaNc(document.getElementById('formali').value, idControllo,tipo_nc,document.getElementById('dosubmit').value);
  	document.getElementById('attivita_inseriti_gravi').value=''
  	document.getElementById('followup_gravi_inseriti').value=''
  	calcolaTotale();
	abilitaInserimentoTotale();
	}
	}
	else
	{
		
		elementi = document.getElementById('elementi_nc_gravi') ;
		document.getElementById('stato_gravi').value = 'true' ;
		document.getElementById('descrizione_or_combo_sel_gravi').value = 'false' ;
		document.getElementById('abilita_gravi').value = 'false' ;
		for (i =elementi.value ; i>1 ; i--  )
		{
			removeGravi('true');
		}
		
		if (document.getElementById('valutazione_rischio_gravi')!=null)
			document.getElementById('valutazione_rischio_gravi').value='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' GRAVI RISCONTRATE';

		if(document.getElementById('puntiGravi')!=null)
			document.getElementById('puntiGravi').value='0';
		document.getElementById('Provvedimenti3_'+elementi.value).disabled='';
		document.getElementById('Provvedimenti3_'+elementi.value+'_selezionato').value='-1';
		document.getElementById('Provvedimenti3_'+elementi.value).options[0].selected ='true';
	  	document.getElementById('nonConformitaGravi_'+elementi.value).readOnly='';
	  	document.getElementById('nonConformitaGravi_'+elementi.value).value='INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
	  //	PopolaCombo.deleteAttivitaNc(document.getElementById('formali').value, idControllo,tipo_nc,document.getElementById('dosubmit').value);
	  	document.getElementById('attivita_inseriti_gravi').value=''
	  	document.getElementById('followup_gravi_inseriti').value=''
	  	calcolaTotale();
		abilitaInserimentoTotale();
	}
	clonaFollowupGravi();
}

function verificaChiusuraNc(flagChiusura,numSottoAttivita,msg2,idTicket,nomeAction)
{
	
if( flagChiusura!=null )
{
	if(flagChiusura=="1")
	{
		alert("Questa Non Conformita' non puo essere chiusa . Ci sono Attivita' collegate (Sanzioni o Note di Reato o Sequestri o Follow Up o Diffide) che non sono state ancora chiuse.");
	}	
}
else
{
	if(numSottoAttivita!=null && numSottoAttivita == 0 )
	{
		alert("Questa Non Conformita' non puo essere chiusa . Inserire almeno una delle sottoattivita' (SANZIONE , SEQUESTRO , NOTIZIA DI REATO O ALTRI FOLLOWUP o DIFFIDE).");
	}

}
if(msg2!='null')
{
	var answer = confirm("Tutte le Attivita Collegate al controllo sono state chiuse . \n Desideri Chiudere il Controllo Ufficiale ? \n Attenzione! La pratica verra\' chiusa e non sara\' piu\' possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore")
	if (answer)
	{

		doSubmit(idTicket,nomeAction);
	}
}
}


function verificaChiusuraNcAziendeAricole(flagChiusura,numSottoAttivita,msg2,idTicket,nomeAction,idstabilimento)
{
	
if( flagChiusura!=null )
{
	if(flagChiusura=="1")
	{
		alert("Questa Non Conformita' non puo essere chiusa . Ci sono Attivita' collegate (Sanzioni o Note di Reato o Sequestri o Follow Up o Diffide) che non sono state ancora chiuse.");
	}	
}
else
{
	if(numSottoAttivita!=null && numSottoAttivita == 0 )
	{
		alert("Questa Non Conformita' non puo essere chiusa . Inserire almeno una delle sottoattivita' (SANZIONE , SEQUESTRO , NOTIZIA DI REATO O ALTRI FOLLOWUP o DIFFIDE).");
	}

}
if(msg2!='null')
{
	var answer = confirm("Tutte le Attivita Collegate al controllo sono state chiuse . \n Desideri Chiudere il Controllo Ufficiale ? \n Attenzione! La pratica verra\' chiusa e non sara\' piu\' possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore")
	if (answer)
	{
		doSubmitAziendeAgricole(idTicket,nomeAction,idstabilimento);
	}
}
}

function doSubmit(idTicket,nomeAction,idstabilimento)
{
	//location.href=nomeAction+".do?command=Chiudi&stabId="+idstabilimento+"&id="+idTicket+"&chiudiCu=true'"
	document.details.action= nomeAction+".do?command=Chiudi&stabId="+idstabilimento+"&id="+idTicket+"&chiudiCu=true'";
	loadModalWindow();
	document.details.submit();
}


function doSubmitAziendeAgricole(idTicket,nomeAction,idstabilimento)
{
	location.href=nomeAction+".do?command=Chiudi&stabId="+idstabilimento+"&id="+idTicket+"&chiudiCu=true'"
	
}
	


function clonaFollowup()
{
	
  	var clonato = document.getElementById('lista_followup_formali');
  
  	
  	/*clona riga vuota*/
  
  	var array = document.getElementById('followup_inseriti_formali').value.split(";");
  	i = 0;
  		while(document.getElementById('lista_followup_formali_'+i)!=null)
  		{
  	  		document.getElementById('lista_followup_formali_'+i).parentNode.removeChild(document.getElementById('lista_followup_formali_'+i));
  	  		i++;
  		}
  	for (i=0;i<array.length;i++)
  	{
  		if(array[i]!='')
  		{
  		clone=clonato.cloneNode(true);
  		clone.getElementsByTagName('label')[0].innerHTML = "Followup "+(parseInt(i)+1);
  		clone.getElementsByTagName('label')[1].innerHTML = array[i];
  		clone.id="lista_followup_formali_"+i;
  		clone.style.display="";
  		clonato.parentNode.appendChild(clone);
  		}
  	}
	
  	
}

function clonaFollowupSignificative()
{
	
  	var clonato = document.getElementById('lista_followup_significativi');
  	var array = document.getElementById('followup_inseriti_significativi').value.split(";");
  	i = 0 ;
  		while(document.getElementById('lista_followup_significativi_'+i)!=null)
  		{
  	  		document.getElementById('lista_followup_significativi_'+i).parentNode.removeChild(document.getElementById('lista_followup_significativi_'+i));

  			i++ ;
  		}

  	
  	
  	for (i=0;i<array.length;i++)
  	{
  		if(array[i]!='')
  		{
  		clone=clonato.cloneNode(true);
  		clone.getElementsByTagName('label')[0].innerHTML = "Followup "+(parseInt(i)+1);
  		clone.getElementsByTagName('label')[1].innerHTML = array[i];
  		clone.id="lista_followup_significativi_"+i;
  		clone.style.display="";
  		clonato.parentNode.appendChild(clone);
  	}
  	}
}

function clonaFollowupGravi()
{
	
  	var clonato = document.getElementById('lista_followup_gravi');
  	var array = document.getElementById('attivita_inseriti_gravi').value.split(";");
  	i = 0 ;
  	while(document.getElementById('lista_followup_gravi_'+i)!=null)
  	{
  	  		document.getElementById('lista_followup_gravi_'+i).parentNode.removeChild(document.getElementById('lista_followup_gravi_'+i));
  			i++;
  	}
  	
  	var array_followup = document.getElementById('followup_gravi_inseriti').value.split(";");
  	num = 1;
	for (j=0;j<array_followup.length;j++)
  	{
		
		if(array_followup[j]!='')
  		{
			
		clone=clonato.cloneNode(true);
		clone.getElementsByTagName('label')[0].innerHTML = "Attivita'"+num+ " (Follow up)";
		clone.getElementsByTagName('label')[1].innerHTML = array_followup[j];
  		clone.id="lista_followup_gravi_"+j;
  		clone.style.display="";
  		clonato.parentNode.appendChild(clone);
  		num++;
  		}
  	}
	
  	
  	for (i=0;i<array.length;i++)
  	{
  		if(array[i]!='')
  		{
  			
  		var array2 = array[i].split("-");
  		clone=clonato.cloneNode(true);
  		if(array2[1]=='san')
  		{
  			clone.getElementsByTagName('label')[0].innerHTML = "Attivita'"+num+ " (Sanzione)";
  		}
  		if(array2[1]=='seq')
  		{
  			clone.getElementsByTagName('label')[0].innerHTML = "Attivita'"+num+ " (Sequestro)";
  		}
  		if(array2[1]=='rea')
  		{
  			clone.getElementsByTagName('label')[0].innerHTML = "Attivita'"+num+ " (Nota di Reato)";
  		}
  		if(array2[1]=='fol')
  		{
  			clone.getElementsByTagName('label')[0].innerHTML = "Attivita'"+num+ " (Followup)";
  		}
  		if(array2[1]=='dif')
  		{
  			clone.getElementsByTagName('label')[0].innerHTML = "Attivita'"+num+ " (Diffida)";
  		}
  		
  		clone.getElementsByTagName('label')[1].innerHTML = array2[0];
  		clone.id="lista_followup_gravi_"+i;
  		clone.style.display="";
  		clonato.parentNode.appendChild(clone);
  		num++;
  		}
  	}
	

  	
}
			