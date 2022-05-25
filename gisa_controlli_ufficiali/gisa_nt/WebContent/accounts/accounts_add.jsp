<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*" %>

<%@page import="org.aspcfs.modules.requestor.base.OrganizationAddress"%>
<%@page import="org.aspcfs.modules.base.Address"%><jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoLocale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="IstatList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Address" class="org.aspcfs.modules.requestor.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressSedeOperativa" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressSedeLegale" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request"/>

<jsp:useBean id="AddressSedeMobile" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLocale1" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLocale2" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request"/>
<jsp:useBean id="AddressLocale3" class="org.aspcfs.modules.accounts.base.OrganizationAddress" scope="request"/>

<jsp:useBean id="rel_ateco_linea_attivita_List" class="org.aspcfs.utils.web.LookupList" scope="request"/>

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
<jsp:useBean id="CountryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
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
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/validaCF.js"></script>

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

		<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        <script type="text/javascript">

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
          	
          	var clonato = document.getElementById('locale_0');
          	
            	/*clona patologia*/	  	
          	clone=clonato.cloneNode(true);
          	clone.id="locale_"+elementi.value ;
          	clone.getElementsByTagName('FONT')[0].style.display = "none";
        	clone.getElementsByTagName('FONT')[0].id = "city_loc"+ elementi.value;
        	
        	

          	clone.getElementsByTagName('FONT')[1].style.display = "none";
        	clone.getElementsByTagName('FONT')[1].id = "address_loc"+ elementi.value;

          	clone.getElementsByTagName('FONT')[2].style.display = "none";
        	clone.getElementsByTagName('FONT')[2].id = "state_loc"+ elementi.value;

          	clone.getElementsByTagName('FONT')[3].style.display = "none";
        	clone.getElementsByTagName('FONT')[3].id = "zip_loc"+ elementi.value;
        	
        	clone.getElementsByTagName('INPUT')[0].name = "address" + elementi.value+"id";
        	clone.getElementsByTagName('INPUT')[0].id = "address" + elementi.value+"id";
        	clone.getElementsByTagName('INPUT')[0].value = "";
        	
        	clone.getElementsByTagName('INPUT')[1].name = "address4type" + elementi.value;
        	clone.getElementsByTagName('INPUT')[1].id = "address4type" + elementi.value;
        	
        	
        	
        	
        	clone.getElementsByTagName('SELECT')[0].name = "TipoLocale" + elementi.value;
        	clone.getElementsByTagName('SELECT')[0].id = "TipoLocale" + elementi.value;
        	clone.getElementsByTagName('SELECT')[0].value = "-1";
        	clone.getElementsByTagName('SELECT')[0].required="required";
      	  	
        	clone.getElementsByTagName('INPUT')[2].name = "address4city" + elementi.value;
        	clone.getElementsByTagName('INPUT')[2].id = "address4city" + elementi.value;
        	clone.getElementsByTagName('INPUT')[2].value = "" ;
        	
        	
        		clone.getElementsByTagName('INPUT')[2].required="required";
        		
        	
      		clone.getElementsByTagName('INPUT')[3].name = "address4line1" + elementi.value;
        	clone.getElementsByTagName('INPUT')[3].id = "address4line1" + elementi.value;
        	clone.getElementsByTagName('INPUT')[3].value = "" ;
        	
    		clone.getElementsByTagName('INPUT')[3].required="required";
    		
        	
      		clone.getElementsByTagName('INPUT')[4].name = "address4zip" + elementi.value;
        	clone.getElementsByTagName('INPUT')[4].id = "address4zip" + elementi.value;
        	clone.getElementsByTagName('INPUT')[4].value = "" ;
        	clone.getElementsByTagName('INPUT')[4].required="required";
        	
      		clone.getElementsByTagName('INPUT')[5].name = "address4state" + elementi.value;
        	clone.getElementsByTagName('INPUT')[5].id = "address4state" + elementi.value;
        	clone.getElementsByTagName('INPUT')[5].value = "" ;
        	clone.getElementsByTagName('INPUT')[5].required="required";
        	
      		clone.getElementsByTagName('INPUT')[6].name = "address4latitude" + elementi.value;
        	clone.getElementsByTagName('INPUT')[6].id = "address4latitude" + elementi.value;
        	clone.getElementsByTagName('INPUT')[6].value = "" ;
        	
        	clone.getElementsByTagName('INPUT')[7].name = "address4longitude" + elementi.value;
        	clone.getElementsByTagName('INPUT')[7].id = "address4longitude" + elementi.value;
        	clone.getElementsByTagName('INPUT')[7].value = "" ;
          	
        	clone.getElementsByTagName('LABEL')[0].innerHTML ='Locale Funzionalmente collegato '+elementi.value ;
          	clone.getElementsByTagName('LABEL')[0].id = "intestazione" + elementi.value;
          	
          	
          
          	
          	//clone.id = "row_" + elementi.value;
          	
          	/*Aggancio il nodo prova*/
          	clone.style.display="";
          	clonato.parentNode.appendChild(clone);
          	
          	/*Lo rendo visibile*/
          	//clone.style.display="block";
          	
          	}else
          	{
          		
          	}

        }
        
        
        
        function removeLocale(indice)
        {
        	
        	
        	
        	if(parseInt(indice)>0)
        		{
        	if (document.getElementById('locale_'+indice) != null)
        		{
        		document.getElementById('locale_'+indice).parentNode.removeChild(document.getElementById('locale_'+indice));
        		document.getElementById('elementi').value=parseInt(document.getElementById('elementi').value)-1;
        		document.getElementById('size').value=parseInt(document.getElementById('size').value)-1;
        		}
        		}
        	else{
        		alert('Locali Funzionamente Collegati Rimossi')
        	}
        	
        	
        	if (document.getElementById('elementi').value=='0' && document.addAccount.tipoDest.value=='Autoveicolo')
        		{
        		
        		clonaLocaleFunzionalmenteCollegato();
        		}
        }
        
        function removeAllLocali()
        {
        	indice = 1 ;
        	while (document.getElementById('locale_'+indice) != null)
        		{
        		document.getElementById('locale_'+indice).parentNode.removeChild(document.getElementById('locale_'+indice)); 
        		indice +=1 ;
        		}
        	document.getElementById('elementi').value="0";
        	document.getElementById('size').value="0";
        		}
        
			function verificaEsistenzaImpresa()
			{
				if (document.forms[0].cancel.value=='false')
					{
				tipoAttivita = document.addAccount.tipoDest.value ;
				ragioneSociale = document.addAccount.name.value ;
				partitaIva = document.addAccount.partitaIva.value ;
				codFiscale = document.addAccount.codiceFiscale.value ;
				if (tipoAttivita = 'Es. Commerciale')
				{
					type = 5 ;
					citta		= document.addAccount.address2city.value ;
					indirizzo 	= document.addAccount.address2line1.value ;
				}
				else
				{
					if (tipoAttivita = 'Autoveicolo')
					{
						type = 7 ;
						citta 		= document.addAccount.address3city.value ;
						indirizzo 	= document.addAccount.address3line1.value ;
					}
					else
					{
						if (tipoAttivita = 'Distributori')
						{
							type = 1 ;
							citta 		= document.addAccount.address1city.value ;
							indirizzo 	= document.addAccount.address1line1.value ;
						}
					}

				}
				
				PopolaCombo.checkEsistenzaImpresa(ragioneSociale ,partitaIva ,citta,indirizzo,codFiscale,1,type,{callback:verificaEsistenzaImpresaCallback,async:false }) ;
				if( document.addAccount.dosubmit.value=='true')
					return true;
				return false;
					}
					return true;
				}
			
			function verificaEsistenzaImpresaCallback(value)
			{
			
				if (value == 'false')
				{
					 if (doCheck(document.addAccount)==true)
					 {
						 
						 document.addAccount.dosubmit.value='true';
						 return true ;
					}
					 document.addAccount.dosubmit.value='false';
					 return false ;
				}
				else
				{
					if (confirm('Impresa esistente , sicuro di voler salvare ? ')==true)
					{
						if ( doCheck(document.addAccount)==true)
						{
							document.addAccount.dosubmit.value='true';
							 return true ;
						}
						document.addAccount.dosubmit.value='false';
						return false;
					}
				}
				
			}

			</script>

 
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
        document.addAccount.address3state.style.background = "#ffffff";
        document.addAccount.address3state.value = "";
        document.addAccount.address3state.disabled = false;
       
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

   function disabilitaDistributoriCampi(){
	   document.getElementById("locali_button").disabled=false;
	   document.getElementById("codiceFiscaleCorrentista").value="";
	   document.getElementById("alertText").value="";
	   document.getElementById("codiceFiscaleCorrentista").onchange();
   }

   function gestioneSedeLegale(tipoDest){

   	if(tipoDest == "Autoveicolo" || tipoDest == "Distributori"){

   		//document.getElementById("address1city2").style.display="block";
   		//document.getElementById("address1city2").disabled=false;
   		//document.getElementById("address1city1").style.display="none";
   		//document.getElementById("address1city1").value="";
   		//document.getElementById("address1city1").disabled=true;
   		document.addAccount.address2city.value=-1;
   		document.addAccount.address2state.value = "";
   		document.getElementById("coord1button").disabled = true;
   		if(tipoDest == "Autoveicolo"){
   			document.getElementById("coord2button").disabled = false;
   			document.getElementById("sl").innerHTML="<font color = 'red'>Inserire comune,indirizzo,provincia,coordinate per sede legale</font>";
   			document.getElementById("so").innerHTML="";
   			document.getElementById("mob").innerHTML="<font color = 'red'>Inserire comune,indirizzo,provincia,coordinate per sede mobile</font>";
   			document.getElementById("loc1").innerHTML="<font color = 'red'>Inserire comune,indirizzo,provincia,coordinate per locale1</font>";
   		}
   		else{
   			document.getElementById("coord2button").disabled = true;
   			document.getElementById("sl").innerHTML="<font color = 'red'>Inserire comune,Indirizzo,provincia,coordinate per sede legale</font>";
   			document.getElementById("so").innerHTML="";
   			document.getElementById("mob").innerHTML="";
   			document.getElementById("loc1").innerHTML="";
   		}
   		document.getElementById("address1latitude").disabled=false;
   		document.getElementById("address1longitude").disabled=false;
   		document.getElementById("coordbutton").disabled = false;
   		
   		document.addAccount.address1state.value = document.addAccount.address1state1.value;
   		document.addAccount.address3state.value = document.addAccount.address1state1.value;
   		document.addAccount.address2state.disabled = true;
   		if(document.addAccount.address1state1.value!="")
   			document.addAccount.address1state.disabled=true;

   		document.getElementById('provs').style.display = "" ; 
   		document.getElementById('provs').disabled = false ;

   		document.getElementById('address1city').style.display = "none" ; 
   		document.getElementById('address1city').disabled = true ;
   		
   				
   	}
   	else if(tipoDest == "Es. Commerciale"){
  		document.getElementById('provs').style.display = "none" ; 
   		document.getElementById('provs').disabled = true ;

   		document.getElementById('address1city').style.display = "" ; 
   		document.getElementById('address1city').disabled = false ;
   		
   		//document.getElementById("address1city2").style.display="block";
   		//document.getElementById("address1city2").style.display="none";
   		//document.getElementById("address1city2").value="";
   		//document.getElementById("address1city").disabled=false;
   		//document.getElementById("address1city2").disabled=false;
   		
   		document.getElementById("address1latitude").value="";
   		document.getElementById("address1latitude").disabled=false;
   		document.getElementById("address1longitude").value="";
   		document.getElementById("address1longitude").disabled=false;
   		document.getElementById("coordbutton").disabled = false;
   		
 		document.addAccount.address1state.value = "";
   		document.addAccount.address1state.disabled=false;
   		document.addAccount.address2state.disabled = false;
   		document.getElementById("coord1button").disabled = false;
   		document.getElementById("coord2button").disabled = true;
   		document.getElementById("sl").innerHTML="";
   		document.getElementById("so").innerHTML="<font color = 'red'>Inserire comune,indirizzo,provincia,coordinate per sede operativa</font>";
   		document.getElementById("mob").innerHTML="";
   		document.getElementById("loc1").innerHTML="";
   		
   			
   	}
   }
   
   function abilitaDistributoriCampi(){
	   document.getElementById("locali_button").disabled=true;
   document.getElementById("codiceFiscaleCorrentista").value="47.99.20";
   document.getElementById("alertText").value="Commercio effettuato per mezzo di distributori automatici";
   document.getElementById("codiceFiscaleCorrentista").onchange();

      elm1 = document.getElementById("tipoVeicolo1"); //Nome
      elm2 = document.getElementById("targaVeicolo1"); //Cognome
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
       document.addAccount.address3state.style.background = "#cccccc";
       document.addAccount.address3state.value = "";
       document.addAccount.address3state.disabled = true;
       
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


       //Disabilitare anche i bottoni di calcola coordinate 
       
   	/*elm3.style.color="#cccccc";
       document.addAccount.codiceCont.style.background = "#cccccc";
       document.addAccount.codiceCont.value = "";
       document.addAccount.codiceCont.disabled = true;*/
       
    	document.getElementById("prov").disabled = true;
       document.addAccount.check.value = "autoveicolo";
       document.addAccount.orgType.value = "17"; //Valore per PROPRIETARIO

       /*elm1=document.addAccount.TipoLocale;
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
       }*/
       if( document.getElementById("aggiungialtrobutton")!=null)
       document.getElementById("aggiungialtrobutton").disabled="true";
       if( document.getElementById("aggiungialtrobutton2")!=null)
        document.getElementById("aggiungialtrobutton2").disabled="true";
   }
   function updateFormElementsNew(indexText) {


		index =0;
	   if(indexText=='Es. Commerciale')
	   {
		   index = 0;

			}
	   if(indexText=='Autoveicolo')
	   {
		   index = 1;

			}
	   

		  //document.getElementById("codiceFiscaleCorrentista").value="";
		  //document.getElementById("alertText").value="";


		  elm1=document.addAccount.nameMiddle;
		    elm2=document.addAccount.cin;
		    elm3=document.addAccount.date3;

	
		 
		  
		  
		  elm1=document.addAccount.TipoLocale;

		    elm2=document.addAccount.address4city11;
		    elm3=document.addAccount.address4line11;
		    elm4=document.addAccount.address4latitude11;
		    elm5=document.addAccount.address4longitude11;
		    elm6=document.addAccount.address4zip11;
		    elm7=document.addAccount.address4state11;
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

	  		document.addAccount.address1latitude.style.background = "#ffffff";
	  		document.addAccount.address1longitude.style.background = "#ffffff";
	  		
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

	  		document.addAccount.address1latitude.style.background = "#cccccc";
	  		document.addAccount.address1longitude.style.background = "#cccccc";
	  	
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
	document.addAccount.address3state.style.background = "#cccccc";
    document.addAccount.address3state.value = "";
    document.addAccount.address3state.disabled = true;
    
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

  /*
  function doCheck(form) {
      if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
*/


  function doCheck(form){
	 
		  if(checkForm(form)==true) {
			  loadModalWindow();
			  return true;
		  }
		  else
			  return false;
		  
	  
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
    
     
    
    if (form.siteId.value && form.siteId.value=="-1"){
    	 //alert(!isNaN(form.address2latitude.value));
    		
     			 message += "- Controllare di aver selezionato un valore per il campo A.S.L.\r\n";
     				 formTest = false;
     		
 	 }   
    
    if(!codiceFISCALE(form.codiceFiscaleRappresentante.value)){
		  
		  message += "- Controllare di aver inserito un codice fiscale valido per il legale rappresentante.\r\n";
	      formTest = false;
	  }  
    
    if (form.name){
      if ((checkNullString(form.name.value))){
        message += "- Impresa richiesta\r\n";
        formTest = false;
      }
    }

    if (
    document.getElementById('tipoD').checked == false && 
    document.getElementById('tipoD2').checked == false  &&
    document.getElementById('tipoD3').checked == false  
)
    {

      	message += "- Tipo di Attivita Richiesto.Indicare se FISSA MOBILE DISTRIBUTORI\r\n";
     	 formTest = false;
        }
    
    if (form.no_piva.checked==false){
    if (checkNullString(form.partitaIva.value)){
   	  if (checkNullString(form.codiceFiscale.value)){
       	message += "- Partita IVA richiesta\r\n";
      	 formTest = false;
   	  }
     }

  if (! checkNullString(form.partitaIva.value) && (form.partitaIva.value.length<11 && form.provenienzaIT.checked)){
 	  
     	message += "- Partita IVA non Valida \r\n";
    	 formTest = false;
 	  
   }
 
  if (form.partitaIva.value.length>11 && form.provenienzaIT.checked){
 	 
   	message += "- Partita IVA non Valida per provenienza ITALIA \r\n";
  	 formTest = false;
	  
 }
    } else {
    	 if ( form.codiceFiscale.value=="" || form.codiceFiscale.value.length<16 ){
    			message += "- Codice Fiscale richiesto \r\n";
    		  	 formTest = false;
    	 }
    }
    	 
  if (form.provenienzaEST.checked && form.country.value==-1){
	 	 
	   	message += "- Selezionare un PAESE in caso di provenienza estera. \r\n";
	  	 formTest = false;
		  
	 }
      
  if (form.no_piva.checked==false){
  	if (form.partitaIva && form.partitaIva.value!="" && form.provenienzaIT.checked){
     	 //alert(!isNaN(form.address2latitude.value));
     		if ((orgSelected == 1)  ){
     			if (isNaN(form.partitaIva.value)  ){
      			 message += "- Valore errato per il campo Partita IVA. Si prega di inserire solo cifre\r\n";
      				 formTest = false;
      			}
     				 
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
	  	 
    
    if(document.getElementById("tipoD").checked || document.getElementById("tipoD3").checked){
        if(document.getElementById("codiceFiscaleCorrentista").value=="47.99.20"){
       	 message += "- Per il codice ISTAT selezionato il tipo di attività deve essere \'Distributori\'.\r\n";
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
    	// message += " test" + form.dateF.value.replace(/"."/g,"") > form.dateI.value +" \r\n";
    }

    if (form.stageId.value == "-1"){
        message += "- Il Servizio Competente è richiesto\r\n";
        formTest = false;
      }
    if (checkNullString(form.codiceFiscaleRappresentante.value)){
        message += "- Codice Fiscale del rappresentante richiesto\r\n";
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

        if(form.tipoD.checked){
        if (checkNullString(document.getElementById("address1city").value)){
          message += "- Comune sede legale richiesta\r\n";
          formTest = false;
        }}

        if(form.tipoD2.checked || form.tipoD3.checked){
        if (document.getElementById("address1city").value==-1){
            message += "- Comune sede legale richiesta\r\n";
            formTest = false;
          }
        }

        if (form.address1state.value == ""){
            message += "- Provincia sede legale richiesta\r\n";
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

          if (form.address2latitude.value == ""){
         	 message += "- Latitudine sede operativa richiesta\r\n";
          	 formTest = false;
     		}

 			if (form.address2longitude.value == ""){
         		message += "- Longitudine sede operativa richiesta\r\n";
         		formTest = false;
    		}
          
         }

    		
    	

      //Versione precedente  
      /*if (form.address2latitude && form.address2latitude.value!=""){
      	 //alert(!isNaN(form.address2latitude.value));
      		if ((orgSelected == 1)  ){
      			if (isNaN(form.address2latitude.value) ||  (form.address2latitude.value < 4431788.049190) || (form.address2latitude.value >4593983.337630 )){
       			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 4431788.049190 e 4593983.337630  (Sede Operativa)\r\n";
       				 formTest = false;
       			}		 
      		}
   	 }  
   	 
      if (form.address2longitude && form.address2longitude.value!=""){
   	 //alert(!isNaN(form.address2longitude.value));
   		if ((orgSelected == 1)  ){
   			if (isNaN(form.address2longitude.value) ||  (form.address2longitude.value < 2417159.584320) || (form.address2longitude.value > 2587487.362260)){
    			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 2417159.584320 e  2587487.362260 (Sede Operativa)\r\n";
    				 formTest = false;
    			}		 
   		}
	 }   
   	 
   	 */

   	 


   	  
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


  	 
  	 
  	 //Controlli javascript per la latitudine e la longitudine del locale collegato
 	if (form.address4latitude1 && form.address4latitude1.value!=""){
     	 //alert(!isNaN(form.address3latitude.value));
     		if ((orgSelected == 1)  ){
     			if (isNaN(form.address4latitude1.value) ||  (form.address4latitude1.value < 39.988475) || (form.address4latitude1.value > 41.503754)){
      			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Locale fun. collegato)\r\n 11111";
      				 formTest = false;
      			}		 
     		}
  	 }   

 	 if (form.address4longitude1 && form.address4longitude1.value!=""){
    	 //alert(!isNaN(form.address2longitude.value));
    		if ((orgSelected == 1)  ){
    			if (isNaN(form.address4longitude1.value) ||  (form.address4longitude1.value < 13.7563172) || (form.address4longitude1.value > 15.8032837)){
     			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Locale fun. collegato)\r\n 11222211";
     				 formTest = false;
     			}		 
    		}
 	 }   

 	if (form.address4latitude2 && form.address4latitude2.value!=""){
    	 //alert(!isNaN(form.address3latitude.value));
    		if ((orgSelected == 1)  ){
    			if (isNaN(form.address4latitude2.value) ||  (form.address4latitude2.value < 39.988475) || (form.address4latitude2.value > 41.503754)){
     			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Locale fun. collegato)\r\n";
     				 formTest = false;
     			}		 
    		}
 	 }   

	 if (form.address4longitude2 && form.address4longitude2.value!=""){
   	 //alert(!isNaN(form.address2longitude.value));
   		if ((orgSelected == 1)  ){
   			if (isNaN(form.address4longitude2.value) ||  (form.address4longitude2.value < 13.7563172) || (form.address4longitude2.value > 15.8032837)){
    			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Locale fun. collegato)\r\n";
    				 formTest = false;
    			}		 
   		}
	 }   

	 if (form.address4latitude3 && form.address4latitude3.value!=""){
    	 //alert(!isNaN(form.address3latitude.value));
    		if ((orgSelected == 1)  ){
    			if (isNaN(form.address4latitude3.value) ||  (form.address4latitude3.value < 39.988475) || (form.address4latitude3.value > 41.503754)){
     			 message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754 (Locale fun. collegato)\r\n";
     				 formTest = false;
     			}		 
    		}
 	 }   

	 if (form.address4longitude3 && form.address4longitude3.value!=""){
   	 //alert(!isNaN(form.address2longitude.value));
   		if ((orgSelected == 1)  ){
   			if (isNaN(form.address4longitude3.value) ||  (form.address4longitude3.value < 13.7563172) || (form.address4longitude3.value > 15.8032837)){
    			 message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 (Locale fun. collegato)\r\n";
    				 formTest = false;
    			}		 
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

       if (form.address3city.value=="-1" && (form.address3city.disabled==false)){
           message += "- Comune attività mobile richiesto\r\n";
           formTest = false;
         }

       
		var obj = form.address3city;
	
	
      
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

<dhv:evaluate if='<%= (request.getParameter("form_type") == null || "organization".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:selectCarattere('source');document.addAccount.name.focus();updateFormElementsNew(<%=OrgDetails.getTipoDest() %>);resetCodice();abilita_codici_ateco_vuoti()">
</dhv:evaluate>


<dhv:evaluate if='<%= ("individual".equals((String) request.getParameter("form_type"))) %>'>
  <body onLoad="javascript:document.addAccount.name.focus();updateFormElementsNew(<%=OrgDetails.getTipoDest() %>);resetCodice();abilita_codici_ateco_vuoti()">
</dhv:evaluate>


<%-- showAddress(this.address2line1.value+', '+this.address2city.value, this.address2latitude, this.address2longitude); return false;--%>
<form id = "addAccount" name="addAccount" action="Accounts.do?command=Insert&auto-populate=true" method="post" onsubmit="return verificaEsistenzaImpresa()">
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
<!-- ------------------------------------------------------------------------------------------------------ -->
  
<dhv:evaluate if="<%= !popUp %>">  
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Accounts.do"><dhv:label name="">Stabilimenti 852</dhv:label></a> >
Aggiungi Impresa
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:formMessage showSpace="false"/>
<input type="submit" value="Inserisci" name="Save" >
<dhv:evaluate if="<%= !popUp %>">
  <input type="button" value="Annulla" onClick="window.location.href='Accounts.do'">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<br /><br />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Stabilimento 852</dhv:label></strong>
    </th>
  </tr>
 
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

 
 

  <tr>
    <td nowrap class="formLabel">
        <dhv:label name="contact.sources">  Attivit&agrave;</dhv:label>
      </td>
	<td>
      <input type="radio" id="tipoD" name="tipoDest" value="Es. Commerciale" onClick="removeAllLocali(); gestisciDivProvenienza(1); javascript:updateFormElementsNew('Es. Commerciale'); gestioneSedeLegale('Es. Commerciale');disabilitaDistributoriCampi(); abilita_chk();" <%if(OrgDetails.getTipoDest()!=null && OrgDetails.getTipoDest().equals("Es. Commerciale")){ %> checked="checked" <%} %> >Fissa
      <input type="radio" id="tipoD3" name="tipoDest" value="Autoveicolo" onClick="gestisciDivProvenienza(0); javascript:removeAllLocali();clonaLocaleFunzionalmenteCollegato();updateFormElementsNew('Autoveicolo'); gestioneSedeLegale('Autoveicolo'); disabilitaDistributoriCampi(); disabilita_chk();" <%if(OrgDetails.getTipoDest()!=null && OrgDetails.getTipoDest().equals("Autoveicolo")){ %> checked="checked" <%} %> >Mobile
      <input type="radio" id="tipoD2" name="tipoDest" value="Distributori" <%if(OrgDetails.getTipoDest()!=null){ if(OrgDetails.getTipoDest()!=null && OrgDetails.getTipoDest().equals("Distributori")){ %> checked <%}}%>  onClick="removeAllLocali();gestisciDivProvenienza(0); javascript:abilitaDistributoriCampi(); gestioneSedeLegale('Distributori');  disabilita_chk();">Distributori
      <!-- Autoveicolo
       <input type="radio" name="tipoDest" value="Contenitore" onClick="javascript:updateFormElementsNew(2);" >
      Contenitore -->
      <input type="hidden" name="orgType" value="" />
      <input type="hidden" name="check" />
      
      
       <div id="divProvenienza" style="display:block">
        <dhv:label name="">Provenienza: </dhv:label>
       <input type="radio" name="provenienza" id="provenienzaIT" checked="checked" value="ITALIA" onclick="gestisciProvenienzaEstera(0); setLabelSedeLegale('ITALIA');"/> <img width="20px" src="images/flags/it.gif"/> Italia
      <input type="radio" name="provenienza" id="provenienzaEST" value="ESTERO" onclick="gestisciProvenienzaEstera(1); setLabelSedeLegale('ESTERO');"/> <img width="20px" src="images/flags/eu.gif"/> Estera
       
       <div id="divListaEstera" style="display:none">
        <%= CountryList.getHtmlSelect("country",-1) %>   	<font color = "red">*</font>
      </div>
      
      
      </div>
   
      </td>
      </tr>
      
     <tr>
    <td nowrap class="formLabel" name="orgname1" id="orgname1">
      <dhv:label name="">Impresa</dhv:label>
    </td>
    <td>
      <input onFocus="if (indSelected == 1) { tabNext(this) }" type="text" size="50" maxlength="80" name="name" value="<%= toHtmlValue(OrgDetails.getName()) %>"> <%= showAttribute(request, "nameError") %>
   	<font color = "red">*</font>
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
  <!-- modificato da d.dauria -->
  <tr >
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="20" maxlength="16" id="codiceFiscale" name="codiceFiscale" disabled="disabled" value=""/><font style="display:none" id="no_cf" color="red">* (INSERIRE CODICE FISCALE)</font>    
    </td>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Vendita con canali non convenzionali</dhv:label>
    </td>
    <td>
     	<input type = "checkbox" name = "flag_vendita">
    </td>
  </tr>
  <!-- fine modifica -->
  
  
  <tr class="containerBody">
	  <td class="formLabel" nowrap>
       		<dhv:label name="">Codice Ateco/Linea di Attivita Principale</dhv:label>
	  </td>
	
	  <td>
	  <input type="hidden" id="id_attivita_masterlist" name="id_attivita_masterlist" value="">
	  <input style="background-color: lightgray" readonly="readonly" type="text" size="20" id="codiceFiscaleCorrentista" name="codiceFiscaleCorrentista" value="" onchange="costruisci_rel_ateco_attivita('codiceFiscaleCorrentista', 'id_rel_principale');"   ><font color="red">*</font>
	  <%-- &nbsp;[<a href="javascript:popLookupSelectorCustomImprese('codiceFiscaleCorrentista','alertText','lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Seleziona</dhv:label></a>]--%>
	  &nbsp;[<a href="javascript:popLookupSelectorCustomImprese('codiceFiscaleCorrentista','alertText','id_attivita_masterlist','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Seleziona</dhv:label></a>]
	  
	  <br/><%= lookup_vuota_linea_attivita.getHtmlSelect("id_rel_principale",-1 ) %>
	  
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
	
	<td>
			<b>Codice 1&nbsp;&nbsp;</b>
		    <input type="hidden" id="id_attivita_masterlist_1" name="id_attivita_masterlist_1" value="-1">
      		<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice1" name="codice1" class="codiciatecolista"
      			   onchange="costruisci_rel_ateco_attivita('codice1',  'id_rel_1' ); abilita_codici_ateco_vuoti();"	value="" >
      		<%-- [<a href="javascript:popLookupSelectorCustomImprese('codice1','cod1', 'lookup_codistat','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]--%>
      		[<a href="javascript:popLookupSelectorCustomImprese('codice1','cod1', 'id_attivita_masterlist_1', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		
      		
      		<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod1" name="cod1" value="" >
      		<br/><%
					out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_1" , -1 ) ) );
					
			%><br/>
			<br></br>
        	
      		 <div id="div_codice1" style="display: none">
      		 	<b>Codice 2&nbsp;&nbsp;</b>
      		    <input type="hidden" id="id_attivita_masterlist_2" name="id_attivita_masterlist_2" value="-1">	
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice2" name="codice2" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice2',  'id_rel_2' ); abilita_codici_ateco_vuoti();" value="" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice2','cod2', 'id_attivita_masterlist_2', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod2" name="cod2" value="" >
      		 	<br/><%
						out.println(lookup_vuota_linea_attivita.getHtmlSelect("id_rel_2", -1 ));
				%><br/>
				<br></br>
      		 </div>
      		 
      		 <div id="div_codice2" style="display: none">
      		 	<b>Codice 3&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_3" name="id_attivita_masterlist_3" value="-1">
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice3" name="codice3" class="codiciatecolista"
      		    	onchange="costruisci_rel_ateco_attivita('codice3',  'id_rel_3' ); abilita_codici_ateco_vuoti();" value="" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice3','cod3', 'id_attivita_masterlist_3', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod3" name="cod3" value="" >
      		    <br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_3" , -1 ) ) );
				%><br/>
				<br></br>
      		 </div>
      		 
      		 <div id="div_codice3" style="display: none">
      		 	<b>Codice 4&nbsp;&nbsp;</b>
      		     <input type="hidden" id="id_attivita_masterlist_4" name="id_attivita_masterlist_4" value="-1">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice4" name="codice4" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice4',  'id_rel_4' ); abilita_codici_ateco_vuoti();" value="" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice4','cod4', 'id_attivita_masterlist_4', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod4" name="cod4" value="" >
      		 	<br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_4" , -1 ) ) );
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice4" style="display: none">
      		 	<b>Codice 5&nbsp;&nbsp;</b>
      		    <input type="hidden" id="id_attivita_masterlist_5" name="id_attivita_masterlist_5" value="-1">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice5" name="codice5" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice5',  'id_rel_5' ); abilita_codici_ateco_vuoti();" value="" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice5', 'cod5','id_attivita_masterlist_5','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod5" name="cod5" value="" >
      		    <br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_5" , -1 ) ) );
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice5" style="display: none">
      		 	<b>Codice 6&nbsp;&nbsp;</b>
      		    <input type="hidden" id="id_attivita_masterlist_6" name="id_attivita_masterlist_6" value="-1">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice6" name="codice6" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice6',  'id_rel_6' ); abilita_codici_ateco_vuoti();" value="" >
      		  	[<a href="javascript:popLookupSelectorCustomImprese('codice6','cod6', 'id_attivita_masterlist_6', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod6" name="cod6" value="" >
      		  	<br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_6" , -1 ) ) );
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice6" style="display: none">
      		 	<b>Codice 7&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_7" name="id_attivita_masterlist_7" value="-1">
      		  	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice7" name="codice7" class="codiciatecolista"
      		  		onchange="costruisci_rel_ateco_attivita('codice7',  'id_rel_7' ); abilita_codici_ateco_vuoti();" value="" >
      		  	[<a href="javascript:popLookupSelectorCustomImprese('codice7','cod7','id_attivita_masterlist_7', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		  	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod7" name="cod7" value="" >
      		  	<br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_7" , -1 ) ) );
				%><br/>
				<br></br>
      		 </div>	

      		 <div id="div_codice7" style="display: none">
      		 	<b>Codice 8&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_8" name="id_attivita_masterlist_8" value="-1">
      		 	<input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice8" name="codice8" class="codiciatecolista"
      		 		onchange="costruisci_rel_ateco_attivita('codice8',  'id_rel_8' ); abilita_codici_ateco_vuoti();" value="" >
      		 	[<a href="javascript:popLookupSelectorCustomImprese('codice8', 'cod8','id_attivita_masterlist_8','attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
      		 	<br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod8" name="cod8" value="" >
      		 	<br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_8" , -1 ) ) );
				%><br/>
				<br></br>
      		 </div>

      		 <div id="div_codice8" style="display: none">
      		 	<b>Codice 9&nbsp;&nbsp;</b>
      		 	<input type="hidden" id="id_attivita_masterlist_9" name="id_attivita_masterlist_9" value="-1">
         	    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice9" name="codice9" class="codiciatecolista"
         	    	onchange="costruisci_rel_ateco_attivita('codice9',  'id_rel_9' ); abilita_codici_ateco_vuoti();" value="" >
         	    [<a href="javascript:popLookupSelectorCustomImprese('codice9','cod9', 'id_attivita_masterlist_9', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
         	    <br/><input style="background-color: lightgray" readonly="readonly" type="text" size="150" id="cod9" name="cod9" value="" >
         	    <br/><%
						out.println( (lookup_vuota_linea_attivita.getHtmlSelect("id_rel_9" , -1 ) ) );
				%><br/>
				<br></br>	
         	 </div>
      		 
			<div id="div_codice9" style="display: none">
      		 	<b>Codice 10</b>
      		 	<input type="hidden" id="id_attivita_masterlist_10" name="id_attivita_masterlist_10" value="-1">
      		    <input style="background-color: lightgray" size="10px" readonly="readonly" type="text" size="20" id="codice10" name="codice10" class="codiciatecolista"
      		    	onchange="costruisci_rel_ateco_attivita('codice10', 'id_rel_10'); abilita_codici_ateco_vuoti();" value="" >
      		    [<a href="javascript:popLookupSelectorCustomImprese('codice10', 'cod10', 'id_attivita_masterlist_10', 'attivita_852_ateco_masterlist','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
  
  
 
  
   <tr >
    <td class="formLabel" nowrap>
      <dhv:label name="">Domicilio Digitale</dhv:label>
    </td>
    <td>
      <input type="text" size="20" maxlength="" name="domicilioDigitale" value="<%= toHtmlValue(OrgDetails.getDomicilioDigitale()) %>">    
    </td>
  </tr>
 
  

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
        	
   
        	<input readonly type="text" id="dateI" name="dateI" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dateI,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
          	</td>
       
       	 	
           	<td style="visibility: hidden;" id="data2">
           		Al
           	</td>
           
            	<td style="visibility: hidden;" id="data4">
            	
            		<input readonly type="text" id="dateF" name="dateF" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dateF,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>
		
           	</td>
           	<td style="visibility: hidden;" id="cessazione">
           	<input type="checkbox" name="cessazione" value ="true"  checked /> <dhv:label name="accounts.Assetsf">Cessazione Automatica</dhv:label>
           	</td>
          
    </tr>
       </table>
        </td>
          	
        
        
        
        
     
        
        
    </tr>

 
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data Presentazione D.I.A./Inizio Attività</dhv:label>
      </td>
      <td>
      	<input readonly type="text" id="dataPresentazione" name="dataPresentazione" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].dataPresentazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
        
      
        <%= showAttribute(request, "date1Error") %><font color="red">*</font>
      </td>
    </tr>
   
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
 
 
  <!--  aggiunto da d.dauria -->
 
  </table>
  
  
  <br>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>
      <input type = "hidden" name = "cessato" value = "0">
    </th>
  </tr>
  
  <tr >
    <td class="formLabel" nowrap>
      Codice Fiscale
    </td>
    <td>
      <input type="text" size="30" maxlength="16" name="codiceFiscaleRappresentante" value="<%= toHtmlValue(OrgDetails.getCodiceFiscaleRappresentante()) %>"><font color="red">*</font>
      &nbsp; &nbsp;
	 <input name="validaCf" type="checkbox" value="validaCf"  onclick="javascript:if(this.checked) { if(codiceFISCALE(document.forms[0].codiceFiscaleRappresentante.value)){alert('CF corretto')} else {alert('CF non valido!')}};" /> Valida CF		         
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
      	<input readonly type="text" id="dataNascitaRappresentante" name="dataNascitaRappresentante" size="10" />
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
  boolean noneSelected = false;

%>

<%-- 
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
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(AddressSedeLegale.getCity())) {%> selected="selected" <%} %>><%= prov4 %></option>	
              <%}%>
		
	</select> 
	
    <input type="text" name="address1city" id="address1city" value = "<%=toHtmlValue(AddressSedeLegale.getCity()) %>" style="display: block;">
	</td><td><div id = "sl"></div> </td></table>
	
	</td>
  	</tr>	
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.AddressLine1">Address Line 1</dhv:label>
    </td>
    <td>
      <input type="text" size="40" id="address1line1" name="address1line1" maxlength="80" value="<%= toHtmlValue(AddressSedeLegale.getStreetAddressLine1()) %>"><font color="red">*</font>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="">C/O</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="address1line2" maxlength="80" value = "<%=toHtmlValue(AddressSedeLegale.getStreetAddressLine2()) %>">
    </td>
  </tr>

  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.ZipPostalCode">Zip/Postal Code</dhv:label>
    </td>
    <td>
      <input type="text" size="28" id="address1zip" name="address1zip" maxlength="5" value = "<%=toHtmlValue(AddressSedeLegale.getZip()) %>">
    </td>
  </tr>
  
  	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
    	  <input type="text" size="28" id="address1state" name="address1state" maxlength="80" value="<%= toHtmlValue(AddressSedeLegale.getCityState()) %>">          
      <div id="div_provincia" style="display: none;">  
      <input type="text"  size="28" name="address1state1" maxlength="80" >  
          
       </div>      
    </td>
  </tr>
 
  
  
   <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	<input type="text" id="address1latitude" name="address1latitude" size="30" value="<%=AddressSedeLegale.getLatitude() %>" >
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td><input type="text" id="address1longitude" name="address1longitude" size="30" value="<%=AddressSedeLegale.getLongitude() %>" ></td>
  </tr>
  <tr style="display: block">
    <td colspan="2">
    	<input id="coordbutton" type="button" value="Calcola Coordinate"
    	 onclick="javascript:showCoordinate(document.getElementById('address1line1').value, document.getElementById('address1city').value,document.forms['addAccount'].address1state.value, document.forms['addAccount'].address1zip.value, document.forms['addAccount'].address1latitude, document.forms['addAccount'].address1longitude);"  
    	/> 
    </td>
  </tr> 
</table>
--%>

   
<%--    
<script>
function setta_sede_legale(sl_city,sl_line1,sl_zip,sl_state,sl_latitude,sl_longitude,so_city,so_line1,so_zip,so_state,so_latitude,so_longitude){
 	var chk = document.getElementById("chk_setta_sede_legale");
 	if (chk.checked==true){
		document.getElementById(sl_city).value = so_city.value; 
		document.getElementById(sl_city).disabled=""; 
		document.getElementById(sl_city).readOnly="readOnly";
		
		document.getElementById(sl_zip).value = so_zip.value;   
		document.getElementById(sl_zip).disabled=""; 
		document.getElementById(sl_zip).readOnly="readOnly";
		
		document.getElementById(sl_state).value = so_state.value; 
		document.getElementById(sl_state).disabled=""; 
		document.getElementById(sl_state).readOnly="readOnly";
		
		document.getElementById(sl_latitude).value = so_latitude.value;
		document.getElementById(sl_longitude).value = so_longitude.value;
		document.getElementById(sl_line1).value = so_line1.value;
 	} else {
 		document.getElementById(sl_city).value = ""; 
 		document.getElementById(sl_city).disabled="";
 		document.getElementById(sl_city).readOnly="";
 		
		document.getElementById(sl_zip).value = "";   
		document.getElementById(sl_zip).disabled="";
		document.getElementById(sl_zip).readOnly="";
		
		document.getElementById(sl_state).value = ""; 
		document.getElementById(sl_state).disabled="";
		document.getElementById(sl_state).readOnly="";

		document.getElementById(sl_latitude).value = "";
		document.getElementById(sl_longitude).value = "";
		document.getElementById(sl_line1).value = "";
 	}
}
</script> --%>

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
                Vector v1 = OrgDetails.getComuni2();
	 			Enumeration e1=v1.elements();
                while (e1.hasMoreElements()) {
                	String prov4=e1.nextElement().toString();
                	
        %>
                <option value="<%=prov4%>" <%if(prov4.equalsIgnoreCase(AddressSedeOperativa.getCity())) {%> selected="selected" <%} %>><%= prov4 %></option>	
              <%}%>
		
	</select> 
	<div id = "so"></div> 
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
      <input type="text" size="28" name="address2zip" maxlength="5" value = "<%=toHtmlValue(AddressSedeOperativa.getZip()) %>" id="cap">
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
          <% if (User.getSiteId() == 204 || User.getSiteId() == 205 || User.getSiteId() == 206 ) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="Napoli">
          <%}%>
          <% if (User.getSiteId() == 207) { %>
          <input type="text" readonly="readonly" size="28" name="address2state" maxlength="80" value="Salerno">
          <%}%>
          <% if (User.getSiteId() == -1) { %>
          <input type="text"  size="28" name="address2state" maxlength="80" value="">
          <%}%>
    </td>
  </tr>
  
  
  
 
  <tr class="containerBody">
    <td class="formLabel" nowrap id="latitude2"><dhv:label name="requestor.address.latitude">Latitude</dhv:label></td>
    <td>
    	    	<input type="text" id="address2latitude" name="address2latitude" size="30" readonly="readonly" value="<%=AddressSedeOperativa.getLatitude() %>">
    	
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap id="longitude2"><dhv:label name="requestor.address.longitude">Longitude</dhv:label></td>
    <td>
    	<%-- <input type="text" id="address2longitude" name="address2longitude" size="30" value="<%=AddressSedeOperativa.getLongitude() %>" id="longitude12">--%>
    	<input type="text" id="address2longitude" name="address2longitude" size="30" readonly="readonly" value="<%=AddressSedeOperativa.getLongitude() %>">
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
	<div id ="mob"></div>	
	
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
      <input type="text" size="28" name="address3zip" value ="<%= toHtmlValue(AddressSedeMobile.getZip() ) %>" maxlength="5" id="addresszip">
    </td>
  </tr>
  	
  	<tr>
    <td nowrap class="formLabel" id="stateProv1" >
      <dhv:label name="requestor.requestor_add.StateProvince">State/Province</dhv:label>
    </td>
    <td>
          <% if (User.getSiteId() == 202) { %>
          <input type="text" readonly="readonly" size="28" name="address3state" maxlength="80" value="BN">          
          <%}%>
          <% if (User.getSiteId() == 201) { %>
          <input type="text" readonly="readonly" size="28" name="address3state" maxlength="80" value="AV">
          <%}%>
          <% if (User.getSiteId() == 203) { %>
          <input type="text" readonly="readonly" size="28" name="address3state" maxlength="80" value="CE">
          <%}%>
          <% if (User.getSiteId() == 204 || User.getSiteId() == 205 || User.getSiteId() == 206) { %>
          <input type="text" readonly="readonly" size="28" name="address3state" maxlength="80" value="NA">
          <%}%>
          <% if (User.getSiteId() == 207) { %>
          <input type="text" readonly="readonly" size="28" name="address3state" maxlength="80" value="SA">
          <%}%>
          <% if (User.getSiteId() == -1) { %>
          <input type="text" size="28" name="address3state" maxlength="80" value="">
          <%}%>
    </td>
  </tr>
  

  

  
  
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
   <tr style="display:block">
    <td colspan="2">
    <input id="coord2button" type="button" value="Calcola Coordinate" disabled="disabled" 
    onclick="javascript:showCoordinate(document.getElementById('addressline1').value, document.forms['addAccount'].address3city.value,document.forms['addAccount'].address3state.value, document.forms['addAccount'].address3zip.value, document.forms['addAccount'].address3latitude, document.forms['addAccount'].address3longitude);" />
     </td>
    </tr>
</table>
<br><br>

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
   
        <%= TipoLocale.getHtmlSelect("TipoLoc",OrgDetails.getTipoLocale())%>
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



<br>

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
<input type="submit" value="Inserisci" name="Save" >
<dhv:evaluate if="<%= !popUp %>">
  <input type=submit value="Annulla" onClick="window.location.href='Accounts.do'">
</dhv:evaluate>
<dhv:evaluate if="<%= popUp %>">
  <input type="button" value="Annulla" onClick="javascript:self.close();">
</dhv:evaluate>
<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="cancel" value="false" />
<input type = "hidden" name = "forzainserimento" id = "forzaInsert" value = "0">
</form>
<script>
document.getElementById('elementi').value = 0;
document.getElementById('size').value = 0;
</script>
