<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>

<%@page import="java.util.Date"%>

<form action="vam.cc.toxoplasmosi.AddEdit.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkForm(this);">    

  

    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    
	  <h4 class="titolopagina">
		<c:if test="${!modify }" >     
			Nuovo Esame Toxoplasmosi
		</c:if>
		<c:if test="${modify }" >
			Modifica Esame Toxoplasmosi 
    			<input type="hidden" name="modify" value="on" />
    			<input type="hidden" name="id" value="${esame.id }" />
		</c:if>
    </h4>  
    
    <table class="tabella">
        <tr>
        	<th colspan="2">
        		Dati dell'esame
        	</th>
        </tr>
    	<tr>
    		<td style="text-align: right;">
    			 Data Richiesta <font color="red">*</font>
    		</td>
    		<td>
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
    		<td>
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
    		<td style="text-align: right;">
				Tipo Prelievo <font color="red">*</font>
    		</td>
    		<td>
    			<c:forEach items="${tipoPrelievo }" var="temp" >
					<input type="checkbox" name="tp_${temp.id }" id="tp_${temp.id }" 
						<us:contain collection="${esame.tipoPrelievo }" item="${temp }" >checked="checked"</us:contain> />
					<label for="tp_${temp.id }">${temp.description }</label> <br/>
				</c:forEach> 
    		</td>
        </tr>
          
        <tr class="odd">
        	<td style="text-align: right;">
        		Esito
        	</td>
        	<td>
        		<textarea name="esito" rows="10" cols="60" maxLength="1024">${esame.esito }</textarea>
        	</td>
        </tr>
        
        <tr>
    		<td style="text-align: right;">    
    			<font color="red">* </font> Campi obbligatori
				<br/>
				<c:if test="${!modify }" >
					<input type="submit" value="Salva" />
				</c:if>
				<c:if test="${modify }" >
					<input type="submit" value="Modifica" />
				</c:if>
    			<input type="button" value="Annulla" onclick="location.href='vam.cc.toxoplasmosi.List.us'">
    		</td>
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
				if( element.name.match("^tp_") && element.checked )
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
