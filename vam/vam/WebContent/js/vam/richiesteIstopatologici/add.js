/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function checkform(form)
{
	var ret = true;
	
	if(isFuturo(form.dataRichiesta.value))
	{
		alert("Non è possibile inserire una DATA RICHIESTA futura");
		ret = false;
	}
	
	if(!controllaDataAnnoCorrente(form.dataRichiesta, 'Data richiesta'))
	{
		ret = false;
	}
	
	//Flusso 170 si commenta
	/*if(form.dataSmaltimento.value!='' && confrontaDate(form.dataRichiesta.value,form.dataSmaltimento.value)>0)
	{
		alert("La data dell'esame istopatologico non può essere successiva alla data dello smaltimento("+form.dataSmaltimento.value+")");	
		form.dataRichiesta.focus();
		return false;
	}*/
	
	
	if( form.idTumoriPrecedenti.value == 2 )//tumori precedenti = si
	{
		if( ret && !isVoidOrIntPositivo(form.t.value,'T',form.t) )
		{
			ret = false;
		}
		
		if( ret  && !isVoidOrIntPositivo(form.n.value,'N',form.n) )
		{
			ret = false;
		}
		
		if( ret && !isVoidOrIntPositivo(form.m.value,'M',form.m) )
		{
			ret = false;
		}
	}
	

	if( ret && !isVoidOrIntPositivo(form.dimensione.value,'Dimensione',form.dimensione) )
	{
		ret = false;
	}

	if(ret && form.lookupAutopsiaSalaSettoria.value == '')
	{
		alert("Inserire il laboratorio di destinazione al quale inviare la richiesta");			
		ret = false;
	}
	
	if(ret && form.padreSedeLesione.value == '-1')
	{
		alert("Inserire la sede della lesione");			
		ret = false;
	}
	
	if( ret )
	{
		attendere();
	}
	
	return ret;
}


function checkformIzsm(form)
{
	var ret = true;
	
	if(form.dataEsito.value=='')
	{
		alert("Inserire la data esito");
		form.dataEsito.focus();
		ret= false;
	}else if(isFuturo(form.dataEsito.value))
	{
		alert("Non e' possibile inserire una data esito futura");
		ret= false;
	}
	
	if( ret )
	{
		attendere();
	}
	
	return ret;
}
