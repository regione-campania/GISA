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
     		Storico Modifiche
</h4>


<div class="area-contenuti-2">
	<form action="StoricoModifiche.us" method="post">
		
		<jmesa:tableModel items="${modifiche}" id="m" var="m" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" columnSort="it.us.web.util.jmesa.CustomColumnSort">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>

					<jmesa:htmlColumn property="entered" title="Data" cellEditor="it.us.web.util.jmesa.DateCellEditor" filterEditor="it.us.web.util.jmesa.DateFilterEditor"/>
					
					<jmesa:htmlColumn property="enteredBy" title="Utente" />
										
					<jmesa:htmlColumn property="cc.numero" title="Cartella Clinica" />
					
					<jmesa:htmlColumn property="descrizioneOperazione" title="Modifica" />
						
					<us:can f="STORICO" sf="DETAIL" og="MAIN" r="r" >												
					<jmesa:htmlColumn sortable="false" filterable="false" title="Operazioni">
						<c:choose>
						<c:when test="${m.ip == 'nuovagestione'}">
							<a href="StoricoModificheDetail.us?id=${m.id}&nuovagestione=true">Dettaglio</a>
						</c:when>
						<c:otherwise>
							<a href="StoricoModificheDetail.us?id=${m.id}&nuovagestione=false">Dettaglio</a>
						</c:otherwise>
						</c:choose>
					</jmesa:htmlColumn>
					</us:can>
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
			location.href = 'StoricoModifiche.us?' + parameterString;
		}
	</script>
</div>