<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.sanzioni.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="defect" class="org.aspcfs.modules.troubletickets.base.TicketDefect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>

<%@ include file="../initPage.jsp" %>

<%
%>
<form name="details" action="GestioneAnagraficaSanzioni.do?command=ModifyTicket&auto-populate=true" method="post">
<input type = "hidden" name = "idC" value = "<%=request.getAttribute("idC")%>">


<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="GestioneAnagraficaAction.do"><dhv:label name="gestioneanagrafica.gestioneanagrafica">gestioneanagrafica</dhv:label></a> > 
<%-- <a href="GestioneAnagraficaAction.do?command=Search"><dhv:label name="gestioneanagrafica.SearchResults">Search Results</dhv:label></a> > --%>
<a href="GestioneAnagraficaAction.do?command=Details&altId=<%=TicketDetails.getAltId()%>"><dhv:label name="gestioneanagrafica.details"> Dettaglio Anagrafica</dhv:label></a> >
<a href="GestioneAnagraficaAction.do?command=ViewVigilanza&altId=<%=TicketDetails.getAltId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
<a href="GestioneAnagraficaVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&altId=<%=TicketDetails.getAltId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
 
 
  <%if (TicketDetails.getTipologiaNonConformita()==Ticket.TIPO_NON_CONFORMITA_A_CARICO){ %>
<a href="GestioneAnagraficaNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&altId=<%=TicketDetails.getAltId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >
 <%}
else
{
%>
<a href="GestioneAnagraficaAltreNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&altId=<%=TicketDetails.getAltId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >

<%} %>
<%--a href="GestioneAnagraficaAction.do?command=ViewSanzioni&altId=<%=TicketDetails.getAltId() %>"><dhv:label name="">Sanzioni Amministrative</dhv:label></a> --%>
<%
	if (defectCheck != null && !"".equals(defectCheck.trim())) {
%>
  <a href="GestioneAnagraficaSanzioni.do?command=TicketDetails&Id=<%=TicketDetails.getId()%>&altId=<%=OrgDetails.getAltId() %>"><dhv:label name="tickets.defects.viewDefects">View Defects</dhv:label></a> >
  <a href="GestioneAnagraficaSanzioniDefects.do?command=Details&defectId=<%= defectCheck %>"><dhv:label name="tickets.defects.defectDetails">Defect Details</dhv:label></a> >
<%
  	} else {
  %>
<%
	if ("yes"
				.equals((String) session.getAttribute("searchTickets"))) {
%>
<%--   <a href="GestioneAnagraficaSanzioni.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> > --%>
<%--   <a href="GestioneAnagraficaSanzioni.do?command=SearchTickets"><dhv:label name="gestioneanagrafica.SearchResults">Search Results</dhv:label></a> > --%>
<%
  	} else {
  %> 
 <%--  <a href="GestioneAnagraficaAction.do?command=ViewSanzioni&altId=<%=OrgDetails.getAltId()%>"><dhv:label name="sanzioni.visualizza">Visualizza Sanzioni Amministrative</dhv:label></a> > --%>
<%
   	}
   %>
<%
	}
%>


<dhv:label name="sanzioni.dettagli">Scheda Sanzione Amministrativa</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId() + "&altId="+TicketDetails.getAltId()+"&idNC="+request.getAttribute("idNC");
%>
<dhv:container name="gestioneanagrafica" selected="vigilanza" object="OrgDetails" param='<%= "altId=" + TicketDetails.getAltId() %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
	
	<%-- @include file="ticket_header_include.jsp" --%>
	
	<%
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-sanzioni-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-sanzioni-delete" ;
	
	%>
	<%@ include file="../controlliufficiali/header_sanzioni.jsp"%>
	
	<%@ include file="../controlliufficiali/sanzioni_view.jsp"%>
	
	
	<br />
	
&nbsp;
<br />
		<%@ include file="../controlliufficiali/header_sanzioni.jsp"%>
<%-- </dhv:container> --%>
</dhv:container>
</form>
