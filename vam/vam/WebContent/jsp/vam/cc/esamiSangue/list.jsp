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
     	Lista esami del sangue
</h4>
	
<input type="button" value="Aggiungi un esame" onclick="if(${cc.dataChiusura!=null}){ 
	if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){attendere(), location.href='vam.cc.esamiSangue.ToAdd.us'}
	}else{attendere(), location.href='vam.cc.esamiSangue.ToAdd.us'}">

<div class="area-contenuti-2">
	<form action="vam.cc.esamiSangue.List.us" method="post">
		
		<jmesa:tableModel items="${esamiSangue}" id="esamiSangue" var="es" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>
															
					<jmesa:htmlColumn property="dataRichiesta" 			title="Data Richiesta" filterEditor="it.us.web.util.jmesa.DateFilterEditor">
						<fmt:formatDate value="${es.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiesta"/>
    			 		<c:out value="${dataRichiesta}"></c:out>					
					</jmesa:htmlColumn>		
					
					<jmesa:htmlColumn property="dataEsito" 			title="Data Esito" filterEditor="it.us.web.util.jmesa.DateFilterEditor">
						<fmt:formatDate value="${es.dataEsito}" pattern="dd/MM/yyyy" var="dataEsito"/>
    			 		<c:out value="${dataEsito}"></c:out>					
					</jmesa:htmlColumn>				
					
					<jmesa:htmlColumn property="enteredBy" 	title="Effettuato Dal Dott."/>
														
					<jmesa:htmlColumn filterable="false" sortable="false"			title="Operazioni">
							<a href="vam.cc.esamiSangue.Detail.us?idEsameSangue=${es.id}">Dettaglio</a>
							<a id="mod" href="vam.cc.esamiSangue.ToEdit.us?idEsameSangue=${es.id}" onclick="if(${cc.dataChiusura!=null}){ 
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
			location.href = 'vam.cc.esamiSangue.List.us?' + parameterString;
		}
	</script>
</div>