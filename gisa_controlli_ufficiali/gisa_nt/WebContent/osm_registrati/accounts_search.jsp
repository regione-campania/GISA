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
<jsp:useBean id="impiantoZ" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="CategoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrganizationAddress" class="org.aspcfs.modules.allevamenti.base.OrganizationAddress" scope="session" />
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<%@ include file="../initPage.jsp" %>

<% Boolean ricercaOsmAssociabiliAttribute = (Boolean)request.getAttribute("ricercaOsmAssociabiliAttribute"); %>

<script language="JavaScript">
  function clearForm() {
    <%-- Account Filters --%>
    document.forms['searchAccount'].searchcodiceAllerta.value="";  
    document.forms['searchAccount'].searchcodeattivita.value="-1";
    document.forms['searchAccount'].searchstatoLab.value="0";
    document.forms['searchAccount'].searchcodecategoriaRischio.value="-1";
    document.forms['searchAccount'].searchAccountName.value="";

    <%if (ricercaOsmAssociabiliAttribute == null) {%>
  		document.forms['searchAccount'].searchAccountCity.value="";
    <%}%>
    
    continueUpdateState('2','true');
    document.forms['searchAccount'].searchcodeAccountState.options.selectedIndex = 0;
    document.forms['searchAccount'].searchAccountOtherState.value = '';
    document.forms['searchAccount'].searchAccountName.focus();
   
    // document.forms['searchAccount'].listFilter2.options.selectedIndex = 0;
    <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 && SiteList.size() > 2 %>" >
      document.forms['searchAccount'].searchcodeOrgSiteId.options.selectedIndex = 0;
    </dhv:evaluate>
     <%-- Contact Filters --%>
    
    <dhv:include name="osmregistrati-search-number" none="true">
      document.forms['searchAccount'].searchAccountNumber.value="";
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
    var url = "OsmRegistrati.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeAccountState";
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

<%
// nel caso in cui arrivo nella pagina cliccando su "Registrazione 183"
// dalla scheda di un allevamento che non ha OSM associati
// visualizzo l'operazione in corso
if (ricercaOsmAssociabiliAttribute != null && ricercaOsmAssociabiliAttribute == true) {
	org.aspcfs.modules.allevamenti.base.Organization allevamentoACuiAssociareOsm = (org.aspcfs.modules.allevamenti.base.Organization)session.getAttribute("allevamentoACuiAssociareOsm");
	%>		
	<div align="center"; style="BACKGROUND-COLOR:#BDCFFF; font-weight:bold; font-size:12;">
		RICERCA OSM DA ASSOCIARE ALL'ALLEVAMENTO "<%=allevamentoACuiAssociareOsm.getName()%>"
	</div>
	<br/>
	<%
} 
%>

<dhv:include name="osmregistrati-search-name" none="true">
  <body onLoad="javascript:document.searchAccount.searchAccountName.focus();clearForm();">
</dhv:include>
<form name="searchAccount" action="OsmRegistrati.do?command=Search" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OsmRegistrati.do?command=Dashboard">OSM Registrati</a> >
<!--			<a href="OsmRegistrati.do"><dhv:label name="osm.osm">OSM Riconusciti</dhv:label></a> > -->
				Cerca OSM Registrati
		</td>
	</tr>
</table>
<%-- End Trails --%>

<%-- Link aggiungi,ricerca, importa --%>
<table class="" cellspacing="0" >
	<tr>
		<td>
			<dhv:permission name="osmregistrati-osmregistrati-add">
				<a href="OsmRegistrati.do?command=Add">Aggiungi OSM Registrati</a>
			</dhv:permission>
			&nbsp;
		</td>
		<td>
			<dhv:permission name="osmregistrati-osmregistrati-view">
				<a href="OsmRegistrati.do?command=SearchForm">Ricerca OSM Registrati</a>
			</dhv:permission>
			&nbsp;
		</td>
		<td>
			<dhv:permission name="osmregistrati-upload-view">
				<a href="OsmRegistrati.do?command=OsmUpload">Importa OSM Registrati</a>
			</dhv:permission>
		</td>
<%-- 		<td>
		<dhv:permission name="osmregistrati-estrazione-view">
			<a href="EstrazioneOSMReg.do?command=Elenco"><dhv:label name="">Esporta Elenco OSM</dhv:label></a>
			</dhv:permission>
		</td> --%>
		
	</tr>
</table>
<%-- End link aggiungi,ricerca, importa --%>

<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="osm.accountInformationFilters">Account Information Filters</dhv:label></strong>
          </th>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="organization.name">Account Name</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
        <dhv:include name="osmregistrati-search-number" none="true">
        <tr>
          <td class="formLabel">
            <dhv:label name="organizationaa.accountNumber">Numero di Registrazione</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountNumber" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountNumber") %>">
          </td>
        </tr>
        </dhv:include>
        
         
        <dhv:include name="accounts-search-segment" none="true">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="osm.osm_search.accountSegment">Account Segment</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountSegment" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountSegment") %>">
            <dhv:evaluate if="<%= SegmentList.size() > 1 %>">
              <% SegmentList.setJsEvent("onchange=\"javascript:fillAccountSegmentCriteria();\"");%>
              <%= SegmentList.getHtmlSelect("viewOnlySegmentId", -1) %>
            </dhv:evaluate>
          </td>
        </tr>
        </dhv:include>
        <dhv:include name="osmregistrati-search-source" none="true">
        <tr style="display: none">
          <td class="formLabel">
            <dhv:label name="stabilimenti.accountSource">Account Source</dhv:label>
          </td>
          <td align="left" valign="bottom">
            <select size="1" name="listView">
              <option <%= SearchOrgListInfo.getOptionValue("all") %>><dhv:label name="accounts.all.accounts">All Accounts</dhv:label></option>
              <option <%= SearchOrgListInfo.getOptionValue("my") %>><dhv:label name="accounts.my.accounts">My Accounts</dhv:label></option>
            </select>
          </td>
        </tr>
        </dhv:include>
        <tr>
          <td class="formLabel">
            <dhv:label name="osm.accountStatus">Account Status</dhv:label>
          </td>
          <td align="left" valign="bottom">
          <select size="1" name="searchstatoLab">
              <%--<option value="-1" <%=(SearchOrgListInfo.getFilterKey("statoLab") == -1)?"selected":""%>><dhv:label name="accounts.any">Any</dhv:label></option>--%>
              <option value="0" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 0)?"selected":""%>><dhv:label name="">Autorizzato</dhv:label></option>
              <option value="1" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 1)?"selected":""%>><dhv:label name="">Revocato</dhv:label></option>
              <option value="2" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 2)?"selected":""%>><dhv:label name="">Sospeso</dhv:label></option>
              <option value="4" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 4) ?"selected":""%>><dhv:label name="">Cessato</dhv:label></option>
           
            </select>
          
            <%--<select size="1" name="listFilter2">
              <option value="-1" <%=(SearchOrgListInfo.getFilterKey("listFilter2") == -1)?"selected":""%>><dhv:label name="stabilimenti.any">Any</dhv:label></option>
              <option value="1" <%=(SearchOrgListInfo.getFilterKey("listFilter2") == 1)?"selected":""%>><dhv:label name="osm.active.osm">Active</dhv:label></option>
              <option value="0" <%=(SearchOrgListInfo.getFilterKey("listFilter2") == 0)?"selected":""%>><dhv:label name="osm.disabled.osm">Inactive</dhv:label></option>
            </select>--%>
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
            <dhv:label name="stabilimenti.stabilimenti_add.City">City</dhv:label>
          </td>
          <td>
            <%
            if (ricercaOsmAssociabiliAttribute != null && ricercaOsmAssociabiliAttribute == true) {
            	org.aspcfs.modules.allevamenti.base.Organization allevamentoACuiAssociareOsm = (org.aspcfs.modules.allevamenti.base.Organization)session.getAttribute("allevamentoACuiAssociareOsm");
            	%>
            		<input type="hidden" id="searchAccountCity" name="searchAccountCity" value="<%=allevamentoACuiAssociareOsm.getCity()%>" />
            		<%=allevamentoACuiAssociareOsm.getCity()%>
            <%} else {%>
            	<input type="text" size="23" name="searchAccountCity" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountCity") %>">
            <%} %>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="stabilimenti.stabilimenti_add.StateProvince">State/Province</dhv:label>
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
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Categoria Rischio</dhv:label>
          </td>
          <td>
           <select name="searchcodecategoriaRischio">
           		<option value="-1">-- Tutte --</option>
           		<option value="1">1</option>
           		<option value="2">2</option>
           		<option value="3">3</option>
           		<option value="4">4</option>
           		<option value="5">5</option>
           </select>
          </td>
        </tr>
        
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Codice Allerta</dhv:label>
          </td>
          <td>
          <input type="hidden" id="ticketid" value="0" name="ticketidd">
   
           <input style="background-color: lightgray" readonly="readonly" type="text" size="20" value="<%= SearchOrgListInfo.getSearchOptionValue("searchcodiceAllerta") %>" id="id_allerta" name="searchcodiceAllerta" >
      &nbsp;[<a href="javascript:popLookupSelectorAllertaRicerca('id_allerta','name','ticket','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
       </td>
       </tr>
     <dhv:evaluate if="<%= SiteList.size() <= 2 %>">
        <input type="hidden" name="searchcodeOrgSiteId" id="searchcodeOrgSiteId" value="-1" />
      </dhv:evaluate>
        <tr id="impianto_0">
          <td class="formLabel">
            Attività
          </td>
          <td>
         
            <%= impiantoZ.getHtmlSelect("searchcodeattivita", SearchOrgListInfo.getSearchOptionValue("searchcodeattivita")) %>
           </td>
        </tr>

      </table>
    </td>
  </tr>

        
</table>
<dhv:include name="osmregistrati-search-contacts" none="false">
  <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="osm.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
  <br />
</dhv:include>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">

<%
// associazione OSM - azienda zootecnica
if (ricercaOsmAssociabiliAttribute != null && ricercaOsmAssociabiliAttribute == true) {
	%>
	<input type="hidden" name="ricercaOsmAssociabiliParameter" value="1">
	<%
}
%>

<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script type="text/javascript">
  </script>
</form>
</body>

<% } %>
