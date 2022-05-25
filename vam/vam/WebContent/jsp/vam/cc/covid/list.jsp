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
     		Lista Esami SARS CoV 2
</h4>

<c:if test="${cc.dataChiusura==null}">
<input type="button" value="Aggiungi Esame SARS CoV 2" onclick="if(${cc.dataChiusura!=null}){ 
	if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){location.href='vam.cc.covid.ToAddEdit.us'}
	}else{location.href='vam.cc.covid.ToAddEdit.us'}">
</c:if>

<div class="area-contenuti-2">
	<form action="vam.cc.covid.List.us" method="post">
		
		<jmesa:tableModel items="${cc.covids}" id="covid" var="covid" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>

					<jmesa:htmlColumn property="dataRichiesta" title="Data Test" filterEditor="it.us.web.util.jmesa.DateFilterEditor">
						<fmt:formatDate value="${covid.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiesta"/>
    			 		<c:out value="${dataRichiesta}"></c:out>					
					</jmesa:htmlColumn>		
					
					<jmesa:htmlColumn property="esito" 	title="Esito" sortable="false" filterable="false">
						<c:if test="${covid.esito=='false'}">
							Negativo
						</c:if>
						<c:if test="${covid.esito=='true'}">
							Positivo
						</c:if>
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn property="tipoTest" 	title="Tipo Test" sortable="false"  filterable="false" />
					
														
					<jmesa:htmlColumn sortable="false" filterable="false" title="Operazioni">
							<a id="mod" href="vam.cc.covid.ToAddEdit.us?modify=on&idCovid=${covid.id}" onclick="if(${cc.dataChiusura!=null}){ 
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
			location.href = 'vam.cc.covid.List.us?' + parameterString;
		}
	</script>
</div>