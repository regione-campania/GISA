<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 
 <%@page import="org.aspcfs.modules.campioni.base.Analita"%>
 <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="10">
      <strong><dhv:label name="">Esito Campione</dhv:label></strong>
    </th>
	</tr>
	<tr>
	 <th>Analita</th>
	 <th>Data</th>
	 <th>Esito</th>
	 <th>Motivo Respingimento</th>
	 <th>Punteggio</th>
	 <th>Responsabilita</th>
	 <th>Segnalazione Allarme Rapido</th>
	 <th>Segnalazione Informazioni</th>
	 <th>Note</th>
	 <th>&nbsp;</th>
	 </tr>
 <%
 for(Analita analita : TicketDetails.getTipiCampioni())
 {
	
		
		
	 
	 %>
	 <tr>
	 <td><%=analita.getDescrizione() %></td>
	 <td><%=toDateString(analita.getEsito_data()) %></td>
	 <td><%= toStringSpace(EsitoCampione.getSelectedValue(analita.getEsito_id()))%></td>
	 <td><%=toStringSpace(analita.getEsito_motivazione_respingimento()) %></td>
	 <td><%= analita.getEsito_punteggio() %></td>
	 <td> <%=toStringSpace(ResponsabilitaPositivita.getSelectedValue(analita.getEsito_responsabilita_positivita()))%> </td>
	 <td><%if(analita.isEsito_allarme_rapido()){%><input type="checkbox" checked disabled><%}else{%><input type="checkbox" disabled><%}%></td>
	 <td><%if(analita.isEsito_segnalazione_informazioni()){%><input type="checkbox" checked disabled><%}else{%><input type="checkbox" disabled><%}%></td>
	 <td> <%= toStringSpace(analita.getEsito_note_esame()) %></td>
	
	 <%
	 if (analita.getEsito_data()==null || analita.getEsito_id()<=0)
		{
			out.print("<td><a href=\"#\" onclick=\"openPopUp('Campioni.do?command=ModifyTicketEsito&idControlloUfficiale="+TicketDetails.getIdControlloUfficiale()+"&id="+TicketDetails.getId()+"&id_analita="+analita.getIdAnalita()+"')\">Inserisci Esito</a></td></tr>");
		}
		else
		{
			out.print("<td><font color='green'>Esito Inserito</font></td></tr>");
		}
	 %>
	
	 
	 </tr>
	 <%
 }
 %>

 
