<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>



<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="java.sql.*,java.util.HashMap,java.util.Map"%>
<%
	Stabilimento StabilimentoDettaglio = (Stabilimento) OperatoreDettagli
	.getListaStabilimenti().get(0);
	Toponimo sedeOperativa = StabilimentoDettaglio.getSedeOperativa();
	LineaProduttiva lp = (LineaProduttiva) StabilimentoDettaglio
	.getListaLineeProduttive().get(0);
%>

<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>


<%@page import="org.aspcfs.modules.opu.base.Operatore"%>
<%@page import="org.aspcfs.modules.opu.base.ImportatoreInformazioni"%>
<table
	cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th colspan="2"><dhv:label
			name="opu.stabilimento.linea_produttiva">
			<strong></strong>
		</dhv:label></th>
	</tr>
	<tr>
		<td colspan="2"><%=lp.getAttivita()%> <dhv:evaluate
			if="<%=(AslList.size() > 0)%>">  / Asl <%=AslList.getSelectedValue(StabilimentoDettaglio
									.getIdAsl())%>
			<%=(StabilimentoDettaglio.isFlagFuoriRegione()) ? " - Fuori regione - "
							: ""%>

		</dhv:evaluate>
	</tr>

</table>
<br>
<br>

<%
	int idRelazioneAttivita = lp.getIdRelazioneAttivita();
	
%>

<%
if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia || 
		lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile || 
		lp.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale || 
		lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore)
{
	%>
	<input type = "button" onclick="location.href='OperatoreAction.do?command=SendGisa&idRelStabLp=<%=lp.getId() %>'" value = "SINCRONIZZA IN GISA"/>
	<%
}

%>


<%
	if (lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneColonia
				&& lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazionePrivato
				&& lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindaco
			&& lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindacoFR){
%>

<%
	String label_operatore = "opu.operatore_"
					+ idRelazioneAttivita;
			String label_operatore_rag_sociale = "opu.operatore.ragione_sociale_"
					+ idRelazioneAttivita;
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><dhv:label name="<%=label_operatore%>">
			<strong></strong>
		</dhv:label></th>
	</tr>
	
	<%String ragione_sociale = ""; %>
	
	<dhv:evaluate
		if="<%=(lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile)%>">
		<%ragione_sociale = OperatoreDettagli.getRagioneSociale();
		if (((CanileInformazioni) lp).isAbusivo()) {
			ragione_sociale += " -Abusivo- ";
		}
			 if (lp.getDataFine() != null) {
				 ragione_sociale += "-Chiuso-";
		} %>
		</dhv:evaluate>
		
			<dhv:evaluate
		if="<%=(lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore || lp.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale )%>">
			<%ragione_sociale = OperatoreDettagli.getRagioneSociale();
	
			 if (lp.getDataFine() != null) {
				 ragione_sociale += "-Chiuso-";
		} %>
		</dhv:evaluate>
		
		
	<dhv:evaluate
		if="<%=!(lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile || lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore || lp.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale)%>">
		<%ragione_sociale = OperatoreDettagli.getRagioneSociale(); %>
	</dhv:evaluate>
	


	<dhv:evaluate if="<%=hasText(OperatoreDettagli.getRagioneSociale())%>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="<%=label_operatore_rag_sociale%>"></dhv:label></td>
			<td><%=toHtmlValue(ragione_sociale)%></td>
		</tr>
	</dhv:evaluate>

	<dhv:evaluate if="<%=hasText(OperatoreDettagli.getPartitaIva())%>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="opu.operatore.piva"></dhv:label></td>
			<td><%=toHtmlValue(OperatoreDettagli.getPartitaIva())%></td>
		</tr>
	</dhv:evaluate>


	<dhv:evaluate if="<%=hasText(OperatoreDettagli.getCodFiscale())%>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="opu.operatore.cf_impresa"></dhv:label></td>
			<td><%=toHtmlValue(OperatoreDettagli.getCodFiscale())%></td>
		</tr>
	</dhv:evaluate>
	
	<dhv:evaluate if="<%=hasText(lp.getTelefono1())%>">
			<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">Telefono struttura (principale)</dhv:label></td>
			<td><%=toHtmlValue(lp.getTelefono1())%></td>
		</tr>
	</dhv:evaluate>
	
	<dhv:evaluate if="<%=hasText(lp.getTelefono2())%>">
			<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">Telefono struttura (secondario)</dhv:label></td>
			<td><%=toHtmlValue(lp.getTelefono2())%></td>
		</tr>
	</dhv:evaluate>
	
		<dhv:evaluate if="<%=hasText(lp.getMail1())%>">
			<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">Email</dhv:label></td>
			<td><%=toHtmlValue(lp.getMail1())%></td>
		</tr>
	</dhv:evaluate>
	
			<dhv:evaluate if="<%=hasText(lp.getFax())%>">
			<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">Fax</dhv:label></td>
			<td><%=toHtmlValue(lp.getFax())%></td>
		</tr>
	</dhv:evaluate>
	</table>
	</br>
	<%
		}
	%>
	
	<!-- SEDE OPERATIVA -->
		<%
			if (lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindaco
						&& lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazionePrivato
						&& lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindacoFR) {
					String label_sede = "opu.stabilimento.sede_"
							+ lp.getIdRelazioneAttivita();
		%>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong><dhv:label
					name="<%=label_sede%>"></dhv:label></strong></th>
			</tr>

			<dhv:evaluate
				if="<%=(AslList.size() > 0 && !StabilimentoDettaglio
									.isFlagFuoriRegione())%>">
				<!-- tr>

					<td class="formLabel" nowrap><dhv:label
						name="opu.stabilimento.asl"></dhv:label></td>
					<td><%=AslList.getSelectedValue(StabilimentoDettaglio
									.getIdAsl())%></td>
				</tr-->
			</dhv:evaluate>
			<dhv:evaluate
				if="<%=(StabilimentoDettaglio.getSedeOperativa() != null)%>">
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="<%=label_sede%>"></dhv:label></td>
					<td><%=sedeOperativa.toString()%></td>
				</tr>
			</dhv:evaluate>
		</table>

		<%
			}
		%>
		<br>
	
	<!-- CONTROLLI UFFICIALI -->
<dhv:evaluate if="<%=(lp.getIdRelazioneAttivita()==LineaProduttiva.idAggregazioneCanile || lp.getIdRelazioneAttivita()==LineaProduttiva.idAggregazioneImportatore || lp.getIdRelazioneAttivita()==LineaProduttiva.IdAggregazioneOperatoreCommerciale || lp.getIdRelazioneAttivita()==LineaProduttiva.idAggregazioneColonia)%>">
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
		<tr>
		<th colspan="2"><dhv:label name="">
			<strong>INFORMAZIONI CONTROLLI</strong>
		</dhv:label></th>
	</tr>
<dhv:evaluate if="<%=(lp.getIdRelazioneAttivita()==LineaProduttiva.idAggregazioneImportatore || lp.getIdRelazioneAttivita()==LineaProduttiva.IdAggregazioneOperatoreCommerciale )%>">

<tr><td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.autorizzazione">Autorizzazione</dhv:label></td>
			<td><%=(lp.getAutorizzazione() != null) ? lp.getAutorizzazione() : ""%>
			</td>
		</tr>
</dhv:evaluate>
	<dhv:evaluate
		if="<%=(lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile)%>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">CONTROLLI UFFICIALI</dhv:label></td>
			<td><%="Aperti: "
						+ ((CanileInformazioni) lp).getNrControlliAperti()
						+ " Chiusi: "
						+ ((CanileInformazioni) lp).getNrControlliChiusi()%></td>
		</tr>

		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">CATEGORIA RISCHIO</dhv:label></td>
			<td><%=((CanileInformazioni) lp).getCategoriaRischio()%></td>
		</tr>
		<dhv:evaluate
			if="<%=((CanileInformazioni) lp)
										.getDataProssimoControllo() != null%>">
			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
					name="">DATA PROSSIMO CONTROLLO</dhv:label></td>
				<td><%=toDateasString(((CanileInformazioni) lp)
								.getDataProssimoControllo())%></td>
			</tr>
		</dhv:evaluate>

		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">Abusivo</dhv:label></td>
			<td><%=(((CanileInformazioni) lp).isAbusivo()) ? "Si"
						: "No"%></td>
		</tr>

		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">Centro di sterilizzazione</dhv:label></td>
			<td><%=(((CanileInformazioni) lp)
										.isCentroSterilizzazione()) ? "Si"
								: "No"%></td>
		</tr>


		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">Autorizzazione</dhv:label></td>
			<td><%=(((CanileInformazioni) lp).getAutorizzazione())%></td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">Data autorizzazione</dhv:label></td>
			<td><dhv:evaluate
				if="<%=(((CanileInformazioni) lp)
										.getDataAutorizzazione()) != null%>">
				<%=toDateasString((((CanileInformazioni) lp)
								.getDataAutorizzazione()))%>
			</dhv:evaluate></td>
		</tr>
			<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">Data chiusura</dhv:label></td>
			<td><dhv:evaluate
				if="<%=lp.getDataFine() != null%>">
				<%=toDateasString(lp.getDataFine())%>
			</dhv:evaluate></td>
		</tr>
	</dhv:evaluate>

	<dhv:evaluate
		if="<%=(lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore)%>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">CONTROLLI UFFICIALI</dhv:label></td>
			<td><%="Aperti: "
								+ ((ImportatoreInformazioni) lp)
										.getNrControlliAperti()
								+ " Chiusi: "
								+ ((ImportatoreInformazioni) lp)
										.getNrControlliChiusi()%></td>
		</tr>

		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">CATEGORIA RISCHIO</dhv:label></td>
			<td><%=((ImportatoreInformazioni) lp).getCategoriaRischio()%></td>
		</tr>
		<dhv:evaluate
			if="<%=((ImportatoreInformazioni) lp)
								.getDataProssimoControllo() != null%>">
			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
					name="">DATA PROSSIMO CONTROLLO</dhv:label></td>
				<td><%=toDateasString(((ImportatoreInformazioni) lp)
								.getDataProssimoControllo())%></td>
			</tr>

		</dhv:evaluate>
		
					
				<tr class="containerBody">
					<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
						name="">Codice UVAC</dhv:label></td>
					<td><%=((ImportatoreInformazioni) lp).getCodiceUvac()%></td>
				</tr>
		
	</dhv:evaluate>

	<dhv:evaluate
		if="<%=(lp.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale)%>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">CONTROLLI UFFICIALI</dhv:label></td>
			<td><%="Aperti: "
						+ ((OperatoreCommercialeInformazioni) lp)
								.getNrControlliAperti()
						+ " Chiusi: "
						+ ((OperatoreCommercialeInformazioni) lp)
								.getNrControlliChiusi()%></td>
		</tr>

		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="">CATEGORIA RISCHIO</dhv:label></td>
			<td><%=((OperatoreCommercialeInformazioni) lp)
								.getCategoriaRischio()%></td>
		</tr>
		<dhv:evaluate
			if="<%=((OperatoreCommercialeInformazioni) lp)
								.getDataProssimoControllo() != null%>">
			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
					name="">DATA PROSSIMO CONTROLLO</dhv:label></td>
				<td><%=toDateasString(((OperatoreCommercialeInformazioni) lp)
										.getDataProssimoControllo())%></td>
			</tr>
		</dhv:evaluate>
		
		


	</dhv:evaluate>


	<dhv:evaluate
		if="<%=(lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia)%>">

			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
					name="">CONTROLLI UFFICIALI</dhv:label></td>
				<td><%="Aperti: "
						+ ((ColoniaInformazioni) lp).getNrControlliAperti()
						+ " Chiusi: "
						+ ((ColoniaInformazioni) lp).getNrControlliChiusi()%></td>
			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
					name="">CATEGORIA RISCHIO</dhv:label></td>
				<td><%=((ColoniaInformazioni) lp)
										.getCategoriaRischio()%></td>
			</tr>
			<dhv:evaluate
				if="<%=((ColoniaInformazioni) lp)
										.getDataProssimoControllo() != null%>">
				<tr class="containerBody">
					<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
						name="">DATA PROSSIMO CONTROLLO</dhv:label></td>
					<td><%=toDateasString(((ColoniaInformazioni) lp)
								.getDataProssimoControllo())%></td>
				</tr>
			</dhv:evaluate>


			</dhv:evaluate>

			<!-- FINE CONTROLLI -->




		</table>
		
		</dhv:evaluate>
		<br />



		<!-- Sede legale -->
		<%
			if (lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneColonia) {
					String label_sede_legale = "opu.sede_legale_"
							+ idRelazioneAttivita;
		%>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><dhv:label name="">
					<strong><dhv:label name="<%=label_sede_legale%>"></dhv:label></strong>
				</dhv:label></th>
			</tr>
			<dhv:evaluate if="<%=(OperatoreDettagli.getSedeLegale() != null)%>">
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="<%=label_sede_legale%>"></dhv:label></td>
					<td>
					<%%> <%=OperatoreDettagli.getSedeLegale()
											.toString()%></td>
				</tr>
				
				
				
				
			</dhv:evaluate>
		</table>
		<br>
		<%
			}
		%>

		<%
			String label_proprietario = "opu.soggetto_fisico_"
						+ lp.getIdRelazioneAttivita();
		%>
		<dhv:evaluate if="<%=(OperatoreDettagli.getRappLegale() != null)%>">
			<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">

				<tr>
					<th colspan="2"><strong><dhv:label
						name="<%=label_proprietario%>"></dhv:label></strong></th>
				</tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.nome"></dhv:label></td>
					<td><%=OperatoreDettagli.getRappLegale().getNome()%></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.cognome"></dhv:label></td>
					<td><%=OperatoreDettagli.getRappLegale().getCognome()%></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.cf"></dhv:label></td>
					<td><%=OperatoreDettagli.getRappLegale().getCodFiscale()%></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.didentita"></dhv:label></td>
					<td><%=(OperatoreDettagli.getRappLegale().getDocumentoIdentita()!=null) ? OperatoreDettagli.getRappLegale().getDocumentoIdentita() : ""%></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.comune_nascita"></dhv:label></td>
					<td><%=OperatoreDettagli.getRappLegale().getComuneNascita()%>
					</td>
				</tr>
				<tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.provincia_nascita"></dhv:label></td>
					<td><%=OperatoreDettagli.getRappLegale()
								.getProvinciaNascita()%></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.data_nascita"></dhv:label></td>
					<td><%=toDateString(OperatoreDettagli.getRappLegale()
								.getDataNascita())%></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.sesso"></dhv:label></td>
					<td><%=OperatoreDettagli.getRappLegale().getSesso()%></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.fax"></dhv:label></td>
					<td><%=OperatoreDettagli.getRappLegale().getFax()%></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.telefono"></dhv:label></td>
					<td><%=(OperatoreDettagli.getRappLegale().getTelefono1() != null) ? OperatoreDettagli.getRappLegale().getTelefono1() : ""%></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.telefono2"></dhv:label></td>
					<td><%=(OperatoreDettagli.getRappLegale().getTelefono2() !=null) ? OperatoreDettagli.getRappLegale().getTelefono2() : ""%></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.mail"></dhv:label></td>
					<td><%=OperatoreDettagli.getRappLegale().getEmail()%></td>
				</tr>
				
				<dhv:evaluate if="<%=(lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile || lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore || lp.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale)%>">
		
					<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.indirizzo"></dhv:label>
					</td>
					<td><%=(OperatoreDettagli.getRappLegale()
											.getIndirizzo() != null) ? OperatoreDettagli
									.getRappLegale().getIndirizzo().toString()
									: ""%></td>
				</tr></dhv:evaluate>
				
				<dhv:evaluate if="<%=(lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia)%>">
		
					<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.soggetto_fisico.indirizzo"></dhv:label>
					</td>
					<td><%=(StabilimentoDettaglio.getRappLegale()
											.getIndirizzo() != null) ? StabilimentoDettaglio
									.getRappLegale().getIndirizzo().toString()
									: ""%></td>
				</tr></dhv:evaluate>
				
					</table>

				<dhv:evaluate
					if="<%=(OperatoreDettagli.getNote() != null && !OperatoreDettagli
										.getNote().equals(""))%>">
						<br></br><table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details"> <tr>
					<th colspan="2"><strong>NOTE</strong></th>
				</tr><tr>
						<td class="formLabel" nowrap><dhv:label name="Note">Note</dhv:label></td>
						<td><%=OperatoreDettagli.getNote()%></td>
					</tr></table>
				</dhv:evaluate>

		</dhv:evaluate>
		<br />
		
		<br>
		<%
			if (lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneColonia
						&& lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazionePrivato
						&& lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindaco
						&& lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindacoFR) {
					String label_responsabile_stabilimento = "opu.stabilimento.soggetto_fisico_"
							+ idRelazioneAttivita;
		%>
		<dhv:evaluate
			if="<%=(StabilimentoDettaglio.getRappLegale() != null && StabilimentoDettaglio
											.getRappLegale().getCodFiscale() != null)%>">
			<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">

				<tr>
					<th colspan="2"><strong><dhv:label
						name="<%=label_responsabile_stabilimento%>"></dhv:label></strong></th>
				</tr>
				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.stabilimento.soggetto_fisico.ragione_sociale"></dhv:label>
					</td>
					<td><%=StabilimentoDettaglio.getRappLegale()
									.getCognome() + " "
							+ StabilimentoDettaglio.getRappLegale().getNome()%></td>
				</tr>

				<tr>
					<td class="formLabel" nowrap><dhv:label
						name="opu.stabilimento.soggetto_fisico.indirizzo_residenza"></dhv:label>
					</td>
					<td><%=(StabilimentoDettaglio.getRappLegale()
											.getIndirizzo() != null) ? StabilimentoDettaglio
									.getRappLegale().getIndirizzo().toString()
									: ""%></td>
				</tr>


			</table>
			<br />
		</dhv:evaluate>
		<%
			}
		%>

	

		<%
			if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia) {
		%>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">

			<tr>
				<th colspan="2"><strong><dhv:label name="">Informazioni colonia</dhv:label></th>
			</tr>

			<tr>
				<td class="formLabel" nowrap>Data registrazione colonia</td>
				<td><%=(((ColoniaInformazioni) lp)
										.getDataRegistrazioneColonia() != null) ? toDateasString(((ColoniaInformazioni) lp)
								.getDataRegistrazioneColonia())
								: "--"%></td>
			</tr>

			<tr>
				<td class="formLabel" nowrap>Numero protocollo</td>
				<td><%=((ColoniaInformazioni) lp).getNrProtocollo()%></td>
			</tr>

			<tr>
				<td class="formLabel" nowrap>Numero totale gatti</td>
				<td><%=((ColoniaInformazioni) lp).getNrGattiTotale()%> --  Totale presunto: <%=(((ColoniaInformazioni) lp)
										.isTotalePresunto()) ? "Si" : "No"%></td>
			</tr>

			<tr>
				<td class="formLabel" nowrap>Data del censimento gatti</td>
				<td><%=(((ColoniaInformazioni) lp)
										.getDataCensimentoTotale() != null) ? toDateasString(((ColoniaInformazioni) lp)
								.getDataCensimentoTotale())
								: "--"%></td>
			</tr>
			
	

			<tr>
				<td class="formLabel" nowrap>Numero totale gatti identificati</td>
				<td><%=((ColoniaInformazioni) lp)
								.getTotaleIdentificatiSterilizzati()%></td>
			</tr>

			<tr>
				<td class="formLabel" nowrap>Numero totale gatti ancora da
				identificare</td>
				<td><%=((ColoniaInformazioni) lp)
										.getNrGattiDaCensire()%></td>
			</tr>
			
						<tr>
				<td class="formLabel" nowrap>Numero gatti sterilizzati</td>
				<td><%=((ColoniaInformazioni) lp).getNrGattiSterilizzati()%></td>
			</tr>
			
						<tr>
				<td class="formLabel" nowrap>Numero gatti non
				sterilizzati</td>
				<td><%=((ColoniaInformazioni) lp)
										.getNrGattiDaSterilizzare()%></td>
			</tr>



			<tr>
				<td class="formLabel" nowrap>Totale femmine</td>
				<td><%=((ColoniaInformazioni) lp).getNrGattiFTotale()%> -- Totale presunto: <%=(((ColoniaInformazioni) lp)
										.isTotaleFPresunto()) ? "Si" : "No"%></td>
			</tr>


			<tr>
				<td class="formLabel" nowrap>Totale maschi</td>
				<td><%=((ColoniaInformazioni) lp).getNrGattiMTotale()%> -- Totale presunto: <%=(((ColoniaInformazioni) lp)
										.isTotaleMPresunto()) ? "Si" : "No"%></td>
			</tr>


			<tr>
				<td class="formLabel" nowrap>Veterinario</td>
				<td><%=((ColoniaInformazioni) lp).getNomeVeterinario()%></td>
			</tr>
			</table>

			<%
				}
			%>




		
		
		</br></br>
		