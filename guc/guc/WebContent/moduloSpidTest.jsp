<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<style>
body {
background-color : #7f7f7f;
} 
div {
background-color : white;
width: 600px;
  padding-top: 50px;
  padding-right: 30px;
  padding-bottom: 50px;
  padding-left: 80px;
}

</style>



<script>
function randomCf(){

	var lettere = "QWERTYUIOPASDFGHJKLZXCVBNM";
	var numeri = "1234567890";
	
	var cf = lettere.charAt(Math. floor(Math. random() * lettere.length)) + lettere.charAt(Math. floor(Math. random() * lettere.length)) + lettere.charAt(Math. floor(Math. random() * lettere.length))+ lettere.charAt(Math. floor(Math. random() * lettere.length))+ lettere.charAt(Math. floor(Math. random() * lettere.length))+ lettere.charAt(Math. floor(Math. random() * lettere.length)) + 	numeri.charAt(Math. floor(Math. random() * numeri.length)) + numeri.charAt(Math. floor(Math. random() * numeri.length)) + 	lettere.charAt(Math. floor(Math. random() * lettere.length)) + 	numeri.charAt(Math. floor(Math. random() * numeri.length)) + numeri.charAt(Math. floor(Math. random() * numeri.length)) + 	lettere.charAt(Math. floor(Math. random() * lettere.length)) + 	numeri.charAt(Math. floor(Math. random() * numeri.length)) + numeri.charAt(Math. floor(Math. random() * numeri.length)) + numeri.charAt(Math. floor(Math. random() * numeri.length)) + 	lettere.charAt(Math. floor(Math. random() * lettere.length));
	document.getElementById("cf").value = cf;
}

function setForm(form, url){
	form.action=url;
}
</script>

<center>


<div>


<label style="font-size: 40px; width: 200px;">MODULO SPID TEST</label>
 
<br/><br/>

<br/><br/>

<form action ="http://172.16.0.41:8080/moduloSpid/index.php">

<input type="hidden" id="admin" name="admin" value="admin"/>
<input type="hidden" id="password" name="password" value="AzuleyaAlessia"/>
<input type="text" id="cf" name="cf" value="" maxlength="16" style="background:lightgray; color: black; font-size: 40px; width: 400px; text-align:center"/>

<br/><br/>

<select onChange="this.form.action=this.value" style="background:lightgray; color: black; font-size: 20px; width: 400px; text-align:center">
<option value="http://172.16.0.41:8080/moduloSpid/index.php">SVILUPPO</option>
<option value="http://wwwcol.gisacampania.it/moduloSpid/index.php">COLLAUDO</option>
</select>

<br/><br/>

<input type="submit" style="background:yellow; color: black; font-size: 50px; width: 100px;" value="VAI "/>

<br/><br/>

<input type="button" style="background:blue; color: white; font-size: 10px; width: 100px;" value="RICARICA" onClick="location.reload()"/>
 
</form>

</div>

</center>

<script>
randomCf();
</script>