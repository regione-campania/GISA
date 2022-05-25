<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aspcfs.modules.diffida.base.Ticket"%>
<%@page import="java.util.Iterator"%>
<jsp:useBean id="DiffideList" class="org.aspcfs.modules.diffida.base.TicketList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.diffida.base.Ticket" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>


<%@ include file="../initPage.jsp" %>




<% 

String param1 = "stabId=" + OrgDetails.getIdStabilimento	();  
String container = OrgDetails.getContainer();

%>




<dhv:container name="<%=container%>" selected="Elenco Diffide" object="OrgDetails" param='<%= param1 %>'>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    
    
       <tr>
	 
      <th colspan="2"><strong>Elenco Diffide</th>
</tr>
        <tr>
	 
	   <th><strong><dhv:label name="">Diffidato Per</dhv:label></strong></th>
      <th ><strong><dhv:label name="">Data</dhv:label></strong></th>
</tr>


<%
Iterator it = DiffideList.iterator();
if (it.hasNext())
{
	while(it.hasNext())
	{
		Ticket diff = (Ticket)it.next();
		HashMap<Integer,String> lista= diff.getListaNorme();
		%>
		
		<tr>
		
		<td>
		<%
		Iterator<Integer> keyIt = lista.keySet().iterator();
		while(keyIt.hasNext())
		{
			out.println("-"+lista.get(keyIt.next())+"<br>");
		}
		%>
		
		</td>
		
		<td>
		<a href="<%=OrgDetails.getAction() %>Vigilanza.do?command=TicketDetails&id=<%=diff.getIdControlloUfficiale()%>&idStabilimentoopu=<%=diff.getIdStabilimento()%>"><%=toDateasString(diff.getAssignedDate()) %></a> 
		</td>
		
		</tr>
		<%
		
			
	}
}

%>
</table>
</dhv:container>