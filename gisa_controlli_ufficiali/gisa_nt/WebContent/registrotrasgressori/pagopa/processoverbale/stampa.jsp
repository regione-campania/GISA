<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="SanzioneDettaglio" class="org.aspcfs.modules.sanzioni.base.Ticket" scope="request"/>
<jsp:useBean id="CUDettaglio" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="listaPagamenti" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="Trasgressore" class="org.aspcfs.modules.registrotrasgressori.base.AnagraficaPagatore" scope="request"/>
<jsp:useBean id="Obbligato" class="org.aspcfs.modules.registrotrasgressori.base.AnagraficaPagatore" scope="request"/>

<%@ page import="org.aspcfs.modules.registrotrasgressori.base.Pagamento" %>
<%@ page import="java.util.*" %>

<%@ include file="../../../initPage.jsp"%>

<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="registrotrasgressori/css/sanzioni_screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="registrotrasgressori/css/sanzioni_print.css" />

<table class="tableScheda">

<tr><td><div class="boxIdDocumento"></div><div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div></td><th>Stampa di accompagnamento dettaglio sanzione per Processo Verbale</th></tr>

<tr><th>Id Controllo ufficiale</th> <td><%=CUDettaglio.getId() %></td></tr>
<tr><th>Id Sanzione</th> <td><%=SanzioneDettaglio.getId() %></td></tr>
<tr><th>Identificativo NC</th> <td><%=SanzioneDettaglio.getIdentificativonc() %></td></tr>
<tr><th>Codice sanzione</th> <td><%=SanzioneDettaglio.getIdentificativo() %></td></tr>
<tr><th>Trasgressore</th> <td><%=SanzioneDettaglio.getTrasgressore() %></td></tr>
<tr><th>Obbligato in solido</th> <td><%=SanzioneDettaglio.getObbligatoinSolido() %></td></tr>
<tr><th>Processo Verbale</th> <td><%=SanzioneDettaglio.getTipo_richiesta() %></td></tr>
<tr><th>Norma violata</th> <td>

<%if(SanzioneDettaglio.getListaNorme().size()!=0){ 
	HashMap<Integer,String> listanorme =SanzioneDettaglio.getListaNorme();
	Set<Integer> setkiavi = listanorme.keySet();
	Iterator<Integer> iteraNorme=setkiavi.iterator();
	while(iteraNorme.hasNext()){
		int chiave = iteraNorme.next();
		String value=listanorme.get(chiave);%>
		<%=listanorme.get(chiave)%><br> 
	<%} } %>	

</td></tr>

<tr><th>Pagamento ridotto</th> <td><%=SanzioneDettaglio.getPagamento() %></td></tr>

<% for (int i = 0; i<listaPagamenti.size(); i++) { 
Pagamento p = (Pagamento) listaPagamenti.get(i); %>
<tr><th colspan="2">RICHIESTA DI PAGAMENTO #<%=p.getIndice() %> </th></tr>

<tr><th colspan="2">INFORMAZIONI SOGGETTO PAGATORE </th></tr>

<tr>
<th>Soggetto pagatore</th> 
<td> <%=p.getPagatore().getRagioneSocialeNominativo() %> (<b><%= (p.getPagatore().getId() == Trasgressore.getId() ? "TRASGRESSORE" : "OBBLIGATO IN SOLIDO") %></b>) </td>
</tr>

<tr><th colspan="2">INFORMAZIONI VERSAMENTO </th></tr>
<tr><th>Tipo pagamento</th> <td><%=p.getTipoPagamento().equals("PV") ? "Per Processo Verbale" : p.getTipoPagamento().equals("NO") ? "Per Numero Ordinanza" : "" %></td>
<tr><th>Data scadenza pagamento</th> <td><%=p.getDataScadenza() %> </td></tr>
<tr><th>Importo totale versamento</th> <td><%=p.getImportoSingoloVersamento() %></td></tr>
<tr><th>Identificativo Univoco Dovuto (IUD) GISA</th> <td><%=p.getIdentificativoUnivocoDovuto() %></td></tr>
<tr><th>Identificativo Univoco Versamento (IUV) PagoPa</th> <td><%=p.getIdentificativoUnivocoVersamento() %></td></tr>
<tr><th>Stato pagamento PagoPa</th> <td><%=toHtml(p.getStatoPagamento()) %> <%if (p.getIdentificativoUnivocoRiscossione()!=null && !"".equals(p.getIdentificativoUnivocoRiscossione())) { %><br/><b>Identificativo univoco riscossione: </b> <%=p.getIdentificativoUnivocoRiscossione() %> <br/><b>Denoninazione beneficiario: </b> <%=p.getDenominazioneBeneficiario() %> <br/><b>Denoninazione attestante: </b> <%=p.getDenominazioneAttestante() %> <br/><b>Esito singolo pagamento: </b> <%=p.getEsitoSingoloPagamento() %>  <%} %></td></tr>

<% } %>

</table>

<center>
<div id="stampa">
<jsp:include page="/gestione_documenti/boxDocumentaleNoAutomatico.jsp"> 
<jsp:param name="orgId" value="<%=CUDettaglio.getOrgId()%>" />
<jsp:param name="stabId" value="<%=CUDettaglio.getIdStabilimento()%>" />
<jsp:param name="altId" value="<%=CUDettaglio.getAltId()%>" />
<jsp:param name="extra" value="<%=SanzioneDettaglio.getId() %>" />
<jsp:param name="tipo" value="SchedaSanzione" />
</jsp:include>
</div>
</center>