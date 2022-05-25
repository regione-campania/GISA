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
     		Lista delle detenzioni
</h4>
<jsp:include page="/jsp/sinantropi/menuCatture.jsp"/>
<script type="text/javascript">
		ddtabmenu.definemenu("ddtabs2",2);	
</script> 
<br>
<br>
<br>

<c:if test="${(c.reimmissioni == null) && (s.dataDecesso == null)}">	
		<input type="button" value="Aggiungi un detentore" onclick="location.href='sinantropi.detenzioni.ToAdd.us?idCattura=${c.id}'">
</c:if>
<div class="area-contenuti-2">
	<form action="sinantropi.detenzioni.List.us" method="post">
		
		<input type="hidden" name="idCattura" 		value="<c:out value="${c.id}"/>"/>
		<input type="hidden" name="idSinantropo" 	value="<c:out value="${s.id}"/>"/>
						
		<jmesa:tableModel items="${c.detenzionis}" id="detenzioni" var="d" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>
										
					<jmesa:htmlColumn filterable="false" property="dataDetenzioneDa" title="Data Detenzione Da">
						<fmt:formatDate value="${d.dataDetenzioneDa}" pattern="dd/MM/yyyy" var="dataDetenzioneDa"/>
    			 		<c:out value="${dataDetenzioneDa}"></c:out>					
					</jmesa:htmlColumn>	
					
					<jmesa:htmlColumn filterable="false" property="dataDetenzioneA"  title="Data Detenzione A">
						<fmt:formatDate value="${d.dataDetenzioneA}" pattern="dd/MM/yyyy" var="dataDetenzioneA"/>
    			 		<c:out value="${dataDetenzioneA}"></c:out>					
					</jmesa:htmlColumn>			
					
					<jmesa:htmlColumn filterable="false"  property="lookupDetentori.description"  title="Detentore"/>					
						
					
					<jmesa:htmlColumn sortable="false" filterable="false" title="Operazioni">
							<a href="sinantropi.detenzioni.Detail.us?idDetenzione=${d.id}">Dettaglio</a>
							<a href="sinantropi.detenzioni.ToEdit.us?idDetenzione=${d.id}">Modifica</a>
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
			location.href = 'sinantropi.detenzioni.List.us?' + parameterString;
		}
	</script>
</div>