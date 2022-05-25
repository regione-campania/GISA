<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcf.modules.controlliufficiali.base.Piano"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.vigilanza.base.Ticket"%>
<link rel="stylesheet" type="text/css" href="javascript/jquerypluginTableSorter/css/theme.css"></link>



<script>

$(function () {
	    
	
	
	
	 $( "#dialogVerifica" ).dialog({
	    	autoOpen: false,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"",
	        width:650,
	        height:300,
	        draggable: false,
	        modal: true,
	        buttons:{
	        	 "PROSEGUI" : function() { 
	        		 
	        		 if(document.forms['sceltaTipoAttivita'].tipoAttivita.value==2)
	        			 {
	        			 alert('ATTENZIONE, NON RISULTA NESSUA ATTIVITA DI APICOLTURA ASSOCIATA AL CODICE FISCALE DEL DELEGANTE.\nPER IL TIPO DI ATTIVITA \"PRODUZIONE PER COMMERCIALIZZAZIONE/APICOLTORE PROFESSIONISTA (DI CUI ALLA LEGGE 24 DICEMBRE 2004 N. 313)\" E\' NECESSARIA LA PRESENTAZIONE DELLA SCIA')
	        			 }
	        		 else
	        			 {
	        			 $( "#dialogVerifica" ).dialog('close');
	        			 loadModalWindowCustom("Caricamento in corso");
	        			 location.href='ApicolturaAttivita.do?command=CambiaCodiceFiscale&cf='+document.forms['sceltaTipoAttivita'].cf.value+'&opId='+document.forms['sceltaTipoAttivita'].opId.value;
	        			 
	        			 }
	        		 
	        	 },
	        	 "ESCI" : function() { $(this).dialog("close");}
	        	
	        },
	        show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	       
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
	 
  
});         
</script>

	<div id ="dialogVerifica">
		

<font><b>ATTENZIONE ! INDICARE IL TIPO DI ATTIVITà CHE SI INTENDE INSERIRE</b>
</font>
<br>
<br>
<form name="sceltaTipoAttivita">
<input type = "hidden" name = "opId" value="">
<input type = "hidden" name = "cf" value="">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		 <tr>
   		<tr>
		<td >TIPO DI ATTIVITA</td>
		<td>
			AUTOCONSUMO <input type="radio" name = "tipoAttivita" value = "1" checked="checked"> PRODUZIONE COMMERICALE <input type="radio" name = "tipoAttivita" value = "2">
			
		</td>
	</tr>

</table>
	 </form>
	</div>
		
		
	
	
	