<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
    <%@page import="java.net.InetAddress"%>
     <%@page import="org.aspcfs.modules.acquedirete.base.InfoPdP"%>
    <jsp:useBean id="valoriScelti" class="java.util.ArrayList" scope="request"/>
    <jsp:useBean id="TicketDetails" class="org.aspcfs.modules.campioni.base.Ticket" scope="request"/>
<jsp:useBean id="definitivoDocumentale" class="java.lang.String" scope="request"/>
<jsp:useBean id="OrgOperatore" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="OrgUtente" class="org.aspcf.modules.controlliufficiali.base.OrganizationUtente" scope="request"/>
<jsp:useBean id="OrgCampione" class="org.aspcf.modules.controlliufficiali.base.ModCampioni" scope="request"/>
<jsp:useBean id="PdpList" class="org.aspcfs.modules.acquedirete.base.InfoPdPList" scope="request"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<link rel="stylesheet" documentale_url="" href="css/moduli_print_medio.css" type="text/css" media="print" />
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="css/moduli_screen.css">

<%int z = 0; %>
<!-- INIT DOCUMENTALE -->
	<%@ include file="/gestione_documenti/initDocumentale.jsp" %>
<!-- FINE INIT DOCUMENTALE -->

<!-- INIZIO HEADER -->
	<%@ include file="/campioni/moduli_html/acque/header_acque.jsp" %>
<!-- FINE HEADER -->

<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<table><tr>
<TD>
<div id="idbutn" style="display:block;">
<%-- <input type="button" class = "buttonClass" value ="Salva in modalità definitiva" onclick="this.form.bozza.value='false';javascript:checkSubmit();"/>
--%>
</div>
<input id="stampaId"  style="display:none"  type="submit" class = "buttonClass" value ="Stampa" onclick="window.print();"/>
<input type="hidden" id = "bozza" name = "bozza" value="">

<dhv:permission name="server_documentale-view">

<%if (definitivoDocumentale!=null && definitivoDocumentale.equals("true")){ %>
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
<input id="salvaId" type="button" class = "buttonClass" value ="Genera e Stampa PDF" onclick="if (confirm ('Nella prossima schermata sarà possibile recuperare l\'ultimo PDF generato a partire dal documento a schermo, \n oppure generarne uno nuovo.')){javascript:salva(this.form)}"/>
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


<P class="main">L'anno <input class="layout" type="text" readonly value="<%= OrgOperatore.getAnnoReferto()%>" size="4"/> 
addi' <input class="layout" type="text" readonly value="<%=OrgOperatore.getGiornoReferto() %>" size="2" />
del mese di <input class="layout" type="text" readonly size="10" value="<%= OrgOperatore.getMeseReferto()%>"/> 
alle ore <input class="editField" type="text" name="ore" id="ore" value="<%=valoriScelti.get(z++) %>"  size="5" maxlength="5"/>
i sottoscritti <input class="layout" type="text" readonly size="50" value="<%= OrgOperatore.getComponente_nucleo()%>" />
 <input class="layout" type="text" readonly size="50" value="<%= (OrgOperatore.getComponente_nucleo_due() != null) ? OrgOperatore.getComponente_nucleo_due() : "" %>" /> 
hanno provveduto, presso i punti di prelievo sotto descritti, secondo le procedure previste dalla norma, al prelievo di un campione di acqua. <br/>
<U><b>Presente all'ispezione:</b></U> 
sig. <input class="editField" type="text" name="nome_presente_ispezione" id="nome_presente_ispezione"  value="<%=valoriScelti.get(z++) %>"  size="50" maxlength=""/> 
nato a <input class="editField" type="text" name="luogo_nascita_presente_ispezione" id="luogo_nascita_presente_ispezione"  value="<%=valoriScelti.get(z++) %>"  size="30" maxlength=""/> il 
<input class="editField" type="text" name="giorno_presente_ispezione" id="giorno_presente_ispezione"  value="<%=valoriScelti.get(z++) %>"  size="4" maxlength="2"/>/
<input class="editField" type="text" name="mese_presente_ispezione" id="mese_presente_ispezione"  value="<%=valoriScelti.get(z++) %>"  size="4" maxlength="2"/>/
<input class="editField" type="text"  name="anno_presente_ispezione" id="anno_presente_ispezione"  value="<%=valoriScelti.get(z++) %>" size="8" maxlength="4"/> 
e residente in <input class="editField" type="text" name="luogo_residenza_presente_ispezione" id="luogo_residenza_presente_ispezione"  value="<%=valoriScelti.get(z++) %>"  size="40" maxlength=""/> 
alla via <input class="editField" type="text" name="via_ispezione" id="via_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="100" maxlength=""/> <br>
num <input class="editField" type="text" name="num_civico_presente_ispezione" id="num_civico_presente_ispezione" value="<%=valoriScelti.get(z++) %>" size="6" maxlength="6"/> 
documento d'identita' <input class="editField" type="text" name="doc_identita_presente_ispezione" id="doc_identita_presente_ispezione"  value="<%=valoriScelti.get(z++) %>"  size="50" maxlength="" />
in qualita' di <input class="editField" type="text" name="qualita_presente_ispezione" id="qualita_presente_ispezione"  value="<%=valoriScelti.get(z++) %>"  size="50" maxlength="" />.<br>
<!--  GESTIONE AD HOC RADIOBUTTON: SE IL CAMPO "PRESENTE AI SEGUENTI PUNTI DI PRELIEVO" E' POPOLATO, CHECKA -->
Presente: <input type="radio" name="radioPresenza" id="radioPresenza" <% if (valoriScelti.get(z).equals("")){ %>checked="checked"<%} %>>A tutti i punti di prelievo</input> <input type="radio" name="radioPresenza" id="radioPresenza" <% if (!valoriScelti.get(z).equals("")){ %>checked="checked"<%} %>>Ai seguenti punti di prelievo</input> <input class="editField" type="text" name="presenzaPunti" id="presenzaPunti"   value="<%=valoriScelti.get(z++) %>"  size="100" maxlength=""/>

<br/>
<TABLE cellpadding="10" style="border-collapse: collapse">
 <col width="3%">
 <col width="15%">
 <col width="18%">
 <col width="25%">
 <col width="10%">
 <col width="10%">
 <col width="3%">
 <col width="3%">
 <col width="10%">
 <col width="3%">
  
 <TR>
<Td style="border:1px solid black;"><b>N</b></Td>
<Td style="border:1px solid black;"><b>Comune</b></Td>
<Td style="border:1px solid black;"><b>Ente gestore</b></Td>
<Td style="border:1px solid black;"><b>Denominazione Punto di Prelievo</b></Td>
<Td style="border:1px solid black;"><b>Codice Punto di prelievo</b></Td>
<Td style="border:1px solid black;"><b>Tipologia Controllo</b></Td>
<Td style="border:1px solid black;"><b>Temp gr.C</b></Td>
<Td style="border:1px solid black;"><b>Cloro Residuo</b></Td>
<Td style="border:1px solid black;"><b>Identificativo Bottiglia</b></Td>
 <Td style="border:1px solid black;"><b>Ora Fine Camp.to</b></Td>
</b></TR>

<% for ( int indicepdp = 0 ; indicepdp<PdpList.size() ; indicepdp ++)
	  {
	  InfoPdP pdp = (InfoPdP) PdpList.get(indicepdp);
	  %>
<tr>
<Td style="border:1px solid black;"> <%=indicepdp+1 %> </Td>
<Td style="border:1px solid black;"> <%=pdp.getOrgdDetails().getAddressList().getAddress(5).getCity()  %></Td>
<Td style="border:1px solid black;"> <%=pdp.getOrgdDetails().getEnte_gestore()  %></Td>
<Td style="border:1px solid black;"><%=pdp.getOrgdDetails().getName() %></Td>
<Td style="border:1px solid black;"><%=pdp.getOrgdDetails().getAccountNumber() %></Td>
<Td style="border:1px solid black;">

 <%  if (pdp.isProt_routine()){%>
 ROUTINE
 <%} else if (pdp.isProt_verifica()) {%>
 VERIFICA
  <%} else if (pdp.isProt_replica_micro() && pdp.isProt_replica_chim()){%>
   REPLICA (esame microbiologico)<br/>
   REPLICA (esame chimico)
 <%} else if (pdp.isProt_replica_micro()){%>
 REPLICA (esame microbiologico)
<%} else if (pdp.isProt_replica_chim()) {%>
	REPLICA (esame chimico)
<%} else if (pdp.isProt_radioattivita()) {%>
	RADIOATTIVITA'
<%} else if (pdp.isProt_ricerca_fitosanitari()) {%>
FITOSANITARI
<%} %>

 </Td>
<Td style="border:1px solid black;"><%=pdp.getTemperatura()%></td>
<Td style="border:1px solid black;"><%=pdp.getCloro()%></td>
<Td style="border:1px solid black;"> <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="20" />  </Td>
<Td style="border:1px solid black;"><%=pdp.getOre()%></td>

</tr>	  
	  
<%} %>


</TABLE>

 <br/>
In ordine alle modalita' seguite nel corso delle operazioni di prelevamento si precisa: 
<ul>
<li>che si e' provveduto al preventivo flambaggio del rubinetto</li> 
<li>che il contenitore <span class="NocheckedItem"> &nbsp;<b>da 1000cc</b></span> <span class="NocheckedItem"> &nbsp;<b>da 2000cc</b></span> destinato all'analisi chimico fisiche e' stato risciacquato con la stessa acqua da prelevare</li>
<li>che il contenitore <span class="NocheckedItem"> &nbsp;<b>da 500cc</b></span> <span class="NocheckedItem"> &nbsp;<b>da 1000cc</b></span> <span class="NocheckedItem"> &nbsp;<b>da 5000cc</b></span> destinato alle analisi batteriologiche e' sterile e  <br/>
<span class="NocheckedItem"> &nbsp;<b>contiene</b></span>  <span class="NocheckedItem"> &nbsp;<b>non contiene</b></span> tiosolfato di Na</li>
</ul>

Le aliquote vengono trasportate alla temperatura di +4/8 gr. C per poi essere trasferite al Laboratorio <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="30" /> <br/>
Il presente verbale e' stato redatto in piu' copie di cui una viene rilasciata al Sig  <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="60" /> 
sopra generalizzato, presente al campionamento, che dichiara <br/>
  <input class="editField" type="text" size="180" value="<%=valoriScelti.get(z++) %>" /> <br>
<input class="editField" type="text" size="180" value="<%=valoriScelti.get(z++) %>" /> <br>
<input class="editField" type="text" size="180" value="<%=valoriScelti.get(z++) %>" /> <br>
<br>
<P>

<center>IL PRESENTE ALL'ISPEZIONE &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
 GLI OPERATORI DEL CONTROLLO UFFICIALE</center> 

<div style="page-break-before:always"> 
<!-- INIZIO FOOTER -->
	<%@ include file="/campioni/moduli_html/footer.jsp" %>
<!-- FINE FOOTER -->

</div>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>
</form>


</body>
</html>