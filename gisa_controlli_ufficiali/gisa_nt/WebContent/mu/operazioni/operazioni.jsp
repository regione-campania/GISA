<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.mu_wkf.base.*"%>
<%@page import="java.util.*"%>
<jsp:useBean id="path" class="org.aspcfs.modules.mu_wkf.base.Path"
	scope="request" />
<jsp:useBean id="listaCapi" class="java.lang.String" scope="request" />


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popLookupSelect.js"></SCRIPT>
<!-- <script type="text/javascript" src="javascript/ui.tabs.js"></script> -->

<%@ include file="include.jsp"%>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
	document.write(getCalendarStyles());
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>



<script>
	function gestisciObbligatorietaComunicazioniEsterne() {

		var comunicazioniEsterneA;
		var dataComunicazioniEsterne;
		var tipoNCComunicazioniEsterne;
		var provvedimentiComunicazioniEsterne;

		comunicazioniEsterneA = document.getElementById('comunicazioneAslOrigineComunicazioni').checked
				|| document.getElementById('comunicazioneProprietarioAnimaleComunicazioni').checked
				|| document.getElementById('comunicazioneAziendaOrigineComunicazioni').checked
				|| document.getElementById('comunicazioneProprietarioMacelloComunicazioni').checked
				|| document.getElementById('comunicazionePifComunicazioni').checked
				|| document.getElementById('comunicazioneUvacComunicazioni').checked
				|| document.getElementById('comunicazioneRegioneComunicazioni').checked
				|| document.getElementById('comunicazioneAltroComunicazioni').checked;

		dataComunicazioniEsterne = document.getElementById('dataComunicazioniEsterne').value != '';

		tipoNCComunicazioniEsterne = document
				.getElementById('listaNonConformita').selectedIndex > 0;

		provvedimentiComunicazioniEsterne = document
				.getElementById('listaProvvedimenti').selectedIndex > 0;
				 
			
 
		if (comunicazioniEsterneA || dataComunicazioniEsterne
				|| tipoNCComunicazioniEsterne
				|| provvedimentiComunicazioniEsterne) {
			
			/* mostra asterischi campi obbligatori */
			document.getElementById('comunicazioneA').style.display = '';
			
			document.getElementById('dataComunicazioneA').style.display = '';
			$("#dataComunicazioniEsterne").prop("required", "true");
			document.getElementById('tipoNonConformita').style.display = '';
			$("#listaNonConformita").prop("required", "true");
			document.getElementById('provvedimentiAdottati').style.display = '';
			$("#listaProvvedimenti").prop("required", "true");
			

		} else {
			// alert('nascondi'); 
			/* nascondi asterischi campi obbligatori */
			document.getElementById('comunicazioneA').style.display = 'none';
			document.getElementById('dataComunicazioneA').style.display = 'none';
			$("#dataComunicazioniEsterne").removeAttr( "required" );
			document.getElementById('tipoNonConformita').style.display = 'none';
			$("#listaNonConformita").removeAttr( "required" );
			document.getElementById('provvedimentiAdottati').style.display = 'none';
			$("#listaProvvedimenti").removeAttr( "required" );

		}

	}

	function gestisciBloccoAnimale() {

		var lista_selezionata = document.getElementById('listaProvvedimenti'); // .value;
		var isBloccoAnimaleSelezionato = lista_selezionata[1].selected;

		if (isBloccoAnimaleSelezionato) {
			document.getElementById('idDestinazioneSbloccoMorteAm').value = -1;
			document.getElementById('blocco_animale_div').style.display = "";
		} else {
			document.getElementById('blocco_animale_div').style.display = "none";
		}
	}

	function gestisciObbligatorietaMorteAnteMacellazione() {

		var dataMorteAnteMacellazione;
		var luogoMorteAnteMacellazione;
		var causaMorteAnteMacellazione;

		dataMorteAnteMacellazione = document.getElementById('mavam_data').value != '';
		luogoMorteAnteMacellazione = document.getElementById('mavam_luogo').selectedIndex > 0;
		causaMorteAnteMacellazione = trim(document
				.getElementById('mavam_motivo').value) != '';

		if (dataMorteAnteMacellazione || luogoMorteAnteMacellazione
				|| causaMorteAnteMacellazione) {
			document.getElementById('dataMorteAnteMacellazione').style.display = '';
			document.getElementById('luogoMorteAnteMacellazione').style.display = '';
			document.getElementById('causaMorteAnteMacellazione').style.display = '';
		} else {
			document.getElementById('dataMorteAnteMacellazione').style.display = 'none';
			document.getElementById('luogoMorteAnteMacellazione').style.display = 'none';
			document.getElementById('causaMorteAnteMacellazione').style.display = 'none';
		}
	}

	function svuotaData(input) {
		input.value = '';
		gestisciObbligatorietaComunicazioniEsterne();
		gestisciObbligatorietaMorteAnteMacellazione();
		gestisciObbligatorietaVisitaAnteMortem();
		gestisciObbligatorietaVisitaPostMortem();
	}

	function gestisciObbligatorietaVisitaAnteMortem() {

		var dataVisitaAnteMortem;
		var provvedimentoVisitaAnteMortem;
		var numCapiOvini = "";
		var numCapiCaprini = "";
		var dataVisitaPostMortem;

		dataVisitaAnteMortem = document.getElementById('vam_data').value != '';
		dataVisitaPostMortem = document.getElementById('vpm_data').value != '';

		provvedimentoVisitaAnteMortem = document
				.getElementById('vam_provvedimenti').selectedIndex > 0;
		if (dataVisitaAnteMortem || dataVisitaAnteMortem
				|| provvedimentoVisitaAnteMortem || numCapiOvini != ''
				|| numCapiCaprini != '') {
			document.getElementById('dataVisitaAnteMortem').style.display = '';
			document.getElementById('dataVisitaPostMortem').style.display = '';
			document.getElementById('provvedimentoVisitaAnteMortem').style.display = '';
		} else {
			document.getElementById('dataVisitaPostMortem').style.display = 'none';
			document.getElementById('destinatarioCarni1').style.display = 'none';
			document.getElementById('dataVisitaAnteMortem').style.display = 'none';
			document.getElementById('provvedimentoVisitaAnteMortem').style.display = 'none';
		}

	}

	function gestisciObbligatorietaVisitaPostMortem() {

		var dataVisitaPostMortem;
		var destinatarioVisitaPostMortem;

		dataVisitaPostMortem = document.getElementById('vpm_data').value != '';
		destinatarioVisitaPostMortem = trim(document
				.getElementById('destinatario_1_nome').value).length > 0;

		if (dataVisitaPostMortem || destinatarioVisitaPostMortem) {
			document.getElementById('dataVisitaPostMortem').style.display = '';
			//document.getElementById('destinatarioCarni1').style.display = '';
			//document.getElementById('destinatarioCarni2').style.display = '';
		} else {
			document.getElementById('dataVisitaPostMortem').style.display = 'none';
			document.getElementById('destinatarioCarni1').style.display = 'none';
			//document.getElementById('destinatarioCarni2').style.display = 'none';
		}

	}

	function trim(str) {
		return str.replace(/^\s+|\s+$/g, "");
	}

	function mostraTextareaEsercente(idTextarea) {
		document.getElementById(idTextarea).style.display = '';
	}

	function nascondiTextareaEsercente(idTextarea) {
		document.getElementById(idTextarea).value = '';
		document.getElementById(idTextarea).style.display = 'none';
	}

	function valorizzaDestinatario(campoTextarea, idDestinatario) {
		document.getElementById(idDestinatario + '_nome').value = campoTextarea.value;
		document.getElementById(idDestinatario + '_id').value = -999;
		gestisciObbligatorietaVisitaPostMortem();
	}

	function visualizzaTextareaCAslToAltro() {
		
		if (document.getElementById('comunicazioneAltroComunicazioni')
				&& document.getElementById('comunicazioneAltroComunicazioni').checked) {
			document.getElementById('comunucazioneAltroTestoComunicazioni').style.display = '';
		} else {
			document.getElementById('comunucazioneAltroTestoComunicazioni').style.display = 'none';
		}

		if (document.getElementById('comunicazioneAltroVisitaAm')
				&& document.getElementById('comunicazioneAltroVisitaAm').checked) {
			
			document.getElementById('comunicazioneAltroTestoVisitaAm').style.display = '';
		} else {
			document.getElementById('comunicazioneAltroTestoVisitaAm').style.display = 'none';
		}
	}

	function visualizzaTextareaMavamToAltro() {
		if (document.getElementById('comunicazioneAltroMorteAm')
				&& document.getElementById('comunicazioneAltroMorteAm').checked) {
			document.getElementById('comunicazioneAltroTestoMorteAm').style.display = '';
		} else {
			document.getElementById('comunicazioneAltroTestoMorteAm').style.display = 'none';
		}
	}

	function visualizzaTextareaVamToAltro() {
		if (document.getElementById('vam_to_altro')
				&& document.getElementById('vam_to_altro').checked) {
			document.getElementById('vam_to_altro_testo').style.display = '';
		} else {
			document.getElementById('vam_to_altro_testo').style.display = 'none';
		}
	}

	function settaSoloCD(valore) {
		document.getElementById('solo_cd').value = valore;
	}

	function displayTabs() {
		document.getElementById('li-7').style.display = '';
		document.getElementById('li-4').style.display = '';
		document.getElementById('li-2').style.display = '';
		document.getElementById('li-3').style.display = '';
		window.location.href = "#";
	}

	function sleep(milliseconds) {
		var start = new Date().getTime();

		for (var i = 0; i < 1e7; i++) {

			if ((new Date().getTime() - start) > milliseconds) {
				break;
			}

		}
	}

	function displayLuogoVerifica() {
		if (document.getElementById("idLuogoVerificaMorteAm").value == 3) {
			document.getElementById("descrizioneLuogoVerificaMorteAm").style.display = "";
		} else {
			document.getElementById("descrizioneLuogoVerificaMorteAm").style.display = "none";
		}

	}
	function checkForm(form) {

		formTest = true;
		message = "";

		/* checkFormComunicazioni();
		checkFormAM(); */
		$(document).find('input').each(
				function() {
					//alert($(this).val());
					if ($(this).prop('required') == true && !($(this).css('display') == 'none') ) {
				
						if (($(this).is(':text') && ($(this).val() == null || $(this).val() == '' )) ){ 
						//	alert('ddd');
						message += label("", "- " + $(this).attr('label')
								+ " richiesta\r\n");
						formTest = false;
					}
					} /* else {
					    	alert('not required ' + $(this).attr('label'));
					    } */
				});
		
		
		$(document).find('select').each(
				
				function() {
				//	alert(alert($(this).prop('name')+ '  '+$(this).val()));
					if ($(this).prop('required') ) {
						
					//	alert($(this).prop('name')+ '  '+$(this).val());
			
					//	alert($(this).is(':text'));
						if ( $(this).val() == null || $(this).val() =='' ||  $(this).val() == 'null' || $(this).val() <  0)  { 
							message += label("", "- " + $(this).attr('label')
								+ " richiesta\r\n");
							formTest = false;
					}
					} /* else {
					    	alert('not required ' + $(this).attr('label'));
					    } */
				});
		
		
		$(document).find('textarea').each(
				
				function() {
				//	alert(alert($(this).prop('name')+ '  '+$(this).val()));
					if ($(this).prop('required') && !($(this).css('display') == 'none') ) {
						
					//	alert($(this).prop('name')+ '  '+$(this).val());
			
					//	alert($(this).is(':text'));
						if ( $(this).val() == null || $(this).val() =='' ||  $(this).val() == 'null' || $(this).val() <  0)  { 
							message += label("", "- " + $(this).attr('label')
								+ " richiesta\r\n");
							formTest = false;
					}
					} /* else {
					    	alert('not required ' + $(this).attr('label'));
					    } */
				});

		if (formTest) {
			form.submit();
		} else {
			alert(message);
		}
	}
	
	
	
	
	 function aggiungiOrgano(){
		  	var maxElementi = 100;
		  	var elementi;
		  	var elementoClone;
		  	var tableClonata;
		  	var tabella;
		  	var selezionato;
		  	var x;
		  	elementi = document.getElementById('elementi');
		  	elementi.value=parseInt(elementi.value)+1;
		  	size = document.getElementById('size');
		  	size.value=parseInt(size.value)+1;
		  	var primo_elemento = document.getElementById('lcso_patologia_1');
		  	var indice = parseInt(elementi.value) - 1;
		  	
		  	x = document.getElementById('lcso_patologia_'+String(indice));
		  	if(primo_elemento!=null && x==null){
		  		selezionato = document.getElementById('lcso_patologia_1').selectedIndex;
		  	}else if(primo_elemento==null && x!=null){
		  		selezionato = x.selectedIndex;
		  	}
		 /*  	alert(document.getElementById('nbsp'));
		  	alert(document.getElementById('row'));
		  	alert( document.getElementById('tr')); 
		  	alert(document.getElementById('ww')); */
		  		
		  	var clonanbsp = document.getElementById('nbsp');
		  	var clonato = document.getElementById('row');
		  	var clonato2 = document.getElementById('tr');
		  	var clonato3 = document.getElementById('ww');
		  	
		  	/*clona riga vuota*/
		  	clone=clonanbsp.cloneNode(true);
			  	
		  	clone.getElementsByTagName('TD')[0].name = "nbsptr1_"+elementi.value;
		  	clone.getElementsByTagName('TD')[0].id = "nbsptr1_"+elementi.value;
		  	clone.getElementsByTagName('input')[0].name = "lcso_id_"+elementi.value;
		  	clone.id = "nbsp_"+elementi.value;
		  	
		  	/*Lo rendo visibile*/
		  	
		  	//clone.style.visibility="visible";
		  		  	
		  	/*Aggancio il nodo*/
		  	clonanbsp.parentNode.appendChild(clone);

		  	/*Lo rendo visibile*/
		  	clone.style.bgcolor="#EDEDED";
		  	clone.style.visibility="visible";
		  	
		  	/*clona organo*/
		  	clone=clonato3.cloneNode(true);
			  	
		  	clone.getElementsByTagName('SELECT')[0].name = "lcso_organo_"+elementi.value;
		  	clone.getElementsByTagName('SELECT')[0].id = "lcso_organo_"+elementi.value;
			clone.getElementsByTagName('SELECT')[0].onchange= function () {
				elemento_selezionato=document.getElementById('lcso_organo_'+String(indice+1)).value;
//				riga=indice+1;
//				alert("Nella funzione chiamata sull'onchange: indice_riga=" + riga + " value_organo=" + elemento_selezionato);
				vpm_seleziona_lookup_patologia_organo(indice+1, elemento_selezionato, -1);
			}
		  	
		  	clone.id = "ww_"+elementi.value;
		  	
		  	/*Aggancio il nodo*/
		  	clonato3.parentNode.appendChild(clone);

		  	/*Lo rendo visibile*/
		  	//clone.style.display="block";
		  	clone.style.visibility="visible";
		  	
		  	/*clona patologia*/	  	
		  	clone=clonato.cloneNode(true);
		  		  		  	
		  	clone.getElementsByTagName('SELECT')[0].name = "lesione_milza_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[0].id = "lesione_milza_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[0].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[0].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[0].size = 5;
		  	clone.getElementsByTagName('SELECT')[0].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		  	
			clone.getElementsByTagName('SELECT')[1].name = "lesione_cuore_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[1].id = "lesione_cuore_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[1].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[1].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[1].size = 5;
		  	clone.getElementsByTagName('SELECT')[1].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
			clone.getElementsByTagName('SELECT')[2].name = "lesione_polmoni_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[2].id = "lesione_polmoni_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[2].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[2].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[2].size = 5;
		  	clone.getElementsByTagName('SELECT')[2].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		  	clone.getElementsByTagName('SELECT')[3].name = "lesione_visceri_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[3].id = "lesione_visceri_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[3].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[3].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[3].size = 5;
		  	clone.getElementsByTagName('SELECT')[3].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
			clone.getElementsByTagName('SELECT')[4].name = "lesione_fegato_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[4].id = "lesione_fegato_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[4].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[4].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[4].size = 5;
		  	clone.getElementsByTagName('SELECT')[4].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		  	
			clone.getElementsByTagName('SELECT')[5].name = "lesione_rene_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[5].id = "lesione_rene_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[5].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[5].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[5].size = 5;
		  	clone.getElementsByTagName('SELECT')[5].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		  	
			clone.getElementsByTagName('SELECT')[6].name = "lesione_mammella_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[6].id = "lesione_mammella_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[6].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[6].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[6].size = 5;
		  	clone.getElementsByTagName('SELECT')[6].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		  	
			clone.getElementsByTagName('SELECT')[7].name = "lesione_apparato_genitale_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[7].id = "lesione_apparato_genitale_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[7].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[7].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[7].size = 5;
		  	clone.getElementsByTagName('SELECT')[7].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		  	
			clone.getElementsByTagName('SELECT')[8].name = "lesione_stomaco_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[8].id = "lesione_stomaco_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[8].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[8].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[8].size = 5;
		  	clone.getElementsByTagName('SELECT')[8].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		  	
			clone.getElementsByTagName('SELECT')[9].name = "lesione_intestino_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[9].id = "lesione_intestino_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[9].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[9].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[9].size = 5;
		  	clone.getElementsByTagName('SELECT')[9].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		  	
			clone.getElementsByTagName('SELECT')[10].name = "lesione_osteomuscolari_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[10].id = "lesione_osteomuscolari_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[10].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[10].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[10].size = 5;
		  	clone.getElementsByTagName('SELECT')[10].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		  	
			clone.getElementsByTagName('SELECT')[11].name = "lesione_generici_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[11].id = "lesione_generici_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[11].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[11].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[11].size = 5;
		  	clone.getElementsByTagName('SELECT')[11].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		  	
		  	if (clone.getElementsByTagName('SELECT')[12] != null){
		  	clone.getElementsByTagName('SELECT')[12].name = "lesione_altro_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[12].id = "lesione_altro_" + elementi.value;
		  	clone.getElementsByTagName('SELECT')[12].selectedIndex = selezionato;
		  	clone.getElementsByTagName('SELECT')[12].multiple = 'multiple';
		  	clone.getElementsByTagName('SELECT')[12].size = 5;
		  	clone.getElementsByTagName('SELECT')[12].setAttribute('onchange','javascript:mostraLcsoPatologiaAltro(this,' + elementi.value + ')');
		  	
		  	}
		  	
		  	var lcso_patologia_altro = clone.getElementsByClassName('lcso_patologia_altro_class')[0];
		  	lcso_patologia_altro.name = "lcso_patologiaaltro_" + elementi.value;
		  	lcso_patologia_altro.id = "lcso_patologiaaltro_" + elementi.value;
		  	
		  	
		  	clone.getElementsByTagName('TD')[1].innerHTML = clone.getElementsByTagName('TD')[1].innerHTML.replace( '--placeholder--', elementi.value );
		  	clone.id = "row_" + elementi.value;
		  	
		  	/*Aggancio il nodo*/
		  	clonato.parentNode.appendChild(clone);
		  	
		  	/*Lo rendo visibile*/
		  	//clone.style.display="block";
		  	clone.style.visibility="visible";

		  	//imposto l'evento per visualizzare e nascondere il campo stadio
//		  	alert( document.getElementById( "lcso_patologia_" + elementi.value ).onchange );
//		  	document.getElementById( "lcso_patologia_" + elementi.value ).onchange = "javascript:displayStadio(" + elementi.value + ")";
//		  	alert( document.getElementById( "lcso_patologia_" + elementi.value ).onchange );
		  	
		  	/*clona stadio*/
		 // 	clone=clonato2.cloneNode(true);
		  //	
		 // 	clone.getElementsByTagName('SELECT')[0].name = "lcso_stadio_"+elementi.value;
		//  	clone.getElementsByTagName('SELECT')[0].id = "lcso_stadio_"+elementi.value;
		//  	clone.id = "stadio_"+elementi.value;
		  	
		  	/*Aggancio il nodo*/
		//  	clonato2.parentNode.appendChild(clone);

		  	/*Lo rendo visibile*/
		 // 	clone.style.display="none";
	//	  	clone.style.visibility="visible";
		  	
		  }
	 
	 
	 function vpm_seleziona_lookup_patologia_organo(indice_riga, value_organo, value_patologia)
	 {
	 	//alert("In prova().... Indice_riga=" + indice_riga + " value_organo=" +value_organo  + " value_patologia=" +value_patologia);
	 try {
	 	var MILZA 			= 1;
	 	var CUORE 			= 2;
	 	var POLMONI 		= 3;
	 	var FEGATO 			= 4;
	 	var RENE 			= 5;
	 	var MAMMELLA 		= 6;
	 	var GENITALE 		= 7;
	 	var STOMACO 		= 8;
	 	var INTESTINO 		= 9;
	 	var VISCERI 		= 26;
	 	var OSTEOMUSCOLARI	= 10;

	 	var ALTRO = 11;

	 	document.getElementById('lesione_milza_'+String(indice_riga)).style.display = "none";
	 	//document.getElementById('lesione_milza_'+String(indice_riga)).value = -1;
	 	document.getElementById('lesione_cuore_'+String(indice_riga)).style.display = "none";
	 	//document.getElementById('lesione_cuore_'+String(indice_riga)).value = -1;
	 	document.getElementById('lesione_polmoni_'+String(indice_riga)).style.display = "none";
	 	document.getElementById('lesione_visceri_'+String(indice_riga)).style.display = "none";
	 	//document.getElementById('lesione_polmoni_'+String(indice_riga)).value = -1;
	 	document.getElementById('lesione_fegato_'+String(indice_riga)).style.display = "none";
	 	//document.getElementById('lesione_fegato_'+String(indice_riga)).value = -1;
	 	document.getElementById('lesione_rene_'+String(indice_riga)).style.display = "none";
	 	//document.getElementById('lesione_rene_'+String(indice_riga)).value = -1;
	 	document.getElementById('lesione_mammella_'+String(indice_riga)).style.display = "none";
	 	//document.getElementById('lesione_mammella_'+String(indice_riga)).value = -1;
	 	document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).style.display = "none";
	 	//document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).value = -1;
	 	document.getElementById('lesione_stomaco_'+String(indice_riga)).style.display = "none";
	 	//document.getElementById('lesione_stomaco_'+String(indice_riga)).value = -1;
	 	document.getElementById('lesione_intestino_'+String(indice_riga)).style.display = "none";
	 	//document.getElementById('lesione_intestino_'+String(indice_riga)).value = -1;
	 	document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).style.display = "none";
	 	//document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).value = -1;

	 	document.getElementById('lesione_generici_'+String(indice_riga)).style.display = "none";
	 	//document.getElementById('lesione_generici_'+String(indice_riga)).value = -1;

	 	document.getElementById('lesione_altro_'+String(indice_riga)).style.display = "none";
	 	//document.getElementById('lesione_altro_'+String(indice_riga)).value = -1;
	 	
	 	if (value_organo == MILZA)  {
	 				document.getElementById('lesione_milza_'+String(indice_riga)).style.display = "";
	 				//document.getElementById('lesione_milza_'+String(indice_riga)).value = value_patologia;
	 	}
	 	else if (value_organo == CUORE)  {
	 				document.getElementById('lesione_cuore_'+String(indice_riga)).style.display = "";
	 				//document.getElementById('lesione_cuore_'+String(indice_riga)).value = value_patologia;
	 	}
	 	else if (value_organo == POLMONI){
	 				document.getElementById('lesione_polmoni_'+String(indice_riga)).style.display = "";
	 				//document.getElementById('lesione_polmoni_'+String(indice_riga)).value = value_patologia;
	 	}
	 	else if (value_organo == VISCERI){
	 		document.getElementById('lesione_visceri_'+String(indice_riga)).style.display = "";
	 		//document.getElementById('lesione_polmoni_'+String(indice_riga)).value = value_patologia;
	 }
	 	else if (value_organo == FEGATO)	{
	 				document.getElementById('lesione_fegato_'+String(indice_riga)).style.display = "";
	 				//document.getElementById('lesione_fegato_'+String(indice_riga)).value = value_patologia;
	 	}
	 	else if (value_organo == RENE)	{
	 				document.getElementById('lesione_rene_'+String(indice_riga)).style.display = "";
	 				//document.getElementById('lesione_rene_'+String(indice_riga)).value = value_patologia;
	 	}
	 	else if (value_organo == MAMMELLA){
	 				document.getElementById('lesione_mammella_'+String(indice_riga)).style.display = "";
	 				//document.getElementById('lesione_mammella_'+String(indice_riga)).value = value_patologia;
	 	}
	 	else if (value_organo == GENITALE){
	 				document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).style.display = "";
	 				//document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).value = value_patologia;
	 	}
	 	else if (value_organo == STOMACO){
	 				document.getElementById('lesione_stomaco_'+String(indice_riga)).style.display = "";
	 				//document.getElementById('lesione_stomaco_'+String(indice_riga)).value = value_patologia;
	 	}
	 	else if (value_organo == INTESTINO){
	 				document.getElementById('lesione_intestino_'+String(indice_riga)).style.display = "";
	 				//document.getElementById('lesione_intestino_'+String(indice_riga)).value = value_patologia;
	 	}
	 	else if (value_organo == OSTEOMUSCOLARI){
	 				document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).style.display = "";
	 				//document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).value = value_patologia;
	 	}
	 	else if ( (value_organo == 13) || (value_organo == 14) || (value_organo == 15) || (value_organo == 16) || (value_organo == 17) || (value_organo == 18)
	 			|| (value_organo == 19) || (value_organo == 20) || (value_organo == 21) || (value_organo == 22) || (value_organo == 23) || (value_organo == 24) || (value_organo == 25)) {
	 		document.getElementById('lesione_generici_'+String(indice_riga)).style.display = "";
	 		//document.getElementById('lesione_generici_'+String(indice_riga)).value = value_patologia;
	 	}
	 	else{
	 		document.getElementById('lesione_altro_'+String(indice_riga)).style.display = "";
	 		//document.getElementById('lesione_altro_'+String(indice_riga)).value = value_patologia;
	 	}
	 }
	 catch(err)
	 {
	 	alert(err.description);
	 }
	 	
	 }
	 
	 function riportaDataArrivoMacello(input){
			if(input.value == '' && document.getElementById('dataSeduta').value != ''){
				input.value = document.getElementById('dataSeduta').value;
			}
		}
	 
</script>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<%
	String param1 = "orgId=" + OrgDetails.getOrgId();
%>
<dhv:container name="stabilimenti_macellazioni_ungulati"
	selected="macellazioniuniche" object="OrgDetails" param="<%=param1%>">


	<form name="operazioniSeduta" id="operazioniSeduta"
		action="MacellazioneUnica.do?command=SalvaMacellazione&auto-populate=true"
		method="post">



		<input type="button" value="ANNULLA"
			onclick="location.href='MacellazioneUnica.do?command=MacellaSeduta&idSeduta=<%=request.getParameter("idSeduta")%>';" />
		<input type="button" value="SALVA" onclick="checkForm(this.form)" />

		<br />

		<%-- <input type="hidden" id="listaCapi" name="listaCapi" value="<%=listaCapi %>"/> --%>

		<%
			ArrayList<Step> steps = path.getListaSteps();
				Iterator i = steps.iterator();
				while (i.hasNext()) {
					Step thisStep = (Step) i.next();
					String file_to_include = "/mu/operazioni/" + thisStep.getJspPageToInclude();
		%>
		<div>
			<jsp:include page='<%=file_to_include%>' flush="true" />

		</div>
		<br />
		<%
			}
		%>

		<input type="hidden" name="idPathMacellazione" id="idPathMacellazione"
			value="<%=path.getId()%>" /> <input type="hidden" name="idSeduta"
			id="idSeduta" value="<%=seduta.getId()%>" /> <input type="button"
			value="ANNULLA"
			onclick="location.href='MacellazioneUnica.do?command=MacellaSeduta&idSeduta=<%=request.getParameter("idSeduta")%>';" />
			<input type="hidden" name="dataSeduta" id="dataSeduta" value="<%=toDateasString(seduta.getData()) %>" />
		<input type="button" value="SALVA" onclick="checkForm(this.form)" />
		

	</form>
</dhv:container>