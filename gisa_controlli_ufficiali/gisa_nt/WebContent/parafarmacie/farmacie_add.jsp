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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.parafarmacie.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.parafarmacie.base.Organization" scope="request"/>
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
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        
        <script>
var campoLat;
	var campoLong;
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
</script>


        <script type="text/javascript">

			function popolaAsl()
			{

				//PopolaCombo.getValoriComboComuniProvincia(document.addAccount.siteId.value,setAslCallback) ;

				PopolaCombo.getValoriComboComuniAsl(document.addAccount.siteId.value,setAslCallback) ;

				if(document.addAccount.siteId.value==201)
					document.addAccount.address1state.value="AV";
				if(document.addAccount.siteId.value==202)
					document.addAccount.address1state.value="BN";
				if(document.addAccount.siteId.value==203)
					document.addAccount.address1state.value="CE";
				if(document.addAccount.siteId.value==204 || document.addAccount.siteId.value==205 || document.addAccount.siteId.value==206)
					document.addAccount.address1state.value="NA";
				if(document.addAccount.siteId.value==207)
					document.addAccount.address1state.value="SA";
		}


			
			/*setta l'asl in base al comune*/
			function popolaComuni()
			{
				PopolaCombo.getValoriComuniASL(document.addAccount.address1city.value,setComuniCallback) ;
				
		}

			function setAslCallback(returnValue)
	          {
				var select = document.addAccount.address1city; //Recupero la SELECT
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
				var select = document.addAccount.siteId; //Recupero la SELECT
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
	            	  if(document.addAccount.siteId.value==201)
	  					document.addAccount.address1state.value="AV";
	  				if(document.addAccount.siteId.value==202)
	  					document.addAccount.address1state.value="BN";
	  				if(document.addAccount.siteId.value==203)
	  					document.addAccount.address1state.value="CE";
	  				if(document.addAccount.siteId.value==204 || document.addAccount.siteId.value==205 || document.addAccount.siteId.value==206)
	  					document.addAccount.address1state.value="NA";
	  				if(document.addAccount.siteId.value==207)
	  					document.addAccount.address1state.value="SA";
	              }catch(e){
	            	  select.add(NewOpt); // Funziona solo con IE
	            	  if(document.addAccount.siteId.value==201)
	  					document.addAccount.address1state.value="AV";
	  				if(document.addAccount.siteId.value==202)
	  					document.addAccount.address1state.value="BN";
	  				if(document.addAccount.siteId.value==203)
	  					document.addAccount.address1state.value="CE";
	  				if(document.addAccount.siteId.value==204 || document.addAccount.siteId.value==205 || document.addAccount.siteId.value==206)
	  					document.addAccount.address1state.value="NA";
	  				if(document.addAccount.siteId.value==207)
	  					document.addAccount.address1state.value="SA";
	            	  
	              }
	              }
			          
				
		          }
</script>
<script language="JavaScript" TYPE="text/javascript">
  var indSelected = 0;
  var orgSelected = 1;
  var onLoad = 1;  
  
  function doCheck(form) {
	  
      if (form.dosubmit.value == "false") {
          alert('falso')
      return true;
    } else {
      return(checkForm(form));
    }
  }
  
  function checkForm(form) {
	
    var formTest = true;
    var formTest2 = true; 
    message = "";
    alertMessage = "";


    if (form.siteId.value == "-1" || form.siteId.value == "" ){
    	 //alert(!isNaN(form.address2longitude.value));
    		 message += "- Si Prega di selezionare un ASL di appartenenza\r\n";
    		 formTest = false;
    }
    if (form.ragioneSociale.value == "" ){
   	 //alert(!isNaN(form.address2longitude.value));
   		 message += "- Si Prega di Inserire il nome Impresa\r\n";
   		 formTest = false;
   }
    if (form.address1city.value == "-1" || form.address1city.value == "" ){
   	 //alert(!isNaN(form.address2longitude.value));
   		 message += "- Si Prega di selezionare  il comune di appartenenza\r\n";
   		 formTest = false;
   }
    if (form.address1line1.value == ""){
      	 //alert(!isNaN(form.address2longitude.value));
      		 message += "- Si Prega di Inserire un indirizzo\r\n";
      		 formTest = false;
      }
   	 
   	 if (formTest && formTest2){
   		 var test = document.addAccount.selectedList;
	     if (test != null) {
	       selectAllOptions(document.addAccount.selectedList);
	     }
	     if(alertMessage != "") {
	       confirmAction(alertMessage);
	     }
	     return true;
   	   	
  }else {
   	    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
   	    return false;
   	 }     	 
  }
</script>
<dhv:evaluate if='<%= (request.getParameter("form_type") == null || "parafarmacie".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.ragioneSociale.focus();updateFormElements(0);">
</dhv:evaluate>
<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.ragioneSociale.focus();updateFormElements(1);">
</dhv:evaluate>
<form name="addAccount" action="Parafarmacie.do?command=InsertFcie&auto-populate=true"   method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate if="<%= !popUp %>">  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Parafarmacie.do?command=SearchFormFcie"><dhv:label name="">Farmacie / Grossisti / Parafarmacie</dhv:label></a> >
<dhv:label name="">Aggiungi Operatore</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';return doCheck(document.addAccount);">
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Parafarmacie.do?command=SearchFormFcie';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<br /><br />
 <% SiteList.setJsEvent("onchange='javascript:popolaAsl();'"); %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi una Nuova Operatore Parafarmacia</dhv:label></strong>
    </th>
  </tr>
 
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.site">Site</dhv:label>
      </td>
      <td>
        <dhv:evaluate if="<%= User.getSiteId() == -1 %>" >
          <%= SiteList.getHtmlSelect("siteId",OrgDetails.getSiteId()) %>
        </dhv:evaluate>
        <dhv:evaluate if="<%= User.getSiteId() != -1 %>" >
           <%= SiteList.getSelectedValue(User.getSiteId()) %>
          <input type="hidden" name="siteId" value="<%=User.getSiteId()%>" >
        </dhv:evaluate>
      </td>
    </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>

<tr>
    <td nowrap class="formLabel" name="ragioneSociale" id="ragioneSociale">
      <dhv:label name="tipologia">Tipologia</dhv:label>
    </td>
    <td>
      <select name="tipologia">
      	<option value="Farmacia">Farmacia</option>
      	<option value="Grossista">Grossista</option>
     	<option value="Parafarmacia">Parafarmacia</option>
      </select>
    </td>
  </tr>

  <dhv:include name="ragioneSociale" none="true">
  <tr>
    <td nowrap class="formLabel" name="ragioneSociale" id="ragioneSociale">
      <dhv:label name="">Impresa</dhv:label>
    </td>
    <td>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" maxlength="80" name="ragioneSociale" value="<%= toHtmlValue(OrgDetails.getRagioneSociale()) %>">
    </td>
  </tr>
  </dhv:include>
  
  
    	
  <tr> <td nowrap class="formLabel">Stato</td>
  <td>
  <table class = "noborder"><tr><td>
    	<select name="stato" >
    	    <option value="Attivo" >Attivo</option>
    		<option value="Sospeso" >Sospeso</option>
    		<option value="Revocato" >Revocato</option>
    	</select></td>
    	<td>
    	
    	<% Timestamp data_stato = new Timestamp(System.currentTimeMillis()); %>
    	In Data  <zeroio:dateSelect form="addAccount"  field="dataCambioStato" timestamp="<%= data_stato %>" showTimeZone="false" />
    	
    	
    	</td>
    	
    	</tr></table>
    </td>
  </tr>
  
   
	
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="">Comune</dhv:label>
    </td>
    <td>

      <select  name="address1city" id="prov12" onchange="javascript:popolaComuni();">

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
    <td nowrap class="formLabel">
      <dhv:label name="">Indirizzo</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address1line1" id="address1line1" maxlength="80" >
       <input type = "hidden" name = "address1type" value = "5">
              <input type = "hidden" name = "address1id" value = "-1">
      
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="">Provincia</dhv:label>
    </td>
    <td>
    	  <input type="text" readonly="readonly" size="2" name="address1state" id="address1state" maxlength="5">          
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="">Latitudine</dhv:label></td>
    <td>
    	<input type="text" id="latitudine" name="address1latitude" size="30" readonly="readonly">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="">Longitudine</dhv:label></td>
    <td><input type="text" id="longitudine" name="address1longitude" size="30"  readonly="readonly">
    </td>
  </tr>
  
  <tr style="display: block">
					<td colspan="2"><input id="coord1button" type="button"
						value="Calcola Coordinate"
						onclick="javascript:showCoordinate(document.getElementById('address1line1').value, document.forms['addAccount'].address1city.value,document.forms['addAccount'].address1state.value, '', document.forms['addAccount'].address1latitude, document.forms['addAccount'].address1longitude);" />
					</td>
				</tr>
				
</table>
<%
  boolean noneSelected = false;
%>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Commercio Ingrosso</dhv:label></strong>
	  </th>
  </tr>
   <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.datae1">Data Ricezione Autorizzazione</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="dataRicIngrosso" timestamp="<%= OrgDetails.getDataRicIngrosso() %>" showTimeZone="false" />
       </td>
    </tr>
    <tr>
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="">Numero Autorizzazione</dhv:label>
    </td>
    <td><input type="text" id="numRicIngrosso1" name="numRicIngrosso" size="30" ></td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Vendita Dettaglio</dhv:label></strong>
	  </th>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.datae1">Data Ricezione Autorizzazione</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="dataRicDettaglio" timestamp="<%= OrgDetails.getDataRicDettaglio() %>" showTimeZone="false" />
      </td>
    </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Numero Autorizzazione</dhv:label>
    </td>
    <td>
     <input type="text" id="numRicDettaglio1" name="numRicDettaglio" size="30" >
    </td>
  </tr>
</table>
<br />
<dhv:evaluate if="<%= !popUp %>">  
<br />
</dhv:evaluate>  
<br />
<input type="hidden" name="onlyWarnings" value='<%=(OrgDetails.getOnlyWarnings()?"on":"off")%>' />
<%= addHiddenParams(request, "actionSource|popup") %>
<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';return doCheck(document.addAccount);" />
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Parafarmacie.do?command=SearchFormFcie';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true" />
</form>
</body>