<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html>
<script>
function openPopup(url) {
	  title  = '_types';
	  width  =  '1000';
	  height =  '600';
	  resize =  'yes';
	  bars   =  'yes';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url, title, windowParams);
}
</script>
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
    	<h1><a href="javascript:"><img src="img/icon.png" alt="" /> <span>Guida GISA</span></a></h1>
    </div>
    
    <!-- div class="pull-right">
    	<a href="http://frittt.com/free-documentation-html-template-docweb" target="_blank" class="btn btn-download"><img src="img/download.png" width="25" alt="Download Free Documentation Template" /> Download Now</a>
    </div -->

</nav>
<header>
  <div class="container">
    <h2 class="lone-header">Guida Utente</h2>
  </div>
</header>
<section>
  <div class="container">
    <ul class="docs-nav">
    
    
	<%@ include file="capitoli/indice.html"%>
       
    </ul>

 <div class="docs-content">
    
    
    <%@ include file="capitoli/pagopa.html"%>
<hr style="height:2px;border-width:0;color:#26abe2;background-color:#26abe2">
    <%@ include file="capitoli/home.html"%>
    <%@ include file="capitoli/preaccettazione.html"%>
    <%@ include file="capitoli/reperibilitaasl.html"%>
    <%@ include file="capitoli/gestionenorme.html"%>
    <%@ include file="capitoli/gestionecu.html"%>
<hr style="height:2px;border-width:0;color:#26abe2;background-color:#26abe2">
    <%@ include file="capitoli/checklist.html"%>
    <%@ include file="capitoli/stampecampioni.html"%>
    <%@ include file="capitoli/gestioneesitocampioni.html"%>
    <%@ include file="capitoli/AMR.html"%>
    <%@ include file="capitoli/Invio_Dati_Acquacoltura.html"%>
	<%@ include file="capitoli/autoritacompetenti.html"%>
	<%@ include file="capitoli/allerte.html"%>
	<%@ include file="capitoli/cras.html"%>
	<%@ include file="capitoli/unitadicrisi.html"%>
	<%@ include file="capitoli/richiesteErrataCorrige.html"%>
	<%@ include file="capitoli/gestionestabilimenti.html"%>
	<%@ include file="capitoli/gestionescia.html"%>
	<%-- <%@ include file="capitoli/pratichesuap20.html"%> --%>
	<%@ include file="capitoli/gestionenoscia.html"%>
    <%@ include file="capitoli/anagraficastabilimento.html"%>
    <%@ include file="capitoli/anagraficastabilimenticampiestesi.html"%>
    <%@ include file="capitoli/anagraficastabilimentidatiaggiuntivi.html"%>
    <%@ include file="capitoli/aggiornalineepregresse.html"%>
    <%@ include file="capitoli/gestioneErrataCorrige.html"%>
    <%@ include file="capitoli/gestioneVariazione.html"%>
    <%@ include file="capitoli/trasferimentoSedeOperativa.html"%>
    <%@ include file="capitoli/cambiosedelegale.html"%>
    <%@ include file="capitoli/aggiuntalineapregressa.html"%>
    <%@ include file="capitoli/cessazioneufficio.html"%>
    <%@ include file="capitoli/aggiunta_codice_sinvsa.html"%>
    <%@ include file="capitoli/convergenza.html"%>
    
    <!-- %@ include file="capitoli/importdistributorimassivo.html"% -->
     <%@ include file="capitoli/altristabilimenti.html"%>
    <%@ include file="capitoli/molluschibivalvi.html"%>
    <%@ include file="capitoli/puntidisbarco.html"%>
    <%@ include file="capitoli/trasportoanimali.html"%>

<hr style="height:2px;border-width:0;color:#26abe2;background-color:#26abe2">
    <%@ include file="capitoli/sintesis.html"%> 
    <%@ include file="capitoli/macellazioni.html"%>
<hr style="height:2px;border-width:0;color:#26abe2;background-color:#26abe2">
    <%@ include file="capitoli/stabilimenti852.html"%>
    <%@ include file="capitoli/allevamenti.html"%>
    <%@ include file="capitoli/gestioneListeRiscontroNEW.html"%>
    <%@ include file="capitoli/gestioneListeRiscontro.html"%>
    
    <%@ include file="capitoli/strutturediriproduzione.html"%>
    <%@ include file="capitoli/acquedirete.html"%>
    <%@ include file="capitoli/gestoriacque.html" %>
    <%@ include file="capitoli/osm.html"%>
    <%@ include file="capitoli/operatori193.html"%>
    <%@ include file="capitoli/laboratorihaccp.html"%>
    <%@ include file="capitoli/aiequidi.html"%>
    <%@ include file="capitoli/macroareaiuv.html"%>
    <%@ include file="capitoli/buffer.html"%>
    <%@ include file="capitoli/dpat.html"%>
    <%@ include file="capitoli/dpatconfigurazione.html"%>
    <%@ include file="capitoli/registrotrasgressori.html"%>
    <%@ include file="capitoli/apicoltura.html"%>
    <%@ include file="capitoli/qualitaDatiAsl.html"%>
    <%@ include file="capitoli/flussodocumentale.html"%> 
     <%@ include file="capitoli/izsmibr.html"%>
      <%@ include file="capitoli/izsmpnaa.html"%>
     <%@ include file="capitoli/izsmolluschi.html"%>
     <%@ include file="capitoli/video.html"%>
     
          
      <%@ include file="capitoli/gestoriacque.html"%>
     
  </div>
</section>
<section class="vibrant centered">
  <div class="">
    <h4>GISA -  Gestione Integrata Servizi e Attività</h4>
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
