<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@page import="java.util.Vector"%>
<%@page import="org.aspcfs.modules.meeting.base.Rilascio"%>
<jsp:useBean id="RilascioList" class="org.aspcfs.modules.meeting.base.RilascioList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="SearchRilasciListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="listaContesti" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>



<table class="trails" cellspacing="0">
<tr>
<td>
<a href="GestioneRiunioni.do?command=SearchForm">Gestione Riunioni</a> > 
Risultati Ricerca
</td>
</tr>
</table>




<dhv:pagedListStatus  object="SearchRilasciListInfo"/>

<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
     <th nowrap> <strong>Data</strong> </th>
     <th nowrap> <strong>Oggetto</strong> </th>
     <th nowrap> <strong>Contesto</strong> </th>
     <th nowrap> <strong>Modulo</strong> </th>
     <th nowrap> <strong>Funzione</strong> </th>
     <th nowrap> <strong>Note</strong> </th>
     <th nowrap> <strong>Azione</strong> </th>
       
    
  </tr>
  
  <%
  for (Rilascio rilascio : (Vector<Rilascio>)RilascioList)
  {
	  
	  %>
	  <tr>
	  <td > <%=toDateasString(rilascio.getData()) %> </td>
	   <td> <%=rilascio.getOggetto() %> </td>
      <td > <%=rilascio.getNoteIdContesto() %> </td>
      <td > <%=rilascio.getNoteModulo() %> </td>
      <td > <%=rilascio.getNoteFunzione() %> </td>
      <td > <%=rilascio.getNoteNote() %> </td>
      
      <td>
      <a href="GestioneRiunioni.do?command=DettaglioRilascio&id=<%=rilascio.getId() %>">Apri</a>     </td>
      
     
	  </tr>
	  
	  <%
	  
  }
  %>
  
  </table>
  
  <dhv:pagedListControl object="SearchRilasciListInfo" tdClass="row1"/>
  
  <div id="dettaglioRilascio"></div>