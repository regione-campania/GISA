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
  - Version: $Id: accounts_list_menu.jsp 12404 2005-08-05 17:37:07Z mrajkowski $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisOrgId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, orgId, status, trashed) {
    thisOrgId = orgId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuAccount", "down", 0, 0, 170, getHeight("menuAccountTable"));
    }
<dhv:permission name="soa-soa-edit">
    if(trashed == 'true'){
      hideSpan('menuModifyAccount');
      hideSpan('menuArchiveAccount');
      hideSpan('menuReEnableAccount');
      hideSpan('menuDeleteAccount');
    } else {
      if(status == 0){
        hideSpan('menuArchiveAccount');
        showSpan('menuReEnableAccount');
      }else if(status == 1){
        hideSpan('menuReEnableAccount');
        showSpan('menuArchiveAccount');
      }else{
        hideSpan('menuReEnableAccount');
        hideSpan('menuArchiveAccount');
      }
    }
</dhv:permission>
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  //Menu link functions
  function details() {
    window.location.href = 'Soa.do?command=Details&orgId=' + thisOrgId;
  }
  
  
  function modify() {
    window.location.href = 'Soa.do?command=Modify&orgId=' + thisOrgId + '&return=list';
  }
  
  function enable() {
    window.location.href = 'Soa.do?command=Enable&orgId=' + thisOrgId + '&return=list';
  }
  
  function archive() {
    window.location.href = 'Soa.do?command=Delete&orgId=' + thisOrgId + '&action=disable&return=list';
  }
  
  function deleteAccount() {
    popURLReturn('Soa.do?command=ConfirmDelete&id=' + thisOrgId+ '&popup=true','Soa.do?command=Search', 'Delete_account','330','200','yes','no');
  }
</script>
<div id="menuAccountContainer" class="menu">
  <div id="menuAccountContent">
    <table id="menuAccountTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="soa-soa-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="details()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="soa.soa_calls_list_menu.ViewDetails">View Details</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="soa-soa-edit">
      <tr id="menuModifyAccount" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify()">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          Modifica
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="soa-soa-edit">
      <tr id="menuArchiveAccount" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="archive()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="soa.soa_list_menu.Archive">Archive</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="soa-soa-edit">
      <tr id="menuReEnableAccount" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="enable()">
        <th>
          <img src="images/icons/stock_archive-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="soa.soa_list_menu.UnArchive">Un-Archive</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="soa-soa-delete">
      <tr id="menuDeleteAccount" onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteAccount()">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
