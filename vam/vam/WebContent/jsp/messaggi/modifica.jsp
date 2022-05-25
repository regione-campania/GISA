<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.io.BufferedReader"%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>

<%@page import="java.util.*" %>
<%@page import="java.io.File" %>
<%@page import="java.io.FileReader" %>
<%@page import="java.io.BufferedReader" %>
    			
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="it.us.web.bean.BRuolo"%>
<%@page import="it.us.web.util.properties.Message"%>
<%@page import="it.us.web.bean.BFunzione"%>
<%@page import="it.us.web.util.properties.Label"%>
<%@page import="it.us.web.bean.BGuiView"%>

    
	 <div class="area-contenuti-2">
	 <form action="messaggi.Modifica.us">
		 <table>
		 	<tr>
		 		<td width="50%">
		 			Messaggio
		 		</td>
		 		<td width="50%">
		 			<textarea rows="5" name="messaggio" id="messaggio"><%out.print(((String)request.getServletContext().getAttribute("MessaggioHome")).split("&&")[1].replace("<b>","").replace("</b>",""));%></textarea>
		 		</td>
		 	</tr>
		 	<tr>
		 		<td colspan="2">
		 			<input type="submit" name="modifica" id="modifica" value="Modifica"/>
		 		</td>
		 	</tr>
		 </table>
	</form>
	</div>
	


