<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.cessazionevariazione.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.cessazionevariazione.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.impiantimacellazione.base.Organization" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DestinatarioCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<form name="details" action="AccountCessazionevariazione.do?command=ModifyTicket&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<td>
  <a href="ImpiantiMacellazione.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
  <a href="ImpiantiMacellazione.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="ImpiantiMacellazione.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <a href="ImpiantiMacellazione.do?command=ViewCessazionevariazione&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="cessazionevariazione2">Cessazioni/Variazioni</dhv:label></a> >
<dhv:label name="campione.dettaglia">Scheda Cessazione</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId();
%>
<dhv:container name="impiantiMacellazione" selected="cessazionevariazione" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
<dhv:container name="accountscessazionevariazione" selected="details" object="TicketDetails"
	param="<%= param1 %>"
	hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
	<%@ include file="ticket_header_include_cessazionevariazione.jsp"%>
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="impiantiMacellazione-impiantiMacellazione-cessazionevariazione-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<%--<dhv:permission name="campioni-campioni-edit">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='AccountCampioni.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>--%>
	<%
		} else {
	%>
	<dhv:permission name="impiantiMacellazione-impiantiMacellazione-cessazionevariazione-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=ModifyTicket&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%--
      <dhv:permission name="quotes-view">
        <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
          <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Details&productId=<%= TicketDetails.getProductId() %>&id=<%= TicketDetails.getId() %>';submit();"/>
        </dhv:evaluate>
      </dhv:permission>
      --%>
	<dhv:permission name="impiantiMacellazione-impiantiMacellazione-cessazionevariazione-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('AccountCessazionevariazione.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('AccountCessazionevariazione.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="impiantiMacellazione-impiantiMacellazione-cessazionevariazione-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=Chiudi&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere questa cessazione?') ){submit()};">
	</dhv:permission>
	<%
		}
	%>
	<dhv:permission name="impiantiMacellazione-impiantiMacellazione-cessazionevariazione-edit,impiantiMacellazione-impiantiMacellazione-cessazionevariazione-delete">
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
					<dhv:permission name="impiantiMacellazione-impiantiMacellazione-contacts-view">
						<a
							href="javascript:popURL('ExternalContacts.do?command=ContactDetails&id=<%= TicketDetails.getContactId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');"><%=toHtml(TicketDetails.getThisContact()
											.getNameFull())%></a>
					</dhv:permission>
					<dhv:permission name="impiantiMacellazione-impiantiMacellazione-contacts-view" none="true">
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
				name="sanzionia.information">Scheda Cessazione</dhv:label></strong></th>
		</tr>
		<%--<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.tipo_richiesta">Ticket State</dhv:label>
    </td>
    <td>
      <dhv:label name="<%="richieste." + TicketDetails.getTipo_richiesta() %>"><%=TicketDetails.getTipo_richiesta()%></dhv:label>
    </td>
  </tr>--%>
		
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
	   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="campioni.data_richiesta">Data</dhv:label>
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
  </tr>
  <dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Tipo di Analisi</dhv:label>
      </td>
    <td>
      <%=TipoCampione.getSelectedValue(TicketDetails
    		  .getTipoCampione())%>
					<input type="hidden" name="provvedimenti"
						value="<%=TicketDetails.getTipoCampione() %>">
    </td>
  </tr>
</dhv:include>
<dhv:include name="organization.source" none="true">
<dhv:evaluate if="<%= TicketDetails.getDestinatarioCampione()!= -1%>">
   <tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Destinatario del Campione</dhv:label>
      </td>
    <td>
      <%=DestinatarioCampione.getSelectedValue(TicketDetails
    		  .getDestinatarioCampione())%>
					<input type="hidden" name="destinatarioCampione"
						value="<%=TicketDetails.getDestinatarioCampione() %>">
    </td>
  </tr>
  </dhv:evaluate>
</dhv:include>
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
  </tr--%>
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
<input type="hidden" name="tipo_richiesta" value="attivita_ispettiva_rilascioautorizzazioni_e_vigilanza" />
<%--dhv:evaluate if="<%= hasText(TicketDetails.getCause()) %>">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Codice Accettazione</dhv:label>
    </td>
    <td><%= toHtmlValue(TicketDetails.getCause()) %>
    </td>
  </tr>
  </dhv:evaluate--%>
   </table>
   </br>
  <%--table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Risultato Campionamento</dhv:label></strong>
    </th>
	</tr>
  <dhv:evaluate if="<%= TicketDetails.getEstimatedResolutionDate()!=null %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzionia.data_ispezione">Data</dhv:label>
    </td>
    <td>
    <zeroio:tz
				timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" dateOnly="true"
				timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"
				showTimeZone="false" default="&nbsp;" /> 
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:include name="" none="true">
<dhv:evaluate if="<%= TicketDetails.getEsitoCampione() > -1 %>">
 <tr class="containerBody">
      <td name="esitoCampione1" id="esitoCampione1" nowrap class="formLabel">
        <dhv:label name="">Esito</dhv:label>
      </td>
    <td>
      <%=EsitoCampione.getSelectedValue(TicketDetails
    		  .getEsitoCampione())%>
					<input type="hidden" name="esitoCampione"
						value="<%=TicketDetails.getEsitoCampione() %>">
    </td>
  </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:evaluate if="<%= hasText(TicketDetails.getSolution()) %>">
<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Ulteriori Note</dhv:label>
    </td>
    <td>
      <%= toString(TicketDetails.getSolution()) %>
    </td>
    </tr>
</dhv:evaluate>
    </table--%>
&nbsp;
<br />
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="impiantiMacellazione-impiantiMacellazione-cessazionevariazione-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<%--<dhv:permission name="campioni-campioni-edit">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='AccountCampioni.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>--%>
	<%
		} else {
	%>
	<dhv:permission name="impiantiMacellazione-impiantiMacellazione-cessazionevariazione-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=ModifyTicket&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%--
  <dhv:permission name="quotes-view">
    <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
      <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Display&productId=<%= TicketDetails.getProductId() %>&id=<%= TicketDetails.getId() %>';submit();"/>
    </dhv:evaluate>
  </dhv:permission>
  --%>
	<dhv:permission name="impiantiMacellazione-impiantiMacellazione-cessazionevariazione-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('AccountCessazionevariazione.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('AccountCessazionevariazione.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%= TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="impiantiMacellazione-impiantiMacellazione-cessazionevariazione-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='AccountCessazionevariazione.do?command=Chiudi&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere questa variazione?') ){submit()};">
	</dhv:permission>
	<%
		}
	%>
</dhv:container>
</dhv:container>
</form>
