<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<form action="vam.cc.anamnesiRecente.ToEdit.us" name="form" method="post" class="marginezero">
           
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    <h4 class="titolopagina">
     		Anamnesi recente
    </h4>
     
    <table class="tabella">
         <tr class='even'>
    		<td>
    			Tipologia di anamnesi
    		</td>
    		<td>
    			<c:choose>
    				<c:when test="${cc.anamnesiRecenteConosciuta == false}">
    					Muta - 
    				</c:when>
    				<c:otherwise>
    					Conosciuta - 
    				</c:otherwise>
    			</c:choose>
    			${cc.anamnesiRecenteDescrizione}
    		</td>
        </tr>
                
       <br>
       <br>
		
        <tr class='odd'>
        	<td>
        	</td>
    		<td>   
    			<input type="submit" value="Modifica" onclick="if(${cc.dataChiusura!=null}){ 
    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){attendere()}
    				}else{attendere()}"/>			
    		</td>
    		<td>
    		</td>
        </tr>
	</table>
</form>