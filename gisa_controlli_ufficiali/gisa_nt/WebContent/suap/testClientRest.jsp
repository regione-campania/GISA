<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.text.*" %>
<%@page import="java.util.Date"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="org.aspcfs.utils.FileAesKeyException"%>
<%@page import="org.aspcfs.modules.login.actions.Login"%>
<%@page import="javax.crypto.spec.SecretKeySpec"%>
<%@page import="javax.crypto.Cipher"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery.ajax-cross-origin.min.js"></script>	
<img src="suap-demo.jpg" onclick='document.getElementById("gLnk").style.visibility="visible"'>
<br>
<a href="http://mon.gisacampania.it/token/_token.php" ><p id='gLnk' style="visibility:hidden; background:#80ff80; font-weight:bold;text-align:center">SCIA Sanitaria</p></a>

<div id="test" />
<button onclick = "getToken();">clickme</button>

<% 	
	String sharK = "\205\265\270<'h\201\003\373\366\345\013\022\277t9"; //16 Character Key"
	Date data = new Date();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	String currTimeStamp = format.format(data);
	String istat="063064";
	String cf1 = "-",cf2="-";
	String ipClient ="94.23.210.207";
	
	String encrypteToken = currTimeStamp+"@"+istat+"@"ipClient+"@"+cf1+"@"+cf2;
	

    
	
	byte[] bytes = new byte[16];
	File f = new File(Login.class.getResource("aes_key").getPath().replaceAll("%20", " "));

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
	
	out.print(new String(bytes));
	
	System.out.println(new String(bytes));
	
	spec = new SecretKeySpec(bytes, "AES");
	
	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, spec);
	byte[]  output = cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(encrypteToken.replaceAll(" ","+" ).getBytes()));
	  
	String token =  new String(output);
	  
	  
	out.println("ipClient "+ipClient);
	out.println("Token "+token);

  
  
	
	
	
	
	
	
	
%>



</body>
</html>