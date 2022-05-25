<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativi"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="ListaToReturn" class="java.util.ArrayList" scope="request" />




<script>
function anagrafaComponente(){
	var indice = document.getElementById("IndiceComponente").value;
	var idQualifica = document.getElementById("nucleo_ispettivo_"+indice).value;
	var nomeQualifica = document.getElementById("nucleo_ispettivo_"+indice).options[document.getElementById("nucleo_ispettivo_"+indice).selectedIndex].text;

	  window.open('NucleoIspettivo.do?command=AnagrafaComponenteNucleo&idQualifica='+idQualifica+'&nomeQualifica='+nomeQualifica+'&indice='+indice,'popupSelect',
      'height=300px,width=400px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}




$(function () {
    
	 
	
	
	 $( "#dialogListaNucleoIspettivo" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"COMPONENTI NUCLEO ISPETTIVO",
	       	buttons :{
	       		"Seleziona":function(){selezionaComponenti();},
	       		"Esci Senza Selezionare":function(){ $('#dialogListaNucleoIspettivo' ).dialog('close');}
	       		
	       	},
	        width:850,
	        height:500,
	        draggable: true,

	        modal: true,
	       
	        show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	       
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	 
 
});        

RegExp.escape = function(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
}
String.prototype.replaceAll = function(search, replace) {
    return this.replace(new RegExp(RegExp.escape(search),'g'), replace);
};

function selezionaComponenti()
{
	var numComponenti = 0 ;
	var itemSelezionati = document.getElementsByName("utente");
	var arrayItemSelezionati = new Array();
	indice = 0 ;
	var userSelezionati = new Array();
	var selected = 1;
	var componenteSettati = 0;
	
	var nodpat = 0 ;
	var indiceArray =0 ;
	while (document.getElementById("risorse_"+selected)!=null)
	{
		if (parseInt(document.getElementById("risorse_"+selected).value)>0)
			{
			userSelezionati[indiceArray]=document.getElementById("risorse_"+selected).value;
// 			alert(selected+'-'+document.getElementById("risorse_"+selected).value);
			componenteSettati ++ ;
			indiceArray ++ ;
			}
		else
			
			{
			if (document.getElementById("Utente_"+selected).value!='')
				{
				nodpat ++ ;
				}
			}
		selected++;
		
	}
	
	
	for (i=0;i<itemSelezionati.length;i++)
		{
			if (itemSelezionati[i].checked)
				{
				numComponenti++ ;
				arrayItemSelezionati[indice]=itemSelezionati[i].value ;
				indice++;
				}
		}
	
	
	indiceComponente =document.getElementById("IndiceComponente").value;
	if ( ( indiceComponente<componenteSettati+nodpat && (numComponenti+(componenteSettati)+nodpat)>10 ) || (indiceComponente==(componenteSettati+1+nodpat) && (numComponenti+componenteSettati+nodpat)>10 ))
		{
		alert("numero massimo 10");
		}
	else
		{
		
		var msg = "" ;
		var continua =true ;
		/*CONTROOLLO SE IL COMPONENTE CHE E STATO SELEZIONATO è GIA PRESENTE O SELEZIONATO PIU VOLTE PER DIVERSE UO*/
		var nominativo;
		var json ;
		for (i=0;i<arrayItemSelezionati.length;i++)
		{
			 nominativo=arrayItemSelezionati[i].replaceAll('_','"');
			 json = JSON.parse(nominativo);
			 
			for (j=0;j<userSelezionati.length;j++)
			{
			if (userSelezionati[j]==json.id)
				{
				msg="IL COMPONENTE "+json.cognome+" "+json.nome+" E' STATO GIA' SELEZIONATO"
				continua=false;
				break;
				
				}
			}
		}
		if (continua==true)
			{
		for (j=0;j<arrayItemSelezionati.length;j++)
		{
			nominativo=arrayItemSelezionati[j].replaceAll('_','"');
			 json = JSON.parse(nominativo);
			 
			for (k=0;k<arrayItemSelezionati.length;k++)
			{
				nominativo2=arrayItemSelezionati[k].replaceAll('_','"');
				 json2 = JSON.parse(nominativo2);
		if (json.id  == json2.id && j!=k)
			{
			msg="IL COMPONENTE "+json.cognome+" "+json.nome+" E' STATO SELEZIONATO PER PIU DI UNA STRUTTURA. SCEGLIERNE UNA";
			continua=false;
			break;
			}
			}
		}
			}
		
		
		if (continua==true && arrayItemSelezionati!=null && arrayItemSelezionati.length>0 )
			{
			
			
		var indiceRiferimento = indiceComponente;
		var nominativo=arrayItemSelezionati[0].replaceAll('_','"');
		var json = JSON.parse(nominativo);
		
		var element1 = document.getElementById("componenteid_"+indiceComponente);
		element1.value=json.cognome+" "+json.nome;
		element1.style.display="block";
		
		
		
		document.getElementById("risorse_"+indiceComponente).value=json.id;
		
		
		document.getElementById("operazione_"+indiceComponente).innerHTML="<a href='javascript:eliminaComponente("+indiceComponente+");' >Elimina</a>"

		var element = document.getElementById("link_"+indiceComponente);
		element.style.display="none";
		
		indiceComponente ++ ;
		clona(document);
		
		indiceComponente=document.getElementById("elementi").value;
		
		document.getElementById("oldValueCombo"+indiceComponente).value=document.getElementById("nucleo_ispettivo_"+indiceComponente).value;

		if(arrayItemSelezionati.length>1 )
		{
		
			document.getElementById("nucleo_ispettivo_"+indiceComponente).options[document.getElementById("nucleo_ispettivo_"+(indiceRiferimento)).options.selectedIndex].setAttribute("selected", "selected");
			
			var element = document.getElementById("link_"+indiceComponente);
		

		element.style.display="none";
		}
		
		
		for (i=1;i<arrayItemSelezionati.length;i++)
			{
			
			if(arrayItemSelezionati[i]!=null)
				{
			 nominativo=arrayItemSelezionati[i].replaceAll('_','"');
			 json = JSON.parse(nominativo);
			
			var element1 = document.getElementById("componenteid_"+indiceComponente);
			element1.value=json.cognome+" "+json.nome;
			element1.style.display="block";
			
			
			document.getElementById("risorse_"+indiceComponente).value=json.id;
			document.getElementById("operazione_"+indiceComponente).innerHTML="<a  href='javascript:eliminaComponente("+indiceComponente+");'>Elimina</a>"

			var element = document.getElementById("link_"+indiceComponente);
			element.style.display="none";
			
			indiceComponente ++ ;
			
			clona(document);
			if(arrayItemSelezionati.length>1 && i<arrayItemSelezionati.length-1)
			{
			
				document.getElementById("nucleo_ispettivo_"+indiceComponente).options[document.getElementById("nucleo_ispettivo_"+(indiceComponente-1)).options.selectedIndex].setAttribute("selected", "selected");
			var element = document.getElementById("link_"+indiceComponente);

			element.style.display="none";
			
			document.getElementById("oldValueCombo"+indiceComponente).value=document.getElementById("nucleo_ispettivo_"+indiceComponente).value;
			}
			
				}
			
			
			}
		indiceComponente--;
		
		 $('#dialogListaNucleoIspettivo' ).dialog('close');
		
		}
		else
			{
			if (msg=='')
				msg = 'Nessuna Risorsa Selezionata';
			alert(msg);
			}
		}
	
	 
	}
	
	
	
	
</script>
<div id="dialogListaNucleoIspettivo">
<input type = "hidden" name = "IndiceComponente" id = "IndiceComponente" value = "-1">


<font color="red">Attenzione! Di seguito sono riportati tutti i nominativi delle persone afferenti alle strutture presenti nello strumento di calcolo per cui è stato eseguito il "Salva e Chiudi".<br>
Qualora non fossero presenti i nominativi desiderati, controllare che figurino correttamente nello strumento di calcolo e che quest'ultimo sia stato Salvato/Chiuso.
</font>
<br>

<center><input id="bottoneAggiungi" disabled="disabled" style="display:none" type="button" value="ANAGRAFA NUOVO COMPONENTE NUCLEO" onClick="anagrafaComponente()"/></center>

<table  class="tablesorter" id ="tablelistanucleo" >
	<thead>
		<tr class="tablesorter-headerRow" role="row">
			<!-- you can also add a placeholder using script; $('.tablesorter th:eq(0)').data('placeholder', 'hello') -->

			<th aria-label="Ruolo: No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER STRUTTURA DI APPARTENENZA" class="first-name filter-select"><div class="tablesorter-header-inner">STRUTTURA DI APPARTENENZA</div></th>
			
			<th aria-label="Nominativo ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER NOMINATIVO" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">NOMINATIVO</th>
			<th  role="columnheader" scope="col" tabindex="0" class="filter-false tablesorter-header" data-column="3" ><div class="tablesorter-header-inner">&nbsp;</div></th>
			
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite" id ="nucleoajax">
			
	</tbody>
</table>
<br>

</div>