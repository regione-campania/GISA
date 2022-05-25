<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<% if (1==1) { %>
<%@ include file="/ricercaunica/ricercaDismessa.jsp" %>
<%} else { %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*" %>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.imbarcazioni.base.Organization" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCheckList.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
<%@ include file="../initPage.jsp" %>
<script>

function clearForm()
{
//document.searchAccount.searchcodeOrgSiteId.value="-2";
document.searchAccount.searchAccountName.value = "" ;
document.searchAccount.searchAccountCity.value = "" ;
document.searchAccount.searchAccountNumber.value = "" ;
document.searchAccount.searchTaxid.value = "" ;



	}
</script>

<body onload="clearForm()">
<form name="searchAccount" action="Imbarcazioni.do?command=Search" method="post">


<table>
<tr>
<td>
    <dhv:permission name="imbarcazioni-imbarcazioni-add"><a href="Imbarcazioni.do?command=Add"><dhv:label name="">Aggiungi</dhv:label></a></dhv:permission>
</td>
<td>
    <dhv:permission name="imbarcazioni-imbarcazioni-view"><a href="Imbarcazioni.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a></dhv:permission>
</td>
</tr>
</table>

<table class="trails" cellspacing="0">
<tr>
<td>
<!-- <a href="AltriOperatori.do?command=DashboardScelta">Imbarcazioni</a> > -->
Ricerca
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="2" cellspacing="2" border="0" width="50%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Ricerca rapida Imbarcazioni</strong>
          </th>
        </tr>
        <tr>
          <td class="formLabel">
            Impresa
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="searchAccountName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
         <tr>
          <td class="formLabel">
            Identificativo barca U.E.
          </td>
          <td>
            <input  type="text" maxlength="50" size="50" name="searchAccountNumber" value="">
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Numero registrazione impresa (ex 852)
          </td>
          <td>
            <input  type="text" maxlength="50" size="50" name="searchTaxid" value="">
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            Ormeggio abituale
          </td>
          <td>
            <%= ComuniList.getHtmlSelectText("searchAccountCity",SearchOrgListInfo.getSearchOptionValue("searchAccountCity")) %>
          </td>
        </tr>
          
      </table>
    </td>
  </tr>
</table>

<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
</form>
</body>


<% } %>