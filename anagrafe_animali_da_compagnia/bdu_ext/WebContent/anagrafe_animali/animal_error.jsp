<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="errore"
	class="java.lang.String"
	scope="request" />   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Errore</title>
</head>
<script>
   function changeScreenSize(w,h)
     {
       window.resizeTo( w,h )
     }
</script>
<body onload="changeScreenSize(500,400)">

<table align="center" cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong>ERRORE</strong></th>
			</tr>
			<tr class="containerBody">
				<td valign="top" class="formLabel">Note
				</td>
				<td><textarea readonly name="noteErrore" rows="10" cols="50" style="color:red"><%=errore %></textarea>
				</td>
			</tr>
		</table>

<center><input type ="button" value="CHIUDI" onClick="window.close()"></input></center>
</body>
</html>