<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.stabilimenti.base.*,org.aspcfs.modules.campioni.base.Ticket,com.zeroio.iteam.base.*" %>

<%@page import="org.aspcfs.modules.stabilimenti.base.MerceInOut"%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request"/>
<jsp:useBean id="MerceInOutList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="AccountTicketInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="TipoMollusco" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="merce_out_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Stabilimenti.do"><dhv:label name="stabilimenti.stabilimenti">Accounts</dhv:label></a> > 
<a href="Stabilimenti.do?command=Search"><dhv:label name="stabilimenti.SearchResults">Search Results</dhv:label></a> >
<a href="Stabilimenti.do?command=Details&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="communications.campaign.Dashboards">Scheda Stabilimento</dhv:label></a> >
<dhv:label name="merce_in_out">Merce in Ingresso/Uscita</dhv:label><br/>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="<%=(OrgDetails.isMacelloUngulati()) ? ("stabilimenti_macellazioni_ungulati") : ("stabilimenti") %>" selected="merce_in_out" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:evaluate if="<%= !OrgDetails.isTrashed() %>">
    <dhv:permission name="stabilimenti-stabilimenti-merce_in_out-add"><a href="StabMerceInOut.do?command=Add&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="merce_in_out.new">Nuova Merce in Ingresso/Uscita</dhv:label></a></dhv:permission><br/>
  </dhv:evaluate>
    <input type=hidden name="orgId" value="<%= OrgDetails.getOrgId() %>">
    <br>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
        
			<th width="8">
	      		&nbsp;
	    	</th>
	    	
		    <th valign="center" align="left">
		      <strong><dhv:label name="merce_in_out.data_arrivo">Data Arrivo</dhv:label></strong>
		    </th>
		    
     		<th>
     			<b><dhv:label name="merce_in_out.data_invio">Data Invio</dhv:label></b>
     		</th>
     		
    		<th>
    			<b><dhv:label name="merce_in_out.specie_moll">Specie Mollusco</dhv:label></b>
    		</th>
    		
    		<th>
    			<b><dhv:label name="merce_in_out.quantita">Quantit&agrave;</dhv:label></b>
    		</th>
    		
  </tr>
  <%
    Iterator j = MerceInOutList.iterator();
    if ( j.hasNext() ) {
      int rowid = 0;
      int i =0;
      while (j.hasNext()) {
        i++;
        rowid = (rowid != 1?1:2);
        MerceInOut thisTic = (MerceInOut)j.next();
  %>
    <tr class="row<%= rowid %>">
      <td width="8" valign="top" nowrap>
        <a href="StabMerceInOut.do?command=Details&id=<%=thisTic.getId() %>" >Scheda</a>
      </td>
		
		<td valign="top">
			<zeroio:tz timestamp="<%= thisTic.getData_arrivo()%>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>&nbsp;
		</td>
		
		<td width="15%" valign="top" class="row<%= rowid %>">
	      <zeroio:tz timestamp="<%= thisTic.getData_invio()%>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>&nbsp;
		</td>
		
		
		<td valign="top">
			<%= TipoMollusco.getSelectedValue( thisTic.getId_specie_mollusco() ) %>&nbsp;
		</td>
		
		<td valign="top">
			<%= toHtmlValue( thisTic.getQuantita() ) %>&nbsp;
		</td>
		
	</tr>

  <%}%>
  <%} else {%>
  
    <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="accounts.richieste.search.notFound">Nessuna Merce in Ingresso/Uscita.</dhv:label>
      </td>
    </tr>
  <%}%>
  </table>
	<br>
</dhv:container>