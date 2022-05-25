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
	document.elementListView.searchcodeidAsl.value = "-1";
	document.elementListView.ragioneSociale.value = "";
}

function clear()
{
	alert('pulisci form');
//	document.elementListView.searchcodeidAsl.value = "-1";-->
//	document.elementListView.ragioneSociale.value = "";
}

</script>
<%@ include file="initPage.jsp" %>


<body style="overflow-y: scroll;" >
<%-- Trails --%>

<% 

String tipologia = (String)request.getParameter("displayFieldId");
//String siteId = (String)request.getParameter("idAsl");
//String codAteco = (String)request.getParameter("codAteco");
%>


<center><strong>LISTA SPEDITORI</strong></center>
<br/>
<br/>
<form name="elementListView" method="post" action="LookupSelector.do?command=PopupSelectorMacellazioneSpeditore&displayFieldId=<%= tipologia%>" >
<input type = "hidden" name = "tipologia" value = "<%= tipologia %>">
<br />
<table width="100%" border="0">
  <tr >
	  <td>
	  <b>Denominazione:</b> 
	  <input type = "text" name = "searchAccountName" value = "" />
	  </td>
	  <td>
	  <b> CODICE AZIENDA : </b> 
	   <input type = "text" name = "searchAccountNumber" value = ""/>
	  </td>
  	  <td>
  	  <input type="button" value="<dhv:label name="button.search">Search</dhv:label>" onClick="checkFiltro();" />
  	  </td>
  </tr>
</table>
<br><br><br>
<div align = "center">
	<a href ="Speditore.do?command=Add&popup=true">Aggiungi Speditore</a>
</div>


<br>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<% int columnCount = 0; %>

<table cellpadding="5" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="20%">
     Denominazione
    </th>
    <th width="10">
      Codice Azienda
    </th>
    <th width="5">
      Modifica
    </th>
    <th width="5">
      Cancella
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
      String proprietario = thisElt.getValue("nome_rappresentante");
      String asl = thisElt.getValue("asldescr");
      String comune = thisElt.getValue("city"); 
      String stato = thisElt.getValue("country");
      String prov = thisElt.getValue("state");
      String tipologiaEl = thisElt.getValue("tipologia");
      
      if( asl == null){
    	  asl = "ND";
      }
      
      if( comune == null){
    	  comune = "ND";
      }
      
      if( prov == null){
    	  prov = "ND";
      }
      
     
  %>
  <tr class="row<%= rowid %>">
    <td valign="center">
     <a href="javascript:setSpeditoreField('<%= toHtml(ragioneSociale).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(num_reg).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(proprietario).replaceAll("'"," ").toUpperCase() %>', '<%= toHtml(asl).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(comune).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(prov).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(stato).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(orgId) %>')">
        <%= toHtml(ragioneSociale) %>
      </a>
    </td>
    <td valign="center">
    	<%= toHtml(num_reg) %>
    </td>
    <td valign="center">
    	<%
    	if("11".equalsIgnoreCase(tipologiaEl))
    	{
    	%>
    	<a href="Speditore.do?command=Modify&popup=true&orgId=<%= orgId %>"><img src="images/edit.gif" /></a>
    	<%}
    	else
    	{
    		%>
    		Non Modificabile (Azienda Zootecninca)
    		<%
    		
    	}
    	%>
    </td>
    <td valign="center">
    <%
    	if("11".equalsIgnoreCase(tipologiaEl))
    	{
    	%>
    	<a href="Speditore.do?command=Trash&popup=true&orgId=<%= orgId %>"><img src="images/delete.gif" /></a>
    	<%}
    	else
    	{
    		%>
    		Non Modificabile (Azienda Zootecninca)
    		<%
    		
    	}
    	%>
    </td>
    
  </tr>
  <%
if(request.getAttribute("codicespeditore")!=null)
{
	%>
	<script>
	setSpeditoreField('<%= toHtml(ragioneSociale).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(num_reg).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(proprietario).replaceAll("'"," ").toUpperCase() %>', '<%= toHtml(asl).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(comune).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(prov).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(stato).replaceAll("'"," ").toUpperCase() %>','<%= toHtml(orgId) %>');
	</script>
	<%
	
}
%>
  
<%} }
  
 
else { %>
<%-- <%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>" --%>
  <tr class="containerBody">
    <td colspan="4">
      <dhv:label name="">Nessun Speditore presente</dhv:label><br />
      <a href="LookupSelector.do?command=PopupSelectorMacellazioneSpeditore&displayFieldId=<%= tipologia%>"><dhv:label name="">Modifica Ricerca</dhv:label></a>.
    </td>
  </tr>
  <script>
  window.location.href='Speditore.do?command=Add&popup=true&fromlist=true'
  </script>
  
<% } %>
</table>
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>
</form>



