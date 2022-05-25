<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>

<jsp:useBean id="SchedaOperatore" class="org.aspcfs.modules.schedeCentralizzate.base.SchedaCentralizzata" scope="request"/>
<jsp:useBean id="tipoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipo" class="java.lang.String" scope="request"/>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<link rel="stylesheet" documentale_url="" href="schede_centralizzate/dettaglio_screen.css" type="text/css" media="screen" />

<%! public static String checkNull(String string)
  {
	  if (string == null)
		  return "";
	  else 
		  return string;
  }%>
  
  <script type="text/javascript" src="dwr/interface/DwrSchedaCentralizzata.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>


<script> 

function annulla(form){
	form.action ="SchedaCentralizzataAction.do?command=DettaglioGestioneScheda";
	form.submit();
}


function salva(form){
	form.submit();
}

function provaQuery (id){
	
	var sql_campo = document.getElementById("sql_campo_"+id).value;
	var sql_origine = document.getElementById("sql_origine_"+id).value;
	var sql_condizione = document.getElementById("sql_condizione_"+id).value;
	var object_id = document.getElementById("testvalue_"+id).value;
	
	DwrSchedaCentralizzata.provaQuery(sql_campo, sql_origine, sql_condizione, object_id,{callback:provaQueryCallBack,async:false});
	
}
function provaQueryCallBack(val)
{
	alert(val);
	}
</script>

<script>
function gestisciRiga (label, sql_campo, sql_origine, sql_condizione, attributo, destinazione, ordine){
	
		var campoIndice = document.getElementById("indice");
		var numeroIndice =campoIndice.value;
		creaRiga(numeroIndice, label, sql_campo, sql_origine, sql_condizione, attributo, destinazione, ordine);
		var nuovoIndice = parseInt(numeroIndice)+1;
		campoIndice.value = nuovoIndice;
		
}

function creaRiga(id, label, sql_campo, sql_origine, sql_condizione, attributo, destinazione, ordine){
	
	var table = document.getElementById("dati");

	// Create an empty <tr> element and add it to the 1st position of the table:
	var row = table.insertRow(table.rows.length);

	// Insert new cells (<td> elements) at the 1st and 2nd position of the "new" <tr> element:
	var cell0 = row.insertCell(0);
	var cell1 = row.insertCell(1);
	var cell2 = row.insertCell(2);
	var cell3 = row.insertCell(3);
	var cell4 = row.insertCell(4);
	var cell5 = row.insertCell(5);
	var cell6 = row.insertCell(6);
	var cell7 = row.insertCell(7);
	var cell8 = row.insertCell(8);
	var cell9 = row.insertCell(9);
	
	

	// Add some text to the new cells:
	cell0.innerHTML =  "<%=tipoList.getSelectedValue(tipo) %>" + "<input type=\"hidden\" id=\"id_"+id+"\" name=\"id_"+id+"\" value=\"-1\"/>";
	cell1.innerHTML = "<textarea id=\"label_"+id+"\" name=\"label_"+id+"\">"+label+"</textarea>";
	cell2.innerHTML = "<textarea type=\"text\" id=\"sql_campo_"+id+"\" name=\"sql_campo_"+id+"\" >"+sql_campo+"</textarea>";
	cell3.innerHTML = "<textarea id=\"sql_origine_"+id+"\" name=\"sql_origine_"+id+"\">"+sql_origine+"</textarea>";
	cell4.innerHTML = "<textarea id=\"sql_condizione_"+id+"\" name=\"sql_condizione_"+id+"\">"+sql_condizione+"</textarea>";
	cell5.innerHTML = "<textarea id=\"attributo_"+id+"\" name=\"attributo_"+id+"\">"+attributo+"</textarea>"
	cell6.innerHTML = "<textarea id=\"destinazione_"+id+"\" name=\"destinazione_"+id+"\">"+destinazione+"</textarea>"
	cell7.innerHTML = "<input type=\"text\" id=\"ordine_"+id+"\" name=\"ordine_"+id+"\" value=\""+ordine+"\"/>";
	cell8.innerHTML = "<input type=\"text\" id=\"testvalue_"+id+"\" \name=\"testvalue_"+indice+"\" value=\"51251\"/><input type=\"button\" id=\"test_"+id+"\" name=\"test_"+id+"\" onClick=\"provaQuery('"+id+"')\" value=\"PROVA QUERY\"/>";
	cell9.innerHTML = "<input type=\"hidden\" id=\"enabled_"+id+"\" name=\"enabled_"+id+"\" checked value=\"on\" onClick=\"disabilitaRiga(this)\" /> <input type=\"button\" id=\"cancella_"+id+"\" name=\"cancella_"+id+"\" onClick=\"cancellaRiga(this)\" value=\"X\"/>";
	
}

function disabilitaRiga(checkbox){
	if (checkbox.checked){
		checkbox.parentNode.parentNode.setAttribute('class', '');
		checkbox.value ='on';
	}
	else {
		checkbox.parentNode.parentNode.setAttribute('class', 'red');
		checkbox.value ='off';
	}
}

function cancellaRiga(bottone){
	//Rimuovo i div di piani che non sono più selezionati
	var table = document.getElementById("dati");
	 var i = bottone.parentNode.parentNode.rowIndex;
	 table.deleteRow(i);
	  }
	  
function clonaRiga(){
	
	  var res;
      var result;
      	  window.open('SchedaCentralizzataAction.do?command=DettaglioGestioneScheda&operazione=clona','popupSelect',
            'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
	
function openLegenda(){
	
	  var res;
    var result;
    	  window.open('schede_centralizzate/legenda.jsp','popupSelect',
          'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
</script>

<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

<body onload="resizeGlobalItemsPane('hide')">

<table style="border:1px solid black" align="center">
<tr><td>
<input type="button" value="Legenda" onClick="openLegenda()"/> 
</td></tr>
</table>



<form method="post" name="form2" action="SchedaCentralizzataAction.do?command=UpdateGestioneScheda">

<table cellpadding='0' class="imagetable" id="dati"> 
<thead>
<tr> 
<th>Tipo Operatore <br/> 
<%=tipo%> - <%=tipoList.getSelectedValue(tipo) %>
<input type="hidden" id="tipo" name="tipo" value="<%=tipo%>"/>
</th> 
<th>Label</th> <th>Campo da recuperare <br/> SELECT </th> <th>Tabella di origine<br/> FROM</th> <th> Condizione da applicare <br/> WHERE</th> <th>Tipo campo</th>  <th>Destinazione</th>  <th>Ordine</th> <th>Test</th> <th>Enabled</th> </tr>
</thead>
<tbody>
<% 
LinkedHashMap<String,String[]> listaElementi = SchedaOperatore.getListaElementi();
int i = 0;
for(Map.Entry<String, String[]> elemento : listaElementi.entrySet()){
	%>
<tr>
<td><%=tipoList.getSelectedValue(elemento.getValue()[0]) %> <input type="hidden" id="id_<%=i %>" name="id_<%=i %>" value="<%=elemento.getKey()%>"/></td>
<td><textarea id="label_<%=i %>" name="label_<%=i %>"><%=checkNull(elemento.getValue()[4])%></textarea></td>
<td><textarea type="text" id="sql_campo_<%=i %>" name="sql_campo_<%=i %>" ><%=checkNull(elemento.getValue()[1])%></textarea></td>
<td><textarea id="sql_origine_<%=i %>" name="sql_origine_<%=i %>"><%=checkNull(elemento.getValue()[2])%></textarea></td>
<td><textarea id="sql_condizione_<%=i %>" name="sql_condizione_<%=i %>"><%=checkNull(elemento.getValue()[3])%></textarea></td>
<td><textarea id="attributo_<%=i %>" name="attributo_<%=i %>"><%=checkNull(elemento.getValue()[5])%></textarea></td>
<td><textarea id=destinazione_<%=i %>" name="destinazione_<%=i %>"><%=checkNull(elemento.getValue()[8])%></textarea></td>
<td><input type="text" id="ordine_<%=i %>" name="ordine_<%=i %>" value="<%=checkNull(elemento.getValue()[6])%>"/></td>
<td><input type="text" id="testvalue_<%=i %>" name="testvalue_<%=i %>" value="51251"/> 
<input type="button" id="test_<%=i %>" name="test_<%=i %>" onClick="provaQuery('<%=i %>')" value="PROVA QUERY"/></td>
<td><input type="checkbox" id="enabled_<%=i %>" name="enabled_<%=i %>" <%=elemento.getValue()[7].equals("t") ? "checked" : "" %> onClick="disabilitaRiga(this)" value="on"/> </td>

<script>
disabilitaRiga(document.getElementById("enabled_<%=i%>"));
</script>

</tr>
<% i++;
} %>
</tbody>
</table>
<input type="button" value="AGGIUNGI UNA RIGA" onClick="gestisciRiga('', '', '', '', '', '')"/> 
<input type="button" value="CLONA UNA RIGA" onClick="clonaRiga()"/> 

<input type="hidden" id="indice" name="indice" value="<%=listaElementi.size()%>"/>



<table style="border:1px solid black" align="center">
<tr><td>
<input type="button" value="ANNULLA" onClick="if (confirm('Sicuro di voler ANNULLARE?')) {annulla(this.form)}"/> &nbsp;&nbsp;&nbsp;
</td><td>
&nbsp;&nbsp;&nbsp;<input type="button" value="SALVA" onClick="salva(this.form)"/>
</td></tr>
</table>



</form>
</body>