/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

try{
	
var domain = window.location.hostname;
domain = domain.substring(domain.indexOf('.')+1);
//alert(domain);
document.domain = domain;
//alert(document.domain);
}catch (err){
	//alert('Errore setting domain !!');
	
}



//alert('Domain BDU d:  ' +document.domain);

try{
	//alert(idEvento);
sendIdEventoToVam2(idEvento);
}catch (e) {
	//alert('Attenzione, si e\' verificato un errore nella comunicazione BDU -> VAM, contatta cortesemente l\'Help Desk. Dettagli errore: ' +e);
}



function sendIdEventoToVam2(idEvento){ //Funzione con gestione cambiamento dominio VAM BDU
	//alert(document.domain);
	window.top.continueVam(idEvento);
}