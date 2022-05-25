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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.modules.base.*,org.aspcfs.modules.global_search.base.* " %>

<%-- <jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/> --%>


<%@page import="java.util.Date"%><jsp:useBean id="OrgList" class="org.aspcfs.modules.global_search.base.OrganizationListViewMinimale" scope="session"/>

<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditList" class="org.aspcfs.modules.audit.base.AuditList" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">

  <%-- Preload image rollovers for drop-down menu --%>
  
</script>

<script> function esportaExcel(){
window.location.href="GlobalSearch.do?command=GlobalSearch.do?command=StampaExportExcelCampioni";
}
</script>

<%-- Trails --%>
<table class="trails" cellspacing="0" >
<tr>
<td>
<a href="GlobalSearch.do"><dhv:label name=" ">Campioni</dhv:label></a> > 
<dhv:label name=" ">Risultati Ricerca Campioni</dhv:label>
</td>
</tr>
</table>
<%if( OrgList.size() > 0 ){ %>
<a href="#" onClick="esportaExcel()">Esporta in excel</a>
<%} %>
<%-- End Trails --%>
<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="accounts.manage">Select an account to manage.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<% int columnCount = 0; %>

<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />

<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs/ext-all.js"></script>
<script type="text/javascript" src="extjs/examples/ux/TableGrid.js"></script>
<script type="text/javascript" >
Ext.onReady(function(){
        
        // create the grid
        var grid = new Ext.ux.grid.TableGrid("tabella-lista-attivita", {
            stripeRows: true // stripe alternate rows
           
        });
        grid.render();
});
</script>
<table cellpadding="8" id="tabella-lista-attivita" cellspacing="0" width="100%" border="1" >
	<thead>
  	<tr>
    
    	<th  <% ++columnCount; %>>
          	<strong>Identificativo Campione</strong>
		</th>
		
    	<th  <% ++columnCount; %>>
          	<strong>Numero Verbale - Barcode</strong>
		</th>
    	 
      	<th  <% ++columnCount; %>>
          	<strong>Motivazione Campione</strong>
		</th>
      	  
      	<th  <% ++columnCount; %>>
          	<strong>Matrice</strong>
		</th>
		  
    	<th <% ++columnCount; %>>
        	<strong><dhv:label name="">Analita</dhv:label></strong>
    	</th>
    
    	<th <% ++columnCount; %>>
        	<strong><dhv:label name="">Esito/Stato</dhv:label></strong>
    	</th>
    
    	<th  <% ++columnCount; %>>
      		<strong><dhv:label name="">ASL</dhv:label></strong>
    	</th>
    
    	<th  <% ++columnCount; %>>
          	<strong>Tipologia Operatore - Ragione Sociale </strong>
		</th>
		
		<th  <% ++columnCount; %>>
          	<strong>Data Prelievo</strong>
		</th>	
      		
  </tr>
  </thead>
  <tbody>
  <tr>
<%
	Iterator j = OrgList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    OrganizationView thisOrg = (OrganizationView)j.next();
%>

	<td >
	<% if(!thisOrg.getTicketId().equals("-1")) { %>
	<% if( (thisOrg.getTipologia_operatore().contains("852"))) { %> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="AccountCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>	
	<% } else if( (thisOrg.getTipologia_operatore().toLowerCase().contains("853"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="StabilimentiCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>	
    <% } else if( (thisOrg.getTipologia_operatore().toLowerCase().contains("mercati ittici"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="StabilimentiCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>	
    <% } else if( (thisOrg.getTipologia_operatore().toLowerCase().equals("osm"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="OsmCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>
	<% }  else if( (thisOrg.getTipologia_operatore().toLowerCase().equals("osm registrato"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="OsmRegistratiCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>	
    <% } else if( (thisOrg.getTipologia_operatore().toLowerCase().contains("soa"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="SoaCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>	
    <% } else if( (thisOrg.getTipologia_operatore().toLowerCase().contains("trasporti animali"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="TrasportiCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>	
    <% } else if( (thisOrg.getTipologia_operatore().toLowerCase().contains("zootec"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="AllevamentiCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>
	 <% } else if( (thisOrg.getTipologia_operatore().toLowerCase().contains("operatore abusivo"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="AbusivismiCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>
	<% } else if( (thisOrg.getTipologia_operatore().toLowerCase().contains("operatore privato"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="OperatoriprivatiCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>
	<% } else if( (thisOrg.getTipologia_operatore().toLowerCase().contains("operatori sperimentazione animale"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="OSAnimaliCampioni.do?command=TicketDetails&id=<%=thisOrg.getIdControllo()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>
	<% } else if( (thisOrg.getTipologia_operatore().toLowerCase().contains("Attivita' mobile fuori ambito Asl"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="OperatoriFuoriRegioneCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>
	<% } else if( (thisOrg.getTipologia_operatore().toLowerCase().contains("molluschi"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="MolluschiBivalviCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>													
	<% }else if( (thisOrg.getTipologia_operatore().toLowerCase().contains("cani padronali"))) {%> 
		<a id="<%= toHtml(thisOrg.getTicketId()) %>" href="CaniPadronaliCampioni.do?command=TicketDetails&id=<%=thisOrg.getTicketId()%>&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getTicketId()) %></a>						
	<%
		} else { %>
			N.D.
		<% } 
	} else { //caso -1
	%>
		-1
	<% } %>
		
    </td>
		
	<td >
       <%= (thisOrg.getNum_verbale() != null && !thisOrg.getNum_verbale().equals("")) ? toHtml(thisOrg.getNum_verbale()) : "N.D." %> 	
    </td>	
    
     <td >
     	 <%= (thisOrg.getMotivazioneCampione() != null && !thisOrg.getMotivazioneCampione().equals("")) ? toHtml(thisOrg.getMotivazioneCampione()) : "N.D." %> 	
    </td>	
    
     <td >
         <%= (thisOrg.getMatrice() != null && !thisOrg.getMatrice().equals("")) ? toHtml(thisOrg.getMatrice()) : "N.D." %> 	
    </td>	
    
    <td >
        <%= (thisOrg.getAnalita() != null && !thisOrg.getAnalita().equals("")) ? toHtml(thisOrg.getAnalita()) : "N.D." %> 	
    </td>
    
    <td>
    	<% if(thisOrg.getEsito() != null && !thisOrg.getEsito().equals("")) {
    		if(thisOrg.getIdControllo().equals("-1")) { %>
        		<%= "PRENOTATO" %>
        	<% } else { %>
        		<%= toHtml(thisOrg.getEsito())  %>
        	<% } }else{ %>
        	 	<%= "N.D"  %>
        	 	<% } %>
    </td>
    
     <td >
        <%= (thisOrg.getAsl() != null && !thisOrg.getAsl().equals("")) ? toHtml(thisOrg.getAsl()) : "N.D." %> 	
    </td>
    
    <td>
    	<%= toHtml(thisOrg.getTipologia_operatore() + " - " +thisOrg.getName()) %>	
	</td>
    
    <td >
    <%if( thisOrg.getDataControllo() != null && !thisOrg.getDataControllo().equals("") ){
    	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
    %>
    	<%= sdf2.format( sdf1.parse( thisOrg.getDataControllo() ) ) %>
    <%} else {%>
   
    	<%= "N.D." %>	
	     <%} %>
    
    </td>
    
   	</tr>
    
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="">Nessun operatore trovato con i parametri di ricerca specificati.</dhv:label><br />
      <a href="GlobalSearch.do?"><dhv:label name="">Modifica la Ricerca</dhv:label></a>.
    </td>
 </tr>
<%}%>
</tbody>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>


