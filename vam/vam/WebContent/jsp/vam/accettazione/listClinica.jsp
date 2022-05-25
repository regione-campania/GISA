<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="org.jmesa.view.html.editor.DroplistFilterEditor"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.jmesa.core.filter.FilterMatcher"%>

<%
	long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	Date dataStart 		= new Date();
	long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
	Date dataEnd  	= new Date();
	BUtente utente = (BUtente)request.getSession().getAttribute("utente");
	long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
	
	System.out.println("Jsp in esecuzione: " + "vam.accettazione.listClinica(inizio)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
			   "Memoria usata: " + (memFreeStart - memFreeEnd) +
			   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
	memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	dataStart 		= new Date();
%>

<fieldset>
	<legend>Nuova Accettazione</legend>
	<form id="newAccettazione" action="vam.accettazione.FindAnimale.us" method="post">
		<font size="+1">Identificativo Animale:</font> 
		<input type="text" name="identificativo" id="identificativo" maxlength="15" /> 
		<input type="submit" value="Prosegui" onclick="if($('#identificativo')[0].value.length > 0){attendere();return true;}else{alert('Inserire l\'identificativo di un animale');return false;}" />
		<input type="button" value="Animale Deceduto senza Identificativo" onclick="attendere(),location.href='vam.accettazione.ToAddDecedutoNonAnagrafe.us'" />
		<input type="button" value="Annulla" onclick="location.href='vam.accettazione.Home.us';" />
	</form>
</fieldset>
<br/>

<!--  c:if test="${!empty accettazioniClinica}" -->
	<form action="vam.accettazione.ListClinica.us" method="post">
		<input type="hidden" name="idAnimale" value="${animale.id }" />
		<jmesa:tableModel limit="${limit}" items="${accettazioniClinica }" id="accettazioni" var="accettazione" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" columnSort="it.us.web.util.jmesa.CustomColumnSort">
			<jmesa:htmlTable caption="Elenco Accettazioni" width="100%">
				<jmesa:htmlRow>
					<%--jmesa:htmlColumn property="aprireCartellaClinica" title="Stato" 
						filterEditor="it.us.web.util.jmesa.AccettazioneStatoFilterEditorAndMatcher" 
						 /--%>
					<jmesa:htmlColumn property="progressivoFormattato" title="Progressivo" />
					<jmesa:htmlColumn property="data" cellEditor="it.us.web.util.jmesa.DateCellEditor" filterEditor="it.us.web.util.jmesa.DateFilterEditor"/>
					<jmesa:htmlColumn property="animale.identificativo" title="Identificativo Animale" filterEditor="it.us.web.util.jmesa.vam.SerialNumberLenghtEditor"/>
					<jmesa:htmlColumn title="Richiedente" property="lookupTipiRichiedente.description" filterEditor="it.us.web.util.jmesa.vam.TipiRichiedentiDroplistFilterEditor" />
					<jmesa:htmlColumn filterable="false" sortable="false" property="id" title="Operazioni" >
						<a href="vam.accettazione.Detail.us?id=${accettazione.id }" onclick="attendere()" >Dettaglio</a>
					</jmesa:htmlColumn> 
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:tableModel>
	</form>
<!--/c:if-->
<!--c:if test="${empty accettazioniClinica}" -->
	<!--  Nessuna Accettazione Aperta -->
<!--/c:if-->
<script type="text/javascript">
	function onInvokeAction(id)
	{
		setExportToLimit(id, '');
		createHiddenInputFieldsForLimitAndSubmit(id);
	}
	function onInvokeExportAction(id)
	{
		var parameterString = createParameterStringForLimit(id);
		location.href = 'vam.accettazione.ListClinica.us?' + parameterString;
	}
	document.getElementById('animale.identificativo').innerHTML='<%=((request.getAttribute("accettazioni_f_animale.identificativo")==null)?(""):(request.getAttribute("accettazioni_f_animale.identificativo")))%>';
</script>


<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.listClinica(fine)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>