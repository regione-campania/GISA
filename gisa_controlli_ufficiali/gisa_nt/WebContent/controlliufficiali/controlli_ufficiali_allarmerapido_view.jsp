<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <%
 
 if(TicketDetails.getTipoIspezioneCodiceInterno().contains("7a"))
   {
	   %>
   
   <table cellpadding="4" cellspacing="0" width="100%" class="details"  >
	<tr>
    <th colspan="2">
      <strong>Sistema di Allarme Rapido : Misure Riscontrate nel Corso della Verifica</strong>
    </th>
	</tr>
  	 
  	 <% if (ListaDistribuzione.getId()>0) {%>
		 <tr class="containerBody" >
			<td nowrap class="formLabel">(LDD) Nome Fornitore</td>
			<td><%=ListaDistribuzione.getNome_fornitore()%>&nbsp;&nbsp;</td>
		</tr>
		 <tr class="containerBody" >
			<td nowrap class="formLabel">(LDD) Data Lista / Chiusura</td>
			<td><%=toDateasString(ListaDistribuzione.getData_lista())%> / <%=toDateasString(ListaDistribuzione.getData_chiusura())%> &nbsp;&nbsp;</td>
		</tr>
	<%} %>	
  	 
    	 <tr class="containerBody" >
    <td nowrap class="formLabel">
     	Ricevuta Comunicazione del rischio <br/> da produttore/fornitore
    </td>
    <td>
	<table class= "noborder">
	<tr>
		<td>Si <input type = "radio" disabled="disabled" value="0" name="comunicazioneRischio1" <%if(TicketDetails.isComunicazioneRischio()){ %> checked="checked"<%} %>> </td>
		<td>No <input type = "radio" disabled="disabled" value="1" name="comunicazioneRischio1" <%if(TicketDetails.isComunicazioneRischio()==false){ %> checked="checked"<%} %>> </td>
<%-- 		<%if(TicketDetails.isComunicazioneRischio())  --%>
<%-- 		{%> --%>
<%-- 		<td id="noteRischio">Note : <textarea disabled="disabled" name = "noteRischio" rows="4" cols="40"><%=TicketDetails.getNoteRischio() %></textarea> </td> --%>
<%-- 	<%} %> --%>
	
	</tr>
	
	
	</table>
	
      	
    </td>
  </tr>
  
   <tr class="containerBody" >
    <td nowrap class="formLabel">
     	Procedure di ritiro attivate da OSA oggetto di CU
    </td>
    <td>
	<table class= "noborder">
	<tr>
		<td>Attivate <input type = "radio" disabled="disabled" value="0" name="procedureRitiro" <%if(TicketDetails.getProceduraRitiro()==0){ %> checked="checked"<%} %> > </td>
		<td>Non Attivate <input type = "radio" disabled="disabled" value="1" name="procedureRitiro" <%if(TicketDetails.getProceduraRitiro()==1){ %> checked="checked"<%} %>> </td>
		<td>L'OSA non è tenuto ad attivarle <input type = "radio" disabled="disabled" value="2" name="procedureRitiro" <%if(TicketDetails.getProceduraRitiro()==2){ %>checked="checked" <%} %>></td>  
		

	</tr>

	</table>
 	
    </td>
  </tr>
  
     <tr class="containerBody" >
    <td nowrap class="formLabel">
     	Procedure di richiamo attivate da OSA oggetto di CU
    </td>
    <td>
	<table class="noborder">
			<tr>
				<td>Attivate 
				<input type="radio" disabled value="0" name="procedureRichiamo" <%if(TicketDetails.getProceduraRichiamo()==0){ %>checked="checked"<%} %> />
				</td>
				<td>Non Attivate 
				<input type="radio" disabled value="1" name="procedureRichiamo"<%if(TicketDetails.getProceduraRichiamo()==1){ %>checked="checked"<%} %> />
									
				<% if (TicketDetails.getProceduraRichiamo()==1){ %>
				Motivo: <%=TicketDetails.getMotivoProceduraRichiamo()%> 
				<%} %>
				</td>
				
			</tr>

		</table>
 	
    </td>
  </tr>
  
       <tr class="containerBody" >
    <td nowrap class="formLabel">
     	Esito del Controllo
    </td>
    <td>
    <table class= "noborder">
    <tr>
   
    <td><%=EsitoControllo.getSelectedValue(TicketDetails.getEsitoControllo()) %></td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <%--7 --%>
    <%if(TicketDetails.getEsitoControllo()==7){ %>
    <tr id = "hiddenEsito1" >
    <td>Data  
     <%=toHtml(TicketDetails.getDataddtTimeZone()) %>
     con DDT Num  <%=TicketDetails.getNumDDt() %>
  </td>
    </tr>
   <tr id = "hiddenEsito2" >
    <td> <b> Quantità Partita : </b> <%=toHtml(TicketDetails.getQuantitaPartita()) %> Unita Misura : <%=TicketDetails.getUnitaMisura() %>
  </td>
    </tr>
    <%}%>
        
    <%if(TicketDetails.getEsitoControllo()==8 || TicketDetails.getEsitoControllo()==4 || TicketDetails.getEsitoControllo()==5 || TicketDetails.getEsitoControllo()==6 ){ %>
    
    <tr id = "hiddenEsito2" >
    <td> <b> Quantità Partita : </b> <%=toHtml(TicketDetails.getQuantitaPartita()) %> Unita Misura : <%=TicketDetails.getUnitaMisura() %>
  </td>
    </tr>
    
    <%}
    if(TicketDetails.getEsitoControllo()==10 || TicketDetails.getEsitoControllo()==11 || TicketDetails.getEsitoControllo()==14) { %>
 
    
     <tr id = "hiddenEsito3" >
    <td><b> Quantità Partita Bloccata: </b> <%=toHtml(TicketDetails.getQuantitaBloccata()) %>  Unita Misura : <%=TicketDetails.getUnitaMisura() %>
  </td>
    </tr>
    <%} %>
    
    </table>
 	
    </td>
  </tr>
  
  <%if(TicketDetails.getEsitoControllo()==13 || TicketDetails.getEsitoControllo()==14){ %>
    <tr class="containerBody" >
    <td nowrap class="formLabel" id = "partita">
     	Ulteriore Distribuzione della Partita
    </td>
    <td><b>Destinazione : </b><%=DestinazioneDistribuzione.getSelectedValue(TicketDetails.getDestinazioneDistribuzione()) %></td>
   
    </tr>
    <%} %>
  
   <%--
  
  
  if(TicketDetails.isListaDistribuzioneAllegata()==true)
	{
	  
	  %>
  
   <tr class="containerBody" id = "hidden3"  >
    <td nowrap class="formLabel">
      <dhv:label name="sanzionia.data_richiesta">Nuova Lista di Distribuzione Allegata</dhv:label>
    </td>
    <td>
    <table class = "noborder">
    <tr><td>
 		
  <a href="AccountsDocuments.do?command=Download&orgId=<%= OrgDetails.getOrgId() %>&fid=<%= fileItem.getId() %>&ver=<%= fileItem.getVersion() %><%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>">
       <%=fileItem.getSubject() %> </a> 
       <input type = "hidden" name = "idFile" value = "<%=fileItem.getId() %>">     	
    </td>
   
    
    </tr></table></td>
  </tr>
     
     
     <%}--%>
     
     
     <%--
  
  
  if(TicketDetails.getHeaderAllegatoDocumentale()!=null)
	{
	  
	  %>
  
   <tr class="containerBody" id = "hidden3"  >
    <td nowrap class="formLabel">
      <dhv:label name="sanzionia.data_richiesta">Nuova Lista di Distribuzione Allegata</dhv:label>
    </td>
    <td>
    <table class = "noborder">
    <tr><td>
 	<a href="GestioneAllegatiUpload.do?command=DownloadPDF&codDocumento=<%=TicketDetails.getHeaderAllegatoDocumentale()%>&nomeDocumento=ListaDistribuzione">Download</a>
 	
     
    </td>
   
    
    </tr></table></td>
  </tr>
     
     
     <%}--%>
     
   
  
  <%
  HashMap <Integer,String> azioni = TicketDetails.getAzioniAdottate();
Iterator<Integer> itAzioni = azioni.keySet().iterator();
  boolean isArticolo = false ;
  boolean isQuantita = false ;
  %>
  <tr class="containerBody" >
    <td nowrap class="formLabel">
     	Azioni Adottate
    </td>
    <td>
    <table class= "noborder">
    <tr>
    <td> 
    <%
    while(itAzioni.hasNext())
    {
    	int key = itAzioni.next();
    	String value = TicketDetails.getAzioniAdottate().get(key);
    	if(key == 3 )
    	{
    		isArticolo = true ;
    	}
    	if(key==2)
    	{
    		isQuantita=true;
    	}
    out.println(" - "+ value +"<br/>");
    	
 	} %>
    </td>
    </tr>
    
     <%if(isQuantita == true)
    {%>
    <tr id = "hiddenAzione2" >
    <td> 
    <br/><br/>
    <%if (!"".equals(TicketDetails.getQuantitaBloccata())){ %>
    <b> Quantità Bloccata </b><%=toHtml(TicketDetails.getQuantitaBloccata())%> <%=TicketDetails.getUnitaMisura() %>
  	<%}else
  		{%>
  		    <b> Quantità </b><%=toHtml(TicketDetails.getQuantitaPartita())%> <%=TicketDetails.getUnitaMisura() %>
  		
  		<%} %>
  	</td>
    </tr>
   <%} %>
    
   
<%--     <%if(isArticolo == true) --%>
<%--     {%> --%>
<!--     <tr id = "hiddenAzione2" > -->
<!--     <td>  -->
<!--     <br/><br/> -->
<%--     <b> Articolo : </b><%=ArticoliAzioni.getSelectedValue(TicketDetails.getAzioneArticolo())%> --%>
<!--   	</td> -->
<!--     </tr> -->
<%--    <%} --%>
    
<%--     %> --%>
    
    </table>
 	
    </td>
  </tr>
	
	<tr class="containerBody">
		<td nowrap class="formLabel">Informazioni aggiuntive</td>
		<td><%=TicketDetails.getAllertaNotes() %></td>
		</tr>	
	
	</table>
	<%}%>