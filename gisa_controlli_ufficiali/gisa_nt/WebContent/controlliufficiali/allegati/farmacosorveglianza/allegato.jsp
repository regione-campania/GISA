<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="Allevamento" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Ticket" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="codice_specie" class="java.lang.String" scope="request"/>
<jsp:useBean id="Checklist" class="org.aspcf.modules.checklist_farmacosorveglianza.base.ChecklistIstanza" scope="request"/>
<%@page import="java.util.regex.*"%>

<%@page import="org.aspcf.modules.checklist_farmacosorveglianza.base.*"%>

<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="controlliufficiali/allegati/farmacosorveglianza/css/screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="controlliufficiali/allegati/farmacosorveglianza/css/print.css" />

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
		window.opener.loadModalWindow();
        //window.opener.location.reload();
        window.opener.location.href="AllevamentiVigilanza.do?command=TicketDetails&id=<%=Ticket.getId()%>";
    	document.getElementById('stampa').style.display = 'block';

	}
	
}

function saveForm(form, tipo){
	
	if (tipo=='temp'){
		if(confirm('La scheda sarà aggiornata come richiesto, ma sarà ancora possibile modificarla. Procedere con il salvataggio?')) {
		 form.bozza.value = "true";
		 $(window).scrollTop(0);
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
		
		if(confirm('La scheda sarà aggiornata come richiesto ma i dati non saranno più modificabili. Assicurarsi di aver compilato tutti i campi obbligatori a seconda delle proprie esigenze. Procedere con il salvataggio definitivo?')) {
		 form.bozza.value = "false";
		 $(window).scrollTop(0);
		 loadModalWindow();
		 form.submit();
		}
	}
	
}

function checkForm(form){
	var esito = true;
	return esito;
}

function checkRisposta(risposta, form){ 
	var idDomanda = risposta.dataset.domanda;
	var giudizio = risposta.value;
	if (idDomanda == "16"){
		var elements = form.elements;
		if (giudizio == 'NA'){
			  for (i=0; i<elements.length; i++){
				    if (elements[i].dataset.sezione == "3" && elements[i].name != risposta.name){
		 		    	if (elements[i].type == 'text'){
				    		elements[i].value = '';
					    	elements[i].readOnly = true;
					    	elements[i].className = 'layout';
				    	}
				    	if (elements[i].type == 'radio'){
					    	elements[i].checked = false;
					    	elements[i].disabled = true;
				    	}
						if (elements[i].type == 'checkbox'){
					    	elements[i].checked = false;
					    	elements[i].disabled = true;
				    	}
				    }
				  }
			
		}
		else {
			  for (i=0; i<elements.length; i++){
				    if (elements[i].dataset.sezione == "3" && elements[i].name != risposta.name){
				    	if (elements[i].type == 'text'){
					    	elements[i].readOnly = false;
					    	elements[i].className = 'editField';
				    	}
				    	if (elements[i].type == 'radio'){
					    	elements[i].disabled = false;
				    	}
						if (elements[i].type == 'checkbox'){
					    	elements[i].disabled = false;
				    	}
				    }
				  }
		}
		
	}
	
}

function checkScortaPresente(val, form){
	var numDataAut = form.numDataAut;
	var veterinarioResponsabile = form.veterinarioResponsabile;
	
	if (val=='S'){
		numDataAut.readOnly = false;
		veterinarioResponsabile.readOnly = false;
		numDataAut.className = 'editField';
		veterinarioResponsabile.className = 'editField';
		
		 var elements = form.elements;
		  for (i=0; i<elements.length; i++){
		    if (elements[i].dataset.sezione == "1"){
		    	if (elements[i].type == 'text'){
			    	elements[i].readOnly = false;
			    	elements[i].className = 'editField';
		    	}
		    	if (elements[i].type == 'radio'){
			    	elements[i].disabled = false;
		    	}
				if (elements[i].type == 'checkbox'){
			    	elements[i].disabled = false;
		    	}
		    }
		  }
	}
	else if (val=='N') {
		numDataAut.value = '';
		veterinarioResponsabile.value = '';
		numDataAut.readOnly = true;
		veterinarioResponsabile.readOnly = true;
		numDataAut.className = 'layout';
		veterinarioResponsabile.className = 'layout';
		
		 var elements = form.elements;
		  for (i=0; i<elements.length; i++){
		    if (elements[i].dataset.sezione == "1"){
 		    	if (elements[i].type == 'text'){
		    		elements[i].value = '';
			    	elements[i].readOnly = true;
			    	elements[i].className = 'layout';
		    	}
		    	if (elements[i].type == 'radio'){
			    	elements[i].checked = false;
			    	elements[i].disabled = true;
		    	}
				if (elements[i].type == 'checkbox'){
			    	elements[i].checked = false;
			    	elements[i].disabled = true;
		    	}
		    }
		  }

	}
}

function ricalcolaPunteggio(form){
	var punteggio = 0;
	var punteggioAggiuntivo = 0;

	 var elements = form.elements;
	  for (i=0; i<elements.length; i++){
	    if (elements[i].dataset.sezione == "4"){
	    	if (elements[i].type == 'radio' || elements[i].type == 'checkbox'){
		    	if (elements[i].checked)
		    		punteggio = parseInt(punteggio, 10) + parseInt(elements[i].dataset.punteggio, 10);
	    	}
			
	    }
	    if (elements[i].dataset.sezione == "valutazione"){
	    	if (elements[i].type == 'checkbox'){
		    	if (elements[i].checked)
		    		punteggioAggiuntivo = parseInt(punteggioAggiuntivo, 10) + parseInt(elements[i].dataset.punteggio, 10);
	    	}
			
	    }
	  }
	
	punteggio = parseInt(punteggio, 10) + parseInt(punteggioAggiuntivo, 10);
	
	form.punteggioTotale.value = punteggio;
	form.punteggioAggiuntivo.value = punteggioAggiuntivo;

}
function gestisciPrescrizioni(val, form){
	if (val=='S'){
		document.getElementById("divPrescrizioni").style.display='block';
	}
	else if (val=='N'){
		document.getElementById("divPrescrizioni").style.display='none';
		
		 var elements = document.getElementById("divPrescrizioni").getElementsByTagName('input');
		 for (i=0; i<elements.length; i++){
		    	if (elements[i].type == 'radio' || elements[i].type == 'checkbox'){
			    	elements[i].checked = false;
		    	}
		    	if (elements[i].type == 'text' || elements[i].type == 'date'){
			    	elements[i].value = '';
		    	}
		    }
		  }
		
	}

</script>


<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div>


<form method="post" name="myform" id="myform" action="PrintModulesHTML.do?command=InsertChecklistFarmacosorveglianza&auto-populate=true">

<center><b>CONTROLLI DI FARMACOSORVEGLIANZA IN AZIENDA ZOOTECNICA (ANIMALI DPA)</b></center> <br/>

<center>
<table class="dettaglioTabella" width="100%" style="border: 1px solid black"> 
<tr><th colspan="4">INFORMAZIONI SPECIFICHE E RACCOLTA DATI AZIENDALI</th></tr>
<tr><td><b>REGIONE:</b> </td><td>CAMPANIA</td> <td><b>ASL: </b></td><td><%=Allevamento.getAsl()%></td></tr>
<tr><td>Data del controllo: </td><td><%=getShortDateLong(Ticket.getAssignedDate())%></td> <td>N. Check List: </td><td><input type="text" class="editField" id="numChecklist" name="numChecklist" value="<%=Checklist.getDati().getNumChecklist() %>"/></td></tr>
<tr><td>Veterinario ispettore: </td> <td><input type="text" class="editField" id="veterinarioIspettore" name="veterinarioIspettore" value="<%=Checklist.getDati().getVeterinarioIspettore() %>"/></td></tr>
<tr><td><b>Codice azienda:</b> </td><td><%=Allevamento.getN_reg()%></td> <td><b>Ragione sociale: </b></td><td><%=Allevamento.getName() %></td></tr>
<tr><td>Indirizzo azienda: </td><td><%=Allevamento.getIndirizzo()%> <%=Allevamento.getComune()%></td> </tr>
<tr><td>Indirizzo sede legale: </td><td><%=Allevamento.getIndirizzo_legale()%> </td> </tr>

<tr><td>Proprietario degli animali: </td><td><%=toHtml(Allevamento.getNominativo_proprietario())%></td> <td>Codice fiscale: </td> <td><%=Allevamento.getCf_prop()%></td></tr>
<tr><td>Conduttore/Detentore: </td><td><%=toHtml(Allevamento.getNominativo_detentore())%></td> <td>Codice fiscale: </td> <td><%=Allevamento.getCf_det()%></td></tr>
<tr><td>Tipologia struttura: </td><td><%=Allevamento.getTipologia_struttura()%></td> </tr>
<tr><td>Specie allevata: </td><td><%=Allevamento.getSpecie_allev() %></td> </tr>
<tr><td>Orientamento produttivo: </td><td><%=Allevamento.getTipologia_att()%></td> </tr>
<tr><td>Veterinario aziendale (se presente): </td><td></td> </tr>
</table>
</center>  
  
<br/>

<center>
<table class="dettaglioTabella" width="100%" style="border: 1px solid black"> 
<tr><th colspan="2">LEGENDA NON CONFORMITA'</th></tr>
<tr><td><b>SCALA E LIVELLO DELLA NON CONFORMITA'</b> </td><td>AZIONI INTRAPRESE DALL'AUTORITA' COMPETENTE</td></tr>
<tr><td><b>SI - CONFORME</b> </td><td>NESSUNA</td></tr>
<tr><td><b>no - non conforme (n.c. minore)</b> </td><td>Richiesta di rimediare alle non conformita' entro un termine dato (PRESCRIZIONE)</td></tr>
<tr><td><b>NO - non conforme (N.C. maggiore)</b> </td><td>Sanzione amministrativa o penale immediata (quando prevista)</td></tr>
<tr><td><b>NA - non applicabile</b> </td><td>Specificare sempre il motivo in campo note</td></tr>
<tr><td><b>Evidenze:</b> </td><td>Indicare ogni evidenza idonea a dimostrare conformita' o non conformita' alla normativa o requisiti superiori rispetto al livello minimo</td></tr>
</table>
</center>  

<br/>

<div style="page-break-before:always">&nbsp; </div>


<table cellpadding="10" class="domande" width="100%"> 

<col width="30%">
<col width="10%"><col width="10%"><col width="10%"><col width="10%">
<col width="30%">

<% 
for (int s = 0; s<Checklist.getSezioni().size(); s++) {
	Sezione sezione = Checklist.getSezioni().get(s); %>
	
	<tr><th colspan="6"><%=sezione.getSezione() %>	</th></tr>
	
	<% if (sezione.getId()<=3){ %>
	
		<% if (sezione.getId()==1){ %>
			<tr><td colspan="6">
			<input type="radio" onClick="checkScortaPresente(this.value, this.form)" value="S" id="presenteScorta_S" name="presenteScorta" <%=(toHtml(Checklist.getDati().getPresenteScorta()).equalsIgnoreCase("S")) ? "checked" : "" %> />
			Se presente, indicare Num e data aut.: <input type="text" class="editField" id="numDataAut" name="numDataAut" value="<%=Checklist.getDati().getNumDataAut() %>"/> Veterinario responsabile (ed eventuali delegati): <input type="text" class="editField" id="veterinarioResponsabile" name="veterinarioResponsabile" value="<%=Checklist.getDati().getVeterinarioResponsabile() %>"/><br/>
			<input type="radio" onClick="checkScortaPresente(this.value, this.form)" value="N" id="presenteScorta_N" name="presenteScorta" <%=(toHtml(Checklist.getDati().getPresenteScorta()).equalsIgnoreCase("N")) ? "checked" : "" %> />
			Se NON presente, passare alla sezione B</td></tr>
					
		<% } %>
		
	<tr><td colspan="2">ELEMENTO DI VERIFICA</td><td colspan="2"> GIUDIZIO DI CONFORMITA'</td><td colspan="2">NOTE/EVIDENZE</td></tr>
	
	<%for (int d = 0; d<sezione.getListaDomande().size(); d++){ 
		Domanda domanda = sezione.getListaDomande().get(d); %>
		<tr>
		<td colspan="2"><%=domanda.getDomanda() %></td> 
		<td colspan="2">
		<% for (int g = 0; g<domanda.getListaGiudizi().size(); g++){ 
			Giudizio giudizio = domanda.getListaGiudizi().get(g);%>
			
			<%if (giudizio.getTipo().equals("radio")){ %>		
			<input data-sezione="<%=sezione.getId() %>" data-domanda="<%=domanda.getId() %>" onClick="checkRisposta(this, this.form)" type="radio" value="<%=giudizio.getGiudizio() %>" id="risposta_radio_<%=domanda.getId()%>_<%=giudizio.getId() %>" name="risposta_radio_<%=domanda.getId()%>" <%=(giudizio.isRisposto()) ? "checked" : "" %> />
			<%=giudizio.getGiudizio() %>
			<% } else if (giudizio.getTipo().equals("checkbox")){ %>	
			<input data-sezione="<%=sezione.getId() %>" data-domanda="<%=domanda.getId() %>" onClick="checkRisposta(this, this.form)" type="checkbox" value="<%=giudizio.getGiudizio() %>" id="risposta_check_<%=domanda.getId()%>_<%=giudizio.getId() %>" name="risposta_check_<%=domanda.getId()%>_<%=giudizio.getId() %>" <%=(giudizio.isRisposto()) ? "checked" : "" %> />
			<%=giudizio.getGiudizio() %>
			<% } %>	<br/>
			
		<%} %>
		</td>  
		<td colspan="2"><%=domanda.getNote() %>
		<br/><br/>
		<input data-sezione="<%=sezione.getId() %>" data-domanda="<%=domanda.getId() %>" type="text" class="editField" id="risposta_evidenze_<%=domanda.getId() %>" name="risposta_evidenze_<%=domanda.getId() %>" value="<%=domanda.getEvidenze() %>"/>
		
		</td></tr>
	<% } %>
	
	<% if (sezione.getId() ==3) { %>
	<tr><td colspan="6"> <b> Il risultato del presente controllo sara' utilizzato per verificare il rispetto degli impegni di condizionalita' alla base dell'erogazione degli aiuti comunitari. Nel caso di presenza di non conformita' l'esito del controllo sara' elaborato dall'Organismo Pagatore.</b></td></tr>
	<% } %>
	
	<%} else if (sezione.getId()==4) { %>
	
	<tr><td>Considerare almeno gli ultimi 12 MESI</td> <td colspan="5">NB: Questa sezione e' obbligatoria nelle attivita' programmate di farmacosorveglianza</td></tr>
	
	<tr><td>ELEMENTO DI VERIFICA</td> <td>INSUFFICIENTE</td> <td>MIGLIORABILE</td> <td>OTTIMALE</td> <td>NA</td> <td>NOTE/EVIDENZE</td></tr>
	
	<%for (int d = 0; d<sezione.getListaDomande().size(); d++){ 
		Domanda domanda = sezione.getListaDomande().get(d); %>
		<tr>
		<td><%=domanda.getDomanda() %></td> 
		
		<% if (domanda.getListaGiudizi().size()<=5) {
			for (int g = 0; g<domanda.getListaGiudizi().size(); g++){ 
			Giudizio giudizio = domanda.getListaGiudizi().get(g); %>
			<%if (giudizio.getTipo().equals("radio") || giudizio.getTipo().equals("checkbox") || giudizio.getTipo().equals("")){ %>	
			<td> 
			<%if (giudizio.getTipo().equals("radio")){ %>		
			<input data-sezione="<%=sezione.getId() %>" data-domanda="<%=domanda.getId() %>" data-punteggio = "<%=giudizio.getPunti() %>" type="radio" onClick="ricalcolaPunteggio(this.form)" value="<%=giudizio.getGiudizio() %>" id="risposta_radio_<%=domanda.getId()%>_<%=giudizio.getId() %>" name="risposta_radio_<%=domanda.getId()%>" <%=(giudizio.isRisposto()) ? "checked" : "" %> /> (<%=giudizio.getPunti() %>) 
			<%=giudizio.getGiudizio() %> 
			<% } else if (giudizio.getTipo().equals("checkbox")){ %>	
			<input data-sezione="<%=sezione.getId() %>" data-domanda="<%=domanda.getId() %>" data-punteggio = "<%=giudizio.getPunti() %>" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="<%=giudizio.getGiudizio() %>" id="risposta_check_<%=domanda.getId()%>_<%=giudizio.getId() %>" name="risposta_check_<%=domanda.getId()%>_<%=giudizio.getId() %>" <%=(giudizio.isRisposto()) ? "checked" : "" %> />
			<%=giudizio.getGiudizio() %> 
			<% } %>	
			 </td>
			 <% } %>
		<% } } else { %>
			<td colspan="4">
			<%for (int g = 0; g<domanda.getListaGiudizi().size(); g++){ 
			Giudizio giudizio = domanda.getListaGiudizi().get(g); %>
			<%if (giudizio.getTipo().equals("radio") || giudizio.getTipo().equals("checkbox") || giudizio.getTipo().equals("")){ %>	
			<%if (giudizio.getTipo().equals("radio")){ %>		
			<input data-sezione="<%=sezione.getId() %>" data-domanda="<%=domanda.getId() %>" data-punteggio = "<%=giudizio.getPunti() %>" type="radio" onClick="ricalcolaPunteggio(this.form)" value="<%=giudizio.getGiudizio() %>" id="risposta_radio_<%=domanda.getId()%>_<%=giudizio.getId() %>" name="risposta_radio_<%=domanda.getId()%>" <%=(giudizio.isRisposto()) ? "checked" : "" %> />
			<%=giudizio.getGiudizio() %> 
			<% } else if (giudizio.getTipo().equals("checkbox")){ %>	
			<input data-sezione="<%=sezione.getId() %>" data-domanda="<%=domanda.getId() %>" data-punteggio = "<%=giudizio.getPunti() %>" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="<%=giudizio.getGiudizio() %>" id="risposta_check_<%=domanda.getId()%>_<%=giudizio.getId() %>" name="risposta_check_<%=domanda.getId()%>_<%=giudizio.getId() %>" <%=(giudizio.isRisposto()) ? "checked" : "" %> />
			<%=giudizio.getGiudizio() %> 
			<% } %>	 
			<%} %>
			<%} %>
			</td>
		<% } %>
				  
		<td><%=domanda.getNote() %><br/><br/>
			<input data-sezione="<%=sezione.getId() %>" data-domanda="<%=domanda.getId() %>" type="text" class="editField" id="risposta_evidenze_<%=domanda.getId() %>" name="risposta_evidenze_<%=domanda.getId() %>" value="<%=domanda.getEvidenze() %>"/>
		</td></tr>
	<% } %>
	
	<%} %>
	
<%}  %>
</table>



<div style="page-break-before:always">&nbsp; </div>


<table class="dettaglioTabella" width="100%" style="border: 1px solid black"> 
<tr><th colspan="2"><b>VALUTAZIONE DEL RISCHIO</b></th></tr>
<tr><td colspan="2">Sommare i punti in relazione al rischio inerente le implicazione di benessere animale legate alle dimensioni e tipologia di allevamento (PIANO NAZIONALE BENESSERE DEGLI ANIMALI DA REDDITO) indicato:</td></tr>
<tr><td><b>Tipologia di allevamento</b> </td><td><b>Punteggio aggiuntivo</b></td></tr>
<tr><td>SUINI > 40 capi o > 6 scrofe</td> <td>10 <input data-sezione="valutazione" data-punteggio="10" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="S" id="valutazioneTipologiaSuini" name="valutazioneTipologiaSuini" <%="S".equalsIgnoreCase(Checklist.getDati().getValutazioneTipologiaSuini()) ? "checked" : "" %>  /></td></tr>
<tr><td>VITELLI A CARNE BIANCA</td> <td>10 <input data-sezione="valutazione" data-punteggio="10" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="S" id="valutazioneTipologiaVitelli" name="valutazioneTipologiaVitelli" <%="S".equalsIgnoreCase(Checklist.getDati().getValutazioneTipologiaVitelli()) ? "checked" : "" %>/></td></tr>
<tr><td>ALTRI BOVINI > 50 capi</td> <td>10 <input data-sezione="valutazione" data-punteggio="10" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="S" id="valutazioneTipologiaAltriBovini" name="valutazioneTipologiaAltriBovini" <%="S".equalsIgnoreCase(Checklist.getDati().getValutazioneTipologiaAltriBovini()) ? "checked" : "" %>/></td></tr>
<tr><td>BROILER > 500 capi</td> <td>10 <input data-sezione="valutazione" data-punteggio="10" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="S" id="valutazioneTipologiaBroiler" name="valutazioneTipologiaBroiler" <%="S".equalsIgnoreCase(Checklist.getDati().getValutazioneTipologiaBroiler()) ? "checked" : "" %>/></td></tr>
<tr><td>OVAIOLE >250 capi</td> <td>5 <input data-sezione="valutazione" data-punteggio="5" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="S" id="valutazioneTipologiaOvaiole" name="valutazioneTipologiaOvaiole" <%="S".equalsIgnoreCase(Checklist.getDati().getValutazioneTipologiaOvaiole()) ? "checked" : "" %>/></td></tr>
<tr><td>TACCHINI & ALTRI AVICOLI > 250 capi</td> <td>10 <input data-sezione="valutazione" data-punteggio="10" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="S" id="valutazioneTipologiaTacchini" name="valutazioneTipologiaTacchini" <%="S".equalsIgnoreCase(Checklist.getDati().getValutazioneTipologiaTacchini()) ? "checked" : "" %>/></td></tr>
<tr><td>OVINI E CAPRINI > 50 capi</td> <td>5 <input data-sezione="valutazione" data-punteggio="5" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="S" id="valutazioneTipologiaOvini" name="valutazioneTipologiaOvini" <%="S".equalsIgnoreCase(Checklist.getDati().getValutazioneTipologiaOvini()) ? "checked" : "" %>/></td></tr>
<tr><td>BUFALI > 10 capi</td> <td>10 <input data-sezione="valutazione" data-punteggio="10" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="S" id="valutazioneTipologiaBufali" name="valutazioneTipologiaBufali" <%="S".equalsIgnoreCase(Checklist.getDati().getValutazioneTipologiaBufali()) ? "checked" : "" %>/></td></tr>
<tr><td>CONIGLI > 250 capi</td> <td>10 <input data-sezione="valutazione" data-punteggio="10" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="S" id="valutazioneTipologiaConigli" name="valutazioneTipologiaConigli" <%="S".equalsIgnoreCase(Checklist.getDati().getValutazioneTipologiaConigli()) ? "checked" : "" %>/></td></tr>
<tr><td>STRUZZI > 10 capi</td> <td>10 <input data-sezione="valutazione" data-punteggio="10" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="S" id="valutazioneTipologiaStruzzi" name="valutazioneTipologiaStruzzi" <%="S".equalsIgnoreCase(Checklist.getDati().getValutazioneTipologiaStruzzi()) ? "checked" : "" %>/></td></tr>
<tr><td>CAVALLI convivenza tra DPA e nDPA</td> <td>10 <input data-sezione="valutazione" data-punteggio="10" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="S" id="valutazioneTipologiaCavalli" name="valutazioneTipologiaCavalli" <%="S".equalsIgnoreCase(Checklist.getDati().getValutazioneTipologiaCavalli()) ? "checked" : "" %>/></td></tr>
<tr><td>PESCI tutti per produzione alimenti</td> <td>10 <input data-sezione="valutazione" data-punteggio="10" type="checkbox" onClick="ricalcolaPunteggio(this.form)" value="S" id="valutazioneTipologiaPesci" name="valutazioneTipologiaPesci" <%="S".equalsIgnoreCase(Checklist.getDati().getValutazioneTipologiaPesci()) ? "checked" : "" %>/></td></tr>
<tr><td>PUNTEGGIO AGGIUNTIVO</td> <td><input type="text" class="layout" id="punteggioAggiuntivo" name="punteggioAggiuntivo" value="<%=Checklist.getDati().getPunteggioAggiuntivo() %>"/></td></tr>
</table>

<br/><br/>

<table class="dettaglioTabella" width="100%" style="border: 1px solid black"> 

<tr><td>La parte ANTIBIOTICORESISTENZA si valuta in base al punteggio totale raggiunto: <input type="text" class="layout" id="punteggioTotale" name="punteggioTotale" value="<%=Checklist.getDati().getPunteggioTotale() %>"/></td></tr>
<tr><td><input type="radio" id="rischio" name="rischio" value="B" <%="B".equals(Checklist.getDati().getRischio()) ? "checked" : "" %>/>RISCHIO BASSO (fino a 60)</td></tr>
<tr><td><input type="radio" id="rischio" name="rischio" value="M" <%="M".equals(Checklist.getDati().getRischio()) ? "checked" : "" %>/>RISCHIO MEDIO (da 61 a 90)</td></tr>
<tr><td><input type="radio" id="rischio" name="rischio" value="A" <%="A".equals(Checklist.getDati().getRischio()) ? "checked" : "" %>/>RISCHIO ALTO (> 90)</td></tr>
<tr><td><b>NB</b>: in caso di sanzioni/denuncia nella parte del Controllo Ufficiale, l'allevamento si valuta ad "ALTO RISCHIO"<br/>Anche tutti i casi di denuncia di smarrimento registri e gli allevamenti in cui l'assenza di trattamento non sia coerente con la tipologia di animali allevati vengono</td></tr>
</table>

<br/><br/>

<table class="dettaglioTabella" width="100%" style="border: 1px solid black"> 
<tr><th>ESITO DEL CONTROLLO</th></tr>
<tr><td><input type="radio" id="esitoControllo_S" name="esitoControllo" value="S" <%="S".equals(Checklist.getDati().getEsitoControllo()) ? "checked" : "" %>/>FAVOREVOLE</td></tr>
<tr><td><input type="radio" id="esitoControllo_N" name="esitoControllo" value="N" <%="N".equals(Checklist.getDati().getEsitoControllo()) ? "checked" : "" %>/>SFAVOREVOLE</td></tr>
</table>

<br/><br/>

<table class="dettaglioTabella" width="100%" style="border: 1px solid black"> 
<tr><th colspan="2">PROVVEDIMENTI ADOTTATI</th></tr>
</table>

<b>PRESCRIZIONI</b><br/>
Sono state assegnate prescrizioni? <input type="radio" onClick="gestisciPrescrizioni(this.value, this.form)" id="prescrizioniAssegnate" name="prescrizioniAssegnate" value="S" <%="S".equals(Checklist.getDati().getPrescrizioniAssegnate()) ? "checked" : "" %>/> SI <input type="radio" onClick="gestisciPrescrizioni(this.value, this.form)" id="prescrizioniAssegnate" name="prescrizioniAssegnate" value="N" <%="N".equals(Checklist.getDati().getPrescrizioniAssegnate()) ? "checked" : "" %>/> NO <br/>

<div id="divPrescrizioni">

Se si, quali? <input type="text" class="editField" id="prescrizioniDescrizione" name="prescrizioniDescrizione" value="<%=Checklist.getDati().getPrescrizioniDescrizione()%>"/><br/>
Entro quale data dovranno essere eseguite? <input type="date" class="editField" id="prescrizioniData" name="prescrizioniData" value="<%=Checklist.getDati().getPrescrizioniData()%>"/><br/><br/>

<br/><br/>

<table class="dettaglioTabella" width="100%" style="border: 1px solid black"> 
<tr><th>SANZIONI APPLICATE</th></tr>
<tr><td><input type="checkbox" id="sanzioniBlocco" name="sanzioniBlocco" value="S" <%="S".equals(Checklist.getDati().getSanzioniBlocco()) ? "checked" : "" %>/> Blocco movimentazioni</td></tr>
<tr><td><input type="checkbox" id="sanzioniAbbattimento" name="sanzioniAbbattimento" value="S" <%="S".equals(Checklist.getDati().getSanzioniAbbattimento()) ? "checked" : "" %>/> Abbattimento capi</td></tr>
<tr><td><input type="checkbox" id="sanzioniAmministrativa" name="sanzioniAmministrativa" value="S" <%="S".equals(Checklist.getDati().getSanzioniAmministrativa()) ? "checked" : "" %>/> Amministrativa/pecuniaria</td></tr>
<tr><td><input type="checkbox" id="sanzioniSequestro" name="sanzioniSequestro" value="S" <%="S".equals(Checklist.getDati().getSanzioniSequestro()) ? "checked" : "" %>/> Sequestro capi</td></tr>
<tr><td><input type="checkbox" id="sanzioniInformativa" name="sanzioniInformativa" value="S" <%="S".equals(Checklist.getDati().getSanzioniInformativa()) ? "checked" : "" %>/> Informativa in procura <input type="text" class="editField" id="sanzioniInformativaDescrizione" name="sanzioniInformativaDescrizione" value="<%=Checklist.getDati().getSanzioniInformativaDescrizione()%>"/></td></tr>
<tr><td><input type="checkbox" id="sanzioniAltro" name="sanzioniAltro" value="S" <%="S".equals(Checklist.getDati().getSanzioniAltro()) ? "checked" : "" %>/> Altro(specificare) <input type="text" class="editField" id="sanzioniAltroDescrizione" name="sanzioniAltroDescrizione" value="<%=Checklist.getDati().getSanzioniAltroDescrizione()%>"/> </td></tr>
</table>

<br/><br/>

NOTE/OSSERVAZIONI DEL CONTROLLORE :  <input type="text" class="editField" id="noteControllore" name="noteControllore" value="<%=Checklist.getDati().getNoteControllore()%>"/><br/>
NOTE/OSSERVAZIONI DEL PROPRIETARIO/DETENTORE/CONDUTTORE PRESENTE ALL'ISPEZIONE :  <input type="text" class="editField" id="noteProprietario" name="noteProprietario" value="<%=Checklist.getDati().getNoteProprietario()%>"/><br/>
DATA CONTROLLO : <%=getShortDateLong(Ticket.getAssignedDate())%><br/>
NOME E COGNOME DEL PROPRIETARIO/DETENTORE/CONDUTTORE PRESENTE ALL'ISPEZIONE: <input type="text" class="editField" id="nomePresente" name="nomePresente" value="<%=Checklist.getDati().getNomePresente()%>"/><br/><br/>
FIRMA DEL PROPRIETARIO/DETENTORE/CONDUTTORE PRESENTE ALL'ISPEZIONE: _______________________<br/><br/>
NOME E COGNOME DEL CONTROLLORE: <input type="text" class="editField" id="nomeControllore" name="nomeControllore" value="<%=Checklist.getDati().getNomeControllore()%>"/><br/><br/>
FIRMA E TIMBRO DEL CONTROLLORE/I: _______________________________________________________<br/>

<br/><br/>

VERIFICA DELL'ESECUZIONE DELLE PRESCRIZIONI (da effettuare alla scadenza del tempo assegnato)<br/>
PRESCRIZIONI ESEGUITE:<br/>
<input type="radio" id="prescrizioniVerificaEseguite_S" name="prescrizioniVerificaEseguite" value="S" <%="S".equals(Checklist.getDati().getPrescrizioniVerificaEseguite()) ? "checked" : "" %>/> SI <input type="radio" id="prescrizioniVerificaEseguite_N" name="prescrizioniVerificaEseguite" value="N" <%="N".equals(Checklist.getDati().getPrescrizioniVerificaEseguite()) ? "checked" : "" %>/> NO<br/>
Descrizione: <input type="text" class="editField" id="prescrizioniVerificaDescrizione" name="prescrizioniVerificaDescrizione" value="<%=Checklist.getDati().getPrescrizioniVerificaDescrizione()%>"/><br/>
DATA VERIFICA : <input type="date" class="editField" id="prescrizioniVerificaData" name="prescrizioniVerificaData" value="<%=Checklist.getDati().getPrescrizioniVerificaData()%>"/><br/>
Nome e cognome del proprietario/detentore/conduttore presente all'ispezione: <input type="text" class="editField" id="nomePresenteVerifica" name="nomePresenteVerifica" value="<%=Checklist.getDati().getNomePresenteVerifica()%>"/><br/><br/>
Firma del proprietario/detentore/conduttore presente all'ispezione: _________________________________<br/><br/>
Nome e cognome del controllore:<input type="text" class="editField" id="nomeControlloreVerifica" name="nomeControlloreVerifica" value="<%=Checklist.getDati().getNomeControlloreVerifica()%>"/><br/><br/>
Firma e timbro del controllore/i: _______________________________________________________
</div>

<br/><br/>

<input type="button" name="salva" id="salvaTemporaneo" class="buttonClass" value="Salva Temporaneo" onclick="saveForm(this.form, 'temp'); return false;"/>
<input type="button" name="salva" id="salvaDefinitivo" class="buttonClass" value="Salva Definitivo" onclick="saveForm(this.form, 'def'); return false;"/>

<input type="hidden" readonly name="bozza" id="bozza" value="" />
<input type="hidden" readonly name="idControllo" id="idControllo" value="<%=Ticket.getId()%>" />
<input type="hidden" readonly name="orgId" id="orgId" value="<%=Ticket.getOrgId()%>" />


<script>
checkScortaPresente('<%=Checklist.getDati().getPresenteScorta()%>', document.getElementById("myform"));
gestisciPrescrizioni('<%=Checklist.getDati().getPrescrizioniAssegnate()%>',  document.getElementById("myform"));

if (document.getElementById("risposta_radio_16_47").checked)
	checkRisposta(document.getElementById("risposta_radio_16_47"), document.getElementById("myform"));
</script>

</form>

<br/>
<div id="stampa" style="display:none">
<jsp:include page="../../../gestione_documenti/boxDocumentaleNoAutomatico.jsp">
<jsp:param name="orgId" value="<%=request.getParameter("orgId") %>" />
<jsp:param name="tipo" value="ChecklistFarmacosorveglianza" />
<jsp:param name="idCU" value="<%=request.getParameter("idControllo") %>" />
<jsp:param name="url" value="<%=request.getParameter("url") %>" />
</jsp:include>
</div>
	

<script>
verificaStatoChecklist('<%=Checklist.isBozza()%>');
</script>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>


<script>

function rispondiCaso() {

	 var nomi = ["Prova", "Test", "Bovino", "Codice", "Recinto", "Farmaco", "Medicinale", "Campo", "Esempio", "Rischio", "Impresa", "Vittoria", "Controllo","Altrove", "SPA", "Food", "Privata"];
	 var inputs = document.getElementsByTagName('input');
	 var inputNamePrecedente="";
    for (i = 0; i < inputs.length; i++) {

    	if (inputs[i].type == 'radio' || inputs[i].type == 'checkbox') {
        	var random = Math.floor(Math.random() * 11);
          	 	if (random>5 || inputNamePrecedente!=inputs[i].name)
           			inputs[i].checked = true;
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

</script>

<%UserBean user = (UserBean) session.getAttribute("User");
if (user.getUserId()==5885) { %>	
<input type="button" id="caso" name="caso" style="background-color:yellow;" value="rispondi a caso a tutta la checklist (TEST)" onClick="rispondiCaso()"/>
<% } %>
