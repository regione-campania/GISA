<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.BUtenteAll"%>
<%@page import="it.us.web.bean.vam.lookup.LookupEsameObiettivoApparati"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="it.us.web.bean.vam.EsameObiettivoEsito"%>
<%@page import="it.us.web.bean.vam.EsameObiettivo"%>
<%@page import="it.us.web.bean.vam.lookup.LookupEsameObiettivoEsito"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="java.util.Date"%>
<link rel="stylesheet" documentale_url="" href="jsp/vam/cc/print.css" type="text/css" media="print" />

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<%@page import="it.us.web.bean.vam.TerapiaDegenza"%>
<%@page import="it.us.web.bean.vam.Diagnosi"%>
<%@page import="it.us.web.bean.vam.Trasferimento"%>
<%@page import="it.us.web.bean.vam.TipoIntervento"%>
<%@page import="it.us.web.bean.vam.Accettazione"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@page import="it.us.web.bean.vam.lookup.LookupPersonaleInterno"%>
<%@page import="it.us.web.bean.SuperUtente"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>


<%@page import="it.us.web.constants.IdOperazioniBdr"%>
<%@page import="it.us.web.constants.IdRichiesteVarie"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.InetAddress"%>
<%@page import="it.us.web.util.properties.Application"%>

<style>
@media screen {
.coloreIdentificativo {
	color:#ff0000;
}
.fondopagina {
	bottom:0;
}
}
@media print {
	table.fondo {
/*position: absolute;*/
	font-size: 12px;
/*	margin-top: 550px;*/
	width:100%;
}
div.header {
	display:none;
}
.stampaSezione {
display:none;
}
.coloreIdentificativo {
	color:#000000;
}
.boxIdDocumento {  
       border: 1px solid black;
       width: 60px;
       height: 20px;
       margin-top: 30px;
       text-align: center;
       padding-top: 5px;
       font-size: 8px; 
}
.boxOrigineDocumento {
	position: absolute;
	width: 160px;
	height: 20px;
	top: 15px;
	left: 70px;
	text-align: left;
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
#divTerap, #divChir {
page-break-before: always;
}


tr.odd {background-color: #FFFFFF}
tr.even {background-color: #E5EAE7;}

table.griglia  {
width:100%;
background-color:#000;
}
table.griglia td {
background-color:#FFF;

}

table.grigliaEsami  {
background-color:#000;
}
table.grigliaEsami td {
background-color:#FFF;

}

table {
line-height:1.8em;
}

.underline {
border-bottom:1px solid;
}
.esamiSangue tr td{
	font-size: 0.8em;
}
}

body{
	-webkit-print-color-adjust:exact;
    font-family: Trebuchet MS,Verdana,Helvetica,Arial,san-serif;	
}

p.intestazione{
	background: none repeat scroll 0 0 #A8C4DC;
    border: medium none;
    color: #000044;
    font-weight: bold;
    padding: 0;
    text-align: center;
    width: 50%;
    text-align:left;
    
}
.pagebreak, #divChir {
page-break-after:always;

}

#intestazione table td img {
	heigth:100px;
}
h2 {
	border:none !important;
	text-decoration: underline;
	display:inline;
}
table td h3 {
	display:inline
	}
table.griglia  {
width:100%;
background-color:#000;
}
table.griglia td {
background-color:#FFF;
text-align:center;
}

table.grigliaEsami  {
background-color:#000;
}
table.grigliaEsami td {
background-color:#FFF;
text-align:center;
}

table.bordo {
border:1px solid;
width:100%;
}
.esamiSangue {
	font-size: 10px;
	}
	table.fondo {

/*position: absolute;*/
	font-size: 12px;
	width:98%;
}

	
</style>

<%

ArrayList<TerapiaDegenza> tdList = (ArrayList<TerapiaDegenza>)request.getAttribute("tdList");
ArrayList<Diagnosi> diagnosi = (ArrayList<Diagnosi>) request.getAttribute("diagnosi");
ArrayList<Trasferimento> trasferimenti = (ArrayList<Trasferimento>) request.getAttribute("trasferimenti");
CartellaClinica cc = (CartellaClinica)request.getAttribute("cc");
%>

<div id="stampaSezione" class="stampaSezione">


<div id="print_div" >

	<div id="intestazioneCC" style="display: block;" >
		<table >
    	<tr>
    		<td>
    			<h3>IDENTIFICATIVO</h3>
    		</td>
    		<td style="border: 1px solid;padding:10px"><label class="coloreIdentificativo">
    			<c:out value="${cc.accettazione.animale.identificativo}"/></label>
    		</td>  
    		<td>
    		</td>  		
        </tr>
        <tr>
    		<td>
    			<h3>TATUAGGIO/II MC</h3>
    		</td>
    		<td style="border: 1px solid;padding:10px"><label class="coloreIdentificativo">
    			<c:out value="${cc.accettazione.animale.tatuaggio}"/></label>
    		</td>  
    		<td>
    		</td>  		
        </tr>
        
        </table>
	</div>

	<br/>
	<div id="divDet" style="display: block;">    
	<table style="width:100%">
  	
    	    	
    	<tr>
    		<td colspan="3">
    		</td>
    		<td>
    			<h3>C.C. N </h3>
    		</td>
    		<td>
    			<c:out value="${cc.numero}"/>
    		</td>
        </tr>
        
        <tr >
        	<td colspan="3">
        	</td>
 
    		<td>
				<h3>FASCICOLO SANITARIO</h3>
    		</td>
    		<td>
    		 	<c:out value="${cc.fascicoloSanitario.numero}"/>
    		 </td>
        </tr>
        
        <tr >
    		<td>
    			<H3>DATA APERTURA</H3>
    			<fmt:setLocale value="en_US" /> <!-- ALTRIMENTI LA STAMPA NON FUNZIONA -->
    			<fmt:formatDate type="date" value="${cc.dataApertura}" pattern="dd/MM/yyyy" var="dataApertura"/>
    		</td>
    		<td>
    			<c:out value="${dataApertura}"/>
    		</td>
    		<td></td> 
    		<td>
	    		<H3>NUMERO ACCETTAZIONE</H3>     			
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.progressivoFormattato}"/>
    		</td>
        </tr>

        
        <tr>
    		<td>
    			<h3>DATA CHIUSURA</h3>
    		</td>
    		<c:choose>
    			<c:when test="${cc.dataChiusura!=null}"></c:when>
				<c:otherwise>
	    			<c:set value="underline" var="classe" ></c:set>
	    		</c:otherwise>
    		</c:choose>
    		<td  class="${classe}" > 
    			<fmt:formatDate type="date" value="${cc.dataChiusura}" pattern="dd/MM/yyyy" var="dataChiusura"/>
    			<c:out value="${dataChiusura}"/>	
    		</td>
    		<td></td>
    		<td>
    			<H3>N REG. STERILIZZAZIONE</H3> 
    		</td>
    		<td class="underline">
    		</td>
        </tr>
        
        <tr>
    		<td colspan="3">
    		</td>
    		<td>
    			<H3>N REG. PRONTO SOCCORSO</H3> 
    		</td>
    		<td class="underline"></td>
        </tr>
        
        <tr><td></td><td></td><td></td>
    		<td>
    			<h3>INSERITA DA</h3> 
    		</td>
    		<td>
    			${cc.enteredBy}
    		</td>
    			 
        </tr>
         <tr>
    		<td colspan="3"></td>
    		<td>
    			<h3>TIPOLOGIA</h3> 
    		</td>
    		<td><c:choose> 
    			<c:when test="${cc.ccMorto}"> Necroscopica</c:when>
    			<c:when test="${cc.dayHospital == true}"> Day Hospital</c:when>
    			<c:when test="${!cc.ccMorto &&  cc.dayHospital == false}">Degenza</c:when>
    			</c:choose>
    		</td>
    			 
        </tr>
		</TABLE>
		
		<br/>
		
        <h2>SEZIONE ANAGRAFICA</h2>
        <br/><br/>
    	<table class="bordo" style="collapse;page-break-inside: avoid">
    	
    	<tr>
    		<td>
    			<h3>TIPOLOGIA</h3>
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.animale.lookupSpecie.description}"/>
    		</td> 
    		
    		<td>
    			<h3>STATO</h3>
    		</td>
    		<td>
    			<c:out value="${anagraficaAnimale.statoAttuale}"/>
    		</td>  		
        </tr>
        
    	<tr>
    		<td>
    		<c:if test="${cc.accettazione.animale.lookupSpecie.id!=3}">
    			<h3>RAZZA</h3> 
    			</c:if>
    		<c:if test="${cc.accettazione.animale.lookupSpecie.id==3}">
    			<h3>FAMIGLIA/GENERE</h3> 
    			</c:if>
    			
    			</td><td><c:if test="${(cc.accettazione.animale.lookupSpecie.id == specie.cane or cc.accettazione.animale.lookupSpecie.id == specie.gatto) && cc.accettazione.animale.decedutoNonAnagrafe==false}">${anagraficaAnimale.razza}</c:if>
				<c:if test="${cc.accettazione.animale.decedutoNonAnagrafe==true}">${anagraficaAnimale.razza}</c:if>
				<c:if test="${cc.accettazione.animale.lookupSpecie.id == specie.sinantropo && cc.accettazione.animale.decedutoNonAnagrafe==false}">${cc.accettazione.animale.specieSinantropoString} - ${cc.accettazione.animale.razzaSinantropo}</c:if>
    		   </td> 
    		<td>
    			<h3>SESSO</h3> </td><td><c:if test="${(cc.accettazione.animale.lookupSpecie.id == specie.cane or cc.accettazione.animale.lookupSpecie.id == specie.gatto) && cc.accettazione.animale.decedutoNonAnagrafe==false}">${cc.accettazione.animale.sesso}</c:if>
				<c:if test="${cc.accettazione.animale.decedutoNonAnagrafe==true}">${cc.accettazione.animale.sesso}</c:if>
				<c:if test="${cc.accettazione.animale.lookupSpecie.id == specie.sinantropo && cc.accettazione.animale.decedutoNonAnagrafe==false}">${cc.accettazione.animale.sesso}</c:if> 
    		</td>		
        </tr>
        
        <tr>
    		<c:choose>
				<c:when test="${cc.accettazione.animale.lookupSpecie.id==3}">
					<td>
						<h3>ETA'</h3>
					</td>
					<td>
						<fmt:formatDate type="date" value="${cc.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
						${cc.accettazione.animale.eta} <c:if test="${dataNascita}">(${dataNascita})</c:if>
					</td>
				</c:when>
				<c:otherwise>
					<td>
						<h3>DATA NASCITA</h3> 
					</td>
					<td>
						<fmt:formatDate type="date" value="${cc.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
  						${dataNascita}
					</td>
				</c:otherwise>
			</c:choose>  
			
			<c:set scope="request" var="anagraficaAnimale" value="${anagraficaAnimale}"/>
        	<c:import url="../vam/cc/anagraficaAnimaleStampa.jsp"/>
        </tr>    	
        
        	<c:if test="${cc.accettazione.animale.clinicaChippatura!=null}">
			<tr><td><h3>MICROCHIPPATO NELLA CLINICA</h3> </td><td>${cc.accettazione.animale.clinicaChippatura.nome}</td><td></td></tr>
		</c:if>
        
        	 <c:if test="${cc.accettazione.animale.dataMorte!=null || res.dataEvento!=null}">
        <tr>
   			<td colspan="1">
   				<h3>DECEDUTO IL</h3>
   			</td>
   			<td class="underline">
			       <c:choose>
			        	<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe == true}">						
							<fmt:formatDate type="date" value="${cc.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="dataMorte"/>
						</c:when>
						<c:otherwise>
							<fmt:formatDate type="date" value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/>
						</c:otherwise>	
					</c:choose>	 
						${dataMorte}			
						<c:choose>
							<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe == true}">						
								${cc.accettazione.animale.dataMorteCertezza}
							</c:when>
							<c:otherwise>
								${res.dataMorteCertezza}
							</c:otherwise>	
						</c:choose>
						</td>	
						
					      	  <td>
					    			<H3>PROBABILE CAUSA DEL DECESSO</H3>    
					   				<c:choose>
					    				<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
					    					${cc.accettazione.animale.causaMorte.description}
					    				</c:when>
					    				<c:otherwise>
					    					${res.decessoValue}
					    				</c:otherwise>
					    			</c:choose>	        	        
					        	</td>
					       	</tr>
				</c:if>
				<tr>
    		<td>
    			<h3>RICOVERATO IN:</h3></td>
    			<td> ${cc.strutturaClinica.denominazione}</td>
    	</tr>
    	<tr>
 		   	<td>	
    			<h3>BOX/GABBIA N.</h3>
    		</td>
    		<c:choose>
    			<c:when test="${cc.ricoveroBox}">
    				<c:set  var="classe" value=""></c:set>
    			<c:otherwise>
    			<c:set var="classe" value="underline"></c:set>
    			</c:otherwise>
    			
    			</c:when>
    		</c:choose>
    		<td> 
    		${cc.ricoveroBox}
    		</td>
    		 	</tr>
    	
        </table>
        <br/>
        
        
            
        <!--  ANAGRAFICA PROPRIETARIO/COLONIA -->
        	<div style="page-break-before:always"> &nbsp;</div>
    	<c:set var="proprietarioCognome" value="${cc.accettazione.proprietarioCognome}"/>
        
        <c:choose>
        	<c:when test="${cc.accettazione.proprietarioTipo=='Importatore' || cc.accettazione.proprietarioTipo=='Operatore Commerciale'}">
        	
        	<h2>ANAGRAFICA PROPRIETARIO ${cc.accettazione.proprietarioTipo.toUpperCase()}</h2><br/><br/>
    	
        	<table class="bordo" style="collapse;page-break-inside: avoid">
    			<tr><td><h3>RAGIONE SOCIALE:</h3></td><td>${cc.accettazione.proprietarioNome}</td><td></td></tr>
				<tr><td><h3>PARTITA IVA:</h3></td><td>${cc.accettazione.proprietarioCognome }</td><td></td></tr>
				<tr><td><h3>RAPPR. LEGALE:</h3></td><td>${cc.accettazione.proprietarioCodiceFiscale }</td><td></td></tr>
				<tr><td><h3>TELEFONO STRUTTURA(principale):</h3></td><td>${cc.accettazione.proprietarioTelefono }</td><td></td></tr>
				<tr><td><h3>TELEFONO STRUTTURA(secondario):</h3></td><td>${cc.accettazione.proprietarioDocumento }</td><td></td></tr>
				<tr><td><h3>INDIRIZZO SEDE OPERATIVA:</h3></td><td>${cc.accettazione.proprietarioIndirizzo }</td><td></td></tr>
				<tr><td><h3>CAP SEDE OPERATIVA:</h3></td><td>${cc.accettazione.proprietarioCap }</td><td></td></tr>
				<tr><td><h3>COMUNE SEDE OPERATIVA:</h3></td><td>${cc.accettazione.proprietarioComune }</td><td></td></tr>
				<tr><td><h3>PROVINCIA SEDE OPERATIVA:</h3></td><td>${cc.accettazione.proprietarioProvincia }</td><td></td></tr>
				</table>
			</c:when>
			<c:when test="${animale.lookupSpecie.id == specie.sinantropo || !fn:startsWith(proprietarioCognome, '<b>')}">
			<h2>ANAGRAFICA PROPRIETARIO <c:if test="${cc.accettazione.proprietarioTipo}">${cc.accettazione.proprietarioTipo}</c:if></h2>
			<br><br>
    	<table class="bordo" style="collapse;page-break-inside: avoid">
  			<c:if test="${cc.accettazione.proprietarioTipo=='Canile' }">
					<tr><td><h3>RAGIONE SOCIALE:</h3></td><td>${cc.accettazione.proprietarioNome}</td><td></td></tr>
					<tr><td><h3>PARTITA IVA:</h3></td><td>${cc.accettazione.proprietarioCognome }</td><td></td></tr>
					<tr><td><h3>RAPPR. LEGALE:</h3></td><td>${cc.accettazione.proprietarioCodiceFiscale }</td><td></td></tr>	
			</c:if>
			<c:if test="${cc.accettazione.proprietarioTipo!='Canile' }">
				
		<c:if test="${!cc.accettazione.randagio}">	
        	<tr>
    			<td>
    				<h3>COGNOME</h3>
    			</td>
    			<td>
    				<c:out value="${cc.accettazione.proprietarioCognome}"/>
    			</td>    		
        	</tr>
        </c:if>
        
        <tr>
    		<td>
    			<h3>NOME</h3>
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.proprietarioNome}"/>
    		</td>    		
        </tr>
        </c:if> 
        
        <c:if test="${!cc.accettazione.randagio}">
        <c:if test="${cc.accettazione.proprietarioTipo!='Canile'}">
        <tr>
    		<td>
    			<h3>CODICE FISCALE</h3>
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.proprietarioCodiceFiscale}"/>
    		</td>
        </tr>
        </c:if>
        
        <tr>
    		<td>
    			<h3>DOCUMENTO</h3>
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.proprietarioDocumento}"/>
    		</td>
        </tr>
        
        <tr>
    		<td>
    			<h3>INDIRIZZO</h3>
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.proprietarioIndirizzo}"/>
    		</td>
        </tr>
        
        <tr>
    		<td>
    			<h3>COMUNE</h3>
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.proprietarioComune}"/>
    		</td>
        </tr>
        
        <tr>
    		<td>
    			<h3>PROVINCIA</h3>
    		</td>
    		<td>
    			<c:out value="${cc.accettazione.proprietarioProvincia}"/>
    		</td>
        </tr>
        </c:if>  
        
        </table>
			</c:when>
			<c:otherwise>
				<h2>ANAGRAFICA COLONIA</h2><br/><br/>
    			
    	    	<table class="bordo" style="collapse;page-break-inside: avoid">
    	
			    	<tr>
			    		<td>
			    			<h3>COLONIA</h3>
			    		</td>
			    		<td>
			    			${cc.accettazione.nomeColonia}
			    		</td>
			        </tr>
			        <tr>
			    		<td>
			    			<h3>INDIRIZZO</h3>
			    		</td>
			    		<td>
			    			${cc.accettazione.proprietarioIndirizzo}, ${cc.accettazione.proprietarioComune}
														<c:if test="${cc.accettazione.proprietarioProvincia!=''}">
															(${cc.accettazione.proprietarioProvincia})						
														</c:if>
														<c:if test="${cc.accettazione.proprietarioCap!=''}">
															- ${cc.accettazione.proprietarioCap}						
														</c:if>
			    		</td>
			        </tr>
			        <tr >
			    		<td>
			    			<h3>NOMINATIVO REFERENTE</h3>
			    		</td>
			    		<td>
			    			${cc.accettazione.proprietarioNome}
			    		</td>
			        </tr>
			        <tr >
			    		<td>
			    			<h3>CODICE FISCALE REFERENTE</h3>
			    		</td>
			    		<td>
			    			${cc.accettazione.proprietarioCodiceFiscale}
			    		</td>
			        </tr>
			        <tr >
			    		<td>
			    			<h3>DOCUMENTO REFERENTE</h3>
			    		</td>
			    		<td>
			    			${cc.accettazione.proprietarioDocumento}
			    		</td>
			        </tr>
			        <tr>
			    		<td>
			    			<h3>TELEFONO REFERENTE</h3>
			    		</td>
			    		<td>
			    			${cc.accettazione.proprietarioTelefono}
			    		</td>
			        </tr>
		        </table>
			</c:otherwise>
		</c:choose>
	     
	   
	    <h2>DATI ACCETTAZIONE</h2>
	    <br/><br/>
	      <table class="bordo" style="collapse;page-break-inside: avoid">
        <tr>
        <TD>
        		<h3>DATA</h3>
        	</TD>    	<td>   
        		<fmt:formatDate type="date" value="${cc.accettazione.data}" pattern="dd/MM/yyyy"/>    
          	</td>
          	  <TD>
        		<h3>INSERITA DA</h3>
        	</TD>    
        	<td> 
        	 	${cc.accettazione.enteredBy.nome} ${cc.accettazione.enteredBy.cognome}  
        	</td>	
          	                	
          	</tr>
          	<tr>
          	 <TD>
        		<h3>RICHIEDENTE</h3>
        	</TD>    
        	
        	<%	Accettazione accettazione = cc.getAccettazione(); %>
        	<td colspan="2"> 
        	<b><%=accettazione.getLookupTipiRichiedente().getDescription()%>:</b>
			<c:choose>
				<c:when test="<%=accettazione.getRichiedenteTipoProprietario()!=null && accettazione.getRichiedenteTipoProprietario().equals(\"Importatore\") %>">
					Importatore ragione sociale: <%=accettazione.getRichiedenteNome()%>
				</c:when>
		
				<c:when test="<%=accettazione.getRichiedenteTipoProprietario()!=null && !accettazione.getRichiedenteTipoProprietario().equals(\"Importatore\") %>"> 
<%-- 				<%if(accettazione.getRichiedenteAsl()!=null && accettazione.getRichiedenteAsl().getDescription()!=null){%> <%=accettazione.getRichiedenteAsl().getDescription()%> <%}%> 
					<%if(accettazione.getRichiedenteAssociazione()!=null && accettazione.getRichiedenteAssociazione().getDescription()!=null){%> <%=accettazione.getRichiedenteAssociazione().getDescription()%> <%}%> --%>
					<c:if test="<%=accettazione.getRichiedenteCognome()!=null && accettazione.getRichiedenteCognome().startsWith(\"<b>\") %>">
						<%=accettazione.getRichiedenteCognome()%> 
					</c:if>
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
					<c:if test="<%=accettazione.getRichiedenteTelefono()!=null && !accettazione.getRichiedenteTelefono().equals(\"\")%>"><%=accettazione.getRichiedenteTelefono()%></c:if>
					<c:if test="<%=accettazione.getRichiedenteResidenza()!=null && !accettazione.getRichiedenteResidenza().equals(\"\") %>"><%=accettazione.getRichiedenteResidenza()%>
					</c:if>
					<c:if test="<%=accettazione.getPersonaleAsl()!=null && accettazione.getPersonaleAsl().size()>0%>">
							<% 	Iterator i = accettazione.getPersonaleAsl().iterator();
								SuperUtente su;
								while (i.hasNext()){
									su = (SuperUtente)i.next(); %>
									<%=su.toString()%>,	
							<%  } %>
					</c:if>	
					<c:if test="<%=accettazione.getRichiedenteAssociazione()!=null%>"><%=accettazione.getRichiedenteAssociazione().getDescription()%></c:if>
					<c:if test="<%=accettazione.getRichiedenteForzaPubblicaComune()!=null%>"><%=accettazione.getRichiedenteForzaPubblicaComune()%>	</c:if>
					<c:if test="<%=accettazione.getRichiedenteForzaPubblicaProvincia()!=null%>"><%=accettazione.getRichiedenteForzaPubblicaProvincia()%></c:if>
					<c:if test="<%=accettazione.getRichiedenteForzaPubblicaComando()!=null%>"><%=accettazione.getRichiedenteForzaPubblicaComando()%></c:if>
					<c:if test="<%=accettazione.getRichiedenteAltro()!=null && !accettazione.getRichiedenteAltro().equals(\"\")%>"><%=accettazione.getRichiedenteAltro()%></c:if>
					</td>	</tr>
					
					<c:if test="<%=accettazione.getPersonaleInterno()!=null && accettazione.getPersonaleInterno().size()>0%>">
						<tr><td><h3>PERSONALE INTERNO</h3></td><td>
						<% 	Iterator i = accettazione.getPersonaleInterno().iterator();
							LookupPersonaleInterno lpi;
							while (i.hasNext()){
								lpi = (LookupPersonaleInterno)i.next(); %>
								<%=lpi.getNominativo()%>,	
						<%  } %>
						</td></tr>
					</c:if>
				</c:when>
			</c:choose>
          	<tr>
        	<td>
        		<h3>MOTIVAZ./OPERAZIONI RICHIESTE</h3>
        	</td>
        	<td>
        	 <% IdOperazioniBdr idOpsBdr = (IdOperazioniBdr)request.getAttribute("idOpsBdr");
        		IdRichiesteVarie idRichiesteVarie = (IdRichiesteVarie)request.getAttribute("idRichiesteVarie");
        		
        	 Boolean scriviVirgola=false;
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
					<c:if test="<%=loa.getId()==idOpsBdr.getTrasferimento() && accettazione.getTipoTrasferimento()!=null && !accettazione.getTipoTrasferimento().equals(\"\") %>">
						(<%=accettazione.getTipoTrasferimento()%>)
					</c:if>
					<c:if test="<%=loa.getId()==idOpsBdr.getAdozione() && accettazione.getAdozioneFuoriAsl()!=null && accettazione.getAdozioneFuoriAsl()%>">
						fuori Asl
					</c:if>
					<c:if test="<%=loa.getId()==idOpsBdr.getAdozione() && accettazione.getAdozioneVersoAssocCanili() && accettazione.getAdozioneVersoAssocCanili()%>">
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
       
<c:if test="${anagraficaAnimale.dataSterilizzazione!=null}">
        <tr >
    		<td>
    			<h3>STERILIZZATO IL</h3>
    		</td>
    		<td>   
    		<fmt:formatDate type="date" value="${anagraficaAnimale.dataSterilizzazione}" pattern="dd/MM/yyyy"/>        	
        	</td>
          </tr>
        	</c:if>
        	
   				<!-- td colspan="3">
					<h3>TRASFERITO C/O IL</h3>
				</td>
				<td class="underline">
				</td-->
   		</tr>
    	 
		
		<!--  DATI RITROVAMENTO  -->
 		<c:if test="${cc.accettazione.animale.decedutoNonAnagrafe == true && cc.accettazione.randagio}">
	               	
        	<tr>
	        	<td>
	        		<h3>COMUNE RITROVAMENTO</h3>
	        	</td>
	        	<td>
	        		<c:out value="${cc.accettazione.animale.comuneRitrovamento.description }"/>		        		
	        	</td>
	        	<td>
	        	</td>
        	</tr>
        	
        	<tr>
	        	<td>
	        		<h3>PROVINCIA RITROVAMENTO</h3>
	        	</td>
	        	<td>
	        		<c:out value="${cc.accettazione.animale.provinciaRitrovamento }"/>	        		
	        	</td>
	        	<td>
	        	</td>
        	</tr>
        		   	
        	<tr>
	        	<td>
	        	<h3>	INDIRIZZO RITROVAMENTO</h3>
	        	</td>
	        	<td>
	        		<c:out value="${cc.accettazione.animale.indirizzoRitrovamento }"/>        		
	        	</td>
	        	<td>
	        	</td>
        	</tr>	
        	
        	<tr>
	        	<td>
	        	<h3>	NOTE RITROVAMENTO</h3>
	        	</td>
	        	<td>
	        		<c:out value="${cc.accettazione.animale.noteRitrovamento }"/>		        		    		
	        	</td>
	        	<td>
	        	</td>
        	</tr>			   		 	   		 
        </c:if>
         <!--  SE SI TRATTA DI UN'ATTIVITA' ESTERNA -->
       <c:if test="${not empty cc.accettazione.attivitaEsterna }">
       	 <tr>
        	<td>
        		<h3>LUOGO DI CATTURA</h3> 
        	</td>
        	<td class="underline"> 
        	${cc.accettazione.indirizzoAttivitaEsterna}, ${cc.accettazione.comuneAttivitaEsterna.description}	
             	</td>
        	
        </tr> 
        </c:if>  
   	</table>
    <br/>
    </div>
    
    
    <!-- ANAMNESI -->
    	<div id="divAnamnesi"  <c:choose>
   			<c:when test="${divAnamnesi=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		  
   		<h2>ANAMNESI</h2>
   		<br/><br/>
   		<table class="bordo" style="collapse;page-break-inside: avoid">
    	<tr>
    		<td>
    			Tipologia di anamnesi
    		</td>
    		<td>
    			<c:choose>
    				<c:when test="${cc.anamnesiRecenteConosciuta == false}">
    					Muta - 
    				</c:when>
    				<c:otherwise>
    					Conosciuta - 
    				</c:otherwise>
    			</c:choose>
    			${cc.anamnesiRecenteDescrizione}
    		</td>
        </tr> 
   		</table>
   		<br/>
   		</div>
   	
   	
   	<!-- ESAME OBIETTIVO GENERALE -->
    	<div id="divEsameObiettivoGenerale"  <c:choose>
   			<c:when test="${divEsameObiettivoGenerale=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		  
   		<h2>ESAME OBIETTIVO GENERALE</h2>
   		<br/><br/>
   		<table class="bordo" style="collapse;page-break-inside: avoid">
			<tr>
    			<td>
    				 Data
    			</td>
    			<td colspan="2">
    				<fmt:formatDate value="${dataEsame}" pattern="dd/MM/yyyy" var="data"/>    		
    				<c:out value="${data}"></c:out>
    			</td>
        	</tr> 
		        <tr>
		        	<td colspan="3">
		        		&nbsp;
		        	</td>
		        </tr>      
				
				<tr>
					<th>
			       		Tipo
			       	</th>
			       	<th>
			       		Esito
			       	</th>
			       	<th>
			       		Anormalità
			       	</th> 	       	     
				</tr>		
			
			
				<c:set var="i" value='1'/>	
				
<%
					ArrayList<String> listDescrizioni = (ArrayList<String>)  request.getAttribute("descrizioniEOTipo"); 
					int countDescrizioni = 0;
					Iterator<ArrayList<LookupEsameObiettivoEsito>> superList = ((ArrayList<ArrayList<LookupEsameObiettivoEsito>>)request.getAttribute("superList")).iterator(); 
					Date data_mod=null, data_ent=null;
					BUtenteAll mod_by=null, ent_by=null;	
					
					while(superList.hasNext())
					{
						Iterator<EsameObiettivo> eoList = cc.getEsameObiettivos().iterator();
						Iterator<LookupEsameObiettivoEsito> esiti = ((ArrayList<LookupEsameObiettivoEsito>)superList.next()).iterator();	
						String thereIsDescription = "NO";
						String esitoAnormale = "NO";
						String alreadyChecked = "NO";
			    	
						while(eoList.hasNext()) 
						{
							EsameObiettivo eo = eoList.next();
							ent_by = eo.getEnteredBy();
							mod_by = eo.getModifiedBy();
							data_ent = eo.getEntered();
							data_mod = eo.getModified();
							
							if (eo.getLookupEsameObiettivoTipo().getDescription().equals(listDescrizioni.get(countDescrizioni) )) 
							{
							   if (eo.getNormale() == true)  
								   thereIsDescription = "YES";
								else if (eo.getNormale() == false) 
								{ 
									esitoAnormale = "SI";
									thereIsDescription = "YES";
								}
							}
						}
						
						if(thereIsDescription.equals("YES"))
						{
		%>
							<tr>
								<td style="width:40%">				
									<%= listDescrizioni.get(countDescrizioni) %>     
								</td>
								<input type="hidden" name="descrizione_${i}" value="<%=listDescrizioni.get(countDescrizioni)%>"/>
		<%
								if(thereIsDescription.equals("YES") && esitoAnormale.equals("NO"))
								{
		%>
								 	<td style="width:20%">
										Normale
									</td>
		<%
								}
								if(thereIsDescription.equals("YES") && esitoAnormale.equals("SI"))
								{
		%>
								 	<td style="width:20%">
										Anormale
									</td>
		<%
								}
								if(thereIsDescription.equals("NO"))
								{
		%>
									<td style="width:20%">
										Non esaminato
									</td>	
		<%
								}
		%>						
								
								<td style="width:40%">
									<div id="_${i }"   
		<%
									if(esitoAnormale.equals("NO"))
									{
		%>
				  		          		style="display:none;"
		<%
									}
		%>
				    				>
		<%
				  		          	while(esiti.hasNext())
				  		          	{
				  		          		Iterator<LookupEsameObiettivoEsito> figli = ((ArrayList<LookupEsameObiettivoEsito>)request.getAttribute("listEsameObiettivoFigliList")).iterator();
				  		          		Iterator<EsameObiettivoEsito> esitiSelezionati = null;
				  		          		if(request.getAttribute("superListChecked")!=null)
				  		          		{
				  		          			esitiSelezionati = ((ArrayList<EsameObiettivoEsito>)request.getAttribute("superListChecked")).iterator();
				  		          		}
				  		          		LookupEsameObiettivoEsito temp = esiti.next();	
										String superesitoDaStampato = "NO";
										String isIn = "NO";
										
										while(figli.hasNext())
										{
											LookupEsameObiettivoEsito figlio = figli.next();
											if(figlio.getLookupEsameObiettivoEsito().getId()==temp.getId())
											{
												isIn = "YES";
												break;
											}
										}
										
										if(isIn.equals("NO"))
										{
											alreadyChecked = "NO";	
											if(esitiSelezionati!=null)
											{
												while(esitiSelezionati.hasNext())
												{
													EsameObiettivoEsito esito = esitiSelezionati.next();
													if(esito.getLookupEsameObiettivoEsito().getId()==temp.getId())
													{
														alreadyChecked = "YES";
														break;
													}
												}
											}
											
											if(alreadyChecked.equals("YES"))
											{
												out.println(temp.getDescription() + "<br/>");
												alreadyChecked = "NO";
											}
										}
										else
										{
											figli = ((ArrayList<LookupEsameObiettivoEsito>)request.getAttribute("listEsameObiettivoFigliList")).iterator();
												
											while(figli.hasNext())
											{
												LookupEsameObiettivoEsito figlio = figli.next();
															
											    if(figlio.getLookupEsameObiettivoEsito().getId() == temp.getId())
											    {
													isIn = "YES";
											    	alreadyChecked = "NO";
													
											    	if(request.getAttribute("superListChecked")!=null)
											    	{
											    		esitiSelezionati = ((ArrayList<EsameObiettivoEsito>)request.getAttribute("superListChecked")).iterator();
											    	}	
											    	if(esitiSelezionati !=null)
												    {
														while(esitiSelezionati.hasNext())
														{
															EsameObiettivoEsito esito = esitiSelezionati.next();
															    
															if(esito.getLookupEsameObiettivoEsito().getId() == figlio.getId())
														    {
														    	alreadyChecked = "YES";
														    	break;
														    }
														}
												    }
													if(alreadyChecked.equals("YES") && superesitoDaStampato.equals("NO"))
													{
														superesitoDaStampato = "YES";
		%>
														<label><b><i><%=temp.getDescription() %></i></b></label><br/>&nbsp;&nbsp;&nbsp;&nbsp;<%=figlio.getDescription() %>	
														<br/>												
		<%
														alreadyChecked = "NO";
												    }
															
													if(alreadyChecked.equals("YES") && superesitoDaStampato.equals("YES"))
													{
		%>
														&nbsp;&nbsp;&nbsp;&nbsp;<%=figlio.getDescription() %>	
														<br/>												
		<%
														alreadyChecked = "NO";
													}
												}
											}
										}
				  		          	}
		%>							
								</div>
							</td>			
						</tr>
						
						<c:set var="i" value='${i+1}'/>	   
					<% 
					
						}
					
					countDescrizioni++; 
					}
					%>	
			    
			    
			    
			    
			    <tr>
			    
			    
			    
			   
			    <!-- Gestione del caso particolare per la Febbre, che fa parte dell'esame
			    obiettivo generale, e perciò apparato=0!!! -->
			    <!-- Si va a controllare se il cane, in quella cartella clinica, e quindi
				per quell'esame obiettivo ha già avuto un valore di febbre.
				Se non lo ha ancora avuto, allora viene presentato il radio button "base"
				con il valore di default "Non esaminato", altrimenti viene mostrato
				il precedente settaggio-->
				<!--  Se già l'ha avuta, si mostra il valore ultimo salvato -->
			     <c:if test="${idApparato == 0 && isFebbre==true}">	
			     <tr>
				     <td>	    
				     Temperatura 
				     </td>						
					
					<c:forEach items="${cc.febbres}" var="febbre" >	
					<c:choose>
						<c:when test="${febbre.normale == true}">
							<td>
								Normale
							</td>	
						</c:when>	
						
						<c:when test="${febbre.normale == false}">
							<td>
								Anormale
							</td>	
						</c:when>									
					</c:choose>					
					<td>
						${febbre.temperatura}
					</td>
					</c:forEach>
				</tr>
				</c:if>	
	    
	  		</tr>     		
   		</table>
   		<br/>
   		</div>
   	
   	<!-- ESAME OBIETTIVO PARTICOLARE -->
    	<div id="divEsameObiettivoParticolare"  <c:choose>
   			<c:when test="${divEsameObiettivoParticolare=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		  
   		<h2>ESAME OBIETTIVO PARTICOLARE</h2>
   		<br/><br/>
   		
<%
		Iterator<LookupEsameObiettivoApparati> apparati = (Iterator<LookupEsameObiettivoApparati>)request.getAttribute("apparati");
		while(apparati.hasNext())
		{
			int idApparato = apparati.next().getId();
			if(request.getAttribute("apparato"+idApparato)!=null)
			{
			listDescrizioni = (ArrayList<String>)  request.getAttribute("descrizioniEOTipo"+idApparato);
			Iterator<ArrayList<LookupEsameObiettivoEsito>> superListApparato = ((ArrayList<ArrayList<LookupEsameObiettivoEsito>>)request.getAttribute("superList"+idApparato)).iterator(); 
%>
			<c:set var="idApparato" value='<%=idApparato%>'/>
			
		<table class="bordo" style="collapse;page-break-inside: avoid">
			<tr>
				<th colspan="3">
					<c:set var="name" value="esameObiettivo${idApparato}" />
	       			Esame Obiettivo "${requestScope[name].description}"
	       		</th> 	       	     
			</tr>
			<tr>
    		<td>
    			 Data
    		</td>
    		<td colspan="2">
    			<fmt:formatDate value="<%=((Date)request.getAttribute("dataEsamePart"+idApparato))%>" pattern="dd/MM/yyyy" var="data"/>    		
    			<c:out value="${data}"/>
    		</td>
        </tr> 
        <tr>
        	<td colspan="3">
        		&nbsp;
        	</td>
        </tr>   
        
        <tr>
        	<th colspan="3">Sintomatologia e Terapie Precedenti</th>
        </tr>
        <tr>
        	<td>Sintomi</td>
        	<c:set var="name" value="sintopatologia${idApparato}" />
        	<td colspan="2">${requestScope[name].esameObiettivoSintomis }</td>
        </tr>
        <tr>
        	<td>Periodo dell'insorgenza</td>
        	<td colspan="2">${requestScope[name].periodoInsorgenzaSintomi.description }</td>
        </tr>
        <tr>
        	<td>Andamento dell'insorgenza</td>
        	<td colspan="2">${requestScope[name].insorgenzaSintomi.description }</td>
        </tr>
        <tr>
        	<td>Terapie Precedenti</td>
        	<td colspan="2">${requestScope[name].terapiePrecedenti }</td>
        </tr>
        
        <tr>
        	<td colspan="3">
        		&nbsp;
        	</td>
        </tr>  
        
        
        <c:set var="name" value="patologieCongenite${idApparato}" />
        <c:if test="${requestScope[name].note}">
	        <tr>
	        	<th colspan="4">Patologie Congenite</th>
	        </tr>
			<tr>
				<td colspan="1">
		       		Note
		       	</td>
				<td colspan="3">
		       		${requestScope[name].note}
		       	</td>
			</tr>
			
			 <tr>
	        	<td colspan="3">
	        		&nbsp;
	        	</td>
	        </tr>   
		</c:if>	
<%
		
		if(superListApparato.hasNext())
		{
%>
		<tr>
        	<th colspan="3">Esame Organi e/o Tessuti</th>
        </tr>
		<tr>
			<th>
	       		Tipo
	       	</th>
	       	<th>
	       		Esito
	       	</th>
	       	<th>
	       		Anormalità
	       	</th> 	       	     
		</tr>		
<%
		}
%>	
	
		<c:set var="i" value='1'/>	
		
<%
		while(superListApparato.hasNext())
		{
			 Iterator<LookupEsameObiettivoEsito> sl = superListApparato.next().iterator();
			 String esitoAnormale = "NO";
			 String thereIsDescription = "NO";
			 String alreadyChecked = "NO";
			 Iterator eoList = cc.getEsameObiettivos().iterator();
			 while( eoList.hasNext() )
			 {
				EsameObiettivo eo = (EsameObiettivo) eoList.next();
				if( eo.getLookupEsameObiettivoTipo().getDescription().equals(listDescrizioni.get(countDescrizioni) ) )
				{
					if (eo.getNormale()) 
					{ 
						thereIsDescription = "YES";
					}
					else if (!eo.getNormale()) 
					{
						thereIsDescription = "YES";
						esitoAnormale = "SI";
					}
				}
			}
			if(thereIsDescription.equals("YES")) 
			{
%>
			<tr>
				<td style="width:40%">				
					<%= listDescrizioni.get(countDescrizioni) %>     
				</td>
				<input type="hidden" name="descrizione_${i}" value="<%=listDescrizioni.get(countDescrizioni)%>"/>
<%
				if(thereIsDescription.equals("YES") && esitoAnormale.equals("NO"))
				{
%>
					<td style="width:20%">
<%
					if(listDescrizioni.get(countDescrizioni).equalsIgnoreCase("Polmone"))
						out.println("Ascultazione nella norma");
					else
						out.println("Normale");
%>
					</td>
<% 
				}
				if(thereIsDescription.equals("YES") && esitoAnormale.equals("SI"))
				{
%>
					<td style="width:20%">
						Anormale
					</td>
<%
				}
				if(thereIsDescription.equals("NO"))
				{
%>
					<td style="width:20%">
						Non esaminato
					</td>	
<%
				}
%>
				<td style="width:40%">
					<div id="_${i }" 
<%
						if(esitoAnormale.equals("NO"))
						{
%>  
		  		       		style="display:none;"
<%
						}
%>
		    		>	
<%
					while(sl.hasNext())
					{
						LookupEsameObiettivoEsito temp = sl.next();
						String superesitoDaStampato = "NO";
						String isIn = "NO";
						Iterator<LookupEsameObiettivoEsito> figli = ((ArrayList<LookupEsameObiettivoEsito>)request.getAttribute("listEsameObiettivoFigliList"+idApparato)).iterator();
						while(figli.hasNext())
						{
							LookupEsameObiettivoEsito tempFiglio = figli.next();
							if(tempFiglio.getLookupEsameObiettivoEsito().getId()==temp.getId())
							{
								isIn = "YES";
								break;
							}
						}
						if(isIn.equals("NO"))
						{
							alreadyChecked = "NO";
							Iterator<EsameObiettivoEsito> esitiSelezionati = null;
							if(request.getAttribute("superListCheckedPart"+idApparato)!=null)
							{
								esitiSelezionati = ((ArrayList<EsameObiettivoEsito>)request.getAttribute("superListCheckedPart"+idApparato)).iterator();
							}
							if(esitiSelezionati!=null)
							{
								while(esitiSelezionati.hasNext())
								{
									EsameObiettivoEsito slc = esitiSelezionati.next();
									if(slc.getLookupEsameObiettivoEsito().getId()==temp.getId())
									{
										alreadyChecked = "YES";
										break;
									}
								}
							}
							if(alreadyChecked.equals("YES"))
							{
								out.println(temp.getDescription() + "<br/>");
								alreadyChecked = "NO";
							}
						}
						else
						{
							figli = ((ArrayList<LookupEsameObiettivoEsito>)request.getAttribute("listEsameObiettivoFigliList"+idApparato)).iterator();
							
							while(figli.hasNext())
							{
								LookupEsameObiettivoEsito figlio = figli.next();
											
							    if(figlio.getLookupEsameObiettivoEsito().getId() == temp.getId())
							    {
									isIn = "YES";
							    	alreadyChecked = "NO";
							    	Iterator<EsameObiettivoEsito> esitiSelezionati = null;
							    	if(request.getAttribute("superListCheckedPart"+idApparato)!=null)
									{
							    		esitiSelezionati = ((ArrayList<EsameObiettivoEsito>)request.getAttribute("superListCheckedPart"+idApparato)).iterator();
									}			
							    	if(esitiSelezionati!=null)
							    	{
										while(esitiSelezionati.hasNext())
										{
											EsameObiettivoEsito esito = esitiSelezionati.next();
											    
											if(esito.getLookupEsameObiettivoEsito().getId() == figlio.getId())
										    {
										    	alreadyChecked = "YES";
										    	break;
										    }
										}
								    }
									if(alreadyChecked.equals("YES") && superesitoDaStampato.equals("NO"))
									{
										superesitoDaStampato = "YES";
%>
										<label><b><i><%=temp.getDescription() %></i></b></label><br/>&nbsp;&nbsp;&nbsp;&nbsp;<%=figlio.getDescription() %>	
										<br/>												
<%
										alreadyChecked = "NO";
								    }
											
									if(alreadyChecked.equals("YES") && superesitoDaStampato.equals("YES"))
									{
%>
										&nbsp;&nbsp;&nbsp;&nbsp;<%=figlio.getDescription() %>	
										<br/>												
<%
										alreadyChecked = "NO";
									}
								}
							}
						}
					}
%>
					</div>
				</td>			
			</tr>
			<c:set var="i" value='${i+1}'/>	   
<%
			}
			countDescrizioni++; 	
		}
%>
	    
		<input type="hidden" name="numeroElementi" value="${i}"/>
		
	</table>	
	<br/><br/>	
<%
			}
		}
%>   		
   		
   		<br/>
   		</div>	
    
    <!-- DIARIO CLINICO -->
    	<div id="divDiario"  <c:choose>
   			<c:when test="${divDiario=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		  
   		<h2>DIARIO CLINICO</h2>
   		<br/><br/>
   	<table class="bordo" style="collapse;page-break-inside: avoid">
    		
    	  <%if(cc.getDiarioClinico().size() == 0) { %>
    	 	<tr>
    	 		<td>Diario Clinico non presente.</td>
    	 	</tr>
    	 <% } else { %>
    	 
    	<c:set var="i" value='1'/>	
        <c:forEach items="${cc.diarioClinico}" var="dc" >
        <tr><td><h3>APPARATO</h3></td>
    			<td>
    				<c:if test="${dc.apparato == null}">EOG</c:if>
					<c:if test="${dc.apparato != null}">${dc.apparato.description }</c:if>
    			</td></tr>
    	<tr><td><h3>DATE ESAME</h3></td>
    			<td>	<fmt:formatDate type="date" value="${dc.data}" pattern="dd/MM/yyyy" var="dataDiarioClinico"/>
    				${dataDiarioClinico}	
    			</td></tr>
    			<tr><td><h3>TEMPERATURA</h3></td>
    			<td>
    				${dc.temperatura}	
    			</td></tr>
    			<tr><td><h3>ESITO</h3></td>
    			<td>
							<c:forEach items="${dc.tipiEO }" var="teo">
					
									${teo.tipo.description }:
										<c:if test="${empty teo.esiti}">Normale</c:if>
										<c:if test="${not empty teo.esiti}">
											<c:forEach items="${teo.esiti }" var="esito">
												${esito }<br/>
											</c:forEach>
										</c:if><br/>
									</c:forEach>
						</td></tr>
    			<tr><td><h3>OSSERVAZIONI</h3></td><td align="center">
					<c:set var="newLine" value="\n" />
					${dc.osservazioni}<br/>
    			</td>
    		</tr>
    		<c:set var="i" value='${i+1}'/>
        </c:forEach>  
       	<% } %>				          
   	</table>
   	<br/>
   	</div>
    
    <!-- RICOVERO -->
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
   	    <h2> RICOVERO </h2><br/><br/>
   	     <table class="bordo" style="collapse;page-break-inside: avoid">
   	     <c:if test="${empty cc.ricoveroData}">
   		 	<tr><td>Nessun ricovero.</td></tr></c:if>
   	     <c:if test="${not empty cc.ricoveroData}">
   	     
       	<tr>
    		
    		<td>
    			<h3>MOTIVO</h3>
    		</td>
    		<td> 
    			${cc.ricoveroMotivo}	    			
    		</td>
    		<td>
   				<h3>IL</h3>
    		</td>
    		<td><c:if test="${cc.ricoveroData != null}">
    			<fmt:formatDate value="${cc.ricoveroData}" pattern="dd/MM/yyyy" var="dataRicovero"/>
	   			${dataRicovero}
	   			</c:if>
    		
        </tr>

        
         <tr>
    		<td>
    			<h3>SINTOMATOLOGIA ATTUALE</h3>
    		</td>
    		<td>
    			${cc.ricoveroSintomatologia}	    			
    		</td>
        </tr>
                
        <tr>
    		<td>
    			<h3>NOTE</h3>
    		</td>
    		<td>
    			${cc.ricoveroNote}  			
    		</td>
        </tr>
		</c:if>
        </table>
        <br/>
        </div>
        
   		<!-- DIAGNOSI -->
   		
   	<div id="divDiagn" <c:choose>
   			<c:when test="${divDiagn=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		
   	<table class="bordo" style="collapse;page-break-inside: avoid">
   	
   	<h2>DIAGNOSI</h2>
    	<br/><br/>
    		 <%if(diagnosi.size() == 0) { %>
    		<tr>
    			<td>Nessuna Diagnosi</td>
    		</tr>
    	<% } else { %>
    	 <c:set var="i" value='1'/>	
    	 <c:forEach items="${diagnosi}" var="d" >
    	<tr><td><h3>DATA</h3></td>
    	<fmt:formatDate value="${d.dataDiagnosi}" pattern="dd/MM/yyyy" var="dataDiagnosi"/>
    		 <td><c:out value="${dataDiagnosi}"></c:out>	</td>   
    			</tr>
<tr><td><H3>DIAGNOSI</H3></td>
 		<td>${d.diagnosiEffettuate}</td>
        </tr>
        
        <tr><td><H3>DIAGNOSI CHIRURGICHE</H3></td>
         	 <td>
    				${d.diagnosiChirurgiche}	
    			</td>
        </tr> </c:forEach>
    <%} %>
    
   	</table>
	</div>
	
	
	<!-- DIMISSIONI -->
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
        	<table class="bordo" style="collapse;page-break-inside: avoid">
   	  	 <c:choose> 
			<c:when test="${cc.ccMorto}"> 
			<tr>
    			<h2>
    				TRASPORTO SPOGLIE
    			</h2>
    		</tr>
    		<br/><br/>
    		<c:if test="${cc.accettazione.animale.dataSmaltimentoCarogna == null}">
    			<tr>
    				<td>
    					Nessun dato inserito relativo alla registrazione di Trasporto Spoglie.
    				</td>
    			</tr>
    		</c:if>
    		<c:if test="${cc.accettazione.animale.dataSmaltimentoCarogna != null}">
    		
    			<tr>
	    			<td>
	    				<h3>DATA</h3>
	    			</td>
	    			<td> 
	    			<fmt:formatDate type="date" value="${cc.accettazione.animale.dataSmaltimentoCarogna}" pattern="dd/MM/yyyy" var="dataSmaltimento"/>  	
	    			${dataSmaltimento}
	    			</td>    		
       	 		</tr>
        		<tr>
			        <td>
			    		<h3>DITTA AUTORIZZATA</h3>
		    		</td>
		    		<td>
		    			${cc.accettazione.animale.dittaAutorizzata}
			        </td>
			     </tr> 
			     <tr>  
			        <td>
			        	<H3>DDT</H3>
			        </td>
			        <td>
						 ${cc.accettazione.animale.ddt}
			        </td>
		        </tr> 
		     </c:if>
	    	</c:when>
	    	<c:otherwise>	
	    	
	    	 	<tr>
    				<h2>
    					DIMISSIONI
    				</h2><br/><br/>
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
	    				<h3>DATA</h3> 
	    			</td>
	    			<td> 
	    				<fmt:formatDate type="date" value="${cc.dataChiusura}" pattern="dd/MM/yyyy" var="dataChiusura"/>
	    				<c:out value="${dataChiusura}"/>
	    			</td>    		
       	 		</tr>
        		<tr>
			        <td>
			    		<H3>DESTINAZIONE DELL'ANIMALE</H3>
		    		</td>
		    		<td>
						${cc.destinazioneAnimale.description}
			        </td>
			        
		        </tr> 
		        <tr>
			        <td>
			    		<H3>INSERITA DA</H3>
		    		</td>
		    		<td>
						${cc.dimissioniEnteredBy.nome} ${cc.dimissioniEnteredBy.cognome}
			        </td>
			        
		        </tr> 
		        </c:if>
			</c:otherwise>
		</c:choose>	   
        </table>
        <br/>
        
        </div>
        
        <!-- CHIRURGIA -->
        
        <div id="divChir" <c:choose>
   			<c:when test="${divChir=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		
   	       <h2>SEZIONE CHIRURGICA</h2>
   	         <%if(cc.getTipoInterventi().size() == 0) { %>
   	         <br/><br/>
   	         	<table class="bordo" style="collapse;page-break-inside: avoid"><tr><td>Nessuna operazione.</td></tr></table>
   	         <%} %>
         <%if(cc.getTipoInterventi().size() != 0) { %>
    	 <c:set var="i" value='1'/>	
        <c:forEach items="${cc.tipoInterventi}" var="ti" >
         <fmt:formatDate type="date" value="${ti.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiestaChirurgia"/>
 				<h3>DATA: ${dataRichiestaChirurgia}</h3> 
    		<table style="border:1px solid;width:100%; ">		
        	<tr>
    			<td><h3>INTERVENTO:</h3> ${ti.note }<br/><br/><br/></td>
    		</tr>
    		<tr>
    			<td>
    				<h3>OPERATORI:</h3> ${ti.operatori}<br/><br/><br/>
    			</td>
    			
    		</tr>
    		<tr>
    			<td>
    				<h3>ANESTESIA:</h3> <br/><br/><br/><br/>
    			</td>
    			
    		</tr>
    		<tr>
    			<td>
    				<h3>DESCRIZIONE:</h3> <br/><br/><br/><br/>
    			</td>
    			
    		</tr>
    		<tr>
    			<td>
    				<h3>ESAME ISTOLOGICO:</h3> <br/><br/><br/><br/>
    			</td>
    			
    		</tr>
    		</table>
    		<c:if test="${i % 2 == 0}"><div class="pagebreak"></div></c:if>
    		<c:set var="i" value='${i+1}'/>
        </c:forEach>
        <% } %>  
   	
   	
   	<br/>
   	</div>
    
    <!-- TERAPIE -->
    
   <div id="divTerap" <c:choose>
   			<c:when test="${divTerap=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		 	<h2>TERAPIE</h2>
   		 	
   		 	<c:if test="${empty tdList}"><br/><br/>
   		 	
   		 	<table class="bordo" style="collapse;page-break-inside: avoid"><tr><td>Nessuna terapia inserita.</td></tr></table></c:if>
   	<c:set var="i" value='1'/>		
	        <c:forEach items="${tdList}" var="td" >
	          
		   	<h3>TERAPIA PRESCRITTA IL:</h3><fmt:formatDate value="${td.data}" pattern="dd/MM/yyyy HH:mm" var="dataTD"/>
		    			<c:out value="${dataTD}"></c:out>
		    			<br/><br/>
		    <h3>NOTE: </h3><hr><br/><hr><br/><hr><br/><hr><br/><hr><br/><hr><br/><hr><br/><hr><br/>
		    	<h3>TERAPIA IN DEGENZA</h3><br/><br/>
			    <table class="griglia">
			
    	<%if(tdList.size() != 0) { %>
	    	
	        	<c:forEach items="${td.tarapiaAssegnatas}" var="ta" >
		        <tr><td><h3>DATA</h3></td>
		    			<td>
		    				<fmt:formatDate value="${ta.data}" pattern="dd/MM/yyyy HH:mm" var="data"/>
		    			 		<c:out value="${data}"></c:out>	
		    			</td></tr>
		    			<tr><td><h3>FARMACO</h3></td><td>
		    			 	<c:out value="${ta.magazzinoFarmaci.lookupFarmaci.description}"></c:out>		
		    			</td></tr>
		    			<tr><td><h3>QUANTITA'</h3></td>
		    			<td>
		    				<c:out value="${ta.quantita} ${ta.unitaMisura}"></c:out>		
		    			</td></tr>
		    			<tr><td><h3>MODALITA' SOMMINISTRAZIONE</h3></td><td>
		    				<c:out value="${ta.lmsf.description}"></c:out>		
		    			</td></tr>
		    			<tr><td><h3>IL VETERINARIO</h3></td><td>
		    				<c:out value="${ta.enteredBy}"></c:out>		
		    			</td></tr>
		    			<tr><td><h3>NOTE</h3></td>
		    			<td>
		    				<c:out value="${ta.note}"></c:out>	
		    			</td></tr>
		    		</tr>
		    		<c:set var="i" value='${i+1}'/>
		    		
	        </c:forEach>    
	        </table>
	        <div class="pagebreak"></div>
	        <% } %>	          
	        </c:forEach>    
	    
   	
   	 	<br/>
   	</div>
   	
   	<!-- TRASFERIMENTI -->
   	<div id="divTrasf" <c:choose>
   			<c:when test="${divTrasf=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		  
   		<h2>TRASFERIMENTI</h2>
   		<BR/><BR/>
   	<table class="bordo" style="collapse;page-break-inside: avoid">
     
    	 <%if(trasferimenti.size() == 0) { %>
    		<tr>
    			<td>Nessun Trasferimento</td>
    		</tr>
    	<% } else { %>
    	 
    	<c:set var="i" value='1'/>	
        <c:forEach items="${trasferimenti}" var="tr" >
        	<tr><td><h3>STATO</h3></td><td>${tr.stato}</td></tr>	
    		<tr><td><H3>DATA RICHIESTA</H3></td><td><fmt:formatDate value="${tr.dataRichiesta}" pattern="dd/MM/yyyy"/></td></tr>	
    		<tr><td><H3>MOTIVAZIONI/OPERAZIONI RICHIESTE</H3></td><td>${tr.operazioniRichieste}</td></tr>	
    		<tr><td><H3>CLINICA DI ORIGINE</H3></td><td>(${tr.clinicaOrigine.lookupAsl }) ${tr.clinicaOrigine.nome }</td></tr>	
    		<tr><td><H3>CLINICA DI DESTINAZIONE</H3></td><td>(${tr.clinicaDestinazione.lookupAsl }) ${tr.clinicaDestinazione.nome }</td></tr>
    		<c:set var="i" value='${i+1}'/>
        </c:forEach>  
          <% } %>
   	</table>
   	<br/>
   	</div>
   	
   	<!-- ESAMI -->
   		<div id="divList" <c:choose>
   			<c:when test="${divList=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		
   		<div style="page-break-before:always"> &nbsp;</div>
   		<h2>ESAMI SPECIFICI RICHIESTI</h2>
    	<br/><br/>
    	<h3>INDAGINI DI LABORATORIO C/O IZS (PRELIEVO DEL SANGUE)</h3><br/><br/>
    	<table class="grigliaEsami esamiSangue">
    	   	
    	<tr>
        	<th>
        		ESAME
        	</th> 
        	<c:forEach items="${cc.esameSangues}" var="es" >  
	     	<th>
   				<fmt:formatDate type="date" value="${es.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiestaEsame"/> 
   				${dataRichiestaEsame}	
    		</th>
    		</c:forEach>
    		
    		
    		<c:if test="${empty cc.esameSangues}"> <!--  SE LA LISTA E' VUOTA, POPOLA CON 5 COLONNE VUOTE -->
			<th> </th>
			<th> </th>
			<th> </th>
			<th> </th>
			<th> </th>
			</c:if>
  
	     
    		 
        </tr> 
    	<c:if test="${not empty cc.esameSangues}"> <!--  SE LA LISTA NON E' VUOTA, POPOLA -->     	 
    	<jsp:include page="include_esamiSangue.jsp"/>
    	</c:if>
    	<c:if test="${empty cc.esameSangues}"> <!--  SE LA LISTA E' VUOTA, POPOLA CON 5 COLONNE VUOTE -->
    	<jsp:include page="include_esamiSangue_vuoto.jsp"/>
    	</c:if>
   	</table>
  	</div>
  	
  	</div>   	
  	
   	<div id="divListUrine" <c:choose>
   			<c:when test="${divListUrine=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   	
   		<h2>ESAMI SPECIFICI RICHIESTI</h2>
    	<br/><br/>
    	<h3>INDAGINI DI LABORATORIO C/O IZS (PRELIEVO DELLE URINE)</h3><br/><br/>
    	<table class="grigliaEsami esamiSangue">
    	   	
    	<tr>
        	<th>
        		ESAME
        	</th> 
        	<c:forEach items="${cc.esamiUrine}" var="es" >  
	     	<th>
   				<fmt:formatDate type="date" value="${es.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiestaEsame"/> 
   				${dataRichiestaEsame}	
    		</th>
    		</c:forEach>
    		
    		
    		<c:if test="${empty cc.esamiUrine}"> <!--  SE LA LISTA E' VUOTA, POPOLA CON 5 COLONNE VUOTE -->
			<th> </th>
			<th> </th>
			<th> </th>
			<th> </th>
			<th> </th>
			</c:if>
  
	     
    		 
        </tr> 
    	<c:if test="${not empty cc.esamiUrine}"> <!--  SE LA LISTA NON E' VUOTA, POPOLA -->     	 
    	<jsp:include page="include_esamiUrine.jsp"/>
    	</c:if>
    	<c:if test="${empty cc.esamiUrine}"> <!--  SE LA LISTA E' VUOTA, POPOLA CON 5 COLONNE VUOTE -->
    	<jsp:include page="include_esamiUrine_vuoto.jsp"/>
    	</c:if>
   	</table>
  	</div>
  	
  	</div>   	
  	
   	
   	<div id="divListCoprologico" <c:choose>
   			<c:when test="${divListCoprologico=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		
   		<div style="page-break-before:always"> &nbsp;</div>
   		<h2>ESAME COPROLOGICO</h2>
    	<br/><br/>
        	
        	 	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	
        			<c:if test="${empty cc.esameCoprologicos}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
   		 	
   		 	    		<c:forEach items="${cc.esameCoprologicos}" var="ec" >
    		 <tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${ec.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiestaCop"/>
		    			 		<c:out value="${dataRichiestaCop}"></c:out>	
		    			 		</td></tr>
		        <tr><td><h3>DATA ESITO</h3></td>
		        <td>
		        <fmt:formatDate value="${ec.dataEsito}" pattern="dd/MM/yyyy" var="dataEsitoCop"/>
		    			 		<c:out value="${dataEsitoCop}"></c:out>	
		    			 		</td></tr>
		    			
		    			<tr><td> <h3>ELMINTI</h3></td>
		    			<td> 	<c:out value="${ec.elminti}"></c:out>	</td></tr>
		    			<tr><td> <h3>PROTOZOI</h3></td>
		    				<td><c:out value="${ec.protozoi}"></c:out>		
		    			</td>
    		        </tr>
    		        </c:forEach>
   	</table>
  	</div>
  	
  	
  	<div id="divListEsterni" <c:choose>
   			<c:when test="${divListEsterni=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		
   		<h2>ESAMI ESTERNI</h2>
    	<br/><br/>
        		<c:if test="${cc.accettazione.animale.lookupSpecie.id==specie.cane}">
        	 	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<tr><td><h3>EHRLICHIOSI</h3></td></tr>
        	 	<c:if test="${empty cc.ehrlichiosis}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
    		<c:forEach items="${cc.ehrlichiosis}" var="ehr" >
		        <tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${ehr.dataRichiesta}" pattern="dd/MM/yyyy" var="dataEhr"/>
		    			 		<c:out value="${dataEhr}"></c:out>	
		    			 		</td></tr>
		    			
		    			<tr><td> <h3>ESITO</h3></td>
		    			<td> 	<c:out value="${ehr.lee.description}"></c:out>	</td></tr>
    		        </c:forEach>
   	</table>
   	
   	 	 	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<tr><td><h3>LEISHMANIOSI</h3></td></tr>
        	 	<c:if test="${empty cc.leishmaniosis}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
    		<c:forEach items="${cc.leishmaniosis}" var="leish" >
		        <tr><td><h3>DATA PRELIEVO</h3></td>
		        <td>
		        <fmt:formatDate value="${leish.dataPrelievoLeishmaniosi}" pattern="dd/MM/yyyy" var="dataLeish"/>
		    			 		<c:out value="${dataLeish}"></c:out>	
		    			 		</td></tr>
		    			
		    			<tr><td> <h3>ESITO</h3></td>
		    			<td> 	<c:out value="${leish.lle.description}"></c:out>	</td></tr>
    		        </c:forEach>
   	</table>
   	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<tr><td><h3>RICKETTSIOSI</h3></td></tr>
        	 	<c:if test="${empty cc.rickettsiosis}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
    		<c:forEach items="${cc.rickettsiosis}" var="rick" >
		        <tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${rick.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRick"/>
		    			 		<c:out value="${dataRick}"></c:out>	
		    			 		</td></tr>
		    			
		    			<tr><td> <h3>ESITO</h3></td>
		    			<td> 	<c:out value="${rick.lre.description}"></c:out>	</td></tr>
    		        </c:forEach>
   	</table>
     	</c:if>
   	
   		<c:if test="${cc.accettazione.animale.lookupSpecie.id==specie.gatto}">
        	 	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<tr><td><h3>FIV</h3></td></tr>
        	 	<c:if test="${empty cc.fivs}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
    		<c:forEach items="${cc.fivs}" var="fiv" >
		        <tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${fiv.dataRichiesta}" pattern="dd/MM/yyyy" var="dataFiv"/>
		    			 		<c:out value="${dataFiv}"></c:out>	
		    			 		</td></tr>
		    			
		    			<tr><td> <h3>ESITO</h3></td>
		    			<td> 	<c:out value="${fiv.esito}"></c:out>	</td></tr>
    		        </c:forEach>
   	</table>
   	
   	 	 	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<tr><td><h3>FIP</h3></td></tr>
        	 	<c:if test="${empty cc.fips}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
    		<c:forEach items="${cc.fips}" var="fip" >
		        <tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${fip.dataRichiesta}" pattern="dd/MM/yyyy" var="dataFip"/>
		    			 		<c:out value="${dataFip}"></c:out>	
		    			 		</td></tr>
		    			
		    			<tr><td> <h3>ESITO</h3></td>
		    			<td> 	<c:out value="${fip.esito}"></c:out>	</td></tr>
    		        </c:forEach>
   	</table>
   	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<tr><td><h3>FELV</h3></td></tr>
        	 	<c:if test="${empty cc.felvs}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
    		<c:forEach items="${cc.felvs}" var="felv" >
		        <tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${felv.dataRichiesta}" pattern="dd/MM/yyyy" var="dataFelv"/>
		    			 		<c:out value="${dataFelv}"></c:out>	
		    			 		</td></tr>
		    			
		    			<tr><td> <h3>ESITO</h3></td>
		    			<td> 	<c:out value="${felv.esito}"></c:out>	</td></tr>
    		        </c:forEach>
   	</table></c:if>
   	
   	<c:if test="${cc.accettazione.animale.lookupSpecie.id!=specie.sinantropo}">
   	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<tr><td><h3>TOXOPLASMOSI</h3></td></tr>
        	 	<c:if test="${empty cc.toxoplasmosis}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
    		<c:forEach items="${cc.toxoplasmosis}" var="topo" >
		        <tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${topo.dataRichiesta}" pattern="dd/MM/yyyy" var="dataTopo"/>
		    			 		<c:out value="${dataTopo}"></c:out>	
		    			 		</td></tr>
		    	<tr><td><h3>TIPO PRELIVEVO</h3></td>	
		    			<td><c:forEach items="${topo.tipoPrelievo}" var="tipo" > ${tipo.description }
		    			</c:forEach></td></tr>
		    			<tr><td> <h3>ESITO</h3></td>
		    			<td> 	<c:out value="${topo.esito}"></c:out>	</td></tr>
    		        </c:forEach>
   	</table>
   	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<tr><td><h3>RABBIA</h3></td></tr>
        	 	<c:if test="${empty cc.rabbias}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
    		<c:forEach items="${cc.rabbias}" var="rabbia" >
		        <tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${rabbia.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRabbia"/>
		    			 		<c:out value="${dataRabbia}"></c:out>	
		    			 		</td></tr>
		    			<c:if test="${rabbia.prelievoSangue}">
		    			<tr><td> <h3>ESITO SANGUE</h3></td>
		    			<td> 	<c:out value="${rabbia.esitoSangue}"></c:out>	</td></tr></c:if>
		    			<c:if test="${rabbia.prelievoEncefalo}">
		    			<tr><td> <h3>ESITO ENCEFALO</h3></td>
		    			<td> 	<c:out value="${rabbia.esitoEncefalo}"></c:out>	</td></tr></c:if>
    		        </c:forEach>
   	</table>
   	</c:if>
   	
  	</div>
  	
  	
  		<div id="divListDiagnosticaImmagini" <c:choose>
   			<c:when test="${divListDiagnosticaImmagini=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		
   		<h2>ESAME DIAGNOSTICA PER IMMAGINI</h2>
    	<br/><br/>
        	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<tr><td><h3>RX</h3></td></tr>
        	 	<c:if test="${empty cc.rxes}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
    		<c:forEach items="${cc.rxes}" var="rxes" >
    		  <tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${rxes.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiestaRxes"/>
		    			 		<c:out value="${dataRichiestaRxes}"></c:out>	
		    			 		</td></tr>
		        <tr><td><h3>DATA ESITO</h3></td>
		        <td>
		        <fmt:formatDate value="${rxes.dataEsito}" pattern="dd/MM/yyyy" var="dataEsitoRxes"/>
		    			 		<c:out value="${dataEsitoRxes}"></c:out>	
		    			 		</td></tr>
		    			
		    			<tr><td> <h3>NOTE</h3></td>
		    			<td> 	<c:out value="${rxes.note}"></c:out>	</td></tr>
    		        </c:forEach>
   	</table>
   	
   	 	 	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<tr><td><h3>TAC</h3></td></tr>
        	 	<c:if test="${empty cc.tacs}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
    		<c:forEach items="${cc.tacs}" var="tacs" >
    		  <tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${tacs.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiestaTacs"/>
		    			 		<c:out value="${dataRichiestaTacs}"></c:out>	
		    			 		</td></tr>
		        <tr><td><h3>DATA ESITO</h3></td>
		        <td>
		        <fmt:formatDate value="${tacs.dataEsito}" pattern="dd/MM/yyyy" var="dataEsitoTacs"/>
		    			 		<c:out value="${dataEsitoTacs}"></c:out>	
		    			 		</td></tr>
		    			
		    			<tr><td> <h3>NOTE</h3></td>
		    			<td> 	<c:out value="${tacs.note}"></c:out>	</td></tr>
    		        </c:forEach>
   	</table>
   	
   	   	 	 	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<tr><td><h3>ECO ADDOME</h3></td></tr>
        	 	<c:if test="${empty cc.ecoAddomes}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
    		<c:forEach items="${cc.ecoAddomes}" var="ecoAddomes" >
    		 <tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${ecoAddomes.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiestaecoAddomes"/>
		    			 		<c:out value="${dataRichiestaecoAddomes}"></c:out>	
		    			 		</td></tr>
		        <tr><td><h3>DATA ESITO</h3></td>
		        <td>
		        <fmt:formatDate value="${ecoAddomes.dataEsito}" pattern="dd/MM/yyyy" var="dataEsitoecoAddomes"/>
		    			 		<c:out value="${dataEsitoecoAddomes}"></c:out>	
		    			 		</td></tr>
		    		
    		        </c:forEach>
   	</table>
   	
   	   	   	 	 	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<tr><td><h3>ECO CUORE</h3></td></tr>
        	 		<c:if test="${empty cc.ecoCuores}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
   		 	
    		<c:forEach items="${cc.ecoCuores}" var="ecoCuores" >
    		 <tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${ecoCuores.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiestaecoCuores"/>
		    			 		<c:out value="${dataRichiestaecoCuores}"></c:out>	
		    			 		</td></tr>
		        <tr><td><h3>DATA ESITO</h3></td>
		        <td>
		        <fmt:formatDate value="${ecoCuores.dataEsito}" pattern="dd/MM/yyyy" var="dataEsitoecoCuores"/>
		    			 		<c:out value="${dataEsitoecoCuores}"></c:out>	
		    			 		</td></tr>
		    		   </c:forEach>
   	</table>
   	
  	</div>
  		<div id="divListECG" <c:choose>
   			<c:when test="${divListECG=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		
<div style="page-break-before:always"> &nbsp;</div>
   		<h2>ESAME ECG</h2>
    	<br/><br/>
        	
        		<table class="bordo" style="collapse;page-break-inside: avoid">
        			<c:if test="${empty cc.ecgs}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
   		 	
    		<c:forEach items="${cc.ecgs}" var="ecgs" >
    		<tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${ecgs.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiestaEcgs"/>
		    			 		<c:out value="${dataRichiestaEcgs}"></c:out>	
		    			 		</td></tr>
		        <tr><td><h3>DATA ESITO</h3></td>
		        <td>
		        <fmt:formatDate value="${ecgs.dataEsito}" pattern="dd/MM/yyyy" var="dataEsitoEcgs"/>
		    			 		<c:out value="${dataEsitoEcgs}"></c:out>	
		    			 		</td></tr>
		     <tr><td><h3>RITMO</h3></td>
		        <td><c:choose>
		        <c:when test="${ecgs.ritmo=='A'}">Aritmico</c:when>
		        <c:when test="${ecgs.ritmo=='S'}">Sinusale</c:when></c:choose>
		        		 		</td></tr>
    		        </c:forEach>
   	</table>
  	</div>
  	
  	<div id="divListIsto" <c:choose>
   			<c:when test="${divListIsto=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   	
   		<h2>ESAME ISTOPATOLOGICO</h2>
    	<br/><br/>
      <table class="bordo" style="collapse;page-break-inside: avoid">
        	 	<c:if test="${empty cc.esameIstopatologicos}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
   		 	
    		<c:forEach items="${cc.esameIstopatologicos}" var="eisto" >
    		 <table class="bordo" style="collapse;page-break-inside: avoid">
    		 <col width="20%">
    		<tr><td><h3>DATA RICHIESTA</h3></td>
		        <td>
		        <fmt:formatDate value="${eisto.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiestaEisto"/>
		    			 		<c:out value="${dataRichiestaEisto}"></c:out></td></tr>
		        <tr><td><h3>DATA ESITO</h3></td>
		        <td>
		        <fmt:formatDate value="${eisto.dataEsito}" pattern="dd/MM/yyyy" var="dataEsitoEisto"/>
		    			 		<c:out value="${dataEsitoEisto}"></c:out></td></tr>	
		    	<tr><td><h3>SEDE LESIONE</h3></td>
		        <td>
		     	<c:out value="${eisto.sedeLesione}"></c:out></td></tr>
		     	</table>
		           </c:forEach>
   	</table>
  	</div>
  	
  	<div id="divListCitologico" <c:choose>
   			<c:when test="${divListCitologico=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   		
   		<h2>ESAME CITOLOGICO</h2>
    	<br/><br/>
        	
        	 	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 		<c:if test="${empty cc.esameCitologicos}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
   		 	
    		<c:forEach items="${cc.esameCitologicos}" var="ecito" >
		        <tr><td><h3>DATA ESITO</h3></td>
		        <td>
		        <fmt:formatDate value="${ecito.dataEsito}" pattern="dd/MM/yyyy" var="dataEsitoEcito"/>
		    			 		<c:out value="${dataEsitoEcito}"></c:out>	
		    			 		</td></tr>
		      <tr><td><h3>DIAGNOSI</h3></td>
		        <td><c:out value="${ecito.diagnosi}"></c:out>	
		        		 		</td></tr>
    		        </c:forEach>
   	</table>
  	</div>
  	
  	  	<div id="divListNecro" <c:choose>
   			<c:when test="${divListNecro=='true'}">
   				style="display: block;"
   			</c:when>
   			<c:otherwise>
   				style="display: none;"
   			</c:otherwise>	
   		</c:choose>>
   	
   		<h2>ESAME NECROSCOPICO</h2>
    	<br/><br/>
        	
        	 	<table class="bordo" style="collapse;page-break-inside: avoid">
        	 		<c:if test="${empty cc.autopsia}">
   		 	<tr><td>Nessun esame inserito.</td></tr></c:if>
   		 	
   		 		<c:if test="${cc.accettazione.animale.necroscopiaNonEffettuabile==true}">
   		 	<tr><td>Esame necroscopico dichiarato non effettuabile.</td></tr></c:if>
   		 	
    			<c:if test="${not empty cc.autopsia}">
		        <tr><td><h3>DATA AUTOPSIA</h3></td>
		        <td>
		        <fmt:formatDate value="${cc.autopsia.dataAutopsia}" pattern="dd/MM/yyyy" var="dataAutopsia"/>
		    			 		<c:out value="${dataAutopsia}"></c:out>	
		    			 		</td></tr>
		    	<td>
		        <fmt:formatDate value="${cc.autopsia.dataEsito}" pattern="dd/MM/yyyy" var="dataEsitoAutopsia"/>
		    			 		<c:out value="${dataEsitoAutopsia}"></c:out>	
		    			 		</td></tr>
		      <tr><td><h3>MOTIVO DECESSO</h3></td>
		         <td><c:out value="${cc.autopsia.motivoFinaleDecesso}"></c:out></td></tr>
		         
		            <tr><td><h3>SALA SETTORIA DI DESTINAZIONE</h3></td>
		         <td><c:out value="${cc.autopsia.lass.description}"></c:out></td></tr>
		         
		            <tr><td><h3>DIAGNOSI ANATOMO PATOLOGICA</h3></td>
		         <td><c:out value="${cc.autopsia.diagnosiAnatomoPatologica}"></c:out></td></tr>
		         
		            <tr><td><h3>DIAGNOSI DEFINITIVA</h3></td>
		         <td><c:out value="${cc.autopsia.diagnosiDefinitiva}"></c:out></td></tr>
		         
		            <tr><td><h3>QUADRO PATOLOGICO PREVALENTE</h3></td>
		         <td><c:out value="${cc.autopsia.patologiaDefinitiva.description}"></c:out></td></tr>
		         
		         <tr><td><h3>NOTE</h3></td>
		         <td><c:out value="${cc.autopsia.note}"></c:out></td></tr>
		      
		        		 		</c:if>
    		     
   	</table>
  	</div>
      
       </div>

<script type="text/javascript">  	
function ctr(div)
{
	document.getElementById(div).style.display = 'block';
}
</script>	
  	
<!-- CARTELLA NECROSCOPICA -->
<%if (cc.getCcMorto()) { %>
<script type="text/javascript">
ctr('divDimis');
ctr('divTrasf');
ctr('divListIsto');
ctr('divListNecro');
</script>

<%} else { %>
<script type="text/javascript">
ctr('divAnamnesi');
ctr('divEsameObiettivoGenerale');
ctr('divEsameObiettivoParticolare');
ctr('divDiario');
ctr('divRic');
ctr('divDiagn');
ctr('divDimis');
ctr('divChir');
ctr('divTerap');
ctr('divTrasf');
ctr('divList');
ctr('divListUrine');
ctr('divListCoprologico');
ctr('divListDiagnosticaImmagini');
ctr('divListEsterni');
ctr('divListECG');
ctr('divListCitologico');
ctr('divListIsto');
</script>
<%} %>        	
  	
  	