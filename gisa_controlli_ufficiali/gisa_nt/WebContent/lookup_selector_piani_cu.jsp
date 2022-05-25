<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.utils.*" %>
<jsp:useBean id="BaseList" class="org.aspcfs.utils.web.CustomLookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="lookup_gruppi_piani" class = "org.aspcfs.utils.web.LookupList" scope = "request"></jsp:useBean>
<jsp:useBean id="DisplayFieldId2" class="java.lang.String" scope="request"/>
<jsp:useBean id="Table" class="java.lang.String" scope="request"/>
<jsp:useBean id="FiltroDesc" class="java.lang.String" scope="request"/>
<jsp:useBean id="SearchPianiListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script>
function checkFiltro() {
  //cancello eventuali spazi all'inizio e alla fine del testo
  var desc = leftTrim(rightTrim(document.elementListView.filtroDesc.value));
  document.elementListView.filtroDesc.value = desc;
  document.elementListView.submit();
    document.elementListView.filtroDesc.focus();  
  
}

function leftTrim(stringa) {
  while (stringa.substring(0,1) == ' ') {
    stringa = stringa.substring(1, stringa.length);
  }
  return stringa;
}

function rightTrim(stringa) {
  while (stringa.substring(stringa.length-1, stringa.length) == ' ') {
    stringa = stringa.substring(0,stringa.length-1);
  }
  return stringa;
}

</script>
<%

boolean nazionali = false ;
boolean regionali = false ;
boolean territoriali = false ;

String  tipi_piani_monitoraggio = (String) request.getAttribute("TipiPiani");
int val_sel = -1 ;
if (tipi_piani_monitoraggio != null)
{
	val_sel = Integer.parseInt(tipi_piani_monitoraggio);
}
%>

<%@ include file="initPage.jsp" %>
<body onload="javascript:document.elementListView.filtroDesc.focus()">
<form name="elementListView" method="post" action="LookupSelector.do?command=PopupSelectorCustomPianiMonitoraggio&table=<%=Table %>">
<input type = "hidden" name = "idAsl" value = "<%=request.getAttribute("idAsl") %>">
<br />

<table width="100%" border="0">
  <tr>
    <td>
    <%if(request.getAttribute("table")!=null)
    	{%>
    	<input type = "hidden" name = "table" value = "<%=request.getAttribute("table") %>">
    	<%} %>
     
    </td>
  </tr>
  
  <tr>
    <td>
    <table>
    <tr><td><b>Gruppo</b></td><td><%=lookup_gruppi_piani.getHtmlSelect("tipo",val_sel)%></td></tr>
    <tr><td><b>Descrizione</b></td><td><input type="text" size="200" name="filtroDesc" value="<%= FiltroDesc %>"/> </td></tr>
<input type="button" value="<dhv:label name="button.search">Search</dhv:label>" onClick="checkFiltro();">
</table>      
     </td>
  </tr>
  
</table>
</form>

<div style="height: 650px; overflow: auto;">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="100%">
      Lista Piani P.R.I
    </th>
  </tr>
  <%
  boolean campio_titolo = true  ;
  String old_gruppo  = "" ;
  String old_sezione  = "" ;
  Iterator j = BaseList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      CustomLookupElement thisElt = (CustomLookupElement)j.next();
      boolean enabled = thisElt.getValue("abilitato") == "true" ? true : false;
      //boolean defaultItem = thisElt.getValue("default_item") == "true" ? true : false;
      String description = thisElt.getValue("descrizione_piano");
     // String shortDescription = thisElt.getValue("short_description");
      String codeString = thisElt.getValue("codice_piano");
      String sezione = thisElt.getValue("sezione");
      
     
      int code =	 codeString.startsWith("--") ? -1 : Integer.parseInt(codeString);
      String gruppo = thisElt.getValue("gruppo");
      
      if (!sezione.equals(old_sezione))
      {
    	  %>
    	  <tr><th> <%=sezione %></th></tr>
    	 
    	  <%
    	  old_sezione = sezione ;
      }
      
      if (!gruppo.equals(old_gruppo))
      {
    	  %>
    	  <tr><th> <%=gruppo %></th></tr>
    	 
    	  <%
    	  old_gruppo = gruppo ;
      }
      
  %>
  <tr><td><a href="javascript:setParentValue_piani(<%= code %>,'<%= StringUtils.jsStringEscape(description).toUpperCase() %>','<%= StringUtils.jsStringEscape(gruppo).toUpperCase() %>');">
        <%= toHtml(description.toUpperCase()) %>
      </a></td></tr>
  
  
<%} } else {%>
      <tr class="containerBody">
        <td >
          <dhv:label name="calendar.noOptionsAvailable.text">No options are available.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
</div>
<input type="hidden" name="rowcount" value="0">
<br/>
<br/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
</body>
