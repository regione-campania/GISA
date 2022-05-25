<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.vigilanza.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>

<%@page import="org.aspcfs.checklist.base.AuditList"%>
<%@page import="org.aspcfs.checklist.base.Audit"%><jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
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
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request" />
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DistribuzionePartita" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="DestinazioneDistribuzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.abusivismi.base.Organization" scope="request"/>
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
<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />
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
<jsp:useBean id="ProvvedimentiList" class="org.aspcfs.modules.prvvedimentinc.base.TicketList" scope="request"/>
<jsp:useBean id="AltreNonCList" class="org.aspcfs.modules.altriprovvedimenti.base.TicketList" scope="request"/>
 
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>



<%@ include file="../initPage.jsp" %>
<form name="details" action="AccountVigilanza.do?command=ModifyTicket&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<td>
 <a href="Abusivismi.do">Operatori Abusivi</a> > 
  <a href="Abusivismi.do?command=Search">Risultati ricerca</a> >
  <a href="Abusivismi.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Scheda Operatore Abusivo</a> >
  <a href="Abusivismi.do?command=ViewVigilanza&orgId=<%=TicketDetails.getOrgId()%>">Controlli Ufficiali</a> >
  Scheda Controllo Ufficiale
</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId()+"&orgId="+TicketDetails.getOrgId();
%>
<dhv:container name="abusivismi" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
<dhv:container name="abusivismivigilanza" selected="details" object="TicketDetails"
	param="<%= param1 %>"
	hideContainer='<%= isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim())) %>'>
	<%@ include file="ticket_header_include_vigilanza.jsp"%>
		
		
			<%
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-vigilanza-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-vigilanza-delete" ;
	
	%>
	
	<%@ include file="../controlliufficiali/header_controlli_ufficiali.jsp" %>
<%@ include file="../controlliufficiali/controlli_ufficiali_stampa_verbale_ispezione.jsp" %>
	</br></br>
	<%-- Ticket Information --%>
	<%-- Primary Contact --%>
	
   </br></br>

   
   
 <table cellpadding="4" cellspacing="0" width="100%" class="details">
		
		<%@ include file="../controlliufficiali/controlli_ufficiali_view.jsp" %>
		
	
	
		
		
	</table>
   <br>
   <br>
   <%-- INCLUSIONE DETTAGLIO SISTEMA ALLARME RAPIDO --%>
   
   <%@ include file="../controlliufficiali/controlli_ufficiali_allarmerapido_view.jsp" %>  
 
 <%@ include file="../controlliufficiali/controlli_ufficiali_sottoattivita.jsp" %>
 
<%-- 
        <table cellpadding="4" cellspacing="0" width="100%">
     <tr>
   
 
   <%if( TicketDetails.getClosed()==null){
     
   %>
     <% if(TicketDetails.getTipoCampione()!=5){ // sorveglianza %>
	  
    	<td>
    		<a href="AbusivismiCampioni.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&orgId=<%= (OrgDetails.getOrgId()==-1)?(TicketDetails.getOrgId()):(OrgDetails.getOrgId()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add" >Inserisci Campione</dhv:label></a>
    	</td>
    	<td>
    		<a href="AbusivismiTamponi.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&orgId=<%= (OrgDetails.getOrgId()==-1)?(TicketDetails.getOrgId()):(OrgDetails.getOrgId()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Inserisci Tampone</dhv:label></a>
    	</td>
    	<%}%>

    	<%
    	if (NonCList.size()==0 ) 
    	{
    	%>
    	<td>
    		<a href="AbusivismiNonConformita.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&idIspezione=<%= TicketDetails.getTipoIspezione() %>&orgId=<%= (OrgDetails.getOrgId()==-1)?(TicketDetails.getOrgId()):(OrgDetails.getOrgId()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Inserisci Non Conformità Rilevate</dhv:label></a>
    	</td>
    	<%}%>
    		 <td>
    		<a href="AbusivismiProvvedimenti.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&orgId=<%= (OrgDetails.getOrgId()==-1)?(TicketDetails.getOrgId()):(OrgDetails.getOrgId()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Inserisci Provvedimenti non causati da n.c. <br> a carico del soggetto ispezionato</dhv:label></a>
    	</td>
    	<%
    	}
    	%>
    	
     </tr>
   </table>

    --%>      
      <% int punteggioAccumulato = 0; %>
   
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
   		   <th><b><dhv:label name="">Livello Rischio</dhv:label></b></th>
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
   

<br />

	<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_view.jsp" %>
	<br>
	<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_non_in_regione_view.jsp" %>
	
	
	<%@ include file="../controlliufficiali/header_controlli_ufficiali.jsp" %>
	
	
</dhv:container>
</dhv:container>
</form>
<% String flag=(String)request.getAttribute("Chiudi");
if(flag!=null){
if(flag.equals("1")){ %>
<script>
	alert("Questo Controllo Ufficiale non puo essere chiuso .Ci sono Attività collegate che non sono state ancora chiuse.");
</script>
<%  }else if(flag.equals("2") || flag.equals("5")){ %>
<script>
	alert("Questo Controllo Ufficiale non puo essere chiuso. Assicurarsi di aver chiuso tutte le sottoattività e aggiornato la categoria di rischio per l'ultima check list inserita(Inserire Check list e aggiornare categoria rishio).");
</script>
<% }else if(flag.equals("3")){ %>
<script>			alert("Chiusura del controllo ufficiale effettuata correttamente."); </script>
<%}else if(flag.equals("4")){ %>
<srcipt>	alert("Controllo Ufficiale chiuso in attesa di esito (sottosezione Tamponi o Campioni)."); </srcipt>
<%	}}  %>