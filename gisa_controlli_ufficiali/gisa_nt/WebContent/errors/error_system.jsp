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
<%@page import="com.sun.corba.se.impl.orbutil.closure.Constant"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.io.*, org.aspcfs.modules.base.Constants"%>
<%
  Object errorObject = request.getAttribute("Error");
  String errorMessage = "";
  if (errorObject!=null){
  if (errorObject instanceof java.lang.String) {
    errorMessage = (String)errorObject;
  } else if (errorObject instanceof java.lang.Exception) {
	  Exception e = (Exception)errorObject;
	  if (errorObject instanceof SQLException) {
		  SQLException ex = (SQLException)errorObject;
		  errorMessage = ex.getSQLState();
	  }
		  
     if (e.getMessage()!=null && !e.getMessage().equals(Constants.NOT_FOUND_ERROR) && !e.getMessage().contains(Constants.INDIRIZZI_EXCEPTION) && !errorMessage.equals(Constants.USER_PERMISSION_EXCEPTION)){
      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      e.printStackTrace(new PrintStream(outStream));
      errorMessage = outStream.toString();
      %>
<img src="images/error.gif" border="0" align="absmiddle"/>
<font color='red'><dhv:label name="errors.anErrorHasOccured">Errore</dhv:label></font>
<hr color="#BFBFBB" noshade>
      
    <%   }
    else if (e.getMessage()!=null && e.getMessage().contains(Constants.INDIRIZZI_EXCEPTION)){
//         ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//         new PrintStream(outStream);
//         errorMessage = outStream.toString();
    		errorMessage = e.getMessage();
         } 
         else if (e.getMessage()!=null && !errorMessage.equals(Constants.USER_PERMISSION_EXCEPTION)){
      		errorMessage = "This Object does not exist or has been deleted";
    	 }
  	}
  }	
  
  
  if (!errorMessage.equals("")) {%>
	<% if (errorMessage.contains(Constants.INDIRIZZI_EXCEPTION)) { %>
		<%@ include file="error_indirizzi.jsp" %>
	<%} else if (errorMessage.equals(Constants.USER_PERMISSION_EXCEPTION))  { %>
		<dhv:label name="errors.actualErrorIs.colon" param='<%= "errorMessage="+errorMessage %>'>The actual error is:<br /><br /><font color="red">ATTENZIONE : Operazione non consentita con il browser utilizzato. I dati inseriti non saranno salvati</font></dhv:label>
	<% } else { %>
		<dhv:label name="errors.actualErrorIs.colon" param='<%= "errorMessage="+errorMessage %>'>The actual error is:<br /><br /><font color="red">ATTENZIONE !SI E' VERIFICATO UN ERRORE CONTATTARE IL SUPPORTO HELP DESK : <%=errorMessage %></font></dhv:label>
	<% } 
  } else { %>
	<dhv:label name="errors.noErrorMessageFromAction">An error message was not provided by this action.</dhv:label>
<%}%>

