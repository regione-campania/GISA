<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope = "request"/>


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
			<a href="GestioneAnagraficaAction.do?command=Details&altId=${altId}">SCHEDA</a> > AMPLIAMENTO FISICO DEL LOCALE
		</td>
	</tr>
</table>

<form class="form-horizontal" role="form" method="post" action="GestioneAnagraficaAction.do?command=AmpliamentoFisico" onsubmit="return validateForm();">
<b>MODIFICA SCHEDA: AMPLIAMENTO FISICO DEL LOCALE</b><br>
<input type="hidden" id="stabId" name="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>
<input type="hidden" id="altId" name="altId" value="<%=StabilimentoDettaglio.getAltId()%>"/>
<input type="hidden" id="id_asl_stab" value="<%=StabilimentoDettaglio.getSedeOperativa().getIdAsl()%>">
<input type="hidden" id="tipo_linee_attivita" value="<%=StabilimentoDettaglio.getTipoAttivita()%>" />


<div id="dati_pratica" style="border: 1px solid black; background: #BDCFFF">
<br>
&nbsp;&nbsp;&nbsp;&nbsp;NUMERO PRATICA: ${numeroPratica} <br>
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
<table class="details" cellspacing="0" border="0" width="100%" cellpadding="4">
<tbody>
	<tr>
		<th colspan="2">STABILIMENTO</th>
	</tr>
	<tr>
		<td class="formLabel">Ragione Sociale Impresa</td>
		<td><%=StabilimentoDettaglio.getOperatore().getRagioneSociale()%></td>
	</tr>
	<tr>
		<td class="formLabel">Numero registrazione</td>
		<td><%=StabilimentoDettaglio.getNumero_registrazione()%></td>
	</tr>
	
	<tr>
		<td class="formLabel">ASL</td>
		<td><%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneAsl()%> </td>
	</tr>

	<tr>
		<td class="formLabel">INDIRIZZO</td>
		<td>
			<%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneToponimo()%>&nbsp;&nbsp;
			<%=StabilimentoDettaglio.getSedeOperativa().getVia() %>&nbsp;&nbsp;
			<%=StabilimentoDettaglio.getSedeOperativa().getCivico() %>&nbsp;&nbsp;
			<%=StabilimentoDettaglio.getSedeOperativa().getCap() %>&nbsp;&nbsp;
		 	<%=StabilimentoDettaglio.getSedeOperativa().getComuneTesto() %>&nbsp;&nbsp;
		 	<%=StabilimentoDettaglio.getSedeOperativa().getDescrizione_provincia() %>
		 </td>
		 <input type="hidden" id="data_inizio_stab" name="data_inizio_stab" value="<%=StabilimentoDettaglio.getDataInizioAttivitaString().replaceAll("/", "-") %>">
		 <input type="hidden" id="civico_old" value="<%=StabilimentoDettaglio.getSedeOperativa().getCivico() %>">
	</tr>
	
	<tr>
		<th colspan="2">AGGIUNTA NUOVO CIVICO</th>
	</tr>
	<tr>
		<td class="formLabel"><p>n° civico aggiuntivo</p></td>
		<td>
			<input placeholder="CIVICO AGGIUNTIVO" type="text" id="civico_aggiuntivo" name="_b_civico_aggiuntivo" 
				   autocomplete="off" size="15" style="text-align:center;" required pattern="^[A-Za-z0-9\-\s,\/]*$" />
		</td>
	</tr>
	<tr>
		<td class="formLabel"> <p>DATA</p> </td>
		<td>
			<input placeholder="DATA OPERAZIONE" type="text" id="data_operazione" name="_b_data_operazione" autocomplete="off" onkeydown="return false" size="15" style="text-align:center;" required />
		</td>
	</tr>
			
	
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
	
	if(document.getElementById("civico_aggiuntivo").value.trim()==''){
		document.getElementById("civico_aggiuntivo").value = '';
		return false;
	}
	
	var civico_aggiunto = document.getElementById("civico_aggiuntivo").value.trim();
	var civico_old = document.getElementById("civico_old").value.trim();
	
	if(civico_old != ''){
		civico_old = civico_old.replace("/", " ");
		if( new RegExp("\\b"+civico_aggiunto+"\\b").test(civico_old)){
			alert('Attenzione, civico inserito risulta gia\' presente ');
			return false;
		}
	}
	
	loadModalWindowCustom('Attendere Prego...');
	return true;
	
}

if(document.getElementById('tipo_linee_attivita').value == '2'){
	alert('Attenzione: Operazione non consentita per stabilimenti senza sede fissa!');
	loadModalWindowCustom('Attendere Prego...');
	window.location.href='GestioneAnagraficaAction.do?command=Details&altId='+ document.getElementById('altId').value;
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
	document.getElementById('operazione_scheda').style='display:'
}

popup_date_op('data_operazione', document.getElementById('data_inizio_stab').value, '0');

function popup_date_op(elemento_html_data, min_data, max_data){
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



</script>