<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.vigilanza.base.*" %>
<%@ include file="../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<script type="text/javascript" src="dwr/interface/ControlliUfficiali.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<%@ page
	import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*"%>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TipoAudit" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Bpi" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Haccp" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Ispezione" class="java.util.HashMap"
	scope="request" />
	<jsp:useBean id="IspezioneMacrocategorie" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="PianoMonitoraggio1"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianoMonitoraggio2"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianoMonitoraggio3"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="resolvedByDeptList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleo" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TitoloNucleoDue"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoTre"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoQuattro"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoCinque"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoSei"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoSette"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoOtto"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoNove"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TitoloNucleoDieci"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategoryList"
	class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
	scope="request" />
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SanzioniAmministrative"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="ticketStateList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Recipient"
	class="org.aspcfs.modules.contacts.base.Contact" scope="request" />
<jsp:useBean id="DestinatarioCampione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem"
	scope="request" />
<jsp:useBean id="EsitoControllo" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="DistribuzionePartita"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="DestinazioneDistribuzione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ArticoliAzioni" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="AzioniAdottate" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="OrgList"
	class="org.aspcfs.modules.accounts.base.OrganizationList"
	scope="request" />
<jsp:useBean id="SubList1"
	class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
	scope="request" />
<jsp:useBean id="SubList2"
	class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
	scope="request" />
<jsp:useBean id="SubList3"
	class="org.aspcfs.modules.troubletickets.base.TicketCategoryList"
	scope="request" />
<jsp:useBean id="UserList"
	class="org.aspcfs.modules.admin.base.UserList" scope="request" />
<jsp:useBean id="resolvedUserList"
	class="org.aspcfs.modules.admin.base.UserList" scope="request" />
<jsp:useBean id="ContactList"
	class="org.aspcfs.modules.contacts.base.ContactList" scope="request" />
<jsp:useBean id="actionPlans"
	class="org.aspcfs.modules.actionplans.base.ActionPlanList"
	scope="request" />
<jsp:useBean id="insertActionPlan" class="java.lang.String"
	scope="request" />
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect"
	scope="request" />
<jsp:useBean id="TimeZoneSelect"
	class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request" />
<jsp:useBean id="systemStatus"
	class="org.aspcfs.controller.SystemStatus" scope="request" />
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript" type="text/javascript"
	src="javascript/confrontaDate.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/controlliUfficiali.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/controlli_ufficiali_stabilimenti.js"></script>
	
<!-- 
	<script>
var r = confirm("ATTENZIONE! Per aggiungere un controllo ufficiale è necessario importare prima lo stabilimento nella NUOVA ANAGRAFICA.\nProseguire con la procedura di importazione?");		
if (r == true) { 
	window.location.href='OpuStab.do?command=CaricaImport&orgId=<%= OrgDetails.getOrgId() %>';
} else {
	window.location.href='Stabilimenti.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';
}  		
</script>
-->
<!--  <script>
alert("ATTENZIONE! Prima di aggiungere un nuovo controllo, è consigliabile importare prima lo stabilimento nella NUOVA ANAGRAFICA tramite il servizio di HELPDESK.");
/*var r = confirm("ATTENZIONE! E' consigliabile importare prima lo stabilimento nella NUOVA ANAGRAFICA.\n\n -Cliccare OK per proseguire con la procedura di importazione\n -Cliccare ANNLLA per aggiungere il controllo");		
if (r == true) { 
	window.location.href='OpuStab.do?command=CaricaImport&orgId=<%= OrgDetails.getOrgId() %>';
} else {
	;
}  	*/	
</script>
-->	
	
	
<body onload = "provaFunzione('addticket'); onloadAllerta('addticket'); resetElementiNucleoIspettivo('<%=TicketDetails.getNucleoasList().size() %>')">
	 <%
	TipoIspezione.setJsEvent("onChange=javascript:mostraMenuTipoIspezione('addticket');");
	TitoloNucleoDue.setJsEvent("onChange=mostraCampo2('addticket')");
	PianoMonitoraggio1.setJsEvent("onChange=javascript:piani('addticket')");
    PianoMonitoraggio2.setJsEvent("onChange=javascript:piani('addticket')");
    PianoMonitoraggio3.setJsEvent("onChange=javascript:piani('addticket')");
	AuditTipo.setJsEvent("onChange=javascript:mostraMenu4('addticket')");
    TipoAudit.setJsEvent("onChange=javascript:mostraMenu2('addticket')");
  
    TipoCampione.setJsEvent("onChange=javascript:reloadAddCU(this.value)"); 
	TitoloNucleo.setJsEvent("onChange=mostraCampooo('addticket')"); 
%>

<form name="addticket" action="StabilimentiVigilanza.do?command=Insert&auto-populate=true" method="post">

<%-- Trails --%>
	<% if (OrgDetails.isOperatoreIttico()) { %>
		<table class="trails" cellspacing="0">
				<tr>
				<td>
					<a href="Stabilimenti.do"><dhv:label name="stabilimenti.stabilimenti">Accounts</dhv:label></a> > 
					<a href="Stabilimenti.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
					<a href="Stabilimenti.do?command=Details&orgId=<%=OrgDetails.getIdMercatoIttico()%>"><dhv:label name="">Mercato Ittico</dhv:label></a> >
					<a href="Stabilimenti.do?command=DetailsOperatoriMercatiIttici&orgId=<%=OrgDetails.getId()%>"><dhv:label name="">Scheda Operatore Mercato Ittico</dhv:label></a> >
					  <a href="Stabilimenti.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="vigilanza">Tickets</dhv:label></a> >
					  <dhv:label name="campioni.aggiungi">Aggiungi Controllo Ufficiale</dhv:label>
				</td>
			</tr>
		</table>
	<% } else { %>
		<table class="trails" cellspacing="0">
				<tr>
				<td>
					  <a href="Stabilimenti.do"><dhv:label name="">Stabilimenti Riconosciuti</dhv:label></a> > 
					  <a href="Stabilimenti.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
					  <a href="Stabilimenti.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Scheda Stabilimento</dhv:label></a> >
					  <a href="Stabilimenti.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="vigilanza">Tickets</dhv:label></a> >
					  <dhv:label name="campioni.aggiungi">Aggiungi Controllo Ufficiale</dhv:label>
				</td>
			</tr>
		</table>
	<% } %>
	
<%-- End Trails --%>

<input type="button" value="<dhv:label name="button.inserta">Inserisci </dhv:label>" name="Save" onClick="return controlloCuSorveglianza()">

<%

%>
<input type="submit" value="Annulla" onClick="javascript:this.form.action='Stabilimenti.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>'">
<br>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
<%-- include basic troubleticket add form --%>
<%
	TipoIspezione.removeElementByLevel(17);

	/*QUESTO FILTRO NON CONSENTIVA AGLI STABILIMENTI 853 DI EFFETTUARE UN CONTROLLO UFFICIALE STANDARD
	  NELLO STATO VERIFICA DOCUMENTAZIONE...*/
	/*if (OrgDetails.getTipologia() == 3  )
	{
		if(OrgDetails.getStatoIstruttoria()==2)
		{
		
			for (int i = 1 ; i<=20; i++)
			{
				if(TipoIspezione.getLevelFromId(i)!=17)
				{
					
					TipoIspezione.removeElementByLevel(TipoIspezione.getLevelFromId(i));
				
				}
			}
			TipoCampione.removeElementByLevel(1);
		}
		else
		{
			TipoIspezione.removeElementByLevel(17);
		}
		
	
		
	}
	else
	{
		TipoIspezione.removeElementByLevel(17);
		
	}*/
		%>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Controllo Ufficiale</dhv:label></strong>
    </th>
	</tr>
  
<%@ include file="../controlliufficiali/controlli_ufficiali_add.jsp" %>


	

<tr>
 <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Stabilimenti per smaltimento SOA</dhv:label>
    </td>
    
        <td >
        <table class = "noborder">
        <tr>
        <td>
        <center>
        &nbsp;[<a href="javascript:popLookupSelectorSOA('codiceFiscaleCorrentista','alertText','organization','',<%=OrgDetails.getSiteId() %>);"><dhv:label name="">Aggiungi SOA</dhv:label></a>]
        </center>
        </td>
        <td>
        <input type = "hidden" id = "elementi1" name = "elementi1" value = "0">
   		<input type = "hidden" id = "size1" name = "size1" value = "0">
        <table id="tab">
        <tr id="row" style = "visibility: hidden;"><td><input type = "text" readonly="readonly" name = "ragioneSociale"></td> <td><input type = "text" readonly="readonly" name = "indirizzo"><input type = "hidden" readonly="readonly" name = "orgIdSoa"></td><td>[<a href="javascript:popLookupSelectorAllerteImpreseElimina()" id="elimina">[elimina]</a></td></tr>
        <tr id="row_fregione" style = "visibility: hidden;"><td><input type = "text" value = "Inserire descrizione soa Fuori Regione" name = "ragioneSociale"></td> <td><input type = "text" value = "Inserire citta soa Fuori Regione" name = "indirizzo"><input type = "hidden" readonly="readonly" name = "orgIdSoa"></td><td>[<a href="javascript:popLookupSelectorAllerteImpreseElimina()" id="elimina">[elimina]</a></td></tr>
        </table> 
        </td>
         </tr>
        </table>
      </td>
     </tr>

</table>
<br><br>

<%@ include file="../controlliufficiali/controlli_ufficiali_allarmerapido_add.jsp" %>
	
 
<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp.jsp" %>

<br>
<br>
<br>

<%@ include file="../controlliufficiali/controlli_ufficiali_laboratori_haccp_non_in_regione.jsp" %>
	
 
  
   </br>
   </br>
   
<input type="hidden" name="close" value="">

<input type="hidden" name="refresh" value="-1">
 <input type="hidden" name="isAllegato" value="<%=TicketDetails.isListaDistribuzioneAllegata() %>">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="button" value="<dhv:label name="button.inserta">Inserisci </dhv:label>" name="Save" onClick="return controlloCuSorveglianza()">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='Stabilimenti.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>'">
</form>
