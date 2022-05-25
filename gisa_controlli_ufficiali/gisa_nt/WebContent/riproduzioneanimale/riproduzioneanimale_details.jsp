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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.riproduzioneanimale.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<%@page import="org.aspcfs.modules.audit.base.Audit"%>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.riproduzioneanimale.base.Organization" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>

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

<%@ include file="../initPage.jsp" %>
<%@page import="java.text.SimpleDateFormat, java.util.Date" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<%if (refreshUrl!=null && !"".equals(refreshUrl)){ %>
<script language="JavaScript" TYPE="text/javascript">
parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>
<%}%>	
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="RiproduzioneAnimale.do?command=SearchForm"><dhv:label name="">Strutture di Riproduzione Animale</dhv:label></a> > 
<%--if (request.getParameter("return") == null) { %>
<a href="RiproduzioneAnimale.do?command=Search&tipoRicerca=<%= request.getAttribute("tipoRicerca")%>"><dhv:label name="">Risultati Ricerca</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<%}--%>
<dhv:label name="">Scheda Strutture di Riproduzione Animale</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

</dhv:evaluate>


<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId();
   %>
  
<dhv:container name="riproduzioneanimale" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= OrgDetails.isTrashed() %>">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>"> 

<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="riproduzioneanimale-riproduzioneanimale-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='RiproduzioneAnimale.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>

<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
 
    <dhv:permission name="riproduzioneanimale-riproduzioneanimale-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='RiproduzioneAnimale.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
 



</dhv:evaluate>


    <dhv:permission name="riproduzioneanimale-riproduzioneanimale-delete">
    <input type="button" value="Elimina" onClick="javascript:popURLReturn('RiproduzioneAnimale.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','RiproduzioneAnimale.do?command=Search', 'Delete_account','320','200','yes','no');">
    </dhv:permission>


 <%if( (OrgDetails.getStatoLab() != -1   && OrgDetails.getStatoLab() != 0) || (OrgDetails.getStatoLab() == -1 && OrgDetails.getStato() != null && !OrgDetails.getStato().equalsIgnoreCase("attivo")  ) ){ %>
  	
  	<%} 
  else
	{%>
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
		 <jsp:param value="RiproduzioneAnimale.do?command=CessazioneAttivita" name="urlSubmitCessazione"/>
		 <jsp:param value="<%=OrgDetails.getDataInizio() != null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date(OrgDetails.getDataInizio().getTime())) : "" %>" name="data_inizio" />
	</jsp:include>
	
<!------------------------------------------------------------------------------------------------> 


<dhv:permission name="opu-add">
<center><font color="red"><b><%="Lo stabilimento ha linee non aggiornate." %></b></font></center>
  <div align="center">
 	 <br/> 
 		<%--<input type="button" class="yellowBigButton" value="AGGIORNA LINEE DI ATTIVITA' PREGRESSE DA MASTERLIST" 
 		onClick="openPopupLarge('Accounts.do?command=PrepareUpdateLineePregresse&orgId=<%=OrgDetails.getOrgId() %>&lda_prin=<%=linea_attivita_principale.getId() %>')"
 		--%>
 	<%-- onClick="loadModalWindow();window.location.href='OpuStab.do?command=PrepareUpdateLineePregresse&stabId=<%=StabilimentoDettaglio.getIdStabilimento() %>'"--%>
 	<%
 	if(true)
 	{
 	%>
 	
 	<input type="button" class="yellowBigButton"s
				value="Importa in Anagrafica stabilimenti"
			    onClick="javascript:window.location.href='OpuStab.do?command=CaricaImport&orgId=<%=OrgDetails.getOrgId()%>'">
			    
			    <%
 	}
 	else
 	{
 		%>
 		
 		 <div align="center">
					<input type="button"  class="greenBigButton" style="color:black !important" value="Completa scheda anagrafica"
			    		onClick="location.href='GestioneAnagraficaAction.do?command=Import&altId=<%=OrgDetails.getOrgId()%>';">
	  </div>
 		<%
 	}
			    %>
 <br/><br/>	
 	</div>

</dhv:permission>

<%} %>

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

<% if (1==1) { %>

<jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="39" />
</jsp:include>


<% } else { %>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazioni Principali</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
        <td nowrap class="formLabel" name="name" id="name">
          <dhv:label name="">Denominazione</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getName()) %>&nbsp;
       </td>
  </tr>
  <tr class="containerBody">
        <td nowrap class="formLabel" name="site_id" id="site_id">
          <dhv:label name="">Asl</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(SiteList.getSelectedValue(OrgDetails.getSiteId())) %>&nbsp;
       </td>
  </tr>
  <tr class="containerBody">
        <td nowrap class="formLabel" name="numaut" id="numaut">
          <dhv:label name="">Numero Autorizzazione</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getNumaut()) %>&nbsp;
       </td>
  </tr>
  <tr class="containerBody">
        <td nowrap class="formLabel">
          <dhv:label name="">Stato</dhv:label>
        </td>
        <td>
 <%
 			  if(OrgDetails.getCessato()>0 || OrgDetails.getStatoLab() == 4)
        	  {
        	  	out.println("Cessato");
        	  }
              else
              {
          	  	out.println("Attivo");
          	  }
%>
&nbsp;
       </td>
  </tr>
  <%if(OrgDetails.getCessato() > 0 || OrgDetails.getStatoLab() == 4) {%>
  		<tr>
  		 	<td nowrap class="formLabel" name="data_cessazione0">
  		 		<dhv:label name="">Data Cessazione</dhv:label>
  		 	</td>
  		 	<td>
  		 		<zeroio:tz timestamp="<%= OrgDetails.getDate2() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
  		 	</td>
  		</tr>
  <%} %>
   <tr class="containerBody">
        <td nowrap class="formLabel" name="codice_fiscale" id="codice_fiscale">
          Codice Fiscale
        </td>
        <td>
          <% if( OrgDetails.getCodice_fiscale().trim()!= null && !(OrgDetails.getCodice_fiscale().trim().equals(""))) {  %>		
	          <%= toHtmlValue(OrgDetails.getCodice_fiscale()) %>&nbsp;
	      <% } else { %>
	      		<%= toHtmlValue("N.D") %>
	      	<%} %>    
       </td>
  </tr>
  <tr class="containerBody">
        <td nowrap class="formLabel" name="partita_iva" id="partita_iva">
          Partita IVA
        </td>
        <td>
          <% if( OrgDetails.getPartita_iva().trim()!= null && !(OrgDetails.getPartita_iva().trim().equals(""))) {  %>		
	          <%= toHtmlValue(OrgDetails.getPartita_iva()) %>&nbsp;
	      <% } else { %>
	      		<%= toHtmlValue("N.D") %>
	      	<%} %>    
       </td>
  </tr>
   <tr class="containerBody">
        <td nowrap class="formLabel" name="nome_rappresentante" id="nome_rappresentante">
          <dhv:label name="">Rappresentante Legale</dhv:label>
        </td>
        <td>
          <% if( OrgDetails.getNome_rappresentante()!= null) {  %>		
	          <%= toHtmlValue(OrgDetails.getNome_rappresentante()) %>&nbsp;
	      <% } else { %>
	      		<%= toHtmlValue("N.D") %>
	      	<%} %>    
       </td>
  </tr>
  
  <dhv:evaluate if="<%= (OrgDetails.getDate2() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data scadenza autorizzazione</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OrgDetails.getDate2() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
      </td>
    </tr>
  </dhv:evaluate>

</table>

<br>


<table cellpadding="6" cellspacing="0" width="100%" class="details">
		<th colspan="6" style="background-color: rgb(204, 255, 153);" >
			<strong>
				<dhv:label name=""><center>Dettaglio Stazione Riproduzione Animale</center></dhv:label>
		    </strong>
		</th>
	    <tr>
	       <th><b><dhv:label name="">Tipo Struttura</dhv:label></b></th>
   		   <th><b><dhv:label name="">Codice L30</dhv:label></b></th>
   		   <th><b><dhv:label name="">Razza</dhv:label></b></th>
   		   <th><b><dhv:label name="">Provvedimento Autorizzazione</dhv:label></b></th>
   		   <th><b><dhv:label name="">Scadenza Autorizzazione</dhv:label></b></th>
   		   <th><b><dhv:label name="">Sede</dhv:label></b></th>
   		   
       </tr>
   <%
   Iterator op = OrgDetails.getStazioniList().iterator();
   if (op.hasNext()) {
     while (op.hasNext()) {
    	 org.aspcfs.modules.riproduzioneanimale.base.Stazione thisOp = (org.aspcfs.modules.riproduzioneanimale.base.Stazione)op.next();
    %> 
   <tr>
    <td>
    	<% if(thisOp.isMonta_equina_attive()) { %>
    		<%= toHtml("STAZIONE DI MONTA NATURALE EQUINA") %>
    	<% } else if(thisOp.isMonta_bovine_attive()) { %>
			<%= toHtml("STAZIONE DI MONTA NATURALE BOVINA") %>
		<% } else if(thisOp.isStazione_inseminazione_equine()) { %>
			<%= toHtml("STAZIONE DI INSEMINAZIONE ARTIFICIALE EQUINA") %>
		<%  } else if(thisOp.isCentro_produzione_embrioni()) { %>
	  	    <%= toHtml("CENTRO DI PRODUZIONE EMBRIONI") %>
	  	<% } else if(thisOp.isCentro_produzione_sperma()) { %>    
	  		<%= toHtml("CENTRO DI PRODUZIONE SPERMA") %>
	  	<% } else if(thisOp.isGruppo_raccolta_embrioni()) { %>
	  		<%= toHtml("GRUPPO DI RACCOLTA EMBRIONI") %>
	  	<% } else { %>
	  		<%= toHtml("RECAPITI ATTIVI") %>
	  	<% } %>			
	</td>
	<td>
	<% if(thisOp.getCodice_legge_30() != null && !thisOp.getCodice_legge_30().equals("")) { %>
       		<%= thisOp.getCodice_legge_30() %>
       <% } else { %>
      	<%= toHtml("ND") %>
       <% } %>
	</td>
	<td>
	<% if(thisOp.getRazza() != null && !thisOp.getRazza().equals("")) { %>
       		<%= thisOp.getRazza() %>
       <% } else { %>
      	<%= toHtml("ND") %>
       <% } %>
	</td>
	<td>
	<% if(thisOp.getProvv_aut() != null && !thisOp.getProvv_aut().equals("")) { %>
       		<%= thisOp.getProvv_aut() %>
       <% } else { %>
      	<%= toHtml("ND") %>
       <% } %>
	</td>
	<td>
	<% if(thisOp.getScadenza_aut() != null && !thisOp.getScadenza_aut().equals("")) { %>
       		<%= thisOp.getScadenza_aut() %>
       <% } else { %>
      	<%= toHtml("ND") %>
       <% } %>
	</td>
	<td>
	<% if(thisOp.getSede() != null && !thisOp.getSede().equals("")) { %>
       		<%= thisOp.getSede() %>
       <% } else { %>
      	<%= toHtml("ND") %>
       <% } %>
	</td>
   </tr>
   <% } %>
       
  <% } else { %>
   <tr class="containerBody">
      <td colspan="6">
        <dhv:label name="">Nessuna stazione associata</dhv:label>
      </td>
   </tr>
   <%}%> 
   
 </table>	 


<br>

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
      
        <dhv:evaluate if="<%=thisAddress.getPrimaryAddress() %>">        
          <dhv:label name="requestor.primary.brackets">(Primary)</dhv:label>
        </dhv:evaluate> 
      </td>
     </tr> 
</table>
	  <%} } %>
	  
	  
	  <%  } %>

<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="riproduzioneanimale-riproduzioneanimale-dit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='RiproduzioneAnimale.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  
    <dhv:permission name="riproduzioneanimale-riproduzioneanimale-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='RiproduzioneAnimale.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
 
    <%--dhv:permission name="riproduzioneanimale-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='RiproduzioneAnimale.do?command=Enable&idFarmacie=<%= OrgDetails.getOrgId() %>';">
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
