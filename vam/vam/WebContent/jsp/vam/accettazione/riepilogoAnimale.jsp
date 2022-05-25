<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.vam.TrasferimentoNoH"%>
<%@page import="it.us.web.bean.vam.lookup.LookupMantelli"%>
<%@page import="it.us.web.bean.vam.lookup.LookupTaglie"%>
<%@page import="it.us.web.bean.vam.AnimaleAnagrafica"%>
<%@page import="it.us.web.bean.vam.Trasferimento"%>
<%@page import="it.us.web.bean.vam.Animale"%>
<%@page import="it.us.web.constants.Specie"%>
<%@page import="it.us.web.bean.remoteBean.Colonia"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.us.web.bean.vam.lookup.LookupRazze"%>
<%@page import="it.us.web.constants.SpecieAnimali"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>

<%
	Iterator<LookupRazze> iterRazzeCane = ((List<LookupRazze> )request.getServletContext().getAttribute("razzeCane")).iterator(); 
	Iterator<LookupRazze> iterRazzeGatto = ((List<LookupRazze> )request.getServletContext().getAttribute("razzeGatto")).iterator(); 
	Iterator<LookupMantelli> iterMantelliCane = ((List<LookupMantelli> )request.getServletContext().getAttribute("mantelliCane")).iterator(); 
	Iterator<LookupMantelli> iterMantelliGatto = ((List<LookupMantelli> )request.getServletContext().getAttribute("mantelliGatto")).iterator(); 
	Iterator<LookupTaglie> iterTaglie =  ((List<LookupTaglie>)request.getServletContext().getAttribute("taglie")).iterator();
	
	TrasferimentoNoH tr = (TrasferimentoNoH)request.getAttribute("tr");
	Animale animale = (Animale)request.getAttribute("animale");
	AnimaleAnagrafica anagraficaAnimale = (AnimaleAnagrafica)request.getAttribute("anagraficaAnimale");
	SpecieAnimali specie = (SpecieAnimali)request.getAttribute("specie");
	String dataDecesso = ((request.getAttribute("dataDecesso")!=null)?((String)request.getAttribute("dataDecesso")):(""));
	Colonia colonia = (Colonia)request.getAttribute("colonia");
	int accettazioniSize = (Integer)request.getAttribute("accettazioniSize");  
	String dataDecessoCertezza = (String)request.getAttribute("dataDecessoCertezza");
	String proprietarioTipo =(String)request.getAttribute("proprietarioTipo");
	String proprietarioCognome =(String)request.getAttribute("proprietarioCognome");
	String proprietarioNome =(String)request.getAttribute("proprietarioNome");
	String proprietarioCodiceFiscale =(String)request.getAttribute("proprietarioCodiceFiscale");
	String proprietarioDocumento =(String)request.getAttribute("proprietarioDocumento");
	String proprietarioIndirizzo =(String)request.getAttribute("proprietarioIndirizzo");
	String proprietarioCap =(String)request.getAttribute("proprietarioCap");
	String proprietarioComune =(String)request.getAttribute("proprietarioComune");
	String proprietarioProvincia =(String)request.getAttribute("proprietarioProvincia");
	String proprietarioTelefono =(String)request.getAttribute("proprietarioTelefono");
	
	String errore_apertura_accettazione =(String)request.getAttribute("errore_apertura_nuova_accettazione");
	
%>

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
	System.out.println("Jsp in esecuzione: " + "vam.accettazione.riepilogoAnimale(inizio)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
			   "Memoria usata: " + (memFreeStart - memFreeEnd) +
			   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
	memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	dataStart 		= new Date();
%>
<table>
<tr>
<td style="width:40%;">
<fieldset>
	<legend>Dettagli Animale "<%=animale.getIdentificativo()%>"</legend>
	<ul>
		<li>Identificativo: <%=animale.getIdentificativo()%></li>
		<li>Tatuaggio/II MC: <%=(animale.getTatuaggio() != null) ? animale.getTatuaggio() : "---" %></li>
		<li>Tipologia: <%=animale.getLookupSpecie().getDescription()%></li>
		<c:if test="<%=(animale.getLookupSpecie().getId()==specie.getCane() || animale.getLookupSpecie().getId()==specie.getGatto())%>">
			<li>Data Nascita: <fmt:formatDate type="date" value="<%=animale.getDataNascita()%>" pattern="dd/MM/yyyy" /></li>
		</c:if>
		<c:if test="<%=animale.getLookupSpecie().getId()==specie.getSinantropo()%>">
			<li>Et&agrave;: <%if (animale.getEta()!=null){%><%=animale.getEta()%><%}%></li>
		</c:if>
		<c:choose>
			<c:when test="<%=animale.getDecedutoNonAnagrafe()==true %>">						
				<fmt:formatDate type="date" value="<%=animale.getDataMorte()%>" pattern="dd/MM/yyyy" var="dataMorte"/>
			</c:when>
			<c:otherwise>
				<%=dataDecesso%>
			</c:otherwise>	
		</c:choose>	
		<li>Data Decesso: ${dataDecesso} - ${dataDecessoCertezza} </li>
		
		<c:set scope="request" var="anagraficaAnimale" value="${anagraficaAnimale}"/>
        <c:import url="../vam/accettazione/anagraficaAnimale.jsp"/>
        
		<c:if test="<%=animale.getClinicaChippatura()!=null %>">
			<li>Microchippato nella clinica <%=animale.getClinicaChippatura().getNome() %></li>
		</c:if>
	</ul>
</fieldset>
</td>

<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.riepilogoAnimale(parte1)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>


<td style="width:60%;">
<fieldset>
	<c:choose>
		<c:when test="<%=animale.getLookupSpecie().getId()==specie.getSinantropo() %>">
			<legend>Detentore</legend>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${colonia!=null}">
					<legend>Colonia</legend>
				</c:when>
				<c:otherwise>
					<legend>Proprietario <%if (proprietarioTipo != null && !proprietarioTipo.equals("null")){%><%=proprietarioTipo%><%}%></legend>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	<ul>
		<c:choose>
			<c:when test="${colonia!=null}">
				<fmt:formatDate pattern="dd/MM/yyyy" var="dataRegistrazioneColonia" value="<%=colonia.getDataRegistrazioneColonia() %>"/>
				<li><b>Colonia: </b><%=colonia.getNumeroProtocollo() %> 
										<c:if test="<%=colonia.getDataRegistrazioneColonia()!=null %>">
										 	registrata il <fmt:formatDate type="date" value="${colonia.dataRegistrazioneColonia}" pattern="dd/MM/yyyy" />
										</c:if>
				</li>
				<li><b>Indirizzo: </b><%=colonia.getIndirizzoColonia()%>, <%=colonia.getCittaColonia() %> 
											<c:if test="${colonia.provinciaColonia}">
												(${colonia.provinciaColonia})						
											</c:if>
											<c:if test="${colonia.cap}">
												- ${colonia.cap}						
											</c:if>
				</li>
				<li><b>Nominativo Referente: </b><%=colonia.getCognomeReferente() %></li>
				<li><b>Codice fiscale Referente: </b><%=colonia.getCodiceFiscaleReferente() %></li>
				<li><b>Documento Identità Referente: </b><%=colonia.getDocumentoIdentita() %></li>
				<li><b>Telefono Referente: </b>${colonia.telefonoReferente}</li>
				</br>
				</br>
			</c:when>
			<c:when test="<%=proprietarioTipo!=null && (proprietarioTipo.equals(\"Importatore\") || proprietarioTipo.equals(\"Operatore Commerciale\")) %>">
				<li><b>Ragione Sociale: </b><%if (proprietarioNome!=null){%><%=proprietarioNome %><%} %></li>
				<li><b>Partita Iva: </b><%if (proprietarioCognome!=null){%><%=proprietarioCognome%><%} %></li>
				<li><b>Rappr. Legale: </b><%if (proprietarioCodiceFiscale!=null){%><%=proprietarioCodiceFiscale %><%} %></li>
				<li><b>Telefono struttura(principale): </b><%if (proprietarioTelefono!=null){%><%=proprietarioTelefono %><%} %></li>
				<li><b>Telefono struttura(secondario): </b><%if (proprietarioDocumento!=null){%><%=proprietarioDocumento %><%} %></li>
				<u>Sede operativa</u>
				<li><b>Indirizzo: </b><%if (proprietarioIndirizzo!=null){%><%=proprietarioIndirizzo %><%} %></li>
				<li><b>CAP: </b><%if (proprietarioCap!=null){%><%=proprietarioCap %><%} %></li>
				<li><b>Comune: </b><%if (proprietarioComune!=null){%><%=proprietarioComune %><%} %></li>
				<li><b>Provincia: </b><%if (proprietarioProvincia!=null){%><%=proprietarioProvincia %><%} %></li>
			</c:when>
			<c:otherwise>
				<c:if test="<%=proprietarioTipo!=null && proprietarioTipo.equals(\"Canile\") %>">
					<li><b>Ragione Sociale: </b><%if (proprietarioNome!=null){%><%=proprietarioNome %><%} %></li>
					<li><b>Partita Iva: </b><%if (proprietarioCognome!=null){%><%=proprietarioCognome%><%} %></li>
					<li><b>Rappr. Legale: </b><%if (proprietarioCodiceFiscale!=null){%><%=proprietarioCodiceFiscale %><%} %></li>
				</c:if>
				<c:if test="<%=proprietarioTipo!=null && !proprietarioTipo.equals(\"Canile\") %>">
					<c:if test="<%=!animale.getRandagio()%>">
						<li><b>Cognome: </b><%if (proprietarioCognome!=null){%><%=proprietarioCognome%><%} %></li>
					</c:if>
					<li><b>Nome: </b><%if (proprietarioNome!=null){%><%=proprietarioNome %><%} %></li>
				</c:if>	
				<c:if test="<%=!animale.getRandagio() %>">
					<c:if test="<%=proprietarioTipo!=null && !proprietarioTipo.equals(\"Canile\") %>">
						<li><b>Codice Fiscale: </b><%if (proprietarioCodiceFiscale!=null){%><%=proprietarioCodiceFiscale %><%} %></li></c:if>
					<li><b>Documento: </b><%if (proprietarioDocumento!=null){%><%=proprietarioDocumento %><%} %></li>
					<li><b>Indirizzo: </b><%if (proprietarioIndirizzo!=null){%><%=proprietarioIndirizzo %><%} %></li>
					<li><b>CAP: </b><%if (proprietarioCap!=null){%><%=proprietarioCap %><%} %></li>
					<li><b>Comune: </b><%if (proprietarioComune!=null){%><%=proprietarioComune %><%} %></li>
					<li><b>Provincia: </b><%if (proprietarioProvincia!=null){%><%=proprietarioProvincia %><%} %></li>
					<li><b>Telefono: </b><%if (proprietarioTelefono!=null){%><%=proprietarioTelefono %><%} %></li>
				</c:if>
				<c:if test="<%=animale.getRandagio()%>">
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
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.riepilogoAnimale(parte2)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>
<c:if test="${errore_apertura_accettazione!=''}">
<div style="color:red;">${errore_apertura_accettazione}</div><br>
</c:if>

<c:if test="<%=tr==null %>">
	<c:if test="${utente.ruolo=='Referente Asl' || utente.ruolo=='14'}">
		<input type="button" onclick="nuovaAccettazionePregressa('<%=animale.getId()%>','<%=utente.getId()%>')" value="Nuova Accettazione" />
	</c:if>
	<c:if test="${utente.ruolo!='Referente Asl' && utente.ruolo!='14' && ((utente.ruolo!='Universita' && utente.ruolo!='IZSM' && utente.ruolo!='6' && utente.ruolo!='8') || dataDecesso!='')}">
		<input type="button" onclick="nuovaAccettazione('<%=animale.getId()%>','<%=utente.getId()%>')" value="Nuova Accettazione" />
	</c:if>
	
	
</c:if>


<c:if test="<%=tr!=null %>">
	<a onclick="$( '#dettaglio_${tr.id}_div' ).dialog( 'open' );" style="cursor: pointer; text-decoration: underline; color: blue;">Dettaglio Richiesta Trasferimento in Ingresso</a>
	<input type="button" onclick="attendere(),location.href='vam.cc.trasferimenti.ToAccettazioneDestinatario.us?id=${tr.id}'" value="Accettazione Trasferimento in Ingresso" />
	<%@ include file="../cc/trasferimenti/detailInclude.jsp" %>
</c:if>

<c:if test="${accettazioniSize>0}">
	<input type="button" onclick="attendere(),location.href='vam.accettazione.List.us?idAnimale=<%=animale.getId() %>'" value="Accettazioni Precedenti" />
</c:if>

 <!--  c:if test="${fuoriAsl==false}"-->
 <br/>
 <c:choose>
  	<c:when test="<%=(animale.getLookupSpecie().getId()==specie.getSinantropo() || animale.getDecedutoNonAnagrafe()==true) %>">
  		<input type="button" value="Modifica Anagrafica Animale" onclick="modificaAnagraficaAnimale(<%=animale.getLookupSpecie().getId()%>);" />
  	</c:when>
  	<c:otherwise>
  		<us:can f="BDR" sf="EDIT" og="MAIN" r="w">
  			<input type="button" value="Modifica Anagrafica Animale" onclick="modificaAnagraficaAnimale(<%=animale.getLookupSpecie().getId()%>);" />
  		</us:can>
  	</c:otherwise>
  </c:choose>
 	
  	
 <!-- /c:if -->
<c:if test="<%=accettazioniSize<=0 %>">
	<br/> Nessuna accettazione precedente.
</c:if>



<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.riepilogoAnimale(parte3)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>



<div id="modifica_anagrafica_animale_div" title="Anagrafica Animale">
</div>
 
 <script type="text/javascript">	
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
								(document.getElementById("mantelloGatto")==null || document.getElementById("mantelloGatto").value=='-1'))
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
	
	function modificaAnagraficaAnimale( idSpecie )
	{	
		<%    
		Animale a = animale;
		int idR,idT,idM;
		if (anagraficaAnimale.getIdRazza()!=null) idR=anagraficaAnimale.getIdRazza();
		else idR=-1;
		if (anagraficaAnimale.getIdTaglia()!=null) idT=anagraficaAnimale.getIdTaglia();
		else idT=-1;
		if (anagraficaAnimale.getIdMantello()!=null) idM=anagraficaAnimale.getIdMantello();
		else idM=-1;
		%>
		$("#modifica_anagrafica_animale_div").load("jsp/anag_div.jsp?idA=<%=a.getId()%>&idS=<%=a.getLookupSpecie().getId()%>&idR=<%=idR%>&idT=<%=idT%>&sesso=<%=anagraficaAnimale.getSesso()%>&dec_no_anag=<%=a.getDecedutoNonAnagrafe()%>&idM=<%=idM%>&act=<%="vam.accettazione.ModificaAnagraficaAnimale.us"%>&redirectTo=<%="FindAnimale"%>&idAcc=-1");
		$( '#modifica_anagrafica_animale_div' ).dialog( 'open' );
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
	};	
	
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
   														        window.location.href=window.location.href;
   														    }
   														    else
   														        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
   														 }
									 			});
	}
</script>



<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.accettazione.riepilogoAnimale(fine)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>
