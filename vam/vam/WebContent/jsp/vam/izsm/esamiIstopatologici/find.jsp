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

<!--  
<h4 class="titolopagina">
     		Ricerca Esame Istopatologico
</h4>  

<table class="tabella">
	    
    <tr>
	    <td> 
	    	Numero Esame
	    </td>
	    <td>
		    <form action="vam.izsm.esamiIstopatologici.Find.us" method="post">			
				<input type="text" name="numeroEsame" maxlength="64" /> 
				<input type="submit" value="Cerca" />
			</form>
		</td>
    </tr>
</table>
-->
 
<h4 class="titolopagina">
     		Lista Richieste Istopatologici
</h4>

<div class="area-contenuti-2">
	<form action="vam.izsm.esamiIstopatologici.ToFind.us" method="post">
		
		<jmesa:tableModel items="${esamiIstopatologici}" id="ei" var="ei" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>

					<jmesa:htmlColumn property="numero" 			title="Numero Esame"/>
					<jmesa:htmlColumn property="identificativoAnimale"     title="Microchip">
						${ei.getIdentificativoAnimale() }
					</jmesa:htmlColumn>
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
					<jmesa:htmlColumn property="enteredBy" 		title="Richiesto Da">
						${ei.enteredBy.ruoloByTalos}
					</jmesa:htmlColumn>
					<jmesa:htmlColumn property="" title="Diagnosi"  sortable="false" filterable="false">
						
					<c:choose>
  <c:when test="${ei.dataEsito!=null && ei.whoUmana==null}">
   - Diagnosi non tumorale - ${ei.diagnosiNonTumorale }
  </c:when>
   <c:otherwise>
    ${ei.tipoDiagnosi} ${ei.whoUmana } ${ei.diagnosiNonTumorale }
					
  </c:otherwise>
</c:choose>
					
					
					
					</jmesa:htmlColumn>
														
					<jmesa:htmlColumn sortable="false" filterable="false" title="Operazioni">
							
							<c:choose>
								<c:when test="${ei.dataEsito!=null}">
									<a href="vam.izsm.esamiIstopatologici.Find.us?numeroEsame=${ei.numero}" onclick="attendere();">
										Dettaglio
									</a>
								</c:when>
			    				<c:otherwise>
			    					<a href="vam.izsm.esamiIstopatologici.ToEdit.us?numeroEsame=${ei.numero}" onclick="attendere();">
										Dettaglio
									</a>
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