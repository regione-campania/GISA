<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<div class="area-contenuti-2">
	
	<form action="<c:if test="${!modify}">utenti.Add.us</c:if><c:if test="${modify}">utenti.Modify.us</c:if>" method="post">
		
		<input type="submit" class="button" value="Salva" />
		<input type="button" class="button" value="Annulla" onclick="location.href='utenti.List.us'" />
		<c:if test="${modify}">
			<input type="hidden" name="id" value="${userDetail.id }" />
		</c:if>
		
		<table class="tabella" >
			<thead>
				<tr>
					<th colspan="2">
						<c:if test="${!modify}">Nuovo Utente</c:if>
						<c:if test="${modify}">Modifica Utente</c:if>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Username</td>
					<td>
						<c:if test="${!modify}"><input type="text" maxlength="64" size="20" name="username" value="${userDetail.username }" /></c:if>
						<c:if test="${modify}">${userDetail.username} </c:if>
					</td>
				</tr>
				<tr>
					<td>Password</td>
					<td>
						<c:if test="${!modify}">
							<input type="password" maxlength="64" size="20" name="password_1" value="" />
							conferma:<input type="password" maxlength="64" size="20" name="password_2" value="" />
						</c:if>
						<c:if test="${modify}"> ******** </c:if>
					</td>
				</tr>
				<tr>
					<td>Domanda Segreta</td>
					<td>
						<c:if test="${!modify}"><input type="text" maxlength="128" size="40" name="domandaSegreta" value="${userDetail.domandaSegreta }" /></c:if>
						<c:if test="${modify}">${userDetail.domandaSegreta } </c:if>
					</td>
				</tr>
				<tr>
					<td>Risposta Segreta</td>
					<td>
						<c:if test="${!modify}"><input type="text" maxlength="128" size="40" name="rispostaSegreta" value="" /></c:if>
						<c:if test="${modify}"> ******** </c:if>
					</td>
				</tr>
				<tr>
					<td>Nome</td>
					<td><input type="text" maxlength="64" size="20" name="nome" value="${userDetail.nome }" /> </td>
				</tr>
				<tr>
					<td>Cognome</td>
					<td><input type="text" maxlength="64" size="20" name="cognome" value="${userDetail.cognome }" /> </td>
				</tr>
				<tr>
					<td>Codice Fiscale</td>
					<td><input type="text" maxlength="16" size="20" name="codiceFiscale" value="${userDetail.codiceFiscale }" /> </td>
				</tr>
				<tr>
					<td>Luogo di Nascita</td>
					<td><input type="text" maxlength="64" size="20" name="comuneNascita" value="${userDetail.comuneNascita }" /> </td>
				</tr>
				<tr>
				<td>Data Di Nascita</td>
	    		<td>
					<input 
						type="text" 
						value="<fmt:formatDate type="date" value="${userDetail.dataNascita }" pattern="dd/MM/yyyy" />" 
						id="dataNascita" 
						name="dataNascita" 
						maxlength="10" 
						size="16" 
						readonly="readonly" />
					<img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
					<script type="text/javascript">
						Calendar.setup({
							inputField     :    "dataNascita",     // id of the input field
							ifFormat       :    "%d/%m/%Y",      // format of the input field
							button         :    "id_img_1",  // trigger for the calendar (button ID)
							// align          :    "rl,           // alignment (defaults to "Bl")
							singleClick    :    true,
							timeFormat		:   "24",
							showsTime		:   false
						});					    
					</script>
	    		</td>
	        </tr>
				<tr>
					<td>Email</td>
					<td><input type="text" maxlength="64" size="20" name="email" value="${userDetail.email }" /> </td>
				</tr>
				<tr>
					<td>Telefono 1</td>
					<td><input type="text" maxlength="64" size="20" name="telefono1" value="${userDetail.telefono1 }" /> </td>
				</tr>
				<tr>
					<td>Telefono 2</td>
					<td><input type="text" maxlength="64" size="20" name="telefono2" value="${userDetail.telefono2 }" /> </td>
				</tr>
				<tr>
					<td>Fax</td>
					<td><input type="text" maxlength="64" size="20" name="fax" value="${userDetail.fax }" /> </td>
				</tr>
				<tr>
					<td>Indirizzo</td>
					<td><input type="text" maxlength="64" size="20" name="indirizzo" value="${userDetail.indirizzo }" /> </td>
				</tr>
				<tr>
					<td>CAP</td>
					<td><input type="text" maxlength="5" size="20" name="cap" value="${userDetail.cap }" /> </td>
				</tr>
				<tr>
					<td>Comune</td>
					<td><input type="text" maxlength="64" size="20" name="comune" value="${userDetail.comune }" /> </td>
				</tr>
				<tr>
					<td>Provincia</td>
					<td><input type="text" maxlength="64" size="20" name="provincia" value="${userDetail.provincia }" /> </td>
				</tr>
				<tr>
					<td>Stato</td>
					<td><input type="text" maxlength="64" size="20" name="stato" value="${userDetail.stato }" /> </td>
				</tr>
			</tbody>
		</table>
	
	</form>

</div>
