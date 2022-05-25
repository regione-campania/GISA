<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.followup.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="FollowupAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="FollowupPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>

<%@ include file="../initPage.jsp" %>
<form name="details" action="<%=OrgDetails.getAction() %>Followup.do?command=ModifyTicket&auto-populate=true" method="post">
<input type ="hidden" name = "idC" value="<%=request.getAttribute("idC") %>">
<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <dhv:label name=""><a href="<%=OrgDetails.getAction()+".do?command=SearchForm" %>" >Anagrafica Stabilimenti </a>-><a  href="<%=OrgDetails.getAction()+".do?command=Details&stabId="+OrgDetails.getIdStabilimento()%>">Scheda Impresa</a> -><a href="<%=OrgDetails.getAction()+".do?command=ViewVigilanza&stabId="+OrgDetails.getIdStabilimento()%>"> Controlli Ufficiali </a>-> <a href="<%=OrgDetails.getAction()+"Vigilanza.do?command=TicketDetails&id="+CU.getIdControlloUfficiale()+"&idStabilimentoopu="+OrgDetails.getIdStabilimento()%>">Scheda controllo</a>->
  
   <%if (TicketDetails.getTipologiaNonConformita()==Ticket.TIPO_NON_CONFORMITA_A_CARICO){ %>
   <a href="<%=OrgDetails.getAction()+"NonConformita.do?command=TicketDetails&id="+TicketDetails.getId_nonconformita()+"&stabId="+OrgDetails.getIdStabilimento()%>">Scheda non Conformita</a>->
 
 <%}
else
{
%>
  <a href="<%=OrgDetails.getAction()+"AltreNonConformita.do?command=TicketDetails&id="+TicketDetails.getId_nonconformita()+"&stabId="+OrgDetails.getIdStabilimento()%>">Scheda non Conformita NON a carico del soggetto Ispezionato</a>


<%} %>
  -> Scheda Followup </dhv:label>

</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId() + "&orgId="+TicketDetails.getOrgId()+"&idNC="+request.getAttribute("idNC");
%>
<%
TicketDetails.setPermission();
String nomeContainer = OrgDetails.getContainer();
request.setAttribute("Operatore",OrgDetails.getOperatore());
if (User.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA)
	nomeContainer+="Ext";
%>
<dhv:container name="<%=nomeContainer %>" selected="vigilanza" object="Operatore" param='<%= "stabId=" + TicketDetails.getIdStabilimento() +"&opId="+OrgDetails.getIdOperatore() +"&id="+CU.getIdMacchinetta() %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>

	<%-- include file="ticket_header_include_followup.jsp" --%>
	
		<%
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-followup-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-followup-delete" ;
	
	%>
	
	
	<%@ include file="../controlliufficiali/opu_header_followupi.jsp" %>
	
	<%@ include file="../controlliufficiali/opu_followup_view.jsp" %>
		
	<br />
	
&nbsp;
<br />
	
	
	<%@ include file="../controlliufficiali/opu_header_followupi.jsp" %>
</dhv:container>
</form>
