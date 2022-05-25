/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
$(function () {
	$( "#validazioneDialog" ).dialog({
		autoOpen: false,
		resizable: false,
		closeOnEscape: false,
		title:"VALIDAZIONE LINEA",
		width:550,
		height:300,
		draggable: false,
		open :function(){document.getElementById("validazioneDialog").focus();},
		modal: true,
		position: 'top',
		buttons:{

			"Esci" : function() {

				$(this).dialog("close");
			}

		},


	}).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	
	$( "#nonValidabileDialog" ).dialog({
		autoOpen: false,
		resizable: false,
		closeOnEscape: false,
		title:"SCIA NON VALIDABILE INDICARE UN MOTIVO",
		width:550,
		height:300,
		draggable: false,
		open :function(){document.getElementById("nonValidabileDialog").focus();},
		modal: true,
		position: 'top',
		buttons:{

			"Esci" : function() {

				$(this).dialog("close");
			}

		},


	}).prev(".ui-dialog-titlebar").css("background","#bdcfff");

});


function intercettaBottoneNonValidabile()
{
	$( '#tabNonValidabileDialog tr:not(:first)').remove();
	
	$( '#tabNonValidabileDialog  tr:last').after('<tr><td class=\"formLabel\">NOTE</td><td><textarea row=\"6\" cols=\"33\" name=\"noteValidazione\"></textarea><input type=\"hidden\" name=\"statoValidazione\" value=\"2\"></td></tr>');
	$( '#tabNonValidabileDialog  tr:last').after('<tr><td colspan=\"2\"><input type = "submit" value = "Non Validabile" onclick="document.getElementById(\"statoValidazione\").value=\"no\""></td></tr>');         	
	$( '#nonValidabileDialog' ).dialog('open');

}

function verificaEsistenzaCodiceNazionale(idSedeOperativa,codiceNazionale)
{
	

SuapDwr.verificaEsistenzaCodiceNazionale(idSedeOperativa,codiceNazionale,verificaEsistenzaCodiceNazionaleCallBack)	;

}

function verificaEsistenzaCodiceNazionaleCallBack(value)
{

if (value ==false)
	{
	alert('Attenzione il CUN inserito è utilizzato per uno stabilimento esistente con una sede operativa diversa.');
	document.getElementById("codiciNazionali").value="";
	
	}
}


var campoCodice;
function verificaEsistenzaCodiceNazionaleSoloCodice(field, codiceNazionale)
{
campoCodice = field;	
SuapDwr.verificaEsistenzaCodiceNazionaleSoloCodice(codiceNazionale,verificaEsistenzaCodiceNazionaleSoloCodiceCallBack)	;

}

function verificaEsistenzaCodiceNazionaleSoloCodiceCallBack(value)
{

if (value ==false)
	{
	alert('Attenzione il CUN inserito è utilizzato per uno stabilimento esistente.');
	campoCodice.value="";
	
	}
}




function intercettaBottoneValida(idLinea,tipoLineaAttivita,descrLinea,statoValidazione,idSedeOperativa,idTipoRichiesta, operazione, idRichiesta,idLineaML)
{
	$( '#tabValidazioneDialog tr:not(:first)').remove();

	
	if(statoValidazione==1)
	{
		
		getTipoCodiceRichiesto(idLineaML);
		var tipoCodiceRichiesto = document.getElementById("tipoCodiceRichiesto").value;
		
		
		
		if(tipoCodiceRichiesto=='2')
			$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">CUN/APPROVAL NUMBER</td><td><input type = \"text\" name = \"codiciNazionali\" value=\"'+codiceAziendaApicoltura+'\" readonly required=\"true\"> <br/> <font color=\"red\" size=\"1px\">Attenzione! In caso di OSM RICONOSCIUTI il CUN va inserito senza il carattere ALFA poiché tale carattere viene automaticamente generato dal sistema</font></td></tr>');
		else if(tipoCodiceRichiesto!='-1' && tipoLineaAttivita!=''  )
			$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">CUN/APPROVAL NUMBER</td><td><input type = \"text\" id = \"codiciNazionali\" name = \"codiciNazionali\" onchange=\"verificaEsistenzaCodiceNazionale('+idSedeOperativa+',this.value)\"  required=\"true\"> <br/> <font color=\"red\" size=\"1px\">Attenzione! In caso di OSM RICONOSCIUTI il CUN va inserito senza il carattere ALFA poiché tale carattere viene automaticamente generato dal sistema</font></td></tr>');
		else
			$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">CUN/APPROVAL NUMBER</td><td><input type = \"text\" id = \"codiciNazionali\" name = \"codiciNazionali\" onchange=\"verificaEsistenzaCodiceNazionale('+idSedeOperativa+',this.value)\" > <br/> <font color=\"red\" size=\"1px\">Attenzione! In caso di OSM RICONOSCIUTI il CUN va inserito senza il carattere ALFA poiché tale carattere viene automaticamente generato dal sistema</font></td></tr>');
			
			}
	
	$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">NOTE</td><td><textarea row=\"6\" cols=\"33\" name=\"noteValidazione\"></textarea><input type=\"hidden\" name=\"statoValidazione\" value=\"'+statoValidazione+'\"></td></tr>');
	$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">LINEA DI ATTIVITA</td><td>'+descrLinea+' <input type = \"hidden\" name = \"idLinea\" value = \"'+idLinea+'\"><input type = \"hidden\" name = \"idTipoLinea\" value = \"'+tipoLineaAttivita+'\"</td></tr>');
	$( '#tabValidazioneDialog  tr:last').after('<tr><td colspan=\"2\">   <input type = "button" value = "Salva" onClick="checkFormValidazione(this, this.form)"></td></tr>');         	



	
	if($("input[type='radio'][name='candidato_scelto']:checked").length	) 
	{

		var idStabCandidatoScelto = $("input[type='radio'][name='candidato_scelto']:checked").val();
		var ragioneSocialeStab =  $("input[type='radio'][name='candidato_scelto']:checked").attr("descrizione");

		$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">CANDIDATO SCELTO</td><td><input type = \"hidden\" name = \"candidato_scelto\" value=\"'+idStabCandidatoScelto+'\">'+ragioneSocialeStab+'</td></tr>');
	}
	
	
	/*per eventuali campi estesi di validazione solo se operazione = ampliamento */
	if(operazione.toLowerCase() == 'ampliamento')
	{
		$( '#tabValidazioneDialog  tr:last').after($("<tr></tr>",{id : 'trPerInjectionCampiEstesi'}).css({'display' : 'none'})); /*qui faro' injection campi estesi di validazione */
		popolaCampiEstesiDiValidazioneAsync(idRichiesta,idLineaML);
	
	}
	
	
	
	
	$( '#validazioneDialog' ).dialog('open');


}

function intercettaBottoneValidaNuovo(idLinea,tipoLineaAttivita,descrLinea,codiceAziendaApicoltura,statoValidazione,idSedeOperativa, idRichiesta, idLineaML/*,campiEstesiPerValidazione*/)
{
	$( '#tabValidazioneDialog tr:not(:first)').remove();
	 
	
	if(statoValidazione==1)
		{
		
		getTipoCodiceRichiesto(idLineaML);
		var tipoCodiceRichiesto = document.getElementById("tipoCodiceRichiesto").value;
		
	if(tipoCodiceRichiesto=='2')
		$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">CUN/APPROVAL NUMBER</td><td><input type = \"text\" name = \"codiciNazionali\" value=\"'+codiceAziendaApicoltura+'\" readonly required=\"true\"> <br/> <font color=\"red\" size=\"1px\">Attenzione! In caso di OSM RICONOSCIUTI il CUN va inserito senza il carattere ALFA poiché tale carattere viene automaticamente generato dal sistema</font></td></tr>');
	else if(tipoCodiceRichiesto!='-1' && tipoLineaAttivita!=''  )
		$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">CUN/APPROVAL NUMBER</td><td><input type = \"text\" id = \"codiciNazionali\" name = \"codiciNazionali\" onchange=\"verificaEsistenzaCodiceNazionale('+idSedeOperativa+',this.value)\"  required=\"true\"> <br/> <font color=\"red\" size=\"1px\">Attenzione! In caso di OSM RICONOSCIUTI il CUN va inserito senza il carattere ALFA poiché tale carattere viene automaticamente generato dal sistema</font></td></tr>');
	else
		$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">CUN/APPROVAL NUMBER</td><td><input type = \"text\" id = \"codiciNazionali\" name = \"codiciNazionali\" onchange=\"verificaEsistenzaCodiceNazionale('+idSedeOperativa+',this.value)\" > <br/> <font color=\"red\" size=\"1px\">Attenzione! In caso di OSM RICONOSCIUTI il CUN va inserito senza il carattere ALFA poiché tale carattere viene automaticamente generato dal sistema</font> </td></tr>');
		
		}
	
	
	$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">NOTE</td><td><textarea row=\"6\" cols=\"33\" name=\"noteValidazione\"></textarea><input type=\"hidden\" name=\"statoValidazione\" value=\"'+statoValidazione+'\"></td></tr>');
	$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">LINEA DI ATTIVITA</td><td>'+descrLinea+' <input type = \"hidden\" name = \"idLinea\" value = \"'+idLinea+'\"><input type = \"hidden\" name = \"idTipoLinea\" value = \"'+tipoLineaAttivita+'\"</td></tr>');
	$( '#tabValidazioneDialog  tr:last').after($("<tr></tr>",{id : 'trPerInjectionCampiEstesi'}).css({'display' : 'none'})); /*qui faro' injection campi estesi di validazione */
	
	
	
	$( '#tabValidazioneDialog  tr:last').after('<tr><td colspan=\"2\"> <input type = "button" value = "Salva" onClick="checkFormValidazione(this, this.form)"></td></tr>');         	
	
	/*gestione campi estesi di validazione in maniera async */
	popolaCampiEstesiDiValidazioneAsync(idRichiesta,idLineaML);
	
	
	
	$( '#validazioneDialog' ).dialog('open');

}



function popolaCampiEstesiDiValidazioneAsync(idRichiesta,idLineaML)
{
	$.ajax({
			
			url : 'InterfValidazioneRichieste.do?command=GetCampiAggiuntiviDiValidazione'
			,method: 'GET'
			,data : {idLinea : idLineaML, idRichiesta : idRichiesta }
			,dataType : 'json'
			,success : function(data){
				if(data.status == '-1')
				{
					console.log(">>>>ERRORE NEL CONTATTARE IL SERVIZIO PER OTTENERE CAMPI ESTESI RICHIESTA "+idRichiesta+", LINEA "+idLineaML)
				}
				else
				{
					console.log(">>>>ARRIVATA RISPOSTA CAMPI ESTESI PER LINEA"+data.idLinea);
					var campiEstesiPerValidazione = data.campiPerLinea;
					
					try
					{
						 if(campiEstesiPerValidazione.length > 0)
						 {
							
							 var trCampiEstesiValidaz = $("#trPerInjectionCampiEstesi");
							 trCampiEstesiValidaz.css({'display': ''}); /*mostro la riga dove ho fatto injection */
							 var tblCampiEstesi = $("<table></table>").css("width","100%");
							 trCampiEstesiValidaz.html($("<td></td>",{colspan : "2"}).html(tblCampiEstesi));
							 
							 for(var indCampoEst in campiEstesiPerValidazione)
							 {
								  
								 var trCampo = $("<tr></tr>");
								 /*per distinguere se è generata server side o deve essere inserita dal client*/
								 var dbiGenerazione = campiEstesiPerValidazione[indCampoEst]['dbi_generazione'];
								 if(dbiGenerazione != undefined && dbiGenerazione != null && dbiGenerazione != 'null' &&  dbiGenerazione.trim().length > 0) /*viene generato da noi, quindi non lo mostriamo*/
								 { /*quelli che devono essere generati server side */
									 trCampo.append($("<td></td>").attr({
										 "colspan" : "2"
										 ,"align" : "center"
											
									 }).css({
										 "color" : "red"
										 ,"font-size" : "9px"
									 }).text("In fase di validazione verrà generato un valore per ").append( $("<font></font>").css("font-weight","bold").text("\""+campiEstesiPerValidazione[indCampoEst]['label_campo']+"\"") ) );
								 }
								 else /*quelli che deve inserire user */
								 {
									 /*TODO- IMPLEMENTA aggiungere gli input con i type, e farli arrivare al server */
								 }
								 tblCampiEstesi.append(trCampo);
								 
							 }
							 
							
							 
						 }
						 
					}
					catch(Ob)
					{
						console.log(">>>>ECCEZIONE GESTIONE RISPOSTA CAMPI ESTESI PER LINEA"+data.idLinea);
					}
				}
			}
			
		});
	
}



function getTipoCodiceRichiesto(idLineaML){
    SuapDwr.getTipoCodiceRichiesto(idLineaML,{callback:getTipoCodiceRichiestoCallBack,async:false});
}
function getTipoCodiceRichiestoCallBack(val){ 
   document.getElementById("tipoCodiceRichiesto").value =val;
}




function ritornaAPaginaPerImport()
{
	window.location.href = 'OpuStab.do?command=CaricaImport';
}


function intercettaBottoneValidaGlobale(statoValidazione)
{
	
	

	$( '#tabValidazioneDialog tr:not(:first)').remove();

	
	$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">NOTE</td><td><textarea row=\"6\" cols=\"33\" name=\"noteValidazione\"></textarea><input type=\"hidden\" name=\"statoValidazione\" value=\"'+statoValidazione+'\"></td></tr>');
	
	$( '#tabValidazioneDialog  tr:last').after('<tr><td colspan=\"2\">   <input type = "button" value = "Salva" onClick="checkFormValidazione(this, this.form)"></td></tr>');         	



	
	if($("input[type='radio'][name='candidato_scelto']:checked").length	) 
	{

		var idStabCandidatoScelto = $("input[type='radio'][name='candidato_scelto']:checked").val();
		var ragioneSocialeStab =  $("input[type='radio'][name='candidato_scelto']:checked").attr("descrizione");

		$( '#tabValidazioneDialog  tr:last').after('<tr><td class=\"formLabel\">CANDIDATO SCELTO</td><td><input type = \"hidden\" name = \"candidato_scelto\" value=\"'+idStabCandidatoScelto+'\">'+ragioneSocialeStab+'</td></tr>');

	}
	
	$( '#validazioneDialog' ).dialog('open');

	
	
}


function intercettaScaricaPlanimetriaBut(urlDest)
{
	var thisWindow = window;
	window.open(urlDest);
	thisWindow.focus();
	//alert(urlDest);
}	
function chiudiERicarica()
{
	window.opener.loadModalWindow();
	window.opener.location.reload(false);
	window.close();
} 