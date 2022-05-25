<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>



<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<jsp:useBean id="Richiesta" class="org.aspcfs.modules.sintesis.base.StabilimentoSintesisImport" scope="request"/>

<jsp:useBean id="lineaAttivitaMasterList" class="java.lang.String" scope="request"/>

<jsp:useBean id="TipoImpresaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvinceList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="msg" class="java.lang.String" scope="request"/>
<jsp:useBean id="msgScartati" class="java.lang.String" scope="request"/>


<%@ include file="../initPage.jsp" %>

<script>
function completaDati(id){
	window.location.href="StabilimentoSintesisAction.do?command=PrepareCompletaDati&id="+id;
}
function recuperaDati(id){
	window.location.href="StabilimentoSintesisAction.do?command=RecuperaDati&id="+id;
}
function selezionaLinea(id){
	window.location.href="StabilimentoSintesisAction.do?command=PrepareSelezionaLinea&id="+id;
}

function processaDati(id){
	window.location.href="StabilimentoSintesisAction.do?command=ProcessaRichiesta&id="+id;
}

function rifiutaRichiesta(id){
	if (confirm("Attenzione. Sei sicuro di voler annullare questa richiesta?"))
		window.location.href="StabilimentoSintesisAction.do?command=RifiutaRichiesta&id="+id;
}

function processaImport(id){
	window.location.href="StabilimentoSintesisAction.do?command=ProcessaImport&idImport="+id;
}

function comparaDati(idRichiesta, approval, piva, attivita, sezione){
	  window.open('StabilimentoSintesisAction.do?command=ComparaDati&idRichiesta='+idRichiesta+'&approval='+approval+'&piva='+piva+'&attivita='+attivita+'&sezione='+sezione,'popupSelect',
      'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

}
</script>
   
  <dhv:container name="sintesisimport" selected="Pratiche" object=""  param="">
  
  <% if (Richiesta.getStatoImport() == 0) { %>
  
  <center>
    <input type="button" value="COMPARA DATI" onClick="comparaDati('<%=Richiesta.getId()%>', '<%=Richiesta.getApprovalNumber()%>', '<%=Richiesta.getPartitaIva() %>', '<%=Richiesta.getAttivita() %>', '<%=Richiesta.getDescrizioneSezione() %>')" />
  <br/>
  <input type="button" value="PROVA A RECUPERARE DATI IMPRESA" onClick="recuperaDati('<%=Richiesta.getId()%>')" <%=Richiesta.getOpuIdOperatore()>0 ? "style=\"background:grey\" disabled" : "" %>/>
  <input type="button" value="COMPLETA DATI IMPRESA" onClick="completaDati('<%=Richiesta.getId()%>')" <%=Richiesta.getOpuIdOperatore()>0 ? "style=\"background:grey\" disabled" : "" %>/>
  <br/>
  <input type="button" value="SELEZIONA LINEA MASTER LIST 6" onClick="selezionaLinea('<%=Richiesta.getId()%>')" />
   <input type="button" value="GESTIONE PRODOTTI"/>
   <br/>
   <input type="button" value="PROCESSA IN GISA"  onClick="processaDati('<%=Richiesta.getId()%>')" <%=(Richiesta.getOpuIdLineaProduttivaMasterList()<=0 || (Richiesta.getOpuTipoImpresa()<=0 && Richiesta.getOpuIdOperatore()<=0) ) ? "style=\"background:grey\" disabled" : "" %>/>
   <input type="button" value="RIFIUTA" onClick="rifiutaRichiesta('<%=Richiesta.getId()%>')"/>
  </center>
  <% } else { %>
  

<table style="border: 1px solid black">
<tr><td> Record processato in data <%=toDateasString(Richiesta.getDataProcess()) %> da <dhv:username id="<%= Richiesta.getIdUtenteProcess() %>"></dhv:username> </td> </tr>
</td></tr>
</table>
<input type="button" value="PROSSIMA RICHIESTA" onClick="processaImport('<%=Richiesta.getIdImport()%>')"/>
 <%} %>
  <br/><br/>
  
<%if (msg!=null && !msg.equals("")){ %>
<b>Informazioni: <br/>
<font color="<%=(msg.contains("rrore")) ? "red" : "green" %>"><%= msg%></font>
</b>
<%}%>  
<%if (msgScartati!=null && !msgScartati.equals("")){ %>
<br/>
<font color="red"><%= msgScartati%></font>
</b>
<%}%>  
  		
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

<tr><th colspan="2">Informazioni</th></tr>

<tr>
<td class="formLabel">Stato sede operativa</td>
<td><%=Richiesta.getStatoSedeOperativa() %></td>
</tr>

<tr>
<td class="formLabel">Approval number</td>
<td><%=Richiesta.getApprovalNumber() %></td>
</tr>

<tr>
<td class="formLabel">Denominazione sede operativa</td>
<td><%=Richiesta.getDenominazioneSedeOperativa() %></td>
</tr>

<tr>
<td class="formLabel">Ragione Sociale Impresa</td>
<td><%=Richiesta.getRagioneSocialeImpresa() %></td>
</tr>

<tr>
<td class="formLabel">Partita IVA</td>
<td><%=Richiesta.getPartitaIva() %></td>
</tr>

<tr>
<td class="formLabel">Codice Fiscale</td>
<td><%=Richiesta.getCodiceFiscale() %></td>
</tr>

<tr>
<td class="formLabel">Indirizzo</td>
<td><%=Richiesta.getIndirizzo() %></td>
</tr>

<tr>
<td class="formLabel">Comune</td>
<td><%=Richiesta.getComune() %></td>
</tr>

<tr>
<td class="formLabel">Sigla Provincia</td>
<td><%=Richiesta.getSiglaProvincia() %></td>
</tr>

<tr>
<td class="formLabel">Provincia</td>
<td><%=Richiesta.getProvincia() %></td>
</tr>

<tr>
<td class="formLabel">Regione</td>
<td><%=Richiesta.getRegione() %></td>
</tr>

<tr>
<td class="formLabel">Cod. Ufficio Veterinario</td>
<td><%=Richiesta.getCodUfficioVeterinario() %></td>
</tr>

<tr>
<td class="formLabel">Ufficio Veterinario</td>
<td><%=Richiesta.getUfficioVeterinario() %></td>
</tr>

<tr>
<td class="formLabel">Attivita'</td>
<td><%=Richiesta.getAttivita() %></td>
</tr>


<tr>
<td class="formLabel">Stato attivita'</td>
<td><%=Richiesta.getStatoAttivita() %></td>
</tr>

<tr>
<td class="formLabel">Descrizione Sezione</td>
<td><%=Richiesta.getDescrizioneSezione() %></td>
</tr>

<tr>
<td class="formLabel">Data inizio attivita'</td>
<td><%=Richiesta.getDataInizioAttivita() %></td>
</tr>

<tr>
<td class="formLabel">Data fine attivita'</td>
<td><%=Richiesta.getDataFineAttivita() %></td>
</tr>

<tr>
<td class="formLabel">Tipo autorizzazione</td>
<td><%=Richiesta.getTipoAutorizzazione() %></td>
</tr>

<tr>
<td class="formLabel">Imballaggio</td>
<td><%=Richiesta.getImballaggio() %></td>
</tr>

<tr>
<td class="formLabel">Paesi abilitati export</td>
<td><%=Richiesta.getPaesiAbilitatiExport() %></td>
</tr>

<tr>
<td class="formLabel">Remark</td>
<td><%=Richiesta.getRemark() %></td>
</tr>

<tr>
<td class="formLabel">Species</td>
<td><%=Richiesta.getSpecies() %></td>
</tr>

<tr>
<td class="formLabel">Informazioni aggiuntive</td>
<td><%=Richiesta.getInformazioniAggiuntive() %></td>
</tr>

</table>
	
	<br/><br/>
	
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr><th colspan="2">Dati completati</th></tr>

<tr><th colspan="2">Linea attivita'</th></tr>

<tr><td class="formLabel">Linea Master List</td>
<td><%=lineaAttivitaMasterList %></td></tr>

<tr><th colspan="2">Impresa</th></tr>

<% if (Richiesta.getOpuOperatore()!=null && Richiesta.getOpuOperatore().getIdOperatore()>0){ %>

<tr><td class="formLabel">Tipo Impresa</td>
<td><%=TipoImpresaList.getSelectedValue(Richiesta.getOpuOperatore().getTipoImpresa()) %></td></tr>

<tr><td class="formLabel">Indirizzo sede legale</td>
<td>
<%=ToponimiList.getSelectedValue(Richiesta.getOpuOperatore().getSedeLegale().getToponimo()) %> 
<%=toHtml(Richiesta.getOpuOperatore().getSedeLegale().getVia())%>, 
<%=toHtml(Richiesta.getOpuOperatore().getSedeLegale().getCivico() )%>, 
<%=ComuniList.getSelectedValue(Richiesta.getOpuOperatore().getSedeLegale().getComune())%> 
(<%=ProvinceList.getSelectedValue(Richiesta.getOpuOperatore().getSedeLegale().getProvincia()) %>), 
 <%=toHtml(Richiesta.getOpuOperatore().getSedeLegale().getCap() )%>
</td></tr>

<% } else { %>

<tr><td class="formLabel">Tipo Impresa</td>
<td><%=TipoImpresaList.getSelectedValue(Richiesta.getOpuTipoImpresa()) %></td></tr>

<tr><td class="formLabel">Tipo Societa</td>
<td><%=TipoSocietaList.getSelectedValue(Richiesta.getOpuTipoSocieta()) %></td></tr>

<tr><td class="formLabel">Domicilio digitale</td>
<td><%=toHtml(Richiesta.getOpuDomicilioDigitale() )%></td></tr>

<tr><td class="formLabel">Indirizzo sede legale</td>
<td>
<%=ToponimiList.getSelectedValue(Richiesta.getOpuToponimoSedeLegale()) %> 
<%=toHtml(Richiesta.getOpuViaSedeLegale())%>, 
<%=toHtml(Richiesta.getOpuCivicoSedeLegale() )%>, 
<%=ComuniList.getSelectedValue(Richiesta.getOpuComuneSedeLegale())%> 
(<%=ProvinceList.getSelectedValue(Richiesta.getOpuProvinciaSedeLegale()) %>), 
 <%=toHtml(Richiesta.getOpuCapSedeLegale() )%>
</td></tr>

<tr><th colspan="2">Rappresentante legale</th></tr>

<tr><td class="formLabel">Nome</td>
<td><%=toHtml(Richiesta.getOpuNomeRappresentante() )%></td></tr>

<tr><td class="formLabel">Cognome</td>
<td><%=toHtml(Richiesta.getOpuCognomeRappresentante() )%></td></tr>

<tr><td class="formLabel">Codice Fiscale</td>
<td><%=toHtml(Richiesta.getOpuCodiceFiscaleRappresentante() )%></td></tr>

<tr><td class="formLabel">Indirizzo residenza</td>
<td>
<%=ToponimiList.getSelectedValue(Richiesta.getOpuToponimoResidenzaRappresentante()) %> 
<%=toHtml(Richiesta.getOpuViaResidenzaRappresentante())%>, 
<%=toHtml(Richiesta.getOpuCivicoResidenzaRappresentante() )%>, 
<%=ComuniList.getSelectedValue(Richiesta.getOpuComuneResidenzaRappresentante())%> 
(<%=ProvinceList.getSelectedValue(Richiesta.getOpuProvinciaResidenzaRappresentante()) %>), 
 <%=toHtml(Richiesta.getOpuCapResidenzaRappresentante() )%>
</td></tr>

<tr><td class="formLabel">Domicilio digitale</td>
<td><%=toHtml(Richiesta.getOpuDomicilioDigitaleRappresentante() )%></td></tr>
<%} %>

</table>


</dhv:container>

</body>
</html>