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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.acquedirete.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAcque" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="AddressSedeOperativa" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.acquedirete.base.Organization" scope="request"/>
<jsp:useBean id="Address" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

       
<script language="JavaScript" TYPE="text/javascript">
  var indSelected = 0;
  var orgSelected = 1;
  var onLoad = 1;  
  
  function doCheck(form) {
	  
      if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  
  function checkForm(form) {
	
    var formTest = true;
    message = "";
    alertMessage = "";
    	
    if(form.name.value =='') {
        message += "- Denominazione richiesta\r\n";
        formTest = false;
    }

    if(form.ente_gestore.value =='') {
        message += "- Ente Gestore \r\n";
        formTest = false;
    }
    if(form.address1line1.value =='') {
        message += "- Indirizzo \r\n";
        formTest = false;
    }
    
    if(form.tipo_struttura.value =='-1') {
        message += "- Tipologia \r\n";
        formTest = false;
    }
    
    if((form.siteId.value =='-1' || form.siteId.value =='') )
    {
    	message += '- ASL richiesta\n' ;
    	formTest = false ;
    }
    
    if(form.address1city.value =='-1' || form.address1city.value=='')
    {
    	message += '- Inserire il comune\n' ;
    	formTest = false ;
    }

    if (form.address1latitude.value == '' || isNaN(form.address1latitude.value)){
    	message += "- Inserire la Latitudine, con un valore compreso tra 39.988475 e 41.503754 \r\n";
		formTest = false;
    } else {
		 if ((form.address1latitude.value < 39.988475) || (form.address1latitude.value > 41.503754)){
		       	message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 \r\n";
		        formTest = false;
		  }		
	}	    
  	 
  	 if (form.address1longitude.value == '' || isNaN(form.address1longitude.value)){
  		message += "- Inserire la Longitudine, con un valore compreso tra 13.7563172 e 15.8032837 \r\n";
		formTest = false;
  	 } else {
  		 if ((form.address1longitude.value < 13.7563172) || (form.address1longitude.value > 15.8032837)){
  	      	message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 \r\n";
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
</script>


<script>

			function popolaComboComuni()
			{
				idAsl = document.addAccount.siteId.value;
				
					PopolaCombo.getValoriComboComuniAsl(idAsl,setComuniComboCallback) ;
				
			}

	          function setComuniComboCallback(returnValue)
	          {
	        	  var select = document.forms['addAccount'].address1city; //Recupero la SELECT
	              
	        	 

	              //Aggiungo l'elemento option
	             
	              //Azzero il contenuto della seconda select
	              for (var i = select.length - 1; i >= 0; i--)
	            	  select.remove(i);
            	  
	              var NewOpt = document.createElement('option');
	              NewOpt.value = '-1'; // Imposto il valore
	              NewOpt.text = '-TUTTI I COMUNI-'; // Imposto il testo
	              try
	              {
	            	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	              }catch(e){
	            	  select.add(NewOpt); // Funziona solo con IE
	              }
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

</script>

<form name="addAccount" action="AcqueRete.do?command=Insert&auto-populate=true"   method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
<dhv:evaluate if="<%= !popUp %>">  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="AcqueRete.do?command=SearchForm"><dhv:label name="">Acque Di Rete</dhv:label></a> >
<dhv:label name="">Aggiungi punto di prelievo acqua di rete</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>

<input type="button" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';return checkForm(document.addAccount);">
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='"AcqueRete.do?command=SearchForm"';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<br /><br />
 <%-- SiteList.setJsEvent("onchange='javascript:popolaAsl();'"); --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi punto di prelievo acqua di rete</dhv:label></strong>
    </th>
  </tr>
   <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">ASL</dhv:label>
      </td>
      <td>
       <% SiteList.setJsEvent("onchange='popolaComboComuni()'");%>
        <dhv:evaluate if="<%= User.getSiteId() == -1 %>" >
          <%= SiteList.getHtmlSelect("siteId",OrgDetails.getSiteId()) %>
        </dhv:evaluate>
        <dhv:evaluate if="<%= User.getSiteId() != -1 %>" >
         
           <%= SiteList.getSelectedValue(User.getSiteId()) %>
          <input type="hidden" name="siteId" value="<%=User.getSiteId()%>" >
        </dhv:evaluate>
      </td>
  </tr>
  <dhv:include name="name" none="true">
  <tr>
    <td nowrap class="formLabel" name="name" id="name">
      <dhv:label name="">Denominazione</dhv:label>
    </td>
    <td>
      <input type="text" size="50"  name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font>
    </td>
  </tr>
  </dhv:include>
   <tr>
    <td nowrap class="formLabel" name="name" id="name">
      <dhv:label name="">Tipologia</dhv:label>
    </td>
    <td>
    	<%= TipoAcque.getHtmlSelect("tipo_struttura",OrgDetails.getTipo_struttura()) %><font color="red">*</font>
    </td>
  </tr>
   <tr>
    <td nowrap class="formLabel" name="name" id="name">
      <dhv:label name="">Ente Gestore</dhv:label>
    </td>
    <td>
      <input type="text" size="50"  name="ente_gestore" value=""><font color="red">*</font>
    </td>
  </tr>
  
     <tr>
    <td nowrap class="formLabel" name="name" id="name">
      <dhv:label name="">Stato</dhv:label>
    </td>
    <td>
			<select name = "stato">
				<option value = "0">Attivo</option>
				<option value = "1">Non Attivo</option>
			</select>
    </td>
  </tr>  
  
  <br>
  
  </table>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Ubicazione</dhv:label></strong>
	  <input type="hidden" name="address1type" value="5">
	  </th>
  </tr>
 <tr>
	<td nowrap class="formLabel" name="address1city" id="address1city">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
	<select  name="address1city" id="address1city">
	<option value="-1">Nessuna Selezione</option>
            
	 <%
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
                while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                	
        %>
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(AddressSedeOperativa.getCity())) {%> selected="selected" <%} %>><%= prov4 %></option>	
              <%}%>
		
	</select> 
	<font color="red">*</font>
	</td>
  	</tr>	
   
  <tr>
    <td nowrap class="formLabel" id="indirizzo1">
      Indirizzo
    </td>
    <td>
      <input type="text" size="40" name="address1line1" maxlength="80" id="indirizzo22" value=""><font color="red">*</font>
    </td>
  </tr>
  <tr>
     <td class="formLabel" nowrap id="latitude2"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" id="address1latitude" name="address1latitude" size="30" value="" ><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude1"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" id="address1longitude" name="address1longitude" size="30" value=""><font color="red">*</font>
     &nbsp;&nbsp;
     <a href="#" onClick="window.open('docs/GuidaCoordinate.pdf','width=600,height=300'); return false;"> Guida al calcolo delle coordinate da Smartphone</a>
  </tr>
  
  <!-- tr>
    <td nowrap class="formLabel" id="cap1">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address1zip" maxlength="5" value = "" id="cap">
    </td>
  </tr-->  
  	 
  	<!-- tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
          <input type="text"  size="28" name="address1state" maxlength="80" value="">
          
    </td>
  </tr-->
  
  
   
</table>
<br>
<input type="button" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';return checkForm(document.addAccount)">
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='"AcqueRete.do?command=SearchForm"';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true" />
</form>
</body>