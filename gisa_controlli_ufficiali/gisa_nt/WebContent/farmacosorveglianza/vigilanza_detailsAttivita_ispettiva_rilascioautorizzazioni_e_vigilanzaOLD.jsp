<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.vigilanza.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="SanzioniList" class="org.aspcfs.modules.sanzioni.base.TicketList" scope="request"/>
<jsp:useBean id="SequestriList" class="org.aspcfs.modules.sequestri.base.TicketList" scope="request"/>
<jsp:useBean id="NonCList" class="org.aspcfs.modules.nonconformita.base.TicketList" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.checklist.base.AuditList" scope="request"/>
<jsp:useBean id="ReatiList" class="org.aspcfs.modules.reati.base.TicketList" scope="request"/>
<jsp:useBean id="TamponiList" class="org.aspcfs.modules.tamponi.base.TicketList" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.farmacosorveglianza.base.Organization" scope="request"/>
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DistribuzionePartita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DestinazioneDistribuzione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="Ispezione" class="java.util.HashMap" scope="request" />
<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TicL" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TicList" class="org.aspcfs.modules.campioni.base.TicketList" scope="request"/>

<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDue" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoTre" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_ufficiali_farmacosorveglianza.js"></script>
<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />
<script>


function openNotePopupAggiornaCategoria(orgId,idC){
	var res;
	var result;

	
		window.open('FarmacieVigilanza.do?command=AggiornaCategoriaFarmacosorveglianza&orgId='+orgId+'&id='+idC,null,
		'height=300px,width=480px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
		
		
		} 
</script>

<body onload="showCampi('<%=TicketDetails.getTipoIspezione() %>')">

<%@ include file="../initPage.jsp" %>
<form name="details" action="FarmacieVigilanza.do?command=ModifyTicket&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<td>
  <a href="Farmacosorveglianza.do?command=SearchFormFcie"><dhv:label name="">Farmacosorveglianza</dhv:label></a> > 
<a href="Farmacosorveglianza.do?command=SearchFcie"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
 <a href="Farmacosorveglianza.do?command=DetailsFcie&idFarmacia=<%=OrgDetails.getOrgId()%>">Dettaglio Farmacosorveglianza</a> >
  <a href="FarmacieVigilanza.do?command=ViewVigilanza&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="vigilanza">Tickets</dhv:label></a> >
  <dhv:label name="campione.dettagli">Scheda Controllo Ufficiale</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%
String param1 = "id=" + TicketDetails.getId()+"&orgId="+TicketDetails.getOrgId()+"&idControllo="+TicketDetails.getIdControlloUfficiale();

%>
<dhv:container name="farmacosorveglianza" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>

	<%@ include file="ticket_header_include_vigilanza.jsp"%>
	<% if(TicketDetails.getTipoCampione()==5)
	   { 
		if(OrgDetails.getCategoriaRischio()>0)
		{
	%>
<table cellpadding="4" cellspacing="0" width="100%" class="empty">
 
  <tr>
      <td name="punteggio" id="punteggio" nowrap >
        <dhv:label name=""><b>Attuale Categoria Rischio:</b></dhv:label>
      &nbsp;
    <% if(OrgDetails.getCategoriaRischio()>0) {%>
    	<%= toHtml(String.valueOf(OrgDetails.getCategoriaRischio())) %>  
    <%} %>
    </td>
    
  </tr>
  </table>
    </br>
     <%}} %>
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="farmacie-farmacie-vigilanza-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='FarmacieVigilanza.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='FarmacieVigilanza.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
		} else {
			if(TicketDetails.isCategoriaisAggiornata()==false)
			{
	%>
	<dhv:permission name="farmacie-farmacie-vigilanza-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='FarmacieVigilanza.do?command=ModifyTicket&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="farmacie-farmacie-vigilanza-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('FarmacieVigilanza.do?command=ConfirmDelete&codiceallerta=<%= TicketDetails.getCodiceAllerta() %>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		 <input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('FarmacieVigilanza.do?command=ConfirmDelete&codiceallerta=<%= TicketDetails.getCodiceAllerta() %>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');"> 
		<%
			}
		%>
	</dhv:permission>
	<%} %>
	<%if (TicketDetails.isControllo_chiudibile()==true) {%>
	<dhv:permission name="farmacie-farmacie-vigilanza-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='FarmacieVigilanza.do?command=Chiudi&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
	</dhv:permission>
	<%}
	else
	{
		%>
		<dhv:permission name="farmacie-farmacie-vigilanza-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiusura in Attesa di Esito</dhv:label>"
			onClick="javascript:this.form.action='FarmacieVigilanza.do?command=ChiudiTemp&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
	</dhv:permission>
		<%
	}
	%>
	
	<%if(TicketDetails.isCategoriaisAggiornata() == false && TicketDetails.getTipoCampione()==5)
		{%>
		
		<input type = "button" onclick="openNotePopupAggiornaCategoria('<%=TicketDetails.getOrgId() %>','<%=TicketDetails.getId() %>')" value = "Aggiorna Categoria">

	
	<%
		}
	
		}
	%>
	
	
		<%@ include file="../controlliufficiali/controlli_ufficiali_stampa_verbale_ispezione.jsp" %>
	
	
	<%-- INCLUSIONE DETTAGLIO CONTROLLO UFFICIALE--%>
	
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
		
		<%@ include file="../controlliufficiali/controlli_ufficiali_view_farmacosorveglianza.jsp" %>
		

		
		
	</table>
   <br>
   <br>
   <%-- INCLUSIONE DETTAGLIO SISTEMA ALLARME RAPIDO --%>
   
   <%@ include file="../controlliufficiali/controlli_ufficiali_allarmerapido_view.jsp" %>
      <% int punteggioAccumulato = 0; %>
 
   
   
    <table cellpadding="4" cellspacing="0" width="100%">
     <tr>
   
   
   <%if(TicketDetails.getClosed()==null && TicketDetails.isCategoriaisAggiornata()==false)
	{
     
   %>
	   
    	<td>
    		<a href="FarmacieNonConformita.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&idIspezione=<%= TicketDetails.getTipoIspezione() %>&orgId=<%= (OrgDetails.getOrgId()==-1)?(TicketDetails.getOrgId()):(OrgDetails.getOrgId()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Inserisci Non Conformità Rilevate</dhv:label></a>
    	</td>
    	<td>
    		<a href="FarmacosorveglianzaProvvedimenti.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&orgId=<%= (OrgDetails.getOrgId()==-1)?(TicketDetails.getOrgId()):(OrgDetails.getOrgId()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Inserisci Provvedimento</dhv:label></a>
    	</td>
     </tr>
   </table>
 
   
   <%} %>
   
   
  
   <table cellpadding="4" cellspacing="0" width="100%" class="details">
		<tr>
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
    
  </tr>
	
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
       </td>
      
		<td  valign="top" nowrap>
			<a href="FarmacieNonConformita.do?command=TicketDetails&idIspezione=<%= TicketDetails.getTipoIspezione() %>&id=<%= thisNonC.getId() %>&orgId=<%= thisNonC.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisNonC.getIdentificativo() %></a>
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
          <a href="FarmacieVigilanzaDocuments.do?command=Download&stream=true&tId=<%= thisNonC.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
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
    <%} %>
    --%>
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
    </table><% } %>
		
    
&nbsp;
<br />

<%--} --%>
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="farmacie-farmacie-vigilanza-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='FarmacieVigilanza.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<dhv:permission name="farmacie-farmacie-vigilanza-edit">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='FarmacieVigilanza.do?command=ReopenTicket&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
		} else {
			if(TicketDetails.isCategoriaisAggiornata()==false)
			{
		
	%>
	<dhv:permission name="farmacie-farmacie-vigilanza-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='FarmacieVigilanza.do?command=ModifyTicket&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="farmacie-farmacie-vigilanza-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('FarmacieVigilanza.do?command=ConfirmDelete&codiceallerta=<%= TicketDetails.getCodiceAllerta() %>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		 <input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('FarmacieVigilanza.do?command=ConfirmDelete&codiceallerta=<%= TicketDetails.getCodiceAllerta() %>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');"> 
		<%
			}
		%>
	</dhv:permission>
	<%} %>
	<%if (TicketDetails.isControllo_chiudibile()==true) {%>
	<dhv:permission name="farmacie-farmacie-vigilanza-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='FarmacieVigilanza.do?command=Chiudi&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
	</dhv:permission>
	<%}
	else
	{
		%>
		<dhv:permission name="farmacie-farmacie-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiusura in Attesa di Esito</dhv:label>"
			onClick="javascript:this.form.action='FarmacieVigilanza.do?command=ChiudiTemp&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
	</dhv:permission>
		<%
	}
	%>
	<%
		}
	%>

</dhv:container>
</form>


<%
String flag=(String)request.getAttribute("Chiudi");
if(flag!=null){
if(flag.equals("1")){
	
%>
<script>

alert("Questo Controllo Ufficiale non puo essere chiuso. Ci sono attività che non sono state ancora chiuse.");
</script>


<% 

}else if(flag.equals("2")){
%>
<script>

alert("Questo Controllo Ufficiale non puo essere chiuso. Assicurarsi di aver chiuso tutte le sottoattività e aggiornato la categoria di rischio per l'ultima check list inserita(Inserire Check list e aggiornare categoria rishio).");
</script>


<%	


}	
}


%>
</body>