<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
  
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@ include file="../initPage.jsp" %>


<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Allevamenti.do">Allevamenti</a> >
Ricerca Elicicoltura in BDN
</td>
</tr>
</table>

<font color="red"> Schermata SOLO PER HELP DESK per tentativo di interrogazione ai nuovi WS Elicicoltura.</font><br/><br/>

<form method="post" action = "Allevamenti.do?command=SearchElicicoltura" onsubmit="javascript:if(this.cod_azienda.value==''){alert('Inserire il codice Azienda');return false;} return true ;">

<table>

<tr>
<td>Codice Azienda</td>
<td><input type = "text" name = "aziendaCodice"></td>
</tr>

</table>
<input type = "submit" value = "Ricerca in Bdn">

</form>
