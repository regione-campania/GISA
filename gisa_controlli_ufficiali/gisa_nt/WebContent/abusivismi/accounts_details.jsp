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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.abusivismi.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.abusivismi.base.Organization" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>

<jsp:useBean id="lat" class="java.lang.String" scope="session"/>
<jsp:useBean id="lon" class="java.lang.String" scope="session"/>

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
<table>
<tr>
<td>
    <dhv:permission name="abusivismi-abusivismi-add"><a href="Abusivismi.do?command=Add">Aggiungi</a></dhv:permission>
</td>
<td>
    <dhv:permission name="abusivismi-abusivismi-view"><a href="Abusivismi.do?command=SearchForm">Ricerca</a></dhv:permission>
</td>
</tr>
</table>
<table class="trails" cellspacing="0">
<tr>
<td>
<!--<a href="AltriOperatori.do?command=DashboardScelta"><dhv:label name="">Altri Operatori</dhv:label></a> >--> 
<a href="Abusivismi.do">Operatori Abusivi</a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Abusivismi.do?command=Search">Risultati Ricerca</a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Abusivismi.do?command=Dashboard">Cruscotto</a> >
<%}%>
Scheda
</td>
</tr>
</table>
<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %>

<%-- End Trails --%>
</dhv:evaluate>
<dhv:permission name="abusivismi-abusivismi-report-view">
  <table width="100%" border="0">
    <tr>
     <%-- aggiunto da d.dauria--%>
     
      <%--<td nowrap align="right">
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="Abusivismi.do?command=PrintReport&file=abusivi.xml&id=<%= OrgDetails.getId() %>"><dhv:label name="abusivismi.osa.print">Stampa Scheda Operatore Abusivo</dhv:label></a>
      </td>--%>
      
      <%-- fine degli inserimenti --%>
    </tr>
  </table>
</dhv:permission>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>
<dhv:container name="abusivismi" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <!--<dhv:permission name="abusivismi-abusivismi-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='Abusivismi.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>-->
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="abusivismi-abusivismi-edit"><input type="button" value="Modifica"	onClick="javascript:window.location.href='Abusivismi.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
  <%--
    <dhv:permission name="abusivismi-abusivismi-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='Abusivismi.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>--%>
  </dhv:evaluate>
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="abusivismi-abusivismi-delete"><input type="button" value="Elimina Operatore Abusivo" onClick="javascript:popURLReturn('Abusivismi.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Abusivismi.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
</dhv:evaluate>
<dhv:permission name="abusivismi-abusivismi-edit,abusivismi-abusivismi-delete"><br>&nbsp;</dhv:permission>



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
<%-- 	 <jsp:param value="Abusivismi.do?command=CessazioneAttivita" name="urlSubmitCessazione"/> --%>
<%-- 	 <jsp:param value="<%=OrgDetails.getDate1() != null ? new SimpleDateFormat("ss:mm:hh dd/MM/yyyy").format(new java.util.Date(OrgDetails.getDate1().getTime())) : "" %>" name="data_inizio" /> --%>
<%-- 	 </jsp:include> --%>
<%--      </dhv:evaluate> --%>

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

<% if (1==1) { %>
<jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="34" />
     </jsp:include>
<% } else { %>     

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Informazione primaria</strong>
    </th>
  </tr>
  
  
	<tr class="containerBody">
    <td valign="top" class="formLabel">
      Ignoto
    </td>
    <td>
    <table class="empty"><tr><td> 
     No <input type="radio" disabled="disabled" <%if(OrgDetails.getCessato()==0) { %> checked ="checked" <%} %> name="cessato" value="0">
    </td>
    <td>
    
    Si <input type="radio" disabled="disabled" <%if(OrgDetails.getCessato()==1) { %> checked ="checked" <%} %> name="cessato" value="1">
    </td> 
    </tr></table></td>
    </tr>
   
  
      <dhv:include name="abusivismi-name" none="true">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          Cognome
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getName()) %>&nbsp;
       </td>
      </tr>
    </dhv:include> 
    <dhv:evaluate if="<%= hasText(OrgDetails.getBanca()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			Nome
			</td>
			<td>
         		<%= toHtml(OrgDetails.getBanca()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate>  
  	<dhv:include name="organization.contractEndDate" none="true">
<dhv:evaluate if="<%= hasText(OrgDetails.getContractEndDateString()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.ContractEndDate">Contract End Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
<%--<dhv:evaluate if="<%= OrgDetails.getAccountSize() != -1 %>">
  <tr class="containerBody">
    <td name="accountSize1" id="accountSize1" nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi_add.accountSize">Account Size</dhv:label>
    </td>
    <td>
      <%= OrgCategoriaRischioList.getSelectedValue(OrgDetails.getAccountSize()) %>
    </td>
  </tr>
</dhv:evaluate>--%>
<dhv:evaluate if="<%= (OrgDetails.getYearStarted() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="abusivismi.abusivismi_add.year_started">Year Started</dhv:label>
      </td>
      <td>
       <%= OrgDetails.getYearStarted() %>&nbsp;
      </td>
    </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= (OrgDetails.getDate1() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
     Data di Nascita
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getDate1() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
<dhv:evaluate if="<%= hasText(OrgDetails.getCategoria()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Luogo di Nascita
      </td>
      <td>
       <%= toHtmlValue(OrgDetails.getCategoria()) %>
      </td>
    </tr>
</dhv:evaluate>
</dhv:include>
<%--<dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.accountNumber">Account Number</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getAccountNumber()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>--%>
<%--<dhv:evaluate if="<%= hasText(OrgDetails.getTipoAbus()) %>">
   <tr class="containerBody">
      <td nowrap class="formLabel" name="tipoAbus1" id="tipoAbus1">
        <dhv:label name="organization.tipoAbus">Account Number</dhv:label>
      </td>
      <td>
        <%= toHtmlValue(OrgDetails.getTipoAbus()) %>
      </td>
    </tr>
 </dhv:evaluate>  --%> 
 <%--<dhv:evaluate if="<%= hasText(OrgDetails.getNumAut())%>" >
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Numero Autorizzazione</dhv:label>
      </td>
      <td>
        <%= toHtmlValue(OrgDetails.getNumAut()) %>
      </td>
    </tr>
 </dhv:evaluate>
<dhv:evaluate if="<%= hasText(OrgDetails.getCategoria()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Categoria</dhv:label>
      </td>
      <td>
       <%= toHtmlValue(OrgDetails.getCategoria()) %>
      </td>
    </tr>
</dhv:evaluate> --%>
 <%-- <dhv:include name="organization.source" none="true">
   <tr>
      <td name="impianto1" id="impianto1" nowrap class="formLabel">
        <dhv:label name="">Tipo Impianto</dhv:label>
      </td>
    <td>
      <%= Impianto.getHtmlSelect("impianto",OrgDetails.getImpianto()) %>
    </td>
  </tr>
  </dhv:include>--%> 
  
<%--<dhv:evaluate if="<%= OrgDetails.getImpianto() > 0 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Tipo Impianto</dhv:label>
    </td>
    <td> 
      <%= impianto.getSelectedValue(OrgDetails.getImpianto()) %>
      <input type="hidden" name="impianto" value="<%=OrgDetails.getImpianto()%>" >
    </td>
  </tr>
</dhv:evaluate>--%>
<%--<dhv:evaluate if="<%= hasText(OrgDetails.getCodiceImpianto()) %>">
<tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Codice Impianto</dhv:label>
      </td>
      <td>
         <%= toHtmlValue(OrgDetails.getCodiceImpianto()) %>
      </td>
    </tr>
 </dhv:evaluate> --%>
<%--
<dhv:evaluate if="<%= StatoLab.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Stato Laboratorio</dhv:label>
    </td>
    <td> 
      <%= StatoLab.getSelectedValue(OrgDetails.getStatoLab()) %>
      <input type="hidden" name="siteId" value="<%=OrgDetails.getStatoLab()%>" >
    </td>
  </tr>
</dhv:evaluate> 


<dhv:evaluate if="<%= StatoAbus.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
</dhv:evaluate>
 <dhv:evaluate if="<%= OrgDetails.getTypes().size() > 0 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="abusivismi.abusivismi.types">Account Type(s)</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getTypes().valuesAsString()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>--%>

<dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscale()) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      Codice Fiscale
			</td>
			<td>
         <%= toHtml(OrgDetails.getCodiceFiscale()) %>&nbsp;
			</td>
		</tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(OrgDetails.getPartitaIva()) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      Partita IVA
			</td>
			<td>
         <%= toHtml(OrgDetails.getPartitaIva()) %>&nbsp;
			</td>
		</tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(OrgDetails.getNameFirst()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Comune Residenza
    </td>
    <td>
      <%= OrgDetails.getNameFirst() %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(OrgDetails.getNameLast()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
     Via
    </td>
    <td>
     <%= OrgDetails.getNameLast() %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= (OrgDetails.getDate2() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      Data Controllo
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getDate2() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
  
</table>
<br>

<dhv:include name="organization.addresses" none="true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="abusivismi.abusivismi_add.Addressesq">Luogo di Rilevazione Attività Abusiva</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      ASL
    </td>
    <td>
      <%= SiteList.getSelectedValue(OrgDetails.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
    </td>
  </tr>
<%  
  Iterator iaddress = OrgDetails.getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
      
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        Indirizzo
      </td>
      <td>
        <%= toHtml(thisAddress.toString()) %>&nbsp;
        <dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">        
        Indirizzo
        </dhv:evaluate>
      </td>
    </tr>
<%
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
<br />
</dhv:include>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Dettagli Addizionali</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
     Note
    </td>
    <td>
      <%=toHtml(OrgDetails.getNotes()) %>&nbsp;
    </td>
  </tr>
</table>

<% } %>
<br />

<dhv:permission name="abusivismi-abusivismi-edit,abusivismi-abusivismi-delete"><br></dhv:permission>
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="abusivismi-abusivismi-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='Abusivismi.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="abusivismi-abusivismi-edit"><input type="button" value="Modifica"	onClick="javascript:window.location.href='Abusivismi.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  <%--<dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="abusivismi-abusivismi-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='Abusivismi.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>--%>
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="abusivismi-abusivismi-delete"><input type="button" value="Elimina Operatore Abusivo" onClick="javascript:popURLReturn('Abusivismi.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Abusivismi.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
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
