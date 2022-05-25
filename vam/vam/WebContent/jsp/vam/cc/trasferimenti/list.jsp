<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.vam.CartellaClinicaNoH"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@page import="java.util.Date"%>

<%
	CartellaClinicaNoH cc 	= (CartellaClinicaNoH)request.getAttribute("cc");   
%>        
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>   
  	<h4 class="titolopagina">
     		Trasferimenti
    </h4>
    
	<div class="area-contenuti-2">
		<c:if test="${nuovoTrasferimentoPossibile && cc.dataChiusura==null}">
		<br/><br/>
			<!--  <input type="button" value="Nuova Richiesta" onclick="location.href='vam.cc.trasferimenti.ToAdd.us'"> -->
			<input type="button" value="Nuova Richiesta in Urgenza" onclick="location.href='vam.cc.trasferimenti.ToAdd.us?urgenza=on'">
		</c:if>
		<c:if test="${riconsegnaPossibile}">
			<input type="button" value="Riconsegna" 
				<c:choose>
					<c:when test="${cc.ccMorto==false}">
						onclick="location.href='vam.cc.trasferimenti.ToRiconsegna.us'"
					</c:when>
					<c:otherwise>
						onclick="alert('Non è possibile riconsegnare un animale deceduto')"
					</c:otherwise>
				</c:choose>
			>
		</c:if>
		<br/><br/>
		
		<form name="myForm" action="vam.cc.trasferimenti.List.us" method="post">
			<jmesa:tableModel items="${trasferimenti }" id="trtm" var="tr">
				<jmesa:htmlTable styleClass="tabella">
					<jmesa:htmlRow>
						
						<jmesa:htmlColumn title="Azioni" sortable="false" filterable="false" >
							<a onclick="$( '#dettaglio_${tr.id }_div' ).dialog( 'open' );" style="cursor: pointer; text-decoration: underline; color: blue;" >Dettaglio Trasferimento</a>
							<%@ include file="detailInclude.jsp" %>
						</jmesa:htmlColumn>
						
						<jmesa:htmlColumn property="stato" filterEditor="org.jmesa.view.html.editor.DroplistFilterEditor" sortable="false"/>
						<jmesa:htmlColumn 
							property="dataRichiesta" 
							pattern="dd/MM/yyyy" 
							cellEditor="org.jmesa.view.editor.DateCellEditor" 
							title="Data Richiesta" 
							filterable="false" 
							sortable="false" />
						<jmesa:htmlColumn property="operazioniRichieste" title="Motivazioni/Operazioni Richieste" filterable="false" sortable="false" />
						<jmesa:htmlColumn property="clinicaOrigine" title="Clinica di Origine" filterable="false" sortable="false">(${tr.clinicaOrigine.lookupAsl }) ${tr.clinicaOrigine.nome }</jmesa:htmlColumn>
						<jmesa:htmlColumn property="clinicaDestinazione" title="Clinica di Destinazione" filterable="false" sortable="false">(${tr.clinicaDestinazione.lookupAsl }) ${tr.clinicaDestinazione.nome }</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			</jmesa:tableModel>
		</form>
	</div>
	

	
	<script type="text/javascript">
		function onInvokeAction(id)
		{
			setExportToLimit(id, '');
			createHiddenInputFieldsForLimitAndSubmit(id);
		};
		function onInvokeExportAction(id)
		{
			var parameterString = createParameterStringForLimit(id);
			location.href = 'vam.cc.trasferimenti.List.us?' + parameterString;
		};
	</script>