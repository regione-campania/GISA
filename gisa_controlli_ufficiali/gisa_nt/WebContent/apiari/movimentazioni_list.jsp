<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="ext.aspcfs.modules.apicolture.actions.Movimentazioni"%>
<%@page import="java.util.Date"%>
<%@page import="ext.aspcfs.modules.apiari.base.ModelloC"%>
<%@page
	import="ext.aspcfs.modules.apicolture.actions.StabilimentoAction"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<jsp:useBean id="ListaMovimentazioni"
	class="ext.aspcfs.modules.apiari.base.MovimentazioniList"
	scope="request" />
<jsp:useBean id="SearchMovimentazioniListInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="LookupTipoMovimentazione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Operatore"
	class="ext.aspcfs.modules.apiari.base.Operatore" scope="request" />

<%@ include file="../initPage.jsp"%>

<script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>

<table class="trails" cellspacing="0">
	<tr>
		<td width="100%">APICOLTURA > LISTA MOVIMENTAZIONI</td>
	</tr>
</table>
<br>




<%
	Date dataAttuale = (Date) request.getAttribute("dataAttuale");

	int columnCount = 0;
%>

<p>Attenzione! Si ricorda che l'effettivo cambio di consistenza
	degli apiari coinvolti nelle movimentazioni avviene a fine anno con il
	censimento.</p>
<table cellpadding="8" cellspacing="0" border="0" width="100%"
	class="pagedList">
	<tr>

		<th>TIPO MOVIMENTAZIONE</th>
		<th>Data Movimentazione</th>

		<th>AZIENDA ORIGINE</th>
		<th>PROG. APIARIO ORIGINE</th>
		<th>NUM ALVEARI ORIGINE</th>
		<th>AZIENDA DESTINAZIONE</th>
		<th>PROG. APIARIO /<br />SEDE LEGALE AZIENDA DESTINAZIONE
		</th>

		<th>MATERIALE MOVIMENTATO</th>
		<th>Stampa</th>

		<th nowrap <%++columnCount;%>><strong>Stato BDN</strong></th>

	</tr>
	<%
		Iterator j = ListaMovimentazioni.iterator();
		if (j.hasNext()) {
	%>
	<%-- <%="ID APIARIO "+((ModelloC)ListaMovimentazioni.get(0)).getIdStabilimentoOrigine()%> --%>
	<input type="button" value="Report XLS Movimentazioni"
		onclick="javascript: window.open('GenerazioneExcel.do?command=GetExcel&tipo_richiesta=movimentazioniApiari&codiceAziendaSearch=<%=ListaMovimentazioni.getCodiceAziendaSearch()%>&progressivoApiarioSearch=<%=ListaMovimentazioni.getProgressivoApiarioSearch()%>&idApiario=-1');" />
	<BR>
	<br>
	<%
		int rowid = 0;
			int i = 0;
			while (j.hasNext()) {
				i++;
				rowid = (rowid != 1 ? 1 : 2);
				ModelloC thisMovimentazione = (ModelloC) j.next();
	%>

	<tr class="row<%=rowid%>">
		<td><%=LookupTipoMovimentazione.getSelectedValue(thisMovimentazione.getIdTipoMovimentazione())%></td>
		<td><%=toDateasString(thisMovimentazione.getDataMovimentazione())%></td>

		<td><%=thisMovimentazione.getCodiceAziendaOrigine()%></td>
		<td><%=(thisMovimentazione.getProgressivoApiarioOrigine()!=null)?(thisMovimentazione.getProgressivoApiarioOrigine()):("")%></td>
		<td><%=thisMovimentazione.getNumApiariOrigine()%></td>


		<td><%=(thisMovimentazione.getIdTipoMovimentazione() != 3) ? ("CODICE AZIENDA: " + thisMovimentazione
							.getCodiceAziendaDestinazione()) : ("CF: " + thisMovimentazione.getCfPartitaApicoltore()
							+ "<br/>" + "DENOMINAZIONE: " + thisMovimentazione.getDenominazioneApicoltore())%></td>
		<td><%=(thisMovimentazione.getIdTipoMovimentazione() != 3 && thisMovimentazione.getProgressivoApiarioDestinazione()!=null) ? (thisMovimentazione.getProgressivoApiarioDestinazione()): (thisMovimentazione.getIndirizzo_dest() + ", " + thisMovimentazione.getComune_dest())%></td>
		<td><%="ALVEARI: " + thisMovimentazione.getNumAlveariDaSpostare() + "<br/>" + "SCIAMI: "
							+ thisMovimentazione.getNumSciamiDaSpostare() + "<br/>" + "PACCHI DI API: "
							+ thisMovimentazione.getNumPacchiDaSpostare() + "<br/>" + "API REGINE: "
							+ thisMovimentazione.getNumRegineDaSpostare() + "<br/>"%></td>

		<td><input type="button" title="Stampa" value="Stampa"
			onClick="openRichiestaPDFMovimentazione('<%=thisMovimentazione.getId()%>', 'ApicolturaMovimentazione');">
			<%
				if (thisMovimentazione.getIdTipoMovimentazione() != 4) {
			%> <input type="button" title="Allegato C" value="Allegato C"
			onClick="openRichiestaPDFMovimentazione('<%=thisMovimentazione.getId()%>', 'ApicolturaAllegatoC');"><br />
			<%
				}

						System.out.println("dataAttuale: " + dataAttuale);

						Date dataMovimentazione = new Date(thisMovimentazione.getEntered().getTime());

						System.out.println("dataMovimentazione: " + dataMovimentazione);

						if (dataAttuale.compareTo(dataMovimentazione) < 0 && (!thisMovimentazione.isAttestazioneSanitaria() || thisMovimentazione.getStato()==Movimentazioni.API_STATO_DA_NOTIFICARE || thisMovimentazione.getStato()==Movimentazioni.API_STATO_VALIDAZIONE_NON_RICHIESTA) && (thisMovimentazione.getAccettazioneDestinatario()==1 || thisMovimentazione.getAccettazioneDestinatario()==3 || thisMovimentazione.getAccettazioneDestinatario()==0)) {
			%> <input type="button" title="Modifica" value="Modifica e Reinvia"
			onClick="location.href='ApicolturaMovimentazioni.do?command=ModificaMovimentazioni&idApiario=<%=thisMovimentazione.getApiarioOrigine().getIdStabilimento()%>&tipoMovimentazione=<%=LookupTipoMovimentazione.getSelectedValue(thisMovimentazione
								.getIdTipoMovimentazione())%>&idMovimentazione=<%=thisMovimentazione.getId()%>';">
			<%
				}
			%> <input type="button" title="Storico" value="Storico"
			onClick="window.location.href='ApicolturaMovimentazioni.do?command=SearchStorico&idMovimentazione=<%=thisMovimentazione.getId()%>';">
		</td>

		<td><%=thisMovimentazione.isSincronizzato_bdn() == true ? "<img src=\"images/verde.gif\">"
							: "<img src=\"images/rosso.gif\">"%></td>


	</tr>
	<%
		}
	%>
	<%
		} else {
	%>
	<!-- metto comportamento dummy per il bottone di reportistica -->
	<input type="button" value="Report XLS Movimentazioni"
		onclick="alert('Attenzione, non esistono movimentazioni associate');" />
	<br>
	<br>
	<tr class="containerBody">
		<td colspan="11">NON ESISTONO MOVIMENTAZIONI</td>
	</tr>
	<%
		}
	%>
</table>
<br />
<dhv:pagedListControl object="SearchMovimentazioniListInfo"
	tdClass="row1" />