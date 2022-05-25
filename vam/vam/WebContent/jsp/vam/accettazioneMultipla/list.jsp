<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>	

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/Test.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<%
	long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	Date dataStart 		= new Date();
	long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
	Date dataEnd  	= new Date();
	BUtente utente = (BUtente)request.getSession().getAttribute("utente");
	long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
	System.out.println("Jsp in esecuzione: " + "vam.accettazione.list(inizio)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
			   "Memoria usata: " + (memFreeStart - memFreeEnd) +
			   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
	memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	dataStart 		= new Date();
	
	
	String errore_apertura_accettazione =(String)request.getAttribute("errore_apertura_nuova_accettazione");
%>

<%
if(errore_apertura_accettazione !=null && !errore_apertura_accettazione.equals(""))
{
%>
	<div style="color:red;"><%=errore_apertura_accettazione %></div><br>
<%
}
%>

<c:if test="${accettazioni.size() > 0 }">
	
	
	<c:set var="tipo2" scope="request" value="stampaAccMultipla"/>
	<c:set var="idAccMultipla" scope="request" value="${accettazione.idAccMultipla}"/>
	<c:import url="../../jsp/documentale/home4.jsp"/>

	<form action="vam.accettazione.List.us" method="post">
		<input type="hidden" name="idAnimale" value="${animale.id }" />
		<jmesa:tableModel items="${accettazioni }" id="accettazioni" var="accettazione" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" columnSort="it.us.web.util.jmesa.CustomColumnSort">
			<jmesa:htmlTable caption="Elenco Accettazioni Multiple">
				<jmesa:htmlRow>
					<jmesa:htmlColumn property="progressivoFormattato" title="Progressivo" />
					<jmesa:htmlColumn property="data" pattern="dd/MM/yyyy" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="it.us.web.util.jmesa.DateFilterEditor"/>
					<jmesa:htmlColumn title="Richiedente" property="lookupTipiRichiedente.description" filterEditor="it.us.web.util.jmesa.vam.TipiRichiedentiDroplistFilterEditor" />
					<jmesa:htmlColumn filterable="false" sortable="false" property="id" title="Operazioni" >
						<a href="vam.accettazione.Detail.us?id=${accettazione.id }" onclick="attendere()" >Dettaglio</a>
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
			location.href = 'vam.accettazione.List.us?' + parameterString;
		}
		function nuovaAccettazione(idAnimale, idUtente)
		{
			Test.possibileAprire(idAnimale, idUtente,
 												{
   														callback:function(msg) 
   														{ 
   															if(msg!=null && msg!='')
   															{
   																alert(msg);
   																return false;
   															}
   															else
   															{
   																attendere(),location.href='vam.accettazione.ToAdd.us?idAnimale='+idAnimale+'&from=list';	
   															}
   														},
   														async: false,
   														timeout:5000,
   														errorHandler:function(message, exception)
   														{
   														    //Session timedout/invalidated
   														    if(exception && exception.javaClassName=='org.directwebremoting.impl.LoginRequiredException')
   														    {
   														        alert(message);
   														        //Reload or display an error etc.
   														        window.location.href=window.location.href;
   														    }
   														    else
   														        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
   														 }
										 			});
		}
		
		
		
		function nuovaAccettazionePregressa(idAnimale, idUtente)
		{
			Test.possibileAprire(idAnimale, idUtente,
 												{
   														callback:function(msg) 
   														{ 
   															if(msg!=null && msg!='')
   															{
   																if(myConfirm(msg+'. Si desidera proseguire per il recupero di accettazioni pregresse?'))
   																	location.href='vam.accettazione.ToAdd.us?idAnimale='+idAnimale;	
   																else
   																	return false;
   															}
   															else
   															{
   																attendere(),location.href='vam.accettazione.ToAdd.us?idAnimale='+idAnimale;	
   															}
   														},
   														async: false,
   														timeout:5000,
   														errorHandler:function(message, exception)
   														{
   														    //Session timedout/invalidated
   														    if(exception && exception.javaClassName=='org.directwebremoting.impl.LoginRequiredException')
   														    {
   														        alert(message);
   														        //Reload or display an error etc.
   														        window.location.href=window.location.href;
   														    }
   														    else
   														        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
   														 }
										 			});
		}
	</script>
	
</c:if>

<c:if test="${accettazioni.isEmpty()}">
	<br/> Nessuna accettazione precedente.
</c:if>


<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.list(fine)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>

<script>
dwr.engine.setErrorHandler(errorHandler);
dwr.engine.setTextHtmlHandler(errorHandler);

function errorHandler(message, exception){
    //Session timedout/invalidated

    if(exception && exception.javaClassName== 'org.directwebremoting.impl.LoginRequiredException'){
        alert(message);
        //Reload or display an error etc.
        window.location.href=window.location.href;
    }
    else
        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
 }
</script>
