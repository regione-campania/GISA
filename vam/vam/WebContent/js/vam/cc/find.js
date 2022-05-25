/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function checkform(form) 
{
	if(form.tipoFind.value == 'cc' && form.numeroCC.value == '')
	{
		alert("Inserire Numero Cartella Clinica");	
		form.numeroCC.focus();
		return false;
	}
	
	if(form.tipoFind.value == 'mc' && form.numeroMC.value == '')
	{
		alert("Inserire Numero identificativo animale");	
		form.numeroMC.focus();
		return false;		
	}
	
	attendere();
	return true;
	
	
}