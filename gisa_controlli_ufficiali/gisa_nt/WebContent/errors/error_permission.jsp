<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: error_permission.jsp 24351 2007-12-09 15:29:31Z srinivasar@cybage.com $
  - Description:
  --%>
<%@page import="java.io.*"%>
<img src="images/error.gif" border="0" align="absmiddle"/>
<% 
String permessoMancante = (String) request.getAttribute("permissionMancante");
String ambiente = (String)application.getAttribute("ambiente"); 
if (ambiente.equals("SLAVE")){ %>
<font color='red'><dhv:label name="errors.anErrorHasOccured">An Error Has Occurred</dhv:label></font>
<hr color="#BFBFBB" noshade>
<dhv:label name="errors.systemAdminNotified.text" param="p=<p>">Azione non consentita in ambiente cloud</dhv:label>
<%} else { %>
<font color='red'><dhv:label name="errors.anErrorHasOccured">An Error Has Occurred</dhv:label></font>
<hr color="#BFBFBB" noshade>
<dhv:label name="errors.systemAdminNotified.text" param="p=<p>">Attenzione! Azione non consentita. <br>
Possibili cause : 
<li>Browser utilizzato diverso da Firefox.</li>
<li>Non si dispone dei permessi necessari per questa azione. <%=(permessoMancante!=null) ? "<a href=\"#\" style=\"text-decoration: none\" onClick=\"document.getElementById('tablePermission').style.display='table-row';\">Permesso mancante.</a>" : "" %> 
<div style="display:none">(Permesso mancante: <%= permessoMancante %>)</div> 
Contattare il servizio Helpdesk in questo caso. </li>
</dhv:label>

<table id="tablePermission" style="border: 1px solid black; display: none" cellpadding="10" cellpadding="10">
<tr><th colspan="2">INFO HELPDESK</th></tr>
<tr><td>Permesso mancante</td><td><b><%= permessoMancante %></b></td></tr>
</table>

<%} %>



