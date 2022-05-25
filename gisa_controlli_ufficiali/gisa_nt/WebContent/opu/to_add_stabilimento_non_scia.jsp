<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script language="JavaScript" TYPE="text/javascript" SRC="opu/to_add_stabilimento.js"></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>




<table>

<tr>


<td colspan="2" align= "center">
<input type="button" class="stitchedGreyBigButton" value="Aggiungi stabilimento pregresso" style="height:50px !important; width:350px !important;" onClick="intercettaPregresso()">
</td>

<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

<td>
<input type="button" class="darkGreyBigButton" value="Aggiungi attività mobile (Non SCIA)" style="height:50px !important; width:350px !important;" onClick="goTo('')">
</td>

<td>
<input type="button" class="darkGreyBigButton" value="Aggiungi altri operatori (Non SCIA)" style="height:50px !important; width:350px !important;"  onClick="showHide('altri1')">
</td>

</tr>
<tr ><td colspan="4">
<br>
<div id="vecchioNumero" name="vecchioNumero">
<label><b><font size="2">Verifica stabilimento</b></label><br>
Num. registrazione: &nbsp;&nbsp;<input type="text" value="" id="num_registrazione" size="40"/>&nbsp;&nbsp;
<input type="button" id="invia" value="Ricerca" onclick="verificaNumRegistrazione()"/></font>
</div>

</td></tr>
<tr>
<td colspan="4"></td>
<td name="altri1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Molluschi bivalvi" style="height:50px !important; width:350px !important;" onClick="goTo('MolluschiBivalvi.do?command=Add')">
</td>
</tr>

<tr>
<td colspan="4"></td>
<td name="altri1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Sperimentazione Animale" style="height:50px !important; width:350px !important;" onClick="goTo('OsAnimali.do?command=Add')">
</td>
</tr>

<tr>
<td colspan="4"></td>
<td name="altri1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Trasporto Animali" style="height:50px !important; width:350px !important;" onClick="goTo('TrasportoAnimali.do?command=ScegliRichiesta')">
</td>
</tr>

<tr>
<td colspan="4"></td>
<td name="altri1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Lab. HACCP" style="height:50px !important; width:350px !important;" onClick="goTo('LaboratoriHACCP.do?command=Add')">
</td>
</tr>

<tr>
<td colspan="4"></td>
<td name="altri1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Zone di controllo" style="height:50px !important; width:350px !important;" onClick="goTo('ZoneControllo.do?command=Add')">
</td>
</tr>

<!-- <tr> -->
<!-- <td colspan="4"></td> -->
<!-- <td name="altri1" style="visibility:hidden"> -->
<!-- <input type="button" class="lightGreyBigButton" value="Colonie feline" style="height:50px !important; width:350px !important;" onClick="goTo('')"> -->
<!-- </td> -->
<!-- </tr> -->

<tr>
<td colspan="4"></td>
<td name="altri1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Imbarcazioni" style="height:50px !important; width:350px !important;" onClick="goTo('Imbarcazioni.do?command=Add')">
</td>
</tr>

<tr>
<td colspan="4"></td>
<td name="altri1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Privati" style="height:50px !important; width:350px !important;" onClick="goTo('Operatoriprivati.do?command=Add')">
</td>
</tr>

<tr>
<td colspan="4"></td>
<td name="altri1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Operatori non presenti altrove" style="height:50px !important; width:350px !important;" onClick="goTo('OpnonAltrove.do?command=Add')">
</td>
</tr>

<tr>
<td colspan="4"></td>
<td name="altri1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Abusivi" style="height:50px !important; width:350px !important;" onClick="goTo('Abusivismi.do?command=Add')">
</td>
</tr>

<tr>
<td colspan="4"></td>
<td name="altri1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Punti di sbarco" style="height:50px !important; width:350px !important;" onClick="goTo('PuntiSbarco.do?command=Add')">
</td>
</tr>

<tr>
<td colspan="4"></td>
<td name="altri1" style="visibility:hidden">
<input type="button" class="lightGreyBigButton" value="Farmacie" style="height:50px !important; width:350px !important;" onClick="goTo('Parafarmacie.do?command=AddFcie')">
</td>
</tr>

</table>





<script>

$('#vecchioNumero').hide();

function intercettaPregresso(){
	$('#vecchioNumero').show();
	
}

function verificaNumRegistrazione(){
	if ($('#num_registrazione').val()==""){
		goTo('OpuStab.do?command=CaricaImport');
	}else{
		loadModalWindowCustom("Verifica Esistenza Num. Registrazione. Attendere");
		$.ajax({
            url : 'opu/verifica852.jsp', // Your Servlet mapping or JSP(not suggested)
            data : 'numRegistrazione='+$('#num_registrazione').val().trim(), 
            type : 'POST',
            dataType : 'text', // Returns HTML as plain text; included script tags are evaluated when inserted in the DOM.
            success : function(response) {
            	if(response.indexOf("NONPRESENTE") > -1 ){
                	//alert("Numero di registrazione 852 non trovato. Si procederà all'operazione.")
                	goTo('OpuStab.do?command=CaricaImport');
                }else{
                 	alert("ATTENZIONE! Esiste già uno stabilimento registrato 852 con il numero inserito. \nSe lo si vuole aggiungere a quelli di nuova gestione, bisogna fare richiesta di import, contattando l'HelpDesk.");
                        }
                loadModalWindowUnlock(); 
            },
            error : function(request, textStatus, errorThrown) {
                alert(errorThrown);
                loadModalWindowUnlock(); 
            }
        });
	   
	}
}

</script>



