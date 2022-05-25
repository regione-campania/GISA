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
<jsp:useBean id="SearchOrgListInfoFcie" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AccountStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.farmacosorveglianza.base.Organization" scope="request"/>
<jsp:useBean id="ContactStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
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
				PopolaCombo.getValoriComuniASL(document.searchAccount.searchCitta.value,setComuniCallback) ;
				
		}

			function setAslCallback(returnValue)
	          {
				var select = document.searchAccount.searchCitta; //Recupero la SELECT
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
<script language="JavaScript">
  function clearForm() {
    <%-- Account Filters --%>
    document.forms['searchAccount'].searchragioneSociale.value="";
    document.forms['searchAccount'].searchStato.value="";
    document.forms['searchAccount'].searchCitta.value="";

    if (document.forms['searchAccount'].searchcodeOrgSiteId.options != null)
    {
    	document.forms['searchAccount'].searchcodeOrgSiteId.options.selectedIndex = -1;
    }
        <dhv:include name="accounts-search-number" none="true">
      //document.forms['searchAccount'].searchAccountNumber.value="";
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
    document.forms['searchAccount'].searchragioneSociale.focus();

    document.forms['searchAccount'].searchcodeTipoAutorizzazzione.value = -1 ;
    document.forms['searchAccount'].searchNumAutorizzazzione.value = '' ;
    document.forms['searchAccount'].searchcodeOrgSiteId.value = -1 ;
	
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
    var url = "Farmacosorveglianza.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeAccountState";
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
<form name="searchAccount" action="Farmacosorveglianza.do?command=SearchFcie" method="post">
<%-- Trails 
<dhv:permission name="farmacosorveglianza-farmacosorveglianza-add">
<a href="Farmacosorveglianza.do?command=AddFcie"><dhv:label name="">Aggiungi</dhv:label></a>
</dhv:permission>
<dhv:permission name="farmacosorveglianza-farmacosorveglianza-view">
<a href="Farmacosorveglianza.do?command=SearchFormFcie"><dhv:label name="">Ricerca</dhv:label></a>
</dhv:permission>--%>
<dhv:permission name="farmacosorveglianza-estrazione-view">
<a href="EstrazioneFcie.do?command=Elenco"><dhv:label name="">Esporta Elenco Operatori del Farmaco</dhv:label></a>
</dhv:permission>
</br>
</br>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Farmacosorveglianza.do?command=SearchFormFcie"><dhv:label name="">Operatori Farmaceutici</dhv:label></a> > 
<dhv:label name="">Cerca Operatore Farmaceutico</dhv:label>
</td>
</tr>
</table>
<br>
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
            <dhv:label name="">Impresa</dhv:label>
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="searchragioneSociale" id="searchragioneSociale" value="<%= SearchOrgListInfoFcie.getSearchOptionValue("searchragioneSociale") %>">
          </td>
        </tr>
        <% SiteList.setJsEvent("onchange='javascript:popolaAsl();'"); %>
       
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.site">Site</dhv:label>
          </td>
          <td>
            <%= SiteList.getHtmlSelect("searchcodeOrgSiteId", ("".equals(SearchOrgListInfoFcie.getSearchOptionValue("searchcodeOrgSiteId")) ? String.valueOf(Constants.INVALID_SITE) : SearchOrgListInfoFcie.getSearchOptionValue("searchcodeOrgSiteId"))) %>
          <%--  <dhv:evaluate if="<%=User.getUserRecord().getSiteId() != -1 %>" >
              <input type="hidden" name="searchcodeOrgSiteId" id="searchcodeOrgSiteId" value="<%= User.getUserRecord().getSiteId() %>">
              <%= SiteList.getSelectedValue(User.getUserRecord().getSiteId()) %>
           </dhv:evaluate>
        --%>
          </td>
        </tr>
<!--      <dhv:evaluate if="<%= SiteList.size() <= 2 %>">-->
<!--        <input type="hidden" name="searchcodeOrgSiteId" id="searchcodeOrgSiteId" value="-1" />-->
<!--      </dhv:evaluate>-->
      <tr>
          <td class="formLabel">
            <dhv:label name="">Comune</dhv:label>
          </td>
          <td>
          
          <%= ComuniList.getHtmlSelectText("searchCitta",SearchOrgListInfoFcie.getSearchOptionValue("searchCitta")) %>
          </td>
          <%-- 
            <select  name="searchCitta" id="prov12" onchange="javascript:popolaComuni();">
				<option value=""></option>
            
	 <%
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
                while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                	                  
        %>
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(OrgDetails.getCitta())) {%> selected="selected" <%} %>><%= prov4 %></option>	
              <%}%>
		
			</select>
			</td>
			--%> 
	
        </tr>
        
           <tr>
          <td class="formLabel">
            <dhv:label name="">Stato</dhv:label>
          </td>
          <td>
            <select  name="searchStato"  >
				<option value=""></option>
				<option value="attivo">Attivo</option>
				<option value="revocato">Revocato</option>
				<option value="sospeso">Sospeso</option>
            

			</select> 
	</td>
        </tr>
        
           <tr>
          <td class="formLabel">
            <dhv:label name="">Tipo Autorizzazzione</dhv:label>
          </td>
          <td>
            <select  name="searchcodeTipoAutorizzazzione" onchange="javascript:if(this.value!=-1){document.getElementById('num_autorizzazzione').style.display=''}else{document.getElementById('num_autorizzazzione').style.display='none'}">
				
				<option value="-1" selected="selected">TUTTE</option>
				<option value="1">INGROSSO</option>
				<option value="2">DETTAGLIO</option>
            

		
			</select> 
	</td>
        </tr>
        
        
                <tr id = "num_autorizzazzione" style = "display: none">
          <td class="formLabel">
            <dhv:label name="">Num. Autorizzazzione</dhv:label>
          </td>
          <td>
           <input type = "text" name = "searchNumAutorizzazzione">
	</td>
        </tr>
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


<% } %>
