<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>

<!DOCTYPE html>


<%@page import="it.us.web.bean.BUtente"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="it" lang="it">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=windows-1252" />
		
		<!-- inclusioni per jmesa -->
		<link rel="stylesheet" type="text/css" href="css/jmesa/jmesa.css" />
		<script type="text/javascript" src="js/jmesa/jquery-1.3.min.js"></script>
		<script type="text/javascript" src="js/jmesa/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="js/jmesa/jmesa.js"></script>
		<script type="text/javascript" src="js/jmesa/jmesa.min.js"></script>
		<script type="text/javascript" src="js/jmesa/jquery.jmesa.js"></script>
		
		<script type="text/javascript">
		
		
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

		
		
		
            $(function(){
                            setInterval( "do_ajax()", 60000 );
            });
            function do_ajax(){
            $.ajax({
                                url : "jsp/messaggi/messaggio_home.jsp",
                dataType : 'json',
                error : function() {


                },
                success : function(data) {
                   console.log(data.msg);

                   if (data.msg!=null && data.msg!="null")
                   {
                       document.getElementById('load_me').innerHTML=data.msg;

                   }

                }
            });
            }

</script>
		
<!--		<script type="text/javascript" src="js/jquery/jquery-1.3.2.min.js"></script>-->
		<script type="text/javascript" src="js/jquery/jquery-ui-1.7.3.custom.min.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.jqprint-0.3.js"></script>
		<script type="text/javascript" src="js/jquery/tooltip.min.js"></script>
		<script type="text/javascript" src="js/jquery/jquery-us.js"></script>

		<!-- link rel="stylesheet" type="text/css" href="css/black-tie/jquery-ui-1.7.3.custom.css" /-->
		<!-- link rel="stylesheet" type="text/css" href="css/custom-theme/jquery-ui-1.7.3.custom.css" /-->
		<!-- link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui-1.7.3.custom.css" /-->
		<link rel="stylesheet" type="text/css" href="css/redmond/jquery-ui-1.7.3.custom.css" />
		
		<link rel="stylesheet" type="text/css" href="css/vam/template_css.css" />
		<link rel="stylesheet" type="text/css" href="css/aqua/theme.css" />
		<script type="text/javascript" src="js/amministrazione/permessi.js"></script>
		<script type="text/javascript" src="js/azionijavascript.js"></script>
		<script type="text/javascript" src="js/date.js"></script>
		<script type="text/javascript" src="js/calendario/calendar.js"></script>
		<script type="text/javascript" src="js/calendario/calendar-setup.js"></script>
		<script type="text/javascript" src="js/calendario/calendar-it.js"></script>
		<title>V.A.M.</title>
		
	</head>
	
  <body class="white" >
  

    
    <div id="dialog-modal" title="Attendere" style="display: none;">
		<p>
			<br />
			<img src="images/loader.gif" />
		</p>
	</div>
	

	
	<c:if test="${errore != null || messaggio != null}">
		<jsp:include page="default/errore-messaggio-popup.jsp" />
	</c:if>
	
    <div  class="header_<%=config.getServletContext().getAttribute("ambiente")%>" >
    	<tiles:insertAttribute name="header" />
    	
    		  		<P id="browser-msg"
			style="display: none; color: red; text-align: right !important;">
			Attenzione! L'utilizzo di browser diverso da Firefox può generare
			problemi</P>
    	
    </div>
    
    <div id="contentBody">
    	<tiles:insertAttribute name="menu" />
	</div>
	
	<font color="red" size="2">
	<div id="load_me">
		<jsp:include page="../messaggi/messaggio.txt" />
	</div>
</font>

		<div class="margine">
		    <div id="attendere-trick">
				<div class="ui-widget-overlay" style="width: 100%; height: 100%; z-index: 1001; position: fixed;"></div>
				<div style="position: absolute;top: 0;left: 0;width: 100%;height: 100%;">
					<div style="display: block; position: relative; overflow: hidden; z-index: 1002; outline: 0px; height: auto; width: 250px; top: 244.5px; margin: 0 auto; " class="ui-dialog ui-widget ui-widget-content ui-corner-all " tabindex="-1" role="dialog" aria-labelledby="ui-dialog-title-dialog-modal">
						<div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" unselectable="on"><span class="ui-dialog-title" id="ui-dialog-title-dialog-modal" unselectable="on">Attendere</span><a href="#" class="ui-dialog-titlebar-close ui-corner-all" role="button" unselectable="on"><span class="ui-icon ui-icon-closethick" unselectable="on">close</span></a></div><div id="dialog-modal" style="height: 95px; min-height: 105px; width: auto; " class="ui-dialog-content ui-widget-content">
							<p>
								<br>
								<img src="images/loader.gif">
							</p>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				jQuery(document).ready(function(){
					$('#attendere-trick').text('');
				});
			</script>
			<us:err classe="errore" />
	 		<us:mex classe="messaggio" />
			<tiles:insertAttribute name="body" />
		</div>
				
	<div id="footer">
		<div class="padding">
             <div class="moduletable">
				<tiles:insertAttribute name="footer" />
			</div>
		</div>
	</div>
					
  </body>

</html>
