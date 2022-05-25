<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<jsp:useBean id="Relazione" class="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva" scope="request"/>
<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>
<jsp:useBean id="Automezzo" class="org.aspcfs.modules.sintesis.base.SintesisAutomezzo" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ProvinceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ page import="org.aspcfs.modules.sintesis.base.*" %>
<%@ page import="org.aspcfs.modules.gestioneml.base.*" %> 

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="sintesis/scarrabili/css/screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="sintesis/scarrabili/css/print.css" />

<%@ include file="../../initPage.jsp" %> 

<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>

<script>

function modifica(id){
	loadModalWindow();
	window.location.href='StabilimentoSintesisAction.do?command=ModificaAutomezzo&id='+id;
}
function dismetti(id){
	if (confirm("Questo automezzo sara' dismesso. Proseguire?")){
	loadModalWindow();
	window.location.href='StabilimentoSintesisAction.do?command=DismettiAutomezzo&id='+id;
	}
}
function elimina(id){
	if (confirm("Questo automezzo sara' eliminato. Proseguire?")){
	loadModalWindow();
	window.location.href='StabilimentoSintesisAction.do?command=EliminaAutomezzo&id='+id;
	}
}
</script>

<center>
<i>Dettaglio automezzi associato alla linea:</i><br/>
<b><%=Relazione.getPathCompleto() %></b><br/>
<i>sullo stabilimento:</i> <br/>
<b><%=Stabilimento.getDenominazione() %></b><br/>
<a href="StabilimentoSintesisAction.do?command=ListaAutomezziLinea&idRelazione=<%=Relazione.getIdRelazione()%>">(Torna alla lista automezzi)</a>
</center>

<br/><br/>	
	
<br/>

<center>
<table cellspacing="20" cellpadding="20">
<tr>

<td align="center">
<dhv:permission name="sintesis-scarrabili-delete">
<input type="button" value="elimina" onClick="elimina('<%=Automezzo.getId()%>')" style="background:red"/>
</dhv:permission>
</td>

<td align="center">
<dhv:permission name="sintesis-scarrabili-edit">
<input type="button" value="modifica" onClick="modifica('<%=Automezzo.getId()%>')"/>
</dhv:permission>
</td>

<td align="center">
<dhv:permission name="sintesis-scarrabili-edit">
<input type="button" value="dismetti" onClick="dismetti('<%=Automezzo.getId()%>')"/>
</dhv:permission>
</td>

<td align="center">
<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/> 
<input type="button" title="Stampa" value="Stampa" onClick="openRichiestaPDFScarrabile('<%= Automezzo.getId() %>', '<%=Stabilimento.getAltId()%>');">
</td>

</tr>
</table>
</center>

<center>
<b>Servizio Veterinario Sanita' Animale UOS SOA ASL NA2NORD</b><br/>
Oggetto: <b>Comunicazione relativa agli automezzi o ai contenitori riutilizzabili per il trasporto <br/>di sottoprodotti o di prodotti derivati</b> (da presentare in duplice copia, per ogni singolo automezzo)
</center>

<br/>

<input type="checkbox" disabled checked><b>comunicazione per acquisizione automezzo/contenitore</b><br/>

<br/>

<b> 1. Impresa che utilizza l'automezzo/contenitore o impresa che noleggia a terzi l'automezzo/contenitore</b><br/>
<table class="details" width="100%"cellpadding="10" cellspacing="10" style="border-collapse: collapse">
<tr><td>
Denominazione o ragione sociale <label class="layout"><%=toHtmlFill(Stabilimento.getOperatore().getRagioneSociale(), 120) %></label><br/>
Codice fiscale: <label class="layout"><%=toHtmlFill(Stabilimento.getOperatore().getCodiceFiscaleImpresa(), 120) %></label><br/>
Partita I.V.A. (se diversa da C.F.) <label class="layout"><%=toHtmlFill(Stabilimento.getOperatore().getPartitaIva(), 120) %></label><br/>
con sede operativa nel comune di <label class="layout"><%=toHtmlFill(ComuniList.getSelectedValue(Stabilimento.getIndirizzo().getComune()), 120) %></label> Provincia <label class="layout"><%=toHtmlFill(ProvinceList.getSelectedValue(Stabilimento.getIndirizzo().getIdProvincia()), 10) %></label> <br/>
Via/Piazza  <label class="layout"><%=toHtmlFill(ToponimiList.getSelectedValue(Stabilimento.getIndirizzo().getToponimo()), 10) %></label> <label class="layout"><%=toHtmlFill(Stabilimento.getIndirizzo().getVia(), 120) %></label> N. <label class="layout"><%=toHtmlFill(Stabilimento.getIndirizzo().getCivico(), 10) %></label> CAP <label class="layout"><%=toHtmlFill(Stabilimento.getIndirizzo().getCap(), 15) %></label>
</td></tr>
</table>

<b> 2. Identificativo dell'automezzo/contenitore</b><br/>
<table class="details" width="100%"cellpadding="10" cellspacing="10" style="border-collapse: collapse">
<tr><td>
Marca <label class="layout"><%=toHtmlFill(Automezzo.getAutomezzoMarca(), 60) %></label> Tipo <label class="layout"><%=toHtmlFill(Automezzo.getAutomezzoTipo(), 60) %></label> Targa <label class="layout"><%=toHtmlFill(Automezzo.getAutomezzoTarga(), 60) %></label> <br/>
</td></tr>
</table>

<b> 3. Luogo di ricovero abituale dell'automezzo/contenitore</b> (se diverso dalla sede operativa dell'impresa)<br/>
<table class="details" width="100%"cellpadding="10" cellspacing="10" style="border-collapse: collapse">
<tr><td>
Via/Corso/Piazza  <label class="layout"><%=toHtmlFill(ToponimiList.getSelectedValue(Automezzo.getRicoveroIdToponimo()), 10) %></label> <label class="layout"><%=toHtmlFill(Automezzo.getRicoveroVia(), 120) %></label> N. civico <label class="layout"><%=toHtmlFill(Automezzo.getRicoveroCivico(), 10) %></label> <br/>
Comune <label class="layout"><%=toHtmlFill(ComuniList.getSelectedValue(Automezzo.getRicoveroIdComune()), 120) %></label> Provincia <label class="layout"><%=toHtmlFill(ProvinceList.getSelectedValue(Automezzo.getRicoveroIdProvincia()), 10) %></label> 
</td></tr>
</table>

<b> 4. Luogo di detenzione del registro delle partite di cui all'art. 22 del Reg. CE/1069/2009</b> (se diverso dalla sede operativa dell'impresa)<br/>
<table class="details" width="100%"cellpadding="10" cellspacing="10" style="border-collapse: collapse">
<tr><td>
Via/Corso/Piazza  <label class="layout"><%=toHtmlFill(ToponimiList.getSelectedValue(Automezzo.getDetenzioneIdToponimo()), 10) %></label> <label class="layout"><%=toHtmlFill(Automezzo.getDetenzioneVia(), 120) %></label> N. civico <label class="layout"><%=toHtmlFill(Automezzo.getDetenzioneCivico(), 10) %></label> <br/>
Comune <label class="layout"><%=toHtmlFill(ComuniList.getSelectedValue(Automezzo.getDetenzioneIdComune()), 120) %></label> Provincia <label class="layout"><%=toHtmlFill(ProvinceList.getSelectedValue(Automezzo.getDetenzioneIdProvincia()), 10) %></label> 
</td></tr>
</table>

<b> 5. Caratteristiche dell'automezzo o del contenitore e materiali trasportati</b><br/>
<table class="details" width="100%"cellpadding="10" cellspacing="10" style="border-collapse: collapse">
<tr><td>
<input type="checkbox" disabled name="automezzoCisternaTrasporto" id="automezzoCisternaTrasporto" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoCisternaTrasporto())) ? "checked=\"checked\"" : "" %>> cisterna adibita al trasporto<br/>
<input type="checkbox" disabled name="automezzoVeicoloFreschi" id="automezzoVeicoloFreschi"  <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloFreschi())) ? "checked=\"checked\"" : "" %>> veicolo adibito al trasporto di sottoprodotti freschi <input type="checkbox" disabled name="automezzoVeicoloFreschiCat1" id="automezzoVeicoloFreschiCat1" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloFreschiCat1())) ? "checked=\"checked\"" : "" %>> categoria 1 <input type="checkbox" disabled name="automezzoVeicoloFreschiCat2" id="automezzoVeicoloFreschiCat2" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloFreschiCat2())) ? "checked=\"checked\"" : "" %>> categoria 2 <input type="checkbox" disabled name="automezzoVeicoloFreschiCat3" id="automezzoVeicoloFreschiCat3" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloFreschiCat3())) ? "checked=\"checked\"" : "" %>> categoria 3<br/>
<input type="checkbox" disabled name="automezzoVeicoloDerivati" id="automezzoVeicoloDerivati" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloDerivati())) ? "checked=\"checked\"" : "" %>> veicolo adibito al trasporto di prodotti derivati <input type="checkbox" disabled name="automezzoVeicoloDerivatiCat1" id="automezzoVeicoloDerivatiCat1" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloDerivatiCat1())) ? "checked=\"checked\"" : "" %>> categoria 1 <input type="checkbox" disabled name="automezzoVeicoloDerivatiCat2" id="automezzoVeicoloDerivatiCat2" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloDerivatiCat2())) ? "checked=\"checked\"" : "" %>> categoria 2 <input type="checkbox" disabled name="automezzoVeicoloDerivatiCat3" id="automezzoVeicoloDerivatiCat3" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloDerivatiCat3())) ? "checked=\"checked\"" : "" %>> categoria 3<br/>
<input type="checkbox" disabled name="automezzoContenitore" id="automezzoContenitore" <%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoContenitore())) ? "checked=\"checked\"" : "" %>> contenitore: dimensioni e caratteristiche <label class="layout"><%=toHtmlFill(Automezzo.getAutomezzoContenitoreDimensioni(), 120) %></label>
</td></tr>
</table>

<b> 6. Caratteristiche del trasporto</b><br/>
<table class="details" width="100%"cellpadding="10" cellspacing="10" style="border-collapse: collapse">
<tr><td>
<input type="checkbox" disabled name="trasportoTemperaturaControllata" id="trasportoTemperaturaControllata" <%=(Automezzo!=null && "S".equals(Automezzo.getTrasportoTemperaturaControllata())) ? "checked=\"checked\"" : "" %>> a temperatura controllata<br/>
<input type="checkbox" disabled name="trasportoIsotermico" id="trasportoIsotermico" <%=(Automezzo!=null && "S".equals(Automezzo.getTrasportoIsotermico())) ? "checked=\"checked\"" : "" %>> isotermico <input type="checkbox" disabled name="trasportoTemperaturaAmbiente" id="trasportoTemperaturaAmbiente" <%=(Automezzo!=null && "S".equals(Automezzo.getTrasportoTemperaturaAmbiente())) ? "checked=\"checked\"" : "" %>> a temperatura ambiente<br/>
</td></tr>
</table>

<table class="details" width="100%"cellpadding="10" cellspacing="10" style="border-collapse: collapse">
<tr><td><b><i>NUMERO IDENTIFICATIVO ATTRIBUITO:</i></b> <label class="layout"><%=toHtmlFill(Automezzo.getNumeroIdentificativo(), 120) %></label> <br/><br/>
</td></tr>
</table>


<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

