<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.controller.SubmenuItem,java.text.DateFormat,java.util.Iterator" %>
<%


  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
  response.setHeader("Expires", "-1");
  response.setDateHeader("Expires", 0);

%>
<link rel="shortcut icon" href="images/gisa_favicon.ico" />  
<%@page import="java.io.File"%><html>
<head>
<%@ include file="../initPage.jsp" %>

<title><dhv:label name="templates.CentricCRM">Centric CRM</dhv:label></title>
 
 <script>
function openPopupGisaReport(link)
{

	window.open(link);

}
var codiceInternoTipoIspezione ;
var flagCondizionalitaReturn ;

function getCodiceInternoTipoIspezioneCallback1(codInterno)
{
	codiceInternoTipoIspezione = codInterno.codiceInterno;
	
	
	}
	
function getCodiceInternoTipoPianoCallback1(codInterno)
{
	codiceInternoTipoIspezione = codInterno.codiceInterno;
	
		flagCondizionalitaReturn = codInterno.flagCondizionalita;
	
	
	}
function getCodiceInternoTipoIspezione(value)
{
	PopolaCombo.getCodiceInternoTipoIspezione(value, {callback:getCodiceInternoTipoIspezioneCallback1,async:false } );

	
	
	}
	
	
function getCodiceInternoTipoPiano(value)
{

	PopolaCombo.getCodiceInternoTipoPiano(value, {callback:getCodiceInternoTipoPianoCallback1,async:false } );

	
	
	}
	var timeIni = new Date().getTime();
function setTimestampStartRichiesta()
{
	

		
		
	 var oInputIni=document.createElement("INPUT");
		oInputIni.setAttribute("type","hidden");
		oInputIni.setAttribute("name","TimeIni");
		oInputIni.setAttribute("id","TimeIni");
		oInputIni.setAttribute("value",new Date().getTime());
		
		var oInputIni2=document.createElement("INPUT");
		oInputIni2.setAttribute("type","hidden");
		oInputIni2.setAttribute("name","TimeIni");
		oInputIni2.setAttribute("value",timeIni);
		oInputIni2.setAttribute("class","exclude_from_json");

		var d = new Date();
		if(document.forms[0]!=null)
			document.forms[0].appendChild(oInputIni2);

		
		
		document.getElementById("dialogCustomerSatisfaction").appendChild(oInputIni);
	
		var url_= location.href;

		url_nuovo = new Array();
		url_nuovo=url_.split("?command=");
		url_ = url_nuovo[0];

		if (url_nuovo.length>1)
			commandold = url_nuovo[1].split("&")[0];
		else
			commandold =  "Default";
		urlold_ =url_.split("/")[url_.split("/").length-1];
		var oInputIni3=document.createElement("INPUT");
		oInputIni3.setAttribute("type","hidden");
		oInputIni3.setAttribute("name","commandOld");
		oInputIni3.setAttribute("id","commandOld");
		oInputIni3.setAttribute("class","exclude_from_json");
		oInputIni3.setAttribute("value",urlold_+';'+commandold);
		if (document.forms!=null)
			for (i = 0 ; i <document.forms.length;i++ )
				document.forms[i].appendChild(oInputIni3)
	
    
}


function svuotaCache(){
	var randomInt = Math.random(); 

		if(!window.location.hash) {
			if (location.href.indexOf("command") > -1)
				{
				if (location.href.indexOf("&loaded") > -1)
		   	 		location.href = location.href.substring(0, location.href.indexOf("&loaded")) + '&loaded='+randomInt;
				else
					location.href = location.href + '&loaded='+randomInt;
		}
		   
		}
		
	
}

</script>
 <jsp:include page="cssInclude.jsp" flush="true"/>
 
 
 <link rel="stylesheet" type="text/css" href="css/colore_demo.css"></link>	
<link rel="stylesheet" type="text/css" href="css/demo.css"></link>		
<link rel="stylesheet" type="text/css" href="css/custom.css"></link>	
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<link rel="stylesheet" type="text/css" href="css/capitalize.css"></link>		

<script src='javascript/modalWindow.js'></script>
<script src='javascript/jquerymini.js'></script>	
<script   src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />
</head>



<script language="JavaScript" type="text/javascript" src="javascript/globalItemsPane.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/scrollReload.js"></script>



<body leftmargin="0" rightmargin="0" margin="0" marginwidth="0" topmargin="0" marginheight="0" >







<div id="header" style="background-image: url(images/header_apistica.jpg);height: 175px;width: 100%;background-repeat:no-repeat;">


      
      
      
<table border="0" width="100%" cellpadding="2" cellspacing="0" >
  <tr>

    <th align="right" valign="top" nowrap>
      <img src="images/icons/refresh.png" border="0" align="absmiddle" height="16" width="16" />
    
	   <a href="#" >Refresh</a>  
      <br>
      
    
    </th>
  </tr>
</table>


</div>
<!-- Main Menu -->
<div id="topmenutabs">
<table  width="100%" cellspacing="0" cellpadding="0" class = "delimiter"  >
 
  <tr class="rowimg">
    
   
   
  </tr>
</table>
</div>
<!-- Sub Menu -->
<div id="header">
<table border="0" width="100%" cellspacing="0" class="submenu">
  <tr>
    <td width="100%">
      <table border="0" cellspacing="0" class="submenuItem">
        
      </table>
    </td>
  </tr>
</table>
</div>

<table border="0" width="100%" cellpadding="0" cellspacing="0" class="layoutPane">
  <tr>
    <td valign="top" width="100%" class="contentPane">
      <!-- The module goes here -->
    
      body
      
      <!-- End module -->
    </td>
  
  </tr>
</table>
<div id="footer">

</div>

</body>
</html>



