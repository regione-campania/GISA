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
	<%@ include file="/campioni/moduli_html/header_fisico.jsp" %>
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
<P class="main" style="line-height:20px;">
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
<U><b>Nome/ditta/Ragione/Denominazione sociale: </b></U> <label class="layout"><%= fixValoreLong(OrgOperatore.getRagione_sociale())%></label> <br> sede legale in <label class="layout"><%= fixValoreLong(OrgOperatore.getSede_legale())%></label> alla via <label class="layout"><%= fixValoreLong(OrgOperatore.getIndirizzo_legale()) %></label> PI/CF <label class="layout"><%= fixValoreLong(OrgOperatore.getCodice_fiscale()) %></label> legale rappr. sig. <label class="layout"><%= fixValoreLong(OrgOperatore.getLegale_rapp()) %></label> nato a <label class="layout"><%= fixValoreLong(OrgOperatore.getLuogo_nascita_rappresentante()) %></label> il <label class="layout"><%= fixValoreShort(OrgOperatore.getGiornoNascita())%></label>/<label class="layout"><%= fixValoreShort(OrgOperatore.getMeseNascita())%></label>/<label class="layout"><%= fixValoreShort(OrgOperatore.getAnnoNascita())%></label> e residente in <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("comune_residenza_rapp_legale").get("comune_residenza_rapp_legale"))%> </label> alla via <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("indirizzo_residenza_rapp_legale").get("indirizzo_residenza_rapp_legale"))%> </label> n° <label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("num_indirizzo_residenza_rapp_legale").get("num_indirizzo_residenza_rapp_legale"))%> </label> 
domicilio digitale <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("domicilio_digitale_rapp_legale").get("domicilio_digitale_rapp_legale"))%> </label><br/>
<U><b>Presente all'ispezione:</b></U> sig. <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("nome_presente_ispezione").get("nome_presente_ispezione"))%> </label> nato a <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("luogo_nascita_presente_ispezione").get("luogo_nascita_presente_ispezione"))%> </label> il <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("data_nascita_presente_ispezione").get("data_nascita_presente_ispezione"))%> </label>  e residente in <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("comune_residenza_presente_ispezione").get("comune_residenza_presente_ispezione"))%> </label> alla via <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("indirizzo_residenza_presente_ispezione").get("indirizzo_residenza_presente_ispezione"))%> </label><br/>
n° <label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("num_indirizzo_residenza_presente_ispezione").get("num_indirizzo_residenza_presente_ispezione"))%> </label>  doc.ident. <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("doc_ident_presente_ispezione").get("doc_ident_presente_ispezione"))%> </label>
 <br/> 
dove in modo randomizzato e con le procedure stabilite da 
<u> <%for(int num_spazi=0; num_spazi < 30; num_spazi++) { %> &nbsp; <% } %> </u>
da una partita di <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("rappresentativo_partita").get("rappresentativo_partita"))%> </label>
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
nel <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("detenuta_in").get("detenuta_in"))%> </label> 
alla temp. di <label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("gradi").get("gradi"))%> </label> 
hanno proceduto al prelievo di un campione di
<label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("campione_prelevato").get("campione_prelevato"))%> </label>
cod NSIS
<u> <%for(int num_spazi=0; num_spazi < 10; num_spazi++) { %> &nbsp; <% } %> </u>
riportando le seguenti indicazioni:
<u> <%for(int num_spazi=0; num_spazi < 1150; num_spazi++) { %> &nbsp; <% } %> </u>
Provenienza dell'ingred.nte principale:
<u> <%for(int num_spazi=0; num_spazi < 30; num_spazi++) { %> &nbsp; <% } %> </u><br>
Il prodotto ha un fattore di conversione di 
<u> <%for(int num_spazi=0; num_spazi < 15; num_spazi++) { %> &nbsp; <% } %> </u>
Il campione &egrave; costituito da n.
<u> <%for(int num_spazi=0; num_spazi < 5; num_spazi++) { %>	&nbsp; <% } %> </u>
aliquote ciascuna costituita da n.
<u> <%for(int num_spazi=0; num_spazi < 5; num_spazi++) { %>	&nbsp; <% } %> </u>
 u.c. del peso di ca. 
<u> <%for(int num_spazi=0; num_spazi < 15; num_spazi++) { %> &nbsp; <% } %> </u>
cadauna poste in contenitori &nbsp;  
<span class="bordo" style="border:1px solid black;"> 
<span class="NocheckedItem"> &nbsp;di plastica</span>
<span class="NocheckedItem"> &nbsp;di vetro</span>
</span> 
 &nbsp; e sigillate con piombino recante la dicitura 
 <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("dicitura").get("dicitura"))%> </label>
 e munite di cartellini controfirmati dal presente al campionamento cui &nbsp; 
<span class="bordo" style="border:1px solid black;">
<span class="NocheckedItem"> &nbsp; è stata </span> 
<span class="NocheckedItem"> &nbsp; non è stata </span>
</span>
&nbsp; lasciata una di dette aliquote.<br>  
Le altre n. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("altre_num").get("altre_num"))%> </label> 
sono inviate al <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("inviate").get("inviate"))%> </label>
per la ricerca di <label class="layout"><%= fixValoreLong(OrgCampione.getAnaliti())%></label><br>
<span class="bordo" style="border:1px solid black;">
<span class="NocheckedItem"> &nbsp; Poich&egrave; aliq.te sono insufficienti a garantire 
il diritto alla difesa, all'OSA verrà comunicato l'ora ed il giorno delle analisi</span> 
</span><br>
Le aliquote vengono conservate e trasferite alla temp. di
 <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("temperatura_aliq").get("temperatura_aliq"))%> </label> °C.
 Il pres. verbale è stato redatto in più copie di cui una viene rilasciata al presente al campionamento che dichiara
<u> <%for(int num_spazi=0; num_spazi < 400; num_spazi++) { %> &nbsp; <% } %> </u>
<span class="bordo" style="border:1px solid black;"> 
<span class="NocheckedItem"> &nbsp; Si è 
</span> <span class="NocheckedItem"> &nbsp; Non Si è </span>
</span> &nbsp; proceduto al sequestro della restante merce 
(verb. n. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("verbale").get("verbale"))%> </label>) 
Note: <u> <%for(int num_spazi=0; num_spazi < 400; num_spazi++) { %> &nbsp; <% } %> </u>
Letto, confermato e sottoscritto<br><br><br>
ESAME FISICO<br>
FISICO<br>
<ul>
	<li>QUALITA' ALIMENTI ZOOTECNICI
		<ul style="list-style: none;">
			<li><span class="NocheckedItem"> &nbsp; CORPI ESTRANEI</span></li>
			<li><span class="NocheckedItem"> &nbsp; FIBRA GREZZA</span></li>
		</ul>
	</li>
	<li>QUALITA' ALIMENTO
		<ul style="list-style: none;">
			<li><span class="NocheckedItem"> &nbsp; CARATTERI ORGANOLETTICI</span></li>
			<li><span class="NocheckedItem"> &nbsp; CORPI ESTRANEI - IMPURITA' NELL'ALIMENTO</span></li>
			<li><span class="NocheckedItem"> &nbsp; PERCENTUALE DI GLASSATURA</span></li>
			<li><span class="NocheckedItem"> &nbsp; PESO NETTO</span></li>
			<li><span class="NocheckedItem"> &nbsp; PESO SPECIFICO</span></li>
			<li><span class="NocheckedItem"> &nbsp; SOLIDI NEL LATTE</span></li>
		</ul>
	</li>
	<li>RADIOATTIVITA'
		<ul style="list-style: none;">
			<li><span class="NocheckedItem"> &nbsp; RADIAZIONI IONIZZANTI</span></li>
			<li><span class="NocheckedItem"> &nbsp; RADIONUCLIDI</span></li>
			<li><span class="NocheckedItem"> &nbsp; RADIONUCLIDI: CESIO 137</span></li>
			<li><span class="NocheckedItem"> &nbsp; RADIONUCLIDI: RADON</span></li>
			<li><span class="NocheckedItem"> &nbsp; RADIONUCLIDI: TRIZIO</span></li>
		</ul>
	</li>
</ul>
</P>
<P> IL PRESENTE ALL'ISPEZIONE</P> <br>
<div align="right">
<P>
GLI OPERATORI DEL CONTROLLO UFFICIALE 
</P>
</div>
<div style="page-break-before:always"> 
<!-- INIZIO HEADER -->
	<%@ include file="/campioni/moduli_html/header_fisico.jsp" %>
<!-- FINE HEADER -->

<!-- INIZIO APPEDICE VIG -->
<br/><br/><br/><br/>
<%@ include file="/campioni/moduli_html/appendice_vig.jsp" %>
<!-- FINE APPEDICE VIG -->

<!-- INIZIO FOOTER -->
	<%@ include file="/campioni/moduli_html/footer.jsp" %>
<!-- FINE FOOTER -->

</div>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>

