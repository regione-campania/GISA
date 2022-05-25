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
	
	<form action="utenti.CambioPassword.us" method="post">
		
		<input type="submit" class="button" value="Salva" />
		<input type="button" class="button" value="Annulla" onclick="location.href='utenti.List.us'" />
		<input type="hidden" name="user_id" value="${userDetail.id }" />
		
		<table class="tabella" >
			<thead>
				<tr>
					<th colspan="2">
						Modifica Password
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td> Username</td>
					<td> ${userDetail.username}</td>
				</tr>
				<tr>
					<td>Password</td>
					<td>
						<font color="red">*</font><input type="password" maxlength="64" size="20" name="password_1" value="" />
						conferma:<font color="red">*</font><input type="password" maxlength="64" size="20" name="password_2" value="" />
					</td>
				</tr>
			</tbody>
		</table>
		<font color="red">* Campi obbligatori</font>
	
	</form>

</div>
