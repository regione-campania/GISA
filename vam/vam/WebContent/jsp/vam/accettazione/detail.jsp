<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.SuperUtenteAll"%>
<%@page import="java.util.HashSet"%>
<%@page import="it.us.web.action.GenericAction"%>
<%@page import="it.us.web.bean.vam.Trasferimento"%>
<%@page import="it.us.web.bean.vam.Animale"%>
<%@page import="it.us.web.constants.Specie"%>
<%@page import="it.us.web.bean.vam.lookup.LookupPersonaleInterno"%>
<%@page import="java.util.List"%>
<%@page import="it.us.web.bean.SuperUtente"%>
<%@page import="it.us.web.constants.IdOperazioniBdr"%>
<%@page import="it.us.web.constants.IdRichiesteVarie"%>
<%@page import="it.us.web.util.vam.AnimaliUtil"%>
<%@page import="it.us.web.bean.vam.AnimaleAnagrafica"%>
<%@page import="it.us.web.bean.vam.lookup.LookupMantelli"%>
<%@page import="it.us.web.bean.vam.lookup.LookupRazze"%>
<%@page import="it.us.web.bean.vam.lookup.LookupTaglie"%>
<%@page import="it.us.web.constants.SpecieAnimali"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
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
<%@page import="it.us.web.bean.vam.Trasferimento"%>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/TestBdr.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<script language="JavaScript" type="text/javascript" src="js/vam/accettazione/smaltimentoCarogna.js"></script>

<%
	Accettazione accettazione = (Accettazione)request.getAttribute("accettazione");
	SpecieAnimali specie = (SpecieAnimali)request.getAttribute("specie");
	AnimaleAnagrafica anagraficaAnimale = (AnimaleAnagrafica)request.getAttribute("anagraficaAnimale");
	IdOperazioniBdr idOpsBdr = (IdOperazioniBdr)request.getAttribute("idOpsBdr");
	IdRichiesteVarie idRichiesteVarie = (IdRichiesteVarie)request.getAttribute("idRichiesteVarie");
	String necroscopicoDaFare = (String)request.getAttribute("necroscopicoDaFare");
	String smaltimentoCarognaDaFare = (String)request.getAttribute("smaltimentoCarognaDaFare");
	Iterator<LookupRazze> iterRazzeCane = ((List<LookupRazze> )request.getServletContext().getAttribute("razzeCane")).iterator(); 
	Iterator<LookupRazze> iterRazzeGatto = ((List<LookupRazze> )request.getServletContext().getAttribute("razzeGatto")).iterator(); 
	Iterator<LookupMantelli> iterMantelliCane = ((List<LookupMantelli> )request.getServletContext().getAttribute("mantelliCane")).iterator(); 
	Iterator<LookupMantelli> iterMantelliGatto = ((List<LookupMantelli> )request.getServletContext().getAttribute("mantelliGatto")).iterator(); 
	Iterator<LookupTaglie> iterTaglie =  ((List<LookupTaglie>)request.getServletContext().getAttribute("taglie")).iterator();
	HashSet<SuperUtenteAll> personaleAslSelezionato = (HashSet<SuperUtenteAll>)request.getAttribute("personaleAslSelezionato");
	

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
     Dettaglio Accettazione n° <b><%=accettazione.getProgressivoFormattato()%></b> <%=accettazione.getAnimale().getLookupSpecie().getDescription()%> : <b><%=accettazione.getAnimale().getIdentificativo()%></b>
</h4> 

<c:choose>
	<c:when test="<%=accettazione.getAnimale().getDecedutoNonAnagrafe()==true%>">						
		<fmt:formatDate type="date" value="<%=accettazione.getAnimale().getDataMorte()%>" pattern="dd/MM/yyyy" var="dataMorte"/>
	</c:when>
	<c:otherwise>
		<fmt:formatDate type="date" value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/>
	</c:otherwise>	
</c:choose>	 


<c:if test="<%=!accettazione.getAprireCartellaClinica()%>">
<% 	Iterator i = accettazione.getCartellaClinicas().iterator();
    CartellaClinica cc;
	while (i.hasNext()){
		cc = (CartellaClinica)i.next(); %>
		Cartella clinica associata: <us:can f="CC" sf="DETAIL" og="MAIN" r="w"><a href="vam.cc.Detail.us?idCartellaClinica=<%=cc.getId()%>" onclick="attendere()"></us:can><%=cc.getNumero()%><us:can f="CC" sf="DETAIL" og="MAIN" r="w"></a></us:can><br/>
<%	}%>
</c:if>

<c:choose>
	<c:when test="${dataMorte!=null && dataMorte!=''}">
		<c:if test="<%=!accettazione.getAprireCartellaNecroscopica() && accettazione.getCartellaClinicas().size()==0%>">
			Non è prevista l'apertura della cc necroscopica in quanto, per quest'accettazione, non sono stati richiesti esami da inserire<br/>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:if test="<%=!accettazione.getAprireCartellaClinica() && accettazione.getCartellaClinicas().size()==0%>">
			Non è prevista l'apertura della cc in quanto, per quest'accettazione, non sono stati richiesti esami da inserire<br/>
		</c:if>
	</c:otherwise>
</c:choose>

<c:set var="tipo" scope="request" value="stampaAcc"/>
<c:import url="../../jsp/documentale/home.jsp"/>

<c:if test="${accettazione.idAccMultipla!=null}">
	<c:set var="tipo2" scope="request" value="stampaAccMultipla"/>
	<c:set var="idAccMultipla" scope="request" value="${accettazione.idAccMultipla}"/>
<c:import url="../../jsp/documentale/home4.jsp"/>
</c:if>



<!-- input type="button" value="Stampa" onclick="stampa('vam.accettazione.Pdf.us?id=<%=accettazione.getId()%>')"/-->

<!-- input type="button" value="Stampa JSP" onclick="location.href='vam.accettazione.StampaAccettazione.us?id=<%=accettazione.getId()%>'"/-->

<c:if test="${dataMorte!=null}">
	<c:set var="tipo" scope="request" value="stampaDecesso"/>
<c:import url="../../jsp/documentale/home2.jsp"/>

</c:if>

<%
if(accettazione.isCovid())
{
%>
	<input type="button" name="downloadPdf2" id="downloadPdf2" value="Stampa Verbale Accompagnamento Campioni" onclick="location.href='documentale.DownloadNewPdf.us?tipo=verbaleAccompagnamentoCampioni&glifo=false&idEsame=<%=accettazione.getId()%>';"/>
<%
}
%>

<%-- c:if test="${dataMorte!=null}">
	<input type="button" value="Stampa Certificato Decesso JSP" onclick="location.href='vam.accettazione.StampaCertificatoDecesso.us?id=<%=accettazione.getId()%>'" />
</c:if--%>

<!--  c:if test="${fuoriAsl==false}"-->
  <c:choose>
  	<c:when test="<%=accettazione.getAnimale().getLookupSpecie().getId()==specie.getSinantropo() || accettazione.getAnimale().getDecedutoNonAnagrafe()==true%>">
  		<input type="button" value="Modifica Anagrafica Animale" onclick="modificaAnagraficaAnimale(<%=accettazione.getAnimale().getLookupSpecie().getId()%>);" />
  	</c:when>
  	<c:otherwise>
  		<us:can f="BDR" sf="EDIT" og="MAIN" r="w">
  			<input type="button" value="Modifica Anagrafica Animale" onclick="modificaAnagraficaAnimale(<%=accettazione.getAnimale().getLookupSpecie().getId()%>);" />
  		</us:can>
  	</c:otherwise>
  </c:choose>
<!--  /c:if-->

<us:can f="ACCETTAZIONE" sf="DELETE" og="MAIN" r="w">
<c:if test="<%=accettazione.getCancellabile()%>">
	<input type="button" value="Elimina" onclick="if( myConfirm('Sei sicuro di voler eliminare l\'accettazione?') ){location.href='vam.accettazione.Delete.us?id=<%=accettazione.getId()%>'}" />
</c:if>
</us:can>

<us:can f="ACCETTAZIONE" sf="EDIT" og="MAIN" r="w">
<c:if test="<%=accettazione.getModificabile()%>">
	<input type="button" value="Modifica" onclick="attendere(),location.href='vam.accettazione.ToAdd.us?modify=true&idAnimale=<%=accettazione.getAnimale().getId()%>&id=<%=accettazione.getId()%>'" />
</c:if>
</us:can>

<input type="hidden" name="dataAperturaToCheck" id="dataAperturaToCheck" value="<fmt:formatDate type="date" value="<%=accettazione.getData()%>" pattern="dd/MM/yyyy" />"> 

<us:can f="CC" sf="ADD" og="MAIN" r="w" >
<%
	Accettazione accTemp = (Accettazione)AnimaliUtil.getAccettazioneConCcAprireRitrovamentoCambioDetentore(accettazione.getAnimale(),request);
%>
	<c:choose>
		<c:when test="${dataMorte!=null && dataMorte!=''}">
			<c:if test="<%=accettazione.getAprireCartellaNecroscopica() || (accTemp!=null && accTemp.getId()==accettazione.getId())%>">
				<input type="button" id="apriCcButton" value="Apri Cartella Necroscopica" 
				<c:if test="<%=accettazione.getAccettazionePiuRecente()!=null%>">
					<c:choose>
						<c:when test="<%=!utente.getRuolo().equals(\"Referente Asl\") && !utente.getRuolo().equals(\"14\") %>">
							onclick="alert('Impossibile aprire la cc in quanto per quest\'animale è stata aperta un\'accettazione più recente');"
						</c:when>
						<c:otherwise>
							onclick="if( myConfirm('Esiste un\'accettazione più recente per quest\'animale, si desidera proseguire per il recupero di accettazioni pregresse?') ){location.href='vam.cc.Add.us?idAccettazione=<%=accettazione.getId()%>'}"
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="<%=accettazione.getEseguireOperazioniBdr()==false%>">
					onclick="if( myConfirm('Sei sicuro di voler aprire la cartella necroscopica associata?') ){location.href='vam.cc.Add.us?idAccettazione=<%=accettazione.getId()%>'}"
				</c:if>
				<c:if test="<%=accettazione.getEseguireOperazioniBdr()==true%>">
					onclick="alert('Impossibile aprire la cc in quanto ci sono delle operazioni da fare in BDR richieste in fase di accettazione');"
				</c:if>
				
				 />
			 </c:if>
		</c:when>
		<c:otherwise>
			<c:if test="<%=accettazione.getAprireCartellaClinica() || (accTemp!=null && accTemp.getId()==accettazione.getId())%>">
				<input type="button" id="apriCcButton" value="Apri Cartella Clinica" 
				<c:if test="<%=accettazione.getAccettazionePiuRecente()!=null%>">
					onclick="alert('Impossibile aprire la cc in quanto per quest\'animale è stata aperta un\'accettazione più recente');"
				</c:if>
				<c:if test="<%=accettazione.getEseguireOperazioniBdr()==false%>">
					onclick="if( myConfirm('Sei sicuro di voler aprire la cartella clinica associata?') ){location.href='vam.cc.Add.us?idAccettazione=<%=accettazione.getId()%>'}" 
				</c:if>
				<c:if test="<%=accettazione.getEseguireOperazioniBdr()==true%>">
					onclick="alert('Impossibile aprire la cc in quanto ci sono delle operazioni da fare in BDR richieste in fase di accettazione');"
				</c:if>
				
				/>
			</c:if>
		</c:otherwise>
	</c:choose>
</us:can>


<% 	
	memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
 		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(datiAnimale)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>

<table class="tabella" style="width: 99%">
	<tr><th colspan="2">Dati Animale</th></tr>
	<tr class='even'><td>Identificativo:</td><td><%=accettazione.getAnimale().getIdentificativo()%></td></tr>
	<tr class='even'><td>Tatuaggio / II MC:</td><td><%if(accettazione.getAnimale().getTatuaggio()!=null){%> <%=accettazione.getAnimale().getTatuaggio()%> <%}%></td></tr>
	<c:choose>
		<c:when test="<%=accettazione.getAnimale().getLookupSpecie().getId()==specie.getSinantropo()%>">
					<fmt:formatDate type="date" value="<%=accettazione.getAnimale().getDataNascita()%>" pattern="dd/MM/yyyy" var="dataNascita" />
					<tr class='odd'><td>Et&agrave;:</td><td>${accettazione.animale.eta.description}
					<c:if test="<%=accettazione.getAnimale().getDataNascita()!=null %>">(<%=accettazione.getAnimale().getDataNascita() %>)</c:if></td></tr>
		</c:when>
		<c:otherwise>
					<fmt:formatDate type="date" value="<%=accettazione.getAnimale().getDataNascita()%>" pattern="dd/MM/yyyy" var="dataNascita"/>
					<tr class='odd'><td>Data nascita:</td><td><%if (accettazione.getAnimale().getDataNascita()!=null){%><%=accettazione.getAnimale().getDataNascita()%><%} %></td></tr>
		</c:otherwise>
	</c:choose>
	
<!--  	<c:set scope="request" var="anagraficaAnimale" value="${anagraficaAnimale}"/> -->
    <c:import url="../vam/accettazione/anagraficaAnimaleDetail.jsp"/>
	
	<c:if test="<%=accettazione.getAnimale().getClinicaChippatura()!=null && accettazione.getAnimale().getClinicaChippatura().getId()>0%>">
		<tr class='even'><td>Microchippato nella clinica </td><td><%=accettazione.getAnimale().getClinicaChippatura().getNome()%></td></tr>
	</c:if>
	<c:if test="${accettazione.animale.dataMorte!=null || res.dataEvento!=null}">
        <tr class='even'>
   			<td>
   				Data del decesso
   			</td>
   			<td> 
				${dataMorte} - 
				<c:choose>
					<c:when test="<%=accettazione.getAnimale().getDecedutoNonAnagrafe()==true %>">
						<%=accettazione.getAnimale().getDataMorteCertezza()%>
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
    				<c:when test="<%=accettazione.getAnimale().getDecedutoNonAnagrafe()%>">
    					<% if (accettazione.getAnimale().getCausaMorte().getDescription()!=null){ %>
    						<%=accettazione.getAnimale().getCausaMorte().getDescription()%>
    					<%} else {%>
    					<%=""%>
    					<%}%>
    				</c:when>
    				<c:otherwise>
    					${res.decessoValue}
    				</c:otherwise>
    			</c:choose>	        	        
        	</td>
       	</tr>
       	</c:if>

<% 	
	memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
 		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(decesso)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>

	<!--  c:if test="${!accettazione.animale.decedutoNonAnagrafe }" -->
		<c:choose>
			<c:when test="<%=accettazione.getAnimale().getLookupSpecie().getId()==specie.getSinantropo()%>">
				<tr><th colspan="2">Detentore</th></tr>
			</c:when>
			<c:otherwise>
				<c:set var="proprietarioCognome" value="<%=accettazione.getProprietarioCognome()%>"/>
				<c:choose>
					<c:when test="<%=accettazione.getProprietarioCognome()!=null && accettazione.getProprietarioCognome().startsWith(\"<b>\")%>">
						<th colspan="2">Colonia</th>
					</c:when>
					<c:otherwise>
						<th colspan="2">Proprietario <%=(accettazione.getProprietarioTipo()==null || accettazione.getProprietarioTipo().equals("null"))?(""):(accettazione.getProprietarioTipo())%></th>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

		<c:choose>
				<c:when test="<%=accettazione.getProprietarioCognome().startsWith(\"<b>\")%>">
					<tr class='even'><td>Colonia</td><td colspan="2"><%=accettazione.getNomeColonia()%></td></tr>
					<tr class='odd'><td>Indirizzo</td><td><%=accettazione.getProprietarioIndirizzo()%>, <%=accettazione.getProprietarioComune()%>
											<c:if test="<%=!accettazione.getProprietarioProvincia().equals(\"\") %>">
												(<%=accettazione.getProprietarioProvincia()%>)						
											</c:if>
											<c:if test="<%=!accettazione.getProprietarioCap().equals(\"\")%>">
												- <%=accettazione.getProprietarioCap()%>						
											</c:if>
					</td></tr>
					<tr class='even'><td>Nominativo Referente</td><td><%=accettazione.getProprietarioNome()%></td></tr>
					<tr class='odd'><td>Codice fiscale Referente</td><td><%=accettazione.getProprietarioCodiceFiscale()%></td></tr>
					<tr class='even'><td>Documento Identità Referente</td><td><%=accettazione.getProprietarioDocumento()%></td></tr>
					<tr class='odd'><td>Telefono Referente</td><td><%=accettazione.getProprietarioTelefono()%></td></tr>
				</c:when>
				<c:when test="${accettazione.proprietarioTipo=='Importatore' || accettazione.proprietarioTipo=='Operatore Commerciale'}">
					<tr class='even'><td>Ragione Sociale:</td><td><%=accettazione.getProprietarioNome()%></td></tr>
					<tr class='odd'><td>Partita Iva:</td><td><%=accettazione.getProprietarioCognome()%></td></tr>
					<tr class='even'><td>Rappr. Legale:</td><td><%=accettazione.getProprietarioCodiceFiscale()%></td></tr>
					<tr class='odd'><td>Telefono struttura(principale):</td><td><%=accettazione.getProprietarioTelefono()%></td></tr>
					<tr class='even'><td>Telefono struttura(secondario):</td><td><%=accettazione.getProprietarioDocumento()%></td></tr>
					<tr class='odd'><td>Indirizzo sede operativa:</td><td><%=accettazione.getProprietarioIndirizzo()%></td></tr>
					<tr class='even'><td>CAP sede operativa:</td><td><%=accettazione.getProprietarioCap()%></td></tr>
					<tr class='odd'><td>Comune sede operativa:</td><td><%=accettazione.getProprietarioComune()%></td></tr>
					<tr class='even'><td>Provincia sede operativa:</td><td><%=accettazione.getProprietarioProvincia()%></td></tr>
				</c:when>
				<c:otherwise>
					<c:if test="<%=accettazione.getProprietarioTipo()!=null && accettazione.getProprietarioTipo().equals(\"Canile\") %>">
						<tr class='even'><td>Ragione Sociale:</td><td><%if (accettazione.getProprietarioNome()!=null){%><%=accettazione.getProprietarioNome() %><%} %></td></tr>
						<tr class='odd'><td>Partita Iva:</td><td><%=accettazione.getProprietarioCognome()%></td></tr>
						<tr class='even'><td>Rappr. Legale:</td><td><%=accettazione.getProprietarioCodiceFiscale()%></td></tr>
					</c:if>
					<c:if test="<%=accettazione.getProprietarioTipo()!=null && !accettazione.getProprietarioTipo().equals(\"Canile\") %>">
						<c:if test="<%=!accettazione.getRandagio()%>">
							<tr class='even'><td>Cognome:</td><td><%if (accettazione.getProprietarioCognome()!=null && !accettazione.getProprietarioCognome().equals("null")){%><%=accettazione.getProprietarioCognome()%><%} %></td></tr>
						</c:if>
						<tr class='odd'><td>Nome:</td><td><%if (accettazione.getProprietarioNome()!=null && !accettazione.getProprietarioNome().equals("null")){%><%=accettazione.getProprietarioNome() %><%} %></td></tr>
					</c:if>	
					<c:if test="<%=!accettazione.getRandagio()%>">
					<c:if test="<%=accettazione.getProprietarioTipo()!=null && !accettazione.getProprietarioTipo().equals(\"Canile\") %>">
						<tr class='even'><td>Codice Fiscale:</td><td><% if (accettazione.getProprietarioCodiceFiscale()!=null && !accettazione.getProprietarioCodiceFiscale().equals("null")){%><%=accettazione.getProprietarioCodiceFiscale()%><%} %></td></tr>
					</c:if>
						<tr class='odd'><td>Documento:</td><td><% if (accettazione.getProprietarioDocumento()!=null && !accettazione.getProprietarioDocumento().equals("null")){%><%=accettazione.getProprietarioDocumento()%><%} %></td></tr>
						<tr class='even'><td>Indirizzo:</td><td><% if (accettazione.getProprietarioIndirizzo()!=null && !accettazione.getProprietarioIndirizzo().equals("null")){%><%=accettazione.getProprietarioIndirizzo()%><%} %></td></tr>
						<tr class='odd'><td>CAP:</td><td><% if (accettazione.getProprietarioCap()!=null && !accettazione.getProprietarioCap().equals("null")){%><%=accettazione.getProprietarioCap()%><%} %></td></tr>
						<tr class='even'><td>Comune:</td><td><% if (accettazione.getProprietarioComune()!=null && !accettazione.getProprietarioComune().equals("null")){%><%=accettazione.getProprietarioComune()%><%} %></td></tr>
						<tr class='odd'><td>Provincia:</td><td><% if (accettazione.getProprietarioProvincia()!=null && !accettazione.getProprietarioProvincia().equals("null")){%><%=accettazione.getProprietarioProvincia()%><%} %></td></tr>
						<tr class='even'><td>Telefono:</td><td><% if (accettazione.getProprietarioTelefono()!=null && !accettazione.getProprietarioTelefono().equals("null")){%><%=accettazione.getProprietarioTelefono()%><%} %></td></tr>
					</c:if>
				</c:otherwise>
			</c:choose>
			

<% 	
	memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
 		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(datiAcc)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>

	
		
	<!--  /c:if-->
	<c:set var="richiedenteCognome" value="<%=accettazione.getRichiedenteCognome()%>"/>
	
	<tr><th colspan="2">Dati Accettazione</th></tr>
	<tr class='even'><td>Data:</td><td><fmt:formatDate type="date" value="<%=accettazione.getData()%>" pattern="dd/MM/yyyy" /></td></tr>
	<tr class='odd'><td>Inserita da:</td><td><%=accettazione.getEnteredBy()%></td></tr>
	<tr class='even'><td>Richiedente:</td>
		<td>	
			<b><%=accettazione.getLookupTipiRichiedente().getDescription()%>:</b>
			<c:choose>
				<c:when test="<%=accettazione.getRichiedenteTipoProprietario()!=null && accettazione.getRichiedenteTipoProprietario().equals(\"Importatore\") %>">
					Importatore ragione sociale: <%=accettazione.getRichiedenteNome()%>
				</c:when>
		
				<c:when test="<%=accettazione.getRichiedenteTipoProprietario()!=null && !accettazione.getRichiedenteTipoProprietario().equals(\"Importatore\") %>"> 
<%-- 				<%if(accettazione.getRichiedenteAsl()!=null && accettazione.getRichiedenteAsl().getDescription()!=null){%> <%=accettazione.getRichiedenteAsl().getDescription()%> <%}%> 
					<%if(accettazione.getRichiedenteAssociazione()!=null && accettazione.getRichiedenteAssociazione().getDescription()!=null){%> <%=accettazione.getRichiedenteAssociazione().getDescription()%> <%}%> --%>
					<%if(accettazione.getRichiedenteCognome()!=null){%><%=accettazione.getRichiedenteCognome()%><%}%>
					<%if(accettazione.getRichiedenteNome()!=null){%><%=accettazione.getRichiedenteNome()%><%}%>
					<%if(accettazione.getRichiedenteCodiceFiscale()!=null){%><%=accettazione.getRichiedenteCodiceFiscale()%><%}%>
					<%if(accettazione.getRichiedenteDocumento()!=null){%><%=accettazione.getRichiedenteDocumento()%><%}%>
					<c:if test="<%=accettazione.getRichiedenteTelefono()!=null && !accettazione.getRichiedenteTelefono().equals(\"\")%>">
						<br/>Telefono: <%=accettazione.getRichiedenteTelefono()%>
					</c:if>
					<c:if test="<%=accettazione.getRichiedenteResidenza()!=null && !accettazione.getRichiedenteResidenza().equals(\"\") %>">
						<br/>Residenza: <%=accettazione.getRichiedenteResidenza()%>
					</c:if>
<%--				<%if(accettazione.getRichiedenteForzaPubblicaComune()!=null){><%=accettazione.getRichiedenteForzaPubblicaComune() %><%}%>
					<%if(accettazione.getRichiedenteForzaPubblicaProvincia()!=null){%><%=accettazione.getRichiedenteForzaPubblicaProvincia() %><%}%>
					<%if(accettazione.getRichiedenteForzaPubblicaComando()!=null){%><%=accettazione.getRichiedenteForzaPubblicaComando() %><%}%>
					<%if(accettazione.getRichiedenteAltro()!=null && !accettazione.getRichiedenteAltro().equals("")){%><%=accettazione.getRichiedenteAltro() %><%}%>
		 			<c:if test="<%=accettazione.getPersonaleAsl()!=null && accettazione.getPersonaleAsl().size()>0%>">
						<br/>Richiedente Asl:
						<% 	Iterator i = accettazione.getPersonaleAsl().iterator();
							SuperUtente su;
							while (i.hasNext()){
								su = (SuperUtente)i.next(); %>
								<%=su.toString()%>,	
						<%  } %>
					</c:if> --%>
					<c:if test="<%=accettazione.getPersonaleInterno()!=null && accettazione.getPersonaleInterno().size()>0 %>">
						<br/>Intervento personale interno:
						<% 	Iterator i = accettazione.getPersonaleInterno().iterator();
							LookupPersonaleInterno lpi;
							while (i.hasNext()){
								lpi = (LookupPersonaleInterno)i.next(); %>
								<%=lpi.getNominativo()%>,	
						<%  } %>
					</c:if>
				</c:when>

				<c:when test="<%=accettazione.getRichiedenteTipoProprietario()==null%>">
					<c:if test="<%=accettazione.getRichiedenteCognome()!=null && !accettazione.getRichiedenteCognome().startsWith(\"<b>\") %>">
						<%=accettazione.getRichiedenteCognome()%> 
					</c:if>
					<%if(accettazione.getRichiedenteNome()!=null){%><%=accettazione.getRichiedenteNome()%><%}%>
					<%if(accettazione.getRichiedenteCodiceFiscale()!=null){%><%=accettazione.getRichiedenteCodiceFiscale()%><%}%>
					<%if(accettazione.getRichiedenteDocumento()!=null){%><%=accettazione.getRichiedenteDocumento()%><%}%>
<%					
					if(accettazione.getRichiedenteTelefono()!=null && !accettazione.getRichiedenteTelefono().equals(""))
					{
						out.println(accettazione.getRichiedenteTelefono());
					}
					if(accettazione.getRichiedenteResidenza()!=null && !accettazione.getRichiedenteResidenza().equals(""))
					{
						out.println(accettazione.getRichiedenteResidenza());
					}
%>

					<c:if test="<%=personaleAslSelezionato!=null && personaleAslSelezionato.size()>0%>">
							<% 	Iterator i = personaleAslSelezionato.iterator();
								SuperUtenteAll su;
								while (i.hasNext()){
									su = (SuperUtenteAll)i.next(); %>
									<%=su.toString()%>,	
							<%  } %>
					</c:if>	
					<c:if test="<%=accettazione.getRichiedenteAssociazione()!=null && accettazione.getRichiedenteAssociazione().getDescription()!=null%>"><%=accettazione.getRichiedenteAssociazione().getDescription()%></c:if>
					<c:if test="<%=accettazione.getRichiedenteForzaPubblicaComune()!=null%>"><%=accettazione.getRichiedenteForzaPubblicaComune()%>	</c:if>
					<c:if test="<%=accettazione.getRichiedenteForzaPubblicaProvincia()!=null%>"><%=accettazione.getRichiedenteForzaPubblicaProvincia()%></c:if>
					<c:if test="<%=accettazione.getRichiedenteForzaPubblicaComando()!=null%>"><%=accettazione.getRichiedenteForzaPubblicaComando()%></c:if>
					<c:if test="<%=accettazione.getRichiedenteAltro()!=null && !accettazione.getRichiedenteAltro().equals(\"\")%>"><%=accettazione.getRichiedenteAltro()%></c:if>
					<c:if test="<%=accettazione.getPersonaleInterno()!=null && accettazione.getPersonaleInterno().size()>0%>">
						<br/>Intervento personale interno:
						<% 	Iterator i = accettazione.getPersonaleInterno().iterator();
							LookupPersonaleInterno lpi;
							while (i.hasNext()){
								lpi = (LookupPersonaleInterno)i.next(); %>
								<%=lpi.getNominativo()%>,	
						<%  } %>
					</c:if>
				</c:when>
			</c:choose>
		</td>
	</tr>
	
	<% 	
	memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
 		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(motivaz)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>		
		
	<tr class='odd'>
		<td>
			Motivazioni/Operazioni Richieste
		</td>
		<td>
			<% 	Boolean scriviVirgola=false;
				Iterator i = accettazione.getOperazioniRichieste().iterator();
				LookupOperazioniAccettazione loa;
				while (i.hasNext()){
					loa = (LookupOperazioniAccettazione)i.next();
					if (scriviVirgola){%>
					,
				<%	}  %>
					<a id="href<%=loa.getId()%>"><%=loa.getDescription()%>
					<c:if test="<%=loa.getSceltaAsl()!=null && loa.getSceltaAsl() && accettazione.getAslRitrovamento()!=null && !accettazione.getAslRitrovamento().equals(\"\") %>">
					(<%=accettazione.getAslRitrovamento()%>) 
					</c:if>
					<c:if test="<%=loa.getCovid()!=null && loa.getCovid() %>">
						(SARS CoV 2) 
					</c:if>
					<c:if test="<%=loa.getId()==idOpsBdr.getTrasferimento() && accettazione.getTipoTrasferimento()!=null && !accettazione.getTipoTrasferimento().equals(\"\") %>">
						(<%=accettazione.getTipoTrasferimento()%>)
					</c:if>
					<c:if test="<%=loa.getId()==idOpsBdr.getAdozione() && accettazione.getAdozioneFuoriAsl()!=null && accettazione.getAdozioneFuoriAsl()%>">
						fuori Asl
					</c:if>
					<c:if test="<%=loa.getId()==idOpsBdr.getAdozione() && accettazione.getAdozioneVersoAssocCanili()!=null && accettazione.getAdozioneVersoAssocCanili()%>">
						verso Associazioni/Canili
					</c:if>
					<c:if test="<%=loa.getId()==idOpsBdr.getAltro() && accettazione.getNoteAltro()!=null && !accettazione.getNoteAltro().equals(\"\")%>">
						(<%=accettazione.getNoteAltro()%>)
					</c:if>
					<c:if test="<%=loa.getId()==idOpsBdr.getRicoveroInCanile() && accettazione.getNoteRicoveroInCanile()!=null && !accettazione.getNoteRicoveroInCanile().equals(\"\")%>">
						(<%=accettazione.getNoteRicoveroInCanile()%>)
					</c:if>
					<c:if test="<%=loa.getId()==idOpsBdr.getIncompatibilitaAmbientale() && accettazione.getNoteIncompatibilitaAmbientale()!=null && !accettazione.getNoteIncompatibilitaAmbientale().equals(\"\")%>">
						(<%=accettazione.getNoteIncompatibilitaAmbientale()%>)
					</c:if>
					<c:if test="<%=loa.getId()==idRichiesteVarie.getAttivitaEsterne()%>">
						(<%=accettazione.getAttivitaEsterna().getDescription()%> presso <%=accettazione.getComuneAttivitaEsterna().getDescription()%> <%if(accettazione.getIndirizzoAttivitaEsterna()!=null && !accettazione.getIndirizzoAttivitaEsterna().equals("")){out.println(", "+accettazione.getIndirizzoAttivitaEsterna());}%>)
					</c:if>
					</a>
					<%scriviVirgola=true;%>
			 <% } %>	
		</td>
	</tr>

<% 	
	memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
 		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(opEseg.Bdr)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>

	<c:if test="<%=accettazione.getEseguireOperazioniBdr()%>">
		<tr>
			<td>Operazioni da Eseguire in BDR:</td>
			<td>
				<%=accettazione.getOperazioniRichiesteBdrNonEseguite()%> 
				<us:can f="BDR" sf="ADD" og="MAIN" r="w">
					<input type="button" value="Esegui" onclick="attendere(),location.href='vam.accettazione.TestRegistrazioni.us?idAccettazione=<%=accettazione.getId()%>'" />
				</us:can>
			</td>
		</tr>
	</c:if>
	
<% 	
	memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
 		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(ritrovam)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>	

	<!--  DATI RITROVAMENTO  -->
	<c:if test="<%=accettazione.getAnimale().getDecedutoNonAnagrafe()==true && (accettazione.getRandagio() || accettazione.getAnimale().getLookupSpecie().getId()==3)%>">
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
	        		${accettazione.animale.comuneRitrovamento.description} 
	        	</td>
	        	<td>
	        	</td>
        	</tr>
        	
        	<tr class='odd'>
	        	<td>
	        		Provincia Ritrovamento
	        	</td>
	        	<td>
	        		${accettazione.animale.provinciaRitrovamento} 
	        	</td>
	        	<td>
	        	</td>
        	</tr>
        		   	
        	<tr class='even'>
	        	<td>
	        		Indirizzo Ritrovamento
	        	</td>
	        	<td>
	        		${accettazione.animale.indirizzoRitrovamento} 
	        	</td>
	        	<td>
	        	</td>
        	</tr>	
        	
        	<tr class='odd'>
	        	<td>
	        		Note Ritrovamento
	        	</td>
	        	<td>
	        		<c:out value="<%=accettazione.getAnimale().getNoteRitrovamento() %>"/>		        		    		
	        	</td>
	        	<td>
	        	</td>
        	</tr>   			   		 	   		 
        </c:if>

<% 	
	memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
 		System.out.println("Jsp in esecuzione: " + "vam.accettazione.detail(whilegetCc1)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>	

 
        <% CartellaClinica cc=null;
           i = accettazione.getCartellaClinicas().iterator(); 
           while (i.hasNext()){
        	   cc = (CartellaClinica)i.next();
           }
           
           Boolean tsc = false;
           Trasferimento t = null;
           if (cc!=null && cc.getTrasferimenti()!=null){
           i = cc.getTrasferimenti().iterator();
           while (i.hasNext()){
        	   t = (Trasferimento) i.next();
        	   if(!t.getAutomaticoPerNecroscopia())
        	   {
        	   		tsc = true;
        	   }
            }}
          %>
		
        <c:if test="<%=((necroscopicoDaFare!=null && necroscopicoDaFare.equals(\"true\")) || 
        				(smaltimentoCarognaDaFare!=null && smaltimentoCarognaDaFare.equals(\"true\")) || 
        				(cc!=null && cc.getAutopsia()!=null && cc.getAutopsia().getDataAutopsia()!=null))  && 
        				(accettazione.getAnimale().getDataSmaltimentoCarogna()==null && tsc==false )%>">
		
			<fmt:formatDate value="<%=accettazione.getData()%>" pattern="dd/MM/yyyy" var="dataAcc" />
			<input type="button" value="Inserisci registrazione trasporto spoglie" onclick="smaltimentoCarcassa(<%=accettazione.getId()%>,'${dataAcc}');" />
      </c:if>
      
      <c:if test="<%=(((smaltimentoCarognaDaFare!=null && smaltimentoCarognaDaFare.equals("true")) || 
    		  		(cc!=null && cc.getAutopsia()!=null && cc.getAutopsia().getDataAutopsia()!=null)) 
    		  		&& accettazione.getAnimale().getDataSmaltimentoCarogna()!=null) || (tsc==true)%>">
    		  		
    	<input type="button" value="Vedi registrazione trasporto spoglie" onclick="smaltimentoCarcassaDetail();" />
      	
      </c:if>
	
</table>


<div id="modifica_anagrafica_animale_div" title="Anagrafica Animale">
</div>

<div id="smaltimento_carcassa_div" title="Registrazione Trasporto Spoglie">
</div> 

<div id="smaltimento_carcassa_detail_div" title="Registrazione Trasporto Spoglie">
	<table class="tabella">
		<tr class='even'>
    		<td>
    			 Data
    		</td>
    		<td>
    			<fmt:formatDate value="<%=accettazione.getAnimale().getDataSmaltimentoCarogna()%>" pattern="dd/MM/yyyy" var="dataSmalt" />
    			${dataSmalt}
    		</td>    		
        </tr>
                
             
        <tr class='even'>
    		
        </tr>
                
             
        <tr class='odd'>
	        <td>
	    		Ditta Autorizzata
    		</td>
    		<td>
		        <%=accettazione.getAnimale().getDittaAutorizzata()%>
	        </td>
	        <td>
	        </td>
        </tr>
        
        <tr class='even'>
	        <td>
	    		DDT
    		</td>
    		<td>
		         <%=accettazione.getAnimale().getDdt()%>
	        </td>
	        <td>
	        </td>
        </tr>
  </table>
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
	document.getElementById(idFrame).src='<%=CaninaRemoteUtil.getLogoutUrl( (BUtente)session.getAttribute( "utente" ),request ) %>';
}
</script>


<script type="text/javascript">

function modificaAnagraficaAnimale( idSpecie )
{
<%    
	Animale a = accettazione.getAnimale();
	int idR,idT,idM;
	
	if (anagraficaAnimale.getIdRazza()!=null) 
		idR=anagraficaAnimale.getIdRazza();
	else 
		idR=-1;
	if (anagraficaAnimale.getIdTaglia()!=null) 
		idT=anagraficaAnimale.getIdTaglia();
	else 
		idT=-1;
	if (anagraficaAnimale.getIdMantello()!=null) 
		idM=anagraficaAnimale.getIdMantello();
	else 
		idM=-1; 
%>
      
	$("#modifica_anagrafica_animale_div").load("jsp/anag_div.jsp?idA=<%=a.getId()%>&idS=<%=a.getLookupSpecie().getId()%>&idR=<%=idR%>&idT=<%=idT%>&sesso=<%=anagraficaAnimale.getSesso()%>&dec_no_anag=<%=a.getDecedutoNonAnagrafe()%>&idM=<%=idM%>&act=<%="vam.accettazione.ModificaAnagraficaAnimale.us"%>&redirectTo=<%="Detail"%>&idAcc=<%=accettazione.getId()%>");
	$('#modifica_anagrafica_animale_div').dialog( 'open' );
	
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
									(document.getElementById("mantelloCane")==null || document.getElementById("mantelloCane").value=='-1') && 
									(document.getElementById("mantelloGatto")==null || document.getElementById("mantelloGatto").value=='-1')
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
	
 function smaltimentoCarcassa(idAcc, dataAcc )
 {
	 var dataAutopsia = null;
<%
		i = accettazione.getCartellaClinicas().iterator();
		while (i.hasNext())
		{
			cc = (CartellaClinica)i.next();
			if(cc.getAutopsia()!=null)
			{
%>
				dataAutopsia = '<%=cc.getAutopsia().getDataAutopsia()%>';
<%
			}
		}
%>
	$("#smaltimento_carcassa_div").load("jsp/vam/accettazione/smaltimentoCarcassa.jsp?dataAutopsia="+dataAutopsia+"&idAcc=<%=accettazione.getId()%>&dataAcc=<%=accettazione.getData()%>");
 	$('#smaltimento_carcassa_div').dialog( 'open' );

 }	

  $(function() 
 			{
 				$( "#smaltimento_carcassa_div" ).dialog({
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
 							if(checkformSmaltimentoCarogna())
 							{
 								document.form.submit();
 							}
 						}
 					}
 			});
 	});	
  
  
  function smaltimentoCarcassaDetail()
  {
 //$("#smaltimento_carcassa_detail_div").load("jsp/vam/accettazione/smaltimentoCarcassaDetail.jsp");
  	$('#smaltimento_carcassa_detail_div').dialog( 'open' );
  }	

   $(function() 
  			{
  				$( "#smaltimento_carcassa_detail_div" ).dialog({
  					height: screen.height/3,
  					modal: true,
  					autoOpen: false,
  					closeOnEscape: true,
  					show: 'blind',
  					resizable: true,
  					draggable: true,
  					width: screen.width/2,
  					buttons: {
  						"OK": function() {
  							$( this ).dialog( "close" );
  						}
  					}
  			});
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
		<div id="dettaglio_reg_<%=temp.getId()%>" title="Dettaglio Registrazione di <%=temp.getDescription()%> <%=(accettazione.getAdozioneVersoAssocCanili()!=null && accettazione.getAdozioneVersoAssocCanili())?("verso Associazioni/Canili"):("")%> <%=(accettazione.getAdozioneFuoriAsl()!=null && accettazione.getAdozioneFuoriAsl())?("fuori Asl"):("")%>">
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
		GenericAction.aggiornaConnessioneApertaSessione(request);
		
		%>
		 <!-- VECCHIA GESTIONE TOKEN CENTRALIZZATO-->
		<%--document.getElementById("href"+<%=temp.getId()%>).href="javascript:dettaglioReg(<%=accettazione.getId()%>,<%=temp.getId()%>,'myFrame<%=temp.getId()%>','<%=CaninaRemoteUtil.getLoginUrl( (BUtente)session.getAttribute( "utente" ), null, accettazione.getAnimale(), temp, accettazione, idRegBdr, connection,request,"2" ) %>');";--%> 
		<!--NUOVA GESTIONE  devo trasformare l'href in una chiamata a funzione con onclick, poichè qui l'url arriva encoded e da problemi con l'href se già encoded-->
		function dettaglioRegCaller()
		{
			dettaglioReg(<%=accettazione.getId()%>,<%=temp.getId()%>,'myFrame<%=temp.getId()%>','<%=CaninaRemoteUtil.getLoginUrl( (BUtente)session.getAttribute( "utente" ), null, accettazione.getAnimale(), temp, accettazione, idRegBdr, connection,request,"2" ) %>');
		}
		
	 	document.getElementById("href"+<%=temp.getId()%>).onclick= dettaglioRegCaller; 
	 	document.getElementById("href"+<%=temp.getId()%>).href = '#';
		<!--fine modifica nuova gestione per token ------------------------------------->
	 	
		<%
			connection.close();
			GenericAction.aggiornaConnessioneChiusaSessione(request);
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
	
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp eseguita: " + "vam.accettazione.detail(fine)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
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
 
if(!controllaDataAnnoCorrente2(document.getElementById('dataAperturaToCheck').value))
{
document.getElementById('apriCcButton').style.display="none";
}
</script>
