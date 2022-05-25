<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.io.*,java.util.*,org.aspcfs.utils.web.*"%>
<%@page import="org.aspcfs.modules.anagrafe_animali.base.Animale"%>
<%@page import="org.aspcfs.modules.system.base.SiteList"%>
<%@page import="org.aspcfs.modules.opu.base.StabilimentoList"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.system.base.SiteList"%>
<%@page import="org.aspcfs.modules.base.Constants"%>
<%@page import="org.aspcfs.utils.ApplicationProperties"%>

<link rel="stylesheet"	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<jsp:useBean id="Cane"	class="org.aspcfs.modules.anagrafe_animali.base.Cane" scope="request" />
<jsp:useBean id="Gatto" class="org.aspcfs.modules.anagrafe_animali.base.Gatto" scope="request" />
<jsp:useBean id="Furetto" class="org.aspcfs.modules.anagrafe_animali.base.Furetto"	scope="request" />
<jsp:useBean id="origine" class="java.lang.String" scope="request"/>
<jsp:useBean id="opId" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="regioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tagliaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="mantelloList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="esitoList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="nazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="comuniList_all" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="provinceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="veterinariList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="veterinariPrivatiList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/dateControl.js"></script>
<script type="text/javascript" src="dwr/interface/DwrUtil.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/PraticaList.js"> </script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/LineaProduttiva.js"> </script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/EventoRilascioPassaporto.js"> </script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/Stabilimento.js"> </script>
<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>

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


<script>
function pulisciCampo(input){
  //Inserire le prime 4 lettere
  if(input.value=='Inserire le prime 4 lettere'){
    input.value='';
  }
}

function ripristinaCampo(input){
  if(input.value==''){
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
      wrapper = this.wrapper = $( "<span>" ).addClass( "ui-combobox" ).insertAfter( select );
      function removeIfInvalid(element) {
        var value = $( element ).val(),
        matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( value ) + "$", "i" ),
        valid = false;
        select.children( "option" ).each(function() {
          if ( matcher.test(this.text) ) {
            this.selected = valid = true;
            return false;
          }
        });
        if ( valid==false ) {
          $( element ).val( "" ).attr( "title", value + " nessuna voce trovata" ).tooltip( "open" );
          select.val( "" );
          setTimeout(function() {
            input.tooltip( "close" ).attr( "title", "" );
          }, 2500 );
          input.data( "autocomplete" ).term = "";
		  select.append('<option value=-1 selected >Seleziona Voce</option>');
          return false;
        }
      }
      var input1;
      input1 = $( "<input id='"+select[0].id+"input' onclick='pulisciCampo(this)' onmouseout='ripristinaCampo(this)'>" );
      input = input1.appendTo( wrapper ).val( value ).attr( "title", "" ).addClass( "ui-state-default ui-combobox-input" ).autocomplete({
        delay: 0,
        minLength: 3,
        source:  function( request, response ) {
        idprovincia = '-1';
        idcomune = '-1';
        inregione = 'si';
        if(select[0].id =='comune_ritrovamento' || select[0].id =='provincia_ritrovamento'){
          if ($("#provincia_ritrovamento").length > 0){
            idprovincia =document.getElementById("provincia_ritrovamento").value ;
          }
          idcomune =  document.getElementById("comune_ritrovamento").value;
          
          if($("input[name=origine_da]:checked").val()=="in_regione")
            inregione = 'si';
          else
        	inregione = 'no';  
        }
        $.ajax({
          url:  "./ServletComuni?nome="+request.term+"&combo="+select[0].id+"&idProvincia="+idprovincia+"&idComune="+idcomune+"&inRegione="+inregione+"&idAsl=-1",
          dataType: "json",
          data: {
            style: "full",
            maxRows: 12,
            name_startsWith: request.term
          },
          success:function( data ) {
            arrayItem = new Array() ; 
			if (select[0].id=='comune_ritrovamento'){
		      response( $.map( data, function( item ) {
        	    item.descrizione=item.descrizione.replace('&agrave;', "à" ).replace('&egrave;', "è" ).replace('&igrave;', "ì" ).replace('&ograve;', "ò" ).replace('&ugrave;', "ù" );
	            select.append('<option value='+item.codice+'>'+item.descrizione+'</option>');
	            return {
	              label: item.descrizione.replace(
	                new RegExp("(?![^&;]+;)(?!<[^<>]*)(" +
	                $.ui.autocomplete.escapeRegex(request.term) +
	                ")(?![^<>]*>)(?![^&;]+;)", "gi"
	                ), "<strong>$1</strong>" ),
	              value: item.descrizione ,
	              idAsl: item.idAsl ,
	              descrizioneAsl : item.descrizioneAsl
	            }
	          }));
	        }else{
              response( $.map( data, function( item ) {
        	    select.append('<option value='+item.codice+'>'+item.descrizione+'</option>');
                return {
                  label: item.descrizione.replace(
                    new RegExp("(?![^&;]+;)(?!<[^<>]*)(" +
                    $.ui.autocomplete.escapeRegex(request.term) +
                    ")(?![^<>]*>)(?![^&;]+;)", "gi"
                    ), "<strong>$1</strong>" ),
                  value: item.descrizione 
                }
              }));
            }
          }
        });
      },
      select: function( event, ui ) {
        if(select[0].id=='comune_ritrovamento'){
          //document.getElementById('idAsl').value = ui.item.idAsl;
          //document.getElementById('descrAsl').value = ui.item.descrizioneAsl;
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
  }).addClass( "ui-widget ui-widget-content ui-corner-left" );
  input.data( "autocomplete" )._renderItem = function( ul, item ) {
    return $( "<li>" ).data( "item.autocomplete", item ).append( "<a>" + item.label + "</a>" ).appendTo( ul );
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
  input.tooltip({
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
  $( "#provincia_ritrovamento" ).combobox();
  $( "#comune_ritrovamento" ).combobox();
});

</script>

<%
	Animale animale = Cane;
	if (Furetto.getIdSpecie() == 3) {
		animale = (Animale) Furetto;
	} else if (Gatto.getIdSpecie() == 2) {
		animale = (Animale) Gatto;
	} else {
		animale = (Animale) Cane;
	}
%>


<script>
  var campo ;
  var win;
  function openDivSterilizzazione(){
  	if (document.addAnimale.flagSterilizzazione.checked==true) { 
	  document.getElementById('divster').style.display='' 
	}else{
  	  document.getElementById('divster').style.display='none'
	}
  }

  function verificaInserimentoAnimale (campoIn){
	campo = campoIn ;
	if(campo.value.length==15)
	  DwrUtil.verificaInserimentoAnimale(campo.value,<%=User.getUserId()%>,verificaInserimentoAnimaleCallBack);
	}

  function verificaInserimentoAnimaleCallBack(value){
    if (value.idEsito=='-1' ){
	  alert(value.descrizione);
	  campo.value="";
	}
	if (value.idEsito=='2' ){
	  alert(value.descrizione);
	  campo.value="";
	}
	if ( value.idEsito=='4'){
	  if ( document.forms[0].ruolo.value != <%=ApplicationProperties.getProperty("ID_RUOLO_HD1")%> 
		&& document.forms[0].ruolo.value != <%=ApplicationProperties.getProperty("ID_RUOLO_HD2")%> &&
		document.forms[0].ruolo.value != <%=ApplicationProperties.getProperty("ID_RUOLO_REFERENTE_ASL")%> &&
		document.forms[0].ruolo.value != <%=ApplicationProperties.getProperty("ID_RUOLO_AMMINISTRATORE_ASL")%> &&
		document.forms[0].ruolo.value != <%=ApplicationProperties.getProperty("ID_RUOLO_ANAGRAFE_CANINA")%>){
		alert( value.descrizione);
		campo.value="";
	  }
	}
	if (value.idEsito=='3' ){
  	  if ( document.forms[0].ruolo.value == '24' || document.forms[0].ruolo.value == '37'){
	    alert( value.descrizione);
		campo.value="";
	  }
	}
  }

  formTest = true;
  message = "";

function checkForm(form) {
	formTest = true;
	message = "";
	var nessuna_origine=false;
	
  if($("#abilita_origine").val()=="true"){
	   // CONTROLLO ORIGINE ANIMALE: SE FUORI REGIONE I DATI SONO SEMPRE OBBLIGATORI
	  if (form.origine_da != null &&  form.origine_da.value!=""){
		   if (form.tipo_origine != null &&  form.tipo_origine.value!=""){
		  		if($("input[name=tipo_origine]:checked").val() == "ritrovamento"){
		    		if(form.provincia_ritrovamento.value==""  || form.comune_ritrovamento.value=="" || 
		       		form.indirizzo_ritrovamento.value=="" ||  form.data_ritrovamento.value==""){
						 message += label("","- Attenzione, inserire tutti i dati inerenti al luogo di ritrovamento.\r\n");
			       		formTest = false;
					}
		  		}else{
		    		if(form.idProprietarioProvenienza.value=="-1"){
		    			message += label("","- Attenzione, inserire il proprietario di origine.\r\n");
		       			formTest = false;	  
					}
		  		}
		   }else if($("input[name=origine_da]:checked").val() == "nazione_estera"){
			   if (form.idNazioneProvenienza.value=="-1" ){
				    message += label("","- Attenzione, inserire la Nazione di provenienza estera.\r\n");
					formTest = false;	  
				} // CONTROLLO CAMPO NOTE --> size>10 se scritto
				/*if (form.idNazioneProvenienza.value!="-1" ){
					if(form.noteNazioneProvenienza.value.length>0 && form.noteNazioneProvenienza.value.length<10){
						message += label("","- Attenzione, il campo 'Note' del flag 'Provenienza da nazione estera' deve avere almeno 10 caratteri se inizializzato.\r\n");
						formTest = false;	  
					}
				} */ 
		   }else{
				message += label("","- Attenzione, inserire i dati inerenti all'origine dell'animale.\r\n");
			    formTest = false; 
		   }
	   }
	
	
		if (form.flagFuoriRegione != null &&  form.flagFuoriRegione.checked && form.idRegioneProvenienza.value=="-1" ){
		    message += label("","- Attenzione, inserire la regione dell'anagrafe di provenienza.\r\n");
			formTest = false;	  
		} // CONTROLLO CAMPO NOTE --> size>10 se scritto
		/*if (form.flagFuoriRegione!= null &&  form.flagFuoriRegione.checked && form.flagFuoriRegione.value!="-1" ){ 
			if(form.noteAnagrafeFr.value.length>0 && form.noteAnagrafeFr.value.length<10){
				message += label("","- Attenzione, il campo 'Note' del flag 'Provenienza da anagrafe altra regione' deve avere almeno 10 caratteri se inizializzato.\r\n");
				formTest = false;	  
			}
		}*/
	
		if (form.flagAcquistoOnline != null &&  form.flagAcquistoOnline.checked && form.sitoWebAcquisto.value=="-1" ){
		    message += label("","- Attenzione, inserire il sito web d'acquisto.\r\n");
			formTest = false;
			var myselect = document.getElementById("sitoWebAcquisto");
			if(myselect.options[myselect.selectedIndex].value=="Altro"){
				if(form.noteAcquistoOnline.value.length<2){
					message += label("","- Attenzione, il campo 'Note' della sezione 'Acquisto online' deve essere valorizzato.\r\n");
					formTest = false;	  
				}
			}
		}
	
		if(true){
			var origine=false;
			if(form.origine_da != null && form.origine_da.value!='')
				origine=true;
			//if(form.flagFuoriNazione != null && form.flagFuoriNazione.checked)
			//	origine=true;
			if(form.flagFuoriRegione != null && form.flagFuoriRegione.checked)
				origine=true;
			if(form.flagFuoriRegione != null && form.flagFuoriRegione.checked)
				origine=true;
			if(form.flagAcquistoOnline != null && form.flagAcquistoOnline.checked)
				origine=true;
			if(form.flagSenzaOrigine != null && form.flagSenzaOrigine.checked){
				origine=true;
				nessuna_origine=true;
			}
			if(!origine){
			    message += label("","- Attenzione, inserire i dati sull'origine dell'animale.\r\n");
				formTest = false;	  			
			}	
		}
  }else{
	  document.getElementById("flagSenzaOrigine").value="on";
  }

	/*CONTROLLO VALIDITA' MC PER LP E SPECIE*/
	if (document.forms[0].ruolo.value == '24'){
		if ((form.idSpecie.value == '1' || ((form.idSpecie.value == '2' || form.idSpecie.value == '3') )) && !(form.microchip.value.substring(0, 6) == "380260")){
			message += label("","- Attenzione, MC non valido: selezionare un Mc del tipo 380260... \r\n");
	       	formTest = false;
	 	}	
	}

    if ( form.idSpecie.value == '1' && form.flagMorsicatore != null &&  form.flagMorsicatore.checked){
    	if(( (form.dataMorso.value ==null ) || (form.dataMorso.value==""))){
    		message += label("","- Flag Morsicatore settato: valorizzare almeno una data\r\n");
		    formTest = false;
	    }   	
    }
  
    lanciaControlloDate();

    //controllo sul proprietario se non è stato selezionato
    if (form.idProprietario.value == "-1") { 
    	message += label("check.ubicazione","- Proprietario è un informazione richiesta\r\n");
        formTest = false;
    }
	
    //controllo sul detentore se non è stato selezionato 
    if (form.idSpecie.value == '1' &&  form.idDetentore.value == "-1") { 
       	message += label("check.detentore","- Detentore è un informazione richiesta\r\n");
       	formTest = false;
    }
    
    if (form.idSpecie.value == '1' && !checkOccupazioneStabilimento()){<%
    	if(!ApplicationProperties.getProperty("blocco_canili_occupati").equals("true")){%>
    		alert("Attenzione, il canile è in sovraffollamento. A far data dal 20/02/2016 non sarà piu possibile registrare nuovi cani in ingresso.");<%
    	}else{%>
 		   	message += label("", "Canile selezionato in sovraffollamento, non e' possibile inserire ulteriori cani, selezionare un altro canile.\r\n ");
			formTest = false;<%
    	} %>		
    }

    if (formTest && checkSpecieEProprietario() == false){
    	message += label("", "Hai selezionato un proprietario di tipo sindaco, devi selezionare un detentore di tipo canile \r\n ");
		formTest = false;
   	}

    if (form.idProprietario.value != "-1" && checkCatturaDaProprietarioDetentoreTipo() == false){
		;
	}

    //----CAMPI OBBLIGATORI--------------
   	//controllo sulla taglia
	if (form.idSpecie.value == '1' &&  form.idTaglia.value == '-1') { 
		message += label("taglia.required", "- Taglia è un informazione richiesta\r\n");
	    formTest = false;
	}
    if ((form.idAslRiferimento==null) || (form.idAslRiferimento.value == '-1' && form.flagSindacoFuoriRegione.checked==false)) { 
		message += label("taglia.required", "- Asl è un informazione richiesta\r\n");
	    formTest = false;
	}
     //controllo sulla razza
	if (form.idRazza.value == '-1') { 
	    message += label("razza.required", "- Razza è un informazione richiesta\r\n");
	    formTest = false;
	}
	//controllo sul mantello
	if (form.idTipoMantello.value == '-1') { 
		message += label("mantello.required", "- Mantello è un informazione richiesta\r\n");
	    formTest = false;
	}
	//controllo sul mc
	if (form.microchip.value == "") {
		message += label("serial.number.required", "- Microchip è un informazione richiesta\r\n");
	    formTest = false;
	}else{
		//controllo sulla lunghezza del microchip    
	    if( !( (form.microchip.value.length == 15) && ( /^([0-9]+)$/.test( form.microchip.value )) ) ){
	    	message += label("", "- La lunghezza del microchip deve essere di 15 caratteri numerici\r\n");
	        formTest = false;
	    }
	}

	 //controllo sul mc
    if (form.tatuaggio != null && form.tatuaggio.value != "") {
		if (form.idSpecie.value=='1' && form.dataTatuaggio != null && form.dataTatuaggio.value == "") {
	    	message += label("", "- Data Inserimento secondo mc richiesta\r\n");
	     	formTest = false;
		}  
	}
	
	 if (form.idSpecie.value == '1' && form.dataTatuaggio != null &&  form.dataTatuaggio.value != "") {
	 	if (form.tatuaggio.value == "") {
			message += label("", "- Tatuaggio/2nd microchip richiesto\r\n");
		    formTest = false;
		}
	}
	
	//controllo sul check  regione
	if (form.idSpecie.value == '1' && form.flagProvenienzaFuoriRegione != null &&  form.flagProvenienzaFuoriRegione.checked){
		if ( form.idRegioneProvenienza.value== -1){
	   		message += label("", "- Selezionare la Regione di Provenienza\r\n");
	   		formTest = false;
	   	}
	}
			 
	if (form.numeroPassaporto && form.numeroPassaporto.value!=""){
		if (form.dataRilascioPassaporto.value==""){
			message += label("", "- Data Rilascio passaporto richiesta.\r\n");
	     	formTest = false;
	    }	
	}

	if (form.dataRilascioPassaporto && form.dataRilascioPassaporto.value!=""){
		if (form.numeroPassaporto.value==""){
	 		message += label("", "- Campo passaporto richiesto.\r\n");
	     	formTest = false;
	    }	
	}

	if (!$('input[name=sesso]:checked').length > 0) {
		message += label("", "- Sesso dell'animale richiesto.\r\n");
 		formTest = false;
	}

    if (document.addAnimale.flagCattura != null && document.addAnimale.flagCattura.checked){
    	if (document.addAnimale.dataCattura.value == null || document.addAnimale.dataCattura.value == ''){
    		message += label("", "- La data della cattura è richiesta.\r\n");
     		formTest = false;
    	}
    }
    
    //Controllo campi attivita itinerante
   	if ( document.addAnimale.flagAttivitaItinerante!=null && document.addAnimale.flagAttivitaItinerante.checked){
		if (document.getElementById("idComuneAttivitaItinerante").value == '-1' || document.getElementById("luogoAttivitaItinerante").value == ''){
			message += label("", "- Inserisci le informazioni sul luogo dell\'attivita itinerante svolta.\r\n");
    		formTest = false;
	   }
   }

    if (formTest == false) {
   		alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      	return false;
    }else{ /*
    	if(nessuna_origine){
    		var r = confirm("ATTENZIONE! Avendo spuntato il flag 'Informazioni mancanti sull'origine dell'animale', si rende necessaria un'autocertificazione di responsabilità da parte del "+
    						"proprietario dell'animale da allegare alla scheda del dettaglio animale.\n\nCliccando 'OK' verrà generato il documento da allegare.\n\nProseguire con l'inserimento?");
    		if (r == true) {
    			openRichiestaPDF('PrintAutocertificazioneMancataOrigineAnimale','-1','-1',''+document.getElementById("microchip").value,''+document.getElementById("idProprietario").value, '-1');
        		loadModalWindow(); //ATTENDERE PREGO
        		return true; 
        	}else{
 		   		return false;
    		}
    	}else{ */
    		loadModalWindow(); //ATTENDERE PREGO
    		return true;
    	//}
    }
}
  
function verificaPassaporto(campoIn){
	var ok = false;
	
	//Flusso 238: rimuovere ultimi due parametri
	EventoRilascioPassaporto.checkValorePassaporto(campoIn.value, <%=User.getSiteId()%> , <%=-1%>,<%=(String)request.getSession().getAttribute("id_canile")%>,<%=(String)request.getSession().getAttribute("id_stabilimento_gisa")%>,  {
		callback:function(data) {
		
		if (data.idEsito == 1)
			ok = true;
		else {
			 alert('Passaporto non utilizzabile. In fase di inserimento anagrafica è possibile l\'inserimento solo di passaporti non appartenenti alla regione campania. Per tutti gli altri casi utilizzare la registrazione di rilascio passaporto. ');
			 campoIn.value = '';
		}
		},
		timeout:8000,
		async:false

		});

	return ok;
}


var array_pratiche = new Array();

function visualizzaPratiche(){
	if (document.addAnimale.flagContributoRegionale.checked){
		if((document.addAnimale.idProprietario.value == "-1")||(document.addAnimale.idDetentore.value == "-1")){
			document.addAnimale.flagContributoRegionale.checked=false;
			alert('- Selezionare prima un Proprietario e un Detentore per questo cane');
		}else{
			if((document.addAnimale.dataSterilizzazione.value == "")||(document.addAnimale.dataSterilizzazione.value == null)){
				document.addAnimale.flagContributoRegionale.checked=false;
				alert('Selezionare prima la data di sterilizzazione');
			}else{
				//estrae tutte le pratiche per cui esiste aleno una posizione aperta
				PraticaList.getListData(document.addAnimale.dataSterilizzazione.value,document.addAnimale.idAslRiferimento.value,document.addAnimale.idDetentore.value, document.addAnimale.idSpecie.value, valorizzaLista);
			}	
		}
	}else{
		document.getElementById("pratica_contributo").style.visibility="hidden";
		document.addAnimale.pratica.value = -1;
	}
}

function valorizzaLista(listaPratiche){
	var select = document.forms['addAnimale'].idProgettoSterilizzazioneRichiesto; //Recupero la SELECT
	i = 0;
  	//indice utilizzato per i canili
  	k = 0;
	//Azzero il contenuto della seconda select
  	for (var j = select.length - 1; j >= 0; j--)
  		select.remove(j);
	var NewOpttmp = document.createElement('option');
 	NewOpttmp.value=-1;
 	NewOpttmp.text=" -- Nessuna Pratica --";
 	try{
 		select.add(NewOpttmp, null); //Metodo Standard, non funziona con IE
 	}catch(e){
	 select.add(NewOpttmp); 
 	}	
	while(i < listaPratiche.length){
		array_pratiche[listaPratiche[i].id]=listaPratiche[i];
		var NewOpt = document.createElement('option');
		NewOpt.value = listaPratiche[i].id;
		//alert(NewOpt.value);
	 	NewOpt.text =  "Decreto numero "+listaPratiche[i].numero_decreto_pratica + " del "+ listaPratiche[i].data_decreto_stringa +" - N° cani padronali restanti "+ listaPratiche[i].cani_restanti_padronali +" - N° cani catturati restanti "+listaPratiche[i].cani_restanti_catturati + " - "+ listaPratiche[i].elenco_comuni ;
		if(listaPratiche[i].elencoCanili.length != 0 ){
			for (var k=0; k<listaPratiche[i].elencoCanili.length; k++) {
				NewOpt.text =  "Decreto numero "+listaPratiche[i].numero_decreto_pratica + " del "+ listaPratiche[i].data_decreto_stringa +" - N° cani padronali restanti "+ listaPratiche[i].cani_restanti_padronali +" - N° cani catturati restanti "+listaPratiche[i].cani_restanti_catturati + " - "+ listaPratiche[i].elencoCanili;	
			}
		}
		//controllo dell'id selezionato nel caso di salva e clona
		if(document.addAnimale.oldPratica.value == NewOpt.value){
			NewOpt.selected = true;
		}
		//Aggiungo l'elemento option
		try{
			select.add(NewOpt, null); //Metodo Standard, non funziona con IE
		}catch(e){
			select.add(NewOpt); // Funziona solo con IE
		}
		i++;						   
	}
   	if (select.length>0){
       	document.getElementById("pratica_contributo").style.visibility="visible";
	}
}

function svuota(){
	document.addAnimale.flagContributoRegionale.checked=false;
	document.getElementById("pratica_contributo").style.visibility="hidden";
}


function checkProprietarioDetentore(){
	if (document.addAnimale.flagCattura.checked){
		LineaProduttiva.verificaLineaProduttiva(document.addAnimale.idProprietario.value, document.addAnimale.idDetentore.value, 2, 4, check );
	}
}

function checkCatturaDaProprietarioDetentoreTipo(){
	var ok = false;
	//alert($('#flagCattura').is(':checked'));
	LineaProduttiva.verificaLineaProduttivaPerCatture(document.addAnimale.idProprietario.value, document.addAnimale.idDetentore.value, $('#flagCattura').is(':checked'), {
		callback:function(data) {
			//alert(data.idEsito);
		if (data.idEsito == -1){
			ok = false;
			message = data.descrizione;
			formTest  = false;
		}else{
			ok = true;
		}
		},
		timeout:8000,
		async:false
		});
	return ok;
}


function checkSpecieEProprietario(){
	var ok = false;
	//alert($('#flagCattura').is(':checked'));
	LineaProduttiva.verificaProprietarioSpecie( document.addAnimale.idSpecie.value, document.addAnimale.idProprietario.value,document.addAnimale.idDetentore.value,  {
		callback:function(data) {
		ok= data;
		},
		timeout:8000,
		async:false
		});
	return ok;
}

function check(rit){
	if (rit == false){
		$('#flagCattura').attr('checked', false);
		alert("Selezionare un proprietario sindaco e un detentore canile per poter aggiungere una registrazione di cattura");
	}
}

function apriPopup(){
	 $("#dialogMunicipalita").dialog('open');
}	

$(document).ready(function(){	
	 $('#idTaglia').change(function(){
		 checkOccupazioneStabilimento(true);
	 });
	if( $('#idComuneCattura').val() > 0){
		visualizzaMunicipalita();
	}
    $("#dialogMunicipalita").dialog({
    	autoOpen: false,
        maxWidth:600,
        maxHeight: 500,
        width: 600,
        height: 500,
        modal: true,
        buttons: {
            "Chiudi": function() {
               $(this).dialog("close");
            }
        },
        close: function() {
        } });
}); 
	 
	 
function checkOccupazioneStabilimento(flagAvviso){
	var ok = false;
	Stabilimento.checkOccupazione(document.addAnimale.idDetentore.value, document.addAnimale.idTaglia.value, document.addAnimale.dataNascita.value, {
		callback:function(data) {
			ok= data;
			//alert(ok);	
			if (!ok && flagAvviso){		
				<% if(!ApplicationProperties.getProperty("blocco_canili_occupati").equals("true")){ %>
					ok=true;
				<%	}  %>
				<% if(!ApplicationProperties.getProperty("blocco_canili_occupati").equals("true")){ %>
					alert("Attenzione, il canile è in sovraffollamento. A far data dal 20/02/2016 non sarà piu possibile registrare nuovi cani in ingresso.");
				<%  }else{ %>
					alert("Canile selezionato in sovraffollamento, non e' possibile inserire ulteriori cani, selezionare un altro canile.");
				<%  } %>
			}
		},
		timeout:8000,
		async:false
		});
	return ok;
}

/* GESTIONE MUNICIPALITA' COMUNE CATTURA */
function visualizzaMunicipalita(){
	DwrUtil.getListaMunicipalita(document.forms[0].idComuneCattura.value, valorizzaListaCallback21);
 }
 
function valorizzaListaCallback21(listaMunicipalita){
	var select = document.createElement("select");
 	select.id = "idMunicipalita"; //Recupero la SELECT
 	select.name="idMunicipalita";
	i = 0;
   	//indice utilizzato per i canili
   	k = 0;
   	//Azzero il contenuto della seconda select
	for (var j = select.length - 1; j >= 0; j--)
   		select.remove(j);
   	while(i < listaMunicipalita.length){
		var NewOpt = document.createElement('option');
 		NewOpt.value = listaMunicipalita[i].id;
 		NewOpt.text =  listaMunicipalita[i].nomeMunicipalita.replace('\uFFFD', 'à');
 		//Aggiungo l'elemento option
 		try{
			select.add(NewOpt, null); //Metodo Standard, non funziona con IE
 		}catch(e){
 			select.add(NewOpt); // Funziona solo con IE
 		}
 		i++;						   
 	}
 	if (select.length>0){
 		var tableRef = document.getElementById('cattura').getElementsByTagName('tbody')[0];
	 	// Insert a row in the table at the last row
 		var newRow   = tableRef.insertRow(tableRef.rows.length);
 		newRow.setAttribute('id','municip');
 		// Insert a cell in the row at index 0
 		var newCell  = newRow.insertCell(0);
	 	// Append a text node to the cell
	 	var newText  = document.createTextNode('Municipalita');
	 	newCell.className = "formLabel";
	 	newCell.appendChild(newText);
	 	var newCell1  = newRow.insertCell(1);
	 	newCell1.appendChild(select);
	 	var link = document.createElement("a");
	 	link.setAttribute("href", "javascript:apriPopup();");
	 	// For IE only, you can simply set the innerText of the node.
	 	// The below code, however, should work on all browsers.
	 	var linkText = document.createTextNode("Informazioni");
	 	link.appendChild(linkText);
	 	newCell1.appendChild(link);
  	}else{
  		var element =  document.getElementById('municip');
  		if (typeof(element) != 'undefined' && element != null){
  			element.style.display = 'none';
  		}
  	}
}
</script>

<%@ include file="../initPage.jsp"%>

<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>

<script language="javascript">

function popUp(url) {
	title  = '_types';
	width  =  '500';
	height =  '600';
	resize =  'yes';
	bars   =  'no';
	url = url + '&idAsl=' + document.addAnimale.idAslRiferimento.value;
	var posx = (screen.width - width)/2;
	var posy = (screen.height - height)/2;
	var windowParams = 'scrollbars=yes ,WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	var newwin=window.open(url, title, windowParams);
	newwin.focus();
	if (newwin != null) {
		if (newwin.opener == null)
	    	newwin.opener = self;
	}
}

function abilitaEsitiEhi(){
	if(document.getElementById('flagControlloEhrlichiosi')!=null){
		if(document.getElementById('flagControlloEhrlichiosi').checked){
			document.getElementById('esitoEhradd1').style.visibility="visible";
			//document.getElementById('esitoEhradd2').style.visibility="visible";
		}else{
			document.getElementById('esitoEhradd1').style.visibility="hidden";
			document.getElementById('dataControlloEhrlichiosi').value="";
			document.addAnimale.esitoControlloEhrlichiosi.value="-1";
			//document.getElementById('esitoEhradd2').style.visibility="hidden";
		}
	}
}

function abilitaEsitiRik(){
	if(document.getElementById('flagControlloRickettsiosi')!=null){
		if(document.getElementById('flagControlloRickettsiosi').checked ){
			document.getElementById('esitoRickadd1').style.visibility="visible";
		}else{
			document.getElementById('esitoRickadd1').style.visibility="hidden";
			document.getElementById('dataControlloRickettsiosi').value="";
			//document.addAnimale.esitoControlloRickettsiosi.value="-1";
		}
	}
}

function checkContributo(){
	if(document.getElementById('flagContributoRegionale')!=null){
		if(document.getElementById('flagContributoRegionale').checked ){
			visualizzaPratiche();
			document.getElementById("pratica_contributo").style.visibility="visible";
		}
	}
}

function doCheck(form){
	if (form.dosubmit.value == "false") {     
    	return true;
    } else {
    	return(checkForm(form));
   	}
}


function removNoDisplay(){
	var ele=null;
	
	document.getElementById("mancata_tracciabilita").style.removeProperty("display");
	$("#flagSenzaOrigine").removeAttr('checked');
	
	if(document.getElementById("provenienza_da_altra_nazione")!=null){
		// OSCURO NAZIONE ESTERA
		document.getElementById("provenienza_da_altra_nazione").style.display="none";
		document.getElementById("utProvenienzaEstera").style.display="none";
		$("#idNazioneProvenienza").val("-1");	 
		document.getElementById("noteNazioneProvenienza").value="";
		// OSCURO ANAGRAFE FUORI REGIONE
		document.getElementById("provenienza_da_altra_regione").style.removeProperty("display");
		$("#flagFuoriRegione").removeAttr('checked');			
		document.getElementById("utProvenienza").style.display="none";
		$("#idRegioneProvenienza").val("-1");	 
		document.getElementById("noteAnagrafeFr").value="";
	}
	// OSCURO EX PROPRIETARIO O RITROVAMENTO
	document.getElementById("provenienza_soggetto_ritrovamento").style.removeProperty("display");
	document.getElementById("radio_tipo_origine").style.display="none";
	ele = document.getElementsByName("origine_da");
	for(var i=0;i<ele.length;i++)
		ele[i].checked = false;
	ele = document.getElementsByName("tipo_origine");
	for(var i=0;i<ele.length;i++)
		ele[i].checked = false;
	
	document.getElementById("proprietarioProvenienza").style.display="none";
	document.getElementById("idProprietarioProvenienza").value="-1"; document.getElementById("nome_proprietario_provenienza").innerHTML="";
	 
	document.getElementById("dati_ritrovamento").style.removeProperty("display");
	document.getElementById("provincia_ritrovamentoinput").value="";
	document.getElementById("comune_ritrovamentoinput").value="";
	$('select[name="provincia_ritrovamento"]').val("");
	$('select[name="comune_ritrovamento"]').val("");
	document.getElementById("indirizzo_ritrovamento").value="";
	document.getElementById("data_ritrovamento").value="";
	// OSCURO ACQUISTO SU SITO WEB
	document.getElementById("mostraAcquistoOnline").style.removeProperty("display");
	$("#flagAcquistoOnline").removeAttr('checked');			
	$("#sitoWebAcquisto").val("-1");	 
	document.getElementById("sitoWebAcquisto_hidden").value="";	
	 document.getElementById("noteAcquistoOnline").style.display="none";
	 document.getElementById("noteAcquistoOnline").value="";
}


function CheckGenericoOrigineAnimale(evt){
	var ele=null;
	if(evt.id == "flagSenzaOrigine"){
		if(evt.checked){
			alert("ATTENZIONE! Il caso di mancata tracciabilità per 'Informazioni mancanti sull'origine animale' è da considerarsi una informazione non conforme.");
			if(document.getElementById("provenienza_da_altra_nazione")!=null){
				// OSCURO NAZIONE ESTERA
				document.getElementById("provenienza_da_altra_nazione").style.display="none";
				document.getElementById("utProvenienzaEstera").style.display="none";
				$("#idNazioneProvenienza").val("-1");	 
				document.getElementById("noteNazioneProvenienza").value="";
				// OSCURO ANAGRAFE FUORI REGIONE
				document.getElementById("provenienza_da_altra_regione").style.display="none";
				$("#flagFuoriRegione").removeAttr('checked');			
				document.getElementById("utProvenienza").style.display="none";
				$("#idRegioneProvenienza").val("-1");	 
				document.getElementById("noteAnagrafeFr").value="";
			}
			// OSCURO EX PROPRIETARIO O RITROVAMENTO
			document.getElementById("provenienza_soggetto_ritrovamento").style.display="none";
			document.getElementById("radio_tipo_origine").style.display="none";
			ele = document.getElementsByName("origine_da");
			for(var i=0;i<ele.length;i++)
				ele[i].checked = false;
			ele = document.getElementsByName("tipo_origine");
			for(var i=0;i<ele.length;i++)
				ele[i].checked = false;
			
			document.getElementById("proprietarioProvenienza").style.display="none";
			document.getElementById("idProprietarioProvenienza").value="-1"; document.getElementById("nome_proprietario_provenienza").innerHTML="";
			
			document.getElementById("dati_ritrovamento").style.display="none";
			if(document.getElementById("provincia_ritrovamentoinput")!=null) document.getElementById("provincia_ritrovamentoinput").value="";
			if(document.getElementById("comune_ritrovamentoinput")!=null)    document.getElementById("comune_ritrovamentoinput").value="";
			$('select[name="provincia_ritrovamento"]').val("");
			$('select[name="comune_ritrovamento"]').val("");
			document.getElementById("indirizzo_ritrovamento").value="";
			document.getElementById("data_ritrovamento").value="";
			
			// OSCURO ACQUISTO SU SITO WEB
			document.getElementById("mostraAcquistoOnline").style.display="none";
			$("#flagAcquistoOnline").removeAttr('checked');			
			$("#sitoWebAcquisto").val("-1");	 
			document.getElementById("sitoWebAcquisto_hidden").value="";	
			 document.getElementById("noteAcquistoOnline").style.display="none";
			 document.getElementById("noteAcquistoOnline").value="";

		}else{
			removNoDisplay();
		}
		
	}else if(evt.id == "flagFuoriNazione"){
		if(evt.checked){
			document.getElementById("utProvenienzaEstera").style.removeProperty("display");
			// OSCURO ANAGRAFE FUORI REGIONE
			document.getElementById("provenienza_da_altra_regione").style.display="none";
			$("#flagFuoriRegione").removeAttr('checked');			
			document.getElementById("utProvenienza").style.display="none";
			$("#idRegioneProvenienza").val("-1");	 
			document.getElementById("noteAnagrafeFr").value="";
			// OSCURO EX PROPRIETARIO O RITROVAMENTO
			document.getElementById("provenienza_soggetto_ritrovamento").style.display="none";
			document.getElementById("radio_tipo_origine").style.display="none";
			ele = document.getElementsByName("origine_da");
			for(var i=0;i<ele.length;i++)
				ele[i].checked = false;
			ele = document.getElementsByName("tipo_origine");
			for(var i=0;i<ele.length;i++)
				ele[i].checked = false;
			
			document.getElementById("proprietarioProvenienza").style.display="none";
			document.getElementById("idProprietarioProvenienza").value="-1"; document.getElementById("nome_proprietario_provenienza").innerHTML="";
			 
			document.getElementById("dati_ritrovamento").style.display="none";
			document.getElementById("provincia_ritrovamentoinput").value="";
			document.getElementById("comune_ritrovamentoinput").value="";
			$('select[name="provincia_ritrovamento"]').val("");
			$('select[name="comune_ritrovamento"]').val("");
						document.getElementById("indirizzo_ritrovamento").value="";
			document.getElementById("data_ritrovamento").value="";
		}else{
			removNoDisplay();
		}
	}else if(evt.id == "flagFuoriRegione"){
		if(evt.checked){
			document.getElementById("origine_table").style.display="none";
			document.getElementById("utProvenienza").style.removeProperty("display");
			// OSCURO NAZIONE ESTERA
			document.getElementById("provenienza_da_altra_nazione").style.display="none";
			document.getElementById("utProvenienzaEstera").style.display="none";
			$("#idNazioneProvenienza").val("-1");	 
			document.getElementById("noteNazioneProvenienza").value="";
			// OSCURO EX PROPRIETARIO O RITROVAMENTO
			document.getElementById("provenienza_soggetto_ritrovamento").style.display="none";
			document.getElementById("radio_tipo_origine").style.display="none";
			ele = document.getElementsByName("origine_da");
			for(var i=0;i<ele.length;i++)
				ele[i].checked = false;
			ele = document.getElementsByName("tipo_origine");
			for(var i=0;i<ele.length;i++)
				ele[i].checked = false;
			
			document.getElementById("proprietarioProvenienza").style.display="none";
			document.getElementById("idProprietarioProvenienza").value="-1"; document.getElementById("nome_proprietario_provenienza").innerHTML="";
			 
			document.getElementById("dati_ritrovamento").style.display="none";
			document.getElementById("provincia_ritrovamentoinput").value="";
			document.getElementById("comune_ritrovamentoinput").value="";
			$('select[name="provincia_ritrovamento"]').val("");
			$('select[name="comune_ritrovamento"]').val("");
						document.getElementById("indirizzo_ritrovamento").value="";
			document.getElementById("data_ritrovamento").value="";
			
			document.getElementById("mostraAcquistoOnline").style.display="none";
			$("#flagAcquistoOnline").removeAttr('checked');
			document.getElementById("utProvenienzaOnline").style.display="none";
			document.getElementById("sitoWebAcquisto").value="-1";
			document.getElementById("sitoWebAcquisto_hidden").value="";
			 document.getElementById("noteAcquistoOnline").style.display="none";
			 document.getElementById("noteAcquistoOnline").value="";
			
			document.getElementById("mancata_tracciabilita").style.display="none";
			$("#flagSenzaOrigine").removeAttr('checked');
		}else{
			mostraOrigine();
			removNoDisplay();
		}
	}else if(evt.id == "origine_da"){
		if(evt.checked){
			if(evt.value=="in_regione")
				 document.getElementById("ricerca_fr").style.removeProperty("display");
			else
				 document.getElementById("ricerca_fr").style.display="none";

			
			document.getElementById("provenienza_soggetto_ritrovamento").style.removeProperty("display");
			document.getElementById("radio_tipo_origine").style.removeProperty("display");
			
			document.getElementById("mostraAcquistoOnline").style.removeProperty("display");
			$("#flagAcquistoOnline").removeAttr('checked');
			document.getElementById("utProvenienzaOnline").style.display="none";
			document.getElementById("sitoWebAcquisto").value="-1";
			document.getElementById("sitoWebAcquisto_hidden").value="";
			 document.getElementById("noteAcquistoOnline").style.display="none";
			 document.getElementById("noteAcquistoOnline").value="";
			
			
			var ele = document.getElementsByName("tipo_origine");
			for(var i=0;i<ele.length;i++)
				ele[i].checked = false;
			    
			document.getElementById("proprietarioProvenienza").style.display="none";
			document.getElementById("idProprietarioProvenienza").value="-1"; document.getElementById("nome_proprietario_provenienza").innerHTML="";
			 
			document.getElementById("dati_ritrovamento").style.display="none";
			document.getElementById("provincia_ritrovamentoinput").value="";
			document.getElementById("comune_ritrovamentoinput").value="";
			$('select[name="provincia_ritrovamento"]').val("");
			$('select[name="comune_ritrovamento"]').val("");
			document.getElementById("indirizzo_ritrovamento").value="";
			document.getElementById("data_ritrovamento").value="";
			if(document.getElementById("provenienza_da_altra_nazione")!=null){
				// OSCURO NAZIONE ESTERA
				document.getElementById("provenienza_da_altra_nazione").style.display="none";
				document.getElementById("utProvenienzaEstera").style.display="none";
				$("#idNazioneProvenienza").val("-1");	 
				document.getElementById("noteNazioneProvenienza").value="";
				//OSCURO ANAGRAFE FUORI REGIONE
				document.getElementById("provenienza_da_altra_regione").style.display="none";
				$("#flagFuoriRegione").removeAttr('checked');			
				document.getElementById("utProvenienza").style.display="none";
				$("#idRegioneProvenienza").val("-1");	 
				document.getElementById("noteAnagrafeFr").value="";
			}
			// OSCURO EX PROPRIETARIO O RITROVAMENTO
				
			
			document.getElementById("proprietarioProvenienza").style.display="none";
			document.getElementById("idProprietarioProvenienza").value="-1"; document.getElementById("nome_proprietario_provenienza").innerHTML="";
			 
			document.getElementById("dati_ritrovamento").style.display="none";
			document.getElementById("provincia_ritrovamentoinput").value="";
			document.getElementById("comune_ritrovamentoinput").value="";
			$('select[name="provincia_ritrovamento"]').val("");
			$('select[name="comune_ritrovamento"]').val("");
			document.getElementById("indirizzo_ritrovamento").value="";
			document.getElementById("data_ritrovamento").value="";
			if(evt.value=="nazione_estera"){
				document.getElementById("radio_tipo_origine").style.display="none";
				document.getElementById("provenienza_da_altra_nazione").style.removeProperty("display");
				document.getElementById("utProvenienzaEstera").style.removeProperty("display");
				$("#idNazioneProvenienza").val("-1");	 
				document.getElementById("noteNazioneProvenienza").value="";


			}
			
		}
	}else if(evt.id == "tipo_origine"){
		if(evt.checked){
			if(evt.value=="ritrovamento"){
				document.getElementById("proprietarioProvenienza").style.display="none";
				document.getElementById("idProprietarioProvenienza").value="-1"; document.getElementById("nome_proprietario_provenienza").innerHTML="";
				
				document.getElementById("dati_ritrovamento").style.removeProperty("display");
				document.getElementById("provincia_ritrovamentoinput").value="";
				document.getElementById("comune_ritrovamentoinput").value="";
				$('select[name="provincia_ritrovamento"]').val("");
				$('select[name="comune_ritrovamento"]').val("");
				document.getElementById("indirizzo_ritrovamento").value="";
				document.getElementById("data_ritrovamento").value="";
				
				document.getElementById("provenienza_da_altra_nazione").style.display="none";
				document.getElementById("utProvenienzaEstera").style.display="none";
				$("#idNazioneProvenienza").val("-1");	 
				document.getElementById("noteNazioneProvenienza").value="";
				 // OSCURO ACQUISTO SU SITO WEB
				document.getElementById("mostraAcquistoOnline").style.display="none";
				$("#flagAcquistoOnline").removeAttr('checked');			
				$("#sitoWebAcquisto").val("-1");	 
				document.getElementById("sitoWebAcquisto_hidden").value="";
				 document.getElementById("noteAcquistoOnline").style.display="none";
				 document.getElementById("noteAcquistoOnline").value="";
				
			}else if(evt.value=="soggetto_fisico"){
				document.getElementById("proprietarioProvenienza").style.removeProperty("display");
				document.getElementById("idProprietarioProvenienza").value="-1"; document.getElementById("nome_proprietario_provenienza").innerHTML="";
				
				document.getElementById("dati_ritrovamento").style.display="none";
				document.getElementById("provincia_ritrovamentoinput").value="";
				document.getElementById("comune_ritrovamentoinput").value="";
				$('select[name="provincia_ritrovamento"]').val("");
				$('select[name="comune_ritrovamento"]').val("");
				document.getElementById("indirizzo_ritrovamento").value="";
				document.getElementById("data_ritrovamento").value="";
				
				document.getElementById("mostraAcquistoOnline").style.removeProperty("display");
				
				document.getElementById("provenienza_da_altra_nazione").style.display="none";
				document.getElementById("utProvenienzaEstera").style.display="none";
				$("#idNazioneProvenienza").val("-1");	 
				document.getElementById("noteNazioneProvenienza").value="";
				
			}else{
				document.getElementById("dati_ritrovamento").style.display="none";
				document.getElementById("provincia_ritrovamentoinput").value="";
				document.getElementById("comune_ritrovamentoinput").value="";
				$('select[name="provincia_ritrovamento"]').val("");
				$('select[name="comune_ritrovamento"]').val("");
				document.getElementById("indirizzo_ritrovamento").value="";
				document.getElementById("data_ritrovamento").value="";
				
				document.getElementById("proprietarioProvenienza").style.display="none";
				document.getElementById("idProprietarioProvenienza").value="-1"; document.getElementById("nome_proprietario_provenienza").innerHTML="";
				
				document.getElementById("mostraAcquistoOnline").style.removeProperty("display");

				document.getElementById("provenienza_da_altra_nazione").style.removeProperty("display");
				document.getElementById("utProvenienzaEstera").style.removeProperty("display");
			}						
		}
	}else if(evt.id == "flagAcquistoOnline"){
		if(evt.checked){
				abilitaNoteOnline();
				document.getElementById("mostraAcquistoOnline").style.removeProperty("display");
				document.getElementById("utProvenienzaOnline").style.removeProperty("display");
			}else{
				$("#flagAcquistoOnline").removeAttr('checked');			
				document.getElementById("utProvenienzaOnline").style.display="none";
				$("#sitoWebAcquisto").val("-1");	 
				document.getElementById("sitoWebAcquisto_hidden").value="";	
				 document.getElementById("noteAcquistoOnline").style.display="none";
				 document.getElementById("noteAcquistoOnline").value="";
			}						
		}
}

function  inserisciInformazioniAttivitaItinerante(){
	if (document.addAnimale.flagAttivitaItinerante != null && document.addAnimale.flagAttivitaItinerante.checked){
		document.getElementById("attivitaItinerante").style.visibility="visible";
	} else {
		document.getElementById("luogoAttivitaItinerante").value = '';
		document.getElementById("idComuneAttivitaItinerante").value = '-1';
		document.getElementById("attivitaItinerante").style.visibility="hidden";
  	}
}

function abilitaSelezionePropDet(idAsl) {
	if(document.getElementById('flagSindacoFuoriRegione') != null && document.getElementById('flagSindacoFuoriRegione').checked){
		document.getElementById("origine_da").style.display = "none";
	}else{
		document.getElementById("origine_da").style.removeProperty("display");	
	}
}

function checkMorsicatore() {
	if (document.addAnimale.flagMorsicatore.checked){
		document.getElementById("mors").style.visibility="visible";
  	} else {
  		document.getElementById("mors").style.visibility="hidden";
  	}
}

function checkSpecieAnimale(obj){
	document.forms[0].doContinue.value = 'false';
	document.forms[0].submit();
}

function openRichiesta(idAnimale, idSpecie){
	var res;
	var result;
	window.open('AnimaleAction.do?command=PrintRichiestaIscrizione&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
	'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function openCertificato(idAnimale, idSpecie){
	var res;
	var result;
	window.open('AnimaleAction.do?command=PrintCertificatoIscrizione&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
	'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function openObblighi(){
	var res;
	var result;
	window.open('anagrafe_animali/documenti/obblighi_legge_proprietario.jsp','popupSelect',
	'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function openCampioni(idAnimale, idSpecie){
	var res;
	var result;
	window.open('AnimaleAction.do?command=PrintRichiestaCampioni&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
	'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function openCampioniRabbia(idAnimale, idSpecie){
	var res;
	var result;
	window.open('AnimaleAction.do?command=PrintRichiestaCampioniRabbia&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
	'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
		<dhv:evaluate if="<%=(origine==null || origine.equals(""))%>">
			<td width="100%">
				<a href="AnimaleAction.do"><dhv:label name="anagrafica.animale">Animale</dhv:label></a> > 
				<dhv:label name="anagrafica.animale.aggiungi">Aggiungi</dhv:label>
			</td>
		</dhv:evaluate>
		<dhv:evaluate if="<%=(origine.equals("operatore"))%>">
		<!--  SE PROVENGO DAl DETTAGLIO OPERATORE LASCIA IL RIFERIMENTO -->
			<td width="100%"><a href="OperatoreAction.do?command=Details&opId=<%=opId %>">
				<dhv:label name="anagrafica.animale">Dettaglio Operatore</dhv:label></a> > 
				<dhv:label name="anagrafica.animale.aggiungi">Aggiungi animale</dhv:label>
			</td>
		</dhv:evaluate>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>

<body onLoad="abilitaEsitiEhi(); abilitaEsitiRik();checkContributo();">
<form name="addAnimale"	action="AnimaleAction.do?command=Insert&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>"	method="post">
	<input type="hidden" name="ruolo" value="<%=User.getRoleId()%>"> <%
			String lineaProduttivaFiltro = "";
	if (animale.getIdSpecie() == Furetto.idSpecie)
		lineaProduttivaFiltro = "idLineaProduttiva1=1;4";
	else
		lineaProduttivaFiltro = "idLineaProduttiva=1";
	
 	if (User.getRoleId() == 24) { %> 
 		<input type="hidden" name="idTipologiaSoggettoSterilizzante" value="2" id="idTipologiaSoggettoSterilizzante" /> 
 		<!-- LLPP --> 
 		<input type="hidden" name="idSoggettoSterilizzante"	id="idSoggettoSterilizzante" value="<%=User.getUserId()%>" /> 
 		<!-- ID VETERINARIO -->
<%	} else {
%> 		<input type="hidden" name="idTipologiaSoggettoSterilizzante" value="1" id="idTipologiaSoggettoSterilizzante" /> 
		<!-- ASL --> 
		<input type="hidden" name="idSoggettoSterilizzante"	id="idSoggettoSterilizzante" value="<%=User.getSiteId()%>" /> 
		<!-- ID ASL -->
<%	} %>
	<input type="hidden" name="customerSatisfaction" id ="customerSatisfaction" value = "si">
	
	<table class="">
		
		<input type="button" value="<dhv:label name="global.button.save">Save</dhv:label>"
		onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='';if(doCheck(this.form)){setTimestampStartRichiesta();this.form.submit()};" />
		
		<dhv:evaluate if="<%=session.getAttribute("caller")==null || !ApplicationProperties.getProperty("VAM_ID").equals(session.getAttribute("caller")) %>" >
			<input type="button" value="Salva e Clona" onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='1';if(doCheck(this.form)){setTimestampStartRichiesta();this.form.submit()};" />
		</dhv:evaluate>
		
		<dhv:evaluate if="<%=(origine==null || origine.equals(""))%>">
			<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"	onClick="window.location.href='AnimaleAction.do';this.form.dosubmit.value='false';" />
		</dhv:evaluate>
				
		<dhv:evaluate if="<%=(origine.equals("operatore"))%>">
			<!--  SE PROVENGO DAl DETTAGLIO OPERATORE LASCIA IL RIFERIMENTO -->
			<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='OperatoreAction.do?command=Details&opId=<%=opId %>';this.form.dosubmit.value='false';" />
		</dhv:evaluate>
			
		<%if (request.getAttribute("SalvaeClona")!=null){ %>
			<font color = "green">Animale con mc <%=animale.getMicrochip()  %> Salvato Correttamente 
			<dhv:permission name="anagrafe_canina-documenti-view">
				<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16" />
				<a href="#" onclick="openRichiesta('<%=animale.getIdAnimale()%>','<%=animale.getIdSpecie()%>');" id="" target="_self">Richiesta prima iscrizione</a>
				<dhv:evaluate if="<%=(animale.getIdAslRiferimento() != Constants.ID_ASL_FUORI_REGIONE)%>">
					<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16" />
					<a href="#" onclick="openCertificato('<%=animale.getIdAnimale()%>','<%=animale.getIdSpecie()%>');" id="" target="_self">Certificato di Iscrizione</a>
					<img src="images/icons/stock_print-16.gif" border="0"	align="absmiddle" height="16" width="16" />
					<a href="#"	onclick="openCampioni('<%=animale.getIdAnimale()%>','<%=animale.getIdSpecie()%>');"	id="" target="_self">Scheda invio campioni</a>
				</dhv:evaluate>
			</dhv:permission>
			</font>
		<%} %>

		<table  cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
			<input type="hidden" value="<%=session.getAttribute("caller") %>" name="origineInserimento" id="origineInserimento" />
			<tr >
				<th colspan="2"><strong><dhv:label name="accounts.accountasset_include.SpecificInformation">Specific Information</dhv:label></strong></th>
			</tr>
			<dhv:evaluate if="<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))
				&& User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA")) 
				&& User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1")) 
				&& User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2")) 
				//&& User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_ANAGRAFE_CANINA"))
			)%>">
				<tr id="aslRif">
					<td class="formLabel" nowrap><dhv:label name="">Asl di Riferimento</dhv:label></td>
					<td>
						<%	if (User.getSiteId() == -1) {
								out.print(AslList.getHtmlSelect("idAslRiferimento", animale.getIdAslRiferimento()));
						} else {
 								out.print(AslList.getSelectedValue(User.getSiteId()));
 						%> 
 								<input type="hidden" size="30" id="idAslRiferimento" name="idAslRiferimento" value="<%=User.getSiteId()%>"> 
 					<% }	%>
					</td>
				</tr>
			</dhv:evaluate>

			<tr>
				<td class="formLabel" nowrap><dhv:label name="">Specie animale</dhv:label></td>
				<td>
					<dhv:evaluate if='<%=(animale.getIdProprietario() > 0)%>'>
						<%=specieList.getSelectedValue(animale.getIdSpecie())%>
						<input type="hidden" value="<%=animale.getIdSpecie()%>" name="idSpecie" id="idSpecie" />
					</dhv:evaluate> 
					<dhv:evaluate if='<%=!(animale.getIdProprietario() > 0)%>'>
						<%	specieList.setJsEvent("onChange=\"javascript:checkSpecieAnimale(this);\"");	%>
						<%=specieList.getHtmlSelect("idSpecie", animale.getIdSpecie())%>
					</dhv:evaluate>
				</td>
			</tr>
			
			<dhv:evaluate if="<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) && User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA")))%>">	
				<tr id="attivitaItinerantetr">
					<td class="formLabel" nowrap><dhv:label name="">Anagrafe effettuata in attività itinerante</dhv:label></td>
					<td>
						<input type="checkbox" id="flagAttivitaItinerante" name="flagAttivitaItinerante" onclick="inserisciInformazioniAttivitaItinerante()" name="flagAttivitaItinerante"
						<%if (animale != null && animale.isFlagAttivitaItinerante()) {%> checked="checked" <%}%> />
						<div id="attivitaItinerante" <%=(animale != null && animale.isFlagAttivitaItinerante()) ? ("") : ("style=\"visibility:hidden\"")%>>
							Comune: <%=comuniList.getHtmlSelect("idComuneAttivitaItinerante", animale.getIdComuneAttivitaItinerante())%><font color="red">*</font> 
							Luogo: <input type="text" name="luogoAttivitaItinerante"	id="luogoAttivitaItinerante"	value="<%=animale.getLuogoAttivitaItinerante()%>"/>
						</div>		
					</td>
				</tr>
			</dhv:evaluate>
			
			
			<% if(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))
				&& User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA"))){%>
			<!--  SEZIONE PROVENIENZA ALTRA ANAGRAFICA REGIONALE -->
			<tr  id="provenienza_da_altra_regione" class="containerBody">
				<td class="formLabel"><dhv:label name="">Provenienza da anagrafe altra regione</dhv:label></td>
				<td>
					<input type="checkbox" id="flagFuoriRegione"	onclick="CheckGenericoOrigineAnimale(this)" name="flagFuoriRegione"
					<%if (animale != null && Cane.isFlagFuoriRegione()) {%>	checked="checked" <%}%> />
					<div id="utProvenienza"	
						<%=(animale != null && Cane.getIdRegioneProvenienza() != -1) ? ("")	: ("style=\"display:none\"")%>>
						<%=regioniList.getHtmlSelect("idRegioneProvenienza",Cane.getIdRegioneProvenienza())%><font color="red">&nbsp;*</font> 
						<input type="hidden" id="verDate" name="verDate" value="ok" />
						<br><textarea rows="4" cols="50" id="noteAnagrafeFr" name="noteAnagrafeFr"><%=((String)request.getAttribute("noteAnagrafeFr")!=null ? (String)request.getAttribute("noteAnagrafeFr") : "")%></textarea>
						
						</div>
				</td>
			</tr>
			<% }else{ %>
  				<input type="hidden" id="provenienza_da_altra_regione"/>
  				<input type="hidden" id="flagFuoriRegione" /> 
  				<input type="hidden" id="utProvenienza" /> 
  				<input type="hidden" id="idRegioneProvenienza" value="-1" />
  				<input type="hidden" id="noteAnagrafeFr" value=""/>
			<% } %>
			
  
  		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Data Registrazione</dhv:label>
			</td>


			<td><input readonly type="text" name="dataRegistrazione" id= "dataRegistrazione"
				size="10" value="<%=toDateString(animale.getDataRegistrazione())%>"
				nomecampo="registrazione" tipocontrollo="T2,T6,T7"
				labelcampo="Data Registrazione" />&nbsp; <a href="#"
				onClick="cal19.select(document.forms[0].dataRegistrazione,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a><font color="red">* Data riportata
			dal documento.</font></td>
		</tr>		






<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Residenza proprietario fuori regione</dhv:label>
				</td>
				<td >
				<input type="checkbox" id="flagFuoriRegione_propr" name="flagFuoriRegione_propr" />

				</td>

</tr>





		<tr class="containerBody" id="proprietario">
			<td class="formLabel" nowrap><dhv:label name="">Seleziona Proprietario</dhv:label>
			</td>
			<td>
			<table cellspacing="0" border="0" width="100%" class="details">
			<%int idTipologiaLineaProdProprietario = -1;
			Stabilimento stabProprietario = new Stabilimento();
			  %>
				<dhv:evaluate
					if="<%=(animale != null
										&& animale.getProprietario() != null && animale
										.getProprietario().getIdOperatore() > 0)%>">
								<%idTipologiaLineaProdProprietario = ((LineaProduttiva) (((Stabilimento) animale
										.getProprietario().getListaStabilimenti()
										.get(0)).getListaLineeProduttive().get(0))).getIdRelazioneAttivita(); 
										stabProprietario = (Stabilimento) animale
										.getProprietario().getListaStabilimenti()
										.get(0); //STABILIMENTO PER EVENTULE COMUNE CATTURA%>
					<input type="hidden" id="tipologia_proprietario" value="<%=idTipologiaLineaProdProprietario%>"/>

					<tr>

						<td><%=toHtml(animale.getProprietario()
										.getRagioneSociale())%></td>
					</tr>
					<input type="hidden" name="idProprietario" id="idProprietario"
						value="<%=(animale.getProprietario() != null) ? ((LineaProduttiva) (((Stabilimento) animale
								.getProprietario().getListaStabilimenti()
								.get(0)).getListaLineeProduttive().get(0)))
								.getId()
								: ""%>">
								
								
								<!-- Sovrascrivo valore asl con quello del proprietario se il ruolo di chi sta inserendo lo prevede -->
					<dhv:evaluate
						if="<%=Animale
											.setAslRiferimentoAnimaleByRole(User)%>">
						<input type="hidden" name="idAslRiferimento" id="idAslRiferimento"
							value="<%=(animale.getProprietario() != null) ? ((Stabilimento) animale
									.getProprietario().getListaStabilimenti()
									.get(0)).getIdAsl()
									: "-1"%>">

					</dhv:evaluate>

				</dhv:evaluate>


				<tr>
					<td><dhv:evaluate
						if="<%=(
								//User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) && 
								User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA")))%>">
						
						<a onclick="win_open_search_proprietario('1','true');return false;">Ricerca</a>
						<a onclick="win_open_add_proprietario('1','true','1');return false;">Aggiungi</a>
							
						<!-- <a
						href="javascript:popUp('OperatoreAction.do?command=SearchForm&tipologiaSoggetto=1&popup=true');">Ricerca</a> -->
					</dhv:evaluate> <dhv:evaluate if="<%=(
							//User.getRoleId() ==  new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) || 
							User.getRoleId() ==  new Integer(ApplicationProperties.getProperty("UNINA")))%>">
						<a
							onclick="window.open('OperatoreAction.do?command=SearchForm&tipologiaSoggetto=1&popup=true','','width=800,height=600');return false;"
							href="OperatoreAction.do?command=SearchForm&tipologiaSoggetto=1&popup=true">Ricerca</a>
						<a onclick="window.open('OperatoreAction.do?command=Add&tipologiaSoggetto=1&<%=lineaProduttivaFiltro%>&popup=true&idLineaProduttiva=1','','scrollbars=1,width=800,height=600');return false;"
							href="OperatoreAction.do?command=Add&tipologiaSoggetto=2&<%=lineaProduttivaFiltro%>&popup=true&idLineaProduttiva=1">Aggiungi</a>
						<!--<a href="javascript:popUp('OperatoreAction.do?command=SearchForm&tipologiaSoggetto=1&popup=true&idLineaProduttiva1=1');">Ricerca</a> -->
					</dhv:evaluate>
					</td>
				</tr>
			</table>

			<dhv:evaluate
				if="<%=(animale.getProprietario() == null || (animale
								.getProprietario() != null && animale
								.getProprietario().getIdOperatore() <= 0))%>">
				<input type="hidden" name="idProprietario" id="idProprietario"
					value="-1">
			</dhv:evaluate></td>

		</tr>

		<%
			int idDetentore = -1;
			String nomeDetentore = "";
			int idTipologiaLineaProduttivaDetentore = -1;
			int idComuneCattura = -1;

				if (animale != null && animale.getDetentore() != null && animale.getDetentore().getIdOperatore() > 0) {
					nomeDetentore = toHtml(animale.getDetentore()
							.getRagioneSociale());
					idTipologiaLineaProduttivaDetentore = ((LineaProduttiva) (((Stabilimento) animale
							.getDetentore().getListaStabilimenti().get(0))
							.getListaLineeProduttive().get(0))).getIdRelazioneAttivita();
					idDetentore = ((animale.getDetentore() != null && animale
							.getDetentore().getListaStabilimenti().size() > 0)) ? ((LineaProduttiva) (((Stabilimento) animale
							.getDetentore().getListaStabilimenti().get(0))
							.getListaLineeProduttive().get(0))).getId()
							: -1;
							if ((idTipologiaLineaProdProprietario == LineaProduttiva.idAggregazioneSindaco || 
									idTipologiaLineaProdProprietario == LineaProduttiva.idAggregazioneSindacoFR) && 
									idTipologiaLineaProduttivaDetentore == LineaProduttiva.idAggregazioneCanile){
								idComuneCattura = stabProprietario.getSedeOperativa().getComune();
								System.out.println("COMUNE CATTURA   "+idComuneCattura);
							}
				
				}
		
		%>
		
						<input type="hidden" name="idDetentore" id="idDetentore"
					value="<%=idDetentore%>">
<dhv:evaluate if="<%=(
		//User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) && 
		User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA")))%>">
		<!-- dhv:evaluate if='<%=(animale.getIdSpecie() == 1)%>' -->
		<tr class="containerBody" id="detentore">
			<td class="formLabel" nowrap><dhv:label name="">Selezione Detentore</dhv:label>
			</td>
			<td>

			<table cellspacing="0" border="0" width="100%" class="details">
				<!-- dhv:evaluate if="<%=(Cane != null && Cane.getDetentore() != null)%>" -->

				<tr>

					<td><%=nomeDetentore%></td>
				</tr>

				<!-- /dhv:evaluate -->
				<tr>
					<td><dhv:evaluate if="<%=(
							//User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) && 
							User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA")))%>">
						<a onclick="win_open_search_delegato('&idLineaProduttiva1=5');return false;">Ricerca</a>
						<a onclick="win_open_add_delegato('<%=lineaProduttivaFiltro%>');return false;">Aggiungi</a>
							
						<!--<a
						href="javascript:popUp('OperatoreAction.do?command=SearchForm&tipologiaSoggetto=2&popup=true');">Ricerca</a>-->
					</dhv:evaluate> <dhv:evaluate if="<%=(
							//User.getRoleId() ==  new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) || 
							User.getRoleId() ==  new Integer(ApplicationProperties.getProperty("UNINA")))%>">
						<a onclick="win_open_search_delegato('');return false;">Ricerca</a>
						<a onclick="win_open_add_delegato('<%=lineaProduttivaFiltro%>');return false;">Aggiungi</a>
						<!-- <a href="javascript:popUp('OperatoreAction.do?command=SearchForm&tipologiaSoggetto=2&popup=true&idLineaProduttiva1=1');">Ricerca</a>	-->
					</dhv:evaluate></td>
				</tr>
			</table>

</dhv:evaluate>
			</td>

		</tr>
		<!-- /dhv:evaluate -->


		<tr class="containerBody">
			<dhv:evaluate if='<%=(animale.getIdSpecie() != 3)%>'>
				<!--  RAZZA CANE GATTO -->
				<td class="formLabel"><dhv:label name="">Razza</dhv:label></td>
				<td><%=razzaList.getHtmlSelect("idRazza", animale
								.getIdRazza())%>
				<%=showAttribute(request, "idRazza")%> <font color="red">*</font></td>
			</dhv:evaluate>
			<dhv:evaluate if='<%=(animale.getIdSpecie() == 3)%>'>
				<!--  RAZZA FURETTO -->
				<input type="hidden" name="idRazza" id="idRazza" value="0"></input>
			</dhv:evaluate>
		</tr>
		<tr>
			<td class="formLabel"><dhv:label name="">Sesso</dhv:label></td>
			<td><input type="radio" name="sesso" value="M" id="sesso"
				<%=("M".equals(animale.getSesso())) ? " checked" : ""%>> <dhv:label
				name="cani.sesso.maschio">M</dhv:label> <input type="radio"
				name="sesso" value="F" id="sesso"
				<%=("F".equals(animale.getSesso())) ? " checked" : ""%>> <dhv:label
				name="cani.sesso.femmina">F</dhv:label></td>
		</tr>

		<dhv:evaluate if='<%=(animale.getIdSpecie() == 1)%>'>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Taglia</dhv:label></td>
				<td>
<%
	if(idTipologiaLineaProdProprietario==LineaProduttiva.idAggregazioneSindaco)
	{
%>
		<%=tagliaList.getHtmlSelectDescriptionEstesa("idTaglia", Cane.getIdTaglia())%> <%=showAttribute(request, "idTaglia")%> 
<%
	}
	else
	{
%>
		<%=tagliaList.getHtmlSelect("idTaglia", Cane.getIdTaglia())%> <%=showAttribute(request, "idTaglia")%> 
<% 
	}
%>

					<font color="red">*</font>
<%
				if(idTipologiaLineaProdProprietario==LineaProduttiva.idAggregazioneSindaco)
				{
%>
					<font id="avvisoTaglia"><i>La taglia si riferisce al peso presunto del cane da adulto</i></font>
<%
				}
%>
				</td>
			</tr>

		</dhv:evaluate>

		<dhv:evaluate if='<%=(animale.getIdSpecie() == 3)%>'>
			<!-- SE E' UN FURETTO, LA TAGLIA PUO' ESSERE SOLO MEDIA (2) -->
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Taglia</dhv:label></td>
				<td>Piccola</td>
			</tr>
			<input type="hidden" id="idTaglia" name="idTaglia" value="1"></input>
		</dhv:evaluate>

		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Nato il</dhv:label></td>


			<td><input readonly type="text" name="dataNascita" id="dataNascita" size="10"
				onchange="mostraOrigine()"
				value="<%=toDateString(animale.getDataNascita())%>"
				nomecampo="nascita" tipocontrollo="T2" labelcampo="Data Nascita" />&nbsp;
			<a href="#" 
				onClick="cal19.select(document.forms[0].dataNascita,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a><font color="red">*</font> <dhv:label
				name="">Data nascita presunta</dhv:label> <input type="checkbox"
				name="flagDataNascitaPresunta"
				<%=animale.isFlagDataNascitaPresunta() ? "DEFAULT CHECKED"
							: ""%> />

			</td>
		</tr>


		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Mantello</dhv:label></td>
			<td><%=mantelloList.getHtmlSelect("idTipoMantello", animale
							.getIdTipoMantello())%> <%=showAttribute(request, "idTipoMantello")%>
			<font color="red">*</font></td>
		</tr>


		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Nome</dhv:label></td>
			<td><input type="text" size="30" id="nome" name="nome"
				maxlength="41" value="<%=toHtmlValue(animale.getNome())%>">
			<%=showAttribute(request, "nome")%></td>
		</tr>
 


		<tr class="containerBody">
			<td class="formLabel"><dhv:label name="">Microchip</dhv:label></td>
		<dhv:evaluate if="<%=!ApplicationProperties.getProperty("VAM_ID").equals(session.getAttribute("caller")) %>" >
			<td><input type="text" size="16" id="microchip" name="microchip"
				maxlength="15" value="<%=(animale.getMicrochip() != null) ? animale.getMicrochip() : "" %>"
				onchange="verificaInserimentoAnimale(document.forms[0].microchip)">

			<font color="red">*</font> <%=showError(request, "ErroreMicrochip")%>&nbsp;&nbsp;

			</td></dhv:evaluate>
			
		<dhv:evaluate if="<%=ApplicationProperties.getProperty("VAM_ID").equals(session.getAttribute("caller")) %>" >
			<td><input type="text" size="16" id="microchip" name="microchip"
				maxlength="15" value="<%=(animale.getMicrochip() != null) ? animale.getMicrochip() : "" %>"
				onchange="verificaInserimentoAnimale(document.forms[0].microchip)" readonly="readonly">

			<font color="red">*</font> <%=showError(request, "ErroreMicrochip")%>&nbsp;&nbsp;

			</td></dhv:evaluate>
		</tr>

		
		<tr class="containerBody" style="">
			<td class="formLabel"><dhv:label name="">Data chippatura</dhv:label>
			</td>
			<td><input readonly type="text" name="dataMicrochip" size="10"
				value="<%=toDateasString(animale.getDataMicrochip())%>"
				nomecampo="chippatura" tipocontrollo="T2,T17"
				labelcampo="Data Chippatura" /> &nbsp; <a href="#"
				onClick="cal19.select(document.forms[0].dataMicrochip,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a> <font color=red>&nbsp;*</font></td>
				
				<td>		<dhv:evaluate if="<%=(User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) || 
				User.getRoleId() == new Integer(ApplicationProperties.getProperty("UNINA")))%>">
				
				<input type="hidden" name="idVeterinarioMicrochip" id="idVeterinarioMicrochip" value="<%=User.getUserId() %>" />
				
	    </dhv:evaluate>	</td>
		</tr>
		
		
	
		<dhv:evaluate if="<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) && 
				User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA")))%>">

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Veterinario chippatura</dhv:label>
				</td>
				<td>
				<table cellspacing="0" border="0" width="100%" class="details">
					<tr>
						<td><dhv:evaluate if='<%=(animale.getIdSpecie() == Cane.idSpecie)%>'>
							<%=veterinariList.getHtmlSelect(
									"idVeterinarioMicrochip", Cane
											.getIdVeterinarioMicrochip())%>
						</dhv:evaluate> <dhv:evaluate if='<%=(animale.getIdSpecie() == Gatto.idSpecie)%>'>
							<%=veterinariList.getHtmlSelect(
									"idVeterinarioMicrochip", Gatto
											.getIdVeterinarioMicrochip())%>
						</dhv:evaluate><dhv:evaluate if='<%=(animale.getIdSpecie() == Furetto.idSpecie)%>'>
							<%=veterinariList.getHtmlSelect(
									"idVeterinarioMicrochip", Furetto
											.getIdVeterinarioMicrochip())%>
						</dhv:evaluate></td>
					</tr>
				</table>


				</td>

			</tr>
			
			
			



			<dhv:evaluate if='<%=(animale.getIdSpecie() == 1)%>'>
				<tr class="containerBody">
					<td class="formLabel">Morsicatore</td>
					<td><input type="checkbox" id="flagMorsicatore"
						onclick="checkMorsicatore()" name="flagMorsicatore" />

					<div id="mors"><br>
					Date: <input readonly type="text" id="dataMorso" name="dataMorso"
						size="10"
						value="<%=(Cane.getDataMorso() != null) ? toDateasString(Cane
									.getDataMorso())
									: ""%>"
						nomecampo="dataMorso" tipocontrollo="T10,T9"
						labelcampo="Data Morsicatore N.1" /> &nbsp; <a href="#"
						onClick="cal19.select(document.forms[0].dataMorso,'anchor19','dd/MM/yyyy'); return false;"
						NAME="anchor19" ID="anchor19"> <img
						src="images/icons/stock_form-date-field-16.gif" border="0"
						align="absmiddle"></a></div>
					</td>
				</tr>
			</dhv:evaluate>

			<dhv:evaluate if='<%=(animale.getIdSpecie() == 1)%>'>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Aggressivo</dhv:label>
					</td>
					<td><input type="checkbox" name="flagAggressivo"
						<%if (Cane.isFlagAggressivo()) {%> checked="checked" <%}%> /></td>
				</tr>
			</dhv:evaluate>
		</dhv:evaluate>
		<tr class="containerBody" id="sterilizzazione">
			<td class="formLabel"><dhv:label name="">Data Sterilizzazione</dhv:label>
			</td>
			<td>
			<table border="0">
				<tr>
					<td><input type="checkbox" name="flagSterilizzazione"
						onclick="javascript : if (this.checked==true) { document.getElementById('divster').style.display='' } else{document.getElementById('divster').style.display='none'}"
						<%=(animale.isFlagSterilizzazione()) ? "checked" : ""%>></td>
				</tr>
				<tr>
					<td>
					<div id="divster" style="display: none"><input readonly
						type="text" name="dataSterilizzazione" size="10"
						value="<%=toDateasString(animale.getDataSterilizzazione())%>"
						nomecampo="dataSterilizzazione" tipocontrollo="T10,T11,T9"
						labelcampo="Data Sterilizzazione" /> <a href="#"
						onClick="cal19.select(document.forms[0].dataSterilizzazione,'anchor19','dd/MM/yyyy');  svuota(); return false;"
						NAME="anchor19" ID="anchor19"> <img
						src="images/icons/stock_form-date-field-16.gif" border="0"
						align="absmiddle"></a>&nbsp; &nbsp;
						<dhv:evaluate if="<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))
						&& User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA")))%>">
						<dhv:evaluate if ="<%=(animale.getIdSpecie() != Furetto.idSpecie) %>">
						Contributo Regionale <input
						type="checkbox" name="flagContributoRegionale"
						id="flagContributoRegionale"
						<%if (animale.isFlagContributoRegionale()) {%> checked="checked"
						<%}%> onclick="visualizzaPratiche();" /> <%=showAttribute(request, "errContr")%>&nbsp;
						</dhv:evaluate>
						</dhv:evaluate>
					<br />
					<br />

					<div id="pratica_contributo" name="pratica_contributo"
						style="visibility: hidden"><input type="hidden"
						name="oldPratica"
						value="<%=request.getParameter("pratica") != null ? request
					.getParameter("pratica") : ""%>">
					<select id="idProgettoSterilizzazioneRichiesto"
						name="idProgettoSterilizzazioneRichiesto">
					</select></div>

					<%=showError(request, "praticaError")%> <input type="hidden"
						name="contributoPagato"></div>

					</td>
				</tr>

			</table>


			</td>
		</tr>



		<dhv:evaluate if="<%=(User.getRoleId() != 24 && User.getRoleId() != Integer.valueOf(ApplicationProperties.getProperty("UNINA")))%>">

			<tr class="containerBody">
				<td class="formLabel"><dhv:label
					name="anagrafe_animali_tatuaggio">Tatuaggio/2°Microchip</dhv:label>
				</td>
				<td><input type="text" size="20" name="tatuaggio"
					id="tatuaggio" maxlength="15"
					value="<%=toHtmlValue(animale.getTatuaggio())%>"
					onchange="verificaInserimentoAnimale(document.forms[0].tatuaggio)">
				<%=showError(request, "ErroreTatuaggio")%>&nbsp;&nbsp;</td>

			</tr>

			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data rilascio secondo Microchip</dhv:label>
				</td>
				<td><input readonly type="text" name="dataTatuaggio" size="10"
					value="<%=toDateasString(animale.getDataTatuaggio())%>"
					nomecampo="dataTatuaggio" tipocontrollo="T10,T11,T9"
					labelcampo="Data Secondo MC" />&nbsp; <a href="#"
					onClick="cal19.select(document.forms[0].dataTatuaggio,'anchor19','dd/MM/yyyy'); return false;"
					NAME="anchor19" ID="anchor19"> <img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmiddle"> </a></td>

			</tr>


<%-- COMMENTATO IN APPLICAZIONE CR 01/10/14 MODIFICA SCHEDA ANAGRAFICA ANIMALE
DECOMMENTATO NUOVAMENTE IN APPLICAZIONE CR 2015 PASSAPORTI A PRIORI
	 --%>		<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Passaporto</dhv:label>
				</td>
				<td><input type="text" name="numeroPassaporto" size="15"
					maxlength="13"
					value="<%=toHtmlValue(animale.getNumeroPassaporto())%>" onchange="javascript:verificaPassaporto(this);"></td>
			</tr>
			
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Data rilascio passaporto - Data scadenza passaporto</dhv:label>
				</td>
				<td> <input readonly type="text"
					name="dataRilascioPassaporto" id="dataRilascioPassaporto" size="10"
					value="<%=toDateasString(animale.getDataRilascioPassaporto())%>"
					nomecampo="dataRilascioPassaporto" tipocontrollo=""
					labelcampo="Data rilascio passaporto" />&nbsp; <a href="#" 
					onClick="cal19.select2(document.forms[0].dataRilascioPassaporto, document.forms[0].dataScadenzaPassaporto, 'anchor19','dd/MM/yyyy'); return false;"
					NAME="anchor19" ID="anchor19"> <img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmiddle"></a>&nbsp;&nbsp;
					--
					<input type="text" readonly
					name="dataScadenzaPassaporto" id="dataScadenzaPassaporto" size="10"
					value=""
					nomecampo="dataScadenzaPassaporto" tipocontrollo=""
					labelcampo="Data scadenza passaporto" />&nbsp; <a href="#" 
					onClick="cal19.select(document.forms[0].dataScadenzaPassaporto, 'anchor19','dd/MM/yyyy'); return false;"
					NAME="anchor19" ID="anchor19"> <img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmidle"></a>&nbsp;&nbsp;</td>
			</tr>

	  </dhv:evaluate>
	  </table>
	  <br/>
	  <table id="origine_table" cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
			<input type="hidden" id="abilita_origine" value="false"/>
			<tr>
				<th  colspan="2"><strong>Dati provenienza animale mai anagrafato</strong></th>
			</tr>
			
			
  			<tr class="containerBody" id="provenienza_soggetto_ritrovamento">
			    <td class="formLabel"><dhv:label name="">Seleziona origine animale</dhv:label></td>
			    <td  bgcolor="#b3d9ff" >
				    <input type="radio" id="origine_da" name="origine_da" value="in_regione"> In regione&nbsp;&nbsp;
				  	<input type="radio" id="origine_da" name="origine_da" value="fuori_regione"> Fuori regione&nbsp;&nbsp;
					<% if(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))
						&& User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA"))){%>
				  	<input type="radio" id="origine_da" name="origine_da" value="nazione_estera"> Proveniente da nazione estera&nbsp;&nbsp;
				  	<% } %>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Pulisci campo" onclick="removNoDisplay()"/)>
				</td>
			</tr>
			
			<tr class="containerBody" id="radio_tipo_origine" height="175">
    			<td class="formLabel"><dhv:label name="">Specificare la provenienza</dhv:label></td>  
    				<td  bgcolor="#b3d9ff">
      					<table width="100%" cellspacing="0" border="0">
        					<tr>
          						<td>
						            <input type="radio" name="tipo_origine" id="tipo_origine" value="soggetto_fisico"> Da proprietario&nbsp;&nbsp;
						  	        <input type="radio" name="tipo_origine" id="tipo_origine"  value="ritrovamento"> Da ritrovamento
						  	    </td>
  	      						<td align="center" id="dati_ritrovamento">
   	 				            	<b>DATI RITROVAMENTO</b>
									<table bgcolor="#ffffff" cellspacing="10" style="border:'none'">
	      <tr>
    	  <td><label>Provincia</label></td>
		  <td>
			<select class="todisable" name="provincia_ritrovamento" id="provincia_ritrovamento">
			  <% System.out.println(request.getAttribute("provincia_ritrovamento")+" - "+request.getAttribute("comune_ritrovamento"));
			  if(request.getAttribute("provincia_ritrovamento")!= null && !((String)request.getAttribute("provincia_ritrovamento")).equals("")){ 
				out.print("<option value=\""+((String)request.getAttribute("provincia_ritrovamento"))+"\">"+provinceList.getSelectedValue(Integer.parseInt(((String)request.getAttribute("provincia_ritrovamento"))))+"</option>");%>  
			  <%}else{ %>
			  <option value="">Inserire le prime 4 lettere</option>
			  <% } %>
			</select>		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>
		    <input type="hidden" name="provincia_ritrovamentoTesto"
			  id="provincia_ritrovamentoTesto" />
          </td>
	    </tr>
	
	    <tr>
   	      <td><label>Comune</label></td>
		  <td>
		    <select class="todisable" name="comune_ritrovamento" id="comune_ritrovamento">
			<% if(request.getAttribute("comune_ritrovamento")!= null && !((String)request.getAttribute("comune_ritrovamento")).equals("")){ 
				out.print("<option selected value=\""+((String)request.getAttribute("comune_ritrovamento"))+"\">"+comuniList_all.getSelectedValue(Integer.parseInt(((String)request.getAttribute("comune_ritrovamento"))))+"</option>"); %>  
			 <% }else{%>
			  <option value="">Inserire le prime 4 lettere</option>
			  <% } %>
			
			</select>		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>
			<input type="hidden" name="comune_ritrovamentoTesto" id="comune_ritrovamentoTesto" />
		  </td>
	    </tr>
	    
	    <tr>
			<td><label>Indirizzo</label></td>
			<td>
				<input type="text" size="50" maxlength="50" id="indirizzo_ritrovamento" name = "indirizzo_ritrovamento" value="<%=(request.getAttribute("indirizzo_ritrovamento")!=null ? ((String)request.getAttribute("indirizzo_ritrovamento")) : "")%>">
			 	<font color="red">&nbsp;*&nbsp;</font> 
			</td>
 	    </tr>
 	    
 	    <tr>
			        <td><label>Data</label></td>
                    <td><input readonly type="text" id="data_ritrovamento" name="data_ritrovamento" value="<%=(request.getAttribute("data_ritrovamento")!=null ? ((String)request.getAttribute("data_ritrovamento")) : "")%>"
				      size="10" value=""
					  nomecampo="ritrovamento" 
					  labelcampo="Data Ritrovamento" />&nbsp; <a href="#"
				      onClick="cal19.select(document.forms[0].data_ritrovamento,'anchor19','dd/MM/yyyy'); return false;"
				      NAME="anchor19" ID="anchor19"><img
				      src="images/icons/stock_form-date-field-16.gif" border="0"
				      align="absmiddle"></a><font color="red">* Data ritrovamento</font>
				    </td>
				  </tr>	
  	       </table>
  	      </td>
  	    </tr>
  	  </table>
    </td>
  </tr>
  
  		<% if(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))
				&& User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA"))){%>
			<!--  SEZIONE PROVENIENZA ALTRA NAZIONE -->
			<tr  id="provenienza_da_altra_nazione" class="containerBody">
				<td class="formLabel"><dhv:label name="">Provenienza da nazione estera</dhv:label></td>
				<td bgcolor="#b3d9ff">
				     <%//if (animale != null && request.getAttribute("idNazioneProvenienza")!=null  && !((String)request.getAttribute("idNazioneProvenienza")).equals("-1")) {;}
					  %>
					<div id="utProvenienzaEstera" 
						<%=(request.getAttribute("idNazioneProvenienza") != null && !((String)request.getAttribute("idNazioneProvenienza")).equals("-1")) ? (""): ("style=\"display:none\"")%>>
						<%=nazioniList.getHtmlSelect("idNazioneProvenienza",Integer.parseInt((request.getAttribute("idNazioneProvenienza") != null ? (String)request.getAttribute("idNazioneProvenienza") : "-1" )))%><font color="red">&nbsp;*</font> 
						<input type="hidden" id="idNazioneProvenienza_hidden" 
						  value='<%=(request.getAttribute("idNazioneProvenienza") != null ? (String)request.getAttribute("idNazioneProvenienza") : "-1")%>' />
						<input type="hidden" id="verDate" name="verDate" value="ok" />
						<br><textarea rows="4" cols="50" id="noteNazioneProvenienza" name="noteNazioneProvenienza"><%=((String)request.getAttribute("noteNazioneProvenienza")!=null ? (String)request.getAttribute("noteNazioneProvenienza") : "")%></textarea>
					</div>
				</td>
			</tr>
			<% }else{ %>
  				<input type="hidden" id="provenienza_da_altra_nazione"/>
  				<input type="hidden" id="utProvenienzaEstera" /> 
  				<input type="hidden" id="idNazioneProvenienza" value="-1" />
  				<input type="hidden" id="noteNazioneProvenienza" value=""/>
			<% } %>
  
<!------------------------------------- GESTIONE PROPRIETARIO DI PROVENIENZA ---------------------------------------->
						<%
			int idProprietarioProvenienza = -1;
			String nomeProprietarioProvenienza = "";
				

				if (animale != null && animale.getProprietarioProvenienza() != null && animale.getProprietarioProvenienza().getIdOperatore() > 0) {
					if(animale.getProprietarioProvenienza().getIdOperatore()<10000000){
					
					nomeProprietarioProvenienza = toHtml(animale.getProprietarioProvenienza().getRagioneSociale());
					idProprietarioProvenienza = ((animale.getProprietarioProvenienza() != null && animale
							.getProprietarioProvenienza().getListaStabilimenti().size() > 0)) ? ((LineaProduttiva) (((Stabilimento) animale
							.getProprietarioProvenienza().getListaStabilimenti().get(0))
							.getListaLineeProduttive().get(0))).getId()
							: -1;
				}else{
					nomeProprietarioProvenienza = toHtml(animale.getProprietarioProvenienza().getRagioneSociale());
					idProprietarioProvenienza = animale.getProprietarioProvenienza().getIdOperatore();
				}
				}
		
		%>
  <input type="hidden" name="idProprietarioProvenienza" id="idProprietarioProvenienza"	value="<%=idProprietarioProvenienza%>">
  <dhv:evaluate if="<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA")))%>">
    <tr class="containerBody" id="proprietarioProvenienza">
	  <td class="formLabel" nowrap><dhv:label name="">Seleziona proprietario provenienza</dhv:label></td>
	  <td  bgcolor="#b3d9ff" ><table cellspacing="0" border="0" width="100%" class="details">
	        <tr>
              <td id="nome_proprietario_provenienza"><%=nomeProprietarioProvenienza%></td>
			</tr>
			<tr id="mostraProprietarioProvenienza" >
   			  <td bgcolor="#ffffff"><dhv:evaluate if="<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA")))%>">
						&nbsp;&nbsp;
						
						<a id="ricerca_fr" onclick="win_open_search_origine('8','true');return false;">Ricerca</a>
						<a onclick="win_open_add_origine('8','true','1');return false;">Aggiungi</a>
				</dhv:evaluate> 
			  </td>
			</tr>
		  </table></td>
		</tr>
  </dhv:evaluate>

<!-- ------------------------------------------------------------------------------------------------------------ -->

			<!--  SEZIONE ACQUISTO ONLINE -->
			<tr  class="containerBody" id="mostraAcquistoOnline">
				<td class="formLabel"><dhv:label name="">Acquisto online</dhv:label></td>
				<td bgcolor="#b3d9ff">
					<input type="checkbox" id="flagAcquistoOnline"	onclick="CheckGenericoOrigineAnimale(this)" name="flagAcquistoOnline" <%if (animale != null && request.getAttribute("sitoWebAcquisto")!=null && !((String)request.getAttribute("sitoWebAcquisto")).equals("-1")) {%> checked="checked" <%}%> />
					<div id="utProvenienzaOnline" 
						<%=((request.getAttribute("sitoWebAcquisto") != null && !((String)request.getAttribute("sitoWebAcquisto")).equals("-1")) ? "": "style=\"display:none\"")%>>
						<input type="hidden" id="sitoWebAcquisto_hidden" 
						  value='<%=(request.getAttribute("sitoWebAcquisto") != null ? (String)request.getAttribute("sitoWebAcquisto") : "-1")%>' />
						<select id="sitoWebAcquisto" name="sitoWebAcquisto" onChange="abilitaNoteOnline()">
							<option value="-1">--Seleziona---</option>
							<option value="AnimalHouseOnline">Animal House Online</option>
							<option value="CaniECuccioli">Cani e Cuccioli</option>
							<option value="AnnunciAnimali">Annunci Animali</option>
							<option value="Subito">Subito</option>
							<option value="Ebay">Ebay</option>
							<option value="Altro">Altro</option>
						</select><font color="red">&nbsp;*</font> 
						<br><textarea rows="4" cols="50" id="noteAcquistoOnline" name="noteAcquistoOnline"><%=((String)request.getAttribute("noteAcquistoOnline")!=null ? (String)request.getAttribute("noteAcquistoOnline") : "")%></textarea>
						<input type="hidden" id="verDate" name="verDate" value="ok" />
						</div>
				</td>
			</tr>
			
		
			<!--  SEZIONE MANCATA TRACCIABILITA' -->
			<tr  id="mancata_tracciabilita" class="containerBody">
				<td class="formLabel"><dhv:label name="">Informazioni mancanti sull'origine dell'animale</dhv:label></td>
				<td bgcolor="#b3d9ff">
					<input type="checkbox" id="flagSenzaOrigine"	onclick="CheckGenericoOrigineAnimale(this)" name="flagSenzaOrigine" 
					     <%if (animale != null && request.getAttribute("flagSenzaOrigine")!=null  && ((String)request.getAttribute("flagSenzaOrigine")).equals("on")) {%> checked="checked" <%}%> />
				&nbsp;<font color="red"><b>SPUNTARE SOLO SE TUTTI I TENTATIVI DI OTTENERE INFORMAZIONI COMPLETE SULLA PROVENIENZA DELL'ANIMALE SONO FALLITI.
				</td>
			</tr>

	</table>
	<dhv:evaluate if="<%=(User.getRoleId() != 24)%>">
		<br>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong>Dati Aggiuntivi</strong></th>
			</tr>

			<tr class="containerBody">
				<td valign="top" class="formLabel"><dhv:label name="">Segni particolari</dhv:label>
				</td>
				<td><textarea name="segniParticolari" rows="3" cols="50"><%=toString(animale.getSegniParticolari())%></textarea>
				<%=showAttribute(request, "descriptionError")%></td>
			</tr>
			<tr class="containerBody">
				<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label>
				</td>
				<td><textarea name="note" rows="3" cols="50"><%=toString(animale.getNota2())%></textarea>
				</td>
			</tr>
		</table>
		<br>


<dhv:evaluate if ="<%=!(User.getRoleId() == Integer.valueOf(ApplicationProperties.getProperty("UNINA") ))%>">
		<dhv:evaluate if='<%=(animale.getIdSpecie() == 1)%>'>
			<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">
				<tr>
					<th colspan="4"><strong><dhv:label name="">Controlli Richiesti</dhv:label></strong>
					</th>
				</tr>

				<!-- Leishmaniosi -->
				<%-- <tr class="containerBody" style="display: none">--%>
				<tr class="containerBody">
					<td width="25%" valign="top" class="formLabel2"><dhv:label
						name="">Leishmaniosi</dhv:label></td>
					<td width="40%" id="esitiLeishSindAdd">
					<table class="noborder">
						<tr>
							<td>Ord.sind. <input type="text" size="10"
								id="leishmaniosiNumeroOrdinanzaSindacale"
								name="leishmaniosiNumeroOrdinanzaSindacale" maxlength="15"
								value="<%=toString(Cane
											.getLeishmaniosiNumeroOrdinanzaSindacale())%>">
							del <input readonly type="text"
								id="leishmaniosiDataOrdinanzaSindacale"
								name="leishmaniosiDataOrdinanzaSindacale" size="10"
								value="<%=toDateasString(Cane
									.getLeishmaniosiDataOrdinanzaSindacale())%>"
								nomecampo="leishmaniosiDataOrdinanzaSindacale"
								tipocontrollo="T10,T9"
								labelcampo="Ordine Sindacale Leishmaniosi" /> &nbsp; <a
								href="#"
								onClick="cal19.select(document.forms[0].leishmaniosiDataOrdinanzaSindacale,'anchor19','dd/MM/yyyy'); return false;"
								NAME="anchor19" ID="anchor19"> <img
								src="images/icons/stock_form-date-field-16.gif" border="0"
								align="absmiddle"> </a></td>
						</tr>
					</table>
					</td>
				</tr>
				<!-- tr leishmaniosi -->

				<!-- Ehrlichiosi -->
				<%-- if ( !update ) { --%>
				<tr class="containerBody">
					<td valign="top" class="formLabel2"><dhv:label name="">Ehrlichiosi</dhv:label>
					</td>
					<td>
					<table class="noborder">
						<tr>
							<td><input type="checkbox" id="flagControlloEhrlichiosi"
								<%if (Cane.isFlagControlloEhrlichiosi()) {%> checked="checked"
								<%}%> name="flagControlloEhrlichiosi"
								onclick="abilitaEsitiEhi();" /></td>


							<td id="esitoEhradd1">
							<table class="noborder">
								<tr>
									<td>Data <input readonly type="text"
										id="dataControlloEhrlichiosi" name="dataControlloEhrlichiosi"
										size="10"
										value="<%=toDateasString(Cane
											.getDataControlloEhrlichiosi())%>"
										nomecampo="dataControlloEhrlichiosi"
										tipocontrollo="T10,T11,T9" labelcampo="Data Ehrlichiosi" />&nbsp;
									<a href="#"
										onClick="cal19.select(document.forms[0].dataControlloEhrlichiosi,'anchor19','dd/MM/yyyy'); return false;"
										NAME="anchor19" ID="anchor19"> <img
										src="images/icons/stock_form-date-field-16.gif" border="0"
										align="absmiddle"> </a> <%--} --%></td>
									<td>Esito <%-- if (.getEsitoControlloEhrlichiosi() == 0 || animale.getEsitoControlloEhrlichiosi() == 1 || animale.getEsitoControlloEhrlichiosi() == -1) { %>
    		<%=esitoList.getSelectedValue(animale.getEsitoControlloEhrlichiosi()) %>

    			
    	<%}else{ --%> <%=esitoList.getHtmlSelect(
									"esitoControlloEhrlichiosi", Cane
											.getEsitoControlloEhrlichiosi())%> <%-- }--%></td>
								</tr>
							</table>
							</td>


						</tr>

					</table>
					</td>
				</tr>

				<!-- Rickettiosi -->
				<tr class="containerBody">
					<td valign="top" class="formLabel2"><dhv:label name="">Rickettiosi</dhv:label>
					</td>
					<td>
					<table class="noborder">
						<tr>
							<td><input type="checkbox" id="flagControlloRickettsiosi"
								name="flagControlloRickettsiosi"
								<%if (Cane.isFlagControlloRickettsiosi()) {%> checked="checked"
								<%}%> onclick="abilitaEsitiRik();" /></td>
							<td id="esitoRickadd1">
							<table class="noborder">
								<tr>
									<td>Data <input readonly type="text"
										id="dataControlloRickettsiosi"
										name="dataControlloRickettsiosi" size="10"
										value="<%=toDateasString(Cane
									.getDataControlloRickettsiosi())%>"
										nomecampo="dataControlloRickettsiosi"
										tipocontrollo="T10,T11,T9" labelcampo="Data Rickettiosi" />&nbsp;
									<a href="#"
										onClick="cal19.select(document.forms[0].dataControlloRickettsiosi,'anchor19','dd/MM/yyyy'); return false;"
										NAME="anchor19" ID="anchor19"> <img
										src="images/icons/stock_form-date-field-16.gif" border="0"
										align="absmiddle"> </a></td>
									<td id=>Esito <%=esitoList.getHtmlSelect(
									"esitoControlloRickettsiosi", Cane
											.getEsitoControlloRickettsiosi())%></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<%--} --%>
			</table>
		</dhv:evaluate>



		<br />
		
		
 	<!-- ------------------------------CATTURA---------------------------------------------- -->
<table width="100%"><tr><td>
	 	<dhv:evaluate if='<%=(animale.getIdSpecie() != Furetto.idSpecie)%>'>
			<table id="cattura" name="cattura" cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">
				<tr>
					<th colspan="2"><strong>Dettagli Cattura</strong></th>
				</tr>

				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Cattura</dhv:label></td>
					<td><%-- if (!update) { --%> <input type="checkbox"
						onclick="javascript:checkProprietarioDetentore();"
						<%if (Cane.isFlagCattura()) {%> checked="checked" <%}%>
						id="flagCattura" name="flagCattura" /></td>
				</tr>


				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Comune Cattura</dhv:label>
					</td><input type="hidden" name="idComuneCattura" id="idComuneCattura" value="<%=idComuneCattura %>" onChange="javascript:visualizzaMunicipalita();"/>
					<td><%-- if (!update) { --%> <%=comuniList.getSelectedValue(idComuneCattura)%>
					<%-- } else { %>
		<%= ( asset.getComuneCattura() != null ) ? asset.getComuneCattura() : "" %>
	<% } --%></td>
				</tr>
				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Luogo Cattura</dhv:label>
					</td>
					<td><input type="text" name="luogoCattura"
						value="<%=toHtmlValue(Cane.getLuogoCattura())%>" id="luogoCattura" /></td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Data Cattura</dhv:label>
					</td>
					<td><%-- if (!update) { --%> <!--  <div id="divDataCatturaSi" style="display:none; vertical-align: middle">-->
					<div id="divDataCatturaSi" style=""><input
						readonly="readonly" type="text" id="dataCattura"
						name="dataCattura" size="10"
						value="<%=toDateasString(Cane.getDataCattura())%>"
						nomecampo="dataCattura" tipocontrollo="" labelcampo="Data Cattura" />
					&nbsp; <a href="#"
						onClick="cal19.select(document.forms[0].dataCattura,'anchor19','dd/MM/yyyy'); return false;"
						NAME="anchor19" ID="anchor19"> <img
						src="images/icons/stock_form-date-field-16.gif" border="0"
						align="absmiddle"> </a></div>
					<%-- } else{ %>
    	<%= ( asset.getDataCattura() != null ) ? asset.getDataCattura() : "" %>
    <% } --%></td>
				</tr>
				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Verbale Cattura</dhv:label>
					</td>
					<td><%-- if (!update) { --%> <textarea name="verbaleCattura"
						id="verbaleCattura" rows="3" cols="50" onmouseover=""></textarea>
					<%-- } else {%>
    <%= ( asset.getVerbaleCattura() != null ) ? asset.getVerbaleCattura() : "" %>	
   <%} --%></td>
				</tr>

			</table>
	  	</dhv:evaluate>
	  	
	  </td>
	  
	  </tr></table>
	  	<!-- --------------------------------------------------------------------------------------------------- -->

	</dhv:evaluate>
</dhv:evaluate>
</br>
	<dhv:permission name="anagrafe_canina-note_internal_use_only-add">
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong>NOTE USO INTERNO</strong></th>
			</tr>
			<tr class="containerBody">
				<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label>
				</td>
				<td><textarea name="noteInternalUseOnly" rows="3" cols="50"><%=toString(animale.getNoteInternalUseOnly())%></textarea>
				</td>
			</tr>
		</table>
	</dhv:permission>

	<input type="hidden" name="dosubmit" value="true" />
	<input type="hidden" name="saveandclone" value="" />

	<br />
	<input type="button"
		value="<dhv:label name="global.button.save">Save</dhv:label>"
		onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='';if(doCheck(this.form)){setTimestampStartRichiesta();this.form.submit()};" />

<dhv:evaluate if="<%=session.getAttribute("caller")==null || !ApplicationProperties.getProperty("VAM_ID").equals(session.getAttribute("caller")) %>" >
	<input type="button"
		value="Salva e Clona"
		onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='1';if(doCheck(this.form)){setTimestampStartRichiesta();this.form.submit()};" />
	
	</dhv:evaluate>
		
	<input type="hidden" name="doContinue" id="doContinue" value="">
	
	</form>
	
	<div id="dialogMunicipalita" title="Municipalit&agrave; del comune di Napoli">
    <jsp:include page="../registrazioni_animale/municipalita.jsp"></jsp:include>
</div>

<script language="javascript">

mostraOrigine();

$("form input:radio").change(function () {
	CheckGenericoOrigineAnimale(this);
});

function gestioneCheckboxOrigineDopoReload(){
	<% 
	if (request.getAttribute("origine_da")!=null){
		String origine_ = (String)request.getAttribute("origine_da");
		if(origine_.equals("in_regione")){  %>
			var $radios = $('input:radio[name=origine_da]');
			$radios.filter('[value=in_regione]').prop('checked', true);
			 document.getElementById("ricerca_fr").style.removeProperty("display");
	<% 	}else if(origine_.equals("fuori_regione")){  %>
		    var $radios = $('input:radio[name=origine_da]');
		    $radios.filter('[value=fuori_regione]').prop('checked', true);
		    document.getElementById("ricerca_fr").style.display="none";
	<% }else{ %>
	    var $radios = $('input:radio[name=origine_da]');
	    $radios.filter('[value=nazione_estera]').prop('checked', true);
	    
		document.getElementById("radio_tipo_origine").style.display="none";
	    document.getElementById("proprietarioProvenienza").style.display="none";
	    document.getElementById("dati_ritrovamento").style.display="none";
	    
		document.getElementById("provenienza_da_altra_nazione").style.removeProperty("display");
		document.getElementById("utProvenienzaEstera").style.removeProperty("display");
	    
	<%}
		if (request.getAttribute("tipo_origine")!=null){%>
			// OSCURO NAZIONE ESTERA
			document.getElementById("provenienza_da_altra_nazione").style.display="none";
			document.getElementById("utProvenienzaEstera").style.display="none";
			$("#idNazioneProvenienza").val("-1");	 
			document.getElementById("noteNazioneProvenienza").value="";<%
			String tipo_origine_ = (String)request.getAttribute("tipo_origine");
			if(tipo_origine_.equals("ritrovamento")){ %>
				var $radios = $('input:radio[name=tipo_origine]');
			    $radios.filter('[value=ritrovamento]').prop('checked', true);
			   	document.getElementById("proprietarioProvenienza").style.display="none";
			    document.getElementById("dati_ritrovamento").style.removeProperty("display");
			    // OSCURO ACQUISTO SU SITO WEB
				document.getElementById("mostraAcquistoOnline").style.display="none";
				$("#flagAcquistoOnline").removeAttr('checked');			
				$("#sitoWebAcquisto").val("-1");	 
				document.getElementById("sitoWebAcquisto_hidden").value="";
				 document.getElementById("noteAcquistoOnline").style.display="none";
				 document.getElementById("noteAcquistoOnline").value="";<%
			}else{ %> 
			   	var $radios = $('input:radio[name=tipo_origine]');
			   	$radios.filter('[value=soggetto_fisico]').prop('checked', true);
			   	document.getElementById("dati_ritrovamento").style.display="none";
			   	document.getElementById("proprietarioProvenienza").style.removeProperty("display");<%
			}
		}
	}else{  %>
		// OSCURO NAZIONE ESTERA
		document.getElementById("provenienza_da_altra_nazione").style.display="none";
		document.getElementById("utProvenienzaEstera").style.display="none";
		$("#idNazioneProvenienza").val("-1");	 
		document.getElementById("noteNazioneProvenienza").value="";
	
		document.getElementById("provenienza_soggetto_ritrovamento").style.display="none";
		document.getElementById("radio_tipo_origine").style.display="none";
		document.getElementById("proprietarioProvenienza").style.display="none";
		document.getElementById("dati_ritrovamento").style.display="none";
		var ele = document.getElementsByName("origine_da");
		for(var i=0;i<ele.length;i++)
			ele[i].checked = false;
		ele = document.getElementsByName("tipo_origine");
		for(var i=0;i<ele.length;i++)
			ele[i].checked = false;<% 
	} %>
}

function gestioneOrigineDopoReloadPagina(){
	<%if (request.getAttribute("flagFuoriRegione")==null ) {%>
		// OSCURO ANAGRAFE FUORI REGIONE
		document.getElementById("provenienza_da_altra_regione").style.display="none";
		$("#flagFuoriRegione").removeAttr('checked');			
		document.getElementById("utProvenienza").style.display="none";
		$("#idRegioneProvenienza").val("-1");	 
		document.getElementById("noteAnagrafeFr").value="";
	<% }else{ %>
		// OSCURO ACQUISTO SU SITO WEB
		document.getElementById("mostraAcquistoOnline").style.display="none";
		$("#flagAcquistoOnline").removeAttr('checked');			
		$("#sitoWebAcquisto").val("-1");	 
		document.getElementById("sitoWebAcquisto_hidden").value="";	
		 document.getElementById("noteAcquistoOnline").style.display="none";
		 document.getElementById("noteAcquistoOnline").value="";
	<% } %>
	
	<%if (request.getAttribute("flagSenzaOrigine")==null ) {%>
		document.getElementById("mancata_tracciabilita").style.display="none";
		$("#flagSenzaOrigine").removeAttr('checked');	
	<% } %>
	
	
	gestioneCheckboxOrigineDopoReload();
	
	<% if(request.getAttribute("flagAcquistoOnline")!= null){ 
	%>
  		$("#sitoWebAcquisto").val(''+document.getElementById("sitoWebAcquisto_hidden").value);
  		abilitaNoteOnline();
	<% }//else{ 
	%>
		// OSCURO ACQUISTO SU SITO WEB
		//document.getElementById("mostraAcquistoOnline").style.display="none";
		//$("#flagAcquistoOnline").removeAttr('checked');			
		//document.getElementById("utProvenienzaOnline").style.display="none";
		//$("#sitoWebAcquisto").val("-1");	 
		//document.getElementById("sitoWebAcquisto_hidden").value="";
		//document.getElementById("noteAcquistoOnline").value="";
	<% //} 
	%>
}


 openDivSterilizzazione();
 abilitaSelezionePropDet(<%=User.getSiteId()%>);
 
 <% if (request.getAttribute("flagFuoriRegione")!=null || request.getAttribute("flagAcquistoOnline")!= null || 
	    request.getAttribute("origine_da")!=null || request.getAttribute("origine_da")!=null || request.getAttribute("flagSenzaOrigine")!=null){  %>
	    gestioneOrigineDopoReloadPagina();
 <% }else{ %>
	document.getElementById("radio_tipo_origine").style.display="none";
	document.getElementById("dati_ritrovamento").style.display="none";
   	document.getElementById("proprietarioProvenienza").style.display="none";
	document.getElementById("provenienza_da_altra_nazione").style.display="none";
	document.getElementById("utProvenienzaEstera").style.display="none";
	 document.getElementById("noteAcquistoOnline").style.display="none";
	//$('#flagSenzaOrigine').attr('checked', true);
	//CheckGenericoOrigineAnimale(document.getElementById("flagSenzaOrigine"));
 <% } %>
 
 
 function mostraOrigine(){
	 var oggi = new Date();
	 var from =$("#dataNascita").val().split("/");
	 var data_reg = new Date(from[2], from[1] - 1, from[0]);
	 var timeinmilisec = oggi.getTime()-data_reg.getTime();
	 var giorni=(Math.floor(timeinmilisec / (1000 * 60 * 60 * 24)));
	 var idSpecie=($('select[name="idSpecie"]').val());
	 var tipologia_proprietario="0";
	 if (document.getElementById("tipologia_proprietario")!=null)
		 tipologia_proprietario=document.getElementById("tipologia_proprietario").value;
	 if(idSpecie==null)
		 idSpecie=$("#idSpecie").val();
	 if(giorni<365 && (tipologia_proprietario=="1" || tipologia_proprietario=="4" || tipologia_proprietario=="5" || tipologia_proprietario=="6" || tipologia_proprietario=="8") && idSpecie=="1"){
		 document.getElementById("origine_table").style.removeProperty("display");
		 document.getElementById("provenienza_da_altra_regione").style.removeProperty("display");
		 $("#abilita_origine").val("true");
	 }else{
		 document.getElementById("origine_table").style.display="none";
		 //document.getElementById("provenienza_da_altra_regione").style.display="none";
		 $("#abilita_origine").val("false");

	 }
 }
 
 function abilitaNoteOnline(){
	 
	var myselect = document.getElementById("sitoWebAcquisto");
	if(myselect.options[myselect.selectedIndex].value=="Altro"){
		 document.getElementById("noteAcquistoOnline").style.removeProperty("display");
	 }else{
		 document.getElementById("noteAcquistoOnline").style.display="none";
		 document.getElementById("noteAcquistoOnline").value="";
	 }
 }
 
 
 function win_open_search_delegato(id_linea){
		win=window.open('OperatoreAction.do?command=SearchForm&tipologiaSoggetto=2'+id_linea+'&popup=true','','scrollbars=1,width=800,height=600');
	  if (win != null) { var timer = setInterval(function() { if(win.closed) { clearInterval(timer); loadModalWindowUnlock(); } }, 1000); }
	}

	function win_open_add_delegato(id_linea){
		win= window.open('OperatoreAction.do?command=Add&tipologiaSoggetto=2&'+id_linea+'&popup=true&idLineaProduttiva=1','','scrollbars=1,width=800,height=600');
	  if (win != null) { var timer = setInterval(function() { if(win.closed) { clearInterval(timer); loadModalWindowUnlock(); } }, 1000); }
	}
 
 

function win_open_add_origine(tipologiaSoggetto, popup, id_linea){
  if($("input[name=origine_da]:checked").val() == "in_regione"){
		win= window.open('OperatoreAction.do?command=OrigineAdd&tipologiaSoggetto='+tipologiaSoggetto+'&<%=lineaProduttivaFiltro%>&popup='+popup+'&idLineaProduttiva='+id_linea+'&in_regione=si','','scrollbars=1,width=800,height=600');
  }else{
		win= window.open('OperatoreAction.do?command=OrigineAdd&tipologiaSoggetto='+tipologiaSoggetto+'&<%=lineaProduttivaFiltro%>&popup='+popup+'&idLineaProduttiva='+id_linea+'&in_regione=no','','scrollbars=1,width=800,height=600');
  }
  if (win != null) { var timer = setInterval(function() { if(win.closed) { clearInterval(timer); loadModalWindowUnlock(); } }, 1000); }
}

function win_open_search_origine(tipologiaSoggetto, popup){
  if($("input[name=origine_da]:checked").val() == "in_regione"){
		win= window.open('OperatoreAction.do?command=SearchForm&tipologiaSoggetto='+tipologiaSoggetto+'&popup='+popup+'&in_regione=si','','scrollbars=1,width=800,height=600');
  }else{
		win= window.open('OperatoreAction.do?command=SearchForm&tipologiaSoggetto='+tipologiaSoggetto+'&popup='+popup+'&in_regione=no','','scrollbars=1,width=800,height=600');
  }
  if (win != null) { var timer = setInterval(function() { if(win.closed) { clearInterval(timer); loadModalWindowUnlock(); } }, 1000); }
}

function win_open_add_proprietario(tipologiaSoggetto, popup, id_linea){
	if(document.getElementById('flagFuoriRegione_propr').checked) {
		win= window.open('OperatoreAction.do?command=Add&tipologiaSoggetto='+tipologiaSoggetto+'&<%=lineaProduttivaFiltro%>&popup='+popup+'&idLineaProduttiva='+id_linea+'&in_regione=no','','scrollbars=1,width=800,height=600');
	  }else{
			win= window.open('OperatoreAction.do?command=Add&tipologiaSoggetto='+tipologiaSoggetto+'&<%=lineaProduttivaFiltro%>&popup='+popup+'&idLineaProduttiva='+id_linea+'&in_regione=si','','scrollbars=1,width=800,height=600');
	  }
	  if (win != null) { var timer = setInterval(function() { if(win.closed) { clearInterval(timer); loadModalWindowUnlock(); } }, 1000); }	
	}

	function win_open_search_proprietario(tipologiaSoggetto, popup){
		if(document.getElementById('flagFuoriRegione_propr').checked) {
			win= window.open('OperatoreAction.do?command=SearchForm&tipologiaSoggetto='+tipologiaSoggetto+'&popup='+popup+'&in_regione=no','','scrollbars=1,width=800,height=600');
	  }else{
			win= window.open('OperatoreAction.do?command=SearchForm&tipologiaSoggetto='+tipologiaSoggetto+'&popup='+popup+'&in_regione=si','','scrollbars=1,width=800,height=600');
	  }
		  if (win != null) { var timer = setInterval(function() { if(win.closed) { clearInterval(timer); loadModalWindowUnlock(); } }, 1000); }
	}
	
<%
if(request.getAttribute("ErrorBlocco")!=null && !((String)request.getAttribute("ErrorBlocco")).equals("")){
%>
alert("<%=((String)request.getAttribute("ErrorBlocco"))%>");
<%
}	
%>
</script>