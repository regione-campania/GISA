<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.json.JSONObject"%>
<%response.setContentType("textl");

String msgSessioneAggiornato = (String) application.getAttribute("MessaggioHome");

String[] split = msgSessioneAggiornato.split("&&");

Timestamp timeMsg = Timestamp.valueOf(split[0]);
Timestamp now = new Timestamp(System.currentTimeMillis());

Calendar GC1 = GregorianCalendar.getInstance();
Calendar GC2 = GregorianCalendar.getInstance();

GC1.setTimeInMillis(now.getTime());
GC2.setTimeInMillis(timeMsg.getTime());
long minuti =(GC1.getTime().getTime() - GC2.getTime().getTime()) / 1000 / 60  ;
JSONObject json = new JSONObject();

Timestamp dataRefresh = (Timestamp)session.getAttribute("DataUltimoRefresh");
if (dataRefresh==null || timeMsg.after(dataRefresh) )
{
	if (split.length > 1 && split[1]!=null && split[1]!="null")
	{
		json.put("msg",(String) split[1]);
		Timestamp now1 = new Timestamp(System.currentTimeMillis());
		session.setAttribute("DataUltimoRefresh",now1);
	}
	out.print(json);
    out.flush();	
}
else
{	
	//json.put("msg", "nd");
	out.print(json);
    out.flush();

}

%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>