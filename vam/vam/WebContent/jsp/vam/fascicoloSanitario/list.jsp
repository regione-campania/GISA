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
<script language="JavaScript" type="text/javascript" src="js/azionijavascript.js"></script>



<h4 class="titolopagina">
     		Fascicoli Sanitari
</h4>

<div class="area-contenuti-2">
	<form action="vam.fascicoloSanitario.List.us" method="post">
		
		<jmesa:tableModel items="${fascicoliSanitari}" id="fascicoloSanitario" var="fascicoloSanitario" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>

					<jmesa:htmlColumn property="numero" title="Numero"/>
					
					<jmesa:htmlColumn property="dataApertura" title="Data Apertura" filterEditor="it.us.web.util.jmesa.DateFilterEditor">
						<fmt:formatDate value="${fascicoloSanitario.dataApertura}" pattern="dd/MM/yyyy" var="dataApertura"/>
    			 		<c:out value="${dataApertura}"></c:out>					
					</jmesa:htmlColumn>		
					
					<jmesa:htmlColumn property="dataChiusura" title="Data Chiusura" filterEditor="it.us.web.util.jmesa.DateFilterEditor">
						<fmt:formatDate value="${fascicoloSanitario.dataChiusura}" pattern="dd/MM/yyyy" var="dataChiusura"/>
    			 		<b><c:out value="${dataChiusura}"></c:out></b>				
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn property="animale.identificativo" title="Mc interessato"/>
					
					<jmesa:htmlColumn sortable="false" filterable="false" title="Operazioni">
							<a href="vam.fascicoloSanitario.Detail.us?id=${fascicoloSanitario.id}" onclick="attendere()">Dettaglio</a>
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
			location.href = 'vam.fascicoloSanitario.List.us?' + parameterString;
		}
	</script>
</div>