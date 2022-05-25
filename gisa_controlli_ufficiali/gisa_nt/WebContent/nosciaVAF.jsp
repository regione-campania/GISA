<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>


<%@page import="java.net.URLEncoder"%>
<%@page import="crypto.nuova.gestione.ClientSCAAesServlet"%>


<% 
String urlVaf = (String)request.getAttribute("urlVaf");
%>

<body>
<iframe id="frameA"  frameborder="0"  vspace="0"  
	hspace="0" marginwidth="0" marginheight="0"
	scrolling="auto"
	width="100%"  scrolling=yes  height="800"
	style="BORDER-RIGHT: black 1px solid; BORDER-TOP: black 1px solid; Z-INDEX: 999; BORDER-LEFT: black 1px solid; BORDER-BOTTOM: black 1px solid;"
	src="${urlVaf}">
</iframe>
</body>


