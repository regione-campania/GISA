<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<div style = "display:none">
	<tr id="rigaATECO" >
        <td nowrap class="formLabel">
            <dhv:label name="">Linea Attività Sottoposta a Controllo</dhv:label>
                     
          
          </td>
          <td>
          		
          		<input type = "hidden" name = "num_linee" id = "num_linee" value = "0"/>
          	   <input type = "hidden" name = "tipo_selezione" id = "tipo_selezione" value = "false"/>
		       <table class = "noborder">
		       <tr id = "la_stab_soa" style="display: none">
		       <td>
		       <input type = "text" readonly="readonly" id ="codici_selezionabili" size = "80"  name = "codici_selezionabili" title="Qualora siano state controllate più linee attività occorre inserire controlli ufficiali (uno per ogni linea attività).">
		       <br>
		         <input type = "text" name = "alertText" id = "alertText" value = "<%=toHtml(TicketDetails.getDescrizioneCodiceAteco())%>" readonly="readonly" size="80" title="Qualora siano state controllate più linee attività occorre inserire controlli ufficiali (uno per ogni linea attività).">
	
		       </td>
		       </tr>
		       </table>
		        
		      <a id = "link_seleziona" href = "javascript:popLookupSelectorCustomSOACU('codici_selezionabili','alertText','lookup_codistat','','<%=OrgDetails.getOrgId() %>',document.getElementById('tipo_selezione').value);"><label id = "lab_linea">Seleziona una voce </label></a>
         	<font color = "red">*</font>
          </td>
	</tr>
	
	</div>