<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="ext.aspcfs.modules.apiari.base.ModelloC"%>
<%@page import="ext.aspcfs.modules.apiari.base.Operatore"%>
<jsp:useBean id="ModelloC" class="ext.aspcfs.modules.apiari.base.ModelloC"  scope="request"/>
<jsp:useBean id="ApicolturaSottospecie"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaModalita"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
	<jsp:useBean id="LookupTipoMovimentazione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaClassificazione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>	
<jsp:useBean id="OperatoreDestinazione" class="ext.aspcfs.modules.apiari.base.Operatore" scope="request"/>
	<script>
	

	function openPopupModulesPdf(orgId, ticketId){
		var res;
		var result;
			window.open('ManagePdfModules.do?command=PrintSelectedModules&orgId='+orgId+'&ticketId='+ticketId,'popupSelect',
			'height=300px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
			}
	</script>
	
	
<%@ include file="../initPage.jsp"%>

<%
	Operatore operatoreDestinazione = null;
	if(ModelloC.getApiarioDestinazione()!=null)
		operatoreDestinazione = ModelloC.getApiarioDestinazione().getOperatore(); 
	else
		operatoreDestinazione = OperatoreDestinazione; 
%>
<table class="details" style="width: 100%">
			<tr>
				<th colspan="2">DATI MOVIMENTAZIONE</th>
			<tr>
			<tr>
				<td nowrap class="formLabel">DATA</td>
				<td><%=toDateasString(ModelloC.getDataMovimentazione())%>
				</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">TIPO MOVIMENTAZIONE</td>
				<td><%=LookupTipoMovimentazione.getSelectedValue(ModelloC.getIdTipoMovimentazione())%>
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">NUM ALVEARI SPOSTATI</td>
				<td><%=ModelloC.getNumAlveariDaSpostare()%>
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">NUM SCIAMI/NUCLEI SPOSTATI</td>
				<td><%=ModelloC.getNumSciamiDaSpostare()%>
				</td>
			</tr>
<%
			//Nel caso è compravendita non si deve vedere
			//Ma se il dato sulle api regine e' stato inserito prima della modifica del flusso 109 - req.2, faccio vedere lo stesso la riga
			if(ModelloC.getIdTipoMovimentazione()!=1 || ModelloC.getNumRegineDaSpostare()>0)
			{
%>		
				<tr>
					<td nowrap class="formLabel">NUM API REGINE SPOSTATI</td>
					<td><%=ModelloC.getNumRegineDaSpostare()%>
					</td>
				</tr>
<%
			}
%>			
			<tr>
				<td nowrap class="formLabel">NUM PACCHI SPOSTATI</td>
				<td><%=ModelloC.getNumPacchiDaSpostare()%>
				</td>
			</tr>
			
			
			<tr>
				<td nowrap class="formLabel">STATO</td>
				<td><%=LookupStati.getSelectedValue(ModelloC.getStato()) %>
				</td>
			</tr>
<%
			if(ModelloC.getIdTipoMovimentazione()==3)
			{
%>
				<tr>
					<td nowrap class="formLabel">RECUPERO MATERIALE BIOLOGICO</td>
					<td><%=(ModelloC.getRecuperoMaterialeBiologico())?("SI"):("NO") %>
					</td>
				</tr>	
<%
}
%>
			
			</table>
		<br>			
<table class="details" style="width: 100%">
			<tr>
				<th colspan="2">APIARIO ORIGINE</th>
			<tr>
			<tr>
				<td nowrap class="formLabel">RAGIONE SOCIALE</td>
				<td><%=ModelloC.getApiarioOrigine().getOperatore().getRagioneSociale() %>
				</td>
			</tr>

			

			<tr>
				<td nowrap class="formLabel">CODICE AZIENDA</td>
				<td><%=ModelloC.getApiarioOrigine().getOperatore().getCodiceAzienda() %></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">PROPRIETARIO</td>
				<td>
				<%=ModelloC.getApiarioOrigine().getOperatore().getRappLegale().getCognome()+" "+ModelloC.getApiarioOrigine().getOperatore().getRappLegale().getNome() %>
 </td>
			</tr>

			
			<tr>
				<td nowrap class="formLabel">PROGRESSIVO APIARIO</td>
				<td><%=ModelloC.getApiarioOrigine().getProgressivoBDA()%>
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">INDIRIZZO APIARIO</td>
				<td><%=ModelloC.getApiarioOrigine().getSedeOperativa().getDescrizioneComune() + " "+ModelloC.getApiarioOrigine().getSedeOperativa().getVia()%>
				</td>
			</tr>

		<tr>
				<td nowrap class="formLabel">CLASSIFICAZIONE</td>
				<td><%=ApicolturaClassificazione.getSelectedValue(ModelloC.getApiarioOrigine()
							.getIdApicolturaClassificazione())%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">SOTTOSPECIE</td>
				<td><%=ApicolturaSottospecie.getSelectedValue(ModelloC.getApiarioOrigine()
							.getIdApicolturaSottospecie())%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">MODALITA</td>
				<td><%=ApicolturaModalita.getSelectedValue(ModelloC.getApiarioOrigine()
							.getIdApicolturaModalita())%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">NUMERO ALVEARI</td>
				<td><%=ModelloC.getApiarioOrigine().getNumAlveari()%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">NUMERO SCIAMI / NUCLEI</td>
				<td><%=ModelloC.getApiarioOrigine().getNumSciami()%></td>
			</tr>
			
		</table>
		
		<br>
		<table class="details" style="width: 100%">
			<tr>
				<th colspan="2"><%=(ModelloC.getIdTipoMovimentazione()!=3 && ModelloC.getIdTipoMovimentazione()!=1)?("APIARIO DESTINAZIONE"):("AZIENDA DESTINAZIONE") %></th>
			<tr>
<%
	if(ModelloC.getIdTipoMovimentazione()!=3)
	{
		String ragioneSociale = operatoreDestinazione.getRagioneSociale();
		if(ragioneSociale==null || ragioneSociale.equals("") || ragioneSociale.equalsIgnoreCase("null"))
			ragioneSociale = ModelloC.getDenominazioneApicoltore();
%>
		<tr>
			<td nowrap class="formLabel">AZIENDA</td>
			<td><%=ragioneSociale%>
			</td>
		</tr>
<%
		
	}
	else
	{
%>
		<tr>
			<td nowrap class="formLabel">CF/PARTITA IVA</td>
			<td>
				<%=ModelloC.getCfPartitaApicoltore()%>
			</td>
		</tr>
		<tr>
			<td nowrap class="formLabel">DENOMINAZIONE</td>
			<td>
				<%=ModelloC.getDenominazioneApicoltore()%>
			</td>
		</tr>
		<tr>
			<td nowrap class="formLabel">SEDE LEGALE</td>
			<td>
				<%=ModelloC.getIndirizzo_dest() + ", " + ModelloC.getComune_dest()%>
			</td>
		</tr>
<%
	}
%>
			

			
<%
	if(ModelloC.getIdTipoMovimentazione()!=3)
	{
%>
			<tr>
				<td nowrap class="formLabel">CODICE AZIENDA</td>
				<td><%=ModelloC.getCodiceAziendaDestinazione() %></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">PROPRIETARIO</td>
				<td>
				<%
				String nominativo = ModelloC.getProprietarioDestinazione();
				if(operatoreDestinazione.getCodiceAzienda()!=null)
					nominativo = operatoreDestinazione.getRappLegale().getCognome() + operatoreDestinazione.getRappLegale().getNome();
				%>
				<%=nominativo %>
 </td>
			</tr>

			<%
			if(ModelloC.getProgressivoApiarioDestinazione()!=null)
			{
			%>
			<tr>
				<td nowrap class="formLabel">PROGRESSIVO APIARIO</td>
				<td><%=ModelloC.getProgressivoApiarioDestinazione()%>
				</td>
			</tr>
			<%
			}
			%>
			
			<tr>
				<td nowrap class="formLabel">INDIRIZZO APIARIO</td>
				<td>
				<%
					String comune = ModelloC.getComune_dest();
					if(operatoreDestinazione.getCodiceAzienda()!=null)
						comune = operatoreDestinazione.getSedeLegale().getDescrizioneComune();
					String indirizzo = ModelloC.getIndirizzo_dest();
					if(operatoreDestinazione.getCodiceAzienda()!=null)
						indirizzo = operatoreDestinazione.getSedeLegale().getVia();
				%>
				<%=comune + " "+indirizzo%>
				</td>
			</tr>
<%
	}
%>

<%
				if(ModelloC.getApiarioDestinazione()!=null)
				{
				%>
		<tr>
				<td nowrap class="formLabel">CLASSIFICAZIONE</td>
				<td>
				<%=ApicolturaClassificazione.getSelectedValue(ModelloC.getApiarioDestinazione()
							.getIdApicolturaClassificazione())%>
							</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">SOTTOSPECIE</td>
				<td><%=ApicolturaSottospecie.getSelectedValue(ModelloC.getApiarioDestinazione()
							.getIdApicolturaSottospecie())%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">MODALITA</td>
				<td><%=ApicolturaModalita.getSelectedValue(ModelloC.getApiarioDestinazione()
							.getIdApicolturaModalita())%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">NUMERO ALVEARI</td>
				<td><%=ModelloC.getApiarioDestinazione().getNumAlveari()%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">NUMERO SCIAMI / NUCLEI</td>
				<td><%=ModelloC.getApiarioDestinazione().getNumSciami()%></td>
			</tr>
			<%
				}
				%>
			
		</table>