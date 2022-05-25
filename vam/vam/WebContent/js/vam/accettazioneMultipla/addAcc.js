/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function abilitaDisabilitaSceltaAsl(checkbox)
{
	if(checkbox.checked)
		document.getElementById('idAslRitrovamento').disabled=false;
	else
	{
		document.getElementById('idAslRitrovamento').disabled="disabled";
		document.getElementById('idAslRitrovamento').value="";
	}
}

function abilitaDisabilitaIntraFuoriAsl(checkbox)
{
	if(checkbox.checked)
	{
		document.getElementById('intraFuoriAsl').disabled=false;
	}
	else
	{
		document.getElementById('intraFuoriAsl').disabled="disabled";
		document.getElementById('intraFuoriAsl').checked=false;
	}
}

function abilitaDisabilitaVersoAssocCanili(checkbox)
{
	if(checkbox.checked)
	{
		document.getElementById('versoAssocCanili').disabled=false;
	}
	else
	{
		document.getElementById('versoAssocCanili').disabled="disabled";
		document.getElementById('versoAssocCanili').checked=false;
	}
}

function abilitaDisabilitaNote(checkbox)
{
	var campoNote;
	if(checkbox.id=="op_47")
		campoNote = 'noteRicoveroInCanile';
	if(checkbox.id=="op_48")
		campoNote = 'noteIncompatibilitaAmbientale';
	if(checkbox.id=="op_49")
		campoNote = 'noteAltro';
		
	if(checkbox.checked)
		document.getElementById(campoNote).disabled=false;
	else
	{
		document.getElementById(campoNote).disabled="disabled";
		document.getElementById(campoNote).value="";
	}
}

function abilitaDisabilitaAttivitaEsterne(checkbox)
{
	if(checkbox.checked)
	{
		document.getElementById('dettagliAttivitaEsterna').style.display="block";
		document.getElementById('idAttivitaEsterna').focus();
		document.getElementById('idAttivitaEsterna').disabled=false;
		document.getElementById('idComuneAttivitaEsterna').disabled=false;
		document.getElementById('indirizzoAttivitaEsterna').disabled=false;
	}
	else
	{
		document.getElementById('dettagliAttivitaEsterna').style.display="none";
		document.getElementById('idAttivitaEsterna').disabled="disabled";
		document.getElementById('idComuneAttivitaEsterna').disabled="disabled";
		document.getElementById('indirizzoAttivitaEsterna').disabled="disabled";
		document.getElementById('idAttivitaEsterna').value="";
		document.getElementById('idComuneAttivitaEsterna').value="";
		document.getElementById('indirizzoAttivitaEsterna').value="";
	}
}


function abilitaDisabilitaTipoTrasferimento(checkbox)
{
	if(checkbox.checked)
		document.getElementById('idTipoTrasferimento').disabled=false;
	else
	{
		document.getElementById('idTipoTrasferimento').disabled="disabled";
		document.getElementById('idTipoTrasferimento').value="";
	}
}

function checkform(idUtente,idAnimale,idClinica,pregresso,modifica)
{
	var toReturn = true;
	var selezionateOperazioni = false;

	if(!modifica)
	{
	Test.controlloDataAccettazioniRecenti(idAnimale, idUtente, document.getElementById('data').value, idClinica, 
				{
						callback:function(msg) 
						{ 
							if(msg!=null && msg!='')
							{
								if(pregresso)
								{
									if(myConfirm(msg+'.Si desidera proseguire per il recupero di accettazioni pregresse?'))
										toReturn = true;
									else
										toReturn = false;
								}
								else
								{
									alert(msg);
									toReturn = false;
								}
								$( "#dialog-modal" ).dialog( "close" );
							}
						},
						async: false,
						timeout:5000,
						errorHandler:function(message, exception)
						{
						    //Session timedout/invalidated
						    if(exception && exception.javaClassName=='org.directwebremoting.impl.LoginRequiredException')
						    {
						        alert(message);
						        //Reload or display an error etc.
						        window.location.href=window.location.href;
						    }
						    else
						        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
						 }
	 			});
	
	//Ricontrollo possibilit� di apertura nuove accettazioni, per evitare salvataggi al tasto indietro, F5 ecc
	Test.possibileAprire(idAnimale, idUtente,
				{
						callback:function(msg) 
						{
							if(msg!=null && msg!='')
							{
								alert(msg);
								toReturn = false;
								
								$( "#dialog-modal" ).dialog( "close" );
							}
							
						},
						async: false,
						timeout:5000,
						errorHandler:function(message, exception)
						{
						    //Session timedout/invalidated
						    if(exception && exception.javaClassName=='org.directwebremoting.impl.LoginRequiredException')
						    {
						        alert(message);
						        //Reload or display an error etc.
						        window.location.href=window.location.href;
						    }
						    else
						        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
						 }
	 			});
	    }
	
		for(var i=0;i<=opSelezionate.length-1;i++){
		    //Se � checkato oppure, se � l'op 'iscrizione anagrafe' e quindi � un campo hidden, ha il value=='on'
			if  (document.getElementById("op_"+opSelezionate[i]).checked==true || document.getElementById("op_"+opSelezionate[i]).value=='on'){	
				selezionateOperazioni = true;
			}
		}
		
	if(!controllaDataAnnoCorrente(document.getElementById('data'),'Data'))
	{
		toReturn = false;
		$( "#dialog-modal" ).dialog( "close" );
	}
		
	if(toReturn == true && document.getElementById('idAttivitaEsterna')!=null && document.getElementById('idAttivitaEsterna').disabled==false && document.getElementById('idAttivitaEsterna').value=="")
	{
		alert("Selezionare l'attivita esterna svolta");
		toReturn = false;
		$( "#dialog-modal" ).dialog( "close" );
	}
	if(toReturn == true && document.getElementById('idComuneAttivitaEsterna')!=null && document.getElementById('idComuneAttivitaEsterna').disabled==false && document.getElementById('idComuneAttivitaEsterna').value=="")
	{
		alert("Selezionare il comune dell'attivita esterna svolta");
		toReturn = false;
		$( "#dialog-modal" ).dialog( "close" );
	}	
	if(document.getElementById('idAslRitrovamento')!=null && document.getElementById('idAslRitrovamento').disabled==false && document.getElementById('idAslRitrovamento').value=="")
	{
		alert("Selezionare l'asl di ritrovamento dell'animale");
		toReturn = false;
		$( "#dialog-modal" ).dialog( "close" );
	}
	if(toReturn == true && document.getElementById('idTipoTrasferimento')!=null && document.getElementById('idTipoTrasferimento').disabled==false && document.getElementById('idTipoTrasferimento').value=="")
	{
		alert("Selezionare il tipo di trasferimento");
		toReturn = false;
		$( "#dialog-modal" ).dialog( "close" );
	}
	
	if(document.getElementById('tipoRichiedente').value=='3' && document.getElementById('idRichiedenteAsl').value=='-1')
	{
		alert("Selezionare l'ASL del richiedente");
		toReturn = false;
		$( "#dialog-modal" ).dialog( "close" );
	}
	
	if(document.getElementById('tipoRichiedente').value=='3' && document.getElementById('idRichiedenteAsl').value!='-1' && document.getElementById('idDipendenteAsl').value=='' )
	{
		alert("Selezionare almeno un richiedente");
		toReturn = false;
		$( "#dialog-modal" ).dialog( "close" );
	}
	
	if(document.getElementById('interventoPersonaleInterno').checked && document.getElementById('idRichiedenteInterno').value=='')
	{
		alert("Selezionare il personale interno");
		toReturn = false;
		$( "#dialog-modal" ).dialog( "close" );
	}
	
	if(document.getElementById('tipoRichiedente').value=='4' && document.getElementById('idAssociazione').value=='-1')
	{
		alert("Selezionare l'Associazione");
		toReturn = false;
		$( "#dialog-modal" ).dialog( "close" );
	}
	
	if(document.getElementById('intraFuoriAsl')!=null && document.getElementById('versoAssocCanili')!=null && document.getElementById('versoAssocCanili').checked==true && document.getElementById('intraFuoriAsl').checked==true)
	{
		alert("Scegliere una sola opzione tra 'fuori asl' e 'verso Associazioni/Canili'");	
		toReturn = false;
		$( "#dialog-modal" ).dialog( "close" );
	}
	
	
	
	
	var data = document.getElementById('data').value;
	var data_limite = '01/01/2014';
	var data_limite2 = '01/02/2014';

	var arr1 = data.split('/');
	var arr2 = data_limite.split('/');
	var arr3 = data_limite2.split('/');

	var d1 = new Date(arr1[2],arr1[1]-1,arr1[0]);
	var d2 = new Date(arr2[2],arr2[1]-1,arr2[0]);
	var d3 = new Date(arr3[2],arr3[1]-1,arr3[0]);
	var oggi = new Date();

	var r1 = d1.getTime();
	var r2 = d2.getTime();
	var r3 = d3.getTime();
	var r4 = oggi.getTime();
	
    if(!modifica)
    {
	if((opSelezionate.indexOf('1')>=0 || opSelezionate.indexOf('9')>=0) && r1<r2 && r4>=r3)
	{
		alert("Non � consentito l'inserimento di anagrafiche animali e registrazioni di sterilizzazione relative ad anni precedenti");
		toReturn = false;
		$( "#dialog-modal" ).dialog( "close" );
	}
    }
	
	var idTipoTrasferimento = 0;
	if(document.getElementById('idTipoTrasferimento')!=null)
		idTipoTrasferimento = document.getElementById('idTipoTrasferimento').value;
	var intraFuoriAsl = false;
	if(document.getElementById('intraFuoriAsl')!=null)
		intraFuoriAsl = document.getElementById('intraFuoriAsl').checked;
	var versoAssocCanili = false;
	if(document.getElementById('versoAssocCanili')!=null)
		versoAssocCanili = document.getElementById('versoAssocCanili').checked;
	 
	if(toReturn == true)
	{
		Test.check(opSelezionate, idUtente, idAnimale,  
 			document.getElementById('tipoRichiedente').value, '', 'accettazione', idTipoTrasferimento, intraFuoriAsl, versoAssocCanili,
 												{
   														callback:function(msg) 
   														{ 
   															if(msg!=null && msg!='' /*&& !myConfirm(msg)*/)
   															{
   																alert(msg);
   																toReturn = false;
   																$( "#dialog-modal" ).dialog( "close" );
   															}
   														},
   														async: false,
   														timeout:20000,
   														errorHandler:function(message, exception)
   														{
   														    //Session timedout/invalidated
   														    if(exception && exception.javaClassName=='org.directwebremoting.impl.LoginRequiredException')
   														    {
   														        alert(message);
   														        //Reload or display an error etc.
   														        window.location.href=window.location.href;
   														    }
   														    else
   														        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
   														 }
										 			});
  }
  return toReturn;
	
}


function condizionaOperazioni(operazioniCondizionate,operazioneCondizionante,defaultEnabled)
{
    var opCondizionanteChecked = operazioneCondizionante.checked;
    operazioniCondizionate = operazioniCondizionate.substring(1,operazioniCondizionate.length-1);
	var op = operazioniCondizionate.split(', ')
	for(var i=0;i<op.length;i++)
	{
		var idOpCondizionata             = op[i].split("@")[0];
		var opDaFare         	   		 = op[i].split("@")[1];
		var opCondizionataDisabledOrigin = document.getElementById('opOriginalDisabled_'+idOpCondizionata).value;
		if(opCondizionataDisabledOrigin==null || opCondizionataDisabledOrigin=="")
			opCondizionataDisabledOrigin = false
		if(opCondizionanteChecked)
		{
			if(opDaFare=='enable')
			{
				document.getElementById('op_'+idOpCondizionata).disabled = false;
			}	
		}
		else
		{
			if(opDaFare=='enable')
			{
				document.getElementById('op_'+idOpCondizionata).disabled = defaultEnabled;
				if(opCondizionataDisabledOrigin)
					document.getElementById('op_'+idOpCondizionata).checked  = false;
			}
		}
	}
}


function abilitazioneOperazione(richiedenteAbilitante, operazioniDaAbilitare,richiedenteSelezionato)
{
	var opCondizionanteSelected;
	if(richiedenteSelezionato.value == richiedenteAbilitante)
     	opCondizionanteSelected = true;
    else
    	opCondizionanteSelected = false;
		
	var opCondizionataDisabledOrigin = document.getElementById('opOriginalDisabled_'+operazioniDaAbilitare).value;
	if(opCondizionataDisabledOrigin==null || opCondizionataDisabledOrigin=="")
		opCondizionataDisabledOrigin = false
	if(opCondizionanteSelected)
		document.getElementById('op_'+operazioniDaAbilitare).disabled = false;
	else
	{
		document.getElementById('op_'+operazioniDaAbilitare).disabled = opCondizionataDisabledOrigin;
		if(opCondizionataDisabledOrigin)
			document.getElementById('op_'+operazioniDaAbilitare).checked  = false;
	}
}


function popolaOpSelezionate(idOp)
{
	if (idOp!=1){ //altre checkbox
		if(document.getElementById('op_' + idOp).checked==true){
			opSelezionate[opSelezionate.length] = idOp;
		}
		else {
			opSelezionate.splice(opSelezionate.indexOf(idOp), 1);
		}
	}
	else {  //caso iscrizione anagrafe in quanto � un campo hidden
		if(document.getElementById('op_' + idOp).value=='on'){
			opSelezionate[opSelezionate.length] = idOp;
		}
		else {
			opSelezionate.splice(opSelezionate.indexOf(idOp), 1);
		}
	}
}


function confermaModifiche(conferma)
{
	if(conferma)
	{
		document.getElementById('idRichiedenteInterno').value = document.getElementById('idRichiedenteInternoTemp').value;
		document.getElementById('personaleInternoSelezionato').innerHTML = document.getElementById('personaleInternoSelezionatoTemp').innerHTML;
	}
	else
	{
		document.getElementById('idRichiedenteInternoTemp').value = document.getElementById('idRichiedenteInterno').value;
		document.getElementById('personaleInternoSelezionatoTemp').innerHTML = document.getElementById('personaleInternoSelezionato').innerHTML;
	}
}

function confermaModificheAsl(conferma)
{
	if(conferma)
	{
		document.getElementById('idDipendenteAsl').value = document.getElementById('idDipendenteAslTemp').value;
		document.getElementById('personaleAslSelezionato').innerHTML = document.getElementById('personaleAslSelezionatoTemp').innerHTML;
	}
	else
	{
		document.getElementById('idDipendenteAslTemp').value = document.getElementById('idDipendenteAsl').value;
		document.getElementById('personaleAslSelezionatoTemp').innerHTML = document.getElementById('personaleAslSelezionato').innerHTML;
	}
}


function popolaPersIntSelezionati(idPersInt, descPersInt)
{
	var persInt = document.getElementById('idRichiedenteInternoTemp').value;
	var persIntSel = document.getElementById('personaleInternoSelezionatoTemp').innerHTML;
	var separator = ",";
	var persIntArray = persInt.split(",");
	var presente = false;
	var indice;
	for(var i=0;i<persIntArray.length;i++)
	{
		if(persIntArray[i]==idPersInt)
		{
			presente = true;
			break;
		}
	}
	if(document.getElementById('idRichiedenteInternoTemp').value=="")
		separator = "";
		
	if(!presente)
	{
		document.getElementById('idRichiedenteInternoTemp').value=document.getElementById('idRichiedenteInternoTemp').value+separator+idPersInt;
		document.getElementById('personaleInternoSelezionatoTemp').innerHTML=document.getElementById('personaleInternoSelezionatoTemp').innerHTML+"<tr><td>"+descPersInt.replace("***","'")+"</td></tr>";
	}
	else
	{
		persIntArray.splice(i,1);
		document.getElementById('idRichiedenteInternoTemp').value = persIntArray;
		persIntSel = persIntSel.replace("<tr><td>"+descPersInt.replace("***","'")+"</td></tr>","");
		document.getElementById('personaleInternoSelezionatoTemp').innerHTML = persIntSel;
	}
}


function popolaPersAslSelezionati(idPersAsl,descPersAsl)
{
	var persAsl = document.getElementById('idDipendenteAslTemp').value;
	var persAslSel = document.getElementById('personaleAslSelezionatoTemp').innerHTML;
	var separator = ",";
	var persAslArray = persAsl.split(",");
	var presente = false;
	var indice;
	for(var i=0;i<persAslArray.length;i++)
	{
		if(persAslArray[i]==idPersAsl)
		{
			presente = true;
			break;
		}
	}
	if(document.getElementById('idDipendenteAslTemp').value=="")
		separator = "";
		
	if(!presente)
	{
		document.getElementById('idDipendenteAslTemp').value=document.getElementById('idDipendenteAslTemp').value+separator+idPersAsl;
		document.getElementById('personaleAslSelezionatoTemp').innerHTML=document.getElementById('personaleAslSelezionatoTemp').innerHTML+"<tr><td>"+descPersAsl.replace("***","'")+"</td></tr>";
	}
	else
	{
		persAslArray.splice(i,1);
		document.getElementById('idDipendenteAslTemp').value = persAslArray;
		persAslSel = persAslSel.replace("<tr><td>"+descPersAsl.replace("***","'")+"</td></tr>","");
		document.getElementById('personaleAslSelezionatoTemp').innerHTML = persAslSel;
	}
}

function contains(a, obj)
{
	for(var i = 0; i < a.length; i++) 
	{
	    if(a[i] === obj)
	    {
	      return true;
	    }
	}
	return false;
}




function selezionaPersInt()
{
		if(document.getElementById('interventoPersonaleInterno').checked)
		{
			var persInt = document.getElementById('idRichiedenteInternoTemp').value;
			var persIntArray = persInt.split(",");
			var persIntIniziale = document.getElementById('div_richiedente_personale_interno').cloneNode(true);
		
			persIntIniziale.id="choosePersInt";
			persIntIniziale.style.display="block";
			var inputs = persIntIniziale.getElementsByTagName("input");
			
			var checkDaSelezionare = new Array(0);
			var esistonoPersInt = false;
			
			for(var i=0;i<inputs.length;i++)
			{
				if(inputs[i].type=="checkbox")
				{
					inputs[i].id=inputs[i].id.replace("checkPersIntIniziale","");
					inputs[i].setAttribute("onclick","popolaPersIntSelezionati('"+inputs[i].value+"','"+inputs[i].name.replace("persInt","").replace("'","***")+"');");
					
					esistonoPersInt = true;
					//Se � stato selezionato prima
					if(contains(persIntArray,inputs[i].id.replace("persInt","")))
					{
						inputs[i].checked=true;
						checkDaSelezionare[checkDaSelezionare.length] = inputs[i];
					}
				}
			}
			
			document.getElementById('div_richiedente_personale_interno').appendChild(persIntIniziale);
			
			mostraPopupChoosePersInt();
		
		}
		
}



function selezionaPersAsl()
{
		if(document.getElementById('idRichiedenteAsl').value>0)
		{
			var persAsl = document.getElementById('idDipendenteAsl').value;
			var persAslArray = persAsl.split(",");
			var asl = document.getElementById('idRichiedenteAsl').value;
			var persAslIniziale = document.getElementById('operatori_asl_'+asl).cloneNode(true);
		
			persAslIniziale.id="choosePersAsl";
			persAslIniziale.style.display="block";
			var inputs = persAslIniziale.getElementsByTagName("input");
			
			var checkDaSelezionare = new Array(0);
			var esistonoPersAsl = false;
			
			for(var i=0;i<inputs.length;i++)
			{
				if(inputs[i].type=="checkbox")
				{
					inputs[i].id=inputs[i].id.replace("checkPersAslIniziale","");
					inputs[i].setAttribute("onclick","popolaPersAslSelezionati('"+inputs[i].value+"','"+inputs[i].name.replace("persAsl","").replace("'","***")+"');");
					
					esistonoPersAsl = true;
					//Se � stato selezionato prima
					if(contains(persAslArray,inputs[i].id.replace("persAsl","")))
					{
						inputs[i].checked=true;
						checkDaSelezionare[checkDaSelezionare.length] = inputs[i];
					}
				}
			}
			
			document.getElementById('div_richiedente_personale_asl').appendChild(persAslIniziale);
			
			mostraPopupChoosePersAsl();
		
		}
		
}


