<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">

	
function catturaHtml(form){
	//popolaCampi();
	h=document.getElementsByTagName('html')[0].innerHTML;
	//h=fixCaratteriSpeciali(h);
	form.htmlcode.value = h;

}

function replaceAll(str, find, replace) {
	  return str.replace(new RegExp(find, 'g'), replace);
}
	
// function fixCaratteriSpeciali(test){
	
// 	test=replaceAll(test,"à", "###agrave###");
// 	test=replaceAll(test,"è", "###egrave###");
// 	test=replaceAll(test,"ì", "###igrave###");
// 	test=replaceAll(test,"ò", "###ograve###");
// 	test=replaceAll(test,"ù", "###ugrave###");
	
// 	test=replaceAll(test,"á", "###aacute###");
// 	test=replaceAll(test,"é", "###eacute###");
// 	test=replaceAll(test,"í", "###iacute###");
// 	test=replaceAll(test,"ó", "###oacute###");
// 	test=replaceAll(test,"ú", "###uacute###");
	
// 	test=replaceAll(test,"À", "###amaiuscgrave###");
// 	test=replaceAll(test,"È", "###emaiuscgrave###");
// 	test=replaceAll(test,"Ì", "###imaiuscgrave###");
// 	test=replaceAll(test,"Ò", "###omaiuscgrave###");
// 	test=replaceAll(test,"Ù", "###umaiuscgrave###");
	
// 	test=replaceAll(test,"Á", "###amaiuscacute###");
// 	test=replaceAll(test,"É", "###emaiuscacute###");
// 	test=replaceAll(test,"í", "###imaiuscacute###");
// 	test=replaceAll(test,"Ó", "###omaiuscacute###");
// 	test=replaceAll(test,"Ú", "###umaiuscacute###");
		
// 	test=replaceAll(test,"°", "###grado###");
// 	return test;
// }

function popolaCampi(){
	var inputs, index;

inputs = document.getElementsByTagName('input');
for (index = 0; index < inputs.length; ++index) {
	if (inputs[index].type=='text')
    inputs[index].innerHTML = 'ciao';
}
}
</script>

<script type="text/javascript">
function openGestioneDocumenti(orgId, ticketId, tipo, url, idCU){
	var res;
	var result;
		window.open('GestioneDocumenti.do?command=ListaDocumentiByTipo&tipo='+tipo+'&orgId='+orgId+'&ticketId='+ticketId+'&idCU='+idCU+'&url='+url,'open_window',
		'height=295px,width=595px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script> 
</head>
<body>
<form>
</form>

<%

if (!request.getParameter("tipo").equals("latteA")
&& !request.getParameter("tipo").equals("molluschi")
&& !request.getParameter("tipo").equals("latteC1")
&& !request.getParameter("tipo").equals("latteC2")
&& !request.getParameter("tipo").equals("pesci")	

) {

%>

<body onload="catturaHtml(this.gestionePdf); this.gestionePdf.submit();">
<%} %>
<form name="gestionePdf" action="GestioneDocumenti.do?command=ListaDocumentiByTipo" method="POST">
<input type="button" id ="generaPDF" class="buttonClass"  value="Genera e Stampa PDF" 	
onClick="if (confirm('Nella prossima schermata sarà possibile recuperare l\'ultimo PDF generato a partire dal documento a schermo, \n oppure generarne uno nuovo.\n\n Assicurarsi di aver salvato le modifiche prima di proseguire. ')){catturaHtml(this.form); this.form.submit()}" />
<input type="hidden" name="orgId" id="orgId" value="<%=request.getParameter("orgId") %>"></input>
<input type="hidden" name="ticketId" id="ticketId" value="<%=request.getParameter("ticketId") %>"></input>
<input type="hidden" name="tipo" id="tipo" value="<%=request.getParameter("tipo") %>"></input>
<input type="hidden" name="idCU" id="idCU" value="<%=request.getParameter("idCU") %>"></input>
<input type="hidden" name="url" id="url" value="<%=request.getParameter("url") %>"></input>
<input type="hidden" name="extra" id="extra" value="<%=request.getParameter("extra") %>"></input>
<input type="hidden" name="altId" id="altId" value="<%=request.getParameter("altId") %>"></input>
<input type="hidden" name="stabId" id="stabId" value="<%=request.getParameter("stabId") %>"></input>
<input type="hidden" name="htmlcode" id="htmlcode" value=""></input>
</form>
<!-- input type="button" name="Timbra PDF" class="buttonClass" value="Genera PDF" onclick="openGestioneDocumenti('<%=request.getParameter("orgId") %>','<%=request.getParameter("ticketId") %>', '<%=request.getParameter("tipo")  %>', '<%=request.getParameter("url")  %>',  '<%=request.getParameter("idCU")  %>');"/-->
</body>
</html>