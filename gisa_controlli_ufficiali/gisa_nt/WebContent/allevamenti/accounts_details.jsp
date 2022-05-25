<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.allevamenti.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>

<jsp:useBean id="InfoAllevamentoBean" class="it.izs.bdn.bean.InfoAllevamentoBean" scope="request"/>


<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="Organization" class="org.aspcfs.modules.osmregistrati.base.Organization" scope="session"/>

<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>


<script>
function openPopupBox(url){
	var res;
	var result;
		window.open(url,'popupSelect',
		'height=300px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		
}

function openPopupModulesPdf(orgId){
	var res;
	var result;
		window.open('ManagePdfModules.do?command=PrintSelectedModules&orgId='+orgId,'popupSelect',
		'height=300px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		} 

function confermaRicercaOsmDaAllevamento() {
	var answer = confirm("Nessun OSM associato all'allevamento.\n\nVuoi cercare un OSM da associare?")
	if (answer) {
		location.href="OsmRegistrati.do?command=SearchForm&ricercaOsmAssociabiliParameter=1";
	}
}
</script>

<body>


<%if (refreshUrl!=null && !"".equals(refreshUrl)){ %>
<script language="JavaScript" TYPE="text/javascript">
parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>
<%}%>

<%
// nel caso in cui arrivo nella pagina cliccando su "Azienda Zootecnica"
// dalla scheda di un OSM che non ha allevamenti associati
// visualizzo il link per l'associazione
Boolean ricercaAllevamentiAssociabiliAttribute = (Boolean)request.getAttribute("ricercaAllevamentiAssociabiliAttribute");
if (ricercaAllevamentiAssociabiliAttribute != null && ricercaAllevamentiAssociabiliAttribute == true) {
	org.aspcfs.modules.osmregistrati.base.Organization osmACuiAssociareAllevamento = (org.aspcfs.modules.osmregistrati.base.Organization)session.getAttribute("osmACuiAssociareAllevamento");
	session.removeAttribute("osmACuiAssociareAllevamento");
	session.removeAttribute("codiceComuneOsmPerRicercaAllevamenti");
	%>		
	<div align="center">
		<a style="BACKGROUND-COLOR:#BDCFFF; font-weight:bold; font-size:12;" href="AssociaAziendaZootecnicaAOsm.do?command=View&orgId=<%=OrgDetails.getId()%>&idOsmDaAssociare=<%=osmACuiAssociareAllevamento.getOrgId()%>&accountNumberOsmDaAssociare=<%=osmACuiAssociareAllevamento.getAccountNumber()%>">ASSOCIA QUESTO ALLEVAMENTO ALL'OSM "<%=osmACuiAssociareAllevamento.getName()%>"</a>
	</div>
	<br/>
	<%
} 
%>

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">

<tr>
<td>
<a href="Allevamenti.do"><dhv:label name="allevamenti.allevamenti">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Allevamenti.do?command=Search"><dhv:label name="allevamenti.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Allevamenti.do?command=Dashboard">Cruscotto</a> >
<%}%>



<dhv:label name="allevamenti.details">Account Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>

<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %>


<dhv:permission name="allevamenti-allevamenti-report-view">
  <table width="100%" border="0">
    <tr>
      <td nowrap align="right">
        <!-- img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="Allevamenti.do?command=PrintReport&file=allevamenti.xml&id=<%= OrgDetails.getId() %>;"><dhv:label name="allevamenti.osa.print">Stampa Scheda Allevamenti</dhv:label></a>
         
         <a href="Allevamenti.do?command=StampaSchedaAllevamento&file=allevamenti.xml&id=<%= OrgDetails.getId() %>;"><dhv:label name="allevamenti.osa.print">Stampa Scheda Allevamenti</dhv:label></a-->
      
         <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
       <%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda Allevamenti" value="Stampa Scheda Allevamenti"		onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '-1', '-1', '-1', '-1', 'SchedaAllevamenti');"--%>
 
      		<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda" value="Stampa Scheda"		onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '-1', '-1', '-1', '6');">
 
 
      </td>
    </tr>
  </table>
</dhv:permission>


<!--<dhv:permission name="allevamento-linkprintmodules-view">
<div style="padding-right: 200px" align="right">
	<a href="javascript:openPopupModulesPdf('<%= OrgDetails.getOrgId() %>');">Stampa moduli precompilati</a>
</div>
</dhv:permission>-->

<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>
<dhv:container name="allevamenti" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="false" >

<jsp:useBean id="differenza" class="java.lang.String" scope="request"/>

<%
	if((Boolean)request.getAttribute("trovatoInBdn"))
	{
%>

	<input type="button" value="COMPARA DATI CON BDN" onclick="window.open('Allevamenti.do?command=CompareWS&allevId=<%=OrgDetails.getId_allevamento()%>&denominazione=<%=(OrgDetails.getName()!=null)?(OrgDetails.getName().replaceAll("%", "_____").replaceAll("\"", "&quot;").replaceAll("'", "|") ):("")  %>&orgId=<%= OrgDetails.getId() %>&codiceAzienda=<%=OrgDetails.getAccountNumber() %>&pIva=<%=(OrgDetails.getPartitaIva()!=null) ? OrgDetails.getPartitaIva().trim() : "" %>&codSpecie=<%= "0"+OrgDetails.getSpecieA() %>','popupSelect',
		'height=400px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes')"> 
		
<%
if(differenza!=null && !differenza.equals(""))
{
%>	
		<font color="red"><b>I DATI NON SONO ALLINEATI</b></font><br/>
<% 
}	
%>
		
<%
	}
	else
	{
%>

		Allevamento non trovato in bdn, aggiornamento dati non possibile. <br/>
<%
		
	}
%>
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <!--<dhv:permission name="allevamenti-allevamenti-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='Allevamenti.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>-->
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
<% if(OrgDetails.getDate2() == null) { %>
  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="allevamenti-allevamenti-edit"><input type="button" value="Modifica"	onClick="javascript:window.location.href='Allevamenti.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
<% } %>
</dhv:evaluate>
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="allevamenti-allevamenti-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='Allevamenti.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="allevamenti-allevamenti-delete"><input type="button" value="<dhv:label name="allevamenti.allevamenti_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('Allevamenti.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Allevamenti.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>

<dhv:permission name="global-search-statosanitario-add">
	<% if (OrgDetails.getTipologia()==2 && (OrgDetails.getSpecieAllev().equalsIgnoreCase("PESCI") || OrgDetails.getSpecieAllev().equalsIgnoreCase("molluschi") || OrgDetails.getSpecieAllev().equalsIgnoreCase("crostacei") )) {%>
	<br/>
	<div align="right">
		<b>     
			<a href="#" style="-moz-appearance: button;" onClick="openPopupBox('PrintReportVigilanza.do?command=GestioneStatoSanitarioBdn&idAzienda=<%=OrgDetails.getOrgId()%>')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Gestione dati per invio in BDN &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
		</b>
	</div>
	<br/>
	<% } %>
	</dhv:permission>
	
<dhv:permission name="allevamenti-allevamenti-edit,allevamenti-allevamenti-delete"><br>&nbsp;</dhv:permission>

<%-- <dhv:permission name="opu-import-add"> --%>
<%-- <center><font color="red"><b><%="Lo stabilimento ha linee non aggiornate." %></b></font></center> --%>
<!--   <div align="center"> -->
<!--  	 <br/><br/> -->
 		<%--<input type="button" class="yellowBigButton" value="AGGIORNA LINEE DI ATTIVITA' PREGRESSE DA MASTERLIST" 
 		onClick="openPopupLarge('Accounts.do?command=PrepareUpdateLineePregresse&orgId=<%=OrgDetails.getOrgId() %>&lda_prin=<%=linea_attivita_principale.getId() %>')"
 		--%>
 	<%-- onClick="loadModalWindow();window.location.href='OpuStab.do?command=PrepareUpdateLineePregresse&stabId=<%=StabilimentoDettaglio.getIdStabilimento() %>'"--%>
 
 <%-- if (!OrgDetails.getOrientamentoProd().toLowerCase().contains("autoconsumo")) {%>
 	<input type="button" class="yellowBigButton"s
				value="Importa in Anagrafica stabilimenti"
			    onClick="javascript:window.location.href='OpuStab.do?command=CaricaImport&orgId=<%=OrgDetails.getOrgId()%>'">
<%} else {  %>
<input type="button" class="greyBigButton" value="Importa in Anagrafica stabilimenti" onClick="alert('Impossibile importare causa orientamento produttivo (<%=OrgDetails.getOrientamentoProd() %>)')"><br/>
<font color="red">Impossibile importare causa orientamento produttivo (<%=OrgDetails.getOrientamentoProd() %>)</font>
<%} --%>
<!--  <br/><br/>	 -->
<!--  	</div> -->

<%-- </dhv:permission> --%>

<dhv:permission name="note_hd-view">
<jsp:include page="../note_hd/link_note_hd.jsp">
<jsp:param name="riferimentoId" value="<%=OrgDetails.getOrgId() %>" />
<jsp:param name="riferimentoIdNomeTab" value="organization" />
</jsp:include> <br><br>
</dhv:permission>

<jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">
    <jsp:param name="riferimentoIdPreaccettazione" value="<%=OrgDetails.getOrgId() %>" />
    <jsp:param name="riferimentoIdNomePreaccettazione" value="orgId" />
    <jsp:param name="riferimentoIdNomeTabPreaccettazione" value="organization" />
    <jsp:param name="userIdPreaccettazione" value="<%=User.getUserId() %>" />
</jsp:include>


<script>
function openPopupScortaFarmaci(orgId){
	  window.open('GestioneScortaFarmaci.do?command=ViewScortaAllevamento&orgId='+orgId,'popupSelect',
	         'height=500px,width=600px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
		}

function refreshDimensioniIframe(iframe){
	$(iframe).height($(iframe).contents().find('html').height());
}
</script>

<% if (OrgDetails.getDate2()==null) { %>
<dhv:permission name="scortefarmaci-view">
<input type="button" value="GESTIONE SCORTA FARMACI" onClick="openPopupScortaFarmaci('<%=OrgDetails.getOrgId()%>')"/>
</dhv:permission>
<% } %>

<% if (OrgDetails.getSpecieA() == 121 || OrgDetails.getSpecieA() == 129) {%>
<iframe scrolling="no" src="SchedeAdeguamentiAction.do?command=ViewSchedaPiccioni&orgId=<%=OrgDetails.getOrgId() %>" style="top:0;left: 0;width:100%;height: 100%; border: none;" onload="refreshDimensioniIframe(this)"></iframe>
<% if (request.getAttribute("MessaggioSchedaPiccioni")!=null && !request.getAttribute("MessaggioSchedaPiccioni").equals("")) {%>
<script>
alert("<%=request.getAttribute("MessaggioSchedaPiccioni")%>");
</script>
<%} %><% } %>


<iframe scrolling="no" src="SchedeAdeguamentiAction.do?command=ViewSchedaBiogas&orgId=<%=OrgDetails.getOrgId() %>" style="top:0;left: 0;width:100%;height: 100%; border: none;" onload="refreshDimensioniIframe(this)"></iframe>
<% if (request.getAttribute("MessaggioSchedaBiogas")!=null && !request.getAttribute("MessaggioSchedaBiogas").equals("")) {%>
<script>
alert("<%=request.getAttribute("MessaggioSchedaBiogas")%>");
</script>
<%} %>

<br>
<br>
<jsp:include page="../gestionecodicesinvsa/gestione_codice_sinvsa.jsp">
	<jsp:param name="action" value="Allevamenti" />
	<jsp:param name="riferimentoId" value="<%=OrgDetails.getOrgId() %>" />
	<jsp:param name="riferimentoIdNomeTab" value="organization" />
	<jsp:param name="idRuoloUtente" value="<%= User.getRoleId() %>" />
</jsp:include>


<%if (1==1) { %>
<jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="6" />
     </jsp:include>
  <% } else { %>   
     
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="allevamenti.allevamenti_details.PrimaryInformation">Primary Information</dhv:label></strong>
    </th>
  </tr>
  
  <dhv:include name="allevamenti-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti.site">Site</dhv:label>
    </td>
    <td>
      <%-- ASL --%>
      <%= SiteList.getSelectedValue(OrgDetails.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
</dhv:include>
  

 
    <dhv:evaluate if="<%= hasText(OrgDetails.getName()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Denominazione</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getName()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate>

  	
<dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.accountNumbera">Codice Azienda</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getAccountNumber()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.accountNumbera">Codice Fiscale Detentore</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getCodiceFiscaleCorrentista()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.accountNumbera">Codice Fiscale Proprietario</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getCodiceFiscaleRappresentante()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= hasText(OrgDetails.getSpecieAllev()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.specieAlleva">Specie Allevata</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getSpecieAllev()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= hasText(OrgDetails.getOrientamentoProd()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.orientamentoProd">Orientamento Produttivo</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getOrientamentoProd()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= hasText(OrgDetails.getTipologiaStrutt()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.tipologiaStrutt">Tipologia Struttura</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getTipologiaStrutt()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>


<dhv:evaluate if="<%= OrgDetails.getTypes().size() > 0 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti.allevamenti.types">Account Type(s)</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getTypes().valuesAsString()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>


  <dhv:evaluate if="<%= hasText(OrgDetails.getPartitaIva()) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      <dhv:label name="">Partita IVA / Codice Fiscale</dhv:label>
			</td>
			<td>
         <%= toHtml(OrgDetails.getPartitaIva()) %>&nbsp;
			</td>
		</tr>
  </dhv:evaluate>
  
  
   <% if(Audit!=null){ %>
  
  <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Categoria di Rischio</dhv:label>
      </td>
      <td>
         <%= (((OrgDetails.getCategoriaRischio()>0))?(OrgDetails.getCategoriaRischio()):("3"))%>
      </td>
    </tr>
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Prossimo Controllo</br>con la tecnica della Sorveglianza</dhv:label>
      </td>
      <td>
      <% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
		Timestamp d = new Timestamp (datamio.getTime()); %>
      <% SimpleDateFormat dataPC = new SimpleDateFormat("dd/MM/yyyy");
       %>
         <%= (((OrgDetails.getData_prossimo_controllo()!=null))?(dataPC.format(OrgDetails.getData_prossimo_controllo())):(dataPC.format(d)))%>
      </td>
    </tr>
  <%}%>



   
  	
        <dhv:include name="organization.date1" none="true">
        <dhv:evaluate if="<%= (OrgDetails.getDate1() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="allevamenti.allevamenti_add.date1a">Data Inizio Attività</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OrgDetails.getDate1() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
        <%= showAttribute(request, "contractEndDateError") %>
      </td>
    </tr>
    </dhv:evaluate>
    </dhv:include>  	

<dhv:include name="organization.rating" none="true">
  <dhv:evaluate if="<%= OrgDetails.getRating() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="sales.rating">Rating</dhv:label>
      </td>
      <td>
        <%= RatingList.getSelectedValue(OrgDetails.getRating()) %> 
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
  	


<dhv:evaluate if="<%= (OrgDetails.getDate2() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Stato Allevamento</dhv:label>
    </td>
    <td>
     <font color='red'>Cessato</font> in data <zeroio:tz timestamp="<%= OrgDetails.getDate2() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>, per cui non più gestibile in modifica.
    </td>
  </tr>
</dhv:evaluate>

<% if(OrgDetails.getAccountSize() > 0){ %>
  <tr class="containerBody">
    <td name="accountSize1" id="accountSize1" nowrap class="formLabel">
      <dhv:label name="osa.categoriaRischio"/>
    </td>
    <td>
      <%= OrgCategoriaRischioList.getSelectedValue(OrgDetails.getAccountSize()) %>
    </td>
  </tr>
<% } %>


  	<dhv:include name="organization.contractEndDate" none="true">
<dhv:evaluate if="<%= hasText(OrgDetails.getContractEndDateString()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti.allevamenti_add.ContractEndDate">Contract End Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>


</dhv:include>

<tr class="containerBody"><td nowrap class="formLabel">Numero Capi</td>
<td>

<%if(OrgDetails.isSuini())
	{
	%>
	<b>Numero Capi Totali: <font color = "green"><%=OrgDetails.getNumeroCapi() %> </font></b><br>
	<br><b>Numero Lattonzoli : <%=OrgDetails.getNum_lattonzoli() %> </b>
	<br><b>Numero Magroncelli : <%=OrgDetails.getNum_magroncelli()%> </b>
	<br><b>Numero Magroni : <%=OrgDetails.getNum_magrnoni() %> </b>
	<br><b>Numero Grassi : <%=OrgDetails.getNum_grassi() %> </b>
	<br><b>Numero Scrofe : <%=OrgDetails.getNum_scrofe() %> </b>
	<br><b>Numero Scrofette : <%=OrgDetails.getNum_scrofette() %> </b>
	<br><b>Numero Verri : <%=OrgDetails.getNum_verri() %> </b>
	<br><b>Numero Cinghiali : <%=OrgDetails.getNum_cinghiali() %> </b>
	<%if(OrgDetails.getData_censimento()!=null){ %>
	<b>Data Ultimo Censimento  <%=toDateString(OrgDetails.getData_censimento() ) %>  </b> <br>
	<%} %>
	
	
<%}
else
{
	if(OrgDetails.isBovini())
	{
	%>
	<b>Numero Capi Totali: <font color = "green"><%=OrgDetails.getNumeroCapi() %> </font></b><br>
	<b>Numero Capi con più di 6 Settimane:  <%=OrgDetails.getNumero_capi_sei_mesi() %> </b><br>
	<b>Numero capi con più di 1 anno:  <%=OrgDetails.getNumero_capi_mag_anno() %></b><br>
	<b>Aggiornato al <%if(!"0".equals(OrgDetails.getNumeroCapi())){ %> <%=toDateString(OrgDetails.getModified()) %> <%} %> </b> <br>
	
	<a  href="#dialog" name = "modal" ><b>Lista Capi</b></a>
		

	<%
	
	}
	else
	{
		if(OrgDetails.isOvini())
		{
		%>  <b>Flag Ovini <%= OrgDetails.getFlag_ovini() 				%> </b><br>
			<b>Flag Caprini <%= OrgDetails.getFlag_caprini()				%> </b><br>
			<b>Num Ovini <%= OrgDetails.getNum_ovini() 				%> </b><br>
			<b>Num Caprini <%= OrgDetails.getNum_caprini()				%> </b><br>
			<b>Num Femmine <%= OrgDetails.getNum_femmine ()				%> </b><br>
			<b>Num Maschi <%= OrgDetails.getNum_maschi() 				%> </b><br>
			<b>Num capi magg. sei mesi <%= OrgDetails.getNum_capi_sup_sei_mesi() 	%> </b><br>
			<b>Num capi min. sei mesi  <%= OrgDetails.getNum_capi_min_sei_mesi() 	%> </b><br>
			<b>Capi Ovini Tot. <%= OrgDetails.getNum_capi_ovini_totale() 	%> </b><br>


			<%
		
		}
		if(OrgDetails.isAvicoli())
		{
		%>  <b>Capi			<%= toHtml(OrgDetails.getCapi()) 				%> </b><br>
			<b>Ciclo Anno 	<%= toHtml(OrgDetails.getCiclo_anno())			%> </b><br>
			<b>Uova Anno 	<%= toHtml(OrgDetails.getUova_anno())			%> </b><br>



			<%
		
		}
	}
}
	%>
</td>
 </tr>

</table>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name=""> Proprietario</dhv:label></strong>
	  </th>
	  
  </tr>
  
 <%if (OrgDetails.getNominativoProp()!=null && !OrgDetails.getNominativoProp().equals("")){ %>
   <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
       Nominativo
      </td>
      <td>
        <%= toHtml(OrgDetails.getNominativoProp()) %>        
      </td>
    </tr>
    <%} %>
    
     <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
       Codice Fiscale
      </td>
      <td>
        <%= toHtml(OrgDetails.getCodiceFiscaleRappresentante()) %>        
      </td>
    </tr>
   
   <%if ((OrgDetails.getIndirizzoProp()!= null && !OrgDetails.getIndirizzoProp().trim().equals("")) || (OrgDetails.getComuneProp()!= null && !OrgDetails.getComuneProp().trim().equals(""))){ %>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
       Indirizzo
      </td>
      <td>
        <%= toHtml(OrgDetails.getIndirizzoProp()) + ","+ toHtml(OrgDetails.getProvProp())+ "<br>" + toHtml(OrgDetails.getComuneProp()) + ","+ toHtml(OrgDetails.getCapProp()) %>        
      </td>
    </tr>
    <%} %>
  
  </table>
  
<br>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name=""> Detentore</dhv:label></strong>
	  </th>
	  
  </tr>
  
 <%if (OrgDetails.getNominativoDetentore()!=null && !OrgDetails.getNominativoDetentore().equals("")){ %>
   <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
       Nominativo
      </td>
      <td>
        <%= toHtml(OrgDetails.getNominativoDetentore()) %>        
      </td>
    </tr>
    <%} %>
    
     <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
       Codice Fiscale
      </td>
      <td>
        <%= toHtml(OrgDetails.getCodiceFiscaleCorrentista()) %>        
      </td>
    </tr>
   
   <%if ((OrgDetails.getIndirizzoDetentore()!= null && !OrgDetails.getIndirizzoDetentore().trim().equals("")) || (OrgDetails.getComuneDetentore()!= null && !OrgDetails.getComuneDetentore().trim().equals(""))){ %>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
       Indirizzo
      </td>
      <td>
        <%= toHtml(OrgDetails.getIndirizzoDetentore()) + ","+ toHtml(OrgDetails.getProvDetentore())+ "<br>" + toHtml(OrgDetails.getComuneDetentore()) + ","+ toHtml(OrgDetails.getCapDetentore()) %>        
      </td>
    </tr>
    <%} %>
  
  </table>
  
<br>
<dhv:include name="organization.addresses" none="true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="stabilimenti.stabilimenti_add.Addresses">Addresses</dhv:label></strong>
	  </th>
  </tr>
<%  
  Iterator iaddress = OrgDetails.getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
	if(thisAddress.getCity()!=null && !thisAddress.getCity().equals(""))
	{
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <%=(thisAddress.getType()==1)? "Sede Legale" : (thisAddress.getType()==5)? "Sede Operativa" : "" %>
        
      </td>
      <td>
        <%= toHtml(thisAddress.toString()) %>&nbsp;<br/><%=thisAddress.getGmapLink() %>
        <dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">        
          <dhv:label name="stabilimenti.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate>
      </td>
    </tr>
<%
	}
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E"><dhv:label name="contacts.NoAddresses">No addresses entered.</dhv:label></font>
      </td>
    </tr>
<%}%>

</table>
</dhv:include>
<br/>


<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="allevamenti.allevamenti_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti.allevamentiasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <%=toHtml(OrgDetails.getNotes()) %>&nbsp;
    </td>
  </tr>
</table>

<%} %>


<br>
<%if(OrgDetails.isSuini()){ %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Ulteriori Informazioni</dhv:label></strong>
	  </th>
  </tr>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti..Notes">Suini</dhv:label>
    </td>
    <td>
     <input type = "checkbox" disabled="disabled" <%if (OrgDetails.getFlag_suini()){ %>checked="checked"<%} %>> SI
     <input type = "checkbox" disabled="disabled"  <%if (!OrgDetails.getFlag_suini()){ %>checked="checked"<%} %>> NO
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti..Notes">Cinghiali</dhv:label>
    </td>
    <td>
     <input type = "checkbox" disabled="disabled"  <%if (OrgDetails.getFlag_cinghiali()){ %>checked="checked"<%} %>> SI
     <input type = "checkbox" disabled="disabled"  <%if (!OrgDetails.getFlag_cinghiali()){ %>checked="checked"<%} %>> NO
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti..Notes">Ciclo Riproduzione</dhv:label>
    </td>
    <td>
     <input type = "checkbox" disabled="disabled"  <%if (OrgDetails.getFlag_ciclo_riproduzione()){ %>checked="checked"<%} %>> SI
     <input type = "checkbox" disabled="disabled"  <%if (!OrgDetails.getFlag_ciclo_riproduzione()){ %>checked="checked"<%} %>> NO
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti..Notes">Magronaggio</dhv:label>
    </td>
    <td>
     <input type = "checkbox" disabled="disabled"  <%if (OrgDetails.getFlag_magronaggio()){ %>checked="checked"<%} %>> SI
     <input type = "checkbox" disabled="disabled"  <%if (!OrgDetails.getFlag_magronaggio()){ %>checked="checked"<%} %>> NO
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti..Notes">Finissaggio</dhv:label>
    </td>
    <td>
     <input type = "checkbox" disabled="disabled"  <%if (OrgDetails.getFlag_finissaggio()){ %>checked="checked"<%} %>> SI
     <input type = "checkbox" disabled="disabled"   <%if (!OrgDetails.getFlag_finissaggio()){ %>checked="checked"<%} %>> NO
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti..Notes">Riproduzione</dhv:label>
    </td>
    <td>
     <input type = "checkbox" disabled="disabled"  <%if (OrgDetails.getFlag_riproduzione()){ %>checked="checked"<%} %>> SI
     <input type = "checkbox" disabled="disabled"  <%if (!OrgDetails.getFlag_riproduzione()){ %>checked="checked"<%} %>> NO
    </td>
  </tr>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti..Notes">Svezzamento</dhv:label>
    </td>
    <td>
     <input type = "checkbox" disabled="disabled"  <%if (OrgDetails.getFlag_svezzamento()){ %>checked="checked"<%} %>> SI
     <input type = "checkbox" disabled="disabled"  <%if (!OrgDetails.getFlag_svezzamento()){ %>checked="checked"<%} %>> NO
    </td>
  </tr>
</table>
<br>
<%} %>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Stato Sanitario</dhv:label></strong>
	  </th>
  </tr>
  <%
  
  ArrayList<InformazioniSanitarie> lista = OrgDetails.getListaInformazioniSanitarie();
  if(lista.size()>0)
  {
  for (InformazioniSanitarie Inf : lista)
  {
	  
	  %>
	  
	  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti..Notes"><%=Inf.getDescrizioneCodiceMalattia()%></dhv:label>
    </td>
    <td>
      <%=Inf.getDescrizioneQualifica() %>&nbsp; Data Ultimo Controllo : <%=toDateString(Inf.getDataRilevazione()) %>
    </td>
  </tr>
	  
	  <%
	  
  }
  }
  else
  {
	  %>
	   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti..Notes"></dhv:label>
    </td>
    <td>
    Non Sono presenti Controlli
    </td>
  </tr>
  <%  
  }
  %>
  
</table>

<br/>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">OSM</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td nowrap>
    	<form action="Allevamenti.do?command=ModificaMiscela" method="post">
    		<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
      		<input type="radio" name="miscela" id="miscela" value="true" <%=OrgDetails.isMiscela()?("checked=\"checked\""):("")%> > Miscela
      		<br/>
      		<input type="radio" name="miscela" id="miscela" value="false" <%=!OrgDetails.isMiscela()?("checked=\"checked\""):("")%>> Non miscela
			<input type="submit" value="Modifica" onclick="loadModalWindow()">     
    	</form>
    </td>
  </tr>
</table>

<br>
<%@ include file="../allevamenti/adeguamento_schede_zoot_view.jsp" %>

<br>

<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="allevamenti-allevamenti-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='Allevamenti.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
<%--   <% if(OrgDetails.getDate2() == null) { %> --%>
<%--   <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>"> --%>
<%--     <dhv:permission name="allevamenti-allevamenti-edit"><input type="button" value="Modifica"	onClick="javascript:window.location.href='Allevamenti.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission> --%>
<%--   </dhv:evaluate> --%>
<%-- <% } %> --%>
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="allevamenti-allevamenti-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='Allevamenti.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="allevamenti-allevamenti-delete"><input type="button" value="<dhv:label name="allevamenti.allevamenti_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('Allevamenti.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Allevamenti.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
</dhv:evaluate>
</dhv:container>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
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
font-family:verdana;
font-size:15px;
}

a {color:#333; 
/*text-decoration:none}
a:hover {color:#ccc; text-decoration:none*/
}

#mask {
  position:absolute;
  left:0;
  top:0;
  z-index:9000;
  background-color:#000;
  display:none;
}
  
#boxes .window {
  position:absolute;
  left:0;
  top:0;
  width:675px;
  height:358;
  display:none;
  z-index:9999;
  padding:20px;
}

#boxes #dialog {
  width:675px; 
  height:380;
  padding:10px;
  background-color:#ffffff;
}

#boxes #dialog1 {
  width:375px; 
  height:203px;
}

#dialog1 .d-header {
  background:url(images/login-header.png) no-repeat 0 0 transparent; 
  width:375px; 
  height:150px;
}

#dialog1 .d-header input {
  position:relative;
  top:60px;
  left:100px;
  border:3px solid #cccccc;
  height:22px;
  width:200px;
  font-size:15px;
  padding:5px;
  margin-top:4px;
}

#dialog1 .d-blank {
  float:left;
  background:url(images/login-blank.png) no-repeat 0 0 transparent; 
  width:267px; 
  height:53px;
}

#dialog1 .d-login {
  float:left;
  width:108px; 
  height:53px;
}

#boxes #dialog2 {
  background:url(images/notice.png) no-repeat 0 0 transparent; 
  width:326px; 
  height:229px;
  padding:50px 0 20px 25px;
}
</style>

<div id="boxes">

<%-- IL CAMPO SRC è DA AGGIUSTARE --%>

<%
if (OrgDetails.getSpecieAllev() != null && !OrgDetails.getSpecieAllev().equalsIgnoreCase("pesci"))
{
%>
<iframe id="dialog" class="window" src="LookupSelector.do?command=ElencoCapi&richiesto_report=NO&codice_azienda=<%=OrgDetails.getAccountNumber()%>&id_allevamento=<%=OrgDetails.getId_allevamento()%>" width="500" height="280">
  
  <a href="#"class="close"/>Close it</a>
</iframe>
<%} %>
<!-- Mask to cover the whole screen -->
  <div id="mask"></div>

</div>

<%
//associazione OSM - azienda zootecnica
Boolean ricercaOsmAssociabiliAttribute = (Boolean)request.getAttribute("ricercaOsmAssociabiliAttribute");
if (ricercaOsmAssociabiliAttribute != null && ricercaOsmAssociabiliAttribute == true) {
	
%>
	<script>confermaRicercaOsmDaAllevamento();</script>
<%
}
%>

<%
// associazione OSM - azienda zootecnica
String messaggio = ((String)request.getAttribute("messaggioAssociazioneAziendaZootecnicaEffettuata"));
request.removeAttribute("messaggioAssociazioneAziendaZootecnicaEffettuata");
if(messaggio != null && !messaggio.equals("")) {
%>
	<script>alert('<%=messaggio%>');</script>
	
<%
}

if (InfoAllevamentoBean != null && !"".equals(InfoAllevamentoBean.getCodice_errore()))
{
	
	messaggio = "Sincronizzazione Non Riuscita \n Errore : "+InfoAllevamentoBean.getErrore()+ "\n Codice : "+InfoAllevamentoBean.getCodice_errore();
	%>
	<script>
	alert('<%=messaggio%>');
	window.close();
	</script>
	
<%
}else
{

	if (InfoAllevamentoBean != null && !"".equals(InfoAllevamentoBean.getCodice_azienda()))
	{
	
		%>
		<script>
		if (window.opener!=null)
		{
	alert('Sincronizzazione Avvenuta Con Successo');
	window.opener.location.reload();
	window.close();
		}
	</script>
		<%
	}
	else
	{
		%>
		<script>
		if (window.opener!=null)
		{
	alert('Sincronizzazione Avvenuta Con Successo');
	window.opener.location.reload();
	window.close();
		}
	</script>
		<%
		
	}

}
%>





</body>