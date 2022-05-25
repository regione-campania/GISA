<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="Allevamento" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Ticket" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="DomandeList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="specie" class="java.lang.String" scope="request"/>
<jsp:useBean id="codice_specie" class="java.lang.String" scope="request"/>
<jsp:useBean id="ChecklistIstanza" class="org.aspcf.modules.checklist_benessere.base.v4.ChecklistIstanza_Generica" scope="request"/>

<%@page import="org.aspcf.modules.checklist_benessere.base.*"%>

<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="controlliufficiali/allegati/v4/css/screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="controlliufficiali/allegati/v4/css/print.css" />

<%@ include file="../../../../initPage.jsp" %>

<%@ include file="js/funzioni.jsp" %>

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div>


<form method="post" name="myform" action="PrintModulesHTML.do?command=InsertChecklistBenessere&auto-populate=true">

<center>
<b>PROTEZIONE DEGLI ANIMALI IN ALLEVAMENTO - GALLINE OVAIOLE</b><br/>
RISULTATI DEI CONTROLLI EFFETTUATI PRESSO LE AZIENDE<br/>
(D.Lgs. 146/2001 - D.Lgs. 267/2003 e succ. modifiche)<br/>
DECISIONE DELLA COMMISSIONE 2006/778/CE del 14 novembre 2006<br/>
</center> 
    
<div class="header">  
<b>REGIONE</b>  <label class="layout">CAMPANIA</label><br/>
<b>ASL</b> <label class="layout"><%=Allevamento.getAsl()%></label> <BR/>
<b>DISTRETTO</b>  <label class="layout">________________________</label> <br/>
<b>EXTRAPIANO</b> <input type="radio" id="extrapiano_SI" name="extrapiano" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getExtrapiano()).equals("S")) ? "checked=\"checked\"" : ""%>/> SI <input type="radio" id="extrapiano_NO" name="extrapiano" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getExtrapiano()).equals("N")) ? "checked=\"checked\"" : ""%>/> NO <br/>
</div>

<table cellpadding="10" width="100%">
<tr><td>
Codice azienda  <label class="layout"><%=Allevamento.getN_reg()%></label> Codice fiscale <label class="layout"><%=Allevamento.getCodice_fiscale()%></label><br/>
Denominazione  <label class="layout"><%=Allevamento.getName()%></label><br/>
Indirizzo e numero civico  <label class="layout"><%=Allevamento.getIndirizzo()%></label> <br/>
Comune <label class="layout"><%=toHtml(Allevamento.getComune())%></label> Provincia <label class="layout"><%=toHtml(Allevamento.getProv())%></label> <br/>
Proprietario <label class="layout"><%=(Allevamento.getProp()!=null && !Allevamento.getProp().equalsIgnoreCase("null")) ? Allevamento.getProp() : "___________"%></label><br/>
Codice fiscale <label class="layout"><%=Allevamento.getCf_prop()%></label> Tel. <label class="layout">___________________</label><br/>
Detentore <label class="layout"><%=Allevamento.getDet()%></label><br/>
Codice fiscale <label class="layout"><%=Allevamento.getCf_det()%></label> Tel. <label class="layout">___________________</label><br/>
Appartenente al Campione Condizionalita'? <input type="radio" id="appartenenteCondizionalita_SI" name="appartenenteCondizionalita" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getAppartenenteCondizionalita()).equals("S")) ? "checked=\"checked\"" : ""%>/> SI <input type="radio" id="appartenenteCondizionalita_NO" name="appartenenteCondizionalita" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getAppartenenteCondizionalita()).equals("N")) ? "checked=\"checked\"" : ""%>/> NO <br/>
Fase produttiva <input type="radio" id="flagFaseProduttiva_-" name="flagFaseProduttiva" value="" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getFlagFaseProduttiva()).equals("")) ? "checked=\"checked\"" : ""%>/> N.D.  <input type="radio" id="flagFaseProduttiva_D" name="flagFaseProduttiva" value="D" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getFlagFaseProduttiva()).equals("D")) ? "checked=\"checked\"" : ""%>/> Fase deposizione <input type="radio" id="flagFaseProduttiva_P" name="flagFaseProduttiva" value="P" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getFlagFaseProduttiva()).equals("P")) ? "checked=\"checked\"" : ""%>/> Fase pollastra <br/> 
 
</td></tr>
</table>

<div style="page-break-before:always">&nbsp; </div>  
<table cellpadding="10" width="100%">

<tr><th>Selezionare i criteri utilizzati per la selezione dell'allevamento sottoposto a controllo:</th></tr>

<tr><td>
<input type="radio" id="altreIndagini" name="criteriUtilizzati" value="002" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getCriteriUtilizzati()).equals("002")) ? "checked=\"checked\"" : ""%>/> Altre indagini degli organi di polizia giudiziaria<br/>
<input type="radio" id="cambiamentiSituazione" name="criteriUtilizzati" value="003" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getCriteriUtilizzati()).equals("003")) ? "checked=\"checked\"" : ""%>/> Cambiamenti della situazione aziendale<br/>
<input type="radio" id="comunicazioneDati" name="criteriUtilizzati" value="004" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getCriteriUtilizzati()).equals("004")) ? "checked=\"checked\"" : ""%>/> Comunicazione dei dati dell'azienda all'Autorita' competente<br/>
<input type="radio" id="implicazioniSalute" name="criteriUtilizzati" value="005" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getCriteriUtilizzati()).equals("005")) ? "checked=\"checked\"" : ""%>/> Implicazioni per la salute umana e animale, precedenti focolai<br/>
<input type="radio" id="indagineIgiene" name="criteriUtilizzati" value="006" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getCriteriUtilizzati()).equals("006")) ? "checked=\"checked\"" : ""%>/> Indagine relativa all'igiene degli allevamenti<br/>
<input type="radio" id="indagineFrodi" name="criteriUtilizzati" value="007" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getCriteriUtilizzati()).equals("007")) ? "checked=\"checked\"" : ""%>/> Indagine relativa alle frodi comunitarie<br/>
<input type="radio" id="infrazioni" name="criteriUtilizzati" value="008" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getCriteriUtilizzati()).equals("008")) ? "checked=\"checked\"" : ""%>/> Infrazioni riscontrate negli anni precedenti<br/>
<input type="radio" id="numeroAnimali" name="criteriUtilizzati" value="009" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getCriteriUtilizzati()).equals("009")) ? "checked=\"checked\"" : ""%>/> Numero di animali<br/>
<input type="radio" id="segnalazioneIrregolarita" name="criteriUtilizzati" value="011" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getCriteriUtilizzati()).equals("011")) ? "checked=\"checked\"" : ""%>/> Segnalazione di irregolarita' da impianto di macellazione<br/>
<input type="radio" id="variazioniEntita" name="criteriUtilizzati" value="012" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getCriteriUtilizzati()).equals("012")) ? "checked=\"checked\"" : ""%>/> Variazioni dell'entita' dei premi<br/>
<input type="radio" id="altroCriterio" name="criteriUtilizzati" value="997" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getCriteriUtilizzati()).equals("997")) ? "checked=\"checked\"" : ""%>/> Altro criterio di rischio ritenuto rilevante dall'Autorita' competente, indicare quale (*)<br/>
<input type="radio" id="random" name="criteriUtilizzati" value="999" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getCriteriUtilizzati()).equals("999")) ? "checked=\"checked\"" : ""%>/> Casuale (Random)<br/>

<b>(*)Altro criterio di rischio ritenuto rilevante dall'AC. Indicare quale:</b><br/>
<input type="text" id="criteriUtilizzatiAltroDescrizione" name="criteriUtilizzatiAltroDescrizione" class="editField" size="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getCriteriUtilizzatiAltroDescrizione()) : ""%>"/> 

</td></tr>

<tr><td>
Numero capi presenti in BDN (sulla base delle registrazioni effettuate nel sistema): <input type="number" id="numCapiPresenti" name="numCapiPresenti" style="width: 4em" class="editField" value="<%=(ChecklistIstanza!=null && ChecklistIstanza.getNumCapiPresenti()!=null) ? toHtml(ChecklistIstanza.getNumCapiPresenti()) : ""%>" size="4"/><br/>
n. uova anno <input type="number" id="numUovaAnno" name="numUovaAnno" style="width: 4em" class="editField" value="<%=(ChecklistIstanza!=null && ChecklistIstanza.getNumUovaAnno()!=null) ? toHtml(ChecklistIstanza.getNumUovaAnno()) : ""%>" size="4"/>
Selezione/imballaggio presso l'allevamento <input type="radio" id="selezioneImballaggio_SI" name="selezioneImballaggio" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getSelezioneImballaggio()).equals("S")) ? "checked=\"checked\"" : ""%>/> SI <input type="radio" id="selezioneImballaggio_NO" name="selezioneImballaggio" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getSelezioneImballaggio()).equals("N")) ? "checked=\"checked\"" : ""%>/> NO   indicare la destinazione <input type="text" id="destinazioneImballaggio" name="destinazioneImballaggio" class="editField" size="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getDestinazioneImballaggio()) : ""%>"/><br/> 
Numero totale capannoni <input type="number" id="numCapannoni" name="numCapannoni" style="width: 4em" class="editField" value="<%=(ChecklistIstanza!=null && ChecklistIstanza.getNumCapannoni()!=null) ? toHtml(ChecklistIstanza.getNumCapannoni()) : ""%>" size="4"/> Numero totale capannoni attivi all'atto dell'ispezione <input type="number" id="numCapannoniAttivi" name="numCapannoniAttivi" style="width: 4em" class="editField" value="<%=(ChecklistIstanza!=null && ChecklistIstanza.getNumCapannoniAttivi()!=null) ? toHtml(ChecklistIstanza.getNumCapannoniAttivi()) : ""%>" size="4"/>
</td></tr>
</table>



<table cellpadding="10" width="100%">
<tr><th colspan="4">Elenco capannoni</th></tr>
<tr>
<td><b>Numero</b></td>
<td><b>Capacita' massima</b></td>
<td><b>Animali presenti all'atto dell'ispezione</b></td>
<td><b>Ispezionato</b></td>
</tr>

<%
	for (int c = 0; c< 10; c++){
%>

<tr>
<td><input type="text" id="cap_<%=c%>_numero" name="cap_<%=c%>_numero" maxlength="15" class="editField" value="<%=(ChecklistIstanza!=null && ChecklistIstanza.getCapannoni().size()>c && ChecklistIstanza.getCapannoni().get(c)!=null) ? toHtml(ChecklistIstanza.getCapannoni().get(c).getNumero()) : ""%>" size="4"/></td>
<td><input type="number" id="cap_<%=c%>_capacita" name="cap_<%=c%>_capacita" style="width: 4em" class="editField" value="<%=(ChecklistIstanza!=null && ChecklistIstanza.getCapannoni().size()>c && ChecklistIstanza.getCapannoni().get(c)!=null) ? toHtml(ChecklistIstanza.getCapannoni().get(c).getCapacita()) : ""%>" size="4"/></td>
<td><input type="number" id="cap_<%=c%>_animali" name="cap_<%=c%>_animali" style="width: 4em" class="editField" value="<%=(ChecklistIstanza!=null && ChecklistIstanza.getCapannoni().size()>c && ChecklistIstanza.getCapannoni().get(c)!=null) ? toHtml(ChecklistIstanza.getCapannoni().get(c).getAnimali()) : ""%>" size="4"/></td>
<td><input type="radio" id="cap_<%=c%>_ispezionato_S" name="cap_<%=c%>_ispezionato" class="editField" value="S" <%=(ChecklistIstanza!=null && ChecklistIstanza.getCapannoni().size()>c && ChecklistIstanza.getCapannoni().get(c)!=null && toHtml(ChecklistIstanza.getCapannoni().get(c).getIspezionato()).equals("S")) ? "checked=\"checked\"" : ""%>/> SI <input type="radio" id="cap_<%=c%>_ispezionato_N" name="cap_<%=c%>_ispezionato" class="editField" value="N" <%=(ChecklistIstanza!=null && ChecklistIstanza.getCapannoni().size()>c && ChecklistIstanza.getCapannoni().get(c)!=null && toHtml(ChecklistIstanza.getCapannoni().get(c).getIspezionato()).equals("N")) ? "checked=\"checked\"" : ""%>/> NO</td>
</tr>

<%
	}
%>
</table>


<table cellpadding="10" width="100%">
 <tr><td>
In allevamento si pratica la "muta"  <input type="radio" id="muta_SI" name="muta" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getMuta()).equals("S")) ? "checked=\"checked\"" : ""%>/> SI <input type="radio" id="muta_NO" name="muta" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getMuta()).equals("N")) ? "checked=\"checked\"" : ""%>/> NO   <br/>
(vedi nota Ministero Salute pot. n. 23052 del 03/12/2013) 
</td></tr>
</table>


<table cellpadding="10" width="100%">
 <tr><td>
PREAVVISO (max 48 ore)             <input disabled value="si" <%=(Ticket.getFlag_preavviso()!=null && !Ticket.getFlag_preavviso().equalsIgnoreCase("n")) ? "checked=\"checked\"" : ""%> type="checkbox"> SI <input disabled value="no" <%=(Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equalsIgnoreCase("n"))  ? "checked=\"checked\"" : ""%> type="checkbox"> NO  <br/>
Se SI in data <label class="layout"><%=toDateasString(Ticket.getData_preavviso_ba())%></label> tramite: <input disabled value="P" <%=(Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equalsIgnoreCase("P")) ? "checked=\"checked\"" : ""%> type="checkbox"> Telefono <input disabled value="T" <%=(Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equalsIgnoreCase("T")) ? "checked=\"checked\"" : ""%> type="checkbox"> Telegramma/lettera/fax <input disabled value="A" <%=(Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equalsIgnoreCase("A")) ? "checked=\"checked\"" : ""%> type="checkbox"> Altra forma 
</td></tr>
</table>

<div style="page-break-before:always">&nbsp; </div>
<table cellpadding="10" width="100%">
<tr><td>

<b>LEGENDA</b> <br/>

<table cellpadding="10" width="100%">
<tr><td><b>Categorie delle non conformita'</b></td><td> <b>Azioni intraprese dall'Autorita' competente</b></td></tr>
<tr><td><b>A</b></td><td> Richiesta di rimediare alle non conformita' entro un termine inferiore a 3 mesi.<br/>Nessuna sanzione amministrativa o penale immediata.</td></tr>
<tr><td><b>B</b></td><td> Richiesta di rimediare alle non conformita' entro un termine superiore a 3 mesi.<br/>Nessuna sanzione amministrativa o penale immediata.</td></tr>
<tr><td><b>C</b></td><td> Sanzione amministrativa o penale immediata.</td></tr>
</table>

</td></tr>
</table>

<table cellpadding="10" width="100%">

<col width="20%"><col width="20%">
<col width="6%"><col width="6%"><col width="6%">
<col width="18%">
<col width="6%"><col width="6%"><col width="6%"><col width="6%">

<tr><th colspan="2">Tipo di irregolarita'</th><th colspan="4"></th><th>Numero irregolarita'</th> <th colspan="4">N. dei<br/>provvedimenti<br/>adottati di<br/>conseguenza<br/>(per categoria<br/>di non<br/>conformita')</th></tr>
<tr><th>Requisito</th><th>Definizione dei requisiti</th><th>SI</th><th>NO</th><th>NA</th><th>osservazioni</th><th></th><th>A</th><th>B</th><th>C</th></tr>

<%
	String requisitoPrecedente = "";
for (int i = 0; i<DomandeList.size(); i++) {
	org.aspcf.modules.checklist_benessere.base.v4.Domanda domanda = (org.aspcf.modules.checklist_benessere.base.v4.Domanda) DomandeList.get(i);
	org.aspcf.modules.checklist_benessere.base.v4.Risposta risposta = (org.aspcf.modules.checklist_benessere.base.v4.Risposta) domanda.getRisposta();
%>

<tr id="tr_<%=domanda.getId()%>">
<td>	
<% if (!requisitoPrecedente.equals(domanda.getRequisito())){ %> 
<%=toHtml(domanda.getRequisito())%> 
<% } 
requisitoPrecedente = domanda.getRequisito(); %>
</td>
<td><%=toHtml(domanda.getQuesito()) %></td>

<td> <input type="radio" id="dom_<%=domanda.getId()%>_risposta_S" name="dom_<%=domanda.getId() %>_risposta" value="S" <%=(ChecklistIstanza!=null && risposta!=null && toHtml(risposta.getRisposta()).equals("S")) ? "checked=\"checked\"" : "" %> onClick="checkRisposta(this.value, '<%=domanda.getId()%>')"/></td>
<td> <input type="radio" id="dom_<%=domanda.getId()%>_risposta_N" name="dom_<%=domanda.getId() %>_risposta" value="N" <%=(ChecklistIstanza!=null && risposta!=null && toHtml(risposta.getRisposta()).equals("N")) ? "checked=\"checked\"" : "" %> onClick="checkRisposta(this.value, '<%=domanda.getId()%>')"/></td>
<td> <input type="radio" id="dom_<%=domanda.getId()%>_risposta_-" name="dom_<%=domanda.getId() %>_risposta" value="-" <%=(ChecklistIstanza!=null && risposta!=null && toHtml(risposta.getRisposta()).equals("-")) ? "checked=\"checked\"" : "" %> onClick="checkRisposta(this.value, '<%=domanda.getId()%>')"/></td>

<td> <input type="text" id="dom_<%=domanda.getId()%>_evidenze" name="dom_<%=domanda.getId()%>_evidenze" class="editField" size="50" value="<%=(ChecklistIstanza!=null && risposta!=null) ? toHtml(risposta.getEvidenze()) : ""%>"/>  </td>

<td> <input type="number" <%=(ChecklistIstanza!=null && risposta!=null && !toHtml(risposta.getRisposta()).equals("N")) ? "disabled" : "" %> readonly id="dom_<%=domanda.getId() %>_irregolarita" name="dom_<%=domanda.getId() %>_irregolarita" style="width: 4em" class="editField" value="<%=(ChecklistIstanza!=null && risposta!=null) ? toHtml(risposta.getNumIrregolarita()) : "" %>" size="4"/></td>
<td> <input type="number" <%=(ChecklistIstanza!=null && risposta!=null && !toHtml(risposta.getRisposta()).equals("N")) ? "disabled" : "" %> id="dom_<%=domanda.getId() %>_provv_a" name="dom_<%=domanda.getId() %>_provv_a" style="width: 4em" class="editField" value="<%=(ChecklistIstanza!=null && risposta!=null) ? toHtml(risposta.getNumProvvA()) : "" %>" size="4" onChange="aggiornaIrregolarita('<%=domanda.getId()%>')"/></td>
<td> <input type="number" <%=(ChecklistIstanza!=null && risposta!=null && !toHtml(risposta.getRisposta()).equals("N")) ? "disabled" : "" %> id="dom_<%=domanda.getId() %>_provv_b" name="dom_<%=domanda.getId() %>_provv_b" style="width: 4em" class="editField" value="<%=(ChecklistIstanza!=null && risposta!=null) ? toHtml(risposta.getNumProvvB()) : "" %>" size="4" onChange="aggiornaIrregolarita('<%=domanda.getId()%>')"/></td>
<td> <input type="number" <%=(ChecklistIstanza!=null && risposta!=null && !toHtml(risposta.getRisposta()).equals("N")) ? "disabled" : "" %> id="dom_<%=domanda.getId() %>_provv_c" name="dom_<%=domanda.getId() %>_provv_c" style="width: 4em" class="editField" value="<%=(ChecklistIstanza!=null && risposta!=null) ? toHtml(risposta.getNumProvvC()) : "" %>" size="4" onChange="aggiornaIrregolarita('<%=domanda.getId()%>')"/></td>

</tr>

	<% }%> 

</table>

<div style="page-break-before:always">&nbsp; </div>

<table cellpadding="10" width="100%">

<tr><td>
<b>ESITO DEL CONTROLLO:
<input name="esitoControllo" id="esitoControllo_S" value="S" type="radio" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getEsitoControllo()).equals("S")) ? "checked=\"checked\"" : "" %>> FAVOREVOLE <input name="esitoControllo" id="esitoControllo_N" value="N" type="radio" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getEsitoControllo()).equals("N")) ? "checked=\"checked\"" : "" %>> SFAVOREVOLE <input name="esitoControllo" id="esitoControllo_A" value="A" type="radio" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getEsitoControllo()).equals("A")) ? "checked=\"checked\"" : "" %>> SFAVOREVOLE PER MANCATO/RIFIUTATO CONTROLLO</b>
</td></tr>

<tr><td>
<b>Intenzionalita' (da valutare in caso di esito del controllo sfavorevole): <input name="intenzionalita" id="intenzionalita_S" value="S" type="radio" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getIntenzionalita()).equals("S")) ? "checked=\"checked\"" : "" %>> SI <input name="intenzionalita" id="intenzionalita_N" value="N" type="radio" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getIntenzionalita()).equals("N")) ? "checked=\"checked\"" : "" %>> NO <input name="intenzionalita" id="intenzionalita_-" value="" type="radio" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getIntenzionalita()).equals("")) ? "checked=\"checked\"" : "" %>> N.A.</b>
</td></tr>

<tr><td>
<b>Elementi di possibile non conformita' relativi al sistema di identificazione e registrazione animale, alla sicurezza alimentare e alle<br/>
TSE ovvero all'impiego di sostanze vietate*: <input name="evidenze" id="evidenze_S" value="S" type="radio" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getEvidenze()).equals("S")) ? "checked=\"checked\"" : "" %>> SI <input name="evidenze" id="evidenze_N" value="N" type="radio" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getEvidenze()).equals("N")) ? "checked=\"checked\"" : "" %>> NO</b>
</td></tr>
<tr><td>
<center><b>EVIDENZE:</b></center>
</td></tr>

<tr><td>
<table cellpadding="10" width="100%">
<tr><td>Sistema di identificazione e registrazione animale</td><td><input type="text" id="evidenzeIr" name="evidenzeIr" class="editField" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getEvidenzeIr()) : ""%>"/></td></tr>
<tr><td>Sicurezza alimentare e TSE</td><td><input type="text" id="evidenzeTse" name="evidenzeTse" class="editField" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getEvidenzeTse()) : ""%>"/></td></tr>
<tr><td>Sostanze vietate</td><td><input type="text" id="evidenzeSv" name="evidenzeSv" class="editField" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getEvidenzeSv()) : ""%>"/></td></tr></table>
</td></tr>
<tr><td>
<b>*Qualora, durante l'esecuzione del controllo, il Veterinario controllore rilevasse elementi di non conformita' relativi al sistema di<br/>
identificazione e registrazione animale, alla sicurezza alimentare e alle TSE ovvero all'impiego di sostanze vietate, egli dovra'<br/>
riportarne l'evenienza flaggando il settore pertinente e specificare nell'apposito campo l'evidenza riscontrata. Al rientro presso<br/>
la ASL, il Veterinario controllore dovra' evidenziare al Responsabile della ASL quanto da lui rilevato e consegnare copia della<br/>
check-list da lui compilata in modo che il Responsabile stesso possa provvedere all'attivazione urgente dei relativi controlli. Il<br/>
sistema inoltre segnalera' opportunamente tale evenienza al fine dell'esecuzione obbligatoria dello specifico controllo.</b>
</td></tr>

<tr><td>
<center><b>PRESCRIZIONI E SANZIONI</b></center>
</td></tr>
<tr><td>
<center><b>PRESCRIZIONI</b></center>
</td></tr>
<tr><td>
SONO STATE ASSEGNATE PRESCRIZIONI ?     <input name="assegnatePrescrizioni" id="assegnatePrescrizioni_S" value="S" type="radio" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getAssegnatePrescrizioni()).equals("S")) ? "checked=\"checked\"" : "" %>> SI <input name="assegnatePrescrizioni" id="assegnatePrescrizioni_N" value="N" type="radio" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getAssegnatePrescrizioni()).equals("N")) ? "checked=\"checked\"" : "" %>> NO
</td></tr>
<tr><td>
SE SI QUALI: <input type="text" id="prescrizioniDescrizione" name="prescrizioniDescrizione" class="editField" size="100" maxlength="2000" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getPrescrizioniDescrizione()) : ""%>"/>
</td></tr>
<tr><td>
ENTRO QUALE DATA DOVRANNO ESSERE ESEGUITE? <input type="date" id="dataPrescrizioni" name="dataPrescrizioni" class="editField" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getDataPrescrizioni()) : ""%>"/>
</td></tr>
<tr><td>

<table cellpadding="10" width="100%">
<tr><td colspan="2"><center><b>SANZIONI APPLICATE</b></center></td></tr>
<tr><td><b>Blocco movimentazioni</b> <input type="number" id="sanzBlocco" name="sanzBlocco" class="editField" size="4" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getSanzBlocco()) : ""%>"/> </td><td><b>Amministrativa/pecuniaria</b>  <input type="number" id="sanzAmministrativa" name="sanzAmministrativa" class="editField" size="4" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getSanzAmministrativa()) : ""%>"/></td></tr>
<tr><td><b>Abbattimento capi</b>  <input type="number" id="sanzAbbattimento" name="sanzAbbattimento" class="editField" size="4" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getSanzAbbattimento()) : ""%>"/> </td><td><b>Sequestro capi</b>  <input type="number" id="sanzSequestro" name="sanzSequestro" class="editField" size="4" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getSanzSequestro()) : ""%>"/></td></tr>
<tr><td colspan="2"><b>Altro(specificare):</b> <input type="number" id="sanzAltro" name="sanzAltro" class="editField" size="4" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getSanzAltro()) : ""%>"/>  <input type="text" id="sanzAltroDesc" name="sanzAltroDesc" class="editField" size="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getSanzAltroDesc()) : ""%>"/> </td></tr>
</table>

</td></tr>
<tr><td>
<b>NOTE/OSSERVAZIONI DEL CONTROLLORE :</b> <input type="text" id="noteControllore" name="noteControllore" class="editField" size="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getNoteControllore()) : ""%>"/> 
</td></tr>
<tr><td>
<b>NOTE/OSSERVAZIONI DEL DETENTORE</b> <input type="text" id="noteProprietario" name="noteProprietario" class="editField" size="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getNoteProprietario()) : ""%>"/> 
</td></tr>
<tr><td>
<b>E' stata consegnata una copia della presente check-list all'allevatore ?:          <input disabled value="si" <%= (Ticket.getFlag_checklist() !=null && Ticket.getFlag_checklist().equalsIgnoreCase("S")) ? "checked=\"checked\"" : ""  %> type="checkbox">  SI  <input disabled value="si" <%= (Ticket.getFlag_checklist() !=null && Ticket.getFlag_checklist().equalsIgnoreCase("N")) ? "checked=\"checked\"" : ""  %> type="checkbox"> NO</b>
</td></tr>
<tr><td>
<b>Il risultato del presente controllo sara' utilizzato per verificare il rispetto degli impegni di condizionalita' alla base dell'erogazione egli aiuti<br/>
comunitari. Nel caso di presenza di non conformita' l'esito del controllo sara' elaborato dall'Organismo Pagatore.</b>
</td></tr>
<tr><td>
<b>DATA PRIMO CONTROLLO IN LOCO:</b> <input type="date" id="dataPrimoControllo" name="dataPrimoControllo" class="editField" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getDataPrimoControllo()) : ""%>"/> <br/>
NOME E COGNOME DEL PROPRIETARIO/DETENTORE/CONDUTTORE PRESENTE ALL'ISPEZIONE: <input type="text" id="nomeProprietario" name="nomeProprietario" class="editField" size="50" maxlength="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getNomeProprietario()) : ""%>"/><br/>
FIRMA DEL PROPRIETARIO/DETENTORE/CONDUTTORE PRESENTE ALL'ISPEZIONE: _______________<br/>
NOME E COGNOME DEL CONTROLLORE: <input type="text" id="nomeControllore" name="nomeControllore" class="editField" size="50" maxlength="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getNomeControllore()) : ""%>"/><br/>
FIRMA E TIMBRO DEL CONTROLLORE/I: _______________________________________________________<br/>
</td></tr>
<tr><td>

<b>VERIFICA DELL'ESECUZIONE DELLE PRESCRIZIONI<br/>
(da effettuare alla scadenza del tempo assegnato)</b>
</td></tr>
<tr><td>
<b>PRESCRIZIONI ESEGUITE: <input name="eseguitePrescrizioni" id="eseguitePrescrizioni_S" value="S" type="radio" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getEseguitePrescrizioni()).equals("S")) ? "checked=\"checked\"" : "" %>> SI <input name="eseguitePrescrizioni" id="eseguitePrescrizioni_N" value="N" type="radio" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getEseguitePrescrizioni()).equals("N")) ? "checked=\"checked\"" : "" %>> NO</b>
</td></tr>
<tr><td>
<b>DATA VERIFICA IN LOCO: <input type="date" id="dataVerifica" name="dataVerifica" class="editField" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getDataVerifica()) : ""%>"/> <br/>
Nome e cognome del proprietario/detentore/conduttore presente all'ispezione: <input type="text" id="nomeProprietarioPrescrizioniEseguite" name="nomeProprietarioPrescrizioniEseguite" class="editField" size="50" maxlength="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getNomeProprietarioPrescrizioniEseguite()) : ""%>"/><br/>
Firma del proprietario/detentore/conduttore presente all'ispezione: _________________________________<br/>
Nome e cognome del controllore: <input type="text" id="nomeControllorePrescrizioniEseguite" name="nomeControllorePrescrizioniEseguite" class="editField" size="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getNomeControllorePrescrizioniEseguite()) : ""%>"/><br/>
Firma e timbro del controllore/i: _______________________________________________________</b>
</td></tr>
<tr><td>
<b>DATA CHIUSURA RELAZIONE DI CONTROLLO**: <input type="date" id="dataChiusuraRelazione" name="dataChiusuraRelazione" class="editField" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getDataChiusuraRelazione()) : ""%>"/> </b>
</td></tr>
<tr><td>
<b>**Ai sensi del Reg. 809-2014, articolo 72, paragrafo 4. Fatta salva ogni disposizione particolare della normativa che si applica ai criteri e alle<br/>
norme, la relazione di controllo e' ultimata entro un mese dal controllo in loco. Tale termine puo' essere tuttavia prorogato a tre mesi in<br/>
circostanze debitamente giustificate, in particolare per esigenze connesse ad analisi chimiche o fisiche.</b>

</td></tr></table>

<input type="hidden" id="dataControllo" name="dataControllo" value="<%=toDateasStringWithFormat(Ticket.getAssignedDate(), "yyyy-MM-dd")%>"/>

<br/>

<input type="button" name="salva" id="salvaTemporaneo" class="buttonClass" value="Salva Temporaneo" onclick="saveForm(this.form, 'temp'); return false;"/>

<input type="button" name="salva" id="salvaDefinitivo" class="buttonClass" value="Salva Definitivo" onclick="saveForm(this.form, 'def'); return false;"/>


<input type="hidden" readonly name="bozza" id="bozza" value="" />
<input type="hidden" readonly name="idControllo" id="idControllo" value="<%=Allevamento.getIdControllo()%>" />
<input type="hidden" readonly name="orgId" id="orgId" value="<%=Allevamento.getOrgId()%>" />
<input type="hidden" readonly name="stabId" id="stabId" value="<%=Allevamento.getIdStabilimento()%>" />
<input type="hidden" readonly name="codice_specie" id="codice_specie" value="<%=codice_specie%>" />
<input type="hidden" readonly name="specie" id="specie" value="<%=specie%>" />
</form>

<br/>
<div id="stampa" style="display:none">
<jsp:include page="../../../gestione_documenti/boxDocumentaleNoAutomatico.jsp">
<jsp:param name="orgId" value="<%=request.getParameter("orgId") %>" />
<jsp:param name="stabId" value="<%=request.getParameter("idStabilimento") %>" />
<jsp:param name="extra" value="<%=request.getParameter("specie") %>" />
<jsp:param name="tipo" value="ChecklistGalline" />
<jsp:param name="idCU" value="<%=request.getParameter("idControllo") %>" />
<jsp:param name="url" value="<%=request.getParameter("url") %>" />
</jsp:include>
</div>
	
<%UserBean user = (UserBean) session.getAttribute("User");
if (user.getUserId()==5885) { %>	
<input type="button" id="caso" name="caso" style="background-color:yellow;" value="rispondi a caso a tutta la checklist (TEST)" onClick="rispondiCaso()"/>
<input type="button" id="casoD" name="casoD" style="background-color:lime" value="rispondi a caso solo alle domande (TEST)" onClick="rispondiSoloDomande()"/>
<% } %>

<script>
verificaStatoChecklist('<%=ChecklistIstanza.isBozza()%>');
</script>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

