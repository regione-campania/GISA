<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script>

function checkFirefox(msg)
{

	if(msg!=null && msg!="")
		{
		if(confirm(msg)==false)
			{
			location.href="login.Logout.us";
			}
		}
		
		}
</script>
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
	<h2>Benvenuto</h2>
	<h3>Scegli la clinica con cui operare</h3>
</div>


<div class="logout">
			<a onclick="attendere()" href="login.Logout.us">
				<span class="clinicaSpanH">Indietro</span>
				<span class="clinicaSpanB">
				</span>
			</a>
		</div>
		
		
<div style="width: 300px; margin: 40px auto;">
	<c:forEach items="${su.utenti }" var="utente">
		<div class="clinicaDiv">
			<a onclick="attendere();" href="login.Ballot.us?id=${utente.id }">
				<span class="clinicaSpanH">${utente.clinica.nomeBreve }</span>
				<span class="clinicaSpanB">
					${utente.clinica.nome } <c:if test="${utente.clinica.dataCessazione!=null}"> <br/>CESSATA IN DATA ${utente.clinica.dataCessazione}</c:if> <br/>
					${utente.clinica.lookupComuni.description } <br/>
					${utente.clinica.lookupAsl }
				</span>
			</a>
		</div>
	</c:forEach>
</div>


<%
if(request.getAttribute("Messaggio")!=null)
{
%>
 <script>
  checkFirefox('<%= ((String)request.getAttribute("Messaggio")).replace("\n", "") %>');
  </script>
<%}%>