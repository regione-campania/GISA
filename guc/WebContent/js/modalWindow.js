/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

/*  MATTEO CARELLA - modalWindow.js */
/*	locka lo schermo facendo apparire la finestra modale */

function loadModalWindow() {
		// controllo se la finestra modale � nascosta o meno
		var currentClass = jQuery('#modalWindow').attr('class'); 
				
		// se lo �, la attivo e disabilito il tasto f5 per il refresh della pagina
		if(currentClass=='unlocked')
		{
			jQuery('#modalWindow').removeClass('unlocked').addClass('locked');
			
			jQuery(document).bind('keydown', function(e){
				if(e.keyCode==116) 
					return false;
			});		
		}
		
	}


function loadModalWindowCustom(messaggio) {
	// controllo se la finestra modale � nascosta o meno
	document.getElementById('modalWindow').innerHTML="<P CLASS='wait'><img src=\"gestione_documenti/images/loading.gif\">"+messaggio+"</P>";
	var currentClass = jQuery('#modalWindow').attr('class'); 
			
	// se lo �, la attivo e disabilito il tasto f5 per il refresh della pagina
	if(currentClass=='unlocked')
	{
		jQuery('#modalWindow').removeClass('unlocked').addClass('locked');
		
		jQuery(document).bind('keydown', function(e){
			if(e.keyCode==116) 
				return false;
		});		
	}
	
}


function loadModalWindowUnlock() {
	// controllo se la finestra modale � nascosta o meno
	var currentClass = jQuery('#modalWindow').attr('class'); 
			
	// se lo �, la attivo e disabilito il tasto f5 per il refresh della pagina
	if(currentClass=='locked')
	{
		jQuery('#modalWindow').removeClass('locked').addClass('unlocked');
		
		jQuery(document).bind('keydown', function(e){
			if(e.keyCode==116) 
				return false;
		});		
	}
	
}


function loadModalWindowAutoHide(delay) {
	// controllo se la finestra modale � nascosta o meno
	var currentClass = jQuery('#modalWindow').attr('class'); 
			
	// se lo �, la attivo e disabilito il tasto f5 per il refresh della pagina
	if(currentClass=='unlocked')
	{
		jQuery('#modalWindow').removeClass('unlocked').addClass('locked');
		
		jQuery(document).bind('keydown', function(e){
			if(e.keyCode==116) 
				return false;
		});	
		
		setTimeout(function(){
			jQuery('#modalWindow').removeClass('locked').addClass('unlocked');
		},delay);

		
		
		
		
	}
	
}

