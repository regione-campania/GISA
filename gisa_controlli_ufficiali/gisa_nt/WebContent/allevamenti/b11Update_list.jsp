<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*,org.aspcfs.utils.*"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../initPage.jsp"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="dataEstrazione" class="java.lang.String"
	scope="request" />
	
	 <dhv:container name="inviocuba" selected="Invia" object="">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="Stabilimenti.do"><dhv:label
			name="">B11 - Sicurezza alimentare</dhv:label></a> > <dhv:label
			name="">Importa B11</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>

<br>

<P style="text-align: center; color: red; font-size:14px">

<% if (request.getParameter("searchtimestampInizio_b11")!=null) {%>
Di seguito verranno mostrati a video i primi 10 risultati dell'invio dei CU tramite WS.<br>
Clicca sul bottone "Genera PDF" per visualizzare il log completo degli invii dei CU nel periodo compreso tra [<%=request.getParameter("searchtimestampInizio_b11")%>] e [<%=request.getParameter("searchtimestampFine_b11")%>]
 
</P>

  <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
<div align="right">	 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Genera PDF" value="Genera PDF"		onClick="openRichiestaPDF_LogB11('<%=dataEstrazione %>', '<%=request.getParameter("searchtimestampInizio_b11")%>', '<%=request.getParameter("searchtimestampFine_b11")%>', '<%=request.getParameter("tipo")%>');">
        </div>
        
<% } %>
<% if (request.getParameter("idControllo")!=null) {%>
Esito invio per il CU [<%=request.getParameter("idControllo")%>]
<% } %>
        
<br/>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th nowrap class="formLabel"><strong>DATA OPERAZIONE</strong></th>
	    <th nowrap class="formLabel"><strong>ID CU</strong></th>
		<th nowrap class="formLabel"><strong>DATA CU</strong></th>
		<th nowrap class="formLabel"><strong>CODICE AZIENDA</strong></th>
		<th nowrap class="formLabel"><strong>SPECIE</strong></th>
		<th nowrap class="formLabel"><strong>ESITO INVIO</strong></th>
		<th nowrap class="formLabel"><strong>ERRORI</strong></th>
		<th nowrap class="formLabel"><strong>TIPO INVIO</strong></th>
		<th nowrap class="formLabel"><strong>INVIATO DA</strong></th>
	</tr>
	
	<% 
		ArrayList<RiepilogoImport> rImport = ( ArrayList<RiepilogoImport> ) request.getAttribute("allRecords");
		if ( rImport.size() > 0 ) {
			
			if(rImport.size() > 10) {
				for ( int i=0; i< 10; i++ ) {
	%>
			<tr>
				<td align="right"><%= toDateWithTimeasString(rImport.get(i).getDataOp()) %></td>
				<td align="right"><%= rImport.get(i).getId_controllo() %></td>
				<td align="right"><zeroio:tz timestamp="<%= rImport.get(i).getData_controllo() %>" dateOnly="true" showTimeZone="true" default="&nbsp;" /></td>
				<td align="right"><%= rImport.get(i).getCodice_azienda() %></td>
				<td align="right"><%= rImport.get(i).getSpecie() %></td>
				<td align="right"><%= rImport.get(i).getEsito() %></td>
				<td align="right"><%= (rImport.get(i).getKoLog()!=null) ? rImport.get(i).getKoLog() : "&nbsp;" %></td>	
				<td align="right"><%= rImport.get(i).getTipo_invio() %></td>
				<td align="right"><%= rImport.get(i).getInviato_da() %></td>
				
			</tr>
			<% } %>
		<% }else {
				for ( int i=0; i< rImport.size(); i++ ) {
			%>
					<tr>
					<td align="right"><%= toDateWithTimeasString(rImport.get(i).getDataOp()) %></td>
					<td align="right"><%= rImport.get(i).getId_controllo() %></td>
					<td align="right"><zeroio:tz timestamp="<%= rImport.get(i).getData_controllo() %>" dateOnly="true" showTimeZone="true" default="&nbsp;" /></td>
					<td align="right"><%= rImport.get(i).getCodice_azienda() %></td>
					<td align="right"><%= rImport.get(i).getSpecie() %></td>
					<td align="right"><%= rImport.get(i).getEsito() %></td>
					<td align="right"><%= (rImport.get(i).getKoLog()!=null) ? rImport.get(i).getKoLog() : "&nbsp;" %></td>	
					<td align="right"><%= rImport.get(i).getTipo_invio() %></td>
					<td align="right"><%= rImport.get(i).getInviato_da() %></td>
					
				</tr>
			<% } 
			}//minori di 10...
			
		} else { %>
		  <tr class="containerBody">
      			<td colspan="7">
        			<dhv:label name="">Nessun import eseguito.</dhv:label>
      			</td>
    	  </tr>
		<% } %> 
	
</table>

</dhv:container>


<script>
if (window.opener!=null){
	window.opener.loadModalWindow();
	window.opener.location.href='AllevamentiVigilanza.do?command=TicketDetails&id=<%=request.getParameter("idControllo")%>';
}
</script>

	