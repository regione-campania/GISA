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
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.troubletickets.base.Ticket"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.ricercaunica.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="StabilimentiList" class="org.aspcfs.modules.ricercaunica.base.RicercaList" scope="request"/>
<jsp:useBean id="SearchOpuListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipoOperatore" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>


<script>
	function intercettaAction(originalAction)
	{
		
		originalAction = originalAction.replace(/&/g,'$');
		//alert(originalAction);
		var link = 'InterfRicercaUnica.do?command=Intercetta&action_originale='+originalAction;
		location.href = link;
	}


</script>


<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="RicercaUnica.do?command=SearchForm"><dhv:label name="">Ricerca in tutto GISA</dhv:label></a> > 
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
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOpuListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
  
   <th <% ++columnCount; %>>
          <strong><dhv:label name="">Tipologia</dhv:label></strong>
        </th>  
        
    <th nowrap <% ++columnCount; %>>
      <dhv:label name="">Denominazione</dhv:label>
      
    </th>  
     <th nowrap <% ++columnCount; %>>
      <dhv:label name="">ASL</dhv:label>
    </th>
    
     <th nowrap <% ++columnCount; %>>
      <dhv:label name="">Indirizzo sede produttiva</dhv:label>
    </th>
    
        <th <% ++columnCount; %>>
          <strong>Partita IVA</strong>
        </th>  
          <th <% ++columnCount; %>>
          <strong><dhv:label name="">Numero Registrazione</dhv:label></strong>
        </th>  
        
         <th nowrap <% ++columnCount; %>>
      <dhv:label name="">Stato</dhv:label>
    </th>
                
  </tr>
<%
	Iterator j = StabilimentiList.iterator();
	if ( j.hasNext()  ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    RicercaOpu thisOrg = (RicercaOpu)j.next();
%>

<%Ticket temp = new Ticket();
temp.setTipologia_operatore(thisOrg.getTipologia());
	%>
	

  <tr class="row<%= rowid %>">
    
    <td valign="top" nowrap>
    <% if (thisOrg.getTipologia()== 999){ %>STABILIMENTI NUOVA GESTIONE <%} else { %> <%=tipoOperatore.getSelectedValue(thisOrg.getTipologia()) %> <%} %>
    </td>
    
	<td>
	
	<a onclick="intercettaAction('<%=temp.getURlDettaglioanagrafica()+".do?command=Details&"+thisOrg.getRiferimentoIdNome()+"="+thisOrg.getRiferimentoId() %>')" id="<%= toHtml(thisOrg.getRagioneSociale().toUpperCase()) %>" href="#"><%= toHtml(thisOrg.getRagioneSociale().toUpperCase()) %></a>
	</td>
	<td valign="top" nowrap><%= toHtml2(thisOrg.getAsl())  %></td>
	
	<td valign="top" nowrap><%= toHtml2(thisOrg.getIndirizzoSedeProduttiva())  %></td>
	
    <td valign="top" nowrap><%=  toHtml2(thisOrg.getPartitaIva()) %></td>	  
    
      <td valign="top" nowrap><%= (thisOrg.getNumeroRegistrazione()!=null && !thisOrg.getNumeroRegistrazione().equals("")) ? toHtml2(thisOrg.getNumeroRegistrazione()) : toHtml2(thisOrg.getNumAut())  %></td>
      
      	<td valign="top" nowrap><%= toHtml2(thisOrg.getStato())  %> <%= toHtml2(thisOrg.getStatoImpresa())  %> </td>
      	  
	
  </tr>
<%}%>

<%} else {%>

  <tr class="containerBody">
    <td colspan="<%= SearchOpuListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="accounts.search.notFound">No accounts found with the specified search parameters.</dhv:label><br />
      <a href="Accounts.do?command=SearchForm"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOpuListInfo" tdClass="row1"/>

