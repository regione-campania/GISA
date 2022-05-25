<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*, org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="Note" class="org.aspcfs.modules.mycfs.base.CFSNote" scope="request"/>
<jsp:useBean id="returnUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="sendUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="forwardType" class="java.lang.String" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>

<script type="text/javascript">
function sendMessage() {
  formTest = true;
  
  message = "";
  if(document.newMessageForm.listView.options[0].value == "none") {
    message += label("select.onerecipient","- Select at least one recipient\r\n");
    formTest = false;
  }
  if(checkNullString(document.newMessageForm.subject.value)){
    message += label("check.subject","- Enter a subject\r\n");
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
</script>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="actionList.nuovoMessaggio">Nuovo Messaggio</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="calendar.recipients">Recipient(s)</dhv:label>
    </td>
    <td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td>
            <select size="3" name="listView" id="listViewId" multiple>
              <%
              
              if(Recipient.getId() > 0){
              %>
              <%if(Recipient.getAddressList().size()>0){ %>
                <option value="<%= Recipient.getId()+";"+Recipient.getAddressList().get(0) %>" selected="selected"><%= Recipient.getNameLastFirst() %></option>
              <%}else{%>
              <option value="<%= Recipient.getId()+";"%>" selected="selected"><%= Recipient.getNameLastFirst() %></option>
              <%} %>
              <%}else{%>
                <option value="none" selected="selected" ><dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label></option>
              <%}%>
            </select>
          </td>
          <td valign="top">
            [<a href="javascript:popContactsListMultiple('listViewId','1', 'reset=true&dipendenti=dipendenti<%= User.getUserRecord().getSiteId() == -1?"&includeAllSites=true&siteId=-1":"&mySiteOnly=true&siteId="+ User.getUserRecord().getSiteId() %>');"><dhv:label name="calendar.addRecipients">Add Recipients</dhv:label></a>]<font color="red">*</font>
            <%= showAttribute(request, "contactsError") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
  <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
    </td>
    <td>
      <input type="text" name="subject" value="<%=Note.getSubject().equals("")?"":Note.getSubject()%>" size="50" maxlength="255">
      <font color="red">*</font> <%= showAttribute(request, "messageSubjectError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
  <td valign="top" class="formLabel">
  Allegati
  </td>
    <td>
       &nbsp;[<a href="javascript:popLookupSelectorAllegatiInBacheca('body','alertText','lookup_codistat','');"><dhv:label name="">Aggiungi Allegato da Bacheca</dhv:label></a>]
     
      <input type = "hidden" id = "elementi" name = "elementi" value = "<%=Note.getListaAllegati().size()%>">
   		<input type = "hidden" id = "size" name = "size" value = "<%=Note.getListaAllegati().size() %>">
   		<table id = "tab">
   		<%if(Note.getListaAllegati().size()>0 ){

	%>
  <%
  int i =0;
  for(Allegato allegato : Note.getListaAllegati())
  {
	  i++;
	  %>
	  
	  <tr id = "row" >
   		<td id = "col" > <a href = "GestioneBacheca.do?command=DownloadPDF&codDocumento=<%=allegato.getCodAllegato() %>&nomeDocumento=<%=allegato.getDescrizione().replaceAll(" ", "_") +"."+allegato.getEstensione()%>" id = "collegamento_<%=i %>">
   		 <label id = "label"><%="Allegato "+i+" - "+allegato.getDescrizione()+" - "+allegato.getEstensione() %></label> 		
   		</a>
   		<input type = "hidden" name ="allegato_<%=i %>" id = "allegato_<%=i %>" value = "<%=allegato.getCodAllegato() %>">
   		<input type = "hidden" name ="title_<%=i %>" id = "title_<%=i %>" value = "<%=allegato.getDescrizione() %>">
   		<input type = "hidden" name ="folderid_<%=i %>" id = "folderid_<%=i %>" value = "<%=allegato.getFolderid() %>">
   		<input type = "hidden" name ="estensione_<%=i %>" id = "estensione_<%=i %>" value = "<%=allegato.getEstensione() %>">
   		<input type = "hidden" name ="versione_<%=i %>" id = "versione_<%=i %>" value = "<%=allegato.getVersione() %>">
   		</td>
   		</tr>

	  
  <%} %>	
  

<%} %>
   		
   		<tr id = "row" style="display: none">
   		<td id = "col" ><a href = "" id = "collegamento"> <label id = "label"></label> 		
   		</a>
   		<input type = "hidden" name ="allegato" id = "allegato">
   		<input type = "hidden" name ="title" id = "title">
   		<input type = "hidden" name ="folderid" id = "folderid">
   		<input type = "hidden" name ="estensione" id = "estensione">
   		<input type = "hidden" name ="versione" id = "versione">
   		</td>
   		</tr>
   		
   		</table>
    </td>

  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="calendar.body">Body</dhv:label>
    </td>    
    <td>
      <table border="0" class="empty">
        <tr>
          <td>
            <textarea name="body" rows="10" COLS="60" value="body"><%= toString(Note.getBody()) %></textarea>
          </td>
          <td valign="top">
            <font color="red">*</font> <%= showAttribute(request, "bodyError") %>
        </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<input type="hidden" name="noteId" value="<%= Note.getId() %>">
<input type="hidden" name="forwardType" value="<%= forwardType %>">
<%= addHiddenParams(request, "popup|popupType|actionId") %>
