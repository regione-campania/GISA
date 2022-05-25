<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.tamponi.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>

<%@page import="com.sun.java.swing.plaf.windows.WindowsBorders.ProgressBarBorder"%>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.tamponi.base.Ticket" scope="request"/>
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>

<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoTampone" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DestinatarioTampone" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoTampone" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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

<form name="details" action="<%=OrgDetails.getAction() %>Tamponi.do?command=ModifyTicket&auto-populate=true" method="post">
<input type = "hidden" name = "idC" value = "<%=TicketDetails.getIdControlloUfficiale() %>">

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<td>
  <dhv:label name=""><a href="<%=OrgDetails.getAction()+".do?command=SearchForm" %>" >Anagrafica Stabilimenti </a>-><a  href="<%=OrgDetails.getAction()+".do?command=Details&stabId="+OrgDetails.getIdStabilimento()%>">Scheda Impresa</a> -><a href="<%=OrgDetails.getAction()+".do?command=ViewVigilanza&stabId="+OrgDetails.getIdStabilimento()%>"> Controlli Ufficiali </a>-> <a href="<%=OrgDetails.getAction()+"Vigilanza.do?command=TicketDetails&id="+CU.getIdControlloUfficiale()+"&idStabilimentoopu="+OrgDetails.getIdStabilimento()%>">Scheda controllo</a>->Scheda Tampone</dhv:label>

</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId();
String container = OrgDetails.getContainer();
request.setAttribute("Operatore",OrgDetails.getOperatore());

%>
<dhv:container name="<%=container %>" selected="vigilanza" object="Operatore" param='<%= "stabId=" + OrgDetails.getIdStabilimento() +"&opId="+OrgDetails.getIdOperatore()+"&id="+CU.getIdMacchinetta()  %>' hideContainer='<%= isPopup(request)  %>'>


	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="accounts-accounts-tamponi-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Tamponi.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
		<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Tamponi.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
		} else {
	%>
	<dhv:permission name="accounts-accounts-tamponi-edit">
		<input type="button"
			value="<dhv:label name="global.button.modifya">Modifica/Inserisci Esito</dhv:label>"
			onClick="javascript:document.forms[0].action='<%=OrgDetails.getAction() %>Tamponi.do?command=ModifyTicket&auto-populate=true';document.forms[0].submit();">
	</dhv:permission>
	
	<dhv:permission name="accounts-accounts-tamponi-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('<%=OrgDetails.getAction() %>Tamponi.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('<%=OrgDetails.getAction() %>Tamponi.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="accounts-accounts-tamponi-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Tamponi.do?command=Chiudi&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il tampone? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
	</dhv:permission>
	
	
	<%@ include file="/tamponi/tamponi_stampa_moduli_precompilati.jsp" %>
	
	<%
		}
	%>
	
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="sanzionia.information">Scheda Tampone</dhv:label></strong></th>
		</tr>
		
		
			<%@ include file="/tamponi/opu_tamponi_view.jsp" %>

   </table>
   
 
&nbsp;
<br />
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="accounts-accounts-tamponi-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Tamponi.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Tamponi.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
		} else {
	%>
	<dhv:permission name="accounts-accounts-tamponi-edit">
		<input type="button"
			value="<dhv:label name="global.button.modifya">Modifica/Inserisci Esito</dhv:label>"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Tamponi.do?command=ModifyTicket&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="accounts-accounts-tamponi-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('<%=OrgDetails.getAction() %>Tamponi.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('<%=OrgDetails.getAction() %>Tamponi.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="accounts-accounts-tamponi-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Tamponi.do?command=Chiudi&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il tampone? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
	</dhv:permission>
	<%
		}
	%>
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

<%
String msg2 = (String)request.getAttribute("Messaggio2");
if(request.getAttribute("Messaggio2")!=null)
{
	%>
	<script>

	var answer = confirm("Tutte le Attivita Collegate al controllo sono state chiuse . \n Desideri Chiudere il Controllo Ufficiale ? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore ")

	if (answer){
		
		doSubmit(<%=TicketDetails.getId() %>);
	}


	function doSubmit(idTicket){

		document.details.action="<%=OrgDetails.getAction() %>Tamponi.do?command=Chiudi&id="+idTicket+"&chiudiCu=true'"
		document.details.submit();

		}
	
	</script>
	<%
}

%>
