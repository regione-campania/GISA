<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="../../../../initPage.jsp" %>

<%@page import="org.aspcfs.modules.schedesupplementari.base.*"%>
<jsp:useBean id="Istanza" class="org.aspcfs.modules.schedesupplementari.base.IstanzaScheda" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="schedesupplementari/css/screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="schedesupplementari/css/print.css" />

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script>
function saveForm(form){
	if (confirm("Salvare la scheda?")){
		loadModalWindow();
		form.submit();
	}
}
</script>

<table class="tableScheda"><col width="30%"><tr><td rowspan="3"><div class="boxIdDocumento"></div><div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div></td><td><%=Istanza.getRagioneSocialeImpresa() %></td></tr><tr><td><%=Istanza.getNomeLinea() %></td></tr><tr><td><b><%=Istanza.getDescrizioneScheda() %></b></td></tr></table>

<form method="post" name="myform" action="GestioneSchedeSupplementari.do?command=InsertScheda&auto-populate=true">

<table class="tableScheda" id="scheda" name="scheda">
<col width="5%"/>

<tr>
<th colspan="6"><b>CORSO N. <input type="text" id="corsoNum" name="corsoNum" size="10" maxlength="10" class="editField" value="<%=(Istanza!=null) ? toHtml(Istanza.getDatiSchedaFormazioneIaa().getCorsoNum()) : ""%>"/> ANNO <input type="text" id="corsoAnno" name="corsoAnno" size="10" maxlength="10" class="editField" value="<%=(Istanza!=null) ? toHtml(Istanza.getDatiSchedaFormazioneIaa().getCorsoAnno()) : ""%>"/></b> </th>
</tr>

<tr>
<td colspan="6"><input type="radio" id="corsoTipoA" name="corsoTipo" class="editField" value="A" <%=(Istanza!=null) ? toHtml(Istanza.getDatiSchedaFormazioneIaa().getCorsoTipo()).equals("A") ? "checked"  : "" : ""%>/> CORSO PROPEDEUTICO <input type="radio" id="corsoTipoB" name="corsoTipo" class="editField" value="B" <%=(Istanza!=null) ? toHtml(Istanza.getDatiSchedaFormazioneIaa().getCorsoTipo()).equals("B") ? "checked"  : "" : ""%>/> CORSO BASE <input type="radio" id="corsoTipoC" name="corsoTipo" class="editField" value="C" <%=(Istanza!=null) ? toHtml(Istanza.getDatiSchedaFormazioneIaa().getCorsoTipo()).equals("C") ? "checked"  : "" : ""%>/> CORSO AVANZATO </td>
</tr>

<tr>
<td></th>
<th colspan="3">SEDE DELLE LEZIONI (PARTE TEORICA E PRATICA)</th>
<th>DATE LEZIONI</th>
<th>ORARI LEZIONI</th>
</tr>

<tr>
<th></th>
<th>Comune</th>
<th>Via</th>
<th>presso</th>
<th></th>
<th></th>
</tr>

<%for (int i = 1; i<=14; i++){ 

%>   
<tr>
<td><%=i %></td>
<td><input type="text" id="lezioniComune_<%=i %>" name="lezioniComune_<%=i %>" class="editField" value="<%=Istanza!=null && Istanza.getDatiSchedaFormazioneIaa().getListaSedeLezioni().size()>0 ? Istanza.getDatiSchedaFormazioneIaa().getListaSedeLezioni().get(i-1).getComune() : ""%>"/></td>
<td><input type="text" id="lezioniVia_<%=i %>" name="lezioniVia_<%=i %>" class="editField" value="<%=Istanza!=null && Istanza.getDatiSchedaFormazioneIaa().getListaSedeLezioni().size()>0 ? Istanza.getDatiSchedaFormazioneIaa().getListaSedeLezioni().get(i-1).getVia() : ""%>"/></td>
<td><input type="text" id="lezioniPresso_<%=i %>" name="lezioniPresso_<%=i %>" class="editField" value="<%=Istanza!=null && Istanza.getDatiSchedaFormazioneIaa().getListaSedeLezioni().size()>0 ? Istanza.getDatiSchedaFormazioneIaa().getListaSedeLezioni().get(i-1).getPresso() : ""%>"/></td>
<td><input type="date" id="lezioniDate_<%=i %>" name="lezioniDate_<%=i %>" class="editField" value="<%=Istanza!=null && Istanza.getDatiSchedaFormazioneIaa().getListaSedeLezioni().size()>0 ? Istanza.getDatiSchedaFormazioneIaa().getListaSedeLezioni().get(i-1).getDateLezioni() : ""%>"/></td>
<td><input type="text" id="lezioniOrari_<%=i %>" name="lezioniOrari_<%=i %>" class="editField" value="<%=Istanza!=null && Istanza.getDatiSchedaFormazioneIaa().getListaSedeLezioni().size()>0 ? Istanza.getDatiSchedaFormazioneIaa().getListaSedeLezioni().get(i-1).getOrari() : ""%>"/></td>
</tr>
<%} %>

<tr>
<th colspan="6"><b>COMPONENTI DELLA SEGRETERIA SCIENTIFICA</b> </th>
</tr>

<tr>
<th></th>
<th>NOME</th>
<th colspan="2">COGNOME</th>
<th colspan="2">MACROSETTORI/AREA SCIENTIFICA</th>
</tr>

<%for (int i = 1; i<=5; i++){ %>
<tr>
<td><%=i %></td>
<td><input type="text" id="segreteriaNome_<%=i %>" name="segreteriaNome_<%=i %>" class="editField" value="<%=Istanza!=null && Istanza.getDatiSchedaFormazioneIaa().getListaComponenteSegreteria().size()>0 ? Istanza.getDatiSchedaFormazioneIaa().getListaComponenteSegreteria().get(i-1).getNome() : ""%>"/></td>
<td colspan="2"><input type="text" id="segreteriaCognome_<%=i %>" name="segreteriaCognome_<%=i %>" class="editField" value="<%=Istanza!=null  && Istanza.getDatiSchedaFormazioneIaa().getListaComponenteSegreteria().size()>0 ? Istanza.getDatiSchedaFormazioneIaa().getListaComponenteSegreteria().get(i-1).getCognome() : ""%>"/></td>
<td colspan="2"> <%= (i==1 ? "SCIENZE MEDICHE" :  i==2 ? "SCIENZE VETERINARIE" :  i==3 ? "FILOSOFIA/PEDAGOGIA/PSICOLOGIA" :  i==4 ? "ZOOLOGIA/FISIOLOGIA/BIOLOGIA APPLICATA" :  i==5 ? "NORMATIVA ED ALTRI CONTRIBUTI DIDATTICI" :  "") %>
</td>
</tr>
<%} %>

<tr>
<th colspan="6"><b>RESPONSABILE SCIENTIFICO</b> </th>
</tr>

<tr>
<th></th>
<th>NOME</th>
<th colspan="2">COGNOME</th>
<th colspan="2">CF</th>
</tr>

<tr>
<td>1</td>
<td><input type="text" id="responsabileNome" name="responsabileNome" class="editField" value="<%=(Istanza!=null) ? toHtml(Istanza.getDatiSchedaFormazioneIaa().getResponsabileNome()) : ""%>"/></td>
<td colspan="2"><input type="text" id="responsabileCognome" name="responsabileCognome" class="editField" value="<%=(Istanza!=null) ? toHtml(Istanza.getDatiSchedaFormazioneIaa().getResponsabileCognome()) : ""%>"/></td>
<td colspan="2"><input type="text" id="responsabileCf" name="responsabileCf" class="editField" value="<%=(Istanza!=null) ? toHtml(Istanza.getDatiSchedaFormazioneIaa().getResponsabileCodiceFiscale()) : ""%>"/></td>
</tr>

<tr>
<th colspan="6"><b>ELENCO DEGLI ISCRITTI</b> </th>
</tr>

<tr>
<th></th>
<th>NOME</th>
<th colspan="2">COGNOME</th>
<th colspan="2">CF</th>
</tr>

<%for (int i = 1; i<=32; i++){ %>
<tr>
<td><%=i %></td>
<td><input type="text" id="iscrittoNome_<%=i %>" name="iscrittoNome_<%=i %>" class="editField" value="<%=Istanza!=null && Istanza.getDatiSchedaFormazioneIaa().getListaIscritto().size()>0 ? Istanza.getDatiSchedaFormazioneIaa().getListaIscritto().get(i-1).getNome() : ""%>"/></td>
<td colspan="2"><input type="text" id="iscrittoCognome_<%=i %>" name="iscrittoCognome_<%=i %>" class="editField" value="<%=Istanza!=null && Istanza.getDatiSchedaFormazioneIaa().getListaIscritto().size()>0 ? Istanza.getDatiSchedaFormazioneIaa().getListaIscritto().get(i-1).getCognome() : ""%>"/></td>
<td colspan="2"><input type="text" id="iscrittoCf_<%=i %>" name="iscrittoCf_<%=i %>" class="editField" value="<%=Istanza!=null && Istanza.getDatiSchedaFormazioneIaa().getListaIscritto().size()>0 ? Istanza.getDatiSchedaFormazioneIaa().getListaIscritto().get(i-1).getCodiceFiscale() : ""%>"/></td>
</tr>
<%} %>

</table>

<br/><br/>

<% if (Istanza.getEnteredBy()>0){ %>
<table class="tableScheda">
<tr><td>Inserita da</td><td> <dhv:username id="<%= Istanza.getEnteredBy() %>" /></td> </tr>
<tr><td>Data</td><td><%=toDateasString(Istanza.getEntered()) %></td></tr>
</table>

<br/><br/>
<%} %>

<center>
<table>
<tr>
<td><input type="button" value="ANNULLA" onClick="if (confirm('Attenzione. I dati non salvati saranno annullati.')) {window.history.back();}"/></td>
<td><dhv:permission name="gestione-schede-supplementari-add"> 
<input type="button" name="salva" id="salva" value="Salva" onclick="saveForm(this.form); return false;"/>
</dhv:permission></td>
</tr>
</table>
</center>

<br/><br/>

<input type="hidden" id="riferimentoId" name="riferimentoId" value="<%=Istanza.getRiferimentoId()%>"/>
<input type="hidden" id="riferimentoIdNomeTab" name="riferimentoIdNomeTab" value="<%=Istanza.getRiferimentoIdNomeTab()%>"/>
<input type="hidden" id="idIstanzaLinea" name="idIstanzaLinea" value="<%=Istanza.getIdIstanzaLinea()%>"/>
<input type="hidden" id="numScheda" name="numScheda" value="<%=Istanza.getNumScheda()%>"/>

</form>

<center>
<div id="stampa">
<jsp:include page="/gestione_documenti/boxDocumentaleNoAutomatico.jsp"> 
<jsp:param name="orgId" value="<%=Istanza.getRiferimentoIdNomeTab().equals("organization") ? Istanza.getRiferimentoId() : "-1" %>" />
<jsp:param name="stabId" value="<%=Istanza.getRiferimentoIdNomeTab().equals("opu_stabilimento") ? Istanza.getRiferimentoId() : "-1" %>" />
<jsp:param name="altId" value="<%=Istanza.getRiferimentoIdNomeTab().equals("sintesis_stabilimento") ? Istanza.getRiferimentoId() : "-1" %>" />
<jsp:param name="extra" value="<%=Istanza.getNumScheda() %>" />
<jsp:param name="tipo" value="SchedaSupplementare" />
</jsp:include>
</div>
</center>


























<script>

function rispondiCaso() {
	
	 var nomi = ["Rita", "Paolo", "Stefano", "Alessandro", "Uolter", "Antonio", "Carmela", "Viviana", "Valentino", "Rischio", "Impresa", "Vittoria", "Mandarino", "Ext", "US", "Caffe", "Altrove", "SPA", "Food", "Privata", "Coffee", "Angolo", "Bar"];
	 var inputs = document.getElementById("scheda").getElementsByTagName('input');
	 var inputNamePrecedente="";
    for (i = 0; i < inputs.length; i++) {
    	    	
        if (inputs[i].type == 'radio' || inputs[i].type == 'checkbox') {
        	var random = Math.floor(Math.random() * 11);
          	 	if (random>5 || inputNamePrecedente!=inputs[i].name)
           			inputs[i].click();
        	}
        else if (inputs[i].type == 'text') {
        	inputs[i].value = nomi[Math.floor((Math.random() * nomi.length-1) + 1)] + " " + nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
    	}
        else if (inputs[i].type == 'number') {
      
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

<% UserBean user = (UserBean) session.getAttribute("User");
if (user.getUserId()==5885) { %>	
<center>
<input type="button" id="caso" name="caso" class="blueBigButton" style="background-color:yellow;" value="rispondi a caso" onClick="rispondiCaso()"/>
</center>
<% } %>

