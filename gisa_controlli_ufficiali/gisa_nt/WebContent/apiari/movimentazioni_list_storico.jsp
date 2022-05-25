<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>
<%@page import="ext.aspcfs.modules.apiari.base.ModelloC"%>
<%@page import="ext.aspcfs.modules.apicolture.actions.StabilimentoAction"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<jsp:useBean id="ListaMovimentazioni" class="ext.aspcfs.modules.apiari.base.MovimentazioniList" scope="request" />
<jsp:useBean id="SearchMovimentazioniStoricoInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="LookupTipoMovimentazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Operatore" class="ext.aspcfs.modules.apiari.base.Operatore" scope="request" />

<%@ include file="../initPage.jsp"%>


<table class="trails" cellspacing="0">
	<tr>
		<td width="100%">APICOLTURA > LISTA MOVIMENTAZIONI</td>
	</tr>
</table>
<br>

<%
	int columnCount = 0;
%>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
	<tr>
		<th>TIPO MOVIMENTAZIONE</th>
		<th>Data Movimentazione</th>
		<th>AZIENDA ORIGINE</th>
		<th>PROG. APIARIO ORIGINE</th>
		<th>AZIENDA DESTINAZIONE</th>
		<th>PROG. APIARIO /<br />SEDE LEGALE AZIENDA DESTINAZIONE</th>
		<th>NUM ALVEARI SPOSTATI</th>
		<th>NUM SCIAMI SPOSTATI</th>
		<th>NUM PACCHI D'API SPOSTATI</th>
		<th nowrap <% ++columnCount; %>>
          <strong>Stato BDN</strong>
		</th>

		</tr>
<%
			Iterator j = ListaMovimentazioni.iterator();
			if (j.hasNext()) 
			{
%>
				<BR><br>
<%
				int rowid = 0;
				int i = 0;
				while (j.hasNext()) 
				{
					i++;
					rowid = (rowid != 1 ? 1 : 2);
					ModelloC thisMovimentazione = (ModelloC) j.next();
%>

					<tr class="row<%=rowid%>">
						<td>
							<%=LookupTipoMovimentazione.getSelectedValue(thisMovimentazione.getIdTipoMovimentazione())%>
						</td>
						<td>
							<%= toDateasString(thisMovimentazione.getDataMovimentazione())%>
						</td>
						<td>
							<%=thisMovimentazione.getCodiceAziendaOrigine()%>
						</td>
						<td>
							<%=thisMovimentazione.getProgressivoApiarioOrigine()%>
						</td>
						<td><%=(thisMovimentazione.getIdTipoMovimentazione() != 3) ? ("CODICE AZIENDA: " + thisMovimentazione
							.getCodiceAziendaDestinazione()) : ("CF: " + thisMovimentazione.getCfPartitaApicoltore()
							+ "<br/>" + "DENOMINAZIONE: " + thisMovimentazione.getDenominazioneApicoltore())%></td>
						<td><%=(thisMovimentazione.getIdTipoMovimentazione() != 3 && thisMovimentazione.getProgressivoApiarioDestinazione()!=null) ? (thisMovimentazione.getProgressivoApiarioDestinazione()): (thisMovimentazione.getIndirizzo_dest() + ", " + thisMovimentazione.getComune_dest())%></td>
						<td>
							<%=thisMovimentazione.getNumAlveariDaSpostare()%>
						</td>
						<td>
							<%=thisMovimentazione.getNumSciamiDaSpostare()%>
						</td>
						<td>
							<%=thisMovimentazione.getNumPacchiDaSpostare()%>
						</td>
						<td><%=thisMovimentazione.isSincronizzato_bdn()==true ? "<img src=\"images/verde.gif\">" : "<img src=\"images/rosso.gif\">" %></td>
					</tr>
<%
				}
			} 
			else 
			{
%>
				<!-- metto comportamento dummy per il bottone di reportistica -->
				<br><br>
				<tr class="containerBody">
					<td colspan="11">NON ESISTE UNO STORICO PER QUESTA MOVIMENTAZIONE</td>
				</tr>
<%
			}
%>
	</table>
	<br />
	<dhv:pagedListControl object="SearchMovimentazioniStoricoInfo" tdClass="row1" />