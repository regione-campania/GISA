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
  - Version: $Id: accounts_modify.jsp 19046 2007-02-07 18:53:43Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.opu.base.*" %>


<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<jsp:useBean id="soggettoAdded" class="org.aspcfs.modules.opu.base.SoggettoFisico" scope="request" /> <!-- soggetto inserito -->
<jsp:useBean id="indirizzoAdded" class="org.aspcfs.modules.opu.base.Indirizzo" scope="request" /> <!-- sede inserita -->
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
    function checkData()
    {
	//AGGIUNTI PER CATTURARE INFO INDIRIZZO
    var cogn = document.getElementById("cognome"); 
	var nom= document.getElementById("nome");
	alert("Cognome: "+cogn.value+" Nome: "+nom.value);
    }
    function checkForm()
    {
	//AGGIUNTI PER CATTURARE INFO INDIRIZZO
    var selectComune = document.getElementById("searchcodeIdComune"); 
	var selectProvincia= document.getElementById("searchcodeIdprovincia");
	var selectIndirizzo= document.getElementById("via");
	var comText = selectComune.options[selectComune.selectedIndex].innerHTML;
	var provText = selectProvincia.options[selectProvincia.selectedIndex].innerHTML;
	var indText = selectIndirizzo.options[selectIndirizzo.selectedIndex].innerHTML;
	document.getElementById("searchcodeIdprovinciaTesto").value=provText;
	document.getElementById("searchcodeIdComuneTesto").value=comText;

	//Se l'ID è -1 significa che è stato inserito un nuovo indirizzo e va creata l'entry nel db
	// Apparentemente non riesce a catturare la stringa, quindi provo a settarla staticamente
	/*if (selectIndirizzo.value ==-1){
		document.getElementById("viaTesto").value="IF PROVA";
	}
	else{*/
	document.getElementById("viaTesto").value=indText;//}
	
	//alert('ID PROVINCIA: '+selectProvincia.value+' \nID COMUNE: '+selectComune.value+'\nID INDIRIZZO: '+selectIndirizzo.value+'\nProvinciaText: '+provText+"\nComuneText:"+comText+'\nIndirizzoText: '+indText);
	
		document.getElementById("addressLegaleCountry").value=selectProvincia.value;
		document.getElementById("addressLegaleCity").value=selectComune.value;
		document.getElementById("addressLegaleLine1").value=selectIndirizzo.value;
		document.getElementById("addressLegaleCountryTesto").value=provText;
		document.getElementById("addressLegaleCityTesto").value=comText;
		document.getElementById("addressLegaleLine1Testo").value=indText;
		//alert('Valori immessi: (hidden)\n'+document.getElementById("addressLegaleCountry").value+'\n'+document.getElementById("addressLegaleCity").value+'\n'+document.getElementById("addressLegaleLine1").value+'\n'+document.getElementById("addressLegaleCountryTesto").value+'\n'+document.getElementById("addressLegaleCityTesto").value+'\n'+document.getElementById("addressLegaleLine1Testo").value);
		//alert('Sesso M: '+document.getElementById('sesso1').checked+' \n Sesso F: '+document.getElementById('sesso2').checked);
		
		//FINE AGGIUNTA
	
    	document.forms[0].doContinue.value="true";
    	msg = "Attenzione Controllare di aver compilato i seguenti campi\n" ;
    	if(document.forms[0].ragioneSociale.value=='')
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo ragione sociale richiesto \n" ;
    	}

    	if(document.forms[0].partitaIva.value=='') //||  document.forms[0].partitaIva.value.length!=11)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo partitaIva richiesto o non corretto (lunghezza 11 caratteri) \n" ;
    	}
/*
    	if(document.forms[0].searchcodeIdprovincia.value=='-1')
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo provincia sede legale richiesto \n" ;
    	}

    	if(document.forms[0].searchcodeIdComune.value=='-1')
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo comune sede legale richiesto \n" ;
    	}

    	if(document.forms[0].via.value=='-1')
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo indirizzo sede legale richiesto \n" ;
    	}

    	if(document.forms[0].codFiscale.value.length > 0 && document.forms[0].codFiscale.value.length!=16)
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo codice fiscale azienda non corretto (Lunghezza 16 caratteri) \n" ;
    	}
*/
    	if((document.forms[0].codFiscaleSoggetto.value=='-1' && document.forms[0].codFiscaleSoggettoTesto.value=='') ||   (document.forms[0].codFiscaleSoggetto.value=='-1' && document.forms[0].codFiscaleSoggettoTesto.value.length!=16))
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo codice fiscale soggetto richiestoo non corretto (lunghezza 16 caratteri) \n" ;
    	}
    	if(document.forms[0].nome.value=='')
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo nome soggetto fisico richiesto \n" ;
    	}

    	if(document.forms[0].cognome.value=='')
    	{
    		document.forms[0].doContinue.value="false";
    		msg+= "- Campo cognome soggetto fisico richiesto \n" ;
    	}

    	if(document.forms[0].doContinue.value=="false")
    		alert(msg);

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
                         	//	document.getElementById('sesso1').checked=true ;
                      		//	document.getElementById('sesso2').checked=false ;
                      			
                      			
                  			/*
                  			document.getElementById('nome').value= "" ;
    						document.getElementById('cognome').value= "" ;
    						document.getElementById('dataNascita').value= "" ;
    						document.getElementById('comuneNascita').value= "" ;
    						document.getElementById('provinciaNascita').value= "" ;

    															
    						document.getElementById('addressLegaleCity').value= "" ;									
    						document.getElementById('addressLegaleLine1').value= "" ;									
    						document.getElementById('email').value= "" ;									
    						document.getElementById('telefono1').value= "" ;									
    						document.getElementById('telefono2').value= "" ;									
    						document.getElementById('fax').value= "" ;		*/		
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

                          			 

                      		/*	document.getElementById('nome').value= "" ;
        						document.getElementById('cognome').value= "" ;
        						document.getElementById('dataNascita').value= "" ;
        						document.getElementById('comuneNascita').value= "" ;
        						document.getElementById('provinciaNascita').value= "" ;

        						if ($("#addressLegaleCountry").length > 0){
        							document.getElementById('addressLegaleCountry').value= "" ;	
        						}								
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
    					select.append('<option value=-1 selected >Seleziona Voce</option>');
                        
                        return false;
                         }
                        }else{

                        	$('.todisable').attr('disabled', true);
                            
                        	// $('#elementsToOperateOn :input').attr('disabled', true);
                        	// alert($('#codFiscaleSoggetto').attr('disabled'));
                        	// $('#codFiscaleSoggetto').removeAttr('disabled');
                        	// $( "#codFiscaleSoggetto" ).combobox();
                        	// alert($('#codFiscaleSoggetto').attr('disabled'));
                        //	 $('input[name="codFiscaleSoggetto"]').attr('disabled','disabled');
                        	// $('#elementsToOperateOn :input').attr('disabled', false);
                        	// $('input[name="codFiscaleSoggetto"]').attr('disabled', false);
                        	 //$('#codFiscaleSoggetto').attr('disabled', false);
                        }
                    }
                     
                    input = $( "<input id='"+select[0].id+"input'>" )
                        .appendTo( wrapper )
                        .val( value )
                        .attr( "title", "" )
                        .addClass( "ui-state-default ui-combobox-input" )
                        .autocomplete({
                            delay: 0,
                            minLength: 0,
                           
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

                        		 if(select[0].id =='addressLegaleCity' || select[0].id =='addressLegaleCountry' || select[0].id =='addressLegaleLine1' )
                                 {


                        			 if ($("#addressLegaleCountry").length > 0){
                        				 idprovincia =document.getElementById("addressLegaleCountry").value ;
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

                                   
                              	
                              		  url:  "./ServletComuni?&nome="+request.term+"&combo="+select[0].id+"&idProvincia="+idprovincia+"&idComune="+idcomune+"&inRegione="+inregione,
                                   
                                   
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
                                });
                             
                            },
                            select: function( event, ui ) {
                            	 if(select[0].id=='searchcodeIdprovincia')
                                 {

                            		
                            		 	document.getElementById('via').value= "-1" ;
    									document.getElementById('viainput').value= "Seleziona Indirizzo" ;
    									document.getElementById('searchcodeIdComune').value= "-1" ;
    									document.getElementById('searchcodeIdComuneinput').value= "Seleziona Comune" ;
    									
    									
    									
                                 }
                            	 if(select[0].id=='searchcodeIdComune')
                                 {

                            		
                            		 	document.getElementById('via').value= "-1" ;
    									document.getElementById('viainput').value= "Seleziona Indirizzo" ;
    									
    									
    									
    							  }
                            	 if(select[0].id=='codFiscaleSoggetto')
                                 {

                                    
                             		if(ui.item.sesso=='M')
                                 		document.getElementById('sesso1').checked=true ;
                            		if(ui.item.sesso=='F')
                              			document.getElementById('sesso2').checked=true ;

                          			document.getElementById('nome').value= ui.item.nome ;
    								document.getElementById('cognome').value= ui.item.cognome ;
    								document.getElementById('dataNascita').value= ui.item.datanascita ;
    								document.getElementById('comuneNascita').value= ui.item.comunenascita ;

    								
    								sel = document.getElementById('addressLegaleCity');
    								//alert('stop');
    							//	alert(document.getElementById('addressLegaleCity').options[0].selected);
    								document.getElementById('addressLegaleCity').options[0].value = ui.item.idcomuneresidenza ;
    								document.getElementById('addressLegaleCity').options[0].text = ui.item.comuneresidenza ;
    								document.getElementById('addressLegaleCityinput').value = ui.item.comuneresidenza;

    								document.getElementById('addressLegaleLine1').options[0].value = ui.item.idindirizzo ;
    								document.getElementById('addressLegaleLine1').options[0].text = ui.item.indirizzoresidenza ;
    								document.getElementById('addressLegaleLine1input').value = ui.item.indirizzoresidenza;


    								
    							//	document.getElementById('addressLegaleCountry').options[0].value = ui.item.provinciaresidenza ;
    							//	document.getElementById('addressLegaleCountry').options[0].text = ui.item.provinciaresidenza ;
    							//	document.getElementById('addressLegaleCountryinput').value = ui.item.provinciaresidenza;
									
									alert('New Option');
    								document.forms[0].addressLegaleCountry.options[document.forms[0].addressLegaleCountry.options.length]= new Option(ui.item.provinciaresidenza,  ui.item.codiceprovincia,selected);
                                    document.getElementById('addressLegaleCountry').options[0].value = ui.item.provinciaresidenza ;
                                    document.getElementById('addressLegaleCountry').options[0].text = ui.item.provinciaresidenza ;
                            //        document.getElementById('addressLegaleCountryTesto').value = ui.item.provinciaresidenza;
                                    document.getElementById('addressLegaleCountryinput').value = ui.item.provinciaresidenza;
                                	alert('Aggiunta: '+ document.getElementById('addressLegaleCountry').options[0].value+'\n'+document.getElementById('addressLegaleCountry').options[0].text+'\n'+ document.getElementById('addressLegaleCountryinput').value);

    							

    								
    							

    								//document.getElementById('addressLegaleCountry').value= ui.item.comuneresidenza ;									
    								//document.getElementById('addressLegaleCity').value= ui.item.provincianascita ;									
    								//document.getElementById('addressLegaleLine1').value= ui.item.indirizzoresidenza ;									
    								document.getElementById('email').value= ui.item.mail ;									
    								document.getElementById('telefono1').value= ui.item.tel1 ;									
    								document.getElementById('telefono2').value= ui.item.tel2 ;									
    								document.getElementById('fax').value= ui.item.fax ;		

    								

    								
    								$('#elementsToOperateOn :input').attr('disabled', true);	
    								$('#elementsToOperateOn :input:codFiscaleSoggetto').attr('disabled', false);
    								 $('#codFiscaleSoggetto').removeAttr('disabled');
    							//	$('input[name="codFiscaleSoggetto"]').attr('disabled','disabled');	
    								//$('input[name="codFiscaleSoggetto"]').attr('disabled', false);
    								//$('#codFiscaleSoggetto').attr('disabled', false);				
    																	
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
        
        $( "#searchcodeIdprovincia" ).combobox();
        $( "#searchcodeIdComune" ).combobox();
        $( "#via" ).combobox();
        $( "#codFiscaleSoggetto" ).combobox();
       
    });
    </script>
<%@ include file="../initPage.jsp" %>
  <%
  	Stabilimento temp = (Stabilimento) Operatore.getListaStabilimenti().get(0); 
   LineaProduttiva temp1 = (LineaProduttiva) temp.getListaLineeProduttive().get(0);
   int id = temp1.getIdRelazioneAttivita(); 
   boolean Privato=false;
   boolean OperatoreCommerciale=false;
   String TipoOperatore="";
   if (id==1){
  	 Privato = true;
  	 TipoOperatore="Privato";
  	 }
   else if (id==6){
  	 OperatoreCommerciale=true;
  	 TipoOperatore="Operatore Commerciale";}
  %>
<form name="operatoreModify" id="operatoreModify" action="OperatoreAction.do?command=ResponsabileStabilimentoUpdate&auto-populate=true&opId=<%=temp1.getId()%>" method="post">
<input type="button" value="<dhv:label name="global.button.update"></dhv:label>" name="Save" onClick="javascript:checkForm()" />

<br /><br />

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<input type="hidden" name="doContinue" id="doContinue" value="">
  <tr>
    <th colspan="2">
    <%
    	String label;
        label = "opu.operatore_"+id;
    %>
      <strong><dhv:label name="<%=label%>">Riepilogo informazione primaria</dhv:label></strong>
    </th>
  </tr>

    
  <tr>
   <td nowrap class="formLabel">
    <%
    	label = "opu.operatore.ragione_sociale_"+id;
    %>
        <dhv:label name="<%=label%>">RAGIONE SOCIALE</dhv:label>
      </td>
      <td>
		
		
		 <input disabled style="background: none repeat scroll 0% 0% rgb(204, 204, 204);" type="text" size="20" maxlength="200" id = "ragioneSociale" name="ragioneSociale" value="<%=(Operatore.getRagioneSociale() != null) ? Operatore.getRagioneSociale() : ""%>">
		
		
	 </td>
 </tr>
 
   <tr>
   <td nowrap class="formLabel">
   <%
   	label = "opu.operatore.cf";
      		if (id>1)
      		label=label+"_impresa";
   %>
        <dhv:label name="<%=label%>">Codice fiscale impresa</dhv:label>
      </td>
      <td>
		<input disabled style="background: none repeat scroll 0% 0% rgb(204, 204, 204);" type="text" size="20" maxlength="16" id = "codFiscale" name="codFiscale" value="<%=(Operatore.getCodFiscale() != null) ? Operatore.getCodFiscale() : ""%>"> 
	 </td>
 </tr>
 
 
   <tr>
 <td nowrap class="formLabel">
        <dhv:label name="opu.operatore.piva">Partita iva</dhv:label>
      </td>
      <td>
     
		<input disabled style="background: none repeat scroll 0% 0% rgb(204, 204, 204);" type="text" size="20" maxlength="11" id = "partitaIva" name="partitaIva" value="<%=(Operatore.getPartitaIva() != null) ? Operatore.getPartitaIva() : ""%>">
		
	 </td>
 </tr>
</table>
</br></br>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
    <%
    	label = "opu.stabilimento.soggetto_fisico_"+id;
    %>
	    <strong><dhv:label name="<%=label%>">RESPONSABILE STABILIMENTO</dhv:label></strong>
	  </th></tr>
		
<tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.nome">Nome</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "nome" name="nome" value="<%=temp.getRappLegale().getNome()%>"><font color="red">*</font>
     
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.cognome">Cognome</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "cognome" name="cognome" value="<%=temp.getRappLegale().getCognome()%>"><font color="red">*</font>
    </td>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.cf">Codice Fiscale</dhv:label>
    </td> 
    <td>
<select name = "codFiscaleSoggetto" id = "codFiscaleSoggetto">
<option value = "<%=temp.getRappLegale().getIdSoggetto()%>" selected="selected"><%=temp.getRappLegale().getCodFiscale()%></option>
</select>
  <input type = "hidden" name = "codFiscaleSoggettoTesto" id = "codFiscaleSoggettoTesto" value = ""/>

    </td>
  </tr>
  
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.sesso">Sesso</dhv:label>
    </td>
    <td>
      <input type="radio" name="sesso" id="sesso1" value="M"  <%=(temp.getRappLegale().getSesso().equalsIgnoreCase("m"))? "checked=\"checked\"" : ""%>>M
       <input type="radio" name="sesso" id="sesso2" value="F" <%=(temp.getRappLegale().getSesso().equalsIgnoreCase("f"))? "checked=\"checked\"" : ""%>>F
    </td>
  </tr>
  
  
  
  
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="opu.stabilimento.soggetto_fisico.data_nascita">Data Nascita</dhv:label>
      </td>
      <td>
      	<input readonly type="text" id="dataNascita" name="dataNascita" size="10" value = "<%=temp.getRappLegale().getDataNascitaString()%>" />
		<a href="#" onClick="cal19.select(document.forms[0].dataNascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
      </td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.comune_nascita">Comune Nascita</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "comuneNascita" name="comuneNascita" value="<%=temp.getRappLegale().getComuneNascita()%>">
    </td>
  </tr>
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.provincia_nascita">Provincia Nascita</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "provinciaNascita" name="provinciaNascita" value="<%=temp.getRappLegale().getProvinciaNascita()%>">
    </td>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.mail">Email</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "email" name="email" value="<%=temp.getRappLegale().getEmail()%>">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.telefono">Telefono</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "telefono1" name="telefono1" value="<%=temp.getRappLegale().getTelefono1()%>">
    </td>
    
  </tr>
  
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.telefono2">Telefono secondario</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "telefono2" name="telefono2" value="<%=temp.getRappLegale().getTelefono2()%>">
    </td>
    
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="opu.stabilimento.soggetto_fisico.fax">Fax</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" id = "fax" name="fax" value="<%=temp.getRappLegale().getFax()%>">
    </td>
    
  </tr>
  
</table>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="opu.stabilimento.soggetto_fisico.indirizzo_residenza">Indirizzo </dhv:label></strong>
	  </th></tr>
	  <jsp:useBean id="stabilimento"
	class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
</br></br>

	  	<tr>
		<td class="formLabel"><dhv:label name="opu.stabilimento.inregione"></dhv:label>
		</td>
		<td><select name="inregioneSedeLegale" onchange="checkLineaProduttiva()" id="inregioneSedeLegale">
			<option value="si"
				<%=(!stabilimento.isFlagFuoriRegione()) ? "selected"
								: ""%>>SI</option>
			<option value="no"
				<%=(stabilimento.isFlagFuoriRegione()) ? "selected"
						: ""%>>NO</option>
		</select></td>
	</tr>
 <tr>

		<td nowrap class="formLabel"><dhv:label
			name="opu.stabilimento.soggetto_fisico.provincia_residenza"></dhv:label></td>
		<td><select name="searchcodeIdprovincia"
			id="searchcodeIdprovincia">
			<option value = "<%=temp.getRappLegale().getIndirizzo().getIdProvincia() %>" selected="selected"><%=temp.getRappLegale().getIndirizzo().getDescrizione_provincia() %></option>

		</select><font color="red">(*)</font> 
		
		<input type="hidden" name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto" />
		</td>
	</tr>
	<tr>
		<td nowrap class="formLabel" name="province" id="province"><dhv:label
			name="opu.stabilimento.soggetto_fisico.comune_residenza"></dhv:label></td>
		<td><select name="searchcodeIdComune" id="searchcodeIdComune">
			<option value = "<%=temp.getRappLegale().getIndirizzo().getComune() %>" selected="selected"><%=temp.getRappLegale().getIndirizzo().getDescrizioneComune() %></option>

		</select><font color="red">(*)</font> <%-- ComuniList.getHtmlSelect("searchcodeIdComune", -1) --%> 
		
		<input type="hidden" name="searchcodeIdComuneTesto"	id="searchcodeIdComuneTesto" /></td>
	</tr>
<tr>
    <td nowrap class="formLabel">
      <dhv:label name="opu.stabilimento.soggetto_fisico.provincia_residenza">INDIRIZZO</dhv:label>
    </td>
    <td>
<select name = "via" id = "via">
<option value = "<%=temp.getRappLegale().getIndirizzo().getIdIndirizzo() %>" selected="selected"><%=temp.getRappLegale().getIndirizzo().getVia() %></option>
</select>

<input type = "hidden" name = "viaTesto" id = "viaTesto"/>

    </td>
  </tr>
	
		<tr>
		<td nowrap class="formLabel"><dhv:label
			name="opu.stabilimento.soggetto_fisico.cap_residenza"></dhv:label></td>
		<td><input readonly type="text" size="28" id="cap" name="cap"
			maxlength="5" value="<%=temp.getRappLegale().getIndirizzo().getCap() %>">  </td>
	</tr>

 
	 <tr class="containerBody">
		<td class="formLabel" nowrap><dhv:label
			name="opu.sede_legale.latitudine"></dhv:label></td>
		<td><input readonly type="text" id="latitudine" name="latitudine"
			size="30" value="<%=temp.getRappLegale().getIndirizzo().getLatitudine() %>"></td>
	</tr>
	<tr class="containerBody">
		<td class="formLabel"><dhv:label
			name="opu.sede_legale.longitudine"></dhv:label></td>
		<td><input readonly type="text" id="longitudine" name="longitudine"
			size="30" value="<%=temp.getRappLegale().getIndirizzo().getLongitudine() %>"></td>
	</tr>
	<!-- <tr style="display: none">
		<td colspan="2"><input id="coordbutton" type="button"
			value="Calcola Coordinate"
			onclick="javascript:showCoordinate(document.getElementById('via').value, document.forms['addSede'].comune.value,document.forms['addSede'].provincia.value, document.forms['addSede'].cap.value, document.forms['addSede'].latitudine, document.forms['addSede'].longitudine);" />
		</td>
	</tr> -->
    
  <!-- <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
<select name = "via" id = "via">
<option value = "<%=Operatore.getSedeLegale().getIdIndirizzo() %>" selected="selected"><%=Operatore.getSedeLegale().getVia() %></option>
</select>  -->


    </td>
  </tr>
  
	  
  
 
</table>
</br></br>
 


  <!--   <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Indirizzo</dhv:label></strong>
      
    </th>
  </tr>
   
  
  
 
  
<tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Provincia residenza</dhv:label>
    </td>
    <td> -->
      <input  type="hidden" size="30" maxlength="50" id = "addressLegaleCountry" name="addressLegaleCountry" value="<%=temp.getRappLegale().getIndirizzo().getIdProvincia() %>">
      <input  type="hidden" size="30" maxlength="50" id = "addressLegaleCountryTesto" name="addressLegaleCountryTesto" value="<%=temp.getRappLegale().getIndirizzo().getDescrizione_provincia() %>">

      <input  type="hidden" size="30" maxlength="50" id = "addressLegaleCity" name="addressLegaleCity" value="<%=temp.getRappLegale().getIndirizzo().getComune() %>">
      <input  type="hidden" size="30" maxlength="50" id = "addressLegaleCityTesto" name="addressLegaleCityTesto" value="<%=temp.getRappLegale().getIndirizzo().getDescrizioneComune() %>">

      <input  type="hidden" size="30" maxlength="50" id = "addressLegaleLine1" name="addressLegaleLine1" value="<%=temp.getRappLegale().getIndirizzo().getIdIndirizzo() %>">
      <input  type="hidden" size="30" maxlength="50" id = "addressLegaleLine1Testo" name="addressLegaleLine1Testo" value="<%=temp.getRappLegale().getIndirizzo().getVia() %>">
   	 <input  type="hidden" size="30" maxlength="50" id = "addressLegaleLine1input" name="addressLegaleLine1input" value="">
     <input  type="hidden" size="30" maxlength="50" id = "addressLegaleCityinput" name="addressLegaleCityinput" value="">
   
    <!-- </td>
  </tr>

  
  </table> -->

</br></br>
  
 
 <!--<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Dettagli addizionali </dhv:label></strong>
      
    </th>
  </tr>
 <tr>
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="">Note</dhv:label>
    </td>
    <td><TEXTAREA NAME="note" ROWS="3" COLS="50"><%= toString(temp.getNote()) %></TEXTAREA></td>
  </tr> 

</table>

</br></br> --> 


</form>
