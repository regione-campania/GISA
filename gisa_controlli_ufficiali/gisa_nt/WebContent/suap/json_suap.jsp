<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.admin.base.Suap"%>
<%@page import="org.aspcfs.modules.suap.base.BeanPerXmlRichiesta"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%Suap st = (Suap) request.getAttribute("SupLogout");




Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
out.print(gson.toJson(st));
out.flush();
%>