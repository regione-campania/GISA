<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.sequestri.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.oia.base.OiaNodo" scope="request"/>
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
<%String idMacchinetta=""+OrgDetails.getId(); %>
<form name="details" action="OiaSequestri.do?command=ModifyTicket&auto-populate=true&idNodo=<%=idMacchinetta %>" method="post">
<input type ="hidden" name = "idC" value="<%=request.getAttribute("idC") %>">
<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Oia.do?command=List"><dhv:label name="">Asl</dhv:label></a> > 
<a href="Oia.do?command=Details&idNodo=<%=idMacchinetta %>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Scheda Dipartimento </dhv:label></a> >
<a href="OiaVigilanza.do?command=ViewVigilanza&idNodo=<%=idMacchinetta %>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
<a href="OiaVigilanza.do?command=TicketDetails&idNodo=<%=idMacchinetta %>&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
<a href="OiaNonConformita.do?command=TicketDetails&idNodo=<%=idMacchinetta %>&id=<%= request.getAttribute("idNC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >

<%--a href="Accounts.do?command=ViewSequestri&orgId=<%=TicketDetails.getOrgId() %>"><dhv:label name="sequestri">Sequestri</dhv:label></a> --%>
<%
	if (defectCheck != null && !"".equals(defectCheck.trim())) {
%>
  <a href="OiaSequestri.do?command=TicketDetails&idNodo=<%=idMacchinetta %>&Id=<%=TicketDetails.getId()%>&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="tickets.defects.viewDefects">View Defects</dhv:label></a> >
  <a href="OiaSequestriDefects.do?command=Details&idNodo=<%=idMacchinetta %>&defectId=<%= defectCheck %>"><dhv:label name="tickets.defects.defectDetails">Defect Details</dhv:label></a> >
<%
  	} else {
  %>
<%
	if ("yes"
				.equals((String) session.getAttribute("searchTickets"))) {
%>
  <a href="OiaSequestri.do?command=SearchTicketsForm&idNodo=<%=idMacchinetta %>"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
  <a href="OiaSequestri.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Ricerca Dipartimenti/Distretti</dhv:label></a> >
<%
  	} else {
  %> 
 
<%
   	}
   %>
<%
	}
%>


<dhv:label name="sequestri.dettagli">Scheda Sequestro/Blocco </dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId() + "&orgId="+TicketDetails.getOrgId()+"&idNC="+request.getAttribute("idNC");
%>
<dhv:container name="asl" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + TicketDetails.getOrgId()+"&idNodo="+idMacchinetta %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>

	
	
	<%UserBean user=(UserBean)session.getAttribute("User");
  String aslMacchinetta=(String)request.getAttribute("aslMacchinetta");
 
  
  if(user.getSiteId()!=-1){
	  
  if(aslMacchinetta!=null && (""+user.getSiteId()).equals(aslMacchinetta)){
  
  %>
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="oia-oia-sequestri-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='OiaSequestri.do?command=Restore&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='OiaSequestri.do?command=ReopenTicket&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
		<%
		} else {
	%>
	<dhv:permission name="oia-oia-sequestri-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='OiaSequestri.do?command=ModifyTicket&idNodo=<%=idMacchinetta %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%--
      <dhv:permission name="quotes-view">
        <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
          <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Details&productId=<%= TicketDetails.getProductId() %>&id=<%= TicketDetails.getId() %>';submit();"/>
        </dhv:evaluate>
      </dhv:permission>
      --%>
	<dhv:permission name="oia-oia-sequestri-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaSequestri.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaSequestri.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="oia-oia-sequestri-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"

			onClick="javascript:this.form.action='OiaSequestri.do?command=Chiudi&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere Questo Sequestro? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
	</dhv:permission>
	<%
		}}}else{
	%>
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="oia-oia-sequestri-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='OiaSequestri.do?command=Restore&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='OiaSequestri.do?command=ReopenTicket&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>

		<%
		} else {
	%>
	<dhv:permission name="oia-oia-sequestri-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='OiaSequestri.do?command=ModifyTicket&idNodo=<%=idMacchinetta %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%--
      <dhv:permission name="quotes-view">
        <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
          <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Details&productId=<%= TicketDetails.getProductId() %>&id=<%= TicketDetails.getId() %>';submit();"/>
        </dhv:evaluate>
      </dhv:permission>
      --%>
	<dhv:permission name="oia-oia-sequestri-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaSequestri.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaSequestri.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="oia-oia-sequestri-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"

			onClick="javascript:this.form.action='OiaSequestri.do?command=Chiudi&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere Questo Sequestro? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">

	</dhv:permission>
	
	
	<%}} %>
	<dhv:permission name="oia-oia-sequestri-edit,oia-oia-sequestri-delete">
		<br />&nbsp;<br />
	</dhv:permission>
	<%-- Ticket Information --%>
	<%-- Primary Contact --%>
	<dhv:evaluate if="<%= TicketDetails.getThisContact() != null %>">
		<table cellpadding="4" cellspacing="0" width="100%" class="details">
			<tr>
				<th colspan="2"><strong><dhv:label name="">Primary Contact</dhv:label></strong>
				</th>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="contacts.name">Name</dhv:label>
				</td>
				<td><dhv:evaluate
					if="<%= !TicketDetails.getThisContact().getEmployee() %>">
					<dhv:permission name="accounts-accounts-contacts-view">
						<a
							href="javascript:popURL('ExternalContacts.do?command=ContactDetails&id=<%= TicketDetails.getContactId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');"><%=toHtml(TicketDetails.getThisContact()
											.getNameFull())%></a>
					</dhv:permission>
					<dhv:permission name="accounts-accounts-contacts-view" none="true">
						<%=toHtml(TicketDetails.getThisContact()
											.getNameFull())%>
					</dhv:permission>
				</dhv:evaluate> <dhv:evaluate
					if="<%= TicketDetails.getThisContact().getEmployee() %>">
					<dhv:permission name="contacts-internal_contacts-view">
						<a
							href="javascript:popURL('CompanyDirectory.do?command=EmployeeDetails&empid=<%= TicketDetails.getContactId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');"><%=toHtml(TicketDetails.getThisContact()
											.getNameLastFirst())%></a>
					</dhv:permission>
					<dhv:permission name="contacts-internal_contacts-view" none="true">
						<%=toHtml(TicketDetails.getThisContact()
											.getNameLastFirst())%>
					</dhv:permission>
				</dhv:evaluate></td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label
					name="accounts.accounts_contacts_add.Title">Title</dhv:label></td>
				<td><%=toHtml(TicketDetails.getThisContact()
											.getTitle())%></td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label
					name="accounts.accounts_add.Email">Email</dhv:label></td>
				<td><%=TicketDetails.getThisContact()
									.getEmailAddressTag(
											"",
											toHtml(TicketDetails
													.getThisContact()
													.getPrimaryEmailAddress()),
											"&nbsp;")%>
				</td>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label
					name="accounts.accounts_add.Phone">Phone</dhv:label></td>
				<td><%=toHtml(TicketDetails.getThisContact()
									.getPrimaryPhoneNumber())%>
				</td>
			</tr>
		</table>
&nbsp;
</dhv:evaluate>
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
			
							
				 <%=toHtml(TicketDetails.getProblem())%> <input type="hidden"
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
</br>
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
		
		
			  
		
		
		
		
		<tr class="containerBody">
			<td class="formLabel"><dhv:label
				name="accounts.accounts_calls_list.Entered">Entered</dhv:label></td>
			<td><dhv:username id="<%= TicketDetails.getEnteredBy() %>" /> <zeroio:tz
				timestamp="<%= TicketDetails.getEntered() %>"
				timeZone="<%= User.getTimeZone() %>" showTimeZone="false" /></td>
		</tr>
		<tr class="containerBody">
			<td class="formLabel"><dhv:label
				name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
			</td>
			<td><dhv:username id="<%= TicketDetails.getModifiedBy() %>" /> <zeroio:tz
				timestamp="<%= TicketDetails.getModified() %>"
				timeZone="<%= User.getTimeZone() %>" showTimeZone="false" /></td>
		</tr>
		 </table>
	
	<br />
	
&nbsp;
<br />
	<%
	 if(user.getSiteId()!=-1){
		  
		  if(aslMacchinetta!=null && (""+user.getSiteId()).equals(aslMacchinetta)){
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="oia-oia-sequestri-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='OiaSequestri.do?command=Restore&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
 	 
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='OiaSequestri.do?command=ReopenTicket&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<%
		} else {
	%>
	<dhv:permission name="oia-oia-sequestri-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='OiaSequestri.do?command=ModifyTicket&idNodo=<%=idMacchinetta %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%--
  <dhv:permission name="quotes-view">
    <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
      <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Display&productId=<%= TicketDetails.getProductId() %>&id=<%= TicketDetails.getId() %>';submit();"/>
    </dhv:evaluate>
  </dhv:permission>
  --%>
	<dhv:permission name="oia-oia-sequestri-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaSequestri.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaSequestri.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&orgId=<%= TicketDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="oia-oia-sequestri-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"

			onClick="javascript:this.form.action='OiaSequestri.do?command=Chiudi&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere Questo Sequestro ? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">

	</dhv:permission>
	<%
		}}}else{
	
	if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="oia-oia-sequestri-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='OiaSequestri.do?command=Restore&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	 
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='OiaSequestri.do?command=ReopenTicket&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<%
		} else {
	%>
	<dhv:permission name="oia-oia-sequestri-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='OiaSequestri.do?command=ModifyTicket&idNodo=<%=idMacchinetta %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%--
  <dhv:permission name="quotes-view">
    <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
      <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Display&productId=<%= TicketDetails.getProductId() %>&id=<%= TicketDetails.getId() %>';submit();"/>
    </dhv:evaluate>
  </dhv:permission>
  --%>
	<dhv:permission name="oia-oia-sequestri-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaSequestri.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaSequestri.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&orgId=<%= TicketDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="oia-oia-sequestri-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"

			onClick="javascript:this.form.action='OiaSequestri.do?command=Chiudi&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere Questo Sequestro ? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">

	</dhv:permission>
	
	<%}} %>
	
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