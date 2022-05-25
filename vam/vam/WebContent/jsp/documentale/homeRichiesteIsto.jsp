<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript" src="js/jquery/jquery-ui-1.7.3.custom.min.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.jqprint-0.3.js"></script>
		<script type="text/javascript" src="js/jquery/tooltip.min.js"></script>
		<script type="text/javascript" src="js/jquery/jquery-us.js"></script>
		<link rel="stylesheet" type="text/css" href="css/redmond/jquery-ui-1.7.3.custom.css" />

<!--  input type="button" name="stampa" id="stampa" value="Stampa" onclick="javascript:window.print();" /-->

<input type="button" name="downloadPdf" id="downloadPdf" value="Stampa" onclick="javascript:sceltaStampa()"/>
<!-- <us:can f="AMMINISTRAZIONE" sf="MAIN" og="MAIN" r="w">-->
	<!-- <input type="button" name="gestionePdf" id="gestionePdf" value="Gestione Stampe" onclick="location.href='documentale.Lista.us?cc=${cc.id}&tipo=${tipo}&action='+location.href;" />-->
<!-- </us:can>-->

<div id="scelta_stampa" title="Selezionare la stampa">
	
	 <table class="tabella">		
        <tr class='odd'>
    		<td>

    			<input type="button" name="downloadPdfNew" id="downloadPdfNew" value="Nuovo Documento" 
	    					onclick="location.href='documentale.DownloadNewPdf.us?tipo=${tipo}&idEsame=${idEsame}&glifo='+document.getElementById('glifo').checked;"
					
			 	/><input type="checkbox" name="glifo" id="glifo" value="glifo" style="display:none;"><!--  input type="text" disabled value="Glifo"/-->
    			<!-- 
    			<input type="button" name="downloadPdfLast" id="downloadPdfLast" value="Ultimo Documento Generato" onclick="location.href='documentale.DownloadLastGenerate.us?tipo=${tipo}';"
    			/> -->
    			
    		</td>
        </tr>
        
      </table>
 </div>

<script type="text/javascript">

$(function() 
		{
			$( "#scelta_stampa" ).dialog({
				height: screen.height/4,
				modal: true,
				autoOpen: false,
				closeOnEscape: true,
				show: 'blind',
				resizable: true,
				draggable: true,
				width: screen.width/3,
				buttons: {
					"Chiudi": function() {
						$( this ).dialog( "close" );
					}
				}
		});
});

function sceltaStampa()
{
$( '#scelta_stampa' ).dialog( 'open' );
}

</script>











<%--><script type="text/javascript" src="js/jquery/jquery-ui-1.7.3.custom.min.js"></script>
		<script type="text/javascript" src="js/jquery/jquery.jqprint-0.3.js"></script>
		<script type="text/javascript" src="js/jquery/tooltip.min.js"></script>
		<script type="text/javascript" src="js/jquery/jquery-us.js"></script>
		<link rel="stylesheet" type="text/css" href="css/redmond/jquery-ui-1.7.3.custom.css" />
		
		
<script type="text/javascript">
function catturaHtml(form){
	//popolaCampi();
	h=document.getElementsByTagName('html')[0].innerHTML;
	form.htmlcode.value = h;
	}</script>
	
<input type="button" name="downloadPdf" id="downloadPdf" value="Stampa" onclick="javascript:sceltaStampa()"/>

<div id="scelta_stampa" title="Selezionare la stampa">
	
	 <table class="tabella">		
        <tr class='odd'>
    		<td>

    		<form name="gestionePdf" action="documentale.DownloadNewPdf.us" method="POST">
    		<input type="button" id ="downloadPdfNew" value="Nuovo Documento" 	
    		onClick="catturaHtml(this.form); this.form.submit()" />
    		<input type="checkbox" name="glifo" id="glifo" value="glifo"><input type="text" disabled value="Glifo"/>
    		<input type="hidden" name="idEsame" id="idEsame" value="${idEsame}"></input>
    		<input type="hidden" name="tipo" id="tipo" value="${tipo}"></input>
    		<input type="hidden" name="htmlcode" id="htmlcode" value=""></input>
    		</form>
    			<input type="button" name="downloadPdfLast" id="downloadPdfLast" value="Ultimo Documento Generato" onclick="location.href='documentale.DownloadLastGenerate.us?tipo=${tipo}';"
    			/>
    			
    		</td>
        </tr>
        
      </table>
 </div>

<script type="text/javascript">

$(function() 
		{
			$( "#scelta_stampa" ).dialog({
				height: screen.height/4,
				modal: true,
				autoOpen: false,
				closeOnEscape: true,
				show: 'blind',
				resizable: true,
				draggable: true,
				width: screen.width/3,
				buttons: {
					"Chiudi": function() {
						$( this ).dialog( "close" );
					}
				}
		});
});

function sceltaStampa()
{
$( '#scelta_stampa' ).dialog( 'open' );
}

</script>--%>