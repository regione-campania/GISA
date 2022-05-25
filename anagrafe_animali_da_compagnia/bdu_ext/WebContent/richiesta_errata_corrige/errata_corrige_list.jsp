<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.richiesteerratacorrige.base.RichiestaErrataCorrige"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../../initPage.jsp" %>

<jsp:useBean id="Animale" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request"/>
<jsp:useBean id="listaErrataCorrige" class="java.util.ArrayList" scope="request" />

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script>
function nuovaRichiesta(riferimentoId, riferimentoIdNomeTab) {
	loadModalWindow();
	window.location.href="GestioneRichiesteErrataCorrige.do?command=NuovaRichiestaErrataCorrige&riferimentoId="+riferimentoId+"&riferimentoIdNomeTab="+riferimentoIdNomeTab;
}

function generaErrataCorrige(id){
	window.open('GestioneRichiesteErrataCorrige.do?command=ModuloRichiestaErrataCorrige&id='+id, 'popupSelect',
    'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}


</script>

 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>

    <br>
   
   
 <%
String param1 = "idAnimale=" + Animale.getIdAnimale() + "&idSpecie=" + Animale.getIdSpecie();
boolean hasRichiesteNonInviate = false;
%>	

<dhv:container name="animale" selected="Gestione Richieste Errata Corrige" object="Animale" param="<%=param1%>">
		
<dhv:permission name="richieste_errata_corrige-view">
  		
<table  class="details" width="50%">
		<tr>
			<th>Data</th>
			<th>Richiedente</th>
			<th>Operazioni</th>
		</tr>

<%
	if (!listaErrataCorrige.isEmpty()) 
	{
		for (RichiestaErrataCorrige richiesta: ((ArrayList<RichiestaErrataCorrige>)listaErrataCorrige))
		{
%>
			<tr>
				<td>
					<%=toDateasString(richiesta.getData())%>
				</td> 
				<td> 
					<dhv:username id="<%=richiesta.getIdUtente()%>"></dhv:username>
				</td>
				<td> 
				<% if (richiesta.getHeaderDocumento()!=null){ %>
				
				<a href="GestioneDocumenti.do?command=DownloadPDF&codDocumento=<%=richiesta.getHeaderDocumento()%>"><input type="button" value="DOWNLOAD"></input></a>
				
				<% } else { hasRichiesteNonInviate = true; %>
				
				
				<a href="#" onClick="generaErrataCorrige('<%=richiesta.getId()%>')">VISUALIZZA</a>
				
				<input type="button" id="bottoneInvia<%=richiesta.getId()%>" onClick="if (confirm('ATTENZIONE. PROSEGUENDO, LA RICHIESTA DI ERRATA CORRIGE VERRÀ INVIATA ALL HELP DESK E NON SARÀ POSSIBILE ANNULLARE L OPERAZIONE. CONFERMARE?')) { this.style.display='none'; openRichiestaPDFRichiestaErrataCorrige('<%=richiesta.getId()%>', '<%=richiesta.getRiferimentoId()%>', '<%=richiesta.getRiferimentoIdNomeTab() %>');}" value="GENERA PDF E INVIA A HELPDESK"/>
				
				
				<%} %>
					
				</td>

			</tr>
<%
		} 
	}
	else 
	{
%>
			<tr>
				<td colspan="3">
					Non sono state generate richieste di errata corrige.
				</td>
			</tr> 
		
<% 	
	} 
%>
		
	
	</table>
</dhv:permission>


<dhv:permission name="richieste_errata_corrige-add">
<center>
<br/>
<a href="#" onClick="nuovaRichiesta('<%=Animale.getIdAnimale()%>', 'animale')">NUOVA RICHIESTA DI ERRATA CORRIGE</a>
</center>
</dhv:permission>


		</dhv:container>

</body>
</html>

<%-- script>
<% if (RichiestaAppenaInserita!=null)	{%>
	if (document.getElementById("bottoneInvia<%=RichiestaAppenaInserita.getId()%>"))
		if (confirm("ATTENZIONE. PROSEGUENDO, LA RICHIESTA DI ERRATA CORRIGE APPENA INSERITA VERRÀ INVIATA ALL'HELP DESK PER ESSERE PROCESSATA E NON SARÀ POSSIBILE ANNULLARE L'OPERAZIONE. CONFERMARE?")) { document.getElementById("bottoneInvia<%=RichiestaAppenaInserita.getId()%>").style.display='none'; openRichiestaPDFRichiestaErrataCorrige('<%=RichiestaAppenaInserita.getId()%>', '<%=RichiestaAppenaInserita.getRiferimentoId()%>', '<%=RichiestaAppenaInserita.getRiferimentoIdNomeTab() %>');}
<% } %>
</script--%>

<% if (hasRichiesteNonInviate)	{%>
<script>
alert("ATTENZIONE! Sono presente richieste di Errata Corrige compilate, ma non inviate all'Help Desk. Si ricorda di premere GENERA PDF ED INVIA A HELP DESK per completare la richiesta.");
</script>
<% } %>