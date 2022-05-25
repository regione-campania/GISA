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
<jsp:useBean id="DiffideListStorico" class="org.aspcfs.modules.diffida.base.TicketList" scope="request"/>

<table>
<tr>

<%

Iterator it1 = DiffideList.iterator();
int count = 0;
if (it1.hasNext())
{
%>
<td>
<div align="left" style="float:left;">
<table cellpadding="2" cellspacing="0" border="0" class="pagedList" >
    
    
       <tr>
	 
      <th colspan="2"><strong>Elenco Diffide</th>
</tr>
        <tr>
	 
	   <th><strong><dhv:label name="">Diffidato Per</dhv:label></strong></th>
      <th ><strong><dhv:label name="">Data</dhv:label></strong></th>
</tr>

	
<% 	while(it1.hasNext())
	{ 
		Ticket diff = (Ticket)it1.next();
		HashMap<Integer,String> lista= diff.getListaNorme();
		if(lista.size() > 0) {
			count=count+1;
		
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
		<a href="OpuStabVigilanza.do?command=TicketDetails&id=<%=diff.getIdControlloUfficiale()%>&stabId=<%=diff.getIdStabilimento()%>&idStabilimentoopu=<%=diff.getIdStabilimento()%>"><%=toDateasString(diff.getAssignedDate()) %></a> 
		</td>
		</tr>
       	<%
	
		}//if
		else { 
			count = count+0;
		}
		
	}
	%>
</table>
</div>
</td>
<%
	}
	it1 = DiffideListStorico.iterator();
	Iterator it2 = DiffideListStorico.iterator();
	int countStorico = 0;
	
	boolean saltaPregresse = false;
	while(it2.hasNext())
	{ 
		Ticket diff = (Ticket)it2.next();
		HashMap<Integer,String> lista= diff.getListaNorme();
		
		Iterator<Integer> keyItCheckStato = lista.keySet().iterator();
		
		while(keyItCheckStato.hasNext())
		{
			if(lista.get(keyItCheckStato.next()).toUpperCase().contains("STATO SANZIONATO PER QUESTA NORMA"))
				saltaPregresse=true;
		}
	}
		
		
		
	if (it1.hasNext() && !saltaPregresse)
	{
%>
	<td>
		<div align="<%=(count>0)?("right"):("left") %>" style="float:left;">
			<table cellpadding="2" cellspacing="0" border="0" class="pagedList" >
 				<tr>
      				<th style="background-color:#86ff6d;" colspan="3"><strong>Elenco Diffide Pregresse</th>
				</tr>
        		<tr>
	   				<th style="background-color:#86ff6d;" ><strong><dhv:label name="">Diffidato Per</dhv:label></strong></th>
	   				<th style="background-color:#86ff6d;" ><strong><dhv:label name="">Partita iva prima della variazione di titolarità</dhv:label></strong></th>
      				<th style="background-color:#86ff6d;" ><strong><dhv:label name="">Data</dhv:label></strong></th>
				</tr>
	
<% 				while(it1.hasNext())
				{ 
					Ticket diff = (Ticket)it1.next();
					HashMap<Integer,String> lista= diff.getListaNorme();
					
					
					Iterator<Integer> keyItCheckStato = lista.keySet().iterator();
					boolean salta = false;
					while(keyItCheckStato.hasNext())
					{
						if(lista.get(keyItCheckStato.next()).toUpperCase().contains("STATO SANZIONATO PER QUESTA NORMA"))
							salta=true;
					}
					
					
					
					if(lista.size() > 0 && !salta)
					{
						countStorico=countStorico+1;
		
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
						<%=((diff.getPartitaIvaVecchia()!=null)?(diff.getPartitaIvaVecchia()):(""))%>
					</td>

					<td>		
						<a href="OpuStabVigilanza.do?command=TicketDetails&id=<%=diff.getIdControlloUfficiale()%>&stabId=<%=diff.getIdStabilimento()%>&idStabilimentoopu=<%=diff.getIdStabilimento()%>"><%=toDateasString(diff.getAssignedDate()) %></a> 
					</td>
				</tr>
<%
				
		
			}//if
			else 
			{ 
				countStorico = countStorico+0;
			}
		
		}
%>
</table>
</div>
</td>

<% 
}
%>
</tr>
</table>	
<%	
if(count>0 || countStorico>0)
{
%>	
	<br>
	<br>
<%
}
%>
<%-- if(count == 0){ 
	<tr>
		<td>
			NESSUNA DIFFIDA PER QUESTO OSA.
		</td>
	</tr>	
<% } %>
--%>


