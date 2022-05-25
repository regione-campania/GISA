<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@page import="java.util.Date"%>

           
    <h4 class="titolopagina">
     		Trasferimenti
    </h4>

	<div class="area-contenuti-2">

		<form name="trasferimenti" action="vam.cc.trasferimenti.Home.us" method="post">
			<jmesa:tableModel limit="${limit}" items="${trasferimenti }" id="tr" var="tr">
				<jmesa:htmlTable styleClass="tabella">
					<jmesa:htmlRow>
						<jmesa:htmlColumn title="Azioni" sortable="false" filterable="false" >
							<a onclick="$( '#dettaglio_${tr.id }_div' ).dialog( 'open' );" style="cursor: pointer; text-decoration: underline; color: blue;" >Dettaglio Trasferimento</a>
							<%@ include file="detailInclude.jsp" %>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="stato" filterable="false" sortable="false">
							${tr.stato }<br/>
							<c:if test="${tr.daApprovare }"> <%-- tr.dataAccettazioneCriuv == null and tr.dataRifiutoCriuv == null and !tr.urgenza }">  --%>
								<a href="vam.cc.trasferimenti.ToApprovazioneCriuv.us?id=${tr.id }" style="cursor: pointer; text-decoration: underline; color: blue;">Approva</a><br/>
								<a href="vam.cc.trasferimenti.ToRifiutoCriuv.us?id=${tr.id }" style="cursor: pointer; text-decoration: underline; color: blue;">Rifiuta</a>
							</c:if>
							<c:if test="${tr.daApprovareRiconsegna }">
								<a href="vam.cc.trasferimenti.ToApprovazioneRiconsegna.us?id=${tr.id }" style="cursor: pointer; text-decoration: underline; color: blue;">Approva</a><br/>
								<a href="vam.cc.trasferimenti.ToRifiutoRiconsegna.us?id=${tr.id }" style="cursor: pointer; text-decoration: underline; color: blue;">Rifiuta</a>
							</c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="cartellaClinica.identificativoAnimale" title="Animale" sortable="false"/>
						<jmesa:htmlColumn 
							property="dataRichiesta" 
							pattern="dd/MM/yyyy" 
							cellEditor="org.jmesa.view.editor.DateCellEditor" 
							title="Data Richiesta" 
							filterable="false" 
							sortable="false" />
						<jmesa:htmlColumn property="clinicaOrigine" title="Clinica di Origine" filterable="false" sortable="false">(${tr.clinicaOrigine.asl }) ${tr.clinicaOrigine.nome }</jmesa:htmlColumn>
						<jmesa:htmlColumn property="operazioniRichieste" title="Motivazioni/Operazioni Richieste" filterable="false" sortable="false" />
						<jmesa:htmlColumn property="dataAccettazioneCriuv" title="Data Accettazione Criuv" filterable="false" sortable="false" >	
							<c:if test="${tr.urgenza }"><font color="red">(Richiesta in urgenza)</font></c:if>
							<c:if test="${!tr.urgenza }"><fmt:formatDate value="${tr.dataAccettazioneCriuv }" pattern="dd/MM/yyyy" /></c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="notaCriuv" title="Nota Criuv" filterable="false" sortable="false" />
						<jmesa:htmlColumn property="clinicaDestinazione" title="Clinica di Destinazione" filterable="false" sortable="false">(${tr.clinicaDestinazione.asl }) ${tr.clinicaDestinazione.nome }</jmesa:htmlColumn>
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
			location.href = 'vam.cc.trasferimenti.Home.us?' + parameterString;
		};
	</script>