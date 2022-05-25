 
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
 
<form class="form-horizontal" role="form" method="post" action="GestioneAnagraficaAction.do?command=VariazioneTitolaritaCambioSoggettoFisico" id="form_inserimento">

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
			<#if gruppo == 'rappleg'>
				<#include "sezioni/templateSezioni.ftl"> 	
			</#if>
		<#else>
		</#if> 
	</#list>
	
	</table>
	<br><br>
	<center>

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
	
	loadModalWindowCustom("Attendere Prego...");
	var form_ins = document.getElementById('form_inserimento');
	form_ins.submit();
}

document.getElementById('tr_rapp_legale_id').innerHTML = '<th colspan="2">RAPPRESENTANTE LEGALE</th>';
 
resetStar();

<#if id_causale == '1'>
	document.getElementById('operazione_scheda').style='display:'; 
<#else></#if>

<#if tipo_impresa == '11'>
		document.getElementById('operazione_scheda').style='display: none';
		alert('Attenzione, operazione non consentita per stabilimenti senza sede fissa con tipo impresa uguale ad impresa individuale!');
		loadModalWindowCustom('Attendere Prego...'); 
		window.location.href='GestioneAnagraficaAction.do?command=Details&altId=${altId}';
<#else></#if>

								
</script>
