/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

function ControllaPIVA(pi) {
	var formtest = true;
	var msg = 'Attenzione!\n'.toUpperCase();

	piField = document.getElementById(pi);
	pi = piField.value;

	if (pi == '') {
		msg += 'Campo Partita iva vuoto!\n'.toUpperCase();
		formtest = false;
	}
	if (pi.length != 11) {
		msg += 'Non corretta: la partita IVA dovrebbe essere lunga esattamente 11 caratteri.\n'
				.toUpperCase();
		formtest = false;
	}
	validi = "0123456789";
	for (i = 0; i < 11; i++) {
		if (validi.indexOf(pi.charAt(i)) == -1) {
			msg += 'Contiene un carattere non valido .\nI caratteri validi sono le cifre.\n'
					.toUpperCase();
			formtest = false;
		}
	}
	s = 0;
	for (i = 0; i <= 9; i += 2) {
		s += pi.charCodeAt(i) - '0'.charCodeAt(0);
	}

	for (i = 1; i <= 9; i += 2) {
		c = 2 * (pi.charCodeAt(i) - '0'.charCodeAt(0));
		if (c > 9)
			c = c - 9;
		s += c;
	}
	if ((10 - s % 10) % 10 != pi.charCodeAt(10) - '0'.charCodeAt(0)) {
		msg += 'Partita Iva non Valida secondo lo Standard.\n'.toUpperCase();
		formtest = false;
	}

	if (formtest == false) {
		msg += 'Vuoi continuare comunque ?'.toUpperCase();
		if (confirm(msg) == false)
			piField.value = '';
	}
}

var output;
function checkPartitaIva(idcampoIVA, idcampoCF, idcampoCFLegRap,
		tipo_impresa_id,idCampoRagSoc) {

	var e = document.getElementById(tipo_impresa_id);
	var tipoImpresa = e.options[e.selectedIndex].value;
	var partita_iva = document.getElementById(idcampoIVA).value;
	var cf = document.getElementById(idcampoCF).value;
	var rag_soc = document.getElementById(idCampoRagSoc).value;
	var cf_rap_leg = "";

	var url = "VerificaEsistenzaImpresa.do?command=Search&piva=" + partita_iva
			+ "&codfisc=" + cf+"&ragione_sociale="+rag_soc;
	if (tipoImpresa == '5' || tipoImpresa == '6') {

		that = $(
				'input[name="' + idcampoIVA + '"],input[name="' + idcampoCF
						+ '"]').not($('#' + idcampoIVA));

		if ($('#' + idcampoIVA).val().length) {
			that.removeAttr("class");
		} else {
			that.attr("class", "required");
			$('#' + idcampoIVA).removeAttr("class");
		}
	}

	if ($('input[name="' + idcampoIVA + '"]').val() != ''|| $('input[name="' + idcampoCF + '"]').val() != '') {
		
		//PopUpLoading('VerificaEsistenzaModal');
        loadModalWindowCustom("Verifica Esistenza Impresa. Attendere");


		var request = $.ajax({
			url : url,
			dataType : "json",
            cache: false,
		});

		request
				.done(function(result) {
					console.log(result);
					if (result.status == 1) {
						output = result;
						var htmlText = "<div></div><br><br>";
						htmlText += '<table class="table table-bordered"><thead><tr><th>Denominazione</th><th>Partita IVA</th><th>Rappresentante Legale</th><th>Sede Legale</th><th></th></tr></thead>'
						if (result.imprese != null && result.imprese.length > 0) {
							for (i = 0; i < result.imprese.length; i++) {
								htmlText += "<tbody><tr><td>"
										+ result.imprese[i].ragione_sociale
										+ "</td><td>"
										+ result.imprese[i].piva
										+ "</td><td>"
										+ (typeof result.imprese[i].soggettofisico.nome === 'undefined' ? ''
												: result.imprese[i].soggettofisico.nome)
										+ " "
										+ result.imprese[i].soggettofisico.cognome
										+ " "
										+ result.imprese[i].soggettofisico.codice_fiscale
										+ " "
										+ result.imprese[i].soggettofisico.indirizzo.comune.nome
										+ " "
										+ result.imprese[i].soggettofisico.indirizzo.provincia.description
										+ " "
										+ result.imprese[i].soggettofisico.indirizzo.via
										+ "</td><td>"
										+ result.imprese[i].indirizzo.comune.nome
										+ " "
										+ result.imprese[i].indirizzo.provincia.description
										+ " "
										+ result.imprese[i].indirizzo.via
										+ "</td><td><input type='button' value='Seleziona' onclick='selezionaImpresa("
										+ i + ")'></td></tr>";
							}
							htmlText += "</tbody></table>";
							
							  $( "#modalImprese" ).html(htmlText);
							  $('#modalImprese').dialog('open');
						}
					}
                    loadModalWindowUnlock();    
				});
		request.fail(function(jqXHR, textStatus) {
            loadModalWindowUnlock();    
			alert("KO");

		});
	}
}

var outputSoggFisico;
function checkCFSoggFis(idCampoCF, idCampoPiva) {
	
	cf = document.getElementById(idCampoCF).value;
	piva = document.getElementById(idCampoPiva).value;
	url = "VerificaEsistenzaSoggettoFisico.do?command=Search&codice_fiscale=" + cf+"&piva="+piva;
	

	if ($('input[name="' + idCampoCF + '"]').val() != '') {

        loadModalWindowCustom("Verifica Esistenza Soggetto Fisico. Attendere");

		var request = $.ajax({
			url : url,
			dataType : "json",
            cache: false,
		});

		request.done(function(result) {
					console.log(result);
					if (result.status == 1) {
						outputSoggFisico = result;
						var htmlText = '<table class="table table-bordered"><thead><tr><th>Nome</th><th>Cognome</th><th>Residenza</th><th></th></tr></thead>'
						if (result.soggFisico != null
								&& result.soggFisico.length > 0) {
							for (i = 0; i < result.soggFisico.length; i++) {
								htmlText += "<tbody><tr><td>"
										+ result.soggFisico[i].nome
										+ "</td><td>"
										+ result.soggFisico[i].cognome
										+ "</td><td>"
										+ result.soggFisico[i].indirizzo.comune.nome
										+ " "
										+ result.soggFisico[i].indirizzo.provincia.description
										+ " "
										+ result.soggFisico[i].indirizzo.toponimo.description
										+ " "
										+ result.soggFisico[i].indirizzo.via
										+ " "
										+ result.soggFisico[i].indirizzo.civico
										+ "</td><td><input type='button' value='Seleziona' onclick='selezionaSoggettoFisico("
										+ i + ")'></td></tr>";
							}
							htmlText += "</tbody></table>";
							
							  $( "#modalSoggFis" ).html(htmlText);
							  $('#modalSoggFis').dialog('open');

						}
					}
					loadModalWindowUnlock();
				});
		request.fail(function(jqXHR, textStatus) {
			loadModalWindowUnlock();
			alert("KO");
		});

	}

}


function checkEsistenzaStabilimento(partitaIva,cfImpresa, comune, via, civico,toponimo)
{
   var esito = -1;
   var url = "VerificaEsistenzaStabilimento.do?command=Search&indirizzo.comune.id="  + comune+
	   												  "&indirizzo.via=" + via +
	   												  "&indirizzo.civico=" + civico +
	   												  "&indirizzo.toponimo.code=" + toponimo;
  
   if(partitaIva != null && cfImpresa == null)
   {
   	url = url + "&impresa.piva="+partitaIva;
   }
   
   if(partitaIva == null && cfImpresa != null)
   {
   	url = url + "&impresa.codfisc="+cfImpresa;
   }
   
   
   var request = $.ajax({
				async: false,
				url : url,
				dataType : "json"
				});
	
   
	request.done(function(result) 
	{
		console.log(result);
		if (result.status==1)
			esito = 1;
		else
			esito = 0;
	});
	
	request.fail(function(jqXHR, textStatus) 
	{
		esito = -1;
	});
	
	return esito;
	
}


function selezionaImpresa(i)
{	
	
	var imprese = output.imprese;
	//ragione sociale 
  	$('input[name="rag_soc_imp"]').val(imprese[i].ragione_sociale);
  	
  	//piva
  	$('input[name="partita_iva_imp"]').val(imprese[i].piva); 
  	
  	//cf
	$('input[name="cf_imp"]').val(imprese[i].codfisc);
	
	//pec
	$('input[name="pec_imp"]').val(imprese[i].pec);
	
	//note
	/*$('input[name=""]').val(imprese[i].note);*/
	
	//via impresa
	$('input[name="via"]').val(imprese[i].indirizzo.via);
	
	//civico impresa
	$('input[name="civico"]').val(imprese[i].indirizzo.civico);
	
	//cap impresa
	$('input[name="cap"]').val(imprese[i].indirizzo.cap);
	
	//Provincia impresa
	$('input[name="prov"]').val(imprese[i].indirizzo.provincia.description);
	
	//Comune Impresa
	$('input[name="comune"]').val(imprese[i].indirizzo.comune.nome);
	
	//Toponimo Impresa
	$('input[name="toponimo"]').val(imprese[i].indirizzo.toponimo.description);
	
	//id provincia sede legale
	$('input[name="provinciaId"]').val(imprese[i].indirizzo.provincia.code);
	
	//id comune sede legale
	$('input[name="comuneId"]').val(imprese[i].indirizzo.comune.id);
	
	//id toponimo sede legale
	$('input[name="topId"]').val(imprese[i].indirizzo.toponimo.code);
	
	//latitudine impresa
	//$('input[name="impresa.indirizzo.latitudine"]').val(imprese[i].indirizzo.latitudine);
	
	//longitudine impresa
	//$('input[name="impresa.indirizzo.longitudine"]').val(imprese[i].indirizzo.longitudine);
		
	
	$('#modalImprese').dialog('close'); 
	
	//tipo impresa
	var element = document.getElementById('tipo_impresa_imp');
	$("select[id='tipo_impresa_imp']").val(imprese[i].tipo_impresa);
	var event = new Event('change');
	element.dispatchEvent(event);
	
	//Nazionalita Impresa
	
	var element = document.getElementById('nazione_sede_leg');
	$("select[id='nazione_sede_leg']").val(imprese[i].indirizzo.nazione.code);
		
	var event = new Event('change');
	element.dispatchEvent(event);
	
	
	// Soggetto fisico
	var soggettofisico =  imprese[i].soggettofisico;
	
	var element;
	var event;
	
	//Nazione Residenza
	element = document.getElementById('nazione_residenza_rapp_leg');
	$("select[id='nazione_residenza_rapp_leg']").val(soggettofisico.indirizzo.nazione.code);
	 event = new Event('change');
	element.dispatchEvent(event);
	
	
	//Nazionalita nascita
	element = document.getElementById('nazione_nascita_rapp_leg');
	$("select[id='nazione_nascita_rapp_leg']").val(soggettofisico.comune_nascita.provincia.nazione.code);
	event = new Event('change');
	element.dispatchEvent(event);
	
	
	// nome
  	$('input[name="nome_rapp_leg"]').val(soggettofisico.nome);
  	
  	//cognome
  	$('input[name="cognome_rapp_leg"]').val(soggettofisico.cognome); 
  	
  	//cf
	$('input[name="cf_rapp_leg"]').val(soggettofisico.codice_fiscale);
	
	//sesso
	 if (soggettofisico.sesso=='F' || soggettofisico.sesso=='f')
	    	$('input[id="sesso2"]').prop('checked', true);
	    else
	    	$('input[id="sesso1"]').prop('checked', true);
	
	//pec
	$('input[name="pec_legale_rapp_leg"]').val(soggettofisico.email);
	

	//data_nascita
	var data_nascita = moment(soggettofisico.data_nascita).format("YYYY-MM-DD");
	console.log('dataNascitaSoggFisico:'+data_nascita);
	$('input[name="data_nascita_rapp_leg"]').val(data_nascita);
		
	//Provincia
	$('input[name="prov_residenza"]').val(soggettofisico.indirizzo.provincia.description);
	
	//comune
	$('input[name="comune_residenza"]').val(soggettofisico.indirizzo.comune.nome);
	
	//toponimo
	$('input[name="toponimo_residenza"]').val(soggettofisico.indirizzo.toponimo.description);
	
	//via 
	$('input[name="via_residenza"]').val(soggettofisico.indirizzo.via);
	
	//civico 	
	$('input[name="civico_residenza"]').val(soggettofisico.indirizzo.civico);
	
	//cap 
	$('input[name="cap_residenza"]').val(soggettofisico.indirizzo.cap);
	
	$('#modalSoggFis').dialog('close');
	
	loadModalWindowUnlock();
	
	
}


function selezionaSoggettoFisico(i)
{	
	var soggettofisico = outputSoggFisico.soggFisico;
	
	
	// nome
  	$('input[name="nome_rapp_leg"]').val(soggettofisico[i].nome);
  	
  	//cognome
  	$('input[name="cognome_rapp_leg"]').val(soggettofisico[i].cognome); 
  	
  	//cf
	$('input[name="cf_rapp_leg"]').val(soggettofisico[i].codice_fiscale);
	
	//sesso
	 if (soggettofisico[i].sesso=='F' || soggettofisico[i].sesso=='f')
	    	$('input[id="sesso2"]').prop('checked', true);
	    else
	    	$('input[id="sesso1"]').prop('checked', true);
	
	//pec
	$('input[name="pec_legale_rapp_leg"]').val(soggettofisico[i].email);
	
	//telefono
	//$('input[name="impresa.soggettofisico.telefono"]').val(soggettofisico[i].telefono);
	
	//c.i.
	//$('input[name="impresa.soggettofisico.documento_identita"]').val(soggettofisico[i].documento_identita);
	
	//data_nascita
	var data_nascita = moment(soggettofisico[i].data_nascita).format("YYYY-MM-DD");
	console.log('dataNascitaSoggFisico:'+data_nascita);
	$('input[name="data_nascita_rapp_leg"]').val(data_nascita);
		
	//Provincia
	$('input[name="prov_residenza"]').val(soggettofisico[i].indirizzo.provincia.description);
	
	//comune
	$('input[name="comune_residenza"]').val(soggettofisico[i].indirizzo.comune.nome);
	
	//toponimo
	$('input[name="toponimo_residenza"]').val(soggettofisico[i].indirizzo.toponimo.description);
	
	//via 
	$('input[name="via_residenza"]').val(soggettofisico[i].indirizzo.via);
	
	//civico 	
	$('input[name="civico_residenza"]').val(soggettofisico[i].indirizzo.civico);
	
	//cap 
	$('input[name="cap_residenza"]').val(soggettofisico[i].indirizzo.cap);
	
	
	//id provincia residenza
	$('input[name="provincia_id_residenza"]').val(soggettofisico[i].indirizzo.provincia.code);
	
	//id comune residenza
	$('input[name="comune_id_residenza"]').val(soggettofisico[i].indirizzo.comune.id);
	
	//id  toponimo residenza
	$('input[name="top_id_residenza"]').val(soggettofisico[i].indirizzo.toponimo.code);
	
	 $('#modalSoggFis').dialog('close');

	
	var element;
	var event;
	
	//Nazione Residenza
	element = document.getElementById('nazione_residenza_rapp_leg');
	$("select[id='nazione_residenza_rapp_leg']").val(soggettofisico[i].indirizzo.nazione.code);
	 event = new Event('change');
	element.dispatchEvent(event);
	
	
	//Nazionalita nascita
	element = document.getElementById('nazione_nascita_rapp_leg');
	$("select[id='nazione_nascita_rapp_leg']").val(soggettofisico[i].comune_nascita.provincia.nazione.code);
	event = new Event('change');
	element.dispatchEvent(event);
	
	element = document.getElementById('comune_nascita_rapp_leg');
	$("select[id='comune_nascita_rapp_leg']").val(soggettofisico[i].comune_nascita.id);
	event = new Event('change');
	element.dispatchEvent(event);
	
	loadModalWindowUnlock();
	
}

function checkCAP (campo)
{
	var value = document.getElementById(campo).value;
	if (value == '80100')
		{
			document.getElementById(campo).value = "";
			alert("Attenzione il cap 80100 non e' accettato dal sistema");
		}
	//alert(value);
}

function valueSelected(campo)
{
	var index = campo.selectedIndex;
	return campo.options[index].value;
}


function showPopUp(nameDialog){
    $('#'+nameDialog).dialog('open');
}




