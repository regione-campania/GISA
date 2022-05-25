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
    
    <script type="text/javascript">		ddtabmenu.definemenu("ddtabs2");
	</script>    
    
      		
	<div id="ddtabs2" class="glowingtabs">
		<ul>
			<li>
				<a href="sinantropi.Detail.us?idSinantropo=${s.id}"><span>Dettaglio Anagrafica</span></a>	
			</li>				
			<li>
				<a href="sinantropi.catture.List.us?idSinantropo=${s.id}"><span>Rinvenimenti</span></a>	
			</li>			
			<li>
				<a href="sinantropi.detenzioni.List.us?idCattura=${c.id}"><span>Detenzioni</span></a>	
			</li>	
			<li>
				<a href="sinantropi.reimmissioni.Detail.us?idCattura=${c.id}"><span>Rilasci</span></a>	
			</li>				
		</ul>
	</div>
	 