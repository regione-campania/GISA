<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.registrazioniAnimali.base.EventoRestituzioneAProprietario,org.aspcfs.modules.opu.base.*"%>



<jsp:useBean id="evento" class="org.aspcfs.modules.registrazioniAnimali.base.Evento" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%EventoRestituzioneAProprietario eventoF = (EventoRestituzioneAProprietario) evento; %>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>



<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
<th colspan="2">Dettagli della restituzione</th>

    <tr>  <td><b><dhv:label name="">Data restituzione</dhv:label></b></td>
      	  <td >
      	<%=toDateasString(eventoF.getDataRestituzione())%>&nbsp;
      </td></tr>

	<tr><td><b><dhv:label name="">Dati vecchio detentore</dhv:label></b></td>
  	<td>
  	  	<% 
 	  
 	  Operatore detentoreO = eventoF.getOldDetentore();
 	  if (detentoreO != null) { 
        Stabilimento stab = (Stabilimento) (detentoreO.getListaStabilimenti().get(0));
        LineaProduttiva linea = (LineaProduttiva)( stab.getListaLineeProduttive().get(0));
 	  %>
			<a href="OperatoreAction.do?command=Details&opId=<%= linea.getId() %>"><%= toHtml(detentoreO.getRagioneSociale()) %></a>
	<%}else{ %>
	--
	<%} %>
  	</td>
     </tr>
     <tr><td><b><dhv:label name="">Dati del nuovo detentore</dhv:label></b></td>
  	<td>
  	<% 
 	  
 	  Operatore detentore = eventoF.getDetentore();
 	  if (detentore != null) { 
        Stabilimento stab = (Stabilimento) (detentore.getListaStabilimenti().get(0));
        LineaProduttiva linea = (LineaProduttiva)( stab.getListaLineeProduttive().get(0));
 	  %>
			<a href="OperatoreAction.do?command=Details&opId=<%= linea.getId() %>"><%= toHtml(detentore.getRagioneSociale()) %></a>
	<%}else{ %>
	--
	<%} %>
	  </td>
     </tr>
     
   <tr><td><b><dhv:label name="">Dati denuncia</dhv:label></b></td>
  	<td>
  	<%=(eventoF.getDenuncia() != null) ? eventoF.getDenuncia() : "" %>
	  </td>
     </tr>
    <% if (eventoF.getIdEventoRitrovamento() > 0){%>
          <tr><td><b><dhv:label name="">Dettagli ritrovamento</dhv:label></b></td>
  	<td>
		<a href="RegistrazioniAnimale.do?command=Details&id=<%=eventoF.getIdEventoRitrovamento() %>&tipologiaEvento=<%=eventoF.getIdTipologiaEventoRitrovamento()%>">Dettagli ritrovamento</a>

	  </td>
     </tr>
   <%} %>

  </table>