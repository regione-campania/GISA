<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.db.ApplicationProperties"%>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>

<!DOCTYPE html>


<%@page import="it.us.web.bean.BUtente"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="it" lang="it">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=windows-1252" />
		
		<!-- inclusioni per jmesa -->
		<link rel="stylesheet" type="text/css" href="css/jmesa/jmesa.css" />
		<script type="text/javascript" src="js/jmesa/jquery-1.3.min.js"></script>
		<script type="text/javascript" src="js/jmesa/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="js/jmesa/jmesa.js"></script>
		<script type="text/javascript" src="js/jmesa/jmesa.min.js"></script>
		<script type="text/javascript" src="js/jmesa/jquery.jmesa.js"></script>
		
		
<!--		<script type="text/javascript" src="js/jquery/jquery-1.3.2.min.js"></script>-->
		<script type="text/javascript" src="js/jquery/jquery-ui-1.7.3.custom.min.js"></script>
		<script type="text/javascript" src="js/jquery/tooltip.min.js"></script>
		<script type="text/javascript" src="js/jquery/jquery-us.js"></script>

		<!-- link rel="stylesheet" type="text/css" href="css/black-tie/jquery-ui-1.7.3.custom.css" /-->
		<!-- link rel="stylesheet" type="text/css" href="css/custom-theme/jquery-ui-1.7.3.custom.css" /-->
		<!-- link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui-1.7.3.custom.css" /-->
		<link rel="stylesheet" type="text/css" href="css/redmond/jquery-ui-1.7.3.custom.css" />
		
		<link rel="stylesheet" type="text/css" href="css/guc/template_css.css" />
		<link rel="stylesheet" type="text/css" href="css/aqua/theme.css" />
		<script type="text/javascript" src="js/amministrazione/permessi.js"></script>
		<script type="text/javascript" src="js/azionijavascript.js"></script>
		<script type="text/javascript" src="js/date.js"></script>
		<script type="text/javascript" src="js/calendario/calendar.js"></script>
		<script type="text/javascript" src="js/calendario/calendar-setup.js"></script>
		<script type="text/javascript" src="js/calendario/calendar-it.js"></script>
		<title>G.U.C.</title>
		
	</head>
	
  <body class="white" >
    
    <div id="dialog-modal" title="Attendere">
		<p>
			<br />
			<img src="images/loader.gif" />
		</p>
	</div>
	
	<c:if test="${errore != null || messaggio != null}">
		<jsp:include page="default/errore-messaggio-popup.jsp" />
	</c:if>
	
    <div  class="header" >
    	<tiles:insertAttribute name="header" />
    </div>

	<div class="contentBody">
		<us:err classe="errore" />
 		<us:mex classe="messaggio" />

<%
if(!ApplicationProperties.getAmbiente().toUpperCase().contains("UFFICIALE"))
{
%>
<br/><br/>
<h1 style="text-align:center;background-color:<%=ApplicationProperties.getAmbiente().contains("COLLAUDO") ? "#4477AA" : "black" %>;color:white;border:0px;" >
AMBIENTE <%=ApplicationProperties.getAmbiente()%> 
</h1>
<%
}
%>
		<tiles:insertAttribute name="body" />
	</div>
				
	<div id="footer">
		<div class="padding">
             <div class="moduletable">
				<tiles:insertAttribute name="footer" />
			</div>
		</div>
	</div>
					
  </body>

</html>
