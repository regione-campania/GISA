<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="Richiesta" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request" />


	<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">
				<tr>
					<th colspan="2"><strong>Validazione Richiesta <%=Richiesta.getOperatore().getDescrizioneOperazione() %></strong>
					</th>
				</tr>
				<tr>
					<td class="formLabel">Id Richiesta</td>
					<td style="background-color: lightgray;">${Richiesta.idOperatore}</td>
				</tr>
				<tr>
					<td class="formLabel">Partita Iva</td>
					<td style="background-color: lightgray;">${Richiesta.operatore.partitaIva}</td>
				</tr>
				<tr>
					<td class="formLabel">Codice Fiscale</td>
					<td style="background-color: lightgray;">${Richiesta.operatore.codFiscale}</td>
				</tr>
				<tr>
					<td class="formLabel">Rapp Legale</td>
					<td style="background-color: lightgray;">${Richiesta.operatore.rappLegale.nome}
						${Richiesta.operatore.rappLegale.cognome}
						${Richiesta.operatore.rappLegale.codFiscale}</td>
				</tr>
				<tr>
					<td class="formLabel">Sede Legale</td>
					<td style="background-color: lightgray;">${Richiesta.operatore.sedeLegaleImpresa.descrizioneComune}
						${Richiesta.operatore.sedeLegaleImpresa.via}</td>
				</tr>

				<tr>
					<td class="formLabel">Sede Operativa</td>
					<td style="background-color: lightgray;">${Richiesta.sedeOperativa.descrizioneComune}
						${Richiesta.sedeOperativa.via}</td>
				</tr>


				<tr>
					<td class="formLabel">Ragione Sociale</td>
					<td style="background-color: lightgray;"><%=Richiesta.getOperatore().getRagioneSociale() %></td>
				</tr>


<%if (Richiesta.getNumeroRegistrazioneVariazione()!=null) {%>
				<tr>
					<td class="formLabel">Numero Registrazione Variazione</td>
					<td style="background-color: lightgray;">
						<p id="codRegText"><%=Richiesta.getNumeroRegistrazioneVariazione() %>
						<p />

					</td>
				</tr>
				<%} %>
				<%if (Richiesta.getPartitaIvaVariazione()!=null) {%>
				<tr>
					<td class="formLabel">Partita Iva Variazione</td>
					<td style="background-color: lightgray;">
						<p id="codRegText"><%=Richiesta.getPartitaIvaVariazione()%>
						<p />

					</td>
				</tr>
				<%} %>
</table>