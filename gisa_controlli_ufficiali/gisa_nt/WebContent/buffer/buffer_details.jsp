<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>

<%@page import="java.util.Date"%>

<%@page import="org.aspcfs.modules.buffer.base.Comune"%>

<jsp:useBean id="BufferDetails" class="org.aspcfs.modules.buffer.base.Buffer" scope="request"/>

<jsp:useBean id="Regioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<%@ include file="../initPage.jsp" %>


<form name="details" action="Buffer.do?command=Modify&auto-populate=true&id=<%=BufferDetails.getId() %>" method="post">
<%-- Trails --%>

<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Buffer.do?command=SearchForm">Buffer</a> >
  <a href="Buffer.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<dhv:label name="">Scheda Buffer</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>



<dhv:permission name="buffer-buffer-edit">
<%if (BufferDetails.getStato()==1)
	{
	%>
	<input type = "submit" value = "Modifica">
	<input type = "button" value = "Chiudi" onclick="location.href='Buffer.do?command=ModificaStato&id=<%=BufferDetails.getId() %>&idStato=2'">
	<%}
else
{
%>
	<input type = "button" value = "Riapri" onclick="location.href='Buffer.do?command=ModificaStato&id=<%=BufferDetails.getId() %>&idStato=1'">

<%	
}

%>
</dhv:permission>


	
<% String param1 = "id=" + BufferDetails.getId();   
%>

<dhv:container name="buffer" selected="Scheda" object="BufferDetails" param="<%= param1 %>" >

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
    </th>
  </tr>
 
 
 <tr class="containerBody">
    <td valign="top" class="formLabel">Codice Buffer</td>
    <td > <%= BufferDetails.getCodiceBuffer() %>  </td> 
    </tr>
    
     <tr class="containerBody">
    <td valign="top" class="formLabel">Descrizione in breve</td>
    <td > <%= BufferDetails.getDescrizioneBreve() %>  </td> 
    </tr>
    
      <tr class="containerBody">
    <td valign="top" class="formLabel">Data Evento</td>
    <td ><%=toDateString(BufferDetails.getDataEvento()) %> </td> 
    </tr>
    
     <tr class="containerBody">
    <td valign="top" class="formLabel">Stato</td>
    <td > <%= BufferDetails.getDescrStato() %>  </td> 
    </tr>
    
     <tr class="containerBody">
    <td valign="top" class="formLabel">Comuni Coinvolti</td>
    <td > 
   <%
				for (Comune c : BufferDetails.getListaComuni())
				{
					out.println("- "+c.getDescrizione());
				}
				%>  </td> 
    </tr>
    
     <tr class="containerBody">
    <td valign="top" class="formLabel">Note</td>
    <td > <%= BufferDetails.getNote() %>  </td> 
    </tr>
    
 
	
	</table>
</dhv:container>

<dhv:permission name="buffer-buffer-edit">
<%if (BufferDetails.getStato()==1)
	{
	%>
	<input type = "submit" value = "Modifica">
	<input type = "button" value = "Chiudi" onclick="location.href='Buffer.do?command=ModificaStato&id=<%=BufferDetails.getId() %>&idStato=2'">
	<%}
else
{
%>
	<input type = "button" value = "Riapri" onclick="location.href='Buffer.do?command=ModificaStato&id=<%=BufferDetails.getId() %>&idStato=1'">

<%	
}

%>
</dhv:permission>
	
</form>
