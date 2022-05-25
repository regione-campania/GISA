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
  - Version: $Id: accounts_contacts_confirm_importupload.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="ImportDetails" class="org.aspcfs.modules.passaporti.base.PassaportoImport" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table class="trails" cellspacing="0">
<tr>
  <td>
    <a href="PassaportoImport.do"><dhv:label name="">Passaporto</dhv:label></a> >
    <a href="PassaportoImport.do?command=View"><dhv:label name="contacts.companydirectory_confirm_importupload.Import">Import</dhv:label></a> >
    <a href="PassaportoImport.do?command=New"><dhv:label name="contacts.companydirectory_confirm_importupload.NewImport">New Import</dhv:label></a> >
    <dhv:label name="contacts.companydirectory_confirm_importupload.UploadComplete">Upload Complete</dhv:label>
  </td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="contacts.companydirectory_confirm_importupload.NotProcessed">Your file to import has been received, but has not been processed.</dhv:label><br />
      <dhv:label name="contacts.companydirectory_confirm_importupload.ToBeginProcessing">To begin processing, use the &quot;Process Now&quot; button.  However, since processing requires a few minutes of configuration, you can choose to process the file later by using the &quot;Process Later&quot; button.</dhv:label>
    </td>
  </tr>
</table>
<%-- Import Details --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="contacts.details">Details</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="contacts.name">Name</dhv:label>
  </td>
  <td>
    <%= toString(ImportDetails.getName()) %>
  </td>
  </tr>
  <% if ( ImportDetails.getDescription() != null && !"".equals( ImportDetails.getDescription() ) ) { %>
  <tr class="containerBody">
  <td valign="top" class="formLabel" nowrap>
    <dhv:label name="">Descrizione</dhv:label>
  </td>
  <td>
    <%= toHtml(ImportDetails.getDescription()) %>
  </td>
  </tr>
  <% } %>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
  </td>
  <td>
    <%= ImportDetails.getFile().getClientFilename() %>&nbsp;&nbsp;[ <a href="javascript:window.location.href='PassaportoImport.do?command=Download&importId=<%= ImportDetails.getId() %>&fid=<%= ImportDetails.getFile().getId() %>'"><dhv:label name="accounts.accounts_contacts_detailsimport.DownloadFile">Download File</dhv:label></a> ]
  </td>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="contacts.companydirectory_confirm_importupload.FileSize">File Size</dhv:label>
  </td>
  <td>
    <%= ImportDetails.getFile().getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
  </td>
  </tr>
  <%--tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
  </td>
  <td>
    <%= ImportDetails.getStatusString() %> &nbsp;
  </td>
  </tr--%>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
  </td>
  <td>
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
<input type="button" value="<dhv:label name="global.button.ProcessNow">Process Now</dhv:label>" onClick="javascript:window.location.href='PassaportoImport.do?command=InitValidate&importId=<%= ImportDetails.getId() %>';">&nbsp;
<input type="button" value="<dhv:label name="global.button.ProcessLater">Process Later</dhv:label>" onClick="javascript:window.location.href='PassaportoImport.do';">

