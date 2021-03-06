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

<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.ricercaunica.base.RicercaOpu" scope="request"/>
<jsp:useBean id="listaErrataCorrige" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="op" class="java.lang.String" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="StabilimentoSintesisDettaglio" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>

<%--jsp:useBean id="RichiestaAppenaInserita" class="org.aspcfs.modules.richiesteerratacorrige.base.RichiestaErrataCorrige" scope="request"/--%>


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script>
function nuovaRichiesta(riferimentoId, riferimentoIdNomeTab, op) {
	loadModalWindow();
	window.location.href="GestioneRichiesteErrataCorrige.do?command=NuovaRichiestaErrataCorrige&riferimentoId="+riferimentoId+"&riferimentoIdNomeTab="+riferimentoIdNomeTab+"&op="+op;
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
String nomeContainer = op;
String obj = "OrgDetails";
String param1 = "orgId=" + Stabilimento.getRiferimentoId(); 
String param2 = "stabId=" ; 
String param3 = "altId=" ;
String param ="";
if (OrgDetails!=null && OrgDetails.getOrgId()>0)
	param = param1;
else if (StabilimentoDettaglio!=null && StabilimentoDettaglio.getIdStabilimento()>0){
	param = param2+StabilimentoDettaglio.getIdStabilimento()+"&opId="+StabilimentoDettaglio.getIdOperatore();
	obj = "Operatore";
    request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
}
else if (StabilimentoSintesisDettaglio!=null && StabilimentoSintesisDettaglio.getAltId()>0){
	param = param3+StabilimentoSintesisDettaglio.getAltId()+"&opId="+StabilimentoSintesisDettaglio.getIdOperatore();
	obj = "Operatore";
    request.setAttribute("Operatore",StabilimentoSintesisDettaglio.getOperatore());
}
boolean hasRichiesteNonInviate = false;
%>	
<dhv:container name="<%=nomeContainer %>"  selected="Gestione Richieste Errata Corrige" object="<%=obj%>" param="<%=param%>"  hideContainer="false">
  


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
					<%=toDateasStringWitTime(richiesta.getData())%>
				</td> 
				<td> 
					<dhv:username id="<%=richiesta.getIdUtente()%>"></dhv:username>
				</td>
				<td> 
				<% if (richiesta.getHeaderDocumento()!=null){ %>
				
				<a href="GestioneDocumenti.do?command=DownloadPDF&codDocumento=<%=richiesta.getHeaderDocumento()%>"><input type="button" value="DOWNLOAD"></input></a>
				
				<% } else { hasRichiesteNonInviate = true; %>
				
				
				<a href="#" onClick="generaErrataCorrige('<%=richiesta.getId()%>')">VISUALIZZA</a>
				
				<input type="button" id="bottoneInvia<%=richiesta.getId()%>" onClick="if (confirm('ATTENZIONE. PROSEGUENDO, LA RICHIESTA DI ERRATA CORRIGE VERR? INVIATA ALL HELP DESK E NON SAR? POSSIBILE ANNULLARE L OPERAZIONE. CONFERMARE?')) { this.style.display='none'; openRichiestaPDFRichiestaErrataCorrige('<%=richiesta.getId()%>', '<%=richiesta.getRiferimentoId()%>', '<%=richiesta.getRiferimentoIdNomeTab() %>');}" value="GENERA PDF E INVIA A HELPDESK"/>
				
				
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
<a href="#" onClick="nuovaRichiesta('<%=Stabilimento.getRiferimentoId()%>', '<%=Stabilimento.getRiferimentoIdNomeTab()%>', '<%=op%>')">NUOVA RICHIESTA DI ERRATA CORRIGE</a>
</center>
</dhv:permission>


		</dhv:container>

</body>
</html>

<%-- script>
<% if (RichiestaAppenaInserita!=null)	{%>
	if (document.getElementById("bottoneInvia<%=RichiestaAppenaInserita.getId()%>"))
		if (confirm("ATTENZIONE. PROSEGUENDO, LA RICHIESTA DI ERRATA CORRIGE APPENA INSERITA VERR? INVIATA ALL'HELP DESK PER ESSERE PROCESSATA E NON SAR? POSSIBILE ANNULLARE L'OPERAZIONE. CONFERMARE?")) { document.getElementById("bottoneInvia<%=RichiestaAppenaInserita.getId()%>").style.display='none'; openRichiestaPDFRichiestaErrataCorrige('<%=RichiestaAppenaInserita.getId()%>', '<%=RichiestaAppenaInserita.getRiferimentoId()%>', '<%=RichiestaAppenaInserita.getRiferimentoIdNomeTab() %>');}
<% } %>
</script--%>

<% if (hasRichiesteNonInviate)	{%>
<script>
alert("ATTENZIONE! Sono presente richieste di Errata Corrige compilate, ma non inviate all'Help Desk. Si ricorda di premere GENERA PDF ED INVIA A HELP DESK per completare la richiesta.");
</script>
<% } %>