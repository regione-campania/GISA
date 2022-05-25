<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="QuesitiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

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
		alert("Controlla di aver selezionato una etichetta.");
		return false;
	}
	//Matrici
	else if(rad_val == 4)
	{
		matriceSize = document.getElementById('size1').value;
		if(matriceSize > 0){
			document.printmodules.submit();
		}
		else {
			alert("Controlla di aver selezionato la matrice.");
			return false;
		}
		
	}
	//Analiti
	else if(rad_val == 5)
	{
		size = document.getElementById('size').value;
		if(size > 0)
		{
			document.printmodules.submit();
		}
		else 
		{
			alert("Controlla di aver selezionato l'analita.");
			return false;
		}
		
	}
	else
	{
		document.printmodules.submit();
		//window.location.href="Accounts.do?command=Details&orgId="+document.printmodules.orgId.value;
		//window.location.href="Accounts.do?command=Details&orgId="+document.printmodules.orgId.value+"&selectMod="+rad_val;
		//window.close();
	}

	return rad_val;

}

function show(item){

	if( item  == 1) {
		document.getElementById("divTesto").style.display ='block' ;
		document.getElementById("divAnaliti").style.display ='none';
		document.getElementById("divMatrici").style.display ='none' ;
		document.getElementById("divMotivo").style.display ='none' ;
		svuota();	
	}
	if( item  == 2) {
		
		document.getElementById("divMotivo").style.display ='block' ;
		document.getElementById("divMatrici").style.display ='none' ;
		document.getElementById("divAnaliti").style.display ='none';
		document.getElementById("numVerbale").value = '' ;
		document.getElementById("divTesto").style.display ='none' ;
		svuota();
		
	}
	if( item  == 4) {
		document.getElementById("divMatrici").style.display ='block' ;
		document.getElementById("divAnaliti").style.display ='none';
		document.getElementById("numVerbale").value = '' ;
		document.getElementById("divTesto").style.display ='none' ;
		document.getElementById("divMotivo").style.display ='none' ;
		svuota();
		
	}
	
	if (item == 5)
	{
		document.getElementById("divAnaliti").style.display ='block';
		document.getElementById("divMatrici").style.display ='none' ;
		document.getElementById("numVerbale").value = '' ;
		document.getElementById("divTesto").style.display ='none' ;
		document.getElementById("divMotivo").style.display ='none' ;
		svuota();
	}
	
}

function svuota()
{
	var size = document.getElementById("size").value;
	var size1 = document.getElementById("size1").value;

	document.getElementById("quesiti_diagnostici_sigla").value = -1;

	if(size > 0) {
		for (var i=1; i <= size; i++)
		{
			document.getElementById("analitiId_"+i).value = -1;
			document.getElementById("pathAnaliti_"+i).value = '';
		}

	}
	
	document.getElementById('size').value = 0;

	if(size1 > 0) {
		for (var j=1; j <= size1; j++)
		{
			document.getElementById("IdMatrice_"+j).value = -1;
			document.getElementById("path_"+j).value = '';
		}

	}
	
	document.getElementById('size1').value = 0;
}

function openTree(campoid1,campoid2,table,id,idPadre,livello,multiplo,divPath,idRiga)
{

	window.open('Tree.do?command=DettaglioTree&multiplo='+multiplo+'&divPath='+divPath+'&idRiga='+idRiga+'&campoId1='+campoid1+'&campoId2='+campoid2+'&nomeTabella='+table+'&campoId='+id+'&campoPadre='+idPadre+'&campoDesc=nome&campoLivello=livello&campoEnabled=nuova_gestione&sel=true');

}

</script>


<form method="post"  name = "printmodules" action="PrintModulesHTML.do?command=PrintEtichette">
<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:label name=""><a href="OsaSearch.do?">Etichette</a></dhv:label> >
<dhv:label name="">Selezionare etichetta</dhv:label>
</td>
</tr>
</table>
<table cellpadding="4" cellspacing="0" width="70%" class="details">
<tr>
	<th colspan="2"><strong>Selezionare il tipo di etichetta</strong></th>
</tr>
<tr class="containerBody">
 	<td nowrap class="formLabel"></td>
	<td>
		<input type="radio" name="selectMod" value="1" onclick="javascript:show(1);">Numero verbale<br>
		<input type="radio" name="selectMod" value="2" onclick="javascript:show(2);" >Motivo del prelievo<br>
		<input type="radio" name="selectMod" value="4" onclick="javascript:show(4);">Matrice<br>
<!-- 	<input type="radio" name="selectMod" value="5" onclick="javascript:show(5);">Analita<br>  -->
	</td>
</tr>
</table>
<br><br/>

<div id="divTesto" style="display: none">
<table cellpadding="4" cellspacing="0" width="70%" class="details">
<tr>
	<th colspan="2"><strong>N.Verbale</strong></th>
</tr>
<tr>
	<td nowrap class="formLabel">
        <dhv:label name="">Inserire numero verbale</dhv:label>
      </td>
    <td><input type="text" id="numVerbale" name="num_verbale"><br><br></td>
</tr>
</table>
</div>	

<div id="divMotivo" style="display: none">
<table cellpadding="4" cellspacing="0" width="70%" class="details">
<tr>
	<th colspan="2"><strong>Quesito diagnostico</strong></th>
</tr>
<tr>
	<td nowrap class="formLabel">
        <dhv:label name="">Selezionare il quesito diagnostico</dhv:label>
      </td>
    <td><%=QuesitiList.getHtmlSelect("quesiti_diagnostici_sigla",-1)%><br><br></td>
</tr>
</table>
</div>	

<div id="divAnaliti" style="display: none">
<table cellpadding="4" cellspacing="0" width="70%" class="details">
<tr>
	<th colspan="2"><strong>Analiti</strong></th>
</tr>

	  <tr>
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Tipo di Analisi</dhv:label>
      </td>
      <td>
    <table class = "noborder"><tr><td>
    <input type = "hidden" name = "elementi" id = "elementi" value = "0">
    <input type = "hidden" name = "size" id = "size" value = "0">
    <tr id = "clona" style="display: none">
    <td>
    <input type = "hidden" name = "analitiId">
    <input type = "hidden" name = "pathAnaliti">
    
    <div id = "divPathAnaliti"></div>
     
   </td>
   </tr>
   <tr>
   <td>
   <a href = "javascript:openTree('analitiId','pathAnaliti','analiti','analiti_id','id_padre','livello','si','divPathAnaliti','clona')">(Selezionare Tipo Analisi)</center></a><font color="red">*</font>
   <br>
   </td>
   </tr>
   </table>
    </td>
     </tr>


</table>
</div>

<div id="divMatrici" style="display: none">
<table cellpadding="4" cellspacing="0" width="70%" class="details">
<tr>
	<th colspan="2"><strong>Matrici</strong></th>
</tr>
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
    <input type = "hidden" name = "path" id = "path">
    <div id = "divPath"></div>     
   </td>
   </tr>
   <tr>
   <td>
    <a href = "javascript:openTree('idMatrice','path','matrici','matrice_id','id_padre','livello','no','divPath','clonaM')">(Selezionare Matrice)</a><font color="red">*</font>
   </td>
   </tr>
   </table>
    </td>
	</tr>
	
</table>
</div>
<br>
	<div align="left">
		<input type="button" value ="Genera Etichetta" onclick="return check();">			
	</div>
</form>

