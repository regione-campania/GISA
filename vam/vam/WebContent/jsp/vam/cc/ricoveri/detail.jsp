<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<form action="vam.cc.ricoveri.ToEdit.us" name="form" method="post" class="marginezero">
       
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    <h4 class="titolopagina">
     		Dettagli del ricovero
    </h4>
   
    <table class="tabella">
    	
    	<tr class='even'>
    		<td>
    			 Data del ricovero
    		</td>
    		<td>
    			<fmt:formatDate value="${cc.ricoveroData}" pattern="dd/MM/yyyy" var="data"/>
	   			${data}
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td colspan="3">
    			Ricoverato in ${cc.strutturaClinica.denominazione}, Numero ${cc.ricoveroBox}
    		</td>
        </tr>   
                
        <tr class='even'>
    		<td>
    			Motivo del ricovero
    		</td>
    		<td>
    			${cc.ricoveroMotivo}	    			
    		</td>
    		<td>
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Sintomatologia
    		</td>
    		<td>
    			${cc.ricoveroSintomatologia}	    			
    		</td>
    		<td>
    		</td>
        </tr>
                
        <tr class='even'>
    		<td>
    			Note
    		</td>
    		<td>
    			${cc.ricoveroNote}  			
    		</td>
    		<td>
    		</td>
        </tr>
        	<tr class='odd'>
    			<td colspan="2">    						
    				<input type="button" value="Modifica" 
    					onclick="if(${cc.dataChiusura!=null})
    							{ 
        							if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?'))
        							{
        								location.href='vam.cc.ricoveri.ToEdit.us';
        							}
								}
								else
								{
									location.href='vam.cc.ricoveri.ToEdit.us';
								}"/>    			
    			</td>
    			<td>
    			</td>
        	</tr>
	</table>
</form>