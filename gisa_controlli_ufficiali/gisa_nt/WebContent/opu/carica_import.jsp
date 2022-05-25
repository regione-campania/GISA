<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!--rita commit 070616-->
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.modules.opu.base.Indirizzo"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<jsp:useBean id="OrgImport"	class="org.aspcfs.modules.accounts.base.Organization" scope="request" />
<jsp:useBean id="SoggettoFisico" class="org.aspcfs.modules.opu.base.SoggettoFisico" scope="request" />
<jsp:useBean id="TipoImpresaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoAttivita" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoCarattere" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="TipoMobili" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
<jsp:useBean id="tipoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="asl_short_description" class="java.lang.String" scope="request"/>



<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">

 

<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>-->
<script f src="dwr/interface/SuapDwr.js"> </script>
<script type="text/javascript" src="dwr/interface/DwrSchedaCentralizzata.js"> </script>
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
<SCRIPT src="javascript/opu.js"></SCRIPT>
<SCRIPT src="javascript/upload.js"></SCRIPT>
<SCRIPT src="javascript/suapUtil.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>


<script src="javascript/jquery.form.js"></script>
<!--PARTE CANDIDATI -->
<script src="opu/rankbased/candidatiRankUtils.js"></script>
<!-- SCRIPT PER CAMPI ESTESI -->
<script src="javascript/aggiuntaCampiEstesiScia.js"></script>


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
  
input:invalid {
  background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAYAAABWdVznAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAT1JREFUeNpi/P//PwMpgImBRMACY/x7/uDX39sXt/67cMoDyOVgMjBjYFbV/8kkqcCBrIER5KS/967s+rmkXxzI5wJiRSBm/v8P7NTfHHFFl5mVdIzhGv4+u///x+xmuAlcdXPB9KeqeLgYd3bDU2ZpRRmwH4DOeAI07QXIRKipYPD35184/nn17CO4p/+cOfjl76+/X4GYAYThGn7/g+Mfh/ZZwjUA/aABpJVhpv6+dQUjZP78Z0YEK7OezS2gwltg64GmfTu6i+HL+mUMP34wgvGvL78ZOEysf8M1sGgZvQIqfA1SDAL8iUUMPIFRQLf+AmMQ4DQ0vYYSrL9vXDz2sq9LFsiX4dLRA0t8OX0SHKzi5bXf2HUMBVA0gN356N7p7xdOS3w5fAgcfNxWtn+BJi9gVVBOQfYPQIABABvRq3BwGT3OAAAAAElFTkSuQmCC);
  background-position:right top;
  background-repeat:no-repeat;
  border: 2px solid red;
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
	
	


	function ControllaPIVA(pi)
	{
		var formtest=true;
		var msg = 'Attenzione!\n'.toUpperCase();
		
		piField=document.getElementById(pi);
		
		pi= piField.value;	
		
		if( pi == '' ){ 
			msg +='Campo Partita iva vuoto!\n'.toUpperCase();
			formtest=false;
		} 
		if( pi.length != 11 ){
			msg+='Non corretta: la partita IVA dovrebbe essere lunga esattamente 11 caratteri.\n'.toUpperCase();
			formtest=false;
		}
	validi = "0123456789";
	for( i = 0; i < 11; i++ ){
	    if( validi.indexOf( pi.charAt(i) ) == -1 ){
	        msg+='Contiene un carattere non valido .\nI caratteri validi sono le cifre.\n'.toUpperCase();
	        formtest=false;
	    }
	}
	s = 0;
	for( i = 0; i <= 9; i += 2 )
	    s += pi.charCodeAt(i) - '0'.charCodeAt(0);
	for( i = 1; i <= 9; i += 2 ){
	    c = 2*( pi.charCodeAt(i) - '0'.charCodeAt(0) );
	    if( c > 9 )  c = c - 9;
	    s += c;
	}
	if( ( 10 - s%10 )%10 != pi.charCodeAt(10) - '0'.charCodeAt(0) ){
		msg+= 'Partita Iva non Valida secondo lo Standard.\n'.toUpperCase();
		formtest=false;
	}


	if(formtest==false){
		msg+='Vuoi continuare comunque ?'.toUpperCase();
		if(confirm(msg)==false)
			piField.value='';
	}
		
	}
</SCRIPT> 


<%@ page import="org.aspcfs.modules.accounts.base.OrganizationAddress"%>
<%@ page import="java.util.*"%>
<%@ page import="org.aspcfs.modules.lineeattivita.base.*"%>
<%@ page import="org.aspcfs.modules.opu.base.Indirizzo" %>
<%@ include file="../initPage.jsp"%>

<%
// PARTE INDIRIZZI

OrganizationAddress addressLegale = new OrganizationAddress();
addressLegale.setIndirizzoOpu(new Indirizzo());
OrganizationAddress addressOperativa = new OrganizationAddress();
addressOperativa.setIndirizzoOpu(new Indirizzo());
OrganizationAddress addressMobile =new  OrganizationAddress();
addressMobile.setIndirizzoOpu(new Indirizzo());


Iterator iaddress = OrgImport.getAddressList().iterator();
Object address[] = null;
if (iaddress.hasNext()) {
	while (iaddress.hasNext()) {
		OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
		if (thisAddress.getType() == 1)
			addressLegale = thisAddress;
		else if (thisAddress.getType() == 5)
			addressOperativa = thisAddress;
		else if (thisAddress.getType() == 7)
			addressMobile = thisAddress;
	}
}
%>

<script>
function calcolaCap(idComune, topon, indir, campo){	
	SuapDwr.getCap(idComune, topon, indir, campo,{callback:calcolaCapCallBack,async:false});
}

function chkCap(idComune,campo_cap) {
	if (idComune == '5279') {
	document.getElementById(campo_cap).pattern="801[1-9][0-9]";
	} else {
	     document.getElementById(campo_cap).pattern="[0-9]{5}";
	}
}


$(document).ready(function(){
	  
	if($("#partitaIva").val()!='')
	$("#partitaIva").trigger("change");
	 mostraDatiStabilimento(document.getElementById("tipoAttivita").value);
	 
});

function calcolaCapCallBack(val){
	var campo = val[1];
	var value = val[0];
	if (value!='80100')
		document.getElementById(campo).value = value;
}
	
function calcolaTipoScheda(tipo){	
	DwrSchedaCentralizzata.getTipoSchedaFromTipoOperatore(tipo,{callback:calcolaTipoSchedaCallBack,async:false});
}

function calcolaTipoSchedaCallBack(val){
	document.getElementById("tipo").value = val;
}
</script>

<%! 
public static String fixIndirizzo(String indirizzo){
	String toRet = "";
	if (indirizzo == null)
		return toRet;
	toRet = indirizzo.trim();
	if (toRet.toUpperCase().startsWith("VIA"))
		toRet = toRet.toUpperCase().replace("VIA", "");
	  	return toHtmlValue(toRet);
	}
%>
<%
// SOGGETTO FISICO
String nome = "";
String cognome = "";
String cf = "";
String sesso_m = "";
String sesso_f = "";
String data_di_nascita = "";
int nazione_nascita = 106;
int nazione_residenza = 106;
String comune_nascita = "";
String code_comune_nascita="";
String comune_residenza = "";
String code_comune_residenza="";
String prov_residenza = "";
String code_prov_residenza="";
String via_residenza = "";
String cap_residenza = "";
String civico_residenza = "";
String email = "";

if(OrgImport.getNomeRappresentante() != null)
	if (!OrgImport.getNomeRappresentante().equals("") && !OrgImport.getNomeRappresentante().equals("NULL"))
		nome = " value=\"" + toHtmlValue(OrgImport.getNomeRappresentante()) + "\" ";
if (OrgImport.getCognomeRappresentante() != null)	
	if (!OrgImport.getCognomeRappresentante().equals("") && !OrgImport.getCognomeRappresentante().equals("NULL"))
		cognome = " value=\"" + toHtmlValue(OrgImport.getCognomeRappresentante()) + "\" ";
if(OrgImport.getCodiceFiscaleRappresentante() != null)	
	if (!OrgImport.getCodiceFiscaleRappresentante().equals("") && !OrgImport.getCodiceFiscaleRappresentante().equals("NULL"))
		cf = " value=\"" + OrgImport.getCodiceFiscaleRappresentante().replace(" ", "") + "\" ";
//if(OrgImport..toUpperCase().contains("M"))
sesso_m = " checked=\"checked\" ";
//else
//	sesso_f=" checked=\"checked\" ";
if (OrgImport.getDataNascitaRappresentante() != null){
	data_di_nascita = " value=\""+toDateasString(OrgImport.getDataNascitaRappresentante())+" \" ";
}
if (OrgImport.getCounty() != null)
	nazione_nascita = 106;//Integer.parseInt(OrgImport.getCounty());
	
	
if (OrgImport.getLuogoNascitaRappresentante()!=null)
	if (!OrgImport.getLuogoNascitaRappresentante().equals("") && !OrgImport.getLuogoNascitaRappresentante().equals("NULL") && OrgImport.getLuogoNascitaRappresentante() != null){
		comune_nascita = "<input type=\"hidden\" size=\"70\" id=\"comuneNascitainput\" name=\"comuneNascitainput\" class=\"required\" value=\""
		+ OrgImport.getLuogoNascitaRappresentante() + "\" >";//" value=\""+SoggettoFisico.getComuneNascita()+"\" readonly=\"readonly\" ";
		code_comune_nascita="<input type=\"hidden\" id=\"code_comune_nascita\" name=\"code_comune_nascita\" value=\""+request.getAttribute("CodiceComuneNascita")+"\">";		
	}
if (OrgImport.getEmailRappresentante() != null)
	if (!OrgImport.getEmailRappresentante().equals("") && !OrgImport.getEmailRappresentante().equals("NULL") && OrgImport.getEmailRappresentante() != null)
		email = " value=\"" + OrgImport.getEmailRappresentante() + "\" ";

// DATI IMPRESA
String den_sociale = "";
String cf_impresa = "";
String pi_impresa = "";
String email_impresa="";

if (OrgImport.getName() != null)
	if (!OrgImport.getName().equals("") && !OrgImport.getName().equals("NULL"))
		den_sociale = " value=\"" + toHtmlValue(OrgImport.getName()) + "\" ";
if (OrgImport.getCodiceFiscale() != null)
	if (!OrgImport.getCodiceFiscale().equals("") && !OrgImport.getCodiceFiscale().equals("NULL"))
		cf_impresa = " value=\"" + OrgImport.getCodiceFiscale().replace(" ", "") + "\" ";
if (OrgImport.getPartitaIva() != null)
	if (!OrgImport.getPartitaIva().equals("") && !OrgImport.getPartitaIva().equals("NULL"))
		pi_impresa = " value=\"" + OrgImport.getPartitaIva().replace(" ", "") + "\" ";
if (OrgImport.getDomicilioDigitale() != null)
	if (!OrgImport.getDomicilioDigitale().equals("") && !OrgImport.getDomicilioDigitale().equals("NULL"))
		email_impresa = " value=\"" + OrgImport.getDomicilioDigitale() + "\" ";

String comune_leg = "";
String code_comune_leg="";
String prov_leg = "";
String code_prov_leg="";
String via_leg = "";
String cap_leg = "";
String civico_leg = "";

String comune_op = "";
String code_comune_op="";
String prov_op = "";
String code_prov_op="";
String via_op = "";
String cap_op = "";
String civico_op = "";

int maxFileSize = -1;
int mb1size = 1048576;
maxFileSize = Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));
String maxSizeString = String.format("%.2f", (double) maxFileSize / (double) mb1size);
%>

<script>

function annullaLineeProduttive()
{
	previstoCodiceNazionale = false;
	mostraAttivitaProduttive('attprincipale',1, <%=(OrgImport.isRiconosciuto() && OrgImport.getTipologia()==1) ? "6500" : "-1"%>, false,-1);
	
	}
	
function annullaLineeProduttive(idTab)
{
	previstoCodiceNazionale = false;
	mostraAttivitaProduttive(idTab.id,1, <%=(OrgImport.isRiconosciuto() && OrgImport.getTipologia()==1) ? "6500" : "-1"%>, false,-1);
	
	}
function checkFormFile(i){
	var form = document.getElementById("form"+i);
	var fileCaricato = form.file1;
	var oggetto = form.subject.value;
	var errorString = '';
	if (fileCaricato.value==''){// || (!fileCaricato.value.endsWith(".pdf") && !fileCaricato.value.endsWith(".csv"))){
		errorString+='Errore! Selezionare un file!';
		form.file1.value='';
	}
	if (oggetto==''){
		errorString+='\nErrore! L\'oggetto è obbligatorio.';
	}
	if (!GetFileSize(form.file1))
		errorString+='\nErrore! Selezionare un file con dimensione inferiore a <%=maxSizeString%> MB';
	if (errorString!= '')
		alert(errorString)
	else{
		//form.filename.value = fileCaricato.value;	
		form.uploadButton.hidden="hidden";
		form.file1.hidden="hidden";
		document.getElementById("image_loading").hidden="";
		document.getElementById("text_loading").hidden="";
		form.submit();
	}
}
function mostraAttivitaProduttiveTutte()
{
	
	
	if(document.getElementById("numeroLinee")!=null)
		{
	var numLinee = parseInt(document.getElementById("numeroLinee").value);
	for ( j = 1; j<=numLinee; j++){
		mostraAttivitaProduttive('attprincipale'+j,1, <%=(OrgImport.isRiconosciuto() && OrgImport.getTipologia()==1) ? "6500" : "-1"%>, false,-1);
// 		mostraAttivitaProduttive('attprincipale'+j,1,-1, false,-1);
	}
		}
}

function GetFileSize(fileid){
	var input = document.getElementById('file1');
    file = input.files[0];
    if (file.size> <%=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"))%>)
	 	return false;
    return true;
}
		
$(function() {
    var that;
    
    
    $('input[name=partitaIva]').change( function() {
        // Get the other input
        that = $('input[name=partitaIva],input[name=codFiscale]').not($(this));
        // If the value of this input is not empty, set the required property of the other input to false
        if($(this).val().length) {
            that.prop('required', false);
        } else {
            that.prop('required', true);
        }
    });
});

function visualizzaData(campo){
	if (campo.value=='1'){
		//	document.getElementById("trDataInizio").style.display="none";
		if (document.getElementById("trDataFine")!=null)
			document.getElementById("trDataFine").style.display="none";
	}else{
		//	document.getElementById("trDataInizio").style.display="";
		if (document.getElementById("trDataFine")!=null)
			document.getElementById("trDataFine").style.display="";
	}
}
	
/*VALIDAZIONE DEL CAMPO DATA*/
$.validator.addMethod("dataFormat",	function(value, element) {
	// put your own logic here, this is just a (crappy) example
    return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
	},
	"Inserire una data nel formato GG/MM/AAAA"
);

var dosubmit=true ;
function validateRequiredFields()
{
	dosubmit=true;
	$(':input[required]:visible').each(function(i, requiredField){

		
		
        if($(requiredField).val()=='')
        {
        	dosubmit=false;
        	 
        
        }
    });
}

$(function () {
	$('form[name=addstabilimento]').submit(function(e){
		
		if('<%=request.getAttribute("pregresso") != null ? request.getAttribute("pregresso") : "false"%>' == 'true')
		{
			$('form[name=addstabilimento]').append($("<input></input>",{type : 'hidden', name : 'pregresso', value : 'true'}));
		}
		
		var ok=1;
		var i=0;
		while(true){
			var app="dataInizioLinea";
			if (i!=0)
				app+=""+i;
			var myElem = document.getElementById(app);
			if (myElem == null) 
				break;
			if (myElem.value==null || myElem.value==""){
				ok=0;
				break;
			}
			i++;
		}
		validateRequiredFields();
		
		
		/*devo forzare in js il controllo del tag pattern per gli input che lo hanno (non funziona in html nativo ??)
		quindi per i campi che hanno il tag pattern, lo controllo, e se non e' superato (IL RETURN FALSE E LA STAMPA DEL MESSAGGIO STA A VALLE DELLE ALTRE VALIDAZIONI ALTRIMENTI PRENDEREBBE LA PRECEDENZA )....
		*/
		 var flagCampiPatternInvalidi = false;
		 if(/*oldIndex == 3*/true)
		 {
		 	var campiPatterns = $("'form[name=addstabilimento]' input[pattern]");
		 	var messaggi = [];
		 	if(campiPatterns.length > 0)
		 	{
		 		//window.campi = campiPatterns;
		 		if(campiPatterns.length > 0)
		 		{
		 			for(var ind = 0; ind < campiPatterns.length; ind++ )
		     		{
		     			var expr = $(campiPatterns[ind]).attr("pattern");
		     			var val = $(campiPatterns[ind]).val();
		     			var regex_expr = new RegExp(expr,"g");
		     			if( regex_expr.test(val) == false )
		     			{
		     				console.log(val+","+expr+" non matcha");
		     				$(campiPatterns[ind]).css('background-color','rgba(255,0,0,0.2)');
		     				messaggi[''+ind] = $(campiPatterns[ind]).attr("title");
		     				
		     			}
		     			else
		     			{
		
		     				$(campiPatterns[ind]).css('background-color','rgba(255,255,255,1.0)');
		     				delete messaggi[''+ind];
		     			}
		     		}
		 			
		 		}
		 		
		 	}
		 	
		 	if(Object.keys(messaggi).length > 0)
		 	{/*almeno uno non matcha */
		 		
		 		var finalMsg = '';
		     	finalMsg += "Attenzione, i seguenti campi non sono conformi :";
		     	 
		     	for(var keyInd in messaggi)
		     	{
		     		finalMsg += ("\n"+messaggi[keyInd]);
		     	}
		     	
		     	flagCampiPatternInvalidi = true;
		     	//alert(finalMsg);
		     	//return false; ritorno in differita
		 	}
		 	
		 } 
		
		
		
		
		
		if((document.getElementById('validatelp').value=='false' || ok==0) || dosubmit == false){
   			if (ok==0)
   				alert('ATTENZIONE! Inserire la data inizio per tutte le linee di attivita\' selezionate'.toUpperCase());
   			else
   				if(dosubmit==false)
   					alert('Controllare di aver valorizzato i campi'.toUpperCase());
   				else
   					alert('Controllare di aver selezionato almeno una linea di attivita\' dalla master list'.toUpperCase());
    			return false ;
   		}else{
   			
   			
   			//validazione aggiuntiva formato date
   			var j = 0;
   			while(true)
   			{
   			
   				var idCampoData = "#dataInizioLinea"+ ( (j > 0) ? j : "");
   				var idCampoData2 = "#dataFineLinea"+ ( (j > 0) ? j : "" );
   				
   				if($(idCampoData).length == 0)
   					break;
   				
   				var dateVal = $(idCampoData).val();
   				var dateVal2 = $(idCampoData2).val();
   				
   				if(!dateVal.match(/^\d\d?\/\d\d?\/\d\d\d\d$/)  ) //la data inizio deve esserci per forza
   				{
   					alert("Inserire un formato data inizio corretto per la linea "+ (j+1));
   					return;
   				}
   				if( (dateVal2 != "" && !dateVal2.match(/^\d\d?\/\d\d?\/\d\d\d\d$/)) ) //la data fine non è obbligatoria, quindi la validazione formato è fatta solo se presente
   				{
   					alert("Inserire un formato data fine corretto per la linea "+ (j+1));
   					return;
   				}
   				
   				j++;
   				
   			}
   			
   			 /*ritorno false se non era stata superata la validazione ad hoc per i campi con attributo pattern */
   	        if(flagCampiPatternInvalidi)
   	        {
   	        	alert(finalMsg);
   	        	 
   	        	return ;
   	        }
   			
   			
			loadModalWindowCustom("Salvataggio In Corso. Attendere");
			e.preventDefault();
	    	$.ajax({
	    		type: 'POST',
	        	dataType: "json",
	        	cache: false,
	        	url: 'OpuStab.do?command=InsertSuap&auto-populate=true',
	        	data: ''+$(this).serialize(), 
	        	success: function(msg){
	        		alert(msg.erroreSuap);
	        		
	        		if(msg.idStabilimento>0 ){
	        			location.href='OpuStab.do?command=Details&stabId='+msg.idStabilimento;
	        		}
            		else
            			location.href='OpuStab.do?command=SearchForm';
            			
	        		
	            	loadModalWindowUnlock();
	        	},
	        	error: function (err) {
	        		alert('ko '+err.responseText);
	        	}
	    	});
		}
	});     
	$( "#dialogsuap" ).dialog({
		
		title : "ATTENZIONE! LA PARTITA IVA INSERITA ESISTE NEL SISTEMA.",
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: false,
	    width:850,
	    height:500,
	    draggable: false,
	    modal: false,
	}).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	$( "#dialogsuaplistastab" ).dialog({
		title: "Attenzione ! Lo Stabilimento che si vuole importare Esiste in Anagrafica Sabilimenti. Selezionarlo se i dati corrispondono",

		autoOpen: false,
	    resizable: false,
	    closeOnEscape: false,
	    width:850,
	    height:500,
	    draggable: false,
	    modal: false,
	}).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	
	$( "#dialogsuaplistaTarghe" ).dialog({
		title : "ATTENZIONE! ALL'IMPRESA CHE SI STA IMPORTANDO RISULTANO ASSOCIATI ALTRI AUTOMEZZI PRESENTI IN STABILIMENTI 852. SPUNTARE GLI AUTOMEZZI DA IMPORTARE IN ANAGRAFICA STABILIMENTI",
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: false,
	    width:850,
	    height:500,
	    draggable: false,
	    modal: false,
	}).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	
	
});         
	 
/*GESTIONE STEP AGGIUNGI STABILIMENTO*/
$(function (){
	$("#spinner").bind("ajaxSend", function() {
	   	$(this).show();
	}).bind("ajaxStop", function() {
		$(this).hide();
	}).bind("ajaxError", function() {
	    $(this).hide();
	});
	var form =$("#example-advanced-form").ajaxForm(options).show();
    form.steps({
    	headerTag: "h3",
        bodyTag: "fieldset",
        transitionEffect: "slideLeft",
        stepsOrientation: "horizontal",
        onStepChanging: function (event, currentIndex, newIndex){
        	// Allways allow previous action even if the current form is not valid!
            if (currentIndex > newIndex){
            	return true;
            }
            // Forbid next action on "Warning" step if the user is to young
            if (currentIndex == 0){
            	if ( $('input[name=partitaIva]').val()!='' || $('input[name=codFiscale]').val()!=''|| $('input[name=ragioneSociale]').val()!=''){
                	loadModalWindowCustom("Verifica Esistenza Impresa. Attendere");
                	$.ajax({
                    	type: 'POST',
                   		dataType: "json",
                   		cache: false,
                  		url: 'OpuStab.do?command=VerificaEsistenza',
        		        data: ''+$('form[name=addstabilimento]').serialize(), 
	                	success: function(msg) {
	                   		result = msg ;
                   			if(msg.codiceErroreSuap =="1"){
                 				showPopUp();
                   			}else{
 	                  			if(msg.codiceErroreSuap =="2"){
                   					var htmlText="" ; //msg[0].erroreSuap;
	                   				if (msg.listaOperatori!=null){
                   						htmlText+='<table border="1" class="pagedList"><tr><th>Denominazione</th><th>Partita IVa</th><th>Rappresentante Legale</th><th>Sede Legale</th><th></th></tr>'
	                   					var jsontext = JSON.stringify(msg);
                   						for(i=0;i<msg.listaOperatori.length;i++){
	                   						htmlText+="<tr><td>"+(msg.listaOperatori[i].ragioneSociale)+"</td><td>"+msg.listaOperatori[i].partitaIva+"</td><td>"+(msg.listaOperatori[i].rappLegale.nome)+" "+(msg.listaOperatori[i].rappLegale.cognome)+" "+msg.listaOperatori[i].rappLegale.codFiscale+ " "+msg.listaOperatori[i].rappLegale.indirizzo.descrizioneComune+" "+msg.listaOperatori[i].rappLegale.indirizzo.descrizione_provincia+" "+msg.listaOperatori[i].rappLegale.indirizzo.via+"</td><td>"+msg.listaOperatori[i].sedeLegaleImpresa.descrizioneComune+" "+msg.listaOperatori[i].sedeLegaleImpresa.descrizione_provincia+" "+(msg.listaOperatori[i].sedeLegaleImpresa.via)+"</td><td><input type='button' value='Seleziona' onclick='selezionaImpresa("+i+")'></td></tr>";
										}
                   						if ( $('input[name=partitaIva]').val()=='' && $('input[name=codFiscale]').val()=='')
                  							htmlText+="<tr><td><input type='button' value='Esci' onclick='closePopup()'></td></tr></table>";
                  						else
                  							if(document.getElementById("tipo_impresa").value=="3" || document.getElementById("tipo_impresa").value=="2"){
                  								
                      							htmlText+="<tr><td><input type='button' value='Dati Impresa non Corrispondenti' onclick='closePopup()'></td></tr></table>";

                  							}
                  							else
                  								{
                  								htmlText+="</table>";
                  								}
                  							
 	                  			
                  						
                   			 			showPopUp();
	                   					$( "#dialogsuap" ).html(htmlText);
               						}
             	        		}
                   			}
                   			loadModalWindowUnlock();    
                   		},
                   		error: function (err) {
                   			alert('ko '+err.responseText);
	                    }
               		});
				} 
			}
            if(currentIndex==1)
            {
            
            	 if(document.getElementById("tipoAttivita").value=="2" && document.getElementById("orgId").value!="0" && document.getElementById("orgId").value!="-1" && document.getElementById("orgId").value!="")
            	 {
            		 
            		 checkForm=true;
            	 
            	if(checkForm==true)
            		 checkAltreTarghe();
            	
            	
            	 }else
                	 if(document.getElementById("tipoAttivita").value!="2")
                	 {
                    	checkStab();
                	 }
             
            }
            	
            if (currentIndex ==2 ){
            	
            
            	if(document.getElementById('validatelp').value=='false'){
                	alert('Controllare di aver selezionato almeno una linea di attivita\' dalla master list'.toUpperCase());
                	return false ;
                }
            }
            // Needed in some cases if the user went back (clean up)
            if (currentIndex < newIndex){
                // To remove error styles
                form.find(".body:eq(" + newIndex + ") label.error").remove();
                form.find(".body:eq(" + newIndex + ") .error").removeClass("error");
            }
            form.validate().settings.ignore = ":disabled,:hidden";
            return form.valid();
        },
        onStepChanged: function (event, currentIndex, priorIndex){
        	// Used to skip the "Warning" step if the user is old enough.
            if (currentIndex ==2 ){
            	
            	 if($("#tipo_impresa").val()=="9")
        		 {
        		 if(document.getElementById("secondarie")!=null)
        		 document.getElementById("secondarie").style.display="none";
        		 }
        	 else
        		 {
        		 if(document.getElementById("secondarie")!=null)
        		 document.getElementById("secondarie").style.display="";
        		 }
            	
                	mostraAttivitaProduttive('attprincipale',1, <%=(OrgImport.isRiconosciuto() && OrgImport.getTipologia()==1) ? "6500" : "-1"%>, false,-1);
                
            }
            if (currentIndex ==3 ){
            	window.frames[0].location.reload(true);
            }
		},
        onFinishing: function (event, currentIndex){
        	form.validate().settings.ignore = ":disabled";
            return form.valid();
        },
        onFinished: function (event, currentIndex){
        	alert("Submitted!".toUpperCase());
        },
        onSaveTemp: function (event, currentIndex){
        	alert("Submitted!");
        }
    }).validate({
    	errorPlacement: function errorPlacement(error, element) { element.before(error); },
        	rules: {
            	// dataNascita: {
                //	  dataFormat :true
                //	 },
                domicilioDigitale:{
                	
                    email: true
                }
            }
        });
})

function sovrascriviImpresa(){
	$('input[name="sovrascrivi"]').val("si");
	$('#dialogsuap').dialog('close');
	$('form[name=addstabilimento]').submit();
}

var result ;

function selezionaStabilimento(i){	
	var stab = resultStab[i];
	$("#idStabilimento").val(stab.idStabilimento);
    document.getElementById("viewRic").innerHTML = "<button onclick=\"javascript:window.open('./schede_centralizzate/iframe.jsp?objectId="+stab.idStabilimento+"&objectIdName=stab_id&tipo_dettaglio=15')\"  value=\"Visualizza Richiesta Inserita\" />"

// 	document.getElementById("test2").src = "./schede_centralizzate/iframe.jsp?objectId="+stab.idStabilimento+"&objectIdName=stab_id&tipo_dettaglio=15"
	$('input[name="searchcodeIdprovinciaStabinput"]').val(stab.sedeOperativa.descrizione_provincia);
	$("#searchcodeIdprovinciaStab").append("<option value='"+stab.sedeOperativa.idProvincia+ "' selected='selected'>"+stab.sedeOperativa.descrizione_provincia+"</option>');");
	$('input[name="searchcodeIdComuneStabinput"]').val(stab.sedeOperativa.descrizioneComune);
	$("#searchcodeIdComuneStab").append("<option value='"+stab.sedeOperativa.comune+ "' selected='selected'>"+stab.sedeOperativa.comune+"</option>');");
	$('input[name="viaStabinput"]').val((stab.sedeOperativa.via));
	$("#viaStab").append("<option value='"+stab.sedeOperativa.idIndirizzo+ "' selected='selected'>"+(stab.sedeOperativa.via)+"</option>');");
	$('input[name="toponimoSedeOperativa"]').val(stab.sedeOperativa.toponimo);
	$('input[name="civicoSedeOperativa"]').val(stab.sedeOperativa.civico);
	$('input[name="capStab"]').val(stab.sedeOperativa.cap);	
	$('#tipoAttivita').val(stab.tipoAttivita);
	$('#tipoCarattere').val(stab.tipoCarattere);
	$('#dataInizio').val(stab.dataInizioAttivitaString);
	$('#dataFine').val(stab.dataFineAttivitaString);
	$('#dialogsuaplistastab').dialog('close');
}
	
function selezionaImpresa(i){
	var msgHD="ATTENZIONE! Uno o più campi obbligatori dell'impresa non sono presenti, contattare l'helpdesk.";
	
	var operatori = result.listaOperatori;
	
  	$('#tipo_impresa').val(operatori[i].tipo_impresa);
  	onChangeTipoImpresa();
  	$('input[name="presentataScia"]').val(operatori[i].presentataScia);
  	$('#tipo_societa').val(operatori[i].tipo_societa);
  	$('input[name="idOperatore"]').val(operatori[i].idOperatore);
  	$('input[name="ragioneSociale"]').val((operatori[i].ragioneSociale));
  	
  	if(operatori[i].presentataScia==true)
  		$('input[name="ragioneSociale"]').prop("readonly", true);
  	$('input[name="partitaIva"]').val(operatori[i].partitaIva);
  	if(operatori[i].partitaIva!=null & operatori[i].partitaIva!="")
  		$('input[name="partitaIva"]').prop("readonly", true);
  	else
  		if( $('input[name="partitaIva"]').prop('required')){
  			alert(msgHD.toUpperCase()); location.href='OpuStab.do?command=SearchForm';		
  		}
	
  	if(operatori[i].codFiscale)
  	$('input[name="codFiscale"]').val(operatori[i].codFiscale);
  	if(operatori[i].codFiscale!=null & operatori[i].codFiscale!="" && operatori[i].presentataScia==true)
  		$('input[name="codFiscale"]').prop("readonly", true);
  	else
  		if( $('input[name="codFiscale"]').prop('required')){
  			alert(msgHD.toUpperCase()); location.href='OpuStab.do?command=SearchForm';		
  		}
	
  	$('input[name="domicilioDigitale"]').val(operatori[i].domicilioDigitale);
  	if(operatori[i].domicilioDigitale!=null & operatori[i].domicilioDigitale!="" && operatori[i].presentataScia==true)
  		$('input[name="domicilioDigitale"]').prop("readonly", true);
  	else
  		if( $('input[name="domicilioDigitale"]').prop('required')){
  			alert(msgHD.toUpperCase()); location.href='OpuStab.do?command=SearchForm';		
  		}
	
  	$('input[name="comuneNascitainput"]').val(operatori[i].rappLegale.comuneNascita);
  	$("#comuneNascita").append("<option value='"+operatori[i].rappLegale.idComuneNascita+ "' selected='selected'>"+operatori[i].rappLegale.comuneNascita+"</option>');");
  	if(operatori[i].rappLegale.comuneNascita!=null & operatori[i].rappLegale.comuneNascita!="" && operatori[i].presentataScia==true)
  	{
  		$('input[name="comuneNascitainput"]').prop("readonly", true);
  		document.getElementById('nazioneNascita').disabled=true;
	}
  	else
  		if( $('input[name="comuneNascitainput"]').prop('required')){
  			alert(msgHD.toUpperCase()); location.href='OpuStab.do?command=SearchForm';		
  		}
	
  	if(operatori[i].presentataScia==true)
	{
		alert("I dati dell'impresa non sono modificatbili".toUpperCase());
	}
	else
	{
		alert("Attenzione le modifiche che verranno apportate ai dati dell'impresa sarannno propagate sugli stabilimenti gia' esistenti.".toUpperCase())
	}
  	
  	$('input[name="nome"]').val((operatori[i].rappLegale.nome));
  	if(operatori[i].rappLegale.nome!=null & operatori[i].rappLegale.nome!="" && operatori[i].presentataScia==true)
  		$('input[name="nome"]').prop("readonly", true);
  	else
  		if( $('input[name="nome"]').prop('required')){
  			alert(msgHD.toUpperCase()); location.href='OpuStab.do?command=SearchForm';		
  		}
	
  	$('input[name="cognome"]').val((operatori[i].rappLegale.cognome));
  	if(operatori[i].rappLegale.cognome!=null & operatori[i].rappLegale.cognome!="" && operatori[i].presentataScia==true)
  		$('input[name="cognome"]').prop("readonly", true);
  	else
  		if( $('input[name="cognome"]').prop('required')){
  			alert(msgHD.toUpperCase()); location.href='OpuStab.do?command=SearchForm';		
  		}
	
	$('input[name="codFiscaleSoggetto"]').val(operatori[i].rappLegale.codFiscale);
  	if(operatori[i].rappLegale.codFiscale!=null & operatori[i].rappLegale.codFiscale!="" && operatori[i].presentataScia==true)
  		$('input[name="codFiscaleSoggetto"]').prop("readonly", true);
  	else
  		if( $('input[name="codFiscaleSoggetto"]').prop('required')){
  			alert(msgHD.toUpperCase()); location.href='OpuStab.do?command=SearchForm';		
  		}
	
  	$('input[name="dataNascita"]').val(operatori[i].rappLegale.dataNascitaString);
  	if(operatori[i].rappLegale.dataNascitaString!=null & operatori[i].rappLegale.dataNascitaString!="" && operatori[i].presentataScia==true)
  		$('input[name="dataNascita"]').prop("readonly", true);
  	else
  		if( $('input[name="dataNascita"]').prop('required')){
  			alert(msgHD.toUpperCase()); location.href='OpuStab.do?command=SearchForm';		
  		}
	
  	$('input[name="nazioneResidenza"]').val(operatori[i].rappLegale.indirizzo.nazione);
  	$('input[name="nazioneResidenzaId"]').val(document.getElementById("nazioneResidenza").options[document.getElementById("nazioneResidenza").selectedIndex].value);
  	if(operatori[i].rappLegale.indirizzo.nazione!=null & operatori[i].rappLegale.indirizzo.nazione!="" && operatori[i].presentataScia==true)
  		$('input[name="nazioneResidenza"]').prop("readonly", true);

	
  	$('input[name="addressLegaleCountryinput"]').val(operatori[i].rappLegale.indirizzo.descrizione_provincia);
  	$("#addressLegaleCountry").append("<option value='"+operatori[i].rappLegale.indirizzo.idProvincia+ "' selected='selected'>"+operatori[i].rappLegale.indirizzo.descrizione_provincia+"</option>');");
  	if(operatori[i].rappLegale.indirizzo.descrizione_provincia!=null & operatori[i].rappLegale.indirizzo.descrizione_provincia!="" && operatori[i].presentataScia==true)
  		$('input[name="addressLegaleCountryinput"]').prop("readonly", true);
  	else
  		if( $('input[name="addressLegaleCountryinput"]').prop('required')){
  			alert(msgHD.toUpperCase()); location.href='OpuStab.do?command=SearchForm';		
  		}
	
	$('input[name="addressLegaleCitta"]').val(operatori[i].rappLegale.indirizzo.descrizioneComune);
  	$("#addressLegaleCity").append("<option value='"+operatori[i].rappLegale.indirizzo.comune+ "' selected='selected'>"+operatori[i].rappLegale.indirizzo.descrizioneComune+"</option>');");
  	if(operatori[i].rappLegale.indirizzo.descrizioneComune!=null & operatori[i].rappLegale.indirizzo.descrizioneComune!="" && operatori[i].presentataScia==true)
  	{
  		$('input[name="addressLegaleCitta"]').prop("readonly", true);
  		document.getElementById('nazioneResidenza').disabled=true;
  	}
  	else
  		if( $('input[name="addressLegaleCitta"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=SearchForm';		
  		}
	
	$('input[name="addressLegaleLine1input"]').val((operatori[i].rappLegale.indirizzo.via));
  	$("#addressLegaleLine1").append("<option value='"+operatori[i].rappLegale.indirizzo.idIndirizzo+ "' selected='selected'>"+(operatori[i].rappLegale.indirizzo.via)+"</option>');");
  	if(operatori[i].rappLegale.indirizzo.via!=null & operatori[i].rappLegale.indirizzo.via!="" && operatori[i].presentataScia==true)
  		$('input[name="addressLegaleLine1input"]').prop("readonly", true);
  	else
  		if( $('input[name="addressLegaleLine1input"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=SearchForm';		
  		}
	
	$('input[name="toponimoResidenza"]').val(operatori[i].rappLegale.indirizzo.toponimo);
	if(operatori[i].rappLegale.indirizzo.toponimo!=null & operatori[i].rappLegale.indirizzo.toponimo!="" && operatori[i].presentataScia==true)
  		$('input[name="toponimoResidenza"]').prop("readonly", true);

	
	$('input[name="civicoResidenza"]').val(operatori[i].rappLegale.indirizzo.civico);
	if(operatori[i].rappLegale.indirizzo.via!=null & operatori[i].rappLegale.indirizzo.via!="" && operatori[i].presentataScia==true)
  		$('input[name="civicoResidenza"]').prop("readonly", true);
  	else
  		if( $('input[name="civicoResidenza"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=SearchForm';		
  		}
	
	$('input[name="capResidenza"]').val(operatori[i].rappLegale.indirizzo.cap);
	if(operatori[i].rappLegale.indirizzo.civico!=null & operatori[i].rappLegale.indirizzo.civico!="" && operatori[i].presentataScia==true)
  		$('input[name="capResidenza"]').prop("readonly", true);
  	else
  		if( $('input[name="capResidenza"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=SearchForm';		
  		}
	
	$('input[name="nazioneSedeLegale"]').val(operatori[i].sedeLegaleImpresa.nazione);
	$('input[name="nazioneSedeLegaleId"]').val(document.getElementById("nazioneSedeLegale").options[document.getElementById("nazioneSedeLegale").selectedIndex].value);
	if(operatori[i].sedeLegaleImpresa.nazione!=null & operatori[i].sedeLegaleImpresa.nazione!="" && operatori[i].presentataScia==true)
  		$('input[name="nazioneSedeLegale"]').prop("readonly", true);

	
  	$('input[name="searchcodeIdprovinciainput"]').val(operatori[i].sedeLegaleImpresa.descrizione_provincia);
  	$("#searchcodeIdprovincia").append("<option value='"+operatori[i].sedeLegaleImpresa.idProvincia+ "' selected='selected'>"+operatori[i].sedeLegaleImpresa.descrizione_provincia+"</option>');");
  	if(operatori[i].sedeLegaleImpresa.descrizione_provincia!=null & operatori[i].sedeLegaleImpresa.descrizione_provincia!="" && operatori[i].presentataScia==true)
  		$('input[name="searchcodeIdprovinciainput"]').prop("readonly", true);
  	else
  		if( $('input[name="searchcodeIdprovinciainput"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=SearchForm';		
  		}
	
	$('input[name="codeIdComune"]').val(operatori[i].sedeLegaleImpresa.descrizioneComune);
  	$("#searchcodeIdComune").append("<option value='"+operatori[i].sedeLegaleImpresa.comune+ "' selected='selected'>"+operatori[i].sedeLegaleImpresa.comune+"</option>');");
  	if(operatori[i].sedeLegaleImpresa.descrizioneComune!=null & operatori[i].sedeLegaleImpresa.descrizioneComune!="" && operatori[i].presentataScia==true)
  	{
  		$('input[name="codeIdComune"]').prop("readonly", true);
  		document.getElementById('nazioneSedeLegale').disabled=true;
  	}
  	else
  		if( $('input[name="codeIdComune"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=SearchForm';		
  		}
	
	$('input[name="viainput"]').val((operatori[i].sedeLegaleImpresa.via));	
	$("#via").append("<option value='"+operatori[i].sedeLegaleImpresa.idIndirizzo+ "' selected='selected'>"+(operatori[i].sedeLegaleImpresa.via)+"</option>');");
	if(operatori[i].sedeLegaleImpresa.via!=null & operatori[i].sedeLegaleImpresa.via!="" && operatori[i].presentataScia==true)
  		$('input[name="viainput"]').prop("readonly", true);
  	else
  		if( $('input[name="viainput"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=SearchForm';		
  		}
	
	$('input[name="toponimoSedeLegale"]').val(operatori[i].sedeLegaleImpresa.toponimo);
	if(operatori[i].sedeLegaleImpresa.toponimo!=null & operatori[i].sedeLegaleImpresa.toponimo!="" && operatori[i].presentataScia==true)
  		$('input[name="toponimoSedeLegale"]').prop("readonly", true);

	$('input[name="civicoSedeLegale"]').val(operatori[i].sedeLegaleImpresa.civico);
	if(operatori[i].sedeLegaleImpresa.civico!=null & operatori[i].sedeLegaleImpresa.civico!="" && operatori[i].presentataScia==true)
  		$('input[name="civicoSedeLegale"]').prop("readonly", true);
  	else
  		if( $('input[name="civicoSedeLegale"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=SearchForm';		
  		}
	
	$('input[name="presso"]').val(operatori[i].sedeLegaleImpresa.cap);	
	if(operatori[i].sedeLegaleImpresa.cap!=null & operatori[i].sedeLegaleImpresa.cap!="" && operatori[i].presentataScia==true)
  		$('input[name="presso"]').prop("readonly", true);
  	else
  		if( $('input[name="presso"]').prop('required')){
  			alert(msgHD); location.href='OpuStab.do?command=SearchForm';		
  		}
	
  	$('input[name="sovrascrivi"]').val("no");
  	  $('#dialogsuap').dialog('close');
  	var x = document.getElementsByName("daBloccare");
	var i;
	for (i = 0; i < x.length; i++) {
   		x[i].disabled = true;
	}
}
          
/*AUTOCOMPLETAMENTO PER GLI INDIRIZZI*/          
$(function() {
    //$( "#searchcodeIdComune" ).combobox();
    //  $( "#partitaIva" ).combobox();
    $( "#addressLegaleCountry" ).combobox();
    //$( "#addressLegaleCity" ).combobox();
    $( "#comuneNascita" ).combobox();
    $( "#addressLegaleLine1" ).combobox();
    //$( "#searchcodeIdComune" ).combobox();
    $( "#searchcodeIdprovincia" ).combobox();
    $( "#via" ).combobox();
    $( "#searchcodeIdComuneStab" ).combobox();
    $( "#searchcodeIdprovinciaStab" ).combobox();
    $( "#viaStab" ).combobox();
    
   

});

function openPopup(id,tipo){
	var res;
    var result;
	window.open('ServletServiziScheda?object_id='+id+'&tipo_dettaglio='+tipo+'&object_id_name=org_id&output_type=html&visualizzazione=screen&object_css=','popupSelect',
    'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

var campoLat;
var campoLong;

function showCoordinate(address,city,prov,cap,campo_lat,campo_long){
	campoLat = campo_lat;
	campoLong = campo_long;
	Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
}

function setGeocodedLatLonCoordinate(value){
	campoLat.value = value[1];;
	campoLong.value =value[0];
}

function controllaCoordinate(coord, tipo){
	var val = coord.value;
	 if(isNaN(val)){
		 alert('Inserire le coordinate nel formato numerico'.toUpperCase());
		 coord.value = '';
		 return false;
	 }
	 if(val.charAt(2)!='.'){
		 alert('Inserire le coordinate nel formato corretto (XX.XXXXXX)'.toUpperCase());
		 coord.value = '';
		 return false;
	 }
	if (tipo=='lat' && (val < 39.988475 || val > 41.503754)){
	 	alert('Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754'.toUpperCase());
		coord.value = '';
		return false;
	}   
	if (tipo=='long' && (val < 13.7563172 || val > 15.8032837)){
      	alert('Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837'.toUpperCase());
      	coord.value = '';
		return false;
  	 }   	 
	return true;
}

function gestisciCFUguale(cb){
	var iv = document.getElementById("partitaIva").value;
	var cf = document.getElementById("codFiscale");
	if (cb.checked)
		cf.value = iv;
	else
		cf.value = '';
	
}

function controlloPiva(){
	var nazione = getSelectedText('nazioneSedeLegale');
	var iva = document.getElementById("partitaIva");
	if (nazione == "Italia"){
		iva.setAttribute("min", 11);
		iva.setAttribute("maxlength", 16);
		}
	else {
		iva.setAttribute("min", 4);
		iva.setAttribute("maxlength", 50);
	}
}

function mostraNascondiLinee(){
	var div = document.getElementById("vecchieLinee");
	if (div.style.display=='none')
		div.style.display='block';
	else
		div.style.display='none';
}

function resizeIframe(iframe){
	alert(iframe.contentWindow.document.body.scrollHeight);
    iframe.height = iframe.contentWindow.document.body.scrollHeight + "px";
}
 
function cancellaFile(element,url,indice){
	$.ajax({
    	url:url,
        async: false,
        success :  function(data){
        	// Your Code here
			alert("Allegato Eliminato Correttamente!".toUpperCase())
            document.getElementById("fileAllegato"+indice).innerHTML="";
			document.getElementById("linkallegato"+indice).style.display="";
			indice=1;
			var allegatoFile=false;
			while(document.getElementById("fileAllegato"+indice)!=null){
				if (document.getElementById("fileAllegato"+indice).innerHTML!=''){
			   		allegatoFile=true ;
			   		break;
			   	}
			   	indice++;
			}
			if (allegatoFile==false){
		   		document.getElementById("documentazione_parziale").value="0";
	  		}
		}
    })
}

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


<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Ricerca Anagrafica stabilimenti</dhv:label></a> >
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Anagrafica stabilimenti</dhv:label></a> >
</td>
</tr>
</table>

<% if (OrgImport.getOrgId()>0){ %>
<center>
<div style="display:none">
<%=tipoList.getHtmlSelect("tipo", 1) %>
</div>
<b><a href ="#" onClick="calcolaTipoScheda('<%=OrgImport.getTipologia() %>'); openPopup('<%=OrgImport.getOrgId() %>', document.getElementById('tipo').value)" >VISUALIZZA SCHEDA OPERATORE IN GISA</a></b>
</center>
<%} %>

<% if (OrgImport.getOrgId()>0){ %>

<h3>TRASFERIMENTO STABILIMENTO/ATTIVITA' IN ANAGRAFICA STABILIMENTI </h3>
<%}
else
{%>
<h3>INSERIMENTO STABILIMENTO/ATTIVITA' PREGRESSO</h3>

<%} %>
<br>
<br>
<form id="example-advanced-form" name="addstabilimento" action="#" enctype="multipart/form-data">

<input type = "hidden" name = "asl_short_description" id = "asl_short_description" value="<%=asl_short_description%>">
<input type = "hidden" name = "pregresso" id = "pregresso" value="<%=request.getAttribute("pregresso") != null ? request.getAttribute("pregresso") : "false"%>">
<input type = "hidden" name = "asl_user" id = "asl_user" value =<%= User.getSiteId() %>>
<input type = "hidden" name = "methodRequest" value ="new">
<input type="hidden"  name="stato" id="stato" value="<%=newStabilimento.getStato()%>"> 
	<input type="hidden" name="tipoInserimentoScia" id="tipoInserimentoScia" value="<%=newStabilimento.getTipoInserimentoScia()%>"> 
	<input type="hidden" name="sovrascrivi" id="sovrascrivi" value="n.d">
	<input type="hidden" name="idOperatore" id="idOperatore" value="n.d">
	<input type="hidden" name="idStabilimento" id="idStabilimento" value=""> 
	<input type="hidden" name="pratica_completa" id="pratica_completa" value="0">
	<input type="hidden" name="documentazione_parziale"	id="documentazione_parziale" value="0">
	
	<input type="hidden" name="presentataScia"	id="presentataScia" value="false">
	

	<div style="display: none;"> 
    &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
	</div>

	<h3>TIPO IMPRESA</h3>
	<fieldset>
		<legend>INDICARE IL TIPO DI IMPRESA CHE SI VUOLE INSERIRE</legend>
	
		  <%
                TipoImpresaList.setJsEvent("onchange=onChangeTipoImpresa();");
                TipoImpresaList.setRequired(true);
                TipoImpresaList.addItem(-1, "Seleziona Voce");
                	   
                %>
                 <%=TipoImpresaList.getHtmlSelect("tipo_impresa", -1) %>
		
	</fieldset>
	
	
	
	<h3>DETTAGLI IMPRESA</h3>
	<fieldset id="formImpresa">
	<fieldset id="dati_impresa">
			<legend>DATI IMPRESA</legend>
			<table style="height: 100%; width: 100%">
				<tr id="tipo_societaTR">
					<td nowrap id="trTipoSocieta">TIPO SOCIETA'</td>
					<td><%=TipoSocietaList.getHtmlSelect("tipo_societa", -1)%></td>
				</tr>
				<tr>
					<td nowrap>
						<p id="labelRagSoc">
							DITTA/<br>DENOMINAZIONE/<br>RAGIONE SOCIALE
						</p>
					</td>
					<td><input type="text" size="70" id="ragioneSociale"
						class="required" name="ragioneSociale" <%=den_sociale %>></td>
				</tr>
				<tr>
					<td nowrap>PARTITA IVA</td>
					<td><input type="text" size="70" min="11" maxlength="11"
						id="partitaIva" name="partitaIva" class="required" <%=pi_impresa %> onchange="ControllaPIVA('partitaIva')"></td>
				</tr>
				<tr id="codFiscaleTR">
					<td nowrap>CODICE FISCALE<br>IMPRESA
					</td>
					<td><input type="text" size="70" maxlength="16"
						id="codFiscale" name="codFiscale" class="required" <%=cf_impresa %>>  <input type="checkbox"  id="codFiscaleUguale" onClick="gestisciCFUguale(this)" name="daBloccare"/> Uguale alla P.IVA    </td>
				</tr>
				<tr id="codFiscaleTR">
					<td nowrap>DOMICILIO DIGITALE(PEC)</td>
					<td><input type="text" size="70" maxlength="70"
						id="domicilioDigitale" name="domicilioDigitale" <%=email_impresa %>>
					</td>
				</tr>
				<tr>
				<td>NOTE</td>
				<td>
				<textarea name="noteImpresa" cols="100" rows="5" id="noteImpresa" ><%=(OrgImport!=null) ? ((OrgImport.getNotes()!=null) ? OrgImport.getNotes():""):""%></textarea>							
				</td>
				</tr>
			</table>
		</fieldset>
		<br><br><fieldset  id="dati_titolare">
			<legend>DATI TITOLARE/LEGALE RAPPRESENTANTE</legend>
			<table style="height: 100%; width: 100%">
				<tr>
					<td>NOME</td>
					<td><input type="text" size="70" id="nome" name="nome"
						class="required" "<%=(nome!=null && !nome.equalsIgnoreCase(cognome)) ? nome :""%>>
						<% if (OrgImport.getOrgId()>0 && (nome!=null && nome.equalsIgnoreCase(cognome))){ 
								
							%>
							<br><font size="2" color="RED">NOMINATIVO IMPORTATO: </font><font size="2"><%=(nome!=null && nome.equalsIgnoreCase(cognome)) ? nome.replace("value=", "") : "non disponibile"  %></font>
							<% } %>
						
						</td>
				</tr>
				<tr>
					<td><label for="cognome-2">COGNOME </label></td>
					<td><input type="text" size="70" id="cognome" name="cognome"
						class="required"  <%=(cognome!=null && !cognome.equalsIgnoreCase(nome)) ? cognome :""%>>
						<% if (OrgImport.getOrgId()>0 && (cognome!=null && cognome.equalsIgnoreCase(nome))){ 
								
							%>
							<br><font size="2" color="RED">NOMINATIVO IMPORTATO: </font><font size="2"><%=(cognome!=null && cognome.equalsIgnoreCase(nome)) ? cognome.replace("value=", "") : "non disponibile"  %></font>
							<% } %>
						
						</td>
				</tr>
				<tr>
					<td><label for="sesso-2">SESSO </label></td>
					<td><div class="test">
							<input type="radio" name="sesso" id="sesso1" value="M"
								class="required css-radio" <%=sesso_m%>> <label
								for="sesso1" class="css-radiolabel radGroup2">M</label> <input
								type="radio" name="sesso" id="sesso2" value="F"
								class="required css-radio" <%=sesso_f%>> <label
								for="sesso2" class="css-radiolabel radGroup2">F</label>
						</div></td>
				</tr>
				<tr>
					<td><label for="dataN-2">DATA NASCITA </label></td>
					<td><input type="text" size="15" name="dataNascita" 
						id="dataNascita2" placeholder="dd/MM/YYYY" <%=(data_di_nascita) %>>
					</td>
				</tr>
				<tr>
					<td><label for="nazioneN-2">NAZIONE NASCITA</label></td>
					<td>
						<%
							NazioniList
									.setJsEvent("onchange=\"abilitaCodiceFiscale('nazioneNascita');sbloccoProvincia('nazioneNascita',null,'comuneNascita',null)\"");
						%>
						<%=NazioniList.getHtmlSelect("nazioneNascita", nazione_nascita)%></td>
				</tr>
				<tr>
					<td nowrap>COMUNE NASCITA</td>
									<td><select name="comuneNascita" id="comuneNascita"
						>
							<option value="">SELEZIONA COMUNE</option>
					</select> <input type="hidden" name="comuneNascitaTesto"
						id="comuneNascitaTesto" /></td>
						<% if(!comune_nascita.equals("")){  %>
							<%=comune_nascita %>
							<%=code_comune_nascita %>
							<script>
							$("#comuneNascita").append("<option value='"+ document.getElementById("code_comune_nascita").value+ "' selected='selected'>"+document.getElementById("comuneNascitainput").value+"</option>");
							</script>
						<%} %>
				</tr>
				<tr>
					<td nowrap>CODICE FISCALE</td>
					<td><input type="text" name="codFiscaleSoggetto"
						readonly="readonly" id="codFiscaleSoggetto" class="required"
						<%=cf%> /></td>
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
					<input type="hidden" value="" id="nazioneResidenzaId" name="nazioneResidenzaId"/>
						
						<%
							NazioniList
									.setJsEvent("onchange=\"sbloccoProvincia('nazioneResidenza','addressLegaleCountry','addressLegaleCity','addressLegaleLine1')\"");
						%>
						<% NazioniList.setJsEvent("onChange=\"abilitaDisabilitaIndirizzo('residenzaRappLegale')\""); %>
						<%=NazioniList.getHtmlSelect("nazioneResidenza", nazione_residenza)%>
						</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red">ATTENZIONE! Posizionarsi sul campo comune per inserire tutto l'indirizzo</font>
					</td>
				</tr>
				<tr>
				<td nowrap>COMUNE RESIDENZA</td>
				<td>
				<!-- Cancellato perchè la gestione deve essere tolto jquery -->
				<!-- <select name="addressLegaleCity" id="addressLegaleCity" class="required"-->
					
					<!--%
					if(StabilimentoOpu.getIdStabilimento()>0 )
					{
					%-->
					<!--option value="<!-%=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getComune()%>" selected="selected"><!--%=StabilimentoOpu.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune() %></option-->
					<!--%} %-->
					
						<!--option value="">SELEZIONA COMUNE</option>
				</select--> 
				
				<%
							String valueT2= "", descrT2 = "";
							try
							{
								valueT2= ( "autoveicolo".equalsIgnoreCase(OrgImport.getTipoDest() )) ? addressMobile.getIndirizzoOpu().getComune() +"":  OrgImport.getIndirizzoOpuRappresentante().getComune() +"";
							} catch(Exception ex){}
							try
							{
								descrT2 = ( "autoveicolo".equalsIgnoreCase(OrgImport.getTipoDest() )) ?   addressMobile.getIndirizzoOpu().getDescrizioneComune()  +"":  OrgImport.getIndirizzoOpuRappresentante().getDescrizioneComune() +"";
							} catch(Exception ex){}
							
							%>
				
				<input type="hidden" name="addressLegaleCityId" id="addressLegaleCityId" />
			
			
					<input value="<%=descrT2%>" size="50" onclick="selezionaIndirizzo('nazioneResidenza','callBackResidenzaRappLegaleImport')" type="text" name="addressLegaleCitta" id="addressLegaleCitta" placeholder="DENOMINAZIONE COMUNE" required readonly="true" />
					<input type="hidden" name="addressLegaleCityTesto" id="addressLegaleCityTesto" />
					<input type="hidden" name="descrT2" id="descrT2" value="<%=descrT2%>"/>
					</td>
			</tr>
			<tr id="addressLegaleCountryTR">
					<td nowrap>PROVINCIA RESIDENZA</td>
					<td>
					<input readonly="true" size="50" type="text" name="addressLegaleCountrySigla" id="addressLegaleCountrySigla" placeholder="DENOMINAZIONE PROVINCIA"/>
			</td>
				</tr>
			
			
			
				<tr>
					<td>INDIRIZZO RESIDENZA</td>
					<td>
						<table class="noborder">
							<tr>
								<input type="hidden" name="toponimoResidenzaId" id="toponimoResidenzaId" />
							
								<td><%=ToponimiList.getHtmlSelect("toponimoResidenza", "VIA")%>
								</td>
								<td><select name="addressLegaleLine1"
									id="addressLegaleLine1" >					
									</td>
									
									<%-- </select> <input type="hidden" name="addressLegaleLine1Testo"
									id="addressLegaleLine1Testo" value="<%=OrgImport.getAddress_legale_rapp() %>" /> --%> 
									
								<td><input type="text" name="civicoResidenza"
									id="civicoResidenza" size="5" placeholder="NUM." maxlength="15"
									 <%=civico_residenza %> readonly></td>
								<td><input type="text" name="capResidenza"
									id="capResidenza" size="4" placeholder="CAP" maxlength="5"
									 <%=cap_residenza %> readonly 
									 onfocus="chkCap(document.getElementById('addressLegaleCity').value, 'capResidenza')"  title="DATI TITOLARE/LEGALE RAPPRESENTANTE: CAP INDIRIZZO RESIDENZA non valido. Tornare indietro e corregere il campo.">
									 <input type="hidden" value="Calcola CAP" id="bottoneCapResidenza"
   							 onclick="calcolaCap(document.getElementById('addressLegaleCity').value, document.getElementById('toponimoResidenza').value, document.getElementById('addressLegaleLine1input').value,  'capResidenza');" />
									 </td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>DOMICILIO DIGITALE<br>(PEC)
					</td>
					<td><input type="text" size="70" name="domicilioDigitalePecSF"
						<%=email%>></td>
				</tr>
			</table>
		</fieldset>
		<br><br><fieldset id="setSedeLegale">
			<legend>DATI SEDE LEGALE</legend>
			<table style="height: 100%; width: 100%">
				<tr>
					<td></td>
					
				</tr>
				<tr>
					<td><label for="nazioneN-2">NAZIONE</label></td>
					<td>
					
						<input type="hidden" value="" name="nazioneSedeLegaleId" id="nazioneSedeLegaleId" />
						<%
							NazioniList
									.setJsEvent("onchange=\"sbloccoProvincia('nazioneSedeLegale','searchcodeIdprovincia','searchcodeIdComune','via'); controlloPiva();\"");
						%>
						<% NazioniList.setJsEvent("onChange=\"abilitaDisabilitaIndirizzo('sedelegale');\""); %>
						<%=NazioniList.getHtmlSelect("nazioneSedeLegale", 106)%></td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red">ATTENZIONE! Posizionarsi sul campo comune per inserire tutto l'indirizzo</font>
					</td>
				</tr>
				<tr>
					<td>COMUNE</td>
					<td><!-- Cancellato perchè la gestione deve essere tolto jquery -->
				<!-- select name="searchcodeIdComune" id="searchcodeIdComune"
					class="required" -->
					<!-- %
								if(StabilimentoOpu.getIdStabilimento()>0)
								{
									% -->
									<!-- option value="<!--%=StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getComune()%>" selected="selected"><!--%=StabilimentoOpu.getOperatore().getSedeLegaleImpresa().getDescrizioneComune() %></option -->
									
									<!-- %
								}% -->
						
				<!-- /select --> 
				
				<input type="hidden" name="searchcodeIdComuneId" id="searchcodeIdComuneId" />
				<input size="50" onclick="selezionaIndirizzo('nazioneSedeLegale','callBackSedeLegaleImport',this.value,true,document.getElementById('asl_short_description').value)" type="text" name="codeIdComune" id="codeIdComune" placeholder="DENOMINAZIONE COMUNE" required readonly="true" value="<%=addressLegale.getIndirizzoOpu().getDescrizioneComune()%>"/>
				<input type="hidden" name="descrT2_sedelegale" id="descrT2_sedelegale" value="<%=addressLegale.getIndirizzoOpu().getDescrizioneComune()%>"/>
				<input type="hidden" name="searchcodeIdComuneTesto"
					id="searchcodeIdComuneTesto" /></td>
			</tr>
			<tr id="searchcodeIdprovinciaTR">
					<td>PROVINCIA</td>
					<td>
					<input readonly="true" size="50" type="text" name="searchcodeIdprovinciaSigla" id="searchcodeIdprovinciaSigla" placeholder="DENOMINAZIONE PROVINCIA"/>
				</td>
				</tr>
			
			
				<tr>
					<td>INDIRIZZO</td>
					<td>
						<table class="noborder">
							<tr>
								<td><%=ToponimiList.getHtmlSelect("toponimoSedeLegale", "VIA")%>
								</td>
								<td>
								<% if (OrgImport.getOrgId()>0){ %><br> <% } %>
								<select name="via" id="via" class="required" size="80">
										<option value="-1" selected="selected"></option>
								</select> <%-- <input type="hidden" name="viaTesto" id="viaTesto" value="<%=addressLegale.getStreetAddressLine1()%>"/> --%>
								<% if (OrgImport.getOrgId()>0){ 
									String via_="";
									if(addressLegale.getIndirizzoOpu().getIdIndirizzo() > 0)
										via_=fixIndirizzo(addressLegale.getIndirizzoOpu().getVia()); 
									else
										via_=fixIndirizzo(addressLegale.getStreetAddressLine1());
								%>
								<br><font size="2" color="RED">Indirizzo importato: </font><font size="2"><%=(!via_.equals("")) ? via_ : "non disponibile" %></font>
								<% } %>
								
								<input type="hidden" name="toponimoSedeLegaleId" id="toponimoSedeLegaleId" />
							
								</td>
								<td><input type="text" name="civicoSedeLegale"
									id="civicoSedeLegale" size="5" placeholder="NUM." maxlength="15" readonly
									></td>
								<td><input type="text" name="presso" id="presso" size="4"
									placeholder="CAP" maxlength="5" readonly
									onfocus="chkCap(document.getElementById('searchcodeIdComune').value, 'presso')"  title="DATI SEDE LEGALE: CAP INDIRIZZO non valido. Tornare indietro e correggere il campo.">
									 <input type="hidden" value="Calcola CAP" id="bottoneCapSedeleg"
   							 onclick="calcolaCap(document.getElementById('searchcodeIdComune').value, document.getElementById('toponimoSedeLegale').value, document.getElementById('viainput').value, 'presso');" />
									</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</fieldset>
	<br><br><fieldset id="dati_stabilimento">
		<legend>DATI STABILIMENTO</legend>
		<table>
		<tr>
				<td>TIPO ATTIVITA</td>
				<td>
				<% 
				TipoAttivita.setRequired(true);
				int caso=0;
				if (OrgImport!=null){
					TipoAttivita.setJsEvent("onchange='mostraDatiStabilimento(this.value)'");
					if (OrgImport.getTipoDest()!=null){
						if (OrgImport.getTipoDest().equals("Autoveicolo")){ caso=2; %>
				<%		}else if(OrgImport.getTipoDest().equals("Es. Commerciale")){ caso=1; %>
							
				<%		}
				
					}}
				
				if(caso<=0)
				{
					caso = 1;
				}
				
				int carattere = -1 ;
				if(OrgImport.getSource()==1)
				{
					carattere = 2;
				}
				else
				{
					carattere=1;
				}
				%>
			
			<%if(caso!=2){ %>
			<%-- disabilitato onchange sul tipo attività --%>
			<select name = "tipoAttivita" id = "tipoAttivita" required="required" onchange="mostraDatiStabilimento(this.value)">
		<option value="" >seleziona</option>
		<%
		for (int i = 0 ; i <TipoAttivita.size(); i++ )
		{
			LookupElement le = (LookupElement)TipoAttivita.get(i);
			if(le.getCode()>0)
			{
			%>
			<option value="<%=le.getCode() %>" <%=caso==le.getCode() ?"selected" : "" %>><%=le.getDescription() %></option>
			
			<%
		}}
		%>
		
		</select>  
		<%}else{ %>
		<input type = "hidden" name = "tipoAttivita" id = "tipoAttivita" value = "2">
		MOBILE
		<%} %> 
		</td>			</tr>
			<tr>
				<td>CARATTERE</td>
				<td>
				<% 
				TipoCarattere.setRequired(true);
				if(caso==0){ 
				%>				
					<%TipoCarattere.setJsEvent("onchange=visualizzaData(this);");%> 
					<%=TipoCarattere.getHtmlSelect("tipoCarattere", carattere)%>
				<% }else if (caso==1) { 
					if(OrgImport.getCessazione()){ %>	
				<%=TipoCarattere.getHtmlSelect("tipoCarattere", carattere)%>
				<script>visualizzaData(document.getElementById('tipoCarattere'));	</script> 
 					<input type="hidden" id="dataInizio_hidden" value="<%=toDateasString(OrgImport.getDataPresentazione())%>"/>
 						<input type="hidden" id="dataFine_hidden" value="<%=toDateasString(OrgImport.getContractEndDate())%>"/>
				<%} else if(OrgImport.getDataPresentazione()!=null && OrgImport.getContractEndDate()!=null){ %>
							<%=TipoCarattere.getHtmlSelect("tipoCarattere", carattere)%>
							<script>visualizzaData(document.getElementById('tipoCarattere')); </script>
					<input type="hidden" id="dataInizio_hidden" value="<%=toDateasString(OrgImport.getDataPresentazione())%>"/>
							<input type="hidden" id="dataFine_hidden" value="<%=toDateasString(OrgImport.getContractEndDate())%>"/>
						<% }else{ %> 
 							<%=TipoCarattere.getHtmlSelect("tipoCarattere",carattere)%> 
 							<script>visualizzaData(document.getElementById('tipoCarattere'));	</script> 
 					<input type="hidden" id="dataInizio_hidden" value="<%=toDateasString(OrgImport.getDataPresentazione())%>"/>
							<% }%> 
				<% }else if (caso==2) { 
						if(OrgImport.getDateI()!=null && OrgImport.getDateF()!=null){ %>
							<%=TipoCarattere.getHtmlSelect("tipoCarattere", carattere)%>
							<script>visualizzaData(document.getElementById('tipoCarattere'));	</script>
						<input type="hidden" id="dataInizio_hidden" value="<%=toDateasString(OrgImport.getDateI())%>"/>
							<input type="hidden" id="dataFine_hidden" value="<%=toDateasString(OrgImport.getDateF())%>"/>
						<% }else{ %>
							<%=TipoCarattere.getHtmlSelect("tipoCarattere", carattere)%>
							<script>visualizzaData(document.getElementById('tipoCarattere'));	</script>
							<input type="hidden" id="dataInizio_hidden" value="<%=toDateasString(OrgImport.getDateI())%>"/>
						<% }
					}%>
				</td>
			</tr>
			<tr id="trDataInizio">
				<td>DATA INIZIO</td>
				<td><input type="text" size="15" name="dataInizioAttivita" 
					id="dataInizio" class="required" placeholder="dd/MM/YYYY">
					
					<img title="<%="ATTENZIONE ! LE ATTIVITA'' CON DATA INIZIO SUPERIORE AL 1 MAGGIO 2015 DEVONO PASARE PER LA SCIA SECONDO DL318/15".toUpperCase() %>" class="masterTooltip" src="images/questionmark.png" width="20"/>
				</td>
			</tr>
			<script> $(function() {	$('#dataInizio').datepick({dateFormat: 'dd/mm/yyyy', maxDate: '01/05/2015',  showOnFocus: false, showTrigger: '#calImg' }); }); </script>
			<tr style="display: none" id="trDataFine">
				<td>DATA FINE</td>
				<td><input type="text" size="15" name="dataFineAttivita" 
					id="dataFine" class="required" placeholder="dd/MM/YYYY"></td>
			</tr>
						<script> $(function() {	$('#dataFine').datepick({dateFormat: 'dd/mm/yyyy',showOnFocus: false, showTrigger: '#calImg', onClose: controlloDate}); }); </script>
			</table>
			<br>
							<% if(caso==0){ ; %>				
				<% }else if (caso==1) { 
 						if(OrgImport.getDataPresentazione()!=null && OrgImport.getContractEndDate()!=null){ %>
							<script> document.getElementById('dataInizio').value=(document.getElementById('dataInizio_hidden').value);  
									 document.getElementById('dataFine').value=(document.getElementById('dataFine_hidden').value); </script> 
 						<% }else{ %> 
 								<script>document.getElementById('dataInizio').value=(document.getElementById('dataInizio_hidden').value); </script>							
						<% }%> 
				<% }else if (caso==2) { 
						if(OrgImport.getDateI()!=null && OrgImport.getDateF()!=null){ %>
					
							<script>document.getElementById('dataInizio').value=(document.getElementById('dataInizio_hidden').value); 
									document.getElementById('dataFine').value=(document.getElementById('dataFine_hidden').value); </script>
						<% }else{ %>
							<script>document.getElementById('dataInizio').value=(document.getElementById('dataInizio_hidden').value); </script>
						<% }
					}%>
		<table id = "datiIndirizzoStab">
				<tr>
					<td>
					<input type="hidden" name="categoria_rischio" id="categoria_rischio" value="<%=(OrgImport.getCategoriaRischio()!=null) ? OrgImport.getCategoriaRischio():""%>"/>
					<input type="hidden" name="prossimo_controllo" id="prossimo_controllo" value="<%=(OrgImport.getProssimoControllo()!=null) ? OrgImport.getProssimoControllo():""%>"/>
					</td>
					
				</tr>
			<tr>
				<td colspan="2">
					<font color="red">ATTENZIONE! Posizionarsi sul campo comune per inserire tutto l'indirizzo</font>
				</td>
			</tr>
			<tr>
				<td>COMUNE</td>
				<td>
		    
		    <input type="hidden" name="idComuneSedeOperativa" id="idComuneSedeOperativa">
			<input value="<%=addressOperativa.getIndirizzoOpu().getDescrizioneComune()%>" size="50" readonly="true" 
<%		
			if(request.getAttribute("pregresso") != null && request.getAttribute("pregresso").equals("true"))
			{
%>
				onclick="selezionaIndirizzo('106','callBackStabImport','',true)"
<%
			}
			else
			{
%>
				onclick="selezionaIndirizzo('106','callBackStabImport',this.value,true,document.getElementById('asl_short_description').value)" 
<%
			}
%>			
			type="text" name="searchcodeIdComuneStabinput" id="searchcodeIdComuneStabinput" placeholder="DENOMINAZIONE COMUNE"/>
		</td>
		
		
			</tr>
			<tr>
				<td>PROVINCIA</td>
				<td><input readonly="true" size="50" type="text" name="searchcodeIdprovinciaStabSigla" id="searchcodeIdprovinciaStabSigla" placeholder="DENOMINAZIONE PROVINCIA"/>
				
					</td>
			</tr>
			<tr>
				<td>INDIRIZZO</td>
				<td>
					<table class="noborder">
						<tr>
							<td><%=ToponimiList.getHtmlSelect("toponimoSedeOperativa", "VIA")%>
							<input type="hidden" name="toponimoSedeOperativaId" id="toponimoSedeOperativaId" />
							</td>
							<td>
							<% if (OrgImport.getOrgId()>0){ %><br> <% } %>
							<select name="viaStab" id="viaStab" class="required" readonly>
									<option value="-1" selected="selected"></option>
							</select> <%-- <input type="hidden" name="viaStabTesto" id="viaStabTesto" value="<%=addressOperativa.getStreetAddressLine1() %>"/> --%>
							<% if (OrgImport.getOrgId()>0){ 
								String viaStab_="";
								if(addressOperativa.getIndirizzoOpu().getIdIndirizzo()>0)
									viaStab_=fixIndirizzo(addressOperativa.getIndirizzoOpu().getVia());
								else
									viaStab_=fixIndirizzo(addressOperativa.getStreetAddressLine1());
							%>
							<br><font size="2" color="RED">Indirizzo importato: </font><font size="2"><%=(!viaStab_.equals("")) ? viaStab_ : "non disponibile"  %></font>
							<% } %>
							</td>
							<td><input type="text" name="civicoSedeOperativa" id="civicoSedeOperativa"
								required="required" placeholder="NUM." size="4" maxlength="7" readonly>
							</td>
							<td><input type="text" size="4" id="capStab" name="capStab"
								maxlength="5" value="" required="required" placeholder="CAP" readonly 
								onfocus="chkCap(document.getElementById('searchcodeIdComuneStab').value, 'capStab')"  title="DATI STABILIMENTO: CAP INDIRIZZO non valido. Tornare indietro e correggere il campo.">
							 <input id="bottoneCapStab" type="hidden" value="Calcola CAP"
   							 onclick="calcolaCap(document.getElementById('searchcodeIdComuneStab').value, document.getElementById('toponimoSedeOperativa').value, document.getElementById('viaStabinput').value, 'capStab');" />
							</td>
							<td>
							<!-- <input id="coord1button" type="button" value="Calcola Coordinate"
   							 onclick="javascript:showCoordinate(getSelectedText('toponimoSedeOperativa')+' '+getSelectedText('viaStab')+', '+ getSelectedText('searchcodeIdComuneStab') + ', '+ getSelectedText('searchcodeIdprovinciaStab') + ' '+ document.forms['example-advanced-form'].capStab.value);" /> 
   							 -->
   							<input id="coord1button" type="button" value="Calcola Coordinate"
   							 onclick="javascript:showCoordinate(getSelectedText('toponimoSedeOperativa')+' '+ document.getElementById('viaStabinput').value +', '+document.getElementById('civicoSedeOperativa').value, document.getElementById('searchcodeIdComuneStabinput').value, document.getElementById('searchcodeIdprovinciaSigla').value, document.getElementById('capStab').value, document.getElementById('latStab'), document.getElementById('longStab'));" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
				<tr>
				<td>COORDINATE</td>
				<td>
				LAT <input type="text" name="latStab" id="latStab" value="" class="required" onChange="controllaCoordinate(this, 'lat')" />
				LONG <input type="text" name="longStab" id="longStab" value="" class="required" onChange="controllaCoordinate(this, 'long')" />							
				</td>
				</tr>
				<tr>
				<td>TELEFONO</td>
				<td><input type="text" name="telefono"></td>
			</tr>
			<tr>
				<td>NOTE</td>
				<td>
				<textarea name="noteStab" cols="100" rows="5" id="noteStab" ></textarea>		<br/><br/><br/><br/><br/><br/>				
				</td>
				</tr>
		</table>
		
			<!--  DATI SEDE MOBILE -->
		<!-- <%//@ include file="campi_mobile_add.jsp"%>-->
		
	</fieldset>
	</fieldset>
<!-- 	<h3>IMPRESA</h3>
	<fieldset>
	</fieldset>
	<h3>STABILIMENTO</h3>-->
	<h3>LINEE DI ATTIVITA'</h3>
<fieldset>
<%
int i = 0; 

	//ArrayList<LineeAttivita> linee_attivita = (ArrayList<LineeAttivita>) request.getAttribute("linee_attivita");
	//LineeAttivita linea_attivita_principale = (LineeAttivita) request.getAttribute("linea_attivita_principale");
	ArrayList<LineeAttivita> linee_attivita = (ArrayList<LineeAttivita>) request.getAttribute("linee_attivita");
%>

<% if (OrgImport.getOrgId()>0 && linee_attivita.size()>0){ 
	LineeAttivita linea_attivita_principale= linee_attivita.get(0);
%>
 

<!-- PARTE CANDIDATI -->
<%@include file="/opu/rankbased/creazione_candidati.jsp" %>
 


<table id="dati_lda_prim" class="one" width="100%">
<colgroup>

<col width="50%">
<tr><th>ATTIVITA ATTUALE</th> <th>ATTIVITA CORRISPONDENTE IN MASTERLIST</th></tr>
<input type="hidden" id="idLineaVecchia" name="idLineaVecchia" value="<%=linea_attivita_principale.getIdLineaVecchiaOriginale() %>"/><%--perchè deve viaggiare l'id vecchia proprio originale (per la parte server sui controlli) --%>

<!-- PARTE CANDIDATI -->
<%@include file="/opu/rankbased/input_nascosti_princ.jsp" %>

<tr><td class="b">
<table><tr><td>
<%--
<%= linea_attivita_principale.getCodice_istat() + " " + linea_attivita_principale.getDescrizione_codice_istat() %><br/>
<%= linea_attivita_principale.getCategoria() + " - " + linea_attivita_principale.getLinea_attivita() %><br><br>
<% if (!linea_attivita_principale.getLinea_attivita().equals(linea_attivita_principale.getAttivita())){ %>
<%= linea_attivita_principale.getAttivita() %><br><br>
<%} %> --%>
<%--<% if(linea_attivita_principale.getMacroarea() != null && !linea_attivita_principale.getMacroarea().equals("")) { --%>

<b>TIPO: </b><%=(linea_attivita_principale.getAggregazione() != null ? linea_attivita_principale.getAggregazione() : "" )
					+ " - " +  (linea_attivita_principale.getAttivita() != null ? linea_attivita_principale.getAttivita() : "") %>
<%-- 
<b>MACROAREA: </b><%=  linea_attivita_principale.getMacroarea() %> <br/>
<b>AGGREGAZIONE: </b><%=  linea_attivita_principale.getAttivita() %> <br/>
<b>ATTIVITA': </b> <%=  linea_attivita_principale.getAttivita() %> --%>

<%--<% } --%>
</td>
</tr></table>
</td><td class="b">	
<table>
 


<!-- PARTE CANDIDATI -->	
<%@include file="/opu/rankbased/tr_conranktable_princ.jsp" %>
 
<tr><td>
<table id = "attprincipale" style="width: 100%;"></table>
</td></tr>
<tr><td>&nbsp;</td></tr>
<tr><td>
<b>DATA INIZIO</b> <input type="text" size="15" name="dataInizioLinea" id="dataInizioLinea" value="" class="required"  placeholder="dd/MM/YYYY" >
<b>DATA FINE</b> <input type="text" size="15" name="dataFineLinea"  id="dataFineLinea" value="" placeholder="dd/MM/YYYY"  >
</td></tr>
<tr><td><b>CUN/APPROVAL NUMBER</b> <input type = "text" id = "codice_nazionale_0" name = "codice_nazionale_0" onchange="verificaEsistenzaCodiceNazionale(document.getElementById('searchcodeIdComuneStabinput').value, document.getElementById('viaStabinput').value, this.value, this)" ></td></tr>
</table>
</td></tr>
</table>
<input type ="hidden" value = "false" id = "validatelp" value = "false">

<%

%>
  	<script>


  	$(document).ready(function(){
  	mostraAttivitaProduttive('attprincipale',1,-1, false,-1);
  	});</script>
  	<br/><br/>
 <table id="dati_lda_sec" class="one">
<col width="50%">
<%
linee_attivita.remove(0);
if(linee_attivita.size()>0){
%>
<tr><th>LINEE AGGIUNTIVE VECCHIE</th> <th>LINEE AGGIUNTIVE NUOVE</th></tr>
<%
}
int indice = 1;
for (LineeAttivita linea: linee_attivita) {
i++;
%>
<tr><td class="b">
<input type="hidden" id="idLineaVecchia<%=i %>" name="idLineaVecchia<%=i %>" value="<%=linea.getIdLineaVecchiaOriginale()%>"/> <%--perchè deve viaggiare come linea vecchia proprio l'originale, per il server (parte dei controlli ufficiali) --%>

<!-- PARTE CANDIDATI -->
<%@include file = "/opu/rankbased/input_nascosti_succ.jsp" %>

	  

<table>
<tr><td>
<%--
<% if (!linea.getLinea_attivita().isEmpty()) { %>
<%=  linea.getCodice_istat() + " " + linea.getDescrizione_codice_istat() %> <br/>
<b>Linea Attivita <%= i %></b> :
<%=  linea.getCategoria() + " - " + linea.getLinea_attivita()  %>
<% } else { %>	
<%= linea.getCodice_istat() + " " + linea.getDescrizione_codice_istat() %> <br/>
<b> Linea Attivita <%= i %></b> :
<%=  linea.getCategoria() %> <%=  linea.getAttivita() %>
<% }  --%>
<%--<%if(linea.getId_attivita_masterlist() != -1 && linea.getId_attivita_masterlist() != 0) { --%>
<br><br>
<b> TIPO: </b><%=(linea.getAggregazione() != null ? linea.getAggregazione() : "" ) + " - " + (linea.getAttivita()!= null ? linea.getAttivita() : "") %>
<%--
<b> MACROAREA <%=i %></b>:  <%= linea.getMacroarea() %> <br/>
<b> AGGREGAZIONE <%=i %></b>: <%=  linea.getAggregazione() %> <br/>
<b> ATTIVITA' <%=i %></b>: <%= linea.getAttivita() %>	
<% } %> --%>
</td></tr>
</table>
</td><td class="b">
<table>
 
<!-- PARTE CANDIDATI -->
<%@include file = "/opu/rankbased/tr_conranktable_succ.jsp" %>
 
  
  
<tr><td>
<table id = "attprincipale<%=i %>" style="width: 100%;"></table>
</td></tr>
<tr><td>&nbsp;</td></tr>
<tr><td>
<b>DATA INIZIO</b> <input type="text" size="15" name="dataInizioLinea<%=i%>" id="dataInizioLinea<%=i %>" value="" class="required" placeholder="dd/MM/YYYY">
<b>DATA FINE</b>   <input type="text" size="15" name="dataFineLinea<%=i%>"  id="dataFineLinea<%=i %>" value="" placeholder="dd/MM/YYYY">
</td>
</tr>
<tr><td><b>CUN/APPROVAL NUMBER</b> <input type = "text" id = "codice_nazionale_<%=i%>" name = "codice_nazionale_<%=i%>" onchange="verificaEsistenzaCodiceNazionale(document.getElementById('searchcodeIdComuneStabinput').value, document.getElementById('viaStabinput').value, this.value, this)" ></td></tr>
</table>
</td></tr>

<script>
mostraAttivitaProduttive('attprincipale<%=indice%>',1,-1, false,-1);

</script>
<%
indice++;
} 
%>
</td></tr>
</table>

 
<input type = "hidden" name = "numeroLinee" id ="numeroLinee"  value="<%=indice %>"> 

<%} else { %>
		<input type="hidden" value="false" id="validatelp" value="false">
		<fieldset>
			<legend>INDICARE IL TIPO DI ATTIVITA</legend>
			<table id="attprincipale" style="width: 100%;">
			</table>
			<table style="width: 100%;">
			<tr id = "trDataInizioLinea">
			<td>DATA INIZIO</td>
			<td>
			<input type="text" size="15" name="dataInizioLinea" id="dataInizioLinea" value="" class="required" placeholder="dd/MM/YYYY" >
			</td>
			</tr>
			<tr id = "trDataFineLinea">
			<td>DATA FINE</td>
			<td>
			<input type="text" size="15" name="dataFineLinea" id="dataFineLinea" value="" placeholder="dd/MM/YYYY"  >
			</td>
			</tr>
			
			<tr><td><b>CUN/APPROVAL NUMBER</b> <input type = "text" id = "codice_nazionale_0" name = "codice_nazionale_0" onchange="verificaEsistenzaCodiceNazionale(document.getElementById('searchcodeIdComuneStabinput').value, document.getElementById('viaStabinput').value, this.value, this)" ></td></tr>
			
			</table>
		</fieldset>
		<br>
		<br>
		<fieldset id="secondarie">
			<legend>INDICARE IL TIPO DI ATTIVITA AGGIUNTIVE</legend>
			<table style="width: 100%;">
				<tr>
					<td width="50%" align="left">Esistono altre Attivita ?</td>
					<td align="left"><input type="button" value="Aggiungi"
						onclick="if(document.getElementById('tipo_impresa')!=null && document.getElementById('tipo_impresa').value=='12') {alert('Impossibile aggiungere ulteriori linee per questo tipo impresa.'); return false; }; aggiungiRiferimentoTabella(<%=newStabilimento.getTipoInserimentoScia()%>)"></td>
				</tr>
			</table>
			<br>
			<br>
		</fieldset>
<%} %>
<br><div align='center'><input type="button" id="preview" onclick="vedi()" value="RIEPILOGO"/>
<div align='center'><input type="button" id="targhe" onclick="checkAltreTarghe()" value="MOSTRA TARGHE" style="display: none"/>

</div>
</fieldset>



	<!-- <h3>ALLEGATI</h3>
	<fieldset>
		<legend>DOCUMENTAZIONE DA ALLEGARE ALLA SCIA</legend>

		<div id="documenti" class="documenti"></div>
	</fieldset>-->
	
	
	
	<h3>FORM</h3>
	<fieldset>
		<legend>SCHEDA STABILIMENTO</legend>
		
		
		<div id = "viewRic"></div>
		
		<div align="right">
			<img src="images/icons/stock_print-16.gif" border="0"
				align="absmiddle" height="16" width="16" /> <input type="button"
				title="Stampa Ricevuta" value="Stampa Ricevuta"
				onClick="openRichiestaPDFOpu('<%=newStabilimento.getIdStabilimento()%>', '-1', '-1', '-1', '15');">
		</div>
	</fieldset>
<input type = "hidden" name = "orgId" id = "orgId" value="<%=OrgImport.getOrgId()%>">
<%
if(OrgImport.getOrgId()>0)
{
%>
<input type = "hidden" name = "importOp" value="si">
<input type = "hidden" name = "riconosciuto" value="<%=OrgImport.isRiconosciuto()%>">
<%} %>
</form>
<div id="dialogsuap"></div>
<div id="dialogsuaplistastab"></div>


<div id="dialogsuaplistaTarghe"></div>


<script>



function getSelectedText(elementId) {
    var elt = document.getElementById(elementId);
    if (elt.selectedIndex == -1)
        return null;
    return elt.options[elt.selectedIndex].text;
}

function closePopup(){
	$('#dialogsuap').dialog('close');
}

function closePopupStab(){
	$('#dialogsuaplistastab').dialog('close');
}

function closePopupTarghe(){
	$('#dialogsuaplistaTarghe').dialog('close');
}



function showPopUp(){
	$('#dialogsuap').dialog('open');
}
	
var resultStab ;

$(function() {
	
	var that;
	$('input[name=partitaIva],input[name=codFiscale]').change( function() {
		tipoImpresa= document.getElementById("tipo_impresa").value;
    	if (tipoImpresa=='5' || tipoImpresa=='6'){
	    	// Get the other input
    		that = $('input[name=partitaIva],input[name=codFiscale],input[name=ragioneSociale]').not($(this));
        	// If the value of this input is not empty, set the required property of the other input to false
        	if($(this).val().length) {
            	that.removeAttr("class");
        	} else {
            	that.attr("class","required");
            	$(this).removeAttr("class");
        	}
  		}
    	if ( $('input[name=partitaIva]').val()!='' || $('input[name=codFiscale]').val()!=''|| $('input[name=ragioneSociale]').val()!=''){
   			loadModalWindowCustom("Verifica Esistenza Impresa. Attendere");
   			$.ajax({
     			type: 'POST',
      			dataType: "json",
      			cache: false,
     			url: 'OpuStab.do?command=VerificaEsistenza',
  		    	data: ''+$('form[name=addstabilimento]').serialize(), 
	     		success: function(msg) {
      				result = msg ;
      				if(msg.codiceErroreSuap =="1"){
     				
	      				showPopUp();
      				}else{
      					if(msg.codiceErroreSuap =="2"){
    		  				var htmlText="" ; //msg[0].erroreSuap;
      						htmlText+='<table border="1" class="pagedlist"><tr><th>Denominazione</th><th>Partita IVa</th><th>Rappresentante Legale</th><th>Sede Legale</th><th></th></tr>'
	      					var jsontext = JSON.stringify(msg);
      						for(i=0;i<msg.listaOperatori.length;i++){
	      						htmlText+="<tr><td>"+msg.listaOperatori[i].ragioneSociale+"</td><td>"+msg.listaOperatori[i].partitaIva+"</td><td>"+msg.listaOperatori[i].rappLegale.nome+" "+msg.listaOperatori[i].rappLegale.cognome+" "+msg.listaOperatori[i].rappLegale.codFiscale+ " "+msg.listaOperatori[i].rappLegale.indirizzo.descrizioneComune+" "+msg.listaOperatori[i].rappLegale.indirizzo.descrizione_provincia+" "+msg.listaOperatori[i].rappLegale.indirizzo.via+"</td><td>"+msg.listaOperatori[i].sedeLegaleImpresa.descrizioneComune+" "+msg.listaOperatori[i].sedeLegaleImpresa.descrizione_provincia+" "+msg.listaOperatori[i].sedeLegaleImpresa.via+"</td><td><input type='button' value='Seleziona' onclick='selezionaImpresa("+i+")'></td></tr>";
    	  					}

      		      			$( "#dialogsuap" ).html(htmlText);
      		      			
      		      			
      		      			
      		      			
      			 			showPopUp();
      					}
      				}
		      		loadModalWindowUnlock();    
      			},
				error: function (err) {
      				alert('ko '+err.responseText);
         		}
  			});
   		}
	});
});

$(function() {
	$('#dataNascita2').datepick({dateFormat: 'dd/mm/yyyy', maxDate: 0, showOnFocus: false, showTrigger: '#calImg'});
	$('#dataInizioLinea').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: '01/05/2015', showOnFocus: false, showTrigger: '#calImg'}); 
	$('#dataFineLinea').datepick({dateFormat: 'dd/mm/yyyy', maxDate: '01/05/2015', showOnFocus: false, showTrigger: '#calImg',  onClose: controlloDate}); 
	var i=1;
	while(document.getElementById('dataInizioLinea'+i)!=null){
		$('#dataInizioLinea'+i).datepick({dateFormat: 'dd/mm/yyyy', maxDate: '01/05/2015',  showOnFocus: false, showTrigger: '#calImg' });
		$('#dataFineLinea'+i).datepick({dateFormat: 'dd/mm/yyyy', maxDate: '01/05/2015',showOnFocus: false, showTrigger: '#calImg', onClose: controlloDate}); 
		i++;
	}
});

function controlloDate(){
	if(document.getElementById(this.id.replace("Fine","Inizio")).value==""){
		alert("ATTENZIONE! Inserire prima la data inizio linea.".toUpperCase());	
		this.value="";
	}else{
		if(!confrontoDate(document.getElementById(this.id.replace("Fine","Inizio")).value,document.getElementById(this.id).value)){
			alert("ATTENZIONE! La data fine deve essere maggiore della data inizio.".toUpperCase());
			this.value="";
		}
	}
}

function confrontoDate(data_iniziale ,data_finale){
	var arr1 = data_iniziale.split("/");
	var arr2 = data_finale.split("/");
	var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
	var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
	var r1 = d1.getTime();
	var r2 = d2.getTime();
	if (r1<=r2) 
		return true;
	else 
		return false;
	}
	
	
	
function vedi(){
	
	if($("input#validatelp").val() == 'false')
	{
		alert("Selezionare tutte le linee di attivita' per accedere alla scheda di riepilogo".toUpperCase());
		return;
	}

	var htmlText="<div align='center'><h3>RIEPILOGO DATI</h3></div><table style='width: 100%'>";
	htmlText+= "<tr><td colspan='2' align='center'><h3><b>DATI IMPRESA</b></h3></td></tr>";
		    
	var e = document.getElementById("tipo_impresa");
	var valore = e.options[e.selectedIndex].text;

	htmlText+="<tr><td nowrap >TIPO IMPRESA</td><td>"+valore+"</td></tr>";
				
	e = document.getElementById("tipo_societa");
	valore="";
	if(e.selectedIndex != null && e.options[e.selectedIndex]!=null)
		valore = e.options[e.selectedIndex].text;

	htmlText+="<tr><td nowrap >TIPO SOCIETA'</td><td>"+valore+"</td></tr>"
	htmlText+="<tr><td nowrap >RAGIONE SOCIALE</td><td>"+$('#ragioneSociale').val()+"</td></tr>";
	htmlText+="<tr><td nowrap >PARTITA IVA</td><td>"+$('#partitaIva').val()+"</td></tr>";
	htmlText+="<tr><td nowrap >CODICE FISCALE<br>IMPRESA</td><td>"+$('#codFiscale').val()+"</td></tr>";
	htmlText+="<tr><td nowrap >DOMICILIO DIGITALE(PEC)</td><td>"+$('#domicilioDigitale').val()+"</td></tr>";

	htmlText+= "<tr><td colspan='2' align='center'><br><h3><b>DATI TITOLARE/LEGALE RAPPRESENTANTE</b></h3></td></tr>";
//    htmlText+="<table style='width: 100%'>";
    htmlText+="<tr><td nowrap >NOME</td><td>"+$('#nome').val()+"</td></tr>";
	htmlText+="<tr><td nowrap >COGNOME</td><td>"+$('#cognome').val()+"</td></tr>";
	htmlText+="<tr><td nowrap >DATA NASCITA</td><td>"+$('#dataNascita2').val()+"</td></tr>";
				
	e = document.getElementById("comuneNascita");
	valore="";
	if(e.selectedIndex != null && e.options[e.selectedIndex]!=null)
		valore = e.options[e.selectedIndex].text;

	htmlText+="<tr><td nowrap >COMUNE NASCITA</td><td>"+valore+"</td></tr>";
	htmlText+="<tr><td nowrap >CODICE FISCALE</td><td>"+$('#codFiscaleSoggetto').val()+"</td></tr>";

	e = document.getElementById("addressLegaleCountryinput");
	
	if(e!=null)
		valore=e.value;
// 	if(e.selectedIndex != null && e.options[e.selectedIndex]!=null)
// 		valore = e.options[e.selectedIndex].text;

	htmlText+="<tr><td nowrap >PROVINCIA RESIDENZA</td><td>"+valore+"</td></tr>";
				
	valore="";
	e = document.getElementById("addressLegaleCitta");
	if(e!=null)
		valore = e.value ;
	
	
// 	if(e.selectedIndex != null && e.options[e.selectedIndex]!=null)
// 		valore = e.options[e.selectedIndex].text;

	htmlText+="<tr><td nowrap >COMUNE RESIDENZA</td><td>"+valore+"</td></tr>";

	var indirizzo="";
	e = document.getElementById("toponimoResidenza");
	valore="";
	if(e!=null && e.selectedIndex != null && e.options[e.selectedIndex]!=null)
		valore = e.options[e.selectedIndex].text;
	indirizzo=valore+" ";
// 	e = document.getElementById("addressLegaleLine1");
	valore="";
// 	if(e.selectedIndex != null && e.options[e.selectedIndex]!=null)
// 		valore = e.options[e.selectedIndex].text;
if(document.getElementById("addressLegaleLine1input")!=null)
	valore = document.getElementById("addressLegaleLine1input").value;
	indirizzo+=valore+", "+$('#civicoResidenza').val()+", "+$('#capResidenza').val();

	htmlText+="<tr><td nowrap >INDIRIZZO RESIDENZA</td><td>"+indirizzo+"</td></tr>";

	
	
	htmlText+= "<tr><td colspan='2' align='center'><br><h3><b>DATI SEDE LEGALE</b></h3></td></tr>";
    //htmlText+="<table style='width: 100%'>";
		   
	e = document.getElementById("searchcodeIdprovinciainput");
	if(e!=null)
		valore=e.value;
// 	if(e.selectedIndex != null && e.options[e.selectedIndex]!=null)
// 		valore = e.options[e.selectedIndex].text;

	htmlText+="<tr><td nowrap >PROVINCIA</td><td>"+valore+"</td></tr>";

	e = document.getElementById("codeIdComune");
	if(e!=null)
		valore=e.value;
// 	if(e.selectedIndex != null && e.options[e.selectedIndex]!=null)
// 		valore = e.options[e.selectedIndex].text;

	htmlText+="<tr><td nowrap >COMUNE</td><td>"+valore+"</td></tr>";

	indirizzo="";
	e = document.getElementById("toponimoSedeLegale");
	valore="";
	if(e!=null && e.selectedIndex != null && e.options[e.selectedIndex]!=null)
		valore = e.options[e.selectedIndex].text;
	indirizzo=valore+" ";
	e = document.getElementById("viainput");
	if(e!=null)
		valore=e.value;
// 	if(e.selectedIndex != null && e.options[e.selectedIndex]!=null)
// 		valore = e.options[e.selectedIndex].text;
	indirizzo+=valore+", "+$('#civicoSedeLegale').val()+", "+$('#presso').val();

	htmlText+="<tr><td nowrap >INDIRIZZO SEDE LEGALE</td><td>"+indirizzo+"</td></tr>";

	htmlText+= "<tr><td colspan='2' align='center'><br><h3><b>DATI STABILIMENTO</b></h3></td></tr>";
    //htmlText+="<table style='width: 100%'>";

	e = document.getElementById("tipoAttivita");
	if(e.selectedIndex!=null &&  e.options[e.selectedIndex]!=null)
		valoreAtt = e.options[e.selectedIndex].text;
	else{
		 valoreAtt = document.getElementById("tipoAttivita").value;
		 if(valoreAtt=='2')
			 valoreAtt ='ATTIVITA MOBILE';
		 else
			 valoreAtt='ATTIVITA FISSA';
	}

	
	htmlText+="<tr><td nowrap >TIPO ATTIVITA</td><td>"+valoreAtt+"</td></tr>";

	e = document.getElementById("tipoCarattere");
	
	valore="";
	if(e.selectedIndex != null && e.options[e.selectedIndex]!=null)
		valore = e.options[e.selectedIndex].text;

	htmlText+="<tr><td nowrap >TIPO CARATTERE</td><td>"+valore+"</td></tr>";
	htmlText+="<tr><td nowrap >DATA INIZIO</td><td>"+$('#dataInizio').val()+"</td></tr>";
	
	if (valoreAtt=="ATTIVITA MOBILE"){
		;//htmlText+="<tr><td colspan='2' align='center'>"+StringTarghe()+"</td></tr>";
	}else{
	
	htmlText+="<tr><td nowrap >DATA FINE</td><td>"+$('#dataFine').val()+"</td></tr>";
				
	e = document.getElementById("searchcodeIdprovinciaStabinput");
	if(e!=null)
		valore=e.value;
// 	if(e.selectedIndex != null && e.options[e.selectedIndex]!=null)
// 		valore = e.options[e.selectedIndex].text;

	htmlText+="<tr><td nowrap >PROVINCIA</td><td>"+valore+"</td></tr>";

	e = document.getElementById("searchcodeIdComuneStabinput");
	if(e!=null)
		valore=e.value;
// 	if(e.selectedIndex != null && e.options[e.selectedIndex]!=null)
// 		valore = e.options[e.selectedIndex].text;

	htmlText+="<tr><td nowrap >COMUNE</td><td>"+valore+"</td></tr>";

	indirizzo="";
	e = document.getElementById("toponimoSedeOperativa");
	
	if(e!=null)
	valore = e.options[e.selectedIndex].text;
	indirizzo=valore+" ";
	e = document.getElementById("viaStabinput");
	if(e!=null)
	valore=e.value;
// 	if(e.selectedIndex != null && e.options[e.selectedIndex]!=null)
// 	valore = e.options[e.selectedIndex].text;
	indirizzo+=valore+", "+$('#civicoSedeOperativa').val()+", "+$('#capStab').val();

	htmlText+="<tr><td nowrap >INDIRIZZO SEDE OPERATIVA</td><td>"+indirizzo+"</td></tr>";
	htmlText+="<tr><td nowrap >COORDINATE</td><td>"+$('#latStab').val()+", "+$('#longStab').val()+"</td></tr>";
	}	
	htmlText+= "<tr><td colspan='2' align='center'><br><h3><b>LINEE DI ATTIVITA'</b></h3></td></tr>";
	
	if(document.getElementById('dati_lda_prim')!=null)
		{
	var lda_pr=document.getElementById('dati_lda_prim').innerHTML;

	lda_pr=lda_pr.replace("type=\"checkbox\"", "type='checkbox' disabled = 'disabled'");
	lda_pr=lda_pr.replace("type=\"radio\"", "type='radio' disabled = 'disabled'");
	lda_pr=lda_pr.replace("type=\"button\"","type='button' disabled='disabled'");
	lda_pr=lda_pr.replace("<select", "<select disabled");
	lda_pr=lda_pr.replace(/<input /g,"<input readonly='readonly' ");
	lda_pr=lda_pr.replace(/class=\"required is-datepick\"/g,"");
	lda_pr=lda_pr.replace(/class=\"is-datepick\"/g,"");
	lda_pr=lda_pr.replace("id=\"dataInizioLinea\" value=\"\"","id='dataInizioLinea' value='"+$('#dataInizioLinea').val()+"'");
	lda_pr=lda_pr.replace("id=\"dataFineLinea\" value=\"\"","id='dataFineLinea' value='"+$('#dataFineLinea').val()+"'");
	lda_pr=lda_pr.replace(/size=\"15\"/g,"size='10'");

	if(document.getElementById('dati_lda_sec')!=null)
	var lda_sec=document.getElementById('dati_lda_sec').innerHTML;
	lda_sec = lda_sec.replace("type=\"checkbox\"", "type=\"checkbox\" disabled=\"disabled\"" );
	lda_sec = lda_sec.replace("type=\"radio\"", "type=\"radio\" disabled=\"disabled\"" );
	lda_sec=lda_sec.replace("type=\"button\"","type='button' disabled='disabled'");
	lda_sec=lda_sec.replace("<select", "<select disabled");
	lda_sec=lda_sec.replace(/class=\"required is-datepick\"/g,"");
	lda_sec=lda_sec.replace(/class=\"is-datepick\"/g,"");
	lda_sec=lda_sec.replace(/<input /g,"<input readonly='readonly' ");
	lda_sec=lda_sec.replace(/value=\"\"/g,"");
	lda_sec=lda_sec.replace(/size=\"15\"/g,"size='10'");
		}
	else{
		var lda_pr=document.getElementById('attprincipale').innerHTML;
		lda_pr=lda_pr.replace("id=\"dataInizioLinea\" value=\"\"","id='dataInizioLinea' value='"+$('#dataInizioLinea').val()+"'");
		lda_pr=lda_pr.replace("id=\"dataFineLinea\" value=\"\"","id='dataFineLinea' value='"+$('#dataFineLinea').val()+"'");
		lda_pr=lda_pr.replace("type=\"button\"","type='button' disabled='disabled'");
		
		
		var i=1 ;
		var lda_sec="";
		while(document.getElementById("attsecondarie"+i)!=null)
			{
		 lda_sec+=document.getElementById("attsecondarie"+i).innerHTML;
		lda_sec=lda_sec.replace("id=\"dataInizioLinea\" value=\"\"","id='dataInizioLinea' value='"+$('#dataInizioLinea').val()+"'");
		lda_sec=lda_sec.replace("id=\"dataFineLinea\" value=\"\"","id='dataFineLinea' value='"+$('#dataFineLinea').val()+"'");
		lda_sec=lda_sec.replace("type=\"button\"","type='button' disabled='disabled'");
		i++;
			}
		}
	
	var ind_linea=1;
	while(document.getElementById('dataInizioLinea'+ind_linea)!=null){
		lda_sec=lda_sec.replace("id=\"dataInizioLinea"+ind_linea+"\"","id='dataInizioLinea"+ind_linea+"' value='"+$('#dataInizioLinea'+ind_linea).val()+"'");
		lda_sec=lda_sec.replace("id=\"dataFineLinea"+ind_linea+"\"","id='dataFineLinea"+ind_linea+"' value='"+$('#dataFineLinea'+ind_linea).val()+"'");
		ind_linea++;	
	}

	
	htmlText+="<tr><td colspan='2'>"+lda_pr+"</td></tr><br>";
	htmlText+="<tr><td colspan='2'><br>"+lda_sec+"</td></tr>";
	htmlText+="</table><br><div align='center'><input type='button' value='Esci' onclick='closePopup()'></div><br>";
	
	$( "#dialogsuap" ).html(htmlText);
	
	//per i campi estesi (li nascondo)
	$("#dialogsuap").find("tr.tr_campi_estesi").css("display","none");
	
	/*e per pulizia trovo l'ultima riga della scelta livello e la nascondo (quella che rimane appes aindicando che bisogna scegliere ancora un livello)*/
	var arrayPs = $("#dialogsuap").find("tr p").toArray();
	for(var ko = 0; ko < arrayPs.length; ko++)
	{
		var textt = $(arrayPs[ko]).text();
		if(/^.*SELEZIONA.*LIVELLO.*$/i.test(textt))
		{
			$(arrayPs[ko]).closest("tr").css("display","none"); /*nascondo la tr a cui appartiene */
		}
	}
	
	
	
	
	/*
	//disabilito i checkbox
	$("#dialogsuap tr.tr_campi_estesi input[type='checkbox']").prop('disabled',true);
	//trasformo tutti gli input (che non siano disabled, e non siano checkbox, quindi basta escludere i disabled)
	//in span
	
	var jqinputs = $("#dialogsuap tr.tr_campi_estesi input:not([disabled])");
	var domsnojqCopy = jqinputs.toArray().slice(0);
	
	for(var f = 0; f< domsnojqCopy.length; f++)
	{
// 		var elnojq_val = domsnojqCopy[f].value;
		//sostituisco quell'elemento con uno span che contiene quel valore testuale
		$(domsnojqCopy[f]).replaceWith( $("<span></span>").text("CIAO"+elnojq_val) );
	}
	 */
	
	showPopUp();
	/* DATI SEDE MOBILE */
}


function StringTarghe(){
	var htmlTarghe="<br><table border='1' width='100%'>";
	var e,valore,k=0;
	while(true){
		if($('#mobile_targa'+k).val()=="")
			break;
		htmlTarghe+="<tr><td><b>"+(k+1)+". Targa:</b> "+$('#mobile_targa'+k).val()+"</td>";
		e = document.getElementById("mobile_tipoautoveicolo"+k);
		valore = e.options[e.selectedIndex].text;
		htmlTarghe+="<td><b>Tipo autoveicolo:</b> "+valore+"</td></tr>";	
		k++;	
	}
	return htmlTarghe+"</table><br>";
}


jQuery(function() {

	var prima_selezione_tipo_impresa=0;

	$('#tipo_impresa').change( function() {
		
		if(prima_selezione_tipo_impresa==0){
			prima_selezione_tipo_impresa++;
			}else{
		
		document.getElementById('codeIdComune').value= "" ;
		document.getElementById('searchcodeIdComuneStabinput').value= "" ;
	  document.getElementById('addressLegaleCitta').value= "" ;
	    
	  	document.getElementById('addressLegaleCountryinput').value= "" ;
	  	document.getElementById('searchcodeIdprovinciainput').value= "" ;
	  	document.getElementById('searchcodeIdprovinciaStabinput').value= "" ;
	  }
		});
		
// 	$('#tipoAttivita').change( function() {
// 		document.getElementById('codeIdComune').value= "" ;
// 		document.getElementById('searchcodeIdComuneStabinput').value= "" ;
// 	  document.getElementById('addressLegaleCityinput').value= "" ;
	    
// 	  	document.getElementById('addressLegaleCountryinput').value= "" ;
// 	  	document.getElementById('searchcodeIdprovinciainput').value= "" ;
// 	  	document.getElementById('searchcodeIdprovinciaStabinput').value= "" ;
// 		});
		
	});
	
 
</script>


<script type="text/javascript">
	document.getElementById('toponimoResidenza').disabled=true;
	document.getElementById('toponimoSedeLegale').disabled=true;
	document.getElementById('toponimoSedeOperativa').disabled=true;
	
	function abilitaDisabilitaIndirizzo(tipo)
	{
		if(tipo=='sedelegale')
		{
			var abilitare = document.getElementById('toponimoSedeLegale').disabled==true;
			if(abilitare && document.getElementById('nazioneSedeLegale').value!='106')
			{
				document.getElementById('toponimoSedeLegale').disabled=!abilitare;
				document.getElementById('civicoSedeLegale').readOnly=!abilitare;
				document.getElementById('presso').readOnly=!abilitare;
				document.getElementById('viainput').readOnly=!abilitare;
				document.getElementById('codeIdComune').readOnly=!abilitare;
			}
			else if(!abilitare && document.getElementById('nazioneSedeLegale').value=='106')
			{
				document.getElementById('toponimoSedeLegale').disabled=!abilitare;
				document.getElementById('civicoSedeLegale').readOnly=!abilitare;
				document.getElementById('presso').readOnly=!abilitare;
				document.getElementById('viainput').readOnly=!abilitare;
				document.getElementById('codeIdComune').readOnly=!abilitare;
			}
			document.getElementById('toponimoSedeLegale').value=-1;
			document.getElementById('civicoSedeLegale').value='';
			document.getElementById('presso').value='';
			document.getElementById('viainput').value='';
			document.getElementById('searchcodeIdprovinciaSigla').value='';
			document.getElementById('codeIdComune').value='';
		}
		else if(tipo=='residenzaRappLegale')
		{
			var abilitare = document.getElementById('toponimoResidenza').disabled==true;
			if(abilitare && document.getElementById('nazioneResidenza').value!='106')
			{
				document.getElementById('toponimoResidenza').disabled=!abilitare;
				document.getElementById('civicoResidenza').readOnly=!abilitare;
				document.getElementById('capResidenza').readOnly=!abilitare;
				document.getElementById('addressLegaleLine1input').readOnly=!abilitare;
				document.getElementById('addressLegaleCitta').readOnly=!abilitare;
			}
			else if(!abilitare && document.getElementById('nazioneResidenza').value=='106')
			{
				document.getElementById('toponimoResidenza').disabled=!abilitare;
				document.getElementById('civicoResidenza').readOnly=!abilitare;
				document.getElementById('capResidenza').readOnly=!abilitare;
				document.getElementById('addressLegaleLine1input').readOnly=!abilitare;
				document.getElementById('addressLegaleCitta').readOnly=!abilitare;
			}
			document.getElementById('toponimoResidenza').value=-1;
			document.getElementById('civicoResidenza').value='';
			document.getElementById('capResidenza').value='';
			document.getElementById('addressLegaleLine1input').value='';
			document.getElementById('addressLegaleCountrySigla').value='';
			document.getElementById('addressLegaleCitta').value='';
			
		}
	}
</script>

 
 
