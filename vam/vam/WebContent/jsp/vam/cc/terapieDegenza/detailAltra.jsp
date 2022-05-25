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


<script language="JavaScript" type="text/javascript" src="js/vam/cc/terapieDegenza/detailAltra.js"></script>

         
  
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    <h4 class="titolopagina">
     		Dettaglio Terapia in Degenza assegnata dal Dott. ${td.enteredBy.nome} ${td.enteredBy.cognome} 
    			in data <fmt:formatDate type="date" value="${td.data}" pattern="dd/MM/yyyy" var="data"/>
    			 <c:out value="${data}"/>
    </h4>
   
       
    
    
<div id="new_assegnazionep_div" title="Assegnazione">
<form id="assFormP" name="assFormP" action="vam.cc.terapieDegenza.AddAssegnazioneAltra.us" method="post">
	<input type="hidden" name="idTerapiaAssegnata" value="" />
	<input type="hidden" name="idTerapiaDegenza" value="${td.id}" />
	
	 <table class="tabella">
		<tr class='even'>
			<td>
				Modalità di somministrazione<font color="red"> *</font>
			</td>
			<td>
				<select name="modalitaSomministrazione">
		        	 <c:forEach items="${modalitaSomministrazione}" var="ms" >	
		        	 	<option value="${ms.id }">${ms.description }</option>	        	 				
					</c:forEach>
	        	</select>
			</td>
		</tr>		
		<tr class='even'>
			<td>
				Tempi (in giorni) <font color="red"> *</font>
			</td>
			<td>
				<input type="text" name="tempi" maxlength="5" size="5"/>
			</td>
		</tr>		
		<tr class='odd'>
			<td>
				Note
			</td>
			<td>
				<TEXTAREA NAME="note" COLS=45 ROWS=7></TEXTAREA>
			</td>
		</tr>														
		
      </table>
    </form>	
 </div>


<form action="vam.cc.terapieDegenza.ListEffettuazioniByData.us?idTerapiaDegenza=${td.id}" method="post" onsubmit="openPopupFromAction(this)">	
		
	<fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" var="dataOdierna"/> 		
	<input type="text" id="dataEffettuazioni" name="dataEffettuazioni" maxlength="13" size="13" readonly="readonly" style="width:100px;" value="${dataOdierna}"/>
	<img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
	<script type="text/javascript">
					 Calendar.setup({
	 					inputField     :    "dataEffettuazioni",     // id of the input field
	 					ifFormat       :    "%d/%m/%Y",      		// format of the input field
						button         :    "id_img_1",  			// trigger for the calendar (button ID)
						// align          :    "Tl",          		 // alignment (defaults to "Bl")
	 					singleClick    :    true,
	 					timeFormat		:   "24",
	 					showsTime		:   false
				 });					    
	</script>   
	
	<input type="submit" value="Consulta il diario delle effettuazioni per data" />	
	<input type="hidden" name="idTerapiaDegenza" value="${td.id}" />
	<br/>
	<c:if test="${td.dataChiusura==null}">
		<input type="button" value="Assegna" onclick="addAssegnazioneP ( );" />	
	</c:if>
</form>


 
 
<!-- FORM PER L'EDIT DELL'ASSEGNAZIONE --> 
<div id="edit_assegnazione_div" title="Assegnazione">
<form id="assFormEdit" name="assFormEdit" action="vam.cc.terapieDegenza.EditAssegnazioneAltra.us" method="post">
	<input type="hidden" name="idTerapiaAssegnata" value="" />
	<input type="hidden" name="idTerapiaDegenza" value="${td.id}" />
	
	 <table class="tabella">
		<tr class='even'>
			<td>
				Modalità di somministrazione<font color="red"> *</font>
			</td>
			<td>
				<select name="modalitaSomministrazione">
		        	 <c:forEach items="${modalitaSomministrazione}" var="ms" >	
		        	 	<option value="${ms.id }">${ms.description }</option>	        	 				
					</c:forEach>
	        	</select>
			</td>
		</tr>		
		<tr class='even'>
			<td>
				Tempi (in giorni) <font color="red"> *</font>
			</td>
			<td>
				<input type="text" name="tempi" maxlength="5" size="5"/>
			</td>
		</tr>		
		<tr class='odd'>
			<td>
				Note
			</td>
			<td>
				<TEXTAREA NAME="note" COLS=45 ROWS=7></TEXTAREA>
			</td>
		</tr>	
		
															
		     
      </table>
    </form>	
 </div>
 
 
 
 
 
 
 <!-- PAGINA DI DETTAGLIO NELLA TABELLA JMESA -->  
<div class="area-contenuti-2" id="listaAssegnazioni">
	<form action="vam.cc.terapieDegenza.DetailAltra.us" method="post">
		
		<input type="hidden" name="idTerapiaDegenza" value="${td.id}" />
		
		<jmesa:tableModel items="${taList}" id="ta" var="ta" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" >
			<jmesa:htmlTable styleClass="tabella" >		
					
				<jmesa:htmlRow>						
																																
					<jmesa:htmlColumn property="data" title="Data" filterable="false">
						<fmt:formatDate value="${ta.data}" pattern="dd/MM/yyyy HH:mm" var="data"/>
    			 		<c:out value="${data}"></c:out>		
    			 	</jmesa:htmlColumn>					
					
					<jmesa:htmlColumn  property="lmsf.description" title="Modalità Somm." sortable="false" filterable="false" />
					
					<jmesa:htmlColumn  property="tempi" title="Giorni" sortable="false" filterable="false" />
					
					<jmesa:htmlColumn property="dataUltimaEffettuazione" title="Ultima Effettuazione" filterable="false">
						<fmt:formatDate value="${ta.dataUltimaEffettuazione}" pattern="dd/MM/yyyy HH:mm" var="dataUltimaEffettuazione"/>
    			 		<c:out value="${dataUltimaEffettuazione}"></c:out>		
    			 	</jmesa:htmlColumn>	
    			 	
    			 	<jmesa:htmlColumn  property="note" title="Note" sortable="false" filterable="false"/>
    			 		    			 	
    			 	<jmesa:htmlColumn  property="" title="Data Stop" sortable="false" filterable="false">
    			 		
    			 		<c:if test="${ta.stopped == true}">		
							<fmt:formatDate value="${ta.stoppedDate}" pattern="dd/MM/yyyy HH:mm" var="dataStop"/>
    			 			<c:out value="${dataStop}"></c:out>									
						</c:if>						
    			 	
    			 	</jmesa:htmlColumn>
					    			 	    			 	
					<jmesa:htmlColumn  sortable="false" filterable="false" title="Operazioni">
					
							<c:if test="${ta.praticable == true && td.dataChiusura==null}">								
								<input type="button" value="Effettua" 
								onclick="if( myConfirm('Sicuro di voler Effettuare la registrazione dell\'assegnazione?' )){ location.href='vam.cc.terapieDegenza.AddEffettuazioneAltra.us?idTerapiaAssegnata=${ta.id} ' } ">
							</c:if>	
														
							<input type="button" value="Consulta" onclick="window.open('vam.cc.terapieDegenza.ListEffettuazioniAltra.us?idTerapiaAssegnata=${ta.id}', '_blank', 'width=700,height=700');"> 
							<br>							
														
							<c:if test="${ta.stopped == false && td.dataChiusura==null}">		
								<input type="button" value="Stop" onclick="if( myConfirm('Sicuro di voler Stoppare questa assegnazione?') ){ location.href='vam.cc.terapieDegenza.StopAssegnazioneAltra.us?idTerapiaAssegnata=${ta.id} ' } ">
								<br>							
							</c:if>
							
							<c:if test="${ta.modifiable == true && td.dataChiusura==null}">		
								<input type="button" value="Modifica" onclick="editAssegnazione( ${ta.id}, ${ta.lmsf.id}, ${ta.tempi}, '${ta.note}');" />	
								<br>							
							</c:if>	
							
							<c:if test="${ta.erasable == true && td.dataChiusura==null}">	
								<input type="button" value="Cancella" onclick="if( myConfirm('Sicuro di voler Cancellare questa assegnazione?') ){ location.href='vam.cc.terapieDegenza.DeleteAssegnazioneAltra.us?idTerapiaAssegnata=${ta.id} ' } ">
							</c:if>															
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
			location.href = 'vam.cc.terapieDegenza.DetailAltra.us?' + parameterString;
		}
	</script>
</div>	
	

<script type="text/javascript">
	$(function() 
	{
		$( "#new_assegnazione_div" ).dialog({
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

					if (document.assForm.tempi.value == '') {
						alert("Selezionare la durata della terapia");							
						return false;
					}

					if (isNaN(document.assForm.tempi.value)) {
						alert("La durata deve essere un valore intero");							
						return false;
					}

					if (!isIntPositivo(document.assForm.tempi.value,'Tempi',document.assForm.tempi)) {													
						alert("La durata deve essere un valore intero");	
						return false;
					}
						
					document.assForm.submit();					
				}
			}
		});
	});



	$(function() 
			{
				$( "#new_assegnazionep_div" ).dialog({
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

							if (document.assFormP.tempi.value == '') {
								alert("Selezionare la durata della terapia");							
								return false;
							}

							if (isNaN(document.assFormP.tempi.value)) {
								alert("La durata deve essere un valore intero");							
								return false;
							}

							if (!isIntPositivo(document.assFormP.tempi.value,'Tempi',document.assFormP.tempi)) {													
								alert("La durata deve essere un valore intero");	
								return false;
							}
								
							document.assFormP.submit();					
						}
					}
				});
			});



	$(function() 
			{
				$( "#edit_assegnazione_div" ).dialog({
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


							if (document.assFormEdit.tempi.value == '') {
								alert("Selezionare la durata della terapia");							
								return false;
							}

							if (isNaN(document.assFormEdit.tempi.value)) {
								alert("La durata deve essere un valore intero");							
								return false;
							}

							if (!isIntPositivo(document.assFormEdit.tempi.value,'Tempi',document.assFormEdit.tempi)) {													
								alert("La durata deve essere un valore intero");	
								return false;
							}

							document.assFormEdit.submit();					
						}
					}
				});
			});


	

	function addAssegnazioneP () {	

		$( '#new_assegnazionep_div' ).dialog( 'open' );
		
	};
	
	
	function editAssegnazione( id, lmsf, tempi, note )
	{

		
		document.assFormEdit.idTerapiaAssegnata.value 		= id;	
		document.assFormEdit.modalitaSomministrazione.value = lmsf;
		document.assFormEdit.tempi.value 					= tempi;
		document.assFormEdit.note.value 					= note;
		
		$( '#edit_assegnazione_div' ).dialog( 'open' );
	};	
</script>

	
	


	
 
