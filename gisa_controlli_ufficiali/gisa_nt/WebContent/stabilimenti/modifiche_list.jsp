<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>


<%@ page import="java.util.*,org.aspcfs.modules.stabilimenti.base.Organization" %>

<%@page import="org.aspcfs.modules.stabilimenti.storico.StoricoModifica"%>
<jsp:useBean id="listaModifiche" class="org.aspcfs.modules.stabilimenti.storico.StoricoModificaList" scope="request"/>
<jsp:useBean id="modificheListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request" />

<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Stabilimenti.do"><dhv:label name="">Stabilimenti</dhv:label></a> >  <a href="Stabilimenti.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a>
   > <a href="Stabilimenti.do?command=Details&orgId=<%=orgDetails.getOrgId() %>"><dhv:label name="">Scheda Stabilimento </dhv:label></a> > Lista Storico Modifiche   
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="modificheListInfo"/>

<br />
<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />

<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs/ext-all.js"></script>
<script type="text/javascript" src="extjs/examples/ux/TableGrid.js"></script>
<script type="text/javascript" >
Ext.onReady(function(){
        
        // create the grid
        var grid = new Ext.ux.grid.TableGrid("tabella-lista-storico", {
            stripeRows: true // stripe alternate rows
           
        });
        grid.render();
});
</script>

<table class="details" id="tabella-lista-storico" cellpadding="4" cellspacing="0" border="0" width="100%" >
	<thead>
	  <tr>
	    <%-- th width="8">
	      &nbsp;
	    </th --%>
	    <th width="16%" nowrap>
	      <strong>Data Modifica</strong>
	    </th>
	    <th width="5%" nowrap>
	      <strong><dhv:label name="">Nr campi modificati</dhv:label></strong>
	    </th>
	    <th width="16%" nowrap>
	      <strong><dhv:label name="">Utente</dhv:label></strong>
	    </th>
	  </tr>
  </thead>
  <tbody>
	  <% 
	    Iterator itr = listaModifiche.iterator();
	    if (itr.hasNext()){
	      int rowid = 0;
	      int i = 0;
	      while (itr.hasNext()){
	        i++;
	        rowid = (rowid != 1 ? 1 : 2);
	        StoricoModifica thisModifica = (StoricoModifica)itr.next();
	    %>    
	  <tr class="row<%=rowid%>">
	  <td width="15%" nowrap>
	  	 <a href="Stabilimenti.do?command=DetailsModifica&orgId=<%=orgDetails.getOrgId()%>&idModifica=<%=thisModifica.getIdModifica() %>">	<%= thisModifica.getDataModifica() %> </a>
		</td>
	 	<td width="15%" nowrap>
	      <%=thisModifica.getNrCampiModificati() %>
		</td>
		<td width="15%" nowrap>
	     <dhv:username id="<%= thisModifica.getIdUtente() %>" /> 
	    </td>
	    <%  
	      }
	    }else{
	    %>
	    <tr class="containerBody">
	      <td colspan="9">
	        <dhv:label name="">La cronologia delle modifiche è vuota</dhv:label>
	      </td>
	    </tr>
	    <%
	    }
	    %>
	  </tbody>
   </table>
<dhv:pagedListControl object="modificheListInfo" tdClass="row1"/>
