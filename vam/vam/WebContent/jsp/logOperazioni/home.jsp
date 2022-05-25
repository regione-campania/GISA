<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<script>
function checkform(form){
	var username = form.username.value;
	var date_start = form.dateStart.value;
	var date_end = form.dateEnd.value;
	var msg = '';
	var formTest=true;
		
	if (username=="" && date_start=="" && date_end==""){
		formTest=false;	
		msg = 'Nessun parametro di ricerca impostato';	
	}
	
	if (date_start!="" && date_end!=""){
		var date_s = date_start.split('/');
		var date_e = date_end.split('/');
		var DS = new Date(date_s[2],date_s[1]-1,date_s[0]);
		var DE = new Date(date_e[2],date_e[1]-1,date_e[0]);
		
		if(DS > DE){
			formTest=false;
			msg = 'Data Fine minore di Data Inizio';
		} 
	}
	
	if (formTest==false){
		alert(msg);
	} 
	return formTest;
}
</script>


<form action="LogOperazioniList.us" name="form" method="post" id="form" class="marginezero" onsubmit="javascript:return checkform(this);">
<h4 class="titolopagina">
     		Ricerca Operazione Utente
</h4>  

<table class="tabella">
	<tr>
		<td>Username</td><td><input type="text" name="username" id="username" value=""/></td>
	</tr>
	<tr>
		<td>Data</td><td>DAL :   
    			 <input type="text" id="dateStart" name="dateStart" readonly="readonly" style="width:246px;" value=""/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dateStart",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      	// format of the input field
       						button         :    "id_img_1",  		// trigger for the calendar (button ID)
       						// align          :    "Tl",           	// alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>
				<br>AL : <input type="text" id="dateEnd" name="dateEnd" readonly="readonly" style="width:246px;" value=""/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_2" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dateEnd",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      	// format of the input field
       						button         :    "id_img_2",  		// trigger for the calendar (button ID)
       						// align          :    "Tl",           	// alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script></td>
	</tr>	
</table>
<input type="submit" value="Cerca" />
</form>