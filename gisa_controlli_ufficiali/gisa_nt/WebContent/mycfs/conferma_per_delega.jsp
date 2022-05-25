<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>
$(function () {
    
	
	 
	 $( "#dialogDelega" ).dialog({
	    	autoOpen: true,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"ATTENZIONE! DELEGA PRESENTE",
	        width:850,
	        height:400,
	        draggable: false,
	        modal: true,
	       
	        show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	       
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	 
 
});        


</script>


<div id = "dialogDelega">
<center>
<p>
Attenzione: la gestione dei tuoi dati apistici risulta attualmente in delega. Scegli cosa fare:
</p>
<input type="button" class="aniceBigButton" style="height:50px !important; width:350px !important;" value="Procedi e annulla la delega" onClick="location.href='ApicolturaAttivita.do?command=RevocaDelegaApicoltore';"/>  <br/><br/>
 <input type="button" class="aniceBigButton" style="height:50px !important; width:350px !important;" value=" Esci dal sistema e mantieni attiva la delega" onClick="location.href='Login.do?command=Logout'"/>
</center>

</div>