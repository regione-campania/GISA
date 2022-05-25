<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Set"%>
<%@page import="org.aspcfs.modules.lineeattivita.base.LineeCampiEstesi"%>
<%@page import="java.util.Map.Entry"%>
<jsp:useBean id="LineeCampiEstesi" class="java.util.LinkedHashMap" scope="request"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>

<script>
function showHideCampiEstesi(){
	var table = document.getElementById("tableDatiEstesi");
	if (table.style.display=='block')
		table.style.display='none';
	else
		table.style.display='block';
}

function openPopupModificaCampiEstesi(form){
	  window.open('OpuStab.do?command=PrepareModificaLineeCampiEstesi&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>','popupSelect',
	              'height=800px,width=500px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
		
		
</script>

<% if (LineeCampiEstesi.size()>0){ %>

<br/><br/>

<div align="right">
<input type="button" value="Mostra/Nascondi dati estesi per linee di attività" onClick="showHideCampiEstesi()" id="bottoneEstesi"/>
</div>

<br/>

<div id ="tableDatiEstesi" style="display:none">

<!-- <center> -->
<!-- <table> -->
<!-- <tr><td valign="center"> -->
<!-- <img src="images/icons/edit.png" height="20"/>  -->
<!-- </td><td valign="center"> -->
<!-- <a href="#" onClick="openPopupModificaCampiEstesi(); return false">Modifica</a> -->
<!-- </td></tr> -->
<!-- </table> -->
<!-- </center> -->

<br/>



<%
boolean esistonoCampiEstesi = false;
Iterator<Integer> itKeySet = LineeCampiEstesi.keySet().iterator();
while (itKeySet.hasNext())
{
	
	%>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	
	
	<tr><th colspan="2" >DESCRIZIONE LINEA ATT.</th></tr>
	<%
	int idRelStabLp = itKeySet.next();
	LinkedHashMap entryLp = (LinkedHashMap ) LineeCampiEstesi.get(idRelStabLp);
	
	Set<Entry> entries =entryLp.entrySet();
	
	for (Entry elemento : entries) 
	{	esistonoCampiEstesi = true;
		LineeCampiEstesi campo = (LineeCampiEstesi) elemento.getValue();

%>




<tr><td class="formlabel"> 
<%=campo.getLabel_campo() %>
</td>

<td>
<%if (campo.getTipo_campo().equals("boolean")){ %>
<input type="checkbox" disabled <%=(campo.getValore_campo().equals("true")) ? "checked" : ""%>/>
<%} else {%>
<%=campo.getValore_campo() %>
<%} %>
</td></tr>

<% } %>


</table>


<%
	}%>
	</div>
	
<%if (!esistonoCampiEstesi) {%>
<script>
document.getElementById("bottoneEstesi").style.display="none";
</script>

<%} %>	
	<br/>
	<%}%>


