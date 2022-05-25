<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="java.sql.*" %>

<% Connection con = null;
   PreparedStatement pst = null;
   ResultSet rs = null;
   String driverName = null;
   String urlDb = null;
   String user = null;
   String pw = null;
   String query = null;
   
   try
   {
	   
		driverName = (String)request.getAttribute("driverName");
		urlDb = (String) request.getAttribute("urlDb");
		user = (String) request.getAttribute("db_username");
		pw = (String) request.getAttribute ("db_password");
		query = (String) request.getAttribute("query"); //si presuppone che sia stata già riempita con i parametri
		
		
	   	Class.forName(driverName);
	   	
	   	con = DriverManager.getConnection(urlDb,user,pw);
	   	
	   	pst = con.prepareStatement(query);
	   	
	   	rs = pst.executeQuery();
	   	rs.next();
	   	String valoreOttenuto = rs.getString(1);
	   	request.setAttribute("valoreOttenutoDaQuery",valoreOttenuto);
	   	
   }
   catch(Exception ex)
   {
	   ex.printStackTrace();
   }
   finally
   {
	   if(con != null) con.close();
   }
   	
%>
