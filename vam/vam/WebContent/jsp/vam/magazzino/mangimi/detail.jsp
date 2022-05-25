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
     		Dettaglio Magazzino Mangimi della clinica
    </h4>
   
     
<INPUT type="button" value="Carico Mangime" onclick="addCarico();" />	

<!-- FORM PER L'AGGIUNTA DI UN CARICO -->		
<div id="carico_div" title="Carica un mangime nel magazzino">
<form id="formCarico" name="formCarico" action="vam.magazzino.mangimi.AddCarico.us" method="post">
		
	 <table class="tabella">
		<tr class='odd'>
			<td>
				Tipo Animale<font color="red"> *</font>
			</td>
			<td>
				<select name="tipoAnimale">
		        	 <c:forEach items="${tipiAnimale}" var="t" >	
	        	 		<option value="${t.id }">${t.description }</option>	        	 				
					</c:forEach>
	        	</select>
			</td>
		</tr>
		
		<tr>
			<td>
				Età Animale<font color="red"> *</font>
			</td>
			<td>
				<select name="etaAnimale">
		        	 <c:forEach items="${etaAnimale}" var="e" >	
	        	 		<option value="${e.id }">${e.description }</option>	        	 				
					</c:forEach>
	        	</select>
			</td>
		</tr>
		
		<tr class='odd'>
			<td>
				Tipo Mangime<font color="red"> *</font>
			</td>
			<td>
				<select name="mangime">
		        	 <c:forEach items="${mangimi}" var="m" >	
	        	 		<option value="${m.id }">${m.description }</option>	        	 				
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
 
 
 
<!-- FORM PER LO SCARICO DI MANGIME --> 
<div id="scarico_div" title="Scarica mangime dal magazzino">
<form id="formScarico" name="formScarico" action="vam.magazzino.mangimi.AddScarico.us" method="post">
	
	<input type="hidden" name="idMangime" />
	
	 <table class="tabella">		
		
		<tr class='odd'>
			<td>
				Tipo Animale
			</td>
			<td>
				<input type="text" readonly="readonly" name="tipoAnimale"/>
			</td>		
		</tr>
		
		<tr>
			<td>
				Età Animale
			</td>
			<td>
				<input type="text" readonly="readonly" name="etaAnimale"/>
			</td>		
		</tr>
		
		<tr class='odd'>
			<td>
				Tipo mangime
			</td>
			<td>
				<input type="text" readonly="readonly" name="nomeMangime"/>
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
				<input type="text" name="quantitaDaScaricare" maxlength="9" value=""/>
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
 
 
 
 
 
 
 <!-- PAGINA DI DETTAGLIO DELLA LISTA MANGIMI DELLA MIA CLINICA-->  
<div class="area-contenuti-2">
	<form action="vam.magazzino.mangimi.Detail.us" method="post">
		
		<jmesa:tableModel items="${magazzinoMangimiList}" id="m" var="m" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" >
			<jmesa:htmlTable styleClass="tabella" >		
					
				<jmesa:htmlRow>						
					
					<jmesa:htmlColumn  property="tipoAnimale.description" 		title="Tipo Animale" sortable="false" filterable="false" />
					
					<jmesa:htmlColumn  property="etaAnimale.description" 		title="Età Animale" sortable="false" filterable="false" />
												
					<jmesa:htmlColumn  property="lookupMangimi.description" 	title="Mangime" sortable="false" filterable="false" />
					
					<jmesa:htmlColumn  property="quantita" title="Quantità" 	sortable="false" filterable="false" />
					
					<jmesa:htmlColumn filterable="false" sortable="false"		title="Operazioni">									
																				
							<input type="button" value="Scarico Mangime" onclick="addScaricoP ( ${m.id}, '${m.lookupMangimi.description}', ${m.quantita}, '${m.tipoAnimale.description}', '${m.etaAnimale.description}' );" />
							
							<input type="button" value="Storico Carico"  onclick="window.open('vam.magazzino.mangimi.Storico.us?idMagazzino=${m.id}&&tipoStorico=sc' , '_blank', 'width=700,height=700');"> 
							
							<input type="button" value="Storico Scarico" onclick="window.open('vam.magazzino.mangimi.Storico.us?idMagazzino=${m.id}&&tipoStorico=ss' , '_blank', 'width=700,height=700');"> 	
								
																																												
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
			location.href = 'vam.magazzino.mangimi.Detail.us?' + parameterString;
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
	
	function addScaricoP (idMangime, mangime, qt, tipoAnimale, etaAnimale) {	

		document.formScarico.idMangime.value 						= idMangime;	
		document.formScarico.quantitaResidua.value 					= qt;
		document.formScarico.nomeMangime.value 						= mangime;	
		document.formScarico.tipoAnimale.value 						= tipoAnimale;	
		document.formScarico.etaAnimale.value 						= etaAnimale;	
		$( '#scarico_div' ).dialog( 'open' );
		
	};


 </script>

	
	


	
 
