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
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>
<%@page import="java.sql.*"%>


<%@page import="org.jfree.data.DataUtilities"%>

<jsp:useBean id="SoggettoDettagli" class="org.aspcfs.modules.opu.base.SoggettoFisico" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Titolo" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>





<body onLoad=""> 




<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Soggetto.do"><dhv:label name="">Soggetto</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Soggetto.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Soggetto.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<%}%>
<dhv:label name="">Dettagli soggetto</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<% String param1 = "sogId=" + SoggettoDettagli.getIdSoggetto();
   %>


<dhv:container name="soggetto" selected="details" object="SoggettoDettagli" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="">


<dhv:permission name="accounts-accounts-edit"><input type="button"  value="<dhv:label name="global.button.modify">Modify</dhv:label>"	onClick="javascript:window.location.href='Soggetto.do?command=Modify&sogId=<%= SoggettoDettagli.getIdSoggetto() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
    </th>
  </tr>
  
  
<dhv:include name="accounts-name" none="true">
	<dhv:evaluate if="<%= hasText((Titolo.getSelectedValue(SoggettoDettagli.getIdTitolo()))) %>">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Titolo</dhv:label>
        </td>
        <td>
			 <%= toHtmlValue(  Titolo.getSelectedValue(SoggettoDettagli.getIdTitolo())) %>
       </td>
      </tr>
      </dhv:evaluate>
</dhv:include> 

<dhv:include name="accounts-name" none="true">
	<dhv:evaluate if="<%= hasText((SoggettoDettagli.getNome())) %>">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Nome</dhv:label>
        </td>
        <td>
			 <%= toHtmlValue(SoggettoDettagli.getNome()) %>
       </td>
      </tr>
      </dhv:evaluate>
</dhv:include>  

<dhv:include name="accounts-name" none="true">
	<dhv:evaluate if="<%= hasText((SoggettoDettagli.getCognome())) %>">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Cognome</dhv:label>
        </td>
        <td>
			 <%= toHtmlValue(SoggettoDettagli.getCognome()) %>
       </td>
      </tr>
	</dhv:evaluate>
</dhv:include> 


<dhv:include name="accounts-name" none="true">
	<dhv:evaluate if="<%= hasText((SoggettoDettagli.getCodFiscale())) %>">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Codice fiscale</dhv:label>
        </td>
        <td>
			 <%= toHtmlValue(SoggettoDettagli.getCodFiscale()) %>
       </td>
      </tr>
	</dhv:evaluate>
</dhv:include> 



<dhv:include name="accounts-name" none="true">
	<dhv:evaluate if="<%= hasText((SoggettoDettagli.getComuneNascita())) %>">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Comune nascita</dhv:label>
        </td>
        <td>
			 <%= toHtmlValue(SoggettoDettagli.getComuneNascita()) %>
       </td>
      </tr>
	</dhv:evaluate>
</dhv:include> 


<dhv:include name="accounts-name" none="true">
	<dhv:evaluate if="<%= hasText((SoggettoDettagli.getProvinciaNascita())) %>">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Provincia nascita</dhv:label>
        </td>
        <td>
			 <%= toHtmlValue(SoggettoDettagli.getProvinciaNascita()) %>
       </td>
      </tr>
	</dhv:evaluate>
</dhv:include> 

   <td nowrap class="formLabel" name="orgname1" id="orgname1">
     <dhv:label name="">Data di nascita</dhv:label>
   </td>
   <td>
      <zeroio:tz timestamp="<%= SoggettoDettagli.getDataNascita() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
   </td>
	
	
	
</table>
<br />



<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni indirizzo</dhv:label></strong>
     
    </th>
  </tr>

<dhv:evaluate if="<%= (hasText(SoggettoDettagli.getIndirizzo().getStreetAddressLine1())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Via</dhv:label>
			</td>
			<td>
         	<%= toHtml((SoggettoDettagli.getIndirizzo().getStreetAddressLine1()) )%>&nbsp; 
			</td>
		</tr>
</dhv:evaluate>

<dhv:evaluate if="<%= (hasText(SoggettoDettagli.getIndirizzo().getCountry())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Comune</dhv:label>
			</td>
			<td>
         	<%= toHtml((SoggettoDettagli.getIndirizzo().getCountry()) )%>&nbsp; 
			</td>
		</tr>
</dhv:evaluate>

<dhv:evaluate if="<%= (hasText(SoggettoDettagli.getIndirizzo().getCity())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Provincia</dhv:label>
			</td>
			<td>
         	<%= toHtml((SoggettoDettagli.getIndirizzo().getCity()) )%>&nbsp; 
			</td>
		</tr>
</dhv:evaluate>
</table>
<br>



 


</dhv:container>



</body>