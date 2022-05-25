/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/


function checkValiditaPec(idCampo,idMsgElement)
{
	var mailAddr = $("#"+idCampo).val();
	if(mailAddr == undefined)
		mailAddr = '';
		
	mailAddr = mailAddr.toLowerCase();
//	if(mailAddr == null || mailAddr == '')
//	{
//		$('#'+idMsgElement).text("CAMPO OBBLIGATORIO");
//		$('#'+idMsgElement).css("visibility","visible");
//		$('#'+idMsgElement).css("color","#B22222");
//		return;
//		
//	}
	
	
	//controllo client side del formato
	/*var formatoValido = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/.test(mailAddr);
	if(formatoValido == false)
	{
		//alert("formato mail non valido");
		window.mailValida = false;
		
		$('#'+idMsgElement).text("FORMATO MAIL INVALIDO");
		$('#'+idMsgElement).css("visibility","visible");
		$('#'+idMsgElement).css("color","#B22222");
		
		return;
	}*/
	
	
	
	//alert(mailAddr);
	//mando richiesta ajax per controllare server side formato E validita PEC

	$.ajax({
		type : 'get',
		url: 'DestinatarioPecMailChecker.do?mail_to_check='+mailAddr,
		dataType : "json",
		success: function(data)
				 {
					//$('#'+idMsgElement).css("visibility","hidden");
					//alert("risposta ricevuta ! :"+data.esito);
					if(parseInt(data.esito) != 1)
					{
						if(parseInt(data.esito) == 2) //provider invalido
						{
							
							//alert("Attenzione: provider PEC non riconosciuto");
							window.mailValida = false; 
						}
//						else //formato non valido
//						{
//							//alert("mail pec non valida -"+ data.messaggio);
////							$('#'+idMsgElement).text(data.messaggio.toUpperCase());
////							$('#'+idMsgElement).css("visibility","visible");
////							$('#'+idMsgElement).css("color","#B22222");
//							//alert(data.messaggio.toUpperCase());
//							window.mailValida = false;
//						}
						
						
						
					}
					else
					{
						//alert("mail valida");
//						$('#'+idMsgElement).css("visibility","hidden");
						window.mailValida = true;
					}
					//window.objTemp = data;
				 } 
		
	});
}

//
//function checkMailPattern(mail)
//{
//	return /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/.test(mail);
//}


function showPopUp(){
    $('#dialogsuap').dialog('open');
}
    
function gestisciCFUguale(cb){
    var iv = document.getElementById("partitaIva").value;
    var cf = document.getElementById("codFiscale");
    if (cb.checked)
            cf.value = iv;
    else
            cf.value = '';
}

$(function() {
	
	 $( "#dialogsuap" ).dialog({
		 title : "IMPRESA ESISTENTE IN ANAGRAFICA CON LA PARTITA IVA INSERITA : ",
         autoOpen: false,
         resizable: false,
         closeOnEscape: false,
         width:850,
         height:500,
         draggable: false,
         modal: false,
 }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	 
	 
	 

	
    
    
 
});


/*****************************************
Controllo della Partita I.V.A.
Linguaggio: JavaScript
******************************************/

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


function checkPartitaIva(idcampo){
    var that;
    
        tipoImpresa= document.getElementById("tipo_impresa").value;
 if (tipoImpresa=='5' ||  tipoImpresa=='6'){
     // Get the other input
     that = $('input[name=partitaIva],input[name=codFiscale]').not($('#'+idcampo));
     // If the value of this input is not empty, set the required property of the other input to false
     if($('#'+idcampo).val().length) {
         that.removeAttr("class");
     } else {
         that.attr("class","required");
         $('#'+idcampo).removeAttr("class");
     }
 }
 
 
 if ( tipoImpresa != '12' && ($('input[name=partitaIva]').val()!='' || $('input[name=codFiscale]').val()!='')) {
                loadModalWindowCustom("Verifica Esistenza Impresa. Attendere");
                $.ajax({
                     type: 'POST',
                   dataType: "json",
                   cache: false,
                  url: 'SuapStab.do?command=VerificaEsistenza',
                       data: ''+$('form[name=addstabilimento]').serialize(), 
                  success: function(msg) {
                           result = msg ;
                           if(msg.codiceErroreSuap =="1"){
                                   showPopUp();
                           }else{
                                   if(msg.codiceErroreSuap =="2"){
                                           var htmlText="<div><input type=\"button\" value = \"ESCI\" onclick=\"$('#dialogsuap').dialog('close');\"></div><br><br>" ; //msg[0].erroreSuap;
                                           htmlText+='<table border="1" class="pagedList"><tr><th>Denominazione</th><th>Partita IVa</th><th>Rappresentante Legale</th><th>Sede Legale</th><th></th></tr>'
                                           var jsontext = JSON.stringify(msg);
                                           if (msg.listaOperatori!=null && msg.listaOperatori.length>0){
                                                   for(i=0;i<msg.listaOperatori.length;i++){
                                                          htmlText+="<tr><td>"+msg.listaOperatori[i].ragioneSociale+"</td><td>"+msg.listaOperatori[i].partitaIva+"</td><td>"+msg.listaOperatori[i].rappLegale.nome+" "+msg.listaOperatori[i].rappLegale.cognome+" "+msg.listaOperatori[i].rappLegale.codFiscale+ " "+msg.listaOperatori[i].rappLegale.indirizzo.descrizioneComune+" "+msg.listaOperatori[i].rappLegale.indirizzo.descrizione_provincia+" "+msg.listaOperatori[i].rappLegale.indirizzo.via+"</td><td>"+msg.listaOperatori[i].sedeLegaleImpresa.descrizioneComune+" "+msg.listaOperatori[i].sedeLegaleImpresa.descrizione_provincia+" "+msg.listaOperatori[i].sedeLegaleImpresa.via+"</td><td><input type='button' value='Seleziona' onclick='selezionaImpresa("+i+")'></td></tr>";
                                                   }
                                                   htmlText+="</table>";
                                                   $( "#dialogsuap" ).html(htmlText);
                                                   
                                                   
                                                   
                                                    showPopUp();
                                           }else{
                                                   alert(msg.erroreSuap);
//                                                   document.getElementById("partitaIva").value="";
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



var result ;
   

	
function selezionaImpresa(i){
	var msgHD="ATTENZIONE! Uno o piu' campi obbligatori dell'impresa non sono presenti, contattare l'helpdesk.".toUpperCase();
	
	var operatori = result.listaOperatori;
	
    $('input[name="idOperatore"]').val(operatori[i].idOperatore);
  	$('input[name="ragioneSociale"]').val(operatori[i].ragioneSociale);
  	$('input[name="partitaIva"]').val(operatori[i].partitaIva); 
  	
	$('input[name="codFiscale"]').val(operatori[i].codFiscale);	
	$('input[name="domicilioDigitale"]').val(operatori[i].domicilioDigitale);
    $('input[name="comuneNascitainput"]').val(operatori[i].rappLegale.comuneNascita);
	$("#comuneNascita").append("<option value='"+operatori[i].rappLegale.idComuneNascita+ "' selected='selected'>"+operatori[i].rappLegale.comuneNascita+"</option>');");
	$('input[name="nome"]').val(operatori[i].rappLegale.nome);
    $('input[name="cognome"]').val(operatori[i].rappLegale.cognome);
  
    if (operatori[i].rappLegale.sesso=='F' || operatori[i].rappLegale.sesso == 'f')
    	$('input[id="sesso2"]').prop('checked', true);
    else
    	$('input[id="sesso1"]').prop('checked', true);
    
  	$('input[name="codFiscaleSoggetto"]').val(operatori[i].rappLegale.codFiscale);
  	$('input[name="dataNascita"]').val(operatori[i].rappLegale.dataNascitaString);  	
  	$('#nazioneNascita_td').html("<input type='text' readonly='readonly' size='50' value='Italia'><input type='hidden' name='nazioneNascita' id='nazioneResidenza' value='106'>"); 
	$('input[name="nazioneResidenza"]').val(operatori[i].rappLegale.indirizzo.nazione);
 	$('input[name="addressLegaleCountryinput"]').val(operatori[i].rappLegale.indirizzo.descrizione_provincia);
  	$("#addressLegaleCountry").append("<option value='"+operatori[i].rappLegale.indirizzo.idProvincia+ "' selected='selected'>"+operatori[i].rappLegale.indirizzo.descrizione_provincia+"</option>');");
	$('input[name="addressLegaleCityinput"]').val(operatori[i].rappLegale.indirizzo.descrizioneComune);
	$('input[name="addressLegaleCitta"]').val(operatori[i].rappLegale.indirizzo.descrizioneComune);
	$('input[name="addressLegaleCityId"]').val(operatori[i].rappLegale.indirizzo.comune);
	$('input[name="addressLegaleCityTesto"]').val(operatori[i].rappLegale.indirizzo.descrizioneComune);
	$("#addressLegaleCity").append("<option value='"+operatori[i].rappLegale.indirizzo.comune+ "' selected='selected'>"+operatori[i].rappLegale.indirizzo.descrizioneComune+"</option>');");
  	$('input[name="addressLegaleLine1input"]').val(operatori[i].rappLegale.indirizzo.via);
  	$("#addressLegaleLine1").append("<option value='"+operatori[i].rappLegale.indirizzo.idIndirizzo+ "' selected='selected'>"+operatori[i].rappLegale.indirizzo.via+"</option>');");
  	$('input[name="toponimoResidenza"]').val(operatori[i].rappLegale.indirizzo.toponimo);
  	$('input[name="civicoResidenza"]').val(operatori[i].rappLegale.indirizzo.civico);
  	$('input[name="capResidenza"]').val(operatori[i].rappLegale.indirizzo.cap);
  	$('input[name="nazioneSedeLegale"]').val(operatori[i].sedeLegaleImpresa.nazione);
  	$('input[name="searchcodeIdprovinciainput"]').val(operatori[i].sedeLegaleImpresa.descrizione_provincia);
  	$("#searchcodeIdprovincia").append("<option value='"+operatori[i].sedeLegaleImpresa.idProvincia+ "' selected='selected'>"+operatori[i].sedeLegaleImpresa.descrizione_provincia+"</option>');");
  	$('input[name="searchcodeIdComuneinput"]').val(operatori[i].sedeLegaleImpresa.descrizioneComune);
  	$("#searchcodeIdComune").append("<option value='"+operatori[i].sedeLegaleImpresa.comune+ "' selected='selected'>"+operatori[i].sedeLegaleImpresa.comune+"</option>');");
  	$('input[name="searchcodeIdComuneId"]').val(operatori[i].sedeLegaleImpresa.comune);
  	$('input[name="searchcodeIdComuneTesto"]').val(operatori[i].sedeLegaleImpresa.descrizioneComune);
  	$('input[name="viainput"]').val(operatori[i].sedeLegaleImpresa.via);
	$("#via").append("<option value='"+operatori[i].sedeLegaleImpresa.idIndirizzo+ "' selected='selected'>"+operatori[i].sedeLegaleImpresa.via+"</option>');");
  	$('input[name="toponimoSedeLegale"]').val(operatori[i].sedeLegaleImpresa.toponimo);
  	$('input[name="civicoSedeLegale"]').val(operatori[i].sedeLegaleImpresa.civico);
  	$('input[name="presso"]').val(operatori[i].sedeLegaleImpresa.cap);	
  
	$('input[name="sovrascrivi"]').val("no");
  	$('#dialogsuap').dialog('close');
  	
  	if($( "#methodRequest" ).val()=="cambioTitolarita")
  		{
  		if(document.getElementById("tipo_impresa").value != operatori[i].tipo_impresa)
  			{
  			alert("Attenzione! il tipo di impresa indicato non coincide con quello presente in anagrafica. Qualora si ritenesse di aver sbagliato cliccare sul tasto INDIETRO presente alla fine di questa pagina.");
  		}
  		}else
  			{
  			if (operatori[i].tipo_impresa>0){
  			  	$('select[name="tipo_impresa"]').val(operatori[i].tipo_impresa);
  			  	onChangeTipoImpresa();
  				$('select[name="tipo_societa"]').val(operatori[i].tipo_societa);
  				}
  			}
}





/*GESTIONE CAMPO TIPO IMPRESA PER SOCIETA E IMPRESE INDIVIDUALI*/
 function onChangeTipoImpresa()
  {
  	var  tipoImpresa =document.getElementById("tipo_impresa").value;
  	if (tipoImpresa!="")
  		SuapDwr.onChangeTipoImpresa(tipoImpresa,{callback:onChangeTipoImpresaCallBack,async:false});
  	}

function onChangeTipoImpresaCallBack(tipoImpresa)
  	{
  	
		var comuneSuap = document.getElementById("comuneSuap").value;
		var idComune = document.getElementById("idComuneSuap").value;
		var descrizioneProvincia = document.getElementById("descrizioneProvincia").value;
		var idProvincia = document.getElementById("idProvinciaSuap").value;
		
		
		$('input[name="addressLegaleCityinput"]').prop("readonly", false);
		$('input[name="addressLegaleCountryinput"]').prop("readonly", false);
		
		$('input[name="searchcodeIdComuneinput"]').prop("readonly", false);
		$('input[name="searchcodeIdprovinciainput"]').prop("readonly", false);
		
		$('input[name="searchcodeIdComuneStabinput"]').prop("readonly", false);
		$('input[name="searchcodeIdprovinciaStabinput"]').prop("readonly", false);
		
		
		
		
			$('input[name="addressLegaleCityinput"]').val("");
		
			$('input[name="addressLegaleCountryinput"]').val("");
		
			$('input[name="searchcodeIdComuneinput"]').val("");
		
			$('input[name="searchcodeIdprovinciainput"]').val("");
		
			$('input[name="searchcodeIdComuneStabinput"]').val("");
		
			$('input[name="searchcodeIdprovinciaStabinput"]').val("");
		
		
		if (document.getElementById("tipo_impresa").value=='1' && (document.getElementById("fissa").value=='false'|| document.getElementById("fissa").value=='api')) // residenza
			{
			
			
			
			$('input[name="addressLegaleCityinput"]').val(comuneSuap);
			$("#addressLegaleCity").append("<option value='"+idComune+ "' selected='selected'>"+comuneSuap+"</option>');");
			
			$('input[name="addressLegaleCountryinput"]').val(descrizioneProvincia);
		  	$("#addressLegaleCountry").append("<option value='"+idProvincia+ "' selected='selected'>"+descrizioneProvincia+"</option>');");
		  	
		  	$('input[name="addressLegaleCityinput"]').prop("readonly", true);
			$('input[name="addressLegaleCountryinput"]').prop("readonly", true);
			
			
			}
		else
			{
			if (document.getElementById("tipo_impresa").value=='1' && (document.getElementById("fissa").value=='true' )) // sede operativa
			{
				
				$('input[name="searchcodeIdComuneStabinput"]').val(comuneSuap);
				$("#searchcodeIdComuneStab").append("<option value='"+idComune+ "' selected='selected'>"+comuneSuap+"</option>');");
				
				$('input[name="searchcodeIdprovinciaStabinput"]').val(descrizioneProvincia);
			  	$("#searchcodeIdprovinciaStab").append("<option value='"+idProvincia+ "' selected='selected'>"+descrizioneProvincia+"</option>');");
			  	
			  	$('input[name="searchcodeIdprovinciaStabinput"]').prop("readonly", true);
				$('input[name="searchcodeIdComuneStabinput"]').prop("readonly", true);
			
			}
			else{
				
				if (document.getElementById("tipo_impresa").value!='1' && document.getElementById("fissa").value=='true') // sede operativa
				{
				
					$('input[name="searchcodeIdComuneStabinput"]').val(comuneSuap);
					$("#searchcodeIdComuneStab").append("<option value='"+idComune+ "' selected='selected'>"+comuneSuap+"</option>');");
					
					$('input[name="searchcodeIdprovinciaStabinput"]').val(descrizioneProvincia);
				  	$("#searchcodeIdprovinciaStab").append("<option value='"+idProvincia+ "' selected='selected'>"+descrizioneProvincia+"</option>');");
				  	
					$('input[name="searchcodeIdprovinciaStabinput"]').prop("readonly", true);
					$('input[name="searchcodeIdComuneStabinput"]').prop("readonly", true);
					
				}
				else
					{
					if (document.getElementById("tipo_impresa").value!='1' && (document.getElementById("fissa").value=='false' || document.getElementById("fissa").value=='api')) // sede legale
					{
					
						
						$('input[name="searchcodeIdComuneinput"]').val(comuneSuap);
						$("#searchcodeIdComune").append("<option value='"+idComune+ "' selected='selected'>"+comuneSuap+"</option>');");
						
						$('input[name="searchcodeIdprovinciainput"]').val(descrizioneProvincia);
					  	$("#searchcodeIdprovincia").append("<option value='"+idProvincia+ "' selected='selected'>"+descrizioneProvincia+"</option>');");
					  	
					  	$('input[name="searchcodeIdComuneinput"]').prop("readonly", true);
						$('input[name="searchcodeIdprovinciainput"]').prop("readonly", true);
					
					}
					
					}
				
				
			}
			
			}
	
		
		if (document.getElementById("tipo_impresa").value=='12') //strutture veterinarie pubbliche
			{
			document.getElementById("fieldsRappLegale").style.display="none";
			$('input[name="nome"]').val("DIRETTORE GENERALE");
			$('input[name="cognome"]').val("ASL");
			}
		else
			{
			document.getElementById("fieldsRappLegale").style.display="block";
			$('input[name="nome"]').val("");
			$('input[name="cognome"]').val("");
			}
		
		
		
		
		
	
  		if (document.getElementById("tipo_impresa").value=='4')
  			{
  			document.getElementById("trTipoSocieta").innerHTML="TIPO COOPERATIVA";
  			}
  		else
  			{
  			document.getElementById("trTipoSocieta").innerHTML="TIPO SOCIETA'";
  			}
  		
  		/*setto la label e obbligatorieta della ragione sociale in base al tipo impresa*/
  		document.getElementById("labelRagSoc").innerHTML=tipoImpresa[0].labelRagioneSociale;
  		if (tipoImpresa[0].requiredRagioneSociale==false)
  			{
  		 $("#ragioneSociale").removeAttr("class");
  		 $("#codFiscaleTR").attr("style","display:none");
  			}
  		else
  			{
  			if (tipoImpresa[0].requiredRagioneSociale==true)
  			{
  				 $("#ragioneSociale").attr("class","required");
  				 $("#codFiscaleTR").removeAttr("style");
  			}
  			}
  		
		if(tipoImpresa[0].idTipoImpresa!=5 && tipoImpresa[0].idTipoImpresa!=6  && tipoImpresa[0].idTipoImpresa!=4)
			{
  		if (tipoImpresa[0].requiredPartitaIva==true)
  			{
  				$("#partitaIva").attr("class","required");
  			}
  		else
  			{
  				$("#partitaIva").removeAttr("class");
  			}
  		if (tipoImpresa[0].requiredCodiceFiscale==true)
			{
  				$("#codFiscale").attr("class","required");
			}
  		else
  			if (tipoImpresa[0].idTipoImpresa==2) // societa
  				{
  				 $("#codFiscaleTR").attr("style","display:none");
  				}
		else
			{
				$("#codFiscale").removeAttr("class");
			}
  	}
  		if (tipoImpresa[0].requiredSedeLegale==true)
		{
				$("#searchcodeIdprovinciainput").attr("class","required");
				$("#searchcodeIdComuneinput").attr("class","required");
				$("#tiponimosedelegaleinput").attr("class","required");
				$("#via").attr("class","required");
				$("#civicoSedeLegale").attr("class","required");
				$("#presso").attr("class","required");
				document.getElementById('addressLegaleCitta').value='';
				$("#addressLegaleCitta").attr("onClick","selezionaIndirizzo('nazioneResidenza','callBackResidenzaRappLegale')");
				document.getElementById("setSedeLegale").style.display="";
				document.getElementById('nazioneResidenza').disabled=false;
		}
	else
		{
		$("#searchcodeIdprovinciainput").removeAttr("class");
		$("#searchcodeIdComuneinput").removeAttr("class");
		$("#tiponimosedelegaleinput").removeAttr("class");
		$("#via").removeAttr("class");
		$("#civicoSedeLegale").removeAttr("class");
		$("#presso").removeAttr("class");
		document.getElementById('addressLegaleCitta').value=document.getElementById('codeIdComune').value;
		
		
		if(document.getElementById("tipoAttivita").value==tipoAttivita_FISSA)
		{
			$("#addressLegaleCitta").attr("onClick","selezionaIndirizzo('nazioneResidenza','callBackResidenzaRappLegale')");
		}
		else
		{
			document.getElementById('addressLegaleCitta').value=document.getElementById('codeIdComune').value;
			$("#addressLegaleCitta").attr("onClick","selezionaIndirizzo('nazioneResidenza','callBackResidenzaRappLegale',this.value,true)");
			document.getElementById('addressLegaleCitta').readOnly=true;
		}
		
		document.getElementById('addressLegaleCitta').readOnly=true;
		document.getElementById('nazioneResidenza').disabled=true;
		document.getElementById("setSedeLegale").style.display="none";
		}
  		/**required su partita iva e codice fisclae impresa  **/
  		/*costruisco la lista delle societa in base al tipo impresa*/
  		$('#tipo_societa').children('option:not(:first)').remove();
  		for (i=0;i<tipoImpresa.length;i++)
  			{
  			if (tipoImpresa[i].tipoImpresa==tipoImpresa[i].tipoSocieta)
  				{
  				$("#tipo_societa").removeAttr("class");
  				 $("#tipo_societaTR").attr("style","display:none");
  				break;
  				}
  			else
  				{
  			 $('#tipo_societa')
  			 .append($("<option></option>")
  			 .attr("value",tipoImpresa[i].codeTipoSocieta)
  			 .text(tipoImpresa[i].tipoSocieta)); 
  			 if (i==0){
  				 $("#tipo_societa").attr("class","required");
  				 $("#tipo_societaTR").removeAttr("style");
  				}
  				}
  			}  	
  		
  	}


$(function() {
	$('#dataNascita2').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: '-18Y',  showOnFocus: false, showTrigger: '#calImg'});
});










var tipoSocietaDefaultGestioneBreve;
function onChangeTipoImpresaGestioneBreve(tipoSocietaDefault)
{
	tipoSocietaDefaultGestioneBreve = tipoSocietaDefault;
	var  tipoImpresa =document.getElementById("tipo_impresa").value;
	if (tipoImpresa!="")
		SuapDwr.onChangeTipoImpresa(tipoImpresa,{callback:onChangeTipoImpresaGestioneBreveCallBack,async:false});
	}

function onChangeTipoImpresaGestioneBreveCallBack(tipoImpresa)
	{
	
		var comuneSuap = document.getElementById("comuneSuap").value;
		var idComune = document.getElementById("idComuneSuap").value;
		var descrizioneProvincia = document.getElementById("descrizioneProvincia").value;
		var idProvincia = document.getElementById("idProvinciaSuap").value;
		
		
		$('input[name="addressLegaleCityinput"]').prop("readonly", false);
		$('input[name="addressLegaleCountryinput"]').prop("readonly", false);
		
		$('input[name="searchcodeIdComuneinput"]').prop("readonly", false);
		$('input[name="searchcodeIdprovinciainput"]').prop("readonly", false);
		
		$('input[name="searchcodeIdComuneStabinput"]').prop("readonly", false);
		$('input[name="searchcodeIdprovinciaStabinput"]').prop("readonly", false);
		
		
		
		if (document.getElementById("tipo_impresa").value=='1' && (document.getElementById("fissa").value=='false'|| document.getElementById("fissa").value=='api')) // residenza
			{
			
			
			
		
		  	
		  	$('input[name="addressLegaleCityinput"]').prop("readonly", true);
			$('input[name="addressLegaleCountryinput"]').prop("readonly", true);
			
			
			}
		else
			{
			if (document.getElementById("tipo_impresa").value=='1' && (document.getElementById("fissa").value=='true' )) // sede operativa
			{
				

			  	
			  	$('input[name="searchcodeIdprovinciaStabinput"]').prop("readonly", true);
				$('input[name="searchcodeIdComuneStabinput"]').prop("readonly", true);
			
			}
			else{
				
				if (document.getElementById("tipo_impresa").value!='1' && document.getElementById("fissa").value=='true') // sede operativa
				{
				

				  	
					$('input[name="searchcodeIdprovinciaStabinput"]').prop("readonly", true);
					$('input[name="searchcodeIdComuneStabinput"]').prop("readonly", true);
					
				}
				else
					{
					if (document.getElementById("tipo_impresa").value!='1' && (document.getElementById("fissa").value=='false' || document.getElementById("fissa").value=='api')) // sede legale
					{
					
						
						
						
					  	
					  	$('input[name="searchcodeIdComuneinput"]').prop("readonly", true);
						$('input[name="searchcodeIdprovinciainput"]').prop("readonly", true);
					
					}
					
					}
				
				
			}
			
			}
	
	
		if (document.getElementById("tipo_impresa").value=='4')
			{
			document.getElementById("trTipoSocieta").innerHTML="TIPO COOPERATIVA";
			}
		else
			{
			document.getElementById("trTipoSocieta").innerHTML="TIPO SOCIETA'";
			}
		
		/*setto la label e obbligatorieta della ragione sociale in base al tipo impresa*/
		document.getElementById("labelRagSoc").innerHTML=tipoImpresa[0].labelRagioneSociale;
		if (tipoImpresa[0].requiredRagioneSociale==false)
			{
		 $("#ragioneSociale").removeAttr("class");
		 $("#codFiscaleTR").attr("style","display:none");
			}
		else
			{
			if (tipoImpresa[0].requiredRagioneSociale==true)
			{
				 $("#ragioneSociale").attr("class","required");
				 $("#codFiscaleTR").removeAttr("style");
			}
			}
		
		if(tipoImpresa[0].idTipoImpresa!=5 && tipoImpresa[0].idTipoImpresa!=6  && tipoImpresa[0].idTipoImpresa!=4)
			{
		if (tipoImpresa[0].requiredPartitaIva==true)
			{
				$("#partitaIva").attr("class","required");
			}
		else
			{
				$("#partitaIva").removeAttr("class");
			}
		if (tipoImpresa[0].requiredCodiceFiscale==true)
			{
				$("#codFiscale").attr("class","required");
			}
		else
			if (tipoImpresa[0].idTipoImpresa==2) // societa
				{
				 $("#codFiscaleTR").attr("style","display:none");
				}
		else
			{
				$("#codFiscale").removeAttr("class");
			}
	}
		if (tipoImpresa[0].requiredSedeLegale==true)
		{
				$("#searchcodeIdprovinciainput").attr("class","required");
				$("#searchcodeIdComuneinput").attr("class","required");
				$("#tiponimosedelegaleinput").attr("class","required");
				$("#via").attr("class","required");
				$("#civicoSedeLegale").attr("class","required");
				$("#presso").attr("class","required");
				document.getElementById("setSedeLegale").style.display="";
		}
	else
		{
		$("#searchcodeIdprovinciainput").removeAttr("class");
		$("#searchcodeIdComuneinput").removeAttr("class");
		$("#tiponimosedelegaleinput").removeAttr("class");
		$("#via").removeAttr("class");
		$("#civicoSedeLegale").removeAttr("class");
		$("#presso").removeAttr("class");
		document.getElementById("setSedeLegale").style.display="none";
		}
		/**required su partita iva e codice fisclae impresa  **/
		/*costruisco la lista delle societa in base al tipo impresa*/
		
		$('#tipo_societa').children('option:not(:first)').remove();
		for (i=0;i<tipoImpresa.length;i++)
			{
			if (tipoImpresa[i].tipoImpresa==tipoImpresa[i].tipoSocieta)
				{
				$("#tipo_societa").removeAttr("class");
				 $("#tipo_societaTR").attr("style","display:none");
				break;
				}
			else
				{
			 $('#tipo_societa')
			 .append($("<option></option>")
			 .attr("value",tipoImpresa[i].codeTipoSocieta)
			 .text(tipoImpresa[i].tipoSocieta)); 
			 if (i==0){
				 $("#tipo_societa").attr("class","required");
				 $("#tipo_societaTR").removeAttr("style");
				}
				}
			
			}
		$('#tipo_societa').val(tipoSocietaDefaultGestioneBreve);
		
	}





