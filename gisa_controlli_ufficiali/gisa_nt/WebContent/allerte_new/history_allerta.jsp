<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.allerte_new.base.AllerteHistory"%>

<script>
function nascondi(id,i)
{
	
	if (document.getElementById('riga_'+id).style.display!='')
	{
		if (document.getElementById('last_click').value!='-1')
		{
			document.getElementById('riga_'+document.getElementById('last_click').value).style.display='none'
			
		}
		
		document.getElementById('riga_'+id).style.display=''
		document.getElementById('last_click').value=id;
		
	}
	/*else
	{
		document.getElementById('riga_'+id).style.display=''
	}*/
	
}
function inRow(riga)
{
	riga.style.background='#FFF5EE';
}
function outRow(i,riga)
{
	if (i==1)
	{
		riga.style.background='#EDEDED';
	}
	else
	{
		riga.style.background='#FFFFFF';
	}
}
</script>

<%@page import="org.aspcfs.modules.allerte_new.base.AllerteAslHistory"%>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="HistoryAllerte" class="java.util.ArrayList" scope="request"/>
<table class="trails" cellspacing="0">
<tr>
	<td>
		<a href="TroubleTicketsAllerteNew.do"><dhv:label name="sanzioniss">Allerta</dhv:label></a> >
		<a href="TroubleTicketsAllerteNew.do?command=Details&id=<%=request.getAttribute("id_allerta") %>"><dhv:label name="sanzioniss">Allerta</dhv:label></a> >
		Storico Allerta
	</td>
</tr>
</table>
<%@ include file="../initPage.jsp" %>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
<tr>
	<th colspan="4"><strong><center>Storico Allerta</center></strong></th>
</tr>
<tr>
<th>Tipo Operazione</th>
		<th>Data Operazione</th>
		<th>Motivazione</th>
		<th>Effettuata Da</th>
	</tr>
<%Iterator<AllerteHistory> itHistory = (Iterator<AllerteHistory>) HistoryAllerte.iterator(); 
int rowid = 0;
int i = 0;
while (itHistory.hasNext())
{
	AllerteHistory curr = itHistory.next();
	i++;
    rowid = (rowid != 1 ? 1 : 2);
%>

	<tr class="row<%= rowid %>" onmouseover="inRow(this)" onmouseout="outRow(<%=rowid %>,this)" onclick="nascondi(<%=curr.getId() %>,<%=rowid %>)"><td><%=curr.getTipo_operazione() %></td>
		<td><%=curr.getData_operazione() %></td>
		<td><%=toHtml(curr.getMotivo()) %></td>
		<td><%=curr.getNominativo() %></td>
	</tr>
<%
ArrayList<AllerteAslHistory> historyAsl = curr.getLista_dettaglio_storia();
if (historyAsl.size()>0)
{
%>
<tr id ="riga_<%=curr.getId() %>" style="display: none">
<td colspan="5">
<table cellpadding="4" cellspacing="0" width="100%" >
<tr style="background-color: #FFE4C4;">
		<td><b>Asl Coinvolta</b></td>
		<td><b>Operazione</b></td>
		<td><b>Cu Pianificati</b></td>
		<td><b>Stato</b></td>
		<td><b>Stato Allegato F</b></td>
		</tr>
<% 
Iterator<AllerteAslHistory> itHistoryAsl = (Iterator<AllerteAslHistory>)historyAsl.iterator();
while (itHistoryAsl.hasNext())
{
	AllerteAslHistory aslHistory = itHistoryAsl.next();
%>
<tr style="background-color: #FFE4C4;">
	<td><%=SiteIdList.getSelectedValue(aslHistory.getAsl()) %></td> 
	<td><%=aslHistory.getTipo_operazione() %></td>
	<td><%=aslHistory.getCu_pianificati_regione() %></td>
	<td><%=aslHistory.getStato() %></td>
	<td><%=toHtml(aslHistory.getStato_allegatof()) %></td>
</tr>

<%	
}%>

</table>
</td>
</tr>
	
<%
}
else
{
%>
<tr id ="riga_<%=curr.getId() %>" style="display: none">
<td colspan="5">
<table cellpadding="4" cellspacing="0" width="100%" class="details" style="background-color: #FFE4C4;">
<tr style="background-color: #FFE4C4;">	
		<td><b>Asl Coinvolta</b></td>
		<td><b>Operazione</b></td>
		<td><b>Cu Pianificati</b></td>
		<td><b>Stato</b></td>
		<td><b>Stato Allegato F</b></td></tr>
<tr style="background-color: #FFE4C4;"><td colspan="4"> Dettaglio Non Disponibile su questa Operazione </td></tr>		
</table>
		
</td>
</tr>

<%	
}
}
%>
	</table>
<input type = "hidden" id ="last_click" value = "-1"> 		


