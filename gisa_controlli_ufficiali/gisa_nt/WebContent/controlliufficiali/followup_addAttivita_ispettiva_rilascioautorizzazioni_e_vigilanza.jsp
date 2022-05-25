<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="OrgDetails" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>

<script type="text/javascript" src="javascript/controlli_sottoattivita.js"></script>
 <SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>

<script>
focus_=true ;
 </script>
 <body  onblur="if(focus_==true){window.focus();}" onmouseout="focus_=true;" onmouseover="focus_=false;"  
 onunload="abilitaAll()" onload = "disabilitaAll();abilitaStatiNc('<%= request.getAttribute("inserito")%>','<%=request.getAttribute("TipoNC") %>','<%= request.getAttribute("idC")%>','<%=request.getAttribute("followup_formali_inseriti") %>','<%=request.getAttribute("followup_significativi_inseriti") %>','<%=request.getAttribute("attivita_gravi_inseriti") %>','<%=request.getAttribute("followup_gravi_inseriti") %>');">
 <form name="addticket" action="FollowupNC.do?command=Insert&auto-populate=true" method="post">
<%-- Trails --%>

<%-- End Trails --%>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla"  onClick="window.close()">
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>


<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.followup.base.Ticket" scope="request"/>
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="FollowupAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Followup" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

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




 

  function controlloLimitazioni(){

		
		for(i=0; i<document.addticket.limitazioniFollowup.options.length;i++){
			if(document.addticket.limitazioniFollowup.options[i].selected && document.addticket.limitazioniFollowup.options[i].value=="2"){

				return true;
				}
		}
		return false;
		
	}
  function checkForm(form){
    formTest = true;
    message = "";
    
    
    var partsCu = document.getElementById("dataCU").value.split("/");
	var dateCu = new Date(partsCu[1] + "/" + partsCu[0] + "/" + partsCu[2]);
	var partsFollowup = document.getElementById("assignedDate").value.split("/");
	var dateFollowup = new Date(partsFollowup[1] + "/" + partsFollowup[0] + "/" + partsFollowup[2]);
	if (dateCu>dateFollowup){
		message += label("check.followup.assignedDate","- Controllare che il campo \"Data\" sia uguale o successivo alla data del CU\r\n");
	      formTest = false;
	}

    if(form.limitazioniFollowup.value=="-1"){// se non ho slezionato niente da bpèi
		 message += label("check.followup.richiedente.selezionato","- Controllare che il campo \"Provvedimenti adottati\" sia stato popolato\r\n");
	      formTest = false;
	}

    if(form.nonConformitaGraviValutazione!=null && (form.nonConformitaGraviValutazione.value == "" || trim(document.getElementById('valutazione_rischio_gravi').value) == 'INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' GRAVI RISCONTRATE')){// se non ho slezionato niente 
		 message += label("check.followup.richiedente.selezionato","- Controllare che il campo \"Valutazione del rischio n.c. gravi\" sia stato popolato\r\n");
	      formTest = false;
	}

	
	//d.zanfardino
    if(form.assignedDate.value==""){// Data Risoluzione
		 message += label("check.followup.data.selezionato","- Controllare che il campo \"Data Termine per la risoluzione\" sia stato popolato\r\n");
	      formTest = false;
	}
    
    <dhv:include name="ticket.contact" none="true">
    if (form.siteId.value == "-1") {
      message += label("check.followup.richiedente.selezionato","- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
      formTest = false;
    }

   
    

    
    </dhv:include>
   
    
   
    <dhv:include name="ticket.resolution" none="false">
    if (form.closeNow){
      if (form.closeNow.checked && form.solution.value == "") {
        message += label("check.ticket.resolution.atclose","- Resolution needs to be filled in when closing a ticket\r\n");
        formTest = false;
      }
    }
    </dhv:include>
    
    <dhv:include name="ticket.actionPlans" none="false">
      if (form.insertActionPlan.checked && form.assignedTo.value <= 0) {
        message += label("check.ticket.assignToUser","- Please assign the ticket to create the related action plan.\r\n");
        formTest = false;
      }
      if (form.insertActionPlan.checked && form.actionPlanId.value <= 0) {
        message += label("check.actionplan","- Please select an action plan to be inserted.\r\n");
        formTest = false;
      }
    </dhv:include>
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
       return false;
    } else {
    	if(controlloLimitazioni()==true)
    	{
        	alert("Attenzione! \n Se l'attività viene sospesa, devi andare  \n nella scheda dell'impresa e modificare lo stato dell'attività (Sospeso).");
    	}
      return true;
    }
  }
  
</script>
<%
if (request.getAttribute("inserito")!=null)
{
%>
<font color = "red">Followup Inserito in maniera corretta.</font>
<a href = "#" >Torna Indietro</a>

<%	
}
%>
<input type = "hidden" id = "Inseriti" name = "inseriti">
<input type = "hidden" id = "followup_formali_inseriti" name = "followup_formali_inseriti">
<input type = "hidden" id = "followup_significativi_inseriti" name = "followup_significativi_inseriti">
<input type = "hidden" id = "attivita_gravi_inseriti" name = "attivita_gravi_inseriti">

<input type = "hidden" id = "followup_gravi_inseriti" name = "followup_gravi_inseriti">

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Followup</dhv:label></strong>
    </th>
	</tr>
	<input type = "hidden" name = "tipoNc" value = "<%=request.getAttribute("TipoNC") %>">
	<input type="hidden" name="idC" value="<%= request.getAttribute("idC")%>" >
  
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
      
       <%
       if (OrgDetails.getOrgId()>0)
       {
       %>
          <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
      <%}
       else if(OrgDetails.getIdStabilimento()>0 && OrgDetails.getIdApiario() < 0) {
    	   %>
    	       <input type="hidden" name="idStabilimento" value="<%=OrgDetails.getIdStabilimento()%>">
    	   
    	   <% } else {%>
    	       	  <input type="hidden" name="id_apiario" value="<%=OrgDetails.getIdApiario()%>">
    	   	   	  <input type="hidden" name="idApiario" value="<%=OrgDetails.getIdApiario()%>">
    	   	
    	   <% } %>
   
       

  
<input type="hidden" name="idControlloUfficiale" id="idControlloUfficiale" value="<%= (String)request.getParameter("idControllo") %>">
      
<input type="hidden" name="identificativoNC" id="identificativoNC" value="<%= (String)request.getAttribute("identificativoNC") %>">

<tr class="containerBody">
    <td nowrap class="formLabel">
     Data termine per la risoluzione non conformita'
    </td>
    <td>
      
      <input readonly type="text" id="assignedDate" name="assignedDate" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			<%= showAttribute(request, "assignedDateError") %> <font color="red">*</font>
		
			
  <%--<zeroio:dateSelect form="addticket" field="assignedDate"  timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false"  />
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>--%>
        
        
    </td>
  </tr>
 <dhv:include name="organization.source" none="true">
   <tr>
      <td  nowrap class="formLabel">
        <dhv:label name="">Provvedimenti Adottati</br></dhv:label>
      </td>
    <td>
     <table border=0 class="empty">
      <tr>
      <td >
     <%--Followup.setMultiple(false); --%>
        <%= Followup.getHtmlSelect("limitazioniFollowup",TicketDetails.getFollowupPenali()) %><font color="red">*</font>
        </td>
       </tr>
       </table>
       </td>
       </tr>
        <tr>
      <td  nowrap class="formLabel">
        <dhv:label name=""> Note</dhv:label>
      </td>
    <td>
     <table border=0 class="empty">
      <tr>
      <td >
        <textarea name="notefollowup" cols="55" rows="8"></textarea>
         </td>      
       </tr>
       </table>
      
     
    </td>
  </tr>
  	<% if(request.getAttribute("TipoNC").equals("3")) { %>
       <tr>
        <td  nowrap class="formLabel">
        	<dhv:label name=""> Valutazione del rischio n.c. gravi</dhv:label>
      	</td>
       	<td>
            <textarea id = "valutazione_rischio_gravi" name="nonConformitaGraviValutazione" cols="55" rows="6" onclick="if (this.value =='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' GRAVI RISCONTRATE'){this.value=''}">INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA' GRAVI RISCONTRATE</textarea>
            <font color="red">*</font> 
        </td>
       </tr>
       <% } %>
    </table> 
    </td>
  </tr>
</dhv:include>
 
  
</table>
<br />
<a name="categories"></a>

<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<input type="hidden" id="stabId" name="stabId" value="<%=request.getParameter("stabId")%>"/>
<input type="hidden" id="altId" name="altId" value="<%=request.getParameter("altId")%>"/>
<input type="hidden" name="dataCU" id="dataCU" value="<%=  toDateasString(CU.getAssignedDate()) %>" />

<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla"  onClick="window.close()">
</form>

</body>