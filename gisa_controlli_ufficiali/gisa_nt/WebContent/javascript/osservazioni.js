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
			message += "- Valore errato per il campo Punteggio per le Osservazioni/Raccomandazioni Formali. Si prega di inserire solo cifre\r\n";
			formTest = false;
		}		 

	}    
//	if (form.assignedDate.value == "") {
//		message += label("check.nonconformita.data_richiesta.selezionato","- Controllare che il campo \"Data \" sia stato popolato\r\n");
//		formTest = false;
//	}
	if (document.getElementById('Provvedimenti1_1').options == null || (document.getElementById('Provvedimenti1_1').options[0].selected==true && document.getElementById('Provvedimenti2_1').options[0].selected==true && document.getElementById('Provvedimenti3_1').options[0].selected==true))
	{
		message += label("check.nonconformita.richiedente.selezionato","- Controllare di aver Inserito almeno un oggetto dell'AUDIT\'\'\r\n");
		formTest = false;
	}

	if ((elementoCheccato(document.getElementById('Provvedimenti1_1'))==false && elementoCheccato(document.getElementById('Osservazioni_Formali'))==true) || 
	(elementoCheccato(document.getElementById('Provvedimenti1_1'))==true && elementoCheccato(document.getElementById('Osservazioni_Formali'))==false)	
	)
	
	{
		
		message += label("check.nonconformita.richiedente.selezionato","- Controllare di aver Inserito tutti i campi per Osservazioni\'	\'\r\n");
		formTest = false;
	}

//	if (document.getElementById('Provvedimenti1_1').options[0].selected==true && document.getElementById('Osservazioni_Formali').options[0].selected==true)
//	{
//		message += label("check.nonconformita.richiedente.selezionato","- Controllare di aver Inserito almeno una osservazione formale e l\'oggetto dell'AUDIT \'\'\r\n");
//		formTest = false;
//	}

	if ((elementoCheccato(document.getElementById('Provvedimenti2_1'))==false && elementoCheccato(document.getElementById('Osservazioni_Significative'))==true) || 
			(elementoCheccato(document.getElementById('Provvedimenti2_1'))==true && elementoCheccato(document.getElementById('Osservazioni_Significative'))==false)	
			)
			
			{
				
				message += label("check.nonconformita.richiedente.selezionato","- Controllare di aver Inserito tutti i campi per Osservazioni Significative\'	\'\r\n");
				formTest = false;
			}
	
	
	if ((elementoCheccato(document.getElementById('Provvedimenti3_1'))==false && elementoCheccato(document.getElementById('Osservazioni_Gravi'))==true) || 
			(elementoCheccato(document.getElementById('Provvedimenti3_1'))==true && elementoCheccato(document.getElementById('Osservazioni_Gravi'))==false)	
			)
			
			{
				
				message += label("check.nonconformita.richiedente.selezionato","- Controllare di aver Inserito tutti i campi per Osservazioni Gravi\'	\'\r\n");
				formTest = false;
			}

	if (formTest == false) {
		alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
		return false;
	} else {
		if (document.getElementById('dosubmit')!=null)
		{
			document.getElementById('dosubmit').value = "1";

		}
		//loadModalWindow();
		
		
		return true;
	}
}

function elementoCheccato(combo)
{
ceccato = false ;
	opt = combo.options ;
for (i=0;i<opt.length;i++)
{
if (opt[i].value!=-1 && opt[i].selected)
{
	ceccato=true;
	break;
}
}
return ceccato ;
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
	
	if (document.getElementById('puntiFormali')!=null)
	{
		
	var punti = 0;
	for (j=0 ; j<idElementi;j++)
	{
	array = document.getElementById('Provvedimenti1_1').options;
	for(i=0; i<array.length; i++){
		if(array[i].value!="-1" && array[i].selected==true )
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
	array = document.getElementById('Provvedimenti2_1');
	for(i=0; i<array.length; i++){
		if(array[i].value!="-1" && array[i].selected==true )
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
	array = document.getElementById('Provvedimenti3_1');
	for(i=0; i<array.length; i++){
		if(array[i].value!="-1" && array[i].selected==true )
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
function clonaOsservazioni_Formali(){
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
	
  		
  	if  ( document.getElementById('Provvedimenti1_'+elementi.value).value != '-1' 		
  		)
  	{
  	document.getElementById('Provvedimenti1_'+elementi.value).disabled='true';
  
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
 
  	clone.getElementsByTagName('input')[0].name = "Provvedimenti1_"+elementi.value+"_selezionato";
  	clone.getElementsByTagName('input')[0].id = "Provvedimenti1_"+elementi.value+"_selezionato";
	clone.getElementsByTagName('input')[0].value = "-1";
 
 
  	clone.id = "nc_formali_"+elementi.value;
  	clonato.parentNode.appendChild(clone);
  	clone.style.visibility="visible";
  	}
  	else{
  		alert('Compilare Tutte le voci per la scheda\' corrente prima di aggiungerne una nuova!')
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
	
  	if  ( document.getElementById('Provvedimenti2_'+elementi.value).value != '-1' 
  	  	)
  	{
  	document.getElementById('Provvedimenti2_'+elementi.value).disabled='true';
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
  		alert('Selezionare una voce Per la scheda corrente Significativa Corrente, e successivamente aggiungere!')
  		
  	}
  	
  	}
  }

/**
 * FUNZIONE DI AGGIUNTA DI UNA NC GRAVE
 * @return
 */
function clonaNC_Gravi(){
  	var maxElementi = 15;
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
	
  		if  ( document.getElementById('Provvedimenti3_'+elementi.value).value != '-1' 			
  	  			  	)
  	  	{
  	document.getElementById('Provvedimenti3_'+elementi.value).disabled='true';
  	
  		
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
  		alert('Selezionare una voce Per la scheda corrente Grave Corrente, e successivamente aggiungere!')
  		
  	}
  	
  	}
  }

/**
 * RIMUOVE UNA NC GRAVE INVOCATA SULL'ONCLICK DEL PULSANTE ELIMINA NC FORMALE
 * @return
 */
function removeOsservazioni(reset,num_nc_inserite)
{
	elementi = document.getElementById('elementi_nc_formali');
	if (parseInt(elementi.value)>1 && document.getElementById('Provvedimenti1_'+elementi.value).disabled==false)
	{
		
	document.getElementById('nc_formali_'+elementi.value).parentNode.removeChild(document.getElementById('nc_formali_'+elementi.value));
	document.getElementById('elementi_nc_formali').value = parseInt(elementi.value)-1;
	calcolaPuntiFormali(document.getElementById('elementi_nc_formali').value,1);
	document.getElementById('Provvedimenti1_'+elementi.value).disabled='';
	
	}
	else
	{
		if (parseInt(elementi.value)==1 && document.getElementById('Provvedimenti1_'+elementi.value).disabled==false)
		{
			var msg_default = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
		
		document.getElementById('Provvedimenti1_'+elementi.value).value = -1 ;
		if(document.getElementById('Provvedimenti1_'+elementi.value+'_selezionato')!=null)
			document.getElementById('Provvedimenti1_'+elementi.value+'_selezionato').value='-1';
		
		} 
		else
			if (reset == 'true')
			{
				document.getElementById('Provvedimenti1_'+elementi.value).disabled='';
				
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
	calcolaPuntiSignificative(document.getElementById('elementi_nc_significative').value,7);
	document.getElementById('Provvedimenti2_'+elementi.value).disabled='';
	}
	else
	{
		if (parseInt(elementi.value)==1 && document.getElementById('Provvedimenti2_'+elementi.value).disabled==false)
		{
			var msg_default = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
		document.getElementById('Provvedimenti2_'+elementi.value).value = -1 ;
		
		if(document.getElementById('Provvedimenti2_'+elementi.value+'_selezionato')!=null)
			document.getElementById('Provvedimenti2_'+elementi.value+'_selezionato').value='-1';
		
		} 
		else
			if (reset == 'true' )
			{
			document.getElementById('Provvedimenti2_'+elementi.value).disabled='';
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
	calcolaPuntiGravi(document.getElementById('elementi_nc_gravi').value,25);
	document.getElementById('Provvedimenti3_'+elementi.value).disabled='';
	}
	else
	if (parseInt(elementi.value)==1 && document.getElementById('Provvedimenti3_'+elementi.value).disabled==false)
	{
		var msg_default = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
		document.getElementById('Provvedimenti3_'+elementi.value).value = -1 ;
		
		if(document.getElementById('Provvedimenti3_'+elementi.value+'_selezionato')!=null)
			document.getElementById('Provvedimenti3_'+elementi.value+'_selezionato').value='-1';
	} 
	else
	if (reset == 'true' )
	{
	document.getElementById('Provvedimenti3_'+elementi.value).disabled='';
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
/**
 * ABILITA PER LE NON CONFORMITA GRAVI IL FLAG ABILITA_GRAVI E descrizione_or_combo_sel_gravi
 * SE PER ALMENO UNA NC TRA QUELLE AGGIUNTE DALL'UTENTE ESISTE UNA PER LA QUALE � STATA SELEZIONATA
 * UNA VOCE DALLA COMBO E INSERITA UNA DESCRIZIONE VIENE SETTATO A TRUE IL FLAG ABILITA_GRAVI.
 * SE INVECE NE ESISTE UNA PER LA QUALE � STATA INSERITA UNA DESCRIZIONE E NON SELEZIONATA UNA VOCE DALLA COMBO
 * O VICEVERSA,VIENE SETTATO A TRUE IL FLAG descrizione_or_combo_sel_gravi
 * @return
 */


/**
 * FUNZIONE INVOCATA ALL'USCITA DELLA PAGINA SE NON SI HA INSERITO LA NC.
 * CANCELLA TUTTE LE SOTTOATTIVITA INSERITE , IN MODO DA NON RIMANERLE APPESE
 * (NON ASSOCIATE A NESSUNA NON CONFORMITA)
 * @param idControllo
 * @return
 */
function deleteAttivita(idControllo,doSubmit)
{
	
		if (document.getElementById('dosubmit').value =='0')
		{
		
		
		
		}
}
	
function deleteAttivitaCallBack(value){
	
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
function resetOsservazioni(idControllo,tipo_nc,num_nc_inserite,domanda)
{	
	if(domanda==true)
	{
	if (confirm('Sei sicuro di voler azzerare il lavoro svolto nelle Osservazioni ?'))
	{
	elementi = document.getElementById('elementi_nc_formali') ;
	document.getElementById('stato_formali').value = 'true' ;
	document.getElementById('descrizione_or_combo_sel_formali').value = 'false' ;
	for (i =elementi.value ; i>=1 ; i--  )
	{
		removeOsservazioni('true',num_nc_inserite);
	}
	
	if(document.getElementById('puntiFormali')!=null)
		document.getElementById('puntiFormali').value='0';
	document.getElementById('Provvedimenti1_'+elementi.value).disabled='';
	document.getElementById('Provvedimenti1_'+elementi.value+'_selezionato').value='-1';
	document.getElementById('Provvedimenti1_'+elementi.value).options[0].selected ='true';
  	
  	
  	
  	calcolaTotale();
  	abilitaInserimentoTotale();
  	
	}
	}
	else
	{
		elementi = document.getElementById('elementi_nc_formali') ;
		document.getElementById('stato_formali').value = 'true' ;
		document.getElementById('descrizione_or_combo_sel_formali').value = 'false' ;
		for (i =elementi.value ; i>1 ; i--  )
		{
			removeOsservazioni('true',num_nc_inserite);
		}
		if(document.getElementById('puntiFormali')!=null)
			document.getElementById('puntiFormali').value='0';
		document.getElementById('Provvedimenti1_'+elementi.value).disabled='';
		document.getElementById('Provvedimenti1_'+elementi.value+'_selezionato').value='-1';
		document.getElementById('Provvedimenti1_'+elementi.value).options[0].selected ='true';
	  	
	  	
	  	
	  
	  	calcolaTotale();
	  	abilitaInserimentoTotale();
	  
	  }
	clonaFollowup();
}



function resetFormali_update(idControllo,tipo_nc,num_nc_inserite,followup_inseriti_formali,nc_inserite,note_inserite)
{
	if (confirm('Sei sicuro di voler azzerare il lavoro svolto nelle Osservazioni ?'))
	{
		array_nc_inserite = nc_inserite.split(";");	
		array_note_inserite = note_inserite.split(";");	
	elementi = document.getElementById('elementi_nc_formali') ;
	document.getElementById('stato_formali').value = 'true' ;
	document.getElementById('descrizione_or_combo_sel_formali').value = 'false' ;

	for (i =elementi.value ; i>=num_nc_inserite ; i--  )
	{
		removeOsservazioni('true',num_nc_inserite);
	}
	
	if(document.getElementById('puntiFormali')!=null)
		document.getElementById('puntiFormali').value= num_nc_inserite;
	have_nc =true ;
	
	}
}

function resetSignificative_update(idControllo,tipo_nc,num_nc_inserite,followup_inseriti_significativi,nc_inserite,note_inserite)
{
	if (confirm('Sei sicuro di voler azzerare il lavoro svolto nelle Raccomandazioni Significative ?'))
	{
		array_nc_inserite = nc_inserite.split(";");	
		array_note_inserite = note_inserite.split(";");	
	elementi = document.getElementById('elementi_nc_significative') ;
	document.getElementById('stato_significative').value = 'true' ;
	document.getElementById('descrizione_or_combo_sel_significative').value = 'false' ;
	if(followup_inseriti_significativi!= "" )

	for (i =elementi.value ; i>=num_nc_inserite ; i--  )
	{
		removeSignificative('true',num_nc_inserite);
	}
	
	have_nc =true ;
	
	}
}

function resetGravi_update(idControllo,tipo_nc,num_nc_inserite,followup_inseriti_gravi,nc_inserite,note_inserite)
{
	if (confirm('Sei sicuro di voler azzerare il lavoro svolto nelle Raccomandazioni Gravi ?'))
	{
	array_nc_inserite = nc_inserite.split(";");	
	array_note_inserite = note_inserite.split(";");	
	elementi = document.getElementById('elementi_nc_gravi') ;
	document.getElementById('stato_gravi').value = 'true' ;
	document.getElementById('descrizione_or_combo_sel_gravi').value = 'false' ;
	
	for (i =elementi.value ; i>=num_nc_inserite ; i--  )
	{
		removeGravi('true',num_nc_inserite);
	}
	
	
	have_nc =true ;
	

	/*for (i =1 ; i<=num_nc_inserite ; i++  )
	{
		document.getElementById('Provvedimenti3_'+i).disabled='';
		document.getElementById('Provvedimenti3_'+i).value = array_nc_inserite[(parseInt(i)-1)];
		document.getElementById('nonConformitaGravi_'+i).value = array_note_inserite[(parseInt(i)-1)];
		document.getElementById('nonConformitaGravi_'+i).readOnly='';
		if(num_nc_inserite=='1' && (array_nc_inserite[(parseInt(i)-1)] =='-1' ||array_nc_inserite[(parseInt(i)-1)] =='0' ) )
		{
			have_nc = false ;
		}
	}
	if(have_nc == true)
	{
		if(document.getElementById('puntiGravi')!=null)
		document.getElementById('puntiGravi').value= (parseInt(num_nc_inserite))*25;
	}
	else
	{
		document.getElementById('Provvedimenti3_1_selezionato').value = '-1';
		document.getElementById('Provvedimenti3_1').value = '-1';
		document.getElementById('nonConformitaGravi_1').value = 'INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\'';
		if(document.getElementById('puntiGravi')!=null)
			document.getElementById('puntiGravi').value= '0';
	}
  	
  	PopolaCombo.deleteAttivitaNc(document.getElementById('formali').value, idControllo,tipo_nc,document.getElementById('dosubmit').value);
  	document.getElementById('attivita_inseriti_gravi').value=followup_inseriti_gravi
  	document.getElementById('followup_gravi_inseriti').value='';
  	calcolaTotale();
  	abilitaInserimentoTotale();
	clonaFollowupGravi();*/
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
	if (confirm('Sei sicuro di voler azzerare il lavoro svolto nelle Raccomandazioni Significative ?'))
	{
	elementi = document.getElementById('elementi_nc_significative') ;
	document.getElementById('stato_significative').value = 'true' ;
	document.getElementById('descrizione_or_combo_sel_significative').value = 'false' ;
	for (i =elementi.value ; i>1 ; i--  )
	{
		removeSignificative('true');
	}
	
	if(document.getElementById('puntiSignificativi')!=null)
	document.getElementById('puntiSignificativi').value='0';
	document.getElementById('Provvedimenti2_'+elementi.value).disabled='';
	document.getElementById('Provvedimenti2_'+elementi.value+'_selezionato').value='-1';
	document.getElementById('Provvedimenti2_'+elementi.value).options[0].selected ='true';
  	calcolaTotale();
	abilitaInserimentoTotale();
	
	
	}
	}
	else
	{
		elementi = document.getElementById('elementi_nc_significative') ;
		document.getElementById('stato_significative').value = 'true' ;
		document.getElementById('descrizione_or_combo_sel_significative').value = 'false' ;
		for (i =elementi.value ; i>1 ; i--  )
		{
			removeSignificative('true');
		}
		
		document.getElementById('puntiSignificativi').value='0';
		document.getElementById('Provvedimenti2_'+elementi.value).disabled='';
		document.getElementById('Provvedimenti2_'+elementi.value+'_selezionato').value='-1';
		document.getElementById('Provvedimenti2_'+elementi.value).options[0].selected ='true';
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
	if (confirm('Sei sicuro di voler azzerare il lavoro svolto nelle Raccomandazioni Gravi ?'))
	{
	elementi = document.getElementById('elementi_nc_gravi') ;
	document.getElementById('stato_gravi').value = 'true' ;
	document.getElementById('descrizione_or_combo_sel_gravi').value = 'false' ;
	for (i =elementi.value ; i>1 ; i--  )
	{
		removeGravi('true');
	}
	
	

	if(document.getElementById('puntiGravi')!=null)
		document.getElementById('puntiGravi').value='0';
	document.getElementById('Provvedimenti3_'+elementi.value).disabled='';
	document.getElementById('Provvedimenti3_'+elementi.value+'_selezionato').value='-1';
	document.getElementById('Provvedimenti3_'+elementi.value).options[0].selected ='true';

	
  	calcolaTotale();
	abilitaInserimentoTotale();
	}
	}
	else
	{
		
		elementi = document.getElementById('elementi_nc_gravi') ;
		document.getElementById('stato_gravi').value = 'true' ;
		document.getElementById('descrizione_or_combo_sel_gravi').value = 'false' ;
		for (i =elementi.value ; i>1 ; i--  )
		{
			removeGravi('true');
		}
		

		if(document.getElementById('puntiGravi')!=null)
			document.getElementById('puntiGravi').value='0';
		document.getElementById('Provvedimenti3_'+elementi.value).disabled='';
		document.getElementById('Provvedimenti3_'+elementi.value+'_selezionato').value='-1';
		document.getElementById('Provvedimenti3_'+elementi.value).options[0].selected ='true';
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
		alert("Questa Schedanon puo essere chiusa . Ci sono Attivita' collegate (Sanzioni o Note di Reato o Sequestri o Follow Up) che non sono state ancora chiuse.");
	}	
}


	var answer = confirm("Tutte le Attivita Collegate al controllo sono state chiuse . \n Desideri Chiudere il Controllo Ufficiale ? \n Attenzione! La pratica verra\' chiusa e non sara\' piu\' possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore")
	if (answer)
	{
		doSubmit(idTicket,nomeAction);
	}

}

function doSubmit(idTicket,nomeAction)
{
	document.details.action=nomeAction+".do?command=Chiudi&id="+idTicket+"&chiudiCu=true'"
	document.details.submit();
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
  		
  		clone.getElementsByTagName('label')[1].innerHTML = array2[0];
  		clone.id="lista_followup_gravi_"+i;
  		clone.style.display="";
  		clonato.parentNode.appendChild(clone);
  		num++;
  		}
  	}
	

  	
}
			