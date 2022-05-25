<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.acquedirete.base.InfoPdP"%>
<jsp:useBean id="PdpList" class="org.aspcfs.modules.acquedirete.base.InfoPdPList" scope="request"/>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>



<table cellpadding="4" cellspacing="0" width="70%" class="details" id="tblClone">
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
  <tr id="riga_acque_di_rete<%=(indicepdp >0) ? ""+indicepdp : ""%>">
	 	<td><label><%=pdp.getOrgdDetails().getName()%></label>
	 	<input type = "hidden" name="pdp" value = "<%=pdp.getOrgdDetails().getOrgId() %>">
	 	</td>
	 	<td><input type = "text" name ="field1_<%=pdp.getOrgdDetails().getOrgId()  %>" value="<%=pdp.getTemperatura()%>"></td>
    <td><input type = "text" name ="field2_<%=pdp.getOrgdDetails().getOrgId()  %>" value ="<%=pdp.getCloro()%>"></td>
    <td><input type = "text" name ="field3_<%=pdp.getOrgdDetails().getOrgId()  %>" value = "<%=pdp.getOre() %>"></td>
    <td><input type = "checkbox" onclick="return false;" id ="field4_<%=pdp.getOrgdDetails().getOrgId()  %>" name ="field4_<%=pdp.getOrgdDetails().getOrgId()  %>" <%=(pdp.isProt_routine()) ? "checked" : "" %>></td>
    <td><input type = "checkbox" onclick="return false;"  id ="field5_<%=pdp.getOrgdDetails().getOrgId()  %>" name ="field5_<%=pdp.getOrgdDetails().getOrgId()  %>" <%=(pdp.isProt_verifica()) ? "checked" : "" %>></td>
<!--<td><input type = "checkbox" onclick="<% if (pdp.getOrgdDetails().getTipo_struttura()==9) {%> disabilitaAltriPrimiDue(this)<%} else {%> disabilitaAltri(this)<%}%>"  id ="field6_<%=pdp.getOrgdDetails().getOrgId()  %>" name ="field6_<%=pdp.getOrgdDetails().getOrgId()  %>" <%=(pdp.isProt_replica_micro()) ? "checked" : "" %>></td>
    <td><input type = "checkbox" onclick="<% if (pdp.getOrgdDetails().getTipo_struttura()==9) {%> disabilitaAltriPrimiDue(this)<%} else {%> disabilitaAltri(this)<%}%>""  id ="field7_<%=pdp.getOrgdDetails().getOrgId()  %>" name ="field7_<%=pdp.getOrgdDetails().getOrgId()  %>" <%=(pdp.isProt_replica_chim()) ? "checked" : "" %>></td>
--> <td><input type = "checkbox" onclick="return false;"  id ="field6_<%=pdp.getOrgdDetails().getOrgId()  %>" name ="field6_<%=pdp.getOrgdDetails().getOrgId()  %>" <%=(pdp.isProt_replica_micro()) ? "checked" : "" %>></td>
    <td><input type = "checkbox" onclick="return false;"  id ="field7_<%=pdp.getOrgdDetails().getOrgId()  %>" name ="field7_<%=pdp.getOrgdDetails().getOrgId()  %>" <%=(pdp.isProt_replica_chim()) ? "checked" : "" %>></td>
    <td><input type = "checkbox" onclick="return false;"  id ="field10_<%=pdp.getOrgdDetails().getOrgId()  %>" name ="field10_<%=pdp.getOrgdDetails().getOrgId()  %>" <%=(pdp.isProt_radioattivita()) ? "checked" : "" %>></td>
    <td><input type = "checkbox" onclick="return false;"  id ="field11_<%=pdp.getOrgdDetails().getOrgId()  %>" name ="field11_<%=pdp.getOrgdDetails().getOrgId()  %>" <%=(pdp.isProt_ricerca_fitosanitari()) ? "checked" : "" %>></td>
    <td><textarea rows="4" cols="30" name="field8_<%=pdp.getOrgdDetails().getOrgId()  %>" value ="<%=pdp.getAltro() %>" ><%=pdp.getAltro() %></textarea> </td>
     <input type = "hidden" id ="field9_<%=pdp.getOrgdDetails().getOrgId() %>" name ="field9_<%=pdp.getOrgdDetails().getOrgId() %>" value="<%=pdp.getOrgdDetails().getTipo_struttura()%>"/>
	 	
  </tr>
  <%} %>
</table>	


</body>
  