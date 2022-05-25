<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />

<%@ page contentType="text/html; charset=windows-1252" language="java"
	errorPage=""%>

<!DOCTYPE html>


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="it" lang="it">
<head>
<meta http-equiv="content-type" content="text/html; charset=windows-1252" />
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>

<script type="text/javascript" src="javascript/jquery.selectbox-0.2.js"></script>
<link href="css/jquery.selectbox.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<jsp:include page="cssInclude.jsp" flush="true"/>
<link rel="stylesheet" type="text/css" href="css/capitalize.css"></link>	
<script   src="javascript/jquery-ui.js"></script>
<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery.validate.js"></script>

<!-- <link rel="stylesheet" href="css/suap.css"> -->
<!-- <link rel="stylesheet" href="css/stilisuap.css"> -->
<style type="text/css">
 #basicModal{
            display:none;
        }
</style>
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/SuapDwr.js"> </script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/Geocodifica.js"> </script>

<!-- <link rel="stylesheet" href="css/normalize.css"> -->
<link rel="stylesheet" href="css/main.css">


<link rel="stylesheet" type="text/css" href="css/suap/template_css.css" />
<link rel="stylesheet" type="text/css" href="css/suap/theme.css" />
<script>
function goTo(link){
	
	if (link=='')
		alert('da implementare');
	else{
		loadModalWindow();
		window.location.href=link;
	}
	
	return false;
}

function showAlert(testo){
	$( "#basicModal" ).html(testo );

	$( "#basicModal" ).dialog({
	    modal: true,
	    title: "ATTENZIONE!",
	    width:600,
	    buttons: {
	        
	        "OK": function() {
	            $( this ).dialog( "close" );
	        }
	    }
	});
}
</script>

<script>
function openNewErrataCorrige(url){
	var idControllo = '';
	var metodo = '<%=request.getAttribute("javax.servlet.forward.request_uri") %>';
	var queryString = '<%=request.getQueryString()%>';
	var contesto = metodo+queryString;
	
	if (contesto.includes("Vigilanza"))
		idControllo = '<%=request.getParameter("id")%>';
	
	loadModalWindow();
	url = url + "&idControllo="+idControllo+"&contesto="+ encodeURIComponent(contesto);
	window.location.href=url;

}

</script>

<title>S.U.A.P</title>

</head>

<body class="white">
<div id="basicModal">
  
</div>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
	<%@ include file="../initPage.jsp"%>



	<div class="header_trasportatori"></div>

	<div id="contentBody">
		<jsp:include page="menu_trasportatori_distributori.jsp" flush="true" />
	</div>



	<div class="margine">



		<% 
String includeModule = (String) request.getAttribute("IncludeModule"); 

      %>

		<jsp:include page="<%= includeModule %>" flush="true" />
	</div>

	<div id="footer">
		<div class="padding">
			<div class="moduletable">
				<jsp:include page="footer.jsp" flush="true" />
			</div>
		</div>
	</div>

</body>

</html>

