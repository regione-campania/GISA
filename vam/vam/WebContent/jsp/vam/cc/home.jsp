<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.vam.Autopsia"%>
<%@page import="it.us.web.bean.vam.AnimaleAnagrafica"%>
<%@page import="it.us.web.bean.vam.Animale"%>
<%@page import="java.util.List"%>
<%@page import="it.us.web.bean.vam.lookup.LookupMantelli"%>
<%@page import="it.us.web.bean.vam.lookup.LookupRazze"%>
<%@page import="java.util.Iterator"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="it.us.web.constants.Specie"%>
<%@page import="java.util.Date"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>


<%
	CartellaClinica cc = (CartellaClinica)session.getAttribute("cc");
	Iterator<LookupRazze> iterRazzeCane = ((List<LookupRazze> )request.getServletContext().getAttribute("razzeCane")).iterator(); 
	Iterator<LookupRazze> iterRazzeGatto = ((List<LookupRazze> )request.getServletContext().getAttribute("razzeGatto")).iterator(); 
	Iterator<LookupMantelli> iterMantelliCane = ((List<LookupMantelli> )request.getServletContext().getAttribute("mantelliCane")).iterator(); 
	Iterator<LookupMantelli> iterMantelliGatto = ((List<LookupMantelli> )request.getServletContext().getAttribute("mantelliGatto")).iterator(); 
	Animale animale = cc.getAccettazione().getAnimale();
	AnimaleAnagrafica anagraficaAnimale = (AnimaleAnagrafica)request.getAttribute("anagraficaAnimale");
	Autopsia autopsia = (Autopsia)request.getAttribute("a");
%>


<%
	long memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	Date dataStart 		= new Date();
	long memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
	Date dataEnd  	= new Date();
	BUtente utente = (BUtente)request.getSession().getAttribute("utente");
	long timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
	System.out.println("Jsp in esecuzione: " + "vam.cc.home(inizio)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
			   "Memoria usata: " + (memFreeStart - memFreeEnd) +
			   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
	memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
	dataStart 		= new Date();
%>


<script language="Javascript">
function isnum(obj) {

if (isNaN(obj.value) || parseInt(obj.value)<0 || parseInt(obj.value) > 9999)

{

alert('Nel campo PESO ANIMALE è possibile immettere solo numeri!');
obj.value="";
obj.focus();
}

}


</script>
     	 
	 <jsp:include page="/jsp/vam/cc/menuCC.jsp?riepilogoCc=no"/>
	 <h4 class="titolopagina">
     		Cartella Clinica
     </h4> 
    
	<c:if test="${cc.ccMorto}">
		<c:set var="tipo" scope="request" value="stampaDecesso"/>
		<c:import url="../../jsp/documentale/home3.jsp"/>
	</c:if>
	
    	<INPUT type="button" value="Gestisci Informazioni (Peso, Habitat, Alimentazione e Ferite d'Arma da Fuoco)" onclick="if(${cc.dataChiusura!=null}){ 
			if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){editInfo();}
			}else{editInfo();}" />
    
    <!--  c:if test="${fuoriAsl==false}"-->
    	<br/>
    	
<%
		if(animale.getLookupSpecie().getId() == Specie.SINANTROPO || animale.getDecedutoNonAnagrafe() )
		{
%>	
			<INPUT type="button" value="Modifica Anagrafica Animale" onclick="modificaAnagraficaAnimale(<%=animale.getLookupSpecie().getId()%>);" />
<%
		}
		else
		{
%>  		
  			<us:can f="BDR" sf="EDIT" og="MAIN" r="w">
				<INPUT type="button" value="Modifica Anagrafica Animale" onclick="modificaAnagraficaAnimale(<%=animale.getLookupSpecie().getId()%>);" />
  			</us:can>
<%
		}
%>
    <INPUT type="button" value="Stampa CC" onclick="javascript:window.open('vam.cc.StampaDetail.us','_blank', 'width=700,height=700,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');" />
    
    <!--  /c:if-->

<%
		if(cc.getCcMorto() && cc.getDataChiusura()==null)
		{
			if(autopsia!=null && (autopsia.getDataEsito()==null || autopsia.getPatologiaDefinitiva()==null))
			{
%>				
				<br/>La cc non può essere chiusa: inserire almeno la data di effettuazione necroscopia e il quadro patologico prevalente
<%			
			}
			else
			{
%>
    			<br/>
    			<input type="button" value="Chiusura Cc" onclick="chiusuraCc('${cc.autopsia.dataAutopsia}','${cc.accettazione.animale.dataSmaltimentoCarogna}');" />
<%
			}
		}
%> 
    <%--   <c:if test="<%=cc.getCancellabile() || utente.getRuolo().equals(\"Referente Asl\") || utente.getRuolo().equals(\"14\") %>"> --%>
<us:can f="CC" sf="DELETE" og="MAIN" r="w" >
	 <input type="button" value="Elimina" onclick="if( myConfirm('Sei sicuro di voler eliminare la cartella clinica?') ){location.href='vam.cc.Delete.us?id=<%=cc.getId()%>'}" />
</us:can>
	 
<%-- </c:if>   --%>
    <table class="tabella">
    
    	<tr>
    		<th colspan="3">
    			Dati generali
    		</th>
    	</tr>
    	
    	    	
    	<tr class='even'>
    		<td>
    			Numero cartella clinica
    		</td>
    		<td>
    			<c:out value="${cc.numero}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Fascicolo Sanitario
    		</td>
    		<td>
    		<%
    		if(cc.getFascicoloSanitario()!=null)
    		{
    		%>
    			<%=cc.getFascicoloSanitario().getNumero()%>
    		<%
    		}
    		%>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Numero di riferimento accettazione
    		</td>
    		<td>
    			<us:can f="ACCETTAZIONE" sf="DETAIL" og="MAIN" r="w">
	    		<a href="vam.accettazione.Detail.us?id=${cc.accettazione.id}" onclick="attendere()">
	    		</us:can>
	    			<%=cc.getAccettazione().getProgressivoFormattato()%>
	    		<us:can f="ACCETTAZIONE" sf="DETAIL" og="MAIN" r="w">
	    			</a>    			
	    		</us:can>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			 Data di apertura
    		</td>
    		<td>
    			 <fmt:formatDate pattern="dd/MM/yyyy" var="dataApertura" value="${cc.dataApertura}"/>
    			 ${dataApertura}
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			<c:choose>
    				<c:when test="${cc.dataChiusura==null}">
    					Data di chiusura
    				</c:when>
    				<c:otherwise>
    					<b>Data di chiusura</b>
    				</c:otherwise>
    			</c:choose>
    		</td>
    		<td>
    			 <fmt:formatDate pattern="dd/MM/yyyy" var="dataChiusura" value="${cc.dataChiusura}"/>
    			 <b>${dataChiusura}</b>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Inserita da
    		</td>
    		<td>
    			 ${cc.enteredBy}
    		</td>
    		<td>
    		<fmt:formatDate pattern="dd/MM/yyyy" value="${cc.entered}"/>
    		</td>
        </tr>
        <tr class='even'>
        	<td>
        		Modificata da
        	</td>
        	<td>
        		${cc.modifiedBy}
        	</td>
        	<td>
        	<fmt:formatDate pattern="dd/MM/yyyy" value="${cc.modified}"/>
        	</td>
        </tr>   
        
        <% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.cc.home(prima parte)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>


        <c:choose> 
			<c:when test="${cc.ccMorto}"> 
				<tr class='odd'>
    				<td>
    					Tipologia di cartella clinica
    				</td>
    				<td>
    					Necroscopica
    				</td>
    				<td>
    				</td>
   				</tr> 
	    	</c:when>
	    	<c:otherwise>
		    	<tr class='odd'>
    				<td>
    					Tipologia di cartella clinica
    				</td>
    				<td>
    					<c:choose> 
    						<c:when test="${cc.dayHospital == true}"> 
    							Day Hospital 
    								<td>  						
    										<input type="button" value="Cambia in degenza" onclick="if(${cc.dataChiusura!=null}){ 
    						    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){if( myConfirm('Sei sicuro di voler cambiare la tipologia della cartella clinica da Day Hospital in Degenza') ){attendere(), location.href='vam.cc.ChangeToDegenza.us'}}
    				    						}else{if( myConfirm('Sei sicuro di voler cambiare la tipologia della cartella clinica da Day Hospital in Degenza') ){attendere(), location.href='vam.cc.ChangeToDegenza.us'}}" />
    								</td>
    						</c:when>    				
		    				<c:otherwise> 
    							Degenza 
    								<td>  
    										<input type="button" value="Cambia in Day Hospital" onclick="if(${cc.dataChiusura!=null}){ 
    						    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){if( myConfirm('Sei sicuro di voler cambiare la tipologia della cartella clinica da Degenza in Day Hospital') ){attendere(), location.href='vam.cc.ChangeToDayHospital.us'}}
    				    						}else{if( myConfirm('Sei sicuro di voler cambiare la tipologia della cartella clinica da Degenza in Day Hospital') ){attendere(), location.href='vam.cc.ChangeToDayHospital.us'}}" />
    								</td>
    						</c:otherwise>    				
		    			</c:choose> 
    				</td>
		        </tr> 
			</c:otherwise>
		</c:choose>	   

		    
		    <% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.cc.home(pre anagraficaanimale)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>  
        
        <tr>
    		<th colspan="3">
    			Anagrafica Animale
    		</th>
    	</tr>
    	
    	<tr class='even'>
    		<td>
    			Tipologia
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.animale.lookupSpecie.description}"/>
    		</td>  
    		<td>
    		</td>  		
        </tr>
    	
    	<tr class='odd'>
    		<td>
    			Identificativo
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.animale.identificativo}"/>
    		</td>  
    		<td>
    		</td>  		
        </tr>
        <tr class='odd'>
    		<td>
    			Tatuaggio/II MC
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.animale.tatuaggio}"/>
    		</td>  
    		<td>
    		</td>  		
        </tr>
        
        <tr class='even'>
    		<c:choose>
				<c:when test="${cc.accettazione.animale.lookupSpecie.id==3}">
					<td>
						Et&agrave;
					</td>
					<td>
						<fmt:formatDate type="date" value="${cc.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita" />
						${cc.accettazione.animale.eta.description} <c:if test="${dataNascita}">(${dataNascita})</c:if>
					</td>
				</c:when>
				<c:otherwise>
					<td>
						Data nascita 
					</td>
					<td>
						<fmt:formatDate type="date" value="${cc.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"  />
  						${dataNascita}
					</td>
				</c:otherwise>
			</c:choose>  
    		<td>
    		</td> 		
        </tr>
        
        <c:set scope="request" var="anagraficaAnimale" value="${anagraficaAnimale}"/>
        <c:import url="../vam/anagraficaAnimale.jsp"/>
        
        <% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.cc.home(pre decesso)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>

       <c:if test="${cc.accettazione.animale.dataMorte!=null || res.dataEvento!=null}">
       <c:choose>
        	<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe == true}">						
				<fmt:formatDate type="date" value="${cc.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="dataMorte"/>
			</c:when>
			<c:otherwise>
				<fmt:formatDate type="date" value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/>
			</c:otherwise>	
		</c:choose>	 
        <tr class='odd'>
   			<td>
   				Data del decesso
   			</td>
   			<td>
				${dataMorte}
			</td>
			<td>			
				<c:choose>
					<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe == true}">						
						${cc.accettazione.animale.dataMorteCertezza}
					</c:when>
					<c:otherwise>
						${res.dataMorteCertezza}
					</c:otherwise>	
				</c:choose>	 
        	</td>
   		</tr>
    	
   		<tr class='even'>
      	  <td>
    			Probabile causa del decesso
   			</td>
   			<td>    
   				<c:choose>
    				<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
    					${cc.accettazione.animale.causaMorte.description}
    				</c:when>
    				<c:otherwise>
    					${res.decessoValue}
    				</c:otherwise>
    			</c:choose>	        	        
        	</td>
       		<td>
        	</td>
       	</tr>
       	</c:if>
       	
       	<c:if test="${cc.accettazione.animale.clinicaChippatura!=null && cc.accettazione.animale.clinicaChippatura.id>0}">
			<tr class='odd'><td>Microchippato nella clinica </td><td>${cc.accettazione.animale.clinicaChippatura.nome}</td><td></td></tr>
		</c:if>
        
        <c:set var="proprietarioCognome" value="${cc.accettazione.proprietarioCognome}"/>
        
        <c:choose>
			<c:when test="${cc.accettazione.proprietarioTipo=='Importatore' || cc.accettazione.proprietarioTipo=='Operatore Commerciale'}">
				<tr>
    				<th colspan="3">
    					Anagrafica proprietario ${cc.accettazione.proprietarioTipo}
    				</th>
    			</tr>	
				<tr class='even'><td>Ragione Sociale:</td><td>${cc.accettazione.proprietarioNome}</td><td></td></tr>
				<tr class='odd'><td>Partita Iva:</td><td>${cc.accettazione.proprietarioCognome }</td><td></td></tr>
				<tr class='even'><td>Rappr. Legale:</td><td>${cc.accettazione.proprietarioCodiceFiscale }</td><td></td></tr>
				<tr class='odd'><td>Telefono struttura(principale):</td><td>${cc.accettazione.proprietarioTelefono }</td><td></td></tr>
				<tr class='even'><td>Telefono struttura(secondario):</td><td>${cc.accettazione.proprietarioDocumento }</td><td></td></tr>
				<tr class='odd'><td>Indirizzo sede operativa:</td><td>${cc.accettazione.proprietarioIndirizzo }</td><td></td></tr>
				<tr class='even'><td>CAP sede operativa:</td><td>${cc.accettazione.proprietarioCap }</td><td></td></tr>
				<tr class='odd'><td>Comune sede operativa:</td><td>${cc.accettazione.proprietarioComune }</td><td></td></tr>
				<tr class='even'><td>Provincia sede operativa:</td><td>${cc.accettazione.proprietarioProvincia }</td><td></td></tr>
			</c:when>        
			<c:when test="${animale.lookupSpecie.id == specie.sinantropo || !fn:startsWith(proprietarioCognome, '<b>')}">
				<tr>
    		<th colspan="3">
    		<c:choose>
    			<c:when test="${cc.accettazione.animale.lookupSpecie.id == specie.sinantropo}">
    			Detentore
    			</c:when>
    		<c:otherwise>    		
    			Anagrafica proprietario <c:if test="${cc.accettazione.proprietarioTipo!=null && cc.accettazione.proprietarioTipo!='null'}">${cc.accettazione.proprietarioTipo}</c:if>
    			</c:otherwise>
    			</c:choose>
    		</th>
    	</tr>			
 	    	<c:if test="${cc.accettazione.proprietarioTipo!=null && cc.accettazione.proprietarioTipo=='Canile'}">
				<tr class='even'><td>Ragione Sociale: </td><td><c:out value="${cc.accettazione.proprietarioNome}"/></td><td></td></tr>
				<tr class='odd'><td>Partita Iva:</td><td>${cc.accettazione.proprietarioCognome }</td><td></td></tr>
				<tr class='even'><td>Rappr. Legale:</td><td>${cc.accettazione.proprietarioCodiceFiscale }</td><td></td></tr>
			</c:if>
			<c:if test="${cc.accettazione.proprietarioTipo!=null && cc.accettazione.proprietarioTipo!='Canile' && cc.accettazione.proprietarioTipo!='colonia'}">
				<c:if test="${!cc.accettazione.randagio}">
						<tr class='even'><td>Cognome:</td><td><c:if test="${cc.accettazione.proprietarioCognome!='null'}"> <c:out value="${cc.accettazione.proprietarioCognome}"/></c:if></td><td></td></tr>
				</c:if>
				<tr class='odd'><td>Nome:</td><td><c:if test="${cc.accettazione.proprietarioNome!='null'}"> <c:out value="${cc.accettazione.proprietarioNome}"/></c:if></td><td></td></tr>
			</c:if>
		
<%--		<c:if test="${!cc.accettazione.randagio}">	
        	<tr class='even'>
    			<td>
    				Cognome
    			</td>
    			<td>
    				<c:out value="${cc.accettazione.proprietarioCognome}"/>
    			</td>
    			<td>
    			</td>    		
        	</tr>
        </c:if>
        
        <tr class='odd'>
    		<td>
    			Nome
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.proprietarioNome}"/>
    		</td>
    		<td>
    		</td>    		
        </tr> --%> 
        
        <c:if test="${!cc.accettazione.randagio && cc.accettazione.proprietarioTipo!='colonia'}">
        <c:if test="${cc.accettazione.proprietarioTipo!='Canile'}">
        <tr class='even'>
    		<td>
    			Codice Fiscale
    		</td>
    		<td>
    			<c:if test="${cc.accettazione.proprietarioCodiceFiscale!='null'}"> <c:out value="${cc.accettazione.proprietarioCodiceFiscale}"/></c:if>
    		</td>
    		<td>
    		</td>
        </tr>
        </c:if>
        
        <tr class='odd'>
    		<td>
    			Documento
    		</td>
    		<td>
    			<c:if test="${cc.accettazione.proprietarioDocumento!='null'}"> <c:out value="${cc.accettazione.proprietarioDocumento}"/></c:if>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Indirizzo
    		</td>
    		<td>
    			<c:if test="${cc.accettazione.proprietarioIndirizzo!='null'}"> <c:out value="${cc.accettazione.proprietarioIndirizzo}"/></c:if>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Comune
    		</td>
    		<td>
    			<c:if test="${cc.accettazione.proprietarioComune!='null'}"> <c:out value="${cc.accettazione.proprietarioComune}"/></c:if>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Provincia
    		</td>
    		<td>
    			<c:if test="${cc.accettazione.proprietarioProvincia!='null'}"> <c:out value="${cc.accettazione.proprietarioProvincia}"/></c:if>
    		</td>
    		<td>
    		</td>
        </tr>
        </c:if>  
        
        
			</c:when>
			<c:otherwise>
				<tr>
    		<th colspan="3">
    			Anagrafica colonia
    		</th>
    	</tr>
    	<tr class='even'>
    		<td>
    			Colonia
    		</td>
    		<td>
    			${cc.accettazione.nomeColonia}
    		</td>
    		<td>
    		</td>
        </tr>
        <tr class='odd'>
    		<td>
    			Indirizzo
    		</td>
    		<td>
    			${cc.accettazione.proprietarioIndirizzo}, ${cc.accettazione.proprietarioComune}
											<c:if test="${cc.accettazione.proprietarioProvincia!='' && cc.accettazione.proprietarioProvincia!='  '}">
												(${cc.accettazione.proprietarioProvincia})						
											</c:if>
											<c:if test="${cc.accettazione.proprietarioCap!='' && cc.accettazione.proprietarioCap!='     '}">
												- ${cc.accettazione.proprietarioCap}						
											</c:if>
    		</td>
    		<td>
    		</td>
        </tr>
        <tr class='even'>
    		<td>
    			Nominativo Referente
    		</td>
    		<td>
    			${cc.accettazione.proprietarioNome}
    		</td>
    		<td>
    		</td>
        </tr>
        <tr class='odd'>
    		<td>
    			Codice Fiscale Referente
    		</td>
    		<td>
    			${cc.accettazione.proprietarioCodiceFiscale}
    		</td>
    		<td>
    		</td>
        </tr>
        <tr class='even'>
    		<td>
    			Documento Referente
    		</td>
    		<td>
    			${cc.accettazione.proprietarioDocumento}
    		</td>
    		<td>
    		</td>
        </tr>
        <tr class='odd'>
    		<td>
    			Telefono Referente
    		</td>
    		<td>
    			${cc.accettazione.proprietarioTelefono}
    		</td>
    		<td>
    		</td>
        </tr>
			</c:otherwise>
		</c:choose>
		
		<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.cc.home(ritrovam)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>


		<!--  DATI RITROVAMENTO  -->
 		<c:if test="${cc.accettazione.animale.decedutoNonAnagrafe == true && cc.accettazione.randagio}">
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
	        		<c:out value="${cc.accettazione.animale.comuneRitrovamento.description }"/>		        		
	        	</td>
	        	<td>
	        	</td>
        	</tr>
        	
        	<tr class='odd'>
	        	<td>
	        		Provincia Ritrovamento
	        	</td>
	        	<td>
	        		<c:out value="${cc.accettazione.animale.provinciaRitrovamento }"/>	        		
	        	</td>
	        	<td>
	        	</td>
        	</tr>
        		   	
        	<tr class='even'>
	        	<td>
	        		Indirizzo Ritrovamento
	        	</td>
	        	<td>
	        		<c:out value="${cc.accettazione.animale.indirizzoRitrovamento }"/>        		
	        	</td>
	        	<td>
	        	</td>
        	</tr>	
        	
        	<tr class='odd'>
	        	<td>
	        		Note Ritrovamento
	        	</td>
	        	<td>
	        		<c:out value="${cc.accettazione.animale.noteRitrovamento }"/>		        		    		
	        	</td>
	        	<td>
	        	</td>
        	</tr>			   		 	   		 
        </c:if>
   	</table>
   	
<div id="edit_info_div" title="Dettagli informativi">
<form id="form" name="form" action="vam.cc.EditGenericInfo.us" method="post">	
	<input type="hidden" name="idCC" value="${cc.id}" />
	
	 <table class="tabella">		
		
		<tr>
        	<th colspan="3">
        		Dati generici dell'animale
        	</th>
        </tr>
    
    	<tr class='even'>
    		<td>
    			Peso dell'animale (in Kg)
    		</td>
    		<td>
    			 <input type="text" name="peso" maxlength="6" size="6" value="<c:out value="${cc.peso}"/>" onblur="isnum(this)"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Habitat 
    		</td>
    		<td>
    		
    			<c:forEach items="${listHabitat}" var="h" >									
							<input type="checkbox" name="oph_${h.id }" id="oph_${h.id }" 
								<c:forEach items="${cc.lookupHabitats}" var="ccH">
									<c:if test="${ccH.id == h.id }">
										<c:out value="checked=checked"></c:out>
									</c:if>
								</c:forEach>					
							/> ${h.description} 	
							<br>				
				</c:forEach>
				
				<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.cc.home(listhabitat)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>


    		
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Ferite d'arma da Fuoco 
    		</td>
    		<td>
    		
    			<c:forEach items="${listFerite}" var="f" >									
							<input type="checkbox" name="opf_${f.id }" id="opf_${f.id }" 
								<c:forEach items="${cc.lookupFerite}" var="ccF">
									<c:if test="${ccF.id == f.id }">
										<c:out value="checked=checked"></c:out>
									</c:if>
								</c:forEach>					
							/> ${f.description} 	
							<br>				
				</c:forEach>
				
				<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.cc.home(listFerite)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>
    		
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Alimentazione
    		</td>
    		<td>
    			<c:forEach items="${listAlimentazioni}" var="a" >									
							<input type="checkbox" name="opa_${a.id }" id="opa_${a.id }" 
							
								<c:forEach items="${cc.lookupAlimentazionis}" var="ccA">
									<c:if test="${ccA.id == a.id }">
										<c:out value="checked=checked"></c:out>
									</c:if>
								</c:forEach>
							
							
							/> ${a.description} 	
							<br>				
				</c:forEach> 
				
				<% 
memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.cc.home(listAlimentazioni)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>

    		</td>
    		<td>
    		</td>
        </tr>							
		     
      </table>
    </form>	
 </div>
   	
   	
   	
<div id="modifica_anagrafica_animale_div" title="Anagrafica Animale">
</div>
 
 
 
 <div id="chiusura_cc_div" title="Chiusura Cartella Necroscopica">
<form id="formChiusuraCc" name="formChiusuraCc" action="vam.cc.Chiusura.us" method="post">	
	<fmt:formatDate type="date" value="${cc.dataApertura}" pattern="dd/MM/yyyy" var="dataAperturaCc"/> 
    <input type="hidden" name="dataApertura" id="dataApertura" value="${dataAperturaCc}">
	 <table class="tabella">		
		<tr>
        	<th colspan="3">
        		Chiusura Cartella Necroscopica
        	</th>
        </tr>
    	<tr class='even'>
    		<td>
    			Data
    		</td>
    		<td>
	       		<input type="text" name="dataChiusuraCc" id="dataChiusuraCc" readonly="readonly"/> 
    		</td>
    		<td>
    		</td>
        </tr>
      </table>
    </form>	
 </div>
   	
   	
   	
   	<script type="text/javascript">	

   	$(function() 
			{
				$( "#edit_info_div" ).dialog({
					height: screen.height/1.5,
					modal: true,
					autoOpen: false,
					closeOnEscape: true,
					show: 'blind',
					resizable: true,
					draggable: true,
					width: screen.width/1.2,
					buttons: {
						"Annulla": function() {
							$( this ).dialog( "close" );
						},
						"Salva": function() {
							document.form.submit();					
						}
					}
			});
	});


	
	function editInfo(  )
	{		
		$( '#edit_info_div' ).dialog( 'open' );
	};
	
	
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
		$("#modifica_anagrafica_animale_div").load("jsp/anag_div.jsp?idA=<%=a.getId()%>&idS=<%=a.getLookupSpecie().getId()%>&idR=<%=idR%>&idT=<%=idT%>&sesso=<%=anagraficaAnimale.getSesso()%>&dec_no_anag=<%=a.getDecedutoNonAnagrafe()%>&idM=<%=idM%>&act=<%="vam.cc.ModificaAnagraficaAnimale.us"%>&redirectTo=<%="FindAnimale"%>&idAcc=-1");
		$( '#modifica_anagrafica_animale_div' ).dialog( 'open' );
		if(1 == idSpecie )
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
	
	$(function() 
			{
				$( "#chiusura_cc_div" ).dialog({
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
							if(document.getElementById("dataChiusuraCc").value=='')
							{
								alert("Inserire la data di chiusura della cartella necroscopica");
								document.getElementById("dataChiusuraCc").focus();
								return false;
							}
							if(confrontaDate(document.getElementById("dataChiusuraCc").value,document.getElementById("dataApertura").value)<0)
							{
								alert("La data di chiusura della cartella necroscopica non può essere antecedente alla data di apertura(" + document.getElementById("dataApertura").value + ")");	
								form.dataChiusura.focus();
								return false;
							}
							document.formChiusuraCc.submit();
												
						}
					}
			});
			$( "#dataChiusuraCc" ).datepicker(
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
	
	function chiusuraCc(dataAutopsia,dataSmaltimento)
	{	
		if(dataAutopsia!='' &&  dataSmaltimento == '')
		{
			if(myConfirm("Chiusura non possibile per mancanza dati smaltimento. Si vuole aprire la pagina di accettazione per inserirli?"))
			{
				location.href='vam.accettazione.Detail.us?id=<%=cc.getAccettazione().getId()%>';
				attendere();
			}
				
		}
		else 
		{
			$( '#chiusura_cc_div' ).dialog( 'open' );
		}
	};	
</script>

<%

memFreeEnd = Runtime.getRuntime().freeMemory()/(1024*1024);
		 dataEnd  	= new Date();
		 timeExecution = (dataEnd.getTime() - dataStart.getTime())/1000;
		System.out.println("Jsp in esecuzione: " + "vam.cc.home(fine)" + " - " + ((timeExecution >=5)?("(ATTENZIONE)"):("")) + "Execution time:" + timeExecution + "s - " + 
				   "Memoria usata: " + (memFreeStart - memFreeEnd) +
				   ", utente: " + ((utente==null)?("non loggato"):(utente.getUsername())));
		memFreeStart 	= Runtime.getRuntime().freeMemory()/(1024*1024);
		dataStart 		= new Date();
%>
	
	


	
 
   	
   	
   	   	
   	