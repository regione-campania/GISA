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
<jsp:useBean id="Checklist" class="org.aspcf.modules.checklist_biosicurezza.base.ChecklistIstanza" scope="request"/>
<%@page import="java.util.regex.*"%>

<%@page import="org.aspcf.modules.checklist_biosicurezza.base.*"%>

<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="controlliufficiali/allegati/biosicurezza/css/screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="controlliufficiali/allegati/biosicurezza/css/print.css" />

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
		if(confirm('La scheda sarà aggiornata come richiesto. Vuoi procedere con il salvataggio?')) {
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
		
		if(confirm('La scheda sarà aggiornata come richiesto ma i dati non saranno più modificabili. Vuoi procedere con il salvataggio definitivo?')) {
		 form.bozza.value = "false";
		 loadModalWindow();
		 form.submit();
		}
	}
	
}

function checkForm(form){
	var esito = true;
	var msg = 'Impossibile salvare. Controllare i seguenti campi: \n\n';
	
	if (form.numTotaleAnimali.value=='') {
		msg+= "Indicare il numero totale di animali.\n";
		esito = false;
	}
	if (form.nomeCognomeProprietario.value=='') {
		msg+= "Indicare il nome del proprietario/detentore/conduttore presente.\n";
		esito = false;
	}
	if (!form.tipoSuini_1.checked && !form.tipoSuini_2.checked && !form.tipoSuini_3.checked) {
		msg+= "Indicare la tipologia di suini presenti.\n";
		esito = false;
	}
	
	if (!tutteDomandeRisposte()){
			msg+="Rispondere a tutte le domande.\n";
			esito = false;
		}
			
	if (!esito)
		alert(msg);
	return esito;
}


function tutteDomandeRisposte(){
	
	<%for (int i = 0; i<Checklist.getDomande().size(); i++) {
		Domanda domanda = Checklist.getDomande().get(i); %>
	
		var x = document.getElementsByName("dom_<%=domanda.getId()%>_risposta");
		var selezionato = false;
		
		for (var j = 0; j<x.length; j++){
			if (x[j].checked)
				selezionato = true;
		}
	
		document.getElementById("tr_<%=domanda.getId()%>").style.background='';
		if (!selezionato){
			document.getElementById("tr_<%=domanda.getId()%>").style.background='red';
			return false;
		}
		selezionato = false;
		
		<% } %>	
		return true;
	}

</script>

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div>


<form method="post" name="myform" action="PrintModulesHTML.do?command=InsertChecklistBiosicurezza&auto-populate=true">

<center>
<b>VALUTAZIONE DELLA BIOSICUREZZA</b><br/>
<b>SUINI</b><br/>
<b>UFFICIALE</b>
</center> <br/>

<center>
<table class="dettaglioAzienda" > <col width="50%">

<tr>
<td>CODICE AZIENDA</td> 
<td> <label class="layout"><%=Allevamento.getN_reg()%></label> </td>
</tr>

<tr>
<td>ID FISCALE</td>
<td><label class="layout"><%=Allevamento.getCf_prop()%></label></td>
</tr>


<tr>
<td>INDIRIZZO</td> 
<td><label class="layout"><%=Allevamento.getIndirizzo()%></label></td>
</tr>

<tr>
<td>CITTA'</td> 
<td><label class="layout"><%=Allevamento.getComune()%></label></td>
</tr>

<tr>
<td>ASL DI COMPETENZA</td> 
<td><label class="layout"><%=Allevamento.getAsl()%></label></td>
</tr>

<tr>
<td>CODICE AREA</td> 
<td><label class="layout">___________________</label></td>
</tr>

<tr>
<td>Latitudine</td> 
<td><label class="layout"><%=Allevamento.getLatitudine()%></label></td>
</tr>

<tr>
<td>Longitudine</td> 
<td><label class="layout"><%=Allevamento.getLongitudine()%></label></td>
</tr>

<tr>
<td>INDIRIZZO PRODUTTIVO<br/>(ciclo chiuso, ciclo aperto, svezzamento, ingrasso, svezzamento + ingrasso)</td> 
<td><label class="layout"><%=Allevamento.getTipologia_att()%></label></td>
</tr>

<tr>
<td>TIPOLOGIA DI ALLEVAMENTO<br/>(biologico, intensivo, semiestensivo)</td> 
<td><label class="layout"><label class="layout"><%=Allevamento.getTipologia_struttura()%></label></label></td>
</tr>

<tr>
<td>NUM TOTALE ANIMALI</td> 
<td><input class="editField" type="number" id="numTotaleAnimali" name="numTotaleAnimali" min="0" step="1" size="5" maxlength="5" value="<%=(Checklist!=null && Checklist.getDati()!=null) ? toHtml(Checklist.getDati().getNumTotaleAnimali()) : ""%>"/></td>
</tr>

<tr>
<td>NOME E COGNOME DEL PROPRIETARIO/DETENTORE/CONDUTTORE PRESENTE ALLISPEZIONE</td> 
<td><input class="editField" type="text" id="nomeCognomeProprietario" name="nomeCognomeProprietario" size="50" maxlength="50" value="<%=(Checklist!=null && Checklist.getDati()!=null) ? toHtml(Checklist.getDati().getNomeCognomeProprietario()) : ""%>"/></td>
</tr>

<tr>
<td>TIPOLOGIA DI SUINI PRESENTI</td> 
<td>
<input type = "radio" id = "tipoSuini_1" name = "tipoSuini" value = "1" <%=(Checklist!=null && Checklist.getDati()!=null) ? "1".equals(Checklist.getDati().getTipoSuini()) ? "checked" : "" : ""%>/>Suini all'ingrasso<br/>
<input type = "radio" id = "tipoSuini_2" name = "tipoSuini" value = "2" <%=(Checklist!=null && Checklist.getDati()!=null) ? "2".equals(Checklist.getDati().getTipoSuini()) ? "checked" : "" : ""%>/>Suini in svezzamento<br/>
<input type = "radio" id = "tipoSuini_3" name = "tipoSuini" value = "3" <%=(Checklist!=null && Checklist.getDati()!=null) ? "3".equals(Checklist.getDati().getTipoSuini()) ? "checked" : "" : ""%>/>Scrofe, Verri e suinetti sottoscrofa
</td>
</tr>


<tr>
<td>DATA</td> 
<td><label class="layout">___________________</label></td>
</tr>

<tr>
<td>NOME COMPILATORE</td> 
<td><label class="layout">___________________</label></td>
</tr>

</table>
</center>    

<div style="page-break-before:always">&nbsp; </div>


<table cellpadding="10" class="domande"> 
<% 
String sezionePrecedente = "";
for (int i = 0; i<Checklist.getDomande().size(); i++) {
	Domanda domanda = Checklist.getDomande().get(i); %>
	
	<% if (!domanda.getSezione().equals(sezionePrecedente)) {%>
	<tr><th colspan="6">QUESTIONARIO BIOSICUREZZA: <%=domanda.getSezione() %></th></tr>
	<tr><td></td><td></td> <td><b>SI</b></td> <td><b>NO</b></td> <td><b>N/A</b></td> <td><b>Motivo</b></td></tr>
	
	<% } %>
	
	<tr id="tr_<%=domanda.getId()%>">
	
	<td> <%=i+1%> </td>
	<td> <%=domanda.getDomanda() %> </td>
	
	<% for (int j = 0; j<domanda.getListaRisposte().size(); j++) {
		Risposta risposta = (Risposta) domanda.getListaRisposte().get(j);%>
		<td>
		<% if (risposta.getTipo().equals("radio")){ %>
		<input type="radio" id="dom_<%=domanda.getId() %>_risposta_<%=risposta.getId() %>" name="dom_<%=domanda.getId() %>_risposta" value="<%=risposta.getId() %>" <%=(risposta.getRisposta()!=null && risposta.isRisposto()) ? "checked=\"checked\"" : "" %>/>
		<%} else if (risposta.getTipo().equals("textarea")) { %>
		<textarea cols="20" rows="3" id="dom_<%=domanda.getId() %>_risposta_<%=risposta.getId() %>" name="dom_<%=domanda.getId() %>_risposta_<%=risposta.getId() %>"><%=risposta.getNote() %></textarea></td>
		<%} %>
		</td>
	<%} %>
	
	</tr>
	
<% sezionePrecedente = domanda.getSezione();
}  %>
</table>


<br/>

<input type="button" name="salva" id="salvaTemporaneo" class="buttonClass" value="Salva Temporaneo" onclick="saveForm(this.form, 'temp'); return false;"/>

<input type="button" name="salva" id="salvaDefinitivo" class="buttonClass" value="Salva Definitivo" onclick="saveForm(this.form, 'def'); return false;"/>

<input type="hidden" readonly name="bozza" id="bozza" value="" />
<input type="hidden" readonly name="idControllo" id="idControllo" value="<%=Ticket.getId()%>" />
<input type="hidden" readonly name="orgId" id="orgId" value="<%=Ticket.getOrgId()%>" />
<input type="hidden" readonly name="specie" id="specie" value="0122" />

</form>

<br/>
<div id="stampa" style="display:none">
<jsp:include page="../../../gestione_documenti/boxDocumentaleNoAutomatico.jsp">
<jsp:param name="orgId" value="<%=request.getParameter("orgId") %>" />
<jsp:param name="extra" value="<%=request.getParameter("specie") %>" />
<jsp:param name="tipo" value="ChecklistBiosicurezzaSuini" />
<jsp:param name="idCU" value="<%=request.getParameter("idControllo") %>" />
<jsp:param name="url" value="<%=request.getParameter("url") %>" />
</jsp:include>
</div>
	

<script>
verificaStatoChecklist('<%=Checklist.isBozza()%>');
</script>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>















































<script>

function rispondiSoloDomande() {
	
	 var nomi = ["Test", "Prova", "Uno", "Due", "Tre", "Quattro", "Cinque", "Sei", "Sette", "Rischio", "Impresa", "Azienda", "Alpha", "Bravo", "Sierra", "Echo", "Novembre", "SPA", "Delta", "Kilo", "Coffee", "Angolo", "Oscar"];
	 var inputs = document.getElementsByTagName('input');
	 var inputNamePrecedente="";
   	for (i = 0; i < inputs.length; i++) {
   		
   	    	
	       if (inputs[i].type == 'radio' || inputs[i].type == 'checkbox') {
	       	var random = Math.floor(Math.random() * 11);
	         	 	if (random>5 || inputNamePrecedente!=inputs[i].name)
	          			inputs[i].click();
	       	}
	       else if (inputs[i].type == 'textarea') {
	         inputs[i].value = nomi[Math.floor((Math.random() * nomi.length-1) + 1)] + " " + nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
	   	}
	      
	       inputNamePrecedente = inputs[i].name;
	         
   	}
	  inputs = document.getElementsByTagName('textarea');

   	for (i = 0; i < inputs.length; i++) {
   		 if (inputs[i].style.display!='none')
   			 inputs[i].value = nomi[Math.floor((Math.random() * nomi.length-1) + 1)] + " " + nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
   		 else
   			 inputs[i].value = '';
	   }
}


</script>


<%UserBean user = (UserBean) session.getAttribute("User");
if (user.getUserId()==5885) { %>
<input type="button" id="casoD" name="casoD" style="background-color:lime" value="rispondi a caso solo alle domande (TEST)" onClick="rispondiSoloDomande()"/>
<% } %>

