/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 * 
 */

function aggiungiCampiEstesi(attivita,idTabellaInCuiInserire,callback_obj) //ritorna l'html
{
	//console.log("idLinea" + attivita.idAttivita);
	$.ajax(
				{ 
						  type : "GET",
						  url: "SuapStab.do?command=OttieniCampiEstesi&idLinea="+attivita.idAttivita,
						  dataType: "json",
						  success: function(data)
						  {
							  if(data.length == 0 || data.ordine.length == 0 || JSON.parse(data.ordine).length == 0)
								  return;
							  
							  var s =""; //"<table style=\"width:100%;\">";
							  console.log("********************ricevuto "+data);
							  
							  /*var maxLen = 0;
							  for(var k in data)
							  {
								  if(data[k].length > maxLen)
							  		maxLen = data[k].length;
							  }
							  */
							  
							  
							 // for(var k in data)
							  var ordine = JSON.parse(data.ordine);
							  
							  for(var j=0;j<ordine.length;j++)
							  {	
									//console.log(data[k] );
								  	var k = ordine[j];
								  	if(data[k].match(/dummy_label/))
								  	{
								  		s+="<tr class=\"tr_campi_estesi\"><td colspan=\"2\" style=\"font-weight: bold; opacity:0.8 \">"+k.toUpperCase()+"&nbsp;</td> </tr>";
								  	}
								  	else if(data[k].match(/horizontal_line/))
								  	{
								  		s+="<tr class=\"tr_campi_estesi\"><td style=\"font-weight: bold; opacity:0.8 \"><hr/></td><td>&nbsp;</td></tr>";
								  	}
								  	else if(data[k].match(/text/) || data[k].match(/number/) || data[k].match(/select/))
									{
										s+="<tr class=\"tr_campi_estesi\"><td style=\"font-weight: bold; opacity:0.8 \">"+k.toUpperCase()+"&nbsp;</td><td>"+data[k]+"</td></tr>";
										
									}
									else s+="<tr class=\"tr_campi_estesi\"><td style=\"font-weight: bold; opacity:0.8 \" nowrap>"+data[k]+"</td></tr>";
									
									//s+="<tr><td>&nbsp;</td></tr>";
							  }
							  //s+="</table>";
							  //aggiungo questa tabella creata, fatta di inputs, come ultima riga della tabella presa in input
							  $('#'+idTabellaInCuiInserire+' tr:last').after(s);
							  
							  /*per il bottone di generazione cf*/
							  /*per hp uno solo per linea di cf e devono esistere degli input field con i seguenti names... */

							  var campoCodiciFiscali = $('#'+idTabellaInCuiInserire+" [name='codice_fiscale"+attivita.idAttivita+"'].codice_fiscale");
							  campoCodiciFiscali.attr("minlength",16);
							  
							  

							  if(campoCodiciFiscali.length > 0)
							  {
								
								  console.log("arrivato un campo di tipo codice_fiscale. Aggiungo bottone per generarlo")
								  /*mancando sul db una tabella per la minlength, la metto per i codici fiscali */
								  campoCodiciFiscali.attr("minlength",16);
								  
								  var bottoneGeneratoreCF = $("<input></input>",{type : 'button'}).on("click",
										  {
									  idTabella : idTabellaInCuiInserire,
									  idOutput : campoCodiciFiscali.attr("id")

										  },function(evt){
											  
											  /*estraggo i valori utilizzati per calcolare cf */
											  var idTabella = evt.data.idTabella;
											  var nome = $('#'+idTabella+" [name='nome"+attivita.idAttivita+"']")[0];
											  var cognome = $('#'+idTabella+" [name='cognome"+attivita.idAttivita+"']")[0];
											  var sesso = $('#'+idTabella+" [name='sesso"+attivita.idAttivita+"']")[0];
											  var comune = $('#'+idTabella+" [name='comune_nascita"+attivita.idAttivita+"']")[0];
											  var data_nascita = $('#'+idTabella+" [name='data_nascita"+attivita.idAttivita+"']")[0];
											  calcolaCF2(sesso,nome,cognome,comune,data_nascita,evt.data.idOutput);

											  
											  
										  }).val("GENERA CF");

								  
								  campoCodiciFiscali.parent().append(bottoneGeneratoreCF);
								  
								  /*inoltre metto su tutti i campi usati per il calcolo del cf, un handler per l'onchange che fa aggiornare il cf */
								  var richiediAggiornamentoCF = function(evt)
								  {
									  /*
									  if( $("#"+evt.data.id).val().trim().length == 0)
									  {
										  return;
									  }
									  
									  bottoneGeneratoreCF.trigger("click"); */
									  campoCodiciFiscali.val("");
								  }
								  $('#'+idTabellaInCuiInserire+" [name='nome"+attivita.idAttivita+"']").on("change",{id : campoCodiciFiscali.attr("id")} ,richiediAggiornamentoCF);
								  $('#'+idTabellaInCuiInserire+" [name='cognome"+attivita.idAttivita+"']").on("change",{id : campoCodiciFiscali.attr("id")} ,richiediAggiornamentoCF);
								  $('#'+idTabellaInCuiInserire+" [name='sesso"+attivita.idAttivita+"']").on("change",{id : campoCodiciFiscali.attr("id")} ,richiediAggiornamentoCF);
								  $('#'+idTabellaInCuiInserire+" [name='comune_nascita"+attivita.idAttivita+"']").on("change",{id : campoCodiciFiscali.attr("id")} ,richiediAggiornamentoCF);
								  $('#'+idTabellaInCuiInserire+" [name='data_nascita"+attivita.idAttivita+"']").on("change",{id : campoCodiciFiscali.attr("id")} ,richiediAggiornamentoCF);
								  $('#'+idTabellaInCuiInserire+" [name='data_nascita"+attivita.idAttivita+"'] + .trigger.datepick-trigger").on("click",{id : campoCodiciFiscali.attr("id")} ,richiediAggiornamentoCF);
								  
							  }
							  
									  
							   
							 
							  
							 //comportamento custom per il checkbox autorizzazione
							 //aggiungo comportamento dinamico per la spunta di "autorizzazione" per rendere obbligatori o meno gli altri campi
							 //ad esso legati
							 //$('#'+idTabellaInCuiInserire+" input#autorizzazione").on('change',{idTab : idTabellaInCuiInserire },function(evento)
							 $('body').on('change','#'+idTabellaInCuiInserire+" input#autorizzazione" ,{idTab : idTabellaInCuiInserire },function(evento)
							 {
								 var targ = $(evento.target);
								 var checked = targ.prop("checked");
								 
								 if(checked)
								 {//abilito i associati all'autorizzazione e li metto obbligatori
									 
									 $('#'+evento.data.idTab+" input.data_autorizzazione").closest("tr").removeAttr('style');
									 $('#'+evento.data.idTab+" input.data_autorizzazione").prop('required',true);
									 $('#'+evento.data.idTab+" input.data_autorizzazione").prop('disabled',false);
									 
									 
									 $('#'+evento.data.idTab+" input.desc_autorizzazione").closest("tr").removeAttr('style');
									 $('#'+evento.data.idTab+" input.desc_autorizzazione").prop('required',true);
									 $('#'+evento.data.idTab+" input.desc_autorizzazione").prop('disabled',false);
									  
								 }
								 else //il contrario
								 {

									 $('#'+evento.data.idTab+" input.data_autorizzazione").closest("tr").css('display','none');
									 $('#'+evento.data.idTab+" input.data_autorizzazione").prop('required',false);
									 $('#'+evento.data.idTab+" input.data_autorizzazione").prop('disabled',true);
									 	
									 $('#'+evento.data.idTab+" input.desc_autorizzazione").closest("tr").css('display','none');
									 $('#'+evento.data.idTab+" input.desc_autorizzazione").prop('required',false);
									 $('#'+evento.data.idTab+" input.desc_autorizzazione").prop('disabled',true);
									  
								 }
						     });	
							 
							 
							 $('#'+idTabellaInCuiInserire+" input.data_autorizzazione").datepick({dateFormat: 'dd/mm/yyyy', maxDate: 0, showOnFocus: false, showTrigger: '#calImg'});
							 //di default parte che i campi sono disattivati...
							 
							 $('#'+idTabellaInCuiInserire+" input.data_autorizzazione").closest("tr").css('display','none');
							 $('#'+idTabellaInCuiInserire+" input.data_autorizzazione").prop('required',false);
							 $('#'+idTabellaInCuiInserire+" input.data_autorizzazione").prop('disabled',true);
							 
							 $('#'+idTabellaInCuiInserire+" input.desc_autorizzazione").closest("tr").css('display','none');
							 $('#'+idTabellaInCuiInserire+" input.desc_autorizzazione").prop('required',false);
							 $('#'+idTabellaInCuiInserire+" input.desc_autorizzazione").prop('disabled',true);
							 
							 //e abilito i date picker
							 //attenzione: i campi data hanno stesso id, mentre il datepick se richiamato su una collezione jquery
							 //lo setta solo sul primo, quindi occorre estrarre solo l'ultimo input data 
							 
							 
							// $('#'+idTabellaInCuiInserire+" input#data_autorizzazione").datePicker();
							 
							 if(callback_obj != undefined)
							 {
								 callback_obj.callback_terminazione();
							 }
							 
							  
							 
						  },
						error: function()
						{
							 
							console.log("errore");
						},
						complete: function()
						{
							  console.log("COMPLETE");
						}
				  
					
				}
	);
}


function verificaEsistenzaCampiEstesiDaOpuDiTipoScelto(idStab,callback_obj,tipo) /*tipo = anagrafici o ex code l30*/
{
	var urlDest = '';
	if(tipo.toLowerCase() == 'anagrafici' )
	{
		urlDest = 'SuapStab.do?command=OttieniEsistenzaAlmenoUnCampoEstesoAnagrafica&idStab='+idStab;
	}
	else if(tipo.toLowerCase() == 'excodel30')
	{
		urlDest = 'SuapStab.do?command=OttieniEsistenzaAlmenoUnCampoEstesoExL30&idStab='+idStab ;
	}
	
	$.ajax(
			{
				url : urlDest
				,method: 'get'
				,dataType : 'json'
				,success : function(data)
				{
					callback_obj.callback(data);
				}
				,error: function()
				{
					 
					console.log("errore verificaEsistenzaCampiEstesiDaOpuDiTipoScelto");
				},
				
			}
	);

}

 


function aggiungiCampiEstesiDaOpuDiTipoScelto(idrelstablpopu,idTabellaInCuiInserire,callback_obj,tipo) //per i campi estesi da linee gia' validate
{ //tipo indica se è di tipo anagrafici (es nome medico ) o di tipo ex codice l30
	//console.log("idLinea" + attivita.idAttivita);
	var urlDest = '';
	if(tipo.toLowerCase() == 'anagrafici')
	{
		urlDest =  'SuapStab.do?command=OttieniCampiEstesiAnagraficiConValori&idStabRelLpOpu='+idrelstablpopu;
	}
	else if (tipo.toLowerCase() == 'excodel30')
	{
		urlDest = 'SuapStab.do?command=OttieniCampiEstesiExCodiceL30&idStabRelLpOpu='+idrelstablpopu;
	}
	
	$.ajax(
				{ 
						  type : "GET",
						  url: urlDest,
						  dataType: "json",
						  success: function(data)
						  {
							  if(data.length == 0 || data.ordine.length == 0 || JSON.parse(data.ordine).length == 0)
							  {
								  $('#'+idTabellaInCuiInserire+' tr:last').after(
										  $("<td></td>").text("Per la  linea scelta non esistono informazioni aggiuntive").css("font-weight","bold")
								  );
								  
								  return;
							  }
							  var s =""; //"<table style=\"width:100%;\">";
							  console.log("********************ricevuto "+data);
							  
							  /*var maxLen = 0;
							  for(var k in data)
							  {
								  if(data[k].length > maxLen)
							  		maxLen = data[k].length;
							  }
							  */
							  
							  
							 // for(var k in data)
							  var ordine = JSON.parse(data.ordine);
							  
							  for(var j=0;j<ordine.length;j++)
							  {	
									//console.log(data[k] );
								  	
								  	var k = ordine[j];
								  	if(data[k].match(/dummy_label/))
								  	{
								  		s+="<tr class=\"tr_campi_estesi\"><td width=\"30%\" colspan=\"2\" style=\"font-weight: bold; opacity:0.8 \">"+k.toUpperCase()+"&nbsp;</td> </tr>";
								  	}
								  	else if(data[k].match(/horizontal_line/))
								  	{
								  		s+="<tr class=\"tr_campi_estesi\"><td width=\"30%\" style=\"font-weight: bold; opacity:0.8 \"><hr/></td><td>&nbsp;</td></tr>";
								  	}
								  	else if(data[k].match(/codice_stazione_recapito/) || data[k].match(/text/) || data[k].match(/number/) || data[k].match(/select/))
									{
										s+="<tr class=\"tr_campi_estesi\"><td width=\"30%\" style=\"font-weight: bold; opacity:0.8 \">"+k.toUpperCase()+"&nbsp;</td><td>"+data[k]+"</td></tr>";
										
									}
									else s+="<tr class=\"tr_campi_estesi\"><td style=\"font-weight: bold; opacity:0.8 \" nowrap>"+data[k]+"</td></tr>";
									
									//s+="<tr><td>&nbsp;</td></tr>";
							  }
							  //s+="</table>";
							  //aggiungo questa tabella creata, fatta di inputs, come ultima riga della tabella presa in input
							  $('#'+idTabellaInCuiInserire+' tr:last').after(s);
							  console.log($('#'+idTabellaInCuiInserire+' tr:last').html());
							  /*per il bottone di generazione cf*/
							  /*per hp uno solo per linea di cf e devono esistere degli input field con i seguenti names... */

							 
							  var campoCodiciFiscali = $('#'+idTabellaInCuiInserire+" [name='codice_fiscale"+idrelstablpopu+"'].codice_fiscale");

							  if(campoCodiciFiscali.length > 0)
							  {
								  console.log("arrivato un campo di tipo codice_fiscale. Aggiungo bottone per generarlo")
								   /*mancando sul db una tabella per la minlength, la metto per i codici fiscali */
								  campoCodiciFiscali.attr("minlength",16);
								  
								  var bottoneGeneratoreCF = $("<input></input>",{type : 'button'}).on("click",
										  {
									  idTabella : idTabellaInCuiInserire,
									  idOutput : campoCodiciFiscali.attr("id")

										  },function(evt){
											  
											  
											  var idTabella = evt.data.idTabella;
											  var nome = $('#'+idTabella+" [name='nome"+idrelstablpopu+"']")[0];
											  var cognome = $('#'+idTabella+" [name='cognome"+idrelstablpopu+"']")[0];
											  var sesso = $('#'+idTabella+" [name='sesso"+idrelstablpopu+"']")[0];
											  var comune = $('#'+idTabella+" [name='comune_nascita"+idrelstablpopu+"']")[0];
											  var data_nascita = $('#'+idTabella+" [name='data_nascita"+idrelstablpopu+"']")[0];
											  calcolaCF2(sesso,nome,cognome,comune,data_nascita,evt.data.idOutput);


											  //console.log("impossibile registrare handler generazione CF per mancanza di tutti i campi necessari di input"); 
											  
											  
										  }).val("GENERA CF");
								  
								  campoCodiciFiscali.parent().append(bottoneGeneratoreCF);
								  
								  /*inoltre metto su tutti i campi usati per il calcolo del cf, un handler per l'onchange che fa aggiornare il cf */
								  var richiediAggiornamentoCF = function(evt)
								  {
									  /*
									  if( $("#"+evt.data.id).val().trim().length == 0)
									  {
										  return;
									  }
									  
									  bottoneGeneratoreCF.trigger("click"); */
									  campoCodiciFiscali.val("");
								  }
								  
								  $('#'+idTabellaInCuiInserire+" [name='nome"+idrelstablpopu+"']").on("change",{id : campoCodiciFiscali.attr("id")} ,richiediAggiornamentoCF);
								  $('#'+idTabellaInCuiInserire+" [name='cognome"+idrelstablpopu+"']").on("change",{id : campoCodiciFiscali.attr("id")} ,richiediAggiornamentoCF);
								  $('#'+idTabellaInCuiInserire+" [name='sesso"+idrelstablpopu+"']").on("change",{id : campoCodiciFiscali.attr("id")} ,richiediAggiornamentoCF);
								  $('#'+idTabellaInCuiInserire+" [name='comune_nascita"+idrelstablpopu+"']").on("change",{id : campoCodiciFiscali.attr("id")} ,richiediAggiornamentoCF);
								  $('#'+idTabellaInCuiInserire+" [name='data_nascita"+idrelstablpopu+"']").on("change",{id : campoCodiciFiscali.attr("id")} ,richiediAggiornamentoCF);
								  $('#'+idTabellaInCuiInserire+" [name='data_nascita"+idrelstablpopu+"'] + .trigger.datepick-trigger").on("click",{id : campoCodiciFiscali.attr("id")} ,richiediAggiornamentoCF);
								  /*******************************/
								  
								  
							  }
							  
									  
							   
							 
							  
							 //comportamento custom per il checkbox autorizzazione
							 //aggiungo comportamento dinamico per la spunta di "autorizzazione" per rendere obbligatori o meno gli altri campi
							 //ad esso legati
							 //$('#'+idTabellaInCuiInserire+" input#autorizzazione").on('change',{idTab : idTabellaInCuiInserire },function(evento)
							 $('body').on('change','#'+idTabellaInCuiInserire+" input#autorizzazione" ,{idTab : idTabellaInCuiInserire },function(evento)
							 {
								 var targ = $(evento.target);
								 var checked = targ.prop("checked");
								 
								 if(checked)
								 {//abilito i associati all'autorizzazione e li metto obbligatori
									 
									 $('#'+evento.data.idTab+" input.data_autorizzazione").closest("tr").removeAttr('style');
									 $('#'+evento.data.idTab+" input.data_autorizzazione").prop('required',true);
									 $('#'+evento.data.idTab+" input.data_autorizzazione").prop('disabled',false);
									 
									 
									 $('#'+evento.data.idTab+" input.desc_autorizzazione").closest("tr").removeAttr('style');
									 $('#'+evento.data.idTab+" input.desc_autorizzazione").prop('required',true);
									 $('#'+evento.data.idTab+" input.desc_autorizzazione").prop('disabled',false);
									  
								 }
								 else //il contrario
								 {

									 $('#'+evento.data.idTab+" input.data_autorizzazione").closest("tr").css('display','none');
									 $('#'+evento.data.idTab+" input.data_autorizzazione").prop('required',false);
									 $('#'+evento.data.idTab+" input.data_autorizzazione").prop('disabled',true);
									 	
									 $('#'+evento.data.idTab+" input.desc_autorizzazione").closest("tr").css('display','none');
									 $('#'+evento.data.idTab+" input.desc_autorizzazione").prop('required',false);
									 $('#'+evento.data.idTab+" input.desc_autorizzazione").prop('disabled',true);
									  
								 }
						     });	
							 
							 
							 $('#'+idTabellaInCuiInserire+" input.data_autorizzazione").datepick({dateFormat: 'dd/mm/yyyy', maxDate: 0, showOnFocus: false, showTrigger: '#calImg'});
							 //di default parte che i campi sono disattivati...
							 
							 $('#'+idTabellaInCuiInserire+" input.data_autorizzazione").closest("tr").css('display','none');
							 $('#'+idTabellaInCuiInserire+" input.data_autorizzazione").prop('required',false);
							 $('#'+idTabellaInCuiInserire+" input.data_autorizzazione").prop('disabled',true);
							 
							 $('#'+idTabellaInCuiInserire+" input.desc_autorizzazione").closest("tr").css('display','none');
							 $('#'+idTabellaInCuiInserire+" input.desc_autorizzazione").prop('required',false);
							 $('#'+idTabellaInCuiInserire+" input.desc_autorizzazione").prop('disabled',true);
							 
							 //e abilito i date picker
							 //attenzione: i campi data hanno stesso id, mentre il datepick se richiamato su una collezione jquery
							 //lo setta solo sul primo, quindi occorre estrarre solo l'ultimo input data 
							 
							 
							// $('#'+idTabellaInCuiInserire+" input#data_autorizzazione").datePicker();
							 
							 if(callback_obj != undefined)
							 {
								 callback_obj.callback_terminazione();
							 }
							 
							  
							 
						  },
						error: function()
						{
							 
							console.log("errore");
						},
						complete: function()
						{
							  console.log("COMPLETE");
						}
				  
					
				}
	);
}