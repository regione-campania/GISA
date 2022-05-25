<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stampa_verbale_prelievo.base.Organization" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DestinatarioCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />
<%
String orgId 	= (String)request.getAttribute("orgId");
%>

<script>

function openPopupModulesForm(form){
	window.open('PrintModulesHTML.do?command=ViewModulesFromPrenotaCampione','popupSelect','height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	form.target = 'popupSelect';
	document.printmodules.submit();
}

function check(){
	var rad_val = "";
	for (var i=0; i < document.printmodules.tipo.length; i++)
	{
	   if (document.printmodules.tipo[i].checked)
	      {
	      		rad_val = document.printmodules.tipo[i].value;
	      }
	}
	
	if(rad_val == "")	
	{
		alert("Controlla di aver selezionato un modulo.");
		return false;
	}

	if(rad_val == 1)	
	{
		alert("Il modulo 1 sarà generato solo per il cavaliere Molluschi Bivalvi.");
	
	}
	else
	{
//		openPopupModulesForm(printmodules); res.sendRedirect( res.encodeRedirectURL( url ) );
	    var c = controllaModelloAnalita();
	    if (c == -1){
	    	var scelta = confirm("Il Tipo di Analisi non corrisponde al modello selezionato\nVuoi continuare?");
	    	if (scelta == true){
	    		document.printmodules.tipoAction.value='prenota';
				document.printmodules.submit();
		    }
		}
	    else {
			document.printmodules.tipoAction.value='prenota';
			document.printmodules.submit();
		}
		//openPopupModules(rad_val);
		//window.close();
	}
}

function controllaModelloAnalita(){
	var status=-1;
	var modello=document.getElementById("tipo");
	var size = document.getElementById("size").value;
	var analita="";
	var index = 1;
	var tipo;

	if (modello.checked == true){
		tipo=2;
	}
	else {
		tipo=3;
	}
	
	for (index=1; index<=size; index++){
		analita=document.getElementById("pathAnaliti_"+index).value;
		if (tipo == 2){
			if (analita.indexOf("BATTERIOLOGICO") !== -1){
				status=1;
				break;
			}
		}
		if (tipo == 3){
			if (analita.indexOf("CHIMICO") !== -1){
				status=1;
				break;
			}		
		}
	}
	return status;
}
</script>

<style type="text/css">
#dhtmltooltip{
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
}
#dhtmlpointer{
	position:absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
</style>

<script type="text/javascript">
	var offsetfromcursorX=12 
	var offsetfromcursorY=10 
	var offsetdivfrompointerX=10 
	var offsetdivfrompointerY=14 
	document.write('<div id="dhtmltooltip"></div>') //write out tooltip DIV
	document.write('<img id="dhtmlpointer" src="images/arrow2.gif">') //write out pointer image
	var ie=document.all
	var ns6=document.getElementById && !document.all
	var enabletip=false
	if (ie||ns6)
		var tipobj=document.all? document.all["dhtmltooltip"] : document.getElementById? document.getElementById("dhtmltooltip") : ""
	var pointerobj=document.all? document.all["dhtmlpointer"] : document.getElementById? document.getElementById("dhtmlpointer") : ""

	function ietruebody(){
		return (document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body
	}

	function ddrivetip(thetext, thewidth, thecolor){
		if (ns6||ie){
			if (typeof thewidth!="undefined") tipobj.style.width=thewidth+"px"
			if (typeof thecolor!="undefined" && thecolor!="") tipobj.style.backgroundColor=thecolor
			tipobj.innerHTML=thetext
			enabletip=true
			return false
		}
	}

	function positiontip(e){
		if (enabletip){
			var nondefaultpos=false
			var curX=(ns6)?e.pageX : event.clientX+ietruebody().scrollLeft;
			var curY=(ns6)?e.pageY : event.clientY+ietruebody().scrollTop;
			
			var winwidth=ie&&!window.opera? ietruebody().clientWidth : window.innerWidth-20
			var winheight=ie&&!window.opera? ietruebody().clientHeight : window.innerHeight-20
			var rightedge=ie&&!window.opera? winwidth-event.clientX-offsetfromcursorX : winwidth-e.clientX-offsetfromcursorX
			var bottomedge=ie&&!window.opera? winheight-event.clientY-offsetfromcursorY : winheight-e.clientY-offsetfromcursorY
			var leftedge=(offsetfromcursorX<0)? offsetfromcursorX*(-1) : -1000
	
			if (rightedge<tipobj.offsetWidth){
				tipobj.style.left=curX-tipobj.offsetWidth+"px"
				nondefaultpos=true
			}
			else if (curX<leftedge)
					tipobj.style.left="5px"
				else{
					tipobj.style.left=curX+offsetfromcursorX-offsetdivfrompointerX+"px"
					pointerobj.style.left=curX+offsetfromcursorX+"px"
				}
	
			if (bottomedge<tipobj.offsetHeight){
				tipobj.style.top=curY-tipobj.offsetHeight-offsetfromcursorY+"px"
				nondefaultpos=true
			}
			else{
				tipobj.style.top=curY+offsetfromcursorY+offsetdivfrompointerY+"px"
				pointerobj.style.top=curY+offsetfromcursorY+"px"
			}
			tipobj.style.visibility="visible"
			if (!nondefaultpos)
				pointerobj.style.visibility="visible"
			else
				pointerobj.style.visibility="hidden"
		}
	}
	
	function hideddrivetip(){
		if (ns6||ie){
			enabletip=false
			tipobj.style.visibility="hidden"
			pointerobj.style.visibility="hidden"
			tipobj.style.left="-1000px"
			tipobj.style.backgroundColor=''
			tipobj.style.width=''
		}
	}

	function inCostruzione(){
		alert('In Costruzione!');
		return false;
	}
	document.onmousemove=positiontip
</script>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OsaSearch.do"><dhv:label name=" ">Operatori</dhv:label></a> > 
<a href="OsaSearch.do?command=Search"><dhv:label name="">Ricerca Operatori</dhv:label></a> >
<dhv:label name="">Prenotazione Campione</dhv:label>
</td>
</tr>
</table>
<dhv:permission name="campioni-prenotati-view">
<div align="left" 
	onmouseover="javascript:ddrivetip('Visualizza l\'elenco dei campioni prenotati per l\'operatore selezionato',150,'lightyellow');" 
	onmouseout="javascript:hideddrivetip();">
	<a class="ovalbutton" 
		href = "Campioni.do?command=ViewElencoPrenotazioni&orgId=<%=orgId%>"><span>Visualizza Campioni Prenotati</span></a>
</div><br/>
</dhv:permission>
<br>
<br>
<form method="post"  name = "printmodules" action="PrintModulesHTML.do?command=ViewModulesFromPrenotaCampione&orgId=<%=orgId%>">
<input type = "hidden" name = "orgId" value = "<%=orgId %>"> 
<table cellpadding="4" cellspacing="0" width="70%" class="details" 
onmouseover="javascript:ddrivetip('Tipo di verbale associato al campione (OBBLIGATORIO)',150,'lightyellow');" 
	onmouseout="javascript:hideddrivetip();">
<tr>
	<th colspan="2"><strong>Selezionare il modulo di interesse</strong></th>
</tr>
<tr class="containerBody">
 	<td nowrap class="formLabel"></td>
	<td>
        <input type="radio" id="tipo" name="tipo" value="1">Mod 1 Verbale campione molluschi in produzione primaria<br> 
		<input type="radio" id="tipo" name="tipo" value="2">Mod 2 Verbale campione battereologico<br>
		<input type="radio" id="tipo" name="tipo" value="3">Mod 3 Verbale campione chimico<br>
		<!-- <input type="radio" id="tipo" name="tipo" value="19">Mod 19 Verbale di prelievo PNAA<br> -->
		<%-- <input type="radio" name="tipo" value="6">Mod 6 Verbale prelievo campioni superficie ambientale<br>
		<input type="radio" name="tipo" value="10">Mod 10 Verbale prelievo campioni superficie di carcasse<br>
		--%>
	</td>
</tr>
</table>
<br><br/>

<div id="campione">

<table cellpadding="4" cellspacing="0" width="100%" class="details"
	onmouseover="javascript:ddrivetip('Informazioni relative al campione da prenotare (FACOLTATIVE)',150,'lightyellow');" 
	onmouseout="javascript:hideddrivetip();">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazione Campione</dhv:label></strong>
    </th>
	</tr>
  
     <%@ include file="/campioni/campioni_add.jsp" %>
  
   </table> <!--  chiusura tabella generale -->
	<input type="hidden" id="tipoAction" name="tipoAction" value="" />
	
</div>

<br>
	<div align="left">
<!-- 		<a class="ovalbutton" href = "#" onclick = "javascript:check();" ><span>Stampa Verbale</span></a>  -->
<!--		<a class="ovalbutton" href = "#" onclick="javascript:document.printmodules.tipoAction.value='prenota';document.printmodules.submit();"><span>Prenota Campione </span></a> -->
			<a class="ovalbutton" href = "#" onclick="javascript:check();"
			onmouseover="javascript:ddrivetip('Prenota il campione per l\'operatore selezionato con le informazioni specificate',150,'lightyellow');" 
			onmouseout="javascript:hideddrivetip();"><span>Prenota Campione</span></a>
	</div>
</form>