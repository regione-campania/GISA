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
<font color = "red">Sequestro Inserito in maniera corretta.</font>
<a href = "#" >Torna Indietro</a>

<%	
}
%>

<script>
focus_=true ;
 </script>
  <body  onblur="if(focus_==true){window.focus();}" onmouseout="focus_=true;" onmouseover="focus_=false;"  onunload="abilitaAll()" onload = "abilitaStatiNc('<%= request.getAttribute("inserito")%>','<%=request.getAttribute("TipoNC") %>','<%= request.getAttribute("idC")%>','<%=request.getAttribute("followup_formali_inseriti") %>','<%=request.getAttribute("followup_significativi_inseriti") %>','<%=request.getAttribute("attivita_gravi_inseriti") %>');disabilitaAll();abilitaTipoSequestro();">


<form name="addticket" action="SequestriNC.do?command=Insert&auto-populate=true" method="post">
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
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.sequestri.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="SequestroDi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoSequestro" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDi_sp" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="AzioneNonConforme" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitiSequestri" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>

<jsp:useBean id="SequestroDiStabilimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDiLocali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDiAttrezzature" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDiAlimentiorigineAnimale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDiAlimentiorigineVegetale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDiAnimali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDiLocalieAttrezzature" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroDivegetaleEanimale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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




function abilitaTipoSequestro(){

	//Amministrativo
if(document.addticket.TipoSequestro.value == 1){

	document.getElementById("rif_amm").style.display="block";
	document.getElementById("rif_san").style.display="none";
	document.getElementById("rif_pen").style.display="none";
	document.getElementById("seq_a").style.display="block";
	document.getElementById("seq_sp").style.display="none";
	document.getElementById("seq").style.display="none";
	document.getElementById("rif").style.display="none";
	//document.getElementById("valutazione_rischio_gravi").disabled = true;
	document.getElementById("articolo").value = "4";
}

//Sanitario	
else if(document.addticket.TipoSequestro.value == 2) {

	document.getElementById("rif_san").style.display="block";
	document.getElementById("rif_amm").style.display="none";
	document.getElementById("rif_pen").style.display="none";
	document.getElementById("seq_a").style.display="none";
	document.getElementById("seq_sp").style.display="block";
	//document.getElementById("valutazione_rischio_gravi").disabled = false;
	document.getElementById("seq").style.display="none";
	document.getElementById("rif").style.display="none";

	document.getElementById("articolo").value = "3";
	
}

//Penale
else if(document.addticket.TipoSequestro.value == 3) {

	document.getElementById("rif_pen").style.display="block";
	document.getElementById("rif_amm").style.display="none";
	document.getElementById("rif_san").style.display="none";
	document.getElementById("seq_a").style.display="none";
	document.getElementById("seq_sp").style.display="block";
	//document.getElementById("valutazione_rischio_gravi").disabled = true;
	document.getElementById("seq").style.display="none";
	document.getElementById("rif").style.display="none";

	document.getElementById("articolo").value = "1";
	
}
//Caso in cui è -1

else{
	
	document.getElementById("rif_amm").style.display="none";
	document.getElementById("rif_san").style.display="none";
	document.getElementById("rif_pen").style.display="none";
	document.getElementById("rif").style.display="block";
	document.getElementById("seq_a").style.display="none";
	//document.getElementById("valutazione_rischio_gravi").disabled = true;
	document.getElementById("seq_sp").style.display="none";
	document.getElementById("seq").style.display="block";

	document.getElementById("articolo").value = "-1";
	
}

}

function abilitaTipoSequestro1_sp(){
	 
	  tipo = document.forms['addticket'].SequestroDi_sp.value;

if(tipo=="-1"){
		 document.getElementById("notesequestridi").style.visibility="hidden";
		 }else{
			document.getElementById("notesequestridi").style.visibility="visible";
	 		 }
if(tipo=="4" || tipo=="5" || tipo=="6"){
		document.getElementById("quantita1").style.display="block";
	}else{
		document.getElementById("quantita1").style.display="none";
	}
}



function abilitaTipoAltro(){

	if(document.addticket.esitoSequestro.value==7){

		document.getElementById("esitoaltro").style.display="block";
	
	}
	
	else	{
		document.getElementById("esitoaltro").style.display="none";
	
	}
}

  function checkForm(form){
    formTest = true;
    message = "";
//     if (form.assignedDate.value == "") {
//         message += label("check.sequestri.data_richiesta.selezionato","- Controllare che il campo \"Data Sequestro\" sia stato popolato\r\n");
//         formTest = false;
//       }
	if(form.tipo_richiesta.value==""){
		message += "- Controllare che il campo \"Numero Verbale\" sia stato popolato \n";
	    formTest = false;
		
	}

	
	//R.M: per la nuova gestione dei Sequestri non è prevista più la gestione dell'articolo 
	//di legge.
	 /*  var art1=document.getElementById("articolo1");
	   var art2=document.getElementById("articolo2");
	   var art3=document.getElementById("articolo3");
	   var art4=document.getElementById("articolo4");
	 
	  if (art1.checked==false && art4.checked==false) {
	     message += "- Controllare di aver selezionato un Articolo Legge \n";
	    formTest = false;
	   }
	  */

	  //Se è stato selezionato un sequestro amministrativo
	  if(document.addticket.TipoSequestro.value == 1){
		  
	  		if(document.forms['addticket'].SequestroDi.value=="-1"){
				message += "- Controllare di aver selezionato un tipo di sequestro \n";
		    	formTest = false;
			}
	  }
	  else {

		  if(document.forms['addticket'].SequestroDi_sp.value=="-1"){
				message += "- Controllare di aver selezionato un tipo di sequestro \n";
		    	formTest = false;
			}	  
	  }

	//Se si seleziona il sequestro sanitario, visualizzare la valutazione del rischio
// 	if(document.addticket.TipoSequestro.value == 2){

// 		if(form.nonConformitaGraviValutazione.value == "" || trim(document.getElementById('valutazione_rischio_gravi').value) == 'INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' GRAVI RISCONTRATE'){// se non ho slezionato niente 
// 			 message += label("check.followup.richiedente.selezionato","- Controllare che il campo \"Valutazione del rischio n.c. gravi\" sia stato popolato\r\n");
// 		      formTest = false;
// 		}
//   	}
    
   
    <dhv:include name="ticket.contact" none="true">

    if (form.siteId.value == "-1") {
      message += label("check.sequestri.richiedente.selezionato","- Controllare che il campo \"A.S.L.\" sia stato popolato\r\n");
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

 function abilitaTipoSequestro1(){
	  tipo = document.forms['addticket'].SequestroDi.value;
   if(tipo=="-1"){
 		 document.getElementById("notesequestridi").style.visibility="hidden";
 		 }else{
 			document.getElementById("notesequestridi").style.visibility="visible";
 	 		 }
	if(tipo=="4" || tipo=="5" || tipo=="6"){
		document.getElementById("quantita1").style.display="block";
	}else{
		document.getElementById("quantita1").style.display="none";
		}
 	
 }
 
</script>
<input type="hidden" name="idC" value="<%= request.getAttribute("idC")%>" >
  <input type="hidden" name="idNC" value="<%= request.getAttribute("idNC")%>" >
  
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Sequestro/Blocco</dhv:label></strong>
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
 
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="idStabilimento" value="<%=OrgDetails.getIdStabilimento()%>">
        <input type="hidden" name="idApiario" value="<%=OrgDetails.getIdApiario()%>">
        <input type="hidden" id="altId" name="altId" value="<%=OrgDetails.getAltId()%>"/>
        
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
    
  
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
  <% } %>
  

    <% String dataC = request.getAttribute("dataC").toString(); %>
  
   <tr class="containerBody" style="display: none">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Sequestro</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="assignedDate" timestamp="<%= dataC %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
      <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>
  <tr>
  <td nowrap class="formLabel">
      <dhv:label name=""> Numero Verbale Sequestro</dhv:label>
    </td>
  <td >
      
	  <input type="text" name="tipo_richiesta" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/><font color="red">*</font>
      
    </td>
 </tr>
 
 <tr>
  <td nowrap class="formLabel">
      <dhv:label name=""> Tipologia di Sequestro</dhv:label>
  </td>
  <td>
  <% TipoSequestro.setJsEvent("onChange=abilitaTipoSequestro()");%>
	<%= TipoSequestro.getHtmlSelect("TipoSequestro",TicketDetails.getTipologiaSequestro()) %>
 </td>		    
 </tr>

<dhv:include name="ticket.location" none="true">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
    Riferimento Normativo
    </td>
    <td id="rif" style="display: block">
   		--Selezionare una Tipologia di Sequestro--
   	</td>
    <td id="rif_amm" style="display: none">
   	   Art. 13 L.689/81 e dell' Art.54 Reg CE 882/04
   	</td>
    <td id="rif_san" style="display: none">
       Artt. 18 e 54 Reg CE 882/04 e dell' Art.1 L 283/62
    </td>
    <td id="rif_pen" style="display: none">
      	Art.354 C.P.P.
    </td> 
    <input type="hidden" name="articolo" id="articolo" value="">
 </tr>
 </dhv:include>
  <tr>
      <td  class="formLabel">
      Sequestro di
      </td>
    <td>
    <table class="noborder" cellpadding="10">
      <tr>
      <td id="seq_a" style="display: none">
      <%
      SequestroDi.setSelectSize(5);
      SequestroDi.setJsEvent("onChange=abilitaTipoSequestro1()"); %>
         <%= SequestroDi.getHtmlSelect("SequestroDi",TicketDetails.getSequestriAmministrative()) %>
         
         <font color = "red">*</font>
      </td>
       <td id="seq_sp" style="display:none">
      <%
      SequestroDi_sp.setSelectSize(5);
      SequestroDi_sp.setJsEvent("onChange=abilitaTipoSequestro1_sp()"); %>
      <%= SequestroDi_sp.getHtmlSelect("SequestroDi_sp",TicketDetails.getSequestriAmministrative())%>
      <font color = "red">*</font>
      </td> 
      <td  id="seq" style="display:block">
      	--Selezionare una Tipologia di Sequestro--
      </td>     
      <td id="quantita1" style="display:none;border:0;border-bottom:0"></br></br></br>
      <center>Quantità (espressa in Kg)</center></br>
    	<input type="text" name="quantita" value="<%=  TicketDetails.getQuantita() %>"  />
      </td>
    	<td  id="notesequestridi" style="visibility:hidden;border:0">
    	<center> Descrizione: </center></br>
    	<textarea rows="8" cols="50" name="notesequestridi" ></textarea>
    	</td>  
       </tr>
       </table>
      
    </td>
  </tr>

	<tr>
    <td valign="top" class="formLabel">
      <dhv:label name="sequestri.note">Note</dhv:label>
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
	
	
	
<!--  <tr> -->
<!--     <td valign="top" class="formLabel"> -->
<%--       <dhv:label name="">Valutazione del rischio n.c. gravi</dhv:label> --%>
<!--     </td> -->
<!--     <td> -->
<!--       <table border="0" cellspacing="0" cellpadding="0" class="empty"> -->
<!--         <tr> -->
<!--           <td> -->
<!--  			<textarea id = "valutazione_rischio_gravi" name="nonConformitaGraviValutazione" cols="55" rows="6" onclick="if (this.value =='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' GRAVI RISCONTRATE'){this.value=''}">INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA' GRAVI RISCONTRATE</textarea> -->
<!--             <font color="red">*</font>            -->
<!--            </td> -->
<!--         </tr> -->
<!--       </table> -->
<!--     </td> -->
<!-- 	</tr> -->
</table>

<br>

<table cellpadding="4" cellspacing="0" width="100%" class="details" style="display: none">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Follow Up del Sequestro/Blocco</dhv:label></strong>
    </th>
	</tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzionia.data_ispezione">Data Esito</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"  showTimeZone="false" />
      <%= showAttribute(request, "estimatedResolutionDateError") %>
    </td>
  </tr>
  
   <dhv:include name="organization.source" none="true">
   <tr>
      <td name="esitoTampone1" id="esitoTampone1" nowrap class="formLabel">
        <dhv:label name="">Esito</dhv:label>
      </td>
    <td>
    
    
    <table class="noborder">
    <tr>
    <td>
     <%
     EsitiSequestri.setSelectSize(5);
      EsitiSequestri.setJsEvent("onChange=abilitaTipoAltro()"); %>
         <%= EsitiSequestri.getHtmlSelect("esitoSequestro",TicketDetails.getEsitoSequestro()) %>
    </td>
    
    <td style="display: none" id="esitoaltro">
    Descrizione: <br>
    <input type="text" name="descrizione">
    
    </td>
    
    </tr>
    </table>
    </td>
  </tr>
</dhv:include>

<tr>
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Ulteriori Note</dhv:label>
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
 </table>
    </td>
</tr>
     
    </table>
   
<input type = "hidden" id = "followup_gravi_inseriti" name = "followup_gravi_inseriti">
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla"  onClick="window.close()">
</form>
</body>