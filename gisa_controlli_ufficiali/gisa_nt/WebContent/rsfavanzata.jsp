<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%>
<link rel="stylesheet" type="text/css" href="css/colore_demo.css"></link>	
<link rel="stylesheet" type="text/css" href="css/demo.css"></link>		
<link rel="stylesheet" type="text/css" href="css/custom.css"></link>	
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<link rel="stylesheet" type="text/css" href="css/capitalize.css"></link>		

<script src='javascript/modalWindow.js'></script>
<script src='javascript/jquerymini.js'></script>	
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
 
<script>
function wait()
{
	
setTimeout(function(){document.getElementById("goRsfavanzata").submit();}, 1000);

}
</script>
<body onload="wait()">
<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<% System.out.println("id_asl RSF: "+ User.getSiteId());
%>

<form name="goRsfavanzata" id="goRsfavanzata" action="http://<%=java.net.InetAddress.getByName("matrix.gisacampania.it").getHostAddress()%>/tables/grid.php?id_asl=<%=(User.getSiteId() > 0) ? (User.getSiteId()) : (-1) %>" method="post">
</form>

</body> 


