<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.nonconformita.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.nonconformita.base.Ticket" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SanzioniList" class="org.aspcfs.modules.sanzioni.base.TicketList" scope="request"/>
<jsp:useBean id="SequestriList" class="org.aspcfs.modules.sequestri.base.TicketList" scope="request"/>
<jsp:useBean id="ReatiList" class="org.aspcfs.modules.reati.base.TicketList" scope="request"/>
<jsp:useBean id="FollowupList" class="org.aspcfs.modules.followup.base.TicketList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="TitoloNucleo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDue" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoTre" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoQuattro" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoCinque" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoSei" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoSette" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoOtto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoNove" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDieci" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ConseguenzePositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ResponsabilitaPositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformatiValori" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.oia.base.OiaNodo" scope="request"/>
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

<%String idMacchinetta=""+OrgDetails.getId(); %>
<body onload="verificaChiusuraNc(<%=request.getAttribute("Chiudi")%>,<%=request.getAttribute("numSottoAttivita")%>,'<%=request.getAttribute("Messaggio2")%>',<%=TicketDetails.getIdControlloUfficiale() %>,'OiaVigilanza')">

<form name="details" action="OiaNonConformita.do?command=ModifyTicket&idNodo=<%=idMacchinetta %>&auto-populate=true" method="post">
<input type = "hidden" name = "idC" value = "<%=TicketDetails.getIdControlloUfficiale() %>">

<table class="trails" cellspacing="0">
<tr>	
<td>
<td>
  <a href="Oia.do?command=SearchForm"><dhv:label name="">Autorità Competenti</dhv:label></a> > 
  
  <a href="Oia.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>&idNodo=<%=idMacchinetta %>"><dhv:label name="">Scheda Dipartimento</dhv:label></a> >
  <a href="OiaVigilanza.do?command=ViewVigilanza&orgId=<%=TicketDetails.getOrgId()%>&idNodo=<%=idMacchinetta %>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
  <a href="OiaVigilanza.do?command=TicketDetails&idNodo=<%=idMacchinetta %>&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
  <%--a href="Oia.do?command=ViewNonConformita&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="nonconformita">Tickets</dhv:label></a> --%>

<dhv:label name="campione.dettagli">Scheda Non Conformità Rilevata</dhv:label>

</td>
</tr>
</table>

<dhv:container name="asl" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId()+"&idNodo="+idMacchinetta %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>


	
	
	<%UserBean user=(UserBean)session.getAttribute("User");
   
  String aslMacchinetta=(String)request.getAttribute("aslMacchinetta");

  if(user.getSiteId()!=-1){
	  
  if((""+user.getSiteId()).equals(aslMacchinetta)){
	
	
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="oia-oia-nonconformita-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='OiaNonConformita.do?command=Restore&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='OiaNonconformita.do?command=ReopenTicket&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
		} else {
	%>
	<dhv:permission name="oia-oia-nonconformita-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='OiaNonConformita.do?command=ModifyTicket&idNodo=<%=idMacchinetta %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="oia-oia-nonconformita-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaNonConformita.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiatNonConformita.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="oia-oia-nonconformita-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"

			onClick="javascript:this.form.action='OiaNonConformita.do?command=Chiudi&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere Questa Non Conformità ? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">

	</dhv:permission>
	<%
		}}}else{
	
	
	
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="oia-oia-nonconformita-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='OiaNonConformita.do?command=Restore&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='OiaNonconformita.do?command=ReopenTicket&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
		} else {
	%>
	<dhv:permission name="oia-oia-nonconformita-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='OiaNonConformita.do?command=ModifyTicket&idNodo=<%=idMacchinetta %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="oia-oia-nonconformita-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaNonConformita.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiatNonConformita.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="oia-oia-nonconformita-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"

			onClick="javascript:this.form.action='OiaNonConformita.do?command=Chiudi&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere Questa Non Conformità ? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">

	</dhv:permission>
	
	<%}} %>
	
	
	
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
<%

int idNodo = OrgDetails.getId();

request.setAttribute("idNodo",idNodo);
%>
  <%@ include file="../nonconformita/nonconformita_view.jsp" %>

 
 

   </table>
   <br>
<br />




  <br><br>



<%
 if(user.getSiteId()!=-1){
	  
  if((""+user.getSiteId()).equals(aslMacchinetta)){
	
	
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="oia-oia-nonconformita-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='OiaNonConformita.do?command=Restore&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='OiaNonconformita.do?command=ReopenTicket&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
		} else {
	%>
	<dhv:permission name="oia-oia-nonconformita-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='OiaNonConformita.do?command=ModifyTicket&idNodo=<%=idMacchinetta %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%--
  <dhv:permission name="quotes-view">
    <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
      <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Display&productId=<%= TicketDetails.getProductId() %>&id=<%= TicketDetails.getId() %>';submit();"/>
    </dhv:evaluate>
  </dhv:permission>
  --%>
	<dhv:permission name="oia-oia-nonconformita-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaNonConformita.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaNonConformita.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="oia-oia-nonconformita-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"

			onClick="javascript:this.form.action='OiaNonConformita.do?command=Chiudi&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere Questa Non Conformità? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">

	</dhv:permission>
	<%
		}}}else{
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="oia-oia-nonconformita-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='OiaNonConformita.do?command=Restore&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='OiaNonconformita.do?command=ReopenTicket&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
		} else {
	%>
	<dhv:permission name="oia-oia-nonconformita-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='OiaNonConformita.do?command=ModifyTicket&idNodo=<%=idMacchinetta %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%--
  <dhv:permission name="quotes-view">
    <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
      <input type="button" value="<dhv:label name="ticket.generateQuote">Generate Quote</dhv:label>" onClick="javascript:this.form.action='Quotes.do?command=Display&productId=<%= TicketDetails.getProductId() %>&id=<%= TicketDetails.getId() %>';submit();"/>
    </dhv:evaluate>
  </dhv:permission>
  --%>
	<dhv:permission name="oia-oia-nonconformita-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaNonConformita.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaNonConformita.do?command=ConfirmDelete&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>&orgId=<%=OrgDetails.getOrgId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="oia-oia-nonconformita-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"

			onClick="javascript:this.form.action='OiaNonConformita.do?command=Chiudi&idNodo=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere Questa Non Conformità? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">

	</dhv:permission>
	
	
	
	<%}} %>
	

</dhv:container>

</form>


