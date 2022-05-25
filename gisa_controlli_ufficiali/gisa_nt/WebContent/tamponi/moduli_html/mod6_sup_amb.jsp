<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="definitivoDocumentale" class="java.lang.String" scope="request"/>

<!-- INIT DOCUMENTALE -->
	<%@ include file="/gestione_documenti/initDocumentale.jsp" %>
<!-- FINE INIT DOCUMENTALE -->

<!-- INIZIO HEADER -->
	<%@ include file="/tamponi/moduli_html/header.jsp" %>
<!-- FINE HEADER -->

<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<table><tr>
<TD>
<div id="idbutn" style="display:block;">
<%-- <input type="button" class = "buttonClass" value ="Salva in modalità definitiva" onclick="this.form.bozza.value='false';javascript:checkSubmit();"/>
--%>
</div>
<input id="stampaId" style="display: none" type="button" class = "buttonClass" value ="Stampa" onclick="window.print();"/>
<input type="hidden" id = "bozza" name = "bozza" value="">
<dhv:permission name="server_documentale-view">
<%if (definitivoDocumentale!=null && definitivoDocumentale.equals("true")){ %>
<!--  BOX DOCUMENTALE -->
	  <jsp:include page="../../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="orgId" value="<%=request.getParameter("orgId") %>" />
     <jsp:param name="ticketId" value="<%=request.getParameter("ticketId") %>" />
      <jsp:param name="tipo" value="<%=request.getParameter("tipo") %>" />
       <jsp:param name="idCU" value="<%=request.getParameter("idCU") %>" />
        <jsp:param name="url" value="<%=request.getParameter("url") %>" />
</jsp:include>
<!--  BOX DOCUMENTALE -->
<% } else {%>
<form method="post" name="form2" action="PrintModulesHTML.do?command=ViewModello10Macelli">
<input id="salvaId" type="button" class = "buttonClass" value ="Genera PDF" onclick="if (confirm ('Nella prossima schermata sarà possibile recuperare l\'ultimo PDF generato a partire dal documento a schermo, \n oppure generarne uno nuovo.')){javascript:salva(this.form)}"/>
<input type="hidden" id="documentale" name ="documentale" value="ok"></input>
<input type="hidden" id="listavalori" name ="listavalori" value=""></input>
 <input type="hidden" id ="orgId" name ="orgId" value="<%=request.getParameter("orgId") %>" />
  <input type="hidden" id ="ticketId" name ="ticketId" value="<%=request.getParameter("ticketId") %>" />
   <input type="hidden" id ="comboDateMacellazione" name ="comboDateMacellazione" value="<%=request.getParameter("comboDateMacellazione") %>" />
   <input type="hidden" id ="tipo" name ="tipo" value="<%=request.getParameter("tipo") %>" />
    <input type="hidden" id ="idCU" name ="idCU" value="<%=request.getParameter("idCU") %>" />
      <input type="hidden" id ="url" name ="url" value="<%=request.getParameter("url") %>" />
</form>
<% } %>
</dhv:permission>
<%-- onclick="this.form.bozza.value='false';" --%>
</TD>
</TABLE>
<P class="main">
L'anno <input class="layout" type="text" readonly size="4" value="<%= OrgOperatore.getAnnoReferto() %>"/> 
 addì <input class="layout" type="text" readonly size="2" value="<%= OrgOperatore.getGiornoReferto()%>"/>
 del mese di <input class="layout" type="text" readonly size="10" value="<%= OrgOperatore.getMeseReferto().toUpperCase()%>"/>
alle ore <input class="editField" type="text" name="ore" id="ore" size="3" maxlength="5" value="<%=valoriScelti.get(z++)%>">
i sottoscritti 
<input class="layout" type="text" readonly size="50" value="<%= OrgOperatore.getComponente_nucleo() %>"/> 
<input class="layout" type="text" readonly size="50" value="<%= OrgOperatore.getComponente_nucleo_due() %>"/>  
qualificandosi, si sono presentati presso:<br>
<%-- <% System.out.println("Nome: "+OrgOperatore.getRagione_sociale()); %> --%>
<U><b>Stabilimento/azienda/altro</b>(luogo del controllo):</U> 
Comune di <input class="layout" type="text" readonly size="20" value="<%= (OrgOperatore.getComune() != null ) ? OrgOperatore.getComune().toUpperCase() : "" %>"/> 
alla via <input class="layout" type="text" readonly size="80" value="<%= (OrgOperatore.getIndirizzo() != null ) ? OrgOperatore.getIndirizzo().toUpperCase() : "" %>"/>
 n° <input class="editField" type="text" name="" id="" size="3" maxlength ="6" value="<%=valoriScelti.get(z++)%>"/> 
 ric.CE n° <input class="layout" type="text" readonly size="10" value="<%= (OrgOperatore.getApproval_number() != null) ? OrgOperatore.getApproval_number() : ""%>"/>
regist./cod.az./targa/n.seriale <input class="layout" type="text" readonly size="20" value="<%= (OrgOperatore.getN_reg() != null) ? OrgOperatore.getN_reg() : "" %>"/> 
linea di attività ispezionata <input class="layout" type="text" readonly size="30" value="<%= (OrgOperatore.getTipologia_att() != null ) ? OrgOperatore.getTipologia_att().toUpperCase() : ""%>"/>.<br>
<U><b>Nome/ditta/ragione o denominazione sociale: </b></U>
<input class="layout" type="text" readonly size="50" value="<%= (OrgOperatore.getRagione_sociale() != null) ? OrgOperatore.getRagione_sociale().toUpperCase() : ""%>"/> 
sede legale in <input class="layout" type="text" readonly size="30" value="<%= ((String )OrgOperatore.getSede_legale()) != null ? OrgOperatore.getSede_legale().toUpperCase() : ""%>"/> 
alla via <input class="layout" type="text" readonly size="80" value="<%= ((String )OrgOperatore.getIndirizzo_legale()) != null ? OrgOperatore.getIndirizzo_legale().toUpperCase() : "" %>"/>
 n° <input class="editField" type="text" name="" id="" size="3" maxlength="6" value="<%=valoriScelti.get(z++)%>"/> 
PI/CF <input class="layout" type="text" readonly size="20" value="<%= (String )OrgOperatore.getCodice_fiscale().toUpperCase()%>"/>
 legale rappr. sig. <input class="layout" type="text" readonly size="50" value="<%= ((String )OrgOperatore.getLegale_rapp()) !=null ? OrgOperatore.getLegale_rapp().toUpperCase(): "" %>"/>
nato a <input class="layout" type="text" readonly size="30" value="<%= ((String )OrgOperatore.getLuogo_nascita_rappresentante()) != null ? (String)OrgOperatore.getLuogo_nascita_rappresentante().toUpperCase() :  "" %>"/>
 il 
<input class="layout" type="text" readonly size="4" value="<%= OrgOperatore.getGiornoNascita()%>"/>/
<input class="layout" type="text" readonly size="4" value="<%= OrgOperatore.getMeseNascita()%>"/>/
<input class="layout" type="text" readonly size="4" value="<%= OrgOperatore.getAnnoNascita()%>"/>
e residente in <input class="layout" type="text" readonly size="30" value="<%=(OrgUtente.getResidenza_legale()!="") ? OrgUtente.getResidenza_legale().toUpperCase() : "" %>"/>
 alla via <input class="layout" type="text" readonly size="80" value="<%= (OrgUtente.getIndirizzo_legale()!="") ? OrgUtente.getIndirizzo_legale().toUpperCase() : ""%>"/>
 n° <input class="layout" type="text" readonly size="5" value="<%=(OrgUtente.getNumero_legale()!="") ? OrgUtente.getNumero_legale() : "" %>"/>
 domicilio digitale <input class="layout" type="text" readonly size="30" value="<%= (OrgOperatore.getDomicilioDigitale() != null && !OrgOperatore.getDomicilioDigitale().equals("null")) ? OrgOperatore.getDomicilioDigitale().toUpperCase() : ""%>"/> <br>
<U><b>Presente all'ispezione:</b></U> sig. <input class="layout" type="text" readonly size="50" value="<%=(OrgUtente.getNome_presente_ispezione()!="") ? OrgUtente.getNome_presente_ispezione().toUpperCase() : ""%>"/>
nato a <input class="layout" type="text" readonly size="30" value="<%=(OrgUtente.getLuogo_nascita_presente_ispezione()!="") ? OrgUtente.getLuogo_nascita_presente_ispezione() : ""%>"/>
 il <input class="layout" type="text" readonly size="4" value="<%=(OrgUtente.getGiorno_presente_ispezione()!="") ? OrgUtente.getGiorno_presente_ispezione() : ""%>"/>
 /<input class="layout" type="text" readonly size="4" value="<%=(OrgUtente.getMese_presente_ispezione()!="") ? OrgUtente.getMese_presente_ispezione() : ""%>"/>
 /<input class="layout" type="text" readonly size="8" value="<%=(OrgUtente.getAnno_presente_ispezione()!="") ? OrgUtente.getAnno_presente_ispezione() : ""%>"/>
  e residente in <input class="layout" type="text" readonly size="30" value="<%=(OrgUtente.getLuogo_residenza_presente_ispezione()!="") ? OrgUtente.getLuogo_residenza_presente_ispezione().toUpperCase() : ""%>"/>
  alla via <input class="layout" type="text" readonly size="80" value="<%=(OrgUtente.getVia_ispezione()!="") ? OrgUtente.getVia_ispezione() : ""%>"/>
n° <input class="layout" type="text" readonly size="4" value="<%=(OrgUtente.getNum_civico_presente_ispezione() !="") ? OrgUtente.getNum_civico_presente_ispezione() : ""%>"/>
doc.ident. <input class="layout" type="text" readonly size="30" value="<%=(OrgUtente.getDoc_identita_presente_ispezione() != "") ? OrgUtente.getDoc_identita_presente_ispezione().toUpperCase() : ""%>"/>.<br> 
I sottoscritti hanno proceduto, in regime di asepsi, al prelievo di un campione per l'esame microbiologico delle superfici come appresso specificato: <br>
<TABLE rules="all" cellpadding="10" width="100%" style="border-collapse: collapse"; >
<tr >
	<td style="text-align:center; width:50px; height:50px; border: 1px solid black;">
		<b>Superficie Campionata</b> 
	</td>
	<td style="text-align:center; width:200px; height:50px; border: 1px solid black;">
		<b>Metodo</b> 
	</td>
	<td style="text-align:center; width:200px; height:50px; border: 1px solid black;">
		<b>Fase</b> 
	</td>
	<td style="text-align:center; width:200px; height:50px; border: 1px solid black;">
		<b>cm<sup>2</sup></b> 
	</td>
	<td style="text-align:center; width:200px; height:50px; border: 1px solid black;">
		<b>Ricerca</b> 
	</td>
</tr>
<tr>
	<td style="text-align:left; width:50px; height:100px; border: 1px solid black;">
		1)	
	</td>
		<td style="text-align:left; width:200px; height:100px; border: 1px solid black;">
		<span class="checkedItem"> &nbsp;TAMPONE</span>&nbsp;
		<span class="NocheckedItem"> &nbsp;SPUGNA</span><BR>
		volume diluente <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>">ml<br>
		<span class="NocheckedItem"> &nbsp;PIASTRA A CONTATTO</span><BR>
	</td>
	<td style="text-align:left; width:200px; height:100px; border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Preoperatoria</span><BR>
		<span class="NocheckedItem"> &nbsp;Nel corso delle operazioni</span>
	</td>
	<td style="text-align:left; width:200px; height:100px; border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Non definibile</span><BR>
		<span class="NocheckedItem"> &nbsp;cm<sup>2</sup></span> <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>">	
	</td>
	<td style="text-align:left; width:200px; height:100px; border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Salmonella spp</span>
		<span class="NocheckedItem"> &nbsp;Enterobatteriacee</span><br>
		<span class="NocheckedItem"> &nbsp;Colonie aerob.</span><BR>
		<span class="NocheckedItem"> &nbsp;</span> <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++)%>">
	</td>
</tr>
<tr>
	<td style="text-align:left; width:50px; height:100px; border: 1px solid black;">
	2)	
	</td>
		<td style="text-align:left; width:200px; height:100px; border: 1px solid black;">
		<span class="checkedItem"> &nbsp;TAMPONE</span>&nbsp;
		<span class="NocheckedItem"> &nbsp;SPUGNA</span><BR>
		volume diluente <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>">ml<br>
		<span class="NocheckedItem"> &nbsp;PIASTRA A CONTATTO</span><BR>
	</td>
	<td style="text-align:left; width:200px; height:100px; border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Preoperatoria</span><BR>
		<span class="NocheckedItem"> &nbsp;Nel corso delle operazioni</span>
	</td>
	<td style="text-align:left; width:200px; height:100px; border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Non definibile</span><BR>
		<span class="NocheckedItem"> &nbsp;cm<sup>2</sup></span> <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>">	
	</td>
	<td style="text-align:left; width:200px; height:100px; border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Salmonella spp</span>
		<span class="NocheckedItem"> &nbsp;Enterobatteriacee</span><br>
		<span class="NocheckedItem"> &nbsp;Colonie aerob.</span><BR>
		<span class="NocheckedItem"> &nbsp;</span> <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++)%>">
	</td>
</tr>
<tr>
	<td style="text-align:left; width:50px; height:100px; border: 1px solid black;">	
	3)
	</td>
		<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="checkedItem"> &nbsp;TAMPONE</span>&nbsp;
		<span class="NocheckedItem"> &nbsp;SPUGNA</span><BR>
		volume diluente <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>">ml<br>
		<span class="NocheckedItem"> &nbsp;PIASTRA A CONTATTO</span><BR>
	</td>
	<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Preoperatoria</span><BR>
		<span class="NocheckedItem"> &nbsp;Nel corso delle operazioni</span>
	</td>
	<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Non definibile</span><BR>
		<span class="NocheckedItem"> &nbsp;cm<sup>2</sup></span> <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>">	
	</td>
	<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Salmonella spp</span>
		<span class="NocheckedItem"> &nbsp;Enterobatteriacee</span><br>
		<span class="NocheckedItem"> &nbsp;Colonie aerob.</span><BR>
		<span class="NocheckedItem"> &nbsp;</span> <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++)%>">
	</td>
</tr>
<tr>
	<td style="text-align:left; width:50px; height:100px;border: 1px solid black;">
	4)	
	</td>
		<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="checkedItem"> &nbsp;TAMPONE</span>&nbsp;
		<span class="NocheckedItem"> &nbsp;SPUGNA</span><BR>
		volume diluente  <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>">ml<br>
		<span class="NocheckedItem"> &nbsp;PIASTRA A CONTATTO</span><BR>
	</td>
	<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Preoperatoria</span><BR>
		<span class="NocheckedItem"> &nbsp;Nel corso delle operazioni</span>
	</td>
	<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Non definibile</span><BR>
		<span class="NocheckedItem"> &nbsp;cm<sup>2</sup></span> <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>">		
	</td>
	<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Salmonella spp</span>
		<span class="NocheckedItem"> &nbsp;Enterobatteriacee</span><br>
		<span class="NocheckedItem"> &nbsp;Colonie aerob.</span><BR>
		<span class="NocheckedItem"> &nbsp;</span> <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++)%>">
	</td>
</tr>
<tr>
	<td style="text-align:left; width:50px; height:100px;border: 1px solid black;">
	5)	
	</td>
		<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="checkedItem"> &nbsp;TAMPONE</span>&nbsp;
		<span class="NocheckedItem"> &nbsp;SPUGNA</span><BR>
		volume diluente  <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>">ml<br>
		<span class="NocheckedItem"> &nbsp;PIASTRA A CONTATTO</span><BR>
	</td>
	<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Preoperatoria</span><BR>
		<span class="NocheckedItem"> &nbsp;Nel corso delle operazioni</span>
	</td>
	<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Non definibile</span><BR>
		<span class="NocheckedItem"> &nbsp;cm<sup>2</sup></span> <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>">
	</td>
	<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Salmonella spp</span>
		<span class="NocheckedItem"> &nbsp;Enterobatteriacee</span><br>
		<span class="NocheckedItem"> &nbsp;Colonie aerob.</span><BR>
		<span class="NocheckedItem"> &nbsp;</span> <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++)%>">
	</td>
</tr>
<tr>
	<td style="text-align:left; width:50px; height:100px;border: 1px solid black;">
	6)	
	</td>
		<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="checkedItem"> &nbsp;TAMPONE</span>&nbsp;
		<span class="NocheckedItem"> &nbsp;SPUGNA</span><BR>
		volume diluente  <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>">ml<br>
		<span class="NocheckedItem"> &nbsp;PIASTRA A CONTATTO</span><BR>
	</td>
	<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Preoperatoria</span><BR>
		<span class="NocheckedItem"> &nbsp;Nel corso delle operazioni</span>
	</td>
	<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Non definibile</span><BR>
		<span class="NocheckedItem"> &nbsp;cm<sup>2</sup></span> <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>">	
	</td>
	<td style="text-align:left; width:200px; height:100px;border: 1px solid black;">
		<span class="NocheckedItem"> &nbsp;Salmonella spp</span>
		<span class="NocheckedItem"> &nbsp;Enterobatteriacee</span><br>
		<span class="NocheckedItem"> &nbsp;Colonie aerob.</span><BR>
		<span class="NocheckedItem"> &nbsp;</span> <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++)%>">
	</td>
</tr>
</TABLE>
Le u.c sono state poste in <span class="NocheckedItem"> &nbsp;buste di plastica sterili</span> <span class="NocheckedItem"> &nbsp;recipienti di</span>  <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++)%>"> sterili che vengono sigillati con piombini recanti la dicitura
 <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++)%>"> e muniti di cartellini controfirmati dal presente al campionamento. Esse sono inviate al  <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++)%>">. Le u.c. vengono
conservate e trasferite alla temperatura di  <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++)%>"> °C 
Letto, confermato e sottoscritto, <br>
IL RAPPRESENTANTE DELL'IMPRESA &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; GLI OPERATORI DEL CONTROLLO UFFICIALE 

<div style="page-break-before:always"> 
<!-- INIZIO HEADER -->
	<%@ include file="/tamponi/moduli_html/header.jsp" %>
<!-- FINE HEADER -->

<!-- INIZIO FOOTER -->
	<%@ include file="/tamponi/moduli_html/footer.jsp" %>
<!-- FINE FOOTER -->
</div>
