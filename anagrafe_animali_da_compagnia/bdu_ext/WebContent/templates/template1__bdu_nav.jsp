<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: template1nav.jsp 24362 2007-12-09 17:01:04Z srinivasar@cybage.com $
  - Description:
  --%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="org.aspcfs.controller.SubmenuItem,java.text.DateFormat,java.util.Iterator, java.io.File"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean"
	scope="request" />
<jsp:useBean id="GlobalItems" class="java.lang.String" scope="request" />
<jsp:useBean id="globalItemsPaneState" class="java.lang.String"
	scope="session" />
<!-- import necessari al funzionamento della finestra modale di locking -->
<link rel="shortcut icon" href="images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/engine.js">
	
</script>
<script type="text/javascript" src="dwr/util.js"></script>

<script type="text/javascript" src="gestionecap/js/lookup.js"></script>

<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/DwrCustomSatisfaction.js"> </script>

<script type="text/javascript">
function selezionaIndirizzo(campoNazione,f, c='', regione='ALL',asl='', aslExclude='')
{
		var nazione = '106';
		if(campoNazione!='106')
			nazione = document.getElementById(campoNazione).value;
		if(nazione=='106')
			openCapWidget(f, c, regione, asl,aslExclude);
		
}

function openCapWidget(f, c='', regione='ALL', asl='', aslExclude='') 
{
	loadModalWindow();

	  var w=314;
	  var h=250;
	  var left = (screen.width/2)-(w/2)-200;
	  var top = (screen.height/2)-(h/2)-100;
	  var stl='location=0,toolbar=0,status=0,menubar=0,scrollbars=0,resizable=0,width='+w+',height='+h+', top='+top+',left='+left;

		window.open("gestionecap/capall.html?callbackName=" + f + "&comune=" + c + "&regione=" + regione + "&asl=" + asl + "&aslExclude=" + aslExclude + "&versione=1" , "", stl); 
	}

	
	
function callbackResidenzaProprietario(addressObj) 
{ 
	if(addressObj.via!='')
	{
		document.getElementById('addressLegaleLine1Testo').value = addressObj.top + ' ' + addressObj.via + ', ' + addressObj.civ ;
		document.getElementById('viaTesto').value = addressObj.top + ' ' + addressObj.via + ', ' + addressObj.civ ;
	}
    document.getElementById('addressLegaleCityTesto').value= addressObj.comune ;
    document.getElementById('addressLegaleCity').value= comuni[addressObj.comune] ;
    document.getElementById('cap').value= addressObj.cap ;
    document.getElementById('provinciaTesto').value=addressObj.prov;
	document.getElementById('addressLegaleCountry').value=province[addressObj.prov];
	document.getElementById('searchcodeIdprovincia').value=province[addressObj.prov];
	document.getElementById('searchcodeIdComune').value=comuni[addressObj.comune];
    loadModalWindowUnlock();
}

function callbackResidenzaSedeLegaleCanile(addressObj) 
{ 
	if(addressObj.via!='')
	{
		document.getElementById('viaTestoSedeLegale').value = addressObj.top + ' ' + addressObj.via + ', ' + addressObj.civ ;
	}
    document.getElementById('presso').value= addressObj.cap ;
	document.getElementById('searchcodeIdprovinciaTesto').value=addressObj.prov;
	document.getElementById('searchcodeIdprovinciaSedeLegale').value=province[addressObj.prov];
	document.getElementById('searchcodeIdComuneSedeLegale').value=comuni[addressObj.comune];
	document.getElementById('searchcodeIdComuneTesto').value=addressObj.comune;
    loadModalWindowUnlock();
}



function callbackResidenzaProprietarioCessione(addressObj) 
{ 
	if(addressObj.via!='')
		document.getElementById('indirizzo').value = addressObj.top + ' ' + addressObj.via + ', ' + addressObj.civ ;
    if(addressObj.comune!='')
    	document.getElementById("idComune").value = comuni[addressObj.comune];
    if(addressObj.prov!='')
    	document.getElementById("idProvincia").value = province[addressObj.prov];
    document.getElementById('cap').value= addressObj.cap ;
    loadModalWindowUnlock();
}

function callbackResidenzaProprietarioAdozioneFuoriAsl(addressObj) 
{ 
	if(addressObj.via!='')
		document.getElementById('indirizzo').value = addressObj.top + ' ' + addressObj.via + ', ' + addressObj.civ ;
    if(addressObj.comune!='')
    	document.getElementById("idComune").value = comuni[addressObj.comune];
    if(addressObj.prov!='')
    	document.getElementById("idProvincia").value = province[addressObj.prov];
    document.getElementById('cap').value= addressObj.cap ;
    loadModalWindowUnlock();
}


function callbackResidenzaProprietarioModificaResidenza(addressObj) 
{ 
	if(addressObj.via!='')
		document.getElementById('via').value = addressObj.top + ' ' + addressObj.via + ', ' + addressObj.civ ;
    if(addressObj.comune!='')
    	document.getElementById("idComuneModificaResidenzaSelect").value = comuni[addressObj.comune];
    if(addressObj.prov!='')
    	document.getElementById("idProvinciaModificaResidenzaSelect").value = province[addressObj.prov];
    document.getElementById('cap').value= addressObj.cap ;
    document.getElementById('idComuneModificaResidenza').value= comuni[addressObj.comune] ;
    document.getElementById('idProvinciaModificaResidenza').value= province[addressObj.prov] ;
    document.getElementById("idAslNuovoProprietario").value = elencoAsl[addressObj.asl];
    loadModalWindowUnlock();
}

function callbackResidenzaProprietarioModifica(addressObj) 
{ 
	if(addressObj.via!='')
		document.getElementById('viaTesto').value = addressObj.top + ' ' + addressObj.via + ', ' + addressObj.civ ;
    if(addressObj.comune!='')
    {
    	document.getElementById("searchcodeIdComune").value = comuni[addressObj.comune];
    	document.getElementById("searchcodeIdComuneSelect").value = comuni[addressObj.comune];
	}
    document.getElementById('cap').value= addressObj.cap ;
    loadModalWindowUnlock();
}

function callbackResidenzaProprietarioModificaColonia(addressObj) 
{ 
	if(addressObj.via!='')
		document.getElementById('viaSedeOperativa').value = addressObj.top + ' ' + addressObj.via + ', ' + addressObj.civ ;
    if(addressObj.comune!='')
    {
    	document.getElementById("idComuneSedeOperativa").value = comuni[addressObj.comune];
    	document.getElementById("idComuneSedeOperativaSelect").value = comuni[addressObj.comune];
	}
    document.getElementById('cap').value= addressObj.cap ;
    loadModalWindowUnlock();
}

function callbackResidenzaProprietarioModificaIndirizzo(addressObj) 
{ 
	if(addressObj.via!='')
		document.getElementById('via').value = addressObj.top + ' ' + addressObj.via + ', ' + addressObj.civ ;
    if(addressObj.comune!='')
    {
    	document.getElementById("idComuneModificaResidenza").value = comuni[addressObj.comune];
    	document.getElementById("idComuneModificaResidenzaSelect").value = comuni[addressObj.comune];
    }
    if(addressObj.prov!='')
    {
    	document.getElementById("idProvinciaModificaResidenza").value = province[addressObj.prov];
    	document.getElementById("idProvinciaModificaResidenzaSelect").value = province[addressObj.prov];
    }
    document.getElementById('cap').value= addressObj.cap ;
    document.getElementById("idAslNuovoProprietarioSelect").value = elencoAsl[addressObj.asl];
    document.getElementById("idAslNuovoProprietario").value = elencoAsl[addressObj.asl];
    loadModalWindowUnlock();
}

function callbackResidenzaProprietarioInserimentoColonia(addressObj) 
{ 
	if(addressObj.via!='')
	{
		document.getElementById('via').value = addressObj.top + ' ' + addressObj.via + ', ' + addressObj.civ ;
		document.getElementById('viaTesto').value = addressObj.top + ' ' + addressObj.via + ', ' + addressObj.civ ;
	}
    if(addressObj.comune!='')
    {
    	document.getElementById("searchcodeIdComune").value = comuni[addressObj.comune];
    	document.getElementById("searchcodeIdComuneSelect").value = addressObj.comune;
    }
    if(addressObj.prov!='')
    {
    	if(document.getElementById("searchcodeIdprovincia")!=null)
    		document.getElementById("searchcodeIdprovincia").value = province[addressObj.prov];
    	if(document.getElementById("searchcodeIdprovinciaSelect")!=null)
    		document.getElementById("searchcodeIdprovinciaSelect").value = addressObj.prov;
    }
    document.getElementById('cap').value= addressObj.cap ;
    loadModalWindowUnlock();
}






function wrapper(cb, dataObj) 
{
	switch (cb) {
	case 'callbackResidenzaProprietario': 
		callbackResidenzaProprietario(dataObj);
		break;
	case 'callbackResidenzaProprietarioCessione': 
		callbackResidenzaProprietarioCessione(dataObj);
		break;
	case 'callbackResidenzaProprietarioAdozioneFuoriAsl': 
		callbackResidenzaProprietarioAdozioneFuoriAsl(dataObj);
		break;
	case 'callbackResidenzaProprietarioModificaResidenza': 
		callbackResidenzaProprietarioModificaResidenza(dataObj);
		break;	
	case 'callbackResidenzaProprietarioModificaIndirizzo': 
		callbackResidenzaProprietarioModificaIndirizzo(dataObj);
		break;	
	case 'callbackResidenzaProprietarioModifica': 
		callbackResidenzaProprietarioModifica(dataObj);
		break;
	case 'callbackResidenzaProprietarioModificaColonia':
		callbackResidenzaProprietarioModificaColonia(dataObj);
		break;
	case 'callbackResidenzaProprietarioInserimentoColonia':
		callbackResidenzaProprietarioInserimentoColonia(dataObj);
		break;	
	case 'callbackResidenzaSedeLegaleCanile':
		callbackResidenzaSedeLegaleCanile(dataObj);
		break;
	default:
		alert('not managed');
	}
}



	    
</script>


<%
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
	response.setHeader("Expires", "-1");
	boolean globalItemsPaneHidden = "HIDE".equals(globalItemsPaneState);
%>
<html>
<head>
<%@ include file="../initPage.jsp"%>
<title><dhv:label name="templates.CentricCRM">Concourse Suite Community Edition</dhv:label><%=((!ModuleBean.hasName()) ? "" : ": " + ModuleBean.getName())%></title>
<%String cssToInclude = getServletContext().getContextPath();
cssToInclude = cssToInclude.substring(cssToInclude.indexOf("/")+1);
cssToInclude = "cssInclude_" +cssToInclude+".jsp";%>
<jsp:include page="<%=cssToInclude %>" flush="true" />
</head>
<script language="JavaScript" type="text/javascript"
	src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript"
	src="javascript/spanDisplay.js"></script>

<script language="JavaScript" type="text/javascript" src="javascript/customerSatisfaction.js"></script>	
	
<body leftmargin="0" rightmargin="0" margin="0" marginwidth="0"
	topmargin="0" marginheight="0">
	<DIV ID='modalWindow' CLASS='unlocked'>
		<P CLASS='wait'>Attendere il completamento dell'operazione...</P>
	</DIV>
<jsp:include page="customerSatisfaction.jsp"></jsp:include>

	<script type="text/javascript">
		$(function() {

			setInterval("do_ajax()", 60000);
		});
		function do_ajax() {

			$
					.ajax({
						url : "templates/messaggio_home.jsp",
						dataType : 'json',
						error : function() {

						},
						success : function(data) {
							console.log(data.msg);

							if (data.msg != null && data.msg != "null") {
								document.getElementById('load_me').innerHTML = data.msg;

							}

						}
					});
		}
		
	    $(document).ready(function(){
            	
            	checkBrowser();
            });
            
            function checkBrowser() { 
        		
                if(navigator.userAgent.indexOf("Firefox") != -1 ) 
               {
                  document.getElementById("browser-msg").style.display = 'none';
               }
               else {
            	   document.getElementById("browser-msg").style.display = '';
               } 
               
               }

	</script>


	<div id="load_me">


		<%@ include file="./avviso_messaggio_urgente.txt"%>
	</div>
	<div id="header" class="header_<%=ApplicationProperties.getProperty("AMBIENTE")%>">
		<table border="0" width="100%" cellpadding="2" cellspacing="0">
			<tr>
				<td valign="top">
					<table border="0" cellpadding="0" cellspacing="2">
						<tr>
							<td valign="top"><dhv:label name="logo">
									<img
										src="images_bdu/bdu_<%=ApplicationProperties.getProperty("AMBIENTE")%>.png"
										align="absMiddle" border="0" />
								</dhv:label></td>
						</tr>
					</table>
				</td>
				<th align="right" valign="top" nowrap><img
					src="images/icons/stock_help-16.gif" border="0" align="absmiddle"
					height="16" width="16" /> <%
 	String includeModule = (String) request.getAttribute("IncludeModule");
 %> <%
 	String pathGuida = application.getRealPath("guida");
 	String fileGuidaAtteso = "";
 	String fileGuida = "";//includeModule.split("/")[1]+"_"+ includeModule.split("/")[2].substring(0,includeModule.split("/")[2].length()-3)+"html" ;
 	fileGuida = includeModule.split("/")[1];
 	File f = new File(pathGuida + "/sezione", fileGuida);
 %> <a
					href="javascript:popURL('<%="guida_2016/guida.jsp?link=" + fileGuida.replaceAll(" ", "_")%>','CRM_Help','1000 ','500','yes','yes');"
					class="s" onMouseOver="window.status='Pop-up Help';return true;"
					onMouseOut="window.status='';return true;"
					title="<%=fileGuidaAtteso%>">Guida Utente</a> | 
<%
					if(false)
					{
%>
						<a href="Login.do?command=LoginCanina" target="_blank">Canina - vecchio sistema</a> | <a href="Login.do?command=LoginFelina" target="_blank">Felina - vecchio sistema</a> | 
				
					<a href="Login.do?command=LoginPortaleImportatori" target="_blank">Gestione
						Prelievo DNA</a>

<%
					}
%>							
						
						 <!--|  <a href="#" onclick="window.open('Login.do?command=LoginDigemon')"
					>Reportistica</a --> <br> <dhv:permission
						name="admin-view">
						<img src="images/icons/stock_form-properties-16.gif" border="0"
							align="absmiddle" height="16" width="16" />
						<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a>
      |</dhv:permission> <img src="images/icons/stock_exit-16.gif" border="0"
					align="absmiddle" height="16" width="16" /> <a
					href="Login.do?command=Logout" class="s">Esci da BDU</a> |<img
					src="images/icons/stock_about-16.gif" border="0" align="absmiddle"
					height="16" width="16" /> <dhv:evaluate
						if='<%=!User.getUserRecord().getContact().getNameFirstLast().equals("")%>'>
						<br />
						<%
							if (User.getActualUserId() != User.getUserId()) {
						%>
						<dhv:label name="admin.userAliasedTo"
							param='<%="contactName=" + toHtml(User.getUserRecord().getContact().getNameFirstLast())%>'>User Aliased To <span
								class="highlight"><%=toHtml(User.getUserRecord().getContact().getNameFirstLast())%></span> /</dhv:label>
						<%
							} else {
						%>
						<dhv:label name="admin.user.colon"
							param='<%="contactName=" + toHtml(User.getUserRecord().getContact().getNameFirstLast())%>'>User: <span
								class="highlight"><%=toHtml(User.getUserRecord().getContact().getNameFirstLast())%></span> /</dhv:label>
						<%
							}
						%>
					</dhv:evaluate> <b class="highlight"><%=User.getRole()%></b> <dhv:evaluate
						if="<%=User.getUserRecord().getManagerUser() != null
						&& User.getUserRecord().getManagerUser().getContact() != null%>">
						<br />
						<dhv:label name="admin.manager.colon"
							param='<%="managerName=" + toHtml(User.getUserRecord().getManagerUser().getContact().getNameFull())%>'>Manager: <span
								class="highlight"><%=toHtml(User.getUserRecord().getManagerUser().getContact().getNameFull())%></span>
						</dhv:label>
					</dhv:evaluate> <dhv:evaluate
						if='<%=System.getProperty("DEBUG") != null && "2".equals(System.getProperty("DEBUG"))
						&& request.getAttribute("debug.action.time") != null%>'>
						<br />
						<dhv:permission name="admin-usage-view">
          Users logged in: <span class="highlight"><%=User.getSystemStatus(getServletConfig()).getTracker().getUserCount()%></span>
						</dhv:permission>
						<dhv:permission name="website-view">
          Website visitors: <span class="highlight"><%=User.getSystemStatus(getServletConfig()).getTracker().getGuestCount()%></span>
						</dhv:permission>
						<dhv:label name="admin.actionTook.colon"
							param='<%="time=" + request.getAttribute("debug.action.time")%>'>Action took:</dhv:label>
					</dhv:evaluate>
					
		<div id="utentiConnessi" >
		<span style="text-align: center;display:block; background-color:#F0F9FC ; width:240px;border:1px solid black;font-weight:normal;">Utenti connessi: <b><%@ include file="../utentiConnessi.jsp"%></b> &nbsp; &nbsp; </span>
		</div>
					
					</th>
			</tr>
		</table>
		
		
		
		
		<P id="browser-msg"
			style="display: none; color: red; text-align: right !important;">
			Attenzione! L'utilizzo di browser diverso da Firefox può generare
			problemi</P>
	</div>
	<!-- Main Menu -->
	<div id="topmenutabs">
		<table border="0" width="100%" cellspacing="0" cellpadding="0"
			class="menuCC_<%=ApplicationProperties.getProperty("AMBIENTE")%>">
			<tr>
				<%=request.getAttribute("MainMenuTops")%>
			</tr>
			<tr>
				<%=request.getAttribute("MainMenu")%>
			</tr>
		</table>
	</div>
	<!-- Sub Menu -->
	<div id="header">
		<table border="0" width="100%" cellspacing="0" class="submenu">
			<tr>
				<td width="100%">
					<table border="0" cellspacing="0" class="submenuItem">
						<tr>
							<td width="5"><font size="1">&nbsp;</font></td>
							<%
								Iterator i = ModuleBean.getMenuItems().iterator();
								while (i.hasNext()) {
									SubmenuItem thisItem = (SubmenuItem) i.next();
									if ("".equals(thisItem.getPermission())
											|| User.getSystemStatus(getServletConfig()).hasPermission(User.getRoleId(),
													thisItem.getPermission())) {
							%>
							<td><%=(thisItem.getAlternateHtml(User.getSystemStatus(getServletConfig())))%></td>
							<td width="10">&nbsp;</td>
							<%
								}
								}
							%>
							<td><font size="1">&nbsp;</font></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<table border="0" width="100%" cellpadding="0" cellspacing="0"
		class="layoutPane">
		<tr>
			<td valign="top" width="100%" class="contentPane">
				<!-- The module goes here --> <jsp:include
					page="<%=includeModule%>" flush="true" /> <!-- End module -->
			</td>

		</tr>
	</table>
	<div id="footer">
		<br />
		<center>
			<zeroio:tz timestamp="<%=new java.util.Date()%>"
				timeFormat="<%=DateFormat.LONG%>"
				timeZone="<%=User.getTimeZone()%>" />
			<br /> &#169; Copyright 2000-2007 Concourse Suite Community Edition
			&#149;
			<dhv:label name="global.label.allRightsReserved">All rights reserved.</dhv:label>
			<br />
	</div>
	<%-- Allow pages have to have a scrollTo... must be at end of html --%>
	<script language="JavaScript" type="text/javascript"
		src="javascript/scrollReload.js"></script>
	<dhv:evaluate if='<%=request.getParameter("scrollTop") != null%>'>
		<script language="JavaScript" type="text/javascript">
			if (window.scrollTo)
				window.scrollTo(
		<%=request.getParameter("scrollLeft")%>
			,
		<%=request.getParameter("scrollTop")%>
			);
		</script>
	</dhv:evaluate>


	<script>
		dwr.engine.setErrorHandler(errorHandler);
		dwr.engine.setTextHtmlHandler(errorHandler);

		function errorHandler(message, exception) {
			//Session timedout/invalidated
			if (exception
					&& exception.javaClassName == 'org.directwebremoting.extend.LoginRequiredException') {
				alert(message);
				//Reload or display an error etc.
				window.location.href = window.location.href;
			} else
				alert('Errore Nella Chiamata Remota : '
						+ exception.javaClassName);
		}
 
 		$('a').click(
				function() {
					// do something exciting with each div
					// alert('1');
					if ((this.href.indexOf('#') == -1 
							&& this.href.indexOf('javascript:') == -1)
							&& (this.href.indexOf('GestioneDocumenti') == -1)
							&& (this.href.indexOf('GestioneAllegati') == -1)
							&& this.href.indexOf('vecchio') == -1
							&& this.id!="elencocomunicoinvolti"
					
					
					) {
						loadModalWindow();
						//  alert('2');
					}

				});  

 		$('form').submit(function() {

			// tasks to do 
			loadModalWindow();

		});  



 $(document).ready(function() {
	
	
	 if (document.forms[0]!=null){
// 		 alert(document.forms[0].getAttribute("onsubmit"))
		 var func = document.forms[0].onsubmit;
 		document.forms[0].onsubmit=function(event){
 			setTimestampStartRichiesta();
 		//	alert(func);
 			if(func!=null)
 				return func();
 			};
 		
	 }

 		 var oInput=document.createElement("INPUT");
 		oInput.setAttribute("type","hidden");
 		oInput.setAttribute("name","endTime");
 		oInput.setAttribute("id","endTime");
 		oInput.setAttribute("value",new Date().getTime());
 		document.getElementById("dialogCustomerSatisfaction").appendChild(oInput);
 		calcolaTempoEsecuzione(document.getElementById('iniTime').value,document.getElementById('endTime').value);

 });
 </script>

<%if (request.getAttribute("customerSatisfaction")!=null){ %>
<script>openCustomerSatisfaction();</script>
<%} %>

</body>
</html>
