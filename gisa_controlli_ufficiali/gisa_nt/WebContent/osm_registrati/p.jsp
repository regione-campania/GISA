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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.osmregistrati.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.osmregistrati.base.Organization" scope="request"/>
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
<a href="OsmRegistrati.do">OSM Registrati</a> > 
<% if (request.getParameter("return") == null) { %>
<a href="OsmRegistrati.do?command=Search"><dhv:label name="stabilimenti.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="OsmRegistrati.do?command=Dashboard">Cruscotto</a> >
<%}%>
<dhv:label name="osm.details">Account Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:permission name="osmregistrati-osmregistrati-report-view">
  <table width="100%" border="0">
    <tr>
     <%-- aggiunto da d.dauria--%>
     
      <td nowrap align="right">
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="OsmRegistrati.do?command=PrintReport&file=stabilimenti.xml&id=<%= OrgDetails.getId() %>"><dhv:label name="stabilimenti.osa.print">Stampa Scheda stabilimenti</dhv:label></a>
      </td>
      
      <%-- fine degli inserimenti --%>
    </tr>
  </table>
</dhv:permission>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>
<dhv:container name="osm" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="osmregistrati-osmregistrati-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='OsmRegistrati.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="osmregistrati-osmregistrati-edit"><input type="button" value="Modifica"	onClick="javascript:window.location.href='OsmRegistrati.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
  <%--
    <dhv:permission name="osmregistrati-osmregistrati-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='OsmRegistrati.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>--%>
  </dhv:evaluate>
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="osmregistrati-osmregistrati-delete"><input type="button" value="<dhv:label name="osm.osm.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('OsmRegistrati.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','OsmRegistrati.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
</dhv:evaluate>
<dhv:permission name="osmregistrati-osmregistrati-edit,osmregistrati-osmregistrati-delete"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="stabilimenti.stabilimenti_details.PrimaryInformation">Primary Information</dhv:label></strong>
    </th>
  </tr>
  
  <dhv:include name="osmregistrati-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.site">Site</dhv:label>
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
  
      <dhv:include name="osmregistrati-name" none="true">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="stabilimenti.stabilimenti_add.OrganizationName">Organization Name</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getName()) %>&nbsp;
       </td>
      </tr>
    </dhv:include>
  
  
    <dhv:evaluate if="<%= hasText(OrgDetails.getBanca()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Ragione Sociale Precedente</dhv:label>
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
<%--<dhv:evaluate if="<%= hasText(OrgDetails.getTipoStab()) %>">
   <tr class="containerBody">
      <td nowrap class="formLabel" name="tipoStab1" id="tipoStab1">
        <dhv:label name="organization.tipoStab">Account Number</dhv:label>
      </td>
      <td>
        <%= toHtmlValue(OrgDetails.getTipoStab()) %>
      </td>
    </tr>
 </dhv:evaluate>  --%> 
 <dhv:evaluate if="<%= hasText(OrgDetails.getNumAut())%>" >
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Numero di Registrazione</dhv:label>
      </td>
      <td>
        <%= toHtmlValue(OrgDetails.getNumAut()) %>
      </td>
    </tr>
 </dhv:evaluate>
<dhv:evaluate if="<%= hasText(OrgDetails.getCategoria()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Sezione</dhv:label>
      </td>
      <td>
       <%= toHtmlValue(OrgDetails.getCategoria()) %>
      </td>
    </tr>
</dhv:evaluate> 
 <%-- <dhv:include name="organization.source" none="true">
   <tr>
      <td name="impianto1" id="impianto1" nowrap class="formLabel">
        <dhv:label name="">Attività</dhv:label>
      </td>
    <td>
      <%= Impianto.getHtmlSelect("impianto",OrgDetails.getImpianto()) %>
    </td>
  </tr>
  </dhv:include>--%> 
  
<dhv:evaluate if="<%= OrgDetails.getImpianto() > 0 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Attività</dhv:label>
    </td>
    <td> 
      <%= impianto.getSelectedValue(OrgDetails.getImpianto()) %>
      <input type="hidden" name="impianto" value="<%=OrgDetails.getImpianto()%>" >
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= OrgDetails.getImpianto() > 0 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Codice Impianto</dhv:label>
    </td>
    <td> 
      <%= (OrgDetails.getCodiceImpianto()) %>
      <input type="hidden" name="impianto" value="<%=OrgDetails.getImpianto()%>" >
    </td>
  </tr>
</dhv:evaluate>


<dhv:evaluate >
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Codice Sanco</dhv:label>
    </td>
    <td> 
      <%= OrgDetails.getCodiceImpianto()%>
      
    </td>
  </tr>
</dhv:evaluate>


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
      <dhv:label name="">Stato OSM</dhv:label>
    </td>
    <td> 
      <%= StatoLab.getSelectedValue(OrgDetails.getStatoLab()) %>
      <input type="hidden" name="siteId" value="<%=OrgDetails.getStatoLab()%>" >
    </td>
  </tr>
</dhv:evaluate> 


<dhv:evaluate if="<%= StatoOsm.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
</dhv:evaluate>
 <dhv:evaluate if="<%= OrgDetails.getTypes().size() > 0 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.osm.types">Account Type(s)</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getTypes().valuesAsString()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>--%>


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
         <dhv:label name="stabilimenti.stabilimenti_add.AlertDescription">Alert Description</dhv:label>
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
          <dhv:label name="stabilimenti.stabilimenti_add.sicDescription">SIC Description</dhv:label>
		</td>
		<td>
         <%= toHtml(OrgDetails.getSicDescription()) %>&nbsp;
		</td>
	  </tr>
    </dhv:evaluate>
</dhv:include>
<%if(hasText(OrgDetails.getCodice1())) {%> 
    	<tr class="containerBody">
			 <td nowrap class="formLabel">
      <dhv:label name="">Codici Istat Secondari</dhv:label>  
   </td>   
    <td>
      		 <%--if((OrgDetails.getCodice1()!= null) && (!OrgDetails.getCodice1().equals(""))){ %>   
      		 Codice 1&nbsp;&nbsp;
      		 <%= toHtmlValue(OrgDetails.getCodice1()) %></br>
      		 <%}if((OrgDetails.getCodice2()!= null) && (!OrgDetails.getCodice2().equals(""))){ %>
      		 Codice 2&nbsp;&nbsp;
      		 <%= toHtmlValue(OrgDetails.getCodice2()) %></br>
      		  <%}if((OrgDetails.getCodice3()!= null) && (!OrgDetails.getCodice3().equals(""))){ %>
      		 Codice 3&nbsp;&nbsp;
      		 <%= toHtmlValue(OrgDetails.getCodice3()) %></br>
      		  <%}if((OrgDetails.getCodice4()!= null) && (!OrgDetails.getCodice4().equals(""))){ %>
      		 Codice 4&nbsp;&nbsp;
      		  <%= toHtmlValue(OrgDetails.getCodice4()) %></br>
      		  <%}if((OrgDetails.getCodice5()!= null) && (!OrgDetails.getCodice5().equals(""))){ %>
      		 Codice 5&nbsp;&nbsp; 
      		 <%= toHtmlValue(OrgDetails.getCodice5()) %></br>
      		  <%}if((OrgDetails.getCodice6()!= null) && (!OrgDetails.getCodice6().equals(""))){ %>
			 Codice 6&nbsp;&nbsp;
			 <%= toHtmlValue(OrgDetails.getCodice6()) %></br>
      		  <%}if((OrgDetails.getCodice7()!= null) && (!OrgDetails.getCodice7().equals(""))){ %>
			 Codice 7&nbsp;&nbsp;
			 <%= toHtmlValue(OrgDetails.getCodice7()) %></br>
      		  <%}if((OrgDetails.getCodice8()!= null) && (!OrgDetails.getCodice8().equals(""))){ %>
			 Codice 8&nbsp;&nbsp;
			 <%= toHtmlValue(OrgDetails.getCodice8()) %></br>
      		  <%}if((OrgDetails.getCodice9()!= null) && (!OrgDetails.getCodice9().equals(""))){ %>
			 Codice 9&nbsp;&nbsp;
			 <%= toHtmlValue(OrgDetails.getCodice9()) %></br>
      		  <%}if((OrgDetails.getCodice10()!= null) && (!OrgDetails.getCodice10().equals(""))){ %>
	         Codice 10
      		 <%= toHtmlValue(OrgDetails.getCodice10()) %></br>
      		 <%} --%>
      		 <%if((OrgDetails.getCodice1()!= null) && (!OrgDetails.getCodice1().equals(""))){ %>   
      		 Codice 1&nbsp;&nbsp;
      		 <%= toHtmlValue(OrgDetails.getCodice1()) %>
      		 <%= request.getAttribute("codice1") %></br>
      		 <%}if((OrgDetails.getCodice2()!= null) && (!OrgDetails.getCodice2().equals(""))){ %>
      		 Codice 2&nbsp;&nbsp;
      		 <%= toHtmlValue(OrgDetails.getCodice2()) %>
      		 <%= request.getAttribute("codice2") %></br>
      		  <%}if((OrgDetails.getCodice3()!= null) && (!OrgDetails.getCodice3().equals(""))){ %>
      		 Codice 3&nbsp;&nbsp;
      		 <%= toHtmlValue(OrgDetails.getCodice3()) %>
      		 <%= request.getAttribute("codice3") %></br>
      		  <%}if((OrgDetails.getCodice4()!= null) && (!OrgDetails.getCodice4().equals(""))){ %>
      		 Codice 4&nbsp;&nbsp;
      		  <%= toHtmlValue(OrgDetails.getCodice4()) %>
      		  <%= request.getAttribute("codice4") %></br>
      		  <%}if((OrgDetails.getCodice5()!= null) && (!OrgDetails.getCodice5().equals(""))){ %>
      		 Codice 5&nbsp;&nbsp; 
      		 <%= toHtmlValue(OrgDetails.getCodice5()) %>
      		 <%= request.getAttribute("codice5") %></br>
      		  <%}if((OrgDetails.getCodice6()!= null) && (!OrgDetails.getCodice6().equals(""))){ %>
			 Codice 6&nbsp;&nbsp;
			 <%= toHtmlValue(OrgDetails.getCodice6()) %>
			 <%= request.getAttribute("codice6") %></br>
      		  <%}if((OrgDetails.getCodice7()!= null) && (!OrgDetails.getCodice7().equals(""))){ %>
			 Codice 7&nbsp;&nbsp;
			 <%= toHtmlValue(OrgDetails.getCodice7()) %>
			 <%= request.getAttribute("codice7") %></br>
      		  <%}if((OrgDetails.getCodice8()!= null) && (!OrgDetails.getCodice8().equals(""))){ %>
			 Codice 8&nbsp;&nbsp;
			 <%= toHtmlValue(OrgDetails.getCodice8()) %>
			 <%= request.getAttribute("codice8") %></br>
      		  <%}if((OrgDetails.getCodice9()!= null) && (!OrgDetails.getCodice9().equals(""))){ %>
			 Codice 9&nbsp;&nbsp;
			 <%= toHtmlValue(OrgDetails.getCodice9()) %>
			 <%= request.getAttribute("codice9") %></br>
      		  <%}if((OrgDetails.getCodice10()!= null) && (!OrgDetails.getCodice10().equals(""))){ %>
	         Codice 10
      		 <%= toHtmlValue(OrgDetails.getCodice10()) %>
      		 <%= request.getAttribute("codice10") %></br>
      		 <%} %>
     </td>
		</tr>
		<%}else{} %>
  	<%--<dhv:evaluate if="<%= hasText(OrgDetails.getAbi()) %>">
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
          <dhv:label name="stabilimenti.stabilimenti_add.cab">CAB</dhv:label>
		</td>
		<td>
         <%= toHtml(OrgDetails.getCab()) %>&nbsp;
		</td>
	  </tr>
    </dhv:evaluate>--%>
<dhv:include name="organization.url" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getUrl()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.stabilimenti_add.WebSiteURL">Web Site URL</dhv:label>
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
        <dhv:label name="stabilimenti.stabilimenti_add.Industry">Industry</dhv:label>
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
      <dhv:label name="stabilimenti.stabilimenti_add.duns_type">DUNS Type</dhv:label>
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
      <dhv:label name="stabilimenti.stabilimenti_add.Potential">Potential</dhv:label>
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
        <dhv:label name="stabilimenti.stabilimenti_add.Revenue">Revenue</dhv:label>
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
        <dhv:label name="stabilimenti.stabilimenti_add.TickerSymbol">Ticker Symbol</dhv:label>
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
      <dhv:label name="stabilimenti.stabilimenti_add.duns_number">DUNS Number</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getDunsNumber()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.businessNameTwo" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getBusinessNameTwo()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.business_name_two">Business Name 2</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getBusinessNameTwo()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<%--
<dhv:include name="organization.sicCode" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getSicCode() > -1) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.sic_code">SIC</dhv:label>
    </td><td>
       <%= SICCodeList.getDescriptionByCode(OrgDetails.getSicCode()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
--%>
<dhv:include name="osmregistrati-size" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getAccountSizeName()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.stabilimenti_add.accountSize">Account Size</dhv:label>
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
        <dhv:label name="osm.osm_add.segment">Segment</dhv:label>
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
        <dhv:label name="stabilimenti.stage">Stage</dhv:label>
      </td>
      <td>
        <%= StageList.getSelectedValue(OrgDetails.getStageId()) %> 
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>		

<dhv:evaluate if="<%= OrgDetails.getStatoLab() !=  -1 %>">
  <tr class="containerBody">
    <td name="statoLab1" id="statoLab1" nowrap class="formLabel">
      <dhv:label name="">Stato OSM</dhv:label>
    </td>
    <td>
      <%= statoLab.getSelectedValue(OrgDetails.getStatoLab()) %>
      <input type="hidden" name="statoLab" value="<%=OrgDetails.getStatoLab()%>">
    </td>
  </tr>
</dhv:evaluate>
  	
<dhv:include name="organization.date1" none="true">
        <dhv:evaluate if="<%= (OrgDetails.getDate1() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.stabilimenti_add.date1">Date1</dhv:label>
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

<% if(OrgDetails.getAccountSize() > 0){ %>
  <tr class="containerBody">
    <td name="accountSize1" id="accountSize1" nowrap class="formLabel">
      <dhv:label name="osa.categoriaRischio"/>
    </td>
    <td>
      <%= OrgCategoriaRischioList.getSelectedValue(OrgDetails.getAccountSize()) %>
    </td>
  </tr>
<% }
if((OrgDetails.getLivelloRischioFinale() > -1 )) {%>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio">Punteggio Totale</dhv:label>
      </td>
      <td>
     
       <%= OrgDetails.getLivelloRischioFinale() %>&nbsp; al <%= (new SimpleDateFormat( "dd/MM/yyyy" )).format(OrgDetails.getDataAudit()) %>
      </td>
    </tr>
    <dhv:evaluate if="<%= OrgDetails.getAccountSize() != -1 %>">
</dhv:evaluate>
<%} %>
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Categoria di Rischio</dhv:label>
      </td>
      <td>
       <%
      Integer supp = OrgDetails.getLivelloRischioFinale(); 
      %>
      <%= 
    	   (((OrgDetails.getLivelloRischioFinale() >= 1) && (OrgDetails.getLivelloRischioFinale() <= 100)) ? (" 1 ") : 
    	   (((OrgDetails.getLivelloRischioFinale() <= 200) && ((OrgDetails.getLivelloRischioFinale() >= 101))) ? (" 2 ") : 
    	   (((OrgDetails.getLivelloRischioFinale() <= 300) && ((OrgDetails.getLivelloRischioFinale() >= 201))) ? (" 3 ") : 
    	   (((OrgDetails.getLivelloRischioFinale() <= 400) && ((OrgDetails.getLivelloRischioFinale() >= 301))) ? (" 4 ") : 
    	   ((OrgDetails.getLivelloRischioFinale() >= 401) ? (" 5 ") : 
    	   ((((OrgDetails.getLivelloRischioFinale() < 1) && (OrgDetails.getLivelloRischio()<1))||(supp == null) ? (" 3 ") : ("-")))))))) %>
      </td>
    </tr>
   <%//}else {%>
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
      <% if((OrgDetails.getAccountSize() > 0)  && (OrgDetails.getLivelloRischioFinale()== -1)  ){ %>
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Prossimo C.U. con la tecnica della sorveglianza</dhv:label>
      </td>
      <td>
        Checklist assegnata ma livello rischio non ancora calcolato.       
      </td>
    </tr>
    <%}else{ %>
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Prossimo C.U. con la tecnica della sorveglianza</dhv:label>
      </td>
      <td>
        <% if(((OrgDetails.getLivelloRischioFinale()< 1)&&(OrgDetails.getLivelloRischio()< 1))||(supp==null)){%><dhv:label name="" >Nessuna Check List</dhv:label><%} %>
        <% if((OrgDetails.getLivelloRischioFinale()>= 1) && (OrgDetails.getLivelloRischioFinale()<= 100)){%><%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 48))%><%} %>
        <% if ((OrgDetails.getLivelloRischioFinale()<= 200) && (OrgDetails.getLivelloRischioFinale()>= 101)) {%> <%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 36 ))%><%} %>
    	<% if ((OrgDetails.getLivelloRischioFinale()<= 300) && (OrgDetails.getLivelloRischioFinale()>= 201)) {%> <%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 24 ))%><%} %>
    	<% if ((OrgDetails.getLivelloRischioFinale()<= 400) && (OrgDetails.getLivelloRischioFinale()>= 301)) {%> <%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 12 ))%><%} %>
    	<% if ((OrgDetails.getLivelloRischioFinale()> 401)) {%><%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 6 ))%> <%} %>
       
       
       <%//="OrgDetails.getDataAudit()" %>
       
       
      </td>
    </tr>
  
    <%} %>
    <%-- %>tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Prossimo C.U. con la tecnica della sorveglianza</dhv:label>
      </td>
      <td>
      
       <% if(((OrgDetails.getLivelloRischioFinale()< 1)&&(OrgDetails.getLivelloRischio()< 1))||(supp==null)){%><dhv:label name="" >Nessuna Check List</dhv:label><%} %>
       <% if((OrgDetails.getLivelloRischioFinale()>= 1) && (OrgDetails.getLivelloRischioFinale()<= 100)){%><%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 48))%><%} %>
       <% if ((OrgDetails.getLivelloRischioFinale()<= 200) && (OrgDetails.getLivelloRischioFinale()>= 101)) {%> <%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 36 ))%><%} %>
    	 <% if ((OrgDetails.getLivelloRischioFinale()<= 300) && (OrgDetails.getLivelloRischioFinale()>= 201)) {%> <%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 24 ))%><%} %>
    	  <% if ((OrgDetails.getLivelloRischioFinale()<= 400) && (OrgDetails.getLivelloRischioFinale()>= 301)) {%> <%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 12 ))%><%} %>
    	   <% if ((OrgDetails.getLivelloRischioFinale()> 401)) {%><%=(new SimpleDateFormat( "dd/MM/yyyy" )).format(org.aspcfs.utils.DateAudit.addMonth( OrgDetails.getDataAudit(), 6 ))%> <%} %>
       
       
       <%//="OrgDetails.getDataAudit()" %>
       
       
      </td>
    </tr--%>
   <%--} --%>
    
  	<dhv:include name="organization.contractEndDate" none="true">
<dhv:evaluate if="<%= hasText(OrgDetails.getContractEndDateString()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.ContractEndDate">Contract End Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
<%--<dhv:evaluate if="<%= OrgDetails.getAccountSize() != -1 %>">
  <tr class="containerBody">
    <td name="accountSize1" id="accountSize1" nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.accountSize">Account Size</dhv:label>
    </td>
    <td>
      <%= OrgCategoriaRischioList.getSelectedValue(OrgDetails.getAccountSize()) %>
    </td>
  </tr>
</dhv:evaluate>--%>
<dhv:evaluate if="<%= (OrgDetails.getYearStarted() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.stabilimenti_add.year_started">Year Started</dhv:label>
      </td>
      <td>
       <%= OrgDetails.getYearStarted() %>&nbsp;
      </td>
    </tr>
</dhv:evaluate>



</dhv:include>
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
  	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
     
    </th>
  </tr>
<dhv:evaluate if="<%= (OrgDetails.getTitoloRappresentante()>0) %>">
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Titolo</dhv:label>
    </td>
    <td class="containerBody"> 
       <%= TitoloList.getSelectedValue(OrgDetails.getTitoloRappresentante()) %></td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscaleRappresentante()) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Codice Fiscale
			</td>
			<td>
         	<%= OrgDetails.getCodiceFiscaleRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
  	 <dhv:evaluate if="<%= hasText(OrgDetails.getCognomeRappresentante()) %>">
<tr class="containerBody">
			<td nowrap class="formLabel">
      			Cognome
			</td>
			<td>
         	<%= OrgDetails.getCognomeRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>

  	 <dhv:evaluate if="<%= hasText(OrgDetails.getNomeRappresentante()) %>">		
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Nome
			</td>
			<td>
         	<%= OrgDetails.getNomeRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
	<dhv:evaluate if="<%= (OrgDetails.getDataNascitaRappresentante() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Nascita</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getDataNascitaRappresentante() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
  	 
		<dhv:evaluate if="<%= hasText(OrgDetails.getLuogoNascitaRappresentante()) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Comune di nascita</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getLuogoNascitaRappresentante()%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
	<dhv:evaluate if="<%= hasText(OrgDetails.getEmailRappresentante()) %>">						
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Email</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getEmailRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
	<dhv:evaluate if="<%= hasText(OrgDetails.getTelefonoRappresentante()) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Telefono</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getTelefonoRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
</table>
<br>
<dhv:include name="organization.phoneNumbers" none="true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="stabilimenti.stabilimenti_add.PhoneNumbers">Phone Numbers</dhv:label></strong>
	  </th>
  </tr>
<dhv:evaluate if="<%=(OrgDetails.getPrimaryContact() == null)%>">
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
          <dhv:label name="stabilimenti.primary.brackets">(Primary)</dhv:label>
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
</dhv:evaluate>
<dhv:evaluate if="<%=(OrgDetails.getPrimaryContact() != null)%>">
<%  
  Iterator inumber = OrgDetails.getPrimaryContact().getPhoneNumberList().iterator();
  if (inumber.hasNext()) {
    while (inumber.hasNext()) {
      ContactPhoneNumber thisPhoneNumber = (ContactPhoneNumber)inumber.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisPhoneNumber.getTypeName()) %>
      </td>
      <td width=100%>
        <%= toHtml(thisPhoneNumber.getPhoneNumber()) %>&nbsp;
        <dhv:evaluate if="<%=thisPhoneNumber.getPrimaryNumber()%>">        
          <dhv:label name="stabilimenti.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate>
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
</dhv:evaluate>
</table>
<br />
</dhv:include>
<dhv:include name="organization.addresses" none="true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="stabilimenti.stabilimenti_add.Addresses">Addresses</dhv:label></strong>
	  </th>
  </tr>
<dhv:evaluate if="<%=(OrgDetails.getPrimaryContact() == null)%>">
<%  
  Iterator iaddress = OrgDetails.getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <%= toHtml(thisAddress.getTypeName()) %>
      </td>
      <td>
        <%= toHtml(thisAddress.toString()) %>&nbsp;
        <dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">        
          <dhv:label name="stabilimenti.primary.brackets">(Primary)</dhv:label>
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
</dhv:evaluate>
<dhv:evaluate if="<%=(OrgDetails.getPrimaryContact() != null)%>">
<%  
  Iterator iaddress = OrgDetails.getPrimaryContact().getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      ContactAddress thisAddress = (ContactAddress)iaddress.next();
%>
    <tr class="containerBody">
      <td valign="top" nowrap class="formLabel">
        <%= toHtml(thisAddress.getTypeName()) %>
      </td>
      <td>
        <%= toHtml(thisAddress.toString()) %>&nbsp;
        <dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">        
          <dhv:label name="stabilimenti.primary.brackets">(Primary)</dhv:label>
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
</dhv:evaluate>
</table>
<br />
</dhv:include>
<dhv:include name="organization.emailAddresses" none="true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="stabilimenti.stabilimenti_add.EmailAddresses">Email Addresses</dhv:label></strong>
	  </th>
  </tr>
<dhv:evaluate if="<%=(OrgDetails.getPrimaryContact() == null)%>">
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
          <dhv:label name="stabilimenti.primary.brackets">(Primary)</dhv:label>
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
</dhv:evaluate>
<dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() != null) %>">
<%
  Iterator iemail = OrgDetails.getPrimaryContact().getEmailAddressList().iterator();
  if (iemail.hasNext()) {
    while (iemail.hasNext()) {
      ContactEmailAddress thisEmailAddress = (ContactEmailAddress)iemail.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%= toHtml(thisEmailAddress.getTypeName()) %>
      </td>
      <td>
        <a href="mailto:<%= toHtml(thisEmailAddress.getEmail()) %>"><%= toHtml(thisEmailAddress.getEmail()) %></a>
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
</dhv:evaluate>
</table>
<br />
</dhv:include>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="stabilimenti.stabilimenti_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimentiasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <%=toHtml(OrgDetails.getNotes()) %>&nbsp;
    </td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="stabilimenti.stabilimenti_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_calls_list.Entered">Entered</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= OrgDetails.getEnteredBy() %>" />
      <zeroio:tz timestamp="<%= OrgDetails.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_contacts_calls_details.Modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= OrgDetails.getModifiedBy() %>" />
      <zeroio:tz timestamp="<%= OrgDetails.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
</table>
<dhv:permission name="osmregistrati-osmregistrati-edit,osmregistrati-osmregistrati-delete"><br></dhv:permission>
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="osmregistrati-osmregistrati-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='OsmRegistrati.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="osmregistrati-osmregistrati-edit"><input type="button" value="Modifica"	onClick="javascript:window.location.href='OsmRegistrati.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  <%--<dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="osmregistrati-osmregistrati-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='OsmRegistrati.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>--%>
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="osmregistrati-osmregistrati-delete"><input type="button" value="<dhv:label name="osm.osm.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('OsmRegistrati.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','OsmRegistrati.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
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
