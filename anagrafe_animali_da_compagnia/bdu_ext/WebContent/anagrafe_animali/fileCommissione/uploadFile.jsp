<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
      <%@page import="org.aspcfs.utils.ApplicationProperties"%>
<%
       int maxFileSize=-1;
	   int mb1size = 1048576;
	    maxFileSize=Integer.parseInt(ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));
	   	String maxSizeString = String.format("%.2f", (double) maxFileSize/ (double) mb1size);
       %>
       
       <script>
       function checkFormFile(form)
       {
	var fileCaricato = form.file1;
	var oggetto = form.nome.value;
	var errorString = '';
	if (fileCaricato.value==''){// || (!fileCaricato.value.endsWith(".pdf") && !fileCaricato.value.endsWith(".csv"))){
		errorString+='Selezionare un file';
		form.file1.value='';
	}
	if (oggetto==''){
		
		if(errorString!='')
			errorString+=" e compilare il campo 'nome'";
		else
			errorString='Il nome del file è obbligatorio.';
		}
	if (fileCaricato.value!='' && !GetFileSize(form.file1))
		errorString+='\nErrore! Selezionare un file con dimensione superiore a 0 ed inferiore a <%=maxSizeString%> MB';
	if (errorString!= '')
		alert(errorString)
	else
	{
	//form.filename.value = fileCaricato.value;	
	form.uploadButton.hidden="hidden";
	form.file1.hidden="hidden";
	document.getElementById("image_loading").hidden="";
	document.getElementById("text_loading").hidden="";
	loadModalWindow();
	form.submit();
	}
}</script>

<script>function GetFileSize(fileid) {
	var input = document.getElementById('file1');
        file = input.files[0];
        if (file.size == 0 || file.size> <%=Integer.parseInt(ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"))%>)
      	 	return false;
        return true;
		}
</script>


<form id="form2" action="GestioneAllegatiUpload.do?command=CreaNuovaCartella" method="post" name="form2" enctype="multipart/form-data">
<input type="hidden" name="folderId" id="folderId" value="-1" />
<input type="hidden" name="parentId" id="parentId" value="-1" />
<input type="hidden" name="idAnimale" id="idAnimale" value="42" />
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b>Carica un nuovo file</b>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Nome
      </td>
      <td>
        <input type="text" name="nome" size="59" maxlength="299" value=""><font color="red">*</font>
        <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
      <tr class="containerBody">
      <td class="formLabel">
        File
       
   
       (Max. <%=maxSizeString %> MB)
       
      </td>
      <td>
        <input type="file" id="file1" name="file1" size="45">  <input type="button" id="uploadButton" name="uploadButton" value="UPLOAD" onclick="checkFormFile(this.form)" />
      
          <img id="image_loading" hidden="hidden" src="gestione_documenti/images/loading.gif" height="15"/>
          <input type="text" disabled id="text_loading" name="text_loading" hidden="hidden" value="Caricamento in corso..."  style="border: none"/>
           * Attendere il messaggio di completamento caricamento
      </td>
    </tr>
  </table>
   </form>
 