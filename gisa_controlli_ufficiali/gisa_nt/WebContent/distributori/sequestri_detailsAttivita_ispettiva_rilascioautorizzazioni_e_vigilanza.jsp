<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.troubletickets.base.Ticket"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.sequestri.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.distributori.base.Organization" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestriAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestriPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="SequestroDi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoSequestro" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDi_sp" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="defect" class="org.aspcfs.modules.troubletickets.base.TicketDefect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>

<%@ include file="../initPage.jsp" %>
<%String idMacchinetta=(String)request.getAttribute("idMacchinetta"); %>
<form name="details" action="DistributoriSequestri.do?command=ModifyTicket&auto-populate=true&idMacchinetta=<%=idMacchinetta %>" method="post">
<input type ="hidden" name = "idC" value="<%=request.getAttribute("idC") %>">
<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Distributori.do?command=List"><dhv:label name="">Distributori Automatici</dhv:label></a> > 
<a href="Distributori.do?command=Details&id=<%=idMacchinetta %>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Scheda Distributore Automatico </dhv:label></a> >
<a href="Distributori.do?command=ViewVigilanza&id=<%=idMacchinetta %>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
<a href="DistributoriVigilanza.do?command=TicketDetails&idmteacchinetta=<%=idMacchinetta %>&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >

<%
if (TicketDetails.getTipologiaNonConformita()==Ticket.TIPO_NON_CONFORMITA_A_CARICO)
{
	%>
<a href="DistributoriNonConformita.do?command=TicketDetails&idMacchinetta=<%=idMacchinetta %>&id=<%= request.getAttribute("idNC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >
	
	<%
}else
{
%>
<a href="DistributoriAltreNonConformita.do?command=TicketDetails&idMacchinetta=<%=idMacchinetta %>&id=<%= request.getAttribute("idNC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >

<%} %>

<%--a href="Accounts.do?command=ViewSequestri&orgId=<%=TicketDetails.getOrgId() %>"><dhv:label name="sequestri">Sequestri</dhv:label></a> --%>
<%
	if (defectCheck != null && !"".equals(defectCheck.trim())) {
%>
  <a href="DistributoriSequestri.do?command=TicketDetails&idMacchinetta=<%=idMacchinetta %>&Id=<%=TicketDetails.getId()%>&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="tickets.defects.viewDefects">View Defects</dhv:label></a> >
  <a href="DistributoriSequestriDefects.do?command=Details&idMacchinetta=<%=idMacchinetta %>&defectId=<%= defectCheck %>"><dhv:label name="tickets.defects.defectDetails">Defect Details</dhv:label></a> >
<%
  	} else {
  %>
<%
	if ("yes"
				.equals((String) session.getAttribute("searchTickets"))) {
%>
  <a href="DistributoriSequestri.do?command=SearchTicketsForm&idMacchinetta=<%=idMacchinetta %>"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
  <a href="DistributoriSequestri.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
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
<dhv:container name="distributori" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + TicketDetails.getOrgId()+"&id="+idMacchinetta %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>


	<%
	TicketDetails.setPermission();
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-sequestri-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-sequestri-delete" ;
	
	%>
	<%@ include file="../controlliufficiali/header_sequestri.jsp"%>
	
	<%@ include file="../controlliufficiali/sequestri_view.jsp"%>
<br />
	<%@ include file="../controlliufficiali/header_sequestri.jsp"%>
	
</dhv:container>
</form>
	
	
<%-- 	
	<%UserBean user=(UserBean)session.getAttribute("User");
  	String aslMacchinetta=(String)request.getAttribute("aslMacchinetta");
 
  
  	if(user.getSiteId()!=-1){
	  
 		 if(aslMacchinetta!=null && (""+user.getSiteId()).equals(aslMacchinetta)){
  
  		%>
  
  
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="distributori-distributori-sequestri-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='DistributoriSequestri.do?command=Restore&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='DistributoriSequestri.do?command=ReopenTicket&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
		<%
		} else {
	%>
	<dhv:permission name="distributori-distributori-sequestri-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='DistributoriSequestri.do?command=ModifyTicket&idMacchinetta=<%=idMacchinetta %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>--%>
	<%--
      <dhv:permission name="quotes-view">
        <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
          <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Details&productId=<%= TicketDetails.getProductId() %>&id=<%= TicketDetails.getId() %>';submit();"/>
        </dhv:evaluate>
      </dhv:permission>
      --%>
	

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