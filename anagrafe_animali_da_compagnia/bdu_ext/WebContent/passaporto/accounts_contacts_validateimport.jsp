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
  - Version: $Id: accounts_contacts_validateimport.jsp 15115 2006-05-31 16:47:51Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.StringUtils,org.aspcfs.utils.web.*,org.aspcfs.apps.transfer.reader.mapreader.*" %>
<jsp:useBean id="ImportValidator" class="org.aspcfs.modules.passaporti.base.PassaportoImportValidate" scope="request"/>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.passaporti.base.PassaportoImport" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="AccessTypeList" class="org.aspcfs.modules.admin.base.AccessTypeList" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/tasks.js"></script>
<script language="JavaScript">
  fields = new Array();
  function checkPrompt(field){
    thisField = document.getElementById(field);
    if(thisField.value != null && thisField.value == 'contactEmail.email'){
      showSpan(field + 'emailtypespan');
    }else{
      hideSpan(field + 'emailtypespan');
    }
    
    if(thisField.value != null && thisField.value == 'contactPhone.number'){
      showSpan(field + 'phonetypespan');
    }else{
      hideSpan(field + 'phonetypespan');
    }
    
    if(thisField.value != null && thisField.value == 'contactAddress.streetAddressLine1'){
      showSpan(field + 'addresstypespan');
    }else{
      hideSpan(field + 'addresstypespan');
    }
    
   var groupSelector = document.getElementById(field + 'groupId');
   groupSelector.options.selectedIndex = 0;
   if(thisField.value != null && ((thisField.value.indexOf('contactPhone.') > -1) || (thisField.value.indexOf('contactAddress.') > -1) || (thisField.value.indexOf('contactEmail.') > -1))){
      
    if(thisField.value.indexOf('contactAddress.') > -1 || thisField.value.indexOf('contactPhone.') > -1){
      showSpan(field + 'groupIdspan');
    }else{
      hideSpan(field + 'groupIdspan');
    }
      
      /* get the next available group id if it is a new set of phone number or address*/
      if(thisField.value == 'contactPhone.number' || thisField.value == 'contactAddress.streetAddressLine1' || thisField.value == 'contactEmail.email'){
        var dependency = 'contactAddress.streetAddressLine1';
        if(thisField.value == 'contactPhone.number'){
          dependency = 'contactPhone.number';
        }
        if(thisField.value == 'contactEmail.email'){
          dependency = 'contactEmail.email';
        }
        
        availableGroupIds = new Array();
        for(i = 1; i < 11; i++){
          availableGroupIds[i -1] = i;
        }
        
        for(i = 0; i < fields.length; i++){
          tmpField = fields[i];
          var mappedValue = document.getElementById(tmpField).value;
          if(mappedValue != null && mappedValue == dependency && field != tmpField){
            availableGroupIds[document.getElementById(tmpField + 'groupId').value - 1] = -1;
          }
        }
        
        for(i = 0; i < 10; i++){
          if(availableGroupIds[i] != -1){
            groupSelector.options.selectedIndex = i;
            break;
          }
        }
       }
      /* change the drop down entries */
        var groupDisplay = "Address";
        if(thisField.value.indexOf('contactPhone.') > -1){
          groupDisplay = "Phone";
        }
        if(thisField.value.indexOf('contactEmail.') > -1){
          groupDisplay = "Email";
        }
        for(i = 1; i < 11; i++){
          groupSelector.options[i-1].text = groupDisplay + i;
        }
    }else{
      hideSpan(field + 'groupIdspan');
    }
  }
</script>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="PassaportoImport.do"><dhv:label name="">Passaporto</dhv:label></a> >
    <% if("list".equals(request.getParameter("return"))){ %>
    <a href="PassaportoImport.do?command=View"><dhv:label name="accounts.ViewImports">View Imports</dhv:label></a> >
    <% }else{ %>
    <a href="PassaportoImport.do?command=View"><dhv:label name="accounts.ViewImports">View Imports</dhv:label></a> >
    <a href="PassaportoImport.do?command=Details&importId=<%= ImportDetails.getId() %>"><dhv:label name="accounts.ImportDetails">Import Details</dhv:label></a> >
    <% } %>
    <dhv:label name="accounts.accounts_contacts_validateimport.ProcessImport">Process Import</dhv:label>
  </td>
</tr>
</table>

<%-- End Trails --%>
<form method="post" name="inputForm" action="PassaportoImport.do?command=Validate">
<input type="submit" value="<dhv:label name="accounts.accounts_contacts_detailsimport.Process">Process</dhv:label>">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='PassaportoImport.do?command=View';this.form.dosubmit.value='false';">
<br />
<%= showError(request, "actionError") %> 
<%-- General Import Properties --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_contacts_validateimport.ImportProperties">Import Properties</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="contacts.name">Name</dhv:label>
  </td>
  <td class="containerBody">
    <%= toString(ImportDetails.getName()) %>
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
  </td>
  <td class="containerBody">
    <%= FileItem.getClientFilename() %>
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="contacts.companydirectory_confirm_importupload.FileSize">File Size</dhv:label>
  </td>
  <td class="containerBody">
    <%= FileItem.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
  </td>
  <td class="containerBody">
    <dhv:username id="<%= ImportDetails.getEnteredBy() %>"/>
    <zeroio:tz timestamp="<%= ImportDetails.getEntered()  %>" />
  </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= ImportDetails.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= ImportDetails.getModified()  %>" />
    </td>
  </tr>
  
</table><br>
<%-- Preview of File to be imported --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_contacts_validateimport.First5linesImportfile">First 5 lines of the import file</dhv:label></strong>
    </th>
  </tr>
  <% 
     PropertyMap propertyMap = ImportValidator.getPropertyMap();
     Iterator  i = ImportValidator.getSampleRecords().iterator();
     int lineCount = 1;
     while(i.hasNext()){
     String line = (String) i.next();
  %>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="accounts.accounts_contacts_validateimport.Line">Line</dhv:label> <%= lineCount++ %> 
  </td>
  <td class="containerBody">
    <input type="text" value="<%= toHtml(line) %>" READONLY size="70">
  </td>
  </tr>
  <% } %>
</table><br>
<%-- Errors/Warnings --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong><dhv:label name="accounts.accounts_contacts_validateimport.GeneralErrorsWarnings">General Errors/Warnings</dhv:label></strong>
    </th>
  </tr>
  <%
  ArrayList generalErrors = ImportValidator.getGeneralErrors();
  if(generalErrors != null){
  Iterator ge =  generalErrors.iterator();
   while(ge.hasNext()){
  %>
  <tr>
    <td class="containerBody" align="left">
       <img src="images/error.gif" border="0" align="absmiddle"/>&nbsp;<%= toString((String) ge.next()) %>
    </td>
  </tr>
  <% }
   }else{
  %>
  <tr>
    <td class="containerBody" align="left">
      <dhv:label name="accounts.accounts_contacts_validateimport.NoErrorsWarningsFound">No errors/warnings found.</dhv:label>
    </td>
  </tr>
 <% } %>
</table><br>
<br />
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="importId" value="<%= ImportDetails.getId() %>">
<input type="submit" value="<dhv:label name="accounts.accounts_contacts_detailsimport.Process">Process</dhv:label>" />
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='PassaportoImport.do?command=View';this.form.dosubmit.value='false';" />
</form>
