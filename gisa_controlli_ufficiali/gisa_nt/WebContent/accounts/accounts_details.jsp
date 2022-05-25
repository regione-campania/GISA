<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!-- script>
alert('Gli stabilimenti 852 sono momentaneamente non disponibili causa allineamento dati in corso.');
loadModalWindow();
window.location.href="OpuStab.do?command=SearchForm";
</script-->

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
  - DAMAGES RELATING TO THE SOFTWARE
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
<%@page import="java.text.SimpleDateFormat,java.util.Date" %>
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
<jsp:useBean id="tipoTrasporto" class="java.lang.String" scope="request"/>
<jsp:useBean id="is_farmacia" class="java.lang.String" scope="request"/>

<%@ include file="../initPage.jsp" %>

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
		if(confirm("Attenzione è necessario verificare se l'attività in corso di import risulta essere \"trasporto conto terzi\".\n\n Nel caso di \"trasporto contro proprio e venditori ambulanti\" l'operazione di import non deve essere eseguita.\n\n Premere \"OK\" per \"trasporto conto terzi\" - \"ANNULLA\" per \"contro proprio\""))
		{
			location.href='OpuStab.do?command=CaricaImport&orgId='+orgId ;
		}
		
		}else
		location.href='OpuStab.do?command=CaricaImport&orgId='+orgId ;
	
	}
	
	
function importaInNuovaAnagrafica(tipoAttivita,orgId, tipoTrasporto,is_farmacia)
{
	if((tipoAttivita=='Autoveicolo' && tipoTrasporto=="1") || is_farmacia){
		alert("Attenzione. L'import non è possibile per i tipi di attività Trasporto conto proprio o Farmacie.")
		return false;
	}
	else if(tipoAttivita=='Autoveicolo' && tipoTrasporto=="3"){
		location.href='OpuStab.do?command=CaricaImport&orgId='+orgId ;
	}
	else
		location.href='OpuStab.do?command=CaricaImport&orgId='+orgId ;
	}
	
	
function completaSchedaAnagrafica(tipoAttivita,orgId, tipoTrasporto,is_farmacia)
{
	
	var url = 'GestioneAnagraficaAction.do?command=Import&altId=<%=OrgDetails.getOrgId()%>';
	
	if((tipoAttivita=='Autoveicolo' && tipoTrasporto=="1") || is_farmacia){
		alert("Attenzione. L'import non è possibile per i tipi di attività Trasporto conto proprio o Farmacie.")
		return false;
	}
	else if(tipoAttivita=='Autoveicolo' && tipoTrasporto=="3"){
		location.href=url;
	}
	else
		location.href=url;
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






<%-- <jsp:include page="../dialog_convergenza_cu_elimina_anagrafica.jsp"/> --%>



<% 


if(containerR != null && containerR.equals("archiviati") ){
out.println("<center><b><font size=\"5px\" color=\"red\">STABILIMENTO ARCHIVIATO</font></b></center><br/><br/>");
}

String BloccaImport = "" ;
if(request.getAttribute("BloccaImport")!=null)
	BloccaImport= (String) request.getAttribute("BloccaImport")  ;
out.print(BloccaImport);
if(containerR != null && containerR.equals("accounts") ){
	
	if(  (OrgDetails.getTipoDest()!= null && OrgDetails.getTipoDest().equalsIgnoreCase("autoveicolo") 
			&& linee_attivita_secondarie !=null && (linee_attivita_secondarie.size())>1) || BloccaImport.equalsIgnoreCase("si"))
		
	{
		
		if( OrgDetails.getCessato() == 1|| (OrgDetails.getCessato() == 1 && OrgDetails.getSource() == 1) || 
				 
				(OrgDetails.getSource() == 1 && OrgDetails.getDateF()!= null && OrgDetails.getDateF().before(d))
			){}else{
		%>
		
	<br><br>
	
		 <dhv:permission name="osa-cessazione-pregressa-view">
	
	
	 <div class="ovale"align="center">
	 <br>
	 <p><center><b>Per cessare senza importare premere su 'CESSAZIONE OSA'</b></center></p>
	 <p>
	 <center>
	 <input type="button" value="CESSAZIONE OSA"  onclick="openPopUpCessazioneAttivita();" width="120px;" >
	 </center></p>
	<br><br>
	 </div>
	 	</dhv:permission>
	 
	 <jsp:include page="../dialog_cessazione_attivita.jsp">
	 <jsp:param value="<%=OrgDetails.getOrgId() %>" name="idAnagrafica"/>
	 <jsp:param value="Accounts.do?command=CessazioneAttivita" name="urlSubmitCessazione"/>
	 <jsp:param value="<%=OrgDetails.getDataInizio() != null ? new SimpleDateFormat("ss:mm:hh dd/MM/yyyy").format(new Date(OrgDetails.getDataInizio().getTime())) : "" %>" name="data_inizio" />
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
 
 <br> <br>
<%--  <dhv:permission name="osa-cessazione-pregressa-view">
	
	
	 <div class="ovale">
	 <br>
	 <p><center><b>Per cessare senza importare premere su 'CESSAZIONE OSA'</b></center></p>
	 <br><br>
	 <center>
	 <input type="button" value="CESSAZIONE OSA" onclick="openPopUpCessazioneAttivita();" width="120px;">
	 </center>
	<br><br>
	 </div>
	 	</dhv:permission>
	 
	 <jsp:include page="../dialog_cessazione_attivita.jsp">
	 <jsp:param value="<%=OrgDetails.getOrgId() %>" name="idAnagrafica"/>
	 <jsp:param value="Accounts.do?command=CessazioneAttivita" name="urlSubmitCessazione"/>
	 </jsp:include>
	  <br> <br> --%>
	  
	  
	  
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
		 <jsp:param value="Accounts.do?command=CessazioneAttivita" name="urlSubmitCessazione"/>
		 <jsp:param value="<%=OrgDetails.getDataInizio() != null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date(OrgDetails.getDataInizio().getTime())) : "" %>" name="data_inizio" />
	</jsp:include>
	
<!------------------------------------------------------------------------------------------------> 
	  
	  
	  <dhv:permission name="opu-import-add">
	  
	  <%
	  	if(User.getRoleId()!=Role.HD_1LIVELLO && User.getRoleId()!=Role.HD_2LIVELLO)
	  	{
	  
	  %>
	  <div align="center">
			<input type="button"  class="yellowBigButton"
				value="Importa in Anagrafica stabilimenti"
			    onClick="importaInNuovaAnagrafica('<%=OrgDetails.getTipoDest()%>',<%=OrgDetails.getOrgId()%>, '<%=tipoTrasporto%>',<%=is_farmacia%>)">
	  </div>
	  
	  
	  <%
	  	}
	  	else
	  	{
	  		%>
	  			
	  
	  
	  <!-- center><p style="color:red; font-weight:bold;" >Per usare la nuova funzione di import cliccare su 'Completa scheda anagrafica NEW!!!'</p></center -->
	  <div align="center">
					<input type="button"  class="greenBigButton" style="color:black !important" value="Completa scheda anagrafica"
			    		onClick="completaSchedaAnagrafica('<%=OrgDetails.getTipoDest()%>',<%=OrgDetails.getOrgId()%>, '<%=tipoTrasporto%>',<%=is_farmacia%>)">
	  </div>
	  
	  <!-- div align="center">
					<input type="button"  value="Importa in Anagrafica stabilimenti (VECCHIA VERSIONE)" style="background-color: grey !important"
			    		onClick="importaInNuovaAnagrafica('<%=OrgDetails.getTipoDest()%>',<%=OrgDetails.getOrgId()%>, '<%=tipoTrasporto%>',<%=is_farmacia%>)">
	  </div -->
	  		
	  		<%
	  		
	  	}
	  %>
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
<%-- <center><font color="red"><b><%="Lo stabilimento ha linee non aggiornate. " %></b></font></center> --%>
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
</div>

<dhv:permission name="accounts-accounts-delete">
	<input type="button" value="Elimina" onClick="javascript:popURLReturn('Accounts.do?command=ConfirmDelete&id=<%=OrgDetails.getId()%>&popup=true','Accounts.do?command=Search', 'Delete_account','320','200','yes','no');">
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

<% if(OrgDetails.getTipoDest().equals("Es. Commerciale") || OrgDetails.getTipoDest().equals("Distributori")){%> 
 <jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
     <jsp:param name="tipo_dettaglio" value="1" />
</jsp:include>
<%}  else {%>
	 <jsp:include page="../schede_centralizzate/iframe.jsp">
	    <jsp:param name="objectId" value="<%=OrgDetails.getOrgId() %>" />
	     <jsp:param name="tipo_dettaglio" value="12" />
	</jsp:include>
<% } %>



<script>
function mostraNascondiLineeStoriche(bottone){
	var val = bottone.value;
	
	if (val=='+'){
		document.getElementById("tableLineeStoriche").style.display="table-row";
		bottone.value = '-';
	}
	else {
		document.getElementById("tableLineeStoriche").style.display="none";
		bottone.value = '+';
	}
}
</script>

<!-- RAMMAGGIO PER FAR USCIRE LE LINEE VECCHIE NASCOSTE CON LO STESSO STILE DELLA SCHEDA-->
<style>.dettaglioTabella852 table{ font-family: Arial, Helvetica, sans-serif;	font-size:16px;	color:#000000;} .dettaglioTabella852 td{	font-family: Arial, Helvetica, sans-serif;	font-size:12px;	color:#000000;} .grey852 {	 background-color:#EBEBEB;	 border: 1px solid black;	 }.blue852 {	 background-color:#e6f3ff;	  border: 1px solid black;	font-size:12px;  text-transform:uppercase;	}.layout852 {	 border: 1px solid black;	 text-transform:uppercase;	} </style>
<table class="dettaglioTabella852" width="100%">
<tr><th class="blue852"> <div align="left"><input type="button" id="buttonLineeStoriche" value="+" onClick="mostraNascondiLineeStoriche(this)"/> DESCRIZIONE STORICA DELLE LINEE DI ATTIVITA' (MASTER LIST , ATECO, ECC.)</div> </th></tr>
<tr><td class="layout852">
<table class="dettaglioTabella852" width="100%" id="tableLineeStoriche" style="display:none">
<% ArrayList<String> lineeStoriche = (ArrayList<String>) request.getAttribute("lineeStoriche");
for (int i = 0; i<lineeStoriche.size(); i++){ %>
<tr><td><%=lineeStoriche.get(i) %></td></tr>
<% } %>
</table>
</td></tr>
</table>
<!-- FINE RAMMAGGIO PER FAR USCIRE LE LINEE VECCHIE NASCOSTE CON LO STESSO STILE DELLA SCHEDA-->

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