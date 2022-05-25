<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

    <h4 class="titolopagina">
     		Dettaglio Clinica
    </h4>
    <table class="tabella">
        <tr>
        	<th colspan="2">
        		Dati Generali
        	</th>
        </tr>
        
    	<tr class="odd">
    		<td>
    			Asl
    		</td>
    		<td>
    			<c:out value="${clinica.lookupAsl.description}"/>
    		</td>
        </tr>
        
        <tr>
    		<td>
    			Nome
    		</td>
    		<td>
    			 <c:out value="${clinica.nome}"/>
    		</td>
        </tr>
        
        <tr class="odd">
    		<td>
    			Comune
    		</td>
    		<td>
    			 <c:out value="${clinica.lookupComuni.description}"/>
    		</td>
        </tr>
        
        <tr>
    		<td>
    			 Indirizzo
    		</td>
    		<td>
    			 <c:out value="${clinica.indirizzo}"/>
    		</td>
        </tr>
        
        <tr class="odd">
    		<td>
    			 Telefono
    		</td>
    		<td>
    			 <c:out value="${clinica.telefono}"/>
    		</td>
        </tr>
        
        <tr>
    		<td>
    			 Fax
    		</td>
    		<td>
    			 <c:out value="${clinica.fax}"/>
    		</td>
        </tr>
        
        <tr class="odd">
    		<td>
    			 Email
    		</td>
    		<td>
    			 <c:out value="${clinica.email}"/>
    		</td>
        </tr>
        
        <tr>
    		<td>
    			 Note
    		</td>
    		<td>
    			 <c:out value="${clinica.note}"/>
    		</td>
        </tr>
        
        <tr>
			<td>
				&nbsp;
			</td>
		</tr>
        <tr>
        	<th colspan="2">
        		Strutture Cliniche
        	</th>
        </tr>
        <tr>
        	<th>
        		Tipo
        	</th>
        	<th>
        		Denominazione
        	</th>
        </tr>
        
        <c:set var="i" value="1"/>
        <c:forEach var="struttura" items="${clinica.strutturaClinicas}">
			<tr class="${i% 2 == 0 ? 'odd' : 'even'}">
				<td><c:out value="${struttura.lookupTipiStruttura.description}" /></td>
				<td style="width:50%;"><c:out value="${struttura.denominazione}" /></td>
			</tr>
			<c:set var="i" value="${i+1}"/>
		</c:forEach>
        
         <tr>
			<td>
				&nbsp;
			</td>
		</tr>
		
		<tr>
			<td>
				<input type="button" value="Chiudi" onclick="window.close()"/>
			</td>
			<td>
			</td>
		</tr>
		
		
        <tr>
			<td>
				&nbsp;
			</td>
		</tr>
	</table>