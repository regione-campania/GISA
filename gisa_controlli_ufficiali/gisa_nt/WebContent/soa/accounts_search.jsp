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
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StatiStabilimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="PermessiRuolo" class="org.aspcfs.modules.stabilimenti.base.PermessoVisibilitaStabilimenti" scope="request"/>

<jsp:useBean id="CategoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<%@ include file="../initPage.jsp" %>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script language="JavaScript">


dwr.engine.setErrorHandler(errorHandler);


function setComboImpianti()
{
		field_id_impianto = 'searchcodeSottoAttivita';
		idCategoria = document.getElementById('searchcodeCodiceSezione').value ;
	   	PopolaCombo.getValoriComboImpiantiSoa(idCategoria,setValoriComboCallBack);
	   
}

 function setValoriComboCallBack(returnValue)
 {
    	var select = document.getElementById(field_id_impianto); //Recupero la SELECT
     

     //Azzero il contenuto della seconda select
     for (var i = select.length - 1; i >= 0; i--)
   	  select.remove(i);

     indici = returnValue [0];
     valori = returnValue [1];
     //Popolo la seconda Select
     if (indici.length==0)
     {
    	 var NewOpt = document.createElement('option');
         NewOpt.value = -1; // Imposto il valore
    	 	NewOpt.text = 'Seleziona Categoria'; // Imposto il testo
         	NewOpt.title = valori[j];
         //Aggiungo l'elemento option
         try
         {
       	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
         }catch(e){
       	  select.add(NewOpt); // Funziona solo con IE
         }
      }
     else
     {
     
     for(j =0 ; j<indici.length; j++){
     //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
     var NewOpt = document.createElement('option');
     NewOpt.value = indici[j]; // Imposto il valore
     if(valori[j] != null)
     	NewOpt.text = valori[j]; // Imposto il testo
     	NewOpt.title = valori[j];
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


 function setComboCategorie()
 {
 		
 		tipoSoa = document.getElementById('searchcodeTipologiaSoa').value ;
 	   	PopolaCombo.getValoriComboCategorieSoa(tipoSoa,setValoriComboCategorieCallBack);
 	   
 }

  function setValoriComboCategorieCallBack(returnValue)
  {
     	var select = document.getElementById('searchcodeCodiceSezione'); //Recupero la SELECT
      

      //Azzero il contenuto della seconda select
      for (var i = select.length - 1; i >= 0; i--)
    	  select.remove(i);

      indici = returnValue [0];
      valori = returnValue [1];
      //Popolo la seconda Select
      if (indici.length==0)
      {
     	 var NewOpt = document.createElement('option');
          NewOpt.value = -1; // Imposto il valore
     	 	NewOpt.text = 'Seleziona Categoria'; // Imposto il testo
          	NewOpt.title = valori[j];
          //Aggiungo l'elemento option
          try
          {
        	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
          }catch(e){
        	  select.add(NewOpt); // Funziona solo con IE
          }
       }
      else
      {
      
      for(j =0 ; j<indici.length; j++){
      //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
      var NewOpt = document.createElement('option');
      NewOpt.value = indici[j]; // Imposto il valore
      if(valori[j] != null)
      	NewOpt.text = valori[j]; // Imposto il testo
      	NewOpt.title = valori[j];
      //Aggiungo l'elemento option
      try
      {
    	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
      }catch(e){
    	  select.add(NewOpt); // Funziona solo con IE
      }
      }
      NewOpt = document.createElement('option');
      NewOpt.value = "-1"; // Imposto il valore
      
      	NewOpt.text = "SELEZIONA CATEGORIA"; // Imposto il testo
      	NewOpt.selected = true ;
      //Aggiungo l'elemento option
      try
      {
    	  	select.add(NewOpt, null); //Metodo Standard, non funziona con IE
      }catch(e){
    	  select.add(NewOpt); // Funziona solo con IE
      }
      }


  }
 
  function clearForm() {
    <%-- Account Filters --%>
    document.forms['searchAccount'].searchAccountName.value="";
    
    document.forms['searchAccount'].searchAccountCity.value="";
   if(document.forms['searchAccount'].searchcodeOrgSiteId.options != null)
	{
		document.forms['searchAccount'].searchcodeOrgSiteId.options.selectedIndex = 0;
	}
		document.forms['searchAccount'].statoLab.options.selectedIndex = 0;
	//document.forms['searchAccount'].tipoSoa.value="";
    document.forms['searchAccount'].searchNumAut.value="";
    document.forms['searchAccount'].searchAccountOtherState.value="";
    continueUpdateState('2','true');
    document.forms['searchAccount'].searchcodeAccountState.options.selectedIndex = 0;

    document.forms['searchAccount'].searchAccountName.focus();
    //document.forms['searchAccount'].listFilter2.options.selectedIndex = 0;
    
     <%-- Contact Filters --%>
    <dhv:include name="soa-contact-information-filters" none="true">
      continueUpdateState('1','true');
      //document.forms['searchAccount'].searchcodeContactCountry.options.selectedIndex = 0;
      
    </dhv:include>
    <dhv:include name="soa-tipo-soa" none="true">
      //document.forms['searchAccount'].tipoSoa.value="";
    </dhv:include>
      <dhv:include name="soa-search-source" none="true">
      document.forms['searchAccount'].listView.options.selectedIndex = 0;
    </dhv:include>
    
    /*
    <dhv:evaluate if="<%=StageList.getEnabledElementCount() > 1 %>" >
      document.forms['searchAccount'].searchcodeStageId.options.selectedIndex = 0;
    </dhv:evaluate>
    */
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
    var url = "Soa.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeAccountState";
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
        } else {
          hideSpan('state21');
          showSpan('state11');
          document.forms['searchAccount'].searchContactOtherState.value = '';
        }
        break;
    }
  }

</script>
<dhv:include name="soa-search-name" none="true">
  <body onLoad="javascript:document.searchAccount.searchAccountName.focus();clearForm();">
</dhv:include>
<form name="searchAccount" action="Soa.do?command=Search" method="post">
<%-- Trails --%>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Soa.do"><dhv:label name="soa.soa">Soa</dhv:label></a> > 
<dhv:label name="soa.search">Cerca soa</dhv:label>
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
            <strong><dhv:label name="soa.accountInformationFilters">Ricerca Rapida Soa</dhv:label></strong>
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
       
       
        <tr>
          <td class="formLabel">
            <dhv:label name="">Approval Number</dhv:label>
          </td>
          <td>
            <input type="text" size="23" id="searchNumAut" name="searchNumAut" value="<%= SearchOrgListInfo.getSearchOptionValue("searchNumAut") %>">
          </td>
        </tr>
    
        <dhv:include name="accounts-search-segment" none="true">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="soa.soa_search.accountSegment">Account Segment</dhv:label>
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
        <dhv:include name="soa-search-source" none="true">
        <tr style="display: none">
          <td class="formLabel">
            <dhv:label name="soa.accountSource">Account Source</dhv:label>
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
            <dhv:label name="soa.accountStatus">Account Status</dhv:label>
          </td>
          <td align="left" valign="bottom">
          <select size="1" name="statoLab">
              <%-->option value="-1" <%=(SearchOrgListInfo.getFilterKey("statoLab") == -1)?"selected":""%>><dhv:label name="accounts.any">Any</dhv:label></option--%>
              <option value="0" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 0)?"selected":""%>><dhv:label name="">Autorizzato</dhv:label></option>
              <option value="1" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 1)?"selected":""%>><dhv:label name="">Revocato</dhv:label></option>
              <option value="2" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 2)?"selected":""%>><dhv:label name="">Sospeso</dhv:label></option>
              <option value="3" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 3) ?"selected":""%>><dhv:label name="">In Domanda</dhv:label></option>
                          <option value="5" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 5) ?"selected":""%>><dhv:label name="">Riconosciuo Condizionato</dhv:label></option>
            
            </select>
           
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
     

       
        <%-- %><tr>
          <td nowrap class="formLabel">
            <dhv:label name="soa.soa_add.City">City</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountCity" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountCity") %>">
          </td>
        </tr>--%>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="stabilimenti.stabilimenti_add.City">City</dhv:label>
          </td>
          <td > 
	<%= ComuniList.getHtmlSelectText("searchAccountCity",SearchOrgListInfo.getSearchOptionValue("searchAccountCity")) %>
	
	</td>
        </tr>
        
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="soa.soa_add.StateProvince">State/Province</dhv:label>
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
        	<th colspan="2">
        		Attivit&agrave;
        	</th>
        </tr>
      <%
      CategoriaList.setJsEvent("onChange=javascript:setComboImpianti()");
      %>  
      
          <tr>
          <td class="formLabel">
            <dhv:label name="">Tipo Soa</dhv:label>
          </td>
          <td>
            <select name = "searchcodeTipologiaSoa" id = "searchcodeTipologiaSoa" onchange="javascript:setComboCategorie()">
            <option value = "-1">TUTTI</option>
            <option value = "1">SOA RICONOSCIUTI</option>
            <option value = "2">SOA REGISTRATI</option>
            
            </select>
          </td>
        </tr>
        
       <tr>
          <td class="formLabel">
            Categoria
          </td>
          <td>
            <%= CategoriaList.getHtmlSelect("searchcodeCodiceSezione", ("".equals(SearchOrgListInfo.getSearchOptionValue("searchcodeCodiceSezione")) ? "" : SearchOrgListInfo.getSearchOptionValue("searchcodeCodiceSezione"))) %>
          </td>
      </tr>
       
       
        
        <tr>
          <td class="formLabel">
            Impianto
          </td>
          <td>
            <%= impianto.getHtmlSelect("searchcodeSottoAttivita", ("".equals(SearchOrgListInfo.getSearchOptionValue("searchcodeSottoAttivita")) ? "" : SearchOrgListInfo.getSearchOptionValue("searchcodeSottoAttivita"))) %>
            <%--<input type="text" name="searchSottoAttivita" value="<%= SearchOrgListInfo.getSearchOptionValue("searchSottoAttivita")%>" />--%>
          </td>
        </tr>
        
        
          <td nowrap class="formLabel">
            <dhv:label name="">Tipo Pratica</dhv:label>
          </td>
          <td> 
          <select name = "searchgrouplistaStatiIstruttoria" multiple="multiple">
          <%
          	for(Integer idStato : PermessiRuolo.getLista_stati())
          	{
          		%>
          		<option value = "<%=idStato %>" selected="selected"><%=StatiStabilimenti.getSelectedValue(idStato) %></option>
          		<%
          	}
          %>
          
          </select>
        
          </td>
        </tr>
       
      </table>
    </td>
  </tr>
</table>
<dhv:include name="soa-search-contacts" none="false">
  <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="soa.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
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

