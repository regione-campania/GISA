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
<%@ page import="java.util.*,org.aspcfs.modules.laboratorihaccp.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.laboratorihaccp.base.OrganizationList" scope="request"/>
<jsp:useBean id="ProveList" class="org.aspcfs.modules.laboratorihaccp.base.ElencoProve" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="Ente" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="MatriciHaccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DenominazioniHaccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="laboratori_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">

  <%-- Preload image rollovers for drop-down menu --%>
  
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="LaboratoriHACCP.do?command=SearchForm"><dhv:label name="">Laboratori HACCP</dhv:label></a> > 
<dhv:label name="">Risultato Ricerca</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="accounts.manage">Select an account to manage.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>
<dhv:permission name="laboratori-laboratori-add"><a href="LaboratoriHACCP.do?command=Add"><dhv:label name="">Aggiungi Laboratorio HACCP</dhv:label></a></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="SearchOrgListInfo"/></center></dhv:include>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="9" <% ++columnCount; %>>
      &nbsp;
    </th>
    <th nowrap <% ++columnCount; %>>
      <%--strong><a href="LaboratoriHACCP.do?command=Search&column=o.ragione_sociale"--%><dhv:label name="">Denominazione</dhv:label><%--/a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.ragione_sociale") --%>
    </th>
        <%--    <dhv:include name="organization.list.siteId" none="true"> --%>
<zeroio:debug value='<%="JSP::accounts_list.jsp "+ SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")+" == "+(String.valueOf(Constants.INVALID_SITE)) %>'/>
      <%--dhv:evaluate if='<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE)) %>'--%>
        <th <% ++columnCount; %>>
          <strong><dhv:label name="">Numero di Iscrizione</dhv:label></strong>
        </th>
               
        <th <% ++columnCount; %>>
          <strong><dhv:label name="">Indirizzi</dhv:label></strong>
        </th>
        <th <% ++columnCount; %>>
          <strong><dhv:label name="">Direttore responsabile Laboratori</dhv:label></strong>
        </th>
        <th <% ++columnCount; %>>
          <strong><dhv:label name=""><center>Elenco Prove</center></dhv:label></strong>
        </th>
       
  </tr>
<%
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
    <td width="8" valign="center" nowrap>
      <% int status = -1;%>
      <%--dhv:permission name="laboratori-laboratori-edit"><% status = thisOrg.getEnabled() ? 1 : 0; %></dhv:permission>--%>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= i %>','menuAccount', '<%= thisOrg.getOrgId() %>', '<%= status %>', '<%=thisOrg.isTrashed() %>');"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuAccount');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
	<td>
      <a href="LaboratoriHACCP.do?command=Details&tipoRicerca=prova&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a>
	</td>
      
	
	<td nowrap>
      <%= toHtml(thisOrg.getAccountNumber()) %>&nbsp;
	</td>
	<% Iterator iaddress = thisOrg.getAddressList().iterator();
       Object address[] = null;
       int x = 0;
       if (iaddress.hasNext()) {%>
       <td>
    	   <%
          while (iaddress.hasNext()) {
        	  
        	  org.aspcfs.modules.laboratorihaccp.base.OrganizationAddress thisAddress = (org.aspcfs.modules.laboratorihaccp.base.OrganizationAddress)iaddress.next(); 
          if(thisAddress.getType()==1){ 
         %>
     <b>Sede Legale:&nbsp;</b> 
      <%= toHtml((thisAddress.getStreetAddressLine1()!= null && (thisAddress.getCity()!=null)&&(thisAddress.getType()==1)) ? (thisAddress.getStreetAddressLine1()+" "+thisAddress.getCity()) : ("")) %>&nbsp;<br/><br/>
	<%}
	if(thisAddress.getType()==5){ %>
	<b>Sede Operativa:&nbsp;</b> 
      <%= toHtml((thisAddress.getStreetAddressLine1()!= null && (thisAddress.getCity()!=null)&&(thisAddress.getType()==5)) ? (thisAddress.getStreetAddressLine1()+" "+thisAddress.getCity()) : ("")) %>&nbsp;
		
	<%}}%>
	</td>
	<%}else{ %>
	<td nowrap>Nessun Indirizzo.</td>
	<%} %>
	<td nowrap>
      <%= toHtml(thisOrg.getCognomeRappresentante()) %>
	</td>
  <% 
 		 Iterator prove = thisOrg.getProveList().iterator();
		 
 		 if (prove.hasNext()) { %>
 			 <td nowrap>
 			 <table>
		    <tr>
		         <th class="lab">Materiale / Prodotto / Matrice
		         </th>
		         <th class="lab">Denominazione della Prova
		         </th>
		         <th class="lab">Accreditamento
		         </th>
		         <th class="lab">Ente di Accreditamento
         	     </th>
           </tr>
 	<%
	  		while (prove.hasNext()) {
	  			
     			Prova thisProva = (Prova)prove.next(); 
     	          
         %>
         
           <tr>
    <!-- b>Materiale / Prodotto / Matrice:&nbsp;</b-->
      <td><%= MatriciHaccp.getSelectedValue( thisProva.getCodiceMatrice()) %><br/></td>
	<!-- b>Denominazione della Prova:&nbsp;</b-->
     <td> <%= DenominazioniHaccp.getSelectedValue( thisProva.getCodiceDenominazione()) %><br/></td>
	<!-- >b>Accreditamento:&nbsp;</b-->
	  <td><%= toHtml(((thisProva.getAccreditata()==true)?("Accreditata"):("In Accreditamento")))%><br/></td>
	<!-- b>Ente di Accreditamento:&nbsp;</b>-->
	  <td><%= Ente.getSelectedValue( thisProva.getCodiceEnte()) %><br/></td>
	  </tr>

	<%}%>
		</table>
	</td>
	<%}else{ %>
	<td nowrap>Nessuna Prova.</td>
	<%} %>
  </tr>
  
  <input type="hidden" name="source" value="searchForm">
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="">Nessun Laboratorio HACCP trovato con i parametri di ricerca specificati.</dhv:label><br />
      <a href="LaboratoriHACCP.do?command=SearchForm"><dhv:label name="">Modifica Ricerca</dhv:label></a>.
    </td>
  </tr>
<%}%>

</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>

