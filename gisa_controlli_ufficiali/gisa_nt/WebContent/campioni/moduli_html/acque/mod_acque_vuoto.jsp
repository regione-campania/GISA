<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
    <%@page import="java.net.InetAddress"%>
    
    <jsp:useBean id="valoriScelti" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="definitivo" class="java.lang.String" scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<link rel="stylesheet" DOCUMENTALE_URL href="campioni/print.css" type="text/css" media="print" />
<link rel="stylesheet" type="text/css" media="screen" DOCUMENTALE_URL href="campioni/screen.css"> 

<!-- INIT DOCUMENTALE -->
	<%@ include file="/gestione_documenti/initDocumentale.jsp" %>
<!-- FINE INIT DOCUMENTALE -->

<!-- INIZIO HEADER -->
	<%@ include file="/campioni/moduli_html/acque/header_acque_vuoto.jsp" %>
<!-- FINE HEADER -->

<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<table><tr>
<TD>
<div id="idbutn" style="display:block;">
<%-- <input type="button" class = "buttonClass" value ="Salva in modalità definitiva" onclick="this.form.bozza.value='false';javascript:checkSubmit();"/>
--%>
</div>
<input id="stampaId" type="submit" class = "buttonClass" value ="Stampa" onclick="window.print();"/>
<input type="hidden" id = "bozza" name = "bozza" value="">

<dhv:permission name="server_documentale-view">
<%if (definitivo!=null && definitivo.equals("true")){ %>
<!--  BOX DOCUMENTALE -->
	  <jsp:include page="../../../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="orgId" value="<%=request.getParameter("orgId") %>" />
     <jsp:param name="ticketId" value="<%=request.getParameter("ticketId") %>" />
      <jsp:param name="tipo" value="<%=request.getParameter("tipo") %>" />
       <jsp:param name="idCU" value="<%=request.getParameter("idCU") %>" />
        <jsp:param name="url" value="<%=request.getParameter("url") %>" />
</jsp:include>
<!--  BOX DOCUMENTALE -->
<% } else {%>
<form method="post" name="form2" action="PrintModulesHTML.do?command=ViewModules">
<input id="salvaId" type="button" class = "buttonClass" value ="Genera PDF" onclick="if (confirm ('Nella prossima schermata sarà possibile recuperare l\'ultimo PDF generato a partire dal documento a schermo, \n oppure generarne uno nuovo.')){javascript:salva(this.form)}"/>
<input type="hidden" id="documentale" name ="documentale" value="ok"></input>
<input type="hidden" id="listavalori" name ="listavalori" value=""></input>
 <input type="hidden" id ="orgId" name ="orgId" value="<%=request.getParameter("orgId") %>" />
  <input type="hidden" id ="ticketId" name ="ticketId" value="<%=request.getParameter("ticketId") %>" />
   <input type="hidden" id ="tipo" name ="tipo" value="<%=request.getParameter("tipo") %>" />
    <input type="hidden" id ="idCU" name ="idCU" value="<%=request.getParameter("idCU") %>" />
      <input type="hidden" id ="url" name ="url" value="<%=request.getParameter("url") %>" />
</form>
<% } %>
</dhv:permission>
<%-- onclick="this.form.bozza.value='false';" --%>
</TD>
</TABLE>


<P class="main">L'anno 20<input class="layout" type="text" readonly value="" size="6"/> 
addì <input class="layout" type="text" readonly value="" size="6" />
del mese di <input class="layout" type="text" readonly size="25" value=""/> 
i sottoscritti <input class="layout" type="text" readonly size="50" value="" />
 <input class="layout" type="text" readonly size="70" value="" /> 
hanno provveduto, presso i punti di prelievo sotto descritti, secondo le procedure previste dalla norma, al prelievo di un campione di acqua. <br/>
<U><b>Presente all'ispezione:</b></U> 
sig. <input class="editField" type="text" name="nome_presente_ispezione" id="nome_presente_ispezione"  value=""  size="50" maxlength=""/> 
nato a <input class="editField" type="text" name="luogo_nascita_presente_ispezione" id="luogo_nascita_presente_ispezione"  value=""  size="30" maxlength=""/> il 
<input class="editField" type="text" name="giorno_presente_ispezione" id="giorno_presente_ispezione"  value=""  size="4" maxlength="2"/>/
<input class="editField" type="text" name="mese_presente_ispezione" id="mese_presente_ispezione"  value=""  size="4" maxlength="2"/>/
<input class="editField" type="text"  name="anno_presente_ispezione" id="anno_presente_ispezione"  value="" size="8" maxlength="4"/> 
e residente in <input class="editField" type="text" name="luogo_residenza_presente_ispezione" id="luogo_residenza_presente_ispezione"  value=""  size="40" maxlength=""/> 
alla via <input class="editField" type="text" name="via_ispezione" id="via_ispezione"   value=""  size="100" maxlength=""/> <br>
n° <input class="editField" type="text" name="num_civico_presente_ispezione" id="num_civico_presente_ispezione" value="" size="6" maxlength="6"/> 
documento d'identità <input class="editField" type="text" name="doc_identita_presente_ispezione" id="doc_identita_presente_ispezione"  value=""  size="50" maxlength="" />
in qualità di <input class="editField" type="text" name="qualita_presente_ispezione" id="qualita_presente_ispezione"  value=""  size="50" maxlength="" />.<br>

<br/>
<TABLE cellpadding="10" style="border-collapse: collapse">
 <col width="5%">
 <col width="15%">
 <col width="10%">
 <col width="20%">
 <col width="10%">
 <col width="20%">
 <col width="5%">
 <col width="5%">
 <col width="5%">
 <col width="10%">
 <col width="5%">
 
 <TR>
<Td style="border:1px solid black;"> N°</Td>
<Td style="border:1px solid black;"> Comune</Td>
<Td style="border:1px solid black;"> Ente gestore</Td>
<Td style="border:1px solid black;"> Denominazione Punto di Prelievo</Td>
<Td style="border:1px solid black;"> Codice Punto di prelievo</Td>
<Td style="border:1px solid black;"> Tipologia Controllo</Td>
<Td style="border:1px solid black;"> Temp °C</Td>
<Td style="border:1px solid black;"> Cloro Residuo</Td>
<Td style="border:1px solid black;"> Identificativo Bottiglia</Td>
 <Td style="border:1px solid black;"> Ora Fine Camp.to</Td>
</TR>
 <% for (int i=0;i<3;i++){ %>
 <TR>
<Td style="border:1px solid black;"> </Td>
<Td style="border:1px solid black;"> </Td>
<Td style="border:1px solid black;"> </Td>
<Td style="border:1px solid black;"> </Td>
<Td style="border:1px solid black;"> </Td>
<Td style="border:1px solid black;">
<div align="left">
 <span class="NocheckedItem"> &nbsp;<b>Routine</b></span><br/>
 <span class="NocheckedItem"> &nbsp;<b>Verifica</b></span><br/>
 <span class="NocheckedItem"> &nbsp;<b>Replica (esame batt.gico)</b></span><br/>
 <span class="NocheckedItem"> &nbsp;<b>Replica (esame chimico)</b></span><br/>
 </div>
 </Td>
<Td style="border:1px solid black;"> </Td>
<Td style="border:1px solid black;"> </Td>
<Td style="border:1px solid black;"> </Td>
<Td style="border:1px solid black;"> </Td>
</TR>
 
<%} %>
</TABLE>
 <br/>
In ordine alle modalità seguite nel corso delle operazioni di prelevamento si precisa: 
<ul>
<li>che si è provveduto al preventivo flambaggio del rubinetto</li> 
<li>che il contenitore <span class="NocheckedItem"> &nbsp;<b>da 1000cc</b></span> <span class="NocheckedItem"> &nbsp;<b>da 2000cc</b></span> destinato all'analisi chimico fisiche è stato risciacquato con la stessa acqua da prelevare</li>
<li>che il contenitore <span class="NocheckedItem"> &nbsp;<b>da 500cc</b></span> <span class="NocheckedItem"> &nbsp;<b>da 1000cc</b></span> <span class="NocheckedItem"> &nbsp;<b>da 5000cc</b></span> destinato alle analisi batteriologiche è sterile e contiene non contiene tiosolfato di Na</li>
</ul>

Le aliquote vengono trasportate alla temperatura di +4/8 °C per poi essere trasferite al Laboratorio <input class="editField" type="text" value="" size="30" /> <br/>
Il presente verbale è stato redatto in più copie di cui una viene rilasciata al Sig  <input class="editField" type="text" value="" size="60" /> 
sopra generalizzato, presente al campionamento, che dichiara <br/>
  <input class="editField" type="text" size="180" value="" /> <br>
<input class="editField" type="text" size="180" value="" /> <br>
<input class="editField" type="text" size="180" value="" /> <br>
<br>
<P>

<center>IL PRESENTE ALL'ISPEZIONE &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
 GLI OPERATORI DEL CONTROLLO UFFICIALE</center> 



</body>
</html>