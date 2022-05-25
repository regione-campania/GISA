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

CODICE AZIENDA (D.P.R. 317/96) <%=(OrgDetails.getAccountNumber())%> &nbsp;&nbsp;&nbsp;&nbsp;SPECIE ALLEVATA <%=(OrgDetails.getSpecieAllev())%> <br><br>
DENOMINAZIONE AZIENDA <%= OrgDetails.getName() %> &nbsp;&nbsp;&nbsp;PARTITA IVA / CODICE FISCALE <%=(OrgDetails.getPartitaIva()) %> <br><br>
LOCALITA' DELLA STALLA O DEL PASCOLO INFETTO <%=focolaio.getLocalita() %>  &nbsp;&nbsp;&nbsp;MALATTIA <%=focolaio.getMalattia() %> <br><br>
DATA DEL SOSPETTO <zeroio:tz timestamp="<%=focolaio.getDataSospetto() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> DATA DELLA PROVA <zeroio:tz timestamp="<%=focolaio.getDataProva() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /><br><br>
DATA APERTURA FOCOLAIO <zeroio:tz timestamp="<%=focolaio.getDataApertura() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> &nbsp;&nbsp;ORIGINE MALATTIA <%=focolaio.getOrigineMalattia()%> <BR><BR>


			<% ArrayList specie = focolaio.getSpecie();
					   ArrayList morti = focolaio.getMorti();
					   ArrayList abbattuti = focolaio.getAbbattuti();
					   ArrayList guariti = 	focolaio.getGuariti();
					   ArrayList totaleMalati = focolaio.getTotaleMalati();
					   ArrayList smarriti = focolaio.getSmarriti();
					   ArrayList sani = focolaio.getSani();
					   ArrayList esistenti = focolaio.getEstinti();
					   
					
					%>


		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">

			<tr>
				<th colspan="2"><strong><dhv:label
					name="acque classificate.decrddeto">Riepilogo dati sul decorso della malattia</dhv:label></strong>
				</th>

			</tr>
			<tr>

				<td>
				<table width="100%" border="1">
				<tr>
					 <td width="20%" colspan="2"> *
					 </td>
					 <td width="30%" colspan="4" align="center">
					    Animali malati
					 </td>
					 <td width="15%" align="center">
					  Smarriti/sottratti
					 </td>
					 <td width="15%" align="center">
					  Sani
					 </td>
					 <td width="20%" align="center">
					  Esistenti
					 </td>
					
				</tr>
				
					<tr >
					
					    <td rowspan="4">
					     A. Animali recettivi alla malattia esistenti all'inizio
					    </td>

						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">Specie</dhv:label>
						</td>


						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">1.morti</dhv:label>
						</td>

						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">2.abbattuti</dhv:label>
						</td>

						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">3.guariti</dhv:label>
						</td>

						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">4.totale malati (1+2+3)</dhv:label>
						</td>


						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">5</dhv:label>
						</td>
						
						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">6</dhv:label>
						</td>
						
						<td align="center"><dhv:label
							name="acque classificate.identificativo_decrecccto">7 (4+5+6)</dhv:label>
						</td>

					</tr>

					<tr>

						<td align="center">
						    <%if(specie.get(0) != null && !specie.get(0).equals("")){ %>
							<%=specie.get(0) %>
							<%}else{ %>
							<%out.println(" - "); %>
							<%} %>   
						</td>


						<td align="center">
						<%if(morti.get(0) != null){ %>
							<%=morti.get(0) %>
							<%} %>  
						
						</td>

						<td align="center">
						   <%if(abbattuti.get(0) != null){ %>
							<%=abbattuti.get(0) %>
							<%} %>
						</td>

						<td align="center">
						<%if(guariti.get(0) != null){ %>
							<%=guariti.get(0) %>
							<%} %>
						</td>

						<td align="center">
						<%if(totaleMalati.get(0) != null){ %>
							<%=totaleMalati.get(0) %>
							<%} %>
						</td>


						<td align="center">
						<%if(smarriti.get(0) != null){ %>
							<%=smarriti.get(0) %>
							<%} %>
						</td>
						
						<td align="center">
						<%if(sani.get(0) != null){ %>
							<%=sani.get(0) %>
							<%} %>
						
						</td>
							
						<td align="center">
						<%if(esistenti.get(0) != null){ %>
							<%=esistenti.get(0) %>
							<%} %>
						</td>		

					</tr>



					<tr>

						<td align="center">
						<%if(specie.get(1) != null && !specie.get(1).equals("")){ %>
							<%=specie.get(1) %>
							<%}else{ %>
							<%out.println(" - "); %>
							<%} %> 
						</td>


						<td align="center">
						<%if(morti.get(1) != null){ %>
							<%=morti.get(1) %>
							<%} %>
						</td>

						<td align="center">
						<%if(abbattuti.get(1) != null){ %>
							<%=abbattuti.get(1) %>
							<%} %>
						
						</td>

						<td align="center">
						<%if(guariti.get(1) != null){ %>
							<%=guariti.get(1) %>
							<%} %>
						
						</td>

						<td align="center">
						<%if(totaleMalati.get(1) != null){ %>
							<%=totaleMalati.get(1) %>
							<%} %>
						</td>


						<td align="center">
						<%if(smarriti.get(1) != null){ %>
							<%=smarriti.get(1) %>
							<%} %>
						</td>
							
						<td align="center">
						<%if(sani.get(1) != null){ %>
							<%=sani.get(1) %>
							<%} %>
						</td>
							
						<td align="center"><%if(esistenti.get(1) != null){ %>
							<%=esistenti.get(1) %>
							<%} %></td>		

					</tr>



					<tr>

						<td align="center">
						<%if(specie.get(2) != null && !specie.get(2).equals("")){ %>
							<%=specie.get(2) %>
							<%}else{ %>
							<%out.println(" - "); %>
							<%} %> 
						
						</td>


						<td align="center">
						<%if(morti.get(2) != null){ %>
							<%=morti.get(2) %>
							<%} %>
						</td>

						<td align="center"><%if(abbattuti.get(2) != null){ %>
							<%=abbattuti.get(2) %>
							<%} %>
						</td>

						<td align="center">
						<%if(guariti.get(2) != null){ %>
							<%=guariti.get(2) %>
							<%} %>
						</td>

						<td align="center">
						<%if(totaleMalati.get(2) != null){ %>
							<%=totaleMalati.get(2) %>
							<%} %>
						</td>


						<td align="center">
						<%if(smarriti.get(2) != null){ %>
							<%=smarriti.get(2) %>
							<%} %>
						</td>
							
						<td align="center">
						<%if(sani.get(2) != null){ %>
							<%=sani.get(2) %>
							<%} %>
						</td>
							
						<td align="center">
						<%if(esistenti.get(2) != null){ %>
							<%=esistenti.get(2) %>
							<%} %>
						</td>		

					</tr>



					<tr>
					
					   <td rowspan="2">
					     B. Animali recettivi alla malattia nati dopo l'apertura del focolaio
					    </td>

						<td align="center">
						<%if(specie.get(3) != null && !specie.get(3).equals("")){ %>
							<%=specie.get(3) %>
							<%}else{ %>
							<%out.println(" - "); %>
							<%} %> 
						</td>


						<td align="center">
						<%if(morti.get(3) != null){ %>
							<%=morti.get(3) %>
							<%} %>
						</td>

						<td align="center">
						<%if(abbattuti.get(3) != null){ %>
							<%=abbattuti.get(3) %>
							<%} %>
						
						</td>

						<td align="center">
						<%if(guariti.get(3) != null){ %>
							<%=guariti.get(3) %>
							<%} %>
						</td>

						<td align="center">
						<%if(totaleMalati.get(3) != null){ %>
							<%=totaleMalati.get(3) %>
							<%} %>
						</td>


						<td align="center">
						<%if(smarriti.get(3) != null){ %>
							<%=smarriti.get(3) %>
							<%} %>
						</td>
							
							<td align="center">
							<%if(sani.get(3) != null){ %>
							<%=sani.get(3) %>
							<%} %>
							</td>


						<td align="center">
						<%if(esistenti.get(3) != null){ %>
							<%=esistenti.get(3) %>
							<%} %>
						</td>	

					</tr>
					



					<tr>
					
						

						<td align="center">
						<%if(specie.get(4) != null && !specie.get(4).equals("")){ %>
							<%=specie.get(4) %>
							<%}else{ %>
							<%out.println(" - "); %>
							<%} %> 
						
						</td>


						<td align="center">
						<%if(morti.get(4) != null){ %>
							<%=morti.get(4) %>
							<%} %>
						</td>

						<td align="center">
						<%if(abbattuti.get(4) != null){ %>
							<%=abbattuti.get(4) %>
							<%} %>
						</td>

						<td align="center">
						<%if(guariti.get(4) != null){ %>
							<%=guariti.get(4) %>
							<%} %>
						</td>

						<td align="center">
						<%if(totaleMalati.get(4) != null){ %>
							<%=totaleMalati.get(4) %>
							<%} %>
						</td>


						<td align="center">
						<%if(smarriti.get(4) != null){ %>
							<%=smarriti.get(4) %>
							<%} %>
						</td>
							
						
						<td align="center">
						<%if(sani.get(4) != null){ %>
							<%=sani.get(4) %>
							<%} %>
						</td>


						<td align="center">
						<%if(esistenti.get(4) != null){ %>
							<%=esistenti.get(4) %>
							<%} %>
						</td>	

					</tr>
					
					<tr>
					
					   <td >
					     Totale (A+B)
					    </td>

						<td align="center">
						<%if(specie.get(5) != null && !specie.get(5).equals("")){ %>
							<%=specie.get(5) %>
							<%}else{ %>
							<%out.println(" - "); %>
							<%} %> 
						</td>


						<td align="center">
						<%if(morti.get(5) != null){ %>
							<%=morti.get(5) %>
							<%} %>
						</td>

						<td align="center">
						<%if(abbattuti.get(5) != null){ %>
							<%=abbattuti.get(5) %>
							<%} %>
						</td>

						<td align="center">
						<%if(guariti.get(5) != null){ %>
							<%=guariti.get(5) %>
							<%} %>
						</td>
                  
						<td align="center">
						<%out.println(" - "); %>
					<%-- <%if(totaleMalati.get(5) != null){ %>
							<%=totaleMalati.get(5) %>
							<%} %>
					--%>			
						</td>
              

						<td align="center">
						<%if(smarriti.get(5) != null){ %>
							<%=smarriti.get(5) %>
							<%} %>
						</td>
							
							<td align="center">
							<%if(sani.get(5) != null){ %>
							<%=sani.get(5) %>
							<%} %>
							</td>

                   
						<td align="center">
						<%out.println(" - "); %>
						<%--<%if(esistenti.get(5) != null){ %>
							<%=esistenti.get(5) %>
							<%} %>
							--%>
						</td>	
						
					

					</tr>

				</table>

				</td>

			</tr>


		</table>


<br><br>
DATA E NUMERO DEI PROVVEDIMENTI SANITARI ADOTTATI DAL SINDACO <%if(focolaio.getDataProvvedimenti() != null) {%> Data <zeroio:tz timestamp="<%= focolaio.getDataProvvedimenti() %>"timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> Numero dei provvedimenti <%=focolaio.getNumeroProvvedimenti() %><%} %> <br><br>
PROPOSTE PER L'ADOZIONE DI MISURE SANITARIE DI COMPETENZA DEL SETTORE VETERINARIO REGIONALE <%=focolaio.getProposteAdozione() %><br><br>
DATE DELL'ESITO (GUARIGIONE-MORTE-ABBATTIMENTO) DELL'ULTIMO CASO DI MALATTIA: <zeroio:tz timestamp="<%= focolaio.getDataUltimoCaso() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />	<br><br>
DATA DELLA REVOCA DEI PROVVEDIMENTI ADOTTATI DAL SINDACO: <zeroio:tz timestamp="<%= focolaio.getDataRevocaSindaco() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />	<br><br>
PROPOSTA DI REVOCA DELLE EVENTUALI MISURE SANITARIE:	<%=focolaio.getProposteRevoca() %>							
<BR><BR><BR>

ADDI'______________<br><br>

    
IL VETERINARIO_________________&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        IL SINDACO ______________________		

<br><br>
<INPUT id="stampa" type=button OnClick="javascript:window.print()" value="Stampa">