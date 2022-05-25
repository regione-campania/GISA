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
  - Version: $Id: accounts_add.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoPesca" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SistemaPesca" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IstatList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Address" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressSedeOperativa" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressSedeMobile" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLocale1" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLocale2" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLocale3" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>

<jsp:useBean id="rel_ateco_linea_attivita_List" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="specieAnimali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipoStabulatorio" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.imbarcazioni.base.Organization" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="AccountSizeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../../initPage.jsp" %>
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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script>

function verificaEsistenzaOperatore()
{
	num_registrazione = document.getElementById('accountNumber').value;
	PopolaCombo.controlloEsuistenzaNumRegistrazioneImbarcazioni(num_registrazione,verificaEsistenzaOperatoreCallback ) ;
	
}

function verificaEsistenzaOperatoreCallback(value)
{
	
	if (value == false)
	{
		 
			 return checkForm(document.addAccount);
		
	}
	else
	{
		if(document.getElementById('accountNumber').value !='')
		{
			//if (confirm('Numero Registrazione assegnato a un altro operatore , sicuro di voler salvare ? ')==true) {
			alert('Non e\' possibile inserire questa imbarcazione in quanto\n l\'identificativo della barca risulta assegnato ad un altro operatore. \n');
			return false ;
			//return checkForm(document.addAccount);
		}
		else{
			alert('Inserire il numero di registrazione');
		return false ;
			}
	}
	
}


function mostraData(campoData,valStato)
{

if(valStato!='0')
{
	campoData.style.display='block';
}
else
{
	campoData.style.display='none';
}
}
function showCoordinate(address,city,prov,cap,campo_lat,campo_long)
	{
   campoLat = campo_lat;
   campoLong = campo_long;
   Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
   
   
}
function setGeocodedLatLonCoordinate(value)
{
	campoLat.value = value[1];;
	campoLong.value =value[0];
	
} 
function doCheck(form){
	  if(form.dosubmit.value=="false") {
		  loadModalWindow();
		  return true;
	  }

	  else {
		  if(checkForm(form)) {
			  form.submit();
			  return true;
		  }
		  else
			  return false;
		  
	  }
}


function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";

if(form.name.value =='')
{
    	message += '- Inserire il nome Impresa \n' ;
    	formTest = false ;
}

/*if(form.address1city.value =='-1')
{
	message += '- Inserire il comune per la sede Legale\n' ;
	formTest = false ;
}

if(form.address2city.value =='-1')
{
	message += '- Inserire il comune per la sede Operativa\n' ;
	formTest = false ;
}

if(form.address2city.value =='' )
{
	message += '- Selezionare il comune della sede operativa \n' ;
	formTest = false ;
}

*/


/*if(form.siteId.value =='-1' || form.siteId.value =='')
{
	message += '- Selezionare l\'asl di appartenenza \n' ;
	formTest = false ;
}*/

if(form.accountNumber.value =='' )
{
	message += '- Inserire il numero identificativo della barca \n' ;
	formTest = false ;
}

if(isNaN(form.capacita_max.value)){
	message += '- Il campo \'Tonnellate di stazza\' deve essere numerico \n' ;
	formTest = false ;

}

/*if (form.address1latitude && form.address1latitude.value!=""){
		if (isNaN(form.address1latitude.value) ||  (form.address1latitude.value < 39.988475) || (form.address1latitude.value > 41.503754)){
			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Legale)\r\n";
			 formTest = false;
		}
	}   
 
 if (form.address1longitude && form.address1longitude.value!=""){
			if (isNaN(form.address1longitude.value) ||  (form.address1longitude.value < 13.7563172) || (form.address1longitude.value > 15.8032837)){
			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Legale)\r\n";
				 formTest = false;
			}		
 }   
*/

	if (form.address2latitude && form.address2latitude.value!=""){
 		if (isNaN(form.address2latitude.value) ||  (form.address2latitude.value < 39.988475) || (form.address2latitude.value > 41.503754)){
   			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Ormeggio abituale)\r\n";
   			 formTest = false;
   		}
   	}   
	 
	 if (form.address2longitude && form.address2longitude.value!=""){
 			if (isNaN(form.address2longitude.value) ||  (form.address2longitude.value < 13.7563172) || (form.address2longitude.value > 15.8032837)){
  			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Ormeggio abituale)\r\n";
  				 formTest = false;
  			}		
	 }   


    if (formTest == false) {
        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
        return false;
      } else {
        var test = document.addAccount.selectedList;
        if (test != null) {
          selectAllOptions(document.addAccount.selectedList);
        }
        if(alertMessage != "") {
          confirmAction(alertMessage);
        }
        form.submit();
        return true;
      }
    }

function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
}    

</script>
<form id = "addAccount" name="addAccount" action="Imbarcazioni.do?command=Insert&auto-populate=true" method="post">
<input type="hidden" name="dosubmit" value="true" />
  
  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Imbarcazioni.do">Imbarcazioni</a> >
Aggiungi imbarcazione
</td>
</tr>
</table>

<input type="button" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';return verificaEsistenzaOperatore()">
<input type="button" value="Annulla" onClick="document.addAccount.action ='Imbarcazioni.do';document.addAccount.submit();">

<br/><br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Aggiungi Imbarcazione</strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
     Ufficio Marittimo di provenienza
    </td>
    <td>
     	<input  type="radio" name="fuori_regione" value="false">In Regione&nbsp;
     	<input  type="radio" name="fuori_regione" value="true">Fuori Regione
    </td>
  </tr>
  <%-- ASL da nonn gestire
  <tr>
    <td nowrap class="formLabel" >
      ASL
    </td>
    <td>
   <%
   if (User.getSiteId()>0)
   {
	   %>
	   <input type = "hidden" name = "siteId" value = "<%=User.getSiteId() %>"/>
	   <%=SiteList.getSelectedValue(User.getSiteId()) %>
	   <%
   }else
   {
	   %>
	   <%=SiteList.getHtmlSelect("siteId",-1) %>
	   <%
   }
   
   %>
	<font color = "red">*</font>
    </td>
  </tr>
  --%>
  <tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"> Data iscrizione agli Uffici Marittimi
				</td>
				<td>
				<input readonly type="text" id="dataPresentazione" name="dataPresentazione" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataPresentazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
				</td>
	</tr>
  
    <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      Identificativo della barca U.E
    </td>
    <td>
      <input  type="text" size="50" id="accountNumber" maxlength="80" name="accountNumber" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>">
   	<font color = "red">*</font>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
     Targa
    </td>
    <td>
      <input  type="text" size="50" id="numaut" maxlength="80" name="numaut" value="<%= toHtmlValue(OrgDetails.getNumaut()) %>">
    </td>
  </tr>
   <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
     Nome dell'imbarcazione
    </td>
    <td>
      <input  type="text" size="50" id="namefirst" maxlength="80" name="namefirst" value="<%= toHtmlValue(OrgDetails.getNamefirst()) %>">
    </td>
  </tr>
    <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      Numero iscrizione agli Uffici Marittimi
    </td>
    <td>
      <input  type="text" size="50" maxlength="80" name="autorizzazione" value="<%= toHtmlValue(OrgDetails.getAutorizzazione()) %>">
    </td>
  </tr>
  <tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"> Data cancellazione dagli Uffici Marittimi
				</td>
				<td>
				<input readonly type="text" id="date2" name="date2" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].date2,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
				</td>
	</tr>
  
      </table>
      <br/><br/>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Armatore</strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      Impresa
    </td>
    <td>
      <input  type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>">
   	<font color = "red">*</font>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      Partita IVA/Codice Fiscale
    </td>
    <td>
      <input  type="text" size="50" maxlength="80" name="partita_iva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      Tonnellate di stazza
    </td>
    <td>
      <input  type="text" size="50" maxlength="80" name="capacita_max" onblur="javascript:if (isNaN(value)) { alert ('Errore il campo può contenere solo valori numerici!');}" value="">
    </td>
  </tr>
   <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      Tipo di pesca
    </td>
    <td>
    	<% TipoPesca.setMultiple(true);
    	   TipoPesca.setSelectSize(9);
    	%>
    	<%=TipoPesca.getHtmlSelect("tipoPesca",-1) %>
    	
     <%-- <input  type="text" size="50" maxlength="80" name="tipologia_strutt" value="<%=toHtmlValue(OrgDetails.getTipo_struttura()) %>">--%>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      Sistema di pesca
    </td>
    <td>
    <% SistemaPesca.setMultiple(true);
    	SistemaPesca.setSelectSize(9);
    %>
    	<%=SistemaPesca.getHtmlSelect("sistemaPesca",-1) %>
      <%-- <input  type="text" size="50" maxlength="80" name="duns_type" value="<%=toHtmlValue(OrgDetails.getDuns_type()) %>">--%>
    </td>
  </tr>
	<tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
     Presenza a bordo di impianto di refrigerazione
    </td>
    <td>
     	<input  type="checkbox" name="flag_selezione">
    </td>
  </tr>
  	<tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
     Note
    </td>
    <td>
     	<input  type="text" size="50" maxlength="80" name="notes" value="<%= toHtmlValue(OrgDetails.getNotes()) %>">
    </td>
  </tr>
    <%-- <tr >
    <td class="formLabel" nowrap>
      Nome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="nomeRappresentante" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      Cognome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="cognomeRappresentante" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
      <td>
      	<input readonly type="text" id="dataNascitaRappresentante" name="dataNascitaRappresentante" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataNascitaRappresentante,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      	
      
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
  <tr > 
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di nascita</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtmlValue(OrgDetails.getLuogoNascitaRappresentante()) %>">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Telefono</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="telefonoRappresentante" value="<%= toHtmlValue(OrgDetails.getTelefonoRappresentante()) %>">
    </td>
    
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Fax</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="fax" value="<%= toHtmlValue(OrgDetails.getFax()) %>">
    </td>
    
  </tr> --%>
   </table>   
    <br/><br/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Sede Legale Armatore</dhv:label></strong>
	  <input type="hidden" name="address1type" value="1">
	  </th>
  </tr>
 <tr>
	<td nowrap class="formLabel" name="province1" id="prov2">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
    <input type = "text"  name="address1city" id="prov12">
	</td>
  	</tr>	
  	  	
  <tr>
    <td nowrap class="formLabel" id="indirizzo1">
      Indirizzo
    </td>
    <td>
      <input type="text" size="40" name="address1line1" maxlength="80" id="indirizzo12" value="">
    </td>
  </tr>
  
  
  <tr>
    <td nowrap class="formLabel" id="cap1">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address1zip" maxlength="5" value = "" id="cap">
    </td>
  </tr>  
  	 
  	<tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
          <% if (User.getSiteId() == 202) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="BN">          
          <%}%>
          <% if (User.getSiteId() == 201) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="AV">
          <%}%>
          <% if (User.getSiteId() == 203) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="CE">
          <%}%>
          <% if (User.getSiteId() == 204 || User.getSiteId() == 205 || User.getSiteId() == 206 ) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="NA">
          <%}%>
          <% if (User.getSiteId() == 207) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="SA">
          <%}%>
          <% if (User.getSiteId() == -1) { %>
          <input type="text"  size="28" name="address1state" maxlength="80" value="">
          <%}%>
    </td>
  </tr>

  
  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude2"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
       	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address1latitude"  name="address1latitude" size="30" value="">
 	
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td>
    	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address1longitude"   name="address1longitude" size="30" value="">
    </td>
    </tr>
    <tr style="display: block">
    <td colspan="2">
    <input id="coord1button" type="button" value="Calcola Coordinate"
    onclick="javascript:showCoordinate(document.getElementById('indirizzo12').value, document.forms['addAccount'].address1city.value,document.forms['addAccount'].address1state.value, document.forms['addAccount'].address1zip.value, document.forms['addAccount'].address1latitude, document.forms['addAccount'].address1longitude);" /> 
    </td>
    </tr>
   
</table>
	
	<br/>
	<br/>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Ormeggio Abituale</dhv:label></strong>
	  <input type="hidden" name="address2type" value="5">
	  </th>
  </tr>
 <tr>
	<td nowrap class="formLabel"  id="prov12">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
	<select  name="address2city" id="prov2">
	<option value="-1">Nessuna Selezione</option>
            
	 <%
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
                while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                	
        %>
                <option value="<%=prov4%>" ><%= prov4 %></option>	
              <%}%>
		
	</select> 
	</td>
  	</tr>	
  	  	
  <tr>
    <td nowrap class="formLabel" id="indirizzo1">
      Indirizzo
    </td>
    <td>
      <input type="text" size="40" name="address2line1" maxlength="80" id="indirizzo22" value="">
    </td>
  </tr>
  
  
  <tr>
    <td nowrap class="formLabel" id="cap1">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address2zip" maxlength="5" value = "" id="cap">
    </td>
  </tr>  
  	 
  	<tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
          <% if (User.getSiteId() == 202) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="BN">          
          <%}%>
          <% if (User.getSiteId() == 201) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="AV">
          <%}%>
          <% if (User.getSiteId() == 203) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="CE">
          <%}%>
          <% if (User.getSiteId() == 204 || User.getSiteId() == 205 || User.getSiteId() == 206 ) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="NA">
          <%}%>
          <% if (User.getSiteId() == 207) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="SA">
          <%}%>
          <% if (User.getSiteId() == -1) { %>
          <input type="text"  size="28" name="address2state" maxlength="80" value="">
          <%}%>
    </td>
  </tr>

  
  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude2"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
       	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address2latitude"   name="address2latitude" size="30" value="">
 	
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td>
    	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address2longitude"   name="address2longitude" size="30" value="">
    </td>
    </tr>
    <tr style="display: block">
    <td colspan="2">
    <input id="coord1button" type="button" value="Calcola Coordinate"
    onclick="javascript:showCoordinate(document.getElementById('indirizzo22').value, document.forms['addAccount'].address2city.value,document.forms['addAccount'].address2state.value, document.forms['addAccount'].address2zip.value, document.forms['addAccount'].address2latitude, document.forms['addAccount'].address2longitude);" /> 
    </td>
    </tr>
   
</table>
	
	<br/>
	<br/>
  
<input type="button" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';return verificaEsistenzaOperatore()">
<input type="button" value="Annulla" onClick="document.addAccount.action ='Imbarcazioni.do';document.addAccount.submit();">
</form>

