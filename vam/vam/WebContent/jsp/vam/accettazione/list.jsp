<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
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

<table>
<tr>
<td style="width:40%;">
<fieldset>
	<legend>Dettagli Animale "${animale.identificativo }"</legend>
	<ul>
		<li>Identificativo: ${animale.identificativo }</li>
		<li>Tatuaggio/II MC: ${animale.tatuaggio }</li>
		<li>Tipologia: ${animale.lookupSpecie.description }</li>
		<c:choose>
			<c:when test="${animale.lookupSpecie.id==3}">
				<li>
					<b>Et&agrave;:</b> 
					<fmt:formatDate type="date" value="${animale.dataNascita }" pattern="dd/MM/yyyy" />
					<c:if test="${animale.eta!=null}">${animale.eta}</c:if><c:if test="${dataNascita!=null}">(${dataNascita})</c:if>
				</li> 
			</c:when>
			<c:otherwise>
				<li>
					<b>Data nascita:</b> 
					<fmt:formatDate type="date" value="${animale.dataNascita }" pattern="dd/MM/yyyy" />
				</li>
 					${dataNascita}
			</c:otherwise>
		</c:choose>
		<li>Data Decesso: <c:if test="${dataMorte}"> ${dataMorte} - ${dataDecessoCertezza} </c:if> </li>
		
		<c:set scope="request" var="anagraficaAnimale" value="${anagraficaAnimale}"/>
        <c:import url="../vam/accettazione/anagraficaAnimale.jsp"/>
		
	</ul>
	</br>
</fieldset>
</td>
<td style="width:60%;">
<fieldset>
	<c:choose>
		<c:when test="${animale.lookupSpecie.id == specie.sinantropo }">
			<legend>Detentore</legend>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${colonia!=null}">
					<legend>Colonia</legend>
				</c:when>
				<c:otherwise>
					<legend>Proprietario <c:if test="${proprietarioTipo!=null && proprietarioTipo!='null'}">${proprietarioTipo}</c:if></legend>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	<ul>
		<c:choose>
			<c:when test="${colonia!=null}">
				<fmt:formatDate pattern="dd/MM/yyyy" var="dataRegistrazioneColonia" value="${colonia.dataRegistrazioneColonia}"/>
				<li><b>Colonia: </b>${colonia.numeroProtocollo}
										 <c:if test="${dataRegistrazioneColonia!=null}">
										 	registrata il ${dataRegistrazioneColonia}
										 </c:if>
				</li>
				<li><b>Indirizzo: </b>${colonia.indirizzoColonia}, ${colonia.cittaColonia} 
											<c:if test="${colonia.provinciaColonia!=''}">
												(${colonia.provinciaColonia})						
											</c:if>
											<c:if test="${colonia.cap!=''}">
												- ${colonia.cap}						
											</c:if>
				<li><b>Nominativo Referente: </b>${colonia.cognomeReferente}</li>
				<li><b>Codice fiscale Referente: </b>${colonia.codiceFiscaleReferente}</li>
				<li><b>Documento Identità Referente: </b>${colonia.documentoIdentita}</li>
				<li><b>Telefono Referente: </b>${colonia.telefonoReferente}</li>
				</br>
				</br>
			</c:when>
			<c:when test="${proprietarioTipo=='Importatore' || proprietarioTipo=='Operatore Commerciale'}">
				<li><b>Ragione Sociale: </b>${proprietarioNome }</li>
				<li><b>Partita Iva: </b>${proprietarioCognome }</li>
				<li><b>Rappr. Legale: </b>${proprietarioCodiceFiscale }</li>
				<li><b>Telefono struttura(principale): </b>${proprietarioTelefono }</li>
				<li><b>Telefono struttura(secondario): </b>${proprietarioDocumento}</li>
				<u>Sede operativa</u>
				<li><b>Indirizzo: </b>${proprietarioIndirizzo }</li>
				<li><b>CAP: </b>${proprietarioCap }</li>
				<li><b>Comune: </b>${proprietarioComune }</li>
				<li><b>Provincia: </b>${proprietarioProvincia }</li>
			</c:when>
			<c:otherwise>
			<c:if test="${proprietarioTipo=='Canile'}">
				<li><b>Ragione Sociale: </b>${proprietarioNome }</li>
				<li><b>Partita Iva: </b>${proprietarioCognome }</li>
				<li><b>Rappr. Legale: </b>${proprietarioCodiceFiscale }</li>
			</c:if>
			<c:if test="${proprietarioTipo!='Canile'}">
				<c:if test="${!animale.randagio}">
					<li><b>Cognome: </b>${proprietarioCognome }</li>
				</c:if>
				<li><b>Nome:</b>${proprietarioNome }</li>
			</c:if>
				<c:if test="${!animale.randagio}">
				<c:if test="${proprietarioTipo!='Canile'}">
					<li><b>Codice Fiscale: </b>${proprietarioCodiceFiscale }</li>
				</c:if>
					<li><b>Documento: </b>${proprietarioDocumento }</li>
					<li><b>Indirizzo: </b>${proprietarioIndirizzo }</li>
					<li><b>CAP: </b>${proprietarioCap }</li>
					<li><b>Comune: </b>${proprietarioComune }</li>
					<li><b>Provincia: </b>${proprietarioProvincia }</li>
					<li><b>Telefono: </b>${proprietarioTelefono }</li>
				</c:if>
				<c:if test="${animale.randagio}">
					</br></br></br></br></br></br></br></br>
				</c:if>
			</c:otherwise>
		</c:choose>
	</ul>


</fieldset>
</td>
</tr>
</table>
<%
if(errore_apertura_accettazione !=null && !errore_apertura_accettazione.equals(""))
{
%>
	<div style="color:red;"><%=errore_apertura_accettazione %></div><br>
<%
}
%>
<c:if test="${tr == null }">
	<c:if test="${utente.ruolo=='Referente Asl' || utente.ruolo=='14'}">
		<input type="button" onclick="nuovaAccettazionePregressa('${animale.id}','${utente.id}')" value="Nuova Accettazione" />
	</c:if>
	<c:if test="${utente.ruolo!='Referente Asl' && utente.ruolo!='14' && ((utente.ruolo!='Universita' && utente.ruolo!='IZSM' && utente.ruolo!='6' && utente.ruolo!='8') || animale.dataMorte!=null)}">
		<input type="button" onclick="nuovaAccettazione('${animale.id}','${utente.id}')" value="Nuova Accettazione" />
	</c:if>
	
</c:if>
<c:if test="${tr != null }">
	<a onclick="$( '#dettaglio_${tr.id }_div' ).dialog( 'open' );" style="cursor: pointer; text-decoration: underline; color: blue;">Dettaglio Richiesta Trasferimento in Ingresso</a>
	<input type="button" onclick="attendere(),location.href='vam.cc.trasferimenti.ToAccettazioneDestinatario.us?id=${tr.id }'" value="Accettazione Trasferimento in Ingresso" />
	<%@ include file="../cc/trasferimenti/detailInclude.jsp" %>
</c:if>
<c:if test="${accettazioni.size() > 0 }">
	
	<form action="vam.accettazione.List.us" method="post">
		<input type="hidden" name="idAnimale" value="${animale.id }" />
		<jmesa:tableModel items="${accettazioni }" id="accettazioni" var="accettazione" filterMatcherMap="it.us.web.util.jmesa.MyFilterMatcherMap" columnSort="it.us.web.util.jmesa.CustomColumnSort">
			<jmesa:htmlTable caption="Accettazioni Precedenti">
				<jmesa:htmlRow>
					<jmesa:htmlColumn property="progressivoFormattato" title="Progressivo" />
					<jmesa:htmlColumn property="data" pattern="dd/MM/yyyy" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="it.us.web.util.jmesa.DateFilterEditor"/>
					<jmesa:htmlColumn title="Richiedente" property="lookupTipiRichiedente.description" filterEditor="it.us.web.util.jmesa.vam.TipiRichiedentiDroplistFilterEditor" />
					<jmesa:htmlColumn filterable="false" sortable="false" property="id" title="Operazioni" >
					<c:if test="${accettazione.enteredBy.clinica.id==utente.clinica.id}">
						<a href="vam.accettazione.Detail.us?id=${accettazione.id }" onclick="attendere()" >Dettaglio</a>
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
