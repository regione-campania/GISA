<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<!--rita commit 070616-->
<%@page import="org.aspcfs.modules.opu.base.Indirizzo"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>

<%@page import="org.aspcfs.modules.sintesis.actions.StabilimentoSintesisAction"%>
<%@page import="org.aspcfs.modules.sintesis.base.StabilimentoSintesisImport"%>


<jsp:useBean id="Richiesta" class="org.aspcfs.modules.sintesis.base.StabilimentoSintesisImport" scope="request"/>
<jsp:useBean id="TipoImpresaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvinceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="lineaAttivitaMasterList" class="java.lang.String" scope="request"/>

<jsp:useBean id="msgValidazione" class="java.lang.String" scope="request"/>
<jsp:useBean id="msgScartati" class="java.lang.String" scope="request"/>
<jsp:useBean id="codiceUscita" class="java.lang.String" scope="request"/>

<jsp:useBean id="LogImport" class="org.aspcfs.modules.sintesis.base.LogImport" scope="request"/>

<jsp:useBean id="stabEsistente" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>
<jsp:useBean id="relEsistente" class="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva" scope="request"/>
<jsp:useBean id="relNuova" class="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva" scope="request"/>
<jsp:useBean id="StatiStabilimento" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StatiLinea" class="org.aspcfs.utils.web.LookupList" scope="request" />



<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>-->
<script f src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>
<script type="text/javascript" src="javascript/jquery.steps_modify.js"></script>
<script src="javascript/jquery-ui.js"></script>
<SCRIPT src="javascript/sintesis.js"></SCRIPT>
<SCRIPT src="javascript/upload.js"></SCRIPT>
<SCRIPT src="javascript/suapUtil.js"></SCRIPT>
<script src="javascript/jquery.form.js"></script>
<style>
   .td {
   border-right: 1px solid #C1DAD7;
   border-bottom: 1px solid #C1DAD7;
   background: #fff;
   padding: 6px 6px 6px 12px;
   color: #6D929B;
   }
   #progress {
   position: relative;
   width: 400px;
   border: 1px solid #ddd;
   padding: 1px;
   border-radius: 3px;
   }
   #bar {
   background-color: #B4F5B4;
   width: 0%;
   height: 20px;
   border-radius: 3px;
   }
   #percent {
   position: absolute;
   display: inline-block;
   top: 3px;
   left: 48%;
   }
   input[readonly]
   {
   background-color:grey;
   }
   table.one {border-collapse:collapse;}
   td.b {
   border-style:solid;
   border-width:1px;
   border-color:#333333;
   padding:10px;
   }
</style>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
   var cal99 = new CalendarPopup();
   cal99.showYearNavigation();
   cal99.showYearNavigationInput();
   cal99.showNavigationDropdowns();
</SCRIPT> 
<%@ page import="java.util.*"%>
<%@ include file="../initPage.jsp"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script>

function recuperaDati(id){
	if (confirm("ATTENZIONE. Proseguendo, i dati modificabili saranno recuperati da GISA se presenti, e quelli attualmente inseriti saranno sovrascritti.")){
		loadModalWindow();
		window.location.href="StabilimentoSintesisAction.do?command=RecuperaDati&id="+id;
	}
}

function selezionaLinea(id){
	if (confirm('ATTENZIONE. Procedere alla modifica della linea? I dati inseriti finora saranno persi se non salvati. Cliccare OK per procedere, cliccare ANNULLA per salvare prima i dati tramite il bottone SALVA.')){
		loadModalWindow();
		window.location.href="StabilimentoSintesisAction.do?command=PrepareSelezionaLinea&id="+id;
}
}

function rifiutaRichiesta(id){
	if (confirm("Attenzione. Questa pratica sara' rifiutata e posta in uno stato non processabile. Sei sicuro di voler proseguire?")){
		loadModalWindow();
		window.location.href="StabilimentoSintesisAction.do?command=RifiutaRichiesta&id="+id;
	}
}

function prossimaRichiesta(id){
	loadModalWindow();
	window.location.href="StabilimentoSintesisAction.do?command=ProssimaRichiesta&idRichiestaValidata="+id;
}


function vaiAnagrafica(id){
	loadModalWindow();
	window.location.href="StabilimentoSintesisAction.do?command=DettaglioStabilimentoDaRichiesta&idRichiesta="+id;
}

function comparaDati(idRichiesta, approval, piva, attivita, sezione){
	  window.open('StabilimentoSintesisAction.do?command=ComparaDati&idRichiesta='+idRichiesta+'&approval='+approval+'&piva='+piva+'&attivita='+attivita+'&sezione='+sezione,'popupSelect',
      'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

}

function showCoordinate(address,city,prov,cap,campo_lat,campo_long){
	campoLat = campo_lat;
	campoLong = campo_long;
	
	Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
}

function setGeocodedLatLonCoordinate(value){
	campoLat.value = value[1];
	campoLong.value =value[0];
}


</script>

<script>
   function calcolaCap(idComune, topon, indir, campo){	
   	SuapDwr.getCap(idComune, topon, indir, campo,{callback:calcolaCapCallBack,async:false});
   }
   
   
   function calcolaCapCallBack(val){
   	var campo = val[1];
   	var value = val[0];
   	document.getElementById(campo).value = value;
   }
   	
   
   
             
   /*AUTOCOMPLETAMENTO PER GLI INDIRIZZI*/          
   $(function() {
       //$( "#searchcodeIdComune" ).combobox();
       //  $( "#partitaIva" ).combobox();
       $( "#addressLegaleCountry" ).combobox();
       $( "#addressLegaleCity" ).combobox();
       $( "#comuneNascita" ).combobox();
       $( "#addressLegaleLine1" ).combobox();
       $( "#searchcodeIdComune" ).combobox();
       $( "#searchcodeIdprovincia" ).combobox();
       $( "#via" ).combobox();
       $( "#searchcodeIdComuneStab" ).combobox();
       $( "#searchcodeIdprovinciaStab" ).combobox();
       $( "#viaStab" ).combobox();
       
      
   
   });
   
   function nascondiCap(codnaz, idcap, idbottone)
   {
   			if( codnaz != "106" ) 
   			{  
   				document.getElementById(idbottone).type = "hidden";
   				document.getElementById(idcap).type = "hidden";
   				} else {
   					document.getElementById(idbottone).type = "button";
   					document.getElementById(idcap).type = "text";
   					}
   }
   
</script>
<script>

function checkForm(form){
	
	var tipo_impresa = form.tipo_impresa;
	var tipo_societa = form.tipo_societa;
	
	var addressSedeLegaleCountryinput = form.searchcodeIdprovinciainput;
	var addressSedeLegaleCityinput = form.searchcodeIdComuneinput
	var addressSedeLegaleLine1input = form.viainput;
	var capSedeLegale = form.presso; 

	var codFiscaleSoggetto = form.codFiscaleSoggetto;
	var addressLegaleCountryinput = form.addressLegaleCountryinput;
	var addressLegaleCityinput = form.addressLegaleCityinput;
	var addressLegaleLine1input = form.addressLegaleLine1input;
	var civicoResidenza = form.civicoResidenza;
	var capResidenza = form.capResidenza; 
	
	var latitudine = form.latitudineStab;
	var longitudine = form.longitudineStab;

	var msg = "";
	
	
	if (tipo_impresa.value=="")
		msg+="[DATI IMPRESA] Indicare il tipo impresa.\n";
	


	if (addressSedeLegaleCountryinput.value=="")
		msg+="[SEDE LEGALE] Indicare la provincia di residenza.\n";
	if (addressSedeLegaleCityinput.value=="")
		msg+="[SEDE LEGALE] Indicare il comune di residenza.\n";
	if (addressSedeLegaleLine1input.value=="")
		msg+="[SEDE LEGALE] Indicare l'indirizzo di residenza.\n";
	if (capSedeLegale.value=="")
		msg+="[SEDE LEGALE] Indicare il CAP.\n";
	
	
	if (codFiscaleSoggetto.value=="")
		msg+="[RAPP. LEGALE] Indicare il codice fiscale.\n";
	if (addressLegaleCountryinput.value=="")
		msg+="[RAPP. LEGALE] Indicare la provincia di residenza.\n";
	if (addressLegaleCityinput.value=="")
		msg+="[RAPP. LEGALE] Indicare il comune di residenza.\n";
	if (addressLegaleLine1input.value=="")
		msg+="[RAPP. LEGALE] Indicare l'indirizzo di residenza.\n";
	if (civicoResidenza.value=="")
		msg+="[RAPP. LEGALE] Indicare il civico.\n";
	if (capResidenza.value=="")
		msg+="[RAPP. LEGALE] Indicare il CAP.\n";
		alert('aaa');
		
	if(latitudine.value=="" || latitudine.value=="0" ||latitudine.value=="0.0")
		 msg+="[STABILIMENTO] Inserire la latitudine.\n";
	if(isNaN(latitudine.value))
		 msg+="[STABILIMENTO] Inserire la latitudine nel formato numerico.\n";
	if(latitudine.value.charAt(2)!='.')
		 msg+="[STABILIMENTO] Inserire la latitudine nel formato corretto (XX.XXXXXX).\n";
	if (latitudine.value < 39.988475 || latitudine.value > 41.503754)
	 	msg+="[STABILIMENTO] Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754.\n";
	if(longitudine.value=="" || longitudine.value=="0" ||longitudine.value=="0.0")
		 msg+="[STABILIMENTO] Inserire la longitudine.\n";
	if(isNaN(longitudine.value))
		 msg+="[STABILIMENTO] Inserire la longitudine nel formato numerico.\n";
	if(longitudine.value.charAt(2)!='.')
		 msg+="[STABILIMENTO] Inserire la longitudine nel formato corretto (XX.XXXXXX).\n";
	if (longitudine.value <  13.7563172 || longitudine.value > 15.8032837)
	 	msg+="[STABILIMENTO] Valore errato per il campo Longitudine, il valore deve essere compreso tra  13.7563172 e 15.8032837.\n";
	
	if (msg!=""){
		alert(msg);
		return false;
	}
	else
		return true;
}

function salvaDati(form){
   	
// 	   if (!checkForm(form))
// 	   return false;
	   
if (confirm("Salvare i dati attuali? Lo stabilimento non sara' processato.")){
		document.getElementById("salva").value="salva";
		form.submit();
}
else
	return false;
	
}

   function processaDati(form){
   	
// 	   if (!checkForm(form))
// 	   return false;
	   
   if (confirm("ATTENZIONE: confermare i dati attuali e processare lo stabilimento?")){
	   	loadModalWindow();
   		form.submit();
   }
   else
   	return false;
   	
   }
   
   function processaDatiIgnora(form){
	   	
// 	   if (!checkForm(form))
// 	   return false;
	   
   if (confirm('ATTENZIONE: confermare i dati attuali e processare lo stabilimento?')){
	   	form.ignoraFlussoStati.value="ok";
	   	loadModalWindow();
   		form.submit();
   }
   else
   	return false;
   	
   }
   
   function visualizzaOrigine(){
	   var cb = document.getElementById("cbOrigineDati");
	   var div =  document.getElementById("divOrigineDati");
	   if (cb.checked)
		   div.style.display="block";
	   else
		   div.style.display="none";
   }
   
	function checkLat(field){

		if (field.value=='')
			return false;
		
		if (field.value < 39.988475 || field.value > 41.503754){
		 	var err="[STABILIMENTO] Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754.\n";
		 	field.value = '';
		 	alert(err);
		}
		
	}
	
function checkLon(field){
	
	if (field.value=='')
		return false;
	
	if (field.value <  13.7563172 || field.value > 15.8032837){
	 		var err="[STABILIMENTO] Valore errato per il campo Longitudine, il valore deve essere compreso tra  13.7563172 e 15.8032837.\n";
		 	field.value = '';
		 	alert(err);
		}
		
	}
   
</script>


<table class="trails" cellspacing="0">
<tr>
<td>
  <dhv:label name=""><a href="StabilimentoSintesisAction.do" >Gestione Anagrafiche SINTESIS </a>-><a  href="StabilimentoSintesisAction.do?command=ListaRichiesteAggregate">Pratiche</a> -> Completa pratica</dhv:label>
 
</td>
</tr>
</table>


  <dhv:container name="sintesisimport" selected="Pratiche" object=""  param="">
  
  <center><h2>Caricamento in anagrafica record Sintesis</h2></center>
  
  <form id = "addAccount" name="addAccount" action="StabilimentoSintesisAction.do?command=CompletaProcessaRichiesta&auto-populate=true" method="post">
   
   <input type="hidden" name="idRichiesta"	id="idRichiesta" value="<%=Richiesta.getId()%>">
   
  <% if (Richiesta.getStatoImport() == 0) { %>
  
  
  <%
  
  int codice = -1;
  if (codiceUscita!=null && !codiceUscita.equals(""))
	  codice = Integer.parseInt(codiceUscita);
  
  if (codice== StabilimentoSintesisImport.CHECK_WORKFLOW_NON_RISPETTATO){%>
  
<center>
<font color="red" size="5px">ATTENZIONE. La validazione di questa richiesta non e' consentita dal flusso degli stati.</font>
<table class="details" width="100%">
<tr><th>TIPOLOGIA</th> <th>Stato attuale</th> <th>Stato post validazione</th> <th>Data ultimo aggiornamento SINTESIS</th> <th>Data documento SINTESIS in validazione</th></tr>
<tr>
<td><b>Sede Operativa</b> <%=(stabEsistente.getIdStabilimento()>=0) ? stabEsistente.getDenominazione()+" ("+stabEsistente.getApprovalNumber()+")"  : "NON ESISTENTE" %> </td> <td><%= (stabEsistente.getIdStabilimento()>=0) ? StatiStabilimento.getSelectedValue(stabEsistente.getStato())  : "NON ESISTENTE" %></td>
<td><%=StatiStabilimento.getSelectedValue(relNuova.getStabilimento().getStato()) %></td> 
<td><%=(stabEsistente.getIdStabilimento()>=0) ? toDateasString(stabEsistente.getDataUltimoAggiornamentoSintesis())  : "NON ESISTENTE" %></td> 
<td><%=toDateasString(LogImport.getDataDocumentoSintesis()) %></td>
</tr>
<tr>
<td><b>LINEA PRODUTTIVA</b> <%= (relEsistente.getIdRelazione()>=0) ? relEsistente.getPathCompleto() : "NON ESISTENTE"%></td>
<td><%=(relEsistente.getIdRelazione()>=0) ?  StatiLinea.getSelectedValue(relEsistente.getStato())  : "NON ESISTENTE"%></td>
<td><%=StatiLinea.getSelectedValue(relNuova.getStato()) %></td> 
<td> <%=(relEsistente.getIdRelazione()>=0) ?  toDateasString(relEsistente.getDataUltimoAggiornamentoSintesis()) : "NON ESISTENTE" %></td> 
<td><%=toDateasString(LogImport.getDataDocumentoSintesis()) %></td></tr>
</table>

<dhv:permission name="sintesis-add">
<input type="button" value="IGNORA L'ERRORE E PROCESSA IN GISA" onClick="processaDatiIgnora(this.form)"/>
</dhv:permission>
</center>  
  <%} %>
  
  
  
  
<!--   <center> -->
<%--     <input type="button" value="COMPARA DATI" onClick="comparaDati('<%=Richiesta.getId()%>', '<%=Richiesta.getApprovalNumber()%>', '<%=Richiesta.getPartitaIva() %>', '<%=Richiesta.getAttivita() %>', '<%=Richiesta.getDescrizioneSezione() %>')" /> --%>
<%--   <input type="button" value="ATTRIBUZIONE LINEA MASTER LIST 6" onClick="selezionaLinea('<%=Richiesta.getId()%>')" /> --%>
<!--    <input type="button" value="GESTIONE PRODOTTI"/> -->
<!--    <br/><br/> -->
<%--    <input type="button" value="PROCESSA IN GISA"  onClick="processaDati(this.form)" <%=(Richiesta.getOpuIdLineaProduttivaMasterList()<=0) ? "style=\"background:grey\" disabled" : "" %>/> --%>
<%--    <input type="button" value="RIFIUTA" onClick="rifiutaRichiesta('<%=Richiesta.getId()%>')"/> --%>
<!--   </center> -->


  <% } else { %>
  

<table style="border: 1px solid black">
<tr><td> Record processato in data <%=toDateasString(Richiesta.getDataProcess()) %> da <dhv:username id="<%= Richiesta.getIdUtenteProcess() %>"></dhv:username> </td> </tr>
</td></tr>
</table>
<input type="button" value="VAI ALL'ANAGRAFICA" onClick="vaiAnagrafica('<%=Richiesta.getId()%>')"/>
 <%} %>
  <br/><br/>
  
<%if (msgValidazione!=null && !msgValidazione.equals("")){ %>
<b>Informazioni: <br/>
<%= msgValidazione%>
</b>
<%}%>  
<%if (msgScartati!=null && !msgScartati.equals("")){ %>
<br/>
<font color="red"><%= msgScartati%></font>
</b>
<%}%>  
  		
  		
  	<input type="checkbox" id="cbOrigineDati" onClick="visualizzaOrigine()")> Visualizza origine dati
  <div id="divOrigineDati" style="display:none">		
  <table class="details" cellpadding="0" cellspacing="0" width="100%">
 <tr> <th>Stato sede operativa</th><th>Approval Number</th><th>Denominazione sede operativa</th><th>Ragione Sociale Impresa</th><th>Partita Iva</th><th>Codice Fiscale</th></tr>
<tr><td><%=Richiesta.getStatoSedeOperativa() %></td>
  <td><%=Richiesta.getApprovalNumber() %></td>
  <td><%=Richiesta.getDenominazioneSedeOperativa() %></td>
  <td><%=Richiesta.getRagioneSocialeImpresa() %></td>	
  <td><%=Richiesta.getPartitaIva() %></td>	
  <td><%=Richiesta.getCodiceFiscale() %></td></tr>



 <tr> <th>Indirizzo</th><th>Comune</th><th>Sigla provincia</th><th>Provincia</th><th>Regione</th><th>Cod. Ufficio Veterinario</th></tr>
 <tr><td><%=Richiesta.getIndirizzo() %></td>
  <td><%=Richiesta.getComune() %></td>	
  <td><%=Richiesta.getSiglaProvincia() %></td>		
  <td><%=Richiesta.getProvincia() %></td>	
  <td><%=Richiesta.getRegione() %></td>	
  <td><%=Richiesta.getCodUfficioVeterinario() %></td>
 </tr>
  
  
  <tr><th>Ufficio Veterinario</th> <th>Attività</th><th>Stato Attività</th><th>Descrizione Sezione</th><th>Data inizio attività</th><th>Data fine attività</th></tr>
 <tr> <td><%=Richiesta.getUfficioVeterinario() %></td>	
 <td><%=Richiesta.getAttivita() %></td>
  <td><%=Richiesta.getStatoAttivita() %></td>
  <td><%=Richiesta.getDescrizioneSezione() %></td>		
  <td><%=Richiesta.getDataInizioAttivita() %></td>																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																													
  <td><%=Richiesta.getDataFineAttivita() %></td>
 </tr>	
  
  <tr> <th>Tipo autorizzazione</th> <th>Imballaggio</th><th>Paesi abilitati export</th><th>Remark</th><th>Species</th><th>Informazioni Aggiuntive</th></tr>
  <tr> <td><%=Richiesta.getTipoAutorizzazione() %></td>
   <td><%=Richiesta.getImballaggio() %></td>
  <td><%=Richiesta.getPaesiAbilitatiExport() %></td>
  <td><%=Richiesta.getRemark() %></td>
  <td><%=Richiesta.getSpecies() %></td>
  <td><%=Richiesta.getInformazioniAggiuntive() %></td> </tr>
  </table>
  	<br/><br/>	
  </div>		
  		
  
            <%
            int tipoImpresa = -1;
             if (Richiesta.getOpuTipoImpresa()>0)
            	tipoImpresa = Richiesta.getOpuTipoImpresa();
            
            int tipoSocieta = -1;
            if (Richiesta.getOpuTipoImpresa()>0)
            	tipoSocieta = Richiesta.getOpuTipoSocieta();
            
            String domicilioDigitale = "";
             if (Richiesta.getOpuDomicilioDigitale()!=null)
            	domicilioDigitale = Richiesta.getOpuDomicilioDigitale();
             
             String capStabilimento = "";
             if (Richiesta.getCap()!=null)
            	 capStabilimento = Richiesta.getCap();
             
             double latitudine = 0;
              if (Richiesta.getLatitudine()>0)
            	  latitudine = Richiesta.getLatitudine();
              
             double longitudine = 0;
              if (Richiesta.getLongitudine()>0)
            	  longitudine = Richiesta.getLongitudine();
            
            int nazioneSedeLegale = 106;
            if (Richiesta.getOpuNazioneSedeLegale()>0)
            	nazioneSedeLegale = Richiesta.getOpuNazioneSedeLegale();
            
            int provinciaSedeLegale = -1;
            if (Richiesta.getOpuProvinciaSedeLegale()>0)
            	provinciaSedeLegale = Richiesta.getOpuProvinciaSedeLegale(); 
            
            String descrizioneProvinciaSedeLegale = "";
             if (Richiesta.getOpuProvinciaSedeLegale()>0)
            	descrizioneProvinciaSedeLegale = Richiesta.getOpuDescrizioneProvinciaSedeLegale(); 
            
            int comuneSedeLegale = -1;
             if (Richiesta.getOpuComuneSedeLegale()>0)
            	comuneSedeLegale = Richiesta.getOpuComuneSedeLegale();
            
            String descrizioneComuneSedeLegale = "";
             if (Richiesta.getOpuComuneSedeLegale()>0)
            	descrizioneComuneSedeLegale = Richiesta.getOpuDescrizioneComuneSedeLegale();
            
            int toponimoSedeLegale = -1;
            if (Richiesta.getOpuToponimoSedeLegale()>0)
            	toponimoSedeLegale = Richiesta.getOpuToponimoSedeLegale();  
            
            String descrizioneToponimoSedeLegale = "VIA";
            if (Richiesta.getOpuToponimoSedeLegale()>0)
            	descrizioneToponimoSedeLegale = Richiesta.getOpuDescrizioneToponimoSedeLegale();  
            
            String viaSedeLegale = "";
             if (Richiesta.getOpuViaSedeLegale()!=null)
            	viaSedeLegale = Richiesta.getOpuViaSedeLegale();  
           
            String civicoSedeLegale = "";
             if (Richiesta.getOpuCivicoSedeLegale()!=null)
            	civicoSedeLegale = Richiesta.getOpuCivicoSedeLegale();  
            
            String capSedeLegale = "";
             if (Richiesta.getOpuCapSedeLegale()!=null)
            	capSedeLegale = Richiesta.getOpuCapSedeLegale();  
            
            String nomeRappresentante = "";
             if (Richiesta.getOpuNomeRappresentante()!=null)
            	nomeRappresentante = Richiesta.getOpuNomeRappresentante();  
            
            String cognomeRappresentante = "";
            if (Richiesta.getOpuNomeRappresentante()!=null)
            	cognomeRappresentante = Richiesta.getOpuCognomeRappresentante();
            
            String sessoRappresentante = "";
            if (Richiesta.getOpuSessoRappresentante()!=null)
            	sessoRappresentante = Richiesta.getOpuSessoRappresentante();  
            
            String dataNascitaRappresentante = "";
             if (Richiesta.getOpuDataNascitaRappresentante()!=null)
            	dataNascitaRappresentante = Richiesta.getOpuDataNascitaRappresentante().toString();  
            
            int nazioneNascitaRappresentante = 106;
             if (Richiesta.getOpuNazioneNascitaRappresentante()>0)
            	nazioneNascitaRappresentante = Richiesta.getOpuNazioneNascitaRappresentante();  
            
            String comuneNascitaRappresentante = "";
             if (Richiesta.getOpuComuneNascitaRappresentante()!=null)
            	comuneNascitaRappresentante = Richiesta.getOpuComuneNascitaRappresentante();  
            
            String codiceFiscaleRappresentante = "";
             if (Richiesta.getOpuCodiceFiscaleRappresentante()!=null)
            	codiceFiscaleRappresentante = Richiesta.getOpuCodiceFiscaleRappresentante();  
            
            int nazioneResidenzaRappresentante = 106;
            if (Richiesta.getOpuNazioneResidenzaRappresentante()>0)
            	nazioneResidenzaRappresentante = Richiesta.getOpuNazioneResidenzaRappresentante();  
            
            int provinciaResidenzaRappresentante = -1;
            if (Richiesta.getOpuProvinciaResidenzaRappresentante()>0)
            	provinciaResidenzaRappresentante = Richiesta.getOpuProvinciaResidenzaRappresentante();  
            
            String descrizioneProvinciaResidenzaRappresentante = "";
            if (Richiesta.getOpuProvinciaResidenzaRappresentante()>0)
            	descrizioneProvinciaResidenzaRappresentante = Richiesta.getOpuDescrizioneProvinciaResidenzaRappresentante();  
            
            int comuneResidenzaRappresentante = -1;
            if (Richiesta.getOpuComuneResidenzaRappresentante()>0)
            	comuneResidenzaRappresentante = Richiesta.getOpuComuneResidenzaRappresentante();  
        
            String descrizioneComuneResidenzaRappresentante = "";
             if (Richiesta.getOpuProvinciaResidenzaRappresentante()>0)
            	descrizioneComuneResidenzaRappresentante = Richiesta.getOpuDescrizioneComuneResidenzaRappresentante(); 
            
            int toponimoResidenzaRappresentante = -1;
             if (Richiesta.getOpuToponimoResidenzaRappresentante()>0)
            	toponimoResidenzaRappresentante = Richiesta.getOpuToponimoResidenzaRappresentante();  
            
            String descrizioneToponimoResidenzaRappresentante = "VIA";
             if (Richiesta.getOpuToponimoResidenzaRappresentante()>0)
            	descrizioneToponimoResidenzaRappresentante = Richiesta.getOpuDescrizioneToponimoResidenzaRappresentante();  
            
            String viaResidenzaRappresentante = "";
             if (Richiesta.getOpuViaResidenzaRappresentante()!=null)
            	viaResidenzaRappresentante = Richiesta.getOpuViaResidenzaRappresentante();  
           
            String civicoResidenzaRappresentante = "";
             if (Richiesta.getOpuCivicoResidenzaRappresentante()!=null)
            	civicoResidenzaRappresentante = Richiesta.getOpuCivicoResidenzaRappresentante();  
            
            String capResidenzaRappresentante = "";
            if (Richiesta.getOpuCapResidenzaRappresentante()!=null)
            	capResidenzaRappresentante = Richiesta.getOpuCapResidenzaRappresentante(); 
            
            String domicilioDigitaleRappresentante = "";
             if (Richiesta.getOpuDomicilioDigitaleRappresentante()!=null)
            	domicilioDigitaleRappresentante = Richiesta.getOpuDomicilioDigitaleRappresentante();
            %>
            

 
      <div style="display: none;"> 
         &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
      </div>
      
      
  		
  		
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

<tr><th colspan="2">Dati Impresa</th></tr>

<tr>
<td class="formLabel">Ragione Sociale Impresa</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getRagioneSocialeImpresa() %></span></td>
</tr>

<tr>
<td class="formLabel">Partita IVA</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getPartitaIva() %></span></td>
</tr>

<tr>
<td class="formLabel">Codice Fiscale</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getCodiceFiscale() %></span></td>
</tr>


<tr><td class="formLabel">Tipo Impresa</td>
<td><%
            TipoImpresaList.setJsEvent("onchange=onChangeTipoImpresaMinimal();");
            TipoImpresaList.addItem(-1, "Seleziona Voce");
            %>
          
         <%=TipoImpresaList.getHtmlSelect("tipo_impresa", tipoImpresa) %>
<font color="red">(*)</font></td></tr>

<tr><td class="formLabel" nowrap id="trTipoSocieta">TIPO SOCIETA'</td>
  <td><%=TipoSocietaList.getHtmlSelect("tipo_societa", tipoSocieta)%></td>

<tr id="codFiscaleTR">
<td class="formLabel" nowrap>DOMICILIO DIGITALE(PEC)</td>
<td><input type="text" size="70" maxlength="70" id="domicilioDigitale" name="domicilioDigitale" value="<%= domicilioDigitale%>"></td>
</tr>

<tr><th colspan="2">Sede Legale</th></tr>

<tr><td class="formLabel">Nazione</td>
 <td>
                     <% NazioniList.setJsEvent("onchange=\"sbloccoProvincia('nazioneSedeLegale','searchcodeIdprovincia','searchcodeIdComune','via')\""); %> 
                     <% NazioniList.setJsEvent("onChange=\"nascondiCap(this.value,'presso','butCapSL')\""); %>
                     <%=NazioniList.getHtmlSelect("nazioneSedeLegale", nazioneSedeLegale)%>
                     
                     <script>
                     $('#nazioneSedeLegale option[value!="106"]').remove();
                     </script>
                     
                  </td>
               </tr>
               <tr id="searchcodeIdprovinciaTR">
                  <td class="formLabel">PROVINCIA</td>
                  <td>
                     <select name="searchcodeIdprovincia" id="searchcodeIdprovincia" class="required">
                     
                     <% if (provinciaSedeLegale>0) {%>
                     <option value="<%=provinciaSedeLegale%>"><%=descrizioneProvinciaSedeLegale %></option>
                     <%} %>
                     
                        <option value=""></option>
                     </select>
                     <input type="hidden" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" />
                  <font color="red">(*)</font></td>
               </tr>
               <tr>
                  <td class="formLabel">COMUNE</td>
                  <td><select name="searchcodeIdComune" id="searchcodeIdComune" class="required">
                   <% if (comuneSedeLegale>0) {%>
                     <option value="<%=comuneSedeLegale%>"><%=descrizioneComuneSedeLegale %></option>
                     <%} %>
                     </select> 
                     <input type="hidden" name="searchcodeIdComuneTesto" id="searchcodeIdComuneTesto" />
                  <font color="red">(*)</font></td>
               </tr>
               <tr>
                  <td class="formLabel" >INDIRIZZO</td>
                  <td>
                     <table class="noborder">
                        <tr>
                           	<td>
						
							<%=ToponimiList.getHtmlSelect("toponimoSedeLegale", descrizioneToponimoSedeLegale)%>
							</td>
                           <td>
                              <select name="via" id="via" class="required">
                              
                              <%if(viaSedeLegale!=null && !viaSedeLegale.equals(""))
								{
									%>
									<option value="<%=viaSedeLegale%>" selected="selected"><%=viaSedeLegale %></option>
									
									<%
								}%>
								
                              </select>
                           </td>
                           <td>
                              <input type="text" name="civicoSedeLegale"
                                 id="civicoSedeLegale" size="5" placeholder="NUM." maxlength="15"
                                 required="required"  value="<%= civicoSedeLegale%>">
                           </td>
                           <td><input type="text" name="presso" id="presso" size="4"  value="<%= capSedeLegale%>"
                              placeholder="CAP" maxlength="5" required="required"> <input
                              type="button" value="Calcola CAP"  id="butCapSL"
                              onclick="if (document.getElementById('searchcodeIdComune').value!=''){ calcolaCap(document.getElementById('searchcodeIdComune').value, document.getElementById('toponimoSedeLegale').value, document.getElementById('viainput').value, 'presso') };" />
                           </td>
                        </tr>
                     </table>
                  <font color="red">(*)</font> </td>
               </tr>


<tr><th colspan="2">Dati stabilimento</th></tr>

<tr>
<td class="formLabel">Stato sede operativa</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getStatoSedeOperativa() %></span></td>
</tr>

<tr>
<td class="formLabel">Approval number</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getApprovalNumber() %></span></td>
</tr>

<tr>
<td class="formLabel">Denominazione sede operativa</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getDenominazioneSedeOperativa() %></span></td>
</tr>


<tr>
<td class="formLabel">Indirizzo</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getIndirizzo() %></span></td>
</tr>

<tr>
<td class="formLabel">Comune</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getComune() %></span></td>
</tr>

<tr>
<td class="formLabel">Sigla Provincia</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getSiglaProvincia() %></span></td>
</tr>

<tr>
<td class="formLabel">Provincia</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getProvincia() %></span></td>
</tr>

<tr>
<td class="formLabel">Regione</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getRegione() %></span></td>
</tr>

<tr>
<td class="formLabel">CAP</td>
<td>
<input type="text" id="capStab" name="capStab" placeholder="CAP" value="<%=capStabilimento%>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" maxlength="5"  pattern=".{5,}"  required title="Inserire CAP a 5 cifre." onBlur="if(this.value!='' && this.value.length!=5){alert('Inserire CAP a 5 cifre!');this.value='';}"/> 
</td></tr>

<tr>
<td class="formLabel">Coordinate</td>
<td>
LAT <input type="text" id="latitudineStab" name="latitudineStab" placeholder="LAT" value="<%=latitudine%>" onkeyup="this.value=this.value.replace(/[^0-9.]+/,'');" onfocusout="checkLat(this)"/> 
LON <input type="text" id="longitudineStab" name="longitudineStab" placeholder="LON" value="<%=longitudine%>" onkeyup="this.value=this.value.replace(/[^0-9.]+/,'');" onfocusout="checkLon(this)"/> 
<input id="coord1button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate('<%=Richiesta.getIndirizzo().replaceAll("'", "") %>', '<%=Richiesta.getComune().replaceAll("'", "") %>', '<%=Richiesta.getSiglaProvincia() %>', '', document.getElementById('latitudineStab'), document.getElementById('longitudineStab'));" />
</td></tr>

<tr>
<td class="formLabel">Cod. Ufficio Veterinario</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getCodUfficioVeterinario() %></span></td>
</tr>

<tr>
<td class="formLabel">Ufficio Veterinario</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getUfficioVeterinario() %></span></td>
</tr>


<tr><th colspan="2">Rappresentante legale</th></tr>

 <tr>
                  <td class="formLabel">NOME</td>
                  <td><input type="text" size="70" id="nome" name="nome" value="<%=nomeRappresentante%>"> 
                  </td>
               </tr>
               <tr>
                  <td class="formLabel"><label for="cognome-2">COGNOME </label></td>
                  <td><input type="text" size="70" id="cognome" name="cognome" class="required" value="<%=cognomeRappresentante%>"> 
                  </td>
               </tr>
               <tr>
                  <td class="formLabel"><label for="sesso-2">SESSO </label></td>
                  <td>
                     <div class="test">
                        <input type="radio" name="sesso" id="sesso1" value="M"  <%= (sessoRappresentante.equalsIgnoreCase("m")) ? "checked" : ""%>
                           class="required css-radio"> <label
                           for="sesso1" class="css-radiolabel radGroup2">M</label> <input
                           type="radio" name="sesso" id="sesso2" value="F" <%= (sessoRappresentante.equalsIgnoreCase("f")) ? "checked" : ""%>
                           class="required css-radio"> <label
                           for="sesso2" class="css-radiolabel radGroup2">F</label>
                     </div>
                  </td>
               </tr>
               <tr>
                  <td class="formLabel"><label for="dataN-2">DATA NASCITA </label></td>
                  <td><input type="text" size="15" name="dataNascita" 
                     id="dataNascita2" placeholder="dd/MM/YYYY"  value="<%=toDateasStringFromString(dataNascitaRappresentante)%>"> 
                  </td>
               </tr>
               <script>
                  $(function() {
                  	$('#dataNascita2').datepick({dateFormat: 'dd/mm/yyyy', maxDate: 0, showOnFocus: false, showTrigger: '#calImg'});
                  });
               </script>	
               <tr>
                  <td class="formLabel"><label for="nazioneN-2">NAZIONE NASCITA</label></td>
                  <td>
                     <%
                        NazioniList
                        		.setJsEvent("onchange=\"abilitaCodiceFiscale('nazioneNascita');sbloccoProvincia('nazioneNascita',null,'comuneNascita',null)\"");
                        %>
                     <%=NazioniList.getHtmlSelect("nazioneNascita", nazioneNascitaRappresentante)%>
                  </td>
               </tr>
               <tr>
                  <td class="formLabel" nowrap>COMUNE NASCITA</td>
                  <td>
                     <select name="comuneNascita" id="comuneNascita">
                     <%if (!comuneNascitaRappresentante.equals("")){ %>
                        <option value="<%=comuneNascitaRappresentante%>"><%=comuneNascitaRappresentante %></option>
                        <%} %>
                        <option value="">SELEZIONA COMUNE</option>
                     </select>
                     <input type="hidden" name="comuneNascitaTesto"
                        id="comuneNascitaTesto" />
                  </td>
                  <script>
                     $("#comuneNascita").append("<option value='"+ document.getElementById("code_comune_nascita").value+ "' selected='selected'>"+document.getElementById("comuneNascitainput").value+"</option>");
                  </script>
               </tr>
               <tr>
                  <td class="formLabel" nowrap >CODICE FISCALE</td>
                  <td><input type="text" name="codFiscaleSoggetto"  value="<%=codiceFiscaleRappresentante%>"
                     readonly="readonly" id="codFiscaleSoggetto" class="required"
                     /> <font color="red">(*)</font></td>
               </tr>
               <tr>
                  <td class="formLabel">&nbsp;</td>
                  <td><input type="button" id="calcoloCF" class="newButtonClass"
                     value="CALCOLA CODICE FISCALE"  <%-- name="daBloccare"--%>
                     onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.getElementById('comuneNascitainput'),document.forms[0].dataNascita,'codFiscaleSoggetto')"></input>
                  </td>
               </tr>
               <tr>
                  <td class="formLabel"><label for="nazioneN-2">NAZIONE RESIDENZA</label></td>
                  <td>
                     <%
                        NazioniList
                        		.setJsEvent("onchange=\"sbloccoProvincia('nazioneResidenza','addressLegaleCountry','addressLegaleCity','addressLegaleLine1')\"");
                        %>
                        <% NazioniList.setJsEvent("onChange=\"nascondiCap(this.value,'capResidenza','butCapRes')\""); %>
                     <%=NazioniList.getHtmlSelect("nazioneResidenza", nazioneResidenzaRappresentante)%>
                  </td>
               </tr>
               <tr id="addressLegaleCountryTR">
                  <td class="formLabel" nowrap >PROVINCIA RESIDENZA</td>
                  <td><select id="addressLegaleCountry"
                     name="addressLegaleCountry">
                     
                     <%if (provinciaResidenzaRappresentante>0){ %>
                        <option value="<%=provinciaResidenzaRappresentante%>"><%=descrizioneProvinciaResidenzaRappresentante %></option>
                        <%} %>
                     
                     </select> <input type="hidden" id="addressLegaleCountryTesto"
                        name="addressLegaleCountryTesto" />
                   <font color="red">(*)</font></td>
               </tr>
               <tr>
                  <td class="formLabel" nowrap >COMUNE RESIDENZA</td>
                  <td>
                     <select name="addressLegaleCity" id="addressLegaleCity">
                       <%if (comuneResidenzaRappresentante>0){ %>
                        <option value="<%=comuneResidenzaRappresentante%>"><%=descrizioneComuneResidenzaRappresentante %></option>
                        <%} %>
                     </select> <input type="hidden" name="addressLegaleCityTesto"
                        id="addressLegaleCityTesto" />
                  <font color="red">(*)</font> </td>
               </tr>
               <tr>
                  <td class="formLabel" >INDIRIZZO RESIDENZA</td>
                  <td>
                     <table class="noborder">
                        <tr>
                        <td>
							
							<%=ToponimiList.getHtmlSelect("toponimoResidenza", descrizioneToponimoResidenzaRappresentante)%>
							</td>
                           <td><select name="addressLegaleLine1" id="addressLegaleLine1" >
                              
                                <%if (!viaResidenzaRappresentante.equals("")){ %>
                        <option value="<%=viaResidenzaRappresentante%>"><%=viaResidenzaRappresentante %></option>
                        <%} %>
                              
                             
                           </td>
                                 
                           <td><input type="text" name="civicoResidenza"
                              id="civicoResidenza" size="5" placeholder="NUM." maxlength="15"  value="<%=civicoResidenzaRappresentante%>"></td>
                           <td><input type="text" name="capResidenza"
                              id="capResidenza" size="4" placeholder="CAP" maxlength="5"  value="<%=capResidenzaRappresentante%>">
                              <input type="button" value="Calcola CAP" id="butCapRes"
                               onclick="if (document.getElementById('addressLegaleCity').value!='') { calcolaCap(document.getElementById('addressLegaleCity').value, document.getElementById('toponimoResidenza').value, document.getElementById('addressLegaleLine1input').value, 'capResidenza') };" />
                           </td>
                        </tr>
                     </table>
                  <font color="red">(*)</font> </td>
               </tr>
               <tr>
                  <td class="formLabel">DOMICILIO DIGITALE<br>(PEC)
                  </td>
                  <td><input type="text" size="70" name="domicilioDigitalePecSF"  value="<%=domicilioDigitaleRappresentante%>"> </td>
               </tr>
               
      <tr> <th colspan="2">Linea attività</th>        </tr>
               <tr>
<td class="formLabel">Attivita'</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getAttivita() %></span></td>
</tr>


<tr>
<td class="formLabel">Stato attivita'</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getStatoAttivita() %></span></td>
</tr>

<tr>
<td class="formLabel">Descrizione Sezione</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getDescrizioneSezione() %></span></td>
</tr>

<tr>
<td class="formLabel">Data inizio attivita'</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getDataInizioAttivita() %></span></td>
</tr>

<tr>
<td class="formLabel">Data fine attivita'</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getDataFineAttivita() %></span></td>
</tr>

<tr>
<td class="formLabel">Tipo autorizzazione</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getTipoAutorizzazione() %></span></td>
</tr>

<tr>
<td class="formLabel">Imballaggio</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getImballaggio() %></span></td>
</tr>

<tr>
<td class="formLabel">Paesi abilitati export</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getPaesiAbilitatiExport() %></span></td>
</tr>

<tr>
<td class="formLabel">Remark</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getRemark() %></span></td>
</tr>

<tr>
<td class="formLabel">Species</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getSpecies() %></span></td>
</tr>

<tr>
<td class="formLabel">Informazioni aggiuntive</td>
<td><span style="background-color:#c9f0c9;"><%=Richiesta.getInformazioniAggiuntive() %></span></td>
</tr>


<tr><td class="formLabel">Linea Master List</td>
<td><span style="background-color:#c9f0c9;"><%=lineaAttivitaMasterList %></span>   

<dhv:permission name="sintesis-add">
<input type="button" value="Modifica" onClick="selezionaLinea('<%=Richiesta.getId()%>')"  <%=(Richiesta.getStatoImport() != 0) ? "style=\"background:grey\" disabled" : "" %>/>
</dhv:permission>

</td></tr>


</table>
      
<br/><br/>
<% if (Richiesta.getStatoImport() == 0) { %>
  
<!--   <center> -->
<%--     <input type="button" value="COMPARA DATI" onClick="comparaDati('<%=Richiesta.getId()%>', '<%=Richiesta.getApprovalNumber()%>', '<%=Richiesta.getPartitaIva() %>', '<%=Richiesta.getAttivita() %>', '<%=Richiesta.getDescrizioneSezione() %>')" /> --%>
<%--   <input type="button" value="ATTRIBUZIONE LINEA MASTER LIST 6" onClick="selezionaLinea('<%=Richiesta.getId()%>')" /> --%>
<!--    <input type="button" value="GESTIONE PRODOTTI"/> -->
<!--    <br/><br/> -->
<%--    <input type="button" value="PROCESSA IN GISA"  onClick="processaDati(this.form)" <%=(Richiesta.getOpuIdLineaProduttivaMasterList()<=0) ? "style=\"background:grey\" disabled" : "" %>/> --%>
<%--    <input type="button" value="RIFIUTA" onClick="rifiutaRichiesta('<%=Richiesta.getId()%>')"/> --%>
<!--   </center> -->

<center>

<dhv:permission name="sintesis-add">
 <input type="button" value="RIFIUTA" onClick="rifiutaRichiesta('<%=Richiesta.getId()%>')"/>
 </dhv:permission>
 
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 
 <dhv:permission name="sintesis-add">   
 <input type="button" value="SALVA"  onClick="salvaDati(this.form)" />
 </dhv:permission>
 
 <dhv:permission name="sintesis-add">
    <input type="button" value="SALVA E CONTINUA"  onClick="processaDati(this.form)" <%=(Richiesta.getOpuIdLineaProduttivaMasterList()<=0) ? "style=\"background:grey\" disabled" : "" %>/>
    </dhv:permission>
    
    
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   
   <dhv:permission name="sintesis-add">
   <input type="button" value="ANNULLA" onClick="window.location.href='StabilimentoSintesisAction.do?command=DettaglioCompletaRichiesta&id=<%=Richiesta.getId()%>'"/>
   </dhv:permission>
   
  </center>
  <% }%>



<input type="hidden" id="salva" name="salva" value=""/>
<input type="hidden" id="ignoraFlussoStati" name="ignoraFlussoStati" value=""/>


   </form>
</dhv:container>



