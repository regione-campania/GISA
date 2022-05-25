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

<table class="trails" cellspacing="0">
<tr>
	<td>
		<a href="AltriStabilimenti.do?command=Default">ALTRI STABILIMENTI </a> > 
		<#if codiceLinea == 'OPR-OPR-X'>
		<#else>
			<a href="GisaNoScia.do?command=Default">GESTIONE NO-SCIA</a> > 
		</#if> 
		INSERISCI STABILIMENTO
	</td>
</tr>
</table>

<input type="hidden" id="id_asl_stab" value="${id_asl_stab}">
<form class="form-horizontal" role="form" method="post" action="GisaNoSciaGINS.do?command=Insert" onsubmit="return validateFormNoscia();">

<div id="specifica_causale" style="display:">
<center>
	<h2>seleziona la causale per cui si insersce questo OSA</h2>
	<br>
	<input type="hidden" id="idTipologiaPratica" name="idTipologiaPratica" value="1"/>
	<select id="id_causale" name="_b_id_causale" style="font-size: 20px;" onchange="cambio_causale();">
		<option value="3">pratica forze dell'ordine</option>
		<option value="4" selected>presa visione diretta</option>
		<option value="5">altro</option>
	</select>

	<br><br>

	<div id="dati_presa_visione" style="padding: 10px; border: 1px solid black; background: #BDCFFF; display:" >
		
		<label style="text-align:center; font-size: 15px;">data presa visione diretta </label><br>
		<input type="text" id="data_pratica_visione_diretta" name="_b_data_pratica" autocomplete="off" size="15" style="text-align:center;"/>
		<br><br>
		<label style="text-align:center; font-size: 15px;">nota presa visione diretta </label><br>
		<input type="text" id="nota_pratica_visione_diretta" name="_b_nota_pratica" autocomplete="off" size="100"/><br>
		<font color="red">*descrizione dalla modalità di rilevazione</font>
	</div>
	
	<div id="dati_forze_ordine" display="none" style="padding: 10px; border: 1px solid black; background: #BDCFFF; display: none">
		<label style="text-align:center; font-size: 15px;">data pratica forze dell'ordine </label><br>
		<input type="text" id="data_pratica_forze_ordine" name="_b_data_pratica" autocomplete="off" size="15" style="text-align:center;"/>
		<br><br>
		<label style="text-align:center; font-size: 15px;">nota pratica forze dell'ordine </label><br>
		<input type="text" id="nota_pratica_forze_ordine" name="_b_nota_pratica" autocomplete="off" size="100"/>
	</div>
	
	<div id="dati_pratica_altro" display="none" style="padding: 10px; border: 1px solid black; background: #BDCFFF; display: none">
		<label style="text-align:center; font-size: 15px;">descrizione</label><br>
		<input type="text" id="nota_pratica_altro" name="_b_nota_pratica" autocomplete="off" size="100"/>
		<br><br>
		<label style="text-align:center; font-size: 15px;">data</label><br>
		<input type="text" id="data_pratica_altro" name="_b_data_pratica" autocomplete="off" size="15" style="text-align:center;"/>
	</div>
	
	<br><br>

	<button type="button" class="yellowBigButton" style="width: 250px;" 
		onClick="gestione_causale(); ">AVANTI</button>
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="yellowBigButton" style="width: 250px;" 
		onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GisaNoSciaGINS.do?command=Default'">ANNULLA</button>
</center>
</div>

<div id="scheda_inserimento_osa" style="display: none">
<#if codiceLinea == 'OPR-OPR-X'>
<table style="border: 3px solid black" cellpadding="10px" cellspacing="10px">
<tr><td style="background-color:#ffffbf" text-align="justify">
<font size="3px" color="red"><img src="images/post-it.png" width="50px"/> <b>GESTIONE CAVALIERE PRIVATI</b></font><br/>
<font size="3px">Il cavaliere "privati" e' adibito all'inserimento di quelle figure che esulano da tutte le possibilita' anagrafiche del sistema GISA, figure di comuni cittadini che non posseggono alcuna attivita' commerciale, ne' tantomeno una partita iva, ma che rientrano trasversalmente all'interno del PRI come soggetti sottoposti ad attivita' di controllo.<br/>
Ad esempio verranno inseriti nei privati le vittime di una morsicatura, persone  coinvolte in una intossicazione alimentare o chiunque non sia inquadrato in alcuna macroarea della master list al quale l'Autorità Competente attribuisca un Controllo Ufficiale.<br/>
L'ambito di inserimento potra' contemplare diversi casi, pertanto i soli campi obbligatori da compilare, necessari all'inserimento, saranno i dati anagrafici necessari a rendere valide le schede stesse.
</font>
</td></tr>
</table>
<#else></#if> 

<#list lineaattivita as lista>
	<#if lista?is_first >
		<b>${lista.desc_linea}<b><br> 
		<input type="button" id="pulisciform" name="pulisciform" value="pulisci schermata"
				onclick="var link = 'GisaNoSciaGINS.do?command=Choose&codice_univoco_ml=${lista.codice_univoco_ml}';
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

<button type="submit" class="yellowBigButton" style="width: 250px;">SALVA</button>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<#list lineaattivita as lista>
	<#if lista?is_first >
		<button type="button" class="yellowBigButton" style="width: 250px;" 
		onClick="loadModalWindowCustom('Attendere Prego...'); 
			 window.location.href='GisaNoSciaGINS.do?command=Choose&codice_univoco_ml=${lista.codice_univoco_ml}'">ANNULLA</button>
	</#if>
</#list>

</center>
</div>
</form>
<br><br>

<div id='dialogimprese'/>
<script src="javascript/noscia/widget.js"></script>
<#-- <script src="javascript/noscia/listner.js"></script> -->
<script>
function validateFormNoscia()
{
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
		}else{
			try{
		        loadModalWindowCustom("Attendere Prego...");
				return true;
			}catch(err) {
				return false;
			}
		}
	}
	else{
		try{
			var keyfield =  document.getElementById('lineaattivita_1_cun_linea_attita');
			if (typeof(keyfield) != 'undefined' && keyfield != null){
				if(verificaEsistenzaCun(keyfield)){
					loadModalWindowCustom("Attendere Prego...");
					return true;
				}else{
					return false;
				}
			}else{
				loadModalWindowCustom("Attendere Prego...");
				return true;
			}
	        
		}catch(err) {
			return false;
		}
	}

}
</script>

<script>

function cambio_causale(){
	var id_tipo_causale = document.getElementById('id_causale').value;
	
	if(id_tipo_causale == '3'){
		document.getElementById('dati_presa_visione').style.display='none';
		document.getElementById('dati_forze_ordine').style.display='';
		document.getElementById('dati_pratica_altro').style.display='none';
	}
	
	if(id_tipo_causale == '4'){
		document.getElementById('dati_presa_visione').style.display='';
		document.getElementById('dati_forze_ordine').style.display='none';
		document.getElementById('dati_pratica_altro').style.display='none';
	}
	
	if(id_tipo_causale == '5'){
		document.getElementById('dati_presa_visione').style.display='none';
		document.getElementById('dati_forze_ordine').style.display='none';
		document.getElementById('dati_pratica_altro').style.display='';
	}
}

function gestione_causale(){
	
	var id_tipo_causale = document.getElementById('id_causale').value;
	
	if(id_tipo_causale == '3'){
		if(document.getElementById('data_pratica_forze_ordine').value.trim() == ''){
			alert('Attenzione: inserire data pratica forze dell ordine!');
			return false;
		}
		
		if(document.getElementById('nota_pratica_forze_ordine').value.trim() == ''){
			alert('Attenzione: inserire nota pratica forze dell ordine!');
			return false;
		}
		document.getElementById('data_pratica_visione_diretta').disabled = true;
		document.getElementById('nota_pratica_visione_diretta').disabled = true;
		document.getElementById('data_pratica_altro').disabled = true;
		document.getElementById('nota_pratica_altro').disabled = true;
	}
	
	if(id_tipo_causale == '4'){
		if(document.getElementById('data_pratica_visione_diretta').value.trim() == ''){
			alert('Attenzione: inserire data pratica presa visione diretta!');
			return false;
		}
		
		if(document.getElementById('nota_pratica_visione_diretta').value.trim() == ''){
			alert('Attenzione: inserire nota pratica presa visione diretta!');
			return false;
		}
		document.getElementById('data_pratica_forze_ordine').disabled = true;
		document.getElementById('nota_pratica_forze_ordine').disabled = true;
		document.getElementById('data_pratica_altro').disabled = true;
		document.getElementById('nota_pratica_altro').disabled = true;
	}
	
	if(id_tipo_causale == '5'){
		if(document.getElementById('nota_pratica_altro').value.trim() == ''){
			alert('Attenzione: inserire una descrizione!');
			return false;
		}
		
		if(document.getElementById('data_pratica_altro').value.trim() == ''){
			alert('Attenzione: inserire il campo data!');
			return false;
		}
		
		document.getElementById('data_pratica_forze_ordine').disabled = true;
		document.getElementById('nota_pratica_forze_ordine').disabled = true;
		document.getElementById('data_pratica_visione_diretta').disabled = true;
		document.getElementById('nota_pratica_visione_diretta').disabled = true;
	}
	
	document.getElementById('scheda_inserimento_osa').style='display:'; 
	document.getElementById('specifica_causale').style='display: none';
}

popup_date('data_pratica_visione_diretta');
popup_date('data_pratica_forze_ordine');
popup_date('data_pratica_altro');

function popup_date(elemento_html_data){
	$( '#' + elemento_html_data ).datepicker({
		  dateFormat: 'dd-mm-yy',
		  changeMonth: true,
		  changeYear: true,
		  yearRange: '-100:+3',
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


</script>

