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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.requestor.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<%@page import="org.aspcfs.modules.lineeattivita.base.*"%>

<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IstatList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CountryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="role" class="org.aspcfs.modules.admin.base.Role" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.requestor.base.Organization" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="istat_principale_non_valido" type="java.lang.Boolean" scope="request"/>

<%@ include file="../initPage.jsp" %>
<script language="javascript">
    document.onkeydown = f5() ;
</script>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>

<%
	ArrayList<LineeAttivita> linee_attivita = (ArrayList<LineeAttivita>) request.getAttribute("linee_attivita");
	LineeAttivita linea_attivita_principale = (LineeAttivita) request.getAttribute("linea_attivita_principale");
	ArrayList<LineeAttivita> linee_attivita_secondarie = (ArrayList<LineeAttivita>) request.getAttribute("linee_attivita_secondarie");
%>

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
<a href="Requestor.do"><dhv:label name="requestor.requestor">D.I.A.</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Requestor.do?command=Search"><dhv:label name="requestor.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Requestor.do?command=Dashboard">Cruscotto</a> >
<%}%>
<dhv:label name="requestor.details">Scheda Richiedente</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>

 
<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %> 
<dhv:permission name="requestor-requestor-report-view">

  <%
  OrganizationAddressList listaInd = OrgDetails.getAddressList();
  Iterator<OrganizationAddress> it= listaInd.iterator();
  int countMio = 0;
  Integer addressid = -1;
  Integer addressid2 = -1;
  Integer addressid3 = -1;
  while(it.hasNext()){
	  
	  OrganizationAddress temp = it.next();
	  if(temp.getType()==6){
		  countMio++;
		  if(countMio == 1){
			  
			   addressid=temp.getId();
			 
		  }
		  if(countMio==2){
			  
			  addressid2=temp.getId();
			
		  }if(countMio==3){
			  
			  addressid3=temp.getId();
			
		  }
	  }
	 
  }
  countMio=0;
  %>
  <table width="100%" border="0">
    <tr>
      <td nowrap align="right">
       
        <br>
         <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
 
        <%if(OrgDetails.getTipoDest().equals("Autoveicolo")){ %>
    	 <%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa scheda" value="Stampa scheda"	onClick="javascript:window.location.href='Accounts.do?command=PrintReport&file=account_attivitaMobili.xml&id=<%= OrgDetails.getId() %>&addressid=<%= addressid%>&addressid2=<%= addressid2%>&addressid3=<%= addressid3%>';"--%>
 		
 		<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda" value="Stampa Scheda"		onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '<%=addressid%>', '<%=addressid2%>', '<%=addressid3%>', '14');"> 
        
 		
 		<%}else{ %>
 		<%--   <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa scheda" value="Stampa scheda"	onClick="javascript:window.location.href='Accounts.do?command=PrintReport&file=account.xml&id=<%= OrgDetails.getId() %>&addressid=<%= addressid%>&addressid2=<%= addressid2%>&addressid3=<%= addressid3%>';">--%>
        	
     		<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda" value="Stampa Scheda"		onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '<%=addressid%>', '<%=addressid2%>', '<%=addressid3%>', '13');"> 
        
        
 		<%} %>
      </td>
    </tr>
  </table>
</dhv:permission>


<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>
<dhv:container name="requestor" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>">
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <!--<dhv:permission name="requestor-requestor-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='Requestor.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>-->
</dhv:evaluate>


<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  
  
  <% if ((!OrgDetails.getAccountNumber().equals(""))&& (User.getActualUserId() != 1)){ %>
  <dhv:permission name="requestor-requestor-edit">
  <input type="button" disabled="disabled" title="Non è possibile effettuare la modifica in quanto è già stato generato il Numero Registrazione" value="Modifica"	onClick="javascript:window.location.href='Requestor.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  <%} else { %>
  
  
  <%--dhv:evaluate if="<%=(OrgDetails.getEnabled())%>"--%>
    <dhv:permission name="requestor-requestor-edit">
    <input type="button" value="Modifica"	onClick="javascript:window.location.href='Requestor.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';">
    </dhv:permission>
  <%--/dhv:evaluate--%>
  <%} %>
  
  
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="requestor-requestor-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='Requestor.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="requestor-requestor-delete">
    <input type="button" value="<dhv:label name="">Elimina</dhv:label>" onClick="javascript:popURLReturn('Requestor.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Requestor.do?command=Search', 'Delete_account','320','200','yes','no');">
    </dhv:permission>
  </dhv:evaluate>
  
 <script language="JavaScript" TYPE="text/javascript">
function enable() {
var b = document.getElementById("modB");
var c = document.getElementById("modC");
b.disabled=false;
c.disabled=false;
}
</script>

<!--dhv:permission name="requestor-requestor-edit"-->
    <% if (!OrgDetails.getAccountNumber().equals("")){ %>
 		<%if (istat_principale_non_valido == true) {%>
 		<font color="red">Codice ISTAT principale non valido</font>
		<%} else { %>
		 <dhv:permission name="requestor-requestor-generaosa-view">
 		<input  disabled="disabled" title= "Genera il Numero Registrazione a patto che l'esito non sia non favorevole e che sia definita una Data Completamento D.I.A." type="button" name="Genera Codice Osa" value="Genera Numero Registrazione" ></a>
    	</dhv:permission>
	    <dhv:permission name="requestor-requestor-cambiaosa-view">
			<input type="button" title= "Trasforma una DIA in un O.S.A., solo dopo aver generato il Numero Registrazione" value="Cambia in OSA"	onClick="javascript:window.location.href='Requestor.do?command=CambiaInOsa&orgId=<%= OrgDetails.getOrgId()%>';">
	 	 </dhv:permission>
		<%} %>	
    <%} else if( OrgDetails.getAccountNumber().equals("")  &&(   !"Non favorevole".equalsIgnoreCase( OrgDetails.getNameMiddle() ) ) && ( OrgDetails.getDate1() != null ) ) { %>
		<%if (istat_principale_non_valido == true) {%>
 		<font color="red">Codice ISTAT principale non valido</font>
		<%} else { %>
		<dhv:permission name="requestor-requestor-generaosa-view">
			<input type="button" title= "Genera il Numero Registrazione a patto che l'esito non sia non favorevole e che ci sia un codice ISTAT e una Data Completamento D.I.A." value="Genera Numero Registrazione"	onClick="javascript:window.location.href='Requestor.do?command=GeneraCodiceOsa&orgId=<%= OrgDetails.getOrgId()%>';">
 		</dhv:permission>
	    <dhv:permission name="requestor-requestor-cambiaosa-view">
			<input disabled="disabled" type="button" title= "Trasforma una DIA in un OSA, solo dopo aver generato il Numero Registrazione" name="Cambia in Osa" value="Cambia in Osa"></a>
		</dhv:permission>
		<%} %>
	<%} else {%>
		<%if (istat_principale_non_valido == true) {%>
 		<font color="red">Codice ISTAT principale non valido</font>
		<%} else { %>
		<dhv:permission name="requestor-requestor-generaosa-view">
		<input disabled="disabled" type="button" title= "Genera il Numero Registrazione a patto che l'esito sia idoneo e che sia definita un codice ISTAT e una DATA DIA" name="Genera Codice Osa" value="Genera Numero Registrazione"></a>
		</dhv:permission>
	    <dhv:permission name="requestor-requestor-cambiaosa-view">
			<input disabled="disabled" type="button" title= "Trasforma una DIA in un OSA, solo dopo aver generato il Numero Registrazione" name="Cambia in Osa" value="Cambia in Osa"></a>
		</dhv:permission>
		<%}}%>

<!-- /dhv:permission-->
         
</dhv:evaluate>

<dhv:permission name="requestor-requestor-edit,requestor-requestor-delete"><br>&nbsp;</dhv:permission>

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

<% if (1==1){ %>
 <jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="13" />
     </jsp:include>
 <%} else {  %>    
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="requestor.requestor_details.PrimaryInformation">Primary Information</dhv:label></strong>
    </th>
  </tr>
  
  <dhv:include name="accounts-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.site">Site</dhv:label>
    </td>
    <td>
      <%= SiteList.getSelectedValue(OrgDetails.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
</dhv:include>
  
  
     <tr class="containerBody"><td nowrap class="formLabel">
    	  <dhv:label name="">Tipo D.I.A.</dhv:label>
    	</td><td>
       		<%= toHtmlValue(OrgDetails.getDunsType()) %>&nbsp;
    	</td></tr>
  
      <dhv:include name="accounts-name" none="true">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="requestor.requestor_add.OrganizationName">Organization Name</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getName()) %>&nbsp;
          <input type="hidden" name="codiceEsistente" id="codiceEsistente" value="<%=request.getAttribute("codiceEsistente")%>">
    			<%-- String codiceEs = request.getAttribute("codiceEsistente").toString();--%>
       </td>
      </tr>
    </dhv:include>
     


	   <%if(request.getAttribute("codiceEsistente")!=null){
		  
		   
	   boolean ce = Boolean.parseBoolean(request.getAttribute("codiceEsistente").toString());

	   if(ce==false){ 
	  %>
   
    <dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Numero Registrazione</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getAccountNumber()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
  <% }else{%>
  <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Numero Registrazione</dhv:label>
    </td><td>
      <font color="red"> Attenzione! Il sistema ha generato un Numero Registrazione già presente in banca dati. Si prega controllare la correttezza dei dati e riprovare.</font>
    </td></tr>
  <%}}else{
   %>
  <dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Numero Registrazione</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getAccountNumber()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
  <%} %>


   
<dhv:evaluate if="<%= OrgDetails.getTypes().size() > 0 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor.types">Account Type(s)</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getTypes().valuesAsString()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= OrgDetails.getNo_piva() == true %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      <dhv:label name="">Ente/Associazione</dhv:label>
			</td>
			<td>
         <input type = "checkbox" checked="checked" disabled="disabled"/>
			</td>
		</tr>
</dhv:evaluate>

<dhv:evaluate if="<%= hasText(OrgDetails.getPartitaIva()) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      Partita IVA
			</td>
			<td>
         <%= toHtml(OrgDetails.getPartitaIva()) %>&nbsp;
         
            <dhv:evaluate if="<%= OrgDetails.getIdNazione() != 106 %>">
        <img width="20" src="images/flags/eu.gif"/> 
        <i>(<%= CountryList.getSelectedValue(OrgDetails.getIdNazione()) %>)</i>
  			</dhv:evaluate>  
  			
			</td>
		</tr>
  </dhv:evaluate>
  
  
    <dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscale()) %>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      Codice Fiscale
			</td>
			<td>
         <%= toHtml(OrgDetails.getCodiceFiscale()) %>&nbsp;
			</td>
		</tr>
  </dhv:evaluate>
  
      <tr class="containerBody">
			<td nowrap class="formLabel">
      <dhv:label name="">Vendita con canali non convenzionali</dhv:label>
			</td>
			<td>
         <input type = "checkbox" <% if(OrgDetails.isFlagVenditaCanali()==true){ %> checked="checked"<%} %> disabled="disabled"/>
			</td>
		</tr>
		
		
		 <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Domicilio Digitale</dhv:label>
    </td>
    <td>
     <%= toHtml(OrgDetails.getDomicilioDigitale()) %> 
    </td>
  </tr>


  	<dhv:evaluate if="<%= linea_attivita_principale != null && hasText( linea_attivita_principale.getCategoria() ) && hasText( linea_attivita_principale.getCategoria() ) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Codice Ateco/Linea di Attività Principale</dhv:label>
			</td>
			<dhv:evaluate if="<%= linea_attivita_principale != null &&  hasText( linea_attivita_principale.getCategoria() ) && hasText( linea_attivita_principale.getCategoria() ) %>">
				<td>
	      			<%= toHtml( linea_attivita_principale.getCodice_istat() + " " + linea_attivita_principale.getDescrizione_codice_istat()) %> <br/>
	        		<%= toHtml( linea_attivita_principale.getCategoria() + " - " + linea_attivita_principale.getLinea_attivita() ) %>&nbsp;
				
				<br><br>
		
				<% if(linea_attivita_principale.getMacroarea() != null && !linea_attivita_principale.getMacroarea().equals("")) { %>
	        			<b>MACROAREA: </b><%= toHtml( linea_attivita_principale.getMacroarea()) %> <br/>
	        			<b>AGGREGAZIONE: </b><%= toHtml( linea_attivita_principale.getAttivita()) %> <br/>
	        			<b>ATTIVITA': </b> <%= toHtml( linea_attivita_principale.getAttivita()) %>
	        	    <% } %>
	        	</td>
			</dhv:evaluate>
			<dhv:evaluate if="<%=linea_attivita_principale != null && hasText( linea_attivita_principale.getCategoria() ) && !hasText( linea_attivita_principale.getCategoria() ) %>">
				<td>
	        		<%= toHtml( linea_attivita_principale.getCodice_istat() + " " + linea_attivita_principale.getDescrizione_codice_istat()) %> <br/>
	        		<%= toHtml( linea_attivita_principale.getCategoria()) %>&nbsp;
				</td>
			</dhv:evaluate>
			
		</tr>
  	</dhv:evaluate>
  	
  
    <dhv:evaluate if="<%= hasText(OrgDetails.getSicDescription()) %>">
      <tr class="containerBody">
	    <td nowrap class="formLabel">
          <dhv:label name="requestor.requestor_add.sicDescription">SIC Description</dhv:label>
		</td>
		<td>
         <%= toHtml(OrgDetails.getSicDescription()) %>&nbsp;
		</td>
	  </tr>
    </dhv:evaluate>
 
	<!-- CODICI ISTAT SECONDARI (NEW) -->
	<% if (linee_attivita_secondarie != null && !linee_attivita_secondarie.isEmpty() ) { %>
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Codici Ateco/Linea di Attività (Secondarie)</dhv:label>  
   			</td>
   			
		    <td>
		    		<%  int i=0;
		    			for (LineeAttivita linea: linee_attivita_secondarie) {
		    				i++; %>
			    			<b>Codice <%= i %>&nbsp;</b> : 
			    			<% if (!linea.getLinea_attivita().isEmpty()) { %>
			    			    <%= toHtml( linea.getCodice_istat() + " " + linea.getDescrizione_codice_istat()) %> <br/>
			    			    <b>Linea Attivita <%= i %>&nbsp;</b> :
	        					<%= toHtml( linea.getCategoria() + " - " + linea.getLinea_attivita() ) %>&nbsp;
							<% } else { %>
								<%= toHtml( linea.getCodice_istat() + " " + linea.getDescrizione_codice_istat()) %> <br/>
								<b>Linea Attivita <%= i %>&nbsp;</b> :
	        					<%= toHtml( linea.getCategoria() ) %>&nbsp;
			    			<% } 
			    			if(linea.getId_attivita_masterlist() != -1 && linea.getId_attivita_masterlist() != 0) { %>
			    			<br><br>
	        				 <b> MACROAREA <%=i %>&nbsp;</b>:  <%= toHtml( linea.getMacroarea()) %> <br/>
	        				 <b> AGGREGAZIONE <%=i %>&nbsp;</b>: <%= toHtml( linea.getAggregazione()) %> <br/>
	        				 <b> ATTIVITA' <%=i %>&nbsp;</b>: <%= toHtml( linea.getAttivita()) %>	
							<% } %>
			    			<br></br>
			    			<%
			    		 } 
			    	%>
		     </td>
		</tr>
	
	<% } %>
	
<dhv:include name="accounts-size" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getAccountSizeName()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.accountSize">Account Size</dhv:label>
      </td>
      <td>
         <%= toHtml(OrgDetails.getAccountSizeName()) %>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>


	<%
	
	if(hasText(OrgDetails.getTipoDest())) {%>
  	
  	
  	<tr class="containerBody">
  		<td nowrap class="formLabel">
     		 <dhv:label name="">Attività</dhv:label>
    	</td>
    	<td>
    	 <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Autoveicolo")%>">
        Mobile
        </dhv:evaluate>
       
        <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Es. Commerciale")%>">
      Fissa
        </dhv:evaluate>
        
         <dhv:evaluate if="<%= OrgDetails.getTipoDest().equalsIgnoreCase("distributori")%>">
      Distributore
        </dhv:evaluate>
   		</td>
  	</tr>
  	<%}%>
  	<%if(hasText(OrgDetails.getCodiceCont())) {%>
   <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Codice Contenitore</dhv:label>
    </td><td>
       <%= toHtmlValue(OrgDetails.getCodiceCont()) %>&nbsp;
    </td></tr>
  	<%}else{} %>
  	<dhv:include name="organization.source" none="true">
  <dhv:evaluate if="<%= OrgDetails.getSource() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="contact.source">Source</dhv:label>
      </td>
      <td>
        <%= SourceList.getSelectedValue(OrgDetails.getSource()) %> 
        <dhv:evaluate if="<%= OrgDetails.getSource()==1 %>">
        &nbsp; dal <zeroio:tz timestamp="<%= OrgDetails.getDateI() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/><%--= (request.getParameter("dateI") ) --%> <zeroio:tz timestamp="<%= request.getParameter("dateI") %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/> al&nbsp; <zeroio:tz timestamp="<%= OrgDetails.getDateF() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
         <dhv:evaluate if="<%= OrgDetails.getCessazione()%>">
        Cessazione automatica
       </dhv:evaluate>
        </dhv:evaluate>
        
       
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>  	



<dhv:include name="organization.rating" none="true">
  <dhv:evaluate if="<%= OrgDetails.getRating() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="sales.rating">Rating</dhv:label>
      </td>
      <td>
        <%= RatingList.getSelectedValue(OrgDetails.getRating()) %> 
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.date1" none="true">
    <dhv:evaluate if="<%= (OrgDetails.getDate1() != null ||  OrgDetails.getDataPresentazione() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Presentazione D.I.A./Inizio Attività</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OrgDetails.getDataPresentazione() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
      </td>
    </tr>
    </dhv:evaluate>
</dhv:include> 

<dhv:include name="requestor-stage" none="true">
  <dhv:evaluate if="<%= OrgDetails.getStageId() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="requestor.stage">Servizio Competente</dhv:label>
      </td>
      <td>
        <%= StageList.getSelectedValue(OrgDetails.getStageId()) %> 
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include> 
<dhv:include name="organization.contractEndDate" none="true">
<dhv:evaluate if="<%= hasText(OrgDetails.getContractEndDateString()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ContractEndDate">Contract End Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate>
</dhv:include>

</table>
<br />

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
   <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
     
    </th>
  </tr>
  <dhv:evaluate if="<%= (OrgDetails.getTitoloRappresentante()>0) %>">
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Titolo</dhv:label>
    </td>
    <td class="containerBody"> 
       <%= TitoloList.getSelectedValue(OrgDetails.getTitoloRappresentante()) %></td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(OrgDetails.getCodiceFiscaleRappresentante()) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Codice fiscale Rappresentante</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getCodiceFiscaleRappresentante() %>&nbsp; 
			</td>
		</tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= hasText(OrgDetails.getNomeRappresentante()) %>">		
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Nome Rappresentante</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getNomeRappresentante() %>&nbsp; 
			</td>
		</tr>
     </dhv:evaluate>
  
	  	<!-- aggiunto da d.dauria -->
  <dhv:evaluate if="<%= hasText(OrgDetails.getCognomeRappresentante()) %>">
     <tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Cognome Rappresentante</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getCognomeRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
<dhv:evaluate if="<%= (OrgDetails.getDataNascitaRappresentante() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Nascita</dhv:label>
    </td>
    <td>
     <%= ((OrgDetails.getDataNascitaRappresentante()!=null)?(toHtml(DateUtils.getDateAsString(OrgDetails.getDataNascitaRappresentante(),Locale.ITALY))):("")) %>
          </td>
  </tr>
</dhv:evaluate>
  	 
		<dhv:evaluate if="<%= hasText(OrgDetails.getLuogoNascitaRappresentante()) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Comune di Nascita</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getLuogoNascitaRappresentante()%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= (hasText(OrgDetails.getCity_legale_rapp())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Comune di residenza</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getCity_legale_rapp())%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= (hasText(OrgDetails.getProv_legale_rapp())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Provincia</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getProv_legale_rapp())%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= (hasText(OrgDetails.getAddress_legale_rapp())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Indirizzo</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getAddress_legale_rapp())%>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
	
	<dhv:evaluate if="<%= hasText(OrgDetails.getEmailRappresentante()) %>">						
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Email Rappresentante</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getEmailRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
	<dhv:evaluate if="<%= hasText(OrgDetails.getTelefonoRappresentante()) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Telefono Rappresentante</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getTelefonoRappresentante() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		<dhv:evaluate if="<%= hasText(OrgDetails.getFax()) %>">							
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Fax</dhv:label>
			</td>
			<td>
         	<%= OrgDetails.getFax() %>&nbsp; 
			</td>
		</tr>
		</dhv:evaluate>
		
		
		<!--  fine delle modifiche -->
		
	</table>
<br>

<%ArrayList<OrganizationAddress> lista_address = new ArrayList<OrganizationAddress>(); %>
<dhv:include name="organization.addresses" none="true">

<%  

  Iterator iaddress = OrgDetails.getAddressList().iterator();
  Object address[] = null;
  int i = 0;
  int cont=0;
  
  if (iaddress.hasNext()) {
    while (iaddress.hasNext()) {
    	org.aspcfs.modules.requestor.base.OrganizationAddress thisAddress = (org.aspcfs.modules.requestor.base.OrganizationAddress)iaddress.next();
   		 if(thisAddress.getType()!=6)
   		 {
%>  
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  
<%if(OrgDetails.getTipoDest().equals("Es. Commerciale")){
	
	%>
	
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
	
	<% 
	
	}
	
	else{ //se è mobile
		if(OrgDetails.getTipoDest().equals("Autoveicolo")){
	
	%>
  
  <tr>
   
   
   
    
      <dhv:evaluate if="<%= thisAddress.getType() == 1 %>">
	  <th colspan="2">  <strong><dhv:label name="accounts.accounts_add.Addressess">Sede legale</dhv:label></strong>  </th>
	  </dhv:evaluate>
	  
	
	
	<%if(OrgDetails.getTipoDest().equals("Autoveicolo")){ %>
	
	  <dhv:evaluate if="<%= (thisAddress.getType() == 7) %>">
	  <th colspan="2">  <strong><dhv:label name="accounts.accounts_add.Addressess">Sede attività mobile</dhv:label></strong>  </th>
	  </dhv:evaluate> 
	
  </tr>
  
  
  <% }}
		else{
  %>
  
   <tr>
   
   
   
    <th colspan="2">
      <dhv:evaluate if="<%= thisAddress.getType() == 1 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede legale</dhv:label></strong>
	  </dhv:evaluate>
	 
	  
	  
	
	</th>
  </tr>
	
  
  <%} %>
  
   <dhv:evaluate if="<%= thisAddress.getType() == 7%>">
  <dhv:evaluate if="<%= OrgDetails.getTipoStruttura()!= -1 %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Tipo Struttura</dhv:label>
			</td>
			<td>
				<%=TipoStruttura.getSelectedValue( OrgDetails.getTipoStruttura() ) %>
         		
			</td>
		</tr>
		  <dhv:evaluate if="<%= hasText(OrgDetails.getNomeCorrentista()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Targa/Codice Autoveicolo</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getNomeCorrentista()) %>&nbsp;
			</td>
		</tr> 
  	</dhv:evaluate> 

    <dhv:evaluate if="<%= hasText(OrgDetails.getContoCorrente()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Tipo Autoveicolo</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getContoCorrente()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate>  
  	    
  	</dhv:evaluate> 
  	</dhv:evaluate> 
 <%} %>

 

 
   <%
    	  
    	if((OrgDetails.getTipoDest().equals("Autoveicolo") && thisAddress.getType()!=5 && thisAddress.getType()!=6) || (OrgDetails.getTipoDest().equals("Es. Commerciale") && thisAddress.getType()!=7 && thisAddress.getType()!=6)  || (OrgDetails.getTipoDest().equals("Distributori") && thisAddress.getType()==1) ) {
    	  %>
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
    </tr><%} %>
  
    </table><%}
   		 else
   		 {
   			lista_address.add(thisAddress);
   		 }
   		 %><br>
<%
    }
  } else {
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="requestor.requestor_add.Addresses">Addresses</dhv:label></strong>
	  </th>
  </tr>
    <tr class="containerBody">
      <td colspan="2">
        <font color="#9E9E9E"><dhv:label name="contacts.NoAddresses">No addresses entered.</dhv:label></font>
      </td>
    </tr>
    </table><br>
<%}%>


</dhv:include>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  

   
   <tr>
   <%
   int i = 0 ;
   for (OrganizationAddress add : lista_address ) 
   {
   i++ ;
   %>
      <td id = "locale_1">
    <table  width="100%" class="details"  >
    <tr>
    <th colspan="2" id = "intestazione">
      <strong>Locale Funzionalmente collegato <%=i %></strong>
    </th>
  </tr>
  <tr>
  <td>
  
  <%if (i==1)
	  {
	  	out.print("Tipo Locale : "+TipoLocale.getSelectedValue(OrgDetails.getTipoLocale())+"<br>");
	  }
  if (i==2)
  {
  	out.print("Tipo Locale : "+TipoLocale.getSelectedValue(OrgDetails.getTipoLocale2())+"<br>");
  }
  if (i==3)
  {
  	out.print("Tipo Locale : "+TipoLocale.getSelectedValue(OrgDetails.getTipoLocale3())+"<br>");
  }
	  %>
  <%= toHtml(add.toString()) %>&nbsp;<br/><%=add.getGmapLink() %>
  </td>
  </tr>
  
  </table></td>
  <%} %>
  </tr></table>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Controllo della Notifica</dhv:label></strong>
    </th>
  </tr>
  <dhv:include name="organization.date1" none="true">
    <dhv:evaluate if="<%= (OrgDetails.getDate1() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data completamento D.I.A.</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OrgDetails.getDate1() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
      </td>
    </tr>
    </dhv:evaluate>
	</dhv:include> 
  
  <dhv:include name="organization.date1" none="true">
    <dhv:evaluate if="<%= (OrgDetails.getNameMiddle() != null) %>">
    
    <dhv:include name="accounts-name" none="true">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Esito</dhv:label>
        </td>
        <td>
          <%= toHtmlValue(OrgDetails.getNameMiddle()) %>&nbsp;
       </td>
      </tr>
</dhv:include>  
    <dhv:evaluate if="<%= hasText(OrgDetails.getCin()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Note relative alla non conformità</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getCin()) %>&nbsp;
    </td></tr>
</dhv:evaluate>
<dhv:include name="organization.date1" none="true">
    <dhv:evaluate if="<%= (OrgDetails.getDate3() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data di risoluzione della non conformita'</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OrgDetails.getDate3() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
       </td>
    </tr>
    </dhv:evaluate>
</dhv:include>
    </dhv:evaluate>
	</dhv:include> 
</table>

<br>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="requestor.requestor_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <%=toHtml(OrgDetails.getNotes()) %>&nbsp;
    </td>
  </tr>
</table>
<br />

<%} %>

<dhv:permission name="requestor-requestor-edit,requestor-requestor-delete"><br></dhv:permission>
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="requestor-requestor-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='Requestor.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%=!OrgDetails.isTrashed()%>">
  <!--  
  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="requestor-requestor-edit"><input type="button" value="Modifica"	onClick="javascript:window.location.href='Requestor.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  -->
  
 <% if ((!OrgDetails.getAccountNumber().equals(""))&& (User.getActualUserId() != 1)){ %>
  <dhv:permission name="requestor-requestor-edit"><input type="button" disabled="disabled" title="Non è possibile effettuare la modifica in quanto è già stato generato il Numero Registrazione" value="Modifica"	onClick="javascript:window.location.href='Requestor.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  <%} else { %>
  
  
  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="requestor-requestor-edit"><input type="button" value="Modifica"	onClick="javascript:window.location.href='Requestor.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  <%} %>
  
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="requestor-requestor-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='Requestor.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="requestor-requestor-delete"><input type="button" value="<dhv:label name="">Elimina</dhv:label>" onClick="javascript:popURLReturn('Requestor.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Requestor.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
</dhv:evaluate>
</dhv:container>
<%= addHiddenParams(request, "popup|popupType|actionId") %>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
