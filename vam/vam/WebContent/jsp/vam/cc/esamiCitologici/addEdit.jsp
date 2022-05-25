<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@page import="java.util.Date"%>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/esamiCitologici/addEdit.js"></script>

<form action="vam.cc.esamiCitologici.AddEdit.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this);">    

  

    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    
	  <h4 class="titolopagina">
		<c:if test="${!modify }" >     
			Nuovo Esame Citologico
		</c:if>
		<c:if test="${modify }" >
			Modifica Esame Citologico 
    			
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
    	<tr class="odd">
    		<td style="text-align: left;">
    			 Data Richiesta<font color="red"> *</font>
    		</td>
    		<td style="text-align: left;">
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
    	<tr class="even">
    		<td style="text-align: left;">
    			 Data Esito
    		</td>
			<td style="text-align: left;">
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
		
		<tr class="odd">
			<td style="text-align: left;">
				Tipo Prelievo<font color="red"> *</font>
			</td>
			<td style="text-align: left;">
    			<select name="idTipoPrelievo" id="idTipoPrelievo" onchange="javascript:mostraAltro()" style="width:250px;">
    				<option value="">&lt;-- Selezionare Tipo Prelievo --&gt;</option>
					<c:forEach items="${tipiPrelievo}" var="temp" >
						<option value="${temp.id}"
							<c:if test="${esame.tipoPrelievo.id == temp.id}">selected="selected"</c:if>
						>${temp.descrizione}</option>
					</c:forEach> 
				</select>
			</td>
        </tr>
        
        
        <tr class="odd">
			<td style="text-align: left;" id="tipoPrelievoAltroTd1">
				<c:if test="${esame.tipoPrelievo.id==4}">
					Specificare altro
				</c:if>
			</td>
			<td style="text-align: left;">
    			<input type="text" name="tipoPrelievoAltro" id="tipoPrelievoAltro" value="${esame.tipoPrelievoAltro}"
    				<c:choose>
    					<c:when test="${esame.tipoPrelievo.id!=4 || esame==null}">
    						style="width:246px;display:none";
    					</c:when>
    					<c:otherwise>
							style="width:246px;";
						</c:otherwise>
					</c:choose>
    			>
			</td>
        </tr>
        
        <tr class="even">
			<td style="text-align: left;">
				Aspetto della lesione
			</td>
			<td style="text-align: left;">
    			<input type="text" name="aspettoLesione" value="${esame.aspettoLesione}" style="width:246px;">
	    	</td>
        </tr>
          
        <tr class="odd">
			<td style="text-align: left;">
				Diagnosi<font color="red"> *</font>
			</td>
			<td style="text-align: left;">
    			<select name="idDiagnosi" id="idDiagnosi" style="width:250px;">
    				<option value="">&lt;-- Selezionare Diagnosi --&gt;</option>
						<c:forEach items="${diagnosi}" var="temp" >
							<option value="${temp.id}"
								<c:if test="${esame.diagnosi.id == temp.id}">selected="selected"</c:if>
							>${temp.descrizione}</option>
						</c:forEach> 
				</select>
	    	</td>
        </tr>  
          
        <tr>
    		<td colspan="2">    
    			<font color="red">* </font> Campi obbligatori
				<br/>
				<c:if test="${!modify }" >
					<input type="submit" value="Salva" />
				</c:if>
				<c:if test="${modify }" >
					<input type="submit" value="Modifica" />
				</c:if>
    			<input type="button" value="Annulla" onclick="location.href='vam.cc.esamiCitologici.List.us'">
    		</td>
        </tr>
	</table>
</form>

<script type="text/javascript">
function checkForm(form)
{
	//non fa nulla perchè dovrebbe controllare solo la data (che già di suo non puo' essere vuota)
};
</script>
