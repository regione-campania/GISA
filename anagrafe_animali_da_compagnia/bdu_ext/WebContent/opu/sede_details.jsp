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


<%@page import="org.jfree.data.DataUtilities"%>

<jsp:useBean id="IndirizzoDettagli" class="org.aspcfs.modules.opu.base.Indirizzo" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>





<body onLoad=""> 




<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sede.do"><dhv:label name="">Sede</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Sede.do?command=Search"><dhv:label name="sede.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Sede.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<%}%>
<dhv:label name="">Dettagli sede</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<% String param1 = "sogId=" + IndirizzoDettagli.getIdIndirizzo();
   %>


<dhv:container name="sede" selected="details" object="IndirizzoDettagli" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="">

<!--  
<dhv:permission name="accounts-accounts-edit"><input type="button"  value="<dhv:label name="global.button.modify">Modify</dhv:label>"	onClick="javascript:window.location.href='Sede.do?command=Modify&sogId=<%=  IndirizzoDettagli.getIdIndirizzo() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
-->
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
    </th>
  </tr>
  
  
<dhv:include name="accounts-name" none="true">
	<dhv:evaluate if="<%= hasText( ComuniList.getSelectedValue(IndirizzoDettagli.getComune())) %>">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Comune</dhv:label>
        </td>
        <td>
			 <%= toHtmlValue(ComuniList.getSelectedValue(IndirizzoDettagli.getComune())) %>
       </td>
      </tr>
      </dhv:evaluate>
</dhv:include> 

<dhv:include name="accounts-name" none="true">
	<dhv:evaluate if="<%= hasText((IndirizzoDettagli.getCap())) %>">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Cap</dhv:label>
        </td>
        <td>
			 <%= toHtmlValue(IndirizzoDettagli.getCap()) %>
       </td>
      </tr>
      </dhv:evaluate>
</dhv:include>  

<dhv:include name="accounts-name" none="true">
	<dhv:evaluate if="<%= hasText((IndirizzoDettagli.getVia())) %>">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Via</dhv:label>
        </td>
        <td>
			 <%= toHtmlValue(IndirizzoDettagli.getVia()) %>
       </td>
      </tr>
	</dhv:evaluate>
</dhv:include> 


<dhv:include name="accounts-name" none="true">
	<dhv:evaluate if="<%= hasText((IndirizzoDettagli.getProvincia())) %>">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Provincia</dhv:label>
        </td>
        <td>
			 <%= toHtmlValue(IndirizzoDettagli.getProvincia()) %>
       </td>
      </tr>
	</dhv:evaluate>
</dhv:include> 



<dhv:include name="accounts-name" none="true">

      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Latitudine</dhv:label>
        </td>
        <td>
			 <%= (IndirizzoDettagli.getLatitudine()) %>
       </td>
      </tr>

</dhv:include> 


<dhv:include name="accounts-name" none="true">

      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Longitudine</dhv:label>
        </td>
        <td>
			 <%= (IndirizzoDettagli.getLongitudine()) %>
       </td>
      </tr>

</dhv:include> 

	
	
	
</table>
<br />




 


</dhv:container>



</body>