<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.net.*"%>
<%@page import="java.io.*"%>
<%@page import="org.json.*"%>

<%!

String ambiente="";

String SVILUPPO = "localhost";
String COLLAUDO = "col.gisacampania.it";

String BDU_VAM_DEMO = "demo.anagrafecaninacampania.it";
String BDU_VAM_UFFICIALE = "srv.anagrafecaninacampania.it";

//String GISA_SVILUPPO = "localhost";
//String GISA_COLLAUDO = "col.gisacampania.it";
String GISA_DEMO = "demo.gisacampania.it";
String GISA_UFFICIALE = "srv.gisacampania.it";

//String BDU_SVILUPPO = "localhost";
//String BDU_COLLAUDO = "col.gisacampania.it";
//String BDU_DEMO = "demo.anagrafecaninacampania.it";
//String BDU_UFFICIALE = "srv.anagrafecaninacampania.it";

//String VAM_SVILUPPO = "localhost";
//String VAM_COLLAUDO = "col.gisacampania.it";
//String VAM_DEMO = "demo.anagrafecaninacampania.it";
//String VAM_UFFICIALE = "srv.anagrafecaninacampania.it";

%>

<%
if(request.getParameter("richiesta") != null && request.getParameter("richiesta").equals("ambiente")){
		ambiente = getAmbiente((String)request.getServerName());
}



JSONObject json = new JSONObject();
json.put("ambiente", ambiente);
//json.put("link", "LINK_TEST");
out.print(json);
out.flush();

//out.print("AMBIENTE: "+ambiente);
%>

<%!
public String getAmbiente(String url) throws IOException{
	// Recupero il IP pubblico del server
	String ip_url_pubblico="";
	URL connection = new URL("http://checkip.amazonaws.com/");
    URLConnection con = connection.openConnection();
    String str = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
    str = reader.readLine();
    ip_url_pubblico=str;
	
    
	// Confront l'ip recuperato con l'ip dei vari sistemi esistenti
    String ip_url_confronto="";
	
	// CASO SVILUPPO
	if (url.equalsIgnoreCase("localhost")){
		return "SVILUPPO";
	}
    
	// CASO COLLAUDO
	InetAddress[] machines = InetAddress.getAllByName(COLLAUDO);
	for(InetAddress address : machines){
		ip_url_confronto=address.getHostAddress();
		break;
	}
	if (ip_url_pubblico.equalsIgnoreCase(ip_url_confronto)){
		return "COLLAUDO";
	}
	
	// CASO DEMO
	machines = InetAddress.getAllByName(BDU_VAM_DEMO);
	for(InetAddress address : machines){
		ip_url_confronto=address.getHostAddress();
		break;
	}
	if (ip_url_pubblico.equalsIgnoreCase(ip_url_confronto)){
		return "DEMO";
	}

	machines = InetAddress.getAllByName(GISA_DEMO);
	for(InetAddress address : machines){
		ip_url_confronto=address.getHostAddress();
		break;
	}
	if (ip_url_pubblico.equalsIgnoreCase(ip_url_confronto)){
		return "DEMO";
	}

	// CASO UFFICIALE
	machines = InetAddress.getAllByName(BDU_VAM_UFFICIALE);
	for(InetAddress address : machines){
		ip_url_confronto=address.getHostAddress();
		break;
	}
	if (ip_url_pubblico.equalsIgnoreCase(ip_url_confronto)){
		return "UFFICIALE";
	}

	machines = InetAddress.getAllByName(GISA_UFFICIALE);
	for(InetAddress address : machines){
		ip_url_confronto=address.getHostAddress();
		break;
	}
	if (ip_url_pubblico.equalsIgnoreCase(ip_url_confronto)){
		return "UFFICIALE";
	}

	
    return "DEMO";

}



%>

