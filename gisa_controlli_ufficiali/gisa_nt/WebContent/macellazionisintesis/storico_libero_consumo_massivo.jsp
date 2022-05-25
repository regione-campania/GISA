<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@ include file="../initPage.jsp"%>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
 
  <%

  ArrayList<String> listaStorico = (ArrayList<String>) request.getAttribute("listaStorico"); %>
    

  
  <h4>Storico Libero Consumo Massivo</h4>
  <br/><br/>
  
  <table class="details" width="100%" cellpadding="4">
  
  <col width="20%"><col width="20%"><col width="40%"><col width="20%">
  <tr>
  <th> Data operazione </th>
  <th> Matricola</th>
  <th> Note </th>
  <th> Utente </th>
  </tr>
  
  
  <% for (int i = 0; i<listaStorico.size(); i++) { 
	  String riga[]  = listaStorico.get(i).split(";;");
	  int idMacello = Integer.parseInt(riga[5]);  
	  String action ="";
	  if (idMacello<20000000)
		  action ="Macellazioni";
	  else if (idMacello<100000000)
		  action ="MacellazioniOpu";
	  else
		  action ="MacellazioniSintesis";
  %>
  <tr class="row<%=i%2%>">
  <td><%=riga[2] %> </td>
  <td><a href="#" onClick="window.open('<%=action %>.do?command=Details&id=<%=riga[0]%>&orgId=<%=riga[5]%>&popup=true')"><%=riga[4] %></a></td> 
  <td><%=riga[3]%></td>
  <td> <dhv:username id="<%=riga[1]%>"></dhv:username></td>
  </tr>
   <%} %>
  
  
  </table>
  
  

</body>
</html>