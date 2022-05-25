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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<%@page import="org.aspcfs.modules.audit.base.Audit"%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>
<%@page import="java.sql.*"%>

<%@page import="org.aspcfs.modules.distributori.base.OrganizationAddress"%><link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>		
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IstatList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="NewDistributore" class="org.aspcfs.modules.distributori.base.Distrubutore" scope="request"/>



<jsp:useBean id="Voltura" class="org.aspcfs.modules.cessazionevariazione.base.Ticket" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%if (refreshUrl!=null && !"".equals(refreshUrl)){ %>
<script language="JavaScript" TYPE="text/javascript">
parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>
<%}%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Distributori.do?command=List">Distributori</a> > 

Dettaglio Macchinetta e Impresa
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>

<% String param1 = "stabId=" + OrgDetails.getIdStabilimento()+"&id="+request.getAttribute("id")+"&asl="+NewDistributore.getAslMacchinetta();

request.setAttribute("Operatore", OrgDetails.getOperatore());
  
String nomeContainer = OrgDetails.getContainer();
%>
  
  
<dhv:container name="<%=nomeContainer %>" selected="details" object="Operatore" param="<%= param1 %>"  appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>




<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
    </th>
  </tr>
  
<dhv:include name="accounts-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
      <%= SiteList.getSelectedValue(OrgDetails.getIdAsl()) %>
      <input type="hidden" name="siteId" value="<%=OrgDetails.getIdAsl()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
</dhv:include>
  
<dhv:include name="accounts-name" none="true">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="accounts.accounts_add.OrganizationName">Organization Name</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getOperatore().getRagioneSociale()) %>&nbsp;
       </td>
      </tr>
</dhv:include>  
  
  
<dhv:evaluate if="<%= hasText(OrgDetails.getInfoStab().getCodice_registrazione()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.accountNumber">Account Number</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getInfoStab().getCodice_registrazione()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= hasText(OrgDetails.getInfoStab().getCodice_interno()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.codice_impresa_interno">Account Number</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getInfoStab().getCodice_interno()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>



  <dhv:evaluate if="<%= hasText(OrgDetails.getOperatore().getPartitaIva()) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      Partita IVA
			</td>
			<td>
         <%= toHtml(OrgDetails.getOperatore().getPartitaIva()) %>&nbsp;
			</td>
		</tr>
  </dhv:evaluate>
 <dhv:evaluate if="<%= hasText(OrgDetails.getOperatore().getCodFiscale()) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      Codice Fiscale
			</td>
			<td>
         <%= toHtml(OrgDetails.getOperatore().getCodFiscale()) %>&nbsp;
			</td>
		</tr>
  </dhv:evaluate>
  
    


  	
  
 <dhv:include name="organization.date1" none="true">
    <dhv:evaluate if="<%= (OrgDetails.getInfoStab().getDataPresentazioneSciaString() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Presentazione D.I.A./Inizio Attività</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OrgDetails.getInfoStab().getDataPresentazioneSciaString() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
      </td>
    </tr>
    </dhv:evaluate>
 </dhv:include> 

  <% if(Audit!=null){ %>
  <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Categoria di Rischio</dhv:label>
      </td>
      <td>
         <%= (((OrgDetails.getCategoriaRischio()!=-1) && (OrgDetails.getCategoriaRischio()>0))?(OrgDetails.getCategoriaRischio()):("3"))%>
      </td>
    </tr>
  <%}%>

</table>
<br />

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
     
    </th>
  </tr>

  <dhv:evaluate if="<%= hasText(OrgDetails.getOperatore().getRappLegale().getCodFiscale()) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Codice Fiscale
			</td>
			<td>
         	<%= OrgDetails.getOperatore().getRappLegale().getCodFiscale() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= hasText(OrgDetails.getOperatore().getRappLegale().getNome())%>">		
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Nome
			</td>
			<td>
         	<%= OrgDetails.getOperatore().getRappLegale().getNome() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
  	 <dhv:evaluate if="<%= hasText(OrgDetails.getOperatore().getRappLegale().getCognome()) %>">
<tr class="containerBody">
			<td nowrap class="formLabel">
      			Cognome
			</td>
			<td>
         	<%= OrgDetails.getOperatore().getRappLegale().getCognome() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>

<dhv:evaluate if="<%= OrgDetails.getOperatore().getRappLegale().getDataNascita() != null %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Nascita</dhv:label>
    </td>
    <td>
     <%= OrgDetails.getOperatore().getRappLegale().getDataNascitaString() %>
    </td>
  </tr>
</dhv:evaluate>
  	 
		<dhv:evaluate if="<%= (hasText(OrgDetails.getOperatore().getRappLegale().getComuneNascita())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Comune di nascita</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getOperatore().getRappLegale().getComuneNascita()%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
	
	
		<!--  fine delle modifiche -->
		
</table>
<br>





<br><br>




<%
 
 
 OrganizationAddress thisAddress=NewDistributore.getOrganizationAddress();
 %>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Informazioni Macchinetta</strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Matricola
    </td>
    <td>
      <%=toHtml(NewDistributore.getMatricola()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
     Situato in
    </td>
    <td>
     <%=NewDistributore.getComune()+"<br>"+NewDistributore.getIndirizzo()+", "+NewDistributore.getProvincia()+"<br> "+NewDistributore.getCap()+"&nbsp; <br>"+thisAddress.getGmapLink()%>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
     Ubicazione
    </td>
    <td>
     <%=NewDistributore.getUbicazione() %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
    Alimento Distribuito
    </td>
    <td>
     <%=NewDistributore.getDescrizioneTipoAlimenti()%>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
Note
    </td>
    <td>
     <%=NewDistributore.getNote()%>
    </td>
  </tr>
  

  
  
</table>


<br>
</dhv:container>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
