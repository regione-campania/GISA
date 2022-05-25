<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="it.us.web.permessi.Permessi"%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/azionijavascript.js"></script>


<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>

<c:choose>
	<c:when test="${cc.accettazione.animale.necroscopiaNonEffettuabile==false && cc.autopsia==null}">
		<input id="aggiungi_richiesta" type="button" value="Aggiungi Richiesta" 
			<c:if test="${cc.autopsia==null}">
				onclick="if(${cc.dataChiusura!=null}){ 
    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){location.href='vam.cc.autopsie.ToAdd.us'}
    				}else{location.href='vam.cc.autopsie.ToAdd.us'}"
			</c:if>
			<c:if test="${cc.autopsia!=null}">
				onclick="alert('Esame necroscopico già inserito per questa CC')"
			</c:if>
		/>
		<c:if test="${cc.dataChiusura==null}">
			<input type="button" id="necroscopia_non_effettuabile" value="Esame Necroscopico Non Effettuabile" onclick="if(myConfirm('Sicuro di voler procedere?')){location.href='vam.cc.autopsie.NecroscopiaNonEffettuabile.us?flag=true';}"/>
		</c:if>
	</c:when>
	<c:when test="${cc.accettazione.animale.necroscopiaNonEffettuabile==true}">
		<script>
			document.getElementById("aggiungi_richiesta").disabled=true;
			document.getElementById("necroscopia_non_effettuabile").disabled=true;
		</script>
		<br><b>Esame Necroscopico dichiarato non effettuabile</b>
		<c:if test="${cc.dataChiusura==null}">
			<input type="button" id="necroscopia_non_effettuabile_riapri" value="Rendi effettuabile" onclick="if(myConfirm('Sicuro di voler procedere?')){location.href='vam.cc.autopsie.NecroscopiaNonEffettuabile.us?flag=false';}"/><br>
		</c:if>
	</c:when>
</c:choose>

<h4 class="titolopagina">
     		Richieste Esami Necroscopici
</h4>

<div class="area-contenuti-2">
	<form action="vam.cc.autopsie.List.us" method="post">
		
		<jmesa:tableModel items="${autopsie}" id="a" var="a" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>
					<jmesa:htmlColumn property="dataAutopsia" 		filterable="false" title="Data richiesta" 	pattern="dd/MM/yyyy" filterEditor="it.us.web.util.jmesa.DateFilterEditor">
						<fmt:formatDate pattern="dd/MM/yyyy" value="${a.dataAutopsia}"  />
					</jmesa:htmlColumn>
					<jmesa:htmlColumn property="dataEsito" 		filterable="false" title="Data Necroscopia" 	pattern="dd/MM/yyyy" filterEditor="it.us.web.util.jmesa.DateFilterEditor">
						<fmt:formatDate pattern="dd/MM/yyyy" value="${a.dataEsito}"  />
					</jmesa:htmlColumn>
					<jmesa:htmlColumn property="lass" 		filterable="false" title="Sala settoria Destinazione"/>
					<jmesa:htmlColumn sortable="false" filterable="false" title="Operazioni">
							<a href="vam.cc.autopsie.Detail.us?id=${a.id}" onclick="attendere();">Dettaglio</a>
								<c:if test="${cc.accettazione.animale.necroscopiaNonEffettuabile==false}">
									<c:if test="${(utenteStrutturaNecroscopia || (utente.clinica.id==a.enteredBy.clinica.id && !accettato) || utente.ruoloByTalos=='HD1' || utente.ruoloByTalos=='HD2') && canEditRichiesta}">
										<a href="vam.cc.autopsie.ToEditRichiesta.us?id=${a.id}" onclick="attendere();">Modifica</a>
									</c:if>
									<c:if test="${utenteStrutturaNecroscopia || utente.ruoloByTalos=='HD1' || utente.ruoloByTalos=='HD2'}">
										<a href="vam.cc.autopsie.ToEdit.us?id=${a.id}" onclick="attendere();">
											<c:if test="${a.dataEsito!=null}">
												Modifica Necroscopia
											</c:if>
											<c:if test="${a.dataEsito==null}">
												Inserisci Necroscopia
											</c:if>
										</a>
									</c:if>
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
			location.href = 'vam.cc.autopsie.List.us?' + parameterString;
		}
	</script>
</div>