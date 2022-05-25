<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Hashtable"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="java.util.HashMap"%> 
<%@page import="java.util.Date"%>
 
<script type="text/javascript" src="javascript/markerclusterer.js"></script> 
<script src="javascript/MapItem.js"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script> 
<script type="text/javascript">
  var markerClusterer = null;
    var mcOptions = {};


var map ;
    function inizialize() {

    var poly;

    var layers = document.getElementsByTagName('input');
    var mapOptions = {
      center: new google.maps.LatLng(40.750956,14.66775),
      zoom: 10,
      minZoom:9,
      mapTypeId: google.maps.MapTypeId.ROADMAP 
    };
    map = new google.maps.Map(document.getElementById("mappa"),mapOptions);

    // The allowed region which the whole map must be within
    
    var southWest = new google.maps.LatLng(40.275055, 14.040527);
    var northEast = new google.maps.LatLng(41.241021,15.336227);
    var allowedBounds = new google.maps.LatLngBounds(southWest, northEast);

    // Add listeners to trigger checkBounds(). bounds_changed deals with zoom changes.
    google.maps.event.addListener(map, "center_changed", function() {checkBounds(); });

    // If the map bounds are out of range, move it back
    function checkBounds() {
    	
    	 if (allowedBounds.contains(map.getCenter())) {
    	        // still within valid bounds, so save the last valid position
    	        lastValidCenter = map.getCenter();
                lastValidZoom = map.getZoom();

    	        return; 
    	    }

    	    // not valid anymore => return to last valid position
    	    map.panTo(lastValidCenter);
    	    map.setZoom(lastValidZoom);
    }

    layerRegioniOscurate = new google.maps.FusionTablesLayer({
    query: {select: "kml_4326", 
            from: 420419,
            where: "name_0 = 'Italy' AND name_1 NOT EQUAL TO 'Campania'"},
    options : {
    	suppressInfoWindows:true},
    	styles: [{
    		polygonOptions: {
    		  strokeWeight: "20",
    		  strokeColor: "#FF0000",
    		  strokeOpacity: "0.1",
    		  fillOpacity: "1",
    		  fillColor: "#C0C0C0"
    }
    }]});

    layerRegioniOscurate.setMap(map);

    markerClusterer  = new MarkerClusterer(map,[]/*,mcOptions*/);

    }
  
 
function createMarker(lat,lon,asl,nome,cognome,username,note){
	
	if(lat!=0 )
		{



content ='<table>' ;
	             
	            

if (note == '')
	note ='Informazioni geolocalizzazione recuperate da ultimo accesso';
content='<tr><td>ASL</td><td>&nbsp;'+asl +'</td></tr><br>';
content+='<tr><td>NOME</td><td>&nbsp;'+nome +'</td></tr><br>';
content+='<tr><td>COGNOME</td><td>&nbsp;'+cognome+'</td></tr><br>';
content+='<tr><td>USERNAME</td><td>&nbsp;'+username+'</td></tr><br>';
content+='<tr><td>NOTE</td><td>&nbsp;'+note+'</td></tr><br>';
content+='</table>';

	/*var marker = new google.maps.Marker({ position: latlng,
		                                      map: map, 
		                                      title: '' });
	
	google.maps.event.addListener(marker, 'click', function(e) {
	    infowindow.setContent(content);
	    infowindow.open(map, this);
	  });*/
	   var myLatLng1 = new google.maps.LatLng(lat,lon);

	          var infowindow1 = new google.maps.InfoWindow({
	              content: content,
              		maxWidth: 430,
	              maxHeight: 600,
	         
	          });
	         
	          url = '' ;
	          if (asl == 'Avellino')
	        	  url = 'images/marker/green.png';
	          else
	        	  if (asl == 'Benevento')
		        	  url = 'images/marker/yellow.png';
	        	  else
	        		  if (asl == 'Caserta')
	    	        	  url = 'images/marker/purple.png';
	        		  else
	        			  if (asl == 'Napoli 1 Centro')
		    	        	  url = 'images/marker/celeste.png';
		    	        	  else
		    	        		  if (asl == 'Napoli 2 Nord')
		    	    	        	  url = 'images/marker/brown.png';
		    	        		  else
		    	        			  if (asl == 'Napoli 3 Sud')
			    	    	        	  url = 'images/marker/blue.png';
		    	        			  else
		    	        				  if (asl == 'Salerno')
				    	    	        	  url = 'images/marker/red.png';
		    	        				  else
		    	        					  
					    	    	        	  url = 'images/marker/grey.png';
	          
	          var image = {
	        		  url: url,
	        		  size: new google.maps.Size(75, 75),
	        		  origin: new google.maps.Point(0, 0),
	        		  anchor: new google.maps.Point(17, 34),
	        		  scaledSize: new google.maps.Size(25, 25)
	        		};
	          
	          
	          var marker1 = new google.maps.Marker({
	              position: myLatLng1,
	              map: map,
	              icon: image
	          });
	           
	          google.maps.event.addListener(marker1, 'click', function() {
	            infowindow1.open(map,marker1);
	          });
	
	
	
	
		}
	

}
</script>
 
<table width="100%" align = "center">
  <caption>Legenda</caption>
  <tr>
      <td><img src="images/marker/green.png"> </td>
  
    <th>AVELLINO</th>
  </tr>
 <tr>
      <td><img src="images/marker/yellow.png"> </td>
  
    <th>BENEVENTO</th>
  </tr>
  <tr>
      <td><img src="images/marker/purple.png"> </td>
  
    <th>CASERTA</th>
  </tr>
  <tr>
      <td><img src="images/marker/celeste.png"> </td>
  
    <th>NAPOLI 1 CENTRO</th>
  </tr>
  <tr>
      <td><img src="images/marker/brown.png"> </td>
  
    <th>NAPOLI 2 NORD</th>
  </tr>
  <tr>
      <td><img src="images/marker/blue.png"> </td>
  
    <th>NAPOLI 3 SUD</th>
  </tr>
  <tr>
      <td><img src="images/marker/red.png"> </td>
  
    <th>SALERNO</th>
  </tr>
  <tr>
      <td><img src="images/marker/grey.png"> </td>
  
    <th>ALTRO</th>
  </tr>
</table>


</table>
</caption> 


<div id="mappa" style="width:100%; height:100%"></div>
<script>
inizialize();
</script>

<% 
HashMap<String, HttpSession> utenti = null;

utenti = (HashMap<String, HttpSession>)request.getSession().getServletContext().getAttribute("utenti");



int aslUtente = 0;




for(String usernameString : utenti.keySet())
{
	out.println(usernameString);
	HttpSession sessione = utenti.get(usernameString);
	BUtente utenteLoggato = null;
	try
	{
	if(sessione!=null && sessione.getAttribute("utente")!=null)
	{



		utenteLoggato =  (BUtente)sessione.getAttribute("utente");
		int idAsl = 0;
		if(utenteLoggato.getClinica()!=null && utenteLoggato.getClinica().getLookupAsl()!=null)
		   idAsl = utenteLoggato.getClinica().getLookupAsl().getId();
		if(idAsl == 0 || idAsl == -1 )
		{
			aslUtente = -1;
		}
		else
		{
			aslUtente = idAsl;
		}

	
	double lat = utenteLoggato.getAccessPositionLat();
	double lon = utenteLoggato.getAccessPositionLon();
	System.out.println(lat+ "  " + lon);
	String username = utenteLoggato.getUsername();
	String nome = utenteLoggato.getNome();
	String cognome = utenteLoggato.getCognome();
	String asl = utenteLoggato.getClinica().getLookupAsl().getDescription();
	System.out.println(asl);
	String note = utenteLoggato.getAccessPositionErr();
	if(idAsl == 0 || idAsl == -1 || idAsl == 16 ){
		%>
		<script>
		createMarker(<%=lat%>,<%=lon%>,'','<%=nome%>','<%=cognome%>','<%=username%>','<%=note%>');
		</script>
		<% 
	}
	else{
		aslUtente = idAsl;
	%>
		<script>
			createMarker(<%=lat%>,<%=lon%>,'<%=asl%>','<%=nome%>','<%=cognome%>','<%=username%>','<%=note%>');
		</script>
	<%
	}
	
}
	}catch(Exception e){
		e.printStackTrace();
	}
}
%>

