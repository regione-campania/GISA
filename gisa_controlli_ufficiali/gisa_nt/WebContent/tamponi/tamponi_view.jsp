<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
	<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.site">Site</dhv:label></td>
					<td><%=SiteIdList.getSelectedValue(TicketDetails
										.getSiteId())%>
					<%
					%> 
					<input type="hidden"
						name="siteId" value="<%=TicketDetails.getSiteId()%>"></td>
				</tr>
			
  <input type="hidden" name="id" id="id"
			value="<%=  TicketDetails.getId() %>" />
			
		<input type="hidden" name="orgId" id="orgId"
			value="<%=  TicketDetails.getOrgId() %>" />
			
<dhv:evaluate if="<%= hasText(TicketDetails.getLocation())%>">
<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Numero Verbale</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(TicketDetails.getLocation()) %>
    </td>
</tr>
<input type="hidden" name="id" id="id"
			value="<%=  TicketDetails.getId() %>" />
		<input type="hidden" name="orgId" id="orgId"
			value="<%=  TicketDetails.getOrgId() %>" />

</dhv:evaluate>
<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
   
     
      <td>
      		<%= toHtmlValue(TicketDetails.getIdControlloUfficiale()) %>
      </td>
    
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Codice Tampone</dhv:label>
    </td>
   
     
      <td>
      		<%= toHtmlValue(TicketDetails.getIdentificativo()) %>
      </td>
    
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="tamponi.data_richiesta">Data Prelievo</dhv:label>
    </td>
    <td>
      <zeroio:tz
				timestamp="<%= TicketDetails.getAssignedDate() %>" dateOnly="true"
				timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>"
				showTimeZone="false" default="&nbsp;" /> 
     
    </td>
  </tr>
  <%--
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.data_ispezione">Data Macellazione</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"  showTimeZone="false" />
      <%= showAttribute(request, "estimatedResolutionDateError") %>
    </td>
  </tr>--%>
  

  <%if(TicketDetails.getListaTamponi().size()!=0){ %>
  <tr class="containerBody">
  
  <td nowrap class="formLabel">Tamponi</td>
   <td>
  <table class="noborder" >
  <%HashMap<Integer,Tampone> tamponi= TicketDetails.getListaTamponi();
  
  Set<Integer> setKiavi=tamponi.keySet();
  Iterator<Integer> iteraKiavi=setKiavi.iterator();
  int progessivo=1;
for(int i=0;i<tamponi.size();i++){
	progessivo=i+1;
	 
	  Tampone curr=tamponi.get(progessivo);
	  
  %>
   <tr>
    <td><b>Tampone  <%=progessivo  %> </td>
    
  <td><table><%if(curr.getTipo()==1){ %><tr><td align="middle" ><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle" ><input type="checkbox" disabled value="1" name="check1_1" id="check1_1" checked="checked" onclick="showRow1_tampone1()" ></td></tr>
     <tr><td align="middle" ><label>Altro</label></td></tr>
     <tr><td align="middle" ><input type="checkbox"  disabled value="2" name="check2_1" id="check2_1" onclick="showRow1_tampone1()" ></td></tr>
      <%}else{ %>
      <tr><td align="middle" ><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle" ><input type="checkbox" disabled value="1" name="check1_1" id="check1_1" onclick="showRow1_tampone1()" ></td></tr>
     <tr><td align="middle" ><label>Altro</label></td></tr>
     <tr><td align="middle" ><input type="checkbox" disabled checked="checked" value="2" name="check2_1" id="check2_1" onclick="showRow1_tampone1()" ></td></tr>
 <%} %></table></td>
 
  <td>
    <table cellpadding="15">
    <tr><td><center><b><%if(curr.getTipo()==1) { out.print("Carcassa: ");}else{ out.print("Superfice Testata: ");} %></b></center></br></br><center><%= curr.getSuperficeStringa() %></center></td>
  	
  <td>
<center>
<b>Tipo di Ricerca:</b></center>

  <%HashMap<Integer,String> ricercaSelezionata= curr.getRicerca();
  HashMap<Integer,String> esitiSelezionati= curr.getEsiti();
Iterator<Integer> iteraKiaviRicerca =ricercaSelezionata.keySet().iterator();  
  
  
  %>
  </br></br>
  <table>
  <%
  int k=0;
  while(iteraKiaviRicerca.hasNext()){
	  int kiaveRicerca=iteraKiaviRicerca.next();
	  String descrizioneRicerca=ricercaSelezionata.get(kiaveRicerca);
	  String esitoRicerca=esitiSelezionati.get(kiaveRicerca);
	  k++;
	  %>
  
  <tr><td><%=descrizioneRicerca+"    -      "%><b>Esito: </b><%=esitoRicerca %></td></tr>
  
  
 
  
  <%} %>
  
  
  
  </table>
  
  
 
  
  </td>
  </tr>
  </table>
  
  
  
  </tr>
  
  
  
  
  
  
  
  
  <%
  progessivo++;
  } %>
  </table>
  
  
  </td>
  
  </tr>
  
  <%} %>

<dhv:include name="organization.source" none="true">
<dhv:evaluate if="<%= TicketDetails.getDestinatarioTampone()!= -1%>">
   <tr class="containerBody">
      <td name="tipoTampone1" id="tipoTampone1" nowrap class="formLabel">
        <dhv:label name="">Laboratorio di Destinazione</dhv:label>
      </td>
    <td>
      <%=DestinatarioTampone.getSelectedValue(TicketDetails
    		  .getDestinatarioTampone())%>
					<input type="hidden" name="destinatarioTampone"
						value="<%=TicketDetails.getDestinatarioTampone() %>">
    </td>
  </tr>
  </dhv:evaluate>
</dhv:include>
<% if(TicketDetails.getDataAccettazione() != null){ %>
<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Accettazione</dhv:label>
    </td>
    <td>
      <zeroio:tz
				timestamp="<%= TicketDetails.getDataAccettazione() %>" dateOnly="true"
				timeZone="<%= TicketDetails.getDataAccettazioneTimeZone() %>"
				showTimeZone="false" default="&nbsp;" /> 
     
    </td>
  </tr>
  <%} %>
  
  <dhv:evaluate if="<%= hasText(TicketDetails.getCause()) %>">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Codice Accettazione</dhv:label>
    </td>
    <td><%= toHtmlValue(TicketDetails.getCause()) %>
    </td>
  </tr>
  </dhv:evaluate>
  
 
 
  
  <tr class="containerBody">
      <td name="punteggio" id="punteggio" nowrap class="formLabel">
        <dhv:label name="">Punteggio</dhv:label>
      </td>
    <td>
    	<%= toHtmlValue(TicketDetails.getPunteggio()) %>
      <input type="hidden" name="punteggio" id="punteggio" size="20" maxlength="256" />
    </td>
  </tr>
  
<dhv:evaluate if="<%= hasText(TicketDetails.getProblem()) %>">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
    </td>
    <td>
      <%= toString(TicketDetails.getProblem()) %>
    </td>
	</tr>
</dhv:evaluate>