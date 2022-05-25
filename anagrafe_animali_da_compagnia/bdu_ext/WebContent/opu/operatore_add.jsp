<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  -  
  - Version: $Id: accounts_add.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%> 
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*"%>

<jsp:useBean id="Titolo" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<jsp:useBean id="Operatore"
	class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="soggettoAdded"
	class="org.aspcfs.modules.opu.base.SoggettoFisico" scope="request" />
<!-- soggetto inserito -->
<jsp:useBean id="indirizzoAdded"
	class="org.aspcfs.modules.opu.base.Indirizzo" scope="request" />
<!-- sede inserita -->
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<!-- ASL -->
<jsp:useBean id="IterList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="ErroreGisaOffline" class="java.lang.String" scope="request"/>
<!-- ITER AUTORIZZATIVI -->
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="provinciaAsl"
	class="org.aspcfs.modules.accounts.base.Provincia" scope="request" />
<jsp:useBean id="LineaProduttivaScelta"
	class="org.aspcfs.modules.opu.base.LineaProduttiva" scope="request" />
<jsp:useBean id="stabilimento"
	class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
	
<%@ include file="../initPage.jsp"%>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();

var cal17 = new CalendarPopup(); //qui c'è un controllo sulla maggiore età
cal17.showYearNavigation();
cal17.showYearNavigationInput();
var anni18fa = new Date();
anni18fa.setFullYear(anni18fa.getFullYear()-18);
cal17.addDisabledDates(formatDate(anni18fa,"yyyy-MM-dd"), null);
</SCRIPT>

<link rel="stylesheet"	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>

<style>
.ui-combobox {
	position: relative;
	display: inline-block;
}

.ui-combobox-toggle {
	position: absolute;
	top: 0;
	bottom: 0;
	margin-left: -1px;
	padding: 0;
	/* adjust styles for IE 6/7 */ *
	height: 1.7em; *
	top: 0.1em;
}

.ui-combobox-input {
	margin: 0;
	padding: 0.3em;
}
</style>
<script type="text/javascript">


function verificaSoggetto()
{

	var cf = '';
	if(document.getElementById('codFiscaleSoggetto')!=null)
		cf = document.getElementById('codFiscaleSoggetto').value ;

	if (cf!='')
{

		PopolaCombo.verificaAslSoggetto(cf,verificaSoggettoCallback);

}
	else
{

		checkForm();

}

	}

function verificaSoggettoCallback(value)
{

	
	if (value != null && value.idSoggetto>0)
	{
	
	nomeEsistente = value.nome;
	
	cognomeEsistente = value.cognome;
	sessoEsistente = value.sesso;
	dataNascitaEsistente = value.dataNascitaString;
	comuneNascitaEsistente = value.comuneNascita;
	provinciaNascitaEsistente = value.provinciaNascita;
	documentoIdentitaEsistente = value.documentoIdentita;
	telefono1Esistente = value.telefono1;
	telefono2Esistente = value.telefono2;
	emailEsistente = value.email;
	faxEsistente = value.fax;
	comuneResidenzaEsistente = value.indirizzo.descrizioneComune ; 
	provinciaResidenzaEsistente = value.indirizzo.provincia ; 
	indirizzoResidenzaEsistente = value.indirizzo.via ; 
	descrizioneComune = value.indirizzo.descrizioneComune ;
	idAsl = value.idAsl ;
	descrAsl = value.descrAsl ;
	
	
	nome = document.getElementById('nome').value ;
	cognome = document.getElementById('cognome').value ;
	if (document.getElementById('sesso1').checked)
		sesso = document.getElementById('sesso1').value ;
	else
		sesso = document.getElementById('sesso2').value ;
	dataNascita = document.getElementById('dataNascita').value ;
	comuneNascita = document.getElementById('comuneNascita').value ;
	
	provinciaNascita = document.getElementById('provinciaNascita').value ;
	documento = document.getElementById('documentoIdentita').value ;
	tel1 = document.getElementById('telefono1').value ;
	tel2 = document.getElementById('telefono2').value ;
	fax = document.getElementById('fax').value ;
	provinciaNascita = document.getElementById('email').value ;
	
	comuneResidenza = document.getElementById('addressLegaleCityTesto').value ;
	provinciaResidenza = document.getElementById('provinciaTesto').value ;
	indirizzoResidenza = document.getElementById('addressLegaleLine1Testo').value ;


	if (nomeEsistente !=nome || cognomeEsistente != cognome || sessoEsistente != sesso ||
			dataNascitaEsistente != dataNascita || comuneNascitaEsistente.toUpperCase()  != comuneNascita.toUpperCase()  ||
			documentoIdentitaEsistente!= documento || telefono1Esistente != tel1 || telefono2Esistente != tel2 ||
			faxEsistente != fax || comuneResidenzaEsistente.toUpperCase() !=comuneResidenza.toUpperCase() || indirizzoResidenzaEsistente!=indirizzoResidenza  )
	{

<%
		boolean soloPrivato2 = request.getAttribute("soloPrivato")!=null && ((Boolean)request.getAttribute("soloPrivato"));
		if(!soloPrivato2)
		{
%>
        //document.getElementById('idAsl') è l'asl dell'utente
        //idAsl è l'asl del soggetto fisico presente in bdu con cf uguale a quello inserito dall'utente
        
       
		if (document.getElementById('idAsl').value != '' &&  
				document.getElementById('idAsl').value != '-1' && 
				document.getElementById('idAsl').value!= idAsl && value.indirizzo.comune!='-1') 
		{
			document.getElementById("intestazione").innerHTML="ATTENZIONE : PER IL CF INSERITO ESISTE UN SOGGETTO ANAGRAFATO PRESSO L'ASL "+descrAsl+". CONTRATTARE L'HELP DESK" ;
		}
		else
		{
			document.getElementById("intestazione").innerHTML="ATTENZIONE : PER IL CF INSERITO ESISTE UN SOGGETTO ANAGRAFATO CON I SEGUENTI DATI. VUOI SOVRASCRIVERE ?" ;
		}
	document.getElementById('nomeSoggetto').innerHTML = nomeEsistente ; 
 	document.getElementById('cognomeSoggetto').innerHTML =cognomeEsistente ;

 		document.getElementById('sessoSoggetto').innerHTML = sessoEsistente ;
 		document.getElementById('dataNascitaSoggetto').innerHTML =dataNascitaEsistente ;
		document.getElementById('comuneNascitaSoggetto').innerHTML =comuneNascitaEsistente ; 
		document.getElementById('documentoSoggetto').innerHTML =documentoIdentitaEsistente ;
		document.getElementById('provinciaNascitaSoggetto').innerHTML =provinciaNascitaEsistente ;
		document.getElementById('telefono1Soggetto').innerHTML =telefono1Esistente ;
		document.getElementById('telefono2Soggetto').innerHTML =telefono2Esistente ;
		document.getElementById('mailSoggetto').innerHTML = emailEsistente;
		document.getElementById('faxSoggetto').innerHTML = faxEsistente;
		document.getElementById('comuneResidenzaSoggetto').innerHTML = descrizioneComune ;
		document.getElementById('provinciaResidenzaSoggetto').innerHTML = value.indirizzo.descrizione_provincia ;
		document.getElementById('indirizzoResidenzaSoggetto').innerHTML =indirizzoResidenzaEsistente ;	


		if (document.getElementById('idAsl').value != '' && document.getElementById('idAsl').value != '-1' && document.getElementById('idAsl').value!= idAsl  && value.indirizzo.comune!='-1')
		{
			document.getElementById("azione").style.display="none";
		}
		else
		{
			document.getElementById("azione").style.display="";		
		}
		
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();
		//Set heigth and width to mask to fill up the whole screen
		$('#mask').css({'width':maskWidth,'height':maskHeight});
		$('#mask').fadeIn(1000);	
		$('#mask').fadeTo("slow",0.8);	
		$('#mask').show();
		//Get the window height and width
		var winH = $(window).height();
		var winW = $(window).width();

		$('#dialog4').css('top',  winH/2-$('#dialog4').height()/2);
		$('#dialog4').css('left', winW/2-$('#dialog4').width()/2);
		$('#dialog4').fadeIn(2000);  
<%
		}
		else
		{
%>
			alert("Per il codice fiscale inserito esiste un soggetto anagrafato con gli stessi dati. Selezionarlo dal link 'Seleziona nuovo proprietario'");
<%
		}
%>
	}
	else
	{
		checkForm();

		}    
		 										
	}
	else
	{
		checkForm();

	}
	

}

function checkForm()


{
	document.forms[0].doContinue.value="true";
	msg = "Attenzione Controllare di aver compilato i seguenti campi\n" ;

	$('.todisable').attr('disabled', false);


	
	
	$('#idLineaProduttiva').attr('disabled', false); //riabilito la select idLineProduttiva che sarà disabilitata in caso di utente vet

	if(document.getElementById('idLineaProduttiva').value!= '5')
	{
	
    if (document.forms[0].provinciaTesto.value == '') {
    	 document.forms[0].doContinue.value="false";
			msg+= "- Inserire la provincia di residenza del soggetto \n" ;
        }


    if (document.forms[0].addressLegaleCityTesto.value == '' ) {
   	 document.forms[0].doContinue.value="false";
			msg+= "- Inserire il comune di residenza del soggetto \n" ;
       }
    if (document.forms[0].addressLegaleLine1Testo.value == '') {
	   	 document.forms[0].doContinue.value="false";
   			msg+= "- Inserire l'indirizzo di residenza del soggetto \n" ;
          }
    
    if (document.forms[0].cap.value == '') 
    {
	   	document.forms[0].doContinue.value="false";
   		msg+= "- Inserire il cap della residenza del soggetto \n" ;
    }
    
    
    if (document.forms[0].cap.value == '80100') 
    {
	   	document.forms[0].doContinue.value="false";
   		msg+= "- Il valore del cap della residenza del soggetto non può essere 80100\n" ;
    }
	}

    
	
	if(document.getElementById('bdu')!=null && document.getElementById('bdu').value=='no')
	{ 
		if (document.getElementById('idLineaProduttiva').value != '3'&& document.getElementById('idLineaProduttiva').value != '7')
		{
		}
		else
		{
			
		document.forms[0].ragioneSociale.value='Sindaco del Comune di '+ document.getElementById('addressLegaleCityTesto').value ;
		document.forms[0].nome.value='Sindaco del Comune di '+ document.getElementById('addressLegaleCityTesto').value ;
		

		}
			var codFisc = document.forms[0].codFiscaleSoggetto.value;
		

		//alert(codFisc);
		document.forms[0].codFiscale.value=codFisc;
		document.forms[0].partitaIva.value=codFisc;
		//alert(document.forms[0].ragioneSociale.value);
		//alert(strUser);
		//alert('eccomi');
	}
	
		else
		{
			if(document.forms[0].codFiscale.value.length > 0 && document.forms[0].codFiscale.value.length!=16)
			{
				document.forms[0].doContinue.value="false";
				msg+= "- Campo codice fiscale azienda non corretto (Lunghezza 16 caratteri) \n" ;
			}
		if(document.forms[0].ragioneSociale.value=='')
		{
			document.forms[0].doContinue.value="false";
			msg+= "- Inserire la ragione sociale \n" ;
		}

		if(document.forms[0].partitaIva.value=='' ||  document.forms[0].partitaIva.value.length!=11)
		{
			document.forms[0].doContinue.value="false";
			msg+= "- Inserire la partita iva in un formato corretto (lunghezza 11 caratteri) \n" ;
		}
		}
	

	//alert(document.getElementById('bdu').value);

	if(document.getElementById('bdu')!=null && document.getElementById('bdu').value=='no')
	{
	 /*if ($("#addressLegaleCountry").length > 0){
			document.forms[0].searchcodeIdprovincia.options[document.forms[0].searchcodeIdprovincia.options.length]=new Option(document.forms[0].addressLegaleCountry.value, document.forms[0].addressLegaleCountry.value);
			document.forms[0].searchcodeIdprovincia.value=document.forms[0].addressLegaleCountry.value;
	 }else{
			document.forms[0].searchcodeIdprovincia.options[document.forms[0].searchcodeIdprovincia.options.length]=new Option(document.forms[0].addressLegaleCountryAsl.value, document.forms[0].addressLegaleCountryAsl.value);
			document.forms[0].searchcodeIdprovincia.value=document.forms[0].addressLegaleCountryAsl.value;
	 }
		document.forms[0].searchcodeIdComune.options[document.forms[0].searchcodeIdComune.options.length]=new Option(document.forms[0].addressLegaleCity.value, document.forms[0].addressLegaleCity.value);
		document.forms[0].searchcodeIdComune.value=document.forms[0].addressLegaleCity.value;
		document.forms[0].via.options[document.forms[0].via.options.length]=new Option(document.forms[0].addressLegaleLine1.value, document.forms[0].addressLegaleLine1.value);
		document.forms[0].via.value=document.forms[0].addressLegaleLine1.value;
		document.forms[0].viaTesto.value=document.forms[0].addressLegaleLine1Testo.value;*/
		
		//alert(document.forms[0].addressLegaleCountry.value);
		//alert(document.forms[0].searchcodeIdprovincia.value);
	}
	else
	{

		if(document.forms[0].searchcodeIdprovincia != null && document.forms[0].searchcodeIdprovincia.value=='')
		{
			document.forms[0].doContinue.value="false";
			msg+= "- Inserire la provincia della sede legale \n" ;
		}
		else if(document.forms[0].searchcodeIdprovinciaSedeLegale != null && document.forms[0].searchcodeIdprovinciaSedeLegale.value=='')
		{
			document.forms[0].doContinue.value="false";
			msg+= "- Inserire la provincia della sede legale \n" ;
		}
		if(document.forms[0].searchcodeIdComune != null && document.forms[0].searchcodeIdComune.value=='')
		{
			document.forms[0].doContinue.value="false";
			msg+= "- Inserire il comune della sede legale \n" ;
		}
		else if (document.forms[0].searchcodeIdComuneSedeLegale != null && document.forms[0].searchcodeIdComuneSedeLegale.value=='' )
		{
			document.forms[0].doContinue.value="false";
			msg+= "- Inserire il comune della sede legale \n" ;
		}
	if(document.forms[0].viaTestoSedeLegale!=null)
	{
		if(document.forms[0].viaTestoSedeLegale.value == '')
		{
			document.forms[0].doContinue.value="false";
			msg+= "- Inserire l'indirizzo della sede legale \n" ;
		}
	}
	 if (document.forms[0].presso.value == '') 
	    {
		   	document.forms[0].doContinue.value="false";
	   		msg+= "- Inserire il cap della sede legale \n" ;
	    }
	}

	if (document.getElementById('idLineaProduttiva').value != '5' && document.getElementById('idLineaProduttiva').value != '3'&& document.getElementById('idLineaProduttiva').value != '7')
	{
	if((document.forms[0].codFiscaleSoggetto.value==''  && document.forms[0].codFiscaleSoggetto.value.length!=16) && ((document.getElementById('estero')!=null && document.getElementById('estero').checked == false ) || document.getElementById('estero')==null))
	{
		document.forms[0].doContinue.value="false";
		msg+= "- Inserire il codice fiscale del soggetto in un formato corretto (lunghezza 16 caratteri) \n" ;
	}
	else
	{
	if (document.getElementById('estero') != null && document.getElementById('estero').checked == true && document.forms[0].codFiscaleSoggetto.value=='' ) 
	{
		document.forms[0].doContinue.value="false";
		msg+= "- Campo codice fiscale soggetto richiesto\n" ;
	}
	}
	if(document.forms[0].nome.value=='')
	{
		document.forms[0].doContinue.value="false";
		msg+= "- Inserire il nome del soggetto \n" ;
	}

	if(document.forms[0].cognome.value=='')
	{
		document.forms[0].doContinue.value="false";
		msg+= "- Inserire il cognome del soggetto \n" ;
	}
	
	if(document.forms[0].telefono1.value=='' && (document.forms[0].idLineaProduttiva.value ==<%=LineaProduttivaScelta.idAggregazioneColonia%> || document.forms[0].idLineaProduttiva.value ==<%=LineaProduttivaScelta.idAggregazionePrivato%> || document.forms[0].idLineaProduttiva.value ==<%=LineaProduttivaScelta.idAggregazioneSindaco%> || document.forms[0].idLineaProduttiva.value ==<%=LineaProduttivaScelta.idAggregazioneSindacoFR%>))
	{
		//if (parseInt(document.getElementById('codFiscaleSoggetto').value)<0 ||document.getElementById('codFiscaleSoggetto').value=='' )
		//{
		document.forms[0].doContinue.value="false";
		msg+= "- Telefono Principale soggetto fisico richiesto \n" ;
	//	}
	}
	}
	

	if(document.forms[0].doContinue.value=="false")
	{
		$('#mask').hide();
		$('.window').hide();
			alert(msg);
	}
	else
	{
		loadModalWindow();
		document.forms[0].doContinue.value="true";
		document.forms[0].submit();
	}
		

}

function pulisciCampo(input)
{
	//Inserire le prime 4 lettere
	
	if(input.value=='Inserire le prime 4 lettere')
	{
		input.value='';
	}
}
function ripristinaCampo(input)
{
	if(input.value=='')
	{
		input.value='Inserire le prime 4 lettere';
	}
}
var arrayItem ;    	
var text = '' ;
var trovato = false ;
    (function( $ ) {
        $.widget( "ui.combobox", {
            _create: function() {
                var input,
                    that = this,
                    select = this.element.hide(),
                    selected = select.children( ":selected" ),
                    value = selected.val() ? selected.text() : "",
                    wrapper = this.wrapper = $( "<span>" )
                        .addClass( "ui-combobox" )
                        .insertAfter( select );
 
                function removeIfInvalid(element) {
                    var value = $( element ).val(),
                        matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( value ) + "$", "i" ),
                        valid = false;
                   
                    select.children( "option" ).each(function() {

                        //if ( $( this ).text().match( matcher.test(text) ) ) {
                        if ( matcher.test(this.text) ) {
                            
                            this.selected = valid = true;
                            return false;
                        }
                    });
                    if ( valid==false ) {
                        // remove invalid value, as it didn't match anything
                        if(select[0].id =='via' || select[0].id =='addressLegaleLine1'|| select[0].id =='codFiscaleSoggetto')
                        {	
                        if(confirm("ATTENZIONE ! La voce selezionata non esiste. Sicuro di inserire ?")==false)
                        {
                        	
                        $( element )
                            .val( "" )
                            .attr( "title", value + " nessuna voce trovata" )
                            .tooltip( "open" );
                        select.val( "" );
                        setTimeout(function() {
                            input.tooltip( "close" ).attr( "title", "" );
                        }, 2500 );
                        input.data( "autocomplete" ).term = "";
						select.append('<option value=-1 selected>Seleziona Voce</option>');
                     		//document.getElementById('sesso1').checked=true ;
                  			//document.getElementById('sesso2').checked=false ;
                  			
                  			
	
                        return false;
                        }
                        else
                        {
                        	$('.todisable').attr('disabled', false);
                       	 select[0].value='-1';
                   		document.getElementById(select[0].id+'Testo').value = value ;
							//sovrascrittura casella indirizzo
                   		select.append('<option value=-1 selected>'+value+'</option>');
                    		//document.getElementById('sesso1').checked=false ;
                 			//document.getElementById('sesso2').checked=true ;

                      					
                            	return true ;
                        }
                            
                        
                    }
                    
                    else
                    {
                    	$( element )
                        .val( "" )
                        .attr( "title", value + " nessuna voce trovata" )
                        .tooltip( "open" );
                    select.val( "" );
                    setTimeout(function() {
                        input.tooltip( "close" ).attr( "title", "" );
                    }, 2500 );
                    input.data( "autocomplete" ).term = "";
					select.append('<option value=-1 selected >Seleziona Voce</option>');
                    
                    return false;
                     }
                    }else{
						

                    }
                }

                var input1;
                if (select[0].id != 'codFiscaleSoggetto'){
                     input1 = $( "<input id='"+select[0].id+"input' class='todisable' onmouseover='pulisciCampo(this)' onclick='pulisciCampo(this)' onmouseout='ripristinaCampo(this)'>" );
                } else{
                    	 input1 = $( "<input id='"+select[0].id+"input' onclick='pulisciCampo(this)' onmouseout='ripristinaCampo(this)'>" );
                    }

                //alert(document.getElementById(select[0].id+"input"));
                $(select[0].id+"input").onclick=(function (event) {
                 	
             		
             });

               
                input = input1.appendTo( wrapper )
                    .val( value )
                    .attr( "title", "" )
                    .addClass( "ui-state-default ui-combobox-input" )
                    .autocomplete({
                        delay: 0,
                        minLength: 3,
                       
                        source:  function( request, response ) {
                    	idprovincia = '-1';
                    	idcomune = '-1';
                    	inregione = 'si';
                    	
                    	 if(select[0].id =='searchcodeIdprovincia' || select[0].id =='searchcodeIdComune' || select[0].id =='via' )
                         {
                    		
                            

                    		 if ($("#searchcodeIdprovincia").length > 0){
                    			idprovincia = document.getElementById("searchcodeIdprovincia").value ;
                    		 }else{
                    			 idprovincia = document.getElementById("searchcodeIdprovinciaAsl").value ;
                    		 }
                    		
                        		
                    		 idcomune =  document.getElementById("searchcodeIdComune").value;
                    		 inregione= document.forms[0].inregioneSedeLegale.value;
                    		// alert(inregione);

                         }
                    	 else
                    	 {

                    		 if(select[0].id =='addressLegaleCity' || select[0].id =='provinciaTesto' || select[0].id =='addressLegaleLine1' )
                             {
                    			 

                    			 if ($("#provinciaTesto").length > 0){
                    				 idprovincia =document.getElementById("provinciaTesto").value ;
                         		 }else{
                         			 idprovincia =document.getElementById("addressLegaleCountryAsl").value ;
                         		 }
                         		
                    		
                    		 idcomune =  document.getElementById("addressLegaleCity").value;
                    		 var idLineaProduttiva = document.getElementById("idLineaProduttiva").value;
                    	//	alert(idLineaProduttiva);
                    		 if (idLineaProduttiva == 7){ // 7 - Sindaco fuori regione
                    		 		inregione = 'no';
                    		 }else{
                    			var inregione = document.getElementById("inregione").value;
                    			// alert(inregione);
                    		 }
                             }
                        	 }
                      	
                            $.ajax({

                               
                          	
                          		  url:  "./ServletComuni?nome="+request.term+"&estero=<%=request.getAttribute("estero")%>&combo="+select[0].id+"&idProvincia="+idprovincia+"&idComune="+idcomune+"&inRegione="+inregione+"&idAsl=<%=(LineaProduttivaScelta.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneColonia) ? User.getSiteId() : -1%>",
                               
                               
                                dataType: "json",
                                data: {
                                    style: "full",
                                    maxRows: 12,
                                    name_startsWith: request.term
                                },
                                success:function( data ) {
                                	arrayItem = new Array() ; 

                                	if(select[0].id=='via' ||select[0].id== 'addressLegaleLine1' )
                                	{
    								response( $.map( data, function( item ) {
											
    									select.append('<option value='+item.codicevia+'>'+item.descrizionevia+'</option>');
    									
                                        return {
                                            label: item.descrizionevia.replace(
                                                    new RegExp(
                                                            "(?![^&;]+;)(?!<[^<>]*)(" +
                                                            $.ui.autocomplete.escapeRegex(request.term) +
                                                            ")(?![^<>]*>)(?![^&;]+;)", "gi"
                                                        ),  "<strong>$1</strong>" ),
                                            value: item.descrizionevia ,
                                            nome: item.nome,
                                            cap: item.cap,
                                            latitudine:item.latitudine,
                                            longitudine:item.longitudine
                                            
                                        }
                                    }));
                                	}
                                	else
                                	{
                                    	
                                		if(select[0].id=='codFiscaleSoggetto')
                                    	{
        								response( $.map( data, function( item ) {
        									//alert(item);
        									select.append('<option value='+item.codice+'>'+item.descrizione+'</option>');
                                            return {
                                                label: item.descrizione.replace(
                                                        new RegExp(
                                                                "(?![^&;]+;)(?!<[^<>]*)(" +
                                                                $.ui.autocomplete.escapeRegex(request.term) +
                                                                ")(?![^<>]*>)(?![^&;]+;)", "gi"
                                                            ), "<strong>$1</strong>" ),
                                                value: item.descrizione ,
                                                nome: item.nome,
                                                cognome: item.cognome ,
                                                documentoIdentita: item.documentoIdentita, //TESTING 
                                                datanascita: item.dataNascitaString,
                                                sesso: item.sesso,
                                                comunenascita: item.comuneNascita,
                                                provincianascita: item.provinciaNascita,

                                                indirizzoresidenza: item.descrizionevia,
                                                idindirizzo:item.codicevia,
                                                comuneresidenza: item.descrizioneComune,
                                                idcomuneresidenza:item.comune,
                                                provinciaresidenza:item.descrizione_provincia,
                                                capresidenza:item.cap,
                                                idvia:item.codice,
                                                email:item.email,
                                                tel1:item.telefono1,
                                                tel2:item.telefono2,
                                                fax:item.fax,
                                                codiceprovincia:item.idProvincia

                                               
                                                
                                                
                                            }
                                        }));
        								
                                    	}
                                		else
                                		{
											if (select[0].id=='addressLegaleCity')
											{
												response( $.map( data, function( item ) {
        											
													item.descrizione=item.descrizione.replace('&agrave;', "à" )
													.replace('&egrave;', "è" )
													.replace('&igrave;', "ì" )
													.replace('&ograve;', "ò" )
													.replace('&ugrave;', "ù" );
	            									select.append('<option value='+item.codice+'>'+item.descrizione+'</option>');
	                                                return {
	                                                    label: item.descrizione.replace(
	                                                            new RegExp(
	                                                                    "(?![^&;]+;)(?!<[^<>]*)(" +
	                                                                    $.ui.autocomplete.escapeRegex(request.term) +
	                                                                    ")(?![^<>]*>)(?![^&;]+;)", "gi"
	                                                                ), "<strong>$1</strong>" ),
	                                                    value: item.descrizione ,
	                                                    idAsl: item.idAsl ,
	                                                    descrizioneAsl : item.descrizioneAsl
	                                                  
	                                                    
	                                                }
	                                            }));
	                                        	

												}else
											{
                                			
            								response( $.map( data, function( item ) {
        											
            									select.append('<option value='+item.codice+'>'+item.descrizione+'</option>');
                                                return {
                                                    label: item.descrizione.replace(
                                                            new RegExp(
                                                                    "(?![^&;]+;)(?!<[^<>]*)(" +
                                                                    $.ui.autocomplete.escapeRegex(request.term) +
                                                                    ")(?![^<>]*>)(?![^&;]+;)", "gi"
                                                                ), "<strong>$1</strong>" ),
                                                    value: item.descrizione 
                                                  
                                                    
                                                }
                                            }));
                                        	
											}
                                		}


                                    }
                                }
                            });
                         
                        },
                       
                        select: function( event, ui ) {
                        	 if(select[0].id=='searchcodeIdprovincia')
                             {
                        		
                        		 	document.getElementById('via').value= "-1" ;
									//document.getElementById('viainput').value= "Seleziona Indirizzo" ;
									document.getElementById('searchcodeIdComune').value= "-1" ;
								//	document.getElementById('searchcodeIdComuneinput').value= "Seleziona Comune" ;
									
									
									
                             }
                        	 if(select[0].id=='searchcodeIdComune')
                             {
                        		 	document.getElementById('via').value= "-1" ;
									//document.getElementById('viainput').value= "Seleziona Indirizzo" ;
									
									
									
							  }
                        	 if(select[0].id=='addressLegaleCity')
                             {

                                 document.getElementById('idAsl').value = ui.item.idAsl;
                                 document.getElementById('descrAsl').value = ui.item.descrizioneAsl;
                                 
                             }
                        	 
                        	 if(select[0].id=='via')
                             {
                                 //document.getElementById("cap").value = ui.item.cap ;
                                 document.getElementById("latitudine").value = ui.item.latitudine ;
                                 document.getElementById("longitudine").value = ui.item.longitudine ;
                                 
                             }
                        	 select.children( "option" ).each(function() {
                                 
                                 if ( $( this ).text().match( ui.item.value ) ) {
                                     
                                     trovato = true ;
                                     
                                     this.selected =  true;
                                    
                                     return false;
                                 }
                                
                             }
                             

                             );
                        },
                        change: function( event, ui ) {
                            if ( !ui.item )
                                return removeIfInvalid( this );
                        }
                    })
                    .addClass( "ui-widget ui-widget-content ui-corner-left" );
 
                input.data( "autocomplete" )._renderItem = function( ul, item ) {
                    return $( "<li>" )
                        .data( "item.autocomplete", item )
                        .append( "<a>" + item.label + "</a>" )
                        .appendTo( ul );
                };
 
                $( "<a>" )
                    .attr( "tabIndex", -1 )
                    .attr( "title", "Mostra tutti" )
                    .tooltip()
                    .appendTo( wrapper )
                    .button({
                        icons: {
                            primary: "ui-icon-triangle-1-s"
                        },
                        text: false
                    })
                    .removeClass( "ui-corner-all" )
                    .addClass( "ui-corner-right ui-combobox-toggle" )
                    .click(function() {
                        // close if already visible
                        if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
                            input.autocomplete( "close" );
                            removeIfInvalid( input );
                            return;
                        }
 
                        // work around a bug (likely same cause as #5265)
                        $( this ).blur();
 
                        // pass empty string as value to search for, displaying all results
                        input.autocomplete( "search", "" );
                        input.focus();
                    });
 
                    input
                        .tooltip({
                            position: {
                                of: this.button
                            },
                            tooltipClass: "ui-state-highlight"
                        });
            },
 
            destroy: function() {
                this.wrapper.remove();
                this.element.show();
                $.Widget.prototype.destroy.call( this );
            }
        });
    })( jQuery );
 
  
   
      
    $(function() {
        //$( "#searchcodeIdComune" ).combobox();
        
        //$( "#searchcodeIdprovincia" ).combobox();
        //$( "#searchcodeIdComune" ).combobox();
        $( "#via" ).combobox();
        //$( "#addressLegaleCity" ).combobox();
        $( "#addressLegaleLine1" ).combobox();
       
        


       
    });
    </script>
<script language="javascript">




function popUp(url) {
	  title  = '_types';
	  width  =  '500';
	  height =  '600';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}
</script>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><dhv:label
				name="opu.operatore.intestazione">PROPRIETARIO</dhv:label> > Aggiungi </td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>

<form id="addOperatore" name="addOperatore"
	action="OperatoreAction.do?command=Insert&auto-populate=true"
	method="post">
<%
	boolean popUp = false;
	if (request.getParameter("popup") != null) {
		popUp = true;
	}
%> <%=addHiddenParams(request, "actionSource|popup")%> 

<dhv:evaluate if="<%=( LineaProduttivaScelta.getIdRelazioneAttivita() > 0)%>">
<%
	if (ErroreGisaOffline == null || ("").equals(ErroreGisaOffline)){
%>
<input
	type="button"
	value="<dhv:label name="global.button.insert">Insert</dhv:label>"
	name="Save" onClick="javascript:verificaSoggetto()"> <dhv:formMessage
	showSpace="false" /> <!--  <input type="button" value="<dhv:label name="global.button.insert">Insert</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';verificaEsistenzaImpresa()">-->
<%
	}
%>
</dhv:evaluate>	
<dhv:evaluate if="<%=( !popUp && LineaProduttivaScelta.getIdRelazioneAttivita() > 0)%>">
	<input type="submit"
		value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
		onClick="javascript:this.form.action='OperatoreAction.do?command=SearchForm';this.form.dosubmit.value='false';">
</dhv:evaluate>

<font color="red"> <%=ErroreGisaOffline%> </font>

 <dhv:evaluate if="<%=popUp%>">
	<input type="button"
		value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
		onClick="javascript:self.close();">
</dhv:evaluate> <br />
<br />
<input type = "hidden" id = "sovrascrivi" name = "sovrascrivi" value = "si"/>
<input type = "hidden" id = "idAsl" name = idAsl value = "<%=User.getSiteId()%>"/>
<input type = "hidden" id = "descrAsl" name = "descrAsl" value = "<%=SiteList.getSelectedValue(User.getSiteId())%>"/>


<%
	if (request.getAttribute("Exist") != null
	&& !("").equals(request.getAttribute("Exist"))) {
%> <font color="red"><%=(String) request.getAttribute("Exist")%></font>
<%
	}
%> <sc:namecontext></sc:namecontext> <sc:context id="opu;gisa">
	<%@ include file="operatore_generic_add.jsp"%>

</sc:context> 

<%
 	if (ErroreGisaOffline == null || ("").equals(ErroreGisaOffline)){
 %>
<sc:context id="bdu_ext">

	<input type="hidden" id="bdu" value="no" name="bdu_ext">
	<%@ include file="operatore_bdu_add.jsp"%>


</sc:context>
 <input type="hidden" name="doContinue" id="doContinue" value="">
<input type="hidden" name="tipologiaSoggetto"
	value="<%=(request.getAttribute("TipologiaSoggetto") != null) ? (String) request
							.getAttribute("TipologiaSoggetto")
							: ""%>">
							
<dhv:evaluate if="<%= (LineaProduttivaScelta.getIdRelazioneAttivita() > 0)%>">					
<input type=button
	value="<dhv:label name="global.button.insert">Insert</dhv:label>"
	name="Save" onClick="verificaSoggetto()"> <dhv:evaluate
	if="<%=!popUp%>">
	<input type="submit"
		value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
		onClick="">
</dhv:evaluate>		
</dhv:evaluate> <dhv:evaluate if="<%=popUp%>">
	<input type="button"
		value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
		onClick="javascript:self.close();">
</dhv:evaluate></form>


<script>


$(document).ready(function() {	

	
	
	
	//if close button is clicked
	$('.window .close').click(function (e) {
		//Cancel the link behavior
		e.preventDefault();
		
		$('#mask').hide();
		$('.window').hide();
	});		
	
	
	
});

</script>
<style>
body {
font-family:verdana;
font-size:15px;
}

a {color:#333; text-decoration:none}
a:hover {color:#ccc; text-decoration:none}

#mask {
  position:absolute;
  left:0;
  top:0;
  z-index:9000;
  background-color:#000;
  display:none;
}
  
#boxes .window {
  position:absolute;
  left:0;
  top:0;
  width:675px;
  height:358;
  display:none;
  z-index:9999;
  padding:20px;
}

#boxes #dialog # {
  width:675px; 
  height:380;
  padding:10px;
  background-color:#ffffff;
}

 #dialog4 {
  width:675px; 
  height:480;
  padding:10px;
  background-color:#ffffff;
}

#boxes #dialog1 {
  width:375px; 
  height:203px;
}

#dialog1 .d-header {
  background:url(images/login-header.png) no-repeat 0 0 transparent; 
  width:375px; 
  height:150px;
}

#dialog1 .d-header input {
  position:relative;
  top:60px;
  left:100px;
  border:3px solid #cccccc;
  height:22px;
  width:200px;
  font-size:15px;
  padding:5px;
  margin-top:4px;
}

#dialog1 .d-blank {
  float:left;
  background:url(images/login-blank.png) no-repeat 0 0 transparent; 
  width:267px; 
  height:53px;
}

#dialog1 .d-login {
  float:left;
  width:108px; 
  height:53px;
}

#boxes #dialog2 {
  background:url(images/notice.png) no-repeat 0 0 transparent; 
  width:326px; 
  height:229px;
  padding:50px 0 20px 25px;
}
</style>

<div id="boxes">

<%-- IL CAMPO SRC è DA AGGIUSTARE --%>
<div id="dialog4" class="window" >
   <a href="#"class="close"/><b>Chiudi</b></a>
   
  <table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">

				<tr>
					<th colspan="2"><strong><div id = "intestazione"></div></strong></th>
				</tr>
				
				<tr>
					<td class="formLabel" nowrap>Nome</td>
					<td><div id = "nomeSoggetto"></div></td>
				</tr>
				
				<tr>
					<td class="formLabel" nowrap>Cognome</td>
					<td><div id = "cognomeSoggetto"></div></td>
				</tr>
				
				<tr>
					<td class="formLabel" nowrap>Sesso</td>
					<td><div id = "sessoSoggetto"></div></td>
				</tr>
				
				<tr>
					<td class="formLabel" nowrap>Data Nascita</td>
					<td><div id = "dataNascitaSoggetto"></div></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>Comune Nascita</td>
					<td><div id = "comuneNascitaSoggetto"></div></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>Provincia Nascita</td>
					<td><div id = "provinciaNascitaSoggetto"></div></td>
				</tr>
				
				<tr>
					<td class="formLabel" nowrap>Comune Residenza</td>
					<td><div id = "comuneResidenzaSoggetto"></div></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>Provincia Residenza</td>
					<td><div id = "provinciaResidenzaSoggetto"></div></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>Indirizzo Residenza</td>
					<td><div id = "indirizzoResidenzaSoggetto"></div></td>
				</tr>
				
				<tr>
					<td class="formLabel" nowrap>Documento</td>
					<td><div id = "documentoSoggetto"></div></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>e-mail</td>
					<td><div id = "mailSoggetto"></div></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>telefono principale</td>
					<td><div id = "telefono1Soggetto"></div></td>
				</tr>
				<tr>
					<td class="formLabel" nowrap>cellulare/tel. secondario</td>
					<td><div id = "telefono2Soggetto"></div></td>
				</tr>
				
				<tr>
					<td class="formLabel" nowrap>Fax</td>
					<td><div id = "faxSoggetto"></div></td>
				</tr>
					<tr id="azione">
					<td><input type = "button" value = "Sovrascrivi" onclick="document.getElementById('sovrascrivi').value='si';checkForm();"/></td>
					<td><input type = "button" value = "Non Sovrascrivere" onclick="document.getElementById('sovrascrivi').value='no';checkForm();" /></td>
					</tr>
				
				</table>
  
  
</div>



<!-- Mask to cover the whole screen -->
  <div id="mask"></div>

</div>

<% } %>


