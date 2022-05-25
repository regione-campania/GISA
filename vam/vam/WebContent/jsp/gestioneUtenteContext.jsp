<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<style type="text/css">
.clinicaDiv {
	width: 100%;
}

.clinicaDiv a {
	width: 100%;
	border: 1px solid aliceBlue;
	display:block;
	color:#555555;
	font-weight:bold;
	height:100px;
	line-height:29px;
	margin-bottom:14px;
	text-decoration:none;
	-webkit-transition: color 0.4s linear;
	-moz-transition: color 0.4s linear;
	-moz-border-radius: 8px;
	border-radius: 8px;
}

.clinicaDiv a:hover {
	color:#0066CC;
}

.clinicaSpanH {
	text-align: center;
	display:block;
}

.clinicaSpanB {
	padding: 0px 10px 0px 10px;
	display:block;
	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;
	font-weight: normal;
	//color: black;
	line-height: normal;
}

</style>

<div style="width: 500px; margin: 10px auto;">
	<h2>Attenzione!!!</h2>
	<h3>L'utente ${utenteVecchioContext.username} risulta già collegato.<br/>
		Scollegare il vecchio e continuare col collegamento?
	</h3>
</div>

<form id="myForm" name="myForm" action="login.Login.us?" method="POST">
	<input type="hidden" name="scollegareUtenteContext" value="true"/>
	<input type="hidden" name="utente" value="${utente}"/>
	<input type="hidden" name="password" value="${password}"/>
	<input type="hidden" name="id" value="${id}"/>
</form>

<div style="width: 300px; margin: 40px auto;">
	<div class="clinicaDiv">
		<a onclick="document.getElementById('myForm').submit();">
			<span class="clinicaSpanB">
				Si
			</span>
		</a>
	</div>
	<div class="clinicaDiv">
		<a href="Index.us?entrypointSinantropi=urlDiretto">
			<span class="clinicaSpanB">
				No
			</span>
		</a>
	</div>
</div>
