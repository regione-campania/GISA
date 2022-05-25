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

<jsp:useBean id="TreePiani" class="org.aspcfs.modules.controller.base.Tree" scope="request"/>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<!-- JQUERY UI PER FINESTRE DI CONFERMA  -->
<script type="text/javascript" src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />
<link rel="stylesheet" type="text/css" href="css/jqcontextmenu.css" />
<script type="text/javascript" src="javascript/jqcontextmenu.js"></script>
<link rel="stylesheet" href="css/jquery.treeview.css" />
<link rel="stylesheet" href="css/screentree.css" />
<script src="javascript/jquery.js" type="text/javascript"></script>
<script src="javascript/jquery.cookie.js" type="text/javascript"></script>
<script src="javascript/jquery.treeview.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript" src="http://static.jstree.com/v.1.0pre/jquery.jstree.js"></script>
<jsp:useBean id="ListaPianiLookup" class="org.aspcfs.utils.web.LookupList"  scope = "request"/>


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
		document.getElementById("formRemove").style.display = "none";
		document.getElementById("formPadre").style.display = "none";
		document.getElementById("idPadre").value = idNodo;
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
				if (idPadreElementoSelezionato == -1)
					idPadreElementoSelezionato = idPadre ;
				setNuovoitemnelpadre(idRiga, idNodo, pathDesc, id1, id2,
						divPath,idPadre,multiplo);

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

	function verificasalvataggio (flagSalvataggio,nomePiano)
	{
		if (flagSalvataggio!=null)
		{
			if(flagSalvataggio=='ok')
			{
				alert('Configurazione Avvenuta Correttamente Per Il Piano' +nomePiano + '!');
				window.close();
			}
			else
			{
				document.getElementById('error').innserHTML='Errore nel salvataggio della configurazione per il Piano '+nomePiano ;
			}
			}
	}
</script>


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
	#modale-conferma {
		--colore-annulla: #e72626;
		--colore-conferma: #47A;
		min-width: 300px;
		min-height: 300px;
		padding: 15px;
		position: relative;
		display: flex;
		flex-direction: column;
		background-color: whitesmoke;
		color: black;
		border-radius: .25rem;
		opacity: 0;
		overflow: auto;
	}
	#modale-conferma .box-tasti {
		height: 50px;
		display: flex;
		justify-content: space-around;
		align-items: center;
		margin-top: auto;
	}
	#modale-conferma .tasto-modale {
		all: unset;
		cursor: pointer;
		background-color: transparent;
		padding: .33rem;
		border: 1px solid;
		border-radius: .25rem;
		transtion: .3s;
	}
	#modale-conferma .tasto-modale:disabled {
		border-color: lightgrey;
		color: lightgrey;
		cursor: not-allowed;
	}
	#modale-conferma #tasto-annulla:not(:disabled) {
		border-color: var(--colore-annulla);
		color: var(--colore-annulla);
	}
	#modale-conferma #tasto-annulla:not(:disabled):hover {
		background-color: var(--colore-annulla);
		color: whitesmoke;
	}
	#modale-conferma #tasto-conferma:not(:disabled) {
		border-color: var(--colore-conferma);
		color: var(--colore-conferma);
	}
	#modale-conferma #tasto-conferma:not(:disabled):hover {
		background-color: var(--colore-conferma);
		color: whitesmoke;
	}
	@keyframes animazione-modale {
		from {opacity: 0} to {opacity: 1}
	}
</style>


</head>
<%
int idPiano = -1 ;
if (request.getAttribute("idPiano") != null)
	idPiano = (Integer)request.getAttribute("idPiano");
%>
<body
	onload="verificasalvataggio('<%=request.getAttribute("Error") %>','<%= ListaPianiLookup.getSelectedValue(idPiano).replaceAll("'", "")%>')">

<%="CONFIGURAZIONE PER PIANO "+ListaPianiLookup.getSelectedValue(idPiano).replaceAll("'", "") %>
<font color="red">
<div id="error"></div>
</font>
<form id="configura-piani-form" method="post" action="MatTree.do?command=SalvaConfigurazione">


<input type="hidden" name="tabellaMappingPiani" value="<%=request.getAttribute("tabellaMappingPiani") %>" />
<input type="hidden" name="colonnatabellaMappingPiani" value="<%=request.getAttribute("colonnatabellaMappingPiani") %>" />
<input type="hidden" name="nomeTabella" value="<%=request.getAttribute("nomeTabella") %>" />	
	

<input type="hidden" name="idPiano" value="<%=request.getAttribute("idPiano") %>" />
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
	
	if (application.getAttribute((String)request.getAttribute("nomeTabella"))!=null)
{
		Tree Tree = (Tree)application.getAttribute((String)request.getAttribute("nomeTabella"));

ArrayList<Nodo> listanodi = Tree.getListaNodi();

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
	
	
}
for(Nodo nodo : listanodi)
{
	Nodo.tab = "" ;
	String riga = "" ;
	
	Nodo.getAlberoCheckbox(nodo,Tree.getNomeTabella(),out,TreePiani);
	//out.print(Nodo.getAlbero(nodo,tree.getNomeTabella()));
	
	
}


}


%>
	<input type="button" id="submit-button" value="Salva Configurazione" />
</form>
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



</ul>
</div>

</div>

<div id="modale-conferma" hidden>
	<div id="header-modale"></div>
	<ul id="lista-argomenti" hidden></ul>
	<div class="box-tasti">
		<button class="tasto-modale" id="tasto-annulla" onclick="chiudiModale()">ANNULLA</button>
		<button class="tasto-modale" id="tasto-conferma" onclick="confermaSelezione()">CONFERMA</button>
	</div>
</div>

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

<!-- aggiunto da a.aversano -->
<script>
	//script per la modale di conferma delle opzioni selezionate
	var submitButton = document.getElementById('submit-button');
	submitButton.addEventListener('click', () => {
		caricaInputSelezionati()
		apriModale()
	})
	
	
	/* function declaration section */
	function resettaCampi() {
		var inputs = document.querySelectorAll('input[type="checkbox"]:checked')
		if(inputs.length > 0)
			inputs.forEach(input => input.checked = false)
	}
	
	function caricaInputSelezionati() {
		var modale = getModale()
		var headerModale = document.getElementById('header-modale')
		var lista = document.getElementById('lista-argomenti')
		while(lista.hasChildNodes()) {	//svuota lista
			lista.removeChild(lista.firstChild)
		}
		var inputs = document.querySelectorAll('input[type="checkbox"]:checked')
		if(inputs.length > 0) {
			headerModale.innerText = 'Elementi selezionati'
			lista.hidden = false
			inputs.forEach(input => {
				var li = document.createElement('li')
				li.innerHTML = input.parentElement.innerText
				lista.append(li)
			})
		}
		else {
			lista.hidden = true
			headerModale.innerText = 'Nessun elemento selezionato'
		}
	}
	
	
	function apriModale() {
		var livelloOmbra = getLivelloOmbra()
		var areaVisibile = getAreaVisibile()
		var modale = getModale()
		modale.hidden = false
		document.body.style.overflow = 'hidden'
		document.body.append(livelloOmbra)
		document.body.append(areaVisibile)
		areaVisibile.append(modale)
		modale.playAnimation()
		window.addEventListener('resize', onWindowResize)
	}
	
	function chiudiModale() {
		var livelloOmbra = getLivelloOmbra()
		var areaVisibile = getAreaVisibile()
		var modale = getModale()
		modale.playAnimation('reverse')
		setTimeout(() => {	//aspetta animazione della modale
			modale.hidden = true
			document.body.append(modale)
			areaVisibile.remove()
			livelloOmbra.remove()
			document.body.style.overflow = 'initial'
			window.removeEventListener('resize', onWindowResize)
		}, 400)
	}
	
	function getLivelloOmbra() {
		var livelloOmbra = document.getElementById('livello-ombra')
		if(!livelloOmbra) {
			livelloOmbra = document.createElement('div')
			livelloOmbra.id ='livello-ombra'
			Object.assign(livelloOmbra.style, {
				width: document.body.scrollWidth + 'px',
				height: document.body.scrollHeight + 'px',
				display: 'flex',
				position: 'absolute',
				top: '0',
				left: '0',
				zIndex: '1000',
				backgroundColor: 'rgba(0,0,0,.5)'
			})
			livelloOmbra.resize = function() {
				livelloOmbra.style.width = document.body.scrollWidth + 'px'
				livelloOmbra.style.height = document.body.scrollHeight + 'px'
			}
		}
		
		return livelloOmbra
	}
	
	function getAreaVisibile() {
		var areaVisibile = document.getElementById('area-visibile')
		if(!areaVisibile) {
			areaVisibile = document.createElement('div')
			areaVisibile.id = 'area-visibile'
			Object.assign(areaVisibile.style, {
				width: '100%',
				height: '100%',
				display: 'flex',
				flexDirection: 'column',
				justifyContent: 'center',
				alignItems: 'center',
				position: 'absolute',
				top: window.scrollY + 'px',
				zIndex: '1001'
			})
			areaVisibile.adjustPosition = function() {
				areaVisibile.style.top = window.scrollY + 'px'
			}
		}
		
		return areaVisibile
	}
	
	function getModale() {
		var modale = document.getElementById('modale-conferma')
		modale.playAnimation = function(mode) {
			if(mode == 'reverse') {
				modale.style.animation = 'animazione-modale ease-out .4s reverse backwards'
			}
			else {
				modale.style.animation = 'animazione-modale ease-in .4s forwards'
			}
		}
		return modale
	}
	
	function onWindowResize() {
		getLivelloOmbra().resize()
		getAreaVisibile().adjustPosition()
	}
	
	function confermaSelezione() {
		var form = document.getElementById('configura-piani-form')
		chiudiModale()
		form.submit()
	}
	
</script>
</body>
</html>
















