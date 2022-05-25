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


<jsp:include page="/jsp/vam/cc/menuCC.jsp?riepilogoCc=ancheDatiRicovero"/>
<h4 class="titolopagina">
  		Lista Diagnosi
</h4>

<input type="button" value="Aggiungi una diagnosi" onclick="if(${cc.dataChiusura!=null}){ 
	if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){attendere(), location.href='vam.cc.diagnosi.ToAdd.us'}
	}else{attendere(), location.href='vam.cc.diagnosi.ToAdd.us'}">
	    			 
<div class="area-contenuti-2">
	<form action="vam.cc.diagnosi.List.us" method="post">
		
		<jmesa:tableModel items="${diagnosi}" id="diagnosi" var="d" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" >
			<jmesa:htmlTable styleClass="tabella" >
				<jmesa:htmlRow>					
										
					<jmesa:htmlColumn width="20%" property="dataDiagnosi" 			title="Data della diagnosi" filterEditor="it.us.web.util.jmesa.DateFilterEditor">
						<fmt:formatDate value="${d.dataDiagnosi}" pattern="dd/MM/yyyy" var="dataDiagnosi"/>
    			 		<c:out value="${dataDiagnosi}"></c:out>					
					</jmesa:htmlColumn>					
					
					<jmesa:htmlColumn property="diagnosiEffettuate" sortable="false" title="Diagnosi">
						${d.diagnosiEffettuate}
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn property="diagnosiChirurgiche" sortable="false" filterable="false" title="Diagnosi Chirurgiche">
						${d.diagnosiChirurgiche}
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn property=" " title="Operazioni" sortable="false" filterable="false" >
							<a href="vam.cc.diagnosi.Detail.us?idDiagnosi=${d.id}" onclick="attendere()">Dettaglio</a>										
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
			location.href = 'vam.cc.diagnosi.List.us?' + parameterString;
		}
	</script>
</div>