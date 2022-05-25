/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function gestisciStatoTrasportoAnimali(stato, orgId, altriParametri){
	
	//var url = 'TrasportoAnimali.do?command=CambiaStato&statoImpresa='+ stato + '&orgId=' + orgId + altriParametri;
	
	/*if(stato == 'Attivo'){
		window.location.href=url;
	}
	else if(stato == 'Sospeso'){
		window.location.href=url;
	}
	else if(stato == 'Revocato'){
		var ok = confirm("La revoca e' uno stato finale.\n" + 
				         "Se si decide di proseguire non sara' piu' possibile modificare i dati dell'impresa ne' il suo stato.\n" + 
				         "Continuare?");
		if(ok){
			window.location.href=url;
		}
	}
	else if(stato == 'Cessato'){
		var ok = confirm("La cessazione e' uno stato finale.\n" + 
		         "Se si decide di proseguire non sara' piu' possibile modificare i dati dell'impresa ne' il suo stato.\n" + 
		         "Continuare?");
		if(ok){
			window.location.href=url;
		}
	}
	else if(stato == 'Rinnovo'){
		//R.M: popup modale
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();
		//Set heigth and width to mask to fill up the whole screen
		$('#mask').css({'width':maskWidth,'height':maskHeight});
		$('#mask').fadeIn(1000);        
	    $('#mask').fadeTo("slow",0.8);        
		$('#mask').show();
		//Get the window height and width
		var winH = $(window).height();
		var winW = $(window).width();
		$('#dialog4').css('top',  winH/2-$('#dialog4').height()/2);
	    $('#dialog4').css('left', winW/2-$('#dialog4').width()/2);
		$('#dialog4').fadeIn(2000); 
		
		document.getElementById('stato_impresa').value= stato;
		//document.details.action='TroubleTicketsAllerte.do?command=ChiudiAllerta&chiusuraUfficio=0&id='+idAllerta;
		//document.details.submit();
		
	}*/
	
	 if(stato == 'Revocato'){
			var ok = confirm("La revoca e' uno stato finale.\n" + 
					         "Se si decide di proseguire non sara' piu' possibile modificare i dati dell'impresa ne' il suo stato.\n" + 
					         "Continuare?");
			if(ok){
				callPopupModale(stato);
			}
			
	}else if(stato == 'Cessato'){
			var ok = confirm("La cessazione e' uno stato finale.\n" + 
			         "Se si decide di proseguire non sara' piu' possibile modificare i dati dell'impresa ne' il suo stato.\n" + 
			         "Continuare?");
			if(ok){
				callPopupModale(stato);
			}
	 }else {
		 callPopupModale(stato);
	 }
		
	

}

function callPopupModale(stato) {
	
	
	//R.M: popup modale
	var maskHeight = $(document).height();
	var maskWidth = $(window).width();
	//Set heigth and width to mask to fill up the whole screen
	$('#mask').css({'width':maskWidth,'height':maskHeight});
	$('#mask').fadeIn(1000);        
    $('#mask').fadeTo("slow",0.8);        
	$('#mask').show();
	//Get the window height and width
	var winH = $(window).height();
	var winW = $(window).width();
	$('#dialog4').css('top',  winH/2-$('#dialog4').height()/2);
    $('#dialog4').css('left', winW/2-$('#dialog4').width()/2);
	$('#dialog4').fadeIn(2000); 
	
	document.getElementById('stato_impresa').value= stato;
}