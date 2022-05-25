<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- f
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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AccountStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="ContactStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.global_search.base.OrganizationView" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OperatoriList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCheckList.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
cal19.showNavigationDropdowns();
</SCRIPT>
<!-- ******************************************************************** -->

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
function positiontip(e){
if (enabletip){
var nondefaultpos=false
var curX=(ns6)?e.pageX : event.clientX+ietruebody().scrollLeft;
var curY=(ns6)?e.pageY : event.clientY+ietruebody().scrollTop;

var winwidth=ie&&!window.opera? ietruebody().clientWidth : window.innerWidth-20
var winheight=ie&&!window.opera? ietruebody().clientHeight : window.innerHeight-20
var rightedge=ie&&!window.opera? winwidth-event.clientX-offsetfromcursorX : winwidth-e.clientX-offsetfromcursorX
var bottomedge=ie&&!window.opera? winheight-event.clientY-offsetfromcursorY : winheight-e.clientY-offsetfromcursorY
var leftedge=(offsetfromcursorX<0)? offsetfromcursorX*(-1) : -1000

if (rightedge<tipobj.offsetWidth){

tipobj.style.left=curX-tipobj.offsetWidth+"px"
nondefaultpos=true
}
else if (curX<leftedge)
tipobj.style.left="5px"
else{

tipobj.style.left=curX+offsetfromcursorX-offsetdivfrompointerX+"px"
pointerobj.style.left=curX+offsetfromcursorX+"px"
}

if (bottomedge<tipobj.offsetHeight){
tipobj.style.top=curY-tipobj.offsetHeight-offsetfromcursorY+"px"
nondefaultpos=true
}
else{
tipobj.style.top=curY+offsetfromcursorY+offsetdivfrompointerY+"px"
pointerobj.style.top=curY+offsetfromcursorY+"px"
}
tipobj.style.visibility="visible"
if (!nondefaultpos)
pointerobj.style.visibility="visible"
else
pointerobj.style.visibility="hidden"
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

document.onmousemove=positiontip
</script>

      <script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
      <script type="text/javascript" src="dwr/interface/CountView.js"> </script>
	  <script type="text/javascript" src="dwr/engine.js"> </script>
      <script type="text/javascript" src="dwr/util.js"></script>
      <script type="text/javascript">

		   
			function popolaComboComuni(idAsl)
			{
				idAsl = document.searchAccount.searchcodeOrgSiteId.value;
				PopolaCombo.getValoriComboComuniAsl(idAsl,setComuniComboCallback);
				CountView.getProvincia(idAsl,setProv);		
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

	        function setProv(returnValue){
		    	  var select = document.forms['searchAccount'].searchAccountOtherState; //Recupero la SELECT
		    	  var idAsl = document.searchAccount.searchcodeOrgSiteId.value;
				
				  if(idAsl == -1){
					  document.searchAccount.searchAccountOtherState.options[0]=new Option("-- SELEZIONA VOCE --", "-1", true, false);
					  document.searchAccount.searchAccountOtherState.options[1]=new Option("AV", "1", true, false);
					  document.searchAccount.searchAccountOtherState.options[2]=new Option("BN", "2", true, false);
					  document.searchAccount.searchAccountOtherState.options[3]=new Option("CE", "3", true, false);
					  document.searchAccount.searchAccountOtherState.options[4]=new Option("NA", "4", true, false);
					  document.searchAccount.searchAccountOtherState.options[5]=new Option("SA", "5", true, false);
					  
					} 
				  	else{
					  
				  		for (var i = select.length - 1; i >= 0; i--)
			            	  select.remove(i);

				    	  
			          	  valori = returnValue;
		          	  	
			              //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
			              var NewOpt = document.createElement('option');
			              

			              if(valori == "AV"){
				              NewOpt.value = 1;
			              }

			              if(valori == "BN"){
				              NewOpt.value = 2;
			              }

			              if(valori == "CE"){
				              NewOpt.value = 3;
			              }

			              if(valori == "NA"){
				              NewOpt.value = 4;
			              }

			              if(valori == "SA"){
				              NewOpt.value = 5;
			              }	

			              NewOpt.text = valori; // Imposto il testo

			              //Aggiungo l'elemento option
			              try
			              {
			            	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
			              }catch(e){
			            	  select.add(NewOpt); // Funziona solo con IE
			              }
			            
				    	 
				  	}
   
			  }

	          function popolaAsl(comune){
		          comune = document.searchAccount.searchAccountCity.value;
		          PopolaCombo.getValoriComuniASL(comune,setAslCallback) ;
		          
			  }

			  function setAslCallback(returnValue){

				  document.searchAccount.searchcodeOrgSiteId.value = returnValue[0];
				  idAsl = document.searchAccount.searchcodeOrgSiteId.value;
				  CountView.getProvincia(idAsl,setProv);
			  }
       
			 function popolaComboProvincia(provincia){
					provincia = document.searchAccount.searchAccountOtherState.value;
					var p=document.getElementById("prov").options[provincia].text;
					//PopolaComuni a partire dalla provincia
					PopolaCombo.getValoriComboComuniProvinciaOSM(p,setComuniComboCallback);
					//PopolaAsl a partire dalla provincia
					PopolaCombo.getValoriAslProvincia(p,setAsl);
				}

			   		function setAsl(returnValue){
				   		
			   			var select = document.forms['searchAccount'].searchcodeOrgSiteId; //Recupero la SELECT
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

  	  	  	document.forms['searchAccount'].searchcodeOrgSiteId.value="-1";
  	  	 	document.forms['searchAccount'].searchcodeOrgSiteId.options.selectedIndex = -1;
	  		document.forms['searchAccount'].searchAccountOtherState2.value="-1";
  	  	 	document.forms['searchAccount'].searchAccountOtherState2.options.selectedIndex = -1;
	  		document.forms['searchAccount'].searchAccountCity.value="";
	  		document.forms['searchAccount'].searchAccountName.value="";	  	  	
	  		
  	 }

  	function check(){
  		var rad_val = "";
  		for (var i=0; i < document.searchAccount.selectMod.length; i++)
  		{
  		   if (document.searchAccount.selectMod[i].checked)
  		      {
  		      		rad_val = document.searchAccount.selectMod[i].value;
  		      }
  		}
  		
  		if(rad_val == "")	
  		{
  			alert("Controlla di aver selezionato un modulo.");
  		
  		}

  		/*if(rad_val == 1)	
  		{
  			alert("Modulo non disponibile per questo Operatore.");
  		
  		}*/
  		else
  		{
  			document.searchAccount.submit();
  			//window.location.href="Accounts.do?command=Details&orgId="+document.printmodules.orgId.value;
  			//window.location.href="Accounts.do?command=Details&orgId="+document.printmodules.orgId.value+"&selectMod="+rad_val;
  			//window.close();
  		}

  	}
 
</script>


<script>
function openPopupModules(tipo){
	var res;
	var result;
	window.open('PrintModulesHTML.do?command=GenerateBarcode&tipo='+tipo,'popupSelect',
		'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');	
}

function visualizzaMod(){

	document.getElementById('tabMod').style.display = 'block';
	document.getElementById('butMod').style.display = 'block';
	document.getElementById('tabOp').style.display = 'none';
	
}

function visualizzaRicerca(){
	
	document.getElementById('tabOp').style.display = 'block';
	//document.getElementById('tabMod').style.display = 'none';
	//document.getElementById('butMod').style.display = 'none';
}
</script>	

<dhv:permission name="global-search-view"><br></dhv:permission>

<dhv:include name="accounts-search-name" none="true">
<body onLoad="">
</dhv:include>
<%-- Trails --%>
<form name="searchAccount" action="OsaSearch.do?command=Search" method="post">        			
<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:label name="Operatori">Operatori</dhv:label> >
<dhv:label name="Ricerca Operatori">Ricerca Operatori</dhv:label>
</td>
</tr>
</table>

<br>

<table id="tabOp" cellpadding="2" cellspacing="2" border="0" width="100%" style="display:none">
  <tr>
    <td width="50%" valign="top">
      <table cellpadding="4" cellspacing="0" border="0" width="50%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="Ricerca Operatori">Ricerca Operatori</dhv:label></strong>
          </th>
        </tr>
        <dhv:evaluate if="<%= SiteList.size() > 2 %>">
        <tr>
          <td nowrap class="formLabel"
          	onmouseover="ddrivetip('<dhv:label name="">Ricerca operatore per ASL di Appartenenza</dhv:label>')"
      		onmouseout="hideddrivetip()">
            <dhv:label name="">ASL</dhv:label>
          </td>
          <td>
           <dhv:evaluate if="<%=User.getUserRecord().getSiteId() == -1 %>" >
           <%
           		SiteList.setJsEvent("onChange=popolaComboComuni()");
           %>
            <%= SiteList.getHtmlSelect("searchcodeOrgSiteId", ("".equals(SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")) ? String.valueOf(Constants.INVALID_SITE) : SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId"))) %>
           </dhv:evaluate>
           <dhv:evaluate if="<%=User.getUserRecord().getSiteId() != -1 %>" >
              <input type="hidden" id="asl" name="searchcodeOrgSiteId" value="<%= User.getUserRecord().getSiteId()%>">
              <%= SiteList.getSelectedValue(User.getUserRecord().getSiteId()) %>
           </dhv:evaluate>
          </td>
        </tr>
      </dhv:evaluate>  
      <dhv:evaluate if="<%= SiteList.size() <= 2 %>">
        <input type="hidden" name="searchcodeOrgSiteId" id="searchcodeOrgSiteId" value="-1" />
      </dhv:evaluate>   
     <%--<tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="">Provincia</dhv:label>
          </td>
          <td>
            <span name="state31" ID="state31" style="<%= AccountStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry"))? "" : " display:none" %>">
              <%= AccountStateSelect.getHtmlSelect("searchcodeAccountState", SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry"),SearchOrgListInfo.getSearchOptionValue("searchcodeAccountState")) %>
            </span>
            <%-- If selected country is not US/Canada use textfield --%>
            <%-- 
            <span name="state41" ID="state41" style="<%= !AccountStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry")) ? "" : " display:none" %>">
              <input id="prov" type="text" size="23" name="searchAccountOtherState"  value="<%= toHtmlValue(SearchOrgListInfo.getSearchOptionValue("searchAccountOtherState")) %>">
            </span> --%>
            <%--<select name="searchAccountOtherState" value="-1" id="prov" onchange="popolaComboProvincia();">
      			<option value="-1">-- SELEZIONA VOCE --</option>
  	  			<option value="1">AV</option>
  				<option value="2">BN</option>
  				<option value="3">CE</option>
  				<option value="4">NA</option>
  				<option value="5">SA</option>
	  		</select> 
          </td>
        </tr>--%>
       <tr class="containerBody">
          <td nowrap class="formLabel"
          	onmouseover="ddrivetip('<dhv:label name="">Ricerca operatore per Provincia di Appartenenza</dhv:label>')"
      		onmouseout="hideddrivetip()">
            <dhv:label name="stabilimenti.stabilimenti_add.Stateince">Provincia</dhv:label>
          </td>
          <td>
            <%-- %><span name="state31" ID="state31" style="<%= AccountStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry"))? "" : " display:none" %>">
              <%= AccountStateSelect.getHtmlSelect("searchcodeAccountState", SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry"),SearchOrgListInfo.getSearchOptionValue("searchcodeAccountState")) %>
            </span>
            <span name="state41" ID="state41" style="<%= !AccountStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry")) ? "" : " display:none" %>">
              <input type="text" size="23" name="searchAccountOtherState"  value="<%= toHtmlValue(SearchOrgListInfo.getSearchOptionValue("searchAccountOtherState")) %>">
            </span>--%>
            <%if(User.getContact().getState()!=null){
            if(User.getContact().getState().equals("AV")){ 
            %>
           
            
            
            	<input type="text" readonly="readonly" size="5" maxlength="5" name="searchAccountOtherState" value="AV">
            <%}else if(User.getContact().getState().equals("BN")){  %>
            <%--select name="searchAccountOtherState"  id="searchAccountOtherState4" onchange="javascript:popolaComboProvincia();">
                
            	<option value="BN" selected="selected">Benevento</option>
            	
            </select--%>
            
            	<input type="text" readonly="readonly" size="5" maxlength="5" name="searchAccountOtherState" value="BN">
            <%}else if(User.getContact().getState().equals("CE")){  %>
            <%-- %>select name="searchAccountOtherState" id="searchAccountOtherState5"  onchange="javascript:popolaComboProvincia();">
                <option value="CE" selected="selected">Caserta</option>
            </select--%>
            	<input type="text" readonly="readonly" size="5" maxlength="5" name="searchAccountOtherState" value="CE">
            <%}else if(User.getContact().getState().equals("NA")){  %>
             <%-- %>select name="searchAccountOtherState" id="searchAccountOtherState6" onchange="javascript:popolaComboProvincia();">
               
            	<option value="NA" selected="selected">Napoli</option>
            	
            </select--%>
            	<input type="text" readonly="readonly" size="5" maxlength="5" name="searchAccountOtherState" value="NA">
            <%}else if(User.getContact().getState().equals("SA")){  %>
            <%-- %>select name="searchAccountOtherState"  id="searchAccountOtherState7" onchange="javascript:popolaComboProvincia();">
                
            	<option value="SA" selected="selected">Salerno</option>
            </select--%>
            	<input type="text" readonly="readonly" size="5" maxlength="5" name="searchAccountOtherState" value="SA">
            <%}else{%>
            <select name="searchAccountOtherState" id="searchAccountOtherState1" onchange="javascript:popolaComboProvincia();">
                <option value="-1">--SELEZIONA VOCE</option>
            	<option value="1">Avellino</option>
            	<option value="2">Benevento</option>
            	<option value="3">Caserta</option>
            	<option value="4">Napoli</option>
            	<option value="5">Salerno</option>
            </select>
            <%}}else{ %>
            <select name="searchAccountOtherState" id="searchAccountOtherState2" onchange="javascript:popolaComboProvincia();">
                <option selected value="-1">--SELEZIONA VOCE--</option>
            	<option value="1">Avellino</option>
            	<option value="2">Benevento</option>
            	<option value="3">Caserta</option>
            	<option value="4">Napoli</option>
            	<option value="5">Salerno</option>
            </select>
            <%} %>
          </td>
        </tr>
        
        
        <% ComuniList.setJsEvent("onChange=popolaAsl();");%>	  
        <tr>
          <td nowrap class="formLabel"
          	onmouseover="ddrivetip('<dhv:label name="">Ricerca operatore per Comune di Appartenenza</dhv:label>')"
      		onmouseout="hideddrivetip()">
            <dhv:label name="">Comune</dhv:label>
          </td>
          <td > 
		<%= ComuniList.getHtmlSelect("searchAccountCity",SearchOrgListInfo.getSearchOptionValue("searchAccountCity")) %>
	
		</td>
		
        </tr>
        <tr>
          <td class="formLabel"
          	onmouseover="ddrivetip('<dhv:label name="">Ricerca operatore per nome Impresa</dhv:label>')"
      		onmouseout="hideddrivetip()">
            <dhv:label name="">Impresa</dhv:label>
          </td>
          <td>
            <input id="nome" type="text" maxlength="70" size="40" name="searchAccountName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
         <tr>
          <td class="formLabel"
          	onmouseover="ddrivetip('<dhv:label name="">Ricerca operatore per Partita IVA</dhv:label>')"
      		onmouseout="hideddrivetip()">
            <dhv:label name="">CF/Partita IVA</dhv:label>
          </td>
          <td>
            <input id="nome" type="text" maxlength="70" size="40" name="searchAccountCodiceFiscale" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountCodiceFiscale") %>">
          </td>
        </tr>  
        
        <tr>
          <td class="formLabel" 
          	onmouseover="ddrivetip('<dhv:label name="">Ricerca operatore per Categoria di Appartenenza</dhv:label>')"
      		onmouseout="hideddrivetip()">
            <dhv:label name="">TIPOLOGIA OPERATORE</dhv:label>
          </td>
          <td>
      		  <%=OperatoriList.getHtmlSelect("searchcodeTipologia_operatore",-1) %></td>
          </tr>  
      </table>
      		<input type="submit" id="search" name="search" value="Ricerca"/>
			<!--  <input type="hidden" name="source" value="searchForm">  -->
			<input type="hidden" name="source" value="searchForm">		
			<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();"/>		
    </td>  
  </tr>
</table>
		
</form>


<dhv:include name="accounts-search-contacts" none="false">
  <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="accounts.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
  <br />
</dhv:include>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script type="text/javascript">
</script>
<script>visualizzaRicerca();</script>


