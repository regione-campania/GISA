<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="Capo" class="org.aspcfs.modules.macellazionisintesis.base.Capo" scope="request" />

<script>function reloadPadre(){
	window.parent.opener.location.href = "MacellazioniSintesis.do?command=List&altId=<%=Capo.getId_macello()%>&tipo=1";
	window.close();
}</script>



<br/>
<h4>Cancellazione del capo <%=Capo.getCd_matricola() %> eseguita con successo.</h4>
<br/><br/>
<input type="button" value="chiudi" onClick="reloadPadre()"/>