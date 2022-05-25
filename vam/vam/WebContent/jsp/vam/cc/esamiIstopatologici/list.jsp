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
<input type="button" value="Aggiungi Richiesta" onclick="if(${cc.dataChiusura!=null}){ 
	if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){location.href='vam.cc.esamiIstopatologici.ToAdd.us'}
	}else{attendere();location.href='vam.cc.esamiIstopatologici.ToAdd.us';}">
	

	<!-- input type="button" value="Stampa richiesta multipla" onclick="location.href='vam.cc.esamiIstopatologici.StampaIstoMultiplo.us?'" /-->

<c:if test="${not empty cc.esameIstopatologicos}">
<br>
<c:set var="tipo" scope="request" value="stampaIstoMultiplo" />
<c:import url="../../jsp/documentale/home.jsp"/>
<br>
</c:if>

<h4 class="titolopagina">
     		Richieste Esami Istopatologici
</h4>



<div class="area-contenuti-2">
	<form action="vam.cc.esamiIstopatologici.List.us" method="post">
		
		<jmesa:tableModel items="${istopatologici}" id="ei" var="ei" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>

					<jmesa:htmlColumn property="numero" 			title="Numero Esame" filterable="false" sortable="false"/>
					
					<jmesa:htmlColumn property="dataRichiesta" 		title="Data Richiesta" 	pattern="dd/MM/yyyy" filterEditor="it.us.web.util.jmesa.DateFilterEditor">
						<fmt:formatDate pattern="dd/MM/yyyy" value="${ei.dataRichiesta}"  />
					</jmesa:htmlColumn>
				    <jmesa:htmlColumn property="dataEsito" 			title="Data Esito" 		pattern="dd/MM/yyyy" filterEditor="it.us.web.util.jmesa.DateFilterEditor">
						<fmt:formatDate pattern="dd/MM/yyyy" value="${ei.dataEsito}"  />
					</jmesa:htmlColumn>
					<jmesa:htmlColumn property="sedeLesione" 		title="Sede Lesione" sortable="false"/>
					
					<jmesa:htmlColumn property="numeroRifMittente" 		title="Numero Rif.Mittente">
						${ei.numeroAccettazioneSigla}
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn property="" title="Diagnosi"  sortable="false" filterable="false">
						${ei.whoUmana } <c:if test="${ei.tipoDiagnosi.id==3}">${ei.diagnosiNonTumorale }</c:if> 
					</jmesa:htmlColumn>
														
					<jmesa:htmlColumn sortable="false" filterable="false" title="Operazioni">
							<a href="vam.cc.esamiIstopatologici.Detail.us?id=${ei.id}" onclick="attendere();" >Dettaglio</a>
							<c:choose>
								<c:when test="${utente.ruolo!='IZSM' && utente.ruolo!='Universita' && utente.ruolo!='6' && utente.ruolo!='8'}">
									<a id="mod" href="vam.cc.esamiIstopatologici.ToAdd.us?modify=on&idEsame=${ei.id}" onclick="if(${cc.dataChiusura!=null}){ 
			    				return myConfirm('Cartella Clinica chiusa. Vuoi procedere?');
	    						}else{ attendere();return true;}">Modifica</a>
								</c:when>
								<c:otherwise>
									<a id="ie" href="vam.cc.esamiIstopatologici.ToAdd.us?modify=on&idEsame=${ei.id}&editIzsm=on" onclick="if(${cc.dataChiusura!=null}){ 
			    				return myConfirm('Cartella Clinica chiusa. Vuoi procedere?');
	    						}else{attendere();return true;}">Inserisci Esito</a>
								</c:otherwise>
							</c:choose>
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
			location.href = 'vam.cc.esamiIstopatologici.List.us?' + parameterString;
		}
	</script>
</div>