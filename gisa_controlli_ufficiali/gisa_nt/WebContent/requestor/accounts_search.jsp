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
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
function cambiaAttivitaImpresa(){
	if(document.forms['searchAccount'].searchAccountCity.value != ""){
		document.getElementById("attivita_impresa").style.display = "block";
		document.getElementById("tipo_impresa_1").checked = "checked";
	}else{
		document.getElementById("attivita_impresa").style.display = "none";
		if(document.getElementById("tipo_impresa_1").checked = "checked")
			document.getElementById("tipo_impresa_1").checked = false;
		if(document.getElementById("tipo_impresa_2").checked = "checked")
			document.getElementById("tipo_impresa_2").checked = false;
		if(document.getElementById("tipo_impresa_3").checked = "checked")
			document.getElementById("tipo_impresa_3").checked = false;
	}
}

function cambiaAddressType(i){
	  document.forms['searchAccount'].searchcodeAddressType.value = i;
}

  function clearForm() {
    <%-- Account Filters --%>
       
    document.forms['searchAccount'].searchAccountName.value="";
    document.forms['searchAccount'].targaVeicolo.value="";
    document.forms['searchAccount'].searchCognomeRappresentante.value="";
    document.forms['searchAccount'].searchNomeRappresentante.value="";
    document.forms['searchAccount'].codiceFiscale.value="";
    document.forms['searchAccount'].searchPartitaIva.value="";
    document.forms['searchAccount'].searchCodiceFiscaleCorrentista.value="";
        
   
   
  }
  

</script>
  <body onLoad="javascript:document.searchAccount.searchAccountName.focus();clearForm();">

<form name="searchAccount" action="Requestor.do?command=Search" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Requestor.do"><dhv:label name="requestor.requestor">Accounts</dhv:label></a> > 
<dhv:label name="requestor.search">Search Accounts</dhv:label>
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
            <strong><dhv:label name="requestor.contactInformationFilters">Account Information Filters</dhv:label></strong>
          </th>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="organization.name">Nome</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
        
        
        
        
        <!--  Form di inserimento del codice
        <dhv:include name="accounts-search-number" none="true">
        <tr>
          <td class="formLabel">
            <dhv:label name="organization.accountNumber">Account Number</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountNumber" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountNumber") %>">
          </td>
        </tr>
        -->
        
      
        </dhv:include>
        
        <tr id="list2" >
    		<td class="formLabel" nowrap name="targaVeicolo1" id="targaVeicolo1">
      			<dhv:label name="">Targa Autoveicolo</dhv:label>
   			 </td>
   			 <td>
      			<input id="targaVeicolo" type="text" size="20" maxlength="10" name="searchNomeCorrentista" value="<%= SearchOrgListInfo.getSearchOptionValue("searchNomeCorrentista") %>">
    		 </td>
  		</tr>
        
        <tr>
          <td class="formLabel">
            <dhv:label name="">Titolare o Legale Rappresentante</dhv:label>
          </td>
           <td>
           <table class = "noborder">
           <tr><td> Cognome <input type="text" size="23" name="searchCognomeRappresentante" value="<%= SearchOrgListInfo.getSearchOptionValue("searchCognomeRappresentante") %>"></td>
           
           <td>Nome <input type="text" size="23" name="searchNomeRappresentante" value="<%= SearchOrgListInfo.getSearchOptionValue("searchNomeRappresentante") %>"></td>
           </tr>
           </table>
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="">Codice Fiscale Impresa</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="codiceFiscale" value="<%= SearchOrgListInfo.getSearchOptionValue("codiceFiscale") %>">
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
        
        
        <%-- <tr>
          <td class="formLabel">
            <dhv:label name="">Targa</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="targa" value="<%= SearchOrgListInfo.getSearchOptionValue("targa") %>">
          </td>
        </tr>--%>
        
        <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Codice Istat Principale</dhv:label>
    </td>
    <td>
      <input type="text" size="20" name="searchCodiceFiscaleCorrentista" id ="searchCodiceFiscaleCorrentista" value="<%= SearchOrgListInfo.getSearchOptionValue("searchCodiceFiscaleCorrentista")%>">     
       &nbsp;[<a href="javascript:popLookupSelectorCustom('searchCodiceFiscaleCorrentista','alertText','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      </br>
       <input type="hidden" size="50" id="alertText" name="alertText" value="">
      
    </td>
  </tr>
        
      
        <dhv:include name="accounts-search-source" none="true">
        <tr style="display: none">
          <td class="formLabel">
            <dhv:label name="requestor.requestorSource">Account Source</dhv:label>
          </td>
          <td align="left" valign="bottom">
            <select size="1" name="listView">
              <option <%= SearchOrgListInfo.getOptionValue("all") %>><dhv:label name="requestor.all.requestor">All Accounts</dhv:label></option>
              <option <%= SearchOrgListInfo.getOptionValue("my") %>><dhv:label name="requestor.my.requestor">My Accounts</dhv:label></option>
            </select>
          </td>
        </tr>
        </dhv:include>
           <%ComuniList.setJsEvent("onchange=\"javascript:cambiaAttivitaImpresa();\""); %>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="requestor.requestor_add.City">City</dhv:label>
          </td>
          <td> 
	<%= ComuniList.getHtmlSelectText("searchAccountCity",SearchOrgListInfo.getSearchOptionValue("searchAccountCity")) %>
	<div id="attivita_impresa" style="display: none;">Attività: 
	<input name="searchTipoDest" id="tipo_impresa_1" type="radio" value="Es. Commerciale" onclick="javascript:cambiaAddressType(5);"/>Fissa
	<input name="searchTipoDest" id="tipo_impresa_2" type="radio" value="Autoveicolo" onclick="javascript:cambiaAddressType(1);"/>Mobile
	<input name="searchTipoDest" id="tipo_impresa_3" type="radio" value="Distributori" onclick="javascript:cambiaAddressType(1);"/>Distributori
	<input name="searchcodeAddressType" id="tipo_impresa_4" type="hidden" value="5"/>
	</div>
	</td>
  	</tr>	
       
   
         
      <dhv:evaluate if="<%= SiteList.size() > 2 %>">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="requestor.site">Site</dhv:label>
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
      
      <dhv:evaluate if="<%= SiteList.size() <= 2 %>">
        <input type="hidden" name="searchcodeOrgSiteId" id="searchcodeOrgSiteId" value="-1" />
      </dhv:evaluate>
        <%--
        <tr>
          <td class="formLabel">
            <dhv:label name="global.trashed">Trashed</dhv:label>
          </td>
          <td>
            <input type="checkbox" name="searchcodeIncludeOnlyTrashed" value="true" <%= "true".equals(SearchOrgListInfo.getSearchOptionValue("searchcodeIncludeOnlyTrashed"))? "checked":""%> />
          </td>
        </tr>
        --%>
      </table>
    </td>
<dhv:include name="accounts-contact-information-filters" none="true">
    <td width="50%" valign="top">
      <table style="display: none;" cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="requestor.contactInformationFilters">Contact Information Filters</dhv:label></strong>
          </th>
        </tr>
        <dhv:include name="accounts-search-firstname" none="true">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="requestor.requestor_add.FirstName">First Name</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchFirstName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchFirstName") %>">
          </td>
        </tr>
        </dhv:include>
        <dhv:include name="accounts-search-lastname" none="true">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="contacts.LastName">Last Name</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchLastName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchLastName") %>">
          </td>
        </tr>
        </dhv:include>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="requestor.requestor_add.Phone">Phone</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchContactPhoneNumber" value="<%= SearchOrgListInfo.getSearchOptionValue("searchContactPhoneNumber") %>">
          </td>
        </tr>
        
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="requestor.requestor_add.City">City</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchContactCity" value="<%= SearchOrgListInfo.getSearchOptionValue("searchContactCity") %>">
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
          </td>
          <td>
            <span name="state11" ID="state11" style="<%= ContactStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeContactCountry"))? "" : " display:none" %>">
              <%= ContactStateSelect.getHtmlSelect("searchcodeContactState", SearchOrgListInfo.getSearchOptionValue("searchcodeContactCountry"),SearchOrgListInfo.getSearchOptionValue("searchcodeContactState")) %>
            </span>
            <%-- If selected country is not US/Canada use textfield --%>
            <span name="state21" ID="state21" style="<%= !ContactStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeContactCountry")) ? "" : " display:none" %>">
              <input type="text" size="23" name="searchContactOtherState"  value="<%= toHtmlValue(SearchOrgListInfo.getSearchOptionValue("searchContactOtherState")) %>">
            </span>
          </td>
        </tr>
      </table>
    </td>
</dhv:include>
  </tr>
</table>
<dhv:include name="accounts-search-contacts" none="false">
  <input type="checkbox" name="searchContacts" value="true" <%="true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="requestor.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
  <br />
</dhv:include>
<input type="submit" onclick='loadModalWindow();' value="<dhv:label name="button.search">Search</dhv:label>">
<!--  
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
-->
<input type="button" onClick="javascript:clearForm();" value="<dhv:label name="button.clear" >Clear</dhv:label>" >
<input type="hidden" name="source" value="searchForm">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script type="text/javascript">
  </script>
</form>
</body>
