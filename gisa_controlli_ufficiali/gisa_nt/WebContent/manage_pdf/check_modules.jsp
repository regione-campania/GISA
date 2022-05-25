<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>
function check(){
	var rad_val = "";
	for (var i=0; i < document.printmodules.selectMod.length; i++)
	{
	   if (document.printmodules.selectMod[i].checked)
	      {
	      		rad_val = document.printmodules.selectMod[i].value;
	      }
	}
	
	if(rad_val == "")	
	{
		alert("Controlla di aver selezionato un modulo.");
	
	}

	/*if(rad_val == 1)	
	{
		alert("Modulo non disponibile per questo Operatore.");
	
	}*/
	else
	{
		document.printmodules.submit();
		//window.location.href="Accounts.do?command=Details&orgId="+document.printmodules.orgId.value;
		//window.location.href="Accounts.do?command=Details&orgId="+document.printmodules.orgId.value+"&selectMod="+rad_val;
		//window.close();
	}

}
</script>
<%
String orgId 	= (String)request.getAttribute("orgId");
String ticketId 	= (String)request.getAttribute("ticketId");
String idCU = (String)request.getAttribute("idCU");

%>

<form method="post"  name = "printmodules" action="ManagePdfModules.do?command=PrintBarcodeNumber">
<input type = "hidden" name = "orgId" value = "<%=orgId %>"> 
<input type = "hidden" name = "ticketId" value = "<%=ticketId %>">
<input type = "hidden" name = "idCU" value = "<%=idCU %>">

<table cellpadding="4" cellspacing="0" width="100%" class="details">
<tr>
	<th colspan="2"><strong>Selezionare il modulo di interesse</strong></th>
</tr>
<tr class="containerBody">
 	<td nowrap class="formLabel"></td>
	<td>
		<input type="radio" name="selectMod" value="1" disabled="disabled">Mod 1 Verbale campione molluschi in produzione primaria<br>
		<input type="radio" name="selectMod" value="2">Mod 2 Verbale campione battereologico<br>
		<input type="radio" name="selectMod" value="3">Mod 3 Verbale campione chimico<br>
		<input type="radio" name="selectMod" value="6" disabled="disabled">Mod 6 Verbale prelievo campioni superficie ambientale<br>
		<input type="radio" name="selectMod" value="10" disabled="disabled">Mod 10 Verbale prelievo campioni superficie di carcasse<br>
		<input type="hidden" id="tipoAction" name="tipoAction" value="modulo" />
		
	</td>
</tr>
</table>
<br>
	<div align="center">
		<input type="button" value ="Stampa modulo" onclick="check();">	
		<input type="button" value ="Chiudi popup" onclick="window.close();">			
	</div>
</form>

