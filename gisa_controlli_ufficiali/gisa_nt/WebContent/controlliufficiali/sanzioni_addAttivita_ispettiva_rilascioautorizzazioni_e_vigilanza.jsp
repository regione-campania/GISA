<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="OrgDetails" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="OrgDetailsSanzionata" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="OrgDetailsFuoriRegione" class="java.lang.String" scope="request"/>
<jsp:useBean id="OperatoreMercatoDetailsSanzionato" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="PagatoreDefault" class="org.aspcfs.modules.registrotrasgressori.base.AnagraficaPagatore" scope="request"/>
<jsp:useBean id="ListaNorme" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="SanzioniAutoritaCompetenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniEntiDestinatari" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript" src="javascript/controlli_sottoattivita.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

  <%! public static String fixString(String nome)
  {
	  String toRet = "";
	  if (nome == null)
		  return nome;
	  
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll("\"", "");
	  return toRet;
	  
  }%>
  
  
<%
if (request.getAttribute("inserito")!=null)
{
%>
<font color = "red">Sanzione Inserito in maniera corretta.</font>
<a href = "#" >Torna Indietro</a>

<%	
}
%>

<script>
focus_=true ;



 </script>
 
 <script>
 function gestisciDomandaPagamento(){
	 var domanda = document.getElementById("pagamento_domanda");
	 var pagamento = document.getElementById("pagamento");
	 var asterisco = document.getElementById("pagamento_asterisco");
	 var trImportoMisuraRidotta = document.getElementById("trImportoMisuraRidotta");
	 
	 var riduzioneSeq_si = document.getElementById("riduzioneSeq_si");
	 var riduzioneSeq_no = document.getElementById("riduzioneSeq_no");
	 var seqRiduzioneApplicata = document.getElementById('seqRiduzioneApplicata');
	 var seqRiduzioneApplicata_label = document.getElementById('seqRiduzioneApplicata_label');
	 
	 if (domanda.checked){
		 pagamento.readOnly = false;
		 asterisco.style.display='block';
		 trImportoMisuraRidotta.style.display = "table-row";
	 }
	 else{
		 pagamento.readOnly=true;
		 pagamento.value = '';
		 asterisco.style.display='none';
		 trImportoMisuraRidotta.style.display = "none";
		 riduzioneSeq_si.checked = false;
		 riduzioneSeq_no.checked = true;
		 seqRiduzioneApplicata.value='';
		 seqRiduzioneApplicata.style.display = 'none';
		 seqRiduzioneApplicata_label.style.display = 'none';
		 
	 }
 }
 
 function gestisciApplicabileRiduzione30(){
	 var ridsequestro = document.getElementById('ridsequestro');
	 var riduzionesequestro = document.getElementById('seqRiduzioneApplicata');
	 var riduzionesequestroLabel = document.getElementById('seqRiduzioneApplicata_label');
	 var riduzioneseq = document.getElementById('riduzioneSeq_si'); 
	 var riduzioneseqNo = document.getElementById('riduzioneSeq_no'); 
	 var pagamento = document.getElementById("pagamento");
	 var pagamentoLabel = document.getElementById("pagamento_label");
	 
	 if (riduzioneseq.checked && pagamento.value == ''){
		 alert("Inserire prima il valore dell'importo del pagamento ridotto.");
		 riduzioneseq.checked = false;
		 riduzioneseqNo.checked = true;
		 return false;
	 }
	 
	 
	 if (riduzioneseq.checked){
		 riduzionesequestro.style.display = '';
		 riduzionesequestroLabel.style.display = '';
		 riduzionesequestro.value = Math.trunc(parseInt(pagamento.value) * 70/100); //Valorizzo con la parte intera del 30% dell'importo in misura ridotta
		 pagamento.readOnly = true;
		 pagamentoLabel.innerHTML ="E' stato selezionato un pagamento in misura ultra ridotta. <br/> Questo valore non e' modificabile se non deselezionandolo.";
		 }
		 else{
			 riduzionesequestro.style.display = 'none';
			 riduzionesequestroLabel.style.display = 'none';
			 riduzionesequestro.value='';
			 pagamento.readOnly = "";
			 pagamentoLabel.innerHTML = "";
		 }
	 
	 
 }
 
 function gestisciEffettuatoSequestro(){
	 var sequestro = document.getElementById("effettuatoSeq_si");
	 var numsequestro = document.getElementById('numsequestro');
	 var verbalesequestro = document.getElementById('verbalesequestro');
		 
	 if (sequestro.checked){
		numsequestro.style.display = '';
	 }
	 else{
	 	numsequestro.style.display = 'none';
		verbalesequestro.value='';
	 }
 }
 </script>

<body  onblur="if(focus_==true){window.focus();}" onmouseout="focus_=true;" onmouseover="focus_=false;"  onunload="abilitaAll()" onload = "carica_contenuto_combo_sanzioni();abilitaStatiNc('<%= request.getAttribute("inserito")%>','<%=request.getAttribute("TipoNC") %>','<%= request.getAttribute("idC")%>','<%=request.getAttribute("followup_formali_inseriti") %>','<%=request.getAttribute("followup_significativi_inseriti") %>','<%=request.getAttribute("attivita_gravi_inseriti") %>');disabilitaAll();">

<%--onmouseover="focus_=false;document.getElementById('form_followup').focus();"  --%>

<form name="addticket" id="addticket" action="SanzioniNC.do?command=Insert&auto-populate=true" method="post">
<%-- Trails --%>
<input type = "hidden" id = "Inseriti" name = "inseriti">
<input type = "hidden" id = "followup_formali_inseriti" name = "followup_formali_inseriti">
<input type = "hidden" id = "followup_significativi_inseriti" name = "followup_significativi_inseriti">
<input type = "hidden" id = "attivita_gravi_inseriti" name = "attivita_gravi_inseriti">
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla"  onClick="window.close()">
<br>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
<%-- include basic troubleticket add form --%>

<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
<%@page import="java.text.SimpleDateFormat"%>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.sanzioni.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Sanzioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>

<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<script language="JavaScript">



function aggiungialtro(indice){

	if(indice==2){
	
		document.getElementById("obbligato"+indice).style.display="";
		document.getElementById("bottone"+indice).style.display="";
		document.getElementById("bottone1").style.display="none";
	}
	
	if(indice==3){
		document.getElementById("bottone2").style.display="none";
		document.getElementById("obbligato"+indice).style.display="";
	}
	
}
//AGGIUNTO DA D.ZANFARDINO PER POSSIBILITA' DI INSERIRE FINO A 3 TRASGRESSORI
function aggiungitrasgressore(index){
	if (index==2)
	{
		document.getElementById("trasgressore"+index).style.display="";
		document.getElementById("bottonetrasgressore"+index).style.display="";
		document.getElementById("bottonetrasgressore"+(index-1)).style.display="none";	
	}
	if (index==3)
	{
		document.getElementById("bottonetrasgressore"+(index-1)).style.display="none";
		document.getElementById("trasgressore"+index).style.display="";
	}
}

function gestisciPagatore(form, val, trasgressoreObbligato){

	if (val == 'Nessuno')
		document.getElementById("tablePagatore"+trasgressoreObbligato).style.display="none";
	else
		document.getElementById("tablePagatore"+trasgressoreObbligato).style.display="block";

		
	if (trasgressoreObbligato == 'T') {
		if (val == '<%= fixString(PagatoreDefault.getRagioneSocialeNominativo())%>'){
			form.nomeT.value = '<%= PagatoreDefault.getRagioneSocialeNominativo()!=null ? fixString(PagatoreDefault.getRagioneSocialeNominativo()) : ""%>';
	    	form.pivaT.value = '<%= PagatoreDefault.getPartitaIvaCodiceFiscale()!=null ? PagatoreDefault.getPartitaIvaCodiceFiscale() : ""%>';
	    	form.indirizzoT.value = '<%= PagatoreDefault.getIndirizzo()!=null ? PagatoreDefault.getIndirizzo() : ""%>';
	    	form.civicoT.value = '<%= PagatoreDefault.getCivico()!=null ? PagatoreDefault.getCivico() : ""%>';
	    	form.comuneT.value = '<%= PagatoreDefault.getComune()!=null ? PagatoreDefault.getComune() : ""%>';
	    	form.capT.value = '<%= PagatoreDefault.getCap()!=null ? PagatoreDefault.getCap() : ""%>';
	    	form.provinciaT.value = '<%= PagatoreDefault.getCodProvincia()!=null ? PagatoreDefault.getCodProvincia() : ""%>';
	    	form.nazioneT.value = '<%= PagatoreDefault.getNazione()!=null ? PagatoreDefault.getNazione() : ""%>';
	    	form.mailT.value = '<%= PagatoreDefault.getDomicilioDigitale()!=null ? PagatoreDefault.getDomicilioDigitale() : ""%>';
	    	form.telefonoT.value = '<%= PagatoreDefault.getTelefono()!=null ? PagatoreDefault.getTelefono() : ""%>';
	    	form.nomeT.readOnly = true;
	    	form.pivaT.readOnly = true;
	    	form.ricercaT.style.display = "none";
		} else {
			form.nomeT.value = '';
	    	form.pivaT.value = '';
	    	form.indirizzoT.value = '';
	    	form.civicoT.value = '';
	    	form.comuneT.value = '';
	    	form.capT.value = '';
	    	form.provinciaT.value = '';
	    	form.nazioneT.value = '';
	    	form.mailT.value = '';
	    	form.telefonoT.value = '';
	    	form.nomeT.readOnly = false;
	    	form.pivaT.readOnly = false;
	    	form.ricercaT.style.display = "block";
		}
	} 
	
	if (trasgressoreObbligato == 'O') {
		if (val == '<%= fixString(PagatoreDefault.getRagioneSocialeNominativo())%>'){
			form.nomeO.value = '<%= PagatoreDefault.getRagioneSocialeNominativo()!=null ? fixString(PagatoreDefault.getRagioneSocialeNominativo()) : ""%>';
	    	form.pivaO.value = '<%= PagatoreDefault.getPartitaIvaCodiceFiscale()!=null ? PagatoreDefault.getPartitaIvaCodiceFiscale() : ""%>';
	    	form.indirizzoO.value = '<%= PagatoreDefault.getIndirizzo()!=null ? PagatoreDefault.getIndirizzo() : ""%>';
	    	form.civicoO.value = '<%= PagatoreDefault.getCivico()!=null ? PagatoreDefault.getCivico() : ""%>';
	    	form.comuneO.value = '<%= PagatoreDefault.getComune()!=null ? PagatoreDefault.getComune() : ""%>';
	    	form.capO.value = '<%= PagatoreDefault.getCap()!=null ? PagatoreDefault.getCap() : ""%>';
	    	form.provinciaO.value = '<%= PagatoreDefault.getCodProvincia()!=null ? PagatoreDefault.getCodProvincia() : ""%>';
	    	form.nazioneO.value = '<%= PagatoreDefault.getNazione()!=null ? PagatoreDefault.getNazione() : ""%>';
	    	form.mailO.value = '<%= PagatoreDefault.getDomicilioDigitale()!=null ? PagatoreDefault.getDomicilioDigitale() : ""%>';
	    	form.telefonoO.value = '<%= PagatoreDefault.getTelefono()!=null ? PagatoreDefault.getTelefono() : ""%>';
	    	form.nomeO.readOnly = true;
	    	form.pivaO.readOnly = true;
	    	form.ricercaO.style.display = "none";
		} else {
			form.nomeO.value = '';
	    	form.pivaO.value = '';
	    	form.indirizzoO.value = '';
	    	form.civicoO.value = '';
	    	form.comuneO.value = '';
	    	form.capO.value = '';
	    	form.provinciaO.value = '';
	    	form.nazioneO.value = '';
	    	form.mailO.value = '';
	    	form.telefonoO.value = '';
	    	form.nomeO.readOnly = false;
	    	form.pivaO.readOnly = false;
	    	form.ricercaO.style.display = "block";
		}
	}
	
}

function validateEmail(email) 
{
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}

function checkImporto(form)
{
if (form.cause.value == "") {
      alert("- Controllare che il campo 'importo' sia stato popolato ");
    }
}

  
  function checkForm(form){
    formTest = true;
    message = "";

arr=form.Provvedimenti;

    
  /* if (form.assignedDate.value == "") {
        message += label("check.sanzioni.data_richiesta.selezionato","- Controllare che il campo \"Data Accertamento\" sia stato popolato\r\n");
        formTest = false;
      } */
   /* if(form.tipo_richiesta.value==""){
   	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Processo Verbale\" sia stato popolato \r\n");
        formTest = false;

   	 }*/
   	 
   	if(document.getElementById("divCompetenza").style.display=='block'){
   		if (form.idAutoritaCompetente.value == -1){
   			message += label("check.sanzioni.richiedente.selezionato","- Controllare di aver selezionato un \"Autorita competente\".\r\n");
   			formTest = false;
   		}
   		if (form.descrizioneAutoritaCompetente.style.display=="table-row" && form.descrizioneAutoritaCompetente.value == ""){
   			message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo descrittivo di \"Autorita competente\" sia stato popolato.\r\n");
   			formTest = false;
   		}
   		if (form.idEnteDestinatario.value == -1){
   			message += label("check.sanzioni.richiedente.selezionato","- Controllare di aver selezionato un \"Ente destinatario\".\r\n");
   			formTest = false;
   		}
   		if (form.descrizioneEnteDestinatario.style.display=="table-row" && form.descrizioneEnteDestinatario.value == ""){
   			message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo descrittivo di \"Ente destinatario\" sia stato popolato.\r\n");
   			formTest = false;
   		}
   	} 
   	   	 
   	if(form.trasgressore.value=="Altro" && form.trasgressorealtro.value==''){
   		 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Trasgressore\" sia stato popolato\r\n");
   	    formTest = false;

   		 }
   	   	 
   	if(form.obbligatoinSolido.value=="Altro" && form.obbligatoinSolidoAltro.value==''){
   		 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Obbligato in Solido\" sia stato popolato\r\n");
   	   formTest = false;

   		 }


// if(form.obbligatoinSolido2.value=="Altro" && form.obbligatoinSolidoAltr2o.value==''){
// 	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Obbligato in Solido2\" sia stato popolato\r\n");
//    formTest = false;

// 	 }
	 

// if(form.obbligatoinSolido3.value=="Altro" && form.obbligatoinSolidoAltro3.value==''){
// 	 message += label("check.sanzioni.richiedente.selezionato","- Controllare che il campo \"Obbligato in Solido3\" sia stato popolato\r\n");
//    formTest = false;

// 	 }


var msgAlmenoUno = "- Controllare che almeno uno tra i campi Trasgressore e obbligato in Solido coincida con la ragione sociale del soggetto ispezionato\r\n";
<% if (OrgDetailsSanzionata.getOrgId()>0 || OperatoreMercatoDetailsSanzionato.getIdStabilimento()>0) {%>
msgAlmenoUno = "- Controllare che almeno uno tra i campi Trasgressore e obbligato in Solido coincida con il soggetto (diverso da quello ispezionato) a cui attribuire la non conformita'\r\n";
<%} %>

if( (form.obbligatoinSolido.value=="Altro" || form.obbligatoinSolido.value=="Nessuno") &&  (form.trasgressore.value=='Altro')){
	 message += label("check.sanzioni.richiedente.selezionato",msgAlmenoUno);
    formTest = false;

	 }
	 
if(form.obbligatoinSolido.value== form.trasgressore.value){
	 message += label("check.sanzioni.richiedente.selezionato","Controllare che Trasgressore e Obbligato in solido non coincidano\r\n");
   formTest = false;

	 }
   	 
   	 
 for(k=0 ; k<arr.length;k++){
		if(arr[k].value=="-1" && arr[k].selected==true){
			message += label("check.sanzioni.richiedente.selezionato","- Controllare di aver selezionato una azione \r\n");
		      formTest = false;

			}

     }
 
 if(form.tipo_richiesta.value==""){
	 message += label("check.sanzioni.tipo_richiesta.selezionato","- Controllare che il campo \"Numero processo verbale\" sia stato popolato\r\n");
     formTest = false;

}
 var domanda = document.getElementById("pagamento_domanda");

 if(form.pagamento.value=="" && domanda.checked){
	 message += label("check.sanzioni.richiedente.selezionato","- Controllare di aver inserito un importo per pagamento in misura ridotta\r\n");
     formTest = false;

	 }


if(form.Provvedimenti.value==""){
	 message += label("check.sanzioni.data_richiesta.selezionato","- Controllare che il campo \"Azione non conforme per\" sia stato popolato\r\n");
     formTest = false;

}
if(form.listaNorme.value==-1 || form.listaNorme.value==''){
	 message += label("check.sanzioni.listaNorme.selezionato","- Controllare che il campo \"Norma violata\" sia stato popolato\r\n");
    formTest = false;

}

if(form.effettuatoSeq_si.checked){
	if (form.verbalesequestro.value.trim()==''){
	 message += label("check.sanzioni.importos","- Controllare che il campo \"Numero verbale di sequestro amministrativo\" sia stato popolato\r\n");
    formTest = false;
	}
}

if(form.effettuatoSeq_no.checked && form.riduzioneSeq_si.checked){
	if (form.seqRiduzioneApplicata.value==''){
	 message += label("check.sanzioni.importos","- Controllare che il campo \"Riduzione del 30%\" sia stato popolato\r\n");
    formTest = false;
	}
	else if ( !isNaN(form.seqRiduzioneApplicata.value) && !isNaN(form.pagamento.value) &&  parseInt(form.seqRiduzioneApplicata.value,10) >= parseInt(form.pagamento.value, 10)){
		 message += label("check.sanzioni.importos","- Controllare che il campo \"Riduzione del 30%\" sia coerente col campo \"Pagamento in Misura Ridotta\" \r\n");
	    formTest = false;
		}
}



    if (isNaN(form.pagamento.value) ){
		 message += "- Valore errato per il campo \"Pagamento in Misura Ridotta\". Si prega di inserire l'importo in cifre\r\n";
			 formTest = false;
		}	
    
    if (document.getElementById("tablePagatoreT").style.display!='none'){

    	var tipo = form.tipoPagatoreT.value;
    	var ragione = form.nomeT.value;
    	var piva = form.pivaT.value;
    	var indirizzo = form.indirizzoT.value;
    	var civico = form.civicoT.value;
    	var comune = form.comuneT.value;
    	var cap = form.capT.value;
    	var provincia = form.provinciaT.value;
    	var nazione = form.nazioneT.value;
    	var mail = form.mailT.value;

    	if (tipo=='') {
   		 	 message += "- Inserire tipo pagatore Trasgressore.\r\n";
			 formTest = false;
   		}
    	if (ragione=='') {
    		 message += "- Inserire ragione sociale Trasgressore.\r\n";
			 formTest = false;
    	}
    
    	if ((tipo=='F' && piva.length>0 && piva.length!=16) || (tipo=='G' && piva.length>0 && piva.length!=11)) { 
    		message += "- Inserire tipo pagatore Trasgressore coerente con il valore indicato in Partita IVA/Codice fiscale.\r\n";
			formTest = false;
    	}
    	
    	if (provincia.length>0 && provincia.length!=2) { 
    		message += "- Il campo Cod Provincia Trasgressore deve contenere la sigla della provincia ed essere di 2 caratteri.\r\n";
			formTest = false;
    	}
    	
    	if (nazione.length>0 && nazione.length!=2) { 
    		message += "- Il campo Nazione Trasgressore deve contenere la sigla della nazione ed essere di 2 caratteri.\r\n";
			formTest = false;
    	}
    	
    	}
   
    if (document.getElementById("tablePagatoreO").style.display!='none'){

    	var tipo = form.tipoPagatoreO.value;
    	var ragione = form.nomeO.value;
    	var piva = form.pivaO.value;
    	var indirizzo = form.indirizzoO.value;
    	var civico = form.civicoO.value;
    	var comune = form.comuneO.value;
    	var cap = form.capO.value;
    	var provincia = form.provinciaO.value;
    	var nazione = form.nazioneO.value;
    	var mail = form.mailO.value;

    	if (tipo=='') {
   		 message += "- Inserire tipo pagatore Obbligato in solido.\r\n";
			 formTest = false;
   		}
    	if (ragione=='') {
    		 message += "- Inserire ragione sociale Obbligato in solido.\r\n";
			 formTest = false;
    	}
    	if ((tipo=='F' && piva.length>0 && piva.length!=16) || (tipo=='G' && piva.length>0 && piva.length!=11)) { 
    		message += "- Inserire tipo pagatore Obbligato in solido coerente con il valore indicato in Partita IVA/Codice fiscale.\r\n";
			formTest = false;
    	}
    	if (provincia.length>0 && provincia.length!=2) { 
    		message += "- Il campo Cod Provincia Obbligato in solido deve contenere la sigla della provincia ed essere di 2 caratteri.\r\n";
			formTest = false;
    	}
    	
    	if (nazione.length>0 && nazione.length!=2) { 
    		message += "- Il campo Nazione Obbligato in solido deve contenere la sigla della nazione ed essere di 2 caratteri.\r\n";
			formTest = false;
    	}
  
    	}
    
    
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
    	loadModalWindow();
      return true;
    }
  }
  //used when a new contact is added
  
  
  function selectCarattere(str, n, m, x){
  
 		elm1 = document.getElementById("dat"+n);
 		elm2 = document.getElementById("dat"+m);
 		
 		
 		if(str == "Provvedimenti"){
 			car = document.addticket.Provvedimenti.value;
 		}
 		if(str == "SanzioniAmministrative"){
 			car = document.addticket.SanzioniAmministrative.value;
 		}
 		if(str == "SanzioniPenali"){
 			car = document.addticket.SanzioniPenali.value;
 		}
 		
 		if(car == 9 || (car == 6 && str == "SanzioniPenali")){
 			elm1.style.visibility = "visible";
 			elm2.style.visibility = "visible";
 			
 		}
 		else {
 			elm1.style.visibility = "hidden";
 			elm2.style.visibility = "hidden";
 			
 			if(x == 1){
 			document.forms['addticket'].descrizione1.value="";
 			}
 			if(x == 2){
 			document.forms['addticket'].descrizione2.value="";
 			}
 			if(x == 3){
 			document.forms['addticket'].descrizione3.value="";
 			}
 		}
 	  }
  

  function verificaEsistenzaDiffidaCallback(val)
  {
  	  if (val==true)
  		  {
  		  formTest = false;
  		  message += label("check.sanzioni.listaNorme.selezionato","- L'operatore risulta già diffidato per questa norma. \r\n");
  		  }
  	  
  }

  var item ;
  function checkDiffida()
  	{
  	  form= document.forms[0];
  	  arr=form.listaNorme;
  	  for(k=0 ; k<arr.length;k++){
  			if(arr[k].value=="-1" && arr[k].selected==true){
  				 	
  			      	formTest = false;

  				}
  			else
  				{
  				if( arr[k].selected==true){
  					
  				item = arr[k];
  				
  				if (form.altId!=null && form.altId.value>0)
  					PopolaCombo.verificaNormaInDiffidaAlt(arr[k].value,form.altId.value,-1,{callback:verificaEsistenzaDiffidaCallback,async:false});
  				else
  					PopolaCombo.verificaNormaInDiffidaOSA(arr[k].value,form.orgId.value,form.idStabilimento.value,form.idApiario.value,-1,{callback:verificaEsistenzaDiffidaCallback,async:false});
  				
  				
  				}
  				}
  				}
  			
  		
  		
  	}
  	

  	function verificaEsistenzaDiffidaCallback(val)
  	{
  	  if (val==true)
  		  {

  		  //message += label("check.sanzioni.listaNorme.selezionato","- Norma presente in diffida per questo controllo \r\n");
  		  if(confirm("Attenzione! Lo stabilimento risulta già diffidato per una o più delle norme selezionate; per tali norme la diffida verrà eliminata.")){
  			formTest = true;	  
  		  }
  		  
  		  }
  	  
  	}  
  	
  	function checkCompetenzaUod()
  	{
  	  form= document.forms[0];
  	  arr=form.listaNorme;
  	  for(k=0 ; k<arr.length;k++){
  			if(arr[k].value=="-1" && arr[k].selected==true){
  				 	
  			      	formTest = false;

  				}
  			else
  				{
  				if( arr[k].selected==true){
  					
  				item = arr[k];
  				PopolaCombo.verificaCompetenzaUod(arr[k].value,{callback:verificaCompetenzaUodCallback,async:false});
  				
  				
  				}
  				}
  				}
  			
  		
  		
  	}
  	

  	function verificaCompetenzaUodCallback(val)
  	{
  	  if (val==true)
  		  { document.getElementById("divCompetenza").style.display="none";
  			document.getElementById("idAutoritaCompetente").value = -1;
  			document.getElementById("idEnteDestinatario").value = -1;
  			document.getElementById("descrizioneAutoritaCompetente").value = "";
  			document.getElementById("descrizioneEnteDestinatario").value = "";
  			  }
  	  else
		  { document.getElementById("divCompetenza").style.display="block";}

  	  
  	}  
  
</script>

<script>
function gestisciAutoritaCompetente(sel){
	var text = sel.options[sel.selectedIndex].text;
	if (text=="ALTRO"){
		document.getElementById("descrizioneAutoritaCompetente").style.display="table-row";	
	}
	else {
		document.getElementById("descrizioneAutoritaCompetente").style.display="none";	
		document.getElementById("descrizioneAutoritaCompetente").value="";
	}
}
function gestisciEnteDestinatario(sel){
	var text = sel.options[sel.selectedIndex].text;
	if (sel.value > 0 && text!= "ASSESSORATO AGRICOLTURA DELLA REGIONE CAMPANIA"){
		document.getElementById("descrizioneEnteDestinatario").style.display="table-row";	
	}
	else {
		document.getElementById("descrizioneEnteDestinatario").style.display="none";	
		document.getElementById("descrizioneEnteDestinatario").value="";
	}
}
</script>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Sanzione Amministrativa</dhv:label></strong>
    </th>
	</tr>
	
	 
  <input type="hidden" name="idC" value="<%= request.getAttribute("idC")%>" >
  	<input type = "hidden" name = "tipoNc" value = "<%=request.getAttribute("TipoNC") %>">
  
  <dhv:include name="stabilimenti-sites" none="true">
 <%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
    <tr>
      <td nowrap class="formLabel">
       ASL di competenza
      </td>
      <td>
       <%if (OrgDetails.getSiteId()>0 && OrgDetails.getTipologia()!=255){ %>
		<%=SiteIdList.getSelectedValue((Integer)request.getAttribute("id_asl"))%> 
		<input type="hidden" name="siteId" id = "siteId" value="<%=(Integer)request.getAttribute("id_asl")%>">
		<%}
		else
		{
			UserBean utente = (UserBean)session.getAttribute("User");
			if(utente.getSiteId()>0)
			{
			%>
			<%=SiteIdList.getSelectedValue(utente.getSiteId()) %>
			<input type="hidden" name="siteId" id = "siteId" value="<%=utente.getSiteId()%>">
			<%
			}
			else
			{%>
				<%=SiteIdList.getSelectedValue((Integer)request.getAttribute("id_asl")) %>
			<input type="hidden" name="siteId" id = "siteId" value="<%=(Integer)request.getAttribute("id_asl")%>">
			<%
			}
		}
		%>
      
      </td>
    </tr>
<%--</dhv:evaluate>  --%>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
	
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
  <tr>
    <td class="formLabel">
      Operatore
    </td>
   
     
      <td>
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="idStabilimento" value="<%=OrgDetails.getIdStabilimento()%>">
                <input type="hidden" name="stabId" value="<%=OrgDetails.getIdStabilimento()%>">
                <input type="hidden" id="altId" name="altId" value="<%=OrgDetails.getAltId()%>"/>
        
        <input type="hidden" name="idApiario" value="<%=OrgDetails.getIdApiario()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
      </td>
    
  </tr>
 
  <% }else{ %>
      <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="text" name="idStabilimento" value="<%=OrgDetails.getIdStabilimento()%>">
                <input type="hidden" id="altId" name="altId" value="<%=OrgDetails.getAltId()%>"/>
        
  <% } %>

  
    <% String dataC = request.getAttribute("dataC").toString();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
    	%>
   
   <tr class="containerBody" style="display: none">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Accertamento</dhv:label>
    </td>
    <td>
	    <!-- 
	    <input readonly type="text" id="assignedDate" name="assignedDate" size="10" /> 
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle">  
		-->
    <zeroio:dateSelect form="addticket" field="assignedDate" timestamp="<%= dataC %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
		</a>
      <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>
  <tr>
  <td nowrap class="formLabel">
      <dhv:label name=""> Numero Processo Verbale</dhv:label>
    </td>
  <td >
     
     
  
	  <input type="text" name="tipo_richiesta" id="tipo_richiesta" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256" onFocusOut="this.value=this.value.trim()"/>
<font color="red">*</font>
      <input type="hidden" name="check"/>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Applicabile il pagamento ridotto?
    </td>
    <td>
      <input type="checkbox" name="pagamento_domanda" id="pagamento_domanda" checked onClick="gestisciDomandaPagamento()"/> 
   </td>
  </tr>

	
  <tr class="containerBody" id="trImportoMisuraRidotta">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.importos">Importo Sanzione in Misura Ridotta (Pagamento ridotto)</dhv:label>
    </td>
    <td>
      <input type="text" name="pagamento" id="pagamento" value="" size="20" maxlength="256" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/> 
        <label id="pagamento_asterisco"><font color="red">*</font></label>
        <label id="pagamento_label"></label>
    </td>
  </tr>
  
   <tr id = "ridsequestro" class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.importos">Applicabile riduzione del 30%? (Pagamento Ultra Ridotto)</dhv:label>
    </td>
    <td>
		SI <input  type = "radio" value="si" name="riduzioneSeq" id="riduzioneSeq_si" onclick="gestisciApplicabileRiduzione30()">		
    	NO <input  type = "radio" checked="checked" value="no" name="riduzioneSeq" id="riduzioneSeq_no" onclick="gestisciApplicabileRiduzione30()"> <br/>
    	<input type = "text" readonly name = "seqRiduzioneApplicata"  id = "seqRiduzioneApplicata" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" style="display:none" placeholder="Importo con la riduzione del 30%">
    	<label id="seqRiduzioneApplicata_label" style="display:none">Applicata automaticamente riduzione del 30% <br/>al valore del Pagamento Ridotto.</label>
     </td>
  </tr>
  
   <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.importos">Effettuato Anche Sequestro Amministrativo ?</dhv:label>
    </td>
    <td>
    	SI <input  type = "radio" value="si" name="effettuatoSeq" id="effettuatoSeq_si" onclick="gestisciEffettuatoSequestro()">
    	NO <input  type = "radio" checked="checked" value="no" name="effettuatoSeq"  id="effettuatoSeq_no" onclick="gestisciEffettuatoSequestro()"> 
    
    </td>
  </tr>
  
   <tr id = "numsequestro" class="containerBody" style="display:none">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.importos">Numero Verbale di Sequestro Amministrativo</dhv:label>
    </td>
    <td>
		<input type = "text" name = "verbalesequestro"  id = "verbalesequestro"   >
    </td>
  </tr>
      
   <tr>
  <td nowrap class="formLabel">
      <dhv:label name=""> Trasgressore</dhv:label>
    </td>
  <td>
  
  <select name="trasgressore" id="trasgressore" onchange="gestisciPagatore(this.form, this.value, 'T'); if(this.value=='Altro'){document.getElementById('trasgressorealtro').style.display='none';}else{document.getElementById('trasgressorealtro').style.display='none';}">
  
  <% if (OrgDetailsSanzionata.getOrgId()>0) {%>

  <option selected value="<%=fixString(OrgDetailsSanzionata.getName()) %>"><%=fixString(OrgDetailsSanzionata.getName()) %></option>
  <%} else if (OperatoreMercatoDetailsSanzionato.getIdStabilimento()>0) { %>
  
  <option selected value="<%=fixString(OperatoreMercatoDetailsSanzionato.getOperatore().getRagioneSociale()) %>"><%=fixString(OperatoreMercatoDetailsSanzionato.getOperatore().getRagioneSociale()) %></option>
  <%} else if (OrgDetailsFuoriRegione.equals("true")) { %>
  
  <option selected value="<%=fixString("OPERATORE FUORI REGIONE") %>"><%=fixString("OPERATORE FUORI REGIONE") %></option>
  <%} else { %>
  
  <option selected value="<%=fixString(OrgDetails.getName()) %>"><%=fixString(OrgDetails.getName()) %></option>
  <%} %>
  <option value="Altro">Altro</option>
  </select>
  <font color="red">*</font>
      
   <input style="display:none" type="text" readonly id="trasgressorealtro" name="trasgressorealtro" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/>

  <table class="details" id="tablePagatoreT" name="tablePagatoreT" style="display:none">
<tr><td colspan="2">
<br/><input type="button" id="ricercaT" name="ricercaT" onClick="window.open('GestionePagoPa.do?command=SearchFormPagatore&trasgressoreObbligato=T', 'cercaPagatore',  'height=400px,width=1280px,toolbar=no, scrollbars=yes,resizable=yes ,modal=yes')" value="RICERCA"/></td></tr>
  <tr><td align="right"><b>Tipo Pagatore</b></td><td><select id="tipoPagatoreT" name="tipoPagatoreT"><option value="">-- SELEZIONARE --</option><option value="G">Società di persone/Associazioni</option><option value="F">Persona Fisica</option></select>    <font color="red">*</font> </td></tr>
  <tr><td align="right"><b>Partita IVA / Codice fiscale</b></td><td><input type="text" name="pivaT" id="pivaT" size="16" maxlength="16"/> </td></tr>
  <tr><td align="right"><b>Ragione sociale / Nominativo</b></td><td><input type="text" name="nomeT" id="nomeT" onChange="document.getElementById('trasgressorealtro').value = this.value"/> </td></tr>
  <tr><td align="right"><b>Indirizzo</b></td><td><input type="text" name="indirizzoT" id="indirizzoT"/> </td></tr>
  <tr><td align="right"><b>Civico</b></td><td><input type="text" name="civicoT" id="civicoT" size="5" maxlength="5"/> </td></tr>
  <tr><td align="right"><b>CAP</b></td><td><input type="text" name="capT" id="capT" size="5" maxlength="5"/> </td></tr>
  <tr><td align="right"><b>Comune</b></td><td><input type="text" name="comuneT" id="comuneT"/> </td></tr>
  <tr><td align="right"><b>Cod Provincia</b></td><td><input type="text" name="provinciaT" id="provinciaT" size="2" maxlength="2"/> </td></tr>
  <tr><td align="right"><b>Nazione</b></td><td><input type="text" name="nazioneT" id="nazioneT" size="2" maxlength="2"/></td> </tr>
  <tr><td align="right"><b>Email</b></td><td><input type="text" name="mailT" id="mailT"/> </td></tr>
  <tr><td align="right"><b>Telefono</b></td><td><input type="text" name="telefonoT" id="telefonoT"/> </td></tr>
  </table>
 
     </td>
  </tr>
  
  <tr>
  <td nowrap class="formLabel">
      <dhv:label name="">Obbligato in Solido</dhv:label>
    </td>
    
    
  <td >
  <table class="noborder"><tr>
  
  <td>
  
  <select name = "obbligatoinSolido"  onchange="gestisciPagatore(this.form, this.value, 'O'); if(this.value=='Altro'){document.getElementById('obbligatoinSolidoAltro').style.display='none';}else{document.getElementById('obbligatoinSolidoAltro').style.display='none';}"> 
  
  <option value="Nessuno">Nessuno</option>
  
  <% if (OrgDetailsSanzionata.getOrgId()>0) {%>
  <option value="<%=fixString(OrgDetailsSanzionata.getName()) %>"><%=fixString(OrgDetailsSanzionata.getName()) %></option>
  <%} else if (OperatoreMercatoDetailsSanzionato.getIdStabilimento()>0) { %>
  
  <option value="<%=fixString(OperatoreMercatoDetailsSanzionato.getOperatore().getRagioneSociale()) %>"><%=fixString(OperatoreMercatoDetailsSanzionato.getOperatore().getRagioneSociale()) %></option>
  <%} else if (OrgDetailsFuoriRegione.equals("true")) { %>
  
  <option value="<%=fixString("OPERATORE FUORI REGIONE") %>"><%=fixString("OPERATORE FUORI REGIONE") %></option>
  <%} else { %>
  <option value="<%=fixString(OrgDetails.getName()) %>"><%=fixString(OrgDetails.getName()) %></option>
  <%} %>
  
   <option value="Altro">Altro</option>
  </select>
  
  <input style="display: none" type="text" readonly name="obbligatoinSolidoAltro"  id="obbligatoinSolidoAltro" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/>
  
  <table class="details" id="tablePagatoreO" name="tablePagatoreO" style="display:none">
  <tr><td colspan="2">
  <br/><input type="button" id="ricercaO" name="ricercaO" onClick="window.open('GestionePagoPa.do?command=SearchFormPagatore&trasgressoreObbligato=O', 'cercaPagatore',  'height=400px,width=1280px,toolbar=no, scrollbars=yes,resizable=yes ,modal=yes')" value="RICERCA"/></td></tr>
  <tr><td align="right"><b>Tipo Pagatore</b></td><td><select id="tipoPagatoreO" name="tipoPagatoreO"><option value="">-- SELEZIONARE --</option><option value="G">Persona Giuridica</option><option value="F">Persona Fisica</option></select>   <font color="red">*</font> </td></tr>
  <tr><td align="right"><b>Partita IVA / Codice fiscale</b></td><td><input type="text" name="pivaO" id="pivaO" size="16" maxlength="16"/> </td></tr>
  <tr><td align="right"><b>Ragione sociale / Nominativo</b></td><td><input type="text" name="nomeO" id="nomeO" onChange="document.getElementById('obbligatoinSolidoAltro').value = this.value"/> </td></tr>
  <tr><td align="right"><b>Indirizzo</b></td><td><input type="text" name="indirizzoO" id="indirizzoO"/> </td></tr>
  <tr><td align="right"><b>Civico</b></td><td><input type="text" name="civicoO" id="civicoO" size="5" maxlength="5"/> </td></tr>
  <tr><td align="right"><b>CAP</b></td><td><input type="text" name="capO" id="capO" size="5" maxlength="5"/> </td></tr>
  <tr><td align="right"><b>Comune</b></td><td><input type="text" name="comuneO" id="comuneO"/> </td></tr>
  <tr><td align="right"><b>Cod Provincia</b></td><td><input type="text" name="provinciaO" id="provinciaO" size="2" maxlength="2"/> </td></tr>
  <tr><td align="right"><b>Nazione</b></td><td><input type="text" name="nazioneO" id="nazioneO" size="2" maxlength="2"/></td> </tr>
  <tr><td align="right"><b>Email</b></td><td><input type="text" name="mailO" id="mailO"/> </td></tr>
  <tr><td align="right"><b>Telefono</b></td><td><input type="text" name="telefonoO" id="telefonoO"/> </td></tr>
  </table>
 
  </td>
  
<!--    <td style="display:block" id="bottone1"> &nbsp; <input type="button" value="Aggiungi Altro" onclick="javascript:aggiungialtro(2);"></td> -->
  
  </tr></table>
 
    </td>
    
    
  </tr>
  
<!--   <tr  style="display:none" id="obbligato2"> -->
<!--   <td nowrap class="formLabel"> -->
<%--       <dhv:label name="">Obbligato in Solido 2</dhv:label> --%>
<!--     </td> -->
<!--   <td > -->
<!--   <table class="noborder"> -->
<!--   <tr> -->
<!--   <td> -->
  
<!--    <select name = "obbligatoinSolido2"  onchange="if(this.value=='Altro'){document.getElementById('obbligatoinSolidoAltro2').style.display=''; }else{document.getElementById('obbligatoinSolidoAltro2').style.display='none';}"> -->
  
<!--   <option value="Nessuno">Nessuno</option> -->
<%--   <option value="<%=OrgDetails.getName() %>"><%=OrgDetails.getName() %></option> --%>
<!--    <option value="Altro">Altro</option> -->
<!--   </select> -->
  
<%--   <input type="text" style="display: none" name="obbligatoinSolidoAltro2"  id="obbligatoinSolidoAltro2" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/> --%>
  
  
<!--   </td> -->
<!--   <td id = "bottone2" style="display:none" > &nbsp; <input type="button" value="Aggiungi Altro" onclick="javascript:aggiungialtro(3);"></td> -->
  
<!--   </tr> -->
  
  
<!--   </table> -->
      
<!--     </td> -->
     
<!--   </tr> -->
  
<!--   <tr style="display: none" id = "obbligato3"> -->
<!--   <td nowrap class="formLabel" > -->
<%--       <dhv:label name="">Obbligato in Solido 3</dhv:label> --%>
<!--     </td> -->
<!--   <td > -->
<!--   <table class="noborder"> -->
  
<!--   <tr> -->
<!--   <td> -->

<!-- <select name = "obbligatoinSolido3"  onchange="if(this.value=='Altro'){document.getElementById('obbligatoinSolidoAltro3').style.display=''; }else{document.getElementById('obbligatoinSolidoAltro3').style.display='none';}"> -->
  
<!--   <option value="Nessuno">Nessuno</option> -->
<%--   <option value="<%=OrgDetails.getName() %>"><%=OrgDetails.getName() %></option> --%>
<!--    <option value="Altro">Altro</option> -->
<!--   </select> -->
  
<%--   <input type="text" style="display: none" name="obbligatoinSolidoAltro3"  id="obbligatoinSolidoAltro3" value="<%= toHtmlValue(TicketDetails.getTipo_richiesta()) %>" size="20" maxlength="256"/> --%>
  
<!--   </td> -->
<!-- <td>&nbsp;</td>   -->
<!--   </tr> -->
<!--   </table> -->
      
<!--     </td> -->
<!--   </tr> -->
  
  
  <dhv:include name="organization.source" none="true">
   <tr>
      <td name="provvedimento1" id="provvedimento1" nowrap class="formLabel">
        <dhv:label name="">Azione non Conforme per</dhv:label>
      </td>
    <td>
   <table class="noborder">
      <tr>
      <td >
      <select size="5" name = "Provvedimenti" id = "Provvedimenti" multiple="multiple" onchange="selectCarattere('Provvedimenti', '1', 2, '1')">
      
      </select><font color="red">*</font>
         
    		</td>
          	<td style="visibility: hidden;" id="dat1">
        		Descrizione
        	</td>
        	<td style="visibility: hidden;" id="dat2">
           		<input type="text" name="descrizione1" size="50">
          	</td>
       </tr>
       </table>
    </td>
  </tr>
</dhv:include>
<%

if (OrgDetailsSanzionata.getOrgId()<=0){
	ListaNorme.setJsEvent("onchange='checkDiffida(); checkCompetenzaUod()'");
} else {
	ListaNorme.setJsEvent("onchange='checkCompetenzaUod()'");
}
%>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
     Norma Violata
    </td>
    <td>
       <%
       ListaNorme.setMultiple(false);
       ListaNorme.setSelectSize(9);
       %>
	   <%=ListaNorme.getHtmlSelect("listaNorme",-1) %>
	   <br/>
<font color="red">*</font>
    
    <!-- <input type="text" name="normaviolata"  size="20" maxlength="256" /> --> 
    </td>
  </tr>
  
 <tr class="containerBody">
    <td valign="top" class="formLabel">
     Competenza
    </td>
    <td>
      <div id="divCompetenza" style="display:none">
      <%
      SanzioniAutoritaCompetenti.setJsEvent("onChange='gestisciAutoritaCompetente(this)'");
      SanzioniEntiDestinatari.setJsEvent("onChange='gestisciEnteDestinatario(this)'");
      %>
      Autorità competente <%=SanzioniAutoritaCompetenti.getHtmlSelect("idAutoritaCompetente", -1) %> <input type="text" id="descrizioneAutoritaCompetente" name="descrizioneAutoritaCompetente" style="display:none" onFocusOut="this.value=this.value.trim()"/> <font color="red">*</font><br/>
      Ente destinatario dei proventi  <%=SanzioniEntiDestinatari.getHtmlSelect("idEnteDestinatario", -1) %> <input type="text" id="descrizioneEnteDestinatario" name="descrizioneEnteDestinatario" style="display:none" onFocusOut="this.value=this.value.trim()"/> <font color="red">*</font>
      </div>
    </td>
  </tr>


<tr> 
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
    </td>
    <td></br>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="8"><%= toString(TicketDetails.getProblem()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>
 <input type = "hidden" id = "followup_gravi_inseriti" name = "followup_gravi_inseriti">
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<input type="hidden" id="tipoNC" name="tipoNC" value="<%= (OrgDetailsSanzionata.getOrgId()>0) ? "10" : "8" %>"/>
<input type="hidden" id="stabId" name="stabId" value="<%=request.getParameter("stabId")%>"/>
<%= addHiddenParams(request, "popup|popupType|actionId") %>


</table>
<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla"  onClick="window.close()">

<script>
gestisciPagatore(document.getElementById("addticket"), document.getElementById("trasgressore").value, 'T');
</script>

</form>
