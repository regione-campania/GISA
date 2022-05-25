<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<div class="area-contenuti-2">

		<a href="utenti.List.us">Elenco Utenti</a>
		<a href="utenti.ToModify.us?user_id=${userDetail.id}">Modifica</a>
		<a href="utenti.ToCambioRuolo.us?user_id=${userDetail.id}">Cambia Ruolo</a>
		<a href="utenti.ToCambioPassword.us?user_id=${userDetail.id}" >Cambia Password</a>
		<a href="utenti.Delete.us?user_id=${userDetail.id}" onclick="javascript:return confirm('Eliminare l\'utente ${userDetail.username}?')" >Elimina</a>
		<a href="utenti.ToAdd.us">Aggiungi un Nuovo Utente</a>
		
		<table class="tabella" >
			<thead>
				<tr>
					<th colspan="2">
						Dettaglio Utente
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Username</td>
					<td>${userDetail.username}</td>
				</tr>
				<tr>
					<td>Domanda Segreta</td>
					<td>
						${userDetail.domandaSegreta }
					</td>
				</tr>
				<tr>
					<td>Nome</td>
					<td>${userDetail.nome }</td>
				</tr>
				<tr>
					<td>Cognome</td>
					<td>${userDetail.cognome }</td>
				</tr>
				<tr>
					<td>Codice Fiscale</td>
					<td>${userDetail.codiceFiscale }</td>
				</tr>
				<tr>
					<td>Luogo di Nascita</td>
					<td>${userDetail.comuneNascita }</td>
				</tr>
				<tr>
				<td>Data Di Nascita</td>
	    		<td><fmt:formatDate type="date" value="${userDetail.dataNascita }" pattern="dd/MM/yyyy" /></td>
	        </tr>
				<tr>
					<td>Email</td>
					<td>${userDetail.email }</td>
				</tr>
				<tr>
					<td>Telefono 1</td>
					<td>${userDetail.telefono1 }</td>
				</tr>
				<tr>
					<td>Telefono 2</td>
					<td>${userDetail.telefono2 }</td>
				</tr>
				<tr>
					<td>Fax</td>
					<td>${userDetail.fax }</td>
				</tr>
				<tr>
					<td>Indirizzo</td>
					<td>${userDetail.indirizzo }</td>
				</tr>
				<tr>
					<td>CAP</td>
					<td>${userDetail.fax }</td>
				</tr>
				<tr>
					<td>Comune</td>
					<td>${userDetail.comune }</td>
				</tr>
				<tr>
					<td>Provincia</td>
					<td>${userDetail.provincia }</td>
				</tr>
				<tr>
					<td>Stato</td>
					<td>${userDetail.stato }</td>
				</tr>
			</tbody>
		</table>


</div>
