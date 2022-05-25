<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Capo"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@page import="org.aspcfs.modules.macellazioninew.utils.MacelliUtil"%>

<%boolean update = ( ( (Boolean)request.getAttribute( "Update" ) ) == null) ? (false) : (true);%>
<%! SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); %>

<%@ include file="../initPage.jsp"%>

<%@page import="org.aspcfs.utils.DateUtils"%>
<jsp:useBean id="Tampone"			class="org.aspcfs.modules.macellazioninew.base.Tampone"		scope="request" />
<jsp:useBean id="MatriciTamponi"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AnalisiTamponi"	class="org.aspcfs.utils.web.LookupList" scope="request" />

<%@page import="org.aspcfs.modules.macellazioninew.base.NonConformita"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.PatologiaRilevata"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Casl_Non_Conformita_Rilevata"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.ProvvedimentiCASL"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Organi"%>
<%@page import="org.aspcfs.modules.contacts.base.Contact"%>
<%@page import="java.util.Date"%>

<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>

<jsp:useBean id="OrgDetails"		class="org.aspcfs.modules.stabilimenti.base.Organization"	scope="request" />
<jsp:useBean id="Capo"				class="org.aspcfs.modules.macellazioninew.base.Capo"			scope="request" />
<jsp:useBean id="CapoLog"			class="org.aspcfs.modules.macellazioninew.base.CapoLog"		scope="request" />
<jsp:useBean id="Speditore" class="org.aspcfs.modules.speditori.base.Organization" 	scope="request" />
<jsp:useBean id="SpeditoreAddress" class="org.aspcfs.modules.speditori.base.OrganizationAddress" 	scope="request" />
<!--<jsp:useBean id="Campione"			class="org.aspcfs.modules.macellazioninew.base.Campione"		scope="request" />-->
<!--<jsp:useBean id="Organo"			class="org.aspcfs.modules.macellazioninew.base.Organi"			scope="request" />-->

<jsp:useBean id="NCVAM"				class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="Campioni"			class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="OrganiList" 		class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="OrganiListNew" 	class="java.util.TreeMap"				scope="request" />
<jsp:useBean id="PatologieRilevate"	class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="Provvedimenti"		class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="casl_NCRilevate"	class="java.util.ArrayList"				scope="request" />

<jsp:useBean id="casl_Provvedimenti_effettuati"	class="java.util.ArrayList"				scope="request" />

<jsp:useBean id="Nazioni"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Matrici"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ASL"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LuoghiVerifica"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Regioni"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Razze"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Specie"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategorieBovine"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategorieBufaline"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianiRisanamento"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="BseList"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiMacellazione"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitiVpm"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Patologie"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PatologieOrgani"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiEsame"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Stadi"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Organi"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiAnalisi"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Molecole"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiNonConformita"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MotiviASL"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvvedimentiVAM"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="look_ProvvedimentiCASL"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Veterinari"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitiCampioni"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MotiviCampioni"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MolecolePNR"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MolecoleBatteriologico"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MolecoleParassitologico"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiNonConformita_Gruppo"	class="org.aspcfs.utils.web.LookupList" scope="request" />


<jsp:useBean id="lookup_lesione_generici"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_altro"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_milza"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_cuore"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_polmoni"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_fegato"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_rene"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_mammella"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_apparato_genitale"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_stomaco"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_intestino"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_osteomuscolari"		class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="User"		class="org.aspcfs.modules.login.beans.UserBean" scope="session" />



<link type="text/css" href="css/ui.all.css" rel="stylesheet" />
<link type="text/css" href="css/demos.css" rel="stylesheet" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script type="text/javascript" src="javascript/ui.core.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<script type="text/javascript" src="javascript/ui.tabs.js"></script>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>


<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript">


var message = "";
function verificaStampaMod10(idMacello,dataPostmortem)
{

		if (dataPostmortem != '')
			PopolaCombo.verificaStampaMod10(idMacello , dataPostmortem,0,verificaStampaMod10CallBack);
	   
}

 function verificaStampaMod10CallBack(returnValue)
 {
		message = '' ;
	if (returnValue+''=='true')
	{
		
		ret = false ;
		message += label("","- [Visita Post Mortem] : \"Data Macellazione Non Valida.\" per Questa seduta è stata effettuata una stampa del Modello 10\r\n" );
alert(message);
		}
	
	


 }
</script>

<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
cal19.showNavigationDropdowns();
</SCRIPT>
<!-- ******************************************************************** -->

<script type="text/javascript">

//funzioni data
var MONTH_NAMES=new Array('January','February','March','April','May','June','July','August','September','October','November','December','Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');var DAY_NAMES=new Array('Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sun','Mon','Tue','Wed','Thu','Fri','Sat');
function LZ(x){return(x<0||x>9?"":"0")+x}
function isDate(val,format){var date=getDateFromFormat(val,format);if(date==0){return false;}return true;}
function compareDates(date1,dateformat1,date2,dateformat2){var d1=getDateFromFormat(date1,dateformat1);var d2=getDateFromFormat(date2,dateformat2);if(d1==0 || d2==0){return -1;}else if(d1 > d2){return 1;}return 0;}
function formatDate(date,format){format=format+"";var result="";var i_format=0;var c="";var token="";var y=date.getYear()+"";var M=date.getMonth()+1;var d=date.getDate();var E=date.getDay();var H=date.getHours();var m=date.getMinutes();var s=date.getSeconds();var yyyy,yy,MMM,MM,dd,hh,h,mm,ss,ampm,HH,H,KK,K,kk,k;var value=new Object();if(y.length < 4){y=""+(y-0+1900);}value["y"]=""+y;value["yyyy"]=y;value["yy"]=y.substring(2,4);value["M"]=M;value["MM"]=LZ(M);value["MMM"]=MONTH_NAMES[M-1];value["NNN"]=MONTH_NAMES[M+11];value["d"]=d;value["dd"]=LZ(d);value["E"]=DAY_NAMES[E+7];value["EE"]=DAY_NAMES[E];value["H"]=H;value["HH"]=LZ(H);if(H==0){value["h"]=12;}else if(H>12){value["h"]=H-12;}else{value["h"]=H;}value["hh"]=LZ(value["h"]);if(H>11){value["K"]=H-12;}else{value["K"]=H;}value["k"]=H+1;value["KK"]=LZ(value["K"]);value["kk"]=LZ(value["k"]);if(H > 11){value["a"]="PM";}else{value["a"]="AM";}value["m"]=m;value["mm"]=LZ(m);value["s"]=s;value["ss"]=LZ(s);while(i_format < format.length){c=format.charAt(i_format);token="";while((format.charAt(i_format)==c) &&(i_format < format.length)){token += format.charAt(i_format++);}if(value[token] != null){result=result + value[token];}else{result=result + token;}}return result;}
function _isInteger(val){var digits="1234567890";for(var i=0;i < val.length;i++){if(digits.indexOf(val.charAt(i))==-1){return false;}}return true;}
function _getInt(str,i,minlength,maxlength){for(var x=maxlength;x>=minlength;x--){var token=str.substring(i,i+x);if(token.length < minlength){return null;}if(_isInteger(token)){return token;}}return null;}
function getDateFromFormat(val,format){val=val+"";format=format+"";var i_val=0;var i_format=0;var c="";var token="";var token2="";var x,y;var now=new Date();var year=now.getYear();var month=now.getMonth()+1;var date=1;var hh=now.getHours();var mm=now.getMinutes();var ss=now.getSeconds();var ampm="";while(i_format < format.length){c=format.charAt(i_format);token="";while((format.charAt(i_format)==c) &&(i_format < format.length)){token += format.charAt(i_format++);}if(token=="yyyy" || token=="yy" || token=="y"){if(token=="yyyy"){x=4;y=4;}if(token=="yy"){x=2;y=2;}if(token=="y"){x=2;y=4;}year=_getInt(val,i_val,x,y);if(year==null){return 0;}i_val += year.length;if(year.length==2){if(year > 70){year=1900+(year-0);}else{year=2000+(year-0);}}}else if(token=="MMM"||token=="NNN"){month=0;for(var i=0;i<MONTH_NAMES.length;i++){var month_name=MONTH_NAMES[i];if(val.substring(i_val,i_val+month_name.length).toLowerCase()==month_name.toLowerCase()){if(token=="MMM"||(token=="NNN"&&i>11)){month=i+1;if(month>12){month -= 12;}i_val += month_name.length;break;}}}if((month < 1)||(month>12)){return 0;}}else if(token=="EE"||token=="E"){for(var i=0;i<DAY_NAMES.length;i++){var day_name=DAY_NAMES[i];if(val.substring(i_val,i_val+day_name.length).toLowerCase()==day_name.toLowerCase()){i_val += day_name.length;break;}}}else if(token=="MM"||token=="M"){month=_getInt(val,i_val,token.length,2);if(month==null||(month<1)||(month>12)){return 0;}i_val+=month.length;}else if(token=="dd"||token=="d"){date=_getInt(val,i_val,token.length,2);if(date==null||(date<1)||(date>31)){return 0;}i_val+=date.length;}else if(token=="hh"||token=="h"){hh=_getInt(val,i_val,token.length,2);if(hh==null||(hh<1)||(hh>12)){return 0;}i_val+=hh.length;}else if(token=="HH"||token=="H"){hh=_getInt(val,i_val,token.length,2);if(hh==null||(hh<0)||(hh>23)){return 0;}i_val+=hh.length;}else if(token=="KK"||token=="K"){hh=_getInt(val,i_val,token.length,2);if(hh==null||(hh<0)||(hh>11)){return 0;}i_val+=hh.length;}else if(token=="kk"||token=="k"){hh=_getInt(val,i_val,token.length,2);if(hh==null||(hh<1)||(hh>24)){return 0;}i_val+=hh.length;hh--;}else if(token=="mm"||token=="m"){mm=_getInt(val,i_val,token.length,2);if(mm==null||(mm<0)||(mm>59)){return 0;}i_val+=mm.length;}else if(token=="ss"||token=="s"){ss=_getInt(val,i_val,token.length,2);if(ss==null||(ss<0)||(ss>59)){return 0;}i_val+=ss.length;}else if(token=="a"){if(val.substring(i_val,i_val+2).toLowerCase()=="am"){ampm="AM";}else if(val.substring(i_val,i_val+2).toLowerCase()=="pm"){ampm="PM";}else{return 0;}i_val+=2;}else{if(val.substring(i_val,i_val+token.length)!=token){return 0;}else{i_val+=token.length;}}}if(i_val != val.length){return 0;}if(month==2){if( ((year%4==0)&&(year%100 != 0) ) ||(year%400==0) ){if(date > 29){return 0;}}else{if(date > 28){return 0;}}}if((month==4)||(month==6)||(month==9)||(month==11)){if(date > 30){return 0;}}if(hh<12 && ampm=="PM"){hh=hh-0+12;}else if(hh>11 && ampm=="AM"){hh-=12;}var newdate=new Date(year,month-1,date,hh,mm,ss);return newdate.getTime();}
function parseDate(val){var preferEuro=(arguments.length==2)?arguments[1]:false;generalFormats=new Array('y-M-d','MMM d, y','MMM d,y','y-MMM-d','d-MMM-y','MMM d');monthFirst=new Array('M/d/y','M-d-y','M.d.y','MMM-d','M/d','M-d');dateFirst =new Array('d/M/y','d-M-y','d.M.y','d-MMM','d/M','d-M');var checkList=new Array('generalFormats',preferEuro?'dateFirst':'monthFirst',preferEuro?'monthFirst':'dateFirst');var d=null;for(var i=0;i<checkList.length;i++){var l=window[checkList[i]];for(var j=0;j<l.length;j++){d=getDateFromFormat(val,l[j]);if(d!=0){return new Date(d);}}}return null;}
//

// Function che restituisce la data attuale in formato dd/mm/aaaa
function dataAttuale() {
	try
	  {
		var a = formatDate(new Date(),'d/MM/y');
	  }
	catch(err)
	  {
		alert(err);
	  }
	return a;
}

function gestisciRazza() {
	try
	  {
		var BOVINI=1;
		var specie = document.getElementById('cd_specie').value; // .value;
	
		if (specie == BOVINI){
			document.getElementById('razza_bovina').style.display = "";
			if(document.getElementById('cd_id_razza') && document.getElementById('cd_id_razza').value == 999 ){
				document.getElementById('cd_razza_altro').style.display = "";
			}
			else{
				document.getElementById('cd_razza_altro').value = "";
				document.getElementById('cd_razza_altro').style.display = "none";
			}
			
		}else {
			document.getElementById('razza_bovina').style.display = "none";
			document.getElementById('cd_razza_altro').value = "";
			document.getElementById('cd_razza_altro').style.display = "none";
		}
	  }
	catch(err)
	  {
		alert(err);
	  }
}

function gestisciCategoria() {
	try
	  {
		var BOVINI=1;
		var BUFALINI=5;
		
		var specie = document.getElementById('cd_specie').value; // .value;
	
		if (specie == BOVINI){
			document.getElementById('categoria_bovina').style.display = "";
			document.getElementById('categoria_bufalina').style.display = "none";
			document.getElementById('cd_categoria_bufalina').value = -1;
		}
		else if (specie == BUFALINI){
			document.getElementById('categoria_bovina').style.display = "none";
			document.getElementById('cd_categoria_bovina').value = -1;
			document.getElementById('categoria_bufalina').style.display = "";
		}
		else{
			document.getElementById('categoria_bovina').style.display = "none";
			document.getElementById('cd_categoria_bovina').value = -1;
			document.getElementById('categoria_bufalina').style.display = "none";
			document.getElementById('cd_categoria_bufalina').value = -1;
			}
	  }
	catch(err)
	  {
		alert(err);
	  }
}

function gestisciBloccoAnimale() {

	var lista_selezionata = document.getElementById('casl_provvedimenti_selezionati'); // .value;
	var isBloccoAnimaleSelezionato=lista_selezionata[1].selected;


	if (isBloccoAnimaleSelezionato){
		document.getElementById('seqa_destinazione_allo_sblocco').value = -1;
		document.getElementById('blocco_animale_div').style.display = "";
	}else {
		document.getElementById('blocco_animale_div').style.display = "none";
	}
}

function setCategoriaRischio()
{


	var dataMorteAnteMacellazione = document.getElementById('mavam_data').value != '';
	var luogoMorteAnteMacellazione = document.getElementById('mavam_luogo').selectedIndex > 0;
	var causaMorteAnteMacellazione = trim( document.getElementById('mavam_motivo').value ) != '';

	

	if(document.getElementById('cd_specie').value =='1' || document.getElementById('cd_specie').value =='5')
	{
	if (document.getElementById("mac_tipo").value == '3' )
	{
		if(document.forms[0].cd_categoria_rischio.value !='2')
		{
			alert('Settata la categoria di rischio a 2 : Macellazione di Urgenza con test bse animale superiore a 48 mesi');
		}
		document.getElementById('cd_categoria_rischio_2').checked = true ;
		
	}
	
	
		if (document.getElementById('cd_macellazione_differita')!= null && parseInt(document.getElementById('cd_macellazione_differita').value )>0)
		{
			if(document.forms[0].cd_categoria_rischio.value !='3')
			{
				alert('Settata la categoria di rischio a 3 : Macellazione Differita con BRC,LEU,TBC');
			}
			document.getElementById('cd_categoria_rischio_3').checked = true ;
		}

		if(dataMorteAnteMacellazione && luogoMorteAnteMacellazione && causaMorteAnteMacellazione)
		{
			if(document.forms[0].cd_categoria_rischio.value !='5')
			{
				alert('Settata la categoria di rischio a 5 : capo morto prima della macellazione');
			}
			
			document.getElementById('cd_categoria_rischio_5').checked = true ;
			
		}
		
		
		document.getElementById('cd_categoria_rischio_1').checked = true ;
	}
	

	
	
}

function displayStadio2( index )
{
	var MILZA 			= 1;
	var CUORE 			= 2;
	var POLMONI 		= 3;
	var FEGATO 			= 4;
	var RENE 			= 5;
	var MAMMELLA 		= 6;
	var GENITALE 		= 7;
	var STOMACO 		= 8;
	var INTESTINO 		= 9;
	var OSTEOMUSCOLARI	= 10;

	value_organo = document.getElementById('lcso_organo_'+String(index)).value;

//	alert('value_organo : ' + value_organo );
//	alert(index);

	var a = document.getElementById('lesione_milza_'+String(index)).value;
	//	alert('Milza_value : ' + a);

	var b = document.getElementById('lesione_cuore_'+String(index)).value;
	//alert('cuore_value : ' + b);

	var c = document.getElementById('polmoni_value'+String(index)).value;
	//	alert('polmoni_value : ' + c);

	
	
	if (value_organo == MILZA)  {

		//		alert('Milza_value' + document.getElementById('lesione_milza_'+String(index)).value );
		
		if( document.getElementById('lesione_milza_'+String(index)).value == 6 )	{
			document.getElementById( "stadio_" + index ).style.display = "table-row";
		}else{
			document.getElementById( "stadio_" + index ).style.display = "none";
		}
	}
	
	if (value_organo == CUORE)  {
		//	alert('************************************')
	//	alert('cuore_value' + document.getElementById('lesione_cuore_'+String(index)).value );
		
		if( document.getElementById('lesione_cuore_'+String(index)).value == 6 )	{
			alert('adesso dovrei comparire');
			document.getElementById( "stadio_" + index ).style.display = "table-row";
		}else{
			alert('adesso non dovrei comparire');
			document.getElementById( "stadio_" + index ).style.display = "none";
		}
	}
	
	if (value_organo == POLMONI){

		//	alert('polmoni_value' + document.getElementById('lesione_polmoni_'+String(index)).value );
		
		if( document.getElementById('lesione_polmoni_'+String(index)).value == 6 )	{
			document.getElementById( "stadio_" + index ).style.display = "table-row";
		}else{
			document.getElementById( "stadio_" + index ).style.display = "none";
		}
	}
	
	if (value_organo == FEGATO)	{
		if( document.getElementById('lesione_fegato_'+String(index)).value == 6 )	{
			document.getElementById( "stadio_" + index ).style.display = "table-row";
		}else{
			document.getElementById( "stadio_" + index ).style.display = "none";
		}
	}
	
	if (value_organo == RENE)	{
		if( document.getElementById('lesione_rene_'+String(index)).value == 6 )	{
			document.getElementById( "stadio_" + index ).style.display = "table-row";
		}else{
			document.getElementById( "stadio_" + index ).style.display = "none";
		}
	}
	
	if (value_organo == MAMMELLA){
		if( document.getElementById('lesione_mammella_'+String(index)).value == 6 )	{
			document.getElementById( "stadio_" + index ).style.display = "table-row";
		}else{
			document.getElementById( "stadio_" + index ).style.display = "none";
		}
	}
	
	if (value_organo == GENITALE){
		if( document.getElementById('lesione_apparato_genitale_'+String(index)).value == 6 )	{
			document.getElementById( "stadio_" + index ).style.display = "table-row";
		}else{
			document.getElementById( "stadio_" + index ).style.display = "none";
		}
	}
	
	if (value_organo == STOMACO){
		if( document.getElementById('lesione_stomaco_'+String(index)).value == 6 )	{
			document.getElementById( "stadio_" + index ).style.display = "table-row";
		}else{
			document.getElementById( "stadio_" + index ).style.display = "none";
		}
	}
	
	if (value_organo == INTESTINO){
		if( document.getElementById('lesione_intestino_'+String(index)).value == 6 )	{
			document.getElementById( "stadio_" + index ).style.display = "table-row";
		}else{
			document.getElementById( "stadio_" + index ).style.display = "none";
		}
	}
	
	if (value_organo == OSTEOMUSCOLARI){
		if( document.getElementById('lesione_osteomuscolari_'+String(index)).value == 6 )	{
			document.getElementById( "stadio_" + index ).style.display = "table-row";
		}else{
			document.getElementById( "stadio_" + index ).style.display = "none";
		}
	}
	
}


function vpm_seleziona_lookup_patologia_organo(indice_riga, value_organo, value_patologia)
{
	//alert("In prova().... Indice_riga=" + indice_riga + " value_organo=" +value_organo  + " value_patologia=" +value_patologia);
try {
	var MILZA 			= 1;
	var CUORE 			= 2;
	var POLMONI 		= 3;
	var FEGATO 			= 4;
	var RENE 			= 5;
	var MAMMELLA 		= 6;
	var GENITALE 		= 7;
	var STOMACO 		= 8;
	var INTESTINO 		= 9;
	var OSTEOMUSCOLARI	= 10;

	var ALTRO = 11;

	document.getElementById('lesione_milza_'+String(indice_riga)).style.display = "none";
	//document.getElementById('lesione_milza_'+String(indice_riga)).value = -1;
	document.getElementById('lesione_cuore_'+String(indice_riga)).style.display = "none";
	//document.getElementById('lesione_cuore_'+String(indice_riga)).value = -1;
	document.getElementById('lesione_polmoni_'+String(indice_riga)).style.display = "none";
	//document.getElementById('lesione_polmoni_'+String(indice_riga)).value = -1;
	document.getElementById('lesione_fegato_'+String(indice_riga)).style.display = "none";
	//document.getElementById('lesione_fegato_'+String(indice_riga)).value = -1;
	document.getElementById('lesione_rene_'+String(indice_riga)).style.display = "none";
	//document.getElementById('lesione_rene_'+String(indice_riga)).value = -1;
	document.getElementById('lesione_mammella_'+String(indice_riga)).style.display = "none";
	//document.getElementById('lesione_mammella_'+String(indice_riga)).value = -1;
	document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).style.display = "none";
	//document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).value = -1;
	document.getElementById('lesione_stomaco_'+String(indice_riga)).style.display = "none";
	//document.getElementById('lesione_stomaco_'+String(indice_riga)).value = -1;
	document.getElementById('lesione_intestino_'+String(indice_riga)).style.display = "none";
	//document.getElementById('lesione_intestino_'+String(indice_riga)).value = -1;
	document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).style.display = "none";
	//document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).value = -1;

	document.getElementById('lesione_generici_'+String(indice_riga)).style.display = "none";
	//document.getElementById('lesione_generici_'+String(indice_riga)).value = -1;

	document.getElementById('lesione_altro_'+String(indice_riga)).style.display = "none";
	//document.getElementById('lesione_altro_'+String(indice_riga)).value = -1;
	
	if (value_organo == MILZA)  {
				document.getElementById('lesione_milza_'+String(indice_riga)).style.display = "";
				//document.getElementById('lesione_milza_'+String(indice_riga)).value = value_patologia;
	}
	else if (value_organo == CUORE)  {
				document.getElementById('lesione_cuore_'+String(indice_riga)).style.display = "";
				//document.getElementById('lesione_cuore_'+String(indice_riga)).value = value_patologia;
	}
	else if (value_organo == POLMONI){
				document.getElementById('lesione_polmoni_'+String(indice_riga)).style.display = "";
				//document.getElementById('lesione_polmoni_'+String(indice_riga)).value = value_patologia;
	}
	else if (value_organo == FEGATO)	{
				document.getElementById('lesione_fegato_'+String(indice_riga)).style.display = "";
				//document.getElementById('lesione_fegato_'+String(indice_riga)).value = value_patologia;
	}
	else if (value_organo == RENE)	{
				document.getElementById('lesione_rene_'+String(indice_riga)).style.display = "";
				//document.getElementById('lesione_rene_'+String(indice_riga)).value = value_patologia;
	}
	else if (value_organo == MAMMELLA){
				document.getElementById('lesione_mammella_'+String(indice_riga)).style.display = "";
				//document.getElementById('lesione_mammella_'+String(indice_riga)).value = value_patologia;
	}
	else if (value_organo == GENITALE){
				document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).style.display = "";
				//document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).value = value_patologia;
	}
	else if (value_organo == STOMACO){
				document.getElementById('lesione_stomaco_'+String(indice_riga)).style.display = "";
				//document.getElementById('lesione_stomaco_'+String(indice_riga)).value = value_patologia;
	}
	else if (value_organo == INTESTINO){
				document.getElementById('lesione_intestino_'+String(indice_riga)).style.display = "";
				//document.getElementById('lesione_intestino_'+String(indice_riga)).value = value_patologia;
	}
	else if (value_organo == OSTEOMUSCOLARI){
				document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).style.display = "";
				//document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).value = value_patologia;
	}
	else if ( (value_organo == 13) || (value_organo == 14) || (value_organo == 15) || (value_organo == 16) || (value_organo == 17) || (value_organo == 18)
			|| (value_organo == 19) || (value_organo == 20) || (value_organo == 21) || (value_organo == 22) || (value_organo == 23) || (value_organo == 24) || (value_organo == 25)) {
		document.getElementById('lesione_generici_'+String(indice_riga)).style.display = "";
		//document.getElementById('lesione_generici_'+String(indice_riga)).value = value_patologia;
	}
	else{
		document.getElementById('lesione_altro_'+String(indice_riga)).style.display = "";
		//document.getElementById('lesione_altro_'+String(indice_riga)).value = value_patologia;
	}
}
catch(err)
{
	alert(err.description);
}
	
}



function vpm_resetta_lookup_patologia_organo(indice_riga, value_organo, value_patologia)
{

	try {
		var MILZA 			= 1;
		var CUORE 			= 2;
		var POLMONI 		= 3;
		var FEGATO 			= 4;
		var RENE 			= 5;
		var MAMMELLA 		= 6;
		var GENITALE 		= 7;
		var STOMACO 		= 8;
		var INTESTINO 		= 9;
		var OSTEOMUSCOLARI	= 10;
	
		var ALTRO = 11;
	
		document.getElementById('lesione_milza_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_milza_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_cuore_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_cuore_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_polmoni_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_polmoni_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_fegato_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_fegato_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_rene_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_rene_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_mammella_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_mammella_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_stomaco_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_stomaco_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_intestino_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_intestino_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).value = -1;
	
		document.getElementById('lesione_generici_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_generici_'+String(indice_riga)).value = -1;
	
		document.getElementById('lesione_altro_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_altro_'+String(indice_riga)).value = -1;
		
		if (value_organo == MILZA)  {
					document.getElementById('lesione_milza_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == CUORE)  {
					document.getElementById('lesione_cuore_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == POLMONI){
					document.getElementById('lesione_polmoni_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == FEGATO)	{
					document.getElementById('lesione_fegato_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == RENE)	{
					document.getElementById('lesione_rene_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == MAMMELLA){
					document.getElementById('lesione_mammella_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == GENITALE){
					document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == STOMACO){
					document.getElementById('lesione_stomaco_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == INTESTINO){
					document.getElementById('lesione_intestino_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == OSTEOMUSCOLARI){
					document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).style.display = "";
		}
		else if ( (value_organo == 13) || (value_organo == 14) || (value_organo == 15) || (value_organo == 16) || (value_organo == 17) || (value_organo == 18)
				|| (value_organo == 19) || (value_organo == 20) || (value_organo == 21) || (value_organo == 22) || (value_organo == 23) || (value_organo == 24) || (value_organo == 25)) {
			document.getElementById('lesione_generici_'+String(indice_riga)).style.display = "";
		}
		else{
			document.getElementById('lesione_altro_'+String(indice_riga)).style.display = "";
		}
	}
	catch(err)
	{
		alert(err.description);
	}
	
}


function mostra_tab_test_bse() {

	//alert("mostra_tab_test_bse: partita");
	
	try
	  {
		var BOVINI=1;
		var BUFALINI=5;
		
		id_specie_selezionata = document.getElementById('cd_specie').value;
	
		annullaID_cd_bse();
		//document.getElementById('cd_bse_check').checked = false;
		//document.getElementById('cd_bse_check').value = false;
		
		
		if (id_specie_selezionata == BOVINI) {
			document.getElementById('razza_bovina').style.display = "";
		} else {
			document.getElementById('razza_bovina').style.display = "none";
			document.getElementById('cd_id_razza').value = "-1";
		}
		
		if (id_specie_selezionata == BOVINI || id_specie_selezionata == BUFALINI) {
			document.getElementById('cd_bse').style.display = "none";
			document.getElementById('test_bse_etichetta').style.display = "";
			document.getElementById('test_bse_valore').style.display = "";
		} else {
			//document.getElementById('tab_test_bse').style.display = "none";
			document.getElementById('test_bse_etichetta').style.display = "none";
			document.getElementById('test_bse_valore').style.display = "none";
		}
	  }
	catch(err)
	  {
		alert(err);
	  }
	  
}

function mostra_tab_test_bse_onload() {

	//alert("mostra_tab_test_bse: partita");
	try
	  {
		var BOVINI=1;
		var BUFALINI=5;

		
		
		id_specie_selezionata = document.getElementById('cd_specie').value;

		//alert("document.getElementById('cd_bse').value : "+document.getElementById('cd_bse').value);
		//alert("document.getElementById('cd_bse_check').value : " + document.getElementById('cd_bse_check').value);
		
		
		if ( document.getElementById('cd_bse').value == '-1' )
		{
			annullaID_cd_bse_onload();
			document.getElementById('cd_bse_check').checked = false;
			//alert('checked = false');
		} else {
			document.getElementById('cd_bse_check').checked = true;
			document.getElementById('cd_bse_list').style.display = "block";
			document.getElementById('cd_bse').style.display = "block";
			//toggleDiv('cd_bse_list');
			//alert('checked = true');
		}

		if (document.getElementById('cd_bse_check').checked) {
			//alert('asdadadasdasdasdasdasd');
			//toggleDiv('cd_bse_list');
			document.getElementById('cd_bse_list').style.display = "block";
			document.getElementById('cd_bse').style.display = "block";
		}
		
		if (id_specie_selezionata == BOVINI) {
			document.getElementById('razza_bovina').style.display = "";
		} else {
			document.getElementById('razza_bovina').style.display = "none";
			document.getElementById('cd_id_razza').value = "-1";
		}
		
		if (id_specie_selezionata == BOVINI || id_specie_selezionata == BUFALINI) {
			//document.getElementById('cd_bse').style.display = "none";
			document.getElementById('test_bse_etichetta').style.display = "";
			document.getElementById('test_bse_valore').style.display = "";
		} else {
			//document.getElementById('tab_test_bse').style.display = "none";
			document.getElementById('test_bse_etichetta').style.display = "none";
			document.getElementById('test_bse_valore').style.display = "none";
		}
	  }
	catch(err)
	  {
		alert(err.description);
	  }
}


//index è l'indice del destinatario (1 se il primo 2 se il secondo)
function selectDestinazioneFromLinkTextarea( index )
{
	try {
		document.getElementById( 'destinatario_label_' + index ).innerHTML	= "-- Seleziona Destinatario --";
		if(document.getElementById( 'destinatario_' + index + "_id" ).value != "-999"){
			document.getElementById( 'destinatario_' + index + "_id" ).value	= "-1";
			document.getElementById( 'destinatario_' + index + "_nome" ).value	= "";
			document.getElementById('esercenteNoGisa' + index).value = '';
			document.getElementById('esercenteFuoriRegione' + index).value = '';
		}
		
		
		
		
		inReg = document.getElementById( "inRegione_" + index );
		fuoriReg = document.getElementById( "outRegione_" + index );
	
		if( inReg.checked )
		{
			document.getElementById( 'imprese_' + index ).style.display		= "block";
			document.getElementById( 'esercenti_' + index ).style.display	= "none";
			document.getElementById('esercenteFuoriRegione' + index).style.display = 'none';
			document.getElementById('esercenteFuoriRegione' + index).value = '';
		}
		else if( fuoriReg.checked )
		{
			document.getElementById( 'imprese_' + index ).style.display		= "none";
			document.getElementById( 'esercenti_' + index ).style.display	= "block";
			document.getElementById('esercenteNoGisa' + index).style.display = 'none';
			document.getElementById('esercenteNoGisa' + index).value = '';
		}
	}
	catch(err)
	{
		alert(err.description);
	}
}


function selectDestinazione( index )
{
	try {
		document.getElementById( 'destinatario_label_' + index ).innerHTML	= "-- Seleziona Destinatario --";
		
		document.getElementById( 'destinatario_' + index + "_id" ).value	= "-1";
		document.getElementById( 'destinatario_' + index + "_nome" ).value	= "";
		document.getElementById('esercenteFuoriRegione' + index).style.display = 'none';
		document.getElementById('esercenteFuoriRegione' + index).value = '';
		document.getElementById('esercenteNoGisa' + index).style.display = 'none';
		document.getElementById('esercenteNoGisa' + index).value = '';
		
		
		
		inReg = document.getElementById( "inRegione_" + index );
		fuoriReg = document.getElementById( "outRegione_" + index );
	
		if( inReg.checked )
		{
			document.getElementById( 'imprese_' + index ).style.display		= "block";
			document.getElementById( 'esercenti_' + index ).style.display	= "none";
			
		}
		else if( fuoriReg.checked )
		{
			document.getElementById( 'imprese_' + index ).style.display		= "none";
			document.getElementById( 'esercenti_' + index ).style.display	= "block";
			
		}
	}
	catch(err)
	{
		alert(err.description);
	}
}

function mostraDestinazione()
{
	try {
		
		inReg1 = document.getElementById( "inRegione_1");
		fuoriReg1 = document.getElementById( "outRegione_1");
		inReg2 = document.getElementById( "inRegione_2");
		fuoriReg2 = document.getElementById( "outRegione_2");
	
		if( inReg1.checked )
		{
			document.getElementById( 'imprese_1').style.display		= "block";
			document.getElementById( 'esercenti_1').style.display	= "none";
			
		}
		else if( fuoriReg1.checked )
		{
			document.getElementById( 'imprese_1').style.display		= "none";
			document.getElementById( 'esercenti_1').style.display	= "block";
			
		}

		if( inReg2.checked )
		{
			document.getElementById( 'imprese_2').style.display		= "block";
			document.getElementById( 'esercenti_2').style.display	= "none";
			
		}
		else if( fuoriReg2.checked )
		{
			document.getElementById( 'imprese_2').style.display		= "none";
			document.getElementById( 'esercenti_2').style.display	= "block";
			
		}
		
	}
	catch(err)
	{
		alert(err.description);
	}
}

//-------------------------------------------------------------------
//Confronta due date in formato stringa d/MM/y
//Ritorna :
//1 se dateA è maggiore di dateB
//0 se dateB è maggiore o uguale di dateA
//-1 se le date sono in formato errato
//-------------------------------------------------------------------
function confrontoAnni( dataA, dataB )
{
	var dataD_A = getDateFromFormat( dataA, 'd/MM/y' );
	var dataD_B = getDateFromFormat( dataB, 'd/MM/y' );

	if (dataD_A==0 || dataD_B==0) {
		return -1;
		}
	else if (dataD_A > dataD_B) {
		return 1;
		}
	return 0;
}


function sonoPassatiQuattroAnni( dal )
{
	if(dal != null && dal != ''){
		var oggi = new Date();
		var dataDal = getDateFromFormat( dal, 'd/MM/y' );
		var qAnni = ( 1000*60*60*24 * (365 * 4 + 1) );
		var diff = ( oggi.getTime() - dataDal );
		return ( diff >= qAnni );
	}
	else{
		return false;
	}

};

function sonoPassatiNAnni( dal, al, anni )
{
	if(dal != null && dal != '' && al != null && al != ''){
		var dataDal = getDateFromFormat( dal, 'd/MM/y' );
		var dataAl = getDateFromFormat( al, 'd/MM/y' );
		var qAnni = ( 1000*60*60*24 * (365 * anni + 1) );
		var diff = ( dataAl - dataDal );
		return ( diff >= qAnni );
	}
	else{
		return false;
	}

};

function sonoPassatiDueAnni( dal )
{
	var oggi = new Date();
	var dataDal = getDateFromFormat( dal, 'd/MM/y' );
	var qAnni = ( 1000*60*60*24 * (365 * 2 + 1) );
	var diff = ( oggi.getTime() - dataDal );
	return ( diff >= qAnni );
};



$(function() {
	$("#tabs").tabs().addClass('ui-tabs-vertical ui-helper-clearfix');
	$("#tabs li").removeClass('ui-corner-top').addClass('ui-corner-left');
});

var ret = true;

function controllaForm( toLiberoConsumo )
{
	
	try{
		var form = document.main;

		//Geocodifica.isCapoEsistente( form.cd_matricola.value, gestisciCapoEsistente );
		message ="";
		ret = true;
		var BOVINI=1;
		var BUFALINI=5;
		id_specie_selezionata = document.getElementById('cd_specie').value;

		
		
		verificaStampaMod10(document.getElementById('orgId').value,document.getElementById('vpm_data').value);


		
		
		
		if (form.cd_codice_azienda_provenienza.value == '')
		{
			message += label("","- [Controllo documentale] : \"Codice Azienda di provenienza\" obbligatorio\r\n" );
			ret = false;
		}
		
		if( form.cd_matricola.value == '' )
		{
			message += label("","- [Controllo documentale] : \"Matricola\" obbligatoria\r\n" );
			ret = false;
		}

	
		if( form.cd_data_arrivo_macello.value == '' )
		{
			message += label("","- [Controllo documentale] : \"Data arrivo in macello\" obbligatoria\r\n" );
			ret = false;
		}

		if( trim(form.cd_veterinario_1.value) == '' && trim(form.cd_veterinario_2.value) == '' && trim(form.cd_veterinario_3.value) == '' ){
			message += label("","- [Controllo documentale] : Selezionare almeno un veterinario\r\n" );
			ret = false;
		}
		
		if( form.capo_esistente.value == 'si' )
		{
			message += label("","- Matricola già esistente\r\n" );
			ret = false;
		}
	
		if ( form.cd_data_nascita.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, dataAttuale() ) == 1 ) { 
				message += label("","- [Controllo documentale] : Inserire \"Data di nascita\" del capo inferiore o uguale alla data corrente" + " (" +  dataAttuale() + ")\r\n" );
				ret = false;
			}
		}

		if ( form.cd_data_mod4.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cd_data_mod4.value ) == 1 ) { 
				message += label("","- [Controllo documentale] : Inserire \"Data Mod. 4\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}
	
		if ( form.cd_data_arrivo_macello.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cd_data_arrivo_macello.value ) == 1 ) { 
				message += label("","- [Controllo documentale] : Inserire \"Data arrivo al macello\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.bse_data_prelievo.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.bse_data_prelievo.value ) == 1 ) { 
				message += label("","- [Controllo documentale] : Inserire \"Data Prelievo Test BSE\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.bse_data_ricezione_esito.value != '' )
		{
			if ( confrontoAnni( form.bse_data_prelievo.value, form.bse_data_ricezione_esito.value ) == 1 ) { 
				message += label("","- [Controllo documentale] : Inserire \"Data Ricezione Esito Test BSE\" maggiore o uguale alla \"Data Prelievo Test BSE\" del capo" + " (" +  form.bse_data_prelievo.value + ")\r\n" );
				ret = false;
			}
		}

		if(!toLiberoConsumo){

			//Controllo che siano valorizzati o tutti o nessuno dei campi obbligatori di Comunicazioni Esterne
			var comunicazioniEsterneA = document.getElementById('casl_to_asl_origine').checked || 
										document.getElementById('casl_to_proprietario_animale').checked ||
										document.getElementById('casl_to_azienda_origine').checked ||
										document.getElementById('casl_to_proprietario_macello').checked ||
										document.getElementById('casl_to_pif').checked ||
										document.getElementById('casl_to_uvac').checked ||
										document.getElementById('casl_to_regione').checked ||
										document.getElementById('casl_to_altro').checked ;
						 
			var dataComunicazioniEsterne = document.getElementById('casl_data').value != '';
		
			var tipoNCComunicazioniEsterne = document.getElementById('casl_NC_rilevate').selectedIndex > 0;
		
			var provvedimentiComunicazioniEsterne = document.getElementById('casl_provvedimenti_selezionati').selectedIndex > 0;
	
			//se almeno uno di essi è valorizzato...
			if(comunicazioniEsterneA || dataComunicazioniEsterne || tipoNCComunicazioniEsterne || provvedimentiComunicazioniEsterne){
	
				//...allora vedi se ce n'è almeno uno NON valorizzato
				if(!comunicazioniEsterneA || !dataComunicazioniEsterne || !tipoNCComunicazioniEsterne || !provvedimentiComunicazioniEsterne){
					message += label("","- [Comunicazioni Esterne] :\r\n \tUno dei seguenti campi è valorizzato:\r\n \t*Comunicazioni a\r\n \t*Data\r\n \t*Tipo di non conformità\r\n \t*Provvedimenti Adottati\r\n \tValorizzare anche gli altri.\r\n"  );
					ret = false;
				}
				
			}
			
	
			if ( form.casl_data.value != '' )
			{
				if ( confrontoAnni( form.cd_data_nascita.value, form.casl_data.value ) == 1 ) { 
					message += label("","- [Comunicazioni Esterne] : Inserire \"Data Comunicazioni Esterne\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
					ret = false;
				}
			}	
		
			if ( form.rca_data.value != '' )
			{
				if ( confrontoAnni( form.casl_data.value, form.rca_data.value ) == 1 ) { 
					message += label("","- [Comunicazioni Esterne] : Inserire \"Data Ricezione Comunicazioni ASL\" maggiore o uguale alla \"Data Comunicazioni Esterne\" del capo" + " (" +  form.casl_data.value + ")\r\n" );
					ret = false;
				}
			}
		
			if ( form.seqa_data.value != '' )
			{
				if ( confrontoAnni( form.cd_data_nascita.value, form.seqa_data.value ) == 1 ) { 
					message += label("","- [Comunicazioni Esterne] : Inserire \"Data blocco animale\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
					ret = false;
				}
			}	
		
			if ( form.seqa_data_sblocco.value != '' )
			{
				if ( confrontoAnni( form.seqa_data.value, form.seqa_data_sblocco.value ) == 1 ) { 
					message += label("","- [Comunicazioni Esterne] : Inserire \"Data sblocco animale\" maggiore o uguale alla \"Data blocco animale\" del capo" + " (" +  form.seqa_data.value + ")\r\n" );
					ret = false;
				}
			}	

		}

		if(!toLiberoConsumo){

			//Controllo che siano valorizzati o tutti o nessuno dei campi obbligatori di Morte ant. macellazione
			var dataMorteAnteMacellazione = document.getElementById('mavam_data').value != '';
			var luogoMorteAnteMacellazione = document.getElementById('mavam_luogo').selectedIndex > 0;
			var causaMorteAnteMacellazione = trim( document.getElementById('mavam_motivo').value ) != '';
		
			//se almeno uno di essi è valorizzato...
			if(dataMorteAnteMacellazione || luogoMorteAnteMacellazione || causaMorteAnteMacellazione){
	
				//...allora vedi se ce n'è almeno uno NON valorizzato
				if(!dataMorteAnteMacellazione || !luogoMorteAnteMacellazione || !causaMorteAnteMacellazione){
					message += label("","- [Morte ant. macellazione] :\r\n \tUno dei seguenti campi è valorizzato:\r\n \t*Data \r\n \t*Luogo di verifica\r\n \t*Causa\r\n \tValorizzare anche gli altri.\r\n"  );
					ret = false;
				}
				
			}
		
		}
	
		if ( form.mavam_data.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.mavam_data.value ) == 1 ) { 
				message += label("","- [Morte ant. macellazione] : Inserire \"Data Morte antecedente macellazione\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	


		if(!toLiberoConsumo){
		
			//Controllo che siano valorizzati o tutti o nessuno dei campi obbligatori di Visita Ante Mortem
			var dataVisitaAnteMortem = document.getElementById('vam_data').value != '';
			var provvedimentoVisitaAnteMortem = document.getElementById('vam_provvedimenti').selectedIndex > 0;
		
			//se almeno uno di essi è valorizzato...
			if(dataVisitaAnteMortem || provvedimentoVisitaAnteMortem){
	
				//...allora vedi se ce n'è almeno uno NON valorizzato
				if(!dataVisitaAnteMortem || !provvedimentoVisitaAnteMortem){
					message += label("","- [Evidenza Visita AM] :\r\n \tUno dei seguenti campi è valorizzato:\r\n \t*Data \r\n \t*Provvedimento adottato\r\n \tValorizzare anche gli altri.\r\n"  );
					ret = false;
				}
				
			}
		
		}
	
		if ( form.vam_data.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.vam_data.value ) == 1 ) { 
				message += label("","- [Evidenza Visita AM] : Inserire \"Data Visita Ante Mortem\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.abb_data.value != '' )
		{
			if ( confrontoAnni( form.vam_data.value, form.abb_data.value ) == 1 ) { 
				message += label("","- [Evidenza Visita AM] : Inserire \"Data Abbattimento\" maggiore o uguale alla \"Data Visita Ante Mortem\" del capo" + " (" +  form.vam_data.value + ")\r\n" );
				ret = false;
			}
		}

		/*
		if ( form.vpm_data.value == ''  )
		{
			message += label("","- [Evidenza Visita PM] : Inserire la data di Macellazione.\r\n" );
			ret = false;
		}	
		*/



		if(!toLiberoConsumo){

			//Controllo che siano valorizzati o tutti o nessuno dei campi obbligatori di Visita Post Mortem
			var dataVisitaPostMortem = document.getElementById('vpm_data').value != '';
			var destinatarioVisitaPostMortem = trim(document.getElementById('destinatario_1_nome').value).length > 0 || trim(document.getElementById('destinatario_2_nome').value).length > 0 || trim(document.getElementById('destinatario_3_nome').value).length > 0 || trim(document.getElementById('destinatario_4_nome').value).length >0;
		
			//se almeno uno di essi è valorizzato...
			if(dataVisitaPostMortem || destinatarioVisitaPostMortem){
	
				//...allora vedi se ce n'è almeno uno NON valorizzato
				if(!dataVisitaPostMortem || !destinatarioVisitaPostMortem){
					message += label("","- [Evidenza Visita PM] :\r\n \tUno dei seguenti campi è valorizzato:\r\n \t*Data Macellazione\r\n \t*Destinatario Carni\r\n \tValorizzare anche gli altri.\r\n"  );
					ret = false;
				}
				
			}

			if(destinatarioVisitaPostMortem && (dataMorteAnteMacellazione || luogoMorteAnteMacellazione ||  causaMorteAnteMacellazione))
			{
				message += label("","- [Evidenza Visita PM] :\r\n \t Animale morto prima della macellazione.Non è possibile indicare Destinatari delle carni\r\n"  );
				ret = false;
			}

		}
		
	
		if ( form.vpm_data.value != '' )
		{
			if ( confrontoAnni( form.abb_data.value, form.vpm_data.value ) == 1 ) { 
				message += label ("","- [Evidenza Visita PM] : Inserire \"Data Visita Post Mortem\" maggiore o uguale alla \"Data Abbattimento\" del capo" + " (" +  form.vam_data.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.cmp_data_ricezione_esito_1.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cmp_data_ricezione_esito_1.value ) == 1 ) { 
				message += label("","- [Evidenza Visita PM] : Inserire \"Data Ricezione Esito Campione n°.1\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.cmp_data_ricezione_esito_2.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cmp_data_ricezione_esito_2.value ) == 1 ) { 
				message += label("","- [Evidenza Visita PM] : Inserire \"Data Ricezione Esito Campione n°.2\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.cmp_data_ricezione_esito_3.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cmp_data_ricezione_esito_3.value ) == 1 ) { 
				message += label("","- [Evidenza Visita PM] : Evidenza Visita PM : \n\nInserire \"Data Ricezione Esito Campione n°.3\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.cmp_data_ricezione_esito_4.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cmp_data_ricezione_esito_4.value ) == 1 ) { 
				message += label("","- [Evidenza Visita PM] : Inserire \"Data Ricezione Esito Campione n°.4\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.cmp_data_ricezione_esito_5.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cmp_data_ricezione_esito_5.value ) == 1 ) { 
				message += label("","- [Evidenza Visita PM] : Inserire \"Data Ricezione Esito Campione n°.5\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	

		/*
		if ( form.vam_provvedimenti.value != '-1' && form.vam_data.value == ''  )
		{
			message += label("","- [Evidenza Visita AM] : Inserire la data della Visita Ante Mortem.\r\n" );
			ret = false;
		}
		*/
	
		if ( form.vam_provvedimenti.value == '4' && form.abb_data.value == ''  )
		{
			message += label("","- [Evidenza Visita AM] : Inserire la data di abbattimento.\r\n" );
			ret = false;
		}
	

		//Controllo che almeno uno dei campi "Destinatario delle Carni" sia stato valorizzato
		/*
		if(!toLiberoConsumo){
			if( trim(form.destinatario_1_nome.value).length == 0 && trim(form.destinatario_2_nome.value).length == 0 ){
				message += label("","- [Evidenza Visita PM] : Inserire almeno un Destinatario delle Carni.\r\n" );
				ret = false;
			}
		}
		*/
		

		if( trim(form.progressivo_macellazione.value).length == 0 ){
			form.progressivo_macellazione.value = 0;
		}

		
		
		
		if ( (form.cmp_matrice_1.value == '-1' && form.cmp_tipo_1.value != '-1') ||
			 (form.cmp_matrice_2.value == '-1' && form.cmp_tipo_2.value != '-1') ||
			 (form.cmp_matrice_3.value == '-1' && form.cmp_tipo_3.value != '-1') ||
			 (form.cmp_matrice_4.value == '-1' && form.cmp_tipo_4.value != '-1') ||
			 (form.cmp_matrice_5.value == '-1' && form.cmp_tipo_5.value != '-1'))
		{
			message += label("","- [Evidenza Visita PM] : Non è possibile inserire un campione senza scegliere la relativa matrice.\r\n" );
			ret = false;
		}

		//se è selezionato il flag test bse deve essere selezionato un valore dalla tendina corrispondente
		if( form.cd_bse_check.checked && form.cd_bse.value == '-1' )
		{
			message += label("","- [Controllo documentale] : Seleziona un valore alla voce Test BSE\r\n" );
			ret = false;
		}

		
		//if( form.cd_bse_check.checked && form.cd_data_nascita.value!='' && !sonoPassatiQuattroAnni( form.cd_data_nascita.value)){
			//message += label("","- Il capo non ha più di 4 anni.\nPer il test BSE e' obbligatorio specificare una data di nascita opportuna\r\n");
			//ret = false;
		//}

		if( sonoPassatiNAnni( form.cd_data_nascita.value, form.vpm_data.value, <%= ApplicationProperties.getProperty("numeroAnniTestBse") %> ) && ( (id_specie_selezionata == BOVINI || id_specie_selezionata == BUFALINI)  ) )
		{
			if(document.getElementById('cd_bse_check').checked==false){
				/*
				message += label("", "- [Controllo Documentale] : Il capo ha più di 4 anni. E\' obbligatorio l\'inserimento dei dati sul test BSE" );
				ret = false;
				document.getElementById('cd_bse_list').style.display = "";
				document.getElementById('sezioneTESTBSE').style.display = "";
				document.getElementById('cd_bse_check').checked = true;
				document.getElementById('cd_bse').style.display = "";
				
				document.getElementById('manca_BSE_Nmesi').value = 'si';*/
			}
			else{
				contralla_cd_bse();
			}
			
			//document.getElementById('cd_bse_list').style.display = "";
			//document.getElementById('sezioneTESTBSE').style.display = "";
			
			//ret=false;
		}


		//Test BSE obbligatorio se l'animale muore prima della macellazione
		/*
		if(document.getElementById('cd_bse_check').checked==false && document.getElementById('mavam_luogo').value != '-1' && ( (id_specie_selezionata == BOVINI || id_specie_selezionata == BUFALINI)  ) ){

			document.getElementById('test_bse_etichetta').style.display = "";
			document.getElementById('test_bse_valore').style.display = "";
			document.getElementById('cd_bse_list').style.display = "";
			document.getElementById('sezioneTESTBSE').style.display = "";
			document.getElementById('cd_bse_check').checked = true;
			document.getElementById('cd_bse').style.display = "";
			message += label("", "- [Controllo Documentale] : Animale morto prima della macellazione. E\' obbligatorio l\'inserimento dei dati sul test BSE" );
			ret = false;
			
		}
		*/
			
		
		
		
		//alert("sonoPassatiQuattroAnni( form.cd_data_nascita.value ) : " + sonoPassatiQuattroAnni( form.cd_data_nascita.value ) );
		//alert("form.bse_data_prelievo.value == '' : " + (form.bse_data_prelievo.value == '') );
		//alert("form.bse_data_prelievo.value == '' : " + (form.bse_data_prelievo.value == '') );
		//alert("id_specie_selezionata : " + id_specie_selezionata)
		//alert("document.getElementById('cd_specie').value : " + document.getElementById('cd_specie').value);
		
		
		/*se il capo ha più di 48 mesi deve essere eseguito il test bse (l\'esito può essere inserito dopo) 
		if( ret && sonoPassatiQuattroAnni( form.cd_data_nascita.value ) && form.bse_data_prelievo.value == '' && ( (id_specie_selezionata == BOVINI || id_specie_selezionata == BUFALINI)  ) )
		{
			alert( "Controllo Documentale : \n\nIl capo ha più di 4 anni.\nE\' obbligatorio l\'inserimento dei dati sul test BSE" );
			document.getElementById('cd_bse_check').checked = true;
			contralla_cd_bse();
			ret = false;
		}*/
	
		/*se è selezionato il test bse e il relativo valore allora devono essere inserite le info sul test bse (data prelievo)
		if( ret && form.cd_bse_check.checked && form.cd_bse.value != '-1' && form.bse_data_prelievo.value == '' ) 
		{
			alert( "Controllo Documentale : \n\nHai selezionato Test BSE. Devi inserire la data di prelievo." );
			ret = false;
		}*/
	
		//la selezione di uno dei campi esito e data ricezione esito nella sezione test bse 
		//implica la selezione di tutti tre i campi: esito, data ricezione esito e data prelievo
	//	if( ret && (form.bse_esito.value != '-1' || form.bse_data_ricezione_esito.value != '') //almeno uno dei due valori è selezionato
	//			&& (form.bse_esito.value == '-1' || form.bse_data_ricezione_esito.value == '' || form.bse_data_prelievo.value == '' ) ) //almeno uno dei tre valori non è selezionato
	//	{
	//		alert( "Controllo Documentale : \n\nDati sul Test BSE incompleti. Inserire Data Prelievo." );
	//		ret = false;
	//	} 


		//CONTROLLO SULL'ESITO DISATTIVATO
		/*se si inseriscono le info sul test bse e non le info sull\'esito non si può mandare l\'animale al libero consumo
		if( ret && form.bse_data_prelievo.value != '' && form.bse_esito.value == '-1' && ( form.vpm_esito.value == '1' || toLiberoConsumo )  )
		{
			alert( "Controllo Documentale : \n\nNon hai inserito i dati sull\'esito del Test BSE. Non puoi mandare l\'animale in libero consumo." );
			ret = false;
		}*/

		
	
		//se non sono disponibili info sulla catena alimentare il capo non puo' essere macellato e va bloccato
		//if( !form.cd_info_catena_alimentare.checked && !form.blocco_animale.checked )
		if( !form.cd_info_catena_alimentare.checked )		
		{
			alert( "Non sono disponibili informazioni sulla catena alimentare. L\'animale va bloccato" );
			ret = false;
		}

		

		//Aggiunta
		 if (ret == false) {
			 document.getElementById('submitOK').value = 'no';
      		alert(label("", "La form non puo' essere salvata, verifica i seguenti errori:\r\n\r\n") + message);
    	}
		 else{
			 document.getElementById('submitOK').value = 'si';
		}

		
	  }
	catch(err)
	  {
		alert(err);
	  }

	return ret;
};




function checkControlloDocumentale( )
{
	try{
		var form = document.main;
		var ret = true;
		var BOVINI=1;
		var BUFALINI=5;
		id_specie_selezionata = document.getElementById('cd_specie').value;

		message = "";


		/*if( form.cd_dati_speditore.value == '' )
		{
			message += label("","- [Controllo documentale] : \"Speditore\" obbligatorio\r\n" );
			ret = false;

		}*/

		if (form.cd_codice_azienda_provenienza == '')
		{
			message += label("","- [Controllo documentale] : \"Codice Azienda di provenienza\" obbligatorio\r\n" );
			ret = false;
		}
		
		if( form.cd_matricola.value == '' )
		{
			message += label("","- [Controllo documentale] : \"Matricola\" obbligatoria\r\n" );
			ret = false;
		}

		if( form.cd_data_arrivo_macello.value == '' )
		{
			message += label("","- [Controllo documentale] : \"Data arrivo in macello\" obbligatoria\r\n" );
			ret = false;
		}

		if( trim(form.cd_veterinario_1.value) == '' && trim(form.cd_veterinario_2.value) == '' && trim(form.cd_veterinario_3.value) == '' ){
			message += label("","- [Controllo documentale] : Selezionare almeno un veterinario\r\n" );
			ret = false;
		}
		
		if( form.capo_esistente.value == 'si' )
		{
			message += label("","- Matricola già esistente\r\n" );
			ret = false;
		}
	
		if ( form.cd_data_nascita.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, dataAttuale() ) == 1 ) { 
				message += label("","- [Controllo documentale] : Inserire \"Data di nascita\" del capo inferiore o uguale alla data corrente" + " (" +  dataAttuale() + ")\r\n" );
				ret = false;
			}
		}

		if ( form.cd_data_mod4.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cd_data_mod4.value ) == 1 ) { 
				message += label("","- [Controllo documentale] : Inserire \"Data Mod. 4\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}
	
		if ( form.cd_data_arrivo_macello.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cd_data_arrivo_macello.value ) == 1 ) { 
				message += label("","- [Controllo documentale] : Inserire \"Data arrivo al macello\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.bse_data_prelievo.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.bse_data_prelievo.value ) == 1 ) { 
				message += label("","- [Controllo documentale] : Inserire \"Data Prelievo Test BSE\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.bse_data_ricezione_esito.value != '' )
		{
			if ( confrontoAnni( form.bse_data_prelievo.value, form.bse_data_ricezione_esito.value ) == 1 ) { 
				message += label("","- [Controllo documentale] : Inserire \"Data Ricezione Esito Test BSE\" maggiore o uguale alla \"Data Prelievo Test BSE\" del capo" + " (" +  form.bse_data_prelievo.value + ")\r\n" );
				ret = false;
			}
		}


		//se è selezionato il flag test bse deve essere selezionato un valore dalla tendina corrispondente
		if( form.cd_bse_check.checked && form.cd_bse.value == '-1' )
		{
			message += label("","- [Controllo documentale] : Seleziona un valore alla voce Test BSE\r\n" );
			ret = false;
		}

		if( sonoPassatiNAnni( form.cd_data_nascita.value, form.vpm_data.value, <%= ApplicationProperties.getProperty("numeroAnniTestBse") %> ) && ( (id_specie_selezionata == BOVINI || id_specie_selezionata == BUFALINI)  ) )
		{
			if(document.getElementById('cd_bse_check').checked==false){
				/*
				message += label("", "- [Controllo Documentale] : Il capo ha più di 4 anni. E\' obbligatorio l\'inserimento dei dati sul test BSE" );
				ret = false;
				document.getElementById('cd_bse_list').style.display = "";
				document.getElementById('sezioneTESTBSE').style.display = "";
				document.getElementById('cd_bse_check').checked = true;
				document.getElementById('cd_bse').style.display = "";
				
				document.getElementById('manca_BSE_Nmesi').value = 'si';*/
			}
			else{
				contralla_cd_bse();
			}
			
			//document.getElementById('cd_bse_list').style.display = "";
			//document.getElementById('sezioneTESTBSE').style.display = "";
			
			//ret=false;
		}


		//Test BSE obbligatorio se l'animale muore prima della macellazione
		if(document.getElementById('cd_bse_check').checked==false && document.getElementById('mavam_luogo').value != '-1' && ( (id_specie_selezionata == BOVINI || id_specie_selezionata == BUFALINI)  ) ){

			document.getElementById('test_bse_etichetta').style.display = "";
			document.getElementById('test_bse_valore').style.display = "";
			document.getElementById('cd_bse_list').style.display = "";
			document.getElementById('sezioneTESTBSE').style.display = "";
			document.getElementById('cd_bse_check').checked = true;
			document.getElementById('cd_bse').style.display = "";
			message += label("", "- [Controllo Documentale] : Animale morto prima della macellazione. E\' obbligatorio l\'inserimento dei dati sul test BSE" );
			ret = false;
			
		}

		//Aggiunta
		 if (ret == false) {
      		alert(label("", "Prima di poter procedere con altre operazioni, verificare quanto segue:\r\n\r\n") + message);
    	 }
    	 else{
        	 displayTabs();
       	 }

		
	  }
	catch(err)
	  {
		alert(err);
	  }

	return ret;
};



function displayAbbattimento()
{
	try {
			if( document.main.vam_provvedimenti.value==4 )
			{
				document.getElementById("table_abbattimento").style.display="";
			}
			else
			{
				document.getElementById("table_abbattimento").style.display="none";
			}
	  }
	catch(err)
	  {
		alert(err);
	  }
}

function displayLuogoVerifica()
{
	if( document.main.mavam_luogo.value==3 )
	{
		document.getElementById("luogo").style.display="";
	}
	else
	{
		document.getElementById("luogo").style.display="none";
	}
	
}


function displayStadio( index )
{
	if( document.getElementsByName( 'lcso_patologia_' + index )[0].value == 6 )
	{
		document.getElementById( "stadio_" + index ).style.display = "table-row";
	}
	else
	{
		document.getElementById( "stadio_" + index ).style.display = "none";
	}
}

function controlla_esito_testBSE() {

	//alert(document.getElementById('bse_esito').value);

	if (document.getElementById('bse_esito').value == "-1") {
		document.getElementById('liberoConsumoButton').value="Libero Consumo in attesa di esito Test BSE >>";
	} else {
		document.getElementById('liberoConsumoButton').value="Libero Consumo >>";
	}
	
}

function nascondi_liberoConsumoButton(nascondi){

	//alert (nascondi);
	
	if (nascondi) {
		document.getElementById('liberoConsumoButton').style.display = "none";
	} else {
		document.getElementById('liberoConsumoButton').style.display = "";
	}
}

function contralla_cd_bse(){
	if ( document.getElementById('cd_bse_check').checked ) {

		//alert(document.getElementById('bse_esito').value);
		
		if (document.getElementById('bse_esito').value == "-1") {
			document.getElementById('liberoConsumoButton').value="Libero Consumo in attesa di esito Test BSE >>";
		}

		//document.getElementById('tab_test_bse').style.display = "none";
		document.getElementById('cd_bse').style.display = "";
		document.getElementById('cd_bse_list').style.display = "";

		document.getElementById('sezioneTESTBSE').style.display = "";
		
		
	} else {

		document.getElementById('liberoConsumoButton').value="Libero Consumo >>";
		
		//document.getElementById('tab_test_bse').style.display = "none";
		document.getElementById('cd_bse').style.display = "none";
		document.getElementById('cd_bse_list').style.display = "none";

		document.getElementById('sezioneTESTBSE').style.display = "none";
	}
}

function annullaID_cd_bse()
{
	document.main.cd_bse.value = -1;
	document.main.bse_data_prelievo.value = '';
	document.main.bse_data_ricezione_esito.value = '';
	document.main.bse_esito.value = -1;
	document.main.bse_note.value = '';
};

function annullaID_cd_bse_onload()
{
	document.getElementById('sezioneTESTBSE').style.display = "none";
	document.main.cd_bse_check.checked = false;
	
	document.main.cd_bse.value = -1;
	document.main.bse_data_prelievo.value = '';
	document.main.bse_data_ricezione_esito.value = '';
	document.main.bse_esito.value = -1;
	document.main.bse_note.value = '';
};

function controllaStatoItaliano()
{
	if ( document.getElementById('cd_provenienza_stato').value != 113 ) {
		document.getElementById('cd_asl').value 	= -1;
		document.getElementById('cd_asl').disabled  = true;

		document.getElementById('cd_provenienza_regione').value 	= -1;
		document.getElementById('cd_provenienza_regione').disabled  = true;
	} else {
		document.getElementById('cd_asl').disabled  = false;
		document.getElementById('cd_provenienza_regione').disabled  = false;
	}
		
};



function toggleDiv( nomeCampo )
{
	var value = document.getElementById( nomeCampo );

	if( value.style.display == "none" )
	{
		value.style.display = "block";
	}
	else
	{
		value.style.display = "none";
	}
};


function toggleVetDiv( nomeCampo )
{
	var value = document.getElementById( nomeCampo ).value;

	if( value.length > 0 )
	{
		if(document.getElementById( nomeCampo + "_toggle" )){
			document.getElementById( nomeCampo + "_toggle" ).style.display = "block";
		}
	}
};

function displayVPMpatologie()
{
	if( (document.main.vpm_esito.value == 4) || (document.main.vpm_esito.value == 2) )
	{
		document.getElementById( "vpm_riga_patologie" ).style.display = "table-row";
	}
	else
	{
		document.getElementById( "vpm_patologie_rilevate").value = -1;
		document.getElementById( "vpm_riga_patologie" ).style.display = "none";
	}
}


function resetSelect(){

	for (i=1; i<=3; i++){
		if(document.getElementById("lcso_organo_"+i)){
			document.getElementById("lcso_organo_"+i).value = -1;
		}
		if(document.getElementById("lesione_milza_"+i)){
			document.getElementById("lesione_milza_"+i).value = -1;
		}
		if(document.getElementById("lesione_cuore_"+i)){
			document.getElementById("lesione_cuore_"+i).value = -1;
		}
		if(document.getElementById("lesione_polmoni_"+i)){
			document.getElementById("lesione_polmoni_"+i).value = -1;	
		}
		if(document.getElementById("lesione_fegato_"+i)){
			document.getElementById("lesione_fegato_"+i).value = -1;
		}
		if(document.getElementById("lesione_rene_"+i)){
			document.getElementById("lesione_rene_"+i).value = -1;
		}
		if(document.getElementById("lesione_mammella_"+i)){
			document.getElementById("lesione_mammella_"+i).value = -1;
		}
		if(document.getElementById("lesione_apparato_genitale_"+i)){
			document.getElementById("lesione_apparato_genitale_"+i).value = -1;
		}
		if(document.getElementById("lesione_stomaco_"+i)){
			document.getElementById("lesione_stomaco_"+i).value = -1;
		}
		if(document.getElementById("lesione_intestino_"+i)){
			document.getElementById("lesione_intestino_"+i).value = -1;
		}
		if(document.getElementById("lesione_osteomuscolari_"+i)){
			document.getElementById("lesione_osteomuscolari_"+i).value = -1;
		}
		if(document.getElementById("lesione_generici_"+i)){
			document.getElementById("lesione_generici_"+i).value = -1;
		}
		if(document.getElementById("lesione_altro_"+i)){
			document.getElementById("lesione_altro_"+i).value = -1;
		}
	}
}


function displayLCSO()
{
	if( document.main.vpm_esito.value == 3 )
	{
		
		document.getElementById("lcso").style.display="";
		document.getElementById("lcsobutton").style.display="";
		document.getElementById("lcpr").style.display="none";
		
		
	}
	else if( document.main.vpm_esito.value == 4 )
	{
		/* vecchia versione, nella nuova se 4 si visualizzano anche i campi di 3
		document.getElementById("lcpr").style.display="";
		document.getElementById("lcso").style.display="none";
		document.getElementById("lcsobutton").style.display="none";
		*/
		document.getElementById("lcpr").style.display="";
		document.getElementById("lcso").style.display="";
		document.getElementById("lcsobutton").style.display="";
	}
	else
	{
		document.getElementById("lcso").style.display="none";
		document.getElementById("lcsobutton").style.display="none";
		document.getElementById("lcpr").style.display="none";
/*
		if( document.main.vpm_esito.value==2 )
		{
			document.getElementById("tr_vpm_abb_dist_carcassa").style.display="";
			document.getElementById("vpm_abb_dist_carcassa").checked=true;
		}
		else
		{
			document.getElementById("tr_vpm_abb_dist_carcassa").style.display="none";
			document.getElementById("vpm_abb_dist_carcassa").checked=false;
		}
*/
	}
};
	
/*
function distrCarcassaVMP()
{
	if( document.main.vpm_esito.value==2 )
	{
	document.getElementById("tr_vpm_abb_dist_carcassa").style.display="";
	document.getElementById("vpm_abb_dist_carcassa").checked=true;
	}
	else{
	document.getElementById("tr_vpm_abb_dist_carcassa").style.display="none";
	document.getElementById("vpm_abb_dist_carcassa").checked=false;
	}
};
*/

function set_vet( select, campo )
{
	var value_to_set = "";
	if( select.value != "-1" )
	{
		value_to_set = select.options[ select.selectedIndex ].text;
	}
	document.getElementById( campo ).value = value_to_set;
	toggleVetDiv( campo );
};

function gestisciUtenteVeterinario(){
	var ruolo = '<%= User.getRoleId() %>';
	var userId = '<%= User.getUserId() %>';
	var nome = "<%= User.getContact().getNameLast().toUpperCase().replaceAll("\"","'") + " " + User.getContact().getNameFirst().toUpperCase().replaceAll("\"","'") %>";

	if(ruolo == 43 || ruolo == 19 || ruolo == 46){
		
		var select = document.getElementById('veterinari_cd1');
		for (var i = 0 ; i < select.length - 1; i++){
			if(select.options[i].value == userId ){
				select.options[i].selected = true;
				document.getElementById('cd_veterinario_1').value = nome;
				document.getElementById('vpm_veterinario').value = nome;
				document.getElementById('cd_veterinario_1_toggle').style.display='block';
				document.getElementById('veterinari_vpm').options[select.selectedIndex].selected = true;
				return;
			}
		}
	}
	
}

function preselezionaVeterinario(select, num){

	var value_to_set = "";
	if( select.value != "-1" )
	{
		value_to_set = select.options[ select.selectedIndex ].text;
	}

	if(num == 1){
		document.getElementById('vpm_veterinario').value = value_to_set;
		document.getElementById('veterinari_vpm').options[select.selectedIndex].selected = true;
	}
	else if(num == 2){
		document.getElementById('vpm_veterinario_toggle').style.display='block';
		document.getElementById('vpm_veterinario_2').value = value_to_set;
		document.getElementById('veterinari_vpm2').options[select.selectedIndex].selected = true;
	}
	else if(num == 3){
		document.getElementById('vpm_veterinario_2_toggle').style.display='block';
		document.getElementById('vpm_veterinario_3').value = value_to_set;
		document.getElementById('veterinari_vpm3').options[select.selectedIndex].selected = true;
	}
	
}

function gestisciDataCorrente(){
	var clona = '<%= request.getParameter("clona")%>';
	var update = '<%= ( ( (Boolean)request.getAttribute( "Update" ) ) == null) ? "no" : "si"%>';
	if(clona != 'si' && update != 'si' && document.getElementById('cd_data_arrivo_macello').value == '' ){
		document.getElementById('cd_data_arrivo_macello').value = '<%= sdf.format(new Date()) %>';
	}
	//preselezionaDate();
}

function riportaDataArrivoMacello(input){
	if(input.value == '' && document.getElementById('cd_data_arrivo_macello').value != ''){
		input.value = document.getElementById('cd_data_arrivo_macello').value;
	}
}

function preselezionaDate(){

	if(confirm('Cliccare OK se si vuole propagare la data anche per:\n' +
			   '- Data Comunicazioni Esterne\n' + 
			   '- Data Ricezione Comunicazioni Esterne\n' + 
			   '- Data Morte antecedente macellazione\n' +
			   '- Data Visita Ante Mortem\n' +
			   '- Data Macellazione\n')){

		var value_to_set = document.getElementById('cd_data_arrivo_macello').value;
	
		document.getElementById('casl_data').value = value_to_set;
		document.getElementById('rca_data').value = value_to_set;
		document.getElementById('mavam_data').value = value_to_set;
		document.getElementById('vam_data').value = value_to_set;
		document.getElementById('vpm_data').value = value_to_set;

	}
	
}

function gestisciCapoEsistente(capo){

	var form = document.main;
	if( capo.esistente )
	{
		form.capo_esistente.value = 'si';
		alert( "Matricola " + capo.matricola + " già esistente" );
	}
	else
	{
		form.capo_esistente.value = 'no';
		Geocodifica.getCapo( capo.matricola, getDatiCapo );
	}
	
}

function getDatiCapo( data )
{
	var form = document.main;
	var matricola = form.cd_matricola.value	;

	if( matricola == '' )
	{
		return;
	}

	form.cd_matricola.value = data.matricola;
	
	if(data.errore == 0){
		form.capo_in_bdn.value = 'si';

		form.cd_codice_azienda.value = data.codice_azienda;
		form.codice_azienda_nascita_from_bdn.value = data.codice_azienda;

		form.cd_data_nascita.value = data.data_nascita;
		form.data_nascita_from_bdn.value = data.data_nascita;

		form.cd_specie.value = data.specie;
		form.specie_from_bdn.value = data.specie;

		form.cd_id_razza.value = data.razza;
		form.razza_from_bdn.value = data.razza;

		form.asl_speditore_from_bdn.value = data.asl;

		form.comune_speditore_from_bdn.value = data.comune;

		var BOVINI = 1;
		var BUFALINI = 5;
		
		if (data.specie == BOVINI){
			document.getElementById('categoria_bovina').style.display = "";
			document.getElementById('razza_bovina').style.display = "";
		}
		else {
			document.getElementById('categoria_bovina').style.display = "none";
			document.getElementById('razza_bovina').style.display = "none";
		} 

		if (data.specie == BUFALINI){
			document.getElementById('categoria_bufalina').style.display = "";
		}
		else {
			document.getElementById('categoria_bufalina').style.display = "none";
		}
		
		var sesso = 1;
		form.sesso_from_bdn.value = 'F';
		if( data.sesso )
		{
			sesso = 0;
			form.sesso_from_bdn.value = 'M';
		}
		form.cd_maschio[sesso].selected = "1";
		
	}
	else if( data.errore == 1 ){
		alert( "Capo " + matricola + " non in BDN" );
		document.getElementById('capo_in_bdn').value = 'no';
	}
	else{
		alert( "I web services della BDN non sono al momento disponibili." );
		document.getElementById('capo_in_bdn').value = 'no';
	}

};

function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
} 


function mostraTextareaEsercente(idTextarea){
	document.getElementById(idTextarea).style.display = '';
}

function nascondiTextareaEsercente(idTextarea){
	document.getElementById(idTextarea).value = '';
	document.getElementById(idTextarea).style.display = 'none';
}

function valorizzaDestinatario(campoTextarea,idDestinatario){
	document.getElementById(idDestinatario + '_nome').value = campoTextarea.value;
	document.getElementById(idDestinatario + '_id').value = -999;
	gestisciObbligatorietaVisitaPostMortem();
}

function visualizzaTextareaCAslToAltro(){
	if(document.getElementById('casl_to_altro') && document.getElementById('casl_to_altro').checked){
		document.getElementById('casl_to_altro_testo').style.display = '';
	}
	else{
		document.getElementById('casl_to_altro_testo').style.display = 'none';
	}
}

function visualizzaTextareaMavamToAltro(){
	if(document.getElementById('mavam_to_altro') && document.getElementById('mavam_to_altro').checked){
		document.getElementById('mavam_to_altro_testo').style.display = '';
	}
	else{
		document.getElementById('mavam_to_altro_testo').style.display = 'none';
	}
}

function visualizzaTextareaVamToAltro(){
	if(document.getElementById('vam_to_altro') && document.getElementById('vam_to_altro').checked){
		document.getElementById('vam_to_altro_testo').style.display = '';
	}
	else{
		document.getElementById('vam_to_altro_testo').style.display = 'none';
	}
}


function checkProgressivoMacellazione(){

	var progr = trim(document.getElementById('progressivo_macellazione').value);

	if (isNaN(progr)){
		alert('Il progressivo macellazione deve essere un valore numerico.');
		campo.value=0;
	}
	else if( parseInt(progr)<0 ){
		alert('Il progressivo macellazione non può avere un numero negativo.');
		campo.value=0;
	}
	else{

		Geocodifica.controlloProgressivoMacellazioneNew(document.getElementById('id_macello').value, document.getElementById('vpm_data').value, progr, document.getElementById('cd_matricola').value, controlloProgressivoMacellazioneNewRet );
	}

}

function controlloProgressivoMacellazioneNewRet(ok){

	if(!ok){
		alert('Progressivo macellazione già esistente!');
	}
	
}


function settaSoloCD(valore){
	document.getElementById('solo_cd').value = valore;
}

function displayTabs(){
	document.getElementById('li-7').style.display = '';
	document.getElementById('li-4').style.display = '';
	document.getElementById('li-2').style.display = '';
	document.getElementById('li-3').style.display = '';
	window.location.href="#";
}

function sleep(milliseconds) {
	  var start = new Date().getTime();
	  
	  for (var i = 0; i < 1e7; i++) {
		  
	    if ((new Date().getTime() - start) > milliseconds){
	      break;
	    }
	    
	  }
}

function gestisciUnload(){

	if(document.getElementById('submitOK').value == 'no'){
		if(confirm('Stai uscendo dalla scheda "<%= update ? "Modifica" : "Aggiungi"%> Capo". Cliccare su OK per salvare i dati, altrimenti le informazioni inserite verranno perse.')){
			if(controllaForm(false)){
				document.main.submit();
				alert('Si è scelto di salvare i dati sul capo.');
			}
			else{
				return false;
			}
		}
	}
	
}

function svuotaData(input){
	input.value = '';
	gestisciObbligatorietaComunicazioniEsterne();
	gestisciObbligatorietaMorteAnteMacellazione();
	gestisciObbligatorietaVisitaAnteMortem();
	gestisciObbligatorietaVisitaPostMortem();
}

function gestisciObbligatorietaComunicazioniEsterne(){

	var comunicazioniEsterneA;
	var dataComunicazioniEsterne;
	var tipoNCComunicazioniEsterne;
	var provvedimentiComunicazioniEsterne;
	
	comunicazioniEsterneA = document.getElementById('casl_to_asl_origine').checked || 
					 document.getElementById('casl_to_proprietario_animale').checked ||
					 document.getElementById('casl_to_azienda_origine').checked ||
					 document.getElementById('casl_to_proprietario_macello').checked ||
					 document.getElementById('casl_to_pif').checked ||
					 document.getElementById('casl_to_uvac').checked ||
					 document.getElementById('casl_to_regione').checked ||
					 document.getElementById('casl_to_altro').checked ;
					 
	dataComunicazioniEsterne = document.getElementById('casl_data').value != '';
	
	tipoNCComunicazioniEsterne = document.getElementById('casl_NC_rilevate').selectedIndex > 0;
	
	provvedimentiComunicazioniEsterne = document.getElementById('casl_provvedimenti_selezionati').selectedIndex > 0;
	
	if(comunicazioniEsterneA || dataComunicazioniEsterne || tipoNCComunicazioniEsterne || provvedimentiComunicazioniEsterne){
		/* mostra asterischi campi obbligatori */
		document.getElementById('comunicazioneA').style.display = '';
		document.getElementById('dataComunicazioneA').style.display = '';
		document.getElementById('tipoNonConformita').style.display = '';
		document.getElementById('provvedimentiAdottati').style.display = '';
	}
	else{
		/* nascondi asterischi campi obbligatori */
		document.getElementById('comunicazioneA').style.display = 'none';
		document.getElementById('dataComunicazioneA').style.display = 'none';
		document.getElementById('tipoNonConformita').style.display = 'none';
		document.getElementById('provvedimentiAdottati').style.display = 'none';
	}
	
}


function gestisciObbligatorietaMorteAnteMacellazione(){

	var dataMorteAnteMacellazione;
	var luogoMorteAnteMacellazione;
	var causaMorteAnteMacellazione;
	
	dataMorteAnteMacellazione = document.getElementById('mavam_data').value != '';
	luogoMorteAnteMacellazione = document.getElementById('mavam_luogo').selectedIndex > 0;
	causaMorteAnteMacellazione = trim( document.getElementById('mavam_motivo').value ) != '';
	
	if(dataMorteAnteMacellazione || luogoMorteAnteMacellazione || causaMorteAnteMacellazione){
		document.getElementById('dataMorteAnteMacellazione').style.display = '';
		document.getElementById('luogoMorteAnteMacellazione').style.display = '';
		document.getElementById('causaMorteAnteMacellazione').style.display = '';
	}
	else{
		document.getElementById('dataMorteAnteMacellazione').style.display = 'none';
		document.getElementById('luogoMorteAnteMacellazione').style.display = 'none';
		document.getElementById('causaMorteAnteMacellazione').style.display = 'none';
	}
	
}

function gestisciObbligatorietaVisitaAnteMortem(){

	var dataVisitaAnteMortem;
	var provvedimentoVisitaAnteMortem;
	
	dataVisitaAnteMortem = document.getElementById('vam_data').value != '';
	provvedimentoVisitaAnteMortem = document.getElementById('vam_provvedimenti').selectedIndex > 0;

	if(dataVisitaAnteMortem || provvedimentoVisitaAnteMortem){
		document.getElementById('dataVisitaAnteMortem').style.display = '';
		document.getElementById('provvedimentoVisitaAnteMortem').style.display = '';
		
	}
	else{
		document.getElementById('dataVisitaAnteMortem').style.display = 'none';
		document.getElementById('provvedimentoVisitaAnteMortem').style.display = 'none';
	}
	
}

function gestisciObbligatorietaVisitaPostMortem(){

	var dataVisitaPostMortem;
	var destinatarioVisitaPostMortem;
	
	dataVisitaPostMortem = document.getElementById('vpm_data').value != '';
	destinatarioVisitaPostMortem = trim(document.getElementById('destinatario_1_nome').value).length > 0 || trim(document.getElementById('destinatario_2_nome').value).length > 0;
	
	if(dataVisitaPostMortem || destinatarioVisitaPostMortem){
		document.getElementById('dataVisitaPostMortem').style.display = '';
		document.getElementById('destinatarioCarni1').style.display = '';
		document.getElementById('destinatarioCarni2').style.display = '';
	}
	else{
		document.getElementById('dataVisitaPostMortem').style.display = 'none';
		document.getElementById('destinatarioCarni1').style.display = 'none';
		document.getElementById('destinatarioCarni2').style.display = 'none';
	}
	
	
}


function gestisciTabellaNonConformitaVam(){
	if( document.forms[0].vam_esito[2].checked ){ //se è "non favorevole"
		document.getElementById('vam_tabella_non_conformita').style.display='';
	}
	else{ // se è "favorevole" o "favorevole con riserva"
		document.getElementById('vam_tabella_non_conformita').style.display='none';
	}
}

function mostraLcsoPatologiaAltro(select,index){

	var ALTRO = 5;
	if(select.value == ALTRO){
		document.getElementById('lcso_patologiaaltro_' + index).style.display='';
	}
	else{
		document.getElementById('lcso_patologiaaltro_' + index).style.display='none';
		document.getElementById('lcso_patologiaaltro_' + index).value = '';
	}
}

function impostaDestinatarioMacelloCorrente(index){
	document.getElementById('destinatario_' + index + '_id').value = document.getElementById('id_macello').value;
	document.getElementById('destinatario_' + index + '_nome').value = "<%=OrgDetails.getName().replaceAll("\"","'")%>";
	document.getElementById('destinatario_label_' + index).innerHTML = "<%=OrgDetails.getName().replaceAll("\"","'")%>";
		
	document.getElementById('esercenteNoGisa' + index).style.display = 'none';
}

</script>

<script type="text/javascript">

function showHide(_action) {
	var _myTableObj = document.getElementById('tableID1');
    if (_action == 'show') {
        _myTableObj.style.display = 'block';
    }
    
    if (_action == 'hide') {
        _myTableObj.style.display = 'none';
        //_myObj.parentNode.innerHTML = '<a href="#" onclick="showHide(this,\'show\');">Show Table</a>';      
    }
}
//-->
</script>


<style type="text/css">
	
/* Vertical Tabs
----------------------------------*/
.ui-tabs-vertical { /*width: 55em; */}
.ui-tabs-vertical .ui-tabs-nav { padding: .2em .1em .2em .2em; float: left; width: 12em; }
.ui-tabs-vertical .ui-tabs-nav li { clear: left; width: 100%; border-bottom-width: 1px !important; border-right-width: 0 !important; margin: 0 -1px .2em 0; }
.ui-tabs-vertical .ui-tabs-nav li a { display:block; }
.ui-tabs-vertical .ui-tabs-nav li.ui-tabs-selected { padding-bottom: 0; padding-right: .1em; border-right-width: 1px; border-right-width: 1px; }
.ui-tabs-vertical .ui-tabs-panel { padding: 1em; float: left; width: 50em;}

</style>

<body onbeforeunload="return gestisciUnload();" onload="gestisciObbligatorietaComunicazioniEsterne();gestisciObbligatorietaMorteAnteMacellazione();gestisciObbligatorietaVisitaAnteMortem();gestisciObbligatorietaVisitaPostMortem();gestisciUtenteVeterinario();gestisciRazza();gestisciCategoria();contralla_cd_bse();mostra_tab_test_bse_onload();gestisciBloccoAnimale();displayAbbattimento(); mostraDestinazione();gestisciTabellaNonConformitaVam();">
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Stabilimenti.do"><dhv:label name="stabilimenti.stabilimenti">Accounts</dhv:label></a> >
			<%
				if (request.getParameter("return") == null) {
			%>
					<a href="Stabilimenti.do?command=Search"><dhv:label
					name="stabilimenti.SearchResults">Search Results</dhv:label></a> >
					<a href="Stabilimenti.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Scheda Stabilimento</a> >
			<%
						} else if (request.getParameter("return").equals("dashboard")) {
					%>
					<a href="Stabilimenti.do?command=Dashboard"><dhv:label
					name="communications.campaign.Dashboard">Dashboard</dhv:label></a> > <%
						}
					%>
			<a href="MacellazioniNew.do?command=List&orgId=<%=OrgDetails.getOrgId()%>">Macellazioni</a> > <%= update ? "Modifica Capo" : "Aggiungi Capo" %>
		</td>
	</tr>
</table>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@page import="org.aspcfs.modules.macellazioninew.utils.MacelliUtil"%>

<%
	String param1 = "orgId=" + OrgDetails.getOrgId();
%>


<%@page import="org.aspcfs.utils.DateUtils"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.NonConformita"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.PatologiaRilevata"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Casl_Non_Conformita_Rilevata"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.ProvvedimentiCASL"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Organi"%>
<%@page import="org.aspcfs.modules.contacts.base.Contact"%>
<%@page import="java.util.Date"%>

<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>

<dhv:container 
	name="<%=(OrgDetails.isMacelloUngulati()) ? ("stabilimenti_macellazioni_ungulati")
								: ("stabilimenti")%>"
	selected="macellazioni" 
	object="OrgDetails" 
	param="<%=param1%>" 
	appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>' 
	hideContainer="<%=!OrgDetails.getEnabled()
						|| OrgDetails.isTrashed()%>">

<font color="red"><%=toHtmlValue((String) request.getAttribute("messaggio"))%></font><br/><br/>
<form name="main" action="MacellazioniNew.do?command=<%=(update) ? ("Update") : ("Save") %>" method="post" onsubmit="return controllaForm( false );" >
<input type="hidden" id="orgId" name="orgId" value="<%=OrgDetails.getOrgId()%>" />
<input type="hidden" id="id_macello" name="id_macello" value="<%=OrgDetails.getOrgId()%>" />
<input type="hidden" name="id" value="<%=Capo.getId() %>" />
<input type="hidden" name="capo_esistente" value="no" />
<input type="hidden" id="capo_in_bdn" name="capo_in_bdn" value="<%= CapoLog.isInBdn() ? "si" : "no" %>" />
<input type="hidden" name="codice_azienda_nascita_from_bdn" value="<%= CapoLog.getCodiceAziendaNascitaFromBdn() != null ? CapoLog.getCodiceAziendaNascitaFromBdn() : "" %>" />
<input type="hidden" name="data_nascita_from_bdn" value="<%= CapoLog.getDataNascitaFromBdn() != null ? sdf.format( CapoLog.getDataNascitaFromBdn() ) : "" %>" />
<input type="hidden" name="specie_from_bdn" value="<%= CapoLog.getSpecieFromBdn() %>" />
<input type="hidden" name="razza_from_bdn" value="<%= CapoLog.getRazzaFromBdn() %>" />
<input type="hidden" name="asl_speditore_from_bdn" value="<%= CapoLog.getAslSpeditoreFromBdn() %>" />
<input type="hidden" name="comune_speditore_from_bdn" value="<%= CapoLog.getComuneSpeditoreFromBdn() != null ? CapoLog.getComuneSpeditoreFromBdn() : "" %>" />
<input type="hidden" name="sesso_from_bdn" value="<%= CapoLog.getSessoFromBdn() != null ? CapoLog.getSessoFromBdn() : "" %>" />
<input type="hidden" name="clona" value="" />
<input type="hidden" id="solo_cd" name="solo_cd" value="<%= Capo.isSolo_cd() ? "true" : "false" %>" />
<input type="hidden" id="manca_BSE_Nmesi" name="manca_BSE_Nmesi" value="no" />
<input type="hidden" id="submitOK" name="submitOK" value="no" />
<input type="hidden" id="updateLiberoConsumo" name="updateLiberoConsumo" value="<%=update %>" />

<div class="demo">


<!-- NON CANCELLARE QUESTA RIGA -->
<a id="tab_test_bse"></a> 

<div id="tabs">
	<ul>
		<li id="li-1" ><a href="#tabs-1" onclick="javascript:showHide('hide');" >Controllo Documentale</a></li>
<!--		<li id="tab_test_bse"><a href="#tabs-14" >Test BSE</a></li> 	-->
		<li id="li-7" style="display: <%= update ? "" : "none" %>;" ><a href="#tabs-7" onclick="javascript:showHide('hide');settaSoloCD('false');" >Comunicazioni Esterne</a></li>
        <%-- <li><a href="#tabs-5">Respingimento</a></li> --%>
		<%-- li><a href="#tabs-6">Blocco Animale</a></li --%>
		<li id="li-4" style="display: <%= update ? "" : "none" %>;" ><a href="#tabs-4" onclick="javascript:showHide('hide');settaSoloCD('false');" >Morte Ant.Macellazione</a></li>
		<li id="li-2" style="display: <%= update ? "" : "none" %>;" ><a href="#tabs-2" onclick="javascript:showHide('hide');settaSoloCD('false');" >Evidenza Visita AM</a></li>
        <li id="li-3" style="display: <%= update ? "" : "none" %>;" ><a href="#tabs-3" onclick="javascript:showHide('show');settaSoloCD('false');" >Evidenza Visita PM</a></li>
        <%--<li><a href="#tabs-9">Macellazione e Distruz. Carcassa</a></li> --%>
		<%-- <li><a href="#tabs-15">Ricezione Esito Accertamenti</a></li> --%>
	</ul>
<div id="tabs-1">
<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody> 
    <tr>
        <td valign="top" width="55%">
        
        <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
            <tbody>
            
            <tr>
                <th colspan="2"><strong>Provenienza animale</strong></th>
            </tr>

            

       
         

            <tr class="containerBody">
            	<td class="formLabel">Codice Azienda di provenienza</td>
            	 <td>
                	   	<input type="text" onchange="searchBdn(this.value,2)" name="cd_codice_azienda_provenienza" value="<%=toHtmlValue( Capo.getCd_codice_azienda_provenienza() ) %>" /><font color="red" >*</font>
					
				
					<input type = "hidden" name = "cd_info_azienda_provenienza" id = "infoAzienda" value ="<%=toHtml2( Capo.getCd_info_azienda_provenienza())%>"/>
					<div id="rowInfoAzienda" align="right" style="padding-right: 20px;"><%= toHtml2( Capo.getCd_info_azienda_provenienza()) %></div>	
											<input type = "hidden" name = "rag_soc_azienda_pro" id = "rag_soc_azienda_provenienza" value ="<%=toHtml2( Capo.getRag_soc_azienda_prov())%>"/>
					<input type = "hidden" name = "denominazione_asl_azienda_prov" id = "denominazione_asl_azienda_prov" value ="<%=toHtml2( Capo.getDenominazione_asl_azienda_prov())%>"/>
					<input type = "hidden" name = "id_asl_azienda_prov" id = "id_codice_asl_azienda_prov" value ="<%=toHtml2( Capo.getId_asl_azienda_prov())%>"/>
					<input type = "hidden" name = "cod_asl_azienda_prov" id = "cod_asl_azienda_prov" value ="<%=toHtml2( Capo.getCod_asl_azienda_prov())%>"/>

				</td>
            </tr>


            <tr>
                <th colspan="2"><strong>Animale</strong>                </th>
            </tr>

            <tr>
                <td style="background-color:yellow;text-align: right" nowrap="nowrap">Matricola</td>
                <td style="background-color:yellow;">
                <%if( update ){ %>
                	<input type="hidden" id="cd_matricola" name="cd_matricola" maxlength="64" value="<%=toHtmlValue( ((Capo)Capo).getCd_matricola() ) %>" />
                	<%=toHtmlValue( ((Capo)Capo).getCd_matricola() ) %>
                <%} else { %>
                	<input type="text" id="cd_matricola" name="cd_matricola" onchange="Geocodifica.isCapoEsistente( this.value, gestisciCapoEsistente );" value="<%=toHtmlValue( ((Capo)Capo).getCd_matricola() ) %>" /><font color="red" >*</font>
                <%} %>
                </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" nowrap="nowrap">Codice Azienda<br>di Nascita</td>
                <td>
                	<input type="text" name="cd_codice_azienda" value="<%=toHtmlValue( Capo.getCd_codice_azienda() ) %>" />
				</td>
            </tr>

            <tr class="containerBody" >
                <td class="formLabel" nowrap="nowrap">Specie<br></td>
                <td>
                	<% Specie.setJsEvent("onChange=\"javascript:mostra_tab_test_bse();contralla_cd_bse();gestisciRazza();gestisciCategoria();\"");  %>
              		<%=Specie.getHtmlSelect("cd_specie", ((Capo)Capo).getCd_specie())%>
				</td>
            </tr>
            
            <tr class="containerBody" id="categoria_bovina">
              <td class="formLabel" nowrap="nowrap">Categoria</td>
              	<td>
              		<%=CategorieBovine.getHtmlSelect("cd_categoria_bovina", ((Capo)Capo).getCd_categoria_bovina() )%>
				</td>
            </tr>
            
            <tr class="containerBody" id="categoria_bufalina">
              <td class="formLabel" nowrap="nowrap">Categoria</td>
              	<td>
              		<%=CategorieBufaline.getHtmlSelect("cd_categoria_bufalina", ((Capo)Capo).getCd_categoria_bufalina() )%>
				</td>
            </tr>
            
			<tr class="containerBody" id="razza_bovina">
              <td class="formLabel" nowrap="nowrap">Razza</td>
              	<td>
              		<% Razze.setJsEvent("onChange=\"javascript:gestisciRazza();\"");  %>
              		<%=Razze.getHtmlSelect("cd_id_razza", ((Capo)Capo).getCd_id_razza())%>
              		<input type="text" id="cd_razza_altro" name="cd_razza_altro" value="<%=toHtmlValue( ((Capo)Capo).getCd_razza_altro() ) %>" />
				</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Sesso</td>
              <td>
                 <select name="cd_maschio" id="cd_maschio">
                   <option value="true" <%=(((Capo)Capo).isCd_maschio()) ? ("selected=\"selected\"") : ("") %> >maschio</option>
                   <option value="false" <%=(((Capo)Capo).isCd_maschio()) ? ("") : ("selected=\"selected\"") %> >femmina</option>
                 </select>
              </td>
            </tr>
            

            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Data di nascita</td>
              <td>
     <%--          		<zeroio:dateSelect field="cd_data_nascita" form="main" showTimeZone="false" timestamp="<%=Capo.getCd_data_nascita() %>" /><font color="red" >*</font> --%>
			      	<input readonly type="text" name="cd_data_nascita" size="10" value="<%=DateUtils.timestamp2string(((Capo)Capo).getCd_data_nascita())%>" />&nbsp;
			        <a href="#" onClick="cal19.select(document.forms[0].cd_data_nascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].cd_data_nascita);"><img src="images/delete.gif" align="absmiddle"/></a>
              </td>
            </tr>
            
           	 <tr class="containerBody">
                <td class="formLabel" nowrap="nowrap">Categoria di Rischio</td>
                <td>
                	<table class = "noborder">
					<tr><td>
                	0 <input type = "radio" id = "cd_categoria_rischio_0" name = "cd_categoria_rischio" value = "0" <%if(Capo.getCd_categoria_rischio()==0 || Capo.getCd_categoria_rischio()<=0  ){%>checked="checked"<%} %> />	[default]
                	</td></tr>
                	<tr><td>
                	1 <input type = "radio" id = "cd_categoria_rischio_1" name = "cd_categoria_rischio" value = "1" <%if(Capo.getCd_categoria_rischio()==1   ){%>checked="checked"<%} %> />	[bovini sani sopra i 72 mesi di eta']
                	</td></tr>
                	<tr><td>
                	2 <input type = "radio" id = "cd_categoria_rischio_2" name = "cd_categoria_rischio" value = "2" <%if(Capo.getCd_categoria_rischio()==2  ){%>checked="checked"<%} %> /> 	[mac. emergenza]->test BSE se età sopra i 48 mesi <br> 
                	</td></tr> 	
                	<tr><td>
                	3 <input type = "radio" id = "cd_categoria_rischio_3" name = "cd_categoria_rischio" value = "3" <%if(Capo.getCd_categoria_rischio()==3  ){%>checked="checked"<%} %> /> 	[mac. differita in caso di Bruc., TBC, Leucosi)<br>
                	</td></tr>
                	<tr><td>
                	5 <input type = "radio" id = "cd_categoria_rischio_5" name = "cd_categoria_rischio" value = "5" <%if(Capo.getCd_categoria_rischio()==5  ){%>checked="checked"<%} %> /> 	[Bovini morti durante il trasporto] BSE obbligatorio <br>
                	</td></tr>
                	</table>
				</td> 
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Vincolo sanitario</td>

              <td valign="middle">
              	<input type="checkbox" name="cd_vincolo_sanitario" <%=(Capo.isCd_vincolo_sanitario()) ? ("checked=\"checked\"") : ("") %> />
                Motivo
                <textarea rows="2" cols="40" name="cd_vincolo_sanitario_motivo"><%=toHtmlValue( Capo.getCd_vincolo_sanitario_motivo() ) %></textarea>
              </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" nowrap="nowrap">Numero Mod. 4</td>
                <td>
                	<input type="text" name="cd_mod4" value="<%=toHtmlValue( Capo.getCd_mod4() ) %>" />
				</td>
            </tr>
            
			<tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Data Mod. 4</td>
              <td>
<%--              		<zeroio:dateSelect field="cd_data_mod4" form="main" showTimeZone="false" timestamp="<%=Capo.getCd_data_mod4() %>" /> --%>
              		<input readonly type="text" name="cd_data_mod4" size="10" value="<%=DateUtils.timestamp2string(Capo.getCd_data_mod4())%>" />&nbsp;  
			        <a href="#" onClick="cal19.select(document.forms[0].cd_data_mod4,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].cd_data_mod4);"><img src="images/delete.gif" align="absmiddle"/></a>
              </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Macell. differita<br>piani risanamento</td>
              <td>
              <%
              
            	 // PianiRisanamento.setJsEvent("onchange=\"setCategoriaRischio()\"");
              %>
              	<%=PianiRisanamento.getHtmlSelect( "cd_macellazione_differita", Capo.getCd_macellazione_differita() ) %>
              </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap" id="test_bse_etichetta">Test BSE</td>
              <td id="test_bse_valore">
              	<input id="cd_bse_check" name="cd_bse_check" type="checkbox" onclick="toggleDiv('cd_bse_list'),annullaID_cd_bse(),contralla_cd_bse()" <%=(((Capo)Capo).getCd_bse() > 0) ? ("checked=\"checked\"") : ("") %> />
<%--               	<div <%=(Capo.getCd_bse() > 0) ? ("") : ("style=\"display: none\"") %> id="cd_bse_list" > --%>
				<div id="cd_bse_list" >
				<%
				//BseList.setJsEvent("onchange=\"setCategoriaRischio()\"");

				%>
              		<%=BseList.getHtmlSelect( "cd_bse", ((Capo)Capo).getCd_bse() )%>
              	</div>
              </td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Disponibili informazioni<br>catena alimentare </td>
              <td>
              	<input type="checkbox" name="cd_info_catena_alimentare" <%=(update && !Capo.isCd_info_catena_alimentare()) ? ("") : ("checked=\"checked\"") %> />
              </td>
            </tr>
                      
            <tr class="containerBody">
            	<td class="formLabel" nowrap="nowrap">Data arrivo al macello</td>
            	<td>
<%--              	<zeroio:dateSelect field="cd_data_arrivo_macello" form="main" showTimeZone="false" timestamp="<%=Capo.getCd_data_arrivo_macello() %>" /> --%>
					<input onfocus="gestisciDataCorrente();" onchange="alert('wewe')" readonly type="text" id="cd_data_arrivo_macello" name="cd_data_arrivo_macello" size="10" value="<%=DateUtils.timestamp2string(Capo.getCd_data_arrivo_macello())%>" />  
			        <font color="red" >*</font>
			        <a href="#" onClick="cal19.select(document.forms[0].cd_data_arrivo_macello,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].cd_data_arrivo_macello);"><img src="images/delete.gif" align="absmiddle"/></a>
            	</td>
            </tr>
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Data dichiarata dal gestore</td>
              <td>
              	<input type="checkbox" name="cd_data_arrivo_macello_flag_dichiarata" <%=(update && !Capo.isCd_data_arrivo_macello_flag_dichiarata()) ? ("") : ("checked=\"checked\"") %>/>
              </td>
            </tr>
             <tr>
                <th colspan="2"><strong>Identificazione Mezzo di Trasporto</strong></th>
            </tr>
            
             <tr class="containerBody">
                <td class="formLabel" nowrap="nowrap">Tipo</td>
                <td>
                	<input type="text" name="cd_tipo_mezzo_trasporto" value="<%=toHtmlValue( Capo.getCd_tipo_mezzo_trasporto() ) %>" />
				</td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel" nowrap="nowrap">Targa</td>
                <td>
                	<input type="text" name="cd_targa_mezzo_trasporto" value="<%=toHtmlValue( Capo.getCd_targa_mezzo_trasporto() ) %>" />
				</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Trasporto superiore<br>a 8 ore</td>
              <td>
              	<input type="checkbox" name="cd_trasporto_superiore8ore" <%=(Capo.isCd_trasporto_superiore8ore()) ? ("checked=\"checked\"") : ("") %> />
              </td>
            </tr>
             
<!--     SEZIONE RELATIVA A TEST BSE (PRIMA ERA UNA TAB APPARTE)   -->

<tbody id="sezioneTESTBSE">
				<tr>

				  <th colspan="2"><strong>Dettagli Test BSE</strong></th>
				</tr>

				 <tr class="containerBody">
					<td class="formLabel">Data Prelievo</td>
					<td>
<%--						<zeroio:dateSelect 
							field="bse_data_prelievo" 
							form="main" 
							showTimeZone="false" 
							timestamp="<%=Capo.getBse_data_prelievo() %>" />
 --%>							
						<input readonly type="text" name="bse_data_prelievo" size="10" value="<%=DateUtils.timestamp2string(Capo.getBse_data_prelievo())%>" />&nbsp;  
			        	<a href="#" onClick="cal19.select(document.forms[0].bse_data_prelievo,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 			<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].bse_data_prelievo);"><img src="images/delete.gif" align="absmiddle"/></a>
					</td>
				</tr>
					
				 <tr class="containerBody">
					<td class="formLabel">Data Ricezione Esito</td>
					<td>
<%--						<zeroio:dateSelect 
							field="bse_data_ricezione_esito" 
							form="main" 
							showTimeZone="false" 
							timestamp="<%=Capo.getBse_data_ricezione_esito() %>" />
 --%>
						<input readonly type="text" name="bse_data_ricezione_esito" size="10" value="<%=DateUtils.timestamp2string(Capo.getBse_data_ricezione_esito())%>" />&nbsp;  
			        	<a href="#" onClick="cal19.select(document.forms[0].bse_data_ricezione_esito,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 			<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].bse_data_ricezione_esito);"><img src="images/delete.gif" align="absmiddle"/></a> 
					</td>
				</tr>
				
				<tr class="containerBody">
					<td class="formLabel">Esito</td>
					<td>
						<select name="bse_esito" id="bse_esito" onchange="javascript:controlla_esito_testBSE()" >
							<option 
								value="-1" 
								<%=( toHtmlValue( Capo.getBse_esito() ).length() > 0 ) ? ("") : ("selected=\"selected\"") %> 
								>-- Seleziona --</option>
							<option 
								value="POSITIVO AL TEST" 
								<%=( "POSITIVO AL TEST".equals( toHtmlValue( Capo.getBse_esito() ) ) ) ? ("selected=\"selected\"") : ("") %> 
								>POSITIVO AL TEST</option>
							<option 
								value="NEGATIVO AL TEST" 
								<%=( "NEGATIVO AL TEST".equals( toHtmlValue( Capo.getBse_esito() ) ) ) ? ("selected=\"selected\"") : ("") %> 
								 >NEGATIVO AL TEST</option>
						</select>
					</td>
				 </tr>
				
				<tr class="containerBody">
					<td class="formLabel">Note</td>
					<td><textarea rows="2" cols="40" name="bse_note"><%=toHtmlValue( Capo.getBse_note() ) %></textarea></td>
				</tr>


</tbody>

<!--***************************************************************-->
            
            
            <tr>
                <th colspan="2"><strong>Veterinari addetti al controllo</strong>                </th>
            </tr>
            
            <tr class="containerBody">
            	<td colspan="2">
		            <table>
		            	<tr class="containerBody" style="display: block;">
		                	<td>
		                		1. <input 
		                				value="<%=toHtmlValue( Capo.getCd_veterinario_1() ) %>" 
		                				onchange="javascript:toggleVetDiv('cd_veterinario_1')" 
		                				size="35" id="cd_veterinario_1" 
		                				name="cd_veterinario_1" 
		                				type="text" />
			                	<%-- 
			                	<% Veterinari.setJsEvent("onchange=\"set_vet( this, 'cd_veterinario_1')\""); %>
			                	<%=Veterinari.getHtmlSelect("veterinari_cd1", "-1") %>
			                	--%>
			                	<% HashMap<String,ArrayList<Contact>> listaVeterinari = (HashMap<String,ArrayList<Contact>>)request.getAttribute("listaVeterinari"); %>
								<select id="veterinari_cd1" name="veterinari_cd1" onchange="set_vet( this, 'cd_veterinario_1'); preselezionaVeterinario(this, 1);">
									<option value="-1">Seleziona</option>
									<%for (String gruppo : listaVeterinari.keySet()){ %>
										<optgroup label="<%=gruppo %>"></optgroup>
										<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
											<option <%if (Capo.getCd_veterinario_1() != null && Capo.getCd_veterinario_1().trim().equals(vet.getNameLast().trim()) ){%>selected="selected" <%} %>  value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
								</select>
		                	</td>
		                	<td style="background: none; border-right: none;">
		                		<font color="red" >*</font>
		                	</td>
		                </tr>
			            <tr 
			            	class="containerBody" 
			            	id="cd_veterinario_1_toggle" 
			            	<%=( toHtmlValue( Capo.getCd_veterinario_1() ).length() == 0 ) ? ("style=\"display: none\"") : ("") %> >
			                <td>
				                2. <input 
		                				value="<%=toHtmlValue( Capo.getCd_veterinario_2() ) %>" 
						                onchange="javascript:toggleVetDiv('cd_veterinario_2')" 
						                size="35" id="cd_veterinario_2" 
						                name="cd_veterinario_2" 
						                type="text" />
								<%-- 						                
				                <% Veterinari.setJsEvent("onchange=\"set_vet( this, 'cd_veterinario_2')\""); %>
								<%= Veterinari.getHtmlSelect("veterinari_cd2", "-1") %>
								--%>
								<select id="veterinari_cd2" name="veterinari_cd2" onchange="set_vet( this, 'cd_veterinario_2'); preselezionaVeterinario(this, 2);">
									<option value="-1">Seleziona</option>
									<%for (String gruppo : listaVeterinari.keySet()){ %>
										<optgroup label="<%=gruppo %>"></optgroup>
										<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
											<option <%if (Capo.getCd_veterinario_2() != null && Capo.getCd_veterinario_2().trim().equals(vet.getNameLast().trim()) ){%>selected="selected" <%} %> value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
								</select>
							</td>
			            </tr>
			            <tr 
			            	class="containerBody" 
			            	id="cd_veterinario_2_toggle" 
			            	<%=( toHtmlValue( Capo.getCd_veterinario_2() ).length() == 0 ) ? ("style=\"display: none\"") : ("") %> >
			                <td>
				                3. <input 
		                				value="<%=toHtmlValue( Capo.getCd_veterinario_3() ) %>" 
				                		size="35" 
				                		id="cd_veterinario_3"  
				                		name="cd_veterinario_3" 
				                		type="text" />
				                <%-- 		
				                <% Veterinari.setJsEvent("onchange=\"set_vet( this, 'cd_veterinario_3')\""); %>
				                <%= Veterinari.getHtmlSelect("veterinari_cd3", "-1") %>
				                --%>
				                <select id="veterinari_cd3" name="veterinari_cd3" onchange="set_vet( this, 'cd_veterinario_3'); preselezionaVeterinario(this, 3);">
									<option value="-1">Seleziona</option>
									<%for (String gruppo : listaVeterinari.keySet()){ %>
										<optgroup label="<%=gruppo %>"></optgroup>
										<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
											<option <%if (Capo.getCd_veterinario_3() != null && Capo.getCd_veterinario_3().trim().equals(vet.getNameLast().trim()) ){%>selected="selected" <%} %> value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
								</select>
			                </td>
			            </tr>
		            </table>
	            </td>
            </tr>
            <tr>
                <th colspan="2"><strong>Dettagli addizionali</strong>                </th>
            </tr>
            <tr class="containerBody">
               <td class="formLabel">Note</td>
               <td>
               		<textarea name="cd_note" rows="3" cols="70"><%=toHtmlValue( Capo.getCd_note() ) %></textarea>
               </td>
            </tr>        
      </tbody></table>        
      </td>

	</tr>
	</tbody>
</table>
<%
	ConfigTipo configTipo = (ConfigTipo)request.getSession().getAttribute("configTipo");
%>
	<input id="liberoConsumoButton" type="button" value="Libero Consumo >>" onclick="if(controllaForm( true )){document.main.action='MacellazioniNew.do?command=ToLiberoConsumo';settaSoloCD('false');document.main.submit();};" />

	<%-- <%	if (update) { %> --%>
		<script type="text/javascript">nascondi_liberoConsumoButton(false)</script>
	<%-- <% } else { %>
		<script type="text/javascript">nascondi_liberoConsumoButton(false)</script>
	<% } %> --%>
	
	<%if(!update){ %>
<!--	<div style="text-align: center; margin-top: 20px;">-->
	<input type="button" value="Altre operazioni" onclick="checkControlloDocumentale();" />
<!--	</div>-->
	<%} %>


</div>
	
<div id="tabs-2">
<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="55%">



				<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
          
           <tr>
              <th colspan="2"><strong>Animale</strong></th>
            </tr>
            

            <tr>
                <td style="background-color:yellow;text-align: right" nowrap="nowrap">Matricola</td>
                <td style="background-color:yellow;">
                	<%=toHtmlValue( ((Capo)Capo).getCd_matricola() ) %>
                </td>
            </tr>
            
           
            <tr class="containerBody" >
                <td class="formLabel" nowrap="nowrap">Specie<br></td>
                <td>
                	
              		<%=Specie.getSelectedValue( ((Capo)Capo).getCd_specie())%>
				</td>
            </tr>
            
            <%if(((Capo)Capo).getCd_categoria_bovina()>0){ %>
            <tr class="containerBody" id="categoria_bovina">
              <td class="formLabel" nowrap="nowrap">Categoria</td>
              	<td>
              		<%=CategorieBovine.getSelectedValue( ((Capo)Capo).getCd_categoria_bovina() )%>
				</td>
            </tr>
            <%} %>
            
             <%if(((Capo)Capo).getCd_categoria_bufalina()>0){ %>
            <tr class="containerBody" id="categoria_bufalina">
              <td class="formLabel" nowrap="nowrap">Categoria</td>
              	<td>
              		<%=CategorieBufaline.getSelectedValue( ((Capo)Capo).getCd_categoria_bufalina() )%>
				</td>
            </tr>
            <%} %>
            
			<tr class="containerBody" id="razza_bovina">
              <td class="formLabel" nowrap="nowrap">Razza</td>
              	<td>
              		
              		<%=Razze.getSelectedValue( ((Capo)Capo).getCd_id_razza())%>
              		
				</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Sesso</td>
              <td>
                 <select disabled="disabled">
                   <option value="true" <%=(((Capo)Capo).isCd_maschio()) ? ("selected=\"selected\"") : ("") %> >maschio</option>
                   <option value="false" <%=(((Capo)Capo).isCd_maschio()) ? ("") : ("selected=\"selected\"") %> >femmina</option>
                 </select>
              </td>
            </tr>
            <tr>
              <th colspan="2"><strong>Visita Ante Mortem </strong></th>
            </tr>

            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
<%--               	<zeroio:dateSelect field="vam_data" form="main" showTimeZone="false" timestamp="<%=Capo.getVam_data() %>" /> --%>
					<input readonly type="text" id="vam_data" name="vam_data" onfocus="riportaDataArrivoMacello(this);gestisciObbligatorietaVisitaAnteMortem();" size="10" value="<%=DateUtils.timestamp2string(Capo.getVam_data())%>" />&nbsp;  
			        <font color="red" id="dataVisitaAnteMortem" style="display: none;">*</font>
			        <a href="#" onClick="cal19.select(document.forms[0].vam_data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].vam_data);"><img src="images/delete.gif" align="absmiddle"/></a>
              </td>
            </tr>
           
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td>
						<input 
							type=radio 
							onclick="if(this.checked==true) document.getElementById('vam_tabella_non_conformita').style.display='none';gestisciObbligatorietaVisitaAnteMortem();" 
							id= "vam_esito" 
							name="vam_esito" 
							value="Favorevole" 
							<%=(update && !Capo.getVam_esito().equalsIgnoreCase("Favorevole")) ? ("") : ("checked=\"checked\"") %> >Favorevole<br/>
						<input 
							type=radio 
							onclick="if(this.checked==true) document.getElementById('vam_tabella_non_conformita').style.display='none';gestisciObbligatorietaVisitaAnteMortem();"
							id= "vam_esito" 
							name="vam_esito" 
							value="Favorevole con riserva"
							<%=(update && Capo.getVam_esito().equalsIgnoreCase("Favorevole con riserva") ) ? ("checked=\"checked\"") : ("") %> >Favorevole con riserva<br/>
						<input 
							type=radio 
							onclick="if(this.checked==true) document.getElementById('vam_tabella_non_conformita').style.display='';document.getElementById('nc_tipo_1').value = -1;document.getElementById('nc_tipo_2').value = -1;document.getElementById('nc_tipo_3').value = -1;gestisciObbligatorietaVisitaAnteMortem();" 
							id= "vam_esito" 
							name="vam_esito" 
							value="Non Favorevole"
							<%=(update && Capo.getVam_esito().equalsIgnoreCase("Non favorevole") ) ? ("checked=\"checked\"") : ("") %> >Non favorevole 
						
							
							<p>
							</p>
				</td>               
            </tr>

            <tr class="containerBody">
                <td class="formLabel">Provvedimento adottato</td>
                <td>
					<%
						ProvvedimentiVAM.setJsEvent("onChange=\"javascript:displayAbbattimento();gestisciObbligatorietaVisitaAnteMortem();\"");
					%>                               
                	
	               	<%=ProvvedimentiVAM.getHtmlSelect( "vam_provvedimenti", Capo.getVam_provvedimenti() ) %>
	               	<font color="red" id="provvedimentoVisitaAnteMortem" style="display: none;">*</font>
                </td>
            </tr>
            
            
            <tr class="containerBody">
            	<td class="formLabel">Comunicazione a</td>
            	<td>
            		<input 
            			type="checkbox" 
            			id="vam_to_asl_origine"
            			name="vam_to_asl_origine" 
            			<%=(Capo.isVam_to_asl_origine()) ? ("checked=\"checked\"") : ("") %> 
            			/> ASL origine <br/>
            		<input 
            			type="checkbox" 
            			id="vam_to_proprietario_animale"
            			name="vam_to_proprietario_animale" 
            			<%=(Capo.isVam_to_proprietario_animale()) ? ("checked=\"checked\"") : ("") %> 
            			/> Proprietario animale <br/>
            		<input 
            			type="checkbox" 
            			id="vam_to_azienda_origine"
            			name="vam_to_azienda_origine" 
            			<%=(Capo.isVam_to_azienda_origine()) ? ("checked=\"checked\"") : ("") %>
            			/> Azienda di origine <br/>
            		<input 
            			type="checkbox" 
            			id="vam_to_proprietario_macello"
            			name="vam_to_proprietario_macello" 
            			<%=(Capo.isVam_to_proprietario_macello()) ? ("checked=\"checked\"") : ("") %>
            			/> Proprietario macello <br/>
            		<input 
            			type="checkbox" 
            			id="vam_to_pif"
            			name="vam_to_pif" 
            			<%=(Capo.isVam_to_pif()) ? ("checked=\"checked\"") : ("") %>
            			/> P.I.F. <br/>	
            		<input 
            			type="checkbox" 
            			id="vam_to_uvac"
            			name="vam_to_uvac" 
            			<%=(Capo.isVam_to_uvac()) ? ("checked=\"checked\"") : ("") %>
            			/> U.V.A.C. <br/>	
            		<input 
            			type="checkbox" 
            			id="vam_to_regione"
            			name="vam_to_regione" 
            			<%=(Capo.isVam_to_regione()) ? ("checked=\"checked\"") : ("") %>
            			/> Regione <br/>
            		<input 
            			type="checkbox"
            			id="vam_to_altro" 
            			name="vam_to_altro" 
            			onclick="visualizzaTextareaVamToAltro();"
            			<%=(Capo.isVam_to_altro()) ? ("checked=\"checked\"") : ("") %>
            			/> Altro <br/>
            		<textarea id="vam_to_altro_testo" name="vam_to_altro_testo" rows="2" cols="40" style="display: <%=(Capo.isVam_to_altro()) ? "''" : "none" %>;" ><%=toHtmlValue( Capo.getVam_to_altro_testo() ) %></textarea>
            			
            	</td>
            </tr>
            
              <tr class="containerBody">
		 		<td class="formLabel">Note</td>
		 		<td><textarea rows="2" cols="40" name="vam_provvedimenti_note"><%=toHtmlValue( Capo.getVam_provvedimenti_note() ) %></textarea></td>
			  </tr>
            
                 </table>
                </td>

    </tr>
     
    <tr id="vam_tabella_non_conformita" <%=(update && !Capo.getVam_esito().equalsIgnoreCase("Favorevole")) ? ("") : ("style=\"display: none;\"") %> >
    	<td>
    		<table  class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
    			<tr>
    				<th colspan="2">Non Conformità</th>
    			</tr>
    			<tr>
    				<th>Tipo</th> <th>Note</th>
    			</tr>
    			<tr class="containerBody">
    				<td>
    					<%
    						int non_conformita_index	= -1;
							int non_conformita_id		= -1;
    						String non_conformita_note	= "";
    						
    						if( NCVAM.size() > 0 )
    						{
    							non_conformita_index	= ( (NonConformita)NCVAM.get( 0 ) ).getId_tipo();
    							non_conformita_note		= ( (NonConformita)NCVAM.get( 0 ) ).getNote();
    							non_conformita_id		= ( (NonConformita)NCVAM.get( 0 ) ).getId();
    						}
    						else
    						{
    							non_conformita_index	= -1;
    							non_conformita_id		= -1;
    							non_conformita_note		= "";
    						}
    					%>
    					<%=TipiNonConformita_Gruppo.getHtmlSelect( "nc_tipo_1", non_conformita_index )%>
    					<input type="hidden" name="nc_id_1" value="<%=non_conformita_id %>" />
    				</td>

    				<td><textarea name="nc_note_1" rows="2" cols="25" ><%=toHtmlValue( non_conformita_note ) %></textarea> </td>
    				
    			</tr>
    			<tr class="containerBody">
    				<td>
    					<%
	    					if( NCVAM.size() > 1 )
							{
								non_conformita_index	= ( (NonConformita)NCVAM.get( 1 ) ).getId_tipo();
								non_conformita_note		= ( (NonConformita)NCVAM.get( 1 ) ).getNote();
    							non_conformita_id		= ( (NonConformita)NCVAM.get( 1 ) ).getId();
    						}
    						else
    						{
    							non_conformita_index	= -1;
    							non_conformita_id		= -1;
    							non_conformita_note		= "";
    						}
						%>
    					<%=TipiNonConformita_Gruppo.getHtmlSelect( "nc_tipo_2",non_conformita_index )%>
    					<input type="hidden" name="nc_id_2" value="<%=non_conformita_id %>" />
    				</td>

    				<td><textarea name="nc_note_2" rows="2" cols="25" ><%=toHtmlValue( non_conformita_note ) %></textarea> </td>
    				
    			</tr>
    			<tr class="containerBody">
    				<td>
    					<%
	    					if( NCVAM.size() > 2 )
							{
								non_conformita_index	= ( (NonConformita)NCVAM.get( 2 ) ).getId_tipo();
								non_conformita_note		= ( (NonConformita)NCVAM.get( 2 ) ).getNote();
    							non_conformita_id		= ( (NonConformita)NCVAM.get( 2 ) ).getId();
    						}
    						else
    						{
    							non_conformita_index	= -1;
    							non_conformita_id		= -1;
    							non_conformita_note		= "";
    						}
						%>
    					<%=TipiNonConformita_Gruppo.getHtmlSelect( "nc_tipo_3", non_conformita_index )%>
    					<input type="hidden" name="nc_id_3" value="<%=non_conformita_id %>" />
    				</td>

    				<td><textarea name="nc_note_3" rows="2" cols="25" ><%=toHtmlValue( non_conformita_note ) %></textarea> </td>
    				
    			</tr>
    		</table>
    	</td>
    </tr>
    
     
    
    </tbody>

</table>
    
      </td>
         
	</tr>

	

</tbody>

	

</table>
	
	
	
	
	
	
	
	
<table id="table_abbattimento" width="100%" border="0" cellpadding="2" cellspacing="2" style="display:none;">
    <tbody><tr>
        <td valign="top" width="55%">
		<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>

        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>
              <th colspan="2"><strong>Abbattimento</strong></th>
            </tr>
            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
<%--			<zeroio:dateSelect field="abb_data" form="main" showTimeZone="false" timestamp="<%=Capo.getAbb_data() %>"/>  --%>              
              	<input readonly type="text" id="abb_data" name="abb_data" size="10" value="<%=DateUtils.timestamp2string(Capo.getAbb_data())%>" />&nbsp;  
			    <a href="#" onClick="cal19.select(document.forms[0].abb_data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 	<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 	<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].abb_data);"><img src="images/delete.gif" align="absmiddle"/></a>
             </td>

            </tr>
     
                <tr class="containerBody">
                <td class="formLabel">Motivo</td>
                <td>
                	<textarea rows="2" cols="40" name="abb_motivo"><%=toHtmlValue( Capo.getAbb_motivo() ) %></textarea>
                </td>
                </tr>
                
             <tr>
                <th colspan="2"><strong>Veterinari addetti al controllo</strong></th>
            </tr>
            
            <tr class="containerBody">
            	<td colspan="2">
		            <table>
		            	<tr class="containerBody">
		                	<td>1. <input 
		                			value="<%=toHtmlValue( Capo.getAbb_veterinario() ) %>"
		                			onchange="javascript:toggleVetDiv('abb_veterinario')" 
		                			size="25" id="abb_veterinario" 
		                			name="abb_veterinario" 
		                			type="text" />
		                	<% Veterinari.setJsEvent( "onchange=\"set_vet( this, 'abb_veterinario')\"" ); %>
		                	<%=Veterinari.getHtmlSelect( "veterinari_abb", "-1" )%></td>
		                </tr>
			            <tr 
			            	class="containerBody" 
			            	id="abb_veterinario_toggle" 
			            	<%=( toHtmlValue( Capo.getAbb_veterinario() ).length() == 0 ) ? ("style=\"display: none\"") : ("") %> >
			                <td>2. <input 
		                			value="<%=toHtmlValue( Capo.getAbb_veterinario_2() ) %>"
			                		onchange="javascript:toggleVetDiv('abb_veterinario_2')" 
			                		size="25" id="abb_veterinario_2" 
			                		name="abb_veterinario_2" 
			                		type="text" />
			                <%Veterinari.setJsEvent("onchange=\"set_vet( this, 'abb_veterinario_2')\"");%>
			                <%=Veterinari.getHtmlSelect("veterinari_abb2", "-1")%></td>
			            </tr>
			            <tr 
			            	class="containerBody" 
			            	id="abb_veterinario_2_toggle"
			            	<%=( toHtmlValue( Capo.getAbb_veterinario_2() ).length() == 0 ) ? ("style=\"display: none\"") : ("") %> >
			                <td>3. <input 
		                			value="<%=toHtmlValue( Capo.getAbb_veterinario_3() ) %>"
			                		size="25" 
			                		id="abb_veterinario_3"  
			                		name="abb_veterinario_3" 
			                		type="text" />
			                <%Veterinari.setJsEvent("onchange=\"set_vet( this, 'abb_veterinario_3')\"");%>
							<%=Veterinari.getHtmlSelect("veterinari_abb3", "-1")%></td>
			            </tr>
		            </table>
	            </td>
            </tr>

          </tbody>
        </table>

	</td>
    
        <td valign="top" width="50%"><!-- FILTRI DI RICERCA --></td>
    </tr></tbody></table>
		
</td>

</tr></tbody></table>
	
	
	
	
</div>
	
	
		
<div id="tabs-3">
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="55%">
		<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
           <tr>
              <th colspan="2"><strong>Animale</strong></th>
            </tr>
            

            <tr>
                <td style="background-color:yellow;text-align: right" nowrap="nowrap">Matricola</td>
                <td style="background-color:yellow;">
                	<%=toHtmlValue( ((Capo)Capo).getCd_matricola() ) %>
                </td>
            </tr>
            
           
            <tr class="containerBody" >
                <td class="formLabel" nowrap="nowrap">Specie<br></td>
                <td>
                	
              		<%=Specie.getSelectedValue( ((Capo)Capo).getCd_specie())%>
				</td>
            </tr>
            
            <%if(((Capo)Capo).getCd_categoria_bovina()>0){ %>
            <tr class="containerBody" id="categoria_bovina">
              <td class="formLabel" nowrap="nowrap">Categoria</td>
              	<td>
              		<%=CategorieBovine.getSelectedValue( ((Capo)Capo).getCd_categoria_bovina() )%>
				</td>
            </tr>
            <%} %>
            
             <%if(((Capo)Capo).getCd_categoria_bufalina()>0){ %>
            <tr class="containerBody" id="categoria_bufalina">
              <td class="formLabel" nowrap="nowrap">Categoria</td>
              	<td>
              		<%=CategorieBufaline.getSelectedValue( ((Capo)Capo).getCd_categoria_bufalina() )%>
				</td>
            </tr>
            <%} %>
            
			<tr class="containerBody" id="razza_bovina">
              <td class="formLabel" nowrap="nowrap">Razza</td>
              	<td>
              		
              		<%=Razze.getSelectedValue( ((Capo)Capo).getCd_id_razza())%>
              		
				</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Sesso</td>
              <td>
                 <select disabled="disabled">
                   <option value="true" <%=(((Capo)Capo).isCd_maschio()) ? ("selected=\"selected\"") : ("") %> >maschio</option>
                   <option value="false" <%=(((Capo)Capo).isCd_maschio()) ? ("") : ("selected=\"selected\"") %> >femmina</option>
                 </select>
              </td>
            </tr>

            <tr>
              <th colspan="2"><strong>Macellazione</strong></th>
            </tr>
<!--            <tr class="containerBody">-->
<!--              <td class="formLabel" >Progressivo</td>-->
<!--              <td >-->
	              	<input type="hidden" name="mac_progressivo" value="<%=Capo.getMac_progressivo() %>" />
<!--              </td>-->
<!--            </tr>-->
 
            <tr class="containerBody">
              <td class="formLabel" >Tipo</td>
              <td>
              <%
              //TipiMacellazione.setJsEvent("onchange=\"setCategoriaRischio()\"");
              %>
              <%=TipiMacellazione.getHtmlSelect( "mac_tipo", Capo.getMac_tipo() ) %></td>
            </tr>
            <tr class="containerBody">
              <td class="formLabel" >Progressivo Macellazione</td>
              <td><input type="text" id="progressivo_macellazione" name="progressivo_macellazione" value="<%=toHtmlValue( Capo.getProgressivo_macellazione() ) %>" onblur="checkProgressivoMacellazione();"></input></td>
            </tr>
     
          </tbody>
        </table>
         </td>
        <%-- %>td valign="top" width="50%"><!-- FILTRI DI RICERCA --></td--%>
    </tr></tbody></table>
	</td>
</tr></tbody></table>
	
	<table width="100%" border="0" cellpadding="2" cellspacing="2" id="tab">
    <tbody>
    <tr>
        <td valign="top" width="55%">
        <table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody>
    <tr>
        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>
              <th colspan="2"><strong>Visita Post Mortem</strong></th>

            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Data Macellazione</td>
              <td>
<%--              	<zeroio:dateSelect field="vpm_data" form="main" showTimeZone="false" timestamp="<%=Capo.getVpm_data() %>" />  --%>
					<input readonly type="text" id="vpm_data" name="vpm_data"  onfocus="riportaDataArrivoMacello(this);gestisciObbligatorietaVisitaPostMortem();" size="10" value="<%=DateUtils.timestamp2string(Capo.getVpm_data())%>" />&nbsp;  
			        <font color="red" id="dataVisitaPostMortem" style="display: none;">*</font>
			        <a href="#" onClick="cal19.select(document.forms[0].vpm_data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].vpm_data);"><img src="images/delete.gif" align="absmiddle"/></a>
              </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td>
                <%
                	EsitiVpm.setSelectSize(5);
                	EsitiVpm.setJsEvent( "onChange=\"javascript:displayVPMpatologie(),displayLCSO(),resetSelect();\"" );
                %>                               
                <%=EsitiVpm.getHtmlSelect( "vpm_esito", Capo.getVpm_esito() ) %>
                </td>
            </tr>
<!--            <tr class="containerBody" id="tr_vpm_abb_dist_carcassa" style="display: none;">-->
<!--               <td class="formLabel">Distruzione Carcassa</td>-->
<!--               <td>-->
<!--               		<input type="checkbox" name="abb_dist_carcassa" id="vpm_abb_dist_carcassa"/>-->
<!--               </td>-->
<!--            </tr>              -->

			<% if (false) { %>
			<tr class="containerBody">
              <td class="formLabel" >Data Ricezione Esito</td>
              <td>
              	<zeroio:dateSelect field="vpm_data_esito" form="main" showTimeZone="false" timestamp="<%=Capo.getVpm_data_esito() %>" />
              </td>
            </tr>
            <% } %>
            
            

            <tr 
            	class="containerBody" 
            	id="vpm_riga_patologie" 
            	<%=(update && (Capo.getVpm_esito() == 2 || Capo.getVpm_esito() == 4 )) ? ("") : ("style=\"display: none\"") %> >
                <td class="formLabel">Patologie Rilevate</td>
                <%
                	Patologie.setMultiple(true);
                	Patologie.setSelectSize(5);
					LookupList vpm_patologie_selezionate = new LookupList();
					for( PatologiaRilevata pr: (ArrayList<PatologiaRilevata>)PatologieRilevate )
					{
						vpm_patologie_selezionate.addItem( pr.getId_patologia(), "" );//add( Patologie.get( Patologie.getLevelFromId(pr.getId_patologia()) ) );
					}
                %>
                <td><%=Patologie.getHtmlSelect( "vpm_patologie_rilevate", vpm_patologie_selezionate ) %></td>
            </tr> 
                
             <tr class="containerBody">
                <td class="formLabel">Causa Presunta o Accertata<br/>(per eventuali patologie)</td>
                <td><input type="text" name="vpm_causa_patologia" value="<%=toHtmlValue( Capo.getVpm_causa_patologia() ) %>" /></td>
             </tr>

             <tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><textarea name="vpm_note" rows="2" cols="25"><%=toHtmlValue( Capo.getVpm_note() ) %></textarea></td>

            </tr>
            
             <tr>
                <th colspan="2"><strong>Veterinari addetti al controllo</strong></th>
            </tr>
            
            <tr class="containerBody">
            	<td colspan="2">
		            <table>
		            	<tr class="containerBody" style="display: block;">
		                	<td>1. <input 
		                			value="<%=toHtmlValue( Capo.getVpm_veterinario() ) %>"
		                			onchange="javascript:toggleVetDiv('vpm_veterinario')" 
		                			size="35" id="vpm_veterinario" 
		                			name="vpm_veterinario" 
		                			type="text" />
		                	 		
		                	<%--  		
		                	<% Veterinari.setJsEvent( "onchange=\"set_vet( this, 'vpm_veterinario')\"" ); %>
							<%=Veterinari.getHtmlSelect( "veterinari_vpm", "-1" )%>
							--%>
							<select id="veterinari_vpm" name="veterinari_vpm" onchange="set_vet( this, 'vpm_veterinario')">
									<option value="-1">Seleziona</option>
									<%for (String gruppo : listaVeterinari.keySet()){ %>
										<optgroup label="<%=gruppo %>"></optgroup>
										<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
											<option <%if (Capo.getVpm_veterinario() != null && Capo.getVpm_veterinario().trim().equals(vet.getNameLast().trim()) ){%>selected="selected" <%} %> value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
							</select>
							
							</td>
		                </tr>
			            <tr 
			            	class="containerBody" 
			            	id="vpm_veterinario_toggle" 
			            	<%=( toHtmlValue( Capo.getVpm_veterinario() ).length() == 0 ) ? ("style=\"display: none\"") : ("") %> >
			                <td>2. <input 
		                			value="<%=toHtmlValue( Capo.getVpm_veterinario_2() ) %>"
			                		onchange="javascript:toggleVetDiv('vpm_veterinario_2')" 
			                		size="35" 
			                		id="vpm_veterinario_2" 
			                		name="vpm_veterinario_2" 
			                		type="text" />
			                <%-- 
			                <% Veterinari.setJsEvent( "onchange=\"set_vet( this, 'vpm_veterinario_2')\"" ); %>
			                <%=Veterinari.getHtmlSelect( "veterinari_vpm2", "-1" ) %>
			                --%>
			                <select id="veterinari_vpm2" name="veterinari_vpm2" onchange="set_vet( this, 'vpm_veterinario_2')">
									<option value="-1">Seleziona</option>
									<%for (String gruppo : listaVeterinari.keySet()){ %>
										<optgroup label="<%=gruppo %>"></optgroup>
										<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
											<option <%if (Capo.getVpm_veterinario_2() != null && Capo.getVpm_veterinario_2().trim().equals(vet.getNameLast().trim()) ){%>selected="selected" <%} %> value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
							</select>
			                </td>
			            </tr>
			            
			            
			            <tr 
			            	class="containerBody" 
			            	id="vpm_veterinario_2_toggle" 
			            	<%=( toHtmlValue( Capo.getVpm_veterinario_2() ).length() == 0 ) ? ("style=\"display: none\"") : ("") %> >
			                <td>3. <input 
		                			value="<%=toHtmlValue( Capo.getVpm_veterinario_3() ) %>"
			                		size="35" 
			                		id="vpm_veterinario_3"  
			                		name="vpm_veterinario_3" 
			                		type="text" />
			                <%-- 		
			                <% Veterinari.setJsEvent( "onchange=\"set_vet( this, 'vpm_veterinario_3')\"" ); %>
			                <%=Veterinari.getHtmlSelect( "veterinari_vpm3", "-1" ) %>
			                --%>
			                <select id="veterinari_vpm3" name="veterinari_vpm3" onchange="set_vet( this, 'vpm_veterinario_3')">
									<option value="-1">Seleziona</option>
									<%for (String gruppo : listaVeterinari.keySet()){ %>
										<optgroup label="<%=gruppo %>"></optgroup>
										<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
											<option <%if (Capo.getVpm_veterinario_3() != null && Capo.getVpm_veterinario_3().trim().equals(vet.getNameLast().trim()) ){%>selected="selected" <%} %> value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
							</select>
			                
			                </td>
			            </tr>
		            </table>
	            </td>
            </tr>
            
              <tr>
              
        <% if (false) { %>
        <td valign="top" width="100%">
        
        <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>

              <th colspan="2"><strong>Test BSE</strong></th>
            </tr>

             <tr class="containerBody">
                <td class="formLabel">Data Prelievo</td>
                <td>
                	<zeroio:dateSelect 
                		field="bse_data_prelievo" 
                		form="main" 
                		showTimeZone="false" 
                		timestamp="<%=Capo.getBse_data_prelievo() %>" />
                </td>
            </tr>
                
             <tr class="containerBody">
                <td class="formLabel">Data Ricezione Esito</td>
                <td>
                	<zeroio:dateSelect 
                		field="bse_data_ricezione_esito" 
                		form="main" 
                		showTimeZone="false" 
                		timestamp="<%=Capo.getBse_data_ricezione_esito() %>" />
                </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td>
                	<select name="bse_esito" id="bse_esito" >
                		<option 
                			value="" 
                			<%=( toHtmlValue( Capo.getBse_esito() ).length() > 0 ) ? ("") : ("selected=\"selected\"") %> 
                			>-- Seleziona --</option>
                		<option 
                			value="POSITIVO" 
                			<%=( "POSITIVO".equals( toHtmlValue( Capo.getBse_esito() ) ) ) ? ("selected=\"selected\"") : ("") %> 
                			>POSITIVO</option>
                		<option 
                			value="NEGATIVO" 
                			<%=( "NEGATIVO".equals( toHtmlValue( Capo.getBse_esito() ) ) ) ? ("selected=\"selected\"") : ("") %> 
                			 >NEGATIVO</option>
                	</select>
                </td>
             </tr>
            
			<tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><textarea rows="2" cols="40" name="bse_note"><%=toHtmlValue( Capo.getBse_note() ) %></textarea></td>
            </tr>

          </tbody>
        </table>
         
	</td>
	<% } %>
        
        

    </tr>
            
            <tr>
                <th colspan="2"><strong>Esercenti</strong>                </th>
            </tr>
            <tr>
            	<td colspan="2">
		            <table width="100%" border="0" cellpadding="2" cellspacing="0" align="left">
			            <tr class="containerBody">
			            	<td  class="formLabel">In Regione </td>
					        <td> 
						        Si <input 
							        	type="radio" 
							        	name="destinatario_1_in_regione" 
							        	value="si" 
							        	onclick="selectDestinazione(1)" 
							        	id="inRegione_1" 
							        	<%=(update && !Capo.isDestinatario_1_in_regione()) ? ("") : ("checked=\"checked\"") %> /> 
							        	
						        No <input 
						        		type="radio" 
						        		name="destinatario_1_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(1)" 
						        		id="outRegione_1"
						        		<%=(update && !Capo.isDestinatario_1_in_regione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					        <td> 
						        Si <input 
						        		type="radio" 
						        		name="destinatario_2_in_regione" 
						        		value="si" 
						        		onclick="selectDestinazione(2)" 
						        		id="inRegione_2" 
						        		<%=(update && !Capo.isDestinatario_2_in_regione()) ? ("") : ("checked=\"checked\"") %> /> 
						        		
						        No <input 
						        		type="radio" 
						        		name="destinatario_2_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(2)" 
						        		id="outRegione_2"
						        		<%=(update && !Capo.isDestinatario_2_in_regione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					    </tr>
					    <tr class="containerBody">
			            	<td  class="formLabel">Destinatario delle Carni </td>
					        <td>
						        <div 
						        	style="<%=(update && !Capo.isDestinatario_1_in_regione()) ? ("display:none") : ("") %>" 
						        	id="imprese_1">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 1, 'impresa' );" onclick="selectDestinazione(1);gestisciObbligatorietaVisitaPostMortem();" >[Seleziona Impresa] </a><br/>
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 3, 'stab');" onclick="selectDestinazione(1);gestisciObbligatorietaVisitaPostMortem();" >[Seleziona Stabilimento]  </a><br/>
						        	<a href="javascript:mostraTextareaEsercente('esercenteNoGisa1');" onclick="selectDestinazioneFromLinkTextarea(1);gestisciObbligatorietaVisitaPostMortem();" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						        	<a href="javascript:impostaDestinatarioMacelloCorrente(1);" onclick="gestisciObbligatorietaVisitaPostMortem();" >[Macello corrente]</a>
						        	<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa1" name="esercenteNoGisa1" onchange="valorizzaDestinatario(this,'destinatario_1');" ><%=toHtmlValue( Capo.getDestinatario_1_nome() ) %></textarea>
						        </div>
						        <div  
						        	style="<%=(update && !Capo.isDestinatario_1_in_regione()) ? ("") : ("display:none") %>" 
						        	id="esercenti_1">
						       		<!--  <a href = "javascript:popLookupSelectorDestinazioneCarni( 'no', 1 );" >[Seleziona Esercente]</a> -->
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione1');" onclick="selectDestinazioneFromLinkTextarea(1);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione1" name="esercenteFuoriRegione1" onchange="valorizzaDestinatario(this,'destinatario_1');" ><%=toHtmlValue( Capo.getDestinatario_1_nome() ) %></textarea>
						        </div>
						        <br/>
						        <div id ="destinatario_label_1" align="center">
						        	<%=(Capo.getDestinatario_1_id() != -1) ? (toHtmlValue( Capo.getDestinatario_1_nome() )) : ("-- Seleziona Destinatario --") %>
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_1_id" 
					        		id="destinatario_1_id" 
					        		value="<%=(Capo.getDestinatario_1_id() != -1) ? (Capo.getDestinatario_1_id()) : ("-1") %>" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_1_nome" 
					        		id="destinatario_1_nome" 
					        		onchange="gestisciObbligatorietaVisitaPostMortem();"
					        		value="<%=toHtmlValue( Capo.getDestinatario_1_nome() ) %>" />
					        		<p id="destinatarioCarni1" align="center" style="display: none;"><font color="red" >*</font></p>
							</td>
						    <td>
						    	<div style="" id="imprese_2">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 2, 'impresa' );" onclick="selectDestinazione(2);" >[Seleziona Impresa]</a><br/>
						      		<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 4, 'stab');" onclick="selectDestinazione(2);" >[Seleziona Stabilimento]</a><br/>
						      		<a href="javascript:mostraTextareaEsercente('esercenteNoGisa2');" onclick="selectDestinazioneFromLinkTextarea(2);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						      		<a href="javascript:impostaDestinatarioMacelloCorrente(2);" onclick="gestisciObbligatorietaVisitaPostMortem();" >[Macello corrente]</a>
						      		<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa2" name="esercenteNoGisa2" onchange="valorizzaDestinatario(this,'destinatario_2');" ><%=toHtmlValue( Capo.getDestinatario_2_nome() ) %></textarea>

						        </div>
						        <div  style="display:none" id="esercenti_2">
						       		<!--  <a href = "javascript:popLookupSelectorDestinazioneCarni( 'no', 2 );" >[Seleziona Esercente]</a> -->
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione2');" onclick="selectDestinazioneFromLinkTextarea(2);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione2" name="esercenteFuoriRegione2" onchange="valorizzaDestinatario(this,'destinatario_2');" ><%=toHtmlValue( Capo.getDestinatario_2_nome() ) %></textarea>
						        </div>
						        <br/>
					        	<div id ="destinatario_label_2" align="center">
						        	<%=(Capo.getDestinatario_2_id() != -1) ? (toHtmlValue( Capo.getDestinatario_2_nome() )) : ("-- Seleziona Destinatario --") %>	
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_2_id" 
					        		id="destinatario_2_id" 
					        		value="<%=(Capo.getDestinatario_2_id() != -1) ? (Capo.getDestinatario_2_id()) : ("-1") %>" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_2_nome" 
					        		id="destinatario_2_nome" 
					        		onchange="gestisciObbligatorietaVisitaPostMortem();"
					        		value="<%=toHtmlValue( Capo.getDestinatario_2_nome() ) %>" />
					        		<p id="destinatarioCarni2" align="center" style="display: none;"><font color="red" >*</font></p>
						    </td>
						</tr>
					</table>
		  		</td>
			 </tr>
			 
			 
			 
			 
			 <!-- AGGIUNA DI ALTRI 2 -->
			 
			 
			 <tr>
            	<td colspan="2">
		            <table width="100%" border="0" cellpadding="2" cellspacing="0" align="left">
			            <tr class="containerBody">
			            	<td  class="formLabel">In Regione </td>
					        <td> 
						        Si <input 
							        	type="radio" 
							        	name="destinatario_3_in_regione" 
							        	value="si" 
							        	onclick="selectDestinazione(3)" 
							        	id="inRegione_3" 
							        	<%=(update && !Capo.isDestinatario_3_in_regione()) ? ("") : ("checked=\"checked\"") %> /> 
							        	
						        No <input 
						        		type="radio" 
						        		name="destinatario_3_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(3)" 
						        		id="outRegione_3"
						        		<%=(update && !Capo.isDestinatario_3_in_regione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					        <td> 
						        Si <input 
						        		type="radio" 
						        		name="destinatario_4_in_regione" 
						        		value="si" 
						        		onclick="selectDestinazione(4)" 
						        		id="inRegione_4" 
						        		<%=(update && !Capo.isDestinatario_4_in_regione()) ? ("") : ("checked=\"checked\"") %> /> 
						        		
						        No <input 
						        		type="radio" 
						        		name="destinatario_4_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(4)" 
						        		id="outRegione_4"
						        		<%=(update && !Capo.isDestinatario_4_in_regione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					    </tr>
					    <tr class="containerBody">
			            	<td  class="formLabel">Destinatario delle Carni </td>
					        <td>
						        <div 
						        	style="<%=(update && !Capo.isDestinatario_3_in_regione()) ? ("display:none") : ("") %>" 
						        	id="imprese_3">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 5, 'impresa' );" onclick="selectDestinazione(3);gestisciObbligatorietaVisitaPostMortem();" >[Seleziona Impresa] </a><br/>
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 5, 'stab');" onclick="selectDestinazione(3);gestisciObbligatorietaVisitaPostMortem();" >[Seleziona Stabilimento]  </a><br/>
						        	<a href="javascript:mostraTextareaEsercente('esercenteNoGisa3');" onclick="selectDestinazioneFromLinkTextarea(3);gestisciObbligatorietaVisitaPostMortem();" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						        	<a href="javascript:impostaDestinatarioMacelloCorrente(3);" onclick="gestisciObbligatorietaVisitaPostMortem();" >[Macello corrente]</a>
						        	<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa3" name="esercenteNoGisa3" onchange="valorizzaDestinatario(this,'destinatario_3');" ><%=toHtmlValue( Capo.getDestinatario_3_nome() ) %></textarea>
						        </div>
						        <div  
						        	style="<%=(update && !Capo.isDestinatario_3_in_regione()) ? ("") : ("display:none") %>" 
						        	id="esercenti_3">
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione3');" onclick="selectDestinazioneFromLinkTextarea(3);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione3" name="esercenteFuoriRegione3" onchange="valorizzaDestinatario(this,'destinatario_3');" ><%=toHtmlValue( Capo.getDestinatario_3_nome() ) %></textarea>
						        </div>
						        <br/>
						        <div id ="destinatario_label_3" align="center">
						        	<%=(Capo.getDestinatario_3_id() != -1) ? (toHtmlValue( Capo.getDestinatario_3_nome() )) : ("-- Seleziona Destinatario --") %>
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_3_id" 
					        		id="destinatario_3_id" 
					        		value="<%=(Capo.getDestinatario_3_id() != -1) ? (Capo.getDestinatario_3_id()) : ("-1") %>" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_3_nome" 
					        		id="destinatario_3_nome" 
					        		onchange="gestisciObbligatorietaVisitaPostMortem();"
					        		value="<%=toHtmlValue( Capo.getDestinatario_3_nome() ) %>" />
					        		<p id="destinatarioCarni3" align="center" style="display: none;"><font color="red" >*</font></p>
							</td>
							
							
						    <td>
						    	<div style="" id="imprese_4">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 6, 'impresa' );" onclick="selectDestinazione(4);" >[Seleziona Impresa]</a><br/>
						      		<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 6, 'stab');" onclick="selectDestinazione(4);" >[Seleziona Stabilimento]</a><br/>
						      		<a href="javascript:mostraTextareaEsercente('esercenteNoGisa4');" onclick="selectDestinazioneFromLinkTextarea(4);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						      		<a href="javascript:impostaDestinatarioMacelloCorrente(4);" onclick="gestisciObbligatorietaVisitaPostMortem();" >[Macello corrente]</a>
						      		<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa4" name="esercenteNoGisa4" onchange="valorizzaDestinatario(this,'destinatario_4');" ><%=toHtmlValue( Capo.getDestinatario_4_nome() ) %></textarea>

						        </div>
						        <div  style="display:none" id="esercenti_4">
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione4');" onclick="selectDestinazioneFromLinkTextarea(4);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione4" name="esercenteFuoriRegione4" onchange="valorizzaDestinatario(this,'destinatario_4');" ><%=toHtmlValue( Capo.getDestinatario_4_nome() ) %></textarea>
						        </div>
						        <br/>
					        	<div id ="destinatario_label_4" align="center">
						        	<%=(Capo.getDestinatario_4_id() != -1) ? (toHtmlValue( Capo.getDestinatario_4_nome() )) : ("-- Seleziona Destinatario --") %>	
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_4_id" 
					        		id="destinatario_4_id" 
					        		value="<%=(Capo.getDestinatario_4_id() != -1) ? (Capo.getDestinatario_4_id()) : ("-1") %>" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_4_nome" 
					        		id="destinatario_4_nome" 
					        		onchange="gestisciObbligatorietaVisitaPostMortem();"
					        		value="<%=toHtmlValue( Capo.getDestinatario_4_nome() ) %>" />
					        		<p id="destinatarioCarni4" align="center" style="display: none;"><font color="red" >*</font></p>
						    </td>
						</tr>
					</table>
		  		</td>
			 </tr>
			 
            
                </tbody>
               </table>
         
			  </td>
          
    </tr>
    
    <tr 
    	id="lcso" 
    	style="<%=( Capo.getVpm_esito() == 3 || Capo.getVpm_esito() == 4 ) ? ( "" ) : ( "display:none" ) %>" >
    	<td>
    		<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
    		<tr>
              <th colspan="2"><strong>Organi</strong></th>
            </tr>
<!--            <tr class="containerBody">-->
<!--              <td class="formLabel" >Data</td>-->
<!---->
<!--              <td><zeroio:dateSelect field="lcso_data" form="main" showTimeZone="false" /> </td>-->
<!--            </tr>-->
     
     <%
     	int numero_organi = 0;
     	int indice_globale= 0;
     	ArrayList<Integer> listaIdOrgani = new ArrayList<Integer>();
     	ArrayList<Organi> listaOrgani = null;
     	for(int key : (Set<Integer>)OrganiListNew.keySet()){
     		listaIdOrgani.add(key);
     	}
     	for( int index = 1; index == 1 || index <= listaIdOrgani.size(); index++ )
     	{
     		//out.println("Indice di Organi = "+ index);
     		//out.println("Numero di Organi = "+ OrganiList.size());
     		indice_globale=index;
     		if(listaIdOrgani.size() > 0){
     			listaOrgani = (ArrayList<Organi>)OrganiListNew.get( listaIdOrgani.get(index-1) );
     		}
     		Organi organo = (index <= listaIdOrgani.size()) ? (Organi)listaOrgani.get(0) : (new Organi());
     		PatologieOrgani.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
     %>
     
    
		    <tr style="background-color: #EDEDED" class="containerBody">
               <td colspan="2">&nbsp;<input type="hidden" name="lcso_id_<%=index %>" value="<%=organo.getId() %>" /> </td>
            </tr>
            
     		<tr id="organo" class="containerBody">
              <td class="formLabel" >Organo</td>
              <td>
              		<%	String id_organo = "document.getElementById('lcso_organo_"+index+"').value" ; 
              		
              			Organi.setJsEvent("onChange=\"javascript:vpm_resetta_lookup_patologia_organo("+ index +"," + id_organo +", -1);\"");
              			Organi.setIdName("lcso_organo_" + index);
              			
              		%>
              		<%=	Organi.getHtmlSelect("lcso_organo_" + index, organo.getLcso_organo())%>
              </td>
            </tr>

            <tr id="patologie" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica</td>
              <td>
              <%
	      			lookup_lesione_milza.setSelectStyle("display:none");
              		lookup_lesione_milza.setMultiple(true);
              		lookup_lesione_milza.setSelectSize(5);
              		lookup_lesione_milza.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
              		
	       			lookup_lesione_cuore.setSelectStyle("display:none");
	       			lookup_lesione_cuore.setMultiple(true);
	       			lookup_lesione_cuore.setSelectSize(5);
	       			lookup_lesione_cuore.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	       			
	        		lookup_lesione_polmoni.setSelectStyle("display:none");
	        		lookup_lesione_polmoni.setMultiple(true);
	        		lookup_lesione_polmoni.setSelectSize(5);
	        		lookup_lesione_polmoni.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_fegato.setSelectStyle("display:none");
	        		lookup_lesione_fegato.setMultiple(true);
	        		lookup_lesione_fegato.setSelectSize(5);
	        		lookup_lesione_fegato.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_rene.setSelectStyle("display:none");
	        		lookup_lesione_rene.setMultiple(true);
	        		lookup_lesione_rene.setSelectSize(5);
	        		lookup_lesione_rene.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_mammella.setSelectStyle("display:none");
	        		lookup_lesione_mammella.setMultiple(true);
	        		lookup_lesione_mammella.setSelectSize(5);
	        		lookup_lesione_mammella.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_apparato_genitale.setSelectStyle("display:none");
	        		lookup_lesione_apparato_genitale.setMultiple(true);
	        		lookup_lesione_apparato_genitale.setSelectSize(5);
	        		lookup_lesione_apparato_genitale.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_stomaco.setSelectStyle("display:none");
	        		lookup_lesione_stomaco.setMultiple(true);
	        		lookup_lesione_stomaco.setSelectSize(5);
	        		lookup_lesione_stomaco.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_intestino.setSelectStyle("display:none");
	        		lookup_lesione_intestino.setMultiple(true);
	        		lookup_lesione_intestino.setSelectSize(5);
	        		lookup_lesione_intestino.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_osteomuscolari.setSelectStyle("display:none");
	        		lookup_lesione_osteomuscolari.setMultiple(true);
	        		lookup_lesione_osteomuscolari.setSelectSize(5);
	        		lookup_lesione_osteomuscolari.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_generici.setSelectStyle("display:none");
	        		lookup_lesione_generici.setMultiple(true);
	        		lookup_lesione_generici.setSelectSize(5);
	        		lookup_lesione_generici.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		lookup_lesione_altro.setSelectStyle("display:none");
	        		lookup_lesione_altro.setMultiple(true);
	        		lookup_lesione_altro.setSelectSize(5);
	        		lookup_lesione_altro.setJsEvent("onChange=\"javascript:mostraLcsoPatologiaAltro(this," + index + ");\"");
	        		
	        		
	        		//lookup_lesione_milza.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_cuore.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_polmoni.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_fegato.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_rene.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_mammella.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_apparato_genitale.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_stomaco.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_intestino.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		//lookup_lesione_osteomuscolari.setJsEvent("onChange=\"javascript:displayStadio2(" + index + ");\"");
	        		
	        		
	        		//out.println("Valore ID lesione : "  + organo.getLcso_patologia() ) ;
	        		LookupList multipleSelect = new LookupList();
	        		if(listaIdOrgani.size() > 0){
		        		for ( Organi o : listaOrgani ){
		        			multipleSelect.addItem(o.getLcso_patologia(), "" );
		        		}
	        		}
	        		LookupList multipleSelectDefault = new LookupList();
	        		multipleSelectDefault.addItem(-1,"");
				               
              %>
                  <%=lookup_lesione_milza.getHtmlSelect("lesione_milza_" + index, organo.getLcso_organo() == 1 ? multipleSelect : multipleSelectDefault )%>
                  <%=lookup_lesione_cuore.getHtmlSelect("lesione_cuore_" + index, organo.getLcso_organo() == 2 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_polmoni.getHtmlSelect("lesione_polmoni_" + index, organo.getLcso_organo() == 3 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_fegato.getHtmlSelect("lesione_fegato_" + index, organo.getLcso_organo() == 4 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_rene.getHtmlSelect("lesione_rene_" + index, organo.getLcso_organo() == 5 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_mammella.getHtmlSelect("lesione_mammella_" + index, organo.getLcso_organo() == 6 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_apparato_genitale.getHtmlSelect("lesione_apparato_genitale_" + index, organo.getLcso_organo() == 7 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_stomaco.getHtmlSelect("lesione_stomaco_" + index, organo.getLcso_organo() == 8 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_intestino.getHtmlSelect("lesione_intestino_" + index, organo.getLcso_organo() == 9 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_osteomuscolari.getHtmlSelect("lesione_osteomuscolari_" + index, organo.getLcso_organo() == 10 ? multipleSelect : multipleSelectDefault)%>
	              
	              <%=lookup_lesione_generici.getHtmlSelect("lesione_generici_" + index, organo.getLcso_organo() >= 13 ? multipleSelect : multipleSelectDefault)%>
	              <%=lookup_lesione_altro.getHtmlSelect("lesione_altro_" + index, organo.getLcso_organo() == 11 || organo.getLcso_organo() == 12 ? multipleSelect : multipleSelectDefault)%>
	              <input style="display: <%= organo.getLcso_patologia() == 5 ? "" : "none" %>;" type="text" id="lcso_patologiaaltro_<%=index %>" name="lcso_patologiaaltro_<%=index %>" 
	              value="<%= organo.getLcso_patologia_altro() != null ? organo.getLcso_patologia_altro() : "" %>" />
	              
              </td>
            </tr>
           
           <script>
	    		vpm_seleziona_lookup_patologia_organo(<%=index%>,<%=organo.getLcso_organo()%>,<%=organo.getLcso_patologia()%>);
	   	   </script> 	
<!-- **************************************************** INSERIMENTO DI ALBERTO ***********************************************     -->
<%-- 	
	
            <tr id="patologie" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica</td>
              <td>
	              <%=PatologieOrgani.getHtmlSelect("lcso_patologia_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>	

			<tr id="lesione_milza" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (milza)</td>
              <td>
	              <%=lookup_lesione_milza.getHtmlSelect("lesione_milza_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>


          
			<tr id="lesione_cuore" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (cuore)</td>
              <td>
	              <%=lookup_lesione_cuore.getHtmlSelect("lesione_cuore_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            			
            <tr id="lesione_polmoni" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (polmoni)</td>
              <td>
	              <%=lookup_lesione_polmoni.getHtmlSelect("lesione_polmoni_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            
			<tr id="lesione_fegato" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (fegato)</td>
              <td>
	              <%=lookup_lesione_fegato.getHtmlSelect("lesione_fegato_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>

            <tr id="lesione_rene" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (rene)</td>
              <td>
	              <%=lookup_lesione_rene.getHtmlSelect("lesione_rene_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            
			<tr id="lesione_mammella" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (mammella)</td>
              <td>
	              <%=lookup_lesione_mammella.getHtmlSelect("lesione_mammella_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            
            <tr id="lesione_apparato_genitale" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (genitale)</td>
              <td>
	              <%=lookup_lesione_apparato_genitale.getHtmlSelect("lesione_apparato_genitale_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            
			<tr id="lesione_stomaco" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (stomaco)</td>
              <td>
	              <%=lookup_lesione_stomaco.getHtmlSelect("lesione_stomaco_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            
            <tr id="lesione_intestino" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (intestino)</td>
              <td>
	              <%=lookup_lesione_intestino.getHtmlSelect("lesione_intestino_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>
            
			<tr id="lesione_osteomuscolari" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica (osteomuscolari)</td>
              <td>
	              <%=lookup_lesione_osteomuscolari.getHtmlSelect("lesione_osteomuscolari_" + index, organo.getLcso_patologia())%>
	          </td>
            </tr>        
--%>
<!-- *********************************************** FINE INSERIMENTO DI ALBERTO ***********************************************     -->	


 <%-- ELIMINA stadio 	
        <tr 
            	id="stadio_<%=index %>" 
            	class="containerBody" 
            	style="<%=(organo.getLcso_patologia() == 6) ? ("") : ("display:none") %>" >
	          <td class="formLabel" >Stadio</td>
    	      <td>
    	      	<%=Stadi.getHtmlSelect("lcso_stadio_" + index, organo.getLcso_stadio())%>
    	      </td>
            </tr>
            --%>   
         
       <%
       		numero_organi++;
     	}
     	PatologieOrgani.setJsEvent("onChange=\"javascript:displayStadio(--placeholder--);\"");
       %>
            
     	   
            
     			<input type="hidden" id="elementi" name="elementi" value="<%=numero_organi %>">
   				<input type="hidden" id="size" name="size" value="<%=numero_organi %>">
   				
   			<tr id="nbsp" style = "visibility: collapse; background-color: #EDEDED" class="containerBody">
               <td  name="nbsptr1_" colspan="2">&nbsp;<input type="hidden" name="lcso_id_" value="-1" /></td>
            </tr>
   			<tr id="ww" style = "visibility: collapse" class="containerBody">
              <td class="formLabel" colspan="1">Organo</td>
              <td>
               <%--Organi.setJsEvent("onChange=alert(this.value"+ out.print(indice_globale)  + ")");--%>
              <%=Organi.getHtmlSelect( "lcso_organo_", "-1" )%>
              
              </td>
            </tr>    
   			
   			<tr id="row" style = "visibility: collapse" class="containerBody">
              <td class="formLabel" >Lesione Anatomopatologica</td>
              <td>
              <%
					lookup_lesione_milza.setSelectStyle("display:none");
              		lookup_lesione_milza.setMultiple(true);
        			lookup_lesione_milza.setSelectSize(5);
        			
             		lookup_lesione_cuore.setSelectStyle("display:none");
             		lookup_lesione_cuore.setMultiple(true);
             		lookup_lesione_cuore.setSelectSize(5);
        			
              		lookup_lesione_polmoni.setSelectStyle("display:none");
              		lookup_lesione_polmoni.setMultiple(true);
              		lookup_lesione_polmoni.setSelectSize(5);
        			
              		lookup_lesione_fegato.setSelectStyle("display:none");
              		lookup_lesione_fegato.setMultiple(true);
              		lookup_lesione_fegato.setSelectSize(5);
        			
              		lookup_lesione_rene.setSelectStyle("display:none");
              		lookup_lesione_rene.setMultiple(true);
              		lookup_lesione_rene.setSelectSize(5);
        			
              		lookup_lesione_mammella.setSelectStyle("display:none");
              		lookup_lesione_mammella.setMultiple(true);
              		lookup_lesione_mammella.setSelectSize(5);
        			
              		lookup_lesione_apparato_genitale.setSelectStyle("display:none");
              		lookup_lesione_apparato_genitale.setMultiple(true);
              		lookup_lesione_apparato_genitale.setSelectSize(5);
        			
              		lookup_lesione_stomaco.setSelectStyle("display:none");
              		lookup_lesione_stomaco.setMultiple(true);
              		lookup_lesione_stomaco.setSelectSize(5);
        			
              		lookup_lesione_intestino.setSelectStyle("display:none");
              		lookup_lesione_intestino.setMultiple(true);
              		lookup_lesione_intestino.setSelectSize(5);
        			
              		lookup_lesione_osteomuscolari.setSelectStyle("display:none");
              		lookup_lesione_osteomuscolari.setMultiple(true);
              		lookup_lesione_osteomuscolari.setSelectSize(5);

              		lookup_lesione_generici.setSelectStyle("display:none");
              		lookup_lesione_generici.setMultiple(true);
              		lookup_lesione_generici.setSelectSize(5);
        			
              		lookup_lesione_altro.setSelectStyle("display:none");
              		lookup_lesione_altro.setMultiple(true);
              		lookup_lesione_altro.setSelectSize(5);
              		//lookup_lesione_altro.setJsEvent("onchange=\"javascript:mostraLcsoPatologiaAltro();\"");
              		
              %>
                  <%=lookup_lesione_milza.getHtmlSelect("lesione_milza_","-1")%>
	              <%=lookup_lesione_cuore.getHtmlSelect("lesione_cuore_" ,"-1")%>
	              <%=lookup_lesione_polmoni.getHtmlSelect("lesione_polmoni_" ,"-1")%>
	              <%=lookup_lesione_fegato.getHtmlSelect("lesione_fegato_","-1")%>
	              <%=lookup_lesione_rene.getHtmlSelect("lesione_rene_","-1")%>
	              <%=lookup_lesione_mammella.getHtmlSelect("lesione_mammella_","-1")%>
	              <%=lookup_lesione_apparato_genitale.getHtmlSelect("lesione_apparato_genitale_","-1")%>
	              <%=lookup_lesione_stomaco.getHtmlSelect("lesione_stomaco_","-1")%>
	              <%=lookup_lesione_intestino.getHtmlSelect("lesione_intestino_","-1")%>
	              <%=lookup_lesione_osteomuscolari.getHtmlSelect("lesione_osteomuscolari_","-1")%>
	              
	              <%=lookup_lesione_generici.getHtmlSelect("lesione_generici_","-1")%>
	              <%=lookup_lesione_altro.getHtmlSelect("lesione_altro_","-1")%>
	              <input style="display: none;" type="text" id="lcso_patologiaaltro_" name="lcso_patologiaaltro_" class="lcso_patologia_altro_class" value="" />
	             
	              
	              
              </td>
            </tr>

		<%-- 
			<tr id="tr" style = "visibility: collapse" class="containerBody" >
	          <td class="formLabel" colspan="1">Stadio</td>
    	      <td><%=Stadi.getHtmlSelect( "lcso_stadio_", "-1" )%></td>
            </tr>
        --%>         
                 
       
          	<tr style="display: none;" id="lcsobutton">
	    	  <input type="button" onClick="javascript:provaOrgani();" id="aggiungialtrobutton" value="Aggiungi Altro Organo" >
	      	</tr>
</table>
</td>
</tr>


<tr id="lcpr" <%=( Capo.getVpm_esito() == 4 ) ? ("") : ("style=\"display: none\"") %> >
    	<td>
    		<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
    		
            <tr>
              <th colspan="2"><strong>Libero Consumo Previo Risanamento</strong></th>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" >Data prevista di liberalizzazione</td>
              <td>
              	<zeroio:dateSelect 
              		field="lcpr_data_prevista_liber" 
              		form="main" 
              		showTimeZone="false" 
              		timestamp="<%=Capo.getLcpr_data_prevista_liber() %>" />
              </td>
            </tr>     
             
            <tr class="containerBody">
              <td class="formLabel" >Data effettiva di liberalizzazione</td>
              <td>
              	<zeroio:dateSelect 
              		field="lcpr_data_effettiva_liber" 
              		form="main" 
              		showTimeZone="false" 
              		timestamp="<%=Capo.getLcpr_data_effettiva_liber() %>" />
              </td>
            </tr>
   </table>
   </td>
</tr>
</tbody>
</table>
</td>
</tr>
	
</tbody></table>	
</div>


<div id="tableID1_Div"></div>
<table id="tableID1" style="display:none;clear:left;">
<tr><td> <%@include file="include_campioni_add.jsp" %> </td></tr>
<tr><td> <jsp:include page="include_tamponi_add1.jsp"/> </td></tr>
</table>

    		



	
<div id="tabs-4">	
<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="55%"><table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>

        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
          
           <tr>
              <th colspan="2"><strong>Animale</strong></th>
            </tr>
            

            <tr>
                <td style="background-color:yellow;text-align: right" nowrap="nowrap">Matricola</td>
                <td style="background-color:yellow;">
                	<%=toHtmlValue( ((Capo)Capo).getCd_matricola() ) %>
                </td>
            </tr>
            
           
            <tr class="containerBody" >
                <td class="formLabel" nowrap="nowrap">Specie<br></td>
                <td>
                	
              		<%=Specie.getSelectedValue( ((Capo)Capo).getCd_specie())%>
				</td>
            </tr>
            
            <%if(((Capo)Capo).getCd_categoria_bovina()>0){ %>
            <tr class="containerBody" id="categoria_bovina">
              <td class="formLabel" nowrap="nowrap">Categoria</td>
              	<td>
              		<%=CategorieBovine.getSelectedValue( ((Capo)Capo).getCd_categoria_bovina() )%>
				</td>
            </tr>
            <%} %>
            
             <%if(((Capo)Capo).getCd_categoria_bufalina()>0){ %>
            <tr class="containerBody" id="categoria_bufalina">
              <td class="formLabel" nowrap="nowrap">Categoria</td>
              	<td>
              		<%=CategorieBufaline.getSelectedValue( ((Capo)Capo).getCd_categoria_bufalina() )%>
				</td>
            </tr>
            <%} %>
            
			<tr class="containerBody" id="razza_bovina">
              <td class="formLabel" nowrap="nowrap">Razza</td>
              	<td>
              		
              		<%=Razze.getSelectedValue( ((Capo)Capo).getCd_id_razza())%>
              		
				</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Sesso</td>
              <td>
                 <select disabled="disabled">
                   <option value="true" <%=(((Capo)Capo).isCd_maschio()) ? ("selected=\"selected\"") : ("") %> >maschio</option>
                   <option value="false" <%=(((Capo)Capo).isCd_maschio()) ? ("") : ("selected=\"selected\"") %> >femmina</option>
                 </select>
              </td>
            </tr>
            <tr>
              <th colspan="2"><strong>Morte antecedente macellazione</strong></th>
            </tr>
            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
<%--              	<zeroio:dateSelect field="mavam_data" showTimeZone="false" form="main" timestamp="<%=Capo.getMavam_data() %>" />   --%>
              		<input readonly type="text" id="mavam_data" name="mavam_data" onfocus="riportaDataArrivoMacello(this);gestisciObbligatorietaMorteAnteMacellazione();" size="10" value="<%=DateUtils.timestamp2string(Capo.getMavam_data())%>" />&nbsp;  
			        <font color="red" id="dataMorteAnteMacellazione" style="display: none;">*</font>
			        <a href="#" onClick="cal19.select(document.forms[0].mavam_data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].mavam_data);"><img src="images/delete.gif" align="absmiddle"/></a>
              </td>
            	
            </tr>
            
             <tr class="containerBody">
                <td class="formLabel">Luogo di verifica</td>
                
                <td>
                	<%
                		LuoghiVerifica.setJsEvent("onChange=\"javascript:displayLuogoVerifica();gestisciObbligatorietaMorteAnteMacellazione();\"");
                	%>                               
                	<%=LuoghiVerifica.getHtmlSelect( "mavam_luogo", Capo.getMavam_luogo() ) %>
                	
                	<input 
                		type="text" 
                		size="30" 
                		id="luogo" 
                	
                		name="mavam_descrizione_luogo_verifica"
                		value="<%=toHtmlValue( Capo.getMavam_descrizione_luogo_verifica() ) %>"
                		<%=( Capo.getMavam_luogo() == 3 ) ? ("") : ("style=\"display: none\"") %> />
                	<font color="red" id="luogoMorteAnteMacellazione" style="display: none;">*</font>
                </td>
                                
            </tr>
                 
            <tr class="containerBody">
                <td class="formLabel">Causa</td>
                <td>
                	<textarea rows="2" cols="40" id="mavam_motivo" name="mavam_motivo" onchange="gestisciObbligatorietaMorteAnteMacellazione();"><%=toHtmlValue( Capo.getMavam_motivo() ) %></textarea>
                	<font color="red" id="causaMorteAnteMacellazione" style="display: none;">*</font>
                </td>
				
           </tr>
           
           <tr class="containerBody">
                <td class="formLabel">Impianto di termodistruzione</td>
                <td>
                	<textarea 
                		rows="2" 
                		cols="40" 
                		name="mavam_impianto_termodistruzione"
                		><%=toHtmlValue( ((Capo)Capo).getMavam_impianto_termodistruzione() ) %></textarea>
                </td>
           </tr>
           
             <tr class="containerBody">
                <td class="formLabel">Destinazione della carcassa</td>
                <td>
                	<textarea 
                		rows="2" 
                		cols="40" 
                		name="mvam_destinazione_carcassa"
                		><%=toHtmlValue( Capo.getMvam_destinazione_carcassa() ) %></textarea>
                </td>
           </tr>
           
           
           <tr class="containerBody">
            	<td class="formLabel">Comunicazione a</td>
            	<td>
            		<input 
            			type="checkbox" 
            			id="mavam_to_asl_origine"
            			name="mavam_to_asl_origine" 
            			<%=(((Capo)Capo).isMavam_to_asl_origine()) ? ("checked=\"checked\"") : ("") %> 
            			/> ASL origine <br/>
            		<input 
            			type="checkbox" 
            			id="mavam_to_proprietario_animale"
            			name="mavam_to_proprietario_animale" 
            			<%=(((Capo)Capo).isMavam_to_proprietario_animale()) ? ("checked=\"checked\"") : ("") %> 
            			/> Proprietario animale <br/>
            		<input 
            			type="checkbox" 
            			id="mavam_to_azienda_origine"
            			name="mavam_to_azienda_origine" 
            			<%=(((Capo)Capo).isMavam_to_azienda_origine()) ? ("checked=\"checked\"") : ("") %>
            			/> Azienda di origine <br/>
            		<input 
            			type="checkbox" 
            			id="mavam_to_proprietario_macello"
            			name="mavam_to_proprietario_macello" 
            			<%=(Capo.isMavam_to_proprietario_macello()) ? ("checked=\"checked\"") : ("") %>
            			/> Proprietario macello <br/>
            		<input 
            			type="checkbox" 
            			id="mavam_to_pif"
            			name="mavam_to_pif" 
            			<%=(((Capo)Capo).isMavam_to_pif()) ? ("checked=\"checked\"") : ("") %>
            			/> P.I.F. <br/>	
            		<input 
            			type="checkbox" 
            			id="mavam_to_uvac"
            			name="mavam_to_uvac" 
            			<%=(((Capo)Capo).isMavam_to_uvac()) ? ("checked=\"checked\"") : ("") %>
            			/> U.V.A.C. <br/>	
            		<input 
            			type="checkbox" 
            			id="mavam_to_regione"
            			name="mavam_to_regione" 
            			<%=(((Capo)Capo).isMavam_to_regione()) ? ("checked=\"checked\"") : ("") %>
            			/> Regione <br/>
            		<input 
            			type="checkbox"
            			id="mavam_to_altro" 
            			name="mavam_to_altro" 
            			onclick="visualizzaTextareaMavamToAltro();"
            			<%=(((Capo)Capo).isMavam_to_altro()) ? ("checked=\"checked\"") : ("") %>
            			/> Altro <br/>
            		<textarea id="mavam_to_altro_testo" name="mavam_to_altro_testo" rows="2" cols="40" style="display: <%=(((Capo)Capo).isMavam_to_altro()) ? "''" : "none" %>;" ><%=toHtmlValue( Capo.getMavam_to_altro_testo() ) %></textarea>
            			
            	</td>
            </tr>
           
           <tr class="containerBody">
                <td class="formLabel">Note</td>
                <td>
                	<textarea rows="2" cols="40" id="mavam_note" name="mavam_note"><%=toHtmlValue( Capo.getMavam_note() ) %></textarea>
                </td>
				
           </tr>
           
        </tbody>
     </table>
          
			  
			  </td>
        
        
    </tr></tbody></table>&nbsp;</td>


</tr></tbody></table>
	</div>
     
<div id="tabs-7">
		<!-- Commento update  -->
		
		
<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody>
    <tr>
        <td valign="top" width="100%">
        <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>
              <th colspan="2"><strong>Animale</strong></th>
            </tr>
            

            <tr>
                <td style="background-color:yellow;text-align: right" nowrap="nowrap">Matricola</td>
                <td style="background-color:yellow;">
                	<%=toHtmlValue( ((Capo)Capo).getCd_matricola() ) %>
                </td>
            </tr>
            
           
            <tr class="containerBody" >
                <td class="formLabel" nowrap="nowrap">Specie<br></td>
                <td>
                	
              		<%=Specie.getSelectedValue( ((Capo)Capo).getCd_specie())%>
				</td>
            </tr>
            
            <%if(((Capo)Capo).getCd_categoria_bovina()>0){ %>
            <tr class="containerBody" id="categoria_bovina">
              <td class="formLabel" nowrap="nowrap">Categoria</td>
              	<td>
              		<%=CategorieBovine.getSelectedValue( ((Capo)Capo).getCd_categoria_bovina() )%>
				</td>
            </tr>
            <%} %>
            
             <%if(((Capo)Capo).getCd_categoria_bufalina()>0){ %>
            <tr class="containerBody" id="categoria_bufalina">
              <td class="formLabel" nowrap="nowrap">Categoria</td>
              	<td>
              		<%=CategorieBufaline.getSelectedValue( ((Capo)Capo).getCd_categoria_bufalina() )%>
				</td>
            </tr>
            <%} %>
            
			<tr class="containerBody" id="razza_bovina">
              <td class="formLabel" nowrap="nowrap">Razza</td>
              	<td>
              		
              		<%=Razze.getSelectedValue( ((Capo)Capo).getCd_id_razza())%>
              		
				</td>
            </tr>
            
            <tr class="containerBody">
              <td class="formLabel" nowrap="nowrap">Sesso</td>
              <td>
                 <select disabled="disabled">
                   <option value="true" <%=(((Capo)Capo).isCd_maschio()) ? ("selected=\"selected\"") : ("") %> >maschio</option>
                   <option value="false" <%=(((Capo)Capo).isCd_maschio()) ? ("") : ("selected=\"selected\"") %> >femmina</option>
                 </select>
              </td>
            </tr>
             <tr>
                <th colspan="2"><strong>Comunicazioni Esterne</strong>                </th>
            </tr>
            <tr class="containerBody">
            	<td class="formLabel">Comunicazione a</td>
            	<td>
            		<p id="comunicazioneA" align="center" style="display: none;"><font color="red" >*</font></p>
            		<input 
            			type="checkbox" 
            			id="casl_to_asl_origine"
            			name="casl_to_asl_origine" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			<%=(Capo.isCasl_to_asl_origine()) ? ("checked=\"checked\"") : ("") %> 
            			/> ASL origine <br/>
            		<input 
            			type="checkbox" 
            			id="casl_to_proprietario_animale"
            			name="casl_to_proprietario_animale" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			<%=(Capo.isCasl_to_proprietario_animale()) ? ("checked=\"checked\"") : ("") %> 
            			/> Proprietario animale <br/>
            		<input 
            			type="checkbox" 
            			id="casl_to_azienda_origine"
            			name="casl_to_azienda_origine" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			<%=(Capo.isCasl_to_azienda_origine()) ? ("checked=\"checked\"") : ("") %>
            			/> Azienda di origine <br/>
            		<input 
            			type="checkbox" 
            			id="casl_to_proprietario_macello"
            			name="casl_to_proprietario_macello" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			<%=(Capo.isCasl_to_proprietario_macello()) ? ("checked=\"checked\"") : ("") %>
            			/> Proprietario macello <br/>
            		<input 
            			type="checkbox" 
            			id="casl_to_pif"
            			name="casl_to_pif" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			<%=(Capo.isCasl_to_pif()) ? ("checked=\"checked\"") : ("") %>
            			/> P.I.F. <br/>	
            		<input 
            			type="checkbox" 
            			id="casl_to_uvac"
            			name="casl_to_uvac" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			<%=(Capo.isCasl_to_uvac()) ? ("checked=\"checked\"") : ("") %>
            			/> U.V.A.C. <br/>	
            		<input 
            			type="checkbox" 
            			id="casl_to_regione"
            			name="casl_to_regione" 
            			onclick="gestisciObbligatorietaComunicazioniEsterne();"
            			<%=(Capo.isCasl_to_regione()) ? ("checked=\"checked\"") : ("") %>
            			/> Regione <br/>
            		<input 
            			type="checkbox"
            			id="casl_to_altro" 
            			name="casl_to_altro" 
            			onclick="visualizzaTextareaCAslToAltro();gestisciObbligatorietaComunicazioniEsterne();"
            			<%=(Capo.isCasl_to_altro()) ? ("checked=\"checked\"") : ("") %>
            			/> Altro <br/>
            		<textarea id="casl_to_altro_testo" name="casl_to_altro_testo" rows="2" cols="40" style="display: <%=(Capo.isCasl_to_altro()) ? "''" : "none" %>;" ><%=toHtmlValue( Capo.getCasl_to_altro_testo() ) %></textarea>
            			
            	</td>
            </tr>
            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
<%--            	<zeroio:dateSelect field="casl_data" form="main" showTimeZone="false" timestamp="<%=Capo.getCasl_data() %>" />  --%>
              		<input readonly type="text" id="casl_data" name="casl_data" onfocus="riportaDataArrivoMacello(this);gestisciObbligatorietaComunicazioniEsterne();" size="10" value="<%=DateUtils.timestamp2string(Capo.getCasl_data())%>" />&nbsp;
              		<font color="red" id="dataComunicazioneA" style="display: none;">*</font>
			        <a href="#" onClick="cal19.select(document.forms[0].casl_data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].casl_data);"><img src="images/delete.gif" align="absmiddle"/></a>
              </td>
            </tr>
   
<%--      	MODIFICATO DA ALBERTO CAMPANILE
             <tr class="containerBody">
                <td class="formLabel">Tipo di non conformità</td>
                <td>
    					<%=MotiviASL.getHtmlSelect( "casl_motivo", Capo.getCasl_motivo() )%>
    			</td>       
            </tr>
--%>
			<td class="formLabel">Tipo di non conformità</td>
                <%
                	MotiviASL.setMultiple(true);
                	MotiviASL.setSelectSize(5);
                	MotiviASL.setJsEvent("onchange=\"javascript:gestisciObbligatorietaComunicazioniEsterne();\"");
					LookupList casl_NC_sel = new LookupList();
					for( Casl_Non_Conformita_Rilevata nc: (ArrayList<Casl_Non_Conformita_Rilevata>)casl_NCRilevate )
					{
						casl_NC_sel.addItem( nc.getId_casl_non_conformita(), "" );//add( Patologie.get( Patologie.getLevelFromId(pr.getId_patologia()) ) );
					}
                %>
			<td>
				<p id="tipoNonConformita" align="center" style="display: none;"><font color="red" >*</font></p>
				<%=MotiviASL.getHtmlSelect( "casl_NC_rilevate", casl_NC_sel ) %>
			</td>
                
           <tr class="containerBody">
	           <td class="formLabel">Descrizione non conformità</td>
	           <td><textarea rows="2" cols="40" name="casl_info_richiesta"><%=toHtmlValue( Capo.getCasl_info_richiesta() ) %></textarea></td>
           </tr>
           
<%--  ELIMINAZIONE CHECKBOX "BLOCCO ANIMALE"   
           <tr class="containerBody">
	           	<td class="formLabel">Blocco animale</td>
	           	<td> 
	           		<input 
		           		type="checkbox" 
		           		name="blocco_animale" 
		           		onchange="toggleDiv('blocco_animale_div')" 
		           		<%=(update && Capo.getSeqa_data() != null) ? ("checked=\"checked\"") : ("") %> />
		       </td>
           </tr>
--%>
         

			<td class="formLabel">Provvedimenti Adottati</td>
                <%
                	look_ProvvedimentiCASL.setMultiple(true);
                	look_ProvvedimentiCASL.setSelectSize(5);
					LookupList provvedimentiSelezionati = new LookupList();
					for( ProvvedimentiCASL pr: (ArrayList<ProvvedimentiCASL>)casl_Provvedimenti_effettuati )
					{
						provvedimentiSelezionati.addItem( pr.getId_provvedimento(), "" );//add( Patologie.get( Patologie.getLevelFromId(pr.getId_patologia()) ) );
					}
					
					look_ProvvedimentiCASL.setJsEvent("onchange=\"javascript:gestisciBloccoAnimale();gestisciObbligatorietaComunicazioniEsterne();\"");
					
                %>
			<td>
				<p id="provvedimentiAdottati" align="center" style="display: none;"><font color="red" >*</font></p>
				<%=look_ProvvedimentiCASL.getHtmlSelect( "casl_provvedimenti_selezionati", provvedimentiSelezionati ) %>
			</td>

		   <tr class="containerBody" id="note_prevvedimento" >
	           <td class="formLabel">Note</td>
	           <td><textarea rows="2" cols="40" name="casl_note_prevvedimento"><%=toHtmlValue( ((Capo)Capo).getCasl_to_altro_testo() ) %></textarea></td>
           </tr>

           
          </tbody>
        </table>
		</td>
	</tr>
    <tr>
        <td valign="top" width="100%">
        <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>
              <th colspan="2"><strong>Ricezione Comunicazioni Esterne</strong></th>
            </tr>

             <tr class="containerBody">
                <td class="formLabel">Data</td>
                <td>
<%--                <zeroio:dateSelect field="rca_data" form="main" showTimeZone="false" timestamp="<%=Capo.getRca_data() %>" /> --%>
                	<input readonly type="text" id="rca_data" name="rca_data" onfocus="riportaDataArrivoMacello(this);" size="10" value="<%=DateUtils.timestamp2string(Capo.getRca_data())%>" />&nbsp;  
			        <a href="#" onClick="cal19.select(document.forms[0].rca_data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].rca_data);"><img src="images/delete.gif" align="absmiddle"/></a>
                </td>
             </tr>
             
             <tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><textarea rows="2" cols="40" name="rca_note"><%=toHtmlValue( Capo.getRca_note() ) %></textarea></td>
             </tr>
          </tbody>
         </table>
        </td>
      </tr>
      
      <tr>
        <td valign="top" width="100%">
	        <div 
	        	<%=(update && Capo.getSeqa_data() != null) ? ("") : ("style=\"display: none;\"") %> 
	        	id="blocco_animale_div">
	                  <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
	          <tbody>
	            <tr>
	              <th colspan="2"><strong>Blocco Animale</strong></th>
	            </tr>
	
	            <tr class="containerBody">
	              <td class="formLabel" >Data blocco</td>
	              <td>
	   <%-- 	 		<zeroio:dateSelect field="seqa_data" form="main" showTimeZone="false" timestamp="<%=Capo.getSeqa_data() %>"/>	--%>          
	              		<input readonly type="text" name="seqa_data" size="10" value="<%=DateUtils.timestamp2string(Capo.getSeqa_data())%>" />&nbsp;
				        <a href="#" onClick="cal19.select(document.forms[0].seqa_data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].seqa_data);"><img src="images/delete.gif" align="absmiddle"/></a>
	              		
	              </td>
	            </tr>
	
	            <tr class="containerBody">
	              <td class="formLabel" >Data sblocco</td>
	              <td>
<%-- 	              		<zeroio:dateSelect field="seqa_data_sblocco" form="main" showTimeZone="false" timestamp="<%=Capo.getSeqa_data_sblocco() %>"/> 	--%>
		              	<input readonly type="text" name="seqa_data_sblocco" size="10" value="<%=DateUtils.timestamp2string(Capo.getSeqa_data_sblocco())%>" />&nbsp;
				        <a href="#" onClick="cal19.select(document.forms[0].seqa_data_sblocco,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
				 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
				 		<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].seqa_data_sblocco);"><img src="images/delete.gif" align="absmiddle"/></a>
	              </td>
	            </tr>
	            
	             <tr class="containerBody">
	                <td class="formLabel">Destinazione allo sblocco</td>
	                <td>
	                	<%=ProvvedimentiVAM.getHtmlSelect("seqa_destinazione_allo_sblocco", Capo.getSeqa_destinazione_allo_sblocco()) %>
	    			</td>                
	            </tr>
	              </tbody>
	           </table>
	        </div>
		</td>
	</tr>
      
      </tbody>
   </table>   
</div>


<%--
<div id="tabs-9">

<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="55%">
		<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>

        <td valign="top" width="100%"><table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>
              <th colspan="2"><strong>Abbattimento</strong></th>
            </tr>
            <tr class="containerBody">
              <td class="formLabel" >Data</td>
              <td>
              	<zeroio:dateSelect field="abb_data" form="main" showTimeZone="false"/>
              	<%--zeroio:timeSelect baseName="abb_data" value="<%= new Timestamp(Calendar.getInstance().getTimeInMillis()) %>" />
             </td>

            </tr>
     
                <tr class="containerBody">
                <td class="formLabel">Motivo</td>
                <td>
                	<textarea rows="2" cols="40" name="abb_motivo"></textarea>
                </td>
                </tr>
                
<!--               <tr class="containerBody">-->
<!--               <td class="formLabel">Distruzione Carcassa</td>-->
<!--               <td>-->
<!--               		<input type="checkbox" name="abb_dist_carcassa" />-->
<!--               </td>-->
<!--            </tr>-->

             <tr>
                <th colspan="2"><strong>Veterinari addetti al controllo</strong></th>
            </tr>
            
            <tr class="containerBody">
            	<td colspan="2">
		            <table>
		            	<tr class="containerBody">
		                	<td>1. <input onchange="javascript:toggleVetDiv('abb_veterinario')" size="25" id="abb_veterinario" name="abb_veterinario" type="text" /><%Veterinari.setJsEvent( "onchange=\"set_vet( this, 'abb_veterinario')\"" ); %> <%=Veterinari.getHtmlSelect("veterinari_abb", "-1") %></td>
		                </tr>
			            <tr class="containerBody" id="abb_veterinario_toggle" style="display: none" >
			                <td>2. <input onchange="javascript:toggleVetDiv('abb_veterinario_2')" size="25" id="abb_veterinario_2" name="abb_veterinario_2" type="text" /><%Veterinari.setJsEvent( "onchange=\"set_vet( this, 'abb_veterinario_2')\"" ); %> <%=Veterinari.getHtmlSelect("veterinari_abb2", "-1") %></td>
			            </tr>
			            <tr class="containerBody" id="abb_veterinario_2_toggle" style="display: none" >
			                <td>3. <input size="25" id="abb_veterinario_3"  name="abb_veterinario_3" type="text" /><%Veterinari.setJsEvent( "onchange=\"set_vet( this, 'abb_veterinario_3')\"" ); %> <%=Veterinari.getHtmlSelect("veterinari_abb3", "-1") %></td>
			            </tr>
		            </table>
	            </td>
            </tr>

          </tbody>
        </table>

			  </td>

        
        

        <td valign="top" width="50%"><!-- FILTRI DI RICERCA --></td>
    </tr></tbody></table>
		
</td>

</tr></tbody></table>
	</div --%>

<% if (false) { %>
<div id="tabs-14">

<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody><tr>
        <td valign="top" width="55%">
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
    <tbody>
    <tr>
        <td valign="top" width="100%">
        
        <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
          <tbody>
            <tr>

              <th colspan="2"><strong>Test BSE</strong></th>
            </tr>

             <tr class="containerBody">
                <td class="formLabel">Data Prelievo</td>
                <td>
                	<zeroio:dateSelect 
                		field="bse_data_prelievo" 
                		form="main" 
                		showTimeZone="false" 
                		timestamp="<%=Capo.getBse_data_prelievo() %>" />
                </td>
            </tr>
                
             <tr class="containerBody">
                <td class="formLabel">Data Ricezione Esito</td>
                <td>
                	<zeroio:dateSelect 
                		field="bse_data_ricezione_esito" 
                		form="main" 
                		showTimeZone="false" 
                		timestamp="<%=Capo.getBse_data_ricezione_esito() %>" />
                </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td>
                	<select name="bse_esito" id="bse_esito" >
                		<option 
                			value="" 
                			<%=( toHtmlValue( Capo.getBse_esito() ).length() > 0 ) ? ("") : ("selected=\"selected\"") %> 
                			>-- Seleziona --</option>
                		<option 
                			value="POSITIVO" 
                			<%=( "POSITIVO".equals( toHtmlValue( Capo.getBse_esito() ) ) ) ? ("selected=\"selected\"") : ("") %> 
                			>POSITIVO</option>
                		<option 
                			value="NEGATIVO" 
                			<%=( "NEGATIVO".equals( toHtmlValue( Capo.getBse_esito() ) ) ) ? ("selected=\"selected\"") : ("") %> 
                			 >NEGATIVO</option>
                	</select>
                </td>
             </tr>
            
			<tr class="containerBody">
                <td class="formLabel">Note</td>
                <td><textarea rows="2" cols="40" name="bse_note"><%=toHtmlValue( Capo.getBse_note() ) %></textarea></td>
            </tr>

          </tbody>
        </table>
         
	</td>

    </tr></tbody></table>
		
	</td>

</tr></tbody></table>
	</div>
<% } %>

	
</div>

<input type="submit" value="Salva" onclick="document.main.clona.value=''" >
<dhv:evaluate if="<%= update != true %>">
<input type="submit" value="Salva e Clona" onclick="document.main.clona.value='si'" >
</dhv:evaluate>
</div><!-- End demo -->
</form>
</dhv:container>