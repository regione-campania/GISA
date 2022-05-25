<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="org.aspcfs.utils.FileAesKeyException"%>
<%@page import="org.aspcfs.modules.login.actions.Login"%>
<%@page import="javax.crypto.spec.SecretKeySpec"%>
<%@page import="javax.crypto.Cipher"%>
<%@page import="crypto.nuova.gestione.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%-- VECCHIA GESTIONE TOKEN CAT 3
<%

String ipClient = request.getRemoteAddr();
String encrypteToken = "yLrICK6xxtOBvmbhd+0rVkSFPq/JIPOyaTacxw3+9cNJnen8IVRT36lJgupdXSgNrL1BEKaNF4dI+LBuv3ouR1bSpf9SMVFcqey+Hl/S6/M=";
try{
    
	
	byte[] bytes = new byte[16];

	SecretKeySpec spec = null;
	String hex_key = "85b5b83c27688103fbf6e50b12bf7439";
	
	byte[] result = null;
	if (hex_key != null) {
		result = new byte[hex_key.length() / 2];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) Integer.parseInt(hex_key.substring(2 * i, 2 * i + 2), 16);
		}
	}
	

	
	
	
	
	bytes = result;
	
	
	
	spec = new SecretKeySpec(bytes, "AES");
	
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, spec);
  byte[]  output = cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(encrypteToken.replaceAll(" ","+" ).getBytes()));
  
  String token =  new String(output);
  
  
  out.println("ipClient "+ipClient);
  out.println("Token "+token);

  
  }catch(Exception e){
    out.print(e.toString());
  }  
 


%>

--%>


<%
	String encryptedToken = request.getParameter("encryptedToken");
	System.out.println("token ricevuto "+encryptedToken);
	ClientSCAAesServlet cclient = new ClientSCAAesServlet();
	String decryptedToken = cclient.decrypt(encryptedToken);
	String ipClient = request.getRemoteAddr();
	System.out.println("token decrypted "+decryptedToken);
	out.println("ip:"+ipClient+"<br>");
	out.println("token encrypted ricevuto >> "+encryptedToken+"<br>");
	out.println("token decrypted >> "+decryptedToken);
%>

</body>
</html>