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
<%@ page import="java.util.*,org.aspcfs.modules.osmregistrati.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.osmregistrati.base.OrganizationList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditList" class="org.aspcfs.modules.audit.base.AuditList" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
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
<%-- Trails --%>

<head>
	<script>
	function confermaEliminazioneAssociazione(idAllevamento, idAssociazione) {
		var answer = confirm("Vuoi eliminare l'associazione esistente?\n\n")
		if (answer) {
			location.href="EliminaAssociazioneConAziendaZootecnica.do?command=Delete&orgId=" + idAllevamento + "&idAssociazione=" + idAssociazione;
		}
	}
	</script>
</head>

<%
// nel caso in cui arrivo nella pagina cliccando su "Registrazione 183"
// dalla scheda di un allevamento che ha OSM associati
// visualizzo la lista di OSM associati e il link per l'eventuale cancellazione
String idAssociazione = (String)request.getAttribute("idAssociazione");
if (idAssociazione != null) {
	org.aspcfs.modules.allevamenti.base.Organization allevamento = (org.aspcfs.modules.allevamenti.base.Organization)request.getAttribute("OrgDetails");
	int idAllevamento = allevamento.getOrgId();
	%>
	<div align="center">
		<a style="BACKGROUND-COLOR:#BDCFFF; font-weight:bold; font-size:12;" href="javascript:confermaEliminazioneAssociazione(<%=idAllevamento%>,<%=idAssociazione%>)">ELIMINA L'ASSOCIAZIONE CON L'ALLEVAMENTO "<%=allevamento.getName()%>"</a>
	</div>
	<br/>
	<%
} 
%>

<%
// nel caso in cui arrivo nella pagina cliccando su "Registrazione 183"
// dalla scheda di un allevamento che non ha OSM associati
// visualizzo l'operazione in corso
Boolean ricercaOsmAssociabiliAttribute = (Boolean)request.getAttribute("ricercaOsmAssociabiliAttribute");
if (ricercaOsmAssociabiliAttribute != null && ricercaOsmAssociabiliAttribute == true) {
	org.aspcfs.modules.allevamenti.base.Organization allevamentoACuiAssociareOsm = (org.aspcfs.modules.allevamenti.base.Organization)session.getAttribute("allevamentoACuiAssociareOsm");
	%>		
	<div align="center"; style="BACKGROUND-COLOR:#BDCFFF; font-weight:bold; font-size:12;">
		RICERCA OSM DA ASSOCIARE ALL'ALLEVAMENTO "<%=allevamentoACuiAssociareOsm.getName()%>"
	</div>
	<br/>
	<%
} 
%>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OsmRegistrati.do">OSM Registrati</a> > 
<dhv:label name="stabilimenti.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>

<br/>
<%-- End Trails --%>
<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="stabilimenti.manage">Select an account to manage.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>

<%if (ricercaOsmAssociabiliAttribute == null) {%>
	<dhv:permission name="osmregistrati-osmregistrati-add"><a href="OsmRegistrati.do?command=Add"><dhv:label name="osm.add">Add an Account</dhv:label></a></dhv:permission>
	<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
	<center><dhv:pagedListAlphabeticalLinks object="SearchOrgListInfo"/></center></dhv:include>
	<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<%}%>

<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
   
    <th nowrap <% ++columnCount; %>>
      <strong><a href="OsmRegistrati.do?command=Search&column=o.name"><dhv:label name="organization.name">Account Name</dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
    <th nowrap <% ++columnCount; %>>
          <strong>Numero di Registrazione</strong>
		</th>
    <th nowrap <% ++columnCount; %>>
      <strong>Stato OSM</strong>
    </th>
    
     <th nowrap <% ++columnCount; %>>
      <strong>Categoria Rischio</strong>
    </th>
    
    <%if (!(
			(idAssociazione != null) && (ricercaOsmAssociabiliAttribute == null)
			||
			(idAssociazione == null) && (ricercaOsmAssociabiliAttribute != null)
		)) {%>
	    <th nowrap <% ++columnCount; %>>
	      <strong>Azienda Zootecnica</strong>
	    </th>
    <%}%>
    <%--<th nowrap <% ++columnCount; %>>
      <strong>Attività</strong>
    </th>
    --%>
<%--    <dhv:include name="organization.list.siteId" none="true"> --%>
<zeroio:debug value='<%="JSP::accounts_list.jsp "+ SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")+" == "+(String.valueOf(Constants.INVALID_SITE)) %>'/>
      <dhv:evaluate if='<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE)) %>'>
        <th <% ++columnCount; %>>
          <strong><dhv:label name="stabilimenti.site">Site</dhv:label></strong>
        </th>
      </dhv:evaluate>
<%--    </dhv:include> --%> 
        <th nowrap <% ++columnCount; %>>
          <strong>Partita IVA</strong>
		</th>

  </tr>
<%

	// associazione OSM - azienda zootecnica
	String ricercaOsmAssociabiliParameter = null;
	if (ricercaOsmAssociabiliAttribute != null && ricercaOsmAssociabiliAttribute == true) {
		ricercaOsmAssociabiliParameter = "1";
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
      <a href="OsmRegistrati.do?command=Details&orgId=<%=thisOrg.getOrgId()%>&ricercaOsmAssociabiliParameter=<%=ricercaOsmAssociabiliParameter%>"><%= toHtml(thisOrg.getName()) %></a>
	</td>
	<%if(thisOrg.getAccountNumber()!=null && !thisOrg.getAccountNumber().equals("")) {%>
	<td>
        <%= toHtmlValue(thisOrg.getAccountNumber()) %>
      </td>
      <%}else{%>
		<td>&nbsp;
		</td> 
	<%}%>
	<%if(thisOrg.getStatoLab()>-1) {%>
	<td>
      <%= statoLab.getSelectedValue(thisOrg.getStatoLab()) %>
	</td>
	<%}else{%>
		<td>&nbsp;
		</td> 
	<%}%>
	<td>
	
            <%= ((thisOrg.getCategoriaRischio()>-1)?(thisOrg.getCategoriaRischio()):("3")) %>
    </td>
    
    <%if (!(
			(idAssociazione != null) && (ricercaOsmAssociabiliAttribute == null)
			||
			(idAssociazione == null) && (ricercaOsmAssociabiliAttribute != null)
		)) {%>
	    <td nowrap align="center">
			<%if (thisOrg.getIdAssociazioneAziendaZootecnica() != 0) {%>
				SI
			<%} else {%>
				&nbsp;
			<%}%>
		</td>
	<%}%>
	

		
<%--    <dhv:include name="organization.list.siteId" none="true"> --%>
      <dhv:evaluate if='<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE)) %>'>
        <td valign="top" nowrap><%= SiteIdList.getSelectedValue(thisOrg.getSiteId()) %></td>
      </dhv:evaluate>
<%--    </dhv:include> --%>

      
	<td nowrap>
      <%= toHtml(thisOrg.getPartitaIva()) %>
	</td>
	
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="osm.search.notFound">No accounts found with the specified search parameters.</dhv:label><br />
      <a href="OsmRegistrati.do?command=SearchForm"><dhv:label name="stabilimenti.stabilimenti_list.ModifySearch">Modify Search</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br />


<%if (ricercaOsmAssociabiliAttribute == null) {%>
	<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>
<%}%>

<form method="post" action="./OsmRegistrati.do?command=export">
<%

String source="";
String numaut="";
 source = (String) request.getParameter("source");

if((request.getParameter("searchNumAut"))!=null){
    numaut=request.getParameter("searchNumAut");
    }

%>


<%--input type="submit" value="scarica stabilimenti .xls"--%>
<input type="hidden" value="<%=numaut %>">
</form>

