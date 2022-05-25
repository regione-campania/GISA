<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.molluschibivalvi.base.*"%>
<%@page import="java.util.Date"%><jsp:useBean id="OrgDetails" class="org.aspcfs.modules.molluschibivalvi.base.Organization" scope="request" />
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ZoneProduzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Classificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="RifiutoClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StatiClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />


<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request" />
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/popLookupSelect.js"></SCRIPT>


<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css" />
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script>


function rifiutaSpecchioAcqueoDwr(data,idMotivazione,orgId)
{
	if (data=='' || idMotivazione=='-1')
		{
		alert('Specificare La data e la motivazione');
		}
	else
		PopolaCombo.rifiutaSpecchioAcqueo(data,idMotivazione,orgId,rifiutaSpecchioAcqueoCallback);
	}
	
	function rifiutaSpecchioAcqueoCallback(value)
	{
		if (value==true)
			{
			alert('Specchio Acqueo Rifiutato');
			window.location.reload(true);		
			
			}
	}
	
function aggiornaCun(orgId,cun)
{
	
	PopolaCombo.aggiornaCunMolluschi(cun,orgId,aggiornaCunCallback);
	}
	
	function aggiornaCunCallback(value)
	{
		if (value==true)
			{
			alert('Aggiornamento Avvenuto con successo');
			window.location.reload(true);		}
	}
var campoLat;
var campoLong;
	function showCoordinate(address,city,prov,cap,campo_lat,campo_long)
	{
   campoLat = campo_lat;
   campoLong = campo_long;
   Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
   
   
}
function setGeocodedLatLonCoordinate(value)
{
	campoLat.value = value[1];;
	campoLong.value =value[0];
	
}


function showFormRiattivazione(id)
{
	title  = '_types';
	  width  =  '600';
	  height =  '550';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var selectedIds = '';
	  var selectedDisplays ='';

	  
	  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('MolluschiBivalvi.do?command=ClassificaSpecchioAcqueo&riattivazione=true&orgId='+id, title, params);
	  newwin.focus();
	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
}

function showFormSospensione(id)
{
	title  = '_types';
	  width  =  '600';
	  height =  '550';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var selectedIds = '';
	  var selectedDisplays ='';

	  
	  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('MolluschiBivalvi.do?command=ClassificaSpecchioAcqueo&sospensione=true&orgId='+id, title, params);
	  newwin.focus();
	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
}

function showFormClassificazione(id, tipo, scelta)
{
	title  = '_types';
	  width  =  '600';
	  height =  '550';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var selectedIds = '';
	  var selectedDisplays ='';

	  
	  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('MolluschiBivalvi.do?command=ClassificaSpecchioAcqueo&classificazione=true&tipo='+tipo+'&orgId='+id+'&scelta='+scelta, title, params);
	  newwin.focus();
	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
}

function showFormConcessione(id)
{
	title  = '_types';
	  width  =  '600';
	  height =  '550';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var selectedIds = '';
	  var selectedDisplays ='';

	  
	  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('Concessionari.do?command=ListaConcessionari&orgId='+id, title, params);
	  newwin.focus();
	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
}

function checkTagliaNonCommerciale(check){
	var valPrecedente = !check.checked;
	if (confirm('Attenzione! Sicuro di voler salvare il dato modificato per TAGLIA NON COMMERCIALE?'))
		{
		aggiornaFlagTaglia('<%=OrgDetails.getOrgId()%>', check.checked);
		}
	else
		check.checked = valPrecedente;
}

function openaddConcessionario(idZona,idConcessionario)
{
	title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var selectedIds = '';
	  var selectedDisplays ='';

	  
	  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('MolluschiBivalvi.do?command=AddConcessione&orgId='+idZona+'&idConcessionario='+idConcessionario, title, params);
	  newwin.focus();
	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
}

// function opendeleteConcessionario(idZona,idConcessionario)
// {
// 	title  = '_types';
// 	  width  =  '500';
// 	  height =  '450';
// 	  resize =  'yes';
// 	  bars   =  'no';
	  
// 	  var posx = (screen.width - width)/2;
// 	  var posy = (screen.height - height)/2;
	  
// 	  var selectedIds = '';
// 	  var selectedDisplays ='';

	  
// 	  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
// 	  var newwin=window.open('MolluschiBivalvi.do?command=EliminaConcessionario&orgId='+idZona+'&id='+idConcessionario, title, params);
// 	  newwin.focus();
// 	  if (newwin != null) {
// 	    if (newwin.opener == null)
// 	      newwin.opener = self;
// 	  }
// }


function sospendiConcessione(idZona,idConcessionario)
{
	title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var selectedIds = '';
	  var selectedDisplays ='';

	  
	  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('MolluschiBivalvi.do?command=SospendiConcessione&orgId='+idZona+'&idConcessionario='+idConcessionario, title, params);
	  newwin.focus();
	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
}

function openDettaglioConcessionario(id,idZona)
{
	
	title  = 'Dettaglio Concessionario';
	  width  =  '800';
	  height =  '650';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var selectedIds = '';
	  var selectedDisplays ='';

	  
	  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open('Concessionari.do?command=Details&IdZona='+idZona+'&orgId='+id+'&popUp=true', title, params);
	  newwin.focus();
	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	
}


$(function () {
	 $( "#insertCun" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"ASSEGNA CODICE UNIVOCO NAZIONALE",
	        width:450,
	        height:200,
	       	draggable: false,
	        modal: true,
	        position: 'top',
	        open:function(){$("#searchAziendaFieldinput").val("");},
	        buttons:{
	        	 "Salva": function() {aggiornaCun(document.getElementById('cun').value,document.getElementById('orgId').value);} ,
	        	 "Esci" : function() { $(this).dialog("close");
	        	 $("html,body").animate({scrollTop: 0}, 500, function(){});
	        	 }
	        	
	        },
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



$(function () {
	 $( "#rifiutaClassificazione" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"RIFIUTA CLASSIFICAZIONE",
	        width:450,
	        height:200,
	        draggable: false,
	        modal: true,
	        open:function(){$("#searchAziendaFieldinput").val("");},
	        buttons:{
	        	 "Salva": function() {rifiutaSpecchioAcqueoDwr(document.getElementById('dataRifiutoClassificazione').value,document.getElementById('motivazioneRifiuto').value,document.getElementById('orgId').value);} ,
	        	 "Esci" : function() { $(this).dialog("close");
	        	 $("html,body").animate({scrollTop: 0}, 500, function(){});
	        	 }
	        	
	        },
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

function aggiornaFlagTaglia(orgId, val)
{	
	DwrMolluschi.updateFlag(orgId, val,{callback:aggiornaFlagTagliaCallBack,async:false});
	
}
function aggiornaFlagTagliaCallBack(val)
{
	
	}

function showFormLocalita(){
	var div = document.getElementById("modificaLocalita");
	if (div.style.display=='none')
		div.style.display='block';
	else
		div.style.display='none';
	
}
function checkFormModificaLocalita(form){
	var nome = document.getElementById("nomeLocalita").value;
	if (nome.trim()=='' || nome.trim()=="<%=OrgDetails.getName()%>"){
		alert('Impossibile salvare il cambio di località. Località non inserita o uguale alla precedente.');
		return false;
	}
	else{
		loadModalWindow();
		form.action='MolluschiBivalvi.do?command=UpdateLocalita';
		form.submit();	
	}
}


</script>

<script type="text/javascript" src="dwr/interface/DwrMolluschi.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>


<%@ include file="../initPage.jsp"%>

<% String param1 = "orgId=" + OrgDetails.getId();
%>


<%@ include file="../../controlliufficiali/diffida_list.jsp" %>
<%
String container =  "molluschibivalvi";
if( OrgDetails.getProvvedimentiRestrittivi()==13)
{
	container = "molluschibivalvisenzacu";
}%>

<dhv:container name="<%=container %>" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>

<form id="addAccount" name="addAccount"
	action="MolluschiBivalvi.do?command=Modify&orgId=<%=OrgDetails.getId() %>"
	method="post">

<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="MolluschiBivalvi.do">Molluschi
		Bivalvi</a> >
		<a href="MolluschiBivalvi.do?command=Search">Risultati ricerca </a>		
		 > Dettaglio </td>
	</tr>
</table>

	
	<%
if ((OrgDetails.getTipoMolluschi()==Organization.TIPOLOGIA_SPECCHIO_ACQUEO ||  (OrgDetails.getTipoMolluschi()==1 && OrgDetails.getClasse()<=0 )) && OrgDetails.getProvvedimentiRestrittivi()!=13 && ("".equals(OrgDetails.getCun()) ||OrgDetails.getCun()==null) )
{
%>
<%-- <dhv:permission name="molluschi-molluschi-edit"> --%>
<!-- <input type="button" value="Inserisci CUN" onclick="javascript:$( '#insertCun' ).dialog('open');"/> -->
<%-- </dhv:permission> --%>
<%}

%>

	
	<script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
<dhv:permission name="">
	<table width="100%" border="0">
		<tr>
			<%-- aggiunto da d.dauria--%>

			<td nowrap align="right">
					
					
 		  <%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda Molluschi" value="Stampa Scheda Molluschi"		onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '-1', '-1', '-1', 'molluschi', 'SchedaMolluschi');"--%>
 
				<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda" value="Stampa Scheda"		onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '-1', '-1', '-1', '7');">
				
			</td>


			<%-- fine degli inserimenti --%>
		</tr>
	</table>
</dhv:permission>

<br />
<br />

<center><table border="1px"><tr><td>&nbsp;&nbsp;&nbsp;
 <input type="checkbox" disabled id="tagliaNonComm" name="tagliaNonComm" onClick="checkTagliaNonCommerciale(this)" <%= (OrgDetails.isTagliaNonCommerciale() ? "checked=\"checked\"" : "") %>/> <b>Taglia non commerciale</b>
 &nbsp;&nbsp;&nbsp;</td></tr></table></center>
 
 <dhv:permission name="molluschibivalvi-taglianoncommerciale-edit">
 <script>
 document.getElementById("tagliaNonComm").disabled="";
 </script>
 </dhv:permission>
<br />
<br />

<dhv:permission name="note_hd-view">
<jsp:include page="../note_hd/link_note_hd.jsp">
<jsp:param name="riferimentoId" value="<%=OrgDetails.getOrgId() %>" />
<jsp:param name="riferimentoIdNomeTab" value="organization" />
</jsp:include> <br><br>
</dhv:permission>

<jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">
    <jsp:param name="riferimentoIdPreaccettazione" value="<%=OrgDetails.getId() %>" />
    <jsp:param name="riferimentoIdNomePreaccettazione" value="orgId" />
    <jsp:param name="riferimentoIdNomeTabPreaccettazione" value="organization" />
    <jsp:param name="userIdPreaccettazione" value="<%=User.getUserId() %>" />
</jsp:include>
<br/><br/>

    <%SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); %>

<table cellpadding="4" cellspacing="0" border="0" width="100%"class="details" id = "zc"> 
	<tr>
		<th colspan="2"><strong><dhv:label name="">Gestione Classificazione</dhv:label></strong>
	
		<dhv:permission name="molluschi-classificazione-view">
		 <input type="button" value="Gestione prima classificazione/Riclassificazione" onclick="showFormClassificazione(<%=OrgDetails.getId() %>, 'newriclassifica')" <%if (OrgDetails.getStatoClassificazione()==Organization.STATO_CLASSIFICAZIONE_REVOCATO){ %> style="background:grey !important" disabled<%} %> />
 		</dhv:permission>
 	
		<% if (OrgDetails.getStatoClassificazione()==3){ %>
		 <dhv:permission name="molluschi-sospensione-view">
		 <input type="button"  value="ATTIVA" onclick="showFormClassificazione(<%=OrgDetails.getId() %>, 'newrevocasospensione', 'attiva')" />
 		 </dhv:permission>
		<%} %>
		
		<% if (OrgDetails.getStatoClassificazione()==0 || OrgDetails.getStatoClassificazione()==1 || OrgDetails.getStatoClassificazione()==2){ %>
		 <dhv:permission name="molluschi-sospensione-view">
		 <input type="button"  value="SOSPENDI" onclick="showFormClassificazione(<%=OrgDetails.getId() %>, 'newrevocasospensione', 'sospendi')" />
 		 </dhv:permission>
		<%} %>
		
		<% if (OrgDetails.getStatoClassificazione()==0 || OrgDetails.getStatoClassificazione()==1 || OrgDetails.getStatoClassificazione()==3 || OrgDetails.getStatoClassificazione()==2){ %>
		 <dhv:permission name="molluschi-sospensione-view">
		 <input type="button"  value="REVOCA" onclick="showFormClassificazione(<%=OrgDetails.getId() %>, 'newrevocasospensione', 'revoca')" />
 		 </dhv:permission>
		<%} %>
		
		
		
		</th>
	
		</tr>
		
		<tr>
		<td nowrap class="formLabel" > Stato classificazione</td>
		<td><%= (OrgDetails.getStatoClassificazione()>-1) ? StatiClassificazione.getSelectedValue(OrgDetails.getStatoClassificazione()) : "N.D." %></td>
	</tr>
	

	<tr id = "zc1">
		<td nowrap class="formLabel" > Numero Decreto</td>
		<td><%=toHtml2(OrgDetails.getNumClassificazione()) %></td>
	</tr>
	
	<tr id = "zc2">
		<td nowrap class="formLabel" > Data Classificazione</td>
		<td>
		<%if(OrgDetails.getDataClassificazione()!=null){
			%>
			<%=sdf.format(new Date(OrgDetails.getDataClassificazione().getTime())) %>
			<%
		}
		%>
				
		</td>
	</tr>
	
		<tr id = "zc3">
		<td nowrap class="formLabel" > Data Fine Classificazione</td>
		<td>
		
		<%if(OrgDetails.getDataFineClassificazione()!=null) {
			%>
			<%=sdf.format(new Date(OrgDetails.getDataFineClassificazione().getTime())) %>
			<%
		}
		%>
			
		</td>
	</tr>
	
	
<% if (OrgDetails.getStatoClassificazione()==Organization.STATO_CLASSIFICAZIONE_REVOCATO){ %>
	<tr style="background:yellow">
		<td nowrap class="formLabel" > Data Revoca</td>
		<td><%=toDateasString(OrgDetails.getDataRevoca()) %></td>
	</tr>
	<%} %>
	<% if (OrgDetails.getStatoClassificazione()==Organization.STATO_CLASSIFICAZIONE_SOSPESO){ %>
	<tr style="background:yellow">
		<td nowrap class="formLabel" > Data Sospensione</td>
		<td><%=toDateasString(OrgDetails.getDataSospensione()) %></td>
	</tr>
	<%} %>

<% if (OrgDetails.getStatoClassificazione()==Organization.STATO_CLASSIFICAZIONE_REVOCATO || OrgDetails.getStatoClassificazione()==Organization.STATO_CLASSIFICAZIONE_SOSPESO){ %>
	<tr style="background:yellow">
		<td nowrap class="formLabel" > Numero decreto (sospensione/revoca) </td>
		<td><%=toHtml(OrgDetails.getNumeroDecretoSospensioneRevoca()) %></td>
	</tr>
	<tr style="background:yellow">
		<td nowrap class="formLabel" > Data provvedimento (sospensione/revoca)</td>
		<td><%=toDateasString(OrgDetails.getDataProvvedimentoSospensioneRevoca()) %></td>
	</tr>
	<%} %>	
	
	<tr>
		<td nowrap class="formLabel" > Zona di produzione</td>
		<td>
		<%=ZoneProduzione.getSelectedValue(OrgDetails.getTipoMolluschi()) %> 
	
		</td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel"> Codice Univoco Nazionale (CUN)</td>
		<td><%=toHtml2(OrgDetails.getCun()) %>
		
		</td>
	</tr>
	
		<%if(OrgDetails.getClasse()>0){ %>
		<tr>
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Classificazione</td>
		<td><%=Classificazione.getSelectedValue(OrgDetails.getClasse()) %></td>
	</tr>
	<%} %>
	
	
	
	<tr>
		<td nowrap="nowrap" class="formLabel">ASL</td>
		<td><%=SiteIdList.getSelectedValue(OrgDetails.getSiteId()) %></td>
	</tr>

	
	
	<%
	if(OrgDetails.getTipoMolluschiInZone().size()>0)
	{
	%>
	<tr>
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Specie Molluschi</td>
		<td>
		<table class= "noborder">
		<%
		Iterator<Integer> itKey = OrgDetails.getTipoMolluschiInZone().keySet().iterator();
		while (itKey.hasNext())
		{
		int key = itKey.next(); 
		String cammino = OrgDetails.getTipoMolluschiInZone().get(key);
		%>
		<tr><td><%=cammino %></td></tr>
		<%
		}
		%>
		
		</table>
		</td>
	</tr>
	<%} %>
	<%if(OrgDetails.getCategoriaRischio()>0) 
	{%>
	<tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Categoria di Rischio</dhv:label>
      </td>
      <td>
         <%= OrgDetails.getCategoriaRischio()%>
      </td>
    </tr>
    <%}%>
    
    <%
    
    if(OrgDetails.getProssimoControllo()!=null)
    {
    %>
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Prossimo Controllo</dhv:label>
      </td>
      <td>
  
         <%= sdf.format(OrgDetails.getProssimoControllo())%>
      </td>
    </tr>
 <%} %>

	
	
	
		
	
	<%
OrganizationAddress oa = new OrganizationAddress();
if(OrgDetails.getAddressList().size()>0)
	oa = (OrganizationAddress) OrgDetails.getAddressList().get(0);
%>

<tr>
		<td nowrap="nowrap" class="formLabel" name="province" id="province">Comune</td>
		<td><%=oa.getCity()%></td>
	</tr>
	<tr>
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Localita'</td>
		<td><%=toHtml2(OrgDetails.getName()) %>
		
<dhv:permission name="molluschi-localita-view">
<input type="button"  value="Modifica Località" onclick="showFormLocalita()" <%if (OrgDetails.getStatoClassificazione()==Organization.STATO_CLASSIFICAZIONE_REVOCATO){ %> style="background:grey !important" disabled<%} %>/>
<div id ="modificaLocalita" style="display:none">
<input type ="text" name ="nomeLocalita" id ="nomeLocalita" value="<%=OrgDetails.getName()%>"/>
<input type="button"  value="Conferma" onclick="checkFormModificaLocalita(this.form)"/>
</div>
</dhv:permission>

		</td>
	</tr>



<%if(oa.getListaCoordinatePoligono().size()>0)
	{
	%>
<tr>
	<td nowrap="nowrap" class="formLabel">Coordinate Poligono</td>
	<td>
	
	<input type = "hidden" name = "elementi" id = "elementi" value = "1">
	<input type = "hidden" name = "size" id = "size" value = "1">
	<table style="border:1px" cellpadding="4">
	<%
	int indPol=1;
	for(CoordinateMolluschiBivalvi coordinate : oa.getListaCoordinatePoligono())
	{
	%>
	<tr id = "riga" <%if (coordinate.getLatitude()<1 && coordinate.getLongitude()<1){%> style="display:none"<%}%>>
	<td><b><%=coordinate.getIdentificativo() %></b></td>
	<td>LAT</td><td><%=coordinate.getLatitude() %></td>
	<td>LON</td><td><%=coordinate.getLongitude() %></td></tr>
	<%
	} %>
	</table> 
	</td>
</tr>
	
	<%} %>
	
	<%if(oa.getListaCoordinatePuntiDiPrelievo().size()>0)
	{
	%>
<tr>
	<td nowrap="nowrap" class="formLabel">Coordinate Punti di Prelievo</td>
	<td>
	
	<input type = "hidden" name = "elementi" id = "elementi" value = "1">
	<input type = "hidden" name = "size" id = "size" value = "1">
	<table style="border:1px" cellpadding="4">
	<%
	int indPre=1;
	for(CoordinateMolluschiBivalvi coordinate : oa.getListaCoordinatePuntiDiPrelievo())
	{
	%>
	<tr id = "riga" <%if (coordinate.getLatitude()<1 && coordinate.getLongitude()<1){%> style="display:none"<%}%>>
	<td><b><%=coordinate.getIdentificativo() %></b></td>
	<td>LAT</td><td><%=coordinate.getLatitude() %></td>
	<td>LON</td><td><%=coordinate.getLongitude() %></td></tr>
	<%
		} %>
	</table> 
	</td>
</tr>
	
	<%} %>

<tr>
		<td nowrap="nowrap" class="formLabel">Linea attivita'</td>
		<td><%=OrgDetails.getTipoMolluschi()==1 ? "BANCHI NATURALI->BANCHI NATURALI->BANCHI NATURALI" : "ZONE DI PRODUZIONE->ZONE DI PRODUZIONE->ZONE DI PRODUZIONE" %> 
	
		</td>
	</tr></td>
	</tr>
		
</table>

<br/><br/>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr><th colspan="2"><strong>Gestione sanitaria</strong>
	
	 <dhv:permission name="molluschi-sanitaria-view">
<%if (OrgDetails.getTipoMotiviInZone().size()==0) {%>
<input type="button" value="Gestisci Provvedimenti" onclick="showFormSospensione(<%=OrgDetails.getId() %>)" <%if (OrgDetails.getStatoClassificazione()==Organization.STATO_CLASSIFICAZIONE_REVOCATO){ %> style="background:grey !important" disabled<%} %> />
<%} else { %>
<input type="button" value="Riattiva" onclick="showFormRiattivazione(<%=OrgDetails.getId() %>)" <%if (OrgDetails.getStatoClassificazione()==Organization.STATO_CLASSIFICAZIONE_REVOCATO){ %> style="background:grey !important" disabled<%} %>>
<% } %>
</dhv:permission>
	
	</th>
	
	<tr>
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Provvedimento Restrittivo</td>
		<td>
	
	<%if(OrgDetails.getTipoMotiviInZone().size()>0){ 	
		HashMap<Integer,String> motivi =  OrgDetails.getTipoMotiviInZone();
  		Iterator<Integer> itMot = motivi.keySet().iterator();
  		while (itMot.hasNext())
  		{
  			int idMot = itMot.next();
  			String path = motivi.get(idMot);
  			%>
  			<%=path %><br/>
  			<%
  		}
  		%>
	<%} else {%> 
	
	<% if (OrgDetails.getTipoMolluschi()== 4 || OrgDetails.getTipoMolluschi()== 5 ){ %>
	N.D.
 <%} else {%>
	Favorevole <%
 } } %>
	</td>
	</tr>
	
<%if(OrgDetails.getProvvedimento()!=null && !"".equals(OrgDetails.getProvvedimento()))
		{%>
	<tr id = "zc5">
		<td nowrap class="formLabel" name="orgname1" id="orgname1"> Provvedimento restrittivo in atto </td>
		<td><%=toHtml2(OrgDetails.getProvvedimento()) %></td>
	</tr>
	<%} %>


<%if(OrgDetails.getTipoMotiviInZone().size()>0){ 	%>
	<tr id = "zc4">
		<td nowrap class="formLabel" > Data Provvedimento</td>
		<td>
		<%if(OrgDetails.getDataProvvedimento()!=null) {
			%>
			<%=sdf.format(new Date(OrgDetails.getDataProvvedimento().getTime())) %>
			<%
		}
		%>
			
		</td>
	</tr>
	<%} %>
	
		
	<%
	if(OrgDetails.getProvvedimentiRestrittivi()==13)
	{
	%>
	<tr>
		<td nowrap class="formLabel" >Data Rifiuto</td>
		<td>
		<%=toDateasString(OrgDetails.getDataRifiuto()) %> </td>
	</tr>
	
	<tr>
		<td nowrap class="formLabel" >Motivazione Rifiuto</td>
		<td>
		<%=RifiutoClassificazione.getSelectedValue(OrgDetails.getIdMotivazioneRifiuto()) %> </td>
	</tr>
	<%} %>
	
<!-- 	<tr> -->
<!-- 		<td nowrap class="formLabel" > Stato</td> -->
<!-- 		<td> -->
<%-- 		<%=OrgDetails.getStato() %> </td> --%>
<!-- 	</tr> -->
	
	
</table>


























<br>
<br>
<dhv:evaluate if="<%=(OrgDetails.getTipoMolluschi()==2)%>">
		<%{
			%>
			<div id="tab4" class="tab">
		
			<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	  			<tr>
	    			<th colspan="2"><strong><dhv:label name="">Concessionari insistenti sullo specchio acqueo</dhv:label></strong>
	    			
<dhv:permission name="molluschibivalvi-concessionario-add">
<input type="button" value="Gestione concessione" onclick="showFormConcessione(<%=OrgDetails.getId() %>)" <%if (OrgDetails.getStatoClassificazione()==Organization.STATO_CLASSIFICAZIONE_REVOCATO){ %> style="background:grey !important" disabled<%} %> />
</dhv:permission>
	    			
	    			</th>
	    			<th colspan="6">
	    			
	    			</th>
	  			</tr>
	  			
	  			<tr class="formLabel">
	  			
	  				<td width ="20%" align="left">Num Concessione</td>
					<td width ="10%" align="left">Data Concessione</td>
					<td width ="10%" align="left">Data Scadenza</td>
					<td width ="20%" align="left">Denominazione</td>
					<td width ="10%" align="left">Legale Rappresentante</td>
					<td width ="10%" align="left">Stato</td>
					<td width ="10%" align="left">&nbsp;</td>			
				</tr>



				<%
					if(OrgDetails.getListaConcessionari().size()>0)
					{
						Iterator<Concessione> itConcessionari = OrgDetails.getListaConcessionari().iterator();
						while(itConcessionari.hasNext())
						{
							Concessione thisConcessione = itConcessionari.next(); 
							Concessionario thisConcessionario = thisConcessione.getConcessionario();
							OrganizationAddress oaConcessionario = new OrganizationAddress();
							if(thisConcessionario.getAddressList().size()>0)
								oaConcessionario = (OrganizationAddress) thisConcessionario.getAddressList().get(0);
	  					%>    
	  						<tr>
	  						
	  						<td>
				      				<%=toHtmlValue( thisConcessione.getNumConcessione()  )%>&nbsp;
				      			</td>
				      			<td>
				      				<%=toHtmlValue( thisConcessione.getDataConcessioneasString()  )%>&nbsp;
				      			</td>
				      			<td>
				      				<%=toHtmlValue( thisConcessione.getDataScadenzaasString()  )%>&nbsp;
				      			</td>
				      			<td>
				      				<%=toHtmlValue( thisConcessionario.getName()  )%>&nbsp;
				      			</td>

				      			<td>
				      				<%=toHtml( thisConcessionario.getTitolareNome())%>&nbsp;
				      			</td>
							
				      			<%
				      			Timestamp current = new Timestamp(System.currentTimeMillis());
			      				Timestamp currenttremesi = new Timestamp(System.currentTimeMillis());
			      				currenttremesi.setMonth(currenttremesi.getMonth()+3) ; 
			      				
			      				
				      			%>
				      			  <td><%=( thisConcessione.getDataScadenza().before(current) ) ? "SCADUTA" : (thisConcessione.isEnabled()==false && thisConcessione.getDataSospensione()==null)? "IN CONCESSIONE" : (thisConcessione.getDataSospensione()!=null) ? ""+Classificazione.getSelectedValue(thisConcessione.getIdSospensione()) : "IN CONCESSIONE"  %></td>
				      			
				      		
				      			<td>
				      				<a href="#" onclick="javascript : openDettaglioConcessionario(<%=thisConcessionario.getId() %>,<%=OrgDetails.getId() %>)">Dettagli</a>
				      				<dhv:permission name="molluschibivalvi-concessionario-delete">
				      				<br><br>
				      				<%if (OrgDetails.getStatoClassificazione()!=Organization.STATO_CLASSIFICAZIONE_REVOCATO){ %>
				      					<a href="MolluschiBivalvi.do?command=EliminaConcessionario&orgId=<%=OrgDetails.getId()%>&id=<%=thisConcessionario.getId() %>">Elimina</a>
				      				   		<%} %>
				      				</dhv:permission>
				      				
				      				<%
				      				
				      				if (thisConcessione.getDataScadenza() != null || thisConcessione.getDataSospensione()!=null  )
				      				{
				      					if (thisConcessione.getDataSospensione()!=null || ( thisConcessione.getDataScadenza().before(current) || thisConcessione.getDataScadenza().before(currenttremesi)))
				      					{
				      				%>
				      				<dhv:permission name="molluschibivalvi-concessionario-add">
				      				<br><br>
				      				<%if (OrgDetails.getStatoClassificazione()!=Organization.STATO_CLASSIFICAZIONE_REVOCATO){ %>
				      					<a onclick="javascript : openaddConcessionario(<%=OrgDetails.getId()%>,<%=thisConcessionario.getId() %>)" 
				      				   		href="#">Rinnova Concessione</a>
				      				   		<%} %>
				      				</dhv:permission>
				      				<%}} %>
				      				
				      			</td>
				      			
				      		</tr>
  		      			<%		  		
	  				}
					}
					else{
						
						%>
						<tr><td colspan="8">Nessun concessionario associato</td></tr>
						<%
					}
	  			
	  			%>
	  			
	  			
	  		</table>
	  		</div>
	  		<%} %>
  		</dhv:evaluate>
 <input type ="hidden" name = "orgId" id = "orgId" value = "<%=OrgDetails.getOrgId()%>">
 
 	<div id="rifiutaClassificazione">
	
	<table>
	<tr>
	<td>Data</td>
	<td>
	
		<input readonly type="text" id="dataRifiutoClassificazione" name="dataRifiutoClassificazione" size="10" />
		<a href="#" onClick="cal19.select(document.getElementById('dataRifiutoClassificazione'),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>
	
	</td>
	</tr>
	
	<tr>
	<td>Motivazione</td>
	<td><%=RifiutoClassificazione.getHtmlSelect("motivazioneRifiuto", "-1") %></td>
	</tr>
	
	
	</table>

		</div>
  		




</form>


</dhv:container>
