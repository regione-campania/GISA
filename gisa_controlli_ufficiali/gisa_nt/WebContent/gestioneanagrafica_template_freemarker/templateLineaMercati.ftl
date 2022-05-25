<script src="javascript/vendor/moment.min.js"></script>
<script src="javascript/noscia/addNoScia.js"></script>
<script src="javascript/noscia/codiceFiscale.js"></script>

<script type="text/javascript" src="dwr/interface/DWRnoscia.js"> </script>
<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script src="javascript/gestioneanagrafica/add.js"></script>

<script type="text/javascript" src="javascript/jquery.miny_1.7.2.js"></script>
<script src="javascript/jquery-ui.js" type="text/javascript" ></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />
<script src="javascript/jquery.validate.js"></script>

<link rel="stylesheet" href="javascript/noscia/css/awesomplete.css" />
<script src="javascript/noscia/js/awesomplete.js"></script>

<script>
resetStar();
</script>

<DIV ID='modalWindow' CLASS='unlocked'>
	<P CLASS='wait'> <img src="images/loadingmw.gif"> Attendere il completamento dell'operazione...</P>
</DIV>

<table class="trails" cellspacing="0">
<tr>
	<td>
		INSERISCI STABILIMENTO 
	</td>
</tr>
</table>


<input type="hidden" id="id_asl_stab" value="${id_asl_stab}">
<#-- <form class="form-horizontal" role="form" method="post" action="GestioneAnagraficaAction.do?command=Insert" onsubmit="return validateForm();"> -->
<form class="form-horizontal" role="form" method="post" action="GestioneAnagraficaAction.do?command=Insert" id="form_inserimento">


<input type="hidden" id="idTipologiaPratica" name="idTipologiaPratica" value="1"/>
<input type="hidden" id="id_causale" name="_b_id_causale" value="5"/>
<input type="hidden" id="nota_pratica" name="_b_nota_pratica" value="${motivoInserimento}"/>
<input type="hidden" id="data_pratica" name="_b_data_pratica" value="${dataPratica}"/>
<input type="hidden" id="url_redirect_ok" name="url_redirect_ok" value="${urlRedirectOK}"/>
<input type="hidden" id="url_redirect_ko" name="url_redirect_ko" value="${urlRedirectKO}"/>
<input type="hidden" id="tipo_linee_attivita" name="tipo_linee_attivita" value="1"/>

<body onload ="gestione_tipo_scheda_inserimento();">

<div id="scheda_inserimento_osa" style="display: none">

<#list lineaattivita as lista>
	<#if lista?is_first >
		<input type="button" id="pulisciform" name="pulisciform" value="pulisci schermata" style="display: none"
			onclick="var link = 'GestioneAnagraficaAction.do?command=Choose';			
       		window.location.href=link;"/>
	</#if>
</#list>

<table class="table details" id="tabella_linee" style="border-collapse: collapse" width="100%" cellpadding="5"> 
<#list lineaattivita as lista>
	<#if lista.ftl_name??> 
		<#assign gruppo = '${lista.ftl_name}'> 
		<#if gruppo == 'impresa'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'rappleg'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'sedeleg'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'stab'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'attivita'>
			<#include "sezioni/templateSezioni.ftl">			
		<#elseif gruppo == 'abusivo'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'luogoabusivo'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'dettagliaddizionali'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'attivitamultiple'>
			<#include "sezioni/templateSezioni.ftl">
			<#-- <#include "sezioni/templateLineeAttivita.ftl"> -->
		</#if>
	<#else>
	</#if> 
</#list>

</table>
<br><br>
<center>

<#-- <button type="submit" class="yellowBigButton" style="width: 250px;">SALVA</button> -->
<button type="button" class="yellowBigButton" style="width: 250px;" onClick="return validateForm()">SALVA</button>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" class="yellowBigButton" style="width: 250px;" value="ANNULLA" 
	onclick="var link = '${urlRedirectKO}';
    window.location.href=link;"/>

</center>
</div>
</form>
<br><br>

<div id='dialogimprese'/>
<div id='popuplineeattivita'/>
<script src="javascript/noscia/widget.js"></script>
<script src="javascript/gestioneanagrafica/aggiungi_linea_mercato.js"></script>

<script>
function validateForm()
{
	var form_elements = document.getElementById("form_inserimento");
	for (var i = 0; i < form_elements.elements.length; i++){
		var this_element = form_elements.elements[i];
	    if (this_element.value.trim() == '' && this_element.hasAttribute('required')){
	    	alert('Attenzione! Controllare di aver inserito tutti i campi obbligatori indicati con * rosso');
	    	return false;
	    }
	}
	
	var num_linee = document.getElementById('numero_linee_effettivo').value;
	if(num_linee == '0'){
		alert('Deve esserci almeno una linea di attivita');
		return false;
	}
	
	var campi = document.getElementById("scheda_inserimento_osa").getElementsByTagName("input");
	for(var x = 0; x < campi.length; x++){
	
		if(campi[x].id.includes("id_linea_attivita_ml")>0){
			for(var y = 0; y < campi.length; y++){
				if((campi[x].id != campi[y].id) && (campi[x].id.includes("id_linea_attivita_ml")>0) && (campi[x].value == campi[y].value)){
					alert('Attenzione! Linea di attivita selezionata piu volte');
					return false;
				}
			}
		}
	}
	
	var tipo_attivita = document.getElementById('tipo_linee_attivita').value;
	var tipo_impresa = document.getElementById('tipo_impresa').value;
	
	if(tipo_impresa == '17' && document.getElementById("codice_fiscale_impresa").value.trim() == ''){
		alert('ATTENZIONE. Inserire il Codice Fiscale nella sezione Dati impresa.');
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
		document.getElementById('nazione_residenza_rapp_legale').value = document.getElementById('nazione_sede_legale').value;
		document.getElementById('comune_residenza_estero_rapp_legale').value = document.getElementById('comune_estero_sede_legale').value;
		document.getElementById('comuneIdResidenzaRappLegale').value = document.getElementById('comuneIdSedeLegale').value;
		document.getElementById('topIdResidenzaRappLegale').value = document.getElementById('topIdSedeLegale').value;		
		document.getElementById('provinciaIdResidenzaRappLegale').value = document.getElementById('provinciaIdSedeLegale').value;		
		document.getElementById('via_residenza_rapp_legale').value = document.getElementById('via_sede_legale').value;
		document.getElementById('civico_residenza_rapp_legale').value = document.getElementById('civico_sede_legale').value;
		document.getElementById('cap_residenza_rapp_legale').value = document.getElementById('cap_sede_legale').value;
	}
	
    loadModalWindowCustom("Attendere Prego...");
	var form_ins = document.getElementById('form_inserimento');
	form_ins.submit();
}



function gestione_tipo_scheda_inserimento()
{
	document.getElementById('tr_codice_fiscale_impresa').innerHTML = '<td class="formLabel">CODICE FISCALE</td>' + 
					'<td>' +
						'<input type="text" id="codice_fiscale_impresa" name="_b_codice_fiscale" value="" maxlength="16" pattern="[A-Za-z0-9]{1,}" title="inserire caratteri alfanumerici">' + 
						'<input id="ugualecodicefiscale" type="checkbox" ' + 
							' onclick="if(this.checked){document.getElementById(\'codice_fiscale_impresa\').value=document.getElementById(\'partita_iva_impresa\').value;}else{document.getElementById(\'codice_fiscale_impresa\').value=\'\';}"/>UGUALE ALLA P.IVA' +
					'</td>'; 
	
	document.getElementById('codice_fiscale_impresa').onchange = function(){
		document.getElementById('ugualecodicefiscale').checked = false;
	};
	
	document.getElementById('ins_numero_registrazione').style = 'display: none';
	document.getElementById('numero_registrazione_stabilimento').disabled = true;
	document.getElementById('numero_registrazione_stabilimento').removeAttribute('required');
	document.getElementById('numero_registrazione_stabilimento').value = '';	
			
	document.getElementById('partita_iva_impresa').onchange = function(){
		if(document.getElementById('ugualecodicefiscale').checked){
			document.getElementById('ugualecodicefiscale').checked = false; 
			document.getElementById('codice_fiscale_impresa').value = '';
		}
	};
	
	var tipo_attivita = document.getElementById('tipo_linee_attivita').value;
	document.getElementById('ins_indirizzo_residenza_rapp_legale').onclick = function(){
			openCapWidget('toponimo_residenza_rapp_legale','topIdResidenzaRappLegale','via_residenza_rapp_legale',
						  'civico_residenza_rapp_legale','comune_residenza_rapp_legale','comuneIdResidenzaRappLegale',
						  'cap_residenza_rapp_legale','provincia_residenza_rapp_legale','provinciaIdResidenzaRappLegale');
			 };
	
	if(tipo_attivita == '1'){
		document.getElementById('coordinate_sede_legale').style = 'display: none';
		document.getElementById('calcola_coord_stabilimento').onclick = function(){
			getCoordinate('toponimo_stabilimento','via_stabilimento','comune_stabilimento','provincia_stabilimento','cap_stabilimento','lat_stabilimento','long_stabilimento');
			recuperaAsl('comuneIdStabilimento', 'asl_stabilimento');
		};
		
		document.getElementById('ins_indirizzo_sede_legale').onclick = function(){
			openCapWidget('toponimo_sede_legale','topIdSedeLegale','via_sede_legale','civico_sede_legale',
						  'comune_sede_legale','comuneIdSedeLegale','cap_sede_legale','provincia_sede_legale',
						  'provinciaIdSedeLegale');
		};
		
		document.getElementById('ins_indirizzo_stabilimento').style = 'display: none';
				
		document.getElementById('toponimo_stabilimento').value = '${toponimo_stabilimento}';
		document.getElementById('topIdStabilimento').value = '${topIdStabilimento}';
		document.getElementById('via_stabilimento').value = '${via_stabilimento}';
		document.getElementById('civico_stabilimento').value = '${civico_stabilimento}';
		document.getElementById('comune_stabilimento').value = '${comune_stabilimento}';
		document.getElementById('comuneIdStabilimento').value = '${comuneIdStabilimento}';
		document.getElementById('cap_stabilimento').value = '${cap_stabilimento}';
		document.getElementById('provincia_stabilimento').value = '${provincia_stabilimento}';
		document.getElementById('provinciaIdStabilimento').value = '${provinciaIdStabilimento}';
		
		document.getElementById('toponimo_stabilimento').style = 'display: ; background-color: #dddddd; text-align: center';
		document.getElementById('via_stabilimento').style = 'display: ; background-color: #dddddd; text-align: center';
		document.getElementById('civico_stabilimento').style = 'display: ; background-color: #dddddd; text-align: center';
		document.getElementById('comune_stabilimento').style = 'display: ; background-color: #dddddd; text-align: center';
		document.getElementById('cap_stabilimento').style = 'display: ; background-color: #dddddd; text-align: center';
		document.getElementById('provincia_stabilimento').style = 'display: ; background-color: #dddddd; text-align: center';
		
		$("._placeholder_provincia_stabilimento").remove();
		$('<span class="_placeholder_provincia_stabilimento">provincia </span>').insertBefore('#provincia_stabilimento');
		$("._placeholder_comune_stabilimento").remove();
		$('<span class="_placeholder_comune_stabilimento">comune </span>').insertBefore('#comune_stabilimento');
		$("._placeholder_cap_stabilimento").remove();
		$('<span class="_placeholder_cap_stabilimento">cap </span>').insertBefore('#cap_stabilimento');
		$("._placeholder_toponimo_stabilimento").remove();
		$('<span class="_placeholder_toponimo_stabilimento">indirizzo </span>').insertBefore('#toponimo_stabilimento');
		
		
		document.getElementById('tipo_impresa').onchange = function(){
			if(document.getElementById('tipo_impresa').value == '11' || document.getElementById('tipo_impresa').value == '17'){
				document.getElementById('tr_sede_legale_id').style = 'display: none';
				document.getElementById('tr_nazione_sede_legale').style = 'display: none';
				document.getElementById('indirizzo_sede_legale').style = 'display: none';
				document.getElementById('cap_residenza_rapp_legale').setAttribute('required', '');
				document.getElementById('cap_sede_legale').removeAttribute('required');
				document.getElementById('cap_residenza_rapp_legale').removeAttribute('readonly');
				document.getElementById('cap_residenza_rapp_legale').onkeydown= function(){return false;};
			}else{
				document.getElementById('tr_sede_legale_id').style = 'display: ';
				document.getElementById('tr_nazione_sede_legale').style = 'display: ';
				document.getElementById('indirizzo_sede_legale').style = 'display: ';
				document.getElementById('cap_residenza_rapp_legale').removeAttribute('required');
				document.getElementById('cap_sede_legale').setAttribute('required', '');
				document.getElementById('cap_residenza_rapp_legale').removeAttribute('readonly');
				document.getElementById('cap_residenza_rapp_legale').onkeydown= function(){return false;};
			}
			
			if(document.getElementById('tipo_impresa').value == '17'){
				document.getElementById('partita_iva_impresa').removeAttribute('required');
				document.getElementById('codice_fiscale_impresa').setAttribute('required', '');
				resetStar();
			}else{
				document.getElementById('partita_iva_impresa').setAttribute('required', '');
				document.getElementById('codice_fiscale_impresa').removeAttribute('required');
				resetStar();
			}
		};
		
	}
	
	document.getElementById('scheda_inserimento_osa').style='display:'; 
	
	resetStar();
	
}


</script>
</body>
