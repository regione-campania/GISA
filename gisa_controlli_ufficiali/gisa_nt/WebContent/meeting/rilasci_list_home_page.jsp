<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@page import="java.util.Vector"%>
<%@page import="org.aspcfs.modules.meeting.base.Rilascio"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="SearchRilasciListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="listaContesti" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>



<style type="text/css">
#table-6 {
width: 100%;
border: 1px solid #B0B0B0;
}
#table-6 tbody {
/* Kind of irrelevant unless your .css is alreadt doing something else */
margin: 0;
padding: 0;
border: 0;
outline: 0;
font-size: 100%;
vertical-align: baseline;
background: transparent;
}
#table-6 thead {
text-align: left;
}
#table-6 thead th {
background: -moz-linear-gradient(top, #F0F0F0 0, #DBDBDB 100%);
background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #F0F0F0), color-stop(100%, #DBDBDB));
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#F0F0F0', endColorstr='#DBDBDB', GradientType=0);
border: 1px solid #B0B0B0;
color: #444;
font-size: 16px;
font-weight: bold;
padding: 3px 10px;
}
#table-6 td {
padding: 3px 10px;

}
#table-6 tr:nth-child(even) {
background: #F2F2F2;
}
</style>


<% ArrayList<Rilascio> RilascioList = (ArrayList<Rilascio>) request.getAttribute("listaRilasci"); %>

<% if (RilascioList.size()>0) { %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" id="table-6">
<col width="10%">
 <thead>
 <tr>
 <th colspan="5" align="center">Ultimi rilasci</th>
 </tr>
  <tr>
     <th nowrap align="center"> <strong>Data</strong> </th>
     <th nowrap align="center"> <strong>Oggetto</strong> </th>
   <th nowrap align="center"> <strong>Modulo</strong> </th>
     <th nowrap align="center"> <strong>Funzione</strong> </th>
     <th nowrap align="center"> <strong>Note</strong> </th>
     
       
    
  </tr>
  </thead>
  <tbody>
  <%
  for (Rilascio rilascio : RilascioList)
  {
	  %>
	  <tr>
	  <td > <%=toDateasString(rilascio.getData()) %> </td>
	   <td> <%=rilascio.getOggetto() %> </td>
     <td > <%=rilascio.getNoteModulo() %> </td>
      <td > <%=rilascio.getNoteFunzione() %> </td>
      <td > <%=rilascio.getNoteNote() %> </td>
      
	  </tr>
	  
	  <%
	  
  }
  %>
  </tbody>
  </table>
  
<%}%>