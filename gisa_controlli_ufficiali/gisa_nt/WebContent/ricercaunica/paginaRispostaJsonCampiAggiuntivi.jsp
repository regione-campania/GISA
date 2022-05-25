<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.suap.base.LineaProduttivaValidazione"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

<%@page import="org.aspcfs.modules.suap.base.LineaProduttiva"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList, java.util.HashMap"%>
<%@page import="org.json.*"%>
<%@page import="org.aspcfs.modules.opu.base.LineeMobiliHtmlFields" %>
<%@page import = "org.aspcfs.utils.Jsonable" %>



 
<%
    LineaProduttiva linea = (LineaProduttiva)request.getAttribute("lineaConCampiAssociati");
    JSONObject resp = new JSONObject(); //{status : int , campiLinea : jsonArray}
    if(linea == null)
    {
    	 resp.put("status","-1");
    	  
    	 
    }
    else
    {
    	ArrayList<LineeMobiliHtmlFields> campiAggiuntivi = linea.getHtmlFieldsValidazione();
    	JSONArray campi = new JSONArray(Jsonable.getListAsJsonArrayString(campiAggiuntivi));
    	resp.put("status","0");
    	resp.put("campiPerLinea",campi);
    	resp.put("idLinea",linea.getIdRelazioneAttivita()+"");
    	System.out.println( campiAggiuntivi.size());
    }
	  
	
	response.setContentType("application/json");
	out.write(resp.toString());
%>


