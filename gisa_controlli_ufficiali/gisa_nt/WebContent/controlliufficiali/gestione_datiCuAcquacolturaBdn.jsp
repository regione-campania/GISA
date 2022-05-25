<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="DatiCuAcquacolturaBdn" class="org.aspcf.modules.controlliufficiali.base.DatiCuAcquacolturaBdn" scope="request"/>
<jsp:useBean id="Response" class="java.lang.String" scope="request"/>
<jsp:useBean id="SpecieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="criteriList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

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
<tr><th colspan="2">DATI CU ACQUACOLTURA</th></tr>

<tr><td>ID CONTROLLO</td> <td> <%=TicketDetails.getId() %> </td> </tr>
<tr><td>CODICE ASL</td> <td><%=toHtml(DatiCuAcquacolturaBdn.getAslCodice()) %></td></tr>
<tr><td>CODICE AZIENDA</td> <td><%=toHtml(DatiCuAcquacolturaBdn.getAziCodice()) %></td></tr>
<tr><td>CODICE FISCALE PROPRIETARIO</td> <td><%=toHtml(DatiCuAcquacolturaBdn.getIdFiscaleProprietario()) %></td></tr>
<tr><td>CODICE GRUPPO SPECIE</td> <td><%=toHtml(SpecieList.getSelectedValue(DatiCuAcquacolturaBdn.getGspCodice())) %></td></tr>
<tr><td>CODICE CRITERIO O MOTIVO CONTROLLO</td> <td><%=toHtml(criteriList.getSelectedValue(DatiCuAcquacolturaBdn.getCritCodice())) %></td></tr>
<tr><td>DATA CONTROLLO</td> <td><%=toDateasStringFromString(DatiCuAcquacolturaBdn.getDataControllo()) %></td></tr>

<% 
	if (DatiCuAcquacolturaBdn.getDataInvio()==null) 
	{ 
%>
		<tr>
			<td colspan="2">
				<center>
					<input type="button" value="MODIFICA DATI" onClick="loadModalWindow(); window.location.href='PrintReportVigilanza.do?command=AggiungiDatiCuAcquacoltura&idControllo=<%=TicketDetails.getId()%>'"/>
					<input type="button" value="INVIA IN BDN" onClick="if (confirm('INVIARE IN BDN?')) { loadModalWindow(); window.location.href='PrintReportVigilanza.do?command=InviaDatiCuAcquacolturaBdn&idControllo=<%=TicketDetails.getId()%>'}"/>
				</center>
			</td>
		</tr>
<% 
	}
   	else 
   	{
%>
		<tr>
			<td>
				DATA INVIO
			</td> 
			<td>
				<%=toDateasString(DatiCuAcquacolturaBdn.getDataInvio()) %>
			</td>
		</tr>
<% 
	} 
%>
	<tr>
		<td colspan="2">
			<center>
				<input type="button" value="CANCELLA IN BDN" onClick="if (confirm('CANCELLARE IN BDN?')) { loadModalWindow(); window.location.href='PrintReportVigilanza.do?command=DeleteDatiCuAcquacolturaBdn&idControllo=<%=TicketDetails.getId()%>'}"/>
			</center>
		</td>
	</tr>
</table>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
