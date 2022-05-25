<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
 <script type="text/javascript">
 function getCoordinate(addr, citt, prov){
	 var geocoder = new google.maps.Geocoder();
	 var address = '';
	 
	 if (citt=='')
		 {alert("Inserire il nome corretto della citta");}
	 else
		 {
	 if( addr==null || addr=='')
	 {address = citt + ','+prov+',italia';
	 }else
	 
	 {address = addr+','+citt+','+prov+',italia';}
	 
	 geocoder.geocode( { 'address': address}, function(results, status) {
		 if (status == google.maps.GeocoderStatus.OK) {
			 
			 alert(JSON.stringify(results));
			 
			 alert(results.length);
			 
			 document.getElementById("test").innerHTML=JSON.stringify(results);
			 
			 
			 var trovato = false ;
			 for(i=0;i<results.length;i++)
			 if (results[i]['address_components'][1]['short_name'].toLowerCase() == document.getElementById("geo_citt").value.toLowerCase() )
				 {
			 var latitude = results[i].geometry.location.lng();
			 var longitude = results[i].geometry.location.lat();
			 document.getElementById('geo_lat').value=latitude;
			 document.getElementById('geo_lon').value=longitude;
			 trovato = true ;
			 break ;
				 }
			
			
			 if (trovato==false)
				 alert("Indirizzo Non presente Nel comune di "+document.getElementById("geo_citt").value + " Indirizzo Trovato presso : "+results[0]['address_components'][1]['short_name']);
			 }
		 
		 
		 });
		 }
	 }</script>
	 
	 
	 <script type="text/javascript">function getVisualizzaSuMappa(lon,lat){ if (lon=='' || lat == '') { alert('Selezionare prima una coppia di coordinate!'); return false;}window.open('https://www.google.it/maps/@'+lon+','+lat+',14z');}</script><table class="details" width="100%" cellpadding="0" cellspacing="0"><tr class="globalItem" ><th style="background-color:#405c81;color:#fff7bd;" class="globalItem" colspan="2">Ricerca Coordinate</th></tr><tr><td>indirizzo</td><td> <input type="text" name="geo_addr" id="geo_addr"/></tr><tr><td>


citt&agrave;</td><td> <input type="text" name="geo_citt" id="geo_citt"/></tr><tr><td>
provincia</td><td> <input type="text" name="geo_prov" id="geo_prov"/></tr><tr><td>
cap</td><td> <input type="text" name="geo_cap" id="geo_cap"/></tr><tr><td>
lat</td><td> <input type="text" name="geo_lon" id="geo_lon"/></tr><tr><td>
lon</td><td> <input type="text" name="geo_lat" id="geo_lat"/></tr><tr><td colspan="2">
<input type="button" onclick="javascript:getCoordinate( document.getElementById('geo_addr').value,document.getElementById('geo_citt').value,document.getElementById('geo_prov').value)" value="Cerca" /><input type="button" onclick="javascript:getVisualizzaSuMappa( document.getElementById('geo_lon').value,document.getElementById('geo_lat').value)" value="Visualizza su mappa" /><input type="hidden" name="coord_type" id="coord_type" value="0" /></td></tr></table>
    </td>
    
    
    <div id = "test"></div>
    
    
    
    
    
    
    
    
    
    
    
    
    
    