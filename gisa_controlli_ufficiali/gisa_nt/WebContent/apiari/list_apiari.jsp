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
<%@page import="ext.aspcfs.modules.apiari.base.Stabilimento"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.modules.base.*" %>

<%@page import="org.aspcfs.modules.opu.base.Operatore"%>
<%@page import="ext.aspcfs.modules.apiari.base.Stabilimento"%>

<jsp:useBean id="OrgList" class="ext.aspcfs.modules.apiari.base.StabilimentoList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LookupStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="SearchApiListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>


  <%-- Preload image rollovers for drop-down menu --%>
  
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ApicolturaAttivita.do?command=SearchForm"><dhv:label name="">Apicoltura</dhv:label></a> > 
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>


<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchApiListInfo"/>
<% int columnCount = 0; %>


<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    
    <th nowrap <% ++columnCount; %>>
    	 Ragione Sociale Apicoltore
      <%= SearchApiListInfo.getSortIcon("o.name") %>
    </th>
    <th nowrap <% ++columnCount; %>>

      <strong>#</strong>

    </th>
   
   <th nowrap <% ++columnCount; %>>

      <strong>ASL di Competenza Apicoltore</strong>

    </th>
    <th nowrap <% ++columnCount; %>>

      <strong>ASL di Competenza Apiario</strong>

    </th>
    <th nowrap <% ++columnCount; %>>

      <strong>Comune Apiario</strong>

    </th>
   <th nowrap <% ++columnCount; %>>

      <strong>Codice Azienda Apicoltore</strong>

    </th>
    
    
    <th nowrap <% ++columnCount; %>>
          <strong>Codice Fiscale Proprietario</strong>
		</th>
    
    
         <th <% ++columnCount; %>>
          <strong>Nominativo Proprietario</strong>
        </th>
        
        <th <% ++columnCount; %>>
          <strong>Stato</strong>
        </th>
       <th nowrap <% ++columnCount; %>>
          <strong>TIPOLOGIA</strong>
		</th>
		 <th nowrap <% ++columnCount; %>>
          <strong>Ubicazione Apiario</strong>
		</th>
        <th nowrap <% ++columnCount; %>>
          <strong>Codice Fiscale Detentore</strong>
		</th>
		 <th nowrap <% ++columnCount; %>>
          <strong>Nominativo Detentore</strong>
		</th>
       
		  <th nowrap <% ++columnCount; %>>
          <strong>Stato BDN</strong>
		</th>
		
  </tr>
<%
	Iterator j = OrgList.iterator();
	if ( j.hasNext() || OrgList.iterator().hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Stabilimento thisOrg = (Stabilimento)j.next();
%> <!-- thisOrg e' l'apiario e thisOrg.getOperatore( )e' l'apicoltore -->
  <tr class="row<%= rowid %>" <%if(thisOrg.getOperatore().getIdAsl()==16)  {%> style="background-color: red"<%} %>>
    
	<td>
	
      <a href="ApicolturaApiari.do?command=Details&stabId=<%=thisOrg.getIdStabilimento()%>"><%=thisOrg.getOperatore().getRagioneSociale() %></a>
	</td>
	
	<td>
	
      <%=thisOrg.getProgressivoBDA() %>
	</td>
	<td>
		<%=SiteIdList.getSelectedValue(thisOrg.getOperatore().getIdAsl()) %> <!-- asl apicoltore -->
	</td>
	<td>
	
      <%=SiteIdList.getSelectedValue(thisOrg.getIdAsl()) %> <!-- asl apiario -->
	</td>
	
	<td>
	<%=thisOrg.getSedeOperativa().getDescrizioneComune() %>
		
	</td>
	
	<td>
	
      <%=toHtml2(thisOrg.getOperatore().getCodiceAzienda()) %>
	</td>
	<td>
      <%=(thisOrg.getOperatore().getRappLegale()!=null)? thisOrg.getOperatore().getRappLegale().getCodFiscale() : "" %>
	</td>
	
	<td>
      <%=thisOrg.getOperatore().getRappLegale()!=null ? thisOrg.getOperatore().getRappLegale().getNome() +" "+ thisOrg.getOperatore().getRappLegale().getCognome() :""%>
	</td>
<td>
	
      <%=LookupStati.getSelectedValue(thisOrg.getStato()) %>
	</td>
	<td>
      <%=thisOrg.isFlagLaboratorio()  ? "LABORATORIO ANNESSO" : "APIARIO"%>
	</td>
	<td>
      <%= thisOrg.getSedeOperativa() != null ? (ComuniList.getSelectedValue(thisOrg.getSedeOperativa().getComune()) + " "+ thisOrg.getSedeOperativa().getVia()) : ""%>
	</td>
	
	<td>
      <%= thisOrg.getDetentore() !=null ? thisOrg.getDetentore().getCodFiscale() : "" %>
	</td>
	
	
	<td>
      <%=thisOrg.getDetentore() != null ? thisOrg.getDetentore().getNome() + " "+thisOrg.getDetentore().getCognome() : ""%>
	</td>
		
	
		
	<td><%=thisOrg.isFlagLaboratorio()==false ? thisOrg.isSincronizzatoBdn()==true ? "<img src=\"images/verde.gif\">" : "<img src=\"images/rosso.gif\">" :""%></td>
  </tr>
<%}%>

<%} else {%>

  <tr class="containerBody">
    <td colspan="<%= SearchApiListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="accounts.search.notFound">No accounts found with the specified search parameters.</dhv:label><br />
      <a href="ApicolturaAttivita.do?command=SearchForm"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchApiListInfo" tdClass="row1"/>

