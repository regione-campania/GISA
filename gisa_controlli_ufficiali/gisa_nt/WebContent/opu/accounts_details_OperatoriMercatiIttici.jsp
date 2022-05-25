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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="org.aspcfs.modules.stabilimenti.base.SottoAttivita,java.util.*,java.text.DateFormat,org.aspcfs.modules.stabilimenti.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="OrgCategoriaRischioList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.stabilimenti.base.Organization"
	scope="request" />
<jsp:useBean id="SICCodeList"
	class="org.aspcfs.modules.admin.base.SICCodeList" scope="request" />
<jsp:useBean id="applicationPrefs"
	class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
	<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
	
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" 	scope="request" />
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="categoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="imballataList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="tipoAutorizzazioneList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request" />
<jsp:useBean id="elencoSottoAttivita" class="java.util.ArrayList" scope="request" />

<jsp:useBean id="impreseAssociateMercatoIttico" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="stabilimentiAssociateMercatoIttico" class="java.util.ArrayList" scope="request" />

<%@ include file="../initPage.jsp"%>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%if (refreshUrl!=null && !"".equals(refreshUrl)) { %>
<script language="JavaScript" TYPE="text/javascript">
	parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>
<%
}
%>


<dhv:evaluate if="<%= !isPopup(request) %>">

	<%-- Trails --%>
		<table class="trails" cellspacing="0">
			<tr>
				<td>
					<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Stabilimenti</dhv:label></a> > 
					<a href="RicercaUnica.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
					<a href="OpuStab.do?command=Details&stabId=<%=OrgDetails.getIdMercatoIttico()%>"><dhv:label name="">Mercato Ittico</dhv:label></a> >
					<dhv:label name="">Scheda Operatore Mercato Ittico</dhv:label>
				</td>
			</tr>
		</table>
	<%-- End Trails --%>


	
</dhv:evaluate>
<% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>



<%
String param1 = "orgId=" + OrgDetails.getOrgId();
%>
<dhv:container name="operatori_mercati_ittici" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">

	<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
	<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
		<dhv:permission name="stabilimenti-stabilimenti-edit">
			<input type="button"
				value="Ripristina"
				onClick="javascript:window.location.href='Stabilimenti.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
		</dhv:permission>
	</dhv:evaluate>
	<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
		<%--dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
			<dhv:permission name="stabilimenti-stabilimenti-edit">
				<input type="button"
					value="Modifica"
					onClick="javascript:window.location.href='Stabilimenti.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';">
			</dhv:permission>
		</dhv:evaluate--%>
		
		<dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
			<dhv:permission name="stabilimenti-stabilimenti-delete">
				<input type="button"
					value="<dhv:label name="stabilimenti.stabilimenti_details.DeleteAccount">Delete Account</dhv:label>"
					onClick="javascript:popURLReturn('Stabilimenti.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Stabilimenti.do?command=Search', 'Delete_account','320','200','yes','no');">
			</dhv:permission>
		</dhv:evaluate>
	</dhv:evaluate>
	<dhv:permission
		name="stabilimenti-stabilimenti-edit,stabilimenti-stabilimenti-delete">
		<br>&nbsp;</dhv:permission>
		
		
	
		
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="stabilimenti.stabilimenti_details.PrimaryInformation">Primary Information</dhv:label></strong>
			</th>
		</tr>

		<dhv:include name="stabilimenti-name" none="true">
			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1">Identificativo univoco
				</td>
<%-- 				<td><%=toHtmlValue(OrgDetails.getNumAut()+"-"+ OrgDetails.getOperatoreItticoNumeroBox() )%>&nbsp;</td> --%>
				<td><%=toHtmlValue(OrgDetails.getOperatoreItticoNumeroBox())%>&nbsp;</td>
			</tr>
		</dhv:include>

		<dhv:include name="stabilimenti-name" none="true">
			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
					name="stabilimenti.stabilimenti_add.OrganizationName">Organization Name</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getName())%>&nbsp;</td>
			</tr>
		</dhv:include>
		
		<tr class="containerBody">
				<td nowrap class="formLabel">Box N°.</td>
				<td><%=toHtmlValue(OrgDetails.getOperatoreItticoNumeroBox())%>&nbsp;</td>
		</tr>
		
		<dhv:evaluate if="<%= hasText(OrgDetails.getBanca())%>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Approval Number Mercato Ittico di appartenenza</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getBanca())%></td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label
					name="organization.accountNumber">Account Number</dhv:label></td>
				<td><%=toHtml(OrgDetails.getAccountNumber())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate if="<%= hasText(OrgDetails.getPartitaIva()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">Partita IVA
				</td>
				<td><%=toHtml(OrgDetails.getPartitaIva())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscale()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel">Codice Fiscale
				</td>
				<td><%=toHtml(OrgDetails.getCodiceFiscale())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>
		

 <% if(Audit!=null){ %>
  
  <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Categoria di Rischio</dhv:label>
      </td>
      <td>
         <%= (((OrgDetails.getCategoriaRischio()>0))?(OrgDetails.getCategoriaRischio()):("3"))%>
      </td>
    </tr>
  <% } %>

  

		<%--
		if (OrgDetails.getAccountSize() > 0) {
		%>
		<tr class="containerBody">
			<td name="accountSize1" id="accountSize1" nowrap class="formLabel">
			<dhv:label name="osa.categoriaRischio" /></td>
			<td><%=OrgCategoriaRischioList.getSelectedValue(OrgDetails
								.getAccountSize())%>
			</td>
		</tr>
		<%
			}
			
		--%>
		<%-- %><tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label
				name="osa.livelloRischio">Punteggio Totale </dhv:label></td>
			<td><%=OrgDetails.getLivelloRischioFinale()%>&nbsp; al <%=(new SimpleDateFormat("dd/MM/yyyy"))
								.format(OrgDetails.getDataAudit())%>
			</td>
		</tr>--%>
		
		<%--<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label
				name="osaa.livelloRischio">Categoria di Rischio</dhv:label></td>
			<td>
			<%
			Integer supp = OrgDetails.getLivelloRischioFinale();
			%> <%=(((OrgDetails.getLivelloRischioFinale() >= 1) && (OrgDetails
									.getLivelloRischioFinale() <= 100)) ? (" 1 ")
									: (((OrgDetails.getLivelloRischioFinale() <= 200) && ((OrgDetails
											.getLivelloRischioFinale() >= 101))) ? (" 2 ")
											: (((OrgDetails
													.getLivelloRischioFinale() <= 300) && ((OrgDetails
													.getLivelloRischioFinale() >= 201))) ? (" 3 ")
													: (((OrgDetails
															.getLivelloRischioFinale() <= 400) && ((OrgDetails
															.getLivelloRischioFinale() >= 301))) ? (" 4 ")
															: ((OrgDetails
																	.getLivelloRischioFinale() >= 401) ? (" 5 ")
																	: ((((OrgDetails
																			.getLivelloRischioFinale() < 1) && (OrgDetails
																			.getLivelloRischio() < 1))
																			|| (supp == null) ? (" 3 ")
																			: ("-"))))))))%>
			</td>
		</tr>
		<tr class="containerBody" style="display: none">
			<td nowrap class="formLabel"><dhv:label
				name="osa.livelloRischio" /></td>
			<td>-&nbsp;</td>
		</tr>
		<tr class="containerBody" style="display: none">
			<td nowrap class="formLabel"><dhv:label
				name="osaa.livelloRischio">Categoria di Rischio</dhv:label></td>
			<td>-&nbsp;</td>
		</tr>
		<%
					if ((OrgDetails.getAccountSize() > 0)
					&& (OrgDetails.getLivelloRischioFinale() == -1)) {
		%>
		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label
				name="osaa.livelloRischio">Prossimo controllo</dhv:label></td>
			<td>Checklist assegnata ma livello rischio non ancora calcolato.
			</td>
		</tr>
		<%
		} else {
		%>
		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label
				name="osaa.livelloRischio">Prossimo controllo</dhv:label></td>
			<td>
			<%
					if (((OrgDetails.getLivelloRischioFinale() < 1) && (OrgDetails
					.getLivelloRischio() < 1))
					|| (supp == null)) {
			%><dhv:label
				name="">Nessuna Check List</dhv:label> <%
 }
 %> <%
 		if ((OrgDetails.getLivelloRischioFinale() >= 1)
 		&& (OrgDetails.getLivelloRischioFinale() <= 100)) {
 %><%=(new SimpleDateFormat("dd/MM/yyyy"))
									.format(org.aspcfs.utils.DateAudit
											.addMonth(
													OrgDetails.getDataAudit(),
													48))%>
			<%
			}
			%> <%
 		if ((OrgDetails.getLivelloRischioFinale() <= 200)
 		&& (OrgDetails.getLivelloRischioFinale() >= 101)) {
 			%>
			<%=(new SimpleDateFormat("dd/MM/yyyy"))
									.format(org.aspcfs.utils.DateAudit
											.addMonth(
													OrgDetails.getDataAudit(),
													36))%>
			<%
			}
			%> <%
 		if ((OrgDetails.getLivelloRischioFinale() <= 300)
 		&& (OrgDetails.getLivelloRischioFinale() >= 201)) {
 			%>
			<%=(new SimpleDateFormat("dd/MM/yyyy"))
									.format(org.aspcfs.utils.DateAudit
											.addMonth(
													OrgDetails.getDataAudit(),
													24))%>
			<%
			}
			%> <%
 		if ((OrgDetails.getLivelloRischioFinale() <= 400)
 		&& (OrgDetails.getLivelloRischioFinale() >= 301)) {
 			%>
			<%=(new SimpleDateFormat("dd/MM/yyyy"))
									.format(org.aspcfs.utils.DateAudit
											.addMonth(
													OrgDetails.getDataAudit(),
													12))%>
			<%
			}
			%> <%
 		if ((OrgDetails.getLivelloRischioFinale() > 401)) {
 		%><%=(new SimpleDateFormat("dd/MM/yyyy"))
									.format(org.aspcfs.utils.DateAudit
											.addMonth(
													OrgDetails.getDataAudit(),
													6))%>
			<%
			}
			%> <%
 			//="OrgDetails.getDataAudit()"
 			%>
			</td>
		</tr>

		<%
		}
		%>
		--%>
		
		</table>
		
		<br>
		<br>
		
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
	    <th colspan="2">
	      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
	     
	    </th>
	</tr>
	
	<dhv:evaluate if="<%= (hasText(OrgDetails.getCodiceFiscaleRappresentante())) %>">			
		<tr class="containerBody">
				<td nowrap class="formLabel">
	      			Codice Fiscale
				</td>
				<td>
	         	<%= toHtml((OrgDetails.getCodiceFiscaleRappresentante()) )%>&nbsp; 
				</td>
			</tr>
	</dhv:evaluate>
			
			<dhv:evaluate if="<%= (hasText(OrgDetails.getNomeRappresentante())) %>">		
			<tr class="containerBody">
				<td nowrap class="formLabel">
	      			Nome
				</td>
				<td>
	         	<%= toHtml((OrgDetails.getNomeRappresentante())) %>&nbsp; 
				</td>
			</tr>
			</dhv:evaluate>
			
	<dhv:evaluate if="<%= (hasText(OrgDetails.getCognomeRappresentante())) %>">
		<tr class="containerBody">
			<td nowrap class="formLabel">
	   			Cognome
			</td>
			<td>
	         	<%= toHtml((OrgDetails.getCognomeRappresentante())) %>&nbsp; 
			</td>
		</tr>
	</dhv:evaluate>
	
	<dhv:evaluate if="<%= (OrgDetails.getDataNascitaRappresentante() != null)  %>">
	   <tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="">Data Nascita</dhv:label>
	    </td>
	    <td>
	    
	        <%= ((OrgDetails.getDataNascitaRappresentante()!=null)?(toHtml(DateUtils.getDateAsString(OrgDetails.getDataNascitaRappresentante(),Locale.ITALY))):("")) %>
	         </td>
	  </tr>
	  
	</dhv:evaluate>
			<dhv:evaluate if="<%= (hasText(OrgDetails.getLuogoNascitaRappresentante())) %>">			
			<tr class="containerBody">
				<td nowrap class="formLabel">
	      			<dhv:label name="">Comune di nascita</dhv:label>
				</td>
				<td>
	         	<%= toHtml(OrgDetails.getLuogoNascitaRappresentante())%>&nbsp; 
				</td>
			</tr>
	</dhv:evaluate>
</table>

	<br>
	<br>
		
		
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  			<tr>
    			<th colspan="2"><strong><dhv:label name="">Indirizzo</dhv:label></strong></th>
  			</tr>
			<%
				Iterator iaddress = OrgDetails.getAddressList().iterator();
				if (iaddress.hasNext()) {
					while (iaddress.hasNext()) {
						OrganizationAddress thisAddress = (OrganizationAddress) iaddress
						.next();
			%>    
    		<tr class="containerBody">
      			<td nowrap class="formLabel" valign="top"><%=toHtml(thisAddress.getTypeName())%></td>
      			<td>
        			<%=toHtml(thisAddress.toString())%>&nbsp;<br/><%=thisAddress.getGmapLink() %>
        			<dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">        
          				<dhv:label name="account.primary.brackets">(Primary)</dhv:label>
        			</dhv:evaluate>
      			</td>
    		</tr>
			<%
				}
				} else {
			%>
    		<tr class="containerBody">
      			<td colspan="2">
        			<font color="#9E9E9E"><dhv:label name="contacts.NoAddresses">No addresses entered.</dhv:label></font>
      			</td>
    		</tr>	
			<%
				}
				%>
		</table>
		
		<br>
		<br>
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  			<tr>
    			<th colspan="2"><strong><dhv:label name="">Informazioni Aggiuntive</dhv:label></strong></th>
  			</tr>
			    
			<tr class="containerBody">
				<td nowrap class="formLabel">
	      			<dhv:label name="">Stato</dhv:label>
				</td>
				<td>
	         	<%= toHtml(OrgDetails.getNotes())%>&nbsp; 
				</td>
			</tr>
		</table>
		
		</dhv:container>
		<%=addHiddenParams(request,
									"popup|popupType|actionId")%>
		<%
		if (request.getParameter("return") != null) {
		%>
		<input type="hidden" name="return"
			value="<%=request.getParameter("return")%>">
		<%
		}
		%>
		<%
		if (request.getParameter("actionplan") != null) {
		%>
		<input type="hidden" name="actionplan"
			value="<%=request.getParameter("actionplan")%>">
		<%
		}
		%>