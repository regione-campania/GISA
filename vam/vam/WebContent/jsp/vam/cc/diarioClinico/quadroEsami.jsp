<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<%@page import="java.net.URLEncoder"%><script language="JavaScript" type="text/javascript" src="js/azionijavascript.js"></script>


<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
	<h4 class="titolopagina">
     		&nbsp;Lista Esami
</h4>

<input type="button" onclick="location.href='vam.cc.diarioClinico.Detail.us'" value="Vai al Diario Clinico" />

<div class="area-contenuti-2">
	<form name="qeForm" action="vam.cc.diarioClinico.QuadroEsami.us" method="post">
		
		<jmesa:tableModel items="${cc.quadroEsami }" id="qe" var="qe">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>
					
					<jmesa:htmlColumn property="nomeEsame" title="Esame" filterable="false" sortable="false"/>
					
					<jmesa:htmlColumn property="dataRichiesta" title="Data Richiesta" pattern="dd/MM/yyyy" cellEditor="org.jmesa.view.editor.DateCellEditor" filterable="false" sortable="false"/>
					
					<jmesa:htmlColumn property="dataEsito" title="Data Esito" pattern="dd/MM/yyyy" cellEditor="org.jmesa.view.editor.DateCellEditor" filterable="false" sortable="false"/>
					
					<jmesa:htmlColumn property="enteredBy" title="Richiesto da" filterable="false" sortable="false"/>
					
				</jmesa:htmlRow>
				
			</jmesa:htmlTable>
		</jmesa:tableModel>
	</form>
	
	<script type="text/javascript">
		function onInvokeAction(id)
		{
			setExportToLimit(id, '');
			createHiddenInputFieldsForLimitAndSubmit(id);
		};
		function onInvokeExportAction(id)
		{
			var parameterString = createParameterStringForLimit(id);
			location.href = 'vam.cc.QuadroEsami.Detail.us?' + parameterString;
		};
	</script>
</div>