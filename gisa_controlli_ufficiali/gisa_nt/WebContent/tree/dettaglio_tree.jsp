<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@page import="org.aspcfs.modules.controller.base.Tree"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.controller.base.Nodo"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<!-- JQUERY UI PER FINESTRE DI CONFERMA  -->
<script src="javascript/jquery-1.9.1.js"></script>
   <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />
<link rel="stylesheet" type="text/css" href="css/jqcontextmenu.css" />
<script type="text/javascript" src="javascript/jqcontextmenu.js"></script>


<script type="text/javascript">

jQuery.noConflict();
jQuery(document).ready(function($){
	$('a.mylinks').addcontextmenu('contextmenu1'); //apply context menu to links with class="mylinks"
});

</script>
 
	<link rel="stylesheet" href="css/jquery.treeview.css" />
	<link rel="stylesheet" href="css/screentree.css" />
	<script src="javascript/jquery.js" type="text/javascript"></script>
	<script src="javascript/jquery.cookie.js" type="text/javascript"></script>
	<script src="javascript/jquery.treeview.js" type="text/javascript"></script>
 <script src="javascript/jquery-ui.min.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>


<script>
	flag = true;
	function controllaNodo() {
		idnodo = document.remItemForm.idNodo.value;
		PopolaCombo.controlloNodo(idnodo,
				document.remItemForm.nomeTabella.value,
				document.remItemForm.campoPadre.value, controllaNodoCallback);
	}
	function controllaNodoCallback(value) {
		if (value == 'si') {
			flag = false;
			alert('Il nodo selezionato contiene dei figli e non puo essere eliminato ! ');
		} else {
			document.remItemForm.submit();

		}

	}
	$( function() {
		$("#tree").treeview( {
			collapsed :true,
			animated :"fast",
			control :"#sidetreecontrol",
			prerendered :true,
			persist :"location"
		});
	})

	function showPath(pathDesc, pathId, idNodo, livello) {
		var splitted = pathId.split(";");
		document.getElementById("formRemove").style.display = "none";
		document.getElementById("formPadre").style.display = "none";
		document.getElementById("idPadre").value = splitted[0];
		document.getElementById("padre").innerHTML = pathDesc;
		document.getElementById("livello").value = livello;

		document.getElementById("id_Nodo").value = idNodo;
		document.getElementById("elemento").innerHTML = pathDesc;

	}

	/*
	 *	MULTIPLO : INDICA SE OCCORRE GESTIRE LA SELEZIONE MULTIPLA
	 *	ID1 : ID DEL CAMPO DI TESTO HIDDEN IN CUI VERRA SETTATO IL VALORE DELO NODO
		ID2 : ID DEL CAMPO DI TSTO HIDDEN IN CUI VIENE SETTATO IL PATH COMPLETO COME STRINGA (TALE VALORE VIAGGERà NELLA REQUEST)
	 *	pathDesc : PERCORSO RELATIVO AL NODO A PARTIRE DALLA ROOT
		IDNODO : ID CORRISPONDENTE AL NODO SELEZIONATI
		LIVELLO : LIVELLO DEL NODO SELEZIONATO
		DIVPATH : ID DELLLA LABEL IN CUI FAR VISUALIZZARE A VIDEO IL PATH COMPLETO DEL NODO SELEZIONATO
	 */
	function setItem(multiplo, id1, id2, pathDesc, pathId, idNodo, livello,
			divPath, idRiga,idPadre) {
		if (window.opener != null) {

			/*if (multiplo == 'false') // caso di selezione singola
			{
				if (window.opener.document.getElementById(id1) != null) {
					window.opener.document.getElementById(id1).value = idNodo;
				}
				if (window.opener.document.getElementById(id2) != null) {
					window.opener.document.getElementById(id2).value = pathDesc;
				}
				if (window.opener.document.getElementById(divPath) != null) {
					window.opener.document.getElementById(divPath).innerHTML = pathDesc;
				}

				window.close();
			} else {*/
				var splitted = pathId.split(";");
				var index;
				if (splitted[0]==-1)
					index = 1;
				else 
					index = 0;
				
				if (idPadreElementoSelezionato == -1)
					idPadreElementoSelezionato = splitted[index] ;
				setNuovoitemnelpadre(idRiga, idNodo, pathDesc, id1, id2,
						divPath,splitted[index],multiplo);

			//}
		}

	}


	var idPadreElementoSelezionato = -1 ;
	function setNuovoitemnelpadre(idRiga, idNodo, pathDesc, id1, id2, divPath,idPadre,multiplo) {
		if (idPadreElementoSelezionato == idPadre)
		{
		var maxElementi = 100;
		var elementi;
		var elementoClone;
		var tableClonata;
		var tabella;
		var selezionato;
		var x;
		if (divPath=='divPath')
		{ // matrici
			elementi = window.opener.document.getElementById('elementi1');
			size = window.opener.document.getElementById('size1');
		}
		else
		{ // analiti
			elementi = window.opener.document.getElementById('elementi');
			size = window.opener.document.getElementById('size');
		}
		trovato = false ;
		for (ind = 1 ; ind <=parseInt(size.value);ind++)
		{
			
			if(window.opener.document.getElementById(id1+'_'+ind)!=null && window.opener.document.getElementById(id1+'_'+ind).value==idNodo)
			{
				trovato = true ;
			}
		}
		
		if(trovato == false)
		{
			
		elementi.value = parseInt(elementi.value) + 1;
		
		size.value = parseInt(size.value) + 1;
		var elementoCorrente = elementi.value;
		var clonanbsp = window.opener.document.getElementById(idRiga);
		


		
		/*clona riga vuota*/
		clone = clonanbsp.cloneNode(true);
		
		clone.getElementsByTagName('INPUT')[0].name = id1 + "_"+ elementi.value;
		clone.getElementsByTagName('INPUT')[0].id = id1 + "_" + elementi.value;
		clone.getElementsByTagName('INPUT')[0].value = idNodo;

		
		clone.getElementsByTagName('INPUT')[1].name = id2 + "_"
				+ elementi.value;
		clone.getElementsByTagName('INPUT')[1].id = id2 + "_" + elementi.value;
		clone.getElementsByTagName('INPUT')[1].value = pathDesc;

		clone.getElementsByTagName('DIV')[0].id = divPath + "_"
				+ elementi.value;
		clone.getElementsByTagName('DIV')[0].innerHTML = "<b>" + elementi.value
				+ "</b>" + pathDesc;
		clone.id = idRiga + "_" + elementi.value;
		clone.style.display = ""
			
		/*Aggancio il nodo*/
		clonanbsp.parentNode.appendChild(clone);
		if(multiplo=='true')
		{
			 $("#dialog").dialog("open");
		/*if (confirm('Elemento Settato nella finestra padre .Vuoi Tornare nella maschera di inserimento ? Cliccare su Ok per conferma') == true)
			window.close();
		*/
		/*else
		{*/
			if(trovato == true)
			{
			alert('Attenzione questo elemento e stato gia selezionato');
			}
		//}
		}
		else
		{
			window.close();
		}}
		
		}
		else
			alert('Attenzione non puoi selezionare elementi di rami diversi');
	}

	function addItem() {

		document.getElementById("formPadre").style.display = "";
		document.getElementById("formRemove").style.display = "none";
		document.getElementById("id_Nodo").value = '-1';
		document.getElementById("elemento").innerHTML = '';

	}

	function checkAddItem() {
		if (document.getElementById("idPadre").value == "-1"
				|| document.getElementById("newItem").value == "") {
			alert('Seleziona il nodo padre e la descrizione del nuovo elemento');
			return;
		} else {
			document.addItemForm.submit();
		}
	}

	function checkRemoveItem() {
		controllaNodo();

	}

	function deleteItem() {
		document.getElementById("formRemove").style.display = "";
		document.getElementById("formPadre").style.display = "none";
		document.getElementById("idPadre").value = '-1';
		document.getElementById("padre").innerHTML = '';
		document.getElementById("livello").value = '';
	}
</script>

<script type="text/javascript" src="javascript/jquery.jstree.js"></script>



<style>
	body {
		position: relative !important;
	}
	[hidden] {
		display: none !important;
	}
	#sidetreecontrol {
		transition: opacity .3s;
	}
	#sidetreecontrol.visibile {
		opacity: 1;
		pointer-events: auto;
	}
	#sidetreecontrol:not(.visibile) {
		opacity: 0;
		pointer-events: none;
	}
</style>


</head>
<body>
<script> 
indice = 1;
while (opener.document.getElementById('divPathAnaliti_'+indice)!=null)
{
	opener.document.getElementById('divPathAnaliti_'+indice).parentNode.removeChild(opener.document.getElementById('divPathAnaliti_'+indice)); 
	opener.document.getElementById('analitiId_'+indice).parentNode.removeChild(opener.document.getElementById('analitiId_'+indice)); 
	opener.document.getElementById('pathAnaliti_'+indice).parentNode.removeChild(opener.document.getElementById('pathAnaliti_'+indice)); 
	indice ++ ;
}
opener.document.getElementById('elementi').value = 0 ;
opener.document.getElementById('size').value = 0 ;
</script>

<%
	Tree tree = null;

if (request.getAttribute("TreePiani")!=null)
{
	tree=(Tree)request.getAttribute("TreePiani");
}
else
{
	 tree = (Tree)application.getAttribute((String)request.getAttribute("nomeTabella"));
	 if(application.getAttribute((String)request.getAttribute("nomeTabella")).equals("analiti")) {
	 %>
	 <script>
	 indice = 1;
		while (opener.document.getElementById('divPathAnaliti_'+indice)!=null && opener.document.getElementById('divPath_'+indice)== null)
		{
			opener.document.getElementById('divPathAnaliti_'+indice).parentNode.removeChild(opener.document.getElementById('divPathAnaliti_'+indice)); 
			opener.document.getElementById('analitiId_'+indice).parentNode.removeChild(opener.document.getElementById('analitiId_'+indice)); 
			opener.document.getElementById('pathAnaliti_'+indice).parentNode.removeChild(opener.document.getElementById('pathAnaliti_'+indice)); 
			indice ++ ;
		}
	 </script>
	 <% 
	 }//fine if analiti
}
%>
<table class="trails" cellspacing="0">
	<tr>
		<td width="100%">
		<%
if(request.getAttribute("Sel")!=null)
{
%>
		<h2>SELEZIONARE IL PERCORSO COMPLETO <%= (tree.getIdPiano()>0)? "(PIANO :" + tree.getDescrizionePiano().toUpperCase()+")" : "" %></h2>
		<%	
}
else
{
%> Home > <a href="Tree.do?command=ListTree">Struttura ad Albero </a> 

<%} %>

		</td>
		<td>
		<div style="display: block; float: right; margin-right: 15px;"><a
			href="javascript:window.close()"> CHIUDI</a></div>
		</td>
	</tr>
</table>

<table style="display: block; float: right;">

	
	<tr style="display: none; float: right;" id="formPadre">
		<td>

		<form name="addItemForm" method="post"
			action="Tree.do?command=AggiungiLivello">
		<fieldset><legend>Nuovo Elemento</legend>
		<table class="details">
			<input type="hidden" name="idPadre" id="idPadre" value="-1" />
			<input type="hidden" name="livello" id="livello" />
			<tr>
				<td class="formLabel">Seleziona Padre da Albero</td>
				<td>
				<div id="padre"></div>
				</td>
			</tr>
			<tr>
				<td class="formLabel">Elemento</td>
				<td><input type="text" name="newItem" id="newItem"></td>
			</tr>
			<tr>
				<td class="formLabel">Codice (Se previsto)</td>
				<td><input type="text" name="codEsame" id="codEsame"></td>
			</tr>
			
			<input type="hidden" name="campoId"
				value="<%=request.getAttribute("campoId") %>" />
			<input type="hidden" name="campoPadre"
				value="<%=request.getAttribute("campoPadre") %>" />
			<input type="hidden" name="campoDesc"
				value="<%=request.getAttribute("campoDesc") %>" />
			<input type="hidden" name="campoLivello"
				value="<%=request.getAttribute("campoLivello") %>" />
			<input type="hidden" name="nomeTabella"
				value="<%=request.getAttribute("nomeTabella") %>" />
			<tr>
				<td colspan="2"><input type="button" value="Inserisci" onclick="checkAddItem()" ></td>
			</tr>
		</table>
		</fieldset>
		</form>
		</td>
	</tr>
	<tr style="display: none; float: right;" id="formRemove">
		<td>

		<form method="post" action="Tree.do?command=RimuoviElemento"
			name="remItemForm">

		<fieldset><legend>Rimuovi Elemento</legend>
		<table class="details">
			<tr>
				<td class="formLabel">Seleziona Elemento</td>
				<td>
				<div id="elemento" style="width: auto;"></div>
				</td>
			</tr>
			<input type="hidden" name="campoId"
				value="<%=request.getAttribute("campoId") %>" />
			<input type="hidden" name="campoPadre"
				value="<%=request.getAttribute("campoPadre") %>" />
			<input type="hidden" name="campoDesc"
				value="<%=request.getAttribute("campoDesc") %>" />
			<input type="hidden" name="campoLivello"
				value="<%=request.getAttribute("campoLivello") %>" />
			<input type="hidden" name="nomeTabella"
				value="<%=request.getAttribute("nomeTabella") %>" />
			<tr>
				<td colspan="2"><input type="button" value="Rimuovi"
					onclick=
	checkRemoveItem();
/></td>
			</tr>
			<input type="hidden" name="idNodo" id="id_Nodo" value="-1" />
		</table>
		</fieldset>
		</form>
		</td>
	</tr>
</table>
<br>



<div id="main">
<div id="filter-container"> <!-- aggiunto da a.aversano -->
	<label for="filter">Filtra in base al testo:</label><br>
	<input id="filter" name="filter">
</div>
<div id="sidetree">
<div class="treeheader">&nbsp;</div>
<div id="sidetreecontrol" class="visibile"><a id="comprimi-button" href="?#">Comprimi</a> | <a id="espandi-button" href="?#">Espandi</a></div>

<ul class="treeview" id="tree">
	<%

ArrayList<Nodo> listanodi = tree.getListaNodi();

if(request.getAttribute("idCampoIntero")!=null)
{
	
	Nodo.idcampoIntero = (String)request.getAttribute("idCampoIntero");
	Nodo.idcampoTest=(String) request.getAttribute("idCampoTesto");
	Nodo.multiplo =(String) request.getAttribute("multiplo");
	Nodo.divPath =(String) request.getAttribute("divPath");
	Nodo.idRiga =(String) request.getAttribute("idRiga");
}
else
{
	Nodo.idcampoIntero = "";
	Nodo.idcampoTest="";
	Nodo.multiplo ="";
	Nodo.divPath="";
	Nodo.idRiga="";
	Nodo.colonnaPadre="";
	
}
for(Nodo nodo : listanodi)
{
	Nodo.tab = "" ;
	String riga = "" ;
	Nodo.getAlbero(nodo,tree.getNomeTabella(),out);

	
	
}

if (((String)request.getAttribute("nomeTabella")).equals("matrici"))
{
%>
<script>
	/*rimuovo gli elementi matrice settati nel padre*/
	indice = 1
	while (opener.document.getElementById('divPath_'+indice)!=null)
	{
		opener.document.getElementById('divPath_'+indice).parentNode.removeChild(opener.document.getElementById('divPath_'+indice)); 
		indice ++ ;
		opener.document.getElementById('elementi1').value = 0 ;
		opener.document.getElementById('size1').value = 0 ;

	}

	</script>
<%
}



%>
<script>
$(function() {
		var Return;
        $( "#dialog" ).dialog({
        	autoOpen: false,
            resizable: false,
            closeOnEscape: false,
            width:400,
            height:150,
            draggable: false,
            modal: true,
            buttons: {
                "Si": function() {
        		window.close();
                    $( this ).dialog( "close" );
                    return true;
                },
                "No": function() {
						
                    $( this ).dialog( "close" );
                    
                    return false
                }
            }
        });
    });
    

</script>
<div id="dialog" title="Conferma Inserimento">
    <p>L'analita è stato selezionato:<br>Cliccare "NO" per aggiungere altri analiti;<br>Cliccare "SI" per ritornare alla maschera di inserimento.</p>
</div>

</ul>
</div>

</div>


<!--HTML for Context Menu 1-->
<ul id="contextmenu1" class="jqcontextmenu">
	<dhv:permission name="tree-add">
		<li><a href="#" onclick="javascript: addItem()";
>Nuovo Elemento</a></li>
	</dhv:permission>
	<dhv:permission name="tree-delete">
		<li><a href="#" onclick="javascript:deleteItem()">Rimuovi
		Elemento</a></li>
	</dhv:permission>

</ul>

<!-- aggiunto da a.aversano -->
<script>
	//script per la gestione del filtro
	
	/* main */
	marcaNodi();
	const RITARDO_FILTRO = 400;	//utile per migliorare le prestazioni
	var filtro = document.getElementById('filter');
	var comprimiButton = document.getElementById('comprimi-button');
	var espandiButton = document.getElementById('espandi-button');
	var contenitoreTasti = document.getElementById('sidetreecontrol');
	var interazionePrecedente, interazioneCorrente;
	
	var isAlberoExpanded = false
	comprimiButton.addEventListener('click', () => isAlberoExpanded = false)
	espandiButton.addEventListener('click', () => isAlberoExpanded = true)
	
	filtro.value = '' //svuota filtro al caricamento della pagina
	filtro.addEventListener('input', () => {
		interazioneCorrente = Date.now();
		if(filtro.value == '') {
			contenitoreTasti.classList.add('visibile') //abilita tasti comprimi/espandi
			resettaFiltro();
			if(isAlberoExpanded)
				comprimiButton.click();
		}
		else {
			contenitoreTasti.classList.remove('visibile') //disabilita tasti comprimi/espandi
			if(!isAlberoExpanded)
				espandiButton.click();
			if(!interazionePrecedente)
				interazionePrecedente = interazioneCorrente;
			else {	// c'è stata un'interazione in passato
				if(interazioneCorrente - interazionePrecedente > RITARDO_FILTRO)
					eseguiRicercaFiltro(filtro.value);
			}
		}
	})
	
	/* function declaration section */
	
	function marcaNodi() {
		var radiceAlbero = document.getElementById('tree')
		var listaNodi = radiceAlbero.querySelectorAll('li')
		listaNodi.forEach(nodo => {
			if(nodo.classList.contains('collapsable') || nodo.classList.contains('expandable'))
				nodo.classList.add('nodo-genitore')
			else
				nodo.classList.add('nodo-foglia')
		})
	}
	
	function resettaFiltro() {
		var radiceAlbero = document.getElementById('tree')
		var nodiNascosti = radiceAlbero.querySelectorAll('[hidden]');
		nodiNascosti.forEach(nodoNascosto => nodoNascosto.hidden = false);
	}
	
	function eseguiRicercaFiltro(pattern) {
	    var radiceAlbero = document.getElementById('tree');
	    var nodiGenitore = radiceAlbero.querySelectorAll('.nodo-genitore');
	    if(nodiGenitore.length == 0) {	//non ci sono nodi genitore
	    	eseguiRicercaSemplice(pattern);
	    	return;
	    }
	    nodiGenitore.forEach(nodoGenitore => {
	        var tuttiFigliNascosti = true;
	        var nodiFoglia = nodoGenitore.querySelectorAll('.nodo-foglia');
	        nodiFoglia.forEach(nodoFoglia => {
	            if(!nodoFoglia.textContent.toLowerCase().includes(normalizzaStringa(pattern.toLowerCase()))) {
	                nodoFoglia.hidden = true;
	            }
	            else {
	                nodoFoglia.hidden = false;
	                tuttiFigliNascosti = false;
	            }
	        })
	        if(tuttiFigliNascosti)
	            nodoGenitore.hidden = true;
	        else
	            nodoGenitore.hidden = false;
	    })
	}
	
	function eseguiRicercaSemplice(pattern) {
		var radiceAlbero = document.getElementById('tree');
		var nodiFoglia = radiceAlbero.querySelectorAll('.nodo-foglia');
		nodiFoglia.forEach(nodoFoglia => {
            if(!nodoFoglia.textContent.toLowerCase().includes(normalizzaStringa(pattern.toLowerCase())))
                nodoFoglia.hidden = true;
            else
            	nodoFoglia.hidden = false;
        })
	}
	
	function normalizzaStringa(s) {
		s = s.trim()
		var t = ''
		var c = ''
		for(var i = 0; i < s.length; i++) {
			c = s.charAt(i)
			if(c != ' ' || (c == ' ' && s.charAt(i+1) != ' '))
				t = t + c
		}
		return t
	}
</script>
</body>
</html>
















