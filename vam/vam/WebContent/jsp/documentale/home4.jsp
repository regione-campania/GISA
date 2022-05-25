<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>

<!--  input type="button" name="stampa" id="stampa" value="Stampa" onclick="javascript:window.print();" /-->

<input type="button" name="downloadPdf" id="downloadPdf" value="Stampa Multipla" onclick="javascript:sceltaStampaAccMultipla()"/>
<!-- <us:can f="AMMINISTRAZIONE" sf="MAIN" og="MAIN" r="w">-->
	<!--<input type="button" name="gestionePdf" id="gestionePdf" value="Gestione Stampe" onclick="location.href='documentale.Lista.us?cc=${cc.id}&tipo=stampaAccMultipla&action='+location.href;" /> -->
<!--</us:can>-->

<div id="scelta_stampa_acc_multipla" title="Selezionare la stampa">
	
	 <table class="tabella">		
        <tr class='odd'>
    		<td>

    			<input type="button" name="downloadPdfNew" id="downloadPdfNew" value="Nuovo Documento" 
						onclick="location.href='documentale.DownloadNewPdf.us?tipo=stampaAccMultipla&idAccMultipla=${idAccMultipla}&glifo='+document.getElementById('glifo').checked;"
			 	/><input type="checkbox" name="glifo" id="glifo" value="glifo" style="display:none;"><!--  input type="text" disabled value="Glifo"/-->
    			<!-- 
    			<input type="button" name="downloadPdfLast" id="downloadPdfLast" value="Ultimo Documento Generato" onclick="location.href='documentale.DownloadLastGenerate.us?tipo=stampaAccMultipla&idAccMultipla=${idAccMultipla}&idEsame=${idEsame}';"
    			/> -->
    			
    		</td>
        </tr>
        
      </table>
 </div>

<script type="text/javascript">

$(function() 
		{
			$( "#scelta_stampa_acc_multipla" ).dialog({
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

function sceltaStampaAccMultipla()
{
$( '#scelta_stampa_acc_multipla' ).dialog( 'open' );
}

</script>