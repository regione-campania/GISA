<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="java.io.*,java.util.*,org.aspcfs.utils.web.*"%>


<%@page import="org.aspcfs.modules.system.base.SiteList"%>
<%@page import="org.aspcfs.modules.opu.base.StabilimentoList"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>

<%@page import="org.aspcfs.modules.system.base.SiteList"%>

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>

<jsp:useBean id="Cane"
	class="org.aspcfs.modules.anagrafe_animali.base.Cane" scope="request" />
<jsp:useBean id="Gatto"
	class="org.aspcfs.modules.anagrafe_animali.base.Gatto" scope="request" />
<jsp:useBean id="Furetto"
	class="org.aspcfs.modules.anagrafe_animali.base.Furetto"
	scope="request" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="utenteInserimento" class="org.aspcfs.modules.admin.base.User"
	scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="regioniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="tagliaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="mantelloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="esitoControlloList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="animale"
	class="org.aspcfs.modules.anagrafe_animali.base.Animale"
	scope="request" />
<jsp:useBean id="oldAnimale"
	class="org.aspcfs.modules.anagrafe_animali.base.Animale"
	scope="request" />
<jsp:useBean id="praticacontributi"
	class="org.aspcfs.modules.praticacontributi.base.Pratica"
	scope="request" />
<jsp:useBean id="veterinariList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="rilascioPassaporto"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoRilascioPassaporto"
	scope="request" />
<jsp:useBean id="nazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="comuniList_all" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="provinceList" class="org.aspcfs.utils.web.LookupList" scope="request" />


<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js"></script>
<script type="text/javascript" src="dwr/interface/DwrUtil.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/PraticaList.js"> </script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/LineaProduttiva.js"> </script>

<script>

$(document).ready(function() {

	
	$("#dialog").dialog({
        autoOpen: false,
        resizable: false,
        modal: true,
        title: 'Attenzione!',
        close: function () {
           // $('#opener').button('refresh');
            },
        buttons: {
            'Prosegui': function() {
                
                if (!$.trim($("#motivazioneModificaPopup").val())) {
                	$("#motivazioneModificaPopup").css('border', '1px solid red');
                	$("#motivazioneModificaPopup").css('margin-top', '0px');
                	$("#testo").css('color', 'red');
                	
                    return false;
                }else{
                	$(this).dialog('close');
                 //   alert( $("#motivazioneModificaPopup").val());
                    $("#motivazioneModifica").val($("#motivazioneModificaPopup").val());
               //     alert( $("#motivazioneModifica").val());
                        
                }
                
            	loadModalWindow(); //ATTENDERE PREGO     
                $("#addAnimale").submit();    
            },
            'Rinuncia': function() {
                $(this).dialog('close');
               return false;
            }
        }
    });



});

/*function CheckProvenienzaFuoriRegione() {
  	if (document.addAnimale.flagFuoriRegione != null && document.addAnimale.flagFuoriRegione.checked){
  		document.getElementById("utProvenienza").style.visibility="visible";
  		document.getElementById("flagSindacoFuoriRegione").disabled= true;
  		
  	} else {
  		document.getElementById("utProvenienza").style.visibility="hidden";
  		document.getElementById("flagSindacoFuoriRegione").disabled= false;

  	}
  }*/


var campo ;
function verificaInserimentoAnimale (campoIn)
{
	campo = campoIn ;
	if(campo.value.length==15)
		DwrUtil.verificaInserimentoAnimale(campo.value,<%=animale.getIdUtenteInserimento()%>,verificaInserimentoAnimaleCallBack);
}

function verificaInserimentoAnimaleCallBackOLD(value)
{
	
	if (value.idEsito=='2' )
	{
		
		alert(value.descrizione);
		campo.value="";
	}
	if ( value.idEsito=='4'){
		
		if ( document.forms[0].ruolo.value != <%=ApplicationProperties.getProperty("ID_RUOLO_HD1")%> 
		&& document.forms[0].ruolo.value != <%=ApplicationProperties.getProperty("ID_RUOLO_HD2")%>)
		{
			
			alert( value.descrizione);
			campo.value="";
		}
	}
	if (value.idEsito=='3' )
	{
		if ( document.forms[0].ruolo.value == '24')
		{
			
			alert( value.descrizione);
			campo.value="";
		}
		
	}
	
}

function verificaInserimentoAnimaleCallBack(value)
{

	alert(value.descrizione);
	
}

function checkDataSterilizzazioneInPratica(){
	var ok = false;
	//alert($('#flagCattura').is(':checked'));
	DwrUtil.verificaSterilizzazioneInPratica(document.forms[0].dataSterilizzazione.value, <%=praticacontributi.getId()%>, {
		callback:function(data) {
		ok= data;
		},
		timeout:8000,
		async:false

		});

	return ok;
}


function checkForm(form) {
	
	//verificaContributo();
	
  	//checkMorsicatore();
   

formTest = true;
message = "";


    lanciaControlloDate();
    
    
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
    //----CAMPI OBBLIGATORI--------------
    
  
    //controllo sul proprietario se non è stato selezionato
   /* if (form.idProprietario.value == "-1") { 
        message += label("check.ubicazione","- Proprietario is required\r\n");
        formTest = false;
    
	//controllo sul detentore se non è stato selezionato 
     if (form.idSpecie.value == '1' &&  form.idDetentore.value == "-1") { 
        message += label("check.detentore","- Detentore is required\r\n");
        formTest = false;
      }
    }*/
	//controllo sulla taglia
	 if (form.idSpecie.value == '1' &&  form.idTaglia.value == '-1') { 
	     message += label("taglia.required", "- Selezionare un valore per la taglia\r\n");
	     formTest = false;
	  }
    //if (form.idAslRiferimento.value == '-1' && form.flagSindacoFuoriRegione.checked==false) { 
	//     message += label("taglia.required", "- Selezionare un valore per l'asl\r\n");
	//     formTest = false;
	//  }
    
	 //controllo sulla razza
	if (form.idRazza.value == '-1') { 
	     message += label("razza.required", "-Selezionare un valore per la razza\r\n");
	     formTest = false;
	     
	  }

	  //controllo sul mantello
	  if (form.idTipoMantello.value == '-1') { 
	     message += label("mantello.required", "- Selezionare un valore per il mantello\r\n");
	     formTest = false;
	  }

	    //controllo sul mc
	 if (form.microchip.value == "") {
	      message += label("serial.number.required", "- Selezionare un valore per il microchip\r\n");
	      formTest = false;
	  }
	  else{
		    //controllo sulla lunghezza del microchip    
	        if( !( (form.microchip.value.length == 15) && ( /^([0-9]+)$/.test( form.microchip.value )) ) )
	        {
	       	  message += label("", "- La lunghezza del microchip deve essere di 15 caratteri numerici\r\n");
	          formTest = false;
	        }
		
	  }


	 //controllo sul mc
	
    if (form.tatuaggio.value != "") {
    	
		 if (form.idSpecie.value=='1' && form.dataTatuaggio != null &&  form.dataTatuaggio.value == "") {
	      message += label("", "- Data Inserimento secondo mc richiesta\r\n");
	    
	      formTest = false;
		 }  
		
	}
 
	if (form.idSpecie.value == '1' && form.dataTatuaggio != null &&   form.dataTatuaggio.value != "") {
	 if (form.tatuaggio.value == "") {
		      message += label("", "- Tatuaggio Richiesto\r\n");
		      formTest = false;
		  }
	}

	//controllo sul check  regione
	//  if (form.idSpecie.value == '1' &&  form.flagFuoriRegione != null &&  form.flagFuoriRegione.checked){
	//   	if ( form.idRegioneProvenienza.value== -1){
	//   		message += label("", "- Selezionare la Regione di Provenienza\r\n");
	//   		formTest = false;
	//   	}
	// }
			 

	 if (form.numeroPassaporto.value!="" && form.numeroPassaporto.value.length<13 && <%=(animale.getIdPartitaCircuitoCommerciale() < 0)%>){

		   	message += label("", "- Sono richieste 13 cifre o caratteri per il passaporto.\r\n");
  			formTest = false;
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

	//--------FINE CAMPI OBBLIGATORI
	 
	// Controllo data sterilizzazione (è possibile modificarla ma deve restare nel range delle date 
	//della pratica associata se esistente)
	
	if (<%=praticacontributi != null
					&& praticacontributi.getId() > 0%>){
		if (!checkDataSterilizzazioneInPratica()){
		message += label("", "- Data Sterilizzazione incongruente con la pratica associata.\r\n");
 		formTest = false;
		}
	}
	
	 
    if (formTest == false) {
      alert(label("check.form", "I dati non possono essera salvati, per favore verifica le seguenti informaioni:\r\n\r\n") + message);
      return false;
    }
    else
    {    $('#dialog').dialog('open');
      	//return true;
    }
	
  }


var array_pratiche = new Array();

function visualizzaPratiche()
{
	if (document.addAnimale.flagSterilizzazione.checked){
		
		 document.getElementById('divster').style.display='';
		 if (document.addAnimale.flagSterilizzazione.checked){
			 document.getElementById('pratica_contributo').style.display='';
		 }

			
			
	}

  
}


function valorizzaLista(listaPratiche)
{

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
	 }
	 catch(e){
		 select.add(NewOpttmp); 
	 }	
  while(i < listaPratiche.length){
		
			 array_pratiche[listaPratiche[i].id]=listaPratiche[i];

			 var NewOpt = document.createElement('option');
			 NewOpt.value = listaPratiche[i].id;
			 alert(NewOpt.value);
		 	 NewOpt.text =  "Decreto numero "+listaPratiche[i].numero_decreto_pratica + " del "+ listaPratiche[i].data_decreto_stringa +" - N° cani padronali restanti "+ listaPratiche[i].cani_restanti_padronali +" - N° cani catturati restanti "+listaPratiche[i].cani_restanti_catturati + " - "+ listaPratiche[i].elenco_comuni ;
			
			if(listaPratiche[i].elencoCanili.length != 0 ){

				for (var k=0; k<listaPratiche[i].elencoCanili.length; k++) {
				 	NewOpt.text =  "Decreto numero "+listaPratiche[i].numero_decreto_pratica + " del "+ listaPratiche[i].data_decreto_stringa +" - N° cani padronali restanti "+ listaPratiche[i].cani_restanti_padronali +" - N° cani catturati restanti "+listaPratiche[i].cani_restanti_catturati + " - "+ listaPratiche[i].elencoCanili[k];	
				}
			}
			
			
			//controllo dell'id selezionato nel caso di salva e clona
			 if(<%=animale.getIdPraticaContributi()%> == NewOpt.value){
			 	NewOpt.selected = true;
			 }
			 
			  //Aggiungo l'elemento option
			    try
			    {
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
	if (document.addAnimale.flagCatturato.checked){
		LineaProduttiva.verificaLineaProduttiva(document.addAnimale.idProprietario.value, document.addAnimale.idDetentore.value, 2, 4, check );
	}
	
}


function check(rit){
	if (rit == false){
		$('#flagCatturato').attr('checked', false);
		alert("Selezionare un proprietario sindaco e un detentore canile per poter aggiungere una registrazione di cattura");
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

	//Controllare asl ma con anagrafica centralizzata non sappiamo come fare
/**	if (document.addAnimale.idAslRiferimento.value == "-1"){
		alert ("Selezionare Asl di riferimento");
		return;
	}*/
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


function abilitaEsitiEhi()
{
		if(document.getElementById('flagControlloIdentita')!=null)
		{
			if(document.getElementById('flagControlloIdentita').checked)
			{
				document.getElementById('esitoEhradd1').style.visibility="visible";
				//document.getElementById('esitoEhradd2').style.visibility="visible";
				
			}
			else
			{
				document.getElementById('esitoEhradd1').style.visibility="hidden";
				document.getElementById('dataControlloIdentita').value="";
				document.addAnimale.esitoControlloEhrlichiosi.value="-1";
				document.getElementById('esitoEhradd2').style.visibility="hidden";
			}
		}
}


function abilitaEsitiRik()
{
	
		if(document.getElementById('flagControlloRickettsiosi')!=null)
		{
			if(document.getElementById('flagControlloRickettsiosi').checked )
			{
				document.getElementById('esitoRickadd1').style.visibility="visible";
			}
			else
			{
				document.getElementById('esitoRickadd1').style.visibility="hidden";
				document.getElementById('dataControlloRickettsiosi').value="";
				//document.addAnimale.esitoControlloRickettsiosi.value="-1";
			}
		}
}

</script>

<script language="JavaScript">

function doCheck(form) {
    if (form.dosubmit.value == "false") {   
        alert('false');  
      return true;
    } else {
    	return(checkForm(form));
    }
   }


/*  function CheckProvenienzaFuoriRegione() {
  	if (document.addAnimale.flagFuoriRegione != null && document.addAnimale.flagFuoriRegione.checked){
  		document.getElementById("utProvenienza").style.visibility="visible";
  		document.getElementById("flagSindacoFuoriRegione").disabled= true;
  		
  	} else {
  		document.getElementById("utProvenienza").style.visibility="hidden";
  		document.getElementById("flagSindacoFuoriRegione").disabled= false;

  	}
  }*/

  function abilitaSelezionePropDet(idAsl) {
		if(document.getElementById('flagSindacoFuoriRegione') != null && document.getElementById('flagSindacoFuoriRegione').checked){
			document.getElementById("origine_da").style.display = "none";
		}else{
			document.getElementById("origine_da").style.removeProperty("display");	
		}
	}
  
  /*function abilitaSelezionePropDet(idAsl)
  {
		if(document.getElementById('flagSindacoFuoriRegione') != null && document.getElementById('flagSindacoFuoriRegione').checked)
		{
			

			document.getElementById("aslRif").style.display = "none";

  			//document.getElementById ("sterilizzazione").style.display="none";
  	  	

			document.getElementById("provenienza").disabled = true;
			

			}
			else
			{
				
				document.getElementById("aslRif").style.display = "";
				
				document.getElementById("provenienza").disabled = false;
				
				
			}
		
  }*/

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
				//alert("ATTENZIONE! Il caso di mancata tracciabilità per 'Informazioni mancanti sull'origine animale' è da considerarsi una informazione non conforme.");
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

<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td><a href="AnimaleAction.do?command=Search"><dhv:label
						name="">Ricerca animali</dhv:label></a> > <a
				href="AnimaleAction.do?command=Details&animaleId=<%=animale.getIdAnimale()%>&idSpecie=<%=animale.getIdSpecie()%>"><dhv:label
						name="">Dettagli animale</dhv:label></a> > <dhv:label name="">Modifica</dhv:label>
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<body onLoad="javascript: visualizzaPratiche();">
	<form name="addAnimale" id="addAnimale"
		action="AnimaleAction.do?command=Update&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>"
		method="post">
		<input type="hidden" name="ruolo" value="<%=User.getRoleId()%>">
		<input type="hidden" value="<%=utenteInserimento.getRoleId()%>"  name="ruoloUtenteInserimento" id="ruoloUtenteInserimento"/>
		<%
			if (User.getRoleId() == 24) {
		%>
		<input type="hidden" name="idTipologiaSoggettoSterilizzante" value="2"
			id="idTipologiaSoggettoSterilizzante" />
		<!-- LLPP -->
		<input typ="hidden" name="idSoggettoSterilizzante"
			id="idSoggettoSterilizzante" value="<%=User.getUserId()%>" />
		<!-- ID VETERINARIO -->
		<%
			} else {
		%>
		<input type="hidden" name="idTipologiaSoggettoSterilizzante" value="1"
			id="idTipologiaSoggettoSterilizzante" />
		<!-- ASL -->
		<input type="hidden" name="idSoggettoSterilizzante"
			id="idSoggettoSterilizzante" value="<%=User.getSiteId()%>" />
		<!-- ID ASL -->
		<%
			}
		%>

		<table class="">
			<input type="button"
				value="<dhv:label name="global.button.save">Save</dhv:label>"
				onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='';if(doCheck(this.form)){this.form.submit()};" />
			<input type="button"
				value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
				onClick="window.location.href='AnimaleAction.do?command=Details&animaleId=<%=animale.getIdAnimale()%>&idSpecie=<%=animale.getIdSpecie()%>';this.form.dosubmit.value='false';" />


			<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">
				<tr>
					<th colspan="2"><strong><dhv:label
								name="accounts.accountasset_include.SpecificInformation">Specific Information</dhv:label></strong>
					</th>
				</tr>

				<tr id="aslRif">
					<td class="formLabel" nowrap><dhv:label name="">Asl di Riferimento</dhv:label>
					</td>
					<td><%=AslList.getSelectedValue(animale.getIdAslRiferimento())%>
						<input type="hidden" size="30" id="idAslRiferimento"
						name="idAslRiferimento" value="<%=animale.getIdAslRiferimento()%>">



					</td>
				</tr>


				<tr>
					<td class="formLabel" nowrap><dhv:label name="">Specie animale</dhv:label>
					</td>
					<td><%=specieList.getSelectedValue(animale.getIdSpecie())%> <input
						type="hidden" name="idSpecie" id="idSpecie"
						value="<%=animale.getIdSpecie()%>" /> <input type="hidden"
						name="idCane" id="idCane" value="<%=Cane.getIdCane()%>" /> <input
						type="hidden" name="idGatto" id="idGatto"
						value="<%=Gatto.getIdGatto()%>" /> <input type="hidden"
						name="idAnimale" id="idAnimale"
						value="<%=animale.getIdAnimale()%>"></td>
				</tr>
				
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


					<td><input readonly type="text" name="dataRegistrazione"
						size="10"
						value="<%=toDateString(animale.getDataRegistrazione())%>"
						nomecampo="registrazione" tipocontrollo="T2,T6,T7"
						labelcampo="Data Registrazione" />&nbsp; <!--  <a href="#"
				onClick="cal19.select(document.forms[0].dataRegistrazione,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a><font color="red">*--> Data riportata dal
						documento.</font></td>
				</tr>
				<% int idTipologiaLineaProdProprietario = -1; 
				   if(animale != null && animale.getProprietario() != null && animale.getProprietario().getIdOperatore() > 0)
				      idTipologiaLineaProdProprietario = ((LineaProduttiva) (((Stabilimento) animale.getProprietario().getListaStabilimenti().get(0)).getListaLineeProduttive().get(0))).getIdRelazioneAttivita(); 
				%>
				<input type="hidden" id="tipologia_proprietario" value="<%=idTipologiaLineaProdProprietario%>"/>
				

				<tr class="containerBody" id="proprietario">
					<td class="formLabel" nowrap><dhv:label name="">Proprietario</dhv:label>
					</td>
					<td>
						<table cellspacing="0" border="0" width="100%">
							<dhv:evaluate
								if="<%=(animale != null && animale.getProprietario() != null && animale
						.getProprietario().getIdOperatore() > 0)%>">

								<tr>

									<td><%=toHtml(animale.getProprietario().getRagioneSociale())%></td>
								</tr>
								<input type="hidden" name="idProprietario" id="idProprietario"
									value="<%=(animale.getProprietario() != null)
						? ((LineaProduttiva) (((Stabilimento) animale
								.getProprietario().getListaStabilimenti()
								.get(0)).getListaLineeProduttive().get(0)))
								.getId() : ""%>">

							</dhv:evaluate>

							<dhv:permission name="anagrafe_canina_operazioni_hd-add">
								<tr>
									<td><a
										href="javascript:popUp('OperatoreAction.do?command=SearchForm&tipologiaSoggetto=1&popup=true&idLineaProduttiva1=1');">Ricerca</a>
									</td>
								</tr>
							</dhv:permission>
						</table> <dhv:evaluate
							if="<%=(animale.getProprietario() == null || (animale
						.getProprietario() != null && animale.getProprietario()
						.getIdOperatore() <= 0))%>">
							<input type="text" name="idProprietario" id="idProprietario"
								value="-1">
						</dhv:evaluate>
					</td>

				</tr>

				<%
					int idDetentore = -1;
					String nomeDetentore = "";
					nomeDetentore = toHtml(animale.getDetentore()
							.getRagioneSociale());
					idDetentore = animale.getDetentore().getIdOperatore();
				%>


				<!-- dhv:evaluate if='<%=(animale.getIdSpecie() == 1)%>' -->
				<tr class="containerBody" id="detentore">
					<td class="formLabel" nowrap><dhv:label name="">Detentore</dhv:label>
					</td>
					<td>

						<table cellspacing="0" border="0" width="100%">
							<!-- dhv:evaluate if="<%=(Cane != null && Cane.getDetentore() != null)%>" -->

							<tr>

								<td><%=nomeDetentore%></td>
							</tr>

							<input type="hidden" name="idDetentore" id="idDetentore"
								value="<%=(animale.getDetentore() != null && animale.getDetentore()
					.getListaStabilimenti().size() > 0)
					? ((LineaProduttiva) (((Stabilimento) animale.getDetentore()
							.getListaStabilimenti().get(0))
							.getListaLineeProduttive().get(0))).getId() : ""%>">


							<!-- /dhv:evaluate -->

							<dhv:permission name="anagrafe_canina_operazioni_hd-add">
								<tr>
									<td><a
										href="javascript:popUp('OperatoreAction.do?command=SearchForm&tipologiaSoggetto=2&popup=true&idLineaProduttiva1=5');">Ricerca</a>
									</td>
								</tr>
							</dhv:permission>
						</table>

<dhv:permission name="anagrafe_canina_operazioni_hd-add">
<input type="checkbox" name="aggiorna_dati_iscrizione" id ="aggiorna_dati_iscrizione">Aggiorna dati prima iscrizione</input>

</dhv:permission>					</td>

				</tr>
				<!-- /dhv:evaluate -->


				<tr class="containerBody">
					<!--  RAZZA CANE GATTO -->
					<dhv:evaluate if='<%=(animale.getIdSpecie() != 3)%>'>
						<td class="formLabel"><dhv:label name="">Razza</dhv:label></td>
						<td><%=razzaList.getHtmlSelect("idRazza",
						animale.getIdRazza())%> <%=showAttribute(request, "idRazza")%> <font
							color="red">*</font></td>
					</dhv:evaluate>
					<dhv:evaluate if='<%=(animale.getIdSpecie() == 3)%>'>
						<!--  RAZZA FURETTO -->
						<input type="hidden" name="idRazza" id="idRazza" value="0"></input>
					</dhv:evaluate>

				</tr>
				<tr>
					<td class="formLabel"><dhv:label name="">Sesso</dhv:label></td>
					<td><input type="radio" name="sesso" value="M" id="sesso"
						<%=(!"F".equals(animale.getSesso())) ? " checked" : ""%>>
						<dhv:label name="cani.sesso.maschio">M</dhv:label> <input
						type="radio" name="sesso" value="F" id="sesso"
						<%=("F".equals(animale.getSesso())) ? " checked" : ""%>> <dhv:label
							name="cani.sesso.femmina">F</dhv:label></td>
				</tr>

				<dhv:evaluate if='<%=(animale.getIdSpecie() == 1)%>'>
					<tr class="containerBody">
						<td class="formLabel"><dhv:label name="">Taglia</dhv:label></td>
						<td><%=tagliaList.getHtmlSelect("idTaglia",
						animale.getIdTaglia())%> <%=showAttribute(request, "idTaglia")%> <font
							color="red">*</font></td>
					</tr>

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
						<%=animale.isFlagDataNascitaPresunta() ? "CHECKED" : ""%>
						readonly="readonly" value="1" /></td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Mantello</dhv:label></td>
					<td><%=mantelloList.getHtmlSelect("idTipoMantello",
					animale.getIdTipoMantello())%> <%=showAttribute(request, "idTipoMantello")%>
						<font color="red">*</font></td>

				</tr>


				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Nome</dhv:label></td>
					<td><input type="text" size="30" id="nome" name="nome"
						maxlength="41" value="<%=toHtmlValue(animale.getNome())%>">
						<%=showAttribute(request, "nome")%></td>
				</tr>

				<input type="hidden" name="microchipOriginale"
					id="microchipOriginale" value="<%=animale.getMicrochip()%>"></input>
				<input type="hidden" name="dataMicrochipOriginale"
					id="dataMicrochipOriginale"
					value="<%=animale.getDataMicrochip()%>"></input>
				<input type="hidden" name="idVeterinarioMicrochipOriginale"
					id="idVeterinarioMicrochipOriginale"
					value="<%=animale.getIdVeterinarioMicrochip()%>"></input>

				<!--  HELPDESK PUO' MODIFICARE MICROCHIP E DATA MICROCHIP SE SONO NULLI -->
				<%
					/* if (((User.getRoleId() == new Integer(
							ApplicationProperties.getProperty("ID_RUOLO_HD1")) || User
							.getRoleId() == new Integer(
							ApplicationProperties.getProperty("ID_RUOLO_HD2"))) && (animale
							.getMicrochip() == null || animale.getMicrochip()
							.equals("")))) { */
				%>
	<dhv:permission name="anagrafe_canina_operazioni_hd-add">
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Microchip</dhv:label></td>
					<td><input type="text" size="16" id="microchip"
						name="microchip" maxlength="15"
						value="<%=toHtmlValue(animale.getMicrochip())%>"
						onchange="verificaInserimentoAnimale(document.forms[0].microchip)" />
						<%=showError(request, "ErroreMicrochip")%>&nbsp;&nbsp;</td>
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
							align="absmiddle"></a></td>
				</tr>
</dhv:permission>
				<!--  NON HELPDESK NON PUO' MODIFICARE -->
<dhv:permission name="anagrafe_canina_operazioni_hd-add" none="true">
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Microchip</dhv:label></td>
					<td><input readonly type="text" size="16" id="microchip"
						name="microchip" maxlength="15"
						value="<%=toHtmlValue(animale.getMicrochip())%>"
						onchange="verificaInserimentoAnimale(document.forms[0].microchip)" />
						<%=showError(request, "ErroreMicrochip")%>&nbsp;&nbsp;</td>
				</tr>

				<tr class="containerBody" style="">
					<td class="formLabel"><dhv:label name="">Data chippatura</dhv:label>
					</td>
					<td><input readonly type="text" name="dataMicrochip" size="10"
						value="<%=toDateasString(animale.getDataMicrochip())%>"
						nomecampo="chippatura" tipocontrollo="T2,T17"
						labelcampo="Data Chippatura" /> &nbsp;</td>
				</tr>
</dhv:permission>

				<!-- ENDIF COMMENTATO 31/05	  -->

				<dhv:evaluate
					if='<%=(User.getRoleId() == new Integer(
						ApplicationProperties.getProperty("ID_RUOLO_HD1")) || User
						.getRoleId() == new Integer(ApplicationProperties
						.getProperty("ID_RUOLO_HD2")))%>'>
					<tr class="containerBody">
						<td class="formLabel"><dhv:label name="">Veterinario chippatura</dhv:label>
						</td>
						<td><%=veterinariList.getHtmlSelect(
						"idVeterinarioMicrochip",
						animale.getIdVeterinarioMicrochip())%></td>
					</tr>
				</dhv:evaluate>
				<dhv:evaluate
					if='<%=(User.getRoleId() != new Integer(
						ApplicationProperties.getProperty("ID_RUOLO_HD1")) && User
						.getRoleId() != new Integer(ApplicationProperties
						.getProperty("ID_RUOLO_HD2")))%>'>
					<tr class="containerBody">
						<td class="formLabel"><dhv:label name="">Veterinario chippatura</dhv:label>
						</td>
						<td><%=veterinariList.getSelectedValue(animale
						.getIdVeterinarioMicrochip())%></td>
					</tr>
				</dhv:evaluate>


<dhv:evaluate if="<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) && User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA")))%>">
				<dhv:evaluate if='false'>
					<tr class="containerBody">
						<td class="formLabel">Morsicatore</td>
						<td><input type="checkbox" id="flagMorsicatore"
							onclick="checkMorsicatore()" name="flagMorsicatore"
							disabled="disabled" <%=Cane.getFlagMorsicatore() ? "checked" : "" %> />
							 <input type="hidden" value="<%=Cane.getFlagMorsicatore() %>" name="flagMorsicatore" id="flagMorsicatore"/>

							<div id="mors">
								<br> Date: <input readonly type="text" id="dataMorso"
									name="dataMorso" size="10"
									value="<%=(Cane.getDataMorso() != null) ? toDateasString(Cane
						.getDataMorso()) : ""%>"
									nomecampo="dataMorso" tipocontrollo="T10,T9"
									labelcampo="Data Morsicatore N.1" /> &nbsp;
								<!--<a href="#"
					 onClick="cal19.select(document.forms[0].dataMorso,'anchor19','dd/MM/yyyy'); return false;"
					NAME="anchor19" ID="anchor19"> <img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmiddle"></a>-->
							</div></td>
					</tr>
				</dhv:evaluate>

				<dhv:evaluate if='false'>
					<tr class="containerBody">
						<td class="formLabel"><dhv:label name="">Aggressivo</dhv:label>
						</td>
						<td><input type="checkbox" name="flagAggressivo"
							<%if (Cane.isFlagAggressivo()) {%> checked="checked" <%}%> /></td>
					</tr>
				</dhv:evaluate>
</dhv:evaluate>
				<input type="hidden" name="dataSterilizzazioneOriginale"
					id="dataSterilizzazioneOriginale"
					value="<%=animale.getDataSterilizzazione()%>"></input>

				<tr class="containerBody" id="sterilizzazione">
					<td class="formLabel"><dhv:label name="">Data Sterilizzazione</dhv:label>
					</td>
					<td>
						<table border="0">
							<tr>
								<td><input type="checkbox" name="flagSterilizzazione"
									<%=(animale.isFlagSterilizzazione()) ? "checked" : ""%>
									disabled="disabled" /></td>
							</tr>
							<tr>
								<td>
									<div id="divster" style="display: none">
										<input readonly type="text" name="dataSterilizzazione"
											size="10"
											value="<%=toDateasString(animale.getDataSterilizzazione())%>"
											nomecampo="dataSterilizzazione"
											labelcampo="Data Sterilizzazione" />

										<dhv:evaluate
											if='<%=((User.getRoleId() == new Integer(
						ApplicationProperties.getProperty("ID_RUOLO_HD1")) || User
						.getRoleId() == new Integer(ApplicationProperties
						.getProperty("ID_RUOLO_HD2"))) && !DwrUtil
						.checkContributo(animale.getMicrochip()))%>'>
											<a href="#"
												onClick="cal19.select(document.forms[0].dataSterilizzazione,'anchor19','dd/MM/yyyy'); return false;"
												NAME="anchor19" ID="anchor19"> <img
												src="images/icons/stock_form-date-field-16.gif" border="0"
												align="absmiddle"></a>
								</td>
								</dhv:evaluate>
<dhv:evaluate if="<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) && User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA")))%>">
								&nbsp;Contributo Regionale
								<input type="checkbox" name="flagContributoRegionale"
									id="flagContributoRegionale"
									<%if (animale.isFlagContributoRegionale()) {%>
									checked="checked" <%}%> onclick="visualizzaPratiche();"
									disabled="disabled" />
								<%=showAttribute(request, "errContr")%>&nbsp;
								<br />
								<br />


								<div id="pratica_contributo" name="pratica_contributo"
									style="display: none;">
									<dhv:evaluate
										if='<%=(praticacontributi.getNumeroDecretoPratica() > 0)%>'>
						Pratica decreto nr: <%=praticacontributi.getNumeroDecretoPratica()%>
									</dhv:evaluate>
								</div>

								<%
									int id = -1;
									if (praticacontributi != null && praticacontributi.getId() > -1) {
										id = praticacontributi.getId();
									}
								%>
								<input type="hidden" id="idPraticaContributi"
									name="idPraticaContributi" value="<%=id%>" />
								<input type="hidden" id="stato" name="stato"
									value="<%=animale.getStato()%>">
								<input type="hidden" id="flagSmarrimento" name="flagSmarrimento"
									value="<%=animale.isFlagSmarrimento()%>">
								<input type="hidden" id="flagDecesso" name="flagDecesso"
									value="<%=animale.isFlagDecesso()%>">
								<%=showError(request, "praticaError")%>

								</div>
								<input type="hidden"
									value="<%=animale.getFlagContributoRegionale()%>"
									name="flagContributoRegionale" id="FlagContributoRegionale" />
								</td>
							</tr>
</dhv:evaluate>
						</table>


					</td>
				</tr>
				
<%-- <dhv:permission name="anagrafe_canina_operazioni_hd-add">
				<tr class="containerBody">
					<td class="formLabel"><dhv:label
							name="anagrafe_animali_tatuaggio">Tatuaggio/2°Microchip</dhv:label>
					</td>
					<td><input type="text" size="20" name="tatuaggio"
						maxlength="15" value="<%=toHtmlValue(animale.getTatuaggio())%>"
						onchange="verificaInserimentoAnimale(document.forms[0].tatuaggio)">
						<%=showError(request, "ErroreTatuaggio")%>&nbsp;&nbsp;</td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Data rilascio secondo Microchip</dhv:label>
					</td>
					<td><input readonly type="text" name="dataTatuaggio"
						size="10" value="<%=toDateasString(animale.getDataTatuaggio())%>"
						nomecampo="dataTatuaggio" tipocontrollo="T10,T11,T9"
						labelcampo="Data Secondo MC" id ="dataTatuaggio" />&nbsp; <a href="#"
					  onClick="cal19.select(document.forms[0].dataTatuaggio,'anchor19','dd/MM/yyyy'); return false;" 
					NAME="anchor19" ID="anchor19"> <img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmiddle"> </a>
					</td>

				</tr>
</dhv:permission> --%>

<%-- <dhv:permission name="anagrafe_canina_operazioni_hd-add" none="true"> --%>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label
							name="anagrafe_animali_tatuaggio">Tatuaggio/2°Microchip</dhv:label>
					</td>
					<td><input readonly type="text" size="20" name="tatuaggio"
						maxlength="15" value="<%=toHtmlValue(animale.getTatuaggio())%>"
						onchange="verificaInserimentoAnimale(document.forms[0].tatuaggio)">
						<%=showError(request, "ErroreTatuaggio")%>&nbsp;&nbsp;</td>
				</tr>
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Data rilascio secondo Microchip</dhv:label>
					</td>
					<td><input readonly type="text" name="dataSecondoMicrochip"
						size="10" value="<%=toDateasString(animale.getDataTatuaggio())%>"
						nomecampo="dataSecondoMicrochip" tipocontrollo="T10,T11,T9"
						labelcampo="Data Secondo MC" />&nbsp; <!-- <a href="#"
					  onClick="cal19.select(document.forms[0].dataSecondoMicrochip,'anchor19','dd/MM/yyyy'); return false;" 
					NAME="anchor19" ID="anchor19"> <img
					src="images/icons/stock_form-date-field-16.gif" border="0"
					align="absmiddle"> </a>--></td>

				</tr>
<%-- </dhv:permission> --%>


				<!--  HELPDESK PUO' MODIFICARE PASSAPORTO E DATA PASSAPORTO -->
				<dhv:evaluate
					if='<%=((User.getRoleId() == new Integer(
						ApplicationProperties.getProperty("ID_RUOLO_HD1")) || User
						.getRoleId() == new Integer(ApplicationProperties
						.getProperty("ID_RUOLO_HD2")))
						&& animale.getNumeroPassaporto() != null && !animale
						.getNumeroPassaporto().equals(""))%>'>
					<tr class="containerBody">
						<td class="formLabel"><dhv:label name="">Passaporto</dhv:label>
						</td>
						<td><input type="text" name="numeroPassaporto" size="15"
							maxlength="13"
							value="<%=toHtmlValue(animale.getNumeroPassaporto())%>" />
							<%=showAttribute(request, "numeroPassaporto")%></td>
					</tr>
					<tr class="containerBody">
						<td class="formLabel"><dhv:label name="">Data rilascio/scadenza passaporto</dhv:label>
						</td>
						<td>
							<%-- if (update==false){ --%> <dhv:evaluate
								if='<%=(animale.getIdPartitaCircuitoCommerciale() > 0)%>'>
								<!-- PARTITA COMMERCIALE: NO CHECK -->
								<input readonly type="text" name="dataRilascioPassaporto"
									size="10"
									value="<%=toDateasString(animale
							.getDataRilascioPassaporto())%>"
									nomecampo="dataRilascioPassaporto" tipocontrollo="T10,T9"
									labelcampo="Data rilascio passaporto" />&nbsp; 
				</dhv:evaluate> <dhv:evaluate
								if='<%=(animale.getIdPartitaCircuitoCommerciale() <= 0)%>'>
								<input readonly type="text" name="dataRilascioPassaporto"
									size="10"
									value="<%=toDateasString(animale
							.getDataRilascioPassaporto())%>"
									nomecampo="dataRilascioPassaporto" tipocontrollo="T10,T9"
									labelcampo="Data rilascio passaporto" />&nbsp;
				</dhv:evaluate> <a href="#"
							onClick="cal19.select2(document.forms[0].dataRilascioPassaporto, document.forms[0].dataScadenzaPassaporto, 'anchor19','dd/MM/yyyy'); return false;"
							NAME="anchor19" ID="anchor19"> <img
								src="images/icons/stock_form-date-field-16.gif" border="0"
								align="absmiddle"></a> <%=showError(request, "dataRilascioPassaporto")%>&nbsp;&nbsp;
							-- <input type="text" readonly name="dataScadenzaPassaporto"
							id="dataScadenzaPassaporto" size="10"
							value="<%=toDateasString(rilascioPassaporto
						.getDataScadenzaPassaporto())%>"
							nomecampo="dataScadenzaPassaporto" tipocontrollo=""
							labelcampo="Data scadenza passaporto" />&nbsp; <a href="#"
							onClick="cal19.select(document.forms[0].dataScadenzaPassaporto, 'anchor19','dd/MM/yyyy'); return false;"
							NAME="anchor19" ID="anchor19"> <img
								src="images/icons/stock_form-date-field-16.gif" border="0"
								align="absmiddle"></a>&nbsp;&nbsp;
						</td>


						<%--	<%= showAttribute(request, "expirationDateError") %>--%>
						</td>
					</tr>
				</dhv:evaluate>

				<dhv:evaluate
					if='<%=((User.getRoleId() != new Integer(
						ApplicationProperties.getProperty("ID_RUOLO_HD1")) && User
						.getRoleId() != new Integer(ApplicationProperties
						.getProperty("ID_RUOLO_HD2")))
						|| animale.getNumeroPassaporto() == null || animale
						.getNumeroPassaporto().equals(""))%>'>

					<tr class="containerBody">
						<td class="formLabel"><dhv:label name="">Passaporto</dhv:label>
						</td>
						<td><input type="text" name="numeroPassaporto" size="15"
							maxlength="13"
							value="<%=toHtmlValue(animale.getNumeroPassaporto())%>"
							readonly="readonly" /> <%=showAttribute(request, "numeroPassaporto")%>
						</td>
					</tr>
					<tr class="containerBody">
						<td class="formLabel"><dhv:label name="">Data rilascio/scadenza passaporto</dhv:label>
						</td>
						<td>
							<%-- if (update==false){ --%> <input readonly type="text"
							name="dataRilascioPassaporto" size="10"
							value="<%=toDateasString(animale.getDataRilascioPassaporto())%>"
							nomecampo="dataRilascioPassaporto" tipocontrollo="T10,T9"
							labelcampo="Data rilascio passaporto" />&nbsp; 
							 <%=showError(request, "dataRilascioPassaporto")%>&nbsp;&nbsp;
							<%--	<%= showAttribute(request, "expirationDateError") %>--%> --
							<input type="text" readonly name="dataScadenzaPassaporto"
							id="dataScadenzaPassaporto" size="10"
							value="<%=toDateasString(rilascioPassaporto
						.getDataScadenzaPassaporto())%>"
							nomecampo="dataScadenzaPassaporto" tipocontrollo=""
							labelcampo="Data scadenza passaporto" />&nbsp;
						</td>

					</tr>
				</dhv:evaluate>

				<input type="hidden" name="numeroPassaportoOriginale"
					id="numeroPassaportoOriginale"
					value="<%=animale.getNumeroPassaporto()%>"></input>
				<input type="hidden" name="dataRilascioPassaportoOriginale"
					id="dataRilascioPassaportoOriginale"
					value="<%=animale.getDataRilascioPassaporto()%>"></input>

			</table>

			<br>
			
	  <table id="origine_table" cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
			<input type="hidden" id="abilita_origine" value="false"/>
			<tr>
				<th  colspan="2"><strong>Dati provenienza animale mai anagrafato</strong></th>
			</tr>
			<!-- -----------------------------------SEZIONE ORIGINE ANIMALE------------------------------------------------------- -->
			
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
						String lineaProduttivaFiltro = "";
						if (animale.getIdSpecie() == Furetto.idSpecie)
							lineaProduttivaFiltro = "idLineaProduttiva1=1;4";
						else
							lineaProduttivaFiltro = "idLineaProduttiva=1";
					
				
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
						</select>
						<font color="red">&nbsp;*</font> 
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
		

  			<tr>
				<th colspan="2"><strong>&nbsp;</strong></th>
			</tr>
<!-- ----------------------------------------------FINE ORIGINE ANIMALE-------------------------------------------------- -->
			
			
			
			
		</table>	
			
			
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
					<td><textarea name="note" rows="3" cols="50"><%=toString(animale.getNote())%></textarea>
						<%=showAttribute(request, "descriptionError")%></td>
				</tr>

				<!-- CAMPI NASCOSTI PER POTER CORRETTAMENTE GESTIRE LA LISTA MODIFICHE,IN MODO DA CONSERVARE I VALORI COSI CHE CHE
		CHECKMODIFICHE NON LI VEDE DIVERSI -->
				<input type="hidden" name="idRegione" id="idRegione"
					value="<%=animale.getIdRegione()%>" />
				<input type="hidden"
					name="idProprietarioUltimoTrasferimentoFRegione"
					id="idProprietarioUltimoTrasferimentoFRegione"
					value="<%=animale.getIdProprietarioUltimoTrasferimentoFRegione()%>" />
				<dhv:evaluate if="<%=animale.getIdSpecie() == Cane.idSpecie%>">
					<input type="hidden" name="idDetentoreUltimoTrasferimentoFRegione"
						id="idDetentoreUltimoTrasferimentoFRegione"
						value="<%=Cane.getIdDetentoreUltimoTrasferimentoFRegione()%>" />
				</dhv:evaluate>
				<dhv:evaluate if="<%=animale.getIdSpecie() == Gatto.idSpecie%>">
					<input type="hidden" name="idDetentoreUltimoTrasferimentoFRegione"
						id="idDetentoreUltimoTrasferimentoFRegione"
						value="<%=Gatto.getIdDetentoreUltimoTrasferimentoFRegione()%>" />
				</dhv:evaluate>
				<dhv:evaluate if="<%=animale.getIdSpecie() == Furetto.idSpecie%>">
					<input type="hidden" name="idDetentoreUltimoTrasferimentoFRegione"
						id="idDetentoreUltimoTrasferimentoFRegione"
						value="<%=Furetto.getIdDetentoreUltimoTrasferimentoFRegione()%>" />
				</dhv:evaluate>
			</table>
			<br>


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

<dhv:evaluate if="<%=(User.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")) && User.getRoleId() != new Integer(ApplicationProperties.getProperty("UNINA")))%>">

			<dhv:evaluate if='<%=(animale.getIdSpecie() == Cane.idSpecie)%>'>
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
									<td>Ord.sind. <input readonly type="text" size="10"
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
										labelcampo="Ordine Sindacale Leishmaniosi" /> &nbsp;
									</td>
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
										onclick="abilitaEsitiEhi();" disabled="disabled" /></td>


									<td id="esitoEhradd1">
										<table class="noborder">
											<tr>
												<td>Data <input readonly type="text"
													id="dataControlloEhrlichiosi"
													name="dataControlloEhrlichiosi" size="10"
													value="<%=toDateasString(Cane.getDataControlloEhrlichiosi())%>"
													nomecampo="dataControlloEhrlichiosi"
													tipocontrollo="T10,T11,T9" labelcampo="Data Ehrlichiosi" />&nbsp;
													<%--} --%></td>
												<td>Esito <%-- if (.getEsitoControlloEhrlichiosi() == 0 || animale.getEsitoControlloEhrlichiosi() == 1 || animale.getEsitoControlloEhrlichiosi() == -1) { %>
    		<%=esitoControlloList.getSelectedValue(animale.getEsitoControlloEhrlichiosi()) %>

    			
    	<%}else{ --%> <%=esitoControlloList.getSelectedValue(Cane
						.getEsitoControlloEhrlichiosi())%> <%-- }--%> <dhv:evaluate
														if="<%=(animale.getIdSpecie() == Cane.idSpecie)%>">
														<input type="hidden" name="esitoControlloEhrlichiosi"
															id="esitoControlloEhrlichiosi"
															value="<%=Cane.getEsitoControlloEhrlichiosi()%>" />
														<input type="hidden" name="esitoControlloRickettsiosi"
															id="esitoControlloRickettsiosi"
															value="<%=Cane.getEsitoControlloRickettsiosi()%>" />
													</dhv:evaluate></td>
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
										<%if (Cane.isFlagControlloRickettsiosi()) {%>
										checked="checked" <%}%> onclick="abilitaEsitiRik();"
										disabled="disabled" /></td>
									<td id="esitoRickadd1">
										<table class="noborder">
											<tr>
												<td>Data <input readonly type="text"
													id="dataControlloRickettsiosi"
													name="dataControlloRickettsiosi" size="10"
													value="<%=toDateasString(Cane.getDataControlloRickettsiosi())%>"
													nomecampo="dataControlloRickettsiosi"
													tipocontrollo="T10,T11,T9" labelcampo="Data Rickettiosi" />&nbsp;


												</td>
												<td id=>Esito <%=esitoControlloList.getSelectedValue(Cane
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

				<table cellpadding="4" cellspacing="0" border="0" width="100%"
					class="details">
					<tr>
						<th colspan="2"><strong>Dettagli Cattura</strong></th>
					</tr>

					<tr class="containerBody">
						<td class="formLabel"><dhv:label name="">Cattura</dhv:label></td>
						<td>
							<%-- if (!update) { --%> <input type="checkbox"
							onclick="javascript:checkProprietarioDetentore();"
							<%if (animale.isFlagCattura()) {%> checked="checked" <%}%>
							id="flagCattura" name="flagCattura" disabled="disabled" />
						</td>
						<input type="hidden" name="flagCattura" id="flagCattura"
							value="<%=animale.isFlagCattura()%>" />
					</tr>


					<tr class="containerBody">
						<td valign="top" class="formLabel"><dhv:label name="">Comune Cattura</dhv:label>
						</td>
						<td>
							<%-- if (!update) { --%> <%=comuniList.getSelectedValue(animale.getIdComuneCattura())%> <input
							type="hidden" name="idComuneCattura" id="idComuneCattura"
							value="<%=animale.getIdComuneCattura()%>" /> <%-- } else { %>
		<%= ( asset.getComuneCattura() != null ) ? asset.getComuneCattura() : "" %>
	<% } --%>
						</td>
					</tr>
					<tr class="containerBody">
						<td valign="top" class="formLabel"><dhv:label name="">Luogo Cattura</dhv:label>
						</td>
						<td><input type="text" name="luogoCattura"
							value="<%=toHtmlValue(Cane.getLuogoCattura())%>"
							readonly="readonly" /></td>
					</tr>
					<tr class="containerBody">
						<td class="formLabel"><dhv:label name="">Data Cattura</dhv:label>
						</td>
						<td>
							<%-- if (!update) { --%> <!--  <div id="divDataCatturaSi" style="display:none; vertical-align: middle">-->
							<div id="divDataCatturaSi" style="">
								<input readonly="readonly" type="text" name="dataCattura"
									size="10" value="<%=toDateasString(Cane.getDataCattura())%>"
									nomecampo="dataCattura" tipocontrollo=""
									labelcampo="Data Cattura" /> &nbsp;
							</div> <%-- } else{ %>
    	<%= ( asset.getDataCattura() != null ) ? asset.getDataCattura() : "" %>
    <% } --%>
						</td>
					</tr>
					<tr class="containerBody">
						<td valign="top" class="formLabel"><dhv:label name="">Verbale Cattura</dhv:label>
						</td>
						<td>
							<%-- if (!update) { --%> <textarea name="verbaleCattura" rows="3"
								cols="50" onmouseover="" disabled="disabled"></textarea> <%-- } else {%>
    <%= ( asset.getVerbaleCattura() != null ) ? asset.getVerbaleCattura() : "" %>	
   <%} --%>
						</td>
					</tr>

				</table>


</dhv:evaluate>


			<input type="hidden" name="dosubmit" value="true" />
			<input type="hidden" name="saveandclone" value="" />

			<br />
			<input type="button"
				value="<dhv:label name="global.button.save">Save</dhv:label>"
				onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='';if(doCheck(this.form)){this.form.submit()};" />

			<input type="hidden" name="doContinue" id="doContinue" value="">
			<div id="dialog" title="" style="display: none">
				<div style="">
				
				<%
				Timestamp now = new Timestamp(System.currentTimeMillis());
				long days = checkDifferenceDays(oldAnimale.getDataInserimento(), now ); %>
					<br> <span id="testo" style="color: black;">
					<dhv:evaluate if="<%=((days > 15 || animale.hasRegistrazioniModificaProprietario()) && (animale.getIdProprietario() != oldAnimale.getIdProprietario()) ) %>">
					Prima di proseguire leggi attentamente:
						<ul>
						<dhv:evaluate if="<%=(days > 15) %>" >
							<li style="color:red;">Attenzione, il cane è registrato in banca dati da un periodo superiore ai 15 giorni</li>
						</dhv:evaluate>
						<dhv:evaluate if="<%=(animale.hasRegistrazioniModificaProprietario()) %>" >
							<li style="color:red;">Attenzione, il cane ha registrazioni di modifica proprietario che potrebbero <br> ritrovarsi in uno stato errato effettuando la modifica del proprietario.</li>
						</dhv:evaluate>
						</ul>
						</dhv:evaluate>
					ATTENZIONE! Le modifiche apportate fuori dalle registrazioni sono la causa principale di incongruenza sui dati.<br>
					Se vuoi continuare lo stesso, indica una motivazione alla tua modifica.</span> <br> <br>
					<textarea rows="5" cols="50" id="motivazioneModificaPopup"
						name="motivazioneModificaPopup"></textarea>
					<br>
					<br>
				</div>
				
			</div>
			<input type="hidden" name="motivazioneModifica"
				id="motivazioneModifica" value="" />
			
			</form>



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
			    document.getElementById("ricerca_fr").style.removeProperty("display");
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

		 function abilitaNoteOnline(){
			 
				var myselect = document.getElementById("sitoWebAcquisto");
				if(myselect.options[myselect.selectedIndex].value=="Altro"){
					 document.getElementById("noteAcquistoOnline").style.removeProperty("display");
				 }else{
					 document.getElementById("noteAcquistoOnline").style.display="none";
					 document.getElementById("noteAcquistoOnline").value="";
				 }
			 }
		 
		 
		 function mostraOrigine(){ 
			 var oggi = new Date(); 
			 var from =$("#dataNascita").val().split("/");
			 var data_reg = new Date(from[2], from[1] - 1, from[0]);
			 var timeinmilisec = oggi.getTime()-data_reg.getTime();
			 var giorni=(Math.floor(timeinmilisec / (1000 * 60 * 60 * 24)));
			 var tipologia_proprietario="0";
			 if (document.getElementById("tipologia_proprietario")!=null)
				 tipologia_proprietario=document.getElementById("tipologia_proprietario").value;
			 var idSpecie=$("#idSpecie").val();
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
</script>