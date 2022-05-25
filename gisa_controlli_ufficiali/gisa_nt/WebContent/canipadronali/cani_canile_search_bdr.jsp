<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp"%>

<%@page import="org.aspcfs.modules.canipadronali.base.CaneList"%>
<%@page import="org.aspcfs.modules.canipadronali.base.Cane"%>
<%@page import="org.aspcfs.modules.canipadronali.base.Proprietario"%><jsp:useBean id="ListaCani" class="org.aspcfs.modules.canipadronali.base.CaneList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>


<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>

<script>


function test(a,b)
{
	alert(a+b);
	}


function setParentValueProprietario(nominativoProp,nomeProp,cognomeProp,aslProp,cfProprietario,luogoNascitaProp,dataNascitaProp,docProprietario,aslRiferimentoasString,tipo_proprietario,citta,provincia,via,cap)
{
	while (citta.indexOf('$')>-1) {
		citta=citta.replace('$','\'');
	}
	/*Informazioni Proprietario*/

window.close();

}
function controllaFitri(form)
{
if (form.mc_cane_canile.value=='' && form.cf.value=='')
{
 alert("- Attenzione selezionare un microchip");
 return false;
}
return true ;	
}

var mcList = new Array();
var razzaList = new Array();
var tagliaList = new Array();
var mantelloList = new Array();
var sessoList = new Array();
var datanascitaList = new Array();
var datadecessoList = new Array();

var caniSelezionati = new Array();

function setCaneList(check,nominativoProp,nomeProp,cognomeProp,aslProp,cfProprietario,luogoNascitaProp,dataNascitaProp,docProprietario,
		microchip,razza,sesso,dataNascita,aslRiferimentoasString,taglia,mantello,tipo_proprietario,citta,provincia,via,cap,index,dataDecesso)
{
	
	if (check.checked)
	{
		caniSelezionati[index]='si';
		mcList[index]=microchip;
		razzaList[index]=razza;
		tagliaList[index]=taglia;
		mantelloList[index]=mantello;
		sessoList[index]=sesso;
		datanascitaList[index]=dataNascita;
		datadecessoList[index]=dataDecesso;
	}
	else
	{
		caniSelezionati[index]='no';
		mcList[index]='';
		razzaList[index]='';
		tagliaList[index]='';
		mantelloList[index]='';
		sessoList[index]='';
		datanascitaList[index]='';
		datadecessoList[index]='';
	}
}

function addCane(mc,razza,taglia,mantello,sesso,data_nascita,indiceCane,dataDecesso){
  
  	var elementi;
  	var elementoClone;
  	var tableClonata;
  	var tabella;
  	var selezionato;
  	var x;
  	elementi = opener.document.getElementById('size_p');
  
  	size = opener.document.getElementById('size_p');
  	size.value=parseInt(size.value)+1;
  	
  	var indice = parseInt(elementi.value) - 1;

  	
  	var clonanbsp = opener.document.getElementById('cane_1');
  	/*clona riga vuota*/
  	clone=clonanbsp.cloneNode(true);

	clone.getElementsByTagName('TD')[0].innerHTML 	= '<b>'+indiceCane+'</b>' ;
	 	
	clone.getElementsByTagName('INPUT')[0].name 	= "mc_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[0].id 		= "mc_"+elementi.value;
	clone.getElementsByTagName('INPUT')[0].value 	= mc ;
	
  	
	var ii = elementi.value;
  	clone.getElementsByTagName('INPUT')[1].name = "razza_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[1].id = "razza_"+elementi.value;
	clone.getElementsByTagName('INPUT')[1].value 	= razza ;

  	
  	clone.getElementsByTagName('INPUT')[2].name = "taglia_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[2].id = "taglia_"+elementi.value;
	clone.getElementsByTagName('INPUT')[2].value 	= taglia ;

	
  	clone.getElementsByTagName('INPUT')[3].name = "mantello_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[3].id = "mantello_"+elementi.value;
	clone.getElementsByTagName('INPUT')[3].value 	= mantello ;

	
  	clone.getElementsByTagName('INPUT')[4].name = "sesso_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[4].id = "sesso_"+elementi.value;
	clone.getElementsByTagName('INPUT')[4].value 	= sesso ;

  	clone.getElementsByTagName('INPUT')[5].name = "data_nascita_cane_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[5].id = "data_nascita_cane_"+elementi.value;
	clone.getElementsByTagName('INPUT')[5].value = 	data_nascita ;
	
	clone.getElementsByTagName('INPUT')[6].name = "data_decesso_"+elementi.value;
  	clone.getElementsByTagName('INPUT')[6].id = "data_decesso_"+elementi.value;
  	if (dataDecesso=='null')
  		dataDecesso = '';
	clone.getElementsByTagName('INPUT')[6].value = 	dataDecesso ;

  	//clone.getElementsByTagName('A')[0].href = "javascript:popCalendar('addticket','data_nascita_cane_"+elementi.value+"','it','IT','Europe/Berlin')";
  	//clone.getElementsByTagName('A')[1].href = "javascript:rimuoviCane("+elementi.value+")";
	
	
  	/*Aggancio il nodo*/
  	clonanbsp.parentNode.appendChild(clone);
	clone.id = "cane_" + size.value;
	
	//opener.document.getElementById("check_"+ii).onclick= function(){  removeRequired(j,opener.document.getElementById("check_"+ii));}

  	
 
  	
  }

function stampaAll(nominativoProp,nomeProp,cognomeProp,aslProp,cfProprietario,luogoNascitaProp,dataNascitaProp,docProprietario,aslRiferimentoasString,tipo_proprietario,citta,provincia,via,cap)
{
	
	size = opener.document.getElementById("size_p").value ;
	
	for (i=size;i>=1;i--)
	{
		if (opener.document.getElementById('cane_'+i)!=null && i!=1)
		{
	  var clonato = opener.document.getElementById('cane_'+i);
  	  clonato.parentNode.removeChild(clonato);
  	  sizeC = opener.document.getElementById('size_p');
  	  sizeC.value=parseInt(sizeC.value)-1;
		}
	}


	indiceCane = 1 ;
if(caniSelezionati.length==0)
{
alert('Selezionare almeno un animale');
return;
}
	
	for (i=0;i<caniSelezionati.length;i++)
		if(caniSelezionati[i]!='no' && caniSelezionati[i]!=null)
		{
			mc = mcList[i];
			razza = razzaList[i];
			taglia = tagliaList[i];
			mantello = mantelloList[i];
			sesso = sessoList[i];
			data_nascita = datanascitaList[i];
			dataDecesso = datadecessoList[i];
			
			if(indiceCane==1)
			{
				var element = null;
				if(window.opener.document.addticket!=null)
					element = window.opener.document.addticket;
				else
					element = window.opener.document.details;
				element.taglia_1.value = taglia;
				element.mantello_1.value = mantello ;
				element.mc_1.value = mc ;
				element.razza_1.value = razza ;	
				element.sesso_1.value = sesso ;
				element.data_nascita_cane_1.value  = data_nascita ;
				if (dataDecesso!='null')
					element.data_decesso_1.value  = dataDecesso ;
				else
					element.data_decesso_1.value  = '' ;
				
			}
			else
			{
			
			addCane(mc,razza,taglia,mantello,sesso,data_nascita,indiceCane,dataDecesso);
			
			}
			indiceCane++;
		}
	setParentValueProprietario(nominativoProp,nomeProp,cognomeProp,aslProp,cfProprietario,luogoNascitaProp,dataNascitaProp,docProprietario,aslRiferimentoasString,tipo_proprietario,citta,provincia,via,cap)
	
	window.close();
}
function selezionaTutti(checkAll)
{
	
	if (checkAll.checked)
	{	
		for (indice = 1 ; indice <numero_cani_trovati ; indice ++)
		{
			
			document.getElementById("check_cane_"+indice).checked=true ;
			document.getElementById("check_cane_"+indice).onclick();
			
		}
	}
	else
	{
		for (indice = 1 ; indice <=numero_cani_trovati ; indice ++)
		{
			document.getElementById("check_cane_"+indice).checked=false ;
			document.getElementById("check_cane_"+indice).onclick();
		}
	}
	
	
}

</script>


<form name="searchAccount" action="CaniPadronali.do?command=Search"
	method="post" onsubmit="return controllaFitri(this)" >
	
	<%
	UserBean user = (UserBean)session.getAttribute("User");
	%>


<br>


<font color="red">Qualora l'animale soggetto a controllo non &egrave; presente nella lista occorre prima anagrafarlo in BDU</font


<table class="trails" cellspacing="0">
	<tr>
		<td>Ricerca Animale in BDR</td>
	</tr>
</table>

<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
		<td width="50%" valign="top">

		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2">Filtri Ricerca Animale in BDR</th>
			</tr>
			<tr>

				<td class="formLabel">Microchip</td>
				<td><input type="text" size="30" maxlength="15" name="mc" value="<%=(request.getAttribute("mc_cane_canile")!=null) ? request.getAttribute("mc_cane_canile") : "" %>"></td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
		
		<input type="hidden" size="30" maxlength="15" name="cani_canile" value="true">
		<input type="hidden" size="30" maxlength="15" name="id_canina" value="<%=(String)request.getAttribute("id_canina")%>">
		<input type="hidden" size="30" maxlength="15" name="id_gisa" value="<%=(String)request.getAttribute("id_gisa")%>">
		<input type = "submit" value = "Ricerca"  >
		
</form>

<br><br>

<%
String asl= "" ;
Proprietario pp = new Proprietario();
if (ListaCani!=null)
{
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	%>
<table cellpadding="2" cellspacing="2" border="0" width="100%" class="details">
<tr><th colspan="6">Lista Animali</th></tr>
<tr><th colspan="5">Informazioni Animali</th>
<th>Seleziona</th></tr>

<%
int index=0;
int i =1 ;
	boolean trovatoCeduto = false;
	if(ListaCani.size()>0)
	{
		%>
	
<tr>
	<th width="20%">Microchip</th>
	<th width="20%">Razza</th>
	<th width="20%">Sesso</th>
	<th width="20%">Nominativo Proprietario</th>
	<th width="10%">Stato</th>
	<th width="10%"><input type = "checkbox" onclick="selezionaTutti(this)"></th>
	
	</tr>
		<%
		
		Iterator itCani2 = ListaCani.iterator();
		
		while(itCani2.hasNext())
		{
				
			String data_cane = "" ;	
			Cane cane = (Cane)itCani2.next();
			
			if(cane.getIdStato()!=51 && cane.getIdStato()!=27 && cane.getIdStato()!=5 && cane.getIdStato()!=22 && cane.getIdStato()!=29/*id stati ceduti*/)
			{
			Proprietario p = cane.getProprietario();
			if (cane.getDataNascita() !=null)
				data_cane = sdf.format(cane.getDataNascita());
			%>
					
		<tr>
		
 		<td width="20%"><%=cane.getMc() %></td> 
 		<td width="20%"><%= toHtml2(cane.getRazza()).toUpperCase() %></td> 
 		<td width="20%"><%=toHtml2(cane.getSesso()).toUpperCase() %></td>
 		<td width="20%"><%=(p!=null) ? (toHtml2(p.getNome()) + " "+toHtml2(p.getCognome())).toUpperCase() : "" %></td> 
 		<td width="10%"><%=toHtml2(cane.getStato()).toUpperCase() %></td> 
 		<td width="10%"><input type = "checkbox"  id="check_cane_<%=i %>" name="check_cane_<%=i %>" onclick="setCaneList(this,'<%=  toHtml2(p.getNome()) + " "+ toHtml2(p.getCognome())%>','<%=toHtml2(p.getNome()) %>','<%=toHtml2(p.getCognome()) %>','<%=p.getIdAsl() %>','<%=toHtml2(p.getCodiceFiscale()) %>','<%=toHtml2(p.getLuogoNascita()) %>','<%=(p.getDataNascitaAsString()!=null)?toHtml2(p.getDataNascitaAsString()):"" %>','<%=toHtml2(p.getDocumentoIdentita()) %>','<%= toHtml2(cane.getMc())%>','<%= toHtml2(cane.getRazza())%>','<%=toHtml2(cane.getSesso()) %>','<%=data_cane %>','<%=toString(cane.getAslRiferimentoStringa()) %>','<%=toHtml2(cane.getTaglia()) %>','<%=toHtml2(cane.getMantello()) %>','<%=toHtml2(cane.getProprietario().getTipoProprietarioDetentore())%>','<%=toHtml2(cane.getProprietario().getLista_indirizzi().get(0).getCitta()) %>','<%=toHtml2(cane.getProprietario().getLista_indirizzi().get(0).getProvincia()) %>','<%=toHtml2(cane.getProprietario().getLista_indirizzi().get(0).getVia()) %>','<%=(cane.getProprietario().getLista_indirizzi().get(0).getCap()) %>',<%=index %>,'<%=cane.getDataDecesso()%>')"> </td> 
 		<td> 
		</tr>	
			<%
			i++ ;
			index++ ;	
			}
			else
			{
				trovatoCeduto = true;
			}
		}
		if(request.getAttribute("Search")==null && trovatoCeduto)
		{
%>
			<tr>
				<td colspan="6">Attenzione, l'animale ricercato si trova in stato ceduto</td>
			</tr>
<%
		}
	}
	else
	{
		if (request.getAttribute("Search")==null)
		{
%>
			<tr>
				<td colspan="6">Nessun Animale Trovato</td>
			</tr>
<%
		}
		
	}
	%>
	</table>
<br>

<script>
numero_cani_trovati = <%=i %>
</script>		
		
		<%
		
		
		
	
}
if (request.getAttribute("Search")==null && ListaCani.size()>0)
	
{%>

<input type = "button" value = "Seleziona animali" onclick="stampaAll('<%= toHtml2(pp.getNome()==null?"":pp.getNome().replaceAll("'","")) + " "+toHtml2( pp.getCognome())%>','<%=toHtml2(pp.getNome()) %>','<%=toHtml2(pp.getCognome()) %>','<%=pp.getIdAsl() %>','<%=toHtml2(pp.getCodiceFiscale()) %>','<%=toHtml2(pp.getLuogoNascita()) %>','<%=toHtml2(pp.getDataNascitaAsString()) %>','<%=toHtml2(pp.getDocumentoIdentita()) %>','<%=toString(asl) %>','<%=toHtml2(pp.getTipoProprietarioDetentore())%>','<%=pp.getLista_indirizzi().isEmpty()?"":pp.getLista_indirizzi().get(0).getCitta().replace("'", "$") %>','<%=toHtml2(pp.getLista_indirizzi().isEmpty()?"":pp.getLista_indirizzi().get(0).getProvincia()) %>','<%=toHtml2(pp.getLista_indirizzi().isEmpty()?"":pp.getLista_indirizzi().get(0).getVia()) %>','<%=toHtml(pp.getLista_indirizzi().isEmpty()?"":pp.getLista_indirizzi().get(0).getCap()) %>')">

<br><br>
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>
<%} %>



	
<%--	<input type = "button" value = "Importa Solo Proprietario" onclick="setParentValueProprietario('<%= toHtml2(p.getNome()) + " "+toHtml2( p.getCognome())%>','<%=toHtml2(p.getNome()) %>','<%=toHtml2(p.getCognome()) %>','<%=p.getIdAsl() %>','<%=toHtml2(p.getCodiceFiscale()) %>','<%=toHtml2(p.getLuogoNascita()) %>','<%=toHtml2(p.getDataNascitaAsString()) %>','<%=toHtml2(p.getDocumentoIdentita()) %>','<%=toString(cane.getAslRiferimentoStringa()) %>','<%=toHtml2(cane.getProprietario().getTipoProprietarioDetentore())%>','<%=toHtml2(cane.getProprietario().getLista_indirizzi().get(0).getCitta()) %>','<%=toHtml2(cane.getProprietario().getLista_indirizzi().get(0).getProvincia()) %>','<%=toHtml2(cane.getProprietario().getLista_indirizzi().get(0).getVia()) %>','<%=(cane.getProprietario().getLista_indirizzi().get(0).getCap()) %>')"></td>--%>	
	