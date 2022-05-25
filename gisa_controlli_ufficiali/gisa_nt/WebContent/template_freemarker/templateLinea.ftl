<script src="javascript/vendor/moment.min.js"></script>
<script src="javascript/noscia/addNoScia.js"></script>
<#--  <script src="javascript/noscia/lineAttivita.js"></script> -->
<#--  <script src="javascript/vendor/bootstrap-datepicker.js"></script> -->
<#-- <script src="javascript/vendor/bootstrap.min.js"></script> -->
<script src="javascript/noscia/codiceFiscale.js"></script>
<#-- <script src="javascript/noscia/utilFunction.js"></script> -->

<script type="text/javascript" src="dwr/interface/DWRnoscia.js"> </script>
<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<link rel="stylesheet" href="javascript/noscia/css/awesomplete.css" />
<script src="javascript/noscia/js/awesomplete.js"></script>

<script>
resetStar();
</script>

<#--  <script src="javascript/jquery-1.11.1.min.js"></script>  -->
<#-- <script src="javascript/jquery-ui.js"></script>  -->

<table class="trails" cellspacing="0">
<tr>
	<td> 
		<#if SUFFISSO_TAB_ACCESSI == '_ext'>
			<a href="AltriOperatori.do?command=DashboardScelta">ALTRI OPERATORI </a> > 
		<#else>
			<a href="AltriStabilimenti.do?command=Default">ALTRI STABILIMENTI </a> > 
		</#if>
		
		<#if codiceLinea == 'OPR-OPR-X'>
		<#else>
			<a href="GisaNoScia.do?command=Default">GESTIONE NO-SCIA</a> > 
		</#if> 
		INSERISCI STABILIMENTO
	
	</td>
</tr>
</table>


<#if codiceLinea == 'OPR-OPR-X'>
<table style="border: 3px solid black" cellpadding="10px" cellspacing="10px">
<tr><td style="background-color:#ffffbf" text-align="justify">
<font size="3px" color="red"><img src="images/post-it.png" width="50px"/> <b>GESTIONE CAVALIERE PRIVATI</b></font><br/>
<font size="3px">Il cavaliere "privati" e' adibito all'inserimento di quelle figure che esulano da tutte le possibilita' anagrafiche del sistema GISA, figure di comuni cittadini che non posseggono alcuna attivita' commerciale, ne' tantomeno una partita iva, ma che rientrano trasversalmente all'interno del PRCP come soggetti sottoposti ad attivita' di controllo.<br/>
Ad esempio verranno inseriti nei privati le vittime di una morsicatura, persone  coinvolte in una intossicazione alimentare o chiunque non sia inquadrato in alcuna macroarea della master list al quale l'Autorita' Competente attribuisca un Controllo Ufficiale.<br/>
L'ambito di inserimento potra' contemplare diversi casi, pertanto i soli campi obbligatori da compilare, necessari all'inserimento, saranno i dati anagrafici necessari a rendere valide le schede stesse.<br/><br/>
I cu per le attività AO9_B(EFFETTUAZIONE DI N ISPEZIONI PER OSSERVAZIONI SANITARIE SU ANIMALI DI PROPRIETA PRESSO IL DOMICILIO), AO9_C( EFFETTUAZIONE DI N ISPEZIONI PER OSSERVAZIONI SANITARIE SU ANIMALI DI PROPRIETA O RANDAGI IDENTIFICATI RICOVERATI PRESSO CANILI/STRUTTURE SANITARI) e B52(Valutazione comportamentale dei cani potenzialmente aggressivi) vanno inseriti in Macroare IUV -> Proprietari di Animali
</font>
</td></tr>
</table>
<#else></#if> 

<input type="hidden" id="id_asl_stab" value="${id_asl_stab}">
<#-- <form class="form-horizontal" role="form" method="post" action="GisaNoScia.do?command=Insert" onsubmit="return validateFormNoscia();"> -->
<form class="form-horizontal" role="form" method="post" action="GisaNoScia.do?command=Insert" id="form_inserimento">

<input type="hidden" id="id_tipologia_pratica" name="_b_id_tipologia_pratica" value="1"/>
<input type="hidden" id="id_causale" name="_b_id_causale" value="5">
<input type="hidden" id="data_pratica" name="_b_data_pratica" value="">
<input type="hidden" id="nota_pratica" name="_b_nota_pratica" value="inserimento OSA">

<#list lineaattivita as lista>
	<#if lista?is_first >
		<b>${lista.desc_linea}<b><br> 
		<input type="button" id="pulisciform" name="pulisciform" value="pulisci schermata"
				onclick="var link = 'GisaNoScia.do?command=Choose&codice_univoco_ml=${lista.codice_univoco_ml}';
       						window.location.href=link;"/>
		<br><br>
	</#if>
</#list>
<table class="table details" style="border-collapse: collapse" width="100%" cellpadding="5"> 
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
		<#elseif gruppo == 'attivita'>
			<#include "sezioni/templateSezioni.ftl">	
		<#elseif gruppo == 'attivitasperimentazione'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'attivitaprivati'>
			<#include "sezioni/templateSezioni.ftl">			
		<#elseif gruppo == 'abusivo'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'luogoabusivo'>
			<#include "sezioni/templateSezioni.ftl">
		<#elseif gruppo == 'dettagliaddizionali'>
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

</table>
<br><br>
<center>
<#-- <button type="submit" class="btn btn-primary" style="width:250px;">Salva</button> -->
<button type="button" class="yellowBigButton" style="width: 250px;" onClick="return validateFormNoscia()">SALVA</button>
</center>
</form>
<br><br>

<div id='dialogimprese'/>
<script src="javascript/noscia/widget.js"></script>
<#-- <script src="javascript/noscia/listner.js"></script> -->
<script>
function validateFormNoscia()
{
	var form_elements = document.getElementById("form_inserimento");
	for (var i = 0; i < form_elements.elements.length; i++){
		var this_element = form_elements.elements[i];
	    if (this_element.value.trim() == '' && this_element.hasAttribute('required')){
	    	alert('Attenzione! Controllare di aver inserito tutti i campi obbligatori indicati con * rosso');
	    	return false;
	    }
	}
	
	if(document.getElementById('lineaattivita_1_tipo_carattere_attivita').value == '2')
	{
	
		var data_iniziale = document.getElementById('lineaattivita_1_data_inizio_attivita').value;
		var data_finale = document.getElementById('lineaattivita_1_data_fine_attivita').value;

		var arr1 = data_iniziale.split("-");
		var arr2 = data_finale.split("-");

		var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
		var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);

		var r1 = d1.getTime();
		var r2 = d2.getTime();
		var diff = r2 - r1;

		if(diff < 0){
			alert('la data inizio attivita non puo essere successiva alla data fine attivita');
			return false;
		}
	}
	
	try{
		var keyfield =  document.getElementById('lineaattivita_1_cun_linea_attita');
		if (typeof(keyfield) != 'undefined' && keyfield != null){
			if(!verificaEsistenzaCun(keyfield)){
				return false;
			}
		} 
	}catch(err) {
		return false;
	}
	
	loadModalWindowCustom("Attendere Prego...");
	var form_ins = document.getElementById('form_inserimento');
	form_ins.submit();
	
}
</script>