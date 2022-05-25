<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcf.modules.controlliufficiali.action.Sanzioni"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%><jsp:useBean id="Ispezioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="ListaNorme" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetailsSanzionata" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="ncAltri" class="org.aspcfs.modules.altriprovvedimenti.base.Ticket" scope="request"/>


  <%! public static String fixString(String nome)
  {
	  String toRet = "";
	  if (nome == null)
		  return nome;
	  
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll("\"", "");
	  return toRet;
	  
  }%>

<script language="JavaScript">



function visualizzaObbligatoinSolido(){

// 	if(document.forms['details'].obbligatoinSolido2.value!=""){
// 		aggiungialtro(2);

// 	}


// 	if(document.forms['details'].obbligatoinSolido3.value!=""){
// 		aggiungialtro(3);

// 		}
// 	//CAMPI TRASGRESSORE
// 	if(document.forms['details'].trasgressore2.value!=""){
// 		aggiungitrasgressore(2);

// 	}


// 	if(document.forms['details'].trasgressore3.value!=""){
// 		aggiungitrasgressore(3);

// 	}
	
	
}


function aggiungialtro(indice){

	if(indice==2){

	document.getElementById("obbligato"+indice).style.display="";
	document.getElementById("bottone"+indice).style.display="";
	document.getElementById("bottone1").style.display="none";
		
	}

	if(indice==3){
		document.getElementById("bottone2").style.display="none";
		document.getElementById("obbligato"+indice).style.display="";

		
	}
		
	}


//AGGIUNTO DA D.ZANFARDINO PER POSSIBILITA' DI INSERIRE FINO A 3 TRASGRESSORI
function aggiungitrasgressore(index){
	if (index==2)
	{
		document.getElementById("trasgressore"+index).style.display="";
		document.getElementById("bottonetrasgressore"+index).style.display="";
		document.getElementById("bottonetrasgressore"+(index-1)).style.display="none";	
	}
	if (index==3)
	{
		document.getElementById("bottonetrasgressore"+(index-1)).style.display="none";
		document.getElementById("trasgressore"+index).style.display="";
	}
}


function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
}
function checkForm(form) {
  formTest = true;
  message = "";
   
if (form.assignedDate.value == "") { 
    message += label("check.ticket.dataRichiesta.entered","- Controlla che \"Data Sanzione\" sia stata selezionata\r\n");
    formTest = false;
  }
  <dhv:include name="ticket.resolution" none="false">
  if (form.closeNow.checked && form.solution.value == "") { 
    message += label("check.ticket.resolution.atclose","- Resolution needs to be filled in when closing a ticket\r\n");
    formTest = false;
  }
  </dhv:include>
  
  var msgAlmenoUno = "- Controllare che almeno uno tra i campi Trasgressore e obbligato in Solido coincida con la ragione sociale del soggetto ispezionato\r\n";
  <% if (OrgDetailsSanzionata.getOrgId()>0) {%>
  msgAlmenoUno = "- Controllare che almeno uno tra i campi Trasgressore e obbligato in Solido coincida con il soggetto (diverso da quello ispezionato) a cui attribuire la non conformita'\r\n";
  <%} %>
  
  if( (form.obbligatoinSolido.value=="Altro" || form.obbligatoinSolido.value=="Nessuno") &&  (form.trasgressore.value=='Altro')){
		 message += label("check.sanzioni.richiedente.selezionato",msgAlmenoUno);
	    formTest = false;

		 }
		 
	if(form.obbligatoinSolido.value== form.trasgressore.value){
		 message += label("check.sanzioni.richiedente.selezionato",msgAlmenoUno);
	  formTest = false;

		 }
	
   if ((form.Provvedimenti.value=="-1")) {
      message += label("check.sanzioni.data_richiesta.selezionato","- Controllare che il campo \"Azione non Conforme per \" sia stato popolato\r\n");
      formTest = false;
    }
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
 		document.details.SanzioniAmministrative.selectedIndex=0;
 		document.details.SanzioniPenali.selectedIndex=0;
 		
  }
  
  function selectCarattere(str, n, m, x){
  
 		elm1 = document.getElementById("dat"+n);
 		elm2 = document.getElementById("dat"+m);
 		
 		
 		if(str == "Provvedimenti"){
 			car = document.details.Provvedimenti.value;
 		}
 		if(str == "SanzioniAmministrative"){
 			car = document.details.SanzioniAmministrative.value;
 		}
 		if(str == "SanzioniPenali"){
 			car = document.details.SanzioniPenali.value;
 		}
 		
 		if(car == 9 || (car == 6 && str == "SanzioniPenali")){
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

String permission = TicketDetails.getPermission_ticket()+"-sanzioni-edit";

%>
<dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
          <dhv:permission name="<%=permission%>">
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Sanzioni.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&stabId=<%=OrgDetails.getIdStabilimento()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Sanzioni.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
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
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Sanzioni.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
          <%= showAttribute(request, "closedError") %>
       </dhv:evaluate>
      </dhv:evaluate>
    <br />
    <dhv:formMessage />

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="sanzioni.information">Scheda Sanzione Amministrativa</dhv:label></strong>
      </th>
	</tr>
	<dhv:include name="" none="true">
	
  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.site">Site</dhv:label>
    </td>
    <td>
      <%= SiteIdList.getSelectedValue(TicketDetails.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=TicketDetails.getSiteId()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
 
</dhv:include>


<input type="hidden" name="altId" value="<%=TicketDetails.getAltId()%>">


	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Impresa</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="stabId" value="<%=OrgDetails.getIdStabilimento()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getIdAsl() %>" />
        
      </td>
    
  </tr>
 
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>
	<tr class="containerBody">
    <td valign="top" class="formLabel">
   Identificativo Non Conformità
    </td>
    <td>
     <%= TicketDetails.getIdentificativonc() %>
    </td>
  </tr>
	
	<tr class="containerBody" style="display: none">
      <td nowrap class="formLabel">
        <dhv:label name="sanzioni.data_richiestas">Data Accertamento</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="details" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
<tr class="containerBody">
  <td nowrap class="formLabel">
    
        Processo Verbale
       
    </td>
    <td>
        <input type="text" name="tipo_richiesta" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/><!--<font color="red">*</font> -->
       <input type="hidden" name="pippo" value="<%= toHtmlValue(TicketDetails.getPippo()) %>">
    </td>
</tr>

<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.importos">Effettuato Anche Sequestro Amministrativo ?</dhv:label>
    </td>
    <td>
    	SI <input  type = "radio" <% if(TicketDetails.getNumVerbaleSequestro()!= null &&  !"".equals(TicketDetails.getNumVerbaleSequestro())){ %> checked="checked"<%} %> value="si" name="effettuatoSeq"  id="effettuatoSeq_si" onclick="document.getElementById('numsequestro').style.display='';">NO <input  type = "radio" <% if(TicketDetails.getNumVerbaleSequestro()== null ||  "".equals(TicketDetails.getNumVerbaleSequestro())){ %> checked="checked"<%} %> value="no" name="effettuatoSeq" onclick="document.getElementById('numsequestro').style.display='none';"> 
    
    </td>
  </tr>
  
   <tr id = "numsequestro" class="containerBody" <%if(TicketDetails.getNumVerbaleSequestro()== null ||  "".equals(TicketDetails.getNumVerbaleSequestro())){ %>style="display:none"<%} %>>
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.importos">Numero Verbale di Sequestro amministrativo</dhv:label>
    </td>
    <td>
		<input type = "text" name = "verbalesequestro" value="<%=TicketDetails.getNumVerbaleSequestro() %>"  id = "verbalesequestro"   >
    </td>
  </tr>
  
  
  <tr>
  <td nowrap class="formLabel">
      <dhv:label name=""> Trasgressore <%=OrgDetailsSanzionata.getName()+"eeecc" %> </dhv:label>
    </td>
  <td>
   <select name="trasgressore" id="trasgressore" onchange="if(this.value=='Altro'){document.getElementById('trasgressorealtro').style.display=''; }else{document.getElementById('trasgressorealtro').style.display='none';}">
<% if (OrgDetailsSanzionata.getOrgId()>0) {%>
 <option value="<%=fixString(OrgDetailsSanzionata.getName()) %>" <%if (fixString(OrgDetailsSanzionata.getName()).equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ out.print("selected");} %>><%=fixString(OrgDetailsSanzionata.getName()) %></option>
 <option value="Altro" <%if (! fixString(OrgDetailsSanzionata.getName()).equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ out.print("selected");} %>>Altro</option>
  <%} else if (ncAltri.isFuoriRegioneImpresaSanzionata()) {%>
 <option value="<%=fixString("OPERATORE FUORI REGIONE") %>" <%if (fixString("OPERATORE FUORI REGIONE").equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ out.print("selected");} %>><%=fixString("OPERATORE FUORI REGIONE") %></option>
 <option value="Altro" <%if (! fixString("OPERATORE FUORI REGIONE").equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ out.print("selected");} %>>Altro</option>
  <%} else { %>
  <option value="<%=fixString(OrgDetails.getName()) %>" <%if (fixString(OrgDetails.getName()).equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ out.print("selected");} %>><%=fixString(OrgDetails.getName()) %></option>
  <option value="Altro" <%if (! fixString(OrgDetails.getName()).equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ out.print("selected");} %>>Altro</option>
   <%} %>
  
  
  </select>
  <font color="red">*</font>
    
     <% if (OrgDetailsSanzionata.getOrgId()>0) {%>  
   <input <%if (fixString(OrgDetailsSanzionata.getName()).equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ %>style="display:none" <%} %> type="text" id="trasgressorealtro" name="trasgressorealtro" value="<%=(! fixString(OrgDetailsSanzionata.getName()).equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))) ? fixString(TicketDetails.getTrasgressore()) :"" %>" size="20" maxlength="256"/>
     <%} else if (ncAltri.isFuoriRegioneImpresaSanzionata()) { %>  
<input <%if (fixString("OPERATORE FUORI REGIONE").equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ %>style="display:none" <%} %> type="text" id="trasgressorealtro" name="trasgressorealtro" value="<%=(! fixString("OPERATORE FUORI REGIONE").equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))) ? fixString(TicketDetails.getTrasgressore()) :"" %>" size="20" maxlength="256"/>
       <%} else { %>
   <input <%if (fixString(OrgDetails.getName()).equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ %>style="display:none" <%} %> type="text" id="trasgressorealtro" name="trasgressorealtro" value="<%=(! fixString(OrgDetails.getName()).equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))) ? fixString(TicketDetails.getTrasgressore()) :"" %>" size="20" maxlength="256"/>
   <%} %>

	
	
    </td>
  </tr>
  
  <tr>
  <td nowrap class="formLabel">
      <dhv:label name="">Obbligato in Solido</dhv:label>
    </td>
    
    
  <td >
  <table class="noborder"><tr>
  
  <td>
  
  <select name = "obbligatoinSolido" id ="obbligatoinSolido" onchange="if(this.value=='Altro'){document.getElementById('obbligatoinSolidoAltro').style.display=''; }else{document.getElementById('obbligatoinSolidoAltro').style.display='none';}">
  
   <% if (OrgDetailsSanzionata.getOrgId()>0) {%>

  <option value="Nessuno" <%=(TicketDetails.getObbligatoinSolido().equalsIgnoreCase("Nessuno")) ? "selected" : "" %>>Nessuno</option>
  <option value="<%=fixString(OrgDetailsSanzionata.getName()) %>" <%=(fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase(fixString(OrgDetailsSanzionata.getName()))) ? "selected" : "" %>><%=fixString(OrgDetailsSanzionata.getName()) %></option>
  <option value="Altro" <%=(!fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase(fixString(OrgDetailsSanzionata.getName())) && ! fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase("Nessuno")) ? "selected" : "" %> >Altro</option>
  
   <%} else if (ncAltri.isFuoriRegioneImpresaSanzionata()) {%>
 <option value="Nessuno" <%=(TicketDetails.getObbligatoinSolido().equalsIgnoreCase("Nessuno")) ? "selected" : "" %>>Nessuno</option>
  <option value="<%=fixString("OPERATORE FUORI REGIONE") %>" <%=(fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase(fixString("OPERATORE FUORI REGIONE"))) ? "selected" : "" %>><%=fixString("OPERATORE FUORI REGIONE") %></option>
  <option value="Altro" <%=(!fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase(fixString("OPERATORE FUORI REGIONE")) && ! fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase("Nessuno")) ? "selected" : "" %> >Altro</option>
  
   <%} else { %>

  <option value="Nessuno" <%=(TicketDetails.getObbligatoinSolido().equalsIgnoreCase("Nessuno")) ? "selected" : "" %>>Nessuno</option>
  <option value="<%=fixString(OrgDetails.getName()) %>" <%=(fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase(fixString(OrgDetails.getName()))) ? "selected" : "" %>><%=fixString(OrgDetails.getName()) %></option>
  <option value="Altro" <%=(!fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase(fixString(OrgDetails.getName())) && ! TicketDetails.getObbligatoinSolido().equalsIgnoreCase("Nessuno")) ? "selected" : "" %> >Altro</option>
    <%} %>
  </select>
  
    <% if (OrgDetailsSanzionata.getOrgId()>0) {%>
  <input <%if(fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase(fixString(OrgDetailsSanzionata.getName()))|| TicketDetails.getObbligatoinSolido().equalsIgnoreCase("Nessuno")){ %> style="display: none"<%} %> type="text" name="obbligatoinSolidoAltro"  id="obbligatoinSolidoAltro" value="<%=(!fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase(fixString(OrgDetailsSanzionata.getName())) && ! TicketDetails.getObbligatoinSolido().equalsIgnoreCase("Nessuno")) ? fixString(TicketDetails.getObbligatoinSolido()) : "" %>" size="20" maxlength="256"/>
    <%} else if (ncAltri.isFuoriRegioneImpresaSanzionata()) { %>  
      <input <%if(fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase(fixString("OPERATORE FUORI REGIONE"))|| TicketDetails.getObbligatoinSolido().equalsIgnoreCase("Nessuno")){ %> style="display: none"<%} %> type="text" name="obbligatoinSolidoAltro"  id="obbligatoinSolidoAltro" value="<%=(!fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase(fixString("OPERATORE FUORI REGIONE")) && ! TicketDetails.getObbligatoinSolido().equalsIgnoreCase("Nessuno")) ? fixString(TicketDetails.getObbligatoinSolido()) : "" %>" size="20" maxlength="256"/>
     <%} else { %>
  <input <%if(fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase(fixString(OrgDetails.getName()))|| TicketDetails.getObbligatoinSolido().equalsIgnoreCase("Nessuno")){ %> style="display: none"<%} %> type="text" name="obbligatoinSolidoAltro"  id="obbligatoinSolidoAltro" value="<%=(!fixString(TicketDetails.getObbligatoinSolido()).equalsIgnoreCase(fixString(OrgDetails.getName())) && ! TicketDetails.getObbligatoinSolido().equalsIgnoreCase("Nessuno")) ? fixString(TicketDetails.getObbligatoinSolido()) : "" %>" size="20" maxlength="256"/>
   <%} %>
  </td>
  
<!--    <td style="display:block" id="bottone1"> &nbsp; <input type="button" value="Aggiungi Altro" onclick="javascript:aggiungialtro(2);"></td> -->
  
  </tr></table>
 
    </td>
    
    
  </tr>
  
  
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
     Pagamento in misura Ridotta (Euro)
    </td>
    <td>
      <input type="text" name="pagamento" value="<%= TicketDetails.getPagamento() %>" size="20" maxlength="256" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/>
    </td>
  </tr>
  
  <tr class="containerBody">
      <td  id="provvedimento1" nowrap class="formLabel">
       Azione non Conforme per
      </td>
    <td>
    
      <table border=0 class="noborder">
      <tr>
      <td >
    
        	
        		<%= Provvedimenti.getHtmlSelect("Provvedimenti",TicketDetails.getProvvedimenti()) %>
        	
       
         
         <font color="red">*</font>
         <br><br>
       
    		</td>
    		   </tr>
       </table>
    </td>
  </tr>


<% if(TicketDetails.getNormaviolata()!=null && !TicketDetails.getNormaviolata().equals("")){
				//if(!TicketDetails.getNormaviolata().equals("")){ %>
				
	<tr class="containerBody">
  	<td nowrap class="formLabel">
      Norma Violata

   </td>
   <td>
      <%= TicketDetails.getNormaviolata() %>
      
   </td>
   </tr>
   <% } else { %>
	
	<tr class="containerBody"> 
    <td valign="top" class="formLabel">
     Norma Violata
    </td>
   <td>
   		  <%
       ListaNorme.setMultiple(true);
       ListaNorme.setSelectSize(9);
       
       %>
          <%
          LookupList selNorme = new LookupList();
          if(TicketDetails.getListaNorme().size()!=0) { 
       			%> 
				<%
						
						HashMap<Integer,String> listanorme =TicketDetails.getListaNorme();
						Set<Integer> setkiavi = listanorme.keySet();
						Iterator<Integer> iteraNorme=setkiavi.iterator();
						
						while(iteraNorme.hasNext()){
							int chiave = iteraNorme.next();
							String value=listanorme.get(chiave);					
						
							LookupElement elem = new LookupElement();
							elem.setCode(chiave);
							elem.setDescription(value);
							selNorme.add(elem);
							} %> 

			<% } %>
			
			<%=ListaNorme.getHtmlSelect("listaNorme",selNorme) %>
	</td>
	
	
	
   </tr>
	   
   <% } %>
   
   





<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
    </td>
    <td>
  </br>
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
	
	<dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
        <dhv:permission name="<%=permission%>">
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Sanzioni.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Sanzioni.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
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
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=OrgDetails.getAction() %>Sanzioni.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
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