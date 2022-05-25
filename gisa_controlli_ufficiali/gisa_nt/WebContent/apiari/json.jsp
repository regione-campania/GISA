<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="org.json.JSONObject"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>
<%
HashMap map= new HashMap();
map.put("EsitoInserimentoSoggettoFisico", request.getAttribute("EsitoInserimentoSoggettoFisico"));
map.put("ErroreInserimento", request.getAttribute("ErroreInserimento"));

map.put("nominativoSoggettoFisico", request.getAttribute("nominativoSoggettoFisico"));
map.put("idSoggettoFisico", request.getAttribute("idSoggettoFisico"));
map.put("cfSoggettoFisico", request.getAttribute("cfSoggettoFisico"));



 Enumeration<String> e =  request.getAttributeNames();
while (e.hasMoreElements())
{
	String kiave = e.nextElement();
	if (kiave.contains("Error"))
	{
		String valore = (String) request.getAttribute(kiave);
		map.put(kiave, valore);
	}
	
}

JSONObject json = new JSONObject(map);

%>

<%=json%>