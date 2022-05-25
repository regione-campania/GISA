<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/anamnesiRecente/edit.js"></script>

<form action="vam.cc.anamnesiRecente.Edit.us" name="form" method="post" class="marginezero">
           
    
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
		    			<c:when test="${cc.anamnesiRecenteConosciuta == true}">
							<input type="radio" name="anamnesiRecenteConosciuta" id="anamnesiRecenteConosciutaF" value="false" onClick="javascript: hiddenDiv('descrizioneAnamnesi');" > <label for="anamnesiRecenteConosciutaF">Muta</label><br>							
  							<input type="radio" name="anamnesiRecenteConosciuta" id="anamnesiRecenteConosciutaT" value="true"  checked onClick="javascript: toggleGroup('descrizioneAnamnesi');" > <label for="anamnesiRecenteConosciutaT">Conosciuta</label><br>
							<td>
					    		<div id="descrizioneAnamnesi" style="display:block;">	    			
					    			<TEXTAREA NAME="anamnesiRecenteDescrizione" COLS=60 ROWS=7><c:out value="${cc.anamnesiRecenteDescrizione}"></c:out></TEXTAREA>	    			
					    		</div>
				    		</td>	
						</c:when>
						<c:when test="${cc.anamnesiRecenteConosciuta == false}">
							<input type="radio" name="anamnesiRecenteConosciuta" id="anamnesiRecenteConosciutaF" value="false" checked onClick="javascript: hiddenDiv('descrizioneAnamnesi');" > <label for="anamnesiRecenteConosciutaF">Muta</label><br>							
  							<input type="radio" name="anamnesiRecenteConosciuta" id="anamnesiRecenteConosciutaT" value="true"  onClick="javascript: toggleGroup('descrizioneAnamnesi');" > <label for="anamnesiRecenteConosciutaT">Conosciuta</label><br>
							<td>
					    		<div id="descrizioneAnamnesi" style="display:none;">	    			
					    			<TEXTAREA NAME="anamnesiRecenteDescrizione" COLS=60 ROWS=7><c:out value="${cc.anamnesiRecenteDescrizione}"></c:out></TEXTAREA>	    			
					    		</div>
				    		</td>
						</c:when>
						<c:otherwise>
							<input type="radio" name="anamnesiRecenteConosciuta" id="anamnesiRecenteConosciutaF" value="false" onClick="javascript: hiddenDiv('descrizioneAnamnesi');" > <label for="anamnesiRecenteConosciutaF">Muta</label><br>							
    						<input type="radio" name="anamnesiRecenteConosciuta" id="anamnesiRecenteConosciutaT" value="true"  onClick="javascript: toggleGroup('descrizioneAnamnesi');" > <label for="anamnesiRecenteConosciutaT">Conosciuta</label><br>
							<td>
					    		<div id="descrizioneAnamnesi" style="display:none;">	    			
					    			<TEXTAREA NAME="anamnesiRecenteDescrizione" COLS=60 ROWS=7><c:out value="${cc.anamnesiRecenteDescrizione}"></c:out></TEXTAREA>	    			
					    		</div>
				    		</td>
						</c:otherwise>	
					</c:choose>	
						
    		</td>
    		
    		
        </tr>
                
       <br>
       <br>
		
        <tr class='odd'>
        	<td>
        	</td>
    		<td>    			
    			<input type="submit" value="Inserisci/Modifica" onclick="attendere()"/> 
    		</td>
    		<td>
    		</td>
        </tr>
	</table>
</form>