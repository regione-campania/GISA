 
<script src="javascript/vendor/moment.min.js"></script>
<script src="javascript/noscia/addNoScia.js"></script>
<script src="javascript/noscia/codiceFiscale.js"></script>

<script type="text/javascript" src="dwr/interface/DWRnoscia.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script src="javascript/gestioneanagrafica/add.js"></script>

<link rel="stylesheet" href="javascript/noscia/css/awesomplete.css" />
<script src="javascript/noscia/js/awesomplete.js"></script>

<script>
resetStar();
</script>

<table class="trails" cellspacing="0">
<tr>
<td>
	<a href="OpuStab.do?command=SearchForm">ANAGRAFICA STABILIMENTI</a> > 
	<a href="GestioneAnagraficaAction.do?command=Details&altId=${altId}">SCHEDA</a> > SUBINGRESSO
</td>
</tr>
</table>
 
<body>
<form class="form-horizontal" role="form" method="post" action="GestioneAnagraficaAction.do?command=VariazioneTitolarita" id="form_inserimento">
<b>MODIFICA SCHEDA: SUBINGRESSO</b><br>
<input type="hidden" id="id_asl_stab" value="${id_asl_stab}"/>
<input type="hidden" id="alt_id" name="alt_id" value="${altId}"/>
<input type="hidden" id="altId" value="${altId}" />

<#if id_causale == '1'>
	<div style="border: 1px solid black; background: #BDCFFF">
	<br>
	&nbsp;&nbsp;&nbsp;&nbsp;NUMERO PRATICA: ${numeroPratica} <br>
	&nbsp;&nbsp;&nbsp;&nbsp;TIPO PRATICA: SUBINGRESSO <br>
	&nbsp;&nbsp;&nbsp;&nbsp;DATA PEC / DATA SCIA: ${dataPratica} <br>
	&nbsp;&nbsp;&nbsp;&nbsp;COMUNE: ${comuneTesto} <br>
	<br>
	<input type="hidden" id="numeroPratica" name="numeroPratica" value="${numeroPratica}"/>
	<input type="hidden" id="idTipologiaPratica" name="idTipologiaPratica" value="${tipoPratica}"/>
	<input type="hidden" id="idComunePratica" name="idComunePratica" value="${comunePratica}"/>
	<input type="hidden" id="id_causale" name="_b_id_causale" value="1"/>
	</div>
	<br/>
<#else>
	<input type="hidden" id="idTipologiaPratica" name="idTipologiaPratica" value="4"/>
	<@include_page path="../gestioneanagrafica/include/sezione_gestione_causale.jsp" />

</#if>

<input type="hidden" id="tipo_linee_attivita" value="1" />
<div id="operazione_scheda" style="display: none">
<center>
<button type="button" class="yellowBigButton" style="width: 450px;" 
onClick="window.open('GestioneAnagraficaAction.do?command=DetailsPopup&altId=${altId}', 'popupSelect','height=600px,width=1200px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');"
>IMPRESA A CUI SI SUBENTRA</button>
</center>
<br>
<input type="button" id="pulisciform" name="pulisciform" value="pulisci schermata" style="display: none"/>
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
		</#if>
	<#else>
	</#if> 
</#list>



</table><hr>
<br><br>
<center>

<#-- <button type="submit" class="yellowBigButton" style="width: 250px;">Salva</button>  -->
<button type="button" class="yellowBigButton" style="width: 250px;" onClick="return validateForm()">SALVA</button>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<#if id_causale == '1'>
<button type="button" class="yellowBigButton" style="width: 250px;"
	onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestionePraticheAction.do?command=ListaPraticheStabilimenti'">Torna alla lista pratiche</button>
<#else>
<button type="button" class="yellowBigButton" style="width: 250px;" 
	onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestioneAnagraficaAction.do?command=Details&altId=${altId}'">Annulla</button>
</#if>

</center>
</div>
</form>
<br><br>

<div id='dialogimprese'/>
</body> 
<script src="javascript/noscia/widget.js"></script>


<script>
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
	
	var tipo_impresa = document.getElementById('tipo_impresa').value;
	
	if(tipo_impresa == '17' && document.getElementById("codice_fiscale_impresa").value.trim() == ''){
		alert('ATTENZIONE. Inserire il Codice Fiscale nella sezione Dati impresa.');
		return false;
	}
	
	if(tipo_impresa == '11' || tipo_impresa == '17'){
		document.getElementById('nazione_sede_legale').value = document.getElementById('nazione_residenza_rapp_legale').value;
		document.getElementById('comune_estero_sede_legale').value = document.getElementById('comune_residenza_estero_rapp_legale').value;
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

<#if id_causale == '1'>
	document.getElementById('operazione_scheda').style='display:'; 
<#else></#if>

document.getElementById('tr_dati_impresa_id').childNodes[1].childNodes[0].nodeValue = 'Dati impresa subentrante \t';

resetStar();
	
</script>
