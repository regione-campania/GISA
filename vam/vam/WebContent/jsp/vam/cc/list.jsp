<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<%
	long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	Date dataStart 		= new Date();
	long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
	Date dataEnd  	= new Date();
	BUtente utente = (BUtente)request.getSession().getAttribute("utente");
	long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
	System.out.println("Jsp in esecuzione: " + "vam.cc.list(inizio)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
			   "Memoria usata: " + (memFreeStart - memFreeEnd) +
			   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
	memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	dataStart 		= new Date();
%>


<script language="JavaScript" type="text/javascript" src="js/azionijavascript.js"></script>

<h4 class="titolopagina">
     		<c:choose>
    			<c:when test="${tipoFind == 'all'}">					
					Lista delle cartella cliniche della clinica
				</c:when>
				<c:otherwise>
		    		Lista delle cartella cliniche per l'animale con identificativo ${numeroMC}
				</c:otherwise>	
			</c:choose>     		
</h4>    
	    			 
<div class="area-contenuti-2">
	<form action="vam.cc.Find.us" method="post">
		
		<input type="hidden" name="tipoFind" value="${tipoFind}"/>
		<input type="hidden" name="numeroMC" value="<c:out value="${numeroMC}"/>"/>
		
		<jmesa:tableModel limit="${limit}" items="${cartelleCliniche}" id="cartelleCliniche" var="cc" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap">
			<jmesa:htmlTable styleClass="tabella">
				<jmesa:htmlRow>
					<jmesa:htmlColumn property="numero"  title="Numero Cartella Clinica"/>
					
					<jmesa:htmlColumn property="fascicoloSanitario.numero"  title="Fascicolo Sanitario"/>
										
					<c:if test="${tipoFind == 'all'}">
						<jmesa:htmlColumn property="accettazione.animale.identificativo"  title="Identificativo Animale">
							${cc.accettazione.animale.identificativo}	
						</jmesa:htmlColumn>											
					</c:if>
															
					<jmesa:htmlColumn width="15%" property="dataApertura" 	title="Data di apertura" filterEditor="it.us.web.util.jmesa.DateFilterEditor">
						<fmt:formatDate value="${cc.dataApertura}" pattern="dd/MM/yyyy" var="dataApertura"/>
    			 		<c:out value="${dataApertura}"></c:out>					
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn sortable="false" width="15%"  property="dataChiusura" 	title="Data di chiusura" filterEditor="it.us.web.util.jmesa.DateFilterEditor">
						<fmt:formatDate value="${cc.dataChiusura}" pattern="dd/MM/yyyy" var="dataChiusura"/>
    			 		<b><c:out value="${dataChiusura}"></c:out></b>					
					</jmesa:htmlColumn>
										
 					<jmesa:htmlColumn  sortable="false" property="lastDiagnosi.diagnosiEffettuate" title="Diagnosi">
						${cc.lastDiagnosi.diagnosiEffettuate}	
					</jmesa:htmlColumn>
					
					<jmesa:htmlColumn  sortable="true" property="tipologia" title="Tipologia" filterEditor="it.us.web.util.jmesa.vam.TipologiaCcDroplistFilterEditor">
						${cc.tipologia}	
					</jmesa:htmlColumn>
										
					<jmesa:htmlColumn sortable="false" filterable="false" 	title="Operazioni">
						<c:if test="${cc.enteredBy.clinica.id==utente.clinica.id}">
							<a href="vam.cc.Detail.us?idCartellaClinica=${cc.id}" onclick="attendere()">Dettaglio</a>
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
			location.href = 'vam.cc.Find.us?' + parameterString;
		}
	</script>
</div>


<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.cc.list(fine)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>