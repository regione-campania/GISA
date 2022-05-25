<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="javascript/vendor/moment.min.js"></script>
<script type="text/javascript" src="dwr/interface/DWRnoscia.js"> </script>

<script  src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<div id="id_pagina_da_suap_a_gisa">
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="GestionePraticheAction.do?command=HomeGins">PRATICHE SUAP 2.0</a> >
			<a href="GestionePraticheAction.do?command=ListaPraticheStabilimenti">GESTIONE ATT. FISSE, MOBILI E RICONOSCIMENTO</a> > SELEZIONA STABILIMENTO DA MODIFICARE
		</td>
	</tr>
</table>

<div id="dati_pratica" style="border: 1px solid black; background: #BDCFFF">
<br>
&nbsp;&nbsp;&nbsp;&nbsp;NUMERO PRATICA: ${numeroPratica} <br>
&nbsp;&nbsp;&nbsp;&nbsp;TIPO PRATICA: ${descTipoPratica} <br>
&nbsp;&nbsp;&nbsp;&nbsp;DATA PEC / DATA SCIA: ${dataPratica} <br>
&nbsp;&nbsp;&nbsp;&nbsp;COMUNE: ${comuneTesto} <br>
<br>
<input type="hidden" id="numeroPratica" value="${numeroPratica}"> 
<input type="hidden" id="tipoPratica" value="${tipoPratica}"> 
<input type="hidden" id="comunePratica" value="${comunePratica}">
<input type="hidden" id="dataPratica" value="${dataPratica}">
<input type="hidden" id="causalePratica" value="${id_causale}">
<input type="hidden" id="stabId" value="0"/>
<input type="hidden" id="altId" value="${altId}"/>
</div>
<P>
<font color="red">SCEGLIERE LO STABILIMENTO SU CUI EFFETTUARE L'OPERAZIONE ANAGRAFICA ESPRESSA NELLA PRATICA. IL 
				  RISULTATO DELLA RICERCA MOSTRERA' GLI STABILIMENTI SU CUI E' POSSIBILE EFFETTUARE QUELLA PRATICA.
				  <BR>SE LO STABILIMENTO NON ESISTE IN GISA E' POSSIBILE INSERIRLO TRAMITE IL LINK "AGGIUNGI STABILIMENTO"
				  NEL CAVALIERE "ANAGRAFICA STABILIMENTI" E POI TORNARE A COMPLETARE LA PRATICA NEL CAVALIERE "PRATICHE SUAP 2.0".
				  <BR>NOTA, PER TROVARE LO STABILIMENTO E' NECESSARIO:
				  <UL>
					  <LI style="color: red;">(PER I SEDE FISSA) CHE IL COMUNE SEDE OPERATIVA COINCIDA CON IL COMUNE PRATICA </LI>
					  <LI style="color: red;">(PER I SENZA SEDE FISSA) CHE COMUNE SEDE LEGALE COINCIDA CON IL COMUNE PRATICA </LI>
					  <LI style="color: red;">(PER I SENZA SEDE FISSA CON UN TIPO IMPRESA CHE NON PREVEDE LA SEDE LEGALE) CHE COMUNE RESIDENZA TITOLARE IMPRESA COINCIDA CON IL COMUNE PRATICA</LI>
				  	  <LI style="color: red;">CHE LO STABILIMENTO NON SIA CESSATO</LI>
				  </UL> 
</font>
</P>
<table id="tabella_ricerca_osa" class="details" cellspacing="0" border="0" width="100%" cellpadding="4">
	<tr id="tr_riepilogo_stab_sez">
		<th colspan="4">dati stabilimento</th>
	</tr>
	
	<tr id="tr_dati_cerca_stab" >
		<td class="formLabel"><p>Partita Iva/Codice fiscale impresa</p></td>
		<td colspan="3">
			<input type="text" id="partita_iva" title="inserire partita iva o codice fiscale impresa"  maxlength="16" />
			<input type="button" value="cerca stabilimento" onclick="cerca_stabilimento_suap();"/> 		
		</td>
	</tr>

	<tr id="tr_riepilogo_stab" >
		<td class="formLabel"><p>RIEPILOGO STABILIMENTO SELEZIONATO</p></td>
		<td colspan="3"><div id="riepilogo_stab"></div> </td>
	</tr>
</table>

<br><br>
<table width="100%" cellpadding="4">
<tr>
	<td align="center">
		<input type="button" id="tasto_prosegui" class="" style="width: 250px; display: none;"  onclick="prosegui()" value="PROSEGUI"/>
		<input type="button" class="yellowBigButton" style="width: 250px;"
			onClick="loadModalWindowCustom('ATTENDERE PREGO'); window.location.href='GestionePraticheAction.do?command=ListaPraticheStabilimenti'"
			value="ANNULLA"/>
	</td>
</tr>
</table>
<br><br>
</div>

<div id='dialogstabilimenti'/>

<script>

if(document.getElementById('altId').value == '-1' || document.getElementById('altId').value == '0'){
	
} else {
	document.getElementById('id_pagina_da_suap_a_gisa').style='display: none';
	prosegui();
}

function prosegui(){
	
	  var stabId = document.getElementById("stabId").value; 
	  var altId = document.getElementById("altId").value; 
	  var numeroPratica = document.getElementById("numeroPratica").value; 
	  var tipoPratica = document.getElementById("tipoPratica").value; 
	  var dataPratica = document.getElementById("dataPratica").value;
	  var comunePratica = document.getElementById("comunePratica").value;
	  var causalePratica = document.getElementById("causalePratica").value;
	  
	
	  //mettere questo controllo if-else nell onclick del button avanti
	  if(altId == '-1'){
		  no_action_found(stabId, altId);
	  } else {
			  switch(tipoPratica){
			  
				  //aggiunzione linea d attivita
			  case "2":
				  loadModalWindowCustom('ATTENDERE PREGO');
				  var link = 'GestioneAnagraficaAction.do?command=ModifyGeneric&altId='+altId
						  		+'&operazione=ampliamento&numeroPratica='+numeroPratica+'&tipoPratica='+tipoPratica+'&dataPratica='+dataPratica
						  		+'&comunePratica='+comunePratica+'&causalePratica='+causalePratica;
				  window.location.href=link;
				  break;
				  
				  //cessazione
			  case "3":
				  loadModalWindowCustom('ATTENDERE PREGO');
				  var link = 'GestioneAnagraficaAction.do?command=ModifyGeneric&altId='+altId
			  					+'&operazione=cessazione&numeroPratica='+numeroPratica+'&tipoPratica='+tipoPratica+'&dataPratica='+dataPratica
			  					+'&comunePratica='+comunePratica+'&causalePratica='+causalePratica;
				  window.location.href=link;
				  break;
				  
				  //variazione titolarita
	 		  case "4":
	 			  loadModalWindowCustom('ATTENDERE PREGO');
	 			  var link = 'GestioneAnagraficaAction.do?command=TemplateVariazione&altId='+altId
	 					  		+'&numeroPratica='+numeroPratica+'&tipoPratica='+tipoPratica+'&dataPratica='+dataPratica
	 					  		+'&comunePratica='+comunePratica+'&causalePratica='+causalePratica;
	 			  window.location.href=link;
	 			  break;
			  		
	 			  //sospensione linee 
	 		  case "5":
	 			  loadModalWindowCustom('ATTENDERE PREGO');
	 			  var link = 'GestioneAnagraficaAction.do?command=AddGestioneSospendiLinee&altId='+altId
	 					  		+'&numeroPratica='+numeroPratica+'&tipoPratica='+tipoPratica+'&dataPratica='+dataPratica
	 					  		+'&comunePratica='+comunePratica+'&causalePratica='+causalePratica;
	 			  window.location.href=link;
	 			  break;
	 			  
				  //variazioni significative allo stato dei luoghi
			  case "6":
				  loadModalWindowCustom('ATTENDERE PREGO');
				  var link = 'GestioneAnagraficaAction.do?command=AssociaPraticaSuap&altId='+altId
			  					+'&numeroPratica='+numeroPratica+'&comunePratica='+comunePratica;
				  window.location.href=link;
				  break;
				 
				  //trasferimento di sede
			  case "7":
				  loadModalWindowCustom('ATTENDERE PREGO');
				  var link = 'GestioneAnagraficaAction.do?command=ModifyGeneric&altId='+altId
			  					+'&operazione=trasferimentoSede&numeroPratica='+numeroPratica+'&tipoPratica='+tipoPratica+'&dataPratica='+dataPratica
			  					+'&comunePratica='+comunePratica+'&causalePratica='+causalePratica;
				  window.location.href=link;
				  break;
				
				  //riattivazione delle attivita temporaneamente sospese
			  case "8":
				  loadModalWindowCustom('ATTENDERE PREGO');
	 			  var link = 'GestioneAnagraficaAction.do?command=AddGestioneRiattivaLinee&altId='+altId
	 					  		+'&numeroPratica='+numeroPratica+'&tipoPratica='+tipoPratica+'&dataPratica='+dataPratica
	 					  		+'&comunePratica='+comunePratica+'&causalePratica='+causalePratica;
	 			  window.location.href=link;
	 			  break;
			  
				  //variazioni informazioni schede supplementari
			  case "9":
				  loadModalWindowCustom('ATTENDERE PREGO');
				  var link = 'GestioneAnagraficaAction.do?command=AssociaPraticaSuap&altId='+altId
			  					+'&numeroPratica='+numeroPratica+'&comunePratica='+comunePratica;
				  window.location.href=link;
				  break;
				
				  //ampliamento
			  case "10":
				  loadModalWindowCustom('ATTENDERE PREGO');
				  var link = 'GestioneAnagraficaAction.do?command=ModifyGeneric&altId='+altId
			  					+'&operazione=ampliamentoFisico&numeroPratica='+numeroPratica+'&tipoPratica='+tipoPratica+'&dataPratica='+dataPratica
			  					+'&comunePratica='+comunePratica+'&causalePratica='+causalePratica;
				  window.location.href=link;
				  break;
				  break;
				  
				  //trasformazione
			  case "11":
				  loadModalWindowCustom('ATTENDERE PREGO');
				  var link = 'GestioneAnagraficaAction.do?command=ModifyGeneric&altId='+altId
						  		+'&operazione=trasformazione&numeroPratica='+numeroPratica+'&tipoPratica='+tipoPratica+'&dataPratica='+dataPratica
						  		+'&comunePratica='+comunePratica+'&causalePratica='+causalePratica;
				  window.location.href=link;
				  break;
				  
				//variazione sede legale
			  case "17":
				  loadModalWindowCustom('ATTENDERE PREGO');
				  var link = 'GestioneAnagraficaAction.do?command=ModifyGeneric&altId='+altId
			  					+'&operazione=variazioneSedeLegale&numeroPratica='+numeroPratica+'&tipoPratica='+tipoPratica+'&dataPratica='+dataPratica
			  					+'&comunePratica='+comunePratica+'&causalePratica='+causalePratica;
				  window.location.href=link;
				  break;
		}
	  }
	  
}

function no_action_found(id_stabi, altId){
	  if(altId == '-1'){
		  var messaggio_stab_mancante = "Attenzione: si sta tentando di applicare una modifica ad uno stabilimento non presente in gisa.\n";
		  messaggio_stab_mancante += "Cliccare ok per inserire un nuovo stabilimento in gisa.";
		  if(confirm(messaggio_stab_mancante)){
			  window.location.href='GestioneAnagraficaAction.do?command=Choose';
		  }
	  } else {
		  window.location.href='GestioneAnagraficaAction.do?command=Details&stabId=' + id_stabi;
	  }
}

var objStab;
function cerca_stabilimento_suap()
{
	var partita_iva = document.getElementById('partita_iva').value;
	var comune = document.getElementById('comunePratica').value;
	var cod_pratica_in = document.getElementById('tipoPratica').value;
	document.getElementById('stabId').value = '0';
	document.getElementById('altId').value = '-1';
	document.getElementById('riepilogo_stab').innerHTML = '<span></span>';
	if (partita_iva.toString().trim() != ''){
		loadModalWindowCustom('Ricerca stabilimento in corso. Attendere');
		DWRnoscia.cercaStabilimentoPraticaSuap(partita_iva, comune, cod_pratica_in, {callback:recuperaDatiStabilimentoCallBack,async:true});
	}else{
		alert('Inserire una partita iva o codice fiscale impresa!');
	}
}
   
$(function() {
	
	 $('#dialogstabilimenti').dialog({
		title : 'RISULTATI RICERCA STABILIMENTI CON I DATI INSERITI : ',
         autoOpen: false,
         resizable: false,
         closeOnEscape: false,
         width:850,
         height:350,
         draggable: false,
         modal: true,
	     buttons: {
			 'STABILIMENTO SELEZIONATO': function() {
				var stabilimentoDaInserire = getRadioValue('radiostabsele'); 
				selezionaStabilimento(stabilimentoDaInserire);
                                loadModalWindowUnlock();
				$( this ).dialog('close');
			},
			'ESCI': function() {
                                loadModalWindowUnlock();
				$( this ).dialog('close');
			}
      }
 });
	 
});

function getRadioValue(theRadioGroup)
{
    var elements = document.getElementsByName(theRadioGroup);
    for (var i = 0, l = elements.length; i < l; i++)
    {
        if (elements[i].checked)
        {
            return elements[i].value;
        }
    }
}
	
function recuperaDatiStabilimentoCallBack(returnValue)
{
	var dati = returnValue;
	var obj;
	obj = JSON.parse(dati);
	objStab = obj;
	var len = obj.length;
	if (len > 0){
		var htmlText='<br>'; 
		htmlText+='<center><table border=\'1\' cellpadding=\'4\'><tr><th>RAGIONE SOCIALE</th><th>Partita IVA/Codice fiscale</th><th>INDIRIZZO</th><th>NUMERO REGISTRAZIONE</th><th></th></tr>';
		for (i = 0; i < len; i++){
			htmlText+='<tr><td align=\'center\'>' + obj[i].ragione_sociale + 
							'</td><td align=\'center\'>' + obj[i].partita_iva + 
							'</td><td align=\'center\'>' + obj[i].indirizzo + 
							'</td><td align=\'center\'>' + obj[i].numero_registrazione + 
					  		'</td><td align=\'center\'><input type=\'radio\' id=\'radiostabsele\' name=\'radiostabsele\' value=\''+i+'\'></td></tr>';
		}
		htmlText+='</table></center>';
        $('#dialogstabilimenti').html(htmlText);
        $('#dialogstabilimenti').dialog('open');
		
	} else {
				document.getElementById('tasto_prosegui').setAttribute("class", "");
				document.getElementById('tasto_prosegui').style='display: none';
				alert('Attenzione: nessuno stabilimento trovato. \n' +
					  'Possibili motivazioni: \n' +
							' 1) non esiste uno stabilimento/OSA per il comune selezionato(in fase di inserimento pratica) e la partita IVA specificata \n' +
							' 2) il tipo di operazione scelto non è compatibile con la tipologia di stabilimento/OSA che si sta ricercando \n' +
							' 3) non vengono contemplati gli stabilimenti cessati');
				document.getElementById('riepilogo_stab').innerHTML = '<span>NESSUNO STABILIMENTO TROVATO</span>';
                loadModalWindowUnlock();
        }
	
}

function selezionaStabilimento(stabilimentoScelto){
	var obj = objStab[stabilimentoScelto];
	document.getElementById('stabId').value = obj.stabid;
	document.getElementById('altId').value = obj.altid;
	document.getElementById('tasto_prosegui').setAttribute("class", "yellowBigButton");
	document.getElementById('tasto_prosegui').style='width: 250px; margin-right: 10%';
	document.getElementById('riepilogo_stab').innerHTML = '<span>' + 
														  '<b>ragione sociale</b>: ' + obj.ragione_sociale + 
														  '<br><b>partita iva/Codice fiscale</b>: ' + obj.partita_iva + 
														  '<br><b>indirizzo stabilimento</b>: ' + obj.indirizzo +
														  '<br><b>numero registrazione</b>: ' + obj.numero_registrazione +
														  '</span>';
}

 
</script>