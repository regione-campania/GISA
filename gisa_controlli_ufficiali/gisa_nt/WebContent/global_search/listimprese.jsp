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
  - Version: $Id: accounts_search.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<jsp:useBean id="SearchOpuListInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="AccountStateSelect"
	class="org.aspcfs.utils.web.StateSelect" scope="request" />
<jsp:useBean id="ContactStateSelect"
	class="org.aspcfs.utils.web.StateSelect" scope="request" />
<jsp:useBean id="CountrySelect"
	class="org.aspcfs.utils.web.CountrySelect" scope="request" />
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.global_search.base.OrganizationView"
	scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popLookupSelect.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCheckList.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/setSalutation.js"></script>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
cal19.showNavigationDropdowns();
</SCRIPT>


<script>function checkForm(form){
	
	var nome = form.nome.value;
	var iva = form.searchPartitaIva.value;
	var targa = form.searchTarga.value;
	
	var esito = true;
	var message = "";
	if (nome == '' && iva =='' && targa==''){
		esito = false;
		message+="Inserire almeno un filtro di ricerca.";
	}
	
	if (esito==false)
		alert(message);
	else{
		loadModalWindow();
		form.submit();
	}
	
}</script>

<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>




<!-- ******************************************************************** -->

<%@ include file="../initPage.jsp"%>


<style type="text/css">
#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
</style>


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


</script>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/interface/CountView.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript">

		   
			function popolaComboComuni(idAsl)
			{
				idAsl = document.searchAccount.searchcodeOrgSiteId.value;
				PopolaCombo.getValoriComboComuniAsl(idAsl,setComuniComboCallback);
				CountView.getProvincia(idAsl,setProv);		
			}

	        function setComuniComboCallback(returnValue)
	        {	        
	        	  var select = document.forms['searchAccount'].searchAccountCity; //Recupero la SELECT
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

	        function setProv(returnValue){
		    	  var select = document.forms['searchAccount'].searchAccountOtherState; //Recupero la SELECT
		    	  var idAsl = document.searchAccount.searchcodeOrgSiteId.value;
				
				  if(idAsl == -1){
					  document.searchAccount.searchAccountOtherState.options[0]=new Option("-- SELEZIONA VOCE --", "-1", true, false);
					  document.searchAccount.searchAccountOtherState.options[1]=new Option("AV", "1", true, false);
					  document.searchAccount.searchAccountOtherState.options[2]=new Option("BN", "2", true, false);
					  document.searchAccount.searchAccountOtherState.options[3]=new Option("CE", "3", true, false);
					  document.searchAccount.searchAccountOtherState.options[4]=new Option("NA", "4", true, false);
					  document.searchAccount.searchAccountOtherState.options[5]=new Option("SA", "5", true, false);
					  
					} 
				  	else{
					  
				  		for (var i = select.length - 1; i >= 0; i--)
			            	  select.remove(i);

				    	  
			          	  valori = returnValue;
		          	  	
			              //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
			              var NewOpt = document.createElement('option');
			              

			              if(valori == "AV"){
				              NewOpt.value = 1;
			              }

			              if(valori == "BN"){
				              NewOpt.value = 2;
			              }

			              if(valori == "CE"){
				              NewOpt.value = 3;
			              }

			              if(valori == "NA"){
				              NewOpt.value = 4;
			              }

			              if(valori == "SA"){
				              NewOpt.value = 5;
			              }	

			              NewOpt.text = valori; // Imposto il testo

			              //Aggiungo l'elemento option
			              try
			              {
			            	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
			              }catch(e){
			            	  select.add(NewOpt); // Funziona solo con IE
			              }
			            
				    	 
				  	}
   
			  }

	          function popolaAsl(comune){
		          comune = document.searchAccount.searchAccountCity.value;
		          PopolaCombo.getValoriComuniASL(comune,setAslCallback) ;
		          
			  }

			  function setAslCallback(returnValue){

				  document.searchAccount.searchcodeOrgSiteId.value = returnValue[0];
				  idAsl = document.searchAccount.searchcodeOrgSiteId.value;
				  CountView.getProvincia(idAsl,setProv);
			  }

	          function checkCount(){

	        	  //importante per reimpostare la ricerca normale (senza report)
	        	  document.forms.searchAccount.attributes['action'].value = 'GlobalSearch.do?command=SearchImprese&items=-1&excel=no';
		          
	        		  
				  
					 
				  pIva = document.forms['searchAccount'].searchNumReg.value;
				 
				  
				  
				  CountView.getCount(asl,tipo,prov,comune,nome,categoria,tipo_attivita,idf,numVerbale,checkOp,checkAtt,checkEsito,tipoRicerca,pIva,nReg,setCount);
				 
			  }

	          //In return value vi è il totale del select count
	          function setCount(returnValue){
			
		          if(returnValue > 1000){
			          //alert("In base ai criteri impostati la ricerca produce "+returnValue+" risultati \n E' necessario restringere il campo di ricerca");   
			          var answer = confirm("In base ai criteri impostati la ricerca produce "+returnValue+" risultati,\n" +
					                       "pertanto non è possibile visualizzarli tutti nella pagina\n" +  
					                       "-> Cliccare su OK per visualizzare i primi 1000\n" + 
					                       "-> Cliccare su Annulla se si vuole invece restringere il campo di ricerca");
                      if(answer){
                          loadModalWindow();
                    	  document.forms.searchAccount.attributes['action'].value = 'GlobalSearch.do?command=Search&items=-1&excel=no'    
                    	  document.forms.searchAccount.submit();
                          //window.location.href = "GlobalSearch.do?command=ToExportExcelOperatori";
                      }
                      else
			    	  	jQuery('#modalWindow').removeClass('locked').addClass('unlocked');
                      
                      
		          }
		          else{
		        	  loadModalWindow();
			          document.forms['searchAccount'].submit();    
			      }
		          
		      }

	          function checkQuery(){

	        	  //importante per reimpostare la ricerca normale (senza report)
	        	  document.forms.searchAccount.attributes['action'].value = 'GlobalSearch.do?command=Search&items=-1&excel=no'
		          
	        	  var asl         = document.forms['searchAccount'].searchcodeOrgSiteId.value;
				  var tipo        = document.getElementById('tipo').value;
				  var prov        = document.getElementById('prov').value;
	        	  var comune      = document.forms['searchAccount'].searchAccountCity.value;
	        	  var nome        = document.getElementById('nome').value;
				  var categoria   = document.forms['searchAccount'].searchcodeCategoriaRischio.value;
				  var tipo_attivita = document.forms['searchAccount'].searchcodeTipologiaAttivita.value;
	        	  var id          = document.getElementById('identificativo').value;
	        	  var num_verbale = document.getElementById('num_verbale').value;
	        	  var numero      = document.getElementById('numero').value;
	        	  var qual 		  = document.getElementById('qualificatore').value;
	        	  var inizio      = document.getElementById('data1').value;
	        	  var fine        = document.getElementById('data2').value;
			      var checkOp = 'no_trashed';
				  var checkAtt = 'no_trashed';	
				  
				  if(tipoRicerca == 'cu'){
					  if(tipo_attivita == -1){
						  alert('Selezionare il Tipo Attività');
						  return;
					  }
				  }

				  if(!document.getElementById('searchOpCancellati').checked){
					  checkOp = 'trashed' ;
				  }

				  if(!document.getElementById('searchAttCancellati').checked){
					  checkAtt = 'trashed' ;
				  }
	        	  
	        	  if(qual != 4){
        	  	  	  if(numero == -1){
        	  	  		  alert('Seleziona un valore numerico\r\n');
        	  	  		  return;
        	  	  	  }	
	        	  }
	        	  else{
	        	  		document.getElementById('numero').disabled = true ;
	        	  }
	        	  
	        	  CountView.getCountQuery(asl,tipo,prov,comune,nome,categoria,tipo_attivita,id,num_verbale,numero,checkOp,checkAtt,qual,inizio,fine,setCountQuery);
	        	  loadModalWindow();
			}

	          //In return value vi è il totale del selec count
	          function setCountQuery(returnValue){
		          if(returnValue > 1000){
			          //alert("In base ai criteri impostati la ricerca produce "+returnValue+" risultati \n E' necessario restringere il campo di ricerca");

		        	  var answer = confirm("In base ai criteri impostati la ricerca produce "+returnValue+" risultati,\n" +
		                       "pertanto non è possibile visualizzarli tutti nella pagina\n" +  
		                       "-> Cliccare su OK per visualizzare i primi 1000\n" + 
		                       "-> Cliccare su Annulla se si vuole invece restringere il campo di ricerca");

				      if(answer){
				    	  loadModalWindow();
				       	  document.forms.searchAccount.attributes['action'].value = 'GlobalSearch.do?command=Search&items=-1&excel=no';  
				       	  document.forms['searchAccount'].submit();
				       		
			         }
				      else
				    	  jQuery('#modalWindow').removeClass('locked').addClass('unlocked');
			             
		          }
		          else{
		        	  document.forms['searchAccount'].submit();
			      }
		          
	          }

	          
				function popolaComboProvincia(provincia){
					provincia = document.searchAccount.searchAccountOtherState.value;
					var p=document.getElementById("prov").options[provincia].text;
					//PopolaComuni a partire dalla provincia
					PopolaCombo.getValoriComboComuniProvinciaOSM(p,setComuniComboCallback);
					//PopolaAsl a partire dalla provincia
					PopolaCombo.getValoriAslProvincia(p,setAsl);
				}

			   		function setAsl(returnValue){
				   		
			   			var select = document.forms['searchAccount'].searchcodeOrgSiteId; //Recupero la SELECT
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
  


  	function clearForm() {  

  	  	  	document.forms['searchAccount'].searchcodeOrgSiteId.value="-1";
	  		document.searchAccount.searchcodeOrgSiteId.options[0]=new Option("-- Tutti i Siti --", "-2", true, false);
	  		document.searchAccount.searchcodeOrgSiteId.options[1]=new Option("-- SELEZIONA VOCE --", "-1", true, false);
	  		document.searchAccount.searchcodeOrgSiteId.options[2]=new Option("AVELLINO", "201", true, false);
	  		//document.searchAccount.searchcodeOrgSiteId.options[3]=new Option("Avellino ex AV 2", "2", true, false);
	  		document.searchAccount.searchcodeOrgSiteId.options[4]=new Option("BENEVENTO", "202", true, false);
	  		document.searchAccount.searchcodeOrgSiteId.options[5]=new Option("CASERTA", "203", true, false);
	  		//document.searchAccount.searchcodeOrgSiteId.options[6]=new Option("Caserta ex CE 2", "5", true, false); 
	  		document.searchAccount.searchcodeOrgSiteId.options[7]=new Option("NAPOLI 1 CENTRO", "204", true, false);
	  		document.searchAccount.searchcodeOrgSiteId.options[8]=new Option("NAPOLI 2 NORD", "7", true, false);
	  		document.searchAccount.searchcodeOrgSiteId.options[9]=new Option("NAPOLI 3 SUD", "8", true, false);
	  		/*document.searchAccount.searchcodeOrgSiteId.options[10]=new Option("Napoli 3 Sud ex NA 4", "9", true, false);
	  		document.searchAccount.searchcodeOrgSiteId.options[11]=new Option("Napoli 3 Sud ex NA 5", "10", true, false);
	  		document.searchAccount.searchcodeOrgSiteId.options[12]=new Option("Salerno ex SA 1", "11", true, false);
	  		document.searchAccount.searchcodeOrgSiteId.options[13]=new Option("Salerno ex SA 2", "12", true, false);*/
	  		document.searchAccount.searchcodeOrgSiteId.options[14]=new Option("SALERNO", "207", true, false); 
	  		document.searchAccount.searchcodeOrgSiteId.options[15]=new Option("Asl Fuori Regione", "16", true, false); 
	  		document.forms['searchAccount'].searchcodeTipologia.value="-1";
	  		document.searchAccount.searchAccountOtherState.options[0]=new Option("-- SELEZIONA VOCE --", "-1", true, false);
	  		document.searchAccount.searchAccountOtherState.options[1]=new Option("AV", "1", true, false);
	  		document.searchAccount.searchAccountOtherState.options[2]=new Option("BN", "2", true, false);
	  		document.searchAccount.searchAccountOtherState.options[3]=new Option("CE", "3", true, false);
	  		document.searchAccount.searchAccountOtherState.options[4]=new Option("NA", "4", true, false);
	  		document.searchAccount.searchAccountOtherState.options[5]=new Option("SA", "5", true, false); 
	  		document.forms['searchAccount'].searchAccountOtherState.value="-1";
	  		document.forms['searchAccount'].searchAccountCity.value="";
	  		document.forms['searchAccount'].searchAccountName.value="";
	  		document.forms['searchAccount'].searchcodeCategoriaRischio.value="-1";
	  		document.forms['searchAccount'].searchcodeTipologiaAttivita.value="-1";
	  	  	document.forms['searchAccount'].searchAccountIdentificativo.value="";
	  	  	document.forms['searchAccount'].searchAccountNumVerbale.value="";
	  	  	document.forms['searchAccount'].searchtimestampInizio.value="";
	  	  	document.forms['searchAccount'].searchtimestampFine.value="";
		  	document.forms['searchAccount'].searchcodeQualificatore.value="-1";
	  	  	document.forms['searchAccount'].searchcodeNumero.value="-1";
	  	  	
	  	  	
	  		
  	 }
 
  	function checkDisplay(){

  	  	var tipo = document.forms['searchAccount'].searchcodeTipologiaAttivita.value;
  	  	if(tipo != -1){
  	  	  	document.getElementById('dettagli').style.display = 'block';
  	  	}
  	  	else{
  	  		document.getElementById('dettagli').style.display = 'none';
  	  	}
  	  	
  	  	if(tipo == 2){
  	  		document.getElementById('viewEsito').style.display = '';
  	  	}
  	  	else {
  	  		document.getElementById('viewEsito').style.display = 'none';
  	  	}
  	  		
  	  	  	
  	}  

  	function checkQualificatore(){

  	  	var tipo = document.forms['searchAccount'].searchcodeQualificatore.value;
  	  	if(tipo != 4){
  	  	  	document.getElementById('numero').disabled = false ;
  	  		document.getElementById('riga_intervallo').style.display = '';
  	  	}
  	  	else{
  	  		document.getElementById('numero').disabled = true ;
  	  		document.getElementById('riga_intervallo').style.display = 'none';
  	  	}
  	  	  	
  	}  

  	
  	function checkNumero(){

  	  	var tipo   = document.forms['searchAccount'].searchcodeQualificatore.value;
  	  	var numero = document.getElementById('numero').value;
  	  	formTest = true;
  	  	if(tipo != 4){
  	  	  	if(numero == -1){
  	  	  		alert('Seleziona un valore numerico\r\n');
  	  	  		formTest = false;
  	  	  	}	
  	  	}
  	  	else{
  	  		document.getElementById('numero').disabled = true ;
  	  	}

  	  	if(formTest){
	        document.forms['searchAccount'].submit();    
  	  			
  	  	}
	  	  	
  		return formTest;
	  	  	
  	}  
  
</script>



<dhv:include name="accounts-search-name" none="true">
	<body onLoad="">
</dhv:include>
<%-- Trails --%>
<form name="searchAccount"
	action="GlobalSearch.do?command=SearchImprese&items=-1" method="post">
	<table class="trails" cellspacing="0">
		<tr>
		
					<td></td>
		</tr>
	</table>

	<%-- End Trails --%>


	<table cellpadding="2" cellspacing="2" border="0" width="100%">
		<tr>
			<td width="50%" valign="top">
				<table cellpadding="4" cellspacing="0" border="0" width="50%"
					class="details">

						<tr>
							<th colspan="2"><strong><dhv:label
										name="Ricerca Globale Operatori">Ricerca Globale Operatori</dhv:label></strong>
							</th>
						</tr>
						
						
						<tr>
							<td class="formLabel"><dhv:label name="">Ragione Sociale</dhv:label>
							</td>
							<td><input id="nome" type="text" maxlength="70" size="40"
								name="searchragioneSociale"
								value="">
							</td>
						</tr>

						<tr>
							<td class="formLabel">Partita IVA
							</td>
							<td><input id="searchPartitaIva" type="text" maxlength="70"
								size="40" name="searchPartitaIva"
								value="">
							</td>
						</tr>

						<tr>
							<td class="formLabel"><dhv:label name="">Targa</dhv:label>
							</td>
							<td><input id="searchTarga" type="text" maxlength="70"
								size="40" name="searchTarga"
								value="">
							</td>
						</tr>
						
				

				</table> 
					<input type="button" id="searchO" name="searchO"
						value="RICERCA"
						onClick="checkForm(this.form)" />
				
				<!--   </form> -->

			</td>

			
		</tr>


		<input type="hidden" id="tipoRicerca" name="tipoRicerca" value="" />
		<input type="hidden" name="source" value="searchForm">

	</table>
</form>




<iframe src="empty.html" name="server_commands" id="server_commands"
	style="visibility: hidden" height="0"></iframe>
<script type="text/javascript">
</script>


