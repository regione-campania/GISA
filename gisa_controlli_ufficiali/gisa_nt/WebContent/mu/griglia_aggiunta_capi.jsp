<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>HTML Table Filter Generator script | Examples - jsFiddle demo by koalyptus</title>
   <script type='text/javascript' src='/js/lib/dummy.js'></script>
  <link rel="stylesheet" type="text/css" href="/css/result-light.css">
<script type='text/javascript' src="javascript/tablefilter/tablefilter_all_min.js"></script>
<link rel="stylesheet" type="text/css" href="javascript/tablefilter/filtergrid.css">
</head>

<script>
(function(document) {
	'use strict';

	var LightTableFilter = (function(Arr) {

		var _input;

		function _onInputEvent(e) {
			_input = e.target;
			var tables = document.getElementsByClassName(_input.getAttribute('data-table'));
			Arr.forEach.call(tables, function(table) {
					Arr.forEach.call(table.tBodies, function(tbody) {
					Arr.forEach.call(tbody.rows, _filter);
				});
			});
		}

		function _filter(row) {
			var text = row.textContent.toLowerCase(), val = _input.value.toLowerCase();
			row.style.display = text.indexOf(val) === -1 ? 'none' : 'table-row';
		}

		return {
			init: function() {
				var inputs = document.getElementsByClassName('light-table-filter');
				Arr.forEach.call(inputs, function(input) {
					input.oninput = _onInputEvent;
				});
			}
		};
	})(Array.prototype);

	document.addEventListener('readystatechange', function() {
		if (document.readyState === 'complete') {
			LightTableFilter.init();
		}
	});

})(document);
</script>

<body>

<table width="100%"><tr bgcolor="#ffff00"><td align="center"> <b>Lista capi della seduta</b> </td></tr></table>
<% int columnCount = 0; %>
	<b>Filtra su un campo della tabella dei capi</b> <input type="search" class="light-table-filter" data-table="order-table" placeholder="Filtra">
	
<table id="table3" cellpadding="0" cellspacing="0"  width="100%" class="order-table table" >
<thead>
<tr><th>Numero Partita</th>
<th>Specie</th> 
<th>Matricola</th> 
<th>Capo selezionato</th> 
</thead>
<tbody><tr><td></td></tr>
</tbody>
</table>

<br/><br/>

<div align="right">
<a href="javascript:popURL('MacellazioneUnica.do?command=NuovaPartita&orgId=<%=request.getParameter("orgId")%>&popup=true', null, '800px', null, 'yes', 'yes')">
<span style="font-size: medium; font-family: trebuchet ms,geneva;">Inserisci nuova partita
</span>
</a>
</div>


<br/><br/>

<table width="100%"><tr bgcolor="#00ff00"><td align="center"> <b>Lista capi in attesa di macellazione </b> </td></tr></table>

<table id="table2" cellpadding="0" cellspacing="0"  width="100%">
    <tr>
			<th>Numero partita</th>
			<th colspan="2">Capi macellabili</th>
			<th>Codice azienda provenienza</th>
			<th>Mod. 4</th>
			<th>Data di arrivo al macello</th>
			
		</tr>		
   <%
	
	if (listaPartite.size()>0){
		for (int i=0;i<listaPartite.size(); i++){
			PartitaUnivoca partita = (PartitaUnivoca) listaPartite.get(i); 
			if (partita.getListaCapiMacellabiliNumeri().size()>0) {
			%>

				
			<tr>
			<td><a href="javascript:popURL('MacellazioneUnica.do?command=DettaglioPartita&idPartita=<%=partita.getId()%>&popup=true', null, '800px', null, 'yes', 'yes')"><%=partita.getNumeroPartita() %></a></td>
			<td>
			<%
			HashMap<String, Integer> grigliaSpecie= partita.getListaCapiMacellabiliNumeri();
			Iterator<String> itSpecie = grigliaSpecie.keySet().iterator();
			String numericapi = "";
			while(itSpecie.hasNext())
			{
			String specie = itSpecie.next(); 
			int num = grigliaSpecie.get(specie); 
			numericapi = numericapi+specie+": "+num+"; ";
			} %> 
			<%=numericapi %> &nbsp;</td>
				<td>
		<input type="button" id="gestisciCapi_<%=partita.getId()%>" onClick="openPopupCapiPartita('<%=partita.getId()%>')" value="AGGIUNGI CAPI DA QUESTA PARTITA" />
			</td> 
			<td><%=partita.getCodiceAziendaProvenienza() %> &nbsp; </td>
			<td><%=partita.getMod4() %> &nbsp; </td>
			<td><%=toDateasString(partita.getDataArrivoMacello()) %> &nbsp;  </td>
		
			</tr>

<% } }
%>
	
	
	<% } else {%>
		<tr><td colspan="8"> Non sono state trovate partite. &nbsp; </td></tr>
		
		<% } %>
</table>
  
  <br/><br/>
  
  
<script type='text/javascript'>//<![CDATA[ 

var table2_Props = {
	col_0: "select",
	col_1: "none",
	col_2: "none",
	col_5: "select",
    display_all_text: " [ Mostra tutte ] ",
    sort_select: true
};
var tf2 = setFilterGrid("table2", table2_Props);
//]]>  

</script>

<script type='text/javascript'>//<![CDATA[ 

var table3_Props = {
	col_0: "none",
	col_1: "none",
	col_2: "none",
	col_3: "none",
    col_4: "none",
  display_all_text: " [ Mostra tutte ] ",
    sort_select: true
};
var tf3 = setFilterGrid("table3", table3_Props);
//]]>  

</script>
</body>



</html>

