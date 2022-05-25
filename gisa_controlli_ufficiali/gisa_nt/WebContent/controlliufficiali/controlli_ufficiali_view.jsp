<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.checklist.base.Audit"%>

<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%>
<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%>
<%@page import="org.aspcf.modules.controlliufficiali.base.Piano"%>
<%@page import="org.aspcfs.modules.soa.base.LineaAttivitaSoa"%>
<%@page import="org.aspcfs.modules.lineeattivita.base.LineeAttivita"%>
<jsp:useBean id="BufferDetails" class="org.aspcfs.modules.buffer.base.Buffer" scope="request"/>
<jsp:useBean id="titoloNucleoTest" class="org.aspcfs.utils.web.LookupList" scope="request"/>
  <jsp:useBean id="View" class="java.lang.String" scope="request"/>

<jsp:useBean id="VerificaQuantitativo" class="org.aspcfs.utils.web.LookupList"
	scope="request" />	
<script
	type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<!-- <link rel="stylesheet" -->
<!-- 	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" /> -->
<!-- <script src="http://code.jquery.com/jquery-1.8.2.js"></script> -->
<!-- <script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script> -->

<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>


<script type="text/javascript">

       	var msg_1 = '';
       	var principale_glob ;
			function controlloChecklist(msg,principale,userId)
			{

				msg_1 = msg;
				orgId = document.details.orgId.value;
				
				idCu = document.details.id.value;
				principale_glob = principale ;
				PopolaCombo.controlloAperturaChecklist(orgId,idCu,userId,viewMessageCallback) ;
			}
	
			function viewMessageCallback (returnValue)
			{

				
				if (returnValue == "")
				{
					if(document.details.assetId!=null)
					{
						compilaCheckList(msg_1,<%=TicketDetails.getOrgId()%>,<%=TicketDetails.getAssetId()%>,<%=TicketDetails.getId()%>,<%=TicketDetails.getPaddedId()%>,principale_glob,'details')

					}
					else
					{
						compilaCheckList(msg_1,<%=TicketDetails.getOrgId()%>,<%=TicketDetails.getId()%>,<%=TicketDetails.getPaddedId()%>,principale_glob,'details')
						
					}
				}
				else
				{
					alert ('ATTENZIONE! Per poter avere un punteggio checklist attendibile bisognerebbe provvedere alla chiusura dei seguenti controlli (Dalla seguente lista sono esclusi i controlli ufficiali con campioni/tamponi in attesa di esito)\n'+returnValue);
					//if(confirm('ATTENZIONE: per poter inserire la checklist occorre provvedere alla chiusura dei seguenti controlli \n'+returnValue+". Continuare?")==true)
					//compilaCheckList(msg_1,<%=TicketDetails.getOrgId()%>,<%=TicketDetails.getId()%>,<%=TicketDetails.getPaddedId()%>,principale_glob,'details')
				}
				
			}			

var flagJava ;
function controllo_java() 
{
	var nAgt = navigator.userAgent; 
	var fullVersion  = ''+parseFloat(navigator.appVersion); 
	verOffset=nAgt.indexOf("Firefox")
	fullVersion = nAgt.substring(verOffset+8);
	flagJava=navigator.javaEnabled();
	fullVersion = nAgt.substring(verOffset+8);
// 	if (flagJava==false) 
// 	 alert('Attenzione!! java non è supportato dal tuo browser, non è garantito il salvataggio in modalità offline.!');
	
}
			</script>
			
<%
String errore = "";
errore = (String)request.getAttribute("Error");
%>
<% if (errore != null && (!errore.equalsIgnoreCase(""))) { %>
<script>
alert("<%=errore%>");
</script>

<% } %>

<input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">

<%
	if (TicketDetails.getTipoCampione() == 5) {
%>
<center><a href="#dialog4" name="modal"><b>Guida alla
compilazione CheckList</b></a></center>
<br>
<br>
<%
	}
%>
<tr>
	<th colspan="2">
	Scheda Controllo Ufficiale 
  </th>
</tr>

<%String stato ="";
if (TicketDetails.getStatusId()==TicketDetails.STATO_APERTO)
	stato="<font color=\"green\">Aperto</font>";
else if (TicketDetails.getStatusId()==TicketDetails.STATO_CHIUSO)
	stato="<font color=\"red\">Chiuso</font>";
else if (TicketDetails.getStatusId()==TicketDetails.STATO_RIAPERTO)
	stato="<font color=\"orange\">Riaperto</font>";
else if (TicketDetails.getStatusId()==TicketDetails.STATO_ANNULLATO)
	stato="<font color=\"red\"><strike>Disattivato</strike></font>";
%>
<tr class="containerBody"><td class="formLabel">Stato Controllo</td><td><%=stato %></td></tr>

<tr class="containerBody">
	<td nowrap class="formLabel"><dhv:label name="stabilimenti.site">Site</dhv:label></td>
	<td><%=SiteIdList.getSelectedValue(TicketDetails.getSiteId())%> 
	<input type="hidden" name="siteId" value="<%=TicketDetails.getSiteId()%>"></td>
</tr>

<input type="hidden" name="id" id="id"
	value="<%=TicketDetails.getId()%>" />

<% if(TicketDetails.getId() == -1 ) {%>
	<script> alert('Contattare il servizio HD in quanto il controllo non è stato inserito in maniera corretta.');</script>
<% } %>

<tr class="containerBody">
	<td class="formLabel"><dhv:label name="">Identificativo C.U.</dhv:label>
	</td>


	<td><%=toHtml(TicketDetails.getPaddedId())%> <input type="hidden"
		name="idControlloUfficiale" id="idControlloUfficiale"
		value="<%=TicketDetails.getPaddedId()%>" /> <input type="hidden"
		name="idC" id="idC" value="<%=TicketDetails.getPaddedId()%>" /></td>

</tr>


<%@ include file="../controlliufficiali/controlli_ufficiali_view_tipo.jsp" %>

<%

	if (TicketDetails.isCategoriaisAggiornata() == false) {
		if (TicketDetails.getClosed() == null) {
%>

<%
	if (TicketDetails.getTipoCampione() == 5) {
%>
<tr class="containerBody">
	<td name="accountSize1" id="accountSize1" nowrap class="formLabel">
	<dhv:label name="osa.categoriaRischioo" />Scegli Tipo Check List</td>
	<td><%=OrgCategoriaRischioList.getHtmlSelect(
										"accountSize", -1)%>
										
<br>
	<%
	if(request.getAttribute("ChecklistError")!=null)
	{
	%>
	<font color = "red"><%=request.getAttribute("ChecklistError") %></font>
	<%	
	}
	%>
	
	
 <%
 	UserBean entered = (UserBean) session
 						.getAttribute("User");
 				if (TicketDetails.getNumeroAudit() == 0) {
 					
 %> <input type="button" value="Compila Checklist Principale"
		name="CompilaChecklistPrincipale"
		onClick="javascript: if(document.getElementById('accountSize').value=='-1'  ){alert('Selezionare il tipo di checklist e controllare che java sia installato correttamente')}else{controlloChecklist('<%="Sei sicuro che la CheckList Selezionata sia quella principale  ? "%>','1',<%=entered.getUserId()%>)}" />
	<%
		} else {
	%> <input type="button" value="Compila Checklist Secondaria"
		name="Save"
		onClick="javascript:  if(document.getElementById('accountSize').value=='-1'   ){alert('Selezionare il tipo di checklist e controllare che java sia installato correttamente')}else{controlloChecklist('Stai per compilare una checklist successiva alla prima. Continuare ?','2',<%=entered.getUserId()%>)}" />

	<%
		}
	%>
	</td>
</tr>
<%
	String checklistInserite = "";
				Iterator<Audit> it = Audit.iterator();
				while (it.hasNext()) {
					Audit a = it.next();
					checklistInserite += a.getTipoChecklist() + ";";

				}
%>
<input type="hidden" name="checklist_inserite" id="checklist_inserite"
	value="<%=checklistInserite%>">
<%
	}
%>

<%
	}
	}
%>

<%@ include file="../controlliufficiali/controlli_ufficiali_view_info.jsp" %>
































