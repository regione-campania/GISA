<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>

<div align="left" style="display:none">
	<img src="images/tree0.gif" border="0" align="absmiddle" height="16" width="16" />
	<a href="javascript:popLookupSelectorCustomZone('15','<%=OrgDetails.getSiteId()%>');"><dhv:label name="">Seleziona Operatore</dhv:label></a>
</div>
<body onload="javascript:document.getElementById('dim').value=0;">

<script>
function checkIdentificativo(campo){
	loadModalWindow();
	campo.style.borderColor =""; 
	var mc = campo.value.trim();
	campo.value = mc;
	if (mc == ""){
		loadModalWindowUnlock();
		return true;
	}
	
	for (var i = 0; i < 12; i++){
		var id = "identificativo_"+i;
		if (id != campo.id){
			if (document.getElementById(id).value == mc){
		   		alert('Attenzione. Il microchip/identificativo '+mc+' risulta inserito alla posizione '+(i+1)+'.');
		   		campo.value = "";
		   	 	campo.style.borderColor = "red";
				loadModalWindowUnlock();
		   	 	return false;
			}
		} 
	}
	
	PopolaCombo.getEsistenzaBduVam(mc, campo.id,{callback:checkEsistenzaBduVamCallBack,async:false});
   }
   
   function checkEsistenzaBduVamCallBack(val){

   	var idcampo = val[1];
   	var value = val[0];

   	if (value=='f'){
   		alert('Attenzione. Il microchip/identificativo '+document.getElementById(idcampo).value+' risulta NON presente in BDU e/o VAM.');
   		document.getElementById(idcampo).value = "";
   	 	document.getElementById(idcampo).style.borderColor = "red"; 
   	}
	loadModalWindowUnlock();

   }
</script>


<table cellpadding="7" cellspacing="0" width="70%" class="details" id="tblClone_ps" style="display:none">
  <tr>
    <th colspan="6">
      <strong><dhv:label name="">Lista Zone controllate</dhv:label></strong>
    </th>
  </tr>
  <tr>
	<th >Denominazione</th>
    <th>Comune</th>
	<th>Note</th>
    <th>Azione</th>
	</tr>  
	<tr style="display: none">
	 	<td><p></p>
	 		<input type="hidden" name="org_id_op" id="org_id_op" value="" />
	 	</td>
	 	
	 	<td><p></p>
	 	</td>
	 	
	 	<td><p></p>
	 	</td>
	 
	 	<td>
	 	<p><a href="javascript:eliminaOperatore()" id="elimina">Elimina</a></p>
	 	</td>
	 	
	 </tr>
</table>	
<input type="hidden" name="dim" id="dim" value ="0">

<br/>
<table cellpadding="7" cellspacing="0" width="70%" class="details" id="">
  <tr><th>Lista Animali Rilevati</th></tr>
  <tr><th>Microchip / Identificativo</th></tr>  
  <tr><td>
  <% for (int i = 0; i<12; i++) { %>  <input type="text" id="identificativo_<%=i%>" name="identificativo_<%=i%>" onChange="checkIdentificativo(this)" placeholder="identificativo n. <%=i+1%>"/> &nbsp;&nbsp;&nbsp;  <%if ((i+1)%4==0) { %> <br/><br/><%}%> <%}%>
  </td></tr>
  </table>
	
		
</body>
  