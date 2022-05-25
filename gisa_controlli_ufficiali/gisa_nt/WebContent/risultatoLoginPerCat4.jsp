<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%
	
	System.out.println("CHIAMATA JSP PER RISULTATO TENTATIVO LOGIN CAT 4");
	String risultatoLogin = (String) request.getAttribute("risultato")+"/"+(String)request.getAttribute("session_id")+
							"/"+ (String) request.getAttribute("comune_suap") +"/"+ (String) request.getAttribute("id_comune_suap")
							+ "/" + (String) request.getAttribute("provincia") + "/"+ (String) request.getAttribute("id_provincia")
							+ "/"+ (String) request.getAttribute("id_user");
							
	System.out.println(risultatoLogin);
	response.getOutputStream().print(risultatoLogin);
%>