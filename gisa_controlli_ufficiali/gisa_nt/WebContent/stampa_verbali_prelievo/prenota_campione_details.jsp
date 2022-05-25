<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.campioni.base.Ticket" scope="request"/>
<jsp:useBean id="orgId" class="java.lang.String" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DestinatarioCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipo" class="java.lang.String" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stampa_verbale_prelievo.base.Organization" scope="request"/>

<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />

<%@page import="org.aspcfs.modules.campioni.base.Analita"%>

<script>
//function openPopupModulesForm(form){	 
//	window.open('PrintModulesHTML.do?command=ViewStampa&orgId=<%=orgId%>&tipo=<%=tipo%>&barcodeId=<%=TicketDetails.getLocation()%>','popupSelect','height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
//	form.target = 'popupSelect';
//	document.printmodules.submit(); 
//} 

/*function openPopupModules(orgId, barcode, tipo, sizeA, sizeM, analiti, matrici, motivazione_campione, motivazione_piano_campione) {
	window.open('PrintModulesHTML.do?command=ViewStampa&orgId='+orgId+'&barcodeId='+barcode+'&tipo='+tipo+'&sizeA='+sizeA+'&sizeM='+sizeM+analiti+matrici+'&motivazione_campione='+motivazione_campione+'&motivazione_piano_campione='+motivazione_piano_campione,'popupSelect',
	'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
} */


function openPopupModules(orgId, ticketId, idCU, url, tipo){
            window.open('PrintModulesHTML.do?command=ViewModules&orgId='+orgId+'&ticketId='+ticketId+'&idCU='+idCU+'&url='+url+'&tipo='+tipo,'popupSelect',
            'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
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

<!-- <form method="post"  name = "printmodules" action="PrintModulesHTML.do?command=ViewStampa&orgId=<%=orgId%>&tipo=<%=tipo%>&barcodeId=<%=TicketDetails.getLocation()%>">  -->

<table class="trails" cellspacing="0" >
<tr>
<td>
<a href="OsaSearch.do">Operatori</dhv:label></a> > 
<a href="OsaSearch.do?command=Search"><dhv:label name=" ">Ricerca Operatori</dhv:label></a> >
<a href="PrintModulesHTML.do?command=Prenota&orgId=<%=orgId%>"><dhv:label name=" ">Prenotazione Campione</dhv:label></a> >
<dhv:label name=" ">Dettaglio Campione Prenotato</dhv:label></a> 
</td>
</tr>
</table>

<table cellpadding="4" cellspacing="0" width="100%" class="details"
	onmouseover="javascript:ddrivetip('Dettagli del campione prenotato per l\'operatore selezionato',150,'lightyellow');" 
	onmouseout="javascript:hideddrivetip();">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Dettaglio Campione Prenotato per : <%=OrgDetails.getName() %></dhv:label></strong>
    </th>
	</tr>
  
     <%@ include file="/campioni/campioni_view.jsp" %>
     <% 	
     
	    ArrayList<Analita> tipiCampioni = TicketDetails.getTipiCampioni();
	 	String descrizione = "";
	 	String analiti = "";
	 	
	 	for (int len = 0; len < tipiCampioni.size(); len++) {
	 		Analita a = tipiCampioni.get(len);
	 		descrizione += a.getDescrizione() + "---"; 
	 		int idA=a.getIdAnalita();
	 		analiti = analiti+"&IdA_"+len+"="+idA; 
	 	}
	 	int sizeA = tipiCampioni.size();
	 	
	 	HashMap<Integer,String> matr= TicketDetails.getMatrici();
		Iterator<Integer> itMatr = matrici.keySet().iterator();
		int k = -1 ;
		String mat = "";
		while(itMatr.hasNext())
		{
			k++ ;
			int chiave = itMatr.next(); 
			mat = mat+"&IdM_"+k+"="+chiave; 
			
		}
		int sizeM = k+1;
		String flagSigla = "SIGLA";
		int motivazione_campione = TicketDetails.getMotivazione_campione();
		int motivazione_piano_campione = TicketDetails.getMotivazione_piano_campione();
     		
     %>
</table> <!--  chiusura tabella generale -->
<br>
<!-- <a class="ovalbutton" href = "#" onclick = "javascript:openPopupModulesForm(printmodules);" ><span>Stampa Verbale</span></a>  -->
<a class="ovalbutton" href = "#" 
onmouseover="javascript:ddrivetip('Stampa il verbale relativo al campione. Contiene le informazioni specificate ed i dati relativi all\'operatore',150,'lightyellow');" 
onmouseout="javascript:hideddrivetip();"
onclick = "javascript:openPopupModules('<%= OrgDetails.getOrgId() %>', '<%= TicketDetails.getId() %>', '<%= TicketDetails.getIdControlloUfficiale() %>', '<%= TicketDetails.getURlDettaglio() %>','<%=request.getAttribute("tipo")%>');"><span>Stampa Verbale</span></a>
<!-- onclick = "javascript:openPopupModules('<%=orgId%>','<%=TicketDetails.getLocation()%>','<%=tipo%>','<%=sizeA %>','<%=sizeM %>','<%=analiti %>','<%=mat %>','<%=motivazione_campione %>','<%=motivazione_piano_campione %>');" ><span>Stampa Verbale</span></a>  -->
<!-- </form>  -->