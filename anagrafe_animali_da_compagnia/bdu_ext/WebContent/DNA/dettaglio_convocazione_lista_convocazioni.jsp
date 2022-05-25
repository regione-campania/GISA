<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%> 
    <%@ page
	import="java.util.*, org.aspcfs.modules.DNA.base.*"%>
    
 <jsp:useBean id="lista" class="org.aspcfs.modules.DNA.base.ListaConvocazione" scope="request"/>
 <jsp:useBean id="statiConvTempList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
	<%@ include file="../initPage.jsp" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista convocazioni</title>
</head>
<body>

<!-- INIZIO -->
<br/>
<table class="details" cellpadding="4" cellspacing="0" border="0"
	width="100%">
	<thead>
		<tr>
			<%-- th width="8">
	      &nbsp;
	    </th --%>
			<th width="16%" nowrap><strong>Denominazione</strong></th>
			<th width="16%" nowrap><strong>Data scadenza convocazione</strong>
			</th>
			<th width="16%" nowrap><strong>Numero convocati</strong>
			</th>
			<th width="16%"><strong>Stato</strong>
			</th>	
			</th>
			
		</tr>
	</thead>
	<tbody>
		<% 
		    ArrayList<ConvocazioneTemporale> convocazioniList = (ArrayList<ConvocazioneTemporale>) lista.getConvocazioniTemporali();
			Iterator itr = convocazioniList.iterator();
			if (itr.hasNext()) {
				int rowid = 0;
				int i = 0;
				while (itr.hasNext()) {
					i++;
					rowid = (rowid != 1 ? 1 : 2);
					ConvocazioneTemporale thisConvocazione = (ConvocazioneTemporale) itr.next();
		%>
		<tr class="row<%=rowid%>">
			<td width="15%" nowrap>			
			<a href="ListaConvocazioneAction.do?command=DettaglioConvocazioneTemporale&idListaConvocazioneTemporale=<%=thisConvocazione.getId() %>"><%= toHtml(thisConvocazione.getDenominazione()) %>	</a>		
			</td>

			<td width="15%" nowrap><%=toDateasString((thisConvocazione.getDataFine()))%></td>
			<td width="15%" nowrap><%=thisConvocazione.getConvocazioni().size() %></td>
			<td width="15%" nowrap><%=statiConvTempList.getSelectedValue((thisConvocazione.getIdStato()))%></td>

		</tr>
		<%
			}
			} else {
				%>
				
						
			   <tr class="containerBody">
		<td colspan="9">Non esistono ancora convocazioni per questa lista proprietari
		</td>
	</tr>
			    <%
			    
		    
	

		}%>
	</tbody>
</table>


</form>



</body>
</html>