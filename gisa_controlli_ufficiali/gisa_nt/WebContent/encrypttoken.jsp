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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


<%
String hex_key = "85b5b83c27688103fbf6e50b12bf7439";

String ipClient = request.getRemoteAddr();

SecretKeySpec skey = null;





byte[] crypted = null;
try{
	//SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
	skey = new SecretKeySpec(hex_key, "AES");
	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	cipher.init(Cipher.ENCRYPT_MODE, skey);
	crypted = cipher.doFinal("prova3prova".getBytes());
}catch(Exception e){
	System.out.println(e.toString());
}
String s= new String(org.apache.commons.codec.binary.Base64.encodeBase64(crypted));



 


%>
</body>
</html>