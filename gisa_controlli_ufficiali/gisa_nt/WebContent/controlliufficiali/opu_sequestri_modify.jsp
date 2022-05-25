<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<script language="JavaScript">


function checkSeq(form) {
	  formTest = true;
	  message = "";

	if(form.tipo_richiesta.value==""){
		message += "- Controllare che il campo \"Numero Verbale\" sia stato popolato \n";
	    formTest = false;
		
	}
	if(document.getElementById("TipoSequestro").value == 2){

// 		if(document.getElementById('valutazione_rischio_gravi').value == ""){// se non ho slezionato niente 
// 			 message += " - Controllare che il campo \"Valutazione del rischio n.c. gravi\" sia stato popolato\r\n";
// 		     formTest = false;
// 		}
	}

	if (formTest == false) {
	    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	    return false;
	 } else {
	    return true;
	 }
}





function abilitaTipoAltro(){

	if(document.details.esitoSequestro.value==7){

	document.getElementById("esitoaltro").style.display="block";
		
	}
	else{
		document.getElementById("esitoaltro").style.display="none";
		
	}
	}


function abilitaTipoSequestro(){

	if(document.getElementById("TipoSequestro").value == 1){

		document.getElementById("rif_amm").style.display="block";
		document.getElementById("rif_san").style.display="none";
		document.getElementById("rif_pen").style.display="none";
		document.getElementById("seq").style.display="block";
//		document.getElementById("seq_a").style.display="block";
		if (document.getElementById("seq_sp")!=null)
			document.getElementById("seq_sp").style.display="none";
		document.getElementById("rif").style.display="none";
	//	document.getElementById("valutazione_rischio_gravi").disabled = true;
		document.getElementById("articolo").value = "4";
	
	}

	else if(document.getElementById("TipoSequestro").value == 2) {

		document.getElementById("rif_san").style.display="block";
		document.getElementById("rif_amm").style.display="none";
		document.getElementById("rif_pen").style.display="none";
		document.getElementById("seq").style.display="none";
	//	document.getElementById("seq_a").style.display="none";
		
	//	document.getElementById("valutazione_rischio_gravi").disabled = false;
		if (document.getElementById("seq_sp")!=null)
			document.getElementById("seq_sp").style.display="block";
		document.getElementById("rif").style.display="none";

		document.getElementById("articolo").value = "3";
		
	}

	else if(document.getElementById("TipoSequestro").value == 3) {

		document.getElementById("rif_pen").style.display="block";
		document.getElementById("rif_amm").style.display="none";
		document.getElementById("rif_san").style.display="none";
		document.getElementById("seq").style.display="none";
		//document.getElementById("seq_a").style.display="none";	
		if (document.getElementById("seq_sp")!=null)
			document.getElementById("seq_sp").style.display="block";
	//	document.getElementById("valutazione_rischio_gravi").disabled = true;
		document.getElementById("rif").style.display="none";
		document.getElementById("articolo").value = "1";
		
	}
	//Caso in cui è -1

	else{
		
		document.getElementById("rif_amm").style.display="none";
		document.getElementById("rif_san").style.display="none";
		document.getElementById("rif_pen").style.display="none";
		document.getElementById("rif").style.display="block";
	//	document.getElementById("seq_a").style.display="none";
	//	document.getElementById("valutazione_rischio_gravi").disabled = true;
		if (document.getElementById("seq_sp")!=null)
			document.getElementById("seq_sp").style.display="none";
		document.getElementById("articolo").value = "-1";
		
	}
}


function abilitaTipoSequestro1(){
	 
	  tipo = document.forms['details'].SequestroDi.value;

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


function abilitaTipoSequestro1_sp(){
	 
	  tipo = document.forms['details'].SequestroDi_sp.value;

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


function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
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
 		document.details.SequestriAmministrative.selectedIndex=0;
 		document.details.SequestriPenali.selectedIndex=0;
 		
  }
  
  function selectCarattere(str, n, m, x){
  
 		elm1 = document.getElementById("dat"+n);
 		elm2 = document.getElementById("dat"+m);
 		
 		
 		if(str == "Provvedimenti"){
 			car = document.details.Provvedimenti.value;
 		}
 		if(str == "SequestriAmministrative"){
 			car = document.details.SequestriAmministrative.value;
 		}
 		if(str == "SequestriPenali"){
 			car = document.details.SequestriPenali.value;
 		}
 		
 		if(car == 9 || (car == 6 && str == "SequestriPenali")){
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

String permission = TicketDetails.getPermission_ticket() + "-sequestri-edit";
String url =OrgDetails.getAction() ;

%>

<dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
          <dhv:permission name="<%=permission%>">
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='<%=url %>Sequestri.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=url %>Sequestri.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
          <dhv:permission name="<%=permission%>">
            <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkSeq(this.form)" />
          </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=url %>Sequestri.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
          <%= showAttribute(request, "closedError") %>
       </dhv:evaluate>
      </dhv:evaluate>
    <br />
    <dhv:formMessage />
   <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="sequestri.information">Scheda Sequestro/Blocco</dhv:label></strong>
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
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
   
  </dhv:evaluate>
</dhv:include>
	
	
	<tr class="containerBody">
  <td nowrap class="formLabel">
      Identificativo Non Conformita
       
        
    </td>
    <td>
        <%=TicketDetails.getIdentificativonc() %>
    </td>
</tr>
<tr class="containerBody" style="display: none">
      <td nowrap class="formLabel">
        <dhv:label name="sequestri.data_richiestas">Data Sequestro</dhv:label>
      </td>
      <td>
      

      	<input readonly type="text" id="assignedDate" name="assignedDate" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
 </tr>
	
	<dhv:evaluate if="<%= TipoSequestro.size() > 1 %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">Tipologia di Sequestro</td>
					<td>
					<% TipoSequestro.setJsEvent("onChange=abilitaTipoSequestro()");%>
      				<%= TipoSequestro.getHtmlSelect("TipoSequestro",TicketDetails.getTipologiaSequestro()) %>
        			<font color = "red">*</font>
					 <input type="hidden" name="TipoSequestro" value="<%=TicketDetails.getTipologiaSequestro()%>"></td>
				</tr>
	</dhv:evaluate>
	<dhv:evaluate if="<%= TipoSequestro.size() <= 1 %>">
			<input type="hidden" name="TipoSequestro" id="TipoSequestro" value="-1" />
	</dhv:evaluate>
	
	
<tr class="containerBody">
  <td nowrap class="formLabel">
      Numero Verbale     
    </td>
    <td>
        <input type="text" name="tipo_richiesta" id="tipo_richiesta" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/>
       <input type="hidden" name="pippo" value="<%= toHtmlValue(TicketDetails.getPippo()) %>">
    </td>
</tr>


<dhv:include name="ticket.location" none="true">
  <tr class="containerBody">
    <td valign="top" class="formLabel">
    Riferimento Normativo
    </td>
    <td>
      <table>
    <tr>
      <td id="rif" style="display: block">
      		<%if(TicketDetails.getCodiceArticolo() == 1){ //Penale
      		
      			out.print("Articolo 354 C.P.P");
      		}
      		/*else{
      			if(TicketDetails.getCodiceArticolo()==2){
      	      		out.print("Articolo 13 L. 689/81");
      	      		
          		}*/
          	else{
          		if(TicketDetails.getCodiceArticolo()==3){ //Sanitario
          	      		out.print("Articoli 18 e 54 Reg CE 882/04 e dell'Articolo 1 L.283/02");
              	}
          		else{
              		if(TicketDetails.getCodiceArticolo()==4){ //Amministrativo
              	      		out.print("Articolo 13 L. 689/81 e Articolo 54 Reg CE 882/04");
                  	}
          		}
          	}
      		//}
      		%>  		
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
    	<input type="hidden" name="articolo" id="articolo" value="<%=TicketDetails.getCodiceArticolo() %>">
      
      
 <%-- <td>Articolo 354 C.P.P<input type="radio" name="articolo" <%if(TicketDetails.getCodiceArticolo()==1) {%>checked="checked" <%} %> value="1"/>
     &nbsp 
     </td>
      <td> Art. 13 L. 689/81 e Articoli 18 e 54 Reg CE 882/04<input type="radio" name="articolo" <%if ((TicketDetails.getCodiceArticolo()==2) || (TicketDetails.getCodiceArticolo()==3) || (TicketDetails.getCodiceArticolo()==4)) {%>checked="checked" <%} %> value="4"/> 
      &nbsp
      </td>
   
	   <td> Articoli 18 e 54 Reg CE 882/04 <input type="radio" name="articolo" <%if(TicketDetails.getCodiceArticolo()==3) {%>checked="checked" <%} %> value="3"/>
	   </td>
	--%>
      </tr>
    </table>
    </td>
  </tr>
</dhv:include>

<dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="sequestriAmministrative1" id="sequestriAmministrative1" nowrap class="formLabel">
        <dhv:label name="">Sequestro di </dhv:label>
      </td>
    <td>
     <table border=0>
      <tr>
      
      <td style="border:0;border-style:none;display: <%= (TicketDetails.getTipologiaSequestro() == 1) ? "block" : "none"  %>" id="seq" >
      	<%
      		SequestroDi.setJsEvent("onChange=abilitaTipoSequestro1()"); %>
         <%= SequestroDi.getHtmlSelect("SequestroDi",TicketDetails.getSequestroDi()) %>
        <font color = "red">*</font>
      </td>
      
      <td style="border:0;border-style:none;display: <%= (TicketDetails.getTipologiaSequestro() != 1) ? "block" : "none"  %>"  id="seq_sp" >
      	<%
      		SequestroDi_sp.setJsEvent("onChange=abilitaTipoSequestro1_sp()"); %>
           <%= SequestroDi_sp.getHtmlSelect("SequestroDi_sp",TicketDetails.getSequestroDi()) %>
          <font color = "red">*</font>
        </td>
        
        
      <input type = "hidden" name = "id_nonconformita" value = "<%=TicketDetails.getId_nonconformita()%>"/>
      
      
      
<!--       <td style="border:0;border-style:none;display:none" id="seq_a"> -->
<%--       	<% --%>
<%--       		SequestroDi.setJsEvent("onChange=abilitaTipoSequestro1()"); %> --%>
<%--          <%= SequestroDi.getHtmlSelect("SequestroDi",TicketDetails.getSequestroDi()) %> --%>
<!--         <font color = "red">*</font> -->
<!--       </td> -->
      
<!--       <td style="border:0;border-style:none; display:none" id="seq_sp"> -->
<%--       	<% --%>
<%--       		SequestroDi_sp.setJsEvent("onChange=abilitaTipoSequestro1_sp()"); %> --%>
<%--            <%= SequestroDi_sp.getHtmlSelect("SequestroDi_sp",TicketDetails.getSequestroDi()) %> --%>
<!--           <font color = "red">*</font> -->
<!--         </td> -->
      
      
    <td id="quantita1" style="display:none;border:0"></br></br>
      <center>Quantità (espressa in Kg)</center></br>
    	<input type="text" name="quantita" value="<%=  TicketDetails.getQuantita() %>"  />
      </td>
    	<td " id="notesequestridi" style="visibility:hidden;border:0">
    	<center> Descrizione</center> </br>
    	<textarea rows="8" cols="50" name="notesequestridi" value="<%=TicketDetails.getNoteSequestrodi() %>"><%=TicketDetails.getNoteSequestrodi() %></textarea>
   	</td>
  </tr>
  </table>
    </td>
  </tr>
</dhv:include>

  <tr class="containerBody">
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

<!-- 	<tr class="containerBody">  -->
<!--     <td valign="top" class="formLabel"> -->
<!--       <dhv:label name="">Valutazione del rischio n.c. gravi</dhv:label> -->
<!--     </td> -->
<!--     <td> -->
<!--       <table border="0" cellspacing="0" cellpadding="0" class="empty"> -->
<!--         <tr> -->
<!--           <td> -->
<%--  			<textarea  id="valutazione_rischio_gravi" name="nonConformitaGraviValutazione" cols="55" rows="6" onclick="if (this.value =='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' GRAVI RISCONTRATE'){this.value=''}"><%= (TicketDetails.getValutazione() != null && !TicketDetails.getValutazione().trim().equals("")) ? toString(TicketDetails.getValutazione()) : "" %></textarea><font color="red">*</font> --%>
<!--           </td> -->
<!--         </tr> -->
<!--       </table> -->
<!--     </td> -->
<!-- 	</tr> -->

	</table>
	
	
	
<br>

<table cellpadding="4" cellspacing="0" width="100%" class="details" style="display:none">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Follow Up del Sequestro/Blocco</dhv:label></strong>
    </th>
	
	
  
  <tr class="containerBody">
    <td class="formLabel" class="containerBody">
      <dhv:label name="sanzionia.data_ispezione">Data</dhv:label>
    </td>
    <td>
    
    
      	<input readonly type="text" id="estimatedResolutionDate" name="estimatedResolutionDate" size="10" value = "<%=toDateString(TicketDetails.getEstimatedResolutionDate()) %>" />
		<a href="#" onClick="cal19.select(document.forms[0].estimatedResolutionDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      
     <%= showAttribute(request, "estimatedResolutionDateError") %>
    </td>
  </tr>
  
   <dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td id="esitoTampone1" nowrap class="formLabel">
        <dhv:label name="">Esito</dhv:label>
      </td>
    <td>
    
    
    <table class="noborder">
    <tr class="containerBody">
    <td>
     <%
      EsitiSequestri.setJsEvent("onChange=abilitaTipoAltro()"); %>
         <%= EsitiSequestri.getHtmlSelect("esitoSequestro",TicketDetails.getEsitoSequestro()) %>
    </td>
    
    <td style="display: none" id="esitoaltro">
    Descrizione <br>
    <input type="text" name="descrizione" value="<%=TicketDetails.getDescrizione() %>">
    
    </td>
    
    </tr>
    </table>
    </td>
  </tr>
</dhv:include>
  
  
  <tr class="containerBody">
    <td valign="top" class="formLabel" class="containerBody">
      <dhv:label name="sequestri.azioni">Ulteriori Note</dhv:label>
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
        &nbsp;<br>
        
         <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
        <dhv:permission name="<%=permission%>">
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='<%=url %>Sequestri.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=url %>Sequestri.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
        <dhv:permission name="<%=permission%>">
          <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkSeq(this.form)" />
        </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='<%=url %>Sequestri.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
        <%= showAttribute(request, "closedError") %>
     </dhv:evaluate>
    </dhv:evaluate>
    <input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
    <input type="hidden" name="stabId" value="<%=TicketDetails.getIdStabilimento()%>">
   <input type="hidden" name="altId" value="<%=TicketDetails.getAltId()%>">
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getSiteId() %>" />
    <input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
    <input type="hidden" name="companyName" value="<%= toHtml(TicketDetails.getCompanyName()) %>">
    <input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
    <input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
    <input type="hidden" name="close" value="">
    <input type="hidden" name="refresh" value="-1">
    <input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />