/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function registrazioneInserita(microchip, specie, idTipoRegBdrDaInserire)
{
	var toReturn = true;
	TestBdr.registrazioneInserita(microchip, specie, idTipoRegBdrDaInserire,
											{
													callback:function(inserita) 
													{ 
														if(!inserita)
														{
															alert("La registrazione non � stata effettuata: procedere all'inserimento.");
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
  return toReturn;
	
}