<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.*"%>
<%@page import="it.us.web.bean.guc.Ruolo"%>
<jsp:useBean id="UserRecord" class="it.us.web.bean.guc.Utente" scope="request"/>
<jsp:useBean id="timeout" class="java.lang.String" scope="request" />
<jsp:useBean id="endpoints_ko" class="java.util.ArrayList" scope="request" />
<%@page import="it.us.web.util.json.JSONObject"%>

<div id="content" align="center">

	<div align="center">
		<a href="Home.us" style="margin: 0px 0px 0px 50px"><img src="images/lista.png" height="18px" width="18px" />Lista Utenti</a>
		<a href="guc.Detail.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/detail.gif" height="18px" width="18px" />Dettaglio Utente</a>
		<a href="guc.ToEditAnagrafica.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/edit.gif" height="18px" width="18px" />Modifica Credenziali</a> 
		<a href="guc.ToAdd.us" style="margin: 0px 0px 0px 50px; display: none"><img src="images/add.png" height="18px" width="18px" />Aggiungi Utente</a>
	</div>

	<h4 class="titolopagina">Riattivazione credenziali</h4>
	
	<form name="enableUser" action="guc.Enable.us" method="post">
	
		<input type="hidden" name="tipo" id="tipo" value="" ></input>
		<input type="hidden" name="endpoint" id="endpoint" value="" ></input>
		<%if(UserRecord.isEnabled() && endpoints_ko.size() > 0){ %>
		<h4>Utente attualmente attivo in GUC. Di seguito saranno elencati le qualifiche per cui l'utente <font color="red"><%= UserRecord.getUsername() %></font> risulta disattivato (ovvero non ha effettuato
		l'accesso negli ultimi <%= (String) request.getAttribute("timeout")%> mesi).<br></h4>
	
		<table width="40%" style="text-align: center;">
		
		<tr>
			<th width="50%">Qualifica</th>
			<th width="50%">Azione</th>
		</tr>
		<% int j =1; 
		
			while(j <=endpoints_ko.size() )
			{
				String endp = endpoints_ko.get(j-1).toString();
		%>		
			<td>
				<%=endpoints_ko.get(j-1).toString()%>
			</td>
			<td>
				<input type="hidden" name="id" value="${UserRecord.id}" ></input>
				&nbsp;<input type="submit" value="Riattiva" onclick="document.getElementById('endpoint').value='<%=endp%>';document.getElementById('tipo').value='abilita';">
			</td>	 
			</tr>	
				<% 
					j++;
				}
				%>	
		</table>
		
		<%}else{ %>
		<h4>Utente attualmente attivo per tutti gli endpoint.</h4>
		<%} %>
		<%-- <input type="hidden" name="id" value="${UserRecord.id}" ></input>
		<input type="submit" value="Invio">
		--%>
	</form>
 	
</div>

