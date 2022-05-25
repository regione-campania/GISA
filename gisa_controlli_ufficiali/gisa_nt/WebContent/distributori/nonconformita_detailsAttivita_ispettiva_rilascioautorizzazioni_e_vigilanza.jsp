<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.nonconformita.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.nonconformita.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SanzioniList" class="org.aspcfs.modules.sanzioni.base.TicketList" scope="request"/>
<jsp:useBean id="SequestriList" class="org.aspcfs.modules.sequestri.base.TicketList" scope="request"/>
<jsp:useBean id="ReatiList" class="org.aspcfs.modules.reati.base.TicketList" scope="request"/>
<jsp:useBean id="FollowupList" class="org.aspcfs.modules.followup.base.TicketList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="ConseguenzePositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ResponsabilitaPositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.distributori.base.Organization" scope="request"/>
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
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/nonConformita.js"></SCRIPT>

<%@ include file="../initPage.jsp" %>

<%String idMacchinetta=(String)request.getAttribute("idMacchinetta"); %>
<body onload="verificaChiusuraNc(<%=request.getAttribute("Chiudi")%>,<%=request.getAttribute("numSottoAttivita")%>,'<%=request.getAttribute("Messaggio2")%>',<%=TicketDetails.getId() %>,'AccountNonConformita')">

<form name="details" action="AccountNonConformita.do?command=ModifyTicket&auto-populate=true" method="post">
<input type = "hidden" name = "idC" value = "<%=TicketDetails.getIdControlloUfficiale() %>">

<table class="trails" cellspacing="0">
<tr>
<td>
<td>
  <a href="Distributori.do?command=List"><dhv:label name="">Distributori</dhv:label></a> > 
  
  <a href="Distributori.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>&id=<%=idMacchinetta %>"><dhv:label name="">Scheda Distributore Automatico</dhv:label></a> >
  <a href="Distributori.do?command=ViewVigilanza&orgId=<%=TicketDetails.getOrgId()%>&id=<%=idMacchinetta %>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
  <a href="DistributoriVigilanza.do?command=TicketDetails&idmacchinetta=<%=idMacchinetta %>&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
  <%--a href="Distributori.do?command=ViewNonConformita&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="nonconformita">Tickets</dhv:label></a> --%>

<dhv:label name="campione.dettagli">Scheda Non Conformità Rilevata</dhv:label>

</td>
</tr>
</table>

<dhv:container name="distributori" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId()+"&id="+idMacchinetta %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>

<%
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-nonconformita-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-nonconformita-delete" ;
	
	%>
	
	
	<%UserBean user=(UserBean)session.getAttribute("User");
   
  String aslMacchinetta=(String)request.getAttribute("aslMacchinetta");

  if(user.getSiteId()!=-1){
	  
 if((""+user.getSiteId()).equals(aslMacchinetta)){ %>
 
 	
	
	
	<%@ include file="../controlliufficiali/header_non_conformita.jsp" %>
 
 <% }} %>
	
	
<!-- 		if (TicketDetails.isTrashed()) { -->
<!-- 	%> -->
<%-- 	<dhv:permission name="distributori-distributori-nonconformita-delete"> --%>
<!-- 		<input type="button" -->
<%-- 			value="Ripristina" --%>
<%-- 			onClick="javascript:this.form.action='DistributoriNonConformita.do?command=Restore&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%>';submit();"> --%>
<%-- 	</dhv:permission> --%>
<%-- 	<% --%>
<%--		} else if (TicketDetails.getClosed() != null) {
<%-- 	%> --%>
<%-- 	<dhv:permission name="reopen-reopen-view"> --%>
<!-- 		<input type="button" -->
<%-- 			value="<dhv:label name="button.reopen">Reopen</dhv:label>" --%>
<%-- 			onClick="javascript:this.form.action='DistributoriNonconformita.do?command=ReopenTicket&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();"> --%>
<%-- 	</dhv:permission> --%>
<%-- 	<% --%>
<%--		} else {
<%-- 	%> --%>
<%-- 	<dhv:permission name="distributori-distributori-nonconformita-edit"> --%>
<!-- 		<input type="button" -->
<%-- 			value="Modifica" --%>
<%-- 			onClick="javascript:this.form.action='DistributoriNonConformita.do?command=ModifyTicket&idMacchinetta=<%=idMacchinetta %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();"> --%>
<%-- 	</dhv:permission> --%>
	
<%-- 	<dhv:permission name="distributori-distributori-nonconformita-delete"> --%>
<%-- 		<% --%>
<%--		if ("searchResults".equals(request
<%--							.getParameter("return"))) {
<%-- 		%> --%>
<!-- 		<input type="button" -->
<%-- 			value="<dhv:label name="global.button.delete">Delete</dhv:label>" --%>
<%-- 			onClick="javascript:popURL('DistributoriNonConformita.do?command=ConfirmDelete&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');"> --%>
<%-- 		<% --%>
<%--		} else {
<%-- 		%> --%>
<!-- 		<input type="button" -->
<%-- 			value="<dhv:label name="global.button.delete">Delete</dhv:label>" --%>
<%-- 			onClick="javascript:popURL('DistributoritNonConformita.do?command=ConfirmDelete&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&popup=true', 'Delete_ticket','320','200','yes','no');"> --%>
<%-- 		<% --%>
<%--		}
<%-- 		%> --%>
<%-- 	</dhv:permission> --%>
<%-- 	<dhv:permission name="distributori-distributori-nonconformita-edit"> --%>
<!-- 		<input type="button" -->
<%-- 			value="<dhv:label name="global.button.close">Chiudi</dhv:label>" --%>

<%-- 			onClick="javascript:this.form.action='DistributoriNonConformita.do?command=Chiudi&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere Questa Non Conformità ? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};"> --%>

<%-- 	</dhv:permission> --%>
<%-- 	<% --%>
<%--	}}}else{--%>
	
	
	
<%--	if (TicketDetails.isTrashed()) {--%>
<%-- 	%> --%>
<%-- 	<dhv:permission name="distributori-distributori-nonconformita-delete"> --%>
<!-- 		<input type="button" -->
<%-- 			value="Ripristina" --%>
<%-- 			onClick="javascript:this.form.action='DistributoriNonConformita.do?command=Restore&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%>';submit();"> --%>
<%-- 	</dhv:permission> --%>
<%-- 	<% --%>
<%--	} else if (TicketDetails.getClosed() != null) {--%>
<%-- 	%> --%>
<%-- 	<dhv:permission name="reopen-reopen-view"> --%>
<!-- 		<input type="button" -->
<%-- 			value="<dhv:label name="button.reopen">Reopen</dhv:label>" --%>
<%-- 			onClick="javascript:this.form.action='DistributoriNonconformita.do?command=ReopenTicket&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();"> --%>
<%-- 	</dhv:permission> --%>
<%-- 	<% --%>
<%--		} else {--%>
<%-- 	%> --%>
<%-- 	<dhv:permission name="distributori-distributori-nonconformita-edit"> --%>
<!-- 		<input type="button" -->
<%-- 			value="Modifica" --%>
<%-- 			onClick="javascript:this.form.action='DistributoriNonConformita.do?command=ModifyTicket&idMacchinetta=<%=idMacchinetta %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();"> --%>
<%-- 	</dhv:permission> --%>
	
<%-- 	<dhv:permission name="distributori-distributori-nonconformita-delete"> --%>
<%-- 		<% --%>
<!-- 			if ("searchResults".equals(request
							.getParameter("return"))) { -->
<%-- 		%> --%>
<!-- 		<input type="button" -->
<%-- 			value="<dhv:label name="global.button.delete">Delete</dhv:label>" --%>
<%-- 			onClick="javascript:popURL('DistributoriNonConformita.do?command=ConfirmDelete&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');"> --%>
<%-- 		<% --%>
<%--			} else {
<%-- 		%> --%>
<!-- 		<input type="button" -->
<%-- 			value="<dhv:label name="global.button.delete">Delete</dhv:label>" --%>
<%-- 			onClick="javascript:popURL('DistributoritNonConformita.do?command=ConfirmDelete&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&popup=true', 'Delete_ticket','320','200','yes','no');"> --%>
<%-- 		<% --%>
<%-- 			}
<%-- 		%> --%>
<%-- 	</dhv:permission> --%>
<%-- 	<dhv:permission name="distributori-distributori-nonconformita-edit"> --%>
<!-- 		<input type="button" -->
<%-- 			value="<dhv:label name="global.button.close">Chiudi</dhv:label>" --%>

<%-- 			onClick="javascript:this.form.action='DistributoriNonConformita.do?command=Chiudi&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere Questa Non Conformità ? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};"> --%>

<%-- 	</dhv:permission> --%>
	
<%-- 	<%}} %> --%>
	
	
	
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="sanzionia.information">Scheda Non Conformita</dhv:label></strong></th>
		</tr>
		
		
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
    <td class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
   
     
      <td>
      		<%= toHtmlValue(TicketDetails.getIdControlloUfficiale()) %>
      </td>
  </tr>
  <%if(TicketDetails.getIdentificativo()!=null && !TicketDetails.getIdentificativo().equals("") ){ %>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Codice Non Conformita</dhv:label>
    </td>
   
     
      <td>
      		<%= toHtmlValue(TicketDetails.getIdentificativo()) %>
      </td>
    
  </tr>	

 
<%} %>

  <%@ include file="../nonconformita/nonconformita_view.jsp" %>

 
 

   </table>
   <br>
<br />




  <br><br>



<%
 if(user.getSiteId()!=-1){
	  
  if((""+user.getSiteId()).equals(aslMacchinetta)){ %>
	
		<%@ include file="../controlliufficiali/header_non_conformita.jsp" %>
		
		<%}} %>
	

</dhv:container>

</form>


