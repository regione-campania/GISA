<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<link rel="stylesheet" href="jsp/vam/cc/print.css" type="text/css" media="print" />

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<%@page import="it.us.web.bean.vam.TerapiaDegenza"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="it.us.web.bean.vam.Diagnosi"%>
<%@page import="it.us.web.bean.vam.Trasferimento"%>
<%@page import="it.us.web.bean.vam.TipoIntervento"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/stampaCC/popupDetails.js"></script>

<style>
@media print {
div.header {
	display:none;
}
.stampaSezione {
display:none;
}
.boxIdDocumento {  
       border: 1px solid black;
       width: 60px;
       height: 20px;
       margin-top: 30px;
       text-align: center;
       padding-top: 10px;
       font-size: 8px; 
}
table.tabella
{
	-webkit-print-color-adjust:exact;
	background-color:#E5EAE7;
	font-size:16px;
	margin:0.5%;
	width:94%;	
	border-collapse: collapse;
	
}

th
{
	background:#A8C4DC;
	color:#000044;
	font-weight:bold;
	text-align:center;
	padding: 0px;
	border: none;	
}

th.sub
{
	background:#CCCCCC;
	color:#000044;
	font-weight:bold;
	text-align:center;
	padding: 0px;
	border: none;	
}


tr.odd {background-color: #FFFFFF}
tr.even {background-color: #E5EAE7;}


}


</style>

<%

ArrayList<TerapiaDegenza> tdList = (ArrayList<TerapiaDegenza>)request.getAttribute("tdList");
ArrayList<Diagnosi> diagnosi = (ArrayList<Diagnosi>) request.getAttribute("diagnosi");
ArrayList<Trasferimento> trasferimenti = (ArrayList<Trasferimento>) request.getAttribute("trasferimenti");
CartellaClinica cc = (CartellaClinica)session.getAttribute("cc");
%>


<div class="boxIdDocumento"></div>
<h4 class="titolopagina">
     Cartella Clinica
</h4> 
<br>


<div id="stampaSezione" class="stampaSezione">
<p class="intestazione">Scegli la sezione che vuoi stampare:</p>
<input type="checkbox" name="sezione" id="1" value="det" checked="checked" onClick="javascript:ctr('1');">Dettaglio Generale<br>

<!-- NASCONDERE -->
<% if (!cc.getCcMorto()) {%>
<input type="checkbox" name="sezione" id="2" value="diario" onClick="javascript:ctr(2,'divDiario');">Diario Clinico<br>
<input type="checkbox" name="sezione" id="3"  value="ricov" onClick="javascript:ctr(3,'divRic');">Ricovero<br>
<input type="checkbox" name="sezione" id="4"  value="diag" onClick="javascript:ctr(4,'divDiagn');">Diagnosi<br>
<%} else {%>
<input type="hidden" name="sezione" id="2" value="diario">
<input type="hidden" name="sezione" id="3"  value="ricov">
<input type="hidden" name="sezione" id="4"  value="diag" ">
<%} %>
<!-- NASCONDERE -->
<input type="checkbox" name="sezione" id="5"  value="dimismalt" onClick="javascript:ctr(5,'divDimis');">Dimissioni/Trasporto Spoglie<br>
<% if (!cc.getCcMorto()) {%>
<input type="checkbox" name="sezione" id="6" value="chir" onClick="javascript:ctr(6,'divChir');">Chirurgia<br>
<input type="checkbox" name="sezione" id="7" value="terap" onClick="javascript:ctr(7,'divTerap');">Terapie<br>
<%} else {%>
<input type="hidden" name="sezione" id="6" value="chir">
<input type="hidden" name="sezione" id="7" value="terap">
<%} %>
<!-- NASCONDERE -->
<input type="checkbox" name="sezione" id="8" value="trasf" onClick="javascript:ctr(8,'divTrasf');">Trasferimenti<br>
<input type="checkbox" name="sezione" id="9" value="lista" onClick="javascript:ctr(9,'divList');">Lista Esami<br>

<br>
<c:set var="tipo" scope="request" value="stampaCc" />
<c:import url="../../jsp/documentale/home.jsp"/>
<br>

</div>      
   
<div id="print_div" >

	<div id="divDet" style="display: block;">    
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
    			<c:out value="${cc.fascicoloSanitario.numero}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Numero di riferimento accettazione
    		</td>
    		<td>
	    		<c:out value="${cc.accettazione.progressivoFormattato}"/>    			
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			 Data di apertura
    		</td>
    		<td>
    			 <fmt:formatDate type="date" value="${cc.dataApertura}" pattern="dd/MM/yyyy" var="dataApertura"/>
    			 <c:out value="${dataApertura}"/>
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
    			 <fmt:formatDate type="date" value="${cc.dataChiusura}" pattern="dd/MM/yyyy" var="dataChiusura"/>
    			 <b><c:out value="${dataChiusura}"/></b>
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
        
        <c:choose> 
			<c:when test="${cc.ccMorto}"> 
				<tr class='odd'>
    				<td>
    					Tipologia di cartella clinica
    				</td>
    				<td>
    					Necroscopica
    				</td>
    				<td></td>
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
    						</c:when>    				
		    				<c:otherwise> 
    							Degenza 
    						</c:otherwise>    				
		    			</c:choose> 
    				</td><td></td>
		        </tr> 
			</c:otherwise>
		</c:choose>	   
		      
		      
        
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
    			Identificativo<br/>Tatuaggio/II MC
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.animale.identificativo}"/><br/><c:out value="${cc.accettazione.animale.tatuaggio}"/>&nbsp;
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
						<fmt:formatDate type="date" value="${cc.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" />
						${cc.accettazione.animale.eta} <c:if test="${dataNascita}">(${dataNascita})</c:if>
					</td>
				</c:when>
				<c:otherwise>
					<td>
						Data nascita 
					</td>
					<td>
						<fmt:formatDate type="date" value="${cc.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" />
  						${dataNascita}
					</td>
				</c:otherwise>
			</c:choose>  
    		<td>
    		</td> 		
        </tr>
        
        <c:set scope="request" var="anagraficaAnimale" value="${anagraficaAnimale}"/>
        <c:import url="../vam/anagraficaAnimale.jsp"/>
        
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
       	
       	<c:if test="${cc.accettazione.animale.clinicaChippatura!=null}">
			<tr class='odd'><td>Microchippato nella clinica </td><td>${cc.accettazione.animale.clinicaChippatura.nome}</td><td></td></tr>
		</c:if>
        
    	<c:set var="proprietarioCognome" value="${cc.accettazione.proprietarioCognome}"/>
        
        <c:choose>
        	<c:when test="${cc.accettazione.proprietarioTipo=='Importatore'}">
				<tr>
    				<th colspan="3">
    					ANAGRAFICA PROPRIETARIO IMPORTATORE
    				</th>
    			</tr>	
				<tr class='even'><td>Ragione Sociale:</td><td>${cc.accettazione.proprietarioNome}</td><td></td></tr>
				<tr class='odd'><td>Partita Iva:</td><td>${cc.accettazione.proprietarioCognome }</td><td></td></tr>
				<tr class='even'><td>Codice Fiscale Impresa:</td><td>${cc.accettazione.proprietarioCodiceFiscale }</td><td></td></tr>
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
    			Anagrafica proprietario <c:if test="${cc.accettazione.proprietarioTipo!=null && !cc.accettazione.proprietarioTipo.equals('null')}">${cc.accettazione.proprietarioTipo}</c:if>
    		</th>
    	</tr>			
		<c:if test="${cc.accettazione.proprietarioTipo!=null && cc.accettazione.proprietarioTipo=='Canile'}">
			<tr class='odd'><td>Ragione Sociale: </td><td><c:out value="${cc.accettazione.proprietarioNome}"/></td><td></td></tr>
		</c:if>
		<c:if test="${cc.accettazione.proprietarioTipo!=null && cc.accettazione.proprietarioTipo!='Canile' && cc.accettazione.proprietarioTipo!='colonia'}">
			<c:if test="${!cc.accettazione.randagio}">
					<tr class='odd'><td>Cognome:</td><td><c:out value="${cc.accettazione.proprietarioCognome}"/></td><td></td></tr>
			</c:if>
			<tr class='even'><td>Nome:</td><td><c:out value="${cc.accettazione.proprietarioNome}"/></td><td></td></tr>
		</c:if>
<%-- 		<c:if test="${!cc.accettazione.randagio}">	
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
        </tr>
--%>
        
        <c:if test="${!cc.accettazione.randagio && cc.accettazione.proprietarioTipo!='colonia'}">
        <tr class='even'>
    		<td>
    			Codice Fiscale
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.proprietarioCodiceFiscale}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Documento
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.proprietarioDocumento}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Indirizzo
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.proprietarioIndirizzo}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Comune
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.proprietarioComune}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Provincia
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.proprietarioProvincia}"/>
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
    <br/>
    </div>
        
       
   
   
   	<div id="divDimis" 
   		<c:choose>
   			<c:when test="${divDimis=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>
   	>	
   	<table class="tabella">
   	  	 <c:choose> 
			<c:when test="${cc.ccMorto}"> 
			<tr>
    			<th colspan="3">
    				Trasporto Spoglie
    			</th>
    		</tr>
    		
    		<c:if test="${cc.accettazione.animale.dataSmaltimentoCarogna == null}">
    			<tr>
    				<td>
    					Nessun dato inserito relativo alla registrazione di trasporto spoglie.
    				</td>
    			</tr>
    		</c:if>
    		<c:if test="${cc.accettazione.animale.dataSmaltimentoCarogna != null}">
    		
    			<tr class='even'>
	    			<td>
	    				 Data
	    			</td>
	    			<td> 
	    				${cc.accettazione.animale.dataSmaltimentoCarogna}"
	    			</td>    		
       	 		</tr>
        		<tr class='odd'>
			        <td>
			    		Ditta Autorizzata
		    		</td>
		    		<td>
		    			${cc.accettazione.animale.dittaAutorizzata}"
			        </td>
			     </tr> 
			     <tr class="even">  
			        <td>
			        	DDT
			        </td>
			        <td>
						 ${cc.accettazione.animale.ddt}"
			        </td>
		        </tr> 
		     </c:if>
	    	</c:when>
	    	<c:otherwise>	
		    	<tr>
    				<th colspan="3">
    					Dimissioni
    				</th>
    			</tr>
    			<c:if test="${cc.dataChiusura == null}">
    			<tr>
    				<td>
    					Nessun dato inserito relativo alle Dimissioni.
    				</td>
    			</tr>
    			</c:if>
    			<c:if test="${cc.dataChiusura != null}">
    			<tr>
    				<td>
    					Nessun dato inserito relativo alle Dimissioni.
    				</td>
    			</tr>
    			
    			<tr class='even'>
	    			<td>
	    				 Data Dimissione
	    			</td>
	    			<td> 
	    				<fmt:formatDate type="date" value="${cc.dataChiusura}" pattern="dd/MM/yyyy" var="dataChiusura"/>
	    				 <b><c:out value="${dataChiusura}"/></b>
	    			</td>    		
       	 		</tr>
        		<tr class='odd'>
			        <td>
			    		Destinazione dell'animale
		    		</td>
		    		<td>
						${cc.destinazioneAnimale.description}
			        </td>
			        <td>
			        </td>
		        </tr> 
		        </c:if>
			</c:otherwise>
		</c:choose>	   
        </table>
        <br/>
        </div>
        
   		
   		
   		<div id="divRic" 
   		<c:choose>
   			<c:when test="${divRic=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>
   	>
        <table class="tabella">
        <tr>
    		<th colspan="3">
    			Ricovero
    		</th>
    	</tr>
    	
    	<c:if test="${cc.ricoveroData == null}">
			<tr>
    			<td>Nessun Ricovero presente</td>
    		</tr>
		</c:if>
    		
    	<c:if test="${cc.ricoveroData != null}">
			<tr class='even'>
    		<td>
    			 Data del ricovero
    		</td>
    		<td>
    			<fmt:formatDate value="${cc.ricoveroData}" pattern="dd/MM/yyyy" var="dataRicovero"/>
	   			${dataRicovero}
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Ricoverato in 
    		</td>
    		<td>
    			${cc.strutturaClinica.denominazione}, Numero ${cc.ricoveroBox}	    			
    		</td>
        </tr>   
                
        <tr class='even'>
    		<td>
    			Motivo del ricovero
    		</td>
    		<td>
    			${cc.ricoveroMotivo}	    			
    		</td>
    		
        </tr>
        
         <tr class='odd'>
    		<td>
    			Sintomatologia
    		</td>
    		<td>
    			${cc.ricoveroSintomatologia}	    			
    		</td>
        </tr>
                
        <tr class='even'>
    		<td>
    			Note
    		</td>
    		<td>
    			${cc.ricoveroNote}  			
    		</td>
        </tr>
		</c:if>
        </table>
        <br/>
        </div>
        
   		
        
        <div id="divTerap" <c:choose>
   			<c:when test="${divTerap=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
        <table class="tabella">
        <tr>
    		<th colspan="4">
    			Terapia in Degenza
    		</th>
    	</tr>
    	<tr>
        	<th>
        		Data Apertura
        	</th>   
        	<th>
        		Data Chiusura
        	</th>      	
        	<th>
        		Tipologia
        	</th>        	
        	<th>
        		Effettuata da
        	</th>       	
        </tr> 
    	
    	<%if(tdList.size() == 0) { %>
    		<tr>
    			<td>Nessuna Terapia in Degenza</td>
    		</tr>
    	<% } else { %>
    	 
	    	<c:set var="i" value='1'/>		
	        <c:forEach items="${tdList}" var="td" >
	        	<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
	    			<td>
	    				<fmt:formatDate value="${td.data}" pattern="dd/MM/yyyy HH:mm" var="data"/>
	    			 		<c:out value="${data}"></c:out>	
	    			</td>
	    			<td>
	    				<fmt:formatDate value="${td.dataChiusura}" pattern="dd/MM/yyyy HH:mm" var="data"/>
	    			 		<b><c:out value="${data}"></c:out></b>		
	    			</td>
	    			<td>
	    				<c:out value="${td.tipo}"></c:out>		
	    			</td>
	    			<td>
	    				<c:out value="${td.enteredBy}"></c:out>		
	    			</td>
	    		</tr>
	    		<c:set var="i" value='${i+1}'/>
	        </c:forEach>    	              
	    <% } %>	
   	</table>
   	 	<br/>
   	</div>

  
   	
   	<div id="divDiagn" <c:choose>
   			<c:when test="${divDiagn=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   	<table class="tabella">
        <tr>
    		<th colspan="3">
    			Diagnosi
    		</th>
    	</tr>
    	<tr>
        	<th>
        		Data Diagnosi
        	</th>   
        	<th>
        		Diagnosi
        	</th>      	
        	<th>
        		Diagnosi Chirurgiche
        	</th>        	     	
        </tr> 
    	 <%if(diagnosi.size() == 0) { %>
    		<tr>
    			<td>Nessuna Diagnosi</td>
    		</tr>
    	<% } else { %>
    	 
    	 
    	<c:set var="i" value='1'/>	
        <c:forEach items="${diagnosi}" var="d" >
        	<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
    			<td>
    				<fmt:formatDate value="${d.dataDiagnosi}" pattern="dd/MM/yyyy" var="dataDiagnosi"/>
    			 		<c:out value="${dataDiagnosi}"></c:out>	
    			</td>
    			<td>
    				${d.diagnosiEffettuate}	
    			</td>
    			<td>
    				${d.diagnosiChirurgiche}	
    			</td>
    		</tr>
    		<c:set var="i" value='${i+1}'/>
        </c:forEach>  
         <% } %> 
   	</table>
   	<br/>
	</div>
	   
   	
   	
   	<div id="divChir" <c:choose>
   			<c:when test="${divChir=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		
   		<table class="tabella">
        <tr>
    		<th colspan="3">
    			Sterilizzazione
    		</th>
    	</tr>
    	
    	<tr class='odd'>
    	    	<c:choose>
    	<c:when test="${anagraficaAnimale.dataSterilizzazione!=null}">
	    		<td>
	    			Data
	    		</td>
	    		<td> 
	    			<fmt:formatDate type="date" value="${anagraficaAnimale.dataSterilizzazione}" pattern="dd/MM/yyyy" var="dataSterilizzazione" /> ${dataSterilizzazione}
	    		</td>
	    	</tr>
	    	<tr class='even'>
	    		<td>
	    			Operatore
	    		</td>
	    		<td> 
	    			${anagraficaAnimale.operatoreSterilizzazione}
	    		</td>
	    	</c:when>
	    	<c:otherwise>
	    		<td>Nessun intervento presente.</td>
	    		</c:otherwise>
	    		</c:choose>
	    	</tr>
    		</table>
        
   		
   		
   		
   	<table class="tabella">
        <tr>
    		<th colspan="2">
    			Chirurgia
    		</th>
    	</tr>
    	<tr>
        	<th>
        		Data Richiesta
        	</th>    	
        	<th>
        		Effettuata dal Dott.
        	</th>      	     	     	
        </tr> 
        
         <%if(cc.getTipoInterventi().size() == 0) { %>
    	 	<tr>
    	 		<td>Nessun intervento presente.</td>
    	 	</tr>
    	 <% } else { %>
    	
    	<c:set var="i" value='1'/>	
        <c:forEach items="${cc.tipoInterventi}" var="ti" >
        	<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
    			<td align="center">
    				<fmt:formatDate type="date" value="${ti.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiestaChirurgia"/>
    				${dataRichiestaChirurgia}
    			</td>
    			<td align="center">
    				${ti.enteredBy}
    			</td>
    		</tr>
    		<c:set var="i" value='${i+1}'/>
        </c:forEach>
        <% } %>  
   	</table>
   	
   	<br/>
   	</div>
   	
   	
   	
   	<div id="divTrasf" <c:choose>
   			<c:when test="${divTrasf=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   	<table class="tabella">
        <tr>
    		<th colspan="5">
    			Trasferimenti
    		</th>
    	</tr>
    	<tr>
        	<th>
        		Stato
        	</th>   
        	<th>
        		Data Richiesta
        	</th> 
        	<th>
        		Nota Richiesta
        	</th> 
        	<th>
        		Inserito Da
        	</th>      	
        	<th>
        		Motivazione/Operazioni Richieste
        	</th>    
        	<th>
        		Clinica di Origine
        	</th>
        	<th>
        		Data Approvazione CRIUV
        	</th> 
        	<th>
        		Data Rifiuto CRIUV
        	</th>  
        	<th>
        		Nota CRIUV
        	</th>      	     	
        	<th>
        		Clinica di Destinazione
        	</th>  
        	<th>
        		Data Accettazione Clinica Destinazione
        	</th>  
        	<th>
        		Nota Clinica di Destinazione
        	</th> 
        	<th>
        		Data Riconsegna/Dimissione
        	</th> 
        	<th>
        		Nota Riconsegna/Dimissione
        	</th> 
        </tr> 
    	 <%if(trasferimenti.size() == 0) { %>
    		<tr>
    			<td>Nessun Trasferimento</td>
    		</tr>
    	<% } else { %>
    	 
    	<c:set var="i" value='1'/>	
        <c:forEach items="${trasferimenti}" var="tr" >
        	<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
    			<td align="center">
    				${tr.stato}	
    			</td>
    			<td align="center">
    				${tr.dataRichiesta}	
    			</td>
    			<td align="center">
    				${tr.notaRichiesta}	
    			</td>
    			<td align="center">
    				${tr.enteredBy}	
    			</td>
    			<td align="center">
    				${tr.operazioniRichieste}	
    			</td>
    			<td align="center">
    				(${tr.clinicaOrigine.lookupAsl }) ${tr.clinicaOrigine.nome }	
    			</td>
    			<td align="center">
    				<c:if test="${tr.urgenza }"><font color="red">(Richiesta in urgenza)</font></c:if>
					<c:if test="${!tr.urgenza }"><fmt:formatDate value="${tr.dataAccettazioneCriuv }" pattern="dd/MM/yyyy" /></c:if>
				</td>	
				<td align="center">
					<fmt:formatDate value="${tr.dataRifiutoCriuv }" pattern="dd/MM/yyyy" />
				</td>
				<td align="center">
					${tr.notaCriuv}
				</td>			
    			<td align="center">
    				(${tr.clinicaDestinazione.lookupAsl }) ${tr.clinicaDestinazione.nome }
    			</td>
    			<td align="center">
    				<fmt:formatDate value="${tr.dataAccettazioneDestinatario }" pattern="dd/MM/yyyy" />
    			</td>
    			<td align="center">
    				${tr.notaDestinatario }
    			</td>
    			<td align="center">
    				<fmt:formatDate value="${tr.dataRiconsegna }" pattern="dd/MM/yyyy" />
    			</td>
    			<td align="center">
    				${tr.notaRiconsegna}
    			</td>
    		</tr>
    		<c:set var="i" value='${i+1}'/>
        </c:forEach>  
          <% } %>
   	</table>
   	<br/>
   	</div>
   	
   	
   	
   	<div id="divDiario" <c:choose>
   			<c:when test="${divDiario=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   	<table class="tabella">
        <tr>
    		<th colspan="5">
    			Diario Clinico
    		</th>
    	</tr>
    	<tr>
        	<th>
        		Apparato
        	</th>   
        	<th>
        		Data Esame
        	</th>      	
        	<th>
        		Temperatura
        	</th>    
        	<th>
        		Esito
        	</th>       	     	
        	<th>
        		Osservazioni
        	</th>   
        </tr> 
    	 
    	  <%if(cc.getDiarioClinico().size() == 0) { %>
    	 	<tr>
    	 		<td>Diario Clinico non presente.</td>
    	 	</tr>
    	 <% } else { %>
    	 
    	<c:set var="i" value='1'/>	
        <c:forEach items="${cc.diarioClinico}" var="dc" >
        	<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
    			<td align="center">
    				<c:if test="${dc.apparato == null}">EOG</c:if>
					<c:if test="${dc.apparato != null}">${dc.apparato.description }</c:if>
    			</td>
    			<td align="center">
    				<fmt:formatDate type="date" value="${dc.data}" pattern="dd/MM/yyyy" var="dataDiarioClinico"/>
    				${dataDiarioClinico}	
    			</td>
    			<td align="center">
    				${dc.temperatura}	
    			</td>
    			
    				<td><table style="width: 100%;">
							<c:forEach items="${dc.tipiEO }" var="teo">
								<tr style="vertical-align: top;">
									<td style="text-align: right; width: 40%;" class="<c:if test="${!teo.normale }" >errore</c:if><c:if test="${teo.normale }" >messaggio</c:if>">${teo.tipo.description }</td>
									<td>
										<c:if test="${empty teo.esiti}">Normale</c:if>
										<c:if test="${not empty teo.esiti}">
											<c:forEach items="${teo.esiti }" var="esito">
												${esito }<br/>
											</c:forEach>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</table>
    			</td>
    			<td align="center">
					<c:set var="newLine" value="\n" />
					${dc.osservazioniHtmlEncoded}<br/>
    			</td>
    		</tr>
    		<c:set var="i" value='${i+1}'/>
        </c:forEach>  
       	<% } %>				          
   	</table>
   	<br/>
   	</div>
   
   	
   	
   	<div id="divList" <c:choose>
   			<c:when test="${divList=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   	<table class="tabella">
        <tr>
    		<th colspan="4">
    			Lista Esami
    		</th>
    	</tr>
    	<tr>
        	<th>
        		Esame
        	</th>   
        	<th>
        		Data Richiesta
        	</th>      	
        	<th>
        		Data Esito
        	</th>    
        	<th>
        		Richiesto da
        	</th>       	     	
        </tr> 
    	 
    	 <%if(cc.getQuadroEsami().size() == 0) { %>
    	 	<tr>
    	 		<td>Lista Esami non presente.</td>
    	 	</tr>
    	 <% } else { %>
    	 
    	<c:set var="i" value='1'/>	
         <c:forEach items="${cc.quadroEsami}" var="qe" >
        	<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
    			<td align="center">
    				${qe.nomeEsame}
    			</td>
    			<td align="center">
    				<fmt:formatDate type="date" value="${qe.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiestaEsame"/> 
    				${dataRichiestaEsame}	
    			</td>
    			<td align="center">
    				<fmt:formatDate type="date" value="${qe.dataEsito}" pattern="dd/MM/yyyy" var="dataEsitoEsame"/>
    				${dataEsitoEsame}	
    			</td>
    			<td align="center">
    				${qe.enteredBy}
				</td>
    		</tr>
    		<c:set var="i" value='${i+1}'/>
        </c:forEach>  
        <% } %>				          
   	</table>
  	</div>
  	
  	</div> 