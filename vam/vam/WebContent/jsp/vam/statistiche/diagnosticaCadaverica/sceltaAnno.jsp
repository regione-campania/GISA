<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<style type="text/css">
.annoSelect{
	height: 50px;
	font-size: 33px;
	padding: 5px;
	border: 1px solid brown;
	border-radius: 4px 0 0 4px;
	-moz-border-radius: 4px 0 0 4px;
}
.annoSubmit{
	height: 50px;
	font-size: 32px;
	border: 1px solid brown;
	border-radius: 0px 4px 4px 0px;
	-moz-border-radius: 0px 4px 4px 0px;
	margin-left: -3px;
	-webkit-transition: color 0.4s linear;
	-moz-transition: color 0.4s linear;
	border-bottom-color: transparent;
	border-top-color: transparent;
	position: absolute;
}
.annoSubmit:hover{
	color: aliceblue;
	cursor: pointer;
}
</style>
<h2>STATISTICHE</h2>
<h4 class="titolopagina" style="text-align: center;">Diagnostica cadaverica degli animali senza padrone deceduti per cause naturali</h4>
<div style="margin: 50px auto; width: 280px;">
	<form action="vam.statistiche.DiagnosticaCadavericaResult.us" method="post">
		<select name="anno" class="annoSelect">
			<c:forEach begin="2012" end="${annoCorrente }" var="i">
				<option <c:if test="${annoCorrente == i }" >selected="selected"</c:if> value="${i }">${i }</option>
			</c:forEach>
		</select>
		<input type="submit" class="annoSubmit" value="&nbsp;calcola &raquo;&nbsp;" onclick="attendere();" style="padding: 0;background-color: brown;border-bottom-color: transparent;border-top-color: transparent;" />
	</form>
</div>
