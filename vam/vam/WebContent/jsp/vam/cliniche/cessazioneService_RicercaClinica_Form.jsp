<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.util.json.JSONArray"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="it.us.web.util.json.JSONObject"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@ page import="java.text.SimpleDateFormat" %>


<script>

function checkForm(form){
	if (form.nomeClinica.value=='' && form.aslClinica.value=='-1')
		alert('Indicare almeno un filtro di ricerca tra nome ed ASL');
	else
		form.submit();
	
}
</script>

<form action="cessazioneService_RicercaClinica_Output.jsp" method="post">


<table>
	<tr><th>Nome Clinica</th> 
	<td><input type="text" id="nomeClinica" name="nomeClinica" value=""/></td>
	</tr>
	
	<tr>
	<th>ASL Clinica</th>
	<td>
	<select id="aslClinica" name="aslClinica">
	<option value="-1">Tutte</option>
	<option value="201">Avellino</option>
	<option value="202">Benevento</option>
	<option value="203">Caserta</option>
	<option value="204">Napoli 1 Centro</option>
	<option value="205">Napoli 2 Nord</option>
	<option value="206">Napoli 3 Sud</option>
	<option value="207">Salerno</option>
	</select> 
	 </td>
	</tr>
	
	<tr>
	<th>Stato Clinica</th>
	<td>
	<select id="statoClinica" name="statoClinica">
	<option value="-1">Tutte</option>
	<option value="0">Attive</option>
	<option value="1">Cessate</option>
	</select> 
	 </td>
	</tr>
		
	<tr><td colspan="2"><input type="button" value="ESEGUI" onClick="checkForm(this.form)"/></td></tr>


</table>
</form>





	 