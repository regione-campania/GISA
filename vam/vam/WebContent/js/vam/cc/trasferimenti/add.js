/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function checkform(idUtente,idAnimale)
{
	if(document.getElementById('dataRichiesta').value=='')
	{
		alert("Selezionare la data richiesta.");
		document.getElementById('dataRichiesta').focus();
		return false;
	}
	
	if(!controllaDataAnnoCorrente(document.getElementById('dataRichiesta'), 'Data richiesta'))
	{
		return false;
	}
	
	
	if(confrontaDate(document.getElementById('dataRichiesta').value,document.getElementById('dataApertura').value)<0)
	{
		alert("La data richiesta deve essere uguale o successiva alla data di apertura della CC");
		return false;
	}

    var idClinicaDestinazione = document.getElementById('clinicaDestinazioneId').value;
	var toReturn = true;

	
		Test.check(opSelezionate, idUtente, idAnimale, 
 			'', idClinicaDestinazione, 'trasferimento', 0, false,false,
 												{
   														callback:function(msg) 

   														{ 
   															if(msg!=null && msg!='' /*&& !myConfirm(msg)*/){
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
		
		
		var selezionateOperazioni = false;
		for(var i=0;i<=opSelezionate.length-1;i++){
			if  (document.getElementById("op_"+opSelezionate[i]).checked==true){	
				selezionateOperazioni = true;
			}
		}
		if(!selezionateOperazioni)
		{
			alert("Selezionare almeno un'operazione");
			toReturn = false;
			$( "#dialog-modal" ).dialog( "close" );
		}
		
    return toReturn;
	
}


function popolaOpSelezionate(idOp)
{
	if(document.getElementById('op_' + idOp).checked==true)
		opSelezionate[opSelezionate.length] = idOp;
	else
		opSelezionate.splice(opSelezionate.indexOf(idOp), 1);
}