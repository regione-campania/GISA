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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<%@page import="org.aspcfs.modules.audit.base.Audit"%>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
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
<%} %>
<body>
<%--form name="addAccount" action="Accounts.do?command=UpdateCatRischio&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" method="post"--%>
<form name="addAccount" action="Accounts.do?command=UpdateCatRischio&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" method="post">
<%
  boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }
%>
<dhv:evaluate if="<%= !popUp %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Stabilimenti 852</dhv:label></a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="Accounts.do?command=Dashboard">Cruscotto</a> >
	<%} %>
<%} else {%>
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Scheda Stabilimento 852</dhv:label></a> >
<%}%>
<a href="AccountsAudit.do?command=List&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.Audit">Check List</dhv:label></a> >
<dhv:label name="accountsa.details">Scegli il tipo di Check List</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>

<% String param1 = "orgId=" + OrgDetails.getOrgId();
   %>
<dhv:container name="accounts" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="accounts-tipochecklist-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='Accounts.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>
 <input type="hidden" name="onlyWarnings" value=<%=(OrgDetails.getOnlyWarnings()?"on":"off")%> />
  <input type="submit" value="<dhv:label name="globala.button.update">Prosegui</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=Search';this.form.dosubmit.value='false';" />
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="statusId" value="<%=OrgDetails.getStatusId()%>">
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
<br />
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <%--tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
    </th>
  </tr--%>
  
  <%--<dhv:include name="accounts-sites" none="true">
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
  	<dhv:evaluate if="<%= hasText(OrgDetails.getAbi()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Codice Istat Secondario</dhv:label>
			</td>
			<td>
        		<%= toHtml(OrgDetails.getAbi()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate>  
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
</dhv:include>--%>
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
<%--<dhv:include name="accounts-size" none="true">
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
  	<dhv:include name="organization.source" none="true">
  <dhv:evaluate if="<%= OrgDetails.getSource() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="contact.source">Source</dhv:label>
      </td>
      <td>
        <%= SourceList.getSelectedValue(OrgDetails.getSource()) %> 
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
  	
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

  	
        <dhv:include name="organization.date1" none="true">
        <dhv:evaluate if="<%= (OrgDetails.getDate1() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Inizio Attività</dhv:label>
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
      <dhv:label name="">Data inizio attività</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getDate2() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>

  	<dhv:include name="organization.contractEndDate" none="true">
<dhv:evaluate if="<%= hasText(OrgDetails.getContractEndDateString()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.ContractEndDate">Contract End Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>--%>


<tr class="containerBody">
    <td name="accountSize1" id="accountSize1" nowrap class="formLabel">
      <dhv:label name="osa.categoriaRischioo"/>Seleziona il tipo Check List
    </td>
    <td>
      <%= OrgCategoriaRischioList.getHtmlSelect("accountSize",OrgDetails.getAccountSize()) %>
    </td>
  </tr>
<%--
if(OrgDetails.getLivelloRischioFinale() > -1) {%>
<dhv:evaluate if="<%= (OrgDetails.getLivelloRischioFinale() > -1) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="osa.livelloRischio"/>
      </td>
      <td>
       <%= OrgDetails.getLivelloRischioFinale() %>&nbsp; al <%= (new SimpleDateFormat( "dd/MM/yyyy" )).format(OrgDetails.getDataAudit()) %>
      </td>
    </tr>
   </dhv:evaluate>
   <%}else {%>
   <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="osa.livelloRischio"/>
      </td>
      <td>
       -&nbsp;
      </td>
    </tr>
   <%} %>
</dhv:include>--%>
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
</table>
<br />
  <input type="hidden" name="onlyWarnings" value=<%=(OrgDetails.getOnlyWarnings()?"on":"off")%> />
  <input type="submit" value="<dhv:label name="globala.button.update">Prosegui</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=Search';this.form.dosubmit.value='false';" />
 <input type="hidden" name="dosubmit" value="true">
 <input type="hidden" name="statusId" value="<%=OrgDetails.getStatusId()%>">
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
</dhv:container>
</form>
