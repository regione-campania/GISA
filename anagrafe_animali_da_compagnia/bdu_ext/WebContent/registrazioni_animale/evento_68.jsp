<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.registrazioniAnimali.base.EventoAggressione,org.aspcfs.modules.opu.base.*"%>


<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="evento"
	class="org.aspcfs.modules.registrazioniAnimali.base.Evento"
	scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
<jsp:useBean id="tipologieMorso" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tipologieMorsoRipetuto" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tipologieRilievi" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tipologieAnalisiGestione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="prevedibilitaEvento" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="taglieAggressore" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="categorieVittimaAggressione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="taglieVittima" class="org.aspcfs.utils.web.LookupList" scope="request" />

<%
EventoAggressione eventoF = (EventoAggressione) evento;
%>
<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>



<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="pagedList">
	<th colspan="2">Dettagli dell'evento di aggressione -- 
	
					<a href="#"
					onclick="openRichiestaPDF('PrintCertificatoAggressione', '<%=eventoF.getIdAnimale()%>','<%=eventoF.getSpecieAnimaleId()%>', '-1', '-1', '<%=eventoF.getIdEvento() %>');"
					id="" target="_self">Stampa certificato</a> --</th>

	<tr>
		<td><b><dhv:label name="">Data aggressione</dhv:label></b></td>
		<td><%=toDateasString(eventoF.getDataAggressione())%>&nbsp;</td>
	</tr>
	
	
	<tr>
		<td><b><dhv:label name="">Tipologia</dhv:label></b></td>
		<td><%=tipologieMorso.getSelectedValue(eventoF.getTipologia())%>&nbsp;</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Prevedibilità evento</dhv:label></b></td>
		<td><%=prevedibilitaEvento.getSelectedValue(eventoF.getPrevedibilitaEvento())%>&nbsp;</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Aggressione ripetuta</dhv:label></b></td>
		<td><%=tipologieMorsoRipetuto.getSelectedValue(eventoF.getAggressioneRipetuta())%>&nbsp;</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Taglia aggressore</dhv:label></b></td>
		<td><%=taglieAggressore.getSelectedValue(eventoF.getTagliaAggressore())%>&nbsp;</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Categoria vittima</dhv:label></b></td>
		<td><%=categorieVittimaAggressione.getSelectedValue(eventoF.getCategoriaVittima())%>&nbsp;</td>
	</tr>
<%
	if(eventoF.getTipologia()==1)
	{
%>
	<tr>
		<td><b><dhv:label name="">Taglia vittima</dhv:label></b></td>
		<td><%=taglieVittima.getSelectedValue(eventoF.getTagliaVittima())%>&nbsp;</td>
	</tr>
<%
	}
%>
	<tr>
		<td><b><dhv:label name="">Rilievi sull'aggressore: Patologie</dhv:label></b></td>
		<td><%=tipologieRilievi.getSelectedValue(eventoF.getPatologie())%>&nbsp;</td>
	</tr>

	<tr>
		<td><b><dhv:label name="">Rilievi sull'aggressore: Alterazioni comportamentali</dhv:label></b></td>
		<td><%=tipologieRilievi.getSelectedValue(eventoF.getAlterazioniComportamentali())%>&nbsp;</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Analisi gestione</dhv:label></b></td>
		<td><%=tipologieAnalisiGestione.getSelectedValue(eventoF.getAnalisiGestione())%>&nbsp;</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Veterinari</dhv:label></b></td>
		<td><%=eventoF.getVeterinariEstesi()%>&nbsp;</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Controllo ufficiale</dhv:label></b></td>
		<td><%=eventoF.getIdCu()%>&nbsp;</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Misure formative</dhv:label></b></td>
		<td><%=eventoF.getMisureFormative()%>&nbsp;</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Misure restrittive</dhv:label></b></td>
		<td><%=eventoF.getMisureRestrittive()%>&nbsp;</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Misure riabilitative</dhv:label></b></td>
		<td><%=eventoF.getMisureRiabilitative()%>&nbsp;</td>
	</tr>
</table>


