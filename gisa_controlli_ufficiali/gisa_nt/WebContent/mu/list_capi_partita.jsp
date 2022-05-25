<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <jsp:useBean id="partita"			class="org.aspcfs.modules.mu.base.PartitaUnivoca" scope="request" />
<%@page import="org.aspcfs.modules.mu.base.CapoUnivoco"%>

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
function gestisciAggiuntaTotale(campo, specie, partita, numero){
	var x = document.getElementsByName("capo_"+partita+"_"+specie);	
	
	if (campo!=null){
			 if (campo.checked){
					for (var i =0; i<x.length; i++){
							x[i].checked = true;
							x[i].parentNode.parentNode.setAttribute('class', 'rowmusel');
					}
					document.getElementById("specie_num_"+specie+"_"+partita).value = i;
				 }
			 else {
					for (var i =0; i<x.length; i++){
							 x[i].checked = false;
							 x[i].parentNode.parentNode.setAttribute('class', 'rowmu'+specie);
					}
					 var numero = document.getElementById("specie_num_"+specie+"_"+partita).value = 0;
				 }
	}
	else{
		var num = numero.value;
		
		if (num>x.length){
			num = x.length;
			numero.value = num;
		}
		
		for (var i =0; i<num; i++){
			x[i].checked = true;
			x[i].parentNode.parentNode.setAttribute('class', 'rowmusel');
		}
		for (var i =num; i<x.length; i++){
			 x[i].checked = false;
			 x[i].parentNode.parentNode.setAttribute('class', 'rowmu'+specie);
		}
		
	}
 }
 
 function gestisciAggiuntaSingola(campo, specie, partita){
		 if (campo.checked){
			 campo.parentNode.parentNode.setAttribute('class', 'rowmusel');
		 }
		 else {
			 document.getElementById("specie_"+specie+"_"+partita).checked = false;
			 campo.parentNode.parentNode.setAttribute('class', 'rowmu'+specie);
		 }
		 }
 
 function checkCapi(){
	 var capiSelezionati = new Array();
	 var capiNonSelezionati = new Array();
	 var i = 0;
	 while (document.getElementById("capo_"+i)!=null){
		 if (document.getElementById("capo_"+i).checked){
			 var idcapo = document.getElementById("capo_"+i+"_id").value;
			 var speciecapo = document.getElementById("capo_"+i+"_specie").value;
			 var idspeciecapo = document.getElementById("capo_"+i+"_idspecie").value;
			 var matricola = document.getElementById("capo_"+i+"_matricola").value;
			 var partita = document.getElementById("numero_partita").value;
			 var idpartita = document.getElementById("id_partita").value;
			 var rigaCapo = [partita, idpartita, idcapo, speciecapo, idspeciecapo, matricola];
			 capiSelezionati.push(rigaCapo)
		 }
		 else{
			 var idcapo = document.getElementById("capo_"+i+"_id").value;
			 var idspeciecapo = document.getElementById("capo_"+i+"_idspecie").value;
			 var rigaCapo = [idcapo, idspeciecapo];
			 capiNonSelezionati.push(rigaCapo)
		 }
	i++;	 
 }

	 window.opener.cancellaDatiCapi(capiNonSelezionati);
	window.opener.aggiungiDatiCapi(capiSelezionati);
	window.close();
	 
 }
 
 function controllaSelezione(i, idcapo){
	var campo = document.getElementById("capo_"+i);
	 if (window.opener.document.getElementById("capo_"+idcapo)!=null){
		 campo.checked="checked";
		 campo.parentNode.parentNode.className="rowmusel";
	 }
 }
		 
 function selezionaNumCapi(specie,partita){
	 var numero = document.getElementById("specie_num_"+specie+"_"+partita);
	 gestisciAggiuntaTotale(null, specie, partita, numero);
 }
 
 </script>
		 
		 
<input type="button" value="CONFERMA CAPI" onClick="checkCapi()"/> 
		 
<table id="tabellaCapiPartita" cellpadding="0" cellspacing="0"  width="100%" class="order-table table" >
<thead>
<tr><th>Numero Partita</th>
<th>Specie</th> 
<th>Matricola</th> 
<th>Aggiungi singolo capo</th> 
<th>Selezione multipla</th></tr>
</thead>
<tbdody>
<input type="hidden" id="numero_partita" value="<%=partita.getNumeroPartita() %>"/>
<input type="hidden" id="id_partita" value="<%=partita.getId() %>"/>
<% 
int specie = -1; 
for (int i =0; i<partita.getListaCapiMacellabili().size(); i++){
CapoUnivoco capo = (CapoUnivoco) partita.getListaCapiMacellabili().get(i);
%>

<input type="hidden" id="capo_<%=i %>_id" value="<%=capo.getId() %>"/>
<input type="hidden" id="capo_<%=i %>_matricola" value="<%=capo.getMatricola() %>"/>
<input type="hidden" id="capo_<%=i %>_idspecie" value="<%=capo.getSpecieCapo() %>"/>
<input type="hidden" id="capo_<%=i %>_specie" value="<%=capo.getSpecieCapoNome() %>"/>

<tr class="rowmu<%=capo.getSpecieCapo()%>">
<td><%=partita.getNumeroPartita() %></td>
<td><%=capo.getSpecieCapoNome() %> &nbsp;</td>
<td><%=capo.getMatricola() %> &nbsp;</td>
<td><input type="checkbox" id="capo_<%=i %>" name="capo_<%=capo.getIdPartita() %>_<%=capo.getSpecieCapo() %>" onClick="gestisciAggiuntaSingola(this, '<%=capo.getSpecieCapo() %>', '<%=capo.getIdPartita()%>')" /></td>
<script>controllaSelezione('<%=i%>', '<%=capo.getId() %>')</script>
<td>
<% if (specie!=capo.getSpecieCapo()){ %>
<input type="checkbox" id="specie_<%=capo.getSpecieCapo() %>_<%=capo.getIdPartita() %>" name="specie_<%=capo.getSpecieCapo() %>" onClick="gestisciAggiuntaTotale(this, '<%=capo.getSpecieCapo() %>', '<%=capo.getIdPartita()%>', '-1')"/> Seleziona tutti i capi di questa specie <br/>
Seleziona <input type ="text" id="specie_num_<%=capo.getSpecieCapo() %>_<%=capo.getIdPartita() %>" size="2" maxlength="3"  onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"  name="specie_num_<%=capo.getSpecieCapo() %>" value="0"/> capi di questa specie <input type="button" onClick="selezionaNumCapi('<%=capo.getSpecieCapo() %>', '<%=capo.getIdPartita() %>')" value="ok"/>
<% } %>
&nbsp;  </td>
</tr>
<%
specie = capo.getSpecieCapo(); 
} %>



</tbdody>
</table>

<script type='text/javascript'>//<![CDATA[ 

var tabellaCapiPartita_Props = {
	col_0: "none",
	col_1: "select",
	col_3: "none",
    col_4: "none",
  display_all_text: " [ Mostra tutte ] ",
    sort_select: true
};
var tf3 = setFilterGrid("tabellaCapiPartita", tabellaCapiPartita_Props);
//]]>  

</script>
