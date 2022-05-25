<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@ page import="java.util.*,java.text.SimpleDateFormat,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope = "request"/>
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<script src="javascript/vendor/moment.min.js"></script>
<script src="javascript/noscia/addNoScia.js"></script>
<script type="text/javascript" src="dwr/interface/DWRnoscia.js"> </script>
<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>

<script  src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script src="javascript/gestioneanagrafica/add.js"></script>


<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OpuStab.do?command=SearchForm">ANAGRAFICA STABILIMENTI</a> > 
			<a href="GestioneAnagraficaAction.do?command=Details&altId=${altId}"> SCHEDA</a> > 
				TRASFORMAZIONE
		</td>
	</tr>
</table>

<form class="form-horizontal" role="form" method="post" action="GestioneAnagraficaAction.do?command=Trasformazione" onsubmit="return validateForm();">
<b>MODIFICA SCHEDA: TRASFORMAZIONE </b><br>
<input type="hidden" id="stabId" name="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>
<input type="hidden" id="altId" name="altId" value="<%=StabilimentoDettaglio.getAltId()%>"/>
<input type="hidden" id="numero_linee_attuali" name="numero_linee_attuali" value="<%=StabilimentoDettaglio.getListaLineeProduttive().size()%>"/>
<input type="hidden" id="tipo_linee_attivita" value="<%=StabilimentoDettaglio.getTipoAttivita()%>" />
<div id="dati_pratica" style="border: 1px solid black; background: #BDCFFF">
<br>
&nbsp;&nbsp;&nbsp;&nbsp;NUMERO PRATICA: ${numeroPratica} <br>
&nbsp;&nbsp;&nbsp;&nbsp;TIPO PRATICA: TRASFORMAZIONE (SOLO PANIFICI) <br>
&nbsp;&nbsp;&nbsp;&nbsp;DATA PEC / DATA SCIA: ${dataPratica} <br>
<br>
<input type="hidden" id="numeroPratica" name="numeroPratica" value="${numeroPratica}"/>
<input type="hidden" id="idTipologiaPratica" name="idTipologiaPratica" value="${tipoPratica}"/>
<input type="hidden" id="idComunePratica" name="idComunePratica" value="${comunePratica}"/>
<input type="hidden" id="causaleRicevuta" value="${id_causale}">
</div>
<br/>

<jsp:include page="include/sezione_gestione_causale.jsp"/>

<div id="operazione_scheda" style="display: none">

<% 
LineaProduttiva lp = new LineaProduttiva(); 
for(int i = 0; i < StabilimentoDettaglio.getListaLineeProduttive().size(); i++ ) { 
	LineaProduttiva lp_isima = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i);
	if(lp_isima.getCodice().equalsIgnoreCase("MS.020-MS.020.200-852IT3A101") && lp_isima.getStato() != 4) { 
		lp = lp_isima;
	}
}
%>


<table class="details" id="tabella_linee" cellspacing="0" border="0" width="100%" cellpadding="4">
<tbody>
	<tr>
		<th colspan="2">Riepilogo</th>
	</tr>
	<tr>
		<td class="formLabel">Numero registrazione</td>
		<td><%=StabilimentoDettaglio.getNumero_registrazione()%></td>
	</tr>
	<tr>
		<td class="formLabel">Ragione Sociale Impresa</td>
		<td><%=StabilimentoDettaglio.getOperatore().getRagioneSociale()%></td>
	</tr>
	<tr>
		<td class="formLabel">Data inizio attivita'</td>
		<td><%=StabilimentoDettaglio.getDataInizioAttivitaString().replaceAll("/", "-")%></td>
	</tr>
	<tr>
		<td class="formLabel">LINEA DI ATTIVITA' DA TRASFORMARE</td>
		<td>
			<table class="details" cellspacing="0" border="0" width="100%" cellpadding="4"> 
		  		<tr>
		  			<td style="width:10%"><%=lp.getNumeroRegistrazione() %></td>
		  			<td>
		  				<%=lp.getDescrizione_linea_attivita().replaceAll("->", "-><br>") %>
		  				<br><br>
		  				<b>DATA INIZIO</b>: <%=lp.getDataInizioString() %>
		  				<br>
						   	<%if(lp.getStato() == 0) { %>
 								<span style='background-color:lime;'>STATO: <b>ATTIVA</b></span>
 						   	<% } else if(lp.getStato() == 2) { %>
 						   		<span style='background-color:orange;'>STATO: <b>SOSPESA</b></span>
						   	<% } else if(lp.getStato() == 4) { %>
 						   		<span style='background-color:red;'>STATO: <b>CESSATA</b></span>  		
 						   	<% } %>			  				
		  				<input type="hidden" id="id_lp_old" name="_b_id_lp_old" value="<%=lp.getId()%>">
		  				<input type="hidden" id="id_rel_lp_old" name="_b_id_rel_lp_old" value="<%=lp.getId_rel_stab_lp()%>">
		  				<input type="hidden" id="codice_lp_old" name="_b_codice_lp_old" value="<%=lp.getCodice()%>">
		  				<input type="hidden" id="stato_lp_old" name="_b_stato_lp_old" value="<%=lp.getStato()%>">
		  				<input type="hidden" id="data_inizio_lp_old" name="_b_data_inizio_lp_old" 
		  						value="<%=lp.getDataInizioString().replaceAll("/", "-") %>">
		  			</td>	
		  		</tr> 
			</table>
		</td>
	</tr>
	<tr>
		<td class="formLabel">NUOVA LINEA DI ATTIVITA'</td>
		<td>
			<table class="details" cellspacing="0" border="0" width="100%" cellpadding="4">
				
				<tr id="tr_macroarea">
					<td style="width:10%"> <p>MACROAREA</p> </td>
					<td>
						<select id="macroarea" disabled>
							<option value="" selected="selected">
								PRODOTTI DA FORNO E DI PASTICCERIA GELATI E PIATTI PRONTI PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO
							</option>
						</select>
					</td>
				</tr>
						
				<tr id="tr_aggregazione">
					<td style="width:10%"> <p>AGGREGAZIONE</p> </td>
						<td>
							<select id="aggregazione" disabled>
								<option value="" selected="selected">
									PRODUZIONE DI PANE, PIZZA E PRODOTTI DA FORNO E DI PASTICCERIA - FRESCHI E SECCHI
								</option>
							</select>
						</td>
				</tr>
					
				<tr id="tr_linea_attivita">
					<td style="width:10%"> <p>LINEA ATTIVITA'</p> </td>
					<td>
						<select id="linea_attivita" name="_b_linea_attivita">
							<option value="" selected="selected">SELEZIONA LINEA ATTIVITA'</option>
						</select>
					</td>
				</tr>
					<td style="width:10%"> <p>LINEA SELEZIONATA</p> </td>
					<td id="desc_linea_selezionata"></td>
				<tr>
					
				</tr>
				
				<tr>
					<td style="width:10%"> <p>TIPO CARATTERE</p> </td>
					<td>
						<select id="tipo_carattere" name="_b_tipo_carattere">
							<option value="1">PERMANENTE</option>
							<option value="2">TEMPORANEA</option>
						</select>
					</td>
				</tr>
						
				<tr id="tr_data_inizio">
					<td style="width:10%"> <p>DATA INIZIO ATTIVITA</p> </td>
					<td>
						<input placeholder="DATA INIZIO ATTIVITA" type="text" id="data_inizio" name="_b_data_inizio" autocomplete="off" required="true" size="15" onkeydown="return false" style="text-align:center;">
					</td>
				 </tr>
						 
				 <tr id="tr_data_fine">
					 <td style="width:10%"> <p>DATA FINE ATTIVITA</p> </td>
					<td>
						<input placeholder="DATA FINE ATTIVITA" type="text" id="data_fine" name="_b_data_fine" autocomplete="off" size="15" onkeydown="return false" style="text-align:center;">
					</td>
				 </tr>
				
				<tr id="tr_cun_linea_pregressa">
					 <td style="width:10%"> <p>CUN</p> </td>
					<td>
						<input placeholder="CUN" type="text" id="cun_linea" name="_b_cun_linea" autocomplete="off" value="" maxlength="20">
					</td>
				 </tr>	
			</table>
		</td>
	</tr>
	
	<input type="hidden" id="current_day" value="<%=(new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date()))%>" >
	
</tbody>
</table>
<br><br>
<center>

<button type="submit" class="yellowBigButton" style="width: 250px;">Salva</button>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<button type="button" id="torna" class="yellowBigButton" style="width: 250px;"
	onClick="loadModalWindowCustom('Attendere Prego...'); window.location.href='GestionePraticheAction.do?command=ListaPraticheStabilimenti'">Annulla</button>

</center>
</div>
</form>
<br><br>

<script>

function validateForm(){
	
	if(document.getElementById('linea_attivita').value == ''){
		alert('Attenzione! Selezionare una linea');
		return false;
	}
	
	if(document.getElementById('tipo_carattere').value == '2')
	{
	
		var data_iniziale = document.getElementById('data_inizio').value;
		var data_finale = document.getElementById('data_fine').value;
		
		if(data_finale == ''){
			alert('Attenzione! per attivita temporanea bisogna inserire la data fine attivita');
			return false;
		}

		var arr1 = data_iniziale.split("-");
		var arr2 = data_finale.split("-");

		var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
		var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);

		var r1 = d1.getTime();
		var r2 = d2.getTime();
		var diff = r2 - r1;

		if(diff < 0){
			alert('La data inizio attivita non puo essere successiva alla data fine attivita');
			return false;
		}
	}
	
	var url = "CunRichiesto.do?command=Search&codiceLinea=" + document.getElementById('linea_attivita').value;
	var cun_richiesto = false;
	var request = $.ajax({
		url : url,
		async: false,
		dataType : "json"
	});

	request.done(function(result) {
		if(result.cun_obbligatorio == '1'){
			if(trim(document.getElementById('cun_linea').value) == ''){
				
				cun_richiesto = true;
			}
		}
	});
	request.fail(function(jqXHR, textStatus) {
		console.log('Error');
		
	});
	
	if(cun_richiesto){
		alert('Attenzione! La linea selezionata richiede obbligatoriamente il cun');
		return false;
	}
	
	if(!verificaEsistenzaCun(document.getElementById('cun_linea'))){
		return false;
	}
	
	loadModalWindowCustom('Attendere Prego...');
	return true;
}

var tipoattivita = document.getElementById('tipo_linee_attivita').value;
var idgruppoutente = <%=User.getId_tipo_gruppo_ruolo()%>;

var json_flags = '{"flagFisso": ' + (tipoattivita==1 ? 'true' : 'false') + ', "flagMobile" : ' + (tipoattivita==2 ? 'true' : 'false') + ', "flagNoScia" : false, "flagRegistrabili" : true, "flagSintesis" : false, "flagVisibiitaAsl": ' + (idgruppoutente==11 ? 'true' : 'false') + ', "flagVisibilitaRegione" : ' + (idgruppoutente==15 ? 'true' : 'false') + '}';

var id_linee_esistenti = document.getElementById('id_lp_old').value;

$('#linea_attivita').prop("disabled",true);
$('#data_fine').prop("disabled",true);

popup_date_trasformazione_linea('data_inizio', document.getElementById('data_inizio_lp_old').value, '0');
popup_date_trasformazione_linea('data_fine', document.getElementById('data_inizio_lp_old').value, '+3Y');
$('#tipo_carattere').change(function(){
	if(document.getElementById('tipo_carattere').value == '2'){
		$('#data_fine').val(null);
		$('#data_fine').prop("disabled",false);
	} else {
		$('#data_fine').val(null);
		$('#data_fine').prop("disabled",true);
	}
});

$('#linea_attivita').children().remove();
$('#linea_attivita').append('<option value="" selected="selected">SELEZIONA LINEA ATTIVITA</option>');
$('#linea_attivita').prop("disabled",false);
popola_select_linea('GestioneAnagraficaGetDatiLinea.do?command=Search&tiporichiesta=lineetrasformazione&id_linee_selezionate='+id_linee_esistenti, 'linea_attivita');
		
$('#linea_attivita').change(function(){
	if(document.getElementById('linea_attivita').value != ''){
		document.getElementById('desc_linea_selezionata').innerHTML = '<b>' +
			document.getElementById('macroarea').options[document.getElementById('macroarea').selectedIndex].innerHTML + '-><br>' +
			document.getElementById('aggregazione').options[document.getElementById('aggregazione').selectedIndex].innerHTML + '-><br>' +
			document.getElementById('linea_attivita').options[document.getElementById('linea_attivita').selectedIndex].innerHTML + '</b>';
	}else{
		document.getElementById('desc_linea_selezionata').innerHTML = '';
	}
});

function popola_select_linea(urlservice, id_elemento){
	
	
 	$.ajax({  	    
       url: urlservice,
       dataType: "text",
       async:false,
       success: function(dati) { 	 
	   	    var obj;
	   	    try {
				console.log(dati);
	       		obj = JSON.parse(dati);
			} catch (e) {
				alert(e instanceof SyntaxError); // true
				
				return false;
			}
			
			obj = JSON.parse(dati);	  	
		  	for (var i = 0; i < obj.length; i++) {
		  		$('#'+id_elemento).append('<option value="'+obj[i].code+'">'+obj[i].description+'</option>');
		  	}		  	
       },
       fail: function(xhr, textStatus, errorThrown){
       	alert('request failed');
      	}
         
 	});
 	
}

function popup_date_trasformazione_linea(elemento_html_data, min_data, max_data){
	$( '#' + elemento_html_data ).datepicker({
		  dateFormat: 'dd-mm-yy',
		  changeMonth: true,
		  changeYear: true,
		  yearRange: '-100:+3',
		  minDate: min_data,
		  maxDate: max_data,
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

function trim(str) {
    try {
        if (str && typeof(str) == 'string') {
            return str.replace(/^\s*|\s*$/g, "");
        } else {
            return '';
        }
    } catch (e) {
        return str;
    }
}


if(document.getElementById('causaleRicevuta').value != '1'){
	document.getElementById('dati_pratica').style.display = 'none';
	document.getElementById('torna').onclick = function(){
		loadModalWindowCustom('Attendere Prego...');
		window.location.href='GestioneAnagraficaAction.do?command=Details&altId='+ document.getElementById('altId').value;
	};
}else{
	document.getElementById('id_causale').value = "1";
	document.getElementById('specifica_causale').style='display: none';
	document.getElementById('operazione_scheda').style='display:';
}


</script>
