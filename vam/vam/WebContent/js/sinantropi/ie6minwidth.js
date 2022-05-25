/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
** JS min-width property for internet explorer 6 & internet explorer 5.5
** By Nemesis Design http://nemesisdesign.altervista.org
*/

minWidth = function(id, width){
	docWidth = document.body.clientWidth;
    screenWidth = window.screen.width;
    if(docWidth <= width || screenWidth <= width){
        document.getElementById(id).style.width = width+"px";
    }
    else if(docWidth > 1000){
        document.getElementById(id).style.width = "100%";
    }
}

if(navigator.userAgent.indexOf("MSIE 6.0") != -1 || navigator.userAgent.indexOf("MSIE 5.5") != -1){
    window.onresize = function(){
        minWidth("container", "1000");
    }
    window.onload = window.onresize;
}
