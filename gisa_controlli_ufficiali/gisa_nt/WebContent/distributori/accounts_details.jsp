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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
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
<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %>

<% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>

<% String param1 = "orgId=" + OrgDetails.getOrgId()+"&id="+request.getAttribute("id")+"&asl="+NewDistributore.getAslMacchinetta();


   %>
  
<dhv:container name="distributoridettaglio" selected="details" object="OrgDetails" param="<%= param1 %>"  appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="false">




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
      <%= SiteList.getSelectedValue(OrgDetails.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
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
          <%= toHtmlValue(OrgDetails.getName()) %>&nbsp;
       </td>
      </tr>
</dhv:include>  
  
<dhv:evaluate if="<%= hasText(OrgDetails.getBanca()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Denominazione</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getBanca()) %>&nbsp;
			</td>
		</tr>
</dhv:evaluate>
  
<dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.accountNumber">Account Number</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getAccountNumber()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= hasText(OrgDetails.getCodiceImpresaInterno()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.codice_impresa_interno">Account Number</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getCodiceImpresaInterno()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= OrgDetails.getTypes().size() > 0 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.account.types">Account Type(s)</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getTypes().valuesAsString()) %>&nbsp;
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
  
    <dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscaleCorrentista()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Codice Istat Principale</dhv:label>
			</td>
			<td>
        		<%= toHtml(OrgDetails.getCodiceFiscaleCorrentista()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate>  
<dhv:include name="organization.alert" none="true">
    <dhv:evaluate if="<%= hasText(OrgDetails.getAlertText()) %>">
      <tr class="containerBody">
       <td nowrap class="formLabel">
         <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
       </td>
       <td>
         <%= toHtml(OrgDetails.getAlertText()) %>
       </td>
     </tr>
   </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.sicDescription" none="true">
    <dhv:evaluate if="<%= hasText(OrgDetails.getSicDescription()) %>">
      <tr class="containerBody">
	    <td nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.sicDescription">SIC Description</dhv:label>
		</td>
		<td>
         <%= toHtml(OrgDetails.getSicDescription()) %>&nbsp;
		</td>
	  </tr>
    </dhv:evaluate>
</dhv:include>

    <dhv:evaluate if="<%= hasText(OrgDetails.getCab()) %>">
      <tr class="containerBody">
	    <td nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.cab">CAB</dhv:label>
		</td>
		<td>
         <%= toHtml(OrgDetails.getCab()) %>&nbsp;
		</td>
	  </tr>
    </dhv:evaluate>
<dhv:include name="organization.url" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getUrl()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.WebSiteURL">Web Site URL</dhv:label>
      </td>
      <td>
        <a href="<%= toHtml(OrgDetails.getUrlString()) %>" target="_new"><%= toHtml(OrgDetails.getUrl()) %></a>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.industry" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getIndustryName()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.Industry">Industry</dhv:label>
      </td>
      <td>
         <%= toHtml(OrgDetails.getIndustryName()) %>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.dunsType" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getDunsType()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.duns_type">DUNS Type</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getDunsType()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.employees" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getEmployees() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="organization.employees">No. of Employees</dhv:label>
      </td>
      <td>
         <%= OrgDetails.getEmployees() %>
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.potential" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getPotential() > 0) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Potential">Potential</dhv:label>
    </td>
    <td>
       <zeroio:currency value="<%= OrgDetails.getPotential() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
  </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.revenue" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getRevenue() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.Revenue">Revenue</dhv:label>
      </td>
      <td>
         <zeroio:currency value="<%= OrgDetails.getRevenue() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
  </dhv:evaluate>
  </dhv:include>
  <dhv:include name="organization.ticker" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getTicker()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.TickerSymbol">Ticker Symbol</dhv:label>
      </td>
      <td>
         <%= toHtml(OrgDetails.getTicker()) %>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.dunsNumber" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getDunsNumber()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.duns_number">DUNS Number</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getDunsNumber()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.businessNameTwo" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getBusinessNameTwo()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.business_name_two">Business Name 2</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getBusinessNameTwo()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<%--
<dhv:include name="organization.sicCode" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getSicCode() > -1) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.sic_code">SIC</dhv:label>
    </td><td>
       <%= SICCodeList.getDescriptionByCode(OrgDetails.getSicCode()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
--%>
<dhv:include name="accounts-size" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getAccountSizeName()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.accountSize">Account Size</dhv:label>
      </td>
      <td>
      
      <%= toHtml(OrgDetails.getAccountSizeName()) %>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.segment" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getSegmentId() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.segment">Segment</dhv:label>
      </td>
      <td>
         <%= SegmentList.getSelectedValue(OrgDetails.getSegmentId()) %>
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.directBill" none="true">
  <dhv:evaluate if="<%= OrgDetails.getDirectBill() %>">
    <tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Direct Bill
        </td>
      <td>
      <input type="checkbox" name="directBill" CHECKED DISABLED />
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>

    <dhv:evaluate if="<%= hasText(OrgDetails.getContoCorrente()) %>">
  	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Tipo Autoveicolo</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getContoCorrente()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate>  
    <dhv:evaluate if="<%= hasText(OrgDetails.getNomeCorrentista()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Targa Autoveicolo</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getNomeCorrentista()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate> 
  	<%if(hasText(OrgDetails.getNomeCorrentista())) {%> 
  	<%-- %><tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Codice Contenitore</dhv:label>
    </td><td>
       <%= toHtmlValue(OrgDetails.getCodiceCont()) %>&nbsp;
    </td></tr>--%>
  	<%}
  	else{}%>
  	<%--dhv:include name="organization.source" none="true">
  <dhv:evaluate if="<%= OrgDetails.getSource() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="contact.source">Source</dhv:label>
      </td>
      <td>
     
        <%= SourceList.getSelectedValue(OrgDetails.getSource()) %>
     <dhv:evaluate if="<%= OrgDetails.getSource()==1 %>">
        &nbsp; dal <zeroio:tz timestamp="<%= OrgDetails.getDateI() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/> al&nbsp; <zeroio:tz timestamp="<%= OrgDetails.getDateF() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
        </dhv:evaluate>
      </td>
    </tr>
  
    
  </dhv:evaluate>
</dhv:include--%>
  	
  	<dhv:include name="organization.stage" none="true">
  <dhv:evaluate if="<%= OrgDetails.getStageId() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="account.stage">Stage</dhv:label>
      </td>
      <td>
        <%= StageList.getSelectedValue(OrgDetails.getStageId()) %> 
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<%if(hasText(OrgDetails.getTipoDest())) {%>
	<tr class="containerBody">
  		<td nowrap class="formLabel">
     		 <dhv:label name="">Attività</dhv:label>
    	</td>
    	<td>
    	 <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Autoveicolo")%>">
        Mobile
        </dhv:evaluate>
       
        <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Es. Commerciale")%>">
        Fissa
        </dhv:evaluate>
      		 <%--= toHtmlValue(OrgDetails.getTipoDest()) --%>&nbsp;
   		</td>
  	</tr>
  	<%} %>
  	<%if(hasText(OrgDetails.getCodiceCont())) {%>
   <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Codice Contenitore</dhv:label>
    </td><td>
       <%= toHtmlValue(OrgDetails.getCodiceCont()) %>&nbsp;
    </td></tr>
  	<%}else{} %>
  	<dhv:include name="organization.source" none="true">
  <dhv:evaluate if="<%= OrgDetails.getSource() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="contact.source">Source</dhv:label>
      </td>
      <td>
        <%= SourceList.getSelectedValue(OrgDetails.getSource()) %> 
        <dhv:evaluate if="<%= OrgDetails.getSource()==1 %>">
        &nbsp; dal <zeroio:tz timestamp="<%= OrgDetails.getDateI() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/><%--= (request.getParameter("dateI") ) --%> <zeroio:tz timestamp="<%= request.getParameter("dateI") %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/> al&nbsp; <zeroio:tz timestamp="<%= OrgDetails.getDateF() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
         <dhv:evaluate if="<%= OrgDetails.getCessazione()%>">
        Cessazione automatica
       </dhv:evaluate>
        </dhv:evaluate>
        
       
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>  

        <%--dhv:include name="organization.date1" none="true">
        <dhv:evaluate if="<%= (OrgDetails.getDate1() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.date1">Date1</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OrgDetails.getDate1() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
        <%= showAttribute(request, "contractEndDateError") %>
      </td>
    </tr>
    </dhv:evaluate>
    </dhv:include--%>  	

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
 <dhv:include name="organization.date1" none="true">
    <dhv:evaluate if="<%= (OrgDetails.getDate1() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Presentazione D.I.A./Inizio Attività</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OrgDetails.getDate1() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
      </td>
    </tr>
    </dhv:evaluate>
 </dhv:include> 
 <dhv:evaluate if="<%= (OrgDetails.getDate2() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data inizio attività</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getDate2() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>

  	<%--<dhv:include name="organization.contractEndDate" none="true">
<dhv:evaluate if="<%= hasText(OrgDetails.getContractEndDateString()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ContractEndDate">Contract End Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
</dhv:include>--%>
<%-- if(OrgDetails.getTipoDest().equals("Autoveicolo")){%>

<% if((OrgDetails.getAccountSize() > 0)    ){ %>
  <tr class="containerBody">
    <td name="accountSize1" id="accountSize1" nowrap class="formLabel">
      <dhv:label name="osa.categoriaRischio"/>
    </td>
    <td>
      <%= OrgCategoriaRischioList.getSelectedValue(OrgDetails.getAccountSize()) %>
    </td>
  </tr>
<% }%>


/*Per osa appena inserito la categoria di rischio nn sarà più - ma 3*/

if((OrgDetails.getLivelloRischioFinale() > -1) && OrgDetails.getDataAudit()!=null) {%>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="osa.livelloRischio">Punteggio Totale</dhv:label>
      </td>
      <td>
       <%= OrgDetails.getLivelloRischioFinale() %>&nbsp; al <%= (new SimpleDateFormat( "dd/MM/yyyy" )).format(OrgDetails.getDataAudit()) %>
      </td>
    </tr>
    <%} %>
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Categoria di Rischio</dhv:label>
      </td>
      <td>
      <%
      Integer supp = OrgDetails.getLivelloRischioFinale();
      Integer supp2 = OrgDetails.getLivelloRischio();
      %>
       <%=(((OrgDetails.getLivelloRischioFinale()>= 1) &&(OrgDetails.getLivelloRischioFinale()<= 100)) ? (" 1 ") : 
    	   ((OrgDetails.getLivelloRischioFinale()<= 200) && ((OrgDetails.getLivelloRischioFinale()>= 101)) ? (" 2 ") : 
    	   ((OrgDetails.getLivelloRischioFinale()<= 300) && ((OrgDetails.getLivelloRischioFinale()>= 201)) ? (" 3 ") : 
    	   ((OrgDetails.getLivelloRischioFinale()<= 400) && ((OrgDetails.getLivelloRischioFinale()>= 301)) ? (" 4 ") : 
    	   ((OrgDetails.getLivelloRischioFinale()>= 401) ? (" 5 ") : ((((OrgDetails.getLivelloRischioFinale() < 1)&&(OrgDetails.getLivelloRischio() < 1))||(supp == null && supp2 == null)) ? (" 3 ") : ("-"))))))) %>
      </td>
    </tr>
   
   <tr class="containerBody" style="display:none">
      <td nowrap class="formLabel">
        <dhv:label name="osa.livelloRischio"/>
      </td>
      <td>
       -&nbsp;
      </td>
    </tr>
     <tr class="containerBody" style="display:none">
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Categoria di Rischio</dhv:label>
      </td>
      <td>
       -&nbsp;
      </td>
    </tr>
    <% %> SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String data = sdf.format(new java.util.Date());  %>
    <% if((OrgDetails.getAccountSize() > 0)  && (OrgDetails.getLivelloRischioFinale()== -1)  ){ %>
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Prossimo C.U. con la tecnica della sorveglianza</dhv:label>
      </td>
      <td>
        <%= data %>       
      </td>
    </tr>
    <%}else{ %>
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Prossimo C.U. con la tecnica della sorveglianza</dhv:label>
      </td>
      <td>
        <% if(((OrgDetails.getLivelloRischioFinale()< 1)&&(OrgDetails.getLivelloRischio()< 1))||(supp==null)){%><dhv:label name="" ><%= data %></dhv:label><%} %>
        <% if((OrgDetails.getLivelloRischioFinale()>= 1) && (OrgDetails.getLivelloRischioFinale()<= 100)){%><%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 48))%><%} %>
        <% if ((OrgDetails.getLivelloRischioFinale()<= 200) && (OrgDetails.getLivelloRischioFinale()>= 101)) {%> <%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 36 ))%><%} %>
    	<% if ((OrgDetails.getLivelloRischioFinale()<= 300) && (OrgDetails.getLivelloRischioFinale()>= 201)) {%> <%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 24 ))%><%} %>
    	<% if ((OrgDetails.getLivelloRischioFinale()<= 400) && (OrgDetails.getLivelloRischioFinale()>= 301)) {%> <%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 12 ))%><%} %>
    	<% if ((OrgDetails.getLivelloRischioFinale()> 401)) {%><%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 6 ))%> <%} %>
       
       
       <%//="OrgDetails.getDataAudit()" %>
       
       
      </td>
    </tr>
  
    <%} %>
    <%} --%>
  <%--<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.owner">Account Owner</dhv:label>
    </td>
    <td>
      <dhv:permission name="contacts-internal_contacts-view" none="true">
        <dhv:username id="<%= OrgDetails.getOwner() %>" />
      </dhv:permission>
      <dhv:permission name="contacts-internal_contacts-view">
        <a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%= UserUtils.getUserContactId(request, OrgDetails.getOwner()) %>"><dhv:username id="<%= OrgDetails.getOwner() %>" /></a>
      </dhv:permission>
      <dhv:evaluate if="<%= !(OrgDetails.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
    </td>
  </tr>--%>
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
  <tr class="containerBody">
	<td nowrap class="formLabel">
      	<dhv:label name="">Stato Impresa</dhv:label>
	</td>
	<td>
	<%
		if(OrgDetails.getSource()== 1){%>
         <%= ((OrgDetails.getDateF()!= null && OrgDetails.getDateF().before(d))  ? ("Cessato in data ") : ("In Attività")) %>&nbsp;
		<%if((OrgDetails.getDateF()!= null) && (OrgDetails.getDateF().before(d))){%>
		<zeroio:tz timestamp="<%= OrgDetails.getDateF() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/> ,di conseguenza  non più gestibile in modifica.	        
	  		<%} %>
		<%}else{
	%>
         <%= ((OrgDetails.getCessato()== 1 ) ? ("Cessato in data ") : ((OrgDetails.getCessato()==0)?("In Attività"):("Sospeso in data "))) %>&nbsp;
	


<% if((OrgDetails.getCessato()==1) || (OrgDetails.getCessato()==2)) {%>
  
    
      <zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>  <%=((OrgDetails.getCessato()==1) ? (",di conseguenza  non più gestibile in modifica.") : (""))%>
    
   
<%} }%>
</td>
</tr>
</table>
<br />

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
     
    </th>
  </tr>
<dhv:evaluate if="<%= (OrgDetails.getTitoloRappresentante()>0)||(Voltura.getTitoloRappresentante()>0) %>">
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Titolo</dhv:label>
    </td>
    <td class="containerBody"> 
       <%= TitoloList.getSelectedValue(((OrgDetails.getVoltura())?(Voltura.getTitoloRappresentante()):(OrgDetails.getTitoloRappresentante()))) %></td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= (hasText(OrgDetails.getCodiceFiscaleRappresentante()))||(hasText(Voltura.getCodiceFiscaleRappresentante())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Codice Fiscale
			</td>
			<td>
         	<%= ((OrgDetails.getVoltura())?(Voltura.getCodiceFiscaleRappresentante()):(OrgDetails.getCodiceFiscaleRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= (hasText(OrgDetails.getNomeRappresentante())) ||(hasText(Voltura.getNomeRappresentante())) %>">		
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Nome
			</td>
			<td>
         	<%= ((OrgDetails.getVoltura())?(Voltura.getNomeRappresentante()):(OrgDetails.getNomeRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
  	 <dhv:evaluate if="<%= (hasText(OrgDetails.getCognomeRappresentante()))||(hasText(Voltura.getCognomeRappresentante())) %>">
<tr class="containerBody">
			<td nowrap class="formLabel">
      			Cognome
			</td>
			<td>
         	<%= ((OrgDetails.getVoltura())?(Voltura.getCognomeRappresentante()):(OrgDetails.getCognomeRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>

<dhv:evaluate if="<%= (OrgDetails.getDataNascitaRappresentante() != null) || (Voltura.getDataNascitaRappresentante() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Nascita</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= ((OrgDetails.getVoltura())?(Voltura.getDataNascitaRappresentante()):(OrgDetails.getDataNascitaRappresentante())) %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
  	 
		<dhv:evaluate if="<%= (hasText(OrgDetails.getLuogoNascitaRappresentante()))||(hasText(Voltura.getLuogoNascitaRappresentante())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Comune di nascita</dhv:label>
			</td>
			<td>
         	<%= ((OrgDetails.getVoltura())?(Voltura.getLuogoNascitaRappresentante()):(OrgDetails.getLuogoNascitaRappresentante()))%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
	
	<dhv:evaluate if="<%= (hasText(OrgDetails.getEmailRappresentante()))||(hasText(Voltura.getEmailRappresentante())) %>">						
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Email</dhv:label>
			</td>
			<td>
         	<%= ((OrgDetails.getVoltura())?(Voltura.getEmailRappresentante()):(OrgDetails.getEmailRappresentante())) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
	<dhv:evaluate if="<%= (OrgDetails.getTelefonoRappresentante()!=null) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Telefono</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getTelefonoRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= (OrgDetails.getFax()!=null) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Fax</dhv:label>
			</td>
			<td>
         	<%= (OrgDetails.getFax()) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<!--  fine delle modifiche -->
		
</table>
<br>

<dhv:include name="organization.phoneNumbers" none="false">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.PhoneNumbers">Phone Numbers</dhv:label></strong>
	  </th>
  </tr>
<%  
  Iterator inumber = OrgDetails.getPhoneNumberList().iterator();
  if (inumber.hasNext()) {
    while (inumber.hasNext()) {
      OrganizationPhoneNumber thisPhoneNumber = (OrganizationPhoneNumber)inumber.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisPhoneNumber.getTypeName()) %>
      </td>
      <td>
        <%= toHtml(thisPhoneNumber.getPhoneNumber()) %>
        <dhv:evaluate if="<%=thisPhoneNumber.getPrimaryNumber()%>">        
          <dhv:label name="requestor.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate>&nbsp;
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E"><dhv:label name="contacts.NoPhoneNumbers">No phone numbers entered.</dhv:label></font>
      </td>
    </tr>
<%}%>

</table>
<br />
</dhv:include>
<dhv:include name="organization.addresses" none="true">
<%

int numLocali=0;
Iterator iaddress2 = OrgDetails.getAddressList().iterator();
while (iaddress2.hasNext()) {
    OrganizationAddress thisAddress = (OrganizationAddress)iaddress2.next();
    if(thisAddress.getType()==6){
    	numLocali++;
    }
    
}

%>
<%





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
	
	  <dhv:evaluate if="<%= thisAddress.getType() == 6 %>">
	   
	 <%if(locali==0){ 
	 locali++;
	 %>
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Locale funzionalmente collegato</dhv:label></strong><%} %>
	  </dhv:evaluate>  
	  
	  
	  <dhv:evaluate if="<%= ((thisAddress.getType() == 7) )%>">
	  <dhv:evaluate if="<%= (thisAddress.getStreetAddressLine1()!="") %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede attività mobile</dhv:label></strong>
	  </dhv:evaluate> 
	  </dhv:evaluate>
	   <%-- %> <strong><dhv:label name="requestor.requestor_add.Addressess"><%= toHtml(thisAddress.getTypeName()) %></dhv:label></strong>--%>
	  </th>
  </tr>
  
   <dhv:evaluate if="<%= ((thisAddress.getType() == 7) && (thisAddress.getStreetAddressLine1()!=""))%>">
  <dhv:evaluate if="<%= OrgDetails.getTipoStruttura()!= -1 %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Tipo Struttura</dhv:label>
			</td>
			<td>
				<%=TipoStruttura.getSelectedValue( OrgDetails.getTipoStruttura() ) %>
         		
			</td>
		</tr>
		  <dhv:evaluate if="<%= hasText(OrgDetails.getNomeCorrentista()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Targa/Codice Autoveicolo</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getNomeCorrentista()) %>&nbsp;
			</td>
		</tr> 
  	</dhv:evaluate> 

    <dhv:evaluate if="<%= hasText(OrgDetails.getContoCorrente()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Tipo Autoveicolo</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getContoCorrente()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate>  
  	    
  	</dhv:evaluate> 
  	</dhv:evaluate> 
 
  <dhv:evaluate if="<%=(thisAddress.getType()== 6)%>">
  <%numLocali--; %>
  <dhv:evaluate if="<%= OrgDetails.getTipoLocale()!= -1 %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Tipo Locale</dhv:label>
			</td>
			<td>
         		<%=TipoLocale.getSelectedValue( OrgDetails.getTipoLocale() ) %>
			</td>
		</tr>
  	</dhv:evaluate>  
  </dhv:evaluate>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <%= toHtml(thisAddress.getTypeName()) %>
      </td>
      <td>
        <%= toHtml(thisAddress.toString()) %>&nbsp;
        <dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">        
          <dhv:label name="requestor.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate>
      </td>
      <%if(numLocali==0){ %>
    </tr>
    
    
    </table><br>
<%}
    }
  } else {
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
    </table><br>
<%}%>


<br />
</dhv:include>
<dhv:include name="organization.emailAddresses" none="false">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="requestor.requestor_add.EmailAddresses">Email Addresses</dhv:label></strong>
	  </th>
  </tr>

<%
  Iterator iemail = OrgDetails.getEmailAddressList().iterator();
  if (iemail.hasNext()) {
    while (iemail.hasNext()) {
      OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress)iemail.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisEmailAddress.getTypeName()) %>
      </td>
      <td>
        <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>&nbsp;
        <dhv:evaluate if="<%=thisEmailAddress.getPrimaryEmail()%>">        
          <dhv:label name="requestor.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate>
      </td>
    </tr>
<%    
    }
  } else {
%>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E"><dhv:label name="contacts.NoEmailAdresses">No email addresses entered.</dhv:label></font>
      </td>
    </tr>
<%}%>

</table>
<br />
</dhv:include>


<br><br>




 <%if(hasText(OrgDetails.getNotes())){ %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="requestor.requestor_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
 
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <%=toHtml(OrgDetails.getNotes()) %>&nbsp;
    </td>
  </tr>
  
</table>
<br />
<%}
 
 
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
