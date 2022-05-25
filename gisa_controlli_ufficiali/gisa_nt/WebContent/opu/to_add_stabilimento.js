/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function showHide(name){
	  var elems = document.getElementsByName(name);
			for(var i = 0; i < elems.length; i++) {
			    // set attribute to property value
			    if (elems[i].style.visibility=='hidden')
			    	elems[i].style.visibility='visible';
			    else
			    	elems[i].style.visibility='hidden';
			}
	
	
}


function goTo(link){
	
	if (link=='')
		alert('da implementare');
	else{
		loadModalWindow();
		window.location.href=link;
	}
	
	
}
