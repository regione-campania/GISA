<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="id_tipo_evento" class="java.lang.String" scope="request"/>

<table style="top:0;left: 0;width:100%;height: 100%; " cellpadding="5">
<tr>
	<%if(id_tipo_evento.equalsIgnoreCase("1")){ %>
		<td style="vertical-align: top; position: relative;" align="center">
			<label><b>scheda stabilimento inserimento iniziale</b></label>
			<iframe scrolling="no" src="GestioneAnagraficaAction.do?command=TemplateDetailsStorico&oggetto_storico_stab=0"  id="dettaglioTemplate0" style="width: 100%; height: 100%; border: none; "  onload="this.style.height=(this.contentDocument.body.scrollHeight)+'px';"></iframe>
		</td>
	<%} else if(id_tipo_evento.equalsIgnoreCase("16")){ %>
		<td style="vertical-align: top; position: relative;" align="center">
			<label><b>scheda stabilimento aggiornata</b></label>
			<iframe scrolling="no" src="GestioneAnagraficaAction.do?command=TemplateDetailsStorico&oggetto_storico_stab=0"  id="dettaglioTemplate0" style="width: 100%; height: 100%; border: none; "  onload="this.style.height=(this.contentDocument.body.scrollHeight)+'px';"></iframe>
		</td>
	<%} else { %>
		<td style="vertical-align: top; position: relative;" align="center">
			<label><b>scheda stabilimento pre operazione</b></label>
			<iframe scrolling="no" src="GestioneAnagraficaAction.do?command=TemplateDetailsStorico&oggetto_storico_stab=0"  id="dettaglioTemplate0" style="width: 100%; height: 100%; border: none; "  onload="this.style.height=(this.contentDocument.body.scrollHeight)+'px';"></iframe>
		</td>
		<td style="vertical-align: top; position: relative;" align="center">
			<label><b>scheda stabilimento post operazione</b></label>
			<iframe scrolling="no" src="GestioneAnagraficaAction.do?command=TemplateDetailsStorico&oggetto_storico_stab=1"  id="dettaglioTemplate1" style="width: 100%; height: 100%; border: none; "  onload="this.style.height=(this.contentDocument.body.scrollHeight)+'px';"></iframe>
		</td>
	<%} %>
</tr>
</table>

