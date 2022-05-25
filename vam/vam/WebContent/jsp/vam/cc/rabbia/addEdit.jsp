<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>

<%@page import="java.util.Date"%>

<form action="vam.cc.rabbia.AddEdit.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkForm(this);">    

    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    
	  <h4 class="titolopagina">
		<c:if test="${!modify }" >     
			Nuovo Esame Rabbia
		</c:if>
		<c:if test="${modify }" >
			Modifica Esame Rabbia 
    			<input type="hidden" name="modify" value="on" />
    			<input type="hidden" name="id" value="${esame.id }" />
		</c:if>
    </h4>  
    
    <table class="tabella">
        <tr>
        	<th colspan="3">
        		Dati dell'esame
        	</th>
        </tr>
    	<tr>
    		<td style="text-align: right; width: 20%">
    			 Data Richiesta <font color="red">*</font>
    		</td>
    		<td colspan="2">
    			 <input 
    			 	type="text" 
    			 	id="dataRichiesta" 
    			 	name="dataRichiesta" 
    			 	maxlength="32" 
    			 	size="50" 
    			 	readonly="readonly" 
    			 	style="width:246px;" 
					<c:if test="${modify }"> value="<fmt:formatDate type="date" value="${esame.dataRichiesta}" pattern="dd/MM/yyyy" />" </c:if>
					<c:if test="${!modify }"> value="<fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" />" </c:if> />
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
    		<tr class="odd">
    		<td style="text-align: right;">
    			 Data Esito
    		</td>
    		<td colspan="2">
    			 <input 
    			 	type="text" 
    			 	id="dataEsito" 
    			 	name="dataEsito" 
    			 	maxlength="32" 
    			 	size="50" 
    			 	readonly="readonly" 
    			 	style="width:246px;" 
					value="<fmt:formatDate type="date" value="${esame.dataEsito }" pattern="dd/MM/yyyy" />" />
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
          
		<tr class="even">
    		<td style="text-align: right; width: 20%">
				Tipo Prelievo <font color="red">*</font>
    		</td>
    		<td style="text-align: left; width: 30%">
    			<input type="checkbox" name="prelievoSangue" id="prelievoSangue" onclick="toggleDiv('esitiSangue')" 
    			<c:if test="${esame.prelievoSangue }">checked="checked"</c:if>/>
    			<label for="prelievoSangue">Sangue</label>
    		</td>
    		<td style="text-align: left; width: 50%">
    			<input type="checkbox" name="prelievoEncefalo" id="prelievoEncefalo" onclick="toggleDiv('esitiEncefalo')" 
    			<c:if test="${esame.prelievoEncefalo }">checked="checked"</c:if>/>
    			<label for="prelievoEncefalo">Encefalo</label>
    		</td>
        </tr>
        
        <tr class="even">
        	<td>&nbsp;</td>
        	<td>
        		<div id="esitiSangue" <c:if test="${!esame.prelievoSangue }">style="display: none;"</c:if> >
   					Esito Sangue
   					<select name="idEsitoSangue">
   						<option value="">-- Seleziona --</option>
	    				<c:forEach items="${esitiRabbia }" var="temp" >
	    					<c:if test="${temp.sangue }">
	    						<option value="${temp.id }"
	    							<c:if test="${esame.esitoSangue == temp }">selected="selected"</c:if> 
	    						/>${temp.description }</option>
							</c:if>
						</c:forEach>
					</select>
    			</div>
        	</td>
        	<td>
        		<div id="esitiEncefalo" <c:if test="${!esame.prelievoEncefalo }">style="display: none;"</c:if> >
   					Esito Encefalo
   					<select name="idEsitoEncefalo">
   						<option value="">-- Seleziona --</option>
	    				<c:forEach items="${esitiRabbia }" var="temp" >
	    					<c:if test="${temp.encefalo }">
	    						<option value="${temp.id }"
	    							<c:if test="${esame.esitoEncefalo == temp }">selected="selected"</c:if> 
	    						/>${temp.description }</option>
							</c:if>
						</c:forEach>
					</select>
    			</div>
        	</td>
        </tr>
		
        <tr class="odd">
    		<td style="text-align: right;">    
    			<font color="red">* </font> Campi obbligatori
				<br/>
				<c:if test="${!modify }" >
					<input type="submit" value="Salva" />
				</c:if>
				<c:if test="${modify }" >
					<input type="submit" value="Modifica" />
				</c:if>
    			<input type="button" value="Annulla" onclick="location.href='vam.cc.rabbia.List.us'">
    		</td>
    		<td>&nbsp;</td>
    		<td>&nbsp;</td>
        </tr>
        
	</table>
</form>

<script type="text/javascript">
function checkForm(form)
{
	//deve controllare che sia stato selezionato almeno un tipo prelievo
	var tipoPrelSelezionato = false;
	var ele = form.elements;
	for( var i = 0; !tipoPrelSelezionato && i < ele.length; i++ )
	{
		element = ele[i];
		if (element.type)
		{
			switch (element.type)
			{
				case 'checkbox':
				if( element.checked )
				{
					tipoPrelSelezionato = true;
				}
			}
		}
	}

	if( !tipoPrelSelezionato )
	{
		alert( "Selezionare almeno un tipo di prelievo" );
	}

	return tipoPrelSelezionato;
};
</script>
