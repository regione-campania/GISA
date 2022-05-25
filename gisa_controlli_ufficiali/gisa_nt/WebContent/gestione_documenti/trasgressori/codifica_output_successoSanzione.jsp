<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        
    <%@ include file="../../initPage.jsp"%>
    <jsp:useBean id="messaggioPost" class="java.lang.String" scope="request"/>
      <jsp:useBean id="label" class="java.lang.String" scope="request"/>
       <jsp:useBean id="codDocumento" class="java.lang.String" scope="request"/>
        <jsp:useBean id="titolo" class="java.lang.String" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

  <%! public static String fixStringa(String nome)
  {
	  String toRet = nome;
	  if (nome == null || nome.equals("null"))
		  return toRet;
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll(" ", "_");
	  toRet = toRet.replaceAll("\\?","");
	
	  return toRet;
	  
  }%>
  
<script>
function ritornaAllegato (cod, tit){
		window.close();
}
function refreshParent (){
	window.opener.loadModalWindow();
	window.opener.location.reload();
	}
</script>

	
<body onload="refreshParent()">
<center><b><p><span style="color:green"><%=codDocumento %> - <%=titolo %></span> </p></b>
<dhv:evaluate if="<%=(messaggioPost!=null) %>"> 
<label><font size="5"><%=messaggioPost %></font></label>
</dhv:evaluate>
<br/>

<input type="button" class="buttonClass" style="width:200px;height:50px" value="CHIUDI E CONTINUA" onclick="ritornaAllegato('<%=codDocumento %>', '<%=fixStringa(titolo) %>')" />

</center>
</body>
