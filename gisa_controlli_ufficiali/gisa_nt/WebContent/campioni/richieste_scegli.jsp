<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.campioni.base.*" %>
<%@ include file="../initPage.jsp" %>
<form name="addticket" action="TroubleTicketsCampioni.do?command=Insert&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsCampioni.do"><dhv:label name="campioni">Richieste</dhv:label></a> > 
<dhv:label name="richieste.aggiungi">Aggiungi Richiesta</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

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
document.onmousemove=positiontip
</script>


<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    	<th colspan="2">
      		<strong>Tipo Richiesta</strong>
    	</th>
	</tr>
	
	<tr> 
    	<td class="formLabel">
     	 	Scegli il tipo di Richiesta
    	</td>
   		 <td>
      		<input
      			type="submit"
      			onmouseover="ddrivetip('<dhv:label name="richieste.epidemologia_malattie_infettive">Epidemologia delle Malattie Infettive</dhv:label>')"
      			onmouseout="hideddrivetip()"
      			name="<dhv:label name="richieste.emi">EMI</dhv:label>"
      			value="<dhv:label name="richieste.emi">EMI</dhv:label>"
      			onclick="javascript:this.form.action='TroubleTicketsCampioni.do?command=Add&tipo_richiesta=epidemologia_malattie_infettive'"
      		/>
      		
      		<input
      			type="submit"
      			onmouseover="ddrivetip('<dhv:label name="richieste.autorizzazione_trasporto_animali_vivi">Autorizzazione Trasporto Animali Vivi</dhv:label>')"
      			onmouseout="hideddrivetip()"
      			name="<dhv:label name="richieste.atav">ATAV</dhv:label>"
      			value="<dhv:label name="richieste.atav">ATAV</dhv:label>"
      			onclick="javascript:this.form.action='TroubleTicketsCampioni.do?command=Add&tipo_richiesta=autorizzazione_trasporto_animali_vivi'"
      		/>

      		
      		<input
      			type="submit"
      			onmouseover="ddrivetip('<dhv:label name="richieste.macellazioni">Macellazioni</dhv:label>')"
      			onmouseout="hideddrivetip()"
      			name="<dhv:label name="richieste.mac">Macellazioni</dhv:label>"
      			value="<dhv:label name="richieste.mac">Macellazioni</dhv:label>"
      			onclick="javascript:this.form.action='TroubleTicketsCampioni.do?command=Add&tipo_richiesta=macellazioni'"
      		/>      		

      		<input
      			type="submit"
      			onmouseover="ddrivetip('<dhv:label name="richieste.attivita_ispettiva_rilascioautorizzazioni_e_vigilanza">Attivit&agrave; Ispettiva per Rilascio Autorizzazioni e Controlli Ufficiali</dhv:label>')"
      			onmouseout="hideddrivetip()"
      			name="<dhv:label name="richieste.airav">AIRAV</dhv:label>"
      			value="<dhv:label name="richieste.airav">AIRAV</dhv:label>"
      			onclick="javascript:this.form.action='TroubleTicketsCampioni.do?command=Add&tipo_richiesta=attivita_ispettiva_rilascioautorizzazioni_e_vigilanza'"
      		/>	
      		
      		<input
      			type="submit"
      			onmouseover="ddrivetip('<dhv:label name="richieste.movimentazioni_compravendita_animali">Movimentazioni Compravendita Animali</dhv:label>')"
      			onmouseout="hideddrivetip()"
      			name="<dhv:label name="richieste.mcva">MCVA</dhv:label>"
      			value="<dhv:label name="richieste.mcva">MCVA</dhv:label>"
      			onclick="javascript:this.form.action='TroubleTicketsCampioni.do?command=Add&tipo_richiesta=movimentazioni_compravendita_animali'"
      		/>	
      		
      		<input
      			type="submit"
      			onmouseover="ddrivetip('<dhv:label name="richieste.smaltimento_spoglie_animali">Smaltimento spoglie animali</dhv:label>')"
      			onmouseout="hideddrivetip()"
      			name="<dhv:label name="richieste.ssa">SSA</dhv:label>"
      			value="<dhv:label name="richieste.ssa">SSA</dhv:label>"
      			onclick="javascript:this.form.action='TroubleTicketsCampioni.do?command=Add&tipo_richiesta=smaltimento_spoglie_animali'"
      		/>	
    	</td>
	</tr>
	
</table>

<%-- li>
	<input type="submit" value="<dhv:label name="richieste.epidemologia_malattie_infettive">Epidemologia delle Malattie Infettive</dhv:label>" onClick="javascript:this.form.action='TroubleTicketsCampioni.do?command=Add&tipo_richiesta=epidemologia_malattie_infettive'">
</li>
<li>
	<input type="submit" value="<dhv:label name="richieste.autorizzazione_trasporto_animali_vivi">Autorizzazione Trasporto Animali Vivi</dhv:label>" onClick="javascript:this.form.action='TroubleTicketsCampioni.do?command=Add&tipo_richiesta=autorizzazione_trasporto_animali_vivi'">
</li>
<li>
	<input type="submit" value="<dhv:label name="richieste.movimentazione_compravendita_animali">Movimentazione/Compravendita Animali</dhv:label>" onClick="javascript:this.form.action='TroubleTicketsCampioni.do?command=Add&tipo_richiesta=movimentazione_compravendita_animali'">
</li>
<li>
	<input type="submit" value="<dhv:label name="richieste.macellazioni">Macellazioni</dhv:label>" onClick="javascript:this.form.action='TroubleTicketsCampioni.do?command=Add&tipo_richiesta=macellazioni'">
</li>
<li>
	<input type="submit" value="<dhv:label name="richieste.attivita_ispettiva_rilascioautorizzazioni_e_vigilanza">Attivit&agrave; Ispettiva per Rilascio Autorizzazioni e Vigilanza</dhv:label>" onClick="javascript:this.form.action='TroubleTicketsCampioni.do?command=Add&tipo_richiesta=attivita_ispettiva_rilascioautorizzazioni_e_vigilanza'">
</li>
<li>
	<input type="submit" value="<dhv:label name="richieste.smaltimento_spoglie_animali">Smaltimento Spoglie Animali</dhv:label>" onClick="javascript:this.form.action='TroubleTicketsCampioni.do?command=Add&tipo_richiesta=smaltimento_spoglie_animali'">
</li --%>
<br>
<dhv:formMessage />
</form>
