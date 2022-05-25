<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="ext.aspcfs.modules.apicolture.actions.StabilimentoAction"%>
<%@page import="ext.aspcfs.modules.apicolture.actions.OperatoreAction"%>
<%@page import="ext.aspcfs.modules.apiari.base.Operatore"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="OrgList" class="ext.aspcfs.modules.apiari.base.OperatoreList" scope="request"/>
<jsp:useBean id="TipoAttivitaApi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

<SCRIPT src="javascript/apiari.js"></SCRIPT>



<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>



<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterApi.js"></script>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<SCRIPT src="javascript/apiari.js"></SCRIPT>

<script>

RegExp.escape = function(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
}
String.prototype.replaceAll = function(search, replace) {
    return this.replace(new RegExp(RegExp.escape(search),'g'), replace);
};

</script>

<body onload="resizeGlobalItemsPane('hide')">


<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
Apicoltura 
</td>
</tr>
</table>


<%
if (OrgList.size()>0 && ((Operatore)OrgList.get(0)).getStato()==StabilimentoAction.API_STATO_PREGRESSO_DA_VALIDARE)
{
	%>
	<font color = "red">ATTENZIONE! ESISTE UNA IMPRESA PREGRESSA ASSOCIATA AL CODICE FISCALE CORRENTE.
	 CLICCA IL LINK PER CONFERMARE I DATI E INVIARE IL MODELLO A ALL'ASL DI COMPETENZA
	 <a href="ApicolturaAttivita.do?command=ModifyPregresso&opId=<%=((Operatore)OrgList.get(0)).getIdOperatore()%>">CONFERMA ATTIVITA DI APICOLTURA</a>
	 </font>
	<%
}
else
{
	if (OrgList.size()==0)
	{
%>
<font color="red">NON ESISTE UNA ATTIVITA' LEGATA ALLA PROPRIA PERSONA</font>

<script>
alert("NON ESISTE UNA ATTIVITA' LEGATA ALLA PROPRIA PERSONA. SI SARA' ORA REINDIRIZZATI ALLA PAGINA DI AGGIUNTA ANAGRAFICA.");
loadModalWindow();
window.location.href='ApicolturaAttivita.do?command=Add';
</script>

	<%} }%>
<iframe src="empty.html" name="server_commands" id="server_commands"
		style="visibility: hidden" height="0"></iframe>
		</body>
