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
     		Lista Sinantropi in BDR
</h4>    
	    			 
<div class="area-contenuti-2">
	<form action="sinantropi.ListZoo.us" method="post">
				
		<jmesa:tableModel items="${sinantropi}" id="sinantropi" var="s" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>
					<jmesa:htmlColumn property="numeroAutomatico"  title="Identificativo in BDR"/>
					
					<c:if test="${s.numeroUfficiale!=''}">					
						<jmesa:htmlColumn property="numeroUfficiale"   title="Numero Ufficiale Istituto Faunistico"/>
					</c:if>
					<c:if test="${s.mc!=''}">											
						<jmesa:htmlColumn property="mc"  title="Microchip" />
					</c:if>
					<c:if test="${s.codiceIspra!=''}">					
						<jmesa:htmlColumn property="codiceIspra"   title="Codice Ispra"/>
					</c:if>
					
					<jmesa:htmlColumn property="lookupSpecieSinantropi.description" title="Genere-Famiglia"/>						
										
					<jmesa:htmlColumn property="lastOperation"	title="Ultimo stato"/>
										
					<jmesa:htmlColumn property="dataDecesso" filterable="false"	title="Data decesso">
						<fmt:formatDate value="${s.dataDecesso}" pattern="dd/MM/yyyy" var="dataDecesso"/>
    			 		<c:out value="${dataDecesso}"></c:out>					
					</jmesa:htmlColumn>
					
					
					<jmesa:htmlColumn sortable="false" filterable="false" 				title="Operazioni">
							<a href="sinantropi.DetailZoo.us?idSinantropo=${s.id}">Dettaglio</a>										
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
			location.href = 'sinantropi.ListZoo.us?' + parameterString;
		}
	</script>
</div>