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
  <%-- %>tr class="containerBody">
  <td class="formLabel" nowrap>
    <dhv:label name="contacts.name">Name</dhv:label>
  </td>
  <td class="containerBody"--%>
    <input type="hidden" name="name" value="<%= request.getAttribute("dataCorrente")  %>" >
    
  <%--/td>
  </tr--%>
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
    <input type="hidden" name="siteId" id="siteId" value="-1" />
 
  
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
          		<br>* Il Formato dei file previsto è una lista di Microchip incolonnati.
          	<% } else { %>
	          	<br>* Il Formato dei file previsto è: "9800008726378","NA1" 
          		<br> Formato asl di appartenenza: "AV","NA1C","NA2N","NA3S","BN","SA","CE"
          	<% } %>   
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

