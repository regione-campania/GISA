<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.izsmibr.base.CampioneMolluschi"%>

<%@ page import="java.util.*,org.aspcfs.utils.*"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="InvioMassivo" class="org.aspcfs.modules.izsmibr.base.InvioMassivoMolluschi"
	scope="request" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="allevamenti/documenti/print.css" />

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<br/><br/>

<% int totaleKO = 0;
	int totaleOK = 0;%>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
	
	    <th nowrap class="formLabel"><strong>PIANOCODICE</strong></th>
		<th nowrap class="formLabel"><strong>NUMSCHEDAPRELIEVO</strong></th>
		<th nowrap class="formLabel"><strong>DATAPREL</strong></th>
		<th nowrap class="formLabel"><strong>LUOGOPRELCODICE</strong></th>
		<th nowrap class="formLabel"><strong>METODOCAMPIONAMENTOCODICE</strong></th>
		<th nowrap class="formLabel"><strong>MOTIVOCODICE</strong></th>
		<th nowrap class="formLabel"><strong>PRELNOME</strong></th>
		<th nowrap class="formLabel"><strong>PRELCOGNOME</strong></th>
		<th nowrap class="formLabel"><strong>PRELCODICEFICALE</strong></th>
		<th nowrap class="formLabel"><strong>SITOCODICE</strong></th>
		<th nowrap class="formLabel"><strong>COMUNECODICEISTATPARZIALE</strong></th>
		<th nowrap class="formLabel"><strong>SIGLAPROVINCIA</strong></th>

<th nowrap class="formLabel"><strong>LABCODICE</strong></th>
<th nowrap class="formLabel"><strong>LATITUDINE</strong></th>
<th nowrap class="formLabel"><strong>LONGITUDINE</strong></th>		
<th nowrap class="formLabel"><strong>CODICECONTAMINANTE</strong></th>		
<th nowrap class="formLabel"><strong>PROGCAMPIONE</strong></th>	
<th nowrap class="formLabel"><strong>FOODEXCODICE</strong></th>		

<th nowrap class="formLabel"><strong>PROFFONDALE</strong></th>		
<th nowrap class="formLabel"><strong>CLASSIFICAZIONE</strong></th>			


		<th nowrap class="formLabel"><strong>DATA INVIO</strong></th>
		<th nowrap class="formLabel"><strong>ESITO INVIO</strong></th>
		<th nowrap class="formLabel"><strong>NOTE</strong></th>
		
	</tr>
	
	
	<% 
	
		ArrayList<CampioneMolluschi> rImport = ( ArrayList<CampioneMolluschi> ) request.getAttribute("allRecords");
		if ( rImport.size() > 0 ) {
			
				for ( int i=0; i< rImport.size(); i++ ) {
					
	%>
	
			<tr>
				<td align="right"><%=rImport.get(i).getPianoCodice() %></td>
				<td align="right"><%=rImport.get(i).getNumeroSchedaPrelievo() %></td>
				<td align="right"><%=rImport.get(i).getDataPrel() %></td>
				<td align="right"><%=rImport.get(i).getLuogoPrelievoCodice() %></td>
				<td align="right"><%=rImport.get(i).getMetodoCampionamentoCodice() %></td>
				<td align="right"><%=rImport.get(i).getMotivoCodice() %></td>
				<td align="right"><%=rImport.get(i).getPrelNome() %></td>
				<td align="right"><%=rImport.get(i).getPrelCognome() %></td>
				<td align="right"><%=rImport.get(i).getPrelCodFiscale() %></td>
				<td align="right"><%=rImport.get(i).getSitoCodice() %></td>
				<td align="right"><%=rImport.get(i).getComuneCodiceIstatParziale() %></td>
				<td align="right"><%=rImport.get(i).getSiglaProvincia() %></td>
				<td align="right"><%=rImport.get(i).getLaboratorioCodice() %></td>
				<td align="right"><%=rImport.get(i).getLatitudine() %></td>
				<td align="right"><%=rImport.get(i).getLongitudine() %></td>
				<td align="right"><%=rImport.get(i).getCodiceContaminante() %></td>
				<td align="right"><%=rImport.get(i).getProgressivoCampione() %></td>
				<td align="right"><%=rImport.get(i).getFoodexCodice() %></td>
				<td align="right"><%=rImport.get(i).getProfFondale() %></td>
				<td align="right"><%=rImport.get(i).getClassificazioneDellaZonaDiMareCe8542004() %></td>
				<td align="right"><%= toDateasString(rImport.get(i).getData_invio_bdn())%></td>
				<td align="right"><%= rImport.get(i).getErrore()%></td>
				
			</tr>
			<% } %>
		<% 
			
		} else { %>
		  <tr class="containerBody">
      			<td colspan="17">
        			<dhv:label name="">Nessun import eseguito.</dhv:label>
      			</td>
    	  </tr>
		<% } %> 
	
</table>




	