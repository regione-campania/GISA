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
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AccountStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="ContactStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<script language="JavaScript">
  function clearForm() {

    <%-- Account Filters --%>
     document.forms['searchAccount'].searchAccountName.value="";
     document.forms['searchAccount'].searchCognomeRappresentante.value="";
     document.forms['searchAccount'].codiceFiscale.value="";
     document.forms['searchAccount'].searchNomeRappresentante.value="";
     document.forms['searchAccount'].searchPartitaIva.value="";
     document.forms['searchAccount'].searchNomeCorrentista.value="";
   	 document.forms['searchAccount'].searchAccountNumber.value="";
   	document.forms['searchAccount'].searchNomeCorrentista.value="";
   	document.forms['searchAccount'].searchAccountCity.value="";
   	document.forms['searchAccount'].searchcodeOrgSiteId.value="-2";
   	document.forms['searchAccount'].searchcodeAccountCountry.value="-1";
   	
     <dhv:include name="accounts-search-type" none="true">
      document.forms['searchAccount'].listFilter1.options.selectedIndex = 0;
    </dhv:include>
    <dhv:include name="accounts-search-segment" none="true">
      document.forms['searchAccount'].searchAccountSegment.value = "";
      <dhv:evaluate if="<%= SegmentList.size() > 1 %>">
        document.forms['searchAccount'].viewOnlySegmentId.options.selectedIndex = 0;
      </dhv:evaluate>
    </dhv:include>
    <dhv:include name="accounts-search-source" none="true">
      document.forms['searchAccount'].listView.options.selectedIndex = 0;
    </dhv:include>
     /*
    <dhv:evaluate if="<%=StageList.getEnabledElementCount() > 1 %>" >
      document.forms['searchAccount'].searchcodeStageId.options.selectedIndex = 0;
    </dhv:evaluate>
    */
    <dhv:include name="accounts-search-country" none="true">
      document.forms['searchAccount'].searchcodeAccountCountry.options.selectedIndex = 0;
    </dhv:include>
   // document.forms['searchAccount'].searchAccountPostalCode.value="";
    document.forms['searchAccount'].searchAccountCity.value="";
    continueUpdateState('2','true');
    <dhv:include name="accounts-search-asset-serial" none="true">
      document.forms['searchAccount'].searchcodeAssetSerialNumber.value="";
    </dhv:include>
    <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 && SiteList.size() > 2 %>" >
          </dhv:evaluate>
    
    <%-- Contact Filters --%>	
    <dhv:include name="accounts-contact-information-filters" none="true">
      //document.forms['searchAccount'].searchFirstName.value="";
      //document.forms['searchAccount'].searchLastName.value="";
      //document.forms['searchAccount'].searchContactPhoneNumber.value="";
      //document.forms['searchAccount'].searchContactCity.value="";
      continueUpdateState('1','true');
      //document.forms['searchAccount'].searchcodeContactCountry.options.selectedIndex = 0;
      //document.forms['searchAccount'].searchcodeContactState.options.selectedIndex = 0;
      //document.forms['searchAccount'].searchContactOtherState.value = '';
    </dhv:include>
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
    var url = "Accounts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeAccountState";
    window.frames['server_commands'].location.href=url;
  }

  function continueUpdateState(stateObj, showText) {
	switch(stateObj){
      case '2':
        if(showText == 'true'){
          hideSpan('state31');
          showSpan('state41');
          //document.forms['searchAccount'].searchcodeAccountState.options.selectedIndex = 0;
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
          //document.forms['searchAccount'].searchcodeContactState.options.selectedIndex = 0;
        } else {
          hideSpan('state21');
          showSpan('state11');
          //document.forms['searchAccount'].searchContactOtherState.value = '';
        }
        break;
    }
  }

</script>
<dhv:include name="accounts-search-name" none="true">
  <body onLoad="javascript:document.searchAccount.searchAccountName.focus();clearForm();">
</dhv:include>
<form name="searchAccount" action="OperatoriFuoriRegione.do?command=Search" method="post">
<%-- Trails --%>
<dhv:permission name="operatoriregione-operatoriregione-add"><a href="OperatoriFuoriRegione.do?command=Add&tipoD=Autoveicolo"><dhv:label name="">Aggiungi Impresa Fuori Ambito ASL</dhv:label></a></dhv:permission>
<%-- <a href="OperatoriFuoriRegione.do?command=SearchForm&tipoD=Distributori"><dhv:label name="">Ricerca</dhv:label></a> --%>


</br>
</br>
<table class="trails" cellspacing="0">
<tr>
<td>

<!--<a href="AltriOperatori.do?command=DashboardScelta"><dhv:label name="">Altri Operatori</dhv:label></a> >--> 
<a href="Distributori.do?command=ScegliD"><dhv:label name="">Imprese Fuori Ambito ASL</dhv:label></a> > 
<dhv:label name="">Ricerca Imprese Erogatrici di Distributori Automatici Registrate Altre ASL della Campania</dhv:label>
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
            <strong><dhv:label name="accounts.accountInformationFilters">Account Information Filters</dhv:label></strong>
          </th>
        </tr>
        
        <tr>
          <td class="formLabel">
          Tipo
          </td>
          <td>
           <select id="searchTipoD" name="searchTipoD">
           <option value="Autoveicolo">Autoveicolo</option>
           <option <%=(request.getParameter("cambio_distr")!=null) ? "selected" : "" %> value="Distributori">Distributori</option>
           <!-- <option value="Operatori">Operatori</option>-->
           </select> 
          </td>
        </tr>
         
        <tr>
          <td class="formLabel">
            <dhv:label name="organization.name">Account Name</dhv:label>
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="searchAccountName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
       
        <tr>
          <td class="formLabel">
            <dhv:label name="organization.accountNumber">Account Number</dhv:label>
          </td>
          <td>
            <input  type="text" maxlength="50" size="50" name="searchAccountNumber" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountNumber") %>">
          </td>
        </tr>
       
 
        <tr>
          <td class="formLabel">
            <dhv:label name="">Titolare o Legale Rappresentante</dhv:label>
          </td>
           <td>
           Cognome &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nome
          </br>
            <input type="text" size="23" name="searchCognomeRappresentante" value="<%= SearchOrgListInfo.getSearchOptionValue("searchCognomeRappresentante") %>">
            <input type="text" size="23" name="searchNomeRappresentante" value="<%= SearchOrgListInfo.getSearchOptionValue("searchNomeRappresentante") %>">
          </td>
        </tr>
        
       
        
        
        
        
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">Codice Fiscale Impresa</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="codiceFiscale" value="<%= SearchOrgListInfo.getSearchOptionValue("searchCodiceFiscale") %>">
          </td>
        </tr>
         <tr>
          <td class="formLabel">
            Partita IVA
          </td>
          <td>
            <input type="text" size="23" name="searchPartitaIva" value="<%= SearchOrgListInfo.getSearchOptionValue("searchPartitaIva") %>">
          </td>
        </tr>
        
         <tr>
          <td class="formLabel">
            <dhv:label name="">Identificativo Veicolo</dhv:label>
          </td>
           <td>
           
            <input type="text" size="23" name="searchNomeCorrentista" value="<%= SearchOrgListInfo.getSearchOptionValue("searchNomeCorrentista") %>">
          </td>
        </tr>
 
        <dhv:include name="organization.stage" none="true">
        <dhv:evaluate if="<%= StageList.getEnabledElementCount() > 1 %>">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="account.stage">Stage</dhv:label>
          </td>
          <td>
            <%= StageList.getHtmlSelect("searchcodeStageId", SearchOrgListInfo.getSearchOptionValueAsInt("searchcodeStageId")) %>
          </td>
        </tr>
      </dhv:evaluate>  
      </dhv:include>
      <dhv:evaluate if="<%= SiteList.getEnabledElementCount() <= 1 %>">
        <input type="hidden" name="searchcodeStageId" id="searchcodeStageId" value="-1" />
      </dhv:evaluate>
     

      
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.City">City</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountCity" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountCity") %>">
          </td>
        </tr>
      
        
    
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.site">Site</dhv:label>
          </td>
          <td>
        
     
<!--              <input type="hidden" name="searchcodeOrgSiteId" value="16">-->
              <%= SiteList.getHtmlSelect("searchcodeOrgSiteId",-2) %>
      
          </td>
        </tr>
     
     
        
      </table>
    </td>
<dhv:include name="accounts-contact-information-filters" none="true">
    <td width="30%" valign="top">
    
    </td>
</dhv:include>
  </tr>
</table>
<dhv:include name="accounts-search-contacts" none="false">
  <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
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

