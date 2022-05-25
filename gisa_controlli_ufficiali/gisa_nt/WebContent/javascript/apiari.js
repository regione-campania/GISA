/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/



var idOperatoreTrovato = -1 ;
function ricercaImpresa(pIva)
{
	PopolaCombo.verificaImpresa(pIva,ricercaImpresaCallBack);
}

function ricercaImpresaCallBack (value)
{
	idOperatoreTrovato = value.idOperatore;
	if (value != null && parseInt(value.idOperatore)>0)
	{
if (document.getElementById('ragioneSociale')!=null)
{
	document.getElementById('ragioneSociale').value = value.ragioneSociale ;
}

if (document.getElementById('partitaIva')!=null)
{
	document.getElementById('partitaIva').value = value.partitaIva ;
}
if (document.getElementById('codFiscale')!=null)
{
	document.getElementById('codFiscale').value = value.codFiscale ;
}

ricercaSoggettoFisicoCallBack (value.rappLegale);




if (document.getElementById('searchcodeIdprovinciaSLinput')!=null)
{
	document.getElementById('searchcodeIdprovinciaSLinput').value = value.listaSediOperatore[0].descrizione_provincia ;
	document.getElementById('searchcodeIdprovinciaSL').appendChild(new Option(value.listaSediOperatore[0].descrizione_provincia,value.listaSediOperatore[0].idProvincia,true,true)) ;
}
else
if (document.getElementById('searchcodeIdprovinciainput')!=null)
{
	document.getElementById('searchcodeIdprovinciainput').value = value.listaSediOperatore[0].descrizione_provincia ;
	document.getElementById('searchcodeIdprovincia').appendChild(new Option(value.listaSediOperatore[0].descrizione_provincia,value.listaSediOperatore[0].idProvincia,true,true)) ;
}

if (document.getElementById('searchcodeIdComuneSLinput')!=null)
{
	document.getElementById('searchcodeIdComuneSLinput').value = value.listaSediOperatore[0].descrizioneComune ;
	
	document.getElementById('searchcodeIdComuneSL').appendChild(new Option(value.listaSediOperatore[0].descrizioneComune,value.listaSediOperatore[0].comune,true,true)) ;
}
else
if (document.getElementById('searchcodeIdComuneinput')!=null)
{
	document.getElementById('searchcodeIdComuneinput').value = value.listaSediOperatore[0].descrizioneComune ;
	
	document.getElementById('searchcodeIdComune').appendChild(new Option(value.listaSediOperatore[0].descrizioneComune,value.listaSediOperatore[0].comune,true,true)) ;
}
if (document.getElementById('viaSLinput')!=null)
{
	document.getElementById('viaSLinput').value = value.listaSediOperatore[0].via ;
	document.getElementById('viaSL').appendChild(new Option(value.listaSediOperatore[0].via,value.listaSediOperatore[0].idIndirizzo,true,true)) ;

}
else
if (document.getElementById('viainput')!=null)
{
	document.getElementById('viainput').value = value.listaSediOperatore[0].via ;
	document.getElementById('via').appendChild(new Option(value.listaSediOperatore[0].via,value.listaSediOperatore[0].idIndirizzo,true,true)) ;

}
if(window.opener==null)
	{
$("#dialog").dialog("open");
	}
else
	{
	alert('Impresa esistente');
	}
	}
	else
	{
		alert('Operatore non Esistente');
	}



}

function ricercaSoggettoFisico(cfSoggetto)
{
	PopolaCombo.verificaSoggetto(cfSoggetto,ricercaSoggettoFisicoCallBack);
}
function ricercaSoggettoFisicoStab(cfSoggetto)
{
	PopolaCombo.verificaSoggetto(cfSoggetto,ricercaSoggettoFisicoStabCallBack);
}

function ricercaSoggettoFisicoStabCallBack( value )
{
if (parseInt(value.idSoggetto)>0)
{
	
	if (document.getElementById('nomeStab')!=null)
	{
		document.getElementById('nomeStab').value = value.nome ;
	}
	
	
	if (document.getElementById('cognomeStab')!=null)
	{
		document.getElementById('cognomeStab').value = value.cognome ;
	}
	if (document.getElementById('sesso1Stab')!=null && value.sesso=='M')
	{
		document.getElementById('sesso1Stab').checked=true;
	}
	else
	{
		if (document.getElementById('sesso2Stab')!=null && value.sesso=='F')
		{
			document.getElementById('sesso2Stab').checked=true;
		}
	}
	if (document.getElementById('dataNascitaStab')!=null)
	{
		document.getElementById('dataNascitaStab').value = value.dataNascitaString ;
	}
	if (document.getElementById('comuneNascitaStab')!=null)
	{
		document.getElementById('comuneNascitaStab').value = value.comuneNascita ;
	}
	if (document.getElementById('provinciaNascitaStab')!=null)
	{
		document.getElementById('provinciaNascitaStab').value = value.provinciaNascita ;
	}
	if (document.getElementById('addressLegaleCountryStabinput')!=null)
	{
		document.getElementById('addressLegaleCountryStabinput').value = value.indirizzo.descrizione_provincia ;
		document.getElementById('addressLegaleCountryStab').appendChild(new Option(value.indirizzo.descrizione_provincia,value.indirizzo.idProvincia,true,true)) ;

		
	}
	if (document.getElementById('addressLegaleCityStabinput')!=null)
	{
		document.getElementById('addressLegaleCityStabinput').value = value.indirizzo.descrizioneComune ;
		document.getElementById('addressLegaleCityStab').appendChild(new Option(value.indirizzo.descrizioneComune,value.indirizzo.comune,true,true)) ;

	}
	if (document.getElementById('addressLegaleLine1Stabinput')!=null)
	{
		document.getElementById('addressLegaleLine1Stabinput').value = value.indirizzo.via ;
		document.getElementById('addressLegaleLine1Stab').appendChild(new Option(value.indirizzo.via,value.indirizzo.idIndirizzo,true,true)) ;
	}
	
	if (document.getElementById('codFiscaleSoggettoStab')!=null)
	{
		document.getElementById('codFiscaleSoggettoStab').value = value.codFiscale ;
		
	}
	if (document.getElementById('documentoIdentitaStab')!=null)
	{
		document.getElementById('documentoIdentitaStab').value = value.documentoIdentita ;
		
	}

}
else
{
	alert('Soggetto Fisco con codice fiscale Non Esistente');	
}
}

function ricercaSoggettoFisicoCallBack( value )
{
if (parseInt(value.idSoggetto)>0)
{
	
	if (document.getElementById('nome')!=null)
	{
		document.getElementById('nome').value = value.nome ;
	}
	
	
	if (document.getElementById('cognome')!=null)
	{
		document.getElementById('cognome').value = value.cognome ;
	}
	if (document.getElementById('sesso1')!=null && value.sesso=='M')
	{
		document.getElementById('sesso1').checked=true;
	}
	else
	{
		if (document.getElementById('sesso2')!=null && value.sesso=='F')
		{
			document.getElementById('sesso2').checked=true;
		}
	}
	if (document.getElementById('dataNascita')!=null)
	{
		document.getElementById('dataNascita').value = value.dataNascitaString ;
	}
	if (document.getElementById('comuneNascita')!=null)
	{
		document.getElementById('comuneNascita').value = value.comuneNascita ;
	}
	if (document.getElementById('provinciaNascita')!=null)
	{
		document.getElementById('provinciaNascita').value = value.provinciaNascita ;
	}
	if (document.getElementById('addressLegaleCountryinput')!=null)
	{
		document.getElementById('addressLegaleCountryinput').value = value.indirizzo.descrizione_provincia ;
		document.getElementById('addressLegaleCountry').appendChild(new Option(value.indirizzo.descrizione_provincia,value.indirizzo.idProvincia,true,true)) ;

		
	}
	if (document.getElementById('addressLegaleCityinput')!=null)
	{
		document.getElementById('addressLegaleCityinput').value = value.indirizzo.descrizioneComune ;
		document.getElementById('addressLegaleCity').appendChild(new Option(value.indirizzo.descrizioneComune,value.indirizzo.comune,true,true)) ;

	}
	if (document.getElementById('addressLegaleLine1input')!=null)
	{
		document.getElementById('addressLegaleLine1input').value = value.indirizzo.via ;
		document.getElementById('addressLegaleLine1').appendChild(new Option(value.indirizzo.via,value.indirizzo.idIndirizzo,true,true)) ;
	}
	
	if (document.getElementById('codFiscaleSoggetto')!=null)
	{
		document.getElementById('codFiscaleSoggetto').value = value.codFiscale ;
		
	}
	if (document.getElementById('documentoIdentita')!=null)
	{
		document.getElementById('documentoIdentita').value = value.documentoIdentita ;
		
	}

}
else
{
	alert('Soggetto Fisco con codice fiscale Non Esistente');	
}
}


function verificaSoggetto(codiceFiscaleField)
{
	if (codiceFiscaleField != null && codiceFiscaleField.value != 'n.d')
	{
	cf = codiceFiscaleField.value ;
	if (cf!='' && codiceFiscaleField.id == 'codFiscaleSoggetto')
		PopolaCombo.verificaSoggetto(cf,verificaSoggettoImpresaCallback);
	else
		if (cf!='' && codiceFiscaleField.id == 'codFiscaleSoggettoStab')
			PopolaCombo.verificaSoggetto(cf,verificaSoggettoStabilimentoCallback);
		else
			
		checkForm(document.addticket);

	}else
	{
		checkForm(document.addticket);
		}
}

/*Usato per le volture*/
function verificaSoggettoStabilimentoCallback(value)
{
	
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
//	telefono1Esistente = value.telefono1;
//	telefono2Esistente = value.telefono2;
//	emailEsistente = value.email;
//	faxEsistente = value.fax;
	comuneResidenzaEsistente = value.indirizzo.comune ; 
	provinciaResidenzaEsistente = value.indirizzo.provincia ; 
	indirizzoResidenzaEsistente = value.indirizzo.via ; 
	descrizioneComune = value.indirizzo.descrizioneComune ;
	
	idAsl = value.idAsl ;
	descrAsl = value.descrAsl ;
//alert('stop');
	
	nome = document.getElementById('nomeStab').value ;
	cognome = document.getElementById('cognomeStab').value ;
	if (document.getElementById('sesso1Stab').checked)
		sesso = document.getElementById('sesso1Stab').value ;
	else
		sesso = document.getElementById('sesso2Stab').value ;
	dataNascita = document.getElementById('dataNascitaStab').value ;
	comuneNascita = document.getElementById('comuneNascitaStab').value ;
	provinciaNascita = document.getElementById('provinciaNascitaStab').value ;
	documento = document.getElementById('documentoIdentitaStab').value ;
//	tel1 = document.getElementById('telefono1Stab').value ;
//	tel2 = document.getElementById('telefono2Stab').value ;
//	fax = document.getElementById('faxStab').value ;
	provinciaNascita = document.getElementById('emailStab').value ;
	comuneResidenza = document.getElementById('addressLegaleCityStabinput').value ;
	provinciaResidenza = document.getElementById('addressLegaleCountryStabinput').value ;
	indirizzoResidenza = document.getElementById('addressLegaleLine1Stabinput').value ;
	if (documentoIdentitaEsistente==null)
		documentoIdentitaEsistente='';
	

	if (nomeEsistente !=nome || cognomeEsistente != cognome || sessoEsistente != sesso ||
			dataNascitaEsistente != dataNascita || comuneNascitaEsistente != comuneNascita || provinciaNascitaEsistente !=provinciaNascita ||
			documentoIdentitaEsistente!= documento || // telefono1Esistente != tel1 || telefono2Esistente != tel2 ||
			 comuneResidenzaEsistente !=comuneResidenza ||provinciaResidenza !=provinciaResidenza || indirizzoResidenzaEsistente!=indirizzoResidenza  )
	{

		
			document.getElementById("intestazioneStab").innerHTML="ATTENZIONE : PER IL CF INSERITO (RAPPRESENTANTE STABILIMENTO) ESISTE UN SOGGETTO ANAGRAFATO CON I SEGUENTI DATI. VUOI SOVRASCRIVERE ?" ;
		
	document.getElementById('nomeSoggettoStab').innerHTML = nomeEsistente ; 
 	document.getElementById('cognomeSoggettoStab').innerHTML =cognomeEsistente ;

 		document.getElementById('sessoSoggettoStab').innerHTML = sessoEsistente ;
 		document.getElementById('dataNascitaSoggettoStab').innerHTML =dataNascitaEsistente ;
		document.getElementById('comuneNascitaSoggettoStab').innerHTML =comuneNascitaEsistente ; 
		document.getElementById('documentoSoggettoStab').innerHTML =documentoIdentitaEsistente ;
		document.getElementById('provinciaNascitaSoggettoStab').innerHTML =provinciaNascitaEsistente ;
//		document.getElementById('telefono1SoggettoStab').innerHTML =telefono1Esistente ;
//		document.getElementById('telefono2SoggettoStab').innerHTML =telefono2Esistente ;
//		document.getElementById('mailSoggettoStab').innerHTML = emailEsistente;
//		document.getElementById('faxSoggettoStab').innerHTML = faxEsistente;
		document.getElementById('comuneResidenzaSoggettoStab').innerHTML = descrizioneComune ;
		document.getElementById('provinciaResidenzaSoggettoStab').innerHTML = value.indirizzo.descrizione_provincia ;
		document.getElementById('indirizzoResidenzaSoggettoStab').innerHTML =indirizzoResidenzaEsistente ;	


			document.getElementById("azioneStab").style.display="";		
		
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

		$('#dialog5').css('top',  winH/2-$('#dialog5').height()/2);
		$('#dialog5').css('left', winW/2-$('#dialog5').width()/2);
		$('#dialog5').fadeIn(2000);   
	}
	else
	{
		checkForm(document.addticket);
		}    
		 										
	}
	else
	{
		checkForm(document.addticket);
	}
	

}


function giorni_differenza(data1,data2)
{
	
	anno1 = parseInt(data1.substr(6),10);
	mese1 = parseInt(data1.substr(3, 2),10);
	giorno1 = parseInt(data1.substr(0, 2),10);
	anno2 = parseInt(data2.substr(6),10);
	mese2 = parseInt(data2.substr(3, 2),10);
	giorno2 = parseInt(data2.substr(0, 2),10);

	var dataok1=new Date(anno1, mese1-1, giorno1);
	var dataok2=new Date(anno2, mese2-1, giorno2);

	differenza = dataok2-dataok1;    
	giorni_diff = new String(differenza/86400000);
	//alert('diff');
	//alert(giorni_diff);
	return giorni_diff;
}

function verificaSoggettoImpresaCallback(value)
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
//	telefono1Esistente = value.telefono1;
//	telefono2Esistente = value.telefono2;
//	emailEsistente = value.email;
//	faxEsistente = value.fax;
	comuneResidenzaEsistente = value.indirizzo.comune ; 
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
//	tel1 = document.getElementById('telefono1').value ;
//	tel2 = document.getElementById('telefono2').value ;
//	fax = document.getElementById('fax').value ;
//	email = document.getElementById('email').value ;
	comuneResidenza = document.getElementById('addressLegaleCity').value ;
	provinciaResidenza = document.getElementById('addressLegaleCountry').options[document.getElementById('addressLegaleCountry').selectedIndex ].text ;
	
	indirizzoResidenza = document.getElementById('addressLegaleLine1').options[document.getElementById('addressLegaleLine1').selectedIndex ].text ;
	
	if (documentoIdentitaEsistente==null)
		documentoIdentitaEsistente='';
	
	
	if (nomeEsistente !=nome || cognomeEsistente != cognome || sessoEsistente != sesso ||
			dataNascitaEsistente != dataNascita || comuneNascitaEsistente != comuneNascita || provinciaNascitaEsistente !=provinciaNascita ||
			documentoIdentitaEsistente!= documento  || comuneResidenzaEsistente !=comuneResidenza ||provinciaResidenzaEsistente !=provinciaResidenza || indirizzoResidenzaEsistente!=indirizzoResidenza  )
	{

		
			document.getElementById("intestazione").innerHTML="ATTENZIONE : PER IL CF INSERITO ESISTE UN SOGGETTO ANAGRAFATO CON I SEGUENTI DATI. VUOI SOVRASCRIVERE ?" ;
		
		document.getElementById('nomeSoggetto').innerHTML = nomeEsistente ; 
 		document.getElementById('cognomeSoggetto').innerHTML =cognomeEsistente ;
 		document.getElementById('sessoSoggetto').innerHTML = sessoEsistente ;
 		document.getElementById('dataNascitaSoggetto').innerHTML =dataNascitaEsistente ;
		document.getElementById('comuneNascitaSoggetto').innerHTML =comuneNascitaEsistente ; 
		document.getElementById('documentoSoggetto').innerHTML =documentoIdentitaEsistente ;
		document.getElementById('provinciaNascitaSoggetto').innerHTML =provinciaNascitaEsistente ;
		//document.getElementById('telefono1Soggetto').innerHTML =telefono1Esistente ;
		//document.getElementById('telefono2Soggetto').innerHTML =telefono2Esistente ;
		//document.getElementById('mailSoggetto').innerHTML = emailEsistente;
		//document.getElementById('faxSoggetto').innerHTML = faxEsistente;
		document.getElementById('comuneResidenzaSoggetto').innerHTML = descrizioneComune ;
		document.getElementById('provinciaResidenzaSoggetto').innerHTML = value.indirizzo.descrizione_provincia ;
		document.getElementById('indirizzoResidenzaSoggetto').innerHTML =indirizzoResidenzaEsistente ;	
		
			document.getElementById("azione").style.display="";		
		
		
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
		if (document.getElementById('codFiscaleSoggettoStab')!= null)
			PopolaCombo.verificaSoggetto(document.getElementById('codFiscaleSoggettoStab'),verificaSoggettoStabilimentoCallback);
		else
			checkForm(document.addticket);

		}    
		 										
	}
	else
	{

		if (document.getElementById('codFiscaleSoggettoStab')!= null)
			PopolaCombo.verificaSoggetto(document.getElementById('codFiscaleSoggettoStab'),verificaSoggettoStabilimentoCallback);
		else
			checkForm(document.addticket);

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

function trim(stringa){
    while (stringa.substring(0,1) == ' '){
        stringa = stringa.substring(1, stringa.length);
    }
    while (stringa.substring(stringa.length-1, stringa.length) == ' '){
        stringa = stringa.substring(0,stringa.length-1);
    }
    return stringa;
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
                   
                   var ui ;
                    select.children( "option" ).each(function() {

                        //if ( $( this ).text().match( matcher.test(text) ) ) {
                        if ( matcher.test(this.text) ) {
                            
                            this.selected = valid = true;
                            return false;
                        }
                    });
                 
                    if ( valid==false ) {
                        
                       
                        
                       
                        if(select[0].id =='addressLegaleCity' && (document.getElementById('nazioneResidenza') == null || document.getElementById('nazioneResidenza').value=='106'))
                    	{
                    	
                    	document.getElementById('addressLegaleCityinput').value= "" ;
                    	if(attivataModificaTitolare=="false")
                    		alert('COMUNE NON VALIDO PER LA PROVINCIA SELEZIONATA');
                    	else
                    		alert('COMUNE NON VALIDO');
                    	}
                        else
                        	{
                        	if(select[0].id =='addressLegaleCity' && (document.getElementById('nazioneResidenza') != null ))
                        			{
                        			
                        		document.getElementById(select[0].id+'Testo').value = value ;
         						select.append('<option value=-1 selected>'+value+'</option>');
                             	return true ;
                        			}
                        	
                        	
                        	}
                        
                        if(select[0].id =='searchcodeIdComune' && (document.getElementById('nazioneSedeLegale') == null || document.getElementById('nazioneSedeLegale').value=='106') )
                        	{
                        	inReg=document.getElementById("inRegione").value;	
                        	
                        	document.getElementById('searchcodeIdComuneinput').value= "" ;
                        	alert('COMUNE NON VALIDO PER LA PROVINCIA SELEZIONATA');
                        	}
                        else
                        	{
                        	 if(select[0].id =='searchcodeIdComune' && (document.getElementById('nazioneSedeLegale') != null))
                        			 {
                        	
                        		 
                        	document.getElementById(select[0].id+'Testo').value = value ;
                        	
                        			 }
                        	 else
                        		 if(select[0].id =='searchcodeIdComuneStab' )
                             	{
                             	
                             	document.getElementById('searchcodeIdComuneStabinput').value= "" ;
                             	alert('COMUNE NON VALIDO PER LA PROVINCIA SELEZIONATA');
                             	}
                        		 else{
                                 	
                                 	
                              		if(document.getElementById('data_assegnazione_detentore')!=null && attivataModificaTitolare=="false")
                              			{
                             		 if(confirm('NESSUNA PERSONA ANAGRAFATA CON QUESTO CODICE FISCALE. VUOI CENSIRLA ORA?')==true)
                             		 {
                             			 document.getElementById(select[0].id+'input').value='';
                             			 document.getElementById(select[0].id+'input').readOnly =true;
                             			 document.getElementById('detentore').style.display="";
                             			//document.getElementById('nominativoDet').style.display=""; --modificato
                             			document.getElementById('calcoloCF').style.display="";
                             			 $( "#addressLegaleCity" ).combobox();
                             			                             			
                             			
                             			 
                             		 }
                             		 else
                             			 {
                                 		 document.getElementById(select[0].id+'input').value= "" ;
                             			 }
                              			}
                              		else
                              			{
                              			document.getElementById(select[0].id+'input').value= "" ;
                              			if(attivataModificaTitolare=="false")
                              				{
                              					alert('NESSUNA VOCE TROVATA CON I FILTRI SPECIFICATI');
                              				}
                              			}
                             			 
                              	
                             	
                             }
                        	}
                        
                       
                        
                        
                    
                    }
                    else
                    	{
                    	
                    	
                    	}
                    
                }
                input = $( "<input type='text' size='26' id='"+select[0].id+"input' class='"+select[0].className +"' name='"+select[0].id+"input' onclick='pulisciCampo(this)' onclick='ripristinaCampo(this)'>" )
                    .appendTo( wrapper )
                    .val( trim(value) )
                    .attr( "title", "" )
                   
                    .autocomplete({
                        delay: 0,
                        minLength: 0,
                        
                        source:  function( request, response ) {
                       	 
                        	 
                        	 
                        	 if (select[0].id=='searchcodeIdComune')
                        		 {
                        		 
                        		 inReg=document.getElementById("inRegione").value;	
                             	
                        		 }
                        		 
                        	idAsl  = '-1';
                        	nazione = '106'; //DEFAULT ITALIA
                    	if (document.getElementById('idAsl')!=null)
                    		idAsl = document.getElementById('idAsl').value;
                    
                            $.ajax(
                                    {	
                                        url:  "./ServletComuni?nazione="+nazione+"&nome="+request.term+"&combo="+select[0].id+"&idProvincia=-1&idComune=-1&inRegione="+inReg+"&idAsl="+idAsl,
                                      	dataType: "json",
                                		data: {
                                    			style: "full",
                                    			maxRows: 12,
                                    			name_startsWith: request.term
                                			   },
                                			   error: function (textStatus, errorThrown) {
                                                   
                                                  },
                                		success:function( data ) {
                                			
                                           		
                                						arrayItem = new Array() ; 
														
                                	
                                					if (select[0].id=='codFiscaleSoggetto' || select[0].id=='codFiscaleSoggettoAdd')
                                						{
                                						
                                						response( $.map( data, function( item ) {
                											
        	            									select.append('<option value='+item.codFiscale+'>'+item.descrizione  +'</option>');
        	                                                return {
        	                                                    label:item.descrizione,
        	                                                    value: item.descrizione,
        	                                                    cf: item.descrizione ,
        	                                                   id:item.codice,
        	                                                   nominativo:item.cognome+' '+item.nome
        	                                                  
        	                                                    
        	                                                }
        	                                            }));
                                						}
                                					else
                                						{
    												response( $.map( data, function( item ) {
            											
    	            									select.append('<option value='+item.value+'>'+item.descrizione+'</option>');
    	                                                return {
    	                                                    label: item.descrizione.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" +$.ui.autocomplete.escapeRegex(request.term) +")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>" ),
    	                                                    value: item.descrizione ,
    	                                                    idAsl: item.idAsl ,
    	                                                    descrizioneAsl : item.descrizioneAsl,
    	                                                    provincia:item.descrizioneProvincia,
    	                                                    idProvincia:item.idProvincia
    	                                                  
    	                                                    
    	                                                }
    	                                            }));
    	                                        	
                                						}

    												
                                    	
                                     
                                }
                            });
                         
                        },
                        select: function( event, ui ) {
                        	if (select[0].id=='searchcodeIdComune')
        					{
                        		
        							document.getElementById('searchcodeIdprovinciaTesto').value=ui.item.provincia;
        							document.getElementById('searchcodeIdprovincia').value=ui.item.idProvincia;
        							if(document.getElementById('aslRomaBdn')!=null)
        							{
        								if(ui.item.value.toUpperCase()!='ROMA' )
        								{
        									document.getElementById('aslRomaBdn').disabled='disabled';
        									document.getElementById('aslRomaBdn').value='';
        								}
        								else
        									document.getElementById('aslRomaBdn').disabled=false;
        							}
        							document.getElementById('viaTesto').value="";
        							
        					
        					}
                        	if (select[0].id=='addressLegaleCity')
        					{
                        			
        							document.getElementById('addressLegaleCountryTesto').value=ui.item.provincia;
        							document.getElementById('addressLegaleCountry').value=ui.item.idProvincia;
        							document.getElementById('addressLegaleLine1Testo').value="";
        					
        					}
                        	if (select[0].id=='codFiscaleSoggetto')
        					{
                        		
                        			
        							document.getElementById('codFiscaleSoggettoinput').value=ui.item.cf;
                        			document.getElementById('codFiscaleSoggetto').value=ui.item.cf;
                        			document.getElementById('idSoggettoFisico').value=ui.item.id;
                        		document.getElementById('nominativo').value=ui.item.nominativo;
        							
        					
        					}
                        	
                        	if (select[0].id=='codFiscaleSoggettoAdd')
        					{
                        		
                        			
        							document.getElementById('codFiscaleSoggettoAddinput').value=ui.item.cf;
                        			document.getElementById('codFiscaleSoggettoAdd').value=ui.item.cf;
                        			document.getElementById('idSoggettoFisico').value=ui.item.id;
                        		document.getElementById('nominativo').value=ui.item.nominativo;
        							
        					
        					}
                        	
                        	 select.children( "option" ).each(function() {
                                 
                                 if ( $( this ).text() == ui.item.value  ) {
                                	
                                     trovato = true ;
                                     this.selected =  "true";
                                     
                                    
                                  
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
    		var Return;
            $( "#dialog" ).dialog({
            	autoOpen: false,
                resizable: false,
                closeOnEscape: false,
                width:300,
                height:180,
                draggable: false,
                modal: true,
                buttons: {
                    "Si": function() {
            			
            				document.location.href='OperatoreAction.do?command=Details&stabId=-1&opId='+idOperatoreTrovato;
            			
                        $( this ).dialog( "close" );
                        return true;
                    },
                    "No": function() {
                        $( this ).dialog( "close" );
                        return false
                    }
                }
            });
        });
    
    $(document).ready(
    		function() {	
    			$('.window .close').click(function (e) 
    			{
    				e.preventDefault();
    				$('#mask').hide();
    				$('.window').hide();
    			});		
    		});

  