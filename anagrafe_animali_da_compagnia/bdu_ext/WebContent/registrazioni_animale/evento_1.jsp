<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page
	import="org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU,org.aspcfs.modules.opu.base.*"%><jsp:useBean
	id="tipoSoggettoSterilizz" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="listaPratiche" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
	<jsp:useBean id="provinceList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	<jsp:useBean id="regioniList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
	<jsp:useBean id="nazioniList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
	
<jsp:useBean id="evento"
	class="org.aspcfs.modules.registrazioniAnimali.base.Evento"
	scope="request" />

<%
	EventoRegistrazioneBDU eventoF = (EventoRegistrazioneBDU) evento;
%>
<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<%@page
	import="org.aspcfs.modules.registrazioniAnimali.base.EventoSterilizzazione"%>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="pagedList">
	<th colspan="2">Dettagli della registrazione di inserimento in BDU</th>

	<tr>
		<td><b><dhv:label name="">Data dell'inserimento</dhv:label></b></td>
		<td><%=toDateasString(eventoF.getDataRegistrazione())%>&nbsp;</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Proprietario/Detentore destinatari registrazione</dhv:label></b></td>
		<td>
			<%
				Operatore proprietario = eventoF.getIdProprietarioOriginarioRegistrazione();
				if (proprietario != null) {
					Stabilimento stab = (Stabilimento) (proprietario.getListaStabilimenti().get(0));
					LineaProduttiva linea = (LineaProduttiva) (stab.getListaLineeProduttive().get(0));
			%> <a
			href="OperatoreAction.do?command=Details&opId=<%=linea.getId()%>"><%=toHtml(proprietario.getRagioneSociale())%></a>
			<%
				} else {
			%> -- <%
				}
			%>/ <%
				Operatore detentore = eventoF.getIdDetentoreOriginarioRegistrazione();
				if (detentore != null) {
					Stabilimento stab1 = (Stabilimento) (detentore.getListaStabilimenti().get(0));
					LineaProduttiva linea1 = (LineaProduttiva) (stab1.getListaLineeProduttive().get(0));
			%> <a
			href="OperatoreAction.do?command=Details&opId=<%=linea1.getId()%>"><%=toHtml(detentore.getRagioneSociale())%>&nbsp;</a>
			<%
				} else {
			%> -- <%
				}
			%>
		</td>
		</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Attivit&agrave; in anagrafe itinerante</dhv:label></b></td>
		<td><%=(eventoF.isFlagAttivitaItinerante()) ? "SI" : "NO"%></td>
		<% if (eventoF.isFlagAttivitaItinerante()){%>
			<tr>
				<td><b><dhv:label name="">Data svolgimento attivit&agrave;</dhv:label></b></td>
				<td><%=toDateasString(eventoF.getDataAttivitaItinerante())%>&nbsp;
				</td>
			</tr>
			<tr>
				<td><b><dhv:label name="">Comune svolgimento attivit&agrave;</dhv:label></b></td>
				<td><%=comuniList.getSelectedValue(eventoF.getIdComuneAttivitaItinerante())%>
				</td>
			</tr>
			<tr>
				<td><b><dhv:label name="">Luogo svolgimento attivit&agrave;</dhv:label></b></td>
				<td><%=eventoF.getLuogoAttivitaItinerante()%></td>
			</tr>
		<%} %>
	</tr>
	
	
	
	
				<!-- GESTIONE ORIGINE ANIMALE -->
			<tr>
				<th colspan="2"><strong>Dati provenienza animale mai anagrafato</strong>
				</th>
			
			</tr>
			<% boolean origine = false; 
			if (eventoF.getProvenienza_origine()!=null && !eventoF.getProvenienza_origine().equals("")){ origine=true; %>
				<tr>
					<td><dhv:label name="">Origine animale</dhv:label></td>
					<td><%=(eventoF.getProvenienza_origine().contains("in") ? "In regione" : "Fuori regione")%></td>
				</tr>
			<% } %>
			
			
			<% if(eventoF.getIdProprietarioProvenienza()>0){ origine=true;%>
				<tr>
					<td><dhv:label name="">Provienienza</dhv:label></td>
					<td>Da proprietario</td>
				</tr>
				<tr>
					<td><dhv:label name="">Proprietario</dhv:label></td>
					<td>
					<% Operatore proprietarioProvenienza = eventoF.getIdProprietarioProvenienzaOp();
					if (proprietarioProvenienza != null) {
						if (proprietarioProvenienza.getIdOperatore()<10000000){
							Stabilimento stab = (Stabilimento) (proprietarioProvenienza.getListaStabilimenti().get(0));
							LineaProduttiva linea = (LineaProduttiva) (stab.getListaLineeProduttive().get(0));
							%> 
							<a href="OperatoreAction.do?command=Details&opId=<%=linea.getId()%>"><%=toHtml(proprietarioProvenienza.getRagioneSociale())%></a><br>
							<%
						}else{ %>
							<%=toHtml(proprietarioProvenienza.getRagioneSociale())%><br>
					<%	}
					} %></td></tr>
			<% } %>

			<% if (eventoF.getData_ritrovamento()!= null && !eventoF.getData_ritrovamento().equals("")){ origine=true;%>
			<tr>
				<td><dhv:label name="">Provienienza</dhv:label></td>
				<td>Da ritrovamento</td>
			</tr>
			<tr>
				<td><dhv:label name="">Dettaglio ritrovamento</dhv:label></td><td>
			<%	out.println("Ritrovato in "+eventoF.getIndirizzo_ritrovamento()+", "+comuniList.getSelectedValue(Integer.parseInt(eventoF.getComune_ritrovamento()))+" - "+provinceList.getSelectedValue(Integer.parseInt(eventoF.getProvincia_ritrovamento()))+" in data ");
				out.println(eventoF.getData_ritrovamento()); %></td></tr>
			<% } %>

			<% if(eventoF.isFlag_anagrafe_estera()){ origine=true; %>
				<tr>
					<td><dhv:label name="">Origine animale</dhv:label></td>
					<td>Provenienza da nazione estera</td>
				</tr>
				<tr>
					<td><dhv:label name="">Nazione di provenienza</dhv:label></td>
					<td><%	out.print(nazioniList.getSelectedValue(Integer.parseInt(eventoF.getNazione_estera()))+"<br>");
							out.print("Note: "+eventoF.getNazione_estera_note());%>
					</td>
				</tr>
			<% } %>
			
			<% if(eventoF.isFlag_anagrafe_fr()){ origine=true;%>
				<tr>
					<td><dhv:label name="">Proveniente da anagrafe altra regione</dhv:label></td>
					<td><% out.print("Regione "+regioniList.getSelectedValue(Integer.parseInt(eventoF.getRegione_anagrafe_fr()))+"<br>");
						   out.print("Note: "+eventoF.getRegione_anagrafe_fr_note());	%>
					</td>
				</tr>
			<% } %>

			<% if(eventoF.isFlag_acquisto_online()){ origine=true;%>
				<tr>
					<td><dhv:label name="">Acquisto online tramite</dhv:label></td>
					<td><% out.print(eventoF.getSito_web_acquisto());	%>
					</td>
				</tr>
			<% } %>
			
			<% if (!origine){ %>
				<tr>
					<td colspan="2">Informazioni mancanti sull'origine dell'animale</td>
				</tr>
			<% } %>
			<!-- FINE ORIGINE -->			
</table>