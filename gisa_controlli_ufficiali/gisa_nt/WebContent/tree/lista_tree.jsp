<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.controller.base.Tree"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script>

function getContenutoCombo()
{
	table = document.getElementById('tabella').value ;
	PopolaCombo.getSchema(table,getContenutoComboCallback);
}
function getContenutoComboCallback(value)
{
	descrizioni = value [0] ;
	valori = value[0];
	var select1 = document.getElementById("campoId"); //Recupero la SELECT
	var select2 = document.getElementById("campoPadre"); //Recupero la SELECT
	var select3 = document.getElementById("campoDesc"); //Recupero la SELECT
	var select4 = document.getElementById("campoLivello"); //Recupero la SELECT
    

    //Azzero il contenuto della seconda select
    for (var i = select1.length - 1; i >= 0; i--)
    {
    	 select1.remove(i);
    	 select2.remove(i);
    	 select3.remove(i);
    	 select4.remove(i);
      }


	 if (valori.length<=1 )
	 {
		 document.getElementById("riga1").style.display="none";
		 document.getElementById("riga2").style.display="none";
		 document.getElementById("riga3").style.display="none";
		 document.getElementById("riga4").style.display="none";


		document.getElementById('msg').innerHTML="<font color = 'red'>La tabella non esiste</font>"
				
}
	 else
	 {
	 for(j =0 ; j<valori.length; j++){
         //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
         var NewOpt = document.createElement('option');
         var NewOpt2 = document.createElement('option');
         var NewOpt3 = document.createElement('option');
         var NewOpt4 = document.createElement('option');
         
         NewOpt.value = valori[j]; // Imposto il valore
         if(valori[j] != null)
         	NewOpt.text = valori[j]; // Imposto il testo
         	NewOpt.title = valori[j];

         	NewOpt2.text = valori[j]; // Imposto il testo
         	NewOpt2.title = valori[j];

         	NewOpt3.text = valori[j]; // Imposto il testo
         	NewOpt3.title = valori[j];

         	NewOpt4.text = valori[j]; // Imposto il testo
         	NewOpt4.title = valori[j];
         //Aggiungo l'elemento option
         try
         {
       	  	select1.add(NewOpt, null); //Metodo Standard, non funziona con IE
       	 select2.add(NewOp2t, null); //Metodo Standard, non funziona con IE
       	select3.add(NewOpt3, null); //Metodo Standard, non funziona con IE

    	select4.add(NewOpt4, null); //Metodo Standard, non funziona con IE
         }catch(e){
       	  select1.add(NewOpt); // Funziona solo con IE
       	 select2.add(NewOpt2); // Funziona solo con IE
       	 select3.add(NewOpt3); // Funziona solo con IE
       	 select4.add(NewOpt4); // Funziona solo con IE
         }
         }

	 document.getElementById("riga1").style.display="";
	 document.getElementById("riga2").style.display="";
	 document.getElementById("riga3").style.display="";
	 document.getElementById("riga4").style.display="";
	 document.getElementById('msg').innerHTML=""
	 }
	
}


</script>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
Gestione Alberi 

</td>
</tr>
</table>
<%
ArrayList<Tree> listaTree = (ArrayList<Tree>) request.getAttribute("ListTree");
%>


<!--<a href = "Tree.do?command=AddTree" >Aggiungi Alberatura </a>-->


<br><br>
<h1>Ricarica Struttura in memoria</h1><br>
<form method="post" action = "Tree.do?command=ReloadAlbero" onsubmit="document.getElementById('tab').value = document.getElementById('tabella').value">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr><td class = "formLabel">Nome Tabella</td><td>
<input type = "hidden" name = "nomeTabella" id = "tab" >
<input type = "text" id = "tabella" name = "tabella" > <a href= "#" ></a></td></tr>
<tr id ="riga1" style="display: none"><td class="formLabel">Campo ID</td><td> <select name = "campoId" id = "campoId"></select></td></tr>
<tr id ="riga2" style="display: none"><td class="formLabel">Campo Padre</td><td> <select name = "campoPadre" id = "campoPadre"></select></td></tr>
<tr id ="riga3" style="display: none"><td class="formLabel">Campo Descr</td><td> <select name = "campoDesc" id = "campoDesc"></select></td></tr>
<tr id ="riga4" style="display: none"><td class="formLabel">Campo Livello</td><td> <select name = "campoLivello" id = "campoLivello"></select></td></tr>


</table>
<input type = "submit" value = "Carica" >
</form>
<br>
<h1>Visualizza Albero</h1><br>
<form method="post" action = "Tree.do?command=DettaglioTree">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr><td class = "formLabel">Nome Tabella</td><td><input type = "text" id = "tabella" name = "nomeTabella" > <a href= "#" ></a></td></tr>
<tr id ="riga1" style="display: none"><td class="formLabel">Campo ID</td><td> <select name = "campoId" id = "campoId"></select></td></tr>
<tr id ="riga2" style="display: none"><td class="formLabel">Campo Padre</td><td> <select name = "campoPadre" id = "campoPadre"></select></td></tr>
<tr id ="riga3" style="display: none"><td class="formLabel">Campo Descr</td><td> <select name = "campoDesc" id = "campoDesc"></select></td></tr>
<tr id ="riga4" style="display: none"><td class="formLabel">Campo Livello</td><td> <select name = "campoLivello" id = "campoLivello"></select></td></tr>


</table>
<input type = "submit" value = "Carica">
</form>


<br>
<h1>Ricarica Struttura Oia</h1><br>
<form method="post" action = "Tree.do?command=ReloadStruttura">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr><td class = "formLabel">Asl</td><td>
<%=SiteList.getHtmlSelect("idAsl", -1) %><a href= "#" ></a></td></tr>

</table>
<input type = "submit" value = "Carica">
</form>


<div id = "msg"></div>