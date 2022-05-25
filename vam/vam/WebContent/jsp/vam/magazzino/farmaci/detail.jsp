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
     		Dettaglio Magazzino Farmaci della clinica
    </h4>
   
     
<INPUT type="button" value="Carico  Farmaco" onclick="addCarico();" />	

<!-- FORM PER L'AGGIUNTA DI UN CARICO -->		
<div id="carico_div" title="Carica un farmaco nel magazzino">
<form id="formCarico" name="formCarico" action="vam.magazzino.farmaci.AddCarico.us" method="post">
		
	 <table class="tabella">
	
		<tr class='odd'>
			<td>
				Nome Farmaco<font color="red"> *</font>
			</td>
			<td>
				<select name="farmaco">
		        	 <c:forEach items="${farmaci}" var="f" >	
	        	 		<option value="${f.id }">${f.description }</option>	        	 				
					</c:forEach>
	        	</select>
			</td>
		</tr>	
		<tr class='even'>
			<td>
				Presentazione farmaco - Unita' di misura<font color="red"> *</font>
			</td>
			<td>
				<select name="tipoFarmaco">
		        	 <c:forEach items="${tipiFarmaco}" var="tf" >	
		        	 	<option value="${tf.id }">${tf.description } - ${tf.unitaMisuraCarico }</option>								
					</c:forEach>
	        </select>
			</td>
		</tr>		
		<tr class='odd'>
			<td>
				Numero di confezioni da caricare<font color="red"> *</font> 
			</td>
			<td>
				<input type="text" name="numeroConfezioni" maxlength="2" size="2"/>
	       		
			</td>
		</tr>	
		<tr class='even'>
			<td>
				Numero totale del contenuto di tutte le confezioni calcolato in unità di misura<font color="red"> *</font>
			</td>
			<td>				
	       		<input type="text" name="quantitaElemento" maxlength="5" size="5"/>
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
		<tr class='even'>
			<td>
			<font color="red">* </font> Campi obbligatori
			</td> 
			<td>
			</td>
		</tr>     
      </table>
    </form>	
 </div>
 
 
 
<!-- FORM PER LO SCARICO DI UN FARMACO --> 
<div id="scarico_div" title="Assegnazione farmaco alla terapia">
<form id="formScarico" name="formScarico" action="vam.magazzino.farmaci.AddScarico.us" method="post">
	
	<input type="hidden" name="idFarmaco" />
	
	 <table class="tabella">		
		
		<tr class='odd'>
			<td>
				Nome farmaco
			</td>
			<td>
				<input type="text" readonly="readonly" name="nomeFarmaco"/>
			</td>		
		</tr>
		
		<tr class='even'>
			<td>
				Presentazione farmaco
			</td>
			<td>
				<input type="text" readonly="readonly" name="tipologiaFarmaco"/>
			</td>		
		</tr>
		
		<tr class='odd'>
			<td>
				Quantità residua - Unità di misura
			</td>
			<td>
				<input type="text" readonly="readonly" name="quantitaResidua"/> - 
				<input type="text" readonly="readonly" name="unitaMisuraQuantitaResidua"/>
			</td>		
		</tr>
		
		<tr>
			<td>
				Quantità da scaricare - Unità di misura<font color="red"> *</font>
			</td>
			<td>
				<input type="text" name="quantitaDaScaricare" value=""/> - 
				<input type="text" readonly="readonly" name="unitaMisuraQuantitaDaScaricare"/>
			</td>		
		</tr>
		<tr class='odd'>
			<td>
			<font color="red">* </font> Campi obbligatori
			</td> 
			<td>
			</td>
		</tr>
		
      </table>
    </form>	
 </div>
 
 
 
 
 
 
 <!-- PAGINA DI DETTAGLIO DELLA LISTA FARMACI DELLA MIA CLINICA-->  
<div class="area-contenuti-2">
	<form action="vam.magazzino.farmaci.Detail.us" method="post">
		
		<input type="hidden" name="idTerapiaDegenza" value="${td.id}" />
		
		<jmesa:tableModel items="${mfList}" id="mf" var="mf" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" >
			<jmesa:htmlTable styleClass="tabella" >		
					
				<jmesa:htmlRow>						
													
					<jmesa:htmlColumn  property="lookupFarmaci.description" 			title="Farmaco" sortable="false" filterable="false" />
					
					<jmesa:htmlColumn  property="quantita" title="Quantità" 			sortable="false" filterable="false" />
					
					<jmesa:htmlColumn  property="lookupTipiFarmaco.description" 		title="Tipo farmaco" sortable="false" filterable="false" />
															
					<jmesa:htmlColumn  property="lookupTipiFarmaco.unitaMisuraCarico" 	title="Unità Misura" sortable="false" filterable="false" />
					
					<jmesa:htmlColumn filterable="false" sortable="false"				title="Operazioni">									
																				
							<input type="button" value="Scarico Farmaco" onclick="addScaricoP ( ${mf.id}, '${mf.lookupFarmaci.description}', ${mf.quantita}, '${mf.lookupTipiFarmaco.description}', '${mf.lookupTipiFarmaco.unitaMisuraCarico}', '${mf.lookupTipiFarmaco.unitaMisuraScarico}');" />
							
							<input type="button" value="Storico Carico"  onclick="window.open('vam.magazzino.farmaci.Storico.us?idMagazzino=${mf.id}&&tipoStorico=sc' , '_blank', 'width=700,height=700');"> 
							
							<input type="button" value="Storico Scarico" onclick="window.open('vam.magazzino.farmaci.Storico.us?idMagazzino=${mf.id}&&tipoStorico=ss' , '_blank', 'width=700,height=700');"> 	
								
																																												
					</jmesa:htmlColumn>
															
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:tableModel>
	</form>
	
	<c:out value="Attenzione: Effettuare lo scarico, da questa sezione, solo quando ci sono farmaci rotti, scaduti o utilizzati fuori dalla Terapia in degenza"></c:out>
	
	<script type="text/javascript">
		function onInvokeAction(id)
		{
			setExportToLimit(id, '');
			createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function onInvokeExportAction(id)
		{
			var parameterString = createParameterStringForLimit(id);
			location.href = 'vam.magazzino.farmaci.Detail.us?' + parameterString;
		}
	</script>
</div>	
	

<script type="text/javascript">
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


					if (document.formCarico.quantitaElemento.value == '') {
						alert("Inserire una quantità di ogni singolo elemento da caricare");							
						return false;
					}	
					
					if (parseFloat(document.formCarico.quantitaElemento) ) {
						alert("Inserire un valore numerico come quantità di ogni singolo elemento da caricare");							
						return false;
					}	

					if (!isNumber(document.formCarico.quantitaElemento.value, 'Quantita', document.formCarico.quantitaElemento)) {
						alert("Selezionare una quantità positiva di ogni singolo elemento da caricare");							
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
	
	function addScaricoP (idFarmaco, farmaco, qt, tipoFarmaco, unitaCarico, unitaScarico) {	

		document.formScarico.idFarmaco.value 						= idFarmaco;	
		document.formScarico.nomeFarmaco.value 						= farmaco;	
		document.formScarico.tipologiaFarmaco.value 				= tipoFarmaco;	
		document.formScarico.quantitaResidua.value 					= qt;	
		document.formScarico.unitaMisuraQuantitaResidua.value 		= unitaCarico;		
		document.formScarico.unitaMisuraQuantitaDaScaricare.value 	= unitaScarico;	
		$( '#scarico_div' ).dialog( 'open' );
		
	};
	
 </script>

	
	


	
 
