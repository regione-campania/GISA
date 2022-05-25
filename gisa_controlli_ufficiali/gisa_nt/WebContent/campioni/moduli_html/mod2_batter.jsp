<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%int z=0; %>

<!-- INIT DOCUMENTALE -->
	<%@ include file="/gestione_documenti/initDocumentale.jsp" %>
<!-- FINE INIT DOCUMENTALE -->

<!-- INIZIO HEADER -->
	<%@ include file="/campioni/moduli_html/header.jsp" %>
<!-- FINE HEADER -->

<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<table><tr>
<TD>
<div id="idbutn" style="display:block;">
<%-- <input type="button" class = "buttonClass" value ="Salva in modalità definitiva" onclick="this.form.bozza.value='false';javascript:checkSubmit();"/>
--%>
</div>
<input id="stampaId" type="button" class = "buttonClass"  style="display:none" value ="Stampa" onclick="javascript:if( confirm('Attenzione! Controlla bene tutti i dati inseriti in quanto alla chiusura della finestra, i dati saranno persi.\nVuoi effettuare la stampa?')){return window.print();}else return false;"/>
<input type="hidden" id = "bozza" name = "bozza" value="">

<%-- onclick="this.form.bozza.value='false';" --%>

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
L'anno <label class="layout"><%= fixValore( OrgOperatore.getAnnoReferto()) %></label> addì <label class="layout"><%= fixValoreShort(OrgOperatore.getGiornoReferto())%></label> del mese di <label class="layout"><%= fixValore(OrgOperatore.getMeseReferto())%></label> alle ore <label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("ora_controllo").get("ora_controllo"))%> </label> i sottoscritti 

<% if (OrgOperatore.getComponente_nucleo()!=null) {%> <label class="layout"><%= fixValoreLong(OrgOperatore.getComponente_nucleo()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_due()!=null) {%> <label class="layout"><%= fixValoreLong(OrgOperatore.getComponente_nucleo_due()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_tre()!=null) {%> <label class="layout"><%= fixValoreLong(OrgOperatore.getComponente_nucleo_tre()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_quattro()!=null) {%> <label class="layout"><%= fixValoreLong(OrgOperatore.getComponente_nucleo_quattro()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_cinque()!=null) {%> <label class="layout"><%= fixValoreLong(OrgOperatore.getComponente_nucleo_cinque()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_sei()!=null) {%> <label class="layout"><%= fixValoreLong(OrgOperatore.getComponente_nucleo_sei()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_sette()!=null) {%> <label class="layout"><%= fixValoreLong(OrgOperatore.getComponente_nucleo_sette()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_otto()!=null) {%> <label class="layout"><%= fixValoreLong(OrgOperatore.getComponente_nucleo_otto()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_nove()!=null) {%> <label class="layout"><%= fixValoreLong(OrgOperatore.getComponente_nucleo_nove()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_dieci()!=null) {%> <label class="layout"><%= fixValoreLong(OrgOperatore.getComponente_nucleo_dieci()) %></label> <%} %> 

, qualificandosi, si sono presentati presso:<br>

<U><b>Stabilimento/azienda/altro</b><i>(luogo del controllo)</i>:</U> Comune di <label class="layout"><%= fixValoreLong(OrgOperatore.getComune())%></label> alla via <label class="layout"><%= fixValoreLong(OrgOperatore.getIndirizzo()) %></label> ric.CE n° <label class="layout"><%= fixValoreLong(OrgOperatore.getApproval_number())%></label> regist./cod.az./targa/n.seriale <label class="layout"><%= fixValoreLong(OrgOperatore.getN_reg()) %></label> linea di attività ispezionata <label class="layout"><%= fixValoreLong(OrgOperatore.getTipologia_att())%></label>.<br>
<U><b>Nome/ditta/Ragione/Denominazione sociale: </b></U> <label class="layout"><%= fixValoreLong(OrgOperatore.getRagione_sociale())%></label> <br> sede legale/residente in <label class="layout"><%= fixValoreLong(OrgOperatore.getSede_legale())%></label> alla via <label class="layout"><%= fixValoreLong(OrgOperatore.getIndirizzo_legale()) %></label> PI/CF <label class="layout"><%= fixValoreLong(OrgOperatore.getCodice_fiscale()) %></label> domicilio digitale <label class="layout">____________</label><br/>
<U><b>Presente all'ispezione:</b></U> sig. <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("nome_presente_ispezione").get("nome_presente_ispezione"))%> </label> (dati identificativi contenuti nel Mod 5 agli atti)
 hanno prelevato un campione di <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("campione_prelevato").get("campione_prelevato"))%> </label> 
rappresentativo di una partita di <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("rappresentativo_partita").get("rappresentativo_partita"))%> </label>
<span class="bordo" style="border:1px solid black;"> 
[<%= fixValore(Modulo.getListaCampiModulo().get("unita_partita").get("unita_partita")).equals("2") ? "X" : ""%>] KG 
[<%= fixValore(Modulo.getListaCampiModulo().get("unita_partita").get("unita_partita")).equals("3") ? "X" : ""%>] LT
[<%= fixValore(Modulo.getListaCampiModulo().get("unita_partita").get("unita_partita")).equals("4") ? "X" : ""%>] UNITA 
</span>
 detenuta nel <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("detenuta_in").get("detenuta_in"))%> </label>
a 
<label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("gradi").get("gradi"))%> </label> °C
in
<span class="bordo" style="border:1px solid black;"> 
[<%= fixValore(Modulo.getListaCampiModulo().get("contenitore_unita_partita").get("contenitore_unita_partita")).equals("2") ? "X" : ""%>] CONFEZIONE INTEGRA 
[<%= fixValore(Modulo.getListaCampiModulo().get("contenitore_unita_partita").get("contenitore_unita_partita")).equals("3") ? "X" : ""%>] PREINCARTO
[<%= fixValore(Modulo.getListaCampiModulo().get("contenitore_unita_partita").get("contenitore_unita_partita")).equals("4") ? "X" : ""%>] CONFEZIONE APERTA 
[<%= fixValore(Modulo.getListaCampiModulo().get("contenitore_unita_partita").get("contenitore_unita_partita")).equals("5") ? "X" : ""%>] ALLO STATO SFUSO 
</span>
 con le seguenti indicazioni:<br>
<label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("indicazioni").get("indicazioni"))%> </label><br/>

Trattasi di alimento R.T.E.? 
[<%= fixValore(Modulo.getListaCampiModulo().get("rte").get("rte")).equals("2") ? "X" : ""%>] SI
[<%= fixValore(Modulo.getListaCampiModulo().get("rte").get("rte")).equals("3") ? "X" : ""%>] NO
--	
Il latte utilizzato come materia prima è pastorizzato?  
[<%= fixValore(Modulo.getListaCampiModulo().get("latte_pastorizzato").get("latte_pastorizzato")).equals("2") ? "X" : ""%>] SI
[<%= fixValore(Modulo.getListaCampiModulo().get("latte_pastorizzato").get("latte_pastorizzato")).equals("3") ? "X" : ""%>] NO
[<%= fixValore(Modulo.getListaCampiModulo().get("latte_pastorizzato").get("latte_pastorizzato")).equals("4") ? "X" : ""%>] NON SO


<br/>
Nazione dello stabilimento di produzione <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("nazione").get("nazione"))%> </label> Zona FAO (per prod.pesca) <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("fao").get("fao"))%> </label> 

Trattamenti subiti dalla merce campionata <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("trattamenti").get("trattamenti"))%> </label><br/>
Il campione e' stato prelevato secondo le norme specifiche in materia per la 
[<%= fixValore(Modulo.getListaCampiModulo().get("motivo_prelievo").get("motivo_prelievo")).equals("2") ? "X" : ""%>] RICERCA DI <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("ricerca_descrizione").get("ricerca_descrizione"))%> </label>
[<%= fixValore(Modulo.getListaCampiModulo().get("motivo_prelievo").get("motivo_prelievo")).equals("3") ? "X" : ""%>] NUMERAZIONE DI <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("numerazione_descrizione").get("numerazione_descrizione"))%> </label>

<br/>
quale:	
[<%= fixValore(Modulo.getListaCampiModulo().get("tipologia_prelievo").get("tipologia_prelievo")).equals("2") ? "X" : ""%>] CRITERIO DI IGIENE DI PROCESSO
[<%= fixValore(Modulo.getListaCampiModulo().get("tipologia_prelievo").get("tipologia_prelievo")).equals("3") ? "X" : ""%>] CRITERIO DI SICUREZZA ALIMENTARE
[<%= fixValore(Modulo.getListaCampiModulo().get("tipologia_prelievo").get("tipologia_prelievo")).equals("4") ? "X" : ""%>] ALTRI PARAMETRI MICROBIOLOGICI

<br/>
Il codice foodex della matrice prelevata è attribuito automaticamente dal Sistema informatico GISA. Il campione è stato prelevato durante la fase <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("fase").get("fase"))%> </label> .<br/>
Ai sensi del combinato disposto del Reg. CE 2073/05 e del punto 2 dell'art. 7 del D.L.vo 27/21, essendo una analisi microbiologica, viene esclusa la possibilità di ripetizione dell'analisi in quanto inopportuna e non pertinente, e pertanto il campione è costituito da n. 1 aliquota formata da n. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("sicurezza_numero_uc").get("sicurezza_numero_uc"))%> </label> unita' campionarie del peso di ca. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("sicurezza_peso").get("sicurezza_peso"))%> </label> cadauna.<br/>
Le unita' campionarie sono poste in contenitori 
[<%= fixValore(Modulo.getListaCampiModulo().get("uc_contenitore").get("uc_contenitore")).equals("2") ? "X" : ""%>] DI PLASTICA
[<%= fixValore(Modulo.getListaCampiModulo().get("uc_contenitore").get("uc_contenitore")).equals("3") ? "X" : ""%>] DI VETRO
 sterili. L'aliquota e' sigillata con piombino recante la dicitura <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("dicitura").get("dicitura"))%> </label> e munita di cartellino, ovvero posta in busta autosigillante antimanomissione, controfirmato/a dal presente al campionamento. L'aliquota viene conservata e inviata al laboratorio del <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("inviate").get("inviate"))%> </label> 	alla temp. di  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("temperatura_aliq").get("temperatura_aliq"))%> </label> °C. <br/>
Il  presente  verbale e' redatto  in  n. 3 copie  di  cui  una  viene  rilasciata  al  presente  al  campionamento  che  dichiara <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("dichiarazione").get("dichiarazione"))%> </label><br/>
[<%= fixValore(Modulo.getListaCampiModulo().get("proceduto_sequestro").get("proceduto_sequestro")).equals("2") ? "X" : ""%>] SI E'
[<%= fixValore(Modulo.getListaCampiModulo().get("proceduto_sequestro").get("proceduto_sequestro")).equals("3") ? "X" : ""%>] NON SI E'
 proceduto al sequestro della restante merce con verbale di sequestro n <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("verbale").get("verbale"))%> </label><br/>
Note: <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("note").get("note"))%> </label><br/><br/>

Letto, confermato e sottoscritto <br/>
IL PRESENTE ALL'ISPEZIONE &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;	GLI OPERATORI DEL CONTROLLO UFFICIALE  		



<div style="page-break-before:always"> 
<!-- INIZIO HEADER -->
	<%@ include file="/campioni/moduli_html/header.jsp" %>
<!-- FINE HEADER -->

<!-- INIZIO FOOTER -->
	<%@ include file="/campioni/moduli_html/footer.jsp" %>
<!-- FINE FOOTER -->

</div>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>
