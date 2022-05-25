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
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieA" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.trasportoanimali.base.Organization" scope="request"/>
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
function mostraCampi(){
	var valore = document.searchAccount.searchDunsType.value;
	
	if(valore == "tipo1" || valore == "tipo2"){
		 document.getElementById("denom").disabled="";
		 document.getElementById("numero").disabled="";
		 document.getElementById("precedente").disabled="";
		 document.getElementById("cognome").disabled="disabled";
		 document.getElementById("codice").disabled="disabled";
	}else if(valore == "tipo3"){
		document.getElementById("denom").disabled="";
		document.getElementById("numero").disabled="disabled";
		document.getElementById("precedente").disabled="disabled";
		document.getElementById("cognome").disabled="disabled";
		document.getElementById("codice").disabled="";
	}else if(valore == "tipo4"){
		 document.getElementById("denom").disabled="disabled";
		 document.getElementById("numero").disabled="disabled";
		 document.getElementById("precedente").disabled="disabled";
		 document.getElementById("cognome").disabled="";
		 document.getElementById("codice").disabled="disabled";
		}else{
		document.getElementById("denom").disabled="disabled";
		 document.getElementById("numero").disabled="disabled";
		 document.getElementById("precedente").disabled="disabled";
		 document.getElementById("cognome").disabled="disabled";
		 document.getElementById("codice").disabled="disabled";
	}
}
  function clearForm() {
    <%-- Account Filters --%>
    document.forms['searchAccount'].searchDunsType.value="";
    document.getElementById("denom").value="";
	document.getElementById("numero").value="";
	document.getElementById("precedente").value="";
	document.getElementById("cognome").value="";
	document.getElementById("codice").value="";
	document.forms['searchAccount'].searchAccountOtherState.value="";
    document.forms['searchAccount'].searchAccountCity.value="";
    document.forms['searchAccount'].searchTarga.value="";
    continueUpdateState('2','true');
    document.forms['searchAccount'].searchcodeAccountState.options.selectedIndex = 0;
    
      document.forms['searchAccount'].searchcodeOrgSiteId.options.selectedIndex = 0;
    
    
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
    var url = "TrasportoAnimali.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeAccountState";
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
<script language="JavaScript">
	function apri(url, w, h) {
	var windowprops = "width=" + w + ",height=" + h;
	popup = window.open(url,'remote',windowprops);
}
</script>
<%--<table width="100%" border="0">
    <tr>
      <td nowrap align="right">
    	 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button"  title="Allegato A1 (RICHIESTA)" value="Allegato A1 (RICHIESTA)"	target="_blank" onClick="javascript:window.location.href='ALL A1 RICHIESTA AUTORIZZAZIONE sotto 8 ore.pdf';">
       </td>
    </tr>
    <tr>
      <td nowrap align="right">
    	 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button"  title="Allegato A2 (RICHIESTA)" value="Allegato A2 (RICHIESTA)"	target="_blank" onClick="javascript:window.location.href='ALL A2 RICHIESTA AUTORIZZAZIONE sopra 8 ore.pdf';">
       </td>
    </tr>
    <tr>
      <td nowrap align="right">
    	 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button"  title="Allegato B (CHECK LIST)" value="Allegato B (CHECK LIST)"	target="_blank" onClick="javascript:window.location.href='CHECKLIST.pdf';">
       </td>
    </tr>
    <tr>
      <td nowrap align="right">
    	 <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button"  title="Allegato D (RICHIESTA OMOLOGAZIONE)" value="Allegato D (RICHIESTA OMOLOGAZIONE)"	target="_blank" onClick="javascript:window.location.href='OMOLOGAZIONE.pdf';">
       </td>
    </tr>
  </table>--%>
  <body onLoad="javascript:document.forms['searchAccount'].searchAccountCity.focus();clearForm();">
<form name="searchAccount" action="TrasportoAnimali.do?command=Search" method="post">
<%-- Trails --%>
<%--<table cellspacing="0">
<tr>
<td align="right">
<a href='ReportXLS.do?command=Crea&rNum=1'>Rendicontazione Annuale</a>
</td>
</tr>
</table>--%>
<br>
<dhv:permission name="trasportoanimali-estrazione-view">
<a href="TrasportoAnimali.do?command=Elenco">Estrazione elenco Trasportatori in Excel</a>
</dhv:permission>
<br>
<br>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TrasportoAnimali.do"><dhv:label name="trasportoanimali.trasportoanimali">Trasporto animali</dhv:label></a> > 
<dhv:label name="trasportoanimali.search">Cerca richiesta</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="2" cellspacing="2" border="1" width="100%">
<tr>
<td colspan="2">
<table width="100%" border="0" class="details">
	<tr>
          <th colspan="2">
            <strong><dhv:label name="">Modulistica</dhv:label></strong>
          </th>
        </tr>
    <tr>
      <td nowrap>
    	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
       	<input type="button"  title="Autorizzazione al trasporto TIPO 1, ai sensi del regolamento (CE) 1/2005." value="Allegato A1 (RICHIESTA)"	onClick="javascript:popLookupSelectorDownloadModulo('A1');">
       	
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button"  title="Autodichiarazione della registrazione come produttore primario ai sensi del Reg. (CE) 852/2004." value="Allegato G (AUTODICHIARAZIONE)" onClick="javascript:popLookupSelectorDownloadModulo('G');">
        
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button"  title="Istanza di OMOLOGAZIONE, ai sensi del regolamento (CE) 1/2005." value="Allegato E (RICHIESTA)  "	onClick="javascript:popLookupSelectorDownloadModulo('E');">
       </td>
    </tr>
    <tr>
      <td nowrap>
      <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button"  title="Autorizzazione al trasporto TIPO 2, ai sensi del regolamento (CE) 1/2005." value="Allegato A2 (RICHIESTA)"	onClick="javascript:popLookupSelectorDownloadModulo('A2');">
        
    	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button"  title="Autodichiarazione della registrazione come trasportatore 'conto proprio' di equidi." value="Allegato H (AUTODICHIARAZIONE)"	onClick="javascript:popLookupSelectorDownloadModulo('H');">
        
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button"  title="Check-list per la certificazione dei requisiti dei mezzi di trasporto degli animali vertebrati vivi per viaggi inferiori alle otto ore" value="Allegato B (CHECK LIST)"	onClick="javascript:popLookupSelectorDownloadModulo('B');">
       </td>
    </tr>
  </table>
</td>
</tr>
<tr>
  <td>
	<table width="100%" cellpadding="4" cellspacing="0" border="0" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="">Ricerca Rapida<%--Autorizzazioni Tipo 1--%></dhv:label></strong>
          </th>
        </tr>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Tipo di Richiesta</dhv:label>
          </td>
          <td>
          	<select name="searchDunsType" onchange="javascript:mostraCampi();">
          	    <option value="">Tutti i Tipi</option>
          		<option value="tipo1">Autorizzazioni Tipo 1</option>
          		<option value="tipo2">Autorizzazioni Tipo 2</option>
          		<option value="tipo3">Autodichiarazioni Produttori Primari</option>
          		<option value="tipo4">Autodichiarazioni Trasporto Equidi 'conto proprio'</option>
          	</select>
          </td>
        </tr>
        <dhv:evaluate if="<%= SiteList.size() > 2 %>">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="trasportoanimali.site">A.S.L.</dhv:label>
          </td>
          <td>
          			      <%= SiteList.getHtmlSelect("searchcodeOrgSiteId", ("".equals(SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")) ? String.valueOf(Constants.INVALID_SITE) : SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId"))) %>
          </td>
        </tr>
      	</dhv:evaluate>
      	<%-- %><tr>
          <td class="formLabel">
            <dhv:label name="">Comune Sede Operativa</dhv:label>
          </td>
          <td>
            <input type="text" size="30" id="searchAccountCity1" name="searchAccountCity" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountCity") %>">
          </td>
        </tr>--%>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Comune Sede Operativa</dhv:label>
          </td>
          <td > 
	<%= ComuniList.getHtmlSelectText("searchAccountCity",SearchOrgListInfo.getSearchOptionValue("searchAccountCity")) %>
	
	</td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            <dhv:label name="">Provincia Sede Operativa</dhv:label>
          </td>
          <td>
            <span name="state31" ID="state31" style="<%= AccountStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry"))? "" : " display:none" %>">
              <%= AccountStateSelect.getHtmlSelect("searchcodeAccountState", SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry"),SearchOrgListInfo.getSearchOptionValue("searchcodeAccountState")) %>
            </span>
            
            <span name="state41" ID="state41" style="<%= !AccountStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry")) ? "" : " display:none" %>">
              <input type="text"  size="30" name="searchAccountOtherState"  value="<%= toHtmlValue(SearchOrgListInfo.getSearchOptionValue("searchAccountOtherState")) %>">
            </span>
          </td>
        </tr>
      	<tr>
          <td class="formLabel">
            <dhv:label name="">Denominazione</dhv:label>
          </td>
          <td>
            <input type="text" size="30" disabled="disabled" id="denom" name="searchAccountName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="">Cognome Nome Trasportatore</dhv:label>
          </td>
          <td>
            <input type="text" size="30" disabled="disabled" id="cognome" name="searchAccountName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="trasportoanimali.targa">Targa<br>autoveicolo</dhv:label>
          </td>
          <td>
            <input type="text" size="30" maxlength="45" name="searchTarga" value="<%= SearchOrgListInfo.getSearchOptionValue("searchTarga") %>">
          </td>
        </tr>
        
        
     
           
    
      <dhv:evaluate if="<%= SiteList.size() <= 2 %>">
        <input type="hidden" name="searchcodeOrgSiteId" id="searchcodeOrgSiteId" value="<%=OrgDetails.getSiteId() %>" />
      </dhv:evaluate>
      <tr>
		<td nowrap class="formLabel">
			<dhv:label name="">Numero Registrazione</dhv:label>
		</td>
		<td>
			<input disabled="disabled" id="numero" type="text" size="30" maxlength="30" name="searchAccountNumber">
		</td>
		</tr>
		<tr>
		<td nowrap class="formLabel">
			<dhv:label name="">Registrazione Precedente</dhv:label>
		</td>
		<td>
			<input disabled="disabled" id="precedente" type="text" size="30" maxlength="30" name="searchNumAut">
		</td>
		</tr>
		<tr>
		<td nowrap class="formLabel">
			<dhv:label name="">Codice Azienda</dhv:label>
		</td>
		<td>
			<input disabled="disabled" id="codice" type="text" size="30" maxlength="30" name="searchAccountNumber">
		</td>
		</tr>
        <dhv:include name="accounts-search-source" none="true">
        <tr style="display: none">
          <td class="formLabel">
            <dhv:label name="trasportoanimali.trasportoanimaliSource">Account Source</dhv:label>
          </td>
          <td align="left" valign="bottom">
            <select size="1" name="listView">
              <option <%= SearchOrgListInfo.getOptionValue("all") %>><dhv:label name="trasportoanimali.all.trasportoanimali">All Accounts</dhv:label></option>
              <option <%= SearchOrgListInfo.getOptionValue("my") %>><dhv:label name="trasportoanimali.my.trasportoanimali">My Accounts</dhv:label></option>
            </select>
          </td>
        </tr>
        </dhv:include>
      
		</table>
		
     </tr>
</table>
<br>
<dhv:include name="accounts-search-contacts" none="false">
  <input type="checkbox" name="searchContacts" value="true" <%="true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="trasportoanimali.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
  <br />
</dhv:include>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">

<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">

<%--<input type="reset" value="<dhv:label name="button.clear">Clear</dhv:label>" >--%>
<input type="hidden" name="source" value="searchForm">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script type="text/javascript">
  </script>
</form>
</body>



