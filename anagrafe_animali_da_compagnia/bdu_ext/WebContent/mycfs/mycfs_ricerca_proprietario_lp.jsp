<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- Trails --%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript"
        SRC="javascript/popCalendar.js"></script>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
			<dhv:label name="">Ricerca Proprietario</dhv:label>
		</td>
	</tr>
</table>

<form id="searchForm" name="searchForm" action="MyCFS.do?command=RicercaProprietarioLP" method="post">
<%
	String errore = (String)request.getAttribute( "errore" ); 
%>
<%=(errore == null) ? ("") : (errore) %>
<br/>

Microchip 
<input type="text" name="mc" id="mc" value="" maxlength="15"/>
<input type="button" value="Ricerca" onclick="if(document.getElementById('mc').value==''){alert('Inserire il microchip da ricercare');}else{document.getElementById('searchForm').submit();}">
</form>