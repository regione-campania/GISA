<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="ext.aspcfs.modules.apiari.base.Operatore"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.sanzioni.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request"/>
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
<form name="details" action="ApicolturaApiariSanzioni.do?command=ModifyTicket&auto-populate=true" method="post">
<input type = "hidden" name = "idC" value = "<%=request.getAttribute("idC")%>">


<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>

  <a href="ApicolturaAttivita.do">Apiari</a> > 
  <a href="ApicolturaAttivita.do?command=SearchForm"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="ApicolturaApiari.do?command=Details&stabId=<%=OrgDetails.getIdStabilimento()%>">Scheda Apiari</a> >
  <a href="ApicolturaApiari.do?command=ViewVigilanza&stabId=<%=OrgDetails.getIdStabilimento()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
  <a href="ApicolturaApiariVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&stabId=<%=OrgDetails.getIdStabilimento()%>&idStabilimentoopu=<%=OrgDetails.getIdStabilimento()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
  
<%if (TicketDetails.getTipologiaNonConformita()==Ticket.TIPO_NON_CONFORMITA_A_CARICO){ %>
<a href="ApicolturaApiariNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&stabId=<%=OrgDetails.getIdStabilimento()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >
 <%}
else
{
%>
<a href="ApicolturaApiariAltreNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&stabId=<%=OrgDetails.getIdStabilimento()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >

<%} %>
 


<dhv:label name="sanzioni.dettagli">Scheda Sanzione Amministrativa</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId() + "&orgId="+TicketDetails.getOrgId()+"&idNC="+request.getAttribute("idNC");
	Operatore operatore = OrgDetails.getOperatore();
	request.setAttribute("Operatore", operatore);
%>
<dhv:container name="apiari" selected="vigilanza" object="Operatore" param='<%= "stabId=" +OrgDetails.getIdStabilimento() +"&opId="+OrgDetails.getIdOperatore()%>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
	
	<%-- @include file="ticket_header_include.jsp" --%>
	
	<%
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-sanzioni-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-sanzioni-delete" ;
	
	%>
	<%@ include file="../controlliufficiali/opu_header_sanzioni.jsp"%>
	
	<%@ include file="../controlliufficiali/opu_sanzioni_view.jsp"%>
	
	
	<br />
	
&nbsp;
<br />
		<%@ include file="../controlliufficiali/opu_header_sanzioni.jsp"%>
<%-- </dhv:container> --%>
</dhv:container>
</form>
