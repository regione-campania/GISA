<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>


function openUploadListaPopUp(orgId,folderId){
	var res;
	var result;
	

		window.open('PrintReportVigilanza.do?command=UploadLista&orgId='+document.getElementById('id').value+'&folderId='+document.getElementById('folderId').value,null,
		'height=450px,width=480px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
		
		} 

</script>


<table cellpadding="4" cellspacing="0" width="100%" class="details" id = "tableHidden" style="display:none">
	<tr>
    <th colspan="2">
      <strong>Sistema di Allarme Rapido : Misure Riscontrate nel Corso della Verifica</strong>
    </th>
	</tr>


    	 <tr class="containerBody" >
    <td nowrap class="formLabel">(LDD) Nome Fornitore</td>
		<td>
		<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="nomeFornitore" name="nomeFornitore" value="<%=ListaDistribuzione.getNome_fornitore()%>"/>
		</td>
		</tr>
		
		
    	 <tr class="containerBody" >
    <td nowrap class="formLabel">(LDD) Data Lista / Chiusura</td>
		<td>
		<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="dataLista" name="dataLista"  value="<%=toDateasString(ListaDistribuzione.getData_lista())%>" />
		<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="dataChiusura" name="dataChiusura"  value="<%=toDateasString(ListaDistribuzione.getData_chiusura())%>"/> 
		</td>
		</tr>
		
    	 <tr class="containerBody" >
    <td nowrap class="formLabel">
     	Ricevuta Comunicazione del rischio <br/> da produttore/fornitore
    </td>
    <td>
	<table class= "noborder">
	<tr>
		<td>Si <input type = "radio" value="0" name="comunicazioneRischio1" <%if(TicketDetails.isComunicazioneRischio()){ %>checked="checked" <%} %>> </td>
		<td>No <input type = "radio" value="1" name="comunicazioneRischio1"  <%if(TicketDetails.isComunicazioneRischio()==false){ %>checked="checked" <%} %>> </td>
<%-- 		<td id="noteRischio"> <textarea name = "noteRischio" rows="4" cols="40"><%=toHtml(TicketDetails.getNoteRischio()) %></textarea>  &nbsp; <font color="red">*</font></td> --%>
	
	
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
		<td>Attivate <input type = "radio" value="0" name="procedureRitiro" <%if(TicketDetails.getProceduraRitiro()==0){ %>checked="checked" <%} %> > </td>
		<td>Non Attivate <input type = "radio" value="1" name="procedureRitiro" <%if(TicketDetails.getProceduraRitiro()==1){ %>checked="checked" <%} %>> </td> 
		<td>L'OSA non è tenuto ad attivarle <input type = "radio" value="2" name="procedureRitiro" <%if(TicketDetails.getProceduraRitiro()==2){ %>checked="checked" <%} %>>  
	&nbsp; <font color="red">*</font></td>
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
				<input type="radio" value="0" name="procedureRichiamo" <%if(TicketDetails.getProceduraRichiamo()==0){ %>checked="checked"<%} %> onClick="gestisciMotivoRichiamo(0)"/>
				</td>
				<td>Non Attivate 
				<input type="radio" value="1" name="procedureRichiamo"<%if(TicketDetails.getProceduraRichiamo()==1){ %>checked="checked"<%} %> onClick="gestisciMotivoRichiamo(1)"/>
				</td>
					
			<td id ="divMotivoRichiamo" <%if(TicketDetails.getProceduraRichiamo()!=1) { %> style="display:none" <%} %>>
				Motivo: <textarea name="motivoRichiamo" id="motivoRichiamo" rows="4" cols="40"><%=TicketDetails.getMotivoProceduraRichiamo() %></textarea>
				</td>
				
				 <td>
				<font color="red">*</font></td>

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
    				
				<%EsitoControllo.setJsEvent("onChange = azioneSuEsitoControllo('details');abilitaDestinazione('details')"); %>
    			<td><%=EsitoControllo.getHtmlSelect("esitoControllo",TicketDetails.getEsitoControllo()) %> &nbsp;
    			<input type = "hidden" name = "unitaMisura" id = "unitaMisura" value = "<%=toHtml(TicketDetails.getUnitaMisura()) %>">
     			<font color="red">*</font></td>
    		</tr>
    <tr><td>&nbsp;</td></tr>
    
    <tr id = "hiddenEsito1" style="display: none">
    <td>Data  
    <input readonly="readonly" type="text" name="dataddt" size="10" value="<%=toHtml2(TicketDetails.getDataddtTimeZone()) %>" />&nbsp;<a href="javascript:popCalendar('details','dataddt','it','IT','Europe/Berlin');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
    
    
    
     con DDT Num <input type ="text" name="numDdt" value = "<%=TicketDetails.getNumDDt() %>" > &nbsp; <font color="red">*</font>
  </td>
    </tr>
    
    <tr id = "hiddenEsito2" style="display: none">
    <td>Quantità <input type ="text" name="quantita" value = "<%=toHtml(TicketDetails.getQuantitaPartita()) %>"> ,   : 
    	<span id="misura" style="display:none"></span> Kg.
    	 <font color="red">*</font>
  </td>
    </tr>
    
     <tr id = "hiddenEsito3" style="display: none">
    <td>Quantità Bloccata <input type ="text" name="quantitaBloccata" value = "<%=toHtml(TicketDetails.getQuantitaBloccata()) %>" onchange="document.getElementById('rowAzione1').innerHTML='Quantita : '+document.details.quantitaBloccata.value+' '+document.details.unitaMisura.value"> ,  <span id = "misura1"></span>
  &nbsp; <font color="red">*</font> </td>
  
    </tr>
    
    
    </table>
 	
    </td>
  </tr>
   <tr class="containerBody" id = "partita">
    	<td nowrap class="formLabel">
     		Ulteriore Distribuzione della Partita
    	</td>
		<td><%DistribuzionePartita.setJsEvent("onChange=abilitaDestinazione('details')"); %>
			<%=DestinazioneDistribuzione.getHtmlSelect("destinazioneDistribuzione",TicketDetails.getDestinazioneDistribuzione()) %></td>
		
	</tr>
  <%--
  
  if(TicketDetails.isListaDistribuzioneAllegata()==false)
	  {
	  
	  %>
  
<tr class="containerBody" id="hidden3" style="display: none">
		<td nowrap class="formLabel"><dhv:label
			name="sanzionia.data_richiesta">Nuova Lista di Distribuzione</dhv:label>
		</td>
		<td>
		<a href = "javascript:openUploadListaPopUp()">Allega lista di Distribuzione</a>
		
		<input type="hidden" name="dosubmit" value="true" /> <input
			type="hidden" id = "id" name="id2" value="<%= OrgDetails.getIdStabilimento() %>" /> <input
			type="hidden" name="gotoPage" value="insert" /> 
			<input type="hidden" id = "folderId" name="folderId" value="<%= "-1" %>" />
			<input type="hidden" name="idFile" id = "idFile" value="<%=fileItem.getId() %>">
			<input type="hidden" name="fileAllegare" id = "fileAllegare" value="<%=fileItem.getId() %>">
			<input type="hidden" name="isAllegato" id = "isAllegato" value="false">
			<label id = "msg_file"><font color = "red">File non Allegato</font></label>
			
		</td>
	</tr>
     
     
     <%}else
    	 {
    	 %>
    	 <input type="hidden" name="gotoPage" value="update" />
    	 
    	    <tr class="containerBody" id = "hidden3"  >
    <td nowrap class="formLabel">
      <dhv:label name="sanzionia.data_richiesta">Nuova Lista di Distribuzione Allegata</dhv:label>
    </td>
    <td>
    <table class = "noborder">
    <tr><td>
 		
  <a href="AccountsDocuments.do?command=Download&orgId=<%= OrgDetails.getIdStabilimento() %>&fid=<%= fileItem.getId() %>&ver=<%= fileItem.getVersion() %><%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>">
       <%=fileItem.getSubject() %> </a> 
       
      <input type = "hidden" name = "gotoPage" value = "update">   	
    </td>
    <td>
    </td>
    
    
    </tr></table></td>
  </tr>
    	 
    	 <%
    	 
    	 }--%>
    	 <input type = "hidden" name = "idFile" value = "<%=fileItem.getId() %>"> 

  
  <tr class="containerBody" >
    <td nowrap class="formLabel">
     	Azioni Adottate
    </td>
    <td>
    <table class= "noborder">
    <tr>
    <%AzioniAdottate.setJsEvent("onChange = azioneSuAzioniAdottate('details')"); %>
    <td><%=AzioniAdottate.getHtmlSelect("azioniAdottate",TicketDetails.getAzioni_adottate_def()) %> &nbsp; <font color="red">*</font></td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <%--7 --%>
    <tr id = "hiddenAzione1" style="display: none">
    <td>
     <b> 
    <span id ="rowAzione1"></span>
    </b>
      </td>
    </tr>
    
    <tr id = "hiddenAzione2" style="display: none">
    <td> <b>Articolo : </b><%=ArticoliAzioni.getHtmlSelect("articoliAzioni",TicketDetails.getAzioneArticolo()) %> &nbsp; <font color="red">*</font>
  </td>
    </tr>
   
    
    </table>
 	
    </td>
  </tr>
	
		
	<tr class="containerBody">
		<td nowrap class="formLabel">Informazioni aggiuntive</td>
		<td> <textarea name="allertaNotes" id="allertaNotes" rows="4" cols="80"><%=TicketDetails.getAllertaNotes() %></textarea> </td>
		</tr>
		
	</table>