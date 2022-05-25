<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="mod" class="org.aspcfs.modules.modello5.base.Mod5" scope="request"/>
<jsp:useBean id="cu" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="tipoMod" class="java.lang.String" scope="request"/>

<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="modello5/css/screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="modello5/css/print.css" />

<script>

function salva(form){
	if (confirm("Salvare il modello 5 in modo DEFINITIVO? Non sara' piu' possibile modificare il modello 5 a meno di richieste all'Help Desk.")){
		form.submit();
	}
}

function verificaStatoMod5(isBozza){
	
	if(isBozza == 'false'){
		var f = document.forms['myform'];
		for(var i=0,fLen=f.length;i<fLen;i++){

			if (f.elements[i].type == 'radio' || f.elements[i].type=='checkbox')
		    { 
		          f.elements[i].disabled = true;
		    } 
			else if (f.elements[i].type == 'submit')
		    { 
		          
		    } 
		    else {
			    
		  		f.elements[i].readOnly = true;
		  		f.elements[i].className = 'layout';
		    }
		}
		var g = document.forms['myform'].getElementsByTagName("textarea");
		for(var j=0; j < g.length; j++)
			 g.item(j).className = '';

		document.getElementById('salvaDefinitivo').style.display = 'none';
		document.getElementById('stampa').style.display = 'block';
	}
}



</script>

<!-- HEADER -->

<form id = "myform" name="myform" action="Modello5.do?command=Save&auto-populate=true" method="post">

<table><tr><td>

<div class="header">
<table cellpadding="10" width="100%" style="border-collapse: collapse">
 <col width="10%"><col width="50%"><col width="30%"><col width="10%">
<tr>
<td style="border:1px solid black;">
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div> 
<br/><b><center>REGIONE<br> CAMPANIA</center></b><br/>
<center><img style="text-decoration: none;" height="80" width="80" documentale_url="" src="gestione_documenti/schede/images/<%=mod.getControlloAsl().toLowerCase() %>.jpg" />
</center>
</td>

<td style="border:1px solid black;"><center><b>AMMINISTRAZIONE COMPETENTE: <br/>
SERVIZIO <input class="editField" type="text" name="headerServizio" id="headerServizio" value="<%=toHtml(mod.getHeaderServizio())%>" size="60" maxlength=""/> &nbsp;<br/> 
PEC <input class="editField" TYPE="text" name="headerPec1" id="headerPec1" value="<%=toHtml(mod.getHeaderPec1())%>" size="60" maxlength="" /><br/>
UO <input class="editField" type="text" name="headerUO" id="headerUO" value="<%=toHtml(mod.getHeaderUO())%>" size="60" maxlength="" /></br>
PEC <input class="editField" type="text" name="headerPec2" id="headerPec2" value="<%=toHtml(mod.getHeaderPec2())%>" size="60" maxlength="" />
</td>

<td style="border:1px solid black;"><center><b>&nbsp; MOD 5/<%=tipoMod %> &nbsp;</b><br/>
&nbsp; Rev. 8 del &nbsp; <br/>
&nbsp; 22/12/19&nbsp;</center>
</td>

<td style="border:1px solid black;"><center>
VERBALE DI ISPEZIONE<br/><br/>
<font size="2px">Id Controllo: <b><%=mod.getControlloId() %></b></font></center>
</td>
</tr>
</table>
</div>
<!-- FINE HEADER -->  

L'anno <label class="layout"><%= mod.getControlloAnno()%></label> addi' <label class="layout"><%= mod.getControlloGiorno()%></label> del mese di <label class="layout"><%= toHtml(mod.getControlloMese())%></label> 
alle ore <input class="editField" type="time" name="controlloOre" id="controlloOre" value="<%=toHtml(mod.getControlloOre())%>" size="5" maxlength="5"/> 
i sottoscritti <br/>
<label class="layout"><%= toHtmlFill(mod.getControlloNucleo(), 50)%></label><br/>
qualificandosi, si sono presentati presso:<br/><br/> 

<b><u>Stabilimento/azienda/altro (<i>luogo dell'ispezione</i>)</u></b>:<br/>
 Comune di <label class="layout"><%= toHtmlFill(mod.getStabilimentoComune(), 20)%></label> alla via <label class="layout"><%= toHtmlFill(mod.getStabilimentoIndirizzo(), 20)%></label> n <label class="layout"><%= toHtmlFill(mod.getStabilimentoCivico(), 4)%></label> <br/>
ric. CE n. <label class="layout"><%= toHtmlFill(mod.getStabilimentoCe(), 10)%></label> registr./cod.az./targa/n.seriale <label class="layout"><%= toHtmlFill(mod.getStabilimentoNumRegistrazione(), 20)%></label> <br/>
linea di attivita' ispezionata <label class="layout"><%= toHtmlFill(mod.getControlloLinea(), 50)%></label> <br/><br/>

<b><u>Nome/ditta/ragione/denominazione sociale</u></b>:<br/>
 <label class="layout"><%= toHtmlFill(mod.getOperatoreRagioneSociale(), 20)%></label>  sede legale in <label class="layout"><%= toHtmlFill(mod.getOperatoreComune(), 20)%></label> alla via <label class="layout"><%= toHtmlFill(mod.getOperatoreIndirizzo(), 20)%></label> n. <label class="layout"><%= toHtmlFill(mod.getOperatoreCivico(), 5)%></label> PI/CF <label class="layout"><%= toHtmlFill(mod.getOperatorePartitaIva(), 20)%></label> <br/>
 legale rappr./titolare sig. <label class="layout"><%= toHtmlFill(mod.getRappresentanteNome(), 20)%></label> nato a <label class="layout"><%= toHtmlFill(mod.getRappresentanteComuneNascita(), 20)%></label> il <label class="layout"><%= toHtmlFill(mod.getRappresentanteDataNascita(), 10)%></label> e residente in <label class="layout"><%= toHtmlFill(mod.getRappresentanteComune(), 20)%></label> alla via <label class="layout"><%= toHtmlFill(mod.getRappresentanteIndirizzo(), 20)%></label> n. <label class="layout"><%= toHtmlFill(mod.getRappresentanteCivico(), 5)%></label> domicilio digitale <label class="layout"><%= toHtmlFill(mod.getRappresentanteDomicilioDigitale(), 20)%></label><br/><br/>
 
<b><u>Presente all'ispezione</u></b>: <br/>
sig. <input class="editField" type="text" name="presenteNome" id="presenteNome" value="<%=toHtml(mod.getPresenteNome())%>" size="20"/> nato a <input class="editField" type="text" name="presenteComuneNascita" id="presenteComuneNascita" value="<%=toHtml(mod.getPresenteComuneNascita())%>" size="20"/> il <input class="editField" type="date" name="presenteDataNascita" id="presenteDataNascita" value="<%=toHtml(mod.getPresenteDataNascita())%>" size="10"/> e residente in <input class="editField" type="text" name="presenteComune" id="presenteComune" value="<%=toHtml(mod.getPresenteComune())%>" size="20"/> alla via <input class="editField" type="text" name="presenteIndirizzo" id="presenteIndirizzo" value="<%=toHtml(mod.getPresenteIndirizzo())%>" size="20"/> n. <input class="editField" type="text" name="presenteCivico" id="presenteCivico" value="<%=toHtml(mod.getPresenteCivico())%>" size="4"/> <br/><br/>

doc. ident. <input type="radio" id="doc_P" name="presenteDocumento" value="P" <%=(mod!=null && toHtml(mod.getPresenteDocumento()).equals("P")) ? "checked=\"checked\"" : ""%>/> pat. auto <input type="radio" id="doc_I" name="presenteDocumento" value="I" <%=(mod!=null && toHtml(mod.getPresenteDocumento()).equals("I")) ? "checked=\"checked\"" : ""%>/> C.I. <input type="radio" id="doc_A" name="presenteDocumento" value="A" <%=(mod!=null && toHtml(mod.getPresenteDocumento()).equals("A")) ? "checked=\"checked\"" : ""%>/> altro doc. (ultime 4 cifre <input class="editField" type="text" name="presenteDocumentoNumeri" id="presenteDocumentoNumeri" value="<%=toHtml(mod.getPresenteDocumentoNumeri())%>" size="4" maxlength="4"/> ) <input type="radio" id="doc_C" name="presenteDocumento" value="C" <%=(mod!=null && toHtml(mod.getPresenteDocumento()).equals("C")) ? "checked=\"checked\"" : ""%>/> personalmente conosciuto <input type="radio" id="doc_S" name="presenteDocumento" value="S" <%=(mod!=null && toHtml(mod.getPresenteDocumento()).equals("S")) ? "checked=\"checked\"" : ""%>/> sprovvisto <br/>
Questi si e' dichiarato quale delegato alla ricezione di atti ed e' stato avvisato della facolta' di farsi assistere da un legale di fiducia.<br/>
Modalita' utilizzate per i rilievi dell'ispezione: <input type="radio" id="mod_V" name="presenteModalitaIspezione" value="V" <%=(mod!=null && toHtml(mod.getPresenteModalitaIspezione()).equals("V")) ? "checked=\"checked\"" : ""%>/> visive <input type="radio" id="mod_D" name="presenteModalitaIspezione" value="D" <%=(mod!=null && toHtml(mod.getPresenteModalitaIspezione()).equals("D")) ? "checked=\"checked\"" : ""%>/> documentali <input type="radio" id="mod_S" name="presenteModalitaIspezione" value="S" <%=(mod!=null && toHtml(mod.getPresenteModalitaIspezione()).equals("S")) ? "checked=\"checked\"" : ""%>/> strumentali <input type="radio" id="mod_R" name="presenteModalitaIspezione" value="R" <%=(mod!=null && toHtml(mod.getPresenteModalitaIspezione()).equals("R")) ? "checked=\"checked\"" : ""%>/> rilievi fotografici/filmati <input type="radio" id="mod_A" name="presenteModalitaIspezione" value="A" <%=(mod!=null && toHtml(mod.getPresenteModalitaIspezione()).equals("A")) ? "checked=\"checked\"" : ""%>/> altro <br/><br/>


<b><u>MOTIVI DELL'ISPEZIONE (Piani di Monitoraggio e Attivita' previsti dal DPAR)</u></b></br/>
<input type="checkbox" disabled <%= mod.getControlloAttivita()!=null ? "checked=\"checked\"" : "" %>/> Attivita'<br/>
<label class="layout"><%=toHtmlFill(mod.getControlloAttivita(), 50) %></label><br/> 
<input type="checkbox" disabled <%= mod.getControlloPiano()!=null ? "checked=\"checked\"" : "" %>/> Piani di monitoraggio<br/>
<label class="layout"><%=toHtmlFill(mod.getControlloPiano(), 50) %></label>   <br/><br/>

<b><u>OGGETTO DEL CONTROLLO (suddivisi per Macroaree)</u></b>:<br/>
 <label class="layout"><%=toHtmlFill(mod.getControlloOggetto(), 50) %></label><br/><br/>
 
 <% if (tipoMod.equals("A")) { %>   
 
L'Unita' organizzativa responsabile del procedimento e' quella in intestazione, presso la cui sede si puo' prendere visione degli atti. Il responsabile del procedimento e' <input class="editField" type="text" name="responsabileProcedimento" id="responsabileProcedimento" value="<%=toHtml(mod.getResponsabileProcedimento())%>" size="20"/>. <br/>
Descrizione delle n.c. e dei provvedimenti <b>NON</b> imputabili al soggetto ispezionato: <input class="editField" type="text" name="provvedimentiNonImputabili" id="provvedimentiNonImputabili" value="<%=toHtml(mod.getProvvedimentiNonImputabili())%>" size="50"/><br/>

<b>Non conformita' <u>formali</u> rilevate</b>: <label class="layout"><%= toHtmlFill(mod.getControlloNcFormali(), 20)%></label><br/>
Punteggio*: <label class="layout"><%= toHtmlFill(mod.getControlloNcFormaliPunti(), 2)%></label>  <br/>
Valutazione del rischio caratterizzato dalla presenza delle n.c. formali (motivazioni di fatto) <label class="layout"><%= toHtmlFill(mod.getControlloNcFormaliVal(), 20)%></label> <br/>
Follow up delle n.c. formali (dispositivo del provvedim. amministr.): visto Reg. UE 2017/625, L. 241/90, DPR 320/54 ed il P.R.I. si impone la risoluzione delle non conformita' entro il termine di n. <input class="editField" type="text" name="ncFormaliGiorniRisoluzione" id="ncFormaliGiorniRisoluzione" value="<%=toHtml(mod.getNcFormaliGiorniRisoluzione())%>" size="2" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/> giorni dalla data odierna (per il diritto alla difesa: vedi sotto). L'avvenuta risoluzione dovra' essere comunicata (via pec) all'indirizzo pec dell'ufficio riportato in intestazione. La mancata comunicazione comporta l'esecuzione di una ulteriore ispezione con spese a carico del soggetto ispezionato.  <br/><br/>

<b>Non conformita' <u>significative</u> rilevate</b>: <label class="layout"><%= toHtmlFill(mod.getControlloNcSignificative(), 20)%></label><br/>
Punteggio*: <label class="layout"><%= toHtmlFill(mod.getControlloNcSignificativePunti(), 2)%></label>  <br/>
Valutazione del rischio caratterizzato dalla presenza delle n.c. significative (motivazioni di fatto) <label class="layout"><%= toHtmlFill(mod.getControlloNcSignificativeVal(), 50)%></label> <br/>
Follow up delle n.c. significative (dispositivo del provvedim. amministr.): visto Reg. UE 2017/625, L 241/90, DPR 320/54, il P.R.I. si impone la risoluzione delle non conformita' entro il termine di n. <input class="editField" type="text" name="ncSignificativeGiorniRisoluzione" id="ncSignificativeGiorniRisoluzione" value="<%=toHtml(mod.getNcSignificativeGiorniRisoluzione())%>" size="2" maxlength="3" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/> giorni da oggi (per il diritto alla difesa: vedi sotto). <br/><br/>

<b>Non conformita' <u>gravi</u> rilevate</b>: <label class="layout"><%= toHtml(mod.getControlloNcGravi())%></label><br/>
Punteggio*: <label class="layout"><%= toHtmlFill(mod.getControlloNcGraviPunti(), 5)%></label>  <br/>

<b>Follow up delle n.c. gravi:</b><br/> 
1) <input type="checkbox" id="ncGraviContestazione" name="ncGraviContestazione" value="S" <%=(mod!=null && toHtml(mod.getNcGraviContestazione()).equals("S")) ? "checked=\"checked\"" : ""%>/> si e' proceduto alla contestazione di illecito/i  amministrativo/i  con verbale/i n. <input class="editField" type="text" name="ncGraviContestazioneDesc" id="ncGraviContestazioneDesc" value="<%=toHtml(mod.getNcGraviContestazioneDesc())%>" size="10"/> <br/>
2) <input type="checkbox" id="ncGraviAccertamento" name="ncGraviAccertamento" value="S" <%=(mod!=null && toHtml(mod.getNcGraviAccertamento()).equals("S")) ? "checked=\"checked\"" : ""%>/> si procedera', se del caso e/o previo ulteriori accertamenti, alla notifica di illecito/i amministrativo/i  con atto/i  a parte
3) <input type="checkbox" id="ncGraviDiffida" name="ncGraviDiffida" value="S" <%=(mod!=null && toHtml(mod.getNcGraviDiffida()).equals("S")) ? "checked=\"checked\"" : ""%>/> vista la L. 116/14 si diffida l'operatore a eliminare la non conformita' grave n. <input class="editField" type="text" name="ncGraviDiffidaDesc" id="ncGraviDiffidaDesc" value="<%=toHtml(mod.getNcGraviDiffidaDesc())%>" size="10"/> entro il termine di venti giorni da oggi. Avverso tale diffida possono essere inviati scritti difensivi a dg04.uod02@pec.regione.campania.it <br>   
4) <input type="checkbox" id="ncGraviSequestroAmministrativo" name="ncGraviSequestroAmministrativo" value="S" <%=(mod!=null && toHtml(mod.getNcGraviSequestroAmministrativo()).equals("S")) ? "checked=\"checked\"" : ""%>/> visto il Reg. UE 2017/625, la L. 689/81 e la DGRC n. 623/14, in seguito alla rilevazione di illecito amministrativo, si e' proceduto al sequestro amministrativo delle cose confiscabili elencate nel/i verbale/i n. <input class="editField" type="text" name="ncGraviSequestroAmministrativoDesc" id="ncGraviSequestroAmministrativoDesc" value="<%=toHtml(mod.getNcGraviSequestroAmministrativoDesc())%>" size="10"/> <br/>
5) <input type="checkbox" id="ncGraviSequestroPenale" name="ncGraviSequestroPenale" value="S" <%=(mod!=null && toHtml(mod.getNcGraviSequestroPenale()).equals("S")) ? "checked=\"checked\"" : ""%>/> si e' proceduto al sequestro penale delle cose elencate nel/i verbale/i n. <input class="editField" type="text" name="ncGraviSequestroPenaleDesc" id="ncGraviSequestroPenaleDesc" value="<%=toHtml(mod.getNcGraviSequestroPenaleDesc())%>" size="10"/><br/>
6) <input type="checkbox" id="ncGraviBlocco" name="ncGraviBlocco" value="S" <%=(mod!=null && toHtml(mod.getNcGraviBlocco()).equals("S")) ? "checked=\"checked\"" : ""%>/> visto il Reg. UE 625/17, L. 241/90, L 283/62, DPR 320/54 ed il P.R.I. si e' proceduto al blocco/sequestro sanitario delle cose/animali elencate nel/i verbale/in. <input class="editField" type="text" name="ncGraviBloccoDesc" id="ncGraviBloccoDesc" value="<%=toHtml(mod.getNcGraviBloccoDesc())%>" size="10"/> che forma parte integrante del presente provvedimento amministrativo<br/>  
7) <input type="checkbox" id="ncGraviDispone" name="ncGraviDispone" value="S" <%=(mod!=null && toHtml(mod.getNcGraviDispone()).equals("S")) ? "checked=\"checked\"" : ""%>/> visto Reg. UE 2017/625, L. 241/90, DPR 320/54 ed il P.R.I. si dispone che (dispositivo del provved. amministr.):<input class="editField" type="text" name="ncGraviDisponeDesc" id="ncGraviDisponeDesc" value="<%=toHtml(mod.getNcGraviDisponeDesc())%>" size="20"/><br/>
Valutazione del rischio caratterizzato dalla presenza delle n.c. gravi (motivazioni di fatto da redigere solo nei sopraelencati casi 6) e 7)):<br/>
<input class="editField" type="text" name="valutazioneRischio" id="valutazioneRischio" value="<%=toHtml(mod.getValutazioneRischio())%>" size="50"/> 
(diritto alla difesa nei casi 6) e 7): vedi sotto)<br/> 
Il presente all'ispezione spontaneamente dichiara che <br/>
<input class="editField" type="text" name="presenteDichiarazione" id="presenteDichiarazione" value="<%=toHtml(mod.getPresenteDichiarazione())%>" size="50"/> <br/><br/>

Ai sensi degli art. 21 bis e quater L. 241/90 il presente provvedimento ha efficacia immediata in quanto cautelare e urgente. Essendo susseguente ad ispezione, al presente provvedimento si applica la deroga ex art.7 p. 1 L. 241/90 in materia di comunicazione di avvio del procedimento. Si avvisa che alla scadenza del termine concesso per la risoluzione delle n.c., si procedera' a nuova ispezione. La mancata risoluzione delle n.c. comportera' la contestazione di illecito amministrativo. Ai sensi dell'art. 6, punto 1, lettera e), del Reg UE 679/16 si assicura la liceita' del trattamento dei dati personali che potranno essere inviati ad altre P.A. con finalita' che riguardano la definizione della presente procedura e degli atti conseguenti, o resi pubblici in ottemperanza al Reg UE 625/17 ed agli altri obblighi normativi in materia di trasparenza. Consci delle sanzioni previste per le dichiarazioni mendaci, gli ispettori dichiarano che per nessuno di loro esistono conflitti d'interesse nello svolgimento della presente ispezione.<br/>
Note: <input class="editField" type="text" name="note" id="note" value="<%=toHtml(mod.getNote())%>" size="50"/><br/>
Fatto in n. ___ copie originali<br/> 
Data chiusura ispezione <label class="layout"><%=toHtmlFill(mod.getControlloDataFine(), 10) %></label>, Punteggio tot. delle n.c. <input class="editField" type="text" name="ncPunteggioTotale" id="ncPunteggioTotale" value="<%=toHtml(mod.getNcPunteggioTotale())%>" size="3" maxlength="5" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/>  <br/><br/>
Letto, confermato, sottoscritto e consegnato.<br/> 
Diritto alla difesa: avverso il presente provvedimento amministrativo si puo': 1) richiedere all'ufficio di annullare/modificare il provvedimento in autotutela ex art. 21 nonies L. 241/80 2) presentare ricorso gerarchico ex DPR 1199/71 entro 30 giorni da oggi, inviandolo esclusivamente tramite pec  agli indirizzi indi cati in intestazione 3) ricorrere al TAR entro 60 giorni da oggi  4) presentare ricorso straordinario al Presidente della Repubblica entro 120 giorni da oggi<br/>
N.C. = Non Conformita'              -                    (*) n. 1 punto per ogni n.c. formale, n. 7 punti per ogni n.c. significativa, n. 50 punti per ogni n.c. grave.<br/><br/>   
  
<% } else if (tipoMod.equals("B")) { %>

<input type="checkbox" id="ispezioneVerifica" name="ispezioneVerifica" value="S" <%=(mod!=null && toHtml(mod.getIspezioneVerifica()).equals("S")) ? "checked=\"checked\"" : ""%>/> l'ispezione e' stata effettuata per la verifica della risoluz. di n.c. rilevate in un precedente controllo e le azioni risolutive messe in atto risultano adeguate ed efficaci come di seguito brevemente descritte: <br/>
<input class="editField" type="text" name="azioniDescrizione" id="azioniDescrizione" value="<%=toHtml(mod.getAzioniDescrizione())%>" size="50"/><br/>
RISULTANZE DELL'ISPEZIONE: limitatamente per quanto riguarda gli oggetti dell'ispezione, non si sono riscontrate non conformita'. Ai sensi dell'art. 6, punto 1, lettera e), del Reg UE 679/2016 si assicura la liceita' del trattamento dei dati personali che potranno essere inviati ad altre P.A. se necessario, o resi pubblici in ottemperanza al Reg UE 625/17 ed agli altri obblighi normativi in materia di trasparenza. Consci delle sanzioni previste per le dichiarazioni mendaci, gli ispettori dichiarano che per nessuno di loro esistono conflitti d'interesse nello svolgimento della presente ispezione. <br/>
Note: <input class="editField" type="text" name="note" id="note" value="<%=toHtml(mod.getNote())%>" size="50"/><br/>
Data chiusura ispezione <label class="layout"><%=toHtmlFill(mod.getControlloDataFine(), 10) %></label>, Fatto in n. ___ copie originali<br/> <br/>
Letto, sottoscritto e consegnato.<br/> 

<% } %>   
    
    IL PRESENTE ALL'ISPEZIONE &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; IL LEGALE DI FIDUCIA&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; GLI OPERATORI DEL CONTROLLO UFFICIALE


<input type="hidden" id="idControllo" name="idControllo" value="<%=mod.getControlloId()%>"/>
<input type="hidden" id="id" name="id" value="<%=mod.getId()%>"/>
<input type="hidden" id="tipoMod" name="tipoMod" value="<%=tipoMod%>"/>  
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>

<br/>
<center><input type="button" name="salvaDefinitivo" id ="salvaDefinitivo" value="Salva Definitivo" onClick="salva(this.form)"/> </center> 

<div id="stampa" style="display:none"><center>  
<jsp:include page="../gestione_documenti/boxDocumentaleNoAutomatico.jsp">
<jsp:param name="orgId" value="<%=cu.getOrgId() %>" />
<jsp:param name="stabId" value="<%=cu.getIdStabilimento() %>" />
<jsp:param name="altId" value="<%=cu.getAltId() %>" />
<jsp:param name="tipo" value="5" />
<jsp:param name="extra" value="<%=tipoMod %>" />
<jsp:param name="idCU" value="<%=mod.getControlloId() %>" /> 
</jsp:include>
</center>
</div>

</td></tr></table>
</form>

<script>
verificaStatoMod5('<%=mod.isBozza()%>');
</script>
