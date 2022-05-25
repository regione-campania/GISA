<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<script language="JavaScript" type="text/javascript" src="js/vam/cliniche/associatesUser.js"></script>

<form action="vam.cliniche.AssociatesUser.us" name="form" method="post" class="marginezero">

 <input type="hidden" name="categorieSelezionate" value=""/>
    
    <h4 class="titolopagina">
     		Associazione Utente/Clinica
    </h4>
    <table class="tabella">
    	<tr>
        	<th>
        		Utente
        	</th>
        	<th>
        		Cliniche
        	</th>
        </tr>
        
        <tr>
    		<td>
    			<input type="hidden" value="${utenteSelezionato.id}" name="idUtente" />
           		<c:out value="${utenteSelezionato.username}"/>: <c:out value="${utenteSelezionato.nome}"/> <c:out value="${utenteSelezionato.cognome}"/>
    		</td>
    		
    		<td>
    			<select name="clinica" id="clinica">
    				<option value="">&lt;--- Selezionare Clinica ---&gt;</option>
    				<c:forEach var="clinica" items="${elencoCliniche}">
    					<option value="${clinica.id}"
    						<c:if test="${clinica.id==utenteSelezionato.clinica.id}">
								selected=selected
							</c:if>    						
    					>${clinica.nome}</option>
    				</c:forEach>
    			</select>
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<input type="button" value="Associa" onclick="javascript:checkform();"/>
    			<input type="button" value="Annulla" onclick="javascript: attendere(); location.href='vam.cliniche.ToAssociatesUser.us';"/>
    		</td>
        </tr>
        
	</table>
</form>