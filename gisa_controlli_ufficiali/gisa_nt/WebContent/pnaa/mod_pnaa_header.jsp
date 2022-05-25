<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<center><i>ALLEGATO 1: Verbale di prelievo (PNAA)</i></center><br/>

<div align="right">
SCHEDA N. <label class="layout"><%= fixValore(Mod.getNumeroScheda())%></label>
</div>

<center>
<b>VERBALE DI PRELIEVO (PNAA)</b><br/>
<i><label class="layout"><%= fixValore(Mod.getCampioneMotivazione())%></label></i>
</center>

<div align="right">
Verbale n. <img src="<%=createBarcodeImage(Mod.getCampioneVerbale())%>" /> Data <label class="layout"><%= fixValoreShort(toDateasString(Mod.getCampioneData()))%></label> <br/>
Codice preccettazione:  <% if(Mod.getCampioneCodicePreaccettazione() != null && !Mod.getCampioneCodicePreaccettazione().equals("")){ %><img align ="middle" src="<%=createBarcodeImage(Mod.getCampioneCodicePreaccettazione().toUpperCase() )%>" /><% } else { %>NON DISPONIBILE<% } %>
</div>

<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div> 

<br/><br/>
