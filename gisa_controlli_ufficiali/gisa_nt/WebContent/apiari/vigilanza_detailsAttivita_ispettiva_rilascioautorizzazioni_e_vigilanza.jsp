<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@ include file="../initPage.jsp" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.vigilanza.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<%@ page import="org.aspcfs.modules.lineeattivita.base.LineeAttivita" %>
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
<jsp:useBean id="OrgDetails" class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request"/>
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DistribuzionePartita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DestinazioneDistribuzione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<jsp:useBean id="mod" class="java.lang.String" scope="request"/>
<jsp:useBean id="View" class="java.lang.String" scope="request"/>
<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="defect" class="org.aspcfs.modules.troubletickets.base.TicketDefect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ProvvedimentiList" class="org.aspcfs.modules.prvvedimentinc.base.TicketList" scope="request"/>
<jsp:useBean id="AltreNonCList" class="org.aspcfs.modules.altriprovvedimenti.base.TicketList" scope="request"/>
 
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_ufficiali_opu.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlliUfficiali.js"></script>

<script>
function openPopupBarcode(orgId, ticketId){
	var res;
	var result;
		window.open('ManagePdfModules.do?command=GenerateBarcode&orgId='+orgId+'&ticketId='+ticketId,'popupSelect',
		'height=300px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
}
</script>

 <script type="text/javascript" src="dwr/interface/RetrieveBarcode.js"> </script>
 <script type="text/javascript" src="dwr/engine.js"> </script>
 <script type="text/javascript" src="dwr/util.js"></script>
 <script type="text/javascript">

 			function checkBarcode(orgId, ticketId){

     			RetrieveBarcode.getGeneratedBarcode(orgId,ticketId,getBarcode) ;
     
 			}

 			function getBarcode(returnValue){
				
 				if(returnValue > 0){
 					window.location.href="ManagePdfModules.do?command=RetrieveBarcode&orgId=<%= TicketDetails.getOrgId() %>&ticketId=<%= TicketDetails.getId() %>";					
 				}
 				else {
 					openPopupBarcode(<%= TicketDetails.getOrgId() %>, <%=TicketDetails.getId()%>);
	 					
 	 			}	
	  			
 			}
 
 </script>


<body onload="verificaChiusuraCu(<%=request.getAttribute("Chiudi") %>)">
<form name="details" action="<%=OrgDetails.getAction() %>Vigilanza.do?command=ModifyTicket&auto-populate=true" method="post">
<table class="trails" cellspacing="0">
<tr>
<td>
  <dhv:label name=""><a href="<%=OrgDetails.getAction()+".do?command=SearchForm" %>" >Gestione Anagrafica Impresa </a>-><a  href="<%=OrgDetails.getAction()+".do?command=Details&stabId="+OrgDetails.getIdStabilimento()%>">Scheda Impresa</a> -><a href="<%=OrgDetails.getAction()+".do?command=ViewVigilanza&stabId="+OrgDetails.getIdStabilimento()%>"> Controlli Ufficiali </a>-> Dettaglio Controllo</dhv:label>
 
</td>
</tr>
</table>
<%-- End Trails --%>
<%
String param = "stabId="+OrgDetails.getIdStabilimento()+"&opId=" + OrgDetails.getIdOperatore()+"&id="+TicketDetails.getIdMacchinetta();

String container = OrgDetails.getContainer();
if (User.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA && (container.startsWith("apiari")|| container.startsWith("opu")))
	container="apiari";
TicketDetails.setPermission();
request.setAttribute("Operatore",OrgDetails.getOperatore());


%>



<dhv:container name="apiari" selected="vigilanza" object="Operatore" param='<%= param%>' >

	
	<% if(TicketDetails.getTipoCampione()==5)
	   { 
		if(OrgDetails.getCategoriaRischio()>0)
		{
	%>
	<br>
	<br>
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
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-vigilanza-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-vigilanza-delete" ;
	
	%>
	
	<%@ include file="../controlliufficiali/opu_header_controlli_ufficiali.jsp" %>

	
	<%@ include file="../controlliufficiali/controlli_ufficiali_stampa_verbale_ispezione.jsp" %>

	<%-- INCLUSIONE DETTAGLIO CONTROLLO UFFICIALE--%>
	
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
		
		
		
		<%@ include file="../controlliufficiali/opu_controlli_ufficiali_view.jsp" %>
		
	
		
	</table>
   <br>
   <br>
   <%-- INCLUSIONE DETTAGLIO SISTEMA ALLARME RAPIDO --%>
   
   <%@ include file="../controlliufficiali/opu_controlli_ufficiali_allarmerapido_view.jsp" %>
      <% int punteggioAccumulato = 0; %>
 
   
   <table cellpadding="4" cellspacing="0" width="100%">
     <tr>
   
 
  
	   <% if(TicketDetails.getTipoCampione()!=5){ // sorveglianza %>
	  
	  
	   <% if(  TicketDetails.getClosed()==null  && !TicketDetails.isflagBloccoCu()  && TicketDetails.getTipoCampione()!=5 && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo()   && TicketDetails.getTipoCampione() != 3 && TicketDetails.getTipoCampione() != 23 && TicketDetails.getTipoCampione() != 7){ // sorveglianza %>
	 
    	<td>
    		<a href="<%=OrgDetails.getAction()%>Campioni.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&&stabId=<%= (OrgDetails.getIdStabilimento()==-1)?(TicketDetails.getIdStabilimento()):(OrgDetails.getIdStabilimento()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add" >Inserisci Campione</dhv:label></a>
    	</td>
    	
    	<%}%>
    	
    	
    	 <%if( TicketDetails.getClosed()==null  && !TicketDetails.isflagBloccoCu()  && (View==null ||"".equals(View))  && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo()){
     
   %>
    	<td>
<%--     		<a href="<%=OrgDetails.getAction() %>Tamponi.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&stabId=<%= (OrgDetails.getIdStabilimento()==-1)?(TicketDetails.getIdStabilimento()):(OrgDetails.getIdStabilimento()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Inserisci Tampone</dhv:label></a> --%>
    	</td>
    	<%}
    	}%>
    	
    	<%
    	if (TicketDetails.getClosed()==null && NonCList.size()==0  && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo() ) 
    	{
    	%>
    	<td>
    		<a href="<%=OrgDetails.getAction() %>NonConformita.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&idIspezione=<%= TicketDetails.getTipoIspezione() %>&stabId=<%= (OrgDetails.getIdStabilimento()==-1)?(TicketDetails.getIdStabilimento()):(OrgDetails.getIdStabilimento()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Inserisci Non Conformita' Rilevate</dhv:label></a>
    	</td>
    	<%} %>
    	 <%if( TicketDetails.getClosed()==null  && !TicketDetails.isflagBloccoCu() && (View==null ||"".equals(View))  && User.getUserRecord().getGruppo_ruolo()==UserUtils.getUserFormId(request, TicketDetails.getEnteredBy()).getGruppo_ruolo()){
     
   %>
    	<td>
    		<a href="<%=OrgDetails.getAction() %>AltreNonConformita.do?command=Add&idC=<%=TicketDetails.getId()%>&idControllo=<%=TicketDetails.getPaddedId() %>&dataC=<%=TicketDetails.getAssignedDate() %>&idIspezione=<%= TicketDetails.getTipoIspezione() %>&stabId=<%= (OrgDetails.getIdStabilimento()==-1)?(TicketDetails.getIdStabilimento()):(OrgDetails.getIdStabilimento()) %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Inserisci Non Conformita' <br><b>NON</b> a carico del soggetto ispezionato</dhv:label></a>
    	</td>
    	<%} %>
     </tr>
   </table>
 
   
  
  
 
   

   
   <%
  
   if(Audit.size()!=0 && (View==null ||"".equals(View)))
   { %>
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
   		   <%--th><b><dhv:label name="">Livello Rischio</dhv:label></b></th--%>
   		   
   		   <th><b><dhv:label name="">Punteggio Check List</dhv:label></b></th>
   		   <th><b><dhv:label name="">Stato Check List</dhv:label></b></th>
   		   
   		   <th>&nbsp;</th>
   		   
     	   
   </tr>
   <%
   			
   			int category = -1;
            Iterator itr = Audit.iterator();
            if (itr.hasNext()){
              int rowid = 0;
              int i = 0;
              while (itr.hasNext()){
            	  
              
                i++;
                rowid = (rowid != 1 ? 1 : 2);
                org.aspcfs.checklist.base.Audit thisAudit = (org.aspcfs.checklist.base.Audit)itr.next();
                punteggioAccumulato += thisAudit.getLivelloRischio();
                category = thisAudit.getCategoria();
   
    %> 
   <tr class="row<%=rowid%>">
   
	 <td>
	    
	   <a href="<%=OrgDetails.getAction() %>CheckList.do?command=View&id=<%= thisAudit.getId()%>&aggiorna=<%=thisAudit.isAggiornaCategoria() %>"><%= toHtml(OrgCategoriaRischioList2.getSelectedValue(thisAudit.getTipoChecklist())) %></a>
	</td>

	
    
    <td class="formLabelTD">
	<%= ((thisAudit.getLivelloRischio()>0) ? (toHtml(String.valueOf(thisAudit.getLivelloRischio()))) : ("-")) %> 
	</td>
	 <td class="formLabelTD">
	<%= ((thisAudit.getStato()!=null ) ? (toHtml(String.valueOf(thisAudit.getStato()))) : ("-")) %> 
	</td>
	<td>
	<%if (TicketDetails.isCategoriaisAggiornata()==false) 
	{%>
	<a href = "javascript:eliminaCheckList('<%=OrgDetails.getAction() %>CheckList',<%=thisAudit.getId() %>,<%=TicketDetails.getId() %>,<%=OrgDetails.getIdStabilimento() %>)">Elimina</a>
	<%}else
		{
		%>
		&nbsp;
		<%		}%>
		
	</td>
   </tr>
   <%}%>
   <tr class="containerBody">
      <td colspan="5" name="punteggio" id="punteggio" nowrap class="formLabelNew">
        <dhv:label name=""><b>Punteggio Totale Check List:</b></dhv:label>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<%= request.getAttribute("PunteggioCheckList") %>
  
    </td>
  </tr>
  
   <tr class="containerBody">
   <td colspan="5" nowrap class="formLabel" ><b><dhv:label name="">Categoria Rischio Attribuita con Questo controllo:</dhv:label></b>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <%if(TicketDetails.getCategoriaRischio()>0) {%>
   
      
     <%= toHtml(String.valueOf(TicketDetails.getCategoriaRischio())) %>
   
   <%} %>
   </td>
  </tr>
  
  <%}else{%>
   <tr class="containerBody">
      <td colspan="5">
        <dhv:label name="accounts.accounts_asset_list_include.NoAuditFound">Nessuna Check List compilata.</dhv:label>
      </td>
   </tr>
   <%}%> 
   
 	</table>
  	
    </br>
    <%} 
	%>
 
 
    	<%@ include file="../controlliufficiali/controlli_ufficiali_dettaglio_sottoattivita.jsp" %>
 
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
<br /><br />

<%--} --%>
	<%@ include file="../controlliufficiali/opu_header_controlli_ufficiali.jsp" %>

	<input type="hidden" name="linea" id="linea" value="<%=request.getAttribute("linea_attivita")%>">
	
	
</dhv:container>
</form>
</body>

