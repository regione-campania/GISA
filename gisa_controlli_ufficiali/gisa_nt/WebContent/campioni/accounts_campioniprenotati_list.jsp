<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="../initPage.jsp"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>

<% ArrayList<String> data = (ArrayList<String>)request.getAttribute("data"); %>
<% ArrayList<String> idCU = (ArrayList<String>)request.getAttribute("idCU"); %>
<% ArrayList<String> elencoCU = (ArrayList<String>)request.getAttribute("elencoCU"); %>
<% String orgId = (String)request.getParameter("orgId"); %>

<script>
function check(){
	var cont=0;
    var tot=document.campioni_prenotati.NElem.value;
    var cb = 0;
    var i = 0;
    var selCU;
    var flagCU = 0;
    
	for (i=0;i<tot;i++){
		cb=document.getElementById("ckbox"+i).checked;
		if (cb!=false) {
			selCU = document.getElementById("CU"+i);
			if (selCU.options[selCU.selectedIndex].value == 'N.D.') { flagCU=1; } 
			break; 
		}
		else { cont=cont+1;}
	}

	if (cont==tot){ 
		alert("Nessun Campione Selezionato");
		flagCU=0;
	}
	else{ 
		if (flagCU==1){
			alert("Attenzione!! Specificare un Controllo Ufficiale per ogni campione selezionato.");
			flagCU=0;
		}
		else { document.campioni_prenotati.submit(); }
	}	
}

function setLink(){
	var op = '<%=request.getParameter("op")%>';
	var a1 = document.getElementById('action1');
	var a2 = document.getElementById('action2');
	var a3 = document.getElementById('action3');
	var gt1 = document.getElementById("gt1"); var gt2 = document.getElementById("gt2"); var gt3 = document.getElementById("gt3");
	switch (op) {
	  case "accounts":
		  	a1.href = "Accounts.do";
			a2.href = "Accounts.do?command=Search"; 
			a3.href = "Accounts.do?command=Details&orgId=<%=request.getParameter("orgId")%>";	
			a1.innerHTML = "Imprese";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Impresa";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	  break;

	  case "molluschibivalvi":
	  		a1.href = "MolluschiBivalvi.do";
	  		a2.href = "MolluschiBivalvi.do?command=Search";
	  		a3.href = "MolluschiBivalvi.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
	  		a1.innerHTML = "Molluschi Bivalvi";
	  		a2.innerHTML = "Ricerca Molluschi Bivalvi";
	  		a3.innerHTML="Dettaglio Molluschi Bivalvi";
	  		gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	  break;

	  case "stabilimenti":
			a1.href = "Stabilimenti.do";
			a2.href = "Stabilimenti.do?command=Search";
			a3.href = "Stabilimenti.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Stabilimenti";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Stabilimento";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	  break;

	  case "allevamenti":
			a1.href = "Allevamenti.do";
			a2.href = "Allevamenti.do?command=Search";
			a3.href = "Allevamenti.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Allevamenti";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Allevamento";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	  break;

	  case "punti_di_sbarco":
			a1.href = "PuntiSbarco.do?command=SearchForm";
			a2.href = "PuntiSbarco.do?command=Search";
			a3.href = "PuntiSbarco.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Punti Di Sbarco";
			a2.innerHTML = "Ricerca Punto di Sbarco";
			a3.innerHTML = "Dettaglio Punto di sbarco";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	  break;

	  case "aziendeagricole":
			a1.href = "AziendeAgricole.do?command=SearchForm";
			a2.href = "AziendeAgricole.do?command=Search";
			a3.href = "AziendeAgricole.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Aziende Agricole";
			a2.innerHTML = "Ricerca Aziende Agricole";
			a3.innerHTML = "Dettaglio Aziende Agricole";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	  break;

	  case "riproduzioneanimale":
			a1.href = "RiproduzioneAnimale.do";
			a2.href = "RiproduzioneAnimale.do?command=Search";
			a3.href = "RiproduzioneAnimale.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Strutture di Riproduzione Animale";
			a2.innerHTML = "Ricerca Strutture di Riproduzione Animale";
			a3.innerHTML = "Dettaglio Strutture di Riproduzione Animale";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;

	  case "osa":
			a1.href = "OsAnimali.do";
			a2.href = "OsAnimali.do?command=Search";
			a3.href = "OsAnimali.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Operatori Sperimentazione Animali";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Operatore Sperimentazione Animali";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;

	  case "osm":
			a1.href = "Osm.do";
			a2.href = "Osm.do?command=Search";
			a3.href = "Osm.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Osm";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Osm";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;

	  case "osmRegistrati":
			a1.href = "OsmRegistrati.do";
			a2.href = "OsmRegistrati.do?command=Search";
			a3.href = "OsmRegistrati.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "OSM Registrati";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Osm";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;
	   
	  case "trasportoanimali":
			a1.href = "TrasportoAnimali.do";
			a2.href = "TrasportoAnimali.do?command=Search";
			a3.href = "TrasportoAnimali.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Trasporto Animali";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Dettaglio Trasporto";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;
	   
	  case "soa":
			a1.href = "Soa.do";
			a2.href = "Soa.do?command=Search";
			a3.href = "Soa.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "SOA";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda SOA";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;

	  case "farmacosorveglianza":
			a1.href = "Farmacosorveglianza.do?command=SearchFormFcie";
			a2.href = "Farmacosorveglianza.do?command=SearchFcie";
			a3.href = "Farmacosorveglianza.do?command=DetailsFcie&idFarmacia=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Farmacosorveglianza";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Dettaglio Farmacia";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;

	  case "parafarmacie":
			a1.href = "Parafarmacie.do?command=SearchFormFcie";
			a2.href = "Parafarmacie.do?command=SearchFcie";
			a3.href = "Parafarmacie.do?command=DetailsFcie&idFarmacia=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Parafarmacie";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Dettaglio Parafarmacia";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;
	   
	  case "laboratorihaccp":
			a1.href = "LaboratoriHACCP.do?command=SearchForm";
			a2.href = "LaboratoriHACCP.do?command=Search";
			a3.href = "Parafarmacie.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "LaboratoriHaccp";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Laboratorio Haccp";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;
   
	  case "canili":
			a1.href = "Canili.do";
			a2.href = "Canili.do?command=Search";
			a3.href = "Canili.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Canili";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Canile";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;
	   
	  case "canipadronali":
			a1.href = "CaniPadronali.do?command=SearchForm";
			a2.href = "CaniPadronaliVigilanza.do?command=SearchVigilanza";
			a3.href = "";
			a1.innerHTML = "Anagrafica Cani di proprieta";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "";
			gt1.innerHTML=" > "; gt2.innerHTML=" > ";
	   break;
	   
	  case "operatori_commerciali":
			a1.href = "OperatoriCommerciali.do";
			a2.href = "OperatoriCommerciali.do?command=Search";
			a3.href = "OperatoriCommerciali.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Operatori Commerciali";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Operatore Commerciale";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;
	   
	  case "operatoriregione":
			a1.href = "OperatoriFuoriRegione.do";
			a2.href = "OperatoriFuoriRegione.do?command=Search";
			a3.href = "OperatoriFuoriRegione.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Attività Mobile Fuori Ambito ASL";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Operatore Altre ASL della Campania";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;
	   
	  case "operatoriregioneT":
			a1.href = "OperatoriFuoriRegione.do";
			a2.href = "OperatoriFuoriRegione.do?command=Search";
			a3.href = "OperatoriFuoriRegione.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Attività Mobile Fuori Ambito ASL";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Operatore Altre ASL della Campania";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;
		   
	  case "abusivismi":
			a1.href = "Abusivismi.do";
			a2.href = "Abusivismi.do?command=Search";
			a3.href = "Abusivismi.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Operatori Abusivi";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Operatore Abusivo";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;

	  case "operatoriprivati":
			a1.href = "Operatoriprivati.do";
			a2.href = "Operatoriprivati.do?command=Search";
			a3.href = "Operatoriprivati.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Privati";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Privato";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;

	  case "operatorinonaltrove":
			a1.href = "OpnonAltrove.do";
			a2.href = "OpnonAltrove.do?command=Search";
			a3.href = "OpnonAltrove.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Operatori non presenti altrove";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;

	  case "imbarcazioni":
			a1.href = "Imbarcazioni.do";
			a2.href = "Imbarcazioni.do?command=Search";
			a3.href = "Imbarcazioni.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Imbarcazioni";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Imbarcazioni";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;	   

	  case "colonie":
			a1.href = "Colonie.do";
			a2.href = "Colonie.do?command=Search";
			a3.href = "Colonie.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Colonie";
			a2.innerHTML = "Risultati Ricerca";
			a3.innerHTML = "Scheda Colonia";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
	   break;	
	}	
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
<tbody><tr>
<td>
<a id="action1" href=""></a><label id="gt1"></label> 
<a id="action2" href=""></a><label id="gt2"></label>
<a id="action3" href=""></a><label id="gt3"></label>
Campioni Prenotati
</td>
</tr>
</tbody>
</table>
<form name="campioni_prenotati" action="Campioni.do?command=UpdateElencoCampioniPrenotati&orgId=<%=orgId%>&op=<%=request.getParameter("op")%>" method="post">
<dhv:container name="<%=request.getParameter("op")%>" selected="campioniprenotati" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
		<th colspan="5" style="background-color: rgb(204, 255, 153);"><strong>Elenco
		dei campioni prenotati</strong></th>
	</tr>

	<tr style="background-color: rgb(189, 207, 255);">
		<td width="10%" 
		onmouseover="javascript:ddrivetip('Codice del campione',150,'lightyellow');" 
		onmouseout="javascript:hideddrivetip();"><b>Codice</b></td>
		<td width="70%"
		onmouseover="javascript:ddrivetip('Motivazione del campione. (N.D. se non inserita in fase di prenotazione)',150,'lightyellow');" 
		onmouseout="javascript:hideddrivetip();"><b>Motivazione</b></td>
		<td width="10%"
		onmouseover="javascript:ddrivetip('Elenco dei Controlli Ufficiali (CU) a cui è possibile associare il campione. (N.D. se non esistono CU con la stessa motivazione)',150,'lightyellow');" 
		onmouseout="javascript:hideddrivetip();"><b>Controllo Ufficiale</b></td>
		<td width="10%"
		onmouseover="javascript:ddrivetip('Azione da eseguire. Se spuntata associa il campione al Controllo Ufficiale selezionato',150,'lightyellow');" 
		onmouseout="javascript:hideddrivetip();"><b>Azione</b></td>
	</tr>

	<%  int color;
		for(int i=0;i<data.size();i++){ 
		 	String split[] = data.get(i).split("---");	

		 	if (i%2==1) color=255; 
		 	else color=237; %>
			<tr style="background-color: rgb(<%=color%>,<%=color%>,<%=color%>)">
				<td onmouseover="javascript:ddrivetip('Codice del campione',150,'lightyellow');" 
					onmouseout="javascript:hideddrivetip();"><%=split[0]%></td>
				<td onmouseover="javascript:ddrivetip('Motivazione del campione. (N.D. se non inserita in fase di prenotazione)',150,'lightyellow');" 
					onmouseout="javascript:hideddrivetip();"><%=split[3]%></td>
				<td onmouseover="javascript:ddrivetip('Elenco dei Controlli Ufficiali (CU) a cui è possibile associare il campione. (N.D. se non esistono CU con la stessa motivazione)',150,'lightyellow');" 
					onmouseout="javascript:hideddrivetip();"><select style="width:80%" id="CU<%=i%>" name="CU<%=i%>">
					<% if (elencoCU.size()>0){
						String split2[] = elencoCU.get(i).split("---");
						for(int j=0;j<split2.length;j++){%>
							<option value="<%=split2[j]%>"><%=split2[j]%></option>
						<%}
					} else {%> <option value="N.D.">N.D.</option> <%}%>
				</select></td>
				<td onmouseover="javascript:ddrivetip('Azione da eseguire. Se spuntata associa il campione al Controllo Ufficiale selezionato',150,'lightyellow');" 
					onmouseout="javascript:hideddrivetip();"><input type="checkbox" name="ckbox<%=i%>" id="ckbox<%=i%>"></input></td>
			</tr>
	<%}%>
</table>

<br>
<p align="center">
	<input type="button" onclick="javascript:check();" value="Importa dati Selezionti" 
	onmouseover="javascript:ddrivetip('Associa ogni campione selezionato (spuntato) al relativo Controllo Ufficiale selezionato',150,'lightyellow');" 
	onmouseout="javascript:hideddrivetip();"></input> 
</p>
<input type="hidden" name="NElem" value="<%=data.size()%>" id="NElem"></input>
<% for(int k=0;k<data.size();k++){ %>
<input type="hidden" name="data<%=k%>" value="<%=data.get(k)%>"></input>
<%}%>

</dhv:container>
</form>

<script type="text/javascript">setLink()</script>
