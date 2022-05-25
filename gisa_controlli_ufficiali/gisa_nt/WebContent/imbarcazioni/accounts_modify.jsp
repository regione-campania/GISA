<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>

<%@page import="org.aspcfs.modules.imbarcazioni.base.OrganizationAddress"%>

<%@page import="java.util.Date"%><jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="specieAnimali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipoStabulatorio" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.imbarcazioni.base.Organization" scope="request"/>
<jsp:useBean id="SpecieAnimaliSelezionati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStabulatorioSelezionati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoPesca" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SistemaPesca" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
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


function verificaEsistenzaOperatore(numRegAttuale)
{
	num_registrazione = document.getElementById('accountNumber').value;
	if (numRegAttuale!=num_registrazione)
		PopolaCombo.controlloEsuistenzaNumRegistrazioneImbarcazioni(num_registrazione,verificaEsistenzaOperatoreCallback ) ;
	else
		return checkForm(document.addAccount);
	
	}

function verificaEsistenzaOperatoreCallback(value)
{
	//Se non esiste...
	if (value == false)
	{
		 
			 return checkForm(document.addAccount);
		
	}
	else
	{
		/*if (confirm('Numero Registrazione assegnato a un altro operatore , sicuro di voler salvare ? ')==true)
		{
			
				 return checkForm(document.addAccount);
			
		}*/
			alert('Non e\' possibile modificare questa imbarcazione in quanto\n l\'identificativo della barca risulta assegnato ad un altro operatore. \n');
			return false ;
			//return checkForm(document.addAccount);
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

/*if(form.address1city.value =='')
{
	message += '- Inserire il comune per sede Legale\n' ;
	formTest = false ;
}

if(form.address2city.value =='-1')
{
	message += '- Inserire il comune per sede Operativa\n' ;
	formTest = false ;
}

if(form.address2city.value =='' )
{
	message += '- Selezionare il comune della sede operativa \n' ;
	formTest = false ;
}


if(form.siteId.value =='-1' || form.siteId.value =='')
{
	message += '- Selezionare l\'asl di appartenenza \n' ;
	formTest = false ;
}
*/
if(form.accountNumber.value =='' )
{
	message += '- Inserire il numero identificativo della barca \n' ;
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
} */  


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

<body >
<form id = "addAccount" name="addAccount" action="Imbarcazioni.do?command=Update&auto-populate=true" method="post">
<input type="hidden" name="dosubmit" value="true" />
  
 <input type = "hidden" name = "orgId" value="<%=OrgDetails.getOrgId()  %>"> 
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Imbarcazioni.do">Imbarcazioni</a> >
<a href="Imbarcazioni.do?command=Details&orgId=<%=OrgDetails.getOrgId() %>">Scheda</a> >
Modifica
</td>
</tr>
</table>

<input type="button" value="Aggiorna" name="Save" onClick="this.form.dosubmit.value='true';return verificaEsistenzaOperatore('<%=OrgDetails.getAccountNumber() %>')">
<input type="button" value="Annulla" onClick="document.addAccount.action ='Imbarcazioni.do?command=Details&orgId<%=OrgDetails.getOrgId() %>';document.addAccount.submit();">

<br/><br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Modifica Imbarcazione</strong>
    </th>
  </tr>
  <%-- ASL DA NON GESTIRE
  <tr>
    <td nowrap class="formLabel" >
      ASL
    </td>
    <td>
     <%
   if (OrgDetails.getSiteId()>0)
   {
	   %>
	   <input type = "hidden" name = "siteId" value = "<%=OrgDetails.getSiteId() %>"/>
	   <%=SiteList.getSelectedValue(OrgDetails.getSiteId()) %>
	   <%
   }else
   {
	   %>
	   <%=SiteList.getHtmlSelect("siteId",OrgDetails.getSiteId()) %>
	   <%
   }
   
   %>
        
      
     
    </td>
  </tr>
  --%>
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
     Ufficio Marittimo di provenienza
    </td>
    <td>
     	<input  type="radio" name="fuori_regione" value="false" <% if(!OrgDetails.isFuori_regione()) { %> checked=""  <% } %>>In Regione&nbsp;
     	<input  type="radio" name="fuori_regione" value="true" <% if(OrgDetails.isFuori_regione()) { %> checked="checked" <% } %>>Fuori Regione
    </td>
  </tr>
  <tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"> Data iscrizione agli Uffici Marittimi
				</td>
				<td>
				<input readonly type="text" id="dataPresentazione" name="dataPresentazione" size="10" value = "<%=OrgDetails.getDataPresenazioneString() %>" />
		<a href="#" onClick="cal19.select(document.forms[0].dataPresentazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				
				</td>
			</tr>
	<tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      Identificativo della barca U.E.
    </td>
    <td>
      <input  type="text" size="50" maxlength="80" id = "accountNumber" name="accountNumber" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>">
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
      <input  type="text" size="50" maxlength="80" name="autorizzazione" value="<%=toHtmlValue(OrgDetails.getAutorizzazione()) %>">
    </td>
  </tr>
  <tr class="containerBody">
				<td nowrap class="formLabel" name="orgname1" id="orgname1"> Data cancellazione dagli Uffici Marittimi
				</td>
				<td>
				<input readonly type="text" id="date2" name="date2" size="10" value = "<%=OrgDetails.getData2String()%>"/>
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
      <input  type="text" size="50" maxlength="80" name="capacita_max" value="<%=(OrgDetails.getCapacita_max() != null) ? toHtmlValue(OrgDetails.getCapacita_max()) : ""%>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      Tipo di pesca
    </td>
    <td>
    	 <%
       TipoPesca.setMultiple(true);
    	 TipoPesca.setSelectSize(9);
       
       %>
          <%
          LookupList selTipoPesca = new LookupList();
          if(OrgDetails.getTipoPesca().size()!=0) { 
       			%> 
				<%
						
						HashMap<Integer,String> tipiPesca = OrgDetails.getTipoPesca();
						Set<Integer> setkiavi = tipiPesca.keySet();
						Iterator<Integer> iteraTipiPesca=setkiavi.iterator();
						
						while(iteraTipiPesca.hasNext()){
							int chiave = iteraTipiPesca.next();
							String value=tipiPesca.get(chiave);					
						
							LookupElement elem = new LookupElement();
							elem.setCode(chiave);
							elem.setDescription(value);
							selTipoPesca.add(elem);
							} %> 
				<%=TipoPesca.getHtmlSelect("tipoPesca",selTipoPesca) %>
			<% } else {
				%>
	   				<%=TipoPesca.getHtmlSelect("tipoPesca",-1) %>
			<% } %>
			
			
    
      <%-- <input  type="text" size="50" maxlength="80" name="tipologia_strutt" value="<%=toHtmlValue(OrgDetails.getTipo_struttura())%>">
      --%>
    </td>
  </tr>
    <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      Sistema di pesca
    </td>
    <td>
    	 <%
       SistemaPesca.setMultiple(true);
       SistemaPesca.setSelectSize(9);
       
       %>
          <%
          LookupList selSistemaPesca = new LookupList();
          if(OrgDetails.getSistemaPesca().size()!=0) { 
       			%> 
				<%
						
						HashMap<Integer,String> sistemaPesca = OrgDetails.getSistemaPesca();
						Set<Integer> setkiavi = sistemaPesca.keySet();
						Iterator<Integer> iteraSistemaPesca=setkiavi.iterator();
						
						while(iteraSistemaPesca.hasNext()){
							int chiave = iteraSistemaPesca.next();
							String value = sistemaPesca.get(chiave);					
						
							LookupElement elem = new LookupElement();
							elem.setCode(chiave);
							elem.setDescription(value);
							selSistemaPesca.add(elem);
							} %> 
				
				<%=SistemaPesca.getHtmlSelect("sistemaPesca",selSistemaPesca) %>
			
			<% } 
			
			else {
				%>
	   				<%=SistemaPesca.getHtmlSelect("sistemaPesca",-1) %>
			<% } %>
			
			
    <%-- <input  type="text" size="50" maxlength="80" name="duns_type" value="<%= toHtmlValue(OrgDetails.getDuns_type())%>"> --%>
    </td>
   </tr>
	<tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
     Presenza a bordo di impianto di refrigerazione
    </td>
    <td>
     	<input  type="checkbox" name="flag_selezione" <% if(OrgDetails.isFlag_selezione()) { %> checked="checked" <% } %>>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
     Note
    </td>
    <td>
     	<input  type="text" size="50" maxlength="80" name="notes" value="<%= toHtmlValue(OrgDetails.getNotes()) %>">
    </td>
  </tr>
   </table>
     
  <br/><br/>
  <%-- 
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Rappresentante Legale</strong>
    </th>
  </tr>
      <tr >
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
      <%
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      String dataNascita = "" ;
      if (OrgDetails.getDataNascitaRappresentante()!=null)
    	  dataNascita = sdf.format(new Date(OrgDetails.getDataNascitaRappresentante().getTime()));
       %>
      	<input readonly type="text" id="dataNascitaRappresentante" name="dataNascitaRappresentante" size="10" value = "<%=dataNascita %>"/>
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
    
  </tr></table>
  --%>
      
 
    <br/><br/>
    <%
    
    OrganizationAddress so = null ;
    OrganizationAddress sl = null ;
    if(OrgDetails.getAddressList().size()==2 || OrgDetails.getAddressList().size()==3 ) //provieni da un import da impresa con locale collegato
    {
    	OrganizationAddress temp = (OrganizationAddress)OrgDetails.getAddressList().get(0);
    	if(temp.getType()==1)
    	{
    		sl=temp;
    		so=(OrganizationAddress)OrgDetails.getAddressList().get(1);
    	}
    		
    	else
    	{
    		so=temp;
    		sl=(OrganizationAddress)OrgDetails.getAddressList().get(1);
    	}
    		
    }
    else 
    {
    	
    Iterator anumber = OrgDetails.getAddressList().iterator();
   	  while (anumber.hasNext()) {
   	     OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
   	     
   	 	if (thisAddress.getType()==1)
   	  		sl = thisAddress;
   	  	else if (thisAddress.getType()==5)
   	  		so = thisAddress;
    }
   	  }
    %>
    
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
  
    <th colspan="2">
	    <strong><dhv:label name="">Sede Legale Armatore</dhv:label></strong>
	  <input type="hidden" name="address1type" value="1"/>
	  <input type="hidden" name = "address1id" value = "<%=sl.getId() %>"/>
	  </th>
  </tr>
 <tr>
	<td nowrap class="formLabel" name="province1" id="prov2">
      <dhv:label name="._add.City">Comune</dhv:label>
    </td> 
    <td > 
	<input type = "text"  name="address1city" id="prov12" value = "<%=sl.getCity()%>">
	</td>
  	</tr>	
  	  	
  <tr>
    <td nowrap class="formLabel" id="indirizzo1">
      <dhv:label name="._add.AddressLine1" >Indirizzo</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address1line1" value="<%=sl.getStreetAddressLine1() %>" maxlength="80" id="indirizzo12">
    </td>
  </tr>
  
  
  <tr>
    <td nowrap class="formLabel" id="cap1">
      <dhv:label name="._add.ZipPostalCode">CAP</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address1zip" value = "<%=sl.getZip() %>" maxlength="5"id="cap">
    </td>
  </tr>  
  	 
  	<tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="._add.StateProvince">Provincia</dhv:label>
    </td>
    <td>
          <% if (User.getSiteId() == 202) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="BENEVENTO">         
          <%}%>
          <% if (User.getSiteId() == 201) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="AVELLINO">
          <%}%>
          <% if (User.getSiteId() == 203) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="CASERTA">
          <%}%>
          <% if (User.getSiteId() == 204 || User.getSiteId() == 205 || User.getSiteId() == 206 ) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="NAPOLI">
          <%}%>
          <% if (User.getSiteId() == 207) { %>
          <input type="text" readonly="readonly" size="28" name="address1state" maxlength="80" value="SALERNO">
          <%}%>
          <% if (User.getSiteId() == -1) { %>
          <input type="text"  size="28" name="address1state" maxlength="80"value="<%=(sl.getState()!=null && ! sl.getState().equals("null")) ? toHtml(sl.getState()) : "" %>">
          <%}%>
    </td>
  </tr>

  
  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude2"><dhv:label name=".address.latitude">Latitudine</dhv:label></td>
    <td>
       	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12"><font color="red">*</font>--%>
    	<input type="text" id="address1latitude"  readonly="readonly" name="address1latitude" size="30" value="<%=(sl.getLatitude() != 0.0) ? sl.getLatitude() : "" %>">
 	
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name=".address.longitude">Longitudine</dhv:label></td>
    <td>
    	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12"><font color="red">*</font>--%>
    	<input type="text" id="address1longitude"  readonly="readonly" name="address1longitude" value ="<%=(sl.getLongitude()!= 0.0) ? sl.getLongitude() : "" %>" size="30" value="">
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
	    <strong><dhv:label name="">Ormeggio abituale</dhv:label></strong>
	  <input type="hidden" name="address2type" value="5">
	  <input type = "hidden" name = "address2id" value = "<%=so.getId() %>">
	  </th>
  </tr>
 <tr>
	<td nowrap class="formLabel"  id="prov12">
      <dhv:label name="._add.City">Comune</dhv:label>
    </td> 
    <td > 
	<select  name="address2city" id="prov2" onchange="popolaComuni()">
	<option value="-1">Nessuna Selezione</option>
            
	 <%
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
                while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                	
        %>
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(so.getCity())){ %>selected="selected"<%} %>  ><%= prov4 %></option>	
              <%}%>
		
	</select> 
	<font color="red">*</font>
	</td>
  	</tr>	
  	  	
  <tr>
    <td nowrap class="formLabel" id="indirizzo1">
      <dhv:label name="._add.AddressLine1" >Indirizzo</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address2line1" maxlength="80" value = "<%=so.getStreetAddressLine1() %>" id="indirizzo22" value="">
    </td>
  </tr>
  
  
  <tr>
    <td nowrap class="formLabel" id="cap1">
      <dhv:label name="._add.ZipPostalCode">CAP</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address2zip" maxlength="5" value = "<%=so.getZip() %>" id="cap">
    </td>
  </tr>  
  	 
  	<tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="._add.StateProvince">Provincia</dhv:label>
    </td>
    <td>
          <% if (User.getSiteId() == 202) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="BENEVENTO">         
          <%}%>
          <% if (User.getSiteId() == 201) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="AVELLINO">
          <%}%>
          <% if (User.getSiteId() == 203) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="CASERTA">
          <%}%>
          <% if (User.getSiteId() == 204 || User.getSiteId() == 205 || User.getSiteId() == 206 ) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="NAPOLI">
          <%}%>
          <% if (User.getSiteId() == 207) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="SALERNO">
          <%}%>
          <% if (User.getSiteId() == -1) { %>
          <input type="text"  size="28" name="address2state" maxlength="80" value="<%=(so.getState()!=null && ! so.getState().equals("null")) ?toHtml(so.getState()) : "" %>">
          <%}%>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude2"><dhv:label name=".address.latitude">Latitudine</dhv:label></td>
    <td>
       	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12"><font color="red">*</font>--%>
    	<input type="text" id="address2latitude" readonly="readonly" name="address2latitude" size="30" value="<%=(so.getLatitude() != 0.0) ? so.getLatitude() : ""%>">
 	
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name=".address.longitude">Longitudine</dhv:label></td>
    <td>
    	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12"><font color="red">*</font>--%>
    	<input type="text" id="address2longitude"  readonly="readonly" name="address2longitude" size="30" value="<%=(so.getLongitude() != 0.0) ? so.getLongitude() : ""%>">
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
  
<input type="button" value="Aggiorna" name="Save" onClick="this.form.dosubmit.value='true';return verificaEsistenzaOperatore('<%=OrgDetails.getAccountNumber() %>')">
<input type="button" value="Annulla" onClick="document.addAccount.action ='Imbarcazioni.do?command=Details&orgId<%=OrgDetails.getOrgId() %>';document.addAccount.submit();">
</form>

</body>