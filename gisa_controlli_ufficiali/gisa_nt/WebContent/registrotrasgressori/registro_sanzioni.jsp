<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="anno" class="java.lang.String" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<script>
function apriRegistro(){
	
	var trimestre = document.getElementById("trimestre").value;
	
	if (trimestre==-1){
		alert("ATTENZIONE. Selezionare un trimestre.");
		return false;
	}
	if (trimestre==0){
		var c = confirm("ATTENZIONE. E' stato selezionato 'Tutti i trimestri'. Il caricamento potra' richiedere diversi minuti.")
			if (c==false)
				return false;
	}
	
	var radios = document.getElementsByName('radioAnno');
	var anno = "-1";
	for (var i = 0, length = radios.length; i < length; i++) {
	    if (radios[i].checked) {
	        anno = radios[i].value;
	        break;
	    }
	}
	        
	  window.open('RegistroTrasgressori.do?command=RegistroSanzioniAnno&anno='+anno+'&trimestre='+trimestre,'popupSelect',
	         'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

		}

	
	function openPopupExcelTrasgressori(anno){
		
		  var link = 'GenerazioneExcel.do?command=GetExcel&anno='+anno+'&tipo_richiesta=registro_trasgressori'; 
		  var result = window.open(link,'popupSelectExcel',
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
	
	function openPopupExcelTrasgressoriOld(anno){
		
		  var link = 'GenerazioneExcel.do?command=GetExcel&anno='+anno+'&tipo_richiesta=registro_trasgressori_old'; 
		  var result = window.open(link,'popupSelectExcel',
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
	
	function inserisciRegistro(){
		var c = confirm("Proseguendo, verra' consentito l'inserimento di una sanzione che comparira' esclusivamente nel Registro Trasgressori.")
				if (c==false)
					return false;
	        
		  window.open('RegistroTrasgressori.do?command=PrepareInserisciSanzione','popupSelect',
		         'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

			}
</script>

<body>

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="RegistroTrasgressori.do?command=RegistroSanzioni">Registro Trasgressori</a> > 
Registro Sanzioni
</td>
</tr>
</table>
<%-- End Trails --%>

<dhv:container name="registrotrasgressori" selected="details"  object="" >

<!-- SERVER DOCUMENTALE -->
<p align="left">
	 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	  <img src="gestione_documenti/images/pdf_icon.png" border="0" align="absmiddle" height="30" width="30"/>
        <a href="#"	onClick="openRichiestaPDF_Trasgressori('<%=anno %>', 'RegistroTrasgressori');"> PDF Registro per l'anno corrente</a>
      <img src="gestione_documenti/images/xls_icon.png" border="0" align="absmiddle" height="30" width="30"/>
        <a href="#"	onClick="openPopupExcelTrasgressori('-1');"> Esporta in Excel per tutti gli anni</a>
</p>

<p align="right">
   <img style="filter: grayscale(100%);" src="gestione_documenti/images/xls_icon.png" border="0" align="absmiddle" height="30" width="30"/>
        <a href="#"	onClick="openPopupExcelTrasgressoriOld('-1');"> <font size="1px">Esporta in Excel per tutti gli anni (Vecchia versione)</font></a>
</p>
 <!-- SERVER DOCUMENTALE -->

<form name="registroSanzioni"
	action="RegistroTrasgressori.do?command=RegistroSanzioniDettaglio&auto-populate=true" onSubmit=""
	method="post"><input type="hidden" name="doContinue"
	id="doContinue" value="" />


<table class="details" cellpadding="10" cellspacing="10">

<tr><th colspan="2"><b>Registro sanzioni</b></th></tr>

<tr><td colspan="2" align="center"><input type="button" value="APRI REGISTRO" onclick="apriRegistro()"/></td> </tr>

<tr><td><b>Anno</b></td><td><b>Trimestre</b></td></tr>


<tr><td>
<input type="radio" id="annoCorrente" name="radioAnno" value="<%=anno %>" checked="checked"/> Anno corrente (<%=anno %>)<br/>
<%for (int i = Integer.parseInt(anno)-1; i>= 2015; i--){ %>
<input type="radio" id="annoPrecedente<%=i %>" name="radioAnno" value="<%=i%>"/> <%=i %> <br/>
<%} %>
</td>

<td valign="top">
<select id="trimestre" name="trimestre">
 <option value="-1" selected>Seleziona</option>
 <% for (int i=1; i<=4; i++) { %>
 <option value="<%=i%>">Trimestre <%=i %></option>
 <% } %>
 <option value="0">Tutti</option>
</select>
</td></tr>

<tr><td colspan="2" align="center">
<dhv:permission name="registro_trasgressori_aggiunta_sanzioni-view">
<input type="button" value="INSERISCI SANZIONE NEL REGISTRO" onclick="inserisciRegistro()"/>
</dhv:permission>
</td> </tr>

</table>

</form>
</dhv:container>


</body>

</html>