<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
      <%
       int maxFileSize=-1;
	   int mb1size = 1048576;
	    maxFileSize=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));
	   	String maxSizeString = String.format("%.2f", (double) maxFileSize/ (double) mb1size);
       %>
       
       <script>function checkFormFile(form){
	var fileCaricato = form.file1;
	var oggetto = form.subject.value;
	var errorString = '';
	if (fileCaricato.value==''){// || (!fileCaricato.value.endsWith(".pdf") && !fileCaricato.value.endsWith(".csv"))){
		errorString+='Errore! Selezionare un file!';
		form.file1.value='';
	}
	if (oggetto==''){
		errorString+='\nErrore! L\'oggetto è obbligatorio.';
		}
	if (!GetFileSize(form.file1))
		errorString+='\nErrore! Selezionare un file con dimensione inferiore a <%=maxSizeString%> MB';
	if (errorString!= '')
		alert(errorString)
	else
	{
	//form.filename.value = fileCaricato.value;	
	form.uploadButton.hidden="hidden";
	form.file1.hidden="hidden";
	document.getElementById("image_loading").hidden="";
	document.getElementById("text_loading").hidden="";
	form.submit();
	}
}</script>

<script>function GetFileSize(fileid) {
	var input = document.getElementById('file1');
        file = input.files[0];
        if (file.size> <%=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"))%>)
      	 	return false;
        return true;
		}
</script>


<form id="form2" action="GestioneAllegatiUploadSuap.do?command=AllegaFile" method="post" name="form2" enctype="multipart/form-data">
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle">Carica Documento</b>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
       Oggetto
      </td>
      <td>
        <input type="text" name="subject" size="59" maxlength="255" value="<%= (String)request.getAttribute("subject") %>"><font color="red">*</font>
     
      </td>
    </tr>
      <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
       
   
       (Max. <%=maxSizeString %> MB)
       
      </td>
      <td>
        <input type="file" id="file1" name="file1" size="45">  <input type="button" id="uploadButton" name="uploadButton" value="UPLOAD" onclick="checkFormFile(this.form)" />
      
          <img id="image_loading" hidden="hidden" src="gestione_documenti/images/loading.gif" height="15"/>
          <input type="text" disabled id="text_loading" name="text_loading" hidden="hidden" value="Caricamento in corso..."  style="border: none"/>
      </td>
    </tr>
     <input type="hidden" name="id" id ="id" value="-1" />
      <input type="hidden" name="stabId" id ="stabId" value="<%=request.getAttribute("stabId") %>" />
      <input type="hidden" name="ticketId" id ="ticketId" value="-1" />
    <input type="hidden" name="folderId" id="folderId" value="-1" />
<input type="hidden" name="parentId" id="parentId" value="-1" /> 
 <input type="hidden" name="op" id="op" value="suapmobile" />
 <input type = "hidden" name = "indice" id = "indice" value = "<%=request.getAttribute("indice") %>">
  </table>
   </form>
   <p align="center">
    * <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label>
     <dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
  </p>
 