<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>

<!--  input type="button" name="stampa" id="stampa" value="Stampa" onclick="javascript:window.print();" /-->

<c:choose>
<c:when test="${tipo=='stampaRefertoNecro'}">
	<input type="button" name="downloadPdf" id="downloadPdf" value="Stampa Referto Necroscopico" onclick="javascript:sceltaStampa()"/>
</c:when>
<c:otherwise>
	<input type="button" name="downloadPdf" id="downloadPdf" value="Stampa" onclick="javascript:sceltaStampa()"/>
</c:otherwise>
</c:choose>


<!--<us:can f="AMMINISTRAZIONE" sf="MAIN" og="MAIN" r="w">-->
	<!--<input type="button" name="gestionePdf" id="gestionePdf" value="Gestione Stampe" onclick="location.href='documentale.Lista.us?cc=${cc.id}&tipo=${tipo}&action='+location.href;" />-->
<!--</us:can>-->

<div id="scelta_stampa" title="Selezionare la stampa">
	
	 <table class="tabella">		
        <tr class='odd'>
    		<td>

    			<input type="button" name="downloadPdfNew" id="downloadPdfNew" value="Nuovo Documento" 
	    			<c:if test="${tipo=='stampaCc'}">
						onclick="location.href='documentale.DownloadNewPdf.us?tipo=${tipo}&glifo='+document.getElementById('glifo').checked+'&divDiario='+document.getElementById('2').checked+'&divRic='+document.getElementById('3').checked+'&divList='+document.getElementById('9').checked+'&divTrasf='+document.getElementById('8').checked+'&divTerap='+document.getElementById('7').checked+'&divChir='+document.getElementById('6').checked+'&divDimis='+document.getElementById('5').checked+'&divDiagn='+document.getElementById('4').checked+'&divListUrine='+document.getElementById('10').checked+'&divListCoprologico='+document.getElementById('11').checked+'&divListDiagnosticaImmagini='+document.getElementById('12').checked+'&divListEsterni='+document.getElementById('13').checked+'&divListECG='+document.getElementById('14').checked+'&divListCitologico='+document.getElementById('15').checked+'&divListIsto='+document.getElementById('16').checked+'&divListNecro='+document.getElementById('17').checked+'&divAnamnesi='+document.getElementById('18').checked+'&divEsameObiettivoGenerale='+document.getElementById('19').checked+'&divEsameObiettivoParticolare='+document.getElementById('20').checked;"
					</c:if>
					<c:if test="${tipo=='stampaAcc'}">
						onclick="location.href='documentale.DownloadNewPdf.us?tipo=${tipo}&glifo='+document.getElementById('glifo').checked+'&idAccettazione='+${accettazione.id};"
					</c:if>
					<c:if test="${tipo=='stampaIstoMultiplo'}">
						onclick="location.href='documentale.DownloadNewPdf.us?tipo=${tipo}&glifo='+document.getElementById('glifo').checked;"
					</c:if>
					<c:if test="${tipo=='stampaIstoSingolo'}">
						onclick="location.href='documentale.DownloadNewPdf.us?tipo=${tipo}&idEsame=${idEsame}&glifo='+document.getElementById('glifo').checked;"
					</c:if>
					<c:if test="${tipo=='stampaRefertoNecro'}">
						onclick="location.href='documentale.DownloadNewPdf.us?tipo=${tipo}&idEsame=${idEsame}&glifo='+document.getElementById('glifo').checked;"
					</c:if>
			 	/><input type="checkbox" name="glifo" id="glifo" value="glifo" style="display:none;"><!--  input type="text" disabled value="Glifo"/-->
    			
    			<!--  
    			<input type="button" name="downloadPdfLast" id="downloadPdfLast" value="Ultimo Documento Generato" onclick="location.href='documentale.DownloadLastGenerate.us?tipo=${tipo}&idAccettazione=${accettazione.id}&idEsame=${idEsame}';"
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