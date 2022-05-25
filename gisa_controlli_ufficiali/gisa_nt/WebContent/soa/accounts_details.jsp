<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="org.aspcfs.modules.soa.base.SottoAttivita,java.util.*,java.text.DateFormat,org.aspcfs.modules.soa.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<%@page import="java.util.Date"%>

<jsp:useBean id="statoLabImpianti"
	class="org.aspcfs.utils.web.LookupList" scope="request" />


<%@page import="org.aspcfs.modules.stabilimenti.base.StatiStabilimenti"%>
<%@page import="org.aspcfs.modules.stabilimenti.base.ControlloDocumentale"%>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.soa.base.Organization" scope="request" />
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request" />
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statiStabilimenti" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupProdotti" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="categoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="imballataList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tipoAutorizzazioneList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request" />
<jsp:useBean id="elencoSottoAttivita" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="impreseAssociateMercatoIttico" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="stabilimentiAssociateMercatoIttico" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="ControlloDocumentale" class="org.aspcfs.modules.stabilimenti.base.ControlloDocumentale" scope="request"></jsp:useBean>
<script>
function openPopupModulesPdf(orgId){
	var res;
	var result;
		window.open('ManagePdfModules.do?command=PrintSelectedModules&orgId='+orgId,'popupSelect',
		'height=300px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
}
</script>

<%@ include file="../initPage.jsp"%>
<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td><a href="Soa.do"><dhv:label
				name="stabilimenti.stabilimenti">Accounts</dhv:label></a> > <%
			if (request.getParameter("return") == null) {
			%> <a href="Soa.do?command=Search"><dhv:label
				name="stabilimenti.SearchResults">Search Results</dhv:label></a> > <%
			} else if (request.getParameter("return").equals("dashboard")) {
			%> <a href="Soa.do?command=Dashboard"><dhv:label
				name="communications.campaign.Dashboard">Dashboard</dhv:label></a> > <%
			}
			%> <dhv:label name="stabilimenti.details">Account Details</dhv:label></td>
		</tr>
	</table>

</dhv:evaluate>


<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %>
    <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	

<% 
java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>
<dhv:permission name="stabilimenti-stabilimenti-report-view">
	<table width="100%" border="0">
		<tr>
				<td nowrap align="right">
				<!-- img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16" /> 
				<a href="SchedaPrint.do?command=PrintReport&file=stabilimenti&id=<%= OrgDetails.getId() %>">
				<dhv:label name="stabilimenti.osa.print">Stampa Scheda stabilimenti</dhv:label></a-->
				
				 <%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda Stabilimenti" value="Stampa Scheda Stabilimenti"		onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '-1', '-1', '-1', 'soa', 'SchedaSOA');"--%>
 		
				 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda" value="Stampa Scheda"		onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '-1', '-1', '-1', '4');">
 
 
			</td>
		</tr>
	</table>
</dhv:permission>


<%-- <dhv:permission name="stabilimento-linkprintmodules-view"> --%>
<!-- 	<div style="padding-right: 200px" align="right"><a -->
<%-- 		href="javascript:openPopupModulesPdf('<%= OrgDetails.getOrgId() %>');">Stampa --%>
<!-- 	moduli precompilati</a></div> -->
<%-- </dhv:permission> --%>

<%
				boolean  isMercatoIttico=false;
				Iterator iElencoAttivita_ = elencoSottoAttivita.iterator();
				if (iElencoAttivita_.hasNext()) {
					while (iElencoAttivita_.hasNext()) {
						SottoAttivita thisAttivita_ = (SottoAttivita) iElencoAttivita_.next();
						
						if ( (thisAttivita_.getCodice_impianto() == 15) && ( thisAttivita_.getCodice_sezione() == 8) ) {
							isMercatoIttico=true;
						}
						else
						{
							
						}
					}
				}
%>

<dhv:evaluate if="<%=isMercatoIttico%>">
	<dhv:permission name="stabilimenti-operatori-ittici-view">
		<table width="100%" border="0">
			<tr>
				<td nowrap align="right"><img src="images/tree0.gif" border="0"
					align="absmiddle" height="16" width="16" /> <a
					href="Soa.do?command=ListaOperatoriMercatoIttico&orgId=<%= OrgDetails.getId() %>">Aggiungi
				Operatore</a></td>
			</tr>
		</table>
	</dhv:permission>
</dhv:evaluate>

<%
String param1 = "orgId=" + OrgDetails.getOrgId();
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>

<%
if ((OrgDetails.getStatoLab() == 0 && OrgDetails.getStatoIstruttoria()==0) || OrgDetails.getStatoLab()==5)
{
%>
<dhv:permission name="stabilimenti-stabilimenti-add">
	<a
		href="Soa.do?command=ModifyStabilimento&account_number=<%=OrgDetails.getNumAut() %>">Aggiungi
	Nuova Linea Produttiva</a>
</dhv:permission>
<br>
<br />
<%} %>
<%

%>
<dhv:evaluate if="<%=OrgDetails.isPregresso()%>">
	<dhv:permission name="soa-soa-adddati-view">
		<a class="ovalbutton" href="Soa.do?command=CompilaPregresso&orgId=<%=OrgDetails.getOrgId() %>"><span >Aggiungi Dati</span></a>

	</dhv:permission>
</dhv:evaluate>
<br><br> 



<dhv:container
	name="<%=(OrgDetails.getStatoIstruttoria()==0) ? "soa" : "soa_controllo_doc" %>"
	selected="details" object="OrgDetails" param="<%= param1 %>"
	appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'
	hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">

	<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
	<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
		<!--
		<dhv:permission name="stabilimenti-stabilimenti-edit">
			<input type="button"
				value="Ripristina"
				onClick="javascript:window.location.href='Soa.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
		</dhv:permission>
		 -->
	</dhv:evaluate>
	<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">


		<dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
			<dhv:permission name="stabilimenti-stabilimenti-delete">
				<input type="button"
					value="<dhv:label name="stabilimenti.stabilimenti_details.DeleteAccount">Delete Account</dhv:label>"
					onClick="javascript:popURLReturn('Soa.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Soa.do?command=Search', 'Delete_account','320','200','yes','no');">
			</dhv:permission>
		</dhv:evaluate>
	</dhv:evaluate>
	<dhv:permission name="opu_import-add">
<center><font color="red"><b><%="Lo stabilimento ha linee non aggiornate." %></b></font></center>
  <div align="center">
 	 <br/><br/>
 		<%--<input type="button" class="yellowBigButton" value="AGGIORNA LINEE DI ATTIVITA' PREGRESSE DA MASTERLIST" 
 		onClick="openPopupLarge('Accounts.do?command=PrepareUpdateLineePregresse&orgId=<%=OrgDetails.getOrgId() %>&lda_prin=<%=linea_attivita_principale.getId() %>')"
 		--%>
 	<%-- onClick="loadModalWindow();window.location.href='OpuStab.do?command=PrepareUpdateLineePregresse&stabId=<%=StabilimentoDettaglio.getIdStabilimento() %>'"--%>
 	<input type="button" class="yellowBigButton"s
				value="Importa in Anagrafica stabilimenti"
			    onClick="javascript:window.location.href='OpuStab.do?command=CaricaImport&orgId=<%=OrgDetails.getOrgId()%>'">
 <br/><br/>	
 	</div>

</dhv:permission>
	<%
	String action = "Soa.do";
	if(OrgDetails.getStatoIstruttoria()==StatiStabilimenti.ISTRUTTORIA_PRELIMINARE || 
			OrgDetails.getStatoIstruttoria()==StatiStabilimenti.ISTRUTTORIA_ESISTENTE || 
			OrgDetails.getStatoIstruttoria()==StatiStabilimenti.DOCUMENTAZIONE_COMPLETATA || 
			OrgDetails.getStatoIstruttoria()==StatiStabilimenti.RICHIESTA_RICONSOCIMENTO_CONDIZIONATO ||
			OrgDetails.getStatoIstruttoria()==StatiStabilimenti.RICHIESTA_RICONSOCIMENTO_DEFINITIVO ||
			OrgDetails.getStatoIstruttoria()==StatiStabilimenti.RICONOSCIUTO_CONDIZIONATO  || 
			OrgDetails.getStatoIstruttoria()==StatiStabilimenti.RICONOSCIUTO_CONDIZIONATO_PROROGA ||
			OrgDetails.getStatoIstruttoria()==StatiStabilimenti.COMPLETATO
			)
	{
		action +="?command=Update&tipoSoa="+OrgDetails.getTipoSoa()+"&orgId="+OrgDetails.getOrgId()+"";
	}
	
	
	if(OrgDetails.getStatoIstruttoria()==StatiStabilimenti.INOLTRO_DEFINITIVO_REGIONE )
	{
		
		action +="?command=Modify&orgId="+OrgDetails.getOrgId();
	}
	if(OrgDetails.getStatoIstruttoria()==StatiStabilimenti.INOLTRO_CONDIZIONATO_REGIONE )
	{
		
		action +="?command=Modify&orgId="+OrgDetails.getOrgId()+"&condizionato=true";
	}
	if(OrgDetails.getStatoIstruttoria()==StatiStabilimenti.RICHIESTA_REVOCA )
	{
		
		action +="?command=Update&tipoSoa="+OrgDetails.getTipoSoa()+"&orgId="+OrgDetails.getOrgId()+"&revoca=true&nuovoStato=11";
	}
	
	%>

	<form name="addAccount" method="post" action="Soa.do?command=Modify">
	<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId() %>">
	<%-- STABILIMENTO IN ISTRUTTORIA PRELIMINARE 	PERMESSI DA ATTIVARE SOLO ALL'ASL --%>

	
	<dhv:evaluate if='<%= OrgDetails.getTipoSoa()==2  %>'>
	
		<dhv:evaluate if='<%= ( OrgDetails.getStatoIstruttoria() == StatiStabilimenti.INOLTRO_DEFINITIVO_REGIONE ) %>'>
		<dhv:permission name="stabilimenti-stabilimenti-genera-approvalnumber-view">
			<input type="hidden" name="tipoModifica" value="3" />
			<input type="submit"
				<%if(OrgDetails.getNumAut()!=null &&  ! "".equals(OrgDetails.getNumAut())) { %>
				value="Riconoscimento Definitivo" <%}else { %>
				value="Attribuisci Approval number" <%} %> />
		</dhv:permission>
		
		</dhv:evaluate>
		
		
		<dhv:evaluate if='<%=  OrgDetails.getStatoIstruttoria()==StatiStabilimenti.DOCUMENTAZIONE_COMPLETATA %>'>
			<dhv:permission name="stabilimenti-stabilimenti-istruttoria-condizionata-view">
				<input type="submit" value="Inoltra Pratica al settore veterinario"
					onclick="javascript : document.addAccount.action='<%=action+"&nuovoStato="+StatiStabilimenti.INOLTRO_DEFINITIVO_REGIONE%>'" />
			</dhv:permission>
		
	</dhv:evaluate> 
	</dhv:evaluate>
	
	<input type = "hidden" name = "tipoSoa" value = "<%=OrgDetails.getTipoSoa() %>">
	
	
		 <!-- MODIFICA DATI DELLA PRATICA SE NON ULTIMATA E NON INVIATA --> 
	 <dhv:evaluate if='<%= (OrgDetails.getStatoIstruttoria() == StatiStabilimenti.ISTRUTTORIA_PRELIMINARE) %>'>
		<dhv:permission name="stabilimenti-stabilimenti-edit">
			<input type="submit" value="Modifica Pratica" onclick="javascript : document.addAccount.action='<%="Soa.do?command=Modify&mode=1" %>'" />
		</dhv:permission>
	</dhv:evaluate> 
	
	<dhv:permission name="stabilimenti-stabilimenti-stato-edit">
		<%if (OrgDetails.getStatoLab()==0 || OrgDetails.getStatoLab()==1 || OrgDetails.getStatoLab()==2 || OrgDetails.getStatoLab()==5){%>
		
		<input type="submit" value="Modifica Stato Impianti"
			onclick="javascript : document.addAccount.action='<%="Soa.do?command=Modify" %>'" />
		<%} %>
	</dhv:permission> 
	<dhv:evaluate if='<%= OrgDetails.getTipoSoa()==1  %>'>
	 
	 <dhv:evaluate if='<%= (OrgDetails.getStatoIstruttoria() == StatiStabilimenti.DOCUMENTAZIONE_COMPLETATA  || OrgDetails.getStatoIstruttoria() == StatiStabilimenti.COMPLETATO  )   %>'>
		<dhv:permission
			name="stabilimenti-stabilimenti-istruttoria-condizionata-view">
			<input type="submit" value="Invia per Riconoscimento Condizionato"
				onclick="javascript : document.addAccount.action='<%=action+"&nuovoStato=4" %>'" />
		</dhv:permission>

	</dhv:evaluate> 
	
	<dhv:evaluate if='<%=  OrgDetails.getStatoIstruttoria()==StatiStabilimenti.RICHIESTA_RICONSOCIMENTO_CONDIZIONATO %>'>
		<dhv:evaluate if='<%= ControlloDocumentale.getStatoStap()==1 %>'>
			<dhv:permission
				name="stabilimenti-stabilimenti-istruttoria-regione-view">
				<input type="submit" value="Inoltra Pratica al settore veterinario"
					onclick="javascript : document.addAccount.action='<%=action+"&nuovoStato=6"%>'" />
			</dhv:permission>
		</dhv:evaluate>
	</dhv:evaluate> 
	
	<dhv:evaluate if='<%= (OrgDetails.getStatoIstruttoria() == StatiStabilimenti.INOLTRO_CONDIZIONATO_REGIONE) %>'>
		<dhv:permission name="stabilimenti-stabilimenti-istruttoria-regione-attribuisci-numero-condizionato-view">
			<input type="submit" value="Attribuisci Approval Number Condizionato" onclick="javascript : document.addAccount.action='<%=action %>'" />
		</dhv:permission>
	</dhv:evaluate> 
	
	

	
	<dhv:evaluate if='<%= (OrgDetails.getStatoIstruttoria() == StatiStabilimenti.RICHIESTA_RICONSOCIMENTO_DEFINITIVO ) %>'>
		<dhv:permission name="stabilimenti-stabilimenti-inoltro-regione-view">
			<input type="hidden" name="tipoModifica" value="3" />
			<%if (ControlloDocumentale.getStatoStap()==1 || (OrgDetails.getStatoLab()==0 && OrgDetails.getStatoIstruttoria() == StatiStabilimenti.ISTRUTTORIA_ESISTENTE)){ %>
			<input type="submit" value="Inoltra Pratica al settore veterinario"
				onclick="javascript : document.addAccount.action='<%=action %>&&nuovoStato=<%=StatiStabilimenti.INOLTRO_DEFINITIVO_REGIONE %>'" />
			<%}
			else
			{
				%>
				Completare la pratica inserendo il controllo Documentale
				<%
			}
			%>
		</dhv:permission>

	</dhv:evaluate> 
	</dhv:evaluate>
	
	<%
	boolean dataisnull = false ;
	if(elencoSottoAttivita.size()>0 && elencoSottoAttivita.get(0)!=null )
	{
		Iterator it2 = elencoSottoAttivita.iterator();
		while (it2.hasNext())
		{
			SottoAttivita sa = (SottoAttivita) it2.next();	
			if (sa.getData_inizio_attivita()==null)
			{
				dataisnull = true ;
				break ;
			}
		}
	}
		
	 %> <%if (OrgDetails.getStatoIstruttoria()==StatiStabilimenti.ISTRUTTORIA_PRELIMINARE || OrgDetails.getStatoIstruttoria()==StatiStabilimenti.ISTRUTTORIA_ESISTENTE )
	{
%> <b>Nota:</b> Il passo successivo e' l'inserimento del controllo
	documentale. <br>
	<%} %>
	<div id="tab1" class="tab">


<% if (1==1) { %>

<input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>">
<input type="hidden" name="statoLab" value="<%=OrgDetails.getStatoLab()%>">

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

<jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="4" />
     </jsp:include>
 <% } else { %>    
     
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="stabilimenti.stabilimenti_details.PrimaryInformation">Primary Information</dhv:label></strong>
			</th>
		</tr>


		<dhv:evaluate if="<%= SiteList.size() > 1 %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label
					name="stabilimenti.site">Site</dhv:label></td>
				<td><%=SiteList.getSelectedValue(OrgDetails.getSiteId())%> <input
					type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>">
				</td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= SiteList.size() <= 1 %>">
			<input type="hidden" name="siteId" id="siteId" value="-1" />
		</dhv:evaluate>


		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
				name="stabilimenti.stabilimenti_add.OrganizationName">Organization Name</dhv:label>
			</td>
			<td><%=toHtmlValue(OrgDetails.getName())%>&nbsp;</td>
		</tr>

 	<% 
      	String lotto="";
      	if(OrgDetails.getTipo_stab() != null && !OrgDetails.getTipo_stab().equals("")){
      		lotto = "-"+OrgDetails.getTipo_stab();
      	}	 
      %>
      
		<dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label
					name="organization.accountNumber">Account Number</dhv:label></td>
				<td><%=toHtml(OrgDetails.getAccountNumber())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate if="<%= hasText(OrgDetails.getNumAut())%>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Approval Number</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getNumAut()+lotto)%></td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= hasText(OrgDetails.getCategoria()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Categoria</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getCategoria())%></td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate if="<%= hasText(OrgDetails.getPartitaIva()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">Partita IVA
				</td>
				<td><%=toHtml(OrgDetails.getPartitaIva())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscale()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">Codice Fiscale
				</td>
				<td><%=toHtml(OrgDetails.getCodiceFiscale())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>



		<dhv:include name="organization.alert" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getAlertText()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.AlertDescription">Alert Description</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getAlertText())%></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:include name="organization.directBill" none="true">
			<dhv:evaluate if="<%= OrgDetails.getDirectBill() %>">
				<tr class="containerBody">
					<td nowrap class="formLabel">Direct Bill</td>
					<td><input type="checkbox" name="directBill" CHECKED DISABLED /></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:evaluate if="<%= OrgDetails.getStatoLab() !=  -1 %>">
			<tr class="containerBody">
				<td name="statoLab1" id="statoLab1" nowrap class="formLabel"><dhv:label
					name="">Stato Stabilimento</dhv:label></td>
				<td><%=statoLab.getSelectedValue(OrgDetails.getStatoLab())%> <input
					type="hidden" name="statoLab" value="<%=OrgDetails.getStatoLab()%>"></td>
			</tr>
		</dhv:evaluate>

		<% if(Audit!=null){ %>

		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label
				name="osaa.livelloRischio">Categoria di Rischio</dhv:label></td>
			<td><%= (((OrgDetails.getCategoriaRischio()>0))?(OrgDetails.getCategoriaRischio()):("3"))%>
			</td>
		</tr>



		<%}%>

		<dhv:include name="organization.date1" none="true">
			<dhv:evaluate if="<%= (OrgDetails.getDate1() != null) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.date1">Date1</dhv:label></td>
					<td><zeroio:tz timestamp="<%= OrgDetails.getDate1() %>"
						dateOnly="true" showTimeZone="false" default="&nbsp;" /> <%=showAttribute(request, "contractEndDateError")%>
					</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:include name="organization.rating" none="true">
			<dhv:evaluate if="<%= OrgDetails.getRating() != -1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="sales.rating">Rating</dhv:label>
					</td>
					<td><%=RatingList.getSelectedValue(OrgDetails.getRating())%></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:evaluate if="<%= (OrgDetails.getDate2() != null) %>">

			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Data presentazione istanza</dhv:label>
				</td>
				<td><zeroio:tz timestamp="<%= OrgDetails.getDate2() %>"
					dateOnly="true" showTimeZone="false" default="&nbsp;" /></td>
			</tr>
		</dhv:evaluate>


		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label name="">Stato Pratica</dhv:label>
			</td>
			<td><%=statiStabilimenti.getSelectedValue(OrgDetails.getStatoIstruttoria()) %>
			<%
			
				if (OrgDetails.getStatoIstruttoria()==7)
				{
					Timestamp data_assegnazione = OrgDetails.getDataScadenzaNumero();
					if(data_assegnazione!=null)
					{
					data_assegnazione.setMonth(data_assegnazione.getMonth()+3);
					out.println("Scadenza Pratica : "+sdf.format(new Date(data_assegnazione.getTime())));
					}
				}
				if (OrgDetails.getStatoIstruttoria()==9)
				{
					Timestamp data_assegnazione = OrgDetails.getDataScadenzaNumero();
					if(data_assegnazione!=null)
					{
					
					data_assegnazione.setMonth(data_assegnazione.getMonth()+6);
					out.print("Scadenza Pratica : "+sdf.format(new Date(data_assegnazione.getTime())));
					}
				}
				%>
			</td>
		</tr>



		<dhv:include name="organization.contractEndDate" none="true">
			<dhv:evaluate
				if="<%= hasText(OrgDetails.getContractEndDateString()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.ContractEndDate">Contract End Date</dhv:label>
					</td>
					<td><zeroio:tz
						timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true"
						timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>"
						showTimeZone="false" default="&nbsp;" /></td>
				</tr>
			</dhv:evaluate>


		</dhv:include>

	</table>
	<br>
	<br>
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label name="">Indirizzo</dhv:label></strong></th>
		</tr>
		<%
				Iterator iaddress = OrgDetails.getAddressList().iterator();
				if (iaddress.hasNext()) {
					while (iaddress.hasNext()) {
						OrganizationAddress thisAddress = (OrganizationAddress) iaddress
						.next();
			%>
		<tr class="containerBody">
			<td nowrap class="formLabel" valign="top"><%=(thisAddress.getType()==1)?"Sede Legale-Domicio Fiscale" : toHtml(thisAddress.getTypeName())%></td>
			<td><%=toHtml(thisAddress.toString())%>&nbsp;<br /><%=thisAddress.getGmapLink() %>
			<dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">
				<dhv:label name="account.primary.brackets">(Primary)</dhv:label>
			</dhv:evaluate></td>
		</tr>
		<%
				}
				} else {
			%>
		<tr class="containerBody">
			<td colspan="2"><font color="#9E9E9E"><dhv:label
				name="contacts.NoAddresses">No addresses entered.</dhv:label></font></td>
		</tr>
		<%
				}
				%>
	</table>
	</div>
	<br />
	<br />


	<%
			if(OrgDetails.getNomeRappresentante()!=null && ! "".equals(OrgDetails.getNomeRappresentante())) 
			{
				%>
	<div id="tab2" class="tab">

	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>

			</th>
		</tr>

		<dhv:evaluate
			if="<%= (hasText(OrgDetails.getCodiceFiscaleRappresentante())) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">Codice Fiscale
				</td>
				<td><%= toHtml((OrgDetails.getCodiceFiscaleRappresentante()) )%>&nbsp;
				</td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate
			if="<%= (hasText(OrgDetails.getNomeRappresentante())) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">Nome
				</td>
				<td><%= toHtml((OrgDetails.getNomeRappresentante())) %>&nbsp;</td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate
			if="<%= (hasText(OrgDetails.getCognomeRappresentante())) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">Cognome
				</td>
				<td><%= toHtml((OrgDetails.getCognomeRappresentante())) %>&nbsp;
				</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate
			if="<%= (OrgDetails.getDataNascitaRappresentante() != null)  %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Data Nascita</dhv:label>
				</td>
				<td><%= ((OrgDetails.getDataNascitaRappresentante()!=null)?(toHtml(DateUtils.getDateAsString(OrgDetails.getDataNascitaRappresentante(),Locale.ITALY))):("")) %>
				</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate
			if="<%= (hasText(OrgDetails.getLuogoNascitaRappresentante())) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Comune di nascita</dhv:label>
				</td>
				<td><%= toHtml(OrgDetails.getLuogoNascitaRappresentante())%>&nbsp;
				</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate if="<%= (hasText(OrgDetails.getCity_legale_rapp())) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Comune di Residenza</dhv:label>
				</td>
				<td><%= toHtml(OrgDetails.getCity_legale_rapp())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate if="<%= (hasText(OrgDetails.getProv_legale_rapp())) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Provincia</dhv:label>
				</td>
				<td><%= toHtml(OrgDetails.getProv_legale_rapp())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate
			if="<%= (hasText(OrgDetails.getAddress_legale_rapp())) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Indirizzo</dhv:label>
				</td>
				<td><%= toHtml(OrgDetails.getAddress_legale_rapp())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate
			if="<%= (hasText(OrgDetails.getEmailRappresentante())&& (!OrgDetails.getEmailRappresentante().equals("-1"))) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Domicilio digitale</dhv:label>
				</td>
				<td><%= toHtml(OrgDetails.getEmailRappresentante()) %>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate
			if="<%= (hasText(OrgDetails.getTelefonoRappresentante()) && (!OrgDetails.getTelefonoRappresentante().equals("-1"))) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Telefono</dhv:label>
				</td>
				<td><%= toHtml(OrgDetails.getTelefonoRappresentante()) %>&nbsp;
				</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate
			if="<%= (hasText(OrgDetails.getFax())&& (!OrgDetails.getFax().equals("-1"))) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Fax</dhv:label>
				</td>
				<td><%= toHtml(OrgDetails.getFax()) %>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<!--  fine delle modifiche -->

	</table>
	</div>
	<br>
	
	<%  } %>
	<%
				
			}
		%> <br>
	<div id="tab3" class="tab">

	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="11"><strong><dhv:label name="">Linee Produttive</dhv:label></strong></th>
		</tr>
		<%
				Iterator iElencoAttivita = elencoSottoAttivita.iterator();
				if (iElencoAttivita.hasNext()) {
			%>
		<tr class="formLabel">
			<td align="left">Categoria/sezione</td>
			<td align="left">Impianto/Attivit&agrave;</td>
			<td align="left">Prodotti</td>
			<td align="left">Data Inizio</td>
			<td align="left">Data Fine</td>
			<td align="left">Descrizione Stato Attivita'</td>
			<td align="left">Autorizzazione</td>
			<td align="left">Produzione con Riti Religiosi</td>
			<td align="left">Prodotti Imballati</td>
			<td align="left">Prodotti non Imballati</td>
			<td>&nbsp;</td>



		</tr>
		<%
					while (iElencoAttivita.hasNext()) {
						SottoAttivita thisAttivita = (SottoAttivita) iElencoAttivita.next();
			%>
		<tr class="containerBody">
			<td><%= categoriaList.getSelectedValue( thisAttivita.getCodice_sezione() ) %>&nbsp;
			</td>
			<td><%=toHtml(thisAttivita.getAttivita())%>&nbsp;</td>
			<td>
			<%
      					if (thisAttivita.getListaProdotti().isEmpty())
      					{
      						out.println("-");
      					}
      					else
      					for(Integer idProdotto : thisAttivita.getListaProdotti())
      					{
      						out.println(" - "+	LookupProdotti.getSelectedValue( idProdotto)+"<br/>");
      					%> <%	
      					
      					}
      					%> &nbsp;
      		</td>
      		
      		<td><zeroio:tz
				timestamp="<%= thisAttivita.getData_inizio_attivita()%>"
				dateOnly="true" showTimeZone="false" default="&nbsp;" />
				</td>
			<td><zeroio:tz
				timestamp="<%= thisAttivita.getData_fine_attivita()%>"
				dateOnly="true" showTimeZone="false" default="&nbsp;" /></td>


			<td>
			<div title="<%=(statoLabImpianti.getSelectedValue(thisAttivita.getStato_attivita()).equalsIgnoreCase("sospeso"))? "SOSPESO DAL "+thisAttivita.getData_inizio_sospensioneAsString() :"" %>">
			<%=toHtml(statoLabImpianti.getSelectedValue(thisAttivita.getStato_attivita()))%>&nbsp;</div>
			<%
        				if(thisAttivita.getStato_attivita()==Organization.STATO_IMPIANTO_IN_RICONOSCIUTO_CONDIZIONATO)
        				{
        					
        					if(thisAttivita.getData_inizio_attivita()!=null)
        					{
        						
        						Timestamp data_assegnazione  = thisAttivita.getData_inizio_attivita() ;	
        						thisAttivita.getData_inizio_attivita().setMonth(thisAttivita.getData_inizio_attivita().getMonth()+3);
        					out.println("Scadenza Pratica : "+sdf.format(new Date(thisAttivita.getData_inizio_attivita().getTime())));
        					thisAttivita.setData_inizio_attivita(data_assegnazione);
        					
        					}
        					
        				}
        			
        			if(thisAttivita.getStato_attivita()==Organization.STATO_IMPIANTO_PROROGA_RIC_CONDIZIONATO)
    				{
    					
    					if(thisAttivita.getData_inizio_attivita()!=null)
    					{
    						Timestamp data_assegnazione  = thisAttivita.getData_inizio_attivita() ;	
    						thisAttivita.getData_inizio_attivita().setMonth(thisAttivita.getData_inizio_attivita().getMonth()+6);
    					out.println("Scadenza Pratica : "+sdf.format(new Date(thisAttivita.getData_inizio_attivita().getTime())));
    					thisAttivita.setData_inizio_attivita(data_assegnazione);
    					
    					}
    					
    				}
        			%>
			</td>
			

			<td><%=(thisAttivita.getTipo_autorizzazione()==-1) ? "-" : tipoAutorizzazioneList.getSelectedValue( thisAttivita.getTipo_autorizzazione() ) %>&nbsp;
			</td>

			<td><input type="checkbox"
				<%if(thisAttivita.isRiti_religiosi()) {%> checked="checked" <% }%>
				disabled="disabled"> &nbsp;</td>

			<td><input type="checkbox"
				<%if(thisAttivita.getImballata()==1) {%> checked="checked" <% }%>
				disabled="disabled"> &nbsp;</td>

			<td><input type="checkbox"
				<%if(thisAttivita.getNon_imballata()==1) {%> checked="checked"
				<% }%> disabled="disabled"> &nbsp;</td>
			<td>
			
			<dhv:evaluate if='<%= OrgDetails.getTipoSoa()==1  %>'>
			<dhv:permission name="stabilimenti-stabilimenti-istruttoria-definitiva-view">
				<dhv:evaluate if='<%= thisAttivita.getStato_attivita()==Organization.STATO_IMPIANTO_IN_RICONOSCIUTO_CONDIZIONATO %>'>
						<input type="submit" value="Invia per Riconoscimento Definitivo" onclick="javascript : document.addAccount.action='<%="Soa.do?command=Update&tipoSoa="+OrgDetails.getTipoSoa()+"&nuovoStato=11&idImpianto="+thisAttivita.getId() %>'" />
				</dhv:evaluate>
			</dhv:permission> 
			
			<dhv:evaluate if='<%=  thisAttivita.getStato_attivita()==Organization.STATO_IMPIANTO_IN_RICONOSCIUTO_CONDIZIONATO %>'>
				<dhv:permission name="stabilimenti-stabilimenti-istruttoria-condizionata-view">
					<input type="submit" value="Proroga" onclick="javascript : document.addAccount.action='<%="Soa.do?command=Update&tipoSoa="+OrgDetails.getTipoSoa()+"&nuovoStato=9&idImpianto="+thisAttivita.getId() %>'" />
				</dhv:permission>
			</dhv:evaluate> 
			
			<dhv:evaluate if='<%=  thisAttivita.getStato_attivita()==Organization.STATO_IMPIANTO_PROROGA_RIC_CONDIZIONATO %>'>
				<dhv:permission name="stabilimenti-stabilimenti-istruttoria-condizionata-view">
					<input type="submit" value="Invia Richiesta Revoca" onclick="javascript : document.addAccount.action='<%="Soa.do?command=Update&tipoSoa="+OrgDetails.getTipoSoa()+"&nuovoStato=10&idImpianto="+thisAttivita.getId() %>'" />
				</dhv:permission>
				
				<dhv:permission name="stabilimenti-stabilimenti-istruttoria-definitiva-view">
					<input type="submit" value="Invia per Riconoscimento Definitivo" onclick="javascript : document.addAccount.action='<%="Soa.do?command=Update&tipoSoa="+OrgDetails.getTipoSoa()+"&nuovoStato=11&idImpianto="+thisAttivita.getId() %>'" />
				</dhv:permission>
			</dhv:evaluate> 
			
			
			
			
			<dhv:evaluate if='<%=  thisAttivita.getStato_attivita()==Organization.STATO_IMPIANTO_RICHIESTA_RIC_DEFINITIVO %>'>
				<dhv:permission name="stabilimenti-stabilimenti-istruttoria-regione-view">
					<input type="submit" value="Inoltra Pratica Al Servizio Veterinario" onclick="javascript : document.addAccount.action='<%="Soa.do?command=Update&tipoSoa="+OrgDetails.getTipoSoa()+"&nuovoStato=12&idImpianto="+thisAttivita.getId() %>'" />
				</dhv:permission>
			</dhv:evaluate> 
			
			<dhv:evaluate if='<%= (thisAttivita.getStato_attivita()==Organization.STATO_IMPIANTO_RICHIESTA_REVOCA) %>'>
				<dhv:permission name="stabilimenti-stabilimenti-istruttoria-regione-revoca-condizionato-view">
					<input type="submit" value="Revoca" onclick="javascript : document.addAccount.action='<%="Soa.do?command=Update&tipoSoa="+OrgDetails.getTipoSoa() +"&nuovoStato=1&idImpianto="+OrgDetails.getTipoSoa()+thisAttivita.getId() %>'" />
				</dhv:permission>
			</dhv:evaluate> 
			
			<dhv:evaluate if='<%= (thisAttivita.getStato_attivita()==Organization.STATO_IMPIANTO_INOLTRO_RIC_DEFINITIVO) %>'>
				<dhv:permission name="stabilimenti-stabilimenti-istruttoria-regione-revoca-condizionato-view">
					<input type="submit" value="Approva Riconoscimento" onclick="javascript : document.addAccount.action='<%="Soa.do?command=Update&tipoSoa="+OrgDetails.getTipoSoa() +"&nuovoStato=0&idImpianto="+thisAttivita.getId() %>'" />
				</dhv:permission>
			</dhv:evaluate>
			</dhv:evaluate>
			
			
			<dhv:evaluate if='<%= OrgDetails.getTipoSoa()==2  %>'>
			
			
			</dhv:evaluate>
			
			</td>







		</tr>
		<%
					}
				} else {
			%>
		<tr class="containerBody">
			<td colspan="2"><font color="#9E9E9E"><dhv:label name="">Attività non inserite.</dhv:label></font>
			</td>
		</tr>
		<%
				}
			%>
	</table>
	</div>
	<br>
</form>
</dhv:container>

<script type="text/javascript">
var tabber=new Yetii('tabcont1',1);
tabber.init();
</script>