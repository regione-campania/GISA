<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%
String msg = (String)request.getAttribute("Messaggio");
if(request.getAttribute("Messaggio")!=null)
{
	%>
	
<%@page import="com.zeroio.iteam.base.FileItem"%><script>
	
	alert("La pratica non può essere chiusa . \n Controllare di aver inserito l'esito.");
	</script>
	<%
}

%>


<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="sequestri.information">Scheda Sequestro/Blocco</dhv:label></strong></th>
		</tr>
		
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
	
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Identificativo Non Conformità</dhv:label>
    </td>
   
     
      <td>
      		<%= TicketDetails.getIdentificativonc() %>
      </td>
    
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Codice Sequestro</dhv:label>
    </td>
   
     
      <td>
      		<%= toHtmlValue(TicketDetails.getIdentificativo()) %>
      </td>
    
  </tr>	
  <input type="hidden" name="id" id="id"
			value="<%=  TicketDetails.getId() %>" />
		<input type="hidden" name="idStabilimento" id="orgId"
			value="<%=  TicketDetails.getIdStabilimento() %>" />
	<tr class="containerBody" style="display: none">
			<td nowrap class="formLabel"><dhv:label	name="sequestri.data_richiesta">Data Sequestro</dhv:label></td>
			<td><zeroio:tz timestamp="<%= TicketDetails.getAssignedDate() %>" dateOnly="true"
				timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>"
				showTimeZone="false" default="&nbsp;" />
				</td>
	</tr> 
	
	<dhv:evaluate if="<%= TipoSequestro.size() > 1 %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">Tipologia di Sequestro</td>
					<td><%=TipoSequestro.getSelectedValue(TicketDetails.getTipologiaSequestro())%>
					 <input type="hidden" name="TipoSequestro" value="<%=TicketDetails.getTipologiaSequestro()%>"></td>
				</tr>
	</dhv:evaluate>
	<dhv:evaluate if="<%= TipoSequestro.size() <= 1 %>">
			<input type="hidden" name="TipoSequestro" id="TipoSequestro" value="-1" />
	</dhv:evaluate>
	  
  <%if(!TicketDetails.getTipo_richiesta().equals("")){ %>
<tr class="containerBody">
  <td nowrap class="formLabel">
     
        Numero Verbale
 
      <input type="hidden" name="pippo" value="<%=TicketDetails.getPippo()%>">
   </td>
   <td>
      <%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>
      
   </td>
</tr>
<%} %>

<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Riferimento Normativo</dhv:label>
    </td>
   
     
      <td>
      		<%if(TicketDetails.getCodiceArticolo() == 1){ //Penale
      		
      			out.print("Articolo 354 C.P.P");
      		}
      		/*else{
      			if(TicketDetails.getCodiceArticolo()==2){
      	      		out.print("Articolo 13 L. 689/81");
      	      		
          		}*/
          	else{
          		if(TicketDetails.getCodiceArticolo()==3){ //Sanitario
          	      		out.print("Articoli 18 e 54 Reg CE 882/04 e dell'Articolo 1 L.283/02");
              	}
          		else{
              		if(TicketDetails.getCodiceArticolo()==4){ //Amministrativo
              	      		out.print("Articolo 13 L. 689/81 e Articolo 54 Reg CE 882/04");
                  	}
          		}
          	}
      		//}
      		%>
      		
      </td>
    
  </tr>	


<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Sequestro di </dhv:label>
    </td>
      <td>
      		<%= "- "+TicketDetails.getSequestroDiDescrizione() %></br>
      		<% if(TicketDetails.getSequestroDi()==4 || TicketDetails.getSequestroDi()==5 || TicketDetails.getSequestroDi()==6){%> 
      		<%= "<b>- Quantità(espressa in kg):</b> "+TicketDetails.getQuantita()%>
	</br><%} %>
	<%= "<b>- Descrizione:</b> "+TicketDetails.getNoteSequestrodi() %>   

	</td>
</tr>	






		<dhv:evaluate if="<%= hasText(TicketDetails.getProblem()) %>">
			<tr class="containerBody">
				<td class="formLabel" valign="top"><dhv:label
					name="sequestri.note">Note</dhv:label></td>
				<td valign="top">
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
				%> <%=toHtml(TicketDetails.getProblem())%> <input type="hidden"
					name="problem" value="<%=toHtml(TicketDetails.getProblem())%>">
				
			</tr>
		</dhv:evaluate>
	
<!-- <tr class="containerBody"> -->
<!--     <td class="formLabel"> -->
<!--       <dhv:label name="">Valutazione del rischio n.c. gravi</dhv:label> -->
<!--     </td> -->
<!--       <td> -->
<%-- 		<%= (TicketDetails.getValutazione() != null && !TicketDetails.getValutazione().trim().equals("")) ? TicketDetails.getValutazione() : "N.D"%> --%>
<!-- 	</td> -->
<!-- </tr> -->

</table>
</br>
<%--
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Follow Up del Sequestro/Blocco</dhv:label></strong>
    </th>
	</tr>
	<%if(TicketDetails.getEstimatedResolutionDate()!=null){ %>
			<tr class="containerBody">
				<td valign="top" class="formLabel"><dhv:label
					name="sequestri.azioni">Data Esito</dhv:label></td>
				<td>
				<%
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
				out.print(sdf.format(TicketDetails.getEstimatedResolutionDate()));
				%>
				</td>
			</tr>
			<%} %>
		<%if(TicketDetails.getEsitoSequestro()>-1){ %>
			<tr class="containerBody">
				<td valign="top" class="formLabel">
				
				Esito
				</td>
				<td><%= (TicketDetails.getDescrizionEsito() != null) ? TicketDetails.getDescrizionEsito(): "N.D"%>
				<%if(TicketDetails.getEsitoSequestro()==7){ %>
				<br>
				<%="Descrizione : "+TicketDetails.getDescrizione() %>
				
				<%} %>
				</td>
			</tr>
		
		<%} %>
	
		<dhv:evaluate if="<%= hasText(TicketDetails.getSolution()) %>">
			<tr class="containerBody">
				<td valign="top" class="formLabel"><dhv:label
					name="sequestri.azioni">Ulteriori Note</dhv:label></td>
				<td><%=toString(TicketDetails.getSolution())%><%-- %></textarea></td>
			</tr>
		</dhv:evaluate>
		
		 </table>--%>
	
	<br />
	
&nbsp;