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
     		&nbsp;Diario Clinico
</h4>
<input type="button" onclick="location.href='vam.cc.diarioClinico.QuadroEsami.us'" value="Vai alla Lista Esami" />
<div class="area-contenuti-2">
	<form name="dcForm" action="vam.cc.diarioClinico.Detail.us" method="post">
		
		<jmesa:tableModel items="${cc.diarioClinico }" id="dc" var="dc">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>
					
					<jmesa:htmlColumn property="apparato.description" title="Apparato" filterable="false" sortable="false">
						<strong>
							<c:if test="${dc.apparato == null}">EOG</c:if>
							<c:if test="${dc.apparato != null}">${dc.apparato.description }</c:if>
						</strong>
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn property="data" title="Data Esame" pattern="dd/MM/yyyy" cellEditor="org.jmesa.view.editor.DateCellEditor" filterable="false" sortable="false"/>
					
					<jmesa:htmlColumn property="temperatura" title="Temperatura" filterable="false" sortable="false"/>
					
					<jmesa:htmlColumn property="tipiEO" title="Esito" filterable="false" sortable="false">
						<table style="width: 100%;">
							<c:forEach items="${dc.tipiEO }" var="teo">
								<tr style="vertical-align: top;">
									<td style="text-align: right; width: 40%;" class="<c:if test="${!teo.normale }" >errore</c:if><c:if test="${teo.normale }" >messaggio</c:if>">${teo.tipo.description }</td>
									<td>
										<c:if test="${teo.normale}">Normale</c:if>
										<c:if test="${!teo.normale}">
											<c:forEach items="${teo.esiti }" var="esito">
												${esito }<br/>
											</c:forEach>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</table>
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn property="osservazioni" title="Osservazioni" filterable="false" sortable="false">
						<c:set var="newLine" value="\n" />
						${dc.osservazioniHtmlEncoded }<br/>
						<a  onclick="editOsservazioni( ${dc.id}, '${dc.osservazioniUrlEncoded }' );" style="cursor: pointer; text-decoration: underline; color: blue;">Modifica</a>
					</jmesa:htmlColumn>
					
				</jmesa:htmlRow>
				
			</jmesa:htmlTable>
		</jmesa:tableModel>
	</form>
	
	<div id="osservazioni_div" title="Osservazioni">
		<form id="ossForm" name="ossForm" action="vam.cc.diarioClinico.OsservazioniEdit.us" method="post">
			<input type="hidden" name="idDc" value="" />
			<textarea id="osservazioni" name="osservazioni" rows="16" cols="80"></textarea>
		</form>
	</div>
	
	<script type="text/javascript">
	$(function() 
	{
		$( "#osservazioni_div" ).dialog({
			height: screen.height/2,
			modal: true,
			autoOpen: false,
			closeOnEscape: true,
			show: 'blind',
			resizable: true,
			draggable: true,
			width: screen.width/2,
			buttons: {
				"Annulla": function() {
					$( this ).dialog( "close" );
				},
				"Salva": function() {
					document.ossForm.submit();
					//location.href=("vam.cc.diarioClinico.EditOsservazioni.us?idDc=${dc.id }&osservazioni=" + osservazioni);
					//confermaModifiche(true,riga);
					//$( this ).dialog( "close" );
				}
			}
		});
	});
	</script>
	
	<script type="text/javascript">
		function editOsservazioni( id, testo )
		{
			document.ossForm.idDc.value = id;
			document.ossForm.osservazioni.value = unescape(String( testo ).replace( /\+/g, " " ));//urlDecode( testo );
			$( '#osservazioni_div' ).dialog( 'open' );
		};
		
		function onInvokeAction(id)
		{
			setExportToLimit(id, '');
			createHiddenInputFieldsForLimitAndSubmit(id);
		};
		function onInvokeExportAction(id)
		{
			var parameterString = createParameterStringForLimit(id);
			location.href = 'vam.cc.diarioClinico.Detail.us?' + parameterString;
		};
	</script>
</div>