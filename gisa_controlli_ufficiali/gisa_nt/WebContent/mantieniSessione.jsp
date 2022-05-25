<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>


<script>
var varTimeout;

function checkKeepAlive(){
	//SE C'E' UN TIMER IN CORSO PER UN'ALTRA INTERAZIONE, LO AZZERO
	if (varTimeout!=null)
		clearTimeout(varTimeout);
	//LANCIO UN TIMER PER KEEPALIVE TRA 3 SECONDI
	varTimeout = setTimeout(keepAlive, 3000);
}

function keepAlive(){
	//CHIAMO UNA QUERY PER TENERE ATTIVA LA SESSIONE
	//PopolaCombo.keepAlive(true,{callback:keepAliveCallback,async:false }) ;
	
	//CHIAMO AJAX PER TENERE ATTIVA LA SESSIONE
	$.ajax({
        type: "POST",
        url: "welcome.jsp",
        contentType: "application/json",
        async: false,
        success: function (data) {
        }
    });
}

function keepAliveCallback(value){
	//POPOLO CAMPI DI SERVIZIO
// 	   document.getElementById("contatoreMantieni").value=parseInt((Math.random() * 10000), 10);
// 	   document.getElementById("contatoreInterazioni").value = parseInt( document.getElementById("contatoreInterazioni").value)+1;
}
</script>

<script>
$(document)
  .on('click', '*', function (evt) {
  // evt.stopImmediatePropagation();
   checkKeepAlive();
   });
  
$(document)
  .on('keypress', '*', function (evt) {
  //evt.stopImmediatePropagation();
  checkKeepAlive();
  });
  </script>
  
<!--   Rand <input type="text" readonly id="contatoreMantieni" name="contatoreMantieni" value="0" size="5"/> -->
<!--   N.Int. <input type="text" readonly id="contatoreInterazioni" name="contatoreInterazioni" value="0" size="3"/> -->
  
  