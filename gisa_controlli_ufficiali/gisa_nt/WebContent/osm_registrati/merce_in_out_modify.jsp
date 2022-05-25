<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.osmregistrati.base.Organization" scope="request"/>
<jsp:useBean id="TipoMollusco" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="merce" class="org.aspcfs.modules.osmregistrati.base.MerceInOut" scope="request"/>
<jsp:useBean id="destinatario" class="org.aspcfs.modules.osmregistrati.base.Organization" scope="request" />
<form name="addticket" action="StabMerceInOut.do?command=Update&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>

			<a href="OsmRegistrati.do">OSM Registrati</a> > 
			<a href="OsmRegistrati.do?command=Search"><dhv:label name="stabilimenti.SearchResults">Search Results</dhv:label></a> >
			<a href="OsmRegistrati.do?command=Details&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="communications.campaign.Dashboards">Scheda OSM</dhv:label></a> >

<a href="StabMerceInOut.do?command=List&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="merce_out">Merce in Ingresso/Uscita</dhv:label></a> > 
<dhv:label name="merce_in_out.new">Modifica Merce in Ingresso/Uscita</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="button.modify">Modifica</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='Molluschicoltori.do?command=ViewSanzioni'">
<br>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError")  %>
<%}%>
<%-- include basic troubleticket add form --%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.sanzioni.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.osmregistrati.base.OrganizationList" scope="request"/>

<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Merce in Uscita</dhv:label></strong>
    </th>
	</tr>

   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stab.stab">OSM</dhv:label>
    </td>
    <td>
    	<%=toHtmlValue( OrgDetails.getName() ) %>
    	<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId() %>" />
    	<input type="hidden" name="id" value="<%=merce.getId() %>" />
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="merce_in_out.data_arrivoo">Data Arrivo</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect timestamp="<%=merce.getData_arrivo() %>" form="addticket" field="data_arrivo" timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
	
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="merce_in_out.data_invio">Data Invio</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect timestamp="<%=merce.getData_invio() %>" form="addticket" field="data_invio" timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="merce_in_out.moll">Specie Mollusco</dhv:label>
    </td>
    <td>
      <%=TipoMollusco.getHtmlSelect( "tipo_mollusco", merce.getId_specie_mollusco() ) %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="merce_in_out.quantita">Quantit&agrave;</dhv:label>
    </td>
    <td>
      <input value="<%=toHtmlValue( merce.getQuantita() ) %>" type="text" name="quantita" />
    </td>
  </tr>
  
  <%
	String tipo_prov	= (String)request.getAttribute( "tipo_provenienza" );
	String provenienza	= (String)request.getAttribute( "provenienza" );
	String id_prov		= (String)request.getAttribute( "id_prov" );
  %>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="merce_in_out.id_destinatario">Provenienza</dhv:label>
    </td>
    <td>
            <div id="changeaccount_prov">
              <% if( !id_prov.equalsIgnoreCase( "-1" ) ) {%>
                <%= toHtml( provenienza ) %>
              <%} else {%>
                <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%}%>
            </div>
    	<input type="hidden" name="id_provenienza" id="id_provenienza" value="<%=id_prov %>" />
        [<a href="javascript:popAccountsListSingleNew('id_provenienza','changeaccount_prov', 'showMyCompany=true&filters=all|my|disabled');"><dhv:label name="accountsa.accounts_add.select">Seleziona OSM</dhv:label></a>]
        [<a href="javascript:popAccountsListSingleNewMoll('id_provenienza','changeaccount_prov', 'showMyCompany=true&filters=all|my|disabled');"><dhv:label name="accountsa.accounts_add.select">Seleziona Molluschicoltore</dhv:label></a>]
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="merce_in_out.stato_regione_provenienza">Stato/Regione Provenienza</dhv:label>
    </td>
    <td>
      <input value="<%=toHtmlValue( merce.getStato_regione_provenienza() ) %>" type="text" name="stato_regione_provenienza" />
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="merce_in_out.id_destinatario">Destinatario</dhv:label>
    </td>
    <td>
            <div id="changeaccount">
              <% if(destinatario.getOrgId() != -1) {%>
                <%= toHtml(destinatario.getName()) %>
              <%} else {%>
                <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%}%>
            </div>
    	<input type="hidden" name="id_destinatario" id="id_destinatario" value="<%=destinatario.getOrgId() %>" />
        [<a href="javascript:popAccountsListSingleNew('id_destinatario','changeaccount', 'showMyCompany=true&filters=all|my|disabled');"><dhv:label name="accountsa.accounts_add.select">Seleziona OSM</dhv:label></a>]
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="merce_in_out.id_doc_traporto">Identificativo Documento di Trasporto</dhv:label>
    </td>
    <td>
      <input value="<%=toHtmlValue( merce.getIdentificativo_documento_trasporto() ) %>" type="text" name="idetificativo_documento_trasporto" />
    </td>
  </tr>


</table>
<br />
<a name="categories"></a>
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="submit" value="<dhv:label name="button.modify">Modifica</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='Molluschicoltori.do?command=ViewSanzioni'">
</form>
