<script src="javascript/vendor/moment.min.js"></script>
<script src="javascript/noscia/addNoScia.js"></script>
<script src="javascript/noscia/codiceFiscale.js"></script>

<script type="text/javascript" src="dwr/interface/DWRnoscia.js"> </script>
<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script src="javascript/gestioneanagrafica/add.js"></script>

<link rel="stylesheet" href="javascript/noscia/css/awesomplete.css" />
<script src="javascript/noscia/js/awesomplete.js"></script>
<script src="javascript/noscia/fix_awesomplete.js"></script>

<script>
resetStar();
</script>

<table class="trails" cellspacing="0">
<tr>
	<td>
		<a href="GestionePraticheAction.do?command=HomeGins">PRATICHE SUAP 2.0</a> >
		<a href="GestionePraticheAction.do?command=ListaPraticheApicoltura"> PRATICHE APICOLTURA</a> > INSERISCI STABILIMENTO
	</td>
</tr>
</table>


<input type="hidden" id="id_asl_stab" value="${id_asl_stab}">
<form class="form-horizontal" role="form" method="post" action="GestioneAnagraficaAction.do?command=InsertApicoltura" id="form_inserimento">
<b>TIPO OPERAZIONE: INSERISCI STABILIMENTO</b><br>
<input type="hidden" id="validatelineacheck" value="">

	<div style="border: 1px solid black; background: #BDCFFF">
	<br>
	&nbsp;&nbsp;&nbsp;&nbsp;NUMERO PRATICA: ${numeroPratica} <br>
	&nbsp;&nbsp;&nbsp;&nbsp;TIPO PRATICA: AVVIO DELL'ATTIVITA' <br>
	&nbsp;&nbsp;&nbsp;&nbsp;DATA PEC / DATA SCIA: ${dataPratica} <br>
	&nbsp;&nbsp;&nbsp;&nbsp;COMUNE: ${comuneTesto} <br>
	<br>
	<input type="hidden" id="numeroPratica" name="numeroPratica" value="${numeroPratica}"/>
	<input type="hidden" id="idTipologiaPratica" name="idTipologiaPratica" value="${tipoPratica}"/>
	<input type="hidden" id="idComunePratica" name="idComunePratica" value="${comunePratica}"/>
	<input type="hidden" id="idProvinciaPratica" name="idProvinciaPratica" value="${idprovinciain}"/>
	<input type="hidden" id="id_causale" name="_b_id_causale" value="1"/>
	</div>
	<br/>

	<input type="hidden" id="tipo_linee_attivita" value="1" />
	<input type="hidden" id="tipo_gruppo_utente" value="${User.getId_tipo_gruppo_ruolo()}"/>
	<input type="hidden" id="tipo_linee_attivita_flag_registrabili" value="1" />
	<input type="hidden" id="tipo_linee_attivita_flag_riconosciuti" value="0" />
	<input type="hidden" id="tipo_linee_attivita_flag_apicoltura" value="1" />

<div id="scheda_inserimento_linee_attivita">
	<div id="info_linee_stabilimento" style="display">
		<p><b>Stai inserendo uno stabilimento apicoltura, puoi aggiungere una linea di attività cliccando sul bottone 'aggiungi linea di attività'</b></p>
	</div>
	<div id="info_scheda_stabilimento" style="display: none;">
	</div>
	<br>
	<table class="table details" id="tabella_linee" style="border-collapse: collapse" width="100%" cellpadding="5"> 
		<tr id="tr_attivita_id_sezione">
			<th colspan="2" style="text-align : center">
				<input type="button" id="attivita_id_sezione" value="Aggiungi linea di attività" class="greenBigButton" 
				onclick="gestione_aggiunzione_linee();"
				/>
			</th>
		</tr>
		<input type="hidden" id="id_linee_selezionate" value="">
		<input type="hidden" id="numero_linee" name="numero_linee" value="1">	
		<input type="hidden" id="numero_linee_effettivo" name="numero_linee_effettivo" value="0">	
	
	</table>
	<br>
	
	<iframe scrolling="no" src="" id="dettaglioTemplate0" style="width: 100%; height: 100%; border: none; display: none; "></iframe>

	<center>
	<input type="button" class="" value="PROSEGUI" style="width: 250px; display: none;" 
		id="button_conferma_linee_ok" onclick="visualizza_scheda_inserimento();" />	
		
	<input type="button" class="yellowBigButton" style="width: 250px;" value="ANNULLA" 
		id="button_conferma_linee_ko"
		onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestionePraticheAction.do?command=ListaPraticheApicoltura'"/>
	</center>

</div>

<div id="scheda_inserimento_osa" style="display: none">
<b>ASL: <#if '${asl_stab_da_inserire}' == '-1'>
			TUTTE LE ASL
		<#else>
		 	${asl_stab_da_inserire}
		</#if>
</b>
	<input type="button" id="pulisciform" name="pulisciform" value="pulisci schermata" style="display: none"
		onclick="" />

	<div id="scheda_anagrafica_caricata">
	</div>
	
	<br><br>
	<center>
	
	<button type="button" class="yellowBigButton" style="width: 250px; margin-right: 10%" onClick="return validateForm()">SALVA</button>
	
	<input type="button" class="yellowBigButton" style="width: 250px;" value="ANNULLA" 				
		onclick="var link = 'GestioneAnagraficaAction.do?command=Choose&numeroPratica=${numeroPratica}&tipoPratica=${tipoPratica}&dataPratica=${dataPratica}&comunePratica=${comunePratica}&causalePratica=${id_causale}&apicoltura=1';
	    window.location.href=link;"/>
	
	</center>
</div>

</form>
<br><br>

<div id='dialogimprese'/>
<div id='popuplineeattivita'/>
<script src="javascript/noscia/widget.js"></script>
<script src="javascript/gestioneanagrafica/aggiungiLinea.js"></script>

<script>
function validateForm()
{
	if(document.getElementById('telefono_rapp_legale')){
		var phone_number = document.getElementById('telefono_rapp_legale').value;
		if(!(/^([0-9 \-+]{1,30})$/.test(phone_number)) && phone_number.trim()!= ''){
			alert('Attenzione! numero di telefono non valido (caratteri consentiti: numeri - + )');
			return false;
		}
	}
	
	var form_elements = document.getElementById("form_inserimento");
	for (var i = 0; i < form_elements.elements.length; i++){
		var this_element = form_elements.elements[i];
		if(this_element.hasAttribute('value') || this_element.hasAttribute('required')){
			if(this_element.value.trim() == '' && this_element.hasAttribute('required')){
		    	alert('Attenzione! Controllare di aver inserito tutti i campi obbligatori indicati con * rosso');
		    	return false;
	    	}
		}
	}
	
	var campi = document.getElementById("form_inserimento").getElementsByTagName("input");
	for(var x = 0; x < campi.length; x++){
		
		if(campi[x].id.includes("id_linea_attivita_ml")>0){
		
			var check =  checkCodiceLineaGins(campi[x].value, document.getElementById("partita_iva_impresa").value, "-1", "-1", "-1" );
         	if (document.getElementById("validatelineacheck").value=='false')
          		return false;
		}
	}
	
	var tipo_attivita = document.getElementById('tipo_linee_attivita').value;
	var tipo_impresa = document.getElementById('tipo_impresa').value;
	
	if((tipo_impresa == '17') && document.getElementById("codice_fiscale_impresa").value.trim() == ''){
		alert('ATTENZIONE. Inserire il Codice Fiscale nella sezione dati impresa.');
		return false;
	}
	
	if(tipo_impresa == '11' || tipo_impresa == '17'){
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

function gestione_aggiunzione_linee(){
	if(document.getElementById('numero_linee_effettivo').value == '0')
	{	
		<#-- inserisco gli id linea gia selezionati in value per l elemento con id id_linee_selezionate -->
		document.getElementById('id_linee_selezionate').value = '';
		var id_linee_selezionate = '';
		var numero_linee_selezioante = 0;
		for(var i = 0; i <= document.getElementById('numero_linee').value; i++){	
		
			if(document.getElementById('lineaattivita_'+i+'_id_linea_attivita_ml')){
				numero_linee_selezioante = numero_linee_selezioante +1;
				if(numero_linee_selezioante == 1){
					id_linee_selezionate = document.getElementById('lineaattivita_'+i+'_id_linea_attivita_ml').value;
				} else {
					id_linee_selezionate = id_linee_selezionate + ',' +  document.getElementById('lineaattivita_'+i+'_id_linea_attivita_ml').value;
				}
			}
		}
		document.getElementById('id_linee_selezionate').value = id_linee_selezionate;
		<#-- function aggiungi_linea() definita nel js javascript/gestioneanagrafica/aggiungiLinea -->
		aggiungi_linea();  
	} else {
		alert('Nota: per stabilimenti apicoltura è possibile aggiungere solo una linea di attività');
	}
		
}

function visualizza_scheda_inserimento(){
		
		if(document.getElementById('numero_linee_effettivo').value == '0'){
			alert('Nessuna linea di attivita\' aggiunta. E\' necessario aggiungere almeno una linea di attivita\' prima di proseguire');
			return false;
		}

		<#-- prima di passare alla scheda anagrafica
		verifico che non vi siano linee duplicate -->
		for(var x = 0; x <= document.getElementById('numero_linee').value; x++){ 
			for(var y = 0; y <= document.getElementById('numero_linee').value; y++){
				if(document.getElementById('lineaattivita_'+x+'_id_linea_attivita_ml') && document.getElementById('lineaattivita_'+y+'_id_linea_attivita_ml')){
					
					var linea_x = document.getElementById('lineaattivita_'+x+'_id_linea_attivita_ml');
					var linea_y = document.getElementById('lineaattivita_'+y+'_id_linea_attivita_ml');
					
					if(x != y && linea_x.value == linea_y.value){
						alert('Attenzione, non è possibile proseguire poichè è stata aggiunta la stessa linea di attività più volte: rimuovere i duplicati prima di proseguire');
						return false;
					}
				}
			}
		}
		
		<#-- prima di passare alla scheda anagrafica
		rimuovo dalla gui tasto elimina linee per ogni linea attivita selezionata -->
		for(var i = 0; i <= document.getElementById('numero_linee').value; i++){	
			if(document.getElementById('button_elimina_lineaattivita_'+i)){
				document.getElementById('button_elimina_lineaattivita_'+i).style='display: none';
			}
		}

		document.getElementById('button_conferma_linee_ok').removeAttribute('class');
		document.getElementById('button_conferma_linee_ko').removeAttribute('class');
		document.getElementById('tr_attivita_id_sezione').innerHTML = '<th colspan=\'2\' >LINEE DI ATTIVITA\'</th>';
		document.getElementById('button_conferma_linee_ok').style='display: none';
		document.getElementById('button_conferma_linee_ko').style='display: none';
		
		document.getElementById('info_scheda_stabilimento').style='display: ';
		document.getElementById('info_linee_stabilimento').style='display: none';
		
		var id_linea_da_passare = '';
		for(var i = 0; i <= document.getElementById('numero_linee').value; i++){
			if(document.getElementById('lineaattivita_'+i+'_id_linea_attivita_ml')){
				id_linea_da_passare = document.getElementById('lineaattivita_'+i+'_id_linea_attivita_ml').value;
			}
		}
		
		document.getElementById('dettaglioTemplate0').src='GestioneAnagraficaAction.do?command=ScegliTemplate&id_linea_ml=' + id_linea_da_passare;
		
		document.getElementById('dettaglioTemplate0').onload = function(){
				var tabella_da_clonare = document.getElementById('dettaglioTemplate0').contentDocument.getElementById('tabella_scheda_anagrafica');
				$('#dettaglioTemplate0').remove();
				
				document.getElementById('scheda_anagrafica_caricata').appendChild(tabella_da_clonare); 
				document.getElementById('scheda_inserimento_osa').style='display: ';
				gestione_tipo_scheda_inserimento(); 
			};
			
}

function gestione_tipo_scheda_inserimento()
{
	if(document.getElementById('data_nascita_rapp_legale')){
	
		document.getElementById('data_nascita_rapp_legale').removeAttribute("class");
		$( '#data_nascita_rapp_legale' ).datepicker({
			  dateFormat: 'dd-mm-yy',
			  changeMonth: true,
			  changeYear: true,
			  yearRange: '-100:+3',					
			  maxDate: '-18Y', 
			  onSelect: function(){
				document.getElementById('cf_rapp_legale').value = '';
				document.getElementById('calcola_cf_rapp_legale').removeAttribute('disabled');
				if(document.getElementById('id_rapp_legale_recuperato') != null){
					document.getElementById('id_rapp_legale_recuperato').value = '';
				}
				if(document.getElementById('id_impresa_recuperata') != null){
					document.getElementById('id_impresa_recuperata').value = '';
				}
			  },
			  dayNamesMin : [ 'do', 'lu', 'ma', 'me', 'gi', 've', 'sa' ],
			  monthNamesShort :['Gennaio','Febbraio','Marzo','Aprile','Maggio','Giugno', 'Luglio','Agosto','Settembre','Ottobre','Novembre','Dicembre'],
			  beforeShow: function(input, inst) {
		             setTimeout(function () {
		                        var offsets = $('#data_nascita_rapp_legale').offset();
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
		                        var offsets = $('#data_nascita_rapp_legale').offset();
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
	 

	if(document.getElementById('tr_codice_fiscale_impresa')){
	
		document.getElementById('tr_codice_fiscale_impresa').innerHTML = '<td class="formLabel">CODICE FISCALE</td>' + 
					'<td>' +
						'<input type="text" id="codice_fiscale_impresa" name="_b_codice_fiscale" value="" maxlength="16" pattern="[A-Za-z0-9]{11,}" title="inserire almeno 11 caratteri alfanumerici">' + 
						'<input id="ugualecodicefiscale" type="checkbox"/> UGUALE ALLA P.IVA' +
					'</td>'; 
	
		document.getElementById('ugualecodicefiscale').onclick = function(){
			if(!document.getElementById('codice_fiscale_impresa').readOnly){
				if(this.checked){
					document.getElementById('codice_fiscale_impresa').value=document.getElementById('partita_iva_impresa').value;
				}else{ 
					document.getElementById('codice_fiscale_impresa').value='';
				}
			} else { document.getElementById('ugualecodicefiscale').checked = false; }
		};
		
		document.getElementById('codice_fiscale_impresa').onchange = function(){
			document.getElementById('ugualecodicefiscale').checked = false;
		};
								
		document.getElementById('partita_iva_impresa').onchange = function(){
			if(document.getElementById('ugualecodicefiscale').checked){
				document.getElementById('ugualecodicefiscale').checked = false; 
				document.getElementById('codice_fiscale_impresa').value = '';
			}
		}; 
	}
	
	if(document.getElementById('tr_numero_registrazione_stabilimento')){
		document.getElementById('tr_numero_registrazione_stabilimento').style='display: none';
	}
	
	if(document.getElementById('asl_stabilimento')){
		<#if '${asl_stab_da_inserire}' == '-1'>
		<#else>
		 	document.getElementById('asl_stabilimento').value = '${asl_stab_da_inserire}';
		</#if>
	}
	
	resetStar();
	
}


</script>
