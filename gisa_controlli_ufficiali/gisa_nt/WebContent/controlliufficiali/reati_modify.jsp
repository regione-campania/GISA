<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script language="JavaScript">
function updateSubList1() {
  var orgId = document.forms['details'].orgId.value;
  if(orgId != '-1'){
    var sel = document.forms['details'].elements['catCode'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets_asl.do?command=CategoryJSList&form=details&catCode=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  } else {
    var sel = document.forms['details'].elements['catCode'];
    sel.options.selectedIndex = 0;
    alert(label("select.account.first",'You have to select an Account first'));
    return;
  }
}
function updateSubList2() {
  var orgId = document.forms['details'].orgId.value;
  var sel = document.forms['details'].elements['subCat1'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets_asl.do?command=CategoryJSList&form=details&subCat1=" + escape(value)+'&orgId='+orgId;
  window.frames['server_commands'].location.href=url;
}
<dhv:include name="ticket.subCat2" none="true">
function updateSubList3() {
  var orgId = document.forms['details'].orgId.value;
  var sel = document.forms['details'].elements['subCat2'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTickets_asl.do?command=CategoryJSList&form=details&subCat2=" + escape(value)+'&orgId='+orgId;
  window.frames['server_commands'].location.href=url;
}
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  function updateSubList4() {
    var orgId = document.forms['details'].orgId.value;
    var sel = document.forms['details'].elements['subCat3'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTickets_asl.do?command=CategoryJSList&form=details&subCat3=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
function updateUserList() {
  var sel = document.forms['details'].elements['departmentCode'];
  var value = sel.options[sel.selectedIndex].value;
  var orgSite = document.forms['details'].elements['orgSiteId'].value;
  var url = "TroubleTickets_asl.do?command=DepartmentJSList&form=details&dept=Assigned&orgSiteId="+ orgSite +"&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateResolvedByUserList() {
  var sel = document.forms['details'].elements['resolvedByDeptCode'];
  var value = sel.options[sel.selectedIndex].value;
  var orgSite = document.forms['details'].elements['orgSiteId'].value;
  var url = "TroubleTickets_asl.do?command=DepartmentJSList&form=details&dept=Resolved&orgSiteId="+ orgSite + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
  function changeDivContent(divName, divContents) {
  if(document.layers){
    // Netscape 4 or equiv.
    divToChange = document.layers[divName];
    divToChange.document.open();
    divToChange.document.write(divContents);
    divToChange.document.close();
  } else if(document.all){
    // MS IE or equiv.
    divToChange = document.all[divName];
    divToChange.innerHTML = divContents;
  } else if(document.getElementById){
    // Netscape 6 or equiv.
    divToChange = document.getElementById(divName);
    divToChange.innerHTML = divContents;
  }
}
function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
}
function checkForm(form) {
  formTest = true;
  message = "";
  
if (form.assignedDate.value == "") { 
    message += label("check.ticket.dataRichiesta.entered","- Controlla che \"Data Rilevazione Reato\" sia stata selezionata\r\n");
    formTest = false;
  }
  <dhv:include name="ticket.resolution" none="false">
  if (form.closeNow.checked && form.solution.value == "") { 
    message += label("check.ticket.resolution.atclose","- Resolution needs to be filled in when closing a ticket\r\n");
    formTest = false;
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
    return true;
  }
}

function setAssignedDate(){
  resetAssignedDate();
  if (document.forms['details'].assignedTo.value > 0){
    document.forms['details'].assignedDate.value = document.forms['details'].currentDate.value;
  }
}

function resetAssignedDate(){
  document.forms['details'].assignedDate.value = '';
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

function selectUserGroups() {
  var siteId = document.forms['details'].orgSiteId.value;
  if ('<%= OrgDetails.getOrgId() %>' != '-1') {
    popUserGroupsListSingle('userGroupId','changeUserGroup', '&userId=<%= User.getUserRecord().getId() %>&siteId='+siteId);
  } else {
    alert(label("select.account.first",'You have to select an Account first'));
    return;
  }
}

function popKbEntries() {
  var siteId = document.forms['details'].orgSiteId.value;
  var form = document.forms['details'];
  var catCode = form.elements['catCode'];
  var catCodeValue = catCode.options[catCode.selectedIndex].value;
  if (catCodeValue == '0') {
    alert(label('','Please select a category first'));
    return;
  }
  var subCat1 = form.elements['subCat1'];
  var subCat1Value = subCat1.options[subCat1.options.selectedIndex].value;
<dhv:include name="ticket.subCat2" none="true">
  var subCat2 = form.elements['subCat2'];
  var subCat2Value = subCat2.options[subCat2.options.selectedIndex].value;
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  var subCat3 = form.elements['subCat3'];
  var subCat3Value = subCat3.options[subCat3.options.selectedIndex].value;
</dhv:include>
  var url = 'KnowledgeBaseManager.do?command=Search&popup=true&searchcodeSiteId='+siteId+'&searchcodeCatCode='+catCodeValue;
  url = url + '&searchcodeSubCat1='+ subCat1Value;
<dhv:include name="ticket.subCat2" none="true">
  url = url + '&searchcodeSubCat2='+ subCat2Value;
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  url = url + '&searchcodeSubCat3='+ subCat3Value;
</dhv:include>
  popURL(url, 'KnowledgeBase','600','550','yes','yes');
}

 function resetCarattere(){
  	
  		
  		elm1 = document.getElementById("dat1");
 		elm2 = document.getElementById("dat2");
 		elm3 = document.getElementById("dat3");
 		elm4 = document.getElementById("dat4");
 		elm5 = document.getElementById("dat5");
 		elm6 = document.getElementById("dat6");
 		
 		elm1.style.visibility = "hidden";
 		elm2.style.visibility = "hidden";
 		elm3.style.visibility = "hidden";
 		elm4.style.visibility = "hidden";
 		elm5.style.visibility = "hidden";
 		elm6.style.visibility = "hidden";
 		
 		document.details.Provvedimenti.selectedIndex=0;
 		document.details.ReatiAmministrative.selectedIndex=0;
 		document.details.ReatiPenali.selectedIndex=0;
 		
  }
  
  function selectCarattere(str, n, m, x){
  
 		elm1 = document.getElementById("dat"+n);
 		elm2 = document.getElementById("dat"+m);
 		
 		
 		if(str == "Provvedimenti"){
 			car = document.details.Provvedimenti.value;
 		}
 		if(str == "ReatiAmministrative"){
 			car = document.details.ReatiAmministrative.value;
 		}
 		if(str == "ReatiPenali"){
 			car = document.details.ReatiPenali.value;
 		}
 		
 		if(car == 9 || (car == 6 && str == "ReatiPenali")){
 			elm1.style.visibility = "visible";
 			elm2.style.visibility = "visible";
 			
 		}
 		else {
 			elm1.style.visibility = "hidden";
 			elm2.style.visibility = "hidden";
 			
 			if(x == 1){
 			document.forms['details'].descrizione1.value="";
 			}
 			if(x == 2){
 			document.forms['details'].descrizione2.value="";
 			}
 			if(x == 3){
 			document.forms['details'].descrizione3.value="";
 			}
 		}
 	  }
</script>   
     <%
   TicketDetails.setPermission();
   String permission = TicketDetails.getPermission_ticket()+"-reati-edit";
   String url = TicketDetails.getURlDettaglio();
   
   %>
    
     <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
          <dhv:permission name="<%=permission%>">
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='<%=url %>Reati.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=url %>Reati.do?command=TicketDetails&orgId=<%=OrgDetails.getOrgId()%>&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
          <dhv:permission name="<%=permission%>">
            <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
          </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=url %>Reati.do?command=TicketDetails&orgId=<%=OrgDetails.getOrgId()%>&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
          <%= showAttribute(request, "closedError") %>
       </dhv:evaluate>
      </dhv:evaluate>
    <br />
    <dhv:formMessage />
   <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="reati.information">Scheda Notizia di Reato</dhv:label></strong>
      </th>
	</tr>
	<dhv:include name="" none="true">
  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      ASL di competenza
    </td>
    <td>
      <%= SiteIdList.getSelectedValue(TicketDetails.getSiteId()) %>
      
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
      <dhv:label name="">Identificativo Non Conformità</dhv:label>
    </td>
   
     
      <td>
      		<%= TicketDetails.getIdentificativonc() %>
      </td>
    
  </tr>
	<tr class="containerBody" style="display: none">
      <td nowrap class="formLabel">
        <dhv:label name="reati.data_richiestas">Data Rilevazione Reato</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="details" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
<tr class="containerBody">
  <td nowrap class="formLabel">
     Protocollo
       
    </td>
    <td>
        <input type="text" name="tipo_richiesta" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/>
       <input type="hidden" name="pippo" value="<%= toHtmlValue(TicketDetails.getPippo()) %>">
    </td>
</tr>
    
    
 <dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="reatiPenali1" id="reatiPenali1" nowrap class="formLabel">
        <dhv:label name="">Illecito Penale</dhv:label>
      </td>
    <td>
     <table border=0 class="noborder">
      <tr>
      <td >
      <%	ReatiPenali.setJsEvent("onChange=\"javascript:selectCarattere('ReatiPenali', '5', '6', '3');\"");
        %>
        <%= ReatiPenali.getHtmlSelect("ReatiPenali",TicketDetails.getReatiPenali()) %>
         
       
    		</td>
    		<dhv:evaluate if="<%=TicketDetails.getReatiPenali() != 6 %>">
          	<td style="visibility: hidden;" id="dat5">
        		Descrizione<font color="red">*</font>
        	</td>
        	<td style="visibility: hidden;" id="dat6">
           		<input type="text" name="descrizione3" size="80" value="<%=TicketDetails.getDescrizione3() %>" id="descrizione3">
          	</td>
          	</dhv:evaluate>
          	<dhv:evaluate if="<%=TicketDetails.getReatiPenali() == 6 %>">
          	<td style="visibility: visible;" id="dat5">
        		Descrizione<font color="red">*</font>
        	</td>
        	<td style="visibility: visible;" id="dat6">
           		<input type="text" name="descrizione3" size="80" value="<%=TicketDetails.getDescrizione3() %>" id="descrizione3">
          	</td>
          	</dhv:evaluate>
       </tr>
       </table>
    </td>
  </tr>
</dhv:include>
    
  <tr class="containerBody">
  <td nowrap class="formLabel">
      <dhv:label name="">Norma Violata</dhv:label>
    </td>
  <td >

	  <input type="text" name="normaviolata" value="<%=TicketDetails.getNormaviolata() %>"  size="20" maxlength="256"/> <font color="red">*</font>
    
    </td>
  </tr>
    
    
  <tr class="containerBody">
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

 
  
	</table>
        &nbsp;<br>
   <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
        <dhv:permission name="<%=permission%>">
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='<%=url %>Reati.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=url %>Reati.do?command=TicketDetails&orgId=<%=OrgDetails.getOrgId()%>&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
        <dhv:permission name="<%=permission%>">
          <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
        </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=url %>Reati.do?command=TicketDetails&orgId=<%=OrgDetails.getOrgId()%>&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
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