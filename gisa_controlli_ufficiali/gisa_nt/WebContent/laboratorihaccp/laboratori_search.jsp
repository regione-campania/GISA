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
<jsp:useBean id="LookupLists" class="org.aspcfs.utils.web.LookupListList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AccountStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.laboratorihaccp.base.Organization" scope="request"/>
<jsp:useBean id="ContactStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="MatriciHaccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DenominazioniHaccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Prova" class="org.aspcfs.modules.laboratorihaccp.base.Prova" scope="request"/>
<jsp:useBean id="Ente" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
 <script type="text/javascript">
		/*setta i comuni in base all'asl*/
			function popolaAsl()
			{
				if (document.searchAccount.searchcodeOrgSiteId.value != "-1")
				{
					PopolaCombo.getValoriComboComuniAsl(document.searchAccount.searchcodeOrgSiteId.value,setAslCallback) ;
				}
		}
			/*setta l'asl in base al comune*/
			function popolaComuni()
			{
				PopolaCombo.getValoriComuniASL(document.searchAccount.searchAccountCityOp.value,setComuniCallback) ;
				
		}

			function setAslCallback(returnValue)
	          {
				var select = document.searchAccount.searchAccountCityOp; //Recupero la SELECT
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

			function setComuniCallback(returnValue)
	          {
		          
				var select = document.searchAccount.searchcodeOrgSiteId; //Recupero la SELECT

				if(select.length!=null)
				{
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
				
		          }
</script>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        
<script language="JavaScript">
  function clearForm() {
    <%-- Account Filters --%>

    document.forms['searchAccount'].searchcodeOrgSiteId.options.selectedIndex = 0;
    document.forms['searchAccount'].searchAccountName.value="";
    document.forms['searchAccount'].searchAccountNumber.value="";
    document.forms['searchAccount'].searchCognomeRappresentante.value="";
    document.forms['searchAccount'].searchAccountCityOp.options.selectedIndex = 0;
    document.forms['searchAccount'].searchAccountCityLeg.value="";
    document.getElementById('id_matrice').innerHTML = "";
    document.getElementById('codiceMatrice').value=-1;
    document.getElementById('id_prova').innerHTML = "";
    document.getElementById('codiceDenominazione').value=-1;
    document.forms['searchAccount'].searchcodiceEnte.value=-1;
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
    var url = "LaboratoriHACCP.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeAccountState";
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
  <body onLoad="javascript:document.searchAccount.searchragioneSociale.focus();clearForm();popolaAsl();">
</dhv:include>
<form name="searchAccount" action="LaboratoriHACCP.do?command=Search" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="LaboratoriHACCP.do?command=SearchForm"><dhv:label name="">Laboratori HACCP</dhv:label></a> > 
<dhv:label name="">Cerca Laboratorio HACCP</dhv:label>
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
            <strong><dhv:label name="">Ricerca laboratori</dhv:label></strong>
          </th>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="lab.denom">Denominazione</dhv:label>
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="searchAccountName" id="searchragioneSociale" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="lab.number">Numero di Iscrizione</dhv:label>
          </td>
          <td>
            <input  type="text" maxlength="50" size="50" name="searchAccountNumber" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountNumber") %>">
          </td>
        </tr>
        <tr>
        	<td nowrap class="formLabel" name="city1">
     		 Sede Legale
   			 </td> 
    
    		<td> 
				<input  type="text" maxlength="50" size="50" name="searchAccountCityLeg" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountCityLeg") %>">
				<input  type="hidden" maxlength="50" size="50" name="addess_type_leg" value ="1">
			</td>
  		</tr>
  		
  		<%--<tr>
        	<td nowrap class="formLabel" name="city2">
     		 <dhv:label name="">Sede Operativa</dhv:label>
   			 </td> 
    
    		<td> 
				<input  type="text" maxlength="50" size="50" name="searchAccountCityOp" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountCityOp") %>">
				<input  type="hidden" maxlength="50" size="50" name="addess_type_op" value ="5">
			</td>
  		</tr>--%>
  		<% SiteList.setJsEvent("onchange='javascript:popolaAsl();'"); %>
       
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.site">Site</dhv:label>
          </td>
          <td>
            <%= SiteList.getHtmlSelect("searchcodeOrgSiteId", ("".equals(SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")) ? String.valueOf(Constants.INVALID_SITE) : SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId"))) %>
          </td>
        </tr>
       <tr>
         <td class="formLabel">
            <dhv:label name="">Comune sede operativa</dhv:label>
          </td>
          <td>
            <select  name="searchAccountCityOp" id="prov12" onchange="javascript:popolaComuni();">
				<option value=""></option>
            
	 <%
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
                while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                	                  
        %>
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(SearchOrgListInfo.getSearchOptionValue("searchAccountCityOp"))) {%> selected="selected" <%} %>><%= prov4 %></option>	
              <%}%>
		
			</select> 
			<input  type="hidden" maxlength="50" size="50" name="addess_type_op" value ="5">
	</td>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="lab.number">Direttore responsabile laboratorio</dhv:label>
          </td>
          <td>
            <input  type="text" maxlength="50" size="50" name="searchCognomeRappresentante" value="<%= SearchOrgListInfo.getSearchOptionValue("searchCognomeRappresentante") %>">
          </td>
        </tr>
        
      <dhv:include name="accounts-search-type" none="true">
        <tr>
          <td class="formLabel">
            <dhv:label name="accounts.type">Account Type</dhv:label>
          </td>
          <td>
            <%= TypeSelect.getHtmlSelect("listFilter1", SearchOrgListInfo.getFilterKey("listFilter1")) %>
          </td>
        </tr>
        </dhv:include>
        <dhv:include name="accounts-search-segment" none="true">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.accounts_search.accountSegment">Account Segment</dhv:label>
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
        <dhv:include name="accounts-search-source" none="true">
        <tr style="display: none">
          <td class="formLabel">
            <dhv:label name="">Account Source</dhv:label>
          </td>
          <td align="left" valign="bottom">
            <select size="1" name="listView">
              <option <%= SearchOrgListInfo.getOptionValue("all") %>><dhv:label name="accounts.all.accounts">All Accounts</dhv:label></option>
              <option <%= SearchOrgListInfo.getOptionValue("my") %>><dhv:label name="accounts.my.accounts">My Accounts</dhv:label></option>
            </select>
          </td>
        </tr>
        </dhv:include>
        
        <dhv:include name="organization.stage" none="true">
        <dhv:evaluate if="<%= StageList.getEnabledElementCount() > 1 %>">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Stage</dhv:label>
          </td>
          <td>
            <%= StageList.getHtmlSelect("searchcodeStageId", SearchOrgListInfo.getSearchOptionValueAsInt("searchcodeStageId")) %>
          </td>
        </tr>
      </dhv:evaluate>  
      </dhv:include>
      </table>
    </td>
  </tr>
</table>
<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">
    <table cellpadding="2" cellspacing="2" border="0" width="100%">
    
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="">Ricerca per prove</dhv:label></strong>
          </th>
        </tr>
        <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Materiale / Prodotto / Matrice</dhv:label>
      </td>
      <td >
      <table>
      <tr>
      <td id = "id_matrice"></td>
      <td>
              <%-- MatriciHaccp.getHtmlSelect("searchcodiceMatrice",Prova.getCodiceMatrice()) --%>
              &nbsp;[<a href="javascript:popLookupSelectorCustomMatrici('description','short_description','lookup_matrici_labhaccp','');">Seleziona Matrice</a>] 
              <input type = "hidden" name = "searchcodiceMatrice" id = "codiceMatrice" value = "-1" /> 
     </td></tr></table>
      </td>
    </tr>
    
     <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Denominazione della Prova</dhv:label>
      </td>
      <td>
      <table>
      <tr>
      <td id = "id_prova"></td>
      <td>
               &nbsp;[<a href="javascript:popLookupSelectorCustomProve('description','short_description','lookup_denominazioni_labhaccp','');">Seleziona Denominazione</a>] 
              <input type = "hidden" name = "searchcodiceDenominazione" id = "codiceDenominazione" value = "-1" > 
   
      </td>
      
      </tr>
      </table>
              <%-- DenominazioniHaccp.getHtmlSelect("searchcodiceDenominazione", Prova.getCodiceDenominazione()) --%>
     </td>
    </tr>
   
 <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Ente Accreditamento Prove</dhv:label>
      </td>
      <td>
          <%= Ente.getHtmlSelect("searchcodiceEnte", Prova.getCodiceEnte()) %>
         
      </td>
    </tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
</table>
<input type="hidden" id="tipoRicerca" name="tipoRicerca" value="" />
<dhv:include name="accounts-search-contacts" none="false">
  <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="accounts.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
  <br />
</dhv:include>
<p>
    * Il risultato per laboratori con elenco prove fornisce l'elenco dei laboratori ricercati con il corrispondente elenco prove.</p>

 
      

<input type="submit" id="searchO" name="searchO" value="Risultati per laboratori" onClick="this.form.tipoRicerca.value='lab';"/>
<input type="submit" id="searchC" name="searchC" value="Risultati per laboratori con elenco prove" onClick="this.form.tipoRicerca.value='prova';"/>
			
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script type="text/javascript">
  </script>
</form>
</body>

<% } %>

