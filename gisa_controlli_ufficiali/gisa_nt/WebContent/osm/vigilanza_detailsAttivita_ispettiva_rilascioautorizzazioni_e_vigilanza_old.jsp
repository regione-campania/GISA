<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.vigilanza.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="TicList" class="org.aspcfs.modules.campioni.base.TicketList" scope="request"/>
<jsp:useBean id="SanzioniList" class="org.aspcfs.modules.sanzioni.base.TicketList" scope="request"/>
<jsp:useBean id="SequestriList" class="org.aspcfs.modules.sequestri.base.TicketList" scope="request"/>
<jsp:useBean id="NonCList" class="org.aspcfs.modules.nonconformita.base.TicketList" scope="request"/>
<jsp:useBean id="CategoriaRischioSoa" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.checklist.base.AuditList" scope="request"/>
<jsp:useBean id="ReatiList" class="org.aspcfs.modules.reati.base.TicketList" scope="request"/>
<jsp:useBean id="TamponiList" class="org.aspcfs.modules.tamponi.base.TicketList" scope="request"/>
<jsp:useBean id="DestinazioneDistribuzione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DistribuzionePartita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.osm.base.Organization" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<script language="javascript">
    document.onkeydown = f5() ;
</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_ufficiali_osm.js"></script>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>

<%@ include file="../initPage.jsp" %>
<form name="details" action="OsmVigilanza.do?command=ModifyTicket&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<td>
  <a href="Osm.do"><dhv:label name="">Osm</dhv:label></a> > 
  <a href="Osm.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="Osm.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Scheda OSM</dhv:label></a> >
  <a href="Osm.do?command=ViewVigilanza&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="vigilanza">Tickets</dhv:label></a> >
  <dhv:label name="campione.dettagli">Scheda Controllo Ufficiale</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId();
%>
<dhv:container name="osm" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
<dhv:container name="stabilimentivigilanza" selected="details" object="TicketDetails"
	param="<%= param1 %>"
	hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
	<%@ include file="ticket_header_include_vigilanza.jsp"%>
	<%
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-vigilanza-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-vigilanza-delete" ;
	
	%>
	
	<%@ include file="../controlliufficiali/header_controlli_ufficiali.jsp" %>
	
	<%@ include file="../controlliufficiali/controlli_ufficiali_stampa_verbale_ispezione.jsp" %>
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<%@ include file="../controlliufficiali/controlli_ufficiali_view.jsp" %>
		
		
		
	</table>
   <br>
   <br>
   <%-- INCLUSIONE DETTAGLIO SISTEMA ALLARME RAPIDO --%>
   
   <%@ include file="../controlliufficiali/controlli_ufficiali_allarmerapido_view.jsp" %>
   
   
   
   
      <% int punteggioAccumulato = 0; %>
   <%--if(TicketDetails.getTipoCampione()!=5) {  //controllo se è in sorveglianza
 
 --%> 
   
   
    <table cellpadding="4" cellspacing="4" width="100%">
     <tr>
   
   <%--if(TicketDetails.getInserisciContinua()){ --%>
   <%if(TicketDetails.getClosed()==null){
   
   
	// if(TicketDetails.isNcrilevate()==true ){

   
   %>
	   
	  
    	<td>
    		<a href="OsmCampioni.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&orgId=<%= (OrgDetails.getOrgId()==-1)?(TicketDetails.getOrgId()):(OrgDetails.getOrgId()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add" >Inserisci Campione</dhv:label></a>
    	</td>
    	
    	<td>
    		<a href="OsmTamponi.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&orgId=<%= (OrgDetails.getOrgId()==-1)?(TicketDetails.getOrgId()):(OrgDetails.getOrgId()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Inserisci Tampone</dhv:label></a>
    	</td>
    	
    	<%
    	if (NonCList.size()==0 ) 
    	{%>
    	<td>
    		<a href="OsmNonConformita.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&idIspezione=<%= TicketDetails.getTipoIspezione() %>&orgId=<%= (OrgDetails.getOrgId()==-1)?(TicketDetails.getOrgId()):(OrgDetails.getOrgId()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Inserisci Non Conformità Rilevate</dhv:label></a>
    	</td>
    	<%
		}   	
    	%>
    	<td>
    		<a href="OsmProvvedimenti.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&orgId=<%= (OrgDetails.getOrgId()==-1)?(TicketDetails.getOrgId()):(OrgDetails.getOrgId()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Inserisci Provvedimenti non causati da n.c. <br> a carico del soggetto ispezionato</dhv:label></a>
    	</td>
     </tr>
   </table>
   <%} %>
   
   <br/>
<%--}--%>
   
   <%if(Audit.size()!=0){ %>
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
		<th colspan="5" style="background-color: rgb(204, 255, 153);" >
			<strong>
				<dhv:label name="">Check List</dhv:label>
		    </strong>
		</th>
	    <tr>
		   <th>
      			Tipo Check List
   		   </th>
   		   <th><b><dhv:label name="">Punteggio Check List</dhv:label></b></th>
   		   <%--th><b><dhv:label name="">Livello Rischio</dhv:label></b></th--%>
   		   <th><b><dhv:label name="">Nuova Categoria Rischio</dhv:label></b></th>
     	   
   </tr>
   <%
            Iterator itr = Audit.iterator();
            if (itr.hasNext()){
              int rowid = 0;
              int i = 0;
              while (itr.hasNext()){
                i++;
                
                rowid = (rowid != 1 ? 1 : 2);
                org.aspcfs.modules.audit.base.Audit thisAudit = (org.aspcfs.modules.audit.base.Audit)itr.next();
                punteggioAccumulato += thisAudit.getLivelloRischio();
   
    %> 
   <tr class="row<%=rowid%>">
   <%if(TicketDetails.getTipoCampione()==3){%>
   <td>
   
      <a href="AccountsAuditOsm.do?command=View&id=<%= thisAudit.getId()%>"><%= toHtml(OrgCategoriaRischioList.getSelectedValue(thisAudit.getTipoChecklist())) %></a>
	</td>
	<%}else if(TicketDetails.getTipoCampione()==4){ %>
	 <td>
	    
	   <a href="AccountsAuditOsm.do?command=View&id=<%= thisAudit.getId()%>"><%= toHtml(OrgCategoriaRischioList2.getSelectedValue(thisAudit.getTipoChecklist())) %></a>
	</td>
	<%} %>
	<td>
	<%= ((thisAudit.getLivelloRischio()>0) ? (toHtml(String.valueOf(thisAudit.getLivelloRischio()))) : ("-")) %> 
	</td>
	<%--td>
      <%= ((thisAudit.getLivelloRischioFinale()>0) ? (toHtml(String.valueOf(thisAudit.getLivelloRischioFinale()))) : ("Non Aggiornato")) %>  
    </td--%>
   
    <td>
    
    <%if(thisAudit.getCategoria()==0 || thisAudit.getCategoria()==-1) {
    
        out.print("3");
        }else{ %>
      <%= toHtml(String.valueOf(thisAudit.getCategoria())) %>
    
    <%} %>
    </td>
   </tr>
   <%}}else{%>
   <tr class="containerBody">
      <td colspan="5">
        <dhv:label name="accounts.accounts_asset_list_include.NoAuditFound">Nessuna Check List compilata.</dhv:label>
      </td>
   </tr>
   <%}%> 
 	</table>
  	<br/>
  	<table cellpadding="4" cellspacing="0" width="100%" class="empty">
    <tr class="containerBody">
      <td name="punteggio" id="punteggio" nowrap class="formLabel">
        <dhv:label name=""><b>Punteggio Totale Check List:</b></dhv:label>
      </td>
    <td>
    	<%= request.getAttribute("PunteggioCheckList") %>
  
    </td>
    
  </tr>
    </table>
    </br>
	<%} 
	
	
     //controllo se è in sorveglianza
	%>
   <table cellpadding="4" cellspacing="0" width="100%" class="details">
		
			<th colspan="5" style="background-color: rgb(204, 255, 153);" ><strong>
				<dhv:label name="">Attività Svolte Durante il Controllo Ufficiale</dhv:label>
		    </strong></th>
	    </tr>
	   
		<th>
      Tipo Attività
    </th>
    <th valign="center" align="left">
      <strong><dhv:label name="">Codice Attività</dhv:label></strong>
    </th>
     <th><b><dhv:label name="sanzionia.data_richiesta">Data Esecuzione Attività</dhv:label></b></th>
    <%-- th><b><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></b></th --%>
    <%-- th><b><dhv:label name="ticket.estResolutionDate">Est. Resolution Date</dhv:label></b></th --%>
    <th><b><dhv:label name="sanzionia.richiedente">Punteggio</dhv:label></b></th>
     <%--th><b><dhv:label name="sanzionia.richiedente">Esito Campionamento</dhv:label></b></th--%>
    <%--th><b><dhv:label name="sanzionia.richiedente">Modificato</dhv:label></b></th--%>
	<%-- th><b><dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label></b></th --%>
  </tr>
	<%
	
    Iterator j = TicList.iterator();
	
    if ( j.hasNext() ) {
      int rowid = 0;
      int i =0;
     //if(thisTic.getIdControlloUfficiale().equals(TicketDetails.getPaddedId())){
      while (j.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.campioni.base.Ticket thisTic = (org.aspcfs.modules.campioni.base.Ticket)j.next();
       //if(TicketDetails.getPaddedId()==thisTic.getIdControlloUfficiale()){
        punteggioAccumulato += thisTic.getPunteggio();
  %>
  
  <tr class="row<%= rowid %>">
      <td rowspan="2" width="10" valign="top" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <%-- To display the menu, pass the actionId, accountId and the contactId--%>
        <%if(thisTic.getTipologia()==2){ %>
        <label><b>Campione Num Verbale : <%=thisTic.getLocation() %></b></label>
        <%} %>
        <%--<a href="javascript:displayMenu('select<%= i %>','menuTic','<%= OrgDetails.getId() %>','<%= thisTic.getId() %>', '<%= thisTic.isTrashed() || OrgDetails.isTrashed() %>', '<%= thisTic.isClosed() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTic');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>--%>
      </td>
      
		<td  valign="top" nowrap>
			<%-- a href="TroubleTickets_asl.do?command=Details&id=<%= thisTic.getId() %>"><%= thisTic.getPaddedId() %></a --%>
			<%-- a href="AccountTickets.do?command=RichiesteDetails&id=<%= thisTic.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTic.getPaddedId() %></a --%>
			<a href="OsmCampioni.do?command=TicketDetails&id=<%= thisTic.getId() %>&orgId=<%= thisTic.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTic.getIdentificativo() %></a>
		</td>
      <%-- 		<td width="20%" valign="top" nowrap>
			<dhv:label name="<%="richieste." + thisTic.getTipo_richiesta() %>"><%=thisTic.getTipo_richiesta()%></dhv:label>
		</td>
	  --%>	
		<td valign="top" class="row<%= rowid %>">
      <% if(!User.getTimeZone().equals(thisTic.getAssignedDate())){%>
      <zeroio:tz timestamp="<%= thisTic.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisTic.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisTic.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
      <% } %>
		</td>
		<%if(thisTic.getPunteggio() > -1) {%>
		<td width="10%" valign="middle"><%= thisTic.getPunteggio() %></td>	
		<%}else{%>
		<td>-
		</td>
		<%} %>
		<%-- da aggiungere il valore del richiedente 
		<td>
		<%= toHtml(thisTic.getCompanyName()) %><dhv:evaluate if="<%= !(thisTic.getCompanyEnabled()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>--%>
		<%-- %>td width="45%" valign="top">
			<zeroio:tz timestamp="<%= thisTic.getModified() %>" dateOnly="false" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
		</td--%>
	</tr>
	<tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
        <%
          if (1==1) {
        	
            Iterator files = thisTic.getFiles().iterator();
            while (files.hasNext()) {
            	
            
              FileItem thisFile = (FileItem)files.next();
              if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
        %>
          <a href="OsmVigilanzaDocuments.do?command=Download&stream=true&tId=<%= thisTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
        <%
              }
            }
          }
        %>
        <%= toHtml(thisTic.getProblemHeader()) %>&nbsp;
        <% if (thisTic.getClosed() == null) { %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
    </tr>
    
  <%}%>
  <%} else {%>
  
    
  <%}%>
  <%
	
    Iterator a = TamponiList.iterator();
	
    if ( a.hasNext() ) {
      int rowid = 0;
      int i =0;
     //if(thisTic.getIdControlloUfficiale().equals(TicketDetails.getPaddedId())){
      while (a.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.tamponi.base.Ticket thisTam = (org.aspcfs.modules.tamponi.base.Ticket)a.next();
       //if(TicketDetails.getPaddedId()==thisTic.getIdControlloUfficiale()){
        punteggioAccumulato += thisTam.getPunteggio();
  %>
  <tr class="row<%= rowid %>">
      <td rowspan="2" width="10" valign="top" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <%-- To display the menu, pass the actionId, accountId and the contactId--%>
        <%if(thisTam.getTipologia()==7){ %>
        <label><b>Tampone</b></label>
        <%} %>
        <%--<a href="javascript:displayMenu('select<%= i %>','menuTic','<%= OrgDetails.getId() %>','<%= thisTic.getId() %>', '<%= thisTic.isTrashed() || OrgDetails.isTrashed() %>', '<%= thisTic.isClosed() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTic');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>--%>
      </td>
      
		<td  valign="top" nowrap>
			<%-- a href="TroubleTickets_asl.do?command=Details&id=<%= thisTic.getId() %>"><%= thisTic.getPaddedId() %></a --%>
			<%-- a href="AccountTickets.do?command=RichiesteDetails&id=<%= thisTic.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTic.getPaddedId() %></a --%>
			<a href="OsmTamponi.do?command=TicketDetails&id=<%= thisTam.getId() %>&orgId=<%= thisTam.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTam.getIdentificativo() %></a>
		</td>
      <%-- 		<td width="20%" valign="top" nowrap>
			<dhv:label name="<%="richieste." + thisTic.getTipo_richiesta() %>"><%=thisTic.getTipo_richiesta()%></dhv:label>
		</td>
	  --%>	
		<td valign="top" class="row<%= rowid %>">
      <% if(!User.getTimeZone().equals(thisTam.getAssignedDate())){%>
      <zeroio:tz timestamp="<%= thisTam.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisTam.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisTam.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
      <% } %>
		</td>
		<%if(thisTam.getPunteggio() > -1) {%>
		<td width="10%" valign="middle"><%= thisTam.getPunteggio() %></td>	
		<%}else{%>
		<td>-
		</td>
		<%} %>
		<%-- da aggiungere il valore del richiedente 
		<td>
		<%= toHtml(thisTic.getCompanyName()) %><dhv:evaluate if="<%= !(thisTic.getCompanyEnabled()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>--%>
		<%-- %>td width="45%" valign="top">
			<zeroio:tz timestamp="<%= thisTic.getModified() %>" dateOnly="false" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
		</td--%>
	</tr>
	<tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
        <%
          if (1==1) {
        	
            Iterator files = thisTam.getFiles().iterator();
            while (files.hasNext()) {
            	
            
              FileItem thisFile = (FileItem)files.next();
              if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
        %>
          <a href="OsmVigilanzaDocuments.do?command=Download&stream=true&tId=<%= thisTam.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
        <%
              }
            }
          }
        %>
        <%= toHtml(thisTam.getProblemHeader()) %>&nbsp;
        <% if (thisTam.getClosed() == null) { %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
    </tr>
    
  <%}%>
  <%} else {%>
  
    
  <%}%>
  
  <%
	
    Iterator z = NonCList.iterator();
	
    if ( z.hasNext() ) {
      int rowid = 0;
      int i =0;
     //if(thisTic.getIdControlloUfficiale().equals(TicketDetails.getPaddedId())){
      while (z.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.nonconformita.base.Ticket thisNonC = (org.aspcfs.modules.nonconformita.base.Ticket)z.next();
       //if(TicketDetails.getPaddedId()==thisTic.getIdControlloUfficiale()){
    if(TicketDetails.getTipoCampione()!=5){ 
        punteggioAccumulato += thisNonC.getPunteggio();
    }
  %>
  <tr class="row<%= rowid %>">
      <td rowspan="2" width="10" valign="top" nowrap>
        
        <%if(thisNonC.getTipologia()==8){ %>

        <label><b>Non Conformità Rilevate</b></label>

        <%} %>
       </td>
      
		<td  valign="top" nowrap>
			<%-- a href="TroubleTickets_asl.do?command=Details&id=<%= thisTic.getId() %>"><%= thisTic.getPaddedId() %></a --%>
			<%-- a href="AccountTickets.do?command=RichiesteDetails&id=<%= thisTic.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTic.getPaddedId() %></a --%>
			<a href="OsmNonConformita.do?command=TicketDetails&idIspezione=<%= TicketDetails.getTipoIspezione() %>&id=<%= thisNonC.getId() %>&orgId=<%= thisNonC.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisNonC.getIdentificativo() %></a>
		</td>
      <%-- 		<td width="20%" valign="top" nowrap>
			<dhv:label name="<%="richieste." + thisTic.getTipo_richiesta() %>"><%=thisTic.getTipo_richiesta()%></dhv:label>
		</td>
	  --%>	
		<td valign="top" class="row<%= rowid %>">
      <% if(!User.getTimeZone().equals(thisNonC.getAssignedDate())){%>
      <zeroio:tz timestamp="<%= thisNonC.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisNonC.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisNonC.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
      <% } %>
		</td>
		<%if(thisNonC.getPunteggio() > -1 && TicketDetails.getTipoCampione()!=5) {%>
		<td width="10%" valign="middle"><%= thisNonC.getPunteggio() %></td>	
		<%}else{%>
		<td>Non Previsto
		</td>
		<%} %>
		<%-- da aggiungere il valore del richiedente 
		<td>
		<%= toHtml(thisTic.getCompanyName()) %><dhv:evaluate if="<%= !(thisTic.getCompanyEnabled()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>--%>
		<%-- %>td width="45%" valign="top">
			<zeroio:tz timestamp="<%= thisTic.getModified() %>" dateOnly="false" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
		</td--%>
	</tr>
	<tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
        <%
          if (1==1) {
        	
            Iterator files = thisNonC.getFiles().iterator();
            while (files.hasNext()) {
            	
            
              FileItem thisFile = (FileItem)files.next();
              if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
        %>
          <a href="OsmVigilanzaDocuments.do?command=Download&stream=true&tId=<%= thisNonC.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
        <%
              }
            }
          }
        %>
        <%= toHtml(thisNonC.getProblemHeader()) %>&nbsp;
        <% if (thisNonC.getClosed() == null) { 
        	if (TicketDetails.isChiusura_attesa_esito()==true)
        	{
        		%>
        		  [<font color="orange">Pratica Chiusa Temporaneamente in Attesa di Esito di Campioni e/o Tamponi</font>]
        		<%
        	}
        	else
        	{
        		%>
        		  [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        		<%
        	}
        %>
        
        <%} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
    </tr>
    
  <%}%>
  <%} else {%>
  
   
  <%}%>
  <%--/table--%>

  </table>
   <br/>

		<% if(TicketDetails.getTipoCampione()!=5){ // sorveglianza %>
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="3">
      <strong><dhv:label name="">Punteggio Controllo Ufficiale</dhv:label></strong>
    </th>
	</tr>
  <dhv:evaluate if="<%= TicketDetails.getEstimatedResolutionDate()!=null %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzionia.data_ispezione">Data</dhv:label>
    </td>
    <td>
    <zeroio:tz
				timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" dateOnly="true"
				timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"
				showTimeZone="false" default="&nbsp;" /> 
    </td>
  </tr>
  </dhv:evaluate>
 
<dhv:evaluate if="<%= (TicketDetails.getPunteggio() > -1) %>">
<tr class="containerBody">
      <td name="punteggio" id="punteggio" nowrap class="formLabel">
        <dhv:label name="">Punteggio Accumulato </dhv:label>
      </td>
    <td>
    	<%= toHtmlValue(TicketDetails.getPunteggio()) %>
      <input type="hidden" name="punteggio" id="punteggio" size="20" maxlength="256" />
    </td>
    <%-- 
    <%if(TicketDetails.getPunteggio()<=3){ %>
    <td>Esito Controllo Ufficiale Favorevole</td>
    <%} %>--%>
  </tr>
 </dhv:evaluate>
 <%//if(punteggioAccumulato<=3) {%>
 
 <dhv:evaluate>
<tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Esito </dhv:label>
      </td>
    <td>
    	
    </td>
  </tr>
 </dhv:evaluate> 
 <%//} %> 
<dhv:evaluate if="<%= hasText(TicketDetails.getSolution()) %>">
<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Ulteriori Note</dhv:label>
    </td>
    <td>
      <%= toString(TicketDetails.getSolution()) %>
    </td>
    </tr>
</dhv:evaluate>
    </table><% } 
		else{
		
		
    %>
    
     <%--%>table cellpadding="4" cellspacing="0" width="100%" class="empty">
    <tr class="containerBody">
      <td name="punteggio" id="punteggio" nowrap class="formLabel">
        <dhv:label name=""><b>Punteggio Totale Check List:</b></dhv:label>
      </td>
    <td>
    	<%= request.getAttribute("PunteggioCheckList") %>
  
    </td>
    
  </tr>
    </table--%>
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="3">
      <strong><dhv:label name="">Esito Sottoattività Controllo Ufficiale</dhv:label></strong>
    </th>
	</tr>
  <dhv:evaluate if="<%= TicketDetails.getEstimatedResolutionDate()!=null %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzionia.data_ispezione">Data</dhv:label>
    </td>
    <td>
    <zeroio:tz
				timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" dateOnly="true"
				timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"
				showTimeZone="false" default="&nbsp;" /> 
    </td>
  </tr>
  </dhv:evaluate>
 
<dhv:evaluate if="<%= (TicketDetails.getPunteggio() > -1) %>">
<tr class="containerBody">
      <td name="punteggio" id="punteggio" nowrap class="formLabel">
        <dhv:label name="">Punteggio Accumulato</dhv:label>
      </td>
    <td>
    	<%= toHtmlValue(TicketDetails.getPunteggio()) %>
      <input type="hidden" name="punteggio" id="punteggio" size="20" maxlength="256" />
    </td>
  </tr>
 </dhv:evaluate>
 <%//if(punteggioAccumulato<=3) {%>
 
 <dhv:evaluate>
<tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Esito </dhv:label>
      </td>
    <td>
    	
    </td>
  </tr>
 </dhv:evaluate> 
 <%//} %> 
<dhv:evaluate if="<%= hasText(TicketDetails.getSolution()) %>">
<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Ulteriori Note</dhv:label>
    </td>
    <td>
      <%= toString(TicketDetails.getSolution()) %>
    </td>
    </tr>
</dhv:evaluate>
    </table>
    <%} %>
    
&nbsp;
<br />

<%--} --%>
	
	
	<%@ include file="../controlliufficiali/header_controlli_ufficiali.jsp" %>
	
	
</dhv:container>
</dhv:container>
</form>


<%
String flag=(String)request.getAttribute("Chiudi");
if(flag!=null){
if(flag.equals("1")){
	
%>
<script>

alert("Questo Controllo Ufficiale non puo essere chiuso . Ci sono Attività collegate che non sono state ancora chiuse.");
</script>


<% 

}else{
%>
<script>

alert("Questo Controllo Ufficiale non puo essere chiuso. Assicurarsi di aver ggiornato il livello di rischio per le Checklist.");
</script>


<%	


}	
}


%>