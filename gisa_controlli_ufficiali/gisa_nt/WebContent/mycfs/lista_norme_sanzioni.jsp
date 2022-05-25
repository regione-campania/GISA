<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcf.modules.controlliufficiali.base.Norma"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../../initPage.jsp" %>

<jsp:useBean id="listaNorme" class="java.util.ArrayList" scope="request" />

<script>

function mostraNascondiBottoni(mostranascondi){
	var inputs = document.getElementById("tableNorme").getElementsByTagName("input");
	for (var i = 0; i < inputs.length; ++i) {
	    if (inputs[i].type == 'button') {
	    	inputs[i].style.display=mostranascondi;
	    }
	}	
}

function inserisci (code, ordinamento){
	
	mostraNascondiBottoni('none');
	
	var tr = document.getElementById('inserisci_'+code);
	tr.innerHTML = '<td><input type="text" id="description" name="description" size="60" style="text-transform: none !important"/> <input type="hidden" id="ordinamento" name="ordinamento" value="'+ordinamento+'"/> </td> <td><input type="checkbox" checked id="viewDiffida" name="viewDiffida"/></td> <td><input type="checkbox" id="competenzaUod" name="competenzaUod" checked="checked"/></td> <td><input type="text" id="codiceTariffa" name="codiceTariffa" value="" size="4" style="text-transform: none !important"/></td>  <td><input type="checkbox" id="enabled" name="enabled" checked disabled/></td>  <td><input type="button" value="salva" onClick="checkFormInserisci(this.form); return false;"/> <input type="button" value="annulla" onClick="annullaInserisci('+code+'); return false;"/> </td>';
	tr.style.display='table-row';
}

function annullaInserisci (code){
	
	var tr = document.getElementById('inserisci_'+code);
	tr.innerHTML = '';
	tr.style.display='none';
	
	mostraNascondiBottoni('table-row');
	
}

function inserisciCapitolo (code, ordinamento){
	
	mostraNascondiBottoni('none');
	
	var tr = document.getElementById('inserisciCapitolo_'+code);
	tr.innerHTML = '<th colspan="5"><input type="text" id="description" name="description" size="60" style="text-transform: none !important"/> <input type="hidden" id="ordinamento" name="ordinamento" value="'+ordinamento+'"/>  <input type="hidden" id="gruppo" name="gruppo" checked value="on"/> <input type="hidden" id="enabled" name="enabled" checked disabled/></th>  <td><input type="button" value="salva" onClick="checkFormInserisciCapitolo(this.form); return false;"/> <input type="button" value="annulla" onClick="annullaInserisciCapitolo('+code+'); return false;"/> </td>';
	tr.style.display='table-row';
}

function annullaInserisciCapitolo (code){
	
	var tr = document.getElementById('inserisciCapitolo_'+code);
	tr.innerHTML = '';
	tr.style.display='none';
	
	mostraNascondiBottoni('table-row');
	
}

function modifica (code, description, viewDiffida, enabled, ordinamento, competenzaUod, codiceTariffa){
	
	mostraNascondiBottoni('none');
	
	var trDettaglio = document.getElementById('dettaglio_'+code);
	trDettaglio.style.display='none';
	
	var tr = document.getElementById('modifica_'+code);
	tr.innerHTML = '<td><input type="text" id="description" name="description" value="'+description+'" size="60" style="text-transform: none !important"/> <input type="hidden" id="code" name="code" value="'+code+'"/> </td> <td><input type="checkbox" id="viewDiffida" name="viewDiffida"' + (viewDiffida=='true' ? 'checked="checked"' : '') + '/></td> <td><input type="checkbox" id="competenzaUod" name="competenzaUod"' + (competenzaUod=='true' ? 'checked="checked"' : '') + '/></td> <td><input type="text" id="codiceTariffa" name="codiceTariffa" value="'+codiceTariffa+'" size="4" style="text-transform: none !important"/></td>  <td><input type="checkbox" id="enabled" name="enabled"' + (enabled=='true' ? 'checked="checked"' : '') + '/> <input type="hidden" id="ordinamento" name="ordinamento" value="'+ordinamento+'"/></td>  <td><input type="button" value="salva" onClick="checkFormModifica(this.form); return false;"/> <input type="button" value="annulla" onClick="annullaModifica('+code+'); return false;"/> </td>';
	tr.style.display='table-row';
}


function annullaModifica (code){
	
	var tr = document.getElementById('modifica_'+code);
	tr.innerHTML = '';
	tr.style.display='none';
	
	var trDettaglio = document.getElementById('dettaglio_'+code);
	trDettaglio.style.display='table-row';
	
	mostraNascondiBottoni('table-row');
	
}

function checkFormInserisci (form){
if (form.description.value.trim()==''){
	alert('Attenzione. Indicare una descrizione.');
	return false;
}

if (form.codiceTariffa.value.trim()=='' && form.competenzaUod.checked){
	alert('Attenzione. Indicare un codice tariffa in caso di competenza UOD.');
	return false;
}

if (confirm('Inserire la norma indicata?')){
	loadModalWindow();
	form.action='MyCFS.do?command=InsertNormeSanzioni';
	form.submit();
}
	
}

function checkFormModifica (form){
	
	if (form.description.value.trim()==''){
		alert('Attenzione. Indicare una descrizione.');
		return false;
	}

	if (form.codiceTariffa.value.trim()=='' && form.competenzaUod.checked){
		alert('Attenzione. Indicare un codice tariffa in caso di competenza UOD.');
		return false;
	}
	
	if (confirm('Aggiornare la norma indicata? ATTENZIONE. Aggiornamento NON retroattivo.')){
		loadModalWindow();
		form.action='MyCFS.do?command=UpdateNormeSanzioni';
		form.submit();
	}
		
	}
	
function checkFormInserisciCapitolo (form){
	if (form.description.value==''){
		alert('Attenzione. Indicare una descrizione.');
		return false;
	}

	if (confirm('Inserire il capitolo indicato?')){
		loadModalWindow();
		form.action='MyCFS.do?command=InsertNormeSanzioni';
		form.submit();
	}
		
	}

</script>
<center>
 <table style="border: 1px solid black" width="50%"><tr><td>
Di seguito sono riportate le norme attualmente presenti nel sistema.<br/>
<input type="checkbox" checked disabled> DIFFIDABILE: La norma e' selezionabile in fase di diffida.<br/>
<input type="checkbox" checked disabled> COMPETENZA UOD: La norma e' soggetta alla generazione di un Avviso di Pagamento (contenente il CODICE TARIFFA) in cooperazione applicativa con PagoPA.<br/>
<input type="checkbox" disabled> ATTIVA: La norma non e' selezionabile nel sistema e non e' piu' modificabile, ma e' solo consultabile nel dettaglio delle diffide/sanzioni in cui e' gia' stata inserita.<br/>
<input type="button" value="Modifica"/>: La norma viene modificata. La modifica sara' valida dal momento dell'operazione in poi. Tutte le diffide/sanzioni in cui e' stata inserita continueranno a vedere la versione pre-modifica.<br/>
<input type="button" value="inserisci norma"/>: La norma viene inserita nel capitolo selezionato.<br/>
<input type="button" value="inserisci capitolo"/>: Il capitolo viene inserito nella posizione selezionata.<br/>
</td></tr></table>
</center>
<br/>


<form name="addAccount"	action="" method="post">

<table class="details" id="tableNorme">
<% 
String ultimoOrdinamento = "";

for (int i = 0; i<listaNorme.size(); i++) {
Norma norma = (Norma) listaNorme.get(i);
if (norma.isGruppo()){%>

<tr><td colspan="6"><br/><br/></td></tr>
<tr><th colspan="6"><input type="button" value="inserisci capitolo" onClick="inserisciCapitolo('<%=norma.getCode()%>', '<%=ultimoOrdinamento%>'); return false;"/></td></tr>
<tr style="display:none" id="inserisciCapitolo_<%=norma.getCode()%>"></tr>

<tr><td colspan="6"><br/></td></tr>
<tr><th colspan="5"><%=norma.getDescription()%></th> <td><input type="button" value="inserisci norma" onClick="inserisci('<%=norma.getCode()%>', '<%=norma.getOrdinamento()%>'); return false;"/></td></tr>
<tr><th>Descrizione</th><th>Diffidabile</th><th>Competenza UOD</th><th>Codice tariffa</th><th>Attiva</th><th>Operazioni</th></tr>

<tr style="display:none" id="inserisci_<%=norma.getCode()%>"></tr>

<%} else { %>

<tr id="dettaglio_<%=norma.getCode()%>"><td style="text-transform: none !important"><%=norma.getDescription()%></td>
<td><input type="checkbox" disabled id="diffida_<%=norma.getCode()%>" name ="diffida_<%=norma.getCode()%>" <%=norma.isViewDiffida() ? "checked=\"checked\"" : ""%>/></td>
<td><input type="checkbox" disabled id="competenzaUod_<%=norma.getCode()%>" name ="competenzaUod_<%=norma.getCode()%>" <%=norma.isCompetenzaUod() ? "checked=\"checked\"" : ""%>/></td>
<td><input type="text" disabled id="codiceTariffa_<%=norma.getCode()%>" name ="codiceTariffa_<%=norma.getCode()%>" value="<%=toHtml(norma.getCodiceTariffa())%>"/></td>
<td><input type="checkbox" disabled id="enabled_<%=norma.getCode()%>" name ="enabled_<%=norma.getCode()%>" <%=norma.isEnabled() ? "checked=\"checked\"" : ""%>/></td>
<td>
<%if (norma.isEnabled()) { %><input type="button" value="Modifica" onClick="modifica('<%=norma.getCode()%>', '<%=norma.getDescription()%>', '<%=norma.isViewDiffida()%>', '<%=norma.isEnabled()%>', '<%=norma.getOrdinamento()%>', '<%=norma.isCompetenzaUod() %>', '<%=toHtml(norma.getCodiceTariffa()) %>'); return false;"><%} %> </td>
</tr>

<tr style="display:none" id="modifica_<%=norma.getCode()%>"></tr>


<% } 
ultimoOrdinamento = norma.getOrdinamento();
} %>
</table>

</form>