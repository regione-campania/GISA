<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="Anagrafica" class="org.aspcfs.modules.gestioneosm.base.SinvsaAnagrafica" scope="request"/>
 <%@ page import="org.aspcfs.modules.gestioneosm.base.*" %>
 
<jsp:useBean id="riferimentoId" class="java.lang.String" scope="request"/>
<jsp:useBean id="riferimentoIdNomeTab" class="java.lang.String" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@ include file="../../initPage.jsp"%>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script>

function checkForm(riferimentoId, riferimentoIdNomeTab){
	if (confirm("Attenzione. Inviare questa anagrafica?")){
		loadModalWindow();
		document.getElementById("invioOsm").submit();
	}
}

function showView(div){
	if (document.getElementById(div).style.display=="none")
		document.getElementById(div).style.display="table-row";
	else
		document.getElementById(div).style.display="none";
}
</script>

<style>
.tableOsm {
table-layout: fixed;
word-wrap:break-word; 
border: 1px solid black;
border-collapse: collapse;
}
.tableOsm td,th{
border: 1px solid black;
font-size: 18px;
}
.tableOsm th{
background-color: 00ffee;
}
.esito {
font-size: 12px !important;
}

</style>

<center>
<div style="border: 1px solid black">
<font size="3px">
<b>LEGENDA</b><br/>
In questa pagina sara' possibile inviare le anagrafiche OSM alla banca dati SINVSA.<br/>
Si ricorda che ogni anagrafica OSM e' formata da:<br/>
<ol>
<li>Persona</li>
<li>Sede Legale</li>
<li>Sede Operativa</li>
<li>Impresa</li>
<li>1-N Attivita' (Composte da Sezioni/Attivita' e Prodotti Specie)</li>
</ol>
<div style="background: lime">Una riga verde indica che quella componente e' stata inviata con esito OK.</div>
<div style="background: yellow">Una riga gialla indica che quella componente non e' stata inviata o che e' stata inviata con esito KO ed e' possibile consultarne il motivo cliccando su "Vedi Dettagli".</div><br/>
In caso di esito KO, le successive componenti non saranno inviate.<br/>
Un OSM sara' considerato completamente inviato a SINVSA quando tutte le sue componenti risultano inviate con esito OK.<br/><br/>
</font>
</div>
</center>

<br/>

<table class="tableOsm">
<col width="40%">

<tr><th colspan="2">ANAGRAFICA OSM</th></tr>

<tr><td>Data ultima interazione SINVSA</td><td><%=toDateasStringWitTime(Anagrafica.getData()) %> <dhv:permission name="osm-invio-view"><input type="button" value="invia" onClick="checkForm('<%=riferimentoId%>', '<%=riferimentoIdNomeTab%>')"/></dhv:permission></td></tr>
<tr><td>Utente ultima interazione SINVSA</td><td><dhv:username id="<%=Anagrafica.getIdUtente() %>"/></td></tr>
<tr><td>CUN SINVSA</td><td><%=toHtml(Anagrafica.getCunSinvsa()) %></td></tr>

<tr bgcolor="<%=Anagrafica.getIdPersona()>0 ? "lime": "yellow"%>">
<td>Persona</td>
<td><%=Anagrafica.getIdPersona()>0 ? "OK" : "KO" %> <a href="#" onClick="showView('esitoPersona'); return false;"><i>Vedi dettagli</i></a></td>
<tr id="esitoPersona" style="display:none">
<td colspan="2" class="esito">
<xmp><%=Anagrafica.getEsitoPersona()!=null ? Anagrafica.getEsitoPersona().replace(">", ">\n") : "" %></xmp>
</td>
</tr>

<tr bgcolor="<%=Anagrafica.getIdSedeLegale()>0 ? "lime": "yellow"%>">
<td>Sede Legale</td>
<td><%=Anagrafica.getIdSedeLegale()>0 ? "OK" : "KO" %> <a href="#" onClick="showView('esitoSedeLegale'); return false;"><i>Vedi dettagli</i></a></td>
<tr id="esitoSedeLegale" style="display:none">
<td colspan="2" class="esito">
<xmp><%=Anagrafica.getEsitoSedeLegale()!=null ? Anagrafica.getEsitoSedeLegale().replace(">", ">\n") : "" %></xmp>
</td>
</tr>

<tr bgcolor="<%=Anagrafica.getIdSedeOperativa()>0 ? "lime": "yellow"%>">
<td>Sede Operativa</td>
<td><%=Anagrafica.getIdSedeOperativa()>0 ? "OK" : "KO" %> <a href="#" onClick="showView('esitoSedeOperativa'); return false;"><i>Vedi dettagli</i></a></td>
<tr id="esitoSedeOperativa" style="display:none">
<td colspan="2" class="esito">
<xmp><%=Anagrafica.getEsitoSedeOperativa()!=null ? Anagrafica.getEsitoSedeOperativa().replace(">", ">\n") : "" %></xmp>
</td>
</tr>

<tr bgcolor="<%=Anagrafica.getIdImpresa()>0 ? "lime": "yellow"%>">
<td>Impresa</td>
<td><%=Anagrafica.getIdImpresa()>0 ? "OK" : "KO" %> <a href="#" onClick="showView('esitoImpresa'); return false;"><i>Vedi dettagli</i></a></td>
<tr id="esitoImpresa" style="display:none">
<td colspan="2" class="esito">
<xmp><%=Anagrafica.getEsitoImpresa() != null ? Anagrafica.getEsitoImpresa().replace(">", ">\n") : "" %></xmp>
</td>
</tr>

<tr><th colspan="2">ATTIVITA OSM</th></tr>

<% for (int i = 0; i< Anagrafica.getListaSezioneAttivita().size(); i++) {
		SinvsaSezioneAttivita SezioneAttivita = (SinvsaSezioneAttivita) Anagrafica.getListaSezioneAttivita().get(i); %>
		<tr bgcolor="<%=SezioneAttivita.getIdSezioneAttivita()>0 ? "lime": "yellow"%>">
		<td><%=SezioneAttivita.getMacroareaDescrizione() %>-><br/><%=SezioneAttivita.getAggregazioneDescrizione() %></td>
		<td><%=SezioneAttivita.getIdSezioneAttivita()>0 ? "OK" : "KO" %> <a href="#" onClick="showView('esitoSezioneAttivita<%=i %>'); return false;"><i>Vedi dettagli</i></a></td>
		<tr id="esitoSezioneAttivita<%=i %>" style="display:none">
		<td colspan="2" class="esito">
		<xmp><%=SezioneAttivita.getEsitoSezioneAttivita()!= null ? SezioneAttivita.getEsitoSezioneAttivita().replace(">", ">\n") : "" %></xmp>
		</td>
		</tr>
		
		<% for (int j = 0; j< SezioneAttivita.getListaProdottoSpecie().size(); j++) {
		SinvsaProdottoSpecie ProdottoSpecie = (SinvsaProdottoSpecie) SezioneAttivita.getListaProdottoSpecie().get(j); %>
		<tr bgcolor="<%=ProdottoSpecie.getIdProdottoSpecie()>0 ? "lime": "yellow"%>">
		<td><%=ProdottoSpecie.getLineaDescrizione().replace("->", "-><br/>") %></td>
		<td><%=ProdottoSpecie.getIdProdottoSpecie()>0 ? "OK" : "KO" %> <a href="#" onClick="showView('esitoProdottoSpecie<%=i %>_<%=j %>'); return false;"><i>Vedi dettagli</i></a></td>
		<tr id="esitoProdottoSpecie<%=i %>_<%=j %>" style="display:none">
		<td colspan="2" class="esito">
		<xmp><%=ProdottoSpecie.getEsitoProdottoSpecie()!= null ? ProdottoSpecie.getEsitoProdottoSpecie().replace(">", ">\n") : "" %></xmp>
		</td>
		</tr>
		<% } %>
		<tr><td><br/></td></tr>
<% } %>

</table>
	
</center>	
	
<form id = "invioOsm" name="invioOsm" action="GestioneOSM.do?command=InviaOSM&auto-populate=true" method="post">
<input type="hidden" id="riferimentoId" name="riferimentoId" value="<%=riferimentoId%>"/>
<input type="hidden" id="riferimentoIdNomeTab" name="riferimentoIdNomeTab" value="<%=riferimentoIdNomeTab%>"/>
</form>



