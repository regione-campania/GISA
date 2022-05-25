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
	import="org.aspcfs.modules.osmregistrati.base.SottoAttivita,java.util.*,java.text.DateFormat,java.text.SimpleDateFormat,java.util.Date,org.aspcfs.modules.osmregistrati.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="OrgCategoriaRischioList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.osmregistrati.base.Organization"
	scope="request" />
<jsp:useBean id="SICCodeList"
	class="org.aspcfs.modules.admin.base.SICCodeList" scope="request" />
<jsp:useBean id="applicationPrefs"
	class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
	<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
	
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="categoriaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="imballataList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="tipoAutorizzazioneList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request" />
<jsp:useBean id="elencoSottoAttivita" class="java.util.ArrayList"
	scope="request" />
<jsp:useBean id="Messaggio" class="java.lang.String" scope="request"/>
	
<%@ include file="../initPage.jsp"%>
<!-- script language="javascript">
    document.onkeydown = f5() ;
</script-->
<style>
	
	.dropbtn {
	}

	.dropdown {
	  position: relative;
	  display: inline-block;
	}
	
	.dropdown-content {
	  display: none;
	  position: absolute;
	  border-style: solid;
  	  border-width: 1px;
	  background-color: #E8E8E8;
	  overflow: auto;
	  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
	  z-index: 1;
	}
	
	.dropdown-content a {
	  display: block;
	  padding: 6px 16px;
	  
	}
	
	
	.show {display: block;}
	
	/* Change color of dropdown links on hover */
	.dropdown-content a:hover {background-color: #D0D0D0}

</style>

<style>

.ovale { border-style:solid; border-color:#405c81; border-width:1px; }



</style>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>

<script>

function openPopupLarge(url){
	
	  var res;
    var result;
    	  window.open(url,'popupSelect',
          'height=600px,width=1000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}		
	
function isOsmAssociabile() {
	<%
	//lettura parametro 'osmAssociabile'
	String flagOsmAssociabile = (String)request.getParameter("osmAssociabile");
	if (flagOsmAssociabile != null && flagOsmAssociabile.equals("0")) {
	%>		
		return false;
	<%} else {%>
		return true;
	<%}%>
}
  
function confermaRicercaAllevamentoDaOsm() {
	var answer = confirm("Nessun allevamento associato all'OSM.\n\nVuoi cercare un allevamento da associare?")
	if (answer) {
		if (isOsmAssociabile()) {	
			location.href="Allevamenti.do?command=SearchForm&ricercaAllevamentiAssociabiliParameter=1";
		} else {
			alert("OSM non associabile. Comune non ricadente nell'ASL di appartenenza dell'OSM.\n\n");
		}
	}
}
</script>

<body onkeydown="nof5()" onkeyup="nof5()">

<%if (refreshUrl!=null && !"".equals(refreshUrl)) { %>
<script language="JavaScript" TYPE="text/javascript">
	parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>
<%
}
%>

<% if (Messaggio!=null && !Messaggio.equalsIgnoreCase("null") && !Messaggio.equals("")) {%>
<script>
alert('<%=Messaggio%>');
</script>
<% } %>

<%
// nel caso in cui arrivo nella pagina cliccando su "Registrazione 183"
// dalla scheda di un allevamento che non ha OSM associati
// visualizzo il link per l'associazione
Boolean ricercaOsmAssociabiliAttribute = (Boolean)request.getAttribute("ricercaOsmAssociabiliAttribute");
if (ricercaOsmAssociabiliAttribute != null && ricercaOsmAssociabiliAttribute == true) {
	org.aspcfs.modules.allevamenti.base.Organization allevamentoACuiAssociareOsm = (org.aspcfs.modules.allevamenti.base.Organization)session.getAttribute("allevamentoACuiAssociareOsm");
	session.removeAttribute("allevamentoACuiAssociareOsm");
	%>		
	<div align="center">
		<a style="BACKGROUND-COLOR:#BDCFFF; font-weight:bold; font-size:12;" href="AssociaOsmAAziendaZootecnica.do?command=View&orgId=<%=OrgDetails.getId()%>&idAllevamentoDaAssociare=<%=allevamentoACuiAssociareOsm.getOrgId()%>&accountNumberAllevamentoDaAssociare=<%=allevamentoACuiAssociareOsm.getAccountNumber()%>">ASSOCIA QUESTO OSM ALL'ALLEVAMENTO "<%=allevamentoACuiAssociareOsm.getName()%>"</a>
	</div>
	<br/>
	<%
} 
%>

<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td>
				<a href="OsmRegistrati.do">OSM Registrati</a> > 
				
				<%
					if (request.getParameter("return") == null) {
				%>
			
				<a href="OsmRegistrati.do?command=Search"><dhv:label name="stabilimenti.SearchResults">Search Results</dhv:label></a> > 
				
				<%
					} else if (request.getParameter("return").equals("dashboard")) {
				%>
				
				<a href="OsmRegistrati.do?command=Dashboard">Cruscotto</a> > 
				<%
					}
				%>
			<dhv:label name="osm.details">Account Details</dhv:label></td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>

<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %>

<% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>
<dhv:permission name="osmregistrati-osmregistrati-report-view">
	<table width="100%" border="0">
		<tr>
			<%-- aggiunto da d.dauria--%>

			<td nowrap align="right"><img
				src="images/icons/stock_print-16.gif" border="0" align="absmiddle"
				height="16" width="16" /> <a
				href="OsmRegistrati.do?command=PrintReport&file=stabilimenti.xml&id=<%= OrgDetails.getId() %>"><dhv:label
				name="stabilimenti.osa.print">Stampa Scheda stabilimenti</dhv:label></a>
			</td>

			<%-- fine degli inserimenti --%>
		</tr>
	</table>
</dhv:permission>

     <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
<dhv:permission name="">
	<table width="100%" border="0">
		<tr>
			<%-- aggiunto da d.dauria--%>

			<td nowrap align="right">
					
					
 		  <%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda OSM" value="Stampa Scheda OSM"		onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '-1', '-1', '-1', 'osmregistrati', 'SchedaOSM');"--%>
 
   <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda" value="Stampa Scheda"		onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '-1', '-1', '-1', '5');">
 
				
				
			</td>


			<%-- fine degli inserimenti --%>
		</tr>
	</table>
</dhv:permission>

<%
String param1 = "orgId=" + OrgDetails.getOrgId();
%>
<dhv:container name="osmRegistrati" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' >

<%if( (OrgDetails.getStatoLab() != -1   && OrgDetails.getStatoLab() != 0) || (OrgDetails.getStatoLab() == -1 && OrgDetails.getStato() != null && !OrgDetails.getStato().equalsIgnoreCase("attivo")  ) ){ %>
  	
  	<%} 
   else
{
%>

 <%-- <dhv:permission name="osa-cessazione-pregressa-view">
 <div class="ovale">
	 <br>
	 <p><center><b>Per cessare senza importare premere su 'CESSAZIONE OSA'</b></center></p>
	 <br><br>
	 <center>
		<input type="button" value="CESSAZIONE OSA" onclick="openPopUpCessazioneAttivita();"  >
	</center>
<br><br>
</div>


	 </dhv:permission>
	 <jsp:include page="../dialog_cessazione_attivita.jsp">
	 <jsp:param value="<%=OrgDetails.getOrgId() %>" name="idAnagrafica"/>
	 <jsp:param value="OsmRegistrati.do?command=CessazioneAttivita" name="urlSubmitCessazione"/>
	 </jsp:include> --%>
	 

<!-- MODIFICA CESSAZIONE OSA ANCHE SU QUESTO CAVALIERE ------------------------------------>
 	<dhv:permission name="osa-cessazione-pregressa-view">
	
		<center>
			<span style="width:50px;"  >
			 <br>
			 <p style="color:red; font-weight:bold;" >Per cessare senza importare premere su 'CESSAZIONE OSA'</p>
			 <input class="yellowBigButton" type="button" value="CESSAZIONE OSA" onclick="openPopUpCessazioneAttivita();" width="120px;">
			 </span>
			 <br><br>
			 <br>
		</center>
	 </dhv:permission>
	 
	<jsp:include page="../dialog_cessazione_attivita.jsp"> 
		 <jsp:param value="<%=OrgDetails.getOrgId() %>" name="idAnagrafica"/>
		 <jsp:param value="OsmRegistrati.do?command=CessazioneAttivita" name="urlSubmitCessazione"/><jsp:param value="<%=OrgDetails.getDataInizio() != null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date(OrgDetails.getDataInizio().getTime())) : "" %>" name="data_inizio" />
	</jsp:include>
	
<!------------------------------------------------------------------------------------------------> 	 
	 

<dhv:permission name="opu-import-osm-add">
	  
	  <%
	    if(true)
	  	//if(User.getRoleId()!=Role.HD_1LIVELLO && User.getRoleId()!=Role.HD_2LIVELLO)
	  	{
	  
	  %>
	  <div align="center">
			<input type="button"  class="yellowBigButton"
				value="Importa in Anagrafica stabilimenti"
			    onClick="javascript:window.location.href='OpuStab.do?command=CaricaImport&orgId=<%=OrgDetails.getOrgId()%>'">
	  </div>
	  
	  
	  <%
	  	}
	  	else
	  	{
	  		%>
	  			
	  
	  
	  <center><p style="color:red; font-weight:bold;" >Per usare la nuova funzione di import cliccare su 'Completa scheda anagrafica NEW!!!'</p></center>
	  <div align="center">
					<input type="button"  class="greenBigButton" style="color:black !important" value="Completa scheda anagrafica NEW!!!"
			    		onClick="location.href='GestioneAnagraficaAction.do?command=Import&altId=<%=OrgDetails.getOrgId()%>';">
	  </div>
	  
	  <div align="center">
					<input type="button"  value="Importa in Anagrafica stabilimenti (VECCHIA VERSIONE)" style="background-color: grey !important"
			    		onClick="javascript:window.location.href='OpuStab.do?command=CaricaImport&orgId=<%=OrgDetails.getOrgId()%>'">
	  </div>
	  		
	  		<%
	  		
	  	}
	  %>
	  </dhv:permission>
	  

<%} %>
	<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
	<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
		<!--<dhv:permission name="osmregistrati-osmregistrati-edit">
			<input type="button"
				value="Ripristina"
				onClick="javascript:window.location.href='OsmRegistrati.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
		</dhv:permission>-->
	</dhv:evaluate>
	<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
		<dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
		<%if((OrgDetails.getStatoLab() != 1) && (OrgDetails.getStatoLab()!=2)){ %>
			<%-- <dhv:permission name="osmregistrati-osmregistrati-edit">
				<input type="button"
					value="Modifica"
					onClick="javascript:window.location.href='OsmRegistrati.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';">
			</dhv:permission>--%>
			<%} %>
		</dhv:evaluate>
		 <script language="JavaScript" TYPE="text/javascript">
function enable() {
var b = document.getElementById("modB");
var c = document.getElementById("modC");
b.disabled=false;
c.disabled=false;
}
</script>

    <% if (!OrgDetails.getAccountNumber().equals("")){ %>
 	<dhv:permission name="osmregistrati-osmregistrati-generaosa-view">

 		<input disabled="disabled" title= "Genera il Numero di Registrazione" type="button" name="Genera Numero Registrazione" value="Genera Numero Registrazione">

    </dhv:permission>
    
		
    <%} else if( OrgDetails.getAccountNumber().equals("") ) { %>
		<dhv:permission name="osmregistrati-osmregistrati-generaosa-view">

			<input type="button" title= "Genera il Numero di Registrazione" value="Genera Numero Registrazione" name="Genera Numero Registrazione"	onClick="javascript:window.location.href='OsmRegistrati.do?command=GeneraCodiceOsa&orgId=<%= OrgDetails.getOrgId()%>';">

 		</dhv:permission>
		<%} %>
	
	</dhv:evaluate>
	
	 <dhv:permission name="osmregistrati-osmregistrati-delete">
    <input type="button" value="Elimina" onClick="javascript:popURLReturn('OsmRegistrati.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','OsmRegistrati.do?command=Search', 'Delete_account','320','200','yes','no');">
    </dhv:permission>
    
    
   
    
		
<dhv:permission name="note_hd-view">
<jsp:include page="../note_hd/link_note_hd.jsp">
<jsp:param name="riferimentoId" value="<%=OrgDetails.getOrgId() %>" />
<jsp:param name="riferimentoIdNomeTab" value="organization" />
</jsp:include> <br><br>
</dhv:permission>

<jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">
    <jsp:param name="riferimentoIdPreaccettazione" value="<%=OrgDetails.getOrgId() %>" />
    <jsp:param name="riferimentoIdNomePreaccettazione" value="orgId" />
    <jsp:param name="riferimentoIdNomeTabPreaccettazione" value="organization" />
    <jsp:param name="userIdPreaccettazione" value="<%=User.getUserId() %>" />
</jsp:include>


<dhv:permission name="osm-invio-view"> 
<center>
<input style="width:250px" type="button" value="GESTIONE INVIO OSM" onclick="openPopupLarge('GestioneOSM.do?command=Details&riferimentoId=<%=OrgDetails.getOrgId()%>&riferimentoIdNomeTab=organization')"/>
</center>
</dhv:permission>

<% if (1==1) {  %>
<jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="5" />
     </jsp:include>
 <% } else { %>    
     
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="stabilimenti.stabilimenti_details.PrimaryInformation">Primary Information</dhv:label></strong>
			</th>
		</tr>

		<dhv:include name="osmregistrati-sites" none="true">
			<dhv:evaluate if="<%= SiteList.size() > 1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.site">Site</dhv:label></td>
					<td><%=SiteList.getSelectedValue(OrgDetails.getSiteId())%> <input
						type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>">
					</td>
				</tr>
			</dhv:evaluate>
			<dhv:evaluate if="<%= SiteList.size() <= 1 %>">
				<input type="hidden" name="siteId" id="siteId" value="-1" />
			</dhv:evaluate>
		</dhv:include>

		<dhv:include name="osmregistrati-name" none="true">
			<tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
					name="stabilimenti.stabilimenti_add.OrganizationName">Organization Name</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getName())%>&nbsp;</td>
			</tr>
		</dhv:include>


		<dhv:evaluate if="<%= hasText(OrgDetails.getBanca()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Ragione Sociale Precedente</dhv:label>
				</td>
				<td><%=toHtml(OrgDetails.getBanca())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label
					name="organization.accountNumber">Account Number</dhv:label></td>
				<td><%=toHtml(OrgDetails.getAccountNumber())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<%--dhv:evaluate if="<%= hasText(OrgDetails.getNumAut())%>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Numero di Registrazione</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getNumAut())%></td>
			</tr>
		</dhv:evaluate--%>
		
		<dhv:evaluate if="<%= hasText(OrgDetails.getCategoria()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Categoria</dhv:label>
				</td>
				<td><%=toHtmlValue(OrgDetails.getCategoria())%></td>
			</tr>
		</dhv:evaluate>
		
		<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Attività</dhv:label>
				</td>
				<td>	
					
					 <% String  selezione2="";
			    		HashMap<Integer,String> lista2=OrgDetails.getListaAttivita();
			    		Iterator<Integer> valori2=OrgDetails.getListaAttivita().keySet().iterator();
			    		
			    		while(valori2.hasNext()){
			    			String Sel2=lista2.get(valori2.next());			    			
			    				selezione2=Sel2;
			    				out.print(selezione2);%></br>
			    				
			    			<%
			    			}			    		
			    		 %>
			    	  <input type="hidden" name="impianto" value="<%=OrgDetails.getListaAttivita()%>" >
			    	  <% if(OrgDetails.getNoteAttivita()!=null && !OrgDetails.getNoteAttivita().equals("")){%>
			    	 		 <dhv:label name=""><b>Principali tipologie produttive:</b></dhv:label></br>
    						 <TEXTAREA NAME="note_attivita" ROWS="3" COLS="50" readonly="readonly"><%= OrgDetails.getNoteAttivita()%></TEXTAREA>
    	 <%} %>
					</td>
			</tr>
	
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
		
		
		<dhv:include name="organization.url" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getUrl()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.WebSiteURL">Web Site URL</dhv:label>
					</td>
					<td><a href="<%= toHtml(OrgDetails.getUrlString()) %>"
						target="_new"><%=toHtml(OrgDetails.getUrl())%></a>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.industry" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getIndustryName()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.Industry">Industry</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getIndustryName())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.dunsType" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getDunsType()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.duns_type">DUNS Type</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getDunsType())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.employees" none="true">
			<dhv:evaluate if="<%= (OrgDetails.getEmployees() > 0) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="organization.employees">No. of Employees</dhv:label></td>
					<td><%=OrgDetails.getEmployees()%></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.potential" none="true">
			<dhv:evaluate if="<%= (OrgDetails.getPotential() > 0) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.Potential">Potential</dhv:label>
					</td>
					<td><zeroio:currency value="<%= OrgDetails.getPotential() %>"
						code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>'
						locale="<%= User.getLocale() %>" default="&nbsp;" /></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.revenue" none="true">
			<dhv:evaluate if="<%= (OrgDetails.getRevenue() > 0) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.Revenue">Revenue</dhv:label></td>
					<td><zeroio:currency value="<%= OrgDetails.getRevenue() %>"
						code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>'
						locale="<%= User.getLocale() %>" default="&nbsp;" /></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.ticker" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getTicker()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.TickerSymbol">Ticker Symbol</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getTicker())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.dunsNumber" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getDunsNumber()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.duns_number">DUNS Number</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getDunsNumber())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.businessNameTwo" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getBusinessNameTwo()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.business_name_two">Business Name 2</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getBusinessNameTwo())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:include name="osmregistrati-size" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getAccountSizeName()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.accountSize">Account Size</dhv:label>
					</td>
					<td><%=toHtml(OrgDetails.getAccountSizeName())%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.segment" none="true">
			<dhv:evaluate if="<%= (OrgDetails.getSegmentId() > 0) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="osm.osm_add.segment">Segment</dhv:label></td>
					<td><%=SegmentList.getSelectedValue(OrgDetails.getSegmentId())%></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		<dhv:include name="organization.directBill" none="true">
			<dhv:evaluate if="<%= OrgDetails.getDirectBill() %>">
				<tr class="containerBody">
					<td nowrap class="formLabel">Direct Bill</td>
					<td><input type="checkbox" name="directBill" CHECKED DISABLED /></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:evaluate if="<%= hasText(OrgDetails.getContoCorrente()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Tipo Autoveicolo</dhv:label></td>
				<td><%=toHtml(OrgDetails.getContoCorrente())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= hasText(OrgDetails.getNomeCorrentista()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Targa Autoveicolo</dhv:label></td>
				<td><%=toHtml(OrgDetails.getNomeCorrentista())%>&nbsp;</td>
			</tr>
		</dhv:evaluate>

		<dhv:include name="organization.source" none="true">
			<dhv:evaluate if="<%= OrgDetails.getSource() != -1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="contact.source">Source</dhv:label></td>
					<td><%=SourceList.getSelectedValue(OrgDetails.getSource())%></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:include name="organization.stage" none="true">
			<dhv:evaluate if="<%= OrgDetails.getStageId() != -1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stage">Stage</dhv:label></td>
					<td><%=StageList.getSelectedValue(OrgDetails.getStageId())%></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:evaluate if="<%= OrgDetails.getStatoLab() !=  -1 %>">
			<tr class="containerBody">
				<td name="statoLab1" id="statoLab1" nowrap class="formLabel"><dhv:label
					name="">Stato OSM</dhv:label></td>
				<td><%=statoLab.getSelectedValue(OrgDetails.getStatoLab())%>
				<input type="hidden" name="statoLab"
					value="<%=OrgDetails.getStatoLab()%>">
					 in data <zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>"
					dateOnly="true" showTimeZone="false" default="&nbsp;" />
 </td>
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
    <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Prossimo Controllo</br>con la tecnica della Sorveglianza</dhv:label>
      </td>
      <td>
      <% SimpleDateFormat dataPC = new SimpleDateFormat("dd/MM/yyyy");
       %>
         <%= (((OrgDetails.getProssimoControllo()!=null))?(dataPC.format(OrgDetails.getProssimoControllo())):(dataPC.format(d)))%>
      </td>
    </tr>
  <%}%>
		<dhv:include name="organization.rating" none="true">
			<dhv:evaluate if="<%= OrgDetails.getRating() != -1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="sales.rating">Rating</dhv:label>
					</td>
					<td><%=RatingList.getSelectedValue(OrgDetails.getRating())%>
					</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:evaluate if="<%= (OrgDetails.getDate2() != null) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Data inizio attività</dhv:label>
				</td>
				<td><zeroio:tz timestamp="<%= OrgDetails.getDate2() %>"
					dateOnly="true" showTimeZone="false" default="&nbsp;" /></td>
			</tr>
		</dhv:evaluate>

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
		<dhv:include name="organization.contractEndDate" none="true">
			<dhv:evaluate
				if="<%= hasText(OrgDetails.getContractEndDateString()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.ContractEndDate">Contract End Date</dhv:label>
					</td>
					<td><zeroio:tz
						timestamp="<%= OrgDetails.getDate1() %>" dateOnly="true"
						showTimeZone="false" default="&nbsp;" /></td>
				</tr>
			</dhv:evaluate>
			
			<dhv:evaluate if="<%= (OrgDetails.getYearStarted() > 0) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.stabilimenti_add.year_started">Year Started</dhv:label>
					</td>
					<td><%=OrgDetails.getYearStarted()%>&nbsp;</td>
				</tr>
			</dhv:evaluate>
		</dhv:include>
		
		</table>
		
		<br>
		
		
		
		<br />

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
      <zeroio:tz timestamp="<%= OrgDetails.getDataNascitaRappresentante() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
  	 
		<dhv:evaluate if="<%= (hasText(OrgDetails.getLuogoNascitaRappresentante())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			Luogo di Nascita
			</td>
			<td>
         	<%= toHtml(OrgDetails.getLuogoNascitaRappresentante())%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
	
	<dhv:evaluate if="<%= (hasText(OrgDetails.getEmailRappresentante())&& (!OrgDetails.getEmailRappresentante().equals("-1"))) %>">						
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Email</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getEmailRappresentante()) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
	<dhv:evaluate if="<%= (hasText(OrgDetails.getTelefonoRappresentante()) && (!OrgDetails.getTelefonoRappresentante().equals("-1"))) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Telefono</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getTelefonoRappresentante()) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= (hasText(OrgDetails.getFax())&& (!OrgDetails.getFax().equals("-1"))) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Fax</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getFax()) %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<!--  fine delle modifiche -->
		
</table>	</br>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  			<tr>
    			<th colspan="2"><strong>Sede Legale</strong></th>
  			</tr>
			<%
				Iterator iaddress = OrgDetails.getAddressList().iterator();
				if (iaddress.hasNext()) {
					while (iaddress.hasNext()) {
						OrganizationAddress thisAddress = (OrganizationAddress) iaddress
						.next();
						if(thisAddress.getType()==1){
			%>    
				<inpu type ="hidden" name = "address1type" value = "1"/>
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
				}}
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
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  			<tr>
    			<th colspan="2"><strong><dhv:label name="">Sede Operativa</dhv:label></strong></th>
  			</tr>
			<%
				 iaddress = OrgDetails.getAddressList().iterator();
				if (iaddress.hasNext()) {
					while (iaddress.hasNext()) {
						OrganizationAddress thisAddress = (OrganizationAddress) iaddress
						.next();
						if(thisAddress.getType()==5){
			%>    
				<inpu type ="hidden" name = "address2type" value = "5"/>
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
				}}
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
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="stabilimenti.stabilimenti_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="stabilimenti.stabilimenti_add.Notes">Notes</dhv:label>
    </td>
    <td><%= toString(OrgDetails.getNotes()) %></td>
  </tr>
</table>
<% } %>


		<br>
		<br>
		<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
		<dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
			<%if(OrgDetails.getStatoLab()!= 1 && OrgDetails.getStatoLab()!=2){ %>
<%-- 				<input type="button"
					value="Modifica"
					onClick="javascript:window.location.href='OsmRegistrati.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';">
			</dhv:permission>--%>
			<%} %>
		</dhv:evaluate>
		
	
	</dhv:evaluate>
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
		
<%
// associazione OSM - azienda zootecnica
Boolean ricercaAllevamentiAssociabiliAttribute = (Boolean)request.getAttribute("ricercaAllevamentiAssociabiliAttribute");
if (ricercaAllevamentiAssociabiliAttribute != null && ricercaAllevamentiAssociabiliAttribute == true) {
%>
	<script>confermaRicercaAllevamentoDaOsm();</script>
<%
}
%>

<%
// associazione OSM - azienda zootecnica
String messaggio = ((String)request.getAttribute("messaggioAssociazioneOsmEffettuata"));
request.removeAttribute("messaggioAssociazioneOsmEffettuata");
if(messaggio != null && !messaggio.equals("")) {
%>
	<script>alert('<%=messaggio%>');</script>
<%
}
%>

<script>


function mostraListaOperazioni(listadamostrare){
	
	var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
	
	document.getElementById(listadamostrare).classList.toggle("show");
}

//Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

function openPopupLarge(url){
	
	  var res;
    var result;
    	  window.open(url,'popupSelect',
          'height=600px,width=1000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
</script>
</body>