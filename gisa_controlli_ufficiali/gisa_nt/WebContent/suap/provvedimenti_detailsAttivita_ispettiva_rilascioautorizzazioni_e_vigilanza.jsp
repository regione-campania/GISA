<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.prvvedimentinc.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.prvvedimentinc.base.Ticket" scope="request"/>
<jsp:useBean id="AltriAlimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ConseguenzePositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ResponsabilitaPositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformatiValori" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="AlimentiVegetali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
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



<%TicketDetails.setPermission(); %>
<%@ include file="../initPage.jsp" %>
<form name="details" action="<%=OrgDetails.getAction() %>Provvedimenti.do?command=ModifyTicket&auto-populate=true" method="post">
<input type = "hidden" name ="idC" value = "<%=TicketDetails.getIdControlloUfficiale() %>">
<input type = "hidden" name ="id" value = "<%=TicketDetails.getId() %>">

<!-- <input type="hidden" name = "idControlloUfficiale" value ="<%= request.getAttribute("idC")%>">-->
<%-- Trails --%>
<table class="trails" cellspacing="0"> 
<tr>
<td>
<td>
     <dhv:label name=""><a href="<%=OrgDetails.getAction()+".do?command=SearchForm" %>" >Gestione Anagrafica Impresa </a>-><a  href="<%=OrgDetails.getAction()+".do?command=Details&altId="+OrgDetails.getAltId()%>">Scheda Impresa</a> -><a href="<%=OrgDetails.getAction()+".do?command=ViewVigilanza&altId="+OrgDetails.getAltId()%>"> Controlli Ufficiali </a>-> <a href="<%=OrgDetails.getAction()+"Vigilanza.do?command=TicketDetails&id="+CU.getIdControlloUfficiale()+"&altId="+OrgDetails.getAltId()%>">Scheda controllo</a>->Scheda Provvedimento</dhv:label>

</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId();
%>
<%
String nomeContainer = OrgDetails.getContainer();
request.setAttribute("Operatore",OrgDetails.getOperatore());

%>
<dhv:container name="<%=nomeContainer %>" selected="vigilanza" object="Operatore" param='<%= "altId=" + OrgDetails.getAltId() +"&opId="+OrgDetails.getIdOperatore()+"&id="+CU.getIdMacchinetta()  %>' 
	>
	<%
		if (TicketDetails.isTrashed()) {
	%>
	
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Provvedimenti.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">

	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Provvedimenti.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
		} else {
	%>
	
		<input type="button"
			value="<dhv:label name="global.button.modifya">Modifica </dhv:label>"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Provvedimenti.do?command=ModifyTicket&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	
	
	
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		
		<%
			} else {
		%>
		
		<%
			}
		%>
	
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Provvedimenti.do?command=Chiudi&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il campione? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">		
	
	
	
	<%
		}
	
	
	%>
	
	
	
	
	
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="sanzionia.information">Scheda Provvedimento</dhv:label></strong></th>
		</tr>
	
		  <%@ include file="/controlliufficiali/opu_provvedimenti_view.jsp" %>
				
   </table>
     
   
   </br>
 
&nbsp;
<br />
	<%
		if (TicketDetails.isTrashed()) {
	%>

	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Provvedimenti.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
		} else {
	%>
		<input type="button"
			value="<dhv:label name="global.button.modifya">Modifica</dhv:label>"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Provvedimenti.do?command=ModifyTicket&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">

	
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Provvedimenti.do?command=Chiudi&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il campione? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
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


	var answer = confirm("Tutte le Attivita Collegate al controllo sono state chiuse . \n Desideri Chiudere il Controllo Ufficiale ?\n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore")

	if (answer){
		
		doSubmit(<%=TicketDetails.getId() %>);
	}


	function doSubmit(idTicket){

		document.details.action="<%=OrgDetails.getAction() %>Provvedimenti.do?command=Chiudi&id="+idTicket+"&chiudiCu=true'"
		document.details.submit();

		}
	
	</script>
	<%
}
%>
