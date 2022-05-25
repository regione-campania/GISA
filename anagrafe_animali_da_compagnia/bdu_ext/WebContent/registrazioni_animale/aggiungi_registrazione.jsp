<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.io.*,java.util.*,org.aspcfs.utils.web.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.registrazioniAnimali.base.*, java.sql.* "%>
 
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="animale"
	class="org.aspcfs.modules.anagrafe_animali.base.Animale"
	scope="request" />
<jsp:useBean id="animalenewinfo"
	class="org.aspcfs.modules.anagrafe_animali.base.Animale"
	scope="request" />
<jsp:useBean id="oldAnimale"
	class="org.aspcfs.modules.anagrafe_animali.base.Animale"
	scope="request" />
<jsp:useBean id="Cane"
	class="org.aspcfs.modules.anagrafe_animali.base.Cane" scope="request" />
<jsp:useBean id="Gatto"
	class="org.aspcfs.modules.anagrafe_animali.base.Gatto" scope="request" />
<jsp:useBean id="registrazioniList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="comuniList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="provinceList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Evento"
	class="org.aspcfs.modules.registrazioniAnimali.base.Evento"
	scope="request" />
<jsp:useBean id="EventoRitrovamento"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoRitrovamento"
	scope="request" />
<jsp:useBean id="EventoSterilizzazione"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoSterilizzazione"
	scope="request" />
<jsp:useBean id="EventoReimmissione"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoReimmissione"
	scope="request" />
	
	<jsp:useBean id="EventoRitrovamentoNonDenunciato"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoRitrovamentoNonDenunciato"
	scope="request" />
	
<jsp:useBean id="EventoAdozioneDaCanile"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneDaCanile"
	scope="request" />
<jsp:useBean id="EventoRestituzioneACanile"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoRestituzioneACanile"
	scope="request" />
<jsp:useBean id="EventoAdozioneDaColonia"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneDaColonia"
	scope="request" />
<jsp:useBean id="EventoTrasferimento"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimento"
	scope="request" />
	<jsp:useBean id="EventoTrasferimentoSindaco"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoSindaco"
	scope="request" />
<jsp:useBean id="EventoTrasferimentoFuoriRegioneSoloProprietario"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoFuoriRegioneSoloProprietario"
	scope="request" />
<jsp:useBean id="EventoTrasferimentoCanile"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoCanile"
	scope="request" />
<jsp:useBean id="EventoRientroFuoriRegione"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoRientroFuoriRegione"
	scope="request" />
<jsp:useBean id="EventoCambioDetentore"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoCambioDetentore"
	scope="request" />
	<jsp:useBean id="EventoRestituzioneAProprietario"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoRestituzioneAProprietario"
	scope="request" />
<jsp:useBean id="EventoCessione"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoCessione"
	scope="request" />
<jsp:useBean id="cessioneaperta"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoCessione"
	scope="request" />
<jsp:useBean id="passaportoInCorso"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoRilascioPassaporto"
	scope="request" />
<jsp:useBean id="decesso"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoDecesso"
	scope="request" />
<jsp:useBean id="datatocheck" class="java.lang.String" scope="request" />
<jsp:useBean id="labeldatatocheck" class="java.lang.String" scope="request" />
<jsp:useBean id="EventoPresaInCaricoDaCessione"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoPresaInCaricoDaCessione"
	scope="request" />
<jsp:useBean id="reimmissionePrecedente"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoReimmissione"
	scope="request" />	
<jsp:useBean id="EventoPresaInCaricoDaAdozioneFuoriAsl"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoPresaInCaricoDaAdozioneFuoriAsl"
	scope="request" />
	<jsp:useBean id="EventoAdozioneFuoriAsl"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneFuoriAsl"
	scope="request" />
<jsp:useBean id="EventoPresaCessioneImport"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoPresaCessioneImport"
	scope="request" />
<jsp:useBean id="EventoSbloccoAnimale"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoSbloccoAnimale"
	scope="request" />
	<jsp:useBean id="adozioneaperta"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneFuoriAsl"
	scope="request" />

	
	
<jsp:useBean id="EventoCattura"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoCattura"
	scope="request" />
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statoList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="EventoRientroFuoriStato"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoRientroFuoriStato"
	scope="request" />

<!-- Errore wkf nell'invio precedente -->

<jsp:useBean id="ErroreWKF" class="java.lang.String" scope="request" />

<!-- Action di provenienza -->
<jsp:useBean id="actionFrom" class="java.lang.String" scope="request"/>

<meta charset="utf-8" />
<link rel="stylesheet" href="css/jquery-ui_new.css" />

<script src="javascript/jquery-1.9.1_new.js"></script>
<script src="javascript/jquery-ui_new.js"></script>


<script language="javascript" SRC="javascript/CalendarPopup.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js?versione=1"></script>
<script language="javascript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>

<script type="text/javascript" src="dwr/interface/DwrUtil.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/PraticaList.js"> </script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/Animale.js"> </script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/Operatore.js"> </script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/LineaProduttiva.js"> </script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/EventoRilascioPassaporto.js"> </script>



<script language="javascript">

<%
if(request.getAttribute("ErrorBlocco")!=null && !((String)request.getAttribute("ErrorBlocco")).equals("")){
%>
alert("<%=((String)request.getAttribute("ErrorBlocco"))%>");
<%
}	
%>

function InserisciRabbica(idTipologiaEvento, idFrame)
{		
	$('#dettaglio_reg_antirabbia').dialog( 'open' );
}

function DwrUtilComuni()
{
	idAsl = document.forms[0].idAslNuovoProprietario.value;
	if (idAsl == -1){
		idAsl = $('#idAslNuovoProprietarioSelect').val();
	}
	DwrUtil.getValoriComboComuni1Asl(idAsl,setComuniComboCallback) ;
	
}


function DwrUtilProvincia()
{
	idAsl = document.forms[0].idAslNuovoProprietario.value;

	if (idAsl == -1){
		idAsl = $('#idAslNuovoProprietarioSelect').val();
	}
	if(idAsl > -1)
		DwrUtil.getValoriProvincia(idAsl!=14,setProvinciaComboCallback) ;
	
}


function setComuniComboCallback(returnValue)
{

  
	  var select = document.forms[0].idComune; //Recupero la SELECT
    

    //Azzero il contenuto della seconda select
    for (var i = select.length - 1; i >= 0; i--)
  	  select.remove(i);

    indici = returnValue [0];
    valori = returnValue [1];
    //Popolo la seconda Select
    
    var NewOpt = document.createElement('option');
    NewOpt.value = '-1'; // Imposto il valore
    NewOpt.text = 'Seleziona'; // Imposto il testo
    try
    {
  	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
    }catch(e){
  	  select.add(NewOpt); // Funziona solo con IE
    }
    
    for(j =0 ; j<indici.length; j++){
    //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
    var NewOpt = document.createElement('option');
    NewOpt.value = indici[j]; // Imposto il valore
    NewOpt.text = valori[j]; // Imposto il testo
    //Aggiungo l'elemento option
    try
    {
  	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
    }catch(e){
  	  select.add(NewOpt); // Funziona solo con IE
    }
    }
    


}


function setProvinciaComboCallback(returnValue)
{

  
	  var select = document.forms[0].idProvincia; //Recupero la SELECT
    

    //Azzero il contenuto della seconda select
    for (var i = select.length - 1; i >= 0; i--)
  	  select.remove(i);

    indici = returnValue [0];
    valori = returnValue [1];
    
    
    var NewOpt = document.createElement('option');
    NewOpt.value = '-1'; // Imposto il valore
    NewOpt.text = 'Seleziona'; // Imposto il testo
    try
    {
  	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
    }catch(e){
  	  select.add(NewOpt); // Funziona solo con IE
    }
    
    
    //Popolo la Select
    for(j =0 ; j<indici.length; j++){
    //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
    var NewOpt = document.createElement('option');
    NewOpt.value = indici[j]; // Imposto il valore
    NewOpt.text = valori[j]; // Imposto il testo

    //Aggiungo l'elemento option
    try
    {
  	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
    }catch(e){
  	  select.add(NewOpt); // Funziona solo con IE
    }
    }
  


}

function import_proprietario_cessione(){
	
	Operatore.importProprietarioPrivatoDaEventoCessione(<%=animale.getIdAnimale()%>, <%=User.getUserId()%>, {
		callback:function(data) {
	//	alert(data.idEsito);
	//	alert(data.descrizione);
	       $("#datiproprietario").html("");
           $("#datiproprietario").append(data.descrizione);
           if (data.idEsito > 0){
          	 $("#idProprietario").val(data.idEsito);
           }
		},
		timeout:8000,
		async:false

		});
}

var giaCliccato = false;
function import_proprietario_adozione()
{
	loadModalWindow();
<%	
	if (Evento.getIdTipologiaEvento() == EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB && adozioneaperta.getIdProprietario()>0) 
	{
%>
        loadModalWindowUnlock();
		alert('Proprietario già presente nel sistema. Non importabile.');
        
<%
	}
	else
	{
%>
	if(giaCliccato)
	{
		loadModalWindowUnlock();
		alert('Proprietario già importato.');
	}
	else
	{
	Operatore.importProprietarioPrivatoDaEventoAdozione(<%=animale.getIdAnimale()%>, <%=User.getUserId()%>, {
		callback:function(data) {
	       $("#datiproprietario").html("");
           $("#datiproprietario").append(data.descrizione);
           $("#idProprietario").val(data.idEsito);
           giaCliccato = true;
           loadModalWindowUnlock();
           alert("Proprietario importato correttamente");
		},
		timeout:8000,
		async:false

		});
	}
<%
	}
%>

}



function checkColoniaPiena(){
	var ok = false;
	//alert($('#flagCattura').is(':checked'));
	LineaProduttiva.verificaDimensioneColonia(<%=EventoReimmissione.getIdDetentore()%>,  {
		callback:function(data) {
		ok= data;
		},
		timeout:8000,
		async:false

		});

	return ok;
}

function checkRabbia(){
	var ok = false;
	//alert($('#flagCattura').is(':checked'));
	if ($("#dataRilascioPassaporto").val() == '' ){
		//alert("Cortesemente valorizza la data rilascio passaporto prima di proseguire");
		return ok;
	}
	Animale.verificaMCperPassaportoRegRabbia('<%=animale.getMicrochip()%>', '<%=animale.getTatuaggio()%>', $("#dataRilascioPassaporto").val() ,  {
		callback:function(data) {
		
		if (data.idEsito == -1)
		{
			ok = true;
			if(data.dataRegistrazioneAnimale!=null && data.dataRegistrazioneAnimale!="")
			 	document.getElementById('dataScadenzaAntirabbica').value=data.dataRegistrazioneAnimale;
		}
		else {
			 //alert(data.descrizione);
			 alert("Il cane non ha una vaccinazione antirabbica valida nella data di rilascio passaporto indicata. Inserisci prima le informazioni di vaccinazione.");
			 $('#idVaccinazioneAntirabbica').show();
			 document.getElementById('dataScadenzaPassaporto').value='';
			 document.getElementById('dataRilascioPassaporto').value='';
			 if(data.dataRegistrazioneAnimale!=null && data.dataRegistrazioneAnimale!="")
				 document.getElementById('dataScadenzaAntirabbica').value=data.dataRegistrazioneAnimale; 
		}
		},
		timeout:8000,
		async:false

		});
<%
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>
	
	if(ok)
		{
		Animale.verificaMCperPassaportoRegRabbia('<%=animale.getMicrochip()%>', '<%=animale.getTatuaggio()%>', '<%=sdf.format(new Date())%>' ,  {
			callback:function(data) {
			
			if (data.idEsito == -1)
			{
				ok = true;
				if(data.dataRegistrazioneAnimale!=null && data.dataRegistrazioneAnimale!="")
					 document.getElementById('dataScadenzaAntirabbica').value=data.dataRegistrazioneAnimale; 
			}
			else {
				 //alert(data.descrizione);
				 alert("Il cane non ha una vaccinazione antirabbica valida alla data odierna. Inserisci prima le informazioni di vaccinazione.");
				 $('#idVaccinazioneAntirabbica').show();
				 document.getElementById('dataScadenzaPassaporto').value='';
				 document.getElementById('dataRilascioPassaporto').value='';
			}
			},
			timeout:8000,
			async:false

			});
		
		}

	return ok;
}


function verificaIntervalloRegistrazioneAntiRabbia(){
	var ok = false;
	
	Animale.verificaMCperRegRabbiaDataEffettivaVaccinazione(microchip.value, $("#dataVaccinazione").val(), {
		callback:function(data) {
		if (data.idEsito == '-1'){
			ok = false;
			alert(data.descrizione);
		}else{
			ok = true;
		}
		},
		timeout:8000,
		async:false

		});
	
	return ok;
}

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
	  bars   =  'yes';

	 // url = url + '&idAsl=' + document.addAnimale.idAslRiferimento.value;
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
	
	
	
function visualizzaMunicipalita()
{
//alert('visualizzaMunicipalita');

						


			DwrUtil.getListaMunicipalita(document.forms[0].idComuneCattura.value, valorizzaListaCallback21);
			}
			
			


function valorizzaListaCallback21(listaMunicipalita)
{

//	alert(listaMunicipalita.length);
		//It does not exist
//		alert($('#idMunicipalita').length > 0);
	var select = document.createElement("select");
	select.id = "idMunicipalita"; //Recupero la SELECT
	select.name="idMunicipalita";


  i = 0;

  k = 0;

  for (var j = select.length - 1; j >= 0; j--)
  	  	select.remove(j);

  while(i < listaMunicipalita.length){
		
		

			 var NewOpt = document.createElement('option');
			 NewOpt.value = listaMunicipalita[i].id;
		 	 NewOpt.text =  (listaMunicipalita[i].nomeMunicipalita.replace('\uFFFD', 'à'));
			
			 
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
	   
	   var element =  document.getElementById('municip');
		   if (typeof(element) == 'undefined' || element == null ||  element.style.display == 'none')
		   {
			 
		 //alert('undefined');
	   
	var tableRef = document.getElementById('datireg').getElementsByTagName('tbody')[0];

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
	   }	}else{
		 //  alert('else');
 			
 		   var element =  document.getElementById('municip');
 		   if (typeof(element) != 'undefined' && element != null)
 		   {//alert('nascondo');
 			 // element.style.display = 'none';
 		  var row = document.getElementById("municip");
 		 row.parentNode.removeChild(row);
 		   }
 		   
 		   
// 			document.getElementById("idCircoscrizione").style.display="none";
// 			document.getElementById("circoscrizioneAssente").style.display="inline";
 		}
			
    
    }

</script>

<script type="text/javascript">
var name_data_registrazione_to_check_vam = '';
var formTest = true;
function checkForm(){
	formTest = true;
	//alert('ss');
	//alert($('#idProvincia').val());
	//$('#idTipologiaEvento').removeAttr('disabled');
	//Flusso 238
	//$('#idTipologiaEvento').attr('disabled', false);
	message = '';
	//alert($('#doContinue').val());
	if ($('#doContinue').val() == 'true'){
		//alert('<%=Evento.getIdTipologiaEvento()%>');
	 
	// alert(formTest);
	
	<%if ( Evento.getIdTipologiaEvento() == EventoCessione.idTipologiaDB || Evento.getIdTipologiaEvento() == EventoAdozioneFuoriAsl.idTipologiaDB) {
	%>
		//alert($('#idAslNuovoProprietario').val());
		$('#idAslNuovoProprietario').val($('#idAslNuovoProprietarioSelect').val());
		//alert($('#idAslNuovoProprietario').val());
if ($('#idProprietario').val() < 0 && $('#idAslNuovoProprietario').val() != 14 && formTest && '<%=Evento.getIdTipologiaEvento()%>' != '59' && '<%=Evento.getIdTipologiaEvento()%>' != '67'){

	if ($('#idAslNuovoProprietario').val() < 0 && formTest){
	message = "Attenzione, seleziona l'asl destinataria della cessione \r\n";
	formTest = false;
	}
	var idAslRif2 = "" + '<%=animale.getIdAslRiferimento()%>';
	if ( $('#idAslNuovoProprietario').val() ==  idAslRif2 && formTest)
	{
		message = "Attenzione, seleziona un'asl diversa dall'asl attuale dell'animale \r\n";
		formTest = false;
	}	
	
if($('#nome').val() == '' && formTest){
	message = "Attenzione, seleziona il nome del destinatario della cessione \r\n";
	formTest = false;
}

if($('#cognome').val() == '' && formTest){
	message = "Attenzione, seleziona il cognome del destinatario della cessione \r\n";
	formTest = false;
}

if($('#codiceFiscale').val().length != 16 && formTest){
	message = "Attenzione, inserire il codice fiscale(16 caratteri) del destinatario della cessione \r\n";
	formTest = false;
}

if($('#sesso').val() == '-1' && formTest){
	message = "Attenzione, seleziona il sesso del destinatario della cessione \r\n";
	formTest = false;
}

if($('#cap').val() == '' && formTest){
	message = "Attenzione, inserire il cap della residenza del destinatario della cessione \r\n";
	formTest = false;
}

if(($('#cap').val().length!=5 || $('#cap').val().indexOf(' ')>=0)&& formTest){
	message = "Attenzione, il cap della residenza del destinatario della cessione deve essere composto da 5 caratteri e non deve contenere spazi\r\n";
	formTest = false;
}

if($('#cap').val() == '80100' && formTest){
	message = "Attenzione, il cap della residenza del destinatario della cessione non può essere 80100\r\n";
	formTest = false;
}

if($('#numeroTelefono').val() == '' && formTest){
	message = "Attenzione, seleziona il numero di telefono del destinatario della cessione \r\n";
	formTest = false;
}

if($('#luogoNascita').val() == '' && formTest){
	message = "Attenzione, seleziona il comune di nascita del destinatario della cessione \r\n";
	formTest = false;
}

if($('#dataNascita').val() == '' && formTest){
	message = "Attenzione, seleziona la data di nascita del destinatario della cessione \r\n";
	formTest = false;
}

if($('#idComune').val() == '' && formTest){
	message = "Attenzione, seleziona il comune del destinatario della cessione \r\n";
	formTest = false;
}

if($('#idProvincia').val() == '' && formTest){
	message = "Attenzione, seleziona la provincia del destinatario della cessione \r\n";
	formTest = false;
}

if($('#indirizzo').val() == '' && formTest){
	message = "Attenzione, seleziona la via del destinatario della cessione \r\n";
	formTest = false;
}

}

if( formTest &&  $('#idProprietario').val() < 0 && '<%=Evento.getIdTipologiaEvento()%>' == '59')
	{
	message = "- Proprietario destinatario della cessione richiesto\r\n";
	formTest = false;
	
	}
	
else if( formTest &&  $('#idProprietario').val() < 0 && '<%=Evento.getIdTipologiaEvento()%>' == '67')
{
message = "- Proprietario destinatario dell'adozione richiesto\r\n";
formTest = false;

}


<%} else if ( Evento.getIdTipologiaEvento() == EventoDecesso.idTipologiaDB) {
%>


if($('#idProvinciaDecesso').val() < 0){
	message += "Attenzione, seleziona la provincia in cui è avvenuto il decesso \r\n";
	formTest = false;
}

if($('#idComuneDecesso').val() == '' || $('#idComuneDecesso').val() < 0){
	message += "Attenzione, seleziona il comune in cui è avvenuto il decesso \r\n";
	formTest = false;
}

if($('#indirizzoDecesso').val() == ''){
	message += "Attenzione, seleziona l'indirizzo del decesso \r\n";
	formTest = false;
}




<%}else if ( Evento.getIdTipologiaEvento() == EventoCambioDetentore.idTipologiaDB) {
%>

if($('#idDetentore').val() < 0){
	message += "- Attenzione, seleziona il nuovo detentore \r\n";
	formTest = false;
}






<%}else if ( Evento.getIdTipologiaEvento() == EventoTrasferimento.idTipologiaDB) {
%>

if($('#idProprietario').val() < 0){
	message += "- Attenzione, seleziona il nuovo proprietario \r\n";
	formTest = false;
}
<%
}
else if ( Evento.getIdTipologiaEvento() == EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB) {
%>

if($('#idProprietario').val() < 0){
	message += "- Attenzione, seleziona il nuovo proprietario \r\n";
	formTest = false;
}

<%}else if ( Evento.getIdTipologiaEvento() == EventoTrasferimentoFuoriRegione.idTipologiaDB) {
%>

if($('#idRegioneA').val() < 0){
	message += "- Attenzione, seleziona la regione di destinazione \r\n";
	formTest = false;
}

if($('#idRegioneA').val() == 15 && formTest){
	message += "- Attenzione, selezionare una regione diversa dalla Campania \r\n";
	formTest = false;
}

<%}else if ( Evento.getIdTipologiaEvento() == EventoTrasferimentoFuoriRegioneSoloProprietario.idTipologiaDB) {%>

if($('#idProprietario').val() < 0){
	message += "- Attenzione, seleziona il nuovo proprietario \r\n";
	formTest = false;
}

<%}else if ( Evento.getIdTipologiaEvento() == EventoTrasferimentoSindaco.idTipologiaDB) {
%>

if($('#idProprietario').val() < 0){
	message += "- Attenzione, seleziona il nuovo proprietario \r\n";
	formTest = false;
}

if($('#idDetentore').val()=='' || $('#idDetentore').val() < 0){
	message += "- Attenzione, seleziona il nuovo detentore \r\n";
	formTest = false;
}

<%}else if ( Evento.getIdTipologiaEvento() == EventoRientroFuoriRegione.idTipologiaDB) {%>

if($('#idProprietario').val() < 0){
	message += "- Attenzione, seleziona il nuovo proprietario \r\n";
	formTest = false;
}

if($('#idAsl').val() < 0){
	message += "- Attenzione, seleziona l'asl del nuovo proprietario \r\n";
	formTest = false;
}









<%}else if ( Evento.getIdTipologiaEvento() == EventoRientroFuoriStato.idTipologiaDB) {
%>

if($('#idProprietario').val() < 0){
	message += "- Attenzione, seleziona il nuovo proprietario \r\n";
	formTest = false;
}


if($('#idDetentore').val() < 0){
	message += "- Attenzione, seleziona il nuovo detentore, eventualmente uguale al proprietario \r\n";
	formTest = false;
}



<%}else if ( Evento.getIdTipologiaEvento() == EventoTrasferimentoFuoriStato.idTipologiaDB) {
%>

if($('#idContinente').val() < 0){
	message += "- Attenzione, seleziona il continente destinatario \r\n";
	formTest = false;
}



<%}else if ( Evento.getIdTipologiaEvento() == EventoTrasferimentoCanile.idTipologiaDB) {
%>

if($('#idDetentore').val() < 0){
	message += "- Attenzione, seleziona il canile destinatario \r\n";
	formTest = false;
}



<%}%>


<%if ( Evento.getIdTipologiaEvento() == 15) {
%>
//alert($('#idAslNuovoProprietario').val());

//alert($('#idAslNuovoProprietario').val());
if ($('#idProprietario').val() < 0){
message = "Attenzione, seleziona il destinatario della cessione \r\n";
formTest = false;
}

<%}else if (Evento.getIdTipologiaEvento() == EventoInserimentoVaccinazioni.idTipologiaDB){
%>

if($('#idTipoVaccino').val() == '-1'){
	message += "Attenzione, seleziona la tipologia di vaccinazione \r\n";
	formTest = false;
}

if($('#idTipoVaccino').val() == '1' && $('#idTipologiaVaccinoInoculato').val()=='-1'){
	message += "Attenzione, selezionare il Tipo di vaccino inoculato \r\n";
	formTest = false;
}



if($('#idTipoVaccino').val() == '1'){
	
	if($('#idVeterinarioEsecutoreAccreditato').val() == '-1' && $('#veterinarioEsecutoreNonAccreditato').val()==''){
		message += " - Selezionare un veterinario esecutore \r\n";
		formTest = false;
	}

if (verificaIntervalloRegistrazioneAntiRabbia() == false){
	formTest = false;
}







}



<%}
   boolean a = false;
   
if ( Evento.getIdTipologiaEvento() == EventoAdozioneDaCanile.idTipologiaDB){
%>
    if ($('#idProprietario').val() < 0){
    	message = "Attenzione, seleziona un proprietario per la registrazione di adozione \r\n";
		formTest = false;
    }

<%}%>
<%if ( Evento.getIdTipologiaEvento() == EventoCattura.idTipologiaDBRicattura) {
%>

if ($('#idDetentore').val() < 0){
	message = "Attenzione, seleziona un nuovo detentore per la ricattura \r\n";
	formTest = false;
}
if ($('#idComuneCattura').val() < 0){
	message = "Attenzione, seleziona un comune per la ricattura \r\n";
	formTest = false;
}




<%}%>
<%if ( Evento.getIdTipologiaEvento() == EventoSterilizzazione.idTipologiaDB) {
%>
//SE HAI SELEZIONATO IL FLAG CONTRIBUTI DEVI SELEZIONARE UN CONTRIBUTO DALLA LISTA
if ($('#flagContributoRegionale').length && document.getElementById("flagContributoRegionale").checked==true){
	if (document.getElementById("idProgettoSterilizzazioneRichiesto").selectedIndex<1){	
	message += "Attenzione, selezionare una pratica contributo o deselezionare contributo regionale.   \r\n";
	formTest = false;
}
}
if(document.getElementById('idTipologiaSoggettoSterilizzante').value=="1" && $("#veterinari :selected").length>2)
{
	message += label("", "- Massimo due veterinari asl selezionabili \r\n");
	formTest = false;	
}
if(document.getElementById('idTipologiaSoggettoSterilizzante').value=="1" && $("#veterinari :selected").length<2)
{
	message += label("", "- Selezionare due veterinari \r\n");
	formTest = false;	
}
<%if (User.getSiteId() != animale.getIdAslRiferimento()	){%>

var sure = confirm ("L'animale appartiene ad un'asl non di tua competenza, sei sicuro di voler inserire la registrazione?");
if (!sure){
	return false;
	}

<%
}
%>

if ($('#flagRegistrazioneForzata').length){

	if (!document.getElementById("flagRegistrazioneForzata").checked){
		
		message += "Attenzione, puoi inserire questa registrazione solo in modalità forzata, cortesemente seleziona il relativo flag.   \r\n";
		formTest = false;
		
	}if (document.getElementById('idTipologiaSoggettoSterilizzante').value=="1" && document.getElementById("idAslEsecutrice").selectedIndex<1){
		message += "Attenzione, per l'inserimento di una registrazione di sterilizzazione forzata è necessario specificare l'asl esecutrice.   \r\n";
		formTest = false;
	}
	if (document.getElementById('idTipologiaSoggettoSterilizzante').value=="1" && $("#veterinari :selected").length<=0){
		message += "Attenzione, per l'inserimento di una registrazione di sterilizzazione forzata è necessario specificare almeno un veterinario dall'elenco.   \r\n";
		formTest = false;
	}
}



<%}%>


<%if ( Evento.getIdTipologiaEvento() == EventoReimmissione.idTipologiaDB && animale.getIdSpecie() == Gatto.idSpecie) {
%>
//alert( $('#idDetentore').val());
	if ( $('#idDetentore').val() > 0 && checkColoniaPiena() == false){
		message += label("", "Hai selezionato una colonia piena, cambia proprietario o modifica la dimensione della colonia \r\n");
		formTest = false;
	}
	





<%}

	if ( Evento.getIdTipologiaEvento() == EventoReimmissione.idTipologiaDB ) 
	{
%>
	if ( $('#luogoReimmissione').val() == '' )
	{
		message += label("", "Inserire il luogo di reimmissione \r\n");
		formTest = false;
	}

<%
	}

if ( Evento.getIdTipologiaEvento() == EventoRilascioPassaporto.idTipologiaDB || Evento.getIdTipologiaEvento() == EventoRilascioPassaporto.idTipologiaRinnovoDB) {
%>
//alert( '32');
//alert(checkRabbia());
//Flusso 238
if((document.getElementById("id_canile").value=="" || document.getElementById("id_canile").value=="null") && (document.getElementById("id_stabilimento_gisa").value=="" || document.getElementById("id_stabilimento_gisa").value=="null"))
{
	if(document.getElementById('flagSmarrimento')==null || document.getElementById('flagSmarrimento').checked)
	{
	message += label("", "Per inserire una registrazione di passaporto è necessario selezionare l'ambulatorio entrando da VAM. \r\n");
	formTest = false;
	}
	
}

<%
String dataToFill2 = ((String) request.getParameter("dataRegistrazione") != null) ? (String) request.getParameter("dataRegistrazione") : "";

%>


if(document.getElementById('dataRilascioPassaporto')!=null && document.getElementById('dataRilascioPassaporto').value!=null && document.getElementById('dataRilascioPassaporto').value!="")
{
	if('<%=dataToFill2%>'!=null && '<%=dataToFill2%>'!="" && '<%=dataToFill2%>'!="null")
	if(document.getElementById('dataRilascioPassaporto').value!='<%=dataToFill2%>')
	{
		message += label("", "Inserire la data di rilascio passaporto uguale alla data accettazione (<%=dataToFill2%>). \r\n");
		formTest = false;
	}
}


if (checkRabbia() == false){
		message += label("", "Il cane non ha una vaccinazione antirabbica valida nell'anno corrente. Inserisci prima le informazioni di vaccinazione. \r\n");
		formTest = false;
	}
	
	
if ($('#numeroPassaporto').val() == ''){
	message += label("", "Valore passaporto vuoto. \r\n");
	formTest = false;
}


//alert(verificaPassaporto(document.forms[0].numeroPassaporto));
	//alert(vecchioPassaporto);
if (!$('#numeroPassaporto').val()== '' && verificaPassaporto(document.forms[0].numeroPassaporto) == false)
{
	//	message += label("", "Il cane non ha una vaccinazione antirabbica valida nell'anno corrente. Inserisci prima le informazioni di vaccinazione. \r\n");
		formTest = false;
	}







<%}if ( Evento.getIdTipologiaEvento() == EventoRitrovamento.idTipologiaDB || Evento.getIdTipologiaEvento() == EventoRitrovamentoNonDenunciato.idTipologiaDB) {
%>
//alert( '32');
//alert(checkRabbia());
	if (!$('#flagRitornoAProprietario').is(':checked') && $('#idDetentore').val() == -1 ){
		message += label("", "Devi cortesemente selezionare un canile detentore o scegliere l'opzione di ritorno al proprietario \r\n");
		formTest = false;
	//	alert("Devi cortesemente selezionare un canile detentore o scegli l'opzione di ritorno al proprietario");
	}





<%}if ( Evento.getIdTipologiaEvento() == EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip) {
%>


if (document.forms[0].numeroMicrochipAssegnato.value == ""){
	
	message += label("", "Inserisci un valore per il secondo Mc. \r\n");
	formTest = false;
}
//alert(verificaInserimentoAnimale(document.forms[0].numeroMicrochipAssegnato));
if (verificaInserimentoAnimale(document.forms[0].numeroMicrochipAssegnato) == false){
	message += label("", "Verifica il secondo MC inserito. \r\n");
	formTest = false;
}





<%}if ( Evento.getIdTipologiaEvento() == EventoModificaResidenza.idTipologiaDB) {
	%>

	if (document.getElementById('idComuneModificaResidenzaSelect')!=null && (document.getElementById('idComuneModificaResidenzaSelect').value=='' || document.getElementById('idComuneModificaResidenzaSelect').value=='-1'))
	{
		message += label("", "- Indirizzo destinazione richiesto\r\n");
		formTest = false;
	}
	
	if (document.getElementById('idAslNuovoProprietario').value == '<%=User.getSiteId()%>')
	{
		message += label("", "- Selezionare indirizzo di asl diversa dall''attuale'\r\n");
		formTest = false;
	}
	





	<%}
if ( Evento.getIdTipologiaEvento() ==EventoSbloccoAnimale.idTipologiaDB) {
%>

if ($('#noteSblocco').val() == ''){
	message += label("", "Fornisci una motivazione allo sblocco. \r\n");
	formTest = false;
	
}


if (!document.getElementById("flagRipristinaStatoPrecendente").checked){
	
	if ($('#idProprietario').val() == -1){
		message += label("", "Seleziona correttamente un proprietario. \r\n");
		formTest = false;
	}
	
	if ($('#idDetentore').val() == -1){
		message += label("", "Seleziona correttamente un detentore. \r\n");
		formTest = false;
	}
	var sureToGo = confirm ("Stai introducendo dati che potrebbero rendere incongruente la storia dell'animale, sei sicuro di voler proseguire?");
	if (!sureToGo){
		formTest = false;
		}
}





<%}if ( Evento.getIdTipologiaEvento() ==EventoBloccoAnimale.idTipologiaDB) {
%>

if ($('#noteBlocco').val() == ''){
	message += label("", "Fornisci una motivazione al blocco. \r\n");
	formTest = false;
	
}






<%}if (Evento.getIdTipologiaEvento() == EventoRegistrazioneEsitoControlliCommerciali.idTipologiaDB ){%>



if ($( '#idDecisioneFinale' ).val() == -1){
	  formTest = false;
	    message = 'Seleziona un esito finale per la registrazione di inserimento controlli'; 
}


<%}if (Evento.getIdTipologiaEvento() == EventoMutilazione.idTipologiaDB ){%>



if ($( '#idMedicoEsecutore' ).val() == -1){
	  formTest = false;
	    message = 'Seleziona un medico esecutore'; 
}

if ($( '#idMedicoEsecutore' ).val() == 1 && $( '#idVeterinarioASL' ).val()==-1){
	  formTest = false;
	    message = 'Seleziona un veterinario ASL'; 
}
if ($( '#idMedicoEsecutore' ).val() == 2 && $( '#idVeterinarioLLPP' ).val()==-1){
	  formTest = false;
	    message = 'Seleziona un veterinario LLPP'; 
}
if ($( '#idInterventoEseguito' ).val() == -1){
	  formTest = false;
	    message = 'Seleziona un intervento eseguito'; 
}
if ($( '#idCausale' ).val() == -1){
	  formTest = false;
	    message = 'Seleziona una causale'; 
}

<%}if (Evento.getIdTipologiaEvento() == EventoAllontanamento.idTipologiaDB ){%>



if ($( '#idMedicoEsecutore' ).val() == -1){
	  formTest = false;
	    message = 'Seleziona un medico esecutore'; 
}

if ($( '#idMedicoEsecutore' ).val() == 1 && $( '#idVeterinarioASL' ).val()==-1){
	  formTest = false;
	    message = 'Seleziona un veterinario ASL'; 
}
if ($( '#idMedicoEsecutore' ).val() == 2 && $( '#idVeterinarioLLPP' ).val()==-1){
	  formTest = false;
	    message = 'Seleziona un veterinario LLPP'; 
}

if ($( '#idCausale' ).val() == -1){
	  formTest = false;
	    message = 'Seleziona una causale'; 
}

<%} 

//commento al 214
if (Evento.getIdTipologiaEvento() == EventoMorsicatura.idTipologiaDB && true){
%>



if ($( '#tipologia' ).val() == -1){
	  formTest = false;
	    message += '- Tipologia richiesta\r\n'; 
}

else if ($( '#tipologia' ).val() == 1)
{
	if ($( '#prevedibilitaEvento' ).val() == -1)
	{
		    formTest = false;
		    message += '- Prevedibilità evento richiesta \r\n'; 
	}
	if(document.getElementById('morsoRipetuto').value== -1)
	//if ($( '#morso ripetuto' ).val() == -1)
	{
		    formTest = false;
		    message += '- Morso ripetuto richiesto \r\n'; 
	}
	if ($( '#tagliaAggressore' ).val() == -1)
	{
		    formTest = false;
		    message += '- Taglia aggressore richiesta \r\n'; 
	}
	if ($( '#categoriaVittima' ).val() == -1)
	{
		    formTest = false;
		    message += '- Categoria vittima richiesta \r\n'; 
	}
	if ($( '#tagliaVittima' ).val() == -1)
	{
		    formTest = false;
		    message += '- Taglia vittima richiesta \r\n'; 
	}
	if ($( '#patologie' ).val() == -1)
	{
		    formTest = false;
		    message += "- Rilievi sull'aggressore: Patologie richiesta \r\n"; 
	}
	if(document.getElementById('alterazioniComportamentali').value== -1)
	//if ($( '#alterazioni comportamentali' ).val() == -1)
	{
		    formTest = false;
		    message += "- Rilievi sull'aggressore: Alterazioni comportamentali richiesta \r\n"; 
	}
	if(document.getElementById('analisiGestione').value== -1)
	//if ($( '#analisi gestione' ).val() == -1)
	{
		    formTest = false;
		    message += '- Analisi gestione richiesta \r\n'; 
	}
	
}
else if ($( '#tipologia' ).val() == 2)
{
	if(document.getElementById('morsoRipetuto').value== -1)
	//if ($( '#morso ripetuto' ).val() == -1)
	{
		    formTest = false;
		    message += '- Morso ripetuto richiesto \r\n'; 
	}
	if ($( '#patologie' ).val() == -1)
	{
		    formTest = false;
		    message += "- Rilievi sull'aggressore: Patologie richiesta \r\n"; 
	}
	if(document.getElementById('alterazioniComportamentali').value== -1)
	//if ($( '#alterazioni comportamentali' ).val() == -1)
	{
		    formTest = false;
		    message += "- Rilievi sull'aggressore: Alterazioni comportamentali richiesta \r\n"; 
	}
	if(document.getElementById('analisiGestione').value== -1)
	//if ($( '#analisi gestione' ).val() == -1)
	{
		    formTest = false;
		    message += '- Analisi gestione richiesta \r\n'; 
	}
	
	if (!checkFormRegistrazioneMorsicatura() )
		formTest = false;	
}

if( $("#veterinariMorsicatura :selected").length<=0 || document.getElementById("veterinariMorsicatura").value=="" || document.getElementById("veterinariMorsicatura").value=="-1")
{
	message += label("", "- Selezionare almeno un veterinario dalla lista \r\n");
	formTest = false;	
}

if(document.getElementById('misureFormative').value== '')
{
    formTest = false;
    message += '- Misure formative richieste \r\n'; 
}

if(document.getElementById('misureRiabilitative').value== '')
{
    formTest = false;
    message += '- Misure riabilitative richieste \r\n'; 
}

if(document.getElementById('misureRestrittive').value== '')
{
    formTest = false;
    message += '- Misure restrittive richieste \r\n'; 
}

var listaCu = document.getElementsByName('idCu');
for(i = 0;i < listaCu.length; i++)
{
	if(listaCu[i].checked)
		break
	else if(i==listaCu.length-1)
	{
	    formTest = false;
	    message += '- Controllo ufficiale richiesto \r\n'; 
	}
}
if(listaCu.length==0)
{
	formTest = false;
    message += '- Nessun controllo ufficiale inserito in Gisa per questo animale su attività AO9_B o AO9_C \r\n'; 
}
}

if(document.getElementById('dataMorso').value!='' && document.getElementById('dataMorso').value != null && giorni_differenza(document.getElementById('nascita').value,document.getElementById('dataMorso').value) <0)
{
	formTest = false;
    message += '- La data della registrazione non può essere antecedente alla data di nascita \r\n'; 
}




if(formTest && checkRegistroAggressori() && !confirm("Attenzione! La registrazione che si sta inserendo ha i requisiti per entrare nel registro unico cani aggressori. " + calcoloPunteggioDehasseAvviso() + " Continuare?"))
{
	formTest = false;
}

<%} 

if (Evento.getIdTipologiaEvento() == EventoAggressione.idTipologiaDB )
{
%>



if ($( '#tipologia' ).val() == -1)
{
	  formTest = false;
	    message += '- Tipologia richiesta\r\n'; 
}

else 
{	
	if ($( '#tipologia' ).val() == 1 || $( '#tipologia' ).val() == 2)
	{
		if ($( '#prevedibilitaEvento' ).val() == -1)
		{
			    formTest = false;
			    message += '- Prevedibilità evento richiesta \r\n'; 
		}
		if(document.getElementById('aggressioneRipetuta').value== -1)
		//if ($( '#morso ripetuto' ).val() == -1)
		{
			    formTest = false;
			    message += '- Aggressione ripetuta richiesto \r\n'; 
		}
		if ($( '#tagliaAggressore' ).val() == -1)
		{
			    formTest = false;
			    message += '- Taglia aggressore richiesta \r\n'; 
		}
		if ($( '#categoriaVittima' ).val() == -1)
		{
			    formTest = false;
			    message += '- Categoria vittima richiesta \r\n'; 
		}
	}
	if ( $( '#tipologia' ).val() == 1)
	{	
	
		if ($( '#tagliaVittima' ).val() == -1)
		{
			    formTest = false;
			    message += '- Taglia vittima richiesta \r\n'; 
		}
	}
	if ($( '#tipologia' ).val() == 1 || $( '#tipologia' ).val() == 2)
	{
		if ($( '#patologie' ).val() == -1)
		{
			    formTest = false;
			    message += "- Rilievi sull'aggressore: Patologie richiesta \r\n"; 
		}
		if(document.getElementById('alterazioniComportamentali').value== -1)
		//if ($( '#alterazioni comportamentali' ).val() == -1)
		{
			    formTest = false;
			    message += "- Rilievi sull'aggressore: Alterazioni comportamentali richiesta \r\n"; 
		}
		if(document.getElementById('analisiGestione').value== -1)
		//if ($( '#analisi gestione' ).val() == -1)
		{
			    formTest = false;
			    message += '- Analisi gestione richiesta \r\n'; 
		}
	}
	
	
	if(document.getElementById('misureFormative').value== '')
	{
	    formTest = false;
	    message += '- Misure formative richieste \r\n'; 
	}

	if(document.getElementById('misureRiabilitative').value== '')
	{
	    formTest = false;
	    message += '- Misure riabilitative richieste \r\n'; 
	}

	if(document.getElementById('misureRestrittive').value== '')
	{
	    formTest = false;
	    message += '- Misure restrittive richieste \r\n'; 
	}
	
	if( $("#veterinariAggressione :selected").length<=0 || document.getElementById("veterinariAggressione").value=="" || document.getElementById("veterinariAggressione").value=="-1")
	{
		message += label("", "- Selezionare almeno un veterinario dalla lista \r\n");
		formTest = false;	
	}

	var listaCu = document.getElementsByName('idCu');
	for(i = 0;i < listaCu.length; i++)
	{
		if(listaCu[i].checked)
			break
		else if(i==listaCu.length-1)
		{
		    formTest = false;
		    message += '- Controllo ufficiale richiesto \r\n'; 
		}
	}
	
	if(listaCu.length==0)
	{
	 	formTest = false;
	 	message += '- Nessun controllo ufficiale inserito in Gisa per questo animale su attività B52 \r\n'; 
	}
	}
	
	if(document.getElementById('dataAggressione').value!='' && document.getElementById('dataAggressione').value != null && giorni_differenza(document.getElementById('nascita').value,document.getElementById('dataAggressione').value) <0)
	{
		formTest = false;
	    message += '- La data della registrazione non può essere antecedente alla data di nascita \r\n'; 
	}
}


if(formTest && checkRegistroAggressori() && !confirm("Attenzione! La registrazione che si sta inserendo ha i requisiti per entrare nel registro unico cani aggressori. " + calcoloPunteggioDehasseAvviso() + " Continuare?"))
{
	formTest = false;
}





<%} %>

	lanciaControlloDate(<%=User.getRoleId()%>);
	if(message=='')
		{
	
	<%
	if ( Evento.getIdTipologiaEvento() != EventoEsitoControlli.idTipologiaDB && Evento.getIdTipologiaEvento() != EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip){
	%>
	if ((checkRicovero() != null && checkRicovero()!="")  && <%=session.getAttribute("caller")==null || !ApplicationProperties.getProperty("VAM_ID").equals(session.getAttribute("caller"))%>){

		//var a = confirm ("L'animale risulta ricoverato in VAM, sei sicuro di voler  continuare?");
		//if (!a){
			//return false;
			//}
		
		alert("Impossibile proseguire. L'animale risulta ricoverato in VAM con fascicolo sanitario "+checkRicovero()+".");
		return false;
		
	}
<%}
%>

		}



	
	
	
	
	    if (formTest == false) {

	    	if (message != '')
	        	alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	        return false;
	      }
	      else
	      {

	    	  //Flusso 238
	    	  $('#idTipologiaEvento').attr('disabled', false);
	    	  $('form:first').submit();
	      }
	}else{

		}
}


var showInvia = false;
var vecchioPassaporto = '';
function popolaCampi(){
//	alert('ddd');
  <%if ( Evento.getIdTipologiaEvento() == 40){
  %>
	 $('#dialog').dialog('open');
 <%}%>
//	alert('ddd');
 <%if ( ErroreWKF!= null && !("").equals(ErroreWKF)){%>
	 $('#dialogErrorWKF').dialog('open');
<%}%>
		 $.ajax({
		        type: "GET",
		        url: "<%=request.getContextPath() %>/ServletForm?idAnimale=<%=animale.getIdAnimale()%>&idStato=<%=animale.getStato()%>&idSpecie=<%=animale.getIdSpecie()%>&idTipoRegistrazione="+ $("#idTipologiaEvento").children("option:selected").val(),
		        async: false,
		        dataType: "json",
		        error: function(XMLHttpRequest, status, errorThrown) {
		            alert(status);
		        },
		        success: function (data, status) {
		        	var  toinsert = '';
		        	var html = '';
		        	
		        	var proprietario = "";
		        	if (data.length == 0){
		        		showInvia = false;    
		        	}else{
		        		showInvia = true;
		        	}
				if (data.length > 0){
    	        	toinsert = toinsert + '<table id="dati" cellpadding="2" cellspacing="0" border="0" width="100%"'+
	        		'class="details">'+
	        	'<tr>'+
	        		'<th colspan="2"><strong><dhv:label name="">Dati da inserire</dhv:label></strong>'+
	        		'</th>'
	        	'</tr>';
}
				var valoreJsp ='';
		        	for (var i = 0; i < data.length; i++) {
			        	
		        	    var row = data[i];
		        	    		
		        	            var label = row['label'];
		        	            var labelLink = row['label_link'];
		        	   			var type = row['type'];
		        	   			var name = row['name'];
		        	   			var lookupname = row['lookup'];
		        	   			var value = row['value'];
		        	   			var javascript = row['javascript'];
		        	   			var controlli = row['controlli'];
		        	   			var label_controlli = row['label_data'];
		        	   			var size = row['size'];
		        	   			


		        	     
		        	        		        	        	
		        	  if (type == 'data'){
		            	//  alert ('in if');
		        		  toinsert = toinsert + '<tr>  <td class="formLabel"> <dhv:label name="">'+label+'</dhv:label></td> <td>';
		                  toinsert = toinsert + '<input  readonly type="text" id="'+name+'" name="'+name+'" size="10" value="" nomecampo="'+name+'" tipocontrollo="'+controlli+'" labelcampo="'+label_controlli+'" />&nbsp;'
							if (name == 'dataRilascioPassaporto'){
								toinsert = toinsert + '<a href="#" onClick="cal19.select2(document.forms[0].'+name+', document.forms[0].dataScadenzaPassaporto, \'anchor19\',\'dd/MM/yyyy\'); return false;" NAME="anchor19" ID="anchor19"><img id ="'+name+'_img" src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a></td></tr>';
								}
							else if (name == 'dataScadenzaPassaporto'){
								toinsert = toinsert + '</td></tr>';
								}
							else{
			              		toinsert = toinsert + '<a href="#" onClick="cal19.select(document.forms[0].'+name+',\'anchor19\',\'dd/MM/yyyy\'); return false;" NAME="anchor19" ID="anchor19"><img id ="'+name+'_img" src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a></td></tr>';
							} 

		
		                  if (name_data_registrazione_to_check_vam == null || name_data_registrazione_to_check_vam == '')
		                  	name_data_registrazione_to_check_vam = name; //Mi serve per passare il nome del campo della data (che cambia in base alla registrazione)
		                  											   // da verificare cn vam
			        		//  alert(toinsert);
		        	  }else if (type == 'select'){

							 html = row['html'];
		            	  
		        		//  var toinsert = toinsert + '<tr><td><a href="javascript:popUp(\'Soggetto.do?command=Search&tipologiaSoggetto=1&popup=true\');">Ricerca soggetto</a></td></tr>'
		        		   toinsert = toinsert + '<tr><td class="formLabel"> <dhv:label name="">'+label+'</dhv:label></td><td>'+html+'</td></tr>'
		            	  }
		        	  else if (type == 'jsp'){

							 html = row['html'];
		            	  
		        		//  var toinsert = toinsert + '<tr><td><a href="javascript:popUp(\'Soggetto.do?command=Search&tipologiaSoggetto=1&popup=true\');">Ricerca soggetto</a></td></tr>'
		        		   toinsert = toinsert + '<tr><td id="jsp" colspan ="2" class="formLabel" style="background-color: white;"></td></tr>';
		        		   valoreJsp = row['value'];
		        		   if(valoreJsp.indexOf('PARAMETROIDANIMALE') !== -1)
		        			   valoreJsp = valoreJsp.replaceAll('PARAMETROIDANIMALE','<%=animale.getIdAnimale()%>' );
		            	  }
		        	  
		        	  else if (type == 'radio'){

							 html = row['html'];
		            	  
		        		//  var toinsert = toinsert + '<tr><td><a href="javascript:popUp(\'Soggetto.do?command=Search&tipologiaSoggetto=1&popup=true\');">Ricerca soggetto</a></td></tr>'
							 toinsert = toinsert + '<tr><td class="formLabel"> <dhv:label name="">'+label+'</dhv:label></td><td>'+html+'</td></tr>'
		            	  }
		        	  
		        	  else if (type == 'textarea'){
							toinsert = toinsert + '<tr><td class="formLabel" nowrap>'+label+'</td><td><textarea rows="10" cols="10" name="'+name+'" id="'+name+'"></textarea></td></tr>';
			            	  
		            	  } else if (type == 'hidden'){

						
		          	  
		        		    toinsert = toinsert + '<input type = "'+type+'" name="'+name+'" id="'+name+'" value ="'+value+'" tipocontrollo="'+controlli+'" />';
		          	  }else if (type == 'link'){
			         
							 html = row['html'];
							 if ((name == 'idProprietario' || name == 'idProprietarioLink')  && ($('#idProprietario').val() != "" && $('#idProprietario').val() != "-1")){
								<%if ( EventoRitrovamento.getIdProprietario() > -1 || EventoAdozioneDaCanile.getIdProprietario() > -1){%>
								 proprietario = '<%if(animalenewinfo!=null && animalenewinfo.getProprietario()!=null && animalenewinfo.getProprietario().getRagioneSociale()!=null){System.out.println(animalenewinfo.getProprietario().getRagioneSociale().replaceAll("\"", "").replaceAll("'", "").replaceAll("\n", "") + " ");} %>';
								 <%}else if (Evento.getIdTipologiaEvento() == 15) {
						            
						            if (cessioneaperta.getIdProprietario() > 0){
						            	Connection conn = GestoreConnessioni.getConnection();
										 Operatore proprietario = new Operatore();
										 proprietario
												.queryRecordOperatorebyIdLineaProduttiva(conn,
														cessioneaperta.getIdProprietario());%>
									 proprietario = '<%=proprietario.getRagioneSociale().replaceAll("\"", "").replaceAll("'", "").replaceAll("\n", "") + " " %>';
										<%GestoreConnessioni.freeConnection(conn);
										}
										}
										else if (EventoCattura.getIdDetentore() > 0){%>
										 proprietario = "<%=animalenewinfo.getDetentore().getRagioneSociale().replaceAll("\"", "") + " " %>";
									<%	}
								
										else if (Evento.getIdTipologiaEvento() == 41){%>
										 proprietario = "<%=animalenewinfo.getDetentore().getRagioneSociale() + " " %>";
									<%	}
										
										
										
										%>
						
							}else if (name == 'idDetentore' || name == 'idDetentoreLink'){
							<% if (EventoCambioDetentore.getIdDetentore() > -1 && animale.getDetentore() != null && animale.getDetentore().getIdOperatore() > 0 ){%>
							 proprietario = '<%=animale.getDetentore().getRagioneSociale().replaceAll("\"", "").replaceAll("'", "") + " " %>';
							// alert(proprietario);
							<%}
								else{%>
							
							
							 proprietario = "";
							<%}%>
							}
							
							
			        		//  var toinsert = toinsert + '<tr><td><a href="javascript:popUp(\'Soggetto.do?command=Search&tipologiaSoggetto=1&popup=true\');">Ricerca soggetto</a></td></tr>'
			        		  var toinsert = toinsert + '<tr><td class="formLabel"> <dhv:label name="">'+labelLink+'</dhv:label></td><td><span id="datiproprietario">'+proprietario+'</span><br>'+html;
			        		  if (name == 'idProprietario' || name == 'idDetentore'){

									
									   	toinsert = toinsert + ' - <a id="idPulisci" href="#" onclick="javascript:pulisciProprietario()">Pulisci</a>';
				        		  }
			        		  toinsert = toinsert + '</td></tr>';
		          	  }
		        	  else{
		            	 
		        	  	var  toinsert = toinsert + '<tr><td class="formLabel" nowrap>'+label+'</td><td><input type = "'+type+'" name="'+name+'" id="'+name+'" value="'+value+'" maxlength="'+size+'" tipocontrollo="'+controlli+'"' +javascript+' /></td></tr>';
		        	  //	alert(toinsert);
		        	  }
		        	}

		          //  alert(toinsert);
		            $("#datireg").html("");
		            $("#datireg").append(toinsert);
		            $("#datireg").append("<tr id =\"pratica_contributo\" name=\"pratica_contributo\"  style=\"visibility: hidden\" >      <td  class=\"formLabel\">Contributo</td><td><select style=\"width:100%;\" id=\"idProgettoSterilizzazioneRichiesto\" name=\"idProgettoSterilizzazioneRichiesto\"></select></td></tr>");

		            if(valoreJsp!='')
		            		$("#jsp").load(valoreJsp); 
		            
		          
		            //SE EventoRitrovamento
		            <%
		           // System.out.println("Tipologia Evento " +Evento.getIdTipologiaEvento());
		            if ( Evento.getIdTipologiaEvento() == EventoRitrovamento.idTipologiaDB){%>  
		            var luogo = "<%=(EventoRitrovamento.getLuogoRitrovamento() != null) ? EventoRitrovamento.getLuogoRitrovamento().replaceAll("\"", " ") : "" %>";
		            var data = '<%=toDateString(EventoRitrovamento.getDataRitrovamento())%>';
		            var idComune = '<%=EventoRitrovamento.getIdComuneRitrovamento()%>';
		            var idProprietario = '<%=EventoRitrovamento.getIdProprietario()%>';
		            var idDetentore = '<%=EventoRitrovamento.getIdDetentore()%>';
		           // alert(idDetentore);
		            $("#luogoRitrovamento").val(luogo);
		            $("#dataRitrovamento").val(data);
		            $("#idComuneRitrovamento").val(idComune);
		            $("#idProprietario").val(idProprietario);
		            $("#idDetentore").val(idDetentore);

		         //  	alert(	$("#idDetentore").val());
		           	if (idDetentore < 0){
		           		$("#flagRitornoAProprietario").attr('checked', true);
		           		hideDetentoreCanile();

		           	}

		            //SE EventoRitrovamentoNonDenunciato
		            <%} else if ( Evento.getIdTipologiaEvento() == 41){%>  
		            var luogo = "<%=(EventoRitrovamentoNonDenunciato.getLuogoRitrovamentoNd() != null) ? EventoRitrovamentoNonDenunciato.getLuogoRitrovamentoNd().replaceAll("\"", " ") : "" %>";
		            var data = '<%=toDateString(EventoRitrovamentoNonDenunciato.getDataRitrovamentoNd())%>';
		            var idComune = '<%=EventoRitrovamentoNonDenunciato.getIdComuneRitrovamentoNd()%>';
		            var idDetentore = '<%=EventoRitrovamentoNonDenunciato.getIdDetentore()%>';
		           // alert(idDetentore);
		            $("#luogoRitrovamentoNd").val(luogo);
		            $("#dataRitrovamentoNd").val(data);
		            $("#idComuneRitrovamentoNd").val(idComune);
		           	$("#idDetentore").val(idDetentore);

		           	if (idDetentore < 0){
		           		$("#flagRitornoAProprietario").attr('checked', true);
		           		hideDetentoreCanile();

		           	}



					//SE EventoRestituzioneAProprietario
		            <%} 
		            
		            else if (Evento.getIdTipologiaEvento() == 13 || Evento.getIdTipologiaEvento() == 61) {%>
		            //alert('<%=toDateString(EventoAdozioneDaCanile.getDataAdozione())%>');
		            var luogo = '<%=EventoAdozioneDaCanile.getLuogoAdozione()%>';
		            var data = '<%=toDateString(EventoAdozioneDaCanile.getDataAdozione())%>';
		            var idComune = '<%=EventoAdozioneDaCanile.getIdComuneDestinazione()%>';
		            var idProprietario = '<%=EventoAdozioneDaCanile.getIdProprietario()%>';
					var idAsl = '<%=EventoAdozioneDaCanile.getIdAslDestinatariaAdozione()%>'
						
		            $("#dataAdozione").val(data);
		            $("#idComuneDestinazione").val(idComune);
		            $("#idAslDestinatariaAdozione").val(idAsl);
		            $("#idProprietario").val(idProprietario);
		            
		            var proprietario = '<%=(EventoAdozioneDaCanile.getNuovoProprietario()!=null && EventoAdozioneDaCanile.getNuovoProprietario().getRagioneSociale().replaceAll("\'"," ")!=null)?(EventoAdozioneDaCanile.getNuovoProprietario().getRagioneSociale().replaceAll("\'"," ")):("")%>';
					$("#datiproprietario").html("");
					$("#datiproprietario").append(proprietario);
		            
		            <%}else if (Evento.getIdTipologiaEvento() == 19) {%> //Adozione da colonia
		            //alert("adozione");
		            var luogo = '<%=EventoAdozioneDaColonia.getLuogoAdozione()%>';
		            var data = '<%=toDateString(EventoAdozioneDaColonia.getDataAdozioneColonia())%>';
		            var idComune = '<%=EventoAdozioneDaColonia.getIdComuneDestinazione()%>';
		            var idProprietario = '<%=EventoAdozioneDaColonia.getIdProprietario()%>';
					var idAsl = '<%=EventoAdozioneDaColonia.getIdAslDestinatariaAdozione()%>'
					var proprietario = '<%=(EventoAdozioneDaColonia.getNuovoProprietario()!=null && EventoAdozioneDaColonia.getNuovoProprietario().getRagioneSociale()!=null)?(EventoAdozioneDaColonia.getNuovoProprietario().getRagioneSociale().replaceAll("\'"," ")):("")%>';
					$("#datiproprietario").html("");
					$("#datiproprietario").append(proprietario);
					
						
		            $("#dataAdozioneColonia").val(data);
		            $("#idComuneDestinazione").val(idComune);
		            $("#idAslDestinatariaAdozione").val(idAsl);
		            $("#idProprietario").val(idProprietario);
		            
		            <%}else if (Evento.getIdTipologiaEvento() == 7 || Evento.getIdTipologiaEvento() == 59) {%>
		         
					var data = '<%=toDateString(EventoCessione.getDataCessione())%>';
		            var idProprietario = '<%=EventoCessione.getIdProprietario()%>';
		            $("#idProprietario").val(idProprietario);
		            $("#dataCessione").val(data);
		            var proprietario = '<%=(EventoCessione.getNuovoProprietario()!=null && EventoCessione.getNuovoProprietario().getRagioneSociale()!=null)?(EventoCessione.getNuovoProprietario().getRagioneSociale().replaceAll("\'"," ")):("")%>';
					$("#datiproprietario").html("");
					$("#datiproprietario").append(proprietario);
					
		        //    $("#dataCessione").val(data);
		            
		            <%}else if (Evento.getIdTipologiaEvento() == 15) {
		            %>
		          // alert("presa");
		          	var propDati = '';
					var data = '<%=toDateString(EventoPresaInCaricoDaCessione.getDataPresaCessione())%>';
		            var idProprietario = '<%=EventoPresaInCaricoDaCessione.getIdProprietario()%>';
		            
		            <%if (cessioneaperta.getIdProprietario() > 0){
		            	Connection conn = GestoreConnessioni.getConnection();
						Operatore proprietarioDestinatarioRegistrazione = new Operatore();
						proprietarioDestinatarioRegistrazione
								.queryRecordOperatorebyIdLineaProduttiva(conn,
										cessioneaperta.getIdProprietario());
						GestoreConnessioni.freeConnection(conn);
						%>
						 propDati = "<%=proprietarioDestinatarioRegistrazione.getRagioneSociale().replaceAll("\"", "").replaceAll("'", "").replaceAll("\n", "")+ " - " + proprietarioDestinatarioRegistrazione.getCodFiscale()%>";
		            <%}else{%>
		            propDati = '<%=(cessioneaperta.getNome()!= null && !("").equals(cessioneaperta.getNome())) ? (cessioneaperta.getNome().replaceAll("'", "").replaceAll("\n", "") + " " + cessioneaperta.getCognome().replaceAll("'", "").replaceAll("\n", "") + " - " + cessioneaperta.getCodiceFiscale() +
		            		" via " + ((cessioneaperta.getIndirizzo()!=null)?(cessioneaperta.getIndirizzo().replaceAll("'", "").replaceAll("\n", "")):("")) + "," + comuniList.getSelectedValue(cessioneaperta.getIdComune()).replaceAll("'", "").replaceAll("\r\n", "  ") + " --  " + provinceList.getSelectedValue(cessioneaperta.getIdProvincia()) ) 
		            		: cessioneaperta.getDestinatarioCessioneVecchiaCanina().replaceAll("'", "").replaceAll("\r\n", "  ") %>';
		            <%} 
		            %>


		            if (idProprietario == -1){			           
			          //  alert("<%=cessioneaperta.getIdProprietario()%>");
			            idProprietario = '<%=cessioneaperta.getIdProprietario()%>';
		            }
		            //alert(propDati);
		          	$("#idProprietario").val(idProprietario);		
		           			
		            $("#dataPresaCessione").val(data);
		            
		            $("#datiproprietario").text(propDati);
		            
		            var proprietario = '<%=(EventoPresaInCaricoDaCessione.getNuovoProprietario()!=null && EventoPresaInCaricoDaCessione.getNuovoProprietario().getRagioneSociale()!=null)?(EventoPresaInCaricoDaCessione.getNuovoProprietario().getRagioneSociale().replaceAll("\'"," ")):("")%>';
		        	if(proprietario!=null && proprietario!="")
		            {
		        		$("#datiproprietario").html("");
		        		$("#datiproprietario").html(proprietario);
		            }
		            
		            <%}else if (Evento.getIdTipologiaEvento() == 47) {
		            %>
		          // alert("presa");
		          	var propDati = '';
					var data = '<%=toDateString(EventoPresaInCaricoDaAdozioneFuoriAsl.getDataPresaAdozione())%>';
		            var idProprietario = '<%=EventoAdozioneFuoriAsl.getIdProprietario()%>';
		            
<%
		            if (EventoAdozioneFuoriAsl.getIdProprietario() > 0)
		            {
		            	Connection conn = GestoreConnessioni.getConnection();
						Operatore proprietarioDestinatarioRegistrazione = new Operatore();
						proprietarioDestinatarioRegistrazione.queryRecordOperatorebyIdLineaProduttiva(conn,EventoAdozioneFuoriAsl.getIdProprietario());
						GestoreConnessioni.freeConnection(conn);
%>
						 proprietario = '<%=proprietarioDestinatarioRegistrazione.getRagioneSociale().replaceAll("\"", "").replaceAll("'", "") + " " %>';
						 propDati =     "<%=proprietarioDestinatarioRegistrazione.getRagioneSociale().replaceAll("'", "").replaceAll("\"", "").replaceAll("\n", "")+ " - " + proprietarioDestinatarioRegistrazione.getCodFiscale()%>";
<%
		            }
		            else if (adozioneaperta.getIdProprietario() > 0)
		            {
		            	Connection conn = GestoreConnessioni.getConnection();
						Operatore proprietarioDestinatarioRegistrazione = new Operatore();
						proprietarioDestinatarioRegistrazione.queryRecordOperatorebyIdLineaProduttiva(conn,adozioneaperta.getIdProprietario());
						GestoreConnessioni.freeConnection(conn);
%>
						 proprietario = '<%=proprietarioDestinatarioRegistrazione.getRagioneSociale().replaceAll("\"", "").replaceAll("'", "") + " " %>';
						 propDati =     "<%=proprietarioDestinatarioRegistrazione.getRagioneSociale().replaceAll("'", "").replaceAll("\"", "").replaceAll("\n", "")+ " - " + proprietarioDestinatarioRegistrazione.getCodFiscale()%>";
<%
		            }
		            else
		            {
%>
		            propDati = '<%=adozioneaperta.getNome().replaceAll("'", "").replaceAll("\n", "") + " " + adozioneaperta.getCognome().replaceAll("'", "").replaceAll("\n", "") + " - " + adozioneaperta.getCodiceFiscale()%>';
<%
					} 
%>


		            if (idProprietario == -1){			           
			          //  alert("<%=adozioneaperta.getIdProprietario()%>");
			            idProprietario = '<%=adozioneaperta.getIdProprietario()%>';
		            }
		            //alert(propDati);
		          	$("#idProprietario").val(idProprietario);		
		           			
		            $("#dataPresaAdozione").val(data);
		           	$("#datiproprietario").html("");
					$("#datiproprietario").append(propDati);
		           	
		            
		            <% }
		            
		            else if (Evento.getIdTipologiaEvento() == 55) { %>

		            var idProprietario = '<%=EventoTrasferimentoSindaco.getIdProprietario()%>';
		            var idDetentore = '<%=EventoTrasferimentoSindaco.getIdDetentore()%>';
		            var data = '<%=toDateString(EventoTrasferimentoSindaco.getDataTrasferimento())%>';
		          //  alert(data);
		            $("#idProprietario").val(idProprietario);
		            $("#idDetentore").val(idDetentore);
		            $("#dataTrasferimento").val(data);
		            var proprietario = '<%=(EventoTrasferimentoSindaco.getProprietario()!=null && EventoTrasferimentoSindaco.getProprietario().getRagioneSociale()!=null)?(EventoTrasferimentoSindaco.getProprietario().getRagioneSociale().replaceAll("\'"," ")):("")%>';
					$("#datiproprietario").html("");
					$("#datiproprietario").append(proprietario);
		            
		            <%}
		            else if (Evento.getIdTipologiaEvento() == 16) { %>

		            var idProprietario = '<%=EventoTrasferimento.getIdProprietario()%>';
		            var data = '<%=toDateString(EventoTrasferimento.getDataTrasferimento())%>';
		          //  alert(data);
		            $("#idProprietario").val(idProprietario);
		            $("#dataTrasferimento").val(data);
		            var proprietario = '<%=(EventoTrasferimento.getProprietario()!=null && EventoTrasferimento.getProprietario().getRagioneSociale()!=null)?(EventoTrasferimento.getProprietario().getRagioneSociale().replaceAll("\'"," ")):("")%>';
					$("#datiproprietario").html("");
					$("#datiproprietario").append(proprietario);
		            
		            <%}
		            else if (Evento.getIdTipologiaEvento() == 40) {%>

		            var idProprietario = '<%=EventoTrasferimentoFuoriRegioneSoloProprietario.getIdProprietario()%>';
		            var data = '<%=toDateString(EventoTrasferimentoFuoriRegioneSoloProprietario.getDataTrasferimentoFuoriRegioneProprietario())%>';
		          // alert(idProprietario);
		            $("#idProprietario").val(idProprietario);
		            $("#dataTrasferimentoFuoriRegioneProprietario").val(data);
		            
		            
		            var proprietario = '<%=(EventoTrasferimentoFuoriRegioneSoloProprietario.getProprietarioFuoriRegione()!=null && EventoTrasferimentoFuoriRegioneSoloProprietario.getProprietarioFuoriRegione().getRagioneSociale()!=null)?(EventoTrasferimentoFuoriRegioneSoloProprietario.getProprietarioFuoriRegione().getRagioneSociale().replaceAll("\'"," ")):("")%>';
					$("#datiproprietario").html("");
			        $("#datiproprietario").append(proprietario);
		            
		            <%}
		            
		            
		            //Accettazione Cessione Import
		            else if (Evento.getIdTipologiaEvento() == 52) {
		            %>
			        

		            var proprietario = '<%=(EventoPresaCessioneImport.getProprietario()!=null && EventoPresaCessioneImport.getProprietario().getRagioneSociale()!=null)?(EventoPresaCessioneImport.getProprietario().getRagioneSociale().replaceAll("\'"," ")):("")%>';
		            var idProprietario = '<%=EventoPresaCessioneImport.getIdProprietario()%>';
		            
		          //  alert(proprietario);
		          //  alert(data);
		            $("#datiproprietario").html("");
		            $("#datiproprietario").append(proprietario);
		            $("#idProprietario").val(idProprietario);
		            
					<%}else if (Evento.getIdTipologiaEvento() == 17) {%>
		        

		            var idProprietario = '<%=EventoRientroFuoriRegione.getIdProprietario()%>';
		            var data =  '<%=toDateString(EventoRientroFuoriRegione.getDataRientroFR())%>';
		            var idAsl = '<%=EventoRientroFuoriRegione.getIdAsl()%>';
		            var idDetentore = '<%=EventoRientroFuoriRegione.getIdDetentore()%>';
		            var proprietario = '<%=(EventoRientroFuoriRegione.getProprietario()!=null && EventoRientroFuoriRegione.getProprietario().getRagioneSociale()!=null)?(EventoRientroFuoriRegione.getProprietario().getRagioneSociale().replaceAll("\'"," ")):("")%>';
		           
		            
		          //  alert(proprietario);
		          //  alert(data);
		            $("#idProprietario").val(idProprietario);
		            $("#datiproprietario").html("");
		            $("#datiproprietario").append(proprietario);
		            $("#dataRientroFR").val(data);
		            $("#idAsl").val(idAsl);
		            $("#idDetentore").val(idDetentore);
		            
		            <%}
					
					
					
					
					
					
					else if (Evento.getIdTipologiaEvento() == 18) {%>
		            var idDetentore = '<%=EventoCambioDetentore.getIdDetentore()%>';
		           <%if (EventoCambioDetentore.getDataModificaDetentore() != null) {%>
		           	 var data = '<%=toDateString(EventoCambioDetentore.getDataModificaDetentore())%>';
		            <% }else {%>
						var data = "";
			        <%    }%>
		            $("#idDetentore").val(idDetentore);
		            $("#dataModificaDetentore").val(data);

		            <%}else if (Evento.getIdTipologiaEvento() == 45) {%>

		            var idVecchioDetentore = '<%=animale.getProprietario().getRagioneSociale().replaceAll("\"", "").replaceAll("'", "").replaceAll("\n", "")%>';
			           // alert(idDetentore);
			          
			        $("#idVecchioDetentore").val(idVecchioDetentore);
			        $("#idVecchioDetentore").attr('readonly', 'readonly');
		           // alert('secondo if');
		            var idDetentore = '<%=(EventoRestituzioneAProprietario.getIdDetentore() > 0) ? EventoRestituzioneAProprietario.getIdDetentore() : -1 %>';
		           <%if (EventoRestituzioneAProprietario.getDataRestituzione() != null) {%>
		           	 var data = '<%=toDateString(EventoRestituzioneAProprietario.getDataRestituzione())%>';
		            <% }else {%>
						var data = "";
			        <%    }%>
		            $("#idDetentore").val(idDetentore);
		            $("#dataRestituzione").val(data);
		            
		            <%}else if (Evento.getIdTipologiaEvento() == EventoCattura.idTipologiaDBRicattura) {%>
		           // alert("ricattura");
		            var luogo = '<%=EventoCattura.getLuogoCattura()%>';
		            var data = '<%=toDateString(EventoCattura.getDataCattura())%>';
		            var idDetentore = '<%=(EventoCattura.getIdDetentore() > 0) ? EventoCattura.getIdDetentore() : reimmissionePrecedente.getIdDetentorePrecedente()%>';
		         //   alert('aaa' +idDetentore);
		            var idComune = '<%=EventoCattura.getIdComuneCattura()%>';
			        var indirizzo = '<%=EventoCattura.getIndirizzoCattura()%>';
				    var idProprietario = '<%=animale.getIdProprietario()%>';
				   // alert('bbb' + idProprietario);
				    var verbaleCattura = '<%=EventoCattura.getVerbaleCattura()%>';
		            
		            $("#luogoCattura").val(luogo);
		            $("#dataCattura").val(data);
		            $("#idComuneCattura").val(idComune);
		            $("#idDetentore").val(idDetentore);
		            $("#indirizzoCattura").val(indirizzo);
		            $("#idProprietarioSindaco").val(idProprietario);
		            $("#verbaleCattura").val(verbaleCattura);
		            visualizzaMunicipalita();
		           // alert( $("#idProprietarioSindaco").val());
		            <%}else if (Evento.getIdTipologiaEvento() == 31) {%>
		            var idDetentore = '<%=EventoTrasferimentoCanile.getIdDetentore()%>';
		            var data = '<%=toDateString(EventoTrasferimentoCanile.getDataTrasferimentoCanile())%>';
		            $("#idDetentore").val(idDetentore);
		            $("#dataTrasferimentoCanile").val(data);
		            <%}else if (Evento.getIdTipologiaEvento() == 14) {%>
		            var idDetentore = '<%=EventoRestituzioneACanile.getIdDetentore()%>';
		            var data = '<%=toDateString(EventoRestituzioneACanile.getDataRestituzioneCanile())%>';
		            var idProprietario = '<%=EventoRestituzioneACanile.getIdProprietario()%>';
		            $("#idDetentore").val(idDetentore);
		            $("#dataRestituzioneCanile").val(data);
		            $("#idProprietario").val(idProprietario);
		            var proprietario = '<%=(EventoRestituzioneACanile.getNuovoProprietarioSindaco()!=null && EventoRestituzioneACanile.getNuovoProprietarioSindaco().getRagioneSociale()!=null)?(EventoRestituzioneACanile.getNuovoProprietarioSindaco().getRagioneSociale().replaceAll("\'"," ")):("")%>';
					$("#datiproprietario").html("");
					$("#datiproprietario").append(proprietario);
					
		            
		            <%}else if (Evento.getIdTipologiaEvento() == 42) {%>
		            var idDetentore = '<%=EventoRientroFuoriStato.getIdDetentore()%>';
		            var data = '<%=toDateString(EventoRientroFuoriStato.getDataRientroFuoriStato())%>';
		            var idProprietario = '<%=EventoRientroFuoriStato.getIdProprietario()%>';
		            $("#idDetentore").val(idDetentore);
		            $("#dataRestituzioneCanile").val(data);
		            $("#idProprietario").val(idProprietario);
		            var proprietario = '<%=(EventoRientroFuoriStato.getProprietario()!=null && EventoRientroFuoriStato.getProprietario().getRagioneSociale()!=null)?(EventoRientroFuoriStato.getProprietario().getRagioneSociale().replaceAll("\'"," ")):("")%>';
					$("#datiproprietario").html("");
					$("#datiproprietario").append(proprietario);

		            
		            <%}else if (Evento.getIdTipologiaEvento() == EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB) {%>
		            var data = '<%=toDateString(EventoPresaInCaricoDaAdozioneFuoriAsl.getDataPresaAdozione())%>';
		            var idProprietario = '<%=EventoPresaInCaricoDaAdozioneFuoriAsl.getIdProprietario()%>';
		          
		            $("#dataPresaAdozione").val(data);
		            $("#idProprietario").val(idProprietario);
		            var proprietario = '<%=(EventoPresaInCaricoDaAdozioneFuoriAsl.getNuovoProprietario()!=null && EventoPresaInCaricoDaAdozioneFuoriAsl.getNuovoProprietario().getRagioneSociale()!=null)?(EventoPresaInCaricoDaAdozioneFuoriAsl.getNuovoProprietario().getRagioneSociale().replaceAll("\'"," ")):("")%>';
					$("#datiproprietario").html("");
					$("#datiproprietario").append(proprietario);

		            
		            <%}else if (Evento.getIdTipologiaEvento() == EventoAdozioneFuoriAsl.idTipologiaDB) {%>
		            var data = '<%=toDateString(EventoAdozioneFuoriAsl.getDataAdozioneFuoriAsl())%>';
		            var idProprietario = '<%=EventoAdozioneFuoriAsl.getIdProprietario()%>';
		          
		            $("#dataAdozioneFuoriAsl").val(data);
		            $("#idProprietario").val(idProprietario);
		            var proprietario = '<%=(EventoAdozioneFuoriAsl.getNuovoProprietario()!=null && EventoAdozioneFuoriAsl.getNuovoProprietario().getRagioneSociale()!=null)?(EventoAdozioneFuoriAsl.getNuovoProprietario().getRagioneSociale().replaceAll("\'"," ")):("")%>';
					$("#datiproprietario").html("");
					$("#datiproprietario").append(proprietario);

		            
		            <%}else if (Evento.getIdTipologiaEvento() == EventoRilascioPassaporto.idTipologiaRinnovoDB) {%>
		             vecchioPassaporto = '<%=toHtml (animale.getNumeroPassaporto())%>';
		            
		         // 	alert(vecchioPassaporto);
		            $("#numeroPassaporto").val(vecchioPassaporto);
		            

		            
		            <%}else if (Evento.getIdTipologiaEvento() == EventoSterilizzazione.idTipologiaDB) {%>
		            var dataSterilizzazione = '<%=(EventoSterilizzazione.getDataSterilizzazione() != null) ? toDateString(EventoSterilizzazione.getDataSterilizzazione()) : ""%>';
		            
		          
		            $("#dataSterilizzazione").val(dataSterilizzazione);
		            

		            
		            //Se la registrazione è decesso e lo stato dell'animale non è il pregresso "Import/Cessione/Da Importare" poichè sono animali di cui non abbiamo il detentore
		            <%}else if (Evento.getIdTipologiaEvento() == EventoDecesso.idTipologiaDB && animale.getStato()!=76) {
			            Operatore detentore = animale.getDetentore();
			            
			            if (detentore.getListaStabilimenti().size() > 0){
				            Stabilimento stabDet = (Stabilimento) detentore.getListaStabilimenti().get(0);
				            Indirizzo indstab = stabDet.getSedeOperativa();
				            LineaProduttiva lpDet = (LineaProduttiva) stabDet.getListaLineeProduttive().get(0);
				            %>
				            <%=indstab.getIdProvincia()%>
				            var idComuneDetentore = <%=indstab.getComune()%>;
				            var idProvincia = <%=indstab.getIdProvincia()%>;
				            var indirizzo = '<%=indstab.getVia().replaceAll("'", "").replaceAll("\n", "")%>';
				            $("#idComuneDecesso").val(idComuneDetentore);
				            $("#idProvinciaDecesso").val(idProvincia);
				            $("#indirizzoDecesso").val(indirizzo);
			            <%}%>
	
			           //alert( $("#idComuneDecesso").val());
			            
			            <%}
			            else if (Evento.getIdTipologiaEvento() == EventoPresaCessioneImport.idTipologiaDB) {%>
			            //alert("rientro");

			            var idProprietario = '<%=EventoPresaCessioneImport.getIdProprietario()%>';
			            var data =  '<%=toDateString(EventoPresaCessioneImport.getDataPresaCessioneImport())%>';
			           // alert(idProprietario);
			          //  alert(data);
			            $("#idProprietario").val(idProprietario);
			            $("#dataPresaCessioneImport").val(data);

			            
			            <%} else if (Evento.getIdTipologiaEvento() == EventoPrelievoLeishmania.idTipologiaDB) {%>
						//	showVet(1);

			         
			            
			            <%} else if (Evento.getIdTipologiaEvento() == EventoSbloccoAnimale.idTipologiaDB) {%>

			            var idProprietario = '<%=EventoSbloccoAnimale.getIdProprietario()%>';
			            var idDetentore = '<%=EventoSbloccoAnimale.getIdDetentore()%>';
			            var data = '<%=toDateString(EventoSbloccoAnimale.getDataSblocco())%>';
			            var idStato = '<%=EventoSbloccoAnimale.getIdStato()%>';
			          //  alert(data);
			            $("#idProprietario").val(idProprietario);
			         //   alert(idDetentore);
			            if (idDetentore > 0)
			            	$("#idDetentore").val(idDetentore);
			            else
			            	$("#idDetentore").val(idProprietario);
			            $("#dataSblocco").val(data);
			            $("#idStato").val(idStato);
			            
			            <%}else if (Evento.getIdTipologiaEvento() == EventoReimmissione.idTipologiaDB){
				            System.out.println("Reimmissione");%>  
				         	  var idDetentore = '<%=EventoReimmissione.getIdDetentore()%>';
				         	  //alert(idDetentore);
					          var dataReimmissione = '<%= toDateString(EventoReimmissione.getDataReimmissione()) %>';
					            $("#dataReimmissione").val(dataReimmissione);
					           	$("#idDetentore").val(idDetentore);
					        	
					            <%}

		            
		            %>

		            
		     
		   //         alert(	$("#idDetentore").val());
		            
		        
		            
		           
		          
		        },
		        complete: function() {
		        	
		        	hideById();
		        	$('#idVaccinazioneAntirabbica').hide();
		        }
		    });
	}

function hideById() {
if ($("#idTipologiaSoggettoSterilizzante").children("option:selected").val() == 1){

	 $('#idAslEsecutrice').show();
	 $('#veterinari').show();
	 $('#idSoggettoSterilizzante').hide();
}else if ($("#idTipologiaSoggettoSterilizzante").children("option:selected").val() == 2){
	 $('#idAslEsecutrice').hide();
	 $('#veterinari').hide();
	 $('#idSoggettoSterilizzante').show();
}else{
	 $('#idAslEsecutrice').hide();
	 $('#veterinari').hide();
	 $('#idSoggettoSterilizzante').hide();
}

if (!($(invia).is(":visible")) && showInvia){
	$('#invia').show();
}else if (($(invia).is(":visible")) && !showInvia){
	$('#invia').hide();
}

}

/*

function hideDetentoreCanile(){
	
//alert('hideDetentoreCanile');
//alert($('#flagRitornoAProprietario').is(':checked'));
//alert($("#datiproprietario"));
	if ($('#flagRitornoAProprietario').is(':checked')){
		
		$("#datiproprietario").html("");
		$('#idDetentoreLink').hide();
		$('#idPulisci').hide();
		$('#idDetentore').val('-1');
	}else{
		$('#idDetentoreLink').show();
		$('#idPulisci').show();
	    
	}
}
*/


function hideDetentoreCanile(){
//alert('hideDetentoreCanile');
//alert($('#flagRitornoAProprietario').is(':checked'));
	if ($('#flagRitornoAProprietario').is(':checked')){
		$('#idDetentoreLink').hide();
		$('#idPulisci').hide();
		$('#idDetentore').val('-1');
	}else{
		$('#idDetentoreLink').show();
		$('#idPulisci').show();
	}
}


function hideNuoveInformazioniSblocco(){
	//alert('hideDetentoreCanile');
	//alert($('#flagRitornoAProprietario').is(':checked'));
		if ($('#flagRipristinaStatoPrecendente').is(':checked')){
			$('#idProprietarioLink').hide();
			$('#idDetentoreLink').hide();
			$('#idStato').hide();
			$('#idDetentore').val('-1');
			$('#idProprietario').val('-1');
		}else{
			$('#idProprietarioLink').show();
			$('#idDetentoreLink').show();
			$('#idStato').show();
		}
	}
	
function apriPopup(){

	 $("#dialogMunicipalita").dialog('open');
	
}	
	

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
                $(this).dialog('close');    
            },
            'Rinuncia': function() {
            	$("#idTipologiaEvento").val(-1);
            	popolaCampi();
                $(this).dialog('close');
               // $('#opener').button('refresh')
            }
        }
    });


	$("#dialogErrorWKF").dialog({
        autoOpen: false,
        resizable: false,
        modal: true,
        title: 'Errore salvataggio registrazione!',
        close: function () {
           // $('#opener').button('refresh');
            },
        buttons: {
            'OK': function() {
                $(this).dialog('close');    
            }
        }
    });


	
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
 
     

   <%
   String value = "-1";
   String contributo = "false";
   String dataToFill = "";
   if ((session.getAttribute("caller") != null && 
	       ApplicationProperties.getProperty("VAM_ID").equals(session.getAttribute("caller")) &&
		   (session.getAttribute("idTipologiaEvento") != null || request.getParameter("idTipologiaEvento")!=null) )
   ||
   
   
   (request.getAttribute("fromPassaporto")!=null && !request.getAttribute("fromPassaporto").equals("") && !request.getAttribute("fromPassaporto").equals("null")))
   	{
	    value = ((String) request.getParameter("idTipologiaEvento") != null ) ? (String)request.getParameter("idTipologiaEvento") : (String) request.getAttribute("idTipologiaEvento") ;
	    dataToFill = ((String) request.getParameter("dataRegistrazione") != null) ? (String) request.getParameter("dataRegistrazione") : "";

	    if (request.getParameter("contributo") != null)
	   		 contributo = (String) request.getParameter("contributo"); // != null se si può inserire solo registrazione di sterilizzazione per un cane detenuto in asl operatore corrente
	    
	   }%>

if ($("#idTipologiaEvento").children("option:selected").val() > 0){
   popolaCampi();
   checkIdProprietario();
   if('<%=Evento.getIdTipologiaEvento()%>' == '46' || '<%=Evento.getIdTipologiaEvento()%>' == '7'  )
	{
		document.getElementById('idComune').disabled='disabled';
		document.getElementById('idComune').value='';
		document.getElementById('idProvincia').value='';
		document.getElementById('idProvincia').disabled='disabled';
		document.getElementById('indirizzo').disabled='disabled';
		document.getElementById('indirizzo').value='';
		document.getElementById('cap').readOnly='readOnly';
		document.getElementById('cap').value='';
	}
   
   if('<%=Evento.getIdTipologiaEvento()%>' == '43')
	{
	   if(document.getElementById('idComuneModificaResidenzaSelect')!=null)
		   {
		document.getElementById('idComuneModificaResidenzaSelect').disabled='disabled';
		document.getElementById('idComuneModificaResidenzaSelect').value='';
		   }
	   if(document.getElementById('idProvinciaModificaResidenzaSelect')!=null)
	   {
		document.getElementById('idProvinciaModificaResidenzaSelect').value='';
		document.getElementById('idProvinciaModificaResidenzaSelect').disabled='disabled';
	   }
	   $("#via").attr('readonly', 'readonly');
	   $("#cap").attr('readonly', 'readonly');
		document.getElementById('via').value='';
		document.getElementById('cap').value='';
	}
   
   
   
}
  
  // alert(dataToFill1);
   <%if (!(("-1").equals(value))){
     %>
    $("#idTipologiaEvento").val(<%=value%>);
    $("#idTipologiaEventoVam").val(<%=value%>);
    $('#idTipologiaEvento').attr('disabled', true);
    if ( $('#'+name_data_registrazione_to_check_vam).val() == null || $('#'+name_data_registrazione_to_check_vam).val() == '' )
   	{
    	if(name_data_registrazione_to_check_vam!='dataRilascioPassaporto')
   			$('#'+name_data_registrazione_to_check_vam).val('<%=dataToFill%>');
   	}
   	if(document.getElementById('idTipologiaEvento').value=='<%=EventoRilascioPassaporto.idTipologiaDB%>' || document.getElementById('idTipologiaEvento').value=='<%=EventoRilascioPassaporto.idTipologiaRinnovoDB%>')
   	{
   		checkRabbia();
   	}
    if ( $("#idTipologiaEventoVam").val() != '<%=EventoSterilizzazione.idTipologiaDB%>' &&
   //Caso Prelievo Leishmania da CC di Vam 
    	!( $("#idTipologiaEventoVam").val() == '<%=EventoPrelievoLeishmania.idTipologiaDB%>' && <%=dataToFill==null || dataToFill.equals("") || dataToFill.equals("null")%>)){
    	<%
    	if(request.getAttribute("fromPassaporto")==null || request.getAttribute("fromPassaporto").equals("") || request.getAttribute("fromPassaporto").equals("null"))
    	{
    	%>
    	if(name_data_registrazione_to_check_vam!='dataRilascioPassaporto')
    		$('#'+name_data_registrazione_to_check_vam+'_img').css("visibility", "hidden");
    	<%
    	}
    	%>
    }
   // document.domain = 'anagrafecaninacampania.it';
    <%}%>
    
	
$("#idTipologiaEvento").change(function() {

	if ($("#idTipologiaEvento").children("option:selected").val() > 0)
	{	
		var tipologiaEventoScelta=$("#idTipologiaEvento").children("option:selected").val();
		//Flusso 238
		var isPassaporto=tipologiaEventoScelta=='<%=EventoRilascioPassaporto.idTipologiaDB%>';
		var id_canile=document.getElementById("id_canile").value;
		var id_stabilimento_gisa=document.getElementById("id_stabilimento_gisa").value;
		var notFromVam = (id_canile=="" || id_canile=="null") && (id_stabilimento_gisa=="" || id_stabilimento_gisa=="null");
		
		//Flusso 238
		if(isPassaporto && notFromVam)
		{
			document.getElementById("idTipologiaEvento").value='<%=Evento.getIdTipologiaEvento()%>';
			alert("Per inserire una registrazione di rilascio/rinnovo passaporto si deve effettuare il login partendo da Vam per selezionare l'ambulatorio in cui si sta lavorando");
		}
		else
		{
			$('#doContinue').val('false');
			$("form").submit();
		}
	}
	else
	{
		 $("#datireg").html("");
	}

});
});



var array_pratiche = new Array();

function visualizzaPratiche()
{


	if (<%=animale.getIdSpecie()%> == 3){
		document.addAnimale.flagContributoRegionale.checked=false;
		alert('Attenzione, non puoi inserire un furetto in un progetto di sterilizzazione');
	}

	
	if (document.addAnimale.flagContributoRegionale.checked){

			if((document.addAnimale.dataSterilizzazione.value == "")||(document.addAnimale.dataSterilizzazione.value == null)){
						document.addAnimale.flagContributoRegionale.checked=false;
						alert('Selezionare prima la data di sterilizzazione');
		
			}
					else{

						
			//estrae tutte le pratiche per cui esiste aleno una posizione aperta
			PraticaList.getListData(document.addAnimale.dataSterilizzazione.value,<%=User.getSiteId()/*animale.getIdAslRiferimento()*/%>,<%=animale.getIdDetentore()%>, <%=animale.getIdSpecie()%>, valorizzaLista);
			}
			
			
	
	
	}
	else{
		document.getElementById("pratica_contributo").style.visibility="hidden";
		//document.addAnimale.pratica.value = -1;
		}
  
}


function valorizzaLista(listaPratiche)
{
//alert('lista');
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
	// alert(listaPratiche.length);
  while(i < listaPratiche.length){
		
			 array_pratiche[listaPratiche[i].id]=listaPratiche[i];

			 var NewOpt = document.createElement('option');
			 NewOpt.value = listaPratiche[i].id;
		 	 NewOpt.text =  "Decreto numero "+listaPratiche[i].numero_decreto_pratica + " del "+ listaPratiche[i].data_decreto_stringa +" - Cani padronali restanti "+ listaPratiche[i].cani_restanti_padronali +" - Cani catturati restanti "+listaPratiche[i].cani_restanti_catturati +" - Gatti padronali restanti "+ listaPratiche[i].gatti_restanti_padronali +" - Gatti catturati restanti "+listaPratiche[i].gatti_restanti_catturati + " - "+ listaPratiche[i].elenco_comuni ;
			
			if(listaPratiche[i].elencoCanili.length != 0 ){

				for (var k=0; k<listaPratiche[i].elencoCanili.length; k++) {
				 	NewOpt.text =  "Decreto numero "+listaPratiche[i].numero_decreto_pratica + " del "+ listaPratiche[i].data_decreto_stringa +" - Cani padronali restanti "+ listaPratiche[i].cani_restanti_padronali +" - Cani catturati restanti "+listaPratiche[i].cani_restanti_catturati  +" - Gatti padronali restanti "+ listaPratiche[i].gatti_restanti_padronali +" - Gatti catturati restanti "+listaPratiche[i].gatti_restanti_catturati + " - "+ listaPratiche[i].elencoCanili[k];	
				}
			}
			
			
			//controllo dell'id selezionato nel caso di salva e clona
			// if(document.addAnimale.oldPratica.value == NewOpt.value){
			 //	NewOpt.selected = true;
			// }
			 
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

function checkImportoSmarrimento(){
	//alert("importo");
	if ($('#importoSmarrimento').val() != ""){
		var valore = $('#importoSmarrimento').val();
	//	alert (valore);
		$('#importoSmarrimento').val(valore.toString().replace(/\,/g, '.'));
	//	alert(valore.toString().replace(/\,/g, '.'));
	}
}

function checkRegistroAggressori()
{
<% 
	if (Evento.getIdTipologiaEvento() == EventoAggressione.idTipologiaDB )
	{
%>
		if(document.getElementById('prevedibilitaEvento').value==2 &&
		   document.getElementById('aggressioneRipetuta').value==2 &&
		   (document.getElementById('tagliaAggressore').value==3 || document.getElementById('tagliaAggressore').value==4 ) &&
		   document.getElementById('alterazioniComportamentali').value==2 &&
		   document.getElementById('analisiGestione').value==2)
			{
				return true;
			}

<%
	}
	else if(Evento.getIdTipologiaEvento() == EventoMorsicatura.idTipologiaDB )
	{
%>
		if(document.getElementById('tipologia').value==1 &&
		   document.getElementById('prevedibilitaEvento').value==2 &&
		   document.getElementById('morsoRipetuto').value==2 &&
		   (document.getElementById('tagliaAggressore').value==3 || document.getElementById('tagliaAggressore').value==4 ) &&
		   (document.getElementById('categoriaVittima').value==1 || document.getElementById('categoriaVittima').value==3 || document.getElementById('categoriaVittima').value==4) &&
		   document.getElementById('tagliaVittima').value==1 &&
		   document.getElementById('alterazioniComportamentali').value==2 &&
		   document.getElementById('analisiGestione').value==2)
			{
				return true;
			}
		else if(document.getElementById('tipologia').value==2 &&
				calcoloPunteggioDehasse() >= 14)
				   {
						return true;
				   }
		else if(document.getElementById('tipologia').value==2 &&
				calcoloPunteggioDehasse() < 14 &&
		   document.getElementById('morsoRipetuto').value==2 &&
		   document.getElementById('alterazioniComportamentali').value==2 &&
		   document.getElementById('analisiGestione').value==2)
		   {
				return true;
		   }
		
<%
	}
%>

	
	
}


function calcoloPunteggioDehasse()
{
	var punteggio = 0;
	var i=1;
	while(document.getElementById('punteggio'+i)!=null)
	{
		var punteggioTemp = parseFloat(document.getElementById('punteggio'+i).value);
		punteggio+=punteggioTemp;
			
		i++;
	}
	
	
	return punteggio;
}

function calcoloPunteggioDehasseAvviso()
{
	var testo = "";
	var punteggio = calcoloPunteggioDehasse();
	if(punteggio > 0)
	{
		testo = "La valutazione Dehasse ha riportato un punteggio pari a " + punteggio +".";
	}
	
	return testo;
}

function checkIdProprietario(){
	//alert($('#idProprietario').val());
//alert($('#idProprietario').val());
	if (!($('#idProprietario').val() == "") && !($('#idProprietario').val() == "-1")){
		//alert('in if');
		$('#nome').attr('disabled', true);
		$('#cognome').attr('disabled', true);
		$('#codiceFiscale').attr('disabled', true);
		$('#luogoNascita').attr('disabled', true);
		$('#dataNascita').attr('disabled', true);
		$('#indirizzo').attr('disabled', true);
		$('#idComune').attr('disabled', true);
		$('#idProvincia').attr('disabled', true);
		$('#idAslNuovoProprietario').attr('disabled', true);
		$('#numeroTelefono').attr('disabled', true);
		$('#docIdentita').attr('disabled', true);
		$('#idAslNuovoProprietarioSelect').attr('disabled', true);
		
		
		
}else{

	//alert($('#nome').is(':disabled'));
	if($('#nome').is(':disabled')){
	 $('#nome').attr('disabled', false);
	}
	 $('#cognome').attr('disabled', false);
	 $('#codiceFiscale').attr('disabled', false);
	 $('#luogoNascita').attr('disabled', false);
	 $('#dataNascita').attr('disabled', false);
	 $('#indirizzo').attr('disabled', false);
	 $('#idComune').attr('disabled', false);
	 $('#idProvincia').attr('disabled', false);
	 $('#idAslNuovoProprietario').attr('disabled', false);
	 $('#numeroTelefono').attr('disabled', false);
	 $('#docIdentita').attr('disabled', false);
}
}

function pulisciProprietario(){
	//alert('pulisci');
	$('#idProprietario').val('-1');
	$('#idDetentore').val('-1');
	//popolaCampi();
	$('#doContinue').val('false');
	 $("form").submit();
}


//DWR FUNCTION TO CHECK ANIMALE RICOVERATO

function checkRicovero(){
	var ricoverato = "";
	//alert($('#flagCattura').is(':checked'));
	
	//alert($('#'+name_data_registrazione_to_check_vam).val());
	//alert(document.getElementById(name_data_registrazione_to_check_vam).value);
	//alert(name_data_registrazione_to_check_vam);
	//alert($('#'+name_data_registrazione_to_check_vam).val());
	<%//System.out.println("Tipo registrazione: "+Evento.getIdTipologiaEvento());%>
	Animale.verificaRicovero('<%=(animale.getMicrochip() != null && !("").equals(animale.getMicrochip())) ? animale.getMicrochip() : animale.getTatuaggio()  %>', $('#'+name_data_registrazione_to_check_vam).val(), {
		callback:function(data) {
		ricoverato= data;
		//alert(data);
		},
		timeout:8000,
		async:false

		});
	return ricoverato;
}


function verificaInserimentoAnimale (campoIn)
{
	campo = campoIn ;
	var ok = true;
	if(campo.value.length==15)
		DwrUtil.verificaInserimentoAnimale(campo.value,<%=User.getUserId()%>,  {
			
			callback:function(value) {
				
				if (value.idEsito=='2' || value.idEsito=='4')
				{
					
					alert(value.descrizione);
					campo.value="";
					ok = false;
				}
				if (value.idEsito=='3' )
				{
					if ( document.forms[0].ruolo.value =='24')
					{
						
						alert( value.descrizione);
						campo.value="";
						ok = false;
					}
					
				}
				},
				timeout:8000,
				async:false

				});
	
	return ok;
}

function verificaInserimentoAnimaleCallBack(value)
{
	
	if (value.idEsito=='2' || value.idEsito=='4')
	{
		
		alert(value.descrizione);
		campo.value="";
		ok = false;
	}
	if (value.idEsito=='3' )
	{
		if ( document.forms[0].ruolo.value =='24')
		{
			
			alert( value.descrizione);
			campo.value="";
			ok = false;
		}
		
	}

	
}


function verificaPassaporto(campoIn){
	var ok = false;
	
//Flusso 238
if (campoIn.value != vecchioPassaporto && document.getElementById('flagSmarrimento')!=null && !document.getElementById('flagSmarrimento').checked) {
	
	 alert("E' possibile modificare il numero passaporto solo in caso di smarrimento");
	 campoIn.value = vecchioPassaporto;
}

if (campoIn.value == vecchioPassaporto && document.getElementById('flagSmarrimento')!=null && document.getElementById('flagSmarrimento').checked) {
	
	 alert("Per inserire lo smarrimento del passaporto è necessario assegnare il nuovo numero");
	 campoIn.value = vecchioPassaporto;
}

if (campoIn.value == vecchioPassaporto && document.getElementById('flagSmarrimento')!=null && !document.getElementById('flagSmarrimento').checked) {
	
	//Flusso 238: caso rinnovo passaporto senza modifica numero
	 ok = true;
}



if (!(campoIn.value == vecchioPassaporto) ) {

//Flusso 238: rimuovere ultimi due parametri in input
	EventoRilascioPassaporto.checkValorePassaporto(campoIn.value, <%=User.getSiteId()%> , <%=animale.getIdAnimale()%>,<%=(String)request.getSession().getAttribute("id_canile")%>,<%=(String)request.getSession().getAttribute("id_stabilimento_gisa")%>,  {
		callback:function(data) {
			
			if (data.idEsito == 4)
				ok = true;
			else {
				 alert(data.descrizione);
				 campoIn.value = '';
			}
			},
		timeout:8000,
		async:false

		});
	
}

	return ok;
}

function gestisciMedicoEsecutore (val){
	if (val=='1'){
		$('#idVeterinarioLLPP').hide();
		$('#idVeterinarioLLPP').attr('disabled', true);
		$('#idVeterinarioASL').attr('disabled', false);
		$('#idVeterinarioASL').show();
	}
	else if (val == '2'){
		$('#idVeterinarioASL').hide();
		$('#idVeterinarioASL').attr('disabled', true);
		$('#idVeterinarioLLPP').attr('disabled', false);
		$('#idVeterinarioLLPP').show();
	}
}

</script>


<script>

$(document).ready(function(){
	
	 $('#dataRilascioPassaporto').change(function(){
		 checkRabbia();
		 
	 });
	
	
	
	
	
 $('#idRegione').change(function(){
  var elem = $(this).val();
  // alert(elem);
  $.ajax({
   type: 'POST',
   url:'ServletRegioniComuniProvince',
   dataType: 'json',
   data: {'idRegione':elem, 'tipoRichiesta':1},
   success: function(res){
	//   alert(res);
    $('#idProvinciaModificaResidenza option').each(function(){$(this).remove()});
    $('#idProvinciaModificaResidenza').append('<option selected="selected">Seleziona...</option>');
    $('#idComuneModificaResidenza option').each(function(){$(this).remove()});
    $("#via").val('');
    $("#idNuovoIndirizzo").val('-1');
    
    $('#idComuneModificaResidenza').append('<option selected="selected">Seleziona...</option>');
    $.each(res, function(i, e){
       // alert(e.codice);
     $('#idProvinciaModificaResidenza').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
    });
   }
  });
 });
  
 $('#idProvinciaModificaResidenza').change(function(){
  var elem = $(this).val();
   
  $.ajax({
   type: 'POST',
   url:'ServletRegioniComuniProvince',
   dataType: 'json',
   data: {'idProvinciaModificaResidenza':elem, 'tipoRichiesta':2},
   success: function(res){
    $('#idComuneModificaResidenza option').each(function(){$(this).remove()});
    $("#via").val('');
    $("#idNuovoIndirizzo").val('-1');
    $('#idComuneModificaResidenza').append('<option selected="selected">Seleziona...</option>');
    $.each(res, function(i, e){
     $('#idComuneModificaResidenza').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
    });
   }
  });
 });


 $('#idComuneModificaResidenza').change(function(){


	    $("#via").val('');
	    $("#idNuovoIndirizzo").val('-1');


	 });
  



var via = { 
		source: function (request, response) {
    $.post("ServletRegioniComuniProvince?tipoRichiesta=4_text&idprovincia="+$("#idProvinciaModificaResidenza").val()+"&idcomune="+$("#idComuneModificaResidenza").val(), request, response);
  }, 


change: function(event, ui){ 	
//	alert((ui.item ? ui.item.idindirizzo : 'sdf'));	

	$("#idNuovoIndirizzo").val(ui.item ? ui.item.idindirizzo : '-1' );  }, 
	minLength:3 }; 


$("#via").autocomplete(via); 


$('#idProvincia, #idProvinciaDecesso').change(function(){
	  var elem = $(this).val();
	 //  alert('aa');
	  $.ajax({
	   type: 'POST',
	   url:'ServletRegioniComuniProvince',
	   dataType: 'json',
	   data: {'idProvinciaModificaResidenza':elem, 'tipoRichiesta':2},
	   success: function(res){
	    $('#idComune option').each(function(){$(this).remove()});
	    $('#idComuneDecesso option').each(function(){$(this).remove()});
	    //$("#via").val('');
	   // $("#idNuovoIndirizzo").val('-1');
	    $('#idComune').append('<option selected="selected" value="-1">Seleziona...</option>');
	    $.each(res, function(i, e){
	     $('#idComune').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
	    });

	    $('#idComuneDecesso').append('<option selected="selected" value="-1" >Seleziona...</option>');
	    $.each(res, function(i, e){
	     $('#idComuneDecesso').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
	    });
	   }
	  });
	 });


	 $('#idComune, #idComuneDecesso').change(function(){


		    $("#indirizzo").val('');
		    $("#indirizzoDecesso").val('');
			  var elem = $(this).val();
			 //  alert('aa');
			  $.ajax({
			   type: 'POST',
			   url:'ServletRegioniComuniProvince',
			   dataType: 'json',
			   data: {'idComune':elem, 'tipoRichiesta':3},
			   success: function(res){
			 //   $('#idComune option').each(function(){$(this).remove()});
			    //$("#via").val('');
			   // $("#idNuovoIndirizzo").val('-1');
			 
			    $.each(res, function(i, e){
					//   alert(e.codice);
					   $('#idAslNuovoProprietarioSelect').val(e.codice);
					   $('#idAslNuovoProprietario').val(e.codice);
					   $('#idAslNuovoProprietarioSelect').prop('disabled', true);
					//   alert($('#idAslNuovoProprietario').val());
			    });
			   }
			  });


		 });


	 $('#idComune, #idComuneDecesso').change(function(){


		    $("#indirizzo").val('');
		    $("#idNuovoIndirizzo").val('-1');


		 });
	  



	var via = { 
			source: function (request, response) {
		<%if (Evento.getIdTipologiaEvento() == EventoAdozioneFuoriAsl.idTipologiaDB) {%>
	    	$.post("ServletRegioniComuniProvince?tipoRichiesta=4_text&idprovincia="+$("#idProvincia").val()+"&idcomune="+$("#idComune").val(), request, response);
	    <%}else if (Evento.getIdTipologiaEvento() == EventoDecesso.idTipologiaDB) {%>
	    	$.post("ServletRegioniComuniProvince?tipoRichiesta=4_text&idprovincia="+$("#idProvinciaDecesso").val()+"&idcomune="+$("#idComuneDecesso").val(), request, response);
	    <%}%>
	  }, 


	change: function(event, ui){ 	
//		alert((ui.item ? ui.item.idindirizzo : 'sdf'));	

		$("#idNuovoIndirizzo").val(ui.item ? ui.item.idindirizzo : '-1' );  }, 
		minLength:3 }; 


	$("#indirizzo, #indirizzoDecesso").autocomplete(via); 


	
	
}); 


/* Commentata perchè nel verbale 15/07/2014 si è deciso di eliminare la selezione dell'LP come veterinario esecutore del prelievo leishmania
 * 
 function showVet(tipoVet){
	
	if (tipoVet == 1){


		$('input[type="radio"][name="idTipologiaVeterinario"][value="1"]:first').prop('checked', true);
		$('#idVeterinarioLLPP').hide();
		$('#idVeterinarioAsl').show();
	} //Vet asl
	else if (tipoVet == 2){
		$('input[type="radio"][name="idTipologiaVeterinario"][value="2"]:first').prop('checked', true);
		$('#idVeterinarioAsl').hide();
		$('#idVeterinarioLLPP').show();
		
	}//Vet LLPP
} */




</script>

<%@ include file="../initPage.jsp"%>
<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
		<td width="100%">
			<dhv:evaluate if ="<%=("RegistrazioniAnimale").equals(actionFrom)%>">
				<a href="AnimaleAction.do?command=Search"><dhv:label name="">Ricerca animali</dhv:label></a> > 
				<a href="AnimaleAction.do?command=Details&animaleId=<%=animale.getIdAnimale()%>&idSpecie=<%=animale.getIdSpecie()%>"><dhv:label name="">Dettagli animale</dhv:label></a> >
				<dhv:label name="anagrafica.animale.aggiungi">Aggiungi registrazione</dhv:label>
			</dhv:evaluate>
			
			<dhv:evaluate if ="<%=("RegistrazioniAnimaleCessioni").equals(actionFrom)%>">
				<a href="RegistrazioniAnimaleCessioni.do?command=Search&searchcodeidTipologiaEvento=7&searchexacttype=in&searchexactstato=opened">
				<dhv:label
				name="anagrafica.animale">Cessioni incompiute</dhv:label></a> > <dhv:label
				name="anagrafica.animale.aggiungi">Presa in carico cessione</dhv:label>
			</dhv:evaluate>
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<body>

<form name="addAnimale"
	action="RegistrazioniAnimale.do?command=Insert&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>"
	method="post">
	<!-- Flusso 238 rollback:  -->
	<!-- input type="hidden" name="id_canile" id="id_canile" value="rollback_flusso_238" -->
	<input type="hidden" name="id_canile" id="id_canile" value="<%=request.getSession().getAttribute("id_canile")%>">
	<input type="hidden" name="id_stabilimento_gisa" id="id_stabilimento_gisa" value="<%=request.getSession().getAttribute("id_stabilimento_gisa")%>">
	<input
	type="hidden" name="doContinue" id="doContinue" value="true"> <input
	type="hidden" name="ruolo" value="<%=User.getRoleId()%>"> 
	<input type="hidden" name="dataAperturaCC" id="dataAperturaCC" value="<%=(String)request.getAttribute("dataAperturaCC")%>"> 
	<input type="hidden" name="primo_load" id="primo_load" value="<%=(String)request.getAttribute("primo_load")%>"> 
	<input
	type="hidden" name="nascita" id="nascita" nomecampo="nascita"
	labelcampo="Data Nascita"
	value="<%= toDateasString(animale.getDataNascita()) %>" /> 
	<input type="hidden" id="dataUltimaRegistrazione" name="dataUltimaRegistrazione" nomecampo="dataUltimaRegistrazione" value="<%=(String)request.getAttribute("dataUltimaRegistrazione")%>" />
	<input
	type="hidden" name="registrazione" nomecampo="registrazione"
	labelcampo="Data Registrazione"
	value="<%= toDateasString(animale.getDataRegistrazione()) %>" />
	<input
	type="hidden" name="datatocheck" nomecampo="datatocheck"
	labelcampo="<%= (labeldatatocheck) %>"
	value="<%= (datatocheck) %>" />
	<input type="hidden" value="<%=session.getAttribute("caller")%>" name="origineInserimento" id="origineInserimento" />
	<input type="hidden" name="idStatoOriginale" id="idStatoOriginale" value="<%=animale.getStato() %>"/>
	<input type="hidden" name="idProprietarioCorrente" id="idProprietarioCorrente" value="<%=oldAnimale.getIdProprietario() %>" />
	<input type="hidden" name="idDetentoreCorrente" id="idDetentoreCorrente" value="<%=oldAnimale.getIdDetentore() %>" />
	<input type="hidden" name="dataScadenzaAntirabbica" id="dataScadenzaAntirabbica" value="<%=(request.getAttribute("dataScadenzaAntirabbica")!=null)?((String)request.getAttribute("dataScadenzaAntirabbica")):("")%>" />
	
<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Informazioni sull'animale</dhv:label></strong>
		</th>
	</tr>

	<tr>
		<td class="formLabel" nowrap>Asl di riferimento</td>
		<td><%=AslList.getSelectedValue(animale.getIdAslRiferimento()) %></td>
		<input type="hidden" name="idAslRiferimento" id="idAslRiferimento"
			value="<%=(User.getSiteId() > 0) ? User.getSiteId() : animale.getIdAslRiferimento() %>" />
	</tr>
	<tr>
		<td class="formLabel" nowrap>Microchip / Tatuaggio</td>
		<td><%=(animale.getMicrochip() != null && !("").equals(animale.getMicrochip())) ? animale.getMicrochip() : animale.getTatuaggio() %></td>
	</tr>
	<input type="hidden" name="microchip" id="microchip"
		value=" <%= (animale.getMicrochip() != null && !("").equals(animale.getMicrochip())) ? animale.getMicrochip() : animale.getTatuaggio() %>" />
	<tr>
		<td class="formLabel" nowrap>Razza</td>
		<td><%=toHtml(razzaList.getSelectedValue(animale.getIdRazza()))%></td>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Proprietario</td>
		<td>
		<% if (oldAnimale.getProprietario()!=null) { %> <%= toHtml(oldAnimale.getProprietario().getRagioneSociale()) %></td>
		<%} %>
	</tr>
	<tr class="containerBody">
		<td class="formLabel"><dhv:label name="">Stato dell'animale</dhv:label></td>
		<td><%=toHtml(statoList.getSelectedValue(animale.getStato() ))%>
		&nbsp;</td>
	</tr>
</table>
</br>
</br>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong><dhv:label name="">Tipologia registrazione</dhv:label></strong>
		</th>
	</tr>
	<tr>
		<td class="formLabel" nowrap>Registrazione</td>
		<td><%= registrazioniList.getHtmlSelect("idTipologiaEvento", Evento.getIdTipologiaEvento()) %></td>
		<input type="hidden" name="idTipologiaEventoVam" id="idTipologiaEventoVam" value=""/>
	</tr>
	<input type="hidden" value="<%=animale.getIdAnimale() %>"
		name="idAnimale" />
	<input type="hidden" value="<%=animale.getIdSpecie() %>"
		name="specieAnimaleId" />

</table>

</br>
</br>
<span id="datireg" class="datireg"> </span><%=showError(request, "praticaError")%>


<div id="dettaglio_reg_antirabbia" title="Aggiungi dati vaccinazione antirabbica">
<iframe src="RegistrazioniAnimale.do?fromPassaporto=true&popup=true&command=Add&idAnimale=<%=animale.getIdAnimale()%>&idTipologiaEvento=<%=EventoInserimentoVaccinazioni.idTipologiaDB%>"
		height="100%"
		width= "100%"
>
</iframe>
</div>

</br>

		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong>NOTE</strong></th>
			</tr>
			<tr class="containerBody">
				<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label>
				</td>
				<td><textarea name="note" rows="3" cols="50"><%=toString(Evento.getNote())%></textarea>
				</td>
			</tr>
		</table>
		
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
				<td><textarea name="noteInternalUseOnly" rows="3" cols="50"><%=toString(Evento.getNoteInternalUseOnly())%></textarea>
				</td>
			</tr>
		</table>
	</dhv:permission>

<div id="dialog" title="Dialog Title" style="display: none">Questa
registrazione ti permetterà di effetuare esclusivamente un cambio di
proprietario. La detenzione dell' animale resterà invariata.</div>




<div id="dialogErrorWKF" title="Errore nel workflow" style="display: none">
Attenzione, si è verificato un errore: <%=ErroreWKF %></div>


<div id="dialogMunicipalita" title="Municipalit&agrave; del comune di Napoli">
    <jsp:include page="municipalita.jsp"></jsp:include>
</div>

</table>
<input type="button" value="invia" id="invia" style="display: none;" onclick="javascript:checkForm();" />

</form>


<script>
function disattivaTipoInoculato()
{
	document.getElementById('idTipologiaVaccinoInoculato').value='-1';
	
	if(document.getElementById('idTipoVaccino').value=='2')
	{
		document.getElementById('idTipologiaVaccinoInoculato').disabled='true';
	}
	
	else
	{
		document.getElementById('idTipologiaVaccinoInoculato').disabled=false;
	}
}



function gestisciTipologiaMorso(tipologia)
{

	if(tipologia=='-1')
	{
		nascondiCampo('prevedibilitaEvento');
		nascondiCampoByName('valoreManualeIndice1');
		nascondiCampoByName('valoreManualeIndice2');
		nascondiCampoByName('indice2');
		nascondiCampoByName('indice3');
		nascondiCampoByName('indice4');
		nascondiCampoByName('indice5');
		nascondiCampoByName('indice6');
		nascondiCampo('morsoRipetuto');
		nascondiCampo('tagliaAggressore');
		nascondiCampo('categoriaVittima');
		nascondiCampo('tagliaVittima');
		nascondiCampo('patologie');
		nascondiCampo('alterazioniComportamentali');
		nascondiCampo('analisiGestione');
		nascondiCampo('veterinari');

		clearCampiTipologiaMorso();
		
	}
	else
	{

		clearCampiTipologiaMorso();
		
		abilitaCampo('morsoRipetuto');
		abilitaCampo('patologie');
		abilitaCampo('alterazioniComportamentali');
		abilitaCampo('analisiGestione');
		abilitaCampo('veterinari');
		
		if(tipologia=='1')
		{
			abilitaCampo('prevedibilitaEvento');
			nascondiCampoByName('valoreManualeIndice1');
			nascondiCampoByName('valoreManualeIndice2');
			nascondiCampoByName('indice2');
			nascondiCampoByName('indice3');
			nascondiCampoByName('indice4');
			nascondiCampoByName('indice5');
			nascondiCampoByName('indice6');
			abilitaCampo('tagliaAggressore');
			abilitaCampo('categoriaVittima');
			abilitaCampo('tagliaVittima');
		}
		else if(tipologia=='2')
		{
			nascondiCampo('prevedibilitaEvento');
			abilitaCampoByName('valoreManualeIndice1');
			abilitaCampoByName('valoreManualeIndice2');
			abilitaCampoByName('indice2');
			abilitaCampoByName('indice3');
			abilitaCampoByName('indice4');
			abilitaCampoByName('indice5');
			abilitaCampoByName('indice6');
			nascondiCampo('tagliaAggressore');
			nascondiCampo('categoriaVittima');
			nascondiCampo('tagliaVittima');
		}
		
	}
	
}	




function gestisciTipologiaAggressione(tipologia)
{
	
	
		if(tipologia=='1')
		{
			abilitaCampo('tagliaVittima');
			
			$("#categoriaVittima option[value='1']").remove();
			if($("#categoriaVittima option[value='5']").length <= 0)
				$("#categoriaVittima").append('<option value="5">Cucciolo</option>');
		}
		else if(tipologia=='2')
		{
			nascondiCampo('tagliaVittima');
			$("#categoriaVittima option[value='5']").remove();
			if($("#categoriaVittima option[value='1']").length <= 0)
				$("#categoriaVittima").append('<option value="1">Bambino</option>');
		}
}	




function nascondiCampo(id_campo)
{
	if(document.getElementById(id_campo)!=null)
	{
		document.getElementById(id_campo).disabled=true;
	}
}

function abilitaCampo(id_campo)
{
	if(document.getElementById(id_campo)!=null)
	{
		document.getElementById(id_campo).disabled=false;
	}
}

function nascondiCampoByName(id_campo)
{
	if(document.getElementsByName(id_campo)!=null)
	{
		var elem = document.getElementsByName(id_campo);
	    for( var i=0; i<elem.length; i++ )
	    	elem[i].disabled=true;
	}
}

function abilitaCampoByName(id_campo)
{
	if(document.getElementsByName(id_campo)!=null)
	{
		var elem = document.getElementsByName(id_campo);
	    for( var i=0; i<elem.length; i++ )
	    	elem[i].disabled=false;
	}
}


function clearCampoById(id_campo)
{
	if(document.getElementById(id_campo)!=null)
	{
		document.getElementById(id_campo).value="";
	}
}



function clearCampoByName(campo)
{
	if(document.getElementsByName(campo)!=null)
	{
		var elem = document.getElementsByName(campo);
	    for( var i=0; i<elem.length; i++ )
	    	elem[i].value="";
	}
}

function clearCampoRadioByName (campo)
{
	
	
	if(document.getElementsByName(campo)!=null)
	{
		var elem = document.getElementsByName(campo);
	    for( var i=0; i<elem.length; i++ )
	    	elem[i].checked=false;
	}
	
}




function clearCampiTipologiaMorso(){

	   
	clearCampoByName('valoreManualeIndice1');
	clearCampoByName('valoreManualeIndice2');
	clearCampoRadioByName('indice2');
	clearCampoRadioByName('indice3');
	clearCampoRadioByName('indice4');
	clearCampoRadioByName('indice5');
	clearCampoRadioByName('indice6');
	

	clearCampoById('punteggio1');
	clearCampoById('punteggio2');
	clearCampoById('punteggio3');
	clearCampoById('punteggio4');
	clearCampoById('punteggio5');
	clearCampoById('punteggio6');
	clearCampoById('valoreManualeIndice1');
	clearCampoById('valoreManualeIndice2');

	
	
	 $("#prevedibilitaEvento").val(["-1"]);
	 $("#morsoRipetuto").val(["-1"]);
	 $("#tagliaAggressore").val(["-1"]);
	 $("#categoriaVittima").val(["-1"]);
	 $("#tagliaVittima").val(["-1"]);
	 $("#patologie").val(["-1"]);
	 $("#alterazioniComportamentali").val(["-1"]);
	 $("#analisiGestione").val(["-1"]);
	 $("#veterinariMorsicatura").val(["-1"]);
 
}

$(function() 
		{
			$( "#dettaglio_reg_antirabbia" ).dialog({
				height: screen.height*0.75,
				modal: true,
				autoOpen: false,
				closeOnEscape: true,
				show: 'blind',
				resizable: true,
				draggable: true,
				width: screen.width*0.75,
				buttons: 
				{
					"OK": function() 
					{
						$('#dettaglio_reg_antirabbia').dialog( "close" );
					}
				}
		});
		$("#ui-datepicker-div").css("z-index",10000); 
});	



</script>