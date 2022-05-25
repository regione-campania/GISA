<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="msg" class="java.lang.String" scope="request"/>


<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<script>$(document).ready(function() {
      // Initialize Smart Wizard
      loadModalWindowUnlock();
 }); 
</script>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>


<script>
function checkForm(form){
	var punteggio = form.punteggio.value;
	
	if (punteggio == '')
		alert('Compilare PUNTEGGIO.');
	else if (isNaN(punteggio))
		alert('Inserire un valore numerico in PUNTEGGIO.');
	else{
		loadModalWindow();
		form.submit();
	}
	
	
}


</script>

<%if (msg!=null && !msg.equals("")) {%>

<%=msg %>

<input type="button" value="chiudi e ricarica" onClick="window.opener.location.href = '<%=TicketDetails.getURlDettaglio()%>Vigilanza.do?command=TicketDetails&id=<%=TicketDetails.getId()%>&altId=<%=TicketDetails.getAltId()%>'; window.close(); "/>


<%} %>



<form id = "addAccount" name="addAccount" action="PrintReportVigilanza.do?command=InserisciDatiPunteggio&auto-populate=true" method="post">
<input type="hidden" id="idCU" name="idCU" value="<%=TicketDetails.getId() %>"/>
<table class="details">
<tr><th colspan="2">Checklist</th></tr>
<tr><th>Tipo checklist</th><th>Punteggio checklist</th></tr>
<tr><td>Checklist SOA</td><td> <input type="number" id="punteggio" name="punteggio" min="0" max="500" value="<%=TicketDetails.getPunteggio()%>"/></td></tr>
<tr><th colspan="2"><input type="button" value="salva" onClick="checkForm(this.form)"/></th></tr>

</table>
</form>


