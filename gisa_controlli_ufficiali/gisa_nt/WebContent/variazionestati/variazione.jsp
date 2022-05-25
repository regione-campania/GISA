<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>
function apriAggiungiVariazione(id, tipologia)
{

	loadModalWindow();
		
		$.ajax({
	    	type: 'POST',
	   		dataType: "html",
	   		cache: false,
	  		url: 'VariazioneStato.do?command=Add&popup=true',
	        data: { "id": id, "tipologia": tipologia} , 
	    	success: function(msg) {
	    		loadModalWindowUnlock();
	       		document.getElementById('aggiungiVariazione').innerHTML=msg ; 
	       		$('#aggiungiVariazione').dialog('open');
	   		},
	   		error: function (err, errore) {
	   			alert('ko '+errore);
	        }
			});
	
}


$(function () {
	$( "#aggiungiVariazione" ).dialog({
		autoOpen: false,
	    resizable: false,
	    closeOnEscape: true,
	   	title:"VARIAZIONE DI STATO <input type=\"button\" value=\"CHIUDI\" id=\"chiudi\" onclick=\"javascript:$('#aggiungiVariazione').dialog('close');\" />",
	    width:950,
	    height:750,
	    draggable: false,
	    modal: true
	   
	}).prev(".ui-dialog-titlebar");
	});
$(".ui-widget-overlay").live("click", function() {  $("#aggiungiVariazione").dialog("close"); } );	




function selezionaCu(indice, idcu){
	document.getElementById("idCu_"+indice).value=idcu;
}
function cancellaCu(indice){
	document.getElementById("idCu_"+indice).value='';
}

function checkForm(form, sizeLinee){
	var esito = true;
	var msg = "";
	
	var esitoSelect = false;
	var dataInizioAttivita = document.getElementById('dataInizioAttivita').value;
	
	
	for(var z=0; z<sizeLinee; z++){
	     sel = document.getElementById("operazione_"+z).value;
	     idcu = document.getElementById("idCu_"+z).value;
	     data  = document.getElementById("dataVariazione_"+z).value;
	     operazioneOrigine  = document.getElementById("idOperazioneOrigine_"+z).value;
	     
	     if (sel!="-1")
	    	 esitoSelect = true;
	     
	     if (sel!="-1" && data==''){
	 		esito = false;
	 		msg+= "Data variazione obbligatoria.\n";
	 	}
	     
	     if (sel!="-1" && dataInizioAttivita!=null && dataInizioAttivita!='null' && dataInizioAttivita!='' && giorni_differenza(data,dataInizioAttivita)>0){
		 		esito = false;
		 		msg+= "Data variazione deve essere maggiore o uguale alla data inizio attività.\n";
		 	}
	     
	     if (sel!="-1" && idcu == "" && sel=="1" && operazioneOrigine!="2"){
	    	 esito = false;
	    	 msg +="Inserire il CU di riferimento.\n";
	     }
	}
	
	if (esitoSelect == false){
	  	esito = false;
		msg+="Variare almeno una linea.\n"	 
	}
	
	 if (esito == false){
		 alert ("Errore: \n"+msg);
		 return false;
	 }
	 else {
		 if (confirm("Stanno per essere eseguite una o piu' operazioni di variazione. Sei sicuro di voler proseguire?")){
			disabilitaBottoni();
			loadModalWindow();
			form.submit();
		 }
		 else
			 return false;
	 }
	
}

function disabilitaBottoni(){
	var button = document.getElementById("conferma");
	button.value="ATTENDERE";
	button.disabled=true;
	button = document.getElementById("chiudi");
	button.value="ATTENDERE";
	button.disabled=true;
}

</script>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT> 

<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>

<input type="button" style="width:250px"  onClick="apriAggiungiVariazione('<%=request.getParameter("id")%>', '<%=request.getParameter("tipologia")%>'); return false;" value="Variazione Stato Stabilimento"/>
<input type="hidden" id="dataInizioAttivita" value="<%=StabilimentoDettaglio.getDataInizioAttivitaString()%>">


<div id = "aggiungiVariazione"></div>