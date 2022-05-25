<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.Macellazione"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.VisitaAM"%>
<%@ include file="include.jsp" %>

              <%
              Macellazione thisMacellazione = dettaglioCapo.getDettagliMacellazione();
              
              VisitaAM visita = (dettaglioCapo.getDettagliMacellazione()).getVisitaAm();
              %>
<table width="100%" border="0" cellpadding="2" cellspacing="2"  class="details" style="border:1px solid black">
   <tr>
              <th colspan="2"><strong>Visita Ante Mortem </strong></th>
            </tr>

            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
					<%=toDateasString(visita.getDataVisitaAm()) %>
              </td>
            </tr>
           
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td>
                <%Iterator i = esitoVisitaAm.iterator();
                	while (i.hasNext()){
                		LookupElement element = (LookupElement) i.next();
                		if (element.getCode() > 0){
                	%>
						<input 
							type=radio 
							onclick="if(this.checked==true) document.getElementById('vam_tabella_non_conformita').style.display='none';gestisciObbligatorietaVisitaAnteMortem();" 
							id= "idEsitoAm" 
							name="idEsitoAm" 
							value="<%=element.getCode()%>" disabled="disabled" <%=(visita.getIdEsitoAm() == element.getCode()) ? "checked" : "" %> ><%=element.getDescription() %><br/>
<%}
                		}%>
							
							<p>
							</p>
				</td>               
            </tr>
			
            
            <tr class="containerBody">
                <td class="formLabel">Provvedimento adottato</td>
                <td>
					<%
						ProvvedimentiVAM.setJsEvent("onChange=\"javascript:displayAbbattimento();gestisciObbligatorietaVisitaAnteMortem();\"");
					%>                               
                	
	               	<%=ProvvedimentiVAM.getSelectedValue(visita.getIdProvvedimentoAdottatoVisitaAm()) %>
	               
                </td>
            </tr>
            
            
            <tr class="containerBody">
            	<td class="formLabel">Comunicazione a</td>
            	<td>
            		<p id="comunicazioneA" align="center" style="display: none;"><font color="red" >*</font></p>
            		<input 
            			type="checkbox" 
            			id="comunicazioneAslOrigineVisitaAm"
            			name="comunicazioneAslOrigineVisitaAm" 
            			disabled="disabled" <%=visita.isComunicazioneAslOrigineVisitaAm() ? "checked" : "" %>
            			/> ASL origine <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazioneProprietarioAnimaleVisitaAm"
            			name="comunicazioneProprietarioAnimaleVisitaAm" 
            			disabled="disabled" <%=visita.isComunicazioneProprietarioAnimaleVisitaAm() ? "checked" : "" %>
            			/> Proprietario animale <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazioneAziendaOrigineVisitaAm"
            			name="comunicazioneAziendaOrigineVisitaAm" 
            				disabled="disabled" <%=visita.isComunicazioneAziendaOrigineVisitaAm() ? "checked" : "" %>
            			/> Azienda di origine <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazioneProprietarioMacelloVisitaAm"
            			name="comunicazioneProprietarioMacelloVisitaAm" 
            				disabled="disabled" <%=visita.isComunicazioneProprietarioMacelloVisitaAm() ? "checked" : "" %>
            			/> Proprietario macello <br/>
            		<input 
            			type="checkbox" 
            			id="comunicazionePifVisitaAm"
            			name="comunicazionePifVisitaAm" 
            				disabled="disabled" <%=visita.isComunicazionePifVisitaAm() ? "checked" : "" %>
            			/> P.I.F. <br/>	
            		<input 
            			type="checkbox" 
            			id="comunicazioneUvacVisitaAm"
            			name="comunicazioneUvacVisitaAm" 
            				disabled="disabled" <%=visita.isComunicazioneUvacVisitaAm() ? "checked" : "" %>
            			/> U.V.A.C. <br/>	
            		<input 
            			type="checkbox" 
            			id="comunicazioneRegioneVisitaAm"
            			name="comunicazioneRegioneVisitaAm" 
            				disabled="disabled" <%=visita.isComunicazioneRegioneVisitaAm() ? "checked" : "" %>
            			/> Regione <br/>
            		<input 
            			type="checkbox"
            			id="comunicazioneAltroVisitaAm" 
            			name="comunicazioneAltroVisitaAm" 
            				disabled="disabled" <%=visita.isComunicazioneAltroVisitaAm() ? "checked" : "" %>
            			/> Altro <br/>
            		<%=visita.getComunicazioneAltroTestoVisitaAm() %>
            			
            	</td>
       
            			
            	</td>
            </tr>   
            		
            			
            	</td>
            </tr>
            
              <tr class="containerBody">
		 		<td class="formLabel">Note</td>
		 		<td><%=visita.getNoteVisitaAm() %></td>
			  </tr>
            
                 </table>
              