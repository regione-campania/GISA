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
<%@ include file="../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.requestor.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>


<%@page import="org.aspcfs.modules.lineeattivita.base.LineeAttivita"%><jsp:useBean id="rel_ateco_linea_attivita_List" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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

<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.requestor.base.Organization" scope="request"/>
<jsp:useBean id="StateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="AccountSizeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CountryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SalutationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CodiceIstatList" class="org.aspcfs.utils.web.CustomLookupList" scope="request"/>
<jsp:useBean id="Address" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressSedeOperativa" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressSedeMobile" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLocale1" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLocale2" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLocale3" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/setSalutation.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_dia_add.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script language="JavaScript" TYPE="text/javascript"></script>
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
<script type="text/javascript">
function resetFormElements() {
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
      <dhv:include name="accounts-size" none="true">
        elm6 = document.getElementById("accountSize1");
      </dhv:include>
      <dhv:include name="accounts-title" none="true">
        elm7 = document.getElementById("listSalutation1");
      </dhv:include>
      <dhv:include name="accounts-firstname" none="true">
        elm1.style.color = "#000000";
        document.addAccount.nameFirst.style.background = "#ffffff";
        document.addAccount.nameFirst.disabled = false;
      </dhv:include>
      <dhv:include name="accounts-middlename" none="true">
        elm2.style.color = "#000000";
        document.addAccount.nameMiddle.style.background = "#ffffff";
        document.addAccount.nameMiddle.disabled = false;
      </dhv:include>
      <dhv:include name="accounts-lastname" none="true">
        elm3.style.color = "#000000";
        document.addAccount.nameLast.style.background = "#ffffff";
        document.addAccount.nameLast.disabled = false;
      </dhv:include>
      elm4.style.color = "#000000";
      document.addAccount.name.style.background = "#ffffff";
        document.addAccount.name.disabled = false;
      if (elm5) {
        elm5.style.color = "#000000";
        document.addAccount.ticker.style.background = "#ffffff";
        document.addAccount.ticker.disabled = false;
      }
      <dhv:include name="accounts-size" none="true">
        elm6.style.color = "#000000";
        document.addAccount.accountSize.style.background = "#ffffff";
        document.addAccount.accountSize.disabled = false;
      </dhv:include>
      /*
      <dhv:include name="accounts-title" none="true">
        elm7.style.color = "#000000";
        document.addAccount.listSalutation.style.background = "#ffffff";
        document.addAccount.listSalutation.disabled = false;
      </dhv:include>
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
      <dhv:include name="accounts-size" none="true">
        elm6 = document.getElementById("accountSize1");
      </dhv:include>
      <dhv:include name="accounts-title" none="true">
        elm7 = document.getElementById("listSalutation1");
      </dhv:include>
      if (index == 1) {
        indSelected = 1;
        orgSelected = 0;
        resetFormElements();
        elm4.style.color="#cccccc";
        document.addAccount.name.style.background = "#cccccc";
        document.addAccount.name.value = "";
        document.addAccount.name.disabled = true;
        if (elm5) {
          elm5.style.color="#cccccc";
          document.addAccount.ticker.style.background = "#cccccc";
          document.addAccount.ticker.value = "";
          document.addAccount.ticker.disabled = true;
        }
        <dhv:include name="accounts-size" none="true">
          elm6.style.color = "#cccccc";
          document.addAccount.accountSize.style.background = "#cccccc";
          document.addAccount.accountSize.value = -1;
          document.addAccount.accountSize.disabled = true;
        </dhv:include>
      } else {
        indSelected = 0;
        orgSelected = 1;
        resetFormElements();
        <dhv:include name="accounts-firstname" none="true">
          elm1.style.color = "#cccccc";
          document.addAccount.nameFirst.style.background = "#cccccc";
          document.addAccount.nameFirst.value = "";
          document.addAccount.nameFirst.disabled = true;
        </dhv:include>
        <dhv:include name="accounts-middlename" none="true">
          elm2.style.color = "#cccccc";
          document.addAccount.nameMiddle.style.background = "#cccccc";
          document.addAccount.nameMiddle.value = "";
          document.addAccount.nameMiddle.disabled = true;
        </dhv:include>
        <dhv:include name="accounts-lastname" none="true">
          elm3.style.color = "#cccccc";
          document.addAccount.nameLast.style.background = "#cccccc";
          document.addAccount.nameLast.value = "";
          document.addAccount.nameLast.disabled = true;
        </dhv:include>
        /*
        <dhv:include name="accounts-title" none="true">
          elm7.style.color = "#cccccc";
          document.addAccount.listSalutation.style.background = "#cccccc";
          document.addAccount.listSalutation.value = -1;
          document.addAccount.listSalutation.disabled = true;
        </dhv:include>
        */
      }
    }
            
        document.getElementById("codice1").value = "";
        document.getElementById("codice2").value = "";
        document.getElementById("codice3").value = "";
        document.getElementById("codice4").value = "";
        document.getElementById("codice5").value = "";
        document.getElementById("codice6").value = "";
        document.getElementById("codice7").value = "";
        document.getElementById("codice8").value = "";
        document.getElementById("codice9").value = "";
        document.getElementById("codice10").value = "";
    
   
    onLoad = 0;
  }

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
		
   
</script>

<script>
function gestisciDivProvenienza(scelta){
	var divProv = document.getElementById("divProvenienza");
	var divListEst = document.getElementById("divListaEstera");
	if (scelta==1)
		divProv.style.display="block";
	else{
		document.getElementById("provenienzaIT").checked="checked";
		divProv.style.display="none";
		divListEst.style.display="none";
		gestisciProvenienzaEstera(0);
		}
}

function gestisciProvenienzaEstera(scelta){
	var pi = document.getElementById("partitaIva");
	var divListEst = document.getElementById("divListaEstera");
	if (scelta==1){
		pi.maxLength="99";
		divListEst.style.display="block";
		}
	else{
		pi.maxLength="11";
		divListEst.style.display="none";
	}
}

function inizializzaProvenienzaEstera(tipoAtt){
	if (tipoAtt == 'Es. Commerciale')
		gestisciDivProvenienza(1);
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
	org.aspcfs.utils.web.LookupList  lookup_vuota_linea_attivita = new org.aspcfs.utils.web.LookupList ();
	lookup_vuota_linea_attivita.addItem(-1, "-- Selezionare prima il codice Ateco --" );
%>

  
  <%if(OrgDetails.getTipoDest()!=null && !OrgDetails.getTipoDest().equals("") ) {
  
	  if(OrgDetails.getTipoDest().equals("Es. Commerciale")){
		 %> <body onLoad="inizializzaProvenienzaEstera('<%=OrgDetails.getTipoDest() %>'); javascript:document.addAccount.name.focus();updateFormElementsNew(0);updateFormElements(0);caricaCodici();abilita_codici_ateco_vuoti(); ">
		<%   
	  }else{
		  
		  if(OrgDetails.getTipoDest().equals("Autoveicolo")){
				 %> <body onLoad="javascript:document.addAccount.name.focus();updateFormElementsNew(1);updateFormElements(0);caricaCodici();abilita_codici_ateco_vuoti(); document.getElementById('coord2button').disabled = false">
				<%   
			  }else{
				  
				  if(OrgDetails.getTipoDest().equals("Distributori")){
				  
					  %> <body onLoad="javascript:document.addAccount.name.focus();abilitaDistributoriCampi();updateFormElements(0);caricaCodici();abilita_codici_ateco_vuoti(); ">
						<% 
				  }
			  }  
	  }
  
  }else{
  %>
  <body onLoad="inizializzaProvenienzaEstera('Es. Commerciale');javascript:document.addAccount.name.focus();updateFormElementsNew(0);updateFormElements(0);caricaCodici();abilita_codici_ateco_vuoti();">
  <%} %>

<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
  
    <%if(OrgDetails.getTipoDest()!=null && !OrgDetails.getTipoDest().equals("") ) {
  
	  if(OrgDetails.getTipoDest().equals("Es. Commerciale")){
		 %> <body onLoad="javascript:document.addAccount.name.focus();updateFormElementsNew(0);updateFormElements(0);resetCodice();abilita_codici_ateco_vuoti() ">
		<%   
	  }else{
		  
		  if(OrgDetails.getTipoDest().equals("Autoveicolo")){
				 %> <body onLoad="javascript:document.addAccount.name.focus();updateFormElementsNew(1);updateFormElements(0);caricaCodici();abilita_codici_ateco_vuoti(); ">
				<%   
			  }else{
				  
				  if(OrgDetails.getTipoDest().equals("Distributori")){
				  
					  %> <body onLoad="javascript:document.addAccount.name.focus();abilitaDistributoriCampi();updateFormElements(0);caricaCodici();abilita_codici_ateco_vuoti(); ">
						<% 
				  }
			  }  
		  
	  }
  
  }else{
  %>
  
  
  <body onLoad="javascript:document.addAccount.name.focus();updateFormElementsNew(0);updateFormElements(0);caricaCodici() ;abilita_codici_ateco_vuoti(); ">
  <%} %>
  
</dhv:evaluate>
<form id = "addAccount" name="addAccount" action="Requestor.do?command=Insert&auto-populate=true" method="post" onSubmit="this.saveandclone.value='';return verificaEsistenzaDIA()"   >
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
  
  


<dhv:evaluate if="<%= !popUp %>">  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Requestor.do"><dhv:label name="requestor.requestor">D.I.A.</dhv:label></a> >
<dhv:label name="requestor.add">Richiedi D.I.A.</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>

<input type="submit" value="Inserisci" name="Save">
<dhv:permission name="requestor-requestor-add"><input type="submit" value="Inserisci e Clona" onClick="this.form.saveandclone.value='true';" /></dhv:permission>
<dhv:evaluate if="<%= !popUp %>">
  <input type=submit value="Annulla" onClick="javascript:this.form.action='Requestor.do';this.form.dosubmit.value='false';this.form.cancel.value='true'">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<br /><br />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="requestor.requestor_add.AddNewRequestor">Richiedi D.I.A.</dhv:label></strong>
    </th>
  </tr>  


  <dhv:include name="requestor-sites" none="true">
  <dhv:evaluate if="<%= SiteList.size() > 1 %>">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="requestor.site">Site</dhv:label>
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
  </dhv:include>

  
  
  
 <dhv:include name="accounts-classification" none="true">
  <tr style="display: none" >
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Classification">Tipo DIA</dhv:label>
    </td>
    <td>
      <input type="radio" name="form_type" id="DIA Differita" value="DIA Differita" onClick="javascript:updateFormElements(0);" <%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) ? " checked" : "" %>>
      <dhv:label name="requestor.requestor_add.Organization">D.I.A. Differita</dhv:label>
      <input type="radio" name="form_type" id="DIA Semplice" value="DIA Semplice" onClick="javascript:updateFormElements(1);" <%= "individual".equals((String) request.getParameter("form_type")) ? " checked" : "" %>>
      <dhv:label name="requestor.requestor_add.Individual">D.I.A. Semplice</dhv:label>
    </td>
  </tr>
  </dhv:include>
 
  

   
  
  <tr >
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Classification">Tipo DIA</dhv:label>
    </td>
    <td>
      <input type="radio" name="dunsType" id="DIA Semplice" value="DIA Semplice" checked>
      <dhv:label name="requestor.requestor_add.Individual">D.I.A. Semplice</dhv:label>
      <input type="radio" id="DIA Differita" name="dunsType" value="DIA Differita" >
      <dhv:label name="requestor.requestor_add.Organization">D.I.A. Differita</dhv:label>
      
    </td>
  </tr>
   
   

    
    
  <tr>
    <td nowrap class="formLabel">
        <dhv:label name="contact.sources">  Attivit&agrave;</dhv:label>
      </td>
	<td>
     <input type="radio" id="tipoD" name="tipoDest" value="Es. Commerciale" onClick="removeAllLocali(); gestisciDivProvenienza(1); javascript:updateFormElementsNew('Es. Commerciale'); gestioneSedeLegale('Es. Commerciale');disabilitaDistributoriCampi(); abilita_chk();" <%if(OrgDetails.getTipoDest()!=null && OrgDetails.getTipoDest().equals("Es. Commerciale")){ %> checked="checked" <%} %> >Fissa
      <input type="radio" id="tipoD3" name="tipoDest" value="Autoveicolo" onClick="gestisciDivProvenienza(0); javascript:removeAllLocali();clonaLocaleFunzionalmenteCollegato();updateFormElementsNew('Autoveicolo'); gestioneSedeLegale('Autoveicolo'); disabilitaDistributoriCampi(); disabilita_chk();" <%if(OrgDetails.getTipoDest()!=null && OrgDetails.getTipoDest().equals("Autoveicolo")){ %> checked="checked" <%} %> >Mobile
      <input type="radio" id="tipoD2" name="tipoDest" value="Distributori" <%if(OrgDetails.getTipoDest()!=null){ if(OrgDetails.getTipoDest()!=null && OrgDetails.getTipoDest().equals("Distributori")){ %> checked <%}}%>  onClick="removeAllLocali();gestisciDivProvenienza(0); javascript:abilitaDistributoriCampi(); gestioneSedeLegale('Distributori'); disabilita_chk();">Distributori
        <input type="hidden" name="orgType" value="" />



      <input type="hidden" name="check" />
      
      <div id="divProvenienza" style="display:none">
        <dhv:label name="">Provenienza: </dhv:label>
       <input type="radio" name="provenienza" id="provenienzaIT" checked="checked" value="ITALIA" onclick="gestisciProvenienzaEstera(0); setLabelSedeLegale('ITALIA');"/> <img width="20px" src="images/flags/it.gif"/> Italia
      <input type="radio" name="provenienza" id="provenienzaEST" value="ESTERO" onclick="gestisciProvenienzaEstera(1); setLabelSedeLegale('ESTERO');"/> <img width="20px" src="images/flags/eu.gif"/> Estera
       
       <div id="divListaEstera" style="display:none">
        <%= CountryList.getHtmlSelect("country",-1) %>   	<font color = "red">*</font>
      </div>
      
      
      </div>
      </td>
      </tr>
  
  
  <dhv:include name="accounts-name" none="true">
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1" TITLE='ATTENZIONE! In questo campo va specificata con esattezza solo la denominazione legale dell impresa (se ditta individuale scrivere solo nome e cognome)'>
      <dhv:label name="requestor.requestor_add.OrganizationName">Organization Name</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  </dhv:include>

<tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Ente/Associazione</dhv:label>
    </td>
    <td>
      <input type="checkbox" id="no_piva" name="no_piva"
      		onclick="javascript:gestisciPIVA(this)"/>(Partita IVA non obbligatoria)
    </td>
  </tr>
  
  <tr>
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <input type="text" size="20" maxlength="11" id = "partitaIva" name="partitaIva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>">
       <div id="linkpiva">
       &nbsp;[<a href="javascript: popLookupSelectorCheckImprese2(document.getElementById('partitaIva').value,'','lookup_codistat','');"><dhv:label name=""> Verifica Preesistenza </dhv:label></a>] <font color="red">* (Inserire partita iva)</font>
    	</div>
    </td>
  </tr>
  
  <tr>
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="20" maxlength="16" id="codiceFiscale" name="codiceFiscale" disabled="disabled" value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>"><font style="display:none" id="no_cf" color="red">* (INSERIRE CODICE FISCALE)</font>     
    </td>
  </tr>
  
   <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Vendita con canali non convenzionali</dhv:label>
    </td>
    <td>
     	<input type = "checkbox" name = "flag_vendita" <%=(OrgDetails.isFlagVenditaCanali()) ? "checked" : "" %>>
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
	  <input type="hidden" id="id_attivita_masterlist" name="id_attivita_masterlist" value="-1">
<%-- 	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="codiceFiscaleCorrentista" name="codiceFiscaleCorrentista" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleCorrentista()) %>"><font color="red">*</font>	--%>
	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="codiceFiscaleCorrentista" name="codiceFiscaleCorrentista" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleCorrentista() ) %>" onchange="costruisci_rel_ateco_attivita('codiceFiscaleCorrentista', 'id_rel_principale' );"   >
	  <font color="red">*</font>
	  &nbsp;[<a href="javascript:popLookupSelectorCustomImprese('codiceFiscaleCorrentista','alertText','id_attivita_masterlist','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Seleziona</dhv:label></a>]
	  
	  <%
	  
	 
	  if ( List_id_rel_principale!= null && linea_attivita_principale != null)
	  {
		  %>
	  <br/><%= List_id_rel_principale.getHtmlSelect("id_rel_principale", linea_attivita_principale.getId_rel_ateco_attivita() ) %>
	  <%}
	  else
	  {
		  
		
		  %>
		  <%= lookup_vuota_linea_attivita.getHtmlSelect("id_rel_principale", -1 ) %>
		  <%
	  }
	  %>
	</td>
  </tr>
  
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.AlertDescription">Alert Description</dhv:label>
      </td>
      <td>
        <input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="alertText" name="alertText" value="<%= toHtmlValue(OrgDetails.getAlertText()) %>">
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
			<input type="hidden" id="id_attivita_masterlist_1" name="id_attivita_masterlist_1" value="-1">
      		<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice1" name="codice1" class="codiciatecolista"
      			   onchange="costruisci_rel_ateco_attivita('codice1',  'id_rel_1' ); abilita_codici_ateco_vuoti();"	value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>0) ? (toHtmlValue(linee_attivita_secondarie.get(0).getCodice_istat())) : ("")) %>" >
      		[<a href="javascript:popLookupSelectorCustomImprese('codice1','cod1',  'id_attivita_masterlist_1', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod1" name="cod1" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>0) ? (toHtmlValue(linee_attivita_secondarie.get(0).getDescrizione_codice_istat())) : ("")) %>" >
      		<br/><%
				if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>0)
					out.println( (List_id_rel_1.getHtmlSelect("id_rel_1" , linee_attivita_secondarie.get(0).getId_rel_ateco_attivita()  ) )  );
				else
					out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_1" , -1 ) ) );
					
			%><br/>
			<br></br>
        	
      		 <div id="div_codice1" <%=(linee_attivita_secondarie != null &&  linee_attivita_secondarie.size()>1) ? ""  : "style='display: none'"%>>
      		 	<b>Codice 2&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_2" name="id_attivita_masterlist_2" value="-1">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice2" name="codice2" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice2',  'id_rel_2' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>1) ? (toHtmlValue(linee_attivita_secondarie.get(1).getCodice_istat())) : ("")) %>" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice2','cod2',  'id_attivita_masterlist_2', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod2" name="cod2" value="<%= ((linee_attivita_secondarie != null &&  linee_attivita_secondarie.size()>1) ? (toHtmlValue(linee_attivita_secondarie.get(1).getDescrizione_codice_istat())) : ("")) %>" >
      		 	<br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>1)
						out.println(List_id_rel_2.getHtmlSelect("id_rel_2", linee_attivita_secondarie.get(1).getId_rel_ateco_attivita() ) );
					else
						out.println(lookup_vuota_linea_attivita.getHtmlSelect("id_rel_2", -1 ));
						
				%><br/>
				<br></br>
      		 </div>
      		 
      		 <div id="div_codice2" <%=(linee_attivita_secondarie != null && linee_attivita_secondarie.size()>2) ? ""  : "style='display: none'"%>>
      		 	<b>Codice 3&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_3" name="id_attivita_masterlist_3" value="-1">
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice3" name="codice3" class="codiciatecolista"
      		    	onchange="costruisci_rel_ateco_attivita('codice3',  'id_rel_3' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>2) ? (toHtmlValue(linee_attivita_secondarie.get(2).getCodice_istat())) : ("")) %>" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice3','cod3',  'id_attivita_masterlist_3', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod3" name="cod3" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>2) ? (toHtmlValue(linee_attivita_secondarie.get(2).getDescrizione_codice_istat())) : ("")) %>" >
      		    <br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>2)
						out.println( (List_id_rel_3.getHtmlSelect("id_rel_3" , linee_attivita_secondarie.get(2).getId_rel_ateco_attivita()  ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_3" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>
      		 
      		 <div id="div_codice3" <%=(linee_attivita_secondarie != null &&  linee_attivita_secondarie.size()>3) ? ""  : "style='display: none'"%>>
      		 	<b>Codice 4&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_4" name="id_attivita_masterlist_4" value="-1">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice4" name="codice4" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice4',  'id_rel_4' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>3) ? (toHtmlValue(linee_attivita_secondarie.get(3).getCodice_istat())) : ("")) %>" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice4','cod4',  'id_attivita_masterlist_4', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod4" name="cod4" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>3) ? (toHtmlValue(linee_attivita_secondarie.get(3).getDescrizione_codice_istat())) : ("")) %>" >
      		 	<br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>3)
						out.println( (List_id_rel_4.getHtmlSelect("id_rel_4" , linee_attivita_secondarie.get(3).getId_rel_ateco_attivita()  ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_4" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice4" <%=(linee_attivita_secondarie != null && linee_attivita_secondarie.size()>4) ? ""  : "style='display: none'"%>>
      		 	<b>Codice 5&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_5" name="id_attivita_masterlist_5" value="-1">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice5" name="codice5" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice5',  'id_rel_5' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>4) ? (toHtmlValue(linee_attivita_secondarie.get(4).getCodice_istat())) : ("")) %>" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice5', 'cod5', 'id_attivita_masterlist_5', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod5" name="cod5" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>4) ? (toHtmlValue(linee_attivita_secondarie.get(4).getDescrizione_codice_istat())) : ("")) %>" >
      		    <br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>4)
						out.println( (List_id_rel_5.getHtmlSelect("id_rel_5" , linee_attivita_secondarie.get(4).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_5" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice5" <%=(linee_attivita_secondarie != null && linee_attivita_secondarie.size()>5) ? ""  : "style='display: none'"%>>
      		 	<b>Codice 6&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_6" name="id_attivita_masterlist_6" value="-1">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice6" name="codice6" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice6',  'id_rel_6' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>5) ? (toHtmlValue(linee_attivita_secondarie.get(5).getCodice_istat())) : ("")) %>" >
      		  	[<a href="javascript:popLookupSelectorCustomImprese('codice6','cod6',  'id_attivita_masterlist_6', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod6" name="cod6" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>5) ? (toHtmlValue(linee_attivita_secondarie.get(5).getDescrizione_codice_istat())) : ("")) %>" >
      		  	<br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>5)
						out.println( (List_id_rel_6.getHtmlSelect("id_rel_6" , linee_attivita_secondarie.get(5).getId_rel_ateco_attivita()  ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_6" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice6" <%=(linee_attivita_secondarie != null && linee_attivita_secondarie.size()>6) ? ""  : "style='display: none'"%>>
      		 	<b>Codice 7&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_7" name="id_attivita_masterlist_7" value="-1">
      		  	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice7" name="codice7" class="codiciatecolista"
      		  		onchange="costruisci_rel_ateco_attivita('codice7',  'id_rel_7' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>6) ? (toHtmlValue(linee_attivita_secondarie.get(6).getCodice_istat())) : ("")) %>" >
      		  	[<a href="javascript:popLookupSelectorCustomImprese('codice7','cod7', 'id_attivita_masterlist_7', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod7" name="cod7" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>6) ? (toHtmlValue(linee_attivita_secondarie.get(6).getDescrizione_codice_istat())) : ("")) %>" >
      		  	<br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>6)
						out.println( (List_id_rel_7.getHtmlSelect("id_rel_7" , linee_attivita_secondarie.get(6).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_7" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>	

      		 <div id="div_codice7" <%=(linee_attivita_secondarie != null && linee_attivita_secondarie.size()>7) ? ""  : "style='display: none'"%>>
      		 	<b>Codice 8&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_8" name="id_attivita_masterlist_8" value="-1">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice8" name="codice8" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice8',  'id_rel_8' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>7) ? (toHtmlValue(linee_attivita_secondarie.get(7).getCodice_istat())) : ("")) %>" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice8', 'cod8','id_attivita_masterlist_8', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod8" name="cod8" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>7) ? (toHtmlValue(linee_attivita_secondarie.get(7).getDescrizione_codice_istat())) : ("")) %>" >
      		 	<br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>7)
						out.println( (List_id_rel_8.getHtmlSelect("id_rel_8" , linee_attivita_secondarie.get(7).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_8" , -1 ) ) );
						
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice8" <%=(linee_attivita_secondarie != null && linee_attivita_secondarie.size()>8) ? ""  : "style='display: none'"%>>
      		 	<b>Codice 9&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_9" name="id_attivita_masterlist_9" value="-1">
         	    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice9" name="codice9" class="codiciatecolista"
         	    	onchange="costruisci_rel_ateco_attivita('codice9',  'id_rel_9' ); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>8) ? (toHtmlValue(linee_attivita_secondarie.get(8).getCodice_istat())) : ("")) %>" >
         	    [<a href="javascript:popLookupSelectorCustomImprese('codice9','cod9', 'id_attivita_masterlist_9', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
         	    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod9" name="cod9" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>8) ? (toHtmlValue(linee_attivita_secondarie.get(8).getDescrizione_codice_istat())) : ("")) %>" >
         	    <br/><%
					if (linee_attivita_secondarie != null && linee_attivita_secondarie.size()>8)
						out.println( (List_id_rel_9.getHtmlSelect("id_rel_9" , linee_attivita_secondarie.get(8).getId_rel_ateco_attivita() ) )  );
					else
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_9" , -1 ) ) );
						
				%><br/>
				<br></br>	
         	 </div>
      		 
			<div id="div_codice9" <%=(linee_attivita_secondarie != null && linee_attivita_secondarie.size()>9) ? ""  : "style='display: none'"%>>
      		 	<b>Codice 10</b>
      		 	<input type="hidden" id="id_attivita_masterlist_10" name="id_attivita_masterlist_10" value="-1">
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice10" name="codice10" class="codiciatecolista"
      		    	onchange="costruisci_rel_ateco_attivita('codice10', 'id_rel_10'); abilita_codici_ateco_vuoti();" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>9) ? (toHtmlValue(linee_attivita_secondarie.get(9).getCodice_istat())) : ("")) %>" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice10', 'cod10','id_attivita_masterlist_10', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod10" name="cod10" value="<%= ((linee_attivita_secondarie != null && linee_attivita_secondarie.size()>9) ? (toHtmlValue(linee_attivita_secondarie.get(9).getDescrizione_codice_istat())) : ("")) %>" >
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
  


   <dhv:include name="organization.source" none="true"> 
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="contact.source">Source</dhv:label>
      </td>
    
      <td>
     <table border=0>
      <tr>
      <td >
      <%	SourceList.setJsEvent("onChange=\"javascript:selectCarattere('source');\"");
        %>
        <%= SourceList.getHtmlSelect("source",OrgDetails.getSource())%>
        
                
    		</td>
          	<td style="<%=((OrgDetails.getSource()==1)?("visibility:visible"):("visibility: hidden;"))%>" id="data1">
        		Dal
        	</td>
        	<td style="<%=((OrgDetails.getSource()==1)?("visibility:visible"):("visibility: hidden;"))%>" id="dat3">
           		<zeroio:dateSelect form="addAccount" field="dateI" timestamp="<%= OrgDetails.getDateI() %>" showTimeZone="false" /><font color="red">*</font>
          	</td>
       
       	 	
           	<td style="<%=((OrgDetails.getSource()==1)?("visibility:visible"):("visibility: hidden;"))%>" id="data2">
           		Al
           	</td>
           
            	<td style="<%=((OrgDetails.getSource()==1)?("visibility:visible"):("visibility: hidden;"))%>" id="data4">
           		<zeroio:dateSelect form="addAccount" field="dateF" timestamp="<%= OrgDetails.getDateF() %>" showTimeZone="false" /><font color="red">*</font>
           	</td>
           	<td style="<%=((OrgDetails.getSource()==1)?("visibility:visible"):("visibility: hidden;"))%>" id="cessazione">
           	<input type="checkbox" name="cessazione" value ="true"  checked /> <dhv:label name="accounts.Assetsf">Cessazione Automatica</dhv:label>
           	</td>
           	
           	<td style="visibility: hidden" id="data5"> 
           	
           	 &nbsp;[<a href="javascript:popLookupSelectorCustomDia('name','alert','organization','');"><dhv:label name="">Seleziona</dhv:label></a>]
           	</td>
          
    </tr>
    </table>
   </dhv:include> 
 
 
  
    
   <tr style="display: none">
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.AlertDate">Alert Date</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="alertDate" timestamp="<%= OrgDetails.getAlertDate() %>" timeZone="<%= OrgDetails.getAlertDateTimeZone() %>" showTimeZone="false" /><font color="red">*</font>
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.data">Data</dhv:label>
      </td>
      <td>
      	
      	<input readonly type="text" id="dataPresentazione" name="dataPresentazione" size="10" value="<%= toDateasString(OrgDetails.getDataPresentazione())%>" />
        <a href="#" onClick="cal19.select(document.forms[0].dataPresentazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
        <font color="red">*</font>
      </td>
    </tr>
    <%-- %><tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data inizio attività</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="date2" timestamp="<%= OrgDetails.getDate2() %>" showTimeZone="false" /><font color="red">*</font>
      </td>
    </tr>--%>
    
    <dhv:include name="requestor-stage" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="requestor.stage">Servizio Competente</dhv:label>
      </td>
      <td>
        <%= StageList.getHtmlSelect("stageId",OrgDetails.getStageId()) %><font color="red">*</font>
      </td>
    </tr>
  </dhv:include>  
  
  <dhv:include name="organization.alert" none="true">
  </dhv:include>
  
  <!--  aggiunto da d.dauria -->
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
<%--
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Titolo</dhv:label>
    </td>
    <td>  <!-- titoloRappresentante è il nome della variabile nel bean -->
       <%= TitoloList.getHtmlSelect("titoloRappresentante",OrgDetails.getTitoloRappresentante()) %></td>
  </tr>--%>
  <tr >
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="30" maxlength="16" name="codiceFiscaleRappresentante" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font>
    </td>
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
      <input readonly type="text" id="dataNascitaRappresentante" name="dataNascitaRappresentante" size="10" value = "<%=toDateasString(OrgDetails.getDataNascitaRappresentante()) %>" />
       <a href="#" onClick="cal19.select(document.forms[0].dataNascitaRappresentante,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
      
       
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di Nascita</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtmlValue(OrgDetails.getLuogoNascitaRappresentante()) %>">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Comune di residenza</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="city_legale_rapp" value="<%= toHtmlValue(OrgDetails.getCity_legale_rapp()) %>">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Provincia</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="prov_legale_rapp" value="<%= toHtmlValue(OrgDetails.getProv_legale_rapp()) %>">
    </td>
  </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Indirizzo</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="address_legale_rapp" value="<%= toHtmlValue(OrgDetails.getAddress_legale_rapp()) %>">
    </td>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Email</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="emailRappresentante" value="<%= toHtmlValue(OrgDetails.getEmailRappresentante()) %>">
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
    
  </tr>
  
  
  
  <!--  -->
  
</table>
<br>



<%
int numlocali=0;

OrganizationAddress sedeLocale1=null;
OrganizationAddress sedeLocale2=null;
OrganizationAddress sedeLocale3=null;
OrganizationAddress sedeLegale=null;
OrganizationAddress sedeOperativa=null;
OrganizationAddress sedeMobile=null;
Iterator itera=OrgDetails.getAddressList().iterator();

while(itera.hasNext()){
	OrganizationAddress temp=(OrganizationAddress)itera.next();
	if(temp.getType()==1)
		sedeLegale=temp;
	if(temp.getType()==5)
		sedeOperativa=temp;
	
	if(temp.getType()==7)
		sedeMobile=temp;
	
	if(temp.getType()==6){
		if(numlocali==0){
			sedeLocale1=temp;
			
		}
		if(numlocali==1){
			sedeLocale2=temp;
			
		}
		if(numlocali==2){
			sedeLocale3=temp;
			
		}
		numlocali++;
	}
		
	
	if(temp.getType()==7)
		sedeMobile=temp;
	if(temp.getType()==7)
		sedeMobile=temp;
	
	
}

if(sedeLegale!=null)
{
	Address = sedeLegale;
}
if(sedeMobile!=null)
{
	AddressSedeMobile = sedeMobile;
}
if(sedeOperativa!=null)
{
	AddressSedeOperativa = sedeOperativa;
}
if(sedeLocale1 !=null)
{
	AddressLocale1 = sedeLocale1;
}
if(sedeLocale2 !=null)
{
	AddressLocale2 = sedeLocale2;
}
if(sedeLocale3 !=null)
{
	AddressLocale3 = sedeLocale3;
}
%>

<%
  boolean noneSelected = false;
%>
<%--
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Sede Legale</strong>
	    <input type="hidden" name="address1type" value="1">
	  </th>
  </tr>
  <tr>
	<td nowrap class="formLabel" name="province" id="province">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
    
    
    <table class = "noborder">
    <td>
    
      <select  name="address1city" id="provs" style="display: none" disabled="disabled">
	<option value="-1">Nessuna Selezione</option>
            
	 <%
                Vector v = OrgDetails.getComuni2();
	 			Enumeration e=v.elements();
                while (e.hasMoreElements()) {
                	String prov4=e.nextElement().toString();
                	
        %>
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(AddressSedeOperativa.getCity())) {%> selected="selected" <%} %>><%= prov4 %></option>	
              <%}%>
		
	</select> 
	
    <input type="text" name="address1city" id="address1city2" value = "<%=toHtmlValue(Address.getCity()) %>" style="display: block;">
	</td><td><div id = "sl"></div> 	</td></table>
	
	</td>
  	</tr>	
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address1line1" id="address1line1" maxlength="80" value="<%= toHtmlValue(Address.getStreetAddressLine1()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="">C/O</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address1line2" maxlength="80" value = "<%=toHtmlValue(Address.getStreetAddressLine2()) %>">
    </td>
  </tr>

  

  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address1zip" maxlength="12" value = "<%=toHtmlValue(Address.getZip()) %>">
    </td>
  </tr>
  
  
  
  
  
  
  
  
  	 
  	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	  <input type="text" size="28" name="address1state" maxlength="80" value="<%= toHtmlValue(Address.getState()) %>">          
        <div id="div_provincia" style="display: none;">  
          <input type="text" size="28" name="address1state1" maxlength="80">
       </div>          
    </td>
  </tr>
  
  	
  
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" id="address1latitude" name="address1latitude" size="30" value="<%=Address.getLatitude() %>" readonly="readonly" >		
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" id="address1longitude" name="address1longitude" size="30" value="<%=Address.getLongitude() %>" readonly="readonly" ></td>
  </tr>
  <tr style="display: block">
    <td colspan="2">
    	<input id="coordbutton" type="button" value="Calcola Coordinate" <%if (OrgDetails!= null && (("Es. Commerciale").equals(OrgDetails.getTipoDest()))){ %>disabled="true" <%} %>
    	onclick="javascript:showCoordinate(document.getElementById('address1line1').value, document.forms['addAccount'].address1city2.value,document.forms['addAccount'].address1state.value, document.forms['addAccount'].address1zip.value, document.forms['addAccount'].address1latitude, document.forms['addAccount'].address1longitude);" /> 
    </td>
  </tr> 
  
</table> --%>

<br>
   
    

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Sede Operativa</dhv:label></strong>
	  <input type="hidden" name="address2type" value="5">
	  </th>
  </tr>
 <tr>
	<td nowrap class="formLabel" name="province1" id="prov2">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
	<select  name="address2city" id="prov12">
	<option value="-1">Nessuna Selezione</option>
            
	 <%
                Vector v4 = OrgDetails.getComuni2();
	 			Enumeration e4=v4.elements();
               
              
              UserBean user = (UserBean) session.getAttribute("User");
              if(user.getRoleId()==16 && user.getSiteId()==8)
              {
            	  %>
            	   <option value="ACERRA" <%if("ACERRA".equalsIgnoreCase(AddressSedeOperativa.getCity())) {%> selected="selected" <%} %>>ACERRA</option>	
             
            	   <option value="CASALNUOVO DI NAPOLI" <%if("CASALNUOVO DI NAPOLI".equalsIgnoreCase(AddressSedeOperativa.getCity())) {%> selected="selected" <%} %>>CASALNUOVO DI NAPOLI</option>	
             
            	  <% 
              }
            
              
               while (e4.hasMoreElements()) {
                	String prov4=e4.nextElement().toString();
                	
                	
                  
        %>
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(AddressSedeOperativa.getCity())) {%> selected="selected" <%} %>><%= prov4 %></option>	
              <%}%>
		
	</select> &nbsp;<div id = "so"></div> 
	</td>
  	</tr>	
  	
  <tr>
    <td nowrap class="formLabel" id="indirizzo1">
      Indirizzo
    </td>
    <td>
      <input type="text" size="40" name="address2line1" maxlength="80" id="indirizzo12" value="<%= toHtmlValue(AddressSedeOperativa.getStreetAddressLine1()) %>">
    </td>
  </tr>
 
  
  <tr>
    <td nowrap class="formLabel" id="cap1">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address2zip" maxlength="12" value = "<%=toHtmlValue(AddressSedeOperativa.getZip()) %>" id="cap">
    </td>
  </tr>  
  	 
  	<tr>
    <td nowrap class="formLabel" id="stateProv2" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    
          <% if (User.getSiteId() == 202) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="Benevento">          
          <%}%>
          <% if (User.getSiteId() == 201) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="Avellino">
          <%}%>
          <% if (User.getSiteId() == 203) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="Caserta">
          <%}%>
          <% if (User.getSiteId() == 204 || User.getSiteId() == 205 || User.getSiteId() == 206) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="Napoli">
          <%}%>
          <% if (User.getSiteId() == 207) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="Salerno">
          <%}%>
          <% if(User.getSiteId() == -1){ %>
          <input type="text" size="28" name="address2state" maxlength="80" value="">
          <%}%>
    </td>
  </tr>
  
  	

  
  
  
  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude2"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<%-- <input type="text" id="address2latitude" name="address2latitude" size="30" value="<%=AddressSedeOperativa.getLatitude() %>" id=""><font color="red">*</font>--%>
    	<input type="text" readonly="readonly" id="address2latitude" name="address2latitude" size="30" value="<%=Address.getLatitude() %>" id="">
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td>
    <%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" > --%>
   	 	<input type="text" readonly="readonly" id="address2longitude" name="address2longitude" size="30" value="<%=Address.getLongitude() %>" >
    </td>
  </tr>
  <tr style="display: block">
    <td colspan="2">
    <input id="coord1button" type="button" value="Calcola Coordinate" 
    onclick="javascript:showCoordinate(document.getElementById('indirizzo12').value, document.forms['addAccount'].address2city.value,document.forms['addAccount'].address2state.value, document.forms['addAccount'].address2zip.value, document.forms['addAccount'].address2latitude, document.forms['addAccount'].address2longitude);" /> 
     </td>
   </tr>

</table>
<br>
<%@ include file="../sede_legale.jsp" %>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Attivit&agrave; Mobile</dhv:label></strong>
	  </th>
  </tr>
      <tr>
      <td nowrap class="formLabel" id="tipoStruttura1">
        <dhv:label name="contact.fsource">Tipo Struttura</dhv:label>
       <input type="hidden" name="address3type" value="7">
      </td>
    
      <td id="tipoStruttura">
        <%= TipoStruttura.getHtmlSelect("TipoStruttura",OrgDetails.getTipoStruttura())%>
        &nbsp;&nbsp;&nbsp;&nbsp;<div id = "mob"></div> 
      </td>
     </tr>
    
   <tr id="list2" >
    <td class="formLabel" nowrap name="targaVeicolo1" id="targaVeicolo1">
      <dhv:label name="">Targa/Codice Autoveicolo</dhv:label>
    </td>
    <td>
      <input id="targaVeicolo" type="text" size="20" maxlength="10" name="nomeCorrentista" value="<%= toHtmlValue(OrgDetails.getNomeCorrentista()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
 
  <tr id="list"  >
    <td class="formLabel" nowrap  id="tipoVeicolo1">
      <dhv:label name="">Tipo Autoveicolo</dhv:label>
    </td>
    <td>
      <input id="tipoVeicolo" type="text" size="30" maxlength="50" name="contoCorrente" value="<%= toHtmlValue(OrgDetails.getContoCorrente()) %>"><%--<font color="red">*</font> <%= showAttribute(request, "nameError") --%>
    </td>
  </tr>
  
  
 <tr>
	<td nowrap class="formLabel" name="province" id="prov1">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
     <td > 
    <select  name="address3city" id="prov">
		<option value="-1">Nessuna Selezione</option>
            
	 <%
                Vector v42 = OrgDetails.getComuni2();
	 			Enumeration e42=v42.elements();
                while (e42.hasMoreElements()) {
                	String prov42=e42.nextElement().toString();
                	
                	
                  
        %>
                <option value="<%=prov42%>" <%if(prov42.equalsIgnoreCase(AddressSedeMobile.getCity())) {%> selected="selected" <%} %>><%= prov42 %></option>	
              <%}%>
		
	</select> 
	
	</td>
   
  	</tr>
  <tr>
    <td nowrap class="formLabel" id="addressLine">
      Indirizzo
    </td>
    <td>
      <input type="text" size="40" name="address3line1" maxlength="80" id="addressline1" value="<%= toHtmlValue(AddressSedeMobile.getStreetAddressLine1()) %>">
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel" id="labelCap">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address3zip" value ="<%=toHtmlValue(AddressSedeMobile.getZip()) %>" maxlength="12" id="addresszip">
    </td>
  </tr>
  	
  	 
  	<tr>
    <td nowrap class="formLabel" id="stateProv1">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	<input type="text"  size="28" name="address3state" maxlength="80" value ="<%=toHtmlValue(AddressSedeMobile.getState()) %>" >
    </td>
  </tr>
  

  

 
  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude1"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" readonly="readonly" id="address3latitude" value="<%=AddressSedeMobile.getLatitude() %>" name="address3latitude" size="30" value="" id="latitude"><font color="red" id="attivita">*</font>
    	<%-- %>input type="button"
			onclick="javascript:Geocodifica.getCoordinate( document.forms[0].address3line1.value,document.forms[0].address3city.value,document.forms[0].address3state.value,'','address3latitude','address3longitude','address3coordtype',setGeocodedLatLon)"
			value="<dhv:label name="geocodifica.calcola">Calcola</dhv:label>"
		/>
    	<input type="hidden" name="address3coordtype" id="address3coordtype" value="0" /--%>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude1"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" readonly="readonly" id="address3longitude" name="address3longitude" size="30" value="<%=AddressSedeMobile.getLongitude() %>" id="longitude"><font color="red" id="attivita2">*</font></td>
  </tr>
   <tr style="display:block">
    <td colspan="2">
    <input id="coord2button" type="button" value="Calcola Coordinate" 
    onclick="javascript:showCoordinate(document.getElementById('addressline1').value, document.forms['addAccount'].address3city.value,document.forms['addAccount'].address3state.value, document.forms['addAccount'].address3zip.value, document.forms['addAccount'].address3latitude, document.forms['addAccount'].address3longitude);" /> 
     </td>
    </tr>
</table>
<br><br>


<%-- Addresses --%>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  
   <input type="hidden" id = "elementi" name="elementi" value="0"/>
   <input type="hidden" id = "size" name="size" value="0"/>
   
   <tr>
   
   <td id = "locale_0" style="display:none">
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
 		<input type="hidden" name="address4type0" value="6">
      </td>
    <td>
   
        <%= TipoLocale.getHtmlSelect("TipoLocale",OrgDetails.getTipoLocale())%>
      <div id = "loc1"></div>
      </td>
  </tr>
  <tr class="containerBody" id = "locale1_city">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
            <input type="text" size="28" id="address4city" name="address4city" maxlength="80" value="">
   
   <font id = "city_loc" style="display: none" color = "red">*</font>
      </td>
  </tr>
  <tr class="containerBody" id = "locale1_indirizzo">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
 
         <input type="text" size="40" name="address4line" maxlength="80" value="<%= toHtmlValue(Address.getStreetAddressLine1()) %>">
 <font id = "address_loc" style="display: none" color = "red">*</font>
    </td>
  </tr>
 
  
  <tr class="containerBody" id = "locale1_zip">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address4zip" maxlength="5" value="">
       <font id = "zip_loc" style="display: none" color = "red">*</font>
    </td>
  </tr>
  <tr class="containerBody" id = "locale1_prov">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
       <input type="text" size="25" name="address4state"   value="">
        <font id = "state_loc" style="display: none" color = "red">*</font>
    </td>
  </tr>

  <tr class="containerBody" id = "locale1_lat">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" id="address4latitude" name="address4latitude" size="30" value="" > 
    	
   </td>
  </tr>
  <tr class="containerBody" id = "locale1_long">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" id="address4longitude" name="address4longitude" size="30" value=""></td>
  </tr>
  
  
  </table>
    </td>
    </tr>
    
  <tr>
  
  <td><input type="button" onClick="clonaLocaleFunzionalmenteCollegato()" id="locali_button" value="Aggiungi altro Indirizzo" ></td>

</tr>
<tr>
<td><input type="button" onClick="removeLocale(document.getElementById('elementi').value)" id="locali_button2" value="Rimuovi Indirizzo" ></td>
</tr>
</table>



<br><br>
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
      
      	<input readonly type="text" id="date1" name="date1" size="10" value = "<%=toDateasString(OrgDetails.getDate1()) %>" />
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
            <option <%=("Favorevole".equals(OrgDetails.getNameMiddle())) ? "selected='selected'" :"" %>   value="Favorevole">Favorevole</option>
			<option <%=("Non favorevole".equals(OrgDetails.getNameMiddle())) ? "selected='selected'" :"" %> value="Non favorevole">Non favorevole</option>
			<option  <%=("Favorevole con lievi non conformita'".equals(OrgDetails.getNameMiddle())) ? "selected='selected'" :"" %> value="Favorevole con lievi non conformita'">Favorevole con lievi non conformita'</option>
    </select> 
	</td>
  	</tr>
  	
   <tr>
      <td name="cin" id="cin" nowrap class="formLabel" valign="top">
        <dhv:label name="">Note relative alle non conformità</dhv:label>
      </td>
      <td>
       <TEXTAREA name="cin" ROWS="3" COLS="50"><%=OrgDetails.getCin() %> </TEXTAREA> 
   	<%--	<input type="text" size="50" maxlength="80" name="nameSuffix"> --%>  
      </td>
    </tr>
    
  	<tr>
      <td name="date3" id="date3" nowrap class="formLabel">
        <dhv:label name="">Data di risoluzione della non conformita'</dhv:label>
      </td>
      <td>
      	<div id="data3">
        	<input readonly type="text" id="date3" name="date3" size="10" value = "<%=toDateasString(OrgDetails.getDate3()) %>" />
        <a href="#" onClick="cal19.select(document.forms[0].date3,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        </div>
      </td>
    </tr>
  
  
  </table>

<br>
<br />

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="requestor.requestor_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
  <tr>
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Notes">Notes</dhv:label>
    </td>
    <td><TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA></td>
  </tr>
</table>

<br>




<br />
<input type="hidden" name="onlyWarnings" value='<%=(OrgDetails.getOnlyWarnings()?"on":"off")%>' />
<%= addHiddenParams(request, "actionSource|popup") %>
<input type="submit" value="Inserisci" name="Save" />
<dhv:permission name="requestor-requestor-add">
<input type="submit" value="Inserisci e Clona" onClick="this.form.dosubmit.value='true';this.form.saveandclone.value='true';" /></dhv:permission>
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Requestor.do';this.form.dosubmit.value='false';this.form.cancel.value='true'">

</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="cancel" value="false" />
<input type="hidden" name="saveandclone"  <%if(request.getAttribute("clona")!=null){%>value="true"<%}else{ %>value=""<%} %>/>
<input type = "hidden" name = "forzainserimento" id = "forzaInsert" value = "0">

</form>
</body>
<script>
document.getElementById('elementi').value = 0;
document.getElementById('size').value = 0;
</script>