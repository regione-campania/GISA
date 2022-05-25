<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<table>
<tr ><td colspan="4">
<br>
<label><b><font size="2">Verifica Esistenza stabilimento</b></label><br>
Num.registrazione 852: &nbsp;&nbsp;<input type="text" value="" id="num_registrazione" size="40"/>&nbsp;&nbsp;
<input type="button" id="invia" value="PROSEGUI" onclick="verificaNumRegistrazione()"/></font>
</td></tr>
</table>

<script>

$('#vecchioNumero').hide();

function intercettaPregresso(){
	$('#vecchioNumero').show();
	
}

function verificaNumRegistrazione(){
	if ($('#num_registrazione').val()==""){
		goTo('OpuStab.do?command=CaricaImport&pregresso=true');
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
               goTo('OpuStab.do?command=CaricaImport&pregresso=true&'); /*perche c'e' & finale ? */
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



