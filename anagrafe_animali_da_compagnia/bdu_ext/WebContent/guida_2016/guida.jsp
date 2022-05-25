<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="description" content="">
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>Guida Utente</title>
<link rel="alternate" type="application/rss+xml" title="frittt.com" href="feed/index.html">
<link href="http://fonts.googleapis.com/css?family=Raleway:700,300" rel="stylesheet"
        type="text/css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/prettify.css">
</head>
<body>
<div class="wrapper">
<nav>
 
  	<div class="pull-left">
    	<h1><a href="javascript:"><img src="img/icon.png" alt="" /> <span>Guida BDU</span></a></h1>
    </div>
    
    <div class="pull-right">
    </div>

</nav>
<header>
  <div class="container">
    <h2 class="lone-header">Guida Utente</h2>
  </div>
</header>
<section>
  <div class="container">
    <ul class="docs-nav">
    
    
	<%@ include file="capitoli/indice.jsp"%>
       
    </ul>

 <div class="docs-content">
    
    
    <%@ include file="capitoli/home.jsp"%>
    
    <%@ include file="capitoli/anagrafeanimali.jsp"%>
    
    <%@ include file="capitoli/registrazioni.jsp"%>
    
    <%@ include file="capitoli/proprietari.jsp"%>
    
    <%@ include file="capitoli/microchip.jsp"%>
    
    <%@ include file="capitoli/contributi.jsp"%>
    
     <%@ include file="capitoli/partitecommerciali.jsp"%>
     
     <%@ include file="capitoli/mcnonstandard.jsp"%>
     
     <%@ include file="capitoli/barcode.jsp"%>
     
     <%@ include file="capitoli/cessioni.jsp"%>
     
     <%@ include file="capitoli/ricercamicrochip.jsp"%>
     
     <%@ include file="capitoli/registrazionifuoriasl.jsp"%>
     
     <%@ include file="capitoli/operazionihd1.jsp"%>
     
     <%@ include file="capitoli/passaporti.jsp"%>
     
     <%@ include file="capitoli/canili.jsp"%>
     
      
  </div>
</section>
<section class="vibrant centered">
  <div class="">
    <h4>BDU -  Banca Dati Unificata</h4>
  </div>
</section>
<footer>
  <div class="">
    <p> &copy; Copyright. All Rights Reserved.</p>
  </div>
</footer>
</div>
<script src="js/jquery.min.js"></script> 
<script type="text/javascript" src="js/prettify/prettify.js"></script> 
<script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js?lang=css&skin=sunburst"></script>
<script src="js/layout.js"></script>
</body>
</html>

<script>
$(window).resize(function() {
	window.location.href=window.location.href;
	});
</script>
>