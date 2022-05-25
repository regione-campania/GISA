<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.json.JSONObject"%>
<jsp:useBean id="json" class="java.lang.String" scope="request" />
<%@ include file="../initPage.jsp"%>


<%
if(request.getAttribute("errore")!=null)
{
%>

	<%=showError(request, "errore")%>

<%
}


if(json!=null)
{
	JSONObject j = new JSONObject(json);
	Iterator<String> iterJson = (Iterator<String>)j.keys();
	while(iterJson.hasNext())
	{
		String key = iterJson.next();
		Object valoreObj = j.get(key);
		String valore="";
		if(!valoreObj.equals(JSONObject.NULL))
			if(valoreObj instanceof Integer)
				valore = ((Integer)valoreObj)+"";
			else
				valore = (String)valoreObj;
			valore=	(valore == null || valore.equalsIgnoreCase("null"))?(""):(valore);
		
%>
		<tr class="containerBody">
		   <td class="formLabel"><%=key%></td>
		   <td><%=valore%></td>
        </tr>
  
<%
	}
}
%>

