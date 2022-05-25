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


<input id="stampaId" type="button" class = "buttonClass"  style="display:none" value ="Stampa" onClick="window.print();"/>
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
<P class="main">L'anno <input class="layout" type="text" readonly value="<%= OrgOperatore.getAnnoReferto()%>" size="4"/> 
addì <input class="layout" type="text" readonly value="<%=OrgOperatore.getGiornoReferto() %>" size="2" />
del mese di <input class="layout" type="text" readonly value="<%= OrgOperatore.getMeseReferto().toUpperCase()%>" size="10" /> 
alle ore <input class="editField" type="text" name="ore" id="ore" value="<%=valoriScelti.get(z++) %>"  size="5" maxlength="5"/>
i sottoscritti <label class="layout"><%= OrgOperatore.getComponente_nucleo()%></label> &nbsp; &nbsp;  
 <label class="layout"><%= (OrgOperatore.getComponente_nucleo_due() != null) ? OrgOperatore.getComponente_nucleo_due() : "" %></label> 
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
<input class="layout" type="text" readonly value="<%= (OrgOperatore.getClasse() != null) ? OrgOperatore.getClasse().toUpperCase(): ""%>" size="30" /> 
per la produzione di 
 <br/>
<label class="layout"><%= (OrgOperatore.getSpecieMolluschi() != null) ? OrgOperatore.getSpecieMolluschi().toUpperCase() : "" %></label>
<br/>

sita nel Comune di <input class="layout" type="text" readonly value="<%= (OrgOperatore.getComune() != null) ? OrgOperatore.getComune().toUpperCase(): ""%>" size="40"/> 
località <input class="layout" type="text" readonly value="<%= (OrgOperatore.getRagione_sociale() != null) ? OrgOperatore.getRagione_sociale().toUpperCase() : ""%>" size="40"/>.<br> 
<U><b>Nome/ditta/Ragione/Denominazione sociale: </b></U>
<input class="layout" type="text" readonly value="<%= (OrgOperatore.getRagione_sociale() != null) ? OrgOperatore.getRagione_sociale().toUpperCase() : ""%>" size="50"/> <br>
sede legale in <input class="layout" type="text" readonly value="<%= ((String )OrgOperatore.getSede_legale()) != null ? OrgOperatore.getSede_legale() : ""%>" size="30"/>
alla via <input class="layout" type="text" readonly value="<%= ((String )OrgOperatore.getIndirizzo_legale()) != null ? OrgOperatore.getIndirizzo_legale() : "" %>" size="80"/> 
PI/CF <input class="layout" type="text" readonly value="<%= (OrgOperatore.getCodice_fiscale() != null ) ? OrgOperatore.getCodice_fiscale() : "" %>" size="30"/>
legale rappr. sig. <input class="layout" type="text" readonly value="<%= ((String )OrgOperatore.getLegale_rapp()) !=null ? OrgOperatore.getLegale_rapp(): "" %>" size="30"/> 
nato a <input class="layout" type="text" readonly value="<%= ((String )OrgOperatore.getLuogo_nascita_rappresentante()) != null ? (String)OrgOperatore.getLuogo_nascita_rappresentante() :  "" %>" size="30"/> 
il <input class="layout" type="text" readonly maxlength="2" value="<%= (OrgOperatore.getGiornoNascita() != null && OrgOperatore.getGiornoNascita() != "") ? OrgOperatore.getGiornoNascita() : ""%>" size="4"/> /
 <input class="layout" type="text" readonly maxlength="2" value="<%= (OrgOperatore.getMeseNascita() != null && OrgOperatore.getMeseNascita() != "") ? OrgOperatore.getMeseNascita() : ""%>" size="4"/> /
  <input class="layout" type="text" readonly maxlength="4" value="<%= (OrgOperatore.getAnnoNascita() != null && OrgOperatore.getAnnoNascita() != "") ? OrgOperatore.getAnnoNascita() : "" %>" size="8"/> 
 e residente in <input class="editField" type="text"  name="luogo_residenza" id="luogo_residenza"  value="<%=valoriScelti.get(z++) %>" size="30" maxlength="" /> 
 alla via <input class="editField" type="text" name="indirizzo_legale_rappresentante" id="indirizzo_legale_rappresentante" maxlength=""  value="<%=valoriScelti.get(z++) %>"  size="80"/> 
 n° <input class="editField" type="text" name="num_civico_rappresentante" id="num_civico_rappresentante" value="<%=valoriScelti.get(z++) %>" size="3" maxlength="6"/> 
 domicilio digitale <input class="editField" type="text" name="domicilio_digitale" id="domicilio_digitale" maxlength=""  value="<%=valoriScelti.get(z++) %>"  size="30"/> <br>
<!--  -->
<U><b>Presente all'ispezione:</b></U> 
sig. <input class="editField" type="text" name="nome_presente_ispezione" id="nome_presente_ispezione" value="<%=valoriScelti.get(z++) %>"  size="50" maxlength=""/> 
nato a <input class="editField" type="text" name="luogo_nascita_presente_ispezione" id="luogo_nascita_presente_ispezione"  value="<%=valoriScelti.get(z++) %>"  size="30" maxlength=""/> il 
<input class="editField" type="text" name="giorno_presente_ispezione" id="giorno_presente_ispezione"  value="<%=valoriScelti.get(z++) %>"  size="4" maxlength="2"/>/
<input class="editField" type="text" name="mese_presente_ispezione" id="mese_presente_ispezione"  value="<%=valoriScelti.get(z++) %>"  size="4" maxlength="2"/>/
<input class="editField" type="text"  name="anno_presente_ispezione" id="anno_presente_ispezione"  value="<%=valoriScelti.get(z++) %>" size="8" maxlength="4"/> 
e residente in <input class="editField" type="text" name="luogo_residenza_presente_ispezione" id="luogo_residenza_presente_ispezione"  value="<%=valoriScelti.get(z++) %>"  size="20" maxlength=""/> 
alla via <input class="editField" type="text" name="via_ispezione" id="via_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="80" maxlength=""/> <br>
n° <input class="editField" type="text" name="num_civico_presente_ispezione" id="num_civico_presente_ispezione" value="<%=valoriScelti.get(z++) %>" size="6" maxlength="6"/> 
doc.ident. <input class="editField" type="text" name="doc_identita_presente_ispezione" id="doc_identita_presente_ispezione"  value="<%=valoriScelti.get(z++) %>"  size="50" maxlength="" />.<br> 
Si è proceduto <span class="NocheckedItem"> &nbsp;con l'ausilio tecnico dell'impresa</span> <span class="NocheckedItem"> &nbsp;
con l'ausilio di</span> <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="30" /> 
al prelievo di un campione di <input class="editField" type="text"  value="<%=valoriScelti.get(z++) %>" size="30" /> 
costituito da n. <input class="editField" type="text" size="5" value="<%=valoriScelti.get(z++) %>" /> 
aliquote del peso di ca. <input class="editField" type="text" size="5" value="<%=valoriScelti.get(z++) %>"  /> kg cadauna.<br>
<span class="NocheckedItem"> &nbsp;L'aliquota unica è costituita da un pool di molluschi prelevati nei punti di cui alle seguenti coordinate geografiche: </span><br>
<span class="NocheckedItem"> &nbsp;
Le n. <input class="editField" type="text" size="4" value="<%=valoriScelti.get(z++) %>" /> u.c., ognuna contrassegnata con lettere dalla A) 
alla <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="10"/>, sono costituite da un pool di molluschi prelevati nei punti di cui alle seguenti coordinate geografiche:<br>
<% int i=-1;
	int count = 0;
	Integer s = ticketDetails.getListaCoordinateCampione().size();
	if(s != null ){
		int size = s;
	 	if(size > 0){
			for(int j = 0; j < size/2; j++) {
			%>
		  	 <%=++count%>)lat <label class="layout"><%=ticketDetails.getListaCoordinateCampione().get(j).getLatitudine()%></label>
		  	 		long <label class="layout"><%=ticketDetails.getListaCoordinateCampione().get(j).getLongitudine()%></label>
		  	 		<br>
		  	<% } 
		 } 
	 	} else { %>
	 	lat <label class="layout"></label>
		long <label class="layout"></label>
		<br>
	 	<% } %>
	Ciascuna aliquota/u.c. è costituita da esemplari raccolti <span class="NocheckedItem"> &nbsp;a diversi livelli di profondità </span> <span class="NocheckedItem"> &nbsp;sul fondale. </span> Gli esemplari costituenti le aliquote/u.c. sono stati posti in buste di plastica per alimenti sigillate con piombino recante la dicitura 
<input class="editField" type="text" size="30" value="<%=valoriScelti.get(z++) %>" /> e munite di cartellini identificativi controfirmati dal rappresentante dell'impresa. Ai fini dell'analisi del rischio si riportano le condizioni meteo-marine al momento del prelievo: <b>cielo: </b> sereno/coperto/pioggia; <b>mare: </b> piatto/leggermente mosso/mosso; <b>vento: </b> assente/leggera brezza/teso;
<b>proveniente da 
<input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="10"/>; 
corrente marina presumibilmente proveniente da <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++) %>" />; 
temperatura dell'aria: <input class="editField" type="text" size="10" value="<%=valoriScelti.get(z++) %>" />; 
temperatura dell'acqua in superficie: <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="10"/>;
temperatura dell'acqua a 10 metri di profondità: <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="10"/>;
data ultima mareggiata <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>"  size="4"/>/
<input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="4"/>/
<input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="8"/> 
data ultima pioggia <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="4"/>/
<input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="4"/>/
<input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="8" />; di intensità </b> fine/consistente/abbondante; <b> 
salinità dell'acqua <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" />.</b>
Le aliquote vengono conservate e trasferite in contenitori isotermici. Il campione è inviato all'I.Z.S.M. per la ricerca di
<label class="layout"><%= (OrgCampione.getAnaliti() != null) ? OrgCampione.getAnaliti() : ""%></label> 
Sezione di <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="30" /> 
per la ricerca di: <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="50"/> da eseguirsi su ogni singola aliquota/u.c.<br>
Note: <input class="editField" type="text" value="<%=valoriScelti.get(z++) %>" size="120" /> <br>
Letto, confermato e sottoscritto, 
<br>
<P> IL PRESENTE ALL'ISPEZIONE &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; IL LEGALE DI FIDUCIA &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; GLI OPERATORI DEL CONTROLLO UFFICIALE 

<div style="page-break-before:always"> 
<!-- INIZIO HEADER -->
	<%@ include file="/campioni/moduli_html/prenotacampioni/header.jsp" %>
<!-- FINE HEADER -->

<!-- INIZIO FOOTER -->
	<%@ include file="/campioni/moduli_html/prenotacampioni/footer.jsp" %>
<!-- FINE FOOTER -->


</div>
