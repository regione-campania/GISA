<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.osmregistrati.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.osmregistrati.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="AccountSizeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
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
<script language="JavaScript">
  indSelected = 0;
  orgSelected = 0; 
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }

  function visibilitaDescrizioneAttivita() {

	  attivita = addAccount.impianto.options;
	  visibile = false ;	
	 
	  for(i = 0 ;i<attivita.length;i++){
		  
			if(attivita[i].selected == true && (attivita[i].value=="1" || attivita[i].value=="18") )
			{
				visibile = true ;
				break;
			}
			
		  }
		 
	  if(visibile == true){
		  
	        document.getElementById("descrizione_attivita").style.display="block";
	        
	     }else {
	    	
	    	 document.getElementById("descrizione_attivita").style.display="none";

		     }
	  
  }
  
  function initializeClassification() {
  <% if (OrgDetails.getIsIndividual()) { %>
      indSelected = 1;
      updateFormElements(1);
  <%} else {%>
      orgSelected = 1;
      updateFormElements(0);
  <%}%>
  }
  function resetFormElements() {
    if (document.getElementById) {
      elm1 = document.getElementById("nameFirst1");
      elm2 = document.getElementById("nameMiddle1");
      elm3 = document.getElementById("nameLast1");
      elm4 = document.getElementById("orgname1");
      elm5 = document.getElementById("ticker1");
      elm6 = document.getElementById("accountSize1");
      elm7 = document.getElementById("listSalutation1");
      elm8 = document.getElementById("primarycontact1");
      if (elm1) {
        elm1.style.color = "#000000";
        document.addAccount.nameFirst.style.background = "#ffffff";
        document.addAccount.nameFirst.disabled = false;
      }
      if (elm2) {
        elm2.style.color = "#000000";
        document.addAccount.nameMiddle.style.background = "#ffffff";
        document.addAccount.nameMiddle.disabled = false;
      }
      if (elm3) {
        elm3.style.color = "#000000";
        document.addAccount.nameLast.style.background = "#ffffff";
        document.addAccount.nameLast.disabled = false;
      }
      if (elm4) {
        elm4.style.color = "#000000";
        document.addAccount.name.style.background = "#ffffff";
        document.addAccount.name.disabled = false;
      }
      if (elm5) {
        elm5.style.color = "#000000";
        document.addAccount.ticker.style.background = "#ffffff";
        document.addAccount.ticker.disabled = false;
      }
      if (elm6) {
        elm6.style.color = "#000000";
        document.addAccount.accountSize.style.background = "#ffffff";
        document.addAccount.accountSize.disabled = false;
      }
      if (elm7) {
        elm7.style.color = "#000000";
        document.addAccount.listSalutation.style.background = "#ffffff";
        document.addAccount.listSalutation.disabled = false;
      }
      if (elm8) {
        elm8.style.color = "#000000";
        document.addAccount.primaryContactId.style.background = "#ffffff";
        document.addAccount.primaryContactId.disabled = false;
      }
    }
  }
  function updateFormElements(index) {
    if (document.getElementById) {
      <dhv:include name="osmregistrati-firstname" none="true">
        elm1 = document.getElementById("nameFirst1");
      </dhv:include>
      <dhv:include name="osmregistrati-middlename" none="true">
        elm2 = document.getElementById("nameMiddle1");
      </dhv:include>
      <dhv:include name="osmregistrati-lastname" none="true">
        elm3 = document.getElementById("nameLast1");
      </dhv:include>
      elm4 = document.getElementById("orgname1");
      elm5 = document.getElementById("ticker1");
      elm6 = document.getElementById("accountSize1");
      elm7 = document.getElementById("listSalutation1");
      elm8 = document.getElementById("primarycontact1");
      if (index == 1) {
        indSelected = 1;
        orgSelected = 0;        
        resetFormElements();
        if (elm4) {
          elm4.style.color="#cccccc";
          document.addAccount.name.style.background = "#cccccc";
          document.addAccount.name.value = "";
          document.addAccount.name.disabled = true;
        }
        if (elm5) {
          elm5.style.color="#cccccc";
          document.addAccount.ticker.style.background = "#cccccc";
          document.addAccount.ticker.value = "";
          document.addAccount.ticker.disabled = true;
        }
        if (elm6) {
          elm6.style.color="#cccccc";
          document.addAccount.accountSize.style.background = "#cccccc";
          document.addAccount.accountSize.value = "";
          document.addAccount.accountSize.disabled = true;
        }
      } else {
        indSelected = 0;
        orgSelected = 1;
        resetFormElements();
        if (elm1) {
          elm1.style.color = "#cccccc";
          document.addAccount.nameFirst.style.background = "#cccccc";
          document.addAccount.nameFirst.value = "";
          document.addAccount.nameFirst.disabled = true;
        }
        if (elm2) {
          elm2.style.color = "#cccccc";  
          document.addAccount.nameMiddle.style.background = "#cccccc";
          document.addAccount.nameMiddle.value = "";
          document.addAccount.nameMiddle.disabled = true;
        }
        if (elm3) {
          elm3.style.color = "#cccccc";      
          document.addAccount.nameLast.style.background = "#cccccc";
          document.addAccount.nameLast.value = "";
          document.addAccount.nameLast.disabled = true;
        }
        if (elm7) {
          elm7.style.color = "#cccccc";
          document.addAccount.listSalutation.style.background = "#cccccc";
          document.addAccount.listSalutation.value = -1;     
          document.addAccount.listSalutation.disabled = true;
        }
        if (elm8) {
          elm8.style.color = "#cccccc";
          document.addAccount.primaryContactId.style.background = "#cccccc";
          document.addAccount.primaryContactId.selectedIndex = 0;
          document.addAccount.primaryContactId.disabled = true;
        }
      }
    }
  }
  //-------------------------------------------------------------------
  // getElementIndex(input_object)
  //   Pass an input object, returns index in form.elements[] for the object
  //   Returns -1 if error
  //-------------------------------------------------------------------
  
 
  
  function getElementIndex(obj) {
    var theform = obj.form;
    for (var i=0; i<theform.elements.length; i++) {
      if (obj.name == theform.elements[i].name) {
        return i;
      }
    }
    return -1;
  }
  // -------------------------------------------------------------------
  // tabNext(input_object)
  //   Pass an form input object. Will focus() the next field in the form
  //   after the passed element.
  //   a) Will not focus to hidden or disabled fields
  //   b) If end of form is reached, it will loop to beginning
  //   c) If it loops through and reaches the original field again without
  //      finding a valid field to focus, it stops
  // -------------------------------------------------------------------
  function tabNext(obj) {
    if (navigator.platform.toUpperCase().indexOf("SUNOS") != -1) {
      obj.blur(); return; // Sun's onFocus() is messed up
      }
    var theform = obj.form;
    var i = getElementIndex(obj);
    var j=i+1;
    if (j >= theform.elements.length) { j=0; }
    if (i == -1) { return; }
    while (j != i) {
      if ((theform.elements[j].type!="hidden") && 
          (theform.elements[j].name != theform.elements[i].name) && 
        (!theform.elements[j].disabled)) {
        theform.elements[j].focus();
        break;
        }
      j++;
      if (j >= theform.elements.length) { j=0; }
    }
  }  
  function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";
    
    if (form.name){
        if ((orgSelected == 1) && (checkNullString(form.name.value))){
          message += "- Ragione Sociale richiesta\r\n";
          formTest = false;
        }
        	
      }
      
      if (checkNullString(form.partitaIva.value)){
       	  if (checkNullString(form.codiceFiscale.value)){
           	message += "- Partita IVA/Codice Fiscale richiesto\r\n";
          	 formTest = false;
       	  }
         }
      
      if (form.impianto.value == "-1"){
     	 
         	message += "- Attività richiesto\r\n";
        	 formTest = false;
     	  
       }
      if (form.statoLab.value == "-1"){
         	message += "- Stato OSM richiesto\r\n";
        	 formTest = false;
     	  
       }
    	if (form.partitaIva && form.partitaIva.value!=""){
       	 //alert(!isNaN(form.address1latitude.value));
       		if ((orgSelected == 1)  ){
       			if (isNaN(form.partitaIva.value)){
        			 message += "- Valore errato per il campo Partita IVA. Si prega di inserire solo cifre\r\n";
        				 formTest = false;
        			}		 
       		}
    	 }   
    	
      
        if (checkNullString(form.date2.value)){
          message += "- Data inizio attività richiesta\r\n";
          formTest = false;
        }
        if (checkNullString(form.codiceFiscaleRappresentante.value)){
            message += "- Codicefiscale del rappresentante richiesto\r\n";
            formTest = false;
          }
          
           if (checkNullString(form.cognomeRappresentante.value)){
            message += "- Cognome del rappresentante richiesto\r\n";
            formTest = false;
          }
          
           if (checkNullString(form.nomeRappresentante.value)){
            message += "- Nome del rappresentante richiesto\r\n";
            formTest = false;
          }

           if (checkNullString(form.accountNumber.value)){
               message += "- Numero Registrazione richiesto\r\n";
               formTest = false;
             }

          
           if (document.getElementById("sedelegale").value == 'true' && form.address2line1 != null && checkNullString(form.address2line1.value)&&(form.address2line1.disabled==false)){
                    message += "- Indirizzo sede legale richiesto\r\n";
               formTest = false;
             }
           if (document.getElementById("sedelegale").value == 'false' && form.address1line1 != null && checkNullString(form.address1line1.value)&&(form.address1line1.disabled==false)){
               message += "- Indirizzo sede operativa richiesto\r\n";
          formTest = false;
        }
           
       	var obj1 = document.getElementById("prov12");
       	if(document.getElementById("prov12")!= null && (document.getElementById("prov12").disabled == false)){
             if ((obj1.value == -1)){
               message += "- Comune sede operativa richiesta\r\n";
               formTest = false;
             }
            }
        
        if (form.address1latitude != null && form.address1latitude && form.address1latitude.value!=""){
	      	 //alert(!isNaN(form.address1latitude.value));
	      		if ((orgSelected == 1)  ){
	      			/*if (isNaN(form.address1latitude.value) ||  (form.address1latitude.value < 4431788.049190) || (form.address1latitude.value >4593983.337630 )){
	       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 4431788.049190 e 4593983.337630  (Sede Operativa)\r\n";
	       				 formTest = false;
	       			}*/		
	       			if (isNaN(form.address1latitude.value) ||  (form.address1latitude.value < 39.988475) || (form.address1latitude.value > 41.503754)){
	        			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Operativa)\r\n";
	        			 formTest = false;
	        		}	 
	      		}
	   	 } 

	   	 
	   	 if (form.address1longitude != null && form.address1longitude && form.address1longitude.value!=""){
	      	 //alert(!isNaN(form.address1longitude.value));
	      		if ((orgSelected == 1)  ){
	      			/*if (isNaN(form.address1longitude.value) ||  (form.address1longitude.value < 2417159.584320) || (form.address1longitude.value > 2587487.362260)){
	       			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2417159.584320 e  2587487.362260 (Sede Operativa)\r\n";
	       				 formTest = false;
	       			}*/
	       			if (isNaN(form.address1longitude.value) ||  (form.address1longitude.value < 13.7563172) || (form.address1longitude.value > 15.8032837)){
	          			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Operativa)\r\n";
	          		     formTest = false;
	          		}	
	       					 
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
      if(alertMessage != ""){
        confirmAction(alertMessage);
      }
      return true;
    }
  }
  
  function update(countryObj, stateObj, selectedValue) {
    var country = document.forms['addAccount'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=s&country="+country+"&obj="+Obj+"&selected="+selectedValue+"&form=addAccount&Obj=address"+Obj+"";
    window.frames['server_commands'].location.href=url;
  }

  function continueUpdateState(stateObj, showText) {
    if(showText == 'true'){
      hideSpan('state1' + stateObj);
      showSpan('state2' + stateObj);
    } else {
      hideSpan('state2' + stateObj);
      showSpan('state1' + stateObj);
    }
  }

  function updateOwnerList(){
    var sel = document.forms['addAccount'].elements['siteId'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "OsmRegistrati.do?command=OwnerJSList&form=addAccount&widget=owner&allowBlank=false&siteId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }

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
<body onLoad="javascript:initializeClassification();">
<form name="addAccount" action="OsmRegistrati.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
<%
  boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }
%>
<dhv:evaluate if="<%= !popUp %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OsmRegistrati.do">OSM Registrati</a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="OsmRegistrati.do?command=Search"><dhv:label name="stabilimenti.SearchResults">Search Results</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="OsmRegistrati.do?command=Dashboard">Cruscotto</a> >
	<%}%>
<%} else {%>
<a href="OsmRegistrati.do?command=Search"><dhv:label name="stabilimenti.SearchResults">Search Results</dhv:label></a> >
<a href="OsmRegistrati.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="osm.details">Account Details</dhv:label></a> >
<%}%>
<dhv:label name="osm.modify">Modify Account</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="osm" selected="details" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmRegistrati.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmRegistrati.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
<br />
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="stabilimenti.stabilimenti_modifyaa.ModifyPrimaryInformation">Modificare l'informazione primaria</dhv:label></strong>
    </th>     
  </tr>
<dhv:include name="osmregistrati-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="stabilimenti.site">Site</dhv:label>
    </td>
    <td>
      <%= SiteList.getSelectedValue(OrgDetails.getSiteId()) %>
      <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
</dhv:include>



    <dhv:include name="osmregistrati-name" none="true">
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="stabilimenti.stabilimenti_add.OrganizationName">Organization Name</dhv:label>
        </td>
        <td>
          <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
       </td>
      </tr>
    </dhv:include>
        <tr class="containerBody">
			<td class="formLabel" nowrap>
				<dhv:label name="">Ragione Sociale Precedente</dhv:label>
			</td>
			<td>
				<input type="text" size="50" maxlength="80" name="banca" value="<%= toHtmlValue(OrgDetails.getBanca()) %>">
			</td>
		</tr>


    <dhv:include name="accounts-number" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Numero di Registrazione</dhv:label>
      </td>
      <td>
      	<input type="text" size="20" name="accountNumber" maxlength="20" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>"><font color="red">*</font>
        
      </td>
    </tr>
  </dhv:include>
		<%LookupList multipleSelects7=new LookupList();
  			HashMap<Integer, String> ListaBpi7=OrgDetails.getListaAttivita();
			Iterator<Integer> iteraKiavi7= OrgDetails.getListaAttivita().keySet().iterator();
			while(iteraKiavi7.hasNext()){
				int kiave1=iteraKiavi7.next();
				String valore1=ListaBpi7.get(kiave1);
				multipleSelects7.addItem(kiave1,valore1);
			}					
  			%>
  			
			
			   <tr class="containerBody">
			      <td name="impianto1" id="impianto1" nowrap class="formLabel">
			        <dhv:label name="">Attività </br></br><b>(per la selezione </br>multipla tenere </br>premuto il tasto </br>"Ctrl" durante la </br>scelta)</b></dhv:label>
			      </td>
			    <td>
			      <% impianto.setJsEvent("onchange=\"javascript:visibilitaDescrizioneAttivita();\""); %>
			      
			      <%= impianto.getHtmlSelect("impianto",multipleSelects7) %>
						      		<font color="red">*</font>
			    </br></br>
			    
			    <div id="descrizione_attivita" <%=((OrgDetails.getNoteAttivita()!=null && !OrgDetails.getNoteAttivita().equals(""))?("style=\"display:block\""):("style=\"display:none\""))%>>
			     			<dhv:label name=""><b>Specificare le principali tipologie produttive:</b></dhv:label></br>
    			 			<TEXTAREA NAME="noteAttivita" ROWS="3" COLS="50"><%= ((OrgDetails.getNoteAttivita()!=null)?toHtmlValue(OrgDetails.getNoteAttivita()):("")) %></TEXTAREA></td>
   				 </div>
			  </tr>

  	<tr class="containerBody">
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <input type="text" size="20" maxlength="11" name="partitaIva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="20" maxlength="16" name="codiceFiscale" value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>">    
    </td>
  </tr>

   <dhv:include name="organization.alert" none="true">
    <tr class="containerBody" style="display: none">
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.stabilimenti_add.AlertDate">Alert Date</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="alertDate" timestamp="<%= OrgDetails.getAlertDate() %>" timeZone="<%= OrgDetails.getAlertDateTimeZone() %>" showTimeZone="false" /><font color="red">*</font>
        <%= showAttribute(request, "alertDateError") %><%= showWarningAttribute(request, "alertDateWarning") %>
      </td>
    </tr>
  </dhv:include>
        
 
  
  
  
   <dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Stato OSM</dhv:label>
      </td>
      <td>
        <%= statoLab.getHtmlSelect("statoLab",OrgDetails.getStatoLab()) %><font color="red">*</font>
        
        modificato in data
        
          <input readonly type="text" id="contractEndDate" name="contractEndDate" size="10" value = "<%=toDateString(OrgDetails.getContractEndDate()) %>" />
		<a href="#" onClick="cal19.select(document.forms[0].contractEndDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
   
 		
      </td>
      
  </tr>
  </dhv:include>
  
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data inizio attività</dhv:label>
      </td>
      <td>
      	          <input readonly type="text" id="date2" name="date2" size="10" value = "<%=toDateString(OrgDetails.getDate2()) %>" />
		<a href="#" onClick="cal19.select(document.forms[0].date2,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
    	 
      </td>
    </tr>
     <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data fine attività</dhv:label>
      </td>
      <td>
      
      <input readonly type="text" id="date1" name="date1" size="10" value = "<%=toDateString(OrgDetails.getDate1()) %>" />
		<a href="#" onClick="cal19.select(document.forms[0].date1,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
    	 
      
      </td>
    </tr>

</table>

<br>

 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>

    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="16" maxlength="16" name="codiceFiscaleRappresentante" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Nome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="nomeRappresentante" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Cognome
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="cognomeRappresentante" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="dataNascitaRappresentante" timestamp="<%= OrgDetails.getDataNascitaRappresentante() %>"  showTimeZone="false" />
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      Luogo di Nascita
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtmlValue(OrgDetails.getLuogoNascitaRappresentante()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Email</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="emailRappresentante" value="<%= toHtmlValue(OrgDetails.getEmailRappresentante()) %>">
    </td>
    
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Telefono</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="telefonoRappresentante" value="<%= toHtmlValue(OrgDetails.getTelefonoRappresentante()) %>">
    </td>
    
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Fax</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="fax" value="<%= toHtmlValue(OrgDetails.getFax()) %>">
    </td>
    
  </tr>
  <!--  -->
  
  
</table>
<br>
<%-- 
<%
  boolean noneSelected = false;
 
int s=1;	
boolean primo=false;
int flag=0;

  int acount = 0;
  int locali=0;

  Iterator anumber = OrgDetails.getAddressList().iterator();
  
  boolean sedelegale = false ;
  while (anumber.hasNext()) {
    ++acount;
   
    OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
    
   
%> 
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      
	  <dhv:evaluate if="<%= thisAddress.getType() == 5 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede operativa</dhv:label></strong>
	    <input type="hidden" name = "address<%=acount %>id" value ="<%= thisAddress.getId()%>">
	  </dhv:evaluate>  
	   <dhv:evaluate if="<%= thisAddress.getType() == 1 %>">
	   <%
	   		sedelegale = true ;
	   %>
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede Legale</dhv:label></strong>
	    <input type="hidden" name = "address<%=acount %>id" value ="<%= thisAddress.getId()%>">
	  </dhv:evaluate>  
	</th>
    <input type="hidden" name = "address<%=acount %>type" value ="<%= thisAddress.getType()%>">
   </tr> 
 <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
   
    <select  name="address<%=acount %>city" id="prov12">
	<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            
	 <%
                Vector v = OrgDetails.getComuni2();
	 
	 			Enumeration e=v.elements();
                while (e.hasMoreElements()) {
                	String prov=e.nextElement().toString();
                	
                  
        %>
                <option <%if(thisAddress.getCity() != null && thisAddress.getCity().equalsIgnoreCase( prov ) ){%>selected="selected"<%} %>  value="<%=prov%>"><%= prov %></option>	
              <%}%>
		
	</select>
	<font color = "red">*</font>
	 </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
   
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate>
    </td>
  </tr>
 
 <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>
</tr>  
  
	<tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
          <% if (User.getSiteId() == 202) { %>
          <input type="text" readonly="readonly" size="28" name="address<%=acount %>state" maxlength="80" value="BN"><font color="red">*</font>          
          <%}%>
          <% if (User.getSiteId() == 201) { %>
          <input type="text" readonly="readonly" size="28" name="address<%=acount %>state" maxlength="80" value="AV"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 203) { %>
          <input type="text" readonly="readonly" size="28" name="address<%=acount %>state" maxlength="80" value="CE"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 204 || User.getSiteId() == 205 || User.getSiteId() == 206) { %>
          <input type="text" readonly="readonly" size="28" name="address<%=acount %>state" maxlength="80" value="NA"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 207) { %>
          <input type="text" readonly="readonly" size="28" name="address<%=acount %>state" maxlength="80" value="SA"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() <=0) { %>
          <input type="text" size="28" name="address<%=acount %>state" maxlength="80" value="<%=toHtmlValue(thisAddress.getState()) %>"><font color="red">*</font>
          <%}%>
          
    </td>
  </tr>

<tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude" size="30" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLatitude()) : "") %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" size="30" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLongitude()) : "") %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate></td>
  </tr>
  <% if(thisAddress.getType() == 5) { %>
  	<tr style="display: block">
    	<td colspan="2">
    		<input id="coord1button" type="button" value="Calcola Coordinate"
    		onclick="javascript:showCoordinate(document.forms['addAccount'].address2line1.value, document.forms['addAccount'].address2city.value,document.forms['addAccount'].address2state.value, document.forms['addAccount'].address2zip.value, document.forms['addAccount'].address2latitude, document.forms['addAccount'].address2longitude);" /> 
    	</td>
    </tr>
    <% } %>	
</table>
<% }

	if(sedelegale == false)
	{++acount;
		%>
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
        <strong><dhv:label name="accounts.accounts_add.Addressess">Sede Legale</dhv:label></strong>
	  </th>
    <input type="hidden" name = "address<%=acount %>type" value ="1"/>
   </tr> 
 <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
   
     <select  name="address<%=acount %>city" id="prov12">
	<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            
	 <%
                Vector v = OrgDetails.getComuni2();
	 
	 			Enumeration e=v.elements();
                while (e.hasMoreElements()) {
                	String prov=e.nextElement().toString();
                	
                  
        %>
                <option  value="<%=prov%>"><%= prov %></option>	
              <%}%>
		
	</select>
	<font color = "red">*</font>
	 </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
   
      <input type="text" size="40" name="address<%= acount %>line1" maxlength="80" value="">
      
    </td>
  </tr>
 
 <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" >
    </td>
</tr>  
  
	<tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
          <% if (User.getSiteId() == 202) { %>
          <input type="text" readonly="readonly" size="28" name="address<%=acount %>state" maxlength="80" value="BN"><font color="red">*</font>          
          <%}%>
          <% if (User.getSiteId() == 201) { %>
          <input type="text" readonly="readonly" size="28" name="address<%=acount %>state" maxlength="80" value="AV"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 203) { %>
          <input type="text" readonly="readonly" size="28" name="address<%=acount %>state" maxlength="80" value="CE"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 204 || User.getSiteId() == 205 || User.getSiteId() == 206) { %>
          <input type="text" readonly="readonly" size="28" name="address<%=acount %>state" maxlength="80" value="NA"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 207) { %>
          <input type="text" readonly="readonly" size="28" name="address<%=acount %>state" maxlength="80" value="SA"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() <=0) { %>
          <input type="text" size="28" name="address<%=acount %>state" maxlength="80" value=""><font color="red">*</font>
          <%}%>
          
    </td>
  </tr>

<tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.latitude">Latitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>latitude" size="30" /> </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" name="address<%= acount %>longitude" size="30"/> </td>
  </tr>
</table>
		
		<%
	}
%>

--%>
<%
  
  int acount = 0;
  Iterator anumber = OrgDetails.getAddressList().iterator();
  boolean sedelegale = false ;
  
  while (anumber.hasNext()) {
   
    OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
    
    if(thisAddress.getType() == 1){
    	acount = 1;
    }
    else {
    	acount = 2;
    }
%> 

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      	<input type="hidden" name = "address1type" value ="1">
      	<input type="hidden" name = "address2type" value ="5">
    	<dhv:evaluate if="<%= thisAddress.getType() == 5 %>">
	    	<strong><dhv:label name="accounts.accounts_add.Addressess">Sede operativa</dhv:label></strong>
	    	<input type="hidden" name = "address<%=acount %>id" value ="<%= thisAddress.getId()%>">
	  	</dhv:evaluate>  
	   <dhv:evaluate if="<%= thisAddress.getType() == 1 %>">
		   <%
		   		sedelegale = true ;
		   %>
		    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede Legale</dhv:label></strong>
		    <input type="hidden" name = "address<%=acount %>id" value ="<%= thisAddress.getId()%>">
	  	</dhv:evaluate>  
  <tr>
	<td nowrap class="formLabel" name="province" id="province">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
    <table class = "noborder"><tr>
    <td>
    
    	<input type = "text" value="<%=thisAddress.getCity()%>"  name="address<%= acount %>city" id="address<%= acount %>city" />
    
    </td>	</tr></table>
	
	</td>
  	</tr>	
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" id="address<%= acount %>line1" name="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="">C/O</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address<%= acount %>line2" maxlength="80" value = "<%=toHtmlValue(thisAddress.getStreetAddressLine2()) %>">
    </td>
  </tr>

  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address<%= acount %>zip" maxlength="12" value = "<%=toHtmlValue(thisAddress.getZip()) %>">
    </td>
  </tr>
  
  	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	  <input type="text" size="28" name="address<%= acount %>state" maxlength="80" value="<%=(thisAddress.getState() != null && thisAddress.getState() != "-1") ? toHtmlValue(thisAddress.getState()) : ""%>">      
    </td>
  </tr>
<tr class="containerBody">
    <td class="formLabel" nowrap id="latitude2"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" readonly="readonly" id="address<%= acount %>latitude" name="address<%= acount %>latitude" size="30" value="<%= (thisAddress.getLatitude() != 0.0 ) ? thisAddress.getLatitude() : "" %>" id="">
    	
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude1"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" readonly="readonly" id="address<%= acount %>longitude" name="address<%= acount %>longitude" size="30" value="<%= (thisAddress.getLongitude() != 0.0 ) ?  thisAddress.getLongitude() : "" %>" id="longitude12">
  </tr>
  
	<tr style="display: block">
	    <td colspan="2">
	    <input id="coord1button" type="button" value="Calcola Coordinate"
	    onclick="javascript:showCoordinate(document.getElementById('address<%=acount%>line1').value, document.forms['addAccount'].address<%=acount%>city.value,document.forms['addAccount'].address<%=acount%>state.value, document.forms['addAccount'].address<%=acount%>zip.value, document.forms['addAccount'].address<%=acount%>latitude, document.forms['addAccount'].address<%=acount%>longitude);" /> 
	    </td>
    </tr>	
 
  
</table>
<% } %>
<input type = "hidden" name = "sedelegale" value = "<%=sedelegale %>" id = "sedelegale" />


<br>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="stabilimenti.stabilimenti_add.AdditionalDetails">Additional Details</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="stabilimenti.accountasset_include.Notes">Notes</dhv:label>
      </td>
      <td>
        <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA>
      </td>
    </tr>
  </table>
  <br />
  <input type="hidden" name="onlyWarnings" value=<%=(OrgDetails.getOnlyWarnings()?"on":"off")%> />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmRegistrati.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmRegistrati.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="statusId" value="<%=OrgDetails.getStatusId()%>">
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
</dhv:container>
</form>
