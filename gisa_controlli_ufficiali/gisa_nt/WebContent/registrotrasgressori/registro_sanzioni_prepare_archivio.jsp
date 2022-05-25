<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="anno" class="java.lang.String" scope="request"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script>
function checkForm(form){
	var lista = document.getElementById("archivioAnno");
	
	if (lista.value==""){
		alert('Selezionare un anno per visualizzarne i dati in archivio.');
		return false;
	}
	var anno = lista.options[lista.selectedIndex].value;
		  window.open('RegistroTrasgressori.do?command=RegistroSanzioniArchivioDettaglio&anno='+anno,'popupSelect',
	              'height=400px,width=400px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
		}
	
</script>

<script> function openPdf(){
	var anno = document.getElementById('archivioAnno').value;
	openRichiestaPDF_Trasgressori(anno, 'RegistroTrasgressori');
}

function openPopupExcelTrasgressori(){
	
	  var anno = document.getElementById('archivioAnno').value;
	  var link = 'GenerazioneExcel.do?command=GetExcel&anno='+anno+'&tipo_richiesta=registro_trasgressori'; 
	 var result = window.open(link,'popupSelect',
		'height=400px,width=400px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	  var text = document.createTextNode('GENERAZIONE EXCEL IN CORSO.');
		span = document.createElement('span');
		span.style.fontSize = "30px";
		span.style.fontWeight = "bold";
		span.style.color ="#ff0000";
		span.appendChild(text);
		var br = document.createElement("br");
		var text2 = document.createTextNode('Attendere la generazione del documento entro qualche secondo...');
		span2 = document.createElement('span');
		span2.style.fontSize = "20px";
		span2.style.fontStyle = "italic";
		span2.style.color ="#000000";
		span2.appendChild(text2);
		result.document.body.appendChild(span);
		result.document.body.appendChild(br);
		result.document.body.appendChild(span2);
		result.focus(); 
	  
 }	


</script>

<body>

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="RegistroTrasgressori.do?command=RegistroSanzioni">Registro Trasgressori</a> > 
Archivio
</td>
</tr>
</table>
<%-- End Trails --%>


<dhv:container name="registrotrasgressori" selected="details"  object="" >

<form name="registroSanzioni"
	action="RegistroTrasgressori.do?command=RegistroSanzioni&auto-populate=true" onSubmit=""
	method="post"><input type="hidden" name="doContinue"
	id="doContinue" value="" />

<table>
<th><tr><td><b>Registro sanzioni</b></td></tr></th>
<tr><td><input type="radio" id="anniPrecedenti" name="radioAnno" value="archivio" checked="checked"/> Archivio anni precedenti
<select id="archivioAnno" name="archivioAnno">
<%int year = Integer.parseInt(anno);
for (int i=year-1; i>=2015; i--){ %>
<option value="<%=i%>"><%=i %></option>
<% } %>
</select> 
</td></tr>
<tr><td><input type="button" value="INVIA" onclick="checkForm(this.form)"/></td></tr>

	<!-- SERVER DOCUMENTALE -->
<p align="left">
	 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
		
	 <img src="gestione_documenti/images/pdf_icon.png" border="0" align="absmiddle" height="30" width="30"/>
        <a href="#"	onclick="openPdf()"> PDF Registro per l'anno selezionato</a>
	  <img src="gestione_documenti/images/xls_icon.png" border="0" align="absmiddle" height="30" width="30"/>
        <a href="#"	onClick="openPopupExcelTrasgressori();">Esporta in Excel per l'anno selezionato</a>
	    
</p>
 <!-- SERVER DOCUMENTALE -->


</table>

</form>


</dhv:container>
</body>
</html>