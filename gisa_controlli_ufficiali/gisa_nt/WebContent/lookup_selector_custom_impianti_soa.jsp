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
<jsp:useBean id="LookupSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script>
var lista_elementi_selezionati = new Array() ;
var lista_elementi_selezionati2 = new Array() ;

function selezionaElementi()
{
	setParentValueMultipla(lista_elementi_selezionati,lista_elementi_selezionati2);
	
}

function fillArray(campo,indice,descr)
{
if (campo.checked == true)
{
	lista_elementi_selezionati[indice] = campo.value;
	lista_elementi_selezionati2[indice] =descr;
}
else
{
	lista_elementi_selezionati[indice] = '';
	lista_elementi_selezionati2[indice] = '';
	}

}

function checkFiltro() {
  //cancello eventuali spazi all'inizio e alla fine del testo
  var desc = leftTrim(rightTrim(document.elementListView.filtroDesc.value));
  document.elementListView.filtroDesc.value = desc;
  if (desc != ""){
    elementListView.submit();  
  } else {
	  alert('Inserire la descrizione dell attività da ricercare');
    document.elementListView.filtroDesc.focus();  
  }
}

function clearRicerca() {
	
	  //cancello eventuali spazi all'inizio e alla fine del testo
	  document.elementListView.filtroDesc.value="";
	  
	  document.elementListView.submit();  
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
String prev="00";
String att = "01";

%>
<%@ include file="initPage.jsp" %>
<body onload="javascript:document.elementListView.filtroDesc.focus()">

<form name="elementListView" method="post" action="LookupSelector.do?command=PopupSelectorCustomSoa">
<input type= "hidden" name = "tipo_selezione" value = "<%=request.getAttribute("tipoSelezione") %>">

<%	

if (request.getAttribute("orgId")!=null)
	
{
%>
<input type= "hidden" name = "orgId" value = "<%=request.getAttribute("orgId") %>">

<%	
}
%>
<br />
<table width="100%" border="0">
  <tr>
    <td>
      <b>Descrizione</b> <input type="text" size="20" name="filtroDesc" value="<%= FiltroDesc %>"/> 
      <input type="button" value="<dhv:label name="button.search">Search</dhv:label>" onClick="checkFiltro();">
      <input type="button" value="Azzera Ricerca" onClick="javascript:clearRicerca();">
      
      
    </td>
  </tr>
</table>
<div style="height: 350px; overflow: auto;">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
 
  <tr style="backgroundcolor:gray">
  	
  	<%String tipoSel = (String)request.getAttribute("tipoSelezione") ;
  	if (tipoSel.equals("true"))
  	{%>
  	<th>&nbsp;</th>
  	<%
  	}
  	%>
  
      <th width="20%">
      <dhv:label name="">Impianto</dhv:label>
    </th>
    <th width="80%">
      <dhv:label name="contact.Descrizione">Categoria</dhv:label>
    </th>
  </tr>
 
  <%
  
  Iterator j = BaseList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    int indice = 0 ;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      CustomLookupElement thisElt = (CustomLookupElement)j.next();
   
      String descriptionImpianto = thisElt.getValue("impianto");
      String descriptionCategoria = thisElt.getValue("categoria");
   
   %>
  <%
  
  %>
  <tr class="row<%= rowid %>">
  <%
  if (tipoSel.equals("true"))
  	{%>
  	<td><input type = "checkbox" id = "sel_<%=indice %>" value = "<%=toHtml(descriptionImpianto) %>" onclick="fillArray(this,<%=indice %>,'<%= toHtml(descriptionCategoria) %>')"></td>
  	<%
  	indice ++ ;
  	}
  	%>
  
    <td valign="center">
   <% if (tipoSel.equals("false"))
  	{%>
      <a href="javascript:setParentValue2Singola('<%= DisplayFieldId %>','<%= StringUtils.jsStringEscape(descriptionImpianto) %>','<%= DisplayFieldId2 %>','<%= StringUtils.jsStringEscape(descriptionCategoria) %>');">
        <%= toHtml(descriptionImpianto) %>
      </a>
      <%}
   else 
   {
   %>
    <%= toHtml(descriptionImpianto) %>
   <%} %>
    </td>
    <td valign="center">
      <%= toHtml(descriptionCategoria) %>
    </td>
  </tr>
<%} } else {%>
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
<%
if (tipoSel.equals("true"))
  	{%>
  	<input type = "button" onclick="selezionaElementi()" value = "Seleziona">
  	<%
  	}
  	%>

</body>
