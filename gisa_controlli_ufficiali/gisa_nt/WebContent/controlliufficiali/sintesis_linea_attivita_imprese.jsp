<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/ControlliUfficialiCampiAggiuntiviSintesis.js"></script>

<%@page import="org.aspcfs.utils.web.LookupList"%><div style = "display:none">
	<tr id="rigaATECO" >

          <td nowrap class="formLabel">
            <dhv:label name="">Linea Attività Sottoposta a Controllo</dhv:label>
            
          </td>
          <td>
          <%
          LookupList lookup_vuota_linea_attivita = new LookupList();
      	lookup_vuota_linea_attivita.addItem(-1, "" );
      	lookup_vuota_linea_attivita.setTableName("");
      	lookup_vuota_linea_attivita.setTable("");
      	lookup_vuota_linea_attivita.setJsEvent("onChange=\"controllaCampiAggiuntiviLinea(this.value, -1)\"");
          
          %>
				<%= lookup_vuota_linea_attivita.getHtmlSelect("id_linea_sottoposta_a_controllo" , TicketDetails.getId_imprese_linee_attivita() ) %>
    
    <label id = "lab_linea"></label>
         	<font color = "red">*</font>
          </td>
	</tr>
	
	
	<tr id="OperatoreMercato">
	<td nowrap class="formLabel"></td>
    <td></td></tr>
    
    <tr id="AutomezzoSoa">
	<td nowrap class="formLabel"></td>
    <td></td></tr>
    
	</div>