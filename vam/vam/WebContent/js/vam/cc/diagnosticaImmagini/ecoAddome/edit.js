/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function toggleGroup( targetId ){ 
	  if (document.getElementById){ 
	        target = document.getElementById( targetId ); 
	           if (target.style.display == "none"){ 
	              target.style.display = ""; 
	           } else { 
	              target.style.display = "none"; 
	           } 
	     } 
	} 


function hiddenDiv( targetId){ 
	  if (document.getElementById){ 
	        target = document.getElementById( targetId ); 
	        target.style.display = "none"; 
	     } 
}

function hiddenDiv( targetId1 , targetId2 ){ 
	  if (document.getElementById){ 
	        target = document.getElementById( targetId1 ); 
	        if(target!=null)
	        	target.style.display = "none"; 
	        target = document.getElementById( targetId2 ); 
	        if(target!=null)
	        	target.style.display = "none";
	     } 
}

function checkform(form) 
{
	if (form.dataRichiesta.value == '') {
		alert("Inserire la data della richiesta");	
		form.dataRichiesta.focus();
		return false;
	}	
	
	if(!(form.gravidanza1.value=='' && form.gravidanza2.value=='' && form.gravidanza3.value=='') && !(form.gravidanza1.value!='' && form.gravidanza2.value!='' && form.gravidanza3.value!='')) 
	{
		alert("Selezionare tutti i valori della dimensione camera ovulare intrauterina");	
		return false;
	}
	form.submit();
}