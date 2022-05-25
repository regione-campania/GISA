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
<%@ page import="java.util.*,org.aspcfs.modules.molluschibivalvi.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.molluschibivalvi.base.OrganizationList" scope="request"/>
<jsp:useBean id="Classificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StatiClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ZoneProduzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="./accounts_list_menu.jsp" %> 
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MolluschiBivalvi.do">Molluschi Bivalvi</a> > 
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<dhv:permission name="molluschi-molluschi-add"><a href="MolluschiBivalvi.do?command=Add">Aggiungi Zona di Produzione</a></dhv:permission>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
  
   <th nowrap  <% ++columnCount; %> >
      <strong>Asl</strong>
    </th>
      <th  <% ++columnCount; %>>
         Cun
    </th>
    <th  <% ++columnCount; %>>
         Comune
    </th>
   
    <th nowrap  <% ++columnCount; %> >
         <strong>Localita'</strong>
	</th>
 
   <th nowrap  <% ++columnCount; %> >
	  <strong>Tipo Zona di Produzione</strong>
	</th>
	<th nowrap  <% ++columnCount; %>>
          <strong>Classe</strong>
	</th>
	<th nowrap  <% ++columnCount; %>>
          <strong>Stato sanitario</strong>
	</th>
	<th nowrap  <% ++columnCount; %>>
          <strong>Stato classificazione</strong>
	</th>
	<th nowrap  <% ++columnCount; %>>
          <strong>Taglia non comm.</strong>
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
   <td>
     <a href="MolluschiBivalvi.do?command=Details&orgId=<%=thisOrg.getId()%>"> <%=SiteIdList.getSelectedValue(thisOrg.getSiteId()) %></a>
	</td>
	
	  <td><%=toHtml(thisOrg.getCun()) %></td>
    
	<%
	Iterator iaddress = thisOrg.getAddressList().iterator();
       Object address[] = null;
       int x = 0;
       boolean trovato = false ;
       if (iaddress.hasNext()) {
          
    	   while (iaddress.hasNext()) {
        	  org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress thisAddress = (org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress)iaddress.next(); 
          if(thisAddress.getType()==5){
        	  trovato = true ;
        	  %>
        	  <td><%=thisAddress.getCity() %></td>
        	
        	  <%
    }}}
    if(trovato==false)
    {
    	%>
    	<td>&nbsp;</td>
    	<%
    }
    %>
	
	  
	
 <td>
      <%= toHtml(thisOrg.getName()) %>
	</td>
	<td>
      <%=ZoneProduzione.getSelectedValue(thisOrg.getTipoMolluschi()) %>
	</td>
	
	<td>
    <%=toHtml(Classificazione.getSelectedValue(thisOrg.getClasse())) %>
	</td>
	
	<td>
   <%
	if(thisOrg.getTipoMotiviInZone().size()>0)
	{	Iterator<Integer> itKey = thisOrg.getTipoMotiviInZone().keySet().iterator();
		while (itKey.hasNext())
		{
		int key = itKey.next(); 
		String cammino = thisOrg.getTipoMotiviInZone().get(key); 
		%>
		<%=cammino %><br/>
		<%
		} } else { %> 	
		<% if (thisOrg.getTipoMolluschi()== 4 || thisOrg.getTipoMolluschi()== 5 ){ %>
		N.D.
 	<%} else {%>
		Favorevole <%
 	} }  %>
	
	</td>
	
	<td>
    <%= (thisOrg.getStatoClassificazione()>-1) ? StatiClassificazione.getSelectedValue(thisOrg.getStatoClassificazione()) : "N.D." %> 
	</td>
	
	<td>
    <%=thisOrg.isTagliaNonCommerciale() ? "SI" : "NO" %>
	</td>
	
	
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      Nessun operatore trovato
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>

