<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- <iframe src="ServletServiziScheda?object_id=<%=request.getParameter("objectId") %>&tipo_dettaglio=<%=request.getParameter("tipo_dettaglio") %>&visualizzazione=screen" name="dettaglio" id="dettaglio" style="width:90%; height: 80%"></iframe> 
 --%>
<%-- Decommentare per lo sviluppo sul codice di preaccettazione.
object_id contiene il riferimento_id dell'anagrafica
tipo_dettaglio contiene l'id della tipologia di operatore secondo la scheda centralizzata
 --%>

 
 
<br>
<br>
 <jsp:include page="/ServletServiziScheda">
  <jsp:param name="object_id" value="<%=request.getParameter("objectId") %>" />
    <jsp:param name="object_id_name" value="<%=request.getParameter("objectIdName") %>" />
  <jsp:param name="tipo_dettaglio" value="<%=request.getParameter("tipo_dettaglio") %>" />
  <jsp:param name="visualizzazione" value="screen" />
 </jsp:include>
 
