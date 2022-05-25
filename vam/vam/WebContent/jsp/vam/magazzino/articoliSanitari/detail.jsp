<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/vam.tld" prefix="vam" %>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
  
  
    <h4 class="titolopagina">
     		Dettaglio Magazzino Articoli Sanitari della clinica
    </h4>
   
     
<INPUT type="button" value="Carico Articolo Sanitario" onclick="addCarico();" />	

<!-- FORM PER L'AGGIUNTA DI UN CARICO -->		
<div id="carico_div" title="Carica un articolo sanitario nel magazzino">
<form id="formCarico" name="formCarico" action="vam.magazzino.articoliSanitari.AddCarico.us" method="post">
		
	 <table class="tabella">
	
		<tr class='odd'>
			<td>
				Nome Articolo Sanitario<font color="red"> *</font>
			</td>
			<td>
				<select name="articoloSanitario">
		        	 <c:forEach items="${articoliSanitari}" var="as" >	
	        	 		<option value="${as.id }">${as.description }</option>	        	 				
					</c:forEach>
	        	</select>
			</td>
		</tr>	
		<tr>
			<td>
				Numero di confezioni da caricare<font color="red"> *</font>
			</td>
			<td>
				<input type="text" name="numeroConfezioni" maxlength="9" size="2"/>
	       		
			</td>
		</tr>	
		<tr class='odd'>
			<td>
				Data di scadenza
			</td>
			<td>				
	       		 <input type="text" name="dataScadenza" id="dataScadenza" readonly="readonly"/>
			</td>
		</tr>						
		<tr>
			<td>
			<font color="red">* </font> Campi obbligatori
			</td> 
			<td>
			</td>
		</tr>     
      </table>
    </form>	
 </div>
 
 
 
<!-- FORM PER LO SCARICO DI UN ARTICOLO SANITARIO --> 
<div id="scarico_div" title="Scarico articolo sanitario">
<form id="formScarico" name="formScarico" action="vam.magazzino.articoliSanitari.AddScarico.us" method="post">
	
	<input type="hidden" name="idArticoloSanitario" />
	
	 <table class="tabella">		
		
		<tr class='odd'>
			<td>
				Nome Articolo Sanitario
			</td>
			<td>
				<input type="text" readonly="readonly" name="nomeArticoloSanitario"/>
			</td>		
		</tr>
		
		<tr>
			<td>
				Quantità residua
			</td>
			<td>
				<input type="text" readonly="readonly" name="quantitaResidua"/>
			</td>		
		</tr>
		
		<tr class='odd'>
			<td>
				Quantità da scaricare<font color="red"> *</font>
			</td>
			<td>
				<input type="text" name="quantitaDaScaricare" value="" maxlength="9" />
			</td>		
		</tr>
		<tr>
			<td>
				<font color="red">* </font> Campi obbligatori
			</td> 
			<td>
			</td>
		</tr> 	
      </table>
    </form>	
 </div>
 
 
 
 
 
 
 <!-- PAGINA DI DETTAGLIO DELLA LISTA ARTICOLI SANITARI DELLA MIA CLINICA-->  
<div class="area-contenuti-2">
	<form action="vam.magazzino.articoliSanitari.Detail.us" method="post">

		<jmesa:tableModel items="${magazzinoArticoliSanitariList}" id="mas" var="mas" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" >
			<jmesa:htmlTable styleClass="tabella" >		
					
				<jmesa:htmlRow>						
													
					<jmesa:htmlColumn  property="lookupArticoliSanitari.description" 			title="Articolo Sanitario" sortable="false" filterable="false" />
					
					<jmesa:htmlColumn  property="quantita" title="Quantità" 			sortable="false" filterable="false" />
					
					<jmesa:htmlColumn filterable="false" sortable="false"				title="Operazioni">									
																				
							<input type="button" value="Scarico Articolo Sanitario" onclick="addScaricoP ( ${mas.id}, '${mas.lookupArticoliSanitari.description}', ${mas.quantita} );" />
							
							<input type="button" value="Storico Carico"  onclick="window.open('vam.magazzino.articoliSanitari.Storico.us?idMagazzino=${mas.id}&&tipoStorico=sc' , '_blank', 'width=700,height=700');"> 
							
							<input type="button" value="Storico Scarico" onclick="window.open('vam.magazzino.articoliSanitari.Storico.us?idMagazzino=${mas.id}&&tipoStorico=ss' , '_blank', 'width=700,height=700');"> 	
																																												
					</jmesa:htmlColumn>
															
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:tableModel>
	</form>
	
	<script type="text/javascript">
		function onInvokeAction(id)
		{
			setExportToLimit(id, '');
			createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function onInvokeExportAction(id)
		{
			var parameterString = createParameterStringForLimit(id);
			location.href = 'vam.magazzino.articoliSanitari.Detail.us?' + parameterString;
		}
	</script>
</div>	
	

<script type="text/javascript">
	$(function(){
		$('#mydatepicker').datepicker({dateFormat: 'dd/mm/yyyy'});		
	});
	
	$(function() 
	{		
		$( "#carico_div" ).dialog({
			height: screen.height/1.5,
			modal: true,
			autoOpen: false,
			closeOnEscape: true,
			show: 'blind',
			resizable: true,
			draggable: true,
			width: screen.width/1.2,			
			buttons: {				
				"Annulla": function() {
					$( this ).dialog( "close" );
				},
				"Salva": function() {

					if (document.formCarico.numeroConfezioni.value == '') {
						alert("Inserire un numero di confezioni");							
						return false;
					}	
					
					if (parseFloat(document.formCarico.numeroConfezioni) ) {
						alert("Inserire un valore numerico come quantità di confezioni");							
						return false;
					}	

					if (!isNumber(document.formCarico.numeroConfezioni.value, 'Quantita', document.formCarico.numeroConfezioni)) {
						alert("Selezionare una quantità positiva come numero di confezioni");							
						return false;
					}


					document.formCarico.submit();					
				}
			}
		});
		$("#ui-datepicker-div").css("z-index",10000); 
		$( "#dataScadenza" ).datepicker(
										{
				 						 dateFormat: 'dd/mm/yy', 
					 					 showOn: "button",
					 					 buttonImage: "images/calendar.gif",
					 					 buttonImageOnly: true,
					 					 monthNames: ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"],
					 					 dayNamesMin: ["Dom","Lun","Mar","Mer","Gio","Ven","Sab"],
					 					 firstDay: 1
										}
				   					   );
	});



	$(function() 
			{
				$( "#scarico_div" ).dialog({
					height: screen.height/1.5,
					modal: true,
					autoOpen: false,
					closeOnEscape: true,
					show: 'blind',
					resizable: true,
					draggable: true,
					width: screen.width/1.2,
					buttons: {
						"Annulla": function() {
							$( this ).dialog( "close" );
						},
						"Salva": function() {

							if (document.formScarico.quantitaDaScaricare.value == '') {
								alert("Inserire la quantità da saricare");							
								return false;
							}	
							
							if (parseFloat(document.formScarico.quantitaDaScaricare) ) {
								alert("Inserire un valore numerico come quantità da saricare");							
								return false;
							}	

							if (!isNumber(document.formScarico.quantitaDaScaricare.value, 'Quantita', document.formScarico.quantitaDaScaricare)) {
								alert("Selezionare una quantità positiva da saricare");							
								return false;
							}		
							
							document.formScarico.submit();					
						}
					}
				});
				$("#ui-datepicker-div").css("z-index",10000); 
				$( "#dataScadenza" ).datepicker(
												{
						 						 dateFormat: 'dd/mm/yy', 
							 					 showOn: "button",
							 					 buttonImage: "images/calendar.gif",
							 					 buttonImageOnly: true,
							 					 monthNames: ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"],
							 					 dayNamesMin: ["Dom","Lun","Mar","Mer","Gio","Ven","Sab"],
							 					 firstDay: 1
												}
						   					   );
			});


	

	

	function addCarico(  )
	{		
		$( '#carico_div' ).dialog( 'open' );		
	};	
	
	function addScarico(  )
	{			
		$( '#scarico_div' ).dialog( 'open' );
	};	
	
	function addScaricoP (idArticoloSanitario, articoloSanitario, qt) {	

		document.formScarico.idArticoloSanitario.value 						= idArticoloSanitario;	
		document.formScarico.nomeArticoloSanitario.value					= articoloSanitario;	
		document.formScarico.quantitaResidua.value 						    = qt;	
		$( '#scarico_div' ).dialog( 'open' );
		
	};
	
 </script>

	
	


	
 
