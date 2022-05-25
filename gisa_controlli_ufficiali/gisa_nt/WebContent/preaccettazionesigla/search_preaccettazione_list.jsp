<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="lista_preac" class="org.json.JSONArray" scope="request"/>
  <%@ include file="../initPage.jsp" %>


<script src="javascript/vendor/moment.min.js"></script>

<script>
function apriCampione(idCampione){
	loadModalWindow();
	window.location.href ='Vigilanza.do?command=CampioneDetails&id='+idCampione;
}
</script>


<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ReportPreaccettazione.do?command=SearchForm">CERCA PREACCETTAZIONE</a> > RISULTATI RICERCA
		</td>
	</tr>
</table>
<center>
<h3>Risultati ricerca</h3>
<%if(lista_preac.length() > 0){ %>
<table class="table details" style="border-collapse: collapse" border="1" width="95%" cellpadding="2">
	<tr>
		<th style="text-align:center"><p>codice preaccettazione</p></th>
		<th style="text-align:center"><p>data generazione</p></th>	
		<th style="text-align:center"><p>stato preaccettazione</p></th>
		<th style="text-align:center"><p>identificativo campione</p></th>
		<th style="text-align:center; width: 35%"><p>dati osa e linea</th>
		<th style="text-align:center"><p>asl</p></th>
		<th style="text-align:center"><p>utente</p></th>	
	</tr>


	<%for(int i=0; i < lista_preac.length(); i++){ %>
		<tr class="row<%=i%2%>">
			<td align="center">
				<p>
					<%if(!lista_preac.getJSONObject(i).getString("codpreacc").equalsIgnoreCase("null")){ %>
						<%=lista_preac.getJSONObject(i).get("codpreacc") %>
					<% } %>
				</p>
			</td>
			<td align="center">
				<p>
					<%if(!lista_preac.getJSONObject(i).getString("data_generazione").equalsIgnoreCase("null")){ %>
						<%=lista_preac.getJSONObject(i).get("data_generazione") %>
					<% } %>
				</p>
			</td>
			<td align="center">
				<p>
					<%if(!lista_preac.getJSONObject(i).getString("stato_preaccettazione").equalsIgnoreCase("null")){ %>
						<%=lista_preac.getJSONObject(i).get("stato_preaccettazione") %>
					<% } %>
					<%if(lista_preac.getJSONObject(i).getString("stato_preaccettazione").equalsIgnoreCase("LETTO DA SIGLA")) { %>
						<br>data lettura: <%=lista_preac.getJSONObject(i).get("data_lettura") %>
						<br>ip lettore: <%=lista_preac.getJSONObject(i).get("ip_lettura") %>
					<%} %>
				</p>
			</td>
			<td align="center">
				<p> <a href="#" onclick="apriCampione('<%=!lista_preac.getJSONObject(i).getString("id_campione").equalsIgnoreCase("null") ? lista_preac.getJSONObject(i).getString("id_campione") : "-1"%>')">
					<%if(!lista_preac.getJSONObject(i).getString("id_cu").equalsIgnoreCase("null")){ %>
						id CU: <%=lista_preac.getJSONObject(i).get("id_cu") %>
					<% } %>
					<br>
					<%if(!lista_preac.getJSONObject(i).getString("codice_campione").equalsIgnoreCase("null")){ %>
						codice campione: <%=lista_preac.getJSONObject(i).get("codice_campione") %>
					<% } %>
					<br>
					<%if(!lista_preac.getJSONObject(i).getString("numero_verbale").equalsIgnoreCase("null")){ %>
						numero verbale: <%=lista_preac.getJSONObject(i).get("numero_verbale") %>
					<% } %>
					</a>
				</p>
			</td>
			<td align="center">
				<p>
					<%if(!lista_preac.getJSONObject(i).getString("ragione_sociale").equalsIgnoreCase("null")){ %>
						<b><%=lista_preac.getJSONObject(i).get("ragione_sociale") %></b>
					<% } %>
					<br>
					<%if(!lista_preac.getJSONObject(i).getString("attivita").equalsIgnoreCase("null")){ %>
						linea di attivita': <%=lista_preac.getJSONObject(i).get("attivita") %>
					<% } %>
					<br>
					<%if(!lista_preac.getJSONObject(i).getString("num_registrazione").equalsIgnoreCase("null")){ %>
						identificativo osa: <%=lista_preac.getJSONObject(i).get("num_registrazione") %>
					<% } %>
					<br>
					<%if(!lista_preac.getJSONObject(i).getString("partita_iva").equalsIgnoreCase("null")){ %>
						P. IVA/cod. fiscale impresa: <%=lista_preac.getJSONObject(i).get("partita_iva") %>
					<% } %>
					<br>
					<%if(!lista_preac.getJSONObject(i).getString("comune").equalsIgnoreCase("null")){ %>
						COMUNE: <%=lista_preac.getJSONObject(i).get("comune") %>
					<% } %>
				</p>
			</td>
			<td align="center">
				<p>
					<%if(!lista_preac.getJSONObject(i).getString("asl").equalsIgnoreCase("null")){ %>
						<%=lista_preac.getJSONObject(i).get("asl") %>
					<% } %>
				</p>
			</td>
			<td align="center">
				<p>
					<%if(!lista_preac.getJSONObject(i).getString("utente").equalsIgnoreCase("null")){ %>
						<%=lista_preac.getJSONObject(i).get("utente") %>
					<% } %>
				</p>
			</td>
			
		</tr>
	<%} %>
</table>
<% } else {%>
Nessun risultato ottenuto, controllare i filtri inseriti!
<%} %>
</center>
