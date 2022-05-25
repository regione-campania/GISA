<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <% response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
 response.setHeader("Pragma","no-cache"); //HTTP 1.0 
 response.setDateHeader ("Expires", 0); //prevents caching at the proxy server  
%>


<%@page import="java.net.MalformedURLException"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.net.URL"%>
<%@page import="javax.crypto.Cipher"%>
<%@page import="java.security.NoSuchAlgorithmException"%>
<%@page import="java.io.IOException"%>
<%@page import="javax.crypto.spec.SecretKeySpec"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.net.InetAddress"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="crypto.nuova.gestione.*,java.net.URLEncoder" %>
    
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
String GisaSuapURL="http://open.gisacampania.it/suap/Login.do?command=LoginSuapWithToken";



%>

<%! public  String getMyIP() { 
	URL ipAdress;

try {
    ipAdress = new URL("http://myexternalip.com/raw");

    BufferedReader in = new BufferedReader(new InputStreamReader(ipAdress.openStream()));

    String ip = in.readLine();
    System.out.println(ip);
    return ip;
} catch (MalformedURLException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}

return "" ;
}


public static byte[] lenientHexToBytes(String hex) {
	byte[] result = null;
	if (hex != null) {
		result = new byte[hex.length() / 2];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
	}
	
	String ss = new String(result);
	System.out.println("Key String "+ss);

	return result;
}

public  String getTimestamp() {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String time = sdf.format(new Date(System.currentTimeMillis()));
	return time;
}

public  SecretKeySpec getKeySpecByString(String preSharedKey) throws IOException, NoSuchAlgorithmException
 {
	SecretKeySpec spec = null;
	spec = new SecretKeySpec(preSharedKey.getBytes(), "AES");
	return spec;
}



double  a = Math.random();

// String crypted_token=NEWencrypt(string_to_token, pre_shared_key,a);

%>

<%


String istat="063064";  // cod. Istat provincia+comune <-- DA CAMBIARE PER IL PROPRIO COMUNE
String  cf1="blzgpp84h13l259s"; //CF impresa <-- DA VALORIZZARE A RUN TIME 
String  cf2="blzgpp84h13l259s"; // CF delegato  <-- DA VALORIZZARE A RUN TIME 
String  myIP=getMyIP();
String time = getTimestamp();
String  pre_shared_key = "85b5b83c27688103fbf6e50b12bf7439"; //16 Character Key

String string_to_token = time + "@" +  istat + "@" +   "94.23.210.207@" + cf1 + "@" + cf2;
/* VECCHIA GESTIONE TOKEN --> QUESTA PAGINA GENERA LA MASCHERA DI GENERAZIONE TOKEN, ENCRYPT DI QUESTO (TRAMITE CHIAMATA SERVIZIO CENTRALIZZATO)  ETEST DI INVIO TOKEN
	  byte[] crypted = null;
	  try{
	    //SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
		  SecretKeySpec skey = getKeySpecByString(new String(lenientHexToBytes(pre_shared_key)));
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.ENCRYPT_MODE, skey);
	      crypted = cipher.doFinal(string_to_token.getBytes());
	    }catch(Exception e){
	    	System.out.println(e.toString());
	    }
	  String crypted_token= new String(org.apache.commons.codec.binary.Base64.encodeBase64(crypted));
	
*/
/*nuova gestione*/
ClientSCAAesServlet cclient = new ClientSCAAesServlet();
String crypted_token = cclient.crypt(string_to_token) ; 

%>

	<form action= "<%=GisaSuapURL %>"  method="post">
		<input type=text name="encryptedToken" id="encryptedToken"  value="<%=crypted_token%>">
		<input type=hidden name="SuapIP"  value="94.23.210.207">
		<input type=hidden name="debugServizioRest"  value="yes"> 
		<input type=submit>
		<input type=button onclick="location.href='/gisa_ext/decryptToken.jsp?encryptedToken=<%=URLEncoder.encode(crypted_token,"UTF-8")%>'" value = "Decrypt Test">
		
		</form></body>
	<script language="JavaScript">
		//document.forms[0].submit();
	</script>



</body>
</html>