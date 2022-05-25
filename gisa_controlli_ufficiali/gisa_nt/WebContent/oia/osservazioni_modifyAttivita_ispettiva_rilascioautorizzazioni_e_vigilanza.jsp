<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  -
  - Version: $Id: accounts_tickets_modify.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="Osservazioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TitoloNucleo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDue" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoTre" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoQuattro" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoCinque" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoSei" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoSette" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoOtto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoNove" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDieci" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.oia.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.osservazioni.base.Osservazioni" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti2" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="Provvedimenti3" class="java.util.ArrayList" scope="request"/><jsp:useBean id="NonConformitaAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformitaPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/osservazioni.js"></SCRIPT>
<script type="text/javascript" src="tabber.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<link rel="stylesheet" href="example.css" type="text/css" media="screen"></link>

<div  id = "nc" >
<%String idMacchinetta=""+OrgDetails.getOrgId(); %>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Oia.do?command=SearchForm"><dhv:label name="">Autorità Competenti</dhv:label></a> >
  
  <a href="Oia.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>&idNodo=<%=idMacchinetta %>"><dhv:label name="">Scheda Dipartimento</dhv:label></a> > 
  <a href="OiaVigilanza.do?command=ViewVigilanza&idNodo=<%=idMacchinetta %>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="nonconformita">Controlli Ufficiali</dhv:label></a> >
   <a href="OiaVigilanza.do?command=TicketDetails&idNodo=<%=idMacchinetta %>&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
 
  <% if (request.getParameter("return") == null) {%>
  <a href="OiaOsservazioni.do?command=TicketDetails&idNodo=<%=idMacchinetta %>&id=<%=TicketDetails.getId()%>"><dhv:label name="nonconformita.dettagli">Scheda Sequestro/Blocco</dhv:label></a> >
  <%}%>
  <dhv:label name="">Modifica Osservazione/Raccomandazione</dhv:label>
</td>
</tr>
</table>
<form name="details" action="OiaOsservazioni.do?command=UpdateTicket&idNodo=<%=idMacchinetta %>&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" onSubmit="return checkForm(this);" method="post">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>

<%-- End Trails --%>
</dhv:evaluate>
    <%--@ include file="accounts_ticket_header_include.jsp" --%>
     <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
          <dhv:permission name="oia-oia-nonconformita-edit">
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='OiaNonConformita.do?command=ReopenTicket&idMacchinetta=<%=idMacchinetta %>&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='OiaVigilanza.do?command=ViewVigilanza&idMacchinetta=<%=idMacchinetta %>&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='OiaOsservazioni.do?command=TicketDetails&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
            <input type="submit" id = "btn_salva" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='OiaVigilanza.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='OiaOsservazioni.do?command=TicketDetails&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
          <%= showAttribute(request, "closedError") %>
       </dhv:evaluate>
      </dhv:evaluate>
    <br />
    <dhv:formMessage />
   <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="nonconformita.information">Scheda Sequestro/Blocco</dhv:label></strong>
      </th>
	</tr>
	<dhv:include name="" none="true">
  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.site">Site</dhv:label>
    </td>
    <td>
     <%=SiteIdList.getSelectedValue(TicketDetails.getSiteId())%>
          <input type="hidden" name="siteId" value="<%=TicketDetails.getSiteId()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
   
  </dhv:evaluate>
</dhv:include>
	
	
	<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="nonconformita.richiedente">Numero C.U</dhv:label>
    </td>
   
     
      <td>
        <%=TicketDetails.getIdentificativo() %>
      </td>
    
  </tr>
   <input type="hidden" name="idControlloUfficiale" value="<%=TicketDetails.getIdControlloUfficiale() %>">
    
   
	
	
	<tr class="containerBody" style="display: none">
      <td nowrap class="formLabel">
        <dhv:label name="nonconformita.data_richiestas">Data</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="details" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformita.note">Note</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="8"><%= toString(TicketDetails.getProblem()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>
  
  
  <%if(!request.getAttribute("idIspezione").equals("3")){ %>
     <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformitaa.azioni">Punteggio Cumulativo</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <input type="text" name="punteggio" readonly="readonly" title="INUTILE CHE SBAREI : punteggio determinato automaticamente dal sistema" name="totale" value="<%=TicketDetails.getPunteggio() %>" id="totale">
          </td>
         
        </tr>
        
        
        
    </table>
    </td>
    </tr>
   
      <%} %>
      </table>
    
 <%@ include file="../nonconformita/osservazioni_edit.jsp" %>
 
 <input type = "hidden" id = "dosubmit" name = "dosubmit" value = "0">
        &nbsp;<br>
   <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
        <dhv:permission name="oia-oia-nonconformita-nonconformita-edit">
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='OiaNonConformita.do?command=ReopenTicket&idMacchinetta=<%=idMacchinetta %>&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='OiaVigilanza.do?command=ViewVigilanza&idMacchinetta=<%=idMacchinetta %>&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='OiaOsservazioni.do?command=TicketDetails&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
          <input type="submit" id = "btn_salva2" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='OiaVigilanza.do?command=ViewVigilanza&idMacchinetta=<%=idMacchinetta %>&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='OiaOsservazioni.do?command=TicketDetails&idMacchinetta=<%=idMacchinetta %>&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
        <%= showAttribute(request, "closedError") %>
     </dhv:evaluate>
    </dhv:evaluate>
    <input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
    <input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getOrgSiteId() %>" />
    <input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
    <input type="hidden" name="companyName" value="<%= toHtml(TicketDetails.getCompanyName()) %>">
    <input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
    <input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
    <input type="hidden" name="close" value="">
    <input type="hidden" name="refresh" value="-1">
    <input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />



<br><br>

   <%-- <% String dataR = request.getAttribute("dataR").toString(); --%>
  <table cellpadding="2" cellspacing="0" width="50%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Dettagli Risoluzione</dhv:label></strong>
    </th>
	</tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data di risoluzione</dhv:label>
      </td>
       <td>
      	<zeroio:dateSelect form="details" field="resolutionDate" timestamp="<%= TicketDetails.getResolutionDate() %>"  timeZone="<%= TicketDetails.getResolutionDateTimeZone() %>" showTimeZone="false" />
      		<font color="red">*</font> <%= showAttribute(request, "resolutionDateError") %>
    	</td>
    </tr>
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Risolto</dhv:label>
    </td>
    <td>
     <input type="radio" value="SI" name="resolvable" id="resolvable" <%= (TicketDetails.isResolvable()) ? "checked=checked" : "" %> >SI
     <input type="radio" value="NO" name="resolvable" id="resolvable" <%= (!TicketDetails.isResolvable()) ? "checked=checked" : "" %> >NO
    </td>
  </tr>
  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformita.azioni">Descrizione</dhv:label>
    </td>
    <td>
      <textarea  name="note_altro" cols="55" rows="8"><%=TicketDetails.getNote_altro() %></textarea>
    </td>
         
    </tr>
  </table>
 



</form>
<script>

resetElementi('<%=TicketDetails.getPuntiFormali()%>','<%=TicketDetails.getPuntiSignificativi()%>','<%=TicketDetails.getPuntiGravi()%>','<%=TicketDetails.getNon_conformita_formali().size()%>','<%=TicketDetails.getNon_conformita_significative().size()%>','<%=TicketDetails.getNon_conformita_gravi().size()%>')

</script>
</body>


