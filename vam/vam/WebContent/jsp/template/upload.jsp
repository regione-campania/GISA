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
		
		<script src="js/jquery/fileUpload/jquery.min.js"></script>
		<script src="js/jquery/fileUpload/jquery.ui.widget.js"></script>
		<script src="js/jquery/fileUpload/tmpl.min.js"></script>
		<script src="js/jquery/fileUpload/load-image.min.js"></script>
		<script src="js/jquery/fileUpload/bootstrap-modal.min.js"></script>
		<script src="js/jquery/fileUpload/bootstrap-image-gallery.min.js"></script>
		<script src="js/jquery/fileUpload/jquery.iframe-transport.js"></script>
		<script src="js/jquery/fileUpload/jquery.fileupload.js"></script>
		<script src="js/jquery/fileUpload/jquery.fileupload-ui.js"></script>
		<script src="js/jquery/fileUpload/application.js"></script>
		<script src="js/jquery/fileUpload/jquery.xdr-transport.js"></script>
		
		<!-- inclusioni per jmesa -->
		<link rel="stylesheet" type="text/css" href="css/jmesa/jmesa.css" />
		<script type="text/javascript" src="js/jmesa/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="js/jmesa/jmesa.js"></script>
		<script type="text/javascript" src="js/jmesa/jmesa.min.js"></script>
		<script type="text/javascript" src="js/jmesa/jquery.jmesa.js"></script>
		
		
<!--		<script type="text/javascript" src="js/jquery/jquery-1.3.2.min.js"></script>-->
		<script type="text/javascript" src="js/jquery/jquery-ui-1.7.3.custom.min.js"></script>
		<script type="text/javascript" src="js/jquery/tooltip.min.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.jqprint-0.3.js"></script>
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
		
		<link rel="stylesheet" href="css/fileUpload/bootstrap.min.css">	
		<link rel="stylesheet" href="css/fileUpload/bootstrap-image-gallery.min.css">
		<link rel="stylesheet" href="css/fileUpload/jquery.fileupload-ui.css">
		<style type="text/css">
		.page-header {
		    background-color: #f5f5f5;
		    padding: 80px 20px 10px;
		    margin: 0 -20px 20px;
		    border: 1px solid #DDD;
		    -webkit-border-radius: 0 0 6px 6px;
		       -moz-border-radius: 0 0 6px 6px;
		            border-radius: 0 0 6px 6px;
		}
		</style>
		<!--[if lt IE 7]>
		<style type="text/css">
		.topbar {
		    position: absolute;
		}
		.topbar ul, .topbar .container {
		    padding-top: 10px;
		}
		.topbar li {
		    display: inline;
		    margin-right: 10px;
		}
		.topbar a:hover {
		    color: #fff;
		}
		.span16 {
		    display: inline;
		    float: left;
		    margin-left: 20px;
		}
		input {
		    display: inline;
		    width: auto;
		}
		/* The following is required for Modals to work on IE6 */
		.modal {
		    position: absolute;
		    top: 50%;
		    filter: none;
		}
		#gallery-modal.fullscreen {
		    overflow: hidden;
		}
		</style>
		<![endif]-->
		
	</head>
	
  <body class="white" >
  
  <!-- gallery-loader is the loading animation container -->
<div id="gallery-loader"></div>
<!-- gallery-modal is the modal dialog used for the image gallery -->
<div id="gallery-modal" class="modal hide fade">
    <div class="modal-header">
        <a href="#" class="close">&times;</a>
        <h3 class="title"></h3>
    </div>
    <div class="modal-body"></div>
    <div class="modal-footer">
        <a class="btn primary next">Successiva</a>
        <a class="btn info prev">Precedente</a>
        <a class="btn success download" target="_blank">Download</a>
    </div>
</div>
<script>
var fileUploadErrors = {
    maxFileSize: 'File is too big',
    minFileSize: 'File is too small',
    acceptFileTypes: 'Filetype not allowed',
    maxNumberOfFiles: 'Max number of files exceeded',
    uploadedBytes: 'Uploaded bytes exceed file size',
    emptyResult: 'Empty file upload result'
};
</script>
<script id="template-upload" type="text/html">
{% for (var i=0, files=o.files, l=files.length, file=files[0]; i<l; file=files[++i]) { %}
    <tr class="template-upload fade">
        <td class="preview"><span class="fade"></span></td>
        <td class="name">{%=file.name%}</td>
        <td class="size">{%=o.formatFileSize(file.size)%}</td>
        {% if (file.error) { %}
            <td class="error" colspan="2"><span class="label important">Error</span> {%=fileUploadErrors[file.error] || file.error%}</td>
        {% } else if (o.files.valid && !i) { %}
            <td class="progress"><div class="progressbar"><div style="width:0%;"></div></div></td>
            <td class="start">{% if (!o.options.autoUpload) { %}<button class="btn primary">Start</button>{% } %}</td>
        {% } else { %}
            <td colspan="2"></td>
        {% } %}
        <td class="cancel">{% if (!i) { %}<button class="btn info">Cancel</button>{% } %}</td>
    </tr>
{% } %}
</script>
<script id="template-download" type="text/html">
{% for (var i=0, files=o.files, l=files.length, file=files[0]; i<l; file=files[++i]) { %}
    <tr class="template-download fade">
        {% if (file.error) { %}
            <td></td>
            <td class="name">{%=file.name%}</td>
            <td class="size">{%=o.formatFileSize(file.size)%}</td>
            <td class="error" colspan="2"><span class="label important">Error</span> {%=Errors[file.error] || file.error%}</td>
        {% } else { %}
            <td class="preview">{% if (file.thumbnail_url) { %}
                <a href="{%=file.url%}" title="{%=file.name%}" rel="gallery"><img src="{%=file.thumbnail_url%}"></a>
            {% } %}</td>
            <td class="name">
                <a href="{%=file.url%}" title="{%=file.name%}" rel="{%=file.thumbnail_url&&'gallery'%}">{%=file.name%}</a>
            </td>
            <td class="size">{%=o.formatFileSize(file.size)%}</td>
            <td colspan="2"></td>
        {% } %}
        <td class="delete">
<%  
		if(request.getAttribute("readonly")==null)
		{
%>
            <button class="btn danger" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}" nome-file="{%=file.name%}">Elimina Immagine</button>
<%
		}
%>   
            <%-- <input type="checkbox" name="delete" value="1"> --%>
        </td>
    </tr>
{% } %}
</script>

    <div id="dialog-modal" title="Attendere">
		<p>
			<br />
			<img src="images/loader.gif" />
		</p>
	</div>
	
	<c:if test="${errore != null || messaggio != null}">
		<jsp:include page="default/errore-messaggio-popup.jsp" />
	</c:if>
	
	<div id="mex-modal" title="Attenzione">
		<p id="mex-modal-p" style="color: green">caricamento...</p>
	</div>
	
	<script type="text/javascript">
	$(function() {
		// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		//$( "#dialog:ui-dialog" ).dialog( "destroy" );

		$( "#mex-modal" ).dialog({
			//height: 140,
			modal: true,
			autoOpen: false,
			closeOnEscape: true,
			show: 'blind',
			resizable: false,
			draggable: false,
			//width: 350,
			buttons: {
				"Chiudi": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	});
</script>
	
    <div  class="header_<%=config.getServletContext().getAttribute("ambiente")%>" >
    	<tiles:insertAttribute name="header" />
    </div>
    
    <div id="contentBody">
    	<tiles:insertAttribute name="menu" />
	</div>

		<div class="margine">
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
