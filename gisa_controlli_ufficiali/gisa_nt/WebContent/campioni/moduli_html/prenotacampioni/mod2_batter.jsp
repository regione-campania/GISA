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
del mese di <input class="layout" type="text" readonly size="20" value="<%= (OrgOperatore.getMeseReferto() != null ) ? OrgOperatore.getMeseReferto().toUpperCase() : ""%>"/>
alle ore <input class="editField" type="text" name="ore" id="ore" size="5" maxlength="5" value="<%=valoriScelti.get(z++) %>"/>
i sottoscritti 
<label class="layout"><%= (OrgOperatore.getComponente_nucleo() != null) ? OrgOperatore.getComponente_nucleo() : "" %></label>
&nbsp;&nbsp;
<label class="layout"><%= (OrgOperatore.getComponente_nucleo_due() != null) ? OrgOperatore.getComponente_nucleo_due()  : "" %></label>, 
qualificandosi, si sono presentati presso:<br>
<U><b>Stabilimento/azienda/altro</b><i>(luogo del controllo)</i>:</U> 
Comune di <input class="layout" type="text" readonly size="20" value="<%= (OrgOperatore.getComune() != null ) ? OrgOperatore.getComune().toUpperCase() : "" %>"/> 
alla via <input class="layout" type="text" readonly size="80" value="<%= (OrgOperatore.getIndirizzo() != null ) ? OrgOperatore.getIndirizzo().toUpperCase() : "" %>"/>
ric.CE n° <input class="layout" type="text" readonly size="20" value="<%= (OrgOperatore.getApproval_number() != null && OrgOperatore.getApproval_number()!="") ? OrgOperatore.getApproval_number() : ""%>"/>
 regist./cod.az./targa/n.seriale <input class="layout" type="text" readonly size="30" value="<%= (OrgOperatore.getN_reg() != null && OrgOperatore.getN_reg()!="") ? OrgOperatore.getN_reg() : "" %>"/> 
 linea di attività ispezionata <input class="layout" type="text" readonly size="30" value="<%= (OrgOperatore.getTipologia_att() != null && OrgOperatore.getTipologia_att()!="") ? OrgOperatore.getTipologia_att().toUpperCase() : ""%>"/>.<br>
<U><b>Nome/ditta/Ragione/Denominazione sociale: </b></U>
<input class="layout" type="text" readonly size="50" value="<%= (OrgOperatore.getRagione_sociale() != null) ? OrgOperatore.getRagione_sociale() : ""%>"/> <br>
sede legale in <input class="layout" type="text" readonly size="30" value="<%= ((String )OrgOperatore.getSede_legale()) != null ? OrgOperatore.getSede_legale() : ""%>"/>
alla via <input class="layout" type="text" readonly size="80" value="<%= ((String )OrgOperatore.getIndirizzo_legale()) != null ? OrgOperatore.getIndirizzo_legale() : "" %>"/> 
PI/CF <input class="layout" type="text" readonly size="30" value="<%= (OrgOperatore.getCodice_fiscale() != null ) ? OrgOperatore.getCodice_fiscale() : "" %>"/>
legale rappr. sig. <input class="layout" type="text" readonly size="30" value="<%= ((String )OrgOperatore.getLegale_rapp()) !=null ? OrgOperatore.getLegale_rapp(): "" %>"/> 
nato a <input class="layout" type="text" readonly size="30" value="<%= ((String )OrgOperatore.getLuogo_nascita_rappresentante()) != null ? (String)OrgOperatore.getLuogo_nascita_rappresentante() :  "" %>"/> 
il <input class="layout" type="text" readonly size="4" maxlength="2" value="<%= (OrgOperatore.getGiornoNascita() != null && OrgOperatore.getGiornoNascita() != "") ? OrgOperatore.getGiornoNascita() : ""%>"/> /
 <input class="layout" type="text" readonly size="4" maxlength="2" value="<%= (OrgOperatore.getMeseNascita() != null && OrgOperatore.getMeseNascita() != "") ? OrgOperatore.getMeseNascita() : ""%>"/> /
  <input class="layout" type="text" readonly size="8" maxlength="4" value="<%= (OrgOperatore.getAnnoNascita() != null && OrgOperatore.getAnnoNascita() != "") ? OrgOperatore.getAnnoNascita() : "" %>"/> 
 e residente in <input class="editField" type="text"  name="luogo_residenza" id="luogo_residenza"  value="<%=valoriScelti.get(z++) %>" size="30" maxlength="" /> 
 alla via <input class="editField" type="text" name="indirizzo_legale_rappresentante" id="indirizzo_legale_rappresentante" size="80" maxlength=""  value="<%=valoriScelti.get(z++) %>" /> 
 n° <input class="editField" type="text" name="num_civico_rappresentante" id="num_civico_rappresentante" value="<%=valoriScelti.get(z++) %>" size="3" maxlength="6"/> 
 domicilio digitale <input class="editField" type="text" name="domicilio_digitale" id="domicilio_digitale" size="30" maxlength=""  value="<%=valoriScelti.get(z++) %>" /> <br>
<U><b>Presente all'ispezione:</b></U> sig. <input class="editField" type="text" name="nome_presente_ispezione" id="nome_presente_ispezione" value="<%=valoriScelti.get(z++) %>"  size="40" maxlength=""/> 
nato a <input class="editField" type="text" name="luogo_nascita_presente_ispezione" id="luogo_nascita_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="20" maxlength=""/> 
il <input class="editField" type="text" name="giorno_presente_ispezione" id="giorno_presente_ispezione" value="<%=valoriScelti.get(z++) %>" size="4" maxlength="2"/>/
<input class="editField" type="text" name="mese_presente_ispezione" id="mese_presente_ispezione"  value="<%=valoriScelti.get(z++) %>" size="4" maxlength="2"/>/
<input class="editField" type="text" name="anno_presente_ispezione" id="anno_presente_ispezione"  value="<%=valoriScelti.get(z++) %>" size="8" maxlength="4"/> 
e residente in <input class="editField" type="text" name="luogo_residenza_presente_ispezione" id="luogo_residenza_presente_ispezione"  value="<%=valoriScelti.get(z++) %>" size="20" maxlength=""/> 
alla via <input class="editField" type="text" name="via_ispezione" id="via_ispezione" value="<%=valoriScelti.get(z++) %>"  size="80" maxlength=""/>
n° <input class="editField" type="text" name="num_civico_presente_ispezione" id="num_civico_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="6" maxlength="6"/> 
doc.ident. <input class="editField" type="text" name="doc_identita_presente_ispezione" id="doc_identita_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="50" maxlength="" />.<br>  
I sottoscritti hanno proceduto in modo randomizzato al prelievo di un campione di <input class="editField" type="text" size="50" value="<%=valoriScelti.get(z++) %>"  /> 
rappresentativo di una partita di <input class="editField" type="text" id ="rappresentativoPartita"  size="30" value="<%=valoriScelti.get(z++) %>"/>
<!-- <span id = "okSpan" style="border: 1px solid;width: 160px;" >
<input type="checkbox" id="checkbox1" class="css-checkbox lrg" checked="" onclick="validate()" />
<label for="checkbox1" name="checkbox69_lbl" class="css-label lrg vlad">kg</label>
<input type="checkbox" id="checkbox2" class="css-checkbox lrg" checked=""/>
<label for="checkbox2" name="checkbox69_lbl" class="css-label lrg vlad">lt</label>
<input type="checkbox" id="checkbox3" class="css-checkbox lrg" checked=""/>
<label for="checkbox3" name="checkbox69_lbl" class="css-label lrg vlad">unità</label>
<span style="border: 1px solid;">
<input type="checkbox" id="checkbox4" class="css-checkbox lrg" checked=""/>
<label for="checkbox4" name="checkbox69_lbl" class="css-label lrg vlad">confezione</label>
<input type="checkbox" id="checkbox5" class="css-checkbox lrg" checked=""/>
<label for="checkbox5" name="checkbox69_lbl" class="css-label lrg vlad">preincarto</label>
<input type="checkbox" id="checkbox6" class="css-checkbox lrg" checked=""/>
<label for="checkbox6" name="checkbox69_lbl" class="css-label lrg vlad">conf.ne aperta</label>
</span>
 -->
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
nel <input class="editField" type="text" size="10" id="aPartita" value="<%=valoriScelti.get(z++) %>"/> a 
<input class="editField" type="text" id="cPartita" size="3" value="<%=valoriScelti.get(z++) %>"/> °C, con le seguenti indicazioni:<br>
<input class="editField" type="text" size ="100" value="<%=valoriScelti.get(z++)%>"/><br>
Il campione, prelevato in regime di asepsi, è stato effettuato per la 
<span class="bordo" style="border:1px solid black;"> 
<span class="NocheckedItem"> &nbsp;ricerca</span> 
<span class="NocheckedItem"> &nbsp;numerazione</span> di
</span><BR>

<label class="layout"><%= (OrgCampione.getAnaliti() != null) ? OrgCampione.getAnaliti() : ""%></label>
<br> 
quali germi indicatori di:<br>
<span class="NocheckedItem"> &nbsp;<U>criterio di igiene di processo</U>; ai sensi del Reg CE 2073/05 il campione è costituito da n. 
<input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>"/> u.c. del peso di ca.
 <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>"/> cadauna; 
 il prelievo è stato effettuato durante la fase <input class="editField" type="text" size="40" value="<%=valoriScelti.get(z++) %>"/>
</span><br>
<span class="NocheckedItem"> &nbsp;<U>criterio di sicurezza alimentare</U>; ai sensi del comb. disp. del Reg CE 2073/05 e del D.L.vo 123/93, il campione è costituito 
da n. <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>"/> 
aliquote ciascuna costituita da n. <input class="editField" type="text"  size="3" value="<%=valoriScelti.get(z++) %>"/> 
u.c. del peso di ca. <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>"/> cadauna.</span><BR>
<span class="NocheckedItem"> &nbsp;<U>altri parametri microbiologici</U>; 
il campione è costituito da n. <input class="editField" type="text"  size="3" value="<%=valoriScelti.get(z++) %>"/> 
aliquote del peso di ca. <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>"/> cadauna</span>
<span class="bordo" style="border:1px solid black;"> 
<span class="NocheckedItem"> &nbsp;Trattasi di aliq. unica per insufficiente quantità di matrice 
</span> 
<span class="NocheckedItem"> &nbsp;Oltre a quelle di cui sopra, è stata prelevata una ulteriore aliq. per la determinazione del ph e dell'aw.</span>
</span>
Le u.c./aliq. sono poste in <span class="NocheckedItem"> &nbsp;buste di plastica sterili</span> <span class="NocheckedItem"> &nbsp; 
recipienti di <input class="editField" type="text" size="20" value="<%=valoriScelti.get(z++) %>"> sterili</span>.
Le aliq. sono state sigillate con piombino 
recante la dicitura <input class="editField" type="text" size="92" value="<%=valoriScelti.get(z++) %>"/> e munite di cartellini controfirmati dal presente al camp.nto cu
<span class="NocheckedItem"> &nbsp; è stata </span> <span class="NocheckedItem"> &nbsp; non è stata </span>lasciata una di dette aliq. 
Le altre n. <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>"/> 
sono inviate al <input class="editField" type="text" size="30" value="<%=valoriScelti.get(z++) %>" /> 
Le aliq. vengono conservate e trasferite alla temp. di <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>"/> °C.<br>
Il presente verbale è stato redatto in più copie di cui una viene rilasciata al presente al camp.nto che dichiara:<br>
<input class="editField" type="text" size="100" value="<%=valoriScelti.get(z++)%>"><br>
Rintracciabilità merce: proveniente da <input class="editField" type="text" size="20" value="<%=valoriScelti.get(z++) %>"/> 
lotto n. <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>"/> 
DDT N. <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>"/><br>
<span class="NocheckedItem"> &nbsp;Ai sensi dell'art. 223 DLvo 271/89, si comunica che le operaz. di analisi 
inizieranno alle ore <input class="editField" type="text" maxlength="5" size="3" value="<%=valoriScelti.get(z++) %>"> </span> 
del <input class="editField" type="text" maxlength="2" size="4"  value="<%=valoriScelti.get(z++) %>"/>/
<input class="editField" type="text" maxlength="2" size="4" value="<%=valoriScelti.get(z++) %>"/>/
<input class="editField" type="text" class="maxlength="4" size="8" value="<%=valoriScelti.get(z++) %>"/>
<span class="bordo" style="border:1px solid black;"> 
<span class="NocheckedItem"> &nbsp; Si è </span> <span class="NocheckedItem"> &nbsp; Non Si è </span>
</span> proceduto al sequestro della restante merce (verb. n. <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>"/>) Note: 
<br>
<input class="editField" type="text" size="100" value="<%=valoriScelti.get(z++)%>"><br>
Letto, confermato e sottoscritto, <br>
<!-- <input type="checkbox" id="checkbox8" class="css-checkbox lrg" checked=""/>
<label for="checkbox8" name="checkbox69_lbl" class="css-label lrg vlad"><U>criterio di igiene di processo</U></label>
ai sensi del Reg CE 2073/05 il campione è costituito da n. <input type="text" class="editField"/> u.c. del peso di ca. <input type="text" class="editField"/> cadauna; il prelievo è stato effettuato durante la fase <input type="text" class="editField" size="192"/>
<input type="checkbox" id="checkbox9" class="css-checkbox lrg" checked=""/>
<label for="checkbox9" name="checkbox69_lbl" class="css-label lrg vlad"><U>criterio di sicurezza alimentare</U></label> ai sensi del comb. disp. del Reg CE 2073/05 e del D.L.vo 123/93, il campione è costituito
da n. <input type="text" class="editField"/> aliquote ciascuna costituita da n. <input type="text" class="editField"/> u.c. del peso di ca. <input type="text" class="editField"/> cadauna.</span><br>
<input type="checkbox" id="checkbox10" class="css-checkbox lrg" checked=""/>
<label for="checkbox10" name="checkbox69_lbl" class="css-label lrg vlad"><U>altri parametri microbiologici</U></label>; il campione è costituito da n. <input type="text" class="editField"/> aliquote del peso di ca. <input type="text" class="editField"/> cadauna</span>
<span style="border:1px solid;width:560px;">
<input type="checkbox" id="checkbox11" class="css-checkbox lrg" checked=""/>
<label for="checkbox11" name="checkbox69_lbl" class="css-label lrg vlad">Trattasi di aliq. unica per insufficiente quantità di matrice</label>
</span>
<span style="border:1px solid;width:560px;">
<input type="checkbox" id="checkbox12" class="css-checkbox lrg" checked=""/>
<label for="checkbox12" name="checkbox69_lbl" class="css-label lrg vlad">Oltre a quelle di cui sopra, è stata prelevata una ulteriore aliq. per la determinazione del ph e dell'aw.</label>
</span>
Le u.c./aliq. sono poste in 
<span style="border:1px solid;width:560px;">
<input type="checkbox" id="checkbox20" class="css-checkbox lrg" checked=""/>
<label for="checkbox20" name="checkbox69_lbl" class="css-label lrg vlad">buste di plastica sterili</label>
<input type="checkbox" id="checkbox21" class="css-checkbox lrg" checked=""/>
<label for="checkbox21" name="checkbox69_lbl" class="css-label lrg vlad">recipienti di</label><input type="text" class="editField"> sterili
</span>
Le aliq. sono state sigillate con piombino recante la dicitura <input type="text" class="editField" size="78"/> e munite di cartellini controfirmati dal presente al camp.nto cu
<span class="bordo" style="border:1px solid black;">
<input type="checkbox" id="checkbox13" class="css-checkbox lrg" checked=""/>
<label for="checkbox13" name="checkbox69_lbl" class="css-label lrg vlad">è stata</label>
<input type="checkbox" id="checkbox14" class="css-checkbox lrg" checked=""/>
<label for="checkbox14" name="checkbox69_lbl" class="css-label lrg vlad">non è stata</label>
</span> lasciata una di dette aliq. Le altre n. <input type="text" class="editField" /> sono inviate al <input type="text" class="editField" size="93"/> Le aliq. vengono conservate e trasferite alla temp. di <input type="text" class="editField" size="45"/> °C.<br>
Il presente verbale è stato redatto in più copie di cui una viene rilasciata al presente al camp.nto che dichiara:<br>
<textarea rows="4" cols="50" class="textDim" ></textarea><br>
Rintracciabilità merce: proveniente da <input type="text" class="editField" size="94"/> lotto n. <input type="text" class="editField" /> DDT N. <input type="text" class="editField" /><br>
<span class="bordo" style="border:1px solid black;">
<input type="checkbox" id="checkbox15" class="css-checkbox lrg" checked=""/>
<label for="checkbox15" name="checkbox69_lbl" class="css-label lrg vlad">Ai sensi dell'art. 223 DLvo 271/89, si comunica che le operaz. di analisi inizieranno alle ore </label>
</span> 
<input type="text" class="editField" maxlength="5" size="5"> del <input type="text" class="editField" maxlength="2" size="2" />/<input type="text" class="editField" maxlength="2" size="2"/>/<input type="text" class="editField" maxlength="4" size="4"/>
<span class="bordo""> 
<input type="checkbox" id="checkbox16" class="css-checkbox lrg" checked=""/>
<label for="checkbox16" name="checkbox69_lbl" class="css-label lrg vlad">Si è</label>
<input type="checkbox" id="checkbox17" class="css-checkbox lrg" checked=""/>
<label for="checkbox17" name="checkbox69_lbl" class="css-label lrg vlad">Non si è</label>
</span>
proceduto al sequestro della restante merce (verb. n. <input type="text" class="editField" />) Note: 
<br><textarea rows="4" cols="50" class="textDim"></textarea><br>
Letto, confermato e sottoscritto, <br> -->

<P> IL PRESENTE ALL'ISPEZIONE &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; IL LEGALE DI FIDUCIA &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; GLI OPERATORI DEL CONTROLLO UFFICIALE 
<br><br><br>

<div style="page-break-before:always"> 
<!-- INIZIO HEADER -->
	<%@ include file="/campioni/moduli_html/prenotacampioni/header.jsp" %>
<!-- FINE HEADER -->

<!-- INIZIO FOOTER -->
	<%@ include file="/campioni/moduli_html/prenotacampioni/footer.jsp" %>
<!-- FINE FOOTER -->

</div>
