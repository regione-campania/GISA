<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>

<input type="button" name="downloadPdf2" id="downloadPdf2" value="Stampa Richiesta" onclick="javascript:sceltaStampaRichiesta()"/>

<div id="scelta_stampaRichiesta" title="Selezionare la stampa">
	
	 <table class="tabella">		
        <tr class='odd'>
    		<td>

    			<input type="button" name="downloadPdfNew2" id="downloadPdfNew2" value="Nuovo Documento" 
						onclick="location.href='documentale.DownloadNewPdf.us?tipo=stampaRichiesta&glifo='+document.getElementById('glifo2').checked+'&idEsame='+${a.id};"
    			/><input type="checkbox" name="glifo2" id="glifo2" value="glifo" style="display:none;"><!-- input type="text" disabled value="Glifo"/-->
    			<!-- 
    			<input type="button" name="downloadPdfLast2" id="downloadPdfLast2" value="Ultimo Documento Generato" onclick="location.href='documentale.DownloadLastGenerate.us?tipo=${tipo}&idEsame=${a.id}';"
    			/> -->
    			
    		</td>
        </tr>
        
      </table>
 </div>

<script type="text/javascript">

$(function() 
		{
			$( "#scelta_stampaRichiesta" ).dialog({
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

function sceltaStampaRichiesta()
{
$( '#scelta_stampaRichiesta' ).dialog( 'open' );
}

</script>