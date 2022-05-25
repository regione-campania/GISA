/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
var fieldLat ;
var fieldLong ;
var fieldErr ;



function setPositionField(fieldHiddenLatitudine,fieldHiddenLongitudine,fieldHiddenError)
{
if (fieldHiddenLatitudine!=null)
{
	fieldHiddenLatitudine.value = '';
	fieldLat = fieldHiddenLatitudine ;

}
if (fieldHiddenLongitudine!=null)
{
	fieldHiddenLongitudine.value = '';
	fieldLong = fieldHiddenLongitudine ;
}
if (fieldHiddenError!=null)
{
	fieldHiddenError.value = '';
	fieldErr = fieldHiddenError ;
}
navigator.geolocation.getCurrentPosition(
		    gotPosition,
		    errorGettingPosition,
		    {'enableHighAccuracy':true,'timeout':10000,'maximumAge':0}
		);
}
function gotPosition(pos)  {
	    /*var outputStr =
	        "latitude:"+ pos.coords.latitude +"\n"+
	        "longitude:"+ pos.coords.longitude +"\n"+
	        "accuracy:"+ pos.coords.accuracy +"\n"+
	
	        "altitude:"+ pos.coords.altitude +"\n"+
	        "altitudeAccuracy:"+ pos.coords.altitudeAccuracy +"\n"+
	        "heading:"+ pos.coords.heading +"\n"+
	        "speed:"+ pos.coords.speed +"";*/
	    	fieldLat.value = pos.coords.latitude ;
	    	fieldLong.value = pos.coords.longitude ;
	        //alert(outputStr);
	}

function errorGettingPosition(err) {
	  
	    if(err.code == 1) {
	    	fieldErr.value='Lutente non ha autorizzato la geolocalizzazione';
	    	
	    } else if(err.code == 2) {
	    	fieldErr.value="Posizione non disponibile";
	    } else if(err.code == 3) {
	    	fieldErr.value="Timeout";
	    } else {
	    	fieldErr.value="ERRORE:" + err.message;
	    }
	}
