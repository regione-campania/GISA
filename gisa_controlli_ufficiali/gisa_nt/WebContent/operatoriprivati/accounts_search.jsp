<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<% if (1==1) { %>
<%@ include file="/ricercaunica/ricercaDismessa.jsp" %>
<%} else { %>

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
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AccountStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="ContactStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>

<script language="JavaScript">
  function clearForm() {
    <%-- Account Filters --%>
	document.forms['searchAccount'].searchAccountName.value="";
    document.forms['searchAccount'].searchAccountOtherState.value="";
    document.forms['searchAccount'].searchAccountCity.value="";
    continueUpdateState('2','true');
    document.forms['searchAccount'].searchcodeAccountState.options.selectedIndex = 0;
    
    document.forms['searchAccount'].searchAccountName.focus();
    document.forms['searchAccount'].searchcodiceAllerta.value="";  
    
    <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 && SiteList.size() > 2 %>" >
      document.forms['searchAccount'].searchcodeOrgSiteId.options.selectedIndex = 0;
    </dhv:evaluate>
     <%-- Contact Filters --%>
    <dhv:include name="operatoriprivati-contact-information-filters" none="true">
    document.forms['searchAccount'].searchContactPhoneNumber.value="";
     document.forms['searchAccount'].searchcodeContactState.options.selectedIndex = 0;
      document.forms['searchAccount'].searchContactOtherState.value = '';
       document.forms['searchAccount'].searchContactCity.value="";
      continueUpdateState('1','true');
      //document.forms['searchAccount'].searchcodeContactCountry.options.selectedIndex = 0;
      
    </dhv:include>
    
    <dhv:include name="operatoriprivati-search-number" none="true">
     // document.forms['searchAccount'].searchAccountNumber.value="";
    </dhv:include>
    <dhv:include name="operatoriprivati-tipo-stab" none="true">
      //document.forms['searchAccount'].tipoAbus.value="";
    </dhv:include>
    <dhv:include name="operatoriprivati-search-type" none="true">
     // document.forms['searchAccount'].listFilter1.options.selectedIndex = 0;
    </dhv:include>
    <dhv:include name="operatoriprivati-search-segment" none="true">
      //document.forms['searchAccount'].searchAccountSegment.value = "";
      <dhv:evaluate if="<%= SegmentList.size() > 1 %>">
        //document.forms['searchAccount'].viewOnlySegmentId.options.selectedIndex = 0;
      </dhv:evaluate>
    </dhv:include>
    <dhv:include name="operatoriprivati-search-source" none="true">
      //document.forms['searchAccount'].listView.options.selectedIndex = 0;
    </dhv:include>
    
    /*
    <dhv:evaluate if="<%=StageList.getEnabledElementCount() > 1 %>" >
      document.forms['searchAccount'].searchcodeStageId.options.selectedIndex = 0;
    </dhv:evaluate>
    */
    <dhv:include name="operatoriprivati-search-country" none="true">
      //document.forms['searchAccount'].searchcodeAccountCountry.options.selectedIndex = 0;
    </dhv:include>
    
    <dhv:include name="operatoriprivati-search-asset-serial" none="true">
     // document.forms['searchAccount'].searchcodeAssetSerialNumber.value="";
    </dhv:include>
      }
      
      
  function fillAccountSegmentCriteria(){
    var index = document.forms['searchAccount'].viewOnlySegmentId.selectedIndex;
    var text = document.forms['searchAccount'].viewOnlySegmentId.options[index].text;
    if (index == 0){
      text = "";
    }
    //document.forms['searchAccount'].searchAccountSegment.value = text;
  }

  function updateContacts(countryObj, stateObj, selectedValue) {

    var country = document.forms['searchAccount'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeContactState";
    window.frames['server_commands'].location.href=url;
  }

  function updateAccounts(countryObj, stateObj, selectedValue) {
    var country = document.forms['searchAccount'].elements[countryObj].value;
    var url = "Operatoriprivati.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeAccountState";
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
<dhv:include name="operatoriprivati-search-name" none="true">
  <body onLoad="javascript:document.searchAccount.searchAccountName.focus();clearForm();">
</dhv:include>
<form name="searchAccount" action="RicercaUnica.do?command=Search" method="post">
<%-- Trails --%>
<table>
<tr>
<td>
    <dhv:permission name="operatoriprivati-operatoriprivati-add"><a href="Operatoriprivati.do?command=Add"><dhv:label name="">Aggiungi</dhv:label></a></dhv:permission>
</td>
<td>
    <dhv:permission name="operatoriprivati-operatoriprivati-view"><a href="Operatoriprivati.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a></dhv:permission>
</td>
</tr>
</table>
<table class="trails" cellspacing="0">
<tr>
<td>
<!--<a href="AltriOperatori.do?command=DashboardScelta"><dhv:label name="">Altri Operatori</dhv:label></a> >--> 
<a href="Operatoriprivati.do"><dhv:label name="">Privati</dhv:label></a> > 
<dhv:label name="operatoriprivati.search">Cerca Privato</dhv:label>
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
            <strong><dhv:label name="abusivismi.accountInformationFiltersa">Ricerca Rapida Operatori Privati</dhv:label></strong>
          </th>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="organization.namea">Cognome</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchragioneSociale" value="<%= SearchOrgListInfo.getSearchOptionValue("searchragioneSociale") %>">
          </td>
        </tr>
        
       
                 
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Comune</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchcomuneSedeProduttiva" value="<%= SearchOrgListInfo.getSearchOptionValue("searchcomuneSedeProduttiva") %>">
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="abusivismi.abusivismi_add.StateProvince">State/Province</dhv:label>
          </td>
          <td>
            <span name="state31" ID="state31" style="<%= AccountStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry"))? "" : " display:none" %>">
              <%= AccountStateSelect.getHtmlSelect("searchcodeAccountState", SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry"),SearchOrgListInfo.getSearchOptionValue("searchcodeAccountState")) %>
            </span>
            <%-- If selected country is not US/Canada use textfield --%>
            <span name="state41" ID="state41" style="<%= !AccountStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry")) ? "" : " display:none" %>">
              <input type="text" size="23" name="searchAccountOtherState"  value="<%= toHtmlValue(SearchOrgListInfo.getSearchOptionValue("searchAccountOtherState")) %>">
            </span>
          </td>
        </tr>
        
      <dhv:evaluate if="<%= SiteList.size() > 2 %>">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.site">Site</dhv:label>
          </td>
          <td>
           <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 %>" >
            <%= SiteList.getHtmlSelect("searchcodeOrgSiteId", ("".equals(SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")) ? String.valueOf(Constants.INVALID_SITE) : SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId"))) %>
           </dhv:evaluate>
           <dhv:evaluate if="<%=User.getUserRecord().getSiteId() != -1 %>" >
              <input type="hidden" name="searchcodeOrgSiteId" value="<%= User.getUserRecord().getSiteId() %>">
              <%= SiteList.getSelectedValue(User.getUserRecord().getSiteId()) %>
           </dhv:evaluate>
          </td>
        </tr>
      </dhv:evaluate> 
       <tr style="display:none">
          <td nowrap class="formLabel">
            <dhv:label name="">Codice Allerta</dhv:label>
          </td>
          <td>
          <input type="hidden" id="ticketid" value="0" name="ticketidd">
   
           <input style="background-color: lightgray" readonly="readonly" type="text" size="20" value="<%= SearchOrgListInfo.getSearchOptionValue("searchIdAllerta") %>" id="id_allerta" name="searchcodiceAllerta" >
      &nbsp;[<a href="javascript:popLookupSelectorAllertaRicerca('id_allerta','name','ticket','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
       </td>
        </tr> 
      <dhv:evaluate if="<%= SiteList.size() <= 2 %>">
        <input type="hidden" name="searchcodeOrgSiteId" id="searchcodeOrgSiteId" value="-1" />
      </dhv:evaluate>
       
      </table>
    </td>

  </tr>
</table>

<input type="hidden" id="searchAttivita" name="searchAttivita" value="PRIVATI"/>
<dhv:include name="operatoriprivati-search-contacts" none="false">
  <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="operatoriprivati.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
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

<% } %>
