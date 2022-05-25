<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/interface/DwrPreaccettazione.js"> </script>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>


 <% String numeroTdTableBottoni = request.getParameter("numeroTdTableBottoni");
 	String riferimento_id_preacc = request.getParameter("riferimentoIdPreaccettazione");
 	String riferimento_id_nome_preacc = request.getParameter("riferimentoIdNomePreaccettazione");
 	String riferimento_id_nome_tab_preacc = request.getParameter("riferimentoIdNomeTabPreaccettazione");
 	String userId_preacc = request.getParameter("userIdPreaccettazione");
 	%>
 
<script type="text/javascript">

function getListaLineeGisa(riferimento_id, riferimento_id_nome, riferimento_id_nome_tab)
{
	DwrPreaccettazione.Preaccettazione_GetListaLineeGisa(riferimento_id, riferimento_id_nome, riferimento_id_nome_tab, {callback:getListaLineeGisaCallBack,async:false});
}
function getListaLineeGisaCallBack(returnValue)
{
	document.getElementById("returnGetListaLineeGisa").value = returnValue;
	return returnValue;
}

function getPreaccettazione(userId, ente, laboratorio)
{
	DwrPreaccettazione.Preaccettazione_GetPreaccettazione(userId, ente, laboratorio, {callback:getPreaccettazioneCallBack,async:false});
}
function getPreaccettazioneCallBack(returnValue)
{
	document.getElementById("returnGetPreaccettazione").value = returnValue;
	return returnValue;
}

function setPreaccettazione(id, riferimento_id,riferimento_id_nome, riferimento_id_nome_tab, id_linea_materializzata, tipologia_operatore, userId){
	DwrPreaccettazione.Preaccettazione_SetPreaccettazione(id, riferimento_id,riferimento_id_nome, riferimento_id_nome_tab, id_linea_materializzata, tipologia_operatore, userId, {callback:setPreaccettazioneCallBack,async:false});
}
function setPreaccettazioneCallBack(returnValue)
{
	document.getElementById("returnSetPreaccettazione").value = returnValue;
	return returnValue;
}

function getElencoDaLinea(identificativo_linea, id_ente, id_laboratorio)
{
	DwrPreaccettazione.Preaccettazione_GetElencoDaLinea(identificativo_linea, id_ente, id_laboratorio, {callback:getElencoDaLineaCallBack,async:false});
}
function getElencoDaLineaCallBack(returnValue)
{
	document.getElementById("returnGetElencoDaLinea").value = returnValue;
	return returnValue;
}
function getElencoLaboratoriDaEnte(id_ente)
{
	DwrPreaccettazione.Preaccettazione_GetElencoLaboratoriDaEnte(id_ente, {callback:getElencoLaboratoriDaEnteCallBack,async:false});
}
function getElencoLaboratoriDaEnteCallBack(returnValue)
{
	document.getElementById("returnGetElencoLaboratoriDaEnte").value = returnValue;
	return returnValue;
}	

function listaLineaAttivita(){
	 
	var codiceHtml = '';
	loadModalWindow();
	
	getListaLineeGisa("<%=riferimento_id_preacc %>","<%=riferimento_id_nome_preacc %>", "<%=riferimento_id_nome_tab_preacc %>" );
	var dati = document.getElementById("returnGetListaLineeGisa").value;
	var obj;
	obj = JSON.parse(dati);
	
	var len= obj.length;
   	codiceHtml = codiceHtml + '<table width= 100% id="tabCodPreacc" border = "2" cellpadding="5" cellspacing="5" class="details">' +
			'<tr>' +
 				//'<th>numero linea</th>' +
				'<th>attivita</th> ' +
				'<th align="center" width= 20%>seleziona</th>' +
			'</tr>';
   		
	for( i = 0; i <len ; i++) {
		codiceHtml = codiceHtml + '<tr>' +
		//'<td>'+ obj[i].n_linea +'</td>' +
		'<td>'+ obj[i].attivita +'</td>' +
		'<td align="center"> <input type="radio" id="lineaSel" name="lineaSel"' +
		'value="'+ obj[i].id_linea + " " + obj[i].tipologia_operatore +'"/></td>'+
		'</tr>';
	}
	loadModalWindowUnlock();	
    
 	codiceHtml = codiceHtml + '</table>';
 	codiceHtml = codiceHtml + '<font color="red">* ' + 
 				 'Attenzione! prima di consegnare il campione al laboratorio' +
		  		 ' e\' INDISPENSABILE associare al codice pregenerato sulla scheda il campione in GISA.' +
		  		 ' Il mancato rispetto di tale procedura vanifica l\'operativita\' della preaccettazione. </font>' +
		  		 '<br><br><br>' +
 				 '<input type="button" value="Visualizza lista codici preaccettazione utilizzabili" onclick="apriListaCodici();">' + 
 				 '<br><br><font color="red">' + 
 				 '* Attenzione! Cliccando sul tasto "Visualizza lista codici preaccettazione utilizzabili" '+
 				 'verranno visualizzati i codici prenotati rispetto alla linea di attività dello stabilimento' + 
 				 ' ma non ancora associati ad alcun campione. </font>';
	return codiceHtml; 
}

function elencoLaboratoriDaEnte(id_ente){
	 
	var codiceHtml = '';
	loadModalWindow();
	
	getElencoLaboratoriDaEnte(id_ente);
	var dati = document.getElementById("returnGetElencoLaboratoriDaEnte").value;
	var obj;
	obj = JSON.parse(dati);
	
	var len= obj.length;
   	codiceHtml = codiceHtml + '<table width= 100% id="tabLab" border = "2" cellpadding="5" cellspacing="5" class="details">' +
			'<tr>' +
 				//'<th>numero linea</th>' +
				'<th>Laboratorio</th> ' +
				'<th align="center" width= 20%>seleziona</th>' +
			'</tr>';
   		
	for( i = 0; i <len ; i++) {
		codiceHtml = codiceHtml + '<tr>' +
		//'<td>'+ obj[i].n_linea +'</td>' +
		'<td>'+ obj[i].description +'</td>' +
		'<td align="center"> <input type="radio" id="labSel" name="labSel"' +
		'value="'+ obj[i].code +'"/></td>'+
		'</tr>';
	}
	loadModalWindowUnlock();	
    
 	codiceHtml = codiceHtml + '</table>';
 	
	return codiceHtml; 
}

function generaCodicePreaccettazione(){
	var idPreacc = '';
	loadModalWindow();
	
	getPreaccettazione("<%=userId_preacc %>", "1", getRadioValue('labSel'));
	var dati = document.getElementById("returnGetPreaccettazione").value;
	var obj;
	obj = JSON.parse(dati);
	idPreacc = obj.idPreaccettazione;
	document.getElementById("codicePreaccettazioneConferma").value = obj.codPreaccettazione;
    loadModalWindowUnlock();
  
    return idPreacc;
}

function associaCodiceAllOsa(idCodicePreacc, idLinea, tipologiaOperatore){

	var messaggio = '';
	setPreaccettazione(idCodicePreacc, "<%=riferimento_id_preacc %>", "<%=riferimento_id_nome_preacc %>", "<%=riferimento_id_nome_tab_preacc %>", idLinea, tipologiaOperatore, "<%=userId_preacc %>");
	var dati = document.getElementById("returnSetPreaccettazione").value;
	var obj;
	obj = JSON.parse(dati);
	messaggio = obj.messaggio;
    loadModalWindowUnlock();
    return messaggio;
}


$(function() {
	var Return;
   $( "#dialogcodicipreaccettazione" ).dialog({
   	autoOpen: false,
       resizable: false,
       closeOnEscape: false,
       width:700,
       height:700,
       draggable: false,
       modal: true,
       open: function(){
<%--     	   alert("<%=riferimento_id_preacc %>" + " " +
				 "<%=riferimento_id_nome_preacc %>" + " " +
				 "<%=riferimento_id_nome_tab_preacc %>" ); --%>
    	   $(this).html(' ');
       	   var codiceHtml;
       	   codiceHtml = listaLineaAttivita();
       	   
       	   var codiceVuoto = '<table width= 100% id="tabCodPreacc" border = "2" cellpadding="5" cellspacing="5" class="details">' +
								'<tr>' +
								'<th>attivita</th> ' +
								'<th align="center" width= 20%>seleziona</th>' +
								'</tr>' + '</table><font color="red">* ' + 
				 				 'Attenzione! prima di consegnare il campione al laboratorio' +
						  		 ' e\' INDISPENSABILE associare al codice pregenerato sulla scheda il campione in GISA.' +
						  		 ' Il mancato rispetto di tale procedura vanifica l\'operativita\' della preaccettazione. </font>' +
						  		 '<br><br><br>' +
				 				 '<input type="button" value="Visualizza lista codici preaccettazione utilizzabili" onclick="apriListaCodici();">' + 
				 				 '<br><br><font color="red">' + 
				 				 '* Attenzione! Cliccando sul tasto "Visualizza lista codici preaccettazione utilizzabili" '+
				 				 'verranno visualizzati i codici prenotati rispetto alla linea di attività dello stabilimento' + 
				 				 ' ma non ancora associati ad alcun campione. </font>';
       	   if (codiceVuoto == codiceHtml){
       		   alert("ATTENZIONE! Ci sono linee di attivita' pregresse. Aggiornarle prima di generare un nuovo codice di preaccettazione!");
       		   $( this ).dialog( "close" );
               loadModalWindowUnlock();
               return false
       	   } else {
       		   
       		  var codiceHtml2 = elencoLaboratoriDaEnte("1");
       		   codiceHtml += codiceHtml2;
       		   
       			$(this).html(codiceHtml);
       	   }
       	   
       },
       buttons: {
           "ASSOCIA": function() {
        	   
        	   if (getRadioValue("lineaSel") != null && getRadioValue("labSel") != null){
        		   var lineaEtipologiaOperatore = getRadioValue("lineaSel").split(' ');
       			   var nota_informativa = "\n\nAttenzione! prima di consegnare il campione al laboratorio" +
       			   						  " e' INDISPENSABILE associare al codice pregenerato sulla scheda il campione in GISA." +
       			   						  " Il mancato rispetto di tale procedura vanifica l'operativita' della preaccettazione.";
       			   var lineaPreacc = lineaEtipologiaOperatore[0];
       			   var tipologiaOperatorePreacc = lineaEtipologiaOperatore[1];
       			   //generare il codice di preaccettazione
       			   var id_preacc = generaCodicePreaccettazione();
       			   //associare il codice all osa
       			   var mess = associaCodiceAllOsa(id_preacc, lineaPreacc, tipologiaOperatorePreacc);
       			   if(mess == 'ok')
       				   alert('CODICE PREACCETTAZIONE:  ' + document.getElementById("codicePreaccettazioneConferma").value + nota_informativa);
       			   else 
       				   alert('OPERAZIONE FALLITA');
       			   $( this ).dialog( "close" );
        	   }else {
        		   alert('SELEZIONARE UNA LINEA DI ATTIVITA E UN LABORATORIO O ANNULLARE OPERAZIONE');
        	   }

               return true;
           },
           "ANNULLA": function() {
               $( this ).dialog( "close" );
               loadModalWindowUnlock();
               return false
           }
       }
   });
});
 
	function generaCodici(){
	    $('#dialogcodicipreaccettazione').dialog('open');
	}
 
 
 
 function listaCodiciPreaccettazioneStabilimento(){
	 
	var codiceHtmlListaLine = '';
	
	
	loadModalWindow();
	
	getListaLineeGisa("<%=riferimento_id_preacc %>","<%=riferimento_id_nome_preacc %>", "<%=riferimento_id_nome_tab_preacc %>" );
	var dati = document.getElementById("returnGetListaLineeGisa").value;
	var obj;
	obj = JSON.parse(dati);
	
   		
   		var len = obj.length;
		
   		codiceHtmlListaLine = codiceHtmlListaLine + 
   		'Codici prenotati rispetto alla linea di attività dello stabilimento ed al laboratorio ma non ancora associati ad alcun campione. <br>' + 
   		'<table width= 100% id="tabCodPreacc" border = "2" cellpadding="5" cellspacing="5" class="details">' +
		'<tr>' +
			'<th>codice preaccettazione</th>' +
			//'<th>numero linea</th>' +
			'<th>attivita</th> ' +
			'<th>data preaccettazione</th>' +
			'<th>ente</th>' +
			'<th>laboratorio</th>' +
			'<th>utente</th>' +
		'</tr>';
		for( i = 0; i <len ; i++) {
   			
   			codiceHtmlListaLine = listaCodiciPreaccettazione(
   					codiceHtmlListaLine, 
   					obj[i].n_linea, 
   					obj[i].attivita, 
   					obj[i].id_linea, 
   					obj[i].tipologia_operatore
   					);
   		}
		codiceHtmlListaLine = codiceHtmlListaLine + '</table>';

   		loadModalWindowUnlock();
    
 	
 	var listavuota = 'Codici prenotati rispetto alla linea di attività dello stabilimento ed al laboratorio ma non ancora associati ad alcun campione. <br>' + 
 		'<table width= 100% id="tabCodPreacc" border = "2" cellpadding="5" cellspacing="5" class="details">' +
		'<tr>' +
				'<th>codice preaccettazione</th>' +
				//'<th>numero linea</th>' +
				'<th>attivita</th> ' +
				'<th>data preaccettazione</th>' +
				'<th>ente</th>' +
				'<th>laboratorio</th>' +
				'<th>utente</th>' +
		'</tr>' + '</table>';
	
	if (codiceHtmlListaLine == listavuota ){
		codiceHtmlListaLine = 'NON ESISTONO CODICI DI PREACCETTAZIONE PER QUESTO STABILIMENTO';
	}
 	
	return codiceHtmlListaLine;
 
}
 
 
 
 function listaCodiciPreaccettazione(codiceHtmlinput, nLineaAttivita, descAttivita, 
		 							idLineaPreacc, tipologiaOperatorePreacc){
     		var identificativoOSA = "<%=riferimento_id_preacc %>" +
     								"<%=riferimento_id_nome_preacc %>" + 
     								"<%=riferimento_id_nome_tab_preacc %>" +
     								idLineaPreacc +
     								tipologiaOperatorePreacc; 

     		var codiceHtmlCodicePreac = codiceHtmlinput;
     		loadModalWindow();
     		
     		getElencoDaLinea(identificativoOSA, "1", getRadioValue("labSel"));
    		var dati = document.getElementById("returnGetElencoDaLinea").value;
    		var obj;
    		obj = JSON.parse(dati);
    		
            		var len = obj.length;
            		
            		for( j = 0; j <len ; j++) {
            			codiceHtmlCodicePreac = codiceHtmlCodicePreac + '<tr>' +
     					'<td>'+ obj[j].codice +'</td>' +
     					//'<td>'+ nLineaAttivita +'</td>' +
     					'<td>'+ descAttivita +'</td>' +
     					'<td>'+ obj[j].data +'</td>' +
     					'<td>'+ obj[j].ente +'</td>' +
     					'<td>'+ obj[j].laboratorio +'</td>' +
     					'<td>'+ obj[j].username +'</td>' +
     					'</tr>';
            		}
            		loadModalWindowUnlock();
           
     		return codiceHtmlCodicePreac;
     	 
     }
	
	
 $(function() {
		var Return;
	   $( "#dialoglistapreaccettazione" ).dialog({
	   	autoOpen: false,
	       resizable: true,
	       closeOnEscape: false,
	       width:900,
	       height:300,
	       draggable: false,
	       modal: true,
	       open: function(){
	    	   $(this).html(' ');
	          	var codiceHtmllista = '';
	          	codiceHtmllista = listaCodiciPreaccettazioneStabilimento();
	          	$(this).html(codiceHtmllista);
	          },
	       buttons: {
	           "close": function(){	   			
	               $( this ).dialog( "close" );
	               loadModalWindowUnlock();
	               return true;
	           }
	       }
	   });
	});
 
    function apriListaCodici(){
    	
    	if (getRadioValue("labSel") == null){
    		alert("Selezionare prima il laboratorio!");
    		return false;
    	}
    	
	    $('#dialoglistapreaccettazione').dialog('open');
	}
 
    function getRadioValue(theRadioGroup)
    {
        var elements = document.getElementsByName(theRadioGroup);
        for (var i = 0, l = elements.length; i < l; i++)
        {
            if (elements[i].checked)
            {
                return elements[i].value;
            }
        }
    }
    
</script>

<input type="hidden" id ="returnGetListaLineeGisa" name="returnGetListaLineeGisa" value=""/>
<input type="hidden" id ="returnGetElencoDaLinea" name="returnGetElencoDaLinea" value=""/>
<input type="hidden" id ="returnGetPreaccettazione" name="returnGetPreaccettazione" value=""/>
<input type="hidden" id ="returnSetPreaccettazione" name="returnSetPreaccettazione" value=""/>
<input type="hidden" id ="returnGetElencoLaboratoriDaEnte" name="returnGetElencoLaboratoriDaEnte" value=""/>

<input type="hidden" id="codicePreaccettazioneConferma" name="codicePreaccettazioneConferma" value=""/>

<div id="dialogcodicipreaccettazione" title="GENERA CODICE PREACCETTAZIONE"></div>
<div id="dialoglistapreaccettazione" title="LISTA CODICI PREACCETTAZIONE UTILIZZABILI"></div>
 
<dhv:permission name="campioni-campioni-preaccettazionesenzacampione-view">  
<%
if(numeroTdTableBottoni!=null)
{
%>
<td valign="top"> 
				<center> 
<%
}
%>
<input style="width:250px" type="button" id="generaPreacc" value="Genera Preaccettazione" onclick="generaCodici();"/>
<%
if(numeroTdTableBottoni!=null)
{
%>
</center> 
</td>
<%
}
%>
</dhv:permission>

<%
	if(numeroTdTableBottoni!=null && (numeroTdTableBottoni.equals("4") || numeroTdTableBottoni.equals("8") || numeroTdTableBottoni.equals("12") || numeroTdTableBottoni.equals("16")))
	{
		out.println("</tr><tr>");
	}
%>
