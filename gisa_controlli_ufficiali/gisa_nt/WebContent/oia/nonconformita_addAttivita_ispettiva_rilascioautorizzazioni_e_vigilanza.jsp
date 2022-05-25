<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.distributori.base.Organization" scope="request"/>
<%String idMacchinetta=(String)request.getAttribute("idMacchinetta"); %>
<form name="addticket" action="AccountNonConformita.do?command=Insert&auto-populate=true&idMacchinetta=<%=idMacchinetta %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Distributori.do"><dhv:label name="accounts.accounts">Distributori</dhv:label></a> > 

<a href="Distributori.do?command=Details&id=<%=idMacchinetta %>&orgId=<%=OrgDetails.getOrgId()%>">Scheda Distributore</a> >

<a href="Distributori.do?command=ViewNonConformita&id=<%=idMacchinetta %>&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="nonconformita">Non Conformita</dhv:label></a> > 
<dhv:label name="nonconformita.aggiungi">Aggiungi Non Conformità Rilevate</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='DistributoriVigilanza.do?command=TicketDetails&id=<%= OrgDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&idmacchinetta=<%=idMacchinetta %>'">
<br>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
<%-- include basic troubleticket add form --%>

<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
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
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.nonconformita.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformitaAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformitaPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popDistributori.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript">

function checkImporto(form)
{
if (form.cause.value == "") {
      alert("- Controllare che il campo 'importo' sia stato popolato ");
    }
}

  
  function checkForm(form){
    formTest = true;
    message = "";
  
    if (form.siteId.value == "-1") {
      message += label("check.nonconformita.richiedente.selezionato","- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
      formTest = false;
    }
  
    if (form.orgId.value == "-1") {
      message += label("check.nonconformita.richiedente.selezionato","- Controllare che l'impresa sia stata selezionata\r\n");
      formTest = false;
    }
    if (form.assignedDate.value == "") {
      message += label("check.nonconformita.data_richiesta.selezionato","- Controllare che il campo \"Data Non Conformita\" sia stato popolato\r\n");
      formTest = false;
    }
    
  
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }

 function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
 }

 function setAssignedDate(){
  resetAssignedDate();
  if (document.forms['addticket'].assignedTo.value > 0){
    document.forms['addticket'].assignedDate.value = document.forms['addticket'].currentDate.value;
  }
 }

 function resetAssignedDate(){
    document.forms['addticket'].assignedDate.value = '';
 }
 
 function setField(formField,thisValue,thisForm) {
  var frm = document.forms[thisForm];
  var len = document.forms[thisForm].elements.length;
  var i=0;
  for( i=0 ; i<len ; i++) {
    if (frm.elements[i].name.indexOf(formField)!=-1) {
      if(thisValue){
        frm.elements[i].value = "1";
      } else {
        frm.elements[i].value = "0";
      }
    }
  }
 }
 
</script>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Non Conformità Rilevate</dhv:label></strong>
    </th>
	</tr>
	
	
  
  <dhv:include name="stabilimenti-sites" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
       <%=SiteIdList.getSelectedValue(OrgDetails
										.getSiteId())%>
          <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
      
      </td>
    </tr>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>

	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
  <tr>
    <td class="formLabel">
      <dhv:label name="nonconformita.richiedente">Impresa</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
      </td>
    
  </tr>
 
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>
  
  <tr>
    <td class="formLabel">
      <dhv:label name="nonconformita.richiedente">Numero C.U</dhv:label>
    </td>
   
     
      <td>
        <input type="text" name="numerocu"  />
      </td>
    
  </tr>
  
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Numero Verbale</dhv:label>
    </td>
    <td>
    <%if(TicketDetails.getLocation() != "" && TicketDetails.getLocation() != null){ %>
      <input type="text" name="location" id="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="20" maxlength="256" />
    <%}else{%>
          <input type="text" name="location" id="location" value="" size="20" maxlength="256" />
    <%} %>
    </td>
  </tr>
  
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
      <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>

<dhv:include name="ticket.location" none="true">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
    <dhv:label name="">Tipo di Non Conformita Rilevata</dhv:label>
    </td>
    <td>
      <input type="text" name="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="50" maxlength="256" />
    </td>
  </tr>
</dhv:include>
  <tr>
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
	
	
	  <dhv:include name="organization.source" none="true">
   <tr>
      <td name="provvedimento1" id="provvedimento1" nowrap class="formLabel">
        <dhv:label name="">Follow Up delle Non Conformità</dhv:label>
      </td>
    <td>
   <table border=0>
      <tr>
      <td >
      <%	Provvedimenti.setJsEvent("onChange=\"javascript:selectCarattere('Provvedimenti', '1', 2, '1');\"");
        %>
         <%= Provvedimenti.getHtmlSelect("Provvedimenti",TicketDetails.getProvvedimenti()) %>
         
       
    		</td>
          	<td style="visibility: hidden;" id="dat1">
        		Descrizione<font color="red">*</font>
        	</td>
        	<td style="visibility: hidden;" id="dat2">
           		<input type="text" name="descrizione1" size="50">
          	</td>
       </tr>
       </table>
    </td>
  </tr>
</dhv:include> 
	
	
  </tr>
  
     <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformitaa.azioni">Punteggio</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <input type="text" name="punteggio">
          </td>
         
        </tr>
        
        
        
    </table>
    </td>
    </tr>
    
    <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformita.azioni">Ulteriori Note</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="solution" cols="55" rows="8"><%= toString(TicketDetails.getSolution()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
          </tr>
           </tr>
  
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='DistributoriVigilanza.do?command=TicketDetails&id=<%= OrgDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&idmacchinetta=<%=idMacchinetta %>'">
</form>
