<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/ricoveri/edit.js"></script>



<form action="vam.cc.ricoveri.Edit.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this);">
       
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    <h4 class="titolopagina">
     		Dettagli del ricovero
    </h4>
   
    <table class="tabella">
    	
    	<tr class='even'>
    		<td>
    			 Data del ricovero<font color="red"> *</font>
    		</td>
    		<td>
    		<fmt:formatDate value="${cc.ricoveroData}" pattern="dd/MM/yyyy" var="data"/>
    			 <input type="text" id="ricoveroData" name="ricoveroData" maxlength="32" size="50" readonly="readonly" value="<c:out value="${data}"></c:out>"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "ricoveroData",     // id of the input field
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
    			Ricoverato in<font color="red"> *</font>
    		</td>
    		<td>
    			<select name="idStrutturaClinica">
					<option value="-1">&lt;--- Selezionare Struttura di ricovero ---&gt;</option>
    				<c:forEach var="s" items="${sc}">
    					<c:if test="${(s.cane and cc.accettazione.animale.lookupSpecie.id==1) or
    					              (s.gatto and cc.accettazione.animale.lookupSpecie.id==2) or
    					              (s.sinantropo and cc.accettazione.animale.lookupSpecie.id==3)}">
	            			<option value="<c:out value="${s.id}"/>"
    	        				<c:if test="${s.id == cc.strutturaClinica.id}">
  			          				<c:out value="selected=selected"></c:out> 
    							</c:if>           			
            				>
            			</c:if>
            				${s.denominazione}
    					</option>
            		</c:forEach>
    			</select>
    		</td>
    		<td>
    		Numero <input type="text" name="ricoveroBox" maxlength="20" size="20" value="<c:out value="${cc.ricoveroBox}"/>"/>
    		</td>
        </tr>   
                
        <tr class='even'>
    		<td>
    			Motivo del ricovero
    		</td>
    		<td>
    			<TEXTAREA NAME="ricoveroMotivo" COLS=40 ROWS=3><c:out value="${cc.ricoveroMotivo}"></c:out></TEXTAREA>	    			
    		</td>
    		<td>
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Sintomatologia
    		</td>
    		<td>
    			<TEXTAREA NAME="ricoveroSintomatologia" COLS=40 ROWS=3><c:out value="${cc.ricoveroSintomatologia}"></c:out></TEXTAREA>	    			
    		</td>
    		<td>
    		</td>
        </tr>
                
        <tr class='even'>
    		<td>
    			Note
    		</td>
    		<td>
    			<TEXTAREA NAME="ricoveroNote" COLS=40 ROWS=3><c:out value="${cc.ricoveroNote}"></c:out></TEXTAREA>   			
    		</td>
    		<td>
    		</td>
        </tr>
		
        <tr class='odd'>
        	<td>
        		<font color="red">* </font> Campi obbligatori    
        	</td>
    		<td>   
    			<c:choose>
    				<c:when test="${cc.ricoveroData!=null}">
    					<input type="submit" value="Modifica"/>
    				</c:when>
    				
    				<c:otherwise>
    					<input type="submit" value="Inserisci"/>
    				</c:otherwise>
    			</c:choose>
    		</td>
    		<td>
    		</td>
        </tr>
	</table>
</form>