<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.abusivismi.base.Organization" scope="request"/>
<form name="addticket" action="AbusivismiSanzioni.do?command=Insert&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Vigilanzas.do"><dhv:label name="">Operatori Abusivi</dhv:label></a> > 
<a href="Vigilanzas.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
 

<a href="Abusivismi.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Scheda Operatore Abusivo</dhv:label></a> >
 <a href="Abusivismi.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
 <a href="AbusivismiVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
 <a href="AbusivismiNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idNC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Non Conformità Rilevata</dhv:label></a> >
 
<%--a href="Abusivismi.do?command=ViewSanzioni&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="sanzioni">Sanzioni Amministrative</dhv:label></a> --%> 
<dhv:label name="sanzioni.aggiungi">Aggiungi Sanzione Amministrativa</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='AbusivismiNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>'">
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
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.sanzioni.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sanzioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAbusivismi.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript">



function aggiungialtro(indice){

if(indice==2){

document.getElementById("obbligato"+indice).style.display="block";
document.getElementById("bottone"+indice).style.display="block";
document.getElementById("bottone1").style.display="none";
	
}

if(indice==3){
	document.getElementById("bottone2").style.display="none";
	document.getElementById("obbligato"+indice).style.display="block";

	
}
	
}



function checkImporto(form)
{
if (form.cause.value == "") {
      alert("- Controllare che il campo 'importo' sia stato popolato ");
    }
}


  function checkForm(form){
    formTest = true;
    message = "";


arr=form.Provvedimenti;

    
    if (form.assignedDate.value == "") {
        message += label("check.sanzioni.data_richiesta.selezionato","- Controllare che il campo \"Data Accertamento\" sia stato popolato\r\n");
        formTest = false;
      }
    if(form.tipo_richiesta.value==""){
   	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Processo Verbale\" sia stato popolato \r\n");
        formTest = false;

   	 }
 if(form.trasgressore.value=="Altro" && form.trasgressorealtro.value==''){
	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Trasgressore\" sia stato popolato\r\n");
     formTest = false;

	 }
 
 if( (form.obbligatoinSolido.value=="Altro" || form.obbligatoinSolido.value=="Nessuno") &&  (form.trasgressore.value=='Altro')){
	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che almeno uno tra i campi Trasgressore e obbligato in Solido coincia con la ragione sociale\r\n");
     formTest = false;

	 }
 
 for(k=0 ; k<arr.length;k++){
		if(arr[k].value=="-1" && arr[k].selected==true){
			message += label("check.sanzioni.richiedente.selezionato","- Controllare di aver selezionato una azione \r\n");
		      formTest = false;

			}

     }

 if(form.pagamento.value==""){
	 message += label("check.sanzioni.richiedente.selezionato","- Controllare di aver inserito un importo per pagamento in misura ridotta\r\n");
     formTest = false;

	 }

 




    
    <dhv:include name="ticket.contact" none="true">
    if (form.siteId.value == "-1") {
      message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
      formTest = false;
    }
    </dhv:include>
    <dhv:include name="ticket.contact" none="true">
    if (form.orgId.value == "-1") {
      message += label("check.sanzioni.richiedente.selezionato","- Controllare che l'Trasgressore sia stata selezionata\r\n");
      formTest = false;
    }
    </dhv:include>
    

if(form.Provvedimenti.value==""){
	 message += label("check.sanzioni.data_richiesta.selezionato","- Controllare che il campo \"Azione non conforme per\" sia stato popolato\r\n");
     formTest = false;

}

    
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
    if (isNaN(form.pagamento.value)){
		 message += "- Valore errato per il campo \"Pagamento in Misura Ridotta\". Si prega di inserire l'importo in cifre\r\n";
			 formTest = false;
		}	
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }
  //used when a new contact is added
  function insertOption(text,value,optionListId){
   var obj = document.forms['addticket'].contactId;
   insertIndex= obj.options.length;
   obj.options[insertIndex] = new Option(text,value);
   obj.selectedIndex = insertIndex;
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
 
  function selectUserGroups() {
    var orgId = document.forms['addticket'].orgId.value;
    var siteId = document.forms['addticket'].orgSiteId.value;
    if (orgId != '-1') {
      popUserGroupsListSingle('userGroupId','changeUserGroup', '&userId=<%= User.getUserRecord().getId() %>&siteId='+siteId);
    } else {
      alert(label("select.account.first",'You have to select an Vigilanza first'));
      return;
    }
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
 		
 		document.addticket.Provvedimenti.selectedIndex=0;
 		document.addticket.SanzioniAmministrative.selectedIndex=0;
 		document.addticket.SanzioniPenali.selectedIndex=0;
 		
  }
  
  function selectCarattere(str, n, m, x){
  
 		elm1 = document.getElementById("dat"+n);
 		elm2 = document.getElementById("dat"+m);
 		
 		
 		if(str == "Provvedimenti"){
 			car = document.addticket.Provvedimenti.value;
 		}
 		if(str == "SanzioniAmministrative"){
 			car = document.addticket.SanzioniAmministrative.value;
 		}
 		if(str == "SanzioniPenali"){
 			car = document.addticket.SanzioniPenali.value;
 		}
 		
 		if(car == 9 || (car == 6 && str == "SanzioniPenali")){
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
</script>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Sanzione Amministrativa</dhv:label></strong>
    </th>
	</tr>
	
	 
 <%-- da commentare --%>
  <input type="hidden" name="idC" value="<%= request.getAttribute("idC")%>" >
  <input type="hidden" name="idNC" value="<%= request.getAttribute("idNC")%>" >
  <dhv:include name="stabilimenti-sites" none="true">
 <%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
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
<%--</dhv:evaluate>  --%>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
	
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
  <tr>
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Trasgressore</dhv:label>
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
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Identificativo Non Conformità</dhv:label>
    </td>
    <td>
      <%= (String)request.getAttribute("identificativoNC") %>
      <input type="hidden" name="idControlloUfficiale" id="idControlloUfficiale" value="<%= (String)request.getParameter("idControllo") %>">
      <input type="hidden" name="idC" id="idC" value="<%= (String)request.getParameter("idC") %>">
     <input type="hidden" name="identificativoNC" id="identificativoNC" value="<%= (String)request.getAttribute("identificativoNC") %>">
 
    </td>
  </tr>
  
    <% String dataC = request.getAttribute("dataC").toString(); %>
   
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Accertamento</dhv:label>
    </td>
    <td>
 <input readonly type="text" id="assignedDate" name="assignedDate" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>      <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>
  <tr>
  <td nowrap class="formLabel">
      <dhv:label name=""> Processo Verbale</dhv:label>
    </td>
  <td >
     
     
  
	  <input type="text" name="tipo_richiesta" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/><font color="red">*</font>
      <input type="hidden" name="check"/>
    </td>
  </tr>
 
	
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.importos">Pagamento in Misura Ridotta (euro)</dhv:label>
    </td>
    <td>
      <input type="text" name="pagamento" value="<%=TicketDetails.getPagamento() %>" size="20" maxlength="256" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/> 
    </td>
  </tr>
  
  
  
  <tr>
  <td nowrap class="formLabel">
      <dhv:label name=""> Trasgressore</dhv:label>
    </td>
  <td>
  
  <select name="trasgressore" onchange="if(this.value=='Altro'){document.getElementById('trasgressorealtro').style.display=''; }else{document.getElementById('trasgressorealtro').style.display='none';}">
  <option value="<%=OrgDetails.getName() %>"><%=OrgDetails.getName() %></option>
  <option value="Altro">Altro</option>
  </select>
  <font color="red">*</font>
      
   <input style="display:none" type="text" id="trasgressorealtro" name="trasgressorealtro" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/>

	
	
    </td>
  </tr>
  
  <tr>
  <td nowrap class="formLabel">
      <dhv:label name="">Obbligato in Solido 1</dhv:label>
    </td>
    
    
  <td >
  <table class="noborder"><tr>
  
  <td>
  
  <select name = "obbligatoinSolido"  onchange="if(this.value=='Altro'){document.getElementById('obbligatoinSolidoAltro').style.display=''; }else{document.getElementById('obbligatoinSolidoAltro').style.display='none';}">
  
  <option value="Nessuno">Nessuno</option>
  <option value="<%=OrgDetails.getName() %>"><%=OrgDetails.getName() %></option>
   <option value="Altro">Altro</option>
  </select>
  
  <input style="display: none" type="text" name="obbligatoinSolidoAltro"  id="obbligatoinSolidoAltro" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/>
  </td>
  
   <td style="display:block" id="bottone1"> &nbsp; <input type="button" value="Aggiungi Altro" onclick="javascript:aggiungialtro(2);"></td>
  
  </tr></table>
 
    </td>
    
    
  </tr>
  
  <tr  style="display:none" id="obbligato2">
  <td nowrap class="formLabel">
      <dhv:label name="">Obbligato in Solido 2</dhv:label>
    </td>
  <td >
  <table class="noborder">
  <tr>
  <td>
  
   <select name = "obbligatoinSolido2"  onchange="if(this.value=='Altro'){document.getElementById('obbligatoinSolidoAltro2').style.display=''; }else{document.getElementById('obbligatoinSolidoAltro2').style.display='none';}">
  
  <option value="Nessuno">Nessuno</option>
  <option value="<%=OrgDetails.getName() %>"><%=OrgDetails.getName() %></option>
   <option value="Altro">Altro</option>
  </select>
  
  <input type="text" style="display: none" name="obbligatoinSolidoAltro2"  id="obbligatoinSolidoAltro2" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/>
  
  
  </td>
  <td id = "bottone2" style="display:none" > &nbsp; <input type="button" value="Aggiungi Altro" onclick="javascript:aggiungialtro(3);"></td>
  
  </tr>
  
  
  </table>
      
    </td>
     
  </tr>
  
  <tr style="display: none" id = "obbligato3">
  <td nowrap class="formLabel" >
      <dhv:label name="">Obbligato in Solido 3</dhv:label>
    </td>
  <td >
  <table class="noborder">
  
  <tr>
  <td>

<select name = "obbligatoinSolido3"  onchange="if(this.value=='Altro'){document.getElementById('obbligatoinSolidoAltro3').style.display=''; }else{document.getElementById('obbligatoinSolidoAltro3').style.display='none';}">
  
  <option value="Nessuno">Nessuno</option>
  <option value="<%=OrgDetails.getName() %>"><%=OrgDetails.getName() %></option>
   <option value="Altro">Altro</option>
  </select>
  
  <input type="text" style="display: none" name="obbligatoinSolidoAltro3"  id="obbligatoinSolidoAltro3" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/>
  
  </td>
<td>&nbsp;</td>  
  </tr>
  </table>
      
    </td>
  </tr>
  
  
  <dhv:include name="organization.source" none="true">
   <tr>
      <td name="provvedimento1" id="provvedimento1" nowrap class="formLabel">
        <dhv:label name="">Azione non Conforme per</dhv:label>
      </td>
    <td>
   <table class="noborder">
      <tr>
      <td >
      <%	Provvedimenti.setJsEvent("onChange=\"javascript:selectCarattere('Provvedimenti', '1', 2, '1');\"");
        %>
       <%= Provvedimenti.getHtmlSelect("Provvedimenti",TicketDetails.getProvvedimenti()) %>
         
    		</td>
          	<td style="visibility: hidden;" id="dat1">
        		Descrizione
        	</td>
        	<td style="visibility: hidden;" id="dat2">
           		<input type="text" name="descrizione1" size="50">
          	</td>
       </tr>
       </table>
    </td>
  </tr>
</dhv:include>

  <tr class="containerBody">
    <td valign="top" class="formLabel">
     Norma Violata
    </td>
    <td>
      <input type="text" name="normaviolata"  size="20" maxlength="256" /> 
    </td>
  </tr>
  



<tr> 
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
    </td>
    <td>Note </br>
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
  
 <!--<dhv:include name="organization.source" none="true">
   <tr>
      <td name="sanzioniPenali1" id="sanzioniPenali1" nowrap class="formLabel">
        <dhv:label name="">Sanzioni Penali</dhv:label>
      </td>
    <td>
     <table border=0>
      <tr>
      <td >
      <%	//SanzioniPenali.setJsEvent("onChange=\"javascript:selectCarattere('SanzioniPenali', '5', '6', '3');\"");
        %>
        <%//= SanzioniPenali.getHtmlSelect("SanzioniPenali",TicketDetails.getSanzioniPenali()) %>
         
       
    		</td>
          	<td style="visibility: hidden;" id="dat5">
        		Descrizione<font color="red">*</font>
        	</td>
        	<td style="visibility: hidden;" id="dat6">
           		<input type="text" name="descrizione3" size="50">
          	</td>
       </tr>
       </table>
      
     
    </td>
  </tr>
</dhv:include>-->
  <!--<dhv:include name="organization.source" none="true">
   <tr>
      <td name="sanzioni1" id="sanzioni1" nowrap class="formLabel">
        <dhv:label name="">Sanzioni</dhv:label>
      </td>
    <td>
    
      <table>
        <tr>
          <td>
            Alimenti
          </td>
          <td>
            <input type="checkbox" name="tipoSequestro" id="tipoSequestro"/>
          </td>
          <td>
            -
          </td>
          
          
          <td>
            Attrezzature
          </td>
          <td>
            <input type="checkbox" name="tipoSequestroDue" id="tipoSequestroDue"/>
          </td>
          <td>
            -
          </td>
          
          <td>
            Locale
          </td>
          <td>
            <input type="checkbox" name="tipoSequestroTre" id="tipoSequestroTre"/>
          </td>
          <td>
            -
          </td>
          
          
          <td>
            Stabilimento
          </td>
          <td>
            <input type="checkbox" name="tipoSequestroQuattro" id="tipoSequestroQuattro"/>
          </td>
          
          
        </tr>
        </table> 
    
    
    </td>
  </tr>
</dhv:include> -->
<%-- <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Motivazioni</dhv:label>
    </td>
    <td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td>
            <select size="3" name="listView" id="listViewId" multiple>
              <%
              if(Recipient.getId() > 0){
              %>
                <option value="<%= Recipient.getId() %>" selected><%= Recipient.getNameLastFirst() %></option>
              <%}else{%>
                <option value="none" selected><dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label></option>
              <%}%>
            </select>
          </td>
          <td valign="top">
            [<a href="javascript:popContactsListMultipleNew('listViewId','1', 'reset=true<%= User.getUserRecord().getSiteId() == -1?"&includeAllSites=true&siteId=-1":"&mySiteOnly=true&siteId="+ User.getUserRecord().getSiteId() %>');"><dhv:label name="">Aggiungi Motivazioni</dhv:label></a>]<font color="red">*</font>
            <%= showAttribute(request, "contactsError") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>--%>
</table>
<br />
<a name="categories"></a>
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Annulla" onClick="javascript:this.form.action='AbusivismiNonConformita.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>'">
</form>
