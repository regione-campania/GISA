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
  - Version: $Id: accounts_list.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.allevamenti.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.allevamenti.base.OrganizationList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  
</script>

<script> function setCodiceAzienda(code){
	if (window.opener!=null){
		window.opener.setCodiceAzienda(code);
		 window.close();
	}
}</script>

<head>
	<script>
	function confermaEliminazioneAssociazione(idOsm, idAssociazione) {
		var answer = confirm("Vuoi eliminare l'associazione esistente?\n\n")
		if (answer) {
			location.href="EliminaAssociazioneConOsm.do?command=Delete&orgId=" + idOsm + "&idAssociazione=" + idAssociazione;
		}
	}
	</script>
</head>


<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Allevamenti.do?command=Dashboard&popup=true">Cambia filtri ricerca</a>  
</td>
</tr>
</table>
<%-- End Trails --%>


<%
// nel caso in cui arrivo nella pagina cliccando su "Azienda Zootecnica"
// dalla scheda di un OSM che ha allevamenti associati
// visualizzo la lista di allevamenti associati e il link per l'eventuale cancellazione
String idAssociazione = (String)request.getAttribute("idAssociazione");
if (idAssociazione != null) {
	org.aspcfs.modules.osmregistrati.base.Organization osm = (org.aspcfs.modules.osmregistrati.base.Organization)request.getAttribute("OrgDetails");
	int idOsm = osm.getOrgId();
	%>
	<div align="center">
		<a style="BACKGROUND-COLOR:#BDCFFF; font-weight:bold; font-size:12;" href="javascript:confermaEliminazioneAssociazione(<%=idOsm%>,<%=idAssociazione%>)">ELIMINA L'ASSOCIAZIONE CON L'OSM "<%=osm.getName()%>"</a>
	</div>
	<br/>
	<%
} 
%>

<%
// nel caso in cui arrivo nella pagina cliccando su "Azienda Zootecnica"
// dalla scheda di un OSM che non ha allevamenti associati
// visualizzo l'operazione in corso
Boolean ricercaAllevamentiAssociabiliAttribute = (Boolean)request.getAttribute("ricercaAllevamentiAssociabiliAttribute");
if (ricercaAllevamentiAssociabiliAttribute != null && ricercaAllevamentiAssociabiliAttribute == true) {
	org.aspcfs.modules.osmregistrati.base.Organization osmACuiAssociareAllevamento = (org.aspcfs.modules.osmregistrati.base.Organization)session.getAttribute("osmACuiAssociareAllevamento");
	%>		
	<div align="center"; style="BACKGROUND-COLOR:#BDCFFF; font-weight:bold; font-size:12;">
		RICERCA ALLEVAMENTO DA ASSOCIARE ALL'OSM "<%=osmACuiAssociareAllevamento.getName()%>"
	</div>
	<br/>
	<%
} 
%>

<% int columnCount = 0; %>
<!--<a href="Allevamenti.do?command=ToExportExcelOperatori">Esporta in excel</a>-->


<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
   
    
    
<%--    <dhv:include name="organization.list.siteId" none="true"> --%>
<zeroio:debug value='<%="JSP::accounts_list.jsp "+ SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")+" == "+(String.valueOf(Constants.INVALID_SITE)) %>'/>
     
      <th nowrap <% ++columnCount; %>>
          <strong>SELEZIONA</strong>
		</th>
		
      <th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="organization.nameDenominaz">Denominazione</dhv:label></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
<%--    </dhv:include> --%> 
        <th nowrap <% ++columnCount; %>>
          <strong>Codice Azienda</strong>
		</th>
		<th nowrap <% ++columnCount; %>>
          <strong>Id Allevamento</strong>
		</th>
		<th nowrap <% ++columnCount; %>>
          <strong>Tipologia Struttura</strong>
		</th>
		
		<th nowrap <% ++columnCount; %>>
          <strong>Stato</strong>
		</th>
	   
  </tr>
<%

	// associazione OSM - azienda zootecnica
	String ricercaAllevamentiAssociabiliParameter = null;
	if (ricercaAllevamentiAssociabiliAttribute != null && ricercaAllevamentiAssociabiliAttribute == true) {
		ricercaAllevamentiAssociabiliParameter = "1";
	}

	Iterator j = OrgList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Organization thisOrg = (Organization)j.next();
%>
  <tr class="row<%= rowid %>">
    
	 <td>
      <a href="#" onClick="setCodiceAzienda('<%=thisOrg.getAccountNumber()%>')">Seleziona</a>
	</td>
		
      <td>
      <%= toHtml(thisOrg.getName()) %>
	</td>
	<td nowrap>
      <%= toHtml(thisOrg.getAccountNumber()) %>
	</td>
     <td nowrap>
      <%= toHtml(thisOrg.getId_allevamento()) %>
	</td> 
		
	<td nowrap>
      <%= toHtml(thisOrg.getTipologiaStrutt()) %>
	</td>
	
	<td nowrap>
      <%if (thisOrg.getDate2()!= null){ 
    	  out.print("<font color ='red'>Cessato</font>");
      } else
      {
    	  out.print("<font color ='green'>Aperto</font>");
      }
      %>
	</td>
   	
  </tr>
<%}%>
<%} %>
 
</table>
<br />

<%if (ricercaAllevamentiAssociabiliAttribute == null) {%>
	<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>
<%}%>

