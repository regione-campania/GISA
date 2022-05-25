<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="OrgDetails" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript" src="javascript/controlli_sottoattivita.js"></script>


<%
if (request.getAttribute("inserito")!=null)
{
%>
<font color = "red">Nota di Reato Inserito in maniera corretta.</font>
<a href = "#" >Torna Indietro</a>

<%	
}
%>

<script>
focus_=true ;
 </script>
  <body  onblur="if(focus_==true){window.focus();}" onmouseout="focus_=true;" onmouseover="focus_=false;"  onunload="abilitaAll()" onload = "abilitaStatiNc('<%= request.getAttribute("inserito")%>','<%=request.getAttribute("TipoNC") %>','<%= request.getAttribute("idC")%>','<%=request.getAttribute("followup_formali_inseriti") %>','<%=request.getAttribute("followup_significativi_inseriti") %>','<%=request.getAttribute("attivita_gravi_inseriti") %>');disabilitaAll();">

<form name="addticket" action="ReatiNC.do?command=Insert&auto-populate=true" method="post">
<input type = "hidden" id = "Inseriti" name = "inseriti">
<input type = "hidden" id = "followup_formali_inseriti" name = "followup_formali_inseriti">
<input type = "hidden" id = "followup_significativi_inseriti" name = "followup_significativi_inseriti">
<input type = "hidden" id = "attivita_gravi_inseriti" name = "attivita_gravi_inseriti">
 <input type = "hidden" name = "tipoNc" value = "<%=request.getAttribute("TipoNC") %>">

<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla"  onClick="window.close()">
<br>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
<%-- include basic troubleticket add form --%>

<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.reati.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ReatiAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ReatiPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
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
function selectCarattere(str, n, m, x){
	  
		elm1 = document.getElementById("dat"+n);
		elm2 = document.getElementById("dat"+m);
		
		
		if(str == "Provvedimenti"){
			car = document.addticket.Provvedimenti.value;
		}
		if(str == "ReatiAmministrative"){
			car = document.addticket.ReatiAmministrative.value;
		}
		if(str == "ReatiPenali"){
			car = document.addticket.ReatiPenali.value;
		}
		
		if(car == 9 || (car == 6 && str == "ReatiPenali")){
			elm1.style.visibility = "visible";
			elm2.style.visibility = "visible";
			
		}
		else {
			elm1.style.visibility = "hidden";
			elm2.style.visibility = "hidden";
			
			if(x == 1){
			document.forms['addticket'].descrizione1.value="";
			}
			if(x == 2){
			document.forms['addticket'].descrizione2.value="";
			}
			if(x == 3){
			document.forms['addticket'].descrizione3.value="";
			}
		}
	  }
  function checkForm(form){
    formTest = true;
    message = "";

//     if (form.assignedDate.value == "") {
//         message += label("check.reati.data_richiesta.selezionato","- Controllare che il campo \"Data\" sia stato popolato\r\n");
//         formTest = false;
//       }
    /*if(form.tipo_richiesta.value==""){
   	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Protocollo\" sia stato popolato \r\n");
        formTest = false;

   	 }*/
    <dhv:include name="ticket.contact" none="true">
    if (form.siteId.value == "-1") {
      message += label("check.reati.richiedente.selezionato","- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
      formTest = false;
    }

    if (form.ReatiPenali.value == "-1") {
        message += label("check.reati.richiedente.selezionato","- Controllare che l'illecito penale sia stato inserito\r\n");
        formTest = false;
      }
    

    
    </dhv:include>
   
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }
 
</script>
<input type="hidden" name="idC" value="<%= request.getAttribute("idC")%>" >
<input type="hidden" name="idNC" value="<%= request.getAttribute("idNC")%>" >
  
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Notizia di Reato</dhv:label></strong>
    </th>
	</tr>
	
  <dhv:include name="stabilimenti-sites" none="true">
 <%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
    <tr>
      <td nowrap class="formLabel">
        ASL di competenza
      </td>
      <td>
       <%if (OrgDetails.getSiteId()>0 && OrgDetails.getTipologia()!=255){ %>
		<%=SiteIdList.getSelectedValue((Integer)request.getAttribute("id_asl"))%> 
		<input type="hidden" name="siteId" id = "siteId" value="<%=(Integer)request.getAttribute("id_asl")%>">
		<%}
		else
		{
			UserBean utente = (UserBean)session.getAttribute("User");
			if(utente.getSiteId()>0)
			{
			%>
			<%=SiteIdList.getSelectedValue(utente.getSiteId()) %>
			<input type="hidden" name="siteId" id = "siteId" value="<%=utente.getSiteId()%>">
			<%
			}
			else
			{%>
				<%=SiteIdList.getSelectedValue((Integer)request.getAttribute("id_asl")) %>
			<input type="hidden" name="siteId" id = "siteId" value="<%=(Integer)request.getAttribute("id_asl")%>">
			<%
			}
		}
		%>
      </td>
    </tr>
<%--</dhv:evaluate>  --%>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
	
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
 
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="idStabilimento" value="<%=OrgDetails.getIdStabilimento()%>">
        <input type="hidden" id="altId" name="altId" value="<%=OrgDetails.getAltId()%>"/>
        <input type="hidden" name="idApiario" value="<%=OrgDetails.getIdStabilimento()%>">
        <input type="hidden" name="id_apiario" value="<%=OrgDetails.getIdStabilimento()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
    
  
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>
  

    <% String dataC = request.getAttribute("dataC").toString(); %>

   
  <tr>
  <td nowrap class="formLabel">
      <dhv:label name="">Protocollo n.</dhv:label>
    </td>
  <td >
	  <input  type="text" name="tipo_richiesta"  value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="60"/>
      <input type="hidden" name="check"/>
    </td>
  </tr>
  <tr class="containerBody" style="display: none">
    <td nowrap class="formLabel">
      Del
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="assignedDate" timestamp="<%= dataC %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
      <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>
  
  
 <dhv:include name="organization.source" none="true">
   <tr>
      <td name="reatiPenali1" id="reatiPenali1" nowrap class="formLabel">
        <dhv:label name="">Illecito Penale</dhv:label>
      </td>
    <td>
     <table class="noborder">
      <tr>
      <td >
      <%
      ReatiPenali.setSelectSize(5);
      ReatiPenali.setJsEvent("onChange=\"javascript:selectCarattere('ReatiPenali', '5', '6', '3');\"");
        %>
        <%= ReatiPenali.getHtmlSelect("ReatiPenali",TicketDetails.getReatiPenali()) %><font color="red">*</font><br>
         (* In caso di selezione multipla tenere premuto il tasto<br>
         Ctrl durante la Selezione)
      
       
    		</td>
          	<td style="visibility: hidden;" id="dat5">
        		Descrizione
        	</td>
        	<td style="visibility: hidden;" id="dat6">
           		<input type="text" name="descrizione3" size="50">
          	</td>
       </tr>
       </table>
      
     
    </td>
  </tr>
</dhv:include>


  <tr>
  <td nowrap class="formLabel">
      <dhv:label name="">Norma Violata</dhv:label>
    </td>
  <td >
     
     
	  <input type="text" name="normaviolata"   size="20" maxlength="256"/> 
    
    </td>
  </tr>


  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="reati.note">Note</dhv:label>
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
  <input type = "hidden" id = "followup_gravi_inseriti" name = "followup_gravi_inseriti">
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<input type="hidden" id="stabId" name="stabId" value="<%=request.getParameter("stabId")%>"/>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
</table>

<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla"  onClick="window.close()">
</form>
