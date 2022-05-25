<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <tr style="display:none">
        	<td colspan="2">
      
	        <b>USA SCELTA PIU' FREQUENTE</b>
	        &nbsp; 
	        <input onchange="mostra_candidati_rank('attprincipale_ranked<%=indice %>','attprincipale<%=indice %>',<%=lp.getId() %>,this,0,'<%=StabilimentoDettaglio.getTipoInserimentoScia()%>')" <%=lp.getCandidatiPerRanking() == null || lp.getCandidatiPerRanking().size() == 0 ? "disabled" : "" %> type ="checkbox" class="checkbox_rank" id="usa_rank" name="usa_rank_linea_principale<%=indice %>" value="true"/>
	        </td>
</tr>
<tr>
			 
    		 
    		<td colspan="2"><table id = "attprincipale_ranked<%=indice %>" style ="width:100%"></table></td>
</tr>