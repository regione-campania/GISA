<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

  <script>
       
       function checkFormFile(form){
    	   
    	 var sintesisData = document.getElementById("sintesisData").value;  
		var importOk = true;
		var errorString = '';
		
		var fileCaricato = form.file;

		if (fileCaricato.value=='' || !fileCaricato.value.toLowerCase().endsWith(".xls")){
		errorString+='Errore! Selezionare un file in formato XLS!';
		form.file.value='';
		importOk = false;
	}
		
		if (importOk==false){
			alert(errorString);
			return false;
		}
	
	if (sintesisData==''){
			importOk = false;
			errorString +='Errore! Indicare data del documento SINTESIS.';
	}
	
	if (!importOk)
		alert(errorString);
	else
	{
		
		if (!confirm("ATTENZIONE! Proseguire?")){
			return false;
		}
		
	alert("L'import impiegherà diversi minuti.");	
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



<dhv:container name="sintesisimport" selected="Importa da File"  object="" >




 <form method="POST" action="StabilimentoSintesisAction.do?command=ImportDaFile" enctype="multipart/form-data" >

<table class="details">

<tr>
<td class="formLabel">Data Documento SINTESIS</td>
<td>
 
 <input readonly type="text" id="sintesisData" name="sintesisData" size="10" />&nbsp; 
 <a href="#" onClick="cal19.select(document.getElementById('sintesisData'),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"> 
 <img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
 
 </td>
 </tr>
 
<!--  <tr> -->
<!-- <td class="formLabel">Ignora flusso stati</td> -->
<!-- <td> -->
<!--  <input type="checkbox" id="ignoraFlussoStati" name="ignoraFlussoStati" /> SI  -->
<!--  </td> -->
<!--  </tr> -->
 
  <tr>
<td class="formLabel">Ignora lunghezza approval</td>
<td>
 <input type="checkbox" id="ignoraLunghezzaApproval" name="ignoraLunghezzaApproval" /> SI <br/><font size="1px" color="red">>= 20 caratteri</font>
 </td>
 </tr>
 
 <tr style="display:none">
<td class="formLabel">Presenza header/footer</td>
<td>
 <input type="checkbox" id="presenzaHeader" name="presenzaHeader" checked /> SI <br/><font size="1px" color="red">Header/footer: Riga delle <br/> intestazioni e legenda</font>
 </td>
 </tr>
 
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

</dhv:container>