<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="include.jsp" %>		
<%@page import="org.aspcfs.modules.mu.operazioni.base.Comunicazioni"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.MacellazioneMorteAnteMacellazione"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.Macellazione"%>

              <%
              Macellazione thisMacellazione = dettaglioCapo.getDettagliMacellazione();
              
              Comunicazioni visita = (dettaglioCapo.getDettagliMacellazione()).getComunicazioni();
              %>


<table width="100%" border="0" cellpadding="2" cellspacing="2"  class="details" style="border:1px solid black">
    <tbody>
   <tr>
                <th colspan="2"><strong>Comunicazioni Esterne</strong>                </th>
            </tr>
            <tr class="containerBody">
            	<td class="formLabel">Comunicazione a</td>
            	<td>
            		<p id="comunicazioneA" align="center" style="display: none;"><font color="red" >*</font></p>
            		<input 
            			type="checkbox" 
            			id="comunicazioneAslOrigineComunicazioni"
            			name="comunicazioneAslOrigineComunicazioni" 
            			disabled="disabled" <%=visita.isComunicazioneAslOrigineComunicazioni() ? "checked" : "" %>
            			/> ASL origine <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazioneProprietarioAnimaleComunicazioni"
            			name="comunicazioneProprietarioAnimaleComunicazioni" 
            			disabled="disabled" <%=visita.isComunicazioneProprietarioAnimaleComunicazioni() ? "checked" : "" %>
            			/> Proprietario animale <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazioneAziendaOrigineComunicazioni"
            			name="comunicazioneAziendaOrigineComunicazioni" 
            				disabled="disabled" <%=visita.isComunicazioneAziendaOrigineComunicazioni() ? "checked" : "" %>
            			/> Azienda di origine <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazioneProprietarioMacelloComunicazioni"
            			name="comunicazioneProprietarioMacelloComunicazioni" 
            				disabled="disabled" <%=visita.isComunicazioneProprietarioMacelloComunicazioni() ? "checked" : "" %>
            			/> Proprietario macello <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazionePifComunicazioni"
            			name="comunicazionePifComunicazioni" 
            				disabled="disabled" <%=visita.isComunicazionePifComunicazioni() ? "checked" : "" %>
            			/> P.I.F. <br/>	
            		<input 
            			type="checkbox" 
            			id="comunicazioneUvacComunicazioni"
            			name="comunicazioneUvacComunicazioni" 
            				disabled="disabled" <%=visita.isComunicazioneUvacComunicazioni() ? "checked" : "" %>
            			/> U.V.A.C. <br/>	
            		<input 
            			type="checkbox" 
            			id="comunicazioneRegioneComunicazioni"
            			name="comunicazioneRegioneComunicazioni" 
            				disabled="disabled" <%=visita.isComunicazioneRegioneComunicazioni() ? "checked" : "" %>
            			/> Regione <br/>
            		<input 
            			type="checkbox"
            			id="comunicazioneAltroComunicazioni" 
            			name="comunicazioneAltroComunicazioni" 
            				disabled="disabled" <%=visita.isComunicazioneAltroComunicazioni() ? "checked" : "" %>
            			/> Altro <br/>
            		<%=visita.getComunucazioneAltroTestoComunicazioni() %>
            			
            	</td>
            </tr>   
    
     <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
              		<%=toDateasString(visita.getDataComunicazioniEsterne()) %>
              </td>
            </tr>
            
            <tr>
            
   			<td class="formLabel">Tipo di non conformità</td>
   			<td>
                <%
                	Iterator it = visita.getListaNonConformita().iterator();
                	while (it.hasNext()){
                		
                	
                %>
			
				<p id="tipoNonConformita" align="center" style="display: none;"><font color="red" >*</font></p>
				<%=MotiviASL.getSelectedValue((Integer) it.next()) %><br>
				<%} %>
				
			</td> 
			</tr>
			
			
           <tr class="containerBody">
	           <td class="formLabel">Descrizione non conformità</td>
	           <td><%=visita.getDescrizioneNonConformitaComunicazioni() %></td>
           </tr>
           
           <tr>
           <td class="formLabel">Provvedimenti Adottati</td>
           
           
           <td>
                <%
                	Iterator itP = visita.getListaProvvedimenti().iterator();
                	while (itP.hasNext()){
                		
                	
                %>
			
				<p id="tipoNonConformita" align="center" style="display: none;"><font color="red" >*</font></p>
				<%=look_ProvvedimentiCASL.getSelectedValue((Integer) itP.next()) %><br>
				<%} %>
				
			</td> 
           
           </tr>
           
            <tr class="containerBody" id="note_prevvedimento" >
	           <td class="formLabel">Note</td>
	           <td><%=visita.getNoteProvvedimentoAdottato() %></textarea></td>
           </tr>
           
        </tbody>
   </table>
   
   <br/>
   
   <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0" style="border:1px solid black">
          <tbody>
            <tr>
              <th colspan="2"><strong>Ricezione Comunicazioni Esterne</strong></th>
            </tr>

             <tr class="containerBody">
                <td class="formLabel">Data</td>
                <td>
                	<%=toDateasString(visita.getDataRicezioneComunicazioniEsterne()) %>
                </td>
             </tr>
             
             <tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><%=visita.getNoteRicezioneComunicazioniEsterne() %></td>
             </tr>
          </tbody>
         </table>