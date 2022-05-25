<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%int z = 0; %>

<!-- INIT DOCUMENTALE -->
	<%@ include file="/gestione_documenti/initDocumentale.jsp" %>
<!-- FINE INIT DOCUMENTALE -->

<!-- INIZIO HEADER -->
	<%@ include file="/campioni/moduli_html/prenotacampioni/header.jsp" %>
<!-- FINE HEADER -->

<div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div>

<table><tr>
<TD>
<div id="idbutn" style="display:block;">
<%-- <input type="button" class = "buttonClass" value ="Salva in modalità definitiva" onclick="this.form.bozza.value='false';javascript:checkSubmit();"/>
--%>
</div>
<input id="stampaId" type="button" class = "buttonClass"  style="display:none" value ="Stampa" onclick="javascript:if( confirm('Attenzione! Controlla bene tutti i dati inseriti in quanto alla chiusura della finestra, i dati saranno persi.\nVuoi effettuare la stampa?')){return window.print();}else return false;"/>
<input type="hidden" id = "bozza" name = "bozza" value="">

<%-- onclick="this.form.bozza.value='false';" --%>
<br>

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
<input id="stampaPdfId" type="button" class = "buttonClass" value ="Genera e Stampa PDF" onclick="if (confirm ('Nella prossima schermata sarà possibile recuperare l\'ultimo PDF generato a partire dal documento a schermo, \n oppure generarne uno nuovo.')){javascript:salva(this.form)}"/>
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
</TD>
</TABLE>
<P class="main">
L'anno <input class="layout" type="text" readonly size="4" value="<%= ( OrgOperatore.getAnnoReferto() != null ) ? OrgOperatore.getAnnoReferto() : "" %>"/>
addì <input class="layout" type="text" readonly size="2" value="<%= (OrgOperatore.getGiornoReferto() != null ) ? OrgOperatore.getGiornoReferto() : ""%>"/>
del mese di <input class="layout" type="text" readonly size="10" value="<%= (OrgOperatore.getMeseReferto() != null ) ? OrgOperatore.getMeseReferto().toUpperCase() : ""%>"/> 
alle ore <input class="editField" type="text" name="ore" id="ore" size="2" maxlength="5" value="<%=valoriScelti.get(z++) %>"/>
i sottoscritti 
<label class="layout"><%= (OrgOperatore.getComponente_nucleo() != null) ? OrgOperatore.getComponente_nucleo() : "____________" %></label>
&nbsp;&nbsp; 
<label class="layout"><%= (OrgOperatore.getComponente_nucleo_due() != null) ? OrgOperatore.getComponente_nucleo_due() : "_____________" %></label>,  
<br>qualificandosi, si sono presentati presso:<br>
<%-- <% System.out.println("Nome: "+OrgOperatore.getRagione_sociale()); %> --%>
<U><b>Stabilimento/azienda/altro</b>(luogo del controllo):</U> 
Comune di <input class="layout" type="text" readonly size="20" value="<%= (OrgOperatore.getComune() != null ) ? OrgOperatore.getComune().toUpperCase() : "" %>"/>
alla via <input class="layout" type="text" readonly size="80" value="<%= (OrgOperatore.getIndirizzo() != null ) ? OrgOperatore.getIndirizzo().toUpperCase() : "" %>"/>
 ric.CE n° <input class="layout" type="text" readonly size="20" value="<%= (OrgOperatore.getApproval_number() != null && OrgOperatore.getApproval_number() != "") ? OrgOperatore.getApproval_number() : ""%>"/> <br>
regist./cod.az./targa/n.seriale <input class="layout" type="text" readonly size="30" value ="<%= (OrgOperatore.getN_reg() != null) ? OrgOperatore.getN_reg() : "" %>"/>
 linea di attività ispezionata <input class="layout" type="text" readonly size="30" value="<%= (OrgOperatore.getTipologia_att() != null ) ? OrgOperatore.getTipologia_att() : ""%>"/>.<br>
<U><b>Nome/ditta/Ragione/Denominazione sociale: </b></U>
<input class="layout" type="text" readonly name="ragione_sociale" id="ragione_sociale" size="50" value="<%= (OrgOperatore.getRagione_sociale() != null) ? OrgOperatore.getRagione_sociale().toUpperCase() : ""%>"/> 
sede legale in <input class="layout" type="text" readonly size="30" value="<%= ((String )OrgOperatore.getSede_legale()) != null ? OrgOperatore.getSede_legale() : ""%>"/>
alla via <input class="layout" type="text" readonly size="80" value="<%= ((String )OrgOperatore.getIndirizzo_legale()) != null ? OrgOperatore.getIndirizzo_legale().toUpperCase() : "" %>"/>  
PI/CF <input class="layout" type="text" readonly size="40" value="<%= (OrgOperatore.getCodice_fiscale() != null ) ? OrgOperatore.getCodice_fiscale() : "" %>"/> legale rappr. sig. 
<input class="layout" type="text" readonly size="50" value="<%= ((String )OrgOperatore.getLegale_rapp()) !=null ? OrgOperatore.getLegale_rapp().toUpperCase() : "" %>"/> 
nato a <input class="layout" type="text" readonly size="20" value="<%= ((String )OrgOperatore.getLuogo_nascita_rappresentante()) != null ? (String)OrgOperatore.getLuogo_nascita_rappresentante().toUpperCase() :  "" %>">
 il 
<input class="layout" type="text" readonly size="4" value="<%= (OrgOperatore.getGiornoNascita() != null) ? OrgOperatore.getGiornoNascita() : ""%>"/>
/<input class="layout" type="text" readonly size="4" value="<%= (OrgOperatore.getMeseNascita() != null) ? OrgOperatore.getMeseNascita().toUpperCase() : ""%>"/>
/<input class="layout" type="text" readonly size="8" value="<%= (OrgOperatore.getAnnoNascita() != null) ? OrgOperatore.getAnnoNascita() : "" %>"/>
 e residente in <input class="editField" type="text" name="luogo_residenza" id="luogo_residenza"   value="<%=valoriScelti.get(z++) %>"  size="30" maxlength="" /> 
 alla via <input class="editField" type="text"  name="indirizzo_legale_rappresentante" id="indirizzo_legale_rappresentante" size="80" maxlength=""   value="<%=valoriScelti.get(z++) %>"  /> 
 n° <input class="editField" type="text"  name="num_civico_rappresentante" id="num_civico_rappresentante"   value="<%=valoriScelti.get(z++) %>"  size="3" maxlength="6"/> 
 domicilio digitale <input class="editField" type="text" name="domicilio_digitale" id="domicilio_digitale" size="30" maxlength=""   value="<%=valoriScelti.get(z++) %>"  /> <br>
<U><b>Presente all'ispezione:</b></U> sig. <input class="editField" type="text" name="nome_presente_ispezione" id="nome_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="30" maxlength=""/> 
nato a <input class="editField" type="text"  name="luogo_nascita_presente_ispezione" id="luogo_nascita_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="30" maxlength=""/> 
il <input class="editField" type="text" name="giorno_presente_ispezione" id="giorno_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="4" maxlength="2"/>/
<input class="editField" type="text" name="mese_presente_ispezione" id="mese_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="4" maxlength="2"/>/
<input class="editField" type="text" name="anno_presente_ispezione" id="anno_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="8" maxlength="4"/> 
e residente in <input class="editField" type="text" name="luogo_residenza_presente_ispezione" id="luogo_residenza_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="20" maxlength=""/> 
alla via <input class="editField" type="text" name="via_ispezione" id="via_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="80" maxlength=""/>
n° <input class="editField" type="text"  name="num_civico_presente_ispezione" id="num_civico_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="3" maxlength="6"/> 
doc.ident. <input class="editField" type="text" name="doc_identita_presente_ispezione" id="doc_identita_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="50" maxlength="" />.<br>  
I sottoscritti hanno proceduto in modo randomizzato e secondo le procedure previste dalla norma <input class="editField" type="text" size="10"  value="<%=valoriScelti.get(z++) %>" /> al prelievo di un campione di <input class="layout" type="text" readonly size="20" value="<%= (OrgCampione.getMatrice() != null) ? OrgCampione.getMatrice() : "" %>"/>
 rappresentativo di una partita di <input class="editField" type="text" size="10"   value="<%=valoriScelti.get(z++) %>" />
<span class="bordo" style="border:1px solid black;">
<span class="NocheckedItem"> &nbsp;kg</span> 
<span class="NocheckedItem"> &nbsp;lt</span> 
<span class="NocheckedItem"> &nbsp;unità</span>
</span>
detenuta in 
<span class="bordo" style="border:1px solid black;">
<span class="NocheckedItem"> &nbsp;confezione</span> 
<span class="NocheckedItem"> &nbsp;preincarto</span> 
<span class="NocheckedItem"> &nbsp;confez.ne aperta</span> 
<span class="NocheckedItem"> &nbsp;allo stato sfuso</span> 
</span>
nel <input class="editField" type="text"size="5"  value="<%=valoriScelti.get(z++) %>" /> a
 <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>" /> °C, con le seguenti indicazioni:<br>
 <input class="editField" type="text" size="100"  value="<%=valoriScelti.get(z++) %>" /><br>
Il campione, effettuato per la ricerca di per la ricerca di
<label class="layout"><%= (OrgCampione.getAnaliti() != null) ? OrgCampione.getAnaliti() : ""%></label> 
 è costituito da n. <input class="editField" type="text" size="5"  value="<%=valoriScelti.get(z++) %>" /> aliquote ciascuna costituita da n. 
<input class="editField" type="text" size="5"   value="<%=valoriScelti.get(z++) %>" /> u.c. del peso di ca.
<input class="editField"  type="text" size="5"   value="<%=valoriScelti.get(z++) %>" /> cadauna poste in 
<span class="bordo" style="border:1px solid black;">
<span class="NocheckedItem"> &nbsp;buste di plastica per alimenti </span>
<span class="NocheckedItem"> &nbsp;recipienti di vetro </span>
</span>
e sigillate con piombino recante la dicitura <input class="editField" type="text"  size="10"   value="<%=valoriScelti.get(z++) %>" > e munite di cartellini controfirmati dal presente al campion.nto cui
<span class="bordo" style="border:1px solid black;">
<span class="NocheckedItem"> &nbsp; è stata</span>
<span class="NocheckedItem"> &nbsp; non è stata</span>
</span> 
lasciata una di dette aliquote. Le altre n. <input class="editField" type="text"size="5"  value="<%=valoriScelti.get(z++) %>" /> sono inviate al <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++) %>" /> per la ricerca di:<br>
<input class="layout" type="text" readonly size="20" value="<%= ( OrgCampione.getAnaliti() != null) ? OrgCampione.getAnaliti() : ""%>"/><br>  
<span class="NocheckedItem"> &nbsp;Poichè le aliquote sono insufficienti a garantire il diritto alla difesa, all'op.tore verrà 
comunicato l'ora ed il giorno delle analisi.</span> 
Le aliquote vengono conservate e trasferite alla temp. di <input class="editField" type="text" size="2" value="<%=valoriScelti.get(z++) %>" /> °C. <br> 
Il presente verbale è stato redatto in più copie di cui una viene rilasciata al presente 
al camp.nto che dichiara:<br>
<input class="editField" type ="text" size="100" value="<%=valoriScelti.get(z++)%>"><br>
Rintracciabilità merce: proveniente da <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++) %>" /> lotto n. <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>" /> DDT N. <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>" /> <span class="NocheckedItem"> &nbsp; Si è </span> <span class="NocheckedItem"> &nbsp; Non Si è </span> proceduto al sequestro della restante merce (verb. n. <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>" />) Note:<br>
<input class="editField" type ="text"size="100" value="<%=valoriScelti.get(z++)%>"><br>
Letto, confermato e sottoscritto, <br>
IL PRESENTE ALL'ISPEZIONE &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; IL LEGALE DI FIDUCIA &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; GLI OPERATORI DEL CONTROLLO UFFICIALE 

<div style="page-break-before:always"> 
<!-- INIZIO HEADER -->
	<%@ include file="/campioni/moduli_html/prenotacampioni/header.jsp" %>
<!-- FINE HEADER -->

<!-- INIZIO FOOTER -->
	<%@ include file="/campioni/moduli_html/prenotacampioni/footer.jsp" %>
<!-- FINE FOOTER -->

</div>
