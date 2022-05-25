<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        
    <%@ include file="../../initPage.jsp"%>
    <jsp:useBean id="error" class="java.lang.String" scope="request"/>
    <jsp:useBean id="idAnimale" class="java.lang.String" scope="request"/>
      <jsp:useBean id="label" class="java.lang.String" scope="request"/>
      
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<body>

	

<dhv:container name="animale" selected="allegati">

<center><b><p><span style="color: #ff0000;">ERRORE</span> </p></b>
<dhv:evaluate if="<%=(error!=null) %>"> 
<label><font size="20"><%=error %></font></label>
</dhv:evaluate>
<br/>
<img style="text-decoration: none;" width="150" src="gestione_documenti/images/sd_error.png" />
</center>
</dhv:container>