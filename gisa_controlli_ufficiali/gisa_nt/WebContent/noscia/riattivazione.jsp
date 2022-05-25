<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.variazionestati.base.*"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope = "request"/>
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaOperazioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaOperazioniConsentite" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<script src="javascript/vendor/moment.min.js"></script>

<script type="text/javascript" src="dwr/interface/SuapDwr.js"> </script>

<script  src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script src="javascript/gestioneanagrafica/add.js"></script>


<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OpuStab.do?command=SearchForm">ANAGRAFICA STABILIMENTI</a> > 
			<a href="OpuStab.do?command=Details&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>"> Scheda </a> > RIATTIVAZIONE
		</td>
	</tr>
</table>

<form class="form-horizontal" role="form" method="post" action="GisaNoScia.do?command=Riattivazione" onsubmit="return validateForm();">
<b>MODIFICA SCHEDA: RIATTIVAZIONE</b><br>
<input type="hidden" id="stabId" name="stabId" value="<%=StabilimentoDettaglio.getIdStabilimento()%>"/>
<input type="hidden" id="altId" name="altId" value="<%=StabilimentoDettaglio.getAltId()%>"/>

<table class="details" id="tabella_stab" cellspacing="0" border="0" width="100%" cellpadding="4">
<tbody>
	
	

<table class="details" id="tabella_linee" cellspacing="0" border="0" width="100%" cellpadding="4"> 
<col width="40%">
<tr>
		<td class="formLabel">Data inizio attivita'</td>
		<td>&nbsp;<%=StabilimentoDettaglio.getDataInizioAttivitaString().replaceAll("/", "-") %>
		<input type="hidden" id="data_inizio_stab" name="data_inizio_stab" value="<%=StabilimentoDettaglio.getDataInizioAttivitaString().replaceAll("/", "-") %>">
		</td>
	</tr>
	
<tr>
		<th colspan="5">RIATTIVAZIONE LINEE</th>
</tr>


<% 	
ArrayList<LineaVariazione> listaLinee = (ArrayList<LineaVariazione>) request.getAttribute("listaLinee");
%>

<% int linea_inserita = 0;
LineaVariazione lineaVariazione = null;
for(int i = 0; i < StabilimentoDettaglio.getListaLineeProduttive().size(); i++ ){ %>
 		<% LineaProduttiva lp = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i); %>
 		
 		<% for(int j=0; j < listaLinee.size(); j++){
 			lineaVariazione = (LineaVariazione) listaLinee.get(i);
 			if (lineaVariazione.getLinea().getId_rel_stab_lp()== lp.getId_rel_stab_lp())
 				break;
 			else
 				lineaVariazione = null;
 		}
 		%>
 		
 		<%if(lp.getStato() == 2){%>
  			<tr id="tr_linea_<%=linea_inserita%>"> 			
	  			<td>
	  				<div id="div_linee_<%=linea_inserita%>" style="padding: 5px;" >
	  				Num. registrazione: <b><%=lp.getNumeroRegistrazione() %></b><br>
	  				<% String[] desc = lp.getDescrizione_linea_attivita().split("->"); %>
	  				<%=desc[0] %>-><br> <%=desc[1] %>-><br> <%=desc[2] %><br>
	  				<i>Stato: <%=ListaStati.getSelectedValue(lp.getStato()) %></i>
	  				
	  				<% if (lineaVariazione.getIdOperazioneOrigine()>0){ %>
	  				<br/> (<i><%=ListaOperazioni.getSelectedValue(lineaVariazione.getIdOperazioneOrigine()) %></i>)
	  				<%} %>
	  				
	  				</div>
	  				<input type="hidden" id="linea_<%=linea_inserita%>" name="linea_<%=linea_inserita%>" value="<%=lp.getDescrizione_linea_attivita() %>" >
	  				<input type="hidden" id="id_lp_<%=linea_inserita%>" name="id_lp_<%=linea_inserita%>" value="<%=lp.getId()%>">
	  				<input type="hidden" id="id_rel_stab_lp_<%=linea_inserita%>" name="id_rel_stab_lp_<%=linea_inserita%>" value="<%=lp.getId_rel_stab_lp()%>">
	  				<input type="hidden" id="codice_lp_<%=linea_inserita%>" name="codice_lp_<%=linea_inserita%>" value="<%=lp.getCodice()%>">
	  				<input type="hidden" id="stato_lp_<%=linea_inserita%>" name="stato_lp_<%=linea_inserita%>" value="<%=lp.getStato()%>">
	  				<input type="hidden" id="data_inizio_lp_<%=linea_inserita%>" name="data_inizio_lp_<%=linea_inserita%>" 
	  					value="<%=lp.getDataInizioString().replaceAll("/", "-") %>">
	  			</td>	
	  			<td align="center">
	  				<input type="checkbox" id="riattiva_linea_<%=linea_inserita%>" name="riattiva_linea_<%=linea_inserita%>" 
	  					onclick="gestisciRiattivazioneLinea(<%=linea_inserita%>, this)" />
	  			</td>
	  			
	  			<td id="td_riattiva_linea_tipo_<%=linea_inserita%>" align="center" style="display: none">
	  			<%= ListaOperazioniConsentite.getHtmlSelect("riattiva_linea_tipo_"+linea_inserita, 3)%>
	  			<input type="text" readonly id="riattiva_linea_cu_<%=linea_inserita%>" name="riattiva_linea_cu_<%=linea_inserita%>" value="" placeholder="ID CONTROLLO"/>
	  			
	  			<br/>
				<% for (int j=0; j<lineaVariazione.getListaCU().size(); j++){
				int idcu = lineaVariazione.getListaCU().get(j);%>
				<a href="#" onClick="selezionaCu('<%=linea_inserita%>', '<%=idcu %>'); return false"><%=idcu %></a> 
				<% } %>
	  			
	  			</td>
	  			
	  			<td id="td_data_riattivazione_<%=linea_inserita%>" align="center" style="display: none">
	  				<input placeholder="DATA RIATTIVAZIONE" type="text" id="data_riattivazione_<%=linea_inserita%>" 
	  					name="data_riattivazione_<%=linea_inserita%>" autocomplete="off" onkeydown="return false" size="15" style="text-align:center;"><br>
	  			</td>
	  			<td id="td_riattiva_linea_note_<%=linea_inserita%>" align="center" style="display: none">
	  				<textarea placeholder="note aggiuntive" id="riattiva_linea_note_<%=linea_inserita%>" name="riattiva_linea_note_<%=linea_inserita%>"></textarea>
	  			</td>
  			</tr>
  		<%linea_inserita++;
  		} %>
	<%} %> 
	<input type="hidden" id="numero_linee" name="numero_linee" value="<%=linea_inserita%>">
	
</table>

<br><br>
<center>
<button style="width:250px" type="button" class="btn btn-primary" style="margin: auto;" onClick="loadModalWindowCustom('Attendere Prego...'); 
						window.location.href='OpuStab.do?command=Details&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>'">Annulla</button>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<button style="width:250px" type="submit" class="btn btn-primary" style="margin: auto;">Salva</button>
</center>
</form>
<br><br>

<script>

function validateForm(){
	
	loadModalWindowCustom('Attendere Prego...');
	return true;
}

popup_date('data_riattivazione', document.getElementById('data_inizio_stab').value, '0');

for(var i=0; i < document.getElementById('numero_linee').value; i++){
	popup_date('data_riattivazione_' + i, document.getElementById('data_inizio_lp_' + i).value, '0');
}

function popup_date(elemento_html_data, min_data, max_data){
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


function gestisciRiattivazioneLinea(numero_linea, campo_check){
	if(campo_check.checked){
		document.getElementById('td_data_riattivazione_' + numero_linea).style.display = '';
		document.getElementById('td_riattiva_linea_note_' + numero_linea).style.display = '';
		document.getElementById('td_riattiva_linea_tipo_' + numero_linea).style.display = '';
		document.getElementById('data_riattivazione_' + numero_linea).setAttribute('required', '');
	}else{
		document.getElementById('td_data_riattivazione_' + numero_linea).style.display = 'none';
		document.getElementById('td_riattiva_linea_note_' + numero_linea).style.display = 'none';
		document.getElementById('riattiva_linea_note_' + numero_linea).value = '';
		document.getElementById('td_riattiva_linea_tipo_' + numero_linea).style.display = 'none';
		document.getElementById('riattiva_linea_cu_' + numero_linea).value = '';
		document.getElementById('riattiva_linea_tipo_' + numero_linea).value = '3';
		document.getElementById('data_riattivazione_' + numero_linea).removeAttribute('required');
		document.getElementById('data_riattivazione_' + numero_linea).value = '';
	}
}

function selezionaCu(indice, idcu){
	document.getElementById("riattiva_linea_cu_"+indice).value=idcu;
}
function cancellaCu(indice){
	document.getElementById("riattiva_linea_cu_"+indice).value='';
}

</script>
