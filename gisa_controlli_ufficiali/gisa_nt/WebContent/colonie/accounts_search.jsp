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
<%@ page import="org.aspcfs.modules.login.beans.UserBean, org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*" %>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AccountStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="ContactStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="aslRifList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"></script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script language="JavaScript"><!--
  function clearForm() {
    <%-- Account Filters --%>
    document.forms['searchAccount'].searchAccountName.value="";
    <dhv:include name="accounts-search-number" none="true">
      document.forms['searchAccount'].searchAccountNumber.value="";
    </dhv:include>
    <dhv:include name="accounts-search-type" none="true">
      document.forms['searchAccount'].listFilter1.options.selectedIndex = 0;
      updateSearch();
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
    document.forms['searchAccount'].listFilter2.options.selectedIndex = 0;
    <dhv:include name="accounts-search-country" none="true">
      document.forms['searchAccount'].searchcodeAccountCountry.options.selectedIndex = 0;
    </dhv:include>

    document.forms['searchAccount'].searchCodiceFiscale.value="";
    document.forms['searchAccount'].partitaIva.value="";
    
    document.forms['searchAccount'].searchcodeAutorizzato[0].checked = false;
    document.forms['searchAccount'].searchcodeAutorizzato[1].checked = false;
    document.forms['searchAccount'].searchcodeAutorizzato[2].checked = true;
    
    document.forms['searchAccount'].searchAccountPostalCode.value="";
    document.forms['searchAccount'].searchAccountCity.options.selectedIndex = -1;
    continueUpdateState('2','true');
    document.forms['searchAccount'].searchcodeAccountState.options.selectedIndex = 0;
    document.forms['searchAccount'].searchAccountOtherState.value = '';
    <dhv:include name="accounts-search-asset-serial" none="true">
      document.forms['searchAccount'].searchcodeAssetSerialNumber.value="";
    </dhv:include>
    <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 && SiteList.size() > 2 %>" >
      document.forms['searchAccount'].searchcodeOrgSiteId.options.selectedIndex = 0;
    </dhv:evaluate>
    document.forms['searchAccount'].searchAccountName.focus();

    <%-- Contact Filters --%>
    <dhv:include name="accounts-contact-information-filters" none="true">
      continueUpdateState('1','true');
    </dhv:include>
  }

function popolaComboComuni(idAsl)
{
	idAsl = document.searchAccount.searchcodeOrgSiteId.value;
	
		PopolaCombo.getValoriComboComuniAsl(idAsl,setComuniComboCallback) ;
	
}

function setComuniComboCallback(returnValue)
{
	  var select = document.forms['searchAccount'].searchCity; //Recupero la SELECT
    

    //Azzero il contenuto della seconda select
    for (var i = select.length - 1; i >= 0; i--)
  	  select.remove(i);

    indici = returnValue [0];
    valori = returnValue [1];
    //Popolo la seconda Select
    for(j =0 ; j<indici.length; j++){
    //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
    var NewOpt = document.createElement('option');
    NewOpt.value = indici[j]; // Imposto il valore
    NewOpt.text = valori[j]; // Imposto il testo

    //Aggiungo l'elemento option
    try
    {
  	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
    }catch(e){
  	  select.add(NewOpt); // Funziona solo con IE
    }
    }


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
        } else {
          hideSpan('state21');
          showSpan('state11');
          document.forms['searchAccount'].searchContactOtherState.value = '';
        }
        break;
    }
  }
   
  function $id(id) { 
   		return (document.getElementById)? document.
 		getElementById(id) : document.all[id];
	}

	function mostraBox(id) {
		 $id(id).style.display = 'block';
	}

	function nascondiBox(id) { 
		$id(id).style.display = 'none';
	}
  
  function checkProv(provincia) {
  	if ( provincia == "NA" ){
  		return "NAPOLI";
  	}
  	if ( provincia == "BN" ){
  		return "BENEVENTO";
  	}
  	if ( provincia == "CE" ){
  		return "CASERTA";
  	}
  	if ( provincia == "SA" ){
  		return "SALERNO";
  	}
  	if ( provincia == "AV" ){
  		return "AVELLINO";
  	}
  }

  
  
	function doCheck(form) {
		formTest = true;
    	message = "";
    	if (form.searchAccountCodiceFiscale) {
    		if (form.searchAccountCodiceFiscale.value) {
  				if (form.searchAccountCodiceFiscale.value.length <16) {
  					message += label("","- Codice fiscale proprietario non valido, controlla la lunghezza.\r\n");
  					formTest = false;
  				}
  			}
  		}
  		if (formTest == false) {
      		alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      		return false;
    	}
  	}
	/*
	function updateSearch() {
		if ( document.searchAccount.listFilter1.value == 11 ) {
			mostraBox('abus');
		}
		else
			
			{
			nascondiBox('abus');
		}
	}*/


	function updateSearch() {
		if ( document.searchAccount.listFilter1.value == 11 ) {
			//mostraBox('abus');
			mostraBox('autor');
			document.searchAccount.searchAccountName.disabled=false;
			document.searchAccount.partitaIva.disabled=false;
			document.searchAccount.searchCodiceFiscale.disabled=false;
			document.searchAccount.aslRif.disabled=false;
		
		}
		else
			if(document.searchAccount.listFilter1.value == 21){
				//	nascondiBox('abus');
					nascondiBox('autor');
					document.searchAccount.searchAccountName.disabled=true;
					document.searchAccount.partitaIva.disabled=true;
					document.searchAccount.searchCodiceFiscale.disabled=true;
					document.searchAccount.aslRif.disabled=true;
					document.searchAccount.aslRif.value=0;				}
			else
		{
		//	nascondiBox('abus');
			nascondiBox('autor');
			document.searchAccount.searchAccountName.disabled=false;
			document.searchAccount.partitaIva.disabled=false;
			document.searchAccount.searchCodiceFiscale.disabled=false;
			document.searchAccount.aslRif.disabled=false;
		}
	}
	
--></script>
<dhv:include name="accounts-search-name" none="true">
  <body onLoad="javascript:document.searchAccount.searchAccountName.focus();clearForm();">
</dhv:include>
<form name="searchAccount" action="Colonie.do?command=Search" method="post" onSubmit="return doCheck(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td><a href="Colonie.do">Colonie</a> > Cerca Colonie</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="100%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Ricerca Rapida Colonie</strong>
          </th>
        </tr>
        <tr>
          <td class="formLabel">
            Progressivo Colonia
          </td>
          <td>
            <input type="text" size="23" name="searchAccountName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Codice Fiscale
          </td>
          <td>
            <input type="text" size="23"  maxlength="16" name="searchCodiceFiscaleRappresentante" value="<%= SearchOrgListInfo.getSearchOptionValue("searchCodiceFiscaleRappresentante") %>">
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
        <td nowrap class="formLabel" name="province1" id="prov2">
      		<dhv:label name="requestor.requestor_add.City">City</dhv:label>
    	</td> 
    
    	<td> 
		<%= ComuniList.getHtmlSelectText("searchCity",SearchOrgListInfo.getSearchOptionValue("searchCity")) %>
		</td>
		</tr>
        
        <tr style="display:none" class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label>
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
           <%
           
           SiteList.setJsEvent("onChange=popolaComboComuni()");
           %>
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
<!-- RICERCA PER RIFERIMENTO NON VISUALIZZATA!! -->
<%--
<dhv:include name="accounts-contact-information-filters" none="true">
    <td width="50%" valign="top">
      <table style="display:none" cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="accounts.contactInformationFilters">Contact Information Filters</dhv:label></strong>
          </th>
        </tr>
        <dhv:include name="accounts-search-name" none="true">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchFirstName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchFirstName") %>">
          </td>
        </tr>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchLastName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchLastName") %>">
          </td>
        </tr>
        </dhv:include>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.Phone">Phone</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchContactPhoneNumber" value="<%= SearchOrgListInfo.getSearchOptionValue("searchContactPhoneNumber") %>">
          </td>
        </tr>
        
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Indirizzo</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchIndirizzoContact" value="<%= SearchOrgListInfo.getSearchOptionValue("searchIndirizzoContact") %>">
          </td>
        </tr>
        
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Comune</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchComuneContact" value="<%= SearchOrgListInfo.getSearchOptionValue("searchComuneContact") %>">
          </td>
        </tr>
        
        <!-- CAMPO NON VISUALIZZATO -->
        
        <tr style="display:none">
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
          </td>
          <td>
            <% CountrySelect.setJsEvent("onChange=\"javascript:updateContacts('searchcodeContactCountry','1','"+ SearchOrgListInfo.getSearchOptionValue("searchContactOtherState") +"');\""); %>
            <%= CountrySelect.getHtml("searchcodeContactCountry", SearchOrgListInfo.getSearchOptionValue("searchcodeContactCountry")) %>
          </td>
        </tr>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.City">City</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchContactCity" value="<%= SearchOrgListInfo.getSearchOptionValue("searchContactCity") %>">
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_add.StateProvince">State/Province</dhv:label>
          </td>
          <td>
            <span name="state11" ID="state11" style="<%= ContactStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeContactCountry"))? "" : " display:none" %>">
              <%= ContactStateSelect.getHtmlSelect("searchcodeContactState", SearchOrgListInfo.getSearchOptionValue("searchcodeContactCountry"),SearchOrgListInfo.getSearchOptionValue("searchcodeContactState")) %>
            </span>
            
            <span name="state21" ID="state21" style="<%= !ContactStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeContactCountry")) ? "" : " display:none" %>">
              <input type="text" size="23" name="searchContactOtherState"  value="<%= toHtmlValue(SearchOrgListInfo.getSearchOptionValue("searchContactOtherState")) %>">
            </span>
          </td>
        </tr>
      </table>
    </td>
</dhv:include>
--%>
  </tr>
</table>
<%--
<dhv:include name="accounts-search-contacts" none="true">
  <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="accounts.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
  <br />
</dhv:include>
--%>
<br />
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script type="text/javascript">
  </script>
</form>
</body>

