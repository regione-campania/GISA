<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Iterator"%>


<%if (View==null){ %>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<th colspan="4" style="background-color: rgb(204, 255, 153);" >
			<strong>
				<dhv:label name=""><center>Lista Laboratori Haccp Non In Regione Controllati</center></dhv:label>
		    </strong>
		</th>
	    <tr>
   		   <th><b><dhv:label name="">Descrizione</dhv:label></b></th>
       </tr>
   <%
   Iterator op_noreg = TicketDetails.getLabNonInRegioneControllatiList().iterator();
   if (op_noreg.hasNext()) {
     while (op_noreg.hasNext()) {
    	 //org.aspcfs.modules.vigilanza.base.Organization thisOp = (org.aspcfs.modules.vigilanza.base.Organization)op.next();
    	 org.aspcfs.modules.laboratorihaccp.base.Organization thisOpNoReg = (org.aspcfs.modules.laboratorihaccp.base.Organization)op_noreg.next();
    %> 
   <tr>
    <td>
	  <%= thisOpNoReg.getName() %>
	</td>
   </tr>
   <% } %>
       
  <% } else { %>
   <tr class="containerBody">
      <td colspan="3">
        <dhv:label name="">Nessun elenco di laboratori non in regione sottoposti a controllo.</dhv:label>
      </td>
   </tr>
   <%}%>    
 </table>	  <%}%>