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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>

<%@page import="org.aspcfs.modules.lineeattivita.base.*"%>


<%@page import="java.util.Date"%><jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="contactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<jsp:useBean id="rel_ateco_linea_attivita_List" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<jsp:useBean id="Voltura" class="org.aspcfs.modules.cessazionevariazione.base.Ticket" scope="request"/>
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

<script type="text/javascript" src="dwr/interface/Geocodifica.js"> </script>
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

<script language="JavaScript">
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
function costruisci_obj_rel_ateco_linea_attivita_per_codice_istat_callback(returnValue) {
	  campo_combo_da_costruire = returnValue [2];
	  //alert('Combobox destinazione : ' + campo_combo_da_costruire);
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
	
	      //Aggiungo l'elemento option
	      try
	      {
	    	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	      } catch(e){
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

  function nascondi_div_se_in_modifica_solo_linea_attivita(isInModificaSoloLineaAttivita){
	  //alert('Sono in modifica solo linea attivita? Risposta : ' + isInModificaSoloLineaAttivita);
	  if (isInModificaSoloLineaAttivita=='true') {
		  document.getElementById("div_da_non_vedere_se_in_modifica_solo_linea_attivita").style.display="none";
		  document.getElementById("div_da_vedere_se_in_modifica_solo_linea_attivita").style.display="";
	  } else {
		  document.getElementById("div_da_non_vedere_se_in_modifica_solo_linea_attivita").style.display="";
		  document.getElementById("div_da_vedere_se_in_modifica_solo_linea_attivita").style.display="none";
	  }
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
		if (document.getElementById("div_codice1") != null )
		document.getElementById("div_codice1").style.display="none";
		if (document.getElementById("div_codice2") != null )
		document.getElementById("div_codice2").style.display="none";	
		if (document.getElementById("div_codice3") != null )
		document.getElementById("div_codice3").style.display="none";
		if (document.getElementById("div_codice4") != null )
		document.getElementById("div_codice4").style.display="none";	
		if (document.getElementById("div_codice5") != null )
		document.getElementById("div_codice5").style.display="none";
		if (document.getElementById("div_codice6") != null )
		document.getElementById("div_codice6").style.display="none";	
		if (document.getElementById("div_codice7") != null )
	    document.getElementById("div_codice7").style.display="none";
		if (document.getElementById("div_codice8") != null )
	    document.getElementById("div_codice8").style.display="none";
		if (document.getElementById("div_codice9") != null )
	    document.getElementById("div_codice9").style.display="none";

	    // Blocco di codice che abilita tutti i div che presentano un valore ateco
		if ( document.getElementById("codice1")!=null && (document.getElementById("codice1").value  != "") )			document.getElementById("div_codice1").style.display="";	
		if (document.getElementById("codice2")!=null && (document.getElementById("codice2").value  != "") )			document.getElementById("div_codice2").style.display="";
		if (document.getElementById("codice3")!=null && (document.getElementById("codice3").value  != "") )			document.getElementById("div_codice3").style.display="";
		if (document.getElementById("codice4")!=null && (document.getElementById("codice4").value  != "") )			document.getElementById("div_codice4").style.display="";
		if (document.getElementById("codice5")!=null && (document.getElementById("codice5").value  != "") )			document.getElementById("div_codice5").style.display="";
		if (document.getElementById("codice6")!=null && (document.getElementById("codice6").value  != "") )			document.getElementById("div_codice6").style.display="";
		if (document.getElementById("codice7")!=null && (document.getElementById("codice7").value  != "") )			document.getElementById("div_codice7").style.display="";
		if (document.getElementById("codice8")!=null && (document.getElementById("codice8").value  != "") )			document.getElementById("div_codice8").style.display="";
		if (document.getElementById("codice9")!=null && (document.getElementById("codice9").value  != "") )			document.getElementById("div_codice9").style.display="";

		// Blocco di codice che abilita un div vuoto dopo l"ultimo inserito
		if ( (document.getElementById("codice1")!=null &&  document.getElementById("codice1").value  != "") && document.getElementById("codice2") != null && (document.getElementById("codice2").value  == "") )
			document.getElementById("div_codice1").style.display="";	

		if ( (document.getElementById("codice2")!=null && document.getElementById("codice2").value  != "") && document.getElementById("codice3") != null && (document.getElementById("codice3").value  == "") )
			document.getElementById("div_codice2").style.display="";

		if ( (document.getElementById("codice3")!=null && document.getElementById("codice3").value  != "") && document.getElementById("codice4") != null && (document.getElementById("codice4").value  == "") )
			document.getElementById("div_codice3").style.display="";

		if ( (document.getElementById("codice4")!=null && document.getElementById("codice4").value  != "") && document.getElementById("codice5") != null && (document.getElementById("codice5").value  == "") )
			document.getElementById("div_codice4").style.display="";

		if ( (document.getElementById("codice5")!=null && document.getElementById("codice5").value  != "") && document.getElementById("codice6") != null && (document.getElementById("codice6").value  == "") )
			document.getElementById("div_codice5").style.display="";

		if ( (document.getElementById("codice6")!=null && document.getElementById("codice6").value  != "") && document.getElementById("codice7") != null && (document.getElementById("codice7").value  == "") )
			document.getElementById("div_codice6").style.display="";

		if ( (document.getElementById("codice7")!=null && document.getElementById("codice7").value  != "") && document.getElementById("codice8") != null && (document.getElementById("codice8").value  == "") )
			document.getElementById("div_codice7").style.display="";

		if ( (document.getElementById("codice8")!=null && document.getElementById("codice8").value  != "") && document.getElementById("codice9") != null && (document.getElementById("codice9").value  == "") )
			document.getElementById("div_codice8").style.display="";

		if ( (document.getElementById("codice9")!=null && document.getElementById("codice9").value  != "") && document.getElementById("codice10") != null && (document.getElementById("codice10").value  == "") )
			document.getElementById("div_codice9").style.display="";
		
	}
	


  indSelected = 0;
  orgSelected = 0; 
  function doCheck(form,lineeattivita) {
      if (form.dosubmit.value == "false") {
      return true;
    } else {
     
      return(checkForm(form));
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
  function initializeClassification() {
  
  
  		/*
       <%--- if((OrgDetails.getDunsType().equals("DIA Semplice"))&&(OrgDetails.getDunsType() != null)) { --%>
        
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

    	
      <%--}--%>
      */

      <%if (OrgDetails.getCessato()==0) {%>
		//document.addAccount.contractEndDate.type="hidden";
		//document.getElementById("data_temp").style.visibility="hidden";
  <%}%>
        
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

  function resetCodiciIstatSecondari(){

	  if ( (document.getElementById("codice1").value  != "") ){
		  document.getElementById("codice1").value="";
		  document.getElementById("cod1").value="";
	  } 
	  if (document.getElementById("codice2") != null &&  (document.getElementById("codice2").value  != "") ){
		  document.getElementById("codice2").value="";
		  document.getElementById("cod2").value="";
	  }
	  if (document.getElementById("codice3") != null &&  (document.getElementById("codice3").value  != "") ){
		  document.getElementById("codice3").value="";
		  document.getElementById("cod3").value="";
	  }
	  if (document.getElementById("codice4") != null &&  (document.getElementById("codice4").value  != "") ){
		  document.getElementById("codice4").value="";
		  document.getElementById("cod4").value="";
	  }
	  if (document.getElementById("codice5") != null &&  (document.getElementById("codice5").value  != "") ){
		  document.getElementById("codice5").value="";
		  document.getElementById("cod5").value="";
	  }
	  if (document.getElementById("codice6") != null &&  (document.getElementById("codice6").value  != "") ){
		  document.getElementById("codice6").value="";
		  document.getElementById("cod6").value="";
	  }
	  if (document.getElementById("codice7") != null &&  (document.getElementById("codice7").value  != "") ){
		  document.getElementById("codice7").value="";
		  document.getElementById("cod7").value="";
	  }
	  if (document.getElementById("codice8") != null &&  (document.getElementById("codice8").value  != "") ){
		  document.getElementById("codice8").value="";
		  document.getElementById("cod8").value="";
	  }
	  if (document.getElementById("codice9") != null &&  (document.getElementById("codice9").value  != "") ){
		  document.getElementById("codice9").value="";
		  document.getElementById("cod9").value="";
	  }
	  if (document.getElementById("codice210") != null &&  (document.getElementById("codice10").value  != "") ){
		  document.getElementById("codice10").value="";
		  document.getElementById("cod10").value="";
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


  function abilitaDistributoriCampi(val){

	  if(val =='Distributori')
	  {
		  form = document.forms['addAccount'];
		/*form.address2city.value = "" ;
		form.address2line1.value = "" ;
		form.address2zip.value = "" ;
		form.address2latitude.value = "" ;
		form.address2longitude.value = "" ;

		form.address2city.disabled = "true" ;
		form.address2line1.disabled = "true" ;
		form.address2zip.disabled = "true" ;
		form.address2latitude.disabled = "true" ;
		form.address2longitude.disabled = "true" ;*/

		/*form.address2city.background = "#cccccc";
		form.address2line1.background = "#cccccc";
		form.address2zip.background = "#cccccc";
		form.address2latitude.background = "#cccccc";
		form.address2longitude.background = "#cccccc";*/
		  
		  /*form.TipoLocale.value = "-1" ;
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
function caricaCodici(){

if(document.addAccount.codice2!= null && document.addAccount.codice2.value!=""){

	document.getElementById("div_codice1").style.display="";
	
}
if(document.addAccount.codice3!= null && document.addAccount.codice3.value!=""){
	document.getElementById("div_codice2").style.display="";	

}
if(document.addAccount.codice4!= null && document.addAccount.codice4.value!=""){
	document.getElementById("div_codice3").style.display="";		
}
if(document.addAccount.codice5!= null && document.addAccount.codice5.value!=""){
	document.getElementById("div_codice4").style.display="";}

if(document.addAccount.codice6!= null && document.addAccount.codice6.value!=""){
	
	document.getElementById("div_codice5").style.display="";		
}

if(document.addAccount.codice7!= null && document.addAccount.codice7.value!=""){
	document.getElementById("div_codice6").style.display="";
	}

if(document.addAccount.codice8!= null && document.addAccount.codice8.value!=""){
	document.getElementById("div_codice7").style.display="";	
}

if(document.addAccount.codice9!= null && document.addAccount.codice9.value!=""){
	document.getElementById("div_codice8").style.display="";
	
}

if(document.addAccount.codice10!= null && document.addAccount.codice10.value!=""){
	document.getElementById("div_codice9").style.display="";
		
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

  function updateFormElementsNew(index) {
	  
	  	if(index==1 || index ==2){
	  		document.getElementById("data_temp").style.visibility=""
	  		//document.addAccount.contractEndDate.type="";
	  		
	  	}
	  	else if(index==0){
	  		document.getElementById("data_temp").style.visibility="hidden"
			//document.addAccount.contractEndDate.type="hidden";
	  			  		
	  	}
	   
	    onLoad = 0;
	  }
  
  function checkForm(form) {
	    formTest = true;
	    message = "";
	    alertMessage = "";
	    
	 if (document.getElementById("div_da_vedere_se_in_modifica_solo_linea_attivita").style.display!= '')
	 {
    	if (document.getElementById("cessato")!= null && document.getElementById("cessato").value=='1' && document.getElementById("cessato").checked==true)
    	{
        	
    		if (form.contractEndDate != null && form.contractEndDate.value =='')
    		{
    			message += "- Controllare di aver selezionato La data di cessazione.\r\n";
    	     	formTest = false;
        	}
    	}
	 }
	    if (form.siteId.value && form.siteId.value=="-1"){
	    	 //alert(!isNaN(form.address2latitude.value));
	    		
	     	message += "- Controllare di aver selezionato un valore per il campo A.S.L.\r\n";
	     	formTest = false;
	     		
	 	 }   
	    
	    if (form.name){
	      if ((checkNullString(form.name.value))){
	        message += "- Impresa richiesta\r\n";
	        formTest = false;
	      }
	    }
	    
	    if (form.no_piva.checked==false){
	    if (checkNullString(form.partitaIva.value) && checkNullString(form.codiceFiscale.value)){
	         	message += "- Partita IVA richiesta\r\n";
	        	 formTest = false;
	     	  }
	       
	      
	  	if (form.partitaIva && form.partitaIva.value!=""){
	     	 //alert(!isNaN(form.address2latitude.value));
	     		if ((orgSelected == 1)  ){
	     			if (isNaN(form.partitaIva.value)){
	      			 message += "- Valore errato per il campo Partita IVA. Si prega di inserire solo cifre\r\n";
	      				 formTest = false;
	      			}		 
	     		}
	  	 }   
	    } else {
	    	if ( form.codiceFiscale.value=="" || form.codiceFiscale.value.length<16 ){
    			message += "- Codice Fiscale richiesto \r\n";
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

      if(form.tipoDest.value=='Autoveicolo'){
    	    if (form.nomeCorrentista){
    	        if ((orgSelected == 1) && (checkNullString(form.nomeCorrentista.value))){
    	          message += "- Targa Veicolo richiesta\r\n";
    	          formTest = false;
    	        }
    	      }
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

	        if (form.address1city.value=="-1" || form.address1city.value==""){
	          message += "- Comune sede legale richiesta\r\n";
	          formTest = false;
	        }


	        if (form.address1state.value == ""){
	            message += "- Provincia sede legale richiesta\r\n";
	            formTest = false;
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


	        	if (checkNullString(form.address2state.value)&&(form.address2state.disabled==false)){
		            message += "- Provincia sede operativa richiesto\r\n";
		            formTest = false;
		          }

	        	
	    	     	 

		    }
	        else
	        {
				if(form.tipoDest.value == "Autoveicolo")
				{

					if (checkNullString(form.address1latitude.value)){
				        message += "- Latitudine sede legale richiesta\r\n";
				        formTest = false;
				      }

					if (checkNullString(form.address1longitude.value)){
				        message += "- Longitudine sede legale richiesta\r\n";
				        formTest = false;
				      }

					if (checkNullString(form.address2latitude.value)){
				        message += "- Latitudine sede mobile richiesta\r\n";
				        formTest = false;
				      }

					if (checkNullString(form.address2longitude.value)){
				        message += "- Longitudine sede mobile richiesta\r\n";
				        formTest = false;
				      }

					
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
					 if((form.address2city.disabled == true)){
				      if ((obj.value == -1)){
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


				//Controllo latitudine e longitudine Attività Mobile
				if (form.address3latitude && form.address3latitude.value!=""){
			      	 //alert(!isNaN(form.address3latitude.value));
			      		if ((orgSelected == 1)  ){
			      			if (isNaN(form.address3latitude.value) ||  (form.address3latitude.value < 39.988475) || (form.address3latitude.value > 41.503754)){
			       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Attività mobile)\r\n";
			       				 formTest = false;
			       			}		 
			      		}
			   	 }   

			  	 if (form.address3longitude && form.address3longitude.value!=""){
			     	 //alert(!isNaN(form.address2longitude.value));
			     		if ((orgSelected == 1)  ){
			     			if (isNaN(form.address3longitude.value) ||  (form.address3longitude.value < 13.7563172) || (form.address3longitude.value > 15.8032837)){
			      			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Attività mobile)\r\n";
			      				 formTest = false;
			      			}		 
			     		}
			  	 }   

			  	 
					 

		     }
	        
	  }

		
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


<!--costruisci_combo_linea_attivita_onLoad();-->

<body onLoad="javascript:initializeClassification();caricaCodici();abilitaDistributoriCampi('<%=OrgDetails.getTipoDest() %>');abilita_codici_ateco_vuoti();nascondi_div_se_in_modifica_solo_linea_attivita('<%= request.getParameter("definisciLdA") %>')">
<form name="addAccount" action="Accounts.do?command=Insert&auto-populate=true<%= (request.getParameter("popup") != null?"&popup=true":"") %>" onSubmit="return doCheck(this);" method="post">
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
<a href="Accounts.do"><dhv:label name="">Stabilimenti 852</dhv:label></a> > 
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
	<%} else if (request.getParameter("return").equals("dashboard")) {%>
	<a href="Accounts.do?command=Dashboard">Cruscotto</a> >
	<%}%>
<%} else {%>
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Scheda Stabilimento 852</dhv:label></a> >
<%}%>
<dhv:label name="accountsc.modify">Modifica Stabilimento</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>


<dhv:container name="accounts" selected="details" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
      <input type="hidden" name="modified" value="<%= OrgDetails.getModified() %>">
<% if (request.getParameter("return") != null) {%>
      <input type="hidden" name="return" value="<%=request.getParameter("return")%>">
<%}%>
<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
<br />
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

<input type="hidden" name="id_la_principale_OLD" value="<%=((linea_attivita_principale!=null) ?linea_attivita_principale.getId() : ""+-1 )%>" >
<input type="hidden" name="id_la_1_OLD" value="<%=  ((linee_attivita_secondarie!= null && linee_attivita_secondarie.size()>0) ? linee_attivita_secondarie.get(0).getId(): "-1") %>" >
<input type="hidden" name="id_la_2_OLD" value="<%=  ((linee_attivita_secondarie!= null && linee_attivita_secondarie.size()>1) ? linee_attivita_secondarie.get(1).getId(): "-1") %>" >
<input type="hidden" name="id_la_3_OLD" value="<%=  ((linee_attivita_secondarie!= null && linee_attivita_secondarie.size()>2) ? linee_attivita_secondarie.get(2).getId(): "-1") %>" >
<input type="hidden" name="id_la_4_OLD" value="<%=  ((linee_attivita_secondarie!= null && linee_attivita_secondarie.size()>3) ? linee_attivita_secondarie.get(3).getId(): "-1") %>" >
<input type="hidden" name="id_la_5_OLD" value="<%=  ((linee_attivita_secondarie!= null && linee_attivita_secondarie.size()>4) ? linee_attivita_secondarie.get(4).getId(): "-1") %>" >
<input type="hidden" name="id_la_6_OLD" value="<%=  ((linee_attivita_secondarie!= null && linee_attivita_secondarie.size()>5) ? linee_attivita_secondarie.get(5).getId(): "-1") %>" >
<input type="hidden" name="id_la_7_OLD" value="<%=  ((linee_attivita_secondarie!= null && linee_attivita_secondarie.size()>6) ? linee_attivita_secondarie.get(6).getId(): "-1") %>" >
<input type="hidden" name="id_la_8_OLD" value="<%=  ((linee_attivita_secondarie!= null && linee_attivita_secondarie.size()>7) ? linee_attivita_secondarie.get(7).getId(): "-1") %>" >
<input type="hidden" name="id_la_9_OLD" value="<%=  ((linee_attivita_secondarie!= null && linee_attivita_secondarie.size()>8) ? linee_attivita_secondarie.get(8).getId(): "-1") %>" >
<input type="hidden" name="id_la_10_OLD" value="<%= ((linee_attivita_secondarie!= null && linee_attivita_secondarie.size()>9) ? linee_attivita_secondarie.get(9).getId(): "-1") %>" >


<div id="div_da_vedere_se_in_modifica_solo_linea_attivita" style="display: none">
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	  <tr>
		<th colspan="2">
		  <strong>Definizione codice ateco e linee di attività</strong>
		</th>     
	  </tr>
	  
	  <% if (request.getParameter("definisciLdA") != null) 
	  	 	if ( Boolean.parseBoolean(request.getParameter("definisciLdA")) == true && linea_attivita_principale != null  ) {%>
	
		  <tr class="containerBody">
			  <td class="formLabel" nowrap>
					<dhv:label name="">Codice Ateco/Linea di Attivita Principale</dhv:label>
			  </td>
			
			  <td>
				<input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="codiceFiscaleCorrentista" name="codiceFiscaleCorrentista" value="<%=((linea_attivita_principale != null)? toHtmlValue( linea_attivita_principale.getCodice_istat() ):"") %>" onchange="costruisci_rel_ateco_attivita('codiceFiscaleCorrentista', 'id_rel_principale' );"   ><font color="red">*</font>
			  <%-- &nbsp;[<a href="javascript:popLookupSelectorCustomImprese('codiceFiscaleCorrentista','alertText','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Seleziona</dhv:label></a>]--%>
	  &nbsp;[<a href="javascript:popLookupSelectorCustomImprese('codiceFiscaleCorrentista','alertText','id_attivita_masterlist','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Seleziona</dhv:label></a>]
	  
			  <br/>
			  
			  <%
			  
			  int val = -1 ;
			  if (linea_attivita_principale != null )
			  	val = linea_attivita_principale.getId_rel_ateco_attivita();


			  %>
			  
			  <%=((List_id_rel_principale != null)? List_id_rel_principale.getHtmlSelect("id_rel_principale", val ) : "" ) %>
			</td>
		  </tr>
		  
		  <tr class="containerBody">
			<td nowrap class="formLabel">
			  <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
			</td>
			<td>
				<input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="alertText" name="alertText" value="<%= toHtmlValue( linea_attivita_principale.getDescrizione_codice_istat() ) %>"> 
			</td>
		  </tr>
		  
		  <tr class="containerBody">
			<td class="formLabel" nowrap>
			  <dhv:label name="">Codici Ateco/Linea di Attività (Secondarie)</dhv:label>
			</td>
			
			<td>
					<b>Codice 1&nbsp;&nbsp;</b>
					<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice1" name="codice1" class="codiciatecolista"
						   onchange="costruisci_rel_ateco_attivita('codice1',  'id_rel_1' ); abilita_codici_ateco_vuoti();"	value="<%= ((linee_attivita_secondarie.size()>0) ? (toHtmlValue(linee_attivita_secondarie.get(0).getCodice_istat())) : ("")) %>" >
					[<a href="javascript:popLookupSelectorCustomImprese('codice1','cod1', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
						<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice2" name="codice2" class="codiciatecolista"
							onchange="costruisci_rel_ateco_attivita('codice2',  'id_rel_2' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>1) ? (toHtmlValue(linee_attivita_secondarie.get(1).getCodice_istat())) : ("")) %>" >
						[<a href="javascript:popLookupSelectorCustomImprese('codice2','cod2', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
						<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice3" name="codice3" class="codiciatecolista"
							onchange="costruisci_rel_ateco_attivita('codice3',  'id_rel_3' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>2) ? (toHtmlValue(linee_attivita_secondarie.get(2).getCodice_istat())) : ("")) %>" >
						[<a href="javascript:popLookupSelectorCustomImprese('codice3','cod3', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
						<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice4" name="codice4" class="codiciatecolista"
							onchange="costruisci_rel_ateco_attivita('codice4',  'id_rel_4' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>3) ? (toHtmlValue(linee_attivita_secondarie.get(3).getCodice_istat())) : ("")) %>" >
						[<a href="javascript:popLookupSelectorCustomImprese('codice4','cod4', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
						<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice5" name="codice5" class="codiciatecolista"
							onchange="costruisci_rel_ateco_attivita('codice5',  'id_rel_5' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>4) ? (toHtmlValue(linee_attivita_secondarie.get(4).getCodice_istat())) : ("")) %>" >
						[<a href="javascript:popLookupSelectorCustomImprese('codice5', 'cod5','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
						<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice6" name="codice6" class="codiciatecolista"
							onchange="costruisci_rel_ateco_attivita('codice6',  'id_rel_6' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>5) ? (toHtmlValue(linee_attivita_secondarie.get(5).getCodice_istat())) : ("")) %>" >
						[<a href="javascript:popLookupSelectorCustomImprese('codice6','cod6', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
						<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice7" name="codice7" class="codiciatecolista"
							onchange="costruisci_rel_ateco_attivita('codice7',  'id_rel_7' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>6) ? (toHtmlValue(linee_attivita_secondarie.get(6).getCodice_istat())) : ("")) %>" >
						[<a href="javascript:popLookupSelectorCustomImprese('codice7','cod7', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
						<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice8" name="codice8" class="codiciatecolista"
							onchange="costruisci_rel_ateco_attivita('codice8',  'id_rel_8' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>7) ? (toHtmlValue(linee_attivita_secondarie.get(7).getCodice_istat())) : ("")) %>" >
						[<a href="javascript:popLookupSelectorCustomImprese('codice8', 'cod8','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
						<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice9" name="codice9" class="codiciatecolista"
							onchange="costruisci_rel_ateco_attivita('codice9',  'id_rel_9' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>8) ? (toHtmlValue(linee_attivita_secondarie.get(8).getCodice_istat())) : ("")) %>" >
						[<a href="javascript:popLookupSelectorCustomImprese('codice9','cod9', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
						<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice10" name="codice10" class="codiciatecolista"
							onchange="costruisci_rel_ateco_attivita('codice10', 'id_rel_10'); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>9) ? (toHtmlValue(linee_attivita_secondarie.get(9).getCodice_istat())) : ("")) %>" >
						[<a href="javascript:popLookupSelectorCustomImprese('codice10', 'cod10','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
					 [ <a href="javascript:resetCodiciIstatSecondari()">Elimina codici istat secondari</a> ]
			  </td>
		  </tr>
		<% } %>
	</table>
</div>

<div id="div_da_non_vedere_se_in_modifica_solo_linea_attivita" style="display: none" >

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_modify.ModifyPrimaryInformation">Modify Primary Information</dhv:label></strong>
    </th>     
  </tr>
	<dhv:include name="accounts-sites" none="true">
	  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
	  <tr class="containerBody">
	    <td nowrap class="formLabel">
	      <dhv:label name="accounts.site">Site</dhv:label>
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
	<input type="hidden" name="voltura" value="<%=OrgDetails.getVoltura() %>">
	
	
	
	
	
	
	  
	    <dhv:include name="accounts-name" none="true">
	      <tr class="containerBody">
	        <td nowrap class="formLabel" name="orgname1" id="orgname1">
	          <dhv:label name="">Impresa</dhv:label>
	        </td>
	        <td>
	        
	          <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" maxlength="80" name="name" value="<%= toHtml(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
	       </td>
	      </tr>
	    </dhv:include>
	        
	  
					  
	  
	  <dhv:include name="accounts-number" none="true">
	    <tr class="containerBody">
	      <td nowrap class="formLabel">
	        <dhv:label name="organization.accountNumber">Account Number</dhv:label>
	      </td>
	      <td>
	        <%= toHtmlValue(OrgDetails.getAccountNumber()) %>
	              <input type="hidden" size="30" maxlength="30" name="accountNumber" value="<%= toHtmlValue(OrgDetails.getAccountNumber()) %>">
	      </td>
	    </tr>
	  </dhv:include>
	  
	  
	  <dhv:include name="accounts-number" none="true">
	    <tr class="containerBody">
	      <td nowrap class="formLabel">
	        <dhv:label name="organization.codice_impresa_interno">Account Number</dhv:label>
	      </td>
	      <td>
	        <%= toHtmlValue(OrgDetails.getCodiceImpresaInterno()) %>
	              <input type="hidden" size="30" maxlength="30" name="codiceImpresaInterno" value="<%= toHtmlValue(OrgDetails.getCodiceImpresaInterno()) %>">
	        
	      </td>
	    </tr>
	  </dhv:include>
	  
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
	      <input type="text" size="20" maxlength="11" id = "partitaIva" name="partitaIva" 	value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>" >
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
      <input type="text" size="20" maxlength="16" id="codiceFiscale" name="codiceFiscale" value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>">
      <font id="no_cf" style="display:none" color="red">* (INSERIRE CODICE FISCALE)</font>    
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
  
 <% if (request.getParameter("definisciLdA") == null) {%>
     	
  <tr class="containerBody">
	  <td class="formLabel" nowrap>
       		<dhv:label name="">Codice Ateco/Linea di Attivita Principale</dhv:label>
	  </td>
	
	  <td>
<%-- 	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="codiceFiscaleCorrentista" name="codiceFiscaleCorrentista" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleCorrentista()) %>"><font color="red">*</font>	--%>
	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="codiceFiscaleCorrentista" name="codiceFiscaleCorrentista" value="<%=(linea_attivita_principale!=null) ? toHtmlValue( linea_attivita_principale.getCodice_istat() ) : "" %>" onchange="costruisci_rel_ateco_attivita('codiceFiscaleCorrentista', 'id_rel_principale' );"   ><font color="red">*</font>
	  
	  
	  <%-- &nbsp;[<a href="javascript:popLookupSelectorCustomImprese('codiceFiscaleCorrentista','alertText','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Seleziona</dhv:label></a>]--%>
	  &nbsp;[<a href="javascript:popLookupSelectorCustomImprese('codiceFiscaleCorrentista','alertText','id_attivita_masterlist','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Seleziona</dhv:label></a>]
	  	  
	  
	  <%
	  int v = -1;
	  
	  
	  if (linea_attivita_principale!=null)
	  {
		 v = linea_attivita_principale.getId_rel_ateco_attivita();
	  }
	  
	  
	  
	  %>
	  
	  <br/><%= List_id_rel_principale.getHtmlSelect("id_rel_principale", v ) %>
	  
	</td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
    </td>
    <td>
<%--       <input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="alertText" name="alertText" value="<%= toHtmlValue(OrgDetails.getAlertText()) %>"> --%>
		   <input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="alertText" name="alertText" value="<%=(linea_attivita_principale!=null) ? toHtmlValue( linea_attivita_principale.getDescrizione_codice_istat() ) : "" %>"> 
    </td>
  </tr>

 
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
      		<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice1" name="codice1" class="codiciatecolista"
      			   onchange="costruisci_rel_ateco_attivita('codice1',  'id_rel_1' ); abilita_codici_ateco_vuoti();"	value="<%= ((linee_attivita_secondarie.size()>0) ? (toHtmlValue(linee_attivita_secondarie.get(0).getCodice_istat())) : ("")) %>" >
      		[<a href="javascript:popLookupSelectorCustomImprese('codice1','cod1', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod1" name="cod1" value="<%= ((linee_attivita_secondarie.size()>0) ? (toHtmlValue(linee_attivita_secondarie.get(0).getDescrizione_codice_istat())) : ("")) %>" >
      		<br/><%
				if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>0)
					out.println( (List_id_rel_1.getHtmlSelect("id_rel_1" , linee_attivita_secondarie.get(0).getId_rel_ateco_attivita()  ) )  );
				else
					out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_1" , -1 ) ) );
					
			%><br/>
			<br></br>
        	
      		 <div id="div_codice1" style="display: none">
      		 	<b>Codice 2&nbsp;&nbsp;</b>
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice2" name="codice2" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice2',  'id_rel_2' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>1) ? (toHtmlValue(linee_attivita_secondarie.get(1).getCodice_istat())) : ("")) %>" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice2','cod2', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod2" name="cod2" value="<%= ((linee_attivita_secondarie.size()>1) ? (toHtmlValue(linee_attivita_secondarie.get(1).getDescrizione_codice_istat())) : ("")) %>" >
      		 	<br/><%
					if (linee_attivita_secondarie!=null && linee_attivita_secondarie.size()>1)
						out.println(List_id_rel_2.getHtmlSelect("id_rel_2", linee_attivita_secondarie.get(1).getId_rel_ateco_attivita() ) );
					else
						out.println(lookup_vuota_linea_attivita.getHtmlSelect("id_rel_2", -1 ));
						
				%><br/>
				<br></br>
      		 </div>
      		 
      		 <div id="div_codice2" style="display: none">
      		 	<b>Codice 3&nbsp;&nbsp;</b>
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice3" name="codice3" class="codiciatecolista"
      		    	onchange="costruisci_rel_ateco_attivita('codice3',  'id_rel_3' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>2) ? (toHtmlValue(linee_attivita_secondarie.get(2).getCodice_istat())) : ("")) %>" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice3','cod3', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod3" name="cod3" value="<%= ((linee_attivita_secondarie.size()>2) ? (toHtmlValue(linee_attivita_secondarie.get(2).getDescrizione_codice_istat())) : ("")) %>" >
      		    <br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>2)
						out.println( (List_id_rel_3.getHtmlSelect("id_rel_3" , linee_attivita_secondarie.get(2).getId_rel_ateco_attivita()  ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_3" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>
      		 
      		 <div id="div_codice3" style="display: none">
      		 	<b>Codice 4&nbsp;&nbsp;</b>
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice4" name="codice4" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice4',  'id_rel_4' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>3) ? (toHtmlValue(linee_attivita_secondarie.get(3).getCodice_istat())) : ("")) %>" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice4','cod4', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod4" name="cod4" value="<%= ((linee_attivita_secondarie.size()>3) ? (toHtmlValue(linee_attivita_secondarie.get(3).getDescrizione_codice_istat())) : ("")) %>" >
      		 	<br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>3)
						out.println( (List_id_rel_4.getHtmlSelect("id_rel_4" , linee_attivita_secondarie.get(3).getId_rel_ateco_attivita()  ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_4" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice4" style="display: none">
      		 	<b>Codice 5&nbsp;&nbsp;</b>
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice5" name="codice5" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice5',  'id_rel_5' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>4) ? (toHtmlValue(linee_attivita_secondarie.get(4).getCodice_istat())) : ("")) %>" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice5', 'cod5','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod5" name="cod5" value="<%= ((linee_attivita_secondarie.size()>4) ? (toHtmlValue(linee_attivita_secondarie.get(4).getDescrizione_codice_istat())) : ("")) %>" >
      		    <br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>4)
						out.println( (List_id_rel_5.getHtmlSelect("id_rel_5" , linee_attivita_secondarie.get(4).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_5" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice5" style="display: none">
      		 	<b>Codice 6&nbsp;&nbsp;</b>
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice6" name="codice6" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice6',  'id_rel_6' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>5) ? (toHtmlValue(linee_attivita_secondarie.get(5).getCodice_istat())) : ("")) %>" >
      		  	[<a href="javascript:popLookupSelectorCustomImprese('codice6','cod6', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod6" name="cod6" value="<%= ((linee_attivita_secondarie.size()>5) ? (toHtmlValue(linee_attivita_secondarie.get(5).getDescrizione_codice_istat())) : ("")) %>" >
      		  	<br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>5)
						out.println( (List_id_rel_6.getHtmlSelect("id_rel_6" , linee_attivita_secondarie.get(5).getId_rel_ateco_attivita()  ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_6" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice6" style="display: none">
      		 	<b>Codice 7&nbsp;&nbsp;</b>
      		  	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice7" name="codice7" class="codiciatecolista"
      		  		onchange="costruisci_rel_ateco_attivita('codice7',  'id_rel_7' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>6) ? (toHtmlValue(linee_attivita_secondarie.get(6).getCodice_istat())) : ("")) %>" >
      		  	[<a href="javascript:popLookupSelectorCustomImprese('codice7','cod7', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod7" name="cod7" value="<%= ((linee_attivita_secondarie.size()>6) ? (toHtmlValue(linee_attivita_secondarie.get(6).getDescrizione_codice_istat())) : ("")) %>" >
      		  	<br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>6)
						out.println( (List_id_rel_7.getHtmlSelect("id_rel_7" , linee_attivita_secondarie.get(6).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_7" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>	

      		 <div id="div_codice7" style="display: none">
      		 	<b>Codice 8&nbsp;&nbsp;</b>
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice8" name="codice8" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice8',  'id_rel_8' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>7) ? (toHtmlValue(linee_attivita_secondarie.get(7).getCodice_istat())) : ("")) %>" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice8', 'cod8','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod8" name="cod8" value="<%= ((linee_attivita_secondarie.size()>7) ? (toHtmlValue(linee_attivita_secondarie.get(7).getDescrizione_codice_istat())) : ("")) %>" >
      		 	<br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>7)
						out.println( (List_id_rel_8.getHtmlSelect("id_rel_8" , linee_attivita_secondarie.get(7).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_8" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice8" style="display: none">
      		 	<b>Codice 9&nbsp;&nbsp;</b>
         	    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice9" name="codice9" class="codiciatecolista"
         	    	onchange="costruisci_rel_ateco_attivita('codice9',  'id_rel_9' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>8) ? (toHtmlValue(linee_attivita_secondarie.get(8).getCodice_istat())) : ("")) %>" >
         	    [<a href="javascript:popLookupSelectorCustomImprese('codice9','cod9', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
         	    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod9" name="cod9" value="<%= ((linee_attivita_secondarie.size()>8) ? (toHtmlValue(linee_attivita_secondarie.get(8).getDescrizione_codice_istat())) : ("")) %>" >
         	    <br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>8)
						out.println( (List_id_rel_9.getHtmlSelect("id_rel_9" , linee_attivita_secondarie.get(8).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_9" , -1 ) ) );
						
				%><br/>
				<br></br>	
         	 </div>
      		 
			<div id="div_codice9" style="display: none">
      		 	<b>Codice 10</b>
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice10" name="codice10" class="codiciatecolista"
      		    	onchange="costruisci_rel_ateco_attivita('codice10', 'id_rel_10'); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie.size()>9) ? (toHtmlValue(linee_attivita_secondarie.get(9).getCodice_istat())) : ("")) %>" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice10', 'cod10','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod10" name="cod10" value="<%= ((linee_attivita_secondarie.size()>9) ? (toHtmlValue(linee_attivita_secondarie.get(9).getDescrizione_codice_istat())) : ("")) %>" >
      		    <br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>9)
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
  <% } %>
  
 
  
   <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Domicilio Digitale</dhv:label>
    </td>
    <td>
      <input type="text" size="20" maxlength="" name="domicilioDigitale" value="<%= toHtmlValue(OrgDetails.getDomicilioDigitale()) %>">    
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
  
  
  <%if(hasText(OrgDetails.getTipoDest())) {%>
   <tr class="containerBody"><td nowrap class="formLabel">
      <dhv:label name="">Attività</dhv:label>
    </td>
    <td>
        <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Autoveicolo")%>">
        Mobile
        </dhv:evaluate>
       
        <dhv:evaluate if="<%= OrgDetails.getTipoDest().equals("Es. Commerciale")%>">
      Fissa
        </dhv:evaluate>
       <%--= toHtmlValue(OrgDetails.getTipoDest()) --%>&nbsp;
       <input type="hidden" name="tipoDest" value="<%= toHtmlValue(OrgDetails.getTipoDest()) %>">
     
    </td></tr>
    <%}
  else
  {
	  %>
	  <input type = "hidden" name = "tipoDest" value = "Es. Commerciale">
	  <%
	  
  }
  %>


    <dhv:include name="organization.source" none="true">
	   <tr class="containerBody">
	      <td nowrap class="formLabel">
	        <dhv:label name="contact.source">Source</dhv:label>
	      </td>
	      
	      <td>
			   <table border=0>
			    <tr>
			    <td>
			       <%	SourceList.setJsEvent("onChange=\"javascript:selectCarattere('source');\"");
			        %>
			       <%= SourceList.getHtmlSelect("source",OrgDetails.getSource()) %>
	      		</td>
	 
			    <dhv:evaluate if="<%= OrgDetails.getSource()!= 1 %>">
			       	<td style="visibility: hidden;" id="data1">
			        		Dal
			        	</td>
			        	<td style="visibility: hidden;" id="data3">
			        	
			        	<input readonly type="text" id="dateI" name="dateI" size="10"  value=""/>
		<a href="#" onClick="cal19.select(document.forms[0].dateI,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
			        	
			       	</td>
			       
			       	<td style="visibility: hidden;" id="data2">
			           		Al
		           	</td>

	            	<td style="visibility: hidden;" id="data4">
	            	
	            				        	<input readonly type="text" id="dateF" name="dateF" size="10" 
		value=""/>
		<a href="#" onClick="cal19.select(document.forms[0].dateF,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
			           		
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
			      
			      	        	<input readonly type="text" id="dateI" name="dateI" size="10" value=""/>
		<a href="#" onClick="cal19.select(document.forms[0].dateI,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
			      
			          	</td>
			       	 	
			           	<td style="visibility: visible;" id="data2">
			           		Al
			           	</td>
			           	
			            <td style="visibility: visible;" id="data4">

<input readonly type="text" id="dateF" name="dateF" size="10" 
		value=""/>
		<a href="#" onClick="cal19.select(document.forms[0].dateF,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
			           		
			           	</td>
			           	
			           	<td style="visibility: visible;" id="cessazione">
			           		<input type="checkbox" name="cessazione" value ="true" <%= OrgDetails.getCessazione()?"checked":"" %> /> <dhv:label name="accounts.Assetsf">Cessazione Automatica</dhv:label>
			           	</td>
		          </dhv:evaluate>
	    		</tr>
	    		</table>
	    	</td>
	 </tr>
  </dhv:include>    
  

  
  
   <dhv:include name="organization.alert" none="true">
    <tr class="containerBody" style="display: none">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label>
      </td>
      <td>
      
      <input readonly type="text" id="alertDate" name="alertDate" size="10" 
		value=""/>
		<a href="#" onClick="cal19.select(document.forms[0].alertDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
			        
      </td>
    </tr>
  </dhv:include>
        
  
  <dhv:include name="organization.date1" none="true">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Presentazione D.I.A./Inizio Attività</dhv:label>
      </td>
      <td>
      <%if(request.getAttribute("Volture")!=null && ((String)request.getAttribute("Volture")).equals("si") &&  OrgDetails.getDataPresentazione()!=null )
       {
    	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	  String data_presentazione = "" ;
    	  if(OrgDetails.getDataPresentazione()!=null)
    	  {
    		  data_presentazione = sdf.format(new Date(OrgDetails.getDataPresentazione().getTime()));
    	  }
    	  %>
    	      		  
      <%}
      else
      {%>
     	
        <%= showAttribute(request, "date1Error") %><font color="red">*</font>
        <input type = "hidden" name = "aggiorna_voltura" value = "si">
      <%} %>
        
        <input readonly type="text" id="dataPresentazione" name="dataPresentazione" size="10" 
		value="<%=toDateasString( OrgDetails.getDataPresentazione())%>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataPresentazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
        
      </td>
    </tr>
  </dhv:include>


  <%if(OrgDetails.getAccountNumber()==null || OrgDetails.getAccountNumber().equals("")){ %>
  <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.stage">Stage</dhv:label>
      </td>
      <td>
        <%= StageList.getHtmlSelect("stageId",OrgDetails.getStageId()) %>
        <font color = "red">*</font>
      </td>
    </tr> 
    <%} %>  
  <tr class="containerBody">
	<td nowrap class="formLabel">
      	<dhv:label name="">Stato Stabilimento</dhv:label>
	</td>
	<td>
      <input type="radio" id="attivo" name="cessato" value="0" onClick="javascript:updateFormElementsNew(0);" <%=((OrgDetails.getCessato()==0) ? ("checked = checked") : (""))%>>
      In Attività
      <input type="radio" id="cessato" name="cessato" value="1" onClick="javascript:updateFormElementsNew(1);" <%=((OrgDetails.getCessato()==1) ? ("checked = checked") : (""))%>>
      Cessato
      <input type="radio" id="sospeso" name="cessato" value="2" onClick="javascript:updateFormElementsNew(2);" <%=((OrgDetails.getCessato()==2) ? ("checked = checked") : (""))%>>
      Sospeso 
      <input type="hidden" name="orgType" value="" />

      <input type="hidden" name="check" />
      
  <div id="data_temp" style="visibility:hidden">
      in data 

      
      <input readonly type="text" id="contractEndDate" name="contractEndDate" size="10" 
		value="<%= (OrgDetails.getContractEndDate()==null)?(""):(getLongDate(OrgDetails.getContractEndDate()))%>"/>
		<a href="#" onClick="cal19.select(document.forms[0].contractEndDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      <%=((OrgDetails.getCessato()==1) ? (", di conseguenza  non più gestibile in modifica.") : (""))%>
  </div>
 </td> 
</tr>
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
		        Codice Fiscale
		      </td>
		      <td>
		        <input type="text" size="50" name="codiceFiscaleRappresentante" maxlength="16" value="<%= toHtml(OrgDetails.getCodiceFiscaleRappresentante()) %>">
		      </td>
		    </tr>
		  </dhv:include>
		  
		  <dhv:include name="" none="true">
		    <tr class="containerBody">
		      <td nowrap class="formLabel">
		        Nome
		      </td>
		      <td>
		        <input type="text" size="50" name="nomeRappresentante" maxlength="300" value="<%= toHtml(OrgDetails.getNomeRappresentante()) %>"><font color="red">*</font>
		      </td>
		    </tr>
		  </dhv:include> 
		  
		  <dhv:include name="" none="true">
		    <tr class="containerBody">
		      <td nowrap class="formLabel">
		        Cognome
		      </td>
		      <td>
		        <input type="text" size="50" name="cognomeRappresentante" maxlength="300" value="<%= toHtml(OrgDetails.getCognomeRappresentante()) %>"><font color="red">*</font>
		      </td>
		    </tr>
		  </dhv:include>
		  
		  <tr class="containerBody">
		      <td nowrap class="formLabel">
		        Data Nascita
		      </td>
		      <td>
		      
		     <input readonly type="text" id="dataNascitaRappresentante" name="dataNascitaRappresentante" size="10" 
			value="<%= ((OrgDetails.getDataNascitaRappresentante()!=null)?(toHtml(DateUtils.getDateAsString(OrgDetails.getDataNascitaRappresentante(),Locale.ITALY))):("")) %>"/>
			<a href="#" onClick="cal19.select(document.forms[0].dataNascitaRappresentante,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
	     	
	     	<%-- <input value="<%= ((OrgDetails.getDataNascitaRappresentante()!=null)?(toHtml(DateUtils.getDateAsString(OrgDetails.getDataNascitaRappresentante(),Locale.ITALY))):("")) %>" />&nbsp;<a href="javascript:popCalendar('addAccount','dataNascitaRappresentante','it','IT','Europe/Berlin');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>--%>
		    <font color='#006699'>&nbsp;</font>
		    </td>
		  </tr>
	  
		  <tr class="containerBody">
		    <td class="formLabel" nowrap>
		      <dhv:label name="">Comune di Nascita</dhv:label>
		    </td>
		    <td>
		      <input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtml(OrgDetails.getLuogoNascitaRappresentante())%>">
		    </td>
		  </tr>
		  
		  <tr>
		    <td class="formLabel" nowrap>
		      <dhv:label name="">Comune di residenza</dhv:label>
		    </td>
		    <td>
		      <input type="text" size="30" maxlength="50" name="city_legale_rapp" value="<%= toHtmlValue(OrgDetails.getCity_legale_rapp()) %>">
		    </td>
		  </tr>
		  
		  <tr>
		    <td class="formLabel" nowrap>
		      <dhv:label name="">Provincia</dhv:label>
		    </td>
		    <td>
		      <input type="text" size="30" maxlength="50" name="prov_legale_rapp" value="<%= toHtmlValue(OrgDetails.getProv_legale_rapp()) %>">
		    </td>
		  </tr>
		  
		  <tr>
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
		        <dhv:label name="">Email</dhv:label>
		      </td>
		      <td>
		        <input type="text" size="50" name="emailRappresentante" maxlength="300" value="<%= toHtml(OrgDetails.getEmailRappresentante()) %>">
		      </td>
		    </tr>
		  </dhv:include>
	  
		  <dhv:include name="" none="true">
		    <tr class="containerBody">
		      <td nowrap class="formLabel">
		        <dhv:label name="">Telefono</dhv:label>
		      </td>
		      <td>
		        <input type="text" size="50" name="telefonoRappresentante" maxlength="300" value="<%= toHtml(OrgDetails.getTelefonoRappresentante()) %>">
		      </td>
		    </tr>
		  </dhv:include>
	  
		  <dhv:include name="" none="true">
		    <tr class="containerBody">
		      <td nowrap class="formLabel">
		        <dhv:label name="">Fax</dhv:label>
		      </td>
		      <td>
		        <input type="text" size="50" name="fax" maxlength="300" value="<%= toHtml(OrgDetails.getFax()) %>">
		      </td>
		    </tr>
		  </dhv:include>
	</table>
<br>
<%
  boolean noneSelected = false;
  int numeroLocaiFunz=0; 
  ArrayList<OrganizationAddress> locali_funz_collegati = new ArrayList<OrganizationAddress>();
  
	int s=1;	
	boolean primo=false;
	int flag=0;
	
	  int acount = 0;
	  int locali=0;
	
	  Iterator anumber = OrgDetails.getAddressList().iterator();
	  while (anumber.hasNext()) {
	     OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
	     
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
		    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede legale</dhv:label></strong>
		  </dhv:evaluate>
		  <dhv:evaluate if="<%= thisAddress.getType() == 5 %>">
		    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede operativa</dhv:label></strong>
		  </dhv:evaluate>  
		 
		  <dhv:evaluate if="<%= thisAddress.getType() == 7%>">
		    <strong><dhv:label name="accounts.accounts_add.Addressess">Sede attività mobile</dhv:label></strong>
		  </dhv:evaluate> 
	      <%-- %><strong><dhv:label name="requestor.requestor_add.Addressess"><%= toHtml(thisAddress.getTypeName())%></dhv:label></strong>--%>
	     
	      
	    </th>
	    </tr>
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
	       <input type="text" name="address1city" id="address1city" value="<%=thisAddress.getCity() %>" size="70">
	      
	    <%}else if((thisAddress.getType()==5)&&(OrgDetails.getTipoDest().equals("Es. Commerciale"))){ %>
	     <%--input type = "text"  name="address2city" id="prov12" value = "<%=thisAddress.getCity() %>"--%>
	     
	     <select  name="address2city" id="prov12">
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
		
	    <%}else{ %>
	    
	  	<input type = "text"  name="address2city" id="prov12" value = "<%=thisAddress.getCity() %>">
		<%} %>
		
		<font color = "red">*</font>
	
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
	            <input type="text" size="10" name="address1state" maxlength="80" value=""> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate>
	   <%
	   			}
			    else
			    {
			    	
		%>
	        <input type="text" size="10" name="address1state" maxlength="80" value="<%=thisAddress.getState()%>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"><font color= "red">*</font></dhv:evaluate>
	   <%
	   			}
			}
		    else if(thisAddress.getType() == 5){ 
		    %>
		      <input type="text"  size="28" name="address2state"  value="<%=thisAddress.getState()%>" maxlength="80"><font color="red">*</font>
		    <%}else{ %>
		      <input type="text"  size="28" name="address<%= acount %>state"  value="<%=thisAddress.getState()%>" maxlength="80"><font color="red">*</font>
		     
		      <%} %>
	    </td>
	  </tr>
	 
	
	  
	  <tr class="containerBody">
	    <td class="formLabel" nowrap><dhv:label name="accounts.address.latitude">Latitude</dhv:label></td>

	    <td><input type="text" <%if(thisAddress.getType()==1 && OrgDetails.getTipoDest().equals("Es. Commerciale")){ %>readonly="readonly"<%} %> name="address<%= acount %>latitude" size="30" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLatitude()) : "") %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate>

	   
	    	<% if(!OrgDetails.getTipoDest().equals("Es. Commerciale")) { %>  
	    		<font color = "red">*</font>
	    	<% } %>	
	   </td>

	  </tr>
	  <tr class="containerBody">
	    <td class="formLabel" nowrap><dhv:label name="accounts.address.longitude">Longitude</dhv:label></td>

	    <td><input type="text" name="address<%= acount %>longitude" <%if(thisAddress.getType()==1 && OrgDetails.getTipoDest().equals("Es. Commerciale")){ %>readonly="readonly"<%} %> size="30" value="<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? String.valueOf(thisAddress.getLongitude()) : "") %>"> <dhv:evaluate if="<%= thisAddress.getType() != 6 %>"></dhv:evaluate>

	    
	    	<% if(!OrgDetails.getTipoDest().equals("Es. Commerciale")) { %>  
	    		<font color = "red">*</font>
	    	<% } %>
	    </td>

	  </tr>
	 
	 <% if( acount == 1 && !OrgDetails.getTipoDest().equals("Es. Commerciale")) { %>
	   <tr style="display: block">
	    	<td colspan="2">
	    		<input id="coord1button" type="button" value="Calcola Coordinate"
				onclick="javascript:showCoordinate(document.getElementById('address1line1').value, document.forms['addAccount'].address1city.value,document.forms['addAccount'].address1state.value, document.forms['addAccount'].address1zip.value, document.forms['addAccount'].address1latitude, document.forms['addAccount'].address1longitude);" />
	     	</td>
	    </tr>
	  <% } %>
	 
	 <% if(acount ==2) { %>  
	    	<tr style="display: block">
	    		<td colspan="2">
	    		<input id="coord1button" type="button" value="Calcola Coordinate"
				onclick="javascript:showCoordinate(document.getElementById('address2line1').value, document.forms['addAccount'].address2city.value,document.forms['addAccount'].address2state.value, document.forms['addAccount'].address2zip.value, document.forms['addAccount'].address2latitude, document.forms['addAccount'].address2longitude);" />
	     	</td>
	    </tr>
	    <%} 
	 }}%>

	 
	</table>
	<br>
	<%if (!OrgDetails.getTipoDest().equals("Distrubutori"))
   {%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  
 
   <input type="hidden" id = "elementi" name="elementi" value="<%=1 %>">
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
 			{%>
         <input type="text" size="40" name="address4line1" maxlength="80" value="<%= toHtmlValue(address.getStreetAddressLine1()) %>">
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
    	<input type="text" id="address4latitude<%=i %>" name="address4latitude<%=i %>" size="30" value="<%=address.getLatitude() %>" > 
    	
   </td>
  </tr>
  <tr class="containerBody" id = "locale1_long">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" id="address4longitude<%=i %>" name="address4longitude<%=i %>" size="30" value="<%=address.getLongitude() %>"></td>
  </tr></table>
    </td>
    <%}} %>
    </tr>
    
  <tr>
  
  <td><input type="button" onClick="clonaLocaleFunzionalmenteCollegato()" value="Aggiungi altro Indirizzo" ></td>
</tr>
</table>
<%} %>


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
  
</div>
  
  <input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';" />
<% if(request.getParameter("return") != null && "list".equals(request.getParameter("return"))) {%>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=Search';this.form.dosubmit.value='false';" />
<% } else if (isPopup(request)) { %>
  <input type="button" value="Annulla" onclick="javascript:window.close();" />
<% } else { %>
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>';this.form.dosubmit.value='false';" />
<% } %>
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="statusId" value="<%=OrgDetails.getStatusId()%>">
  <input type="hidden" name="trashedDate" value="<%=OrgDetails.getTrashedDate()%>">
<% if (request.getParameter("actionplan") != null) { %>
<input type="hidden" name="actionplan" value="<%=request.getParameter("actionplan")%>">
<%}%>
</dhv:container>
</form>