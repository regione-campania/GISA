<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<P text-align="center" style="text-align: center">
	<b>PROTEZIONE DEGLI ANIMALI NEGLI ALLEVAMENTI 
	
	<%if (nuova_gestione.equals("true") && numAllegato.equals("1")) {%>
	<br/> <b>ALTRE SPECIE: </b>	
	<% }%>
	<label class="layout"><%=specie%></label>
	<br/></b>
	
	<%if (nuova_gestione.equals("true") && numAllegato.equals("1")) {%>
	 <i>("ALTRE SPECIE": qualsiasi animale, inclusi pesci, rettili e anfibi, allevati o custoditi per la produzione di derrate alimentari, lana, pelli, pellicce o per altri scopi agricoli)</i>
	 <br/>	
	<% }%>
	
	   RISULTATI DEI CONTROLLI EFFETTUATI PRESSO LE AZIENDE
</P>
<BR>
<p style="">
 REGIONE <input type="text" class="layout" readonly value="CAMPANIA"/>
 ASL <input type="text" class="layout" readonly value="<%= Allevamento.getAsl()%>"/>
<br> 
EXTRAPIANO: <span class="NocheckedItem">SI&nbsp;&nbsp;</span>
<span class="NocheckedItem">NO&nbsp;&nbsp;</span>
DATA DEL CONTROLLO:<input type="text" class="layout" readonly value="<%= Allevamento.getGiornoReferto()+ " " + Allevamento.getMeseReferto() + " "+ Allevamento.getAnnoReferto()%>" /> 
</p>
