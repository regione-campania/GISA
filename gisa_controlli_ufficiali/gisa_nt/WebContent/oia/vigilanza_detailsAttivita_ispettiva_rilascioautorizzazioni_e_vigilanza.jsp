<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />

<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.vigilanza.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="TicList" class="org.aspcfs.modules.campioni.base.TicketList" scope="request"/>
<jsp:useBean id="SanzioniList" class="org.aspcfs.modules.sanzioni.base.TicketList" scope="request"/>
<jsp:useBean id="SequestriList" class="org.aspcfs.modules.sequestri.base.TicketList" scope="request"/>
<jsp:useBean id="NonCList" class="org.aspcfs.modules.nonconformita.base.TicketList" scope="request"/>
<jsp:useBean id="OsservazioniList" class="org.aspcfs.modules.osservazioni.base.OsservazioniList" scope="request"/>

<jsp:useBean id="Audit" class="org.aspcfs.checklist.base.AuditList" scope="request"/>
<jsp:useBean id="ReatiList" class="org.aspcfs.modules.reati.base.TicketList" scope="request"/>
<jsp:useBean id="TamponiList" class="org.aspcfs.modules.tamponi.base.TicketList" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.oia.base.Organization" scope="request"/>
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request" />
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DistribuzionePartita" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DestinazioneDistribuzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />

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

<jsp:useBean id="messaggioPost" class="java.lang.String" scope="request" />

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>

<%@ include file="../initPage.jsp" %>
<form name="details" action="AccountVigilanza.do?command=ModifyTicket&auto-populate=true" method="post">
<%-- Trails --%>

<table class="trails" cellspacing="0">
<tr>
<td>
<td>
  <a href="Oia.do?command=SearchForm"><dhv:label name="">Autorità Competenti</dhv:label></a> > 
  <a href="OiaVigilanza.do?command=ViewVigilanza&aslMacchinetta=<%=(String)request.getAttribute("aslMacchinetta") %>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="vigilanza">Tickets</dhv:label></a> >
  <dhv:label name="campione.dettagli">Scheda Controllo Ufficiale</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%


	String param1 = "id=" + TicketDetails.getId();
%>
<dhv:container name="asl" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId()%>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>

	<%UserBean user=(UserBean)session.getAttribute("User");
	  String aslMacchinetta=(String)(OrgDetails.getSiteId()+"");
 	  if(user.getSiteId()!=-1){
	  if(aslMacchinetta!=null && (""+user.getSiteId()).equals(aslMacchinetta)){
  
  %>

	
  
  %>
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="vigilanza-vigilanza-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='OiaVigilanza.do?command=Restore&orgId=<%=OrgDetails.getOrgId() %>&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	
	<%
		} else {
	%>
	<dhv:permission name="vigilanza-vigilanza-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='OiaVigilanza.do?command=ModifyTicket&orgId=<%=OrgDetails.getOrgId() %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="vigilanza-vigilanza-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaVigilanza.do?command=ConfirmDelete&orgId=<%=OrgDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		 <input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaVigilanza.do?command=ConfirmDelete&orgId=<%=OrgDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');"> 
		<%
			}
		%>
	</dhv:permission>
	<%	if (TicketDetails.isControllo_chiudibile()==true)
	{
	%>
	
		<dhv:permission name="vigilanza-vigilanza-edit">
	<a class="ovalbutton" href = "#dialog5" name="modal" ><span >Chiudi</span></a>
	</dhv:permission>
	
	
	<%}
	else
	{
		%>
			<input type="button"
			value="Chiudi in Attesa di Esito"
			title="Chiude il Controllo in maniera momentanea in attesa di esito di campioni e tamponi"
			onClick="javascript:alert('ATTENZIONE! Stai per chiudere il controllo in maniera momentanea in attesa di esito di campioni e tamponi. Non sara possibile modificare i dati del controllo e delle attivita collegate.Si potra inserire solo l esito dei campioni e tamponi)');this.form.action='<%=TicketDetails.getURlDettaglio() %>Vigilanza.do?command=ChiudiTemp&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
	
		<%		
			}
	
	%>
	<%
		}}}else
			
		{
			
			if (TicketDetails.isTrashed()) {
		%>
		<dhv:permission name="vigilanza-vigilanza-delete">
			<input type="button"
				value="Ripristina"
				onClick="javascript:this.form.action='OiaVigilanza.do?command=Restore&orgId=<%=OrgDetails.getOrgId() %>&id=<%= TicketDetails.getId()%>';submit();">
		</dhv:permission>
		<%
			} else if (TicketDetails.getClosed() != null) {
		%>
	
	<dhv:permission name="reopen-reopen-view">
		<%--input type="button"
			value="Scarica Rapporto Conclusivo"
			onClick="javascript:this.form.action='OiaVigilanza.do?command=DownloadRapportoConclusivo&fid=-1&idNodo=<%=OrgDetails.getId()  %>&orgId=<%=TicketDetails.getOrgId() %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();"--%>

	
	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/> 
	<a href="GestioneAllegatiUpload.do?command=DownloadByTipo&orgId=<%=TicketDetails.getOrgId() %>&id=<%= TicketDetails.getId()%>&tipoAllegato=RapportoChiusura"><input type="button" value="Scarica rapporto conclusivo"></input></a>
		</dhv:permission>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='OiaVigilanza.do?command=ReopenTicket&orgId=<%=OrgDetails.getOrgId()  %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
		<%
			} else {
		%>
		<dhv:permission name="vigilanza-vigilanza-edit">
			<input type="button"
				value="Modifica"
				onClick="javascript:this.form.action='OiaVigilanza.do?command=ModifyTicket&idNodo=<%=OrgDetails.getOrgId() %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
		</dhv:permission>
		
		<dhv:permission name="vigilanza-vigilanza-delete">
			<%
				if ("searchResults".equals(request
									.getParameter("return"))) {
			%>
			<input type="button"
				value="<dhv:label name="global.button.delete">Delete</dhv:label>"
				onClick="javascript:popURL('OiaVigilanza.do?command=ConfirmDelete&idNodo=<%=OrgDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
			<%
				} else {
			%>
			 <input type="button"
				value="<dhv:label name="global.button.delete">Delete</dhv:label>"
				onClick="javascript:popURL('OiaVigilanza.do?command=ConfirmDelete&idNodo=<%=OrgDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');"> 
			<%
				}
			%>
		</dhv:permission>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<dhv:permission name="vigilanza-vigilanza-edit">
	<a class="ovalbutton" href = "#dialog5" name="modal" ><span >Chiudi</span></a>
	</dhv:permission>
	
		<%}} %>
	
   </br></br>
   
   		<% if (messaggioPost!=null && !messaggioPost.equals("null")){ %>
	<font color="red"><%=messaggioPost %></font>
	<%} %>
   
   <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>

<dhv:permission name="cu_stampa-view">
<div align="right">
<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
<input type="button" title="Stampa Riepilogativa del Controllo Ufficiale" value="Stampa Riepilogativa del Controllo Ufficiale"	onClick="openRichiestaPDFControlli('<%= TicketDetails.getId() %>');">
</div>
</dhv:permission>

   <table cellpadding="4" cellspacing="0" width="100%" class="details">
		
		<%@ include file="../controlliufficiali/controlli_ufficiali_view.jsp" %>
			
	</table>
 
   
      <% int punteggioAccumulato = 0; %>
   <%--if(TicketDetails.getTipoCampione()!=5) {  //controllo se è in sorveglianza
 
 --%> 
   
   
   
    <table cellpadding="4" cellspacing="0" width="100%" >
     <tr>
   
   <%--if(TicketDetails.getInserisciContinua()){ --%>
  
   
   <% user=(UserBean)session.getAttribute("User");
	 
	 
  %>
   
   <%if(TicketDetails.getClosed()==null){
 
      %>
	  
    	    	
    	<%
    	if (NonCList.size()==0  && TicketDetails.getTipoCampione()!=22) 
    	{
    	%>
    	<td>
    		<a href="OiaOsservazioni.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&idIspezione=<%= TicketDetails.getTipoIspezione() %>&orgId=<%= (OrgDetails.getOrgId()==-1)?(TicketDetails.getOrgId()):(OrgDetails.getOrgId()) %><%= addLinkParams(request, "popup|popupType|actionId") %>&aslMacchinetta=<%=aslMacchinetta %>&idMacchinetta=<%=OrgDetails.getOrgId()%>&idNodo=<%=OrgDetails.getOrgId() %>"><dhv:label name="accounts.richiesta.add">Inserisci Osservazioni/Raccomandazioni</dhv:label></a>
    	</td>
    	
    	<%
    	}%>
    	
    	  	
 	
    	
     </tr>
   </table>
   <%} 
  else{
    if(TicketDetails.getClosed()==null  && TicketDetails.getTipoCampione()!=22){
   
   
    
    	%>
    	<td> <!-- SOLO AUDIT MOSTRA LINK -->
    			<dhv:evaluate if="<%=TicketDetails.getTipoCampione() == 3 || TicketDetails.getTipoCampione() == 23%>">
    		<br><b><a href="OiaOsservazioni.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&idIspezione=<%= TicketDetails.getTipoIspezione() %>&orgId=<%= (OrgDetails.getOrgId()==-1)?(TicketDetails.getOrgId()):(OrgDetails.getOrgId()) %><%= addLinkParams(request, "popup|popupType|actionId") %>&aslMacchinetta=<%=aslMacchinetta %>&idMacchinetta=<%=OrgDetails.getOrgId()%>&idNodo=<%=OrgDetails.getOrgId() %>"><dhv:label name="accounts.richiesta.add">Inserisci Osservazioni/Raccomandazioni</dhv:label></b></a>
    	</dhv:evaluate>
    	</td>
 	
    	
     </tr>
   </table>


<%}} %>
   
   <br/>

	
   <table cellpadding="4" cellspacing="0" width="100%" class="details" >
		
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
     <th><b><dhv:label name="sanzionia.richiedente">Punteggio</dhv:label></b></th>

	
 
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
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <%-- To display the menu, pass the actionId, accountId and the contactId--%>
        <%if(thisNonC.getTipologia()==8){ %>

        <label><b>Non Conformità Rilevate</b></label>

        <%} %>
        <%--<a href="javascript:displayMenu('select<%= i %>','menuTic','<%= OrgDetails.getId() %>','<%= thisTic.getId() %>', '<%= thisTic.isTrashed() || OrgDetails.isTrashed() %>', '<%= thisTic.isClosed() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTic');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>--%>
      </td>
      
		<td  valign="top" nowrap>
			<a href="OiaNonConformita.do?command=TicketDetails&idIspezione=<%= TicketDetails.getTipoIspezione() %>&id=<%= thisNonC.getId() %>&idNodo=<%=OrgDetails.getOrgId() %>&orgId=<%= thisNonC.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>&idNodo=<%=OrgDetails.getOrgId() %>"><%= thisNonC.getIdentificativo() %></a>
		</td>
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
          <a href="OiaVigilanzaDocuments.do?command=Download&stream=true&tId=<%= thisNonC.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
        <%
              }
            }
          }
        %>
        <%= toHtml(thisNonC.getProblemHeader()) %>&nbsp;
        <% if (thisNonC.getClosed() == null) { %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
    </tr>
    
  <%}%>
  <%} else {%>
  
   
  <%}%>
  <%--/table--%>



  <%
	
     z = OsservazioniList.iterator();
	
    if ( z.hasNext() ) {
      int rowid = 0;
      int i =0;
     //if(thisTic.getIdControlloUfficiale().equals(TicketDetails.getPaddedId())){
      while (z.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.osservazioni.base.Osservazioni thisNonC = (org.aspcfs.modules.osservazioni.base.Osservazioni)z.next();
       //if(TicketDetails.getPaddedId()==thisTic.getIdControlloUfficiale()){
    if(TicketDetails.getTipoCampione()!=5){ 
        punteggioAccumulato += thisNonC.getPunteggio();
    }
  %>
  <tr class="row<%= rowid %>">
      <td rowspan="2" width="10" valign="top" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <%-- To display the menu, pass the actionId, accountId and the contactId--%>
        <%if(thisNonC.getTipologia()==16){ %>

        <label><b>Osservazione/Raccomandazione</b></label>

        <%} %>
        <%--<a href="javascript:displayMenu('select<%= i %>','menuTic','<%= OrgDetails.getOrgId() %>','<%= thisTic.getId() %>', '<%= thisTic.isTrashed() || OrgDetails.isTrashed() %>', '<%= thisTic.isClosed() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTic');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>--%>
      </td>
      
		<td  valign="top" nowrap>
			<a href="OiaOsservazioni.do?command=TicketDetails&idIspezione=<%= TicketDetails.getTipoIspezione() %>&id=<%= thisNonC.getId() %>&idNodo=<%=OrgDetails.getOrgId() %>&orgId=<%= thisNonC.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>&idNodo=<%=OrgDetails.getOrgId() %>"><%= thisNonC.getIdentificativo() %></a>
		</td>
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
		</tr>
	<tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
     
        <%= toHtml(thisNonC.getProblemHeader()) %>&nbsp;
        <% if (thisNonC.getClosed() == null) { %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
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
   
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="3">
      <strong><dhv:label name="">Esito Controllo Ufficiale</dhv:label></strong>
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
     
    <td>
    	<%= toHtmlValue(TicketDetails.getPunteggio()) %>
      <input type="hidden" name="punteggio" id="punteggio" size="20" maxlength="256" />
    </td>
    <%if(TicketDetails.getPunteggio()<=3){ %>
    <td>Esito Controllo Ufficiale Favorevole</td>
    <%} %>
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
&nbsp;
<br />

<%--} --%>

<% user=(UserBean)session.getAttribute("User");
	//String idaslMacchinetta=(String)request.getAttribute("");
  
	
  
 
  
  
 
  
 
  
  if(user.getSiteId()!=-1){
	  
  if((""+user.getSiteId()).equals(aslMacchinetta)){
  
  %>
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="vigilanza-vigilanza-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='OiaVigilanza.do?command=Restore&idNodo=<%=OrgDetails.getOrgId() %>&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	
	<%
		} else {
	%>
	<dhv:permission name="vigilanza-vigilanza-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='OiaVigilanza.do?command=ModifyTicket&idNodo=<%=OrgDetails.getOrgId() %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="vigilanza-vigilanza-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaVigilanza.do?command=ConfirmDelete&idNodo=<%=OrgDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		 <input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('OiaVigilanza.do?command=ConfirmDelete&idNodo=<%=OrgDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');"> 
		<%
			}
		%>
	</dhv:permission>
	<%	if (TicketDetails.isControllo_chiudibile()==true)
	{
	%>
	
		<dhv:permission name="vigilanza-vigilanza-edit">
	<a class="ovalbutton" href = "#dialog5" name="modal" ><span >Chiudi</span></a>
	</dhv:permission>
	
	
	<%}
	else
	{
		%>
			<input type="button"
			value="Chiudi in Attesa di Esito"
			title="Chiude il Controllo in maniera momentanea in attesa di esito di campioni e tamponi"
			onClick="javascript:alert('ATTENZIONE! Stai per chiudere il controllo in maniera momentanea in attesa di esito di campioni e tamponi. Non sara possibile modificare i dati del controllo e delle attivita collegate.Si potra inserire solo l esito dei campioni e tamponi)');this.form.action='<%=TicketDetails.getURlDettaglio() %>Vigilanza.do?command=ChiudiTemp&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
	
		<%		
			}
	
	%>
	<%
		}}}else
			
		{
			
			if (TicketDetails.isTrashed()) {
		%>
		<dhv:permission name="vigilanza-vigilanza-delete">
			<input type="button"
				value="Ripristina"
				onClick="javascript:this.form.action='OiaVigilanza.do?command=Restore&idNodo=<%=OrgDetails.getOrgId() %>&id=<%= TicketDetails.getId()%>';submit();">
		</dhv:permission>
		<%
			} else if (TicketDetails.getClosed() != null) {
		%>
		
		<%
			} else {
		%>
		<dhv:permission name="vigilanza-vigilanza-edit">
			<input type="button"
				value="Modifica"
				onClick="javascript:this.form.action='OiaVigilanza.do?command=ModifyTicket&idNodo=<%=OrgDetails.getOrgId() %>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
		</dhv:permission>
		
		<dhv:permission name="vigilanza-vigilanza-delete">
			<%
				if ("searchResults".equals(request
									.getParameter("return"))) {
			%>
			<input type="button"
				value="<dhv:label name="global.button.delete">Delete</dhv:label>"
				onClick="javascript:popURL('OiaVigilanza.do?command=ConfirmDelete&idNodo=<%=OrgDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
			<%
				} else {
			%>
			 <input type="button"
				value="<dhv:label name="global.button.delete">Delete</dhv:label>"
				onClick="javascript:popURL('OiaVigilanza.do?command=ConfirmDelete&idNodo=<%=OrgDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');"> 
			<%
				}
			%>
		</dhv:permission>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<dhv:permission name="vigilanza-vigilanza-edit">
	<a class="ovalbutton" href = "#dialog5" name="modal" ><span >Chiudi</span></a>
	</dhv:permission>
	
		<%}} %>
</dhv:container>

</form>


<script>


$(document).ready(function() {	

	
	//select all the a tag with name equal to modal
	$('a[name=modal]').click(function(e) {
		//Cancel the link behavior
		e.preventDefault();
		
		//Get the A tag
		var id = $(this).attr('href');
	
		//Get the screen height and width
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();
	
		//Set heigth and width to mask to fill up the whole screen
		$('#mask').css({'width':maskWidth,'height':maskHeight});
		
		//transition effect		
		$('#mask').fadeIn(1000);	
		$('#mask').fadeTo("slow",0.8);	
	
		//Get the window height and width
		var winH = $(window).height();
		var winW = $(window).width();
              
		//Set the popup window to center
		$(id).css('top',  winH/2-$(id).height()/2);
		$(id).css('left', winW/2-$(id).width()/2);
		
		//transition effect
		$(id).fadeIn(2000); 
	
	});
	
	//if close button is clicked
	$('.window .close').click(function (e) {
		//Cancel the link behavior
		e.preventDefault();
		
		$('#mask').hide();
		$('.window').hide();
	});		
	
	//if mask is clicked
	$('#mask').click(function () {
		$(this).hide();
		$('.window').hide();
	});			
	
});

</script>
<style>
body {
	font-family: verdana;
	font-size: 15px;
}

a {
	color: #333;
	text-decoration: none
}

a:hover {
	color: #ccc;
	text-decoration: none
}

#mask {
	position: absolute;
	left: 0;
	top: 0;
	z-index: 9000;
	background-color: #000;
	display: none;
}

#boxes .window {
	position: absolute;
	left: 0;
	top: 0;
	width: 675px;
	height: 658;
	display: none;
	z-index: 9999;
	padding: 20px;
}

#boxes
#dialog
#
{
width:675px;
height:680;
padding:10px;
background-color:#ffffff
;}
#dialog5 {
	width: 100%;
	height: 70%;
	padding: 40px;
	background-color: #ffffff;
	overflow: scroll;
	margin-top: 70px;
}

#boxes #dialog1 {
	width: 375px;
	height: 203px;
}

#dialog1 .d-header {
	background: url(images/login-header.png) no-repeat 0 0 transparent;
	width: 375px;
	height: 150px;
}

#dialog1 .d-header input {
	position: relative;
	top: 60px;
	left: 100px;
	border: 3px solid #cccccc;
	height: 22px;
	width: 200px;
	font-size: 15px;
	padding: 5px;
	margin-top: 4px;
}

#dialog1 .d-blank {
	float: left;
	background: url(images/login-blank.png) no-repeat 0 0 transparent;
	width: 267px;
	height: 53px;
}

#dialog1 .d-login {
	float: left;
	width: 108px;
	height: 53px;
}

#boxes #dialog2 {
	background: url(images/notice.png) no-repeat 0 0 transparent;
	width: 326px;
	height: 229px;
	padding: 50px 0 20px 25px;
}
</style>

<div id="boxes">
<div id="dialog5" class="window"><a href="#" class="close" /><font
	color="red">CHIUDI</font></a> <br>


<script>

function checkForm()
{
	formTest= false ;
	msg = '' ;

	if(document.getElementById('subject').value == '')
	{
		formTest = true ;
		msg='Controllare di aver inserito L oggetto\r\n' ;
	}
	if (motivazioneForm.id<%= TicketDetails.getOrgId() %>.value.length < 5) {
		  formTest = true;
	      msg = 'Controllare di aver allegato un file \r\n';
	      
	    }
	/*if(document.getElementById('folderId').value == '-1')
	{
		formTest = true ;
		msg='Controllare di aver inserito il file da allegare' ;
	}*/


if(formTest==true)
{
alert(msg);
}
else
{
	document.motivazioneForm.submit();
}
}
</script>

<script>function checkFormFile(form){
	var fileCaricato = form.file1;
	var oggetto = form.subject.value;
	var errorString = '';
	if (fileCaricato.value==''){// || (!fileCaricato.value.endsWith(".pdf") && !fileCaricato.value.endsWith(".csv"))){
		errorString+='Errore! Selezionare un file!';
		form.file1.value='';
	}
	if (oggetto==''){
		errorString+='\nErrore! L\'oggetto è obbligatorio.';
		}
	if (errorString!= '')
		alert(errorString)
	else
	{
	//form.filename.value = fileCaricato.value;	
	form.uploadButton.hidden="hidden";
	form.file1.hidden="hidden";
	document.getElementById("image_loading").hidden="";
	document.getElementById("text_loading").hidden="";
	loadModalWindow();
	form.submit();
	}
}</script>

<form id="form2" action="GestioneAllegatiUpload.do?command=AllegaFile" method="post" name="form2" enctype="multipart/form-data">
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b><dhv:label name="accounts.accounts_documents_upload.UploadNewDocument">Upload a New Document</dhv:label></b>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
      </td>
      <td>
        <input type="text" name="subject" size="59" maxlength="255" value="Rapporto Conclusivo di Audit/ispezione Semplice"><font color="red">*</font>
        <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
      <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
       
         <%
       int maxFileSize=-1;
	   int mb1size = 1048576;
	    maxFileSize=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));
	   	String maxSizeString = String.format("%.2f", (double) maxFileSize/ (double) mb1size);
       %>
       (Max. <%=maxSizeString %> MB)
       
      </td>
      <td>
        <input type="file" id="file1" name="file1" size="45">  <input type="button" id="uploadButton" name="uploadButton" value="UPLOAD" onclick="checkFormFile(this.form)" />
      
          <img id="image_loading" hidden="hidden" src="gestione_documenti/images/loading.gif" height="15"/>
          <input type="text" disabled id="text_loading" name="text_loading" hidden="hidden" value="Caricamento in corso..."  style="border: none"/>
      </td>
    </tr>
     <input type="hidden" name="id" id ="id" value="<%=TicketDetails.getOrgId()%>" />
       <input type="hidden" name="ticketId" id ="ticketId" value="<%=TicketDetails.getId()  %>" />
    <input type="hidden" name="folderId" id="folderId" value="-1" />
<input type="hidden" name="parentId" id="parentId" value="-1" /> 
 <input type="hidden" name="op" id="op" value="" />
  <input type="hidden" name="tipoAllegato" id="tipoAllegato" value="RapportoChiusura" />
  <input type="hidden" name="actionOrigine" id="actionOrigine" value="OiaVigilanza" />
  	<input type = "hidden" name = "idNodo" value = "<%=OrgDetails.getOrgId()%>">
  	<input type = "hidden" name = "orgId" value = "<%=OrgDetails.getOrgId()%>">  
  </table>
   </form>
   <p align="center">
    * <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label>
     <dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
  </p>
  
<table class="trails" cellspacing="0">
<tr>
<td>
Attenzione ! Allegare il rapporto conclusivo di Audit/Ispezione Semplice prima di chiudere il controllo
</td>
</tr>
</table>
<%--
<form method="post" enctype="multipart/form-data" action="OiaVigilanza.do?command=UploadRapporto" name = "motivazioneForm">
<input type = "hidden" name = "idVoltura" value = "<%=TicketDetails.getId()  %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Allega Rapporto Audit/ispezione Semplice</dhv:label></strong>
     
    </th>
  </tr>
  	<input type = "hidden" name = "idNodo" value = "<%=OrgDetails.getOrgId()%>"> 
  	<input type = "hidden" name = "folderId" id = "folderId" value = "-1"> 
  	<input type="hidden" name="isAllegato" id = "isAllegato" value="false">
	<input type = "hidden" name = "orgId" value = "<%=TicketDetails.getOrgId() %>"> 
	<input type = "hidden" name = "id" value = "<%=TicketDetails.getId() %>">
   	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Oggetto</dhv:label>
			</td>
			<td> 
			<textarea rows="5" cols="30" name="subject" id="subject">Rapporto Conclusivo di Audit/ispezione Semplice</textarea> 
			</td>
		</tr>
		
		  <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="">File</dhv:label>
      </td>
      <td>
        <input type="file" name="id<%= TicketDetails.getOrgId() %>"  id="id<%= TicketDetails.getOrgId() %>" size="45" value="">
      </td>
    </tr>
		
</table>		
<input type = "button" onclick="return checkForm()" value = "Allega">
	</form>
--%>


</div>
</div>


