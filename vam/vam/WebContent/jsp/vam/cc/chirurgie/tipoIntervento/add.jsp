<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@page import="java.util.Date"%>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/chirurgie/add.js"></script>


<form action="vam.cc.chirurgie.tipoIntervento.Add.us" name="form" id="form" method="post" class="marginezero">
           
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    <h4 class="titolopagina">
     		Nuovo Intervento
    </h4>
	
    <table class="tabella">
    	
    	<tr>
        	<th colspan="3">
        		Informazioni preliminari
        	</th>
        </tr>
    	<tr>
    		<td style="width:50%">
    			 Data Richiesta<font color="red"> *</font>
    			 <fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" var="dataOdierna"/> 		
    			 
    			 <input type="text" id="dataRichiesta" name="dataRichiesta" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataOdierna}"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataRichiesta",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_1",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
    	
        </tr>
        <tr class='odd'>
    		<td>
    			 Operatori<font color="red"> *</font></br>
    			 <a id="linkChooseOp1"  style="cursor: pointer; text-decoration: underline; color: blue;" onclick="selezionaOp();">Seleziona</a>
					<input type="hidden" name="idOp" id="idOp" value=""/>
					<input type="hidden" name="idOpTemp" id="idOpTemp" value="${a.operatori}"/>
    		</td>
    		<td colspan="2">
				<table id="operatoriSelezionati">
					<c:forEach items="${a.operatori}" var="temp"><tr><td>${temp}</td></tr></c:forEach>
				</table>
				<table id="operatoriSelezionatiTemp" style="display:none;">
					<c:forEach items="${a.operatori}" var="temp"><tr><td>${temp}</td></tr></c:forEach>
				</table>
	    	</td>
        </tr>
        </table>
        
        <table class="tabella">
        <tr class="even">
        	<th colspan="3">
        		Informazioni Aggiuntive
        	</th>
        </tr>
        <tr>
        	<td style="width:20%">
        		Tipologia	
        	</td>
        	<td colspan="2">
     			<select name="tipologia" multiple="multiple" size="20">
				 	<c:forEach items="${listTipologieAltriInterventi}" var="t" >
				 		<c:choose>
					 		<c:when test="${t.isGroup}">
					 			<optgroup label="${t.descrizione}" style="font-style: italic"/>
					 		</c:when>
					 		<c:otherwise>
					    		<option value="${t.id}">${t.descrizione}</option>
					    	</c:otherwise>
				    	</c:choose>
					</c:forEach>
		      	</select>       
        	</td>
        </tr>
        <tr>
        	<td style="width:20%">
        		Note	
        	</td>
        	<td colspan="2">
     			<TEXTAREA NAME="note" COLS="40" ROWS="6"></TEXTAREA>         
        	</td>
        </tr>
         <tr class='even'>
    		<td colspan="1">  
    			<font color="red">* </font> Campi obbligatori
				<br/>  			
    			<input type="button" value="Salva" onclick="checkform(document.getElementById('form'))"/>
    			<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.chirurgie.tipoIntervento.List.us'">
    		</td>
        </tr>
        
	</table>
</form>


<script type="text/javascript">
	function mostraPopupChooseOp(){

		$( "#chooseOp" ).dialog({
			height: screen.height/2,
			modal: true,
			autoOpen: true,
			closeOnEscape: true,
			show: 'blind',
			resizable: true,
			draggable: true,
			title: 'Seleziona Operatori',
			width: screen.width/2,
			buttons: {
				"Annulla": function() {
					confermaModificheOperatori(false);
					$( this ).dialog( "close" );
				},
				"Ok": function() {
					confermaModificheOperatori(true);
					$( this ).dialog( "close" );
				}
			}
		});
	}
</script>



<div id="div_operatori" style="display: none;">
	<table class="tabella" id="tabellaOp">
		<tr>
						<th colspan="3">
			</th>
		</tr>

		<c:forEach items="${operatori}" var="operatore">
			<c:if test="${operatore.enabled == true}">
				<tr class="${i % 2 == 0 ? 'odd' : 'even'}" id="trTuttiEsiti${i}_${organoTipoEsito.id}">
					<td colspan="3">
						<input type="checkbox" value="${operatore.id}"  
							   	id="op${operatore.id}"  
						   		name="op${operatore}"
				   		   		onclick="popolaOpSelezionati('${operatore.id}','${operatore}')"
							<us:contain collection="${a.operatori}" item="${operatore}">
								checked="checked"
							</us:contain>	
						/>
						${operatore}
					</td>
				</tr>
				<c:set var="i" value='${i+1}'/>
			</c:if>
		</c:forEach>
	</table>
</div>

