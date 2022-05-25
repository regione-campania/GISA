<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<h4 class="titolopagina">
     		Lista Rilasci
</h4>
<jsp:include page="/jsp/sinantropi/menuSin.jsp"/>
<script type="text/javascript">
		ddtabmenu.definemenu("ddtabs2",3);		
</script> 
<br>
<br>
<br>
<input type="button" value="Aggiungi Rilascio" onclick="location.href='sinantropi.reimmissioni.ToAdd.us?idSinantropo=${s.id}'">

<div class="area-contenuti-2">
	<form action="sinantropi.reimmissioni.List.us" method="post">
		
		<input type="hidden" name="idCattura" 		value="<c:out value="${c.id}"/>"/>
		<input type="hidden" name="idSinantropo" 	value="<c:out value="${s.id}"/>"/>
		
					
		<jmesa:tableModel items="${c.reimmissioni}" id="reimmissioni" var="r" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>
										
					<jmesa:htmlColumn property="dataReimmissione" 	title="Data Reimmissione">
						<fmt:formatDate value="${r.dataReimmissione}" pattern="dd/MM/yyyy" var="dataReimmissione"/>
    			 		<c:out value="${dataReimmissione}"></c:out>					
					</jmesa:htmlColumn>			
					
					<jmesa:htmlColumn sortable="false" filterable="false"  property="" title="Provincia Reimmissione">
						<c:choose>			
							<c:when test="${r.comuneReimmissione.bn == true}">
								<c:out value="Benevento"></c:out>	
							</c:when>
							<c:when test="${r.comuneReimmissione.na == true}">
								<c:out value="Napoli"></c:out>	
							</c:when>
							<c:when test="${r.comuneReimmissione.sa == true}">
								<c:out value="Salerno"></c:out>	
							</c:when>
							<c:when test="${r.comuneReimmissione.av == true}">
								<c:out value="Avellino"></c:out>	
							</c:when>
							<c:when test="${r.comuneReimmissione.ce == true}">
								<c:out value="Caserta"></c:out>	
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</jmesa:htmlColumn>					
										
					<jmesa:htmlColumn sortable="false" filterable="false"  property="comuneReimmissione.description"  title="Comune Rilascio"/>					
					
					<jmesa:htmlColumn sortable="false" filterable="false"  property="luogoReimmissione"  title="Luogo Rilascio"/>
					
					<jmesa:htmlColumn sortable="false" filterable="false" title="Operazioni">							
							<a href="sinantropi.reimmissioni.ToEdit.us?idReimmissione=${r.id}">Modifica</a>
					</jmesa:htmlColumn>
					
					
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:tableModel>
	</form>
	
	<script type="text/javascript">
		function onInvokeAction(id)
		{
			setExportToLimit(id, '');
			createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function onInvokeExportAction(id)
		{
			var parameterString = createParameterStringForLimit(id);
			location.href = 'sinantropi.reimmissioni.List.us?' + parameterString;
		}
	</script>
</div>