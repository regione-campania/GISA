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
<jsp:useBean id="IstatList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Address" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLegale" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressSedeOperativa" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressSedeMobile" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLocale1" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLocale2" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLocale3" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IndustryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
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

<jsp:useBean id="Impresa" class="org.aspcfs.modules.imprese_pregresso.base.BImpresePregresso" scope="request"/>
<jsp:useBean id="rel_ateco_linea_attivita_List" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
		  } 
		  if ( (document.getElementById("codice2").value  != "") ){
			  document.getElementById("codice2").value="";
			  document.getElementById("cod2").value="";
		  }
		  if ( (document.getElementById("codice3").value  != "") ){
			  document.getElementById("codice3").value="";
			  document.getElementById("cod3").value="";
		  }
		  if ( (document.getElementById("codice4").value  != "") ){
			  document.getElementById("codice4").value="";
			  document.getElementById("cod4").value="";
		  }
		  if ( (document.getElementById("codice5").value  != "") ){
			  document.getElementById("codice5").value="";
			  document.getElementById("cod5").value="";
		  }
		  if ( (document.getElementById("codice6").value  != "") ){
			  document.getElementById("codice6").value="";
			  document.getElementById("cod6").value="";
		  }
		  if ( (document.getElementById("codice7").value  != "") ){
			  document.getElementById("codice7").value="";
			  document.getElementById("cod7").value="";
		  }
		  if ( (document.getElementById("codice8").value  != "") ){
			  document.getElementById("codice8").value="";
			  document.getElementById("cod8").value="";
		  }
		  if ( (document.getElementById("codice9").value  != "") ){
			  document.getElementById("codice9").value="";
			  document.getElementById("cod9").value="";
		  }
		  if ( (document.getElementById("codice10").value  != "") ){
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
function inCostruzione(){
	alert('In Costruzione!');
	return false;
}
document.onmousemove=positiontip
</script>

<script language="JavaScript" TYPE="text/javascript">
  var indSelected = 0;
  var orgSelected = 1;
  var onLoad = 1;


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
   function resetFormElementsNew() {
   	   elm1 = document.getElementById("tipoVeicolo1"); //Nome
   	   document.addAccount.tipoVeicolo.style.background = "#ffffff";
       document.addAccount.tipoVeicolo.disabled = false;
       elm1.style.color = "#000000";
        
       elm2 = document.getElementById("targaVeicolo1"); //Cognom
       document.addAccount.targaVeicolo.style.background = "#ffffff";
       document.addAccount.targaVeicolo.disabled = false;
       elm2.style.color = "#000000";
       
     /*  elm3 = document.getElementById("codiceCont1"); // Nome (Organization)
       document.addAccount.codiceCont.style.background = "#ffffff";
       document.addAccount.codiceCont.disabled = false;
       elm3.style.color = "#000000";*/
       
      	elm5 = document.getElementById("addressLine"); // Nome (Organization)
       	document.addAccount.addressline1.style.background = "#ffffff";
       	document.addAccount.addressline1.disabled = false;
       	elm5.style.color = "#000000";
       	
       	elm6 = document.getElementById("prov1"); // Nome (Organization)
       	document.getElementById("prov").disabled = true;
       	elm6.style.color = "#000000";
       	
       	elm7 = document.getElementById("labelCap"); // Nome (Organization)
       	document.addAccount.addresszip.style.background = "#ffffff";
       	document.addAccount.addresszip.disabled = false;
       	elm7.style.color = "#000000";
       
        elm8 = document.getElementById("stateProv1"); // Nome (Organization)
       	elm8.style.color = "#000000";
       	
       
       		elm9 = document.getElementById("latitude1"); // Nome (Organization)
       	    document.addAccount.address3latitude.style.background = "#ffffff";
       		document.addAccount.address3latitude.disabled = false;
       		elm9.style.color = "#000000";
       	
       	
       		elm10 = document.getElementById("longitude1"); // Nome (Organization)
       		document.addAccount.address3longitude.style.background = "#ffffff";
       		document.addAccount.address3longitude.disabled = false;
       		elm10.style.color = "#000000";
       	
       	
       	elm = document.getElementById("tipoStruttura1"); // Nome (Organization)
       	document.addAccount.TipoStruttura.style.background = "#ffffff";
       	document.addAccount.TipoStruttura.disabled = false;
       	elm.style.color = "#000000";
       	
       elm12 = document.getElementById("indirizzo1");
       document.addAccount.indirizzo12.style.background = "#ffffff";
       document.addAccount.indirizzo12.disabled = false;
       elm12.style.color = "#000000";
       
       elm17 = document.getElementById("prov2");
       document.getElementById("prov12").disabled = true;
       //document.getElementById("prov12").selectedIndex = 0;
       elm17.style.color = "#000000";
       
       //document.getElementById("prov").selectedIndex = 0;
       
       elm13 = document.getElementById("cap1");
       document.addAccount.cap.style.background = "#ffffff";
       document.addAccount.cap.disabled = false;
       elm13.style.color = "#000000";
       
       elm14 = document.getElementById("stateProv2");
       elm14.style.color = "#000000";
       
       elm15 = document.getElementById("latitude2");
       document.addAccount.address2latitude.style.background = "#ffffff";
       document.addAccount.address2latitude.disabled = false;
       elm15.style.color = "#000000";
       
       elm16 = document.getElementById("longitude2");
       document.addAccount.address2longitude.style.background = "#ffffff";
       document.addAccount.address2longitude.disabled = false;
       elm16.style.color = "#000000";
       
       document.addAccount.address1type.style.background = "#ffffff";
       document.addAccount.address1type.disabled = false;
       document.addAccount.address1type.style.color="#000000"
       	
       
       
  }


   function abilitaDistributoriCampi(){

   document.getElementById("codiceFiscaleCorrentista").value="47.99.20";
   document.getElementById("alertText").value="Commercio effettuato per mezzo di distributori automatici";

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
      elm1.style.color="#cccccc";
       document.addAccount.tipoVeicolo.style.background = "#cccccc";
       document.addAccount.tipoVeicolo.value = "";
       document.addAccount.tipoVeicolo.disabled = true;
       
       elm2.style.color="#cccccc";
       document.addAccount.targaVeicolo.style.background = "#cccccc";
       document.addAccount.targaVeicolo.value = "";
       document.addAccount.targaVeicolo.disabled = true;

   /*    elm3.style.color="#cccccc";
       document.addAccount.codiceCont.style.background = "#cccccc";
       document.addAccount.codiceCont.value = "";
       document.addAccount.codiceCont.disabled = true;*/
       
          elm4.style.color="#cccccc";
       document.getElementById("prov12").disabled = true;
               
       elm5.style.color="#cccccc";
       document.addAccount.addressline1.style.background = "#cccccc";
       document.addAccount.addressline1.value = "";
       document.addAccount.addressline1.disabled = true;
       
       elm6.style.color="#cccccc";
       document.getElementById("prov12").disabled = true;
       //document.getElementById("prov12").selectedIndex=0;
       
       elm7.style.color="#cccccc";
       document.addAccount.addresszip.style.background = "#cccccc";
       document.addAccount.addresszip.value = "";
       document.addAccount.addresszip.disabled = true;
       
       elm8.style.color="#cccccc";
       
       elm9.style.color="#cccccc";
       document.addAccount.address3latitude.style.background = "#cccccc";
       document.addAccount.address3latitude.value = "";
       document.addAccount.address3latitude.disabled = true;
       
       elm10.style.color="#cccccc";
       document.addAccount.address3longitude.style.background = "#cccccc";
       document.addAccount.address3longitude.value = "";
       document.addAccount.address3longitude.disabled = true;
       
       elm4.style.color="#cccccc";
       document.addAccount.TipoStruttura.style.background = "#cccccc";
       document.addAccount.TipoStruttura.value = "";
       document.addAccount.TipoStruttura.disabled = true;
           
       document.getElementById("prov12").disabled = true;
       document.addAccount.check.value = "es";
       document.addAccount.orgType.value = "11"; //Valore per PROPRIETARIO
       elm5 = document.getElementById("indirizzo1");
      elm6 = document.getElementById("prov2");
      elm7 = document.getElementById("cap1");
      elm8 = document.getElementById("stateProv2");
      elm9 = document.getElementById("latitude2");
      elm10 = document.getElementById("longitude2");
      
     	 elm5.style.color="#cccccc";
       document.addAccount.indirizzo12.style.background = "#cccccc";
       document.addAccount.indirizzo12.value = "";
       document.addAccount.indirizzo12.disabled = true;
       
       elm6.style.color="#cccccc";
       document.getElementById("prov").disabled = true;
      //peppe document.getElementById("prov").selectedIndex=0;
       
       elm7.style.color="#cccccc";
       document.addAccount.cap.style.background = "#cccccc";
       document.addAccount.cap.value = "";
       document.addAccount.cap.disabled = true;
       
       elm8.style.color="#cccccc";
       
       elm9.style.color="#cccccc";
       document.addAccount.address2latitude.style.background = "#cccccc";
       document.addAccount.address2latitude.value = "";
       document.addAccount.address2latitude.disabled = true;
       
       elm10.style.color="#cccccc";
       document.addAccount.address2longitude.style.background = "#cccccc";
       document.addAccount.address2longitude.value = "";
       document.addAccount.address2longitude.disabled = true;
       
       document.getElementById("prov").disabled = true;
       document.addAccount.check.value = "autoveicolo";
       document.addAccount.orgType.value = "17"; //Valore per PROPRIETARIO

       elm1=document.addAccount.TipoLocale;
       elm2=document.addAccount.address4city1;
       elm3=document.addAccount.address4line1;
       elm4=document.addAccount.address4latitude1;
       elm5=document.addAccount.address4longitude1;
       elm6=document.addAccount.address4zip1;
       elm7=document.addAccount.address4state1;
       if(elm1!=null){
	
       elm1.style.color="#cccccc";
       elm1.style.background = "#cccccc";

       elm1.value = "";
       elm1.disabled = true;
       }
       if(elm2!=null){
           
       elm2.style.color="#cccccc";
       elm2.style.background = "#cccccc";
       elm2.value = "";
       elm2.disabled = true;
       }

       if(elm3!=null){

       elm3.style.color="#cccccc";
       elm3.style.background = "#cccccc";
       elm3.value = "";
       elm3.disabled = true;
       }
       if(elm4!=null){
           
       elm4.style.color="#cccccc";
       elm4.style.background = "#cccccc";
       elm4.value = "";
       elm4.disabled = true;
       }
       if(elm5!=null){
           
       elm5.style.color="#cccccc";
       elm5.style.background = "#cccccc";
       elm5.value = "";
       elm5.disabled = true;
       }
       if(elm6!=null){
           
       elm6.style.color="#cccccc";
       elm6.style.background = "#cccccc";
       elm6.value = "";
       elm6.disabled = true;
       }
       if(elm7!=null){
           
       elm7.style.color="#cccccc";
       elm7.style.background = "#cccccc";
       elm7.value = "";
       elm7.disabled = true;
       }

       elm1=document.addAccount.TipoLocale2;
       elm2=document.addAccount.address4city2;
       elm3=document.addAccount.address4line12;
       elm4=document.addAccount.address4latitude2;
       elm5=document.addAccount.address4longitude2;
       elm6=document.addAccount.address4zip2;
       elm7=document.addAccount.address4state2;
       if(elm1!=null){

       elm1.style.color="#cccccc";
       elm1.style.background = "#cccccc";
       elm1.value = "";
       elm1.disabled = true;
       }
       if(elm2!=null){
           
       elm2.style.color="#cccccc";
       elm2.style.background = "#cccccc";
       elm2.value = "";
       elm2.disabled = true;
       }
       if(elm3!=null){
           
       elm3.style.color="#cccccc";
       elm3.style.background = "#cccccc";
       elm3.value = "";
       elm3.disabled = true;
       }
       if(elm4!=null){
           
       elm4.style.color="#cccccc";
       elm4.style.background = "#cccccc";
       elm4.value = "";
       elm4.disabled = true;
       }
       if(elm5!=null){
           
       elm5.style.color="#cccccc";
       elm5.style.background = "#cccccc";
       elm5.value = "";
       elm5.disabled = true;
       }
       if(elm6!=null){
           
       elm6.style.color="#cccccc";
       elm6.style.background = "#cccccc";
       elm6.value = "";
       elm6.disabled = true;
       }
       if(elm7!=null){
           
       elm7.style.color="#cccccc";
       elm7.style.background = "#cccccc";
       elm7.value = "";
       elm7.disabled = true;
       }


      

       elm1=document.addAccount.TipoLocale3;
       elm2=document.addAccount.address4city3;
       elm3=document.addAccount.address4line13;
       elm4=document.addAccount.address4latitude3;
       elm5=document.addAccount.address4longitude3;
       elm6=document.addAccount.address4zip3;
       elm7=document.addAccount.address4state3;

       if(elm1!=null){

        elm1.style.color="#cccccc";
       elm1.style.background = "#cccccc";
       elm1.value = "";
       elm1.disabled = true;
       }
       if(elm2!=null){
           
       elm2.style.color="#cccccc";
       elm2.style.background = "#cccccc";
       elm2.value = "";
       elm2.disabled = true;
       }
       if(elm3!=null){
           
       elm3.style.color="#cccccc";
       elm3.style.background = "#cccccc";
       elm3.value = "";
       elm3.disabled = true;
       }
       if(elm4!=null){
           
       elm4.style.color="#cccccc";
       elm4.style.background = "#cccccc";
       elm4.value = "";
       elm4.disabled = true;
       }
       if(elm5!=null){
           
       elm5.style.color="#cccccc";
       elm5.style.background = "#cccccc";
       elm5.value = "";
       elm5.disabled = true;
       }
       if(elm6!=null){
           
       elm6.style.color="#cccccc";
       elm6.style.background = "#cccccc";
       elm6.value = "";
       elm6.disabled = true;
       }
       if(elm7!=null){
           
       elm7.style.color="#cccccc";
       elm7.style.background = "#cccccc";
       elm7.value = "";
       elm7.disabled = true;
       }
       if( document.getElementById("aggiungialtrobutton")!=null)
       document.getElementById("aggiungialtrobutton").disabled="true";
       if( document.getElementById("aggiungialtrobutton2")!=null)
        document.getElementById("aggiungialtrobutton2").disabled="true";
   }
   function updateFormElementsNew(indexText) {
	   /*alert(indexText);*/

		index =0;
	   if(indexText=='Es. Commerciale')
	   {
		   index = 0;

			}
	   if(indexText=='Autoveicolo')
	   {
		   index = 1;

			}
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

	//elm2.style.color="#cccccc";
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
		    //elm1.value = "";
		    elm1.disabled = false;
		    }
		    if(elm2!=null){
			    
		    //elm2.style.color="#cccccc";
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
		    //elm1.value = "";
		    elm1.disabled = false;
		    }
		    if(elm2!=null){
			    
		   // elm2.style.color="#cccccc";
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
	        document.addAccount.address3type.disabled = "true";
	      
	        if(elm1!=null){
	        elm1.style.color="#cccccc";
	        }
	        if(document.addAccount.tipoVeicolo!=null){
	        document.addAccount.tipoVeicolo.style.background = "#cccccc";
	        document.addAccount.tipoVeicolo.value = "";
	        document.addAccount.tipoVeicolo.disabled = true;}
	        if(elm2!=null)
	       // elm2.style.color="#cccccc";
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
	        if(document.addAccount.address3latitude!=null){
	        document.addAccount.address3latitude.style.background = "#cccccc";
	        document.addAccount.address3latitude.value = "";
	        document.addAccount.address3latitude.disabled = true;
	        }
	        if(elm10!=null)
	        elm10.style.color="#cccccc";
	        if(document.addAccount.address3longitude!=null){
	        document.addAccount.address3longitude.style.background = "#cccccc";
	        document.addAccount.address3longitude.value = "";
	        document.addAccount.address3longitude.disabled = true;
	        }
	        if(elm4!=null)
	        elm4.style.color="#cccccc";
	        if(document.addAccount.TipoStruttura!=null){
	        document.addAccount.TipoStruttura.style.background = "#cccccc";
	        document.addAccount.TipoStruttura.value = "";
	        document.addAccount.TipoStruttura.disabled = true;
	        }
	        
	       if(document.getElementById("prov12")!=null){
	        document.getElementById("prov12").disabled = false;
	        document.addAccount.check.value = "es";
	        document.addAccount.orgType.value = "11"; //Valore per PROPRIETARIO
	       }
	        tipo1 = document.getElementById("tipoD");
	        tipo1.checked = true;
	        } else if (index == 1){
	    	  document.addAccount.address3type.disabled = "";
	        resetFormElementsNew();
	        document.addAccount.address1type.style.background = "#000000";
	       	document.addAccount.address1type.disabled = false;
	        
	       elm5 = document.getElementById("indirizzo1");
	       elm6 = document.getElementById("prov2");
	       elm7 = document.getElementById("cap1");
	       elm8 = document.getElementById("stateProv2");
	       elm9 = document.getElementById("latitude2");
	       elm10 = document.getElementById("longitude2");
	       
	      	 elm5.style.color="#cccccc";
	        document.addAccount.indirizzo12.style.background = "#cccccc";
	        document.addAccount.indirizzo12.value = "";
	        document.addAccount.indirizzo12.disabled = true;
	        
	        elm6.style.color="#cccccc";
	        document.getElementById("prov").disabled = true;
	        //peppedocument.getElementById("prov").selectedIndex=0;
	        
	        elm7.style.color="#cccccc";
	        document.addAccount.cap.style.background = "#cccccc";
	        document.addAccount.cap.value = "";
	        document.addAccount.cap.disabled = true;
	        
	        elm8.style.color="#cccccc";
	        
	        elm9.style.color="#cccccc";
	        document.addAccount.address2latitude.style.background = "#cccccc";
	        document.addAccount.address2latitude.value = "";
	        document.addAccount.address2latitude.disabled = true;
	        
	        elm10.style.color="#cccccc";
	        document.addAccount.address2longitude.style.background = "#cccccc";
	        document.addAccount.address2longitude.value = "";
	        document.addAccount.address2longitude.disabled = true;
	        
	        
	    	/*elm3.style.color="#cccccc";
	        document.addAccount.codiceCont.style.background = "#cccccc";
	        document.addAccount.codiceCont.value = "";
	        document.addAccount.codiceCont.disabled = true;*/
	        
	     	document.getElementById("prov").disabled = false;
	        document.addAccount.check.value = "Autoveicolo";
	        document.addAccount.orgType.value = "17"; //Valore per PROPRIETARIO
	       } else if (index==2) {
	      	
	      	resetFormElementsNew();
	        document.addAccount.address3type.disabled = "true";
	        elm1.style.color="#cccccc";
	        document.addAccount.tipoVeicolo.style.background = "#cccccc";
	        document.addAccount.tipoVeicolo.value = "";
	        document.addAccount.tipoVeicolo.disabled = true;
	    
	       // elm2.style.color="#cccccc";
	        document.addAccount.targaVeicolo.style.background = "#cccccc";
	        document.addAccount.targaVeicolo.value = "";
	        document.addAccount.targaVeicolo.disabled = true;
	        
	        document.addAccount.check.value = "codiceCont";
	        document.addAccount.orgType.value = "19"; //Valore per sindaco
	        
	      }
	    }

	   
	   /* if (onLoad != 1){
	      var url = "Accounts.do?command=RebuildFormElements&index=" + index;
	      window.frames['server_commands'].location.href=url;
	    }*/
	    onLoad = 0;
	  }
  
  function resetCarattere(){
  	
  		
  		elm1 = document.getElementById("data1");
 		elm2 = document.getElementById("data2");
 		elm3 = document.getElementById("dat3");
 		elm4 = document.getElementById("data4");
 		
 		elm1.style.visibility = "hidden";
 		elm2.style.visibility = "hidden";
 		elm3.style.visibility = "hidden";
 		elm4.style.visibility = "hidden";
 		document.addAccount.source.selectedIndex=0;
 		
  }
  
  function selectCarattere(){
  
 		elm1 = document.getElementById("data1");
 		elm2 = document.getElementById("data2");
 		elm3 = document.getElementById("dat3");
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
 		}
 	
  }
  function resetCodice(){
  
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
  }
  function doCheck(form) {
      if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function resetCarattere(){
  	
  		
  		elm1 = document.getElementById("data1");
 		elm2 = document.getElementById("data2");
 		elm3 = document.getElementById("data3");
 		elm4 = document.getElementById("data4");
 		
 		elm1.style.visibility = "hidden";
 		elm2.style.visibility = "hidden";
 		elm3.style.visibility = "hidden";
 		elm4.style.visibility = "hidden";
 		document.addAccount.source.selectedIndex=0;
 		
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
 			
 		}
 	
  }
  
 
  function IsNumber(Expression)
{
    Expression = Expression.toLowerCase();
    RefString = "0123456789.-";
    
    if (Expression.length < 1) 
        return (false);
    
    for (var i = 0; i < Expression.length; i++) 
    {
        var ch = Expression.substr(i, 1);
        var a = RefString.indexOf(ch, 0);
        if (a == -1)
            return (false);
    }
    return(true);
}
  
  
  function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";
    
     //aggiunto da d.dauria
       /*if (checkNullString(form.codiceFiscaleRappresentante.value)){
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
      }*/
     
    
    if (form.name){
      if ((checkNullString(form.name.value))){
        message += "- Ragione Sociale richiesta\r\n";
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
    	// message += " test" + form.dateF.value.replace(/"."/g,"") > form.dateI.value +" \r\n";
    }
    
    

     /*
      if (checkNullString(form.accountNumber.value)){
        message += "- Codice richiesto\r\n";
        formTest = false;
      }
      */
      //aggiunto da d.dauria
       
      /*
      if (form.address1latitude && form.address1latitude.value!=""){
      	
      		if ((orgSelected == 1)  ){
      			if (isNaN(form.address1latitude.value) ||  (form.address1latitude.value < 2417159.584320) || (form.address1latitude.value > 4431788.049190)){
       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 2417159.584320 e 4431788.049190  (Sede Legale)\r\n";
       				 formTest = false;
       			}		 
      		}
   	 }   
   	 
   	 if (form.address1longitude && form.address1longitude.value!=""){
      	 
      		if ((orgSelected == 1)  ){
      			if (isNaN(form.address1longitude.value) ||  (form.address1longitude.value < 2587487.362260) || (form.address1longitude.value > 4593983.337630)){
       			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2587487.362260 e 4593983.337630  (Sede Legale)\r\n";
       				 formTest = false;
       			}		 
      		}
   	 }     
      
      */
      
     if (form.address1latitude && form.address1latitude.value!=""){
    	 //alert(!isNaN(form.address2latitude.value));
    		if ((orgSelected == 1)  ){
    			if (isNaN(form.address1latitude.value) ||  (form.address1latitude.value < 39.988475) || (form.address1latitude.value > 41.503754)){
     			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Legale)\r\n";
     			 formTest = false;
     			}		 
    		}
 	 }   
	 
	 if (form.address1longitude && form.address1longitude.value!=""){
   	 //alert(!isNaN(form.address2longitude.value));
   		if ((orgSelected == 1)  ){
   			if (isNaN(form.address1longitude.value) ||  (form.address1longitude.value < 13.7563172) || (form.address1longitude.value > 15.8032837)){
    			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Legale)\r\n";
    		     formTest = false;
    		}		 
   		}
	 }   


	  
  if (form.address2latitude && form.address2latitude.value!=""){
   	 //alert(!isNaN(form.address2latitude.value));
   		if ((orgSelected == 1)  ){
   			if (isNaN(form.address2latitude.value) ||  (form.address2latitude.value < 39.988475) || (form.address2latitude.value > 41.503754)){
    			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Sede Operativa)\r\n";
    				 formTest = false;
    			}		 
   		}
	 }   


	 
	 if (form.address2longitude && form.address2longitude.value!=""){
  	 //alert(!isNaN(form.address2longitude.value));
  		if ((orgSelected == 1)  ){
  			if (isNaN(form.address2longitude.value) ||  (form.address2longitude.value < 13.7563172) || (form.address2longitude.value > 15.8032837)){
   			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Sede Operativa)\r\n";
   				 formTest = false;
   			}		 
  		}
	 }   


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


	if (checkNullString(form.partitaIva.value)){
   	  if (checkNullString(form.codiceFiscale.value)){
       	message += "- Partita IVA/Codice Fiscale richiesto\r\n";
      	 formTest = false;
   	  }
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
      /*if (checkNullString(form.codiceFiscaleCorrentista.value)){
        message += "- Codice ISTAT richiesto\r\n";
        formTest = false;
      }*/
      
      if (form.stageId.value == "-1"){
        message += "- Il Servizio Competente è richiesto\r\n";
        formTest = false;
      }
      
      /*if (checkNullString(form.date2.value)){
        message += "- Data inizio attività richiesta\r\n";
        formTest = false;
      }*/

 
 if (checkNullString(form.codiceFiscaleRappresentante.value)){
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
      if (checkNullString(form.address1line1.value)){
        message += "- Indirizzo sede legale richiesto\r\n";
        formTest = false;
      }

      if (checkNullString(form.address1city.value)){
        message += "- Comune sede legale richiesta\r\n";
        formTest = false;
      }
      if (checkNullString(form.address2line1.value)&&(form.address2line1.disabled==false)){
        message += "- Indirizzo sede operativa richiesto\r\n";
        formTest = false;
      }
	var obj1 = document.getElementById("prov12");
	if((document.getElementById("prov12").disabled == false)){
      if ((obj1.value == -1)){
        message += "- Comune sede operativa richiesta\r\n";
        formTest = false;
      }
     }
      if (checkNullString(form.nomeCorrentista.value)&&(form.nomeCorrentista.disabled==false)){
        message += "- Targa/Codice autoveicolo richiesto\r\n";
        formTest = false;
      }
      
       if (checkNullString(form.address3line1.value)&&(form.address3line1.disabled==false)){
        message += "- Indirizzo attività mobile richiesto\r\n";
        formTest = false;
      }
		var obj = document.getElementById("prov");
		var obj3 = document.getElementById("prov3");
	 if((document.getElementById("prov").disabled == false)){
      if ((obj.value == -1)){
        message += "- Comune attività mobile richiesta\r\n";
        formTest = false;
      }
      
     if (checkNullString(form.address4line1.value)){
        message += "- Indirizzo locale funzionalmente collegato richiesto\r\n";
        formTest = false;
      }
       if ((obj3.value == -1)){
        message += "- Comune locale funzionalmente collegato richiesta\r\n";
        formTest = false;
      }
      if (checkNullString(form.address4zip1.value)){
        message += "- C.A.P. locale funzionalmente collegato richiesto\r\n";
        formTest = false;
      }
      if (checkNullString(form.address4latitude1.value)){
        message += "- Latitudine locale funzionalmente collegato richiesto\r\n";
        formTest = false;
      }
      if (checkNullString(form.address4longitude1.value)){
        message += "- Longitudine locale funzionalmente collegato richiesto\r\n";
        formTest = false;
      }
     
      
     
      
  }
      
      /*      
      if (form.address1state.value == "-1"){
        message += "- Provincia richiesta\r\n";
        formTest = false;
      }
      */
      
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
  
  <dhv:include name="organization.url" none="true">
    if (!checkURL(form.url.value)) {
      message += label("check.url", "- URL entered is invalid.  Make sure there are no invalid characters\r\n");
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

  var states = new Array();
  var initStates = false;
  function resetStateList(country, stateObj) {
    var stateSelect = document.forms['addAccount'].elements['address'+stateObj+'state'];
    var i = 0;
    if (initStates == false) {
      for(i = stateSelect.options.length -1; i > 0 ;i--) {
        var state = new Array(stateSelect.options[i].value, stateSelect.options[i].text);
        states[states.length] = state;
      }
    }
    if (initStates == false) {
      initStates = true;
    }
    stateSelect.options.length = 0;
    for(i = states.length -1; i > 0 ;i--) {
      var state = states[i];
      if (state[0].indexOf(country) != -1 || country == label('option.none','-- None --')) {
        stateSelect.options[stateSelect.options.length] = new Option(state[1], state[0]);
      }
    }
  }
  
  function updateCopyAddress(state){
    copyAddr = document.getElementById("copyAddress");
    if (state == 0){
     copyAddr.checked = false;
     copyAddr.disabled = true;
    } else {
     copyAddr.disabled = false;
    }
  }
</script>
<dhv:evaluate if='<%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();updateFormElements(0);updateFormElementsNew(<%=OrgDetails.getTipoDest() %>);resetCodice();abilita_codici_ateco_vuoti()">
</dhv:evaluate>
<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();updateFormElements(1);updateFormElementsNew(<%=OrgDetails.getTipoDest() %>);resetCodice();abilita_codici_ateco_vuoti()">
</dhv:evaluate>
<form name="addAccount" action="ImpresePregresso.do?command=Insert&auto-populate=true"  onsubmit="return doCheck(this);" method="post">
<%boolean popUp = false;
  if(request.getParameter("popup")!=null){
    popUp = true;
  }%>
  
  <%
	org.aspcfs.utils.web.LookupList lookup_vuota_linea_attivita = new org.aspcfs.utils.web.LookupList();
	lookup_vuota_linea_attivita.addItem(-1, "-- Selezionare prima il codice Ateco --" );
  %>

<!-- ----Da eliminare dopo l'implementazione in DWR del controllo su inserimento di impresa già esistente--	-->
 <%
 	if (request.getAttribute("id_codici_ateco") != null) {
 		request.setAttribute("id_codici_ateco", request.getAttribute("id_codici_ateco"));
 
	 	for (int i : (int[]) request.getAttribute("id_codici_ateco")) {
	 		if (i!=-1)
	 		%>
	 		<input type = "hidden" name = "codici_sel" value="<%=i %>"/>
	 		<%
	 	}
 	}
 
 %>
<input type="hidden" name="id_cc" value="<%=Impresa.getId() %>" />

<dhv:evaluate if="<%= !popUp %>">  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> >
Aggiungi Impresa
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';">
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='ImpresePregresso.do?command=Dettaglio&id=<%=Impresa.getId() %>';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<br /><br />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accounts_add.AddNewAccount">Add a New Account</dhv:label></strong>
    </th>
  </tr>
  <dhv:include name="accounts-sites" none="true">
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
  </dhv:include>
  <dhv:include name="organization.types" none="true">
    <tr>
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="accounts.account.types">Account Type(s)</dhv:label>
      </td>
      <td>
        <table border="0" cellspacing="0" cellpadding="0" class="empty">
          <tr>
            <td>
              <select multiple name="selectedList" id="selectedList" size="5">
              <dhv:evaluate if="<%=OrgDetails.getTypes().isEmpty()%>">
              <option value="-1"><dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label></option>
              </dhv:evaluate>
              <dhv:evaluate if="<%=!(OrgDetails.getTypes().isEmpty())%>">
        <%
                Iterator i = OrgDetails.getTypes().iterator();
                while (i.hasNext()) {
                  LookupElement thisElt = (LookupElement)i.next();
        %>
                <option value="<%=thisElt.getCode()%>"><%=thisElt.getDescription()%></option>
              <%}%>
              </dhv:evaluate>
              </select>
            </td>
            <td valign="top">
              <input type="hidden" name="previousSelection" value="" />
              &nbsp;[<a href="javascript:popLookupSelectMultiple('selectedList','1','lookup_account_types');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </dhv:include>
  
    <%-- %>tr >
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Classification">Tipo DIA</dhv:label>
    </td>
    <td>
      <input type="radio" name="dunsType" value="DIA Semplice" checked>
      <dhv:label name="requestor.requestor_add.Individual">D.I.A. Semplice</dhv:label>
      <input type="radio" name="dunsType" value="DIA Differita" >
      <dhv:label name="requestor.requestor_add.Organization">D.I.A. Differita</dhv:label>
      
    </td>
  </tr--%>
  
  <dhv:include name="accounts-classification" none="true">
  <tr style="display: none">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Classification">Classification</dhv:label>
    </td>
    <td>
      <input type="radio" name="form_type" value="organization" onClick="javascript:updateFormElements(0);" <%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) ? " checked" : "" %>>
      <dhv:label name="accounts.accounts_add.Organization">Organization</dhv:label>
      <input type="radio" name="form_type" value="individual" onClick="javascript:updateFormElements(1);" <%= "individual".equals((String) request.getParameter("form_type")) ? " checked" : "" %>>
      <dhv:label name="accounts.accounts_add.Individual">Individual</dhv:label>
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="accounts-name" none="true">
  <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      <dhv:label name="accounts.accounts_add.OrganizationName">Organization Name</dhv:label>
    </td>
    <td>
    <% String ragione =  OrgDetails.getName()+" "+OrgDetails.getBanca(); %>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(ragione) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
  </dhv:include>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Denominazione</dhv:label>
    </td>
    <td>
      <input type="text" size="50" maxlength="80" name="banca" value="<%= toHtmlValue(OrgDetails.getBanca()) %>">
    </td>
  </tr>
  <dhv:include name="organization.accountNumber" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="organization.codice_impresa_interno">Account Number</dhv:label>
      </td>
      <td>
        <input type="text" onmouseover="ddrivetip('<dhv:label name="">Campo facoltativo. Da utilizzarsi per inserire dati di Aut. San. o di un Certif. San. o di una registrazione eseguita precedentemente senza il sistema GISA.</dhv:label>')"
      			onmouseout="hideddrivetip()" size="30" name="codiceImpresaInterno" maxlength="30" value="<%= toHtmlValue(OrgDetails.getCodiceImpresaInterno()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="account-salutation" none="true">
    <tr>
      <td id="listSalutation1" name="listSalutation1" nowrap class="formLabel">
        <dhv:label name="accounts.accounts_contacts_add.Salutation">Salutation</dhv:label>
      </td>
      <td>
        <% SalutationList.setJsEvent("onchange=\"javascript:fillSalutation('addAccount');\"");%>
        <% SalutationList.setJsEvent("onFocus=\"if (orgSelected == 1) { tabNext(this) }\"");%>
        <%= SalutationList.getHtmlSelect("listSalutation",OrgDetails.getNameSalutation()) %>
        <input type="hidden" size="35" name="nameSalutation" value="<%= toHtmlValue(OrgDetails.getNameSalutation()) %>">
      </td>
    </tr>
  </dhv:include>

  <dhv:include name="accounts-firstname" none="true">
    <tr>
      <td name="nameFirst1" id="nameFirst1" nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label>
      </td>
      <td>
        <input onFocus="if (orgSelected == 1) { tabNext(this) }" type=text size="35" name="nameFirst" value="<%= toHtmlValue(OrgDetails.getNameFirst()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="accounts-middlename" none="true">
    <tr>
      <td name="nameMiddle1" id="nameMiddle1" nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.MiddleName">Middle Name</dhv:label>
      </td>
      <td>
        <input onFocus="if (orgSelected == 1) { tabNext(this) }" type=text size="35" name="nameMiddle" value="<%= toHtmlValue(OrgDetails.getNameMiddle()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="accounts-lastname" none="true">
    <tr>
      <td name="nameLast1" id="nameLast1" nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label>
      </td>
      <td>
        <input onFocus="if (orgSelected == 1) { tabNext(this) }" type="text" size="35" name="nameLast" value="<%= toHtmlValue(OrgDetails.getNameLast()) %>"><font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
      </td>
    </tr>
  </dhv:include>
  <tr>
    <td class="formLabel" nowrap>
      Partita IVA
    </td>
    <td>
      <input type="text" size="20" maxlength="11" name="partitaIva" value="<%= toHtmlValue(OrgDetails.getPartitaIva()) %>">
    </td>
  </tr>
  <!-- modificato da d.dauria -->
  <tr >
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="20" maxlength="16" name="codiceFiscale" value="<%= toHtmlValue(OrgDetails.getCodiceFiscale()) %>">    
    </td>
  </tr>
  <!-- fine modifica -->
  <tr class="containerBody">
	  <td class="formLabel" nowrap>
       		<dhv:label name="">Codice Ateco/Linea di Attivita Principale</dhv:label>
	  </td>
	
	  <td>
<%-- 	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="codiceFiscaleCorrentista" name="codiceFiscaleCorrentista" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleCorrentista()) %>"><font color="red">*</font>	--%>
	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="codiceFiscaleCorrentista" name="codiceFiscaleCorrentista" value="" onchange="costruisci_rel_ateco_attivita('codiceFiscaleCorrentista', 'id_rel_principale' );"   ><font color="red">*</font>
	  &nbsp;[<a href="javascript:popLookupSelectorCustomImprese('codiceFiscaleCorrentista','alertText','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Seleziona</dhv:label></a>]
	  
	  <br/><%= lookup_vuota_linea_attivita.getHtmlSelect("id_rel_principale", -1 ) %>
	  
	</td>
  </tr>
  
  <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.AlertDescription">Alert Description</dhv:label>
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
      		<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice1" name="codice1" 
      			   onchange="costruisci_rel_ateco_attivita('codice1',  'id_rel_1' ); abilita_codici_ateco_vuoti();"	value="" >
      		[<a href="javascript:popLookupSelectorCustomImprese('codice1','cod1', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod1" name="cod1" value="" >
      		<br/><%
					out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_1" , -1 ) ) );
					
			%><br/>
			<br></br>
        	
      		 <div id="div_codice1" style="display: none">
      		 	<b>Codice 2&nbsp;&nbsp;</b>
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice2" name="codice2" 
      		 		onchange="costruisci_rel_ateco_attivita('codice2',  'id_rel_2' ); abilita_codici_ateco_vuoti();" value="" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice2','cod2', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod2" name="cod2" value="" >
      		 	<br/><%
						out.println(lookup_vuota_linea_attivita.getHtmlSelect("id_rel_2", -1 ));
				%><br/>
				<br></br>
      		 </div>
      		 
      		 <div id="div_codice2" style="display: none">
      		 	<b>Codice 3&nbsp;&nbsp;</b>
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice3" name="codice3" 
      		    	onchange="costruisci_rel_ateco_attivita('codice3',  'id_rel_3' ); abilita_codici_ateco_vuoti();" value="" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice3','cod3', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod3" name="cod3" value="" >
      		    <br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_3" , -1 ) ) );
				%><br/>
				<br></br>
      		 </div>
      		 
      		 <div id="div_codice3" style="display: none">
      		 	<b>Codice 4&nbsp;&nbsp;</b>
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice4" name="codice4" 
      		 		onchange="costruisci_rel_ateco_attivita('codice4',  'id_rel_4' ); abilita_codici_ateco_vuoti();" value="" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice4','cod4', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod4" name="cod4" value="" >
      		 	<br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_4" , -1 ) ) );
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice4" style="display: none">
      		 	<b>Codice 5&nbsp;&nbsp;</b>
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice5" name="codice5" 
      		 		onchange="costruisci_rel_ateco_attivita('codice5',  'id_rel_5' ); abilita_codici_ateco_vuoti();" value="" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice5', 'cod5','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod5" name="cod5" value="" >
      		    <br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_5" , -1 ) ) );
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice5" style="display: none">
      		 	<b>Codice 6&nbsp;&nbsp;</b>
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice6" name="codice6" 
      		 		onchange="costruisci_rel_ateco_attivita('codice6',  'id_rel_6' ); abilita_codici_ateco_vuoti();" value="" >
      		  	[<a href="javascript:popLookupSelectorCustomImprese('codice6','cod6', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod6" name="cod6" value="" >
      		  	<br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_6" , -1 ) ) );
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice6" style="display: none">
      		 	<b>Codice 7&nbsp;&nbsp;</b>
      		  	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice7" name="codice7" 
      		  		onchange="costruisci_rel_ateco_attivita('codice7',  'id_rel_7' ); abilita_codici_ateco_vuoti();" value="" >
      		  	[<a href="javascript:popLookupSelectorCustomImprese('codice7','cod7', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod7" name="cod7" value="" >
      		  	<br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_7" , -1 ) ) );
				%><br/>
				<br></br>
      		 </div>	

      		 <div id="div_codice7" style="display: none">
      		 	<b>Codice 8&nbsp;&nbsp;</b>
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice8" name="codice8" 
      		 		onchange="costruisci_rel_ateco_attivita('codice8',  'id_rel_8' ); abilita_codici_ateco_vuoti();" value="" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice8', 'cod8','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod8" name="cod8" value="" >
      		 	<br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_8" , -1 ) ) );
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice8" style="display: none">
      		 	<b>Codice 9&nbsp;&nbsp;</b>
         	    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice9" name="codice9" 
         	    	onchange="costruisci_rel_ateco_attivita('codice9',  'id_rel_9' ); abilita_codici_ateco_vuoti();" value="" >
         	    [<a href="javascript:popLookupSelectorCustomImprese('codice9','cod9', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
         	    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod9" name="cod9" value="" >
         	    <br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_9" , -1 ) ) );
				%><br/>
				<br></br>	
         	 </div>
      		 
			<div id="div_codice9" style="display: none">
      		 	<b>Codice 10</b>
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice10" name="codice10"
      		    	onchange="costruisci_rel_ateco_attivita('codice10', 'id_rel_10'); abilita_codici_ateco_vuoti();" value="" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice10', 'cod10','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod10" name="cod10" value="" >
      		    <br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_10" , -1 ) ) );
				%><br/>
				<br></br>	
        	 </div>
        	 <br><br>
        	 [ <a href="javascript:resetCodiciIstatSecondari()">Elimina codici istat </a> ]
      </td>
  </tr>
  <dhv:include name="organization.industry" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.Industry">Industry</dhv:label>
      </td>
      <td>
        <%= IndustryList.getHtmlSelect("industry",OrgDetails.getIndustry()) %>
      </td>
    </tr>
  </dhv:include>
  <%--tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Tipo Autoveicolo</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="contoCorrente" value="<%= toHtmlValue(OrgDetails.getContoCorrente()) %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Targa Autoveicolo</dhv:label>
    </td>
    <td>
      <input type="text" size="20" maxlength="10" name="nomeCorrentista" value="<%= toHtmlValue(OrgDetails.getNomeCorrentista()) %>">
    </td>
  </tr>
  
   <tr id="list3" style="display: none">
    <td class="formLabel" nowrap  id="codiceCont1">
      <dhv:label name="">Codice Contenitore</dhv:label>
    </td>
    <td>
      <input id="codiceCont" type="text" size="20" maxlength="20" name="codiceCont" value="<%= toHtmlValue(OrgDetails.getCodiceCont()) %>">
    </td--%>
  <tr>
    <td nowrap class="formLabel">
        <dhv:label name="contact.sources">  Attivit&agrave;</dhv:label>
      </td>
	<td>
      <input type="radio" id="tipoD" name="tipoDest" value="Es. Commerciale" onClick="javascript:updateFormElementsNew('Es. Commerciale');"<%if(OrgDetails.getTipoDest()==null){ %> checked<%}else { if(OrgDetails.getTipoDest().equals("Es. Commerciale")) {%> checked <%}} %>>
      <!-- Es. Commerciale-->Fissa
      <input type="radio" id="tipoD2" name="tipoDest" value="Autoveicolo" onClick="javascript:updateFormElementsNew('Autoveicolo');" <%if(OrgDetails.getTipoDest()!=null && OrgDetails.getTipoDest().equals("Autoveicolo")){ %> checked="checked" <%} %> >Mobile
      
      <input type="radio" id="tipoD2" name="tipoDest" value="Distributori" <%if(OrgDetails.getTipoDest()!=null){ if(OrgDetails.getTipoDest()!=null && OrgDetails.getTipoDest().equals("Distributori")){ %> checked <%}}%>  onClick="javascript:abilitaDistributoriCampi();">Distributori
   
      <!-- Autoveicolo
       <input type="radio" name="tipoDest" value="Contenitore" onClick="javascript:updateFormElementsNew(2);" >
      Contenitore-->
      <input type="hidden" name="orgType" value="" />

      <input type="hidden" name="check" />
      </td>
      </tr>
  <dhv:include name="organization.source" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="contact.source">Source</dhv:label>
      </td>
      <td>
    
      
      	<table border="0">
        	<tr>
        
        <td>
          <%	SourceList.setJsEvent("onChange=\"javascript:selectCarattere('source');\"");
        %>
        <%= SourceList.getHtmlSelect("source",OrgDetails.getSource())%>
        </td>
        <td style="visibility: hidden;" id="data1">
        		Dal
        	</td>
        	<td style="visibility: hidden;" id="data3">
           		<zeroio:dateSelect form="addAccount" field="dateI" timestamp="<%= OrgDetails.getDateI() %>" showTimeZone="false" /><font color="red">*</font>
          	</td>
       
       	 	
           	<td style="visibility: hidden;" id="data2">
           		Al
           	</td>
           
            	<td style="visibility: hidden;" id="data4">
           		<zeroio:dateSelect form="addAccount" field="dateF" timestamp="<%= OrgDetails.getDateF() %>" showTimeZone="false" /><font color="red">*</font>
           	</td>
           	<td style="visibility: hidden;" id="cessazione">
           	<input type="checkbox" name="cessazione" value ="true"  checked /> <dhv:label name="accounts.Assetsf">Cessazione Automatica</dhv:label>
           	</td>
          
    </tr>
       </table>
        </td>
          	
        
        
        
        
     
        
        
     <!--   </td> -->
    </tr>
  </dhv:include>
    
    
  
  
  
  
  
    <%--dhv:include name="organization.date1" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.date1">Data1</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="date1" timestamp="<%= OrgDetails.getDate1() %>" showTimeZone="false" />
        <%= showAttribute(request, "date1Error") %>
      </td>
    </tr>
  </dhv:include--%>
  <dhv:include name="organization.rating" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="sales.rating">Rating</dhv:label>
      </td>
      <td>
        <%= RatingList.getHtmlSelect("rating",OrgDetails.getRating()) %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.url" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.WebSiteURL">Web Site URL</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="url" value="<%= toHtmlValue(OrgDetails.getUrl()) %>">
      </td>
    </tr>
  </dhv:include>

  <dhv:include name="organization.dunsType" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.duns_type">DUNS Type</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="dunsType" maxlength="300" value="<%= toHtmlValue(OrgDetails.getDunsType()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.yearStarted" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.year_started">Year Started</dhv:label>
      </td>
      <td>
        <input type="text" size="10" name="yearStarted" value="<%= OrgDetails.getYearStarted() > -1 ? String.valueOf(OrgDetails.getYearStarted()) : "" %>">
        <%= showAttribute(request, "yearStartedWarning") %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.employees" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="organization.employees">No. of Employees</dhv:label>
      </td>
      <td>
        <input type="text" size="10" name="employees" value='<%= OrgDetails.getEmployees() == 0 ? "" : String.valueOf(OrgDetails.getEmployees()) %>'>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.revenue" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.Revenue">Revenue</dhv:label>
      </td>
      <td>
        <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
        <input type="text" name="revenue" size="15" value="<zeroio:number value="<%= OrgDetails.getRevenue() %>" locale="<%= User.getLocale() %>" />">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.potential" none="true">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Potential">Potential</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="potential" size="15" value="<zeroio:number value="<%= OrgDetails.getPotential() %>" locale="<%= User.getLocale() %>" />">
    </td>
  </tr>
  </dhv:include>
  <dhv:include name="organization.ticker" none="true">
    <tr>
      <td name="ticker1" id="ticker1" nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.TickerSymbol">Ticker Symbol</dhv:label>
      </td>
      <td>
        <input onFocus="" type="text" size="10" maxlength="10" name="ticker" value="<%= toHtmlValue(OrgDetails.getTicker()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.dunsNumber" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.duns_number">DUNS Number</dhv:label>
      </td>
      <td>
        <input type="text" size="15" name="dunsNumber" maxlength="30" value="<%= toHtmlValue(OrgDetails.getDunsNumber()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.businessNameTwo" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.business_name_two">Business Name 2</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="businessNameTwo" maxlength="300" value="<%= toHtmlValue(OrgDetails.getBusinessNameTwo()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.sicCode" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.sic_code">SIC</dhv:label>
      </td>
      <td>
        <%= SICCodeList.getHtmlSelect("sicCode",OrgDetails.getSicCode()) %>
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="organization.sicDescription" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.sicDescription">SIC Description</dhv:label>
      </td>
      <td>
        <input type="text" size="50" name="sicDescription" maxlength="300" value="<%= toHtmlValue(OrgDetails.getSicDescription()) %>">
      </td>
    </tr>
  </dhv:include>
  <dhv:include name="accounts-directbill" none="true">
    <dhv:permission name="accounts-directbill-edit">
      <tr>
        <td nowrap class="formLabel">
          <dhv:label name="accounts.accounts_add.directBill">Direct Bill</dhv:label>
        </td>
        <td>
          <input type="checkbox" name="directBill" Direct Bill>
        </td>
      </tr>
    </dhv:permission>
  </dhv:include>
    
   <tr style="display: none">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="alertDate" timestamp="<%= OrgDetails.getAlertDate() %>" timeZone="<%= OrgDetails.getAlertDateTimeZone() %>" showTimeZone="false" /><font color="red">*</font>
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data Presentazione D.I.A./Inizio Attività</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="dataPresentazione" timestamp="<%= OrgDetails.getDataPresentazione() %>" showTimeZone="false" />
        <%= showAttribute(request, "date1Error") %>
      </td>
    </tr>
    <%--<tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data inizio attività</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="date2" timestamp="<%= OrgDetails.getDate2() %>" showTimeZone="false" />
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
  <%--dhv:include name="organization.contractEndDate" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.ContractEndDate">Contract End Date</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="addAccount" field="contractEndDate" timestamp="<%= OrgDetails.getContractEndDate() %>" timeZone="<%= OrgDetails.getContractEndDateTimeZone() %>" showTimeZone="false" /><%= showError(request, "dataFineAttivitaError") %>
      </td>
    </tr>
  </dhv:include>
  <tr>
    <td name="accountSize1" id="accountSize1" nowrap class="formLabel">
      <dhv:label name="osa.categoriaRischio"/>
    </td>
    <td>
      <%= OrgCategoriaRischioList.getHtmlSelect("accountSize",OrgDetails.getAccountSize()) %>
    </td>
  </tr--%>
  
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
      <%--input type="hidden" name="address1type" value="1"--%>
    </th>
  </tr>
  
    <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Titolo</dhv:label>
    </td>
    <td>  <!-- titoloRappresentante è il nome della variabile nel bean -->
       <%= TitoloList.getHtmlSelect("titoloRappresentante",OrgDetails.getTitoloRappresentante()) %></td>
    </tr>
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
        <zeroio:dateSelect form="addAccount" field="dataNascitaRappresentante" timestamp="<%= OrgDetails.getDataNascitaRappresentante() %>"  showTimeZone="false" />
        <%= showAttribute(request, "alertDateError") %>
        <%= showWarningAttribute(request, "alertDateWarning") %>
        
      </td>
    </tr>
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Luogo Nascita</dhv:label>
    </td>
    <td>
      <input type="text" size="30" maxlength="50" name="luogoNascitaRappresentante" value="<%= toHtmlValue(OrgDetails.getLuogoNascitaRappresentante()) %>">
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
  boolean noneSelected = false;
%>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Sede Legale</strong>
	    <input type="hidden" name="address1type" value="1">
	  </th>
  <tr>
	<td nowrap class="formLabel" name="province" id="province">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
    <input type="text" name="address1city" id="address1city" value = "<%=toHtmlValue(AddressLegale.getCity()) %>">
	
	<font color="red">*</font>
	</td>
  	</tr>	
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address1line1" maxlength="80" value="<%= toHtmlValue(AddressLegale.getStreetAddressLine1()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="">C/O</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address1line2" maxlength="80" value = "<%=toHtmlValue(AddressLegale.getStreetAddressLine2()) %>">
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address1zip" maxlength="12" value = "<%=toHtmlValue(AddressLegale.getZip()) %>">
    </td>
  </tr>
  
  <dhv:include name="address.country" none="true">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Country">Country</dhv:label>
    </td>
    <td>
    <% CountrySelect.setJsEvent("onChange=\"javascript:update('address1country', '1','');\"");%>
    <%= CountrySelect.getHtml("address1country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
    <%
      CountrySelect = new CountrySelect(systemStatus);
     %>
    </td>
  </tr>
  </dhv:include>
</table>

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
      <input type="text" size="40" name="address2line1" maxlength="80" id="indirizzo12" value="<%= toHtmlValue(AddressSedeOperativa.getStreetAddressLine1()) %>"><font color="red">*</font>
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
          <% if (User.getSiteId() == 3) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="BN"><font color="red">*</font>          
          <%}%>
          <% if (User.getSiteId() == 1 || User.getSiteId() == 2) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="AV"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 4 || User.getSiteId() == 5) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="CE"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 6 || User.getSiteId() == 7 || User.getSiteId() == 8 || User.getSiteId() == 9 || User.getSiteId() == 10) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="NA"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 11 || User.getSiteId() == 12 || User.getSiteId() == 13) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="SA"><font color="red">*</font>
          <%}%>
    </td>
  </tr>
  
  	
  
  <!--  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state11" ID="state11" style='<%= StateSelect.hasCountry(applicationPrefs.get("SYSTEM.COUNTRY")) ? "" : " display:none" %>'>
        <%= StateSelect.getHtmlSelect("address1state",applicationPrefs.get("SYSTEM.COUNTRY")) %>
      </span><font color="red">*</font>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state21" ID="state21" style='<%= !StateSelect.hasCountry(applicationPrefs.get("SYSTEM.COUNTRY")) ? "" : " display:none" %>'>
        <input type="text" size="25" name='<%= "address1otherState" %>' >
      </span>
    </td>
  </tr>
  -->
  
  <dhv:include name="address.country" none="true">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Country">Country</dhv:label>
    </td>
    <td>
    <% CountrySelect.setJsEvent("onChange=\"javascript:update('address1country', '1','');\"");%>
    <%= CountrySelect.getHtml("address1country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
    <%
      CountrySelect = new CountrySelect(systemStatus);
     %>
    </td>
  </tr>
  </dhv:include>
  <!--  
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.county">County</dhv:label></td>
    <td><input type="text" name="address1county" size="28" maxlenth="80"></td>
  </tr>
  -->
  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude2"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" id="address2latitude" name="address2latitude" size="30" value="<%=AddressSedeOperativa.getLatitude() %>" id="">
    	<%--input type="button"
			onclick="javascript:Geocodifica.getCoordinate( document.forms[0].address2line1.value,document.forms[0].address2city.value,document.forms[0].address2state.value,'','address2latitude','address2longitude','address2coordtype',setGeocodedLatLon)"
			value="<dhv:label name="geocodifica.calcola">Calcola</dhv:label>"
		/>
    	<input type="hidden" name="address2coordtype" id="address2coordtype" value="0" /--%>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">
  </tr>
  
 
  
</table>
<br><br>

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
  
  <%-- <tr id="list3">
    <td class="formLabel" nowrap  id="codiceCont1">
      <dhv:label name="">Codice Contenitore</dhv:label>
    </td>
    <td>
      <input id="codiceCont" type="text" size="20" maxlength="20" name="codiceCont" value="<%= toHtmlValue(OrgDetails.getCodiceCont()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
    </td>
  </tr>--%>
 <tr>
	<td nowrap class="formLabel" name="province" id="prov1">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
	<select  name="address3city" id="prov" >
	<option value="-1"><dhv:label name="requestor.requestor_add.NoneSelected">None Selected</dhv:label></option>
            
	 <%
                Vector v6 = OrgDetails.getComuni2();
	 			Enumeration e6=v6.elements();
                while (e6.hasMoreElements()) {
                	String prov6=e6.nextElement().toString();
                  
        %>
                <option value="<%=prov6%>" <%if(prov6.equalsIgnoreCase(AddressSedeMobile.getCity())){ %>selected="selected"<%} %>><%= prov6 %></option>	
              <%}%>
		
	</select> 
	<font color="red">*</font>
	</td>
  	</tr>
  <tr>
    <td nowrap class="formLabel" id="addressLine">
      Indirizzo
    </td>
    <td>
      <input type="text" size="40" name="address3line1" maxlength="80" id="addressline1" value="<%= toHtmlValue(AddressSedeMobile.getStreetAddressLine1()) %>"><font color="red">*</font>
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel" id="labelCap">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" name="address3zip" value ="<%=AddressSedeMobile.getZip() %>" maxlength="12" id="addresszip">
    </td>
  </tr>
  	
  	 
  	<tr>
    <td nowrap class="formLabel" id="stateProv1">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	  <%--if(OrgDetails.getTipoDest().equals("Autoveicolo")){}else{ --%>
          <% if (User.getSiteId() == 3) { %>
          <input type="text" readonly="readonly" size="28" name="address3state" maxlength="80" value="BN"><font color="red">*</font>          
          <%}%>
          <% if (User.getSiteId() == 1 || User.getSiteId() == 2) { %>
          <input type="text" readonly="readonly" size="28" name="address3state" maxlength="80" value="AV"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 4 || User.getSiteId() == 5) { %>
          <input type="text" readonly="readonly" size="28" name="address3state" maxlength="80" value="CE"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 6 || User.getSiteId() == 7 || User.getSiteId() == 8 || User.getSiteId() == 9 || User.getSiteId() == 10) { %>
          <input type="text" readonly="readonly" size="28" name="address3state" maxlength="80" value="NA"><font color="red">*</font>
          <%}%>
          <% if (User.getSiteId() == 11 || User.getSiteId() == 12 || User.getSiteId() == 13) { %>
          <input type="text" readonly="readonly" size="28" name="address3state" maxlength="80" value="SA"><font color="red">*</font>
          <%}%>
    </td>
  </tr>
  
  	
  
  <!--  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state11" ID="state11" style='<%= StateSelect.hasCountry(applicationPrefs.get("SYSTEM.COUNTRY")) ? "" : " display:none" %>'>
        <%= StateSelect.getHtmlSelect("address1state",applicationPrefs.get("SYSTEM.COUNTRY")) %>
      </span><font color="red">*</font>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state21" ID="state21" style='<%= !StateSelect.hasCountry(applicationPrefs.get("SYSTEM.COUNTRY")) ? "" : " display:none" %>'>
        <input type="text" size="25" name='<%= "address1otherState" %>' >
      </span>
    </td>
  </tr>
  -->
  
  <dhv:include name="address.country" none="true">
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Country">Country</dhv:label>
    </td>
    <td>
    <% CountrySelect.setJsEvent("onChange=\"javascript:update('address1country', '1','');\"");%>
    <%= CountrySelect.getHtml("address1country",applicationPrefs.get("SYSTEM.COUNTRY")) %>
    <%
      CountrySelect = new CountrySelect(systemStatus);
     %>
    </td>
  </tr>
  </dhv:include>
  <!--  
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.county">County</dhv:label></td>
    <td><input type="text" name="address1county" size="28" maxlenth="80"></td>
  </tr>
  -->
  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude1"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" id="address3latitude" name="address3latitude" size="30" value="<%=AddressSedeMobile.getLatitude() %>" id="latitude">
    	<%-- %>input type="button"
			onclick="javascript:Geocodifica.getCoordinate( document.forms[0].address3line1.value,document.forms[0].address3city.value,document.forms[0].address3state.value,'','address3latitude','address3longitude','address3coordtype',setGeocodedLatLon)"
			value="<dhv:label name="geocodifica.calcola">Calcola</dhv:label>"
		/>
    	<input type="hidden" name="address3coordtype" id="address3coordtype" value="0" /--%>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude1"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" id="address3longitude" name="address3longitude" size="30" value="<%=AddressSedeMobile.getLongitude() %>" id="longitude"></td>
  </tr>
  
 
  
</table>
<br><br>

<dhv:include name="organization.addresses" none="true">
<%-- Addresses --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="requestor.requestor_add.Addresses1">Locale Funzionalmente collegato</dhv:label></strong>
    </th>
  </tr>
  
  <%
  int acount = 0;
    noneSelected = false;
  
  %>
  <dhv:evaluate if="<%= (OrgDetails.getPrimaryContact() == null) %>">
<%
  
  Iterator anumber = OrgDetails.getAddressList().iterator();
  if(anumber.hasNext()){
  while (anumber.hasNext()) {
	  
    
    OrganizationAddress thisAddress = (OrganizationAddress)anumber.next();
    if(thisAddress.getType()==6)
    {
    	
    
    	if(!thisAddress.getCity().equals("") || !thisAddress.getStreetAddressLine1().equals(""))

    	{
    		acount =acount+1;
    	
    
%>
 <tr class="containerBody">
    <input type="hidden" name="address<%= acount %>id" value="<%= thisAddress.getId() %>">
   
    
     <tr>
      <td nowrap class="formLabel" id="tipoStruttura1">
        <dhv:label name="contact.fsource">Tipo locale</dhv:label>
 		<input type="hidden" name="address4type<%=acount %>" value="6">
      </td>
    <td>
    <%if(acount ==1)
    	{
    	%>
        <%= TipoLocale.getHtmlSelect("TipoLocale",OrgDetails.getTipoLocale())%>
      <%}else
    	  {
    	  if(acount ==2){
    	  
    	  %>
    	   <%= TipoLocale.getHtmlSelect("TipoLocale2",OrgDetails.getTipoLocale2())%>
    	  <%
    	  }
    	  else
    	  {
    		  if(acount ==3){
    	    	  
    	    	  %>
    	    	   <%= TipoLocale.getHtmlSelect("TipoLocale3",OrgDetails.getTipoLocale3())%>
    	    	  <%
    	    	  }
    	  }
    	  }
    	  %>
      
      </td>
     
     
    
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td>
    <td>
    
     <%if(acount ==1)
    	{
    	%>
            <input type="text" size="28" id="address4city<%=acount %>" name="address4city1" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>">

      <%}else
    	  {
    	  if(acount ==2){
    	  
    	  %>
    	     <input type="text" size="28" id="address4city<%=acount %>" name="address4city2" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>">

    	  <%
    	  }
    	  else
    	  {
    		  if(acount ==3){
    	    	  
    	    	  %>
    	    	     <input type="text" size="28" id="address4city<%=acount %>" name="address4city3" maxlength="80" value="<%= toHtmlValue(thisAddress.getCity()) %>">

    	    	  <%
    	    	  }
    	  }
    	  }
    	  %>
      </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
   <% if(acount==1) { %>
         <input type="text" size="40" name="address4line1" maxlength="80" value="<%= toHtmlValue(Address.getStreetAddressLine1()) %>">
   
   <%} 
   else
   {
   %>
         <input type="text" size="40" name="address4line1<%=acount %>" maxlength="80" value="<%= toHtmlValue(Address.getStreetAddressLine1()) %>">
   
   <%} %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="address4zip<%=acount %>" maxlength="12" value="<%= toHtmlValue(thisAddress.getZip()) %>  ">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
      <span name="state1<%= acount %>" ID="state1<%= acount %>" style='<%= StateSelect.hasCountry(thisAddress.getCountry())? "" : " display:none" %>'>
      </span>
      <%-- If selected country is not US/Canada use textfield --%>
      <span name="state2<%= acount %>" ID="state2<%= acount %>" style='<%= !StateSelect.hasCountry(thisAddress.getCountry()) ? "" : " display:none" %>'>
        <input type="text" size="25" name='<%= "address4state" + acount  %>'  value="<%= toHtmlValue(thisAddress.getState()) %>">
      </span>
    </td>
  </tr>
  
  <dhv:include name="address.country" none="true">
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.Country">Country</dhv:label>
    </td>
    <td>
      <% CountrySelect.setJsEvent("onChange=\"javascript:update('address" + acount + "country', '" + acount + "','"+ thisAddress.getState()+"');\"");%>
      <%= CountrySelect.getHtml("address" + acount + "country", thisAddress.getCountry()) %>
      <%
        CountrySelect = new CountrySelect(systemStatus);
      %>
    </td>
  </tr>
  </dhv:include>
  <!--  
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="accounts.address.county">County</dhv:label></td>
    <td><input type="text" name="address<%= acount %>county" size="28" maxlenth="80" value="<%= toHtmlValue(thisAddress.getCounty()) %>"></td>
  </tr>
  -->
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" id="address4latitude<%=acount %>" name="address4latitude<%=acount %>" size="30" value='<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? "" + thisAddress.getLatitude() : "") %>'> 
    	<%--input type="button"
			onclick="javascript:Geocodifica.getCoordinate( document.forms[0].address4line1.value,document.forms[0].address4city.value,document.forms[0].address4state.value,'','address4latitude','address4longitude','address4coordtype',setGeocodedLatLon)"
			value="<dhv:label name="geocodifica.calcola">Calcola</dhv:label>"
		/>
    	<input type="hidden" name="address4coordtype" id="address4coordtype" value="0" /--%>
   </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" id="address4longitude<%=acount %>" name="address4longitude<%=acount %>" size="30" value='<%= ((thisAddress.getLatitude() != 0.0 || thisAddress.getLongitude() != 0.0) ? "" + thisAddress.getLongitude() : "") %>'></td>
  </tr>
  <tr class="containerBody">
    <td colspan="2">
      &nbsp;
    </td>
  </tr>
<%
  }}
  }
  if(acount ==1 )
  {
	  %>
	  
	   <tr class="containerBody" id="indirizzo2button">
	    <td class="formLabel" nowrap></td>
	    <td><input type="button" onClick="mostraNextIndirizzo(2)" value="Aggiungi altro Indirizzo" ></td>
	  </tr>
	  <%
  }
  
  if(acount ==2 )
  {
	  %>
	  
	  <tr class="containerBody" style="display:true" id="indirizzo3button">
	    <td class="formLabel" nowrap></td>
	    <td><input type="button" onClick="mostraNextIndirizzo(3)" value="Aggiungi Altro Indirizzo" > </td>
	  </tr>
	  <%
  }
  
  
%>
 
  
<%
  }
%>
  </dhv:evaluate>

<%


if(acount==0 ){%>


  <%
    Iterator addressTypeIterator = OrgAddressTypeList.iterator();
  %>

 
  
  
 
  <dhv:include name="accounts-address3" none="true">

 		<tr>
      <td nowrap class="formLabel" id="tipoStruttura1">
        <dhv:label name="contact.fsource">Tipo locale</dhv:label>
 		<input type="hidden" name="address4type1" value="6">
      </td>
    <td>
        <%= TipoLocale.getHtmlSelect("TipoLocale",OrgDetails.getTipoLocale())%>
      </td>
     
     
    
  </tr>
    <tr>
	<td nowrap class="formLabel" name="province3" id="province3">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
    <input type="text" name="address4city1" id="prov3" >
	
	<font color="red" id="starMobil4">*</font>
	</td>
  	</tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
      </td>
      <td>
        <input type="text" size="40" name="address4line1" maxlength="80" value="<%= toHtmlValue(AddressLocale1.getStreetAddressLine4()) %>">
      <font color="red" id="starMobil3">*</font></td>
    </tr>
    

  	<tr>
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
     </td>
      <td>
        <input type="text" size="28" name="address4zip1" maxlength="12">
      <font color="red" id="starMobil5">*</font></td>
    </tr>
  	 
  	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	  <input type="text" size="28" name="address4state1" maxlength="80" value="<%= toHtmlValue(AddressLocale1.getState()) %>">          
          
    <font id="starMobil10" color="red">*</font></td>
  </tr>   
    
    <dhv:include name="address.country" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.Country">Country</dhv:label>
      <font color="red" id="starMobil6">*</font>
      </td>
      <td>
        <% CountrySelect.setJsEvent("onChange=\"javascript:update('address3country', '3','');\"");%>
        <%= CountrySelect.getHtml("address2country", applicationPrefs.get("SYSTEM.COUNTRY")) %>
        <%
          CountrySelect = new CountrySelect(systemStatus);
         %>
      <font color="red" id="starMobil7">*</font></td>
    </tr>
    </dhv:include>
    
	  <tr class="containerBody">
	    <td class="formLabel" nowrap><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
	    <td>
	    	<input type="text" id="address4latitude" name="address4latitude1" size="30" value=""><font color="red" id="starMobil8">*</font>
	       	<%--input type="button"
				onclick="javascript:Geocodifica.getCoordinate( document.forms[0].address4line1.value,document.forms[0].address4city.value,document.forms[0].address4state.value,'','address4latitude','address4longitude','address4coordtype',setGeocodedLatLon)"
				value="<dhv:label name="geocodifica.calcola">Calcola</dhv:label>"
			/>
    	<input type="hidden" name="address4coordtype" id="address4coordtype" value="0" /--%>
	    </td>
	  </tr>
	  <tr class="containerBody">
	    <td class="formLabel" nowrap><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
	    <td><input type="text" id="address4longitude" name="address4longitude1" size="30" value=""><font color="red" id="starMobil9">*</font></td>
	  </tr>
	  <tr class="containerBody" id="indirizzo2button">
	    <td class="formLabel" nowrap></td>
	    <td><input type="button" onClick="mostraNextIndirizzo(2)" value="Aggiungi altro Indirizzo" ></td>
	  </tr>
  </dhv:include> 
  <%} %>
  
  
  
 
 
 
 <%if(acount==1 || acount==0){ %>
 
  <dhv:include name="accounts-address3" none="true" >
    <tr style="display:none" id="indirizzo26">
	<td nowrap class="formLabel" name="province3" id="province3">
     <b>Indirizzo 2</b>
    </td> 
    <td >
	</td>
  	</tr>
  	  <tr style="display:none" id="indirizzo21">
      <td nowrap class="formLabel" id="tipoStruttura2">
        <dhv:label name="contact.fsource">Tipo locale</dhv:label>
 		<input type="hidden" name="address4type2" value="6">
      </td>
    
      <td>
        <%= TipoLocale.getHtmlSelect("TipoLocale2",OrgDetails.getTipoLocale2())%>
      </td>
     </tr>
    <tr style="display:none" id="indirizzo22">
	<td nowrap class="formLabel" name="province3" id="province3">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
    <input type="text" name="address4city2" id="prov32" >
	
	
	</td>
  	</tr>
    <tr style="display:none" id="indirizzo23">
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
      </td>
      <td>
        <input type="text" size="40" name="address4line12" maxlength="80" value="<%= toHtmlValue(Address.getStreetAddressLine4()) %>">
     </td>
    </tr>
  <%//GIUSEPPE %>  
  	<tr style="display:none" id="indirizzo24">
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
     </td>
      <td>
        <input type="text" size="28" name="address4zip2" maxlength="12">
    </td>
    </tr>
  	 
  	<tr style="display:none" id="indirizzo25">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	  <input type="text" size="28" name="address4state2" maxlength="80" value="<%= toHtmlValue(AddressLocale2.getState()) %>">          
          
 </td>
  </tr>   
    
    <dhv:include name="address.country" none="true">
    <tr >
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.Country">Country</dhv:label>
    
      </td>
      <td>
        <% CountrySelect.setJsEvent("onChange=\"javascript:update('address3country', '3','');\"");%>
        <%= CountrySelect.getHtml("address2country", applicationPrefs.get("SYSTEM.COUNTRY")) %>
        <%
          CountrySelect = new CountrySelect(systemStatus);
         %>
   </td>
    </tr>
    </dhv:include>
    
	  <tr class="containerBody" style="display:none" id="indirizzo27">
	    <td class="formLabel" nowrap><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
	    <td>
	    	<input type="text" id="address4latitude" name="address4latitude2" size="30" value="">
	       	<%--input type="button"
				onclick="javascript:Geocodifica.getCoordinate( document.forms[0].address4line1.value,document.forms[0].address4city.value,document.forms[0].address4state.value,'','address4latitude','address4longitude','address4coordtype',setGeocodedLatLon)"
				value="<dhv:label name="geocodifica.calcola">Calcola</dhv:label>"
			/>
    	<input type="hidden" name="address4coordtype" id="address4coordtype" value="0" /--%>
	    </td>
	  </tr>
	  <tr class="containerBody" style="display:none" id="indirizzo28">
	    <td class="formLabel" nowrap><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
	    <td><input type="text" id="address4longitude2" name="address4longitude" size="30" value=""></td>
	  </tr>
	  
	   <tr class="containerBody" style="display:none" id="indirizzo3button">
	    <td class="formLabel" nowrap></td>
	    <td><input type="button" onClick="mostraNextIndirizzo(3)" value="Aggiungi Altro Indirizzo" > </td>
	  </tr>
  </dhv:include> 
  
<%} %>
 

 
  
  
 <%if(acount==2 || acount==0){ %>
  <dhv:include name="accounts-address3" none="true">
  
    
    <tr style="display:none" id="indirizzo36">
	<td nowrap class="formLabel" name="province3" id="province3">
     <b>Indirizzo 3</b>
    </td> 
    <td >
	</td>
  	</tr >
  	  <tr style="display:none" id="indirizzo31">
      <td nowrap class="formLabel" id="tipoStruttura1">
        <dhv:label name="contact.fsource">Tipo locale</dhv:label>
 		<input type="hidden" name="address4type3" value="6">
      </td>
    
      <td>
        <%= TipoLocale.getHtmlSelect("TipoLocale3",OrgDetails.getTipoLocale3())%>
      </td>
     </tr>
    <tr style="display:none" id="indirizzo32">
	<td nowrap class="formLabel" name="province3" id="province3">
      <dhv:label name="requestor.requestor_add.City">City</dhv:label>
    </td> 
    <td > 
    <input type="text" name="address4city3" id="prov3" >
	

	</td>
  	</tr>
    <tr style="display:none" id="indirizzo33">
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
      </td>
      <td>
        <input type="text" size="40" name="address4line13" maxlength="80" value="<%= toHtmlValue(Address.getStreetAddressLine4()) %>">
   </td>
    </tr>
    
   
  	<tr style="display:none" id="indirizzo34">
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
     </td>
      <td>
        <input type="text" size="28" name="address4zip3" maxlength="12">
      <font color="red" id="starMobil5">*</font></td>
    </tr>
  	 
  	<tr style="display:none" id="indirizzo35">
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	  <input type="text" size="28" name="address4state3" maxlength="80" value="<%= toHtmlValue(AddressLocale3.getState()) %>">          
         
    <font id="starMobil10" color="red">*</font></td>
  </tr>   
    
    <dhv:include name="address.country" none="true">
    <tr style="display:none" id="indirizzo36">
      <td nowrap class="formLabel">
        <dhv:label name="requestor.requestor_add.Country">Country</dhv:label>
      <font color="red" id="starMobil6">*</font>
      </td>
      <td>
        <% CountrySelect.setJsEvent("onChange=\"javascript:update('address3country', '3','');\"");%>
        <%= CountrySelect.getHtml("address2country", applicationPrefs.get("SYSTEM.COUNTRY")) %>
        <%
          CountrySelect = new CountrySelect(systemStatus);
         %>
      <font color="red" id="starMobil7">*</font></td>
    </tr>
    </dhv:include>
    
	  <tr class="containerBody" style="display:none" id="indirizzo37">
	    <td class="formLabel" nowrap><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
	    <td>
	    	<input type="text" id="address4latitude3" name="address4latitude3" size="30" value=""><font color="red" id="starMobil8">*</font>
	      
	    </td>
	  </tr>
	  <tr class="containerBody" style="display:none" id="indirizzo38">
	    <td class="formLabel" nowrap><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
	    <td><input type="text" id="address4longitude3" name="address4longitude3" size="30" value=""><font color="red" id="starMobil9">*</font></td>
	  </tr>
  </dhv:include> 
  
  <%} %>


</table>
<br>
</dhv:include>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
  <tr>
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="accounts.accounts_add.Notes">Notes</dhv:label>
    </td>
    <td><TEXTAREA NAME="notes" ROWS="3" COLS="50"><%= toString(OrgDetails.getNotes()) %></TEXTAREA></td>
  </tr>
</table>
<dhv:evaluate if="<%= !popUp %>">  
<br /><%-- %>
<dhv:label name="accounts.radio.header">Where do you want to go after this action is complete?</dhv:label><br />
<input type="radio" name="target" value="return" onClick="javascript:updateCopyAddress(0)" <%= request.getParameter("target") == null || "return".equals(request.getParameter("target")) ? " checked" : "" %> /> <dhv:label name="accounts.radio.details">View this account's details</dhv:label><br />
<input type="radio" name="target" value="add_contact" onClick="javascript:updateCopyAddress(1)" <%= "add_contact".equals(request.getParameter("target")) ? " checked" : "" %> /> <dhv:label name="accounts.radio.addContact">Add a contact to this account</dhv:label>
<input type="checkbox" id="copyAddress" name="copyAddress" value="true"  disabled="true" /><dhv:label name="accounts.accounts_add.copyEmailPhoneAddress">Copy email, phone and postal address</dhv:label>--%>
</dhv:evaluate>  
<br />
<input type="hidden" name="onlyWarnings" value='<%=(OrgDetails.getOnlyWarnings()?"on":"off")%>' />
<%= addHiddenParams(request, "actionSource|popup") %>
<input type="submit" value="Inserisci" name="Save" onClick="this.form.dosubmit.value='true';" />
<dhv:evaluate if="<%= !popUp %>">
  <input type="submit" value="Annulla" onClick="javascript:this.form.action='Accounts.do?command=Search';this.form.dosubmit.value='false';">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true" />

<input type = "hidden" name = "forzainserimento" id = "forzaInsert" value = "0">
</form>
<%
if(request.getAttribute("Trovato")!=null)
{
	%>
	<script>
	
			

		<%
				if(OrgDetails.getTipoDest().equals("Distributori")) {%>
				abilitaDistributoriCampi();

				<%}
				else{
					
				%>
				updateFormElementsNew('<%=OrgDetails.getTipoDest()%>');
				<%				}
				
			
			
			
			
		
		
		%>
		
		var  chiediConferma = confirm('Impresa Esistente .Sei sicuro di voler proseguire col l inserimento ? ');
		  
		  if (chiediConferma == true){
			  document.getElementById('forzaInsert').value = "1";
			  document.getElementById("addAccount").submit();
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
				  } 
				  if ( (document.getElementById("codice2").value  != "") ){
					  document.getElementById("codice2").value="";
					  document.getElementById("cod2").value="";
				  }
				  if ( (document.getElementById("codice3").value  != "") ){
					  document.getElementById("codice3").value="";
					  document.getElementById("cod3").value="";
				  }
				  if ( (document.getElementById("codice4").value  != "") ){
					  document.getElementById("codice4").value="";
					  document.getElementById("cod4").value="";
				  }
				  if ( (document.getElementById("codice5").value  != "") ){
					  document.getElementById("codice5").value="";
					  document.getElementById("cod5").value="";
				  }
				  if ( (document.getElementById("codice6").value  != "") ){
					  document.getElementById("codice6").value="";
					  document.getElementById("cod6").value="";
				  }
				  if ( (document.getElementById("codice7").value  != "") ){
					  document.getElementById("codice7").value="";
					  document.getElementById("cod7").value="";
				  }
				  if ( (document.getElementById("codice8").value  != "") ){
					  document.getElementById("codice8").value="";
					  document.getElementById("cod8").value="";
				  }
				  if ( (document.getElementById("codice9").value  != "") ){
					  document.getElementById("codice9").value="";
					  document.getElementById("cod9").value="";
				  }
				  if ( (document.getElementById("codice10").value  != "") ){
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
	
	</script>
	
	<%
	
}

%>

</body>