<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
         
    <h4 class="titolopagina">
     		Dettaglio delle somministrazioni
    </h4>
       
 
<div class="area-contenuti-2">
	<form action="vam.cc.terapieDegenza.ListEffettuazioni.us" method="post">
		
		<jmesa:tableModel items="${teList}" id="te" var="te" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" >
			<jmesa:htmlTable styleClass="tabella" >
				<jmesa:htmlRow>	
					
					<jmesa:htmlColumn property="data" title="Data ed ora" sortable="false" filterable="false">
						<fmt:formatDate value="${te.data}" pattern="dd/MM/yyyy HH:mm" var="data"/>
    			 		<c:out value="${data}"></c:out>		
    			 	</jmesa:htmlColumn>
										
					<jmesa:htmlColumn property=" " title="Farmaco" sortable="false" filterable="false">
						${te.terapiaAssegnata.magazzinoFarmaci.lookupFarmaci.description } 
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn property="terapiaAssegnata.note" title="Note" sortable="false" filterable="false">
						${te.terapiaAssegnata.note}
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn property="  " title="Tipo" sortable="false" filterable="false">
						${te.terapiaAssegnata.terapiaDegenza.tipo}
					</jmesa:htmlColumn>
															
					<jmesa:htmlColumn property="enteredBy" title="Effettuata da" sortable="false" filterable="false" />
										
										
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:tableModel>
	</form>
	
	
	<c:if test="${ta.stopped}">
		<table class="tabella">
    	<tr>
    		<th colspan="3">
    			Dati stop somministrazioni
    		</th>
    	</tr>
    	
    	<tr class="odd">
    		<th>
    			Data
    		</th>
    		<th>
    			Farmaco
    		</th>
    		<th>
    			Stoppato da
    		</th>
    	</tr>
    	<tr class="even">
    		<td>
    			<fmt:formatDate value="${ta.stoppedDate}" pattern="dd/MM/yyyy HH:mm" var="data"/>
    			<c:out value="${data}"></c:out>	
    		</td>
    		<td>
    			${ta.magazzinoFarmaci.lookupFarmaci.description } 
    		</td>
    		<td>
    			${ta.stoppedBy}
    		</td>
    	</tr> 
    	</table>
    	
    	
    	
	</c:if>
	
	<div align="center">
		<a onclick="window.close()">
			<input type="button" value="Chiudi"/>
		</a>
	</div>
	
	<script type="text/javascript">
		function onInvokeAction(id)
		{
			setExportToLimit(id, '');
			createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function onInvokeExportAction(id)
		{
			var parameterString = createParameterStringForLimit(id);
			location.href = 'vam.cc.terapieDegenza.ListEffettuazioni.us?' + parameterString;
		}
	</script>
</div>	
	

