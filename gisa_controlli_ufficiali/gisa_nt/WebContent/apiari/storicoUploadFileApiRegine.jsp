<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="ext.aspcfs.modules.apiari.base.StoricoImport"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<jsp:useBean id="storicoImportList" class="ext.aspcfs.modules.apiari.base.StoricoImport" scope="request" />
<jsp:useBean id="StoricoUploadFileApiRegineInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session" />

<%@ include file="../initPage.jsp"%>

<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>

<table class="trails" cellspacing="0">
	<tr>
		<td width="100%">APICOLTURA > STORICO UPLOAD FILE API REGINE</td>
	</tr>
</table>
<br>
	<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
		<tr>
			<th>DATA UPLOAD</th>
			<th>UTENTE UPLOAD</th>
			<th>ESITO ELABORAZIONE</th>
			<th>NOME FILE</th>
			<th>RECUPERA FILE</th>
		</tr>
<%
			
			Iterator j = storicoImportList.iterator();
			if (j.hasNext()) 
			{
				int rowid = 0;
				int i = 0;
				while (j.hasNext()) 
				{
					i++;
					rowid = (rowid != 1 ? 1 : 2);
					StoricoImport storicoTemp = (StoricoImport) j.next();
%>

					<tr class="row<%=rowid%>">
						<td><%= toDateasString(storicoTemp.getDataImport())%></td>
						<td><dhv:username id="<%=storicoTemp.getIdUtente()%>"></dhv:username></td>
						<td>
<%
							if( (storicoTemp.getErroreInsert()!=null && !storicoTemp.getErroreInsert().equals("")) || (storicoTemp.getErroreParsingFile()!=null && !storicoTemp.getErroreParsingFile().equals("")))
							{
%>
								<a href="#" onClick="window.open('ApicolturaMovimentazioni.do?command=VediErrori&idImport=<%=storicoTemp.getId()%>','popupSelect',
					              'height=700px,width=700px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes')
									">Vedi</a>
<%
							}
							else
							{
								out.println("N.D.");
							}
%>
						</td>
						<td><%=storicoTemp.getNomeFile() %></td>
						<td>
<%
						   String url = "ApicolturaMovimentazioni.do?command=DownloadImport&idImport=" + storicoTemp.getId();
							if(storicoTemp.getCodDocumento()!=null && !storicoTemp.getCodDocumento().equals(""))
							{
								String estensione = storicoTemp.getNomeFile().split("\\.")[1];
								url = "GestioneAllegatiUpload.do?command=DownloadPDF&codDocumento=" + storicoTemp.getCodDocumento() + "&tipoDocumento="+estensione+"&nomeDocumento=" + storicoTemp.getNomeFile();
							}
%>	
							<a href="#" onClick="openPopup('<%=url%>')">Download</a> 
					</tr>
<%
				}
			} 
			else 
			{
%>
				<tr class="containerBody">
					<td colspan="11">NON ESISTONO UPLOAD</td>
				</tr>
<%
			}
%>
	  </table>
	<br />
	<dhv:pagedListControl object="StoricoUploadFileApiRegineInfo" tdClass="row1" />
	
	
	<script>
		function openPopup(link)
		{
		  var res;
	        var result;
	        	 window.open(link,'popupSelect',
	              'height=400px,width=400px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

		}
		
		
		</script>