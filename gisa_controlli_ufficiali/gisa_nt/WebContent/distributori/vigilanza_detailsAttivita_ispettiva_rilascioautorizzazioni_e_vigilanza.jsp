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
<jsp:useBean id="Audit" class="org.aspcfs.checklist.base.AuditList" scope="request"/>
<jsp:useBean id="ReatiList" class="org.aspcfs.modules.reati.base.TicketList" scope="request"/>
<jsp:useBean id="TamponiList" class="org.aspcfs.modules.tamponi.base.TicketList" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.distributori.base.Organization" scope="request"/>
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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>

<%@ include file="../initPage.jsp" %>
<form name="details" action="AccountVigilanza.do?command=ModifyTicket&auto-populate=true" method="post">
<%-- Trails --%>
<%String idMacchinetta=(String)request.getAttribute("idMacchinetta");%>
<table class="trails" cellspacing="0">
<tr>
<td>
<td>
  <a href="Distributori.do?command=List"><dhv:label name="">Distributori</dhv:label></a> > 
  <a href="Distributori.do?command=Details&id=<%=idMacchinetta %>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Scheda Distributore Automatico</dhv:label></a> >
  <a href="Distributori.do?command=ViewVigilanza&aslMacchinetta=<%=(String)request.getAttribute("aslMacchinetta") %>&id=<%=idMacchinetta %>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="vigilanza">Tickets</dhv:label></a> >
  <dhv:label name="campione.dettagli">Scheda Controllo Ufficiale</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId();
%>
<dhv:container name="distributori" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId()+"&id="+idMacchinetta %>' >

	<%@ include file="ticket_header_include_vigilanza.jsp"%>
	<%UserBean user=(UserBean)session.getAttribute("User");
	  String aslMacchinetta=(String)request.getAttribute("aslMacchinetta");
	  aslMacchinetta = ""+OrgDetails.getSiteId();
 	  if(user.getSiteId()!=-1){
	  if(aslMacchinetta!=null && (""+user.getSiteId()).equals(aslMacchinetta)){
  
  %>
	
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="vigilanza-vigilanza-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='DistributoriVigilanza.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='DistributoriVigilanza.do?command=ReopenTicket&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
		} else {
	%>
	<dhv:permission name="vigilanza-vigilanza-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='DistributoriVigilanza.do?command=ModifyTicket&idMacchinetta=<%=idMacchinetta%>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="vigilanza-vigilanza-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('DistributoriVigilanza.do?command=ConfirmDelete&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		 <input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('DistributoriVigilanza.do?command=ConfirmDelete&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');"> 
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="vigilanza-vigilanza-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='DistributoriVigilanza.do?command=Chiudi&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
	</dhv:permission>
	<%
		}}}else{
	%>
	<%
		if (TicketDetails.isTrashed()) {
	%>
	<dhv:permission name="vigilanza-vigilanza-delete">
		<input type="button"
			value="Ripristina"
			onClick="javascript:this.form.action='DistributoriVigilanza.do?command=Restore&id=<%= TicketDetails.getId()%>';submit();">
	</dhv:permission>
	<%
		} else if (TicketDetails.getClosed() != null) {
	%>
	<dhv:permission name="reopen-reopen-view">
		<input type="button"
			value="<dhv:label name="button.reopen">Reopen</dhv:label>"
			onClick="javascript:this.form.action='DistributoriVigilanza.do?command=ReopenTicket&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId()%><%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	<%
		} else {
	%>
	<dhv:permission name="vigilanza-vigilanza-edit">
		<input type="button"
			value="Modifica"
			onClick="javascript:this.form.action='DistributoriVigilanza.do?command=ModifyTicket&idMacchinetta=<%=idMacchinetta%>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="vigilanza-vigilanza-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('DistributoriVigilanza.do?command=ConfirmDelete&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		 <input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('DistributoriVigilanza.do?command=ConfirmDelete&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');"> 
		<%
			}
		%>
	</dhv:permission>
	<%	if (TicketDetails.isControllo_chiudibile()==true)
	{
	%>
	<dhv:permission name="vigilanza-vigilanza-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='DistributoriVigilanza.do?command=Chiudi&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
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
	<%}} %>
	
   </br></br>
   
   		<%@ include file="../controlliufficiali/controlli_ufficiali_stampa_verbale_ispezione.jsp" %>
   	
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
   <br>
   <br>
   <%-- INCLUSIONE DETTAGLIO SISTEMA ALLARME RAPIDO --%>
   
   <%@ include file="../controlliufficiali/controlli_ufficiali_allarmerapido_view.jsp" %>  
   
   <br>
   <%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_view.jsp" %>
	<br>
	<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_non_in_regione_view.jsp" %>
    <br>
   
      <% int punteggioAccumulato = 0; %>

  <%@ include file="../controlliufficiali/controlli_ufficiali_sottoattivita.jsp" %>
  
   
  

   
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
   		   <th><b><dhv:label name="">Livello Rischio</dhv:label></b></th>
   		   <th><b><dhv:label name="">Data Esecuzione Check List</dhv:label></b></th>
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
   
      <a href="AccountsAudit.do?command=View&id=<%= thisAudit.getId()%>"><%= toHtml(OrgCategoriaRischioList.getSelectedValue(thisAudit.getTipoChecklist())) %></a>
	</td>
	<%}else if(TicketDetails.getTipoCampione()==4){ %>
	 <td>
	    
	   <a href="AccountsAudit.do?command=View&id=<%= thisAudit.getId()%>"><%= toHtml(OrgCategoriaRischioList2.getSelectedValue(thisAudit.getTipoChecklist())) %></a>
	</td>
	<%} %>
	<td>
	<%= ((thisAudit.getLivelloRischio()>0) ? (toHtml(String.valueOf(thisAudit.getLivelloRischio()))) : ("-")) %> 
	</td>
	<td>
      <%= ((thisAudit.getLivelloRischioFinale()>0) ? (toHtml(String.valueOf(thisAudit.getLivelloRischioFinale()))) : ("Non Aggiornato")) %>  
    </td>
    <td>
      <%= toHtml(DateUtils.getDateAsString(thisAudit.getData1(),Locale.ITALY)) %>
    </td>
    <td>
      <%= toHtml(String.valueOf(thisAudit.getCategoria())) %>
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
	<%} 
	
	
     //controllo se è in sorveglianza
	%>
	
      	<%@ include file="../controlliufficiali/controlli_ufficiali_dettaglio_sottoattivita.jsp" %>

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
  <%--<dhv:include name="" none="true">
<dhv:evaluate if="<%= TicketDetails.getEsitoCampione() > -1 %>">
 <tr class="containerBody">
      <td name="esitoCampione1" id="esitoCampione1" nowrap class="formLabel">
        <dhv:label name="">Esito</dhv:label>
      </td>
    <td>
      <%=EsitoCampione.getSelectedValue(TicketDetails
    		  .getEsitoCampione())%>
					<input type="hidden" name="esitoCampione"
						value="<%=TicketDetails.getEsitoCampione() %>">
    </td>
  </tr>
  </dhv:evaluate>
</dhv:include>--%>
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
			onClick="javascript:this.form.action='DistributoriVigilanza.do?command=Restore&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId()%>';submit();">
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
			onClick="javascript:this.form.action='DistributoriVigilanza.do?command=ModifyTicket&idMacchinetta=<%=idMacchinetta%>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
	</dhv:permission>
	
	<dhv:permission name="vigilanza-vigilanza-delete">
		<%
			if ("searchResults".equals(request
								.getParameter("return"))) {
		%>
		<input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('DistributoriVigilanza.do?command=ConfirmDelete&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
		<%
			} else {
		%>
		 <input type="button"
			value="<dhv:label name="global.button.delete">Delete</dhv:label>"
			onClick="javascript:popURL('DistributoriVigilanza.do?command=ConfirmDelete&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');"> 
		<%
			}
		%>
	</dhv:permission>
	<%	if (TicketDetails.isControllo_chiudibile()==true)
	{
	%>
	<dhv:permission name="vigilanza-vigilanza-edit">
		<input type="button"
			value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
			onClick="javascript:this.form.action='DistributoriVigilanza.do?command=Chiudi&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
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
				onClick="javascript:this.form.action='DistributoriVigilanza.do?command=Restore&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId()%>';submit();">
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
				onClick="javascript:this.form.action='DistributoriVigilanza.do?command=ModifyTicket&idMacchinetta=<%=idMacchinetta%>&auto-populate=true<%= (defectCheck != null && !"".equals(defectCheck.trim())?"&defectCheck="+defectCheck:"") %>';submit();">
		</dhv:permission>
		
		<dhv:permission name="vigilanza-vigilanza-delete">
			<%
				if ("searchResults".equals(request
									.getParameter("return"))) {
			%>
			<input type="button"
				value="<dhv:label name="global.button.delete">Delete</dhv:label>"
				onClick="javascript:popURL('DistributoriVigilanza.do?command=ConfirmDelete&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
			<%
				} else {
			%>
			 <input type="button"
				value="<dhv:label name="global.button.delete">Delete</dhv:label>"
				onClick="javascript:popURL('DistributoriVigilanza.do?command=ConfirmDelete&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId() %>&orgId=<%=TicketDetails.getOrgId()%>&popup=true', 'Delete_ticket','320','200','yes','no');"> 
			<%
				}
			%>
		</dhv:permission>
		<dhv:permission name="vigilanza-vigilanza-edit">
			<input type="button"
				value="<dhv:label name="global.button.close">Chiudi</dhv:label>"
				onClick="javascript:this.form.action='DistributoriVigilanza.do?command=Chiudi&idMacchinetta=<%=idMacchinetta%>&id=<%= TicketDetails.getId() %>';if( confirm('Sei sicuro di voler chiudere il Controllo Ufficiale? \n Attenzione! La pratica verrà chiusa e non sarà più possibile fare modifiche \n sulla scheda se non con il permesso del supervisore o dell amministratore') ){submit()};">
		</dhv:permission>
		<%}} %>
</dhv:container>
</form>
<% String flag=(String)request.getAttribute("Chiudi");
if(flag!=null){
if(flag.equals("1")){ %>
<script>
	alert("Questo Controllo Ufficiale non puo essere chiuso .Ci sono Attività collegate che non sono state ancora chiuse.");
</script>
<%  }else if(flag.equals("2")){ %>
<script>
	alert("Questo Controllo Ufficiale non puo essere chiuso. Assicurarsi di aver chiuso tutte le sottoattività e aggiornato la categoria di rischio per l'ultima check list inserita(Inserire Check list e aggiornare categoria rishio).");
</script>
<% }else if(flag.equals("3")){ %>
<script>			alert("Chiusura del controllo ufficiale effettuata correttamente."); </script>
<%}else if(flag.equals("4")){ %>
<srcipt>	alert("Controllo Ufficiale chiuso in attesa di esito (sottosezione Tamponi o Campioni)."); </srcipt>
<%	}}  %>