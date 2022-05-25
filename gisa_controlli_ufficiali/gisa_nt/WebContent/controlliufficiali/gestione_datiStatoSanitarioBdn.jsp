<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="DatiStatoSanitarioBdn" class="org.aspcf.modules.controlliufficiali.base.DatiStatoSanitarioBdn" scope="request"/>
<jsp:useBean id="Response" class="java.lang.String" scope="request"/>
<jsp:useBean id="qualificaSanitariaList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="malattieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

 <script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
<% 
if (Response != null && !Response.equals("")) 
{ 
%>
<table width="100%">
<tr style="background:yellow"><th colspan="2">ESITO INVIO</th></tr>
<tr><td><%=Response %>
</td></tr>
</table>
<br/>
<% } %>	
	
	
<table class="details" width="100%">
<tr><th colspan="2">DATI STATO SANITARIO</th></tr>

<tr><td>ID CONTROLLO</td> <td> <%=OrgDetails.getId() %> </td> </tr>
<tr><td>CODICE ASL</td> <td><%=toHtml(DatiStatoSanitarioBdn.getAslCodice()) %></td></tr>
<tr><td>CODICE AZIENDA</td> <td><%=toHtml(DatiStatoSanitarioBdn.getAziCodice()) %></td></tr>
<tr><td>CODICE GRUPPO SPECIE</td> <td><%=toHtml(SpecieList.getSelectedValue(DatiStatoSanitarioBdn.getGspCodice())) %></td></tr>
<tr><td>CODICE MALATTIA</td> <td><%=toHtml(malattieList.getSelectedValue(DatiStatoSanitarioBdn.getMalCodice())) %></td></tr>
<tr><td>CODICE QUALIFICA SANITARIA</td> <td><%=toHtml(qualificaSanitariaList.getSelectedValue(DatiStatoSanitarioBdn.getQsaCodice())) %></td></tr>
<tr><td>DATA INIZIO VALIDITA'</td> <td><%=toDateasStringFromString(DatiStatoSanitarioBdn.getDtInizioValidita()) %></td></tr>
<tr><td>DATA INVIO</td><td><%=toDateasString(DatiStatoSanitarioBdn.getDataInvio()) %></td></tr>
	<tr>
		<td colspan="2">
			<center>
				<input type="button" value="MODIFICA DATI" onClick="loadModalWindow(); window.location.href='PrintReportVigilanza.do?command=AggiungiDatiStatoSanitario&idAzienda=<%=OrgDetails.getOrgId()%>'"/>
<%
if(DatiStatoSanitarioBdn.getIdBdn()==null)
{
%>				
				<input type="button" value="INVIA IN BDN" onClick="if (confirm('INVIARE IN BDN?')) { loadModalWindow(); window.location.href='PrintReportVigilanza.do?command=InviaDatiStatoSanitarioBdn&idAzienda=<%=OrgDetails.getOrgId()%>'}"/>
<%
}
%>
				<input type="button" value="CANCELLA IN BDN" onClick="if (confirm('CANCELLARE IN BDN?')) { loadModalWindow(); window.location.href='PrintReportVigilanza.do?command=DeleteDatiStatoSanitarioBdn&idAzienda=<%=OrgDetails.getOrgId()%>'}"/>
			</center>
		</td>
	</tr>
</table>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
