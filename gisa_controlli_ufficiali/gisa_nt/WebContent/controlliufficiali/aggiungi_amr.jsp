<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="DatiAMR" class="org.aspcf.modules.controlliufficiali.base.DatiAMR" scope="request"/>
<jsp:useBean id="listaVeterinari" class="java.util.ArrayList" scope="request"/>

<%@ include file="../initPage.jsp" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

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
function checkForm(form){
	var invio = form.inviosinvsa.value;
	
	document.getElementById("numVerbaleAMR").value = document.getElementById("numVerbaleAMR").value.toUpperCase(); 
	
	var msg = "Salvare i dati inseriti?";
	
	if (invio =='invia')
		msg+="\n\n I dati saranno inviati automaticamente a SINVSA.";
	else
		msg+="\n\n ATTENZIONE. E' stato selezionato di NON inviare i dati a SINVSA.";
	
if (confirm(msg)){
	loadModalWindow();
	form.submit();
}
	
}

</script>


<form id = "addAccount" name="addAccount" action="PrintReportVigilanza.do?command=SalvaAMR&auto-populate=true" method="post">

<table class="details">
<tr><th colspan="2">DATI AMR</th></tr>

<tr><td>ID CONTROLLO</td> <td> <input type="text" readonly id="idControllo" name="idControllo" value="<%=TicketDetails.getId() %>"/> </td> </tr>
<tr><td>NUMERO VERBALE AMR</td> <td><input type="text" id="numVerbaleAMR" name="numVerbaleAMR" value="<%=toHtml(DatiAMR.getNumVerbaleAMR()) %>"/>
<br/><font color="red" size="1px">Il numero di verbale AMR deve terminare con la lettera<br/>"<b>C</b>" per le carni refrigerate<br/>"<b>I</b>" per l'intestino</font>
</td></tr>
<tr><td>CODICE FISCALE VETERINARIO PRELEVATORE 
</td> <td>
<select id="codiceFiscaleVeterinario" name="codiceFiscaleVeterinario">
<% for (int i = 0; i<listaVeterinari.size(); i++){ 
String elemento = (String) listaVeterinari.get(i);
String elem[] = elemento.split(";;");
%>
<option value="<%=elem[0]%>" <%=(DatiAMR!=null && DatiAMR.getCodiceFiscaleVeterinario()!=null && DatiAMR.getCodiceFiscaleVeterinario().equalsIgnoreCase(elem[0])) ? "selected" : "" %>><%=elem[1] %>: <%=elem[0] %></option>
<% } %>
</select>
<div title="Sono selezionabili solo i veterinari presenti nel nucleo ispettivo e con un codice fiscale valido."><label style="background:lime">( ? )</label></div>
</td></tr>

<tr><td>ORA INIZIO PRELIEVO CAMPIONE</td> <td><input type="time" id="oraInizioPrelievo" name="oraInizioPrelievo" value="<%=toHtml(DatiAMR.getOraInizioPrelievo()) %>"/></td></tr>
<tr><td>ORA FINE PRELIEVO CAMPIONE</td> <td><input type="time" id="oraFinePrelievo" name="oraFinePrelievo" value="<%=toHtml(DatiAMR.getOraFinePrelievo()) %>"/></td></tr>
<tr><td>CODICE FISCALE RAPPRESENTANTE</td> <td><input type="text" id="codiceFiscaleRappresentante" name="codiceFiscaleRappresentante" value="<%=toHtml(DatiAMR.getCodiceFiscaleRappresentante()) %>"/></td></tr>
<tr><td>TELEFONO DELLA DITTA</td> <td><input type="text" id="telefono" name="telefono" value="<%=toHtml(DatiAMR.getTelefono()) %>"/></td></tr>

<%if (TicketDetails.getTipologia_operatore()==2000) { //SOLO SE SINTESIS (MACELLO)%>
<tr><td>ID FISCALE PROPRIETARIO</td> <td><input type="text" id="idFiscaleProprietario" name="idFiscaleProprietario" value="<%=toHtml(DatiAMR.getIdFiscaleProprietario()) %>"/></td></tr>
<tr><td>RAGIONE SOCIALE AZIENDA</td> <td><input type="text" id="ragioneSociale" name="ragioneSociale" value="<%=toHtml(DatiAMR.getRagioneSociale()) %>"/></td></tr>
<tr><td>INDIRIZZO DELL'AZIENDA</td> <td><input type="text" id="indirizzo" name="indirizzo" value="<%=toHtml(DatiAMR.getIndirizzo()) %>"/></td></tr>
<% } %>

<tr><td>NUM REGISTRAZIONE PROVENIENZA</td> <td><input type="text" id="numRegistrazioneProvenienza" name="numRegistrazioneProvenienza" value="<%=toHtml(DatiAMR.getNumRegistrazioneProvenienza()) %>"/><br/><font color="red" size="1px">Il numero di registrazione dell'impresa di provenienza del campione deve essere diverso da quello del punto di prelievo.</font></td></tr>
<tr><td>LOCALE/RECINTO CAMPIONATO</td> <td><input type="number" id="locale" name="locale" size="4" value="<%=toHtml(DatiAMR.getLocale()) %>"/></td></tr>
<tr><td>DATA DI ACCASAMENTO</td> 
<td> <input type="text" readonly id="dataAccasamento" name="dataAccasamento" size="10" value="<%=toHtml(DatiAMR.getDataAccasamento()) %>"/>
<a href="#" onClick="cal19.select(document.forms[0].dataAccasamento,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> &nbsp;
</td> 
</tr>
<tr><td>CAPACITA DI MACELLAZIONE</td> <td><input type="number" id="capacita" name="capacita" size="4" value="<%=toHtml(DatiAMR.getCapacita()) %>"/></td></tr>
<tr><td> <input type="radio" id="invia" name="inviosinvsa" value="invia" checked>Inviare a SINVSA</td> <td><input type="radio" id="noninvia" name="inviosinvsa" value="noninvia">NON inviare a SINVSA</td></tr>

<tr><td colspan="2"><input type="button" value="SALVA" onClick="checkForm(this.form)"/></td></tr>
</table>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

