/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
 $(function() {
            	var Return;
            	$( "#dialogCustomerSatisfaction" ).dialog({
                	autoOpen: false,
                    resizable: false,
                    draggable:false,
                    modal: true,
                    width:650,
                    height:300
                   
            });
 });

function openCustomerSatisfaction()
{
	
	if (document.getElementById("iniTime").value!='')
	{
	$(document).ready(function() {
		
		$('#dialogCustomerSatisfaction').dialog('open');
		});
	}}
	


function closeCustomerSatisfaction()
{
$('#dialogCustomerSatisfaction').dialog('close');
disabilita=0 ;
document.getElementById('iniTime').value='';
}
var disabilita = 0 ;

function saveSatisfaction( data_operazione, username, soddisfatto, descrizione_problema, operazione_eseguita,longtimeini,longtimeend)
{
	
	
	if (soddisfatto=='no' && descrizione_problema.trim().length<15)
		{
		alert('Attenzione, si prega di fornire una descrizione di almeno 15 caratteri per il problema riscontrato.');
		}
	else
		{
		if (document.getElementById("check").checked && document.getElementById("tel").value.trim()=='')
			{
			alert('Attenzione se desiderate essere contattati dal nostro servizio  help-desk fornire un recapito telefonico valido!');

			}
		else
			{
		if (disabilita==0 && document.getElementById('iniTime').value!=''){
			if (document.getElementById("tel").value != '')
				descrizione_problema+="\n [DESIDERO ESSERE CONTATTATO AL "+document.getElementById("tel").value+"]";
				disabilita = 1 ;
				DwrCustomSatisfaction.insertCustomSatisfaction(data_operazione, username, soddisfatto, descrizione_problema, operazione_eseguita,longtimeini,longtimeend,{callback:saveSatisfactionCallBack});
				
		}
			}
		}
}


function calcolaTempoEsecuzione( longtimeini,longtimeend,comando,action)
{
	

		if ( document.getElementById('iniTime').value!=''){
					DwrCustomSatisfaction.calcolaTempoEsecuzioneCustomSatisfaction(longtimeini,longtimeend,comando,action,{callback:calcolaTempoEsecuzioneCallBack});
				
		}
			
		
}




function calcolaTempoEsecuzioneCallBack(secondi)
{
	if (secondi<20)
		document.getElementById("label_tempo_esecuzione").innerHTML="<h3>OPERAZIONE ESEGUITA IN "+secondi+" SECONDI</h3>"

}

function saveSatisfactionCallBack(val)
{

	closeCustomerSatisfaction();
}