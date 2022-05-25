<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="BufferListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="ListaStato" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaComuni" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>

<body >
<form name="searchTicket" action="Buffer.do?command=Search" method="post" >
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsAllerte.do"><dhv:label name="sanzioniaaa">Ricerca Buffer</dhv:label></a> > 
<dhv:label name="tickets.searchForm">Ricerca Buffer</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="sanzioni.cercass">Ricerca Buffer</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="sanzioni.numerodd">Stato</dhv:label>
    </td>
    <td>
    <%=ListaStato.getHtmlSelect("searchcodeStato",BufferListInfo.getSearchOptionValueAsInt("searchcodeStato")) %>
    </td>
  </tr>
  
    <tr>
    <td class="formLabel">
      <dhv:label name="sanzioni.numerodd">Comuni Coinvolti</dhv:label>
    </td>
    <td>
    <%
    ListaComuni.setMultiple(true);
    ListaComuni.setSelectSize(6);
    %>
    <%=ListaComuni.getHtmlSelect("comuniCoinvolti",-1) %>
    </td>
  </tr>
  
</table>
<br>
<input type="submit" onclick='loadModalWindow();' value="<dhv:label name="button.search">Search</dhv:label>">
</form>
</body>
