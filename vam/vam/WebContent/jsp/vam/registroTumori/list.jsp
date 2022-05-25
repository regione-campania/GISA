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

<h4 class="titolopagina">
     		Registro Tumori
</h4>    
	    			 
<div class="area-contenuti-2">
	<form action="vam.registroTumori.List.us" method="post">
		
		<jmesa:tableModel items="${listEsami}" id="listEsami" var="e" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap">

		<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>
					
					<jmesa:htmlColumn width="15%" property="dataRichiesta" title="Data Richiesta" cellEditor="it.us.web.util.jmesa.DateCellEditor" filterEditor="it.us.web.util.jmesa.DateFilterEditor"/>
					
					<jmesa:htmlColumn property="cartellaClinica.accettazione.animale.identificativo" title="Identificativo" sortable="false" filterable="false">
												
						<c:choose>
			    			<c:when test="${e.outsideCC == true}">					
								 ${e.animale.identificativo} 
							</c:when>
							<c:otherwise>
								${e.cartellaClinica.accettazione.animale.identificativo} 
							</c:otherwise>	
						</c:choose>	
																		
					</jmesa:htmlColumn>		
										
<%-- 					<jmesa:htmlColumn width="10%" property="cartellaClinica.accettazione.animale.dataNascita" 	title="Data Di Nascita" filterable="false"> --%>
						    			 		
<%--     			 		<c:choose> --%>
<%-- 			    			<c:when test="${e.outsideCC == true}">					 --%>
<%-- 								<fmt:formatDate value="${e.animale.dataNascita}" pattern="dd/MM/yyyy" var="dataNascita"/> --%>
<%--     			 				<c:out value="${dataNascita}"></c:out>	 --%>
<%-- 							</c:when> --%>
<%-- 							<c:otherwise> --%>
<%-- 								<fmt:formatDate value="${e.cartellaClinica.accettazione.animale.dataNascita}" pattern="dd/MM/yyyy" var="dataNascita"/> --%>
<%--     			 				<c:out value="${dataNascita}"></c:out>	 --%>
<%-- 							</c:otherwise>	 --%>
<%-- 						</c:choose>	 --%>
    			 		    			 						
<%-- 					</jmesa:htmlColumn> --%>
										
										
					<jmesa:htmlColumn width="4%"  filterEditor="org.jmesa.view.html.editor.DroplistFilterEditor" property="cartellaClinica.accettazione.animale.sesso" title="Sesso" sortable="false">
												
						<c:choose>
			    			<c:when test="${e.outsideCC == true}">					
								<c:if test="${(e.animale.lookupSpecie.id == specie.cane or e.animale.lookupSpecie.id == specie.gatto) && e.animale.decedutoNonAnagrafe==false}">${e.animale.sesso}</c:if>
							  <c:if test="${e.animale.decedutoNonAnagrafe==true}">${e.animale.sesso}</c:if>
						      <c:if test="${e.animale.lookupSpecie.id == specie.sinantropo && e.animale.decedutoNonAnagrafe==false}">${e.animale.sesso}</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${(e.cartellaClinica.accettazione.animale.lookupSpecie.id == specie.cane or e.cartellaClinica.accettazione.animale.lookupSpecie.id == specie.gatto) && e.cartellaClinica.accettazione.animale.decedutoNonAnagrafe==false}">${e.cartellaClinica.accettazione.animale.sesso}</c:if>
							  <c:if test="${e.cartellaClinica.accettazione.animale.decedutoNonAnagrafe==true}">${e.cartellaClinica.accettazione.animale.sesso}</c:if>
						      <c:if test="${e.cartellaClinica.accettazione.animale.lookupSpecie.id == specie.sinantropo}">${e.cartellaClinica.accettazione.animale.sesso}</c:if>
							</c:otherwise>	
						</c:choose>	
												
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn filterEditor="org.jmesa.view.html.editor.DroplistFilterEditor" property="cartellaClinica.accettazione.animale.lookupSpecie.description" title="Specie" sortable="false" >
												
						<c:choose>
			    			<c:when test="${e.outsideCC == true}">					
								${e.animale.lookupSpecie.description}
							</c:when>
							<c:otherwise>
								${e.cartellaClinica.accettazione.animale.lookupSpecie.description}
							</c:otherwise>	
						</c:choose>	
												 
					</jmesa:htmlColumn>	
															
					<jmesa:htmlColumn  property="whoUmana" title="Diagnosi" sortable="false">
						${e.whoUmana } 
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn sortable="false" filterable="false" title="Dettaglio">
							<a href="vam.registroTumori.Detail.us?id=${e.id}" onclick="avviaPopup(this.href); return false;" target="_blank">Dettaglio</a>							
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
			location.href = 'vam.registroTumori.List.us?' + parameterString;
		}
	</script>
</div>