<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="gestione_documenti/schede/schede_anagrafica_screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="gestione_documenti/schede/schede_anagrafica_print.css" />

<link rel="stylesheet" href="javascript/noscia/css/awesomplete.css" />
<script src="javascript/noscia/js/awesomplete.js"></script>

<table class="headerStampa" width="100%">
<col width="33%"/><col width="33%"/><col width="33%"/>
<tr>

<td>
	<div align="left"><img style="text-decoration: none;" width="80" height="80" documentale_url="" src="gestione_documenti/schede/images/regionecampania.jpg" /></div>
</td>
<td>
	<div class="boxIdDocumento"></div>
	<div class="boxOrigineDocumento"><@include_page path="../hostName.jsp"/></div>
	<label class="titolo">Scheda Anagrafica</label>
</td>
<td>
	<div align="right">
	<img style="text-decoration: none;" height="80px" width="200px" documentale_url="" 
		src="gestione_documenti/schede/images/<#if "${descrizione_asl_print}"??>${descrizione_asl_print}<#else></#if>.jpg" />
	</div>
</td>
</tr>
</table>

<script src="javascript/noscia/addNoScia.js"></script>
<script src="javascript/gestioneanagrafica/detail.js"></script>
<script src="javascript/jquery-1.11.1.min.js"></script>    
<script src="javascript/jquery-ui.js"></script>  

<input type="hidden" id="codice_linea_template" value="${codiceLinea}" />
<input type="hidden" id="tipo_attivita_stab_template" value="${tipo_attivita_stabilimento}" />

<body onload ="VisualizzaDettaglio(ValoriAnagrafica, ValoriExtra, ValoriLinee, ValoriEstesi, ValoriInfoSpec)">   
<div id="dettaglio" style="display: none" >

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

<tr id="trLinee"><th colspan="2">LINEE DI ATTIVITA'</th></tr>

<#if id_info_specifica??>
	<#if id_info_specifica != ''>
		<tr id="trInformazioneSpecifica"><th colspan="2">INFORMAZIONE SPECIFICA</th></tr>
	<#else></#if>
<#else></#if>

<tr id="trExtra"><th colspan="2">INFORMAZIONI AGGIUNTIVE</th></tr>

</table><hr><br>



<script>
var ValoriAnagrafica = {
<#list ValoriAnagrafica?keys as key>
"${key}" : "${ValoriAnagrafica[key]}",
</#list>
}
</script> 

<script>
var ValoriExtra = {
<#list ValoriExtra?keys as key>
"${key}" : "${ValoriExtra[key]}",
</#list>
}
</script>

<script>
var ValoriLinee = {
<#list ValoriLinee?keys as key>
"${key}" : "${ValoriLinee[key]}",
</#list>
}
</script>

<script>
var ValoriEstesi = {
<#if ValoriEstesi??>
	<#list ValoriEstesi?keys as key>
		"${key}" : "${ValoriEstesi[key]}",
	</#list>
<#else>
</#if>
}
</script>

<script>
var ValoriInfoSpec = {
<#if ValoriInfoSpecifica??>
	<#list ValoriInfoSpecifica?keys as key>
		"${key}" : "${ValoriInfoSpecifica[key]}",
	</#list>
<#else>
</#if>
}
</script>
 
</div>
</body> 