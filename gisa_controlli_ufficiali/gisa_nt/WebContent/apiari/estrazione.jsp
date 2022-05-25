<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

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
<%@ include file="../initPage.jsp"%>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
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


<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

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
         			document.getElementById('nominativoDet').style.display="";
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
	 	 
	 	    
	 	   if (form.nominativo.value == ""){
	 	        message += "- Nominativo richiesto\r\n";
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
			<td>Apicoltura </td>
		</tr>
	</table>
</dhv:evaluate>


<div id ="start"></div>


<fieldset>
<legend>
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
			<th aria-label="CUN No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" class="filter-false tablesorter-header" ><div class="tablesorter-header-inner">CUN</div></th>
			<th aria-label="DENOMINAZIONE No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER DETENTORE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">DENOMINAZIONE</div></th>
			<th aria-label="PARTITA IVA/CF ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER CF DETENTORE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">PARTITA IVA/CF</th>
			<th aria-label="NUM. TOTALI APIARI ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER CLASSIFICAZIONE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">NUM. TOTALI APIARI</th>
			<th aria-label="NUM. TOTALI ALVEARI ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER SOTTOSPECIE" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">NUM. TOTALI ALVEARI</th>
			<th aria-label="NUM. TOTALI SCIAMI/NUCLEI ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER MODALITA" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">NUM. TOTALI SCIAMI/NUCLEI</th>
			<th aria-label="COMUNE SEDE LEGALE ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" CLASS="filter-false tablesorter-header"><div class="tablesorter-header-inner">COMUNE SEDE LEGALE</th>
			<th aria-label="INDIRIZZO SEDE LEGALE No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" class="filter-false tablesorter-header" ><div class="tablesorter-header-inner">INDIRIZZO SEDE LEGALE</div></th>
			<th aria-label="DATA REGISTRAZIONE BDA-R No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">DATA REGISTRAZIONE BDA-R</div></th>
			<th aria-label="DATA REGISTRAZIONE BDN No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" class="filter-false tablesorter-header"><div class="tablesorter-header-inner">DATA REGISTRAZIONE BDN</div></th>
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
	
	<%
		Iterator<StabilimentoEstrazione> itStabEstraz = OperatoreDettagli.getListaStabilimentiEstrazione().iterator();
		while (itStabEstraz.hasNext())
		{
			StabilimentoEstrazione thisStab = itStabEstraz.next();
			if (thisStab.isFlagLaboratorio()==false)
			{
			%>
			<tr>
			<td><%=thisStab.getOperatore().getCodiceAzienda()%></td>
			<td><%=thisStab.getOperatore().getRagioneSociale()%></td>
			<td><%=thisStab.getOperatore().getcodice_fiscale_impresa()%></td>
			<td><%=thisStab.getNumApiari()%></td>
			<td><%=thisStab.getNumAlveari()%></td>
			<td><%=thisStab.getNumSciami()%></td>
			<td><%=thisStab.getComuneSedeLegale()%></td>
			<td><%=thisStab.getIndirizzoSedeLegale()%></td>
			<td><%=toDateasString(thisStab.getEntered())%></td>
			<td><%=toDateasString(thisStab.getDataSincronizzazione()) %></td>
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