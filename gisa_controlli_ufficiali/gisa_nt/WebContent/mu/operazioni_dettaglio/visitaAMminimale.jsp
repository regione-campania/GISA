<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.VisitaAMSemplificata"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.MacellazioneLiberoConsumo"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.Macellazione"%>
<%@ include file="include.jsp" %>
<table width="100%" border="0" cellpadding="2" cellspacing="2"  class="details" style="border:1px solid black">
   <tr>
              <th colspan="2"><strong>Visita Ante Mortem </strong></th>
            </tr>

            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
              <%
              Macellazione thisMacellazione = dettaglioCapo.getDettagliMacellazione();
              
              
              
              VisitaAMSemplificata visita = (dettaglioCapo.getDettagliMacellazione()).getVa();
              %>
					<%=toDateasString(visita.getDataVisitaAm()) %>
              </td>
            </tr>
           
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td> <%=esitoVisitaAm.getSelectedValue(visita.getIdEsitoAm()) %><br/>
							
							<p>
							</p>
				</td>               
            </tr>
			
            
            
                 </table>
              