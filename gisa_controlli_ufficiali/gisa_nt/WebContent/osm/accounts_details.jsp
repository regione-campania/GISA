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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="org.aspcfs.modules.osm.base.SottoAttivita,java.util.*,java.text.DateFormat,org.aspcfs.modules.osm.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="OrgCategoriaRischioList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.osm.base.Organization"
	scope="request" />
<jsp:useBean id="SICCodeList"
	class="org.aspcfs.modules.admin.base.SICCodeList" scope="request" />
<jsp:useBean id="applicationPrefs"
	class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
	<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
	
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="categoriaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="imballataList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="tipoAutorizzazioneList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request" />
<jsp:useBean id="elencoSottoAttivita" class="java.util.ArrayList"
	scope="request" />
<jsp:useBean id="AddressSedeOperativa" class="org.aspcfs.modules.osm.base.OrganizationAddress" scope="request"/>
	<jsp:useBean id="Messaggio" class="java.lang.String" scope="request"/>
	
<%@ include file="../initPage.jsp"%>
<!-- script language="javascript">
    document.onkeydown = f5() ;
</script-->

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%if (refreshUrl!=null && !"".equals(refreshUrl)) { %>
<script language="JavaScript" TYPE="text/javascript">
	parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>



<%
}
%>

<% if (Messaggio!=null && !Messaggio.equalsIgnoreCase("null") && !Messaggio.equals("")) {%>
<script>
alert('<%=Messaggio%>');
</script>
<% } %>

<script>

function openPopupLarge(url){
	
	  var res;
    var result;
    	  window.open(url,'popupSelect',
          'height=600px,width=1000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}		
	
function openPopupModulesPdf(orgId){
	var res;
	var result;
		window.open('ManagePdfModules.do?command=PrintSelectedModules&orgId='+orgId,'popupSelect',
		'height=300px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		} 
</script>


<%

%>


<style>
	
	.dropbtn {
	}

	.dropdown {
	  position: relative;
	  display: inline-block;
	}
	
	.dropdown-content {
	  display: none;
	  position: absolute;
	  border-style: solid;
  	  border-width: 1px;
	  background-color: #E8E8E8;
	  overflow: auto;
	  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
	  z-index: 1;
	}
	
	.dropdown-content a {
	  display: block;
	  padding: 6px 16px;
	  
	}
	
	
	.show {display: block;}
	
	/* Change color of dropdown links on hover */
	.dropdown-content a:hover {background-color: #D0D0D0}

</style>


<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td><a href="Osm.do"><dhv:label
				name="osm.osm">Accounts</dhv:label></a> > <%
			if (request.getParameter("return") == null) {
			%>
			<a href="Osm.do?command=Search"><dhv:label
				name="stabilimenti.SearchResults">Search Results</dhv:label></a> > <%
			} else if (request.getParameter("return").equals("dashboard")) {
			%>
			<a href="Osm.do?command=Dashboard"><dhv:label
				name="communications.campaign.Dashboard">Dashboard</dhv:label></a> > <%
			}
			%>
			<dhv:label name="osm.details">Account Details</dhv:label></td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>

<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %>
     <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
<dhv:permission name="">
	<table width="100%" border="0">
		<tr>
			<%-- aggiunto da d.dauria--%>

			<td nowrap align="right">
					
					
 		  <%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda OSM" value="Stampa Scheda OSM"		onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '-1', '-1', '-1', 'osm', 'SchedaOSM');"--%>
 
				<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda" value="Stampa Scheda"		onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '-1', '-1', '-1', '17');">
 
				
			</td>


			<%-- fine degli inserimenti --%>
		</tr>
	</table>
</dhv:permission>


<% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>
<dhv:permission name="osm-osm-report-view">
	<table width="100%" border="0">
		<tr>
			<%-- aggiunto da d.dauria--%>

			<td nowrap align="right"><img
				src="images/icons/stock_print-16.gif" border="0" align="absmiddle"
				height="16" width="16" /> <a
				href="Osm.do?command=PrintReport&file=stabilimenti.xml&id=<%= OrgDetails.getId() %>"><dhv:label
				name="stabilimenti.osa.print">Stampa Scheda stabilimenti</dhv:label></a>
			</td>

			<%-- fine degli inserimenti --%>
		</tr>
	</table>
</dhv:permission>

<!--<dhv:permission name="osm-linkprintmodules-view">
<div style="padding-right: 200px" align="right">
	<a href="javascript:openPopupModulesPdf('<%= OrgDetails.getOrgId() %>');">Stampa moduli precompilati</a>
</div>
</dhv:permission>-->


<%
String param1 = "orgId=" + OrgDetails.getOrgId();
%>
<dhv:container name="osm" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">
	
	<dhv:permission name="opu_import-add">

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


<dhv:permission name="gestioneanagrafica-import-view">
<div class="dropdown">
	<input type="button" onclick="mostraListaOperazioni('dropdownModifica')" value="Modifica dati scheda" class="dropbtn" style="width:250px"/>
	<div id="dropdownModifica" class="dropdown-content" style="width:250px">
		<br/>
		<a href="GestioneAnagraficaAction.do?command=Import&altId=<%=OrgDetails.getOrgId()%>">Completa scheda anagrafica</a>
	</div>
</div>

</dhv:permission>

</dhv:permission>
	<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
	<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
		<!--<dhv:permission name="osm-osm-edit">
			<input type="button"
				value="Ripristina"
				onClick="javascript:window.location.href='Osm.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
		</dhv:permission>-->
	</dhv:evaluate>
	<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
	
		<dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
		<%--((OrgDetails.getStatoLab() != 1) && (OrgDetails.getStatoLab()!=2)){ --%>
			<%-- <dhv:permission name="osm-osm-edit">
				<input type="button"
					value="Modifica"
					onClick="javascript:window.location.href='Osm.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';">
			</dhv:permission>--%>
			<%--} --%>
		</dhv:evaluate>

		 <script language="JavaScript" TYPE="text/javascript">
function enable() {
var b = document.getElementById("modB");
var c = document.getElementById("modC");
b.disabled=false;
c.disabled=false;
}
</script>
<%-- questa parte di codice deve essere decommentata dopo l'inserimento del pregresso --%>
<%if(User.getContact().getState()!=null){ %>
<%if(User.getContact().getState().equalsIgnoreCase(AddressSedeOperativa.getState())){ 
%>
	
    <% if (!OrgDetails.getAccountNumber().equals("")){ %>
 	<dhv:permission name="osm-osm-generaosa-view">

 		<input disabled="disabled" title= "Genera il Numero di Riconoscimento" type="button" name="Genera Numero Riconoscimento" value="Genera Numero Riconoscimento"/>

    </dhv:permission>
    
		
    <%} else if( OrgDetails.getAccountNumber().equals("") ) { %>
		<dhv:permission name="osm-osm-generaosa-view">

			<input type="button" title= "Genera il Numero di Riconoscimento" value="Genera Numero Riconoscimento" name="Genera Numero Riconoscimento"	onClick="javascript:window.location.href='Osm.do?command=GeneraCodiceOsa&orgId=<%= OrgDetails.getOrgId()%>';">

 		</dhv:permission>
		<%} %>
		<%} }%>
		
	</dhv:evaluate>
	
<body >


 <dhv:permission name="osm-osm-delete">
    <input type="button" value="Elimina" onClick="javascript:popURLReturn('Osm.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Osm.do?command=Search', 'Delete_account','320','200','yes','no');">
    </dhv:permission>
    
    
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

<dhv:permission name="osm-invio-view"> 
<center>
<input style="width:250px" type="button" value="GESTIONE INVIO OSM" onclick="openPopupLarge('GestioneOSM.do?command=Details&riferimentoId=<%=OrgDetails.getOrgId()%>&riferimentoIdNomeTab=organization')"/>
</center>
</dhv:permission>

<% if (1==1) { %>

<jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="17" />
     </jsp:include>
 <% } else {  %>    
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="stabilimenti.stabilimenti_details.PrimaryInformation">Primary Information</dhv:label></strong>
			</th>
		</tr>

		<dhv:include name="osm-sites" none="true">
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
		</dhv:include>

		<dhv:include name="osm-name" none="true">
			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
					name="stabilimenti.stabilimenti_add.OrganizationName">Organization Name</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getName())%>&nbsp;</td>
			</tr>
		</dhv:include>


		<dhv:evaluate if="<%= hasText(OrgDetails.getBanca()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Ragione Sociale Precedente</dhv:label>
				</td>
				<td><%=toHtml(OrgDetails.getBanca())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label
					name="organization.accountNumber">Account Number</dhv:label></td>
				<td>&alpha;IT<%=toHtml(OrgDetails.getAccountNumber())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<%--dhv:evaluate if="<%= hasText(OrgDetails.getNumAut())%>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Numero di Riconoscimento</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getNumAut())%></td>
			</tr>
		</dhv:evaluate--%>
		
		<dhv:evaluate if="<%= hasText(OrgDetails.getCategoria()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Categoria</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getCategoria())%></td>
			</tr>
		</dhv:evaluate>
		
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Attività</dhv:label>
				</td>
				<td>
				<%if(OrgDetails.getImpianto()>-1){%>
				<%=impianto.getSelectedValue(OrgDetails.getImpianto())%>
				<input type="hidden" name="impianto" value="<%=OrgDetails.getImpianto()%>">
				<%}else{ %>
				<%=toHtml(OrgDetails.getTaxId())%>&nbsp;
				<%} %>
				</td>
			</tr>
	
		<dhv:evaluate if="<%= hasText(OrgDetails.getLead()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Decreto</dhv:label>
				</td>
				<td><%=toHtml(OrgDetails.getLead())%>&nbsp;</td>
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
		<dhv:evaluate
			if="<%= hasText(OrgDetails.getCodiceFiscaleCorrentista()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Codice Istat Principale</dhv:label>
				</td>
				<td><%=toHtml(OrgDetails.getCodiceFiscaleCorrentista())%>&nbsp;
				</td>
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
		<dhv:include name="organization.sicDescription" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getSicDescription()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.sicDescription">SIC Description</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getSicDescription())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<%
		if (hasText(OrgDetails.getCodice1())) {
		%>
		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label name="">Codici Istat Secondari</dhv:label>
			</td>
			<td>
			<%
					if ((OrgDetails.getCodice1() != null)
					&& (!OrgDetails.getCodice1().equals(""))) {
			%>
			Codice 1&nbsp;&nbsp; <%=toHtmlValue(OrgDetails.getCodice1())%></br>
			<%
					}
					if ((OrgDetails.getCodice2() != null)
					&& (!OrgDetails.getCodice2().equals(""))) {
			%>
			Codice 2&nbsp;&nbsp; <%=toHtmlValue(OrgDetails.getCodice2())%></br>
			<%
					}
					if ((OrgDetails.getCodice3() != null)
					&& (!OrgDetails.getCodice3().equals(""))) {
			%>
			Codice 3&nbsp;&nbsp; <%=toHtmlValue(OrgDetails.getCodice3())%></br>
			<%
					}
					if ((OrgDetails.getCodice4() != null)
					&& (!OrgDetails.getCodice4().equals(""))) {
			%>
			Codice 4&nbsp;&nbsp; <%=toHtmlValue(OrgDetails.getCodice4())%></br>
			<%
					}
					if ((OrgDetails.getCodice5() != null)
					&& (!OrgDetails.getCodice5().equals(""))) {
			%>
			Codice 5&nbsp;&nbsp; <%=toHtmlValue(OrgDetails.getCodice5())%></br>
			<%
					}
					if ((OrgDetails.getCodice6() != null)
					&& (!OrgDetails.getCodice6().equals(""))) {
			%>
			Codice 6&nbsp;&nbsp; <%=toHtmlValue(OrgDetails.getCodice6())%></br>
			<%
					}
					if ((OrgDetails.getCodice7() != null)
					&& (!OrgDetails.getCodice7().equals(""))) {
			%>
			Codice 7&nbsp;&nbsp; <%=toHtmlValue(OrgDetails.getCodice7())%></br>
			<%
					}
					if ((OrgDetails.getCodice8() != null)
					&& (!OrgDetails.getCodice8().equals(""))) {
			%>
			Codice 8&nbsp;&nbsp; <%=toHtmlValue(OrgDetails.getCodice8())%></br>
			<%
					}
					if ((OrgDetails.getCodice9() != null)
					&& (!OrgDetails.getCodice9().equals(""))) {
			%>
			Codice 9&nbsp;&nbsp; <%=toHtmlValue(OrgDetails.getCodice9())%></br>
			<%
					}
					if ((OrgDetails.getCodice10() != null)
					&& (!OrgDetails.getCodice10().equals(""))) {
			%>
			Codice 10 <%=toHtmlValue(OrgDetails.getCodice10())%></br>
			<%
			}
			%>
			</td>
		</tr>
		<%
			} else {
			}
		%>
		
		 <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Categoria di Rischio</dhv:label>
      </td>
      <td>
         <% if (OrgDetails.getCategoriaRischio() == 0 || OrgDetails.getCategoriaRischio() == -1)
         {
        	 out.print("N.D.");
         }
         else
         {
        	 out.print(OrgDetails.getCategoriaRischio());
         }
         %>
      </td>
    </tr>
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Prossimo Controllo</br>con la tecnica della Sorveglianza</dhv:label>
      </td>
      <td>
      <% SimpleDateFormat dataPC = new SimpleDateFormat("dd/MM/yyyy");%>
        
         <%= (((OrgDetails.getDataProssimoControllo()!=null))?(dataPC.format(OrgDetails.getDataProssimoControllo())):(dataPC.format(d))) %>
      </td>
    </tr>
		<dhv:include name="organization.url" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getUrl()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.WebSiteURL">Web Site URL</dhv:label>
					</td>
					<td><a href="<%= toHtml(OrgDetails.getUrlString()) %>"
						target="_new"><%=toHtml(OrgDetails.getUrl())%></a>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.industry" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getIndustryName()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.Industry">Industry</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getIndustryName())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.dunsType" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getDunsType()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.duns_type">DUNS Type</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getDunsType())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.employees" none="true">
			<dhv:evaluate if="<%= (OrgDetails.getEmployees() > 0) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="organization.employees">No. of Employees</dhv:label></td>
					<td><%=OrgDetails.getEmployees()%></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.potential" none="true">
			<dhv:evaluate if="<%= (OrgDetails.getPotential() > 0) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.Potential">Potential</dhv:label>
					</td>
					<td><zeroio:currency value="<%= OrgDetails.getPotential() %>"
						code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>'
						locale="<%= User.getLocale() %>" default="&nbsp;" /></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.revenue" none="true">
			<dhv:evaluate if="<%= (OrgDetails.getRevenue() > 0) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.Revenue">Revenue</dhv:label></td>
					<td><zeroio:currency value="<%= OrgDetails.getRevenue() %>"
						code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>'
						locale="<%= User.getLocale() %>" default="&nbsp;" /></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.ticker" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getTicker()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.TickerSymbol">Ticker Symbol</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getTicker())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.dunsNumber" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getDunsNumber()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.duns_number">DUNS Number</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getDunsNumber())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.businessNameTwo" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getBusinessNameTwo()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.business_name_two">Business Name 2</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getBusinessNameTwo())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:include name="osm-size" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getAccountSizeName()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.accountSize">Account Size</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getAccountSizeName())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.segment" none="true">
			<dhv:evaluate if="<%= (OrgDetails.getSegmentId() > 0) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="osm.osm_add.segment">Segment</dhv:label></td>
					<td><%=SegmentList.getSelectedValue(OrgDetails.getSegmentId())%></td>
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

		<dhv:evaluate if="<%= hasText(OrgDetails.getContoCorrente()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Tipo Autoveicolo</dhv:label></td>
				<td><%=toHtml(OrgDetails.getContoCorrente())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= hasText(OrgDetails.getNomeCorrentista()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Targa Autoveicolo</dhv:label></td>
				<td><%=toHtml(OrgDetails.getNomeCorrentista())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<dhv:include name="organization.source" none="true">
			<dhv:evaluate if="<%= OrgDetails.getSource() != -1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="contact.source">Source</dhv:label></td>
					<td><%=SourceList.getSelectedValue(OrgDetails.getSource())%></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:include name="organization.stage" none="true">
			<dhv:evaluate if="<%= OrgDetails.getStageId() != -1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stage">Stage</dhv:label></td>
					<td><%=StageList.getSelectedValue(OrgDetails.getStageId())%></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:evaluate if="<%= OrgDetails.getStatoLab() !=  -1 %>">
			<tr class="containerBody">
				<td name="statoLab1" id="statoLab1" nowrap class="formLabel"><dhv:label
					name="">Stato OSM</dhv:label></td>
				<td><%=statoLab.getSelectedValue(OrgDetails.getStatoLab())%>
				<input type="hidden" name="statoLab"
					value="<%=OrgDetails.getStatoLab()%>">
					 <%--in data <zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>"
					dateOnly="true" showTimeZone="false" default="&nbsp;" />--%>
 </td>
			</tr>
		</dhv:evaluate>
 <%-- if(Audit!=null){ %>
  
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
      <% SimpleDateFormat dataPC = new SimpleDateFormat("dd/MM/yyyy");
       %>
         <%= (((Audit.getDataProssimoControllo()!=null))?(dataPC.format(Audit.getDataProssimoControllo())):(dataPC.format(d)))%>
      </td>
    </tr>
  <%}--%>

		<dhv:include name="organization.rating" none="true">
			<dhv:evaluate if="<%= OrgDetails.getRating() != -1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="sales.rating">Rating</dhv:label>
					</td>
					<td><%=RatingList.getSelectedValue(OrgDetails.getRating())%>
					</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:evaluate if="<%= (OrgDetails.getDate2() != null) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Data inizio attività</dhv:label>
				</td>
				<td><zeroio:tz timestamp="<%= OrgDetails.getDate2() %>"
					dateOnly="true" showTimeZone="false" default="&nbsp;" /></td>
			</tr>
		</dhv:evaluate>
	
		
		<dhv:include name="organization.contractEndDate" none="true">
			<dhv:evaluate
				if="<%= hasText(OrgDetails.getContractEndDateString()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.ContractEndDate">Contract End Date</dhv:label>
					</td>
					<td><zeroio:tz
						timestamp="<%= OrgDetails.getDate1() %>" dateOnly="true"
						showTimeZone="false" default="&nbsp;" /></td>
				</tr>
			</dhv:evaluate>
			
			<dhv:evaluate if="<%= (OrgDetails.getYearStarted() > 0) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.year_started">Year Started</dhv:label>
					</td>
					<td><%=OrgDetails.getYearStarted()%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		
		</table>
	
		<br>
		
		<%--table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  			<tr>
    			<th colspan="8"><strong><dhv:label name="">Lista Sotto Attività</dhv:label></strong></th>
  			</tr>
			<%
				Iterator iElencoAttivita = elencoSottoAttivita.iterator();
				if (iElencoAttivita.hasNext()) {
			%>
			<tr class="formLabel">
				<td width ="10%" align="left">Impianto</td>
				<td width ="20%" align="left">Categoria</td>
				<td width ="20%" align="left">Descrizione Stato Attività</td>
				<td width ="10%" align="left">Data Inizio</td>
				<td width ="10%" align="left">Data Fine</td>
				
				<td width ="10%" align="left">Autorizzazione</td>	
			</tr>
			<%
					while (iElencoAttivita.hasNext()) {
						SottoAttivita thisAttivita = (SottoAttivita) iElencoAttivita.next();
			%>    
    		<tr class="containerBody">
      			<td>
      				<%=toHtml(thisAttivita.getAttivita())%>&nbsp;
      			</td>
      			<td>
      				<%= categoriaList.getSelectedValue( thisAttivita.getCodice_sezione() ) %>&nbsp;
      			</td>
      			<td>
        			<%=toHtml(thisAttivita.getDescrizione_stato_attivita())%>&nbsp;
      			</td>
      			<td>
      				<zeroio:tz timestamp="<%= thisAttivita.getData_inizio_attivita()%>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
      			</td>
      			<td>
      				<zeroio:tz timestamp="<%= thisAttivita.getData_fine_attivita()%>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
      			</td>
      			
      			<td>
      				<%= tipoAutorizzazioneList.getSelectedValue( thisAttivita.getTipo_autorizzazione() ) %>&nbsp;
      			</td>
      			
    		</tr>
			<%
					}
				} else {
			%>
    		<tr class="containerBody">
      			<td colspan="2">
        			<font color="#9E9E9E"><dhv:label name="">Attività non inserite.</dhv:label></font>
      			</td>
    		</tr>	
			<%
				}
			%>
		</table--%>
		
		<br />

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
     
    </th>
  </tr>

  <dhv:evaluate if="<%= (hasText(OrgDetails.getCodiceFiscaleRappresentante())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Codice Fiscale
			</td>
			<td>
         	<%= toHtml((OrgDetails.getCodiceFiscaleRappresentante()) )%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= (hasText(OrgDetails.getNomeRappresentante())) %>">		
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Nome
			</td>
			<td>
         	<%= toHtml((OrgDetails.getNomeRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
  	 <dhv:evaluate if="<%= (hasText(OrgDetails.getCognomeRappresentante())) %>">
<tr class="containerBody">
			<td nowrap class="formLabel">
      			Cognome
			</td>
			<td>
         	<%= toHtml((OrgDetails.getCognomeRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>

<dhv:evaluate if="<%= (OrgDetails.getDataNascitaRappresentante() != null)  %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Nascita</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getDataNascitaRappresentante() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
  	 
		<dhv:evaluate if="<%= (hasText(OrgDetails.getLuogoNascitaRappresentante())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Luogo di Nascita
			</td>
			<td>
         	<%= toHtml(OrgDetails.getLuogoNascitaRappresentante())%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
	
	<dhv:evaluate if="<%= (hasText(OrgDetails.getEmailRappresentante())&& (!OrgDetails.getEmailRappresentante().equals("-1"))) %>">						
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Email</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getEmailRappresentante()) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
	<dhv:evaluate if="<%= (hasText(OrgDetails.getTelefonoRappresentante()) && (!OrgDetails.getTelefonoRappresentante().equals("-1"))) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Telefono</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getTelefonoRappresentante()) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= (hasText(OrgDetails.getFax())&& (!OrgDetails.getFax().equals("-1"))) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Fax</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getFax()) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<!--  fine delle modifiche -->
		
</table>		
		<br>		
<%




int cont=0;
  Iterator iaddress = OrgDetails.getAddressList().iterator();
  Object address[] = null;
  int i = 0;
  int locali=0;
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
%>  
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:evaluate if="<%= thisAddress.getType() == 1 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede legale</dhv:label></strong>
	  </dhv:evaluate>
	  <dhv:evaluate if="<%= thisAddress.getType() == 5 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede operativa</dhv:label></strong>
	  </dhv:evaluate>  
	
	  </th>
  </tr>
    	  
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <%= toHtml(thisAddress.getTypeName()) %>
      </td>
      <td>
        <%= toHtml(thisAddress.toString()) %>&nbsp;<br/><%=thisAddress.getGmapLink() %>
        <dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">        
          <dhv:label name="requestor.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate>
      </td>
      </tr>
    </table>
     <%
    
	 }} else {
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="requestor.requestor_add.Addresses">Addresses</dhv:label></strong>
	  </th>
  </tr>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E"><dhv:label name="contacts.NoAddresses">No addresses entered.</dhv:label></font>
      </td>
    </tr>
    </table>
<%}%>
		</br>
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="stabilimenti.stabilimenti_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.Notes">Note</dhv:label>
    </td>
    <td><%= toString(OrgDetails.getNotes()) %></td>
  </tr>
</table>

	<% }  %>
	
</body>
		<br>
		<br>
		<%
		
		%>
		
		<%--L OPERATORE STAP VEDE I PULSANTI DI MODIFICA SOLO SE L'OSM FA PARTE DELLA PROVINCIA IN CUI APPARTIENE--%>
		<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
		<dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
			<%--<dhv:permission name="osm-osm-edit">
				<input type="button"
				value="Modifica"
					onClick="javascript:window.location.href='Osm.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';">
			</dhv:permission> --%>
			
		</dhv:evaluate>
		
		
	</dhv:evaluate>
	
		</dhv:container>
		<%=addHiddenParams(request,
									"popup|popupType|actionId")%>
		<%
		if (request.getParameter("return") != null) {
		%>
		<input type="hidden" name="return"
			value="<%=request.getParameter("return")%>">
		<%
		}
		%>
		<%
		if (request.getParameter("actionplan") != null) {
		%>
		<input type="hidden" name="actionplan"
			value="<%=request.getParameter("actionplan")%>">
		<%
		}
		%>
		
		
		
		<script>


function mostraListaOperazioni(listadamostrare){
	
	var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
	
	document.getElementById(listadamostrare).classList.toggle("show");
}

//Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

function openPopupLarge(url){
	
	  var res;
    var result;
    	  window.open(url,'popupSelect',
          'height=600px,width=1000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
</script>