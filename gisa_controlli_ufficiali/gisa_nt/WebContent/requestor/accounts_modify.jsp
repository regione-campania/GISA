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
  - Version: $Id: accounts_modify.jsp 19046 2007-02-07 18:53:43Z matt $
  - Description:
  --%>
  
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.requestor.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>

<%@page import="org.aspcfs.modules.lineeattivita.base.*"%>

<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.requestor.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<jsp:useBean id="List_id_rel_principale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="List_id_rel_1 " class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="List_id_rel_2 " class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="List_id_rel_3 " class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="List_id_rel_4 " class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="List_id_rel_5 " class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="List_id_rel_6 " class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="List_id_rel_7 " class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="List_id_rel_8 " class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="List_id_rel_9 " class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="List_id_rel_10" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="AccountSizeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CountryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="cod1" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod2" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod3" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod4" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod5" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod6" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod7" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod8" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod9" class="java.lang.String" scope="request"/>
<jsp:useBean id="cod10" class="java.lang.String" scope="request"/>
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

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
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

	function costruisci_obj_rel_ateco_linea_attivita_per_codice_istat_callback(returnValue) {
		campo_combo_da_costruire = returnValue [2];
		var select = document.getElementById(campo_combo_da_costruire); //Recupero la SELECT
	    
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
		
		      try {
		    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
		      } catch(e) {
		    	  select.add(NewOpt); // Funziona solo con IE
		      }
	    }
	}
	
	function costruisci_rel_ateco_attivita( campo_codice_fiscale, campo_combo_da_costruire ) {
		  // "costruisci_rel_ateco_attivita('codiceFiscaleCorrentista', 'id_rel_principale' );"
		  //alert('Sono in : costruisci_rel_ateco_attivita');
		  //cod_istat_principale = document.getElementById("codiceFiscaleCorrentista").value;
		  cod_istat_principale = document.getElementById(campo_codice_fiscale).value;
		  //alert('Valore selezionato : ' + cod_istat_principale);
		  PopolaCombo.costruisci_obj_rel_ateco_linea_attivita_per_codice_istat(cod_istat_principale , campo_combo_da_costruire, costruisci_obj_rel_ateco_linea_attivita_per_codice_istat_callback)
	}
	
	function costruisci_combo_linea_attivita_onLoad(){
		costruisci_rel_ateco_attivita('codiceFiscaleCorrentista', 'id_rel_principale' );
		costruisci_rel_ateco_attivita('codice1',  'id_rel_1' );
		costruisci_rel_ateco_attivita('codice2',  'id_rel_2' );
		costruisci_rel_ateco_attivita('codice3',  'id_rel_3' );
		costruisci_rel_ateco_attivita('codice4',  'id_rel_4' );
		costruisci_rel_ateco_attivita('codice5',  'id_rel_5' );
		costruisci_rel_ateco_attivita('codice6',  'id_rel_6' );
		costruisci_rel_ateco_attivita('codice7',  'id_rel_7' );
		costruisci_rel_ateco_attivita('codice8',  'id_rel_8' );
		costruisci_rel_ateco_attivita('codice9',  'id_rel_9' );
		costruisci_rel_ateco_attivita('codice10', 'id_rel_10');
	}

	function abilita_codici_ateco_vuoti() {

		// Blocco di codice che disabilita tutti i div relativi ai codici ateco
		document.getElementById("div_codice1").style.display="none";
		document.getElementById("div_codice2").style.display="none";	
		document.getElementById("div_codice3").style.display="none";
		document.getElementById("div_codice4").style.display="none";	
		document.getElementById("div_codice5").style.display="none";
		document.getElementById("div_codice6").style.display="none";	
	    document.getElementById("div_codice7").style.display="none";
	    document.getElementById("div_codice8").style.display="none";
	    document.getElementById("div_codice9").style.display="none";

	    // Blocco di codice che abilita tutti i div che presentano un valore ateco
		if ( (document.getElementById("codice1").value  != "") )			document.getElementById("div_codice1").style.display="";	
		if ( (document.getElementById("codice2").value  != "") )			document.getElementById("div_codice2").style.display="";
		if ( (document.getElementById("codice3").value  != "") )			document.getElementById("div_codice3").style.display="";
		if ( (document.getElementById("codice4").value  != "") )			document.getElementById("div_codice4").style.display="";
		if ( (document.getElementById("codice5").value  != "") )			document.getElementById("div_codice5").style.display="";
		if ( (document.getElementById("codice6").value  != "") )			document.getElementById("div_codice6").style.display="";
		if ( (document.getElementById("codice7").value  != "") )			document.getElementById("div_codice7").style.display="";
		if ( (document.getElementById("codice8").value  != "") )			document.getElementById("div_codice8").style.display="";
		if ( (document.getElementById("codice9").value  != "") )			document.getElementById("div_codice9").style.display="";

		// Blocco di codice che abilita un div vuoto dopo l"ultimo inserito
		if ( (document.getElementById("codice1").value  != "") && (document.getElementById("codice2").value  == "") )
			document.getElementById("div_codice1").style.display="";	

		if ( (document.getElementById("codice2").value  != "") && (document.getElementById("codice3").value  == "") )
			document.getElementById("div_codice2").style.display="";

		if ( (document.getElementById("codice3").value  != "") && (document.getElementById("codice4").value  == "") )
			document.getElementById("div_codice3").style.display="";

		if ( (document.getElementById("codice4").value  != "") && (document.getElementById("codice5").value  == "") )
			document.getElementById("div_codice4").style.display="";

		if ( (document.getElementById("codice5").value  != "") && (document.getElementById("codice6").value  == "") )
			document.getElementById("div_codice5").style.display="";

		if ( (document.getElementById("codice6").value  != "") && (document.getElementById("codice7").value  == "") )
			document.getElementById("div_codice6").style.display="";

		if ( (document.getElementById("codice7").value  != "") && (document.getElementById("codice8").value  == "") )
			document.getElementById("div_codice7").style.display="";

		if ( (document.getElementById("codice8").value  != "") && (document.getElementById("codice9").value  == "") )
			document.getElementById("div_codice8").style.display="";

		if ( (document.getElementById("codice9").value  != "") && (document.getElementById("codice10").value  == "") )
			document.getElementById("div_codice9").style.display="";	
	}

	function resetCodiciIstatSecondari(){

		  if ( (document.getElementById("codice1").value  != "") ){
			  document.getElementById("codice1").value="";
			  document.getElementById("cod1").value="";
			  document.getElementById("id_attivita_masterlist_1").value="-1";

		  } 
		  if ( (document.getElementById("codice2").value  != "") ){
			  document.getElementById("codice2").value="";
			  document.getElementById("cod2").value="";
			  document.getElementById("id_attivita_masterlist_2").value="-1";

		  }
		  if ( (document.getElementById("codice3").value  != "") ){
			  document.getElementById("codice3").value="";
			  document.getElementById("cod3").value="";
			  document.getElementById("id_attivita_masterlist_3").value="-1";

		  }
		  if ( (document.getElementById("codice4").value  != "") ){
			  document.getElementById("codice4").value="";
			  document.getElementById("cod4").value="";
			  document.getElementById("id_attivita_masterlist_4").value="-1";

		  }
		  if ( (document.getElementById("codice5").value  != "") ){
			  document.getElementById("codice5").value="";
			  document.getElementById("cod5").value="";
			  document.getElementById("id_attivita_masterlist_5").value="-1";

		  }
		  if ( (document.getElementById("codice6").value  != "") ){
			  document.getElementById("codice6").value="";
			  document.getElementById("cod6").value="";
	          document.getElementById("id_attivita_masterlist_6").value="-1";

		  }
		  if ( (document.getElementById("codice7").value  != "") ){
			  document.getElementById("codice7").value="";
			  document.getElementById("cod7").value="";
			  document.getElementById("id_attivita_masterlist_7").value="-1";

		  }
		  if ( (document.getElementById("codice8").value  != "") ){
			  document.getElementById("codice8").value="";
			  document.getElementById("cod8").value="";
			  document.getElementById("id_attivita_masterlist_8").value="-1";

		  }
		  if ( (document.getElementById("codice9").value  != "") ){
			  document.getElementById("codice9").value="";
			  document.getElementById("cod9").value="";
			  document.getElementById("id_attivita_masterlist_9").value="-1";

		  }
		  if ( (document.getElementById("codice10").value  != "") ){
			  document.getElementById("codice10").value="";
			  document.getElementById("cod10").value="";
			  document.getElementById("id_attivita_masterlist_10").value="-1";

		  }
		  
		  document.getElementById("div_codice1").style.display="none";
		  document.getElementById("div_codice2").style.display="none";	
		  document.getElementById("div_codice3").style.display="none";
		  document.getElementById("div_codice4").style.display="none";	
		  document.getElementById("div_codice5").style.display="none";
		  document.getElementById("div_codice6").style.display="none";	
	      document.getElementById("div_codice7").style.display="none";
	      document.getElementById("div_codice8").style.display="none";
	      document.getElementById("div_codice9").style.display="none";
	      costruisci_combo_linea_attivita_onLoad();

	}

	

  indSelected = 0;
  orgSelected = 0; 
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  
  function clonaLocaleFunzionalmenteCollegato()
  {
  	var maxElementi = 3;
    	var elementi;
    	var elementoClone;
    	var tableClonata;
    	var tabella;
    	var selezionato;
    	var x;
    	elementi = document.getElementById('elementi');
    	if (elementi.value<maxElementi)
    	{
    	elementi.value=parseInt(elementi.value)+1;
    	size = document.getElementById('size');
    	size.value=parseInt(size.value)+1;
    
    	var clonato = document.getElementById('locale_1');
    	
      	/*clona patologia*/	  	
    	clone=clonato.cloneNode(true);
    
    	clone.getElementsByTagName('INPUT')[0].name = "address" + elementi.value+"id";
    	clone.getElementsByTagName('INPUT')[0].id = "address" + elementi.value+"id";
    	clone.getElementsByTagName('INPUT')[0].value = "";
    	
    	clone.getElementsByTagName('INPUT')[1].name = "address4type" + elementi.value;
    	clone.getElementsByTagName('INPUT')[1].id = "address4type" + elementi.value;
    	
    	clone.getElementsByTagName('SELECT')[0].name = "TipoLocale" + elementi.value;
    	clone.getElementsByTagName('SELECT')[0].id = "TipoLocale" + elementi.value;
    	clone.getElementsByTagName('SELECT')[0].value = "-1";
  	  	
    	clone.getElementsByTagName('INPUT')[2].name = "address4city" + elementi.value;
    	clone.getElementsByTagName('INPUT')[2].id = "address4city" + elementi.value;
    	clone.getElementsByTagName('INPUT')[2].value = "" ;
    	
  		clone.getElementsByTagName('INPUT')[3].name = "address4line1" + elementi.value;
    	clone.getElementsByTagName('INPUT')[3].id = "address4line1" + elementi.value;
    	clone.getElementsByTagName('INPUT')[3].value = "" ;
    	
  		clone.getElementsByTagName('INPUT')[4].name = "address4zip" + elementi.value;
    	clone.getElementsByTagName('INPUT')[4].id = "address4zip" + elementi.value;
    	clone.getElementsByTagName('INPUT')[4].value = "" ;
    	
  		clone.getElementsByTagName('INPUT')[5].name = "address4state" + elementi.value;
    	clone.getElementsByTagName('INPUT')[5].id = "address4state" + elementi.value;
    	clone.getElementsByTagName('INPUT')[5].value = "" ;
    	
  		clone.getElementsByTagName('INPUT')[6].name = "address4latitude" + elementi.value;
    	clone.getElementsByTagName('INPUT')[6].id = "address4latitude" + elementi.value;
    	clone.getElementsByTagName('INPUT')[6].value = "" ;
    	
    	clone.getElementsByTagName('INPUT')[7].name = "address4longitude" + elementi.value;
    	clone.getElementsByTagName('INPUT')[7].id = "address4longitude" + elementi.value;
    	clone.getElementsByTagName('INPUT')[7].value = "" ;
    	
  		clone.getElementsByTagName('LABEL')[0].innerHTML ='Locale Funzionalmente collegato '+elementi.value ;
    	clone.getElementsByTagName('LABEL')[0].id = "intestazione" + elementi.value;
    	
    	
    
    	
    	//clone.id = "row_" + elementi.value;
    	
    	/*Aggancio il nodo*/
    	clonato.parentNode.appendChild(clone);
    	
    	/*Lo rendo visibile*/
    	//clone.style.display="block";
    	clone.style.visibility="visible";
    	}else
    	{
    		
    	}

  }
  function caricaCodici(){

  if(document.addAccount.codice2.value!=""){

  	document.getElementById("div_codice1").style.display="";
  	
  }
  if(document.addAccount.codice3.value!=""){
  	document.getElementById("div_codice2").style.display="";	

  }
  if(document.addAccount.codice4.value!=""){
  	document.getElementById("div_codice3").style.display="";		
  }
  if(document.addAccount.codice5.value!=""){
  	document.getElementById("div_codice4").style.display="";}

  if(document.addAccount.codice6.value!=""){
  	
  	document.getElementById("div_codice5").style.display="";		
  }

  if(document.addAccount.codice7.value!=""){
  	document.getElementById("div_codice6").style.display="";
  	}

  if(document.addAccount.codice8.value!=""){
  	document.getElementById("div_codice7").style.display="";	
  }

  if(document.addAccount.codice9.value!=""){
  	document.getElementById("div_codice8").style.display="";
  	
  }

  if(document.addAccount.codice10.value!=""){
  	document.getElementById("div_codice9").style.display="";
  		
  }


  	
  }

 
  function initializeClassification() {
  
  
  	
  		
       <%// if((OrgDetails.getDunsType().equals("DIA Semplice"))&&(OrgDetails.getDunsType() != null)) { %>
        	/* 
     	elmEsIdo = document.getElementById("nameMiddle");
    	elmEsIdo.style.color = "#000000";
       	document.addAccount.nameMiddle.style.background = "#ffffff";
        document.addAccount.nameMiddle.disabled = true;
        
        elmNS = document.getElementById("cin");
        elmNS.style.color = "#000000";        
        document.addAccount.cin.style.background = "#ffffff";
        document.addAccount.cin.disabled = true;        
                
        elmNSd3 = document.getElementById("date3");
        elmNSd3.style.color = "#000000";
        document.addAccount.date3.style.background = "#ffffff";
        document.addAccount.date3.disabled = true;
                
       date3 = document.getElementById("data3");
    	date3.style.visibility="hidden";
         */
         
      <%//}%>
    
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



  function mostraNextIndirizzo(ind){

  document.getElementById("indirizzo"+ind+"1").style.display="";
  document.getElementById("indirizzo"+ind+"2").style.display="";
  document.getElementById("indirizzo"+ind+"3").style.display="";
  document.getElementById("indirizzo"+ind+"4").style.display="";
  document.getElementById("indirizzo"+ind+"5").style.display="";
  document.getElementById("indirizzo"+ind+"6").style.display="";
  document.getElementById("indirizzo"+ind+"7").style.display="";
  document.getElementById("indirizzo"+ind+"8").style.display="";
  if(ind==2){
  	document.getElementById("indirizzo"+"2"+"button").style.display="none";
  	document.getElementById("indirizzo3button").style.display="";

  }else{

  	document.getElementById("indirizzo3button").style.display="none";
  	
  }


  }



  

  function abilitaDistributoriCampi(val){

	  if(val =='Distributori')
	  {
		 
		  //form = document.forms['addAccount'];
		 /* form.TipoLocale.value = "-1" ;
			form.address4city1.value = "" ;
			form.address4line1.value = "" ;
			form.address4zip1.value = "" ;
			form.address4state1.value = "" ;
			form.address4latitude1.value = "" ;
			form.address4longitude1.value = "" ;

			form.TipoLocale.disabled = "true" ;
			form.address4city1.disabled = "true" ;
			form.address4line1.disabled = "true" ;
			form.address4zip1.disabled = "true" ;
			form.address4state1.disabled = "true" ;
			form.address4latitude1.disabled = "true" ;
			form.address4longitude1.disabled = "true" ;

			form.TipoLocale.background = "#cccccc";
			form.address4city1.background = "#cccccc";
			form.address4line1.background = "#cccccc";
			form.address4zip1.background = "#cccccc";
			form.address4state1.background = "#cccccc";
			form.address4latitude1.background = "#cccccc";
			form.address4longitude1.background = "#cccccc";

			form.TipoLocale2.value = "-1" ;
			form.address4city2.value = "" ;
			form.address4line12.value = "" ;
			form.address4zip2.value = "" ;
			form.address4state2.value = "" ;
			form.address4latitude2.value = "" ;
			form.address4longitude2.value = "" ;

			form.TipoLocale2.disabled = "true" ;
			form.address4city2.disabled = "true" ;
			form.address4line12.disabled = "true" ;
			form.address4zip2.disabled = "true" ;
			form.address4state2.disabled = "true" ;
			form.address4latitude2.disabled = "true" ;
			form.address4longitude2.disabled = "true" ;

			form.TipoLocale2.background = "#cccccc";
			form.address4city2.background = "#cccccc";
			form.address4line12.background = "#cccccc";
			form.address4zip2.background = "#cccccc";
			form.address4state2.background = "#cccccc";
			form.address4latitude2.background = "#cccccc";
			form.address4longitude2.background = "#cccccc";


			form.TipoLocale3.value = "-1" ;
			form.address4city3.value = "" ;
			form.address4line13.value = "" ;
			form.address4zip3.value = "" ;
			form.address4state3.value = "" ;
			form.address4latitude3.value = "" ;
			form.address4longitude3.value = "" ;

			form.TipoLocale3.disabled = "true" ;
			form.address4city3.disabled = "true" ;
			form.address4line13.disabled = "true" ;
			form.address4zip3.disabled = "true" ;
			form.address4state3.disabled = "true" ;
			form.address4latitude3.disabled = "true" ;
			form.address4longitude3.disabled = "true" ;

			form.TipoLocale3.background = "#cccccc";
			form.address4city3.background = "#cccccc";
			form.address4line13.background = "#cccccc";
			form.address4zip3.background = "#cccccc";
			form.address4state3.background = "#cccccc";
			form.address4latitude3.background = "#cccccc";
			form.address4longitude3.background = "#cccccc";
		  */

	   
	     
	      
	     
	  	}
	  }


  
  function updateFormElements(index) {
    if (document.getElementById) {
      <dhv:include name="accounts-firstname" none="true">
        elm1 = document.getElementById("nameFirst1");
      </dhv:include>
      <dhv:include name="accounts-middlename" none="true">
        elm2 = document.getElementById("nameMiddle1");
      </dhv:include>
      <dhv:include name="accounts-lastname" none="true">
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
	    if (form.siteId.value && form.siteId.value=="-1"){
	    	 //alert(!isNaN(form.address2latitude.value));
	    		
	     	message += "- Controllare di aver selezionato un valore per il campo A.S.L.\r\n";
	     	formTest = false;
	     		
	 	 }   
	    
	    if (form.name){
	      if ((checkNullString(form.name.value))){
	        message += "- Ragione Sociale richiesta\r\n";
	        formTest = false;
	      }
	    }

	    if (form.no_piva.checked==false){
	    if (checkNullString(form.partitaIva.value) && checkNullString(form.codiceFiscale.value)){
	     	  
	         	message += "- Partita IVA e/o Codice Fiscale richiesto\r\n";
	        	 formTest = false;
	     	  
	    }
	    
	    if (! checkNullString(form.partitaIva.value) && form.partitaIva.value.length<11 && (form.provenienza==null || form.provenienzaIT.checked)){
		   	  
	     	message += "- Partita IVA non Valida \r\n";
	    	 formTest = false;
	 	  
	   }
	    
	    if (form.partitaIva.value.length>11 && (form.provenienza==null || form.provenienzaIT.checked)){
	    	 
	       	message += "- Partita IVA non Valida per provenienza ITALIA \r\n";
	      	 formTest = false;
	    	  
	     }
	    } else {
	    	 if ( form.codiceFiscale.value=="" || form.codiceFiscale.value.length<16 ){
	    			message += "- Codice Fiscale richiesto \r\n";
	    		  	 formTest = false;
	    	 }
	    }
	    
	      if (form.provenienza!=null && form.provenienzaEST.checked && form.country.value==-1){
	    	 	 
	    	   	message += "- Selezionare un PAESE in caso di provenienza estera. \r\n";
	    	  	 formTest = false;
	    		  
	    	 }  
	      
		if (form.no_piva.checked==false){
	    if (form.partitaIva && form.partitaIva.value!="" && (form.provenienza==null || form.provenienzaIT.checked)){
	        	 //alert(!isNaN(form.address2latitude.value));
	     		if ((orgSelected == 1)  ){
	     			if (isNaN(form.partitaIva.value)){
	      			 message += "- Valore errato per il campo Partita IVA. Si prega di inserire solo cifre\r\n";
	      				 formTest = false;
	      			}		 
	     		}
	  	 }   

   
	    if (form.partitaIva && form.partitaIva.value!="" && (form.provenienza==null || form.provenienzaIT.checked)){
      	 //alert(!isNaN(form.address2latitude.value));
      		
      			if (isNaN(form.partitaIva.value)){
       			 message += "- Valore errato per il campo Partita IVA. Si prega di inserire solo cifre\r\n";
       				 formTest = false;
       			 
      		}
   	 	}   
		}
   
 	   
    	if (checkNullString(form.codiceFiscaleRappresentante.value) || (form.codiceFiscaleRappresentante.value == "")){
        	message += "- Codice Fiscale del rappresentante richiesto\r\n";
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
    
    
       	if (form.name){
      		if ((orgSelected == 1) && (checkNullString(form.name.value))){
        		message += "- Ragione Sociale richiesta\r\n";
        		formTest = false;
      		}
    	}
    
  	    if (form.nomeCorrentista){
      		if ((orgSelected == 1) && (checkNullString(form.nomeCorrentista.value))){
        		message += "- Targa Veicolo richiesta\r\n";
        		formTest = false;
      		}
    	}
    	
     	if(form.source.value == 1){
    		if(checkNullString(form.dateI.value)){
    			message += "- Data inizio carattere temporanea richiesta\r\n";
    			formTest = false;
    		}
       		if(checkNullString(form.dateF.value)){
    			message += "- Data fine carattere temporanea richiesta\r\n";
    			formTest = false;
    		}
    		message += " test" + form.dateF>form.dateI +" \r\n";
    	}

      if(form.tipoDest.value=='Autoveicolo'){
    	  if(checkNullString(form.address4city1.value))
    	  {
    	  	message += "- Controllare di aver inserito il comune per locale funzionalmente collegato\r\n";
    	  	formTest = false;

    	  }
    	  if(checkNullString(form.address4line1.value))
    	  {
    	  	message += "-  Controllare di aver inserito l'indirizzo per locale funzionalmente collegato\r\n";
    	  	formTest = false;

    	  }
    	  if(checkNullString(form.address4zip1.value))
    	  {
    	  	message += "-  Controllare di aver inserito il cap per locale funzionalmente collegato\r\n";
    	  	formTest = false;


    	  }
    	  if(checkNullString(form.address4state1.value))
    	  {
    	  	message += "-  Controllare di aver inserito la provincia per locale funzionalmente collegato\r\n";
    	  	formTest = false;

    	  	}
   	}//Chiusura tipoDestAutoveicolo
    
    
      
      if (form.address2latitude && form.address2latitude.value!=""){
       			if (isNaN(form.address2latitude.value) ||  (form.address2latitude.value < 39.988475) || (form.address2latitude.value > 41.503754)){
       				message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Operativa)\r\n";
       			 	formTest = false;
          		}	 
   	 }   
   	 
   	 if (form.address2longitude && form.address2longitude.value!=""){
      	 //alert(!isNaN(form.address2longitude.value));
      		if ((orgSelected == 1)  ){
      			if (isNaN(form.address2longitude.value) ||  (form.address2longitude.value < 13.7563172) || (form.address2longitude.value >  15.8032837)){
       				message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Operativa)\r\n";
         		    formTest = false;
          		}		 
      		}
   	 }      


	  	 if(document.getElementById("codiceFiscaleCorrentista").value==""){
	       	 message += "- Controllare di aver selezionato il codice ISTAT principale.\r\n";
	   		 formTest = false;
	        }
	  	 
	 	if (document.getElementById("codiceFiscaleCorrentista").value=="00.00.00"){
	  		message += "- Codice ISTAT principale 00.00.00 non valido. Selezionare un Codice ISTAT principale valido.\r\n";
	   		formTest = false;
	  	}
	 	
	 	var codici_ateco_list = document.getElementsByClassName("codiciatecolista");
	  	for (var i = 0; i < codici_ateco_list.length; ++i) {
	  	    var item = codici_ateco_list[i]; 
	  	    if (item.value!=null && item.value=="00.00.00"){
	  	    	message += item.id+" - 00.00.00 non valido. Selezionare un Codice Ateco valido.\r\n";
	  	    	formTest = false;
	  	    }
	  	}
	    
	   
	   

		if(checkNullString(form.dataPresentazione.value)){
			message += "- Data Presentazione D.I.A./Inizio Attività richiesta\r\n";
			formTest = false;
		}

		
	    if(form.source.value == 1){
	    	if(checkNullString(form.dateI.value)){
	    		message += "- Data inizio carattere temporanea richiesta\r\n";
	    		formTest = false;
	    	}
	   
	       	if(checkNullString(form.dateF.value)){
	    		message += "- Data fine carattere temporanea richiesta\r\n";
	    		formTest = false;
	    	}
	    }


		if(form.stageId.value=="-1"){
			message += "- Servizio Competente Richiesto\r\n";
			formTest = false;
		}

	    if (checkNullString(form.nomeRappresentante.value)){
	        message += "- Nome del rappresentante richiesto\r\n";
	        formTest = false;
	    }
	    
	    if (checkNullString(form.cognomeRappresentante.value)){
	       message += "- Cognome del rappresentante richiesto\r\n";
	       formTest = false;
	     }

	      if (checkNullString(form.address1line1.value)){
	          message += "- Indirizzo sede legale richiesto\r\n";
	          formTest = false;
	       }

	        if (form.address1city.value=="" || form.address1city.value=="-1"){
	          message += "- Comune sede legale richiesta\r\n";
	          formTest = false;
	        }


	        if (form.address1state.value == ""){
	            message += "- Provincia sede legale richiesta\r\n";
	            formTest = false;
	          }

	/*        if (form.address1latitude && form.address1latitude.value!=""){
	         	 if (isNaN(form.address1latitude.value) ||  (form.address1latitude.value < 39.988475) || (form.address1latitude.value > 41.503754)){
	          				message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Legale)\r\n";
	          			 	formTest = false;
	          	}	
	          					 
	      	 }   
	      	 
	      	 if (form.address1longitude && form.address1longitude.value!=""){
	          			if (isNaN(form.address1longitude.value) ||  (form.address1longitude.value < 13.7563172) || (form.address1longitude.value >  15.8032837)){
	          				message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Legale)\r\n";
	            		    formTest = false;
	             		}			 
	         		
	      	 } */

				if (form.address4latitude1 && form.address4latitude1.value!=""){
	     	  		if (isNaN(form.address4latitude1.value) ||  (form.address4latitude1.value < 39.988475) || (form.address4latitude1.value > 41.503754)){
	     	   			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Locale fun. collegato)\r\n";
	     	   				 formTest = false;
	     	   			}		 
	     	  	
	     		 }   

	     		 if (form.address4longitude1 && form.address4longitude1.value!=""){
	     	 	
	     	 			if (isNaN(form.address4longitude1.value) ||  (form.address4longitude1.value < 13.7563172) || (form.address4longitude1.value > 15.8032837)){
	     	  			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Locale fun. collegato)\r\n";
	     	  				 formTest = false;
	     	  			}		 
	     	 		//}
	     		 }   

	     		if (form.address4latitude2 && form.address4latitude2.value!=""){
	     	 	 //alert(!isNaN(form.address3latitude.value));
	     	 		//if ((orgSelected == 1)  ){
	     	 			if (isNaN(form.address4latitude2.value) ||  (form.address4latitude2.value < 39.988475) || (form.address4latitude2.value > 41.503754)){
	     	  			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Locale fun. collegato)\r\n";
	     	  				 formTest = false;
	     	  			}		 
	     	 		//}
	     		 }   

	     		 if (form.address4longitude2 && form.address4longitude2.value!=""){
	     		 //alert(!isNaN(form.address2longitude.value));
	     			//if ((orgSelected == 1)  ){
	     				if (isNaN(form.address4longitude2.value) ||  (form.address4longitude2.value < 13.7563172) || (form.address4longitude2.value > 15.8032837)){
	     	 			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Locale fun. collegato)\r\n";
	     	 				 formTest = false;
	     	 			}		 
	     			//}
	     		 }   

	     		 	
	     		 if (form.address4latitude3 && form.address4latitude3.value!=""){
	     	 	 //alert(!isNaN(form.address3latitude.value));
	     	 		//if ((orgSelected == 1)  ){
	     	 			if (isNaN(form.address4latitude3.value) ||  (form.address4latitude3.value < 39.988475) || (form.address4latitude3.value > 41.503754)){
	     	  			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Locale fun. collegato)\r\n";
	     	  				 formTest = false;
	     	  			}		 
	     	 		//}
	     		 }   

	     		 if (form.address4longitude3 && form.address4longitude3.value!=""){
	     		 //alert(!isNaN(form.address2longitude.value));
	     			//if ((orgSelected == 1)  ){
	     				if (isNaN(form.address4longitude3.value) ||  (form.address4longitude3.value < 13.7563172) || (form.address4longitude3.value > 15.8032837)){
	     	 			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Locale fun. collegato)\r\n";
	     	 				 formTest = false;
	     	 			}		 
	     			//}
	     		 }
	      	       
	         
	        


	        if(form.tipoDest.value=="Es. Commerciale")
	        {

	        	if (form.address2city.value=="-1"){
		            message += "- Comune sede operativa richiesto\r\n";
		            formTest = false;
		          }
		          
	        	if (checkNullString(form.address2line1.value)&&(form.address2line1.disabled==false)){
		            message += "- Indirizzo sede operativa richiesto\r\n";
		            formTest = false;
		          }

	        	if (checkNullString(form.address2state.value)&&(form.address2line1.disabled==false)){
		            message += "- Provincia sede operativa richiesta\r\n";
		            formTest = false;
		          } 


	        	
	        	 if (form.address2latitude && form.address2latitude.value!=""){
	    	      	 //alert(!isNaN(form.address2latitude.value));
	    	      		//if ((orgSelected == 1)  ){
	    	      			/*if (isNaN(form.address2latitude.value) ||  (form.address2latitude.value < 4431788.049190) || (form.address2latitude.value >4593983.337630 )){
	    	       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 4431788.049190 e 4593983.337630  (Sede Operativa)\r\n";
	    	       				 formTest = false;
	    	       			}*/
	    	       			if (isNaN(form.address2latitude.value) ||  (form.address2latitude.value < 39.988475) || (form.address2latitude.value > 41.503754)){
	    	         			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Operativa)\r\n";
	    	         				 formTest = false;
	    	         		}		 
	    	      		//}
	    	   	 }   
	    	   	 
	    	   	 if (form.address2longitude && form.address2longitude.value!=""){
	    	      	 //alert(!isNaN(form.address2longitude.value));
	    	      	//	if ((orgSelected == 1)  ){
	    	      			/*if (isNaN(form.address2longitude.value) ||  (form.address2longitude.value < 2417159.584320) || (form.address2longitude.value > 2587487.362260)){
	    	       			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2417159.584320 e  2587487.362260 (Sede Operativa)\r\n";
	    	       				 formTest = false;
	    	       			}*/	
	    	       			if (isNaN(form.address2longitude.value) ||  (form.address2longitude.value < 13.7563172) || (form.address2longitude.value > 15.8032837)){
	    	        			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Operativa)\r\n";
	    	        				 formTest = false;
	    	        		}		 
	    	      		//}
	    	   	 }   

		    }
	        else
	        {
				if(form.tipoDest.value == "Autoveicolo")
				{

				 	if (checkNullString(form.nomeCorrentista.value)&&(form.nomeCorrentista.disabled==false)){
				        message += "- Targa/Codice autoveicolo richiesto\r\n";
				        formTest = false;
				      }
				      
				       if (checkNullString(form.address2line1.value)&&(form.address2line1.disabled==false)){
				        message += "- Indirizzo attività mobile richiesto\r\n";
				        formTest = false;
				      }
						var obj = form.address2city;
						var obj3 = document.getElementById("prov3");
					 if((form.address2city.disabled == false)){
				      	if ((obj.value == "")){
				        	message += "- Comune attività mobile richiesta\r\n";
				        	formTest = false;
				      	}   

				      if(form.TipoLocale.value == "-1" ){
				    	  message += "- Tipo Locale Funzionalmente collegato richiesto\r\n";
					        formTest = false;
				      }

				      if(form.address4city1.value == "" ){
				    	  message += "- Comune Locale Funzionalmente collegato richiesto\r\n";
					        formTest = false;
				      }
				      if(form.address4line1.value == "" ){
				    	  message += "- Indirizzo Locale Funzionalmente collegato richiesto\r\n";
					        formTest = false;
				      }


				      if(form.address4zip1.value == "-1" ){
				    	  message += "- Cap Locale Funzionalmente collegato richiesto\r\n";
					        formTest = false;
				      }


				      if(form.address4state1.value == "-1" ){
				    	  message += "- Provincia Locale Funzionalmente collegato richiesto\r\n";
					        formTest = false;
				      }


				     
				     

				 
					}
				}

	        }//else chiusura	     

	        if(form.tipoDest.value == "Distributori")
	  	  	{

	  		  	if (checkNullString(form.address1latitude.value)){
	  		        message += "- Latitudine sede legale richiesta\r\n";
	  		        formTest = false;
	  		      }

	  			if (checkNullString(form.address1longitude.value)){
	  		        message += "- Longitudine sede legale richiesta\r\n";
	  		        formTest = false;
	  			}
	  		  	
	  	  }

	    

	      
	    if (form.nameLast){
	      if ((indSelected == 1) && (checkNullString(form.nameLast.value))){
	        message += label("check.lastname", "- Last name is a required field\r\n");
	        formTest = false;
	      }
	    }
	    /*
	  <dhv:include name="organization.alert" none="true">
	    if ((!checkNullString(form.alertText.value)) && (checkNullString(form.alertDate.value))) {
	      message += label("specify.alert.date", "- Please specify an alert date\r\n");
	      formTest = false;
	    }
	    if ((!checkNullString(form.alertDate.value)) && (checkNullString(form.alertText.value))) {
	      message += label("specify.alert.description", "- Please specify an alert description\r\n");
	      formTest = false;
	    }
	  </dhv:include>
	  */
	  <dhv:include name="organization.phoneNumbers" none="false">
	    if ((!checkPhone(form.phone1number.value)) || (!checkPhone(form.phone2number.value))) {
	      message += label("check.phone", "- At least one entered phone number is invalid.  Make sure there are no invalid characters and that you have entered the area code\r\n");
	      formTest = false;
	    }
	    if ((checkNullString(form.phone1ext.value) && form.phone1ext.value != "") || (checkNullString(form.phone1ext.value) && form.phone1ext.value != "")) {
	      message += label("check.phone.ext","- Please enter a valid phone number extension\r\n");
	      formTest = false;
	    }
	  </dhv:include>
	  

	
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
	      return true;
	    }
	  }//Chiusura function checkForm
  function updateFormElementsNew(index) {

	  //document.getElementById("codiceFiscaleCorrentista").value="";
	  //document.getElementById("alertText").value="";


	  elm1=document.addAccount.nameMiddle;
	    elm2=document.addAccount.cin;
	    elm3=document.addAccount.date3;

if(elm1!=null){
	    elm1.style.color="";
	    elm1.style.background = "";
	    elm1.value = "";
	    elm1.disabled = false;
}
if(elm2!=null){
	    elm2.style.color="";
	    elm2.style.background = "";
	    elm2.value = "";
	    elm2.disabled = false;
}

if(elm3!=null){
	    elm3.style.color="";
	    elm3.style.background = "";
	    elm3.value = "";
	    elm3.disabled = false;
}
	 
	  
	  
	  elm1=document.addAccount.TipoLocale;

	    elm2=document.addAccount.address4city1;
	    elm3=document.addAccount.address4line1;
	    elm4=document.addAccount.address4latitude1;
	    elm5=document.addAccount.address4longitude1;
	    elm6=document.addAccount.address4zip1;
	    elm7=document.addAccount.address4state1;
if(elm1!=null){
	    elm1.style.color="";
		
	    elm1.style.background = "";
	    //elm1.value = "";
	    elm1.disabled = false;
}
if(elm2!=null){

elm2.style.color="#cccccc";
	    elm2.style.background = "";
	   // elm2.value = "";
	    elm2.disabled = false;
}
if(elm3!=null){

	    elm3.style.color="";
	    elm3.style.background = "";
	    //elm3.value = "";
	    elm3.disabled = false;
}
if(elm4!=null){

	    elm4.style.color="";
	    elm4.style.background = "";
	    //elm4.value = "";
	    elm4.disabled = false;
}
if(elm5!=null){

	    elm5.style.color="";
	    elm5.style.background = "";
	    //elm5.value = "";
	    elm5.disabled = false;
}
if(elm6!=null){

	    elm6.style.color="";
	    elm6.style.background = "";
	    //elm6.value = "";
	    elm6.disabled = false;

}
if(elm7!=null){

	    elm7.style.color="";
	    elm7.style.background = "";
	    //elm7.value = "";
	    elm7.disabled = false;

}
	    elm1=document.addAccount.TipoLocale2;
	    elm2=document.addAccount.address4city2;
	    elm3=document.addAccount.address4line12;
	    elm4=document.addAccount.address4latitude2;
	    elm5=document.addAccount.address4longitude2;
	    elm6=document.addAccount.address4zip2;
	    elm7=document.addAccount.address4state2;

	    if(elm1!=null){

		 elm1.style.color="";
	    elm1.style.background = "";
	    elm1.value = "";
	    elm1.disabled = false;
	    }
	    if(elm2!=null){
		    
	    elm2.style.color="#cccccc";
	    elm2.style.background = "";
	    //elm2.value = "";
	    elm2.disabled = false;
	    }
	    if(elm3!=null){
		    
	    elm3.style.color="";
	    elm3.style.background = "";
	    //elm3.value = "";
	    elm3.disabled = false;
	    }
	    if(elm4!=null){
		    
	    elm4.style.color="";
	    elm4.style.background = "";
	    //elm4.value = "";
	    elm4.disabled = false;
	    }
	    if(elm5!=null){
		    
	    elm5.style.color="";
	    elm5.style.background = "";
	    //elm5.value = "";
	    elm5.disabled = false;
	    }
	    if(elm6!=null){
		    
	    elm6.style.color="";
	    elm6.style.background = "";
	    //elm6.value = "";
	    
	    elm6.disabled = false;
	    }
	    if(elm7!=null){
		    
	    elm7.style.color="";
	    elm7.style.background = "";
	    //elm7.value = "";
	    elm7.disabled = false;
	    }
	    elm1=document.addAccount.TipoLocale3;
	    elm2=document.addAccount.address4city3;
	    elm3=document.addAccount.address4line13;
	    elm4=document.addAccount.address4latitude3;
	    elm5=document.addAccount.address4longitude3;
	    elm6=document.addAccount.address4zip3;
	    elm7=document.addAccount.address4state3;
	    if(elm1!=null){

	    elm1.style.color="";
	    elm1.style.background = "";
	    elm1.value = "";
	    elm1.disabled = false;
	    }
	    if(elm2!=null){
		    
	    elm2.style.color="#cccccc";
	    elm2.style.background = "";
	    //elm2.value = "";
	    elm2.disabled = false;
	    }
	    if(elm3!=null){
		    
	    elm3.style.color="";
	    elm3.style.background = "";
	    //elm3.value = "";
	    elm3.disabled = false;
	    }
	    if(elm4!=null){
		    
	    elm4.style.color="";
	    elm4.style.background = "";
	    //elm4.value = "";
	    elm4.disabled = false;
	    }
	    if(elm5!=null){
		    
	    elm5.style.color="";
	    elm5.style.background = "";
	    //elm5.value = "";
	    elm5.disabled = false;
	    }
	    if(elm6!=null){
		    
	    elm6.style.color="";
	    elm6.style.background = "";
	    //elm6.value = "";
	    elm6.disabled = false;
	    }
	    if(elm7!=null){
		    
	    elm7.style.color="";
	    elm7.style.background = "";
	    //elm7.value = "";
	    elm7.disabled = false;
	    }
	    if(document.getElementById("aggiungialtrobutton")!=null)
	    document.getElementById("aggiungialtrobutton").disabled="";
	    if(document.getElementById("aggiungialtrobutton2")!=null)
	    document.getElementById("aggiungialtrobutton2").disabled="";
  	
  	if(index==1){
  	  	if(document.getElementById("starMobil3")!=null)
  		document.getElementById("starMobil3").style.display="";
  	  if(document.getElementById("starMobil4")!=null)
  		document.getElementById("starMobil4").style.display="";
  	if(document.getElementById("starMobil5")!=null)
		document.getElementById("starMobil5").style.display="";
  	if(document.getElementById("starMobil8")!=null)
  		document.getElementById("starMobil8").style.display="";
  	if(document.getElementById("starMobil9")!=null)
  		document.getElementById("starMobil9").style.display="";
  		if(document.getElementById("starMobil10")) document.getElementById("starMobil10").style.display="";
  	}
  	else if(index==0){
  		if(document.getElementById("starMobil3")!=null)
  		document.getElementById("starMobil3").style.display="none";
  	  if(document.getElementById("starMobil4")!=null)
  		document.getElementById("starMobil4").style.display="none";
  	if(document.getElementById("starMobil5")!=null)
    		document.getElementById("starMobil5").style.display="none";
  	if(document.getElementById("starMobil8")!=null)
  		document.getElementById("starMobil8").style.display="none";
	if(document.getElementById("starMobil9")!=null)
  		document.getElementById("starMobil9").style.display="none";
  		if(document.getElementById("starMobil10")) document.getElementById("starMobil10").style.display="none";
  	
  	
  	}
  	

  	
    if (document.getElementById) {
       elm1 = document.getElementById("tipoVeicolo1"); //Nome
       elm2 = document.getElementById("targaVeicolo1"); //Cognome
      /* elm3 = document.getElementById("codiceCont1"); // Nome (Organization)*/
       elm4 = document.getElementById("tipoStruttura1");
       elm5 = document.getElementById("addressLine");
       elm6 = document.getElementById("prov1");
       elm7 = document.getElementById("labelCap");
       elm8 = document.getElementById("stateProv1");
       elm9 = document.getElementById("latitude1");
       elm10 = document.getElementById("longitude1");
       
      if (index == 0) {
        resetFormElementsNew();
        
      
        if(elm1!=null){
        elm1.style.color="#cccccc";
        }
        if(document.addAccount.tipoVeicolo!=null){
        document.addAccount.tipoVeicolo.style.background = "#cccccc";
        document.addAccount.tipoVeicolo.value = "";
        document.addAccount.tipoVeicolo.disabled = true;}
        if(elm2!=null)
        elm2.style.color="#cccccc";
if(document.addAccount.targaVeicolo!=null){
        document.addAccount.targaVeicolo.style.background = "#cccccc";
        document.addAccount.targaVeicolo.value = "";
        document.addAccount.targaVeicolo.disabled = true;
}
    
    /*    elm3.style.color="#cccccc";
        document.addAccount.codiceCont.style.background = "#cccccc";
        document.addAccount.codiceCont.value = "";
        document.addAccount.codiceCont.disabled = true;*/
        if(elm3!=null)
           elm4.style.color="#cccccc";
        if(  document.getElementById("prov12")!=null)
        document.getElementById("prov12").disabled = true;
                
if(elm5!=null)
        elm5.style.color="#cccccc";
if( document.addAccount.addressline1!=null){
        document.addAccount.addressline1.style.background = "#cccccc";
        document.addAccount.addressline1.value = "";
        document.addAccount.addressline1.disabled = true;}
if(elm6!=null)
        
        elm6.style.color="#cccccc";
if(document.getElementById("prov12")!=null){
        document.getElementById("prov12").disabled = true;
        //document.getElementById("prov12").selectedIndex=0;
}
if(elm7!=null)
        elm7.style.color="#cccccc";
if(document.addAccount.addresszip!=null){
        document.addAccount.addresszip.style.background = "#cccccc";
        document.addAccount.addresszip.value = "";
        document.addAccount.addresszip.disabled = true;
}
if(elm8!=null)
        elm8.style.color="#cccccc";
        if(elm9!=null)
        elm9.style.color="#cccccc";
        /*if(document.addAccount.address3latitude!=null){
        document.addAccount.address3latitude.style.background = "#cccccc";
        document.addAccount.address3latitude.value = "";
        document.addAccount.address3latitude.disabled = true;
        }*/
        if(elm10!=null)
        elm10.style.color="#cccccc";
        /*if(document.addAccount.address3longitude!=null){
        document.addAccount.address3longitude.style.background = "#cccccc";
        document.addAccount.address3longitude.value = "";
        document.addAccount.address3longitude.disabled = true;
        }*/
        if(elm4!=null)
        elm4.style.color="#cccccc";
        if(document.addAccount.TipoStruttura!=null){
        document.addAccount.TipoStruttura.style.background = "#cccccc";
        document.addAccount.TipoStruttura.value = "";
        document.addAccount.TipoStruttura.disabled = true;
        }
        
       if(document.getElementById("prov12")!=null){
        document.getElementById("prov12").disabled = false;

if(document.addAccount.check!=null)
        document.addAccount.check.value = "es";
if(document.addAccount.orgType!=null)
   document.addAccount.orgType.value = "11"; //Valore per PROPRIETARIO
       }
        tipo1 = document.getElementById("tipoD");
        if(tipo1!=null)
        tipo1.checked = true;
        
        /*document.getElementById("codice1").value = "";
        document.getElementById("codice2").value = "";
        document.getElementById("codice3").value = "";
        document.getElementById("codice4").value = "";
        document.getElementById("codice5").value = "";
        document.getElementById("codice6").value = "";
        document.getElementById("codice7").value = "";
        document.getElementById("codice8").value = "";
        document.getElementById("codice9").value = "";
        document.getElementById("codice10").value = "";*/
        
        
      } else if (index == 1){
      
        resetFormElementsNew();
        
        document.addAccount.address1type.style.background = "#000000";
       	document.addAccount.address1type.disabled = false;
        
       elm5 = document.getElementById("indirizzo1");
       elm6 = document.getElementById("prov2");
       elm7 = document.getElementById("cap1");
       elm8 = document.getElementById("stateProv2");
       elm9 = document.getElementById("latitude2");
       elm10 = document.getElementById("longitude2");
       if(elm5!=null)
      	 elm5.style.color="#cccccc";
    	 if(document.addAccount.indirizzo12!=null){
        document.addAccount.indirizzo12.style.background = "#cccccc";
        document.addAccount.indirizzo12.value = "";
        document.addAccount.indirizzo12.disabled = true;
    	 }
        if(elm6!=null)
        elm6.style.color="#cccccc";
       if( document.getElementById("prov")!=null){
        document.getElementById("prov").disabled = true;
       }
        //peppedocument.getElementById("prov").selectedIndex=0;
         if(elm7!=null)
        elm7.style.color="#cccccc";
if(document.addAccount.cap!=null){
         document.addAccount.cap.style.background = "#cccccc";
        document.addAccount.cap.value = "";
        document.addAccount.cap.disabled = true;
}
        if(elm8!=null)
        elm8.style.color="#cccccc";
        if(elm9!=null)
        elm9.style.color="#cccccc";
        if(document.addAccount.address2latitude!=null){
        document.addAccount.address2latitude.style.background = "#cccccc";
        document.addAccount.address2latitude.value = "";
        document.addAccount.address2latitude.disabled = true;}
        if(elm10!=null)
        elm10.style.color="#cccccc";
        if(document.addAccount.address2longitude!=null){
        document.addAccount.address2longitude.style.background = "#cccccc";
        document.addAccount.address2longitude.value = "";
        document.addAccount.address2longitude.disabled = true;
        }
        
    	/*elm3.style.color="#cccccc";
        document.addAccount.codiceCont.style.background = "#cccccc";
        document.addAccount.codiceCont.value = "";
        document.addAccount.codiceCont.disabled = true;*/
        if(document.getElementById("prov")!=null)
     	document.getElementById("prov").disabled = false;
        if(document.addAccount.check!=null)
        document.addAccount.check.value = "autoveicolo";
        if(document.addAccount.orgType!=null)
           document.addAccount.orgType.value = "17"; //Valore per PROPRIETARIO
        
      } else if (index==2) {
      	
      	resetFormElementsNew();
        
        elm1.style.color="#cccccc";
        document.addAccount.tipoVeicolo.style.background = "#cccccc";
        document.addAccount.tipoVeicolo.value = "";
        document.addAccount.tipoVeicolo.disabled = true;
    
        elm2.style.color="#cccccc";
        document.addAccount.targaVeicolo.style.background = "#cccccc";
        document.addAccount.targaVeicolo.value = "";
        document.addAccount.targaVeicolo.disabled = true;
        if(document.addAccount.check!=null)
        document.addAccount.check.value = "codiceCont";
        if(document.addAccount.orgType!=null)
        document.addAccount.orgType.value = "19"; //Valore per sindaco
        
      }
    }
   /* if (onLoad != 1){
      var url = "Accounts.do?command=RebuildFormElements&index=" + index;
      window.frames['server_commands'].location.href=url;
    }*/
    onLoad = 0;
  }
  function update(countryObj, stateObj, selectedValue) {
    var country = document.forms['addAccount'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=addAccount&stateObj=address"+stateObj+"state";
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
    var url = "Requestor.do?command=OwnerJSList&form=addAccount&widget=owner&allowBlank=false&siteId=" + escape(value);
    window.frames['server_commands'].location.href=url;
  }
  
  function selectCarattere(){
  
 		elm1 = document.getElementById("data1");
 		elm2 = document.getElementById("data2");
 		elm3 = document.getElementById("data3");
 		elm4 = document.getElementById("data4");
 		elm5 = document.getElementById("cessazione");
 		car = document.addAccount.source.value;
 	
 		if(car == 1){
 			elm1.style.visibility = "visible";
 			elm2.style.visibility = "visible";
 			elm3.style.visibility = "visible";
 			elm4.style.visibility = "visible";
 			elm5.style.visibility = "visible";
 			
 		}
 		else {
 			elm1.style.visibility = "hidden";
 			elm2.style.visibility = "hidden";
 			elm3.style.visibility = "hidden";
 			elm4.style.visibility = "hidden";
 			elm5.style.visibility = "hidden";
 			document.forms['addAccount'].dateI.value = ""; 
 			document.forms['addAccount'].dateF.value = ""; 
 			document.forms['addAccount'].cessazione.checked = "true";
 		}
 	
  }

  function resetFormElementsNew() {


   	   elm1 = document.getElementById("tipoVeicolo1"); //Nome

if(document.addAccount.tipoVeicolo!=null){
 	   document.addAccount.tipoVeicolo.style.background = "#ffffff";
       document.addAccount.tipoVeicolo.disabled = false;
}
   	   if(elm1!=null){
       elm1.style.color = "#000000";
   	   }
       elm2 = document.getElementById("targaVeicolo1"); //Cognom
      if( document.addAccount.targaVeicolo!=null){
        document.addAccount.targaVeicolo.style.background = "#ffffff";
       document.addAccount.targaVeicolo.disabled = false;}
      if(elm2!=null){

      elm2.style.color = "#000000";
      }
     /*  elm3 = document.getElementById("codiceCont1"); // Nome (Organization)
       document.addAccount.codiceCont.style.background = "#ffffff";
       document.addAccount.codiceCont.disabled = false;
       elm3.style.color = "#000000";*/
       
      	elm5 = document.getElementById("addressLine"); // Nome (Organization)
if(document.addAccount.addressline1!=null){
       	document.addAccount.addressline1.style.background = "#ffffff";
       	document.addAccount.addressline1.disabled = false;}
       	if(elm5!=null){

        	elm5.style.color = "#000000";
       	}       	
       	elm6 = document.getElementById("prov1"); // Nome (Organization)

if(document.getElementById("prov")!=null){
       	document.getElementById("prov").disabled = true;}
       	if(elm6!=null){

         	elm6.style.color = "#000000";
       	}
       	elm7 = document.getElementById("labelCap"); // Nome (Organization)
if(document.addAccount.addresszip!=null){
       	document.addAccount.addresszip.style.background = "#ffffff";
       	document.addAccount.addresszip.disabled = false;}
       	if(elm7!=null){

           	elm7.style.color = "#000000";
       	}       

          elm8 = document.getElementById("stateProv1"); // Nome (Organization)
          if(elm8!=null){

         	elm8.style.color = "#000000";
       	
          }
       		elm9 = document.getElementById("latitude1"); // Nome (Organization)
if(document.addAccount.address3latitude!=null){
       	    document.addAccount.address3latitude.style.background = "#ffffff";
       		document.addAccount.address3latitude.disabled = false;}
       		if(elm9!=null){

       		elm9.style.color = "#000000";
       	
       		}
       		elm10 = document.getElementById("longitude1"); // Nome (Organization)
if(document.addAccount.address3longitude!=null){
       		document.addAccount.address3longitude.style.background = "#ffffff";
       		document.addAccount.address3longitude.disabled = false;}
       		if(elm10!=null){

           		elm10.style.color = "#000000";
       		}
       	
       	elm = document.getElementById("tipoStruttura1"); // Nome (Organization)
if(document.addAccount.TipoStruttura!=null){
       	document.addAccount.TipoStruttura.style.background = "#ffffff";
       	document.addAccount.TipoStruttura.disabled = false;}
       	if(elm!=null){

        	elm.style.color = "#000000";
       	}
       elm12 = document.getElementById("indirizzo1");
if(document.addAccount.indirizzo12!=null){
       document.addAccount.indirizzo12.style.background = "#ffffff";
       document.addAccount.indirizzo12.disabled = false;}
       if(elm12!=null){

         elm12.style.color = "#000000";
       }
       elm17 = document.getElementById("prov2");
       if(document.getElementById("prov12")!=null){
       document.getElementById("prov12").disabled = true;
       document.getElementById("prov12").selectedIndex = 0;}
       if(elm17!=null){

         elm17.style.color = "#000000";
       }
       //peppedocument.getElementById("prov").selectedIndex = 0;
       
       elm13 = document.getElementById("cap1");
if(document.addAccount.cap!=null){
        document.addAccount.cap.style.background = "#ffffff";
       document.addAccount.cap.disabled = false;}
       if(elm13!=null){

         elm13.style.color = "#000000";
       }
       elm14 = document.getElementById("stateProv2");
       if(elm14!=null){

           elm14.style.color = "#000000";
       }
       elm15 = document.getElementById("latitude2");
if( document.addAccount.address2latitude!=null){
           document.addAccount.address2latitude.style.background = "#ffffff";
       document.addAccount.address2latitude.disabled = false;

  }
        if(elm15!=null){

        elm15.style.color = "#000000";
       }
       elm16 = document.getElementById("longitude2");
if( document.addAccount.address2longitude!=null){
          document.addAccount.address2longitude.style.background = "#ffffff";
       document.addAccount.address2longitude.disabled = false;
}
       if(elm16!=null){

           elm16.style.color = "#000000";
       }   
       document.addAccount.address1type.style.background = "#ffffff";
       document.addAccount.address1type.disabled = false;
       document.addAccount.address1type.style.color="#000000"
       	
       
       
  }
</script>

 
<script type="text/javascript">
/*script per gestione modulo di calcolo coordinate
   var geocoder = null;

   function initialize() {
            if (GBrowserIsCompatible()) {
                geocoder = new GClientGeocoder();
            }
   }

   function showAddress(address, lat, lng) {
   
        initialize();
        if (geocoder) {
            geocoder.getLatLng(
                  address,
                function (point) {
                    if (!point) {
                        alert(address + " non trovato");
                    } else {
                        lat.value = point.lat();
                        lng.value = point.lng();
                    }
                }
            );
        }
        GUnload();
   }*/

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
    
    
<script>
function gestisciProvenienzaEstera(scelta){
	var pi = document.getElementById("partitaIva");
	var divListEst = document.getElementById("divListaEstera");
	if (divListEst!=null){
	if (scelta==1){
		pi.maxLength="99";
		divListEst.style.display="block";
		}
	else{
		pi.maxLength="11";
		divListEst.style.display="none";
	}
	}
}

function inizializzaProvenienzaEstera(idNazione){
	if (idNazione==106)
		gestisciProvenienzaEstera(0);
	else
		gestisciProvenienzaEstera(1);

}

</script>

<script>
function gestisciPIVA(ckb){
	if (ckb.checked){
		//DISABILITA P_IVA
		document.getElementById("partitaIva").value="";
		document.getElementById("partitaIva").disabled="disabled";
		document.getElementById("linkpiva").style.display="none";
		document.getElementById("no_cf").style.display="block";
		document.getElementById("codiceFiscale").value="";
		document.getElementById("codiceFiscale").disabled="";
	} else{
		//RIABILITA P_IVA
		document.getElementById("partitaIva").value="";
		document.getElementById("partitaIva").disabled="";
		document.getElementById("linkpiva").style.display="";
		document.getElementById("no_cf").style.display="none";
		document.getElementById("codiceFiscale").value="";
		document.getElementById("codiceFiscale").disabled="disabled";
	}
}
</script>

<%
	LineeAttivita linea_attivita_principale = (LineeAttivita) request.getAttribute("linea_attivita_principale");
	ArrayList<LineeAttivita> linee_attivita_secondarie = (ArrayList<LineeAttivita>) request.getAttribute("linee_attivita_secondarie");
	org.aspcfs.utils.web.LookupList lookup_vuota_linea_attivita = new org.aspcfs.utils.web.LookupList();
	lookup_vuota_linea_attivita.addItem(-1, "-- Selezionare prima il codice Ateco --" );
%>

 <body onLoad="caricaCodici() ;abilitaDistributoriCampi('<%=OrgDetails.getTipoDest() %>');abilita_codici_ateco_vuoti(); inizializzaProvenienzaEstera('<%=OrgDetails.getIdNazione()%>')">
<form name="addAccount" action="Requestor.do?command=Update&orgId=<%= OrgDetails.getOrgId() %>&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
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
<a href="Requestor.do"><dhv:label name="requestor.requestor">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="Requestor.do?command=Search"><dhv:label name="requestor.SearchResults">Search Results</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="Requestor.do?command=Dashboard">Cruscotto</a> >
	<%}%>
<%} else {%>
<a href="Requestor.do?command=Search"><dhv:label name="requestor.SearchResults">Search Results</dhv:label></a> >
<a href="Requestor.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="requestor.details">Account Details</dhv:label></a> >
<%}%>
<dhv:label name="requestor.modify">Modify Account</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="requestor" selected="details" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Requestor.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Requestor.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
<br />
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="requestor.requestor_modify.ModifyPrimaryInformation">Modify Primary Information</dhv:label></strong>
    </th>     
  </tr>
<dhv:include name="accounts-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.site">Site</dhv:label>
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


<dhv:include name="organization.classification" none="true">

  
 
	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Classification">Tipo DIA</dhv:label>
    </td>
    <td>
      <input type="radio" name="dunsType" id="DIA Semplice" value="DIA Semplice" <%=((OrgDetails.getDunsType().equals("DIA Semplice"))?("checked"):(""))%>>
      <dhv:label name="requestor.requestor_add.Individual">D.I.A. Semplice</dhv:label>
      <input type="radio" id="DIA Differita" name="dunsType" value="DIA Differita" <%=((OrgDetails.getDunsType().equals("DIA Differita"))?("checked"):(""))%>>
      <dhv:label name="requestor.requestor_add.Organization">D.I.A. Differita</dhv:label>
      
    </td>
  </tr>	
 
</dhv:include>
      <tr class="containerBody">
        <td nowrap class="formLabel" name="orgname1" id="orgname1">
          <dhv:label name="requestor.requestor_add.OrganizationName">Organization Name</dhv:label>
        </td>
        <td>
          <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
       </td>
      </tr>
      
      
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Ente/Associazione</dhv:label>
    </td>
    <td>
      <input type="checkbox" id="no_piva" name="no_piva"
      		<% if  (OrgDetails.getNo_piva()==true){ %> checked="checked"<%} %>
      		onclick="javascript:gestisciPIVA(this)"/>Partita IVA non obbligatoria
    </td>
  </tr>
  
   <tr class="containerBody">
	    <td class="formLabel" nowrap>
	      Partita IVA
	    </td>
	    <td>
	      <input type="text" size="20" maxlength="11" id = "partitaIva" name="partitaIva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>" >
			<div id="linkpiva">
			<font color="red">*</font>
	    	 &nbsp;[<a href="javascript: popLookupSelectorCheckImprese2(document.getElementById('partitaIva').value,'','lookup_codistat','');"><dhv:label name=""> Verifica Preesistenza </dhv:label></a>] <font color="red">* (Inserire partita iva)</font>
    		</div>
	    </td>
	  </tr>
	  
	  <tr class="containerBody">
	    <td class="formLabel" nowrap>
	      Codice Fiscale
	    </td>
	    <td>
      <input type="text" size="20" maxlength="16" id="codiceFiscale" name="codiceFiscale"    value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>">
      <font id="no_cf" color="red">* (INSERIRE CODICE FISCALE)</font>    
	    </td>
	  </tr>
	  
	 <% if (OrgDetails.getNo_piva()==true || OrgDetails.getPartitaIva()==null || OrgDetails.getPartitaIva().trim().equals("")){ %>
    		<script>
    		    document.getElementById("no_piva").checked="checked";
    			document.getElementById("partitaIva").value="";
    			document.getElementById("partitaIva").disabled="disabled";
    			document.getElementById("linkpiva").style.display="none";
    			document.getElementById("linkpiva").style.display="none";
    			document.getElementById("no_cf").style.display="block";
    		</script>
    <% } 
    	if (OrgDetails.getNo_piva()==false && (OrgDetails.getCodiceFiscale()==null || OrgDetails.getCodiceFiscale().trim().equals(""))) { %>
    		<script>
			    document.getElementById("no_piva").checked="";
				document.getElementById("no_cf").style.display="none";
				document.getElementById("codiceFiscale").value="";
				document.getElementById("codiceFiscale").disabled="disabled";
			</script>
    <% }%>
  
  <tr class="containerBody">
			<td nowrap class="formLabel">
      <dhv:label name="">Vendita con canali non convenzionali</dhv:label>
			</td>
			<td>
         <input type = "checkbox" <% if(OrgDetails.isFlagVenditaCanali()==true){ %> checked="checked"<%} %> />
			</td>
		</tr>
		
		<tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Domicilio Digitale</dhv:label>
    </td>
    <td>
      <input type="text" size="20" maxlength="" name="domicilioDigitale" value="<%= toHtmlValue(OrgDetails.getDomicilioDigitale()) %>">    
    </td>
  </tr>
  
  
  <tr class="containerBody">
	  <td class="formLabel" nowrap>
       		<dhv:label name="">Codice Ateco/Linea di Attivita Principale</dhv:label>
	  </td>
	
	  <td>
	   <input type="hidden" id="id_attivita_masterlist" name="id_attivita_masterlist" value="<%= linea_attivita_principale.getId_attivita_masterlist() %>">
<%-- 	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="codiceFiscaleCorrentista" name="codiceFiscaleCorrentista" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleCorrentista()) %>"><font color="red">*</font>	--%>
	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="codiceFiscaleCorrentista" name="codiceFiscaleCorrentista" value="<%=(linea_attivita_principale!=null) ? toHtmlValue( linea_attivita_principale.getCodice_istat() ) : "" %>" onchange="costruisci_rel_ateco_attivita('codiceFiscaleCorrentista', 'id_rel_principale' );"   ><font color="red">*</font>
	  &nbsp;[<a href="javascript:popLookupSelectorCustomImprese('codiceFiscaleCorrentista','alertText','id_attivita_masterlist','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Seleziona</dhv:label></a>]
	  
	  <%
	  int value = -1 ;
	  if(linea_attivita_principale!=null)
	  {
		  value = linea_attivita_principale.getId_rel_ateco_attivita();
	  }
	  
	  %>
	  
	  <br/><%= List_id_rel_principale.getHtmlSelect("id_rel_principale", value ) %>
	  
	</td>
  </tr>
  
  
    <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
    </td>
    <td>
<%--       <input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="alertText" name="alertText" value="<%= toHtmlValue(OrgDetails.getAlertText()) %>"> --%>
		   <input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="alertText" name="alertText" value="<%=(linea_attivita_principale!=null)? toHtmlValue( linea_attivita_principale.getDescrizione_codice_istat()):""  %>"> 
    </td>
  </tr>
  
	<input type="hidden" name="id_la_principale_OLD" value="<%=(linea_attivita_principale!=null) ? linea_attivita_principale.getId() : "-1"%>" >
	<input type="hidden" name="id_la_1_OLD" value="<%=  (linee_attivita_secondarie!=null && linee_attivita_secondarie.size()>0) ? linee_attivita_secondarie.get(0).getId(): "-1" %>" >
	<input type="hidden" name="id_la_2_OLD" value="<%=  (linee_attivita_secondarie!=null && linee_attivita_secondarie.size()>1) ? linee_attivita_secondarie.get(1).getId(): "-1" %>" >
	<input type="hidden" name="id_la_3_OLD" value="<%=  (linee_attivita_secondarie!=null && linee_attivita_secondarie.size()>2) ? linee_attivita_secondarie.get(2).getId(): "-1" %>" >
	<input type="hidden" name="id_la_4_OLD" value="<%=  (linee_attivita_secondarie!=null && linee_attivita_secondarie.size()>3) ? linee_attivita_secondarie.get(3).getId(): "-1" %>" >
	<input type="hidden" name="id_la_5_OLD" value="<%=  (linee_attivita_secondarie!=null && linee_attivita_secondarie.size()>4) ? linee_attivita_secondarie.get(4).getId(): "-1" %>" >
	<input type="hidden" name="id_la_6_OLD" value="<%=  (linee_attivita_secondarie!=null && linee_attivita_secondarie.size()>5) ? linee_attivita_secondarie.get(5).getId(): "-1" %>" >
	<input type="hidden" name="id_la_7_OLD" value="<%=  (linee_attivita_secondarie!=null && linee_attivita_secondarie.size()>6) ? linee_attivita_secondarie.get(6).getId(): "-1" %>" >
	<input type="hidden" name="id_la_8_OLD" value="<%=  (linee_attivita_secondarie!=null && linee_attivita_secondarie.size()>7) ? linee_attivita_secondarie.get(7).getId(): "-1" %>" >
	<input type="hidden" name="id_la_9_OLD" value="<%=  (linee_attivita_secondarie!=null && linee_attivita_secondarie.size()>8) ? linee_attivita_secondarie.get(8).getId(): "-1" %>" >
	<input type="hidden" name="id_la_10_OLD" value="<%= (linee_attivita_secondarie!=null && linee_attivita_secondarie.size()>9) ? linee_attivita_secondarie.get(9).getId(): "-1" %>" >
  
  <tr class="containerBody">
	<td class="formLabel" nowrap>
	  <dhv:label name="">Codici Ateco/Linea di Attività (Secondarie)</dhv:label>
	</td>
	<%--<td>
	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="abi" name="abi" value="<%= toHtmlValue(OrgDetails.getAbi()) %>">
	  &nbsp;[<a href="javascript:popLookupSelectorCustom('abi','cab','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
	</td>--%>
	<td>
			<b>Codice 1&nbsp;&nbsp;</b>
			 <input type="hidden" id="id_attivita_masterlist_1" name="id_attivita_masterlist_1" value="<%= ((linee_attivita_secondarie.size()>0) ? (toHtmlValue(linee_attivita_secondarie.get(0).getId_attivita_masterlist())) : ("-1")) %>">
      		<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice1" name="codice1" class="codiciatecolista"
      			   onchange="costruisci_rel_ateco_attivita('codice1',  'id_rel_1' ); abilita_codici_ateco_vuoti();"	value="<%= ((linee_attivita_secondarie.size()>0) ? (toHtmlValue(linee_attivita_secondarie.get(0).getCodice_istat())) : ("")) %>" >
      		[<a href="javascript:popLookupSelectorCustomImprese('codice1','cod1', 'id_attivita_masterlist_1','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod1" name="cod1" value="<%= ((linee_attivita_secondarie.size()>0) ? (toHtmlValue(linee_attivita_secondarie.get(0).getDescrizione_codice_istat())) : ("")) %>" >
      		<br/><%
				if (linee_attivita_secondarie.size()>0)
					out.println( (List_id_rel_1.getHtmlSelect("id_rel_1" , linee_attivita_secondarie.get(0).getId_rel_ateco_attivita()  ) )  );
				else
					out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_1" , -1 ) ) );
					
			%><br/>
			<br></br>
        	
      		 <div id="div_codice1" style="display: none">
      		 	<b>Codice 2&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_2" name="id_attivita_masterlist_2" value="<%= ((linee_attivita_secondarie.size()>1) ? (toHtmlValue(linee_attivita_secondarie.get(1).getId_attivita_masterlist())) : ("-1")) %>">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice2" name="codice2" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice2',  'id_rel_2' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>1) ? (toHtmlValue(linee_attivita_secondarie.get(1).getCodice_istat())) : ("")) %>" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice2','cod2', 'id_attivita_masterlist_2','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod2" name="cod2" value="<%= ((linee_attivita_secondarie.size()>1) ? (toHtmlValue(linee_attivita_secondarie.get(1).getDescrizione_codice_istat())) : ("")) %>" >
      		 	<br/><%
					if (linee_attivita_secondarie.size()>1)
						out.println(List_id_rel_2.getHtmlSelect("id_rel_2", linee_attivita_secondarie.get(1).getId_rel_ateco_attivita() ) );
					else
						out.println(lookup_vuota_linea_attivita.getHtmlSelect("id_rel_2", -1 ));
						
				%><br/>
				<br></br>
      		 </div>
      		 
      		 <div id="div_codice2" style="display: none">
      		 	<b>Codice 3&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_3" name="id_attivita_masterlist_3" value="<%= ((linee_attivita_secondarie.size()>2) ? (toHtmlValue(linee_attivita_secondarie.get(2).getId_attivita_masterlist())) : ("-1")) %>">
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice3" name="codice3" class="codiciatecolista"
      		    	onchange="costruisci_rel_ateco_attivita('codice3',  'id_rel_3' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>2) ? (toHtmlValue(linee_attivita_secondarie.get(2).getCodice_istat())) : ("")) %>" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice3','cod3', 'id_attivita_masterlist_3','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod3" name="cod3" value="<%= ((linee_attivita_secondarie.size()>2) ? (toHtmlValue(linee_attivita_secondarie.get(2).getDescrizione_codice_istat())) : ("")) %>" >
      		    <br/><%
					if (linee_attivita_secondarie.size()>2)
						out.println( (List_id_rel_3.getHtmlSelect("id_rel_3" , linee_attivita_secondarie.get(2).getId_rel_ateco_attivita()  ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_3" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>
      		 
      		 <div id="div_codice3" style="display: none">
      		 	<b>Codice 4&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_4" name="id_attivita_masterlist_4" value="<%= ((linee_attivita_secondarie.size()>3) ? (toHtmlValue(linee_attivita_secondarie.get(3).getId_attivita_masterlist())) : ("-1")) %>">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice4" name="codice4" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice4',  'id_rel_4' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>3) ? (toHtmlValue(linee_attivita_secondarie.get(3).getCodice_istat())) : ("")) %>" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice4','cod4','id_attivita_masterlist_4','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod4" name="cod4" value="<%= ((linee_attivita_secondarie.size()>3) ? (toHtmlValue(linee_attivita_secondarie.get(3).getDescrizione_codice_istat())) : ("")) %>" >
      		 	<br/><%
					if (linee_attivita_secondarie.size()>3)
						out.println( (List_id_rel_4.getHtmlSelect("id_rel_4" , linee_attivita_secondarie.get(3).getId_rel_ateco_attivita()  ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_4" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice4" style="display: none">
      		 	<b>Codice 5&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_5" name="id_attivita_masterlist_5" value="<%= ((linee_attivita_secondarie.size()>4) ? (toHtmlValue(linee_attivita_secondarie.get(4).getId_attivita_masterlist())) : ("-1")) %>">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice5" name="codice5" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice5',  'id_rel_5' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>4) ? (toHtmlValue(linee_attivita_secondarie.get(4).getCodice_istat())) : ("")) %>" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice5', 'cod5','id_attivita_masterlist_5','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod5" name="cod5" value="<%= ((linee_attivita_secondarie.size()>4) ? (toHtmlValue(linee_attivita_secondarie.get(4).getDescrizione_codice_istat())) : ("")) %>" >
      		    <br/><%
					if (linee_attivita_secondarie.size()>4)
						out.println( (List_id_rel_5.getHtmlSelect("id_rel_5" , linee_attivita_secondarie.get(4).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_5" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice5" style="display: none">
      		 	<b>Codice 6&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_6" name="id_attivita_masterlist_6" value="<%= ((linee_attivita_secondarie.size()>5) ? (toHtmlValue(linee_attivita_secondarie.get(5).getId_attivita_masterlist())) : ("-1")) %>">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice6" name="codice6" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice6',  'id_rel_6' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>5) ? (toHtmlValue(linee_attivita_secondarie.get(5).getCodice_istat())) : ("")) %>" >
      		  	[<a href="javascript:popLookupSelectorCustomImprese('codice6','cod6', 'id_attivita_masterlist_6','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod6" name="cod6" value="<%= ((linee_attivita_secondarie.size()>5) ? (toHtmlValue(linee_attivita_secondarie.get(5).getDescrizione_codice_istat())) : ("")) %>" >
      		  	<br/><%
					if (linee_attivita_secondarie.size()>5)
						out.println( (List_id_rel_6.getHtmlSelect("id_rel_6" , linee_attivita_secondarie.get(5).getId_rel_ateco_attivita()  ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_6" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice6" style="display: none">
      		 	<b>Codice 7&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_7" name="id_attivita_masterlist_7" value="<%= ((linee_attivita_secondarie.size()>6) ? (toHtmlValue(linee_attivita_secondarie.get(6).getId_attivita_masterlist())) : ("-1")) %>">
      		  	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice7" name="codice7" class="codiciatecolista"
      		  		onchange="costruisci_rel_ateco_attivita('codice7',  'id_rel_7' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>6) ? (toHtmlValue(linee_attivita_secondarie.get(6).getCodice_istat())) : ("")) %>" >
      		  	[<a href="javascript:popLookupSelectorCustomImprese('codice7','cod7', 'id_attivita_masterlist_7','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod7" name="cod7" value="<%= ((linee_attivita_secondarie.size()>6) ? (toHtmlValue(linee_attivita_secondarie.get(6).getDescrizione_codice_istat())) : ("")) %>" >
      		  	<br/><%
					if (linee_attivita_secondarie.size()>6)
						out.println( (List_id_rel_7.getHtmlSelect("id_rel_7" , linee_attivita_secondarie.get(6).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_7" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>	

      		 <div id="div_codice7" style="display: none">
      		 	<b>Codice 8&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_8" name="id_attivita_masterlist_8" value="<%= ((linee_attivita_secondarie.size()>7) ? (toHtmlValue(linee_attivita_secondarie.get(7).getId_attivita_masterlist())) : ("-1")) %>">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice8" name="codice8" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice8',  'id_rel_8' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>7) ? (toHtmlValue(linee_attivita_secondarie.get(7).getCodice_istat())) : ("")) %>" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice8', 'cod8','id_attivita_masterlist_8','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod8" name="cod8" value="<%= ((linee_attivita_secondarie.size()>7) ? (toHtmlValue(linee_attivita_secondarie.get(7).getDescrizione_codice_istat())) : ("")) %>" >
      		 	<br/><%
					if (linee_attivita_secondarie.size()>7)
						out.println( (List_id_rel_8.getHtmlSelect("id_rel_8" , linee_attivita_secondarie.get(7).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_8" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice8" style="display: none">
      		 	<b>Codice 9&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_9" name="id_attivita_masterlist_9" value="<%= ((linee_attivita_secondarie.size()>8) ? (toHtmlValue(linee_attivita_secondarie.get(8).getId_attivita_masterlist())) : ("-1")) %>">
         	    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice9" name="codice9" class="codiciatecolista"
         	    	onchange="costruisci_rel_ateco_attivita('codice9',  'id_rel_9' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>8) ? (toHtmlValue(linee_attivita_secondarie.get(8).getCodice_istat())) : ("")) %>" >
         	    [<a href="javascript:popLookupSelectorCustomImprese('codice9','cod9', 'id_attivita_masterlist_9','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
         	    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod9" name="cod9" value="<%= ((linee_attivita_secondarie.size()>8) ? (toHtmlValue(linee_attivita_secondarie.get(8).getDescrizione_codice_istat())) : ("")) %>" >
         	    <br/><%
					if (linee_attivita_secondarie.size()>8)
						out.println( (List_id_rel_9.getHtmlSelect("id_rel_9" , linee_attivita_secondarie.get(8).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_9" , -1 ) ) );
						
				%><br/>
				<br></br>	
         	 </div>
      		 
			<div id="div_codice9" style="display: none">
      		 	<b>Codice 10</b>
      		 	<input type="hidden" id="id_attivita_masterlist_10" name="id_attivita_masterlist_10" value="<%= ((linee_attivita_secondarie.size()>9) ? (toHtmlValue(linee_attivita_secondarie.get(9).getId_attivita_masterlist())) : ("-1")) %>">
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice10" name="codice10" class="codiciatecolista"
      		    	onchange="costruisci_rel_ateco_attivita('codice10', 'id_rel_10'); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>9) ? (toHtmlValue(linee_attivita_secondarie.get(9).getCodice_istat())) : ("")) %>" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice10', 'cod10','id_attivita_masterlist_10','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod10" name="cod10" value="<%= ((linee_attivita_secondarie.size()>9) ? (toHtmlValue(linee_attivita_secondarie.get(9).getDescrizione_codice_istat())) : ("")) %>" >
      		    <br/><%
					if (linee_attivita_secondarie.size()>9)
						out.println( (List_id_rel_10.getHtmlSelect("id_rel_10" , linee_attivita_secondarie.get(9).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_10" , -1 ) ) );
						
				%><br/>
				<br></br>	
        	 </div>
        	 <br><br>
        	 [ <a href="javascript:resetCodiciIstatSecondari()">Elimina codici istat </a> ]
      </td>
  </tr>
  
  
  
  
  
  <%if((OrgDetails.getContoCorrente()==null) || (OrgDetails.getContoCorrente()== "")) {}else{%>
		<%}if((OrgDetails.getCodiceCont()==null)||(OrgDetails.getContoCorrente()== "")) {}else{ %>
	 <tr class="containerBody" id="list3">
    <td class="formLabel" nowrap  id="codiceCont1">
      <dhv:label name="">Codice Contenitore</dhv:label>
    </td>
    <td>
      <input id="codiceCont" type="text" size="20" maxlength="20" name="codiceCont" value="<%= toHtmlValue(OrgDetails.getCodiceCont()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  <%} %>
   <%//if(hasText(OrgDetails.getTipoDest())) {%>
  	
  	<tr class="containerBody">
  		<td nowrap class="formLabel">
     		 <dhv:label name="">Attività</dhv:label>
    	</td>
    	
    	<%if(OrgDetails.getTipoDest()!=null){ %>
    	<td>
    	 <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Autoveicolo")%>">
        Mobile
        </dhv:evaluate>
       
        <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Es. Commerciale")%>">
      Fissa
      
      <div id="divProvenienza" style="display:block">
        <dhv:label name="">Provenienza: </dhv:label>
       <input type="radio" name="provenienza" id="provenienzaIT" <%if (OrgDetails.getIdNazione()==106) {%> checked="checked" <% } %> value="ITALIA" onclick="gestisciProvenienzaEstera(0)"/> <img width="20px" src="images/flags/it.gif"/> Italia
      <input type="radio" name="provenienza" id="provenienzaEST" <%if (OrgDetails.getIdNazione()!=106) {%> checked="checked" <% } %> value="ESTERO" onclick="gestisciProvenienzaEstera(1)";/> <img width="20px" src="images/flags/eu.gif"/> Estera
       
       <div id="divListaEstera" style="display:none">
        <%= CountryList.getHtmlSelect("country",OrgDetails.getIdNazione()) %>   	<font color = "red">*</font>
      </div>
      
      
      </div>	

        
        </dhv:evaluate>
      		&nbsp;
   		</td>
   		<input type="hidden" id="tipoD2" name="tipoDest" value="<%=OrgDetails.getTipoDest() %>">
   		<%}else{ %>
   		<td>
   		<input type="radio" id="tipoD" name="tipoDest" value="Es. Commerciale" onClick="javascript:updateFormElementsNew(0);" checked>
      <%-- Es. Commerciale--%>Fissa
      <input type="radio" id="tipoD2" name="tipoDest" value="Autoveicolo" onClick="javascript:updateFormElementsNew(1);">Mobile
      <%-- Autoveicolo
       <input type="radio" name="tipoDest" value="Contenitore" onClick="javascript:updateFormElementsNew(2);" >
      Contenitore--%>
      <input type="hidden" name="orgType" value="" />

      <input type="hidden" name="check" />
   		</td>
   		
   		<%} %>
  	</tr>
  	<%//}%>
    <dhv:include name="organization.source" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="contact.source">Source</dhv:label>
      </td>
      <td>
      <table border=0>
      <tr>
      <td >
       <%	SourceList.setJsEvent("onChange=\"javascript:selectCarattere('source');\"");
        %>
        <%= SourceList.getHtmlSelect("source",OrgDetails.getSource()) %>
      </td>
 
      <dhv:evaluate if="<%= OrgDetails.getSource()!= 1 %>">
       	<td style="visibility: hidden;" id="data1">
        		Dal
        	</td>
        	<td style="visibility: hidden;" id="data3">
        	
        	 	<input readonly type="text" id="dateI" name="dateI" size="10" value = "<%= toDateString(OrgDetails.getDateI()) %>" />
		<a href="#" onClick="cal19.select(document.forms[0].dateI,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>    
	
        	
           		<font color="red">*</font>
          	</td>
       
       	 	
           	<td style="visibility: hidden;" id="data2">
           		Al
           	</td>
            	<td style="visibility: hidden;" id="data4">
            	
            	
        	 	<input readonly type="text" id="dateF" name="dateF" size="10" value = "<%= toDateString(OrgDetails.getDateF()) %>" />
		<a href="#" onClick="cal19.select(document.forms[0].dateF,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>    
	
            	<font color="red">*</font>
           	</td>
           	<td style="visibility: hidden;" id="cessazione">
           	<input type="checkbox" name="cessazione" value ="true" <%= OrgDetails.getCessazione()?"checked":"" %> /> <dhv:label name="accounts.Assetsf">Cessazione Automatica</dhv:label>
           	</td>
        </dhv:evaluate> 
        <dhv:evaluate if="<%= OrgDetails.getSource()== 1 %>"> 
        <td style="visibility: visible;" id="data1">
        		Dal
        	</td>
        	<td style="visibility: visible;" id="data3">
        	
	 	<input readonly type="text" id="dateI" name="dateI" size="10" value = "<%= toDateString(OrgDetails.getDateI()) %>" />
		<a href="#" onClick="cal19.select(document.forms[0].dateI,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>    
	
        
<font color="red">*</font>
          	</td>
       
       	 	
           	<td style="visibility: visible;" id="data2">
           		Al
           	</td>
            	<td style="visibility: visible;" id="data4">
           		
           		   	
        	 	<input readonly type="text" id="dateF" name="dateF" size="10" value = "<%= toDateString(OrgDetails.getDateF()) %>" />
		<a href="#" onClick="cal19.select(document.forms[0].dateF,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>    
	
           		<font color="red">*</font>
           	</td>
           	<td style="visibility: visible;" id="cessazione">
           	<input type="checkbox" name="cessazione" value ="true" <%= OrgDetails.getCessazione()?"checked":"" %> /> <dhv:label name="accounts.Assetsf">Cessazione Automatica</dhv:label>
           	</td>
           	</dhv:evaluate>
           	
    </tr>
    </table>
    </tr>
  </dhv:include>    
   <dhv:include name="organization.alert" none="true">
    <tr class="containerBody" style="display: none">
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.AlertDate">Alert Date</dhv:label>
      </td>
      <td>
      
         	
        	 	<input readonly type="text" id="alertDate" name="alertDate" size="10" value = "<%= toDateString(OrgDetails.getAlertDate()) %>" />
		<a href="#" onClick="cal19.select(document.forms[0].alertDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>    
	
        <font color="red">*</font>
        <%= showAttribute(request, "alertDateError") %><%= showWarningAttribute(request, "alertDateWarning") %>
      </td>
    </tr>
  </dhv:include>
        

   
 


    <dhv:include name="organization.date1" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.data">Data Presentazione D.I.A./Inizio Attività</dhv:label>
      </td>
      <%--<td>
                      <zeroio:dateSelect form="addAccount" field="dataPresentazione" timestamp="<%= OrgDetails.getDataPresentazione() %>" showTimeZone="false" /><font color="red">*</font>
         </td>--%>
         <td>
      	
      	<input readonly type="text" id="dataPresentazione" name="dataPresentazione" size="10" value="<%= toDateasString(OrgDetails.getDataPresentazione())%>"/>
        <a href="#" onClick="cal19.select(document.forms[0].dataPresentazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
        <font color="red">*</font>
      </td>
    </tr>
    </dhv:include>

  	<tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="requestor.stage">Stage</dhv:label>
      </td>
      <td>
        <%= StageList.getHtmlSelect("stageId",OrgDetails.getStageId()) %>
        <font color = "red">*</font>
      </td>
    </tr>   




  <!-- aggiunto da d.dauria -->
  
  </table>
  
  <table>
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr>
  </table>
  
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
    </th>
  </tr>
  
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Codice fiscale Rappresentante</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="codiceFiscaleRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Nome Rappresentante</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="nomeRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font>
      </td>
    </tr>
  </dhv:include>
  
    <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Cognome Rappresentante</dhv:label>
      </td>
      <td>

        <input type="text" size="50" name="cognomeRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font>

      </td>
    </tr>
  </dhv:include>
  <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Nascita</dhv:label>
      </td>
       <td>
      <input readonly type="text" id="dataNascitaRappresentante" name="dataNascitaRappresentante" size="10" value="<%= (OrgDetails.getDataNascitaRappresentante()==null)?(""):(getLongDate(OrgDetails.getDataNascitaRappresentante()))%>" />
       <a href="#" onClick="cal19.select(document.forms[0].dataNascitaRappresentante,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di Nascita</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtmlValue(OrgDetails.getLuogoNascitaRappresentante()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di residenza</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="city_legale_rapp" value="<%= toHtmlValue(OrgDetails.getCity_legale_rapp()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Provincia</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="prov_legale_rapp" value="<%= toHtmlValue(OrgDetails.getProv_legale_rapp()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="">Indirizzo</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="address_legale_rapp" value="<%= toHtmlValue(OrgDetails.getAddress_legale_rapp()) %>">
    </td>
  </tr>
  
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Email Rappresentante</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="emailRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getEmailRappresentante()) %>">
      </td>
    </tr>
  </dhv:include>
  
  <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Telefono Rappresentante</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="telefonoRappresentante" maxlength="300" value="<%= toHtmlValue(OrgDetails.getTelefonoRappresentante()) %>">
      </td>
    </tr>
  </dhv:include>
  
    <dhv:include name="" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Fax</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="fax" maxlength="300" value="<%= toHtmlValue(OrgDetails.getFax()) %>">
      </td>
    </tr>
  </dhv:include>
  
  <!-- fine delle modifiche -->
  

</table>
<br>
<%
  boolean noneSelected = false;
  int s=1;	
boolean primo=false;
int flag=0;
int numeroLocaiFunz=0;
  int acount = 0;
  ArrayList<OrganizationAddress> locali_funz_collegati = new ArrayList<OrganizationAddress>();
  int locali=0;
  Iterator anumber = OrgDetails.getAddressList().iterator();

  while(anumber.hasNext() ){
   
	 
    org.aspcfs.modules.requestor.base.OrganizationAddress thisAddress=(org.aspcfs.modules.requestor.base.OrganizationAddress)anumber.next();
  	if (thisAddress.getType()==6)
  	{
  		numeroLocaiFunz ++ ;
  		locali_funz_collegati.add(thisAddress);
  	}
  	else
  	{
  		 ++acount;
%> 

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <dhv:evaluate if="<%= thisAddress.getType() == 1 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede Legale</dhv:label></strong>
	  </dhv:evaluate>  
     	  <dhv:evaluate if="<%= thisAddress.getType() == 5 %>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede operativa</dhv:label></strong>
	  </dhv:evaluate>  
	  <dhv:evaluate if="<%= thisAddress.getType() == 7%>">
	    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede attività mobile</dhv:label></strong>
	  </dhv:evaluate> 
      <%-- %><strong><dhv:label name="requestor.requestor_add.Addressess"><%= toHtml(thisAddress.getTypeName())%></dhv:label></strong>--%>
      <input type="hidden" name="address<%=acount %>id" value="<%=thisAddress.getId() %>">
      
    </th>
    <input type="hidden" name = "address<%=acount %>type" value ="<%= thisAddress.getType()%>">
   <dhv:evaluate if="<%=thisAddress.getType() == 7 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel" id="tipoStruttura1">
        <dhv:label name="contact.fsource">Tipo Struttura</dhv:label>
    	<%-- %><input type="hidden" name="address3type" value="7">--%>
      </td>
      <td class="containerBody" id="tipoStruttura">
        <%= TipoStruttura.getHtmlSelect("TipoStruttura",OrgDetails.getTipoStruttura())%>
      </td>
     </tr>
     <%if((OrgDetails.getContoCorrente()==null) || (OrgDetails.getContoCorrente()== "")) {}else{%>
  		<tr class="containerBody" id="list"  >
    <td class="formLabel" nowrap  id="tipoVeicolo1">
      <dhv:label name="">Tipo Autoveicolo</dhv:label>
    </td>
    <td>
      <input id="tipoVeicolo" type="text" size="30" maxlength="50" name="contoCorrente" value="<%= toHtmlValue(OrgDetails.getContoCorrente()) %>">
    </td>
  </tr>
  <tr class="containerBody" id="list2" >
    <td class="formLabel" nowrap id="targaVeicolo1">
      <dhv:label name="">Targa Autoveicolo</dhv:label>
    </td>
    <td>
      <input id="targaVeicolo" type="text" size="20" maxlength="10" name="nomeCorrentista" value="<%= toHtmlValue(OrgDetails.getNomeCorrentista()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
    <%} %>
   
   </dhv:evaluate>
 
  
  </tr> 
  
  
    
 
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
    
    
   
    <%
   
    if(thisAddress.getType()==1 && (OrgDetails.getTipoDest().equals("Autoveicolo") || OrgDetails.getTipoDest().equals("Distributori"))){%>
    <select  name="address1city" id="address1city">
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
    <%}else if(thisAddress.getType()==1 && OrgDetails.getTipoDest().equals("Es. Commerciale")){%>
       <input type="text" name="address1city" id="address1city" value="<%=thisAddress.getCity() %>">
      
    <%}else if((thisAddress.getType()==5)&&(OrgDetails.getTipoDest().equals("Es. Commerciale"))){ %>
     <select  name="address2city" id="prov12">
	<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            
	 <%
                Vector v = OrgDetails.getComuni2();
	 			Enumeration e=v.elements();
	 			
	 			 UserBean user = (UserBean) session.getAttribute("User");
              if(user.getRoleId()==16 && user.getSiteId()==8)
              {
            	  %>
            	   <option value="ACERRA" <%if(thisAddress.getCity() != null && thisAddress.getCity().equalsIgnoreCase( "ACERRA" ) ){%>selected="selected"<%} %>>ACERRA</option>	
             
            	   <option value="CASALNUOVO DI NAPOLI" <%if(thisAddress.getCity() != null && thisAddress.getCity().equalsIgnoreCase( "CASALNUOVO DI NAPOLI" ) ){%>selected="selected"<%} %>>CASALNUOVO DI NAPOLI</option>	
             
            	  <% 
              }
	 			
	 			
	 			
                while (e.hasMoreElements()) {
                	String prov=e.nextElement().toString();
                  
        %>
                <option <%if(thisAddress.getCity() != null && thisAddress.getCity().equalsIgnoreCase( prov ) ){%>selected="selected"<%} %>  value="<%=prov%>"><%= prov %></option>	
              <%}%>
		
	</select>
    <%}else{ %>
    
    <input type = "text"  name="address<%= acount %>city" value = "<%=thisAddress.getCity() %>">
	
	<%} %>
	 
    
    
    </td>
  </tr>
  
 
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
   
      <input type="text" size="40" name="address<%= acount %>line1" id="address<%= acount %>line1" maxlength="80" value="<%= toHtmlValue(thisAddress.getStreetAddressLine1()) %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate>
    </td>
  </tr>

   <dhv:evaluate if="<%=thisAddress.getType() == 1 %>">
      <tr class="containerBody">
       <td nowrap class="formLabel">
          <dhv:label name="">C/O</dhv:label>
       </td>
       <td>
        <input type="text" size="40" name="address1line2" maxlength="80" value="<%= ((thisAddress.getStreetAddressLine2()!=null) ? (thisAddress.getStreetAddressLine2()) : ("")) %>">
      </td>
  </tr>
  </dhv:evaluate>
   <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    
    <td>
      <input type="text" size="10" name="address<%= acount %>zip" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>">
    </td>

  
  </tr>  
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>   
    
    <%
    
	    if(thisAddress.getType() == 1)
	    {
		    if(OrgDetails.getState()==null) 
		    {
    %>
            <input type="text" size="10" name="address<%= acount %>state" maxlength="80" value=""> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate>
   <%
   			}
		    else
		    {
		    	
	%>
        <input type="text" size="10" name="address1state" maxlength="80" value="<%=OrgDetails.getState()%>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate>
   <%
   			}
		}
	    else {
  %>
    
	   <input type="text"  size="28" name="address2state" maxlength="80" value="<%=((thisAddress.getState()!=null)?(thisAddress.getState()):("")) %>"><font color="red">*</font>
         
       <%-- input type="text" readonly="readonly" size="10" name="address<%= acount %>state" maxlength="80" value="<%=OrgDetails.getState()%>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate--%>
       <%} %>
    </td>
  </tr>

  
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" readonly="readonly" name="address<%= acount %>latitude" size="30" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLatitude()) : "") %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate>
    	<% if(!OrgDetails.getTipoDest().equals("Es. Commerciale")) { %>  
	    		<font color = "red">*</font>
	    <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.longitude">Longitude</dhv:label></td>
    <td>
    	<input type="text" readonly="readonly"  name="address<%= acount %>longitude" size="30" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLongitude()) : "") %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate>
    	<% if(!OrgDetails.getTipoDest().equals("Es. Commerciale")) { %>  
	    		<font color = "red">*</font>
	    <% } %>
    </td>
  </tr>
  <% if(acount ==1) { %>  
    	<tr style="display: block">
    		<td colspan="2">
    		<input id="coordbutton" type="button" value="Calcola Coordinate" 
    		onclick="javascript:showCoordinate(document.forms['addAccount'].address1line1.value, document.forms['addAccount'].address1city.value,document.forms['addAccount'].address1state.value, document.forms['addAccount'].address1zip.value, document.forms['addAccount'].address1latitude, document.forms['addAccount'].address1longitude);" /> 
     	</td>
    </tr>
    <%}%>
  
  <% if(acount ==2) { %>  
    	<tr style="display: block">
    		<td colspan="2">
    		<input id="coord1button" type="button" value="Calcola Coordinate" 
    		onclick="javascript:showCoordinate(document.getElementById('address2line1').value, document.forms['addAccount'].address2city.value,document.forms['addAccount'].address2state.value, document.forms['addAccount'].address2zip.value, document.forms['addAccount'].address2latitude, document.forms['addAccount'].address2longitude);" /> 
     	</td>
    </tr>
    <%}}} %>
  
    </table><br>
  
  <%if (!OrgDetails.getTipoDest().equals("Distributori")) %>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  
   <input type="hidden" id = "elementi" name="elementi" value="<%=numeroLocaiFunz %>">
   <input type="hidden" id = "size" name="size" value="<%=numeroLocaiFunz %>">
   
   <tr>
   <%
   if(numeroLocaiFunz == 0)
   {
	   %>
	   <td id = "locale_1">
    <table  width="100%" class="details"  >
    <tr>
    <th colspan="2" id = "intestazione">
      <strong><label id = "intestazione">Locale Funzionalmente collegato</label></strong>
    </th>
  </tr>
    <input type="hidden" name="address1id" value="-1">
     <tr id = "locale1_tipo">
      <td nowrap class="formLabel" id="tipoStruttura1">
        <dhv:label name="contact.fsource">Tipo locale</dhv:label>
 		<input type="hidden" name="address4type1" value="6">
      </td>
    <td>
   
        <%= TipoLocale.getHtmlSelect("TipoLocale",OrgDetails.getTipoLocale())%>
      
      </td>
  </tr>
  <tr class="containerBody" id = "locale1_city">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
            <input type="text" size="28" id="address4city1" name="address4city1" maxlength="80" value="">
   
      </td>
  </tr>
  <tr class="containerBody" id = "locale1_indirizzo">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
 
         <input type="text" size="40" name="address4line1" maxlength="80" value="">
 
    </td>
  </tr>
 
  
  <tr class="containerBody" id = "locale1_zip">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address4zip1" maxlength="12" value="">
    </td>
  </tr>
  <tr class="containerBody" id = "locale1_prov">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
       <input type="text" size="25" name="address4state1"   value="">
    </td>
  </tr>

  <tr class="containerBody" id = "locale1_lat">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" id="address4latitude1" name="address4latitude1" size="30" value="" > 
    	
   </td>
  </tr>
  <tr class="containerBody" id = "locale1_long">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" id="address4longitude1" name="address4longitude1" size="30" value=""></td>
  </tr></table>
    </td>
	   <%
   }else
   {
	int i = 0;
   for (OrganizationAddress address : locali_funz_collegati)
	 {
	   i++;
	 %>
   <td id = "locale_<%=i %>">
    <table  width="100%" class="details"  >
    <tr>
    <th colspan="2" id = "intestazione">
      <strong><label id = "intestazione">Locale Funzionalmente collegato <%=i %></label></strong>
    </th>
  </tr>
    <input type="hidden" name="address4id<%=i %>" value="<%=address.getId() %>">
     <tr id = "locale1_tipo">
      <td nowrap class="formLabel" id="tipoStruttura1">
        <dhv:label name="contact.fsource">Tipo locale</dhv:label>
 		<input type="hidden" name="address4type<%=i %>" value="6">
      </td>
    <td>
   		
   		<%if (i==1){ %>
        <%= TipoLocale.getHtmlSelect("TipoLocale",OrgDetails.getTipoLocale())%>
        <%} %>
        <%if (i==2){ %>
        <%= TipoLocale.getHtmlSelect("TipoLocale2",OrgDetails.getTipoLocale2())%>
        <%} %>
        <%if (i==3){ %>
        <%= TipoLocale.getHtmlSelect("TipoLocale3",OrgDetails.getTipoLocale3())%>
        <%} %>
      
      </td>
  </tr>
  <tr class="containerBody" id = "locale1_city">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
            <input type="text" size="28" id="address4city<%=i %>" name="address4city<%=i %>" maxlength="80" value="<%=address.getCity() %>">
   
      </td>
  </tr>
  <tr class="containerBody" id = "locale1_indirizzo">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
 		
 		<%if (i==1)
 			{
 				
 		%>
         <input type="text" size="40" name="address4line1<%=i %>" maxlength="80" value="<%= toHtmlValue(address.getStreetAddressLine1()) %>">
 		<%} else
 		{%>
 		         <input type="text" size="40" name="address4line1<%=i %>" maxlength="80" value="<%= toHtmlValue(address.getStreetAddressLine1()) %>">
 		
 		
 		<%} %>
    </td>
  </tr>
 
  
  <tr class="containerBody" id = "locale1_zip">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address4zip<%=i %>" maxlength="12" value="<%=address.getZip() %>">
    </td>
  </tr>
  <tr class="containerBody" id = "locale1_prov">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
       <input type="text" size="25" name="address4state<%=i %>"   value="<%=toHtml(address.getState()) %>">
    </td>
  </tr>

  <tr class="containerBody" id = "locale1_lat">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" id="address4latitude<%=i %>" name="address4latitude<%=i %>" size="30" value="<%= ( (address.getLatitude()!= 0.0 ) ? ( address.getLatitude()) : ( "" ) ) %>" > 
   </td>
  </tr>
  <tr class="containerBody" id = "locale1_long">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td>
    	<%-- <input type="text" id="address4longitude<%=i %>" name="address4longitude<%=i %>" size="30" value="<%=address.getLongitude() %>"> --%>
    	<input type="text" id="address4longitude<%=i %>" name="address4longitude<%=i %>" size="30" value="<%= ( (address.getLongitude()!= 0.0 ) ? ( address.getLongitude()) : ( "" ) ) %>" > 
   </td>
  </tr>
  </table>
    </td>
    <%}} %>
    </tr>
    
</table>
  

  
  <%
 
 
  OrganizationAddress thisAddress = new OrganizationAddress();
  thisAddress.setCountry(applicationPrefs.get("SYSTEM.COUNTRY"));
%>

<br />




<br>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Controllo della Notifica</dhv:label></strong>
	  </th>
  </tr>
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.date1">Data1</dhv:label>
      </td>
      <td>
      
      	<input readonly type="text" id="date1" name="date1" size="10" value="<%= (OrgDetails.getDate1()==null)?(""):(getLongDate(OrgDetails.getDate1()))%>"/>
        <a href="#" onClick="cal19.select(document.forms[0].date1,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>

      </td>
    </tr>
  
  <tr>
	<td nowrap class="formLabel" name="nameMiddle" id="nameMiddle">
      <dhv:label name="requestor.requestor_add.Classificatio">Esito</dhv:label>
    </td> 
   <td>
	<select onFocus="if (indSelected == 1) { tabNext(this) }" name="nameMiddle">
			<option value=" "><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            <option value="Favorevole" <%= ( (  "Favorevole".equals( OrgDetails.getNameMiddle() )) ? ( "selected='selected'" ) : ( "" ) ) %>>Favorevole</option>
			<option value="Non favorevole" <%= ( (  "Non favorevole".equals( OrgDetails.getNameMiddle() )) ? ( "selected='selected'" ) : ( "" ) ) %>>Non favorevole</option>
			<option value="Favorevole con lievi non conformita'" <%= ( (  "Favorevole con lievi non conformita'".equals( OrgDetails.getNameMiddle() )) ? ( "selected='selected'" ) : ( "" ) ) %>>Favorevole con lievi non conformita'</option>
    </select> 
	</td>
  	</tr>
  	
   <tr>
      <td name="cin" id="cin" nowrap class="formLabel" valign="top">
        <dhv:label name="">Note relative alle non conformità</dhv:label>
      </td>
      <td>
       <TEXTAREA name="cin" ROWS="3" COLS="50"> </TEXTAREA> 
   	<%--	<input type="text" size="50" maxlength="80" name="nameSuffix"> --%>  
      </td>
    </tr>
    
  	<tr>
      <td name="date3" id="date3" nowrap class="formLabel">
        <dhv:label name="">Data di risoluzione della non conformita'</dhv:label>
      </td>
       <td>
      	<div id="data3">
        	<input readonly type="text" id="date3" name="date3" size="10" value="<%= (OrgDetails.getDate3()==null)?(""):(getLongDate(OrgDetails.getDate3()))%>"/>
        <a href="#" onClick="cal19.select(document.forms[0].date3,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        </div>
    </tr>
  
  
  </table>
  </br>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="requestor.requestor_add.AdditionalDetails">Additional Details</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td valign="top" class="formLabel">
        <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
      </td>
      <td>
        <TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA>
      </td>
    </tr>
  </table>
  <br />
  <input type="hidden" name="onlyWarnings" value=<%=(OrgDetails.getOnlyWarnings()?"on":"off")%> />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if(request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Requestor.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Requestor.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="statusId" value="<%=OrgDetails.getStatusId()%>">
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
</dhv:container>
</form>

 