<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.registrazioniAnimali.base.EventoRilascioPassaporto"%>


<jsp:useBean id="evento" class="org.aspcfs.modules.registrazioniAnimali.base.Evento" scope="request"/>

<%EventoRilascioPassaporto eventoF = (EventoRilascioPassaporto) evento; %>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>



<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
<th colspan="2">Dettagli della registrazione di rinnovo / smarrimento passaporto</th>

    <tr>  <td><b><dhv:label name="">Data di rilascio</dhv:label></b></td>
      	  <td >
      	<%=toDateasString(eventoF.getDataRilascioPassaporto()) %>&nbsp;
      </td></tr>
      <tr>  <td><b><dhv:label name="">Data scadenza</dhv:label></b></td>
      	  <td >
      	<%=(eventoF.getDataScadenzaPassaporto() != null) ? toDateasString(eventoF.getDataScadenzaPassaporto()) : "--" %>&nbsp;
      </td></tr>
	<tr>  <td width="20%">
        <b><dhv:label name="">Numero del passaporto</dhv:label></b>
      </td>
       <td>
       <%=eventoF.getNumeroPassaporto() %>
  
	   </td></tr>
	  <tr>  <td width="20%">
        <b><dhv:label name="">Smarrimento</dhv:label></b>
      </td>
       <td>
       <%=(eventoF.isFlagSmarrimento()) ? "Sì" : "No" %>
  
	   </td></tr>


  </table>