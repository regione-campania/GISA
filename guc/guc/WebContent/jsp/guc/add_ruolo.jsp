<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.endpointconnector.*"%>
<%@page import="it.us.web.bean.guc.Ruolo"%>
<%@page import="java.util.ArrayList"%>

<script>

function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
} 

function checkCheckbox(id, check){

var ruolo = document.getElementById('ruolo_'+id);
if (check.checked && !ruolo.checked)
	{
	alert('Abilitare prima il ruolo.');
	check.checked = false;
	return false;
	}
	
	
} 

 function checkForm(form) {
	  
	    formTest = true;
	    message = "";
	
	    


	    if ((  trim(form.nomeRuolo.value) == "")) {
	        message += "- Campo ruolo obbligatorio.\r\n";
	        formTest = false;
	    }
	    if ((  trim(form.descrizioneRuolo.value) == "")) {
	        message += "- Campo descrizione ruolo obbligatorio.\r\n";
	        formTest = false;
	    }
	
	    var ep = "";
    	for ( i = 0; i<10; i++){
    		var ruolo = document.getElementById("ruolo_"+i);
    		var label = document.getElementById("label_"+i);
    		var ruoloDaCopiare = document.getElementById("ruoloDaCopiare_"+i);

    		if (ruolo!=null && ruolo.checked){
    			ep = ep+label.innerHTML+" [Copiato da: "+ ruoloDaCopiare.options[ruoloDaCopiare.selectedIndex].innerHTML+ "];\n";
    			if (presenteInLista(i, form.nomeRuolo.value)==true){
    				formTest = false;
    				message+= "Il ruolo "+form.nomeRuolo.value+" esiste gia' nell'end point selezionato.";
    			}
    			}
    		
    		
    	}
    	if (ep==''){
    		formTest = false;
    		message += "- Nessun EndPoint selezionato.\r\n";
    	}
    		
	    
	    if (formTest == false) {
	      alert("La form non può essere salvata, si prega di verificare quanto segue:\r\n\r\n" + message);
	     
	      return false;
	    }
	    else {    	
	    	
	    	var nome = form.nomeRuolo.value;
	    	
	    	var msg = "";
	    	msg+= "ATTENZIONE. Sei sicuro di voler procedere a questo inserimento? \n\n";
	    	msg+= "Ruolo: "+nome+ "\n\n";
	    	msg+="Da inserire in: \n\n"+ep;
	    	
	    	
	    	
	    	
	    	if(confirm(msg)){
	    		loadModalWindow();
	      		return form.submit();
	    	}
	    	else {
	    		
	    		return false;
	    	}
	    }
  }
 
 
 function presenteInLista(indice, ruolo){
	
	 var select = document.getElementById("ruoloDaCopiare_"+indice);
	 var i = 0;
	 for (i=0; i<select.options.length;i++){
	 if (ruolo.toUpperCase() ==  select.options[i].innerHTML.toUpperCase())
			 return true;
		} 
	 return false;
	 }
	 

  </script>

<div align="center">
		<a href="Index.us" style="margin: 0px 0px 0px 50px">Indietro</a>
	</div>
<br/><br/>

<form name="addRuolo" action="guc.AddRuolo.us" onSubmit="return checkForm(this);" method="post">
<div id="content" align="center">

		
	<h4 class="titolopagina">Aggiungi ruolo</h4>
	
	

	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	
	<tr>
  		<td class="formLabel">Nome Ruolo</td>
  		<td><input type="text" name="nomeRuolo" id="nomeRuolo" value="" maxlength="25" /><font color=red>*</font></td>
	</tr>
	
	<tr>
  		<td class="formLabel">Descrizione Ruolo</td>
  		<td><input type="text" name="descrizioneRuolo" id="descrizioneRuolo" value="" size="100" maxlength="200" /><font color=red>*</font></td>
	</tr>
	



<% EndPointList listaEndPoint = (EndPointList) request.getAttribute("listaEndPoint");
 for (int i = 0; i<listaEndPoint.size(); i++){
	EndPoint ep = (EndPoint) listaEndPoint.get(i);
	if (ep.getId()!= EndPoint.GUC){
	%>

<tr><th colspan="2"> <label for="ruolo_<%=ep.getId() %>" id="label_<%=ep.getId()%>"><%= ep.getNome().toUpperCase()%></label> <input type="checkbox" id="ruolo_<%=ep.getId() %>" name="ruolo_<%=ep.getId() %>"/></th></tr>

<% if (ep.getId()== EndPoint.GISA) { %>

<tr><td colspan="2" align="center">
<input type="checkbox" id="inAccess_<%=ep.getId() %>" name="inAccess_<%=ep.getId() %>" onClick="checkCheckbox('<%=ep.getId()%>', this)"/> Access

<input type="checkbox" id="inNucleo_<%=ep.getId() %>" name="inNucleo_<%=ep.getId() %>" onClick="checkCheckbox('<%=ep.getId()%>', this)"/> Nucleo Ispettivo (<input type="checkbox" id="nucleoLista_<%=ep.getId() %>" name="nucleoLista_<%=ep.getId() %>" onClick="checkCheckbox('<%=ep.getId()%>', this)"/> Mostra lista)

<input type="checkbox" id="inDpat_<%=ep.getId() %>" name="inDpat_<%=ep.getId() %>" onClick="checkCheckbox('<%=ep.getId()%>', this)"/> DPAT

<input type="checkbox" id="isQualifica_<%=ep.getId() %>" name="isQualifica_<%=ep.getId() %>" onClick="checkCheckbox('<%=ep.getId()%>', this)"/> Qualifica
</td></tr> 

<%} else if (ep.getId()== EndPoint.GISA_EXT) {  %>
<tr><td colspan="2" align="center">
<input type="checkbox" id="inAccess_<%=ep.getId() %>" name="inAccess_<%=ep.getId() %>" onClick="checkCheckbox('<%=ep.getId()%>', this)"/> Access

<input type="checkbox" id="inNucleo_<%=ep.getId() %>" name="inNucleo_<%=ep.getId() %>" onClick="checkCheckbox('<%=ep.getId()%>', this)"/> Nucleo Ispettivo (<input type="checkbox" id="nucleoLista_<%=ep.getId() %>" name="nucleoLista_<%=ep.getId() %>" onClick="checkCheckbox('<%=ep.getId()%>', this)"/> Mostra lista)

<input type="checkbox" id="inDpat_<%=ep.getId() %>" name="inDpat_<%=ep.getId() %>" onClick="checkCheckbox('<%=ep.getId()%>', this)"/> DPAT

<input type="checkbox" id="isQualifica_<%=ep.getId() %>" name="isQualifica_<%=ep.getId() %>" onClick="checkCheckbox('<%=ep.getId()%>', this)"/> Qualifica
</td></tr> 
<%} else if (ep.getId()== EndPoint.BDU) {  %>
<tr><td colspan="2" align="center"><i>Nessuna informazione aggiuntiva</i></td></tr>

<%} else if (ep.getId()== EndPoint.VAM) {  %>
<tr><td colspan="2" align="center"><i>Nessuna informazione aggiuntiva</i></td></tr>
<%} else if (ep.getId()== EndPoint.IMPORTATORI) {  %>
<tr><td colspan="2" align="center"><i>Nessuna informazione aggiuntiva</i></td></tr>
<%} else if (ep.getId()== EndPoint.DIGEMON) {  %>
<tr><td colspan="2" align="center"><i>Nessuna informazione aggiuntiva</i></td></tr>
<%} %>



<tr><td colspan="2" align="center">Ruolo da cui copiare i permessi
<% ArrayList<Ruolo> listaRuoli = (ArrayList<Ruolo>)request.getAttribute("listaRuoli" + ep.getNome()); %>
<select name="ruoloDaCopiare_<%= ep.getId() %>" id="ruoloDaCopiare_<%= ep.getId() %>">
<optgroup label="<%= ep.getNome() %>">
<option value="-1">Default</option>
<%
if(listaRuoli.size() > 0){
for(Ruolo r : listaRuoli){ 
%>
<option value="<%= r.getRuoloInteger() %>"><%= r.getRuoloString() %></option>
<%	} } 
%>
</optgroup>
</select>
</td></tr>	

<% } } %>

	
<tr> <td colspan="2" align="center">
<input type="button" value="Inserisci" onclick="javascript: checkForm(this.form);"/>
</td>



</div>
</form>
</body>
 	

 <%@ include file="modalWindow.jsp"%>