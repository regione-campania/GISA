<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="ext.aspcfs.modules.apiari.base.Operatore"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.sequestri.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestriAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestriPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="defect" class="org.aspcfs.modules.troubletickets.base.TicketDefect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script type="text/javascript">

</script>
<%@ include file="../initPage.jsp" %>
<form name="details" action="ApicolturaApiariSequestri.do?command=ModifyTicket&auto-populate=true" method="post">

<%
Operatore operatore = OrgDetails.getOperatore();
request.setAttribute("Operatore", operatore);
%>
<input type ="hidden" name = "idC" value="<%=request.getAttribute("idC") %>">
<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>

 <a href="ApicolturaAttivita.do">Apiari</a> > 
  <a href="ApicolturaAttivita.do?command=SearchForm"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="ApicolturaApiari.do?command=Details&stabId=<%=request.getParameter("stabId")%>">Scheda Apiari</a> >
  <a href="ApicolturaApiari.do?command=ViewVigilanza&stabId=<%=request.getParameter("stabId")%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
  <a href="ApicolturaApiariVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&stabId=<%=request.getParameter("stabId")%>&idStabilimentoopu=<%=OrgDetails.getIdApiario()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >

<%
if (TicketDetails.getTipologiaNonConformita()==8)
{
	%>
<a href="ApicolturaApiariNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&stabId=<%=request.getParameter("stabId")%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >
	
	<%
}else
{
%>
<a href="ApicolturaApiariAltreNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&stabId=<%=request.getParameter("stabId")%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >

<%} %>

<dhv:label name="sequestri.dettagli">Scheda Sequestro/Blocco </dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<dhv:container name="apiari" selected="vigilanza" object="Operatore" param='<%= "stabId=" + OrgDetails.getIdStabilimento()+"&opId=" + OrgDetails.getIdOperatore() %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
	<%-- include file="ticket_header_include_sequestri.jsp" --%>
									<%
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-vigilanza-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-vigilanza-delete" ;
	
	%>
	
	<%@ include file="../controlliufficiali/opu_header_sequestri.jsp" %>
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="sequestri.information">Scheda Sequestro/Blocco</dhv:label></strong></th>
		</tr>
		<%--<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sequestri.tipo_richiesta">Ticket State</dhv:label>
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
		<%--  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sequestri.richiedente">Impresa</dhv:label>
    </td>
    <td>
                <%= toHtml(TicketDetails.getCompanyName()) %>
          </td>
  </tr>--%>
		<%--<tr class="containerBody">
		<td nowrap class="formLabel">
      <dhv:label name="sequestri.tipo_animale">Ticket Source</dhv:label>
		</td>
		<td>
      <%= toHtml(TicketDetails.getSourceName()) %>
		</td>
  </tr>  --%>
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
		<input type="hidden" name="orgId" id="orgId"
			value="<%=  TicketDetails.getOrgId() %>" />
		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label
				name="sequestri.data_richiesta">Data Sequestro</dhv:label></td>
			<td><zeroio:tz
				timestamp="<%= TicketDetails.getAssignedDate() %>" dateOnly="true"
				timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>"
				showTimeZone="false" default="&nbsp;" /> <%-- if (!User.getTimeZone().equals(TicketDetails.getAssignedDateTimeZone())) { %>
      <br />
      <zeroio:tz timestamp="<%= TicketDetails.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } --%></td>
		</tr>
		<%--<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzione.data_macellazione">Estimated Resolution Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" dateOnly="true" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>" showTimeZone="false"  default="&nbsp;"/>
      <%-- if(!User.getTimeZone().equals(TicketDetails.getEstimatedResolutionDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"  default="&nbsp;" />
      <% } 
    </td>
  </tr>--%>
  
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
      <dhv:label name="">Articolo Legge</dhv:label>
    </td>
   
     
      <td>
      		<%if(TicketDetails.getCodiceArticolo()==1){
      		
      			out.print("Articolo 354 C.P.P");
      		}else{
      			if(TicketDetails.getCodiceArticolo()==2){
      	      		out.print("Articolo 13 L. 689/81");
      	      		
          		}else{
          			if(TicketDetails.getCodiceArticolo()==3){
          	      		out.print("Articoli 18 e 54 Reg CE 882/04");
              		}
          		}
      		}
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
				<%--<input type="hidden" name="orgId"
					value="<%=TicketDetails.getOrgId()%>"> <input type="hidden"
					name="id" value="<%=TicketDetails.getId()%>">--%></td>
			</tr>
		</dhv:evaluate>
	

<%--<tr class="containerBody">

 <td class="formLabel"> Punteggio :</td>
 <td>  <%=TicketDetails.getPunteggio() %></td>

</tr>--%>

</table>
</br>

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
				<td><%=TicketDetails.getDescrizionEsito()%>
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
				<td><%=toString(TicketDetails.getSolution())%><%-- %></textarea>--%></td>
			</tr>
		</dhv:evaluate>
		
		 </table>
	
	<br />
	
&nbsp;
<br />

	
	<%@ include file="../controlliufficiali/opu_header_sequestri.jsp" %>
</dhv:container>
</form>

<%
String msg = (String)request.getAttribute("Messaggio");
if(request.getAttribute("Messaggio")!=null)
{
	%>
	<script>
	
	alert("La pratica non può essere chiusa . \n Controllare di aver inserito l'esito.");
	</script>
	<%
}

%>
