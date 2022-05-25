/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function checkform(form) 
{
	if (document.getElementById('dataRichiesta').value == '') {
		alert("Inserire la data della richiesta");	
		document.getElementById('dataRichiesta').focus();
		return false;
	}	
	
	if(!controllaDataAnnoCorrente(document.getElementById('dataRichiesta'), 'Data richiesta'))
	{
		return false;
	}
	
	if(document.getElementById("idTipoPrelievo").value=="")
	{
		alert("Inserire Tipo prelievo");
		document.getElementById('idTipoPrelievo').focus();
		return false;
	}
	
	if(document.getElementById("idDiagnosi").value=="")
	{
		alert("Inserire Diagnosi");
		document.getElementById('idDiagnosi').focus();
		return false;
	}
	
	form.submit();

	return true;

}


function mostraAltro()
{
	if(document.getElementById("idTipoPrelievo").value==4)
	{
		document.getElementById("tipoPrelievoAltroTd1").innerHTML='Specificare altro';
		document.getElementById("tipoPrelievoAltro").style.display='block';
	}
	else
	{
		document.getElementById("tipoPrelievoAltroTd1").innerHTML='';
		document.getElementById("tipoPrelievoAltro").style.display='none';
	}
}