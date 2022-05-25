<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.vigilanza.base.*,com.zeroio.iteam.base.*,org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="TicList" class="org.aspcfs.modules.campioni.base.TicketList" scope="request"/>
<jsp:useBean id="SanzioniList" class="org.aspcfs.modules.sanzioni.base.TicketList" scope="request"/>
<jsp:useBean id="SequestriList" class="org.aspcfs.modules.sequestri.base.TicketList" scope="request"/>
<jsp:useBean id="NonCList" class="org.aspcfs.modules.nonconformita.base.TicketList" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.checklist.base.AuditList" scope="request"/>
<jsp:useBean id="ReatiList" class="org.aspcfs.modules.reati.base.TicketList" scope="request"/>
<jsp:useBean id="TamponiList" class="org.aspcfs.modules.tamponi.base.TicketList" scope="request"/>
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DistribuzionePartita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DestinazioneDistribuzione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketCategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.operatorifuoriregione.base.Organization" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
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
<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvvedimentiList" class="org.aspcfs.modules.prvvedimentinc.base.TicketList" scope="request"/>
<jsp:useBean id="AltreNonCList" class="org.aspcfs.modules.altriprovvedimenti.base.TicketList" scope="request"/>
 
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="defect" class="org.aspcfs.modules.troubletickets.base.TicketDefect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script type="text/javascript">  </script>
<%@ include file="../initPage.jsp" %>
<form name="details" action="OperatoriFuoriRegioneVigilanza.do?command=ModifyTicket&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<td>
  <a href="OperatoriFuoriRegione.do?command=SearchForm"><dhv:label name="a">Attività Mobile Fuori Ambito ASL</dhv:label></a> > 
  <a href="OperatoriFuoriRegione.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="OperatoriFuoriRegione.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Scheda Attività Mobile Fuori Ambito ASL</dhv:label></a> >
  <a href="OperatoriFuoriRegione.do?command=ViewVigilanza&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="vigilanza">Tickets</dhv:label></a> >
  <dhv:label name="campione.dettagli">Scheda Controllo Ufficiale</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%
	String param1 = "id=" + TicketDetails.getId();
%>
<dhv:container name="operatoriregione" selected="vigilanza" object="OrgDetails" param='<%="orgId=" + OrgDetails.getOrgId()%>' hideContainer='<%=isPopup(request)
						|| (defectCheck != null && !"".equals(defectCheck
								.trim()))%>'>

	<%@ include file="ticket_header_include_vigilanza.jsp"%>
	
	
	<%
	UserBean user=(UserBean)session.getAttribute("User");
	int siteIdUser=user.getSiteId();
	
	%>
														<%
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-vigilanza-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-vigilanza-delete" ;
	
	%>
	<%@ include file="../controlliufficiali/header_controlli_ufficiali.jsp" %>
		
	<%@ include file="../controlliufficiali/controlli_ufficiali_stampa_verbale_ispezione.jsp" %>
	
	<%-- INCLUSIONE DETTAGLIO CONTROLLO UFFICIALE--%>
	
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
		
		<%@ include file="../controlliufficiali/controlli_ufficiali_view.jsp" %>
	</table>
   <br>
   <br>
	
	
	<%-- -<table cellpadding="4" cellspacing="0" border="0" width="100%" style="margin-left: 550px;margin-bottom: 50px">		
		<% 
			tipoControllo = TicketDetails.getTipoCampione();
 			if( tipoControllo != 3 ){ 
 		%>
 		<% if(request.getAttribute("bozza")== null || Boolean.parseBoolean((String)request.getAttribute("bozza")) ) { %>		
 			<tr>
				<td>
					<a href="OperatoriFuoriRegioneVigilanza.do?command=StampaSchedaIspezione&idControllo=<%=TicketDetails.getId()%>&orgId=<%=TicketDetails.getOrgId()%>"
					id="verbale"
					target ="_blank"
					> Mod-5-Verbale-ispezione-Apri e Modifica</a>	
				</td>
			</tr>
		<% } else { %>
			<tr>
				<td>
					<a href="OperatoriFuoriRegioneVigilanza.do?command=StampaSchedaIspezione&idControllo=<%=TicketDetails.getId()%>&orgId=<%=TicketDetails.getOrgId()%>" 
					id="verbale_def"
					target="_blank" />Mod-5-Verbale-ispezione-Versione Definitiva</a>	
				</td>
			</tr>	
		<%} %>
 			<% } %>
		
		</table>
	-%>
	
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<%@ include file="../controlliufficiali/controlli_ufficiali_view.jsp" %>
	
		
	</table>
   <br>
   <br>
   
   
   
   <%-- INCLUSIONE DETTAGLIO SISTEMA ALLARME RAPIDO --%>
   
   <%@ include file="../controlliufficiali/controlli_ufficiali_allarmerapido_view.jsp" %>
   
      <%
         	int punteggioAccumulato = 0;
         %>
   <%--if(TicketDetails.getTipoCampione()!=5) {  //controllo se è in sorveglianza
 
 --%> 
   
 
        <%@ include file="../controlliufficiali/controlli_ufficiali_sottoattivita.jsp" %>
   
   <br/>

   
   <%
   
      	if (Audit.size() != 0) {
      %>
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
   				if (itr.hasNext()) {
   					int rowid = 0;
   					int i = 0;
   					while (itr.hasNext()) {
   						i++;
   						rowid = (rowid != 1 ? 1 : 2);
   						org.aspcfs.modules.audit.base.Audit thisAudit = (org.aspcfs.modules.audit.base.Audit) itr
   								.next();
   						punteggioAccumulato += thisAudit
   								.getLivelloRischio();
   %> 
   <tr class="row<%=rowid%>">
   <%
   	if (TicketDetails.getTipoCampione() == 3) {
   %>
   <td>
   
      <a href="AccountsAudit.do?command=View&id=<%=thisAudit.getId()%>"><%=toHtml(OrgCategoriaRischioList
															.getSelectedValue(thisAudit
																	.getTipoChecklist()))%></a>
	</td>
	<%
		} else if (TicketDetails.getTipoCampione() == 4) {
	%>
	 <td>
	    
	   <a href="AccountsAudit.do?command=View&id=<%=thisAudit.getId()%>"><%=toHtml(OrgCategoriaRischioList2
															.getSelectedValue(thisAudit
																	.getTipoChecklist()))%></a>
	</td>
	<%
		}
	%>
	<td>
	<%=((thisAudit
														.getLivelloRischio() > 0) ? (toHtml(String
														.valueOf(thisAudit
																.getLivelloRischio())))
														: ("-"))%> 
	</td>
	<%--td>
      <%= ((thisAudit.getLivelloRischioFinale()>0) ? (toHtml(String.valueOf(thisAudit.getLivelloRischioFinale()))) : ("Non Aggiornato")) %>  
    </td--%>
   
    <td>
      <%=toHtml(String.valueOf(thisAudit
												.getCategoria()))%>
    </td>
   </tr>
   <%
   	}
   				} else {
   %>
   <tr class="containerBody">
      <td colspan="5">
        <dhv:label name="accounts.accounts_asset_list_include.NoAuditFound">Nessuna Check List compilata.</dhv:label>
      </td>
   </tr>
   <%
   	}
   %> 
 	</table>
  	<br/>
	<%
		}

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
  <dhv:evaluate if="<%=TicketDetails
												.getEstimatedResolutionDate() != null%>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzionia.data_ispezione">Data</dhv:label>
    </td>
    <td>
    <zeroio:tz
				timestamp="<%=TicketDetails.getEstimatedResolutionDate()%>" dateOnly="true"
				timeZone="<%=TicketDetails
										.getEstimatedResolutionDateTimeZone()%>"
				showTimeZone="false" default="&nbsp;" /> 
    </td>
  </tr>
  </dhv:evaluate>
  
<dhv:evaluate if="<%=(TicketDetails.getPunteggio() > -1)%>">
<tr class="containerBody">
      <td name="punteggio" id="punteggio" nowrap class="formLabel">
        <dhv:label name="">Punteggio Accumulato </dhv:label>
      </td>
    <td>
    	<%=toHtmlValue(TicketDetails.getPunteggio())%>
      <input type="hidden" name="punteggio" id="punteggio" size="20" maxlength="256" />
    </td>
    <%-- 
    <%
    	if (TicketDetails.getPunteggio() <= 3) {
    %>
    <td>Esito Controllo Ufficiale Favorevole</td>
    <%
    	}
    %>--%>
  </tr>
 </dhv:evaluate>
 <%
 	//if(punteggioAccumulato<=3) {
 %>
 
 <dhv:evaluate>
<tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Esito </dhv:label>
      </td>
    <td>
    	
    </td>
  </tr>
 </dhv:evaluate> 
 
<dhv:evaluate if="<%=hasText(TicketDetails.getSolution())%>">
<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Ulteriori Note</dhv:label>
    </td>
    <td>
      <%=toString(TicketDetails.getSolution())%>
    </td>
    </tr>
</dhv:evaluate>
    </table>
&nbsp;
<br />
<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_view.jsp" %>
	<br>
	<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_non_in_regione_view.jsp" %>
		
	<%@ include file="../controlliufficiali/header_controlli_ufficiali.jsp" %>
	
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