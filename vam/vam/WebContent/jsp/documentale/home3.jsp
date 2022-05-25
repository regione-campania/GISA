<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>

<!--  input type="button" name="stampa" id="stampa" value="Stampa" onclick="javascript:window.print();" /-->

<c:if test="${tipo=='stampaDecesso'}">
<input type="button" name="downloadPdf2" id="downloadPdf2" value="Stampa Certificato Decesso" onclick="javascript:sceltaStampa2()"/>
</c:if>
<!--<us:can f="AMMINISTRAZIONE" sf="MAIN" og="MAIN" r="w">-->
	<!--<input type="button" name="gestionePdf2" id="gestionePdf2" value="Gestione Stampe" onclick="location.href='documentale.Lista.us?cc=${cc.id}&tipo=${tipo}&action='+location.href;" />-->
<!--</us:can>-->

<div id="scelta_stampa2" title="Selezionare la stampa">
	
	 <table class="tabella">		
        <tr class='odd'>
    		<td>

    			<input type="button" name="downloadPdfNew2" id="downloadPdfNew2" value="Nuovo Documento" 
	    			<c:if test="${tipo=='stampaDecesso'}">
						onclick="location.href='documentale.DownloadNewPdf.us?tipo=${tipo}&glifo='+document.getElementById('glifo2').checked+'&idAccettazione='+${cc.accettazione.id};"
					</c:if>
    			/><input type="checkbox" name="glifo2" id="glifo2" value="glifo" style="display:none;"><!-- input type="text" disabled value="Glifo"/-->
    			<!-- 
    			<input type="button" name="downloadPdfLast2" id="downloadPdfLast2" value="Ultimo Documento Generato" onclick="location.href='documentale.DownloadLastGenerate.us?tipo=${tipo}&idAccettazione=${accettazione.id}&idCc=${cc.id}';"
    			/> -->
    			
    		</td>
        </tr>
        
      </table>
 </div>

<script type="text/javascript">

$(function() 
		{
			$( "#scelta_stampa2" ).dialog({
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

function sceltaStampa2()
{
$( '#scelta_stampa2' ).dialog( 'open' );
}

</script>