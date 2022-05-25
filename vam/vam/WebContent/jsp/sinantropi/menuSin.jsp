<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
	
	<script type="text/javascript" src="js/sinantropi/ddtabmenu.js">
	</script>

	<!-- CSS for Tab Menu #2 -->
	<link rel="stylesheet" type="text/css" href="css/sinantropi/menuSin/glowtabs.css" />
    
    <script type="text/javascript">
		ddtabmenu.definemenu("ddtabs2")
	</script>    
    
<c:set var="action" value=""/>    
<c:if test="${s.zoo}">
	<c:set var="action" value="Zoo"/>    
</c:if>
<c:if test="${s.marini}">
	<c:set var="action" value="Marini"/>    
</c:if>

	<div id="ddtabs2" class="glowingtabs">
		<ul>
			<li>
				<a href="sinantropi.Detail${action}.us?idSinantropo=${s.id}"><span>Dettaglio Anagrafica</span></a>	
			</li>				
			<li>
				<a href="sinantropi.catture.List.us?idSinantropo=${s.id}"><span>Rinvenimenti, Detenzioni e Rilasci</span></a>	
			</li>			
<!--			<li>-->
<!--				<a href="sinantropi.detenzioni.List.us?idSinantropo=${s.id}"><span>Detenzioni</span></a>	-->
<!--			</li>	-->
<!--			<li>-->
<!--				<a href="sinantropi.reimmissioni.List.us?idSinantropo=${s.id}"><span>Rilasci</span></a>	-->
<!--			</li>			-->
			<li>
				<a href="sinantropi.decessi.Detail.us?idSinantropo=${s.id}""><span>Decesso</span></a>
			</li>			
			
			
			
		</ul>
	</div>
	 
	

	