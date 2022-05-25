<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<form action="vam.cc.esamiUrine.AddEdit.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this);" >
           
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
  	<h4 class="titolopagina">
     		<c:if test="${edit }">Modifica</c:if><c:if test="${!edit }">Nuovo</c:if> esame delle urine
    </h4>
    
    <table class="tabella">
    	        
        <tr>
    		<td>
    			 Data Richiesta <font color="red"> *</font>
    			 <input type="text" id="dataRichiesta" name="dataRichiesta" maxlength="32" size="50" readonly="readonly" style="width:246px;"
    			 	value="<fmt:formatDate value="${eu.dataRichiesta}" pattern="dd/MM/yyyy" />"/>
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
    		<td>
    			 Data Esito
    			 <input type="text" id="dataEsito" name="dataEsito" maxlength="32" size="50" readonly="readonly" style="width:246px;"
    			 	value="<fmt:formatDate value="${eu.dataEsito}" pattern="dd/MM/yyyy" />"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_2" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataEsito",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_2",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
        </tr>
        
        
        <tr>
        	<th colspan="2">
        		Esame Fisico/Chimico
        	</th>        	
        </tr>
        
        <tr class='even'>
    		<td>
    			Volume (L/die)
    		</td>
    		<td>
    			 <input type="text" id="urine1" name="volume" maxlength="10" size="10" value="${eu.volume }"/>
    		</td>
        </tr>
        
        
        <tr class='odd'>
    		<td>
    			Peso Specifico
    		</td>
    		<td>
    			 <input type="text" id="urine2" name="pesoSpecifico" maxlength="10" size="10" value="${eu.pesoSpecifico }"/>
    		</td>
        </tr>
                
        <tr class='even'>
    		<td>
    			Colore
    		</td>
    		<td>
    			 <select name="colore_id">
    			 	<c:forEach items="${colori }" var="colore">
    			 		<option value="${colore.id }" <c:if test="${colore == eu.colore }">selected="selected"</c:if> >
    			 			${colore.description }
    			 			<c:if test="${colore.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
        
		<tr class='odd'>
    		<td>
    			PH
    		</td>
    		<td>
    			 <input type="text" id="urine3" name="ph" maxlength="10" size="10" value="${eu.ph }"/>
    		</td>
        </tr>

		<tr class='even'>
    		<td>
    			Proteine mg/die
    		</td>
    		<td>
    			 <input type="text" id="urine4" name="proteine" maxlength="10" size="10" value="${eu.proteine }"/>
    		</td>
        </tr>
        
		<tr class='odd'>
    		<td>
    			Glucosio
    		</td>
    		<td>
    			 <select name="glucosio_id">
    			 	<c:forEach items="${presenzeB }" var="presenza">
    			 		<option value="${presenza.id }" <c:if test="${presenza == eu.glucosio }">selected="selected"</c:if> >
    			 			${presenza.descriptionS }
    			 			<c:if test="${presenza.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Bilirubina
    		</td>
    		<td>
    			 <select name="bilirubina_id">
    			  	<c:forEach items="${presenzeB }" var="presenza">
    			 		<option value="${presenza.id }" <c:if test="${presenza == eu.bilirubina }">selected="selected"</c:if> >
    			 			${presenza.descriptionS }
    			 			<c:if test="${presenza.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
        
		<tr class='odd'>
    		<td>
    			Corpi Chetonici
    		</td>
    		<td>
    			 <select name="corpi_chetonici_id">
    			 	<c:forEach items="${presenzeB }" var="presenza">
    			 		<option value="${presenza.id }" <c:if test="${presenza == eu.corpiChetonici }">selected="selected"</c:if> >
    			 			${presenza.descriptionPM }
    			 			<c:if test="${presenza.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Emoglobina
    		</td>
    		<td>
    			 <select name="emoglobina_id">
    			 	<c:forEach items="${presenzeB }" var="presenza">
    			 		<option value="${presenza.id }" <c:if test="${presenza == eu.emoglobina }">selected="selected"</c:if> >
    			 			${presenza.descriptionS }
    			 			<c:if test="${presenza.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
        
		<tr class='odd'>
    		<td>
    			Nitriti
    		</td>
    		<td>
    			 <select name="nitriti_id">
    			 	<c:forEach items="${presenzeB }" var="presenza">
    			 		<option value="${presenza.id }" <c:if test="${presenza == eu.nitriti }">selected="selected"</c:if> >
    			 			${presenza.descriptionS }
    			 			<c:if test="${presenza.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
        
		<tr class='even'>
    		<td>
    			Urobilinogeno (unità Erlich.)
    		</td>
    		<td>
    			 <input type="text" id="urine5" name="urobilinogeno" maxlength="10" size="10" value="${eu.urobilinogeno }"/>
    		</td>
        </tr>
                
		<tr class='odd'>
    		<td>
    			Sangue
    		</td>
    		<td>
    			 <select name="sangue_id">
    			 	<c:forEach items="${presenzeB }" var="presenza">
    			 		<option value="${presenza.id }" <c:if test="${presenza == eu.sangue }">selected="selected"</c:if> >
    			 			${presenza.descriptionS }
    			 			<c:if test="${presenza.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
	
		<tr>
        	<th colspan="2">
        		Sedimento Urinario
        	</th>        	
        </tr>
	
		<tr class='even'>
    		<td>
    			Batteri
    		</td>
    		<td>
    			 <select name="batteri_id">
    			 	<c:forEach items="${presenzeB }" var="presenza">
    			 		<option value="${presenza.id }" <c:if test="${presenza == eu.batteri }">selected="selected"</c:if> >
    			 			${presenza.descriptionPM }
    			 			<c:if test="${presenza.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Cellule Epiteliali
    		</td>
    		<td>
    			 <select name="cellule_epiteliali_id">
    			 	<c:forEach items="${presenze }" var="presenza">
    			 		<option value="${presenza.id }" >
    			 			${presenza.descriptionPF }
    			 			<c:if test="${presenza.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
                
		<tr class='even'>
    		<td>
    			Cilindri
    		</td>
    		<td>
    			 <select name="cilindri_id">
    			 	<c:forEach items="${presenzeB }" var="presenza">
    			 		<option value="${presenza.id }" <c:if test="${presenza == eu.cilindri }">selected="selected"</c:if> >
    			 			${presenza.descriptionPM }
    			 			<c:if test="${presenza.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Cristalli
    		</td>
    		<td>
    			 <select name="cristalli_id">
    			 	<c:forEach items="${presenze }" var="presenza">
    			 		<option value="${presenza.id }" <c:if test="${presenza == eu.cristalli }">selected="selected"</c:if> >
    			 			${presenza.descriptionPM }
    			 			<c:if test="${presenza.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
        
		<tr class='even'>
    		<td>
    			Eritrociti
    		</td>
    		<td>
    			 <select name="eritrociti_id">
    			 	<c:forEach items="${presenze }" var="presenza">
    			 		<option value="${presenza.id }" <c:if test="${presenza == eu.eritrociti }">selected="selected"</c:if> >
    			 			${presenza.descriptionPM }
    			 			<c:if test="${presenza.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Leucociti
    		</td>
    		<td>
    			 <select name="leucociti_id">
    			 	<c:forEach items="${presenze }" var="presenza">
    			 		<option value="${presenza.id }" <c:if test="${presenza == eu.leucociti }">selected="selected"</c:if> >
    			 			${presenza.descriptionPM }
    			 			<c:if test="${presenza.id == -1 }">- Seleziona -</c:if>
    			 		</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>
        
		
        <tr class='even'>
        	<td>
        		<font color="red">* </font> Campi obbligatori
        	</td>
    		<td>
    			<c:if test="${!edit }">			
    				<input type="submit" value="Aggiungi" />
    			</c:if>
    			<c:if test="${edit }">
    				<input type="hidden" name="idEu" value="${eu.id }">
    				<input type="hidden" name="edit" value="on">
    				<input type="submit" value="Modifica" />
    			</c:if>
    			<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.Detail.us'">
    			
    		</td>
        </tr>
	</table>
</form>

<script type="text/javascript" >
function checkform(form)
{
	if (form.dataRichiesta.value == '')
	{
		alert("Inserire la data della richiesta");
		form.dataRichiesta.focus();
		return false;
	}	
	
	var i=1;
	
	while(document.getElementById('urine' + i) != null) 
	{
		
		if (!testFloating(document.getElementById('urine' + i).value) && document.getElementById('urine' + i).value != '') {
			alert ("Non è possibile inserire valori non numerici");					
			return false; 
		}
		
		i++;
	}		
		
	attendere();
	return true;
	
}
</script>