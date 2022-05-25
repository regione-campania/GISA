<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script>
function verificaStatoChecklist(isBozza){
	
	if(isBozza == 'false' ){
		var f = document.forms['myform'];
		for(var i=0,fLen=f.length;i<fLen;i++){

			if (f.elements[i].type == 'radio' || f.elements[i].type=='checkbox')
		    { 
		          f.elements[i].disabled = true;
		    } 
			else if (f.elements[i].type == 'submit')
		    { 
		          
		    } 
		    else {
			    
		  		f.elements[i].readOnly = true;
		  		f.elements[i].className = 'layout';
		    }
		}
		var g = document.forms['myform'].getElementsByTagName("textarea");
		for(var j=0; j < g.length; j++)
			 g.item(j).className = '';

		document.getElementById('salvaTemporaneo').style.display = 'none';
		document.getElementById('salvaDefinitivo').style.display = 'none';
		document.getElementById('stampa').style.display = 'block';
		window.opener.loadModalWindow();
       // window.opener.location.reload();
       window.opener.location.href="AllevamentiVigilanza.do?command=TicketDetails&id=<%=Ticket.getId()%>";
	}
}

function saveForm(form, tipo){
	
	if (tipo=='temp'){
		if(confirm('La scheda sarà aggiornata come richiesto. Vuoi procedere con il salvataggio?')) {
		 form.bozza.value = "true";
		 loadModalWindow();
		 form.submit();
		}
	}
	else {
		
		//QUI VANNO MESSI TUTTI I CONTROLLI DI CONGRUENZA
		var check = true;
		check = checkForm(form);
		if (!check)
			return false;
		
		if(confirm('La scheda sarà aggiornata come richiesto ma i dati non saranno più modificabili. Vuoi procedere con il salvataggio definitivo?')) {
		 form.bozza.value = "false";
		 loadModalWindow();
		 form.submit();
		}
	}
	
}

function checkForm(form){
	var esito = true;
	var msg = 'Impossibile salvare. Controllare i seguenti campi: \n\n';
		
	var esitoControllo = form.esitoControllo.value;
	var	extrapiano = form.extrapiano.value;
	var nomeControllore = form.nomeControllore.value;
	var nomeProprietario = form.nomeProprietario.value;
	var criteriUtilizzati = form.criteriUtilizzati.value;
	var criteriUtilizzatiAltroDescrizione = form.criteriUtilizzatiAltroDescrizione.value;
	var sanzAltro= form.sanzAltro.value;
	var sanzAltroDesc= form.sanzAltroDesc.value;	
	var dataPrimoControllo = form.dataPrimoControllo.value;
	var appartenenteCondizionalita = form.appartenenteCondizionalita.value;
	var	evidenze = form.evidenze.value;
	var	evidenzeIr = form.evidenzeIr.value;
	var	evidenzeTse = form.evidenzeTse.value;
	var	evidenzeSv = form.evidenzeSv.value;
	
	var dataVerifica = form.dataVerifica.value;
	var dataChiusura = form.dataChiusuraRelazione.value;
	var dataControllo = form.dataControllo.value;
	
	if ((dataChiusura != '' && dataVerifica == '') || (dataChiusura == '' && dataVerifica != '')){
		msg+="[DATA CHIUSURA RELAZIONE DI CONTROLLO, DATA VERIFICA IN LOCO] Valorizzare o svuotare entrambi i campi.\n";
		esito = false;
	}
	else {
		if (checkDate(dataChiusura, dataVerifica)==1){
			msg+="[DATA CHIUSURA RELAZIONE DI CONTROLLO] Deve essere maggiore o uguale a [DATA VERIFICA IN LOCO].\n";
			esito = false;
		}
		if (checkDate(dataChiusura, dataControllo)==1){
			msg+="[DATA CHIUSURA RELAZIONE DI CONTROLLO] Deve essere maggiore o uguale a [DATA DEL CONTROLLO].\n";
			esito = false;
		}
		
		if (checkDate(dataVerifica, dataControllo)==1){
			msg+="[DATA VERIFICA IN LOCO] Deve essere maggiore o uguale a [DATA DEL CONTROLLO].\n";
			esito = false;
		}
	}
	
	if (nomeControllore == ''){
		msg+="[NOME E COGNOME DEL CONTROLLORE] Obbligatorio.\n";
		esito = false;
	}
	if (nomeProprietario == ''){
		msg+="[NOME E COGNOME DEL PROPRIETARIO/DETENTORE/CONDUTTORE PRESENTE ALL'ISPEZIONE] Obbligatorio.\n";
		esito = false;
	}
	if (criteriUtilizzati == ''){
		msg+="[Selezionare i criteri utilizzati per la selezione dell'allevamento sottoposto a controllo] Obbligatorio.\n";
		esito = false;
	}
	if (criteriUtilizzati == '997' && criteriUtilizzatiAltroDescrizione == ''){
		msg+="[Descrizione altro criterio utilizzato] Obbligatorio.\n";
		esito = false;
	}
	
	if (sanzAltro != ''  && sanzAltroDesc == ''){
		msg+="[Descrizione Sanzioni Altro] Obbligatorio.\n";
		esito = false;
	}
	if (dataPrimoControllo == ''){
		msg+="[DATA PRIMO CONTROLLO IN LOCO] Obbligatoria.\n";
		esito = false;
	}
	if (appartenenteCondizionalita == ''){
		msg+="[CONTROLLO APPARTENENTE/NON APPARTENENTE AL CAMPIONE CONDIZIONALITA'] Obbligatorio.\n";
		esito = false;
	}
	if (extrapiano == ''){
		msg+="[EXTRAPIANO] Obbligatorio.\n";
		esito = false;
	}
	
	if (evidenze == ''){
		msg+="[Elementi di possibile non conformita'] Indicare SI o NO.\n";
		esito = false;
	}
	
	if (esitoControllo == 'A'){ // mancato controllo
		
		msg+= "\nErrori per salvataggio definitivo su esito: SFAVOREVOLE PER MANCATO/RIFIUTATO CONTROLLO.\n\n";
		
		if (evidenze == 'S'){
			msg+="[Elementi di possibile non conformita' relativi al sistema di identificazione e registrazione animale, alla sicurezza alimentare e alle TSE ovvero all'impiego di sostanze vietate] Non consentito rispondere SI.\n";
			esito = false;
		}
		
		if (dataVerifica != ''){
			msg+="[DATA VERIFICA] Deve essere vuoto.\n";
			esito = false;
		}
			
	}
	else if (esitoControllo == 'N'){ //sfavorevole
		
		if ((evidenze != '' && evidenze =='S') && (evidenzeIr=='' && evidenzeTse == '' && evidenzeSv == '')){
			msg+="Avendo selezionato [Elementi di possibile non conformita'] e' necessario indicare almeno uno tra [Sistema di identificazione e registrazione animale] [Sicurezza alimentare e TSE] [Sostanze vietate].\n";
			esito = false;
		}
		if ((evidenze == '' || evidenze == 'N') && (evidenzeIr!='' || evidenzeTse != '' || evidenzeSv != '')){
			msg+="Non avendo selezionato [Elementi di possibile non conformita'] e' necessario svuotare [Sistema di identificazione e registrazione animale] [Sicurezza alimentare e TSE] [Sostanze vietate].\n";
			esito = false;
		}
		
	}
	else if (esitoControllo == 'S'){ //favorevole
		
		if ((evidenze != '' && evidenze =='S') && (evidenzeIr=='' && evidenzeTse == '' && evidenzeSv == '')){
			msg+="Avendo selezionato [Elementi di possibile non conformita'] e' necessario indicare almeno uno tra [Sistema di identificazione e registrazione animale] [Sicurezza alimentare e TSE] [Sostanze vietate].\n";
			esito = false;
		}
		if ((evidenze == '' || evidenze == 'N') && (evidenzeIr!='' || evidenzeTse != '' || evidenzeSv != '')){
			msg+="Non avendo selezionato [Elementi di possibile non conformita'] e' necessario svuotare [Sistema di identificazione e registrazione animale] [Sicurezza alimentare e TSE] [Sostanze vietate].\n";
			esito = false;
		}
		
		if (dataVerifica != ''){
			msg+="[DATA VERIFICA] Deve essere vuoto.\n";
			esito = false;
		}
		
		if (!tutteDomandeRisposte()){
			msg+="[ELEMENTO DI VERIFICA] Rispondere ed indicare le evidenze di tutte le domande.\n";
			esito = false;
		}
			
	}
	else {
		msg+="[ESITO DEL CONTROLLO] Obbligatorio.";
		esito = false;
	}
	
	if (!esito)
		alert(msg);
	return esito;
}

function checkDate(data_iniziale, data_finale){
	var arr1 = data_iniziale.split("-");
	var arr2 = data_finale.split("-");

	var d1 = new Date(arr1[0],arr1[1]-1,arr1[2]);
	var d2 = new Date(arr2[0],arr2[1]-1,arr2[2]);
	
	var r1 = d1.getTime();
	var r2 = d2.getTime();
	
	if (r1<r2)
		return 1;
	else
		return 2;
	}

function filtraInteri(campo){
	campo.value=campo.value.replace(/[^0-9]+/,'')
}

function filtraDecimali(campo){
	campo.value=campo.value.replace(/[^0-9.]+/,'')
}

function tutteDomandeRisposte(){
	
	<%for (int i = 0; i<DomandeList.size(); i++) {
		org.aspcf.modules.checklist_benessere.base.v4.Domanda domanda = (org.aspcf.modules.checklist_benessere.base.v4.Domanda) DomandeList.get(i);%>
	
		var x = document.getElementsByName("dom_<%=domanda.getId()%>_risposta");
		var selezionato = false;
		
		for (var j = 0; j<x.length; j++){
			if (x[j].checked)
				selezionato = true;
		}
		
		document.getElementById("tr_<%=domanda.getId()%>").style.background='';
		if (!selezionato){
			document.getElementById("tr_<%=domanda.getId()%>").style.background='red';
			return false;
		}
		selezionato = false;
		
		<% } %>	
		return true;
	}

function aggiornaIrregolarita(idDomanda){
	
	var irr = document.getElementById("dom_"+idDomanda+"_irregolarita");
	var a = document.getElementById("dom_"+idDomanda+"_provv_a").value;
	var b = document.getElementById("dom_"+idDomanda+"_provv_b").value;
	var c = document.getElementById("dom_"+idDomanda+"_provv_c").value;
	irr.value = Number(a) + Number(b) + Number(c);
}


function rispondiCaso() {
	
	 var nomi = ["Rita", "Paolo", "Stefano", "Alessandro", "Uolter", "Antonio", "Carmela", "Viviana", "Valentino", "Rischio", "Impresa", "Vittoria", "Mandarino", "Ext", "US", "Caffe", "Altrove", "SPA", "Food", "Privata", "Coffee", "Angolo", "Bar"];
	 var inputs = document.getElementsByTagName('input');
	 var inputNamePrecedente="";
    for (i = 0; i < inputs.length; i++) {
    	if (!inputs[i].disabled) {
        if (inputs[i].type == 'radio' || inputs[i].type == 'checkbox') {
        	var random = Math.floor(Math.random() * 11);
          	 	if (random>5 || inputNamePrecedente!=inputs[i].name)
           			inputs[i].click();
        	}
        else if (inputs[i].type == 'text') {
        	if($(inputs[i]).attr("onkeyup")=='filtraInteri(this)'){
           		inputs[i].value = Math.floor((Math.random() * nomi.length-1) + 1) +''+ Math.floor((Math.random() * nomi.length-1) + 1) +''+ Math.floor((Math.random() * nomi.length-1) + 1);
        	}
        	else{
        		 var maxlength = inputs[i].maxLength;
    	    	 var val = nomi[Math.floor((Math.random() * nomi.length-1) + 1)] + " " + nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
    	    	 if (maxlength > 0 && val.length>maxlength)
    	    		 val = val.substring(1, maxlength-1);
    	    	 inputs[i].value = val;
        	}
    	}
        else if (inputs[i].type == 'number') {
        	var random1 = Math.floor(Math.random() * 11);
        	var random2 = Math.floor(Math.random() * 11);

        	if($(inputs[i]).attr("step")=='.01')
	        	inputs[i].value = random1+'.'+random2;
	        else
	        	inputs[i].value = random1;
    	}
        
        else if (inputs[i].type == 'date') {
        	
        	var date = new Date();
        	var currentDate = date.toISOString().slice(0,10);
			inputs[i].value = currentDate;
    	}
        
        inputNamePrecedente = inputs[i].name;

        ev = document.createEvent('Event');
        ev.initEvent('change', true, false);
        inputs[i].dispatchEvent(ev);
    }
    }
   		
}

function rispondiSoloDomande() {
	
	 var nomi = ["Rita", "Paolo", "Stefano", "Alessandro", "Uolter", "Antonio", "Carmela", "Viviana", "Valentino", "Rischio", "Impresa", "Vittoria", "Mandarino", "Ext", "US", "Caffe", "Altrove", "SPA", "Food", "Privata", "Coffee", "Angolo", "Bar"];
	 var inputs = document.getElementsByTagName('input');
	 var inputNamePrecedente="";
  	for (i = 0; i < inputs.length; i++) {
  		if (!inputs[i].disabled) {
  		if (inputs[i].name.indexOf("dom_")>=0) {
  	    	
	       if (inputs[i].type == 'radio' || inputs[i].type == 'checkbox') {
	       	var random = Math.floor(Math.random() * 11);
	         	 	if (random>5 || inputNamePrecedente!=inputs[i].name)
	          			inputs[i].click();
	       	}
	       else if (inputs[i].type == 'text') {
	    	 var maxlength = inputs[i].maxLength;
	    	 var val = nomi[Math.floor((Math.random() * nomi.length-1) + 1)] + " " + nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
	    	 if (maxlength>0 && val.length>maxlength)
	    		 val = val.substring(1, maxlength-1);
	    	 inputs[i].value = val;
	   	}
	       else if (inputs[i].type == 'number') {
	       	var random1 = Math.floor(Math.random() * 11);
	       	var random2 = Math.floor(Math.random() * 11);
	
	       	if($(inputs[i]).attr("step")=='.01')
		        	inputs[i].value = random1+'.'+random2;
		        else
		        	inputs[i].value = random1;
	   	}
	       
	       else if (inputs[i].type == 'date') {
	       	
	       	var date = new Date();
	       	var currentDate = date.toISOString().slice(0,10);
				inputs[i].value = currentDate;
	   	}
	       
	       inputNamePrecedente = inputs[i].name;
	         }
  	} }
}

function checkRisposta(val, iddom){
	
	var numIrr = document.getElementById("dom_"+iddom+"_irregolarita");
	var catA = document.getElementById("dom_"+iddom+"_provv_a");
	var catB = document.getElementById("dom_"+iddom+"_provv_b");
	var catC = document.getElementById("dom_"+iddom+"_provv_c");
	
	if (val=='N' || val == ''){
		numIrr.disabled = false;
		catA.disabled = false;
		catB.disabled = false;
		catC.disabled = false;
	}
	else {
		numIrr.disabled = true;
		numIrr.value="";
		catA.disabled = true;
		catA.value="";
		catB.disabled = true;
		catB.value="";
		catC.disabled = true;
		catC.value="";
	}
	
}

</script>