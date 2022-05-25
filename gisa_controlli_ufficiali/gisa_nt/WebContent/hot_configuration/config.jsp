<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.TreeSet"%>
<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">La Mia Home Page</dhv:label></a> >
Configurazione a caldo
</td>
</tr>
</table>

<form name="configForm" action="HotConfiguration.do?command=Config" method="post">

<% TreeSet<Object> chiavi = new TreeSet<Object>( ApplicationProperties.getApplicationProperties().keySet() ); %>
<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
	    <td width="50%" valign="top">
	    	<table cellpadding="4" cellspacing="0" border="0" width="50%" class="details">
	        	<tr>
	          		<th colspan="2"><strong>Configurazione a caldo</dhv:label></strong></th>
	        	</tr>
		        <% 
				for(Object chiave : chiavi){
					if(!chiave.equals("livelloLOG")){
				%>
		        <tr>
	          		<td nowrap class="formLabel"><%= chiave %></td>
	          		<td><input type="text" size="98%" name="<%= chiave %>" value="<%= ApplicationProperties.getProperty(chiave.toString()) %>" /></td>
	        	</tr>
		        <%
					}
				}
				%>
				
				<tr>
	          		<th colspan="2"><strong>Configurazione a caldo - LOG</dhv:label></strong></th>
	        	</tr>
		        
		        <tr>
	          		<td nowrap class="formLabel">livelloLOG</td>
	          		<td>
	          			Livello attuale: <%= ApplicationProperties.getProperty("livelloLOG") %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	          			Modifica livello:  
	          			<select name="livelloLOG" >
	          				<option value="SEVERE" <% if( ApplicationProperties.getProperty("livelloLOG").equalsIgnoreCase("SEVERE") ){ %>selected="selected"<% } %> >SEVERE</option>
	          				<option value="WARNING" <% if( ApplicationProperties.getProperty("livelloLOG").equalsIgnoreCase("WARNING") ){ %>selected="selected"<% } %> >WARNING</option>
	          				<option value="INFO" <% if( ApplicationProperties.getProperty("livelloLOG").equalsIgnoreCase("INFO") ){ %>selected="selected"<% } %> >INFO</option>
	          				<option value="CONFIG" <% if( ApplicationProperties.getProperty("livelloLOG").equalsIgnoreCase("CONFIG") ){ %>selected="selected"<% } %> >CONFIG</option>
	          			</select>
	          		</td>
	        	</tr>
		        
			</table>
			<input type="submit" value="Salva"></input><br/>
			<p style="color: red;"><%= request.getAttribute("configMessage") != null ? request.getAttribute("configMessage") : ""%></p>
		</td>
	</tr>
</table>

</form>