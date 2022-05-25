<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<jsp:useBean id="messaggioUscita" class="java.lang.String" scope="request" />
<jsp:useBean id="idStabilimento" class="java.lang.String" scope="request" />
<jsp:useBean id="altIdStabilimento" class="java.lang.String" scope="request" />

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>


<script>
function vaiARichiesta(){
	loadModalWindow();
	window.location.href="GisaSuapStab.do?command=Details&altId=<%=altIdStabilimento%>";
}
function vaiAStabilimento(){
	loadModalWindow();
	window.location.href="OpuStab.do?command=Details&stabId=<%=idStabilimento%>";
}
</script>

<% if (altIdStabilimento==null || Integer.parseInt(altIdStabilimento)<=0){ %>
<script>
alert('<%=messaggioUscita%>');
vaiAStabilimento();
</script>
<%} %>


<script>

if (confirm("<%=messaggioUscita%>")){
	vaiARichiesta();
}
else {
	vaiAStabilimento();
}
</script>