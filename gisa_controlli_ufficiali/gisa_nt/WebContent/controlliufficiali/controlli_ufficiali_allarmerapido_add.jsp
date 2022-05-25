<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>



<table cellpadding="4" cellspacing="0" width="100%" class="details"
	id="tableHidden" style="display: none">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Sistema di Allarme Rapido : Misure Riscontrate nel Corso della Verifica</dhv:label></strong>
		</th>
	</tr>

<tr class="containerBody">
		<td nowrap class="formLabel">(LDD) Nome Fornitore</td>
		<td>
		<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="nomeFornitore" name="nomeFornitore"/>
		</td>
		</tr>
		
<tr class="containerBody">
		<td nowrap class="formLabel">(LDD) Data Lista / Chiusura</td>
		<td>
		<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="dataLista" name="dataLista" />
		<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="dataChiusura" name="dataChiusura" /> 
		</td>
		</tr>
		
	<tr class="containerBody">
		<td nowrap class="formLabel">Ricevuta Comunicazione del rischio <br />
		da produttore/fornitore</td>
		<td>
		<table class="noborder">
			<tr>
				<td>Si <input type="radio" value="0"
					name="comunicazioneRischio1" 
					<%if(TicketDetails.isComunicazioneRischio()==true){ %>checked="checked"<%} %>></td>
				<td>No <input type="radio" value="1"
					name="comunicazioneRischio1" <%if(TicketDetails.isComunicazioneRischio()==false){ %>checked="checked"<%} %>>
				</td>
<!-- 				<td id="noteRischio" >Note : <textarea name="noteRischio" style="visibility: hidden;" -->
<%-- 					rows="4" cols="40"><%=toHtml(TicketDetails.getNoteRischio()) %></textarea> &nbsp; <font color="red">*</font></td> --%>


			</tr>


		</table>


		</td>
	</tr>

	<tr class="containerBody">
		<td nowrap class="formLabel">Procedure di ritiro attivate da OSA oggetto di CU</td>
		<td>
		<table class="noborder">
			<tr>
				<td>Attivate <input type="radio" value="0"
					name="procedureRitiro" checked="checked"></td>
				<td>Non Attivate <input type="radio" value="1"
					name="procedureRitiro"> </td>
				<td>L'OSA non è tenuto ad attivarle <input type="radio" value="2"
					name="procedureRitiro">
 			&nbsp; <font color="red">*</font></td>

			</tr>

		</table>

		</td>
	</tr>

	<tr class="containerBody">
		<td nowrap class="formLabel">Procedure di richiamo attivate da OSA oggetto di CU</td>
		<td>
		<table class="noborder">
			<tr>
				<td>Attivate 
				<input type="radio" value="0" name="procedureRichiamo" <%if(TicketDetails.getProceduraRichiamo()==0){ %>checked="checked"<%} %> onClick="gestisciMotivoRichiamo(0)"/>
				</td>
				<td>Non Attivate 
				<input type="radio" value="1" name="procedureRichiamo"<%if(TicketDetails.getProceduraRichiamo()==1){ %>checked="checked"<%} %> onClick="gestisciMotivoRichiamo(1)"/>
				</td>
					
				<td id ="divMotivoRichiamo"style="display:none"> 
				Motivo: <textarea name="motivoRichiamo" id="motivoRichiamo" rows="4" cols="40"></textarea>
				</td>
				
				 <td>
				<font color="red">*</font></td>

			</tr>

		</table>

		</td>
	</tr>

	<tr class="containerBody">
		<td nowrap class="formLabel">Esito del Controllo</td>
		<td>
		<table class="noborder">
			<tr>
			
				<%EsitoControllo.setJsEvent("onChange = azioneSuEsitoControllo('addticket');abilitaDestinazione('addticket')"); %>
				<td><%=EsitoControllo.getHtmlSelect("esitoControllo",TicketDetails.getEsitoControllo()) %>
				&nbsp; <input type="hidden" name="unitaMisura" id="unitaMisura"
					value="Kilogrammi"> <font
					color="red">*</font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			
			<%--7 --%>
			<tr id="hiddenEsito1" style="display: none">
				<td>Data <input type="text" name="dataddt" size="10"
					readonly="readonly" value="<%=toHtml2(TicketDetails.getDataddtTimeZone()) %>" />&nbsp;<a
					href="javascript:popCalendar('addticket','dataddt','it','IT','Europe/Berlin');"><img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmiddle"></a> con DDT Num <input type="text"
					name="numDdt"> &nbsp; <font color="red">*</font></td>
			</tr>

			<tr id="hiddenEsito2" style="display: none">
				<td>Quantità <input type="text" title="Inserire solo la Quantita (senza unita di misura) espressa nell'unità di misura di riferimento dell'allerta selezionata" name="quantita"> , : 
				<span id="misura" style="display:none"></span> Kg.  &nbsp; <font color="red">*</font></td>
			</tr>

			<tr id="hiddenEsito3" style="display: none">
				<td>Quantità Bloccata <input type="text" title="Inserire solo la Quantita (senza unita di misura) espressa nell'unità di misura di riferimento dell'allerta selezionata"
					name="quantitaBloccata"
					onchange="document.getElementById('rowAzione1').innerHTML='Quantita : '+document.addticket.quantitaBloccata.value+'  '+document.addticket.unitaMisura.value">
				, <span id="misura1"></span> &nbsp; <font color="red">*</font></td>

			</tr>


		</table>

		</td>
	</tr>
	<tr id ="partita" class="containerBody" <% if(TicketDetails.isListaDistribuzioneAllegata()==false){ %>style = "display:none" <%} %>>
		<td nowrap class="formLabel">Ulteriore Distribuzione della Partita</td>
			<td><%DestinazioneDistribuzione.setSelectStyle("display:none");%>
			<%=DestinazioneDistribuzione.getHtmlSelect("destinazioneDistribuzione",TicketDetails.getDestinazioneDistribuzione()) %>
			<font color = "red">*</font>
			</td>
	</tr>
	
	<%--
  if(TicketDetails.isListaDistribuzioneAllegata()==false)
	  {
	  
	  %>

	<tr class="containerBody" id="hidden3" style="display: none">
		<td nowrap class="formLabel"><dhv:label
			name="sanzionia.data_richiesta">Nuova Lista di Distribuzione</dhv:label>
		</td>
		<td>
		<a href = "javascript:openUploadListaPopUpDocumentale(<%=OrgDetails.getOrgId() %>,-1,'ListaDistribuzione')">Allega lista di Distribuzione</a>
		
		<input type="hidden" name="dosubmit" value="true" /> <input
			type="hidden" id = "id" name="id" value="<%= OrgDetails.getOrgId() %>" /> <input
			type="hidden" name="gotoPage" value="insert" /> 
			<input type="hidden" id = "folderId" name="folderId" value="<%= "-1" %>" />
			<input type="hidden" name="idFile" id = "idFile" value="<%=fileItem.getId() %>">
			<input type="hidden" name="fileAllegare" id = "fileAllegare" value="<%=fileItem.getId() %>">
			<input type="hidden" name="isAllegato" id = "isAllegato" value="false">
			<input type="text" readonly style="border-style:none;" name="allegatoDocumentale" id = "allegatoDocumentale" value=""/>
			<label name="allegatoDocumentaleNome" id = "allegatoDocumentaleNome"></label>
			<label name="msg_file" id = "msg_file"><font color = "red">File non Allegato</font></label>
			
		</td>
	</tr>


	<%}--%>

	<tr class="containerBody">
		<td nowrap class="formLabel">Azioni Adottate</td>
		<td>
		<table class="noborder">
			<tr>
				<%AzioniAdottate.setJsEvent("onChange = azioneSuAzioniAdottate('addticket')"); %>
				<td><%=AzioniAdottate.getHtmlSelect("azioniAdottate",-1) %>
				&nbsp; <font color="red">*</font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<%--7 --%>
			<tr id="hiddenAzione1" style="display: none">
				<td><b> <span id="rowAzione1"></span> </b></td>
			</tr>

			<tr id="hiddenAzione2" style="display: none">
				<td><b>Articolo : </b><%=ArticoliAzioni.getHtmlSelect("articoliAzioni",-1) %>
				&nbsp; <font color="red">*</font></td>
			</tr>


		</table>

		</td>
	</tr>
	
	<tr class="containerBody">
		<td nowrap class="formLabel">Informazioni aggiuntive</td>
		<td> <textarea name="allertaNotes" id="allertaNotes" rows="4" cols="80"></textarea> </td>
		</tr>
	
	
	</table>