<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.campioni.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*,org.aspcfs.modules.base.EmailAddress" %>

<jsp:useBean id="elencoMotivazioni" class="java.util.ArrayList" scope="session"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.campioni.base.Ticket" scope="request"/>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>

<script language="JavaScript">
function openTree(campoid1,campoid2,table,id,idPadre,livello,multiplo,divPath,idRiga)
{
	window.open('Tree.do?command=DettaglioTree&multiplo='+multiplo+'&divPath='+divPath+'&idRiga='+idRiga+'&campoId1='+campoid1+'&campoId2='+campoid2+'&nomeTabella='+table+'&campoId='+id+'&campoPadre='+idPadre+'&campoDesc=nome&campoLivello=livello&campoEnabled=nuova_gestione&sel=true');
}

function openTreeAnaliti()
{
	window.open('Tree.do?command=DettaglioTree&nomeTabella=analiti&campoId=analiti_id&campoPadre=id_padre&campoDesc=nome&campoLivello=livello&campoEnabled=nuova_gestione&sel=true');
}
</script>

<script>
function check(){
	var msg="Inserire i campi : ";
	
	if (document.getElementById("numero_verbale_add")!=null){
		var numverbale = document.getElementById("numero_verbale_add").value;
		if (numverbale.trim()=="")
			msg=msg+"\nNumero Verbale"
	}
	if (document.getElementById("data_prelievo_add")!=null){
		if (document.getElementById("data_prelievo_add").value=="")
			msg=msg+"\nData Prelievo";
	}
	if (document.getElementById("size1")!=null){
		if (document.getElementById("size1").value==0)
			msg=msg+"\nMatrice";
	}
	if (document.getElementById("size")!=null){
		if (document.getElementById("size").value==0)
			msg=msg+"\nTipo di Analisi";
	}

	if (document.getElementById("motivazione_add")!=null){
		var sel = document.getElementById("motivazione_add");
		var opt = sel.options[sel.selectedIndex].value;
		var id = sel.options[sel.selectedIndex].id;
		document.update_campione_prenotato.descr_mot.value=opt;
		document.update_campione_prenotato.id_mot.value=id;
	}

	if (msg=="Inserire i campi : "){ 
		var sA = document.getElementById("elementi").value;
		var n_v = "<%=request.getParameter("ck_nv")%>";
		var flag=0;
		var str="";


		if (sA>0){
			if (n_v.match("vpb$") ){ //BATTERIOLOGICO
				for(i=1;i<=sA;i++){
					str = document.getElementById("pathAnaliti_"+i).value;
					if(str.match('BATTERIOLOGICO')){ 
						flag=1;
					}
				}
			} 
			else if (n_v.match("vpc$") ){ //CHIMICO
					for(i=1;i<=sA;i++){
						str = document.getElementById("pathAnaliti_"+i).value;
						if(str.match('CHIMICO')){ 
							flag=1;
						}
					}
				}
				else { // ALTRO TIPO DI VERBALE
					flag=1;
				} 
		}else {
			flag=1;
		}

		if (flag!=0){ 
			var nomeAction = '<%=request.getParameter("input")%>';	
			document.update_campione_prenotato.action="Campioni.do?command=UpdateCompletaCampione&id=<%=request.getParameter("id")%>&orgId=<%=request.getParameter("orgId")%>&input=<%=request.getParameter("input")%>";
			document.update_campione_prenotato.submit();
		}
		else {
			alert("Impossibile completare il campione.\nIl tipo di verbale non corrisponde al tipo di analiti selezionati");
		}
	}
	else {	
		alert(msg); 
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
	var offsetfromcursorX=12; 
	var offsetfromcursorY=10; 
	var offsetdivfrompointerX=10; 
	var offsetdivfrompointerY=14; 
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

<script>
function setLink(){
	var op = '<%=TicketDetails.getURlDettaglio()%>'
	var a1 = document.getElementById('action1'); var gt1 = document.getElementById("gt1");
	var a2 = document.getElementById('action2'); var gt2 = document.getElementById("gt2");
	var a3 = document.getElementById('action3'); var gt3 = document.getElementById("gt3");
	var a4 = document.getElementById('action4'); var gt4 = document.getElementById("gt4");
	var a5 = document.getElementById('action5'); var gt5 = document.getElementById("gt5");
	var a6 = document.getElementById('action6'); var gt6 = document.getElementById("gt6");

	switch (op) {
	  case "Account":
		  	a1.href = "Accounts.do";
			a2.href = "Accounts.do?command=Search"; 
			a3.href = "Accounts.do?command=Details&orgId=<%=request.getParameter("orgId")%>";	
			a4.href = "Accounts.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "AccountVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "AccountCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Imprese"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda Impresa"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale"; 
			a6.innerHTML = "Scheda Campione";
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  	 
	  break;

	  case "MolluschiBivalvi":
	  		a1.href = "MolluschiBivalvi.do";
	  		a2.href = "MolluschiBivalvi.do?command=Search";
	  		a3.href = "MolluschiBivalvi.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "MolluschiBivalvi.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "MolluschiBivalviVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "MolluschiBivalviCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
	  		a1.innerHTML = "Molluschi Bivalvi"; 
	  		a2.innerHTML = "Ricerca Molluschi Bivalvi"; 
	  		a3.innerHTML = "Dettaglio Molluschi Bivalvi"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	  break;

	  case "Stabilimenti":
			a1.href = "Stabilimenti.do";
			a2.href = "Stabilimenti.do?command=Search";
			a3.href = "Stabilimenti.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "Stabilimenti.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "StabilimentiVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "StabilimentiCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Stabilimenti"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda Stabilimento"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	  break;

	  case "Allevamenti":
			a1.href = "Allevamenti.do";
			a2.href = "Allevamenti.do?command=Search";
			a3.href = "Allevamenti.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "Allevamenti.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "AllevamentiVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "AllevamentiCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Allevamenti"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda Allevamento"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	  break;

	  case "PuntiSbarco":
			a1.href = "PuntiSbarco.do?command=SearchForm";
			a2.href = "PuntiSbarco.do?command=Search";
			a3.href = "PuntiSbarco.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "PuntiSbarco.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "PuntiSbarcoVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "PuntiSbarcoCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Punti Di Sbarco"; 
			a2.innerHTML = "Ricerca Punto di Sbarco"; 
			a3.innerHTML = "Dettaglio Punto di sbarco"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	  break;

	  case "AziendeAgricole":
			a1.href = "AziendeAgricole.do?command=SearchForm";
			a2.href = "AziendeAgricole.do?command=Search";
			a3.href = "AziendeAgricole.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "AziendeAgricole.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "AziendeAgricoleVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "AziendeAgricoleCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Aziende Agricole"; 
			a2.innerHTML = "Ricerca Aziende Agricole"; 
			a3.innerHTML = "Dettaglio Aziende Agricole"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	  break;

	  case "RiproduzioneAnimale":
			a1.href = "RiproduzioneAnimale.do";
			a2.href = "RiproduzioneAnimale.do?command=Search";
			a3.href = "RiproduzioneAnimale.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "RiproduzioneAnimale.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "RiproduzioneAnimaleVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "RiproduzioneAnimaleCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Strutture di Riproduzione Animale"; 
			a2.innerHTML = "Ricerca Strutture di Riproduzione Animale"; 
			a3.innerHTML = "Dettaglio Strutture di Riproduzione Animale"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;

	  case "OSAnimali":
			a1.href = "OsAnimali.do";
			a2.href = "OsAnimali.do?command=Search";
			a3.href = "OsAnimali.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "OSAnimali.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "OSAnimaliVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "OSAnimaliCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Operatori Sperimentazione Animali"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda Operatore Sperimentazione Animali"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;

	  case "Osm":
			a1.href = "Osm.do";
			a2.href = "Osm.do?command=Search";
			a3.href = "Osm.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "Osm.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "OsmVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "OsmCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Osm"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda Osm"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;

	  case "OsmRegistrati":
			a1.href = "OsmRegistrati.do";
			a2.href = "OsmRegistrati.do?command=Search";
			a3.href = "OsmRegistrati.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "OsmRegistrati.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "OsmRegistratiVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "OsmRegistratiCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "OSM Registrati"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda Osm"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";   
	   break;
	   
	  case "TrasportoAnimali":
			a1.href = "TrasportoAnimali.do";
			a2.href = "TrasportoAnimali.do?command=Search";
			a3.href = "TrasportoAnimali.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "TrasportoAnimali.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "TrasportoAnimaliVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "TrasportoAnimaliCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Trasporto Animali"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Dettaglio Trasporto"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;
	   
	  case "Soa":
			a1.href = "Soa.do";
			a2.href = "Soa.do?command=Search";
			a3.href = "Soa.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "Soa.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "SoaVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "SoaCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "SOA"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda SOA"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;

	  case "Farmacosorveglianza":
			a1.href = "Farmacosorveglianza.do?command=SearchFormFcie";
			a2.href = "Farmacosorveglianza.do?command=SearchFcie";
			a3.href = "Farmacosorveglianza.do?command=DetailsFcie&idFarmacia=<%=request.getParameter("orgId")%>";
			a4.href = "Farmacosorveglianza.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "FarmacosorveglianzaVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "FarmacosorveglianzaCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Farmacosorveglianza";  
			a2.innerHTML = "Risultati Ricerca";  
			a3.innerHTML = "Dettaglio Farmacia";  
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;

	  case "Parafarmacie":
			a1.href = "Parafarmacie.do?command=SearchFormFcie";
			a2.href = "Parafarmacie.do?command=SearchFcie";
			a3.href = "Parafarmacie.do?command=DetailsFcie&idFarmacia=<%=request.getParameter("orgId")%>";
			a4.href = "Parafarmacie.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "ParafarmacieVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "ParafarmacieCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Parafarmacie"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Dettaglio Parafarmacia"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;
	   
	  case "LaboratoriHACCP":
			a1.href = "LaboratoriHACCP.do?command=SearchForm";
			a2.href = "LaboratoriHACCP.do?command=Search";
			a3.href = "LaboratoriHACCP.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "LaboratoriHACCP.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "LaboratoriHACCPVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "LaboratoriHACCPCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "LaboratoriHaccp"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda Laboratorio Haccp"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;
   
	  case "Canili":
			a1.href = "Canili.do";
			a2.href = "Canili.do?command=Search";
			a3.href = "Canili.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "Canili.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "CaniliVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "CaniliCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Canili"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda Canile"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";   
	   break;
	   
	  case "CaniPadronali":
			a1.href = "CaniPadronali.do?command=SearchForm";
			a2.href = "CaniPadronaliVigilanza.do?command=SearchVigilanza";
			a3.href = "CaniPadronaliVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "";
			a5.href = "";
			a6.href = "";
			a1.innerHTML = "Anagrafica Cani di proprieta"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "";
			a4.innerHTML = "";
			a5.innerHTML = "";
			a6.innerHTML = "";
			gt1.innerHTML=" > "; gt2.innerHTML=" > ";
	   break;
	   
	  case "OperatoriCommerciali":
			a1.href = "OperatoriCommerciali.do";
			a2.href = "OperatoriCommerciali.do?command=Search";
			a3.href = "OperatoriCommerciali.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "OperatoriCommerciali.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "OperatoriCommercialiVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "OperatoriCommercialiCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Operatori Commerciali"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda Operatore Commerciale"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;
	   
	  case "OperatoriFuoriRegione":
			a1.href = "OperatoriFuoriRegione.do";
			a2.href = "OperatoriFuoriRegione.do?command=Search";
			a3.href = "OperatoriFuoriRegione.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "OperatoriFuoriRegione.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "OperatoriFuoriRegioneVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "OperatoriFuoriRegioneCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Attività Mobile Fuori Ambito ASL"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda Operatore Altre ASL della Campania"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;
	   
//	  case "operatoriregioneT":
//			a1.href = "OperatoriFuoriRegione.do";
//			a2.href = "OperatoriFuoriRegione.do?command=Search";
//			a3.href = "OperatoriFuoriRegione.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
//			a4.href = "OperatoriFuoriRegione.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
//			a5.href = "OperatoriFuoriRegioneVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
//			a6.href = "OperatoriFuoriRegioneCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
//			a1.innerHTML = "Attività Mobile Fuori Ambito ASL";
//			a2.innerHTML = "Risultati Ricerca";
//			a3.innerHTML = "Scheda Operatore Altre ASL della Campania";
//	   break; 
		   
	  case "Abusivismi":
			a1.href = "Abusivismi.do";
			a2.href = "Abusivismi.do?command=Search";
			a3.href = "Abusivismi.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "Abusivismi.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "AbusivismiVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "AbusivismiCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Operatori Abusivi";  
			a2.innerHTML = "Risultati Ricerca";  
			a3.innerHTML = "Scheda Operatore Abusivo";  
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;

	  case "Operatoriprivati":
			a1.href = "Operatoriprivati.do";
			a2.href = "Operatoriprivati.do?command=Search";
			a3.href = "Operatoriprivati.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "Operatoriprivati.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "OperatoriprivatiVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "OperatoriprivatiCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Privati"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda Privato"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;

	  case "OpnonAltrove":
			a1.href = "OpnonAltrove.do";
			a2.href = "OpnonAltrove.do?command=Search";
			a3.href = "OpnonAltrove.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "OpnonAltrove.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "OpnonAltroveVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "OpnonAltroveCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Operatori non presenti altrove"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;

	  case "Imbarcazioni":
			a1.href = "Imbarcazioni.do";
			a2.href = "Imbarcazioni.do?command=Search";
			a3.href = "Imbarcazioni.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "Imbarcazioni.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "ImbarcazioniVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "ImbarcazioniCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Imbarcazioni"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Imbarcazioni"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;	   

	  case "Colonie":
			a1.href = "Colonie.do";
			a2.href = "Colonie.do?command=Search";
			a3.href = "Colonie.do?command=Details&orgId=<%=request.getParameter("orgId")%>";
			a4.href = "Colonie.do?command=ViewVigilanza&orgId=<%=request.getParameter("orgId")%>";
			a5.href = "ColonieVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&orgId=<%=request.getParameter("orgId")%>";
			a6.href = "ColonieCampioni.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&orgId=<%=request.getParameter("orgId")%>";
			a1.innerHTML = "Colonie"; 
			a2.innerHTML = "Risultati Ricerca"; 
			a3.innerHTML = "Scheda Colonia"; 
			a4.innerHTML = "Controlli Ufficiali"; 
			a5.innerHTML = "Controllo Ufficiale";  
			a6.innerHTML = "Scheda Campione"; 
			gt1.innerHTML=" > "; gt2.innerHTML=" > "; gt3.innerHTML=" > ";
			gt4.innerHTML=" > "; gt5.innerHTML=" > "; gt6.innerHTML=" > ";  
	   break;	   
	}	
}
</script>

<script>
function bloccaSelezioneAnaliti(){
	var num_analiti_selezionati = document.getElementById("elementi").value;
	if (num_analiti_selezionati!=0){
		openTree('analitiId','pathAnaliti','analiti','analiti_id','id_padre','livello','si','divPathAnaliti','clona');
	}
	else {
		openTree('analitiId','pathAnaliti','analiti','analiti_id','id_padre','livello','si','divPathAnaliti','clona');
	}
}
</script>

<form name="update_campione_prenotato" action="Campioni.do?command=UpdateCompletaCampione&orgId=<%=request.getParameter("orgId")%>" method="post">

<input type="hidden" name="descr_mot" id="descr_mot" value="-2"/>
<input type="hidden" name="id_mot" id="id_mot" value="-2"/>

<table class="trails" cellspacing="0" cellpadding="0">
<tbody>
<tr><td> 
<a id="action1" href=""></a><label id="gt1"></label>
<a id="action2" href=""></a><label id="gt2"></label>
<a id="action3" href=""></a><label id="gt3"></label>
<a id="action4" href=""></a><label id="gt4"></label>
<a id="action5" href=""></a><label id="gt5"></label>
<a id="action6" href=""></a><label id="gt6"></label>
Completa Campione</td></tr>
</tbody>
</table>
<script type="text/javascript">setLink()</script>
<table cellpadding="4" cellspacing="0" width="100%" class="details"
	onmouseover="javascript:ddrivetip('Informazioni richieste per il completamento del campione',150,'lightyellow');" 
	onmouseout="javascript:hideddrivetip();">


<% if  (request.getParameter("ck_mot")==null || request.getParameter("ck_mot").equals("")) { %>
		<tr><td class="formLabel" width="10%">MOTIVAZIONE</td>
		<td><select id="motivazione_add">
			<%  for (int i=0;i<elencoMotivazioni.size();i++){ 
					String s = elencoMotivazioni.get(i).toString(); 
					String split[] = s.split("---");%>
					<option id="<%=split[0]%>" value="<%=split[1]%>"><%=split[1]%></option>
			<%}%>
			</select></td></tr>
<%}%>


<% if  (request.getParameter("ck_nv")==null || request.getParameter("ck_nv").equals("")) {%>
<tr><td class="formLabel" width="10%">NUMERO VERBALE</td><td><input type="text" id="numero_verbale_add" name="numero_verbale_add" value=""></input></td></tr>
<%} %>


<% if  (request.getParameter("ck_dp")==null || request.getParameter("ck_dp").equals("")) {%>
<tr><td class="formLabel" width="10%">DATA PRELIEVO</td><td><input type="text" id="data_prelievo_add" name="data_prelievo_add" value="" readonly="">
	<a id="anchor19" name="anchor19" onclick="cal19.select(document.update_campione_prenotato.data_prelievo_add,'anchor19','dd/MM/yyyy'); return false;" href="#">
	<img border="0" align="absmiddle" src="images/icons/stock_form-date-field-16.gif"></img></a></td></tr> 
<%} %>


<% if  (request.getParameter("ck_mat")==null || request.getParameter("ck_mat").equals("")) {%>
 
 <tr><td class="formLabel">MATRICE</td>
 	<td><table class = "noborder"><tr><td>
    <input type = "hidden" name = "elementi1" id = "elementi1" value = "0">
    <input type = "hidden" name = "size1" id = "size1" value = "0">
    <tr id = "clonaM" style="">
    <td>
    <input type = "hidden" name = "idMatrice" id = "idMatrice" value = "-1">
    <input type = "hidden" name = "path" id = "path">
    <div id = "divPath"></div>     
   </td>
   </tr>
   <tr>
   <td>
    <a href = "javascript:openTree('idMatrice','path','matrici','matrice_id','id_padre','livello','no','divPath','clonaM')">(Selezionare Matrice)</a>
   <br> <br>
     <textarea id="noteMatrici" rows="5" cols="30" name = "noteMatrici"></textarea></br>
   </td>
   </tr>
   </table>
    </td>
</tr>
<%} %>


<% if  (request.getParameter("ck_an")==null || request.getParameter("ck_an").equals("")) {%>
<tr><td class="formLabel" width="10%">TIPO DI ANALISI</td>
    <td>
    <table class = "noborder"><tr><td>
    <input type = "hidden" name = "elementi" id = "elementi" value = "0">
    <input type = "hidden" name = "size" id = "size" value = "0">
    <tr id = "clona" style="display: none">
    <td>
    <input type = "hidden" name = "analitiId">
    <input type = "hidden" name = "pathAnaliti">
    <div id = "divPathAnaliti"></div>
    </td>
   </tr>
   <tr>
   <td>
   <a href = "javascript:bloccaSelezioneAnaliti()">(Selezionare Tipo Analisi)</center></a>
   <br> <br>
    <textarea id="noteAnalisi" rows="8" cols="40" name="noteAnalisi"></textarea></br>
   </td>
   </tr>
   </table>
    </td>
    </tr>
<% } else {%>
    <input type = "hidden" name = "elementi" id = "elementi" value = "0">
<%} %>


</table>
<br><br>
<!--  <input type="button" value="Aggiorna" id="aggiorna" onClick="javascript:this.form.action='AccountCampioni.do?command=UpdateCompletaCampione&id=<%=request.getParameter("id")%>&orgId=<%=request.getParameter("orgId")%>';submit();"></input> -->
<input type="button" value="Aggiorna" id="aggiorna" onClick="javascript:check();"
		onmouseover="javascript:ddrivetip('Salva i cambiamenti',150,'lightyellow');" 
		onmouseout="javascript:hideddrivetip();"></input>
</form>
