<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<p>	
	<a href="ruoli.ToAdd.us">Aggiungi un Nuovo Ruolo</a>
</p>
	
<div class="area-contenuti-2">
	<form action="ruoli.List.us" method="post">
		<jmesa:tableModel items="${ruoli}" id="ruoli" var="ru">
			<jmesa:htmlTable>
				<jmesa:htmlRow>
					<jmesa:htmlColumn property="ruolo"/>
					<jmesa:htmlColumn property="descrizione" />
					<jmesa:htmlColumn filterable="false">
						<a href="ruoli.ToPermissionEdit.us?ruolo=${ru.ruolo}" >Modifica permessi</a>
						<a href="ruoli.ToDescriptionEdit.us?ruolo=${ru.ruolo}" >Modifica descrizione</a>
						<c:if test="${ru.numeroUtentiAssegnatiRuolo > 0}">
							utenti: ${ru.numeroUtentiAssegnatiRuolo}
						</c:if>
						<c:if test="${ru.numeroUtentiAssegnatiRuolo <= 0}">
							<a href="ruoli.Delete.us?NOME_RUOLO=${ru.ruolo}" onclick="javascript:return confirm('Eliminare il ruolo ${ru.ruolo}?')" >Elimina</a>
						</c:if>
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
			location.href = 'ruoli.List.us?' + parameterString;
		}
	</script>
</div>
