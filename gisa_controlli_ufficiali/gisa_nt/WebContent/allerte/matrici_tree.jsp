<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script language="JavaScript">
function openTree(campoid1,campoid2,table,id,idPadre,livello,multiplo,divPath,idRiga)
{
	idPiano='-1';
	controllo = true ;
	if(document.getElementById('motivazione_campione')!=null  && document.getElementById('motivazione_campione').value=='2')
	{
		idPiano=document.getElementById('motivazione_piano_campione').value;
		if (idPiano=='-1')
		{
			alert('- Controllare di aver selezionato il piano come motivazione del campione');
			controllo = false ;
		}
		else
		{
			window.open('Tree.do?command=DettaglioTree&idPiano='+idPiano+'&multiplo='+multiplo+'&divPath='+divPath+'&idRiga='+idRiga+'&campoId1='+campoid1+'&campoId2='+campoid2+'&nomeTabella='+table+'&campoId='+id+'&campoPadre='+idPadre+'&campoDesc=nome&campoLivello=livello&campoEnabled=nuova_gestione&sel=true');

		}
	}
	else
	{
		window.open('Tree.do?command=DettaglioTree&multiplo='+multiplo+'&divPath='+divPath+'&idRiga='+idRiga+'&campoId1='+campoid1+'&campoId2='+campoid2+'&nomeTabella='+table+'&campoId='+id+'&campoPadre='+idPadre+'&campoDesc=nome&campoLivello=livello&campoEnabled=nuova_gestione&sel=true');
		

		}

}


</script>




 <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="">Matrice</dhv:label>
    </td>
   
    
    <td>
    <table class = "noborder"><tr><td>
    <input type = "hidden" name = "elementi1" id = "elementi1" value = "0">
    <input type = "hidden" name = "size1" id = "size1" value = "0">
    <tr id = "clonaM" style="">
    <td>
    <input type = "hidden" name = "idMatrice" id = "idMatrice" value = "-1">
    <input type = "hidden" name = "path" id = "path" onchange="mostracampiPnaaMangimi();">
    <div id = "divPath"></div>     
   </td>
   </tr>
   <tr>
   <td>
    <a href = "javascript:openTree('idMatrice','path','matrici','matrice_id','id_padre','livello','no','divPath','clonaM')">(Selezionare Matrice)</a><font color="red">*</font>
   <br> <br>
     <textarea rows="5" cols="30" name = "noteMatrici"></textarea></br>
   </td>
   </tr>
   </table>
    </td>
	</tr>
	
  

