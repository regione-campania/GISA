<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.acquedirete.base.InfoPdP"%>
<jsp:useBean id="PdpList" class="org.aspcfs.modules.acquedirete.base.InfoPdPList" scope="request"/>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>



<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="11">
      <strong><dhv:label name="">Punto di Prelievo</dhv:label></strong>
    </th>
  </tr>
  <tr>
  <th>Punti Prelievo Controllato</th>
    <th>Temperatura Acqua</th>
    <th>Cloro Residuo</th>
    <th>Ora Prelievo</th>
    <th>Protocollo di Routine</th>
    <th>Protocollo di Verifica</th>
    <th>Protocollo di replica(Microbiologico) </th>
    <th>Protocollo di replica(Chimico) </th>
    <th>Radioattività </th>
    <th>Ricerca fitosanitari </th>
    <th>Altro</th>
  </tr>  
  <%for ( int indicepdp = 0 ; indicepdp<PdpList.size() ; indicepdp ++)
	  {
	  InfoPdP pdp = (InfoPdP) PdpList.get(indicepdp);
	  %>
  <tr id="riga_acque_di_rete<%=indicepdp%>">
	 	<td><label><%=pdp.getOrgdDetails().getName()%></label>
	 	<input type = "hidden" name="pdp" value = "<%=pdp.getOrgdDetails().getOrgId() %>">
	 	</td>
	 	<td><%=pdp.getTemperatura()%> &nbsp;</td>
    <td><%=pdp.getCloro()%> &nbsp;</td>
    <td><%=pdp.getOre() %> &nbsp;</td>
    <td><input type = "checkbox" disabled="disabled" name ="field4_" <%=(pdp.isProt_routine()) ? "checked" : "" %>> &nbsp;</td>
    <td><input type = "checkbox" disabled="disabled" name ="field5_" <%=(pdp.isProt_verifica()) ? "checked" : "" %>> &nbsp;</td>
    <td><input type = "checkbox" disabled="disabled" name ="field6_" <%=(pdp.isProt_replica_micro()) ? "checked" : "" %>> &nbsp;</td>
    <td><input type = "checkbox" disabled="disabled" name ="field7_" <%=(pdp.isProt_replica_chim()) ? "checked" : "" %>> &nbsp;</td>
    <td><input type = "checkbox" disabled="disabled" name ="field10_" <%=(pdp.isProt_radioattivita()) ? "checked" : "" %>> &nbsp;</td>
    <td><input type = "checkbox" disabled="disabled" name ="field11_" <%=(pdp.isProt_ricerca_fitosanitari()) ? "checked" : "" %>> &nbsp;</td>
    <td><textarea rows="4" disabled="disabled" cols="30" name="field8_" ><%=pdp.getAltro() %></textarea>  &nbsp;</td>
	 	
  </tr><%} %>
</table>	


</body>
  