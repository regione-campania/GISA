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
			<#if tipologia_operatore == '802'>
			<a href="Parafarmacie.do?command=Details&idFarmacia=${altId}&opId=${altId}&orgId=${altId}">SCHEDA</a> > Importa in Anagrafica stabilimenti
			<#else>
			<a href="Accounts.do?command=Details&idFarmacia=${altId}&opId=${altId}&orgId=${altId}">SCHEDA</a> > Importa in Anagrafica stabilimenti
			</#if>
		</td>
	</tr>
</table>

<#if tipologia_operatore == '802'>
	<#-- il secondo parametro (19) si riferisce al numero di scheda centralizzata -->
	<a href ="#" onClick="openPopup(${altId}, '19')" >VISUALIZZA SCHEDA OPERATORE IN GISA</a>
<#else>
	<a href ="#" onClick="openPopup(${altId}, '1')" >VISUALIZZA SCHEDA OPERATORE IN GISA</a>
</#if>

<body onload ="VisualizzaValoriModifica(ValoriAnagrafica);resetStar();">

<form class="form-horizontal" role="form" method="post" action="GestioneAnagraficaAction.do?command=Insert" id="form_inserimento">

<input type="hidden" id="id_asl_stab" value="${id_asl_stab}">
<input type="hidden" id="alt_id" name="alt_id" value="${altId}"/>
<input type="hidden" id="tipo_linee_attivita" name="_b_tipo_linee_attivita" value="${tipo_linee_attivita}"/>
<input type="hidden" id="id_tipologia_pratica" name="_b_id_tipologia_pratica" value="${tipoPratica}"/>
<input type="hidden" id="idTipologiaPratica" name="idTipologiaPratica" value="${tipoPratica}"/>
<input type="hidden" id="id_causale" name="_b_id_causale" value="${id_causale}">


<input type="hidden" id="data_pratica" name="_b_data_pratica" value="${data_pratica}">
<input type="hidden" id="nota_pratica" name="_b_nota_pratica" value="Aggiornamento dati">
<input type="hidden" id="flag_pregresse" name="flag_pregresse" value="${flag_pregresse}" />
<#assign i = 0>
<#list linee_attivita as linea>
	<input type="hidden" name="lineaMappata${i}" id="lineaMappata${i}" value="${linea.mappato?string('true','false')}" />
	<#if ValoriAnagrafica['data_inizio_attivita_osa']!=''>
	<input type="hidden" id="lineaattivita_${i}_data_inizio_attivita" name="_b_lineaattivita_${i}_data_inizio_attivita" 
		value="${ValoriAnagrafica['data_inizio_attivita_osa']}" 
			/>
	<#else>
		<#if tipologia_operatore == '802'>
			<input type="hidden" id="lineaattivita_${i}_data_inizio_attivita" name="_b_lineaattivita_${i}_data_inizio_attivita" 
				value="" 
			/>
		</#if>
	</#if>
	<input type="hidden" name="_b_idLineaVecchia${i}" id="idLineaVecchia${i}" value="${linea.id}" />
	<input type="hidden" name="_b_idLineaVecchiaMasterlist${i}" id="idLineaVecchiaMasterlist${i}" value="${linea.idAttivita}" />
	<input type="hidden" id="lineaattivita_${i}_codice_univoco_ml" name="_b_lineaattivita_${i}_codice_univoco_ml" value="<#if linea.codice_istat??>${linea.codice_istat}<#else></#if>" />
	<input type="hidden" id="lineaattivita_${i}_data_fine_attivita" name="_b_lineaattivita_${i}_data_fine_attivita" value="" />
	<input type="hidden" id="lineaattivita_${i}_tipo_carattere_attivita" name="_b_lineaattivita_${i}_tipo_carattere_attivita" value="1" />
	<input type="hidden" id="lineaattivita_${i}_num_riconoscimento" name="_b_lineaattivita_${i}_num_riconoscimento" value="" />
	<input type="hidden" id="lineaattivita_${i}_tipo_attivita" name="_b_lineaattivita_${i}_tipo_attivita" value="${tipo_linee_attivita}" />
	<input type="hidden" id="lineaattivita_${i}_stato" name="_b_lineaattivita_${i}_stato" value="<#if statoStabilimento??>${statoStabilimento}<#else></#if>" />
	
	 <#assign i = i + 1>
</#list>

<div id="operazione_scheda">


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
		<#-- 
		<#elseif gruppo == 'attivitamultiple'>
			<#if tipoPratica == '16'>	
				<#include "sezioni/templateSezioni.ftl">		
			<#else></#if>
		 -->
		 
		</#if>
	<#else>
	</#if> 
</#list>


<tr id="tr_attivita_id_sezione">
	<th colspan="2" style="text-align : center">
		<input id="attivita_id_sezione" class="greenBigButton" type="button" value="Selezione linea/e di attività" onclick="" >
	</th>
</tr>	
<input id="numero_linee" type="hidden" value=""> 
<input id="numero_linee_effettivo" type="hidden" name="numero_linee_effettivo" value="0">

<#if flag_scia_or_noscia == 'NOSCIA'>
	<tr id="tr_attivita_id_sezione">
		<th colspan="2">
			LINEE DI ATTIVITA'
		</th>
	</tr>
	<tr>
		<td class="formLabel">LINEA</td>
		<td>${descr_linea_paraf}
			<#if tipologia_operatore == '802'>
			<input type="hidden" id="n_reg_import_802" name="n_reg_import_802" value="">
			<#else></#if>
		</td>
	</tr>
	<#if ValoriAnagrafica['data_inizio_attivita_osa'] !=''>
	<tr>
		<td class="formLabel">Data inizio attività</td>
		<td>${ValoriAnagrafica['data_inizio_attivita_osa']}</td>
	</tr>
	</#if>
	
</#if>

<#if ValoriAnagrafica['data_inizio_attivita_osa']==''>

	<tr>
		<td class="formLabel">Data inizio attività</td>
		<td>
        	<input value="" required placeholder="Inserisci data" type="text" id="_b_lineaattivita_data_inizio_attivita" name="_b_lineaattivita_data_inizio_attivita" autocomplete="off" onkeydown="return false">                
        	<script>
        	$( '#_b_lineaattivita_data_inizio_attivita' ).datepicker({
				  dateFormat: 'dd/mm/yy',
				  changeMonth: true,
				  changeYear: true,
				  yearRange: '-100:+3',
				  maxDate: "+0m", 					
				  dayNamesMin : [ 'do', 'lu', 'ma', 'me', 'gi', 've', 'sa' ],
				  monthNamesShort :['Gennaio','Febbraio','Marzo','Aprile','Maggio','Giugno', 'Luglio','Agosto','Settembre','Ottobre','Novembre','Dicembre'],
				  beforeShow: function(input, inst) {
                         setTimeout(function () {
                                    var offsets = $('#_b_lineaattivita_data_inizio_attivita').offset();
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
                                    var offsets = $('#_b_lineaattivita_data_inizio_attivita').offset();
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
        	</script>
		</td>
	</tr>
</#if>




</table><hr>
<br><br>
<center>


<button type="button" class="yellowBigButton" style="width: 250px; margin-right: 10%;" onClick="return validateForm()">SALVA</button>

<#-- vericare ritorno annulla per altre tipologie di operatori da importare es parafarmacie -->
<#if tipologia_operatore == '802'>
	<button type="button" class="yellowBigButton" style="width: 250px;" onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='Parafarmacie.do?command=Details&idFarmacia=${altId}&opId=${altId}&orgId=${altId}'">Annulla</button>
<#else>
	<button type="button" class="yellowBigButton" style="width: 250px;" onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='Accounts.do?command=Details&idFarmacia=${altId}&opId=${altId}&orgId=${altId}'">Annulla</button>
</#if>

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
<div id='dialogimprese'></div>

<div id='popuplineeattivita'></div>
<script src="javascript/gestioneanagrafica/aggiungiLinea.js"></script>

<script>


function openPopupLarge(url){
	
	  var res;
      var result;
      	  window.open(url,'popupSelect',
            'height=600px,width=1000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}


<#if codiceLinea == 'TEST-SCIA' || flag_scia_or_noscia == 'SCIA'>
	function validateForm()
	{
		var form_elements = document.getElementById("form_inserimento");
		for (var i = 0; i < form_elements.elements.length; i++){
			var this_element = form_elements.elements[i];
			if(this_element.hasAttribute('value')){
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
			
		var num_linee = document.getElementById('numero_linee_effettivo').value;
	
		var campi = document.getElementById("operazione_scheda").getElementsByTagName("input");
		for(var x = 0; x < campi.length; x++){
		
			if(campi[x].id.includes("codice_univoco_ml")>0){
				for(var y = 0; y < campi.length; y++){
					if((campi[x].id != campi[y].id) && (campi[x].id.includes("codice_univoco_ml")>0) && (campi[x].value == campi[y].value)){
						alert('Attenzione! Linea di attivita selezionata piu volte');
						return false;
					}
				}
			}
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
		
		<#if tipologia_operatore == '802'>
			if(document.getElementById('_b_lineaattivita_data_inizio_attivita')){
				document.getElementById('lineaattivita_0_data_inizio_attivita').value = document.getElementById('_b_lineaattivita_data_inizio_attivita').value; 
			}
		</#if>
		
		loadModalWindowCustom("Attendere Prego...");
		var form_ins = document.getElementById('form_inserimento');
		form_ins.submit();
	}
</#if>


<#if codiceLinea == 'TEST-SCIA' || flag_scia_or_noscia == 'SCIA'>
	gestione_tipo_scheda_inserimento();
	function gestione_tipo_scheda_inserimento()
	{
		var tipo_attivita = document.getElementById('tipo_linee_attivita').value;
					
		document.getElementById('attivita_id_sezione').onclick = function(){
				openPopupLarge('OpuStab.do?command=PrepareUpdateLineePregresse&stabId=${altId}&commit=false&tipoAttivita=${tipo_linee_attivita}');
			};
		document.getElementById('attivita_id_sezione').value="Aggiorna linee";
		if(document.getElementById('tr_attivita_id_sezione')!=null && document.getElementById('flag_pregresse')!=null && document.getElementById('flag_pregresse').value=='false')
		{
			document.getElementById('tr_attivita_id_sezione').style.display = 'none';
		}		
	
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
		
		
		if(document.getElementById('partita_iva_impresa')) {
			document.getElementById('partita_iva_impresa').removeAttribute('onblur');
		}

		document.getElementById('operazione_scheda').style='display:';
		
		
		if(document.getElementById('tr_stabilimento_id_sezione').style.display != 'none' && '${ValoriAnagrafica['comuneIdStabilimento']}' != '')
		{
			document.getElementById('ins_indirizzo_stabilimento').onclick = function(){
				openCapWidgetRidotta('toponimo_stabilimento','topIdStabilimento','via_stabilimento','civico_stabilimento',
							  'comune_stabilimento', 'comuneIdStabilimento','cap_stabilimento','provincia_stabilimento',
							  'provinciaIdStabilimento','', '-1', 
							 '${ValoriAnagrafica['comuneIdStabilimento']}', 
							 '${ValoriAnagrafica['provinciaIdStabilimento']}');
					document.getElementById('lat_stabilimento').value = '';
				    document.getElementById('long_stabilimento').value = '';
				};
		}
		
		else if(document.getElementById('tr_sede_legale_id').style.display != 'none' && '${ValoriAnagrafica['comuneIdSedeLegale']}' != '')
		{
			document.getElementById('ins_indirizzo_sede_legale').onclick = function(){
				openCapWidgetRidotta('toponimo_sede_legale','topIdSedeLegale','via_sede_legale','civico_sede_legale',
							  'comune_sede_legale', 'comuneIdSedeLegale','cap_sede_legale','provincia_sede_legale',
							  'provinciaIdSedeLegale','', '-1', 
							  '${ValoriAnagrafica['comuneIdSedeLegale']}', 
							  '${ValoriAnagrafica['provinciaIdSedeLegale']}'); 
			   
					};
		}		
			
		else if(document.getElementById('tr_rapp_legale_id').style.display != 'none' && '${ValoriAnagrafica['comuneIdResidenzaRappLegale']}' != '')
		{
			document.getElementById('ins_indirizzo_residenza_rapp_legale').onclick = function(){
				openCapWidgetRidotta('toponimo_residenza_rapp_legale','topIdResidenzaRappLegale','via_residenza_rapp_legale','civico_residenza_rapp_legale',
							  'comune_residenza_rapp_legale', 'comuneIdResidenzaRappLegale','cap_residenza_rapp_legale','provincia_residenza_rapp_legale',
							  'provinciaIdResidenzaRappLegale','', '-1', 
							  '${ValoriAnagrafica['comuneIdResidenzaRappLegale']}', 
							  '${ValoriAnagrafica['provinciaIdResidenzaRappLegale']}');
					};	
		}
			
		resetStar();
	}

<#else>
	if(document.getElementById('tr_attivita_id_sezione')!=null && document.getElementById('flag_pregresse')!=null && document.getElementById('flag_pregresse').value=='false')
	{
		document.getElementById('tr_attivita_id_sezione').style.display = 'none';
	}
	
	if(document.getElementById('partita_iva_impresa')) {
		document.getElementById('partita_iva_impresa').removeAttribute('onblur');
	}

	document.getElementById('operazione_scheda').style='display:';
	
	if(document.getElementById('tr_stabilimento_id_sezione').style.display != 'none' && '${ValoriAnagrafica['comuneIdStabilimento']}' != '')
	{
		document.getElementById('ins_indirizzo_stabilimento').onclick = function(){
			openCapWidgetRidotta('toponimo_stabilimento','topIdStabilimento','via_stabilimento','civico_stabilimento',
						  'comune_stabilimento', 'comuneIdStabilimento','cap_stabilimento','provincia_stabilimento',
						  'provinciaIdStabilimento','', '-1', 
						 '${ValoriAnagrafica['comuneIdStabilimento']}', 
						 '${ValoriAnagrafica['provinciaIdStabilimento']}');
				document.getElementById('lat_stabilimento').value = '';
			    document.getElementById('long_stabilimento').value = '';
			};
	}
	
	resetStar();
	
</#if> 


function openPopup(id,tipo){
	var res;
    var result;
	window.open('ServletServiziScheda?object_id='+id+'&tipo_dettaglio='+tipo+'&object_id_name=org_id&output_type=html&visualizzazione=screen&object_css=','popupSelect',
    'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}


</script>
 
</div>
</body> 