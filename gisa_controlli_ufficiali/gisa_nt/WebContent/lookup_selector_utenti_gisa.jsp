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
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="DisplayFieldId2" class="java.lang.String" scope="request"/>
<jsp:useBean id="Table" class="java.lang.String" scope="request"/>
<jsp:useBean id="FiltroDesc" class="java.lang.String" scope="request"/>

<jsp:useBean id="FiltroDesc2" class="java.lang.String" scope="request"/>
<jsp:useBean id="FiltroDesc3" class="java.lang.String" scope="request"/>


<jsp:useBean id="LookupSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script>
function checkFiltro() {
  //cancello eventuali spazi all'inizio e alla fine del testo
  var nome = leftTrim(rightTrim(document.elementListView.filtroDesc.value));
  var cognome = leftTrim(rightTrim(document.elementListView.filtroDesc1.value));

  document.elementListView.filtroDesc.value = nome;
  document.elementListView.filtroDesc1.value = cognome;
 //if (desc != "" || desc2!=""){
    elementListView.submit();  
  //} else {
    //document.elementListView.filtroDesc.focus();  
  //}
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
<%@ include file="initPage.jsp" %>
<body >
<form name="elementListView" method="post" action="LookupSelector.do?command=PopupSelectorUtentiGisa">
<br />
<table width="100%" border="0">
  <tr>
    <td>
      <b>Nome</b> <input type="text" size="20" name="filtroDesc" value="<%= FiltroDesc %>"/> 
      <b>Cognome</b> <input type="text" size="20" name="filtroDesc1" value="<%= FiltroDesc2 %>"/> 
      <input type = "hidden" id = "idasl" name = "idasl" value= "<%=request.getAttribute("idasl") %>">
    </td>
     <td><input type="button" value="<dhv:label name="button.search">Search</dhv:label>" onClick="checkFiltro();"></td>
    
  </tr>
</table>
<div style="height: 350px; overflow: auto;">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="10%">
      <dhv:label name="">Username</dhv:label>
    </th>
    <th width="10%">
      <dhv:label name="contact.Descrizione">Nome</dhv:label>
    </th>
    <th width="10%">
      Cognome
    </th>
    <th width="10%">
      <dhv:label name="">Ruolo</dhv:label>
    </th>
    
  </tr>
  <%
  Iterator j = BaseList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      CustomLookupElement thisElt = (CustomLookupElement)j.next();
     
    
      String username = thisElt.getValue("username");
      String nome = thisElt.getValue("nome");
      String cognome = thisElt.getValue("cognome");
      String userid = thisElt.getValue("userid");
      String ruolo = thisElt.getValue("ruolo");
      //int code = codeString.startsWith("--") ? -1 : Integer.parseInt(codeString);
  %>
  <tr class="row<%= rowid %>">
    <td valign="center">
      <a href="javascript:setParentValue2_utentiGisa('<%=username %>','<%=userid %>','<%=ruolo %>');">
        <%= toHtml(username) %>
      </a>
    </td>
    <td valign="center">
     <%= toHtml(nome) %>
    </td>
     <td valign="center">
     <%= toHtml(cognome) %>
    </td>
    <td valign="center">
     <%= toHtml(ruolo) %>
    </td>
    
  </tr>
<%} }
  
  else {%>
      <tr class="containerBody">
        <td colspan="2">
          <dhv:label name="calendar.noOptionsAvailable.text">No options are available.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
</div>
<input type="hidden" name="rowcount" value="0">
<input type="hidden" name="displayFieldId" value="<%= DisplayFieldId %>">
<input type="hidden" name="displayFieldId2" value="<%= DisplayFieldId2 %>">
<input type="hidden" name="table" value="<%= Table %>">
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
<br />
</form>
</body>
