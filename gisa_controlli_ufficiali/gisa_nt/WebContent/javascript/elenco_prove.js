/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function eliminaProva (idProva,orgId)
{
	if (confirm("Sei sicuro di voler eliminare questa prova? ")) 
	{
	    document.location = 'LaboratoriHACCP.do?command=DeleteProve&id='+idProva+'&orgId='+orgId;
	}
	
}

function aggiungiProva(orgId,form)
{

			document.forms[form].action='ElencoProveHACCP.do?command=Add&orgId='+orgId;
			document.forms[form].submit();
	
}

function modificaProva (idProva,orgId)
{
	
	window.open('ElencoProveHACCP.do?command=Modify&id='+idProva+'&orgId='+orgId+'&popup=true','modificaProve',
	'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
	
	
}
