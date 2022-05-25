/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function checkform() 
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
	
	
	if (document.getElementById('ritmo').value == '') {
		alert("Selezionare un ritmo");	
		document.getElementById('ritmo').focus();
		return false;
	}
	
	if((document.getElementById('intervalloQT').value=="" && document.getElementById('intervalloRR').value!=""))
	{
		alert("Inserire intervallo QT");
		document.getElementById('intervalloQT').focus();
		return false;
	}
	
	if((document.getElementById('intervalloQT').value!="" && document.getElementById('intervalloRR').value==""))
	{
		alert("Inserire intervallo RR");
		document.getElementById('intervalloRR').focus();
		return false;
	}
	
	document.getElementById('form').submit();

	return true;

}


function disabilitaAnomalie()
{
	var disabled = false;
	if(document.getElementById("diagnosi").checked)
	{
		disabled = true;
	}
	
	var inputs = document.getElementsByTagName("input");
	for(var i=0;i<inputs.length;i++)
	{
		var input = inputs[i];
		if(input.getAttribute("type")=="checkbox" && input.getAttribute("name")!="diagnosi")
		{
			document.getElementById(input.getAttribute("id")).disabled = disabled;
			document.getElementById(input.getAttribute("id")).checked  = false;
		}
	}
}

function calcolaQTCorretto()
{
	var qt = document.getElementById('intervalloQT').value;
	var rr = document.getElementById('intervalloRR').value;
	if(  qt!="" && rr!="" && (parseFloat(rr)>0 || parseFloat(rr)<0) )
	{
		document.getElementById('QTCorretto').value = qt/Math.pow(rr,1/3);
	}
	else
	{
		document.getElementById('QTCorretto').value="";
	}
}