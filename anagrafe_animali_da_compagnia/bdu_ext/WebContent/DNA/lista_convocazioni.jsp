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
    
 <jsp:useBean id="convocazioniList" class="org.aspcfs.modules.DNA.base.ListaConvocazioneList" scope="request"/>
 <jsp:useBean id="statiList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
 <jsp:useBean id="circoscrizioniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
	
	<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
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
			<th width="16%" nowrap><strong>Comune</strong>
			</th>
			<th width="16%"><strong>Circoscrizione</strong>
			</th>
			<th width="16%" nowrap><strong>Data inizio</strong>
			</th>
			<th width="16%" nowrap><strong>Data fine</strong>
			</th>
			<th width="16%" nowrap><strong>Stato</strong>
			</th>
			
		</tr>
	</thead>
	<tbody>
		<%
			Iterator itr = convocazioniList.iterator();
			if (itr.hasNext()) {
				int rowid = 0;
				int i = 0;
				while (itr.hasNext()) {
					i++;
					rowid = (rowid != 1 ? 1 : 2);
					ListaConvocazione thisLista = (ListaConvocazione) itr.next();
		%>
		<tr class="row<%=rowid%>">
		
			<td width="15%" nowrap>
			
			<% if (thisLista.getIdStato()== ListaConvocazione.terminato_senza_errori || thisLista.getIdStato()== ListaConvocazione.terminato_con_errori){%>
			<a href="ListaConvocazioneAction.do?command=DettaglioListaConvocazione&idLista=<%=thisLista.getIdListaConvocazione() %>"> <%=toHtml(thisLista.getDenominazione()) %></a></td>
			<%} else { %>
			<%=toHtml(thisLista.getDenominazione()) %>
			<%} %>
			
			</td>
			<td width="15%" nowrap><%=toHtml(comuniList.getSelectedValue(thisLista.getIdComune()))%></td>
			<td width="15%" nowrap><%=circoscrizioniList.getSelectedValue(thisLista.getIdCircoscrizione())%></td>
			<td width="15%" nowrap><%=toHtml(toDateString(thisLista.getDataInizio()))%></td>
			<td width="15%" nowrap><%=toHtml(toDateString(thisLista.getDataFine()))%></td>
			<td width="15%" nowrap><%=toHtml(statiList.getSelectedValue(thisLista
										.getIdStato()))%>
			<% if (thisLista.getIdStato()== ListaConvocazione.terminato_senza_errori || thisLista.getIdStato()== ListaConvocazione.terminato_con_errori){%>
			<br>
			<a href="ListaConvocazioneAction.do?command=VisualizzaFilesEsito&idListaConvocazione=<%=thisLista.getIdListaConvocazione()%>"><span style="font-size: 8px">File lista nominativi</span></a>
			<a href="ListaConvocazioneAction.do?command=VisualizzaFilesEsito&idListaConvocazione=<%=thisLista.getIdListaConvocazione()%>&errore=true"><span style="font-size: 8px">File segnalazioni/errori</span></a><br>
			<%} %>
		    </td>
			
			
		</tr>
		<%
			}
			} else {
				%>
				
						
			   <tr class="containerBody">
		<td colspan="9">Non sono state trovate convocazioni.
		</td>
	</tr>
			    <%
			    
		    
	

		}%>
	</tbody>
</table>





</body>
</html>