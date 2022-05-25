/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
function generaNuovoCodice(asl)
{
	TestAutopsie.getProgressivoNumRifMittente(
			{
					callback:function(progressivo) 
					{ 
						
						popolaNumRifMittente(progressivo,asl)
					},
					async: false,
					timeout:5000,
					errorHandler:function(message, exception)
					{
					    //Session timedout/invalidated
					    if(exception && exception.javaClassName=='org.directwebremoting.impl.LoginRequiredException')
					    {
					        alert(message);
					        //Reload or display an error etc.
					        window.location.href=window.location.href;
					    }
					    else
					        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
					 }
 			});
}


function checkform(form) {
	
	if (form.dataAutopsia.value == '') {
		alert("Inserire la data dell'esame necroscopico");	
		form.dataAutopsia.focus();
		return false;
	}
	
	if (form.lookupAutopsiaSalaSettoria.value == '') {
		alert("Inserire la sala settoria di destinazione");	
		form.lookupAutopsiaSalaSettoria.focus();
		return false;
	}
	
	if(!controllaDataAnnoCorrente(form.dataAutopsia,'Data'))
	{
		form.dataAutopsia.focus();
		return false;
	}
	
	if(confrontaDate(form.dataAutopsia.value,form.dataMorte.value)<0)
	{
		alert("La data dell'esame necroscopico non puo'' essere antecedente alla data del decesso("+form.dataMorte.value+")");	
		form.dataAutopsia.focus();
		return false;
	}
	
	
	//if(form.dataSmaltimento.value!='' && confrontaDate(form.dataAutopsia.value,form.dataSmaltimento.value)>0)
	//{
	//alert("La data dell'esame necroscopico non puo'' essere successivo alla data dello smaltimento("+form.dataSmaltimento.value+")");	
	//form.dataAutopsia.focus();
	//return false;
	//}
	
	
	if(document.getElementById('idOp')!=null && document.getElementById('idOp').value=='')
	{
		alert("Selezionare almeno un operatore");
		return false;
	}
	
	if(document.getElementById('idOp')!=null && document.getElementById('idOp').value.split(',').length>5)
	{
		alert("Non � possibile selezionare pi� di cinque operatori");
		return false;
	}
						
	if ( document.getElementById('mc_5')!=null && document.getElementById('mc_5').checked ) {		
		if (!testFloating(form.temperaturaConservazione.value)) {	
			alert ("Non è possibile inserire valori non numerici per il campo Temperatura di Conservazione");					
			return false; 
		}
	}
			
	
	
	var i =1;
	var dettaglioVuoto = false;
	var indiceDettaglioVuoto;
	
	while(document.getElementById('organo' + i) != null) 
	{
		
		if (document.getElementById('organo' + i).value == '' && (document.getElementById('tipo' + i).value != '' || document.getElementById('esiti' + i).value != '')) 
		{
			alert("Selezionare, nella sezione Dettaglio Esami, l\'organo nella riga " + i);
			document.getElementById('organo' + i).focus();
			return false;
		}
		if (document.getElementById('tipo' + i).value == '' && (document.getElementById('organo' + i).value != '' || document.getElementById('esiti' + i).value != '')) 
		{
			alert("Specificare, nella sezione Dettaglio Esami, la tipologia di esame nella riga " + i);
			document.getElementById('tipo' + i).focus();
			return false;
		}
//		if (
//				(
//				 document.getElementById('esiti' + i).value == '' && 
//				 document.getElementById('esitoDaAnagrafare' + i ).value == '' 
//			    ) 
//				&& 
//				(
//				 document.getElementById('tipo' + i).value != '' || 
//				 document.getElementById('organo' + i).value != ''
//				)
//			) 
//		{
//			alert("Selezionare almeno un esito nella riga " + i);
//			document.getElementById('linkChooseEsiti' + i).focus();
//			return false;
//		}
		if (document.getElementById('organo' + i).value == '' && document.getElementById('tipo' + i).value == '' && (document.getElementById('esiti' + i).value == '' || document.getElementById('esitoDaAnagrafare' + i).value == '') && !dettaglioVuoto) 
		{
			dettaglioVuoto = true;
			indiceDettaglioVuoto = i;
		}
		if (document.getElementById('organo' + i).value != '' && document.getElementById('tipo' + i).value != '' && (document.getElementById('esiti' + i).value != '' || document.getElementById('esitoDaAnagrafare' + i).value != '') ) 
		{
			if (dettaglioVuoto) {
				alert("Valorizzare la riga numero " + indiceDettaglioVuoto );
				document.getElementById('organo' + indiceDettaglioVuoto).focus();
				return false;
			}
		}
		i++;
	}
	
	attendere();
	return true;
	
	
}

function toggleGroup( targetId ){ 
	  if (document.getElementById){ 
	        target = document.getElementById( targetId ); 
	           if (target.style.display == "none"){ 
	              target.style.display = ""; 
	           } else { 
	              target.style.display = "none"; 
	           } 
	     } 
	} 


function hiddenDiv( targetId ){ 
	  if (document.getElementById){ 
	        target = document.getElementById( targetId ); 
	        target.style.display = "none"; 
	           
	     } 
}


function showTemperatura( targetId ){ 
	target = document.getElementById( "mc_"+targetId ); 
	if (target.value == "5") {
		document.getElementById("temperatura").style.display = "";		
	} 
	else {
		document.getElementById("temperatura").style.display = "none";	
		document.form.temperaturaConservazione.value='';
	}
}

function showTemperaturaRichiesta( targetId ){ 
	target = document.getElementById( "mcRichiesta_"+targetId ); 
	if (target.value == "5") {
		document.getElementById("temperaturaRichiesta").style.display = "";		
	} 
	else {
		document.getElementById("temperaturaRichiesta").style.display = "none";	
		document.form.temperaturaConservazioneRichiesta.value='';
	}
}

function nascondiMostraLottiInterni()
{
	if(document.getElementById("lottiInterniDiv").style.display=='block')
	{
		document.getElementById("lottiInterniDiv").style.display='none';
	}
	else
	{
		document.getElementById("lottiInterniDiv").style.display='block';
	}
}

function campiAltroDettaglio()
{
	var dettaglioEsami = document.getElementById("dettaglioEsami");
	var numeroDettaglioDaVisualizzare = dettaglioEsami.getElementsByTagName("select").length/2+1;
	
	var organo       		   = document.getElementById("organo1").cloneNode(true);
	var tipo         		   = document.getElementById("tipo1").cloneNode(true);
	var note         		   = document.getElementById("note1").cloneNode(true);
	var esiti        		   = document.getElementById("esiti1").cloneNode(true);
	var esitiTemp      		   = document.getElementById("esitiTemp1").cloneNode(true);
	var esitiValore    		   = document.getElementById("esitiValore1").cloneNode(true);
	var esitiTempValore		   = document.getElementById("esitiTempValore1").cloneNode(true);
	var esitoDaAnagrafare	   = document.getElementById("esitoDaAnagrafare1").cloneNode(true);
	var esitoDaAnagrafareValore	     = document.getElementById("esitoDaAnagrafareValore1").cloneNode(true);
	var esitoTempDaAnagrafare        = document.getElementById("esitoTempDaAnagrafare1").cloneNode(true);
	var esitoTempDaAnagrafareValore  = document.getElementById("esitoTempDaAnagrafareValore1").cloneNode(true);
	var linkChooseEsiti        = document.getElementById("linkChooseEsiti1").cloneNode(true);

	document.getElementById("pulsanteRimuoviUltimoDettaglio").disabled=false;
	organo.setAttribute("name", "organo"+numeroDettaglioDaVisualizzare);
	organo.setAttribute("id"  , "organo"+numeroDettaglioDaVisualizzare);
	organo.setAttribute("onchange"  , organo.getAttribute("onchange").replace("tipo1","tipo"+numeroDettaglioDaVisualizzare));
	organo.setAttribute("onchange"  , organo.getAttribute("onchange").replace("esiti1","esiti"+numeroDettaglioDaVisualizzare));
	
	tipo.setAttribute("name", "tipo"+numeroDettaglioDaVisualizzare);
	tipo.setAttribute("id"  , "tipo"+numeroDettaglioDaVisualizzare);
	tipo.setAttribute("onchange"  , tipo.getAttribute("onchange").replace("organo1","organo"+numeroDettaglioDaVisualizzare));
	tipo.setAttribute("onchange"  , tipo.getAttribute("onchange").replace("esiti1","esiti"+numeroDettaglioDaVisualizzare));
	
	note.setAttribute("name", "note"+numeroDettaglioDaVisualizzare);
	note.setAttribute("id"  , "note"+numeroDettaglioDaVisualizzare);
	
	esiti.setAttribute("name", 		"esiti"+numeroDettaglioDaVisualizzare);
	esiti.setAttribute("id"  , 		"esiti"+numeroDettaglioDaVisualizzare);
	
	esitiTemp.setAttribute("name", 		"esitiTemp"+numeroDettaglioDaVisualizzare);
	esitiTemp.setAttribute("id"  , 		"esitiTemp"+numeroDettaglioDaVisualizzare);
	
	esitiValore.setAttribute("name", 		"esitiValore"+numeroDettaglioDaVisualizzare);
	esitiValore.setAttribute("id"  , 		"esitiValore"+numeroDettaglioDaVisualizzare);
	
	esitiTempValore.setAttribute("name", 		"esitiTempValore"+numeroDettaglioDaVisualizzare);
	esitiTempValore.setAttribute("id"  , 		"esitiTempValore"+numeroDettaglioDaVisualizzare);
	
	esitoDaAnagrafare.setAttribute("name",	"esitoDaAnagrafare"+numeroDettaglioDaVisualizzare );
	esitoDaAnagrafare.setAttribute("id"  ,	"esitoDaAnagrafare"+numeroDettaglioDaVisualizzare );
	
	esitoDaAnagrafareValore.setAttribute("name",	"esitoDaAnagrafareValore"+numeroDettaglioDaVisualizzare );
	esitoDaAnagrafareValore.setAttribute("id"  ,	"esitoDaAnagrafareValore"+numeroDettaglioDaVisualizzare );
	
	esitoTempDaAnagrafare.setAttribute("name",	"esitoTempDaAnagrafare"+numeroDettaglioDaVisualizzare );
	esitoTempDaAnagrafare.setAttribute("id"  ,	"esitoTempDaAnagrafare"+numeroDettaglioDaVisualizzare );
	
	esitoTempDaAnagrafareValore.setAttribute("name",	"esitoTempDaAnagrafareValore"+numeroDettaglioDaVisualizzare );
	esitoTempDaAnagrafareValore.setAttribute("id"  ,	"esitoTempDaAnagrafareValore"+numeroDettaglioDaVisualizzare );
	
	linkChooseEsiti.setAttribute("name", "linkChooseEsiti"+numeroDettaglioDaVisualizzare);
	linkChooseEsiti.setAttribute("id", "linkChooseEsiti"+numeroDettaglioDaVisualizzare);
	
	linkChooseEsiti.setAttribute("onclick", "selezionaEsiti("+numeroDettaglioDaVisualizzare+",document.getElementById('organo"+numeroDettaglioDaVisualizzare+"').options[document.getElementById('organo"+numeroDettaglioDaVisualizzare+"').selectedIndex].innerHTML,document.getElementById('tipo"+numeroDettaglioDaVisualizzare+"').options[document.getElementById('tipo"+numeroDettaglioDaVisualizzare+"').selectedIndex].innerHTML);" );

	tr = document.createElement("tr");
	tr.setAttribute("id", "tr_"+numeroDettaglioDaVisualizzare);
	td1 = document.createElement("td");
	td1.setAttribute("id", "td1_"+numeroDettaglioDaVisualizzare);
	td2 = document.createElement("td");
	td2.setAttribute("id", "td2_"+numeroDettaglioDaVisualizzare);
	td3 = document.createElement("td");
	td3.setAttribute("id", "td3_"+numeroDettaglioDaVisualizzare);
	td4 = document.createElement("td");
	td4.setAttribute("id", "td4_"+numeroDettaglioDaVisualizzare);
	td5 = document.createElement("td");
	td5.setAttribute("id", "td5_"+numeroDettaglioDaVisualizzare);
	
	td1.appendChild(organo);
	td1.appendChild(document.createTextNode(" "));
	td2.appendChild(tipo);
	td2.appendChild(document.createTextNode(" "));
	td3.appendChild(note);
	td3.appendChild(document.createTextNode(" "));
	td4.appendChild(esiti);
	td4.appendChild(esitiTemp);
	td4.appendChild(esitiValore);
	td4.appendChild(esitiTempValore);
	td4.appendChild(esitoDaAnagrafare);
	td4.appendChild(esitoTempDaAnagrafare);
	td4.appendChild(esitoDaAnagrafareValore);
	td4.appendChild(esitoTempDaAnagrafareValore);
	td4.appendChild(linkChooseEsiti);
	td4.appendChild(document.createTextNode(" "));
	br = document.createElement("br");
	br.setAttribute("id", "br"+numeroDettaglioDaVisualizzare);
	td4.appendChild(br);
	
	tr.appendChild(td1);
	tr.appendChild(td2);
	tr.appendChild(td3);
	tr.appendChild(td4);
	tr.appendChild(td5);
	dettaglioEsami.appendChild(tr);
	
	document.getElementById("organo"+numeroDettaglioDaVisualizzare).value="";
	document.getElementById("tipo"+numeroDettaglioDaVisualizzare).value="";
	document.getElementById("note"+numeroDettaglioDaVisualizzare).value="";
	document.getElementById("esiti"+numeroDettaglioDaVisualizzare).value="";
	document.getElementById("esitiTemp"+numeroDettaglioDaVisualizzare).value="";
	document.getElementById("esitiValore"+numeroDettaglioDaVisualizzare).value="";
	document.getElementById("esitiTempValore"+numeroDettaglioDaVisualizzare).value="";
	document.getElementById("esitoDaAnagrafare"+numeroDettaglioDaVisualizzare).value="";
	document.getElementById("esitoTempDaAnagrafare"+numeroDettaglioDaVisualizzare).value="";
	document.getElementById("esitoDaAnagrafareValore"+numeroDettaglioDaVisualizzare).value="";
	document.getElementById("esitoTempDaAnagrafareValore"+numeroDettaglioDaVisualizzare).value="";
}


function rimuoviUltimoDettaglio()
{
	var dettaglioEsami = document.getElementById("dettaglioEsami");
	var numeroDettagliDaVisualizzare = dettaglioEsami.getElementsByTagName("select").length/2;
	
	dettaglioEsami.removeChild(document.getElementById("tr_"+numeroDettagliDaVisualizzare));
	
	if(numeroDettagliDaVisualizzare==2)
	{
		document.getElementById("pulsanteRimuoviUltimoDettaglio").disabled=true;
	}
	
}


function controllaOrganoRipetuto(selectOrgano, selectTipo)
{
	//Da controllare solo se ho selezionato un organo, se non seleziono niente il controllo � inutile
	var i=1;
	if(selectOrgano.value!="" && selectTipo.value!="")
	{	
		while(document.getElementById("organo"+i)!=null)
		{
			if(document.getElementById("organo"+i).id!=selectOrgano.id && document.getElementById("tipo"+i).id!=selectTipo.id && selectOrgano.value==document.getElementById("organo"+i).value && selectTipo.value==document.getElementById("tipo"+i).value )
			{
				selectOrgano.value="";
				alert('Coppia Organo/Tipologia Esame gi� selezionata, selezionare un altro organo');
				return false;
			}
			i++;
		}
	}
}

function controllaTipoRipetuto(selectTipo,selectOrgano)
{
	//Da controllare solo se ho selezionato un tipo, se non seleziono niente il controllo � inutile
	var i=1;
	if(selectTipo.value!="" && selectOrgano.value!="")
	{	
		while(document.getElementById("tipo"+i)!=null)
		{
			if(document.getElementById("organo"+i).id!=selectOrgano.id && document.getElementById("tipo"+i).id!=selectTipo.id && selectOrgano.value==document.getElementById("organo"+i).value && selectTipo.value==document.getElementById("tipo"+i).value )
			{
				selectTipo.value="";
				alert('Coppia Organo/Tipologia Esame gi� selezionata, selezionare un\'altra tipologia esame');
				return false;
			}
			i++;
		}
	}
}


function selezionaEsiti(riga,organoDescription,tipoDescription)
{
	if(document.getElementById('organo'+riga).value=='')
	{
		alert('Selezionare un organo');
	}
	else if(document.getElementById('tipo'+riga).value=='')
	{
		alert('Selezionare un tipo esame');
	}
	else
	{
		var tuttiEsiti = document.getElementById('chooseTuttiEsiti').cloneNode(true);
		var esitiOrgano = document.getElementById('esitiOrganoTipo'+document.getElementById('organo'+riga).value+"---"+document.getElementById('tipo'+riga).value).value;
		esitiOrgano = esitiOrgano.replace("[","");
		esitiOrgano = esitiOrgano.replace("]","");
		esitiOrgano = esitiOrgano.split(", ");
		var esitiRiga   = document.getElementById('esiti'+riga).value;
		esitiRiga = esitiRiga.replace("[","");
		esitiRiga = esitiRiga.replace("]","");
		esitiRiga = esitiRiga.split(",");
		
		tuttiEsiti.id="chooseEsiti"+riga;
		tuttiEsiti.style.display="block";
		var inputs = tuttiEsiti.getElementsByTagName("input");
		
		var checkDaRimuovere   = new Array(0);
		var checkDaSelezionare = new Array(0);
		var esistonoEsiti = false;
		var idEsito = 0;
		
		for(var i=0;i<inputs.length;i++)
		{
			if(inputs[i].type=="checkbox")
			{
				inputs[i].id=inputs[i].id.replace("checkTuttiEsiti","");
				inputs[i].setAttribute("onclick","popolaEsitiSelezionati('"+riga+"','"+inputs[i].value+"');");
				
				if(contains(esitiOrgano,inputs[i].id.split("_")[1]))
				{
					esistonoEsiti = true;
					if(contains(esitiRiga,inputs[i].id.split("_")[1]))
					{
						inputs[i].checked=true;
						checkDaSelezionare[checkDaSelezionare.length] = inputs[i];
					}
				}
				else
				{
					checkDaRimuovere[checkDaRimuovere.length] = inputs[i].id;
				}
				idEsito = inputs[i].id.split("_")[1];
			}
			
			if(inputs[i].type=="text" && inputs[i].id.indexOf("checkTuttiEsitiValore")>=0)
			{
				inputs[i].id=inputs[i].id.replace("checkTuttiEsiti","");
				var valore=getValoreEsito(riga,idEsito);
				inputs[i].setAttribute("value",valore);
				var onchange = inputs[i].getAttribute("onchange");
				onchange = onchange.replace("popolaEsitiSelezionatiValore('1'","popolaEsitiSelezionatiValore('"+riga+"'");
				inputs[i].setAttribute("onchange",onchange);
			}
			if(inputs[i].type=="text" && inputs[i].id=="esitoDaAnagrafare")
			{
				var onchange = inputs[i].getAttribute("onchange");
				onchange = onchange.replace("popolaEsitiDaAnagrafare('1'","popolaEsitiDaAnagrafare('"+riga+"'");
				inputs[i].setAttribute("onchange",onchange);
			}
			else if(inputs[i].type=="text" && inputs[i].id=="esitoDaAnagrafareValore")
			{
				var onchange = inputs[i].getAttribute("onchange");
				onchange = onchange.replace("popolaEsitiDaAnagrafareValore('1'","popolaEsitiDaAnagrafareValore('"+riga+"'");
				inputs[i].setAttribute("onchange",onchange);
			}
		}
		if(!esistonoEsiti)
		{
			var footerElencoEsiti = tuttiEsiti.getElementsByTagName("tr")[tuttiEsiti.getElementsByTagName("tr").length-3].getElementsByTagName("td")[0];
			footerElencoEsiti.innerHTML="Nessun esito disponibile per l'organo ed il tipo selezionato";
		}
		
		var labels = tuttiEsiti.getElementsByTagName("label");
		for(var i=0;i<labels.length;i++)
		{
			labels[i].id=labels[i].id.replace("TuttiEsiti","");
			labels[i].setAttribute("for", labels[i].getAttribute("for").replace("checkTuttiEsiti","")); 
		}
		
		var th = tuttiEsiti.getElementsByTagName("th")[0];
		th.innerHTML="Organo selezionato: " + organoDescription+"; tipologia esame selezionato: " + tipoDescription;
		
		var th = tuttiEsiti.getElementsByTagName("th")[1];
		th.innerHTML="Anagrafe esiti per organo " + organoDescription+" e tipologia esame " + tipoDescription;
		
		document.form.appendChild(tuttiEsiti);
		
		var init = 1;
		if(navigator.userAgent.indexOf("IE")>0)
			init = 0;
		for(var i=0;i<checkDaRimuovere.length;i++)
		{
			tuttiEsiti.childNodes[init].childNodes[init].removeChild(document.getElementById("trTuttiEsiti"+checkDaRimuovere[i]));
		}
		
		for(var i=0;i<tuttiEsiti.childNodes[init].childNodes[init].getElementsByTagName("tr").length;i++)
		{
			var temp = tuttiEsiti.childNodes[init].childNodes[init].getElementsByTagName("tr")[i].setAttribute("class",(i%2==0)?("odd"):("even"));
		}
		
		var esitoDaAnagrafareRiga   = document.getElementById('esitoDaAnagrafare'+riga).value;
		var esitoDaAnagrafareValoreRiga   = document.getElementById('esitoDaAnagrafareValore'+riga).value;
		document.getElementById('esitoDaAnagrafare').value = esitoDaAnagrafareRiga;
		document.getElementById('esitoDaAnagrafareValore').value = esitoDaAnagrafareValoreRiga;
		
		mostraPopupChooseEsiti(riga);
	}
}


function popolaEsitiSelezionati(riga,idEsito)
{
	var esiti = document.getElementById('esitiTemp'+riga).value;
	var separator = ",";
	var esitiArray = esiti.split(",");
	var presente = false;
	var indice;
	for(var i=0;i<esitiArray.length;i++)
	{
		if(esitiArray[i]==idEsito)
		{
			presente = true;
			break;
		}
	}
	if(document.getElementById('esitiTemp'+riga).value=="")
		separator = "";
		
	if(!presente)
	{
		document.getElementById('esitiTemp'+riga).value=document.getElementById('esitiTemp'+riga).value+separator+idEsito;
	}
	else
	{
		esitiArray.splice(i,1);
		document.getElementById('esitiTemp'+riga).value = esitiArray;
	}
}

function popolaEsitiSelezionatiValore(riga,idEsito,valore)
{

	var esiti = document.getElementById('esitiTempValore'+riga).value;
	var separator = "&&&&&";
	var esitiArray = esiti.split("&&&&&");
	var presente = false;
	var indice;
	for(var i=0;i<esitiArray.length;i++)
	{
		if(esitiArray[i].split("###")[0]==idEsito)
		{
			presente = true;
			break;
		}
	}

	if(document.getElementById('esitiTempValore'+riga).value=="")
		separator = "";
	else
		separator="&&&&&";
	
	if(!presente)
	{
		document.getElementById('esitiTempValore'+riga).value = document.getElementById('esitiTempValore'+riga).value+separator+idEsito+"###"+valore;
	}
	else
	{
		esitiArray.splice(i,1);
		document.getElementById('esitiTempValore'+riga).value = esitiArray + separator+idEsito+"###"+valore;
	}
	
}

function popolaEsitiDaAnagrafare(riga,numEsito,valore)
{
	document.getElementById('esitoTempDaAnagrafare'+riga).value=valore;
}

function popolaEsitiDaAnagrafareValore(riga,numEsito,valore)
{
	document.getElementById('esitoTempDaAnagrafareValore'+riga).value=valore;
}


function azzeraEsiti(id)
{
	document.getElementById(id).value="";
	id=id.substring(5,id.length);
	document.getElementById('esitoDaAnagrafare'+id).value="";
	document.getElementById('esitoDaAnagrafareValore'+id).value="";
	document.getElementById('esitiTemp'+id).value="";
}

function contains(a, obj)
{
	for(var i = 0; i < a.length; i++) 
	{
	    if(a[i] === obj)
	    {
	      return true;
	    }
	}
	return false;
}

function confermaModifiche(conferma,riga)
{
	if(conferma)
	{
		document.getElementById('esiti'+riga).value = document.getElementById('esitiTemp'+riga).value;
		document.getElementById('esitiValore'+riga).value = document.getElementById('esitiTempValore'+riga).value;
		document.getElementById('esitoDaAnagrafare'+riga).value = document.getElementById('esitoTempDaAnagrafare'+riga).value;
		document.getElementById('esitoDaAnagrafareValore'+riga).value = document.getElementById('esitoTempDaAnagrafareValore'+riga).value;
	}
	else
	{
		document.getElementById('esitiTemp'+riga).value = document.getElementById('esiti'+riga).value;
		document.getElementById('esitiTempValore'+riga).value = document.getElementById('esitiValore'+riga).value;
		document.getElementById('esitoTempDaAnagrafare'+riga).value = document.getElementById('esitoDaAnagrafare'+riga).value;
		document.getElementById('esitoTempDaAnagrafareValore'+riga).value = document.getElementById('esitoDaAnagrafareValore'+riga).value;
	}
	
	var a = document.getElementsByTagName("body")[0].childNodes;
	a[a.length-1].removeChild(document.getElementById("chooseEsiti"+riga));
}

function testFloating(str) {    
	return /^[+]?[0-9]+([\.,][0-9]+)?$/.test(str);
}



function chooseProvinciaRitrovamento(provincia) {
	if (provincia == 'BN') {
		document.getElementById( 'comuneRitrovamentoBN' ).style.display = "";
		document.getElementById( 'comuneRitrovamentoNA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoSA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoCE' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoAV' ).style.display = "none";
		
	}
	else if (provincia == 'NA') {
		document.getElementById( 'comuneRitrovamentoBN' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoNA' ).style.display = "";
		document.getElementById( 'comuneRitrovamentoSA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoCE' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoAV' ).style.display = "none";
	}	
	else if (provincia == 'AV') {
		document.getElementById( 'comuneRitrovamentoBN' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoNA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoSA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoCE' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoAV' ).style.display = "";
	}
	else if (provincia == 'SA') {
		document.getElementById( 'comuneRitrovamentoBN' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoNA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoSA' ).style.display = "";
		document.getElementById( 'comuneRitrovamentoCE' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoAV' ).style.display = "none";
	}
	else if (provincia == 'CE') {
		document.getElementById( 'comuneRitrovamentoBN' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoNA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoSA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoCE' ).style.display = "";
		document.getElementById( 'comuneRitrovamentoAV' ).style.display = "none";
	}
	else if (provincia == '') {
		document.getElementById( 'comuneRitrovamentoBN' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoNA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoSA' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoCE' ).style.display = "none";
		document.getElementById( 'comuneRitrovamentoAV' ).style.display = "none";
	}
	
}


function confermaModificheOperatori(conferma)
{
	if(conferma)
	{
		document.getElementById('idOp').value = document.getElementById('idOpTemp').value;
		document.getElementById('operatoriSelezionati').innerHTML = document.getElementById('operatoriSelezionatiTemp').innerHTML;
	}
	else
	{
		document.getElementById('idOpTemp').value = document.getElementById('idOp').value;
		document.getElementById('operatoriSelezionatiTemp').innerHTML = document.getElementById('operatoriSelezionati').innerHTML;
	}
}


function popolaOpSelezionati(idOp, descOp)
{
	var op = document.getElementById('idOpTemp').value;
	var opSel = document.getElementById('operatoriSelezionatiTemp').innerHTML;
	var separator = ",";
	var opArray = op.split(",");
	var presente = false;
	var indice;
	for(var i=0;i<opArray.length;i++)
	{
		if(opArray[i]==idOp)
		{
			presente = true;
			break;
		}
	}
	if(document.getElementById('idOpTemp').value=="")
		separator = "";
		
	if(!presente)
	{
		document.getElementById('idOpTemp').value=document.getElementById('idOpTemp').value+separator+idOp;
		document.getElementById('operatoriSelezionatiTemp').innerHTML=document.getElementById('operatoriSelezionatiTemp').innerHTML+"<tr><td>"+descOp.replace("***","'")+"</td></tr>";
	}
	else
	{
		opArray.splice(i,1);
		document.getElementById('idOpTemp').value = opArray;
		opSel = opSel.replace("<tr><td>"+descOp.replace("***","'")+"</td></tr>","");
		document.getElementById('operatoriSelezionatiTemp').innerHTML = opSel;
	}
}

function selezionaOp()
{
			var op = document.getElementById('idOpTemp').value;
			var opArray = op.split(",");
			var opIniziale = document.getElementById('div_operatori').cloneNode(true);
		
			opIniziale.id="chooseOp";
			opIniziale.style.display="block";
			var inputs = opIniziale.getElementsByTagName("input");
			
			var checkDaSelezionare = new Array(0);
			var esistonoOp = false;
			
			for(var i=0;i<inputs.length;i++)
			{
				if(inputs[i].type=="checkbox")
				{
					inputs[i].id=inputs[i].id.replace("checkOpIniziale","");
					inputs[i].setAttribute("onclick","popolaOpSelezionati('"+inputs[i].value+"','"+inputs[i].name.replace("op","").replace("'","***")+"');");
					
					esistonoOp = true;
					//Se � stato selezionato prima
					if(contains(opArray,inputs[i].id.replace("op","")))
					{
						inputs[i].checked=true;
						checkDaSelezionare[checkDaSelezionare.length] = inputs[i];
					}
				}
			}
			
			document.getElementById('div_operatori').appendChild(opIniziale);
			
			mostraPopupChooseOp();
}		




function getValoreEsito(riga,idEsito)
{
	var esitiValoreRiga = document.getElementById("esitiValore"+riga).value;
	var array = esitiValoreRiga.split('&&&&&');
	if(array!=null)
	{
		for(var i=0;i<array.length;i++)
		{
			var temp = array[i];
			var array2 = temp.split('###');
			if(array2[0]==idEsito)
			{
				return array2[1];
			}
		}
	}
	return "";
}

function popolaNumRifMittente(progressivo,aslUtente)
{
	var numRifMittente = "";
	
	var salaSettRifMittente = "";
	if(document.getElementById('idTipoAccettazione1').checked)
		salaSettRifMittente = 'IZ';
	else if(document.getElementById('idTipoAccettazione2').checked)
		salaSettRifMittente = 'UN';
	else if(document.getElementById('idTipoAccettazione3').checked)
	{
		if(aslUtente=='201')
			salaSettRifMittente = 'AV';
		else if(aslUtente=='202')
			salaSettRifMittente = 'BN';
		else if(aslUtente=='203')
			salaSettRifMittente = 'CE';
		else if(aslUtente=='204')
			salaSettRifMittente = 'N1';
		else if(aslUtente=='205')
			salaSettRifMittente = 'N2';
		else if(aslUtente=='206')
			salaSettRifMittente = 'N3';
		else if(aslUtente=='207')
			salaSettRifMittente = 'SA';
	}
	else if(document.getElementById('idTipoAccettazione4').checked)
		salaSettRifMittente = 'CR';
	
	var data = document.getElementById('dataAutopsia').value;
	var anno = data.substring(6, data.length);
	
	
	numRifMittente = progressivo + salaSettRifMittente + anno;
	document.getElementById('numeroAccettazioneSigla').value = numRifMittente;
}



