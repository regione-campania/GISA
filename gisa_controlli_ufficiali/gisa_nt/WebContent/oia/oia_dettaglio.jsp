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
<%@page import="org.aspcfs.modules.lineeattivita.base.*"%>
<%@page import="org.aspcf.modules.report.util.*"%>

<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>

<%@page import="org.aspcfs.modules.oia.base.ResponsabileNodo"%><jsp:useBean id="NodoDetails" class="org.aspcfs.modules.oia.base.OiaNodo" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="lookup_comuni" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="lookupASL" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>





<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Oia.do?command=SearchForm">Autorità Competenti</a> > 
Dettaglio Dipartimento
</td>
</tr>
</table>




<% String param1 = "orgId=" + NodoDetails.getOrgId()+"&idNodo="+NodoDetails.getId();   
%>




<dhv:container name="asl" selected="details" object="NodoDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>



<input type="hidden" name="orgId" value="<%= NodoDetails.getOrgId() %>"> 


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
    </th>
  </tr>
  
   <tr class="containerBody">
    <td  class="formLabel">
     Asl
    </td>
    <td>
       <%= toHtml(lookupASL.getSelectedValue(NodoDetails.getId_asl())) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td  class="formLabel">
     Nome Struttura
    </td>
    <td>
       <%= toHtml(NodoDetails.getDescrizione_lunga()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td  class="formLabel">
     Responsabili
    </td>
    <td>
       <%for(ResponsabileNodo resp : NodoDetails.getListaResponsabili())
									{
										
										out.println(resp.getNome_responsabile()+" "+resp.getCognome_responsabile()+"<br>");
									}
       %>
    </td>
  </tr>
  <tr class="containerBody">
    <td  class="formLabel">
     Indirizzo
    </td>
    <td>
       <%= toHtml(NodoDetails.getIndirizzo()) %>&nbsp;
    </td>
  </tr>
  <tr class="containerBody">
    <td  class="formLabel">
     Comune
    </td>
    <td>
        <%= toHtml(lookup_comuni.getSelectedValue(NodoDetails.getComune())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td  class="formLabel">
     Mail
    </td>
    <td>
       <%= toHtml(NodoDetails.getMail()) %>&nbsp;
    </td>
  </tr>
  

	
		
</table>
<br>

</dhv:container>