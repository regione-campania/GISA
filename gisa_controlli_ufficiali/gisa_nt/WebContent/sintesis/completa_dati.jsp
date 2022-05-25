<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<!--rita commit 070616-->
<%@page import="org.aspcfs.modules.opu.base.Indirizzo"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>

<jsp:useBean id="Richiesta" class="org.aspcfs.modules.sintesis.base.StabilimentoSintesisImport" scope="request"/>
<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>
<jsp:useBean id="TipoImpresaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request" />
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

function annulla(form){
	if (confirm('ATTENZIONE! Annullare le modifiche?')){
		loadModalWindow();
		 window.history.back();
		 }
	
}

   function checkForm(form){
   	
   	var tipo_impresa = form.tipo_impresa;
   	var tipo_societa = form.tipo_societa;
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
   		msg+="Indicare il tipo impresa.\n";
   	if (tipo_societa.style.display!="none" && tipo_societa.value=="")
   		msg+="Indicare il tipo societa.\n";
   	if (codFiscaleSoggetto.value=="")
   		msg+="Indicare il codice fiscale.\n";
   	if (addressLegaleCountryinput.value=="")
   		msg+="Indicare la provincia di residenza.\n";
   	if (addressLegaleCityinput.value=="")
   		msg+="Indicare il comune di residenza.\n";
   	if (addressLegaleLine1input.value=="")
   		msg+="Indicare l'indirizzo di residenza.\n";
   	if (civicoResidenza.value=="")
   		msg+="Indicare il civico.\n";
   	if (capResidenza.value=="")
   		msg+="Indicare il CAP.\n";
   	
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
   	
//    	if (msg!=""){
//    		alert(msg);
//    		return false;
//    	}
//    	else
//    		form.submit();
   	loadModalWindow();
   	form.submit();
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
<dhv:container name="sintesisimport" selected="Stabilimenti" object=""  param="">
<% if (Richiesta.getId()>0) {%>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

<tr><th colspan="2">Riepilogo richiesta</th></tr>

<tr>
<td class="formLabel">Stato sede operativa</td>
<td><%=Richiesta.getStatoSedeOperativa() %></td>
</tr>

<tr>
<td class="formLabel">Approval number</td>
<td><%=Richiesta.getApprovalNumber() %></td>
</tr>

<tr>
<td class="formLabel">Denominazione sede operativa</td>
<td><%=Richiesta.getDenominazioneSedeOperativa() %></td>
</tr>

<tr>
<td class="formLabel">Ragione Sociale Impresa</td>
<td><%=Richiesta.getRagioneSocialeImpresa() %></td>
</tr>

<tr>
<td class="formLabel">Partita IVA</td>
<td><%=Richiesta.getPartitaIva() %></td>
</tr>
	</table>
	
<%} else if (Stabilimento.getIdStabilimento()>0) { %>
   <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
         <th colspan="2">Riepilogo stabilimento</th>
      </tr>
      <tr>
         <td class="formLabel">Approval number</td>
         <td><%=Stabilimento.getApprovalNumber() %></td>
      </tr>
      <tr>
         <td class="formLabel">Denominazione sede operativa</td>
         <td><%=Stabilimento.getDenominazione() %></td>
      </tr>
      <tr>
         <td class="formLabel">Ragione Sociale Impresa</td>
         <td><%=Stabilimento.getOperatore().getRagioneSociale() %></td>
      </tr>
      <tr>
         <td class="formLabel">Partita IVA</td>
         <td><%=Stabilimento.getOperatore().getPartitaIva() %></td>
      </tr>
   </table>
   
   <%} %>
   
   <br/><br/>
   
   <% String metodo = "";
   if (Richiesta.getId()>0)
	   metodo="CompletaDati";
   else if (Stabilimento.getIdStabilimento()>0)
	   metodo = "ModificaDati";
  %>
  
  
    
            <%
            int tipoImpresa = -1;
            if (Stabilimento.getIdStabilimento()>0)
            	tipoImpresa =  Stabilimento.getOperatore().getTipoImpresa();
            else if (Richiesta.getOpuTipoImpresa()>0)
            	tipoImpresa = Richiesta.getOpuTipoImpresa();
            
            int tipoSocieta = -1;
            if (Stabilimento.getIdStabilimento()>0)
            	tipoSocieta =  Stabilimento.getOperatore().getTipoSocieta();
            else if (Richiesta.getOpuTipoImpresa()>0)
            	tipoSocieta = Richiesta.getOpuTipoSocieta();
            
            String domicilioDigitale = "";
            if (Stabilimento.getIdStabilimento()>0)
            	domicilioDigitale =  Stabilimento.getOperatore().getDomicilioDigitale();
            else if (Richiesta.getOpuDomicilioDigitale()!=null)
            	domicilioDigitale = Richiesta.getOpuDomicilioDigitale();
            
            int nazioneSedeLegale = 106;
            if (Stabilimento.getIdStabilimento()>0)
            	nazioneSedeLegale =  Stabilimento.getOperatore().getSedeLegale().getNazione();
            else if (Richiesta.getOpuNazioneSedeLegale()>0)
            	nazioneSedeLegale = Richiesta.getOpuNazioneSedeLegale();
            
            int provinciaSedeLegale = -1;
            if (Stabilimento.getIdStabilimento()>0)
            	provinciaSedeLegale =  Stabilimento.getOperatore().getSedeLegale().getIdProvincia();
            else if (Richiesta.getOpuProvinciaSedeLegale()>0)
            	provinciaSedeLegale = Richiesta.getOpuProvinciaSedeLegale(); 
            
            String descrizioneProvinciaSedeLegale = "";
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getSedeLegale().getDescrizione_provincia()!=null)
            	descrizioneProvinciaSedeLegale =  Stabilimento.getOperatore().getSedeLegale().getDescrizione_provincia();
            else if (Richiesta.getOpuProvinciaSedeLegale()>0)
            	descrizioneProvinciaSedeLegale = Richiesta.getOpuDescrizioneProvinciaSedeLegale(); 
            
            int comuneSedeLegale = -1;
            if (Stabilimento.getIdStabilimento()>0)
            	comuneSedeLegale =  Stabilimento.getOperatore().getSedeLegale().getComune();
            else if (Richiesta.getOpuComuneSedeLegale()>0)
            	comuneSedeLegale = Richiesta.getOpuComuneSedeLegale();
            
            String descrizioneComuneSedeLegale = "";
            if (Stabilimento.getIdStabilimento()>0 &&  Stabilimento.getOperatore().getSedeLegale().getDescrizioneComune()!=null)
            	descrizioneComuneSedeLegale =  Stabilimento.getOperatore().getSedeLegale().getDescrizioneComune();
            else if (Richiesta.getOpuComuneSedeLegale()>0)
            	descrizioneComuneSedeLegale = Richiesta.getOpuDescrizioneComuneSedeLegale();
            
            int toponimoSedeLegale = -1;
            if (Stabilimento.getIdStabilimento()>0)
            	toponimoSedeLegale =  Stabilimento.getOperatore().getSedeLegale().getToponimo();
            else if (Richiesta.getOpuToponimoSedeLegale()>0)
            	toponimoSedeLegale = Richiesta.getOpuToponimoSedeLegale();  
            
            String descrizioneToponimoSedeLegale = "VIA";
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getSedeLegale().getDescrizioneToponimo()!=null)
            	descrizioneToponimoSedeLegale =  Stabilimento.getOperatore().getSedeLegale().getDescrizioneToponimo();
            else if (Richiesta.getOpuToponimoSedeLegale()>0)
            	descrizioneToponimoSedeLegale = Richiesta.getOpuDescrizioneToponimoSedeLegale();  
            
            String viaSedeLegale = "";
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getSedeLegale().getVia()!=null)
            	viaSedeLegale =  Stabilimento.getOperatore().getSedeLegale().getVia();
            else if (Richiesta.getOpuViaSedeLegale()!=null)
            	viaSedeLegale = Richiesta.getOpuViaSedeLegale();  
           
            String civicoSedeLegale = "";
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getSedeLegale().getCivico()!=null)
            	civicoSedeLegale =  Stabilimento.getOperatore().getSedeLegale().getCivico();
            else if (Richiesta.getOpuCivicoSedeLegale()!=null)
            	civicoSedeLegale = Richiesta.getOpuCivicoSedeLegale();  
            
            String capSedeLegale = "";
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getSedeLegale().getCap()!=null)
            	capSedeLegale =  Stabilimento.getOperatore().getSedeLegale().getCap();
            else if (Richiesta.getOpuCapSedeLegale()!=null)
            	capSedeLegale = Richiesta.getOpuCapSedeLegale();  
            
            String nomeRappresentante = "";
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getRappLegale().getNome()!=null)
            	nomeRappresentante =  Stabilimento.getOperatore().getRappLegale().getNome();
            else if (Richiesta.getOpuNomeRappresentante()!=null)
            	nomeRappresentante = Richiesta.getOpuNomeRappresentante();  
            
            String cognomeRappresentante = "";
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getRappLegale().getCognome()!=null)
            	cognomeRappresentante =  Stabilimento.getOperatore().getRappLegale().getCognome();
            else if (Richiesta.getOpuNomeRappresentante()!=null)
            	cognomeRappresentante = Richiesta.getOpuCognomeRappresentante();
            
            String sessoRappresentante = "";
            if (Stabilimento.getIdStabilimento()>0 &&  Stabilimento.getOperatore().getRappLegale().getSesso()!=null)
            	sessoRappresentante =  Stabilimento.getOperatore().getRappLegale().getSesso();
            else if (Richiesta.getOpuSessoRappresentante()!=null)
            	sessoRappresentante = Richiesta.getOpuSessoRappresentante();  
            
            String dataNascitaRappresentante = "";
            if (Stabilimento.getIdStabilimento()>0 &&  Stabilimento.getOperatore().getRappLegale().getDataNascita()!=null)
            	dataNascitaRappresentante =  Stabilimento.getOperatore().getRappLegale().getDataNascita().toString();
            else if (Richiesta.getOpuDataNascitaRappresentante()!=null)
            	dataNascitaRappresentante = Richiesta.getOpuDataNascitaRappresentante().toString();  
            
            int nazioneNascitaRappresentante = 106;
            if (Stabilimento.getIdStabilimento()>0)
            	nazioneNascitaRappresentante =  Stabilimento.getOperatore().getRappLegale().getNazioneNascita();
            else if (Richiesta.getOpuNazioneNascitaRappresentante()>0)
            	nazioneNascitaRappresentante = Richiesta.getOpuNazioneNascitaRappresentante();  
            
            String comuneNascitaRappresentante = "";
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getRappLegale().getComuneNascita()!=null)
            	comuneNascitaRappresentante =  Stabilimento.getOperatore().getRappLegale().getComuneNascita();
            else if (Richiesta.getOpuComuneNascitaRappresentante()!=null)
            	comuneNascitaRappresentante = Richiesta.getOpuComuneNascitaRappresentante();  
            
            String codiceFiscaleRappresentante = "";
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getRappLegale().getCodiceFiscale()!=null)
            	codiceFiscaleRappresentante =  Stabilimento.getOperatore().getRappLegale().getCodiceFiscale();
            else if (Richiesta.getOpuCodiceFiscaleRappresentante()!=null)
            	codiceFiscaleRappresentante = Richiesta.getOpuCodiceFiscaleRappresentante();  
            
            int nazioneResidenzaRappresentante = 106;
            if (Stabilimento.getIdStabilimento()>0)
            	nazioneResidenzaRappresentante =  (Stabilimento.getOperatore().getRappLegale().getIndirizzo() != null && Stabilimento.getOperatore().getRappLegale().getIndirizzo().getNazione()>0) ? Stabilimento.getOperatore().getRappLegale().getIndirizzo().getNazione() : 106;
            else if (Richiesta.getOpuNazioneResidenzaRappresentante()>0)
            	nazioneResidenzaRappresentante = Richiesta.getOpuNazioneResidenzaRappresentante();  
            
            int provinciaResidenzaRappresentante = -1;
            if (Stabilimento.getIdStabilimento()>0)
            	provinciaResidenzaRappresentante =  (Stabilimento.getOperatore().getRappLegale().getIndirizzo() != null) ?  Stabilimento.getOperatore().getRappLegale().getIndirizzo().getIdProvincia() : -1;
            else if (Richiesta.getOpuProvinciaResidenzaRappresentante()>0)
            	provinciaResidenzaRappresentante = Richiesta.getOpuProvinciaResidenzaRappresentante();  
            
            String descrizioneProvinciaResidenzaRappresentante = "";
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getRappLegale().getIndirizzo()!=null && Stabilimento.getOperatore().getRappLegale().getIndirizzo().getDescrizione_provincia()!=null)
            	descrizioneProvinciaResidenzaRappresentante =  Stabilimento.getOperatore().getRappLegale().getIndirizzo().getDescrizione_provincia();
            else if (Richiesta.getOpuProvinciaResidenzaRappresentante()>0)
            	descrizioneProvinciaResidenzaRappresentante = Richiesta.getOpuDescrizioneProvinciaResidenzaRappresentante();  
            
            int comuneResidenzaRappresentante = -1;
            if (Stabilimento.getIdStabilimento()>0)
            	comuneResidenzaRappresentante =   Stabilimento.getOperatore().getRappLegale().getIndirizzo()!=null ? Stabilimento.getOperatore().getRappLegale().getIndirizzo().getComune() : -1;
            else if (Richiesta.getOpuComuneResidenzaRappresentante()>0)
            	comuneResidenzaRappresentante = Richiesta.getOpuComuneResidenzaRappresentante();  
        
            String descrizioneComuneResidenzaRappresentante = "";
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getRappLegale().getIndirizzo()!=null && Stabilimento.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune()!=null)
            	descrizioneComuneResidenzaRappresentante =  Stabilimento.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune();
            else if (Richiesta.getOpuProvinciaResidenzaRappresentante()>0)
            	descrizioneComuneResidenzaRappresentante = Richiesta.getOpuDescrizioneComuneResidenzaRappresentante(); 
            
            int toponimoResidenzaRappresentante = -1;
            if (Stabilimento.getIdStabilimento()>0)
            	toponimoResidenzaRappresentante =   Stabilimento.getOperatore().getRappLegale().getIndirizzo()!=null ? Stabilimento.getOperatore().getRappLegale().getIndirizzo().getToponimo() : -1;
            else if (Richiesta.getOpuToponimoResidenzaRappresentante()>0)
            	toponimoResidenzaRappresentante = Richiesta.getOpuToponimoResidenzaRappresentante();  
            
            String descrizioneToponimoResidenzaRappresentante = "VIA";
            if (Stabilimento.getIdStabilimento()>0 &&  Stabilimento.getOperatore().getRappLegale().getIndirizzo()!=null && Stabilimento.getOperatore().getRappLegale().getIndirizzo().getDescrizioneToponimo()!=null)
            	descrizioneToponimoResidenzaRappresentante =  Stabilimento.getOperatore().getRappLegale().getIndirizzo().getDescrizioneToponimo();
            else if (Richiesta.getOpuToponimoResidenzaRappresentante()>0)
            	descrizioneToponimoResidenzaRappresentante = Richiesta.getOpuDescrizioneToponimoResidenzaRappresentante();  
            
            String viaResidenzaRappresentante = "";
            if (Stabilimento.getIdStabilimento()>0 &&  Stabilimento.getOperatore().getRappLegale().getIndirizzo()!=null && Stabilimento.getOperatore().getRappLegale().getIndirizzo().getVia()!=null)
            	viaResidenzaRappresentante =  Stabilimento.getOperatore().getRappLegale().getIndirizzo().getVia();
            else if (Richiesta.getOpuViaResidenzaRappresentante()!=null)
            	viaResidenzaRappresentante = Richiesta.getOpuViaResidenzaRappresentante();  
           
            String civicoResidenzaRappresentante = "";
            if (Stabilimento.getIdStabilimento()>0 &&  Stabilimento.getOperatore().getRappLegale().getIndirizzo()!=null && Stabilimento.getOperatore().getRappLegale().getIndirizzo().getCivico()!=null)
            	civicoResidenzaRappresentante =  Stabilimento.getOperatore().getRappLegale().getIndirizzo().getCivico();
            else if (Richiesta.getOpuCivicoResidenzaRappresentante()!=null)
            	civicoResidenzaRappresentante = Richiesta.getOpuCivicoResidenzaRappresentante();  
            
            String capResidenzaRappresentante = "";
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getRappLegale().getIndirizzo()!=null &&  Stabilimento.getOperatore().getRappLegale().getIndirizzo().getCap()!=null)
            	capResidenzaRappresentante =  Stabilimento.getOperatore().getRappLegale().getIndirizzo().getCap();
            else if (Richiesta.getOpuCapResidenzaRappresentante()!=null)
            	capResidenzaRappresentante = Richiesta.getOpuCapResidenzaRappresentante(); 
            
            String domicilioDigitaleRappresentante = "";
            if (Stabilimento.getIdStabilimento()>0 &&  Stabilimento.getOperatore().getRappLegale().getIndirizzo()!=null && Stabilimento.getOperatore().getRappLegale().getDomicilioDigitale()!=null)
            	domicilioDigitaleRappresentante =  Stabilimento.getOperatore().getRappLegale().getDomicilioDigitale();
            else if (Richiesta.getOpuDomicilioDigitaleRappresentante()!=null)
            	domicilioDigitaleRappresentante = Richiesta.getOpuDomicilioDigitaleRappresentante();
            
            double latitudine = 0;
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getIndirizzo().getLatitudine()>0)
            	latitudine =  Stabilimento.getIndirizzo().getLatitudine();
            else if (Richiesta.getLatitudine()>0)
            	latitudine = Richiesta.getLatitudine();
            
            double longitudine = 0;
            if (Stabilimento.getIdStabilimento()>0 && Stabilimento.getIndirizzo().getLongitudine()>0)
            	longitudine =  Stabilimento.getIndirizzo().getLongitudine();
            else if (Richiesta.getLatitudine()>0)
            	longitudine = Richiesta.getLongitudine();
           
            String capStabilimento = "";
            if (Stabilimento.getIdStabilimento()>0 &&  Stabilimento.getIndirizzo()!=null && Stabilimento.getIndirizzo().getCap()!=null)
            	capStabilimento =  Stabilimento.getIndirizzo().getCap();
            else if (Richiesta.getCap()!=null)
            	capStabilimento = Richiesta.getCap();
            %>
    
 
   <form id = "addAccount" name="addAccount" action="StabilimentoSintesisAction.do?command=<%=metodo %>&auto-populate=true" method="post">
   
   <input type="hidden" name="idRichiesta"	id="idRichiesta" value="<%=Richiesta.getId()%>">
   <input type="hidden" name="idStabilimento"	id="idStabilimento" value="<%=Stabilimento.getIdStabilimento()%>">
   
   
      <div style="display: none;"> 
         &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
      </div>
      <h3>TIPO IMPRESA</h3>
      <fieldset>
         <legend>INDICARE IL TIPO DI IMPRESA</legend>
         <%
            TipoImpresaList.setJsEvent("onchange=onChangeTipoImpresaMinimal();");
            TipoImpresaList.addItem(-1, "Seleziona Voce");
            %>
          
         <%=TipoImpresaList.getHtmlSelect("tipo_impresa", tipoImpresa) %>
         
      </fieldset>
      <h3>DETTAGLI IMPRESA</h3>
      <fieldset id="formImpresa">
         <fieldset id="dati_impresa">
            <legend>DATI IMPRESA</legend>
            <table style="height: 100%; width: 100%">
               <tr id="tipo_societaTR" <% if ( Stabilimento.getIdStabilimento()>0 && Stabilimento.getOperatore().getTipoImpresa()!=4 && Stabilimento.getOperatore().getTipoImpresa()!=3 && Stabilimento.getOperatore().getTipoImpresa()!=2 )  {%>style="display:none"<%} %>>
                  <td nowrap id="trTipoSocieta">TIPO SOCIETA'</td>
                  <td><%=TipoSocietaList.getHtmlSelect("tipo_societa", tipoSocieta)%></td>
               </tr>
          
              <tr id="codFiscaleTR">
                  <td nowrap>DOMICILIO DIGITALE(PEC)</td>
                  <td><input type="text" size="70" maxlength="70" id="domicilioDigitale" name="domicilioDigitale" value="<%= toHtml(domicilioDigitale)%>"></td>
               </tr>
            </table>
         </fieldset>
         <fieldset id="setSedeLegale">
            <legend>DATI SEDE LEGALE</legend>
            <table style="height: 100%; width: 100%">
               <tr>
                  <td><label for="nazioneN-2">NAZIONE</label></td>
                  <td>
                     <% NazioniList.setJsEvent("onchange=\"sbloccoProvincia('nazioneSedeLegale','searchcodeIdprovincia','searchcodeIdComune','via')\""); %> 
                     <% NazioniList.setJsEvent("onChange=\"nascondiCap(this.value,'presso','butCapSedLeg')\""); %> 
                     <%=NazioniList.getHtmlSelect("nazioneSedeLegale", nazioneSedeLegale)%>
                     
                     <script>
                     $('#nazioneSedeLegale option[value!="106"]').remove();
                     </script>
                     
                  </td>
               </tr>
               <tr id="searchcodeIdprovinciaTR">
                  <td>PROVINCIA</td>
                  <td>
                     <select name="searchcodeIdprovincia" id="searchcodeIdprovincia" class="required">
                     
                     <% if (provinciaSedeLegale>0) {%>
                     <option value="<%=provinciaSedeLegale%>"><%=descrizioneProvinciaSedeLegale %></option>
                     <%} %>
                     
                        <option value=""></option>
                     </select>
                     <input type="hidden" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" />
                  </td>
               </tr>
               <tr>
                  <td>COMUNE</td>
                  <td><select name="searchcodeIdComune" id="searchcodeIdComune" class="required">
                   <% if (comuneSedeLegale>0) {%>
                     <option value="<%=comuneSedeLegale%>"><%=descrizioneComuneSedeLegale %></option>
                     <%} %>
                     </select> 
                     <input type="hidden" name="searchcodeIdComuneTesto" id="searchcodeIdComuneTesto" />
                  </td>
               </tr>
               <tr>
                  <td>INDIRIZZO</td>
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
                              type="button" value="Calcola CAP" id="butCapSedLeg"
                              onclick="if (document.getElementById('searchcodeIdComune').value!='') { calcolaCap(document.getElementById('searchcodeIdComune').value, document.getElementById('toponimoSedeLegale').value, document.getElementById('via').value, 'presso') };" />
                           </td>
                        </tr>
                     </table>
                  </td>
               </tr>
            </table>
         </fieldset>
         <br><br>
         <fieldset  id="dati_titolare">
            <legend>DATI TITOLARE/LEGALE RAPPRESENTANTE</legend>
            <table style="height: 100%; width: 100%">
               <tr>
                  <td>NOME</td>
                  <td><input type="text" size="70" id="nome" name="nome" value="<%=nomeRappresentante%>"> 
                  </td>
               </tr>
               <tr>
                  <td><label for="cognome-2">COGNOME </label></td>
                  <td><input type="text" size="70" id="cognome" name="cognome" class="required" value="<%=cognomeRappresentante%>"> 
                  </td>
               </tr>
               <tr>
                  <td><label for="sesso-2">SESSO </label></td>
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
                  <td><label for="dataN-2">DATA NASCITA </label></td>
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
                  <td><label for="nazioneN-2">NAZIONE NASCITA</label></td>
                  <td>
                     <%
                        NazioniList
                        		.setJsEvent("onchange=\"abilitaCodiceFiscale('nazioneNascita');sbloccoProvincia('nazioneNascita',null,'comuneNascita',null)\"");
                        %>
                     <%=NazioniList.getHtmlSelect("nazioneNascita", nazioneNascitaRappresentante)%>
                  </td>
               </tr>
               <tr>
                  <td nowrap>COMUNE NASCITA</td>
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
                  <td nowrap>CODICE FISCALE</td>
                  <td><input type="text" name="codFiscaleSoggetto"  value="<%=codiceFiscaleRappresentante%>"
                     readonly="readonly" id="codFiscaleSoggetto" class="required"
                     /></td>
               </tr>
               <tr>
                  <td>&nbsp;</td>
                  <td><input type="button" id="calcoloCF" class="newButtonClass"
                     value="CALCOLA CODICE FISCALE"  <%-- name="daBloccare"--%>
                     onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.getElementById('comuneNascitainput'),document.forms[0].dataNascita,'codFiscaleSoggetto')"></input>
                  </td>
               </tr>
               <tr>
                  <td><label for="nazioneN-2">NAZIONE RESIDENZA</label></td>
                  <td>
                     <%
                        NazioniList
                        		.setJsEvent("onchange=\"sbloccoProvincia('nazioneResidenza','addressLegaleCountry','addressLegaleCity','addressLegaleLine1')\"");
                        %>
                     <% NazioniList.setJsEvent("onChange=\"nascondiCap(this.value,'capResidenza','butCapResid')\""); %> 
                     <%=NazioniList.getHtmlSelect("nazioneResidenza", nazioneResidenzaRappresentante)%>
                  </td>
               </tr>
               <tr id="addressLegaleCountryTR">
                  <td nowrap>PROVINCIA RESIDENZA</td>
                  <td><select id="addressLegaleCountry"
                     name="addressLegaleCountry">
                     
                     <%if (provinciaResidenzaRappresentante>0){ %>
                        <option value="<%=provinciaResidenzaRappresentante%>"><%=descrizioneProvinciaResidenzaRappresentante %></option>
                        <%} %>
                     
                     </select> <input type="hidden" id="addressLegaleCountryTesto"
                        name="addressLegaleCountryTesto" />
                  </td>
               </tr>
               <tr>
                  <td nowrap>COMUNE RESIDENZA</td>
                  <td>
                     <select name="addressLegaleCity" id="addressLegaleCity">
                       <%if (comuneResidenzaRappresentante>0){ %>
                        <option value="<%=comuneResidenzaRappresentante%>"><%=descrizioneComuneResidenzaRappresentante %></option>
                        <%} %>
                     </select> <input type="hidden" name="addressLegaleCityTesto"
                        id="addressLegaleCityTesto" />
                  </td>
               </tr>
               <tr>
                  <td>INDIRIZZO RESIDENZA</td>
                  <td>
                     <table class="noborder">
                        <tr>
                        <td>
							
							<%=ToponimiList.getHtmlSelect("toponimoResidenza", descrizioneToponimoResidenzaRappresentante)%>
							</td>
                           <td><select name="addressLegaleLine1"
                              id="addressLegaleLine1" >
                              
                                <%if (!viaResidenzaRappresentante.equals("")){ %>
                        <option value="<%=viaResidenzaRappresentante%>"><%=viaResidenzaRappresentante %></option>
                        <%} %>
                              
                              </select> <input type="hidden" name="addressLegaleLine1Testo"
                                 id="addressLegaleLine1Testo" value="" />
                           </td>
                           <td><input type="text" name="civicoResidenza"
                              id="civicoResidenza" size="5" placeholder="NUM." maxlength="15"  value="<%=civicoResidenzaRappresentante%>"></td>
                           <td><input type="text" name="capResidenza"
                              id="capResidenza" size="4" placeholder="CAP" maxlength="5"  value="<%=capResidenzaRappresentante%>">
                              <input type="button" value="Calcola CAP" id="butCapResid"
                                 onclick="if (document.getElementById('addressLegaleCity').value!='') { calcolaCap(document.getElementById('addressLegaleCity').value, document.getElementById('toponimoResidenza').value, document.getElementById('addressLegaleLine1Testo').value, 'capResidenza'); }" />
                           </td>
                        </tr>
                     </table>
                  </td>
               </tr>
               <tr>
                  <td>DOMICILIO DIGITALE<br>(PEC)
                  </td>
                  <td><input type="text" size="70" name="domicilioDigitalePecSF"  value="<%=domicilioDigitaleRappresentante%>"> </td>
               </tr>
            </table>
         </fieldset>
      </fieldset>
      
      <fieldset id="formStabilimento">
         <fieldset id="dati_stabilimento">
            <legend>DATI STABILIMENTO</legend>
            <table style="height: 100%; width: 100%">
             <tr>
                  <td nowrap>CAP</td>
                  <td> 
                  <input type="text" id="capStab" name="capStab" placeholder="CAP" value="<%=capStabilimento.trim()%>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" maxlength="5"  pattern=".{5,}"  required title="Inserire CAP a 5 cifre." onBlur="if(this.value!='' && this.value.length!=5){alert('Inserire CAP a 5 cifre!');this.value='';}"/>
				  </td>
               </tr>
              <tr>
                  <td nowrap>COORDINATE</td>
                  <td> 
                  
                LAT <input type="text" id="latitudineStab" name="latitudineStab" placeholder="LAT" value="<%=latitudine%>" onkeyup="this.value=this.value.replace(/[^0-9.]+/,'');" onfocusout="checkLat(this)"/> 
				LON <input type="text" id="longitudineStab" name="longitudineStab" placeholder="LON" value="<%=longitudine%>" onkeyup="this.value=this.value.replace(/[^0-9.]+/,'');" onfocusout="checkLon(this)"/>
				 
				<input id="coord1button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate('<%=Stabilimento.getIndirizzo().getDescrizioneToponimo() %> <%=Stabilimento.getIndirizzo().getVia().replaceAll("'", "") %> <%=Stabilimento.getIndirizzo().getCivico() %>', '<%=Stabilimento.getIndirizzo().getDescrizioneComune().replaceAll("'", "")%>', '<%=Stabilimento.getIndirizzo().getDescrizione_provincia().replaceAll("'", "") %>', '', document.getElementById('latitudineStab'), document.getElementById('longitudineStab'));" />
                  
                  </td>
               </tr>
            </table>
         </fieldset>
         
      <center>
      
         <input type="button" value="ANNULLA" onClick="annulla(this.form)"/>
         
         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         
         <input type="button" value="CONFERMA" onClick="checkForm(this.form)"/>
      </center>
  

   </form>
</dhv:container>



