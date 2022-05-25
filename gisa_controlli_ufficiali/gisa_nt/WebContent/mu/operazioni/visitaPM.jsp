<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="include.jsp"%>
<table width="100%" border="0" cellpadding="2" cellspacing="2"
	class="details" style="border:1px solid black">
	
	
		<tr>
              		<th colspan="3"><strong>Macellazione</strong></th>
            	</tr>
	            <tr class="containerBody">
	              <td class="formLabel" >Progressivo</td>
	              <td >
	              	<input type="text" name="progressivoMacellazionePm" required="required" label="Progressivo macellazione" />
	              </td>
	            </tr>
	            <tr class="containerBody">
	              <td class="formLabel" >Tipo</td>
	              <%TipiMacellazione.setRequired(true); 
	              TipiMacellazione.setLabel("Tipologia Macellazione");%>
	              <td colspan="2"><%=TipiMacellazione.getHtmlSelect( "idTipoMacellazionePm",  1 ) %>
	              </td>
	            </tr>
	            
	            
	            
	<tr>
		<th colspan="2"><strong>Visita Post Mortem</strong></th>
	</tr>

	<tr class="containerBody">
		<td class="formLabel">Data Macellazione</td>
		<td><input readonly type="text" required="true" label="Data visita PM" id="dataVisitaPm" name="dataVisitaPm"
			onfocus="riportaDataArrivoMacello(this);gestisciObbligatorietaVisitaPostMortem();"
			size="10" value="" />&nbsp; <font color="red"
			id="dataVisitaPm" style="display: none;">*</font> <a href="#"
			onClick="cal19.select(document.forms[0].dataVisitaPm,'anchor19','dd/MM/yyyy'); return false;"
			NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a> <a href="#" style="cursor: pointer;"
			onclick="svuotaData(document.forms[0].dataVisitaPm);"><img
				src="images/delete.gif" align="absmiddle" /></a></td>
	</tr>

	<tr class="containerBody">
		<td class="formLabel">Esito</td>
		<td>
			<%
				EsitiVpm.setJsEvent("onChange=\"javascript:displayVPMpatologie(),displayLCSO(),resetSelect();\"");
				//EsitiVpm.setSelectSize(5);
				//EsitiVpm.setMultiple(true);
			//	EsitiVpm.setRequired(true);
				LookupList esiti = new LookupList();
				for (Esito e : (ArrayList<Esito>) Esiti) {
					esiti.addItem(e.getId_esito(), "");//add( Patologie.get( Patologie.getLevelFromId(pr.getId_patologia()) ) );
				}
			%> <%=EsitiVpm.getHtmlSelect("idEsitoPm", esiti)%>
		</td>
	</tr>

	<tr class="containerBody">
		<td class="formLabel">Data Ricezione Esito</td>
		<td>
		<input readonly type="text" id="dataRicezioneEsitoVisitaPm" name="dataRicezioneEsitoVisitaPm"
			size="10" value="" />&nbsp; <a href="#"
			onClick="cal19.select(document.forms[0].dataRicezioneEsitoVisitaPm,'anchor19','dd/MM/yyyy'); return false;"
			NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a> <a href="#" style="cursor: pointer;"
			onclick="svuotaData(document.forms[0].dataRicezioneEsitoVisitaPm);"><img
				src="images/delete.gif" align="absmiddle" /></a>
				</td>
	</tr>



	<tr style="display:none;" class="containerBody" id="vpm_riga_patologie">
		<td class="formLabel">Patologie Rilevate</td>
		<%
			Patologie.setMultiple(true);
			Patologie.setSelectSize(5);
			LookupList vpm_patologie_selezionate = new LookupList();
			for (PatologiaRilevata pr : (ArrayList<PatologiaRilevata>) PatologieRilevate) {
				vpm_patologie_selezionate.addItem(pr.getId_patologia(), "");//add( Patologie.get( Patologie.getLevelFromId(pr.getId_patologia()) ) );
			}
		%>
		<td><%=Patologie.getHtmlSelect("listaPatologieRilevate", vpm_patologie_selezionate)%></td>
	</tr>

	<tr class="containerBody">
		<td class="formLabel">Causa Presunta o Accertata<br />(per
			eventuali patologie)
		</td>
		<td><input type="text" name="causaPresuntaAccertataVisitaPm" value="" /></td>
	</tr>

	<tr class="containerBody">
		<td class="formLabel">Note</td>
		<td><textarea name="noteVisitaPm" rows="2" cols="25"></textarea></td>

	</tr>

	<tr>
		<th colspan="2">Veterinari addetti al controllo</th>
	</tr>
	<%
		HashMap<String, ArrayList<Contact>> listaVeterinari = (HashMap<String, ArrayList<Contact>>) request
				.getAttribute("listaVeterinari");
	%>
	<tr>
		<td colspan="2">1. <input value="" size="35" id="veterinario1"
			name="veterinario1" type="hidden" /> <select id="idVeterinario1Pm"
			name="idVeterinario1Pm" required="required" label="Veterinario addetto al controllo" onchange="set_vet( this, 'veterinario1'); ">
				<option value="-1">Seleziona</option>
				<%
					for (String gruppo : listaVeterinari.keySet()) {
				%>
				<optgroup label="<%=gruppo%>"></optgroup>
				<%
					for (Contact vet : listaVeterinari.get(gruppo)) {
				%>
				<option value="<%=vet.getUserId()%>"><%=vet.getNameLast()%></option>
				<%
					}
				%>
				<%
					}
				%>
		</select> <font color="red">*</font>
		</td>
	</tr>

	<tr>
		<td colspan="2">2.<input value="" size="35" id="veterinario2"
			name="veterinario2" type="hidden" /> <select id="idVeterinario2Pm"
			name="idVeterinario2Pm" onchange="set_vet( this, 'veterinario2'); ">
				<option value="-1">Seleziona</option>
				<%
					for (String gruppo : listaVeterinari.keySet()) {
				%>
				<optgroup label="<%=gruppo%>"></optgroup>
				<%
					for (Contact vet : listaVeterinari.get(gruppo)) {
				%>
				<option value="<%=vet.getUserId()%>"><%=vet.getNameLast()%></option>
				<%
					}
				%>
				<%
					}
				%>
		</select>
		</td>
	</tr>

	<tr>
		<td colspan="2">3.<input value="" size="35" id="veterinario3"
			name="veterinario3" type="hidden" /> <select id="idVeterinario3Pm"
			name="idVeterinario3Pm" onchange="set_vet( this, 'veterinario3');">
				<option value="-1">Seleziona</option>
				<%
					for (String gruppo : listaVeterinari.keySet()) {
				%>
				<optgroup label="<%=gruppo%>"></optgroup>
				<%
					for (Contact vet : listaVeterinari.get(gruppo)) {
				%>
				<option value="<%=vet.getUserId()%>"><%=vet.getNameLast()%></option>
				<%
					}
				%>
				<%
					}
				%>
		</select>
		</td>
	</tr>



<!-- 	<tr>
		<th colspan="2"><strong>Destinatari/Esercenti</strong> <input
			onclick="javascript:gestioneAddDest();" type="button" name="addDest"
			id="addDest" value="Aggiungi" /></th>
	</tr>
	<tr>
		<td colspan="2"><i>Il numero capi non &egrave; selezionabile
				se tutte le carni e visceri degli animali macellati vanno al libero
				consumo, senza distruzione di organi</i></td>
	</tr>

	<tr>
		<td colspan="2">
			<table width="100%" border="0" cellpadding="2" cellspacing="0"
				align="left">
				<tr class="containerBody">
					<td class="formLabel">In Regione</td>
					<td>Si <input type="radio" name="destinatario_1_in_regione"
						value="si" onclick="selectDestinazione(1)" id="inRegione_1" />

						No <input type="radio" name="destinatario_1_in_regione" value="no"
						onclick="selectDestinazione(1)" id="outRegione_1" />
					</td>
				</tr>

				<tr class="containerBody">
					<td class="formLabel">Destinatari/Esercenti</td>
					<td>
						<div id="imprese_1">
							<a
								href="javascript:popLookupSelectorDestinazioneCarni( 'si', 1, 'impresa' );"
								onclick="selectDestinazione(1);gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(1,1);">[Seleziona
								Impresa] </a><br /> <a
								href="javascript:popLookupSelectorDestinazioneCarni( 'si', 3, 'stab');"
								onclick="selectDestinazione(1);gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(1,2);">[Seleziona
								Stabilimento] </a><br /> <a
								href="javascript:mostraTextareaEsercente('esercenteNoGisa1');"
								onclick="selectDestinazioneFromLinkTextarea(1);gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(1,3);">[Inserisci
								Esercente non in G.I.S.A.]</a><br /> <a
								href="javascript:impostaDestinatarioMacelloCorrente(1);"
								onclick="gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(1,4);">[Macello
								corrente]</a>
							<textarea style="display: none;" rows="3" cols="36"
								id="esercenteNoGisa1" name="esercenteNoGisa1"
								onchange="valorizzaDestinatario(this,'destinatario_1');"></textarea>
						</div>
						<div id="esercenti_1">
							<a
								href="javascript:mostraTextareaEsercente('esercenteFuoriRegione1');"
								onclick="selectDestinazioneFromLinkTextarea(1);gestisciObbligatorietaVisitaPostMortem();gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(1,0);">[Inserisci
								Esercente fuori Regione]</a>
							<textarea style="display: none;" rows="3" cols="36"
								id="esercenteFuoriRegione1" name="esercenteFuoriRegione1"
								onchange="valorizzaDestinatario(this,'destinatario_1');"></textarea>
						</div> <br />
						<div id="destinatario_label_1" align="center"></div> <input
						type="hidden" name="destinatario_1_id" id="destinatario_1_id"
						value="" /> <input type="hidden" name="destinatario_1_nome"
						id="destinatario_1_nome"
						onchange="gestisciObbligatorietaVisitaPostMortem();" value="" />
						<p id="destinatarioCarni1" align="center" style="display: none;">
							<font color="red">*</font>
						</p>
					</td>
			</table>
		</td>
	</tr>
 -->


</table>
<%@include file="patologie_pm.jsp"%>
</br>
<%-- <table id="tableID1" class="details" width="100%" style="border:1px solid black">
	<tr>
		<td><%@include file="campioni_add.jsp"%>
		</td>
	</tr>
	<tr>
		<td><%@include file="tamponi_add.jsp"%>
		</td>
	</tr>
</table> --%>
