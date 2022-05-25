<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript" src="js/jquery/jquery-1.3.2.min.js" ></script>

<style type="text/css">
#Copia ed adattamento del css contenuto in css > vam > homePage
#content
{
margin:0 auto;
width:899px;
}
#content p
{
font:normal 12px/18px Arial, Helvetica, sans-serif;
padding:10px;
color:#333333;
}
#content_right
{
margin:0 auto;
width:860px;
padding:5px;
}
#content h3
{
font:bold 12px/20px Arial, Helvetica, sans-serif;
color:#607B35;
}
#content_row1
{
margin:0 auto;
width:670px;
height:175px;
background:url('images/homePage/pets_clinic_08.gif') no-repeat 0 0;
padding-left:250px
}
#content_row2
{
margin:0 auto;
width:625px;
}

</style>

<div id="content">

<div align="center">
	<a href="Home.us" style="margin: 0px 0px 0px 0px"><img src="images/lista.png" height="18px" width="18px" />Lista Utenti</a>
</div>

<center>
  
<h4 class="titolopagina">Allineamento Utenti</h4>

<div class="area-contenuti-2">
	<form action="guc.AllineamentoUtenti.us" method="post">
	
		<p>Selezionare l'endpoint che si vuole allineare con gli utenti presenti in S.I.R.V.</p>
		
		<select name="endpoint" id="endpoint">
			<c:forEach items="${endpoints}" var="e">
				<option value="${e}" <c:if test="${e == endpoint}">selected="selected"</c:if> >${e}</option>
			</c:forEach>
		</select>
		<br/>
		<input type="submit" value="OK" />
	</form>
	
</div>
</center>
  
</div>