<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%--Pagina JSP creata da Francesco --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.utils.*" %>
<jsp:useBean id="BaseList" class="org.aspcfs.utils.web.CustomLookupList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LookupSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<link type="text/css" href="css/ui.all.css" rel="stylesheet" />
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script type="text/javascript">
function checkFiltro() {

    elementListView.submit();  

}
function clearR(){
	//document.elementListView.searchcodeidAsl.value = "-1";
	document.elementListView.ragioneSociale.value = "";
}

</script>
<%@ include file="initPage.jsp" %>


<body style="overflow-y: scroll;" >
<%-- Trails --%>

<% 

String indiceLab = (String)request.getParameter("displayFieldId");
//String siteId = (String)request.getParameter("idAsl");
%>


<center><strong>LISTA LABORATORI HACCP IN REGIONE</strong></center>
<br/>
<br/>
<form name="elementListView" method="post" action="LookupSelector.do?command=PopupSelectorCustomLaboratoriHaccp&displayFieldId=<%= indiceLab%>" >
<input type = "hidden" name = "indiceLab" value = "<%= indiceLab %>">

<br />
<table width="100%" border="0">
<%--   <tr >
<td>
<b>ASL&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b> 
      
<%
	int idAsl =-1;
	if(request.getAttribute("siteId")!=null)
	{
		idAsl = Integer.parseInt((String)(request.getAttribute("siteId")));
	}
	
%>

<%=SiteIdList.getHtmlSelect("searchcodeidAsl",BaseList.getIdAsl()) %>
</td>
</tr>
  --%>
  <tr >
  <td>
  <b>Ragione Sociale : </b> 
  <input type = "text" name = "searchAccountName" value ="">
  </td>
  </tr>
   
  <tr >
    <td><input type="button" value="<dhv:label name="button.search">Search</dhv:label>" onClick="checkFiltro();">
    <input type="button" value="Pulisci" onClick="clearR()">
  </td></tr>
</table>

<br><br><br>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<% int columnCount = 0; %>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="20%">
     Ragione Sociale
    </th> 
    <th width="30%">
     	Num. iscrizione
    </th>
    <th width="30%">
     	Dir. Responsabile Lab.
    </th>
  </tr>

<%
  Iterator j = BaseList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      CustomLookupElement thisElt = (CustomLookupElement)j.next();
     
      String ragioneSociale = thisElt.getValue("name");
      String orgId = thisElt.getValue("org_id");
      String num_reg = thisElt.getValue("account_number");
      String dir = thisElt.getValue("cognome_rappresentante");
 	  if (dir != null && !dir.equals("")){
 		 dir = thisElt.getValue("cognome_rappresentante");
 	  }
 	  else {
 		dir = "ND";  
 	  }
%>
  <tr class="row<%= rowid %>">
    <td valign="center">
      <a href="javascript:setLaboratorioField('<%= toHtml(ragioneSociale).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(num_reg).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(orgId) %>')">
        <%= toHtml(ragioneSociale) %>
      </a>
    </td>
    <td valign="center">
    	<%= toHtml(num_reg) %>
    </td>
    <td valign="center">
    	<%= toHtml(dir) %>
    </td>
    
  </tr>
<%} }
  
 
else {%>
  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      <dhv:label name="">No accounts found with the specified search parameters.</dhv:label><br />
      <a href="LookupSelector.do?command=PopupSelectorCustomLaboratoriHaccp&displayFieldId=<%= indiceLab%>"><dhv:label name="">Modifica Ricerca</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>
</form></body>
<script type="text/javascript" >
function clear()
{
	alert('pulisci form');
//	document.elementListView.searchcodeidAsl.value = "-1";-->
//	document.elementListView.ragioneSociale.value = "";
}
</script>

