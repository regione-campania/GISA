/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

function codiceFISCALE(cfins)
   {
   var cf = cfins.toUpperCase();
   var cfReg = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
   if (!cfReg.test(cf)){
	  
	   //document.addAccount.validaCf.checked = '';
	   return false; 
   }
   var set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   var set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
   var setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   var setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
   var s = 0;
   for( i = 1; i <= 13; i += 2 )
      s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
   for( i = 0; i <= 14; i += 2 )
      s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
   if ( s%26 != cf.charCodeAt(15)-'A'.charCodeAt(0) ){
	   //document.addAccount.validaCf.checked = '';
	   return false;
   }
	//document.addAccount.validaCf.checked = '';
   return true;
   }
