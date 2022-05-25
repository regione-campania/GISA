<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.opu.base.Operatore"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.vigilanza.base.Ticket,com.zeroio.iteam.base.*" %>

<%@page import="org.aspcfs.modules.opu.base.InformazioniStabilimento"%><jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditTipo" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAudit" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TicList" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>
<jsp:useBean id="AccountTicketInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%> 
<%@ include file="../initPopupMenu.jsp" %> 
<%@ include file="accounts_vigilanza_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%

%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <dhv:label name=""><a href="<%=OrgDetails.getAction()+".do?command=SearchForm" %>" >Gestione Anagrafica Impresa </a>-><a  href="<%=OrgDetails.getAction()+".do?command=Details&stabId="+OrgDetails.getIdStabilimento()%>">Scheda Impresa</a> -><a href="<%=OrgDetails.getAction()+".do?command=ViewVigilanza&stabId="+OrgDetails.getIdStabilimento()%>"> Controlli Ufficiali </a></dhv:label>

</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%
request.setAttribute("Operatore",OrgDetails.getOperatore());
String nomeContainer = OrgDetails.getContainer();
if (User.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA)
	nomeContainer+="Ext";
String param = "stabId="+OrgDetails.getIdStabilimento()+"&opId=" + OrgDetails.getIdOperatore()+"&id="+request.getAttribute("idMacchinetta") ;
%>
<dhv:container name="<%=nomeContainer %>"  selected="vigilanza" object="Operatore" param="<%=param%>"  hideContainer="false">
 
    <dhv:permission name="accounts-accounts-vigilanza-add"><a href="<%=OrgDetails.getAction() %>Vigilanza.do?command=Add&operatoreunico=1&idMacchinetta=<%=request.getAttribute("idMacchinetta") %>&orgId=<%= OrgDetails.getIdStabilimento() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Aggiungi Nuovo Controllo Ufficiale</dhv:label></a></dhv:permission>
    <input type=hidden name="orgId" value="<%= OrgDetails.getIdStabilimento() %>">
    <br>
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="AccountTicketInfo"/>
  

  

<script>
function filtraCu(radio){
	var value = radio.value;
	loadModalWindow();
	window.location.href = 	"<%=OrgDetails.getAction() %>.do?command=ViewVigilanza&stabId=<%= request.getParameter("stabId")%>&opId=<%= request.getParameter("opId")%>&altId=<%= request.getParameter("altId")%>&statusId="+value;
}

</script>

<dhv:permission name="cu-filtra-view">
<table style="border: 1px solid black">
<tr>
<td><input type="radio" id="tutti" name="status" value="-1" <%if (request.getParameter("statusId")==null || request.getParameter("statusId").equals("-1")){ %>checked<%} %> onChange="filtraCu(this)"/>Tutti</td>
<td><input type="radio" id="aperti" name="status" value="1" <%if (request.getParameter("statusId")!=null && request.getParameter("statusId").equals("1")){ %>checked<%} %> onChange="filtraCu(this)"/>Aperti/Riaperti</td>
<%-- <td><input type="radio" id="riaperti" name="status" value="3" <%if (request.getParameter("statusId")!=null && request.getParameter("statusId").equals("3")){ %>checked<%} %> onChange="filtraCu(this)"/>Riaperti</td> --%>
<td><input type="radio" id="chiusi" name="status" value="2" <%if (request.getParameter("statusId")!=null && request.getParameter("statusId").equals("2")){ %>checked<%} %> onChange="filtraCu(this)"/>Chiusi</td>
</tr>
</table>
<font color="red">Attenzione: Per i filtri Aperti e Chiusi, tutti i risultati saranno mostrati nella prima pagina.</font>
</dhv:permission>
  
 
  
<%@ include file="../controlliufficiali/opu_lista_controlli_ufficiali.jsp" %>
  <dhv:pagedListControl object="AccountTicketInfo"/>

	<br>
</dhv:container>