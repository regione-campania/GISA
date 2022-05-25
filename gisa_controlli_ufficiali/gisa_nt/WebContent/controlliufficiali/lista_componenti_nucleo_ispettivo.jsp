<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativi"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="ListaToReturn" class="java.util.ArrayList" scope="request" />

<%@ include file="../initPage.jsp" %>


<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>



<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script src="javascript/jquerypluginTableSorter/widget-scroller.js"></script>
<script type="text/javascript" src="javascript/jquerypluginTableSorter/tableJqueryFilter.js"></script>

<script>


RegExp.escape = function(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
}
String.prototype.replaceAll = function(search, replace) {
    return this.replace(new RegExp(RegExp.escape(search),'g'), replace);
};

function selezionaComponenti(indiceComponente)
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
	while (window.opener.document.getElementById("risorse_"+selected)!=null)
	{
		if (parseInt(window.opener.document.getElementById("risorse_"+selected).value)>0)
			{
			userSelezionati[indiceArray]=window.opener.document.getElementById("risorse_"+selected).value;
// 			alert(selected+'-'+window.opener.document.getElementById("risorse_"+selected).value);
			componenteSettati ++ ;
			indiceArray ++ ;
			}
		else
			
			{
			if (window.opener.document.getElementById("Utente_"+selected).value!='')
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
		
		
		if (continua==true)
			{
			
			
		var indiceRiferimento = indiceComponente;
		var nominativo=arrayItemSelezionati[0].replaceAll('_','"');
		var json = JSON.parse(nominativo);
		
		var element1 = window.opener.document.getElementById("componenteid_"+indiceComponente);
		element1.value=json.cognome+" "+json.nome;
		element1.style.display="block";
		
		
		
		window.opener.document.getElementById("risorse_"+indiceComponente).value=json.id;
		
		
		window.opener.document.getElementById("operazione_"+indiceComponente).innerHTML="<a href='javascript:eliminaComponente("+indiceComponente+");' >Elimina</a>"

		var element = window.opener.document.getElementById("link_"+indiceComponente);
		element.style.display="none";
		
		indiceComponente ++ ;
		window.opener.clona(window.opener.document);
		
		indiceComponente=window.opener.document.getElementById("elementi").value;
		
		window.opener.document.getElementById("oldValueCombo"+indiceComponente).value=window.opener.document.getElementById("nucleo_ispettivo_"+indiceComponente).value;

		if(arrayItemSelezionati.length>1 )
		{
		
			window.opener.document.getElementById("nucleo_ispettivo_"+indiceComponente).options[window.opener.document.getElementById("nucleo_ispettivo_"+(indiceRiferimento)).options.selectedIndex].setAttribute("selected", "selected");
			
			var element = window.opener.document.getElementById("link_"+indiceComponente);
		

		element.style.display="none";
		}
		
		
		for (i=1;i<arrayItemSelezionati.length;i++)
			{
			
			
			 nominativo=arrayItemSelezionati[i].replaceAll('_','"');
			 json = JSON.parse(nominativo);
			
			var element1 = window.opener.document.getElementById("componenteid_"+indiceComponente);
			element1.value=json.cognome+" "+json.nome;
			element1.style.display="block";
			
			
			window.opener.document.getElementById("risorse_"+indiceComponente).value=json.id;
			window.opener.document.getElementById("operazione_"+indiceComponente).innerHTML="<a  href='javascript:eliminaComponente("+indiceComponente+");'>Elimina</a>"

			var element = window.opener.document.getElementById("link_"+indiceComponente);
			element.style.display="none";
			
			indiceComponente ++ ;
			
			window.opener.clona(window.opener.document);
			if(arrayItemSelezionati.length>1 && i<arrayItemSelezionati.length-1)
			{
			
				window.opener.document.getElementById("nucleo_ispettivo_"+indiceComponente).options[window.opener.document.getElementById("nucleo_ispettivo_"+(indiceComponente-1)).options.selectedIndex].setAttribute("selected", "selected");
			var element = window.opener.document.getElementById("link_"+indiceComponente);

			element.style.display="none";
			
			window.opener.document.getElementById("oldValueCombo"+indiceComponente).value=window.opener.document.getElementById("nucleo_ispettivo_"+indiceComponente).value;
			}
			
			
			
			
			}
		indiceComponente--;
		
		
		window.close();
		
		}
		else
			{
			alert(msg);
			}
		}
	
	
	}
	
// 	function selezionaComponenti(indiceComponente,jsonnominativo)
// {
	
		
// 			var nominativo=jsonnominativo.replaceAll('_','"');
// 			var json = JSON.parse(nominativo);
			
// 			var element1 = window.opener.document.getElementById("componenteid_"+indiceComponente);
// 			element1.value=json.cognome+" "+json.nome;
// 			element1.style.display="block";
			
			
// 			window.opener.document.getElementById("risorse_"+indiceComponente).value=json.id;
			
// 			var element = window.opener.document.getElementById("link_"+indiceComponente);
// 			element.style.display="none";
			
// 			window.opener.document.getElementById("operazioni_"+indiceComponente).innerHTML="[ CAMBIA | ELIMINA ]";
			
// 			indiceComponente ++ ;
// 			window.opener.clona(window.opener.document);
			
			
// 			var element = window.opener.document.getElementById("link_"+indiceComponente);
// 			element.style.display="none";
			
			
			
			
			
		
		
// 	indiceComponente--;
	
	
// 	window.close();
	
// 	}
	
	
	
</script>

<table  class="tablesorter">
	<thead>
		<tr class="tablesorter-headerRow" role="row">
			<!-- you can also add a placeholder using script; $('.tablesorter th:eq(0)').data('placeholder', 'hello') -->

			<th aria-label="Ruolo: No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="0" data-placeholder="FILTRO PER STRUTTURA DI APPARTENENZA" class="first-name filter-select"><div class="tablesorter-header-inner">STRUTTURA DI APPARTENENZA</div></th>
			
			<th aria-label="Nominativo ( filter-match ): No sort applied, activate to apply an ascending sort" aria-sort="none" style="-moz-user-select: none;" unselectable="on" aria-controls="table" aria-disabled="false" role="columnheader" scope="col" tabindex="0" data-column="1" data-placeholder="FILTRO PER NOMINATIVO" class="filter-match tablesorter-header tablesorter-headerUnSorted"><div class="tablesorter-header-inner">NOMINATIVO</th>
			<th  role="columnheader" scope="col" tabindex="0" class="filter-false tablesorter-header" data-column="3" ><div class="tablesorter-header-inner">&nbsp;</div></th>
			
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
	<%
	ArrayList<DpatStrumentoCalcoloNominativi> listaUtenti = (ArrayList<DpatStrumentoCalcoloNominativi>) ListaToReturn;
	for (DpatStrumentoCalcoloNominativi utente :listaUtenti)
	{
		
		String[]  strutture = null ;
		if (utente.getNominativo()!=null && utente.getNominativo().getStrutturaAppartenenza() != null)
			strutture = utente.getNominativo().getStrutturaAppartenenza().split("->");
		if (utente.getId()>0)
		{
	%>
		<tr style="display: table-row;" class="odd" role="row">
<%-- 			<td><%=toHtml2(utente.getNominativo().getSiteIdName())%></td> --%>
<%-- 			<td><%=toHtml2(utente.getNominativo().getRole())%></td> --%>
			<td><%= (strutture!=null) ? strutture[1] + ( (strutture!=null && strutture.length >2) ? "---> (STRUTT.SEMPLICE) "+strutture[2] : "") : "" %></td>
			
			
			<td><%=toHtml2(utente.getNominativo().getContact().getNameLast()+ " "+utente.getNominativo().getContact().getNameFirst())%></td>
			
			
			<td>
			
			<input type="checkbox" name="utente" value="<%=utente.getNominativo().toString2()%>" ></td>
			</tr>
		
		<%}
		else
		{
			%>
			<tr style="display: table-row;" class="odd" role="row">
			<td colspan="4">
			Attenzione non esiste nessun utente associato nel Dpat
			</tr>
			
			<%
		}
		
		}%>
						
	</tbody>
</table>
<br>
<button onclick="selezionaComponenti(<%=request.getAttribute("IndiceComponente")%>)">Fatto</button>