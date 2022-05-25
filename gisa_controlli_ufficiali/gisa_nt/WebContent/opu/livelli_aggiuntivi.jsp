<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<jsp:useBean id="alberoLivelli" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="esito" class="java.lang.String" scope="request"/>

<%@ page import="org.aspcfs.modules.opu.base.*" %>
<%@ page import="org.aspcfs.modules.gestioneml.base.*" %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>


<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>


<script>
function myFunction() {
  // Declare variables
  var input, filter, table, tr, td, i;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("myTable");
  tr = table.getElementsByTagName("tr");

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[0];
    if (td) {
      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
}
</script>


<script>
function checkForm(form){
	loadModalWindow();
	form.submit();
}


</script>

<div class="documentaleNonStampare">
<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
</div>


<center>
<i>Livelli aggiuntivi configurabili sullo stabilimento: <br/>
<b><%=Stabilimento.getOperatore().getRagioneSociale() %></b></i></center>

<br/><br/>

<%if (esito!=null && esito.equalsIgnoreCase("ok")){ %>
<center><font color="green">Livelli salvati con successo.</font></center>
<%} %>

<%if (alberoLivelli.size()==0){ %>
<script>
alert('AL MOMENTO NON SONO DISPONIBILI LIVELLI AGGIUNTIVI PER QUESTE LINEE PRODUTTIVE');
window.close();
</script>
<%}  %>

<form name="searchAccount" action="OpuStab.do?command=SalvaLivelliAggiuntivi" method="post">

 <input type="text" id="myInput" onkeyup="myFunction()" placeholder="FILTRA">
<table width="100%" cellpadding="2" cellspacing="2"  border="1" id="myTable">
<thead></thead>
<tbody> 	
			
			<%String codice = "";
			int indice = 0;
			
			for (int i=0;i<alberoLivelli.size(); i++){%>
			
			<%
				LivelloAggiuntivo livello = (LivelloAggiuntivo) alberoLivelli.get(i);
				int idFoglia = livello.getId();
				boolean checked = false;
				checked = livello.isChecked();
			%>
			
			<%if (!codice.equals(livello.getCodiceLinea())){ %>
			<tr><td></td></tr>
			<tr style="background-color:yellow"><td align="center"><i><font size="3px"><%=livello.getDescrizioneLinea() %></font></i></td></tr>
			<tr><td></td></tr>
			<% codice = livello.getCodiceLinea();
			} %>
			
		
		<tr style="background-color:<%=(indice%2==0) ? "#d6d6d6" : "#e8e8e8"%>"><td>
		
			<font size="5px"><%=livello.getNome() %></font> <br/>
			
			<% 
			LivelloAggiuntivo livelloFiglio = livello.getLivelloFiglio();
			if (livelloFiglio!=null){
			idFoglia = livelloFiglio.getId();
			checked = livelloFiglio.isChecked();%>
			<font size="4px"><%=livelloFiglio.getNome() %></font> <br/>
			
			<% 
			LivelloAggiuntivo livelloFiglio2 = livelloFiglio.getLivelloFiglio();
			if (livelloFiglio2!=null){
			idFoglia = livelloFiglio2.getId();
			checked = livelloFiglio2.isChecked();%>
			<font size="3px"><%=livelloFiglio2.getNome() %></font> <br/>
			
			<% 
			LivelloAggiuntivo livelloFiglio3 = livelloFiglio2.getLivelloFiglio();
			if (livelloFiglio3!=null){
			idFoglia = livelloFiglio3.getId();
			checked = livelloFiglio3.isChecked();%>
			<font size="2px"><%=livelloFiglio3.getNome() %></font> <br/>
			<%}%>
			
			<%}%>
			
			<%}%>
			<input type="hidden" id="idIstanza<%=indice %>"  name="idIstanza<%=indice %>" value="<%=livello.getIdIstanza()%>"/>
			<input type="hidden" id="idFoglia<%=indice %>"  name="idFoglia<%=indice %>" value="<%=idFoglia%>"/>
			<input type="checkbox" id="<%=idFoglia %>" name="<%=idFoglia %>" <%=(checked) ? "checked"  : "" %>/> 
			 
			<br/><br/>
			</td></tr>		
			
			<%indice++;}%>
			
			<input type="hidden" id="sizeAlbero" name="sizeAlbero" value="<%=indice%>"/>
			<input type="hidden" id="stabId" name="stabId" value="<%=Stabilimento.getIdStabilimento()%>"/>
			
				
<input type="button" onClick="checkForm(this.form)" value="Salva"/>
</tbody>
</table>
</form>
