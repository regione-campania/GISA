<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script>
function fixOra (ora){

	if (ora.length==2 && !ora.includes(':'))
		ora = ora+":";
	
		return ora;
}

</script>
<div align="left">
	<img  style="display:none" src="images/tree0.gif" border="0" align="absmiddle" height="16" width="16" />
	<a style="display:none" href="javascript:popLookupSelectorCustomPdP(<%=OrgDetails.getOrgId()%>,document.forms[0].pdp);"><dhv:label name="">Aggiungi Punto di Prelievo</dhv:label></a>
</div>
<body onload="javascript:document.getElementById('dim_lab').value=0;">

<table cellpadding="4" cellspacing="0" width="70%" class="details" id="tblClone" name="tblClone">
  <tr>
    <th colspan="11">
      <strong><dhv:label name="">Punto di Prelievo</dhv:label></strong>
    </th>
  </tr>
  <tr>
  <th>Punto Prelievo Controllato</th>
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
  <tr id="riga_acque_di_rete">
	 	<td><label><%=OrgDetails.getName() %></label>
	 	<input type = "hidden" name="pdp" id="pdp" value = "<%=OrgDetails.getOrgId() %>">
	 	</td>
	 	<td><input type = "text" name ="field1_<%=OrgDetails.getOrgId()%>"></td>
    <td><input type = "text" name ="field2_<%=OrgDetails.getOrgId() %>"></td>
    <td><input type = "time" name ="field3_<%=OrgDetails.getOrgId() %>" maxlength="5" placeholder="  :  " oninput="this.value = fixOra(this.value)"></td>
    <td><input disabled type = "checkbox" id ="field4_<%=OrgDetails.getOrgId() %>" name ="field4_<%=OrgDetails.getOrgId() %>" onclick="disabilitaAltri(this)"></td>
    <td><input disabled type = "checkbox" id ="field5_<%=OrgDetails.getOrgId() %>" name ="field5_<%=OrgDetails.getOrgId() %>" onclick="disabilitaAltri(this)"></td>
    <td><input disabled type = "checkbox" id ="field6_<%=OrgDetails.getOrgId() %>" name ="field6_<%=OrgDetails.getOrgId() %>" onclick="disabilitaAltri(this)"</td>
    <td><input disabled type = "checkbox" id ="field7_<%=OrgDetails.getOrgId() %>" name ="field7_<%=OrgDetails.getOrgId() %>" onclick="disabilitaAltri(this)"></td>
    <td><input disabled type = "checkbox" id ="field10_<%=OrgDetails.getOrgId() %>" name ="field10_<%=OrgDetails.getOrgId() %>" onclick="disabilitaAltri(this)"></td>
    <td><input disabled type = "checkbox" id ="field11_<%=OrgDetails.getOrgId() %>" name ="field11_<%=OrgDetails.getOrgId() %>" onclick="disabilitaAltri(this)"></td>
    
    <td><textarea rows="4" cols="30" name="field8_<%=OrgDetails.getOrgId()%>"></textarea> </td>
<input type = "hidden" id ="field9_<%=OrgDetails.getOrgId() %>" name ="field9_<%=OrgDetails.getOrgId() %>" value="<%=OrgDetails.getTipo_struttura()%>"/>
	 	
  </tr>
</table>	


</body>
  