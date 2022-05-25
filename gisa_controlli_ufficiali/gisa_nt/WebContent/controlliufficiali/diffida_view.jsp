<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="Ispezioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="ListaNorme" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="sanzioni.information">Scheda Diffida</dhv:label></strong></th>
		</tr>
		<%--<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.tipo_richiesta">Ticket State</dhv:label>
    </td>
    <td>
      <dhv:label name="<%="richieste." + TicketDetails.getTipo_richiesta() %>"><%=TicketDetails.getTipo_richiesta()%></dhv:label>
    </td>
  </tr>--%>
		<dhv:include name="" none="true">
			<dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.site">Site</dhv:label></td>
					<td><%=SiteIdList.getSelectedValue(TicketDetails
										.getSiteId())%>
					<%
						
					%> <input type="hidden"
						name="siteId" value="<%=TicketDetails.getSiteId()%>"></td>
				</tr>
			</dhv:evaluate>
			<dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
				<input type="hidden" name="siteId" id="siteId" value="-1" />
						</dhv:evaluate>
		</dhv:include>
	
  <input type="hidden" name="id" id="id"
			value="<%=  TicketDetails.getId() %>" />
		<input type="hidden" name="orgId" id="orgId"
			value="<%=  TicketDetails.getOrgId() %>" />
			
	
	<%if(TicketDetails.getIdentificativonc()!=null){ %>
			
<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Identificativo Non Conformità</dhv:label>
    </td>
   
     
      <td> 
      		<%= TicketDetails.getIdentificativonc() %>
      </td>
    
  </tr><%} %>
  
  <%if(TicketDetails.getIdentificativo()!=null){ %>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Codice Diffida</dhv:label>
    </td>
   
     
      <td>
      		<%= toHtmlValue(TicketDetails.getIdentificativo()) %>
      </td>
    
  </tr>	
	<%} %>
		
		  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Impresa</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(OrgDetails.getName()) %>
       
      </td>
    
  </tr>
	
	<%if(TicketDetails.getAzioninonConformePer().size()!=0){ 
	
	HashMap<Integer,String> azionenonConformePer=TicketDetails.getAzioninonConformePer();
	Set<Integer> setkiavi=azionenonConformePer.keySet();
	Iterator<Integer> itera=setkiavi.iterator();

	%>
	
	    
		<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="">Azione non Conforme Per</dhv:label>
					</td>
		
					<td><%
				
					if ((CU.getTipoCampione()==4 || CU.getTipoCampione()==5 ) && CU.getLisaElementi_Ispezioni().size()!=0)
        		{
						while(itera.hasNext()){
							int chiave=itera.next();
							
							
							%>
					<%="- "+Ispezioni.getSelectedValue(chiave) %>

						<%} %>
        		
        		
        		<%        		
        	}
        	else
        	{
        	while(itera.hasNext()){
						int chiave=itera.next();
						String value=azionenonConformePer.get(chiave);
						
						%>
					<%="- " + value %>

					<%} %>
        	
         <%} %>
					

					</br>
					<% if(!TicketDetails.getDescrizione1().equals("")){%>
					<%="- Descrizione: " + TicketDetails.getDescrizione1() %>
					<%} %>
					</td>
					
			

				</tr>
				
				<%} %>
				
	
	
<%
if(TicketDetails.getDataChiusura()!=null)
{ 
%>
	<tr class="containerBody">
		<td nowrap class="formLabel"><dhv:label name="">Data scadenza d'ufficio</dhv:label>
		</td>
		<td>
			<%=(new SimpleDateFormat("dd/MM/yyyy")).format(TicketDetails.getDataChiusura().getTime())%>
		</td>
	</tr>
				
				<%
}				
				if(TicketDetails.getNormaviolata()!=null){
				if(!TicketDetails.getNormaviolata().equals("")){ %>
				
				<tr class="containerBody">
  <td nowrap class="formLabel">
      Norma Violata

   </td>
   <td>
      <%= TicketDetails.getNormaviolata() %>
      
   </td>
   <%} %>
   </tr>
   <% } 
   %>
   
   <%if(TicketDetails.getListaNorme().size()!=0){ 
	
	HashMap<Integer,String> listanorme =TicketDetails.getListaNorme();
	Set<Integer> setkiavi = listanorme.keySet();
	Iterator<Integer> iteraNorme=setkiavi.iterator();

	%>
	
	    
		<tr class="containerBody">
					<td nowrap class="formLabel">
		<dhv:label name="">Norma Violata</dhv:label>
					</td>
		
					<td><%
        	while(iteraNorme.hasNext()){
						int chiave = iteraNorme.next();
						String value=listanorme.get(chiave);
						
						%>
					<%="- "+listanorme.get(chiave)%><br> 

					<%} %>  
</tr>
				
			<% } %>	
				
				
				
				
			<dhv:evaluate if="<%= hasText(TicketDetails.getProblem()) %>">
			<tr class="containerBody">
				<td class="formLabel" valign="top"><dhv:label
					name="sanzioni.note">Note</dhv:label></td>
				<td valign="top">
				</br>
				<%
					//Show audio files so that they can be streamed
							Iterator files = TicketDetails.getFiles().iterator();
							while (files.hasNext()) {
								FileItem thisFile = (FileItem) files.next();
								if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
				%> <a
					href="TroubleTicketsDocuments_asl.do?command=Download&stream=true&tId=<%= TicketDetails.getId() %>&fid=<%= thisFile.getId() %>"><img
					src="images/file-audio.gif" border="0" align="absbottom"><dhv:label
					name="tickets.playAudioMessage">Play Audio Message</dhv:label></a><br />
				<%
					}
							}
				%> <%=TicketDetails.getProblem()%> <input type="hidden"
					name="problem" value="<%=TicketDetails.getProblem()%>">
				</td>
			</tr>
		</dhv:evaluate>
			  

		
	</table>