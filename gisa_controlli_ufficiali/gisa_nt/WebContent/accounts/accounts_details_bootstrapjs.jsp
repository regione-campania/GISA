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
  - ANY DAMAGES, INCLUDIFNG ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<%@page import="org.aspcfs.modules.audit.base.Audit"%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>
<%@page import="java.sql.*"%>
<%@page import="org.aspcfs.modules.lineeattivita.base.*"%>
<%@page import="org.aspcf.modules.report.util.*"%>

<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>		
<!-- 		<script type="text/javascript" src="javascript/jquery-1.3.min.js"></script> -->
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CountryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IstatList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="Voltura" class="org.aspcfs.modules.cessazionevariazione.base.Ticket" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="container" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>



<script>
function chiamaAction(stringa1){
	var scroll = document.body.scrollTop;
	location.href=stringa1+scroll;
}

function openPopupModulesPdf(orgId){
	var res;
	var result;
		window.open('ManagePdfModules.do?command=PrintSelectedModules&orgId='+orgId,'popupSelect',
		'height=300px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
}
</script>	

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script type="text/javascript">
function controlla_linea_attivita_definita_callback(returnValue) {

	// Alert se returnValue = true
	if (returnValue == true) {
		alert('ATTENZIONE! Per questo stabilimento ancora non è stata definita una linea di attivita'); 

		//abilita un bottone che passa un parametro (tipo gestisci_solo_ateco) alla jsp di modifica. Nella jsp 
		// di modifica se gestisci_solo_ateco=true allora disabilitera tutti i campi eccetto
		document.getElementById("buttonDefinisciLdA").style.display="";
	} else {
		document.getElementById("buttonDefinisciLdA").style.display="none";
	}
	 
} 

function controlla_linea_attivita_definita_2() {	


	// Alert se returnValue = true
	if (document.getElementById("la")!=null) {
	if (document.getElementById("la").value == 'Linea di attivita\' non ancora specificata'
		|| document.getElementById("la").value == 'Linea di attivita non ancora specificata') {
		alert('ATTENZIONE! Per questo stabilimento ancora non è stata definita una linea di attivita'); 

		//abilita un bottone che passa un parametro (tipo gestisci_solo_ateco) alla jsp di modifica. Nella jsp 
		// di modifica se gestisci_solo_ateco=true allora disabilitera tutti i campi eccetto
		if(document.getElementById("buttonDefinisciLdA")!=null)
				document.getElementById("buttonDefinisciLdA").style.display="";
	} else {
		if(document.getElementById("buttonDefinisciLdA")!=null)
			document.getElementById("buttonDefinisciLdA").style.display="none";
	}
	}
}

function controlla_linea_attivita_definita( orgId ) {
	PopolaCombo.controlla_linea_attivita_definita_per_orgId( orgId, controlla_linea_attivita_definita_callback);
}


function import_imbarcazione(orgId, id_utente) {
	PopolaCombo.importImbarcazione( orgId, id_utente,  imbarcazionecallback);
}

function imbarcazionecallback(returnValue) {

	var org_id =  document.getElementById('orgID').value;
	if (returnValue == true) { //se l'aggiornamento è avvenuto con successo
		
		alert('IMPORT AVVENUTO CON SUCCESSO!'); 
		window.location.href = 'Imbarcazioni.do?command=Details&orgId='+org_id;

	} else {
	
		alert('L\'IMPORT NON E\' AVVENUTO CON SUCCESSO'); 
		
	}
	
	 
}

</script>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>

<%
	ArrayList<LineeAttivita> linee_attivita = (ArrayList<LineeAttivita>) request.getAttribute("linee_attivita");
	LineeAttivita linea_attivita_principale = (LineeAttivita) request.getAttribute("linea_attivita_principale");


%>

<%if (refreshUrl!=null && !"".equals(refreshUrl)){ %>
<script language="JavaScript" TYPE="text/javascript">
parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>
<%}%>

<body onLoad="javascript:controlla_linea_attivita_definita_2();"> 


<% String containerR  = ""; %>
<% if(container == null || container.equals("") ) { containerR = "accounts";} else { containerR = container; } %>

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<% if(containerR != null && containerR.equals("accounts")){ %>
	<table class="trails" cellspacing="0">
	<tr>
	<td>
	<a href="Accounts.do"><dhv:label name="">Stabilimenti 852</dhv:label></a> > 
	<% if (request.getParameter("return") == null) { %>
	<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="Accounts.do?command=Dashboard">Cruscotto</a> >
	<%}%>
	<dhv:label name="">Scheda Stabilimento 852</dhv:label>
	</td>
	</tr>
	</table>
<% } else { %>
<table class="trails" cellspacing="0">
	<tr>
	<td>
	<a href="RicercaArchiviati.do"><dhv:label name="">Stabilimenti archiviati</dhv:label></a> > 
	<% if (request.getParameter("return") == null) { %>
	<a href="RicercaArchiviati.do?command=Search"><dhv:label name="accounts.SearchResults">Risultati ricerca</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="RicercaArchiviati.do?command=Dashboard">Cruscotto</a> >
	<%}%>
	<dhv:label name="">Scheda Stabilimento 852</dhv:label>
	</td>
	</tr>
	</table>
<% } %>
<%-- End Trails --%>

</dhv:evaluate>




<% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>
<dhv:permission name="accounts-accounts-report-view">
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
        <!-- 
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="Accounts.do?command=PrintReport&id=<%= OrgDetails.getId() %>"><dhv:label name="accounts.osa.print">Stampa Scheda Impresa</dhv:label></a>
       -->
       
       <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
       <%if((OrgDetails.getTipoDest()!=null)&&(OrgDetails.getTipoDest().equals("Autoveicolo"))){ %>
      <%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Comunicazione Numero Registrazione per le attivita mobili" value="Comunicazione Numero Registrazione per le attivita mobili"	onClick="javascript:window.location.href='Accounts.do?command=PrintReport&file=modelloCattivitamobile.xml&id=<%= OrgDetails.getId() %>&addressid=<%= addressid%>&addressid2=<%= addressid2%>&addressid3=<%= addressid3%>';"--%>
 		
 		  <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Comunicazione Numero Registrazione per le attivita mobili" value="Comunicazione Numero Registrazione per le attivita mobili"		onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '<%= addressid%>', '<%= addressid2%>', '<%= addressid3%>', 'modelloCattivitamobile.xml', 'ComunicazioneNumeroRegistrazione');">
 
        <br><%}else{ %>
        <%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Comunicazione Numero Registrazione" value="Comunicazione Numero Registrazione"	onClick="javascript:window.location.href='Accounts.do?command=PrintReport&file=modelloC.xml&id=<%= OrgDetails.getId() %>&addressid=<%= addressid%>&addressid2=<%= addressid2%>&addressid3=<%= addressid3%>';"--%>
        
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Comunicazione Numero Registrazione" value="Comunicazione Numero Registrazione"		onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '<%= addressid%>', '<%= addressid2%>', '<%= addressid3%>', 'modelloC.xml', 'ComunicazioneNumeroRegistrazione');">
 
 
 <%} %>
 <%if((OrgDetails.getCessato()==1)||((OrgDetails.getSource()==1)&& OrgDetails.getDateF() !=null && (OrgDetails.getDateF().before(d)))){
 if(OrgDetails.getTipoDest().equals("Es. Commerciale") || OrgDetails.getTipoDest().equals("Distributori")){%>
  <%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Certificato Cessazione" value="Certificato Cessazione"	onClick="javascript:window.location.href='Accounts.do?command=PrintReport&file=modelloCessazione.xml&id=<%= OrgDetails.getId() %>&addressid=<%= addressid%>&addressid2=<%= addressid2%>&addressid3=<%= addressid3%>';"--%>
 	
 	 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Certificato Cessazione" value="Certificato Cessazione"	onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '<%= addressid%>', '<%= addressid2%>', '<%= addressid3%>', 'modelloCessazione.xml', 'SchedaCessazione');">
 
 	
 	
 	<%}else{ %>	
 	<%--img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Certificato Cessazione" value="Certificato Cessazione"	onClick="javascript:window.location.href='Accounts.do?command=PrintReport&file=modelloCessazioneMobili.xml&id=<%= OrgDetails.getId() %>&addressid=<%= addressid%>&addressid2=<%= addressid2%>&addressid3=<%= addressid3%>';"--%>
 	 
 	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Certificato Cessazione" value="Certificato Cessazione"	onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '<%= addressid%>', '<%= addressid2%>', '<%= addressid3%>', 'modelloCessazioneMobili.xml', 'SchedaCessazione');">
 	 
 	 
 	 
 	  <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Comunicazione Numero Registrazione" value="Comunicazione Numero Registrazione"		onClick="openRichiestaPDF('<%= OrgDetails.getId() %>', '<%= addressid%>', '<%= addressid2%>', '<%= addressid3%>', 'modelloC.xml', 'ComunicazioneNumeroRegistrazione');">
 
 	<%} %>
  <%}%>
        <br>
        <%@ include file="../manage_pdf/link_stampa_scheda.jsp" %>
        <br>
        <br>
        


       <%-- <%if((OrgDetails.getTipoDest()!=null)&& (OrgDetails.getTipoDest().equals("Autoveicolo"))){
        	
        	%>
    	 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa scheda" value="Stampa scheda"	onClick="javascript:window.location.href='Accounts.do?command=PrintReport&file=account_attivitaMobili.xml&id=<%= OrgDetails.getId() %>&addressid=<%= addressid%>&addressid2=<%= addressid2%>&addressid3=<%= addressid3%>';">
 		<%}else{
 			
 			%>
 		<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="Stampa scheda" value="Stampa scheda"	onClick="javascript:window.location.href='Accounts.do?command=PrintReport&file=account.xml&id=<%= OrgDetails.getId() %>&addressid=<%= addressid%>&addressid2=<%= addressid2%>&addressid3=<%= addressid3%>';">
 		<%} %>
 		--%> 
 		
 		
 		
      </td>
    </tr>
  </table>
</dhv:permission>

<%@ include file="../../controlliufficiali/diffida_list.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId();   
%>
<script>

function importaInNuovaAnagrafica(tipoAttivita,orgId)
{
	if(tipoAttivita=='Autoveicolo')
		{
		if(confirm("Attenzione è necessario verificare se l'attività in corso di import risulta essere \"trasporto conto terzi\".\n\n Nel caso di \"trasporto contro proprio\" l'operazione di import non deve essere eseguita.\n\n Premere \"OK\" per \"trasporto conto terzi\" - \"ANNULLA\" per \"contro proprio\""))
		{
			location.href='OpuStab.do?command=CaricaImport&orgId='+orgId ;
		}
		
		}else
		location.href='OpuStab.do?command=CaricaImport&orgId='+orgId ;
	
	}
</script>
<!--<dhv:permission name="impresa-linkprintmodules-view">
<div style="padding-right: 200px" align="right">
	<a href="javascript:openPopupModulesPdf('<%= OrgDetails.getOrgId() %>');">Stampa moduli precompilati</a>
</div>
</dhv:permission>-->

<%
ArrayList<LineeAttivita> linee_attivita_secondarie = (ArrayList<LineeAttivita>) request.getAttribute("linee_attivita_secondarie");

%>
<dhv:container name="<%=containerR%>" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="">

<% if(containerR != null && containerR.equals("accounts") ){
	
	if( OrgDetails.getTipoDest()!= null && OrgDetails.getTipoDest().equalsIgnoreCase("autoveicolo") 
			&& linee_attivita_secondarie !=null && (linee_attivita_secondarie.size())>1 )
		
	{
		
		if( OrgDetails.getCessato() == 1|| (OrgDetails.getCessato() == 1 && OrgDetails.getSource() == 1) || 
				 
				(OrgDetails.getSource() == 1 && OrgDetails.getDateF()!= null && OrgDetails.getDateF().before(d))
			){}else{
		%>
		
	 
	 <jsp:include page="../dialog_cessazione_attivita.jsp">
	 <jsp:param value="<%=OrgDetails.getOrgId() %>" name="idAnagrafica"/>
	 <jsp:param value="Accounts.do?command=CessazioneAttivita" name="urlSubmitCessazione"/>
	 </jsp:include>
	 
	 <h3>Attenzione! Per importare questo stabilimento, contattare l'Help Desk in quanto risultano associate più linee di attività</h3>
	 <%} %>
		
		<%
	}
	else
	{
	
	%>

 
 
 
 
 <%if( OrgDetails.getCessato() == 1|| (OrgDetails.getCessato() == 1 && OrgDetails.getSource() == 1) || 
		 
		(OrgDetails.getSource() == 1 && OrgDetails.getDateF()!= null && OrgDetails.getDateF().before(d))
	)
 {
	 %>
	 
	 
	 
	 <%
	 
 }else{%>
 
 
 
	 
	 <jsp:include page="../dialog_cessazione_attivita.jsp">
	 <jsp:param value="<%=OrgDetails.getOrgId() %>" name="idAnagrafica"/>
	 <jsp:param value="Accounts.do?command=CessazioneAttivita" name="urlSubmitCessazione"/>
	 </jsp:include>
	 
	 
	  <dhv:permission name="opu-import-add">
	 
	 <%if(User.getSiteId()<=0 || (User.getSiteId()>0 && User.getSiteId()==OrgDetails.getSiteId())){ %>
	  <div align="center">
			<input type="button" class="yellowBigButton"
				value="Importa in Anagrafica stabilimenti"
			    onClick="importaInNuovaAnagrafica('<%=OrgDetails.getTipoDest()%>',<%=OrgDetails.getOrgId()%>)">
	  </div>
	  <%} %>
	  </dhv:permission>
	  <%}}%>
<br>
<br>

<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>"> 
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <!--<dhv:permission name="accounts-accounts-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='Accounts.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>-->
</dhv:evaluate>


<%
	
	if((OrgDetails.getSource()!=1) && (OrgDetails.getCessato()!=1)){
	%>
	  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
<%-- 	    <dhv:permission name="accounts-accounts-edit"><input type="button" id="buttonDefinisciLdA" style="display: none" value="Definisci linee di attivita"	onClick="javascript:window.location.href='Accounts.do?command=Modify&definisciLdA=true&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission> --%>
	  </dhv:evaluate>

	  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
<%-- 	    <dhv:permission name="accounts-accounts-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='Accounts.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission> --%>
	  </dhv:evaluate>
	  
	  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
	    <dhv:permission name="accounts-accounts-edit">
<%-- 	      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='Accounts.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';"> --%>
	    </dhv:permission>
	  </dhv:evaluate>
	  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
<%-- 	    <dhv:permission name="accounts-accounts-delete"><input type="button" value="<dhv:label name="accounts.accounts_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('Accounts.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Accounts.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission> --%>
	  </dhv:evaluate>
	  
	
	  
	   <!--  Adding import imbarcazione -->
		<dhv:evaluate
			if="<%=(linea_attivita_principale != null && (linea_attivita_principale.getCodice_istat()
								.equals("03.11.00")
								|| linea_attivita_principale.getCodice_istat().equals("01.70.00")
								|| linea_attivita_principale.getCodice_istat().equals("03.21.00") || linea_attivita_principale
								.getCodice_istat().equals("50.20.00")))%>">
			<dhv:permission name="accounts-accounts-import-mbarcazione-view">
				<input type="button"
					value="<dhv:label name="">Importa in Imbarcazone</dhv:label>"
					onClick="javascript:import_imbarcazione(<%= OrgDetails.getOrgId() %>, <%= User.getUserId()%>);">
			</dhv:permission>
		</dhv:evaluate>



		<%}else if((OrgDetails.getSource()==1)&& OrgDetails.getDateF()!= null && (OrgDetails.getDateF().after(d))){ %>
  

  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="accounts-accounts-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='Accounts.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>

  </dhv:evaluate>
  
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="accounts-accounts-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='Accounts.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
  
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="accounts-accounts-delete"><input type="button" value="<dhv:label name="accounts.accounts_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('Accounts.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Accounts.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>
  <%}
  else
  {
	  /*POSSIBILITA DI MODIFICARE QUALORA MANCHI LA DATA FINE CARATTERE*/
	  if((OrgDetails.getSource()==1)&& OrgDetails.getDateF()== null)
	  {
		  %>
		   <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
	    <dhv:permission name="accounts-accounts-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='Accounts.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
	  </dhv:evaluate>
		  <%
	   }
	  
  }
%>
  	<%if(OrgDetails.getTipoDest()!=null || !OrgDetails.getTipoDest().equalsIgnoreCase("null")){
  	
  	
  	%>
  <dhv:evaluate if='<%= (OrgDetails.getTipoDest().equalsIgnoreCase("distributori")) %>'>
 
  
    <input type="button" value="<dhv:label name="">Importa Distributori da File </dhv:label>" onClick="javascript:window.location.href='Accounts.do?command=InsertDistributori&id=<%=OrgDetails.getId()%>'">
   
  </dhv:evaluate>

  
  <%} 


%>

<%
Timestamp now = new Timestamp(System.currentTimeMillis());


 }
else
{
	%>
	
	
<%if( OrgDetails.getCessato() == 1 || (OrgDetails.getCessato() == 1 && OrgDetails.getSource() == 1) || (OrgDetails.getSource() == 1 && OrgDetails.getDateF()!= null && OrgDetails.getDateF().before(d))){
	
	
}else{
%>
<dhv:permission name="opu-import-add">
<center><font color="red"><b><%="Lo stabilimento ha linee non aggiornate. " %></b></font></center>
  <div align="center">
 	 <br/><br/>
 		<%--<input type="button" class="yellowBigButton" value="AGGIORNA LINEE DI ATTIVITA' PREGRESSE DA MASTERLIST" 
 		onClick="openPopupLarge('Accounts.do?command=PrepareUpdateLineePregresse&orgId=<%=OrgDetails.getOrgId() %>&lda_prin=<%=linea_attivita_principale.getId() %>')"
 		--%>
 	<%-- onClick="loadModalWindow();window.location.href='OpuStab.do?command=PrepareUpdateLineePregresse&stabId=<%=StabilimentoDettaglio.getIdStabilimento() %>'"--%>
 	 <%if(User.getSiteId()<=0 || (User.getSiteId()>0 && User.getSiteId()==OrgDetails.getSiteId())){ %>
 	<input type="button" class="yellowBigButton"
				value="Importa in Anagrafica stabilimenti"
			    onClick="javascript:window.location.href='OpuStab.do?command=CaricaImport&orgId=<%=OrgDetails.getOrgId()%>'">
			    <%} %>
 <br/><br/>	
 	</div>
</dhv:permission>
<% }}%>
<br>
<br>

<jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">
    <jsp:param name="riferimentoIdPreaccettazione" value="<%=OrgDetails.getOrgId() %>" />
    <jsp:param name="riferimentoIdNomePreaccettazione" value="orgId" />
    <jsp:param name="riferimentoIdNomeTabPreaccettazione" value="organization" />
    <jsp:param name="userIdPreaccettazione" value="<%=User.getUserId() %>" />
</jsp:include>

<% if(OrgDetails.getTipoDest().equals("Es. Commerciale") || OrgDetails.getTipoDest().equals("Distributori")){%> 
 <jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="1" />
</jsp:include>
<%}  else { %>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
    </th>
  </tr>
  
  <input type="hidden" id="orgID" value="<%=OrgDetails.getOrgId() %>" name="orgID"/>
  
<dhv:include name="accounts-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.site">Site</dhv:label>
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
  
<dhv:include name="accounts-name" none="true">
	<dhv:evaluate if="<%= hasText((OrgDetails.getName())) || hasText((Voltura.getName())) %>">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="">Impresa</dhv:label>
        </td>
        <td>
        			         	<%--= ((OrgDetails.getVoltura() && hasText(Voltura.getName()))?(toHtml(Voltura.getName())):(toHtml(OrgDetails.getName()))) --%>
          <%= toHtmlValue(OrgDetails.getName()) %>
       </td>
      </tr>
      </dhv:evaluate>
</dhv:include>  
  
<%--<dhv:evaluate if="<%= hasText((OrgDetails.getBanca())) || hasText((Voltura.getBanca())) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Denominazione</dhv:label>
			</td>
			<td>
			         	
         		<%= toHtml(OrgDetails.getBanca()) %>
			</td>
		</tr>
</dhv:evaluate>--%>

		

  
<dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.accountNumber">Account Number</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getAccountNumber()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= hasText(OrgDetails.getCodiceImpresaInterno()) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="organization.codice_impresa_interno">Account Number</dhv:label>
    </td>
    <td>
       <%= toHtml(OrgDetails.getCodiceImpresaInterno()) %>&nbsp;
    </td>
  </tr>
</dhv:evaluate>

<dhv:evaluate if="<%= OrgDetails.getTypes().size() > 0 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.account.types">Account Type(s)</dhv:label>
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

  
<!-- LINEE DI ATTIVITA -->
  	<%if (linea_attivita_principale!=null){  %>
  	<dhv:evaluate if="<%= hasText( linea_attivita_principale.getCategoria() ) && hasText( linea_attivita_principale.getCategoria() ) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Codice Ateco/Linea di Attivita Principale</dhv:label>
			</td>
			<dhv:evaluate if="<%= hasText( linea_attivita_principale.getCategoria() ) && hasText( linea_attivita_principale.getCategoria() ) %>">
				<td>
	      			<%= toHtml( linea_attivita_principale.getCodice_istat() + " " + linea_attivita_principale.getDescrizione_codice_istat()) %> <br/>
	        		<%= toHtml( linea_attivita_principale.getCategoria() + " - " + linea_attivita_principale.getLinea_attivita() ) %>&nbsp;
	        		<br><br>
	        		
				</td>
			</dhv:evaluate>
			<dhv:evaluate if="<%= hasText( linea_attivita_principale.getCategoria() ) && !hasText( linea_attivita_principale.getCategoria() ) %>">
				<td>
	        		<%= toHtml( linea_attivita_principale.getCodice_istat() + " " + linea_attivita_principale.getDescrizione_codice_istat()) %> <br/>
	        		<%= toHtml( linea_attivita_principale.getCategoria()) %>&nbsp;
				</td>
			</dhv:evaluate>
			<input type = "hidden" name = "la" id = "la" value ="<%=linea_attivita_principale.getCategoria() %>">
		</tr>
  	</dhv:evaluate>
<%}
  	else{
  	%>
  				<input type = "hidden" name = "la" id = "la" value ="Linea di attivita\' non ancora specificata">
  	
  	
  	<%} %>

<dhv:include name="organization.sicDescription" none="true">
    <dhv:evaluate if="<%= hasText(OrgDetails.getSicDescription()) %>">
      <tr class="containerBody">
	    <td nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.sicDescription">SIC Description</dhv:label>
		</td>
		<td>
         <%= toHtml(OrgDetails.getSicDescription()) %>&nbsp;
		</td>
	  </tr>
    </dhv:evaluate>
</dhv:include>



<!--linee_attivita_secondarie-->

	<!-- CODICI ISTAT SECONDARI (NEW) -->
	<% if (!linee_attivita_secondarie.isEmpty() ) { 
	
		
	%>
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Codici Ateco/Linea di Attivita (Secondarie)</dhv:label>  
   			</td>
   			
		    <td>
		    		<%  int i=0;
		    			for (LineeAttivita linea: linee_attivita_secondarie) {
		    				if(linea.isPrimario()!=true)
		    				{
		    				i++;
		    				 %>
			    			<b>Codice <%= i %>&nbsp;</b> : 
			    			<% if (!linea.getLinea_attivita().isEmpty()) { %>
			    			    <%= toHtml( linea.getCodice_istat() + " " + linea.getDescrizione_codice_istat()) %> <br/>
			    			    <b>Linea Attivita <%= i %>&nbsp;</b> :
	        					<%= toHtml( linea.getCategoria() + " - " + linea.getLinea_attivita() ) %>&nbsp;
	        				<% } else { %>	
								<%= toHtml( linea.getCodice_istat() + " " + linea.getDescrizione_codice_istat()) %> <br/>
								<b> Linea Attivita <%= i %>&nbsp;</b> :
	        					<%= toHtml( linea.getCategoria() ) %>&nbsp;
			    			<% } %>
	        			
			    			<br></br>
			    			<%
			    		 } }
			    	%>
		     </td>
		</tr>
	
	<% } %>
	
	
		 <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Domicilio Digitale</dhv:label>
    </td>
    <td>
     <%= toHtml(OrgDetails.getDomicilioDigitale()) %> 
    </td>
  </tr>
		
		
    <dhv:evaluate if="<%= hasText(OrgDetails.getCab()) %>">
      <tr class="containerBody">
	    <td nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.cab">CAB</dhv:label>
		</td>
		<td>
         <%= toHtml(OrgDetails.getCab()) %>&nbsp;
		</td>
	  </tr>
    </dhv:evaluate>
<dhv:include name="organization.url" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getUrl()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.WebSiteURL">Web Site URL</dhv:label>
      </td>
      <td>
        <a href="<%= toHtml(OrgDetails.getUrlString()) %>" target="_new"><%= toHtml(OrgDetails.getUrl()) %></a>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.industry" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getIndustryName()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.Industry">Industry</dhv:label>
      </td>
      <td>
         <%= toHtml(OrgDetails.getIndustryName()) %>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.dunsType" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getDunsType()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.duns_type">DUNS Type</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getDunsType()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.employees" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getEmployees() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="organization.employees">No. of Employees</dhv:label>
      </td>
      <td>
         <%= OrgDetails.getEmployees() %>
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.potential" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getPotential() > 0) %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Potential">Potential</dhv:label>
    </td>
    <td>
       <zeroio:currency value="<%= OrgDetails.getPotential() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
  </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.revenue" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getRevenue() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.Revenue">Revenue</dhv:label>
      </td>
      <td>
         <zeroio:currency value="<%= OrgDetails.getRevenue() %>" code='<%= applicationPrefs.get("SYSTEM.CURRENCY") %>' locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </td>
    </tr>
  </dhv:evaluate>
  </dhv:include>
  <dhv:include name="organization.ticker" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getTicker()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.TickerSymbol">Ticker Symbol</dhv:label>
      </td>
      <td>
         <%= toHtml(OrgDetails.getTicker()) %>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.dunsNumber" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getDunsNumber()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.duns_number">DUNS Number</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getDunsNumber()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.businessNameTwo" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getBusinessNameTwo()) %>">
    <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.business_name_two">Business Name 2</dhv:label>
    </td><td>
       <%= toHtml(OrgDetails.getBusinessNameTwo()) %>&nbsp;
    </td></tr>
  </dhv:evaluate>
</dhv:include>

<dhv:include name="accounts-size" none="true">
  <dhv:evaluate if="<%= hasText(OrgDetails.getAccountSizeName()) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.accountSize">Account Size</dhv:label>
      </td>
      <td>
      
      <%= toHtml(OrgDetails.getAccountSizeName()) %>&nbsp;
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.segment" none="true">
  <dhv:evaluate if="<%= (OrgDetails.getSegmentId() > 0) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.segment">Segment</dhv:label>
      </td>
      <td>
         <%= SegmentList.getSelectedValue(OrgDetails.getSegmentId()) %>
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>
<dhv:include name="organization.directBill" none="true">
  <dhv:evaluate if="<%= OrgDetails.getDirectBill() %>">
    <tr>
      <tr class="containerBody">
        <td nowrap class="formLabel">
          Direct Bill
        </td>
      <td>
      <input type="checkbox" name="directBill" CHECKED DISABLED />
      </td>
    </tr>
  </dhv:evaluate>
</dhv:include>

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
    <dhv:evaluate if="<%= hasText(OrgDetails.getNomeCorrentista()) %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Targa Autoveicolo</dhv:label>
			</td>
			<td>
         		<%= toHtml(OrgDetails.getNomeCorrentista()) %>&nbsp;
			</td>
		</tr>
  	</dhv:evaluate> 
  	<%if(hasText(OrgDetails.getNomeCorrentista())) {%> 
 
  	<%}
  	else{}%>
  	
  	
  <dhv:evaluate if="<%= OrgDetails.getStageId() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        Servizio Competente
      </td>
      <td>
        <%= StageList.getSelectedValue(OrgDetails.getStageId()) %> 
      </td>
    </tr>
  </dhv:evaluate>
<%if(hasText(OrgDetails.getTipoDest())) {%>
	<tr class="containerBody">
  		<td nowrap class="formLabel">
     		 <dhv:label name="">Attivita</dhv:label>
    	</td>
    	<td>
    	 <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Autoveicolo")%>">
        Mobile
        </dhv:evaluate>
       
        <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Es. Commerciale")%>">
        Fissa
        </dhv:evaluate>
      		<dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Distributori")%>">
        Distributori
        </dhv:evaluate>
   		</td>
  	</tr>
  	<%} %>
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
    <dhv:evaluate if="<%= (OrgDetails.getDataPresentazione() != null) %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Presentazione D.I.A./Inizio Attivita</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= OrgDetails.getDataPresentazione() %>" dateOnly="true" showTimeZone="false" default="&nbsp;" />
      </td>
    </tr>
    </dhv:evaluate>
 </dhv:include> 
 <%--dhv:evaluate if="<%= (OrgDetails.getDate2() != null) %>">
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data inizio attivita</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= OrgDetails.getDate2() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
    </td>
  </tr>
</dhv:evaluate--%>

  <% if(Audit!=null){ %>
  
  <tr class="containerBody" >
      <td nowrap class="formLabel">
        <dhv:label name="osaa.livelloRischio" >Categoria di Rischio</dhv:label>
      </td>
      <td>
         <%= OrgDetails.getCategoriaRischio()%>
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
 
  <tr class="containerBody">
	<td nowrap class="formLabel">
      	<dhv:label name="">Stato Stabilimento</dhv:label>
	</td>
	<td>
	<%
	if( OrgDetails.getCessato() == 1|| (OrgDetails.getCessato() == 1 && OrgDetails.getSource() == 1) || (OrgDetails.getSource() == 1 && OrgDetails.getDateF()!= null && OrgDetails.getDateF().before(d))){ %>
        <font color='red'>Cessato</font> in data: 
		<%if((OrgDetails.getDateF()!= null) && (OrgDetails.getDateF().before(d))){%>
		<zeroio:tz timestamp="<%= OrgDetails.getDateF() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/> ,di conseguenza  non piu gestibile in modifica.	        
	  		<%} %>
		<%}else{
	%>
         <%= ((OrgDetails.getCessato()== 1 ) ? ("<font color='red'>Cessato</font> in data ") : ((OrgDetails.getCessato()==0)?("In Attivita"):("Sospeso in data "))) %>&nbsp;
	


<% if((OrgDetails.getCessato()==1) || (OrgDetails.getCessato()==2)) {%>
  
    
      <zeroio:tz timestamp="<%= OrgDetails.getContractEndDate() %>" dateOnly="true" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>  <%=((OrgDetails.getCessato()==1) ? (",di conseguenza  non piu gestibile in modifica.") : (""))%>&nbsp;
       
<%} }%>

</td>
</tr>


</table>
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
    
        <%= ((OrgDetails.getDataNascitaRappresentante()!=null)?(toHtml(DateUtils.getDateAsString(OrgDetails.getDataNascitaRappresentante(),Locale.ITALY))):("")) %>
         </td>
  </tr>
</dhv:evaluate>
  	 
		<dhv:evaluate if="<%= (hasText(OrgDetails.getLuogoNascitaRappresentante())) %>">			
		<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Comune di Nascita</dhv:label>
			</td>
			<td>
         	<%= toHtml(OrgDetails.getLuogoNascitaRappresentante())%>&nbsp; 
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


<dhv:include name="organization.addresses" none="true">
<%

int numLocali=0;
Iterator iaddress2 = OrgDetails.getAddressList().iterator();
while (iaddress2.hasNext()) {
    OrganizationAddress thisAddress = (OrganizationAddress)iaddress2.next();
    if(thisAddress.getType()==6){
    	numLocali++;
    }
    
}

%>
<%




int cont=0;
  Iterator iaddress = OrgDetails.getAddressList().iterator();
  Object address[] = null;
  int i = 0;
  int locali=0;
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
	
	  <dhv:evaluate if="<%= thisAddress.getType() == 6 %>">
	   
	 
	 
	
	     <% if(cont==0 ){
	    	
	     %>
	  <dhv:evaluate if="<%= thisAddress.getType() == 6 &&  !thisAddress.getCity().equals("")%>">
	  <%cont++; %>
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Locale funzionalmente collegato</dhv:label></strong>
	  </dhv:evaluate>  
	<%}else{
	if( thisAddress.getType() == 6 &&  !thisAddress.getCity().equals("")){
		cont++;
		 locali++;
	}
	}	
		%>
	  </dhv:evaluate>  
	  
	  
	  <dhv:evaluate if="<%= ((thisAddress.getType() == 7) )%>">
	  <dhv:evaluate if="<%= (thisAddress.getStreetAddressLine1()!="") %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede attivita mobile</dhv:label></strong>
	  </dhv:evaluate> 
	  </dhv:evaluate>
	   <%-- %> <strong><dhv:label name="requestor.requestor_add.Addressess"><%= toHtml(thisAddress.getTypeName()) %></dhv:label></strong>--%>
	  </th>
  </tr>
  
   <dhv:evaluate if="<%= ((thisAddress.getType() == 7) && (thisAddress.getStreetAddressLine1()!=""))%>">
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
  	<%
 int tipolocale=-1;
 if(cont==1){
	 tipolocale=OrgDetails.getTipoLocale();
	
	session.setAttribute("addressid",thisAddress.getId());
	
 }
 if(cont==2){
	 tipolocale=OrgDetails.getTipoLocale2();
		
		session.setAttribute("addressid2",thisAddress.getId());
		
 }
 if(cont==3){
	 tipolocale=OrgDetails.getTipoLocale3();
		
		session.setAttribute("addressid3",thisAddress.getId());
		
 }
 
 %>
  	
 
  <dhv:evaluate if="<%=(thisAddress.getType()== 6)%>">
  <%numLocali--; %>
  <dhv:evaluate if="<%= OrgDetails.getTipoLocale()!= -1 %>">
    	<tr class="containerBody">
			<td nowrap class="formLabel">
      			<dhv:label name="">Tipo Locale</dhv:label>
			</td>
			<td>
         			<%
         		
         		
         		out.print(TipoLocale.getSelectedValue( tipolocale ));
         		
         		
         		%>
			</td>
		</tr>
  	</dhv:evaluate>  
  </dhv:evaluate>
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
      <%if(numLocali==0){ %>
    </tr>
    
    
    </table><br>
<%}
    
	 }} else {
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
 


<%if(OrgDetails.getTipoDest().equalsIgnoreCase("distributori")){ %>

<p>
			Utilizzare le caselle vuote sopra l'intestazione per filtrare per anno, richiedente, impresa, capo e/o rapporto
		</p>
		<%
		request.setAttribute("Organization",OrgDetails);
		%>
		
		
		<%
		if(request.getAttribute("errore")!=null){
			
			%>
			<font color="red">
			<%=request.getAttribute("errore")+" Utilizzare il seguente formato (gg/mm/yyyy) <br>" %>
			
			</font>
			
			<% 
			
		}
		
		%>
		
		
		
		
	<%
	String aggiunto="false";
	if(request.getAttribute("aggiunto")!=null)
	 aggiunto=(String)request.getAttribute("aggiunto");
	
	%>

		
	 <%
  Integer numeroDistributori =(Integer) request.getAttribute("numeroDistributori");
  
  %>
		<a href="javascript:chiamaAction('DistributoriListImprese.do?command=Add&orgId=<%=OrgDetails.getOrgId() %>&maxRows=15&15_sw_=true&15_tr_=true&15_p_=<%=numeroDistributori %>&15_mr_=15&scroll=')">Inserisci Distributore se non importato da file</a>
		
		<form name="aiequidiForm" action="DistributoriListImprese.do?orgId=<%=OrgDetails.getOrgId() %>&aggiunto=<%=aggiunto %>">
		<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId() %>">
	     
	     <%if(request.getAttribute("add")!=null) {%>
	     <input type="hidden" name="add" value="add">
	     
	     <%} %>
	     
	     
	      <jmesa:htmlColumn property="chkbox" title=" " worksheetEditor="org.jmesa.worksheet.editor.CheckboxWorksheetEditor" filterable="false" sortable="false"/>
	     
	     <%if(request.getAttribute( "tabella" )!=null) {%>
	       <%=request.getAttribute( "tabella" )%>
	       <%} %>
	    <jmesa:tableFacade editable="true" >   <jmesa:htmlRow uniqueProperty="matricola">   </jmesa:htmlRow></jmesa:tableFacade>
	    
	    <script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
            function onInvokeExportAction(id) {
                var parameterString = $.jmesa.createParameterStringForLimit(id);
				
                 location.href = 'DistributoriListImprese.do?&' + parameterString;
            }
    </script>
	    </form>
	   


<%} %>
<br>
 <%if(hasText(OrgDetails.getNotes())){ %>
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


<% } %>

<dhv:permission name="accounts-accounts-edit,accounts-accounts-delete"><br></dhv:permission>
<dhv:evaluate if="<%=OrgDetails.isTrashed()%>">
    <dhv:permission name="accounts-accounts-edit">
      <input type="button" value="Ripristina"	onClick="javascript:window.location.href='Accounts.do?command=Restore&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
</dhv:evaluate>
<%
	
	if((OrgDetails.getSource()!=1) && (OrgDetails.getCessato()!=1)){
	%>
<%
	

	%>
  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="accounts-accounts-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='Accounts.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="accounts-accounts-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='Accounts.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="accounts-accounts-delete"><input type="button" value="<dhv:label name="accounts.accounts_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('Accounts.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Accounts.do?command=Search', 'Delete_account','320','200','yes','no');"></dhv:permission>
  </dhv:evaluate>

  
  <%}else if((OrgDetails.getSource()==1)&& OrgDetails.getDateF() != null && (OrgDetails.getDateF().after(d))){ %>
  

  <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
    <dhv:permission name="accounts-accounts-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='Accounts.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
  </dhv:evaluate>
  
  <dhv:evaluate if="<%=!(OrgDetails.getEnabled())%>">
    <dhv:permission name="accounts-accounts-edit">
      <input type="button" value="<dhv:label name="global.button.Enable">Enable</dhv:label>" 	onClick="javascript:window.location.href='Accounts.do?command=Enable&orgId=<%= OrgDetails.getOrgId() %>';">
    </dhv:permission>
  </dhv:evaluate>
  <dhv:evaluate if='<%= (request.getParameter("actionplan") == null) %>'>
    <dhv:permission name="accounts-accounts-delete"><input type="button" value="<dhv:label name="accounts.accounts_details.DeleteAccount">Delete Account</dhv:label>" onClick="javascript:popURLReturn('Accounts.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Accounts.do?command=Search', 'Delete_account','320','200','yes','no');">
    </dhv:permission>
  </dhv:evaluate>
  <%}else
  {
	  /*POSSIBILITA DI MODIFICARE QUALORA MANCHI LA DATA FINE CARATTERE*/
	  if((OrgDetails.getSource()==1)&& OrgDetails.getDateF()== null)
	  {
		  %>
		   <dhv:evaluate if="<%=(OrgDetails.getEnabled())%>">
	    <dhv:permission name="accounts-accounts-edit"><input type="button"  value="Modifica"	onClick="javascript:window.location.href='Accounts.do?command=Modify&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|actionplan") %>';"></dhv:permission>
	  </dhv:evaluate>
		  <%
	   }
	  
  }
%>
  	<%if(OrgDetails.getTipoDest()!=null || !OrgDetails.getTipoDest().equalsIgnoreCase("null")){
  	
  
  	%>
  <dhv:evaluate if='<%= (OrgDetails.getTipoDest().equalsIgnoreCase("distributori")) %>'>
 
  
    <input type="button" value="<dhv:label name="">Importa Distributori da File </dhv:label>" onClick="javascript:window.location.href='Accounts.do?command=InsertDistributori&id=<%=OrgDetails.getId()%>'">
   
  </dhv:evaluate>
  <%} 

  %>
 
 
  
</dhv:container>

<%= addHiddenParams(request, "popup|popupType|actionId") %>
<% if (request.getParameter("return") != null) { %>
<input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>

<script>

function generaNumero(asl,stageId,codIstat,tipoDest,cityLegale,cityMobile,cityoperativa)
{
	flag = false;
	msg = 'Attenzione controllare di aver valorizzato i seguenti Campi : \n';

	if(asl == '' || asl == '-1')
	{
		msg = msg + ' - Asl di appartenenza \n';
		flag = true;
	}
	if(stageId == '' || stageId == '-1' || stageId == '0') 
	{
		msg = msg + ' - Servizio Competente \n';
		flag = true;
	}
	if(codIstat == '' )
	{
		msg = msg + ' - Codice Istat Principale \n';
		flag = true;
	}
	if(tipoDest == '' )
	{
		msg = msg + ' - Tipo di Attivita \n';
		flag = true;
	}

	if(tipoDest == 'Es. Commerciale' && cityoperativa == '' )
	{
		msg = msg + ' - Comune sede Operativa  \n';
		flag = true;
	}
	else{

	if(tipoDest == 'Autoveicolo' && cityMobile == '' )
	{
		msg = msg + ' - Comune sede Mobile \n';
		flag = true;
	}
	else{

	if(tipoDest == 'Distributori' )
	{
		msg = msg + ' - Comune sede Legale \n';
		flag = true;
	}
	}	
	}	

	
	if(flag == false)
	{
	javascript:window.location.href='Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>&generaCodice=1';
	}
	else{
	alert(msg)
	}
}

document.body.scrollTop=<%= request.getAttribute("scroll") %>;


function openPopupLarge(url){
	
	  var res;
    var result;
    	  window.open(url,'popupSelect',
          'height=600px,width=1000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}	
</script>



</body>