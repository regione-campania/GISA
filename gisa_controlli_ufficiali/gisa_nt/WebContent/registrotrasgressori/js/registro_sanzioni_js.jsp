<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript" src="dwr/interface/DwrDocumentale.js"> </script>


<script>

 function aggiornaGiorni(){
	 
	dataEmissione = document.getElementById('data_emissione');
	dataProt = document.getElementById('data_prot_entrata');
	giorni = document.getElementById('giorni_lavorazione');
	if (dataProt.value!='' && dataEmissione.value!=''){
		diffDays = giorni_differenza(dataProt.value, dataEmissione.value);
		alert('Giorni di lavorazione pratica: '+diffDays);
		giorni.value = diffDays;
	}
}

function aggiornaPagamentoRidottoConsentito(){
	
	dataUltimaNotifica = document.getElementById('data_ultima_notifica');
	dataPagamento = document.getElementById('data_pagamento');
	pagamentoRidottoConsentito = document.getElementById('pagamento_ridotto_consentito');
	if (dataUltimaNotifica.value!='' && dataPagamento.value!=''){
		diffDays = giorni_differenza(dataUltimaNotifica.value, dataPagamento.value);
		if (diffDays> 62){
			alert('Differenza di 62 giorni tra Data Pagamento e Data Ultima Notifica. Pagamento NON effettuato nei termini.');
			pagamentoRidottoConsentito.innerHTML='NO';
		}
		else
			pagamentoRidottoConsentito.innerHTML='SI';	
	}
}

function aggiornaPagamentoRidottoConsentitoOrdinanza(){
	
	dataUltimaNotificaOrdinanza = document.getElementById('data_ultima_notifica_ordinanza');
	dataPagamentoOrdinanza = document.getElementById('data_pagamento_ordinanza');
	pagamentoRidottoConsentitoOrdinanza = document.getElementById('pagamento_ridotto_consentito_ordinanza');
	if (dataUltimaNotificaOrdinanza.value!='' && dataPagamentoOrdinanza.value!=''){
		diffDays = giorni_differenza(dataUltimaNotificaOrdinanza.value, dataPagamentoOrdinanza.value);
		if (diffDays> 32){
			alert('Differenza di 32 giorni tra Data Pagamento Ordinanza e Data Ultima Notifica Ordinanza. Pagamento Ordinanza NON effettuato nei termini.');
			pagamentoRidottoConsentitoOrdinanza.innerHTML='NO';
		}
		else
			pagamentoRidottoConsentitoOrdinanza.innerHTML='SI';	
	}
}

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
	giorni_diff = new String(Math.ceil(differenza/86400000));
	//alert('diff');
	//alert(giorni_diff);
	return giorni_diff;
}

function gestisciArgomentazioni(){
	
	var arg = document.getElementById('ordinanza_emessaB');
	
	if (!arg.checked)
		scopriNascondi('argomentazioniClass', 'si');
	else{
		scopriNascondi('argomentazioniClass', 'no');
		gestisciSentenza();
		gestisciRichiestaRateizzazione();
		}
	
}

function gestisciSentenza(){
	
	var arg = document.getElementById('sentenza_favorevoleSI');
	
	if (arg.checked)
		scopriNascondi('sentenzaClass', 'si');
	else
		scopriNascondi('sentenzaClass', 'no');
}

function gestisciRichiestaRateizzazione(){

	var arg = document.getElementById('richiesta_rateizzazioneSI');
	
	if (arg.checked)
		scopriNascondi('rateizzazioneClass', 'no');
	else
		scopriNascondi('rateizzazioneClass', 'si');
}

function gestisciCompetenzaRegionale(){
	
	var arg = document.getElementById('competenza_regionale');
	
	if (arg.checked)
		scopriNascondi('competenzaRegionaleClass', 'no');
	else
		scopriNascondi('competenzaRegionaleClass', 'si');
}

function scopriNascondi(classe, nascondi){
	
	 var elements = document.getElementsByClassName(classe);
     n = elements.length;
 for (var i = 0; i < n; i++) {
   var e = elements[i];
 
   if (nascondi=='si')
     e.style.visibility = 'collapse';
    else 
     e.style.visibility = 'visible';
   }
}

function gestisciPraticaChiusa(){
	
	var arg = document.getElementById('pratica_chiusa');
	var tabella =  document.getElementById('tabletrasgressione');
	
		if (arg.checked)
			disabilitaTabella(tabella);
		else
			abilitaTabella(tabella);
	
}

function gestisciPraticaChiusaIndiretta(){
	
	var arg = document.getElementById('pratica_chiusa');
	var ord = document.getElementById('ordinanza_emessaB');
	var ill = document.getElementById('competenza_regionale');
	
	if (!ord.checked || !ill.checked){
		arg.checked = "checked";
		gestisciPraticaChiusa();
	}
	else {
		arg.checked = "";
		gestisciPraticaChiusa();
	}
}


function abilitaTabella(tabella){
	
	var inputs = tabella.getElementsByTagName('input');
	for (var z=0; z < inputs.length; z++){
		if (inputs[z].id.startsWith('_')){
			inputs[z].parentElement.removeChild(inputs[z]);
		}
	}
	
	inputs = tabella.getElementsByTagName('input');
	for (var z=0; z < inputs.length; z++){
		var gruppo = inputs[z].getAttribute("gruppo");
		var permesso = false;
		
		if (gruppo==null)
			permesso=true;
		else{
			var res = gruppo.split(","); 
			for (var k = 0; k < res.length; k++) {
		    	if (res[k].indexOf(<%=gruppiUtente%>)>-1 )
		    		permesso = true;
		}	
		}
		
		if (inputs[z].type == 'text'){
			if (permesso){
				  inputs[z].readOnly=false;
				}
		}
		else if (inputs[z].type == 'button'){
			inputs[z].style.display = 'table-row';
		}
		else if (inputs[z].type == 'radio'){
			inputs[z].style.display='table-row';
		}
		else if (inputs[z].type == 'checkbox'){  
			 inputs[z].style.display='table-row';
			}
		
		 }
	var inputs = tabella.getElementsByTagName('textarea');
	for (var z=0; z < inputs.length; z++)
		  inputs[z].readOnly=false;
	
	var imgs = tabella.getElementsByTagName('img');
	for (var z=0; z < imgs.length; z++)
		imgs[z].style.display = 'table-row';
	
	var allega = document.getElementById("allega_");
	if (allega!=null)
		allega.style.display='table-row';
}

function disabilitaTabella(tabella){
	var inputs = tabella.getElementsByTagName('input');
	for (var z=0; z < inputs.length; z++){

		if (inputs[z].id.startsWith('_') || (inputs[z].id=='competenza_regionale' && !inputs[z].checked) || inputs[z].id =='pratica_chiusa' || (inputs[z].id =='annulla' || inputs[z].id =='salva'  || inputs[z].id =='torna')){
		  //Non fare nulla
		  }
		
		else if (inputs[z].type == 'text'){
			inputs[z].readOnly=true;
		}
		else if (inputs[z].type == 'button'){
			inputs[z].style.display = 'none';
		}
		else if (inputs[z].type == 'radio'){
			var radio = document.createElement('input'); 
		    radio.type= 'radio';
		    radio.name = '_'+inputs[z].name;
		    radio.id = '_'+inputs[z].id;
		    if (inputs[z].checked)
		    	radio.checked = true;
		    radio.disabled = true;
			inputs[z].parentNode.insertBefore( radio, inputs[z].nextSibling );
			inputs[z].style.display='none';
		}
		else if (inputs[z].type == 'checkbox'){  
			 var checkbox = document.createElement('input'); 
			    checkbox.type= 'checkbox';
			    checkbox.name = '_'+inputs[z].name;
			    checkbox.id = '_'+inputs[z].id;
			    if (inputs[z].checked)
			    	checkbox.checked = true;
			    checkbox.disabled = true;
				inputs[z].parentNode.insertBefore( checkbox, inputs[z].nextSibling );
				inputs[z].style.display='none';
			}
	}
	
	var inputs = tabella.getElementsByTagName('textarea');
	for (var z=0; z < inputs.length; z++){
		 if (!inputs[z].id.startsWith('note'))
			 inputs[z].readOnly=true;
	}
	
	var imgs = tabella.getElementsByTagName('img');
	for (var z=0; z < imgs.length; z++)
		imgs[z].style.display = 'none';
	
	var allega = document.getElementById("allega");
	if (allega!=null)
		allega.style.display='none';
}


function controlloDate(){
	
	
	var dataAccertamento = document.getElementById('data_accertamento').value;
	var dataEmissione = document.getElementById('data_emissione').value;
	var dataProtocollo = document.getElementById('data_prot_entrata').value;
	var dataUltimaNotifica = document.getElementById('data_ultima_notifica').value;
	var dataPagamento = document.getElementById('data_pagamento').value;
	var dataUltimaNotificaOrdinanza = document.getElementById('data_ultima_notifica_ordinanza').value;
	var dataPagamentoOrdinanza = document.getElementById('data_pagamento_ordinanza').value;
	
	var esito = true;
	var message = '';
	
		if (giorni_differenza(dataAccertamento, dataEmissione)<0){
			message=message+"\n - Inserire una Data Emissione successiva alla Data Accertamento ("+dataEmissione+" - "+dataAccertamento+")";
			esito = false;
			}
		if (giorni_differenza(dataProtocollo, dataEmissione)<0){
			message=message+"\n - Inserire una Data Emissione successiva alla Data Protocollo ("+dataEmissione+" - "+dataProtocollo+")";
			esito = false;
			}
	
		if (giorni_differenza(dataPagamentoOrdinanza, dataUltimaNotificaOrdinanza)>0){
			message=message+"\n - Inserire una Data Ultima Notifica Ordinanza antecedente alla Data Pagamento Ordinanza ("+dataUltimaNotificaOrdinanza+" - "+dataPagamentoOrdinanza+")";
			esito = false;
			}
		
		if (giorni_differenza(dataUltimaNotificaOrdinanza, dataPagamentoOrdinanza)<0){
			message=message+"\n - Inserire una Data Pagamento Ordinanza successiva alla Data Ultima Notifica Ordinanza ("+dataUltimaNotificaOrdinanza+" - "+dataPagamentoOrdinanza+")";
			esito = false;
			}
		
// 		if (giorni_differenza(dataPagamento, dataUltimaNotifica)>0){
// 			message=message+"\n - Inserire una Data Ultima Notifica antecedente alla Data Pagamento ("+dataUltimaNotifica+" - "+dataPagamento+")";
// 			esito = false;
// 			}
		
// 		if (giorni_differenza(dataUltimaNotifica, dataPagamento)<0){
// 			message=message+"\n - Inserire una Data Pagamento successiva alla Data Ultima Notifica ("+dataUltimaNotifica+" - "+dataPagamento+")";
// 			esito = false;
// 			}
	
		if (!esito)
			alert(message);
		return esito;
}



function openUploadAllegatoTragressori(trasgrId,idSanzione, tipoUpload){
	var res;
	var result;
	
	if (document.all || 1==1) {
		window.open('GestioneAllegatiTrasgressori.do?command=PrepareUploadAllegato&tipo='+tipoUpload+'&tipoAllegato='+tipoUpload+'&trasgrId='+trasgrId+'&ticketId='+idSanzione,null,
		'height=450px,width=480px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
		} else {
		
			res = window.showModalDialog('GestioneAllegatiTrasgressori.do?command=PrepareUploadAllegato&tipo='+tipoUpload+'&tipoAllegato='+tipoUpload+'&trasgrId='+trasgrId+'&ticketId='+idSanzione,null,
			'dialogWidth:480px;dialogHeight:450px;center: 1; scroll: 0; help: 1; status: 0');
		
		}
		} 

function openDettaglioControllo(idcontrollo){
	
	  var res;
    var result;
    
   	  window.open('RegistroTrasgressori.do?command=DettaglioControllo&idControllo='+idcontrollo,'popupSelect',
          'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}

function filtra(index) {
	  // Declare variables
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("myInput"+index);
	  filter = input.value.toUpperCase();
	  table = document.getElementById("tablelistatrasgressioni");
	  tr = table.getElementsByTagName("tr");
	  
	  for (j=0; j<100; j++)
		  if (index!=j)
			  if (document.getElementById("myInput"+j)!=null)
			 	 document.getElementById("myInput"+j).value="";
	  
	  for (i = 0; i < tr.length; i++) {
		  
		  td = tr[i].getElementsByTagName("td")[index];
	    if (td) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }
	  }
}

function checkFormModificaSanzione(form){
		var esitoDate = true;
		esitoDate = controlloDate();
	
		if (esitoDate){
			if (confirm('Le modifiche saranno salvate. Proseguire?')){
				loadModalWindow();
				form.submit();
			}
		}
	
}

function annullaModificaSanzione(anno, trimestre){
	if (confirm("Attenzione. Le modifiche saranno perse. Proseguire?")){
		loadModalWindow();
		window.location.href="RegistroTrasgressori.do?command=RegistroSanzioniAnno&anno="+anno+"&trimestre="+trimestre;
	}
}
function tornaAlRegistro(anno, trimestre){
	loadModalWindow();
	window.location.href="RegistroTrasgressori.do?command=RegistroSanzioniAnno&anno="+anno+"&trimestre="+trimestre;
}




function tornaAllaSanzione(id){
	loadModalWindow();
	window.location.href='RegistroTrasgressori.do?command=ModificaSanzione&id='+id;
}

function inserimentoPagoPa(form, idPagoPa){
	
	 var inputs = form.getElementsByTagName("input"), input = null, flag = true;
	    for(var i = 0, len = inputs.length; i < len; i++) {
	        input = inputs[i];
	        if(!input.value && input.type!="hidden") {
	            flag = false;
	            input.focus();
	            alert("Compilare tutti i campi!");
	            break;
	        }
	    }
	
	    if (!flag)
	  	  return null;
	
	var importo = document.getElementById("importoSingoloVersamento_"+idPagoPa).value;
	var numeroRate = document.getElementById("numeroRate_"+idPagoPa).value;
	var importoDiviso = importo/numeroRate;
//	importoDiviso=importoDiviso.toFixed(2);
	importoDiviso = Math.trunc( importoDiviso );
	
	var confirmString= "";
	
	if (numeroRate>1)
		confirmString = 'Sono state selezionate ' + numeroRate + ' rate. Verranno generate ' + numeroRate + ' richieste di pagamento di importo: '+importoDiviso + '. Salvare e inviare?';
	else
		confirmString = 'Salvare e inviare?';
	
	if (confirm (confirmString)) {
		form.action = 'RegistroTrasgressori.do?command=InserimentoPagoPa';
		form.idPagoPa.value = idPagoPa;
		document.getElementById("importoSingoloVersamento_"+idPagoPa).value = importoDiviso;
		loadModalWindow();
		form.submit();
	}
}

function aggiornamentoPagoPa(form, idPagoPa){
	if (confirm ('Aggiornare e inviare?')) {
	form.action = 'RegistroTrasgressori.do?command=AggiornamentoPagoPa';
	form.idPagoPa.value = idPagoPa;
	loadModalWindow();
	form.submit();
	}
}

function annullamentoPagoPa(form, idPagoPa){
	if (confirm ('Annullare e inviare?')) {
	form.action = 'RegistroTrasgressori.do?command=AnnullamentoPagoPa';
	form.idPagoPa.value = idPagoPa;
	loadModalWindow();
	form.submit();
	}
}
	
function recuperaOggettoAllegato(header){
	DwrDocumentale.Documentale_InfoService(header, {callback:recuperaOggettoAllegatoCallBack,async:false});
}
function recuperaOggettoAllegatoCallBack(returnValue)
{
	if (returnValue == null || returnValue == '' || returnValue == '{}')
		return null;
	
	var dati = returnValue;
	var obj;
	obj = JSON.parse(dati);
	if(obj!=null && obj.oggetto != ""){        			
		document.getElementById(obj.header).innerHTML = obj.oggetto;
		if (document.getElementById(obj.header+"_download")!=null)
			document.getElementById(obj.header+"_download").href = "GestioneAllegatiTrasgressori.do?command=DownloadPDF&codDocumento="+obj.header+"&nomeDocumento="+obj.oggetto;
	}  else {
	}  
}
	
	</script>