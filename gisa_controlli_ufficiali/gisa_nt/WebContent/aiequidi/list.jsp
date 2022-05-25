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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.modules.base.*" %>

<%@page import="org.aspcfs.modules.aiequidi.base.Aiequidi"%>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.aiequidi.base.AiequidiList" scope="request"/>
<jsp:useBean id="SearchListEquidiInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>

<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<script type="text/javascript" src="javascript/ext-base.js"></script>
<script type="text/javascript" src="javascript/ext-all.js"></script>
<script type="text/javascript" src="javascript/TableGrid.js"></script>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script type="text/javascript" >
Ext.onReady(function(){
    
    // create the grid
    var grid = new Ext.ux.grid.TableGrid("tabella-lista-equidi", {stripeRows: true} );
    grid.render();
}
);
</script>

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
AIEquidi
</td>
</tr>
</table>

<br>

<form method="post" action="AIEquidi.do?command=List">
<table cellpadding="4" cellspacing="0" border="0" width="50%" class="details">
<tr>
    <th colspan="2">
      <strong>iMPOSTA fILTRI</strong>
    </th>
  </tr>

 <tr>
    <td nowrap class="formLabel" >
      Anno
    </td>
    <td>
      <select name = "searchcodeanno">
      <option value = "-1" <%if (SearchListEquidiInfo.getSearchOptionValue("searchcodeanno").equals("-1")){%> selected="selected"<%} %>>--Tutti--</option>
      <option value = "2007"  <%if (SearchListEquidiInfo.getSearchOptionValue("searchcodeanno").equals("2007")){%> selected="selected"<%} %>>2007</option>
       <option value = "2008"  <%if (SearchListEquidiInfo.getSearchOptionValue("searchcodeanno").equals("2008")){%> selected="selected"<%} %>>2008</option>
       <option value = "2009"  <%if (SearchListEquidiInfo.getSearchOptionValue("searchcodeanno").equals("2009")){%> selected="selected"<%} %>>2009</option>
        <option value = "2010"  <%if (SearchListEquidiInfo.getSearchOptionValue("searchcodeanno").equals("2010")){%> selected="selected"<%} %>>2010</option>
         <option value = "2011"  <%if (SearchListEquidiInfo.getSearchOptionValue("searchcodeanno").equals("2011")){%> selected="selected"<%} %>>2011</option>
          <option value = "2012"  <%if (SearchListEquidiInfo.getSearchOptionValue("searchcodeanno").equals("2012")){%> selected="selected"<%} %>>2012</option>
           <option value = "2013"  <%if (SearchListEquidiInfo.getSearchOptionValue("searchcodeanno").equals("2013")){%> selected="selected"<%} %>>2013</option>
             <option value = "2014"  <%if (SearchListEquidiInfo.getSearchOptionValue("searchcodeanno").equals("2014")){%> selected="selected"<%} %>>2014</option>
           
      </select>
      
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel" >
      Id Capo
    </td>
    <td>
      <input type = "text" name = "searchidCapo" value = "<%=toHtml2(SearchListEquidiInfo.getSearchOptionValue("searchidCapo")) %>" >
      
    </td>
  </tr>
 <tr>
    <td nowrap class="formLabel" >
      Num Accert.
    </td>
    <td>
      <input type = "text" name = "searchnumAccertamento" value = "<%=toHtml2(SearchListEquidiInfo.getSearchOptionValue("searchnumAccertamento")) %>" >
      
    </td>
  </tr>
 <tr>
    <td colspan="2" >
      <input type = "submit" value = "Ricerca">
    </td>
   
  </tr>
</table>
</form>
<br>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchListEquidiInfo"/>
<% int columnCount = 0; %>


<table cellpadding="8" id="tabella-lista-equidi" cellspacing="0" width="100%" border="1">
 
 <thead> <tr>
    
    <th width="15%" >
      Anno
    </th>
   <th nowrap <% ++columnCount; %>>

      <strong>Num. Accettazione</strong>

    </th>
    <th nowrap <% ++columnCount; %>>
          <strong>Num campione</strong>
		</th>
    
    
         <th <% ++columnCount; %>>
         Num. Conf
        </th>
      
        <th nowrap <% ++columnCount; %>>
          Data Prelievo
		</th>
		 <th nowrap <% ++columnCount; %>>
          Data Accettazione
		</th>
        <th nowrap <% ++columnCount; %>>
          Richiedente
		</th>
		<th nowrap <% ++columnCount; %>>
      Cod. Azienda
    </th>
    
        <th nowrap <% ++columnCount; %>>
         Ragione Sociale
		</th>
		
		<th nowrap <% ++columnCount; %>>
         Citta
		</th>
		<th nowrap <% ++columnCount; %>>
         Id Capo
		</th>
		<th nowrap <% ++columnCount; %>>
         Risultato
		</th>
		<th nowrap <% ++columnCount; %>>
         Esito
		</th>
		<th nowrap <% ++columnCount; %>>
         Data Fine Prova
		</th>
		<th nowrap <% ++columnCount; %>>
         Num. Rapporto
		</th>
  </tr>
  </thead>
  <tbody>

<%
	Iterator j = OrgList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Aiequidi thisOrg = (Aiequidi)j.next();
%>
  <tr class="row<%= rowid %>">
    
	<td width="15%">
     <%=thisOrg.getAnno() %>
	</td>
	
	<td>
      <%=thisOrg.getNum_accettazione() %>
	</td>

		
	<td>
      <%= toHtml("" +thisOrg.getNum_capi_prelevati()) %>
	</td>
	
     <td>
      <%= toHtml("" +thisOrg.getNum_acc_progressivo_camp()) %>
	</td> 
	 <td>
      <%= toHtml(thisOrg.getData_prelievo_string()) %>
	</td>
	 <td>
      <%= toHtml(thisOrg.getData_accettazione_string()) %>
	</td>
	 <td>
      <%= toHtml(thisOrg.getNomin_utente()) %>
	</td>
	 <td>
      <%= toHtml(thisOrg.getCodazie()) %>
	</td>
	
	<td>
      <%= toHtml(thisOrg.getRagione_sociale()) %>
	</td>
	
	<td>
      <%= toHtml(thisOrg.getCitta()) %>
	</td>
	
	<td>
      <%= toHtml(thisOrg.getId_capo()) %>
	</td>
	
	
	<td>
      <%= toHtml(thisOrg.getRisultato()) %>
	</td>
	
	
	
	<td>
      <%= toHtml(thisOrg.getEsito()) %>
	</td>
	
	<td>
      <%= toHtml(thisOrg.getData_fine_prova_string()) %>
	</td>
	<td>
      <%= toHtml(""+thisOrg.getNum_rapporto()) %>
	</td>
	
	
	
  </tr>
  
<%
    }
	}
%></tbody> 
</table>
<br />
<dhv:pagedListControl object="SearchListEquidiInfo" tdClass="row1"/>

