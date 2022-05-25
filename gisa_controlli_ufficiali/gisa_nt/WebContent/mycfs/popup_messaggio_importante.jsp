<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>

function apriMessaggiImportanti()
{	
		 $( "#dialogMessaggi").dialog('open');
}

function closeDialogMessaggi(){
	  $('#dialogMessaggi').dialog('close');
}

$(function () {
	 
	 $( "#dialogMessaggi" ).dialog({
	    	autoOpen: true,
	        resizable: false,
	        closeOnEscape: false,
	       	title:"MESSAGGIO IMPORTANTE",
	        width:700,
	        height:'auto', 
	        position: 'center',
	        draggable: false,
	        modal: true,
	       show: {
	            effect: "bounce",
	            duration: 1000 
	        },
	        hide: {
	            effect: "drop",
	            duration: 1000
	        }
	    }).prev(".ui-dialog-titlebar").css("background","#FF9999");
});         

</script>

<!--  spostare prima della dialog se dà errore -->
<script>$('#dialogMessaggi').dialog('open');</script>  

<div id = "dialogMessaggi">
<center>

<font color="red">Attenzione. Sono presenti i seguenti messaggi non letti: </font><br/>
</center>

<%for (int i =0; i<5;i++){ %>
<b><%=i+1 %>. </b> Messaggio importante <%=i+1 %> <br/>
<%} %>

<center>
<input type="button" value="OK" onClick="closeDialogMessaggi()"/>
</center>
</div>

  
<input id="btnMessaggi" type="button" onClick="apriMessaggiImportanti()" value="Controlla Messaggi Importanti"/>

