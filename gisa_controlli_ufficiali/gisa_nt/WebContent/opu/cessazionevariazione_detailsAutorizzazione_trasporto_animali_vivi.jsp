<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.opu.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.cessazionevariazione.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>

<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.cessazionevariazione.base.Ticket" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>


<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>

<%@ page import="java.util.*,org.aspcfs.modules.cessazionevariazione.base.*" %>
<%@ include file="../initPage.jsp" %>

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<td>
<dhv:label name="">Impresa -> Stabilimento -> Volture -> Dettaglio Voltura</dhv:label>
</td>
</tr>
</table>
<%
	String param1 = "id=" + TicketDetails.getId();
%>
<%
String nomeContainer = OrgDetails.getContainer();
%>
<dhv:container name="<%=nomeContainer %>" selected="cessazionevariazione" object="OrgDetails" param='<%= "stabId=" + OrgDetails.getIdStabilimento()+"&opId="+OrgDetails.getIdOperatore() %>' hideContainer='<%= isPopup(request) %>'>

<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>

<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>

<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>

<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
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
      <strong><dhv:label name="">Voltura</dhv:label></strong>
    </th>
	</tr>
  
   <dhv:include name="stabilimenti-sites" none="true">
 <%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
       <%=SiteIdList.getSelectedValue(OrgDetails.getIdAsl())%>
      </td>
    </tr>
<%--</dhv:evaluate>  --%>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
 <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="sanzionia.data_richiesta">Data Voltura</dhv:label>
    </td>
    <td>
          <zeroio:tz timestamp="<%= TicketDetails.getAssignedDate() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
</td>
  </tr>
  
     <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Denominazione</dhv:label>
    </td>
   
     
      <td>
        <%= toHtmlValue(TicketDetails.getBanca()) %>
        
      </td>
    
  </tr>
   
      <tr class="containerBody">
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <%= toHtmlValue(TicketDetails.getPartitaIva()) %>
    </td>
  </tr>
  <tr class="containerBody" style="display:none">
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="20" maxlength="16" name="codiceFiscale" value="<%= toHtmlValue(Operatore.getCodFiscale()) %>">    
    </td>
  </tr>
   
  </table>
  </br>
  
  
  <table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">

		<tr>
			<th colspan="2">Responsabile Impresa</strong>
	</th>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.nome"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateImpresa().getNome()%></td>
		</tr>
				<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.cognome"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateImpresa().getCognome()%></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.cf"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateImpresa().getCodFiscale()%></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.comune_nascita"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateImpresa().getComuneNascita()%> </td>
		</tr>
		<tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.provincia_nascita"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateImpresa().getProvinciaNascita() %></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.data_nascita"></dhv:label></td>
			<td><%=toDateString(TicketDetails.getRappresentateImpresa()
								.getDataNascita())%> </td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.sesso"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateImpresa().getSesso()%></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.fax"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateImpresa().getFax()%> </td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.telefono"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateImpresa()
										.getTelefono1()%> </td>
		</tr>
				<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.telefono2"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateImpresa()
										.getTelefono2()%> </td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.mail"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateImpresa().getEmail()%></td>
		</tr>
	
	</table>
<br>


  <table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">

		<tr>
			<th colspan="2">Responsabile Stabilimento</strong>
	</th>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.nome"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateStabilimento().getNome()%></td>
		</tr>
				<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.cognome"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateStabilimento().getCognome()%></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.cf"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateStabilimento().getCodFiscale()%></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.comune_nascita"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateStabilimento().getComuneNascita()%> </td>
		</tr>
		<tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.provincia_nascita"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateStabilimento().getProvinciaNascita() %></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.data_nascita"></dhv:label></td>
			<td><%=toDateString(TicketDetails.getRappresentateStabilimento()
								.getDataNascita())%> </td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.sesso"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateStabilimento().getSesso()%></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.fax"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateStabilimento().getFax()%> </td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.telefono"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateStabilimento()
										.getTelefono1()%> </td>
		</tr>
				<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.telefono2"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateStabilimento()
										.getTelefono2()%> </td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.mail"></dhv:label></td>
			<td><%=TicketDetails.getRappresentateStabilimento().getEmail()%></td>
		</tr>
	
	</table>

</dhv:container>

