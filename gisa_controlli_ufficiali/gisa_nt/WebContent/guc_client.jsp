<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.MalformedURLException"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.URLEncoder"%>
<%
String title = "test";
String content ="sdf" ;
String price = "wdf";
String tags = "" ;
String data = "product[title]=" + URLEncoder.encode(title) +
"&product[content]=" + URLEncoder.encode(content) + 
"&product[price]=" + URLEncoder.encode(price.toString()) +
"&tags=" + tags;
try {
URL url = new URL("");
URLConnection conn;
conn = url.openConnection();
conn.setRequestProperty ("Authorization", "Basic " + "");
conn.setDoOutput(true);
conn.setDoInput(true);
OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
wr.write(data);
wr.flush(); 
// Get the response 
BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
String line; 
while ((line = rd.readLine()) != null) { 
// Process line... 
} 
wr.close(); 
rd.close(); 
} catch (MalformedURLException e) {

e.printStackTrace();
}
catch (IOException e) {

e.printStackTrace();

} 

%>