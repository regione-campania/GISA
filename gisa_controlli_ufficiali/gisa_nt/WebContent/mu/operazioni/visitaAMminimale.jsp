<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="include.jsp" %>
<table width="100%" border="0" cellpadding="2" cellspacing="2"  class="details" style="border:1px solid black">
   <tr>
              <th colspan="2"><strong>Visita Ante Mortem </strong></th>
            </tr>

            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
					<input label="Data visita ante mortem" required="required" readonly type="text" id="dataVisitaAm" name="dataVisitaAm" onfocus="riportaDataArrivoMacello(this);" size="10" value="<%=toDateasString(seduta.getData()) %>" />&nbsp;  
			        <font color="red" id="dataVisitaAnteMortem" style="display: none;">*</font>
			        <!-- <a href="#" onClick="cal19.select(document.forms[0].vam_data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].vam_data);"><img src="images/delete.gif" align="absmiddle"/></a> -->
              </td>
            </tr>
           
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td> 	<input 
							type="hidden" 
							id= idEsitoAm 
							name="idEsitoAm"
							value="1"/>Favorevole<br/>
							
							<p>
							</p>
				</td>               
            </tr>
			
            
            
                 </table>
              