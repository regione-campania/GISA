<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.diffida.base.Ticket" scope="request"/>

<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>

<%@ include file="../initPage.jsp" %>

<%
TicketDetails.setPermission();
%>
<form name="details" action="<%=OrgDetails.getAction() %>Diffida.do?command=ModifyTicket&auto-populate=true" method="post">
<input type = "hidden" name = "idC" value = "<%=request.getAttribute("idC")%>">


<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>

  <a href="GestioneAnagraficaAction.do?command=Details&altId=<%=OrgDetails.getAltId()%>">Scheda Impresa</a> ->
  <a href="GestioneAnagraficaAction.do?command=ViewVigilanza&altId=<%=OrgDetails.getAltId()%>"> Controlli Ufficiali </a>-> 
  <a href="GestioneAnagraficaVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&altId=<%=OrgDetails.getAltId()%>">Scheda controllo</a>->
  <a href="GestioneAnagraficaNonConformita.do?command=TicketDetails&id=<%=TicketDetails.getId_nonconformita()%>&altId=<%=OrgDetails.getAltId()%>">Scheda non Conformita</a>-> 
  Scheda Diffida 

</td>
</tr>
</table>
<%-- End Trails --%>
<%
String nomeContainer = "gestioneanagrafica";
request.setAttribute("Operatore",OrgDetails.getOperatore());
String param = "altId="+OrgDetails.getAltId();

%>
<dhv:container name="<%=nomeContainer%>" selected="vigilanza" object="Operatore" param='<%= "altId=" + OrgDetails.getAltId() %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
	
	
	<%
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-diffida-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-diffida-delete" ;
	
	%>
	<%@ include file="../controlliufficiali/opu_header_diffida.jsp"%>
	
	<%@ include file="../controlliufficiali/diffida_view.jsp"%>
	
	
	<br />
	
&nbsp;
<br />
		<%@ include file="../controlliufficiali/opu_header_diffida.jsp"%>
<%-- </dhv:container> --%>
</dhv:container>
</form>
