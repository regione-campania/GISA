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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="normeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

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


<style type="text/css">
#dhtmltooltip{
position: absolute;
left: -300px;
width: 150px;
border: 1px solid black;
padding: 2px;
background-color: lightyellow;
visibility: hidden;
z-index: 100;
/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
}
#dhtmlpointer{
position:absolute;
left: -300px;
z-index: 101;
visibility: hidden;
}
</style>

<script type="text/javascript">

function inCostruzione(){
	alert('In Costruzione!');
	return false;
}
</script>

      <script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        <script type="text/javascript">
        var offsetfromcursorX=12 
        var offsetfromcursorY=10 
        var offsetdivfrompointerX=10 
        var offsetdivfrompointerY=14 
        document.write('<div id="dhtmltooltip"></div>') //write out tooltip DIV
        document.write('<img id="dhtmlpointer" src="images/arrow2.gif">') //write out pointer image
        var ie=document.all
        var ns6=document.getElementById && !document.all
        var enabletip=false
        if (ie||ns6)
        var tipobj=document.all? document.all["dhtmltooltip"] : document.getElementById? document.getElementById("dhtmltooltip") : ""
        var pointerobj=document.all? document.all["dhtmlpointer"] : document.getElementById? document.getElementById("dhtmlpointer") : ""
        function ietruebody(){
        return (document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body
        }
        function ddrivetip(thetext, thewidth, thecolor){
        if (ns6||ie){
        if (typeof thewidth!="undefined") tipobj.style.width=thewidth+"px"
        if (typeof thecolor!="undefined" && thecolor!="") tipobj.style.backgroundColor=thecolor
        tipobj.innerHTML=thetext
        enabletip=true
        return false
        }
        }
        

        function hideddrivetip(){
        if (ns6||ie){
        enabletip=false
        tipobj.style.visibility="hidden"
        pointerobj.style.visibility="hidden"
        tipobj.style.left="-1000px"
        tipobj.style.backgroundColor=''
        tipobj.style.width=''
        }
        }
        function cambiaAddressType(i){
      	  document.forms['searchAccount'].searchcodeAddressType.value = i;
        }
      	  

      	function cambiaAttivitaImpresa(){
      		if(document.forms['searchAccount'].searchAccountCity.value != ""){
      			document.getElementById("attivita_impresa").style.display = "block";
      			document.getElementById("tipo_impresa_1").checked = "checked";
      			cambiaAddressType(5);
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
			function popolaComboComuni(idAsl)
			{
				idAsl = document.searchAccount.searchcodeOrgSiteId.value;
				
					PopolaCombo.getValoriComboComuniAsl(idAsl,setComuniComboCallback) ;
				
			}

	          function setComuniComboCallback(returnValue)
	          {
	        	  var select = document.forms['searchAccount'].searchAccountCity; //Recupero la SELECT
	              

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
  function clearForm() {
    <%-- Account Filters --%>
    document.forms['searchAccount'].searchTipoDest.value="";
    document.forms['searchAccount'].searchcodecategoriaRischio.value="-1";
    document.forms['searchAccount'].searchcodeCessato.value="-1"; 
    document.forms['searchAccount'].searchNomeCorrentista.value="";
    document.forms['searchAccount'].searchCognomeRappresentante.value="";
    document.forms['searchAccount'].searchNomeRappresentante.value="";
    document.forms['searchAccount'].searchCodiceFiscale.value="";
    document.forms['searchAccount'].searchPartitaIva.value="";
    document.forms['searchAccount'].searchAccountName.value="";
    document.forms['searchAccount'].searchCodiceFiscaleCorrentista.value="";

    if (document.searchAccount.flagAllerte.checked)
	{
    	document.forms['searchAccount'].searchcodiceAllerta.value="Tutte";  
	}
    else
    {
    	document.forms['searchAccount'].searchcodiceAllerta.value="";  
      }
    <dhv:include name="accounts-search-number" none="true">
      document.forms['searchAccount'].searchAccountNumber.value="";
    </dhv:include>
   }
 

	function cambiaAttivitaImpresa(){
		if(document.forms['searchAccount'].searchAccountCity.value != ""){
			document.getElementById("attivita_impresa").style.display = "block";
			document.getElementById("tipo_impresa_1").checked = "checked";
			cambiaAddressType(5);
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
	
	function disabilitaNorme()
	{
		option = document.getElementById("searchgroupNorma").options ;
		for (i=0;i<option.length ; i++)
		{
		 option[i].selected = false ;
				
		}
	}
  
	function onChangeTipoOperatore()
	{
		if (document.getElementById("searchtipoOperatore") != null)
			{
		option = document.getElementById("searchtipoOperatore").options ;
		isSel = false ;
		isSelPreg = false ;
		for (i=0;i<option.length ; i++)
			{
			if (option[i].selected && option[i].value=="2")
				{
				document.getElementById("list2").style.display="none";
				document.getElementById("list3").style.display="none";
				document.getElementById("list4").style.display="none";
				document.getElementById("list5").style.display="";
				document.getElementById("targaVeicolo").value ="";
				document.getElementById("alertText").value ="";
				document.getElementById("secondari").checked=false;
				document.getElementById("tipo_impresa_1").checked=false;
				document.getElementById("tipo_impresa_2").checked=false;
				document.getElementById("tipo_impresa_3").checked=false;
				isSel=true;
				
				}
			
			if (option[i].selected && option[i].value=="1")
			{
				
				isSelPreg=true ;
				
				
			}
			}
		
		if (isSelPreg ==false)
		{ 
			
			document.getElementById("list5").style.display="";
		}
		else
			{
			document.getElementById("list5").style.display="none";
			disabilitaNorme();
			}
		
		if (isSel ==false)
			{ 
		
			document.getElementById("list2").style.display="";
			document.getElementById("list3").style.display="";
			document.getElementById("list4").style.display="";
			
			
			}
		
		
			}
		else
			{
		
			document.getElementById("list2").style.display="";
			document.getElementById("list3").style.display="";
			document.getElementById("list4").style.display="";
			document.getElementById("list5").style.display="none";
			disabilitaNorme();

			
			}
		
	}

</script>
<dhv:include name="accounts-search-name" none="true">
  <body onLoad="clearForm();">
</dhv:include>
<form name="searchAccount" action="Accounts.do?command=Search" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="">Stabilimenti 852</dhv:label></a> > 
<dhv:label name="">Ricerca Rapida</dhv:label> 
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
            <strong><dhv:label name="">Ricerca Rapida Stabilimento</dhv:label></strong> <br/><font color="red">ATTENZIONE! In caso di eccessiva lentezza si consiglia di usare il modulo di ricerca presente in "Anagrafica Stabilimenti" selezionando "Cerca in Vecchia Anagrafica"</font>
          </th>
        </tr>
         <dhv:permission name="opu-view">
        <tr style = "display:none">
          <td class="formLabel">	
            <dhv:label name="">Tipo Operatore</dhv:label>
          </td>
          <td>
          <select name = "searchgrouptipoOperatore" id = "searchtipoOperatore" multiple="multiple" onchange="onChangeTipoOperatore()">
          <option value = "1" selected="selected">852 ex ateco</option>
           <option value = "2" selected="selected">SINVSA</option>
          </select>
          
             
          </td>
        </tr>
       </dhv:permission>
        <tr>
          <td class="formLabel">
            <dhv:label name="">Impresa</dhv:label>
          </td>
          <td>
            <input type="text" maxlength="70" size="50" name="searchAccountName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
        <dhv:include name="accounts-search-number" none="true">
        <tr>
          <td class="formLabel">
            <dhv:label name="organization.accountNumber">Account Number</dhv:label>
          </td>
          <td>
            <input  type="text" maxlength="50" size="50" name="searchAccountNumber" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountNumber") %>">
          </td>
        </tr>
        </dhv:include>
        
        <tr id="list2"  >
    		<td class="formLabel" nowrap name="targaVeicolo1" id="targaVeicolo1">
      			<dhv:label name="">Targa Autoveicolo</dhv:label>
   			 </td>
   			 <td>
      			<input id="targaVeicolo" type="text" size="20" maxlength="10"  name="searchNomeCorrentista" value="<%= SearchOrgListInfo.getSearchOptionValue("searchNomeCorrentista") %>">
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
            Codice Fiscale
          </td>
          <td>
            <input type="text" size="23" name="searchCodiceFiscale" value="<%= SearchOrgListInfo.getSearchOptionValue("searchCodiceFiscale") %>">
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
  <tr id= "list3" >
    <td class="formLabel" nowrap>
      <dhv:label name="">Codice Istat Principale</dhv:label>
    </td>
    <td>
      <input type="hidden" id="id_attivita_masterlist" name="id_attivita_masterlist" value="-1">
      <input readonly="readonly" type="text" size="20" id ="searchCodiceFiscaleCorrentista" name="searchCodiceFiscaleCorrentista" value="<%= SearchOrgListInfo.getSearchOptionValue("searchCodiceFiscaleCorrentista")%>">     
       &nbsp;[<a href="javascript:popLookupSelectorCustomImprese('searchCodiceFiscaleCorrentista','alertText','id_attivita_masterlist','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      &nbsp;cerca anche nei secondari <input type = "checkbox" name = "searchIstatSecondari" id = "secondari" value = "si" disabled="disabled">
      </br>
       <input type="hidden" size="50" id="alertText" name="alertText" value="">
      
    </td>
  </tr>
        <tr> 
          <td class="formLabel">
            <dhv:label name="">Stato</dhv:label>
          </td>
          <td align="left" valign="bottom">
           
            <select size="1" name="searchcodeCessato">
              <option value="-1" <%=(SearchOrgListInfo.getFilterKey("searchcodeCessato") == -1)?"selected":""%>><dhv:label name="accounts.any">Any</dhv:label></option>
              <option value="0" <%=(SearchOrgListInfo.getFilterKey("searchcodeCessato") == 0)?"selected":""%>><dhv:label name="">In Attività</dhv:label></option>
              <option value="1" <%=(SearchOrgListInfo.getFilterKey("searchcodeCessato") == 1)?"selected":""%>><dhv:label name="">Cessato</dhv:label></option>
              <option value="2" <%=(SearchOrgListInfo.getFilterKey("searchcodeCessato") == 2)?"selected":""%>><dhv:label name="">Sospeso</dhv:label></option>
            </select>
          </td>
        </tr>
        
     
      
       <%ComuniList.setJsEvent("onchange=\"javascript:cambiaAttivitaImpresa();\""); %>
        <tr id = "list4" >
        
	<td nowrap class="formLabel" name="province1" id="prov2">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td> 
	<%= ComuniList.getHtmlSelectText("searchAccountCity",SearchOrgListInfo.getSearchOptionValue("searchAccountCity")) %>
	<div id="attivita_impresa" style="">Attività: 
	<input name="searchTipoDest" id="tipo_impresa_1" type="radio" value="Es. Commerciale" 
	onclick="javascript:cambiaAddressType(5)"
	onmouseover="ddrivetip('<dhv:label name="">Per le attività Fisse la Ricerca verrà effettuata sul comune della sede Operativa</dhv:label>')"
  	onmouseout="hideddrivetip()">
	Fissa 	
	<input name="searchTipoDest" id="tipo_impresa_2" type="radio" 
	value="Autoveicolo" 
	onclick="javascript:cambiaAddressType(1)"
	onmouseover="ddrivetip('<dhv:label name="">Per le attività Mobili la Ricerca verrà effettuata sul comune della sede Legale</dhv:label>')"
	onmouseout="hideddrivetip()">
	
	
	Mobile
	
	
	<input name="searchTipoDest" id="tipo_impresa_3" type="radio" value="Distributori" 
	onclick="javascript:cambiaAddressType(1)"
	onmouseover="ddrivetip('<dhv:label name="">Per le attività erogatrici di Distributori la Ricerca verrà effettuata sul comune della sede Legale</dhv:label>')"
	onmouseout="hideddrivetip()">
	
	
	
		  
	Distributori
	

	<input name="searchcodeAddressType" id="tipo_impresa_4" type="hidden" value=""/>
	</div>
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
           		<%-- %>option value="3">3 senza Check List</option--%>
           </select>
          </td>
        </tr>
       
              <tr id = "list5" style = "display:none">
          <td nowrap class="formLabel">
            <dhv:label name="">Norma</dhv:label>
          </td>
          <td>
          <%normeList.setMultiple(true);
          normeList.setSelectSize(6);
          %>
          <%=normeList.getHtmlSelect("searchgroupNorma", -1) %>
       </td>
        </tr> 
        
          <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Controlli per Allerta</dhv:label>
          </td>
          <td>
          <input type = "checkbox" name = "flagAllerte" onclick="javascript:abilitaRicercaAllerte()">
          
          <div id = "bloccoAllerte" style = "display: none">
          
          		<input type="hidden" id="ticketid" value="-1" name="ticketidd">
           		<input style="background-color: lightgray" readonly="readonly" type="text" size="20"  id="id_allerta" name="searchcodiceAllerta"  value="" >
      			&nbsp;[<a href="javascript:popLookupSelectorAllertaRicerca('id_allerta','name','ticket','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
     			<br>
     			Controlli Aperti <input type = "radio" name = "statoCu" onclick="javascript:setStatoCu('aperti')" checked="checked"> Controlli chiusi <input type = "radio" name = "statoCu" onclick="javascript:setStatoCu('chiusi')"> 
     			<input type = "hidden" name = "searchstatoCu" value = "aperti" >
     	</div>
       </td>
        </tr>
     
       
      
      </table>
    </td>

  </tr>
</table>
<dhv:include name="accounts-search-contacts" none="false">
  <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="accounts.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
  <br />
</dhv:include>
<input type="submit" onclick='loadModalWindow();' value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script type="text/javascript">
  </script>
</form>
</body>


<% } %>
