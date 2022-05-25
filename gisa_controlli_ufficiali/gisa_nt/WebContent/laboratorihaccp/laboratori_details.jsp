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
<%@ page import="java.util.*,java.text.DateFormat,java.text.SimpleDateFormat,java.util.Date,org.aspcfs.modules.laboratorihaccp.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<%@page import="org.aspcfs.modules.audit.base.Audit"%>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ElencoProve" class="org.aspcfs.modules.laboratorihaccp.base.ElencoProve" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Ente" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="MatriciHaccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DenominazioniHaccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.laboratorihaccp.base.Organization" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/elenco_prove.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%if (refreshUrl!=null && !"".equals(refreshUrl)){ %>
<script language="JavaScript" TYPE="text/javascript">
parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>


<%}%>	



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


<script language="JavaScript" TYPE="text/javascript">
function confirmDelete(orgId) {

	if ( confirm('ATTENZIONE! Sicuro di voler proseguire con l\'eliminazione del laborarorio HACCP?') ) {
    	window.open('LaboratoriHACCP.do?command=ConfirmDelete&id='+orgId,'popupSelect',
		'height=300px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
		} 
    	
  	}
</script>

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="LaboratoriHACCP.do?command=SearchForm"><dhv:label name="">Laboratori HACCP</dhv:label></a> > 
<%--if (request.getParameter("return") == null) { %>
<a href="LaboratoriHACCP.do?command=Search&tipoRicerca=<%= request.getAttribute("tipoRicerca")%>"><dhv:label name="">Risultati Ricerca</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="LaboratoriHACCP.do?command=Dashboard">Cruscotto</a> >
<%}--%>
<dhv:label name="">Scheda Laboratorio HACCP</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

</dhv:evaluate>

<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %>
<dhv:permission name="laboratori-laboratori-report-view">
  <table width="100%" border="0">
    <tr>
      <td nowrap align="right">
        <%-- 
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="LaboratoriHACCP.do?command=PrintReport&id=<%= OrgDetails.getId() %>"><dhv:label name="accounts.osa.print">Stampa Scheda O.S.A.</dhv:label></a>
       --%>
      </td>
    </tr>
  </table>
</dhv:permission>
<% String param1 = "orgId=" + OrgDetails.getOrgId();
   %>
  
<dhv:container name="laboratorihaccp" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= OrgDetails.isTrashed() %>">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>"> 

<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="laboratori-laboratori-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='LaboratoriHACCP.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>

<%-- <%=OrgDetails.getStato()  %>
  <%=OrgDetails.getStatoLab() %>  --%>
  <%if( (OrgDetails.getStatoLab() != -1   && OrgDetails.getStatoLab() != 0) || (OrgDetails.getStatoLab() == -1 && OrgDetails.getStato() != null && !OrgDetails.getStato().equalsIgnoreCase("attivo")  ) ){ %>
  	
  	<%} 
else {%>
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
		 <jsp:param value="LaboratoriHACCP.do?command=CessazioneAttivita" name="urlSubmitCessazione"/>
		 <jsp:param value="<%=OrgDetails.getDataInizio() != null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date(OrgDetails.getDataInizio().getTime())) : "" %>" name="data_inizio" />
	</jsp:include>
	
<!------------------------------------------------------------------------------------------------> 


<dhv:permission name="opu-import-add">
	  
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

<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
 
    <dhv:permission name="laboratori-laboratori-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='LaboratoriHACCP.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
 

</dhv:evaluate>


	<dhv:permission name="laboratori-laboratori-delete">
	<input type="button" value="Elimina" onClick="javascript:popURLReturn('LaboratoriHACCP.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','LaboratoriHACCP.do?command=Search', 'Delete_account','320','200','yes','no');">
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

  <%if (1==1) { %>
 <jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="40" />
     </jsp:include>
  
<% } else { %>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni Principali</dhv:label></strong>
    </th>
  </tr>
   
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
    </td>
    <td>
      <%= SiteList.getSelectedValue(OrgDetails.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
    </td>
   
  </tr>
 

 <input type="hidden" id="tipoRicerca" name="tipoRicerca" value="<%=request.getParameter("tipoRicerca") %>" />
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Denominazione</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getName()) %>&nbsp;
       </td>
      </tr>
      <dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
  		<tr class="containerBody">
    		<td nowrap class="formLabel">
      			<dhv:label name="">Numero di Iscrizione</dhv:label>
    		</td>
   			 <td>
       			<%= toHtml(OrgDetails.getAccountNumber()) %>&nbsp;
    		</td>
  			</tr>
		</dhv:evaluate>
		<tr class="containerBody">
        <td nowrap class="formLabel" name="partita_iva" id="partita_iva">
          Partita IVA
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getPartitaIva()) %>&nbsp;
        </td>
        </tr>
		 <tr class="containerBody">
        <td nowrap class="formLabel" name="cognomeRappresentante" id="cognomeRappresentante">
          <dhv:label name="">Direttore Responsabile Laboratorio</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>&nbsp;
       </td>
      </tr>
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Stato Laboratorio</dhv:label>
        </td>
        <td>
        <%if(OrgDetails.getStato()==null || OrgDetails.getStato().equals("")){ %>
          <%="Attivo"%>
          <%}else{ %>
          <%= toHtml(OrgDetails.getStato()) %>&nbsp;
          <%} %>
          <%if(OrgDetails.getDataCambioStato()!=null && !OrgDetails.getDataCambioStato().equals("")){ %>
          In Data<%} %> &nbsp;<zeroio:tz timestamp="<%= OrgDetails.getDataCambioStato() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
       </td>
      </tr>
      
</table>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Linea produttiva</dhv:label></strong>
    </th>
  </tr>
   
  <tr class="containerBody">
    <td nowrap class="formLabel">
      ALTRA NORMATIVA
    </td>
    <td>
     ALTRO->LABORATORIO DI ANALISI CHE EFFETTUA PROVE RELATIVE ALLAUTOCONTROLLO PER LE INDUSTRIE ALIMENTARI (HACCP)->LABORATORIO DI ANALISI CHE EFFETTUA PROVE RELATIVE ALLAUTOCONTROLLO PER LE INDUSTRIE ALIMENTARI (HACCP)
    </td>
   
  </tr>
  </table>



<%


  Iterator iaddress = OrgDetails.getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
%>  
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:evaluate if="<%= thisAddress.getType() == 1 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede legale</dhv:label></strong>
	  </dhv:evaluate>
	  <dhv:evaluate if="<%= thisAddress.getType() == 5 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede operativa</dhv:label></strong>
	  </dhv:evaluate>  
	  </th>
	  </tr>
<%-- 	  in alternativa
	<tr>
	<td nowrap class="formLabel" name="province" id="province">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
    	<%=toHtmlValue(thisAddress.getCity()) %>
	</td>
	</tr>
	<tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
	    </td>
	    <td>
	   		<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>
	   	</td>
	</tr>
	<tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="">C/O</dhv:label>
	    </td>
	    <td>
	   		<%= toHtmlValue(thisAddress.getStreetAddressLine2()) %>
	   	</td>
	</tr>
	<tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
	    </td>
	    <td>
	      <%= toHtmlValue(thisAddress.getZip()) %>
	    </td>
	</tr>
	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <%= toHtmlValue(thisAddress.getState()) %>
    </td>
  </tr>
  --%> 
  <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <%= toHtml(thisAddress.getTypeName()) %>
      </td>
      <td>
        <%= toHtml(thisAddress.toString()) %>&nbsp;<br/><%=thisAddress.getGmapLink() %>
   
        <dhv:evaluate if="<%=thisAddress.getPrimaryAddress() %>">        
          <dhv:label name="requestor.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate> 
      </td>
     </tr> 
</table>
	  <%} } %>

<%-- 
<%


  Iterator iaddress = OrgDetails.getAddressList().iterator();
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress)iaddress.next();
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

	<tr>
    	<th colspan="2">
      		<dhv:evaluate if="<%= thisAddress.getType() == 1 %>">
	    	<strong><dhv:label name="accounts.accounts_add.Addressess">Sede legale</dhv:label></strong>
	  		</dhv:evaluate>
	  		<dhv:evaluate if="<%= thisAddress.getType() == 5 %>">
	    	<strong><dhv:label name="accounts.accounts_add.Addressess">Sede operativa</dhv:label></strong>
	  		</dhv:evaluate>  
	  	</th>
	 </tr>
	 
	 <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <%= toHtml(thisAddress.getTypeName()) %>
      </td>
      <td>
        <%= toHtml(thisAddress.toString()) %>&nbsp;<br/><%=thisAddress.getGmapLink() %>
   
        <dhv:evaluate if="<%=thisAddress.getPrimaryAddress()%>">        
          <dhv:label name="requestor.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate>
      </td>
     </tr> 

</table>
	  <%} } %>
	  
<br/>
--%>	  

<dhv:permission name="laboratori-laboratori-prove-add"><a href="#" onclick="javascript:window.open('ElencoProveHACCP.do?command=Add&orgId=<%= OrgDetails.getOrgId()%>',null,
		'height=800px,width=680px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes')"><dhv:label name="">Aggiungi Prova</dhv:label></a></dhv:permission>
	<br><br>
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
		<th colspan="6" style="background-color: rgb(204, 255, 153);" >
			<strong>
				<dhv:label name=""><center>Elenco Prove</center></dhv:label>
		    </strong>
		</th>
	    <tr>
		   <th><b><dhv:label name="">Materiale / Prodotto / Matrice </dhv:label></b></th>
   		   <th><b><dhv:label name="">Denominazione Prova</dhv:label></b></th>
   		   <th><b><dhv:label name="">Norma / Metodo</dhv:label></b></th>
   		   <th><b><dhv:label name="">Accreditata / In Accreditamento</dhv:label></b></th>
   		   <th><b><dhv:label name="">Ente Accreditamento</dhv:label></b></th>
   		   <th>&nbsp;</th>
   		   
     	   
   </tr>
   <%
   			
   int cont1=0;
   Iterator prove = OrgDetails.getProveList().iterator();
   Object prov[] = null;
   int j = 0;
   if (prove.hasNext()) {
     while (prove.hasNext()) {
       Prova thisProva = (Prova)prove.next();
                
   
    %> 
   <tr>

	
    <td>
       <%= MatriciHaccp.getSelectedValue( thisProva.getCodiceMatrice()) %>
    </td>
    <td>
	  <%= DenominazioniHaccp.getSelectedValue( thisProva.getCodiceDenominazione()) %>
	</td>
	 <td>
	  <%= toHtml(thisProva.getNorma()) %>
	</td>
	
	<td>
	  <%= toHtml(((thisProva.getAccreditata()==true)?("Accreditata"):("In Accreditamento")))%>
	</td>
	<td>
	  <%= Ente.getSelectedValue( thisProva.getCodiceEnte()) %>
	</td>
	<td>
	
	<dhv:permission name="laboratori-laboratori-prove-edit"><a href = "javascript:modificaProva(<%=thisProva.getId() %>,<%=OrgDetails.getOrgId() %>)">Modifca</a></dhv:permission>	
	<dhv:permission name="laboratori-laboratori-prove-delete"><a href = "javascript:eliminaProva(<%=thisProva.getId() %>,<%=OrgDetails.getOrgId() %>)">Elimina</a></dhv:permission>
	
	</td>
   </tr>
   <%}%>
       
  <%}else{%>
   <tr class="containerBody">
      <td colspan="6">
        <dhv:label name="accounts.accounts_asset_list_include.NoAuditFound">Nessun Elenco Prove.</dhv:label>
      </td>
   </tr>
   <%}%> 
   
 	</table>
 	
 	
 	<%}  %>
 	
 	
 	
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="laboratori-laboratori-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='LaboratoriHACCP.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  
    <dhv:permission name="laboratori-laboratori-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='LaboratoriHACCP.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
 
    <%--dhv:permission name="laboratori-laboratori-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='LaboratoriHACCP.do?command=Enable&idFarmacie=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission--%>
 
</dhv:evaluate>
</dhv:container>
<input type="hidden" name="source" value="searchForm">
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>


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