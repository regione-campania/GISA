<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>

<!DOCTYPE html>

<LINK REL="SHORTCUT ICON" HREF="images/animated_favicon1.gif">


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="it" lang="it">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=windows-1252" />
		<link rel="stylesheet" type="text/css" href="css/vam/template_css.css" />
		<link rel="stylesheet" type="text/css" href="css/vam/homePage/homePage.css" />
		<script type="text/javascript" src="js/jmesa/jquery-1.3.min.js"></script>		
		<script type="text/javascript" src="js/azionijavascript.js"></script>
		<script type="text/javascript" src="js/amministrazione/permessi.js"></script>
		<title>VAM</title>
	</head>
	
	<div class="contentpaneopen">
			<div class="cpocontent">
				<us:err classe="errore" />
				<us:mex classe="messaggio" />
				<tiles:insertAttribute name="body" />
			</div>
		</div>
</html>
