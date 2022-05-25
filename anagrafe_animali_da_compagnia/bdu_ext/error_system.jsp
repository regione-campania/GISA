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
  - Version: $Id: error_system.jsp 24351 2007-12-09 15:29:31Z srinivasar@cybage.com $
  - Description:
  --%>
<%@page import="java.io.*, org.aspcfs.modules.base.Constants"%>
<img src="images/error.gif" border="0" align="absmiddle"/>
<font color='red'><dhv:label name="errors.anErrorHasOccured">Si è verificato un problema</dhv:label></font>
<hr color="#BFBFBB" noshade>
<dhv:label name="errors.reportErrorTo">Cortesemente invia una </dhv:label> <a href="MyCFSInbox.do?command=Inbox&return=1">segnalazione all'help desk</a><p>
<!-- <dhv:label name="errors.backButtonBrowser.text">You may be able to hit the back button on your browser, review your selection, and try your request again.</dhv:label><p> -->
<pre>
<%
  Object errorObject = request.getAttribute("Error");
  
  String errorMessage = "";
  
  if (errorObject instanceof java.lang.String) {
    errorMessage = (String)errorObject;
  } else if (errorObject instanceof java.lang.Exception) {
    Exception e = (Exception)errorObject;
    if (e!=null && e.getMessage()!=null && !e.getMessage().equals(Constants.NOT_FOUND_ERROR)){
      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      e.printStackTrace(new PrintStream(outStream));
      errorMessage = outStream.toString();
    } else{
      errorMessage = "This Object does not exist or has been deleted";
    }
  }
  if (errorMessage.indexOf("evento_presa_in_carico_adozione_fuori_asl_check")>0) 
  {
	out.println("Errore: Deve essere selezionato il nuovo proprietario in fase di presa in carico di adozione fuori asl");
  }
  else if (!errorMessage.equals("")) {
%>
<dhv:label name="errors.actualErrorIs.colon" param='<%= "errorMessage="+errorMessage %>'>Questo il messaggio di errore:<br /><br /><%= errorMessage %></dhv:label>
<%
  } else {
%>
<dhv:label name="errors.noErrorMessageFromAction">Nessun messaggio relativo all'errore</dhv:label>
<%
  }
%>
</pre>
