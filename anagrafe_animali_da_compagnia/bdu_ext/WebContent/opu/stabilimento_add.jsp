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



<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu.base.Operatore"%>

<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<jsp:useBean id="rappLegale" class="org.aspcfs.modules.opu.base.SoggettoFisico" scope="request" />
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
<jsp:useBean id="indirizzoAdded" class="org.aspcfs.modules.opu.base.Indirizzo" scope="request" />
<!-- sede inserita -->
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<!-- ASL -->
<jsp:useBean id="IterList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="Address" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="provinciaAsl"  class="org.aspcfs.modules.accounts.base.Provincia" scope="request" />
<jsp:useBean id="ImportatoreInformazioni"  class="org.aspcfs.modules.opu.base.ImportatoreInformazioni" scope="request" />

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
	//cal19.showNavigationDropdowns();
</SCRIPT>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
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
        /* adjust styles for IE 6/7 */
        *height: 1.7em;
        *top: 0.1em;
    }
    .ui-combobox-input {
        margin: 0;
        padding: 0.3em;
    }
    </style>
    
    <script>
function verificaSoggetto()
{
	if(document.getElementById('cap') != null && document.getElementById('cap').value=="80100")
	{
		alert('Il valore del cap non può essere 80100');
		return fale;
	}
	if (document.getElementById('codFiscaleSoggetto') != null)
	{
	cf = document.getElementById('codFiscaleSoggetto').value ;
	if (cf!='')
		PopolaCombo.verificaAslSoggetto(cf,verificaSoggettoCallback);
	else
		checkForm();

	}else
	{
checkForm();
		}
}

function verificaSoggettoCallback(value)
{
	//alert('stop');
	if (value != null && value.idSoggetto>0)
	{
	nomeEsistente = value.nome;
	//alert('stop');
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
	comuneResidenzaEsistente = value.indirizzo.comune ; 
	provinciaResidenzaEsistente = value.indirizzo.provincia ; 
	indirizzoResidenzaEsistente = value.indirizzo.via ; 
	descrizioneComune = value.indirizzo.descrizioneComune ;
	idAsl = value.idAsl ;
	descrAsl = value.descrAsl ;
//alert('stop');
	
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
	comuneResidenza = document.getElementById('addressLegaleCityinput').value ;
	provinciaResidenza = document.getElementById('addressLegaleCountryinput').value ;
	indirizzoResidenza = document.getElementById('addressLegaleLine1input').value ;


	if (nomeEsistente !=nome || cognomeEsistente != cognome || sessoEsistente != sesso ||
			dataNascitaEsistente != dataNascita || comuneNascitaEsistente != comuneNascita || provinciaNascitaEsistente !=provinciaNascita ||
			documentoIdentitaEsistente!= documento || telefono1Esistente != tel1 || telefono2Esistente != tel2 ||
			faxEsistente != fax || comuneResidenzaEsistente !=comuneResidenza ||provinciaResidenza !=provinciaResidenza || indirizzoResidenzaEsistente!=indirizzoResidenza  )
	{

		if (document.getElementById('idAsl').value != '' && document.getElementById('idAsl').value!= idAsl)
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


		if (document.getElementById('idAsl').value != '' && document.getElementById('idAsl').value!= idAsl)
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
    
    
    </script>
<script>

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
    
    function checkForm()
    {
    	$('.todisable').attr('disabled', false);
    	document.forms[0].doContinue.value="true";

    	
    	msg = "Attenzione Controllare di aver compilato i seguenti campi\n" ;


    	//alert(document.forms[0].searchcodeIdprovincia.value);

    	if($("#searchcodeIdprovincia").length > 0){
        	if( document.forms[0].searchcodeIdprovincia.value=='-1' || document.forms[0].searchcodeIdprovincia.value=='')
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo provincia stabilimento richiesto \n" ;
    	}
    	}

    	if(document.forms[0].searchcodeIdComune.value=='-1' || document.forms[0].searchcodeIdComune.value=='')
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo comune stabilimento richiesto \n" ;
    	}

    	if(document.forms[0].viainput != null && (document.forms[0].viainput.value=='' || document.forms[0].viainput.value=='Seleziona Indirizzo'))
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo indirizzo stabilimento richiesto \n" ;
    		
    	}
    	
    	if(document.forms[0].via != null && document.forms[0].via.value=='' )
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo indirizzo sede legale richiesto \n" ;
    		
    	}
    	
    	/* if(document.forms[0].via.value=='-1')
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo indirizzo sede legale richiesto \n" ;
    		
    	}*/

    	if($("#codFiscaleSoggetto").length =0)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo codice fiscale soggetto richiesto \n" ;
    	}
    	if($("#nome").length > 0 && document.forms[0].nome.value=='')
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo nome soggetto fisico richiesto \n" ;
    	}

    	if($("#cognome").length > 0 &&document.forms[0].cognome.value=='')
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo cognome soggetto fisico richiesto \n" ;
    	}

    //	if(document.forms[0].numLineeProduttive.value=='0')
   // 	{
   // 		document.forms[0].doContinue.value="false";
  //  		msg+= "- Campo Linee produttive richiesto \n" ;
   // 	}


if ($("#telefono1").val() == ""  ){
	//alert("fjffj");
	document.forms[0].doContinue.value="false";
	msg+= "- Telefono Principale Richiesto \n" ;
	}
    	if ($("#codiceUvac").length > 0 && $("#codiceUvac").val() == ""){
        	//alert("fjffj");
    		document.forms[0].doContinue.value="false";
    		msg+= "- Codice Uvac richiesto per un proprietario di tipo importatore \n" ;
        	}
    	
    	if (false /*$("#mqDisponibili").length > 0*/ ){
    		if( $("#mqDisponibili").val() == ""){
    	
        	//alert("fjffj");
    		document.forms[0].doContinue.value="false";
    		msg+= "- Informazione sulla superficie disponibile al ricovero richiesta \n" ;
        	}else{
        		
        	if	( !(/^[0-9]+$/i.test( $("#mqDisponibili").val())) ){
        		document.forms[0].doContinue.value="false";
        		msg+= "- Informazione sulla superficie disponibile al ricovero deve contenere solo numeri \n" ;
        	}
        	}
    	}
    	

    	

    	if(document.forms[0].doContinue.value=="false"){
    		alert(msg);
    	}

    	else
    	{
    		document.forms[0].doContinue.value="true";
    		document.forms[0].submit();
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
                        if(select[0].id =='via'|| select[0].id =='addressLegaleLine1'|| select[0].id =='codFiscaleSoggetto')
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
						select.append('<option value=-1 selected>Inserire le prime 4 lettere</option>');
                     	//	document.getElementById('sesso1').checked=false ;
                  		//	document.getElementById('sesso2').checked=true ;

              			document.getElementById('nome').value= "" ;
						document.getElementById('cognome').value= "" ;
						document.getElementById('dataNascita').value= "" ;
						document.getElementById('comuneNascita').value= "" ;
						document.getElementById('provinciaNascita').value= "" ;

						document.getElementById('addressLegaleCountry').value= "" ;									
						document.getElementById('addressLegaleCity').value= "" ;									
						document.getElementById('addressLegaleLine1').value= "" ;									
						document.getElementById('email').value= "" ;									
						document.getElementById('telefono1').value= "" ;									
						document.getElementById('telefono2').value= "" ;									
						document.getElementById('fax').value= "" ;				
                        return false;
                        }
                        else
                        {
                        	$('.todisable').attr('disabled', false);
                       	 select[0].value='-1';
                   		document.getElementById(select[0].id+'Testo').value = value ;
							//sovrascrittura casella indirizzo
                   		select.append('<option value=-1 selected>'+value+'</option>');
                    	//	document.getElementById('sesso1').checked=false ;
                 		//	document.getElementById('sesso2').checked=true ;

                            /*$('.todisable').attr('disabled', false);
	                        	 select[0].value='-1';
                        		document.getElementById(select[0].id+'Testo').value = value ;
                        		select.append('<option value=-1 selected>Seleziona Voce</option>');
                         		document.getElementById('sesso1').checked=false ;
                      			document.getElementById('sesso2').checked=true ;

                  			document.getElementById('nome').value= "" ;
    						document.getElementById('cognome').value= "" ;
    						document.getElementById('dataNascita').value= "" ;
    						document.getElementById('comuneNascita').value= "" ;
    						document.getElementById('provinciaNascita').value= "" ;

    						document.getElementById('addressLegaleCountry').value= "" ;									
    						document.getElementById('addressLegaleCity').value= "" ;									
    						document.getElementById('addressLegaleLine1').value= "" ;									
    						document.getElementById('email').value= "" ;									
    						document.getElementById('telefono1').value= "" ;									
    						document.getElementById('telefono2').value= "" ;									
    						document.getElementById('fax').value= "" ;	*/		
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
					select.append('<option value=-1 selected >Inserire le prime 4 lettere</option>');
                    
                    return false;
                     }
                    }else{
                    	$('.todisable').attr('disabled', true);
                    }
                }
                 
                input = $( "<input id='"+select[0].id+"input' onclick='pulisciCampo(this)' onmouseout='ripristinaCampo(this)'>" )
                    .appendTo( wrapper )
                    .val( value )
                    .attr( "title", "" )
                    .addClass( "ui-state-default ui-combobox-input" )
                    .autocomplete({
                        delay: 0,
                        minLength: 0,
                        
                        source:  function( request, response ) {
                       	 
                    	 if(select[0].id =='searchcodeIdprovincia' || select[0].id =='searchcodeIdComune' || select[0].id =='via' )
                         {
                    		 if ($("#searchcodeIdprovincia").length > 0){
                     			idprovincia = document.getElementById("searchcodeIdprovincia").value ;
                     		 }else{
                     			 idprovincia = document.getElementById("searchcodeIdprovinciaAsl").value ;
                     		 }
                    		 idcomune =  document.getElementById("searchcodeIdComune").value;
                    		 inregione= document.forms[0].inregione.value;
                    		 idAsl = document.getElementById("idAsl").value;
                    		// alert(idAsl);

                         }
                    	 else
                    	 {

                    		 if(select[0].id =='addressLegaleCity' || select[0].id =='addressLegaleCountry' || select[0].id =='addressLegaleLine1' )
                             {

                    		 idprovincia =document.getElementById("addressLegaleCountry").value ;
                    		 idcomune =  document.getElementById("addressLegaleCity").value;
                    		 inregione = document.forms[0].inregioneRappOperativo.value;
                    		 idAsl = -1;
                             }
                        	 }
                      	
                            $.ajax({

                               
                          	
                          		  url:  "./ServletComuni?&nome="+request.term+"&combo="+select[0].id+"&idProvincia="+idprovincia+"&idComune="+idcomune+"&inRegione="+inregione+"&idAsl=<%=User.getSiteId()%>",
                                       		  
								
                               
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
                                                datanascita: item.dataNascitaString,
                                                sesso: item.sesso,
                                                comunenascita: item.comuneNascita,
                                                provincianascita: item.provinciaNascita,

                                                indirizzoresidenza: item.descrizionevia,
                                                idindirizzo:item.codicevia,
                                                comuneresidenza: item.descrizioneComune,
                                                idcomuneresidenza:item.comune,
                                                provinciaresidenza:item.provincia,
                                                capresidenza:item.cap,
                                                idvia:item.codice,
                                                mail:item.email,
                                                tel1:item.telefono1,
                                                tel2:item.telefono2,
                                                fax:item.fax
                                                
                                                
                                            }
                                        }));
                                    	}
                                		else
                                		{
                                    		if(select [0].id=='searchcodeIdComune')
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
                                                        value: item.descrizione ,
                                                        asl: item.descrizioneAsl,
                                                        idasl:item.idAsl
                                                      
                                                        
                                                    }
                                                }));
                                        	}
                                    		else
                                    		{
                                    			if (select[0].id=='addressLegaleCity')
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
    	                                                    value: item.descrizione ,
    	                                                    idAsl: item.idAsl ,
    	                                                    descrizioneAsl : item.descrizioneAsl
    	                                                  
    	                                                    
    	                                                }
    	                                            }));
    	                                        	

    												}
                                    			else
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
                                }
                            });
                         
                        },
                        select: function( event, ui ) {
                        	 if(select[0].id=='searchcodeIdprovincia')
                             {

                        		
                        		 	document.getElementById('via').value= "-1" ;
									document.getElementById('viainput').value= "Inserire le prime 4 lettere" ;
									document.getElementById('searchcodeIdComune').value= "-1" ;
									document.getElementById('searchcodeIdComuneinput').value= "Inserire le prime 4 lettere" ;
									document.getElementById('idAsl').value= "-1" ;
									
									
                             }
                        	 if(select[0].id=='searchcodeIdComune')
                             {

                        		  document.getElementById("descrizioneasl").innerHTML = ui.item.asl ;
                                  document.getElementById("idAsl").value = ui.item.idasl ;
                        		  document.getElementById('via').value= "-1" ;
								  document.getElementById('viainput').value= "Inserire le prime 4 lettere" ;
									
							  }
                        	 if(select[0].id=='addressLegaleCity')
                             {

                                 document.getElementById('idAsl').value = ui.item.idAsl;
                                 document.getElementById('descrAsl').value = ui.item.descrizioneAsl;
                                 
                             }
                        	 
                        	 if(select[0].id=='via')
                             {
                                 document.getElementById("cap").value = ui.item.cap ;
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
        //$( "#via" ).combobox();
        $( "#addressLegaleCity" ).combobox();
        $( "#addressLegaleCountry" ).combobox();
        $( "#addressLegaleLine1" ).combobox();
       
    });
    </script>
    <script language="javascript">

function popUp(url) {
	  title  = '_types';
	  width  =  '500';
	  height =  '600';
	  resize =  'yes';
	  bars   =  'yes';
	  
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

<form id="addStabilimento" name="addStabilimento"
	action="StabilimentoAction.do?command=Insert&auto-populate=true"
	method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%> <input type="button"
	value="<dhv:label name="global.button.insert">Insert</dhv:label>"
	name="Save" onClick="verificaSoggetto();"> <dhv:formMessage showSpace="false" /> <!--  <input type="button" value="<dhv:label name="global.button.insert">Insert</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';verificaEsistenzaImpresa()">-->
<dhv:evaluate if="<%= !popUp %>">
	<input type="submit"
		value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
		onClick="javascript:this.form.action='OperatoreAction.do?command=SearchForm';this.form.dosubmit.value='false';">
</dhv:evaluate> <dhv:evaluate if="<%= popUp %>">
	<input type="button"
		value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
		onClick="javascript:self.close();">
		
		
</dhv:evaluate> <br />
<br />

<input type = "hidden" id = "sovrascrivi" name = "sovrascrivi" value = "si"/>
<input type = "hidden" id = "idAsl" name = idAsl value = ""/>
<input type = "hidden" id = "descrAsl" name = "descrAsl" value = ""/>
<input type = "hidden" name = "tipologiaSoggetto" value = "<%=(request.getAttribute("tipologiaSoggetto")!=null)? (String)request.getAttribute("tipologiaSoggetto"):"" %>">
				<!-- DATI DELL'IMPRESA  -->
<%
String label_rag_sociale = "";
int id_relazione = -1;
if (newStabilimento != null && (newStabilimento.getListaLineeProduttive().size() > 0)){
	//if (newStabilimento.getListaLineeProduttive().size() > 0){
	 id_relazione = ((LineaProduttiva)newStabilimento.getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
	 label_rag_sociale = "opu.operatore_"+id_relazione;
}else {
	 label_rag_sociale = "opu.operatore";
}
%>			
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label
			name="opu.stabilimento.linea_produttiva"></dhv:label></strong></th>
	</tr>
	<dhv:evaluate
		if="<%=(newStabilimento.getListaLineeProduttive().size() > 0)%>">
		<tr>
			<td class="formLabel"><dhv:label
				name="opu.stabilimento.linea_produttiva"></dhv:label></td>
			<td><input type="hidden" name="idLineaProduttiva"
				value="<%=((LineaProduttiva) newStabilimento
							.getListaLineeProduttive().get(0)).getId()%>">
			<%=((LineaProduttiva) newStabilimento
							.getListaLineeProduttive().get(0)).getAttivita()%></td>
		</tr>
	</dhv:evaluate>

</table>
<br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><dhv:label name="<%=label_rag_sociale %>"></dhv:label>
		</th>
	</tr>


<input type="hidden" name="idOperatore" id="idOperatore"
				value="<%=newStabilimento.getIdOperatore() %>" />

	<dhv:evaluate if="<%= hasText(newStabilimento.getRagioneSociale()) %>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1">
			<dhv:label name="opu.operatore.ragione_sociale"></dhv:label>
			</td>
			<td> <%= toHtmlValue(newStabilimento.getRagioneSociale()) %>
			</td>
		</tr>
	</dhv:evaluate>

	<dhv:evaluate if="<%= hasText(newStabilimento.getPartitaIva()) %>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1">
			<dhv:label name="opu.operatore.piva"></dhv:label>
				</td>
			<td><%= toHtmlValue(newStabilimento.getPartitaIva()) %></td>
		</tr>
	</dhv:evaluate>

	<dhv:evaluate if="<%= hasText(newStabilimento.getCodFiscale()) %>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1">
			<dhv:label name="opu.operatore.cf"></dhv:label>
			</td>
			<td><%= toHtmlValue(newStabilimento.getCodFiscale()) %></td>
		</tr>
	</dhv:evaluate>
	
	<dhv:evaluate if="<%= hasText(newStabilimento.getNote()) %>">
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1">
			<dhv:label name="opu.operatore.note"></dhv:label>
			</td>
			<td><%= toHtmlValue(newStabilimento.getNote()) %></td>
		</tr>
	</dhv:evaluate>

	<dhv:evaluate if="<%= (rappLegale != null) %>">
		<tr>
		<%String rapp = "opu.soggetto_fisico_"+id_relazione;%>
			<td class="formLabel" nowrap><dhv:label name="<%=rapp%>"></dhv:label>
			</td>
			<td>
			<input type = "hidden" value="<%=rappLegale.getIdSoggetto() %>" name="idRappLegale" id="idRappLegale"/>
			<%= rappLegale.getNome()+ " "+rappLegale.getCognome()+ " ("+rappLegale.getCodFiscale()+")" %></td>
		</tr>	</dhv:evaluate>
		
</table>
<br />
<dhv:evaluate if ="<%=(id_relazione != LineaProduttiva.idAggregazioneColonia) %>">
<%String label_sede_legale = "opu.sede_legale_" + id_relazione; %>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="<%=label_sede_legale %>"></dhv:label></strong>
		</th>
	</tr>
	
	
	<dhv:evaluate if="<%= (newStabilimento.getSedeLegale() != null) %>">
		<tr>
			<td class="formLabel" nowrap><dhv:label name="opu.sede_legale.indirizzo"></dhv:label>
			</td>
			<td>
			<%String comune = 
    		ComuniList.getSelectedValue(newStabilimento.getSedeLegale().getComune());
    			
    			String via = (newStabilimento.getSedeLegale().getVia()!= null)? newStabilimento.getSedeLegale().getVia():"";  %>

			<%= newStabilimento.getSedeLegale().toString() %></td>
		</tr>
		
	
		
	</dhv:evaluate>

</table>
</dhv:evaluate>
<!-- FINE DATI DELL'IMPRESA  -->

 
<sc:context id="opu;gisa">
<%@ include file="stabilimento_generic_add.jsp" %>
<sc:namecontext></sc:namecontext>
</sc:context>
 
<sc:context id="bdu_ext">
<sc:namecontext></sc:namecontext>
<%@ include file="stabilimento_bdu_add.jsp" %>
</sc:context>


<input type="hidden" name="doContinue" id="doContinue" value="">
<input type="button"
	value="<dhv:label name="global.button.insert">Insert</dhv:label>"
	name="Save" onClick="verificaSoggetto()"> <dhv:evaluate if="<%= !popUp %>">
	<input type="submit"
		value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
		onClick="">
</dhv:evaluate> <dhv:evaluate if="<%= popUp %>">
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

