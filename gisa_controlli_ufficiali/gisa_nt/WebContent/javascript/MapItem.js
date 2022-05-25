/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function MapItem(/*denominazione,latitudine,longitudine*/)
{
	var index =0;
	var marker 			= null;
	var infowindow 		= null;
	var icona 			= null;
	var lat		 		= null;
	var long			= null;
	var LatLng 			= null;
	//ATTRIBUTI VARIABILI IN BASE AL TIPO DI DATO
	var listaAttributi 	= {};
	//LISTA DEI NOMI DEGLI ATTRIBUTI
	var nomiCampi 		= [];
	

	//
	this.setAttributo = function (key,value) {
		listaAttributi[key] = value;
		nomiCampi.push(key);	
		//alert("Key: "+key+" value: "+listaAttributi[key]);
	};
	//
	this.getAttributo = function (key) {
		return listaAttributi[key]; 
	};
	//
	this.getNomiCampi = function() {
		return nomiCampi;
	}
	
	//INIZIALIZZA IL MARKER
	this.initMarker = function() {
		
		LatLng = new google.maps.LatLng(listaAttributi["LATITUDINE"]  ,listaAttributi["LONGITUDINE"] );
		if (LatLng != null)
		{
			//alert (listaAttributi["DENOMINAZIONE"]);
			this.marker = new google.maps.Marker({
		     	 position:LatLng,
		         icon: this.icona,
		         title:listaAttributi["DENOMINAZIONE"]
			});
			//alert("Marker creato "+this.marker.title);
			return true;
			
		}
		else
		{
			alert ("Errore");
			return false;
		}
	};
	//
	this.initInfoWindow = function()
	{
		
		var htmlContent = 	'<div id="content">'+
						    '<div id="siteNotice">'+
						    '</div>'+
						    '<h2 id="firstHeading" class="firstHeading">'+listaAttributi["DENOMINAZIONE"]+'</h2>'+
						    '<div id="bodyContent"><p>';
		
		for (var i=0; i<nomiCampi.length; i++) {
			var key = nomiCampi[i];
			var value = this.getAttributo(key);
			htmlContent += '<b>'+key+':</b> '+value+'<br/>';
   	 	}
		htmlContent += 	'</p>'+
					    '</div>'+
					    '</div>';
		
		this.infowindow = new google.maps.InfoWindow({
			content: htmlContent
		});

		
		
		
	};
	
}