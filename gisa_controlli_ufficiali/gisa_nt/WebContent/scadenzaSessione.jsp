<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%
int secondiSessione= session.getMaxInactiveInterval();
int minutiSessione = secondiSessione/60;

//A volte viene recuperato 5 minuti all'inizio. Sovrascrivo per evitare ambiguita'
if (minutiSessione <=5)
	minutiSessione = 30;
%>

<script>

// CONFIGURAZIONE
var minutiAttesaGiallo = 20;
var secondiAttesaGiallo = minutiAttesaGiallo*60;
var millisecondiAttesaGiallo = secondiAttesaGiallo*1000;

var minutiAttesaRosso = <%=minutiSessione%>-5;
var secondiAttesaRosso = minutiAttesaRosso*60;
var millisecondiAttesaRosso = secondiAttesaRosso*1000;

var secondiRefresh = 60;
var durataSessione = <%=minutiSessione%>;

</script>

<label id="contatoreScadenza"><%=minutiSessione %></label>

<script>

//ALERT ALLARME ROSSO (minutiAttesaRosso prima della fine sessione)
setTimeout(function(){
alert('Attenzione. Sono passati '+minutiAttesaRosso+' minuti dall\'apertura della pagina. LA SESSIONE STA PER SCADERE! Salvare le eventuali modifiche per evitare di perderle a causa dello scadere della sessione di lavoro tra circa '+ (durataSessione-minutiAttesaRosso)  +' minuti.'); }, millisecondiAttesaRosso);

//ALERT ALLARME GIALLO (minutiAttesaGiallo dopo inizio sessione)
setTimeout(function(){
	document.getElementById("scadenzaSessione").style.display="block"; 
	alert('Attenzione. Sono passati '+minutiAttesaGiallo+' minuti dall\'apertura della pagina. Salvare le eventuali modifiche per evitare di perderle a causa dello scadere della sessione di lavoro tra circa '+ (durataSessione-minutiAttesaGiallo)  +' minuti.'); }, millisecondiAttesaGiallo);

	
//REFRESH SCADENZA
setInterval(function() {
	refreshScadenza();}, secondiRefresh*1000);

	function refreshScadenza(){
		var minutiRecuperati = Number(document.getElementById("contatoreScadenza").innerHTML);
		minutiRecuperati = minutiRecuperati-secondiRefresh/60;
		if (minutiRecuperati<0)
			minutiRecuperati=0;
		document.getElementById("contatoreScadenza").innerHTML=minutiRecuperati;
	}
</script>



