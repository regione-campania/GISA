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

<b>Data prelievo</b>: anno <label class="layout"><%= fixValore( OrgOperatore.getAnnoReferto()) %></label> addì <label class="layout"><%= fixValoreShort(OrgOperatore.getGiornoReferto())%></label> del mese di <label class="layout"><%= fixValore(OrgOperatore.getMeseReferto())%></label> 
<b>Prelevatore</b> dr.  <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("prelevatore").get("prelevatore"))%></label> 
ha proceduto al prelievo di num <label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("num_campioni").get("num_campioni"))%> </label> campioni costituiti da n. 1 pezzo di 

[<%= Modulo.getListaCampiModulo().get("pezzo_prelevato").get("pezzo_prelevato")!=null && Modulo.getListaCampiModulo().get("pezzo_prelevato").get("pezzo_prelevato").equals("1") ? "X" : "" %>] muscolo diaframmatico  
[<%= Modulo.getListaCampiModulo().get("pezzo_prelevato").get("pezzo_prelevato")!=null && Modulo.getListaCampiModulo().get("pezzo_prelevato").get("pezzo_prelevato").equals("2") ? "X" : "" %>] altro

<br/><br/><center>AZIENDA</center><br/><br/>

<b><u>azienda</u></b>: Comune di <label class="layout"><%= fixValoreLong(OrgOperatore.getComune())%></label> alla via <label class="layout"><%= fixValoreLong(OrgOperatore.getIndirizzo())%></label> 
n <label class="layout"><%= fixValoreShort(OrgOperatore.getCivico_indirizzo())%></label> 
cod.az. <label class="layout"><%= fixValore(OrgOperatore.getApproval_number())%></label> <br/>
<b><u>Nome/ditta/ragione/denominazione sociale</u></b>: <label class="layout"><%= fixValoreLong(OrgOperatore.getRagione_sociale())%></label> 
sede legale in <label class="layout"><%= fixValoreLong(OrgOperatore.getSede_legale())%></label>  
alla via <label class="layout"><%= fixValoreLong(OrgOperatore.getIndirizzo_legale())%></label> n. <label class="layout"><%= fixValoreShort(OrgOperatore.getCivico_indirizzo_legale())%></label> 
PI/CF <label class="layout"><%= fixValoreLong(OrgOperatore.getCodice_fiscale())%></label> 
legale rappr. sig. <label class="layout"><%= fixValoreLong(OrgOperatore.getLegale_rapp())%></label> nato a <label class="layout"><%= fixValoreLong(OrgOperatore.getLuogo_nascita_rappresentante())%></label> 
il <label class="layout"><%= fixValoreShort(OrgOperatore.getGiornoNascita())%></label>/<label class="layout"><%= fixValoreShort(OrgOperatore.getMeseNascita())%></label>/<label class="layout"><%= fixValoreShort(OrgOperatore.getAnnoNascita())%></label> 
e residente in <label class="layout"><%= fixValoreLong(OrgOperatore.getCitta_legale_rapp())%></label> 
alla via <label class="layout"><%= fixValoreLong(OrgOperatore.getIndirizzo_legale_rapp())%></label> n° <label class="layout"><%= fixValoreShort(OrgOperatore.getCivico_indirizzo_legale_rapp())%></label> 
domicilio digitale <label class="layout"><%= fixValoreLong(OrgOperatore.getDomicilio_digitale_legale_rapp())%></label>
 
<br/><br/><center>PRESSO PRIVATO </center><br/>
<b><u>Proprietario</u></b> sig. <label class="layout"><%= fixValoreLong("")%></label> nato a <label class="layout"><%= fixValoreLong("")%></label>
il <label class="layout"><%= fixValoreShort("")%></label> e residente in <label class="layout"><%= fixValoreLong("")%></label>
alla via <label class="layout"><%= fixValoreLong("")%></label> n° <label class="layout"><%= fixValoreShort("")%></label>
domicilio digitale <label class="layout"><%= fixValoreLong("")%></label>
<br/><center>AL MACELLO</center><br/>
<b><u>Stabilimento</u></b>: Comune di<label class="layout"><%= fixValoreLong("")%></label>
alla via <label class="layout"><%= fixValoreLong("")%></label> n <label class="layout"><%= fixValoreShort("")%></label> 
ric. CE n° <label class="layout"><%= fixValore("")%></label> <b><u>ditta/ragione/denominazione sociale</u></b>: <label class="layout"><%= fixValoreLong("")%></label>
sede legale in <label class="layout"><%= fixValoreLong("")%></label> alla via <label class="layout"><%= fixValoreLong("")%></label> 
n. <label class="layout"><%= fixValoreShort("")%></label> PI/CF <label class="layout"><%= fixValore("")%></label> 
legale rappr. sig. <label class="layout"><%= fixValoreLong("")%></label> nato a <label class="layout"><%= fixValoreLong("")%></label>
il _<label class="layout"><%= fixValoreShort("")%></label> e residente in <label class="layout"><%= fixValoreLong("")%></label>
alla via <label class="layout"><%= fixValoreLong("")%></label> n° <label class="layout"><%= fixValoreShort("")%></label> 
domicilio digitale <label class="layout"><%= fixValoreLong("")%></label> 
<br/><br/>

<b>A)</b>	Proprietario sig. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("a_proprietario").get("a_proprietario"))%></label> cod.az. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("a_cod_azienda").get("a_cod_azienda"))%></label>  n. capi <label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("a_num_capi").get("a_num_capi"))%></label><br/>
<b>B)</b>	Proprietario sig. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("b_proprietario").get("b_proprietario"))%></label> cod.az. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("b_cod_azienda").get("b_cod_azienda"))%></label>  n. suidi <label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("b_num_capi").get("b_num_capi"))%></label><br/>
<b>C)</b>	Proprietario sig. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("c_proprietario").get("c_proprietario"))%></label> cod.az. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("c_cod_azienda").get("c_cod_azienda"))%></label>  n. suidi <label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("c_num_capi").get("c_num_capi"))%></label><br/>
<b>D)</b>	Proprietario sig. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("d_proprietario").get("d_proprietario"))%></label> cod.az. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("d_cod_azienda").get("d_cod_azienda"))%></label>  n. suidi <label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("d_num_capi").get("d_num_capi"))%></label><br/>
<b>E)</b>	Proprietario sig. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("e_proprietario").get("e_proprietario"))%></label> cod.az. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("e_cod_azienda").get("e_cod_azienda"))%></label>  n. suidi <label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("e_num_capi").get("e_num_capi"))%></label><br/>
<b>F)</b>	Proprietario sig. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("f_proprietario").get("f_proprietario"))%></label> cod.az. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("f_cod_azienda").get("f_cod_azienda"))%></label>  n. suidi <label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("f_num_capi").get("f_num_capi"))%></label><br/>


<br/><br/>


<U><b>Presente all'ispezione:</b></U> sig. <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("nome_presente_ispezione").get("nome_presente_ispezione"))%> </label> nato a <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("luogo_nascita_presente_ispezione").get("luogo_nascita_presente_ispezione"))%> </label> il <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("data_nascita_presente_ispezione").get("data_nascita_presente_ispezione"))%> </label>  e residente in <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("comune_residenza_presente_ispezione").get("comune_residenza_presente_ispezione"))%> </label> alla via <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("indirizzo_residenza_presente_ispezione").get("indirizzo_residenza_presente_ispezione"))%> </label><br/>
n° <label class="layout"><%= fixValoreShort(Modulo.getListaCampiModulo().get("num_indirizzo_residenza_presente_ispezione").get("num_indirizzo_residenza_presente_ispezione"))%> </label>  doc.ident. <label class="layout"><%= fixValoreLong(Modulo.getListaCampiModulo().get("doc_ident_presente_ispezione").get("doc_ident_presente_ispezione"))%> </label>
 <br/> 

Le u.c. sono poste in buste di plastica identificate. Il presente verbale è stato redatto in più copie di cui una viene rilasciata al presente al camp.nto, che è stato reso edotto che le carni, in attesa dell'esito, non possono essere consumate ma possono essere lavorate. <br/>
Note: 
<br>
<label class="layout"><%= fixValore(ticketDetails.getProblem())%></label>  <br>
Letto, confermato e sottoscritto, <br>

<P> IL PRESENTE ALL'ISPEZIONE &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  GLI OPERATORI DEL CONTROLLO UFFICIALE 


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
