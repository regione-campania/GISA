<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page
	import="org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni,org.aspcfs.modules.opu.base.*"%>


<jsp:useBean id="tipoVaccinazione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tipoVaccinoInoculato"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="veterinariList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="evento"
	class="org.aspcfs.modules.registrazioniAnimali.base.Evento"
	scope="request" />

<%
	EventoInserimentoVaccinazioni eventoF = (EventoInserimentoVaccinazioni) evento;
%>
<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<jsp:useBean id="animaleDettaglio" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="pagedList">
	<th colspan="2">Dettagli della registrazione di inserimento
	vaccinazione
<%
	if(eventoF.getIdTipoVaccino()==1 && (User.getRoleId()==new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1")) || User.getRoleId()==new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2"))))
	{
%>
		-- <a href="GestioneDocumenti.do?command=GeneraPDF&tipo=PrintCertificatoVaccinazioneAntiRabbia&IdAnimale=<%=eventoF.getIdAnimale()%>&generaNonLista=ok&generazionePulita=si"
 			  target="_new">Rigenera Certificato</a>
<%
	}
%></th>

	<tr>
		<td><b><dhv:label name="">Data della vaccinazione</dhv:label></b></td>
		<td><%=toDateasString(eventoF.getDataVaccinazione())%>&nbsp;</td>
	</tr>
	<tr>
		<td width="20%"><b><dhv:label name="">Tipologia vaccinazione</dhv:label></b>
		</td>
		<td><%=tipoVaccinazione.getSelectedValue(eventoF
							.getIdTipoVaccino())%></td>
	</tr>
	<tr>
		<td width="20%"><b><dhv:label name="">Tipo vaccino inoculato</dhv:label></b>
		</td>
		<td><%=tipoVaccinoInoculato.getSelectedValue(eventoF.getIdTipologiaVaccinoInoculato())%></td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Nr. lotto vaccino</dhv:label></b></td>
		<td><%=(eventoF.getNumeroLottoVaccino() != null && !("").equals(eventoF.getNumeroLottoVaccino())) ? eventoF.getNumeroLottoVaccino() : "---"%></td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Nome vaccino</dhv:label></b></td>
		<td><%=(eventoF.getNomeVaccino() != null && !("").equals(eventoF.getNomeVaccino())) ? eventoF.getNomeVaccino() : "--"  %></td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Produttore vaccino</dhv:label></b></td>
		<td><%=(eventoF.getProduttoreVaccino() != null && !("").equals(eventoF.getProduttoreVaccino())) ? eventoF.getProduttoreVaccino() : "--"  %></td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Scadenza vaccino</dhv:label></b></td>
		<td><%=(eventoF.getDataScadenzaVaccino() != null && !("").equals(eventoF.getDataScadenzaVaccino())) ? toDateasString(eventoF.getDataScadenzaVaccino()) : "--"  %></td>
	</tr>
	
	<tr>
		<td><b><dhv:label name="">Veterinario esecutore</dhv:label></b></td>
		<td><%=(eventoF.getIdVeterinarioEsecutoreAccreditato() > 0) ? veterinariList.getSelectedValue(eventoF.getIdVeterinarioEsecutoreAccreditato()) :( (eventoF.getVeterinarioEsecutore() != null && !("").equals(eventoF.getVeterinarioEsecutore())) ? (eventoF.getVeterinarioEsecutore()) : "--")  %></td>
	</tr>
	

	
	
</table>