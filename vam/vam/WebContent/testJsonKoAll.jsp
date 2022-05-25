<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="com.google.gson.JsonParser"%>
<%@page import="org.json.JSONObject"%>
<%
	Context ctxStorico = new InitialContext();
	Connection connectionStorico = null;
	javax.sql.DataSource dsStorico = (javax.sql.DataSource)ctxStorico.lookup("java:comp/env/jdbc/storico");
	connectionStorico = dsStorico.getConnection();
	
	Context ctxBdu = new InitialContext();
	Connection connectionBdu = null;
	javax.sql.DataSource dsBdu = (javax.sql.DataSource)ctxBdu.lookup("java:comp/env/jdbc/bduM");
	connectionBdu = dsBdu.getConnection();
	
	PreparedStatement stat =  connectionStorico.prepareStatement(" select parametri, data from bdu_storico_operazioni_utenti " +
									   " where path ilike '%RegistrazioniAnimale.do?command=Insert%' and " +
									   " parametri ilike '%doContinue=true%' and " +
									   " data >= '01/03/2016' " +
									   " order by data asc " );
	
	
	ResultSet rs =  stat.executeQuery();
	while(rs.next())
	{
		String stringToParse = rs.getString("parametri");
		Timestamp data 		  =  rs.getTimestamp("data");
		/*JsonParser parser = new JsonParser();
		JsonObject json1 = (JsonObject) parser.parse(stringToParse);
		int idAnimale 		  = json1.get("idAnimale").getAsInt();
		int idTipologiaEvento = json1.get("idTipologiaEvento").getAsInt();*/
		String stringa = stringToParse;
		int indiceStringa = stringa.indexOf("idAnimale");
		stringa = stringa.substring(indiceStringa, stringa.length());
		int indiceVirgola = stringa.indexOf(",");
		stringa = stringa.substring(0, indiceVirgola);
		String[] valore = stringa.split("=");
		String valore2 = valore[1].trim();
		int idAnimale = -1;
		idAnimale = Integer.parseInt(valore2);
		
		stringa = stringToParse;
		indiceStringa = stringa.indexOf("idTipologiaEvento=");
		int idTipologiaEvento = -1;
		if(indiceStringa>0)
		{
			stringa = stringa.substring(indiceStringa, stringa.length());
			indiceVirgola = stringa.indexOf(",");
			stringa = stringa.substring(0, indiceVirgola);
			valore = stringa.split("=");
			if(valore!=null && valore.length>1 && valore[1]!=null && !valore[1].equals(""))
				valore2 = valore[1].trim();
			idTipologiaEvento = Integer.parseInt(valore2);  
			
		}
		
		PreparedStatement stat2 =  connectionBdu.prepareStatement(" select count(*) as count from evento where id_animale = ? and id_tipologia_evento = ?" );
		stat2.setInt(1, idAnimale);
		stat2.setInt(2, idTipologiaEvento);
		

		ResultSet rs2 =  stat2.executeQuery();
		if(rs2.next())
		{
			if(rs2.getInt("count")>0)
			{
%>
				Esito: OK;
				<br/>
<%
			}
			else
			{
%>
				Registrazione inserita -  idAnimale: <%=idAnimale%>, idTipologiaEvento: <%=idTipologiaEvento%>, data: <%=data%>
				<br/>
				Esito: KO;
				<br/>
<%			
			}
			
		}

	}

	connectionBdu.close();
	connectionStorico.close();
	
%>







	 