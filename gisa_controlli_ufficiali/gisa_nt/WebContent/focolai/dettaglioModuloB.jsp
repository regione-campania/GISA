<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.allevamenti.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="focolaio" class="org.aspcfs.modules.focolai.base.Focolaio" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="Asl" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%if (refreshUrl!=null && !"".equals(refreshUrl)){ %>
<script language="JavaScript" TYPE="text/javascript">
parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>
<%}%>

	<script type="text/javascript">

	function apriPopupB(id)
	{
		window.open('Focolai.do?command=StampaModuloB&focolaioId='+id,'miaFinestra','width=700,height=600 ,toolbar=yes, location=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes');
	
	}	
	
	
	</script>


<dhv:evaluate if="<%= !isPopup(request) %>">
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
		<dhv:label name="focolai.aggiungicc">Scheda Focolaio</dhv:label></td>
	</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:permission name="allevamenti-allevamenti-report-view">
  <table width="100%" border="0">
    <tr>
      <td nowrap align="right">
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="Allevamenti.do?command=PrintReport&file=allevamenti.xml&id=<%= OrgDetails.getId() %>;"><dhv:label name="allevamenti.osa.print">Stampa Scheda Allevamenti</dhv:label></a>
      </td>
    </tr>
  </table>
</dhv:permission>




<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>
<dhv:container name="allevamenti" selected="focolai" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="focolai-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='Allevamenti.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>&focolaioId=<%= focolaio.getFocolaioId() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="focolai-edit"><input type="button" value="Modifica"	onClick="javascript:window.location.href='Focolai.do?command=ModificaModuloB&orgId=<%= OrgDetails.getOrgId() %>&focolaioId=<%= focolaio.getFocolaioId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="focolai-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='Allevamenti.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
 <%--  
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="focolai-delete"><input type="button" value="<dhv:label name="allevamenti.allevamenti_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('Focolai.do?command=Elimina&id=<%=OrgDetails.getId()%>&popup=true','Focolai.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
 --%> 
</dhv:evaluate>
<dhv:permission name="focolai-edit,focolai-delete"><br>&nbsp;</dhv:permission>

<%-- 
<div align="right"><a href="Focolai.do?command=ModuloChiusura&focolaioId=<%=focolaio.getFocolaioId() %>"><dhv:label name="accounts.accountssss">Chiudi il focolaio</dhv:label></a><div>
--%>

<input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>">
<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">



<table cellpadding="2" cellspacing="2" border="0" width="100%">
<div align="right">
  <a href="Focolai.do?command=DettaglioLista&focolaioId=<%=focolaio.getIdFocolaioChiuso() %>">Modulo A </a>
</div>
<%request.getSession().setAttribute("abilitaModulo","no"); %> <%-- serve a disabilitare una nuova apertura del modulo B  --%>

<br><br>

<div align="right">
    <a href="javascript:apriPopupB(<%=focolaio.getFocolaioId() %>)" >Stampa Modulo B</a>
</div>

	<tr>
		<td width="100%" valign="top">


		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
	<tr>
				<th colspan="2"><strong><dhv:label name="titolo">Denuncia di malattia infettiva e diffusiva degli animali</dhv:label></strong>
				</th>
			</tr>

 
			<!-- modifiche d.dauria -->
			<tr >
				<td class="formLabel"><dhv:label name="allevamenti.site">Site</dhv:label>
				</td>
				<td width="90%"><%=Asl.getSelectedValue(OrgDetails.getSiteId())%>

				</td>
			</tr>


			<dhv:evaluate if="<%=hasText(OrgDetails.getName())%>">
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Denominazione</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getName())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>

			<dhv:evaluate if="<%=hasText(OrgDetails.getAccountNumber())%>">
				<tr class="containerBody">
					<td class="formLabel"><dhv:label
						name="organization.accountNumbera">Codice Azienda</dhv:label></td>
					<td><%=toHtml(OrgDetails.getAccountNumber())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>

			<dhv:evaluate if="<%=hasText(OrgDetails.getSpecieAllev())%>">
				<tr class="containerBody">
					<td class="formLabel"><dhv:label
						name="organization.specieAlleva">Specie Allevata</dhv:label></td>
					<td><%=toHtml(OrgDetails.getSpecieAllev())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>

			<dhv:evaluate if="<%=hasText(OrgDetails.getPartitaIva())%>">
				<tr class="containerBody">
					<td class="formLabel"><dhv:label name="">Partita IVA / Codice Fiscale</dhv:label>
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
					<td class="formLabel" valign="top">Sede Legale</td>
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
				<td class="formLabel"><dhv:label name="loc">Stato</dhv:label>
				</td>
				<td>
				  <% if(focolaio.getApertura() == true ) {%>
				   <% out.println("APERTO"); %>
				  <%} else { %>
				    <% out.println("CHIUSO"); %>
				</td>
			</tr>
			<tr>
		<%} %>
			
			<% if(!focolaio.getMalattia().equals("")) {%>
			<tr>
				<td class="formLabel"><dhv:label name="loc">Malattia</dhv:label>
				</td>
				<td>
				   <%=focolaio.getMalattia() %>
				</td>
			</tr>
			<tr>
		<%} %>
		<%--  
		<% if(!focolaio.getSpecieAnimale().equals("")) {%>
			<tr>
				<td class="formLabel"><dhv:label name="loc">Specie</dhv:label>
				</td>
				<td>
				   <%=focolaio.getSpecieAnimale() %>
				</td>
			</tr>
			<tr>
		<%} %>
--%>
		<% if(!focolaio.getLocalita().equals("")) {%>
			<tr>
				<td class="formLabel"><dhv:label name="loc">Località della stalla o del pascolo infetto</dhv:label>
				</td>
				<td>
				   <%=focolaio.getLocalita() %>
				</td>
			</tr>
			<tr>
		<%} %>
			<% if(focolaio.getDataSospetto()!=null) {%>
			  <tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Data del sospetto</dhv:label>
				</td>
				<td>  <zeroio:tz timestamp="<%=focolaio.getDataSospetto() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />
			</td>
			</tr>
			<%} %>
			
			<% if(focolaio.getDataProva()!=null) {%>
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Data della prova</dhv:label>
				</td>
				<td>
		      <zeroio:tz timestamp="<%=focolaio.getDataProva() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />
				
				</td>
			</tr>
			<%} %>
			
			<% if(focolaio.getDataApertura()!=null) {%>
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Data apertura</dhv:label>
				</td>
				<td>
			  <zeroio:tz timestamp="<%=focolaio.getDataApertura() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />
				
				</td>
			</tr>
		    <%} %>
			
			
		<% if(!focolaio.getOrigineMalattia().equals("")) {%>
			<tr>
				<td class="formLabel"><dhv:label name="loc">Origine malattia</dhv:label>
				</td>
				<td>
				  <%=focolaio.getOrigineMalattia()%>
				</td>
			</tr>
		 <%} %>	
		
		<%-- 	
			<tr>
				<td></td>
				<td>Malattie soggette a piani di risanamento
				</td>
			</tr>
			
		  
			<tr>
				<td class="formLabel"><dhv:label name="loc">Animali di provenienza regionale</dhv:label>
				</td>
				<td>
				 <% if( focolaio.getProvenienzaRegionale() != false){ %>
				   <input type="checkbox" disabled checked="checked">
				 <% }else {%>
				     <input type="checkbox" disabled >
				     <%} %>
	    <%if(focolaio.getDataProvenienzaRegionale() != null){ %>
		         		&nbsp;&nbsp;Data ultima introduzione 
								<zeroio:tz timestamp="<%=focolaio.getDataProvenienzaRegionale() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />
				
				<%} %>
				</td>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="loc">Animali di provenienza extraregionale</dhv:label>
				</td>
				<td>
				<% if( focolaio.getProvenienzaExtraRegionale() != false){ %>
				  <input type="checkbox" disabled checked="checked">
				   <% }else {%>
				     <input type="checkbox" disabled >
				     <%} %>
		  <%if(focolaio.getDataProvenienzaExtraRegionale() != null){ %>		     
				&nbsp;&nbsp;Data ultima introduzione 
				<zeroio:tz timestamp="<%=focolaio.getDataProvenienzaExtraRegionale() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />
				<%} %>
				</td>
			</tr>
--%>
   
		</table>
		
		
<br/>


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


		
		
		<br/>
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">

			<tr>
				<th colspan="2"><strong><dhv:label
					name="acque classificate.decrddeto">Provvedimenti</dhv:label></strong></th>
			</tr>

			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Sindaco</dhv:label>
				</td>
				<td>
				<%if(focolaio.getDataProvvedimenti() != null) {%>
				   Data <zeroio:tz timestamp="<%= focolaio.getDataProvvedimenti() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />
				  Numero dei provvedimenti  <%=focolaio.getNumeroProvvedimenti() %>
				<%} %>	
				</td>
			</tr>

			<%if(!focolaio.getProposteAdozione().equals("")){ %>	
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Proposte Settore Veterinario Regionale</dhv:label>
				</td>
				<td>
				  <%=focolaio.getProposteAdozione() %>
				</td>
			</tr>
			<%} %>

			<%if(focolaio.getDataUltimoCaso() != null) {%>		
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Date esito (guarigione /morte/ abbattimento) ultimo caso malattia</dhv:label>
				</td>
				<td>
				<zeroio:tz timestamp="<%=focolaio.getDataUltimoCaso() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />
				</td>
			</tr>
			<%} %>

        	<%if(focolaio.getDataRevocaSindaco() != null) {%>					
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Date revoca provvedimenti del Sindaco</dhv:label>
				</td>
				<td><zeroio:tz timestamp="<%=focolaio.getDataRevocaSindaco() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />
				</td>
			</tr>
			<%} %>

			<%if(!focolaio.getProposteRevoca().equals("")){ %>				
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Proposte di revoca</dhv:label>
				</td>
				<td><%=focolaio.getProposteRevoca() %></td>
			</tr>
			<%} %>

			<%if(!focolaio.getOsservazioni().equals("")){ %>							
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Osservazioni</dhv:label>
				</td>
				<td><%=focolaio.getOsservazioni() %></td>
			</tr>
           <%} %>
		</table>
		
<%-- 		
		
		
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
				<td>
				<%if(focolaio.getDataProvvedimenti() != null) {%>
				   Data <zeroio:tz timestamp="<%= focolaio.getDataProvvedimenti() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />
				  Numero dei provvedimenti  <%=focolaio.getNumeroProvvedimenti() %>
				<%} %>	
				</td>
			</tr>
			
			<%if(!focolaio.getProposteAdozione().equals("")){ %>
			<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Proposte</dhv:label>
				</td>
				<td>
				   <%=focolaio.getProposteAdozione() %>
				</td>
			</tr>
			<%} %>
			
			<%if(focolaio.getDataImmunizzanti() != null) {%>
				<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Date trattamenti immunizzanti</dhv:label>
				</td>
				<td>
					<zeroio:tz timestamp="<%= focolaio.getDataImmunizzanti() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />
				</td>
			</tr>
			<%} %>
			
			<%if(!focolaio.getOsservazioni().equals("")) {%>
				<tr>
				<td class="formLabel"><dhv:label name="acque classificate.cla">Osservazioni</dhv:label>
				</td>
				<td>
				   <zeroio:tz timestamp="<%= focolaio.getOsservazioni()  %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />
				</td>
			</tr>
			<%} %>
		</table>	
		
		--%>
		
		
		


		</td>

	</tr>

</table>



<br />

<br />

<table>
<tr>
   <td></td>
</tr>
</table>


<br/>



 


<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="allevamenti.allevamenti_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti.allevamenti_calls_list.Entered">Entered</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= OrgDetails.getEnteredBy() %>" />
      <zeroio:tz timestamp="<%= OrgDetails.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="allevamenti.allevamenti_contacts_calls_details.Modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= OrgDetails.getModifiedBy() %>" />
      <zeroio:tz timestamp="<%= OrgDetails.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
</table>
<dhv:permission name="allevamenti-allevamenti-edit,allevamenti-allevamenti-delete"><br></dhv:permission>
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="allevamenti-allevamenti-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='Allevamenti.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="allevamenti-allevamenti-edit"><input type="button" value="Modifica"	onClick="javascript:window.location.href='Focolai.do?command=ModificaModuloB&orgId=<%= OrgDetails.getOrgId() %>&focolaioId=<%= focolaio.getFocolaioId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="allevamenti-allevamenti-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='Allevamenti.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
 <%--  
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="allevamenti-allevamenti-delete"><input type="button" value="<dhv:label name="allevamenti.allevamenti_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('Allevamenti.do?command=Elimina&id=<%=OrgDetails.getId()%>&popup=true','Focolai.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
 --%> 
</dhv:evaluate>
</dhv:container>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
