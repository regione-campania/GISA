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

<jsp:useBean id="SearchOrgListInfo"
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

		   
// 			function popolaComboComuni(idAsl)
// 			{
// 				idAsl = document.searchAccount.searchcodeOrgSiteId.value;
// 				PopolaCombo.getValoriComboComuniAsl(idAsl,setComuniComboCallback);
// 				CountView.getProvincia(idAsl,setProv);		
// 			}

// 	        function setComuniComboCallback(returnValue)
// 	        {	        
// 	        	  var select = document.forms['searchAccount'].searchAccountCity; //Recupero la SELECT
// 	              //Azzero il contenuto della seconda select
	              
// 	              for (var i = select.length - 1; i >= 0; i--)
// 	            	  select.remove(i);

// 	              indici = returnValue [0];
// 	              valori = returnValue [1];
// 	              //Popolo la seconda Select
// 	              for(j =0 ; j<indici.length; j++){
// 	              //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
// 	              var NewOpt = document.createElement('option');
// 	              NewOpt.value = indici[j]; // Imposto il valore
// 	              NewOpt.text = valori[j]; // Imposto il testo

// 	              //Aggiungo l'elemento option
// 	              try
// 	              {
// 	            	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
// 	              }catch(e){
// 	            	  select.add(NewOpt); // Funziona solo con IE
// 	              }
// 	              }


// 	          }

// 	        function setProv(returnValue){
// 		    	  var select = document.forms['searchAccount'].searchAccountOtherState; //Recupero la SELECT
// 		    	  var idAsl = document.searchAccount.searchcodeOrgSiteId.value;
				
// 				  if(idAsl == -1){
// 					  document.searchAccount.searchAccountOtherState.options[0]=new Option("-- SELEZIONA VOCE --", "-1", true, false);
// 					  document.searchAccount.searchAccountOtherState.options[1]=new Option("AV", "1", true, false);
// 					  document.searchAccount.searchAccountOtherState.options[2]=new Option("BN", "2", true, false);
// 					  document.searchAccount.searchAccountOtherState.options[3]=new Option("CE", "3", true, false);
// 					  document.searchAccount.searchAccountOtherState.options[4]=new Option("NA", "4", true, false);
// 					  document.searchAccount.searchAccountOtherState.options[5]=new Option("SA", "5", true, false);
					  
// 					} 
// 				  	else{
					  
// 				  		for (var i = select.length - 1; i >= 0; i--)
// 			            	  select.remove(i);

				    	  
// 			          	  valori = returnValue;
		          	  	
// 			              //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
// 			              var NewOpt = document.createElement('option');
			              

// 			              if(valori == "AV"){
// 				              NewOpt.value = 1;
// 			              }

// 			              if(valori == "BN"){
// 				              NewOpt.value = 2;
// 			              }

// 			              if(valori == "CE"){
// 				              NewOpt.value = 3;
// 			              }

// 			              if(valori == "NA"){
// 				              NewOpt.value = 4;
// 			              }

// 			              if(valori == "SA"){
// 				              NewOpt.value = 5;
// 			              }	

// 			              NewOpt.text = valori; // Imposto il testo

// 			              //Aggiungo l'elemento option
// 			              try
// 			              {
// 			            	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
// 			              }catch(e){
// 			            	  select.add(NewOpt); // Funziona solo con IE
// 			              }
			            
				    	 
// 				  	}
   
// 			  }

// 	          function popolaAsl(comune){
// 		          comune = document.searchAccount.searchAccountCity.value;
// 		          PopolaCombo.getValoriComuniASL(comune,setAslCallback) ;
		          
// 			  }

// 			  function setAslCallback(returnValue){

// 				  document.searchAccount.searchcodeOrgSiteId.value = returnValue[0];
// 				  idAsl = document.searchAccount.searchcodeOrgSiteId.value;
// 				  CountView.getProvincia(idAsl,setProv);
// 			  }

	          function checkCount(){
	        	  //importante per reimpostare la ricerca normale (senza report)
	        	  document.forms.searchAccount.attributes['action'].value = 'GlobalSearch.do?command=Search&items=-1&excel=no';
		          
	        	  var asl= document.forms['searchAccount'].searchcodeOrgSiteId.value;
				//  var tipo = document.getElementById('tipo').value;
	        	//  var prov = document.getElementById('prov').value;
	        	//  var comune = document.forms['searchAccount'].searchAccountCity.value;
	        	//  var nome = document.getElementById('nome').value;
				//  var categoria = document.forms['searchAccount'].searchcodeCategoriaRischio.value;
				  
				  
				  var tipo_attivita='';
				  var idf='';
				  var numVerbale =''; 
				  var tipoRicerca ='' ;  	
				  var checkOp = 'no_trashed';
				  var checkEsito = 'non_esito';
				  var checkEsito = 'non_esito';
				  var checkAtt = '';	
				//  var pIva ;
				 // var nReg ;
				  
				  
					 
// 				  pIva = document.forms['searchAccount'].searchNumReg.value;
// 				  nReg = document.forms['searchAccount'].searchPartitaIva.value;
				  tipoRicerca = document.getElementById('tipoRicerca').value;  
				  if (document.forms['searchAccount'].searchcodeTipologiaAttivita != null)
					  {
				   tipo_attivita = document.forms['searchAccount'].searchcodeTipologiaAttivita.value;
				   idf = document.getElementById('identificativo').value;
				   numVerbale = document.getElementById('num_verbale').value;
				  
					  }
				  
  
				  if(document.getElementById('searchAttCancellati') != null && !document.getElementById('searchAttCancellati').checked){
					  checkAtt = 'trashed' ;
				  }
	        	  
				  if(document.getElementById('searchEsito') != null && !document.getElementById('searchEsito').checked){
					  checkEsito = 'ok_respinti' ;
				  }
				  
				 if(document.getElementById('searchOpCancellati') != null && !document.getElementById('searchOpCancellati').checked){
					  checkOp = 'trashed' ;
				  }

				 if(tipo_attivita!= null && tipo_attivita != -1){
					 if(document.getElementById('searchAttCancellati') != null && !document.getElementById('searchAttCancellati').checked){
						  checkAtt = 'trashed' ;
					  }
					 else {
						  checkAtt = 'no_trashed' ;
					 }
				 }
				  
				 
				
				  
				  if(tipoRicerca == 'cu'){
					  if(tipo_attivita == -1){
						  alert('Selezionare il Tipo Attività');
						  return;
					  }
				  }
				  
				  loadModalWindow();
            	  document.forms.searchAccount.attributes['action'].value = 'GlobalSearch.do?command=Search&items=-1&excel=no'    
            	  document.forms.searchAccount.submit();
		        	
			  }


	          function checkQuery(){

	        	  //importante per reimpostare la ricerca normale (senza report)
	        	  document.forms.searchAccount.attributes['action'].value = 'GlobalSearch.do?command=Search&items=-1&excel=no'
		          
	        	  var asl         = document.forms['searchAccount'].searchcodeOrgSiteId.value;
// 				  var tipo        = document.getElementById('tipo').value;
// 				  var prov        = document.getElementById('prov').value;
// 	        	  var comune      = document.forms['searchAccount'].searchAccountCity.value;
// 	        	  var nome        = document.getElementById('nome').value;
// 				  var categoria   = document.forms['searchAccount'].searchcodeCategoriaRischio.value;
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
	        	  
	        	  CountView.getCountQuery(asl,tipo_attivita,id,num_verbale,numero,checkOp,checkAtt,qual,inizio,fine,setCountQuery);
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

	          
// 				function popolaComboProvincia(provincia){
// 					provincia = document.searchAccount.searchAccountOtherState.value;
// 					var p=document.getElementById("prov").options[provincia].text;
// 					//PopolaComuni a partire dalla provincia
// 					PopolaCombo.getValoriComboComuniProvinciaOSM(p,setComuniComboCallback);
// 					//PopolaAsl a partire dalla provincia
// 					PopolaCombo.getValoriAslProvincia(p,setAsl);
// 				}

// 			   		function setAsl(returnValue){
				   		
// 			   			var select = document.forms['searchAccount'].searchcodeOrgSiteId; //Recupero la SELECT
// 						//Azzero il contenuto della seconda select
// 		                for (var i = select.length - 1; i >= 0; i--)
// 		            	  select.remove(i);

// 		              	indici = returnValue [0];
// 		              	valori = returnValue [1];
// 		              	//Popolo la seconda Select
// 		              	for(j =0 ; j<indici.length; j++){
// 		              	//Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
// 		              var NewOpt = document.createElement('option');
// 		              NewOpt.value = indici[j]; // Imposto il valore
// 		              NewOpt.text = valori[j]; // Imposto il testo
// 		              //Aggiungo l'elemento option
// 		              try
// 		              {
// 		            	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
// 		              }catch(e){
// 		            	  select.add(NewOpt); // Funziona solo con IE
// 		              }
// 		              }

							
				   		
// 					}
  


  	function clearForm() {  

  	  	  	document.forms['searchAccount'].searchcodeOrgSiteId.value=-2;
	  		
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
	action="GlobalSearch.do?command=Search&items=-1" method="post">
	<table class="trails" cellspacing="0">
		<tr>
			<td>Ricerca</td>
		</tr>
	</table>

	<%-- End Trails --%>


	<table cellpadding="2" cellspacing="2" border="0" width="100%">
		<tr>
			<td width="50%" valign="top">
				<table cellpadding="4" cellspacing="0" border="0" width="50%"
					class="details">

				
					<dhv:permission name="global-search-attivita-view">

						<tr>
							<th colspan="2"><strong><dhv:label
										name="Ricerca Globale Controlli Ufficiali e Sottoattività">Ricerca Globale Controlli Ufficiali e Sottoattività</dhv:label></strong>
							</th>
							
						</tr>
						<dhv:evaluate if="<%= SiteList.size() > 2 %>">
							<tr>
								<td nowrap class="formLabel"><dhv:label name="">ASL</dhv:label>
								</td>
								<td><dhv:evaluate
										if="<%=User.getUserRecord().getSiteId() == -1 %>">
<%-- 										<%SiteList.setJsEvent("onChange=popolaComboComuni()");%> --%>
										<%= SiteList.getHtmlSelect("searchcodeOrgSiteId", ("".equals(SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")) ? String.valueOf(Constants.INVALID_SITE) : SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId"))) %>
									</dhv:evaluate> <dhv:evaluate
										if="<%=User.getUserRecord().getSiteId() != -1 %>">
										<input type="hidden" id="asl" name="searchcodeOrgSiteId"
											value="<%= User.getUserRecord().getSiteId()%>">
										<%= SiteList.getSelectedValue(User.getUserRecord().getSiteId()) %>
									</dhv:evaluate></td>
							</tr>
						</dhv:evaluate>
						<dhv:evaluate if="<%= SiteList.size() <= 2 %>">
							<input type="hidden" name="searchcodeOrgSiteId"
								id="searchcodeOrgSiteId" value="-1" />
						</dhv:evaluate>
						<tr
							<%=User.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA ? "style='display:none'" :"" %>>
							<td nowrap class="formLabel"><dhv:label name="">Stato Operatore</dhv:label>
							</td>
							<td>
								 <input type="radio" name="searchOpCancellati"
								id="searchOpCancellati" value="no_trashed" checked="checked" />Non
								cancellati <input type="radio" name="searchOpCancellati"
								id="searchOpCancellati" value="trashed" />Cancellati/Archiviati
							</td>
						</tr>
						
						<tr>
							<td nowrap class="formLabel"><dhv:label name="">Tipo Attività</dhv:label>
							</td>
							<td>
								<!-- Tipo Attività --> <select
								name="searchcodeTipologiaAttivita" value="-1" id="tipoA"
								onchange="javascript:checkDisplay()">
									<option value="-1">-- SELEZIONA VOCE --</option>
									<option value="2">Campioni</option>
									<option value="3">Controlli Ufficiali</option>
<!-- 									<option value="7">Tamponi</option> -->
									<option value="1">Sanzioni</option>
									<option value="9">Sequestri</option>
									<option value="6">Notizie di Reato</option>
									<option value="15">Altri follow up</option>
							</select> <input type="radio" name="searchAttCancellati"
								id="searchAttCancellati" value="no_trashed" checked="checked" />Non
								cancellati <input type="radio" name="searchAttCancellati"
								id="searchAttCancellati" value="trashed" />Cancellati </>
							</td>
						</tr>

						<tr class="containerBody">
							<td nowrap class="formLabel"><dhv:label name="">Identificativo C.U.</dhv:label>
							</td>
							<td><input id="identificativo" type="text" maxlength="70"
								size="50" name="searchAccountIdentificativo" /></td>
						</tr>
						<tr>
							<td nowrap class="formLabel"><dhv:label name="Comune">Numero Verbale</dhv:label>
							</td>
							<td><input id="num_verbale" type="text" maxlength="70"
								size="50" name="searchAccountNumVerbale" /></td>
						</tr>

						<tr style="display: none" id="viewEsito">
							<td nowrap class="formLabel"><dhv:label name="Comune">Esito Campione</dhv:label>
							</td>
							<td><input type="radio" name="searchEsito" id="searchEsito"
								value="non_esito" checked="checked" />Senza esito <input
								type="radio" name="searchEsito" id="searchEsito"
								value="ok_respinti" />Respinti</td>
						</tr>
					</dhv:permission>

				</table> <dhv:permission name="global-search-attivita-view">
					<input type="button" id="searchC" name="searchC"
						value="Visualizza per Controlli"
						onClick="this.form.tipoRicerca.value='cu';javascript:checkCount();" />
					<input type="button" id="searchC" name="searchC"
						value="Visualizza per Campioni"
						onClick="if (this.form.tipoA.value!=2) {alert('Selezionare CAMPIONI come attivita.');} else {this.form.tipoRicerca.value='cam';javascript:checkCount();}" />
				</dhv:permission> <input type="button"
				value="<dhv:label name="button.clear">Clear</dhv:label>"
				onClick="javascript:clearForm();" /> <!--  <input type="hidden" name="source" value="searchForm">  -->

				<!--   </form> -->

			</td>

			<dhv:include name="accounts-contact-information-filters" none="true">
			</dhv:include>
		</tr>



		<dhv:permission name="global-search-attivita-view">
			<tr>
				<td id="dettagli" width="70%" valign="top" style="display: none">
					<!--   <form name="searchAccount" action="GlobalSearch.do?command=SearchCountControlli" method="post"> -->
					<table cellpadding="4" cellspacing="0" border="0" width="50%"
						class="details">
						<tr>
							<th colspan="2"><strong>Dettagli Controlli
									Ufficiali e Sottoattivita</strong></th>
						</tr>
						<tr>
							<td nowrap class="formLabel"><dhv:label name="">Numero Attivita'</dhv:label>
							</td>
							<td><select name="searchcodeQualificatore" value="-1"
								id="qualificatore" onchange="javascript:checkQualificatore()">
									<option value="-1">-- SELEZIONA VOCE --</option>
									<option value="1">Maggiore uguale di</option>
									<option value="2">Minore uguale di</option>
									<option value="3">Uguale a</option>
									<option value="4">Nessuno</option>
							</select>&nbsp <select name="searchcodeNumero" value="-1" id="numero"
								disabled="disabled">
									<option value="-1">-- SELEZIONA VOCE --</option>
									<option value="1">1</option>
									<option value="10">10</option>
									<option value="50">50</option>
									<option value="100">100</option>
							</select></td>

						</tr>
						<tr id="riga_intervallo">
							<td class="formLabel"><dhv:label name="">Nell'intervallo compreso tra: </dhv:label>
							</td>
							<td>
								<%-- value="<%=DateUtils.timestamp2string(Capo.getCd_data_nascita())%>" />&nbsp; --%>

								<input readonly type="text" name="searchtimestampInizio"
								id="data1" size="10" /> <a href="#"
								onClick="cal19.select(document.forms[0].searchtimestampInizio,'anchor19','dd/MM/yyyy'); return false;"
								NAME="anchor19" ID="anchor19"> <img
									src="images/icons/stock_form-date-field-16.gif" border="0"
									align="absmiddle"></a> <input readonly type="text"
								name="searchtimestampFine" id="data2" size="10" />&nbsp; <a
								href="#"
								onClick="cal19.select(document.forms[0].searchtimestampFine,'anchor19','dd/MM/yyyy'); return false;"
								NAME="anchor19" ID="anchor19"> <img
									src="images/icons/stock_form-date-field-16.gif" border="0"
									align="absmiddle"></a>

							</td>
						</tr>

					</table>


					<!--  </form> -->
				</td>
			</tr>
		</dhv:permission>
		</div>
		<input type="hidden" id="tipoRicerca" name="tipoRicerca" value="" />
		<input type="hidden" name="source" value="searchForm">

	</table>
	
	
	
	
	
	
	
	
	<!--  tutti i campi inutili  -->
	
	<div style="display:none !important">
	<!-- Tipo Operatore --> <select name="searchcodeTipologia"
								value="-1" id="tipo">
									<option value="-1">-- SELEZIONA VOCE --</option>
									<option value="1">Stabilimento registrato 852</option>
									<option value="2">Azienda Zootecnica</option>
									<option value="3">Stabilimento 853</option>
									<option value="4">Mercati Ittici</option>
									<option value="9">Trasporto Animali</option>
									<option value="97">Soa</option>
									<option value="800">Osm Riconosciuto</option>
									<option value="801">Osm Registrato</option>
									<option value="201">Molluschi Bivalvi</option>
							</select>
							
							
	<select name="searchAccountOtherState" value="-1" id="prov"
								onchange="popolaComboProvincia();">
									<option value="-1">-- SELEZIONA VOCE --</option>
									<option value="1">AV</option>
									<option value="2">BN</option>
									<option value="3">CE</option>
									<option value="4">NA</option>
									<option value="5">SA</option>
							</select>
							<dhv:evaluate
									if="<%=User.getUserRecord().getSiteId() == -1 %>">
									<%
           		ComuniList.setJsEvent("onChange=popolaAsl();");
           %>
									<%= ComuniList.getHtmlSelectText("searchAccountCity",SearchOrgListInfo.getSearchOptionValue("searchAccountCity")) %>
								</dhv:evaluate>
								<input id="nome" type="text" maxlength="70" size="40"
								name="searchAccountName"
								value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
						<input id="searchPartitaIva" type="text" maxlength="70"
								size="40" name="searchPartitaIva"
								value="<%= SearchOrgListInfo.getSearchOptionValue("searchPartitaIva") %>">
							<input id="searchNumReg" type="text" maxlength="70"
								size="40" name="searchNumReg"
								value="<%= SearchOrgListInfo.getSearchOptionValue("searchNumReg") %>">
							<select name="searchcodeCategoriaRischio">
									<option value="-1">-- Tutte --</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<%-- %>option value="3">3 senza Check List</option--%>
							</select>
						
						</div>
	
	
	
	
	<!--  fine tutti i campi inutili  -->
	
	
	
	
	
	
	
	
	
	
	
</form>



<dhv:include name="accounts-search-contacts" none="false">
	<input type="checkbox" name="searchContacts" value="true"
		<%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
	<dhv:label name="accounts.search.includeContactsInSearchResults">Include contacts in search results</dhv:label>
	<br />
	<br />
</dhv:include>
<iframe src="empty.html" name="server_commands" id="server_commands"
	style="visibility: hidden" height="0"></iframe>
<script type="text/javascript">
</script>


