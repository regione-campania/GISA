<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.constants.Specie"%>
<%@page import="java.util.List"%>
<%@page import="it.us.web.bean.vam.AnimaleAnagrafica"%>
<%@page import="it.us.web.bean.vam.Animale"%>
<%@page import="it.us.web.util.vam.AnimaliUtil"%>
<%@page import="it.us.web.bean.vam.lookup.LookupMantelli"%>
<%@page import="it.us.web.bean.vam.lookup.LookupRazze"%>
<%@page import="java.util.Date"%>
<%@page import="it.us.web.util.vam.RegistrazioniUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@page import="it.us.web.bean.vam.Accettazione"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="it.us.web.util.vam.CaninaRemoteUtil"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/TestBdr.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<%
	Iterator<LookupRazze> iterRazzeCane = ((List<LookupRazze> )request.getServletContext().getAttribute("razzeCane")).iterator(); 
	Iterator<LookupRazze> iterRazzeGatto = ((List<LookupRazze> )request.getServletContext().getAttribute("razzeGatto")).iterator(); 
	Iterator<LookupMantelli> iterMantelliCane = ((List<LookupMantelli> )request.getServletContext().getAttribute("mantelliCane")).iterator(); 
	Iterator<LookupMantelli> iterMantelliGatto = ((List<LookupMantelli> )request.getServletContext().getAttribute("mantelliGatto")).iterator(); 
	Accettazione accettazione = ((Accettazione)request.getAttribute("accettazione"));
	Animale animale = accettazione.getAnimale();
	AnimaleAnagrafica anagraficaAnimale = (AnimaleAnagrafica)request.getAttribute("anagraficaAnimale");
%>

<script language="JavaScript" type="text/javascript" src="js/vam/accettazione/smaltimentoCarogna.js"></script>

<%
	long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	Date dataStart 		= new Date();
	long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
	Date dataEnd  	= new Date();
	BUtente utente = (BUtente)request.getSession().getAttribute("utente");
	long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
	System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(inizio)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
			   "Memoria usata: " + (memFreeStart - memFreeEnd) +
			   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
	memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	dataStart 		= new Date();
%>

<h4 class="titolopagina">
     Dettaglio Accettazione n° <b><%=accettazione.getProgressivoFormattato()%></b> <%=animale.getLookupSpecie().getDescription()%>: <b><%=animale.getIdentificativo()%></b>
</h4> 

<%
	 memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	 dataStart 		= new Date();
	 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
	 dataEnd  	= new Date();
	 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
	System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(inizio)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
			   "Memoria usata: " + (memFreeStart - memFreeEnd) +
			   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
	memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	dataStart 		= new Date();
%>

<c:choose>
	<c:when test="${accettazione.animale.decedutoNonAnagrafe == true}">						
		<fmt:formatDate type="date" value="${accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="dataMorte"/>
	</c:when>
	<c:otherwise>
		<fmt:formatDate type="date" value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/>
	</c:otherwise>	
</c:choose>	 


<%
	 memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	 dataStart 		= new Date();
	 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
	 dataEnd  	= new Date();
	 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
	System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(inizio)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
			   "Memoria usata: " + (memFreeStart - memFreeEnd) +
			   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
	memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	dataStart 		= new Date();
%>


<c:if test="${!accettazione.aprireCartellaClinica }">
	<c:forEach items="${accettazione.cartellaClinicas }" var="cc" >
		Cartella clinica associata: <a href="vam.cc.Detail.us?idCartellaClinica=${cc.id }" onclick="attendere()">${cc.numero }</a><br/>
	</c:forEach>
</c:if>


<%
	 memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	 dataStart 		= new Date();
	 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
	 dataEnd  	= new Date();
	 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
	System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(inizio)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
			   "Memoria usata: " + (memFreeStart - memFreeEnd) +
			   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
	memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	dataStart 		= new Date();
%>

<% 
		memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(foreachCc)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>



<c:if test="${!accettazione.aprireCartellaClinica and fn:length(accettazione.cartellaClinicas)==0 }">
	Non è prevista l'apertura della cc in quanto, per quest'accettazione, non sono stati richiesti esami da inserire<br/>
</c:if>

<input type="button" value="Stampa" onclick="stampa('vam.accettazione.Pdf.us?id=${accettazione.id}')"/>
<c:if test="${dataMorte!=null}">
	<input type="button" value="Stampa Certificato Decesso" onclick="stampa('vam.accettazione.StampaCertificatoDecesso.us?accettazione=${accettazione.id}')" />
</c:if>

<!--  c:if test="${fuoriAsl==false}"-->
  <br/>
  <c:choose>
  	<c:when test="${accettazione.animale.lookupSpecie.id == specie.sinantropo || accettazione.animale.decedutoNonAnagrafe==true}">
  		<input type="button" value="Modifica Anagrafica Animale" onclick="modificaAnagraficaAnimale(${accettazione.animale.lookupSpecie.id});" />
  	</c:when>
  	<c:otherwise>
  		<us:can f="BDR" sf="EDIT" og="MAIN" r="w">
  			<input type="button" value="Modifica Anagrafica Animale" onclick="modificaAnagraficaAnimale(${accettazione.animale.lookupSpecie.id});" />
  		</us:can>
  	</c:otherwise>
  </c:choose>
<!--  /c:if-->

<c:if test="${accettazione.cancellabile || utente.ruolo=='Referente Asl' || utente.ruolo=='14' }">
	<input type="button" value="Elimina" onclick="if( myConfirm('Sei sicuro di voler eliminare l\'accettazione?') ){location.href='vam.accettazione.Delete.us?id=${accettazione.id }'}" />
</c:if>

<%
	 memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	 dataStart 		= new Date();
	 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
	 dataEnd  	= new Date();
	 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
	System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(inizio)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
			   "Memoria usata: " + (memFreeStart - memFreeEnd) +
			   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
	memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	dataStart 		= new Date();
%>

<c:if test="${accettazione.modificabile || utente.ruolo=='Referente Asl' || utente.ruolo=='14' }">
	<input type="button" value="Modifica" onclick="attendere(),location.href='vam.accettazione.ToAdd.us?modify=true&id=${accettazione.id }'" />
</c:if>


<%
	 memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	 dataStart 		= new Date();
	 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
	 dataEnd  	= new Date();
	 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
	System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(inizio)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
			   "Memoria usata: " + (memFreeStart - memFreeEnd) +
			   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
	memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	dataStart 		= new Date();
%>

<us:can f="CC" sf="ADD" og="MAIN" r="w" >
<c:if test="${accettazione.aprireCartellaClinica}">
	<c:choose>
		<c:when test="${dataMorte!=null && dataMorte!=''}">
			<input type="button" value="Apri Cartella Necroscopica" 
			<c:if test="${accettazione.accettazionePiuRecente!=null}">
				<c:choose>
					<c:when test="${utente.ruolo!='Referente Asl' && utente.ruolo!='14'}">
						onclick="alert('Impossibile aprire la cc in quanto per quest\'animale è stata aperta un\'accettazione più recente');"
					</c:when>
					<c:otherwise>
						onclick="if( myConfirm('Esiste un\'accettazione più recente per quest\'animale, si desidera proseguire per il recupero di accettazioni pregresse?') ){location.href='vam.cc.Add.us?idAccettazione=${accettazione.id }'}"
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${accettazione.eseguireOperazioniBdr==false}">
				onclick="if( myConfirm('Sei sicuro di voler aprire la cartella necroscopica associata?') ){location.href='vam.cc.Add.us?idAccettazione=${accettazione.id }'}"
			</c:if>
			<c:if test="${accettazione.eseguireOperazioniBdr==true}">
				onclick="alert('Impossibile aprire la cc in quanto ci sono delle operazioni da fare in BDR richieste in fase di accettazione');"
			</c:if>
			
			 />
		</c:when>
		<c:otherwise>
			<input type="button" value="Apri Cartella Clinica" 
			<c:if test="${accettazione.accettazionePiuRecente!=null}">
				onclick="alert('Impossibile aprire la cc in quanto per quest\'animale è stata aperta un\'accettazione più recente');"
			</c:if>
			<c:if test="${accettazione.eseguireOperazioniBdr==false}">
				onclick="if( myConfirm('Sei sicuro di voler aprire la cartella clinica associata?') ){location.href='vam.cc.Add.us?idAccettazione=${accettazione.id }'}" 
			</c:if>
			<c:if test="${accettazione.eseguireOperazioniBdr==true}">
				onclick="alert('Impossibile aprire la cc in quanto ci sono delle operazioni da fare in BDR richieste in fase di accettazione');"
			</c:if>
			
			/>
		</c:otherwise>
	</c:choose>
</c:if>
</us:can>



<%
	 memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	 dataStart 		= new Date();
	 memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
	 dataEnd  	= new Date();
	 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
	System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(inizio)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
			   "Memoria usata: " + (memFreeStart - memFreeEnd) +
			   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
	memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	dataStart 		= new Date();
%>

<table class="tabella" style="width: 99%">
	<tr><th colspan="2">Dati Animale</th></tr>
	<tr class='even'><td>Identificativo:</td><td>${accettazione.animale.identificativo }</td></tr>
	<c:choose>
		<c:when test="${accettazione.animale.lookupSpecie.id==3}">
					<fmt:formatDate type="date" value="${accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita" />
					<tr class='odd'><td>Et&agrave;:</td><td>${accettazione.animale.eta} <c:if test="${dataNascita}">(${dataNascita})</c:if></td></tr>
		</c:when>
		<c:otherwise>
					<fmt:formatDate type="date" value="${accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
					<tr class='odd'><td>Data nascita:</td><td>${dataNascita}</td></tr>
		</c:otherwise>
	</c:choose>
	
	<c:set scope="request" var="anagraficaAnimale" value="${anagraficaAnimale}"/>
    <c:import url="../vam/accettazione/anagraficaAnimaleDetail.jsp"/>
	
	<c:if test="${accettazione.animale.clinicaChippatura!=null}">
		<tr class='even'><td>Microchippato nella clinica </td><td>${accettazione.animale.clinicaChippatura.nome}</td></tr>
	</c:if>
	<c:if test="${accettazione.animale.dataMorte!=null || res.dataEvento!=null}">
        <tr class='even'>
   			<td>
   				Data del decesso
   			</td>
   			<td>
				${dataMorte} - 
				<c:choose>
					<c:when test="${accettazione.animale.decedutoNonAnagrafe == true}">
						${accettazione.animale.dataMorteCertezza}
					</c:when>
					<c:otherwise>
						${res.dataMorteCertezza}
					</c:otherwise>	
				</c:choose>	 
        	</td>
   		</tr>
    	
   		<tr class='odd'>
      	  <td>
    			Probabile causa del decesso
   			</td>
   			<td>    
   				<c:choose>
    				<c:when test="${accettazione.animale.decedutoNonAnagrafe}">
    					${accettazione.animale.causaMorte.description}
    				</c:when>
    				<c:otherwise>
    					${res.decessoValue}
    				</c:otherwise>
    			</c:choose>	        	        
        	</td>
       	</tr>
       	</c:if>
	
	<!--  c:if test="${!accettazione.animale.decedutoNonAnagrafe }" -->
		<c:choose>
			<c:when test="${accettazione.animale.lookupSpecie.id == specie.sinantropo }">
				<tr><th colspan="2">Detentore</th></tr>
			</c:when>
			<c:otherwise>
				<c:set var="proprietarioCognome" value="${accettazione.proprietarioCognome}"/>
				<c:choose>
					<c:when test="${fn:startsWith(proprietarioCognome, '<b>')}">
						<th colspan="2">Colonia</th>
					</c:when>
					<c:otherwise>
						<th colspan="2">Proprietario ${accettazione.proprietarioTipo}</th>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

		<c:choose>
				<c:when test="${fn:startsWith(proprietarioCognome, '<b>')}">
					<tr class='even'><td>Colonia</td><td colspan="2">${accettazione.nomeColonia}</td></tr>
					<tr class='odd'><td>Indirizzo</td><td>${accettazione.proprietarioIndirizzo}, ${accettazione.proprietarioComune}
											<c:if test="${accettazione.proprietarioProvincia!=''}">
												(${accettazione.proprietarioProvincia})						
											</c:if>
											<c:if test="${accettazione.proprietarioCap!=''}">
												- ${accettazione.proprietarioCap}						
											</c:if>
					</td></tr>
					<tr class='even'><td>Nominativo Referente</td><td>${accettazione.proprietarioNome}</td></tr>
					<tr class='odd'><td>Codice fiscale Referente</td><td>${accettazione.proprietarioCodiceFiscale}</td></tr>
					<tr class='even'><td>Documento Identità Referente</td><td>${accettazione.proprietarioDocumento}</td></tr>
					<tr class='odd'><td>Telefono Referente</td><td>${accettazione.proprietarioTelefono}</td></tr>
				</c:when>
				<c:when test="${accettazione.proprietarioTipo=='Importatore'}">
					<tr class='even'><td>Ragione Sociale:</td><td>${accettazione.proprietarioNome}</td></tr>
					<tr class='odd'><td>Partita Iva:</td><td>${accettazione.proprietarioCognome }</td></tr>
					<tr class='even'><td>Codice Fiscale Impresa:</td><td>${accettazione.proprietarioCodiceFiscale }</td></tr>
					<tr class='odd'><td>Telefono struttura(principale):</td><td>${accettazione.proprietarioTelefono }</td></tr>
					<tr class='even'><td>Telefono struttura(secondario):</td><td>${accettazione.proprietarioDocumento }</td></tr>
					<tr class='odd'><td>Indirizzo sede operativa:</td><td>${accettazione.proprietarioIndirizzo }</td></tr>
					<tr class='even'><td>CAP sede operativa:</td><td>${accettazione.proprietarioCap }</td></tr>
					<tr class='odd'><td>Comune sede operativa:</td><td>${accettazione.proprietarioComune }</td></tr>
					<tr class='even'><td>Provincia sede operativa:</td><td>${accettazione.proprietarioProvincia }</td></tr>
				</c:when>
				<c:otherwise>
					<c:if test="${!accettazione.randagio}">
			<tr class='even'><td>Cognome:</td><td>${accettazione.proprietarioCognome }</td></tr>
		</c:if>
		<tr class='odd'><td>Nome:</td><td>${accettazione.proprietarioNome }</td></tr>
		<c:if test="${!accettazione.randagio}">
			<tr class='even'><td>Codice Fiscale:</td><td>${accettazione.proprietarioCodiceFiscale }</td></tr>
			<tr class='odd'><td>Documento:</td><td>${accettazione.proprietarioDocumento }</td></tr>
			<tr class='even'><td>Indirizzo:</td><td>${accettazione.proprietarioIndirizzo }</td></tr>
			<tr class='odd'><td>CAP:</td><td>${accettazione.proprietarioCap }</td></tr>
			<tr class='even'><td>Comune:</td><td>${accettazione.proprietarioComune }</td></tr>
			<tr class='odd'><td>Provincia:</td><td>${accettazione.proprietarioProvincia }</td></tr>
			<tr class='even'><td>Telefono:</td><td>${accettazione.proprietarioTelefono }</td></tr>
		</c:if>
				</c:otherwise>
			</c:choose>
		
		
	<!--  /c:if-->
	<c:set var="richiedenteCognome" value="${accettazione.richiedenteCognome}"/>
	
	<tr><th colspan="2">Dati Accettazione</th></tr>
	<tr class='even'><td>Data:</td><td><fmt:formatDate type="date" value="${accettazione.data }" pattern="dd/MM/yyyy" /></td></tr>
	<tr class='odd'><td>Inserita da:</td><td>${accettazione.enteredBy}</td></tr>
	<tr class='even'><td>Richiedente:</td>
		<td>
			<b>${accettazione.lookupTipiRichiedente.description }:</b>
			<c:choose>
				<c:when test="${accettazione.richiedenteTipoProprietario=='Importatore'}">
					Importatore ragione sociale: ${accettazione.richiedenteNome }
				</c:when>
				<c:otherwise>
					${accettazione.richiedenteAsl.description }
					${accettazione.richiedenteAssociazione.description }
					<c:if test="${!fn:startsWith(richiedenteCognome, '<b>')}">
						${accettazione.richiedenteCognome }
					</c:if>
					${accettazione.richiedenteNome }
					${accettazione.richiedenteCodiceFiscale }
					${accettazione.richiedenteDocumento }
					<c:if test="${accettazione.richiedenteTelefono!=null && accettazione.richiedenteTelefono!=''}">
						<br/>Telefono: ${accettazione.richiedenteTelefono }
					</c:if>
					<c:if test="${accettazione.richiedenteResidenza!=null && accettazione.richiedenteResidenza!=''}">
						<br/>Residenza: ${accettazione.richiedenteResidenza }
					</c:if>
					${accettazione.richiedenteForzaPubblicaComune }
					${accettazione.richiedenteForzaPubblicaProvincia }
					${accettazione.richiedenteForzaPubblicaComando }
					${accettazione.richiedenteAltro }
					<c:if test="${!empty accettazione.personaleAsl}">
						<br/>Richiedente Asl:
						<c:forEach items="${accettazione.personaleAsl}" var="pA">
							${pA},
						</c:forEach>
						
						<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(personaleAsl)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>

					</c:if>
					<c:if test="${!empty accettazione.personaleInterno}">
						<br/>Intervento personale interno:
						<c:forEach items="${accettazione.personaleInterno}" var="pI">
							${pI.nominativo},
						</c:forEach>
						
						<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(personaleInterno)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>


					</c:if>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr class='odd'>
		<td>
			Motivazioni/Operazioni Richieste
		</td>
		<td>
			<c:set value="false" var="scriviVirgola"/>
			<c:forEach items="${accettazione.operazioniRichieste}" var="temp" >
				<c:if test="${scriviVirgola}">
					, 
				</c:if>
				<us:contain collection="${accettazione.idTipoAttivitaBdrsCompletate}" item="${temp.id}">
					<a id="href${temp.id}">
				</us:contain>
				${temp.description}
				<us:contain collection="${accettazione.idTipoAttivitaBdrsCompletate}" item="${temp.id}">
					</a>
				</us:contain>
				<c:if test="${temp.sceltaAsl!=null && temp.sceltaAsl && accettazione.aslRitrovamento!=null && accettazione.aslRitrovamento!='' }">
					(${accettazione.aslRitrovamento}) 
				</c:if>
				<c:if test="${temp.id==idOperazioniBdr.trasferimento && accettazione.tipoTrasferimento!=null && accettazione.tipoTrasferimento!=''}">
					(${accettazione.tipoTrasferimento})
				</c:if>
				<c:if test="${temp.id==idOperazioniBdr.altro && accettazione.noteAltro!=null && accettazione.noteAltro!=''}">
					(${accettazione.noteAltro})
				</c:if>
				<c:if test="${temp.id==idOperazioniBdr.ricoveroInCanile && accettazione.noteRicoveroInCanile!=null && accettazione.noteRicoveroInCanile!=''}">
					(${accettazione.noteRicoveroInCanile})
				</c:if>
				<c:if test="${temp.id==idOperazioniBdr.incompatibilitaAmbientale && accettazione.noteIncompatibilitaAmbientale()!=null && accettazione.noteIncompatibilitaAmbientale!=''}">
					(${accettazione.noteIncompatibilitaAmbientale})
				</c:if>
				<c:if test="${temp.id==idRichiesteVarie.attivitaEsterne}">
					( ${accettazione.attivitaEsterna.description})
				</c:if>
				<c:set value="true" var="scriviVirgola"/>
			</c:forEach>
			
			<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(foreachOpRichieste)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>

	
		</td>
	</tr>
	<c:if test="${accettazione.eseguireOperazioniBdr }">
		<tr>
			<td>Operazioni da Eseguire in BDR:</td>
			<td>
				${accettazione.operazioniRichiesteBdrNonEseguite } 
				<us:can f="BDR" sf="ADD" og="MAIN" r="w">
					<input type="button" value="Esegui" onclick="attendere(),location.href='vam.accettazione.TestRegistrazioni.us?idAccettazione=${accettazione.id}'" />
				</us:can>
			</td>
		</tr>
	</c:if>
	<!--  DATI RITROVAMENTO  -->
	<c:if test="${accettazione.animale.decedutoNonAnagrafe == true && accettazione.randagio}">
	       <tr>
	        	<th colspan="3">
	        		Dati inerenti il ritrovamento
	        	</th>        	
	        </tr>
        	
        	
        	<tr class='even'>
	        	<td>
	        		Comune Ritrovamento
	        	</td>
	        	<td>
	        		<c:out value="${accettazione.animale.comuneRitrovamento.description }"/>		        		
	        	</td>
	        	<td>
	        	</td>
        	</tr>
        	
        	<tr class='odd'>
	        	<td>
	        		Provincia Ritrovamento
	        	</td>
	        	<td>
	        		<c:out value="${accettazione.animale.provinciaRitrovamento }"/>	        		
	        	</td>
	        	<td>
	        	</td>
        	</tr>
        		   	
        	<tr class='even'>
	        	<td>
	        		Indirizzo Ritrovamento
	        	</td>
	        	<td>
	        		<c:out value="${accettazione.animale.indirizzoRitrovamento }"/>        		
	        	</td>
	        	<td>
	        	</td>
        	</tr>	
        	
        	<tr class='odd'>
	        	<td>
	        		Note Ritrovamento
	        	</td>
	        	<td>
	        		<c:out value="${accettazione.animale.noteRitrovamento }"/>		        		    		
	        	</td>
	        	<td>
	        	</td>
        	</tr>   			   		 	   		 
        </c:if>
        
        <c:forEach items="${accettazione.cartellaClinicas }" var="ccTemp" >
			<c:set value="${ccTemp}" var="cc" />
		</c:forEach>
		
		<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(foreachCc2)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>


        <c:if test="${(necroscopicoDaFare == 'true' || smaltimentoCarognaDaFare=='true' || cc.autopsia.dataAutopsia!=null) && accettazione.animale.dataSmaltimentoCarogna==null  && empty cc.trasferimenti}">
		<form action="vam.accettazione.AddSmaltimentoCarogna.us" name="form" method="post" id="form" class="marginezero">
		
		<fmt:formatDate type="date" value="${accettazione.data}" pattern="dd/MM/yyyy" var="dataAperturaAccettazione"/> 
    	<input type="hidden" name="dataApertura" id="dataApertura" value="${dataAperturaAccettazione}"> 
		<input type="hidden" name="idAccettazione" value="${accettazione.id}" />
		<c:forEach items="${accettazione.cartellaClinicas }" var="ccTemp" >
				<fmt:formatDate type="date" value="${ccTemp.autopsia.dataAutopsia}" pattern="dd/MM/yyyy" var="DataAutopsia"/> 
				<input type="hidden" name="dataAutopsia" value="${DataAutopsia}" />
				<c:set value="${ccTemp}" var="cc" />
		</c:forEach>
		
		<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(foreachCc3)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>


 		<tr class='even'>
        	<th colspan="3">
  	    		Registrazione Smaltimento Carogna
       		</th>
       	</tr>
       	<tr>
    		<td>
    			 Data<font color="red"> *</font>
    		</td>
    		<td>
    			 <fmt:formatDate pattern="dd/MM/yyyy" var="data" value="${accettazione.animale.dataSmaltimentoCarogna}" />    		
    			 <input type="text" id="dataSmaltimentoCarogna" name="dataSmaltimentoCarogna" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${data}"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataSmaltimentoCarogna",             // id of the input field
        					ifFormat       :    "%d/%m/%Y",      	// format of the input field
       						button         :    "id_img_1",  		// trigger for the calendar (button ID)
       						// align          :    "Tl",           	// alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>    		
        </tr>
                
             
        <tr class='even'>
    		
        </tr>
                
             
        <tr class='odd'>
	        <td>
	    		Ditta Autorizzata
    		</td>
    		<td>
		        <input type="text" id="dittaAutorizzata" name="dittaAutorizzata" maxlength="255" size="50%" value="${accettazione.animale.dittaAutorizzata}">
	        </td>
	        <td>
	        </td>
        </tr>
        
        <tr class='even'>
	        <td>
	    		DDT
    		</td>
    		<td>
		        <input type="text" id="ddt" name="ddt" maxlength="255" size="50%" value="${accettazione.animale.ddt}">
	        </td>
	        <td>
	        </td>
        </tr>
        
        <tr class='odd'>
	        <td>
	    		 <font color="red">* </font> Campi obbligatori
    		</td>
    		<td>
	        </td>
	        <td>
	        </td>
        </tr>
        
        <tr class='even'>
        	<td align="center">
        		<input type="button" value="Salva" onclick="if(checkformSmaltimentoCarogna()){document.form.submit();}"/>
        	</td>
    		<td>    			
    		</td>  
    		<td>    			
    		</td>    		
        </tr>
        
        </form>
      </c:if>
      
      <c:if test="${(smaltimentoCarognaDaFare=='true'  || cc.autopsia.dataAutopsia!=null) && accettazione.animale.dataSmaltimentoCarogna!=null}">
      	<tr>
      		<th colspan="3">
  	    		Registrazione Smaltimento Carogna
       		</th>
      	</tr>
		<tr class='even'>
    		<td>
    			 Data
    		</td>
    		<td>
    			<fmt:formatDate value="${accettazione.animale.dataSmaltimentoCarogna}" pattern="dd/MM/yyyy" var="dataSmaltimento"/>
    			${dataSmaltimento}
    		</td>    		
        </tr>
                
             
        <tr class='even'>
    		
        </tr>
                
             
        <tr class='odd'>
	        <td>
	    		Ditta Autorizzata
    		</td>
    		<td>
		        ${accettazione.animale.dittaAutorizzata}
	        </td>
	        <td>
	        </td>
        </tr>
        
        <tr class='even'>
	        <td>
	    		DDT
    		</td>
    		<td>
		        ${accettazione.animale.ddt}
	        </td>
	        <td>
	        </td>
        </tr>
      </c:if>
	
</table>


   	<div id="modifica_anagrafica_animale_div" title="Anagrafica Animale">
	<form id="formAnagraficaAnimale" name="formAnagraficaAnimale" action="vam.accettazione.ModificaAnagraficaAnimale.us" method="post">	
	<input type="hidden" name="idAnimale"  value="${accettazione.animale.id}" />
	<input type="hidden" name="idSpecie" id="idSpecie"  value="${accettazione.animale.lookupSpecie.id}" />
	<input type="hidden" name="idAccettazione" value="${accettazione.id}" />
	<input type="hidden" name="redirectTo" value="Detail" />
	
	 <table class="tabella">		
		
		<tr>
        	<th colspan="3">
        		Anagrafica Animale
        	</th>
        </tr>
    	    
    	<c:if test="${(temp.cane  && accettazione.animale.lookupSpecie.id==specie.cane) or (temp.gatto && accettazione.animale.lookupSpecie.id==specie.gatto)}">     
        <tr>
        	<td colspan="3">
        		<font color="red">Attenzione!!! Le modifiche ai dati saranno apportate anche in BDU</font><br/>
    		</td>
    	</tr>
    	</c:if>
    	
    	<c:if test="${(accettazione.animale.lookupSpecie.id != specie.sinantropo)}">
    	<tr class='even'>
    		<td>
				Razza
    		</td>
    		<td>
				<select id="razza" name="razza">
<%
					if(animale.getLookupSpecie().getId()==Specie.CANE)
					{
					while(iterRazzeCane.hasNext())
					{
						LookupRazze temp = iterRazzeCane.next();
%>
							<option value="<%=temp.getId()%>"
<%
								if(temp.getId()==anagraficaAnimale.getIdRazza())
								{
%>
									selected="selected"
<%
								}
%>								
							><%=temp.getDescription()%></option>
<%
					}
					}
					
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
 		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(razzeCane)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();

					if(animale.getLookupSpecie().getId()==Specie.GATTO)
					{
					while(iterRazzeGatto.hasNext())
					{
						LookupRazze temp = iterRazzeGatto.next();
%>
							<option value="<%=temp.getId()%>"
<%
								if(temp.getId()==anagraficaAnimale.getIdRazza())
								{
%>
									selected="selected"
<%
								}
%>								
							><%=temp.getDescription()%></option>
<%
					}
					}
					
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(razzeGatto)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>


				</select>
    		</td>
    		<td>
    		</td>
        </tr>
        </c:if>
        
        <tr class='odd'>
    		<td>
    			Sesso 
    		</td>
    		<td>
    			<select name="sesso">
					<option value="M"
						<c:if test="${anagraficaAnimale.sesso=='M'}">
							selected="selected"
						</c:if>
					>Maschio</option>
					<option value="F"
						<c:if test="${anagraficaAnimale.sesso=='F'}">
							selected="selected"
						</c:if>
					>Femmina</option>
					<c:if test="${accettazione.animale.lookupSpecie.id == specie.sinantropo}">
					<option value="ND"
						<c:if test="${accettazione.animale.sesso=='ND'}">
							selected="selected"
						</c:if>
					>Non Definito</option>
					</c:if>
				</select>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <c:if test="${accettazione.animale.lookupSpecie.id==specie.cane}">
        <tr class='even'>
    		<td>
    			Taglia 
    		</td>
    		<td>
    			<select name="idTaglia" id="idTaglia">
					<option value="-1">-- Seleziona --</option>
						<c:forEach items="${taglie}" var="temp" >
							<option value="${temp.id}"
								<c:if test="${temp.id==anagraficaAnimale.idTaglia}">
									selected="selected"
								</c:if>
							>${temp.description}</option>
						</c:forEach>

						<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(taglie)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>

				</select>
    		</td>
    		<td>
    		</td>
        </tr>
        </c:if>
        
        <c:if test="${accettazione.animale.lookupSpecie.id!=specie.sinantropo}">
        <tr class='odd'>
    		<td>
    			Mantello 
    		</td>
    		<td>
    			<div name="mantelli_cane" id="mantelli_cane" style="display: none;">
						<select name="mantelloCane" id="mantelloCane">
							<option value="-1">-- Seleziona --</option>

<%					
if(animale.getLookupSpecie().getId()==Specie.CANE)
{
					while(iterMantelliCane.hasNext())
					{
						LookupMantelli temp = iterMantelliCane.next();
%>
							<option value="<%=temp.getId()%>"
<%
								if(temp.getId()==anagraficaAnimale.getIdMantello())
								{
%>
									selected="selected"
<%
								}
%>								
							><%=temp.getDescription()%></option>
<%
					}
}

							memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(mantelliCane)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>


						</select>
					</div>
					<div name="mantelli_gatto" id="mantelli_gatto" style="display: none;">
						<select name="mantelloGatto" id="mantelloGatto">
							<option value="-1">-- Seleziona --</option>
							
<%							
					if(animale.getLookupSpecie().getId()==Specie.GATTO)
					{
					while(iterMantelliGatto.hasNext())
					{
						LookupMantelli temp = iterMantelliGatto.next();
%>
							<option value="<%=temp.getId()%>"
<%
								if(temp.getId()==anagraficaAnimale.getIdMantello())
								{
%>
									selected="selected"
<%
								}
%>								
							><%=temp.getDescription()%></option>
<%
					}
					}


memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(mantelliGatto)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>


						</select>
					</div>
					<div name="mantelli_sinantropo" id="mantelli_sinantropo" style="display: none;">
						<input type="text" maxlength="255" id="mantelloSinantropo" name="mantelloSinantropo" value="${accettazione.animale.mantelloSinantropo}"/>
					</div>
    		</td>
    		<td>
    		</td>
        </tr>
        </c:if>
        
        <!--  <tr class='even'>
			<td>
				Data di nascita
			</td>
			<td>	
				 <fmt:formatDate type="date" value="${accettazione.animale.dataNascita}" pattern="dd/MM/yyyy" var="dataNascita"/>			
	       		 <input type="text" name="dataNascita" id="dataNascita" readonly="readonly" value="${dataNascita}"/> 	       		    	       				  		 
			</td>
		</tr>-->
		
      </table>
    </form>	
 </div>
 
 
<script type="text/javascript">


function stampa(url)
{
	this.disabled=true;
	var win = window.open(url);
	var timer = setInterval(function() 
	{   
	    if(win.closed) 
	    {  
	        clearInterval(timer);  
	        this.disabled=false;
	    }  
	}, 500); 
}

function dettaglioReg( idAccettazione, idOperazione, idFrame, loginUrl)
{		
	document.getElementById(idFrame).src=loginUrl;
	$( '#dettaglio_reg_' + idOperazione ).dialog( 'open' );
}	

function logoutFromCanina( idFrame )
{
	document.getElementById(idFrame).src='<%=CaninaRemoteUtil.getLogoutUrl( (BUtente)session.getAttribute( "utente" ) ) %>';
}
</script>


<script type="text/javascript">

function modificaAnagraficaAnimale( idSpecie )
{
	if(1== idSpecie )
	{
		toggleDivMantello2( "mantelli_cane" );
	}

	if( 2 == idSpecie )
	{
		toggleDivMantello2( "mantelli_gatto" );
	}

	if(3 == idSpecie )
	{
		toggleDivMantello2( "mantelli_sinantropo" );
	}		
	$('#modifica_anagrafica_animale_div').dialog( 'open' );
}	

 $(function() 
			{
				$( "#modifica_anagrafica_animale_div" ).dialog({
					height: screen.height/3,
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
							if(document.getElementById("idSpecie").value=='1' && document.getElementById("idTaglia")!=null && document.getElementById("idTaglia").value=='-1')
							{
								alert("Selezionare la taglia");
								document.getElementById("idTaglia").focus();
								return false;
							}
							if((document.getElementById("idSpecie").value=='1' || document.getElementById("idSpecie").value=='2') &&
								document.getElementById("mantelloCane").value=='-1' && 
							    document.getElementById("mantelloGatto").value=='-1'
							)
							{
								alert("Inserire il mantello");
								document.getElementById("mantello").focus();
								return false;
							}
							document.formAnagraficaAnimale.submit();
						}
					}
			});
			$( "#dataNascita" ).datepicker(
										{
				 						 dateFormat: 'dd/mm/yy', 
					 					 showOn: "button",
					 					 buttonImage: "images/calendar.gif",
					 					 buttonImageOnly: true,
					 					 monthNames: ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"],
					 					 dayNamesMin: ["Dom","Lun","Mar","Mer","Gio","Ven","Sab"],
					 					 firstDay: 1
										}
				   					   );
			$("#ui-datepicker-div").css("z-index",10000); 
	});	
	
	
	
	
</script>



<%
	Iterator<LookupOperazioniAccettazione> iter = accettazione.getOperazioniRichieste().iterator();
	while(iter.hasNext())
	{
		LookupOperazioniAccettazione temp = iter.next();
		if(accettazione.getIdTipoAttivitaBdrsCompletate().contains(temp.getId()))
		{
			Integer idRegBdr = RegistrazioniUtil.getIdRegBdr(accettazione, temp);
%>
		<div id="dettaglio_reg_<%=temp.getId()%>" title="Dettaglio Registrazione di <%=temp.getDescription()%>">
			<iframe id="myFrame<%=temp.getId()%>" name="myFrame<%=temp.getId()%>" frameborder="0" vspace="0" hspace="0" marginwidth="0" marginheight="0"
					width="99%"  		   scrolling=yes  			height="98%"
					style="BORDER-RIGHT: black 1px solid; 			BORDER-TOP: black 1px solid; 		  Z-INDEX: 999; 
					BORDER-LEFT: black 1px solid; 
					BORDER-BOTTOM: black 1px solid;"
					src="">
			</iframe>
		</div>
		
		<script type="text/javascript">
		
		<%
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
		Connection connection = ds.getConnection();
		aggiornaConnessioneApertaSessione(req);
		%>
			document.getElementById("href"+<%=temp.getId()%>).href="javascript:dettaglioReg(<%=accettazione.getId()%>,<%=temp.getId()%>,'myFrame<%=temp.getId()%>','<%=CaninaRemoteUtil.getLoginUrl( (BUtente)session.getAttribute( "utente" ), null, accettazione.getAnimale(), temp, accettazione, idRegBdr, connection ) %>');";
		
		<%
			connection.close();
			aggiornaConnessioneChiusaSessione(req);
		%>
			
			$(function() 
				{
					$( "#dettaglio_reg_<%=temp.getId()%>" ).dialog({
						height: screen.height/1.5,
						modal: true,
						autoOpen: false,
						closeOnEscape: true,
						show: 'blind',
						resizable: true,
						draggable: true,
						width: screen.width/1.2,
						buttons: {
							"Chiudi": function() {
								logoutFromCanina("myFrame<%=temp.getId()%>");
								$( this ).dialog( "close" );
							}
						}
				});
			});
		</script>
		
		
<%
		}
	}
%>


<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(fine)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>
