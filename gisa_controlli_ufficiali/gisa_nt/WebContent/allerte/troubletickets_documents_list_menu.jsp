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
  - Version: $Id: troubletickets_documents_list_menu.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="javascript">
  var thisFolderId = -1;
  var thisTicId = -1;
  var thisFileId = -1;
  var menu_init = false;
  //Set the action parameters for clicked item
  function displayMenu(loc, id, folderId, fileId, ticId) {
    thisFolderId = folderId;
    thisTicId = ticId;
    thisFileId = fileId;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuFolder", "down", 0, 0, 170, getHeight("menuFolderTable"));
      new ypSlideOutMenu("menuFile", "down", 0, 0, 170, getHeight("menuFileTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  
  //Menu link functions

  //Folder operations
  function viewFolder() {
    window.location.href='TroubleTicketsDocumentsAllerte.do?command=View&tId=' + thisTicId + '&folderId=' + thisFolderId;
  }
  function editFolder() {
    window.location.href='TroubleTicketsDocumentsFoldersAllerte.do?command=Modify&tId=' + thisTicId + '&folderId=' + thisFileId + '&id=' + thisFolderId + '&parentId='+thisFileId;
  }
  function moveFolder() {
    popURL('TroubleTicketsDocumentsFoldersAllerte.do?command=Move&tId=' + thisTicId + '&id=' + thisFolderId + '&popup=true&return=TroubleTicketsDocuments&param='+ thisTicId +'&param2='+ thisFolderId ,'Files','400','375','yes','yes');
  }
  function deleteFolder() {
    confirmDelete('TroubleTicketsDocumentsFoldersAllerte.do?command=Delete&tId=' + thisTicId + '&id=' + thisFolderId + '&folderId=' + thisFileId );
  }

  //File operations
  function viewFileHistory() {
    document.location.href='TroubleTicketsDocumentsAllerte.do?command=Details&&tId=' + thisTicId +'&fid=' + thisFileId + '&folderId='+thisFolderId;
  }
  function details() {
    window.location.href='TroubleTicketsDocumentsAllerte.do?command=Details&tId=' + thisTicId + '&fid=' + thisFileId+ '&folderId='+thisFolderId;
  }
  function view() {
    popURL('TroubleTicketsDocumentsAllerte.do?command=Download&tId='+ thisTicId +'&fid=' + thisFileId + '&stream=true', 'Content', 640,480, 1, 1);
  }
  function modify() {
    window.location.href='TroubleTicketsDocumentsAllerte.do?command=Modify&fid=' + thisFileId + '&tId=' + thisTicId+ '&folderId='+thisFolderId;
  }
  function download() {
    window.location.href='TroubleTicketsDocumentsAllerte.do?command=Download&tId=' + thisTicId + '&fid=' + thisFileId+ '&folderId='+thisFolderId;
  }
  function addVersion() {
    document.location.href='TroubleTicketsDocumentsAllerte.do?command=AddVersion&tId=' + thisTicId + '&fid=' + thisFileId + '&folderId='+ thisFolderId;
  }
  function moveFile() {
    popURL('TroubleTicketsDocumentsAllerte.do?command=Move&tId=' + thisTicId + '&fid=' + thisFileId + '&popup=true&return=TroubleTicketsDocuments&param=' + thisTicId +'&param2='+thisFolderId,'Files','400','375','yes','yes');
  }
  function deleteFile() {
    confirmDelete('TroubleTicketsDocumentsAllerte.do?command=Delete&fid='+ thisFileId + '&tId=' + thisTicId+ '&folderId='+thisFolderId);
  }
  
</script>
<div id="menuFileContainer" class="menu">
  <div id="menuFileContent">
    <table id="menuFileTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="allerte-allerte-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewFileHistory()">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.ViewFileHistory">View File History</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="allerte-allerte-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="download();">
        <th>
          <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_contacts_detailsimport.DownloadFile">Download File</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="allerte-allerte-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="view();">
        <th>
          <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.ViewFileContents">View File Contents</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="allerte-allerte-documents-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="modify();">
        <th>
          <img src="images/icons/stock_edit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.RenameFile">Rename File</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="allerte-allerte-documents-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="addVersion()">
        <th>
          <img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.AddVersion">Add Version</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="allerte-allerte-documents-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFile()">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.MoveFile">Move File</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="allerte-allerte-documents-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)" onclick="deleteFile();">
        <th>
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="global.button.delete">Delete</dhv:label>
        </td>
      </tr>
      </dhv:permission>
    </table>
  </div>
</div>
<div id="menuFolderContainer" class="menu">
  <div id="menuFolderContent">
    <table id="menuFolderTable" class="pulldown" width="170" cellspacing="0">
      <dhv:permission name="allerte-allerte-documents-view">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="viewFolder();">
        <th valign="top">
          <img src="images/icons/stock_zoom-folder-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.ViewFolder">View Folder</dhv:label>
        </td>
      </tr>
    </dhv:permission>
      <dhv:permission name="allerte-allerte-documents-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="editFolder();">
        <th>
          <img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="accounts.accounts_documents_list_menu.RenameFolder">Rename Folder</dhv:label>
        </td>
      </tr>
    </dhv:permission>
      <dhv:permission name="allerte-allerte-documents-edit">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="moveFolder();">
        <th>
          <img src="images/icons/stock_drag-mode-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.MoveFolder">Move Folder</dhv:label>
        </td>
      </tr>
      </dhv:permission>
      <dhv:permission name="allerte-allerte-documents-delete">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="deleteFolder();">
        <th valign="top">
          <img src="images/icons/stock_left-with-subpoints-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td>
          <dhv:label name="accounts.accounts_documents_list_menu.DeleteFolderMoveContents">Delete Folder and Move contents to current folder</dhv:label>
        </td>
      </tr>
    </dhv:permission> 
    </table>
  </div>
</div>

