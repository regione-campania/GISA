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
  - Version: $Id: accounts_search.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*" %>
<jsp:useBean id="SearchOrgListInfoFcie" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AccountStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="ContactStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function clearForm() {
    <%-- Account Filters --%>
    document.forms['searchAccount'].searchAccountName.value="";
    <dhv:include name="accounts-search-number" none="true">
      document.forms['searchAccount'].searchAccountNumber.value="";
    </dhv:include>
    <dhv:include name="accounts-search-type" none="true">
      document.forms['searchAccount'].listFilter1.options.selectedIndex = 0;
    </dhv:include>
    <dhv:include name="accounts-search-segment" none="true">
      document.forms['searchAccount'].searchAccountSegment.value = "";
      <dhv:evaluate if="<%= SegmentList.size() > 1 %>">
        document.forms['searchAccount'].viewOnlySegmentId.options.selectedIndex = 0;
      </dhv:evaluate>
    </dhv:include>
    /*
    <dhv:evaluate if="<%=StageList.getEnabledElementCount() > 1 %>" >
      document.forms['searchAccount'].searchcodeStageId.options.selectedIndex = 0;
    </dhv:evaluate>
    */
    <dhv:include name="accounts-search-country" none="true">
      document.forms['searchAccount'].searchcodeAccountCountry.options.selectedIndex = 0;
    </dhv:include>
    
    <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 && SiteList.size() > 2 %>" >
      document.forms['searchAccount'].searchcodeOrgSiteId.options.selectedIndex = 0;
    </dhv:evaluate>
    document.forms['searchAccount'].searchAccountName.focus();

    <%-- Contact Filters --%>
    
  }
  
  function fillAccountSegmentCriteria(){
    var index = document.forms['searchAccount'].viewOnlySegmentId.selectedIndex;
    var text = document.forms['searchAccount'].viewOnlySegmentId.options[index].text;
    if (index == 0){
      text = "";
    }
    document.forms['searchAccount'].searchAccountSegment.value = text;
  }

  function updateContacts(countryObj, stateObj, selectedValue) {

    var country = document.forms['searchAccount'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeContactState";
    window.frames['server_commands'].location.href=url;
  }

  function updateAccounts(countryObj, stateObj, selectedValue) {
    var country = document.forms['searchAccount'].elements[countryObj].value;
    var url = "ParafarmacieVet.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeAccountState";
    window.frames['server_commands'].location.href=url;
  }

  function continueUpdateState(stateObj, showText) {
	switch(stateObj){
      case '2':
        if(showText == 'true'){
          hideSpan('state31');
          showSpan('state41');
          document.forms['searchAccount'].searchcodeAccountState.options.selectedIndex = 0;
        } else {
          hideSpan('state41');
          showSpan('state31');
          document.forms['searchAccount'].searchAccountOtherState.value = '';
        }
        break;
	  case '1':
      default:
        if(showText == 'true'){
          hideSpan('state11');
          showSpan('state21');
          document.forms['searchAccount'].searchcodeContactState.options.selectedIndex = 0;
        } else {
          hideSpan('state21');
          showSpan('state11');
          document.forms['searchAccount'].searchContactOtherState.value = '';
        }
        break;
    }
  }
</script>
<dhv:include name="accounts-search-name" none="true">
  <body onLoad="javascript:document.searchAccount.searchAccountName.focus();">
</dhv:include>
<form name="searchAccount" action="ParafarmacieVet.do?command=SearchVet" method="post">
<%-- Trails --%>
<dhv:permission name="parafarmacie-parafarmacie-add">
<a href="ParafarmacieVet.do?command=AddVet"><dhv:label name="">Aggiungi</dhv:label></a>
</dhv:permission>
<dhv:permission name="parafarmacie-parafarmacie-view">
<a href="ParafarmacieVet.do?command=SearchFormVet"><dhv:label name="">Ricerca</dhv:label></a>
</dhv:permission>
</br>
</br>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ParafarmacieVet.do?command=SearchFormVet"><dhv:label name="">Veterinari</dhv:label></a> > 
<dhv:label name="">Cerca Veterinario</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="">Inserire Informazioni per la Ricerca</dhv:label></strong>
          </th>
        </tr>
        <tr>
          <td class="formLabel">
            Cognome
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="searchAccountName" id="searchAccountName" value="<%= SearchOrgListInfoFcie.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="">Comune</dhv:label>
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="searchAccountNumber" id="searchAccountNumber" value="<%= SearchOrgListInfoFcie.getSearchOptionValue("searchAccountNumber") %>">
          </td>
        </tr>
        <dhv:evaluate if="<%= SiteList.size() > 2 %>">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.site">Site</dhv:label>
          </td>
          <td>
           <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 %>" >
            <%= SiteList.getHtmlSelect("searchcodeOrgSiteId", ("".equals(SearchOrgListInfoFcie.getSearchOptionValue("searchcodeOrgSiteId")) ? String.valueOf(Constants.INVALID_SITE) : SearchOrgListInfoFcie.getSearchOptionValue("searchcodeOrgSiteId"))) %>
           </dhv:evaluate>
           <dhv:evaluate if="<%=User.getUserRecord().getSiteId() != -1 %>" >
              <input type="hidden" name="searchcodeOrgSiteId" id="searchcodeOrgSiteId" value="<%= User.getUserRecord().getSiteId() %>">
              <%= SiteList.getSelectedValue(User.getUserRecord().getSiteId()) %>
           </dhv:evaluate>
          </td>
        </tr>
      </dhv:evaluate>  
      <dhv:evaluate if="<%= SiteList.size() <= 2 %>">
        <input type="hidden" name="searchcodeOrgSiteId" id="searchcodeOrgSiteId" value="-1" />
      </dhv:evaluate>
      <dhv:include name="accounts-search-type" none="true">
        <tr>
          <td class="formLabel">
            <dhv:label name="accounts.type">Account Type</dhv:label>
          </td>
          <td>
            <%= TypeSelect.getHtmlSelect("listFilter1", SearchOrgListInfoFcie.getFilterKey("listFilter1")) %>
          </td>
        </tr>
        </dhv:include>
        <dhv:include name="accounts-search-segment" none="true">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_search.accountSegment">Account Segment</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountSegment" value="<%= SearchOrgListInfoFcie.getSearchOptionValue("searchAccountSegment") %>">
            <dhv:evaluate if="<%= SegmentList.size() > 1 %>">
              <% SegmentList.setJsEvent("onchange=\"javascript:fillAccountSegmentCriteria();\"");%>
              <%= SegmentList.getHtmlSelect("viewOnlySegmentId", -1) %>
            </dhv:evaluate>
          </td>
        </tr>
        </dhv:include>
        <dhv:include name="accounts-search-source" none="true">
        <tr style="display: none">
          <td class="formLabel">
            <dhv:label name="">Account Source</dhv:label>
          </td>
          <td align="left" valign="bottom">
            <select size="1" name="listView">
              <option <%= SearchOrgListInfoFcie.getOptionValue("all") %>><dhv:label name="accounts.all.accounts">All Accounts</dhv:label></option>
              <option <%= SearchOrgListInfoFcie.getOptionValue("my") %>><dhv:label name="accounts.my.accounts">My Accounts</dhv:label></option>
            </select>
          </td>
        </tr>
        </dhv:include>
        <tr>
          <td class="formLabel">
            <dhv:label name="">Stato Farmaci</dhv:label>
          </td>
          <td align="left" valign="bottom">
            <select size="1" name="listFilter2">
              <option value="-1" <%=(SearchOrgListInfoFcie.getFilterKey("listFilter2") == -1)?"selected":""%>><dhv:label name="">Qualsiasi</dhv:label></option>
              <option value="1" <%=(SearchOrgListInfoFcie.getFilterKey("listFilter2") == 1)?"selected":""%>><dhv:label name="">Veterinari Attivi</dhv:label></option>
              <option value="0" <%=(SearchOrgListInfoFcie.getFilterKey("listFilter2") == 0)?"selected":""%>><dhv:label name="">Veterinari Inattivi</dhv:label></option>
            </select>
          </td>
        </tr>
        <dhv:include name="organization.stage" none="true">
        <dhv:evaluate if="<%= StageList.getEnabledElementCount() > 1 %>">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Stage</dhv:label>
          </td>
          <td>
            <%= StageList.getHtmlSelect("searchcodeStageId", SearchOrgListInfoFcie.getSearchOptionValueAsInt("searchcodeStageId")) %>
          </td>
        </tr>
      </dhv:evaluate>  
      </dhv:include>
      </table>
    </td>
  </tr>
</table>
<dhv:include name="accounts-search-contacts" none="false">
  <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfoFcie.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="accounts.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
  <br />
</dhv:include>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script type="text/javascript">
  </script>
</form>
</body>