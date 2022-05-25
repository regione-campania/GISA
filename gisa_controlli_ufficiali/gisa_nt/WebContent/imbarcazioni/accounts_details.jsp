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
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - ANY DAMAGES, INCLUDIFNG ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.imbarcazioni.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants"%>
<%@page import="org.aspcfs.modules.audit.base.Audit"%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>
<%@page import="java.sql.*"%>
<%@page import="org.aspcfs.modules.lineeattivita.base.*"%>

<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%><link
	rel="stylesheet" type="text/css" href="css/jmesa.css"></link>
<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
<script type="text/javascript" src="javascript/jmesa.js"></script>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="OrgCategoriaRischioList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="IstatList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="specieAnimali" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="tipoStabulatorio"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statoAutorizzazzioni"
	class="org.aspcfs.utils.web.LookupList" scope="request" />


<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.imbarcazioni.base.Organization"
	scope="request" />
<jsp:useBean id="Voltura"
	class="org.aspcfs.modules.cessazionevariazione.base.Ticket"
	scope="request" />
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit"
	scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SICCodeList"
	class="org.aspcfs.modules.admin.base.SICCodeList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="applicationPrefs"
	class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request" />
<%@ include file="../initPage.jsp"%>


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
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	display: block;
	padding: 6px 16px;
}

.show {
	display: block;
}

/* Change color of dropdown links on hover */
.dropdown-content a:hover {
	background-color: #D0D0D0
}
</style>

<body>




	<dhv:evaluate if="<%= !isPopup(request) %>">
		<%-- Trails --%>

		<table class="trails" cellspacing="0">
			<tr>
				<td><a href="Imbarcazioni.do">Imbarcazioni</a> > <% if (request.getParameter("return") == null) { %>
					<a href="Imbarcazioni.do?command=Search"><dhv:label
							name="accounts.SearchResults">Search Results</dhv:label></a> > <%} else if (request.getParameter("return").equals("dashboard")) {%>
					<a href="Imbarcazioni.do?command=Dashboard">Cruscotto</a> > <%}%>
					Scheda Imbarcazione</td>
			</tr>
		</table>

		<%-- End Trails --%>
	</dhv:evaluate>

	<br>
	<br>
	<%@ include file="../../controlliufficiali/diffida_list.jsp"%>

	<% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>
	<dhv:permission name="imbarcazioni-imbarcazioni-report-view">
		<%
  OrganizationAddressList listaInd = OrgDetails.getAddressList();
  Iterator<OrganizationAddress> it= listaInd.iterator();
  int countMio = 0;
  Integer addressid = -1;
  Integer addressid2 = -1;
  Integer addressid3 = -1;
  while(it.hasNext()){
	  
	  OrganizationAddress temp = it.next();
	  if(temp.getType()==6){
		  countMio++;
		  if(countMio == 1){
			  
			   addressid=temp.getId();
			 
		  }
		  if(countMio==2){
			  
			  addressid2=temp.getId();
			
		  }if(countMio==3){
			  
			  addressid3=temp.getId();
			
		  }
	  }
  }
  countMio=0;
  %>


	</dhv:permission>
	<% String param1 = "orgId=" + OrgDetails.getOrgId();
   %>
	<script language="JavaScript" TYPE="text/javascript"
		SRC="gestione_documenti/generazioneDocumentale.js"></script>

	<dhv:permission name="">
		<table width="100%" border="0">
			<tr>
				<%-- aggiunto da d.dauria--%>

				<td nowrap align="right">
					<!-- img
				src="images/icons/stock_print-16.gif" border="0" align="absmiddle"
				height="16" width="16" /> <a
				href="SchedaPrint.do?command=PrintReport&file=stabilimenti&id=<%= OrgDetails.getId() %>"><dhv:label
				name="stabilimenti.osa.print">Stampa Scheda stabilimenti</dhv:label></a-->



					<%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda imbarcazioni" value="Stampa Scheda imbarcazioni"		onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '-1', '-1', '-1', 'imbarcazioni', 'SchedaImbarcazioni');"--%>

					<img src="images/icons/stock_print-16.gif" border="0"
					align="absmiddle" height="16" width="16" /> <input type="button"
					title="Stampa Scheda" value="Stampa Scheda"
					onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '-1', '-1', '-1', '3');">


				</td>


				<%-- fine degli inserimenti --%>
			</tr>
		</table>
	</dhv:permission>





	<dhv:permission name="opnonaltrove-import-add">

		<%
		if(true)
	  	//if(User.getRoleId()!=Role.HD_1LIVELLO && User.getRoleId()!=Role.HD_2LIVELLO)
	  	{
	  
	  %>
		<div align="center">
			<input type="button" class="yellowBigButton"
				value="Importa in Anagrafica stabilimenti"
				onClick="javascript:window.location.href='OpuStab.do?command=CaricaImport&orgId=<%=OrgDetails.getOrgId()%>'">
		</div>


		<%
	  	}
	  	else
	  	{
	  		%>



		<center>
			<p style="color: red; font-weight: bold;">Per usare la nuova
				funzione di import cliccare su 'Completa scheda anagrafica NEW!!!'</p>
		</center>
		<div align="center">
			<input type="button" class="greenBigButton"
				style="color: black !important"
				value="Completa scheda anagrafica NEW!!!"
				onClick="location.href='GestioneAnagraficaAction.do?command=Import&altId=<%=OrgDetails.getOrgId()%>';">
		</div>

		<div align="center">
			<input type="button"
				value="Importa in Anagrafica stabilimenti (VECCHIA VERSIONE)"
				style="background-color: grey !important"
				onClick="javascript:window.location.href='OpuStab.do?command=CaricaImport&orgId=<%=OrgDetails.getOrgId()%>'">
		</div>

		<%
	  		
	  	}
	  %>
	</dhv:permission>


	<dhv:container name="imbarcazioni" selected="details"
		object="OrgDetails" param="<%= param1 %>"
		appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'
		hideContainer="<%= OrgDetails.isTrashed() %>">

		<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
		<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
			<!--<dhv:permission name="imbarcazioni-imbarcazioni-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='Imbarcazioni.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>-->
		</dhv:evaluate>


		<dhv:permission name="imbarcazioni-imbarcazioni-edit">
			<%-- <dhv:evaluate if="<%=(((UserBean)session.getAttribute("User")).getSiteId()>0 &&((UserBean)session.getAttribute("User")).getSiteId()==OrgDetails.getSiteId()) %>">--%>
			<input type="button" value="Modifica"
				onClick="javascript:window.location.href='Imbarcazioni.do?command=Modify&orgId=<%=OrgDetails.getOrgId()%><%=addLinkParams(request, "popup|actionplan")%>';">
			<%-- </dhv:evaluate> --%>
		</dhv:permission>
		<%-- 
	   <dhv:permission name="imbarcazioni-imbarcazioni-edit">
	   <dhv:evaluate if="<%=(((UserBean)session.getAttribute("User")).getSiteId()<=0) %>">
	    <dhv:permission name="imbarcazioni-imbarcazioni-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='Imbarcazioni.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
	  </dhv:evaluate>
	  </dhv:permission>
	  --%>
		<dhv:permission name="imbarcazioni-imbarcazioni-delete">
			<input type="button" value="Elimina"
				onClick="javascript:popURLReturn('Imbarcazioni.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Imbarcazioni.do?command=Search', 'Delete_account','320','200','yes','no');">
		</dhv:permission>




		<%-- 	 <dhv:evaluate if='<%=OrgDetails.getStatoLab()!=4 %>'> --%>
		<%-- 	 <dhv:permission name="osa-cessazione-pregressa-view"> --%>
		<!-- 	 <div class="ovale"align="center"> -->
		<!-- 	 <br> -->
		<!-- 	 <p> -->
		<!-- 	 <center> -->
		<!-- 	 <input type="button" value="CESSAZIONE OSA"  onclick="openPopUpCessazioneAttivita();" width="120px;" > -->
		<!-- 	 </center></p> -->
		<!-- 	 <br><br> -->
		<!-- 	 </div> -->
		<%-- 	 </dhv:permission> --%>

		<%-- 	 <jsp:include page="../dialog_cessazione_attivita.jsp"> --%>
		<%-- 	 <jsp:param value="<%=OrgDetails.getOrgId() %>" name="idAnagrafica"/> --%>
		<%-- 	 <jsp:param value="Imbarcazioni.do?command=CessazioneAttivita" name="urlSubmitCessazione"/> --%>
		<%-- 	 <jsp:param value="<%=OrgDetails.getDate1() != null ? new SimpleDateFormat("ss:mm:hh dd/MM/yyyy").format(new java.util.Date(OrgDetails.getDate1().getTime())) : "" %>" name="data_inizio" /> --%>
		<%-- 	 </jsp:include> --%>
		<%--      </dhv:evaluate> --%>

		<dhv:permission name="note_hd-view">
			<jsp:include page="../note_hd/link_note_hd.jsp">
				<jsp:param name="riferimentoId" value="<%=OrgDetails.getOrgId()%>" />
				<jsp:param name="riferimentoIdNomeTab" value="organization" />
			</jsp:include>
			<br>
			<br>
		</dhv:permission>

		<jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">
			<jsp:param name="riferimentoIdPreaccettazione"
				value="<%=OrgDetails.getOrgId()%>" />
			<jsp:param name="riferimentoIdNomePreaccettazione" value="orgId" />
			<jsp:param name="riferimentoIdNomeTabPreaccettazione"
				value="organization" />
			<jsp:param name="userIdPreaccettazione"
				value="<%=User.getUserId()%>" />
		</jsp:include>

		<%
			if (1 == 1) {
		%>
		<jsp:include page="../schede_centralizzate/iframe.jsp">
			<jsp:param name="objectId" value="<%=OrgDetails.getOrgId()%>" />
			<jsp:param name="tipo_dettaglio" value="3" />
		</jsp:include>

		<%
			} else {
		%>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong><dhv:label
							name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
				</th>
			</tr>

			<%-- <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
      <%= SiteList.getSelectedValue(OrgDetails.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
--%>
			<tr>
				<td nowrap class="formLabel" name="orgname1" id="orgname1">
					Ufficio Marittimo di provenienza</td>
				<td>
					<%
						if (OrgDetails.isFuori_regione()) {
					%> FUORI REGIONE <%
						} else {
					%> IN REGIONE <%
						}
					%>
				</td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Data iscrizione agli Uffici
					Marittimi</td>
				<td><%=(OrgDetails.getDataPresenazioneString() != null && !OrgDetails.getDataPresenazioneString().equals(""))
		? OrgDetails.getDataPresenazioneString()
		: "N.D"%>&nbsp;
				</td>
			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel">Identificativo della barca U.E</td>
				<td><%=toHtml(OrgDetails.getAccountNumber())%>&nbsp;</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">Targa</td>
				<td><%=(OrgDetails.getNumaut() != null && !OrgDetails.getNumaut().equals(""))
		? toHtmlValue(OrgDetails.getNumaut())
		: "N.D"%>
				</td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
						name="">Num. Reg (ex 852)</dhv:label></td>
				<td><%=(OrgDetails.getTaxid() != null && !OrgDetails.getTaxid().equals(""))
		? toHtmlValue(OrgDetails.getTaxid())
		: "N.D"%>
				</td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Nome dell'imbarcazione</td>
				<td><%=(OrgDetails.getNamefirst() != null && !OrgDetails.getNamefirst().equals(""))
		? toHtmlValue(OrgDetails.getNamefirst())
		: "N.D"%>
				</td>
			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
						name="accounts.accounts_add.OrganizationName">Armatore</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getName())%></td>
			</tr>


			<%
				if (Audit != null) {
			%>

			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Categoria di Rischio</dhv:label>
				</td>
				<td><%=OrgDetails.getCategoriaRischio()%></td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Prossimo Controllo</br>con la tecnica della Sorveglianza</dhv:label>
				</td>
				<td>
					<%
						SimpleDateFormat dataPC = new SimpleDateFormat("dd/MM/yyyy");
					%> <%=(((OrgDetails.getDataProssimoControllo() != null))
		? (dataPC.format(OrgDetails.getDataProssimoControllo()))
		: (dataPC.format(d)))%>
				</td>
			</tr>
			<%
				}
			%>
			<tr class="containerBody">
				<td nowrap class="formLabel">Partita IVA/Codice Fiscale</td>
				<td><%=(OrgDetails.getPartitaIva() != null && !OrgDetails.getPartitaIva().equals(""))
		? toHtmlValue(OrgDetails.getPartitaIva())
		: "N.D"%>
				</td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Tonnellate di stazza</td>
				<td><%=(OrgDetails.getCapacita_max() != null) ? toHtmlValue(OrgDetails.getCapacita_max()) : "N.D"%>
				</td>
			</tr>
			<%
				if (OrgDetails.getTipoPesca().size() != 0) {

				HashMap<Integer, String> tipiPesca = OrgDetails.getTipoPesca();
				Set<Integer> setkiavi = tipiPesca.keySet();
				Iterator<Integer> iteraTipi = setkiavi.iterator();
			%>
			<tr class="containerBody">

				<td nowrap class="formLabel">Tipo di pesca</td>
				<td>
					<%
						while (iteraTipi.hasNext()) {
						int chiave = iteraTipi.next();
						String value = tipiPesca.get(chiave);
					%> <%="- " + tipiPesca.get(chiave)%><br> <%
 	}
 %> <%-- <%=(OrgDetails.getTipo_struttura() != null && !OrgDetails.getTipo_struttura().equals("")) ? toHtmlValue(OrgDetails.getTipo_struttura()) : "N.D" %>--%>
				</td>

			</tr>
			<%
				}
			%>

			<%
				if (OrgDetails.getSistemaPesca().size() != 0) {

				HashMap<Integer, String> sistemiPesca = OrgDetails.getSistemaPesca();
				Set<Integer> setkiavi = sistemiPesca.keySet();
				Iterator<Integer> iteraSistemi = setkiavi.iterator();
			%>
			<tr class="containerBody">


				<td nowrap class="formLabel">Sistema di pesca</td>
				<td>
					<%
						while (iteraSistemi.hasNext()) {
						int chiave = iteraSistemi.next();
						String value = sistemiPesca.get(chiave);
					%> <%="- " + sistemiPesca.get(chiave)%><br> <%
 	}
 %> <%--  <%= (OrgDetails.getDuns_type() != null && !OrgDetails.getDuns_type().equals("")) ? toHtmlValue(OrgDetails.getDuns_type()) : "N.D" %>--%>
				</td>
			</tr>
			<%
				}
			%>
			<tr class="containerBody">
				<td nowrap class="formLabel">Numero iscrizione agli Uffici
					Marittimi</td>
				<td><%=(OrgDetails.getAutorizzazione() != null && !OrgDetails.getAutorizzazione().equals(""))
		? toHtmlValue(OrgDetails.getAutorizzazione())
		: "N.D"%>
				</td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Data cancellazione dagli Uffici
					Marittimi</td>
				<td><%=(OrgDetails.getData2String() != null && !OrgDetails.getData2String().equals(""))
		? toHtmlValue(OrgDetails.getData2String())
		: "N.D"%>
				</td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Presenza a bordo di impianto di
					refrigerazione</td>
				<td>
					<%
						if (OrgDetails.isFlag_selezione()) {
					%> SI <%
						} else {
					%>NO <%
						}
					%>
				</td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Note</td>
				<td><%=(OrgDetails.getNotes() != null && !OrgDetails.getNotes().equals(""))
		? toHtmlValue(OrgDetails.getNotes())
		: "N.D"%>
				</td>
			</tr>
		</table>
		<br />
		<br />
		<%-- <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Rappresentante Legale</strong>
    </th>
  </tr>
      <tr >
    <td class="formLabel" nowrap>
      Nome
    </td>
    <td>
   <%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Cognome
    </td>
    <td>
      <%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>
    </td>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
      <td>
      	    <%= ((OrgDetails.getDataNascitaRappresentante()!=null)?(toHtml(DateUtils.getDateAsString(OrgDetails.getDataNascitaRappresentante(),Locale.ITALY))):("")) %>
   
        
      </td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di nascita</dhv:label>
    </td>
    <td>
     <%=toHtmlValue(OrgDetails.getLuogoNascitaRappresentante()) %>
    </td>
  </tr>

  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Telefono</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(OrgDetails.getTelefonoRappresentante()) %>
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Fax</dhv:label>
    </td>
    <td>
     <%= toHtmlValue(OrgDetails.getFax()) %>
    </td>
    
  </tr></table>--%>


		<br>


		<dhv:include name="organization.addresses" none="true">
			<%
				int numLocali = 0;
			Iterator iaddress2 = OrgDetails.getAddressList().iterator();
			while (iaddress2.hasNext()) {
				OrganizationAddress thisAddress = (OrganizationAddress) iaddress2.next();
				/*if(thisAddress.getType()==6){
					numLocali++;
				}*/

			}
			%>

			<%
				int cont = 0;
			Iterator iaddress = OrgDetails.getAddressList().iterator();
			Object address[] = null;
			int i = 0;
			int locali = 0;
			if (iaddress.hasNext()) {
				while (iaddress.hasNext()) {
					OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
					if (thisAddress.getType() != 6) {
			%>
			<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">
				<tr>
					<th colspan="2"><dhv:evaluate
							if="<%=thisAddress.getType() == 1%>">
							<strong><dhv:label
									name="accounts.accounts_add.Addressess">Sede legale Armatore</dhv:label></strong>
						</dhv:evaluate> <dhv:evaluate if="<%=thisAddress.getType() == 5%>">
							<strong><dhv:label
									name="accounts.accounts_add.Addressess">Ormeggio abituale</dhv:label></strong>
						</dhv:evaluate> <%--   <dhv:evaluate if="<%= thisAddress.getType() == 6 %>">
	   
	 
	 
	
	     <% if(cont==0 ){
	    	
	     %>
	  <dhv:evaluate if="<%= thisAddress.getType() == 6 &&  !thisAddress.getCity().equals("")%>">
	  <%cont++; %>
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Locale funzionalmente collegato</dhv:label></strong>
	  </dhv:evaluate>  
	<%}else{
	if( thisAddress.getType() == 6 &&  !thisAddress.getCity().equals("")){
		cont++;
		 locali++;
	}
	}	
		%>
	  </dhv:evaluate>  --%></th>
				</tr>


				<%
					int tipolocale = -1;
				if (cont == 1) {

					session.setAttribute("addressid", thisAddress.getId());

				}
				if (cont == 2) {

					session.setAttribute("addressid2", thisAddress.getId());

				}
				if (cont == 3) {

					session.setAttribute("addressid3", thisAddress.getId());

				}
				%>



				<tr class="containerBody">
					<td nowrap class="formLabel" valign="top"><%=toHtml(thisAddress.getTypeName())%>
					</td>
					<td><%=toHtml(thisAddress.toString())%>&nbsp;<br /><%=thisAddress.getGmapLink()%>

						<dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">
							<dhv:label name="requestor.primary.brackets">(Primary)</dhv:label>
						</dhv:evaluate></td>
					<%
						if (numLocali == 0) {
					%>
				</tr>


			</table>
			<br>
			<%
				}

			}
			}
			} else {
			%>
			<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">
				<tr>
					<th colspan="2"><strong><dhv:label
								name="requestor.requestor_add.Addresses">Addresses</dhv:label></strong></th>
				</tr>
				<tr class="containerBody">
					<td colspan="2"><font color="#9E9E9E"><dhv:label
								name="contacts.NoAddresses">No addresses entered.</dhv:label></font></td>
				</tr>
			</table>
			<br>
			<%
				}
			%>


		</dhv:include>

		<%
			}
		%>



		<br>


		<dhv:permission name="imbarcazioni-imbarcazioni-edit">
			<dhv:evaluate
				if="<%=(((UserBean) session.getAttribute("User")).getSiteId() > 0
		&& ((UserBean) session.getAttribute("User")).getSiteId() == OrgDetails.getSiteId())%>">
				<dhv:permission name="imbarcazioni-imbarcazioni-edit">
					<input type="button" value="Modifica"
						onClick="javascript:window.location.href='Imbarcazioni.do?command=Modify&orgId=<%=OrgDetails.getOrgId()%><%=addLinkParams(request, "popup|actionplan")%>';">
				</dhv:permission>
			</dhv:evaluate>
		</dhv:permission>
		<dhv:permission name="imbarcazioni-imbarcazioni-edit">
			<dhv:evaluate
				if="<%=(((UserBean) session.getAttribute("User")).getSiteId() <= 0)%>">
				<dhv:permission name="imbarcazioni-imbarcazioni-edit">
					<input type="button" value="Modifica"
						onClick="javascript:window.location.href='Imbarcazioni.do?command=Modify&orgId=<%=OrgDetails.getOrgId()%><%=addLinkParams(request, "popup|actionplan")%>';">
				</dhv:permission>
			</dhv:evaluate>
		</dhv:permission>


		<dhv:evaluate if='1==1'>
			<dhv:permission name="imbarcazioni-imbarcazioni-delete">
				<input type="button" value="Elimina"
					" onClick="javascript:popURLReturn('Imbarcazioni.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Imbarcazioni.do?command=Search', 'Delete_account','320','200','yes','no');">
			</dhv:permission>
		</dhv:evaluate>



	</dhv:container>


	<script>
		function mostraListaOperazioni(listadamostrare) {

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
				var dropdowns = document
						.getElementsByClassName("dropdown-content");
				var i;
				for (i = 0; i < dropdowns.length; i++) {
					var openDropdown = dropdowns[i];
					if (openDropdown.classList.contains('show')) {
						openDropdown.classList.remove('show');
					}
				}
			}
		}

		function openPopupLarge(url) {

			var res;
			var result;
			window
					.open(
							url,
							'popupSelect',
							'height=600px,width=1000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
		}
	</script>

</body>