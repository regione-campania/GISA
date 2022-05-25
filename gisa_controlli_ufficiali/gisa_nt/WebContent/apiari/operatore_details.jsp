<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="ext.aspcfs.modules.apiari.base.StabilimentoEstrazione"%>
<%@page import="java.util.Date"%>
<%@page import="ext.aspcfs.modules.apicolture.actions.StabilimentoAction"%>
<%@page import="ext.aspcfs.modules.apicolture.actions.CfUtil"%>
<%@page import="ext.aspcfs.modules.apiari.base.Stabilimento"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>
<%@page import="java.sql.*,java.util.HashMap,java.util.Map"%>
<%@page import="org.aspcfs.modules.opu.base.ComuniAnagrafica"%>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LoginBean" class="org.aspcfs.modules.login.beans.LoginBean" scope="request"/>

<jsp:useBean id="mapAttributi" class="java.util.HashMap" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="OperatoreDettagli" class="ext.aspcfs.modules.apiari.base.Operatore" scope="request" />

<jsp:useBean id="ApicolturaSottospecie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ApicolturaModalita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ApicolturaClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAttivitaApi" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="nome_entered" class="java.lang.String" scope="request" />
<jsp:useBean id="nome_modified" class="java.lang.String" scope="request" />

<jsp:useBean id="LookupStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="msgCancellazione" class="java.lang.String" scope="request" />
<jsp:useBean id="msgAggiornamento" class="java.lang.String" scope="request" />


<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>



<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilterApi.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<%@ include file="../initPage.jsp"%>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>



<script>
function rosso(){

	if(document.getElementById('lampeggio')!=null)
	{
document.getElementById('lampeggio').style.color='red';
setTimeout("giallo()",800);
}

}
function giallo(){
	if(document.getElementById('lampeggio')!=null)
	{
document.getElementById('lampeggio').style.color='white';
setTimeout("rosso()",800);
	}

	
}

function rosso_lampeggio(){


	if(document.getElementById('lampeggio2')!=null)
	{
document.getElementById('lampeggio2').style.color='red';
setTimeout("giallo_lampeggio()",500);
}
}
function giallo_lampeggio(){

	if(document.getElementById('lampeggio2')!=null)
	{
document.getElementById('lampeggio2').style.color='white';
setTimeout("rosso_lampeggio()",800);
	}
}
</script>

<script>
setTimeout( "rosso()",500);
setTimeout( "rosso_lampeggio()",200);

</script>

<script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>

<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var attivataModificaTitolare = "false";
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
	
	function checkFirefox(msg)
	{
		
		if(msg!=null && msg!="")
			{
			if(confirm(msg)==false)
				{
				location.href="Login.do?command=Logout";
				}
			}
			
			}
</SCRIPT>


<%
	String dataFrom = (String)request.getAttribute("dataFrom");
	String dataTo =   (String)request.getAttribute("dataTo");
	Boolean inRangeAnnoAttuale = (Boolean)request.getAttribute("inRangeAnnoAttuale");
	Boolean inRangeAnnoPrecedente = (Boolean)request.getAttribute("inRangeAnnoPrecedente");
	Boolean inRangeTolleranzaAnnoAttuale = (Boolean)request.getAttribute("inRangeTolleranzaAnnoAttuale");
	Boolean inRangeTolleranzaAnnoPrecedente = (Boolean)request.getAttribute("inRangeTolleranzaAnnoPrecedente");
	Date dataInizioAnno 				=   (Date)request.getAttribute("dataInizioAnno");
	int annoOdierno 				=   (Integer)request.getAttribute("annoOdierno");
%>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

<script type="text/javascript">
	function apriTemplate()
	{
		var urlTemplate = "apiari/import_massivo_api_regine.xls";
		window.open(urlTemplate);
	}
</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<SCRIPT src="javascript/apiari.js"></SCRIPT>
<style>
.displaynone
{
display: none;

}
.display
{
display: ;

}
</style>

<script>


var campoLat;
var campoLong;
	function showCoordinate(address,city,prov,cap,campo_lat,campo_long)
	{
   campoLat = campo_lat;
   campoLong = campo_long;
   Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
   
   
}
function setGeocodedLatLonCoordinate(value)
{
	campoLat.value = value[1];;
	campoLong.value =value[0];
	
}

RegExp.escape = function(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
}
String.prototype.replaceAll = function(search, replace) {
    return this.replace(new RegExp(RegExp.escape(search),'g'), replace);
};

function showdialogCessazione()
{
	 $("#dialogCessazione").dialog("open");
	}
	
function showdialogLaboratorio()
{
	 $("#dialogLaboratorio").dialog("open");
	}
	
$(function() {
	  
    $( "#addressLegaleCity" ).combobox();
    $( "#searchcodeIdComune" ).combobox();
    $( "#codFiscaleSoggettoAdd" ).combobox();

	$( "#codFiscaleSoggettoAddinput" ).attr("required","required");

    
    
});
 



$(function () {
	 $( "#dialogProprietario" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"CAMBIO DETENTORE",
	        width:850,
	        height:500,
	        draggable: false,
	        open :function(){document.getElementById("dialogUbicazione").focus();},
	        modal: true,
	        position: 'top',
	        buttons:{
	        	 "Salva": function() {if(checkFormDetentore()){$("#addpersona").submit(); }} ,
	        	 "Esci" : function() {
	        		 document.getElementById('codFiscaleSoggettoAddinput').value='';
         			 document.getElementById('codFiscaleSoggettoAddinput').readOnly =false;
         			 document.getElementById('detentore').style.display="none";
         			document.getElementById('nominativoDetNome').style.display="";
         			document.getElementById('nominativoDetCognome').style.display="";
         			document.getElementById('calcoloCF').style.display="none";
         			document.getElementById('idSoggettoFisico').value='';
         			
         			
	        		 
	        		 $(this).dialog("close");
	        	 $("html,body").animate({scrollTop: 0}, 500, function(){});
	        	 }
	        	
	        },
	        show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	       
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	 
	 
	 
	 
		 $( "#dialogMovMassiva" ).dialog({
		    	autoOpen: false,
		        resizable: false,
		        closeOnEscape: false,
		       	title:"MOVIMENTAZIONE MASSIVA API REGINE",
		        width:850,
		        height:500,
		        draggable: false,
		        open :function(){document.getElementById("dialogUbicazione").focus();},
		        modal: true,
		        position: 'top',
		        buttons:{
		        	 "Salva": function() {if(checkFormApiRegine()){$("#movmassivaapiregineForm").submit(); }} ,
		        	 "Esci" : function() {
		        		 $(this).dialog("close");
		        	 $("html,body").animate({scrollTop: 0}, 500, function(){});
		        	 }
		        	
		        },
		        show: {
		            effect: "blind",
		            duration: 1000
		        },
		        hide: {
		            effect: "explode",
		            duration: 1000
		        }
		       
		    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	 
	 
	 $( "#dialogCessazione" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"CESSAZIONE ATTIVITA",
	        width:350,
	        height:200,
	        draggable: false,
	        modal: true,
	        position: 'top',
	        buttons:{
	        	 "Salva": function() {if(checkFormCessazione()){$("#formcessazione").submit(); }} ,
	        	 "Esci" : function() {
	        		
	        		 
	        		 
	        		 $(this).dialog("close");
	        	 $("html,body").animate({scrollTop: 0}, 500, function(){});
	        	 }
	        	
	        },
	        show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	       
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	 
	 $( "#dialogLaboratorio" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"GESTIONE PRESENZA LABORATORIO DI SMIELATURA",
	        width:350,
	        height:250,
	        draggable: false,
	        modal: true,
	        position: 'top',
	        buttons:{
	        	 "Salva": function() {if(checkFormLaboratorio()){$("#formlaboratorio").submit(); }} ,
	        	 "Esci" : function() {
	        		
	        		 
	        		 
	        		 $(this).dialog("close");
	        	 $("html,body").animate({scrollTop: 0}, 500, function(){});
	        	 }
	        	
	        },
	        show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	       
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	 
	 
	 function checkFormCessazione()
	 {
	 	
	 	
	 	 	formTest = true;
	 	    message = "";
	 	    alertMessage = "";
	 	   
	 	    form = document.formcessazione ;
	 	    
	 	   if (form.dataCessazione.value == ""){
	 	        message += "- Data Cessazione richiesto\r\n";
	 	        formTest = false;
	 	     }
	 	
	 	   
			
	 	    
	 	    if (formTest == false) {
	 	        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	 	        return false;
	 	      } else {
	 	      
	 	        loadModalWindow();
	 	        return true;
	 	      }
	 		
	 }
	 
	 
	 function checkFormDetentore()
	 {
	 	
	 	
	 	 	formTest = true;
	 	    message = "";
	 	    alertMessage = "";
	 	   
	 	    form = document.addpersona ;
	 	    
	 	    if (form.codFiscaleSoggettoAddinput.value == ""){
	 	        message += "- Codice Fiscale richiesto\r\n";
	 	        formTest = false;
	 	     }
	 	    
	 	   if (form.data_assegnazione_detentore.value == ""){
	 	        message += "- Data Assegnazione richiesto\r\n";
	 	        formTest = false;
	 	     }
	 	 
	 	    
	 	   if (form.nomeDet.value == ""){
	 	        message += "- Nome richiesto\r\n";
	 	        formTest = false;
	 	     }
	 	   
	 	  if (form.cognomeDet.value == ""){
	 	        message += "- Cognome richiesto\r\n";
	 	        formTest = false;
	 	     }
	 	    
	 	   
	 	    
			 if (form.addressLegaleCity.value == "" && document.getElementById("detentore").style.display==""){
		 	        message += "- Comune Residenza richiesto\r\n";
		 	        formTest = false;
		 	     }
			 
			 if (form.addressLegaleCountry.value == "" && document.getElementById("detentore").style.display==""){
		 	        message += "- Provincia Residenza richiesto\r\n";
		 	        formTest = false;
		 	     }
			 
			 if (form.addressLegaleLine1Testo.value == "" && document.getElementById("detentore").style.display==""){
		 	        message += "- Indirizzo Residenza richiesto\r\n";
		 	        formTest = false;
		 	     }
			 
			 if(form.idSoggettoFisico.value!='')
				 {
				   formTest = true;
				   message = "";
				   if (form.data_assegnazione_detentore.value == ""){
			 	        message += "- Data Assegnazione richiesto\r\n";
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
	 

	 function checkFormUbicazione()
	 {
	 	
	 	
	 	 	formTest = true;
	 	    message = "";
	 	    alertMessage = "";
	 	   
	 	    form = document.addubicazione ;
	 	    
	 	   if (form.data_assegnazione_ubicazione.value == ""){
	 	        message += "- Data Spostamento Ubicazione richiesto\r\n";
	 	        formTest = false;
	 	     }
	 	
	 	    if (form.searchcodeIdComuneinput.value == ""){
	 	        message += "- Comune richiesto\r\n";
	 	        formTest = false;
	 	     }
			 if (form.presso.value == ""){
		 	        message += "- Cap richiesto\r\n";
		 	        formTest = false;
		 	     }
			 
			 if (form.viaTesto.value == ""){
		 	        message += "- Indirizzo richiesto\r\n";
		 	        formTest = false;
		 	     }
			 
			 if (form.searchcodeIdprovinciaTesto.value == ""){
		 	        message += "- Provincia richiesto\r\n";
		 	        formTest = false;
		 	     }
			 
			 if (form.latitudine.value == ""){
		 	        message += "- Latitudine richiesto\r\n";
		 	        formTest = false;
		 	     }
			 
			 if (form.longitudine.value == ""){
		 	        message += "- Longitudine richiesto\r\n";
		 	        formTest = false;
		 	     }
			 
			
	 	    
	 	    if (formTest == false) {
	 	        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	 	        return false;
	 	      } else {
	 	      
	 	        loadModalWindow();
	 	        return true;
	 	      }
	 		
	 }
	 
	 
	 
	 function checkFormCensimento()
	 {
	 	
	 	
	 	 	formTest = true;
	 	    message = "";
	 	    alertMessage = "";
	 	   
	 	    form = document.addcensimento ;
	 	
	 	   if (form.data_assegnazione_censimento.value == ""){
	 	        message += "- Data Censimento richiesto\r\n";
	 	        formTest = false;
	 	     }
	 	   
	 	    if (form.numAlveari.value == ""){
	 	        message += "- Num Alveari richiesto\r\n";
	 	        formTest = false;
	 	     }
			 if (form.numSciami.value == ""){
		 	        message += "- Num Sciami/Nuclei richiesto\r\n";
		 	        formTest = false;
		 	     }
			 
			   
	 	    if (formTest == false) {
	 	        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	 	        return false;
	 	      } else {
	 	      
	 	        loadModalWindow();
	 	        return true;
	 	      }
	 		
	 }
	 
	 
	 function checkFormCessazioneApiario()
	 {
	 	
	 	
	 	 	formTest = true;
	 	    message = "";
	 	    alertMessage = "";
	 	   
	 	    form = document.cessazioneApiarioForm ;
	 	
	 	   if (form.data_cessazione_apiario.value == ""){
	 	        message += "- Data Cessazione Apiario\r\n";
	 	        formTest = false;
	 	     }
	 	   
	 	    
			 
			   
	 	    if (formTest == false) {
	 	        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	 	        return false;
	 	      } else {
	 	      
	 	        loadModalWindow();
	 	        return true;
	 	      }
	 		
	 }
	 
	 function checkFormApiRegine()
	 {
	 	 	formTest = true;
	 	    message = "";
	 	    alertMessage = "";
	 	   
	 	    form = document.movmassivaapiregineForm ;
	 	
	 	   if(form.file1.value=="")
	 	   {
	 			message += "- Selezionare file";
	 			formTest = false;
	 	   }
	 	   else if(!form.file1.value.endsWith(".xls"))
	 	   {
	 			message += "- Selezionare un file xls";
	 			formTest = false;
	 	   }
	 	   
	 	    
			 
			   
	 	    if (formTest == false) {
	 	        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	 	        return false;
	 	      } else {
	 	      
	 	        loadModalWindow();
	 	        return true;
	 	      }
	 		
	 }
	 
	 function checkFormModificaTitolare()
	 {
	 	 formTest = true;
	 	 message = "";
	 	 alertMessage = "";
	 	   
	 	 form = document.modificaTitolareForm;
	 	
	 	 if (form.data_modifica_titolare.value == "")
	 	 {
 	     	message += "- Data Modifica\r\n";
 	        formTest = false;
 	     }
	 	 
	 	 if (form.usa_questo_proprietario.checked)
	 	 {
	 		 if(document.getElementById('id_nuovo_proprietario').value=="")
	 	     {
 			   message += "Nessun proprietario trovato con il codice fiscale inserito\r\n";
 	           formTest = false;
	 	     }
	     }
	 	 else
	 	 {
	 		if(document.getElementById('nome_nuovo_proprietario').value=="")
	 		 {
	 			message += "- Nome\r\n";
	 	        formTest = false;	 
	 		 }
	 		 if(document.getElementById('cognome_nuovo_proprietario').value=="")
	 		 {
	 			message += "- Cognome\r\n";
	 	        formTest = false;	 
	 		 }
	 		 if(document.getElementById('data_nascita_nuovo_proprietario').value=="")
	 		 {
	 			message += "- Data nascita\r\n";
	 	        formTest = false;	 
	 		 }
	 		 if(document.getElementById('comune_nascita_nuovo_proprietario').value=="")
	 		 {
	 			message += "- Comune nascita\r\n";
	 	        formTest = false;	 
	 		 }
	 		 if(document.getElementById('codice_fiscale_nuovo_proprietario').value=="")
	 		 {
	 			message += "- Codice fiscale\r\n";
	 	        formTest = false;	 
	 		 }
	 		 if(document.getElementById('addressLegaleCityinput').value=="")
	 		 {
	 			message += "- Comune residenza\r\n";
	 	        formTest = false;	 
	 		 }
	 		 if(document.getElementById('indirizzo_residenza_nuovo_proprietario').value=="")
	 		 {
	 			message += "- Indirizzo Residenza\r\n";
	 	        formTest = false;	 
	 		 }
	 		 if(document.getElementById('email_nuovo_proprietario').value=="")
	 		 {
	 			message += "- Domicilio Digitale\r\n";
	 	        formTest = false;	 
	 		 }
	 	 }
	 	 
	 	 if(document.getElementById('ragione_sociale').value=="")
		 {
			message += "- Ragione sociale\r\n";
	        formTest = false;	 
		 }

	 	if (formTest == false) 
	 	 {
	 	    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	 	    return false;
	 	      
	 	 } 
	 	 else 
	 	 {
	 	    loadModalWindow();
	 	    return true;
	 	 }
	 		
	 }
	
	 function checkFormLaboratorio()
	 {
	 	
	 	
	 	 	formTest = true;
	 	    message = "";
	 	    alertMessage = "";
	 	   
	 	    form = document.laboratorioForm ;
	 	
	 	   if (form.note.value == ""){
	 	        message += "- Note\r\n";
	 	        formTest = false;
	 	     }
	 	 	   
	 	    if (formTest == false) {
	 	        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	 	        return false;
	 	      } else {
	 	      
	 	        loadModalWindow();
	 	        form.submit();
	 	      }
	 		
	 }
	 
	 $( "#dialogUbicazione" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"VARIAZIONE UBICAZIONE APIARIO",
	        width:850,
	        height:500,
	        position: 'top',
	        draggable: false,
	        modal: true,
	        buttons:{
	        	 "Salva": function() { if (checkFormUbicazione()) {$("#addubicazione").submit();} } ,
	        	 "Esci" : function() { $(this).dialog("close");
	             $("html,body").animate({scrollTop: 0}, 500, function(){});
	        	 }
	        	
	        },
	        show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	       
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	 
	 
	 
	 $( "#dialogNuovaMovimentazione" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"NUOVA MOVIMENTAZIONE",
	        width:850,
	        height:500,
	        position: 'top',
	        draggable: false,
	        modal: true,
	        buttons:{
	        	
	        	 "Esci" : function() { $(this).dialog("close");
	        	 $("html,body").animate({scrollTop: 0}, 500, function(){});
	        	 }
	        	
	        },
	        show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	       
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	 
	 
	 
	 
	 $( "#dialogCensimento" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"NUOVO CENSIMENTO",
	        width:850,
	        height:500,
	        position: 'top',
	        draggable: false,
	        modal: true,
	        buttons:{
	        	 "Salva": function() {if (checkFormCensimento()){$("#addcensimento").submit();} } ,
	        	 "Esci" : function() { $(this).dialog("close");
	        	 $("html,body").animate({scrollTop: 0}, 500, function(){});
	        	 }
	        	
	        },
	        show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	       
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	       

	 
	 $( "#dialogModificaTitolare" ).dialog({
			autoOpen: false,
		    resizable: false,
		    closeOnEscape: false,
		   	title:"MODIFICA TITOLARE/VOLTURA",
		    width:850,
		    height:500,
		    position: 'top',
		    draggable: false,
		    modal: true,
		    buttons:{
		    	 "Salva": function() {if (checkFormModificaTitolare()){$("#modificaTitolareForm").submit();} } ,
		    	 "Esci" : function() { attivataModificaTitolare = "false"; $(this).dialog("close");
		    	 $("html,body").animate({scrollTop: 0}, 500, function(){});
		    	 }
		    	
		    },
		    show: {
		        effect: "blind",
		        duration: 1000
		    },
		    hide: {
		        effect: "explode",
		        duration: 1000
		    }
		   
		}).prev(".ui-dialog-titlebar").css("background","#bdcfff");

		
		

$( "#dialogCessazioneApiario" ).dialog({
	autoOpen: false,
    resizable: false,
    closeOnEscape: false,
   	title:"CESSAZIONE APIARIO",
    width:850,
    height:500,
    position: 'top',
    draggable: false,
    modal: true,
    buttons:{
    	 "Salva": function() {if (checkFormCessazioneApiario()){$("#cessazioneApiarioForm").submit();} } ,
    	 "Esci" : function() { $(this).dialog("close");
    	 $("html,body").animate({scrollTop: 0}, 500, function(){});
    	 }
    	
    },
    show: {
        effect: "blind",
        duration: 1000
    },
    hide: {
        effect: "explode",
        duration: 1000
    }
   
}).prev(".ui-dialog-titlebar").css("background","#bdcfff");

});




function cambioDetentore(jsonImpresa)
{
	
	jsonImpresa=jsonImpresa.replaceAll('_','"');
	 var json = JSON.parse(jsonImpresa);
	
	document.getElementById('labelnominativoDetentore').innerHTML=""+json.detentore.nome +" "+json.detentore.cognome ;
	document.getElementById('labelcfDetentore').innerHTML=""+json.detentore.cf;
	
	document.getElementById('idStabilimento').value=json.id;
	
	
	 var json = JSON.parse(jsonImpresa);
	 
	$( '#dialogProprietario' ).dialog('open');
	
}

function movMassivaApiRegine(idOperatore)
{
	document.getElementById('idOperatoreMovMassiva').value=idOperatore;
	$( '#dialogMovMassiva' ).dialog('open')
}




function cercaSoggetto( cf ) 
{
	if(cf=="")
	{
		alert("Valorizzare il codice fiscale");
		document.getElementById('id_nuovo_proprietario').value="";
		document.getElementById('dati_trovati_nuovo_proprietario').innerHTML = innerHtml;
		document.getElementById("usa_questo_proprietario").style.display = "none"; 
		document.getElementById("usa_questo_proprietario_label").style.display = "none"; 
		document.getElementById("usa_questo_proprietario").checked = false;
	}
	else
	{
	  PopolaCombo.verificaSoggetto(cf, cercaSoggettoCallBack);
	}
}

function cercaSoggettoCallBack(value)
{
	if(value.idSoggetto<=0)
	{
		value=null;
	}
	valorizzaDatiNuovoProprietario(value);
	
}

function valorizzaDatiNuovoProprietario(soggetto)
{
	var innerHtml="Nessun soggetto trovato";
	if(soggetto==null)
	{
		document.getElementById('id_nuovo_proprietario').value="";
		document.getElementById('dati_trovati_nuovo_proprietario').innerHTML = innerHtml;
		document.getElementById("usa_questo_proprietario").style.display = "none"; 
		document.getElementById("usa_questo_proprietario_label").style.display = "none"; 
		document.getElementById("usa_questo_proprietario").checked = false;
	}
	else
	{
		document.getElementById('id_nuovo_proprietario').value=soggetto.idSoggetto;

		innerHtml = soggetto.nome + " " + soggetto.cognome;
		innerHtml += "<br/>Sesso: " + soggetto.sesso + ", Data Nascita: " + $.datepicker.formatDate('dd/mm/yy', soggetto.dataNascita) + ", Comune Nascita: " + soggetto.comuneNascita;
		innerHtml += "<br/>Residenza: " + soggetto.indirizzo.via + ", " + soggetto.indirizzo.civico + " - " + soggetto.indirizzo.descrizioneComune + " (" + soggetto.indirizzo.descrizione_provincia + ")";
		document.getElementById('dati_trovati_nuovo_proprietario').innerHTML = innerHtml;
		document.getElementById("usa_questo_proprietario").style.display = ""; 
		document.getElementById("usa_questo_proprietario_label").style.display = ""; 
		
	}
}



function cambioUbicazione(jsonImpresa)
{
	
	jsonImpresa=jsonImpresa.replaceAll('_','"');
	 var json = JSON.parse(jsonImpresa);
	
	
	document.addubicazione.idStabilimento.value=json.id;
	document.addubicazione.indirizzoOld.value=json.idIndirizzo;
	document.getElementById('labelnominativoDetentoreUbi').innerHTML=""+json.detentore.nome +" "+json.detentore.cognome ;
	document.getElementById('labelcfDetentoreUbi').innerHTML=""+json.detentore.cf;
	
	
	 var json = JSON.parse(jsonImpresa);

	$( '#dialogUbicazione' ).dialog('open')
	
}

function nuovocensimento(jsonImpresa)
{
	
	jsonImpresa=jsonImpresa.replaceAll('_','"');
	 var json = JSON.parse(jsonImpresa);
	
	
	document.addcensimento.idStabilimento.value=json.id;
	
		
	
	 var json = JSON.parse(jsonImpresa);
	 
	$( '#dialogCensimento' ).dialog('open')
	
}

function cessazioneApiario(jsonImpresa)
{
	
	jsonImpresa=jsonImpresa.replaceAll('_','"');
	 var json = JSON.parse(jsonImpresa);
	
	 document.getElementById('idStabilimentoCessazioneApiario').value=json.id;
	
	 var json = JSON.parse(jsonImpresa);
	 
	$( '#dialogCessazioneApiario' ).dialog('open')
	
}

function modificaTitolare(jsonImpresa)
{
	attivataModificaTitolare = "true";
	jsonImpresa=jsonImpresa.replaceAll('_','"');
	 var json = JSON.parse(jsonImpresa);
	
	 document.getElementById('idOperatore').value=json.id;
	
	 var json = JSON.parse(jsonImpresa);
	 
	$( '#dialogModificaTitolare' ).dialog('open')
	
}







</script>



  <script>
  checkFirefox('<%=LoginBean.getMessage()%>');
  </script>


<body onload="resizeGlobalItemsPane('hide')">


<% if (msgCancellazione!=null && !msgCancellazione.equals("")){ %>
<script>
alert("<%=msgCancellazione%>");
</script>
<%} %>

<% if (msgAggiornamento!=null && !msgAggiornamento.equals("")){ %>
<script>
alert("<%=msgAggiornamento%>");
</script>
<%} %>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<table class="trails" cellspacing="0">
		<tr>
			<td>ApicolturA </td>
		</tr>
	</table>
</dhv:evaluate>


<%if (OperatoreDettagli.getStato()==StabilimentoAction.API_STATO_DA_NOTIFICARE || OperatoreDettagli.getStato()==StabilimentoAction.API_STATO_INCOMPLETO){ %>
<%
String codiceFiscaleCompare = null;
if(User.getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_DELEGATO)
	codiceFiscaleCompare = User.getSoggetto().getCodFiscale();
else
	codiceFiscaleCompare = User.getContact().getVisibilitaDelega();
if (codiceFiscaleCompare.equalsIgnoreCase(OperatoreDettagli.getCodFiscale())) { %>
<button onclick="javascript:if (confirm('Eliminare la pratica prima che venga validata?')){ location.href='ApicolturaAttivita.do?command=DeleteRichiesta&opId=<%=OperatoreDettagli.getIdOperatore()%>'}">Elimina Richiesta</button>
<%} %>

<%} %>



<div id = "dialogCensimento">

<form name="addcensimento" id="addcensimento" action="ApicolturaApiari.do?command=UpdateCensimento&autopopulate=true" method="POST">

<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">


<input type ="hidden" name = "idStabilimento" value = "" />
		
	<tr>
				<td nowrap class="formLabel">Ragione Sociale </td>
				<td>
				
				<div ><%=OperatoreDettagli.getRagioneSociale() %></div>
				</td>

			</tr>
			
			<tr>
				<td nowrap class="formLabel">Proprietario </td>
				<td>
				
				<div ><%=OperatoreDettagli.getRappLegale()!= null ? OperatoreDettagli.getRappLegale().getNome()+" "+OperatoreDettagli.getRappLegale().getCognome() :"" %></div>
				</td>

			</tr>
			 <tr>
		<td nowrap class="formLabel">Data Ultimo Censimento</td>
		<td>
	
					<%=(String)request.getAttribute("dataCensimentoDaAssegnare")%>
					<input type ="hidden" name="data_assegnazione_censimento" readonly="readonly" value="<%=(String)request.getAttribute("dataCensimentoDaAssegnare")%>" required="required">
					
					<!--  a href="#" onClick="cal19.select(document.forms['addcensimento'].data_assegnazione_censimento,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"-->
				<!--  img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a-->
				
					
			
		</td>
	</tr>
			<tr>
				<td nowrap class="formLabel">Numero Alveari </td>
				<td>
				
				<input type = "text" name = "numAlveari" required="required">
				</td>

			</tr>
			
			<tr>
				<td nowrap class="formLabel">Numero Sciami/Nuclei </td>
				<td>
				
<input type = "text" name = "numSciami" required="required">
				</td>

			</tr>
			<tr>
			<td nowrap class="formLabel">Numero Pacchi d'Api</td>
			
			<td>
			<input type = "text" name = "numPacchi" id = "numPacchi" style="width: 50px;">
			
			</td>
		
		</tr>
		<tr>
			<td nowrap class="formLabel">Numero Api Regine</td>
			
			<td>
			<input type = "text" name = "numRegine" id = "numRegine" style="width: 50px;">
			
			</td>
		
		</tr>
			
			</table>
			
			</form>
			
			</div>
			
			
			
			
			<div id = "dialogCessazioneApiario">



<form name="cessazioneApiarioForm" id="cessazioneApiarioForm" action="ApicolturaApiari.do?command=CessazioneApiario&autopopulate=true" method="POST">

<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">


<input type ="hidden" name = "idStabilimento" id = "idStabilimentoCessazioneApiario" value = "" />
		
	<tr>
				<td nowrap class="formLabel">Ragione Sociale </td>
				<td>
				
				<div ><%=OperatoreDettagli.getRagioneSociale() %></div>
				</td>

			</tr>
			
			<tr>
				<td nowrap class="formLabel">Proprietario </td>
				<td>
				
				<div ><%=OperatoreDettagli.getRappLegale()!= null ? OperatoreDettagli.getRappLegale().getNome()+" "+OperatoreDettagli.getRappLegale().getCognome() :"" %></div>
				</td>

			</tr>
			 <tr>
		<td nowrap class="formLabel">Data Cessazione</td>
		<td>
			<input type ="text" name="data_cessazione_apiario" readonly="readonly" value="" required="required">
			<a href="#" onClick="cal19.select(document.forms['cessazioneApiarioForm'].data_cessazione_apiario,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		</td>
	</tr>
			</table>
			
			</form>
			
			</div>
			
		
	<div id="dialogModificaTitolare">
		<form name="modificaTitolareForm" id="modificaTitolareForm" action="ApicolturaAttivita.do?command=ModificaTitolare&autopopulate=true" method="POST">
			<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
				<input type ="hidden" name="idOperatore" id = "idOperatoreModificaTitolare" value="<%=OperatoreDettagli.getIdOperatore() %>" />
				<input type ="hidden" name="id_nuovo_proprietario" id = "id_nuovo_proprietario" value="" />
				<tr>
					<th colspan="2">
						Dati Attuali
					</th>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						Nominativo Proprietario
					</td>
					<td>
						<div>
							<%=OperatoreDettagli.getRappLegale()!= null ? OperatoreDettagli.getRappLegale().getNome()+" "+OperatoreDettagli.getRappLegale().getCognome() :"" %>
						</div>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						Data e Luogo Nascita Proprietario *
					</td>
					<td>
						<div>
							<%=OperatoreDettagli.getRappLegale()!= null ? OperatoreDettagli.getRappLegale().getDataNascitaString()+" a "+OperatoreDettagli.getRappLegale().getComuneNascita() :"" %>
						</div>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						Codice Fiscale/Partita Iva Proprietario
					</td>
					<td>
						<div>
							<%=OperatoreDettagli.getRappLegale()!= null ? OperatoreDettagli.getRappLegale().getCodFiscale() :"" %>
						</div>
					</td>
				</tr>
				
				<tr>
					<td nowrap class="formLabel">
						Residenza Proprietario *
					</td>
					<td>
						<div>
							<%=OperatoreDettagli.getRappLegale()!= null && OperatoreDettagli.getRappLegale().getIndirizzo()!= null ? OperatoreDettagli.getRappLegale().getIndirizzo().getVia() + ((OperatoreDettagli.getRappLegale().getIndirizzo().getCivico()!=null)?(", " + OperatoreDettagli.getRappLegale().getIndirizzo().getCivico()):("")) + " - " + ComuniList.getSelectedValue(OperatoreDettagli.getRappLegale().getIndirizzo().getComune())  + "(" + OperatoreDettagli.getRappLegale().getIndirizzo().getDescrizione_provincia() + ")":"" %>
						</div>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						Denominazione attività
					</td>
					<td>
						<div>
							<%=OperatoreDettagli.getRagioneSociale()!= null ? OperatoreDettagli.getRagioneSociale() :"" %>
						</div>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						Partita iva attività **
					</td>
					<td>
						<div>
							<%=OperatoreDettagli.getPartitaIva()!= null ? OperatoreDettagli.getPartitaIva() :"" %>
						</div>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						Data Modifica Titolarità
					</td>
					<td>
						<input type ="text" name="data_modifica_titolare" readonly="readonly" value="" required="required">
						<a href="#" onClick="cal19.select(document.forms['modificaTitolareForm'].data_modifica_titolare,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
						<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					</td>
				</tr>
				<tr>
					<th colspan="2">
						Nuovi dati
					</th>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						Nuovo Proprietario
					</td>
					<td>
						<input type="text" name="ricerca_proprietario" id="ricerca_proprietario"/>
						<input type="button" name="Ricerca" value="Ricerca" onclick="cercaSoggetto(document.getElementById('ricerca_proprietario').value)"/>
						<p id="dati_trovati_nuovo_proprietario" name="dati_trovati_nuovo_proprietario" >
						</p>
						<input style="display:none;" type="checkbox" id="usa_questo_proprietario" name="usa_questo_proprietario" value="usa_questo_proprietario">
						<label style="display:none;" id="usa_questo_proprietario_label" for="usa_questo_proprietario">Usa Questo Proprietario</label>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						Nome
					</td>
					<td>
						<input type="text" name="nome" id="nome_nuovo_proprietario" required="required"/>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						Cognome
					</td>
					<td>
						<input type="text" name="cognome" id="cognome_nuovo_proprietario" required="required"/>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						<label for="sesso-2">Sesso *</label>
					</td>
					<td>
						<div class="test">
							<input type="radio" name="sesso" id="sesso1" value="M" checked="checked" class="required css-radio">
							<label for="sesso1" class="css-radiolabel radGroup2">M</label>
						 	<input type="radio" name="sesso" id="sesso2" value="F" class="required css-radio">
						    <label for="sesso2" class="css-radiolabel radGroup2">F</label>
						</div>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						<label for="dataN-2">Data Nascita *</label>
					</td>
					<td>
						<input type="text" size="70" name="dataNascita" readonly="readonly" id="data_nascita_nuovo_proprietario" class="required" placeholder="dd/MM/YYYY">
						<a href="#" onClick="cal19.select(document.getElementById('data_nascita_nuovo_proprietario'),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
						<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						<label for="nazioneN-2">Nazione Nascita *</label>
					</td>
					<td>
						<%NazioniList.setJsEvent("onchange=\"abilitaCodiceFiscale('nazioneNascita')\"") ; %>
						<%=NazioniList.getHtmlSelect("nazione_nascita_nuovo_proprietario", 106) %>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						<label for="comuneNascita-2">Comune Nascita *</label>
					</td>
					<td>
						<input type="text" size="70" name="comuneNascita" id="comune_nascita_nuovo_proprietario" class="required">
					</td>
				</tr>
				<tr>
					<td id="label_cf" class="formLabel" nowrap>
						Codice Fiscale/Partita Iva
					</td>
					<td>
						<input type="text" name="codFiscaleSoggetto" maxlength="16" id="codice_fiscale_nuovo_proprietario" class="required" />  	
						<div id="cfError"></div>
						<input type="button" id="calcoloCF" class="newButtonClass" value="Calcola Cod. Fiscale" onclick="javascript:CalcolaCF(document.modificaTitolareForm.sesso,document.modificaTitolareForm.nome,document.modificaTitolareForm.cognome,document.modificaTitolareForm.comuneNascita,document.modificaTitolareForm.dataNascita,'codice_fiscale_nuovo_proprietario')"></input>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						<label for="nazioneN-2">Nazione Sede Legale *</label>
					</td>
					<td>
						<%NazioniList.setJsEvent("onchange=\"sbloccoProvincia('nazione_residenza_nuovo_proprietario','addressLegaleCountry','addressLegaleCity','indirizzo_residenza_nuovo_proprietario')\"") ; %>
						<%=NazioniList.getHtmlSelect("nazioneResidenza", 106) %>
						<div id="nazioneError"></div>
					</td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>
						Comune Sede Legale *
					</td>
					<td>
						<select name="addressLegaleCity" id="addressLegaleCity" class="required" >
							<option value="">Seleziona Comune</option>
						</select> 
						<input type="hidden" name="addressLegaleCityinput" id="comune_residenza_nuovo_proprietario_testo" />
						<div id="comuneError"></div>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">Indirizzo Sede Legale *</td>
				<td>
					<input type="text" name="addressLegaleLine1Testo" id="indirizzo_residenza_nuovo_proprietario" />
				</td>
				</tr>
				<tr>
    				<td nowrap class="formLabel">
      					Domicilio Digitale<br>(Pec)
    				</td>
    				<td>
      					<input type="text" size="70" name="email" id="email_nuovo_proprietario" >    
    				</td>
  				</tr>
				<tr>
					<td nowrap class="formLabel">
						Denominazione
					</td>
					<td>
						<input type="text" name="ragione_sociale" id="ragione_sociale" required="required" value="<%=OperatoreDettagli.getRagioneSociale()!= null ? OperatoreDettagli.getRagioneSociale() :"" %>"/>
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">
						Partita iva **
					</td>
					<td>
						<input type="text" name="partita_iva" id="partita_iva" required="required" value="<%=OperatoreDettagli.getPartitaIva()!= null ? OperatoreDettagli.getPartitaIva() :"" %>"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" nowrap>
						* = Significativo solo per apicoltori autoconsumo con proprietario soggetto fisico<br/>
						** = Significativo solo per apicoltori commercio
					</td>
				</tr>
			</table>
		</form>
	</div>	
			
<div id = "dialogUbicazione">
<form name="addubicazione" id="addubicazione" action="ApicolturaApiari.do?command=UpdateUbicazione&autopopulate=true" method="POST">


<font color="red">ATTENZIONE! Il mittente di ogni movimentazione specificata nel file deve essere uguale all'azienda per la quale si sta lavorando: <b><%=OperatoreDettagli.getCodiceAzienda()%></b></font>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">


<input type ="hidden" name = "idStabilimento" value = "" />
<input type ="hidden" name = "indirizzoOld" value = "" />
		
		
	<tr>
				<td nowrap class="formLabel">Ragione Sociale </td>
				<td>
				
				<div ><%=OperatoreDettagli.getRagioneSociale() %></div>
				</td>

			</tr>
			<tr>
				<td nowrap class="formLabel">Proprietario </td>
				<td>
				
				<div ><%= OperatoreDettagli.getRappLegale()!=null ? OperatoreDettagli.getRappLegale().getNome()+" "+OperatoreDettagli.getRappLegale().getCognome() :""%></div>
				</td>

			</tr>
			<tr>
				<td nowrap class="formLabel">Codice Fiscale Proprietario</td>
				<td>
				
				<div ><%= OperatoreDettagli.getRappLegale()!=null ?  OperatoreDettagli.getRappLegale().getCodFiscale() :"" %></div>
				</td>

			</tr>
			<tr>
				<td nowrap class="formLabel">Detentore </td>
				<td>
				
				<div id="labelnominativoDetentoreUbi"></div>
				</td>

			</tr>
			<tr>
				<td nowrap class="formLabel">Codice Fiscale Detentore</td>
				<td>
				
				<div id="labelcfDetentoreUbi"></div>
				</td>

			</tr>
			
			 <tr>
		<td nowrap class="formLabel">Data Spostamento Ubicazione</td>
		<td>
			
			
					<input type ="text" name="data_assegnazione_ubicazione" readonly="readonly" value="" required="required">
						<a href="#" onClick="cal19.select(document.forms['addubicazione'].data_assegnazione_ubicazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					
			
		</td>
	</tr>
		
							
   <tr>
		<td nowrap class="formLabel">Comune</td>
		<td>
			<select name="searchcodeIdComune" id="searchcodeIdComune" class="required">
				<option value=""></option>
			</select>
			
			<input type="hidden" name="searchcodeIdComuneTesto" id="searchcodeIdComuneTesto" />
			
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			Cap
		</td>
		<td>
			<input type="text" size="70" name="presso"  style="width: 50px;" required="required">
		</td>
	</tr>
	
	<tr id ="searchcodeIdprovinciaTR">

		<td nowrap class="formLabel">
			Provincia
		</td>
		<td>
			
			<input type="text" readonly="readonly" required="required" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" />
			<input type="hidden"  required="required" name="searchcodeIdprovincia" id="searchcodeIdprovincia" />
			
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			Indirizzo
		</td>
		<td>
			
			<input type="text" name="viaTesto" id="viaTesto" required="required" />
		</td>
	</tr>
	
	
	
	
	<tr>
		<td nowrap class="formLabel">
			Latitudine
		</td>
		<td>
			
			<input type="text" name="latitudine" id="latitudine" pattern="-?\d{1,3}\.\d+" required="required" />
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel">
			Longitudine
		</td>
		<td>
			
			<input type="text" name="longitudine" id="longitudine"  pattern="-?\d{1,3}\.\d+" required="required" />
		</td>
	</tr>
	
	 <tr style="display:block">
    <td colspan="2">
    <input id="coord2button" type="button" value="Calcola Coordinate" 
    onclick="javascript:showCoordinate(document.getElementById('viaTesto').value, document.forms['addubicazione'].searchcodeIdComuneinput.value,document.forms['addubicazione'].searchcodeIdprovinciaTesto.value, document.forms['addubicazione'].presso.value, document.forms['addubicazione'].latitudine, document.forms['addubicazione'].longitudine);" />
     </td>
    </tr>
	
	
		
		</table>
</form>
</div>
<div id = "dialogUbicazioneEnd"></div>



<div id = "dialogCessazione">
<form name="formcessazione" id="formcessazione" action="ApicolturaAttivita.do?command=CessaAttivita" method="POST">

<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details" id ="">
	
		
		<input type ="hidden" name = "idOperatore" id = "idOperatore" value="<%=OperatoreDettagli.getIdOperatore()%>"/>
		<tr>
				<td nowrap class="formLabel"><label for="dataN-2">Data Cessazione</label></td>
				<td><input type ="text" name="dataCessazione" id="dataCessazione" value="" readonly="readonly">
						<a href="#" onClick="cal19.select(document.forms['formcessazione'].dataCessazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
					</td>
			</tr>
		</table>	
			
</form>
</div>

<div id = "dialogProprietario">
<form name="addpersona" id="addpersona" action="ApicolturaApiari.do?command=UpdateSoggettoFisicoStabilimento&autopopulate=true" method="POST">
<div id="messaggioErrore"></div>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details" id ="">
	
				<input type ="hidden" value = "si" name = "inReg" id = "inReg"/>
		
		<input type ="hidden" name = "idStabilimento" id = "idStabilimento"/>
		
		<tr>
				<td nowrap class="formLabel"><label for="dataN-2">Data Assegnazione Detentore</label></td>
				<td><input type ="text" name="data_assegnazione_detentore" id="data_assegnazione_detentore" value="" readonly="readonly">
						<a href="#" onClick="cal19.select(document.forms['addpersona'].data_assegnazione_detentore,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
					</td>
			</tr>
		
		<tr>
				<td nowrap class="formLabel">Ragione Sociale </td>
				<td>
				
				<div id="labelRagioneSociale"><%=OperatoreDettagli.getRagioneSociale() %></div>
				</td>

			</tr>
			<tr>
				<td nowrap class="formLabel">Proprietario </td>
				<td>
				
				<div id="labelnominativoProprietario"><%=OperatoreDettagli.getRappLegale() !=null ?  OperatoreDettagli.getRappLegale().getNome()+" "+OperatoreDettagli.getRappLegale().getCognome() :""%></div>
				</td>

			</tr>
			<tr>
				<td nowrap class="formLabel">Codice Fiscale Proprietario</td>
				<td>
				
				<div id="labelcfProprietario"><%=OperatoreDettagli.getRappLegale()!=null ?   OperatoreDettagli.getRappLegale().getCodFiscale() :"" %></div>
				</td>

			</tr>
			<tr>
				<td nowrap class="formLabel">Detentore </td>
				<td>
				
				<div id="labelnominativoDetentore"></div>
				</td>

			</tr>
			<tr>
				<td nowrap class="formLabel">Codice Fiscale Attuale Detentore</td>
				<td>
				
				<div id="labelcfDetentore"></div>
				</td>

			</tr>
			
				<tr>
				<td class="formLabel" nowrap>Codice Fiscale Nuovo Detentore</td>
				<td><input type="text" name="codFiscaleSoggetto"  placeholder="RICERCA DIGITANDO ILCODICE FISCALE"
					id="codFiscaleSoggettoAdd" class="required" /> 
					<font color="red">*</font>
						<%=showError(request, "cfSoggettoFisicoError") %>
						
						<input type="button" id="calcoloCF" class="newButtonClass" style="display: none"
					value="Calcola Cod. Fiscale"
					onclick="javascript:CalcolaCF(document.addpersona.sesso,document.getElementById('nomedetentore'),document.getElementById('cognomedetentore'),document.addpersona.comuneNascita,document.addpersona.dataNascita,'codFiscaleSoggettoAddinput')"></input>
								
				</td>
				
			</tr>
			
			<tr id ="nominativoDetNome">
				<td nowrap class="formLabel">Nome</td>
				<td><input type="text" size="70" id="nomeDet" name="nome" class="required">
				<input type = "hidden" name = "idSoggettoFisico" id="idSoggettoFisico" value = "" >

				<font color="red">*</font>
				</td>

			</tr>
			
			<tr id ="nominativoDetCognome">
				<td nowrap class="formLabel">Cognome</td>
				<td><input type="text" size="70" id="cognomeDet" name="cognome" class="required">
				<font color="red">*</font>
				</td>

			</tr>
			
			</table>
			
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details" id ="detentore" style="display: none">			
		
		<tr>
   		<th colspan="2">
      		<strong>ANAGRAFA NUOVO DETENTORE</strong>
    	</th>
  		</tr>
  
		<tr>
				<td nowrap class="formLabel">Nome </td>
				<td><input type="text" size="70" id="nomedetentore" name="nome" class="required">
					<div id="nomeError"></div>
				</td>

			</tr>

			<tr >
				<td nowrap class="formLabel"><label for="cognome-2">Cognome </label></td>
				<td><input type="text" size="70" id="cognomedetentore" name="cognome"
					class="required">
						<div id="cognomeError"></div>
					</td>
			</tr>

			<tr >
				<td nowrap class="formLabel"><label for="sesso-2">Sesso </label></td>
				<td><div class="test">
						<input type="radio" name="sesso" id="sesso1" value="M"
							checked="checked" class="required css-radio">
							<label for="sesso1" class="css-radiolabel radGroup2">M</label>
						 <input type="radio"
							name="sesso" id="sesso2" value="F" class="required css-radio">
						<label for="sesso2" class="css-radiolabel radGroup2">F</label>
					</div></td>
			</tr >

			<tr >
				<td nowrap class="formLabel"><label for="dataN-2">Data Nascita </label></td>
				<td><input type="text" size="70" name="dataNascita" readonly="readonly"
					id="dataNascita" class="required" placeholder="dd/MM/YYYY">
						<a href="#" onClick="cal19.select(document.forms['addpersona'].dataNascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
					</td>
			</tr>

			<tr >
				<td nowrap class="formLabel"><label for="nazioneN-2">Nazione Nascita </label></td>
				<td>
				<%NazioniList.setJsEvent("onchange=\"abilitaCodiceFiscale('nazioneNascita')\"") ; %>
				<%=NazioniList.getHtmlSelect("nazioneNascita", 106) %></td>
			</tr>


			<tr >
				<td nowrap class="formLabel"><label for="comuneNascita-2">Comune Nascita </label></td>
				<td><input type="text" size="70" name="comuneNascita"
					id="comuneNascita" class="required">
					
					</td>
			</tr>

			
	



			<tr >
				<td nowrap class="formLabel"><label for="nazioneN-2">Nazione Residenza </label></td>
				<td>
				<%NazioniList.setJsEvent("onchange=\"sbloccoProvincia('nazioneResidenza','addressLegaleCountry','addressLegaleCity','addressLegaleLine1')\"") ; %>
				<%=NazioniList.getHtmlSelect("nazioneResidenza", 106) %>
				<div id="nazioneError"></div>
				</td>
			</tr>
			
			

			<tr >
				<td class="formLabel" nowrap>Comune Residenza</td>
				<td><select name="addressLegaleCity" id="addressLegaleCity" class="required" >
						<option value="">Seleziona Comune</option>
				</select> <input type="hidden" name="addressLegaleCityTesto"
					id="addressLegaleCityTesto" />
					<div id="comuneError"></div>
					</td>
			</tr>
			
			<tr  id ="addressLegaleCountryTR">
				<td class="formLabel" nowrap>Provincia Residenza</td>
				<td><input type= "hidden" id="addressLegaleCountry" class="required"
					name="addressLegaleCountry">
									<input type="text" id="addressLegaleCountryTesto" readonly="readonly"
					name="addressLegaleCountryTesto" /></td>
			</tr>


		<tr >
		<td nowrap class="formLabel">Indirizzo Residenza</td>
		<td>
			
			
			<input type="text" name="addressLegaleLine1Testo" id="addressLegaleLine1Testo" />
			<div id="indirizzoError"></div>
				
		</td>
	</tr>
	
	<tr >
    		<td nowrap class="formLabel">
      			Domicilio Digitale<br>(Pec)
    		</td>
    		<td>
      			<input type="text" size="70" name="domicilioDigitale" >    
    		</td>
  		</tr>
</table>
</form>
</div>




<div id = "dialogMovMassiva">
	<form  name=movmassivaapiregineForm id="movmassivaapiregineForm" action="ApicolturaMovimentazioni.do?command=MassivaApiRegine&autopopulate=true" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="alt_id" id="alt_id" value="<%=OperatoreDettagli.getIdOperatore()%>">
		<div id="messaggioErrore"></div>
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" id ="">
			<input type ="hidden" name = "idOperatoreMovMassiva" id = "idOperatoreMovMassiva"/>
			<tr>
				<td nowrap class="formLabel" colspan="4">
					<input type="file" name="file1" id="file1" >
				</td>
			</tr>
			<tr>
				<td nowrap class="formLabel" colspan="4">
					<input type="button" value="file d'esempio" onclick="apriTemplate()"/>	
				</td>
			</tr>
		</table>
	</form>
</div>







	<div id = "dialogLaboratorio">



<form name="laboratorioForm" id="laboratorioForm" action="ApicolturaAttivita.do?command=UpdateLaboratorio&autopopulate=true" method="POST">

<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">


<input type ="hidden" name = "idOperatore" id = "idOperatore" value = "<%=OperatoreDettagli.getIdOperatore() %>" />
		
	    	<tr>
				<td nowrap class="formLabel">Presenza laboratorio</td>
				<td><input type="checkbox" id="laboratorio" name ="laboratorio" <%= OperatoreDettagli.isFlagProduzioneConLaboratorio() ? "checked=\"checked\"" : ""%>/>
				</td>
			</tr>
	
			
			<tr>
				<td nowrap class="formLabel">Note</td>
				<td><textarea cols="20" rows="3" name="note" id="note"></textarea>
				</td>
			</tr>
			
			</table>
			
			</form>
			
			</div>
			

<div align="left">
<%
if(request.getAttribute("mov_in_ingresso")!=null && ((Boolean)request.getAttribute("mov_in_ingresso")))
{
%>
<font size="2">
<div id="lampeggio">
	Sono presenti movimentazioni in ingresso da accettare
</div>
</font>
<%
	
}
%>

<dhv:anagraficapermissionRole codiceFiscale="<%=OperatoreDettagli.getCodFiscale() %>" name="apicoltura-listamovimentazioni-view">
	<input type="button" value="Lista Movimentazioni" onclick="javascript:location.href='ApicolturaMovimentazioni.do?command=SearchTutte&opId=<%=OperatoreDettagli.getIdOperatore()%>&searchcodeidAzienda=<%=OperatoreDettagli.getIdOperatore()%>&searchcodeCodiceAziendaSearch=<%=OperatoreDettagli.getCodiceAzienda()%>'" />
</dhv:anagraficapermissionRole>

<dhv:anagrficapermission codiceFiscale="<%=OperatoreDettagli.getCodFiscale() %>">
	<input type="button" value="Movimentazione massiva api regine" onclick="javascript:movMassivaApiRegine('<%=OperatoreDettagli.getIdOperatore()%>')" />
	<input type="button" value="Compravendita api regine" onclick="javascript:location.href='ApicolturaMovimentazioni.do?command=AddRichiesta&tipoMovimentazione=ApiRegine&idOperatore=<%=OperatoreDettagli.getIdOperatore()%>'" />
	<input type="button" value="Storico Upload File Api Regine" onclick="javascript:location.href='ApicolturaMovimentazioni.do?command=StoricoUploadFileApiRegine&idOperatore=<%=OperatoreDettagli.getIdOperatore()%>'" />
	<br/>
	<input type="button" value="Movimentazioni in ingresso" onclick="javascript:location.href='ApicolturaMovimentazioni.do?command=SearchIngresso&opId=<%=OperatoreDettagli.getIdOperatore()%>&searchcodeidAzienda=<%=OperatoreDettagli.getIdOperatore()%>&searchcodeCodiceAziendaSearch=<%=OperatoreDettagli.getCodiceAzienda()%>'" />
	
</dhv:anagrficapermission>


<%
if(OperatoreDettagli.getStato()!=StabilimentoAction.API_STATO_CESSATO && OperatoreDettagli.getStato()!=StabilimentoAction.API_STATO_CESSATO_DA_SINCRONIZZARE && OperatoreDettagli.getStato()!=StabilimentoAction.API_STATO_CESSATO_SINCRONIZZATO)
{
%>
<dhv:anagrficapermission codiceFiscale="<%=OperatoreDettagli.getCodFiscale() %>">
<input type = "button" value = "CESSAZIONE ATTIVITA" onclick="showdialogCessazione()">
</dhv:anagrficapermission>


<%
}
%>
<dhv:permission name="apicoltura-cessazioneattivia_hd-view">
<input type = "button" value = "CESSAZIONE ATTIVITA HD" onclick="showdialogCessazione()">
</dhv:permission>
</div>	

<div align="right">
	<input style="background-color: green;" type="button" value="REPORT CENSIMENTI APICOLTORE" onclick="javascript:window.open('GenerazioneExcel.do?command=GetExcel&tipo_richiesta=censimentiApicoltore&idApicoltore=<%=OperatoreDettagli.getIdOperatore() %>');" />
</div>

<%
	if(!OperatoreDettagli.getListaStabilimenti().isEmpty())
	{
%>
		<div align="right">
			<input style="background-color: green;" type="button" value="Stampa Scheda" onClick="openRichiestaPDFOpu('<%=((Stabilimento)OperatoreDettagli.getListaStabilimenti().get(0)).getIdStabilimento()%>', '-1', '-1', '-1', '50');" >
		</div>
<%
	}
%>

<div id ="start"></div>
<%=showError(request, "Error") %>
<%
	if(request.getAttribute("esitiErroriParsingFile") != null && request.getAttribute("esitiInsert") != null)
	{
%>
		<br><font style="font-weight: bold;">Log file:</font>	
	    <%= ( (StringBuffer)request.getAttribute("esitiErroriParsingFile") ).toString() %>  
			   		 
			   		 <br><font style="font-weight: bold;">Log inserimenti:</font>
			   		 <%= ( (StringBuffer)request.getAttribute("esitiInsert") ).toString() %>
			   <%} %>
<fieldset>
		<legend><b>SCHEDA</b></legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		 <tr>
    <th colspan="2">
      <strong>ATTIVITA DI APICOLTURA</strong>
    </th>
  </tr>


<tr>
		<td nowrap class="formLabel">Stato Sincronizzazione</td>
		<td>
			<%=OperatoreDettagli.isSincronizzatoBdn()==true ? "<img src=\"images/verde.gif\">" : "<img src=\"images/rosso.gif\">" %>
			
		</td>
	</tr>
	
	
	<tr>
		<td nowrap class="formLabel">Partita Iva</td>
		<td>
			<%=toHtml(OperatoreDettagli.getPartitaIva())%>
			
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">Asl di Competenza</td>
		<td>
			<%=AslList.getSelectedValue(OperatoreDettagli.getIdAsl()) %>
			
		</td>
	</tr>
	
		<tr>
		<td nowrap class="formLabel">Comune Sede Legale</td>
		<td>
			<%=OperatoreDettagli.getSedeLegale()!= null ? OperatoreDettagli.getSedeLegale().getDescrizioneComune() :"" %>
			
		</td>
	</tr>
	
	
	
	<tr>
		<td nowrap class="formLabel">
			Cap
		</td>
		<td>
			<%=OperatoreDettagli.getSedeLegale() != null ? OperatoreDettagli.getSedeLegale().getCap():"" %>
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel">
			STATO
		</td>
						<td><%=LookupStati.getSelectedValue(OperatoreDettagli.getStato()) %>
						<%=OperatoreDettagli.getDataChiusura()!=null ? toDateasString(OperatoreDettagli.getDataChiusura()):"" %>

	</tr>
	
	
	
	
	<tr id ="searchcodeIdprovinciaTR">

		<td nowrap class="formLabel">
		Provincia
		</td>
		<td>
			
			<%=OperatoreDettagli.getSedeLegale() != null ? OperatoreDettagli.getSedeLegale().getDescrizione_provincia() :""%>
		</td>
	</tr>
	
		
		<tr>
			<td nowrap class="formLabel">Denominazione</td>
			<td><%=OperatoreDettagli.getRagioneSociale() %></td>
		</tr>
		
		
		
		<%if (OperatoreDettagli.getCodiceAzienda()!=null && !"".equals(OperatoreDettagli.getCodiceAzienda())) {%>
			<tr>
			<td nowrap class="formLabel">Codice Azienda</td>
			<td><%=toHtml2(OperatoreDettagli.getCodiceAzienda()) %></td>
		</tr>
		<%} %>
		
			
			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Proprietario</td>
				<td>

						<%=OperatoreDettagli.getRappLegale()!=null ?   OperatoreDettagli.getRappLegale().getCodFiscale() : ""%>
					
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Proprietario (cognome e nome) </td>
				<td>
					<%=OperatoreDettagli.getRappLegale()!=null ?   OperatoreDettagli.getRappLegale().getCognome()+" "+OperatoreDettagli.getRappLegale().getNome():""%>
					<dhv:permission name="apicoltura-modificatitolare-edit">
						<button onclick="javascript: modificaTitolare('<%=OperatoreDettagli.getIdOperatore()%>')">MODIFICA TITOLARE/VOLTURA</button>
					</dhv:permission>
				</td>

			</tr>
			
			
			<tr>
				<td class="formLabel" nowrap>Data Inizio Attivita</td>
				<td>
					<%=toDateasString(OperatoreDettagli.getDataInizio())%>
				
					
				</td>
			</tr>
			
			
	<tr>
		<td nowrap class="formLabel">
			Indirizzo Sede Legale
		</td>
		<td>
			
			<%=OperatoreDettagli.getSedeLegale()!=null ? (OperatoreDettagli.getSedeLegale().getVia()+" "+ toHtml(OperatoreDettagli.getSedeLegale().getCivico())) :"" %>
			
		</td>
	</tr>
	
	
	
	<tr>
		<td nowrap class="formLabel">
			Tipo Attivita
		</td>
		<td>
			<%=TipoAttivitaApi.getSelectedValue(OperatoreDettagli.getIdTipoAttivita())%>
			 <input type = "checkbox" disabled="disabled" <%=(OperatoreDettagli.isFlagProduzioneConLaboratorio()?"checked" :"") %>> Presenza Laboratorio di smielatura
			 
			 <dhv:permission name="apicoltura-modifica-laboratorio-view">
			 <input type = "button" value = "GESTIONE PRESENZA LABORATORIO" onclick="showdialogLaboratorio()">
			 </dhv:permission>
			 
		</td>
	</tr>
	
	<tr>
    		<td nowrap class="formLabel">
      			Domicilio Digitale<br>(Pec)
    		</td>
    		<td>
      			<%=toHtml2( OperatoreDettagli.getDomicilioDigitale()) %>
    		</td>
  		</tr>
  		
  		<tr>
    		<td nowrap class="formLabel">
      			Telefono Fisso
    		</td>
    		<td>
      				<%=toHtml2(OperatoreDettagli.getTelefono1()) %> 
    		</td>
  		</tr>
  		
  		<tr>
    		<td nowrap class="formLabel">
      			Telefono Cellulare
    		</td>
    		<td>
      		<%=toHtml2(OperatoreDettagli.getTelefono2()) %>  
    		</td>
  		</tr>
  		
  		<tr>
    		<td nowrap class="formLabel">
      			Fax
    		</td>
    		<td>
      			<%=toHtml2(OperatoreDettagli.getFax()) %>  
    		</td>
  		</tr>

	</table>
	
	</fieldset>


<br/>

<%
if (OperatoreDettagli.getListaStabilimenti().getLaboratorio()!=null)
{
%>
<fieldset>
<legend>Laboratorio di Smielatura</legend>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		 <tr>
    <th colspan="2">
      <strong>UBICAZIONE LABORATORIO</strong>
    </th>
  </tr>


<tr>
		<td nowrap class="formLabel">COMUNE</td>
			<td>
			<%=OperatoreDettagli.getListaStabilimenti().getLaboratorio().getSedeOperativa().getDescrizioneComune()%></td>
			
	</tr>	
	</tr>	
	
	<tr>
		<td nowrap class="formLabel">provincia</td>
		<td>
			<%=OperatoreDettagli.getListaStabilimenti().getLaboratorio().getSedeOperativa().getDescrizione_provincia()%></td>
			
	</tr>	
	
	<tr>
		<td nowrap class="formLabel">cap</td>
		<td>
			<%=OperatoreDettagli.getListaStabilimenti().getLaboratorio().getSedeOperativa().getCap() %>
			
		</td>
	</tr>	
	
	<tr>
		<td nowrap class="formLabel">indirizzo</td>
	<td>
<%
		String via =    OperatoreDettagli.getListaStabilimenti().getLaboratorio().getSedeOperativa().getVia();
		String civico = ", " + OperatoreDettagli.getListaStabilimenti().getLaboratorio().getSedeOperativa().getCivico();
		if(civico==null)
			civico = "";
		out.println(via + civico);
		
%>  
	</td>
	</tr>	
	
	
	</table>
	</fieldset>
<br>
<%} %>


<fieldset>
<legend>

<%
if (OperatoreDettagli.getStato()!=StabilimentoAction.API_STATO_CESSATO)
{ 
	if (User.getRoleId()==Role.RUOLO_APICOLTORE && User.getContact().getVisibilitaDelega().equalsIgnoreCase(OperatoreDettagli.getCodFiscale())) 
	{ 
%>

	<button onclick="location.href='ApicolturaApiari.do?command=Add&opId=<%=OperatoreDettagli.getIdOperatore() %>'" >Nuovo Apiario</button>
<%
	if(OperatoreDettagli.getListaStabilimenti().getLaboratorio()==null && OperatoreDettagli.isFlagProduzioneConLaboratorio()==true && OperatoreDettagli.getIdTipoAttivita()==1)
	{
%>
	<button onclick="location.href='ApicolturaApiari.do?command=Add&opId=<%=OperatoreDettagli.getIdOperatore() %>&flagLaboratio=true'" >AGGIUNGI LABORATORIO</button>
	
<%
	}
%>

<br><br>
<%
} 
else
{
%>
	<dhv:anagrficapermission codiceFiscale="<%=OperatoreDettagli.getCodFiscale() %>">
		<button onclick="location.href='ApicolturaApiari.do?command=Add&opId=<%=OperatoreDettagli.getIdOperatore() %>'" >Nuovo Apiario</button>
<%
		if(OperatoreDettagli.getListaStabilimenti().getLaboratorio()==null && OperatoreDettagli.isFlagProduzioneConLaboratorio()==true && OperatoreDettagli.getIdTipoAttivita()==1)
		{
%>
			<button onclick="location.href='ApicolturaApiari.do?command=Add&opId=<%=OperatoreDettagli.getIdOperatore() %>&flagLaboratio=true'" >AGGIUNGI LABORATORIO</button>
<%
		}
%>
		<br><br>
	</dhv:anagrficapermission>

<%	
}
%>
<%} %>
</legend>




<div class="pager">
	Page: <select class="gotoPage"></select>		
	<img src="javascript/img/first.png" class="first" alt="First" title="First page" />
	<img src="javascript/img/prev.png" class="prev" alt="Prev" title="Previous page" />
	<img src="javascript/img//next.png" class="next" alt="Next" title="Next page" />
	<img src="javascript/img/last.png" class="last" alt="Last" title= "Last page" />
	<select class="pagesize">
		<option value="10">10</option>
		<option value="20">20</option>
		<option value="30">30</option>
		<option value="40">40</option>
	</select>
</div>




<table  class="tablesorter">

	<thead>
		<tr class="tablesorter-headerRow" role="row">

			<th aria-label="PROGRESSIVO No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" class="filter-false tablesorter-header" ><div class="tablesorter-header-inner">#</div></th>
			<th aria-label="DETENTORE: No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER DETENTORE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">DETENTORE</div></th>
			
			<th aria-label="CF DETENTORE ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER CF DETENTORE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">CF DETENTORE</th>
			<th aria-label="CLASSIFICAZIONE ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER CLASSIFICAZIONE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">CLASSIFICAZIONE</th>
			<th aria-label="SOTTOSPECIE ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER SOTTOSPECIE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">SOTTOSPECIE</th>
			<th aria-label="MODALITA ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER MODALITA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">MODALITA</th>
			
			<th aria-label="NUM ALVEARI ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" CLASS="filter-false tablesorter-header"><div class="tablesorter-header-inner">NUM ALVEARI</th>
			<th aria-label="NUM SCIAMI No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" class="filter-false tablesorter-header" ><div class="tablesorter-header-inner">NUM SCIAMI / NUCLEI</div></th>
			
			<th aria-label="DATA INIZIO: No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">DATA INIZIO</div></th>
			<th aria-label="DATA CESSAZIONE No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">DATA CESSAZIONE</div></th>
			<th aria-label="COMUNE No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER COMUNE UBICAZIONE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">UBICAZIONE</div></th>
			
						<th aria-label="INDIRIZZO No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">STATO</div></th>
			<th aria-label="INDIRIZZO No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">STATO BDN</div></th>
			
			<th aria-label="INDIRIZZO No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">&nbsp;</div></th>
			
			
			
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
	
	<%
		Iterator<Stabilimento> itStab = OperatoreDettagli.getListaStabilimenti().iterator();
		while (itStab.hasNext())
		{
			Stabilimento thisStab = itStab.next();
			String suffissoLabelTastoCensimenti =   (String)request.getAttribute("suffissoLabelTastoCensimenti");
			if (thisStab.isFlagLaboratorio()==false)
			{
			%>
			<tr>
			<td><%=thisStab.getProgressivoBDA()%></td>
			<td><a href="ApicolturaApiari.do?command=Details&stabId=<%=thisStab.getIdStabilimento()%>"><%=thisStab.getDetentore()!= null ? toHtml2(thisStab.getDetentore().getCognome() ) + " "+toHtml2(thisStab.getDetentore().getNome()):"" %></a></td>
			<td><%=thisStab.getDetentore()!= null ?  toHtml2(thisStab.getDetentore().getCodFiscale()):""%></td>
			<td><%=toHtml2(ApicolturaClassificazione.getSelectedValue(thisStab.getIdApicolturaClassificazione())) %></td>
			<td><%=toHtml2(ApicolturaSottospecie.getSelectedValue(thisStab.getIdApicolturaSottospecie())) %></td>
			<td><%=toHtml2(ApicolturaModalita.getSelectedValue(thisStab.getIdApicolturaModalita())) %></td>
			<td><%=thisStab.getNumAlveari() %></td>
			<td><%=thisStab.getNumSciami() %></td>
			
			<td><%=toDateasString(thisStab.getDataApertura())%></td>
			<td><%=toDateasString(thisStab.getDataCessazione()) %></td>
			
			<td><%=thisStab.getSedeOperativa()!= null ? thisStab.getSedeOperativa().getDescrizioneComune() +" - "+ thisStab.getSedeOperativa().getVia() :""%></td>
			<td><%=LookupStati.getSelectedValue(thisStab.getStato()) %>
			</td>
			
			
		
		<td>
<%=thisStab.isSincronizzatoBdn()==true ? "<img src=\"images/verde.gif\">" : "<img src=\"images/rosso.gif\">" %>			
		</td>
	
			
			<td>
			<%if(thisStab.getStato() != StabilimentoAction.API_STATO_CESSATO && thisStab.getStato() != StabilimentoAction.API_STATO_CESSATO_SINCRONIZZATO){	%>
				
				<dhv:anagrficapermission codiceFiscale="<%=OperatoreDettagli.getCodFiscale() %>">
					<button onclick="javascript: cessazioneApiario('<%=thisStab.toString().replaceAll("'", "")%>')">CESSAZIONE APIARIO</button>
				</dhv:anagrficapermission>
			<% } %>
			
			<%if(thisStab.getStato()==StabilimentoAction.API_STATO_VALIDATO && thisStab.getStato()!=StabilimentoAction.API_STATO_VENDUTO) 
			{ 
			
			String disabled = "disabled='disabled'" ;
			String title = "title=\"Censimento Attivo dal "+dataFrom + " Al "+dataTo + "\"";
			
			if(inRangeTolleranzaAnnoAttuale || inRangeTolleranzaAnnoPrecedente)
			{
				disabled="" ;
				title = "" ;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if(inRangeTolleranzaAnnoPrecedente && !inRangeAnnoPrecedente && thisStab.getDataApertura().compareTo(dataInizioAnno)>=0)
			{
				disabled = "disabled='disabled'" ;
				suffissoLabelTastoCensimenti = "";
				title = "title=\"Censimento Attivo dal "+dataFrom + " Al "+dataTo + "\"";
			}
			
			
			if(OperatoreDettagli.getIdAsl()!=16 && (OperatoreDettagli.getCodFiscale().equals(User.getUserRecord().getContact().getCodiceFiscale()) || OperatoreDettagli.getCodFiscale().equals(User.getSoggetto().getCodFiscale()) || OperatoreDettagli.getCodFiscale().equalsIgnoreCase(User.getContact().getVisibilitaDelega().trim()) || User.getUserRecord().getRoleId()==270)) 
			{

			int anno = -1;
			int mese = -1;
			if(thisStab.getData_assegnazione_censimento()!=null)
			{
				anno = thisStab.getData_assegnazione_censimento().getYear()+1900;
				mese = thisStab.getData_assegnazione_censimento().getMonth()+1;
			}

			System.out.println("anno: " + anno + ", annoOdierno: " + annoOdierno + ", mese: " + mese);

			if( anno==-1 || anno!=annoOdierno || (anno==annoOdierno && mese<11) )
			{
%>
				<!-- Abilitare il controllo sul permeso SOLO se si vuole dare l'inserimento dei censimenti SOLO all'Help Desk -->
				<!--  dhv:permission name="apicoltura-censimenti-view"-->
				<button <%=disabled %>  <%=title %> onclick="javascript: nuovocensimento('<%=thisStab.toString().replaceAll("'"," ").replaceAll("\""," ")%>')">CENSIMENTI<%=suffissoLabelTastoCensimenti%></button>
				<!-- /dhv:permission -->
<%
			}
%>
			<button onclick="javascript: cambioDetentore('<%=thisStab.toString()%>')">Cambio Detentore</button><br>
			<button id = "compravenditaAdd" onclick="javascript:location.href='ApicolturaMovimentazioni.do?command=AddRichiesta&tipoMovimentazione=Compravendita&idApiarioScelto=<%=thisStab.getIdStabilimento()%>'">COMPRAVENDITA</button>
			<button id = "nomadismoAdd" onclick="javascript:location.href='ApicolturaMovimentazioni.do?command=AddRichiesta&tipoMovimentazione=Nomadismo&idApiarioScelto=<%=thisStab.getIdStabilimento()%>'">NOMADISMO</button>
			<button id = "impollinazioneAdd" onclick="javascript:location.href='ApicolturaMovimentazioni.do?command=AddRichiesta&tipoMovimentazione=Impollinazione&idApiarioScelto=<%=thisStab.getIdStabilimento()%>'">IMPOLLINAZIONE</button>
			
			<%} %>
		<%}else
		{
			if(thisStab.isSincronizzatoBdn()==false){	
				%>			
				<dhv:anagrficapermission codiceFiscale="<%=OperatoreDettagli.getCodFiscale() %>">
					<button onclick="if(confirm('Sei sicuro di voler elminare questo apiario ?')) {location.href='ApicolturaApiari.do?command=Delete&stabId=<%=thisStab.getIdStabilimento()%>' ;}" >Elimina Apiario</button>
				</dhv:anagrficapermission>
				<%
			}
		}
			
			%>
			
			<dhv:permission name="apicoltura-delete">
			<button onclick="if(confirm('Sei sicuro di voler elminare questo apiario ?')) {location.href='ApicolturaApiari.do?command=Delete&stabId=<%=thisStab.getIdStabilimento()%>' ;}" >Elimina Apiario per HD</button>
			</dhv:permission>
			
			
			<br>
			
			</td>
			
			</tr>
			<%}
			
		}
	%>
	
	</tbody>
	</table>
	
	<div class="pager">
	Page: <select class="gotoPage"></select>		
	<img src="javascript/img/first.png" class="first" alt="First" title="First page" />
	<img src="javascript/img/prev.png" class="prev" alt="Prev" title="Previous page" />
	<img src="javascript/img//next.png" class="next" alt="Next" title="Next page" />
	<img src="javascript/img/last.png" class="last" alt="Last" title= "Last page" />
	<select class="pagesize">
		<option value="10">10</option>
		<option value="20">20</option>
		<option value="30">30</option>
		<option value="40">40</option>
	</select>
</div>
</fieldset>
<br><br><br>
<br><br><br>

<iframe src="empty.html" name="server_commands" id="server_commands"
		style="visibility: hidden" height="0"></iframe>
		
		
		</body>