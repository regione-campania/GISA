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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.operatori_commerciali.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.operatori_commerciali.base.Organization" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>

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


<%
	if (refreshUrl != null && !"".equals(refreshUrl)) {
%>
<script language="JavaScript" TYPE="text/javascript">
parent.opener.window.location.href='<%=refreshUrl%><%=request.getAttribute("actionError") != null ? "&actionError="
								+ request.getAttribute("actionError")
								: ""%>';
</script>
<%
	}
%>
<dhv:evaluate if="<%=!isPopup(request)%>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OperatoriCommerciali.do">Operatori Commerciali</a> > 
<%
	if (request.getParameter("return") == null) {
%>
<a href="OperatoriCommerciali.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%
	} else if (request.getParameter("return").equals("dashboard")) {
%>
<a href="OperatoriCommerciali.do?command=Dashboard">Cruscotto</a> >
<%
	}
%>
Scheda OperatoreCommerciale
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>

<br>
<br>
<%@ include file="../../controlliufficiali/diffida_list.jsp" %>

<dhv:evaluate if="<%=!(request.getAttribute("disable") == null)%>" >
<br />
<strong><font color="red">Funzionalit� non consentita.</font></strong>
<br /><br /><br />
</dhv:evaluate>


<!-- <form method="post" action = "OperatoriCommerciali.do?command=Modify"> -->


<%
	String param1 = "orgId=" + OrgDetails.getOrgId();
%>


 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
<dhv:permission name="">
	<table width="100%" border="0">
		<tr>
			<%-- aggiunto da d.dauria--%>

			<td nowrap align="right">
			<!-- img
				src="images/icons/stock_print-16.gif" border="0" align="absmiddle"
				height="16" width="16" /> <a
				href="SchedaPrint.do?command=PrintReport&file=stabilimenti&id=<%= OrgDetails.getId() %>"><dhv:label
				name="stabilimenti.osa.print">Stampa Scheda stabilimenti</dhv:label></a-->
				
				
				
 		  <%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda Operatori Commerciali" value="Stampa Scheda Operatori Commerciali"		onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '-1', '-1', '-1', 'operatoricommerciali', 'SchedaOperatoriCommerciali');"--%>
 
				
						<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa Scheda" value="Stampa Scheda"		onClick="openRichiestaPDF2('<%= OrgDetails.getId() %>', '-1', '-1', '-1', '10');">
 
				
			</td>


			<%-- fine degli inserimenti --%>
		</tr>
	</table>
</dhv:permission>


<dhv:container name="operatori_commerciali" selected="details" object="OrgDetails" param="<%=param1%>" appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>' hideContainer="<%=!OrgDetails.getEnabled()
						|| OrgDetails.isTrashed()%>">



<%
if (request.getAttribute("msgbdu")!=null)
{
String messaggio = (String)	request.getAttribute("msgbdu");
if (messaggio.equalsIgnoreCase("OK"))
{
	%>
	<font color = "green">Il Controllo e stato inserito In BDU</font>
	<%
}
else
{
	%>
		<font color = "red">Attenzione si � verificato un errore nell'inserimento in BDU : <%=messaggio %></font>
	
	<%
	
}
}

%>



<%if (User.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_GISA){ %>
<%} %>
<dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
	    <dhv:permission name="operatori-commerciali-delete"><input type="button" value="<dhv:label name="accounts.accounts_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('OperatoriCommerciali.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','OperatoriCommerciali.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
	  </dhv:evaluate>
<%
java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime());
if( OrgDetails.getCessato() == 1 || (OrgDetails.getCessato() == 1 && OrgDetails.getSource() == 1) || (OrgDetails.getSource() == 1 && OrgDetails.getDateF()!= null && OrgDetails.getDateF().before(d))){
	
	
}else{
%>


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
	  
	  
	  
<%}%>
<br/>


<style>
.ovale { border-style:solid; border-color:#405c81; border-width:1px; }
</style>

<%if (OrgDetails.getDataChiusuraCanile()==null) {%>
<br> <br>
<%
if (User.getUserRecord().getRoleId()==Role.GRUPPO_GISA || User.getUserRecord().getRoleId()==32)
{ 
%>
	
	
	 <div class="ovale">
	 <br>
	 <p><center><b>Questa funzione consente di cessare lo stabilimento senza passare per SCIA, nel caso in cui la cessazione sia avvenuta prima della normativa 318/15</b></center></p>
	 <br><br>
	 <center>
	 <input type="button" value="CESSAZIONE D'UFFICIO" onclick="if (confirm('Attenzione. Procedere alla cessazione d\'ufficio di questo operatore commerciale? ')) {openPopUpCessazioneAttivita();}" width="120px;">
	 </center>
	<br><br>
	 </div>
<%
}
%>
	 
	 <jsp:include page="../dialog_cessazione_attivita.jsp">
	 <jsp:param value="<%=OrgDetails.getOrgId() %>" name="idAnagrafica"/>
	 <jsp:param value="OperatoriCommerciali.do?command=CessazioneAttivita" name="urlSubmitCessazione"/>
	 </jsp:include>
	  <br> <br>
<%} else { %>

<jsp:useBean id="cessazioneBDU" class="java.lang.String" scope="request"/>
<jsp:useBean id="cessazioneVAM" class="java.lang.String" scope="request"/>

<%if (cessazioneBDU!=null)  {%> <font color="green"><%= cessazioneBDU%></font><br/> <%} %>
<%if (cessazioneVAM!=null)  {%> <font color="green"><%= cessazioneVAM%></font><br/> <%} %>

<%} %>


<%--dhv:container name="<%= (request.getAttribute("visibilita")!=null && "si".equals(request.getAttribute("visibilita")))  ? ( ( request.getAttribute("tipologiaCanile")!=null ) ? "accountsCensimenti" : "accounts" ) : "accountsristretto" %>" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>"--%>
<%--dhv:container name="accounts" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>"--%>
<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">


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
     <jsp:param name="tipo_dettaglio" value="10" />
     </jsp:include>

<% } else { %>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
    </th>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Asl di Riferimento</dhv:label>
    </td>
    <td>
    <%
    if(OrgDetails.getSiteId()==-1 || OrgDetails.getSiteId()==0) {%>
    	A.S.L Fuori Regione
    <%}else { %>
       <%= SiteList.getSelectedValue(OrgDetails.getSiteId()) %>
       <%} %>
    </td>
  </tr>
  
  <!-- Campo non VISUALIZZATO -->
  
  <tr style="display:none" class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.owner">Account Owner</dhv:label>
    </td>
    <td>
      <dhv:permission name="contacts-internal_contacts-view" none="true">
        <dhv:username id="<%=OrgDetails.getOwner()%>" />
      </dhv:permission>
      <dhv:permission name="contacts-internal_contacts-view">
        <a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%=UserUtils.getUserContactId(request, OrgDetails
									.getOwner())%>"><dhv:username id="<%=OrgDetails.getOwner()%>" /></a>
      </dhv:permission>
      <dhv:evaluate if="<%=!(OrgDetails.getHasEnabledOwnerAccount())%>"><font color="red">*</font></dhv:evaluate>
    </td>
  </tr>
  
<dhv:evaluate if="<%=OrgDetails.getTypes().size() > 0%>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.account.types">Account Type(s)</dhv:label>
    </td>
    <td>
       <%=toHtml(OrgDetails.getTypes().valuesAsString())%>&nbsp;
    </td>
  </tr>
</dhv:evaluate>
<dhv:include name="organization.source" none="true">
  <dhv:evaluate if="<%=OrgDetails.getSource() != -1%>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="contact.source">Source</dhv:label>
      </td>
      <td>
        <%=SourceList.getSelectedValue(OrgDetails
										.getSource())%> 
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>

<dhv:evaluate if="<%=OrgDetails.getListatipologiaOperatoriCommerciali().size()>0%>">
    <tr class="containerBody">
		<td nowrap class="formLabel">Specie Animali</td>
		<td>
		<%--<%=(OrgDetails.getTipoOrganization() == 21) ? "Sindaco del Comune di " : "" %>--%>
        	<%HashMap<Integer,String> listaTipi = OrgDetails.getListatipologiaOperatoriCommerciali() ;
        	
        	Iterator<Integer> itKiavi = listaTipi.keySet().iterator();
        	while (itKiavi.hasNext())
        	{
        		out.println("<b>"+listaTipi.get(itKiavi.next())+"</b><br/>");
        	}
        	
        	%>&nbsp;
		</td>
	</tr>
  </dhv:evaluate>

 <dhv:evaluate if="<%=hasText(OrgDetails.getName())%>">
    <tr class="containerBody">
		<td nowrap class="formLabel">Impresa</td>
		<td>
		<%--<%=(OrgDetails.getTipoOrganization() == 21) ? "Sindaco del Comune di " : "" %>--%>
        	<%=toHtml(OrgDetails.getName())%>&nbsp;
        	<%= OrgDetails.getDataChiusuraCommerciale() != null ? " - CESSATO " : "" %>
		</td>
	</tr>
  </dhv:evaluate>
  
  <dhv:evaluate if="<%=hasText(OrgDetails.getPartitaIva())%>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      Partita IVA
			</td>
			<td>
         <%=toHtml(OrgDetails.getPartitaIva())%>&nbsp;
			</td>
		</tr>
  </dhv:evaluate>
  
  <dhv:evaluate if="<%=hasText(OrgDetails.getCodiceFiscale())%>">
    <tr class="containerBody">
			<td nowrap class="formLabel">
      Codice Fiscale
			</td>
			<td>
         <%=toHtml(OrgDetails.getCodiceFiscale())%>&nbsp;
			</td>
		</tr>
  </dhv:evaluate>
  
 
    <tr class="containerBody">
		<td nowrap class="formLabel">
    	  <dhv:label name="">Categoria di Rischio</dhv:label>
		</td>
		<td>
		 <dhv:evaluate if="<%=OrgDetails.getCategoriaRischio() > 0%>">
        	 <%=OrgDetails.getCategoriaRischio()%>
        	   </dhv:evaluate>
        	  <dhv:evaluate if="<%=OrgDetails.getCategoriaRischio()== 0%>">
        	 <%=3%>
        	   </dhv:evaluate>
		</td>
	</tr>
  
  <dhv:evaluate if="<%=OrgDetails.getProssimoControllo() != null%>">
    <tr class="containerBody">
		<td nowrap class="formLabel">
    	  <dhv:label name="">Prossimo Controllo</br>con la tecnica della Sorveglianza</dhv:label>
		</td>
		<td>
        	 <%=toHtml(new SimpleDateFormat("dd/MM/yyyy").format(OrgDetails.getProssimoControllo()))%>
		</td>
	</tr>
  </dhv:evaluate>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Controlli Ufficiali
    </td>
    <td>
      Aperti: <%=OrgDetails.getNumeroCUaperti()%>&nbsp; Chiusi: <%=OrgDetails.getNumeroCUchiusi()%>&nbsp;
    </td>
  </tr>
  
  
  
  <dhv:evaluate if="<%=hasText(OrgDetails.getAutorizzazione())%>">
    <tr class="containerBody">
		<td nowrap class="formLabel">
    	  <dhv:label name="">Autorizzazione</dhv:label>
		</td>
		<td>
        	 <%=toHtml(OrgDetails.getAutorizzazione())%>
		</td>
	</tr>
  </dhv:evaluate>
  
    <dhv:evaluate if="<%=OrgDetails.getDataRicezioneAutorizzazione()!=null%>">
    <tr class="containerBody">
		<td nowrap class="formLabel">
    	  <dhv:label name="">Data</dhv:label>
		</td>
		<td>
        	 <%=toDateString(OrgDetails.getDataRicezioneAutorizzazione())%>
		</td>
	</tr>
  </dhv:evaluate>
  
<%--   <dhv:evaluate if="<%=hasText(OrgDetails.getBanca())%>"> --%>
<!--     <tr class="containerBody"> -->
<!-- 			<td nowrap class="formLabel"> -->
<%--       <dhv:label name="">Stato</dhv:label> --%>
<!-- 			</td> -->
<!-- 			<td> -->
<%--          <%=toHtml("In Attesa del " + OrgDetails.getBanca())%>&nbsp; --%>
<!-- 			</td> -->
<!-- 		</tr> -->
<%--   </dhv:evaluate> --%>
  
    <tr class="containerBody">
			<td nowrap class="formLabel">Stato
			</td>
			<td>
  			<%=(OrgDetails.getDataChiusuraCanile()!=null) ? "CHIUSA IL "+toDateasString(OrgDetails.getDataChiusuraCanile()) : "ATTIVITA" %>
			</td>
		</tr>
  
  
  
  <dhv:evaluate if="<%=hasText(OrgDetails.getUrl())%>">
    <tr class="containerBody">
		<td nowrap class="formLabel">
    		<dhv:label name="">Indirizzo Supplementare</dhv:label>
		</td>
		<td>
       		<%=toHtml(OrgDetails.getUrl())%>
		</td>
	</tr>
  </dhv:evaluate>
    

 </table>
 </br>
 
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
		
		<dhv:evaluate if="<%= (hasText(OrgDetails.getCity_legale_rapp())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Comune di residenzas</dhv:label>
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
		
</table>
 <br>

<br />


<dhv:include name="organization.addresses" none="true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.Addresses">Addresses</dhv:label></strong>
	  </th>
  </tr>

<%
	Iterator iaddress = OrgDetails.getAddressList()
						.iterator();
				if (iaddress.hasNext()) {
					while (iaddress.hasNext()) {
						OrganizationAddress thisAddress = (OrganizationAddress) iaddress
								.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <%=toHtml(thisAddress.getTypeName())%>
      </td>
      <td>
        <%=toHtml(thisAddress.toString())%>&nbsp;
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
<br />
</dhv:include>


<dhv:include name="organization.emailAddresses" none="true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.EmailAddresses">Email Addresses</dhv:label></strong>
	  </th>
  </tr>
<%
	Iterator iemail = OrgDetails.getEmailAddressList()
						.iterator();
				if (iemail.hasNext()) {
					while (iemail.hasNext()) {
						OrganizationEmailAddress thisEmailAddress = (OrganizationEmailAddress) iemail
								.next();
%>    
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <%=toHtml(thisEmailAddress
														.getTypeName())%>
      </td>
      <td>
        <a href="mailto:<%=toHtml(thisEmailAddress.getEmail())%>"><%=toHtml(thisEmailAddress.getEmail())%></a>&nbsp;
        <dhv:evaluate if="<%=thisEmailAddress
													.getPrimaryEmail()%>">        
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
        <font color="#9E9E9E"><dhv:label name="contacts.NoEmailAdresses">No email addresses entered.</dhv:label></font>
      </td>
    </tr>
<%
	}
%>

</table>
<br />
</dhv:include>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </td>
    <td>
      <%=toHtml(OrgDetails.getNotes())%>&nbsp;
    </td>
  </tr>
</table>
<br />



<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_contacts_calls_details.RecordInformation">Record Information</dhv:label></strong>
    </th>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
    </td>
    <td>
      <dhv:username id="<%=OrgDetails.getEnteredBy()%>" />
      <zeroio:tz timestamp="<%=OrgDetails.getEntered()%>" timeZone="<%=User.getTimeZone()%>" showTimeZone="false" />
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label>
    </td>
    <td>
      <dhv:username id="<%=OrgDetails.getModifiedBy()%>" />
      <zeroio:tz timestamp="<%=OrgDetails.getModified()%>" timeZone="<%=User.getTimeZone()%>" showTimeZone="false" />
    </td>
  </tr>
</table>

<% } %>

</dhv:container>

<%=addHiddenParams(request,
									"popup|popupType|actionId")%>
<%
	if (request.getParameter("return") != null) {
%>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%
	}
%>
<%
	if (request.getParameter("actionplan") != null) {
%>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%} %>
<!-- </form> -->


<script type="text/javascript">

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
</script>