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
  - Version: $Id: mycfs_newmessage.jsp 15627 2006-08-08 19:08:07Z matt $
  - Description:
  --%>
<%-- Trails --%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popCalendar.js"></script>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<a href="MyCFSInbox.do?command=Inbox&return=1"><dhv:label name="myitems.mailbox">Mailbox</dhv:label></a> >
<dhv:label name="actionList.newMessage">New Message</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<script type="text/javascript">
  function hideSendButton() {
    try {
      var send1 = document.getElementById('send1');
      send1.value = label('label.sending','Sending...');
      send1.disabled=true;
    } catch (oException) {}
    try {
      var send2 = document.getElementById('send2');
      send2.value = label('label.sending','Sending...');
      send2.disabled=true;
    } catch (oException) {}
  }
</script>
<form name="newMessageForm" action="MyCFSInbox.do?command=SendMessage" method="post" onSubmit="return sendMessage();">
<input onclick="return setXXList(recipientEmails,recipientIds,'list','listViewId','','moz');" type="submit" id="send1" value="<dhv:label name="global.button.send">Send</dhv:label>" />
<input type="button" id="cancel1" value="Annulla" onClick="javascript:window.location.href='MyCFSInbox.do?command=Inbox'"><br><br>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*, org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="Note" class="org.aspcfs.modules.mycfs.base.CFSNote" scope="request"/>
<jsp:useBean id="returnUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="sendUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="forwardType" class="java.lang.String" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>

<jsp:useBean id="SoggettoMessaggio" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoMessaggio" class="org.aspcfs.utils.web.LookupList" scope="request"	/>
<jsp:useBean id="MotivoSegnalazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoBug" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script type="text/javascript">
function sendMessage() {
  formTest = true;
  message = "";
  //if(document.newMessageForm.listView.options[0].value == "none") {
  //  message += label("select.onerecipient","- Select at least one recipient\r\n");
  //  formTest = false;
  //}
  if(checkNullString(document.newMessageForm.subject.value)){
    message += label("check.subject","- Enter a subject\r\n");
    formTest = false;
  }
  if(checkNullString(document.newMessageForm.rispondi_a.value)){
    message += label("","- Inserisci un indirizzo email per ricevere una risposta\r\n");
    formTest = false;
  }else {
	  if ( isEmail(document.newMessageForm.rispondi_a.value)== 0 ) {
		  message += label("","- Inserisci un indirizzo email valido per ricevere una risposta\r\n");
		  formTest = false;
	  }
  }
  if(checkNullString(document.newMessageForm.numero_tel.value)){
    message += label("","- Inserisci un numero di telefono\r\n");
    formTest = false;
  }
  if(checkNullString(document.newMessageForm.body.value)){
    message += label("check.message","- Enter a message in the body\r\n");
    formTest = false;
  }
  if (formTest) {
    hideSendButton();
    return true;
  } else {
    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
    return false;
  }
}

//SCRIPT AGGIUNTI
function isEmail(string) {
	if (string.search(/^\w+((-\w+)|(\.\w+))*\@\w+((\.|-)\w+)*\.\w+$/) != -1)
	return 1;
	else
	return 0;
}


function updateTipoMessaggio() {
	var sel = document.forms['newMessageForm'].elements['tipo'];
	var value = sel.options[sel.selectedIndex].value;
	if (value == '1'){
		document.getElementById('motivoSegnalazione').style.display = "";
		document.getElementById('tipoBug').style.display = "none";
		document.getElementById('dettagliBug').style.display = "none";
	} else if (value == '2'){
		document.getElementById('tipoBug').style.display = "";
		document.getElementById('dettagliBug').style.display = "";
		document.getElementById('motivoSegnalazione').style.display = "none";
	} else {
		document.getElementById('motivoSegnalazione').style.display = "none";
		document.getElementById('tipoBug').style.display = "none";
		document.getElementById('dettagliBug').style.display = "none";
	}
}

function updateTipoBug() {
	var sel = document.forms['newMessageForm'].elements['tipoBug'];
	var value = sel.options[sel.selectedIndex].value;
	if (value == '1'){
		document.getElementById('dettagliBug').style.display = "";
	} else {
		document.getElementById('dettagliBug').style.display = "none";
	}
}

</script>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="actionList.newMessage">New Message</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
          Informazioni di contatto
    </td>
    <td valign="middle">
    <table class="empty">
    <%-- <tr>
       <td width=150px>Username utilizzato per login applicazione:</td> <td> <input type="text" name="username"
             value="<%= User.getUsername() %>" size="20"
             maxlength="30"/></td>
    </tr> --%>
    <tr>
       <td>Telefono:</td> <td><input type="text" name="numero_tel"
             value="<%= User.getContact().getPhoneNumber(1)%>" size="20"
             maxlength="15"/>
             <font color="red">*</font></td>
   	</tr>
   	<tr>
       <td>Email: </td><td><input type="text" name="rispondi_a"
             value="<%= User.getContact().getEmailAddress(1) %>" size="200"
             maxlength="100"/>
             <font color="red">*</font></td>
     </tr>
     </table>
    </td>
  </tr>
  
   <tr>
    <td class="formLabel">
         Data riscontro problema
    </td>
    <td valign="middle">
       <zeroio:dateSelect form="newMessageForm" field="problemDate"
                       timeZone="<%= User.getTimeZone() %>"
                       showTimeZone="false"/>
    </td>
  </tr>
 
  <tr class="containerBody">
    <td valign="top" class="formLabel">
     	Tipo segnalazione
    </td>
    <td>
    	<%= TipoMessaggio.getHtmlSelect( "tipo", -1 )%> 
    </td>
  </tr>
  <tr class="containerBody" id="motivoSegnalazione" style="display: none">
    <td valign="top" class="formLabel">
     	Motivo segnalazione
    </td>
    <td>
    	<%= MotivoSegnalazione.getHtmlSelect( "motivo", -1 )%>
    </td>
  </tr>
   <tr class="containerBody" id="tipoBug" style="display:none"> 
    <td valign="top" class="formLabel">
      Tipo Bug
    </td>
    <td>
    	<%= TipoBug.getHtmlSelect("tipoBug",-1) %> 
    	</td>
  </tr>
  <tr class="containerBody" id="dettagliBug" style="display:none">
    <td valign="top" class="formLabel">
      Dettagli bug tecnico
    </td>
    <td>
       <table class="empty">
        	<tr class="containerBody">
        		<td class="formLabel">Postazione&nbsp; </td>
        		<td><select name="postazione">	
        				<option value="nd">N.D.</option>
        				<option value="portatile">Pc portatile</option>
        				<option value="fisso" selected>Pc fisso</option>
        				<option value="mobile">Dispositivo mobile</option>
        			</select>
        		</td>
        	</tr>
        	<tr>
        		<td class="formLabel">Browser&nbsp; </td>
        		<td>
        		<select name="browser">
        		        <option value="nd">N.D.</option>
        				<option value="IE6">IE 6</option>
        				<option value="IE7">IE 7</option>
        				<option value="IE8">IE 8+</option>
        				<option value="firefox" selected>Firefox</option>
        				<option value="chrome">Chrome</option>
        				<option value="safari">Safari</option>
        				<option value="altro">Altro</option>
        			</select>
        		</td>
        	</tr>
        	<tr class="containerBody">
        		<td class="formLabel">Ripetibilit&agrave&nbsp; </td>
        		<td>
        		<select name="ripet">
        		        <option value="nd">N.D.</option>
        				<option value="sistematico">Sistematico</option>
        				<option value="casuale">Casuale</option>
        				<option value="unatantum">Una tantum</option>
        			</select>
        		</td>
        	</tr>
        </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
     	Problema
    </td>
    <td>
      <table border="0" class="empty">
        <tr>
          <td>
            <textarea name="body" rows="10" COLS="60" value="body"><%= toString(Note.getBody()) %></textarea>
          </td>
          <td valign="top">
            <font color="red">*</font> <%= showAttribute(request, "bodyError") %>
            
            <div style="visibility:hidden">
	            <select size="3" name="listView" id="listViewId" multiple>
	              <%
	              if(Recipient.getId() > 0){
	              %>
	                <option value="<%= Recipient.getId() %>" selected><%= Recipient.getNameLastFirst() %></option>
	              <% }else{ %>
	                <option value="none" selected><dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label></option>
	              <%}%>
	            </select>
	            
            </div>
            
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
  	<td class="formLabel">Oggetto</td>
    <td>
    <%if(User.getRole().equalsIgnoreCase("bdapi")){ %>
    
    <input type="hidden" name = "subject" id = "subject" value="900">
        	APICOLTURA    
    <%} else{%>
    	<%= SoggettoMessaggio.getHtmlSelect( "subject", -1 )%>
    	<%} %>
    </td>
  </tr>
  
</table>
<input type="hidden" name="noteId" value="<%= Note.getId() %>">
<input type="hidden" name="forwardType" value="<%= forwardType %>">
<input type="hidden" name="mailrecipients" value="true" >
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input onclick="return setXXList(recipientEmails,recipientIds,'list','listViewId','','moz');" type="submit" id="send2" value="<dhv:label name="global.button.send">Send</dhv:label>" />
<input type="button" id="cancel2" value="Annulla" onClick="javascript:window.location.href='MyCFSInbox.do?command=Inbox'">
</form>


            <script>
            function setXXList(recipientEmails,recipientIds,listType,displayFieldId,hiddenFieldId){
			  if(recipientEmails.length == 0 && listType == "list"){
			    deleteOptions(displayFieldId);
			    insertOption("Niente selezionato","-1",displayFieldId);
			    return ;
			  }
			  var i = 0;
			  if (listType == "list"){
			    deleteOptions(displayFieldId);
			    for (i=0; i < recipientEmails.length; i++) {
			      insertOption(recipientEmails[i],recipientIds[i],displayFieldId);
			    }
			  } else if(listType == "single") {
			    document.getElementById(hiddenFieldId).value = recipientIds[i];
			    changeDivContent(displayFieldId,recipientEmails[i]);
			  }
			}
			
			var recipientEmails = new Array();
			var recipientIds = new Array();
			
		    recipientEmails[0] = "Amministratore";
		    recipientIds[0] = '1';
			
            
           </script>
