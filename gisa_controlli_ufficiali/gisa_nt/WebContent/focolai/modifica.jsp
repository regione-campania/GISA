<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../initPage.jsp"%>
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.allevamenti.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="Asl" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.allevamenti.base.Organization"
	scope="request" />
	<jsp:useBean id="focolaio" class="org.aspcfs.modules.focolai.base.Focolaio" scope="request"/>


<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCalendar.js"></script>

<script type="text/javascript">

	
	
	function checkForm()
	{
		
	};

	function controlloNumeri(obj) {
		if (isNaN(obj.value) || parseInt(obj.value)<0 || parseInt(obj.value) > 9999)
		{
		alert('Nel campo è possibile immettere solo numeri!');
		obj.value="";
		obj.focus();
		}
		}
	
	
</script>



<form name="focolai" action="Focolai.do?command=Update&focolaioId=<%= focolaio.getFocolaioId() %>" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td><a href="Allevamenti.do"><dhv:label
			name="allevamenti.allevamenti">Accounts</dhv:label></a> > <%
			if (request.getParameter("return") == null) {
		%>
		<a href="Allevamenti.do?command=Search"><dhv:label
			name="allevamenti.SearchResults">Search Results</dhv:label></a> > <%
			} else if (request.getParameter("return").equals("dashboard")) {
		%>
		<a href="Allevamenti.do?command=Dashboard"><dhv:label
			name="communications.campaign.Dashboard">Dashboard</dhv:label></a> > <%
			}
		%>
			<a href="Allevamenti.do?command=Details&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="allevamenti.details">Account Details</dhv:label> </a> >
		<a href="Focolai.do?&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="focolai">Focolai</dhv:label></a> >
		<dhv:label name="focolai.aggiungi">Modifica</dhv:label></td>
	</tr>
</table>

<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
		<td width="100%" valign="top">


		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong><dhv:label name="titolo">Denuncia di malattia infettiva e diffusiva degli animali</dhv:label></strong>
				</th>
			</tr>


			<!-- modifiche d.dauria -->
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="allevamenti.site">Site</dhv:label>
				</td>
				<td><%=Asl.getSelectedValue(OrgDetails.getSiteId())%> <input
					type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>">
					<input
					type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
				</td>
			</tr>


			<dhv:evaluate if="<%=hasText(OrgDetails.getName())%>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="">Denominazione</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getName())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>

			<dhv:evaluate if="<%=hasText(OrgDetails.getAccountNumber())%>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="organization.accountNumbera">Codice Azienda</dhv:label></td>
					<td><%=toHtml(OrgDetails.getAccountNumber())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>

			<dhv:evaluate if="<%=hasText(OrgDetails.getSpecieAllev())%>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="organization.specieAlleva">Specie Allevata</dhv:label></td>
					<td><%=toHtml(OrgDetails.getSpecieAllev())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>

			<dhv:evaluate if="<%=hasText(OrgDetails.getPartitaIva())%>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="">Partita IVA / Codice Fiscale</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getPartitaIva())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>


				<%
					Iterator iaddress = OrgDetails.getAddressList().iterator();
						if (iaddress.hasNext()) {
							while (iaddress.hasNext()) {
								OrganizationAddress thisAddress = (OrganizationAddress) iaddress
										.next();
				%>
				<tr class="containerBody">
					<td nowrap class="formLabel" valign="top">Sede Legale</td>
					<td><%=toHtml(thisAddress.toString())%>&nbsp; <dhv:evaluate
						if="<%=thisAddress.getPrimaryAddress()%>">
						<dhv:label name="stabilimenti.primary.brackets">(Primary)</dhv:label>
					</dhv:evaluate></td>
				</tr>
				<%
					}
						} else {
				%>
				<tr class="containerBody">
					<td colspan="2"><font color="#9E9E9E"><dhv:label
						name="contacts.NoAddresses">No addresses entered.</dhv:label></font></td>
				</tr>
				<%
					}
				%>
			
			


			<!--  fine -->



			<tr>
				<th colspan="2"><strong><dhv:label
					name="focolai.denuncia">Denuncia malattia</dhv:label></strong></th>
			</tr>
			
			<tr>
				<td class="formLabel"><dhv:label name="loc">Malattia</dhv:label>
				</td>
				<td><input id="classe_sprincipale" type="text" maxlength="100"
					size="70" name="malattia" value="<%=focolaio.getMalattia() %>"/></td>
			</tr>
			<%-- 
			<tr>
				<td class="formLabel"><dhv:label name="loc">Specie</dhv:label>
				</td>
				<td><input id="classe_sprincipdale" type="text" maxlength="100"
					size="70" name="specieAnimale" value="<%=focolaio.getSpecieAnimale() %>"/></td>
			</tr>
			--%>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Località della stalla o del pascolo infetto</dhv:label>
				</td>
				<td><input id="classe_principale" type="text" maxlength="50"
					size="50" name="localita" value="<%=focolaio.getLocalita() %>"/></td>
			</tr>
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Data del sospetto</dhv:label>
				</td>
				<td><zeroio:dateSelect form="focolai" field="dataSospetto"
					showTimeZone="false" timestamp="<%=focolaio.getDataSospetto() %>"/></td>
			</tr>
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Data della prova</dhv:label>
				</td>
				<td><zeroio:dateSelect form="focolai" field="dataProva" timestamp="<%=focolaio.getDataProva() %>"
					showTimeZone="false" /></td>
			</tr>
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Data apertura</dhv:label>
				</td>
				<td><zeroio:dateSelect form="focolai" timestamp="<%=focolaio.getDataApertura() %>" field="dataApertura"
					showTimeZone="false" /></td>
			</tr>
			<tr>
				<td class="formLabel"><dhv:label name="loc">Origine malattia</dhv:label>
				</td>
				<td><input id="classe_principaled" type="text" maxlength="50" value="<%=focolaio.getOrigineMalattia()%>"
					size="50" name="origineMalattia" /></td>
			</tr>
			<tr>
				<td></td>
				<td>In caso di malattie soggette a piani di risanamento
				specificare</td>
			</tr>
			<tr>
				<td class="formLabel"><dhv:label name="loc">Animali di provenienza regionale</dhv:label>
				</td>
				<td>
				 <% if(focolaio.getProvenienzaRegionale() == true) { %>
				    <input type="checkbox" name="provenienzaRegionale" checked/>
				    <%}else { %>
				        <input type="checkbox" name="provenienzaRegionale" />
				      <%} %>   
				&nbsp;&nbsp;Data ultima introduzione <zeroio:dateSelect
					form="focolai" field="dataProvenienzaRegionale"
					showTimeZone="false" timestamp="<%=focolaio.getDataProvenienzaRegionale() %>"/></td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Animali di provenienza extraregionale</dhv:label>
				</td>
				<td>
				  <% if(focolaio.getProvenienzaExtraRegionale() == true) { %>
				    <input type="checkbox" name="provenienzaExtraRegionale" checked />
				  <%}else { %>
				        <input type="checkbox" name="provenienzaExtraRegionale" />
				      <%} %>   
				   
				&nbsp;&nbsp;Data ultima introduzione <zeroio:dateSelect
					form="focolai" field="dataProvenienzaExtraRegionale"
					showTimeZone="false" timestamp="<%=focolaio.getDataProvenienzaRegionale() %>"/></td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Pascolo infetto</dhv:label>
				</td>
				<td>
				<% if(focolaio.getPascoloInfetto() == true) { %>
				    <input type="checkbox" name="pascoloInfetto" checked/>
				    <%}else { %>
				      <input type="checkbox" name="pascoloInfetto"/> 
				      <%} %>
				</td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Reinfezione</dhv:label>
				</td>
				<td>
				 	<% if(focolaio.getReinfezione() == true) { %>
				        <input type="checkbox" name="reinfezione" checked/>
				        <%}else { %>
				         <input type="checkbox" name="reinfezione"/> 
				      <%} %>  
				  
				  
				 </td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Cani infetti</dhv:label>
				</td>
				<td>
				<% if(focolaio.getCaniInfetti() == true) { %>
				  <input type="checkbox" name="caniInfetti" checked/>
				    <%}else { %>
				         <input type="checkbox" name="caniInfetti"/> 
				      <%} %>  
				  </td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Monta esterna</dhv:label>
				</td>
				<td>
					<% if(focolaio.getMontaEsterna() == true) { %>
				   <input type="checkbox" name="montaEsterna" checked/>
				    <%}else { %>
				         <input type="checkbox" name="montaEsterna"/> 
				      <%} %>  
				   </td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Iatrogena</dhv:label>
				</td>
				<td>
				<% if(focolaio.getIatrogena() == true) { %>
				 <input type="checkbox" name="iatrogena" checked/></td>
				 <%}else { %>
				         <input type="checkbox" name="iatrogena"/> 
				      <%} %> 
			</tr>
		</table>
<br/>
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
					   ArrayList introdotti = focolaio.getIntrodotti();
					   ArrayList ammalati = focolaio.getAmmalati();
					   ArrayList morti = focolaio.getMorti();
					   ArrayList denuncia = focolaio.getDenuncia();
					%>

					<tr>

						<td align="center"><input type="text"  size="20"
							name="specie1" value="<%=specie.get(0)%>"/>
						<input type="hidden"  size="20"
							name="denuncia1" value="<%=denuncia.get(0)%>"/>	
						</td>


						<td align="center"><input type="text"   size="7"
							name="complessivo1" onBlur="controlloNumeri(this)"  value="<%=complessivo.get(0)%>"/></td>

						<td align="center"><input type="text"   size="7"
							name="natiStalla1" onBlur="controlloNumeri(this)" value="<%=natiStalla.get(0)%>"/></td>

						<td align="center"><input type="text"  size="7"
							name="introdotti1" onBlur="controlloNumeri(this)" value="<%=introdotti.get(0)%>"/></td>

						<td align="center"><input type="text"   size="7"
							name="ammalati1" onBlur="controlloNumeri(this)" value="<%=ammalati.get(0)%>"/></td>


						<td align="center"><input type="text"  size="7" name="morti1" onBlur="controlloNumeri(this)" value="<%=morti.get(0)%>"/>
						</td>

					</tr>
					
					

					<tr>

						<td align="center"><input type="text"   size="20"
							name="specie2" value="<%=specie.get(1)%>"/>
						<input type="hidden"  size="20"
							name="denuncia2" value="<%=denuncia.get(1)%>"/>		
						</td>


						<td align="center"><input type="text"  size="7"
							name="complessivo2" onBlur="controlloNumeri(this)" value="<%=complessivo.get(1)%>"/></td>

						<td align="center"><input type="text"  size="7"
							name="natiStalla2" onBlur="controlloNumeri(this)" value="<%=natiStalla.get(1)%>"/></td>

						<td align="center"><input type="text"   size="7"
							name="introdotti2" onBlur="controlloNumeri(this)" value="<%=introdotti.get(1)%>" /></td>

						<td align="center"><input type="text"   size="7"
							name="ammalati2" onBlur="controlloNumeri(this)" value="<%=ammalati.get(1)%>"/></td>


						<td align="center"><input type="text"   size="7" name="morti2" onBlur="controlloNumeri(this)" value="<%=morti.get(1)%>"/>
						</td>

					</tr>
					
					

					<tr>

						<td align="center"><input type="text"  size="20"
							name="specie3" value="<%=specie.get(2)%>"/>
						<input type="hidden"  size="20"
							name="denuncia3" value="<%=denuncia.get(2)%>"/>		
						</td>


						<td align="center"><input type="text" size="7"
							name="complessivo3" onBlur="controlloNumeri(this)" value="<%=complessivo.get(2)%>"/></td>

						<td align="center"><input type="text"  size="7"
							name="natiStalla3" onBlur="controlloNumeri(this)" value="<%=natiStalla.get(2)%>"/></td>

						<td align="center"><input type="text"  size="7"
							name="introdotti3" onBlur="controlloNumeri(this)" value="<%=introdotti.get(2)%>"/></td>

						<td align="center"><input type="text" size="7"
							name="ammalati3" onBlur="controlloNumeri(this)" value="<%=ammalati.get(2)%>"/></td>


						<td align="center"><input type="text" size="7" name="morti3" onBlur="controlloNumeri(this)" value="<%=morti.get(2)%>"/>
						</td>

					</tr>
					
					

					<tr>

						<td align="center"><input type="text"  size="20"
							name="specie4" value="<%=specie.get(3)%>"/>
						<input type="hidden"  size="20"
							name="denuncia4" value="<%=denuncia.get(3)%>"/>		
						</td>


						<td align="center"><input type="text" size="7"
							name="complessivo4" onBlur="controlloNumeri(this)" value="<%=complessivo.get(3)%>"/></td>

						<td align="center"><input type="text"  size="7"
							name="natiStalla4" onBlur="controlloNumeri(this)" value="<%=natiStalla.get(3)%>"/></td>

						<td align="center"><input type="text"  size="7"
							name="introdotti4" onBlur="controlloNumeri(this)" value="<%=introdotti.get(3)%>"/></td>

						<td align="center"><input type="text" size="7"
							name="ammalati4" onBlur="controlloNumeri(this)" value="<%=ammalati.get(3)%>"/></td>


						<td align="center"><input type="text"  size="7" name="morti4" onBlur="controlloNumeri(this)" value="<%=morti.get(3)%>" />
						</td>

					</tr>
					
					

					<tr>

						<td align="center"><input type="text"  size="20"
							name="specie5" value="<%=specie.get(4)%>"/>
						<input type="hidden"  size="20"
							name="denuncia5" value="<%=denuncia.get(4)%>"/>		
						</td>


						<td align="center"><input type="text"  size="7"
							name="complessivo5" onBlur="controlloNumeri(this)" value="<%=complessivo.get(4)%>"/></td>

						<td align="center"><input type="text" size="7"
							name="natiStalla5" onBlur="controlloNumeri(this)" value="<%=natiStalla.get(4)%>"/></td>

						<td align="center"><input type="text"  size="7"
							name="introdotti5" onBlur="controlloNumeri(this)" value="<%=introdotti.get(4)%>"/></td>

						<td align="center"><input type="text" maxlength="50" size="7"
							name="ammalati5" onBlur="controlloNumeri(this)" value="<%=ammalati.get(4)%>"/></td>


						<td align="center"><input type="text" size="7" name="morti5" onBlur="controlloNumeri(this)" value="<%=morti.get(3)%>"/>
						</td>

					</tr>

				</table>

				</td>
				
			</tr>


		</table>
		
		
		
		<br/>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">

			<tr>
				<th colspan="2"><strong><dhv:label
					name="acque classificate.decrddeto">Provvedimenti</dhv:label></strong>
				</th>
			</tr>
			
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Sindaco</dhv:label>
				</td>
				<td>Data <zeroio:dateSelect form="focolai" field="dataProvvedimenti"
					showTimeZone="false" timestamp="<%=focolaio.getDataProvvedimenti() %>"/> Numero dei provvedimenti  <input type="text" size="30" value="<%=focolaio.getNumeroProvvedimenti() %>" name="numeroProvvedimenti" />
					
				</td>
			</tr>
			
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Proposte</dhv:label>
				</td>
				<td>
				   <input type="text" size="30" name="proposteAdozione"  value="<%=focolaio.getProposteAdozione() %>" />	
				</td>
			</tr>
			
				<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Date trattamenti immunizzanti</dhv:label>
				</td>
				<td><zeroio:dateSelect form="focolai" field="dataImmunizzanti" timestamp="<%=focolaio.getDataImmunizzanti() %>"
					showTimeZone="false" /> 
					
				</td>
			</tr>
			
				<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Osservazioni</dhv:label>
				</td>
				<td>
				   <input type="text" size="50" name="osservazioni" value="<%=focolaio.getOsservazioni() %>" />	
				</td>
			</tr>
			
		</table>	
		
		
		
		
		


		</td>

	</tr>

</table>


<input onclick="return checkForm();" type="submit"
	value="<dhv:label name="button.save">Save</dhv:label>"></form>


