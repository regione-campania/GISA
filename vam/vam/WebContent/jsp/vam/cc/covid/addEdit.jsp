<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/covid/addEdit.js"></script>

<%@page import="java.util.Date"%>

<form action="vam.cc.covid.AddEdit.us" name="form" method="post" class="marginezero" id="form">    

  

    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    
	  <h4 class="titolopagina">
		<c:if test="${!modify }" >     
			Nuovo Esame SARS CoV 2
		</c:if>
		<c:if test="${modify }" >
			Modifica Esame SARS CoV 2
    			<input type="hidden" name="modify" value="on" />
    			<input type="hidden" name="id" value="${covid.id }" />
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
    			 Data Test <font color="red">*</font>
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
					<c:if test="${modify }"> value="<fmt:formatDate type="date" value="${covid.dataRichiesta}" pattern="dd/MM/yyyy" />" </c:if>
					<c:if test="${!modify }"> value="" </c:if> />
					
					<c:if test="${cc.dataChiusura==null}">
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
  					</c:if>
  					  
    		</td>
    		</tr>
    		
    	<tr>
        	<td style="text-align: right;">
        		Tipo Test
        	</td>
        	<td>
        	<c:if test="${cc.dataChiusura==null}">
        		<select id="idTipoTest" name="idTipoTest">
    			 <option value="">&lt;---Selezionare---&gt;</option>
				 	<c:forEach items="${listCovidTipoTest}" var="t" >	
						<option value="${t.id }"
							<c:if test="${covid.tipoTest.id == t.id && modify}">
  		          				<c:out value="selected=selected"></c:out> 
    						</c:if> 
						>${t.nome }</option>
					</c:forEach>
		      	</select>
		      	</c:if>
		      	<c:if test="${cc.dataChiusura!=null}">
		      		<input type="text" readonly="readonly" value="${covid.tipoTest.nome}" name="idTipoTestDescrizione" />
		      		<input type="hidden" value="${covid.tipoTest.id}" name="idTipoTest" id="idTipoTest"/>
		      	</c:if>
        	</td>
        </tr>
    		          
        <tr>
        	<td style="text-align: right;">
        		Esito
        	</td>
        	<td>
        		<select name="esito" id="esito">	
					<option value="">Selezionare Esito</option>			
           			<option value="false"
           				<c:if test="${covid.esito == 'false' && modify}">
  		          			<c:out value="selected=selected"></c:out> 
    					</c:if>   
           			>Negativo</option>
   					<option value="true"
           				<c:if test="${covid.esito == 'true' && modify}">
  		          			<c:out value="selected=selected"></c:out> 
    					</c:if>   
           			>Positivo</option>
	    		</select> 
        	</td>
        </tr>
        
        <tr>
    		<td style="text-align: right;">    
    			<font color="red">* </font> Campi obbligatori
				<br/>
				<c:if test="${!modify }" >
					<input type="button" value="Salva" onclick="checkform()" />
				</c:if>
				<c:if test="${modify }" >
					<input type="button" value="Modifica" onclick="checkform()" />
				</c:if>
    			<input type="button" value="Annulla" onclick="location.href='vam.cc.covid.List.us'">
    		</td>
    		<td>&nbsp;</td>
        </tr>
        
	</table>
</form>