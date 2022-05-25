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
	<%@ include file="/campioni/moduli_html/header.jsp" %>
<!-- FINE HEADER -->

<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<table><tr>
<TD>
<div id="idbutn" style="display:block;">
<%-- <input type="button" class = "buttonClass" value ="Salva in modalità definitiva" onclick="this.form.bozza.value='false';javascript:checkSubmit();"/>
--%>
</div>


<input id="stampaId" type="button" class = "buttonClass"  style="display:none" value ="Stampa" onClick="window.print();"/>
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
<%-- onclick="this.form.bozza.value='false';" --%>
</TD>
</TABLE>
<P class="main">L'anno <label class="layout"><%= fixValore(OrgOperatore.getAnnoReferto())%></label> 
addì <label class="layout"><%=fixValore(OrgOperatore.getGiornoReferto()) %></label>
del mese di <label class="layout"><%= fixValore(OrgOperatore.getMeseReferto().toUpperCase())%></label> 
alle ore 
<label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("ora_controllo").get("ora_controllo"))%> </label>
i sottoscritti 

<% if (OrgOperatore.getComponente_nucleo()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_due()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_due()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_tre()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_tre()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_quattro()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_quattro()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_cinque()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_cinque()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_sei()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_sei()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_sette()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_sette()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_otto()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_otto()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_nove()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_nove()) %></label> <%} %> 
<% if (OrgOperatore.getComponente_nucleo_dieci()!=null) {%> <label class="layout"><%= fixValore(OrgOperatore.getComponente_nucleo_dieci()) %></label> <%} %> 


si sono recati nell'area di produzione sede di: 
<% if(OrgOperatore.isChecked(1)) { %>
   <span class="checkedItem"> &nbsp;banco naturale</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;banco naturale</span>
<% } %> 
<% if(OrgOperatore.isChecked(2)) { %>
   <span class="checkedItem"> &nbsp;impianto molluschicoltura</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;impianto molluschicoltura</span>
<% } %>
<% if(OrgOperatore.isChecked(3)) { %>
   <span class="checkedItem"> &nbsp;zona di stabulazione</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;zona di stabulazione</span>
<% } %>
<% if(OrgOperatore.isChecked(5)) { %>
   <span class="checkedItem"> &nbsp;specchio acqueo da classificare</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;specchio acqueo da classificare</span>
<% } %>
<% if(OrgOperatore.isChecked(5)) { %>
   <span class="checkedItem"> &nbsp;impianto abusivo</span>
<% } else { %>
  <span class="NocheckedItem"> &nbsp;impianto abusivo</span>
<% } %>
classificato come classe 
<label class="layout"><%= fixValore(OrgOperatore.getClasse())%></label> 
per la produzione di 
 <br/>
<label class="layout"><%=fixValore(OrgOperatore.getSpecieMolluschi())%></label>
<br/>

sita nel Comune di <label class="layout"><%= fixValore(OrgOperatore.getComune())%></label> 
località <label class="layout"><%= fixValore(OrgOperatore.getRagione_sociale())%></label>.<br> 
codice univoco nazionale <label class="layout"><%= fixValore(OrgOperatore.getCun())%></label>.<br> 
<%-- <U><b>Nome/ditta/Ragione/Denominazione sociale: </b></U>
<label class="layout"><%= fixValore(OrgOperatore.getRagione_sociale())%></label> <br>
sede legale in <label class="layout"><%= fixValore(OrgOperatore.getSede_legale())%></label>
alla via <label class="layout"><%= fixValore(OrgOperatore.getIndirizzo_legale())%></label> 
PI/CF <label class="layout"><%= fixValore(OrgOperatore.getCodice_fiscale()) %></label>
legale rappr. sig. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("nome_rapp_legale").get("nome_rapp_legale"))%></label> 
nato a <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("luogo_nascita_rapp_legale").get("luogo_nascita_rapp_legale"))%></label> 
il <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("data_nascita_rapp_legale").get("data_nascita_rapp_legale"))%></label>  
 e residente in 
 <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("comune_residenza_rapp_legale").get("comune_residenza_rapp_legale"))%> </label> 
 alla via 
  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("indirizzo_residenza_rapp_legale").get("indirizzo_residenza_rapp_legale"))%> </label>
 n° 
  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_indirizzo_residenza_rapp_legale").get("num_indirizzo_residenza_rapp_legale"))%> </label>
  domicilio digitale 
   <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("domicilio_digitale_rapp_legale").get("domicilio_digitale_rapp_legale"))%> </label>
   <br/>
<!--  -->
--%>
<U><b>Presente all'ispezione:</b></U> 
 sig. 
<label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("nome_presente_ispezione").get("nome_presente_ispezione"))%> </label>
 nato a 
<label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("luogo_nascita_presente_ispezione").get("luogo_nascita_presente_ispezione"))%> </label>
 il
 <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("data_nascita_presente_ispezione").get("data_nascita_presente_ispezione"))%> </label> 
 e residente in 
 <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("comune_residenza_presente_ispezione").get("comune_residenza_presente_ispezione"))%> </label>
 alla via 
  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("indirizzo_residenza_presente_ispezione").get("indirizzo_residenza_presente_ispezione"))%> </label>
  <br/>
n° 
 <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_indirizzo_residenza_presente_ispezione").get("num_indirizzo_residenza_presente_ispezione"))%> </label> 
doc.ident. 
 <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("doc_ident_presente_ispezione").get("doc_ident_presente_ispezione"))%> </label>
 <br/> 
Si è proceduto <!--  <span class="NocheckedItem"> &nbsp;con l'ausilio tecnico dell'impresa</span> <span class="NocheckedItem"> &nbsp;-->
con l'ausilio di</span>  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("ausilio").get("ausilio"))%> </label>
al prelievo di un campione di  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("campione_prelevato").get("campione_prelevato"))%> </label>
costituito da n.  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("aliquote").get("aliquote"))%> </label>
unit&agrave; campionarie del peso di ca.  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("peso_aliquote").get("peso_aliquote"))%> </label> kg cadauna.<br>
<span class="NocheckedItem"> &nbsp;L'aliquota unica è costituita da un pool di molluschi prelevati nei punti di cui alle seguenti coordinate geografiche: </span><br>
<span class="NocheckedItem"> &nbsp;
Le n. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_uc").get("num_uc"))%> </label> u.c., ognuna contrassegnata con lettere<span class="NocheckedItem"> &nbsp;A <span class="NocheckedItem"> &nbsp;B <span class="NocheckedItem"> &nbsp;C <span class="NocheckedItem"> &nbsp;D <span class="NocheckedItem"> &nbsp;E
alla <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("lettera").get("lettera"))%> </label>, sono costituite da un pool di molluschi prelevati nei punti di cui alle seguenti coordinate geografiche:<br>

<br/>
<% 

int i=-1;
	int count = 0;
	Integer s = ticketDetails.getListaCoordinateCampione().size();
	if(s != null ){
		int size = s;
	 	if(size > 0){%>
	 	
	 	COORDINATE PUNTI DI PRELIEVO <br/>
	 	<%
			for(int j = 0; j < size; j++) {
			%>
		  <b><%=ticketDetails.getListaCoordinateCampione().get(j).getIdentificativo() %>)</b> LAT <label class="layout"><%=ticketDetails.getListaCoordinateCampione().get(j).getLatitude()%></label> LONG <label class="layout"><%=ticketDetails.getListaCoordinateCampione().get(j).getLongitude()%></label><br/>
		  	<% } 
		 } 
	 	} else { %>
	 	LAT <label class="layout"></label>
		LONG <label class="layout"></label>
		<% } %>
		<br/><br/>
	Ciascuna aliquota/u.c. è costituita da esemplari raccolti <span class="NocheckedItem"> &nbsp;a diversi livelli di profondità </span> <span class="NocheckedItem"> &nbsp;sul fondale. </span> Gli esemplari costituenti le aliquote/u.c. sono stati posti in buste di plastica per alimenti sigillate con piombino recante la dicitura 
<label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("dicitura").get("dicitura"))%> </label> e munite di cartellini identificativi controfirmati dal rappresentante dell'impresa. Ai fini dell'analisi del rischio si riportano le condizioni meteo-marine al momento del prelievo: <b>cielo: </b> sereno/coperto/pioggia; <b>mare: </b> piatto/leggermente mosso/mosso; <b>vento: </b> assente/leggera brezza/teso;
<b>proveniente da 
<label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("proveniente").get("proveniente"))%> </label>; 
corrente marina presumibilmente proveniente da <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("corrente_proveniente").get("corrente_proveniente"))%> </label>; 
temperatura dell'aria: <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("temperatura_aria").get("temperatura_aria"))%> </label>; 
temperatura dell'acqua in superficie: <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("temperatura_acqua").get("temperatura_acqua"))%> </label>;
temperatura dell'acqua a 10 metri di profondità: <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("temperatura_acqua_10m").get("temperatura_acqua_10m"))%></label>;
data ultima mareggiata <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("data_mareggiata").get("data_mareggiata"))%></label>
data ultima pioggia <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("data_pioggia").get("data_pioggia"))%></label> ; di intensità </b> fine/consistente/abbondante; <b> 
salinità dell'acqua <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("salinita").get("salinita"))%></label>.</b>
Le aliquote vengono conservate e trasferite in contenitori isotermici. Il campione è inviato all'I.Z.S.M. Sezione di <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("sezione").get("sezione"))%></label>
per la ricerca di <label class="layout"><%= fixValore(OrgCampione.getAnaliti())%></label> 
<%-- per la ricerca di: <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("ricerca").get("ricerca"))%></label>--%> 
da eseguirsi su ogni singola aliquota/u.c.<br>
Note: <%--input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="120" /--%>
<label class="layout"><%= fixValore(ticketDetails.getProblem())%></label>  <br>
Letto, confermato e sottoscritto, 
<br>
<P> IL PRESENTE ALL'ISPEZIONE &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; IL LEGALE DI FIDUCIA &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; GLI OPERATORI DEL CONTROLLO UFFICIALE 

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