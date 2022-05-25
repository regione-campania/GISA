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
  - Version: $Id: admin_modifylist.jsp 15115 2006-05-31 16:47:51Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,java.text.DateFormat,org.aspcfs.utils.*" %>
<%@ page import="org.aspcfs.utils.web.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="SelectedList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="moduleId" class="java.lang.String" scope="request"/>
<jsp:useBean id="SubTitle" class="java.lang.String" scope="request"/>
<jsp:useBean id="category" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/checkString.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/editListForm.js"></script>
<script language="JavaScript" type="text/javascript">
  function doCheck() {
    if (document.modifyList.dosubmit.value == "false") {
      return true;
    }
    var test = document.modifyList.selectedList;
    if (test != null) {
      return selectAllOptions(document.modifyList.selectedList);
    }
  }
</script>
<body onLoad="javascript:document.forms['modifyList'].newValue.focus();">
<form name="modifyList" method="post" action="LaboratoriHACCP.do?command=UpdateList" onSubmit="return doCheck();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>

</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="3">
      <strong><%= toHtml(SubTitle) %></strong>
    </th>
  </tr>
  <!-- GUIDA UTENTE-->
<dhv:permission name="guidautente-view">
<a href="javascript:popURL('admin/guida.jsp','CRM_Help','790','500','yes','yes');">
<font size="3px" color="#006699"><b>Clicca qui per la guida utente </font><b></a>
</dhv:permission>
<tr>&nbsp;</tr>
  <tr>
    <td width="50%">
      <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
        <tr>
          <td valign="center">
            <dhv:label name="admin.newOption">New Option</dhv:label>
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="text" name="newValue" id="newValue" value="" size="25" maxlength="350">
          </td>
        </tr>
        <tr>
          <td valign="center">
            <input type="button" name="addButton" id="addButton" value="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>" onclick="javascript:addValues()">
          </td>
        </tr>
      </table>
    </td>
    <td width="25">
    <table width="100%" cellspacing="0" cellpadding="2" border="0" class="empty">
      
      
      
    </table>
    </td>
    <td width="50%">
    <%
    int count = 0;
    HtmlSelect itemListSelect = SelectedList.getHtmlSelectObj(0);
    itemListSelect.setSelectSize(10);
    Iterator i = SelectedList.iterator();
    if (i.hasNext()) {
      while (i.hasNext()) {
          LookupElement thisElement = (LookupElement) i.next();
          if (!thisElement.isGroup()) {
     %>
          <script>itemList[<%= count %>] = new category(<%= thisElement.getCode() %>, "<%= thisElement.getDescription() %>", '<%= thisElement.getEnabled() ? "true" : "false" %>');</script>
     <% 
         count++;
         }
        }%>
      <% SelectedList.setJsEvent("onChange=\"javascript:resetOptions();\""); %>
      <%= SelectedList.getHtmlSelect("selectedList",0) %>
    <% }else{%>
      <select name="selectedList" multiple id="selectedList" size="10" onChange="javascript:resetOptions();">
        <option value="-1"><dhv:label name="admin.itemList">--------Item List-------</dhv:label></option>
        </select>
    <%}%>
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <input type="hidden" name="selectNames" value="">
      <input type="hidden" name="moduleId" value="<%= moduleId %>">
      <input type="hidden" name="dosubmit" value="true">
      <input type="hidden" name="tableName" value="<%= SelectedList.getTableName() %>">
      <input type="hidden" name="category" value="<%= category %>">
      <input type="submit" value="<dhv:label name="button.saveChanges">Save Changes</dhv:label>" onClick="javascript:this.form.dosubmit.value='true';">
      <input type="submit" value="Annulla" onClick="javascript:this.form.dosubmit.value='false';this.form.action='LaboratoriHACCP.do?command=SearchForm'">
    </td>
  </tr>
</table>
</form>
</body>