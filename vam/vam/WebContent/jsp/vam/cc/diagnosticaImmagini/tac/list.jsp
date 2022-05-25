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

<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
<h4 class="titolopagina">
     		Lista TAC precedenti
</h4>

<input type="button" value="Aggiungi TAC" onclick="if(${cc.dataChiusura!=null}){ 
	if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){location.href='vam.cc.diagnosticaImmagini.tac.ToAdd.us'}
	}else{location.href='vam.cc.diagnosticaImmagini.tac.ToAdd.us'}">

<div class="area-contenuti-2">
	<form action="vam.cc.diagnosticaImmagini.tac.List.us" method="post">
		
		<jmesa:tableModel items="${tac}" id="tac" var="tac" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" columnSort="it.us.web.util.jmesa.CustomColumnSort">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>

					<jmesa:htmlColumn property="dataRichiesta"  filterEditor="it.us.web.util.jmesa.DateFilterEditor"                     title="Data Richiesta"     cellEditor="it.us.web.util.jmesa.DateCellEditor"/>
					<jmesa:htmlColumn property="dataEsito"  filterEditor="it.us.web.util.jmesa.DateFilterEditor"                     title="Data Esito"     cellEditor="it.us.web.util.jmesa.DateCellEditor"/>
					<jmesa:htmlColumn property="enteredBy" 	title="Effettuato dal Dott." />															
					<jmesa:htmlColumn filterable="false" sortable="false" title="Operazioni">
							<a href="vam.cc.diagnosticaImmagini.tac.Detail.us?idTac=${tac.id}">Dettaglio</a>
							<a id="mod" href="vam.cc.diagnosticaImmagini.tac.ToEdit.us?idTac=${tac.id}" onclick="if(${cc.dataChiusura!=null}){ 
			    				return myConfirm('Cartella Clinica chiusa. Vuoi procedere?');
	    						}else{return true;}">Modifica</a>
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
			location.href = 'vam.cc.diagnosticaImmagini.tac.List.us?' + parameterString;
		}
	</script>
</div>