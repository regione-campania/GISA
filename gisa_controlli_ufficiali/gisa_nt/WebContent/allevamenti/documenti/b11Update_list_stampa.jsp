<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*,org.aspcfs.utils.*"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="B11totaliOK" class="java.lang.String" scope="request" />
<jsp:useBean id="B11totaliKO" class="java.lang.String" scope="request" />
<jsp:useBean id="B11totali_2014OK" class="java.lang.String" scope="request" />
<jsp:useBean id="B11totali_2014KO" class="java.lang.String" scope="request" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="allevamenti/documenti/print.css" />

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<br/><br/>

<% int totaleKO = 0;
	int totaleOK = 0;%>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<thead>
	
	<tr><th colspan="10">Log completo degli invii dei CU nel periodo compreso tra <%=request.getParameter("searchtimestampInizio_b11")%> e <%=request.getParameter("searchtimestampFine_b11")%>
	</th> 
	</tr>
	<tr>
		<th nowrap class="formLabel"><strong>DATA OPERAZIONE</strong></th>
	    <th nowrap class="formLabel"><strong>ID CU</strong></th>
		<th nowrap class="formLabel"><strong>DATA CU</strong></th>
		<th nowrap class="formLabel"><strong>CODICE AZIENDA</strong></th>
		<th nowrap class="formLabel"><strong>SPECIE</strong></th>
		<th nowrap class="formLabel"><strong>ESITO INVIO</strong></th>
		<th nowrap class="formLabel"><strong>DATA ESITO</strong></th>
		<th nowrap class="formLabel"><strong>ERRORI</strong></th>
		<th nowrap class="formLabel"><strong>TIPO INVIO</strong></th>
		<th nowrap class="formLabel"><strong>INVIATO DA</strong></th>
	</tr>
	</thead>
	<tbody>
	<% 
		ArrayList<RiepilogoImport> rImport = ( ArrayList<RiepilogoImport> ) request.getAttribute("allRecords");
		if ( rImport.size() > 0 ) { 
			for ( int i=0; i< rImport.size(); i++ ) {
	%>
			<tr class="row<%=i%2%>">
				<td align="right"><%= toDateWithTimeasString(rImport.get(i).getDataOp()) %></td> 
				<td align="right"><%= rImport.get(i).getId_controllo() %></td>
				<td align="right"><zeroio:tz timestamp="<%= rImport.get(i).getData_controllo() %>" dateOnly="true" showTimeZone="true" default="&nbsp;" /></td>
				<td align="right"><%= rImport.get(i).getCodice_azienda() %></td>
				<td align="right"><%= rImport.get(i).getSpecie() %></td>
				<td align="right"><%= rImport.get(i).getEsito() %></td>
				<td align="right"><zeroio:tz timestamp="<%=rImport.get(i).getData_esito() %>" dateOnly="true" showTimeZone="true" default="&nbsp;" /></td>
				<td align="right"><%= (rImport.get(i).getKoLog()!=null) ? rImport.get(i).getKoLog() : "" %></td>	
				<td align="right"><%= rImport.get(i).getTipo_invio() %></td>
				<td align="right"><%= rImport.get(i).getInviato_da() %></td>
				
				<%  if (rImport.get(i).getEsito()!=null){
						if (rImport.get(i).getEsito().equals("OK")) 
							totaleOK++;
						else
							totaleKO++;
				}
				%>
				
			</tr>
			<% } %>
		<% } else { %>
		  <tr class="containerBody">
      			<td colspan="7">
        			<dhv:label name="">Nessun import eseguito.</dhv:label>
      			</td>
    	  </tr>
		<% } %> 
		
		<tr><td colspan="2" style="background-color:#7FFF00"> Totale OK (singola estrazione): </td> <td style="background-color:#7FFF00"> <%=totaleOK %> </td>
		<td colspan="2" style="background-color:#ff9999"> Totale KO (singola estrazione) : </td> <td style="background-color:#ff9999"> <%=totaleKO %> </td></tr>
<%-- 		<tr><td colspan="2" style="background-color:#4AC948"> Totale OK (anno corrente): </td> <td style="background-color:#4AC948"> <%=B11totaliOK %> </td> --%>
<%-- 		<td colspan="2" style="background-color:#e60000"> Totale KO (anno corrente): </td> <td style="background-color:#e60000"> <%=B11totaliKO %> </td></tr> --%>
<%-- 		<tr><td colspan="2" style="background-color:#A0C544"> Totale OK (2014): </td> <td style="background-color:#A0C544"> <%=B11totali_2014OK %> </td> --%>
<%-- 		<td colspan="2" style="background-color:#C24641"> Totale KO (2014): </td> <td style="background-color:#C24641"> <%=B11totali_2014KO %> </td></tr> --%>
	</tbody>
</table>




	