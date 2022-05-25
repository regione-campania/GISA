<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*,org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.reati.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.trasportoanimali.base.Organization" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ReatiAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ReatiPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>

<%@ include file="../initPage.jsp" %>
<form name="details" action="AccountReati.do?command=ModifyTicket&auto-populate=true" method="post">

<input type ="hidden" name = "idC" value="<%=request.getAttribute("idC") %>">
<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TrasportoAnimali.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="TrasportoAnimali.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="TrasportoAnimali.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="TrasportoAnimali.do?command=ViewVigilanza&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
<a href="TrasportiVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
<%

if (TicketDetails.getTipologiaNonConformita()==Ticket.TIPO_NON_CONFORMITA_A_CARICO)
{
	%>
<a href="TrasportiNonConformita.do?command=TicketDetails&idC=<%= request.getAttribute("idC")%>&id=<%= request.getAttribute("idNC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >
	
	<%
}else
{
%>
<a href="TrasportiAltreNonConformita.do?command=TicketDetails&idC=<%= request.getAttribute("idC")%>&id=<%= request.getAttribute("idNC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >

<%} %>
<%
	if (defectCheck != null && !"".equals(defectCheck.trim())) {
%>
  <a href="TrasportiReati.do?command=TicketDetails&Id=<%=TicketDetails.getId()%>&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="tickets.defects.viewDefects">View Defects</dhv:label></a> >
<%
  	} else {
  %>
<%
	if ("yes".equals((String) session.getAttribute("searchTickets"))) {
%>
  <a href="TrasportiReati.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
  <a href="TrasportiReati.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%
  	} 
  %> 
 
<%
	}
%>


<dhv:label name="reati.dettagli">Scheda Notizia di reato </dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId() + "&orgId="+TicketDetails.getOrgId()+"&idNC="+request.getAttribute("idNC");
%>
<dhv:container name="accounts" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + TicketDetails.getOrgId() %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>

	<%
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-reati-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-reati-delete" ;
	
	%>
	<%@ include file="../controlliufficiali/header_reati.jsp"%>
	
	
		<%@ include file="../controlliufficiali/reati_view.jsp"%>
	
	<br />
	
&nbsp;
<br />
	<%@ include file="../controlliufficiali/header_reati.jsp"%>
	
</dhv:container>
</form>
