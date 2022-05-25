<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.microchip.base.*,java.text.DateFormat" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
</script>
<body>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  Elimina microchip
</td>
</tr>
</table>
<%-- End Trails --%>
<form action="Microchip.do?command=EseguiElimina&auto-populate=true" method="post">


<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="100%" valign="top">
    
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	  <tr>
	    <th colspan="2">
		  <strong>Elimina Microchip a priori</strong>
		</th>
	  </tr>
	  
	  <tr class="containerBody">
	    <td nowrap class="formLabel">
	 	  Inserisci Microchip
	    </td>
	    <td>
	      <input name="microchip" type="text" size="20" maxlength="15">
	    </td>
	  </tr>
	  
	  <tr class="containerBody">
	    <td nowrap class="formLabel">
	 	  Motivazione
	    </td>
	     <td>
	      <textarea rows="5" cols="50"name="motivazione"></textarea>
	    </td>
	  </tr>

	</table>
		  
    <%= showError( request, "errore") %>
    
    <% if ( request.getAttribute("ok") != null ) { %>
    	<br>
    	<%= showAttribute( request, "ok") %>
    <% } %>
    
   </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="">Esegui Elimina</dhv:label>">
</form>
</body>