<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.allevamenti.base.Organization"
	scope="request" />
<jsp:useBean id="Asl" class="org.aspcfs.utils.web.LookupList"
	scope="request" />	
<jsp:useBean id="focolaio"
	class="org.aspcfs.modules.focolai.base.Focolaio" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
	
<link rel="stylesheet" type="text/css" media="print" href="print.css" />	
		
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.allevamenti.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants"%>

<%@page import="org.aspcfs.modules.focolai.base.Focolaio"%>
	
	

ASL <%=Asl.getSelectedValue(OrgDetails.getSiteId())%>

<CENTER><h3> SERVIZIO VETERINARIO </H3></CENTER>

<center><h4>DENUNCIA DI MALATTIA INFETTIVA E DIFFUSIVA DEGLI ANIMALI </H4></center>

CODICE AZIENDA (D.P.R. 317/96): <%=(OrgDetails.getAccountNumber())%> &nbsp;&nbsp;&nbsp;&nbsp;SPECIE ALLEVATA: <%=(OrgDetails.getSpecieAllev())%> <br><br>
DENOMINAZIONE AZIENDA: <%= OrgDetails.getName() %> &nbsp;&nbsp;&nbsp;PARTITA IVA / CODICE FISCALE: <%=(OrgDetails.getPartitaIva()) %> <br><br>
LOCALITA' DELLA STALLA O DEL PASCOLO INFETTO: <%=focolaio.getLocalita() %>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MALATTIA: <%=focolaio.getMalattia() %> <br><br>
DATA DEL SOSPETTO: <zeroio:tz timestamp="<%=focolaio.getDataSospetto() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />&nbsp;&nbsp; DATA DELLA PROVA: <zeroio:tz timestamp="<%=focolaio.getDataProva() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /><br><br>
DATA APERTURA FOCOLAIO: <zeroio:tz timestamp="<%=focolaio.getDataApertura() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> &nbsp;&nbsp;ORIGINE MALATTIA: <%=focolaio.getOrigineMalattia()%> <BR><BR>
MALATTIE SOGGETTE A PIANI DI RISANAMENTO:<br>
<% if( focolaio.getProvenienzaRegionale() != false){ %> <input type="checkbox" disabled="disabled"  checked="checked"> &nbsp;&nbsp;Introduzione di animali di provenienza regionale&nbsp<% }else {%> <input type="checkbox" disabled="disabled">&nbsp;&nbsp;Introduzione di animali di provenienza regionale&nbsp <%} %> <%if(focolaio.getDataProvenienzaRegionale() != null){ %>;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Data ultima introduzione <zeroio:tz timestamp="<%=focolaio.getDataProvenienzaRegionale() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> <%} %><br>
<% if( focolaio.getProvenienzaExtraRegionale() != false){ %> <input type="checkbox" disabled="disabled"  checked="checked">&nbsp;&nbsp;Introduzione di animali di provenienza extraregionale <% }else {%> <input type="checkbox" disabled="disabled">&nbsp;&nbsp;Introduzione di animali di provenienza extraregionale <%} %> <%if(focolaio.getDataProvenienzaExtraRegionale() != null){ %>&nbsp;&nbsp;Data ultima introduzione <zeroio:tz timestamp="<%=focolaio.getDataProvenienzaExtraRegionale() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> <%} %><br>
<% if(focolaio.getPascoloInfetto() != false){ %> <input type="checkbox" disabled checked="checked"> <% }else {%> <input type="checkbox" disabled> <%} %>&nbsp;&nbsp;Pascolo infetto &nbsp;&nbsp;<% if(focolaio.getReinfezione() != false){ %> <input type="checkbox" disabled checked="checked"> <% }else {%> <input type="checkbox" disabled> <%} %>&nbsp;&nbsp;Reinfezione&nbsp;&nbsp;<% if(focolaio.getCaniInfetti() != false){ %> <input type="checkbox" disabled checked="checked"> <% }else {%> <input type="checkbox" disabled> <%} %>&nbsp;&nbsp;Cani infetti<br>
<% if(focolaio.getMontaEsterna() != false){ %> <input type="checkbox" disabled checked="checked"> <% }else {%> <input type="checkbox" disabled> <%} %> &nbsp;&nbsp;Monta esterna&nbsp;&nbsp;&nbsp;<% if(focolaio.getIatrogena() != false){ %> <input type="checkbox" disabled checked="checked"> <% }else {%> <input type="checkbox" disabled> <%} %>&nbsp;&nbsp;&nbsp;Iatrogena<br><br>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">

				<tr>
					<th colspan="2"><strong><dhv:label
						name="acque classificate.decrddeto">Censimento degli animali presenti in azienda</dhv:label></strong>
					</th>

				</tr>
				<tr>

					<td>
					<table width="100%" border="1">
						<tr>

							<td align="center"><dhv:label
								name="acque classificate.identificativo_decrecccto">1 Specie</dhv:label>
							</td>


							<td align="center"><dhv:label
								name="acque classificate.identificativo_decrecccto">2 n. complessivo</dhv:label>
							</td>

							<td align="center"><dhv:label
								name="acque classificate.identificativo_decrecccto">3 Nati in stalla</dhv:label>
							</td>

							<td align="center"><dhv:label
								name="acque classificate.identificativo_decrecccto">4 Introdotti</dhv:label>
							</td>

							<td align="center"><dhv:label
								name="acque classificate.identificativo_decrecccto">5 Ammalati</dhv:label>
							</td>


							<td align="center"><dhv:label
								name="acque classificate.identificativo_decrecccto">6 Morti</dhv:label>
							</td>

						</tr>

						<% ArrayList specie = focolaio.getSpecie();
					   ArrayList complessivo = focolaio.getComplessivo();
					   ArrayList natiStalla = focolaio.getNatiStalla();
					   ArrayList introdotti = 	focolaio.getIntrodotti();
					   ArrayList ammalati = focolaio.getAmmalati();
					   ArrayList morti = focolaio.getMorti();
					
					%>

						<tr>

							<td align="center">
							<%if(specie.get(0) != null && !specie.get(0).equals("")){ %> <%=specie.get(0) %>
							<%}else{ %> <%out.println(" - "); %> <%} %>
							</td>


							<td align="center">
							<%if(complessivo.get(0) != null){ %> <%=complessivo.get(0) %> <%} %>


							</td>

							<td align="center">
							<%if(natiStalla.get(0) != null){ %> <%=natiStalla.get(0) %> <%} %>
							</td>

							<td align="center">
							<%if(introdotti.get(0) != null){ %> <%=introdotti.get(0) %> <%} %>
							</td>

							<td align="center">
							<%if(ammalati.get(0) != null){ %> <%=ammalati.get(0) %> <%} %>
							</td>


							<td align="center">
							<%if(morti.get(0) != null){ %> <%=morti.get(0) %> <%} %>
							</td>

						</tr>



						<tr>

							<td align="center">
							<%if(specie.get(1) != null && !specie.get(1).equals("")){ %> <%=specie.get(1) %>
							<%}else { %> <% out.println("-"); %> <%} %>
							</td>


							<td align="center">
							<%if(complessivo.get(1) != null){ %> <%=complessivo.get(1) %> <%} %>


							</td>

							<td align="center">
							<%if(natiStalla.get(1) != null){ %> <%=natiStalla.get(1) %> <%} %>
							</td>

							<td align="center">
							<%if(introdotti.get(1) != null){ %> <%=introdotti.get(1) %> <%} %>
							</td>

							<td align="center">
							<%if(ammalati.get(1) != null){ %> <%=ammalati.get(1) %> <%} %>
							</td>


							<td align="center">
							<%if(morti.get(1) != null){ %> <%=morti.get(1) %> <%} %>
							</td>


						</tr>



						<tr>

							<td align="center">
							<%if(specie.get(2) != null && !specie.get(2).equals("")){ %> <%=specie.get(2) %>
							<%}else { %> <% out.println("-"); %> <%} %>
							</td>


							<td align="center">
							<%if(complessivo.get(2) != null){ %> <%=complessivo.get(2) %> <%} %>


							</td>

							<td align="center">
							<%if(natiStalla.get(2) != null){ %> <%=natiStalla.get(2) %> <%} %>
							</td>

							<td align="center">
							<%if(introdotti.get(2) != null){ %> <%=introdotti.get(2) %> <%} %>
							</td>

							<td align="center">
							<%if(ammalati.get(2) != null){ %> <%=ammalati.get(2) %> <%} %>
							</td>


							<td align="center">
							<%if(morti.get(2) != null){ %> <%=morti.get(2) %> <%} %>
							</td>

						</tr>



						<tr>

							<td align="center">
							<%if(specie.get(3) != null && !specie.get(3).equals("")){ %> <%=specie.get(3) %>
							<%}else { %> <% out.println("-"); %> <%} %>
							</td>


							<td align="center">
							<%if(complessivo.get(3) != null){ %> <%=complessivo.get(3) %> <%} %>


							</td>

							<td align="center">
							<%if(natiStalla.get(3) != null){ %> <%=natiStalla.get(3) %> <%} %>
							</td>

							<td align="center">
							<%if(introdotti.get(1) != null){ %> <%=introdotti.get(1) %> <%} %>
							</td>

							<td align="center">
							<%if(ammalati.get(3) != null){ %> <%=ammalati.get(3) %> <%} %>
							</td>


							<td align="center">
							<%if(morti.get(3) != null){ %> <%=morti.get(3) %> <%} %>
							</td>
						</tr>



						<tr>

							<td align="center">
							<%if(specie.get(4) != null && !specie.get(4).equals("")){ %> <%=specie.get(4) %>
							<%}else { %> <% out.println("-"); %> <%} %>
							</td>


							<td align="center">
							<%if(complessivo.get(4) != null){ %> <%=complessivo.get(4) %> <%} %>


							</td>

							<td align="center">
							<%if(natiStalla.get(4) != null){ %> <%=natiStalla.get(4) %> <%} %>
							</td>

							<td align="center">
							<%if(introdotti.get(4) != null){ %> <%=introdotti.get(4) %> <%} %>
							</td>

							<td align="center">
							<%if(ammalati.get(4) != null){ %> <%=ammalati.get(4) %> <%} %>
							</td>


							<td align="center">
							<%if(morti.get(4) != null){ %> <%=morti.get(4) %> <%} %>
							</td>

						</tr>

					</table>

					</td>

				</tr>


			</table>
<br><br>
DATA E NUMERO DEI PROVVEDIMENTI SANITARI ADOTTATI DAL SINDACO: <%if(focolaio.getDataProvvedimenti() != null) {%> Data: <zeroio:tz timestamp="<%= focolaio.getDataProvvedimenti() %>"timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> Numero dei provvedimenti: <%=focolaio.getNumeroProvvedimenti() %><%} %> <br><br>
PROPOSTE PER L'ADOZIONE DI MISURE SANITARIE DI COMPETENZA DEL SETTORE VETERINARIO REGIONALE: <%=focolaio.getProposteAdozione() %><br><br>
DATA DI EVENTUALI TRATTAMENTI IMMUNIZZANTI CONTRO LA MALATTIA IN ATTO: <zeroio:tz timestamp="<%= focolaio.getDataImmunizzanti() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />									
<BR><BR><BR>

ADDI'______________<br><br>

    
IL VETERINARIO_________________&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        IL SINDACO ______________________		

<br><br>
<INPUT id="stampa" type=button OnClick="javascript:window.print()" value="Stampa">