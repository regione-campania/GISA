/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function checkform(form) {
		
	if (form.dataRichiesta.value == '') {
		alert("Inserire la data della richiesta");
		form.dataRichiesta.focus();
		return false;
	}	
	
	if(!controllaDataAnnoCorrente(form.dataRichiesta, 'Data'))
	{
		return false;
	}
	
	var i=1;
	
	while(document.getElementById('sangue' + i) != null) 
	{
		
		if (!testFloating(document.getElementById('sangue' + i).value) && document.getElementById('sangue' + i).value != '') {
			alert ("Non è possibile inserire valori non numerici");					
			return false; 
		}
		
		i++;
	}		
		
	attendere();
	return true;
	
}

function testFloating(str) {    
    return /^[+]?[0-9]+([\.,][0-9]+)?$/.test(str);
}