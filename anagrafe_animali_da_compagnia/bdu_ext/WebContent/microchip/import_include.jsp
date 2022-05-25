<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="contacts.companydirectory_confirm_importupload.NewImport">New Import</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="contacts.name">Name</dhv:label>
  </td>
  <td class="containerBody">
    <input type="text" name="name" value="<%= toString(ImportDetails.getName()) %>" maxlength="250" size="65"><font color="red">*</font>
    <%= showAttribute(request, "nameError") %>
  </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="">Descrizione</dhv:label>
    </td>
    <td>
      <TEXTAREA NAME="description" ROWS="3" COLS="50"><%= toString(ImportDetails.getDescription()) %></TEXTAREA>
    </td>
  </tr>
  <tr style="display:none" class="containerBody">
    <td nowrap class="formLabel" valign="top">
      <dhv:label name="campaign.comments">Comments</dhv:label>
    </td>
    <td>
      <TEXTAREA NAME="comments" ROWS="3" COLS="50"><%= toString(ImportDetails.getComments()) %></TEXTAREA>
    </td>
  </tr>
  <tr style="display:none" class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="contacts.source">Source</dhv:label>
    </td>
    <td class="containerBody">
      <%= SourceTypeList.getHtmlSelect("sourceType", ImportDetails.getSourceType()) %>
    </td>
  </tr>
  <tr style="display:none" class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="sales.rating">Rating</dhv:label>
    </td>
    <td><%= RatingList.getHtmlSelect("rating", ImportDetails.getRating()) %></td>
  </tr>
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="admin.user.site">Site</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%=User.getSiteId() == - 1%>" >
        <%= SiteList.getHtmlSelect("siteId", Constants.INVALID_SITE) %><font color="red">*</font>
        <%= showAttribute(request, "siteIdError") %>
      </dhv:evaluate>
      <dhv:evaluate if="<%=User.getSiteId() != - 1%>" >
        <%= SiteList.getSelectedValue(User.getSiteId()) %>
        <input type="hidden" name="siteId" value="<%=User.getSiteId()%>" >
      </dhv:evaluate>
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
    </td>
    <td>
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td valign="top">
            <input type="file" name="id" size="45" onchange="checkfile(this);" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
            <%= showAttribute(request, "fileError") %>
          </td>
        </tr>
        <tr>
          <td valign="top">
            <br><dhv:label name="calendar.fileShouldBeInCSVformat.text">* File should be in CSV format.</dhv:label><br /> <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload"> Large files may take a while to upload.</dhv:label>
          	<% if ( request.getAttribute("veterinario") != null ) { %>
          		<br>* Il Formato dei file previsto è 1° riga: "Microchip","CF"  dalla 2° riga in poi es. "9800008726378","CF"
          	<% } else { %>
	          	<br>* Il Formato dei file previsto è 1° riga: "Microchip","Asl"  dalla 2° riga in poi es. "9800008726378","NA1" 
          		<br> Formato asl di appartenenza: "AV","NA1C","NA2N","NA3S","BN","SA","CE"
          		<br>* Per il caricamento microchips utenti UNINA il formato,  dalla 2° riga in poi, è  "9800008726378","UNINA"
          	<% } %>   
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

