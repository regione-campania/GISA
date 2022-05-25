<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript">
	function chiudi_e_aggiorna_chiamante() {
		//alert('OK');
		//window.opener.location.reload ();
		//window.opener.location.reload ();
		//window.close();

		window.resizeBy(100, 100);  
		
	}
</script>

<body onLoad="javascript:chiudi_e_aggiorna_chiamante()')">

<%
	if (Boolean.parseBoolean( request.getAttribute("bOperazioneOK").toString() )) { %>
			<font color="green"><%= request.getAttribute("sMessaggioOperazione") %></font>
<%	} else { 	%>
			<font color="red"><%= request.getAttribute("sMessaggioOperazione") %></font>
<%	} %>

</body>