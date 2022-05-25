<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
  <script>
       
       function checkFormFile(form){
    	   
		var importOk = true;
		var errorString = '';
		
		var fileCaricato = form.file;

		if (fileCaricato.value=='' || (!fileCaricato.value.toLowerCase().endsWith(".xls") && !fileCaricato.value.toLowerCase().endsWith(".xlsx"))){
		errorString+='Errore! Selezionare un file in formato XLS o XLSX!';
		form.file.value='';
		importOk = false;
	}
		
		if (importOk==false){
			alert(errorString);
			return false;
		}
	
	if (!importOk)
		alert(errorString);
	else
	{
		
		if (!confirm("ATTENZIONE! Proseguire?")){
			return false;
		}
		
	alert("L'invio impiegherà diversi minuti.");	
	form.uploadButton.hidden="hidden";
	form.file.hidden="hidden";
	document.getElementById("image_loading").hidden="";
	document.getElementById("text_loading").hidden="";
	loadModalWindow();
	form.submit();
	}
}</script>


<jsp:useBean id="msg" class="java.lang.String" scope="request" />


<%if (msg!=null && !msg.equals("") && !msg.equals("null")) {%>
<script>
alert("<%=msg%>");
</script>
<%} %>



<dhv:container name="inviocupnaa" selected="Importa da File"  object="" >




 <form method="POST" action="GestioneInvioPNAA.do?command=Import" enctype="multipart/form-data" >

<table class="details">


 <tr>
 <td class="formLabel">File</td>
 
 <td>
 
 <input type="file" name="file" id="file"  /> 
 <img id="image_loading" hidden="hidden" src="gestione_documenti/images/loading.gif" height="15"/>
 <input type="text" disabled id="text_loading" name="text_loading" hidden="hidden" value="Caricamento in corso..."  style="border: none"/>
 <input type="hidden" value="/tmp" name="destination"/>
 
 </td>
 </tr>
 
 <tr>
 <td colspan="2" class="formLabel">
 
 <input type="button" value="CONFERMA E INVIA FILE"  id="uploadButton" name="uploadButton"  onClick="checkFormFile(this.form)" />
 
 </td>
 </tr>
 </table>
 
</form>


<a href="#" onClick="window.open('izsmibr/allegati/file_di_esempio_tracciato_invio_prelievi_sinvsa.xlsx')">Scarica file di esempio</a> 

</dhv:container>