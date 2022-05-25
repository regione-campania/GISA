<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="org.aspcfs.modules.canipadronali.base.Cane"%><jsp:useBean id="TipiControlliCani" class="org.aspcfs.utils.web.LookupList" scope="request"/>

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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.canipadronali.base.Proprietario" scope="request"/>
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
<jsp:useBean id="AltreNonCList" class="org.aspcfs.modules.altriprovvedimenti.base.TicketList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="mod" class="java.lang.String" scope="request"/>
<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="defect" class="org.aspcfs.modules.troubletickets.base.TicketDefect" scope="request"/>7<jsp:useBean id="ProvvedimentiList" class="org.aspcfs.modules.prvvedimentinc.base.TicketList" scope="request"/>
 
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_ufficiali_canipadronali.js"></script>
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



<%-- End Trails --%>
<%
String param1 = "id=" + TicketDetails.getId()+"&orgId="+TicketDetails.getOrgId()+"&idControllo="+TicketDetails.getIdControlloUfficiale();

%>
<%-- 
<dhv:permission name="impresa-generate-barcode-view">
	<div align="right" style="padding-right: 200px">
	<a href="javascript:checkBarcode('<%= TicketDetails.getOrgId() %>', '<%=TicketDetails.getId()%>');">Stampa Barcode Numero Verbale</a>
	</div>
</dhv:permission>
--%>
<body onload="showCampi('<%=TicketDetails.getTipoIspezione() %>');verificaChiusuraCu(<%=request.getAttribute("Chiudi") %>)">
<form name="details" action="CaniPadronaliVigilanza.do?command=ModifyTicket&auto-populate=true" method="post">
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="CaniPadronali.do?command=SearchForm">Anagrafica Cani di proprieta</a> > 
   <dhv:label name="campioni.aggiungi">Dettaglio Controllo Ufficiale </dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>



<dhv:container name="canipadronali" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId()%>' >
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
	String permission_op_edit = TicketDetails.getPermission_ticket()+"-vigilanza-edit" ;
	String permission_op_del = TicketDetails.getPermission_ticket()+"-vigilanza-delete" ;
	
	
	%>
	
	<%@ include file="../controlliufficiali/header_controlli_ufficiali.jsp" %>

	
	<%@ include file="../controlliufficiali/controlli_ufficiali_stampa_verbale_ispezione.jsp" %>

<input type = "hidden" name = "assetId" value = "<%=TicketDetails.getAssetId() %>">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni Proprietario</dhv:label></strong>
    </th>
	</tr>
	
	<%if(OrgDetails.getIdAsl()>0){ %>
	<tr class="containerBody">
		<td nowrap class="formLabel"> ASL Proprietario
		</td>
		<td><%=(OrgDetails!=null)? SiteIdList.getSelectedValue(OrgDetails.getIdAsl()) : "" %>&nbsp;
		</td> 
	</tr>
	<%} %>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Nominativo
		</td>
		<td><%=(OrgDetails!=null)? toHtml(OrgDetails.getRagioneSociale()) : "" %>
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Tipo(i)
		</td>
		<td>
		<%=(OrgDetails!=null)? toHtml(OrgDetails.getTipoProprietarioDetentore()) : "" %>
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Codice Fiscale
		</td>
		<td><%=(OrgDetails!=null)? toHtml(OrgDetails.getCodiceFiscale()) : "" %>
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Comune di nascita
		</td>
		<td><%=(OrgDetails!=null)? toHtml(OrgDetails.getLuogoNascita()): "" %>
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Data Nascita
		</td>
		<%
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		%>
		<td><%=(OrgDetails!=null && OrgDetails.getDataNascita()!=null )? sdf2.format(OrgDetails.getDataNascita()): "" %>&nbsp;
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Estremi Documento
		</td>
		<td><%=(OrgDetails!=null)? toHtml(OrgDetails.getDocumentoIdentita()): "" %>
		</td>
	</tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Indirizzi Proprietario</dhv:label></strong>
    </th>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Citta
		</td>
		<td>
		<%=(OrgDetails!=null)? toHtml(OrgDetails.getLista_indirizzi().get(0).getCitta()): "" %>
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Indirizzo
		</td>
		<td>
		<%=(OrgDetails!=null)? toHtml(OrgDetails.getLista_indirizzi().get(0).getVia()): "" %>
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Provincia
		</td>
		<td>
		<%=(OrgDetails!=null)? toHtml(OrgDetails.getLista_indirizzi().get(0).getProvincia()): "" %>
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Cap
		</td>
		<td>
		<%=(OrgDetails!=null)? toHtml( OrgDetails.getLista_indirizzi().get(0).getCap()): "" %>
		</td> 
	</tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni Conduttore</dhv:label></strong>
    </th>
	</tr>
	
	<tr class="containerBody">
		<td nowrap class="formLabel"> Nominativo
		</td>
		<td><%=toHtml(TicketDetails.getNome_conducente()) %>
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Codice Fiscale
		</td>
		<td><%=toHtml(TicketDetails.getCognome_conducente()) %>
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Comune di nascita
		</td>
		<td><%=toHtml(TicketDetails.getLuogo_nascita_conducente()) %>
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Data Nascita
		</td>
		<td><%=toHtml(TicketDetails.getData_nascita_conducente()) %>
       
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel"> Estremi Documento
		</td>
		<td><%=toHtml(TicketDetails.getDocumento_conducente()) %>
		</td>
	</tr>


</table>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Indirizzi Conduttore</dhv:label></strong>
    </th>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Citta
		</td>
		<td>
		<%= toHtml(TicketDetails.getCitta_conducente()) %>
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Indirizzo
		</td>
		<td>
			<%= toHtml(TicketDetails.getVia_connducente()) %>
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Provincia
		</td>
		<td>
		<%= toHtml(TicketDetails.getProv_conducente()) %>
		</td> 
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Cap
		</td>
		<td>
		<%= toHtml(TicketDetails.getCap_conducente()) %>
		</td> 
	</tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
		<tr>
    <th colspan="7">
      <strong><dhv:label name="">Lista Cani Controllati</dhv:label></strong>
    </th>
	</tr>
	<tr>
    <th >
     Num Cane
    </th>
	<th >
     Microchip
    </th>
    <th >
     Razza
    </th>
    <th >
     Taglia
    </th>
    <th >
     Mantello
    </th>
    <th >
    Sesso
    </th>
    <th >
     Data Nascita Cane
    </th>
	</tr>
	
	<%
	int num_cane = 1 ;
	for (Cane cane_vigilanza : OrgDetails.getListaCani()) { %>
	<tr class="containerBody">
		<td><b><%=num_cane %></b></td>
		<td><%=(cane_vigilanza!=null)?  toHtml(cane_vigilanza.getMc()):"" %>
		</td>
		<td><%= (cane_vigilanza!=null)?  toHtml(cane_vigilanza.getRazza()) :""%>
		</td>
	<td><%=(cane_vigilanza!=null)?  toHtml(cane_vigilanza.getTaglia()) :"" %>
		</td>
		<td><%=(cane_vigilanza!=null)?  toHtml(cane_vigilanza.getMantello()) :"" %>
		</td>
		<td><%=(cane_vigilanza!=null)?  toHtml(cane_vigilanza.getSesso()) :"" %>
		</td>
		<%

		String data_cane = "" ;
		if (cane_vigilanza!=null && cane_vigilanza.getDataNascita()!=null)
			data_cane = sdf2.format(cane_vigilanza.getDataNascita());
		%>
		<td><%=data_cane %>&nbsp;
		</td>
	</tr>
	
	<%num_cane ++ ;
	} %>
	
</table>


</br>

		<table cellpadding="4" cellspacing="0" width="100%" class="details">
		
		<%@ include file="../controlliufficiali/controlli_ufficiali_view.jsp" %>
		 <tr class="containerBody">
    	<td class="formLabel">
     	Situazione Controllata
    	</td>
	   <td>
        <%=TipiControlliCani.getSelectedValue(TicketDetails.getTipologia_controllo_cani()) %>
      </td>
    
  		</tr>
  		<%if (TicketDetails.getTipologia_controllo_cani()==5){ %>
  		 <tr class="containerBody">
    	<td class="formLabel">
     	Codice Azienda
    	</td>
	   <td>
        <%=TicketDetails.getCodice_azienda() %>
      </td>
    
  		</tr>
  		
  		
  		<%} %>
		</table>
		<br>
		
		

		
	
   <br>
   <%-- INCLUSIONE DETTAGLIO SISTEMA ALLARME RAPIDO --%>
   
   <%@ include file="../controlliufficiali/controlli_ufficiali_allarmerapido_view.jsp" %>
      <% int punteggioAccumulato = 0; %>
 
   
   
         <%@ include file="../controlliufficiali/controlli_ufficiali_sottoattivita.jsp" %>
    
   

   
   <%
  
   if(Audit.size()!=0)
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
   		    <th><b><dhv:label name="">Stato</dhv:label></b></th
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
   <%if(TicketDetails.getTipoCampione()==3){%>
   <td>
   
      <a href="CheckListCaniPadronali.do?command=View&orgId=<%=OrgDetails.getOrgId() %>&id=<%= thisAudit.getId()%>&aggiorna=<%=thisAudit.isAggiornaCategoria() %>"><%= toHtml(OrgCategoriaRischioList.getSelectedValue(thisAudit.getTipoChecklist())) %></a>
	</td>
	<%}else if(TicketDetails.getTipoCampione()==4){ %>
	 <td>
	    
	   <a href="CheckListCaniPadronali.do?command=View&orgId=<%=OrgDetails.getOrgId() %>&id=<%= thisAudit.getId()%>&aggiorna=<%=thisAudit.isAggiornaCategoria() %>"><%= toHtml(OrgCategoriaRischioList2.getSelectedValue(thisAudit.getTipoChecklist())) %></a>
	</td>
	<%} %>
	
    
    <td class="formLabelTD">
	<%= ((thisAudit.getLivelloRischio()>0) ? (toHtml(String.valueOf(thisAudit.getLivelloRischio()))) : ("-")) %> 
	</td>
	<td><%=thisAudit.getStato() %></td>
	<td>
	<%if (TicketDetails.isCategoriaisAggiornata()==false) 
	{%>
	<a href = "javascript:eliminaCheckList(<%=thisAudit.getId() %>,<%=TicketDetails.getId() %>,<%=OrgDetails.getOrgId() %>)">Elimina</a>
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
	<br>
	
	<br>
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
         <%
         if(thisTic.getTipologia()==2){ %>
        <label><b>Campione Num Verbale : <%=thisTic.getLocation() %></b></label>
        <%} %>
       </td>
      
		<td  valign="top" nowrap>
				<a href="CaniPadronaliCampioni.do?command=TicketDetails&idC=<%= thisTic.getIdControlloUfficiale() %>&id=<%= thisTic.getId() %>&orgId=<%= thisTic.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTic.getIdentificativo() %></a>
		</td>
     
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
          <a href="CaniPadronaliVigilanzaDocuments.do?command=Download&stream=true&tId=<%= thisTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
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
     
      while (a.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.tamponi.base.Ticket thisTam = (org.aspcfs.modules.tamponi.base.Ticket)a.next();
      
        punteggioAccumulato += thisTam.getPunteggio();
  %>
  <tr class="row<%= rowid %>">
      <td rowspan="2" width="10" valign="top" nowrap>
        
        <%if(thisTam.getTipologia()==7){ %>
        <label><b>Tampone</b></label>
        <%} %>
        </td>
      
		<td  valign="top" nowrap>
				<a href="CaniPadronaliTamponi.do?command=TicketDetails&id=<%= thisTam.getId() %>&orgId=<%= thisTam.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTam.getIdentificativo() %></a>
		</td>
  
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
          <a href="CaniPadronaliVigilanzaDocuments.do?command=Download&stream=true&tId=<%= thisTam.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
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

    Iterator z = AltreNonCList.iterator();

	
    if ( z.hasNext() ) {
      int rowid = 0;
      int i =0;
     //if(thisTic.getIdControlloUfficiale().equals(TicketDetails.getPaddedId())){
      while (z.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.altriprovvedimenti.base.Ticket altreNonC = (org.aspcfs.modules.altriprovvedimenti.base.Ticket)z.next();
       //if(TicketDetails.getPaddedId()==thisTic.getIdControlloUfficiale()){

  %>
  <tr class="row<%= rowid %>">
      <td rowspan="2" width="10" valign="top" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <%-- To display the menu, pass the actionId, accountId and the contactId--%>
        <%if(altreNonC.getTipologia()==10){ %>

        <label><b>Non Conformità Rilevate Non a carico sel soggetto Ispezionato</b></label>

        <%} %>
       </td>
      
		<td  valign="top" nowrap>
			<a href="<%=TicketDetails.getURlDettaglio() %>AltreNonConformita.do?command=TicketDetails&idIspezione=<%= TicketDetails.getTipoIspezione() %>&id=<%= altreNonC.getId() %>&orgId=<%= altreNonC.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= altreNonC.getIdentificativo() %></a>
		</td>
      
		<td valign="top" class="row<%= rowid %>">
      <% if(!User.getTimeZone().equals(altreNonC.getAssignedDate())){%>
      <zeroio:tz timestamp="<%= altreNonC.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= altreNonC.getAssignedDate() %>" dateOnly="true" timeZone="<%= altreNonC.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
      <% } %>
		</td>
	
		<td>Non Previsto</td>
		
		
	</tr>
	<tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
   
        <%= toHtml(altreNonC.getProblemHeader()) %>&nbsp;
        <% if (altreNonC.getClosed() == null) { 
        	if(altreNonC.isChiusura_attesa_esito()==true)
     	   {
     		   %>
     		   [<font color="orange">Pratica Temporaneamente Chiusa in Attesa di Esito di Campioni e/o Tamponi</font>]
     		   <%
     	   }
     	   else
     	   {
     		
     	   %>
        }
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%}} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
    </tr>
    
  <%}%>
  <%} else {%>
  
   
  <%}%>
  
  <%
	
    z = NonCList.iterator();
	
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
			<a href="CaniPadronaliNonConformita.do?command=TicketDetails&idIspezione=<%= TicketDetails.getTipoIspezione() %>&id=<%= thisNonC.getId() %>&orgId=<%= thisNonC.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisNonC.getIdentificativo() %></a>
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
          <a href="CaniPadronaliVigilanzaDocuments.do?command=Download&stream=true&tId=<%= thisNonC.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
        <%
              }
            }
          }
        %>
        <%= toHtml(thisNonC.getProblemHeader()) %>&nbsp;
        <% if (thisNonC.getClosed() == null) { 
        	if(thisNonC.isChiusura_attesa_esito()==true)
     	   {
     		   %>
     		   [<font color="orange">Pratica Temporaneamente Chiusa in Attesa di Esito di Campioni e/o Tamponi</font>]
     		   <%
     	   }
     	   else
     	   {
     		
     	   %>
        }
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%}} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
    </tr>
    
  <%}%>
  <%} else {%>
  
   
  <%}%>
  <%--/table--%>
  
   <%
	
    Iterator aa = ProvvedimentiList.iterator();
	
    if ( aa.hasNext() ) {
      int rowid = 0;
      int i =0;
     
      while (aa.hasNext()) {      
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.prvvedimentinc.base.Ticket thisTam = (org.aspcfs.modules.prvvedimentinc.base.Ticket)aa.next();
      
       
  %>
  <tr class="row<%= rowid %>">
      <td rowspan="2" width="10" valign="top" nowrap>
        
        
        <label><b>Provvedimento</b></label>
    
        </td>
      
		<td  valign="top" nowrap>
				<a href="CaniPadronaliProvvedimenti.do?command=TicketDetails&idC=<%= thisTam.getIdControlloUfficiale()%>&id=<%= thisTam.getId() %>&orgId=<%= thisTam.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTam.getIdentificativo() %></a>
		</td>
  
		<td valign="top" class="row<%= rowid %>">
     	Non Previsto
		</td>
		
		<td width="10%" valign="middle">Non Previsto</td>	
		
		
	</tr>
	<tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
        <%= toHtml(thisTam.getProblemHeader()) %>&nbsp;
        <% if (thisTam.getClosed() == null) { %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
    </tr>
    
  <%}}%>

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

	<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_view.jsp" %>
	<br>
	<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_non_in_regione_view.jsp" %>
<%--} --%>
	<%@ include file="../controlliufficiali/header_controlli_ufficiali.jsp" %>

	

	
	
</dhv:container>
</form>
</body>