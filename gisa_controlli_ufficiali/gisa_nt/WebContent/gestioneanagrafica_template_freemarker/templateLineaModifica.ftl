<#-- commento --> 
<script src="javascript/vendor/moment.min.js"></script>
<script src="javascript/noscia/addNoScia.js"></script>
<script src="javascript/noscia/codiceFiscale.js"></script>

<script type="text/javascript" src="dwr/interface/DWRnoscia.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script src="javascript/gestioneanagrafica/add.js"></script>

<link rel="stylesheet" href="javascript/noscia/css/awesomplete.css" />
<script src="javascript/noscia/js/awesomplete.js"></script>

<script src="javascript/gestioneanagrafica/modify.js"></script>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OpuStab.do?command=SearchForm">ANAGRAFICA STABILIMENTI</a> > 
			<a href="GestioneAnagraficaAction.do?command=Details&altId=${altId}">SCHEDA</a> > Modifica SCHEDA
		</td>
	</tr>
</table>

<body onload ="VisualizzaValoriModifica(ValoriAnagrafica); 
			   if(document.getElementById('partita_iva_impresa')){
			   		document.getElementById('partita_iva_impresa').removeAttribute('readOnly');
			   		document.getElementById('partita_iva_impresa').removeAttribute('onblur');
			   } 
			   resetStar();">

<form class="form-horizontal" role="form" method="post" action="GestioneAnagraficaAction.do?command=Update" id="form_inserimento">


<input type="hidden" id="id_asl_stab" value="${id_asl_stab}">
<input type="hidden" id="alt_id" name="alt_id" value="${altId}"/>
<input type="hidden" id="tipo_linee_attivita" name="_b_tipo_linee_attivita" value="${tipo_linee_attivita}"/>
<input type="hidden" id="id_tipologia_pratica" name="_b_id_tipologia_pratica" value="${tipoPratica}"/>
<input type="hidden" id="idTipologiaPratica" name="idTipologiaPratica" value="${tipoPratica}"/>
<input type="hidden" id="id_causale" name="_b_id_causale" value="${id_causale}">

<#if codiceLinea == 'TEST-SCIA' || flag_scia_or_noscia == 'SCIA'>
	<div id="specifica_causale" style="display:">
	<center>
		<h2>CAUSALE OPERAZIONE DI MODIFICA SCHEDA: ERRATA CORRIGE</h2>
	
		<div id="dati_errata_corrige"  style="padding: 10px; border: 1px solid black; background: #BDCFFF">
			<label style="text-align:center; font-size: 15px;">numero richiesta errata corrige </label><br>
			<input type="text" id="numero_pratica_errata_corrige" name="_b_numero_pratica" autocomplete="off" size="40" style="text-align:center;"/>
			<font color="red"> *</font>
			<br><br>
			<label style="text-align:center; font-size: 15px;">data richiesta errata corrige </label><br>
			<input type="text" id="data_pratica_errata_corrige" name="_b_data_pratica" autocomplete="off" onkeydown="return false" size="15" style="text-align:center;"/>
			<font color="red"> *</font>
			<br><br>
			<label style="text-align:center; font-size: 15px;">nota richiesta errata corrige </label><br>
			<input type="text" id="nota_pratica_errata_corrige" name="_b_nota_pratica" autocomplete="off" size="100"/>
			<font color="red"> *</font>
			<br><br>
			<input type="hidden" id="idAggiuntaPratica" name="idAggiuntaPratica" value="${idAggiuntaPratica}"/>
			<a href="javascript:openUploadAllegatoGins(${idAggiuntaPratica}, 'richiesta_errata_corrige', 'GINS_Pratica')" id='allega'>Allega richiesta errata corrige</a>
			<input type='hidden' readonly='readonly' id='header_richiesta_errata_corrige' name='header_richiesta_errata_corrige' value=''/>
			<label id='titolo_richiesta_errata_corrige' name='titolo_richiesta_errata_corrige'></label>
			<input type='button' value='rimuovi allegato' 
				onclick="if(document.getElementById('header_richiesta_errata_corrige').value.trim() != ''){
							document.getElementById('header_richiesta_errata_corrige').value = '';
							document.getElementById('titolo_richiesta_errata_corrige').innerHTML = '';
						} "/>
		</div>
		
		<br><br>
	
		<button type="button" class="yellowBigButton" style="width: 250px;" 
			onClick="gestione_causale(); ">PROSEGUI</button>
		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="yellowBigButton" style="width: 250px;" 
			onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestioneAnagraficaAction.do?command=Details&altId=${altId}'">ANNULLA</button>
	</center>
	</div>
	
	<div id="operazione_scheda" style="display: none">
<#else>
	<input type="hidden" name="_b_numero_pratica" id="numero_pratica_errata_corrige" value=" " />
	<input type="hidden" name="_b_data_pratica" id="data_pratica_errata_corrige" value=" " />
	<input type="hidden" name="_b_nota_pratica" id="nota_pratica_errata_corrige" value="errata corrige modifica anagrafica" />
	<div id="operazione_scheda">
</#if>


<table class="table details" id="tabella_linee" style="border-collapse: collapse" width="100%" cellpadding="5"> 

<#list lineaattivita as lista>
	
	<#if lista.ftl_name??> 
		<#assign gruppo = '${lista.ftl_name}'> 
		<#if gruppo == 'impresa'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'impresasperimentazione'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'rappleg'>
			<#include "sezioni/templateSezioni.ftl"> 
		<#elseif gruppo == 'sedeleg'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'stab'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'dettagliaddizionali'>
			<#include "sezioni/templateSezioni.ftl">			
		<#elseif gruppo == 'abusivo'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'luogoabusivo'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'soggettooperatoreprivato'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'residenzaoperatoreprivato'>
			<#include "sezioni/templateSezioni.ftl">		
		<#elseif gruppo == 'luogocontrollooperatoreprivato'>
			<#include "sezioni/templateSezioni.ftl"> 
		 
		</#if>
	<#else>
	</#if> 
</#list>

</table><hr>
<br><br>
<center>

<button type="button" class="yellowBigButton" style="width: 250px;" onClick="return validateForm()">SALVA</button>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<button type="button" class="yellowBigButton" style="width: 250px;" onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestioneAnagraficaAction.do?command=Details&altId=${altId}'">Annulla</button>

</center>
</div>
</form>
<br><br>

<script src="javascript/noscia/widget.js"></script>
<script>
var ValoriAnagrafica = {
<#list ValoriAnagrafica?keys as key>
"${key}" : "${ValoriAnagrafica[key]}",
</#list>
}
</script> 
<div id='dialogimprese'/>

<script>

<#if codiceLinea == 'TEST-SCIA' || flag_scia_or_noscia == 'SCIA'>
	function validateForm()
	{
		var form_elements = document.getElementById("form_inserimento");
		for (var i = 0; i < form_elements.elements.length; i++){
			var this_element = form_elements.elements[i];
			if(this_element.hasAttribute('value') || this_element.hasAttribute('required')){
			    if (this_element.value.trim() == '' && this_element.hasAttribute('required')){
			    	alert('Attenzione! Controllare di aver inserito tutti i campi obbligatori indicati con * rosso');
			    	return false;
			    }
		    }
		}	
		
		if((document.getElementById('nazione_sede_legale') != null && document.getElementById('nazione_sede_legale').value=='106') && document.getElementById('toponimo_sede_legale')!=null && document.getElementById('tr_sede_legale_id').style.display != 'none' && document.getElementById('toponimo_sede_legale').value=='')
		{
			alert('Inserire indirizzo della sede legale');
			return false;
		}
		
		if(document.getElementById('toponimo_stabilimento')!=null && document.getElementById('tr_stabilimento_id_sezione').style.display != 'none' &&  document.getElementById('toponimo_stabilimento').value=='')
		{
			alert('Inserire indirizzo dello stabilimento');
			return false;
		}
		
		var tipo_attivita = document.getElementById('tipo_linee_attivita').value;
		var tipo_impresa = document.getElementById('tipo_impresa').value;	
		
		if(tipo_impresa == '17' && document.getElementById("codice_fiscale_impresa").value.trim() == ''){
			alert('ATTENZIONE. Inserire il Codice Fiscale Impresa.');
			return false;
		}
		
		if(tipo_attivita == '1' && (tipo_impresa == '11' || tipo_impresa == '17')){
			document.getElementById('nazione_sede_legale').value = document.getElementById('nazione_residenza_rapp_legale').value;
			document.getElementById('comune_estero_sede_legale').value = document.getElementById('comune_residenza_estero_rapp_legale').value;
			document.getElementById('comuneIdSedeLegale').value = document.getElementById('comuneIdResidenzaRappLegale').value;
			document.getElementById('topIdSedeLegale').value = document.getElementById('topIdResidenzaRappLegale').value;
			document.getElementById('provinciaIdSedeLegale').value = document.getElementById('provinciaIdResidenzaRappLegale').value;
			document.getElementById('via_sede_legale').value = document.getElementById('via_residenza_rapp_legale').value;
			document.getElementById('civico_sede_legale').value = document.getElementById('civico_residenza_rapp_legale').value;
			document.getElementById('cap_sede_legale').value = document.getElementById('cap_residenza_rapp_legale').value;
		}
		
		if(tipo_attivita == '2' && (tipo_impresa == '11' || tipo_impresa == '17')){
			document.getElementById('nazione_sede_legale').value = document.getElementById('nazione_residenza_rapp_legale').value;
			document.getElementById('comuneIdSedeLegale').value = document.getElementById('comuneIdResidenzaRappLegale').value;
			document.getElementById('topIdSedeLegale').value = document.getElementById('topIdResidenzaRappLegale').value;		
			document.getElementById('provinciaIdSedeLegale').value = document.getElementById('provinciaIdResidenzaRappLegale').value;		
			document.getElementById('via_sede_legale').value = document.getElementById('via_residenza_rapp_legale').value;
			document.getElementById('civico_sede_legale').value = document.getElementById('civico_residenza_rapp_legale').value;
			document.getElementById('cap_sede_legale').value = document.getElementById('cap_residenza_rapp_legale').value;
		}
		
	    loadModalWindowCustom("Attendere Prego...");
		var form_ins = document.getElementById('form_inserimento');
		form_ins.submit();
	}
<#else>
	function validateForm()
	{
		var form_elements = document.getElementById("form_inserimento");
		for (var i = 0; i < form_elements.elements.length; i++){
			var this_element = form_elements.elements[i];
			if(this_element.hasAttribute('value') || this_element.hasAttribute('required')){
			    if (this_element.value.trim() == '' && this_element.hasAttribute('required')){
			    	alert('Attenzione! Controllare di aver inserito tutti i campi obbligatori indicati con * rosso');
			    	return false;
			    }
		    }
		}
		
		loadModalWindowCustom("Attendere Prego...");
		var form_ins = document.getElementById('form_inserimento');
		form_ins.submit();
	}
</#if>

var verifica_esistenza_pratica = '0';
function gestione_causale(){

	if(document.getElementById('numero_pratica_errata_corrige').value.trim() == ''){
		alert('Attenzione: inserire numero richiesta errata corrige!');
		return false;
	}
	
	controllaEsistenzaNumeroPratica(document.getElementById('numero_pratica_errata_corrige').value.trim(), '-1', '2');
	if(verifica_esistenza_pratica == '1'){
		alert('Attenzione, numero richiesta errata corrige non valido perchè già utilizzato!');
		return false;
	}
	
	if(document.getElementById('data_pratica_errata_corrige').value.trim() == ''){
		alert('Attenzione: inserire data richiesta errata corrige!');
		return false;
	}
	
	if(document.getElementById('nota_pratica_errata_corrige').value.trim() == ''){
		alert('Attenzione: inserire nota richiesta errata corrige!');
		return false;
	}
	
	if(document.getElementById('header_richiesta_errata_corrige').value.trim() == ''){
		alert('Attenzione: inserire allegato richiesta errata corrige!');
		return false;
	}
	
	document.getElementById('operazione_scheda').style='display:'; 
	document.getElementById('specifica_causale').style='display: none';
}


popup_date_err_corr('data_pratica_errata_corrige');

function popup_date_err_corr(elemento_html_data){
	$( '#' + elemento_html_data ).datepicker({
		  dateFormat: 'dd-mm-yy',
		  changeMonth: true,
		  changeYear: true,
		  yearRange: '-100:+3',
		  minDate: '01-01-1990',
		  maxDate: 0,
		  dayNamesMin : [ 'do', 'lu', 'ma', 'me', 'gi', 've', 'sa' ],
		  monthNamesShort :['Gennaio','Febbraio','Marzo','Aprile','Maggio','Giugno', 'Luglio','Agosto','Settembre','Ottobre','Novembre','Dicembre'],
		  beforeShow: function(input, inst) {
              setTimeout(function () {
                         var offsets = $('#' + elemento_html_data).offset();
                         var top = offsets.top - 100;
                         inst.dpDiv.css({ top: top, left: offsets.left});
                         $(".ui-datepicker-next").hide();
							$(".ui-datepicker-prev").hide();
							$(".ui-state-default").css({'font-size': 15});
							$(".ui-datepicker-title").css({'text-align': 'center'});
							$(".ui-datepicker-calendar").css({'text-align': 'center'});
               });
             },
        onChangeMonthYear: function(year, month, inst) {
              setTimeout(function () {
                         var offsets = $('#' + elemento_html_data).offset();
                         var top = offsets.top - 100;
                         inst.dpDiv.css({ top: top, left: offsets.left});
                         $(".ui-datepicker-next").hide();
							$(".ui-datepicker-prev").hide();
							$(".ui-state-default").css({'font-size': 15});
							$(".ui-datepicker-title").css({'text-align': 'center'});
							$(".ui-datepicker-calendar").css({'text-align': 'center'});
               });
             }                                                  
		});
}


<#if codiceLinea == 'TEST-SCIA' || flag_scia_or_noscia == 'SCIA'>
	gestione_tipo_scheda_inserimento();
	function gestione_tipo_scheda_inserimento()
	{
		var tipo_attivita = document.getElementById('tipo_linee_attivita').value;
	
		if(tipo_attivita == '2'){			
			
			if(ValoriAnagrafica['tipo_impresa'] == '11' || ValoriAnagrafica['tipo_impresa'] == '17'){
				document.getElementById('tr_sede_legale_id').style = 'display: none';
				<#-- document.getElementById('tr_comune_estero_sede_legale').style = 'display: none';  -->
				document.getElementById('tr_nazione_residenza_rapp_legale').style = 'display: none';
				document.getElementById('indirizzo_sede_legale').style = 'display: none'; 
				document.getElementById('nazione_residenza_rapp_legale').value = '106';
				document.getElementById('tr_comune_residenza_estero_rapp_legale').style = 'display: none';
				document.getElementById('indirizzo_residenza_rapp_legale').style = 'display: '; 
				document.getElementById('comuneIdSedeLegale').value = '';
				document.getElementById('topIdSedeLegale').value = '';		
				document.getElementById('provinciaIdSedeLegale').value = '';		
				document.getElementById('via_sede_legale').value = '';
				document.getElementById('civico_sede_legale').value = '';
				document.getElementById('cap_sede_legale').value = '';
				document.getElementById('toponimo_sede_legale').value = '';
				document.getElementById('comune_sede_legale').value = '';
				document.getElementById('provincia_sede_legale').value = '';
				
				document.getElementById('lat_sede_legale').value = '';
				document.getElementById('long_sede_legale').value = '';
				
				var ind_res = $("#indirizzo_residenza_rapp_legale").closest('tr');
				var tr_telefono_rapp_leg = $("#tr_telefono_rapp_legale").closest('tr');
				ind_res.insertAfter(tr_telefono_rapp_leg);
				
				document.getElementById('cap_residenza_rapp_legale').setAttribute('required', '');
				document.getElementById('cap_sede_legale').removeAttribute('required');
				document.getElementById('cap_residenza_rapp_legale').removeAttribute('readonly');
				document.getElementById('cap_residenza_rapp_legale').onkeydown= function(){return false;};
		
				document.getElementById('ins_indirizzo_residenza_rapp_legale').onclick = function(){
					openCapWidget('toponimo_residenza_rapp_legale','topIdResidenzaRappLegale','via_residenza_rapp_legale','civico_residenza_rapp_legale',
								  'comune_residenza_rapp_legale','comuneIdResidenzaRappLegale','cap_residenza_rapp_legale','provincia_residenza_rapp_legale',
								  'provinciaIdResidenzaRappLegale','campania', document.getElementById('id_asl_stab').value);
					document.getElementById('lat_sede_legale').value = '';
				    document.getElementById('long_sede_legale').value = '';
				};
				
				
				document.getElementById('calcola_coord_sede_legale').onclick = function(){
					getCoordinate('toponimo_residenza_rapp_legale','via_residenza_rapp_legale','comune_residenza_rapp_legale',
								  'provincia_residenza_rapp_legale','cap_residenza_rapp_legale','lat_sede_legale','long_sede_legale');
				};
			}
			
	
		}
		
		resetStar();
	}

<#else>
</#if> 


function controllaEsistenzaNumeroPratica(numeroPratica, comune_ric, id_causale_pratica)
{
	loadModalWindowCustom('Verifica esistenza richiesta in corso. Attendere...');
	DWRnoscia.controlloEsistenzaNumeroPratica(numeroPratica, comune_ric, id_causale_pratica,{callback:controllaEsistenzaNumeroPraticaCallBack,async:false});
}

function controllaEsistenzaNumeroPraticaCallBack(val)
{	
	var dati = val;
	var objresp;
	objresp = JSON.parse(dati);
	var len = objresp.length;
	if (len > 0){
		verifica_esistenza_pratica = '1';
		loadModalWindowUnlock();	
		
	} else {
		verifica_esistenza_pratica = '0';
		loadModalWindowUnlock();
		}
}

</script>
 
</div>
</body> 