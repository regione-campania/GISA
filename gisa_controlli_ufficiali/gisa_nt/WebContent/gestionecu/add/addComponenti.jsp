<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="ListaComponenti" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="Qualifica" class="org.aspcfs.modules.gestionecu.base.Qualifica" scope="request"/>

<%@ page import="org.aspcfs.modules.gestionecu.base.Componente"%>
<%@ page import="java.util.ArrayList"%>

<%@ include file="../../initPage.jsp" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>


<script>

function setComponente(val){
	
	var componenteId = val.value;
	var componenteNome = val.getAttribute("nominativo");
	var strutturaId =  val.getAttribute("idStruttura");
	var strutturaNome = val.getAttribute("nomeStruttura");
	var qualificaId =  val.getAttribute("idQualifica");
	var qualificaNome =  val.getAttribute("nomeQualifica");
	
	if (window.opener.document.getElementById("div_componente_"+componenteId)!=null) {
		alert('attenzione. Componente risulta gia selezionato');
		val.checked = false;
		return false;
	}


	var divQualifica = window.opener.document.getElementById("div_"+qualificaId);
	var divComponente = '<div id="div_componente_'+componenteId+'"> '+componenteNome+ ' ('+ strutturaNome + ') <input type="text" readonly id="componenteId_'+componenteId+'" name="componenteId" value="'+componenteId+'"/><input type="text" readonly id="componenteNome_'+componenteId+'" name="componenteNome_'+componenteId+'" value="'+componenteNome+'"/><input type="text" readonly id="componenteQualificaId_'+componenteId+'" name="componenteQualificaId_'+componenteId+'" value="'+qualificaId+'"/><input type="text" readonly id="componenteQualificaNome_'+componenteId+'" name="componenteQualificaNome_'+componenteId+'" value="'+qualificaNome+'"/><input type="text" readonly id="componenteStrutturaId_'+componenteId+'" name="componenteStrutturaId_'+componenteId+'" value="'+strutturaId+'"/><input type="text" readonly id="componenteStrutturaNome_'+componenteId+'" name="componenteStrutturaNome_'+componenteId+'" value="'+strutturaNome+'"/><input type ="button" style="background: red" value="X" onClick="eliminaComponente('+componenteId+')"/></div>'; 
	divQualifica.innerHTML = divQualifica.innerHTML + divComponente;
	window.close();
}
	
function filtraRigheComponenti() {
	  // Declare variables
	  var input, filter, table, tr, td, i, txtValue;
	  input1 = document.getElementById("myInputStruttura");
	  input2 = document.getElementById("myInputNominativo");
	  filter1 = input1.value.toUpperCase();
	  filter2 = input2.value.toUpperCase();
	  
	  table = document.getElementById("tableComponenti");
	  tr = table.getElementsByTagName("tr");

	  // Loop through all table rows, and hide those who don't match the search query
	  for (i = 0; i < tr.length; i++) {
	    td0 = tr[i].getElementsByTagName("td")[0];
	    td1 = tr[i].getElementsByTagName("td")[1];
	    td2 = tr[i].getElementsByTagName("td")[2];
	    td3 = tr[i].getElementsByTagName("td")[3];
	    
	    if (td0) {
	      txtValue0 = td0.textContent || td0.innerText;
	      txtValue1 = td1.textContent || td1.innerText;
	      txtValue2 = td2.textContent || td2.innerText;
	      txtValue3 = td3.textContent || td3.innerText;
	      
	      if (txtValue1.toUpperCase().indexOf(filter1) > -1 && txtValue2.toUpperCase().indexOf(filter2) > -1 ) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }
	  }
	}


function anagrafaComponente(){
	var indice = -1;
	var idQualifica = '<%=Qualifica.getId()%>';
	var nomeQualifica = '<%=Qualifica.getNome()%>';

	  window.open('NucleoIspettivo.do?command=AnagrafaComponenteNucleo&idQualifica='+idQualifica+'&nomeQualifica='+nomeQualifica+'&indice='+indice,'popupSelect',
      'height=300px,width=400px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>

<%if (!Qualifica.isViewListaComponenti()){ %>
<br/><center><input id="bottoneAggiungi" type="button" value="ANAGRAFA NUOVO COMPONENTE NUCLEO" onClick="anagrafaComponente()"/></center><br/>
<%} %>


<table class="details" id ="tableComponenti" name="tableComponenti" cellpadding="10" cellspacing="10" width="100%" style="border-collapse: collapse">
<col width="10%"><col width="20%"><col width="60%">
<tr>
<th>QUALIFICA</th>
<th>STRUTTURA</th>
<th>NOMINATIVO</th>
<th></th>
</tr>

<tr>
<th></th>
<th><input type="text" id="myInputStruttura" onkeyup="filtraRigheComponenti()" placeholder="FILTRA STRUTTURA" size="20"></th>
<th><input type="text" id="myInputNominativo" onkeyup="filtraRigheComponenti()" placeholder="FILTRA NOMINATIVO" size="20"></th>
<th></th>
</tr>
	
<% 
for (int i = 0; i<ListaComponenti.size(); i++) {
Componente com = (Componente) ListaComponenti.get(i);  %>
	
<tr>
<td><%= com.getNomeQualifica() %></td>
<td><%= com.getNomeStruttura() %></td>
<td><%= com.getNominativo() %></td>
<td>
<input type="radio" onClick="setComponente(this)" nominativo="<%=com.getNominativo().replaceAll("\"", "")%>" nomeStruttura="<%=com.getNomeStruttura().replaceAll("\"", "")%>" idStruttura="<%=com.getIdStruttura()%>" nomeQualifica="<%=com.getNomeQualifica().replaceAll("\"", "")%>" idQualifica="<%=com.getIdQualifica()%>" value="<%=com.getId()%>"/>
</td>

</tr>	
	
<%} %>	
	
</table>



