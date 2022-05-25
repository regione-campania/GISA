<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.registrazioniAnimali.base.EventoRitrovamento,org.aspcfs.modules.opu.base.*"%>

<jsp:useBean id="tipoSoggettoSterilizz" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="evento" class="org.aspcfs.modules.registrazioniAnimali.base.Evento" scope="request"/>

<%EventoRitrovamento eventoF = (EventoRitrovamento) evento; %>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>



<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
<th colspan="2">Dettagli della registrazione di ritrovamento</th>

    <tr>  <td><b><dhv:label name="">Data del ritrovamento</dhv:label></b></td>
      	  <td >
      	<%=toDateasString(eventoF.getDataRitrovamento())%>&nbsp;
      </td></tr>
	<tr>  <td width="20%">
        <b><dhv:label name="">Luogo del ritrovamento</dhv:label></b>
      </td>
       <td>
       <%=(eventoF.getLuogoRitrovamento() != null) ? eventoF.getLuogoRitrovamento() : "---"%>
  
	   </td></tr>
	<tr><td><b><dhv:label name="">Comune del ritrovamento</dhv:label></b></td>
  	<td>
  	<%=comuniList.getSelectedValue(eventoF.getIdComuneRitrovamento())%>
  	</td>
     </tr>
     <tr><td><b><dhv:label name="">Proprietario/Detentore a</dhv:label></b></td>
  	<td>
  	<% 
 	  
 	  Operatore proprietario = eventoF.getIdProprietario(EventoRitrovamento.proprietarioNew);
 	  if (proprietario != null) { 
        Stabilimento stab = (Stabilimento) (proprietario.getListaStabilimenti().get(0));
        LineaProduttiva linea = (LineaProduttiva)( stab.getListaLineeProduttive().get(0));
 	  %>
			<a href="OperatoreAction.do?command=Details&opId=<%= linea.getId() %>"><%= toHtml(proprietario.getRagioneSociale()) %></a>
	<%}else{ %>
	--
	<%} %>/
	  <% 
	  Operatore detentore = eventoF.getIdProprietario(EventoRitrovamento.detentoreNew);
	  if (detentore != null) { 
	        Stabilimento stab1 = (Stabilimento) (detentore.getListaStabilimenti().get(0));
	        LineaProduttiva linea1 = (LineaProduttiva)( stab1.getListaLineeProduttive().get(0));
	  %>
		<a href="OperatoreAction.do?command=Details&opId=<%= linea1.getId() %>"><%= toHtml(detentore.getRagioneSociale()) %>&nbsp;</a>
	 <%}
	  else{ %>  
	--
	 <%} %>
	  </td>
  	</td>
     </tr>
      <tr><td><b><dhv:label name="">Proprietario/Detentore da</dhv:label></b></td>
  	<td>
  	<% 
 	  
 	  Operatore proprietarioOLD = eventoF.getIdProprietario(EventoRitrovamento.proprietarioOld);
 	  if (proprietarioOLD != null) { 
        Stabilimento stab = (Stabilimento) (proprietarioOLD.getListaStabilimenti().get(0));
        LineaProduttiva linea = (LineaProduttiva)( stab.getListaLineeProduttive().get(0));
 	  %>
			<a href="OperatoreAction.do?command=Details&opId=<%= linea.getId() %>"><%= toHtml(proprietarioOLD.getRagioneSociale()) %></a>
	<%}else{ %>
	--
	<%} %>/
	  <% 
	  Operatore detentoreOLD = eventoF.getIdProprietario(EventoRitrovamento.detentoreOld);
	  if (detentoreOLD != null) { 
	        Stabilimento stab1 = (Stabilimento) (detentoreOLD.getListaStabilimenti().get(0));
	        LineaProduttiva linea1 = (LineaProduttiva)( stab1.getListaLineeProduttive().get(0));
	  %>
		<a href="OperatoreAction.do?command=Details&opId=<%= linea1.getId() %>"><%= toHtml(detentoreOLD.getRagioneSociale()) %>&nbsp;</a>
	 <%}
	  else{ %>  
	--
	 <%} %>
	  </td>
  	</td>
     </tr>


  </table>