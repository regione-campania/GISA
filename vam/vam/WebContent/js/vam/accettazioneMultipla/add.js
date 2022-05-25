/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function aggiungiMc(indice)
{
	mcSelezionati++;
	
	var tableAggiungiMc = document.getElementById('tableAggiungiMc');
	var trAggiungiMc = document.getElementById('trAggiungiMc'+indice).cloneNode();
	trAggiungiMc.setAttribute("id", 'trAggiungiMc'+mcSelezionati);
	var td1AggiungiMc = document.getElementById('td1AggiungiMc'+indice).cloneNode();
	td1AggiungiMc.setAttribute("id",'td1AggiungiMc'+mcSelezionati);
	var td2AggiungiMc = document.getElementById('td2AggiungiMc'+indice).cloneNode();
	td2AggiungiMc.setAttribute("id",'td2AggiungiMc'+mcSelezionati);
	var mc = document.getElementById('microchip'+indice).cloneNode();
	mc.setAttribute("value","");
	mc.setAttribute("name","microchip"+mcSelezionati);
	mc.setAttribute("id","microchip"+mcSelezionati);
	var label = document.getElementById('label'+indice).cloneNode();
	label.innerHTML=mcSelezionati+".";
	label.setAttribute("name","label"+mcSelezionati);
	label.setAttribute("id","label"+mcSelezionati);
	
	td1AggiungiMc.appendChild(label);
	if(mcSelezionati<10)
	td1AggiungiMc.innerHTML += '&nbsp;';
	td1AggiungiMc.appendChild(mc);

	trAggiungiMc.appendChild(td1AggiungiMc);
	trAggiungiMc.appendChild(td2AggiungiMc);
	
	tableAggiungiMc.appendChild(trAggiungiMc);
	document.getElementById("microchip"+mcSelezionati).value="";

}



function checkForm()
{
	var indice = 1;
	var mcInseriti = 0;
	var messaggio = "";
	var lungMcErrore = false;
	var formatoMcErrore = false;
	var esisteMcErrore = false;
	
	while(indice<=mcSelezionati)
	{
		if(document.getElementById('microchip'+indice).value!="")
		{
			if(document.getElementById('microchip'+indice).value.length!=15 && !lungMcErrore)
			{
				lungMcErrore = true;
				messaggio = messaggio + "La lunghezza dei microchip deve essere pari a 15\n";
			}
			if(Controlla(document.getElementById('microchip'+indice).value) && !formatoMcErrore)
			{
				formatoMcErrore = true;
				messaggio = messaggio + "Il formato dei microchip deve essere alfanumerico\n";
			}
			
			
			if(!esisteMcErrore)
			{
			Test.esisteMc(document.getElementById('microchip'+indice).value, 
					{
							callback:function(msg) 
							{ 
								if(msg)
								{
									messaggio = messaggio + "Microchip " + document.getElementById('microchip'+indice).value + " presente in Bdu \n";
									esisteMcErrore = true;
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
			
			
			
			
			mcInseriti++;
		}
		indice++;
	}
	
	if(mcInseriti<2)
	{
		messaggio = messaggio + "Digitare almeno due microchip\n";
	}
	
	if(messaggio!="")
		alert(messaggio);
	else
		document.form.submit();
}





function esisteMc(mc)
{
	Test.esisteMc(mc,
											{
													callback:function(msg) 
													{ 
														if(msg!=null && msg!='')
														{
															alert(msg);
															return false;
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
													        window.location.href=window.location.href;
													    }
													    else
													        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
													 }
								 			});
}





function Controlla(stringa) 
{
	var myregexp = /^[a-zA-Z0-9]+$/;
	if (myregexp.test(stringa) == false)
	{
		return true;
	}
	else
	{
		return false;
	}
}




