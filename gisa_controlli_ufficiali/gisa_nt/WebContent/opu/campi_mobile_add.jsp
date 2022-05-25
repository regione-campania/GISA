<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>
function clearDatiAttivitaMobile(){
	 var inp = document.getElementById("datiAttivitaMobile").getElementsByTagName('input');
	   for(var i in inp){
	       if(inp[i].type == "text"){
	           inp[i].value='';
	       }
	   }
	   var inp2 = document.getElementById("datiAttivitaMobile").getElementsByTagName('select');
	   for(var i in inp2){
	           inp2[i].value='-1';
	   }
	   
	   var inp3 = document.getElementById("datiAttivitaMobile").getElementsByTagName('label');
	   for(var i in inp3){
	           inp3[i].innerHTML='';
	   }
}

</script>

<script>
var id_targa="";
function checkEsistenzaTarga(targa, id){
		id_targa = id;
        if (targa!="")
                SuapDwr.esisteTarga(targa,{callback:checkEsistenzaTargaCallBack,async:false});
        checkEsistenzaTargaNellaForm(targa);
      
}
function checkEsistenzaTargaCallBack(val){
        if (val==true){
               alert("Attenzione! La targa inserita è già presente nel sistema");
               document.getElementById("mobile_targa"+id_targa).value="";
        }
        return val;
}
function checkEsistenzaTargaNellaForm(targa){
	for (var i=0;i<10;i++){
		var t1 = document.getElementById("mobile_targa"+i);
		if (i!=id_targa && t1.value==targa){
			   alert("Attenzione! La targa inserita è già presente nella form");
	           document.getElementById("mobile_targa"+id_targa).value="";
		}
	}
}
</script>


<table id="datiAttivitaMobile" style="display: none" width="100%">

<tr><td colspan="7"><font color="red">ATTENZIONE! E' necessario indicare tutti i campi relativi alla singola targa affinchè i dati siano salvati correttamente.</font></td></tr>

<% for (int k=0; k<10;k++){ %>

<tr>

<td align="right"><%=k+1 %>.</td>

<td><b>Targa</b> </td> 
<td><input type="text" id="mobile_targa<%=k%>" name="mobile_targa<%=k%>" onChange="checkEsistenzaTarga(this.value, '<%=k %>')" /></td>

<td><b>Tipo autoveicolo</b> </td>
<td> <%=TipoMobili.getHtmlSelect("mobile_tipoautoveicolo"+k, -1) %></td>

<td><b>Carta di circolazione</b> </td>
<td>
<input type="text" id="mobile_carta<%=k%>" name="mobile_carta<%=k%>" readonly style="background:grey;" placeholder="Allegare"/>
 <label id="mobile_carta_link<%=k %>"></label>
 <a href="#" onClick="openUploadAllegatoMobile(document.getElementById('idStabilimento').value, '<%=k%>', document.getElementById('mobile_targa<%=k %>').value); return false"><img src="gestione_documenti/images/new_file_icon.png" width="20"/><b>ALLEGA FILE</b></a>
 
 </td>

</tr>


<%} %>

<tr><td colspan="7" align="center">
<input type="button" value="Pulisci tutto" onClick="clearDatiAttivitaMobile()"/>
</td></tr>
</table>