<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">La Mia Home Page</dhv:label></a> >
Messaggio Home Page
</td>
</tr>
</table>

<form name="messageForm" action="MessaggioHomePage.do?command=Messaggio" method="post">

<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
	    <td width="50%" valign="top">
	    	<table cellpadding="4" cellspacing="0" border="0" width="50%" class="details">
	        	<tr>
	          		<th colspan="2"><strong>Messaggio Home Page</dhv:label></strong></th>
	        	</tr>
		       
		        <tr>
	          		<td nowrap class="formLabel">Messaggio</td>
	          		<td><textarea cols="150" rows="4" name="messaggio"></textarea></td>
	        	</tr>
		        
				
		        
			</table>
			<input type="submit" value="Salva"></input><br/>
			<p style="color: red;"><%= request.getAttribute("mess") != null ? request.getAttribute("mess") : ""%></p>
		</td>
	</tr>
</table>

</form>