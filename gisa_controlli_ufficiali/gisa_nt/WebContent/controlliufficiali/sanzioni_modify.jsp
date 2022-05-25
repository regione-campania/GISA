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
<jsp:useBean id="OperatoreMercatoDetailsSanzionato" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="ncAltri" class="org.aspcfs.modules.altriprovvedimenti.base.Ticket" scope="request"/>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

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

function verificaEsistenzaDiffidaCallback(val)
{
	  if (val==true)
		  {
		  formTest = false;
		  message += label("check.sanzioni.listaNorme.selezionato","- L'operatore risulta già diffidato per questa norma. \r\n");
		  }
	  
}

var item ;
function checkDiffida()
	{
	  form= document.forms[0];
	  arr=form.listaNorme;
	  for(k=0 ; k<arr.length;k++){
			if(arr[k].value=="-1" && arr[k].selected==true){
				 	
			      	formTest = false;

				}
			else
				{
				if( arr[k].selected==true){
					
				item = arr[k];
				PopolaCombo.verificaNormaInDiffidaOSA(arr[k].value, form.org_id.value,form.idStabilimento.value,form.idApiario.value,-1,{callback:verificaEsistenzaDiffidaCallback,async:false});
				
				
				}
				}
				}
			
		
		
	}
	

	function verificaEsistenzaDiffidaCallback(val)
	{
	  if (val==true)
		  {

		  //message += label("check.sanzioni.listaNorme.selezionato","- Norma presente in diffida per questo controllo \r\n");
		  if(confirm("Attenzione! L'operatore risulta già diffidato per questa norma.")){
			formTest = true;	  
		  }
		  
		  }
	  
	}  



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

function gestisciEffettuatoSequestro(){
	 var sequestro = document.getElementById("effettuatoSeq_si");
	 var numsequestro = document.getElementById('numsequestro');
	 var verbalesequestro = document.getElementById('verbalesequestro');
	 var ridsequestro = document.getElementById('ridsequestro');
	 var riduzionesequestro = document.getElementById('seqRiduzioneApplicata');
	 var riduzioneseq = document.getElementById('riduzioneSeq_si'); 
	 
	 if (sequestro.checked){
		numsequestro.style.display = '';
		ridsequestro.style.display = 'none';
		riduzionesequestro.value='';
	 }
	 else{
		 numsequestro.style.display = 'none';
			verbalesequestro.value='';
			ridsequestro.style.display = '';
	 }
	 
	 if (riduzioneseq.checked){
		 riduzionesequestro.style.display = '';
		 }
		 else{
			 riduzionesequestro.style.display = 'none';
			 riduzionesequestro.value='';
		 }
	 
	 
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

function checkForm(form){
  formTest = true;
  message = "";

arr=form.Provvedimenti;

  
/* if (form.assignedDate.value == "") {
      message += label("check.sanzioni.data_richiesta.selezionato","- Controllare che il campo \"Data Accertamento\" sia stato popolato\r\n");
      formTest = false;
    } */
 /* if(form.tipo_richiesta.value==""){
 	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Processo Verbale\" sia stato popolato \r\n");
      formTest = false;

 	 }*/

if(form.trasgressore.value=="Altro" && form.trasgressorealtro.value==''){
	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Trasgressore\" sia stato popolato\r\n");
    formTest = false;

	 }
   	 
if(form.obbligatoinSolido.value=="Altro" && form.obbligatoinSolidoAltro.value==''){
	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Obbligato in Solido\" sia stato popolato\r\n");
   formTest = false;

	 }


// if(form.obbligatoinSolido2.value=="Altro" && form.obbligatoinSolidoAltr2o.value==''){
// 	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Obbligato in Solido2\" sia stato popolato\r\n");
//    formTest = false;

// 	 }
	 

// if(form.obbligatoinSolido3.value=="Altro" && form.obbligatoinSolidoAltro3.value==''){
// 	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Obbligato in Solido3\" sia stato popolato\r\n");
//    formTest = false;

// 	 }


var msgAlmenoUno = "- Controllare che almeno uno tra i campi Trasgressore e obbligato in Solido coincida con la ragione sociale del soggetto ispezionato\r\n";
<% if (OrgDetailsSanzionata.getOrgId()>0 || OperatoreMercatoDetailsSanzionato.getIdStabilimento()>0) {%>
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
  	 
   	 
for(k=0 ; k<arr.length;k++){
		if(arr[k].value=="-1" && arr[k].selected==true){
			message += label("check.sanzioni.richiedente.selezionato","- Controllare di aver selezionato una azione \r\n");
		      formTest = false;

			}

   }

if(form.tipo_richiesta.value==""){
	 message += label("check.sanzioni.tipo_richiesta.selezionato","- Controllare che il campo \"Numero processo verbale\" sia stato popolato\r\n");
   formTest = false;

}

if(form.pagamento.value==""){
	 message += label("check.sanzioni.richiedente.selezionato","- Controllare di aver inserito un importo per pagamento in misura ridotta\r\n");
   formTest = false;

	 }


if(form.Provvedimenti.value==""){
	 message += label("check.sanzioni.data_richiesta.selezionato","- Controllare che il campo \"Azione non conforme per\" sia stato popolato\r\n");
   formTest = false;

}

if(form.listaNorme.value==-1){
	 message += label("check.sanzioni.listaNorme.selezionato","- Controllare che il campo \"Norma violata\" sia stato popolato\r\n");
  formTest = false;

}

if(form.effettuatoSeq_si.checked){
	if (form.verbalesequestro.value==''){
		 message += label("check.sanzioni.importos","- Controllare che il campo \"Numero verbale di sequestro amministrativo\" sia stato popolato\r\n");
	    formTest = false;
		}

}

if(form.effettuatoSeq_no.checked && form.riduzioneSeq_si.checked){
	if (form.seqRiduzioneApplicata.value==''){
	 message += label("check.sanzioni.importos","- Controllare che il campo \"Riduzione del 30%\" sia stato popolato\r\n");
    formTest = false;
	}
	else if ( !isNaN(form.seqRiduzioneApplicata.value) && !isNaN(form.pagamento.value) &&  parseInt(form.seqRiduzioneApplicata.value,10) >= parseInt(form.pagamento.value, 10)){
		 message += label("check.sanzioni.importos","- Controllare che il campo \"Riduzione del 30%\" sia coerente col campo \"Pagamento in Misura Ridotta\" \r\n");
	    formTest = false;
		}
}
  
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
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>Sanzioni.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>Sanzioni.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
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
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>Sanzioni.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
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
    ASL di competenza
    </td>
    <td>
      <%= SiteIdList.getSelectedValue(TicketDetails.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=TicketDetails.getSiteId()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
 
</dhv:include>
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Impresa</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
        <input type="hidden" name="idStabilimento" value="<%=OrgDetails.getIdStabilimento()%>">
        <input type="hidden" name="idApiario" value="<%=OrgDetails.getIdApiario()%>">
      </td>
    
  </tr>
 
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="idStabilimento" value="<%=OrgDetails.getIdStabilimento()%>">
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
        <input type="text" name="tipo_richiesta" id="tipo_richiesta" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/><!--<font color="red">*</font> -->
       <input type="hidden" name="pippo" value="<%= toHtmlValue(TicketDetails.getPippo()) %>">
    </td>
</tr>

  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.importos">Effettuato Anche Sequestro Amministrativo ?</dhv:label>
    </td>
    <td>
    	SI <input  type = "radio" <% if(TicketDetails.getNumVerbaleSequestro()!= null &&  !"".equals(TicketDetails.getNumVerbaleSequestro())){ %> checked="checked"<%} %> value="si" name="effettuatoSeq"  id="effettuatoSeq_si" onclick="gestisciEffettuatoSequestro()">
    	NO  <input  type = "radio" <% if(TicketDetails.getNumVerbaleSequestro()== null ||  "".equals(TicketDetails.getNumVerbaleSequestro())){ %> checked="checked"<%} %> value="no" name="effettuatoSeq"  id="effettuatoSeq_no" onclick="gestisciEffettuatoSequestro()"> 
    
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
  
  <tr id = "ridsequestro" class="containerBody" <%if(TicketDetails.getNumVerbaleSequestro()!= null &&  !"".equals(TicketDetails.getNumVerbaleSequestro())){ %>style="display:none"<%} %>>
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.importos">Applicabile riduzione del 30%?</dhv:label>
    </td>
    <td>
		SI <input  type = "radio" <%=(TicketDetails.getSequestroRiduzione()>0) ? "checked='checked'" : "" %> value="si" name="riduzioneSeq" id="riduzioneSeq_si" onclick="gestisciEffettuatoSequestro()">		
    	NO <input  type = "radio" <%=(TicketDetails.getSequestroRiduzione()==0) ? "checked='checked'" : "" %>  value="no" name="riduzioneSeq" onclick="gestisciEffettuatoSequestro()"> <br/>
    	<input type = "text" name = "seqRiduzioneApplicata"  id = "seqRiduzioneApplicata" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" <%=(TicketDetails.getSequestroRiduzione()>0) ? "" : "style='display:none'" %> value="<%=(TicketDetails.getSequestroRiduzione()>0) ? TicketDetails.getSequestroRiduzione() : ""%>">
     </td>
  </tr>
  
   <tr>
  <td nowrap class="formLabel">
      <dhv:label name=""> Trasgressore</dhv:label>
    </td>
  <td>
  
  <select name="trasgressore" id="trasgressore" onchange="if(this.value=='Altro'){document.getElementById('trasgressorealtro').style.display=''; }else{document.getElementById('trasgressorealtro').style.display='none';}">
<% if (OrgDetailsSanzionata.getOrgId()>0) {%>
 <option value="<%=fixString(OrgDetailsSanzionata.getName()) %>" <%if (fixString(OrgDetailsSanzionata.getName()).equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ out.print("selected");} %>><%=fixString(OrgDetailsSanzionata.getName()) %></option>
 <option value="Altro" <%if (! fixString(OrgDetailsSanzionata.getName()).equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ out.print("selected");} %>>Altro</option>
 <%} else if (OperatoreMercatoDetailsSanzionato.getIdStabilimento()>0) { %>
 <option value="<%=fixString(OperatoreMercatoDetailsSanzionato.getOperatore().getRagioneSociale()) %>" <%if (fixString(OperatoreMercatoDetailsSanzionato.getOperatore().getRagioneSociale()).equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ out.print("selected");} %>><%=fixString(OperatoreMercatoDetailsSanzionato.getOperatore().getRagioneSociale()) %></option>
 <option value="Altro" <%if (! fixString(OperatoreMercatoDetailsSanzionato.getOperatore().getRagioneSociale()).equalsIgnoreCase(fixString(TicketDetails.getTrasgressore()))){ out.print("selected");} %>>Altro</option>
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
  
<!--   <tr  style="display:none" id="obbligato2"> -->
<!--   <td nowrap class="formLabel"> -->
<!--       <dhv:label name="">Obbligato in Solido 2</dhv:label> -->
<!--     </td> -->
<!--   <td > -->
<!--   <table class="noborder"> -->
<!--   <tr> -->
<!--   <td> -->
  
  
<!--   <select name = "obbligatoinSolido2"  onchange="if(this.value=='Altro'){document.getElementById('obbligatoinSolidoAltro2').style.display=''; }else{document.getElementById('obbligatoinSolidoAltro2').style.display='none';}"> -->
  
<%--   <option value="Nessuno" <%=(TicketDetails.getObbligatoinSolido2().equalsIgnoreCase("Nessuno")) ? "selected" : "" %>>Nessuno</option> --%>
<%--   <option value="<%=OrgDetails.getName() %>" <%=(TicketDetails.getObbligatoinSolido2().equalsIgnoreCase(OrgDetails.getName())) ? "selected" : "" %>><%=OrgDetails.getName() %></option> --%>
<%--    <option value="Altro" <%=(!TicketDetails.getObbligatoinSolido2().equalsIgnoreCase(OrgDetails.getName()) && ! TicketDetails.getObbligatoinSolido2().equalsIgnoreCase("Nessuno")) ? "selected" : "" %> >Altro</option> --%>
<!--   </select> -->
  
<%--   <input <%if(TicketDetails.getObbligatoinSolido2().equalsIgnoreCase(OrgDetails.getName()) || TicketDetails.getObbligatoinSolido2().equalsIgnoreCase("Nessuno")){ %> style="display: none"<%} %> type="text" name="obbligatoinSolidoAltro2"  id="obbligatoinSolidoAltro2" value="<%=(!TicketDetails.getObbligatoinSolido2().equalsIgnoreCase(OrgDetails.getName()) && ! TicketDetails.getObbligatoinSolido2().equalsIgnoreCase("Nessuno")) ? TicketDetails.getObbligatoinSolido2() : "" %>" size="20" maxlength="256"/> --%>

  
<!--   </td> -->
<!--   <td id = "bottone2" style="display:none" > &nbsp; <input type="button" value="Aggiungi Altro" onclick="javascript:aggiungialtro(3);"></td> -->
  
<!--   </tr> -->
  
  
<!--   </table> -->
      
<!--     </td> -->
     
<!--   </tr> -->
  
<!--   <tr style="display: none" id = "obbligato3"> -->
<!--   <td nowrap class="formLabel" > -->
<!--       <dhv:label name="">Obbligato in Solido 3</dhv:label> -->
<!--     </td> -->
<!--   <td > -->
<!--   <table class="noborder"> -->
  
<!--   <tr> -->
<!--   <td> -->
<!--  <select name = "obbligatoinSolido3"  onchange="if(this.value=='Altro'){document.getElementById('obbligatoinSolidoAltro3').style.display=''; }else{document.getElementById('obbligatoinSolidoAltro3').style.display='none';}"> -->
  
<%--   <option value="Nessuno" <%=(TicketDetails.getObbligatoinSolido3().equalsIgnoreCase("Nessuno")) ? "selected" : "" %>>Nessuno</option> --%>
<%--   <option value="<%=OrgDetails.getName() %>" <%=(TicketDetails.getObbligatoinSolido3().equalsIgnoreCase(OrgDetails.getName())) ? "selected" : "" %>><%=OrgDetails.getName() %></option> --%>
<%--    <option value="Altro" <%=(!TicketDetails.getObbligatoinSolido3().equalsIgnoreCase(OrgDetails.getName()) && ! TicketDetails.getObbligatoinSolido3().equalsIgnoreCase("Nessuno")) ? "selected" : "" %> >Altro</option> --%>
<!--   </select> -->
  
<%--   <input <%if(TicketDetails.getObbligatoinSolido3().equalsIgnoreCase(OrgDetails.getName())||TicketDetails.getObbligatoinSolido3().equalsIgnoreCase("Nessuno")){ %> style="display: none"<%} %> type="text" name="obbligatoinSolidoAltro3"  id="obbligatoinSolidoAltro3" value="<%=(!TicketDetails.getObbligatoinSolido3().equalsIgnoreCase(OrgDetails.getName()) && ! TicketDetails.getObbligatoinSolido3().equalsIgnoreCase("Nessuno")) ? TicketDetails.getObbligatoinSolido3() : "" %>" size="20" maxlength="256"/> --%>

<!--   </td> -->
<!-- <td>&nbsp;</td>   -->
<!--   </tr> -->
<!--   </table> -->
      
<!--     </td> -->
<!--   </tr> -->
  
  
  
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
     Pagamento in misura Ridotta (Euro)
    </td>
    <td>
      <input type="text" name="pagamento" id ="pagamento" value="<%= TicketDetails.getPagamento() %>" size="20" maxlength="256" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/>
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


<%
if (OrgDetailsSanzionata.getOrgId()<=0){
	ListaNorme.setJsEvent("onchange='checkDiffida()'");
}
%>

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
            <textarea name="problem" id="problem" cols="55" rows="8"><%= toString(TicketDetails.getProblem()) %></textarea>
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
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>Sanzioni.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>Sanzioni.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
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
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=TicketDetails.getURlDettaglio() %>Sanzioni.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
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
    <input type="hidden" id="tipoNC" name="tipoNC" value="<%= (OrgDetailsSanzionata.getOrgId()>0) ? "10" : "8" %>"/>