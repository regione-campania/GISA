<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>
</head>
<body>
<% String cmd = request.getAttribute("cmd").toString();
   
   if (cmd.equals("AR")){
	   String r1=request.getAttribute("r1").toString();
	   String r2=request.getAttribute("r2").toString();
	   out.println("<h1>ERRORE : Esiste già un'associazione per il ruolo <span style='color:red; font-variant: small-caps'>"+r1+"</span> o per il ruolo <span style='color:blue; font-variant: small-caps'>"+r2+"</span>.</h1><br><h1>Controllare la sezione <span style='color:green; font-variant: small-caps'>Ruoli Associati</span> nella pagina precedente.</h1>");	   
   }
   if (cmd.equals("SR")){
	   String r1=request.getAttribute("r1").toString();
	   String r2=request.getAttribute("r2").toString();
	   out.println("<h1>ERRORE : Non esiste un'associazione tra il ruolo <span style='color:red; font-variant: small-caps'>"+r1+"</span> ed il ruolo <span style='color:blue; font-variant: small-caps'>"+r2+"</span>.</h1><br><h1>Controllare la sezione <span style='color:green; font-variant: small-caps'>Ruoli Associati</span> nella pagina precedente.</h1>");
   }
   if (cmd.equals("AP")){
	   String p1=request.getAttribute("p1").toString();
	   String p2=request.getAttribute("p2").toString();
	   out.println("<h1>ERRORE : Esiste già un'associazione per il permesso <span style='color:red; font-variant: small-caps'>"+p1+"</span> o per il permesso <span style='color:blue; font-variant: small-caps'>"+p2+"</span>.</h1><br><h1>Controllare la sezione <span style='color:green; font-variant: small-caps'>Permessi Associati</span> nella pagina precedente.</h1>");
   }
   if (cmd.equals("SP")){
	   String p1=request.getAttribute("p1").toString();
	   String p2=request.getAttribute("p2").toString();
	   out.println("<h1>ERRORE : Non esiste un'associazione tra il permesso <span style='color:red; font-variant: small-caps'>"+p1+"</span> ed il permesso <span style='color:blue; font-variant: small-caps'>"+p2+"</span>.</h1><br><h1>Controllare la sezione <span style='color:green; font-variant: small-caps'>Permessi Associati</span> nella pagina precedente.</h1>");
   }
   if (cmd.equals("SC")){
	   String r1=request.getAttribute("r1").toString();
	   out.println("<h1>ERRORE : Esiste già un'associazione per il ruolo <span style='color:red; font-variant: small-caps'>"+r1+"</span>.</h1><br><h1>Controllare la sezione <span style='color:green; font-variant: small-caps'>Ruoli Associati</span> nella pagina precedente.</h1>");	   
   }
 %>
</body>
</html>