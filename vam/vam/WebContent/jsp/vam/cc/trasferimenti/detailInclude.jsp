<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>


		<div id="dettaglio_${tr.id }_div" title="Dettaglio Trasferimento" style="display:none;">
			<table align="center">
				<tr>
					<th id="titoloDettaglioTrasferimento">
						Dettaglio Trasferimento
					</th>
					<th>
					</th>
				</tr>
				<tr class="even">
					<td><strong>&nbsp;Identificativo Animale&nbsp;</strong></td>
					<td>&nbsp; ${tr.cartellaClinica.identificativoAnimale}&nbsp;</td>
				</tr>
				<tr class="odd">
					<td><strong>&nbsp;Data Richiesta&nbsp;</strong></td>
					<td>&nbsp;<fmt:formatDate value="${tr.dataRichiesta }" pattern="dd/MM/yyyy" />&nbsp;</td>
				</tr>
				<tr class="even">
					<td><strong>&nbsp;Motivazioni/Operazioni Richieste&nbsp;</strong></td>
					<td>&nbsp;${tr.operazioniRichieste }&nbsp;</td>
				</tr>
				<tr class="odd">
					<td><strong>&nbsp;Nota Richiesta&nbsp;</strong></td>
					<td>&nbsp;${tr.notaRichiesta }&nbsp;</td>
				</tr>
				<tr class="even">
					<td><strong>&nbsp;Stato del trasferimento&nbsp;</strong></td>
					<td>&nbsp;${tr.stato }&nbsp;</td>
				</tr>
				<tr class="odd">
					<td><strong>&nbsp;Clinica di Origine&nbsp;</strong></td>
					<td>&nbsp;(${tr.clinicaOrigine.lookupAsl }) ${tr.clinicaOrigine.nome }&nbsp;</td>
				</tr>
				<tr class="even">
					<td><strong>&nbsp;Inserito da&nbsp;</strong></td>
					<td>&nbsp;${tr.enteredBy}&nbsp;</td>
				</tr>
				<tr class="even">
					<td><strong>&nbsp;Data Approvazione CRIUV&nbsp;</strong></td>
					<td>
						&nbsp;
						<c:if test="${tr.urgenza }"><font color="red">(Richiesta in urgenza)</font></c:if>
						<c:if test="${!tr.urgenza }"><fmt:formatDate value="${tr.dataAccettazioneCriuv }" pattern="dd/MM/yyyy" /></c:if>
						&nbsp;
					</td>
				</tr>
				<tr class="odd">
					<td><strong>&nbsp;Data Rifiuto CRIUV&nbsp;</strong></td>
					<td>&nbsp;<fmt:formatDate value="${tr.dataRifiutoCriuv }" pattern="dd/MM/yyyy" />&nbsp;</td>
				</tr>
				<tr class="even">
					<td><strong>&nbsp;Nota CRIUV&nbsp;</strong></td>
					<td>&nbsp;${tr.notaCriuv }&nbsp;</td>
				</tr>
				<tr class="odd">
					<td><strong>&nbsp;Clinica di Destinazione&nbsp;</strong></td>
					<td>&nbsp;(${tr.clinicaDestinazione.lookupAsl }) ${tr.clinicaDestinazione.nome }&nbsp;</td>
				</tr>
				<tr class="even">
					<td><strong>&nbsp;Data Accettazione Clinica di Destinazione&nbsp;</strong></td>
					<td>&nbsp;<fmt:formatDate value="${tr.dataAccettazioneDestinatario }" pattern="dd/MM/yyyy" />&nbsp;</td>
				</tr>
				<tr class="odd">
					<td><strong>&nbsp;Nota Clinica di Destinazione&nbsp;</strong></td>
					<td>&nbsp;${tr.notaDestinatario }&nbsp;</td>
				</tr>
				<tr class="even">
					<td><strong>&nbsp;Data Riconsegna/Dimissione&nbsp;</strong></td>
					<td>&nbsp;<fmt:formatDate value="${tr.dataRiconsegna }" pattern="dd/MM/yyyy" />&nbsp;</td>
				</tr>
				<tr class="odd">
					<td><strong>&nbsp;Nota Riconsegna/Dimissioni&nbsp;</strong></td>
					<td>&nbsp;${tr.notaRiconsegna }&nbsp;</td>
				</tr>
				<tr class="odd">
					<td style="text-align:center;" id="footerDettaglioTrasferimento1"></br></br></br></br></br></br></br>Data</td>
					<td style="text-align:right;" id="footerDettaglioTrasferimento2"></br></br></br></br></br></br></br>Firma&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr class="odd">
					<td style="text-align:center;" id="footerDettaglioTrasferimento3"><fmt:formatDate type="date" value="<%=new Date()%>" pattern="dd/MM/yyyy" /></td>
					<td id="footerDettaglioTrasferimento4"></td>
				</tr>
				<tr class="odd">
					<td id="footerDettaglioTrasferimento5"></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></td>
					<td id="footerDettaglioTrasferimento6"></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></td>
				</tr>
			</table>
		</div>
		
		<script type="text/javascript">
			$(function() 
			{
				$( "#dettaglio_${tr.id }_div" ).dialog({
					height: 500,
					modal: true,
					autoOpen: false,
					closeOnEscape: true,
					show: 'blind',
					resizable: true,
					draggable: true,
					width: screen.width/2,
					buttons: {
						"Chiudi": function() {$( this ).dialog( "close" );},
						"Stampa": function() {
						 $( "#dettaglio_${tr.id }_div" ).jqprint(); 
						}
					}
				});
			});
		</script>
		