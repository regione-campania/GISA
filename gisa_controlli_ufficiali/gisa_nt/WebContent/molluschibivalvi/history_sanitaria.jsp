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
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>


<%@page import="org.aspcfs.modules.molluschibivalvi.base.HistoryClassificazione"%>
<%@page import="java.util.Date"%><jsp:useBean id="SearchHistoryOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<%@page import="org.aspcfs.modules.molluschibivalvi.base.HistoryClassificazioneList"%>
<jsp:useBean id="ZoneProduzione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Classificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="orgId" class="java.lang.String" scope="request" />
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.molluschibivalvi.base.Organization" scope="request" />




<%@ include file="../initPage.jsp"%>

<% String param1 = "orgId=" + orgId;
%>
<dhv:container name="molluschibivalvi" selected="details" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>

<%
HistoryClassificazioneList list =OrgDetails.getListaDecreti();


int id = OrgDetails.getOrgId() ;

%>

<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td><a href="MolluschiBivalvi.do">Molluschi bivalvi</a> > <%
			if (request.getParameter("return") == null) {
			%>
			<a href="MolluschiBivalvi.do?command=Search"><dhv:label
				name="stabilimenti.SearchResults">Search Results</dhv:label></a> > <%
			} 
			%>
			
			
			<a href="MolluschiBivalvi.do?command=Details&orgId=<%= id %>">Scheda Molluschi Bivalvi</a> > History Sanitaria
			
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>

<script type="text/javascript">

$(document).ready(function()
{
    $('.RowToClick').click(function ()
    {
        $(this).parent().nextAll('tr').each( function()
        {
            if ($(this).is('.RowToClick'))
           {
              return false;
           }
           $(this).toggle(350);
        });
    });
});

</script>

<% int columnCount = 0; %>

<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">

   <tr>
  
   
	<th nowrap  <% ++columnCount; %> >
         <strong>Provvedimento Restrittivo</strong>
	</th>
	
	<th nowrap  <% ++columnCount; %> >
         <strong>Num. decreto</strong>
	</th>
	
	<th nowrap  <% ++columnCount; %> >
         <strong>Data Provvedimento</strong>
	</th>
	<th nowrap  <% ++columnCount; %> >
         <strong>Data Riattivazione</strong>
	</th>
	</tr>
	
<%

if (OrgDetails.getListaDecreti()!=null)
{
	Iterator j = list.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    HistoryClassificazione thisOrg = (HistoryClassificazione)j.next();
%>



  
  
  <%
  if (thisOrg.getListaProvvedimenti()!=null)
  {
  	HistoryClassificazioneList provvedimentoList = thisOrg.getListaProvvedimenti();
  Iterator<HistoryClassificazione> itProv = provvedimentoList.iterator();
  	while (itProv.hasNext())
  	{
  		HistoryClassificazione provvedimento = itProv.next();
  		if (provvedimento.getClassificazione()>0){
  		%>
  		
  	
  		<tr class="row<%= rowid %>">
  	
	
 <td>
    <%=toHtmlValue(Classificazione.getSelectedValue(provvedimento.getClassificazione())) %>
 
<%
if(provvedimento.getListaMotivi().size()>0){
	HashMap<Integer,String> motivi = provvedimento.getListaMotivi();
		Iterator<Integer> itMot = motivi.keySet().iterator();
  		while (itMot.hasNext())
  		{
  			int idMot = itMot.next();
  			String path = motivi.get(idMot);
  			%>
  			<%=path %><br/>
  			<%
  		} }
  		%>

	</td>
	
	<td>
	 <%=thisOrg.getNumDecreto() %>
	</td>
	
	<td>
	 <%=toDateasString(provvedimento.getDataProvvedimento() ) %>
	</td>
	<td>
	 <%=toDateasString(provvedimento.getDataRiattivazione() ) %>
	</td>
	

	
  </tr>
  
  
  		
  		<%
  		
  	} }
  }
  %>  
  <br/><br/>
  
<%}%>
<%}} else {%>
  <tr class="containerBody">
    <td colspan="8">
    Nessuna Storia Sanitaria Presente
    </td>
  </tr>
<%}%>
 </table>
<br />
</dhv:container> 
