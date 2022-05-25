<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

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
<jsp:useBean id="PermessiRuolo" class="org.aspcfs.modules.stabilimenti.base.PermessoVisibilitaStabilimenti" scope="request"/>


<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="CategoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>

<%@ include file="../initPage.jsp" %>
<script language="JavaScript">


	 
	var field_id_impianto ;
function setComboImpianti()
{	
		field_id_impianto = 'searchcodeSottoAttivita';

		idCategoria = document.getElementById('searchcodeCodiceSezione').value ;
		
		PopolaCombo.getValoriComboImpiantiStabilimenti(idCategoria,
				{
			callback: setValoriComboCallBack ,
				textHtmlHandler:function(){alert('sessione scaduteee');}
				}
		);
	   
}

 function setValoriComboCallBack(returnValue)
 {
	 //alert ("returnValue: "+returnValue);
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
         	NewOpt.title = valori[i];
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

function mostraCampi(){
	
	var valoreSottoAttivita = document.searchAccount.searchcodeSottoAttivita.value;
	var valoreCategoria     = document.searchAccount.searchcodeCodiceSezione.value;

	document.searchAccount.searchAccountName.disabled=false;
	document.searchAccount.searchNumAut.disabled=false;
	document.searchAccount.searchcodeStatoLab.disabled=false;
	document.searchAccount.searchAccountPostalCode.disabled=false;
	document.searchAccount.searchAccountCity.disabled=false;
	document.searchAccount.searchAccountOtherState.disabled=false;
	document.searchAccount.searchcodeOrgSiteId.disabled=false;
	//document.getElementById("linkAllerta").style.display="";
	
	if(valoreSottoAttivita==24 && valoreCategoria==8){
		document.searchAccount.searchAccountName.disabled=false;
		document.searchAccount.searchNumAut.disabled=true;
		document.searchAccount.searchcodeStatoLab.disabled=true;
		document.searchAccount.searchAccountPostalCode.disabled=true;
		document.searchAccount.searchAccountCity.disabled=true;
		document.searchAccount.searchAccountOtherState.disabled=true;
		document.searchAccount.searchcodeOrgSiteId.disabled=true;
		//document.getElementById("linkAllerta").style.display="none";
		
	} 
	
}

function mostraCampi2(){
	document.searchAccount.searchAccountName.disabled=false;
	document.searchAccount.searchNumAut.disabled=false;
	document.searchAccount.searchcodeStatoLab.disabled=false;
	document.searchAccount.searchAccountPostalCode.disabled=false;
	document.searchAccount.searchAccountCity.disabled=false;
	document.searchAccount.searchAccountOtherState.disabled=false;
	document.searchAccount.searchcodeOrgSiteId.disabled=false;
	//document.getElementById("linkAllerta").style.display="";
}

  function clearForm() {
    <%-- Account Filters --%>

    
    if (document.searchAccount.flagAllerte.checked)
	{
    	document.forms['searchAccount'].searchcodiceAllerta.value="Tutte";  
	}
    else
    {
    	document.forms['searchAccount'].searchcodiceAllerta.value="";  
      }
    document.forms['searchAccount'].searchcodecategoriaRischio.value="-1";
    document.forms['searchAccount'].searchAccountName.value="";
    document.forms['searchAccount'].searchAccountPostalCode.value="";
    document.forms['searchAccount'].searchAccountCity.options.selectedIndex = -1;;
    
    document.forms['searchAccount'].searchcodeAccountState.options.selectedIndex = 0;
    document.forms['searchAccount'].searchAccountOtherState.value = '';
    document.forms['searchAccount'].searchAccountName.focus();
    document.forms['searchAccount'].searchNumAut.value="";
    document.forms['searchAccount'].searchcodeStatoLab.options.selectedIndex=0;

    

    
      }
      
   
  function setStatoCu(stato)
  {
      document.searchAccount.searchstatoCu.value = stato;
  }
 function abilitaRicercaAllerte()
 {
		if (document.searchAccount.flagAllerte.checked)
		{
			document.getElementById("bloccoAllerte").style.display="block";

			document.searchAccount.searchcodiceAllerta.value = "Tutte";
		}
		else
		{
			document.getElementById("bloccoAllerte").style.display="none";
			document.searchAccount.searchcodiceAllerta.value = "";
		}
     
 }
function controllaStatoStabilimento()
{
	check = false ;
if (document.getElementById('tipo1')!=null && document.getElementById('tipo1').checked == true )
{
	check = true ;
}

if(check==false)
if (document.getElementById('tipo2')!=null && document.getElementById('tipo2').checked == true )
{
	check = true ;
}
if(check==false)
if (document.getElementById('tipo3')!=null &&document.getElementById('tipo3').checked == false )
{
	check = true 
}
if(check==false)
	if (document.getElementById('tipo4')!=null && document.getElementById('tipo4').checked == false  )
{
		check = true ;
	
}
	/*if(check==false)
	{
		document.getElementById('tipo2').checked =true
	}*/
	
}
 
</script>
<dhv:include name="stabilimenti-search-name" none="true">
  <body onLoad="javascript:document.searchAccount.searchAccountName.focus();clearForm();">
</dhv:include>
<form name="searchAccount" action="Stabilimenti.do?command=Search" method="post">


<br><br>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Stabilimenti.do"><dhv:label name="stabilimenti.stabilimenti">stabilimenti</dhv:label></a> > 
<dhv:label name="stabilimenti.search">Cerca stabilimenti</dhv:label>
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
            <strong><dhv:label name="stabilimenti.accountInformationFilters">Account Information Filters</dhv:label></strong>
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
      
        
        <tr>
          <td class="formLabel">
            <dhv:label name="stabilimenti.accountStatus">Account Status</dhv:label>
          </td>
          <td align="left" valign="bottom">
          <select size="1" name="searchcodeStatoLab">
              <option value="-1" <%=(SearchOrgListInfo.getFilterKey("searchcodeStatoLab") == -1)?"selected":""%>><dhv:label name="accounts.any">Any</dhv:label></option>
              <option value="0" <%=(SearchOrgListInfo.getFilterKey("searchcodeStatoLab") == 0)?"selected":""%>><dhv:label name="">Autorizzato</dhv:label></option>
              <option value="1" <%=(SearchOrgListInfo.getFilterKey("searchcodeStatoLab") == 1)?"selected":""%>><dhv:label name="">Revocato</dhv:label></option>
              <option value="2" <%=(SearchOrgListInfo.getFilterKey("searchcodeStatoLab") == 2)?"selected":""%>><dhv:label name="">Sospeso</dhv:label></option>
              <option value="3" <%=(SearchOrgListInfo.getFilterKey("searchcodeStatoLab") == 3) ?"selected":""%>><dhv:label name="">In Domanda</dhv:label></option>
              <option value="5" <%=(SearchOrgListInfo.getFilterKey("searchcodeStatoLab") == 3) ?"selected":""%>><dhv:label name="">Riconosciuto Condizionato</dhv:label></option>
           
            </select>
           
          </td>
        </tr>
        

        <tr>
          <td class="formLabel">
            <dhv:label name="stabilimenti.stabilimenti_add.ZipPostalCode">Postal Code</dhv:label>
          </td>
          <td>
            <input type="text" size="10" maxlength="12" name="searchAccountPostalCode" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountPostalCode") %>">
          </td>
        </tr>
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
       

              <tr id ="filtroAllerta" >
          <td nowrap class="formLabel" >
            <dhv:label name="">Controlli per Allerta</dhv:label>
          </td>
          <td>
          <input type = "checkbox" name = "flagAllerte" onclick="javascript:abilitaRicercaAllerte()">
          
          <div id = "bloccoAllerte" style = "display: none">
          
          		<input type="hidden" id="ticketid" value="-1" name="ticketidd">
           		<input style="background-color: lightgray" readonly="readonly" type="text" size="20"  id="id_allerta" name="searchcodiceAllerta"  value="Tutte" >
      			&nbsp;[<a href="javascript:popLookupSelectorAllertaRicerca('id_allerta','name','ticket','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
     			<br>
     			Controlli Aperti <input type = "radio" name = "statoCu" onclick="javascript:setStatoCu('aperti')" checked="checked"> Controlli chiusi <input type = "radio" name = "statoCu" onclick="javascript:setStatoCu('chiusi')"> 
     			<input type = "hidden" name = "searchstatoCu" value = "aperti" >
     	</div>
     		
     		
       </td>
       
        </tr>
        
        
       
     <dhv:evaluate if="<%= SiteList.size() <= 2 %>">
        <input type="hidden" name="searchcodeOrgSiteId" id="searchcodeOrgSiteId" value="-1" />
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
            Categoria
          </td>
          <td>
            <%= CategoriaList.getHtmlSelect("searchcodeCodiceSezione", -1) %>
          
            &nbsp;&nbsp; <font color ='red'><b>*** ATTENZIONE! LA RICERCA PER CATEGORIA ED IMPIANTO SI ESTENDE A TUTTE LE LINEE DI ATTIVITA' DELLO STABILIMENTO.</b></font>
        
          </td>
        </tr>
        
        <tr>
          <td class="formLabel">
            Impianto
          </td>
          <td>
         
            <%= impianto.getHtmlSelect("searchcodeSottoAttivita", ("".equals(SearchOrgListInfo.getSearchOptionValue("searchcodeSottoAttivita")) ? "" : SearchOrgListInfo.getSearchOptionValue("searchSottoAttivita"))) %>
            
           </td>
        </tr>
        
      </table> 
    </td>
  </tr>

        
</table>
<dhv:include name="stabilimenti-search-contacts" none="false">
  <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="stabilimenti.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
  <br />
</dhv:include>
<input type="submit" onclick="controllaStatoStabilimento()" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">

</form>


