<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <!--  CAMPIONI_ESITO_VIEW_1 : TUTTO MODIFICABILE -->
 
 
 
 <%@page import="org.aspcfs.modules.campioni.base.Analita"%>

 <!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<script>
function checkFormEsiti(){
	var checkEsiti =true;
	var msg = "Impossibile salvare gli esiti campione. Controllare i seguenti campi: \n";
	var i = 1;
	
	var codice = document.getElementById('cause');
	var dataAccettazione = document.getElementById('dataAccettazione');
	var descrizione = document.getElementById('esito_note_esame');
	var dataRisultato = document.getElementById('dataRisultato');
	
// 	if (codice!=null && codice.value==''){
// 		checkEsiti = false;
// 		msg += "Codice accettazione \n";
// 	}
// 	if (dataAccettazione!=null && dataAccettazione.value==''){
// 		checkEsiti = false;
// 		msg += "Data accettazione \n";
// 	}




	if ( window.location.pathname.indexOf("AcqueRete")>0 && descrizione!=null && descrizione.value==''){
		checkEsiti = false;
		msg += "Descrizione risultato esame \n";
}
// 	if (dataRisultato!=null && dataRisultato.value==''){
// 		checkEsiti = false;
// 		msg += "Data risultato \n";
// }
		
	while (document.getElementById('idAnalita_'+i)!=null){
		var dataEsito = document.getElementById('esito_data_'+i);
		var esito = document.getElementById('esito_id_'+i);
		var motivo = document.getElementById('esito_motivazione_respingimento_'+i);
		//var punteggio = document.getElementById('esito_punteggio_'+i);
		//var responsabilita = document.getElementById('esito_responsabilita_positivita_'+i);
		
		if (dataEsito!=null && dataEsito.value == ''){
			checkEsiti = false;
			msg += "Data esito "+i+ "\n";
		}
		if (esito!=null && esito.value == -1){
			checkEsiti = false;
			msg += "Esito "+i+ "\n";
		}	
// 		if (motivo!=null && motivo.value == ''){
// 			checkEsiti = false;
// 			msg += "Motivo "+i+ "\n";
// 		}
		i++;
	}
	
	if (checkEsiti!=true)
		alert(msg);
	
	return checkEsiti;
}

function impostaResponsabilita(indice){

	punteggio = document.getElementById('esito_punteggio_'+indice).value;
	if(punteggio==0){
			document.getElementById('esito_responsabilita_positivita_'+indice).value="1";
	}else
		document.getElementById('esito_responsabilita_positivita_'+indice).value="-1";
}


function mostraDescrizioneNonAccettato(indice){
	if(document.getElementById('esito_id_'+indice).value=='4'){
		document.getElementById("esito_motivazione_respingimento_"+indice).style.display="";
		document.getElementById("esito_motivazione_respingimento_"+indice).disabled="";
	}else{
		document.getElementById("esito_motivazione_respingimento_"+indice).style.display="none";
		document.getElementById("esito_motivazione_respingimento_"+indice).disabled="disabled";
		document.getElementById("esito_motivazione_respingimento_"+indice).value="";
	}

}

function mostraSelectNonConforme(indice){
	if(document.getElementById('esito_id_'+indice).value=='1'){
		document.getElementById("esito_nonconforme_nonc_"+indice).style.display="";
		document.getElementById("esito_nonconforme_nonc_"+indice).disabled="";
		document.getElementById("esito_nonconforme_nonc_legenda_"+indice).style.display="";
	}else{
		document.getElementById("esito_nonconforme_nonc_"+indice).style.display="none";
		document.getElementById("esito_nonconforme_nonc_"+indice).disabled="disabled";
		document.getElementById("esito_nonconforme_nonc_legenda_"+indice).style.display="none";
	}

}

function gestioneFlag(indice){
	var allarme = document.getElementById("esito_allarme_rapido_"+indice);
	var segnalazione = document.getElementById("esito_segnalazione_informazioni_"+indice);
	if(allarme.checked==true){
		segnalazione.disabled=true;
	}else{

		if(segnalazione.checked==true){
			allarme.disabled=true;
		}else{
			segnalazione.disabled=false;
			allarme.disabled=false;
		}
	}
}

function nascondiResponsabilita(){
	
// 	if ((document.getElementById('EsitoCampione').value=="2")||(document.getElementById('EsitoCampione').value=="5")){
// 		document.getElementById('campo_responsabilita').style.display="none";
// 	}else{
// 		document.getElementById('campo_responsabilita').style.display="";
//	}
}



</script>

<style type="text/css">
       select option:not(:enabled) { background-color: white; font-weight: bold; color: blue; }
    </style>


  <% int colspan = 10 ;
	 if (TicketDetails.getPdp()!=null && TicketDetails.getPdp().getId()>0)
	 {
		colspan = 11 ;
	 }
	 %>
	 
	  <table cellpadding="4" cellspacing="0" width="100%" class="details">
  	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Esito Laboratorio</dhv:label></strong>
    </th>
	</tr>
	 <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Codice Accettazione</dhv:label>
    </td>
    <%--<td><%= toHtmlValue(TicketDetails.getCause()) %></td>--%>
    <td>
      <input type="text" name="cause" id="cause" value="<%= toHtmlValue(TicketDetails.getCause()) %>" size="20" maxlength="256" />
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Accettazione</dhv:label>
    </td>
    <td>   
    <input readonly type="text" id="dataAccettazione" name="dataAccettazione" size="10" 
		value="<%= (TicketDetails.getDataAccettazione()==null)?(""):(getLongDate(TicketDetails.getDataAccettazione()))%>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataAccettazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>	
    </td>
  </tr>
  
 
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Descrizione Risultato Esame</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td>
            <textarea name="esito_note_esame" id="esito_note_esame" cols="55" rows="8" onkeyup="this.value = this.value.replace(/[`~!@#$%^&*|+\-=?§°ç£<>\{\}\[\]\\\/]/gi, '').replace(/\r?\n/g, '');" onpaste="this.value = this.value.replace(/[`~!@#$%^&*|+\-=?§°ç£<>\{\}\[\]\\\/]/gi, '').replace(/\r?\n/g, '');"><%=(TicketDetails.getEsitoNoteEsame()!=null) ? TicketDetails.getEsitoNoteEsame() : "" %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
        </table>
        </td>
        </tr>
  <!-- NUOVO CAMPO DATA -->
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Risultato</dhv:label>
    </td>
    <td>   
    <input readonly type="text" id="dataRisultato" name="dataRisultato" size="10" 
		value="<%= (TicketDetails.getDataRisultato()==null)?(""):(getLongDate(TicketDetails.getDataRisultato()))%>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataRisultato,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>	
    </td>
   
  </tr>
  
  </table>
  <br>
  <br>
	 
	 
 <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="<%=colspan%>">
      <strong><dhv:label name="">Esito Campione</dhv:label></strong> <input type="button" value="VAI IN SIGLA WEB" onClick="openPopupGisaReport('http://siglaweb.izsmportici.it/SiglaWEB/login.do')"/>
    </th>
	</tr>
	<tr>
	 <th>Analita</th>
	 <th>Data esito</th>
	 <th>Esito</th>
	 <th>Motivo Respingimento</th>
	 
	 <% if (TicketDetails.getURlDettaglio().contains("AcqueRete")) {%>
	 <th>Motivazione non conformita'</th>
	 <%} %>
	 
	 <% if (!TicketDetails.getURlDettaglio().contains("AcqueRete")) {%>
	 <th>Punteggio</th>
	 <th>Responsabilita</th>
	 <th>Allarme Rapido</th>
	 <th>Segnal. Info</th>
	 <%} %>
	  <% if (TicketDetails.getPdp()!=null && TicketDetails.getPdp().getId()>0)
	 {
		 %>
		 <th>
		 Punto di Prelievo</th>
		 <%
	 }
	 %>
	
	 </tr>
	 
	  <input type="hidden" id="numAnaliti" name="numAnaliti" value="<%=TicketDetails.getTipiCampioni().size()%>"/>
	  
 <% int k = 0;
 for(Analita analita : TicketDetails.getTipiCampioni())
 { k++;
	 %>
	 <input type="hidden" id="idAnalita_<%=k %>" name="idAnalita_<%=k %>" value="<%=analita.getIdAnalita()%>"/>
	 <tr>
	 <td><%=analita.getDescrizione() %> &nbsp;</td>
	 <td>
	 
	   <%--<%=toDateasString(analita.getEsito_data()) --%> &nbsp;
       <input readonly type="text" id="esito_data_<%=k %>" name="esito_data_<%=k %>" size="10" value = ""/>
		<a href="#" onClick="cal19.select(document.forms[0].esito_data_<%=k %>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        <%= showAttribute(request, "esito_data") %><FONT color="red">*</FONT> 
	 </td>
	 
	 <td>
	    <%-- <%= toStringSpace(EsitoCampione.getSelectedValue(analita.getEsito_id()))--%> &nbsp;
	     <% EsitoCampione.setJsEvent("onChange=\"javascript:mostraDescrizioneNonAccettato('"+k+"');javascript:nascondiResponsabilita();\""); %> 
	     
	     <% if (!TicketDetails.getURlDettaglio().contains("AcqueRete")) {
	     	 EsitoCampione.setJsEvent("onChange=\"javascript:mostraDescrizioneNonAccettato('"+k+"');javascript:nascondiResponsabilita();\"");
	     }
	        else {
		     	 EsitoCampione.setJsEvent("onChange=\"javascript:mostraSelectNonConforme('"+k+"');javascript:mostraDescrizioneNonAccettato('"+k+"');javascript:nascondiResponsabilita();\"");

	        }
	    %> 
	    
	     
      <%= EsitoCampione.getHtmlSelect("esito_id_"+k,-1) %><FONT color="red">*</FONT>
      <input type="hidden" name="esitoCampione_<%=k %>" value="-1" >
	  
	 </td>
	 <td>
	 <%-- <%=toStringSpace(analita.getEsito_motivazione_respingimento()) --%> &nbsp;
	  <textarea id="esito_motivazione_respingimento_<%=k %>" name="esito_motivazione_respingimento_<%=k %>" cols="55" rows="8" style="display:none" disabled="disabled"></textarea>
	  </td>
	  
	   <% if (TicketDetails.getURlDettaglio().contains("AcqueRete")) {%>
	  <td>
	  <select id = "esito_nonconforme_nonc_<%=k %>" name = "esito_nonconforme_nonc_<%=k %>" style="display:none" disabled multiple>
	  <%if (motiviAcqueNcList!=null){ 
	  	for (int m = 0; m<motiviAcqueNcList.size(); m++) { 
	  	AcqueReteMotiviNc motivo = (AcqueReteMotiviNc)motiviAcqueNcList.get(m);
	  	%>
	  		<option value="<%=motivo.getId()%>" <%=motivo.isCapitolo() ? "disabled= \"disabled\"" : "" %>><%=motivo.getDescription()%></option>
	  <%} }%>
	  </select> <div style="display:none" id="esito_nonconforme_nonc_legenda_<%=k %>">* Parametri con valori consigliati</div>
	 </td>
	 <%} %>
	 
	<% if (!TicketDetails.getURlDettaglio().contains("AcqueRete")) {%>
	 <td>
	 	<%-- <%= analita.getEsito_punteggio() --%> &nbsp;
	 	 <table>
    <tr><td>
     <select name="esito_punteggio_<%=k %>" id="esito_punteggio_<%=k %>" onchange="impostaResponsabilita('<%=k%>')">
   
   
   <option value="0"  >   0</option>
   <option value="1" >   1 </option>
   <option value="7" > 7 </option>
    
    <option value="50"> 50 </option>
   
   </select>
   
    </td>
  </tr></table>
  <td id="campo_responsabilita_<%=k %>">
  &nbsp;
 <%= ResponsabilitaPositivita.getHtmlSelect("esito_responsabilita_positivita_"+k,-1) %>
  </td>
	 </td>
	 <%--<td> <%=toStringSpace(ResponsabilitaPositivita.getSelectedValue(analita.getEsito_responsabilita_positivita()))%> &nbsp; </td> --%>
	 <td>
    
         <input type="checkbox" name="esito_allarme_rapido_<%=k %>" id="esito_allarme_rapido_<%=k %>" size="20" maxlength="256"  onclick="gestioneFlag('<%=k %>')" />
       
     </td>
     <%-- <%if(analita.isEsito_allarme_rapido()){--%>
     <td>
         <input type="checkbox" name="esito_segnalazione_informazioni_<%=k %>" id="esito_segnalazione_informazioni_<%=k %>" size="20" maxlength="256" onclick="gestioneFlag('<%=k %>')" />
     </td>

	 <%-- <td><%if(analita.isEsito_segnalazione_informazioni()){%><input type="checkbox" checked disabled><%}else{%><input type="checkbox" disabled> &nbsp;<%}%></td>--%>
	 <% }
  if (TicketDetails.getPdp()!=null && TicketDetails.getPdp().getId()>0)
	 {
		 %>
		 <td>
		 <%=(TicketDetails.getPdp().getOrgdDetails() != null) ? TicketDetails.getPdp().getOrgdDetails().getName()+"<br> loc. "+TicketDetails.getPdp().getOrgdDetails().getAddressList().getAddress(5).toString() : ""%>
		
		 </td>
		 <%
	 }
	 %>
	 </tr>
	 <%
 }
 %>

 
