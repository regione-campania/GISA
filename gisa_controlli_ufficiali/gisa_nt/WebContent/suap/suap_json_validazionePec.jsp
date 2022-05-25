<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.HashMap" %>
<%@page import ="org.aspcfs.utils.DestinatarioPecMailChecker" %>
<%

HashMap<String,String> esitoObj = new HashMap<String,String>();
Integer esito =  Integer.parseInt((String)request.getAttribute("esito_validazione_pec"));

esitoObj.put("esito",esito+"");

String msgPerClient = null;
if(esito == DestinatarioPecMailChecker.FORMATO_INVALIDO)
{
	msgPerClient = "FORMATO INVALIDO";
}
else if(esito == DestinatarioPecMailChecker.PROVIDER_INVALIDO)
{
	msgPerClient = "PROVIDER PEC NON VALIDO";
}
else if(esito == DestinatarioPecMailChecker.ESITO_OK)
{
	msgPerClient = "PEC MAIL VALIDA";
}

esitoObj.put("messaggio",msgPerClient);


Gson gson = new GsonBuilder().create();
out.print(gson.toJson(esitoObj));
out.flush();
%>