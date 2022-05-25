<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="messaggio" class="java.lang.String" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>


<form id = "addAccount" name="addAccount" action="DpatSDC2019.do?command=MessaggioAggiorna&auto-populate=true" method="post">
<table style="border:1px solid black">
<tr>
<td><b>Messaggio</b></td>
<td><textarea name="messaggio" id="messaggio" rows="5" cols="80"><%=(messaggio!=null) ? messaggio  : "" %></textarea></td>
</tr>
<tr>
<td><a href="DpatSDC2019.do?command=MessaggioVisualizza">Annulla</a></td>
<td><input type="submit" value="SALVA"/></td></tr>
</table>
</form>