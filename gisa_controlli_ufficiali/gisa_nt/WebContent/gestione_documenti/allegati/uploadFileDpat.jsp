<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
      <%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegato"%>
<%
       int maxFileSize=-1;
	   int mb1size = 1048576;
	    maxFileSize=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));
	   	String maxSizeString = String.format("%.2f", (double) maxFileSize/ (double) mb1size);
       %>
       
       <script>function checkFormFile(form){
	var fileCaricato = form.file1;
	var oggetto = form.subject.value;
	
	var tipoAllegato = form.tipoAllegato.value;
	var errorString = '';
	if (fileCaricato.value==''){// || (!fileCaricato.value.endsWith(".pdf") && !fileCaricato.value.endsWith(".csv"))){
		errorString+='Errore! Selezionare un file!';
		form.file1.value='';
	}
	if (oggetto==''){
		errorString+='\nErrore! L\'oggetto è obbligatorio.';
		}
	if (tipoAllegato==''){
		errorString+='\nErrore! Il Campo tipo Allegato è obbligatorio.';
		}
	
	if (!GetFileSize(form.file1))
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
        if (file.size == 0 || file.size> <%=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"))%>)
      	 	return false;
        return true;
		}
</script>




 <%     	
 boolean isLoadMod1=false;
 boolean isLoadMod2=false;
 boolean isLoadMod3=false;
 boolean isLoadMod6=false;
 boolean isLoadMod4=false;
 boolean isLoadMod5=false;

 for (int j=0;j<listaAllegati.size(); j++){
		DocumentaleAllegato doc1 = (DocumentaleAllegato) listaAllegati.get(j);	
		if(doc1.getTipoAllegato()!=null && doc1.getTipoAllegato().equalsIgnoreCase("DPAT_MOD1"))
		{
			isLoadMod1=true ;
		}
		if(doc1.getTipoAllegato()!=null && doc1.getTipoAllegato().equalsIgnoreCase("DPAT_MOD2"))
		{
			isLoadMod2=true ;
		}
		if(doc1.getTipoAllegato()!=null && doc1.getTipoAllegato().equalsIgnoreCase("DPAT_MOD4"))
		{
			isLoadMod4=true ;
		}
		if(doc1.getTipoAllegato()!=null &&  doc1.getTipoAllegato().equalsIgnoreCase("DPAT_MOD3"))
		{
			isLoadMod3=true ;
		}
		if(doc1.getTipoAllegato()!=null &&  doc1.getTipoAllegato().equalsIgnoreCase("DPAT_MOD5"))
		{
			isLoadMod5=true ;
		}
		if(doc1.getTipoAllegato()!=null && doc1.getTipoAllegato().equalsIgnoreCase("DPAT_MOD6"))
		{
			isLoadMod6=true ;
		}
	}
 %>
<form id="form2" action="GestioneAllegatiUpload.do?command=AllegaFileDpat" method="post" name="form2" enctype="multipart/form-data">
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b><dhv:label name="accounts.accounts_documents_upload.UploadNewDocument">Upload a New Document</dhv:label></b>
      </th>
    </tr>
    
     <tr class="containerBody">
      <td class="formLabel">
        SCEGLI MODELLO
      </td>
      <td>
        	<select name="tipoAllegato" id ="tipoAllegato" required="required">
        	<option value="">SCEGLI MODELLO</option>
        	
  			<%if(isLoadMod1==false){ %>
        	<dhv:permission name="server_documentale-dpat-mod1-add">
        	<option value="DPAT_MOD1" >MODELLO 1</option>
        	</dhv:permission>
        	<%}
  			if(isLoadMod2==false){ %>
        	<dhv:permission name="server_documentale-dpat-mod2-add">
        	<option value="DPAT_MOD2" >MODELLO 2</option>
        	</dhv:permission>
        	<%}
  			if(isLoadMod3==false){
  			%>
  			<dhv:permission name="server_documentale-dpat-mod3-add">
        	<option value="DPAT_MOD3">MODELLO 3</option>
        	</dhv:permission>
        	<%}
  			if(isLoadMod4==false){ %>
        	<dhv:permission name="server_documentale-dpat-mod4-add">
        	<option value="DPAT_MOD4" >MODELLO 4</option>
        	</dhv:permission>
        	<%}
  			if(isLoadMod5==false){ %>
        	<dhv:permission name="server_documentale-dpat-mod5-add">
        	<option value="DPAT_MOD5" >MODELLO 5</option>
        	</dhv:permission>
        	<%}
  			if(isLoadMod6==false)
  			{
  			%>
  			<dhv:permission name="server_documentale-dpat-mod6-add">
        	<option value="DPAT_MOD6">MODELLO 6</option>
        	</dhv:permission>
        	<%} %>
        	</select>
      </td>
    </tr>
    
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
      </td>
      <td>
      <%=StrutturaAmbito.getDescrizione_lunga() %>
        <input type="hidden" name="subject" size="59" maxlength="255" value="<%=StrutturaAmbito.getDescrizione_lunga() %>"><font color="red">*</font>
        <%= showAttribute(request, "subjectError") %>
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
     <input type="hidden" name="idStruttura" id ="idStruttura" value="<%= (String)request.getAttribute("idStruttura") %>" />
      <input type="hidden" name="anno" id ="anno" value="<%= (String)request.getAttribute("anno") %>" />
       
 <input type="hidden" name="op" id="op" value="<%= (String)request.getAttribute("op") %>" />
  </table>
   </form>
   <p align="center">
    * <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label>
     <dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
  </p>
 