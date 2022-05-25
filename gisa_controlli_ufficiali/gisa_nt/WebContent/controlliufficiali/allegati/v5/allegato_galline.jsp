<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="Allevamento" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Ticket" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="DomandeList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="codice_specie" class="java.lang.String" scope="request"/>
<jsp:useBean id="ChecklistIstanza" class="org.aspcf.modules.checklist_benessere.base.v5.ChecklistIstanza_Galline" scope="request"/>
<%@page import="java.util.regex.*"%>

<%@page import="org.aspcf.modules.checklist_benessere.base.*"%>

<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="controlliufficiali/allegati/v5/css/screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="controlliufficiali/allegati/v5/css/print.css" />

<%@ include file="../../../../initPage.jsp" %>


<script>
function verificaStatoChecklist(isBozza){
	
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

		document.getElementById('salvaTemporaneo').style.display = 'none';
		document.getElementById('salvaDefinitivo').style.display = 'none';
		document.getElementById('stampa').style.display = 'block';
		window.opener.loadModalWindow();
        //window.opener.location.reload();
        window.opener.location.href="AllevamentiVigilanza.do?command=TicketDetails&id=<%=Ticket.getId()%>";

	}
}

function saveForm(form, tipo){
	
	if (tipo=='temp'){
		if(confirm("La scheda sara' aggiornata come richiesto. Vuoi procedere con il salvataggio?")) {
		 form.bozza.value = "true";
		 loadModalWindow();
		 form.submit();
		}
	}
	else {
		
		//QUI VANNO MESSI TUTTI I CONTROLLI DI CONGRUENZA
		var check = true;
		check = checkForm(form);
		if (!check)
			return false;
		
		if(confirm("La scheda sara' aggiornata come richiesto ma i dati non saranno piu' modificabili. Vuoi procedere con il salvataggio definitivo?")) {
		 form.bozza.value = "false";
		 loadModalWindow();
		 form.submit();
		}
	}
	
}

function checkForm(form){
	var esito = true;
	var msg = 'Impossibile salvare. Controllare i seguenti campi: \n\n';
		
	var esitoControllo = form.esitoControllo.value;
	var numChecklist = form.numChecklist.value;
	var	extrapiano = form.extrapiano.value;
	var nomeControllore = form.nomeControllore.value;
	var nomeProprietario = form.nomeProprietario.value;
	var criteriUtilizzati = form.criteriUtilizzati.value;
	var criteriUtilizzatiAltroDescrizione = form.criteriUtilizzatiAltroDescrizione.value;
	var sanzAltro= form.sanzAltro.value;
	var sanzAltroDesc= form.sanzAltroDesc.value;	
	var dataPrimoControllo = form.dataPrimoControllo.value;
	var appartenenteCondizionalita = form.appartenenteCondizionalita.value;
	var	presenzaManuale = form.presenzaManuale.value;
	var	evidenze = form.evidenze.value;
	var	evidenzeIr = form.evidenzeIr.value;
	var	evidenzeTse = form.evidenzeTse.value;
	var	evidenzeSv = form.evidenzeSv.value;
	
	var dataVerifica = form.dataVerifica.value;
	var dataChiusura = form.dataChiusuraRelazione.value;
	var dataControllo = form.dataControllo.value;
	
	var	selezioneImballaggio = form.selezioneImballaggio.value;
	var	selezioneImballaggioDestinazione = form.selezioneImballaggioDestinazione.value;
	var	muta = form.muta.value;
	var	ibridoRazza = form.ibridoRazza.value;
	var	numPianiGabbie = form.numPianiGabbie.value;
	var	numGallineGabbia = form.numGallineGabbia.value;
	var	batteria = form.batteria.value;
	var	batteriaUnicoPiano = form.batteriaUnicoPiano.value;
	var	batteriaPianiSfasati = form.batteriaPianiSfasati.value;
	var	batteriaPianiSovrapposti = form.batteriaPianiSovrapposti.value;

	
	if ((dataChiusura != '' && dataVerifica == '') || (dataChiusura == '' && dataVerifica != '')){
		msg+="[DATA CHIUSURA RELAZIONE DI CONTROLLO, DATA VERIFICA IN LOCO] Valorizzare o svuotare entrambi i campi.\n";
		esito = false;
	}
	else {
		if (checkDate(dataChiusura, dataVerifica)==1){
			msg+="[DATA CHIUSURA RELAZIONE DI CONTROLLO] Deve essere maggiore o uguale a [DATA VERIFICA IN LOCO].\n";
			esito = false;
		}
		if (checkDate(dataChiusura, dataControllo)==1){
			msg+="[DATA CHIUSURA RELAZIONE DI CONTROLLO] Deve essere maggiore o uguale a [DATA DEL CONTROLLO].\n";
			esito = false;
		}
		if (checkDate(dataVerifica, dataControllo)==1){
			msg+="[DATA VERIFICA IN LOCO] Deve essere maggiore o uguale a [DATA DEL CONTROLLO].\n";
			esito = false;
		}
	}
	
	if (numChecklist == ''){
		msg+="[N. Check List] Obbligatorio.\n";
		esito = false;
	}
	if (nomeControllore == ''){
		msg+="[NOME E COGNOME DEL CONTROLLORE] Obbligatorio.\n";
		esito = false;
	}
	if (nomeProprietario == ''){
		msg+="[NOME E COGNOME DEL PROPRIETARIO/DETENTORE/CONDUTTORE PRESENTE ALL'ISPEZIONE] Obbligatorio.\n";
		esito = false;
	}
	if (criteriUtilizzati == ''){
		msg+="[Selezionare i criteri utilizzati per la selezione dell'allevamento sottoposto a controllo] Obbligatorio.\n";
		esito = false;
	}
	
	if (criteriUtilizzati == '997' && criteriUtilizzatiAltroDescrizione == ''){
		msg+="[Descrizione altro criterio utilizzato] Obbligatorio.\n";
		esito = false;
	}
	
	if (sanzAltro != ''  && sanzAltroDesc == ''){
		msg+="[Descrizione Sanzioni Altro] Obbligatorio.\n";
		esito = false;
	}
	
	if (dataPrimoControllo == ''){
		msg+="[DATA PRIMO CONTROLLO IN LOCO] Obbligatoria.\n";
		esito = false;
	}
	if (appartenenteCondizionalita == ''){
		msg+="[CONTROLLO APPARTENENTE/NON APPARTENENTE AL CAMPIONE CONDIZIONALITA'] Obbligatorio.\n";
		esito = false;
	}
	if (extrapiano == ''){
		msg+="[EXTRAPIANO] Obbligatorio.\n";
		esito = false;
	}
	
	if (selezioneImballaggio == ''){
		msg+="[SELEZIONE/IMBALLAGGIO] Obbligatorio.\n";
		esito = false;
	}

	if (selezioneImballaggio == 'S' && selezioneImballaggioDestinazione == ''){
		msg+="[SELEZIONE/IMBALLAGGIO DESTINAZIONE] Obbligatorio.\n";
		esito = false;
	}
	
	if (muta == ''){
		msg+="[MUTA] Obbligatorio.\n";
		esito = false;
	}
		
	if (esitoControllo == 'A'){ // mancato controllo
		
		msg+= "\nErrori per salvataggio definitivo su esito: SFAVOREVOLE PER MANCATO/RIFIUTATO CONTROLLO.\n\n";
		
		if (evidenze == 'S'){
			msg+="[Elementi di possibile non conformita' relativi al sistema di identificazione e registrazione animale, alla sicurezza alimentare e alle TSE ovvero all'impiego di sostanze vietate] Non consentito rispondere SI.\n";
			esito = false;
		}
		
		if (dataVerifica != ''){
			msg+="[DATA VERIFICA] Deve essere vuoto.\n";
			esito = false;
		}
			
	}
	else if (esitoControllo == 'N'){ //sfavorevole
		
		if (ibridoRazza == ''){
			msg+="[IBRIDO/RAZZA] Obbligatorio.\n";
			esito = false;
		}
		if (numPianiGabbie == ''){
			msg+="[N. PIANI DI GABBIE] Obbligatorio.\n";
			esito = false;
		}
		if (numGallineGabbia == ''){
			msg+="[N. GALLINE PER GABBIA] Obbligatorio.\n";
			esito = false;
		}
		
		if (batteria == ''){
			msg+="[IN BATTERIA] Obbligatorio.\n";
			esito = false;
		}
	
		if (batteria == 'S' && (batteriaUnicoPiano == '' || batteriaPianiSfasati == '' || batteriaPianiSovrapposti == '')){
			msg+="[IN BATTERIA SU PIANO UNICO, SFASATI, SOVRAPPOSTI] Obbligatorio.\n";
			esito = false;
		}
		
		if (presenzaManuale == ''){
			msg+="[Presenza di un manuale di buone pratiche] Obbligatorio.\n";
			esito = false;
		}
		
		if ((evidenze != '' && evidenze =='S') && (evidenzeIr=='' && evidenzeTse == '' && evidenzeSv == '')){
			msg+="Avendo selezionato [Elementi di possibile non conformita'] e' necessario indicare almeno uno tra [Sistema di identificazione e registrazione animale] [Sicurezza alimentare e TSE] [Sostanze vietate].\n";
			esito = false;
		}
		if ((evidenze == '' || evidenze == 'N') && (evidenzeIr!='' || evidenzeTse != '' || evidenzeSv != '')){
			msg+="Non avendo selezionato [Elementi di possibile non conformita'] e' necessario svuotare [Sistema di identificazione e registrazione animale] [Sicurezza alimentare e TSE] [Sostanze vietate].\n";
			esito = false;
		}
		
	}
	else if (esitoControllo == 'S'){ //favorevole
		
		if (ibridoRazza == ''){
			msg+="[IBRIDO/RAZZA] Obbligatorio.\n";
			esito = false;
		}
		if (numPianiGabbie == ''){
			msg+="[N. PIANI DI GABBIE] Obbligatorio.\n";
			esito = false;
		}
		if (numGallineGabbia == ''){
			msg+="[N. GALLINE PER GABBIA] Obbligatorio.\n";
			esito = false;
		}
		
		if (batteria == ''){
			msg+="[IN BATTERIA] Obbligatorio.\n";
			esito = false;
		}
	
		if (batteria == 'S' && (batteriaUnicoPiano == '' || batteriaPianiSfasati == '' || batteriaPianiSovrapposti == '')){
			msg+="[IN BATTERIA SU PIANO UNICO, SFASATI, SOVRAPPOSTI] Obbligatorio.\n";
			esito = false;
		}
	
		if (presenzaManuale == ''){
			msg+="[Presenza di un manuale di buone pratiche] Obbligatorio.\n";
			esito = false;
		}
		
		if ((evidenze != '' && evidenze =='S') && (evidenzeIr=='' && evidenzeTse == '' && evidenzeSv == '')){
			msg+="Avendo selezionato [Elementi di possibile non conformita'] e' necessario indicare almeno uno tra [Sistema di identificazione e registrazione animale] [Sicurezza alimentare e TSE] [Sostanze vietate].\n";
			esito = false;
		}
		if ((evidenze == '' || evidenze == 'N') && (evidenzeIr!='' || evidenzeTse != '' || evidenzeSv != '')){
			msg+="Non avendo selezionato [Elementi di possibile non conformita'] e' necessario svuotare [Sistema di identificazione e registrazione animale] [Sicurezza alimentare e TSE] [Sostanze vietate].\n";
			esito = false;
		}
		
		if (dataVerifica != ''){
			msg+="[DATA VERIFICA] Deve essere vuoto.\n";
			esito = false;
		}
		
		if (!tutteDomandeRisposte()){
			msg+="[ELEMENTO DI VERIFICA] Rispondere ed indicare le evidenze di tutte le domande.\n";
			esito = false;
		}
			
	}
	else {
		msg+="[ESITO DEL CONTROLLO] Obbligatorio.";
		esito = false;
	}
	
	if (!esito)
		alert(msg);
	return esito;
}

function filtraInteri(campo){
	campo.value=campo.value.replace(/[^0-9]+/,'')
}

function filtraDecimali(campo){
	campo.value=campo.value.replace(/[^0-9.]+/,'')
}

function tutteDomandeRisposte(){
	
	<%for (int i = 0; i<DomandeList.size(); i++) {
		org.aspcf.modules.checklist_benessere.base.v5.Domanda domanda = (org.aspcf.modules.checklist_benessere.base.v5.Domanda) DomandeList.get(i);%>
	
		var x = document.getElementsByName("dom_<%=domanda.getId()%>_risposta");
		var evidenze = "";
		var selezionato = false;
		
		for (var j = 0; j<x.length; j++){
			if (x[j].checked)
				selezionato = true;
		}
		
		evidenze = document.getElementById("dom_<%=domanda.getId()%>_evidenze").value;

		document.getElementById("table_<%=domanda.getId()%>").style.background='';
		if (!selezionato || evidenze == ""){
			document.getElementById("table_<%=domanda.getId()%>").style.background='red';
			return false;
		}
		selezionato = false;
		
		<% } %>	
		return true;
	}

	function checkDate(data_iniziale, data_finale){
		var arr1 = data_iniziale.split("-");
		var arr2 = data_finale.split("-");

		var d1 = new Date(arr1[0],arr1[1]-1,arr1[2]);
		var d2 = new Date(arr2[0],arr2[1]-1,arr2[2]);
		
		var r1 = d1.getTime();
		var r2 = d2.getTime();
		
		if (r1<r2)
			return 1;
		else
			return 2;
		}


</script>

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div>


<form method="post" name="myform" action="PrintModulesHTML.do?command=InsertChecklistBenessere&auto-populate=true">

<center>
<b>PROTEZIONE DEGLI ANIMALI IN ALLEVAMENTO</b></br>
<b>GALLINE OVAIOLE</b><br/>
<b>(D. Lgs. 146/2001 - D. Lgs. 267/2003 e ss.mm.ii)</b><br/>
INFORMAZIONI SPECIFICHE E RACCOLTA DATI AZIENDALI<br/>
</center> 
    
<div class="header">  
<b>REGIONE</b>  <label class="layout">CAMPANIA</label> <b>ASL</b> <label class="layout"><%=Allevamento.getAsl()%></label> <BR/>
<b>Data del controllo:</b>  <label class="layout"><%=toDateasString(Ticket.getAssignedDate())%></label>  <b>N. Check List:</b> <input type="text" id="numChecklist" name="numChecklist" size ="13" maxlength="13" onkeyup="filtraInteri(this)" class="editField" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getNumCheckList()) : ""%>"/> <br/>
<b>Veterinario Ispettore</b> <input type="text" id="veterinarioIspettore" name="veterinarioIspettore" class="editField" size="50" maxlength="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getVeterinarioIspettore()) : ""%>"/>  EXTRAPIANO: SI <input type="radio" id="extrapiano_SI" name="extrapiano" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getExtrapiano()).equals("S")) ? "checked=\"checked\"" : ""%>/>  NO <input type="radio" id="extrapiano_NO" name="extrapiano" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getExtrapiano()).equals("N")) ? "checked=\"checked\"" : ""%>/>  <br/>
</div>

<table cellpadding="10">
<tr><td>
Codice azienda  <label class="layout"><%=Allevamento.getN_reg()%></label> Ragione sociale  <label class="layout"><%=Allevamento.getName()%></label><br/>
Indirizzo azienda  <label class="layout"><%=Allevamento.getIndirizzo()%></label> <br/>
Indirizzo sede legale <label class="layout"><%=Allevamento.getIndirizzo_legale()%></label><br/>
Proprietario degli animali  <label class="layout"><%=(Allevamento.getProp()!=null && !Allevamento.getProp().equalsIgnoreCase("null")) ? Allevamento.getProp() : "___________"%></label><br/>
Codice fiscale <label class="layout"><%=Allevamento.getCf_prop()%></label> Tel. <label class="layout">___________________</label><br/>
Conduttore/Detentore <label class="layout"><%=Allevamento.getDet()%></label><br/>
Codice fiscale <label class="layout"><%=Allevamento.getCf_det()%></label> Tel. <label class="layout">___________________</label><br/>
Tipo attivita' <label class="layout"><%=Allevamento.getTipologia_struttura()%></label><br/>
<i>(AL Allevamento; AF Allevamento familiare; MS Mista)</i><br/>
Orientamento produttivo <label class="layout"><%=Allevamento.getTipologia_att()%></label> <br/>
Tipologia produttiva <label class="layout">______________________________________</label><br/>
Modalita' di allevamento <label class="layout"><%=(Allevamento.getCodice_tipo_allevamento()!=null && Allevamento.getCodice_tipo_allevamento().equalsIgnoreCase("ST")) ? "STABULATO" : "______________________________________"%></label> <br/>
<i>(0 Biologico; 1 All'aperto (Free range); 2 A terra/In voliera; 3 In gabbia)</i><br/>
<b>Presenza di un manuale di buone pratiche:</b>           SI <input type="radio" id="presenzaManuale_SI" name="presenzaManuale" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getPresenzaManuale()).equals("S")) ? "checked=\"checked\"" : ""%>/> NO <input type="radio" id="presenzaManuale_NO" name="presenzaManuale" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getPresenzaManuale()).equals("N")) ? "checked=\"checked\"" : ""%>/><br/>
Veterinario Aziendale (se presente): Dr. _______________________________________________________________________<br/>
</td></tr>

<tr><th>NUMERO CAPI PRESENTI IN BDN (sulla base delle registrazioni effettuate nel sistema)</th></tr>
<tr><td>

n. totale capannoni _______  <br/>
n. totale capannoni attivi all'atto dell'ispezione: _______  <br/>

<table>
<tr>
<th>Numero capannone</th><th>Capacita' massima</th><th>Data di accasamento</th><th>Numero complessivo animali accasati</th><th>presenti al momento della stampa della check-list (inclusi sfoltimenti e mortalita' ove registrati)</th></tr>
<%for (int r = 0; r<4; r++){ %>
<tr><td><br/></td><td></td><td></td><td></td><td></td></tr>
<% } %>
</table>

<br/>

<table cellpadding="10">

<tr><th>DATI AZIENDALI (da compilare sulla base delle informazioni fornite dall'allevatore):</th></tr>
<tr><td>
Numero uova anno <input type="number" step="1" id="numUovaAnno" name="numUovaAnno" class="editField" size="4" maxlength="6" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getNumUovaAnno()) : ""%>"/><br/>
In batteria? SI <input type="radio" id="batteria_SI" name="batteria" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getBatteria()).equals("S")) ? "checked=\"checked\"" : ""%>/>  NO <input type="radio" id="batteria_NO" name="batteria" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getBatteria()).equals("N")) ? "checked=\"checked\"" : ""%>/><br/>
Se in batteria, le gabbie sono disposte: <br/>
Su un unico piano? SI <input type="radio" id="batteriaUnicoPiano_SI" name="batteriaUnicoPiano" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getBatteriaUnicoPiano()).equals("S")) ? "checked=\"checked\"" : ""%>/>  NO <input type="radio" id="batteriaUnicoPiano_NO" name="batteriaUnicoPiano" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getBatteriaUnicoPiano()).equals("N")) ? "checked=\"checked\"" : ""%>/>
Su piani sfasati? SI <input type="radio" id="batteriaPianiSfasati_SI" name="batteriaPianiSfasati" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getBatteriaPianiSfasati()).equals("S")) ? "checked=\"checked\"" : ""%>/>  NO <input type="radio" id="batteriaPianiSfasati_NO" name="batteriaPianiSfasati" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getBatteriaPianiSfasati()).equals("N")) ? "checked=\"checked\"" : ""%>/>
Su piani sovrapposti? SI <input type="radio" id="batteriaPianiSovrapposti_SI" name="batteriaPianiSovrapposti" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getBatteriaPianiSovrapposti()).equals("S")) ? "checked=\"checked\"" : ""%>/>  NO <input type="radio" id="batteriaPianiSovrapposti_NO" name="batteriaPianiSovrapposti" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getBatteriaPianiSovrapposti()).equals("N")) ? "checked=\"checked\"" : ""%>/><br/>
n. piani di gabbie <input type="number" step="1" id="numPianiGabbie" name="numPianiGabbie" class="editField" size="4" maxlength="6" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getNumPianiGabbie()) : ""%>"/> n. galline per gabbia <input type="number" step="1" id="numGallineGabbia" name="numGallineGabbia" class="editField" size="4" maxlength="6" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getNumGallineGabbia()) : ""%>"/><br/>
Ibrido/Razza allevata <input type="text" id="ibridoRazza" name="ibridoRazza" class="editField" size="20" maxlength="30" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getIbridoRazza()) : ""%>"/> <br/>
Selezione/imballaggio presso l'allevamento? SI <input type="radio" id="selezioneImballaggio_SI" name="selezioneImballaggio" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getSelezioneImballaggio()).equals("S")) ? "checked=\"checked\"" : ""%>/>  NO <input type="radio" id="selezioneImballaggio_NO" name="selezioneImballaggio" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getSelezioneImballaggio()).equals("N")) ? "checked=\"checked\"" : ""%>/><br/>
Se si, indicare la destinazione <input type="text" id="selezioneImballaggioDestinazione" name="selezioneImballaggioDestinazione" class="editField" size="20" maxlength="30" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getSelezioneImballaggioDestinazione()) : ""%>"/> <br/>
In allevamento si pratica la "muta"? SI <input type="radio" id="muta_SI" name="muta" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getMuta()).equals("S")) ? "checked=\"checked\"" : ""%>/>  NO <input type="radio" id="muta_NO" name="muta" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getMuta()).equals("N")) ? "checked=\"checked\"" : ""%>/><br/>
<i>(vedi note Ministero salute prot. n. 23052 del 03/12/2013 e 14833 del 19/06/2017 e check list di seguito riportata)</i>
</td></tr></table>

<table>
<tr>
<% for (int r = 0; r<2; r++) { %>
<td>
<table>
<tr><th colspan="3">ELENCO CAPANNONI</th></tr>
<tr><td>Numero capannone</td><td>Ispezionato</td><td>n. capi rilevati al momento dell'ispezione</td></tr>
<% for (int m = 0; m<15; m++) { %>
<tr><td></td><td> SI <input type="checkbox" disabled/> NO <input type="checkbox" disabled/></td><td></td></tr>
<% } %>
</table>
</td>
<% } %>
</tr>
</table>

<div style="page-break-before:always">&nbsp; </div>  
<table cellpadding="10">
<tr><td>

<b>CONTROLLO APPARTENENTE AL CAMPIONE CONDIZIONALITA'</b>        SI <input type="radio" id="appartenenteCondizionalita_SI" name="appartenenteCondizionalita" value="S" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getAppartenenteCondizionalita()).equals("S")) ? "checked=\"checked\"" : ""%>/> NO <input type="radio" id="appartenenteCondizionalita_NO" name="appartenenteCondizionalita" value="N" <%=(ChecklistIstanza!=null && toHtml(ChecklistIstanza.getAppartenenteCondizionalita()).equals("N")) ? "checked=\"checked\"" : ""%>/> 

</td></tr>
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

PREAVVISO (max 48 ore)             <input disabled value="si" <%=(Ticket.getFlag_preavviso()!=null && !Ticket.getFlag_preavviso().equalsIgnoreCase("n")) ? "checked=\"checked\"" : ""%> type="checkbox"> SI <input disabled value="no" <%=(Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equalsIgnoreCase("n"))  ? "checked=\"checked\"" : ""%> type="checkbox"> NO  <br/>
Se SI in data <label class="layout"><%=toDateasString(Ticket.getData_preavviso_ba())%></label> tramite: <input disabled value="P" <%=(Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equalsIgnoreCase("P")) ? "checked=\"checked\"" : ""%> type="checkbox"> Telefono <input disabled value="T" <%=(Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equalsIgnoreCase("T")) ? "checked=\"checked\"" : ""%> type="checkbox"> Telegramma/lettera/fax <input disabled value="A" <%=(Ticket.getFlag_preavviso()!=null && Ticket.getFlag_preavviso().equalsIgnoreCase("A")) ? "checked=\"checked\"" : ""%> type="checkbox"> Altra forma 

</td></tr>
</table>

<div style="page-break-before:always">&nbsp; </div>
<table cellpadding="10">
<tr><td>

<b>LEGENDA NON CONFORMITA'</b> <br/>

<table cellpadding="10">
<tr><td><b>SCALA E LIVELLO DELLA NON CONFORMITA'</b></td><td> <b>AZIONI INTRAPRESE DALL'AUTORITA' COMPETENTE</b></td></tr>
<tr><td><b>SI - CONFORME</b></td><td> NESSUNA</td></tr>
<tr><td><b>no- non conforme n.c. minore categoria A </b></td><td> Richiesta di rimediare alle non conformita' entro un termine inferiore a tre mesi nessuna <br/> sanzione amministrativa o penale immediata</td></tr>
<tr><td><b>no- non conforme n.c. minore categoria B </b></td><td> Richiesta di rimediare alle non conformita' entro un termine superiore a tre mesi nessuna<br/> sanzione amministrativa o penale immediata</td></tr>
<tr><td><b>NO non conforme N.C. maggiore categoria C</b></td><td> sanzione amministrativa o penale immediata </td></tr>
<tr><td><b>NA non applicabile</b></td><td></td></tr>
<tr><td><b>OTTIMALE - superiore al requisito previsto</b></td><td> facoltativo (in aggiunta a conforme)</td></tr>
<tr><td><b>Evidenze:</b> </td> <td>Indicare ogni evidenza idonea a dimostrare conformita' o non conformita' alla normativa o<br/> requisiti superiori rispetto al livello minimo</td></tr>
</table>


</td></tr>
</table>

<div style="page-break-before:always">&nbsp; </div>
<table cellpadding="10">
<tr><td>
<center>ELEMENTO DI VERIFICA</center>
</td></tr>



<%
	String descrizionePrecedente = "";
for (int i = 0; i<DomandeList.size(); i++) {
	org.aspcf.modules.checklist_benessere.base.v5.Domanda domanda = (org.aspcf.modules.checklist_benessere.base.v5.Domanda) DomandeList.get(i);
	org.aspcf.modules.checklist_benessere.base.v5.Risposta risposta = (org.aspcf.modules.checklist_benessere.base.v5.Risposta) domanda.getRisposta();
%>
	
<% if (!descrizionePrecedente.equals(domanda.getDescrizione())){ %>	
<tr><th class="capitolo"><%=domanda.getDescrizione() %></th></tr>
<% }
descrizionePrecedente = domanda.getDescrizione(); %>

<tr><td>
<b><%=toHtml(domanda.getTitolo()) %></b><br/><br/>
<i><%=toHtml(domanda.getSottotitolo()) %></i>
</td></tr>
<tr><td>
<table cellpadding="10" width="100%" id="table_<%=domanda.getId()%>">
<tr><th colspan="<%=domanda.getEsiti().size()%>"><%=toHtml(domanda.getQuesito()) %></th></tr>

<tr>
<% for (int e = 0; e<domanda.getEsiti().size(); e++) {%>
<td><%=toHtml(domanda.getEsiti().get(e).getDescription()) %></td>
<% } %>
</tr>

<tr>
<% for (int e = 0; e<domanda.getEsiti().size(); e++) {%>
<td> <input type="radio" id="dom_<%=domanda.getId()%>_risposta_<%=domanda.getEsiti().get(e).getShortDescription() %>" name="dom_<%=domanda.getId() %>_risposta" value="<%=domanda.getEsiti().get(e).getShortDescription()%>" <%=(ChecklistIstanza!=null && risposta!=null && toHtml(risposta.getRisposta()).equals(domanda.getEsiti().get(e).getShortDescription())) ? "checked=\"checked\"" : "" %>/></td>
<% } %>
</tr>
<tr><td colspan="<%=domanda.getEsiti().size()%>"><b>EVIDENZE(*)</b></td></tr>
<tr><td colspan="<%=domanda.getEsiti().size()%>">  <input type="text" id="dom_<%=domanda.getId()%>_evidenze" name="dom_<%=domanda.getId()%>_evidenze" class="editField" size="50" value="<%=(ChecklistIstanza!=null && risposta!=null) ? toHtml(risposta.getEvidenze()) : ""%>"/>  </td></tr>
<tr><td colspan="<%=domanda.getEsiti().size()%>"><%=toHtml(domanda.getEvidenze()) %></td></tr>
</table>
</td></tr>

	
	
	<% }%> 


</table>

<div style="page-break-before:always">&nbsp; </div>
<table cellpadding="10"> 

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
<table cellpadding="10">
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
<center><b>PROVVEDIMENTI ADOTTATI</b></center>
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
<tr><td><b>Informativa in procura:</b> <input type="number" id="sanzInformativa" name="sanzInformativa" class="editField" size="4" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getSanzInformativa()) : ""%>"/> </td><td><b>Altro(specificare):</b> <input type="number" id="sanzAltro" name="sanzAltro" class="editField" size="4" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getSanzAltro()) : ""%>"/>  <input type="text" id="sanzAltroDesc" name="sanzAltroDesc" class="editField" size="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getSanzAltroDesc()) : ""%>"/> </td></tr>
</table>

</td></tr>
<tr><td>
<b>NOTE/OSSERVAZIONI DEL CONTROLLORE :</b> <input type="text" id="noteControllore" name="noteControllore" class="editField" size="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getNoteControllore()) : ""%>"/> 
</td></tr>
<tr><td>
<b>NOTE/OSSERVAZIONI DEL PROPRIETARIO/DETENTORE/CONDUTTORE PRESENTE ALL'ISPEZIONE</b> <input type="text" id="noteProprietario" name="noteProprietario" class="editField" size="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getNoteProprietario()) : ""%>"/> 
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
<b>Descrizione:</b><input type="text" id="prescrizioniEseguiteDescrizione" name="prescrizioniEseguiteDescrizione" class="editField" size="50" value="<%=(ChecklistIstanza!=null) ? toHtml(ChecklistIstanza.getPrescrizioniEseguiteDescrizione()) : ""%>"/>
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
circostanze debitamente giustificate, in particolare per esigenze connesse ad analisi chimiche o fisiche.</b><br/>
</td></tr></table>

<input type="hidden" id="dataControllo" name="dataControllo" value="<%=toDateasStringWithFormat(Ticket.getAssignedDate(), "yyyy-MM-dd")%>"/>

<br/>

<input type="button" name="salva" id="salvaTemporaneo" class="buttonClass" value="Salva Temporaneo" onclick="saveForm(this.form, 'temp'); return false;"/>

<input type="button" name="salva" id="salvaDefinitivo" class="buttonClass" value="Salva Definitivo" onclick="saveForm(this.form, 'def'); return false;"/>


<input type="hidden" readonly name="bozza" id="bozza" value="" />
<input type="hidden" readonly name="idControllo" id="idControllo" value="<%=Allevamento.getIdControllo()%>" />
<input type="hidden" readonly name="orgId" id="orgId" value="<%=Allevamento.getOrgId()%>" />
<input type="hidden" readonly name="stabId" id="stabId" value="<%=Allevamento.getIdStabilimento()%>" />
<input type="hidden" readonly name="specie" id="specie" value="<%=codice_specie%>" />

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
	


<script>

function rispondiCaso() {
	
	 var nomi = ["Rita", "Paolo", "Stefano", "Alessandro", "Uolter", "Antonio", "Carmela", "Viviana", "Valentino", "Rischio", "Impresa", "Vittoria", "Mandarino", "Ext", "US", "Caffe", "Altrove", "SPA", "Food", "Privata", "Coffee", "Angolo", "Bar"];
	 var inputs = document.getElementsByTagName('input');
	 var inputNamePrecedente="";
    for (i = 0; i < inputs.length; i++) {
    	    	
        if (inputs[i].type == 'radio' || inputs[i].type == 'checkbox') {
        	var random = Math.floor(Math.random() * 11);
          	 	if (random>5 || inputNamePrecedente!=inputs[i].name)
           			inputs[i].click();
        	}
        else if (inputs[i].type == 'text') {
        	if($(inputs[i]).attr("onkeyup")=='filtraInteri(this)'){
           		inputs[i].value = Math.floor((Math.random() * nomi.length-1) + 1) +''+ Math.floor((Math.random() * nomi.length-1) + 1) +''+ Math.floor((Math.random() * nomi.length-1) + 1);
        	}
        	else
           		inputs[i].value = nomi[Math.floor((Math.random() * nomi.length-1) + 1)] + " " + nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
    	}
        else if (inputs[i].type == 'number') {
        	var random1 = Math.floor(Math.random() * 11);
        	var random2 = Math.floor(Math.random() * 11);

        	if($(inputs[i]).attr("step")=='.01')
	        	inputs[i].value = random1+'.'+random2;
	        else
	        	inputs[i].value = random1;
    	}
        
        else if (inputs[i].type == 'date') {
        	
        	var date = new Date();
        	var currentDate = date.toISOString().slice(0,10);
			inputs[i].value = currentDate;
    	}
        
        inputNamePrecedente = inputs[i].name;
          }
   		
}

function rispondiSoloDomande() {
	
	 var nomi = ["Rita", "Paolo", "Stefano", "Alessandro", "Uolter", "Antonio", "Carmela", "Viviana", "Valentino", "Rischio", "Impresa", "Vittoria", "Mandarino", "Ext", "US", "Caffe", "Altrove", "SPA", "Food", "Privata", "Coffee", "Angolo", "Bar"];
	 var inputs = document.getElementsByTagName('input');
	 var inputNamePrecedente="";
   	for (i = 0; i < inputs.length; i++) {
   		
   		if (inputs[i].name.indexOf("dom_")>=0) {
   	    	
	       if (inputs[i].type == 'radio' || inputs[i].type == 'checkbox') {
	       	var random = Math.floor(Math.random() * 11);
	         	 	if (random>5 || inputNamePrecedente!=inputs[i].name)
	          			inputs[i].click();
	       	}
	       else if (inputs[i].type == 'text') {
	         inputs[i].value = nomi[Math.floor((Math.random() * nomi.length-1) + 1)] + " " + nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
	   	}
	       else if (inputs[i].type == 'number') {
	       	var random1 = Math.floor(Math.random() * 11);
	       	var random2 = Math.floor(Math.random() * 11);
	
	       	if($(inputs[i]).attr("step")=='.01')
		        	inputs[i].value = random1+'.'+random2;
		        else
		        	inputs[i].value = random1;
	   	}
	       
	       else if (inputs[i].type == 'date') {
	       	
	       	var date = new Date();
	       	var currentDate = date.toISOString().slice(0,10);
				inputs[i].value = currentDate;
	   	}
	       
	       inputNamePrecedente = inputs[i].name;
	         }
   	}
}


</script>

<%UserBean user = (UserBean) session.getAttribute("User");
if (user.getUserId()==5885) { %>	
<input type="button" id="caso" name="caso" style="background-color:yellow;" value="rispondi a caso a tutta la checklist (TEST)" onClick="rispondiCaso()"/>
<input type="button" id="casoD" name="casoD" style="background-color:lime" value="rispondi a caso solo alle domande (TEST)" onClick="rispondiSoloDomande()"/>
<% } %>

<script>
verificaStatoChecklist('<%=ChecklistIstanza.isBozza()%>');
</script>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

