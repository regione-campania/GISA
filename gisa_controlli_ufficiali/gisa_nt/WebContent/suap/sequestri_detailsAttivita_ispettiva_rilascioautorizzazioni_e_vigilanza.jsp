<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.sequestri.base.Ticket" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.suap.base.Stabilimento" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestriAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestriPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>

<jsp:useBean id="SequestroDi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoSequestro" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDi_sp" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="defect" class="org.aspcfs.modules.troubletickets.base.TicketDefect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>

<%@ include file="../initPage.jsp" %>
<form name="details" action="<%=OrgDetails.getAction() %>Sequestri.do?command=ModifyTicket&auto-populate=true" method="post">

<input type ="hidden" name = "idC" value="<%=request.getAttribute("idC") %>">
<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>

  <dhv:label name=""><a href="<%=OrgDetails.getAction()+".do?command=SearchForm" %>" >Gestione Anagrafica Impresa </a>-><a  href="<%=OrgDetails.getAction()+".do?command=Details&altId="+OrgDetails.getAltId()%>">Scheda Impresa</a> -><a href="<%=OrgDetails.getAction()+".do?command=ViewVigilanza&altId="+OrgDetails.getAltId()%>"> Controlli Ufficiali </a>-> <a href="<%=OrgDetails.getAction()+"Vigilanza.do?command=TicketDetails&id="+CU.getIdControlloUfficiale()+"&altId="+OrgDetails.getAltId()%>">Scheda controllo</a>->
  
  
 <%if (TicketDetails.getTipologiaNonConformita()==Ticket.TIPO_NON_CONFORMITA_A_CARICO){ %>
   <a href="<%=OrgDetails.getAction()+"NonConformita.do?command=TicketDetails&id="+TicketDetails.getId_nonconformita()+"&altId="+OrgDetails.getAltId()%>">Scheda non Conformita</a>->
 
 <%}
else
{
%>
  <a href="<%=OrgDetails.getAction()+"AltreNonConformita.do?command=TicketDetails&id="+TicketDetails.getId_nonconformita()+"&altId="+OrgDetails.getAltId()%>">Scheda non Conformita  NON a carico del soggetto Ispezionato</a>


<%} %>
  -> Scheda Sequestro </dhv:label>

</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId() + "&altId="+TicketDetails.getAltId()+"&idNC="+request.getAttribute("idNC");
String container = OrgDetails.getContainer();
TicketDetails.setPermission();
request.setAttribute("Operatore",OrgDetails.getOperatore());
if (User.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA)
	container+="Ext";
%>

<dhv:container name="<%=container %>" selected="vigilanza" object="Operatore" param='<%= "altId=" + TicketDetails.getAltId() +"&opId="+OrgDetails.getIdOperatore()+"&id="+CU.getIdMacchinetta()  %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>

	<%
	
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-sequestri-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-sequestri-delete" ;
	
	%>
	<%@ include file="../controlliufficiali/opu_header_sequestri.jsp"%>
	
	<%@ include file="../controlliufficiali/opu_sequestri_view.jsp"%>
<br />
	<%@ include file="../controlliufficiali/opu_header_sequestri.jsp"%>
	
</dhv:container> 
</form>
