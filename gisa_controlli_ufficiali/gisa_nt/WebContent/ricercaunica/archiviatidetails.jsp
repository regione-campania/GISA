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
<%@page import="java.sql.*"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>		
<!-- 		<script type="text/javascript" src="javascript/jquery-1.3.min.js"></script> -->
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.ricercaunica.base.RicercaOpu" scope="request"/>
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


<% String containerR  = ""; %>
<% if(container == null || container.equals("") ) { containerR = "accounts";} else { containerR = container; } %>

<dhv:evaluate if="<%= !isPopup(request) %>">
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
<%-- End Trails --%>

</dhv:evaluate>

<% String param1 = "orgId=" + OrgDetails.getRiferimentoId(); %>

<dhv:container name="<%=containerR%>" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="">

<% if( (OrgDetails.getStato()!=null && !OrgDetails.getStato().equals("Cessato")) || (OrgDetails.getCessato() > 0 && OrgDetails.getCessato() != 1 )    ){ %>


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
	 <jsp:param value="<%=OrgDetails.getRiferimentoId() %>" name="idAnagrafica"/>
	 <jsp:param value="RicercaArchiviati.do?command=CessazioneAttivita" name="urlSubmitCessazione"/>
	 <jsp:param value="" name="data_inizio" />
	 </jsp:include>




<dhv:permission name="opu-import-archiviati-view">
<center><font color="red"><b><%="Lo stabilimento ha linee non aggiornate. " %></b></font></center>
  <div align="center">
 	 <br/><br/>
 		<%--<input type="button" class="yellowBigButton" value="AGGIORNA LINEE DI ATTIVITA' PREGRESSE DA MASTERLIST" 
 		onClick="openPopupLarge('Accounts.do?command=PrepareUpdateLineePregresse&orgId=<%=OrgDetails.getOrgId() %>&lda_prin=<%=linea_attivita_principale.getId() %>')"
 		--%>
 	<%-- onClick="loadModalWindow();window.location.href='OpuStab.do?command=PrepareUpdateLineePregresse&stabId=<%=StabilimentoDettaglio.getIdStabilimento() %>'"--%>
 	<input type="button" class="yellowBigButton"s
				value="Importa in Anagrafica stabilimenti"
			    onClick="javascript:window.location.href='OpuStab.do?command=CaricaImport&orgId=<%=OrgDetails.getRiferimentoId()%>'">
 <br/><br/>	
 	</div>
</dhv:permission>
<% } %>

<br>
<br>
<%-- <% if(OrgDetails.getTipoDest().equals("Es. Commerciale") || OrgDetails.getTipoDest().equals("Distributori")){--%> 
 <jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%= OrgDetails.getRiferimentoId() > 0 ? OrgDetails.getRiferimentoId() : request.getParameter("orgId") %>" />
    <jsp:param name="objectIdName" value="<%=OrgDetails.getRiferimentoIdNomeCol()!=null ? OrgDetails.getRiferimentoIdNomeCol() : "orgId" %>" />
     <jsp:param name="tipo_dettaglio" value="30" />
</jsp:include>

  
</dhv:container>




</body>